package com.xlkfinance.bms.web.controller.inloan;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.SysLogTypeConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.MoneyFormatUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleIndexDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BalanceConfirmDTO;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService.Client;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.inloan.ForeclosureIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicMap;
import com.xlkfinance.bms.rpc.inloan.HandleFlowDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.HouseBalanceDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.rpc.inloan.OverdueFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundDetailsDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.BizFileService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;
import com.xlkfinance.bms.web.util.FileUtil;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月16日上午12:22:34 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务办理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/bizHandleController")
public class BizHandleController extends BaseController {
   private static final String PAGE_PATH = "inloan/bizHandle/";
   private Logger logger = LoggerFactory.getLogger(BizHandleController.class);
   private final String serviceName = "BizHandleService";
   /**
    * 业务办理查询跳转
    */
   @RequestMapping(value = "/index")
   public String toIndex(ModelMap model) {
      return PAGE_PATH + "index";
   }
   /**
    * 从任务跳转到业务办理列表，并定位数据
    *@author:liangyanjun
    *@time:2016年5月21日上午1:06:03
    *@param model
    *@param projectName
    *@return
    * @throws TException 
    * @throws ThriftServiceException 
    */
   @RequestMapping(value = "/toIndexByTask")
   public String toIndexByTask(ModelMap model,int projectId) throws ThriftServiceException, TException {
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      Project projectInfo = projectService.getProjectInfoById(projectId);
      model.put("projectName", projectInfo.getProjectName());
      return PAGE_PATH + "index";
   }
   /**
    *  从任务跳转到赎楼列表，并定位数据
    *@author:liangyanjun
    *@time:2016年5月23日上午9:13:04
    *@param model
    *@param projectId
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping(value = "/toForeclosureIndexByTask")
   public String toForeclosureIndexByTask(ModelMap model,int projectId) throws ThriftServiceException, TException {
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      Project projectInfo = projectService.getProjectInfoById(projectId);
      model.put("projectName", projectInfo.getProjectName());
      return PAGE_PATH + "foreclosure_index";
   }
   /**
    * 赎楼页面跳转
    *@author:liangyanjun
    *@time:2016年3月11日下午2:26:14
    *@param model
    *@return
    */
   @RequestMapping(value = "/foreclosure_index")
   public String toForeclosureIndex(ModelMap model) {
      return PAGE_PATH + "foreclosure_index";
   }
   /**
    * 跳转到预警列表
    *@author:liangyanjun
    *@time:2016年5月14日上午10:07:03
    *@param model
    *@return
    */
   @RequestMapping(value = "/differ_warn_index")
   public String toDifferWarnIndex(ModelMap model) {
      return PAGE_PATH + "differ_warn_index";
   }

   /**
    * 业务办理查询列表
    *@author:liangyanjun
    *@time:2016年1月16日上午12:28:21
    *@param applyHandleIndexDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/list")
   @ResponseBody
   public void list(ApplyHandleIndexDTO applyHandleIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (applyHandleIndexDTO==null)applyHandleIndexDTO=new ApplyHandleIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<ApplyHandleIndexDTO> list = null;
      int total = 0;
      // 设置数据权限--用户编号list
      applyHandleIndexDTO.setUserIds(getUserIds(request));
      try {
         // 查询业务办理列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         list = service.findAllApplyHandleIndex(applyHandleIndexDTO);
         total = service.getApplyHandleIndexTotal(applyHandleIndexDTO);
         logger.info("业务办理查询列表查询成功：total：" + total + ",financeHandleDtoList:" + list);
      } catch (Exception e) {
         logger.error("获取业务办理列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + applyHandleIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
   }
   /**
    * 获取办理动态对应的条数
    *@author:liangyanjun
    *@time:2016年4月27日下午3:05:42
    *@param applyHandleIndexDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/qeuryHandleDynamicCountMapList")
   @ResponseBody
   public void qeuryHandleDynamicCountMapList(ApplyHandleIndexDTO applyHandleIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      Map<String, Object> result = new HashMap<String, Object>();
      int total = 0;
      try {
         // 查询业务办理列表
         applyHandleIndexDTO.setUserIds(getUserIds(request));
         Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
         List<HandleDynamicMap> handleDynamicMapList = client.qeuryHandleDynamicCountMapList(applyHandleIndexDTO);
         // 输出
         result.put("resultList", handleDynamicMapList);
         outputJson(result, response);
         logger.info("获取办理动态对应的条数成功：total：" + total + ",handleDynamicMapList:" + handleDynamicMapList);
      } catch (Exception e) {
         logger.error("获取办理动态对应的条数失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + applyHandleIndexDTO);
         e.printStackTrace();
      }
   }
   /**
    * 赎楼列表分页查询
    *@author:liangyanjun
    *@time:2016年3月11日下午4:40:18
    *@param foreclosureIndexDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/foreclosureIndexList")
   public void foreclosureIndexList(ForeclosureIndexDTO foreclosureIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (foreclosureIndexDTO==null)foreclosureIndexDTO=new ForeclosureIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<ForeclosureIndexDTO> foreclosureIndexList = null;
      int total = 0;
      // 设置数据权限--用户编号list
      foreclosureIndexDTO.setUserIds(getUserIds(request));
      // 过滤放款的状态
      List<Integer> recFeeStatusList=new ArrayList<Integer>();
      recFeeStatusList.add(Constants.REC_STATUS_ALREADY_LOAN);
      recFeeStatusList.add(Constants.REC_STATUS_LOAN_NO_FINISH);
      foreclosureIndexDTO.setRecFeeStatusList(recFeeStatusList);
      try {
         // 查询赎楼列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         foreclosureIndexList = service.queryForeclosureIndex(foreclosureIndexDTO);
         total = service.getForeclosureIndexTotal(foreclosureIndexDTO);
      } catch (Exception e) {
         logger.error("获取赎楼列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + foreclosureIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", foreclosureIndexList);
      map.put("total", total);
      outputJson(map, response);
   }
   /** 编辑 */
   @RequestMapping(value = "/{handleId}/edit")
   public String edit(ModelMap model, @PathVariable java.lang.Integer handleId, HttpServletRequest request, HttpServletResponse response) {
      String url = PAGE_PATH + "edit";
      // 判断id是否合法
      if (handleId == null || handleId <= 0) {
         logger.error("ID非法："+ handleId);
         return url;
      }
      try {
       //填充业务办理编辑用的数据
         fillEditData(model, handleId, request);
      } catch (Exception e) {
         logger.error("查询办理数据失败.handleId=" + handleId + "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         return url;
      }
      return url;
   }

   /**
    * 填充业务办理编辑用的数据
    * 包括：申请业务处理信息，赎楼及余额回转信息，办理动态数据，可以处理的办理流程条目ID集合，财务退款明细
    *@author:liangyanjun
    *@time:2016年1月29日下午5:40:52
    *@param model
    *@param handleId
    *@param request
    *@throws Exception
    */
   private void fillEditData(ModelMap model, java.lang.Integer handleId, HttpServletRequest request) throws Exception {
      Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
      ShiroUser user = getShiroUser();//获取登录用户
      String userName = user.getUserName();
      String realName = user.getRealName();
      
      // .填充申请业务处理主表信息
      HandleInfoDTO handleInfoQuery=new HandleInfoDTO();
      handleInfoQuery.setPid(handleId);
      List<HandleInfoDTO> handleInfoList = client.findAllHandleInfoDTO(handleInfoQuery);
      HandleInfoDTO handleInfo = handleInfoList.get(0);
      model.put("handleInfo", handleInfo);
      logger.info("业务处理主表信息查询成功：handleInfo:" + handleInfo);
      
      // .填充项目信息
      int projectId = handleInfo.getProjectId();
      Project projectInfo = projectService.getProjectInfoById(projectId);
      model.put("projectId", projectId);
      model.put("projectNumber", projectInfo.getProjectNumber());
      model.put("projectName", projectInfo.getProjectName());
      logger.info("项目信息查询成功：projectInfo:" + projectInfo);
      
      // .填充申请业务处理信息
      ApplyHandleInfoDTO applyHandleInfoDTO = new ApplyHandleInfoDTO();
      applyHandleInfoDTO.setHandleId(handleId);
      List<ApplyHandleInfoDTO> applyHandleInfoList = client.findAllApplyHandleInfo(applyHandleInfoDTO);
      model.put("applyHandleInfo", applyHandleInfoList.get(0));
      logger.info("申请业务处理信息查询成功：applyHandleInfoList:" + applyHandleInfoList);
      
      // .填充赎楼及余额回转信息
      Map<Long, HouseBalanceDTO> houseBalanceMap=new HashMap<Long, HouseBalanceDTO>();
      HouseBalanceDTO houseBalanceDTO = new HouseBalanceDTO();
      houseBalanceDTO.setHandleId(handleId);
      List<HouseBalanceDTO> houseBalanceList = client.findAllHouseBalance(houseBalanceDTO);
      for (HouseBalanceDTO dto : houseBalanceList) {
         houseBalanceMap.put(new Long(dto.getCount()), dto);
      }
      model.put("houseBalanceMap", houseBalanceMap);
      
      // .根据业务处理ID和用户名，获取可以处理的办理流程条目ID集合
      List<Integer> canHandleFlowIdList = client.getCanHandleFlowByHandleId(handleId, user.getUserName());
      model.put("canHandleFlowIdList", canHandleFlowIdList);
      
      // .填充办理流程条目数据
      HandleFlowDTO handleFlowDTO = new HandleFlowDTO();
      List<HandleFlowDTO> handleFlowList = client.findAllHandleFlow(handleFlowDTO);
      model.put("handleFlowList", handleFlowList);
      // .处理当前用户可办理的流程，可以办理设置为true
      if (canHandleFlowIdList!=null&&!canHandleFlowIdList.isEmpty()) {
         for (int i = 0; i < handleFlowList.size(); i++) {
            HandleFlowDTO dto = handleFlowList.get(i);
            if (canHandleFlowIdList.contains(dto.getPid()))dto.setCanHandle(true);
         }
      }
      
      // .填充办理动态数据
      int sumHandleDay=0;//操作总天数=所有的操作天数总和
      Map<Long, HandleDynamicDTO> handleDynamicMap=new HashMap<Long, HandleDynamicDTO>();
      HandleDynamicDTO handleDynamicDTO = new HandleDynamicDTO();
      handleDynamicDTO.setHandleId(handleId);
      List<HandleDynamicDTO> handleDynamicList = client.findAllHandleDynamic(handleDynamicDTO);
      for (int i = 0; i < handleDynamicList.size(); i++) {
          HandleDynamicDTO dto=handleDynamicList.get(i);
         //正在办理的办理动态，则计算差异
         if (canHandleFlowIdList!=null&&canHandleFlowIdList.contains(dto.getHandleFlowId())) {
            int handleFlowId = dto.getHandleFlowId();
            //操作天数：操作天数=创建时间-当前时间
            String createDate = dto.getCreateDate();
            int dayDifference = DateUtils.dayDifference(DateUtils.stringToDate(createDate), new Date());
            dto.setHandleDay(dayDifference);
            //计算差异：1.差异=规定天数-（当前时间-开始时间）>0=0
            //       2.差异=规定天数-（当前时间-开始时间）<0=规定天数-（当前时间-开始时间）
            HandleFlowDTO flowDTO = getHandleFlow(handleFlowList, handleFlowId);
            int differ = calculateDiffer(dayDifference, flowDTO);
            dto.setDiffer(differ);
            dto.setOperator(realName);
            sumHandleDay=dayDifference+sumHandleDay;
            //办理人
            Set<Map<String,String>> handleUserSet = client.getHandleUser(handleId, dto.getHandleFlowId(),userName);
            model.put("handleUserSet", handleUserSet);
         }
         sumHandleDay=dto.getHandleDay()+sumHandleDay;
         handleDynamicMap.put(new Long(dto.getHandleFlowId()), dto);
      }
      model.put("sumHandleDay", sumHandleDay);//操作总天数
      model.put("handleDynamicMap", handleDynamicMap);
      
      // .填充财务退款明细
      Map<Long, RefundDetailsDTO> refundDetailsMap=new HashMap<Long, RefundDetailsDTO>();
      RefundDetailsDTO refundDetailsDTO = new RefundDetailsDTO();
      refundDetailsDTO.setHandleId(handleId);
      List<RefundDetailsDTO> refundDetailsList = client.findAllRefundDetails(refundDetailsDTO);
      for (RefundDetailsDTO dto : refundDetailsList) {
         if (StringUtil.isBlank(dto.getOperator())) {
            dto.setOperator(realName);
         }
         refundDetailsMap.put(new Long(dto.getRefundPro()), dto);
      }
      model.put("refundDetailsMap", refundDetailsMap);
      
      //财务退款明细:退款项目
      model.put("refundProMap", Constants.REFUND_PRO_MAP);
      logger.info("财务退款明细查询成功：refundDetailsList:" + refundDetailsList);
   }

   private HandleFlowDTO getHandleFlow(List<HandleFlowDTO> handleFlowList, int flowId) {
      for (HandleFlowDTO flowDTO : handleFlowList) {
         if (flowDTO.pid==flowId) {
            return flowDTO;
         }
      }
      return null;
   }
   /**
    * 根据handleId获取正在执行中的办理动态信息，没有正在执行中的办理动态返回null
    *@author:liangyanjun
    *@time:2016年4月29日上午9:18:06
    *@param model
    *@param handleId
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getHandleDynamicInfo", method = RequestMethod.POST)
   public void getHandleDynamicInfo(int handleId, HttpServletRequest request, HttpServletResponse response) {
      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      SysUser user = getSysUser();// 获取登录用户
      Map<String, Object> result=new HashMap<>();
      try {
         HandleDynamicDTO dto = getHandleDynamicInfoByhandleId(handleId, client);
         if (dto==null) {
            outputJson(null, response);
            return;
         }
         Set<Map<String,String>> handleUserSet = client.getHandleUser(handleId, dto.getHandleFlowId(),user.getUserName());
         result.put("handleDynamic", dto);
         result.put("handleUserSet", handleUserSet);
         outputJson(result, response);
      } catch (Exception e) {
         logger.error("根据id获取办理动态信息失败：入参："+ handleId + "，错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   /**
    * 根据办理id和当前用户，获取当前用户的办理动态，并且计算差异和操作天数
    *@author:liangyanjun
    *@time:2016年5月9日下午4:20:21
    *@param handleId
    *@param client
    *@return
    *@throws TException
    */
   private HandleDynamicDTO getHandleDynamicInfoByhandleId(int handleId, Client client) throws TException {
      SysUser user = getSysUser();// 获取登录用户
      String realName = user.getRealName();
      // .根据业务处理ID和办理用户名，获取可以处理的办理流程条目ID集合
      List<Integer> canHandleFlowIdList = client.getCanHandleFlowByHandleId(handleId, user.getUserName());
      if (canHandleFlowIdList==null||canHandleFlowIdList.size()==0) {
         return null;
      }
      return handleData(handleId, client, realName, canHandleFlowIdList);
   }
   /**
    * 计算差异和操作天数
    * @param handleId
    * @param client
    * @param realName
    * @param canHandleFlowIdList
    * @return
    * @throws TException
    */
   private HandleDynamicDTO handleData(int handleId, Client client, String realName,
		List<Integer> canHandleFlowIdList) throws TException {
	//根据办理id和办理流程id获取办理动态
      HandleDynamicDTO query=new HandleDynamicDTO();
      query.setHandleId(handleId);
      query.setHandleFlowId(canHandleFlowIdList.get(0));
      List<HandleDynamicDTO> handleDynamicList = client.findAllHandleDynamic(query);
      //获取所有的办理流程
      List<HandleFlowDTO> handleFlowList = client.findAllHandleFlow(new HandleFlowDTO());
      
      HandleDynamicDTO dto = handleDynamicList.get(0);
      // 正在办理的办理动态，则计算差异
      if (canHandleFlowIdList != null && canHandleFlowIdList.contains(dto.getHandleFlowId())) {
         int handleFlowId = dto.getHandleFlowId();
         // 操作天数：操作天数=创建时间-当前时间
         String createDate = dto.getCreateDate();
         int dayDifference = DateUtils.dayDifference(DateUtils.stringToDate(createDate), new Date());
         dto.setHandleDay(dayDifference);
         // 计算差异：1.差异=规定天数-（当前时间-开始时间）>0=0
         // 2.差异=规定天数-（当前时间-开始时间）<0=规定天数-（当前时间-开始时间）
         HandleFlowDTO flowDTO = getHandleFlow(handleFlowList, handleFlowId);
         int differ = calculateDiffer(dayDifference, flowDTO);
         dto.setDiffer(differ);
         dto.setOperator(realName);
         dto.setFixDay(flowDTO.getFixDay());
      }
      return dto;
   }
  
   /**
    * 修改申请办理状态：
    * 只有已完成才能把状态改为已归档
    *@author:liangyanjun
    *@time:2016年1月18日下午4:42:01
    *@return
    */
   @RequestMapping(value = "/cancelGuarantee", method = RequestMethod.POST)
   public void cancelGuarantee(int projectId,HttpServletRequest request, HttpServletResponse response) {
      // 检查id和申请办理状态是否合法
      if (projectId <= 0 ) {
         fillReturnJson(response, false, "归档失败,请求数据不合法!");
         logger.error("请求数据不合法："+projectId);
         return;
      }
      Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      try {
         // 获取要修改的业务处理信息
         HandleInfoDTO query = new HandleInfoDTO();
         query.setProjectId(projectId);
         List<HandleInfoDTO> handleInfoList = client.findAllHandleInfoDTO(query);
         if (handleInfoList == null || handleInfoList.size() == 0) {
            fillReturnJson(response, false, "归档失败,请联系管理员!");
            logger.error("业务处理信息数据不存在："+ projectId);
            return;
         }
         HandleInfoDTO updateDandleInfoDTO = handleInfoList.get(0);
         //获取项目信息
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         if (project.getForeclosureStatus()==Constants.FORECLOSURE_STATUS_13) {
            fillReturnJson(response, false, "项目已经归档，请勿重复提交");
            return;
         }
         // 只有完成保后监管才能把状态改为已归档
         if (updateDandleInfoDTO.getApplyHandleStatus() != Constants.APPLY_HANDLE_STATUS_3 ) {
            fillReturnJson(response, false, "归档失败,只有完成保后监管才能把状态改为已归档!");
            return;
         }
         //检查是否已回款完毕
         RepaymentService.Client repaymentService = (RepaymentService.Client) getService(BusinessModule.MODUEL_INLOAN, "RepaymentService");
         RepaymentDTO repaymentDTO = repaymentService.getRepaymentByProjectId(projectId);
         if (repaymentDTO.getStatus()!=Constants.REPAYMENT_STATUS_2) {
            fillReturnJson(response, false, "未回款，不能归档");
            return;
         }
         //检查逾期费是否已确认
         OverdueFeeDTO overdueFee = repaymentService.getOverdueFeeByProjectId(projectId);
         if (overdueFee.getIsConfirm()==Constants.NO_CONFIRM_OVERDUE_FEE) {
            fillReturnJson(response, false, "归档失败,逾期费未确认!");
            return;
         }
         // 检查是否有退手续费正在审核中，或审核通过待确认的
         RefundFeeService.Client refundFeeService = (RefundFeeService.Client) getService(BusinessModule.MODUEL_INLOAN, "RefundFeeService");
         RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
         refundFeeQuery.setProjectId(projectId);
         List<RefundFeeDTO> refundFeeList = refundFeeService.findAllRefundFee(refundFeeQuery);
         for (int i = 0; i < refundFeeList.size(); i++) {
            RefundFeeDTO refundFeeDTO = refundFeeList.get(i);
            int applyStatus = refundFeeDTO.getApplyStatus();
            int isConfirm = refundFeeDTO.getIsConfirm();
            if (applyStatus > Constants.APPLY_REFUND_FEE_STATUS_2 && applyStatus <= Constants.APPLY_REFUND_FEE_STATUS_4) {
               fillReturnJson(response, false, "归档失败,有退费流程正在审核中");
               return;
            } else if (applyStatus == Constants.APPLY_REFUND_FEE_STATUS_5 && isConfirm == Constants.NO_CONFIRM) {
               fillReturnJson(response, false, "归档失败,有退费审批待确认");
               return;
            }
         }
         // 设置归档状态
         updateDandleInfoDTO.setApplyHandleStatus(Constants.APPLY_HANDLE_STATUS_4);
         // 执行更新
         client.updateHandleInfo(updateDandleInfoDTO);
         // 执行解保
         String cancelGuaranteeDate = DateUtils.getCurrentDateTime();
         projectService.cancelGuarantee(projectId, cancelGuaranteeDate);
         // 往业务动态插入一条记录
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_21, "解保时间：" + cancelGuaranteeDate);
         logger.info("归档成功：修改作者ID:" + getShiroUser().getPid() + "，参数：" + updateDandleInfoDTO);
         fillReturnJson(response, true, "归档成功!");
      } catch (Exception e) {
         logger.error("归档失败：入参："+ projectId +  "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "归档失败,请联系管理员!");
         e.printStackTrace();
      }
   }

   /**
    * 根据ID更新申请业务处理信息 
    *@author:liangyanjun
    *@time:2016年1月18日下午6:24:22
    *@param applyHandleInfoDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/updateApplyHandleInfo", method = RequestMethod.POST)
   public void updateApplyHandleInfo(ApplyHandleInfoDTO applyHandleInfoDTO, HttpServletRequest request, HttpServletResponse response) {
      if (applyHandleInfoDTO==null){fillReturnJson(response, false, "提交失败,请重新操作!");return;}
      //获取参数
      ShiroUser shiroUser = getShiroUser();
      int pid = applyHandleInfoDTO.getPid();//
      int handleId = applyHandleInfoDTO.getHandleId();//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
//      String subDate = applyHandleInfoDTO.getSubDate();//合同及放款确认书提交时间
      String contactPerson = applyHandleInfoDTO.getContactPerson();//联系人
      String contactPhone = applyHandleInfoDTO.getContactPhone();//联系电话
      String handleDate = applyHandleInfoDTO.getHandleDate();//办理时间
      String specialCase = applyHandleInfoDTO.getSpecialCase();//特殊情况说明
      String remark = applyHandleInfoDTO.getRemark();//备注
      //检查数据
      if (pid <= 0 ||handleId<=0|| StringUtil.isBlank(contactPerson, contactPhone, handleDate)) {
         logger.error("请求数据不合法：" + applyHandleInfoDTO);
         fillReturnJson(response, false, "提交失败,请重新操作!");
         return;
      }
      try {
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         //查询业务处理信息
         HandleInfoDTO handleInfoQuery=new HandleInfoDTO();
         handleInfoQuery.setPid(handleId);
         List<HandleInfoDTO> handleInfoDTOList = client.findAllHandleInfoDTO(handleInfoQuery);
         if (handleInfoDTOList==null||handleInfoDTOList.isEmpty()) {
            return;
         }
         
         //检查申请业务处理信息是否可以更新（申请办理状态为已完成和已归档，不可修改）
         HandleInfoDTO handleInfoDTO = handleInfoDTOList.get(0);
         if (handleInfoDTO.getApplyHandleStatus()==Constants.APPLY_HANDLE_STATUS_3||handleInfoDTO.getApplyHandleStatus()==Constants.APPLY_HANDLE_STATUS_4) {
            fillReturnJson(response, false, "提交失败,申请办理状态为已完成和已归档，不可修改,请重新操作!");
            return;
         }
         
         //查询项目信息
         int projectId = handleInfoDTO.getProjectId();
         Project projectInfo = projectService.getProjectInfoById(projectId);
         int pmUserId = projectInfo.getPmUserId();
         //判断修改的是否为项目的业务经理
         if (shiroUser.getPid()!=pmUserId) {
            fillReturnJson(response, false, "提交失败,非业务经理，不可修改!");
            return;
         }
         
         //查询要修改的申请业务处理信息 
         ApplyHandleInfoDTO query = new ApplyHandleInfoDTO();
         query.setPid(pid);
         List<ApplyHandleInfoDTO> applyHandleInfoList = client.findAllApplyHandleInfo(query);
         if (applyHandleInfoList == null || applyHandleInfoList.size() <= 0) {
            logger.error("修改的申请业务处理信息数据不存在："+ pid);
            fillReturnJson(response, false, "提交失败,请重新操作!");
            return;
         }
         //设置修改的值
         ApplyHandleInfoDTO updateApplyHandleInfoDTO = applyHandleInfoList.get(0);
         ApplyHandleInfoDTO oldApplyHandleInfoDTO=new ApplyHandleInfoDTO();
         BeanUtils.copyProperties(updateApplyHandleInfoDTO, oldApplyHandleInfoDTO);
//         updateApplyHandleInfoDTO.setSubDate(subDate);
         updateApplyHandleInfoDTO.setContactPerson(contactPerson);
         updateApplyHandleInfoDTO.setContactPhone(contactPhone);
         updateApplyHandleInfoDTO.setHandleDate(handleDate);
         updateApplyHandleInfoDTO.setSpecialCase(specialCase);
         updateApplyHandleInfoDTO.setRemark(remark);
         //执行修改
         client.updateApplyHandleInfo(updateApplyHandleInfoDTO);
         logger.info("修改申请业务处理信息成功：修改作者ID:"+shiroUser.getPid()+"，参数："+updateApplyHandleInfoDTO);
         
         recordLogOfApplyHandle(handleId, client, updateApplyHandleInfoDTO, oldApplyHandleInfoDTO);
      } catch (Exception e) {
         logger.error("修改申请业务处理信息失败：修改作者ID:"+getShiroUser().getPid()+"，入参："+applyHandleInfoDTO+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         fillReturnJson(response, false, "提交失败,请重新操作!");
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }

   
   
   /**
    * 提交办理反馈：
    * 办理反馈有值则设置（更新）办理反馈内容
    * 办理反馈内容为空则更新办理时间
    *@author:liangyanjun
    *@time:2016年1月26日上午10:43:07
    *@param applyHandleInfoDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/submitFeedback")
   @ResponseBody
   public String submitFeedback(ApplyHandleInfoDTO applyHandleInfoDTO, HttpServletRequest request, HttpServletResponse response) {
      if (applyHandleInfoDTO==null)  return "-1";
      //获取参数
      int handleId = applyHandleInfoDTO.getHandleId();//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
      String handleDate = applyHandleInfoDTO.getHandleDate();//办理时间
      String feedback = applyHandleInfoDTO.getFeedback();
      //检查数据
      if (handleId<=0|| StringUtil.isBlank(handleDate)) {
         logger.error("请求数据不合法：" + applyHandleInfoDTO);
         return "-1";
      }
      try {
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         //检查申请业务处理信息是否可以更新（申请办理状态为已完成和已归档，不可修改）
         if (!isUpdate(handleId)) {
            return "-1";
         }
         //根据业务处理ID获取申请业务处理信息
         ApplyHandleInfoDTO applyHandleInfoQuery=new ApplyHandleInfoDTO();
         applyHandleInfoQuery.setHandleId(handleId);
         List<ApplyHandleInfoDTO> applyHandleInfoList = client.findAllApplyHandleInfo(applyHandleInfoQuery);
         if (applyHandleInfoList==null||applyHandleInfoList.isEmpty()) {
            return "-1";
         }
         //如果办理反馈为空，则修改办理时间，否则修改反馈内容
         ApplyHandleInfoDTO updateApplyHandleInfo = applyHandleInfoList.get(0);
         ApplyHandleInfoDTO oldApplyHandleInfoDTO=new ApplyHandleInfoDTO();
         BeanUtils.copyProperties(updateApplyHandleInfo, oldApplyHandleInfoDTO);
         if (StringUtil.isBlank(feedback)) {
            updateApplyHandleInfo.setHandleDate(handleDate);
         }else{
            updateApplyHandleInfo.setFeedback(feedback);
         }
         //执行修改
         client.updateApplyHandleInfo(updateApplyHandleInfo);
         //记录日志
         recordLogOfApplyHandle(handleId, client, updateApplyHandleInfo, oldApplyHandleInfoDTO);
         logger.info("修改申请业务处理信息成功：参数："+updateApplyHandleInfo);
      } catch (Exception e) {
         logger.error("修改申请业务处理信息失败：入参："+applyHandleInfoDTO+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         return "-1";
      }
      return "1";
   }
   /**
    * 根据ID更新赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月18日下午7:39:25
    *@param houseBalanceDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/updateHouseBalance", method = RequestMethod.POST)
   public void updateHouseBalance(HouseBalanceDTO houseBalanceDTO, HttpServletRequest request, HttpServletResponse response) {
      if (houseBalanceDTO==null){fillReturnJson(response, false, "提交失败,请重新操作!");return;}
      //获取参数
      int pid = houseBalanceDTO.getPid();
      int handleId = houseBalanceDTO.getHandleId();//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
      double principal = houseBalanceDTO.getPrincipal();//赎楼本金（付款金额）
      String payDate = houseBalanceDTO.getPayDate();//付款日期
      String houseClerk = houseBalanceDTO.getHouseClerk();//赎楼员
      double interest = houseBalanceDTO.getInterest();//利息
      double defaultInterest = houseBalanceDTO.getDefaultInterest();//罚息
      double balance = houseBalanceDTO.getBalance();//赎楼余额
      String backAccount = houseBalanceDTO.getBackAccount();//转回账号
      int count = houseBalanceDTO.getCount();//赎楼次数
      String remark = houseBalanceDTO.getRemark();
      //检查入参
      if (pid<=0||handleId<=0||principal<0||count<0||count>2||StringUtil.isBlank(payDate,houseClerk)) {
         logger.error("请求数据不合法：" + houseBalanceDTO);
         fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,请重新操作!");
         return;
      }
      try {
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         //检查赎楼及余额回转信息是否可以更新（申请办理状态为已完成和已归档，不可修改）
         if (!isUpdate(handleId)) {
            fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,状态为已完成和已归档，不可修!");
            return;
         }
         //获取要修改的数据
         HouseBalanceDTO houseBalanceQuery=new HouseBalanceDTO();
         houseBalanceQuery.setPid(pid);
//         houseBalanceQuery.setUserIds(getUserIds(request));
         List<HouseBalanceDTO> houseBalanceList = client.findAllHouseBalance(houseBalanceQuery);
         if (houseBalanceList==null||houseBalanceList.isEmpty()) {
            logger.error("修改的赎楼及余额回转信息数据不存在：", pid);
            fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,请重新操作!");
            return;
         }
         //设置修改的值
         HouseBalanceDTO updateHouseBalanceDTO = houseBalanceList.get(0);
         HouseBalanceDTO oldHouseBalanceDTO=new HouseBalanceDTO();
         BeanUtils.copyProperties(updateHouseBalanceDTO, oldHouseBalanceDTO);
         updateHouseBalanceDTO.setPrincipal(principal);
         updateHouseBalanceDTO.setPayDate(payDate);
         updateHouseBalanceDTO.setHouseClerk(houseClerk);
         updateHouseBalanceDTO.setInterest(interest);
         updateHouseBalanceDTO.setDefaultInterest(defaultInterest);
         updateHouseBalanceDTO.setBalance(balance);
         updateHouseBalanceDTO.setBackAccount(backAccount);
         updateHouseBalanceDTO.setCount(count);
         updateHouseBalanceDTO.setRemark(remark);
         //执行更新
         client.updateHouseBalance(updateHouseBalanceDTO);
         //记录日志
         recordLogOfHouseBalance(handleId, client, updateHouseBalanceDTO, oldHouseBalanceDTO);
         logger.info("修改的赎楼及余额回转信息成功：修改作者ID:"+getShiroUser().getPid()+"，参数："+updateHouseBalanceDTO);
      } catch (Exception e) {
         logger.error("修改赎楼及余额回转信息失败：入参："+houseBalanceDTO+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         fillReturnJson(response, false, "第"+count+"次赎楼及余额回转提交失败,请重新操作!");
         return;
      }
      fillReturnJson(response, true, "第"+count+"次赎楼及余额回转 提交成功");
   }
   /**
    * 根据ID更新财务退款明细
    *@author:liangyanjun
    *@time:2016年1月19日上午8:53:12
    *@param refundDetailsDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/updateRefundDetails")
   @ResponseBody
   public String updateRefundDetails(RefundDetailsDTO refundDetailsDTO, HttpServletRequest request, HttpServletResponse response) {
      if (refundDetailsDTO==null) return "-1";
      //获取参数
      ShiroUser shiroUser = getShiroUser();
      String realName = shiroUser.getRealName();
      int pid = refundDetailsDTO.getPid();
      int handleId = refundDetailsDTO.getHandleId();//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
      double refundMoney = refundDetailsDTO.getRefundMoney();//退款金额
      String recAccount = refundDetailsDTO.getRecAccount();//收款账号
      String recName = refundDetailsDTO.getRecName();//收款户名
      String payDate = refundDetailsDTO.getPayDate();//付款日期
      //检查入参
      if (pid<=0||handleId<=0||refundMoney<0||StringUtil.isBlank(recAccount,recName,payDate)) {
         logger.error("请求数据不合法：" + refundDetailsDTO);
         return "-1";
      }
      try {
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         //检查财务退款明细是否可以更新（申请办理状态为已完成和已归档，不可修改）
         if (!isUpdate(handleId)) {
            return "-1";
         }
         RefundDetailsDTO refundDetailsQuery=new RefundDetailsDTO();
         refundDetailsQuery.setPid(pid);
//         refundDetailsQuery.setUserIds(getUserIds(request));
         List<RefundDetailsDTO> refundDetailList = client.findAllRefundDetails(refundDetailsQuery);
         if (refundDetailList==null||refundDetailList.isEmpty()) {
            logger.error("修改的财务退款明细数据不存在：", pid);
            return "-1";
         }
         //设置修改的值
         RefundDetailsDTO updateRefundDetailsDTO = refundDetailList.get(0);
         RefundDetailsDTO oldRefundDetailsDTO = new RefundDetailsDTO();
         BeanUtils.copyProperties(updateRefundDetailsDTO, oldRefundDetailsDTO);
         updateRefundDetailsDTO.setRefundMoney(refundMoney);
         updateRefundDetailsDTO.setRecAccount(recAccount);
         updateRefundDetailsDTO.setRecName(recName);
         updateRefundDetailsDTO.setPayDate(payDate);
         updateRefundDetailsDTO.setOperator(realName);
         //执行更新
         client.updateRefundDetails(updateRefundDetailsDTO);
         //记录日志
         logger.info("修改财务退款明细成功：修改作者ID:"+getShiroUser().getPid()+"，参数："+updateRefundDetailsDTO);
         recordLogOfRefundDetails(handleId, client, updateRefundDetailsDTO, oldRefundDetailsDTO);
      } catch (Exception e) {
         logger.error("修改财务退款明细信息失败：入参："+refundDetailsDTO+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         return "-1";
      }
      return "1";
   }
   /**
    * 根据ID更新办理动态
    *@author:liangyanjun
    *@time:2016年1月19日上午9:08:29
    *@param handleDynamicDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/updateHandleDynamic", method = RequestMethod.POST)
   public void updateHandleDynamic(HandleDynamicDTO handleDynamicDTO, HttpServletRequest request, HttpServletResponse response) {
      if (handleDynamicDTO==null){ fillReturnJson(response, false, "提交失败,请重新操作!");return;}
      //获取参数
      int pid = handleDynamicDTO.getPid();
      int handleId = handleDynamicDTO.getHandleId();//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
      String finishDate = handleDynamicDTO.getFinishDate();//完成日期
      String remark = handleDynamicDTO.getRemark();//备注
      int currentHandleUserId = handleDynamicDTO.getCurrentHandleUserId();//办理人
      //检查入参
      if (pid<=0||handleId<=0||currentHandleUserId<=0||StringUtil.isBlank(finishDate)) {
         logger.error("请求数据不合法：" + handleDynamicDTO);
         fillReturnJson(response, false, "提交失败,请重新操作!");
         return;
      }
      try {
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         //检查办理动态是否可以更新（申请办理状态为已完成和已归档，不可修改）
         if (!isUpdate(handleId)) {
            fillReturnJson(response, false, "提交失败,申请办理状态为已完成和已归档，不可修,请重新操作!");
            return;
         }
         //查询要修改的办理动态
         HandleDynamicDTO handleDynamicQuery=new HandleDynamicDTO();
         handleDynamicQuery.setPid(pid);
//         handleDynamicQuery.setUserIds(getUserIds(request));
         List<HandleDynamicDTO> handleDynamicList = client.findAllHandleDynamic(handleDynamicQuery);
         if (handleDynamicList==null||handleDynamicList.isEmpty()) {
            logger.error("修改的办理动态数据不存在：", pid);
            fillReturnJson(response, false, "提交失败,请重新操作!");
            return;
         }
         HandleDynamicDTO updateHandleDynamicDTO = handleDynamicList.get(0);
         //检查当前用户是否有权限处理该办理动态
         ShiroUser user = getShiroUser();
         List<Integer> canHandleFlowIdList = client.getCanHandleFlowByHandleId(handleId, user.getUserName());
         int handleFlowId = updateHandleDynamicDTO.getHandleFlowId();
         if (!canHandleFlowIdList.contains(handleFlowId)) {
            logger.error("修改办理动态信息失败：修改作者ID:"+user.getPid()+"无权限修改该办理动态.入参："+handleDynamicDTO);
            fillReturnJson(response, false, "提交失败,无权限修改!");
            return;
         }
         int projectId = client.getProjectIdByHandleId(handleId);
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project project = projectService.getProjectInfoById(projectId);
         //检查是否已撤单
         if (project.getIsChechan()==Constants.PROJECT_IS_CHECHAN) {
            fillReturnJson(response, false, "提交失败,该项目已经撤单");
            return;
         }
         HouseBalanceDTO houseBalanceQuery=new HouseBalanceDTO();
         houseBalanceQuery.setHandleId(handleId);
         houseBalanceQuery.setBalanceConfirm(Constants.NO_BALANCE_CONFIRM);
         int houseBalanceTotal = client.getHouseBalanceTotal(houseBalanceQuery);
         //检查赎楼余额是否已确认
         if(handleFlowId==Constants.HANDLE_FLOW_ID_2&&houseBalanceTotal>1){
            fillReturnJson(response, false, "提交失败：该项目赎楼余额未确认，不能完成赎楼办理节点!");
            return;
            //如果是办理回款节点，则检查是否已回款和逾期费是否确认
         }
         SysUser sysUser = getSysUser();

         //设置修改的值
         updateHandleDynamicDTO.setFinishDate(finishDate);
         updateHandleDynamicDTO.setOperator(sysUser.getRealName());
         updateHandleDynamicDTO.setRemark(remark);
         updateHandleDynamicDTO.setCurrentHandleUserId(currentHandleUserId);
         //操作天数：操作天数=创建时间-当前时间
         String createDate = updateHandleDynamicDTO.getCreateDate();
         int dayDifference = DateUtils.dayDifference(DateUtils.stringToDate(createDate), new Date());
         updateHandleDynamicDTO.setHandleDay(dayDifference);
         List<HandleFlowDTO> handleFlowList=client.findAllHandleFlow(new HandleFlowDTO());
         //计算差异：1.差异=规定天数-（当前时间-开始时间）>0=0
         //       2.差异=规定天数-（当前时间-开始时间）<0=规定天数-（当前时间-开始时间）
         HandleFlowDTO flowDTO = getHandleFlow(handleFlowList, handleFlowId);
         int differ = calculateDiffer(dayDifference, flowDTO);
         updateHandleDynamicDTO.setDiffer(differ);
         
         //执行更新并完成任务
         client.updateHandleDynamicAndFinishTask(updateHandleDynamicDTO, sysUser.getUserName());
         
         //往业务动态插入一条记录
         SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
         SysUser currentHandleUser = sysUserService.getSysUserByPid(currentHandleUserId);
         String bizDynamicRemark="完成日期:"+finishDate+",操作天数:"+dayDifference+",办理人:"+currentHandleUser.getRealName()+",规定天数:"+flowDTO.getFixDay()+",差异:"+differ+",备注:"+remark;
         if (handleFlowId==Constants.HANDLE_FLOW_ID_3) {
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_10, bizDynamicRemark);
         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_4){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_11, bizDynamicRemark);
         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_5){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_12, bizDynamicRemark);
         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_6){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_13, bizDynamicRemark);
         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_7){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_14, bizDynamicRemark);
         }else if(handleFlowId==Constants.HANDLE_FLOW_ID_10){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_15, bizDynamicRemark);
         }
         
         //记录操作日记
         recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, 
               " 修改"+flowDTO.getName()+"-完成日期    更改为："+updateHandleDynamicDTO.getFinishDate()+
               ",-操作天数    更改为："+updateHandleDynamicDTO.getHandleDay()+
               ",-操作人    更改为："+updateHandleDynamicDTO.getOperator()+
               ",-差异    更改为："+updateHandleDynamicDTO.getDiffer()+
               ",-备注    更改为："+updateHandleDynamicDTO.getRemark()
               ,projectId);
         logger.info("修改办理动态信息成功：修改作者ID:"+user.getPid()+"，参数："+updateHandleDynamicDTO);
      } catch (Exception e) {
         logger.error("修改的办理动态信息失败：入参："+handleDynamicDTO+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         fillReturnJson(response, false, "提交失败,请重新操作!");
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }

   /**
    *计算差异：1.差异=规定天数-（当前时间-开始时间）>0=0
    *       2.差异=规定天数-（当前时间-开始时间）<0=规定天数-（当前时间-开始时间）
    *@author:liangyanjun
    *@time:2016年3月6日上午10:41:00
    *@param dayDifference
    *@param flowDTO
    *@return
    */
   private int calculateDiffer(int dayDifference, HandleFlowDTO flowDTO) {
      int differ = flowDTO.getFixDay()-dayDifference;
      if (differ>0) {
         differ=0;
      }else{
         differ=differ*-1;
      }
      return differ;
   }

   /**
    * 根据办理动态id查询办理动态文件列表
    *@author:liangyanjun
    *@time:2016年1月25日下午6:56:34
    *@param applyHandleIndexDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/uploanFilelist")
   @ResponseBody
   public void uploanFilelist(int handleId,int page,int rows, HttpServletRequest request, HttpServletResponse response) {
      Map<String, Object> map = new HashMap<String, Object>();
      List<HandleDynamicFileDTO> list = new ArrayList<>();
      int total = 0;
      //检查业务处理ID是否合法
      if (handleId<=0) {
         map.put("rows", list);
         map.put("total", total);
         outputJson(map, response);
         return;
      }
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         // 根据办理动态id查询办理动态文件列表
         HandleDynamicFileDTO handleDynamicFileQuery = new HandleDynamicFileDTO();
         handleDynamicFileQuery.setHandleId(handleId);
         handleDynamicFileQuery.setStatus(Constants.STATUS_ENABLED);
         handleDynamicFileQuery.setPage(page);
         handleDynamicFileQuery.setRows(rows);
         list = service.findAllHandleDynamicFile(handleDynamicFileQuery);
         total = service.getHandleDynamicFileTotal(handleDynamicFileQuery);
         // 输出
         map.put("rows", list);
         map.put("total", total);
         outputJson(map, response);
      } catch (Exception e) {
         logger.error("根据办理动态id查询办理动态文件列表失败.更改者=" + getShiroUser().getRealName()+",handleId=" + handleId + "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
   }
   
   /**
    * 上传文件
    *@author:liangyanjun
    *@time:2016年1月25日下午5:31:35
    *@param request
    *@param response
    *@param handleDynamicDTO
    *@return
    */
   @RequestMapping(value = "/uploadFile")
   @ResponseBody
   public int uploadFile(HttpServletRequest request, HttpServletResponse response,HandleDynamicFileDTO handleDynamicFileDTO) {
      int handleDynamicId = handleDynamicFileDTO.getHandleDynamicId();
      String remark = handleDynamicFileDTO.getRemark();
      if (handleDynamicId<=0||StringUtil.isBlank(remark))return -1;
      try {
         BizFile bizFile = saveFile(request, response, remark);
         if (bizFile==null||bizFile.getFileSize()<=0) {
            return -1;
         }
         // 执行保存
         Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         client.addHandleDynamicFile(handleDynamicFileDTO, bizFile);
        
         int projectId = client.getProjectIdByHandleId(handleDynamicFileDTO.getHandleId());
         recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, " 上传文件："+bizFile.fileName+"."+bizFile.fileType,projectId);
      } catch (Exception e) {
         logger.error("上传办理动态文件列表失败.更改者=" + getShiroUser().getRealName()+ "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         return -1;
      } 
      return 1;
   }
   /**
    * 上传赎楼资料文件
    *@author:liangyanjun
    *@time:2016年3月16日下午6:32:09
    *@param request
    *@param response
    *@param bizHandleId
    */
   @RequestMapping(value = "/uploadForeclosureFile")
   public void uploadForeclosureFile(HttpServletRequest request, HttpServletResponse response,int bizHandleId) {
      try {
         Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         BizFile bizFile = saveFile(request, response, "赎楼资料文件");
         if (bizFile==null||bizFile.getFileSize()<=0) {
            fillReturnJson(response, false, "上传失败,请重新操作!");
            return;
         }
         HandleDynamicDTO handleDynamicQuery=new HandleDynamicDTO();
         handleDynamicQuery.setHandleId(bizHandleId);
         handleDynamicQuery.setHandleFlowId(Constants.HANDLE_FLOW_ID_2);
         List<HandleDynamicDTO> handleDynamicList = client.findAllHandleDynamic(handleDynamicQuery);
         if (handleDynamicList==null||handleDynamicList.isEmpty()) {
            fillReturnJson(response, false, "上传失败,请重新操作!");
            return;
         }
         HandleDynamicDTO handleDynamicDTO = handleDynamicList.get(0);
         int pid = handleDynamicDTO.getPid();
         HandleDynamicFileDTO handleDynamicFileDTO = new HandleDynamicFileDTO();
         handleDynamicFileDTO.setHandleDynamicId(pid);
         // 执行保存
         client.addHandleDynamicFile(handleDynamicFileDTO, bizFile);
         
         int projectId = client.getProjectIdByHandleId(handleDynamicFileDTO.getHandleId());
         recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, " 上传文件："+bizFile.fileName+"."+bizFile.fileType,projectId);
      } catch (Exception e) {
         e.printStackTrace();
         fillReturnJson(response, false, "上传失败,请重新操作!");
         return;
      } 
      fillReturnJson(response, true, "上传成功");
      return;
   }
   
   private BizFile saveFile(HttpServletRequest request, HttpServletResponse response, String remark) throws ServletException, IOException {
      // 文件信息BIZ_FILE
      BizFile bizFile = new BizFile();
      Map<String, Object> map = new HashMap<String, Object>();
      map = FileUtil.processFileUpload(request, response, BusinessModule.MODUEL_INLOAN, getUploadFilePath(), getFileSize(), getFileDateDirectory(), getUploadFileType());
      @SuppressWarnings("rawtypes")
      List items = (List) map.get("items");
      for (int i = 0; i < items.size(); i++) {
         FileItem item = (FileItem) items.get(i);
         String fieldName = item.getFieldName();
         fieldName=fieldName.replaceAll(" ", "");
         if ("offlineMeetingFile".equals(fieldName)) {
               if (item.getSize() != 0) {
                  bizFile.setFileSize((int) item.getSize());
                  // 获得文件名
                  String fileFullName = item.getName().toLowerCase();
                  int dotLocation = fileFullName.lastIndexOf(".");
                  String fileName = fileFullName.substring(0, dotLocation).replaceAll(" ", "");
                  String fileType = fileFullName.substring(dotLocation).substring(1);
                  bizFile.setFileType(fileType);
                  bizFile.setFileName(fileName);
               }
         }
      }
     
      if (bizFile.getFileSize() == 0) {
         return bizFile;
      }
      // 文件信息设置值
      String uploadDttm = DateUtil.format(new Date());
      bizFile.setUploadDttm(uploadDttm);
      String fileUrl = String.valueOf(map.get("path"));
      if (fileUrl == null || "null".equalsIgnoreCase(fileUrl)) {
         return bizFile;
      }
      bizFile.setFileUrl(fileUrl);
      int uploadUserId = getShiroUser().getPid();
      bizFile.setUploadUserId(uploadUserId);
      bizFile.setStatus(Constants.STATUS_ENABLED);
      bizFile.setRemark(remark);
      return bizFile;
   }
   /**
    * 把文件的状态修改为“无效”
    *@author:liangyanjun
    *@time:2016年1月25日下午11:29:26
    *@param request
    *@param response
    *@param fileIds
    *@return
    */
   @RequestMapping(value = "/deleteFile")
   public void deleteFile(HttpServletRequest request, HttpServletResponse response,String fileIds) {
      if (StringUtil.isBlank(fileIds)) {
          fillReturnJson(response, false, "删除失败,参数不合法!");
          return;
      }
      BizFileService.Client bizFileServiceClient = (BizFileService.Client) getService(BusinessModule.MODUEL_SYSTEM, "BizFileService");
      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         String[] fileIdArr = fileIds.split(",");
         for (int i = 0; i < fileIdArr.length; i++) {
            int fileId=Integer.parseInt(fileIdArr[i]);
            //根据办理动态文件关联的fileID查询出办理动态id
            HandleDynamicFileDTO handleDynamicFileQeury=new HandleDynamicFileDTO();
            handleDynamicFileQeury.setFileId(fileId);
            handleDynamicFileQeury.setStatus(Constants.STATUS_ENABLED);
            List<HandleDynamicFileDTO> handleDynamicFileList = client.findAllHandleDynamicFile(handleDynamicFileQeury);
            if (handleDynamicFileList==null||handleDynamicFileList.isEmpty()) {
                fillReturnJson(response, false, "删除失败,文件不存在!");
                return;
            }
            HandleDynamicFileDTO handleDynamicFileDTO = handleDynamicFileList.get(0);
            //根据办理动态id查询出办理动态信息
            HandleDynamicDTO handleDynamicQuery=new HandleDynamicDTO();
            handleDynamicQuery.setPid(handleDynamicFileDTO.getHandleDynamicId());
            HandleDynamicDTO handleDynamicDTO = client.findAllHandleDynamic(handleDynamicQuery).get(0);
            int handleId = handleDynamicDTO.getHandleId();
            
            ShiroUser user = getShiroUser();
            //查询文件主表
            BizFile bizFileQuery=new BizFile();
            bizFileQuery.setPid(fileId);
            bizFileQuery.setStatus(Constants.STATUS_ENABLED);
            List<BizFile> bizFileList = bizFileServiceClient.findAllBizFile(bizFileQuery);
            if (bizFileList==null||bizFileList.isEmpty()) {
                fillReturnJson(response, false, "删除失败,文件不存在!");
                return;
            }
            BizFile updateBizFile = bizFileList.get(0);
            int uploadUserId = updateBizFile.getUploadUserId();
            if (uploadUserId!=user.getPid()) {//检查是不是当前登录人上传的文件，否则不能删除
                fillReturnJson(response, false, "部分文件删除失败,你无权限删除!");
                return;
            }
            int projectId = client.getProjectIdByHandleId(handleId);
            ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
            Project projectInfo = projectService.getProjectInfoById(projectId);
            int foreclosureStatus = projectInfo.getForeclosureStatus();
            if (foreclosureStatus==Constants.FORECLOSURE_STATUS_13) {//已归档，不能删除
                fillReturnJson(response, false, "删除失败,已归档不能删除!");
                return;
            }
            //把文件的状态修改为“无效”
            String uploadDttm = DateUtil.format(DateUtil.format(updateBizFile.getUploadDttm()));
            updateBizFile.setUploadDttm(uploadDttm);
            updateBizFile.setStatus(Constants.STATUS_DISABLED);
            //执行更新
            bizFileServiceClient.updateBizFile(updateBizFile);
            //记录日志
            recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, " 删除文件："+updateBizFile.fileName+"."+updateBizFile.fileType,projectId);
         }
      } catch (Exception e) {
         logger.error(" 把文件的状态修改为无效失败.fileIds=" + fileIds + "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         fillReturnJson(response, false, "程序异常，请联系管理员");
         return;
      }
      fillReturnJson(response, true, "删除成功");
   }
   /**
    * 检查业务处理状态是否为已完成或已归档
    * 已完成或已归档返回false;
    * 不存在或未申请，已申请返回true
    *@author:liangyanjun
    *@time:2016年1月18日下午7:40:30
    *@param handleId
    *@return
    *@throws Exception
    */
   private boolean isUpdate(int handleId) throws Exception {
      Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      //查询业务处理信息
      HandleInfoDTO handleInfoQuery=new HandleInfoDTO();
      handleInfoQuery.setPid(handleId);
      List<HandleInfoDTO> handleInfoDTOList = client.findAllHandleInfoDTO(handleInfoQuery);
      if (handleInfoDTOList==null||handleInfoDTOList.isEmpty()) {
         return false;
      }
      //申请办理状态为已完成和已归档，不可修改
      HandleInfoDTO handleInfoDTO = handleInfoDTOList.get(0);
      if (handleInfoDTO.getApplyHandleStatus()==Constants.APPLY_HANDLE_STATUS_3||handleInfoDTO.getApplyHandleStatus()==Constants.APPLY_HANDLE_STATUS_4) {
         return false;
      }
      return true;
   }
   /**
    * 获取首页的预警事项
    *@author:liangyanjun
    *@time:2016年2月18日下午5:11:18
    *@param taskVo
    *@param response
    */
   @RequestMapping(value = "findIndexHandleDifferWarn",method = RequestMethod.POST)
   public void findIndexHandleDifferWarn(TaskVo taskVo, HttpServletResponse response) {
      Map<String, Object> map = new HashMap<String, Object>();
      Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      try {
         List<HandleDifferWarnDTO> list = client.findIndexHandleDifferWarn(getShiroUser().getPid());
         
         
         if(CollectionUtils.isEmpty(list)){
        	 list = new ArrayList<HandleDifferWarnDTO>();
         }
         
         //查询查档预警列表----------
         HandleDifferWarnDTO queryHand = new HandleDifferWarnDTO();
         queryHand.setStatus(com.xlkfinance.bms.common.constant.Constants.NOT_HANDLE_WARN_STATUS);
         queryHand.setHandleType(com.xlkfinance.bms.common.constant.Constants.WARN_HANDLE_TYPE_3);
         queryHand.setHandleAuthor(getShiroUser().getPid());
         List<HandleDifferWarnDTO> needHandleWarnList2 = client.getHandleDifferWarnList(queryHand);
         if(!CollectionUtils.isEmpty(needHandleWarnList2)){
        	 for (HandleDifferWarnDTO handleDifferWarnDTO : needHandleWarnList2) {
        		 handleDifferWarnDTO.setFlowName("系统查档");
			 }
        	 list.addAll(needHandleWarnList2);
         }
         
         map.put("rows", list);
         outputJson(map, response);
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   @RequestMapping(value = "/needHandleWarnList")
   public void needHandleWarnList(HandleDifferWarnIndexDTO handleDifferWarnIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      Map<String, Object> map = new HashMap<String, Object>();
      List<HandleDifferWarnIndexDTO> list = null;
      int total = 0;
      // 设置数据权限--用户编号list
      handleDifferWarnIndexDTO.setUserIds(getUserIds(request));
      try {
         // 查询业务办理列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         list = service.queryNeedHandleWarnIndex(handleDifferWarnIndexDTO);
         total = service.getNeedHandleWarnIndexTotal(handleDifferWarnIndexDTO);
         logger.info("预警中查询列表查询成功：total：" + total + ",financeHandleDtoList:" + list);
      } catch (Exception e) {
         logger.error("预警中查询列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + handleDifferWarnIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", list);
      map.put("total", total);
      outputJson(map, response);
   }
   /**
    * 更改差异预警处理备注
    *@author:liangyanjun
    *@time:2016年2月18日下午5:21:11
    *@param handleDifferWarnDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/submitWarnRemark")
   @ResponseBody
   public String updateWarnRemark(HandleDifferWarnDTO handleDifferWarnDTO, HttpServletRequest request, HttpServletResponse response) {
      if (handleDifferWarnDTO==null) {
         return "-1";
      }
      //检查id和备注是否合法
      int pid = handleDifferWarnDTO.getPid();
      String remark = handleDifferWarnDTO.getRemark();
      if (pid<=0||StringUtil.isBlank(remark)) {
         return "-1";
      }
      try {
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         //查询需要修改的差异预警处理信息
         HandleDifferWarnDTO handleDifferWarnQuery=new HandleDifferWarnDTO();
         handleDifferWarnQuery.setHandleAuthor(getShiroUser().getPid());
         handleDifferWarnQuery.setPid(pid);
         List<HandleDifferWarnDTO> handleDifferWarnList = client.findAllHandleDifferWarn(handleDifferWarnQuery);
         if (handleDifferWarnList==null||handleDifferWarnList.isEmpty()) {
            return "-1";
         }
         //设置修改的值
         HandleDifferWarnDTO updateDifferWarnDTO = handleDifferWarnList.get(0);
         //检查是否已经处理
         if (updateDifferWarnDTO.getStatus()==Constants.IS_HANDLE_WARN_STATUS) {
            return "-1";
         }
         updateDifferWarnDTO.setRemark(remark);//备注
         updateDifferWarnDTO.setStatus(Constants.IS_HANDLE_WARN_STATUS);//状态：失效=-1,未处理=1，已处理=2
         
         //记录日志
         int projectId = updateDifferWarnDTO.getProjectId();
         String flowName = updateDifferWarnDTO.getFlowName();
         
         //系统查档
         if(updateDifferWarnDTO.getHandleType() == Constants.WARN_HANDLE_TYPE_3){
        	 flowName ="系统查档";
        	 if(updateDifferWarnDTO.getDiffer() == 0 ){
     			int dayDifference = 0;
    			if(!StringUtil.isBlank(updateDifferWarnDTO.getCreateDate())){
    				//计算差异天数
    				Date tempCreateDate = DateUtils.stringToDate(updateDifferWarnDTO.getCreateDate(), "yyyy-MM-dd");
    				int diffDay = DateUtils.dayDifference(tempCreateDate, new Date());
    				dayDifference = (int) Math.ceil(((double)diffDay /365 ));
    				updateDifferWarnDTO.setDiffer(dayDifference);
    			}
        	 }
         }
         
         //更新差异预警处理状态和备注
         client.updateHandleDifferWarn(updateDifferWarnDTO);
         //添加到差异预警历史处理表中
         client.addHisHandleDifferWarn(updateDifferWarnDTO);

         
         recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, " 差异预警处理备注("+flowName+")："+updateDifferWarnDTO.getRemark(),projectId);
         //获取未处理预警事项的数量
         HttpSession session = request.getSession(false);
         handleDifferWarnQuery.setPid(-1);
         handleDifferWarnQuery.setPage(-1);
         handleDifferWarnQuery.setStatus(Constants.NOT_HANDLE_WARN_STATUS);
         handleDifferWarnList = client.findAllHandleDifferWarn(handleDifferWarnQuery);
         if (handleDifferWarnList==null||handleDifferWarnList.isEmpty()) {
            session.setAttribute(Constants.NOT_HANDLE_WARN_COUNT, 0);
         }else{
            session.setAttribute(Constants.NOT_HANDLE_WARN_COUNT, handleDifferWarnList.size());
         }
      } catch (Exception e) {
         logger.error("更改差异预警处理备注失败.handleDifferWarnDTO=" + handleDifferWarnDTO + "错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         return "-1";
      }
      return "1";
   }
   /**
    * 添加预警备注
    *@author:liangyanjun
    *@time:2016年5月14日上午11:53:42
    *@param handleDifferWarnDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/addWarnRemark")
   public void addWarnRemark(HandleDifferWarnDTO handleDifferWarnDTO, HttpServletRequest request, HttpServletResponse response) {
      int handleDynamicId = handleDifferWarnDTO.getHandleDynamicId();
      int projectId = handleDifferWarnDTO.getProjectId();
      String projectName = handleDifferWarnDTO.getProjectName();
      String remark = handleDifferWarnDTO.getRemark();
      if (handleDynamicId<=0||projectId<=0||StringUtil.isBlank(remark,projectName)) {
         logger.error("请求数据不合法：" + handleDifferWarnDTO);
         fillReturnJson(response, false, "提交失败,数据不合法请重新提交!");
         return;
      }
      try {
         ShiroUser shiroUser = getShiroUser();
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         handleDifferWarnDTO.setStatus(Constants.IS_HANDLE_WARN_STATUS);
         handleDifferWarnDTO.setHandleType(Constants.WARN_HANDLE_TYPE_2);
         handleDifferWarnDTO.setHandleDate(DateUtils.getCurrentDate());
         handleDifferWarnDTO.setHandleAuthor(shiroUser.getPid());
         client.addHandleDifferWarn(handleDifferWarnDTO);
         //添加到差异预警历史处理表中
         client.addHisHandleDifferWarn(handleDifferWarnDTO);
         fillReturnJson(response, true, "提交成功");
      } catch (Exception e) {
         logger.error("添加预警备注失败.handleDifferWarnDTO=" + handleDifferWarnDTO + "错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
   }
   /**
    * 根据业务办理id查询赎楼及余额回转信息信息
    *@author:liangyanjun
    *@time:2016年3月11日下午4:55:33
    *@param houseBalanceDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/getHouseBalance")
   public void getHouseBalance(HouseBalanceDTO houseBalanceDTO, HttpServletRequest request, HttpServletResponse response) {
      int handleId = houseBalanceDTO.getHandleId();
      if (handleId<=0) {
         return;
      }
      Map result=new HashMap<>();
      ShiroUser shiroUser = getShiroUser();
      Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      try {
         // houseBalanceDTO.setUserIds(getUserIds(request));
         List<HouseBalanceDTO> houseBalanceList = client.findAllHouseBalance(houseBalanceDTO);
         //办理人
         Set<Map<String,String>> handleUserSet = client.getHandleUser(handleId, Constants.HANDLE_FLOW_ID_2,shiroUser.getUserName());
         result.put("handleUserSet", handleUserSet);
         result.put("houseBalanceList", houseBalanceList);
         //返回结果集
         outputJson(result, response);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   /**
    * 赎楼
    *@author:liangyanjun
    *@time:2016年3月11日下午6:57:25
    *@param houseBalanceDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/foreclosure", method = RequestMethod.POST)
   public void foreclosure(HouseBalanceDTO houseBalanceDTO, HttpServletRequest request, HttpServletResponse response) {
      if (houseBalanceDTO==null){fillReturnJson(response, false, "提交失败,请重新操作!");return;}
     
      //获取参数
      int pid = houseBalanceDTO.getPid();
      int handleId = houseBalanceDTO.getHandleId();//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
      double principal = houseBalanceDTO.getPrincipal();//赎楼本金（付款金额）
      String payDate = houseBalanceDTO.getPayDate();//付款日期
      double interest = houseBalanceDTO.getInterest();//利息
      double defaultInterest = houseBalanceDTO.getDefaultInterest();//罚息
      double balance = houseBalanceDTO.getBalance();//赎楼余额
      String backAccount = houseBalanceDTO.getBackAccount();//转回账号
      int count = houseBalanceDTO.getCount();//赎楼次数
      String remark = houseBalanceDTO.getRemark();
      int handleUserId = houseBalanceDTO.getHandleUserId();//办理人
      //检查入参
      if (pid<=0||handleId<=0||count<0||count>2||handleUserId<=0||StringUtil.isBlank(payDate)) {
         logger.error("请求数据不合法：" + houseBalanceDTO);
         fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,请输入必填项!");
         return;
      }
      if (principal<=0) {
          fillReturnJson(response, false, "第"+count+"次赎楼 提交失败,请输入赎楼本金!");
          return;
      }
      if (balance>0&&("--请选择--".equals(backAccount)||StringUtil.isBlank(backAccount))) {
          logger.error("请求数据不合法：" + houseBalanceDTO);
          fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,赎楼余额大于零，转回账号必填!");
          return;
      }
      if ("--请选择--".equals(backAccount)) {
          backAccount="";
      }
      try {
         ShiroUser shiroUser = getShiroUser();
         String userName = shiroUser.getUserName();
         String realName = shiroUser.getRealName();
         Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
         //查询业务处理信息
         HandleInfoDTO handleInfoQuery=new HandleInfoDTO();
         handleInfoQuery.setPid(handleId);
         List<HandleInfoDTO> handleInfoDTOList = client.findAllHandleInfoDTO(handleInfoQuery);
         if (handleInfoDTOList==null||handleInfoDTOList.isEmpty()) {
            fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,请重新操作!");
            return;
         }
         
         //申请办理状态为已完成和已归档，不可修改
         HandleInfoDTO handleInfoDTO = handleInfoDTOList.get(0);
         int projectId = handleInfoDTO.getProjectId();
         if (handleInfoDTO.getApplyHandleStatus()==Constants.APPLY_HANDLE_STATUS_3||handleInfoDTO.getApplyHandleStatus()==Constants.APPLY_HANDLE_STATUS_4) {
            fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,状态为已完成和已归档，不可修!");
            return;
         }
         if (handleInfoDTO.getForeclosureStatus()==Constants.TURN_DOWN_FORECLOSURE) {
        	 fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,赎楼状态为已驳回，不可赎楼!");
        	 return;
         }
         //检查是否查档通过
         IntegratedDeptService.Client integratedDeptServiceClient=(IntegratedDeptService.Client) getService(BusinessModule.MODUEL_INLOAN,"IntegratedDeptService");
         CheckDocumentDTO checkDocumentQuery=new CheckDocumentDTO();
         checkDocumentQuery.setProjectId(projectId);
         List<CheckDocumentDTO> queryCheckDocument = integratedDeptServiceClient.queryCheckDocument(checkDocumentQuery);
         if (queryCheckDocument==null||queryCheckDocument.isEmpty()||queryCheckDocument.get(0).getApprovalStatus()!=Constants.CHECK_DOCUMENT_APPROVAL_STATUS_3) {
            fillReturnJson(response, false, "提交失败,查档未通过,不可赎楼!");
            return;
         }
         //检查是否查诉讼通过
         CheckLitigationDTO checkLitigation = integratedDeptServiceClient.getCheckLitigationByProjectId(projectId);
         if (checkLitigation.getApprovalStatus()!=Constants.CHECK_LITIGATION_APPROVAL_STATUS_3) {
            fillReturnJson(response, false, "提交失败,查诉讼未通过,不可赎楼!");
            return;
         }
         //获取要修改的数据
         HouseBalanceDTO houseBalanceQuery=new HouseBalanceDTO();
         houseBalanceQuery.setPid(pid);
//         houseBalanceQuery.setUserIds(getUserIds(request));
         List<HouseBalanceDTO> houseBalanceList = client.findAllHouseBalance(houseBalanceQuery);
         if (houseBalanceList==null||houseBalanceList.isEmpty()) {
            logger.error("修改的赎楼及余额回转信息数据不存在：", pid);
            fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,请重新操作!");
            return;
         }
         
         HouseBalanceDTO updateHouseBalanceDTO = houseBalanceList.get(0);
         //检查余款是否已确认，已确认不可在赎楼
         if (updateHouseBalanceDTO.getBalanceConfirm()==Constants.IS_BALANCE_CONFIRM) {
            fillReturnJson(response, false, "第"+count+"次赎楼及余额回转 提交失败,余款已确认，不可修改!");
            return;
         }
         
         //设置修改的值
         HouseBalanceDTO oldHouseBalanceDTO=new HouseBalanceDTO();
         BeanUtils.copyProperties(updateHouseBalanceDTO, oldHouseBalanceDTO);
         updateHouseBalanceDTO.setPrincipal(principal);
         updateHouseBalanceDTO.setPayDate(payDate);
         updateHouseBalanceDTO.setHouseClerk(shiroUser.getRealName());
         updateHouseBalanceDTO.setInterest(interest);
         updateHouseBalanceDTO.setDefaultInterest(defaultInterest);
         updateHouseBalanceDTO.setBalance(balance);
         updateHouseBalanceDTO.setBackAccount(backAccount);
         updateHouseBalanceDTO.setCount(count);
         updateHouseBalanceDTO.setRemark(remark);
         updateHouseBalanceDTO.setHandleUserId(handleUserId);
         
         HandleDynamicDTO updateHandleDynamicDTO=null;
         if (balance<=0) {//余额<=0，则无余款
            updateHouseBalanceDTO.setBalanceConfirm(Constants.IS_BALANCE_CONFIRM);
            //如果赎楼余额确认，则完成赎楼办理动态任务,此处获取完成任务的办理动态，在这获取的办理动态回计算差异和操作天数
            updateHandleDynamicDTO = getHandleDynamicInfoByhandleId(handleId, client);
         }else{
            //如果赎楼余额还未确认，获取数据库的办理动态，不做差异和操作天数计算
            HandleDynamicDTO handleDynamicQuery=new HandleDynamicDTO();
            handleDynamicQuery.setHandleId(handleId);
            handleDynamicQuery.setHandleFlowId(Constants.HANDLE_FLOW_ID_2);
            List<HandleDynamicDTO> handleDynamicList = client.findAllHandleDynamic(handleDynamicQuery);
            if (handleDynamicList==null||handleDynamicList.isEmpty()) {
               logger.error("赎楼失败,办理动态数据不存在：入参："+houseBalanceDTO);
               return;
            }
            updateHandleDynamicDTO=handleDynamicList.get(0);
         }
         updateHandleDynamicDTO.setFinishDate(payDate);//办理动态完成时间
         updateHandleDynamicDTO.setOperator(realName);//操作人
         updateHandleDynamicDTO.setCurrentHandleUserId(handleUserId);
         updateHandleDynamicDTO.setRemark(remark);
         //执行赎楼
         client.foreclosure(updateHouseBalanceDTO,handleInfoDTO,updateHandleDynamicDTO,userName);
         //往业务动态插入一条记录
         String bizDynamicRemark="第一次赎楼本金:"+MoneyFormatUtil.format(principal)+",咨询费:"+MoneyFormatUtil.format(interest)+",罚息:"+MoneyFormatUtil.format(defaultInterest)+",余额转回:"+MoneyFormatUtil.format(balance)+",转回帐号:"+backAccount;
         finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_8, bizDynamicRemark);

         //记录日志
         recordLogOfHouseBalance(handleId, client, updateHouseBalanceDTO, oldHouseBalanceDTO);
         logger.info("修改的赎楼及余额回转信息成功：修改作者ID:"+getShiroUser().getPid()+"，参数："+updateHouseBalanceDTO);
      } catch (Exception e) {
         logger.error("修改赎楼及余额回转信息失败：入参："+houseBalanceDTO+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         fillReturnJson(response, false, "第"+count+"次赎楼及余额回转提交失败,请重新操作!");
         return;
      }
      fillReturnJson(response, true, "第"+count+"次赎楼及余额回转 提交成功");
   }
   /**
    * 赎楼余额确认
    *@author:liangyanjun
    *@time:2016年3月12日上午12:52:40
    *@param balanceConfirmDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/balanceConfirm", method = RequestMethod.POST)
   public void balanceConfirm(BalanceConfirmDTO balanceConfirmDTO, HttpServletRequest request, HttpServletResponse response) {
      if (balanceConfirmDTO==null){fillReturnJson(response, false, "提交失败,请重新操作!");return;}
      int handleId = balanceConfirmDTO.getHandleId();//业务主表id
      double firstBalance = balanceConfirmDTO.getFirstBalance();//一次赎楼余额
      String firstBackAccount = balanceConfirmDTO.getFirstBackAccount();//一次赎楼余额转回帐号
      double secondBalance = balanceConfirmDTO.getSecondBalance();//二次赎楼余额
      String secondBackAccount = balanceConfirmDTO.getSecondBackAccount();//二次赎楼余额转回帐号
      if (handleId<=0) {
         logger.error("请求数据不合法：" + balanceConfirmDTO);
         fillReturnJson(response, false, "提交失败,请求数据不合法,请重新操作!");
         return;
      }
      Client client=(Client) getService(BusinessModule.MODUEL_INLOAN,serviceName);
      ShiroUser shiroUser = getShiroUser();
      String userName = shiroUser.getUserName();
      try {
         //查询业务办理主表
         HandleInfoDTO handleInfoDTO = client.getHandleInfoById(handleId);
         if (handleInfoDTO==null||handleInfoDTO.getPid()<=0) {
            fillReturnJson(response, false, "提交失败,请重新操作!");
            return;
         }
         //检查业务办理状态是否已完成或已归档
         int applyHandleStatus = handleInfoDTO.getApplyHandleStatus();
         if (applyHandleStatus==Constants.APPLY_HANDLE_STATUS_3||applyHandleStatus==Constants.APPLY_HANDLE_STATUS_4) {
            fillReturnJson(response, false, "提交失败,业务申请办理已完成或已归档，不可更改!");
            return;
         }
         //查询赎楼信息
         HouseBalanceDTO houseBalanceQuery=new HouseBalanceDTO();
         houseBalanceQuery.setHandleId(handleId);
         List<HouseBalanceDTO> houseBalanceList = client.findAllHouseBalance(houseBalanceQuery);
         if (houseBalanceList==null||houseBalanceList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,请重新操作!");
            return;
         }
         boolean balanceConfirmSucc=false;
         //设置更新
         for (HouseBalanceDTO houseBalanceDTO : houseBalanceList) {
            int count = houseBalanceDTO.getCount();
            int balanceConfirm = houseBalanceDTO.getBalanceConfirm();
            if (balanceConfirm==Constants.IS_BALANCE_CONFIRM) {
               continue;
            }
            if (count==1) {
               houseBalanceDTO.setBalance(firstBalance);
               houseBalanceDTO.setBackAccount(firstBackAccount);
            }else{
               houseBalanceDTO.setBalance(secondBalance);
               houseBalanceDTO.setBackAccount(secondBackAccount);
            }
            houseBalanceDTO.setBalanceConfirm(Constants.IS_BALANCE_CONFIRM);//余额确认
            //执行余额确认更新
            balanceConfirmSucc = client.balanceConfirm(houseBalanceDTO);
         }
         if (balanceConfirmSucc) {
        	logger.info("赎楼余额确认成功：入参：userName:"+userName+",balanceConfirmDTO:"+balanceConfirmDTO);
            HandleDynamicDTO handleDynamicDTO = handleData(handleId, client, shiroUser.getRealName(), Arrays.asList(Constants.HANDLE_FLOW_ID_2));
            if (handleDynamicDTO!=null) {
                client.finishHandleDynamicTask(handleDynamicDTO, "");
                logger.info("自动完成赎楼办理成功：入参：handleDynamicDTO："+handleDynamicDTO+",userName:"+userName);
            }
         }
      } catch (Exception e) {
         logger.error("赎楼余额失败：入参："+balanceConfirmDTO+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }
   /**
    * 赎楼驳回
    *@author:liangyanjun
    *@time:2016年3月31日下午2:31:00
    *@param handleInfoDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/foreclosureTurnDown", method = RequestMethod.POST)
   public void foreclosureTurnDown(HandleInfoDTO handleInfoDTO, HttpServletRequest request, HttpServletResponse response) {
      String foreclosureTurnDownRemark = handleInfoDTO.getForeclosureTurnDownRemark();
      int pid = handleInfoDTO.getPid();
      // 检查id是否合法
      if (pid <= 0) {
         fillReturnJson(response, false, "请求数据不合法!");
         logger.error("请求数据不合法：" + handleInfoDTO);
         return;
      }
      FinanceHandleService.Client financeHandleService = (FinanceHandleService.Client) getService(BusinessModule.MODUEL_INLOAN, "FinanceHandleService");
      Client client = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         int projectId = client.getProjectIdByHandleId(pid);
         ProjectService.Client projectService=(ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN,"ProjectService");
         Project projectInfo = projectService.getProjectInfoById(projectId);
         //如果不需要办理贷中，则不能赎楼驳回
         if (projectInfo.getIsNeedHandle()==Constants.IS_NEED_HANDLE_2) {
             fillReturnJson(response, false, "此订单不需要贷中办理，无法赎楼驳回!");
             return;
         }
         FinanceHandleDTO financeHandle = financeHandleService.getFinanceHandleByProjectId(projectId);
         //放款申请中不能驳回,只有未申请放款和已放款才能驳回
         if (financeHandle.getStatus()==Constants.REC_STATUS_LOAN_APPLY) {
             fillReturnJson(response, false, "放款申请中不能驳回!");
             return;
         }
         // 获取要修改的业务处理信息
         HandleInfoDTO query = new HandleInfoDTO();
         query.setPid(pid);
         // query.setUserIds(getUserIds(request));
         List<HandleInfoDTO> handleInfoList = client.findAllHandleInfoDTO(query);
         if (handleInfoList == null || handleInfoList.size() == 0) {
            fillReturnJson(response, false, "驳回失败,业务处理信息数据不存在!");
            logger.error("业务处理信息数据不存在：", pid);
            return;
         }
         HandleInfoDTO updateDandleInfoDTO = handleInfoList.get(0);
         if (updateDandleInfoDTO.getForeclosureStatus()==Constants.TURN_DOWN_FORECLOSURE) {
            fillReturnJson(response, false, "驳回失败,该业务已经驳回了!");
            logger.error("驳回失败,该业务已经驳回了：", updateDandleInfoDTO);
            return;
         }
         //检查该业务是否已经赎楼了，赎楼之后不可驳回
         if (updateDandleInfoDTO.getForeclosureStatus()==Constants.IS_FORECLOSURE) {
            fillReturnJson(response, false, "驳回失败,该业务已经赎楼了!");
            logger.error("驳回失败,该业务已经赎楼：", updateDandleInfoDTO);
            return;
         }
         // 执行更新
         updateDandleInfoDTO.setForeclosureStatus(Constants.TURN_DOWN_FORECLOSURE);
         updateDandleInfoDTO.setForeclosureTurnDownRemark(foreclosureTurnDownRemark);
         client.foreclosureTurnDown(updateDandleInfoDTO);
         logger.info("赎楼驳回成功：修改作者ID:" + getShiroUser().getPid() + "，参数：" + updateDandleInfoDTO);
         //记录操作日记
         recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, "驳回" ,projectId);
         fillReturnJson(response, true, "驳回成功!");
      } catch (Exception e) {
         logger.error("赎楼驳回失败：入参：" + handleInfoDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "驳回失败,请联系管理员!");
         e.printStackTrace();
      }
   }
   /**
    * 查询历史预警备注记录
    *@author:liangyanjun
    *@time:2016年3月1日下午8:36:37
    *@param handleDifferWarnDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/hisDifferWarnList")
   @ResponseBody
   public void hisDifferWarnList(HandleDifferWarnDTO handleDifferWarnDTO, HttpServletRequest request, HttpServletResponse response) {
      Map<String, Object> map = new HashMap<String, Object>();
      List<HandleDifferWarnDTO> differWarnReportList = null;
      int total = 0;
      try {
         // 查询预警事项列表
         BizHandleService.Client service = (BizHandleService.Client) getService(BusinessModule.MODUEL_INLOAN, "BizHandleService");
         differWarnReportList = service.findAllHisHandleDifferWarn(handleDifferWarnDTO);
         total = service.getHisHandleDifferWarnTotal(handleDifferWarnDTO);
         logger.info("查询历史预警备注记录成功：total：" + total + ",hisDifferWarnList:" + differWarnReportList);
      } catch (Exception e) {
         logger.error("查询历史预警备注记录失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + handleDifferWarnDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", differWarnReportList);
      map.put("total", total);
      outputJson(map, response);
   }
   /**
    * 记录申请业务处理信息修改日志到数据库
    *@author:liangyanjun
    *@time:2016年2月21日下午2:42:28
    *@param handleId
    *@param client
    *@param updateApplyHandleInfoDTO
    *@param oldApplyHandleInfoDTO
    *@param hashMap
    *@throws TException
    */
   private void recordLogOfApplyHandle(int handleId, Client client, ApplyHandleInfoDTO updateApplyHandleInfoDTO, ApplyHandleInfoDTO oldApplyHandleInfoDTO) throws TException {
      HashMap<ApplyHandleInfoDTO._Fields, String> hashMap = new HashMap<ApplyHandleInfoDTO._Fields,String>();
      hashMap.put(ApplyHandleInfoDTO._Fields.SUB_DATE, "合同及放款确认书提交时间");
      hashMap.put(ApplyHandleInfoDTO._Fields.CONTACT_PERSON, "联系人");
      hashMap.put(ApplyHandleInfoDTO._Fields.CONTACT_PHONE, "联系电话");
      hashMap.put(ApplyHandleInfoDTO._Fields.HANDLE_DATE, "办理时间");
      hashMap.put(ApplyHandleInfoDTO._Fields.SPECIAL_CASE, "特殊情况说明");
      hashMap.put(ApplyHandleInfoDTO._Fields.FEEDBACK, "办理反馈");
      hashMap.put(ApplyHandleInfoDTO._Fields.REMARK, "备注");
      Set<ApplyHandleInfoDTO._Fields> keySet = hashMap.keySet();
      StringBuffer logsb=new StringBuffer();
      for (ApplyHandleInfoDTO._Fields key : keySet) {
         Object updateFieldValue = updateApplyHandleInfoDTO.getFieldValue(key);//修改后的值
         Object oldFieldValue = oldApplyHandleInfoDTO.getFieldValue(key);//原来的值
         if ((updateFieldValue==null||StringUtil.isBlank(updateFieldValue.toString()))&&(oldFieldValue==null||StringUtil.isBlank(oldFieldValue.toString()))) {
            continue;
         }
         if (updateFieldValue==null||!updateFieldValue.equals(oldFieldValue)) {
            String fieldName = hashMap.get(key);
            logsb.append("-"+fieldName+":("+oldFieldValue+")更改为("+updateFieldValue+") ");
         }
      }
      if (logsb.length()==0) {
         return;
      }
      logsb.insert(0, " 修改申请业务处理信息:");
      int projectId = client.getProjectIdByHandleId(handleId);
      recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, logsb.toString(),projectId);
   }
   
   /**
    * 记录财务退款明细信息修改日志到数据库
    *@author:liangyanjun
    *@time:2016年2月21日下午3:25:38
    *@param handleId
    *@param client
    *@param updateRefundDetailsDTO
    *@param oldRefundDetailsDTO
    *@throws TException
    */
   private void recordLogOfRefundDetails(int handleId, Client client, RefundDetailsDTO updateRefundDetailsDTO, RefundDetailsDTO oldRefundDetailsDTO) throws TException {
      int refundPro = oldRefundDetailsDTO.getRefundPro();
      String refundProName = Constants.REFUND_PRO_MAP.get(new Long(refundPro));
      HashMap<RefundDetailsDTO._Fields, String> hashMap = new HashMap<RefundDetailsDTO._Fields,String>();
      hashMap.put(RefundDetailsDTO._Fields.REFUND_MONEY, "退款金额");
      hashMap.put(RefundDetailsDTO._Fields.REC_ACCOUNT, "收款账号");
      hashMap.put(RefundDetailsDTO._Fields.REC_NAME, "收款户名");
      hashMap.put(RefundDetailsDTO._Fields.PAY_DATE, "付款日期");
      hashMap.put(RefundDetailsDTO._Fields.OPERATOR, "操作员");
      Set<RefundDetailsDTO._Fields> keySet = hashMap.keySet();
      StringBuffer logsb=new StringBuffer();
      //检查值是否更新，更新了则写入日志
      for (RefundDetailsDTO._Fields key : keySet) {
         Object updateFieldValue = updateRefundDetailsDTO.getFieldValue(key);//修改后的值
         Object oldFieldValue = oldRefundDetailsDTO.getFieldValue(key);//原来的值
         if ((updateFieldValue==null||StringUtil.isBlank(updateFieldValue.toString()))&&(oldFieldValue==null||StringUtil.isBlank(oldFieldValue.toString()))) {
            continue;
         }
         if (updateFieldValue==null||!updateFieldValue.equals(oldFieldValue)) {
            String fieldName = hashMap.get(key);
            logsb.append("-"+fieldName+":("+oldFieldValue+")更改为("+updateFieldValue+") ");
         }
      }
      //没有更新内容，则不写入日志
      if (logsb.length()==0) {
         return;
      }
      logsb.insert(0, " 修改财务退款明细信息  退款项目("+refundProName+"):");
      int projectId = client.getProjectIdByHandleId(handleId);
      recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, logsb.toString(),projectId);
   }
   private void recordLogOfHouseBalance(int handleId, Client client, HouseBalanceDTO updateHouseBalanceDTO, HouseBalanceDTO oldHouseBalanceDTO) throws TException {
      int count = oldHouseBalanceDTO.getCount();//赎楼次数
      HashMap<HouseBalanceDTO._Fields, String> hashMap = new HashMap<HouseBalanceDTO._Fields,String>();
      hashMap.put(HouseBalanceDTO._Fields.PRINCIPAL, "赎楼本金");
      hashMap.put(HouseBalanceDTO._Fields.PAY_DATE, "付款日期");
      hashMap.put(HouseBalanceDTO._Fields.HOUSE_CLERK, "赎楼员");
      hashMap.put(HouseBalanceDTO._Fields.DEFAULT_INTEREST, "罚息");
      hashMap.put(HouseBalanceDTO._Fields.INTEREST, "利息");
      hashMap.put(HouseBalanceDTO._Fields.BALANCE, "赎楼余额");
      hashMap.put(HouseBalanceDTO._Fields.BACK_ACCOUNT, "转回账号");
      hashMap.put(HouseBalanceDTO._Fields.REMARK, "备注");
      Set<HouseBalanceDTO._Fields> keySet = hashMap.keySet();
      StringBuffer logsb=new StringBuffer();
      //检查值是否更新，更新了则写入日志
      for (HouseBalanceDTO._Fields key : keySet) {
         Object updateFieldValue = updateHouseBalanceDTO.getFieldValue(key);//修改后的值
         Object oldFieldValue = oldHouseBalanceDTO.getFieldValue(key);//原来的值
         if ((updateFieldValue==null||StringUtil.isBlank(updateFieldValue.toString()))&&(oldFieldValue==null||StringUtil.isBlank(oldFieldValue.toString()))) {
            continue;
         }
         if (updateFieldValue==null||!updateFieldValue.equals(oldFieldValue)) {
            String fieldName = hashMap.get(key);
            logsb.append("-"+fieldName+":("+oldFieldValue+")更改为("+updateFieldValue+") ");
         }
      }
      //没有更新内容，则不写入日志
      if (logsb.length()==0) {
         return;
      }
      logsb.insert(0, " 修改第"+count+"次赎楼及余额回转信息 :");
      int projectId = client.getProjectIdByHandleId(handleId);
      recordLog(BusinessModule.MODUEL_INLOAN, SysLogTypeConstant.LOG_TYPE_BIZ_HANDLE, logsb.toString(),projectId);
   }
}
