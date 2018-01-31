package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.MortgageDynamicConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月28日上午9:28:26 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：房抵贷放款申请工作流特殊处理类 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component("fddMakeLoansProcessWrokflow")
@Scope("prototype")
public class FddMakeLoansProcessWrokflow extends WorkflowSpecialDispose {
   private Logger logger = LoggerFactory.getLogger(FddMakeLoansProcessWrokflow.class);
   @SuppressWarnings("rawtypes")
   @Resource(name = "workflowProjectMapper")
   private WorkflowProjectMapper workflowProjectMapper;

   @SuppressWarnings("rawtypes")
   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;
   
   @Resource(name = "projectMapper")
   private ProjectMapper projectMapper;
   
   @Resource(name = "projectInfoServiceImpl")
   private ProjectInfoService.Iface projectInfoService;
   
   @Resource(name = "bizDynamicServiceImpl")
   private BizDynamicService.Iface bizDynamicService;

@Override
   public int updateProjectStatus() throws TException {
      int res = 1;
      WorkflowSpecialBean bean = super.getBean();
      String isReject = bean.getIsReject();
      String workflowTaskDefKey = bean.getWorkflowTaskDefKey();
      int projectId = bean.getProjectId();
      int handleAuthorId = bean.getHandleAuthorId();
      String message = bean.getMessage();
      //获取项目信息
      Project project = projectMapper.getProjectInfoById(projectId);
      String capitalName = project.getCapitalName();//资方
      
      ApplyFinanceHandleDTO applyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_3);
      if (applyFinanceHandle == null || applyFinanceHandle.getPid() <= 0) {
         return res;
      }
      double makeLoansMoney = applyFinanceHandle.getRecMoney();// 第一次放款金额
      // 驳回
      if (!StringUtil.isBlank(isReject)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_3);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         updateRejectStatus(isReject, projectId);
         return res;
         // 开始流程
      } else if ("task_Request".equals(workflowTaskDefKey)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_4);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         // 否决${flow=='refuse'}
      } else if ("refuse".equals(isReject)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_6);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         // 走完最后一个流程时处理状态为已通过
         // 1.自有资金小于300w资金主管审核同意
      } else if ("ziyouzijin".equals(capitalName)&&("task_FundManagerCheck".equals(workflowTaskDefKey) && makeLoansMoney < 3000000)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_5);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         updateMakeLoans(projectId,handleAuthorId);
         // 修改审批状态 还款中
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_REPAYMENT, "");
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_4, message);
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_6, message);
         return res;
         // 走完最后一个流程时处理状态为已通过
         // 2.自有资金大于等于300w财务总监审批同意
      } else if ("ziyouzijin".equals(capitalName)&&"task_FinanceDirectorCheck".equals(workflowTaskDefKey)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_5);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         updateMakeLoans(projectId,handleAuthorId);
         // 修改审批状态 还款中
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_REPAYMENT, "");
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_5, message);
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_6, message);
         return res;
         // 3.非自有资金走完最后一个流程时处理状态为已通过
      } else if ("task_operateDepartmentCheck2".equals(workflowTaskDefKey)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_5);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         updateMakeLoans(projectId,handleAuthorId);
         // 修改审批状态 还款中
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_REPAYMENT, "");
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_3, message);
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_6, message);
         return res;
      } 
      // 驳回之后执行下一个任务，修改状态为审核中
      if (applyFinanceHandle.getApplyStatus() == Constants.APPLY_STATUS_3) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_4);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
      }
      
      updateNextStatus(workflowTaskDefKey, projectId, handleAuthorId, message);
      return res;
   }

   /**
    * 提交下一步任务修改全局审批状态
    *@author:liangyanjun
    *@time:2017年12月27日上午10:50:36
    *@param workflowTaskDefKey
    *@param projectId
    * @param handleAuthorId 
    * @param message 
    * @throws TException 
    */
   private void updateNextStatus(String workflowTaskDefKey, int projectId, int handleAuthorId, String message) throws TException {
      if ("task_Request".equals(workflowTaskDefKey)) {
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_MAKE_LOAN_CHECK, "");
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_2, message);
      } else if("task_operateDepartmentCheck".equals(workflowTaskDefKey)||"task_operateDepartmentCheck2".equals(workflowTaskDefKey)) {
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_MAKE_LOAN_FUNDS, "");
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_3, message);
      }else if("task_Request".equals(workflowTaskDefKey)){
         finishBizDynamicByInloan(handleAuthorId, projectId, MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_4, message);
      }else if("task_Request".equals(workflowTaskDefKey)){
         
      }
   }
   /**
    * 驳回任务修改全局审批状态
    *@author:liangyanjun
    *@time:2017年12月27日上午10:50:36
    *@param workflowTaskDefKey
    *@param projectId
    */
   private void updateRejectStatus(String workflowTaskDefKey, int projectId) {
      if ("task_Request".equals(workflowTaskDefKey)) {
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_MAKE_LOAN, "");
      } else if("task_operateDepartmentCheck".equals(workflowTaskDefKey)||"task_operateDepartmentCheck2".equals(workflowTaskDefKey)) {
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_MAKE_LOAN_CHECK, "");
      }
   }
   

/**
 *@author:liangyanjun
 * @param userId 
 * @throws TException 
 * @throws ThriftServiceException 
 *@time:2017年12月14日下午3:46:55
 */
   private void updateMakeLoans(int projectId, int userId) throws ThriftServiceException, TException {
      FinanceHandleDTO updateFinanceHandle = financeHandleMapper.getFinanceHandleByProjectId(projectId);
      updateFinanceHandle.setStatus(Constants.REC_STATUS_ALREADY_LOAN);
      financeHandleMapper.updateFinanceHandle(updateFinanceHandle);
      //生成还款计划数据
      projectInfoService.makeRepaymentPlan(projectId, userId);
   }


   /**
    * 重置
    */
   @Override
   public void resetProject() {
      int projectId = super.getBean().getProjectId();
      ApplyFinanceHandleDTO applyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_3);
      if (applyFinanceHandle == null || applyFinanceHandle.getPid() <= 0) {
         return;
      }
      applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_1);
      financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
   }

   /**
    * 处理分支
    *@author:liangyanjun
    *@time:2016年8月8日下午3:16:26
    */
   @Override
   public void handleBranch(Task task, Map<String, Object> taskVariables) {
      // 更新下一级节点审批用户到项目的待办人中
      WorkflowSpecialBean bean = super.getBean();
      try {
         Object candidateUsers = taskVariables.get("candidateUsers");
         projectInfoService.setNextUserInfo(candidateUsers == null ? null : String.valueOf(candidateUsers), bean.getProjectId());
      }
      catch (Exception e) {
         logger.error("更新下一级节点审批用户到项目的待办人中：" + ExceptionUtil.getExceptionMessage(e));
      }
      // 资金主管审核节点：判断贷款金额是否需要财务总监审核
      if ("task_FundManagerCheck".equals(task.getTaskDefinitionKey())) {
         int refId = super.getBean().getRefId();
         ApplyFinanceHandleDTO applyFinanceHandle = financeHandleMapper.getApplyFinanceHandleById(refId);
         if (applyFinanceHandle == null || applyFinanceHandle.getPid() <= 0) {
            return;
         }
         double recMoney = applyFinanceHandle.getRecMoney();
         taskVariables.put("makeLoansMoney", recMoney);
      }
      // 房抵贷放款申请审核节点：判断资方走不一样流程
      if ("task_Request".equals(task.getTaskDefinitionKey())) {
         int projectId = super.getBean().getProjectId();
          //获取项目信息
         Project project = projectMapper.getProjectInfoById(projectId);
         String capitalName = project.getCapitalName();//资方
         taskVariables.put("capitalName", capitalName);
      }
   }
   protected void finishBizDynamicByInloan(int userId,int projectId,String dynamicNumber,String remark) throws TException{
      BizDynamic bizDynamic=new BizDynamic();
      bizDynamic.setProjectId(projectId);
      bizDynamic.setModuelNumber(MortgageDynamicConstant.MODUEL_NUMBER_FINANCE);
      bizDynamic.setDynamicNumber(dynamicNumber);
      bizDynamic.setDynamicName(MortgageDynamicConstant.DYNAMIC_NUMBER_FINANCE_MAP.get(dynamicNumber));
      bizDynamic.setHandleAuthorId(userId);
      bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
      bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
      bizDynamic.setRemark(remark);
      bizDynamicService.addOrUpdateBizDynamic(bizDynamic);
}
}
