package com.xlkfinance.bms.web.controller.inloan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentFile;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentFileService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentHisService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationHisService;
import com.xlkfinance.bms.rpc.inloan.CollectFileDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFilePrintInfo;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService.Client;
import com.xlkfinance.bms.rpc.inloan.PerformJobRemark;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.FileUtil;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月12日下午4:49:55 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：综合部<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/integratedDeptController")
public class IntegratedDeptController extends BaseController {
   private static final String PAGE_PATH = "inloan/integratedDept/";
   private Logger logger = LoggerFactory.getLogger(IntegratedDeptController.class);
   private final String serviceName = "IntegratedDeptService";

   /**
    * 首页跳转
    */
   @RequestMapping(value = "/integrated_dept_index")
   public String toIntegratedDeptIndex(ModelMap model) {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      url.append("integrated_dept_index");
      return url.toString();
   }

   /**
    * 收件查档列表（分页查询）
    *@author:liangyanjun
    *@time:2016年3月12日下午5:00:20
    *@param checkDocumentIndexDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/checkDocumentList")
   public void checkDocumentList(CheckDocumentIndexDTO checkDocumentIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (checkDocumentIndexDTO==null)checkDocumentIndexDTO=new CheckDocumentIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<CheckDocumentIndexDTO> checkDocumentIndexList = null;
      int total = 0;
      // 设置数据权限--用户编号list
      checkDocumentIndexDTO.setUserIds(getUserIds(request));
      try {
         // 查询要件查档列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         checkDocumentIndexList = service.queryCheckDocumentIndex(checkDocumentIndexDTO);
         total = service.getCheckDocumentIndexTotal(checkDocumentIndexDTO);
         logger.info("要件查档查询列表查询成功：total：" + total + ",checkDocumentIndexList:" + checkDocumentIndexList);
      } catch (Exception e) {
         logger.error("获取要件查档列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + checkDocumentIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", checkDocumentIndexList);
      map.put("total", total);
      outputJson(map, response);
   }
   /**
    * 收件
    *@author:liangyanjun
    *@time:2016年3月12日下午6:55:35
    *@param collectFileDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/collectFile",method= RequestMethod.POST)
   public void collectFile(CollectFileDTO collectFileDTO, HttpServletRequest request, HttpServletResponse response) {
      int projectId = collectFileDTO.getProjectId();
      String collectDate = collectFileDTO.getCollectDate();
      if (projectId<=0||StringUtil.isBlank(collectDate)) {
         logger.error("请求数据不合法：" + collectFileDTO);
         fillReturnJson(response, false, "提交失败,输入必填项,请重新操作!");
         return;
      }
      try {
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         collectFileDTO.setUpdateId(userId);
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         
         Project project = projectService.getProjectInfoById(projectId);
         if (project.getForeclosureStatus()==Constants.FORECLOSURE_STATUS_12) {
            fillReturnJson(response, false, "提交失败,业务办理已完成,不可更改");
            return;
         }
         if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单或已经拒单");
            return;
         }
         service.collectFile(collectFileDTO);
         fillReturnJson(response, true, "提交成功");
         //往业务动态插入一条记录
         String bizDynamicRemark="收件日期:"+collectDate;
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_2, bizDynamicRemark);
      } catch (Exception e) {
         logger.error("收件失败：入参：collectFileDTO" + collectFileDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
   }
   /**
    * 退件
    *@author:liangyanjun
    *@time:2016年5月16日下午4:20:04
    *@param collectFileDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/refundFile",method= RequestMethod.POST)
   public void refundFile(CollectFileDTO collectFileDTO, HttpServletRequest request, HttpServletResponse response) {
      int projectId = collectFileDTO.getProjectId();
      String refundDate = collectFileDTO.getRefundDate();
      if (projectId<=0||StringUtil.isBlank(refundDate)) {
         logger.error("请求数据不合法：" + collectFileDTO);
         fillReturnJson(response, false, "提交失败,输入必填项,请重新操作!");
         return;
      }
      try {
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         collectFileDTO.setUpdateId(userId);
         
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         //检查退件是否完结
         Project project = projectService.getProjectInfoById(projectId);
         int refundFileStatus = project.getRefundFileStatus();
         if (refundFileStatus==Constants.REFUND_FILE_STATUS_2) {
            fillReturnJson(response, false, "提交失败,退件已完结!");
            return;
         }
         //执行退件
         service.refundFile(collectFileDTO);
         fillReturnJson(response, true, "提交成功");
      } catch (Exception e) {
         logger.error("退件失败：入参：collectFileDTO" + collectFileDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
   }
   /**
    * 退件完结
    *@author:liangyanjun
    *@time:2016年5月27日下午6:43:48
    *@param projectId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/refundFileFinish",method= RequestMethod.POST)
   public void refundFileFinish(int projectId, HttpServletRequest request, HttpServletResponse response) {
      if (projectId<=0) {
         logger.error("请求数据不合法：" + projectId);
         fillReturnJson(response, false, "提交失败,输入必填项,请重新操作!");
         return;
      }
      try {
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         if (project.getRefundFileStatus()==Constants.REFUND_FILE_STATUS_2) {
            fillReturnJson(response, false, "提交失败,该项目已经退件完结");
            return;
         }
         // 退件完结则修改退件状态为已退件
         projectService.updaterefundFileStatusByPid(projectId, Constants.REFUND_FILE_STATUS_2);
         fillReturnJson(response, true, "提交成功");
      } catch (Exception e) {
         logger.error("退件完结失败：入参：projectId" + projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
   }
   /**
    * 根据项目id获取收件信息
    *@author:liangyanjun
    *@time:2016年4月1日下午11:24:57
    *@param projectId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getCollectFileInfo",method= RequestMethod.POST)
   public void getCollectFileInfo(int projectId,int buyerSellerType,String buyerSellerName, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         Map result=new HashMap<>();
         CollectFileDTO collectfileQuery=new CollectFileDTO();
         collectfileQuery.setProjectId(projectId);
         collectfileQuery.setPage(-1);//不分页查询
         collectfileQuery.setStatus(Constants.STATUS_ENABLED);
         collectfileQuery.setBuyerSellerType(buyerSellerType);
         collectfileQuery.setBuyerSellerName(buyerSellerName);
         List<CollectFileDTO> collectFileList = service.queryCollectFile(collectfileQuery);
         result.put("collectFileList", collectFileList);
         outputJson(result, response);
      } catch (TException e) {
         logger.error("根据项目id获取收件列表失败：入参：projectId" + projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 获取退要件信息
    *@author:liangyanjun
    *@time:2016年5月17日上午8:57:42
    *@param projectId
    *@param buyerSellerType
    *@param buyerSellerName
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getRefundFileInfo",method= RequestMethod.POST)
   public void getRefundFileInfo(int projectId,int buyerSellerType,String buyerSellerName, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         Map result=new HashMap<>();
         CollectFileDTO collectfileQuery=new CollectFileDTO();
         collectfileQuery.setProjectId(projectId);
         collectfileQuery.setPage(-1);//不分页查询
         collectfileQuery.setStatus(Constants.STATUS_ENABLED);
         collectfileQuery.setBuyerSellerType(buyerSellerType);
         collectfileQuery.setBuyerSellerName(buyerSellerName);
         //查询已收取的要件
         List<CollectFileDTO> collectFileList = service.queryCollectFile(collectfileQuery);
         //查询已经退还的要件
         collectfileQuery.setRefundStatus(Constants.REFUND_FILE_STATUS_2);
         List<CollectFileDTO> refundFileList = service.queryCollectFile(collectfileQuery);
         //获取没有收取的要件,退要件时用到
         Set<String> keySet = Constants.COLLECT_FILE_MAP.keySet();
         Set<String> collectFileCodeSet=new HashSet<String>();
         for (CollectFileDTO dto : collectFileList) {
            collectFileCodeSet.add(dto.getCode());
         }
         Set<String> notCollectFileCodeSet=new HashSet<String>();
         for (String code : keySet) {
            if (!collectFileCodeSet.contains(code)) {
               notCollectFileCodeSet.add(code);
            }
         }
         
         result.put("notCollectFileCodeSet", notCollectFileCodeSet);
         result.put("collectFileList", collectFileList);
         result.put("refundFileList", refundFileList);
         outputJson(result, response);
      } catch (TException e) {
         logger.error("根据项目id获取收件列表失败：入参：projectId" + projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 获取买卖方要件信息
    *@author:liangyanjun
    *@time:2016年5月11日下午3:36:18
    *@param projectId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getBuyerSellerByProjectId",method= RequestMethod.POST)
   public void getBuyerSellerByProjectId(int projectId, HttpServletRequest request, HttpServletResponse response) {
      ProjectService.Client projectService = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      Map result=new HashMap<>();
      try {
         
         Project project = projectService.getLoanProjectByProjectId(projectId);
         ProjectProperty projectProperty = project.getProjectProperty();
         ProjectForeclosure projectForeclosure = project.getProjectForeclosure();
         
         String sellerName = projectProperty.getSellerName();//卖方姓名
         String sellerCardNo = projectProperty.getSellerCardNo();//卖方身份证号
         String buyerName = projectProperty.getBuyerName();//买家名称
         String buyerCardNo = projectProperty.getBuyerCardNo();//买家身份证号
         String supersionReceAccount = projectForeclosure.getSuperviseAccount();//监管账号
         String paymentAccount = projectForeclosure.getPaymentAccount();//回款账号
         String paymentName = projectForeclosure.getPaymentName();//回款账号名
         String foreAccount = projectForeclosure.getForeAccount();//赎楼账号
         String idCardNumber = projectForeclosure.getIdCardNumber(); //回款人身份证
         
         Map sellerMap = fillBuyerSellerMap(sellerName, sellerCardNo);
         Map buyerMap = fillBuyerSellerMap(buyerName, buyerCardNo);
         
         result.put("sellerMap", sellerMap);
         result.put("buyerMap", buyerMap);
         result.put("supersionReceAccount", supersionReceAccount);
         result.put("paymentAccount", paymentAccount);
         result.put("foreAccount", foreAccount);
         result.put("idCardNumber", idCardNumber);
         result.put("paymentName", paymentName);
         outputJson(result, response);
      } catch (TException e) {
         logger.error("根据项目id获取收件列表失败：入参：projectId" + projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }

   private Map fillBuyerSellerMap(String nameStr, String cardNoStr) {
      Map result=new HashMap<>();
      if (!StringUtil.isBlank(nameStr)) {
         String[] names = nameStr.split(",");
         String[] cardNos = cardNoStr.split(",");
         for (int i = 0; i < cardNos.length; i++) {
            result.put(names[i], cardNos[i]);
         }
      }
      return result;
   }
   /**
    * 根据查档id获取查档数据
    *@author:liangyanjun
    *@time:2016年3月13日上午12:54:39
    *@param checkDocumentId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getCheckDocument",method= RequestMethod.POST)
   public void getCheckDocument(int checkDocumentId, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      ProjectService.Client projectServiceClient = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      try {
         Map<String, Object> result = new HashMap<>();
         CheckDocumentDTO checkDocument = service.getCheckDocumentById(checkDocumentId);
         ProjectProperty projectProperty = projectServiceClient.getPropertyByProjectId(checkDocument.getProjectId());
         result.put("checkDocument", checkDocument);
         result.put("projectProperty", projectProperty);
         outputJson(result, response);
      } catch (TException e) {
         logger.error("根据查档id获取查档数据失败：入参：checkDocumentId" + checkDocumentId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 修改查档
    *@author:liangyanjun
    *@time:2016年3月13日上午1:04:59
    *@param checkDocumentDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/updateCheckDocument",method= RequestMethod.POST)
   public void updateCheckDocument(CheckDocumentDTO checkDocumentDTO, HttpServletRequest request, HttpServletResponse response) {
      String checkDate = checkDocumentDTO.getCheckDate();//查档时间
      String checkHours = checkDocumentDTO.getCheckHours();//查档精确到时
      int checkStatus = checkDocumentDTO.getCheckStatus();//查档状态：未查档=1，抵押=2，有效=3，无效=4，查封=5，抵押查封=6，轮候查封=7
      int pid = checkDocumentDTO.getPid();
      int projectId = checkDocumentDTO.getProjectId();
      int houseId = checkDocumentDTO.getHouseId();//物业id
      //检查数据是否合法
      if (pid<1||projectId<1||houseId<1||
          StringUtil.isBlank(checkDate)) {
         logger.error("请求数据不合法：" + checkDocumentDTO);
         fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
         return;
      }
      try {
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         boolean isUpdate = checkUpdateCheckDocument(response, project,service);
         if (!isUpdate) {
             return;
         }
         //获取查档信息
         CheckDocumentDTO updateCheckDocumentDTO = service.getCheckDocumentById(pid);
         StringBuffer buffer = new StringBuffer(checkDate);
         buffer = buffer.append(" ").append(checkHours).append(":00:00");
         checkDate =buffer.toString();
         updateCheckDocumentDTO.setCheckDate(checkDate);
         updateCheckDocumentDTO.setCheckStatus(checkStatus);
         updateCheckDocumentDTO.setUpdateId(userId);
         updateCheckDocumentDTO.setCreaterId(userId);
         updateCheckDocumentDTO.setUpdateDate(DateUtils.getCurrentDateTime());
         updateCheckDocumentDTO.setCheckWay(Constants.CHECK_WAY_ARTIFICIAL);
         updateCheckDocumentDTO.setHouseId(houseId);
         //执行更新查档信息
         service.updateCheckDocument(updateCheckDocumentDTO);
         //保存查档记录
         BaseClientFactory CheckDocumentHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
         CheckDocumentHisService.Client CheckDocumentHisService = (CheckDocumentHisService.Client) CheckDocumentHisFactory.getClient();
         CheckDocumentHisService.insert(updateCheckDocumentDTO);
         //往业务动态插入一条记录
         String bizDynamicRemark="查档时间:"+checkDate+",查档状态："+ getSysLookupValByChildType("CHECK_DOCUMENT_STATUS", checkStatus+"").getLookupDesc()+",审批结果："+Constants.CHECK_DOCUMENT_APPROVAL_STATUS_MAP.get(updateCheckDocumentDTO.getApprovalStatus());
         //记录操作日记
         recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_CHECK_DOCUMENT, bizDynamicRemark,projectId);
         fillReturnJson(response, true, "提交成功");
      } catch (Exception e) {
         logger.error("修改查档失败：入参：checkDocumentDTO" + checkDocumentDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
   }
   /**
    * 更新查档审批结果
    *@author:liangyanjun
    *@time:2017年1月4日下午2:27:00
    *@param checkDocumentDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/updateCheckDocumentApprovalStatus",method= RequestMethod.POST)
   public void updateCheckDocumentApprovalStatus(CheckDocumentDTO checkDocumentDTO, HttpServletRequest request, HttpServletResponse response) {
       int approvalStatus = checkDocumentDTO.getApprovalStatus();//审批状态：未审批=1，不通过=2，通过=3
       String remark = checkDocumentDTO.getRemark();//审批意见
       int projectId = checkDocumentDTO.getProjectId();
       //检查数据是否合法
       if (!(approvalStatus==0||approvalStatus==Constants.CHECK_DOCUMENT_APPROVAL_STATUS_1||approvalStatus==Constants.CHECK_DOCUMENT_APPROVAL_STATUS_2||
               approvalStatus==Constants.CHECK_DOCUMENT_APPROVAL_STATUS_3)||projectId<1) {
           logger.error("请求数据不合法：" + checkDocumentDTO);
           fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
           return;
       }
       try {
           ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
           Project project = projectService.getProjectInfoById(projectId);
           ShiroUser shiroUser = getShiroUser();
           Integer userId = shiroUser.getPid();
           Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
           boolean isUpdate = checkUpdateCheckDocument(response, project,service);
           if (!isUpdate) {
               return;
           }
           BaseClientFactory CheckDocumentHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
           CheckDocumentHisService.Client CheckDocumentHisService = (CheckDocumentHisService.Client) CheckDocumentHisFactory.getClient();
           //根据项目id查询该项目的所有物业是否已查档,返回结果为：未查档的物业，多个物业以英文逗号隔开
           String notCheckDocumentHouseName = CheckDocumentHisService.getNotCheckDocumentHouseName(projectId);
           if (!StringUtil.isBlank(notCheckDocumentHouseName)) {
               fillReturnJson(response, false, "提交失败,物业："+notCheckDocumentHouseName+"未查档,请给该物业查档在提交审批结果");
               return;
           }
           //获取查档信息
           CheckDocumentDTO updateCheckDocumentDTO = service.getCheckDocumentByProjectId(projectId);
           CheckDocumentDTO oldCheckDocumentDTO=new CheckDocumentDTO();
           BeanUtils.copyProperties(updateCheckDocumentDTO, oldCheckDocumentDTO);
           updateCheckDocumentDTO.setApprovalStatus(approvalStatus);
           //查档复核已通过后，再修改审批状态为不通过，查档复核状态则修改成“重新复核”
           if ( oldCheckDocumentDTO.getReCheckStatus()==Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_3&&
                   (approvalStatus==Constants.CHECK_DOCUMENT_APPROVAL_STATUS_2||oldCheckDocumentDTO.getCheckStatus()==Constants.CHECK_DOCUMENT_APPROVAL_STATUS_3)) {
               updateCheckDocumentDTO.setReCheckStatus(Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_2);
           }
           updateCheckDocumentDTO.setRemark(remark);
           updateCheckDocumentDTO.setUpdateId(userId);
           updateCheckDocumentDTO.setUpdateDate(DateUtils.getCurrentDateTime());
           service.updateCheckDocument(updateCheckDocumentDTO);
           
           //往业务动态插入一条记录
           String bizDynamicRemark="审批结果："+Constants.CHECK_DOCUMENT_APPROVAL_STATUS_MAP.get(updateCheckDocumentDTO.getApprovalStatus());
           finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_5, bizDynamicRemark);
           fillReturnJson(response, true, "提交成功");
           //记录操作日记
           recordLogOfCheckDocument(updateCheckDocumentDTO, oldCheckDocumentDTO, projectId);
       } catch (Exception e) {
           logger.error("修改查档审批结果失败：入参：checkDocumentDTO" + checkDocumentDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
           fillReturnJson(response, false, "提交失败,请联系管理员!");
           e.printStackTrace();
           return;
       }
   }
   
    /** 
     * 检查是否可以更新查档
     * @author:liangyanjun
     * @time:2017年1月4日下午2:24:01
     * @param response
     * @param project
     * @param service
     * @return
     * @throws ThriftServiceException
     * @throws TException */
   private boolean checkUpdateCheckDocument(HttpServletResponse response, Project project,Client service) throws ThriftServiceException, TException {
       int projectId = project.getPid();
       //检查是否已撤单
       if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
           fillReturnJson(response, false, "提交失败,该项目已经撤单或已经拒单");
           return false;
       }
       //检查是否已完成收件
       if (project.getCollectFileStatus()==Constants.COLLECT_FILE_STATUS_1) {
           fillReturnJson(response, false, "提交失败,要件未收,不可查档!");
           return false;
       }
       //检查是否已经完成执行岗备注
       PerformJobRemark updatePerformJobRemark = service.getPerformJobRemark(projectId);
       if (updatePerformJobRemark.getPid() <= 0) {
           fillReturnJson(response, false, "提交失败,执行岗数据不存在，请联系管理员!");
           return false;
       }
       if (updatePerformJobRemark.getStatus()==Constants.PERFORM_JOB_REMARK_STATUS_1) {
           fillReturnJson(response, false, "提交失败,请在执行岗备注后再查档!");
           return false;
       }
       //检查是否已经完成查诉讼
       CheckLitigationDTO checkLitigation = service.getCheckLitigationByProjectId(projectId);
       if (checkLitigation.getPid() <= 0) {
           fillReturnJson(response, false, "提交失败,查诉讼数据不存在，请联系管理员!");
           return false;
       }
       if (checkLitigation.getApprovalStatus()!=Constants.CHECK_LITIGATION_APPROVAL_STATUS_3) {
           fillReturnJson(response, false, "提交失败,请在查诉讼审批通过后再查档!");
           return false;
       }
       //财务已放款并且无赎楼驳回返回false,此时不可更改查档数据
       if (!checkMakeLoans(project)) {
           fillReturnJson(response, false, "提交失败,财务已放款,不可更改!");
           return false;
       }
    return true;
   }
   /**
    * 查档复核
    * 注：查档复核不同意，则把查档审批状态修改为重新查档
    *@author:liangyanjun
    *@time:2016年5月6日下午3:14:38
    *@param checkDocumentDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/reCheckCheckDocument",method= RequestMethod.POST)
   public void reCheckCheckDocument(CheckDocumentDTO checkDocumentDTO, HttpServletRequest request, HttpServletResponse response) {
      int reCheckStatus = checkDocumentDTO.getReCheckStatus();//查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4
      String reCheckRemark = checkDocumentDTO.getReCheckRemark();//查档复核备注
      int pid = checkDocumentDTO.getPid();
      int projectId = checkDocumentDTO.getProjectId();
      //检查数据是否合法
      if (reCheckStatus<=0||pid<=0||projectId<=0) {
         logger.error("请求数据不合法：" + checkDocumentDTO);
         fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
         return;
      }
      if (reCheckStatus!=Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_3&&reCheckStatus!=Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_4) {
         fillReturnJson(response, false, "提交失败,请选择查档复核状态!");
         return;
      }
      try {
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         //检查是否已撤单
         if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单或已经拒单");
            return;
         }
         //检查是否已经完成查诉讼
         CheckLitigationDTO checkLitigation = service.getCheckLitigationByProjectId(projectId);
         if (checkLitigation.getPid() <= 0) {
            fillReturnJson(response, false, "提交失败,查诉讼数据不存在，请联系管理员!");
            return;
         }
         if (checkLitigation.getApprovalStatus()!=Constants.CHECK_LITIGATION_APPROVAL_STATUS_3) {
            fillReturnJson(response, false, "提交失败,请在查诉讼审批通过后再查档!");
            return;
         }
         //获取查档信息
         CheckDocumentDTO updateCheckDocumentDTO = service.getCheckDocumentByProjectId(projectId);
         //检查查档是否通过
         if (updateCheckDocumentDTO.getPid() <= 0) {
            fillReturnJson(response, false, "提交失败查档数据不存在，请联系管理员!");
            return;
         }
         if (updateCheckDocumentDTO.getApprovalStatus()!=Constants.CHECK_DOCUMENT_APPROVAL_STATUS_3) {
            fillReturnJson(response, false, "提交失败,请在查档审批通过后再查档复核!");
            return;
         }
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         //copy查档信息
         CheckDocumentDTO oldCheckDocumentDTO=new CheckDocumentDTO();
         BeanUtils.copyProperties(updateCheckDocumentDTO, oldCheckDocumentDTO);
         //财务已放款并且无赎楼驳回返回false,此时不可更改查档数据
         if (!checkMakeLoans(project)) {
            fillReturnJson(response, false, "提交失败,财务已放款,不可更改!");
            return;
         }
         updateCheckDocumentDTO.setReCheckStatus(reCheckStatus);
         updateCheckDocumentDTO.setReCheckRemark(reCheckRemark);
         updateCheckDocumentDTO.setReCheckDate(DateUtils.getCurrentDateTime());
         updateCheckDocumentDTO.setReCheckUserId(userId);
         //执行查档复核
         service.reCheckCheckDocument(updateCheckDocumentDTO);
         
         //往业务动态插入一条记录
         String bizDynamicRemark="查档复核状态:"+Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_MAP.get(reCheckStatus)+",备注："+reCheckRemark;
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_6, bizDynamicRemark);
         fillReturnJson(response, true, "提交成功");
         //记录操作日记
         recordLogOfCheckDocument(updateCheckDocumentDTO, oldCheckDocumentDTO, projectId);
      } catch (Exception e) {
         logger.error("查档复核失败：入参：checkDocumentDTO" + checkDocumentDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
   }

   /**
    * 财务已放款并且无赎楼驳回返回false
    * 财务已放款并且无赎楼驳回，此时不可更改查档数据
    *@author:liangyanjun
    *@time:2016年5月6日下午6:05:52
    * @param project
    * @return
    *@throws TException
    */
   private boolean checkMakeLoans(Project project) throws TException {
      int projectId=project.getPid();
      List<Integer> foreclosureStatusList = Arrays.asList(Constants.FORECLOSURE_STATUS_11,Constants.FORECLOSURE_STATUS_12,Constants.FORECLOSURE_STATUS_13);
      BizHandleService.Client bizHandleServiceClient = (BizHandleService.Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
      //贷中数据未生成
      HandleInfoDTO handleInfoDTO = bizHandleServiceClient.getHandleInfoByProjectId(projectId);
      if (handleInfoDTO==null||handleInfoDTO.getPid()<=0) {
         return true;
      }
      FinanceHandleService.Client financeHandleService = (FinanceHandleService.Client) getService(BusinessModule.MODUEL_INLOAN, "FinanceHandleService");
      FinanceHandleDTO financeHandleDTO = financeHandleService.getFinanceHandleByProjectId(projectId);
      int status = financeHandleDTO.getStatus();
      String productNumber = "";
		Product product = new Product();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_PRODUCT, "ProductService");
		ProductService.Client client;
		try {
			client = (ProductService.Client) clientFactory.getClient();
			product = client.findProductByProjectId(projectId);
			productNumber = product.getProductNumber();
		} catch (ThriftException e) {
			e.printStackTrace();
		}
      if (status==Constants.REC_STATUS_LOAN_APPLY &&(!Constants.JY_YSL_TFD.equals(productNumber) && !Constants.FJY_YSL_TFD
				.equals(productNumber))) {
          return false;
      }
      
      int foreclosureStatus = handleInfoDTO.getForeclosureStatus();
      if ((!Constants.JY_YSL_TFD.equals(productNumber) && !Constants.FJY_YSL_TFD
				.equals(productNumber))&&foreclosureStatusList.contains(Integer.valueOf(project.getForeclosureStatus()))&&foreclosureStatus!=Constants.TURN_DOWN_FORECLOSURE) {
         return false;
      }
      
      
      return true;
   }
   /**
    * 查诉讼
    *@author:liangyanjun
    *@time:2016年4月5日下午10:53:24
    *@param checkLitigationDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/updateCheckLitigation",method= RequestMethod.POST)
   public void updateCheckLitigation(CheckLitigationDTO checkLitigationDTO, HttpServletRequest request, HttpServletResponse response) {
      String checkDate = checkLitigationDTO.getCheckDate();//查法院网时间
      String checkHours = checkLitigationDTO.getCheckHours();//查法院网精确到小时
      int checkStatus = checkLitigationDTO.getCheckStatus();//诉讼状态：未查诉讼=1，无新增诉讼=2，有增诉讼=3，有新增诉讼非本人=4
      int approvalStatus = checkLitigationDTO.getApprovalStatus();//审批状态：未审批=1，不通过=2，通过=3，重新查诉讼=4
      String remark = checkLitigationDTO.getRemark();
      int pid = checkLitigationDTO.getPid();
      int projectId = checkLitigationDTO.getProjectId();
      StringBuffer buffer = new StringBuffer(checkDate);
      buffer = buffer.append(" ").append(checkHours).append(":00:00");
      checkDate =buffer.toString();
      //检查数据是否合法
      if (!(approvalStatus==0||approvalStatus==Constants.CHECK_LITIGATION_APPROVAL_STATUS_1||approvalStatus==Constants.CHECK_LITIGATION_APPROVAL_STATUS_2||
          approvalStatus==Constants.CHECK_LITIGATION_APPROVAL_STATUS_3)||pid<1||projectId<1||
          StringUtil.isBlank(checkDate)) {
         logger.error("请求数据不合法：" + checkLitigationDTO);
         fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
         return;
      }
      if (checkStatus==0||checkStatus<=Constants.CHECK_LITIGATION_STATUS_1||checkStatus>Constants.CHECK_LITIGATION_STATUS_4) {
         fillReturnJson(response, false, "提交失败,请选择查诉讼状态!");
         return;
      }
      try {
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         CheckLitigationDTO updateLitigationDTO = service.getCheckLitigationById(pid);
         CheckLitigationDTO oldLitigationDTO=new CheckLitigationDTO();
         BeanUtils.copyProperties(updateLitigationDTO, oldLitigationDTO);
         //检查是否已撤单
         if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单或已经拒单");
            return;
         }
         //财务已放款并且无赎楼驳回返回false,此时不可更改查诉讼数据
         if (!checkMakeLoans(project)) {
            fillReturnJson(response, false, "提交失败,财务已申请放款或已放款,不可更改!");
            return;
         }
         //获取查档信息
         CheckDocumentDTO checkDocumentDTO = service.getCheckDocumentByProjectId(projectId);
         //查档复核已通过后，再修改查诉讼审批状态为不通过，查档复核状态则修改成“重新复核”
         if (checkDocumentDTO.getReCheckStatus()==Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_3&&
             (approvalStatus==Constants.CHECK_LITIGATION_APPROVAL_STATUS_2)) {
            checkDocumentDTO.setReCheckStatus(Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_2);
            service.updateCheckDocument(checkDocumentDTO);
         }
         //诉讼状态：未查诉讼=1，无新增诉讼=2，有增诉讼=3，有新增诉讼非本人=4
         updateLitigationDTO.setApprovalStatus(approvalStatus);
         updateLitigationDTO.setCheckDate(checkDate);
         updateLitigationDTO.setCheckStatus(checkStatus);
         updateLitigationDTO.setRemark(remark);
         updateLitigationDTO.setUpdateId(userId);
         service.updateCheckLitigation(updateLitigationDTO);
         
         //保存查诉讼记录
         BaseClientFactory CheckDocumentHisFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckLitigationHisService");
         CheckLitigationHisService.Client checkLitigationHisService = (CheckLitigationHisService.Client) CheckDocumentHisFactory.getClient();
         updateLitigationDTO.setCheckWay(Constants.CHECK_WAY_ARTIFICIAL);
         updateLitigationDTO.setCreaterId(userId);
         checkLitigationHisService.insert(updateLitigationDTO);
         recordLogOfCheckLitigation(updateLitigationDTO, oldLitigationDTO, projectId);
         
         //往业务动态插入一条记录
         String bizDynamicRemark="查法院网时间:"+checkDate+",诉讼情况："+Constants.CHECK_LITIGATION_STATUS_MAP.get(checkStatus)+",审批结果："+Constants.CHECK_LITIGATION_APPROVAL_STATUS_MAP.get(approvalStatus)+",审批意见："+remark;
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_4, bizDynamicRemark);
         fillReturnJson(response, true, "提交成功");
      } catch (Exception e) {
         logger.error("修改查诉讼失败：入参：checkLitigationDTO" + checkLitigationDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
   }
   /**
    * 更新执行岗备注
    *@author:liangyanjun
    *@time:2016年4月5日下午10:55:54
    *@param performJobRemark
    *@param request
    *@param response
    */
   @RequestMapping(value = "/updatePerformJobRemark",method= RequestMethod.POST)
   public void updatePerformJobRemark(PerformJobRemark performJobRemark, HttpServletRequest request, HttpServletResponse response) {
      String remarkDate = performJobRemark.getRemarkDate();
      String remark = performJobRemark.getRemark();
      int projectId = performJobRemark.getProjectId();
      // 检查数据是否合法
      if (projectId < 1 || StringUtil.isBlank(remarkDate)) {
         logger.error("请求数据不合法：" + performJobRemark);
         fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
         return;
      }
      try {
         ProjectService.Client projectService = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         //检查是否已撤单
         if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单或已经拒单");
            return;
         }
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         List<Integer> foreclosureStatusList = Arrays.asList(Constants.FORECLOSURE_STATUS_11, Constants.FORECLOSURE_STATUS_12, Constants.FORECLOSURE_STATUS_13);
         if (foreclosureStatusList.contains(Integer.valueOf(project.getForeclosureStatus()))) {
            fillReturnJson(response, false, "提交失败,财务已放款,不可更改!");
            return;
         }
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         PerformJobRemark updatePerformJobRemark = service.getPerformJobRemark(projectId);
         if (updatePerformJobRemark.getPid() <= 0) {
            fillReturnJson(response, false, "提交失败,执行岗数据不存在，请联系管理员!");
            return;
         }
         updatePerformJobRemark.setRemark(remark);
         updatePerformJobRemark.setRemarkDate(remarkDate);
         updatePerformJobRemark.setUpdateId(userId);
         updatePerformJobRemark.setStatus(Constants.PERFORM_JOB_REMARK_STATUS_2);
         service.updatePerformJobRemark(updatePerformJobRemark);
         
         //往业务动态插入一条记录
         String bizDynamicRemark="备注日期:"+remarkDate+",备注："+remark;
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_3, bizDynamicRemark);
         fillReturnJson(response, true, "提交成功");
      } catch (Exception e) {
         logger.error("执行岗备注失败：入参：performJobRemark" + performJobRemark + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,出现未知异常请联系管理员!");
         e.printStackTrace();
         return;
      }
   }
   /**
    * 根据id获取查诉讼
    *@author:liangyanjun
    *@time:2016年3月30日下午3:49:59
    *@param checkLitigationId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getCheckLitigation",method= RequestMethod.POST)
   public void getCheckLitigation(int checkLitigationId, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         CheckLitigationDTO checkLitigationDTO = service.getCheckLitigationById(checkLitigationId);
         outputJson(checkLitigationDTO, response);
      } catch (TException e) {
         logger.error("根据id获取查诉讼数据失败：入参：checkLitigationId" + checkLitigationId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 根据类型获取买卖用户名和身份证
    *@author:liangyanjun
    *@time:2016年4月2日上午2:14:31
    *@param projectId
    *@param type
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getBuyerOrSeller",method= RequestMethod.POST)
   public void getBuyerOrSeller(int projectId,int type, HttpServletRequest request, HttpServletResponse response) {
      ProjectService.Client service = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      try {
         Project project = service.getLoanProjectByProjectId(projectId);
         ProjectProperty projectProperty = project.getProjectProperty();
         HashMap<String, String> hashMap = new HashMap<>();
         if (type==1) {
            hashMap.put("name", projectProperty.getSellerName());
            hashMap.put("cardNo", projectProperty.getSellerCardNo());
         }else{
            hashMap.put("name", projectProperty.getBuyerName());
            hashMap.put("cardNo", projectProperty.getBuyerCardNo());
         }
         outputJson(hashMap, response);
      } catch (TException e) {
         logger.error("根据类型获取买卖用户名和身份证失败。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 获取要件打印信息
    *@author:liangyanjun
    *@time:2016年5月19日下午5:45:54
    *@param model
    *@param projectId
    *@param buyerSellerType
    *@param buyerSellerName
    *@return
    */
   @RequestMapping(value = "/toCollectFilePrint")
   public String toCollectFilePrint(ModelMap model, int printType, int projectId, int buyerSellerType, String buyerSellerName) {
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         CollectFilePrintInfo collectFilePrintInfo = new CollectFilePrintInfo();
         collectFilePrintInfo.setProjectId(projectId);
         collectFilePrintInfo = service.getCollectFilePrintInfo(collectFilePrintInfo);
         String printLoanMoney = "";
         if(collectFilePrintInfo.getLoanMoney() != 0){
        	 //打印页面会显示科学计数法转换成string即可避免
        	 printLoanMoney = CommonUtil.formatDoubleNumber(collectFilePrintInfo.getLoanMoney());
        	 collectFilePrintInfo.setPrintLoanMoney(printLoanMoney);
         }
         
         collectFilePrintInfo.setPrintType(printType);
         collectFilePrintInfo.setBuyerSellerType(buyerSellerType);
         if (buyerSellerType == 1) {//买卖类型：买方=1，卖方=2
            collectFilePrintInfo.setBuyerName(buyerSellerName);
         } else {
            collectFilePrintInfo.setSellerName(buyerSellerName);
         }
         model.put("collectFilePrintInfo", collectFilePrintInfo);
      } catch (TException e) {
         logger.error("获取要件打印信息;入参：printType："+printType+",projectId:"+projectId+",buyerSellerType:"+buyerSellerType+",buyerSellerName:"+buyerSellerName+"错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
      return PAGE_PATH + "collect_file_print";
   }
   /**
    * 查档文件查询列表
    *@author:liangyanjun
    *@time:2016年10月14日下午4:20:44
    *@param query
    *@param request
    *@param response
    */
   @RequestMapping(value = "/checkDocumentFileList")
   public void checkDocumentFileList(CheckDocumentFile query, HttpServletRequest request, HttpServletResponse response) {
      Map<String, Object> map = new HashMap<String, Object>();
      BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentFileService");
      List<CheckDocumentFile> list = null;
      int total = 0;
      try {
         // 查询要件查档列表
          CheckDocumentFileService.Client client = (CheckDocumentFileService.Client) factory.getClient();
         list = client.queryCheckDocumentFile(query);
         total = client.getCheckDocumentFileTotal(query);
         logger.info("查档文件查询列表查询成功：total：" + total + ",checkDocumentIndexList:" + list);
      } catch (Exception e) {
         logger.error("获取查档文件列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + query);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
      destroyFactory(factory);
   }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年10月14日下午5:45:28
    *@param checkDocumentFile
    *@param req
    *@param resp
    *@throws Exception
    */
   @RequestMapping("/uploadCheckDocumentFile")
   public void uploadCheckDocumentFile(CheckDocumentFile checkDocumentFile,HttpServletRequest req, HttpServletResponse resp) throws Exception {
      Map<String, Object> resultMap = FileUtil.uploadFile2(req, resp, "system", getAomUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
      List<BizFile> bizFileList = (List<BizFile>) resultMap.get("bizFileList");
      if (bizFileList == null || bizFileList.isEmpty()) {
         return;
      }
      int projectId = checkDocumentFile.getProjectId();
      BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentFileService");
      CheckDocumentFileService.Client client = (CheckDocumentFileService.Client) factory.getClient();
      client.saveCheckDocumentFile(checkDocumentFile, bizFileList);
      recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_UPLOAD_FILE, "",projectId);
      destroyFactory(factory);
   }
   /**
    * 批量删除查档文件
    *@author:liangyanjun
    *@time:2016年10月17日下午2:39:01
    *@param idsStr
    *@param projectId
    *@param req
    *@param resp
    *@throws Exception
    */
   @RequestMapping("/deleteCheckDocumentFile")
    public void deleteCheckDocumentFile(String idsStr, int projectId, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (StringUtil.isBlank(idsStr)||projectId<=0) {
            fillReturnJson(resp, false, "删除失败，参数不全");
            return;
        }
        try {
            String[] ids = idsStr.split(",");
            if (ids.length == 0) {
                fillReturnJson(resp, false, "删除失败，参数不全");
                return;
            }
            List<Integer> idList = new ArrayList<>();
            for (String id : ids) {
                idList.add(Integer.parseInt(id));
            }
            BaseClientFactory checkDocumentFileFactory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentFileService");
            CheckDocumentFileService.Client checkDocumentFileService = (CheckDocumentFileService.Client) checkDocumentFileFactory.getClient();
            BaseClientFactory ProjectFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
            ProjectService.Client projectService = (ProjectService.Client) ProjectFactory.getClient();
            Project project = projectService.getProjectInfoById(projectId);
            int foreclosureStatus = project.getForeclosureStatus();
            //检查是否已归档，已归档不能删除
            if (foreclosureStatus==Constants.FORECLOSURE_STATUS_13) {
                fillReturnJson(resp, false, "已归档（解保）项目，不能删除");
                return;
            }
            //执行删除关联
            checkDocumentFileService.deleteByIds(idList);
            recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_DELETE, "删除查档文件：idsStr" + idsStr,projectId);
            destroyFactory(checkDocumentFileFactory,ProjectFactory);
            fillReturnJson(resp, true, "删除成功");
        } catch (Exception e) {
            logger.error("批量删除查档文件失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：projectId"+projectId+",idsStr" + idsStr);
            e.printStackTrace();
            fillReturnJson(resp, false, "删除失败,请联系管理员!");
        }
    }
   /**
    * 查询查档历史列表（分页查询）
    *@author:liangyanjun
    *@time:2017年1月5日下午2:49:18
    *@param checkDocument
    *@param request
    *@param response
    */
   @RequestMapping(value = "/checkDocumentHisList")
   public void checkDocumentHisList(CheckDocumentDTO checkDocument,HttpServletResponse response) {
      Map<String, Object> map = new HashMap<String, Object>();
      List<CheckDocumentDTO> checkDocumentHisList = null;
      int total = 0;
      try {
         // 查询查档历史列表
         CheckDocumentHisService.Client service = (CheckDocumentHisService.Client) getService(BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
         checkDocumentHisList = service.queryCheckDocumentHisIndex(checkDocument);
         total = service.getCheckDocumentHisIndexTotal(checkDocument);
      } catch (Exception e) {
         logger.error("获取查档历史列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + checkDocument);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", checkDocumentHisList);
      map.put("total", total);
      outputJson(map, response);
   }
   /**
    * 获取查诉讼历史列表（分页查询）
    *@author:liangyanjun
    *@time:2017年1月16日上午10:18:43
    *@param query
    *@param response
    */
   @RequestMapping(value = "/checkLitigationHisList")
   public void checkLitigationHisList(CheckLitigationDTO query,HttpServletResponse response) {
       Map<String, Object> map = new HashMap<String, Object>();
       List<CheckLitigationDTO> checkLitigationHisList = null;
       int total = 0;
       try {
           // 查询查档历史列表
           CheckLitigationHisService.Client service = (CheckLitigationHisService.Client) getService(BusinessModule.MODUEL_INLOAN, "CheckLitigationHisService");
           checkLitigationHisList = service.queryCheckLitigationHisIndex(query);
           total = service.getCheckLitigationHisIndexTotal(query);
       } catch (Exception e) {
           logger.error("获取查诉讼历史列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + query);
           e.printStackTrace();
       }
       // 输出
       map.put("rows", checkLitigationHisList);
       map.put("total", total);
       outputJson(map, response);
   }
   /**
    * 保存查诉讼记录
    *@author:liangyanjun
    *@time:2017年1月13日上午10:33:57
    *@param checkLitigation
    *@param request
    *@param response
    */
    @RequestMapping(value = "/saveCheckLitigationHis",method= RequestMethod.POST)
    public void saveCheckLitigationHis(CheckLitigationDTO checkLitigation, HttpServletRequest request, HttpServletResponse response) {
        String checkDate = checkLitigation.getCheckDate();//查法院网时间
        int checkStatus = checkLitigation.getCheckStatus();//诉讼状态：未查诉讼=1，无新增诉讼=2，有增诉讼=3，有新增诉讼非本人=4
        int approvalStatus = checkLitigation.getApprovalStatus();//审批状态：未审批=1，不通过=2，通过=3，重新查诉讼=4
        int projectId = checkLitigation.getProjectId();
        //检查数据是否合法
        if (!(approvalStatus == 0 || approvalStatus == Constants.CHECK_LITIGATION_APPROVAL_STATUS_1
                || approvalStatus == Constants.CHECK_LITIGATION_APPROVAL_STATUS_2 || approvalStatus == Constants.CHECK_LITIGATION_APPROVAL_STATUS_3)
                ||  projectId < 1 || StringUtil.isBlank(checkDate)) {
            logger.error("请求数据不合法：" + checkLitigation);
            fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
            return;
        }
        if (checkStatus == 0 || checkStatus <= Constants.CHECK_LITIGATION_STATUS_1 || checkStatus > Constants.CHECK_LITIGATION_STATUS_4) {
            fillReturnJson(response, false, "提交失败,请选择查诉讼状态!");
            return;
        }
        ShiroUser shiroUser = getShiroUser();
        Integer userId = shiroUser.getPid();
        try {
            BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckLitigationHisService");
            CheckLitigationHisService.Client client = (CheckLitigationHisService.Client) factory.getClient();
            checkLitigation.setCheckWay(Constants.CHECK_WAY_ARTIFICIAL);
            checkLitigation.setCreaterId(userId);
            checkLitigation.setUpdateId(userId);
            client.insert(checkLitigation);
            fillReturnJson(response, true, "提交成功");
            destroyFactory(factory);
        } catch (Exception e) {
            logger.error("保存查诉讼记录失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + checkLitigation);
            fillReturnJson(response, false, "提交异常，请联系管理员");
        }
    }
    /**
     * 保存查档记录
     *@author:liangyanjun
     *@time:2017年1月13日上午10:46:45
     *@param checkDocument
     *@param request
     *@param response
     */
    @RequestMapping(value = "/saveCheckDocumentHis",method= RequestMethod.POST)
    public void saveCheckDocumentHis(CheckDocumentDTO checkDocument, HttpServletRequest request, HttpServletResponse response) {
       String checkDate = checkDocument.getCheckDate();//查档时间
       String checkHours = checkDocument.getCheckHours();
       int checkStatus = checkDocument.getCheckStatus();//查档状态：未查档=1，抵押=2，有效=3，无效=4，查封=5，抵押查封=6，轮候查封=7
       int projectId = checkDocument.getProjectId();
       int houseId = checkDocument.getHouseId();//物业id
       //检查数据是否合法
       if (projectId<1||houseId<1||
           StringUtil.isBlank(checkDate)) {
          logger.error("请求数据不合法：" + checkDocument);
          fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
          return;
       }
       try {
          ShiroUser shiroUser = getShiroUser();
          Integer userId = shiroUser.getPid();
          //保存查档记录
          BaseClientFactory factory = getFactory(BusinessModule.MODUEL_INLOAN, "CheckDocumentHisService");
          CheckDocumentHisService.Client client = (CheckDocumentHisService.Client) factory.getClient();
          checkDocument.setUpdateId(userId);
          checkDocument.setCreaterId(userId);
          checkDocument.setCheckWay(Constants.CHECK_WAY_ARTIFICIAL);
          client.insert(checkDocument);
          fillReturnJson(response, true, "提交成功");
          destroyFactory(factory);
       } catch (Exception e) {
          logger.error("修改查档失败：入参：checkDocumentDTO" + checkDocument + "。错误：" + ExceptionUtil.getExceptionMessage(e));
          fillReturnJson(response, false, "提交异常,请联系管理员!");
          return;
       }
    }   
   private void recordLogOfCheckDocument(CheckDocumentDTO updateCheckDocument, CheckDocumentDTO oldCheckDocumentDTO,int projectId) throws TException {
      HashMap<CheckDocumentDTO._Fields, String> hashMap = new HashMap<CheckDocumentDTO._Fields,String>();
      hashMap.put(CheckDocumentDTO._Fields.CHECK_DATE, "查档时间");
      hashMap.put(CheckDocumentDTO._Fields.CHECK_STATUS, "查档状态");
      hashMap.put(CheckDocumentDTO._Fields.APPROVAL_STATUS, "审批状态");
      hashMap.put(CheckDocumentDTO._Fields.REMARK, "备注");
      hashMap.put(CheckDocumentDTO._Fields.RE_CHECK_STATUS, "查档复核状态");
      hashMap.put(CheckDocumentDTO._Fields.RE_CHECK_REMARK, "查档复核备注");
      Set<CheckDocumentDTO._Fields> keySet = hashMap.keySet();
      StringBuffer logsb=new StringBuffer();
      //检查值是否更新，更新了则写入日志
      for (CheckDocumentDTO._Fields key : keySet) {
         Object updateFieldValue = updateCheckDocument.getFieldValue(key);//修改后的值
         Object oldFieldValue = oldCheckDocumentDTO.getFieldValue(key);//原来的值
         if ((updateFieldValue==null||StringUtil.isBlank(updateFieldValue.toString()))&&(oldFieldValue==null||StringUtil.isBlank(oldFieldValue.toString()))) {
            continue;
         }
         if (updateFieldValue==null||!updateFieldValue.equals(oldFieldValue)) {
            String fieldName = hashMap.get(key);
            if ("查档状态".equals(fieldName)) {
               logsb.append("<br>-"+fieldName+":("+Constants.CHECK_DOCUMENT_STATUS_MAP.get(oldFieldValue)+")更改为("+Constants.CHECK_DOCUMENT_STATUS_MAP.get(updateFieldValue)+") ");
            }else if("审批状态".equals(fieldName)){
               logsb.append("<br>-"+fieldName+":("+Constants.CHECK_LITIGATION_APPROVAL_STATUS_MAP.get(oldFieldValue)+")更改为("+Constants.CHECK_DOCUMENT_APPROVAL_STATUS_MAP.get(updateFieldValue)+") ");
            }else if("查档复核状态".equals(fieldName)){
               logsb.append("<br>-"+fieldName+":("+Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_MAP.get(oldFieldValue)+")更改为("+Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_MAP.get(updateFieldValue)+") ");
            }else{
               logsb.append("<br>-"+fieldName+":("+oldFieldValue+")更改为("+updateFieldValue+") ");
            }
         }
      }
      //没有更新内容，则不写入日志
      if (logsb.length()==0) {
         return;
      }
      logsb.insert(0, "查档信息:");
      recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_CHECK_DOCUMENT, logsb.toString(),projectId);
   }
   private void recordLogOfCheckLitigation(CheckLitigationDTO updateCheckLitigation, CheckLitigationDTO oldCheckLitigation,int projectId) throws TException {
      HashMap<CheckLitigationDTO._Fields, String> hashMap = new HashMap<CheckLitigationDTO._Fields,String>();
      hashMap.put(CheckLitigationDTO._Fields.CHECK_DATE, "查法院网时间");
      hashMap.put(CheckLitigationDTO._Fields.CHECK_STATUS, "查诉讼情况");
      hashMap.put(CheckLitigationDTO._Fields.APPROVAL_STATUS, "审批状态");
      hashMap.put(CheckLitigationDTO._Fields.REMARK, "备注");
      Set<CheckLitigationDTO._Fields> keySet = hashMap.keySet();
      StringBuffer logsb=new StringBuffer();
      //检查值是否更新，更新了则写入日志
      for (CheckLitigationDTO._Fields key : keySet) {
         Object updateFieldValue = updateCheckLitigation.getFieldValue(key);//修改后的值
         Object oldFieldValue = oldCheckLitigation.getFieldValue(key);//原来的值
         if ((updateFieldValue==null||StringUtil.isBlank(updateFieldValue.toString()))&&(oldFieldValue==null||StringUtil.isBlank(oldFieldValue.toString()))) {
            continue;
         }
         if (updateFieldValue==null||!updateFieldValue.equals(oldFieldValue)) {
            String fieldName = hashMap.get(key);
            if ("查诉讼情况".equals(fieldName)) {
               logsb.append("<br>-"+fieldName+":("+Constants.CHECK_LITIGATION_STATUS_MAP.get(oldFieldValue)+")更改为("+Constants.CHECK_LITIGATION_STATUS_MAP.get(updateFieldValue)+") ");
            }else if("审批状态".equals(fieldName)){
               logsb.append("<br>-"+fieldName+":("+Constants.CHECK_DOCUMENT_APPROVAL_STATUS_MAP.get(oldFieldValue)+")更改为("+Constants.CHECK_DOCUMENT_APPROVAL_STATUS_MAP.get(updateFieldValue)+") ");
            }else{
               logsb.append("<br>-"+fieldName+":("+oldFieldValue+")更改为("+updateFieldValue+") ");
            }
         }
      }
      //没有更新内容，则不写入日志
      if (logsb.length()==0) {
         return;
      }
      logsb.insert(0, "查诉讼信息:");
      recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_CHECK_LITIGATION, logsb.toString(),projectId);
   }
}
