package com.xlkfinance.bms.web.controller.inloan;

import java.util.ArrayList;
import java.util.Collections;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.MoneyFormatUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizBatchRefundFeeRelation;
import com.xlkfinance.bms.rpc.inloan.BizLoanBatchRefundFeeMain;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeMap;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService.Client;
import com.xlkfinance.bms.rpc.workflow.TaskHistoryDto;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月27日上午10:32:34 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 退手续费<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Controller
@RequestMapping("/refundFeeController")
public class RefundFeeController extends BaseController {
   private static final String PAGE_PATH = "inloan/refundFee/";
   private Logger logger = LoggerFactory.getLogger(RefundFeeController.class);
   private final String serviceName = "RefundFeeService";

   /**
    * 首页跳转
    */
   @RequestMapping(value = "/index")
   public String toIndex(ModelMap model, int type) {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      if (type == Constants.REFUND_FEE_TYPE_1) {
         url.append("refund_fee_index");
      } else if (type == Constants.REFUND_FEE_TYPE_2) {
         url.append("refund_interest_fee_index");
      } else if (type == Constants.REFUND_FEE_TYPE_3) {
         url.append("refund_tail_money_index");
      } else if (type == Constants.REFUND_FEE_TYPE_4) {
         url.append("refund_commission_index");
      }
      return url.toString();
   }

   @RequestMapping(value = "/list")
   @ResponseBody
   public void list(RefundFeeIndexDTO refundFeeIndexDTO, HttpServletRequest request, HttpServletResponse response) {
      if (refundFeeIndexDTO==null)refundFeeIndexDTO=new RefundFeeIndexDTO();
      Map<String, Object> map = new HashMap<String, Object>();
      List<RefundFeeIndexDTO> refundFeeIndexList = null;
      int total = 0;
      // 设置数据权限--用户编号list
      refundFeeIndexDTO.setUserIds(getUserIds(request));
      try {
         // 查询退手续费列表
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         refundFeeIndexList = service.findAllRefundFeeIndex(refundFeeIndexDTO);
         total = service.getRefundFeeIndexTotal(refundFeeIndexDTO);
         logger.info("退手续费查询列表查询成功：total：" + total + ",refundFeeIndexList:" + refundFeeIndexList);
      } catch (Exception e) {
         logger.error("获取退手续费列表失败：" + ExceptionUtil.getExceptionMessage(e) + ",入参：" + refundFeeIndexDTO);
         e.printStackTrace();
      }
      // 输出
      map.put("rows", refundFeeIndexList);
      map.put("total", total);
      outputJson(map, response);
   }

   /**
    * 添加（保存）或修改退费信息
    *@author:liangyanjun
    *@time:2016年3月4日下午8:37:54
    *@param refundFeeDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
   public void addOrUpdate(RefundFeeDTO refundFeeDTO, HttpServletRequest request, HttpServletResponse response) {
      ShiroUser shiroUser = getShiroUser();
      double returnFee = refundFeeDTO.getReturnFee();// 退费金额
      String recAccountName = refundFeeDTO.getRecAccountName();// 收款人户名
      String bankName = refundFeeDTO.getBankName();// 开户行
      String recAccount = refundFeeDTO.getRecAccount();// 收款人账号
      int projectId = refundFeeDTO.getProjectId();
      int type = refundFeeDTO.getType();
      if (projectId <= 0 || type <= 0) {
         logger.error("请求数据不合法：" + refundFeeDTO);
         fillReturnJson(response, false, "提交失败,请输入必填项,请重新操作!");
         return;
      }
      if (returnFee <= 0) {
         fillReturnJson(response, false, "提交失败,退费金额要大于0,请重新操作!");
         return;
      }
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
         // refundFeeQuery.setUserIds(getUserIds(request));
         refundFeeQuery.setType(type);
         refundFeeQuery.setProjectId(projectId);
         List<RefundFeeDTO> refundFeeList = service.findAllRefundFee(refundFeeQuery);
         if (refundFeeList == null || refundFeeList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不存在!");
            return;
         }
         RefundFeeDTO updateRefundFeeDTO = refundFeeList.get(0);
         // 检查项目是否归档
         int foreclosureStatus = updateRefundFeeDTO.getForeclosureStatus();
         if (foreclosureStatus == Constants.FORECLOSURE_STATUS_13) {
            fillReturnJson(response, false, "提交失败,项目已归档，不可修改!");
            return;
         }
         // 状态为待审核以后不可修改
         if (updateRefundFeeDTO.getApplyStatus() > Constants.APPLY_REFUND_FEE_STATUS_3) {
            fillReturnJson(response, false, "提交失败,状态为待审核以后不可修改!");
            return;
         }
         // 检查修改用户是否为该业务经理
         int pmUserId = updateRefundFeeDTO.getPmUserId();
         if (pmUserId != shiroUser.getPid()) {
            fillReturnJson(response, false, "提交失败,非业务经理，不可修改!");
            return;
         }
         // 执行添加
         // int pid = updateRefundFeeDTO.getPid();
         // if (pid==0) {
         // refundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_2);
         // service.addRefundFee(refundFeeDTO);
         // }
         //
         if (updateRefundFeeDTO.getApplyStatus() <= Constants.APPLY_REFUND_FEE_STATUS_2) {
            updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_2);
         }
         updateRefundFeeDTO.setReturnFee(returnFee);
         updateRefundFeeDTO.setRecAccountName(recAccountName);
         updateRefundFeeDTO.setBankName(bankName);
         updateRefundFeeDTO.setRecAccount(recAccount);
         updateRefundFeeDTO.setUpdateId(shiroUser.getPid());
         if (updateRefundFeeDTO.getCreaterId() == 0) {
            updateRefundFeeDTO.setCreaterId(shiroUser.getPid());
         }
         // 执行更新
         service.updateRefundFee(updateRefundFeeDTO);
      } catch (Exception e) {
         logger.error("添加（保存）或修改退费信息失败：入参：RefundFeeDTO" + refundFeeDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      fillReturnJson(response, true, "提交成功");
   }

   /**
    * 跳转编辑页面
    *@author:liangyanjun
    *@time:2016年1月27日上午11:52:29
    *@param model
    *@param pid
    *@param request
    *@return
    * @throws TException 
    * @throws ThriftServiceException 
    */
   @RequestMapping(value = "/edit")
   public String edit(ModelMap model, RefundFeeDTO refundFeeQuery, HttpServletRequest request) throws ThriftServiceException, TException {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      int type = refundFeeQuery.getType();
      int projectId = refundFeeQuery.getProjectId();
      ProjectService.Client projectClient = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      Project project = projectClient.getProjectInfoById(projectId);
     
      int projectSource = project.getProjectSource();//项目来源（1万通2小科）
      if (type == Constants.REFUND_FEE_TYPE_1) {
         url.append("refund_fee_edit");
      } else if (type == Constants.REFUND_FEE_TYPE_2) {
         url.append("refund_interest_fee_edit");
      } else if (type == Constants.REFUND_FEE_TYPE_3) {
         url.append("refund_tail_money_edit");
         //根据项目来源（1万通2小科）加载流程
         if (projectSource==1) {
            model.put("workflowId", "WTrefundTailMoneyProcess");
            model.put("nextRoleCode", "GUARANTEE_COMMISSIONER");
         }else{
            model.put("workflowId", "refundTailMoneyProcess");
            model.put("nextRoleCode", "POST_LOAN_MANAGEMENT");
         }
      } else if (type == Constants.REFUND_FEE_TYPE_4) {
         url.append("refund_commission_edit");
      }
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         // 根据id查询退手续费信息
         // refundFeeQuery.setUserIds(getUserIds(request));
         List<RefundFeeDTO> refundRefundFeeList = service.findAllRefundFee(refundFeeQuery);
         if (refundRefundFeeList == null || refundRefundFeeList.isEmpty()) {
            model.put("editRefundFeeDTO", new RefundFeeDTO());
            model.put("projectId", projectId);
            return url.toString();
         }
         RefundFeeDTO editRefundFeeDTO = refundRefundFeeList.get(0);
         int applyStatus = editRefundFeeDTO.getApplyStatus();
         //如果退费未申请，并且项目已归档，则不允许在申请退费流程
         if (project.getForeclosureStatus()==Constants.FORECLOSURE_STATUS_13&&(applyStatus==Constants.APPLY_STATUS_1||applyStatus==Constants.APPLY_STATUS_2)) {
            model.put("msg", "已归档（解保）项目，不能申请退费");
            return "msg_info";
         }
         // 把查出的数据填充到model中
         model.put("editRefundFeeDTO", editRefundFeeDTO);
         model.put("projectSource", projectSource);
      } catch (Exception e) {
         logger.error("查询退手续费失败refundFeeQuery：" + refundFeeQuery + ",错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }

      return url.toString();
   }
   /**
    * 跳转到批量退费页面
    *@author:liangyanjun
    *@time:2017年12月29日下午5:13:05
    *@param model
    *@param batchRefundFeeMainId
    *@param projectIds
    *@param type
    *@param request
    *@return
    *@throws ThriftServiceException
    *@throws TException
    */
   @RequestMapping(value = "/toBatchHandle")
   public String toBatchHandle(ModelMap model,int batchRefundFeeMainId,String projectIds,int type, HttpServletRequest request) throws ThriftServiceException, TException {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      if (StringUtil.isBlank(projectIds)&&batchRefundFeeMainId<=0) {
         return url.toString();
      }
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         ArrayList<Integer> projectList = new ArrayList<>();
         //batchRefundFeeMainId为空则说明该批没有申请过
         if (batchRefundFeeMainId<=0) {
            //把项目id分割，并且放到集合中
            String[] projectIdsTemp = projectIds.split(",");
            for (String projectId : projectIdsTemp) {
               projectList.add(Integer.parseInt(projectId));
            }
         }else{
            BizBatchRefundFeeRelation query=new BizBatchRefundFeeRelation();
            query.setBatchRefundFeeMainId(batchRefundFeeMainId);
            List<BizBatchRefundFeeRelation> batchRefundFeeRelationList = service.getAllBatchRefundFeeRelation(query);
            for (BizBatchRefundFeeRelation obj : batchRefundFeeRelationList) {
               projectList.add(obj.getProjectId());
            }
         }
         if (type == Constants.REFUND_FEE_TYPE_2) {
            url.append("batch_refund_interest_fee_edit");
         } 
         RefundFeeDTO refundFeeQuery=new RefundFeeDTO();
         refundFeeQuery.setRows(150);
         refundFeeQuery.setProjectList(projectList);
         refundFeeQuery.setType(type);
         BizLoanBatchRefundFeeMain batchRefundFeeMain = service.getBatchRefundFeeMainById(batchRefundFeeMainId);
         //如果没有保存过，则生成一个批量名称
         if (batchRefundFeeMain.getPid()<=0) {
            String batchName = service.generatedBatchName();
            batchRefundFeeMain=new BizLoanBatchRefundFeeMain();
            batchRefundFeeMain.setBatchName(batchName);
         }
         Integer projectId = projectList.get(0);
         RefundFeeIndexDTO refundFeeIndexQuery=new RefundFeeIndexDTO();
         refundFeeIndexQuery.setType(type);
         refundFeeIndexQuery.setProjectId(projectId);
         //查询第一个项目的机构名称
         String businessSourceStr = service.findAllRefundFeeIndex(refundFeeIndexQuery).get(0).getBusinessSourceStr();
         // 根据id查询退手续费信息
         List<RefundFeeDTO> refundRefundFeeList = service.findAllRefundFee(refundFeeQuery);
         // 把查出的数据填充到model中
         model.put("refundRefundFeeList", refundRefundFeeList);
         model.put("refundRefundFeeListSize", refundRefundFeeList.size());
         model.put("batchRefundFeeMain", batchRefundFeeMain);
         model.put("businessSourceStr", businessSourceStr);
      } catch (Exception e) {
         e.printStackTrace();
      }
      
      return url.toString();
   }

   /**
    * 跳转打印页面
    *@author:liangyanjun
    *@time:2016年2月24日上午12:38:14
    *@param model
    *@param refundFeeQuery
    *@param request
    * @param processDefinitionKey 
    *@return
    */
   @RequestMapping(value = "/print")
   public String print(ModelMap model, RefundFeeDTO refundFeeQuery, HttpServletRequest request, String processDefinitionKey) {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      int type = refundFeeQuery.getType();
      if (type == Constants.REFUND_FEE_TYPE_1) {
         url.append("refund_fee_print");
      } else if (type == Constants.REFUND_FEE_TYPE_2) {
         url.append("refund_interest_fee_print");
      } else if (type == Constants.REFUND_FEE_TYPE_3) {
         url.append("refund_tail_money_print");
      } else if (type == Constants.REFUND_FEE_TYPE_4) {
         url.append("refund_commission_print");
      }
      int projectId = refundFeeQuery.getProjectId();
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
          ProjectService.Client projectClient = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
          Project project = projectClient.getProjectInfoById(projectId);
         
          int projectSource = project.getProjectSource();//项目来源（1万通2小科）
         // 根据id查询退手续费信息
         // refundFeeQuery.setUserIds(getUserIds(request));
         List<RefundFeeDTO> refundRefundFeeList = service.findAllRefundFee(refundFeeQuery);
         if (refundRefundFeeList == null || refundRefundFeeList.isEmpty()) {
            return url.toString();
         }
         // 把查出的数据填充到model中
         RefundFeeDTO editRefundFeeDTO = refundRefundFeeList.get(0);
         model.put("editRefundFeeDTO", editRefundFeeDTO);
         model.put("projectSource", projectSource);
         // 查询审批历史记录
         WorkflowService.Client workClient = (WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
         WorkflowProjectVo vo = new WorkflowProjectVo();
         vo.setProjectId(projectId);
         vo.setProcessDefinitionKey(processDefinitionKey);
         WorkflowProjectVo w = workClient.findWorkflowProject(vo);
         if (null != w && w.getPid() > 0) {
            String workflowTaskDefKey = workClient.getRunLastTaskKeyByWIId(w.getWorkflowInstanceId());
            w.setWorkflowTaskDefKey(workflowTaskDefKey);
         }
         if (w != null && !StringUtil.isBlank(w.getWorkflowInstanceId())) {
            String workflowInstanceId = w.getWorkflowInstanceId();
            List<TaskHistoryDto> workFlowHistoryList = workClient.queryWorkFlowHistory(workflowInstanceId, 1, 100);
            Collections.reverse(workFlowHistoryList);
            model.put("workFlowHistoryList", workFlowHistoryList);
         }
      } catch (Exception e) {
         logger.error("查询退手续费失败refundFeeQuery："+ refundFeeQuery + ",错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }

      return url.toString();
   }

   /**
    * 跳转退手续费工作流处理页面
    *@author:liangyanjun
    *@time:2016年1月28日下午7:45:41
    *@param projectId
    *@param model
    *@param request
    *@return
    * @throws TException 
    * @throws ThriftServiceException 
    */
   @RequestMapping("editByProcess")
   public String editByProcess(RefundFeeDTO refundFeeQuery, ModelMap model, HttpServletRequest request) throws ThriftServiceException, TException {
      StringBuffer url = new StringBuffer(PAGE_PATH);
      int type = refundFeeQuery.getType();
      int projectId = refundFeeQuery.getProjectId();
      ProjectService.Client projectClient = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
      Project project = projectClient.getProjectInfoById(projectId);
      if (project.getForeclosureStatus()==Constants.FORECLOSURE_STATUS_13) {
         model.put("msg", "已归档（解保）项目，不能申请退费");
         return "msg_info";
      }
      int projectSource = project.getProjectSource();//项目来源（1万通2小科）
      if (type == Constants.REFUND_FEE_TYPE_1) {
         url.append("refund_fee_edit");
      } else if (type == Constants.REFUND_FEE_TYPE_2) {
         url.append("refund_interest_fee_edit");
      } else if (type == Constants.REFUND_FEE_TYPE_3) {
         url.append("refund_tail_money_edit");
         //根据项目来源（1万通2小科）加载流程
         if (projectSource==1) {
            model.put("workflowId", "WTrefundTailMoneyProcess");
            model.put("nextRoleCode", "GUARANTEE_COMMISSIONER");
         }else{
            model.put("workflowId", "refundTailMoneyProcess");
            model.put("nextRoleCode", "POST_LOAN_MANAGEMENT");
         }
      } else if (type == Constants.REFUND_FEE_TYPE_4) {
         url.append("refund_commission_edit");
      }
      try {
         Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
         // 根据id查询退手续费信息
         // refundFeeQuery.setUserIds(getUserIds(request));
         List<RefundFeeDTO> refundRefundFeeList = service.findAllRefundFee(refundFeeQuery);
         if (refundRefundFeeList == null || refundRefundFeeList.isEmpty()) {
            return url.toString();
         }
         RefundFeeDTO editRefundFeeDTO = refundRefundFeeList.get(0);
         model.put("editRefundFeeDTO", editRefundFeeDTO);
         model.put("isProcess", true);
         model.put("projectSource", projectSource);
      } catch (Exception e) {
         logger.error("跳转退手续费工作流处理页面：refundFeeQuery" + refundFeeQuery + ",错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
      return url.toString();
   }

   /**
    * 根据项目id和退费类型查询申请状态，不存在返回-1，存在返回对于的状态
    *@author:liangyanjun
    *@time:2016年3月3日下午11:56:04
    *@param refundFeeDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/getApplyStatus")
   @ResponseBody
   public int getApplyStatus(RefundFeeDTO refundFeeDTO, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      ShiroUser shiroUser = getShiroUser();
      try {
         List<RefundFeeDTO> list = service.findAllRefundFee(refundFeeDTO);
         if (list == null || list.isEmpty()) {
            return -1;
         }
         // 不是自己的单，返回-1
         RefundFeeDTO refundFee = list.get(0);
         int pmUserId = refundFee.getPmUserId();
         if (pmUserId != shiroUser.getPid()) {
            return -1;
         }
         int applyStatus = refundFee.getApplyStatus();
         return applyStatus;
      } catch (TException e) {
         e.printStackTrace();
         return -1;
      }
   }
   /**
    * 根据批量主表id查询申请状态，不存在返回-1，存在返回对于的状态
    *@author:liangyanjun
    *@time:2018年1月2日上午9:24:30
    *@param refundFeeDTO
    *@param request
    *@param response
    *@return
    */
   @RequestMapping(value = "/getBatchApplyStatus")
   @ResponseBody
   public int getBatchApplyStatus(RefundFeeDTO refundFeeDTO, HttpServletRequest request, HttpServletResponse response) {
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      int batchRefundFeeMainId = refundFeeDTO.getBatchRefundFeeMainId();
      try {
         BizLoanBatchRefundFeeMain batchRefundFeeMain = service.getBatchRefundFeeMainById(batchRefundFeeMainId);
         if (batchRefundFeeMain == null || batchRefundFeeMain.getPid()<=0) {
            return -1;
         }
         int applyStatus = batchRefundFeeMain.getApplyStatus();
         return applyStatus;
      } catch (TException e) {
         e.printStackTrace();
         return -1;
      }
   }

   /**
    * 退款确认
    *@author:liangyanjun
    *@time:2016年4月2日上午10:56:13
    *@param refundFeeDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/refundFeeConfirm", method = RequestMethod.POST)
   public void refundFeeConfirm(RefundFeeDTO refundFeeDTO, HttpServletRequest request, HttpServletResponse response) {
      ShiroUser shiroUser = getShiroUser();
      int projectId = refundFeeDTO.getProjectId();//
      int type = refundFeeDTO.getType();//退款类型：退手续费=1，退利息=2，退款=3，退佣金=4
      double confirmMoney = refundFeeDTO.getConfirmMoney();//确认金额
      String confirmDate = refundFeeDTO.getConfirmDate();//确认日期
      if (projectId <= 0 || type <= 0 || StringUtil.isBlank(confirmDate)) {
         logger.error("请求数据不合法：" + refundFeeDTO);
         fillReturnJson(response, false, "提交失败,请输入必填项,请重新操作!");
         return;
      }
      if (confirmMoney <= 0) {
         fillReturnJson(response, false, "提交失败,金额要大于0,请重新操作!");
         return;
      }
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
         // refundFeeQuery.setUserIds(getUserIds(request));
         refundFeeQuery.setType(type);
         refundFeeQuery.setProjectId(projectId);
         List<RefundFeeDTO> refundFeeList = service.findAllRefundFee(refundFeeQuery);
         if (refundFeeList == null || refundFeeList.isEmpty()) {
            fillReturnJson(response, false, "提交失败,数据不存在!");
            return;
         }
         RefundFeeDTO updateRefundFeeDTO = refundFeeList.get(0);
         // 检查项目是否归档
         int foreclosureStatus = updateRefundFeeDTO.getForeclosureStatus();
         if (foreclosureStatus == Constants.FORECLOSURE_STATUS_13) {
            fillReturnJson(response, false, "提交失败,项目已归档，不可修改!");
            return;
         }
         // 状态为,审核未通过，不可确认
         if (updateRefundFeeDTO.getApplyStatus() != Constants.APPLY_REFUND_FEE_STATUS_5) {
            fillReturnJson(response, false, "提交失败,审核未通过!");
            return;
         }
         // 状态为,审核未通过，不可确认
         if (updateRefundFeeDTO.getIsConfirm() == Constants.IS_CONFIRM) {
            fillReturnJson(response, false, "提交失败,退款已确认!");
            return;
         }
         updateRefundFeeDTO.setConfirmMoney(confirmMoney);
         updateRefundFeeDTO.setConfirmDate(confirmDate);
         updateRefundFeeDTO.setIsConfirm(Constants.IS_CONFIRM);
         updateRefundFeeDTO.setUpdateId(shiroUser.getPid());
         if (updateRefundFeeDTO.getCreaterId() == 0) {
            updateRefundFeeDTO.setCreaterId(shiroUser.getPid());
         }
         // 执行更新
         service.updateRefundFee(updateRefundFeeDTO);
         //往业务动态插入一条记录
         String bizDynamicRemark = "申请退费金额:" + MoneyFormatUtil.format(updateRefundFeeDTO.getReturnFee())+",收款人户名:" + updateRefundFeeDTO.getRecAccountName()+
               ",收款人账号:" + updateRefundFeeDTO.getRecAccount()+",开户行:" + updateRefundFeeDTO.getBankName()+
               ",确认金额:" + MoneyFormatUtil.format(updateRefundFeeDTO.getConfirmMoney())+",确认日期:" + updateRefundFeeDTO.getConfirmDate();
         if (type==Constants.REFUND_FEE_TYPE_1) {
            bizDynamicRemark = "申请退费金额:" + updateRefundFeeDTO.getReturnFee()+",确认金额:" + updateRefundFeeDTO.getConfirmMoney()+",确认日期:" + updateRefundFeeDTO.getConfirmDate();
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_17, bizDynamicRemark);
         }else if(type==Constants.REFUND_FEE_TYPE_2){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_18, bizDynamicRemark);
         }else if(type==Constants.REFUND_FEE_TYPE_3){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_19, bizDynamicRemark);
         }else if(type==Constants.REFUND_FEE_TYPE_4){
            finishBizDynamicByInloan(projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_20, bizDynamicRemark);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
      fillReturnJson(response, true, "提交成功");
   }
   /**
    * 保存批量退费信息
    *@author:liangyanjun
    *@time:2017年12月29日上午11:10:49
    *@param refundFeeDTO
    *@param request
    *@param response
    */
   @RequestMapping(value = "/saveBatchRefundFee", method = RequestMethod.POST)
   public void saveBatchRefundFee(RefundFeeDTO refundFeeDTO, HttpServletRequest request, HttpServletResponse response) {
      String recAccount = refundFeeDTO.getRecAccount();
      String bankName = refundFeeDTO.getBankName();
      String recAccountName = refundFeeDTO.getRecAccountName();
      String batchName = refundFeeDTO.getBatchName();
      int batchRefundFeeMainId = refundFeeDTO.getBatchRefundFeeMainId();
      List<RefundFeeMap> batchRefundFeeMapList = refundFeeDTO.getBatchRefundFeeMapList();
      if (StringUtil.isBlank(recAccount,bankName,recAccountName,batchName)||batchRefundFeeMapList==null||batchRefundFeeMapList.isEmpty()) {
         logger.error("请求数据不合法：" + refundFeeDTO);
         fillReturnJson(response, false, "提交失败,请输入必填项,请重新操作!");
         return;
      }
      Client service = (Client) getService(BusinessModule.MODUEL_INLOAN, serviceName);
      try {
         ShiroUser shiroUser = getShiroUser();
         Integer userId = shiroUser.getPid();
         refundFeeDTO.setUpdateId(userId);
         BizLoanBatchRefundFeeMain updateBatchRefundFeeMain = service.getBatchRefundFeeMainById(batchRefundFeeMainId);
         if (updateBatchRefundFeeMain.getPid()>0&&updateBatchRefundFeeMain.getApplyStatus()> Constants.APPLY_REFUND_FEE_STATUS_3) {
            fillReturnJson(response, false, "提交失败,状态为待审核以后不可修改!");
            return;
         }
         batchRefundFeeMainId = service.saveBatchRefundFee(refundFeeDTO);
         
      } catch (Exception e) {
         logger.error("保存批量退费信息失败：入参：RefundFeeDTO" + refundFeeDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         fillReturnJson(response, false, "提交失败,请联系管理员!");
         e.printStackTrace();
         return;
      }
      Map<String, Object> body=new HashMap<String, Object>();
      body.put("batchRefundFeeMainId", batchRefundFeeMainId);
      fillReturnJson(response, true, "提交成功", body);
   }
   
}
