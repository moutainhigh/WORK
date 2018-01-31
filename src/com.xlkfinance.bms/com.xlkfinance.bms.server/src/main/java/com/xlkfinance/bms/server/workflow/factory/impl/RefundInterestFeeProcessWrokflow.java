package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.inloan.BizBatchRefundFeeRelation;
import com.xlkfinance.bms.rpc.inloan.BizLoanBatchRefundFeeMain;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizBatchRefundFeeRelationMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizLoanBatchRefundFeeMainMapper;
import com.xlkfinance.bms.server.inloan.mapper.RefundFeeMapper;
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
 * ★☆ @ClassAnnotation：退咨询费处理工作流特殊处理类 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component("refundInterestFeeProcessWrokflow")
@Scope("prototype")
public class RefundInterestFeeProcessWrokflow extends WorkflowSpecialDispose {

   @SuppressWarnings("rawtypes")
   @Resource(name = "workflowProjectMapper")
   private WorkflowProjectMapper workflowProjectMapper;

   @SuppressWarnings("rawtypes")
   @Resource(name = "refundFeeMapper")
   private RefundFeeMapper refundFeeMapper;
   
   @Resource(name = "projectMapper")
   private ProjectMapper projectMapper;
   
   @Resource(name = "bizBatchRefundFeeRelationMapper")
   private BizBatchRefundFeeRelationMapper batchRefundFeeRelationMapper;
   
   @Resource(name = "bizLoanBatchRefundFeeMainMapper")
   private BizLoanBatchRefundFeeMainMapper batchRefundFeeMainMapper;

   @Override
   public int updateProjectStatus() {
      int res = -1;
      WorkflowSpecialBean bean = super.getBean();
      String isReject = bean.getIsReject();
      String workflowTaskDefKey = bean.getWorkflowTaskDefKey();
      int projectId = bean.getProjectId();
      int refId = bean.getRefId();
      BizLoanBatchRefundFeeMain batchRefundFeeMain = batchRefundFeeMainMapper.getById(projectId);
      boolean batchRefundFeeFlag=false;
      int newApplyStatus=4;
      //判断是否是批量退费
      if (projectId==refId&&batchRefundFeeMain!=null) {
         batchRefundFeeFlag=true;
      }
      //批量退费更改审核状态
      if (batchRefundFeeFlag) {
         BizBatchRefundFeeRelation query=new BizBatchRefundFeeRelation();
         query.setBatchRefundFeeMainId(projectId);
         List<BizBatchRefundFeeRelation> list = batchRefundFeeRelationMapper.getAll(query);
         for (BizBatchRefundFeeRelation refundFeeRelation : list) {
            RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
            refundFeeQuery.setProjectId(refundFeeRelation.getProjectId());
            refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_2);
            List<RefundFeeDTO> refundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
            if (refundFeeList == null || refundFeeList.isEmpty()) {
               return res;
            }
            RefundFeeDTO updateRefundFeeDTO = refundFeeList.get(0);
            int oldApplyStatus = updateRefundFeeDTO.getApplyStatus();
            newApplyStatus = getNewApplyStatus(bean, isReject, workflowTaskDefKey, newApplyStatus, oldApplyStatus);
            updateRefundFeeDTO.setApplyStatus(newApplyStatus);
            refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
         }
         if (list!=null&&list.size()>0) {
            batchRefundFeeMain.setApplyStatus(newApplyStatus);
            batchRefundFeeMainMapper.update(batchRefundFeeMain);
         }
         //批单一退费更改审核状态
      }else{
         RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
         refundFeeQuery.setProjectId(projectId);
         refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_2);
         List<RefundFeeDTO> refundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
         if (refundFeeList == null || refundFeeList.isEmpty()) {
            return res;
         }
         RefundFeeDTO updateRefundFeeDTO = refundFeeList.get(0);
         int oldApplyStatus = updateRefundFeeDTO.getApplyStatus();
         newApplyStatus = getNewApplyStatus(bean, isReject, workflowTaskDefKey, newApplyStatus, oldApplyStatus);
         updateRefundFeeDTO.setApplyStatus(newApplyStatus);
         refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
      }
      return res;
   }

   private int getNewApplyStatus(WorkflowSpecialBean bean, String isReject, String workflowTaskDefKey, int newApplyStatus,
         int oldApplyStatus) {
      // 驳回
      if (!StringUtil.isBlank(bean.getIsReject())) {
         newApplyStatus=Constants.APPLY_REFUND_FEE_STATUS_3;
         // 开始流程${flow=='task_Request'}
      }else if ("task_Request".equals(workflowTaskDefKey)) {
         newApplyStatus=Constants.APPLY_REFUND_FEE_STATUS_4;
         // 否决${flow=='refuse'}
      } else if ("refuse".equals(isReject)) {
         newApplyStatus=Constants.APPLY_REFUND_FEE_STATUS_6;
         // 走完最后一个流程时更新处理状态为已通过
      } else if ("task_FinanceSupervisorCheck".equals(workflowTaskDefKey)) {
         newApplyStatus=Constants.APPLY_REFUND_FEE_STATUS_5;
         // 驳回之后执行下一个任务，修改状态为审核中
      } else if(oldApplyStatus == Constants.APPLY_REFUND_FEE_STATUS_3){
            newApplyStatus=Constants.APPLY_REFUND_FEE_STATUS_4;
      }
      return newApplyStatus;
   }

   @Override
   public void resetProject() {
      RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
      refundFeeQuery.setProjectId(super.getBean().getProjectId());
      refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_2);
      List<RefundFeeDTO> refundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
      if (refundFeeList == null || refundFeeList.isEmpty()) {
         return;
      }
      RefundFeeDTO updateRefundFeeDTO = refundFeeList.get(0);
      updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_1);
      refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
   }

   /**
    *@author:liangyanjun
    *@time:2016年10月13日下午3:49:41
    *
    */
    @Override
    public void handleBranch(Task task, Map<String, Object> taskVariables) {
        // 申请时就判断项目来源是否为小科或万通：万通和小科的流程不一样
        if ("task_Request".equals(task.getTaskDefinitionKey())) {
            int projectId = super.getBean().getProjectId();
            int projectSource=2;//默认小科
            Project project = projectMapper.getProjectInfoById(projectId);
           if (project != null && project.getPid() > 0) {
              projectSource = project.getProjectSource();
           }
           taskVariables.put("projectSource", projectSource);
        }
    }
   
}
