package com.xlkfinance.bms.web.controller.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.alibaba.druid.util.StringUtils;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfig;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfigService;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHis;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHisService;
import com.xlkfinance.bms.rpc.inloan.RefundFeeIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService;
import com.xlkfinance.bms.rpc.report.BusinessApprovalBill;
import com.xlkfinance.bms.rpc.report.BusinessApprovalDetail;
import com.xlkfinance.bms.rpc.report.BusinessSummaryReport;
import com.xlkfinance.bms.rpc.report.ChechanReport;
import com.xlkfinance.bms.rpc.report.FinancialStatisticsReport;
import com.xlkfinance.bms.rpc.report.ForeclosureCapitalReport;
import com.xlkfinance.bms.rpc.report.ForeclosureOrganizationReport;
import com.xlkfinance.bms.rpc.report.ForeclosureReport;
import com.xlkfinance.bms.rpc.report.HandleDifferWarnReport;
import com.xlkfinance.bms.rpc.report.RefundFeesReport;
import com.xlkfinance.bms.rpc.report.RefuseProjectReport;
import com.xlkfinance.bms.rpc.report.ReportService;
import com.xlkfinance.bms.rpc.report.ReportService.Client;
import com.xlkfinance.bms.rpc.report.TrackRecordReport;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月1日下午3:47:00 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/reportController")
public class ReportController extends BaseController {
	private static final String financeHandle = "report/";
	private Logger logger = LoggerFactory.getLogger(ReportController.class);
	private final String serviceName = "ReportService";

	/**
	 * 预警事项首页跳转
	 */
	@RequestMapping(value = "/differWarnReportIndex")
	public String toDifferWarnReportIndex(ModelMap model) {
		return financeHandle + "differ_Warn_Report_index";
	}

	/**
	 * 业务汇总首页跳转
	 */
	@RequestMapping(value = "/businessSummaryReportIndex")
	public String toBusinessSummaryReportIndex(ModelMap model) {
		return financeHandle + "business_summary_report_index";
	}

	/**
	 * 撤单汇总首页跳转
	 */
	@RequestMapping(value = "/chechanReportIndex")
	public String toChechanIndex(ModelMap model) {
		return financeHandle + "chechan_report_index";
	}

	/**
	 * 业绩一览首页跳转
	 */
	@RequestMapping(value = "/trackRecordIndex")
	public String toTrackRecordIndex(ModelMap model) {
		return financeHandle + "track_record_index";
	}

	/**
	 * 报表统计跳转
	 */
	@RequestMapping(value = "/reportIndex")
	public String toReportIndex(ModelMap model, int type) {
		StringBuffer url = new StringBuffer(financeHandle);
		if (type == Constants.REFUND_FEE_TYPE_1) {// 退手续费
			url.append("refund_fee_report_index");
		} else if (type == Constants.REFUND_FEE_TYPE_2) {// 退利息
			url.append("refund_interest_fee_report_index");
		} else if (type == Constants.REFUND_FEE_TYPE_3) {// 退尾款
			url.append("refund_tail_money_report_index");
		}
		return url.toString();
	}

	/**
	 * 赎楼贷业务统计跳转
	 */
	@RequestMapping(value = "/foreclosureIndex")
	public String toForeclosureIndex(ModelMap model) {
		return financeHandle + "foreclosure_index";
	}

	/**
	 * 赎楼贷合作机构业务统计跳转
	 */
	@RequestMapping(value = "/foreclosureOrganizationIndex")
	public String toForeclosureOrganizationIndex(ModelMap model) {
		return financeHandle + "foreclosure_organization_index";
	}

	/**
	 * 赎楼贷资金方业务统计跳转
	 */
	@RequestMapping(value = "/foreclosureCapitalIndex")
	public String toForeclosureCapitalIndex(ModelMap model) {
		return financeHandle + "foreclosure_capital_index";
	}

	/**
	 * 财务统计报表跳转
	 */
	@RequestMapping(value = "/financialStatisticsIndex")
	public String toFinancialStatisticsIndex(ModelMap model) {
		return financeHandle + "financial_statistics_index";
	}

	/**
	 * 退咨询费统计跳转
	 */
	@RequestMapping(value = "/refundFeesIndex")
	public String toRefundFeesIndex(ModelMap model) {
		return financeHandle + "refund_fees_index";
	}

	/**
	 * 业务审批明细跳转
	 */
	@RequestMapping(value = "/businessApprovalDetailIndex")
	public String toBusinessApprovalDetailIndex(ModelMap model) {
		return financeHandle + "business_approval_detail_index";
	}

	/**
	 * 业务审批台账跳转
	 */
	@RequestMapping(value = "/businessApprovalBillIndex")
	public String toBusinessApprovalBillIndex(ModelMap model) {
		return financeHandle + "business_approval_bill_index";
	}
	
	/**
	 * 征信报告费用统计跳转
	 */
	@RequestMapping(value = "/creditReportFeeIndex")
	public String toCreditReportFeeIndex(ModelMap model) {
		return financeHandle + "credit_report_fee_index";
	}

	/**
	 * 新增或者在途笔数明细
	 * 
	 * @param projectId
	 * @param model
	 * @return
	 * @author: baogang
	 * @date: 2016年5月17日 上午10:24:38
	 */
	@RequestMapping(value = "getNewOrSquareOverView")
	public String toNewOrSquareOverView(ModelMap model,
			HttpServletRequest request, Integer newOrSquare,
			Integer mothOrWekNum, String rePaymentMonthId,Integer includeWt) {
		if (newOrSquare == 1) {
			model.put("rePaymentMonthId", rePaymentMonthId);
			model.put("mothOrWekNum", mothOrWekNum);
			model.put("includeWt", includeWt);
			return financeHandle + "new_count_index";
		} else {
			model.put("rePaymentMonthId", rePaymentMonthId);
			model.put("mothOrWekNum", mothOrWekNum);
			model.put("includeWt", includeWt);
			return financeHandle + "square_count_index";
		}
	}

	/**
	 * 业务审批台账明细
	 * 
	 * @param dateId
	 * @param model
	 * @return
	 * @author: Jony
	 * @date: 2016年5月17日 上午10:24:38
	 */
	@RequestMapping(value = "getBillOverView")
	public String toBillOverView(ModelMap model, HttpServletRequest request,
			String dateId, Integer mothOrWekNum, String approvalPerson,
			String approvalStep) {
		model.put("dateId", dateId);
		model.put("mothOrWekNum", mothOrWekNum);
		model.put("approvalPerson", approvalPerson);
		model.put("approvalStep", approvalStep);
		return financeHandle + "bill_count_index";
	}

	/**
	 * 退咨询费统计
	 *
	 * @author:liangyanjun
	 * @time:2016年3月7日下午3:13:45
	 * @param businessSummaryReport
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/refundFeesIndexReportList")
	@ResponseBody
	public void refundFeesIndexReportList(RefundFeesReport refundFeesReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (refundFeesReport == null)
			refundFeesReport = new RefundFeesReport();
		Map<String, Object> map = new HashMap<String, Object>();
		List<RefundFeesReport> refundFeesReportList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		refundFeesReport.setUserIds(getUserIds(request));
		try {
			// 查询咨询费统计
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			refundFeesReportList = service.queryRefundFeesReport(refundFeesReport);
			total = service.getRefundFeesReportTotal(refundFeesReport);
		} catch (Exception e) {
			logger.error("获取业务退咨询费列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + refundFeesReport);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", refundFeesReportList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 收退咨询费导出excel
	 *
	 * @author:Jony He
	 * @time:2016年11月30日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "refundFeesExportList")
	@ResponseBody
	public void refundFeesExportList(RefundFeesReport refundFeesReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (refundFeesReport == null) refundFeesReport = new RefundFeesReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		refundFeesReport.setUserIds(getUserIds(request));
		try {
			List<RefundFeesReport> list = reportService.queryRefundFeesReport(refundFeesReport);
			if(list !=null && list.size()>0){
				Date fromDate = null;
				Date endDate = null;
				String beginDate = "";
				String lastdate = "";
				for(int i = 0;i<list.size();i++){
					beginDate = list.get(i).getLoanDate();
					lastdate = list.get(i).getRecLoanDate();
					if(!StringUtils.isEmpty(beginDate) && !StringUtils.isEmpty(lastdate)){
						fromDate = DateUtils.stringToDate1(beginDate);
						endDate =  DateUtils.stringToDate1(lastdate);
						int realLoanDays = DateUtils.dayDifference(fromDate, endDate) + 1;
						list.get(i).setRealLoanDays(realLoanDays);
					}
				}
			}
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("TZXFTJ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"收退咨询费导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 财务报表统计
	 *
	 * @author:Jony He
	 * @time:2016年3月7日下午3:13:45
	 * @param businessSummaryReport
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/financialStatisticsReportList")
	@ResponseBody
	public void financialStatisticsReportList(
			FinancialStatisticsReport financialStatisticsReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (financialStatisticsReport == null)
			financialStatisticsReport = new FinancialStatisticsReport();
		Map<String, Object> map = new HashMap<String, Object>();
		List<FinancialStatisticsReport> financialStatisticstList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		financialStatisticsReport.setUserIds(getUserIds(request));
		try {
			//  查询财务报表统计
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			financialStatisticstList = service
					.queryFinancialStatisticsReport(financialStatisticsReport);
			total = service
					.getFinancialStatisticsReportTotal(financialStatisticsReport);
		} catch (Exception e) {
			logger.error("获取财务汇总列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + financialStatisticsReport);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", financialStatisticstList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 财务报表导出excel
	 *
	 * @author:Jony He
	 * @time:2016年11月30日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "financialStatisticsExportList")
	@ResponseBody
	public void financialStatisticsExportList(FinancialStatisticsReport financialStatisticsReport,
			HttpServletRequest request, HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		List<FinancialStatisticsReport> financialStatisticstList = null;
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		financialStatisticsReport.setUserIds(getUserIds(request));
		try {
			financialStatisticstList = reportService
					.queryFinancialStatisticsReport(financialStatisticsReport);
			map.put("bean", financialStatisticstList);
			TemplateFile template = templateFileService
					.getTemplateFile("CWTJBB");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"财务导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷业务统计
	 */

	@RequestMapping(value = "/foreclosureReportList")
	@ResponseBody
	public void foreclosureReportList(ForeclosureReport foreclosureReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (foreclosureReport == null)
			foreclosureReport = new ForeclosureReport();
		Map<String, Object> map = new HashMap<String, Object>();
		ReportService.Client service = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		try {
			List<ForeclosureReport> foreclosureList = null;
			int total = 0;
			foreclosureList = getList(foreclosureReport, request, service);
			total = service.getForeclosureReportTotal(foreclosureReport);
			// 输出
			map.put("rows", foreclosureList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷业务项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + foreclosureReport);
			e.printStackTrace();
		}
	}

	// 赎楼贷业务统计根据日期获取数据
	private List<ForeclosureReport> getList(
			ForeclosureReport foreclosureReport, HttpServletRequest request,
			ReportService.Client service) throws TException {
		List<ForeclosureReport> foreclosureList;
		// 设置数据权限--用户编号list
		foreclosureReport.setUserIds(getUserIds(request));
		String fromDate = request.getParameter("fromDate");
		String endDate = request.getParameter("endDate");

		int MonthOrWeekNum = foreclosureReport.getChooseMonthOrWeek();
		if (MonthOrWeekNum == 1 || MonthOrWeekNum == 2) {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "");
			}else{
				fromDate = "20150101";
			}
			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "");
			}else {
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyyMMdd");
				endDate = format.format(date);
			}
		} else {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "").substring(0,
						fromDate.length() - 4);
			}else{
				fromDate = "201501";
			}
			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "").substring(0,
						endDate.length() - 4);
			}else {
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyyMM");
				endDate = format.format(date);
			}
		}
		foreclosureReport.setFromDate(fromDate);
		foreclosureReport.setEndDate(endDate);
		// 查询赎楼贷业务项列表
		foreclosureList = service.queryForeclosureReport(foreclosureReport);
		return foreclosureList;
	}

	// 业务审批台账根据日期获取数据
	private List<BusinessApprovalBill> getList4(
			BusinessApprovalBill businessApprovalBill,
			HttpServletRequest request, ReportService.Client service)
			throws TException {
		List<BusinessApprovalBill> businessApprovalBillList;
		// 设置数据权限--用户编号list
		businessApprovalBill.setUserIds(getUserIds(request));

		String fromDate = request.getParameter("fromDate");
		String endDate = request.getParameter("endDate");

		int MonthOrWeekNum = businessApprovalBill.getChooseMonthOrWeek();
		if (MonthOrWeekNum == 1 || MonthOrWeekNum == 2) {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "");
			}
			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "");
			}
		} else {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "").substring(0,
						fromDate.length() - 4);
			}
			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "").substring(0,
						endDate.length() - 4);
			}
		}
		businessApprovalBill.setFromDate(fromDate);
		businessApprovalBill.setEndDate(endDate);
		// 查询赎楼贷业务项列表
		businessApprovalBillList = service.queryBusinessApprovalBill(businessApprovalBill);
		return businessApprovalBillList;
	}

	/**
	 * 
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷新增明细
	 */

	@RequestMapping(value = "/newCountList")
	@ResponseBody
	public void newCountList(Integer mothOrWekNum, String rePaymentMonthId,Integer includeWt,
			ForeclosureReport foreclosureReport, HttpServletRequest request,
			HttpServletResponse response) {
		if (foreclosureReport == null)
			foreclosureReport = new ForeclosureReport();
		Map<String, Object> map = new HashMap<String, Object>();
		ReportService.Client service = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		try {
			List<ForeclosureReport> newCountList = null;
			int total = 0;
			if (mothOrWekNum == 1) {// 周  "20160125~20160131"
				String fromWeek = rePaymentMonthId.substring(0, 8);
				foreclosureReport.setFromDate(fromWeek);
				String endWeek = rePaymentMonthId.substring(9);
				foreclosureReport.setEndDate(endWeek);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else if (mothOrWekNum == 2) {// 日
				String fromDay = rePaymentMonthId;
				String endDay = getEndDate(fromDay, mothOrWekNum);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
				foreclosureReport.setFromDate(fromDay);
				foreclosureReport.setEndDate(endDay);
			} else if (mothOrWekNum == 0) {// 月
				String year = (String) rePaymentMonthId.substring(0, 4);
				String month = (String) rePaymentMonthId.substring(4, 6);
				String fromMonth = year + "" + month + "" + "01";
				String endMonth = year + "" + month + "" + "31";
				foreclosureReport.setFromDate(fromMonth);
				foreclosureReport.setEndDate(endMonth);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else {
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			}
			newCountList = service.queryNewForeclosureReport(foreclosureReport);
			total = service.getNewForeclosureReportTotal(foreclosureReport);
			// 输出
			map.put("rows", newCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + foreclosureReport);
			e.printStackTrace();
		}
	}
	

	private String getEndDate(String fromDate, int mothOrWekNum) {
		String year = (String) fromDate.substring(0, 4);
		String month = (String) fromDate.substring(4, 6);
		String day = (String) fromDate.substring(6);
		String fromWeek = year + "-" + month + "-" + day;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(fromDate, pos);

		String sLateDate = "";
		Calendar calendar = Calendar.getInstance();
		String time = fromWeek;
		int iDays = 7;
		if (mothOrWekNum == 2) {
			iDays = 1;
		}
		String[] arrDate = time.split("-");
		int iYear = Integer.valueOf(arrDate[0]);
		int iMonth = Integer.valueOf(arrDate[1]);
		int iDay = Integer.valueOf(arrDate[2]);

		calendar.set(iYear, iMonth, iDay);
		calendar.add(Calendar.MONTH, -1);// 因为Month值从0开始，所以取得的值应该减去1
		calendar.add(Calendar.DATE, iDays);

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");// 构造日期格式化器
		date = calendar.getTime();
		sLateDate = sdf.format(date);
		String endDate = sLateDate.replaceAll("-", "");
		return endDate;
	}

	/**
	 * 新增详情列表导出excel
	 *
	 * @author:Jony
	 * @time:2016年11月1日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "newCountExportList")
	@ResponseBody
	public void newCountExportList(ForeclosureReport foreclosureReport,
			String projectIds, HttpServletRequest request,
			HttpServletResponse response) {
		if (foreclosureReport == null)
			foreclosureReport = new ForeclosureReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ForeclosureReport> list = new ArrayList<ForeclosureReport>();
		try {
			int mothOrWekNum = foreclosureReport.getChooseMonthOrWeek();
			String rePaymentMonthId = foreclosureReport.getRePaymentMonthId();
			
			if (mothOrWekNum == 1) {// 周  "20160125~20160131"
				String fromWeek = rePaymentMonthId.substring(0, 8);
				foreclosureReport.setFromDate(fromWeek);
				String endWeek = rePaymentMonthId.substring(9);
				foreclosureReport.setEndDate(endWeek);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else if (mothOrWekNum == 2) {// 日
				String fromDay = rePaymentMonthId;
				String endDay = getEndDate(fromDay, mothOrWekNum);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
				foreclosureReport.setFromDate(fromDay);
				foreclosureReport.setEndDate(endDay);
			} else if (mothOrWekNum == 0) {// 月
				String year = (String) rePaymentMonthId.substring(0, 4);
				String month = (String) rePaymentMonthId.substring(4, 6);
				String fromMonth = year + "" + month + "" + "01";
				String endMonth = year + "" + month + "" + "31";
				foreclosureReport.setFromDate(fromMonth);
				foreclosureReport.setEndDate(endMonth);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else {
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			}
			 list = reportService.queryNewForeclosureReport(foreclosureReport);
			
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("XZXQMB");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"结清详情导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 * 赎楼贷在途明细统计
	 */

	@RequestMapping(value = "/squareCountList")
	@ResponseBody
	public void squareCountList(Integer mothOrWekNum, String rePaymentMonthId,
			ForeclosureReport foreclosureReport,Integer includeWt, HttpServletRequest request,
			HttpServletResponse response) {
		if (foreclosureReport == null)
			foreclosureReport = new ForeclosureReport();
		Map<String, Object> map = new HashMap<String, Object>();
		ReportService.Client service = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		try {
			List<ForeclosureReport> squareCountList = null;
			int total = 0;
			if (mothOrWekNum == 1) {// 周  "20160125~20160131"
				String fromWeek = rePaymentMonthId.substring(0, 8);
				foreclosureReport.setFromDate(fromWeek);
				String endWeek = rePaymentMonthId.substring(9);
				foreclosureReport.setEndDate(endWeek);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else if (mothOrWekNum == 2) {// 日
				String fromDay = rePaymentMonthId;
				String endDay = getEndDate(fromDay, mothOrWekNum);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
				foreclosureReport.setFromDate(fromDay);
				foreclosureReport.setEndDate(endDay);
			} else if (mothOrWekNum == 0) {// 月
				String year = (String) rePaymentMonthId.substring(0, 4);
				String month = (String) rePaymentMonthId.substring(4, 6);
				String fromMonth = year + "" + month + "" + "01";
				String endMonth = year + "" + month + "" + "31";
				foreclosureReport.setFromDate(fromMonth);
				foreclosureReport.setEndDate(endMonth);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else {
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			}
			squareCountList = service.querySquareForeclosureReport(foreclosureReport);
			total = service.getSquareForeclosureReportTotal(foreclosureReport);
			// 输出
			map.put("rows", squareCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + foreclosureReport);
			e.printStackTrace();
		}
	}

	/**
	 * 结清详情列表导出excel
	 *
	 * @author:Jony
	 * @time:2016年11月1日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "squareCountExportList")
	@ResponseBody
	public void squareCountExportList(ForeclosureReport foreclosureReport,
			String projectIds, HttpServletRequest request,
			HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ForeclosureReport> list = new ArrayList<ForeclosureReport>();
		try {
			int mothOrWekNum = foreclosureReport.getChooseMonthOrWeek();
			String rePaymentMonthId = foreclosureReport.getRePaymentMonthId();
			if (mothOrWekNum == 1) {// 周  "20160125~20160131"
				String fromWeek = rePaymentMonthId.substring(0, 8);
				foreclosureReport.setFromDate(fromWeek);
				String endWeek = rePaymentMonthId.substring(9);
				foreclosureReport.setEndDate(endWeek);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else if (mothOrWekNum == 2) {// 日
				String fromDay = rePaymentMonthId;
				String endDay = getEndDate(fromDay, mothOrWekNum);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
				foreclosureReport.setFromDate(fromDay);
				foreclosureReport.setEndDate(endDay);
			} else if (mothOrWekNum == 0) {// 月
				String year = (String) rePaymentMonthId.substring(0, 4);
				String month = (String) rePaymentMonthId.substring(4, 6);
				String fromMonth = year + "" + month + "" + "01";
				String endMonth = year + "" + month + "" + "31";
				foreclosureReport.setFromDate(fromMonth);
				foreclosureReport.setEndDate(endMonth);
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			} else {
				foreclosureReport.setChooseMonthOrWeek(mothOrWekNum);
			}
			list = reportService.querySquareForeclosureReport(foreclosureReport);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("JQXQMB");

			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"结清详情导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 赎楼贷业务导出excel
	 *
	 * @author:Jony
	 * @time:2016年11月1日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "foreclosureExportList")
	@ResponseBody
	public void foreclosureExportList(ForeclosureReport foreclosureReport,
			 HttpServletRequest request,
			HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client service = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ForeclosureReport> foreclosureList = new ArrayList<>();
		try {
			foreclosureList = getList(foreclosureReport, request, service);
			map.put("bean", foreclosureList);
			TemplateFile template = templateFileService
					.getTemplateFile("SLDYWTJ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,path,"赎楼贷导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 赎楼贷合作机构列表导出excel
	 *
	 * @author:Jony
	 * @time:2016年11月1日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "foreclosureOrgExportList")
	@ResponseBody
	public void foreclosureOrgExportList(
			ForeclosureOrganizationReport foreclosureOrganizationReport,
			String rePaymentMonthIds, HttpServletRequest request,
			HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client service = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ForeclosureOrganizationReport> foreclosureOrgList = new ArrayList<>();
		try {
			foreclosureOrgList = getList2(foreclosureOrganizationReport, request, service);
			//启用额度
			String availableLimit = null;
			//单笔上线
			String singeUpperLimit= null;
			//保证金
			String assureMoney= null;
			//累计金额
			String sumNewMoney= null;
			//在途金额
			String ingMoney= null;
			//结清金额
			double squareMoney = 0.00;
			//新增金额
			double newMoney = 0.00;
			
			for(ForeclosureOrganizationReport index : foreclosureOrgList){
				availableLimit = index.getAvailableLimit();
				assureMoney = index.getAssureMoney();
				singeUpperLimit = index.getSingeUpperLimit();
				sumNewMoney = index.getSumNewMoney();
				ingMoney = index.getIngMoney();
				newMoney = index.getNewMoney();
				squareMoney = index.getSquareMoney();
				newMoney = index.getNewMoney();
				if(!"--".equals(availableLimit)){
					availableLimit = availableLimit.substring(0, availableLimit.length()-4);
					index.setAvailableLimit(availableLimit);
				}
				if((!"--".equals(assureMoney)) && (assureMoney.length()>4)){
					assureMoney = assureMoney.substring(0, assureMoney.length()-4);
					index.setAssureMoney(assureMoney);
				}
				if((!"--".equals(singeUpperLimit)) && (singeUpperLimit.length()>4)){
					singeUpperLimit = singeUpperLimit.substring(0, singeUpperLimit.length()-4);
					index.setSingeUpperLimit(singeUpperLimit);
				}

				if((!"--".equals(sumNewMoney)) && (sumNewMoney.length()>4)){
					sumNewMoney = sumNewMoney.substring(0, sumNewMoney.length()-4);
					index.setSumNewMoney(sumNewMoney);
				}
				if((!"--".equals(ingMoney)) && (ingMoney.length()>4)){
					ingMoney = ingMoney.substring(0, ingMoney.length()-4);
					index.setIngMoney(ingMoney);
				}
				
				if(newMoney > 0){
					newMoney = newMoney/10000;
					index.setNewMoney(newMoney);
				}
				if(squareMoney > 0){
					squareMoney = squareMoney/10000;
					index.setSquareMoney(squareMoney);
				}
			}
			map.put("bean", foreclosureOrgList);
			TemplateFile template = templateFileService
					.getTemplateFile("SLDHZYWTJ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"赎楼贷合作机构导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	

    
    /**
     * 资金方列表导出excel
     *@author:Jony
     *@time:2016年11月1日下午8:26:39
     *@param pids
     *@param request
     *@param response
     */
    @RequestMapping(value = "foreclosureCapExportList")
    @ResponseBody
    public void foreclosureCapExportList(ForeclosureCapitalReport foreclosureCapitalReport,String rePaymentMonthIds, HttpServletRequest request, HttpServletResponse response) {
       TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
       ReportService.Client service = (Client) getService(BusinessModule.MODUEL_REPORT, serviceName);
       Map<String, Object> map = new HashMap<String, Object>();
       try {
    	   List<ForeclosureCapitalReport> tempList = null;
    	  
    	   int mOrWnum = foreclosureCapitalReport.getChooseMonthOrWeek();
    	   foreclosureCapitalReport.setChooseMonthOrWeek(mOrWnum);
    	   tempList = getList3(foreclosureCapitalReport, request, service);
           map.put("bean", tempList);								
          TemplateFile template = templateFileService.getTemplateFile("ZJYWTJL");
          
          if (template!=null&&!template.getFileUrl().equals("")) {
             String path = CommonUtil.getRootPath() + template.getFileUrl();
             ExcelExport.outToExcel(map, path, "资金方业务导出" + "." + template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".") + 1), response, request);
          }
       } catch (Exception e) {
          e.printStackTrace();
       }
    }
    /**
     * 
     *@author:Jony
     *@time:2016年11月1日下午8:36:32
     *@param ForeclosureReport
     *@param request
     *@param response
     *赎楼贷合作机构统计
     */
    @RequestMapping(value = "/foreclosureOrganizationReportList")
    @ResponseBody
    public void foreclosureOrganizationReportList(ForeclosureOrganizationReport foreclosureOrganizationReport, HttpServletRequest request, HttpServletResponse response) {
       if (foreclosureOrganizationReport==null)foreclosureOrganizationReport=new ForeclosureOrganizationReport();
       Map<String, Object> map = new HashMap<String, Object>();
       ReportService.Client service = (Client) getService(BusinessModule.MODUEL_REPORT, serviceName);
       try {
       List<ForeclosureOrganizationReport> foreclosureOrganizationReportList = null;
       int total = 0;
       foreclosureOrganizationReportList = getList2(foreclosureOrganizationReport, request, service);
          total = service.getForeclosureOrganizationReportTotal(foreclosureOrganizationReport);
          // 输出
          map.put("rows", foreclosureOrganizationReportList);
          map.put("total", total);
          outputJson(map, response);
       } catch (Exception e) {
          logger.error("赎楼贷业务项列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + foreclosureOrganizationReport);
          e.printStackTrace();
       }
    }
    

	private List<ForeclosureOrganizationReport> getList2(
			ForeclosureOrganizationReport foreclosureOrganizationReport,
			HttpServletRequest request, ReportService.Client service)
			throws TException {
		List<ForeclosureOrganizationReport> ForeclosureOrganizationReportList;
		
		String fromDate = foreclosureOrganizationReport.getFromDate();
		String endDate = foreclosureOrganizationReport.getEndDate();

		int MonthOrWeekNum = foreclosureOrganizationReport
				.getChooseMonthOrWeek();
		if (MonthOrWeekNum == 1 || MonthOrWeekNum == 2) {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "");
			} else {
				fromDate = "20150101";
			}
			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "");
			} else {
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyyMMdd");
				endDate = format.format(date);
			}

		} else {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "").substring(0,
						fromDate.length() - 4);
			} else {
				fromDate = "201501";
			}

			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "").substring(0,
						endDate.length() - 4);
			} else {
				Date date = new Date();
				DateFormat format = new SimpleDateFormat("yyyyMM");
				endDate = format.format(date);
			}
		}
		foreclosureOrganizationReport.setFromDate(fromDate);
		foreclosureOrganizationReport.setEndDate(endDate);
		// 查询赎楼贷业务项列表
		ForeclosureOrganizationReportList = service
				.queryForeclosureOrganizationReport(foreclosureOrganizationReport);
		return ForeclosureOrganizationReportList;
	}

	/**
	 * 
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 * 资金方业务统计
	 */
	@RequestMapping(value = "/foreclosureCapitalReportList")
	@ResponseBody
	public void foreclosureCapitalReportList(
			ForeclosureCapitalReport foreclosureCapitalReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (foreclosureCapitalReport == null)
			foreclosureCapitalReport = new ForeclosureCapitalReport();
		Map<String, Object> map = new HashMap<String, Object>();
		ReportService.Client service = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		try {
			List<ForeclosureCapitalReport> foreclosureCapitalReporttList = null;
			int total = 0;
			foreclosureCapitalReporttList = getList3(foreclosureCapitalReport,
					request, service);
			total = service
					.getForeclosureCapitalReportTotal(foreclosureCapitalReport);
			// 输出
			map.put("rows", foreclosureCapitalReporttList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷业务项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + foreclosureCapitalReport);
			e.printStackTrace();
		}
	}

	private List<ForeclosureCapitalReport> getList3(
			ForeclosureCapitalReport foreclosureCapitalReport,
			HttpServletRequest request, ReportService.Client service)
			throws TException {
		List<ForeclosureCapitalReport> foreclosureCapitalReportList;
		// 设置数据权限--用户编号list
		foreclosureCapitalReport.setUserIds(getUserIds(request));
		String fromDate = request.getParameter("fromDate");
		String endDate = request.getParameter("endDate");

		int MonthOrWeekNum = foreclosureCapitalReport.getChooseMonthOrWeek();
		if (MonthOrWeekNum == 1 || MonthOrWeekNum == 2) {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "");
			}
			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "");
			}
		} else {
			if (!StringUtil.isBlank(fromDate)) {
				fromDate = fromDate.replaceAll("-", "").substring(0,
						fromDate.length() - 4);
			}

			if (!StringUtil.isBlank(endDate)) {
				endDate = endDate.replaceAll("-", "").substring(0,
						endDate.length() - 4);
			}
		}
		foreclosureCapitalReport.setFromDate(fromDate);
		foreclosureCapitalReport.setEndDate(endDate);
		// 查询赎楼贷业务项列表
		foreclosureCapitalReportList = service
				.queryForeclosureCapitalReport(foreclosureCapitalReport);
		return foreclosureCapitalReportList;
	}

	/**
	 * 
	 * @author:liangyanjun
	 * @time:2016年3月1日下午8:36:32
	 * @param handleDifferWarnReport
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/differWarnReportList")
	@ResponseBody
	public void differWarnReportList(
			HandleDifferWarnReport handleDifferWarnReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (handleDifferWarnReport == null)
			handleDifferWarnReport = new HandleDifferWarnReport();
		Map<String, Object> map = new HashMap<String, Object>();
		List<HandleDifferWarnReport> differWarnReportList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		handleDifferWarnReport.setUserIds(getUserIds(request));
		try {
			// 查询预警事项列表
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			differWarnReportList = service
					.queryHandleDifferWarnReport(handleDifferWarnReport);
			total = service
					.getHandleDifferWarnReportTotal(handleDifferWarnReport);
			// logger.info("预警事项查询列表查询成功：total：" + total +
			// ",differWarnReportList:" + differWarnReportList);
		} catch (Exception e) {
			logger.error("获取预警事项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + handleDifferWarnReport);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", differWarnReportList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 预警导出excel
	 *
	 * @author:liangyanjun
	 * @time:2016年3月2日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "differWarnExcelExportList")
	@ResponseBody
	public void differWarnExcelExportList(HandleDifferWarnReport query,
			HttpServletRequest request, HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		try {
			List<HandleDifferWarnReport> list = reportService.queryHandleDifferWarnReport(query);
			if (list != null && list.size() > 0) {
				for (HandleDifferWarnReport handleDifferWarnReport : list) {
					String loanWay = handleDifferWarnReport.getLoanWay();
					handleDifferWarnReport.setLoanWay(Constants.LOAN_WAY_MAP.get(loanWay + ""));
				}
			}
			map.put("bean", list);
			TemplateFile template = templateFileService
					.getTemplateFile("YJTJBB");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"预警导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author:Jony
	 * @time:2016年3月1日下午8:36:32
	 * @param businessApprovalDetail
	 * @param request
	 * @param response
	 *  业务审批明细
	 */
	@RequestMapping(value = "/businessApprovalDetailList")
	@ResponseBody
	public void businessApprovalDetailList(BusinessApprovalDetail businessApprovalDetail,
			HttpServletRequest request, HttpServletResponse response) {
		if (businessApprovalDetail == null)
			businessApprovalDetail = new BusinessApprovalDetail();
		Map<String, Object> map = new HashMap<String, Object>();
		List<BusinessApprovalDetail> businessApprovalDetailList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		businessApprovalDetail.setUserIds(getUserIds(request));
		try {
			// 查询业务明细列表
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			businessApprovalDetailList = service.queryBusinessApprovalDetail(businessApprovalDetail);
			total = service.getBusinessApprovalDetailTotal(businessApprovalDetail);
		} catch (Exception e) {
			logger.error("获取业务审批明细失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + businessApprovalDetail);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", businessApprovalDetailList);
		map.put("total", total);
		outputJson(map, response);
	}
	/**
	 * 业务审批明细导出excel
	 *
	 * @author:jony
	 * @time:2016年3月2日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "businessApprovalDetailExportList")
	@ResponseBody
	public void businessApprovalDetailExportList(BusinessApprovalDetail businessApprovalDetail,
			HttpServletRequest request, HttpServletResponse response) {
		if (businessApprovalDetail == null)businessApprovalDetail = new BusinessApprovalDetail();
		// 设置数据权限--用户编号list
		businessApprovalDetail.setUserIds(getUserIds(request));
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		List<BusinessApprovalDetail> list = null;
		try {
			 list = reportService.queryBusinessApprovalDetail(businessApprovalDetail);
			 if(list != null && list.size() > 0){
				 for(BusinessApprovalDetail index: list){
					 if(index.getApprovalStatus()==3){
						 index.setApprovalStatusName("通过");
					 }else{
						 index.setApprovalStatusName("不通过");
					 }
					 if(!StringUtil.isBlank(index.getApprovalDate())){
//					 if(index.getApprovalDate() != null && index.getApprovalDate() !=""){
						  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					       String dateStr =index.getApprovalDate();
					       Date d = parseDate(dateStr);
					       String approvalDate = sdf.format(d);
					       index.setApprovalDate(approvalDate);
					 }
				 }
			 }
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("YWSPMXTJ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"业务审批明细导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 static public Date parseDate(String s) throws ParseException {
	        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	        return format.parse(s);
	    }

	/**
	 * 
	 * @author: Jony
	 * @time:2016年3月1日下午8:36:32
	 * @param businessApprovalDetail
	 * @param request
	 * @param response
	 *  业务审批台账
	 */
	@RequestMapping(value = "/businessApprovalBillList")
	@ResponseBody
	public void businessApprovalBillList(BusinessApprovalBill businessApprovalBill,
			HttpServletRequest request, HttpServletResponse response) {
		if (businessApprovalBill == null)
			businessApprovalBill = new BusinessApprovalBill();
		Map<String, Object> map = new HashMap<String, Object>();
		List<BusinessApprovalBill> businessApprovalBillList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		businessApprovalBill.setUserIds(getUserIds(request));
		try {
			// 查询业务台账列表
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			businessApprovalBillList = getList4(businessApprovalBill, request,
					service);
			total = service.getBusinessApprovalBillTotal(businessApprovalBill);
		} catch (Exception e) {
			logger.error("获取业务审批明细失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + businessApprovalBill);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", businessApprovalBillList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 业务审批台账导出excel
	 *
	 * @author:Jony
	 * @time:2016年3月2日下午8:26:39
	 * @param pids
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "businessApprovalBillExportList")
	@ResponseBody
	public void businessApprovalBillExportList(BusinessApprovalBill businessApprovalBill,
			HttpServletRequest request, HttpServletResponse response) {
		if (businessApprovalBill == null)
			businessApprovalBill = new BusinessApprovalBill();
		businessApprovalBill.setUserIds(getUserIds(request));
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<BusinessApprovalBill> list = getList4(businessApprovalBill, request,reportService);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("YWSPTZTJ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"业务审批台账导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param BusinessApprovalBill
	 * @param request
	 * @param response
	 * 赎楼贷新增明细
	 */

	@RequestMapping(value = "/billCountList")
	@ResponseBody
	public void billCountList(Integer mothOrWekNum, String dateId,
			String approvalPerson, String approvalStep,
			BusinessApprovalBill businessApprovalBill,
			HttpServletRequest request, HttpServletResponse response) {
		if (businessApprovalBill == null)
			businessApprovalBill = new BusinessApprovalBill();
		Map<String, Object> map = new HashMap<String, Object>();
		ReportService.Client service = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
//		if (!"".equals(approvalPerson) && !"".equals(approvalStep)) {
		if (!StringUtil.isBlank(approvalPerson) && StringUtil.isBlank(approvalStep)) {
			businessApprovalBill.setApprovalPerson(approvalPerson);
			businessApprovalBill.setApprovalStep(approvalStep);
		}
		try {
			List<BusinessApprovalBill> billCountList = null;
			int total = 0;
			if (mothOrWekNum == 1) {// 周"2017/02/20-2017/02/26"
				String fromWeek = dateId.substring(0, 10);
				String endWeek = dateId.substring(11);
				
				businessApprovalBill.setFromDate(fromWeek);
				businessApprovalBill.setEndDate(endWeek);
				businessApprovalBill.setChooseMonthOrWeek(mothOrWekNum);
			} else if (mothOrWekNum == 2) {// 日
				String fromDay = dateId + "";
				String endDay = getEndDate(fromDay, mothOrWekNum);
				businessApprovalBill.setChooseMonthOrWeek(mothOrWekNum);
				businessApprovalBill.setFromDate(fromDay);
				businessApprovalBill.setEndDate(endDay);
			} else {// 月
				String rePaymentMonth = dateId + "";
				String year = (String) rePaymentMonth.substring(0, 4);
				String month = (String) rePaymentMonth.substring(4, 6);
				String fromMonth = year + "" + month + "" + "01";
				String endMonth = "";
				//判断二月多少天，闰年就是29 返回true
				boolean isLeapyear = isLeapyear(Integer.parseInt(year));
				if("02".equals(month)){
					if(isLeapyear){
						endMonth = year + "" + month + "" + "29";
					}else {
						endMonth = year + "" + month + "" + "28";
					}
				}else if("04".equals(month) || "06".equals(month) || "09".equals(month) || "11".equals(month) ){
					endMonth = year + "" + month + "" + "30";
				}else{
					endMonth = year + "" + month + "" + "31";
				}
				businessApprovalBill.setFromDate(fromMonth);
				businessApprovalBill.setEndDate(endMonth);
				businessApprovalBill.setChooseMonthOrWeek(mothOrWekNum);
			}
			businessApprovalBill.setUserIds(getUserIds(request));
			billCountList = service.queryBusinessApprovalBillCount(businessApprovalBill);
			total = service
					.getBusinessApprovalBillCountTotal(businessApprovalBill);
			// 输出
			map.put("rows", billCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + businessApprovalBill);
			e.printStackTrace();
		}
	}
	//判断二月多少天
	private static boolean isLeapyear(int year) {
        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
            return true;
        } else {
            return false;
        }
    }

	/**
	 * 业务审批台账明细导出
	 *
	 * @author:Jony
	 * @time:2016年3月7日下午3:13:45
	 * @param businessApprovalBill
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/billCountDetailExportList")
	@ResponseBody
	public void billCountDetailExportList(BusinessApprovalBill businessApprovalBill,
			HttpServletRequest request, HttpServletResponse response) {
		if (businessApprovalBill == null)businessApprovalBill = new BusinessApprovalBill();
		List<BusinessApprovalBill> list = new ArrayList<BusinessApprovalBill>();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			int mothOrWekNum = businessApprovalBill.getChooseMonthOrWeek();
			String dateId = businessApprovalBill.getDateId();
			
			if (mothOrWekNum == 1) {// 周"2017/02/20-2017/02/26"
				String fromWeek = dateId.substring(0, 10);
				String endWeek = dateId.substring(11);
				
				businessApprovalBill.setFromDate(fromWeek);
				businessApprovalBill.setEndDate(endWeek);
				businessApprovalBill.setChooseMonthOrWeek(mothOrWekNum);
			} else if (mothOrWekNum == 2) {// 日
				String fromDay = dateId + "";
				String endDay = getEndDate(fromDay, mothOrWekNum);
				businessApprovalBill.setChooseMonthOrWeek(mothOrWekNum);
				businessApprovalBill.setFromDate(fromDay);
				businessApprovalBill.setEndDate(endDay);
			} else {// 月
				String rePaymentMonth = dateId + "";
				String year = (String) rePaymentMonth.substring(0, 4);
				String month = (String) rePaymentMonth.substring(4, 6);
				String fromMonth = year + "" + month + "" + "01";
				String endMonth = "";
				//判断二月多少天，闰年就是29 返回true
				boolean isLeapyear = isLeapyear(Integer.parseInt(year));
				if("02".equals(month)){
					if(isLeapyear){
						endMonth = year + "" + month + "" + "29";
					}else {
						endMonth = year + "" + month + "" + "28";
					}
				}else if("04".equals(month) || "06".equals(month) || "09".equals(month) || "11".equals(month) ){
					endMonth = year + "" + month + "" + "30";
				}else{
					endMonth = year + "" + month + "" + "31";
				}
				businessApprovalBill.setFromDate(fromMonth);
				businessApprovalBill.setEndDate(endMonth);
				businessApprovalBill.setChooseMonthOrWeek(mothOrWekNum);
			}
			businessApprovalBill.setUserIds(getUserIds(request));
			
			list = reportService.queryBusinessApprovalBillCount(businessApprovalBill);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("YWSPTZMXTJ");

			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"业务审批台账明细导出导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 业务汇总
	 *
	 * @author:liangyanjun
	 * @time:2016年3月7日下午3:13:45
	 * @param businessSummaryReport
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/businessSummaryReportList")
	@ResponseBody
	public void businessSummaryReportList(
			BusinessSummaryReport businessSummaryReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (businessSummaryReport == null)
			businessSummaryReport = new BusinessSummaryReport();
		Map<String, Object> map = new HashMap<String, Object>();
		List<BusinessSummaryReport> businessSummarytList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		businessSummaryReport.setUserIds(getUserIds(request));
		try {
			// 查询事项列表
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			businessSummarytList = service
					.queryBusinessSummary(businessSummaryReport);
			total = service.getBusinessSummaryTotal(businessSummaryReport);
			// logger.info("业务汇总查询列表查询成功：total：" + total +
			// ",differWarnReportList:" + businessSummarytList);
		} catch (Exception e) {
			logger.error("获取业务汇总列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + businessSummaryReport);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", businessSummarytList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 撤单统计列表
	 * 
	 * @param chechanReport
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年6月21日 下午2:52:47
	 */
	@RequestMapping(value = "/chechanReportList")
	@ResponseBody
	public void chechanReportList(ChechanReport chechanReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (chechanReport == null)
			chechanReport = new ChechanReport();
		Map<String, Object> map = new HashMap<String, Object>();
		List<ChechanReport> chechanList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		chechanReport.setUserIds(getUserIds(request));
		try {
			// 查询事项列表
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			chechanList = service.queryChechan(chechanReport);
			total = service.getChechanTotal(chechanReport);
			// logger.info("撤单汇总查询列表查询成功：total：" + total + ",chechanList:" +
			// chechanList);
		} catch (Exception e) {
			logger.error("获取撤单汇总列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + chechanReport);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", chechanList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 业务汇总报表导出
	 *
	 * @author:liangyanjun
	 * @time:2016年3月8日下午8:14:02
	 * @param projectIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "businessSummaryExcelExportList")
	@ResponseBody
	public void businessSummaryExcelExportList(BusinessSummaryReport query,
			HttpServletRequest request, HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		try {
			List<BusinessSummaryReport> list = reportService.queryBusinessSummary(query);
			if (list != null && list.size() > 0) {
				for (BusinessSummaryReport businessSummaryReport : list) {
					String businessSourceName = businessSummaryReport
							.getBusinessSourceName();
					String businessSourceStr = businessSummaryReport
							.getBusinessSourceStr();
					if (!StringUtil.isBlank(businessSourceStr)) {
						businessSourceName = businessSourceName + "--"
								+ businessSourceStr;
					}
					businessSummaryReport
							.setBusinessSourceName(businessSourceName);
					int innerOrOut = businessSummaryReport.getInnerOrOut();
					businessSummaryReport
							.setInnerOrOutName(Constants.INNER_OR_OUT_MAP
									.get(innerOrOut + ""));
					int foreclosureStatus = businessSummaryReport
							.getForeclosureStatus();
					businessSummaryReport
							.setForeclosureStatusName(Constants.FORECLOSURE_STATUS_MAP
									.get(foreclosureStatus + ""));
					double overdueFee = businessSummaryReport.getOverdueFee();
					double extensionFee = businessSummaryReport
							.getExtensionFee();
					double poundage = businessSummaryReport.getPoundage();
					businessSummaryReport.setFeeTotal(overdueFee + extensionFee
							+ poundage);
				}
			}
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("YWHZBB");

			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"业务汇总报表导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 撤单汇总报表导出
	 *
	 * @author:liangyanjun
	 * @time:2016年3月8日下午8:14:02
	 * @param projectIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "chechanExcelExportList")
	@ResponseBody
	public void chechanExcelExportList(ChechanReport query,
			HttpServletRequest request, HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		try {
			List<ChechanReport> list = reportService.queryChechan(query);
			if (list != null && list.size() > 0) {
				for (ChechanReport chechanReport : list) {
					String businessSourceName = chechanReport
							.getBusinessSourceName();
					String businessSourceStr = chechanReport
							.getBusinessSourceStr();
					if (!StringUtil.isBlank(businessSourceStr)) {
						businessSourceName = businessSourceName + "--"
								+ businessSourceStr;
					}
					chechanReport.setBusinessSourceName(businessSourceName);
					int innerOrOut = chechanReport.getInnerOrOut();
					chechanReport.setInnerOrOutName(Constants.INNER_OR_OUT_MAP
							.get(innerOrOut + ""));
					chechanReport.setPaymentType(Constants.LOAN_WAY_MAP
							.get(chechanReport.getPaymentType()));
					int foreclosureStatus = chechanReport
							.getForeclosureStatus();
					chechanReport
							.setForeclosureStatusName(Constants.FORECLOSURE_STATUS_MAP
									.get(foreclosureStatus + ""));
				}
			}
			map.put("bean", list);
			TemplateFile template = templateFileService
					.getTemplateFile("CHECHAN");

			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"撤单报表导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/reportList")
	@ResponseBody
	public void reportList(RefundFeeIndexDTO refundFeeIndexDTO,
			HttpServletRequest request, HttpServletResponse response) {
		if (refundFeeIndexDTO == null)
			refundFeeIndexDTO = new RefundFeeIndexDTO();
		Map<String, Object> map = new HashMap<String, Object>();
		List<RefundFeeIndexDTO> refundFeeIndexList = null;
		int total = 0;
		// 设置数据权限--用户编号list
		refundFeeIndexDTO.setUserIds(getUserIds(request));
		try {
			// 查询退费列表
			RefundFeeService.Client service = (RefundFeeService.Client) getService(
					BusinessModule.MODUEL_INLOAN, "RefundFeeService");
			refundFeeIndexList = service
					.findAllRefundFeeIndex(refundFeeIndexDTO);
			total = service.getRefundFeeIndexTotal(refundFeeIndexDTO);
			logger.info("退费查询成功：total：" + total + ",refundFeeIndexList:"
					+ refundFeeIndexList);
		} catch (Exception e) {
			logger.error("获取退费列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + refundFeeIndexDTO);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", refundFeeIndexList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 退手续费报表导出
	 *
	 * @author:liangyanjun
	 * @time:2016年3月8日下午8:14:02
	 * @param projectIds
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "refundFeeExcelExportList")
	@ResponseBody
	public void refundFeeExcelExportList(String projectIds,
			String templateName, HttpServletRequest request,
			HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		RefundFeeService.Client service = (RefundFeeService.Client) getService(
				BusinessModule.MODUEL_INLOAN, "RefundFeeService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<RefundFeeIndexDTO> list = service
					.queryRefundFeeByIds(projectIds);
			if (list != null && list.size() > 0) {
				for (RefundFeeIndexDTO refundFeeIndexDTO : list) {

					int innerOrOut = refundFeeIndexDTO.getInnerOrOut();
					int businessSource = refundFeeIndexDTO.getBusinessSource();
					int bizApplyHandleStatus = refundFeeIndexDTO
							.getBizApplyHandleStatus();
					int backFeeApplyHandleStatus = refundFeeIndexDTO
							.getBackFeeApplyHandleStatus();
					refundFeeIndexDTO
							.setInnerOrOutName(Constants.INNER_OR_OUT_MAP
									.get(innerOrOut + ""));
					refundFeeIndexDTO
							.setBusinessSourceName((Constants.BUSINESS_SOURCE_MAP
									.get(new Long(businessSource))) == null ? "其它"
									: Constants.BUSINESS_SOURCE_MAP
											.get(new Long(businessSource)));
					refundFeeIndexDTO
							.setBizApplyHandleStatusName(Constants.BIZ_APPLY_HANDLE_STATUS_MAP
									.get(new Long(bizApplyHandleStatus)));
					refundFeeIndexDTO
							.setBackFeeApplyHandleStatusName(Constants.BACK_FEE_APPLY_HANDLE_STATUS_MAP
									.get(new Long(backFeeApplyHandleStatus)));
				}
			}
			map.put("bean", list);
			TemplateFile template = templateFileService
					.getTemplateFile(templateName);

			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"退费报表导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 业绩一览表
	 * 
	 * @param trackRecordReportList
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年6月21日 下午2:52:47
	 */
	@RequestMapping(value = "/trackRecordReportList")
	@ResponseBody
	public void trackRecordReportList(TrackRecordReport trackRecordReport,
			HttpServletRequest request, HttpServletResponse response) {
		if (trackRecordReport == null) {
			trackRecordReport = new TrackRecordReport();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<TrackRecordReport> trackRecordList = new ArrayList<TrackRecordReport>();
		int total = 0;
		// 设置数据权限--用户编号list
		trackRecordReport.setUserIds(getUserIds(request));
		try {
			// 查询业绩一览表
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			trackRecordList = service.queryTrackRecordReport(trackRecordReport);
			total = service.queryTrackRecordReportTotal(trackRecordReport);
			// logger.info("业绩一览表查询列表查询成功：total：" + total + ",trackRecordList:"
			// + trackRecordList);
		} catch (Exception e) {
			logger.error("获取业绩一览表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + trackRecordReport);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", trackRecordList);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * 业绩一览导出
	 * 
	 * @param projectIds
	 * @param request
	 * @param response
	 * @author: baogang
	 * @date: 2016年6月22日 上午9:24:31
	 */
	@RequestMapping(value = "/trackRecordExcelExportList")
	@ResponseBody
	public void trackRecordExcelExportList(String projectIds,
			HttpServletRequest request, HttpServletResponse response) {
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		ReportService.Client reportService = (Client) getService(
				BusinessModule.MODUEL_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			List<TrackRecordReport> list = reportService
					.queryTrackRecordReportByIds(projectIds);
			if (list != null && list.size() > 0) {
				for (TrackRecordReport trackRecord : list) {
					int innerOrOut = trackRecord.getInnerOrOut();
					trackRecord.setInnerOrOutName(Constants.INNER_OR_OUT_MAP
							.get(innerOrOut + ""));
				}
			}
			map.put("bean", list);
			TemplateFile template = templateFileService
					.getTemplateFile("TRACKRECORD");

			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"业绩一览报表导出"
								+ "."
								+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 跳转拒单列表
	 * @return
	 */
	@RequestMapping(value = "/toRefuseProject")
	public String toRefuseProject(){
		return financeHandle + "refuse_project_index";
	}
	
	/**
	 * 分页查询拒单列表
	 * @param query
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryRefuseProject")
	@ResponseBody
	public void queryRefuseProject(RefuseProjectReport query,
			HttpServletRequest request, HttpServletResponse response) {
		if (query == null)
			query = new RefuseProjectReport();
		Map<String, Object> map = new HashMap<String, Object>();
		List<RefuseProjectReport> list = null;
		int total = 0;
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		try {
			// 查询事项列表
			ReportService.Client service = (Client) getService(
					BusinessModule.MODUEL_REPORT, serviceName);
			list = service.queryResuseProjectByPage(query);
			total = service.queryResuseProjectCount(query);
		} catch (Exception e) {
			logger.error("获取拒单列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + list);
			e.printStackTrace();
		}
		// 输出
		map.put("rows", list);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 * 获取征信报告费用统计列表
	 */
	@RequestMapping(value = "/creditReportFeeList")
	@ResponseBody
	public void creditReportFeeList(CusCreditReportHis query,HttpServletRequest request, HttpServletResponse response) {
		if (query == null){
			query = new CusCreditReportHis();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		BaseClientFactory clientFactory = null;
		List<CusCreditReportHis> list = null;
		int total = 0;
		// 设置数据权限--用户编号list
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusCreditReportHisService");
			CusCreditReportHisService.Client client =(CusCreditReportHisService.Client) clientFactory.getClient();
			
			query.setUserIds(getUserIds(request));
			if(!StringUtil.isBlank(query.getBeginCreateTime())){
				query.setBeginCreateTime(query.getBeginCreateTime() + " 00:00:00");
			}
			if(!StringUtil.isBlank(query.getEndCreateTime())){
				query.setEndCreateTime(query.getEndCreateTime() + " 23:59:59");
			}
			list = client.selectCreditReportFeeList(query);
			total = client.selectTotal(query);
		} catch (Exception e) {
			logger.error("获取征信报告费用统计列表：" + ExceptionUtil.getExceptionMessage(e)+ ",入参：" + query);
		}finally {
			destroyFactory(clientFactory);
		}
		// 输出
		map.put("rows", list);
		map.put("total", total);
		outputJson(map, response);
	}
	
	
	/**
	 * 获取征信报告费用统计总和
	 */
	@RequestMapping(value = "/creditReportFeeSum")
	@ResponseBody
	public void creditReportFeeSum(CusCreditReportHis query,HttpServletRequest request, HttpServletResponse response) {
		if (query == null){
			query = new CusCreditReportHis();
		}
		Map<String,Object> body =new HashMap<String,Object>();
		BaseClientFactory clientFactory = null;
		CusCreditReportHis feeSum = null;
		int total = 0;
		// 设置数据权限--用户编号list
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusCreditReportHisService");
			CusCreditReportHisService.Client client =(CusCreditReportHisService.Client) clientFactory.getClient();
			
			query.setUserIds(getUserIds(request));
			if(!StringUtil.isBlank(query.getBeginCreateTime())){
				query.setBeginCreateTime(query.getBeginCreateTime() + " 00:00:00");
			}
			if(!StringUtil.isBlank(query.getEndCreateTime())){
				query.setEndCreateTime(query.getEndCreateTime() + " 23:59:59");
			}
			feeSum = client.selectCreditReportFeeSum(query);
			
			body.put("feeSum", feeSum);
			fillReturnJson(response, true, "操作成功!",body);
			return ;
			
		} catch (Exception e) {
			logger.error("获取征信报告费用统计总和：" + ExceptionUtil.getExceptionMessage(e)+ ",入参：" + query);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactory);
		}
 
	}
}
