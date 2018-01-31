package com.xlkfinance.bms.web.controller.foreafterloan;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReport;
import com.xlkfinance.bms.rpc.foreafterloan.AfterExceptionReportService;
import com.xlkfinance.bms.rpc.foreafterloan.ExceptionMonitorIndex;
import com.xlkfinance.bms.rpc.foreafterloan.ForeAfterLoanService;
import com.xlkfinance.bms.rpc.foreafterloan.LegalIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitApplyReportIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitExceptionIndex;
import com.xlkfinance.bms.rpc.foreafterloan.TransitMonitorIndex;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleFlowDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017年1月11日下午2:28:47 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 赎楼贷后模块<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/foreAfterLoanController")
public class ForeAfterLoanController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(ForeAfterLoanController.class);
	/**
	 * 跳转到在途业务监控列表
	 * 
	 * @author:liangyanjun
	 * @time:2017年1月12日下午2:19:21
	 * @return
	 */
	@RequestMapping(value = "toTransitMonitorIndex")
	public String toTransitMonitorIndex() {
		return "foreafterloan/transit_monitor_index";
	}

	/**
	 * 跳转到异常业务列表
	 * 
	 * @author:Jony He
	 * @time:2017年1月12日下午2:19:21
	 * @return
	 */
	@RequestMapping(value = "toTransitExceptionIndex")
	public String toTransitExceptionIndex() {
		return "foreafterloan/transit_exception_index";
	}

	/**
	 * 跳转到异常业务监控
	 * 
	 * @author:Jony He
	 * @time:2017年1月12日下午2:19:21
	 * @return
	 */
	@RequestMapping(value = "toExceptionManageIndex")
	public String toExceptionManageIndex() {
		return "foreafterloan/exception_manage_index";
	}

	/**
	 * 新增or修改（分页查询）
	 *
	 * @author:Jony
	 * @time:2017年1月12日下午2:17:43
	 * @param query
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/addOrUpdateReport")
	public String addOrUpdateReport(ModelMap model,@RequestParam(value = "projectId", required = true) Integer projectId,
			@RequestParam(value = "applyStatus", required = true) Integer applyStatus)
			throws TException, ThriftException {
		model.put("projectId", projectId);
		if (applyStatus == 2) {
			BaseClientFactory factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN,"ForeAfterLoanService");
			ForeAfterLoanService.Client c = (ForeAfterLoanService.Client) factory.getClient();
			// 根据Id查询申请报告
			TransitApplyReportIndex applyReport = new TransitApplyReportIndex();
			applyReport = c.getById(projectId);
			int reportId = projectId;
			// 根据Id查询诉讼列表
			List<LegalIndex> legalList = c.getLegalListByReport(reportId);
			model.put("applyReport", applyReport);
			model.put("legalList", legalList);
			return "foreafterloan/update_report";
		} else {
			return "foreafterloan/add_report";
		}
	}

	/**
	 * 跳转到增加异常业务页面
	 * 
	 * @author:Jony He
	 * @time:2017年1月12日下午2:19:21
	 * @return
	 */
	@RequestMapping(value = "addExceptionTransit")
	public String addExceptionTransit(
			ModelMap model,
			@RequestParam(value = "projectId", required = true) Integer projectId) {
		model.put("projectId", projectId);
		return "foreafterloan/add_exception_index";
	}

	/**
	 * 跳转到查看（打印）
	 * 
	 * @author:Jony He
	 * @time:2017年1月12日下午2:19:21
	 * @return
	 */
	@RequestMapping(value = "checkView")
	public String checkView(ModelMap model, Integer projectId,
			String businessSourceStr) throws Exception {
		model.put("projectId", projectId);
		BaseClientFactory clientFactory = null;
		SysUser loginUser = null;
		SysUserService.Client client2;
		BaseClientFactory clientFactory2 = getFactory(
				BusinessModule.MODUEL_SYSTEM, "SysUserService");
		String loginUserName = "";
		try {
			client2 = (SysUserService.Client) clientFactory2.getClient();
			ShiroUser shiroUser = getShiroUser();
			loginUser = client2.getSysUserByPid(shiroUser.getPid());
			//获取登录者姓名
			loginUserName = loginUser.getRealName();
			model.put("loginUserName", loginUserName);
			clientFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN,
					"ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory
					.getClient();
			Project project = client.getLoanProjectByProjectId(projectId);
			// 项目编号
			model.put("projectNumber", project.getProjectNumber());
			Date date = new Date();
			DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			String reportDate = format.format(date);
			// 当前日期
			model.put("reportDate", reportDate);
			// 合作机构
			model.put("businessSourceStr", businessSourceStr);
	
			BaseClientFactory factory = getFactory(
					BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
			ForeAfterLoanService.Client client1 = (ForeAfterLoanService.Client) factory.getClient();
			TransitApplyReportIndex transitApplyReportIndex = new TransitApplyReportIndex();
			//获取反担保情况，房产状态等
			transitApplyReportIndex  = client1.getById(projectId);
			String houseProperyCondition = transitApplyReportIndex.getHouseProperyCondition();
			String unAssureCondition = transitApplyReportIndex.getUnAssureCondition();
			model.put("houseProperyCondition", houseProperyCondition);
			model.put("unAssureCondition", unAssureCondition);
			String remark = transitApplyReportIndex.getRemark();
			//获取借款天数，借款金额，借款人等信息
			TransitExceptionIndex transitExceptionIndex = new TransitExceptionIndex();
			transitExceptionIndex = client1.getTransitExceptionIndexById(projectId);
			// 根据项目Id 查询存入保证金，单笔上线，启用额度
			 DecimalFormat df = new DecimalFormat("###0.0#");//最多保留几位小数，就用几个#，最少位就用0来确定  
			double assureMoney =  transitExceptionIndex.getAssureMoney();
			double singleUpperLimit = transitExceptionIndex.getSingleUpperLimit();
			String availableLimit = df.format(transitExceptionIndex.getAvailableLimit());
			String chinaName = transitExceptionIndex.getChinaName();
			double DloanMoney = transitExceptionIndex.getLoanMoney();
			int loanDays = transitExceptionIndex.getLoanDays();
			String  productName = transitExceptionIndex.getProductName();
			//我司出款情况
			String updateDate = "";
			if(!StringUtils.isEmpty(transitExceptionIndex.getReceDate())){
				 updateDate = transitExceptionIndex.getReceDate().substring(0,10);
			}
			//赎楼日期
			String payDate = "";
			//2017-2-7 0:0:0. 0
			//2016-10-17 0:0:0. 0
			if(!StringUtils.isEmpty(transitExceptionIndex.getPayDate())){
				 payDate = transitExceptionIndex.getPayDate().substring(0,10);
				 if("0".equals(payDate.substring(payDate.length()-1))){
					 payDate = payDate.substring(0,payDate.length()-1);
					}
			}
			//上资方情况
			String partnerLoanDate = "";
			if(!StringUtils.isEmpty(transitExceptionIndex.getPartnerLoanDate())){
				partnerLoanDate = transitExceptionIndex.getPartnerLoanDate().substring(0, 10);
			}
			//关联企业
			String cpyName = transitExceptionIndex.getCpyName();
			//房产证号
			String housePropertyCard = transitExceptionIndex.getHousePropertyCard();
			//物业名称
			String houseName = transitExceptionIndex.getHouseName();
			//在保笔数和金金额
			int totalCount = transitExceptionIndex.getTotalCount();
			String totalMoney = df.format(transitExceptionIndex.getTotalMoney());
			String loanMoney =  df.format(DloanMoney);
			String certNumber = transitExceptionIndex.getCertNumber();
			model.put("certNumber", certNumber);
			model.put("totalCount", totalCount);
			model.put("totalMoney", totalMoney);
			model.put("chinaName", chinaName);
			model.put("loanMoney", loanMoney);
			model.put("loanDays", loanDays);
			model.put("productName", productName);
			model.put("updateDate", updateDate);
			model.put("payDate", payDate);
			model.put("cpyName", cpyName);
			model.put("partnerLoanDate", partnerLoanDate);
			model.put("housePropertyCard", housePropertyCard);
			model.put("houseName", houseName);
			model.put("assureMoney", assureMoney);
			model.put("singleUpperLimit", singleUpperLimit);
			model.put("availableLimit", availableLimit);
			model.put("remark", remark);
			//查询诉讼表
			List<LegalIndex> legalList = client1.getLegalListByReport(projectId);
			model.put("legalList", legalList);
			//跟进情况
			List<ExceptionMonitorIndex> exceptionMonitorIndexList = new ArrayList<ExceptionMonitorIndex>();
			exceptionMonitorIndexList = client1.getExceptionMonitorIndexById(projectId);
			model.put("exceptionMonitorIndexList", exceptionMonitorIndexList);
			 BaseClientFactory factory3 = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN, "AfterExceptionReportService");
		    AfterExceptionReportService.Client client3 = (AfterExceptionReportService.Client) factory3.getClient();
		    //跟进情况中的时间和说明
			List<AfterExceptionReport> afterExceptionReportList= client3.getByProjectId(projectId);
			model.put("afterExceptionReportList", afterExceptionReportList);
		} catch (TException e) {
			e.printStackTrace();
		}
		return "foreafterloan/printReport";
	}

	/**
	 * 跳转到业务监控查看页面
	 *
	 * @author:liangyanjun
	 * @time:2017年1月17日下午3:33:51
	 * @param model
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "toTransitMonitorShow")
	public String toTransitMonitorShow(ModelMap model, int projectId) {
		try {
			BaseClientFactory userFactory = getFactory(
					BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client userClient = (SysUserService.Client) userFactory
					.getClient();
			BaseClientFactory factory = getFactory(
					BusinessModule.MODUEL_INLOAN, "BizHandleService");
			BizHandleService.Client client = (BizHandleService.Client) factory
					.getClient();
			// 填充监控人
			List<SysUser> users =userClient.getUsersByRoleCode("FORE_AFTER_LOAN_MANAGER"); 
			HashMap<Integer, String> monitorUserMap = new HashMap<>();
			for (SysUser u : users) {
				monitorUserMap.put(u.getPid(), u.getRealName());
			}
			model.put("monitorUserMap", monitorUserMap);
			// .填充办理流程条目数据
			HandleFlowDTO handleFlowDTO = new HandleFlowDTO();
			List<HandleFlowDTO> handleFlowList = client
					.findAllHandleFlow(handleFlowDTO);
			model.put("handleFlowList", handleFlowList);
			// 根据项目id查询办理信息
			HandleInfoDTO handleInfo = client
					.getHandleInfoByProjectId(projectId);
			int handleId = handleInfo.getPid();
			// 根据办理id查询办理动态列表
			HandleDynamicDTO handleDynamicQuery = new HandleDynamicDTO();
			handleDynamicQuery.setHandleId(handleId);
			List<HandleDynamicDTO> handleDynamicList = client
					.findAllHandleDynamic(handleDynamicQuery);
			int sumHandleDay = 0;// 操作总天数=所有的操作天数总和
			Map<Long, HandleDynamicDTO> handleDynamicMap = new HashMap<Long, HandleDynamicDTO>();
			for (int i = 0; i < handleDynamicList.size(); i++) {
				HandleDynamicDTO dto = handleDynamicList.get(i);
				sumHandleDay = dto.getHandleDay() + sumHandleDay;
				handleDynamicMap.put(new Long(dto.getHandleFlowId()), dto);
			}
			model.put("sumHandleDay", sumHandleDay);// 操作总天数
			model.put("handleDynamicMap", handleDynamicMap);
			model.put("projectId", projectId);
			destroyFactory(factory,userFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "foreafterloan/transit_monitor_show";
	}

	/**
	 * 跳转到异常监控查看页面
	 *
	 * @author:liangyanjun
	 * @time:2017年1月17日下午3:33:51
	 * @param model
	 * @param projectId
	 * @return
	 */
	@RequestMapping(value = "toExceptionMonitorShow")
	public String toExceptionMonitorShow(ModelMap model, int projectId) {
		try {
			BaseClientFactory userFactory = getFactory(
					BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client userClient = (SysUserService.Client) userFactory
					.getClient();
			BaseClientFactory factory = getFactory(
					BusinessModule.MODUEL_INLOAN, "BizHandleService");
			BizHandleService.Client client = (BizHandleService.Client) factory
					.getClient();
			// 填充监控人
			List<SysUser> users = userClient.getUsersByRoleCode("FORE_AFTER_LOAN_MANAGER");
			HashMap<Integer, String> monitorUserMap = new HashMap<>();
			for (SysUser u : users) {
				monitorUserMap.put(u.getPid(), u.getRealName());
			}
			model.put("monitorUserMap", monitorUserMap);
			// .填充办理流程条目数据
			HandleFlowDTO handleFlowDTO = new HandleFlowDTO();
			List<HandleFlowDTO> handleFlowList = client
					.findAllHandleFlow(handleFlowDTO);
			model.put("handleFlowList", handleFlowList);
			// 根据项目id查询办理信息
			HandleInfoDTO handleInfo = client
					.getHandleInfoByProjectId(projectId);
			int handleId = handleInfo.getPid();
			// 根据办理id查询办理动态列表
			HandleDynamicDTO handleDynamicQuery = new HandleDynamicDTO();
			handleDynamicQuery.setHandleId(handleId);
			List<HandleDynamicDTO> handleDynamicList = client
					.findAllHandleDynamic(handleDynamicQuery);
			int sumHandleDay = 0;// 操作总天数=所有的操作天数总和
			Map<Long, HandleDynamicDTO> handleDynamicMap = new HashMap<Long, HandleDynamicDTO>();
			for (int i = 0; i < handleDynamicList.size(); i++) {
				HandleDynamicDTO dto = handleDynamicList.get(i);
				sumHandleDay = dto.getHandleDay() + sumHandleDay;
				handleDynamicMap.put(new Long(dto.getHandleFlowId()), dto);
			}
			model.put("sumHandleDay", sumHandleDay);// 操作总天数
			model.put("handleDynamicMap", handleDynamicMap);
			model.put("projectId", projectId);
			destroyFactory(factory,userFactory);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "foreafterloan/exception_monitor_show";
	}

	/**
	 * 在途业务监控列表（分页查询）
	 *
	 * @author:liangyanjun
	 * @time:2017年1月12日下午2:17:43
	 * @param query
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/transitMonitorIndexList")
	public void transitMonitorIndexList(TransitMonitorIndex query,
			HttpServletRequest request, HttpServletResponse response)
			throws TException, ThriftException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TransitMonitorIndex> list = null;
		int total = 0;
		List<Integer> userIds = getUserIds(request);
		query.setUserIds(userIds);
		BaseClientFactory factory = getFactory(
				BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
		ForeAfterLoanService.Client client = (ForeAfterLoanService.Client) factory
				.getClient();
		list = client.queryTransitMonitorIndex(query);
		total = client.getTransitMonitorIndexTotal(query);
		// 输出
		map.put("rows", list);
		map.put("total", total);
		outputJson(map, response);
		destroyFactory(factory);
	}
	
	/**
	 * 在途业务监控列表导出
	 *
	 * @author:Jony
	 * @time:2017年1月12日下午2:17:43
	 * @param query
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/exportTransitMonitorIndexList")
	public void exportTransitMonitorIndexList(TransitMonitorIndex query,
			HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
		try {
				ForeAfterLoanService.Client client = (ForeAfterLoanService.Client) factory.getClient();
				Map<String, Object> map = new HashMap<String, Object>();
				
				TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
						BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			List<TransitMonitorIndex> list = client.queryTransitMonitorIndex(query);
			if(list !=null && list.size() > 0){
				
				for(TransitMonitorIndex index :list){
					if(index.getForeAfterMonitorStatus() == 1){
						index.setForeAfterMonitorStatusName("无异常");
					}else if(index.getForeAfterMonitorStatus() == 2){
						index.setForeAfterMonitorStatusName("有异常");
					}else{
						index.setForeAfterMonitorStatusName("异常转正常");
					}
				}
				
			}
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("ZTYWJK");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"在途业务监控导出"
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
	 * 异常监控处理列表（分页查询）
	 * 
	 * @author:Jony
	 * @time:2017年1月12日下午2:17:43
	 * @param query
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/toTransitExceptionIndexList")
	public void toTransitExceptionIndexList(TransitExceptionIndex query, HttpServletRequest request, HttpServletResponse response,int type) throws TException, ThriftException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TransitExceptionIndex> list = null;
		int total = 0;
		List<Integer> userIds = getUserIds(request);
		if(type==2){
			query.setApplyStatus(2);
		}
		query.setUserIds(userIds);
		BaseClientFactory factory = getFactory(
				BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
		ForeAfterLoanService.Client client = (ForeAfterLoanService.Client) factory
				.getClient();
		list = client.queryTransitExceptionIndex(query);
		total = client.getTransitExceptionIndexTotal(query);
		// 输出
		map.put("rows", list);
		map.put("total", total);
		outputJson(map, response);
		destroyFactory(factory);
	}
	/**
	 * 异常业务监控列表导出
	 *
	 * @author:Jony
	 * @time:2017年1月12日下午2:17:43
	 * @param query
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/exportTransitExceptionIndexList")
	public void exportTransitExceptionIndexList(TransitExceptionIndex query,
			HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
		try {
				ForeAfterLoanService.Client client = (ForeAfterLoanService.Client) factory.getClient();
				Map<String, Object> map = new HashMap<String, Object>();
				
				TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
						BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			List<TransitExceptionIndex> list = client.queryTransitExceptionIndex(query);
			if(list != null && list.size() >0){
				for(TransitExceptionIndex index: list){
					if(index.dangerLevel == 3){
						index.setDangerLevelName("紧急风险");
					}else if(index.dangerLevel == 2){
						index.setDangerLevelName("重大风险");
					}else if(index.dangerLevel == 1){
						index.setDangerLevelName("正常风险");
					}else{
						index.setDangerLevelName("待跟进");
					}
					if(index.monitorStatus == 3){
						index.setMonitorStatusName("异常转正常");
					}else if(index.monitorStatus == 1){
						index.setMonitorStatusName("正常");
					}else{
						index.setMonitorStatusName("异常");
					}
				}
			}
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("YCYWJK");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"异常业务监控导出"
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
	 * 异常监控处理列表（分页查询）
	 * 
	 * @author:jony
	 * @time:2017年1月12日下午2:17:43
	 * @param query
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/toExceptionManageIndexList")
	public void toExceptionManageIndexList(TransitExceptionIndex query,
			HttpServletRequest request, HttpServletResponse response)
			throws TException, ThriftException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TransitExceptionIndex> list = null;
		int total = 0;
		List<Integer> userIds = getUserIds(request);
		query.setUserIds(userIds);
		BaseClientFactory factory = getFactory(
				BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
		ForeAfterLoanService.Client client = (ForeAfterLoanService.Client) factory
				.getClient();
		list = client.queryTransitExceptionIndex(query);
		total = client.getTransitExceptionIndexTotal(query);
		// 输出
		map.put("rows", list);
		map.put("total", total);
		outputJson(map, response);
		destroyFactory(factory);
	}
	
	/**
	 * 异常业务处理列表导出
	 *
	 * @author:Jony
	 * @time:2017年1月12日下午2:17:43
	 * @param query
	 * @param request
	 * @param response
	 * @throws TException
	 * @throws ThriftException
	 */
	@RequestMapping(value = "/exportExceptionManageList")
	public void exportExceptionManageList(TransitExceptionIndex query,int type,
			HttpServletRequest request, HttpServletResponse response) {
		List<TransitExceptionIndex> list = null;
		BaseClientFactory factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN, "ForeAfterLoanService");
		try {
				ForeAfterLoanService.Client client = (ForeAfterLoanService.Client) factory.getClient();
				Map<String, Object> map = new HashMap<String, Object>();
				TemplateFileService.Client templateFileService = (TemplateFileService.Client) getService(
						BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
				if(type==2){
					query.setApplyStatus(2);
				}
				list = client.queryTransitExceptionIndex(query);
				for(TransitExceptionIndex index: list){
					if(index.dangerLevel == 3){
						index.setDangerLevelName("紧急风险");
					}else if(index.dangerLevel == 2){
						index.setDangerLevelName("重大风险");
					}else if(index.dangerLevel == 1){
						index.setDangerLevelName("正常风险");
					}else{
						index.setDangerLevelName("待跟进");
					}
					if(index.monitorStatus == 3){
						index.setMonitorStatusName("异常转正常");
					}else if(index.monitorStatus == 1){
						index.setMonitorStatusName("正常");
					}else{
						index.setMonitorStatusName("异常");
					}
				}
			map.put("bean", list);
			TemplateFile template = templateFileService.getTemplateFile("YCYWCL");
			if (template != null && !template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(
						map,
						path,
						"异常业务处理导出"
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
	 * 新增异常报告
	 * @param transitApplyReportIndex
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: Jony
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "addApplyReport")
	public void addApplyReport(TransitApplyReportIndex transitApplyReportIndex,
			HttpServletResponse response, HttpServletRequest request)
			throws Exception {
		Json j = super.getSuccessObj();
		BaseClientFactory factory = null;
		BaseClientFactory projectFactory = getFactory(
				BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN,
					"ForeAfterLoanService");
			ForeAfterLoanService.Client c = (ForeAfterLoanService.Client) factory
					.getClient();
			int rows = c.addTransitApplyReportIndex(transitApplyReportIndex);
			List<LegalIndex> legalList = new ArrayList<LegalIndex>();
			legalList = transitApplyReportIndex.getLegalList();
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE,
						SysLogTypeConstant.LOG_TYPE_ADD, "新增申请报告");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "新增申请报告成功!");
				int projectId = transitApplyReportIndex.getProjectId();
				// 新增诉讼列表数据
				if (legalList != null && legalList.size() > 0) {
					for (LegalIndex legal : legalList) {
						legal.setReportId(projectId);
						c.addLegalIndex(legal);
					}
				}
				ProjectService.Client projectClient = (ProjectService.Client) projectFactory
						.getClient();
				// 新增以后修改申请报告状态为2（下次就是修改）
				projectClient.updateForeAfterReportStatus(projectId, 2);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			logger.error("新增申请报告:" + e.getMessage());
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "新增失败,请重新操作!");
		} finally {
			if (factory != null) {
				try {
					factory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(j, response);
	}

	/**
	 * 修改异常报告
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: qiancong.Xian
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "updateApplyReport")
	public void updateApplyReport(
			TransitApplyReportIndex transitApplyReportIndex,
			HttpServletResponse response, String pids,
			HttpServletRequest request) throws Exception {
		Json result = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN,"ForeAfterLoanService");
			ForeAfterLoanService.Client c = (ForeAfterLoanService.Client) clientFactory.getClient();
			// 修改报告
			int row = c.updateTransitApplyReportIndex(transitApplyReportIndex);
			if (row > 0) {
				result = getSuccessObj();
			} else {
				result = this.getFailedObj("找到不到对于的记录，请联系管理员");
			}
			String pidStr = pids.substring(1);
			String[] pidsStr = pidStr.split(",");
			if (pidsStr.length == 0) {
				fillReturnJson(response, false, "删除失败，参数不全");
				return;
			}
			// 修改诉讼
			for (String id : pidsStr) {
				LegalIndex legal = new LegalIndex();
				String statusName = id + "status";
				String legalContentName = id + "legalContent";
				String legalContent = request.getParameter(legalContentName);
				String legalStatus = request.getParameter(statusName);
				int status = Integer.parseInt((String) legalStatus.subSequence(0, 1));
				legal.setPid(Integer.parseInt(id));
				legal.setStatus(status);
				legal.setLegalContent(legalContent);
				c.updateLegal(legal);
			}
			List<LegalIndex> legalList = new ArrayList<LegalIndex>();
			legalList = transitApplyReportIndex.getLegalList();
			// 如果有新增 则新增诉讼列表数据
			if (legalList != null && legalList.size() > 0) {
				for (LegalIndex legal : legalList) {
					legal.setReportId(transitApplyReportIndex.getProjectId());
					if (legal.status != -1 && legal.legalContent != null) {
						c.addLegalIndex(legal);
					}
				}
			}
		} catch (Exception e) {
			logger.error("修改异常报告失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		outputJson(result, response);
	}

	/**
	 * 增加异常监控表
	 * @param pid
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: Jony
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "addExceptionList")
	public void addExceptionList(ExceptionMonitorIndex exceptionMonitorIndex,
			HttpServletResponse response, HttpServletRequest request,
			String noticeWays) throws Exception {
		Json j = super.getSuccessObj();
		BaseClientFactory factory = null;
		List<String> noticeWayStr = null;
		
		try {
			factory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN,"ForeAfterLoanService");
			ForeAfterLoanService.Client c = (ForeAfterLoanService.Client) factory.getClient();
			SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			//通过Id查询姓名
			SysUser sysUser = new SysUser(); 
			sysUser = sysUserService.getSysUserByPid(exceptionMonitorIndex.getFollowId());
			String followName = sysUser.getRealName();
//			if(followName !=null && followName != ""){
			if(!StringUtil.isBlank(followName)){
				exceptionMonitorIndex.setFollowName(followName);
			}
			int rows = c.addExceptionMonitorIndex(exceptionMonitorIndex);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(BusinessModule.MODUEL_MORTGAGE,
						SysLogTypeConstant.LOG_TYPE_ADD, "新增异常监控列表");
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "新增异常监控成功!");
				// 选择发邮件or短信
				if (StringUtil.isBlank(noticeWays)) {
					return;
				} else {
					noticeWayStr = Arrays.asList(noticeWays.split(","));
					int projectId = exceptionMonitorIndex.getProjectId();// 项目ID
					BaseClientFactory projectFactory = getFactory(
							BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
					ProjectService.Client projectClient = (ProjectService.Client) projectFactory.getClient();
					Project project = projectClient.getProjectInfoById(projectId);
					// 发短信=1,email=2
					// 发送通知
					sendNoticeMsg(exceptionMonitorIndex, project, noticeWayStr);
				}
				
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "新增失败,请重新操作!");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		}  finally {
			if (factory != null) {
				try {
					factory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(j, response);
	}

	/**
	 * 查询贷后异常监控(发短信和发邮件)
	 *
	 * @author:liangyanjun
	 * @time:2017年1月17日上午10:00:42
	 * @return
	 * @throws ThriftException
	 * @throws TException
	 */
	private void sendNoticeMsg(ExceptionMonitorIndex exceptionMonitorIndex,
			Project project, List<String> noticeWayStr) throws ThriftException,
			TException {
		String projectName = project.getProjectName();
		if (noticeWayStr.isEmpty()) {
			return;
		}
		//需要发送信息的用户map
		Map<Integer, SysUser> sendMsgUserMap=new HashMap<Integer, SysUser>();
		// 查询贷后异常通知用户
		List<SysLookupVal> lookupVals = getSysLookupValByLookType("AFTER_EXCEPTION_REPORT_NOTICE_ROLE");
		BaseClientFactory factory = getFactory(BusinessModule.MODUEL_SYSTEM,"SysUserService");
		SysUserService.Client client = (SysUserService.Client) factory.getClient();
		 BaseClientFactory workflowServiceFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
	    WorkflowService.Client workflowService = (WorkflowService.Client) workflowServiceFactory.getClient();
        int followId = exceptionMonitorIndex.getFollowId();
		SysUser followUser = client.getUserDetailsByPid(followId);
		int pmUserId=project.getPmUserId();
		SysUser pmUser = client.getUserDetailsByPid(pmUserId);
		if (!StringUtils.isEmpty(followUser)) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("projectName", projectName);
			map.put("followName", followUser.getRealName());
			map.put("monitorDate", exceptionMonitorIndex.getMonitorDate());
			String maillText = templateParsing(map,
					"afterMonitorReportEmail.ftl");
			String smsText = templateParsing(map, "afterMonitorReportSms.ftl");
		     sendMsgUserMap.put(followUser.getPid(), pmUser);
			for (SysLookupVal v : lookupVals) {
				String roleCode = v.getLookupVal();
				List<SysUser> users = workflowService.findBizUserByRoleCode(pmUser, roleCode);
				for (SysUser sysUser : users) {
					sendMsgUserMap.put(sysUser.getPid(), sysUser);
				}
			}
			 //遍历用户发送信息
	        Set<Integer> keySet = sendMsgUserMap.keySet();
	        for (Integer userId : keySet) {
	            sendMsg(noticeWayStr, maillText, smsText, sendMsgUserMap.get(userId));
	        }
		}
		destroyFactory(factory,workflowServiceFactory);
	}
	 private void sendMsg(List<String> noticeWays, String maillText, String smsText, SysUser sysUser) {
	        System.out.println(sysUser.getRealName());
	        String phone = sysUser.getPhone();
	        String mail = sysUser.getMail();
	        if (!StringUtil.isBlank(phone)&&noticeWays.contains(Constants.FORE_AFTER_NOTICE_WAY_SMS)) {
	           sendSms(phone, smsText);
	        } 
	        if(!StringUtil.isBlank(mail)&&noticeWays.contains(Constants.FORE_AFTER_NOTICE_WAY_MAIL)){
	            sendMail(mail, "贷后异常监控", maillText);
	        }
	    }
	/**
	 * 删除诉讼
	 * 
	 * @param pids
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: Jony
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "deleteLegalIndexByIds")
	public void deleteLegalIndexByIds(String ids, HttpServletResponse response)
			throws Exception {
		Json result = null;
		BaseClientFactory clientFactory = null;
		String pidsStr = ids.substring(1);
		List<Integer> idList = new ArrayList<>();
		// 查询账户信息
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_FORE_AFTERLOAN,"ForeAfterLoanService");
			ForeAfterLoanService.Client c = (ForeAfterLoanService.Client) clientFactory.getClient();
			String[] pids = pidsStr.split(",");
			if (pids.length == 0) {
				fillReturnJson(response, false, "删除失败，参数不全");
				return;
			}
			for (String pid : pids) {
				idList.add(Integer.parseInt(pid));
			}
			// 批量删除诉讼
			c.deleteLegalIndexByIds(idList);
		} catch (Exception e) {
			logger.error("删除诉讼失败", e);
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
				}
			}
		}
		outputJson(result, response);
	}

	/**
	 * 回归正常
	 * 
	 * @param pid
	 * @param model
	 * @return
	 * @throws Exception
	 * @author: Jony
	 * @date: 2015年4月9日 上午10:02:50
	 */
	@RequestMapping(value = "updateMonitorStatus")
	public void updateMonitorStatus(String returnNormalRemark,int projectId, HttpServletResponse response)
			throws Exception {
		BaseClientFactory projectFactory = getFactory(
				BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			ProjectService.Client projectClient = (ProjectService.Client) projectFactory.getClient();
			projectClient.updateMonitorStatusAndReturnNormalRemark(projectId,Constants.FORE_AFTER_MONITOR_STATUS_3,returnNormalRemark);
			destroyFactory(projectFactory);
			fillReturnJson(response, true, "提交成功");
		} catch (Exception e) {
			fillReturnJson(response, false, "提交失败");
		}
	}
}
