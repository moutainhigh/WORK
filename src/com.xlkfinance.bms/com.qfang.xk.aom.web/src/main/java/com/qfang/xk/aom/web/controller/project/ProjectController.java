package com.qfang.xk.aom.web.controller.project;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.qfang.xk.aom.rpc.project.BizProjectDTO;
import com.qfang.xk.aom.rpc.project.BizProjectService;
import com.qfang.xk.aom.rpc.project.OrderRejectInfo;
import com.qfang.xk.aom.rpc.project.OrderRejectInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.web.controller.BaseController;
import com.qfang.xk.aom.web.page.Json;
import com.qfang.xk.aom.web.util.FileUtil;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoan;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoanService;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;
import com.xlkfinance.bms.rpc.beforeloan.ProjectPublicMan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysBankInfoDto;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;

/**
 * 业务申请Controller
  * @ClassName: ProjectController
  * @author: baogang
  * @date: 2016年7月19日 下午2:30:01
 */
@Controller
@RequestMapping("/projectController")
public class ProjectController extends BaseController {
   private Logger logger = LoggerFactory.getLogger(ProjectController.class);
   private String PATH = "/project";

   @ExceptionHandler
   public String exception(HttpServletRequest request, HttpServletResponse response, Exception e) {
      logger.error(ExceptionUtil.getExceptionMessage(e), e);
      fillReturnJson(response, false, "出现未知异常,请与系统管理员联系!");
      return null;
   }

   /**
    * 跳转到业务申请列表
     * @param model
     * @return
     * @author: baogang
     * @date: 2016年7月19日 下午2:30:38
    */
   @RequestMapping("/toProjectIndex")
   public String toProjectIndex(ModelMap model) {
	   model.put("productList", getProductLists());
      return PATH + "/project_index";
   }

   /**
    * 跳转业务编辑页面
     * @param model
     * @param pid
     * @return
     * @throws ThriftServiceException
     * @throws TException
     * @author: baogang
     * @throws ThriftException 
     * @date: 2016年7月19日 下午2:31:33
    */
   @RequestMapping("/toEditProject")
   public String toEditProject(ModelMap model, Integer pid,@RequestParam(value = "editType", required = false)Integer editType, HttpServletResponse response) throws ThriftServiceException, TException, ThriftException {
	   OrgUserInfo loginUser = getLoginUser();//登录人
	   int orgId = loginUser.getOrgId();
	   OrgCooperatCompanyApplyService.Client client = (OrgCooperatCompanyApplyService.Client) getService(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
	   //查询所属机构合作状态
	   OrgAssetsCooperationService.Client orgClient = (OrgAssetsCooperationService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
	   OrgAssetsCooperationInfo cooperationInfo = orgClient.getById(orgId);
	   if(editType != null && editType != 3 &&(cooperationInfo == null || cooperationInfo.getCooperateStatus() != AomConstant.ORG_COOPERATE_STATUS_2)){
		   fillReturnJson(response, false, "所属机构未展开合作，不能提单！");
	       return PATH + "/project_index";
	   }
	   OrgCooperatCompanyApplyInf companyInfo = client.getByOrgId(orgId);//机构合作信息
	   BizProject project = new BizProject();
	   List<DataInfo> dataInfoList = new ArrayList<DataInfo>();
	   //查询合作城市集合
	   OrgCooperateCityInfo query = new OrgCooperateCityInfo();
	   query.setOrgId(orgId);
	   List<OrgCooperateCityInfo> cityInfoList = client.getOrgCooperateCityInfo(query);
	   List<CusDTO> publicList = new ArrayList<CusDTO>();
	   //根据业务申请ID查询详情以及资料集合
	   if (pid != null && pid > 0) {
    	  BizProjectService.Client projectService = (BizProjectService.Client) getService(AomConstant.AOM_SYSTEM, BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
    	  project = projectService.getProjectByPid(pid);
          dataInfoList = projectService.getProjectDataInfos(pid);
          //根据项目ID查询项目关联共同借款人
		  CusPerService.Client perClient = (CusPerService.Client) getService(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		  publicList = perClient.getNoSpouseLists(pid);
      }
	   //获取银行列表
	  List<SysBankInfoDto> bankList = getBankList();
	  model.put("productList", getProductLists());
	  model.put("bankList", bankList);
	  model.put("companyInfo", companyInfo);
	  model.put("project", project);
	  model.put("dataInfoList", dataInfoList);
	  model.put("cityInfoList", cityInfoList);
      model.put("editType", editType);
      model.put("publicList", publicList);
      return PATH + "/edit_project";
   }
   
   /**
    * 分页查询业务申请信息
     * @param query
     * @param response
     * @throws TException
     * @author: baogang
     * @date: 2016年7月19日 下午2:50:11
    */
   @RequestMapping("/getProjectList")
   public void getProjectList(BizProjectDTO query, HttpServletResponse response) throws TException {
      OrgUserInfo loginUser = getLoginUser();
      int total = 0;
      List<BizProjectDTO> list = new ArrayList<BizProjectDTO>();
	   BaseClientFactory factory = null;
	   try {
		  factory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		  BizProjectService.Client projectService = (BizProjectService.Client) factory.getClient();
    	  query.setOrgId(loginUser.getOrgId());//设置机构ID
          query.setApplyUserId(getDataScope());//设置数据权限
          list = projectService.getBizProjectByPage(query);
          
          total = projectService.getBizProjectCount(query);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		}finally{
			destroyFactory(factory);
		}
      
      // 输出
      outputPage(query.getRows(), response, list, total);
   }
   
   /**
    * 保存业务申请信息
     * 
     * @author: baogang
     * @date: 2016年7月20日 上午10:28:30
    */
   @RequestMapping(value = "/saveProjectInfo", method = RequestMethod.POST)
   public void saveProjectInfo(BizProject bizProject, HttpServletResponse resp,HttpServletRequest req) throws TException {
	   String customerCard = bizProject.getOrgCustomerCard();
	   recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "保存业务申请信息：参数saveProjectInfo："+bizProject.getOrgCustomerName()+",金额"+bizProject.getPlanLoanMoney(), req);
	   
	   Json j = super.getSuccessObj();
	   BaseClientFactory factory = null;
	   try {
		   //根据证件号码查询该客户是否为黑名单
		   CusAcctService.Client actClient = (CusAcctService.Client) getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_CUSTOMER, "CusAcctService").getClient();
		   customerCard = customerCard.trim();
		   int blackFlags = actClient.getBlackByCertNum(customerCard);
		   if(blackFlags >0){
			   fillReturnJson(resp, false, "此客户已加入黑名单，不允许再提交订单！");
		       return;
		   }
		   
		   factory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		   BizProjectService.Client projectService = (BizProjectService.Client) factory.getClient();
		   OrgUserInfo loginUser = getLoginUser();//登录用户
		   bizProject.setOrgId(loginUser.getOrgId());//设置机构ID
		   
		   int projectId = bizProject.getPid();
		   String result = "";
		   if(projectId >0){
			   result = projectService.updateBizProject(bizProject);
		   }else{
			   bizProject.setApplyUserId(loginUser.getPid());//设置业务申请提交人
			   bizProject.setRequestStatus(AomConstant.BUSINESS_REQUEST_STATUS_1);
			   result = projectService.addBizProject(bizProject);
		   }
		   if(StringUtil.isBlank(result)){
			   fillReturnJson(resp, false, "保存出错，请联系后台管理员！");
		       return;
		   }
		   String[] pids = result.split(",");
		   if(pids.length == 4){
			   j.getHeader().put("projectId", pids[0]);
			   j.getHeader().put("foreclosureId", pids[1]);
			   j.getHeader().put("propertyId", pids[2]);
			   j.getHeader().put("guaranteeId", pids[3]);
		   }
		   
		   j.getHeader().put("success", true);
		   j.getHeader().put("msg", "保存成功");
		} catch (Exception e) {
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存失败");
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		}finally{
			destroyFactory(factory);
		}
	   
	   outputJson(j, resp);
	   
   }
   
   /**
    * 提交业务申请信息
     * 
     * @author: baogang
     * @date: 2016年7月20日 上午10:28:30
    */
   @RequestMapping(value = "/submitProjectInfo", method = RequestMethod.POST)
   public void submitProjectInfo(BizProject bizProject, HttpServletResponse resp, HttpServletRequest req) throws TException {
	   String customerCard = bizProject.getOrgCustomerCard();
	   recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "提交机构业务申请信息：参数："+bizProject.getOrgCustomerName()+",金额"+bizProject.getPlanLoanMoney(), req);
	   int projectId = bizProject.getPid();
	   if(projectId <= 0){
		   fillReturnJson(resp, false, "资料附件未上传，请上传后再提交！");
	       return;
	   }
	   
	   OrgUserInfo loginUser = getLoginUser();//登录用户
	   bizProject.setOrgId(loginUser.getOrgId());//设置机构ID
	   bizProject.setIsReject(AomConstant.IS_REJECT_2);//驳回状态正常
	   Json j = super.getSuccessObj();
	   BaseClientFactory factory = null;
	   BaseClientFactory orgFactory = null;
	   try {
		   //根据证件号码查询该客户是否为黑名单
		   CusAcctService.Client actClient = (CusAcctService.Client) getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_CUSTOMER, "CusAcctService").getClient();
		   int blackFlags = actClient.getBlackByCertNum(customerCard);
		   if(blackFlags >0){
			   fillReturnJson(resp, false, "此客户已加入黑名单，不允许再提交订单！");
		       return;
		   }
		   factory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		   BizProjectService.Client projectService = (BizProjectService.Client) factory.getClient();
		   orgFactory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG, "OrgAssetsCooperationService");
		   //判断资料附件是否已提交
		   List<DataInfo> dataInfoList = projectService.getProjectDataInfos(projectId);
		   if(!checkDataList(dataInfoList)){
			   fillReturnJson(resp, false, "资料附件未上传，请上传后再提交！");
		       return;
		   }
		   
		   int orgId = bizProject.getOrgId();
		   OrgAssetsCooperationService.Client client = (OrgAssetsCooperationService.Client) orgFactory.getClient();
		   OrgAssetsCooperationInfo orgAssetsInfo = client.getById(orgId);
		   //机构信息不存在或者机构合作状态不是已合作
		   if(orgAssetsInfo == null || orgAssetsInfo.getCooperateStatus() != AomConstant.ORG_COOPERATE_STATUS_2){
		        fillReturnJson(resp, false, "提单机构已解除合作状态,不能提单!");
		        return;
		   }
		   
		   //查看合作信息详情
		   OrgCooperatCompanyApplyService.Client applyClient = (OrgCooperatCompanyApplyService.Client) getService(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG, "OrgCooperatCompanyApplyService");
		   OrgCooperatCompanyApplyInf companyInfo = applyClient.getByOrgId(orgId);
		   double planLoanMoney = bizProject.getProjectGuarantee().getLoanMoney();//借款金额
		   if(companyInfo == null || companyInfo.getAvailableLimit()<planLoanMoney ){
			   fillReturnJson(resp, false, "机构可用额度不足，请联系管理员！");
		       return;
		   }
		   //判断借款金额是否大于单笔金额上限
		   if(planLoanMoney > companyInfo.getSingleUpperLimit()){
			   fillReturnJson(resp, false, "借款金额不能大于单笔提单上限，请修改后提交！");
		       return;
		   }
		   String result = "";
		   if(projectId >0){
			   result = projectService.updateBizProject(bizProject);
		   }else{
			   bizProject.setApplyUserId(loginUser.getPid());//设置业务申请提交人
			   bizProject.setRequestStatus(AomConstant.BUSINESS_REQUEST_STATUS_2);
			   result = projectService.addBizProject(bizProject);
		   }
		   if(StringUtil.isBlank(result)){
			   fillReturnJson(resp, false, "保存出错，请联系后台管理员！");
		       return;
		   }
		   String[] pids = result.split(",");
		   if(pids.length == 4){
			   j.getHeader().put("projectId", pids[0]);
			   j.getHeader().put("foreclosureId", pids[1]);
			   j.getHeader().put("propertyId", pids[2]);
			   j.getHeader().put("guaranteeId", pids[3]);
		   }
		   
		   j.getHeader().put("success", true);
		   j.getHeader().put("msg", "提交成功");
		}catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "提交失败");
		}finally{
			destroyFactory(factory,orgFactory);
		}
	   
	   outputJson(j, resp);
	   
   }
   
   /**
    * 判断资料文件是否存在，存在返回TRUE,不存在返回false
    * @return
    */
   private boolean checkDataList(List<DataInfo> dataInfoList){
	   boolean flag =false;
	   if(dataInfoList != null && dataInfoList.size() >0){
		   for(DataInfo dataInfo :dataInfoList){
			   if(dataInfo.getDataId()>0){
				   flag = true;
			   }
		   }
	   }
	   return flag;
   }
   
   /**
    * 上传文件资料
     * @param pid
     * @param projectId
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     * @throws ThriftServiceException
     * @throws TException
     * @author: baogang
     * @date: 2016年7月22日 上午10:37:16
    */
   @RequestMapping(value = "/uploadFile")
   public void uploadFile(Integer pid,Integer projectId, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,ThriftServiceException, TException {
      if (projectId<=0) {
         fillReturnJson(resp, false, "参数不合法");
         return;
      }
      BaseClientFactory factory = null;
	  BaseClientFactory bizFactory = null;
	  Json j = super.getSuccessObj();
	  try {
		  factory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		  BizProjectService.Client projectService = (BizProjectService.Client) factory.getClient();
		  bizFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_SYSTEM, "BizFileService");
		  BizFileService.Client fileService = (BizFileService.Client) bizFactory.getClient();
		  OrgUserInfo loginUser = getLoginUser();
          //获取上传文件并保存本地
          Map<String, Object> fileMap = getFileMap(req, resp, "", BusinessModule.MODUEL_ORG);
          boolean uploadIsSucc = (boolean) fileMap.get("flag");
          if (!uploadIsSucc) {
             fillReturnJson(resp, false, "文件上传失败："+fileMap.get("errorMsg"));
             return;
          }
          BizFile uploadFile = (BizFile) fileMap.get("bizFile");
          //把文件数据保存数据库
         
          int bizFileId = fileService.saveBizFile(uploadFile);
          //关联到业务申请资料
          DataInfo dataInfo = new DataInfo();
          dataInfo.setDataId(pid);
          dataInfo.setProjectId(projectId);
          dataInfo.setFileId(bizFileId);
          dataInfo.setUserId(loginUser.getPid());
          projectService.saveDataFile(dataInfo);
          
          List<DataInfo> dataInfoList = projectService.getProjectDataInfos(projectId);
          
          j.getHeader().put("dataInfoList", dataInfoList);
          j.getHeader().put("success", true);
          j.getHeader().put("msg", "上传成功");
          recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "上传文件资料：pid："+pid, req);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
			j.getHeader().put("success", false);
			j.getHeader().put("msg", e.getMessage());
		}finally{
			destroyFactory(factory,bizFactory);
		}

      outputJson(j, resp);
   }
   
   /**
    * 修改项目关闭状态
     * @param pid
     * @param isClosed
     * @param req
     * @param resp
     * @throws TException
     * @author: baogang
     * @date: 2016年7月22日 上午11:30:57
    */
   @RequestMapping(value = "/updateClosed")
   public void updateClosed(Integer pid,Integer isClosed,HttpServletRequest req, HttpServletResponse resp) throws TException{
	   if (pid<=0) {
         fillReturnJson(resp, false, "参数不合法");
         return;
      }
	   BaseClientFactory factory = null;
	   try {
		   factory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		   BizProjectService.Client projectService = (BizProjectService.Client) factory.getClient();
		   BizProject bizProject = new BizProject();
		   bizProject.setPid(pid);
		   bizProject.setIsClosed(isClosed);
		   projectService.updateProjectClosed(bizProject);
		   recordLog(OrgSysLogTypeConstant.LOG_TYPE_UPDATE, "修改项目关闭状态：pid："+pid+"&isClosed"+isClosed, req);
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		}finally{
			destroyFactory(factory);
		}
	   
	   fillReturnJson(resp, true, "修改成功");
   }
   
   /**
    * 查询业务申请附件列表
     * @param projectId
     * @param req
     * @param resp
     * @author: baogang
     * @date: 2016年8月26日 下午2:54:16
    */
   @RequestMapping(value = "/getProjectDataInfoList", method = RequestMethod.POST)
   public void getProjectDataInfoList(Integer projectId,HttpServletRequest req, HttpServletResponse resp){
	  if (projectId<=0) {
	        fillReturnJson(resp, false, "参数不合法");
	        return;
	      }
	   BaseClientFactory bizFactory = null;
	   List<DataInfo> dataInfoList = new ArrayList<DataInfo>();
	   try {
		   bizFactory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		   BizProjectService.Client projectService = (BizProjectService.Client) bizFactory.getClient();
		   dataInfoList = projectService.getProjectDataInfos(projectId);
		   
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		}finally{
			destroyFactory(bizFactory);
		}
	   outputJson(dataInfoList, resp);
   }
   
   /**
    * 删除上传的文件资料
     * @param dataId
     * @param req
     * @param resp
     * @author: baogang
     * @date: 2016年9月5日 上午9:47:33
    */
   @RequestMapping(value = "/delDataInfo", method = RequestMethod.POST)
   public void delDataInfo(Integer dataId,HttpServletRequest req, HttpServletResponse resp){
	  if (dataId<=0) {
	        fillReturnJson(resp, false, "参数不合法");
	        return;
	      }
	   BaseClientFactory bizFactory = null;
	   Json j = super.getSuccessObj();
	   try {
		   bizFactory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		   BizProjectService.Client projectService = (BizProjectService.Client) bizFactory.getClient();
		   int result = projectService.delDataFile(dataId);
		   if(result >0){
			   j.getHeader().put("success", true);
			   j.getHeader().put("msg", "删除成功");
		   }else{
			   j.getHeader().put("success", false);
			   j.getHeader().put("msg", "删除失败");
		   }
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
			j.getHeader().put("success", false);
			j.getHeader().put("msg", e.getMessage());
		}finally{
			destroyFactory(bizFactory);
		}
	   outputJson(j, resp);
   }
   
   /**
    * 获取驳回信息
     * @param projectId
     * @param req
     * @param resp
     * @author: baogang
     * @date: 2016年9月5日 上午10:02:10
    */
   @RequestMapping(value="/getRejectInfo")
   public void getRejectInfo(Integer projectId,HttpServletRequest req, HttpServletResponse resp){
	   if (projectId<=0) {
	        fillReturnJson(resp, false, "参数不合法");
	        return;
	      }
	   BaseClientFactory bizFactory = null;
	   OrderRejectInfo result = new OrderRejectInfo();
	   try {
		   bizFactory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "OrderRejectInfoService");
		   OrderRejectInfoService.Client client = (OrderRejectInfoService.Client) bizFactory.getClient();
		   OrderRejectInfo query = new OrderRejectInfo();
		   query.setOrderId(projectId);
		   List<OrderRejectInfo> list = client.getAll(query);
		   if(list != null && list.size() >0){
			   result = list.get(0);
		   }
		} catch (Exception e) {
			logger.error(ExceptionUtil.getExceptionMessage(e), e);
		}finally{
			destroyFactory(bizFactory);
		}
	   outputJson(result, resp);
   }
   
   /**
    * 上传文件
     * @param projectId
     * @param fileProperty
     * @param req
     * @param resp
     * @throws Exception
     * @author: baogang
     * @date: 2016年9月6日 下午3:21:52
    */
   @RequestMapping("/uploadProjectFile")
   public void uploadProjectFile(Integer projectId,Integer fileProperty,HttpServletRequest req, HttpServletResponse resp) throws Exception {
	   if (projectId<=0 || fileProperty <=0) {
	        fillReturnJson(resp, false, "参数不合法");
	        return;
	  }
	  BaseClientFactory bizFactory = null;
	  BaseClientFactory factory = null;
      try {
          Map<String, Object> resultMap = FileUtil.uploadFile(req, resp, "system", getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
          List<BizFile> bizFileList = (List<BizFile>) resultMap.get("bizFileList");
          if (bizFileList == null || bizFileList.isEmpty()) {
             return;
          }
    	  bizFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_SYSTEM, "BizFileService");
    	  BizFileService.Client fileService = (BizFileService.Client) bizFactory.getClient();
    	  factory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
		  BizProjectService.Client projectService = (BizProjectService.Client) factory.getClient();
    	  OrgUserInfo loginUser = getLoginUser();
    	  for(BizFile bizFile : bizFileList){
        	  int bizFileId = fileService.saveBizFile(bizFile);
        	  //关联到业务申请资料
              DataInfo dataInfo = new DataInfo();
              dataInfo.setProjectId(projectId);
              dataInfo.setFileId(bizFileId);
              dataInfo.setFileProperty(fileProperty);
              dataInfo.setUserId(loginUser.getPid());
              projectService.saveDataFile(dataInfo);
          }
		} catch (Exception e) {
			logger.error("上传文件",ExceptionUtil.getExceptionMessage(e));
		}finally {
			destroyFactory(bizFactory,factory);
		}

      
   }
   
   /**
    * 查询上传资料类型列表
     * @param projectId
     * @param response
     * @param request
     * @author: baogang
     * @date: 2016年9月6日 下午2:06:08
    */
   	@RequestMapping(value = "searchDataType")
	@ResponseBody
	public void searchDataType(int projectId, HttpServletResponse response,HttpServletRequest request) {
		BaseClientFactory c = null;
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		BaseClientFactory factory = null;
		try {
			factory = getFactory(AomConstant.AOM_SYSTEM,
					BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
			BizProjectService.Client projectService = (BizProjectService.Client) factory
					.getClient();
			BizProject project = projectService.getById(projectId);
			String projectTypes = "";
			if(project.getBusinessCategory() == 13756){//非交易
				projectTypes = "1,3";//1与3表示数据字典中文件类型的数值，数值1和3的文件为非交易类型的文件
			}else if(project.getBusinessCategory() == 13755){
				projectTypes = "1,4";//1与4表示数据字典中文件类型的数值，数值1和4的文件为交易类型的文件
			}
			
			c = getFactory(AomConstant.BMS_SYSTEM,
					BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client sysLookupService = (SysLookupService.Client) c
					.getClient();
			list = sysLookupService.getDataTypeByType(projectTypes);
		} catch (Exception e) {
			logger.error("查询资料类型列表", ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(c,factory);
		}
		outputJson(list, response);
	}
   	
	/**
	 * 保存物业信息
	 * @param projectEstate
	 * @param response
	 */
	@RequestMapping(value="saveHouseInfo", method = RequestMethod.POST)
	public void saveHouseInfo(BizProjectEstate projectEstate,HttpServletResponse response){
		logger.info("保存物业信息，参数：" + projectEstate);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			int userId = getLoginUser().getPid();//获取登录用户的ID
			
			//判断物业ID,小于等于0时做新增操作，大于0时做更新操作
			int houseId = projectEstate.getHouseId();
			if(houseId<=0){
				projectEstate.setCreaterId(userId);
				projectEstate.setStatus(1);//设置数据有效
				houseId = client.insert(projectEstate);
			}else{
				projectEstate.setUpdateId(userId);
				client.update(projectEstate);
			}
			//根据返回的物业ID判断操作是否成功
			if(houseId >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功");
				j.getHeader().put("houseId", houseId);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存物业信息失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存物业信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存物业信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	
	/**
	 * 根据物业ID集合查询物业信息
	 * @param response
	 */
	@RequestMapping(value="getHouseList")
	public void getHouseList(String houseIds,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			BizProjectEstate query = new BizProjectEstate();
			List<Integer> houseIdList = StringUtil.StringToList(houseIds);
			if(houseIdList != null && houseIdList.size()>0){
				query.setHouseIds(houseIdList);
				query.setStatus(1);//有效的
				list = client.getAll(query);
			}
		} catch (Exception e) {
			logger.error("根据物业ID集合查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 根据项目ID查询物业信息
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value="getHouseByProjectId")
	public void getHouseByProjectId(Integer projectId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			list = client.getAllByProjectId(projectId );
		} catch (Exception e) {
			logger.error("根据项目ID查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 删除物业信息
	 * @param houseIds
	 * @param response
	 */
	@RequestMapping(value="delHouse")
	public void delHouse(String houseIds,HttpServletResponse response){
		logger.info("删除物业信息，参数：" + houseIds);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		BaseClientFactory loanFactory = null;
		try {
			List<Integer> houseIdList = StringUtil.StringToList(houseIds);
			
			List<BizOriginalLoan> list = new ArrayList<BizOriginalLoan>();
			loanFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client loanClient = (BizOriginalLoanService.Client) loanFactory.getClient();
			BizOriginalLoan query = new BizOriginalLoan();
			query.setEstateId(houseIdList.get(0));
			query.setStatus(1);//有效的
			list = loanClient.getAllByCondition(query);
			if(list != null && list.size()>0){
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,此物业已关联原贷款信息!");
			}else{
				clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
				BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
				
				int result = 0;
				if(houseIdList != null && houseIdList.size()>0){
					result = client.delProjectEstate(houseIdList);
				}
				//根据返回的物业ID判断操作是否成功
				if(result >0){
					j.getHeader().put("success", true);
					j.getHeader().put("msg", "操作成功");
				} else {
					// 失败的话,做提示处理
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "操作失败,请重新操作!");
				}
			}
		}catch(Exception e) {
			logger.error("批量删除物业信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 补充资料后再次提交
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value="saveReject", method = RequestMethod.POST)
	public void saveReject(Integer projectId,HttpServletResponse resp){
		if (projectId <= 0) {
			logger.error("请求数据不合法：" + projectId);
			fillReturnJson(resp, false, "请求数据不合法!");
			return;
		}
		BaseClientFactory clientFactory = null;
		try {

			clientFactory = getFactory(AomConstant.AOM_SYSTEM,BusinessModule.MODUEL_ORG_PROJECT, "BizProjectService");
			BizProjectService.Client projectService = (BizProjectService.Client) clientFactory
					.getClient();
			BizProject bizProject = new BizProject();
			bizProject.setPid(projectId);
			bizProject.setIsReject(AomConstant.IS_REJECT_2);//设置项目状态正常
			bizProject .setExamineUser(getLoginUser().getPid());//登录人
			projectService.update(bizProject);
			fillReturnJson(resp, true, "保存成功");
		} catch (Exception e) {
			fillReturnJson(resp, false, "保存失败,请重新操作!");
			logger.error("补充资料后再次提交失败:" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
	}
	
	/**
	 * 保存原贷款信息
	 * @param originalLoan
	 * @param response
	 */
	@RequestMapping(value="saveOriginalLoanInfo", method = RequestMethod.POST)
	public void saveOriginalLoanInfo(BizOriginalLoan originalLoan,HttpServletResponse response){
		logger.info("保存原贷款信息，参数：" + originalLoan);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			int userId = getLoginUser().getPid();//获取登录用户的ID
			
			//判断原贷款信息ID,小于等于0时做新增操作，大于0时做更新操作
			int originalLoanId = originalLoan.getOriginalLoanId();
			if(originalLoanId<=0){
				originalLoan.setCreaterId(userId);
				originalLoan.setStatus(1);//设置数据有效
				originalLoanId = client.insert(originalLoan);
			}else{
				originalLoan.setUpdateId(userId);
				client.update(originalLoan);
			}
			//根据返回的原贷款ID判断操作是否成功
			if(originalLoanId >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "保存成功");
				j.getHeader().put("originalLoanId", originalLoanId);
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "保存原贷款信息失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("保存原贷款信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "保存原贷款信息失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	
	/**
	 * 根据原贷款信息ID集合查询原贷款信息
	 * @param response
	 */
	@RequestMapping(value="getOriginalLoanList")
	public void getOriginalLoanList(String originalLoanIds,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizOriginalLoan> list = new ArrayList<BizOriginalLoan>();
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			BizOriginalLoan query = new BizOriginalLoan();
			List<Integer> originalLoanIdList = StringUtil.StringToList(originalLoanIds);
			if(originalLoanIdList != null && originalLoanIdList.size()>0){
				query.setOriginalLoanIds(originalLoanIdList);
				query.setStatus(1);//有效的
				list = client.getAllByCondition(query);
			}
		} catch (Exception e) {
			logger.error("根据原贷款信息ID集合查询原贷款信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 根据项目ID查询原贷款信息
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value="getOriginalLoanByProjectId")
	public void getOriginalLoanByProjectId(Integer projectId,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizOriginalLoan> list = new ArrayList<BizOriginalLoan>();
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			BizOriginalLoan query = new BizOriginalLoan();
			query.setProjectId(projectId);
			query.setStatus(1);//有效的
			list = client.getAllByCondition(query);
		} catch (Exception e) {
			logger.error("根据项目ID查询原贷款信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 删除原贷款信息
	 * @param originalLoanIds
	 * @param response
	 */
	@RequestMapping(value="delOriginalLoan")
	public void delOriginalLoan(String originalLoanIds,HttpServletResponse response){
		logger.info("删除原贷款信息，参数：" + originalLoanIds);
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client client = (BizOriginalLoanService.Client) clientFactory.getClient();
			//页面传的ID字符串分解为list
			List<Integer> originalLoanIdList = StringUtil.StringToList(originalLoanIds);
			int result = 0;
			if(originalLoanIdList != null && originalLoanIdList.size()>0){
				result = client.delOriginalLoan(originalLoanIdList);
			}
			//根据返回的结果判断操作是否成功
			if(result >0){
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "操作成功");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,请重新操作!");
			}
		}catch(Exception e) {
			logger.error("批量删除原贷款信息失败：" + ExceptionUtil.getExceptionMessage(e));
	        e.printStackTrace();
	        j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 
	 * @Description: 根据数据字典类型查询当前数据类型的值
	 * @param lookupType
	 *            数据字典类型
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2014年12月29日 上午11:17:29
	 */
	@RequestMapping(value = "getSysLookupValByLookType")
	@ResponseBody
	public void getSysLookupValByLookType(String lookupType, HttpServletResponse response) {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client client = (SysLookupService.Client) clientFactory.getClient();
			SysLookupVal s = new SysLookupVal();
			s.setPid(-1);
			s.setLookupVal("");
			s.setLookupDesc("--请选择--");
			list.add(s);
			list.addAll(client.getSysLookupValByLookType(lookupType));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		// 输出
		outputJson(list, response);
	}
	
	/**
	 * 根据物业ID集合查询物业信息,用于物业下拉框
	 * @param response
	 */
	@RequestMapping(value="getEstateList")
	@ResponseBody
	public void getEstateList(@RequestParam(value = "houseIds", required = true)String houseIds,HttpServletResponse response){
		BaseClientFactory clientFactory = null;
		List<BizProjectEstate> list = new ArrayList<BizProjectEstate>();
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client client = (BizProjectEstateService.Client) clientFactory.getClient();
			BizProjectEstate query = new BizProjectEstate();
			BizProjectEstate result = new BizProjectEstate();
			result.setHouseId(-1);
			result.setHouseName("--请选择--");
			list.add(result);
			List<Integer> houseIdList = StringUtil.StringToList(houseIds);
			if(houseIdList != null && houseIdList.size()>0){
				query.setHouseIds(houseIdList);
				query.setStatus(1);//有效的
				list.addAll(client.getAll(query));
			}
		} catch (Exception e) {
			logger.error("根据物业ID集合查询物业信息:" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		outputJson(list, response);
	}
	
	/**
	 * 获取产品列表
	 * @param response
	 */
	private List<Product> getProductLists(){
		List<Product> resultList = new ArrayList<Product>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_PRODUCT, "ProductService");
			ProductService.Client service = (ProductService.Client) clientFactory.getClient();
			//查询条件
			Product productDTO = new Product();
			productDTO.setCityId(2);//使用小科金融的ID
			productDTO.setStatus(1);//有效的产品
			productDTO.setProductType(2); // 设置查询条件为查询赎楼产品列表
			resultList.addAll(service.getAllProductList(productDTO));
		} catch (Exception e) {
			logger.error("获取产品列表失败：" + ExceptionUtil.getExceptionMessage(e));
		}finally{
			destroyFactory(clientFactory);
		}
		return resultList;
	}
	
	/**
	 * 
	 * @Description: 根据客户ID查询用户信息
	 * @param acctId
	 *            客户ID
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Cai.Qing
	 * @date: 2015年5月25日 下午3:19:01
	 */
	@RequestMapping(value = "getCusPersonByAcctId")
	public void getCusPersonByAcctId(@RequestParam(value = "acctId", required = false) Integer acctId, HttpServletResponse response) {
		CusDTO cusBase = null;
		BaseClientFactory factory = null;
		try {
			factory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientPer = (CusPerService.Client) factory.getClient();
			// 根据客户ID查询个人信息对象
			cusBase = clientPer.getPersonalListByAcctId(acctId);

		} catch (Exception e) {
			logger.error("根据客户ID查询用户信息失败：" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(factory);
		}
		// 输出
		outputJson(cusBase, response);
	}
	
	/**
	 * 根据客户ID查询关系人列表
	 * @param acctId
	 * @param response
	 */
	@RequestMapping(value = "getNoSpouseList", method = RequestMethod.POST)
	public void getNoSpouseList(Integer acctId, HttpServletResponse response) {
		List<CusDTO> list = new ArrayList<CusDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			if(acctId != null && acctId>0){
				list = client.getNoSpouseList(acctId);
			}
		} catch (Exception e) {
			logger.error("根据客户ID查询关系人列表失败：" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(list, response);
	}
	
	/**
	 * 根据项目ID查询项目关联关系人列表
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value = "getNoSpouseLists", method = RequestMethod.POST)
	public void getNoSpouseLists(int projectId, HttpServletResponse response) {
		List<CusDTO> list = new ArrayList<CusDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list = client.getNoSpouseLists(projectId);
		} catch (Exception e) {
			logger.error("根据项目ID查询项目关联关系人列表失败：" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(list, response);
	}

	/**
	 * 根据关系人ID集合查询项目关联关系人列表
	 * @param projectId
	 * @param response
	 */
	@RequestMapping(value = "getSpouseListByPids", method = RequestMethod.POST)
	public void getSpouseListByPids(String userIds, HttpServletResponse response) {
		List<CusDTO> list = new ArrayList<CusDTO>();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			list = client.getNoSpouseListByPid(userIds);
		} catch (Exception e) {
			logger.error("根据关系人ID集合查询关系人列表失败：" + ExceptionUtil.getExceptionMessage(e));
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(list, response);
	}
	
	/**
	 * 删除共同借款人
	 * @param userPids
	 * @param response
	 * @param req
	 */
	@RequestMapping(value = "delPublicMan", method = RequestMethod.POST)
	public void delPublicMan(String userPids, HttpServletResponse response,HttpServletRequest req) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			// 调用批量删除的方法
			int rows = client.batchDeleteProjectPublicMan(userPids);
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(OrgSysLogTypeConstant.LOG_TYPE_DELETE, "删除共同借款人",req);
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "删除共同借款人成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "删除失败,请重新操作!");
			}
		} catch (Exception e) {
			logger.debug("删除共同借款人失败:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
	/**
	 * 选择共同借款人
	 * @param userPids
	 * @param projectId
	 * @param response
	 * @param req
	 */
	@RequestMapping(value = "addProjectPublicMan", method = RequestMethod.POST)
	public void addProjectPublicMan(String userPids,int projectId, HttpServletResponse response,HttpServletRequest req) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(AomConstant.BMS_SYSTEM,BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			ProjectPublicMan projectPublicMan = new ProjectPublicMan();
			int rows = 0;
			if(!StringUtil.isBlank(userPids)){
				projectPublicMan.setProjectId(projectId);
				projectPublicMan.setUserPids(userPids);
				// 调用批量增加的方法
				rows = client.addProjectPublicMan(projectPublicMan);
			}
			
			if (rows != 0) {
				// 成功的话,就做业务日志记录
				recordLog(OrgSysLogTypeConstant.LOG_TYPE_ADD, "选择共同借款人",req);
				j.getHeader().put("success", true);
				j.getHeader().put("msg", "选择共同借款人成功！");
			} else {
				// 失败的话,做提示处理
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "选择失败,请重新操作!");
			}
		}catch (Exception e) {
			logger.debug("选择共同借款人:" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "操作失败,请重新操作!");
		} finally {
			destroyFactory(clientFactory);
		}
		// 输出
		outputJson(j, response);
	}
	
}
