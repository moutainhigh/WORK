package com.xlkfinance.bms.web.controller.report;

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

import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.otherReport.ForeclosureCapDetailsReport;
import com.xlkfinance.bms.rpc.otherReport.ForeclosureOrgDetailsReport;
import com.xlkfinance.bms.rpc.otherReport.OtherReportService;
import com.xlkfinance.bms.rpc.otherReport.OtherReportService.Client;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author：Jony <br>
 * ★☆ @time：2016年3月1日下午3:47:00 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */

@Controller
@RequestMapping("/otherReportController")
public class OtherReportController extends BaseController {
	private static final String financeHandle = "otherReport/";
	private Logger logger = LoggerFactory.getLogger(OtherReportController.class);
	private final String serviceName = "OtherReportService";

	/**
	 * 资金方新增，结清，在途等笔数明细
	 * @param projectId
	 * @param model
	 * @return
	 * @author: Jony
	 * @date: 2016年5月17日 上午10:24:38
	 */
	@RequestMapping(value = "getCapDetailsView")
	public String toCapDetailsView(ModelMap model,
			HttpServletRequest request, Integer type,
			Integer mothOrWekNum, String reMonth,String orgName) {
		//1=新增 2=结清 3=在途
		if (type == 1) {
			model.put("orgName", orgName);
			model.put("reMonth", reMonth);
			model.put("mothOrWekNum", mothOrWekNum);
			return financeHandle + "cap_new_count_index";
		} else if(type == 2){
			model.put("reMonth", reMonth);
			model.put("orgName", orgName);
			model.put("mothOrWekNum", mothOrWekNum);
			return financeHandle + "cap_square_count_index";
		}else{
			model.put("reMonth", reMonth);
			model.put("orgName", orgName);
			model.put("mothOrWekNum", mothOrWekNum);
			return financeHandle + "cap_ing_count_index";
		}
	}
	
	/**
	 * 合作机构新增,结清，在途等笔数明细
	 * @param projectId
	 * @param model
	 * @return
	 * @author: Jony
	 * @date: 2016年5月17日 上午10:24:38
	 */
	@RequestMapping(value = "getOrgDetailsView")
	public String toOrgDetailsView(ModelMap model,
			HttpServletRequest request, Integer type,
			Integer mothOrWekNum, String reMonth,String orgName,int includeWt) {
		//1=新增 2=结清 3=在途
		if (type == 1) {
			model.put("orgName", orgName);
			model.put("reMonth", reMonth);
			model.put("mothOrWekNum", mothOrWekNum);
			model.put("includeWt", includeWt);
			return financeHandle + "org_new_count_index";
		} else if(type == 2){
			model.put("reMonth", reMonth);
			model.put("orgName", orgName);
			model.put("mothOrWekNum", mothOrWekNum);
			model.put("includeWt", includeWt);
			return financeHandle + "org_square_count_index";
		}else{
			model.put("reMonth", reMonth);
			model.put("orgName", orgName);
			model.put("mothOrWekNum", mothOrWekNum);
			model.put("includeWt", includeWt);
			return financeHandle + "org_ing_count_index";
		}
	}
	
	/**
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷资金方新增明细
	 */
	@RequestMapping(value = "/capNewCountList")
	@ResponseBody
	public void capNewCountList(Integer mothOrWekNum, String reMonth,
			ForeclosureCapDetailsReport query, HttpServletRequest request,String orgName,
			HttpServletResponse response) {
		if (query == null)query = new ForeclosureCapDetailsReport();
		Map<String, Object> map = new HashMap<String, Object>();
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		try {
			List<ForeclosureCapDetailsReport> capNewCountList = null;
			int total = 0;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 1;
			query.setReMonth(reMonth);
			query.setChooseMonthOrWeek(mothOrWekNum);
			capNewCountList = getCapDetailsList(capType,query, request, service);
			total = service.queryForeclosureCapNewDetailsTotal(query);
			// 输出
			map.put("rows", capNewCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + query);
			e.printStackTrace();
		}
	}
	
	/**
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷资金方新增明细导出
	 */
	@RequestMapping(value = "/capNewCountExportList")
	@ResponseBody
	public void capNewCountExportList(ForeclosureCapDetailsReport query,
			HttpServletRequest request, HttpServletResponse response) {
		if (query == null) query = new ForeclosureCapDetailsReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		List<ForeclosureCapDetailsReport> list = new ArrayList<ForeclosureCapDetailsReport>();
		try {
			int capType = 1;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			list = getCapDetailsList(capType,query, request, service);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("ZJFXQDCMB");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map,path,"资金方详情导出模板导出."+ template.getFileUrl()
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
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷资金方结清明细
	 */
	@RequestMapping(value = "/capSquareCountList")
	@ResponseBody
	public void capSquareCountList(Integer mothOrWekNum, String reMonth,
			ForeclosureCapDetailsReport query, HttpServletRequest request,String orgName,
			HttpServletResponse response) {
		if (query == null)query = new ForeclosureCapDetailsReport();
		Map<String, Object> map = new HashMap<String, Object>();
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		try {
			List<ForeclosureCapDetailsReport> capSquareCountList = null;
			int total = 0;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 2;
			query.setReMonth(reMonth);
			query.setChooseMonthOrWeek(mothOrWekNum);
			capSquareCountList = getCapDetailsList(capType,query, request, service);
			total = service.queryForeclosureCapSquareDetailsTotal(query);
			// 输出
			map.put("rows", capSquareCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + query);
			e.printStackTrace();
		}
	}
	
	/**
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷资金方在途明细
	 */
	@RequestMapping(value = "/capIngCountList")
	@ResponseBody
	public void capIngCountList(Integer mothOrWekNum, String reMonth,
			ForeclosureCapDetailsReport query, HttpServletRequest request,String orgName,
			HttpServletResponse response) {
		if (query == null)query = new ForeclosureCapDetailsReport();
		Map<String, Object> map = new HashMap<String, Object>();
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		try {
			List<ForeclosureCapDetailsReport> capNewCountList = null;
			int total = 0;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 3;
			query.setReMonth(reMonth);
			query.setChooseMonthOrWeek(mothOrWekNum);
			capNewCountList = getCapDetailsList(capType,query, request, service);
			total = service.queryForeclosureCapIngDetailsTotal(query);
			// 输出
			map.put("rows", capNewCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + query);
			e.printStackTrace();
		}
	}
	
	/**
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷资金方在途明细导出
	 */
	@RequestMapping(value = "/capIngCountExportList")
	@ResponseBody
	public void capIngCountExportList(ForeclosureCapDetailsReport query,
			HttpServletRequest request, HttpServletResponse response) {
		if (query == null) query = new ForeclosureCapDetailsReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		List<ForeclosureCapDetailsReport> list = new ArrayList<ForeclosureCapDetailsReport>();
		try {
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 3;
			list = getCapDetailsList(capType,query, request, service);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("ZJFXQDCMB");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map,path,"资金方详情导出模板导出."+ template.getFileUrl()
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
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷资金方结清明细导出
	 */
	@RequestMapping(value = "/capSquareCountExportList")
	@ResponseBody
	public void capSquareCountExportList(ForeclosureCapDetailsReport query,
			HttpServletRequest request, HttpServletResponse response) {
		if (query == null) query = new ForeclosureCapDetailsReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		List<ForeclosureCapDetailsReport> list = new ArrayList<ForeclosureCapDetailsReport>();
		try {
			int capType = 2;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			list = getCapDetailsList(capType,query, request, service);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("ZJFXQDCMB");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map,path,"资金方详情导出模板导出."+ template.getFileUrl()
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
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param requestorgSquareCountList
	 * @param response
	 *赎楼贷合作机构新增明细
	 */
	@RequestMapping(value = "/orgNewCountList")
	@ResponseBody
	public void orgNewCountList(Integer mothOrWekNum, String reMonth,
			ForeclosureOrgDetailsReport query, HttpServletRequest request,String orgName,
			HttpServletResponse response) {
		if (query == null)query = new ForeclosureOrgDetailsReport();
		Map<String, Object> map = new HashMap<String, Object>();
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		try {
			List<ForeclosureOrgDetailsReport> orgNewCountList = null;
			int total = 0;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 1;
			query.setReMonth(reMonth);
			query.setChooseMonthOrWeek(mothOrWekNum);
			orgNewCountList = getOrgDetailsList(capType,query, request, service);
			total = service.queryForeclosureOrgNewDetailsTotal(query);
			// 输出
			map.put("rows", orgNewCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + query);
			e.printStackTrace();
		}
	}
	
	/**
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷合作机构新增明细导出
	 */

	@RequestMapping(value = "/orgNewCountExportList")
	@ResponseBody
	public void orgNewCountExportList(ForeclosureOrgDetailsReport query,
			HttpServletRequest request, HttpServletResponse response) {
		if (query == null) query = new ForeclosureOrgDetailsReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		List<ForeclosureOrgDetailsReport> list = new ArrayList<ForeclosureOrgDetailsReport>();
		try {
			int capType = 1;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			list = getOrgDetailsList(capType,query, request, service);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("HZJGYWBSXQ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map,path,"合作机构新增详情导出模板导出."+ template.getFileUrl()
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
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷合作机构结清明细
	 */

	@RequestMapping(value = "/orgSquareCountList")
	@ResponseBody
	public void orgSquareCountList(Integer mothOrWekNum, String reMonth,
			ForeclosureOrgDetailsReport query, HttpServletRequest request,String orgName,
			HttpServletResponse response) {
		if (query == null)query = new ForeclosureOrgDetailsReport();
		Map<String, Object> map = new HashMap<String, Object>();
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		try {
			List<ForeclosureOrgDetailsReport> orgNewCountList = null;
			int total = 0;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 2;
			query.setReMonth(reMonth);
			query.setChooseMonthOrWeek(mothOrWekNum);
			orgNewCountList = getOrgDetailsList(capType,query, request, service);
			total = service.queryForeclosureOrgSquareDetailsTotal(query);
			// 输出
			map.put("rows", orgNewCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + query);
			e.printStackTrace();
		}
	}
	
	/**
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷合作机构结清明细导出
	 */

	@RequestMapping(value = "/orgSquareCountExportList")
	@ResponseBody
	public void orgSquareCountExportList(ForeclosureOrgDetailsReport query,
			HttpServletRequest request, HttpServletResponse response) {
		if (query == null) query = new ForeclosureOrgDetailsReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		List<ForeclosureOrgDetailsReport> list = new ArrayList<ForeclosureOrgDetailsReport>();
		try {
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 2;
			list = getOrgDetailsList(capType,query, request, service);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("HZJGYWBSXQ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map,path,"合作机构结清详情导出模板导出."+ template.getFileUrl()
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
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param requestorgSquareCountList
	 * @param response
	 *赎楼贷合作机构在途明细
	 */

	@RequestMapping(value = "/orgIngCountList")
	@ResponseBody
	public void orgIngCountList(Integer mothOrWekNum, String reMonth,
			ForeclosureOrgDetailsReport query, HttpServletRequest request,String orgName,
			HttpServletResponse response) {
		if (query == null)query = new ForeclosureOrgDetailsReport();
		Map<String, Object> map = new HashMap<String, Object>();
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		try {
			List<ForeclosureOrgDetailsReport> orgNewCountList = null;
			int total = 0;
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 3;
			query.setReMonth(reMonth);
			query.setChooseMonthOrWeek(mothOrWekNum);
			orgNewCountList = getOrgDetailsList(capType,query, request, service);
			total = service.queryForeclosureOrgIngDetailsTotal(query);
			// 输出
			map.put("rows", orgNewCountList);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("赎楼贷新增项列表失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + query);
			e.printStackTrace();
		}
	}
	/**
	 * @author:Jony
	 * @time:2016年11月1日下午8:36:32
	 * @param ForeclosureReport
	 * @param request
	 * @param response
	 *赎楼贷合作机构在途明细导出
	 */
	@RequestMapping(value = "/orgIngCountExportList")
	@ResponseBody
	public void orgIngCountExportList(ForeclosureOrgDetailsReport query,
			HttpServletRequest request, HttpServletResponse response) {
		if (query == null) query = new ForeclosureOrgDetailsReport();
		TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
				BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		OtherReportService.Client service = (Client) getService(BusinessModule.MODUEL_OTHER_REPORT, serviceName);
		Map<String, Object> map = new HashMap<String, Object>();
		// 设置数据权限--用户编号list
		query.setUserIds(getUserIds(request));
		List<ForeclosureOrgDetailsReport> list = new ArrayList<ForeclosureOrgDetailsReport>();
		try {
			//1（capType）=新增，2（capType）=结清，3（capType）=在途 
			int capType = 3;
			list = getOrgDetailsList(capType,query, request, service);
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("HZJGYWBSXQ");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map,path,"合作机构结清详情导出模板导出."+ template.getFileUrl()
										.substring(
												template.getFileUrl()
														.lastIndexOf(".") + 1),
						response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//根据当前日计算N天后日期
	private String getEndDate(String fromDay, int mothOrWekNum) {
		String year = (String) fromDay.substring(0, 4);
		String month = (String) fromDay.substring(4, 6);
		String day = (String) fromDay.substring(6);
		String fromDay1 = year + "-" + month + "-" + day;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		ParsePosition pos = new ParsePosition(0);
		formatter.parse(fromDay1, pos);

		String sLateDate = "";
		Calendar calendar = Calendar.getInstance();
		String time = fromDay1;
		int iDays = 6;
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
	
	//判断二月多少天
		private static boolean isLeapyear(int year) {
	        if ((year % 4 == 0 && year % 100 != 0) || year % 400 == 0) {
	            return true;
	        } else {
	            return false;
	        }
	    }
		/*
		 * 资金方业务笔数查询**/	
		private List<ForeclosureCapDetailsReport> getCapDetailsList(int capType,
				ForeclosureCapDetailsReport query, HttpServletRequest request,
				Client service) throws TException {
			List<ForeclosureCapDetailsReport> capNewCountList = null;
			List<ForeclosureCapDetailsReport> capSquareList = null;
			String fromDay = "";
			String endDay = "";
			int mothOrWekNum = query.getChooseMonthOrWeek();
			String reMonth = query.getReMonth();
			if (mothOrWekNum == 1) {//周
				fromDay = reMonth;
				query.setFromDay(fromDay);
				endDay = getEndDate(fromDay,mothOrWekNum);
				query.setEndDay(endDay);
				query.setChooseMonthOrWeek(mothOrWekNum);
			} else if (mothOrWekNum == 2) {// 日
				fromDay = reMonth;
				query.setChooseMonthOrWeek(mothOrWekNum);
				query.setFromDay(fromDay);
				query.setEndDay(fromDay);
			} else{// 月
				String year = (String) reMonth.substring(0, 4);
				String month = (String) reMonth.substring(4, 6);
				fromDay = year + "" + month + "" + "01";
				boolean isLeapyear = isLeapyear(Integer.parseInt(year));
				if("02".equals(month)){
					if(isLeapyear){
						endDay = year + "" + month + "" + "29";
					}else {
						endDay = year + "" + month + "" + "28";
					}
				}else if("04".equals(month) || "06".equals(month) || "09".equals(month) || "11".equals(month) ){
					endDay = year + "" + month + "" + "30";
				}else{
					endDay = year + "" + month + "" + "31";
				}
				query.setFromDay(fromDay);
				query.setEndDay(endDay);
				query.setChooseMonthOrWeek(mothOrWekNum);
			}	
			//1=新增列表，2=结清列表，3=在途列表
			if(capType == 1){
				capNewCountList = service.queryForeclosureCapNewDetails(query);
				//仅仅为导出做设计
				formartList(capNewCountList);
				return capNewCountList;
			}else if(capType == 2){
				capSquareList = service.queryForeclosureCapSquareDetails(query);
				formartList(capSquareList);
				return capSquareList;
			}else{
			   capSquareList = service.queryForeclosureCapIngDetails(query);
			   formartList(capSquareList);
			   return capSquareList;
			}
		}
		
		/*
		 * 合作机构业务笔数查询**/	
		private List<ForeclosureOrgDetailsReport> getOrgDetailsList(int capType,
				ForeclosureOrgDetailsReport query, HttpServletRequest request,
				Client service) throws TException {
			List<ForeclosureOrgDetailsReport> orgNewCountList = null;
			List<ForeclosureOrgDetailsReport> orgSquareList = null;
			String fromDay = "";
			String endDay = "";
			int mothOrWekNum = query.getChooseMonthOrWeek();
			int includeWt = query.getIncludeWt();
			String reMonth = query.getReMonth();
			if (mothOrWekNum == 1) {//周20161107~20161113
				fromDay = reMonth.substring(0, 8);
				query.setFromDay(fromDay);
				endDay = reMonth.substring(9);
				query.setEndDay(endDay);
				query.setChooseMonthOrWeek(mothOrWekNum);
				query.setIncludeWt(includeWt);
			} else if (mothOrWekNum == 2) {// 日
				fromDay = reMonth;
				query.setChooseMonthOrWeek(mothOrWekNum);
				query.setFromDay(fromDay);
				query.setEndDay(fromDay);
				query.setIncludeWt(includeWt);
			} else{// 月
				String year = (String) reMonth.substring(0, 4);
				String month = (String) reMonth.substring(4, 6);
				fromDay = year + "" + month + "" + "01";
				boolean isLeapyear = isLeapyear(Integer.parseInt(year));
				if("02".equals(month)){
					if(isLeapyear){
						endDay = year + "" + month + "" + "29";
					}else {
						endDay = year + "" + month + "" + "28";
					}
				}else if("04".equals(month) || "06".equals(month) || "09".equals(month) || "11".equals(month) ){
					endDay = year + "" + month + "" + "30";
				}else{
					endDay = year + "" + month + "" + "31";
				}
				query.setFromDay(fromDay);
				query.setEndDay(endDay);
				query.setChooseMonthOrWeek(mothOrWekNum);
				query.setIncludeWt(includeWt);
			}	
			//1=新增列表，2=结清列表，3=在途 列表
			if(capType == 1){
				orgNewCountList = service.queryForeclosureOrgNewDetails(query);
				return orgNewCountList;
			}else if(capType == 2){
				orgSquareList = service.queryForeclosureOrgSquareDetails(query);
				return orgSquareList;
			}else if(capType == 3){
				orgSquareList = service.queryForeclosureOrgIngDetails(query);
				return orgSquareList;
			}else{
				return null;
			}
		}
		/*
		 * 为资金方导出设计**/	
		private List<ForeclosureCapDetailsReport> formartList(List<ForeclosureCapDetailsReport> capCountList){
			if(capCountList != null && capCountList.size() > 0){
				for(ForeclosureCapDetailsReport index :capCountList){
					//审批状态:审核中=1,通过=2,未通过=3
					if(index.getApprovalStatus() == 1){
						index.setApprovalStatusName("审核中");
					}else if(index.getApprovalStatus() == 2){
						index.setApprovalStatusName("通过");
					}else{
						index.setApprovalStatusName("未通过");
					}
					//确认要款状态:1：未发送  2：审核中 3：审核通过 4 审核不通过
					if(index.getConfirmLoanStatus() == 1){
						index.setConfirmLoanStatusName("未发送");
					}else if (index.getConfirmLoanStatus() == 2){
						index.setConfirmLoanStatusName("审核中");
					}else if (index.getConfirmLoanStatus() == 3){
						index.setConfirmLoanStatusName("审核通过");
					}else{
						index.setConfirmLoanStatusName("审核不通过");
					}
					//放款状态:1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败
					if(index.getLoanStatus() == 1){
						index.setLoanStatusName("未申请");
					}else if (index.getLoanStatus() == 2){
						index.setLoanStatusName("申请中");
					}else if (index.getLoanStatus() == 3){
						index.setLoanStatusName("放款中");
					}else if(index.getLoanStatus() == 4){
						index.setLoanStatusName("放款成功");
					}else{
						index.setLoanStatusName("放款失败");
					}
				}
			}
			return capCountList;
		}
}