/**
 * @Title: ProjectPartnerController.java
 * @Package com.xlkfinance.bms.web.controller
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月18日 下午3:56:31
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.partner;

import java.io.File;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.PartnerConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBank;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBankService;
import com.xlkfinance.bms.rpc.partner.PartnerApprovalRecord;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerRefund;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.workflow.TaskHistoryDto;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.api.partnerapi.daju.constant.DaJuConstant;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.ResponseParams;
import com.xlkfinance.bms.web.api.partnerapi.daju.util.DaJuUtil;
import com.xlkfinance.bms.web.api.partnerapi.impl.DRPartnerApiImpl;
import com.xlkfinance.bms.web.api.partnerapi.impl.DaJuPartnerApiImpl;
import com.xlkfinance.bms.web.api.partnerapi.impl.HNBXPartnerApiImpl;
import com.xlkfinance.bms.web.api.partnerapi.impl.TLPartnerApiImpl;
import com.xlkfinance.bms.web.api.partnerapi.impl.XYPartnerApiImpl;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.ParseDocAndDocxUtil;
import com.xlkfinance.bms.web.util.PartnerProjectUtil;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.UuidUtil;
import com.xlkfinance.bms.web.util.YZTSercurityUtil;

@Controller
@RequestMapping("/projectPartnerController")
public class ProjectPartnerController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(ProjectPartnerController.class);
	
	
	//小赢白名单操作用户
	//private static String XIAOYING_PARAM_ADD_USER = PropertiesUtil.getValue("xiaoying.partner.param.add.user");
	//小赢接口地址
	private static String XIAOYING_PARTNER_API_URL = PropertiesUtil.getValue("xiaoying.partner.api.url");
	
	//小赢MD5
	private static String XIAOYING_PARTNER_MD5 = PropertiesUtil.getValue("xiaoying.partner.md5");
	
	
	@Resource(name = "tLPartnerApiImpl")
	private TLPartnerApiImpl tLPartnerApiImpl;		//统联接口实现类
	@Resource(name = "dRPartnerApiImpl")
	private DRPartnerApiImpl dRPartnerApiImpl;		//点融接口实现类
	@Resource(name = "xYPartnerApiImpl")
	private XYPartnerApiImpl xYPartnerApiImpl;		//小赢接口实现类
	@Resource(name = "daJuPartnerApiImpl")
	private DaJuPartnerApiImpl daJuPartnerApiImpl;		//大桔接口实现类
	@Resource(name = "hNBXPartnerApiImpl")
	private HNBXPartnerApiImpl hNBXPartnerApiImpl;		//华安保险接口实现类
	
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	
	
	
	/**
	 * 跳转机构合作项目列表
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月18日 下午4:03:17
	 */
	@RequestMapping(value = "/partnerIndex")
	public String partnerIndex(){
		return "partner/partner_index";
	}
	
	/**
	 * 查询机构合作项目列表
	  * @param partner
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月21日 下午5:44:26
	 */
	@RequestMapping(value = "/projectList")
	public void projectList(ProjectPartner partner, HttpServletRequest request, HttpServletResponse response){
		logger.info("机构合作项目查询，入参："+partner);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProjectPartner> result = null;
		int total = 0;
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			partner.setUserIds(getUserIds(request));//数据权限
			result = clientPartner.findAllProjectInfo(partner);
			total = clientPartner.findAllProjectInfoCount(partner);
		} catch (Exception e) {
			logger.error("获取机构合作项目列表失败：" + e.getMessage());
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>projectList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 * 打开项目详情页面，公用方法
	 * @param projectId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/editProjectPartner")
	public void editProjectPartner(int projectId,Integer pid, String partnerNo ,HttpServletRequest request, HttpServletResponse response){
		logger.info("机构合作项目详情，入参："+projectId);
		ProjectPartnerDto dto = new ProjectPartnerDto();
		
		BaseClientFactory clientFactoryPartner = null;
		BaseClientFactory clientFactoryCus = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientFactoryWorkflow = null;
		BaseClientFactory clientFactoryEstate = null;
		BaseClientFactory clientFactoryProject = null;
		BaseClientFactory clientFactorySystem = null;
		BaseClientFactory clientFactoryCusBank = null;
		
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientCus =(CusPerService.Client) clientFactoryCus.getClient();
			
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			clientFactoryWorkflow = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
			WorkflowService.Client clientWorkflow =(WorkflowService.Client) clientFactoryWorkflow.getClient();
			
			clientFactoryEstate = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client clientEstate = (BizProjectEstateService.Client) clientFactoryEstate.getClient();
			
			clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			
			
			clientFactorySystem = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client clientSystem =(SysUserService.Client) clientFactorySystem.getClient();
			
			//银行开户
			clientFactoryCusBank = getFactory(BusinessModule.MODUEL_PARTNER, "BizPartnerCustomerBankService");
			BizPartnerCustomerBankService.Client clientCusBank = (BizPartnerCustomerBankService.Client) clientFactoryCusBank.getClient();
			
			//项目基本信息---------------
			Project tempProject =clientProject.getLoanProjectByProjectId(projectId);
			//查询客户基本信息--------------
			Integer perId = clientCus.selectPerIdByAcctId(tempProject.getAcctId());
			CusPerson tempCusPerson = clientCus.getCusPerBase(perId).getCusPerson();
			
			//收费
			ProjectGuarantee tempGuarantee = clientProject.getGuaranteeByProjectId(projectId);
			
			//共同借款人----------------
			List<CusDTO> tempPublicManList= clientCus.getNoSpouseLists(projectId);
			//多物业信息--------------------
			List<BizProjectEstate > tempEstateList = clientEstate.getAllByProjectId(projectId);
			//赎楼信息----------------
			ProjectForeclosure tempForeclosure =clientProject.getForeclosureByProjectId(projectId);
			//工作流-----------
			//creditLoanRequestProcess 小科  foreLoanRequestProcess 万通  
			WorkflowProjectVo queryWorkFlow= new WorkflowProjectVo();
			queryWorkFlow.setProjectId(projectId);
			queryWorkFlow.setProcessDefinitionKey("creditLoanRequestProcess");
			WorkflowProjectVo tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			//小科为空，查询万通
			if(tempW == null || tempW.getPid() == 0){
				queryWorkFlow.setProcessDefinitionKey("foreLoanRequestProcess");
				tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			}
			//机构合伙人
			if(tempW == null || tempW.getPid() == 0){
				queryWorkFlow.setProcessDefinitionKey("businessApplyRequestProcess");
				tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			}
			List<TaskHistoryDto> taskHistorys = new ArrayList<TaskHistoryDto>();
			// 有效的工作流对象
			if(null != tempW && tempW.getPid()>0  ){
				String workflowInstanceId = tempW.getWorkflowInstanceId();
				taskHistorys = clientWorkflow.queryWorkFlowHistory(workflowInstanceId,1,100);
			}
			ProjectPartner tempPartner = null;
			
			if(pid != null && pid > 0){
				ProjectPartner queryPartner = new ProjectPartner();
				queryPartner.setPid(pid);
				tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			}
			
			//设置值
			dto.setPublicManList(tempPublicManList);
			dto.setEstateList(tempEstateList);
			dto.setProjectForeclosure(tempForeclosure);
			//工作流记录
			dto.setTaskHistoryList(taskHistorys);
			//物业信息
			dto.setProjectProperty(tempProject.getProjectProperty());
			if(!StringUtil.isBlank(partnerNo)){
				dto.setPartnerNo(partnerNo);
			}
			//查询资金项目，覆盖数据
			if(tempPartner != null && tempPartner.getPid() > 0){
				//操作人
				SysUser tempSysUser = clientSystem.getSysUserByPid(tempPartner.getPmUserId());
				dto.setPmUserName(tempSysUser.getRealName());
			}
			
			//转换对象
			dto = PartnerProjectUtil.convertBean(dto, tempProject, tempGuarantee, tempPartner, tempCusPerson);
			
			//当前操作人
			ShiroUser shiroUser = getShiroUser();
			if(StringUtil.isBlank(dto.getPmUserName())){
				dto.setPmUserName(shiroUser.getRealName());
				dto.setPmUserId(shiroUser.getPid());
			}
			
/*			if(StringUtil.isBlank(dto.getLoanId()) && StringUtil.isBlank(dto.getPayBankName())){
				//查询公司打款银行配置
				//字典service  银行_支行_开户名_帐号_省code_市code
				SysLookupVal  sysLookupVal =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_VAL_PARTNER_LOAN_BANK,Constants.SYS_LOOKUP_VAL_PARTNER_LOAN_BANK_1);
				if(sysLookupVal != null && !StringUtil.isBlank(sysLookupVal.getLookupDesc()) ){
					logger.info(">>>>>>editProjectPartner bank config:" + sysLookupVal.getLookupDesc());
					String [] bankArray = sysLookupVal.getLookupDesc().trim().split("_");
					if(bankArray.length == 6){
						dto.setPayBankName(bankArray[0]);
						dto.setPayBankBranch(bankArray[1]);
						dto.setPayAcctName(bankArray[2]);
						dto.setPayAcctNo(bankArray[3]);
						dto.setPayProvinceCode(bankArray[4]);
						dto.setPayCityCode(bankArray[5]);
					} 
				}
			}*/
			
			//设置默认值
			if(dto.getConfirmLoanDays() == 0 ){
				dto.setConfirmLoanDays(dto.getApplyDate());
			}
			if(dto.getConfirmLoanMoney() == 0 ){
				if(dto.getApproveMoney() != 0){
					dto.setConfirmLoanMoney(dto.getApproveMoney());
				}else{
					dto.setConfirmLoanMoney(dto.getApplyMoney());
				}
			}
			
			
			//查询是否己经开户过了
			BizPartnerCustomerBank queryCustomerBank = new BizPartnerCustomerBank();
			queryCustomerBank.setAcctId(dto.getAcctId());
			queryCustomerBank.setStatus(Constants.COMM_YES);	//成功
			queryCustomerBank.setPage(-1);
			
			List<BizPartnerCustomerBank> tempCusBankList =  clientCusBank.selectList(queryCustomerBank);
			if(!org.springframework.util.CollectionUtils.isEmpty(tempCusBankList)){
				//设置为己开户 ,如果为空，默认赋值
				dto.setIsPartnerOpenAccount(Constants.COMM_YES);
				if(StringUtils.isEmpty(dto.getPaymentAcctNo())){
					dto.setPaymentAcctNo(tempCusBankList.get(0).getBankCard());
				}
				if(StringUtils.isEmpty(dto.getPaymentBankPhone())){
					dto.setPaymentBankPhone(tempCusBankList.get(0).getBankMobileNo());
				}
				
			}
			
		} catch (Exception e) {
			logger.error(">>>>>>editProjectPartner error：", e);
		}finally {
			
			destroyFactory(clientFactoryPartner);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
			destroyFactory(clientFactoryWorkflow);
			destroyFactory(clientFactoryEstate);
			destroyFactory(clientFactoryProject);
			destroyFactory(clientFactorySystem);
			destroyFactory(clientFactoryCusBank);
		}
		outputJson(dto,response);
	}
	
	/**
	 * 保存合作机构项目信息
	  * @param projectPartnerDto
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月23日 下午2:47:56
	 */
	@RequestMapping(value="savePartnerInfo")
	public void savePartnerInfo(ProjectPartnerDto projectPartnerDto,HttpServletResponse response){
		Map<String,Object> body =new HashMap<String,Object>();
		int pid = projectPartnerDto.getPid();
		int rows = 0;
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
 
			//当前操作人员
			ShiroUser shiroUser = getShiroUser();
			projectPartnerDto.setPmUserId(shiroUser.getPid());
			//转化设置对象值
			ProjectPartner updatePartner = convertBean(projectPartnerDto);
			ProjectPartner queryPartner = new ProjectPartner();
			
			//queryPartner.setPartnerNo(projectPartnerDto.getPartnerNo());
			//queryPartner.setRequestStatus(PartnerConstant.RequestStatus.NO_APPLY.getCode());	//未申请
			queryPartner.setProjectId(projectPartnerDto.getProjectId());
			queryPartner.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());				//未关闭
	 
			body.put("pid", pid);
			ProjectPartner tempPartner = new ProjectPartner();
			//查询是否己保存
			List<ProjectPartner> tempPartnerList = clientPartner.findProjectPartnerInfoList(queryPartner); 
			if(!org.springframework.util.CollectionUtils.isEmpty(tempPartnerList) ){
 	
				/**申请放款的数量*/
				int requestLoanCount = 0 ;  
				for(ProjectPartner indexObj : tempPartnerList){
					
					//该项目己经申请放款，不允许操作
					if(indexObj.getLoanStatus() > 1 ){
						fillReturnJson(response, false, "该资金订单已申请放款，请刷新重新操作!");
						return;
					}
					
					//不同资方允许同时审批，不能同时存在2条
					if(indexObj.getPartnerNo().equals(projectPartnerDto.getPartnerNo())){
						if(indexObj.getApprovalStatus() > 0 && indexObj.getPid() != projectPartnerDto.getPid()){
							fillReturnJson(response, false, "该订单己提交资金审批，不能再操作!");
							return;
						}
					}
					
					//未申请的数据
					if(indexObj.getRequestStatus() == PartnerConstant.RequestStatus.NO_APPLY.getCode() ){
						if(indexObj.getPartnerNo().equals(projectPartnerDto.getPartnerNo()) && indexObj.getPid() !=projectPartnerDto.getPid()){
							fillReturnJson(response, false, "该资金订单己存在，不能再操作!");
							return;
						}
					}
					
					if(indexObj.getLoanStatus() > 1 ){
						requestLoanCount ++;
					}
					if(pid == indexObj.getPid()){
						tempPartner = indexObj;
					}
				}
 
				if(requestLoanCount > 0){
					fillReturnJson(response, false, "该资金订单已申请放款，请刷新重新操作!");
					return;
				}
			}
			
			if(pid >0){
				//如果没有的话，就重新设备机构订单编号
				if(StringUtil.isBlank(tempPartner.getPartnerOrderCode())){
					//设置机构项目订单编号
					updatePartner.setPartnerOrderCode(DateUtils.dateToString(new Date(),"yyyyMMddHHmmssSSSS"));
				}
				rows = clientPartner.updateProjectPartner(updatePartner);
				body.put("pid", pid);
				if(rows != 0){
					fillReturnJson(response, true, "操作成功!",body);
					return;
				}else {
					// 失败的话,做提示处理
					fillReturnJson(response, false, "修改失败,请重新操作!",body);
					return;
				}
			}else{
				if(tempPartner != null && tempPartner.getPid() > 0){
					body.put("pid", pid);
					fillReturnJson(response, false, "新增失败，该机构数据己存在，请刷新重新操作!",body);
					return;
				}
				//设置机构项目订单编号
				updatePartner.setPartnerOrderCode(DateUtils.dateToString(new Date(),"yyyyMMddHHmmssSSSS"));
				updatePartner.setRequestStatus(PartnerConstant.RequestStatus.NO_APPLY.getCode());
				updatePartner.setLoanStatus(PartnerConstant.LoanStatus.NO_APPLY.getCode());
				queryPartner.setRepaymentRepurchaseStatus(PartnerConstant.RepaymentRepurchaseStatus.NO_APPLY.getCode());
				queryPartner.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());
				
				pid = clientPartner.addProjectPartner(updatePartner);
				if(pid >0){
					body.put("pid", pid);
					fillReturnJson(response, true, "操作成功!",body);
					return;
				}else {
					// 失败的话,做提示处理
					fillReturnJson(response, false, "新增失败，请重新操作!");
					return;
				}
			}
		}catch (Exception e) {
			logger.error(">>>>>>savePartnerInfo error:" , e);
			// 失败的话,做提示处理
			fillReturnJson(response, false, "操作失败,请重新操作!",body);
			return;
		}finally {
			destroyFactory(clientFactoryPartner);
		}
	}
	
	
	/**
	 * 提交审核，补充材料
	 * @param projectPartnerDto
	 * @param requestAddFiles	补充材料
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "applyPartner")
	public void applyPartner(ProjectPartnerDto projectPartnerDto,String requestAddFiles,HttpServletRequest request,HttpServletResponse response){
		logger.info("合作项目提交审核，参数："+projectPartnerDto);
		
		String uuid = UuidUtil.randomUUID();
		
		int pid = 0;
		String loanId = "";
		int errCode = -1;
		BaseClientFactory clientFactoryPartner = null;
		BaseClientFactory clientFactorySys = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();

			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			
			
			Date nowDate = new Date();
			Timestamp nowTime = new Timestamp(nowDate.getTime());
			ProjectPartner partner = convertBean(projectPartnerDto);
			
			pid = partner.getPid();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner != null && tempPartner.getPid() >0){
				//验证状态
				if(tempPartner == null || tempPartner.getPid() == 0){
					fillReturnJson(response, false, "数据不存在");
					return;
				}else if(tempPartner.getApprovalStatus() >0 
						&& Constants.APPROVAL_STATUS_4 !=tempPartner.getApprovalStatus()){
					//只能未申请和补充材料才能提交申请
					fillReturnJson(response, false, "必须是补充材料才能提交申请");
					return;
				}
				clientPartner.updateProjectPartner(partner);
			}else{
				partner.setRequestStatus(Constants.REQUEST_APPLY_STATUS_1);
				partner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_1);
				partner.setLoanStatus(Constants.LOAN_STATUS_1);
				pid = clientPartner.addProjectPartner(partner);
			}
			
			String requestFiles = projectPartnerDto.getRequestFiles();
			
			//如果是补充材料，单独提交新的文件
			if(!StringUtil.isBlank(requestAddFiles)){
				projectPartnerDto.setRequestFiles(requestAddFiles);
				if(!StringUtil.isBlank(requestFiles)){
					requestFiles += ","+requestAddFiles;
				}else{
					requestFiles = requestAddFiles;
				}
			}
			
			
			JSONObject resultJson = null;
			String msg="";
			
 
			//-----------------------------华丽的分割线-------------------正式-----------------------------------
			if(PartnerConstant.PARTNER_TL.equals(projectPartnerDto.getPartnerNo())){
				
				//判断机构的放款金额
				if(StringUtil.isBlank(partner.getLoanId())){
					//查询统联的配
					//字典service
					SysLookupVal  sysLookupVal =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_LOAN_LIMIT,Constants.SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_TL);
					
					ProjectPartner queryPartner2 = new ProjectPartner();
					queryPartner2.setPartnerNo(partner.getPartnerNo());
					queryPartner2.setIsClosed(Constants.COMM_YES);
					double loanAmountCount =  clientPartner.findLoanAmountCountByPartnerNo(queryPartner2);
					
					double loanMoneyLimit = Double.parseDouble(sysLookupVal.getLookupDesc().trim());
					if(sysLookupVal != null && sysLookupVal.getPid() > 0 && !StringUtil.isBlank(sysLookupVal.getLookupDesc())){
						
						if(loanMoneyLimit < loanAmountCount + partner.getLoanAmount()){
							fillReturnJson(response, false, "机构统联放款额度己超过限制，累计金额："+CommonUtil.formatDoubleNumber(loanAmountCount)+",总额度："+CommonUtil.formatDoubleNumber(loanMoneyLimit));
							return ;
						}
					}
				}
				resultJson = tLPartnerApiImpl.apply(projectPartnerDto,request);
			}else if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
				
				resultJson = xYPartnerApiImpl.apply(projectPartnerDto, request);
				
			}else if(PartnerConstant.PARTNER_DR.equals(projectPartnerDto.getPartnerNo())){
				//点融单笔放款额度
				SysLookupVal  sysLookupValOne =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_LOAN_LIMIT,Constants.SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_ONE);
				if(!StringUtil.isBlank(sysLookupValOne.getLookupDesc()) && !"-1".equals(sysLookupValOne.getLookupDesc())  ){
					double loanMoneyLimitOne = Double.parseDouble(sysLookupValOne.getLookupDesc().trim());
					if(loanMoneyLimitOne <  partner.getLoanAmount()){
						fillReturnJson(response, false, "机构点融单笔放款额度己超过限制，放款金额："+CommonUtil.formatDoubleNumber(partner.getLoanAmount())+",单笔额度："+CommonUtil.formatDoubleNumber(loanMoneyLimitOne));
						return ;
					}
				}
				//点融单天放款额度
				SysLookupVal  sysLookupValDay =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_LOAN_LIMIT,Constants.SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_DAY);
				if(!StringUtil.isBlank(sysLookupValDay.getLookupDesc()) && !"-1".equals(sysLookupValDay.getLookupDesc())  ){
					ProjectPartner queryPartner2 = new ProjectPartner();
					queryPartner2.setPartnerNo(partner.getPartnerNo());
					queryPartner2.setIsClosed(Constants.COMM_YES);
					queryPartner2.setBeginTime(DateUtils.dateFormatByPattern(nowDate,"yyyy-MM-dd")+" 00:00:00");
					queryPartner2.setEndTime(DateUtils.dateFormatByPattern(nowDate,"yyyy-MM-dd")+" 23:59:59");
					double loanAmountCount =  clientPartner.findLoanAmountCountByPartnerNo(queryPartner2);
					
					double loanMoneyLimitDay = Double.parseDouble(sysLookupValDay.getLookupDesc().trim());
					if(loanMoneyLimitDay <  (loanAmountCount+partner.getLoanAmount())){
						fillReturnJson(response, false, "机构点融当天放款额度己超过限制，累计金额："+CommonUtil.formatDoubleNumber(loanAmountCount)+",当月额度："+CommonUtil.formatDoubleNumber(loanMoneyLimitDay));
						return ;
					}
				}
				//点融当月放款额度
				SysLookupVal  sysLookupValMonth =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_LOAN_LIMIT,Constants.SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_MONTH);
				if(!StringUtil.isBlank(sysLookupValMonth.getLookupDesc()) && !"-1".equals(sysLookupValMonth.getLookupDesc())  ){
					ProjectPartner queryPartner2 = new ProjectPartner();
					queryPartner2.setPartnerNo(partner.getPartnerNo());
					queryPartner2.setIsClosed(Constants.COMM_YES);
					
					queryPartner2.setBeginTime(DateUtils.dateFormatByPattern(DateUtils.getFirstDayOfMonth(),"yyyy-MM-dd")+" 00:00:00");
					queryPartner2.setEndTime(DateUtils.dateFormatByPattern(DateUtils.getLastDayOfMonth(nowDate),"yyyy-MM-dd")+" 23:59:59");
					
					double loanAmountCount =  clientPartner.findLoanAmountCountByPartnerNo(queryPartner2);
					double loanMoneyLimitMonth = Double.parseDouble(sysLookupValMonth.getLookupDesc().trim());
					if(loanMoneyLimitMonth <  (loanAmountCount+partner.getLoanAmount())){
						fillReturnJson(response, false, "机构点融当月放款额度己超过限制，累计金额："+CommonUtil.formatDoubleNumber(loanAmountCount)+",当月额度："+CommonUtil.formatDoubleNumber(loanMoneyLimitMonth));
						return ;
					}
				}
				//点融放款总额度
				SysLookupVal  sysLookupValTotal = clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_LOAN_LIMIT,Constants.SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_TOTAL);
				if(!StringUtil.isBlank(sysLookupValTotal.getLookupDesc()) && !"-1".equals(sysLookupValTotal.getLookupDesc())  ){
					ProjectPartner queryPartner2 = new ProjectPartner();
					queryPartner2.setPartnerNo(partner.getPartnerNo());
					queryPartner2.setIsClosed(Constants.COMM_YES);
					double loanAmountCount =  clientPartner.findLoanAmountCountByPartnerNo(queryPartner2);
					
					double loanMoneyLimitTotal = Double.parseDouble(sysLookupValTotal.getLookupDesc().trim());
					if(loanMoneyLimitTotal <  (loanAmountCount+partner.getLoanAmount())){
						fillReturnJson(response, false, "机构点融总放款额度己超过限制，累计金额："+CommonUtil.formatDoubleNumber(loanAmountCount)+",总额度："+CommonUtil.formatDoubleNumber(loanMoneyLimitTotal));
						return ;
					}
				}
				resultJson = dRPartnerApiImpl.apply(projectPartnerDto,request);
			}else if(PartnerConstant.PARTNER_NYYH.equals(projectPartnerDto.getPartnerNo())){
				//南粤银行
				resultJson = daJuPartnerApiImpl.apply(uuid,projectPartnerDto,request);
			}else if(PartnerConstant.PARTNER_HNBX.equals(projectPartnerDto.getPartnerNo())){
				//华安保险
				resultJson = hNBXPartnerApiImpl.apply(uuid,projectPartnerDto,request);
			}
				
			
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}else{
				logger.info("applyPartner-提交审核resultJson:"+resultJson.toString());
			}
			
			JSONObject dataJson = null;
			if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
				if(resultJson.get("err_code")!= null){
					errCode =resultJson.getIntValue("err_code");	
				}
				msg =resultJson.getString("err_msg");	
			}else{
				dataJson = resultJson.getJSONObject("data");
				if(dataJson.get("err_code")!= null){
					errCode =dataJson.getIntValue("err_code");
				}
				msg =resultJson.getString("msg");	
			}
			if(errCode == Constants.ERR_CODE_0){
				if(dataJson == null){
					dataJson = resultJson.getJSONObject("data");
				}
				loanId = dataJson.getString("loan_id");
			}
			
			//参数错误为未申请，申请成功为已申请
			if(errCode == Constants.ERR_CODE_0){
				//更新对象
				ProjectPartner updatePartner = new ProjectPartner();
				updatePartner.setPid(pid);
				updatePartner.setRequestFiles(CommonUtil.array_unique(requestFiles.split(",")));
				updatePartner.setRequestTime(nowTime.toString());
				updatePartner.setSubmitApprovalTime(nowTime.toString());
				
				updatePartner.setLoanId(loanId);
				
				updatePartner.setRequestStatus(PartnerConstant.RequestStatus.APPLY_PASS.getCode());
				updatePartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_ING.getCode());//申请成功审核中
				
				ShiroUser shiroUser = getShiroUser();
				updatePartner.setPmUserId(shiroUser.getPid());
				
				//添加机构审核记录
				PartnerApprovalRecord record = new PartnerApprovalRecord();
				record.setPartnerId(pid);
				record.setLoanId(loanId);
				record.setApprovalStatus(Constants.APPROVAL_STATUS_1);
				record.setSubmitApprovalTime(nowTime.toString());
				record.setReApplyReason(partner.getReApplyReason());
				record.setIsNotify(Constants.IS_NOTIFY_1);
				record.setNotifyType(Constants.NOTIFY_TYPE_1);
				clientPartner.addPartnerApprovalRecord(record);
				
				clientPartner.updateProjectPartner(updatePartner);
				
				fillReturnJson(response, true, "操作成功!");
				return;
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
			
		} catch (Exception e) {
			logger.error("applyPartner-提交审核-error:" ,e);
			fillReturnJson(response, false, "网络请求失败,请重新操作!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>applyPartner clientFactoryPartner destroy thrift error：",e);
				}
			}
			if (clientFactorySys != null) {
				try {
					clientFactorySys.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>applyPartner clientFactorySys destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 跳转合作机构审核列表
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月18日 下午4:04:11
	 */
	@RequestMapping(value = "/approvalIndex")
	public String approvalIndex(){
		return "partner/approval_index";
	}
	
	
	/**
	 * 复议(小赢)审核列表提交审核
	  * 
	  * @author: Administrator
	  * @date: 2016年3月18日 下午4:57:36
	 */
	@RequestMapping(value = "reApplyPartner")
	public void reApplyPartner(ProjectPartnerDto projectPartnerDto,HttpServletRequest request,HttpServletResponse response){
		logger.info("合作项目提交审核，参数："+projectPartnerDto);
		int pid = 0;
		int errCode = -1;
		String loanId = "";
		BaseClientFactory clientFactoryPartner = null;
 
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner partner = convertBean(projectPartnerDto);
			pid = partner.getPid();
			
			//验证状态
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner partnerTemp = clientPartner.findProjectPartnerInfo(queryPartner);
			if(partnerTemp == null || partnerTemp.getPid() == 0){
				fillReturnJson(response, false, "数据不存在");
				return;
			}else if(Constants.APPROVAL_STATUS_3 !=partnerTemp.getApprovalStatus()){
				//不通过才能复议
				fillReturnJson(response, false, "必须是审核未通过才能提交申请");
				return;
			}
			clientPartner.updateProjectPartner(partner);
			
			JSONObject resultJson = null;
			String msg="";
			
			
			//-----------------------------华丽的分割线-------------------测试-----------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				
				resultJson = new JSONObject();
				resultJson.put("err_code", 0);
				resultJson.put("err_msg", "ok");
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				dataJson.put("err_msg", "ok");

				dataJson.put("loan_id", StringUtil.isBlank(partnerTemp.getLoanId()) 
						? new Date().getTime() : partnerTemp.getLoanId());
				
				resultJson.put("data", dataJson);
				
			}else{
				//-----------------------------华丽的分割线-------------------正式-----------------------------------
				
				if(PartnerConstant.PARTNER_TL.equals(projectPartnerDto.getPartnerNo())){
					resultJson = tLPartnerApiImpl.reApply(projectPartnerDto,request);
				}else if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
					
					/*
					String cityCode = null;
					//查询字典配置-------------------
					List<SysLookupVal> cityList = clientSys.getSysLookupValByLookType(Constants.SYS_LOOKUP_PARTNER_XIAOGYIN_CITY);
					if(cityList != null && cityList.size() > 0){
						String [] cityConfigArray ;
						for (SysLookupVal indexObj : cityList) {
							//格式为：小赢code_Q房code
							if(!StringUtils.isEmpty(indexObj.getLookupVal())){
								cityConfigArray = indexObj.getLookupVal().split("_");
								if(cityConfigArray.length == 2 && partner.getCityCode().equals(cityConfigArray[1])){
									cityCode = cityConfigArray[0];
								}
							}
						}
					}
					if(StringUtils.isEmpty(cityCode)){
						fillReturnJson(response, false, "该城市暂未开放");
						return;
					}
					
					
					JSONObject param = getPartnerToJson(projectPartnerDto,cityCode,getServerBaseUrl(request));	//请求参数
					param.put("loan_id", projectPartnerDto.getLoanId());
					//复议内容不能为空
					param.put("content", projectPartnerDto.getReApplyReason());
					
					logger.info(">>>>>>reApplyPartner复议申请-加密前参数："+param.toString());
					
					List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
					
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.initConnection();
					String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_RE_APPLY, paramMap, "UTF-8");
	 
					if(!StringUtil.isBlank(result)){
						String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
						logger.info("applyPartner-提交审核，补充材料请求返回结果resContent："+resContent);
						resultJson = JSON.parseObject(resContent);
					}*/
					resultJson = xYPartnerApiImpl.reApply(projectPartnerDto, request);
					
				}else{
					fillReturnJson(response, false, "该机构不支持此服务方法");
					return ;
				}
			}
			
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			
			JSONObject dataJson = null;
			if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
				if(resultJson.get("err_code")!= null){
					errCode =resultJson.getIntValue("err_code");	
				}
				msg =resultJson.getString("err_msg");	
			}else{
				dataJson = resultJson.getJSONObject("data");
				if(dataJson.get("err_code")!= null){
					errCode =dataJson.getIntValue("err_code");
				}
				msg =resultJson.getString("msg");	
			}
			if(errCode == Constants.ERR_CODE_0){
				if(dataJson == null){
					dataJson =resultJson.getJSONObject("data");
				}
				loanId = dataJson.getString("loan_id");
			}
			
			logger.info("reApplyPartner-复议返回结果  resultJson:"+resultJson);
			
			//参数错误为未申请，申请成功为已申请
			if(errCode==Constants.ERR_CODE_0){
				
				Timestamp nowTime = new Timestamp(new Date().getTime());
				//更新机构合作项目
				ProjectPartner updatePartner = new ProjectPartner();
				updatePartner.setPid(pid);
				updatePartner.setRequestTime(nowTime.toString());
				updatePartner.setSubmitApprovalTime(nowTime.toString());
				
				updatePartner.setLoanId(loanId);
				updatePartner.setRequestStatus(Constants.REQUEST_APPLY_STATUS_2);
				updatePartner.setApprovalStatus(Constants.APPROVAL_STATUS_1);//申请成功审核中
				
				ShiroUser shiroUser = getShiroUser();
				updatePartner.setPmUserId(shiroUser.getPid());
				
				clientPartner.updateProjectPartner(updatePartner);
				
				//添加机构审核记录
				PartnerApprovalRecord record = new PartnerApprovalRecord();
				record.setPartnerId(pid);
				record.setLoanId(loanId);
				record.setApprovalStatus(Constants.APPROVAL_STATUS_1);
				record.setSubmitApprovalTime(nowTime.toString());
				record.setReApplyReason(partner.getReApplyReason());
				record.setIsNotify(Constants.IS_NOTIFY_1);
				record.setNotifyType(Constants.NOTIFY_TYPE_2);
				clientPartner.addPartnerApprovalRecord(record);

				fillReturnJson(response, true, "操作成功!");
				return;
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
		}catch (Exception e) {
			logger.error("提交复议申请:" + e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>reApplyPartner clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 查询机构合作项目审批列表
	  * @param partner
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月21日 下午5:44:26
	 */
	@RequestMapping(value = "/partnerInfoList")
	public void partnerInfoList(ProjectPartner partner, HttpServletRequest request, HttpServletResponse response){
		logger.info("机构合作项目审批列表查询，入参："+partner);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProjectPartner> result = null;
		int total = 0;
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();

			//己申请的所有状态
			List<Integer> requestStatusList = new ArrayList<Integer>();
			requestStatusList.add(PartnerConstant.RequestStatus.APPLY_ING.getCode());
			requestStatusList.add(PartnerConstant.RequestStatus.APPLY_PASS.getCode());
			requestStatusList.add(PartnerConstant.RequestStatus.APPLY_NO_PASS.getCode());
			//partner.setRequestStatus(Constants.REQUEST_APPLY_STATUS_2);//已申请的项目
			partner.setRequestStatusList(requestStatusList);
			
			partner.setUserIds(getUserIds(request));//数据权限
			
			partner.setOrderByType(1);	//按提交审核时间倒序
			
			result = clientPartner.findAllProjectPartner(partner);
			total = clientPartner.findAllProjectPartnerCount(partner);
			
			
		} catch (Exception e) {
			logger.error("获取机构合作项目审批列表失败：" + e.getMessage());
	         e.printStackTrace();
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>partnerInfoList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 *  查询项目审核记录
	 * @param partnerId 项目机构id
	 * @param response
	 */
	@RequestMapping(value = "/getApprovalList")
	public void getApprovalList(Integer partnerId, HttpServletResponse response){
		List<PartnerApprovalRecord> list = new ArrayList<PartnerApprovalRecord>();
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			list = clientPartner.findAllPartnerApprovalRecord(partnerId);
		}catch (Exception e) {
			logger.error(">>>>>>getApprovalList error：" , e);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getApprovalList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		outputJson(list, response);
	}
	
	/**
	 * 关闭单据
	  * @param loanId
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月25日 上午9:53:05
	 */
	@RequestMapping(value = "/closedPartnerInfo")
	public void closedPartnerInfo(Integer pid, HttpServletRequest request, HttpServletResponse response){
		
		BaseClientFactory clientFactoryPartner = null;
		String uuid = UuidUtil.randomUUID();
		try {
			if(pid == null || pid.intValue() == 0){
				fillReturnJson(response, false, "操作失败，参数错误");
				return;
			}
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//验证状态
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner partnerTemp = clientPartner.findProjectPartnerInfo(queryPartner);
			if(partnerTemp == null || partnerTemp.getPid() == 0){
				fillReturnJson(response, false, "数据不存在");
				return;
			}else if(PartnerConstant.LoanStatus.LOAN_SUCCESS.getCode() ==partnerTemp.loanStatus){
				fillReturnJson(response, false, "放款成功不能操作");
				return;
			}else if(!(0 == partnerTemp.loanStatus 
					||PartnerConstant.LoanStatus.NO_APPLY.getCode() ==partnerTemp.loanStatus
					|| PartnerConstant.LoanStatus.LOAN_FAIL.getCode() ==partnerTemp.loanStatus
					|| PartnerConstant.LoanStatus.BACK.getCode() ==partnerTemp.loanStatus)){
				fillReturnJson(response, false, "放款状态为未申请，失败，驳回才能操作");
				return;
			}
			
			
			ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
			projectPartnerDto.setLoanId(partnerTemp.getLoanId());
			projectPartnerDto.setPid(partnerTemp.getPid());
			projectPartnerDto.setPartnerNo(partnerTemp.getPartnerNo());
			
			JSONObject resultJson = null;
			String msg="";
			
			
			//-----------------------------华丽的分割线-------------------测试-----------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				
				resultJson = new JSONObject();
				resultJson.put("err_code", 0);
				resultJson.put("err_msg", "ok");
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				dataJson.put("err_msg", "ok");
				resultJson.put("data", dataJson);
				
			}else{
				
				//-----------------------------华丽的分割线-------------------正式-----------------------------------
				if(PartnerConstant.PARTNER_TL.equals(projectPartnerDto.getPartnerNo())){
					resultJson = tLPartnerApiImpl.close(projectPartnerDto,request);
				}else if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
					/*JSONObject param = new JSONObject();
					param.put("loan_id", projectPartnerDto.getLoanId());
					param.put("add_user", XIAOYING_PARAM_ADD_USER);
					
					logger.info(">>>>>>closedPartnerInfo 加密前参数："+param.toString());
					
					List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.initConnection();
					String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_CLOSE_APPLY, paramMap, "UTF-8");
					
					if(!StringUtil.isBlank(result)){
						String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
						logger.info("closedPartnerInfo-关闭单据请求返回结果resContent："+resContent);
						resultJson = JSON.parseObject(resContent);
					}*/
					
					resultJson = xYPartnerApiImpl.close(projectPartnerDto,request);
				}else if(PartnerConstant.PARTNER_NYYH.equals(projectPartnerDto.getPartnerNo())){
					//南粤银行，关闭单据，撤消出帐通知
					//出帐通知
					projectPartnerDto.setLoanRemark("撤销未出账合同");
					resultJson = daJuPartnerApiImpl.loanApply(uuid, projectPartnerDto,false, request);
				}else if(PartnerConstant.PARTNER_DR.equals(projectPartnerDto.getPartnerNo())){
					//点融直接关闭
					resultJson = new JSONObject();
					resultJson.put("err_code", 0);
					resultJson.put("err_msg", "ok");
					JSONObject dataJson = new JSONObject();
					dataJson.put("err_code", 0);
					dataJson.put("err_msg", "ok");
					resultJson.put("data", dataJson);
				}else if(PartnerConstant.PARTNER_HNBX.equals(projectPartnerDto.getPartnerNo())){
					//华安保险直接关闭
					resultJson = new JSONObject();
					resultJson.put("err_code", 0);
					resultJson.put("err_msg", "ok");
					JSONObject dataJson = new JSONObject();
					dataJson.put("err_code", 0);
					dataJson.put("err_msg", "ok");
					resultJson.put("data", dataJson);
				}else{
					fillReturnJson(response, false, "该机构不支持此服务方法");
					return ;
				}
			}
			
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			
 
			int errCode = -1;
			JSONObject dataJson = null;
			if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
				
				if(resultJson.get("err_code")!= null){
					errCode =resultJson.getIntValue("err_code");	
				}
				msg =resultJson.getString("err_msg");	
			}else{
				dataJson = resultJson.getJSONObject("data");
				if(dataJson.get("err_code") != null){
					errCode =dataJson.getIntValue("err_code");
				}
				
				msg =resultJson.getString("msg");	
			}
			
			if(errCode == Constants.ERR_CODE_0){
				ProjectPartner updateProjectPartner = new ProjectPartner();
				updateProjectPartner.setPid(pid);
				updateProjectPartner.setIsClosed(PartnerConstant.IsClosed.HAS_CLOSED.getCode());
				
				ShiroUser shiroUser = getShiroUser();
				updateProjectPartner.setPmUserId(shiroUser.getPid());
				
				//修改
				clientPartner.updateProjectPartner(updateProjectPartner);
				fillReturnJson(response, true, "操作成功!");
				return;
			}else{
				fillReturnJson(response, false, "操作失败，"+msg+"!");
				return ;
			}
 
		}catch (Exception e) {
			logger.error(">>>>>>closedPartnerInfo error" ,e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>closedPartnerInfo clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	/**
	 * 确认要款（小赢）
	 * @param loanId	小赢id
	 * @param confirmLoanReason 理由
	 */
	@RequestMapping(value = "/confirmLoan")
	public void confirmLoan(String loanId,String confirmLoanReason ,  HttpServletRequest request, HttpServletResponse response){
		int errCode =-1;
		BaseClientFactory clientFactoryPartner = null;
		try {
			if(!StringUtil.isBlank(loanId)){
				clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
				ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
				
				//验证状态
				ProjectPartner queryPartner = new ProjectPartner(); 
				queryPartner.setLoanId(loanId);
				ProjectPartner partnerTemp = clientPartner.findProjectPartnerInfo(queryPartner);
				if(partnerTemp == null || partnerTemp.getPid() == 0){
					fillReturnJson(response, false, "数据不存在");
					return;
				}else if(Constants.APPROVAL_STATUS_2 !=partnerTemp.getApprovalStatus()){
					fillReturnJson(response, false, "必须是审核通过才能操作");
					return;
				}else if(!(Constants.LOAN_STATUS_1 == partnerTemp.getLoanStatus()
							|| 0 == partnerTemp.getLoanStatus())){
					fillReturnJson(response, false, "必须是未申请过放款才能操作");
					return;
				}
				
				Timestamp time = new Timestamp(new Date().getTime());
				ProjectPartner updatePartner = new ProjectPartner();
				updatePartner.setPid(partnerTemp.getPid());
				updatePartner.setLoanId(loanId);
				updatePartner.setConfirmLoanRequestTime(time.toString());
				updatePartner.setConfirmLoanReason(confirmLoanReason);
				
				JSONObject resultJson = null;
				
				//-----------------------------华丽的分割线-------------------测试-----------------------------------
				if("true".equals(IS_PARTNER_API_DEBUG)){
					resultJson = new JSONObject();
					resultJson.put("err_code", 0);
					resultJson.put("err_msg", "ok");
					
				}else{
					//-----------------------------华丽的分割线-------------------正式-----------------------------------
					
/*					JSONObject param = new JSONObject();
					param.put("loan_id", loanId);
					param.put("add_user", XIAOYING_PARAM_ADD_USER);
					param.put("remark", confirmLoanReason);
					
					logger.info(">>>>>>confirmLoan确认要款-加密前参数："+param.toString());
					
					List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
					
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.initConnection();
					String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_REQUEST_LOAN, paramMap, "UTF-8");
					if(!StringUtil.isBlank(result)){
						String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
						logger.info("confirmLoan-确认要款-resContent:"+resContent);
						JSONObject respMap = JSON.parseObject(resContent);
						errCode = respMap.getIntValue("err_code");
					} */
					
					
					ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
					projectPartnerDto.setLoanId(loanId);
					projectPartnerDto.setConfirmLoanReason(confirmLoanReason);
					resultJson = xYPartnerApiImpl.confirmLoan(projectPartnerDto, request);
				}
				
				
				if(resultJson == null){
					fillReturnJson(response, false, "网络请求失败,请重新操作!");
					return;
				}
				
				
				errCode =resultJson.getIntValue("err_code");
				
				//成功
				if(errCode == Constants.ERR_CODE_0){
					//放款申请未申请
					updatePartner.setLoanStatus(Constants.LOAN_STATUS_1);
					updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
					
					ShiroUser shiroUser = getShiroUser();
					updatePartner.setPmUserId(shiroUser.getPid());
					
					clientPartner.updateProjectPartner(updatePartner);
					
					fillReturnJson(response, true, "操作成功!");
					return;
				}else{
					fillReturnJson(response, false, "操作失败,请重新操作!");
					return;
				}
			}else{
				fillReturnJson(response, false, "操作失败,请重新操作!");
				return;
			}
		}catch (Exception e) {
			logger.error(">>>>>>confirmLoan error" ,e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>confirmLoan clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	/**
	 * 复议确认要款（小赢）
	 * @param loanId	小赢id
	 * @param confirmLoanReason 理由
	 */
	@RequestMapping(value = "/reConfirmLoan")
	public void reConfirmLoan(String loanId,String confirmLoanReason ,  HttpServletRequest request, HttpServletResponse response){
		int errCode =-1;
		BaseClientFactory clientFactoryPartner = null;
		try {
			if(!StringUtil.isBlank(loanId)){
				clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
				ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
				
				//验证状态
				ProjectPartner queryPartner = new ProjectPartner(); 
				queryPartner.setLoanId(loanId);
				ProjectPartner partnerTemp = clientPartner.findProjectPartnerInfo(queryPartner);
				if(partnerTemp == null || partnerTemp.getPid() == 0){
					fillReturnJson(response, false, "数据不存在");
					return;
				}else if(Constants.APPROVAL_STATUS_2 !=partnerTemp.getApprovalStatus()){
					fillReturnJson(response, false, "必须是审核通过才能操作");
					return;
				}else if(!(Constants.LOAN_STATUS_1 == partnerTemp.getLoanStatus() 
						|| 0 == partnerTemp.getLoanStatus())){
					fillReturnJson(response, false, "必须是未申请过放款才能操作");
					return;
				}else if(Constants.CONFIRM_LOAN_STATUS_4 == partnerTemp.getConfirmLoanStatus()){
					fillReturnJson(response, false, "复议确认要款只能提交一次且己经审核不通过");
					return;
				}else if(!(Constants.CONFIRM_LOAN_STATUS_1 == partnerTemp.getConfirmLoanStatus()
						 || 0 == partnerTemp.getConfirmLoanStatus())){
					fillReturnJson(response, false, "确认要款状态是未发送才能操作");
					return;
				}
				
				Timestamp time = new Timestamp(new Date().getTime());
				ProjectPartner updatePartner = new ProjectPartner();
				updatePartner.setPid(partnerTemp.getPid());
				updatePartner.setLoanId(loanId);
				updatePartner.setConfirmLoanRequestTime(time.toString());
				updatePartner.setConfirmLoanReason(confirmLoanReason);
				
				JSONObject resultJson = null;
				
				//-----------------------------华丽的分割线-------------------测试-----------------------------------
				if("true".equals(IS_PARTNER_API_DEBUG)){
					resultJson = new JSONObject();
					resultJson.put("err_code", 0);
					resultJson.put("err_msg", "ok");
				}else{
					//-----------------------------华丽的分割线-------------------正式-----------------------------------
				
/*					JSONObject param = new JSONObject();
					param.put("loan_id", loanId);
					param.put("add_user", XIAOYING_PARAM_ADD_USER);
					param.put("remark", confirmLoanReason);
					
					logger.info(">>>>>>reConfirmLoan复议确认要款-加密前参数："+param.toString());
					
					List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
					
					HttpUtils httpUtils = new HttpUtils();
					httpUtils.initConnection();
					String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_REQUEST_LOAN_REC, paramMap, "UTF-8");
					if(!StringUtil.isBlank(result)){
						String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
						
						logger.info("reConfirmLoan-复议确认要款-resContent:"+resContent);
						
						JSONObject respMap = JSON.parseObject(resContent);
						
						JSONObject dataJson = null;
						errCode = respMap.getIntValue("err_code");
						if(errCode ==  Constants.ERR_CODE_0){
							dataJson = respMap.getJSONObject("data");
							loanId = dataJson.getString("loan_id");
						}
					}*/
					
					ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
					projectPartnerDto.setLoanId(loanId);
					projectPartnerDto.setConfirmLoanReason(confirmLoanReason);
					
					resultJson = xYPartnerApiImpl.reConfirmLoan(projectPartnerDto, request);
				}
				
				if(resultJson == null){
					fillReturnJson(response, false, "网络请求失败,请重新操作!");
					return;
				}				
				
				//关闭成功
				if(errCode == Constants.ERR_CODE_0){
					updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_2);
					
					ShiroUser shiroUser = getShiroUser();
					updatePartner.setPmUserId(shiroUser.getPid());
					
					clientPartner.updateProjectPartner(updatePartner);
					
					//添加机构审核记录
					PartnerApprovalRecord record = new PartnerApprovalRecord();
					record.setPartnerId(updatePartner.getPid());
					record.setLoanId(loanId);
					record.setApprovalStatus(Constants.APPROVAL_STATUS_1);
					record.setSubmitApprovalTime(time.toString());
					record.setReApplyReason(confirmLoanReason);
					record.setIsNotify(Constants.IS_NOTIFY_1);
					record.setNotifyType(Constants.NOTIFY_TYPE_3);
					clientPartner.addPartnerApprovalRecord(record);
					
					fillReturnJson(response, true, "操作成功!");
					return;
				}else{
					fillReturnJson(response, false, "操作失败,请重新操作!");
					return;
				}
			}else{
				fillReturnJson(response, false, "操作失败,请重新操作!");
				return;
			}
		}catch (Exception e) {
			logger.error("reConfirmLoan error" ,e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>reConfirmLoan clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	
	
	/**
	 * 跳转放款页面列表
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月18日 下午4:05:23
	 */
	@RequestMapping(value = "/loanIndex")
	public String loanIndex(){
		return "partner/loan_index";
	}
	
	/**
	 * 查询机构合作项目放款列表
	  * @param partner
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月21日 下午5:44:26
	 */
	@RequestMapping(value = "loanInfoList")
	public void loanInfoList(ProjectPartner partner, HttpServletRequest request, HttpServletResponse response){
		logger.info("机构合作项目放款列表查询，入参："+partner);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProjectPartner> result = null;
		int total = 0;
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			partner.setRequestStatus(Constants.REQUEST_APPLY_STATUS_2);//已申请的项目
			partner.setApprovalStatus(Constants.APPROVAL_STATUS_2);//审核通过
			partner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3); //确认要款审核通过
			partner.setIsClosed(1);//未关闭
			partner.setUserIds(getUserIds(request));//数据权限
			
			partner.setOrderByType(2);	//按提交放款时间倒序
			
			result = clientPartner.findAllProjectPartner(partner);
			total = clientPartner.findAllProjectPartnerCount(partner);
		} catch (Exception e) {
			logger.error(">>>>>>loanInfoList error：" + e.getMessage());
	         e.printStackTrace();
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>loanInfoList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 * 放款申请（小赢/统联/南粤银行）
	 * @param loanId
	 * @param projectId
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/sendLoan")
	public void sendLoan(Integer pid , HttpServletRequest request, HttpServletResponse response){
		BaseClientFactory clientFactoryPartner = null;
		String uuid = UuidUtil.randomUUID();
		try {			
			
			String loanJusticeFiles = request.getParameter("loanJusticeFiles");			
			String loanBlankFiles = request.getParameter("loanBlankFiles");			
			String loanOtherFiles = request.getParameter("loanOtherFiles");		
			
			//确认借款天数和确认借款金额
			String confirmLoanDays = request.getParameter("confirmLoanDays");		
			String confirmLoanMoney = request.getParameter("confirmLoanMoney");		
			//申请放款日期
			String applyLoanDate = request.getParameter("applyLoanDate"); 
			//放款备注
			String loanRemark = request.getParameter("loanRemark"); 
			
			if(pid == null){
				fillReturnJson(response, false, "操作失败,参数不存在!");
				return;
			}
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//查询数据
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setPid(pid);
			ProjectPartner partnerTemp = clientPartner.findProjectPartnerInfo(queryPartner);
			if(partnerTemp == null || partnerTemp.getPid() == 0){
				fillReturnJson(response, false, "操作失败,数据不存在!");
				return;
			}else if(Constants.CONFIRM_LOAN_STATUS_3 != partnerTemp.getConfirmLoanStatus() ){
				fillReturnJson(response, false, "操作失败,确认要款状态必须是审核通过才能操作!");
				return;
			}else if(!( partnerTemp.getLoanStatus() == 0
					||  Constants.LOAN_STATUS_1 == partnerTemp.getLoanStatus() 
					||  Constants.LOAN_STATUS_5 ==partnerTemp.getLoanStatus()
					||  Constants.LOAN_STATUS_6 ==partnerTemp.getLoanStatus())){
				fillReturnJson(response, false, "操作失败,只有未申请、放款失败、驳回才能操作!");
				return;
			}
			
			//查询些项目是否己经有己申请放款或者放过款的单
			queryPartner = new ProjectPartner();
			queryPartner.setProjectId(partnerTemp.getProjectId());
			queryPartner.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());	//未关闭
			queryPartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_PASS.getCode());	//审核通过
			
			List<ProjectPartner> tempPartnerList = clientPartner.findProjectPartnerInfoList(queryPartner);
			
			if(!org.springframework.util.CollectionUtils.isEmpty(tempPartnerList)){
				for (ProjectPartner indexObj : tempPartnerList) {
					if(indexObj.getPid() != pid){
						//判断是否己经放款过
						if(indexObj.getLoanStatus() > 1){
							fillReturnJson(response, false, "操作失败,该项目己存在放款申请的数据，不能重复操作!");
							return;
						}
					}
				}
			}
			
			JSONObject resultJson = null;
			String msg="";
			
			ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
			projectPartnerDto.setLoanId(partnerTemp.getLoanId());
			projectPartnerDto.setPid(partnerTemp.getPid());
			projectPartnerDto.setPartnerNo(partnerTemp.getPartnerNo());
			projectPartnerDto.setProjectId(partnerTemp.getProjectId());
			//放款备注
			projectPartnerDto.setLoanRemark(loanRemark);
			
			
			//-----------------------------华丽的分割线-------------------测试-----------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				
				resultJson = new JSONObject();
				resultJson.put("err_code", 0);
				resultJson.put("err_msg", "ok");
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", partnerTemp.getLoanId());
				dataJson.put("err_code", 0);
				dataJson.put("err_msg", "ok");
				
				resultJson.put("data", dataJson);
				
				
				
			}else{
				//-----------------------------华丽的分割线-------------------正式-----------------------------------
				
				if(PartnerConstant.PARTNER_TL.equals(partnerTemp.getPartnerNo())){
					//判断放款有效期是否过期
					if(StringUtil.isBlank(partnerTemp.getLoanEffeDate()) || 
							DateUtils.secondsDifference(DateUtils.stringToDate(partnerTemp.getLoanEffeDate()+" 23:59:59"),new Date()) > 0){
						fillReturnJson(response, false, "操作失败,放款有效期己过期!");
						return ;
					}
					resultJson = tLPartnerApiImpl.loanApply(projectPartnerDto,request);
				}else if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
					
					projectPartnerDto.setConfirmLoanMoney(Double.parseDouble(confirmLoanMoney));
					projectPartnerDto.setConfirmLoanDays(Integer.parseInt(confirmLoanDays));
					projectPartnerDto.setLoanJusticeFiles(loanJusticeFiles);
					projectPartnerDto.setLoanBlankFiles(loanBlankFiles);
					projectPartnerDto.setLoanOtherFiles(loanOtherFiles);
					
					resultJson = xYPartnerApiImpl.loanApply(projectPartnerDto, request);
					
					
				}else if(PartnerConstant.PARTNER_DR.equals(projectPartnerDto.getPartnerNo())){
					
					//判断申请放款日期是否是今天
					if(StringUtil.isBlank(applyLoanDate) || !applyLoanDate.equals(DateUtils.getCurrentDate())){
						fillReturnJson(response, false, "操作失败,申请放款日期必须是今天!");
						return ;
					}
					
					//通知上标
					resultJson = dRPartnerApiImpl.notifyShelf(projectPartnerDto, request);
				}else if(PartnerConstant.PARTNER_NYYH.equals(projectPartnerDto.getPartnerNo())){
					//出帐通知
					resultJson = daJuPartnerApiImpl.loanApply(uuid, projectPartnerDto,true, request);
				}
			}
			
 			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
 
			JSONObject dataJson = null;
			int errCode = -1;
			if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
				if(resultJson.get("err_code")!= null){
					errCode =resultJson.getIntValue("err_code");	
				}
				msg =resultJson.getString("err_msg");	
			}else{
				dataJson = resultJson.getJSONObject("data");
				if(dataJson.get("err_code") != null){
					errCode =dataJson.getIntValue("err_code");
				}
				msg =resultJson.getString("msg");	
			}
			
			
			if(errCode == Constants.ERR_CODE_0){
				Timestamp nowTime = new Timestamp(new Date().getTime());
				ProjectPartner updateProjectPartner = new ProjectPartner();
				
				updateProjectPartner.setPid(pid);
				updateProjectPartner.setLoanTime(nowTime.toString());
				updateProjectPartner.setLoanStatus(Constants.LOAN_STATUS_2);//申请中
				
				ShiroUser shiroUser = getShiroUser();
				updateProjectPartner.setPmUserId(shiroUser.getPid());
				
				if(PartnerConstant.PARTNER_DR.equals(projectPartnerDto.getPartnerNo())){
					updateProjectPartner.setApplyLoanDate(applyLoanDate);
					
				}else{
					//存在则追加文件资料
					if(!StringUtil.isBlank(loanJusticeFiles)){
						loanJusticeFiles = StringUtil.isBlank(partnerTemp.getLoanJusticeFiles())?
								loanJusticeFiles:partnerTemp.getLoanJusticeFiles()+","+loanJusticeFiles;
						updateProjectPartner.setLoanJusticeFiles(loanJusticeFiles);
					}
					if(!StringUtil.isBlank(loanJusticeFiles)){
						loanBlankFiles = StringUtil.isBlank(partnerTemp.getLoanBlankFiles())?
								loanBlankFiles:partnerTemp.getLoanBlankFiles()+","+loanBlankFiles;
						updateProjectPartner.setLoanBlankFiles(loanBlankFiles);
					}
					if(!StringUtil.isBlank(loanJusticeFiles)){
						loanOtherFiles = StringUtil.isBlank(partnerTemp.getLoanOtherFiles())?
								loanOtherFiles:partnerTemp.getLoanOtherFiles()+","+loanOtherFiles;
						updateProjectPartner.setLoanOtherFiles(loanOtherFiles);
					}
					if(!StringUtil.isBlank(confirmLoanDays)){
						updateProjectPartner.setConfirmLoanDays(Integer.parseInt(confirmLoanDays.trim()));
					}
					if(!StringUtil.isBlank(confirmLoanMoney)){
						updateProjectPartner.setConfirmLoanMoney(Double.parseDouble(confirmLoanMoney.trim()));
					}
				}
				
				if(!StringUtil.isBlank(projectPartnerDto.getLoanRemark())){
					updateProjectPartner.setLoanRemark(projectPartnerDto.getLoanRemark());
				}
				
				//修改放款请求数据
				clientPartner.updateProjectPartner(updateProjectPartner);
				
				
				fillReturnJson(response, true, "操作成功!");
				return;
			}else{
				fillReturnJson(response, false, "操作失败，"+msg+"!");
				return ;
			}
		}catch (Exception e) {
			logger.error(">>>>>>sendLoan error" , e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>sendLoan clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	

	
	/**
	 * 跳转还款回购页面列表
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月18日 下午4:05:23
	 */
	@RequestMapping(value = "/repaymentIndex")
	public String repaymentIndex(){
		return "partner/repayment_index";
	}
	
	/** 
	 * 查询机构合作项目还款回购列表
	  * @param partner
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月21日 下午5:44:26
	 */
	@RequestMapping(value = "repaymentList") 
	public void repaymentList(ProjectPartner partner, HttpServletRequest request, HttpServletResponse response){
		logger.info("机构合作项目还款回购列表查询，入参："+partner);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProjectPartner> result = null;
		int total = 0;
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			partner.setRequestStatus(Constants.REQUEST_APPLY_STATUS_2);//已申请的项目
			partner.setApprovalStatus(Constants.APPROVAL_STATUS_2);//审核通过
			partner.setLoanStatus(Constants.LOAN_STATUS_4);//放款成功
			partner.setUserIds(getUserIds(request));//数据权限
			
			partner.setOrderByType(3);	//按提交还款时间倒序
			
			partner.setIsClosed(1);//未关闭
			result = clientPartner.findAllProjectPartner(partner);
			total = clientPartner.findAllProjectPartnerCount(partner);
		} catch (Exception e) {
			logger.error(">>>>>>repaymentList error：" , e);
	         e.printStackTrace();
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>repaymentList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 * 上传还款回购凭证
	  * 
	  * @author: Administrator
	  * @date: 2016年3月25日 下午4:49:41
	 */
	@RequestMapping(value = "/uploadRepaymentDocument")
	public void uploadRepaymentDocument(HttpServletRequest request, HttpServletResponse response){
		Json j = super.getSuccessObj();
		String filePath = "";
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			Map<String, Object> map = FileUtil.processFileUpload(request, response, "partner", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			ProjectPartner partner = new ProjectPartner();
			filePath = (String) map.get("path");
			partner.setRepaymentVoucherPath(filePath);//凭证路径
			
			List items = (List) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String fieldName = item.getFieldName();
				if (item.isFormField()) {
					if ("loanId".equals(fieldName)) {
						String loanId = ParseDocAndDocxUtil.getStringCode(item);
						partner.setLoanId(loanId);
					} else if ("repaymentRepurchaseType".equals(fieldName)) {
						String repaymentRepurchaseType = ParseDocAndDocxUtil.getStringCode(item);
						partner.setRepaymentRepurchaseType(StringUtils.isEmpty(repaymentRepurchaseType) ? 0 : Integer.parseInt(repaymentRepurchaseType));
					} else if("projectId".equals(fieldName)){
						String projectId = ParseDocAndDocxUtil.getStringCode(item);
						partner.setProjectId(StringUtils.isEmpty(projectId) ? 0 : Integer.parseInt(projectId));
					}
				}
			}
			//验证状态
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setProjectId(partner.getProjectId());
			ProjectPartner tempPartner =clientPartner.findProjectPartnerInfo(queryPartner);
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在!");
				return;
			}else if(!(tempPartner.getRepaymentRepurchaseStatus() == 0
						||Constants.REPAYMENT_REPURCHASE_STATUS_1 ==tempPartner.getRepaymentRepurchaseStatus() 
						|| Constants.REPAYMENT_REPURCHASE_STATUS_4 ==tempPartner.getRepaymentRepurchaseStatus())){
				fillReturnJson(response, false, "只有未申请和未收到才能上传凭证");
				return;				
			}
			
			clientPartner.updateRepaymentInfo(partner);//修改还款回购信息
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "上传成功!");
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改息费信息:" + e.getMessage());
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>uploadRepaymentDocument clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		j.getHeader().put("path",filePath);
		
		outputJson(j, response);
	}
	
	/**
	 * 还款、回购申请(小赢、统联)
	 * @param pid	id
	 * @param repaymentRepurchaseType  还款文件
	 * @param repaymentVoucherPath	  回购文件
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/repaymentApply")
	public void repaymentApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request, HttpServletResponse response){
		int errCode =-1;
		BaseClientFactory clientFactoryPartner = null;
		try {
				clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
				ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
				
				if(projectPartnerDto == null || projectPartnerDto.getPid() == 0){
					fillReturnJson(response, false, "操作失败,参数错误！");
					return;
				}
				
				//查询项目上传凭证信息
				ProjectPartner queryPartner = new ProjectPartner(); 
				queryPartner.setPid(projectPartnerDto.getPid()); 
				ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
				
				if(tempPartner == null || tempPartner.getPid() == 0 ){
					fillReturnJson(response, false, "操作失败,数据不存在！");
					return;
				}
				
				if(Constants.REPAYMENT_REPURCHASE_STATUS_2 == tempPartner.getRepaymentRepurchaseStatus()
						|| Constants.REPAYMENT_REPURCHASE_STATUS_3 == tempPartner.getRepaymentRepurchaseStatus()
					    || Constants.REPAYMENT_REPURCHASE_STATUS_5 == tempPartner.getRepaymentRepurchaseStatus()){
					fillReturnJson(response, false, "操作失败,只有未申请和未收到才能提交申请！");
					return;
				}
				
				JSONObject resultJson = null;
				String msg="";
				projectPartnerDto.setPartnerNo(tempPartner.getPartnerNo());
				projectPartnerDto.setLoanId(tempPartner.getLoanId());
				projectPartnerDto.setProjectId(tempPartner.getProjectId());
				
				//-----------------------------华丽的分割线-------------------测试-----------------------------------
				if("true".equals(IS_PARTNER_API_DEBUG)){
					
					resultJson = new JSONObject();
					resultJson.put("err_code", 0);
					resultJson.put("err_msg", "ok");
					
					JSONObject dataJson = new JSONObject();
					dataJson.put("err_code", 0);
					dataJson.put("err_msg", "ok");
					
					resultJson.put("data", dataJson);
					
				}else{
					//-----------------------------华丽的分割线-------------------正式-----------------------------------
					if(PartnerConstant.PARTNER_TL.equals(projectPartnerDto.getPartnerNo())){
						resultJson = tLPartnerApiImpl.refundApply(projectPartnerDto,request);
						
					}else if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
						if(projectPartnerDto == null || StringUtil.isBlank(projectPartnerDto.getRepaymentVoucherPath())){
							fillReturnJson(response, false, "操作失败,请选择凭证文件！");
							return;
						}
						
/*						//请求参数拼接
						JSONObject param = new JSONObject();
						String loanId = tempPartner.getLoanId();
						param.put("loan_id", loanId);
						param.put("type", tempPartner.getRepaymentRepurchaseType());
						param.put("add_user", XIAOYING_PARAM_ADD_USER);
						param.put("repay_real_amount", tempPartner.getRefundLoanAmount());
						param.put("repay_real_name", tempPartner.getPayAcctName());
						param.put("bank_code", tempPartner.getPayBankCode());
						param.put("swiping_time", tempPartner.getRefundDate());
						
						JSONObject filesJson = new JSONObject();
						filesJson.put("repay_info", getFileJsonArray(projectPartnerDto.getRepaymentVoucherPath(), tempPartner.getProjectId(), getServerBaseUrl(request)));
						param.put("files", filesJson);
						
						logger.info(">>>>>>repaymentApply加密前参数："+param.toString());
						
						//加密请求参数
						List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
						
						HttpUtils httpUtils = new HttpUtils();
						httpUtils.initConnection();
						String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_SEND_LOAN_VONCHER, paramMap, "UTF-8");
						
						if(!StringUtil.isBlank(result)){
							String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
							logger.info("applyPartner-还款、回购申请返回结果resContent："+resContent);
							resultJson = JSON.parseObject(resContent);
						}*/
						
						projectPartnerDto.setRepaymentRepurchaseType(tempPartner.getRepaymentRepurchaseType());
						projectPartnerDto.setRefundLoanAmount(tempPartner.getRefundLoanAmount());
						projectPartnerDto.setPayAcctName(tempPartner.getPayAcctName());
						projectPartnerDto.setPayBankCode(tempPartner.getPayBankCode());
						projectPartnerDto.setRefundDate(tempPartner.getRefundDate());
						
						resultJson = xYPartnerApiImpl.refundApply(projectPartnerDto, request);
					}
				}
				
				if(resultJson == null){
					fillReturnJson(response, false, "网络请求失败,请重新操作!");
					return;
				}
				
				JSONObject dataJson = null;
				if(PartnerConstant.PARTNER_XY.equals(projectPartnerDto.getPartnerNo())){
					errCode =resultJson.getIntValue("err_code");	
					msg =resultJson.getString("err_msg");	
				}else{
					dataJson = resultJson.getJSONObject("data");
					errCode =dataJson.getIntValue("err_code");
					msg =resultJson.getString("msg");	
				}
				
				if(errCode == Constants.ERR_CODE_0){
					Timestamp nowTime = new Timestamp(new Date().getTime());
					//更新对象
					ProjectPartner updatePartner = new ProjectPartner();
					updatePartner.setPid(tempPartner.getPid());
					updatePartner.setUpdateTime(nowTime.toString());
					updatePartner.setRemark(projectPartnerDto.getRemark());
					if(!StringUtil.isBlank(projectPartnerDto.getRepaymentVoucherPath())){
						updatePartner.setRepaymentVoucherPath(projectPartnerDto.getRepaymentVoucherPath());
					}
					if(!StringUtil.isBlank(projectPartnerDto.getXiFeeVoucherPath())){
						updatePartner.setXiFeeVoucherPath(projectPartnerDto.getXiFeeVoucherPath());
					}
/*					if(projectPartnerDto.getRepaymentRepurchaseType() != 0 ){
						updatePartner.setRepaymentRepurchaseType(projectPartnerDto.getRepaymentRepurchaseType());
					}*/
					
					updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_2);//申请成功
					updatePartner.setRepaymentRepurchaseTime(nowTime.toString());//回款回购请求时间
					
					ShiroUser shiroUser = getShiroUser();
					updatePartner.setPmUserId(shiroUser.getPid());
					
					clientPartner.updateProjectPartner(updatePartner);		//修改还款回购信息
					fillReturnJson(response, true, "操作成功!");
					return;
				}else{
					fillReturnJson(response, false, "操作失败,"+msg+"!");
					return;
				}
		}catch (Exception e) {
			logger.error(">>>>>>repaymentApply error" ,e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>repaymentApply clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 跳转息费结算页面列表
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月18日 下午4:05:23
	 */
	@RequestMapping(value = "/xiFeeIndex")
	public String xiFeeIndex(){
		return "partner/xiFee_index";
	}
	
	/**
	 * 查询机构合作项目息费结算列表
	  * @param partner
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月21日 下午5:44:26
	 */
	@RequestMapping(value = "xiFeeList")
	public void xiFeeList(ProjectPartner partner, HttpServletRequest request, HttpServletResponse response){
		logger.info("机构合作项目息费结算列表查询，入参："+partner);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProjectPartner> result = null;
		int total = 0;
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			partner.setRequestStatus(Constants.REQUEST_APPLY_STATUS_2);//已申请的项目
			partner.setApprovalStatus(Constants.APPROVAL_STATUS_2);//审核通过
			partner.setLoanStatus(Constants.LOAN_STATUS_4);//放款成功
			partner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_3);//还款回购确认收到
			partner.setIsClosed(1);//未关闭
			partner.setUserIds(getUserIds(request));//数据权限
			result = clientPartner.findAllProjectPartner(partner);
			total = clientPartner.findAllProjectPartnerCount(partner);
		} catch (Exception e) {
			logger.error("获取机构合作项目息费结算列表失败：" + e.getMessage());
	         e.printStackTrace();
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>xiFeeList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 * 上传息费结算凭证
	  * 
	  * @author: Administrator
	  * @date: 2016年3月25日 下午4:43:09
	 */
	@RequestMapping(value = "uploadXiFeeDocument")
	public void uploadDocument(HttpServletRequest request, HttpServletResponse response){
		Json j = super.getSuccessObj();
		String filePath = "";
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			Map<String, Object> map = FileUtil.processFileUpload(request, response, "partner", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			ProjectPartner partner = new ProjectPartner();
			filePath = (String) map.get("path");
			partner.setXiFeeVoucherPath(filePath);//凭证路径
			
			List items = (List) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String fieldName = item.getFieldName();
				if (item.isFormField()) {
					if ("loanId".equals(fieldName)) {
						String loanId = ParseDocAndDocxUtil.getStringCode(item);
						partner.setLoanId(loanId);
					}else if("projectId".equals(fieldName)){
						String projectId = ParseDocAndDocxUtil.getStringCode(item);
						partner.setProjectId(StringUtils.isEmpty(projectId) ? 0 : Integer.parseInt(projectId));
					}
				}
			}
			//验证状态
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setProjectId(partner.getProjectId());
			ProjectPartner tempPartner =clientPartner.findProjectPartnerInfo(queryPartner);
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在!");
				return;
			}else if(!(tempPartner.getXiFeeStatus() == 0 || Constants.XIFEE_STATUS_1 ==tempPartner.getXiFeeStatus()
					|| Constants.XIFEE_STATUS_3 ==tempPartner.getXiFeeStatus())){
				fillReturnJson(response, false, "只有未发送和未到账才能上传凭证");
				return;				
			}
			
			clientPartner.updateXiFeeInfo(partner);//修改息费信息
			j.getHeader().put("success", true);
			j.getHeader().put("msg", "上传成功!");
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改息费信息:" + e.getMessage());
			}
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>uploadXiFeeDocument clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		j.getHeader().put("path",filePath);
		
		outputJson(j, response);
	}
	

	/**
	 * 获取息费结算列表(小赢) 获取后，同步数据
	  * 
	  * @author: Administrator
	  * @date: 2016年3月25日 下午4:48:21
	 */
	@RequestMapping(value = "getXiFeeList")
	public void getXiFeeList(HttpServletRequest request, HttpServletResponse response){
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//加密请求参数
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams("", XIAOYING_PARTNER_MD5);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.initConnection();
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_XIFEE_LIST, paramMap, "UTF-8");
			
			int errCode =0;
			
			JSONObject dataJson = null;
			//返回结果解析
			if(StringUtil.isBlank(result)){
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "网络请求失败,请重新操作!");
				outputJson(j, response);
				return;
			}
			
			String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			
			logger.info("getXiFeeList-获取息费结算列表返回结果resContent:"+resContent);
			
			JSONObject resJson = JSON.parseObject(resContent);
			dataJson = resJson.getJSONObject("data");
			errCode =  dataJson.getIntValue("err_code");
			
			if(errCode == Constants.ERR_CODE_0){//发送成功
				Timestamp time = new Timestamp(new Date().getTime());
				List<ProjectPartner> updatePartnerList = new ArrayList<ProjectPartner>();	//更新列表
				List<String> loadIds=new ArrayList<String>();
				Map<String,ProjectPartner> partnerMap = new HashMap<String, ProjectPartner>();
				//转化数据
				JSONArray jsonArray = dataJson.getJSONArray("list");
				
				if(jsonArray.size() == 0){
					fillReturnJson(response, true, "没有结算清单!");
					return;	
				}
				
				
				ProjectPartner partner = null;
				JSONObject objTemp = null;
				for (int i = 0; i < jsonArray.size(); i++) {
					objTemp  = jsonArray.getJSONObject(i);
					partner = new ProjectPartner();
					partner.setLoanId(objTemp.getString("loan_id"));
					partner.setLoanAmount(objTemp.getDoubleValue("loan_amount"));		//借款金额
					partner.setTotalCost(objTemp.getDoubleValue("total_cost"));			//总成本
					partner.setPremium(objTemp.getDoubleValue("premium"));				//保费
					partner.setGuaranteeFee(objTemp.getDoubleValue("guarantee_fee"));	//担保费
					partner.setXiFeeRequestTime(time.toString());		//息费结算请求时间
					partner.setXiFeeStatus(Constants.XIFEE_STATUS_1);	//己发送
					loadIds.add(partner.getLoanId());
					partnerMap.put(partner.getLoanId(), partner);
				}
				
				//验证
				ProjectPartner queryPartner=new ProjectPartner();
				queryPartner.setLoanIds(loadIds);
				List<ProjectPartner> tempList = clientPartner.findProjectPartnerInfoList(queryPartner);
				if(tempList.isEmpty()){
					fillReturnJson(response, false, "数据不存在");
					return;		
				}
				for (ProjectPartner temp : tempList) {
					if(Constants.XIFEE_STATUS_4 != temp.getXiFeeStatus()){
						updatePartnerList.add(partnerMap.get(temp.getLoanId()));
					}
				}
				
				//执行批量更新
				if(!updatePartnerList.isEmpty()){
					clientPartner.updateXiFeeInfos(updatePartnerList);
					logger.info("getXiFeeList updateList.size:"+updatePartnerList.size());
				}else{
					fillReturnJson(response, true, "己到帐不能更新结算清单!");
					return;	
				}
				
				fillReturnJson(response, true, "更新成功!");
				return;	
			}else if(errCode == Constants.ERR_CODE_1){
				fillReturnJson(response, false, "发送失败,请重新操作!");
				return;	
			}
			
		} catch (Exception e) {
			logger.error("getXiFeeList error",e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
			return;	
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getXiFeeList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	/**
	 * 批量获取息费到款确认状态（小赢）
	  * 
	  * @author: Administrator
	  * @date: 2016年3月28日 下午6:59:10
	 */
	@RequestMapping(value = "batchXiFeeStatus")
	public void batchXiFeeStatus(String loanIds,HttpServletRequest request, HttpServletResponse response){
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//loanid数组
			String[] loandIdArr = loanIds.split(",");
			
			//验证
			ProjectPartner queryPartner=new ProjectPartner();
			queryPartner.setLoanIds(Arrays.asList(loandIdArr));
			List<ProjectPartner> tempList = clientPartner.findProjectPartnerInfoList(queryPartner);
			
			if(tempList.isEmpty()){
				fillReturnJson(response, false, "数据不存在");
				return;		
			}
			for (ProjectPartner temp : tempList) {
				if(!(Constants.XIFEE_STATUS_2 == temp.getXiFeeStatus() 
						|| Constants.XIFEE_STATUS_3 == temp.getXiFeeStatus()
						|| Constants.XIFEE_STATUS_5 == temp.getXiFeeStatus()) ){
					fillReturnJson(response, false, "只有已发送或者未到账才能操作");
					return;		
				}
			}
			
			//加密请求参数
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(JSON.toJSONString(loandIdArr,true), XIAOYING_PARTNER_MD5);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.initConnection();
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_BATCH_XIFEE_STATUS, paramMap, "UTF-8");
			
			//返回结果解析
			if(StringUtil.isBlank(result)){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;	
			}
			String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			
			logger.info("batchXiFeeStatus-获取息费到款确认状态返回结果resContent:"+resContent);
			
			int errCode =0;
			JSONObject dataJson = null;
			JSONObject respMap = JSON.parseObject(resContent);
			dataJson = respMap.getJSONObject("data");
			errCode =  dataJson.getIntValue("err_code");
 
			if(errCode == Constants.ERR_CODE_0){//发送成功
				
				Timestamp time = new Timestamp(new Date().getTime());
				ProjectPartner partner =null;
				List<ProjectPartner> updateList = new ArrayList<ProjectPartner>();	//更新列表
				JSONObject objTemp = null;
				//转化数据
				JSONArray jsonArray = dataJson.getJSONArray("list");
				
				for (int i = 0; i < jsonArray.size(); i++) {
					objTemp  = jsonArray.getJSONObject(i);
					partner = new ProjectPartner();
					partner.setLoanId(objTemp.getString("loan_id"));
					partner.setXiFeeResultTime(time.toString());		//息费结算确认时间
					if("0".equals(objTemp.getString("status"))){
						partner.setXiFeeStatus(Constants.XIFEE_STATUS_4); //己到帐
					}else if("1".equals(objTemp.getString("status"))){
						partner.setXiFeeStatus(Constants.XIFEE_STATUS_3); //未到帐
					}else if("99".equals(objTemp.getString("status"))){
						partner.setXiFeeStatus(Constants.XIFEE_STATUS_5); //数据不存在
					}
					updateList.add(partner);
				}
				//执行批量更新
				if(!updateList.isEmpty()){
					clientPartner.updateXiFeeInfos(updateList);
				}
				fillReturnJson(response, true, "操作成功!");
				return;	
			}else if(errCode == Constants.ERR_CODE_1){
				fillReturnJson(response, false, "发送失败,请重新操作!");
				return;	
			}
		} catch (Exception e) {
			logger.error("batchXiFeeStatus error",e);
			fillReturnJson(response, false, "网络请求失败,请重新操作!");
			return;	
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>projectList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	/**
	 * 发送批量息费结算指令
	  * 
	  * @author: Administrator
	  * @date: 2016年3月25日 下午4:47:32
	 */
	@RequestMapping(value = "sendXiFeeSettle")
	public void sendXiFeeSettle(String loanIds,HttpServletRequest request, HttpServletResponse response){
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//查询合作机构项目列表
			ProjectPartner query = new ProjectPartner();
			String[] loandIdArr = loanIds.split(",");
			query.setLoanIds(Arrays.asList(loandIdArr));
			List<ProjectPartner> resultList = clientPartner.findProjectPartnerInfoList(query);
			List<JSONObject> paramList = new ArrayList<JSONObject>();
			//请求参数拼接
			for(ProjectPartner partner:resultList){
				JSONObject param = new JSONObject();
				param.put("loan_id", partner.getLoanId());
				param.put("file", partner.getXiFeeVoucherPath());
				paramList.add(param);
			}
			//加密请求参数
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(JSON.toJSONString(paramList,true), XIAOYING_PARTNER_MD5);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.initConnection();
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_BATCH_SEND_XIFEE, paramMap, "UTF-8");
			
			if(StringUtil.isBlank(result)){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;	
			}
			//返回结果解析
			String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			
			int errCode =0;
			logger.info("sendXiFeeSettle-发送批量息费结算指令返回结果resContent:"+resContent);
			
			JSONObject respMap = JSON.parseObject(resContent);
			JSONObject dataJson = respMap.getJSONObject("data");
			errCode = dataJson.getIntValue("err_code");
			
			Timestamp time = new Timestamp(new Date().getTime());
			ProjectPartner partner = null;
			
			if(errCode == Constants.ERR_CODE_0){//发送成功
				
				List<ProjectPartner> updateList = new ArrayList<ProjectPartner>();	//更新列表
				JSONObject objTemp = null;
				//转化数据
				JSONArray jsonArray = dataJson.getJSONArray("list");
				
				for (int i = 0; i < jsonArray.size(); i++) {
					objTemp  = jsonArray.getJSONObject(i);
					partner = new ProjectPartner();
					partner.setLoanId(objTemp.getString("loan_id"));
					partner.setXiFeeRequestTime(time.toString());		//息费结算请求时间
					if("0".equals(objTemp.getString("status"))){
						partner.setXiFeeStatus(Constants.XIFEE_STATUS_2); //已发送
					}else if("1".equals(objTemp.getString("status"))){
						partner.setXiFeeStatus(Constants.XIFEE_STATUS_1); //未发送，参数错误
					}else if("99".equals(objTemp.getString("status"))){
						partner.setXiFeeStatus(Constants.XIFEE_STATUS_5); //数据不存在
					}
					updateList.add(partner);
				}
				//执行批量更新
				if(!updateList.isEmpty()){
					clientPartner.updateXiFeeInfos(updateList);
				}
				fillReturnJson(response, true, "操作成功!");
				return;	
			}else if(errCode == Constants.ERR_CODE_1){
				fillReturnJson(response, false, "发送失败,请重新操作!");
				return;	
			}
		} catch (Exception e) {
			logger.error("sendXiFeeSettle error",e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
			return;	
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>sendXiFeeSettle clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}

	
   
	/**
	 * 将前台的数据转换
	  * @param projectPartnerDto
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月18日 下午5:08:25
	 */
	private ProjectPartner convertBean(ProjectPartnerDto projectPartnerDto){
		ProjectPartner partner = new ProjectPartner();
		partner.setPid(projectPartnerDto.getPid());
		partner.setRequestFiles(projectPartnerDto.getRequestFiles());
		partner.setIsClosed(1);//未关闭
		partner.setProjectId(projectPartnerDto.getProjectId());
		partner.setLoanId(projectPartnerDto.getLoanId());
		partner.setPartnerNo(projectPartnerDto.getPartnerNo());
		partner.setRemark(projectPartnerDto.getRemark());
		partner.setPmUserId(projectPartnerDto.getPmUserId());
		partner.setReApplyReason(projectPartnerDto.getReApplyReason());
		partner.setLoanAmount(projectPartnerDto.getApplyMoney());
		partner.setApplyLoanDate(projectPartnerDto.getApplyLoanDate());
		
		//省市
		partner.setProvinceCode(projectPartnerDto.getProvinceCode());
		partner.setCityCode(projectPartnerDto.getCityCode());
		
		partner.setPaymentBank(projectPartnerDto.getPaymentBank());
		partner.setPaymentBankBranch(projectPartnerDto.getPaymentBankBranch());
		partner.setPaymentAcctName(projectPartnerDto.getPaymentAcctName());
		partner.setPaymentAcctNo(projectPartnerDto.getPaymentAcctNo());
		partner.setPaymentProvinceCode(projectPartnerDto.getPaymentProvinceCode());
		partner.setPaymentCityCode(projectPartnerDto.getPaymentCityCode());
		partner.setApplyDate(projectPartnerDto.getApplyDate());
		
		partner.setHouseProvinceCode(projectPartnerDto.getHouseProvinceCode());
		partner.setHouseCityCode(projectPartnerDto.getHouseCityCode());
		
		if(!StringUtil.isBlank(projectPartnerDto.getRequestFiles())){
			partner.setRequestFiles(projectPartnerDto.getRequestFiles());
		}
		
		
		if(PartnerConstant.PARTNER_DR.equals(projectPartnerDto.getPartnerNo()) || PartnerConstant.PARTNER_HNBX.equals(projectPartnerDto.getPartnerNo())){
			partner.setPayBankName(projectPartnerDto.getPayBankName());
			partner.setPayBankBranch(projectPartnerDto.getPayBankBranch());
			partner.setPayAcctName(projectPartnerDto.getPayAcctName());
			partner.setPayAcctNo(projectPartnerDto.getPayAcctNo());
			partner.setPayProvinceCode(projectPartnerDto.getPayProvinceCode());
			partner.setPayCityCode(projectPartnerDto.getPayCityCode());
			
			partner.setIsCreditLoan(projectPartnerDto.getIsCreditLoan());
			
			partner.setPartnerGrossRate(projectPartnerDto.getPartnerGrossRate());
			partner.setPartnerPushAccount(projectPartnerDto.getPartnerPushAccount());
		}
		
		partner.setPaymentBankPhone(projectPartnerDto.getPaymentBankPhone());
		partner.setLoanPeriodLimit(projectPartnerDto.getLoanPeriodLimit());
		partner.setPaymentBankLineNo(projectPartnerDto.getPaymentBankLineNo());
		partner.setIsCreditLoan(projectPartnerDto.getIsCreditLoan());
		
		
 
		
		return partner;
	}
	
	
	/**
	 * 查询合作项目相关附件上传
	 */
	@RequestMapping(value = "/getProjectFileList")
	public void getProjectFileList(Integer projectId,HttpServletRequest request , HttpServletResponse response) throws Exception{
		List<ProjectPartnerFile> fileList = new ArrayList<ProjectPartnerFile>();
		ProjectPartnerFile query = new ProjectPartnerFile();
		BaseClientFactory clientFactoryPartnerFile = null;
		try {
			String partnerNo = request.getParameter("partnerNo");
			String partnerId = request.getParameter("partnerId");
			String accessoryType = request.getParameter("accessoryType");
			if(!StringUtil.isBlank(partnerNo)){
				partnerNo = URLDecoder.decode(partnerNo, "UTF-8");
			}
			if(!StringUtil.isBlank(accessoryType)){
				query.setAccessoryType(accessoryType);
			}
			if(!StringUtil.isBlank(partnerId)){
				query.setPartnerId(Integer.parseInt(partnerId));
			}
			
			query.setProjectId(projectId);
			query.setPartnerNo(partnerNo);
			
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			fileList = clientPartnerFile.findAllProjectPartnerFile(query);
		}catch (Exception e) {
			logger.error(">>>>>>getProjectFileList errpr：",e);
		}finally {
			destroyFactory(clientFactoryPartnerFile);
		}
		outputJson(fileList, response);
	}
	
	
	/**
	 * 上传项目合作机构附件
	 */
	@RequestMapping(value = "uploadPartnerFile")
	public void uploadPartnerFile(HttpServletRequest request, HttpServletResponse response){
		BaseClientFactory clientFactoryPartnerFile = null;
		try {
			String filePath = "";
			String isUpdateFile= "";	//是否上传
			int fileSize = 0 ;
			String partnerId=""  ;			//机构项目合作id
			//是否更新机构息费文件
			String upload_refund_file_flag = null;
			int pid = 0;
			
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			Map<String, Object> map = FileUtil.processFileUpload(request, response, "partner", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			ProjectPartnerFile partnerFile = new ProjectPartnerFile();
			
			List items = (List) map.get("items");
			for (int i = 0; i < items.size(); i++) {
				FileItem item = (FileItem) items.get(i);
				String fieldName = item.getFieldName();
				if (item.isFormField()) {
					if ("accessoryType".equals(fieldName)) {
						String accessoryType = ParseDocAndDocxUtil.getStringCode(item);
						partnerFile.setAccessoryType(accessoryType);
					}if ("accessoryChildType".equals(fieldName)) {
						String accessoryChildType = ParseDocAndDocxUtil.getStringCode(item);
						partnerFile.setAccessoryChildType(accessoryChildType);
					}else if("projectId".equals(fieldName)){
						String projectId = ParseDocAndDocxUtil.getStringCode(item);
						partnerFile.setProjectId(StringUtils.isEmpty(projectId) ? 0 : Integer.parseInt(projectId));
					}else if("remark".equals(fieldName)){
						String remark = ParseDocAndDocxUtil.getStringCode(item);
						partnerFile.setRemark(remark);
					} else if("pid".equals(fieldName)){
						String pidTemp = ParseDocAndDocxUtil.getStringCode(item);
						partnerFile.setPid(StringUtils.isEmpty(pidTemp) ? 0 : Integer.parseInt(pidTemp));
					} else if("partnerNo".equals(fieldName)){
						String partnerNo = ParseDocAndDocxUtil.getStringCode(item);
						partnerFile.setPartnerNo(partnerNo);
					} else if("isUpdateFile".equals(fieldName)){
						
						isUpdateFile = ParseDocAndDocxUtil.getStringCode(item);
						if("1".equals(isUpdateFile)){
							filePath = (String) map.get("path");
							partnerFile.setFileUrl(filePath);	
						}
					}  else if("fileSize".equals(fieldName)){
						
						String fileSizeStr = ParseDocAndDocxUtil.getStringCode(item);
						//保留原有大小
						if(!StringUtil.isBlank(fileSizeStr)){
							partnerFile.setFileSize(Integer.parseInt(fileSizeStr));
						}
						if("1".equals(isUpdateFile)){
							filePath = (String) map.get("path");
							partnerFile.setFileUrl(filePath);	
						}
					} else if("upload_refund_file_flag".equals(fieldName)){
						upload_refund_file_flag = ParseDocAndDocxUtil.getStringCode(item);
					} else if("partnerId".equals(fieldName)){
						partnerId = ParseDocAndDocxUtil.getStringCode(item);
					}
				}else if("file".equals(fieldName)){
					
					fileSize = (int)item.getSize();
					if(!StringUtil.isBlank(item.getName())){
						String [] fileNameArray =item.getName().split("\\."); 
						partnerFile.setFileName(fileNameArray[0]);
						partnerFile.setFileType(fileNameArray[1]);
					}
				}
			}
			
			if("1".equals(isUpdateFile)){
				partnerFile.setFileSize(fileSize);
				partnerFile.setThirdFileUrl("");
			}else if(StringUtil.isBlank(isUpdateFile)){
				filePath = (String) map.get("path");
				partnerFile.setFileUrl(filePath);	
				partnerFile.setFileSize(fileSize);
				partnerFile.setThirdFileUrl("");
			}
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			partnerFile.setUpdateTime(nowTime.toString());
			if(partnerFile.getPid() >0){
				pid = partnerFile.getPid();
				partnerFile.setPartnerId(Integer.parseInt(partnerId));
				clientPartnerFile.updateProjectPartnerFile(partnerFile);	//修改
			}else{
				partnerFile.setStatus(Constants.COMM_YES);
				partnerFile.setPartnerId(Integer.parseInt(partnerId));
				pid = clientPartnerFile.addProjectPartnerFile(partnerFile);		//新增
			}
			
			//更新息费和还款凭证字段
			if("1".equals(upload_refund_file_flag) && !StringUtil.isBlank(partnerId) && !"0".equals(partnerId)){
				ProjectPartnerService.Client clientPartner = (ProjectPartnerService.Client)
						getClient(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
				ProjectPartner queryPartner = new ProjectPartner();
				queryPartner.setPid(Integer.parseInt(partnerId));
				
				ProjectPartner partnerTemp = clientPartner.findProjectPartnerInfo(queryPartner);
				
				if(PartnerConstant.PARTNER_TL.equals(partnerTemp.getPartnerNo())){
					ProjectPartner updatePartner = new ProjectPartner();
					updatePartner.setPid(queryPartner.getPid());
					updatePartner.setUpdateTime(nowTime.toString());
					
					String repaymentVoucherPath = partnerTemp.getRepaymentVoucherPath();
					String xiFeeVoucherPath = partnerTemp.getXiFeeVoucherPath();
					if(Constants.PARTNER_ACCESSORY_TYPE_F28.equals(partnerFile.getAccessoryChildType())){
						repaymentVoucherPath = StringUtil.isBlank(repaymentVoucherPath) ? ""+pid : repaymentVoucherPath+","+pid;
						updatePartner.setRepaymentVoucherPath(repaymentVoucherPath);
						
					}else if(Constants.PARTNER_ACCESSORY_TYPE_F29.equals(partnerFile.getAccessoryChildType())){
						xiFeeVoucherPath = StringUtil.isBlank(xiFeeVoucherPath) ? ""+pid : xiFeeVoucherPath+","+pid;
						updatePartner.setXiFeeVoucherPath(xiFeeVoucherPath);
					}
					clientPartner.updateProjectPartner(updatePartner);
				}
			}
			
			fillReturnJson(response, true, "操作成功!");
			return ;
			
		} catch (Exception e) {
			logger.error("uploadPartnerFile error",e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartnerFile != null) {
				try {
					clientFactoryPartnerFile.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>uploadPartnerFile clientFactoryPartnerFile destroy thrift error：",e);
				}
			}
		}
	}
	
	/**
	 * 删除项目附件
	 */
	@RequestMapping(value = "/deleteProjectFile")
	public void deleteProjectFile(Integer pid, HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartnerFile = null;
		try {
			Timestamp nowTime = new Timestamp(new Date().getTime());
			
			ProjectPartnerFile updateFile = new ProjectPartnerFile();
			updateFile.setPid(pid);
			updateFile.setUpdateTime(nowTime.toString());
			updateFile.setStatus(Constants.COMM_NO);
			
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			clientPartnerFile.updateProjectPartnerFile(updateFile);
			fillReturnJson(response, true, "操作成功!");
			return ;
		}catch (Exception e) {
			logger.error("deleteProjectFile error",e);
		}finally {
			if (clientFactoryPartnerFile != null) {
				try {
					clientFactoryPartnerFile.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>deleteProjectFile clientFactoryPartnerFile destroy thrift error：",e);
				}
			}
		}
	}
	
	/**
	 * 更新还款信息
	 */
	@RequestMapping(value = "/updatePartnerRefund")
	public void updatePartnerRefund(ProjectPartner updatePartner, HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			if(updatePartner == null || updatePartner.getPid() == 0){
				fillReturnJson(response, false, "参数错误!");
				return ;
			}
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(updatePartner.getPid());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			//判断还款状态
			if(Constants.REPAYMENT_REPURCHASE_STATUS_2 == tempPartner.getRepaymentRepurchaseStatus() ||
					Constants.REPAYMENT_REPURCHASE_STATUS_3 == tempPartner.getRepaymentRepurchaseStatus() ||
					Constants.REPAYMENT_REPURCHASE_STATUS_5 == tempPartner.getRepaymentRepurchaseStatus()){
				fillReturnJson(response, false, "还款中不允许操作");
				return ;
			}
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			//更新还款信息
			updatePartner.setUpdateTime(nowTime.toString());
			
			//设置还款总金额
			if(updatePartner.getRefundLoanAmount() > 0){
				updatePartner.setRefundTotalAmount(updatePartner.getRefundLoanAmount()+updatePartner.getRefundXifee());
			}
			
			clientPartner.updateProjectPartner(updatePartner);
			
			fillReturnJson(response, true, "操作成功!");
			return ;
		}catch (Exception e) {
			logger.error("updatePartnerRefund error",e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>updatePartnerRefund clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	
	/**
	 * 通过文件id查询文件
	 * @param pid
	 * @param response
	 */
	@RequestMapping(value="getProjectFileByPid")
	public void getProjectFileByPid(Integer pid,HttpServletResponse response){
		BaseClientFactory clientFactoryPartnerFile = null;
		try {
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setPid(pid);
			
			ProjectPartnerFile  file = new ProjectPartnerFile();
			List<ProjectPartnerFile> list =  clientPartnerFile.findAllProjectPartnerFile(query);
			if(list != null && list.size() > 0){
				file = list.get(0);
			}
			outputJson(file,response);
		}catch(Exception e){
			logger.error(">>>>>>getProjectFileByPid error",e);
		}finally {
			if (clientFactoryPartnerFile != null) {
				try {
					clientFactoryPartnerFile.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getProjectFileByPid clientFactoryPartnerFile destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 更新同步第三方贷款状态 （点融、南粤银行）
	 * @param pid	机构合作项目id
	 * @param includePostLoanStatus 是否包含贷中状态   true | false
	 * @param thirdSyncStatusType 同步类型  1：审批状态   2：还款状态
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "updteThirdLoanStatus")
	public void updteThirdLoanStatus(Integer pid, String includePostLoanStatus,Integer thirdSyncStatusType,HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		String uuid = UuidUtil.randomUUID();
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			//同步类型（默认为审批）
			if(thirdSyncStatusType == null){
				thirdSyncStatusType = PartnerConstant.ThirdSyncStatusType.APPROVAL_STATUS.getCode();
			}
			
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在");
				return ;
			}else if(tempPartner.getApprovalStatus() == 0 ){
				fillReturnJson(response, false, "该数据未提交申请，不允许操作");
				return ;
			}
			
			
			if(thirdSyncStatusType == PartnerConstant.ThirdSyncStatusType.APPROVAL_STATUS.getCode()){
				if(tempPartner.getApprovalStatus() != 1  && PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
					fillReturnJson(response, false, "审核状态必须是申请中才允许操作");
					return ;
				}
			}else if(thirdSyncStatusType == PartnerConstant.ThirdSyncStatusType.PAYMENT_STATUS.getCode()){
				if(tempPartner.getRepaymentRepurchaseStatus() !=  PartnerConstant.RepaymentRepurchaseStatus.ALREADY_APPLY.getCode()){
					fillReturnJson(response, false, "还款状态必须是己申请才允许操作");
					return ;
				}
			}
			
			//是否查询贷后状态
			boolean includePostLoanStatusFlag = false; 
			if(!StringUtil.isBlank(includePostLoanStatus) && "true".equals(includePostLoanStatus)){
				includePostLoanStatusFlag = true;
			}
			//=====================执行接口层面请求
			JSONObject resultJson = null;
			ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
			projectPartnerDto.setPid(pid);
			projectPartnerDto.setPartnerNo(tempPartner.getPartnerNo());
			projectPartnerDto.setLoanId(tempPartner.getLoanId());
			if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
				//点融
				resultJson = dRPartnerApiImpl.queryLoanStatus(projectPartnerDto, includePostLoanStatusFlag, request);
			}else if(PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
				
				//审批状态
				if(thirdSyncStatusType == PartnerConstant.ThirdSyncStatusType.APPROVAL_STATUS.getCode()){
					//南粤银行
					resultJson = daJuPartnerApiImpl.queryApply(uuid, projectPartnerDto);
				}else if(thirdSyncStatusType == PartnerConstant.ThirdSyncStatusType.PAYMENT_STATUS.getCode()){
					//还款状态
					resultJson = daJuPartnerApiImpl.queryPrepayment(uuid, projectPartnerDto);
				}
			}else{
				fillReturnJson(response, false, "该机构不支持此服务方法");
				return ;
			}
			
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			
			String	msg = resultJson.getString("msg");
			JSONObject dataJson = resultJson.getJSONObject("data");
			int errCode =-1;
			if(dataJson.get("err_code")!= null){
				errCode =dataJson.getIntValue("err_code");
			}
			//=====================执行业务层面处理
			
			if(errCode  == Constants.ERR_CODE_0){
				JSONObject dataTemp = resultJson.getJSONObject("data");
				//点融机构
				if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
					//String externalId = dataTemp.getString("externalId");
					String status = dataTemp.getString("status");
					String statusD = dataTemp.getString("statusD");
					String message = dataTemp.getString("message");
					String loanAppId = dataTemp.getString("loanAppId");
					
					Date statusDate = DateUtils.longToDate(Long.parseLong(statusD));
					Timestamp statusDTime = new Timestamp(statusDate.getTime());
					
					//String 
					ProjectPartner updatePartner = new ProjectPartner();
					updatePartner.setPid(pid);
					boolean isUpdateStatus = false ;
					//判断当前系统的状态，及更新
					switch (status) {
						case "ERROR":			//创建贷款申请失败
							fillReturnJson(response, false, "操作失败,该项目创建贷款申请失败!");
							break;
						case "NEW":				//审核中
							break;
						case "IN_FUNDING":		//投资中
							if(tempPartner.loanStatus != Constants.LOAN_STATUS_3){
								updatePartner.setLoanStatus(Constants.LOAN_STATUS_3);
								updatePartner.setLoanRemark(message);
								updatePartner.setLoanResultTime(statusDTime.toString());

								//updatePartner.setApprovalStatus(Constants.APPROVAL_STATUS_2);
								//updatePartner.setApproveMoney(tempPartner.getLoanAmount());
								//updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
								//updatePartner.setLoanTime(statusDTime.toString());
								/*if(StringUtil.isBlank(tempPartner.getApprovalTime())){
									updatePartner.setApprovalTime(statusDTime.toString());
								}*/
								isUpdateStatus = true;
							}
							break;
						case "EXPIRED":			//已过期
							updatePartner.setIsClosed(2);
							isUpdateStatus = true;
							break;
						case "REJECTED":		//审核拒绝
							if(tempPartner.getApprovalStatus() == Constants.APPROVAL_STATUS_1){
								updatePartner.setApprovalStatus(Constants.APPROVAL_STATUS_3);
								updatePartner.setApprovalTime(statusDTime.toString());
								updatePartner.setApprovalComment(message);
								isUpdateStatus = true;
							}
							break;
						case "ISSUED":			//已放款
							if(tempPartner.loanStatus != Constants.LOAN_STATUS_4){
								updatePartner.setLoanStatus(Constants.LOAN_STATUS_4);
								updatePartner.setLoanRemark(message);
								updatePartner.setLoanResultTime(statusDTime.toString());
								updatePartner.setPartnerLoanDate(DateUtils.dateFormatByPattern(statusDate, "yyyy-MM-dd"));

								//updatePartner.setApproveMoney(tempPartner.getLoanAmount());
								//updatePartner.setApprovalStatus(Constants.APPROVAL_STATUS_2);
								//updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
								//updatePartner.setLoanTime(statusDTime.toString());
							    /*if(StringUtil.isBlank(tempPartner.getApprovalTime())){
									updatePartner.setApprovalTime(statusDTime.toString());
								}*/
								
								isUpdateStatus = true;
							}
							break;
						case "APPROVED":		//审核通过
							if(tempPartner.getApprovalStatus() != Constants.APPROVAL_STATUS_2){
								
								updatePartner.setApprovalStatus(Constants.APPROVAL_STATUS_2);
								updatePartner.setApproveMoney(tempPartner.getLoanAmount());
								updatePartner.setApprovalComment(message);
								updatePartner.setApprovalTime(statusDTime.toString());
								
								updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
								updatePartner.setConfirmLoanMoney(tempPartner.getLoanAmount());
								updatePartner.setConfirmLoanDays(tempPartner.getApplyDate());
								updatePartner.setConfirmLoanRequestTime(statusDTime.toString());
								updatePartner.setConfirmLoanResultTime(statusDTime.toString());
								
								updatePartner.setLoanStatus(Constants.LOAN_STATUS_1);
								isUpdateStatus = true;
							}
							break;
						case "ACCEPTED":		//用户接受
							break;
						case "WITHDRAWN":		//用户取消
							updatePartner.setIsClosed(2);
							isUpdateStatus = true;
							break;
						case "PRE_APPROVED":	//预审批通过
							break;
						//======<以下为贷后状态， includePostLoanStatus为true时使用>		
						case "CURRENT":			//正常     
							if(tempPartner.getRepaymentRepurchaseStatus() != Constants.REPAYMENT_REPURCHASE_STATUS_8){
								updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_8);
								updatePartner.setRepaymentRepurchaseType(Constants.REPAYMENT_REPURCHASE_TYPE_1);
								updatePartner.setRepaymentRepurchaseTime(nowTime.toString());
								updatePartner.setRpResultTime(nowTime.toString());
								isUpdateStatus = true;
							}
							break;
						case "GRACE_CURRENT":	//在宽限期内
							if(tempPartner.getRepaymentRepurchaseStatus() != Constants.REPAYMENT_REPURCHASE_STATUS_9){
								updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_9);
								updatePartner.setRepaymentRepurchaseType(Constants.REPAYMENT_REPURCHASE_TYPE_1);
								updatePartner.setRepaymentRepurchaseTime(nowTime.toString());
								updatePartner.setRpResultTime(nowTime.toString());
								isUpdateStatus = true;
							}
							break;
						case "DEFAULTED":		//坏帐
							if(tempPartner.getRepaymentRepurchaseStatus() != Constants.REPAYMENT_REPURCHASE_STATUS_7){
								updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_7);
								updatePartner.setRepaymentRepurchaseType(Constants.REPAYMENT_REPURCHASE_TYPE_1);
								updatePartner.setRepaymentRepurchaseTime(nowTime.toString());
								updatePartner.setRpResultTime(nowTime.toString());
								isUpdateStatus = true;
							}
							break;
						case "OVERDUE":			//逾期
							if(tempPartner.getRepaymentRepurchaseStatus() != Constants.REPAYMENT_REPURCHASE_STATUS_6){
								updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_6);
								updatePartner.setRepaymentRepurchaseType(Constants.REPAYMENT_REPURCHASE_TYPE_1);
								updatePartner.setRepaymentRepurchaseTime(nowTime.toString());
								updatePartner.setRpResultTime(nowTime.toString());
								isUpdateStatus = true;
							}
							break;
						case "SETTLED":			//已结清
							if(tempPartner.getRepaymentRepurchaseStatus() != Constants.REPAYMENT_REPURCHASE_STATUS_3){
								updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_3);
								updatePartner.setRepaymentRepurchaseType(Constants.REPAYMENT_REPURCHASE_TYPE_1);
								updatePartner.setRepaymentRepurchaseTime(nowTime.toString());
								updatePartner.setRpResultTime(nowTime.toString());
								isUpdateStatus = true;
							}
							break;
						default:
							break;
					}
					if(isUpdateStatus){
						//更新项目机构合作表状态
						int rows = clientPartner.updateProjectPartner(updatePartner);
						if(rows > 0){
							//更新审核记录表状态
							PartnerApprovalRecord updateRecord = new PartnerApprovalRecord();
							updateRecord.setReApplyReason(tempPartner.getReApplyReason());
							updateRecord.setPartnerId(tempPartner.getPid());
							updateRecord.setLoanId(loanAppId);
							updateRecord.setApprovalComment(message);
							updateRecord.setApprovalTime(statusDTime.toString());
							updateRecord.setApprovalStatus(updatePartner.getApprovalStatus());
							updateRecord.setIsNotify(Constants.IS_NOTIFY_2);
							updateRecord.setApproveMoney(tempPartner.getLoanAmount());
							updateRecord.setQueryIsNotify(Constants.IS_NOTIFY_1);
							clientPartner.updatePartnerApprovalRecord(updateRecord);
						}
					}
					String tipMessage = isUpdateStatus ?  "同步完成" : "数据未变动"; 
					fillReturnJson(response, true, "操作成功,"+tipMessage+","+message+"!");
					return;
				}else if(PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
					
					boolean isUpdateStatus = false ;
					ProjectPartner updatePartner = new ProjectPartner();
					updatePartner.setPid(pid);
					
					//处理南粤银行(审批状态)
					if(thirdSyncStatusType == PartnerConstant.ThirdSyncStatusType.APPROVAL_STATUS.getCode()){
						String lend_apply_stat = dataJson.getString("lend_apply_stat");
						if(DaJuConstant.ApplyStat.APPLY_NO_PASS.getCode().equals(lend_apply_stat)){
							//申请不通过
							isUpdateStatus = true ;
							updatePartner.setRequestStatus(PartnerConstant.RequestStatus.APPLY_NO_PASS.getCode());
							updatePartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_NO_PASS.getCode());
						}else if(lend_apply_stat.equals(DaJuConstant.ApplyStat.APPROVAL_NO_PASS.getCode())){
							//审批不通过
							isUpdateStatus = true ;
							updatePartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_NO_PASS.getCode());
						}else if(lend_apply_stat.equals(DaJuConstant.ApplyStat.APPROVAL_PASS.getCode())){
							//审批通过
							isUpdateStatus = true ;
							updatePartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_PASS.getCode());
							updatePartner.setApproveMoney(tempPartner.getLoanAmount());
							//默认设置确认在款
							updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
							updatePartner.setConfirmLoanDays(tempPartner.getApplyDate());
							updatePartner.setConfirmLoanMoney(tempPartner.getLoanAmount());
							updatePartner.setConfirmLoanRequestTime(nowTime.toString());
							updatePartner.setConfirmLoanResultTime(nowTime.toString());
						}
						//审批意见
						updatePartner.setApprovalComment(msg);
						updatePartner.setApprovalTime(nowTime.toString()); //设置审批时间
						
					}else if(thirdSyncStatusType == PartnerConstant.ThirdSyncStatusType.PAYMENT_STATUS.getCode()){
						
						//还款通知时间
						updatePartner.setRpResultTime(nowTime.toString());
						
						//处理南粤银行(还款状态)
						String payment_stat = dataJson.getString("payment_stat");
						//判断该状态是否可以操作
						int  tempRepaymentRepurchaseStatus = tempPartner.getRepaymentRepurchaseStatus() ; //还款状态
						
						if(tempRepaymentRepurchaseStatus >PartnerConstant.RepaymentRepurchaseStatus.NO_APPLY.getCode() && 
								tempRepaymentRepurchaseStatus != PartnerConstant.RepaymentRepurchaseStatus.SUCCESS.getCode() ){
							
							if(DaJuConstant.PaymentStat.SUCCESS.getCode().equals(payment_stat)){
								
								updatePartner.setRepaymentRepurchaseStatus(PartnerConstant.RepaymentRepurchaseStatus.SUCCESS.getCode());
								//机构实际还款到帐日(默认为还款日)
								updatePartner.setPartnerRealRefundDate(tempPartner.getRefundDate());
								isUpdateStatus = true ;
								
							}else if(DaJuConstant.PaymentStat.FAIL.getCode().equals(payment_stat)){
								
								updatePartner.setRepaymentRepurchaseStatus(PartnerConstant.RepaymentRepurchaseStatus.FAIL.getCode());
								isUpdateStatus = true ;
								
							}else if(DaJuConstant.PaymentStat.DEALING.getCode().equals(payment_stat)){
							}
						}
					}
					
					if(isUpdateStatus){
						//更新项目机构合作表状态
						int rows = clientPartner.updateProjectPartner(updatePartner);
						if(rows > 0){
							//更新审核记录表状态
							PartnerApprovalRecord updateRecord = new PartnerApprovalRecord();
							updateRecord.setPartnerId(tempPartner.getPid());
							updateRecord.setLoanId(tempPartner.getLoanId());
							updateRecord.setApprovalComment(msg);
							updateRecord.setApprovalTime(nowTime.toString());
							//审批状态保持一致
							updateRecord.setApprovalStatus(updatePartner.getApprovalStatus());
							updateRecord.setIsNotify(Constants.IS_NOTIFY_2);
							updateRecord.setApproveMoney(tempPartner.getLoanAmount());
							updateRecord.setQueryIsNotify(Constants.IS_NOTIFY_1);
							//修改审核记录
							clientPartner.updatePartnerApprovalRecord(updateRecord);
						}
					}
					
					String tipMessage = isUpdateStatus ?  "同步完成" : "数据未变动"; 
					fillReturnJson(response, true, "操作成功,"+tipMessage+","+msg+"!");
					return;
				}
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
		}catch(Exception e){
			logger.error("updteThirdLoanStatus error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>updteThirdLoanStatus clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	/**
	 * 更新同步第三方还款计划
	 * @param pid	机构合作项目id
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "updateThirdLoanRefund")
	public void updateThirdLoanRefund(Integer pid,HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在");
				return ;
			}else if(tempPartner.getLoanStatus() != Constants.LOAN_STATUS_4 ){
				fillReturnJson(response, false, "该数据未放款成功，不允许操作");
				return ;
			}else if(tempPartner.getRepaymentRepurchaseStatus() == Constants.REPAYMENT_REPURCHASE_STATUS_3 ){
				fillReturnJson(response, false, "该数据己还款成功，不允许操作");
				return ;
			}
			
			//=====================执行接口层面请求
			JSONObject resultJson = null;
			if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
				ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
				projectPartnerDto.setPid(pid);
				projectPartnerDto.setPartnerNo(tempPartner.getPartnerNo());
				resultJson = dRPartnerApiImpl.queryPaymentPlan(projectPartnerDto, request);
			}else{
				fillReturnJson(response, false, "该机构不支持此服务方法");
				return ;
			}
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			
			
			String	msg = resultJson.getString("msg");
			JSONObject dataJson = resultJson.getJSONObject("data");
			int errCode =dataJson.getIntValue("err_code");
			//=====================执行业务层面处理
			if(errCode  == Constants.ERR_CODE_0){
				JSONObject dataTemp = resultJson.getJSONObject("data");
				//点融机构
				if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
					
					JSONObject loanJsonObj = dataTemp.getJSONObject("loan");
					/*String externalId = loanJsonObj.getString("id");
					String loanAppId = loanJsonObj.getString("loanAppId");*/
					
					JSONObject contentJsonObj = loanJsonObj.getJSONObject("content");
					JSONArray paymentPlansJsonObj = contentJsonObj.getJSONArray("paymentPlans");
					
					List<ProjectPartnerRefund> refundList = new ArrayList<ProjectPartnerRefund>();
					ProjectPartnerRefund tempRefund = null;
					JSONObject tempJsonObj = null;
					for (int i = 0; i < paymentPlansJsonObj.size(); i++) {
						tempJsonObj = paymentPlansJsonObj.getJSONObject(i);
						tempRefund = new ProjectPartnerRefund();
						
						Date dueDate = DateUtils.longToDate(tempJsonObj.getLongValue("dueDate"));
						Timestamp dueDateTime = new Timestamp(dueDate.getTime());
						
						tempRefund.setCurrPlanRefundDate(dueDateTime.toString());		//当期预计还款日期
						tempRefund.setCurrShouldTotalMoney(tempJsonObj.getDoubleValue("totalAmount"));	//当期还款总金额
						tempRefund.setCurrNo(tempJsonObj.getIntValue("index"));			//当期还款期数
						tempRefund.setCurrShouldXiFee(tempJsonObj.getDoubleValue("estimatedDueInterest"));	//当期应还利息
						tempRefund.setCurrShouldCapitalMoney(tempJsonObj.getDoubleValue("duePrincipal"));	//当期应还本金金额
						tempRefund.setCurrShouldManageFee(tempJsonObj.getDoubleValue("dueManagementFee"));	//当期应还管理费
						tempRefund.setCurrShouldOtherFee(tempJsonObj.getDoubleValue("estimatedOtherFee"));	//当期应还其他费用
						tempRefund.setOweCapitalMoney(tempJsonObj.getDoubleValue("principalOut"));		//剩余本金
						
						tempRefund.setPartnerId(tempPartner.getPid());
						tempRefund.setProjectId(tempPartner.getProjectId());
						tempRefund.setLoanId(tempPartner.getLoanId());
						tempRefund.setPartnerNo(tempPartner.getPartnerNo());
						
						refundList.add(tempRefund);
					}
					
					//查询出己有的还款记录
					ProjectPartnerRefund queryRefund = new ProjectPartnerRefund();
					queryRefund.setPartnerId(tempPartner.getPid());
					List<ProjectPartnerRefund> tempRefundList = clientPartner.findAllProjectPartnerRefund(queryRefund);  
					int row = 0 ;
					//如果不存在，则新增
					if(tempRefundList == null || tempRefundList.size() == 0){
						//设置默认值
						for (ProjectPartnerRefund indexObj : refundList) {
							indexObj.setIsSettlementStatus(Constants.COMM_NO);
							indexObj.setIsForbit(Constants.COMM_NO);
							indexObj.setRefundStatus(Constants.REFUND_STATUS_1);
							indexObj.setCurrOverdueStatus(Constants.CURR_OVERDUE_STATUS_1);
						}
						row = clientPartner.addBatchPartnerRefund(refundList);
					}else{
						
						//如果存在，则修改对应状态
						//分期号-对象
						Map<Integer,ProjectPartnerRefund> tempRefundMap = new HashMap<Integer,ProjectPartnerRefund>(); 
						for (ProjectPartnerRefund indexObj : tempRefundList) {
							tempRefundMap.put(indexObj.getCurrNo(), indexObj);
						}
						
						List<ProjectPartnerRefund> updateRefundList = new ArrayList<ProjectPartnerRefund>();

						for (ProjectPartnerRefund indexObj : refundList) {
							tempRefund = tempRefundMap.get(indexObj.getCurrNo());
							if(tempRefund != null && (tempRefund.getRefundStatus() == Constants.REFUND_STATUS_1 
									|| tempRefund.getRefundStatus() == Constants.REFUND_STATUS_4) ){
								
								indexObj.setPid(tempRefund.getPid());
								updateRefundList.add(indexObj);
							}
						}
						if(!updateRefundList.isEmpty()){
							row = clientPartner.updateBatchPartnerRefund(refundList);
						}
					}
					if(row > 0 ){
						fillReturnJson(response, true, "操作成功,同步更新了"+row+"条数据!");
						return;
					}
				}
				fillReturnJson(response, true, "操作失败!");
				return;
				
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
		}catch(Exception e){
			logger.error(">>>>>>updateThirdLoanRefund error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>updateThirdLoanRefund clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 跳转计划还款列表
	 * @return
	 */
	@RequestMapping(value = "/refundIndex")
	public String refundIndex(Integer partnerId , ModelMap model){
		model.put("partnerId", partnerId);
		return "partner/refund_index";
	}
	
	/**
	 * 查询还款列表
	  * @param partner
	  * @param request
	  * @param response
	  * @author: Administrator
	  * @date: 2016年3月21日 下午5:44:26
	 */
	@RequestMapping(value = "/refundList")
	public void refundList(ProjectPartnerRefund refund, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询还款列表，入参："+refund);
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProjectPartnerRefund> result = null;
		int total = 0;
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			refund.setUserIds(getUserIds(request));//数据权限
			
			result = clientPartner.findAllProjectPartnerRefund(refund);
			total = clientPartner.findAllProjectPartnerRefundCount(refund);
		} catch (Exception e) {
			logger.error(">>>>>>refundList error：", e);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>refundList clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	/**
	 * 更新分期还款信息
	 */
	@RequestMapping(value = "/uploadPartnerThirdRefund")
	public void uploadPartnerThirdRefund(ProjectPartnerRefund projectPartnerRefund, HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			if(projectPartnerRefund == null || projectPartnerRefund.getPid() == 0){
				fillReturnJson(response, false, "参数错误!");
				return ;
			}
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartnerRefund query = new ProjectPartnerRefund();
			query.setPid(projectPartnerRefund.getPid());
 
			ProjectPartnerRefund tempRefund = clientPartner.findProjectPartnerRefund(query);
			
			if(Constants.REFUND_STATUS_2 == tempRefund.getRefundStatus()){
				fillReturnJson(response, false, "还款中不允许操作");
				return ;
			}else if(Constants.REFUND_STATUS_3 == tempRefund.getRefundStatus()){
				fillReturnJson(response, false, "还款成功不允许操作");
				return ;
			}
			
			//当期实际还款日期
			if(!StringUtil.isBlank(projectPartnerRefund.getCurrRealRefundDate())){
				Date refundDate = DateUtils.stringToDate(projectPartnerRefund.getCurrRealRefundDate(), "yyyy-MM-dd");
				projectPartnerRefund.setCurrRealRefundDate((new Timestamp(refundDate.getTime()).toString()));	
				
				Date planRefundDate = DateUtils.stringToDate(tempRefund.getCurrPlanRefundDate() ,"yyyy-MM-dd");
				
				//判断两个实还款日期相隔天数
				int days = DateUtils.dayDifference(planRefundDate, refundDate);
				if(days > 0 ){
					projectPartnerRefund.setCurrOverdueDays(days);
				}
			}
 
			clientPartner.updatePartnerRefundMoney(projectPartnerRefund);
			
			fillReturnJson(response, true, "操作成功!");
			return ;
		}catch (Exception e) {
			logger.error(">>>>>>uploadPartnerThirdRefund error",e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>uploadPartnerThirdRefund clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 借款人提交渠道实际还款信息(点融)
	 * @param pid	还款信息
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "applyPayment")
	public void applyPayment(String pids,HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			if(StringUtil.isBlank(pids)){
				fillReturnJson(response, false, "参数为空");
				return ;
			}
			
			List<Integer> pidList = StringUtil.StringToList(pids);
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			
			ProjectPartnerRefund queryRefund = new ProjectPartnerRefund();
			queryRefund.setPids(pidList);
			List<ProjectPartnerRefund> tempRefundList =  clientPartner.findAllProjectPartnerRefund(queryRefund);
 
			if(tempRefundList == null || tempRefundList.size() == 0 ){
				fillReturnJson(response, false, "数据不存在");
				return;
			}else if(tempRefundList.size() != pidList.size() ){
				fillReturnJson(response, false, "数据不完整");
				return;
			}
			
			//机构项目id
			int partnerId = tempRefundList.get(0).getPartnerId();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(partnerId);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "业务订单数据不存在");
				return ;
			}else if(tempPartner.getRepaymentRepurchaseStatus() == Constants.REPAYMENT_REPURCHASE_STATUS_3 ){
				fillReturnJson(response, false, "该业务订单数据己还款成功，不允许操作");
				return ;
			}
			
			//分期-主键
			Map<Integer,Integer> currNoPidMap = new HashMap<Integer, Integer>();
			//分期-还款对象
			Map<Integer,ProjectPartnerRefund> currNoRefundMap = new HashMap<Integer, ProjectPartnerRefund>();
			
			int curr_no_isSettlement_temp = 0;		//提前还款期数
			int curr_no_count = 0 ;
			//检查数据的状态是否
			for (ProjectPartnerRefund indexObj : tempRefundList) {
				
				currNoPidMap.put(indexObj.getCurrNo(), indexObj.getPid());
				currNoRefundMap.put(indexObj.getCurrNo(), indexObj);
				
				if(indexObj.getRefundStatus() == Constants.REFUND_STATUS_2){
					fillReturnJson(response, false, "当前期："+indexObj.getCurrNo()+",还款申请中不允许操作");
					return;
				}else if(indexObj.getRefundStatus() == Constants.REFUND_STATUS_3){
					fillReturnJson(response, false, "当前期："+indexObj.getCurrNo()+",还款成功不允许操作");
					return;
				}
				//提前还款必段是最后一条数据
				if(indexObj.getIsSettlementStatus() == 1){
					curr_no_isSettlement_temp = indexObj.getCurrNo();
					curr_no_count++;
				}
			}
			
			if(curr_no_count > 1 ){
				fillReturnJson(response, false, "只能存在一条提前还款记录");
				return;
			}
			if(curr_no_isSettlement_temp > 0 ){
				for (ProjectPartnerRefund indexObj : tempRefundList) {
					if(indexObj.getCurrNo() != curr_no_isSettlement_temp){
						if(indexObj.getCurrNo() > curr_no_isSettlement_temp){
							fillReturnJson(response, false, "提前还款期数必段是提交的最后一期");
							return;
						}
					}
				}
			}
			
			//=====================执行接口层面请求
			JSONObject resultJson = null;
			String partnerNo = tempRefundList.get(0).getPartnerNo();
			if(PartnerConstant.PARTNER_DR.equals(partnerNo)){
				
				resultJson = dRPartnerApiImpl.applyPayment(tempRefundList, request);
			}else{
				fillReturnJson(response, false, "该机构不支持此服务方法");
				return ;
			}
			
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			
			String	msg = resultJson.getString("msg");
			JSONObject dataJson = resultJson.getJSONObject("data");
			int errCode =dataJson.getIntValue("err_code");
			boolean isSettingmentRefundFlag = false;	//是否提前还款成功标识
			StringBuffer totalMsg = new StringBuffer("");	//所有提示
			//=====================执行业务层面处理
			if(errCode  == Constants.ERR_CODE_0){
				
				JSONArray loanArray = dataJson.getJSONArray("loans");
				
				List<ProjectPartnerRefund> updateRefundList = new ArrayList<ProjectPartnerRefund>();
				JSONObject tempJson = null;
				ProjectPartnerRefund tempRefund = null;
				
				double refund_loan_amount = 0 ;	//己还本金
				double refund_xifee = 0 ;		//己还息费
				
				ProjectPartnerRefund caclRefund = null;
				for(int i = 0 ; i < loanArray.size() ; i++){
					
					tempJson = loanArray.getJSONObject(i);
					tempRefund = new ProjectPartnerRefund();
					tempRefund.setPid(currNoPidMap.get(tempJson.getInteger("currentNum")));
					
					String message = tempJson.getString("message");
					tempRefund.setRefundRemark(message);
					//成功
					if("OK".equals(message) || "ok".equals(message)){
						tempRefund.setRefundStatus(Constants.REFUND_STATUS_3);
						
						caclRefund = currNoRefundMap.get(tempJson.getInteger("currentNum"));
						if(caclRefund != null){
							refund_loan_amount += caclRefund.getCurrRealCapitalMoney();
							refund_xifee += caclRefund.getCurrRealXiFee();
						}
						
						if(curr_no_isSettlement_temp == tempJson.getInteger("currentNum")){
							isSettingmentRefundFlag = true;
						}
						totalMsg.append("</br>期数：").append(tempJson.getInteger("currentNum")).append(",");
						totalMsg.append("提示：").append(message).append(",</br>");
						totalMsg.append("状态：").append("还款成功").append(";</br>");
					}else{
						//失败
						tempRefund.setRefundStatus(Constants.REFUND_STATUS_4);
						totalMsg.append("</br>期数：").append(tempJson.getInteger("currentNum")).append(",");
						totalMsg.append("提示：").append(message).append(",</br>");
						totalMsg.append("状态：").append("还款失败").append(";</br>");
					}
					tempRefund.setCurrNo(tempJson.getInteger("currentNum"));
					updateRefundList.add(tempRefund);
				}
				
				int row = 0 ;
				if(!updateRefundList.isEmpty()){
					row = clientPartner.updateBatchPartnerRefund(updateRefundList);
				}
				
				if(row > 0 ){
					
				
				 //修改为渠道提交还款修改状态	
				/*	//更新机构合作
					ProjectPartner updatePartner = new ProjectPartner();
					
					Timestamp nowTime = new Timestamp(new Date().getTime());
					
					//判断是否还有需要还款的
					ProjectPartnerRefund queryRefund2 = new ProjectPartnerRefund();
					queryRefund2.setPartnerId(partnerId);
					queryRefund2.setIsForbit(Constants.COMM_NO);
					// 未申请 ， 还款失败
					queryRefund.setRefundStatusList(Arrays.asList(Constants.REFUND_STATUS_1,Constants.REFUND_STATUS_4));
					List<ProjectPartnerRefund> tempRefundList2 =  clientPartner.findAllProjectPartnerRefund(queryRefund);
					
					if(isSettingmentRefundFlag){
						//将提前还款后面的期数作废
						if(tempRefundList2 != null && tempRefundList2.size() > 0 ){
							List<ProjectPartnerRefund> updateForbitList = new ArrayList<ProjectPartnerRefund>();
							ProjectPartnerRefund tempForbit= null;
							for (ProjectPartnerRefund indexObj : tempRefundList2) {
								if(indexObj.getCurrNo() > curr_no_isSettlement_temp){
									tempForbit = new ProjectPartnerRefund();
									tempForbit.setPid(indexObj.getPid());
									tempForbit.setUpdateTime(nowTime.toString());
									tempForbit.setIsForbit(Constants.COMM_NO);	//作废
								}
							}
							if(updateForbitList != null & updateForbitList.size()> 0 ){
								row = clientPartner.updateBatchPartnerRefund(updateForbitList);
								logger.info(">>>>>>applyPayment forbit num:"+updateForbitList.size());
							}
						}
					}else{
						
						if(tempRefundList2 != null && tempRefundList2.size()  > 0){

						}
					}
					
					//只修改提交状态，还款状态，需要手动同步
					if(tempPartner.getRepaymentRepurchaseStatus() == 0 
							|| tempPartner.getRepaymentRepurchaseStatus() == Constants.REPAYMENT_REPURCHASE_STATUS_1){
						updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_2);
					}
					
					updatePartner.setPid(partnerId);
					updatePartner.setRefundLoanAmount(refund_loan_amount);
					updatePartner.setRefundXifee(refund_xifee);
					updatePartner.setRepaymentRepurchaseType(Constants.REPAYMENT_REPURCHASE_TYPE_1);
					updatePartner.setRepaymentRepurchaseTime(nowTime.toString());
					updatePartner.setRpResultTime(nowTime.toString());
					//更新还款信息和本金 
					clientPartner.updatePartnerRefundLoanMoney(updatePartner);
					
					*/
					
					fillReturnJson(response, true, "操作成功，"+totalMsg.toString());
					return;
				}else{
					fillReturnJson(response, false, "操作失败!");
					return;
				}
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
		}catch(Exception e){
			logger.error("applyPayment error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>applyPayment clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	

	
	
	
	/**
	 * 查询提前还款信息
	 * @param pid	还款信息
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "getAdvancedSettlement")
	public void getAdvancedSettlement(Integer pid,HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在");
				return ;
			}else if(tempPartner.getRepaymentRepurchaseStatus() == Constants.REPAYMENT_REPURCHASE_STATUS_3 ){
				fillReturnJson(response, false, "该数己还款成功，不允许操作");
				return ;
			}
			
			//=====================执行接口层面请求
			JSONObject resultJson = null;
			if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
 				ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
				projectPartnerDto.setPid(pid);
				projectPartnerDto.setLoanId(tempPartner.getLoanId());
				resultJson = dRPartnerApiImpl.getAdvancedSettlement(projectPartnerDto, request);
			}else{
				fillReturnJson(response, false, "该机构不支持此服务方法");
				return ;
			}
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			
			String	msg = resultJson.getString("msg");
			JSONObject dataJson = resultJson.getJSONObject("data");
			int errCode =dataJson.getIntValue("err_code");
			//=====================执行业务层面处理
			if(errCode  == Constants.ERR_CODE_0){
				JSONObject dataTemp = resultJson.getJSONObject("data");
				//点融机构
				if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
					
					JSONArray jsonArray = dataTemp.getJSONArray("payment");
					JSONArray changeJsonArray = new JSONArray();
					
					JSONObject tempJson = new JSONObject();
					tempJson.put("date", "-1");
					tempJson.put("date_amount", "请选择");
					tempJson.put("amount", "0");
					changeJsonArray.add(tempJson);
					
					if(jsonArray !=null &&jsonArray.size()> 0){
						
						for(int i = 0 ; i < jsonArray.size() ; i ++){
							tempJson = jsonArray.getJSONObject(i);
							tempJson.put("date_amount", tempJson.getString("date")+"_"+tempJson.getString("amount"));
							tempJson.put("amount", tempJson.getString("amount"));
							tempJson.put("date", tempJson.getString("date"));
							changeJsonArray.add(tempJson);
						}
						jsonArray = changeJsonArray;
					}
					
					Json j = getSuccessObj();
					j.getHeader().put("success", true);
					j.getHeader().put("msg", msg);
					j.getHeader().put("paymentList", jsonArray);
					outputJson(j, response);
					return ;
				}
				fillReturnJson(response, true, "操作失败!");
				return;
				
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
		}catch(Exception e){
			logger.error(">>>>>>updateThirdLoanRefund error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>updateThirdLoanRefund clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	
	/**
	 * 提交提前还款信息(渠道)  （点融、南粤银行）
	 * @param pid	还款信息
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "applyAdvancedSettlement")
	public void applyAdvancedSettlement(ProjectPartner projectPartner ,HttpServletRequest request , HttpServletResponse response) throws Exception{
		
		BaseClientFactory clientFactoryPartner = null;
		String uuid = UuidUtil.randomUUID();
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(projectPartner.getPid());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在");
				return ;
			}else if(tempPartner.getRepaymentRepurchaseStatus() == Constants.REPAYMENT_REPURCHASE_STATUS_3 ){
				fillReturnJson(response, false, "该数己还款成功，不允许操作");
				return ;
			}else if(tempPartner.getRepaymentRepurchaseStatus() == Constants.REPAYMENT_REPURCHASE_STATUS_2 ){
				fillReturnJson(response, false, "该数己提交申请，不允许操作");
				return ;
			}
			
			
			JSONObject resultJson = null;
			//测试
			if("true".equals(IS_PARTNER_API_DEBUG)){
				
				resultJson = new JSONObject();
				resultJson.put("err_code", 0);
				resultJson.put("err_msg", "ok");
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				dataJson.put("err_msg", "ok");
				dataJson.put("payment_stat", "04000");
				resultJson.put("data", dataJson);
				
			}else{
				//=====================执行接口层面请求
				if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
	 				ProjectPartnerDto projectPartnerDto = new ProjectPartnerDto();
					projectPartnerDto.setPid(tempPartner.getPid());
					projectPartnerDto.setLoanId(tempPartner.getLoanId());
					projectPartnerDto.setRefundDate(projectPartner.getRefundDate());
					projectPartnerDto.setRefundLoanAmount(projectPartner.getRefundLoanAmount());
					
					resultJson = dRPartnerApiImpl.advancedSettlement(projectPartnerDto, request);
				}else if(PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
					projectPartner.setLoanId(tempPartner.getLoanId());
					resultJson = daJuPartnerApiImpl.prepayment(uuid, projectPartner);
					
				}else{
					fillReturnJson(response, false, "该机构不支持此服务方法");
					return ;
				}
			}
			
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			int errCode = -1;
			String	msg = resultJson.getString("msg");
			JSONObject dataJson = resultJson.getJSONObject("data");
			if(dataJson.get("err_code") != null ){
				errCode =dataJson.getIntValue("err_code");
			}
			
			//=====================执行业务层面处理
			if(errCode  == Constants.ERR_CODE_0){
				
				Timestamp nowTime = new Timestamp(new Date().getTime());
				
				//通用更新条件
				ProjectPartner updatePartner = new ProjectPartner();
				updatePartner.setPid(tempPartner.getPid());
				updatePartner.setRefundDate(projectPartner.getRefundDate());
				updatePartner.setRefundLoanAmount(projectPartner.getRefundLoanAmount());
				updatePartner.setRepaymentRepurchaseTime(nowTime.toString());
				updatePartner.setRepaymentRepurchaseType(Constants.REPAYMENT_REPURCHASE_TYPE_1);
				
				//点融机构
				if(PartnerConstant.PARTNER_DR.equals(tempPartner.getPartnerNo())){
					updatePartner.setRefundTotalAmount(projectPartner.getRefundLoanAmount());
					updatePartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_2);//申请中
					
				}else if(PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
					updatePartner.setRefundTotalAmount(projectPartner.getRefundTotalAmount());
					updatePartner.setRefundXifee(projectPartner.getRefundXifee());
					updatePartner.setRefundPenalty(projectPartner.getRefundPenalty());
					updatePartner.setRefundFine(projectPartner.getRefundFine());
					updatePartner.setRefundCompdinte(projectPartner.getRefundCompdinte());
					
					updatePartner.setPayAcctName(projectPartner.getPayAcctName());
					updatePartner.setPayAcctNo(projectPartner.getPayAcctNo());
					
					//还款通知时间
					updatePartner.setRpResultTime(nowTime.toString());
					
					//String payment_stat = dataJson.getString("payment_stat");
					//还款中
					updatePartner.setRepaymentRepurchaseStatus(PartnerConstant.RepaymentRepurchaseStatus.ALREADY_APPLY.getCode());

				}
				if(clientPartner.updateProjectPartner(updatePartner) > 0 ){
					fillReturnJson(response, true, "操作成功!");
					return;
				}
				
				fillReturnJson(response, false, "操作失败!");
				return;
				
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
		}catch(Exception e){
			logger.error(">>>>>>updateThirdLoanRefund error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>updateThirdLoanRefund clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
	
	
	
	/**
	 * 多文件上传
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "uploadMultipartFile")
	public void uploadMultipartFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String projectId = request.getParameter("projectId");
		String accessoryType = request.getParameter("accessoryType");
		String accessoryChildType = request.getParameter("accessoryChildType");
		String partnerNo = request.getParameter("partnerNo");
		String remark = request.getParameter("remark");
		String partnerId = request.getParameter("partnerId");
		
		BaseClientFactory clientFactoryPartnerFile = null;
		try{
			if(!StringUtil.isBlank(partnerNo)){
				partnerNo = URLDecoder.decode(partnerNo, "UTF-8");
			}
			if(!StringUtil.isBlank(remark)){
				remark = URLDecoder.decode(remark, "UTF-8");
			}
			
			List<Map<String,Object>> fileMapList = FileUtil.uploadFile(request, response, "partner", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
			
			if(fileMapList == null || fileMapList.size() == 0 ){
				fillReturnJson(response, false, "操作失败!");
			}
			
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			ProjectPartnerFile updateFile  = null;
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			int count = 0 ;
			int temp_pid = 0;
			
			for (Map<String, Object> indexObj : fileMapList) {
				
				updateFile = new ProjectPartnerFile();
				updateFile.setUpdateTime(nowTime.toString());
				updateFile.setProjectId(Integer.parseInt(projectId));
				updateFile.setPartnerNo(partnerNo);
				updateFile.setFileName(indexObj.get("fileName").toString());
				updateFile.setFileSize(Integer.parseInt(indexObj.get("fileSize").toString()));
				updateFile.setFileType(FileUtil.getFileType(indexObj.get("fileUrl").toString()));
				updateFile.setStatus(Constants.COMM_YES);
				updateFile.setAccessoryType(accessoryType);
				updateFile.setAccessoryChildType(accessoryChildType);
				updateFile.setFileUrl(indexObj.get("fileUrl").toString());
				updateFile.setRemark(remark);
				updateFile.setPartnerId(Integer.parseInt(partnerId));
				temp_pid = clientPartnerFile.addProjectPartnerFile(updateFile);
				
				if(temp_pid > 0){
					count ++ ;
				}
			}
			if(count >  0){
				fillReturnJson(response, true, "操作成功!");
			}else{
				fillReturnJson(response, false, "操作失败!");
			}
		}catch(Exception e){
			logger.error("uploadMultipartFile error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			if (clientFactoryPartnerFile != null) {
				try {
					clientFactoryPartnerFile.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>uploadMultipartFile clientFactoryPartnerFile destroy thrift error：",e);
				}
			}
		}
	}
	
	
	/**
	 * 查看客户基本信息
	 */
	@RequestMapping(value = "/editPerBaseInit")
	public String editPerBaseInit(@RequestParam(value = "acctId", required = false) Integer acctId, @RequestParam(value = "flag", required = false) Integer flag, @RequestParam(value = "isLook", required = false) Integer isLook, @RequestParam(value = "currUserPid", required = false) Integer currUserPid, @RequestParam(value = "type", required = false) Integer type,HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		int perId = 0;
		CusPerBaseDTO cusPerBaseDTO;
		try {
			if (acctId != null) {
				CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
				perId = client.selectPerIdByAcctId(acctId);
				cusPerBaseDTO = client.getCusPerBase(perId);
				model.put("cusPerBaseDTO", cusPerBaseDTO);
				model.put("flag", flag);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
		return "partner/partner_edit_cus_per";
	}
	
	

	/**
	 * 保存客户附加信息
	 * 
	 * @param cusPerBaseDTO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCusPer", method = RequestMethod.POST)
	public void saveCusPer(CusPerson cusPerson, HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
		try {
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			if (cusPerson.getPid() > 0) {
				client.updateFromPartnerById(cusPerson);
			} else {
				client.addCusPerson(cusPerson);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			destroyFactory(clientFactory);
		}
		Json j = super.getSuccessObj();
		outputJson(j, response);

	}
	
	
	/**
	 * 上传附件到第三方平台（南粤银行）
	 * @param pid	文件id
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadFileToThird")
	public void uploadFileToThird(Integer pid, HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartnerFile = null;
		String uuid = UuidUtil.randomUUID();
		
		try {
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			//查询文件
			ProjectPartnerFile tempFile = clientPartnerFile.getById(pid);
			
			if(tempFile== null  || tempFile.getPid() == 0 ){
				fillReturnJson(response, false, "文件数据不存在");
				return;
			}
			//时间格式
			String nowDateStr = DateUtils.dateToFormatString(new Date(), "yyyyMMdd");
			//时间格式
			String nowTimeStr = DateUtils.dateToFormatString(new Date(), "yyyyMMddHHmmssSSS");
			//打包文件
			List<String> fileUrlList = Arrays.asList(tempFile.getFileUrl());
			List<String> fileNameList = Arrays.asList(tempFile.getFileName());
			//系统本地路径
			String localRootPath =	CommonUtil.getRootPath();
			//系统上传根目录
			String uploadFileRoot = getUploadFilePath();
			//zip相对目录
			String relativeZipPath = "partner"+File.separator+nowDateStr+File.separator+tempFile.getPartnerNo();
			//zip文件名
			String zipFileName = nowTimeStr+".zip";
			
			//组装全文件路径
			Map<String,Object> returnMap  = FileUtil.packageZipFile(fileUrlList, fileNameList, localRootPath, uploadFileRoot, relativeZipPath, zipFileName,true);
			
			String zipFilePath = returnMap.get("zipFilePath").toString();
			List<String> zipFileNameList = (List<String>) returnMap.get("zipFileNameList");
			File uploadfile = new File(localRootPath+File.separator+zipFilePath);
			//上传到大桔
			ResponseParams responseParams = DaJuUtil.postFile(uuid, uploadfile);
			
			
			//模拟成功 begin------------------------
//			ResponseParams responseParams = new ResponseParams();
//			responseParams.setMessage(new Message(MessageCode.SUCCESS.getCode(), MessageCode.SUCCESS.getMsg()));
//			JSONObject test_bodyJson = new JSONObject();
//			test_bodyJson.put("return_code", DaJuConstant.ReturnCode.SUCCESS.getCode());
//			test_bodyJson.put("return_msg", DaJuConstant.ReturnCode.SUCCESS.getMsg());
//			test_bodyJson.put("file_path", "/third/"+nowTimeStr+".zip");
//			responseParams.setBody(test_bodyJson);
			//模拟成功 end------------------------			
			
			if(responseParams == null){
				uploadfile.delete();
				fillReturnJson(response, false,"查询失败，网络请求失败");
				return;
			}
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				uploadfile.delete();
				fillReturnJson(response, false,"查询失败，"+responseParams.getMessage().getDesc());
				return;
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			if(!DaJuConstant.ReturnCode.SUCCESS_FILE.getCode().equals(bodyJson.getString("return_code"))){
				uploadfile.delete();
				fillReturnJson(response, false,"查询失败，"+ bodyJson.getString("return_msg"));
				return;
			}
			String thirdUrl = bodyJson.getString("file_path");
			Timestamp nowTime = new Timestamp(new Date().getTime());
			ProjectPartnerFile updateFile  = new ProjectPartnerFile();
			//xxxx.zip>xxx.jpg   (默认只有一个文件)
			updateFile.setThirdFileUrl(thirdUrl+">"+zipFileNameList.get(0));
			updateFile.setPid(tempFile.getPid());
			updateFile.setUpdateTime(nowTime.toString());
			if(clientPartnerFile.updateProjectPartnerFile(updateFile) > 0 ){
				fillReturnJson(response, true, "操作成功!");
				return ;
			}else{
				fillReturnJson(response, false, "操作失败!");
				return ;
			}
		}catch (Exception e) {
			logger.error("uploadFileToThird error",e);
		}finally {
			 destroyFactory(clientFactoryPartnerFile);
		}
	}
	
	
	
	/**
	 * 上传附件到第三方平台（华安保险）
	 * @param pidListStr	文件多个id
	 * @param partnerId		机构合作机目id
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadFileToThird_hnbx")
	public void uploadFileToThird_hnbx(String pidListStr,Integer partnerId, HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartnerFile = null;
		BaseClientFactory clientFactoryPartner = null;
		String uuid = UuidUtil.randomUUID();
		int errCode = -1;
		try {
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner queryProjectPartner = new ProjectPartner();
			queryProjectPartner.setPid(partnerId);
			
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryProjectPartner);
			
			ProjectPartner partner = new ProjectPartner();
			partner.setPid(partnerId);
			partner.setPartnerNo(PartnerConstant.PARTNER_HNBX);
			partner.setRequestFiles(pidListStr);
			//推送帐号
			partner.setPartnerPushAccount(tempPartner.getPartnerPushAccount());
			
			JSONObject resultJson = hNBXPartnerApiImpl.uploadFile(uuid , partner);
			
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			} 
			
			JSONObject dataJson = resultJson.getJSONObject("data");
			String msg =resultJson.getString("msg"); 
			//上传文件路径
			String filePath = "";
			
			if(dataJson != null && dataJson.get("err_code") != null){
				errCode =dataJson.getIntValue("err_code");
				filePath = dataJson.getString("filePath");
			}
			
			if(errCode == 0){
				//机构合作项目更新标识
				boolean updatePartnerFlag = false ;
				//文件更新标识
				boolean updateFileFlag = false ;
				
				//更新到机构合作项目请求文件字段
				ProjectPartner updatePartner = new ProjectPartner();
				updatePartner.setPid(partnerId);
				updatePartner.setRequestFiles(pidListStr);
				
				if(clientPartner.updateProjectPartner(updatePartner) > 0 ){
					updatePartnerFlag = true ;
				}
				
				
				//先将所有的文件路径清空
				ProjectPartnerFile queryFile = new ProjectPartnerFile();
				queryFile.setPartnerId(partnerId);
				clientPartnerFile.updateAllFileUploadEmpty(queryFile);
				
				
				//更新文件路径
				ProjectPartnerFile updateFile = new ProjectPartnerFile();
				updateFile.setThirdFileUrl(filePath);
				updateFile.setPidList(CommonUtil.getSepStrtoList(pidListStr, "Integer", ","));
				if(clientPartnerFile.updateProjectPartnerFile(updateFile) > 0 ){
					updateFileFlag = true ;
				}
				
				logger.info("uuid:"+uuid+",updatePartnerFlag="+updatePartnerFlag+",updateFileFlag:"+updateFileFlag);
				
				if(!(updatePartnerFlag && updateFileFlag)){
					fillReturnJson(response, false, "更新失败,请重新操作!");
					return;
				}
				fillReturnJson(response, true, msg);
				return;
			}else{
				fillReturnJson(response, false, msg);
				return;
			}
		}catch (Exception e) {
			logger.error("uuid:"+uuid+",uploadFileToThird_hnbx error",e);
		}finally {
			 destroyFactory(clientFactoryPartner);
			 destroyFactory(clientFactoryPartnerFile);
		}
	}

	/**
	 * 提前还款试算(南粤银行)
	 * @param pid	还款信息
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "prepaymentAdvance")
	public void prepaymentAdvance(Integer pid,String refundDate , HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		String uuid = UuidUtil.randomUUID();
		Json j = super.getSuccessObj();
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在");
				return ;
			}else if(tempPartner.getRepaymentRepurchaseStatus() == Constants.REPAYMENT_REPURCHASE_STATUS_3 ){
				fillReturnJson(response, false, "该项目己还款成功，不允许操作");
				return ;
			}
			//=====================执行接口层面请求
			JSONObject resultJson = null;
			if(PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
				
				tempPartner.setRefundDate(refundDate);
				resultJson = daJuPartnerApiImpl.prepaymentAdvance(uuid, tempPartner);
			}else{
				fillReturnJson(response, false, "该机构不支持此服务方法");
				return ;
			}
			if(resultJson == null){
				fillReturnJson(response, false, "网络请求失败,请重新操作!");
				return;
			}
			
			String	msg = resultJson.getString("msg");
			JSONObject dataJson = resultJson.getJSONObject("data");
			int errCode =dataJson.getIntValue("err_code");
			//=====================执行业务层面处理
			if(errCode  == Constants.ERR_CODE_0){
				//南粤银行
				if(PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
					j.getHeader().put("success", true);
					j.getHeader().put("msg", msg);
					j.getBody().put("data", dataJson);
					outputJson(j, response);
					return ;
				}
				fillReturnJson(response, true, "操作失败!");
				return;
				
			}else{
				fillReturnJson(response, false, "操作失败,"+msg+"!");
				return;
			}
		}catch(Exception e){
			logger.error(">>>>>>prepaymentAdvance error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactoryPartner);
		}
	}
	
	
	
	
	/**
	 * 重置业务申请（清空状态，重新成生成业务编号）
	 * @param pid	 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "resetPartnerOrderCode")
	@ResponseBody
	public void resetPartnerOrderCode(Integer pid, HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		String uuid = UuidUtil.randomUUID();
		try {
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setPid(pid);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			if(tempPartner == null || tempPartner.getPid() == 0 ){
				fillReturnJson(response, false, "数据不存在");
				return ;
			}
			//己关不能操作2
			if(tempPartner.getIsClosed()   == Constants.COMM_NO  ){
				fillReturnJson(response, false, "己关闭不能操作");
				return ;
			}else if(!PartnerConstant.PARTNER_NYYH.equals(tempPartner.getPartnerNo())){
				fillReturnJson(response, false, "该机构不支持");
				return ;
			}
			
			//申请拒绝和审批拒绝才能重置
			if(!(PartnerConstant.RequestStatus.APPLY_NO_PASS.getCode() == tempPartner.getRequestStatus() ||
					PartnerConstant.ApprovalStatus.APPROVAL_NO_PASS.getCode() == tempPartner.getApprovalStatus())){				
				fillReturnJson(response, false, "必须是申请拒绝或审核未通过才能重置");
				return ;
			}
			
			
			
			ProjectPartner updateProjectPartner = new ProjectPartner();
			
			updateProjectPartner.setPid(pid);
			//设置机构项目订单编号
			updateProjectPartner.setPartnerOrderCode(DateUtils.dateToString(new Date(),"yyyyMMddHHmmssSSSS"));
			updateProjectPartner.setRequestStatus(PartnerConstant.RequestStatus.NO_APPLY.getCode());
			updateProjectPartner.setApprovalStatus(-1);
			
			if(clientPartner.updateProjectPartner(updateProjectPartner)> 0){
				fillReturnJson(response, true, "操作成功");
				return ;
			}else{
				fillReturnJson(response, false, "操作失败");
				return ;
			}
 
		}catch(Exception e){
			logger.error(">>>>>>resetPartnerOrderCode error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactoryPartner);
		}
	}
	
	
	
	/**
	 * 修改赎楼信息（资金机构）
	 * @param pid	 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "updateForeclosureByPartner")
	@ResponseBody
	public void updateForeclosureByPartner(ProjectForeclosure projectForeclosure , HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryProject = null;
		try {
			//项目
			clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			
			if(projectForeclosure.getPid() == 0 || StringUtil.isBlank(projectForeclosure.getNewRecePerson()) ||
				StringUtil.isBlank(projectForeclosure.getNewRecePerson())){
				fillReturnJson(response, false, "操作失败,参数格式有误!");
			}
			
			if(clientProject.updateForeclosureByPartner(projectForeclosure) == 0){
				fillReturnJson(response, false, "操作失败，未保存成功!");
				return ;
			}
			
			fillReturnJson(response, true, "操作成功");
			return ;
 
		}catch(Exception e){
			logger.error(">>>>>>updateForeclosureByPartner error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactoryProject);
		}
	}
	
	
	
	/**
	 * 修改审核状态信息
	 * @param pid	 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "updateApprovalStatus")
	@ResponseBody
	public void updateApprovalStatus(ProjectPartner projectPartner , HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			//项目
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			if(projectPartner.getPid() == 0 || projectPartner.getApprovalStatus() == 0 
					|| StringUtil.isBlank(projectPartner.getPartnerNo())){
				fillReturnJson(response, false, "操作失败,参数格式有误!");
				return;
			}
			
			Timestamp time = new Timestamp(new Date().getTime());
			
			ProjectPartner query = new ProjectPartner(); 
			query.setPid(projectPartner.getPid()); 
			query.setPartnerNo(projectPartner.getPartnerNo());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(query);
			
			if(tempPartner == null || tempPartner.getPid() == 0){
				fillReturnJson(response, false, "操作失败,数据不存在!");
				return ;
			}
			
			if(tempPartner.getApprovalStatus() != 1){
				fillReturnJson(response, false, "操作失败,审核状态必须是审核中才能修改!");
				return ;
			}
			
			projectPartner.setApprovalTime(time.toString());
			//审批通过
			if(PartnerConstant.ApprovalStatus.APPROVAL_PASS.getCode() == projectPartner.getApprovalStatus() ){
				projectPartner.setApproveMoney(tempPartner.getLoanAmount());
				//默认设置确认在款
				projectPartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
				projectPartner.setConfirmLoanDays(tempPartner.getApplyDate());
				projectPartner.setConfirmLoanMoney(tempPartner.getLoanAmount());
				projectPartner.setConfirmLoanRequestTime(time.toString());
				projectPartner.setConfirmLoanResultTime(time.toString());
			}
			
			if(clientPartner.updateProjectPartner(projectPartner) == 0){
				fillReturnJson(response, false, "操作失败，未保存成功!");
				return ;
			}
			
			//修改审批记录
			PartnerApprovalRecord updateRecord = new PartnerApprovalRecord();
			
			updateRecord.setPartnerId(projectPartner.getPid());
			updateRecord.setLoanId(tempPartner.getLoanId());
			updateRecord.setApprovalComment(projectPartner.getApprovalComment());
			updateRecord.setApprovalTime(time.toString());
			updateRecord.setApprovalStatus(projectPartner.getApprovalStatus());
			updateRecord.setIsNotify(Constants.IS_NOTIFY_2);
			updateRecord.setApproveMoney(tempPartner.getLoanAmount());
			updateRecord.setQueryIsNotify(Constants.IS_NOTIFY_1);
			//修改审核记录
			clientPartner.updatePartnerApprovalRecord(updateRecord);
			
			fillReturnJson(response, true, "操作成功");
			return ;
 
		}catch(Exception e){
			logger.error(">>>>>>updateApprovalStatus error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactoryPartner);
		}
	}
	
	/**
	 * 修改放款状态信息
	 * @param pid	 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "updateLoanStatus")
	@ResponseBody
	public void updateLoanStatus(ProjectPartner projectPartner , HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			//项目
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			if(projectPartner.getPid() == 0 || projectPartner.getLoanStatus() == 0 
					|| StringUtil.isBlank(projectPartner.getPartnerNo()) 
					|| StringUtil.isBlank(projectPartner.getPartnerLoanDate())){
				fillReturnJson(response, false, "操作失败,参数格式有误!");
				return;
			}
			
			Timestamp time = new Timestamp(new Date().getTime());
			ProjectPartner query = new ProjectPartner(); 
			query.setPid(projectPartner.getPid()); 
			query.setPartnerNo(projectPartner.getPartnerNo());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(query);
			
			if(tempPartner == null || tempPartner.getPid() == 0){
				fillReturnJson(response, false, "操作失败,数据不存在!");
				return ;
			}
			if( !(tempPartner.loanStatus == 0 ||   tempPartner.loanStatus == PartnerConstant.LoanStatus.NO_APPLY.getCode())){
				fillReturnJson(response, false, "操作失败,放款状态必须是未申请才能修改!");
				return ;
			}
			
			projectPartner.setLoanTime(time.toString());
			projectPartner.setLoanResultTime(time.toString());
			
			if(clientPartner.updateProjectPartner(projectPartner) == 0){
				fillReturnJson(response, false, "操作失败，未保存成功!");
				return ;
			}
			fillReturnJson(response, true, "操作成功");
			return ;
 
		}catch(Exception e){
			logger.error(">>>>>>updateLoanStatus error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactoryPartner);
		}
	}
	
	
	/**
	 * 修改还款状态信息
	 * @param pid	 
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "updateRepaymentRepurchaseStatus")
	@ResponseBody
	public void updateRepaymentRepurchaseStatus(ProjectPartner projectPartner , HttpServletRequest request , HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			//项目
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			if(projectPartner.getPid() == 0 || projectPartner.getRepaymentRepurchaseStatus() == 0 
					|| StringUtil.isBlank(projectPartner.getPartnerNo()) 
					|| StringUtil.isBlank(projectPartner.getRefundDate())
					|| projectPartner.getRefundLoanAmount() == 0 
					|| projectPartner.getRefundTotalAmount() == 0 ){
				fillReturnJson(response, false, "操作失败,参数格式有误!");
				return;
			}
			
			Timestamp time = new Timestamp(new Date().getTime());
			ProjectPartner query = new ProjectPartner(); 
			query.setPid(projectPartner.getPid()); 
			query.setPartnerNo(projectPartner.getPartnerNo());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(query);
			
			if(tempPartner == null || tempPartner.getPid() == 0){
				fillReturnJson(response, false, "操作失败,数据不存在!");
				return ;
			}
			if( !(tempPartner.getRepaymentRepurchaseStatus() == 0 ||   tempPartner.getRepaymentRepurchaseStatus() == PartnerConstant.RepaymentRepurchaseStatus.NO_APPLY.getCode())){
				fillReturnJson(response, false, "操作失败,还款状态必须是未申请才能修改!");
				return ;
			}
			
			//还款请求和回调日期
			projectPartner.setRepaymentRepurchaseTime(time.toString());
			projectPartner.setRpResultTime(time.toString());
			//机构实际还款到帐日  默认跟还款日期一致
			projectPartner.setPartnerRealRefundDate(projectPartner.getRefundDate());
			//默认还款
			projectPartner.setRepaymentRepurchaseType(PartnerConstant.RepaymentRepurchaseType.C_1.getCode()	);
			
			if(clientPartner.updateProjectPartner(projectPartner) == 0){
				fillReturnJson(response, false, "操作失败，未保存成功!");
				return ;
			}
			fillReturnJson(response, true, "操作成功");
			return ;
 
		}catch(Exception e){
			logger.error(">>>>>>updateRepaymentRepurchaseStatus error",e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}finally {
			destroyFactory(clientFactoryPartner);
		}
	}
	
	
	
	
}
