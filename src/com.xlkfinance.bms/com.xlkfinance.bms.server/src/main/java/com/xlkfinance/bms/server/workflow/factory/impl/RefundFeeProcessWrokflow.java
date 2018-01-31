package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.server.inloan.mapper.BizHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.RefundFeeMapper;
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
 * ★☆ @ClassAnnotation：退手续费处理工作流特殊处理类 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component("refundFeeProcessWrokflow")
@Scope("prototype")
public class RefundFeeProcessWrokflow extends WorkflowSpecialDispose {

   @SuppressWarnings("rawtypes")
   @Resource(name = "workflowProjectMapper")
   private WorkflowProjectMapper workflowProjectMapper;

   @SuppressWarnings("rawtypes")
   @Resource(name = "refundFeeMapper")
   private RefundFeeMapper refundFeeMapper;
   
   @Resource(name = "bizHandleMapper")
   private BizHandleMapper bizHandleMapper;

   @Override
   public int updateProjectStatus() {
      int res = -1;
      String isReject = super.getBean().getIsReject();
      String workflowTaskDefKey = super.getBean().getWorkflowTaskDefKey();
      RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
      int projectId = super.getBean().getProjectId();
      refundFeeQuery.setProjectId(projectId);
      refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_1);
      List<RefundFeeDTO> RefundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
      if (RefundFeeList == null || RefundFeeList.isEmpty()) {
         return res;
      }
      RefundFeeDTO updateRefundFeeDTO = RefundFeeList.get(0);
//      int applyStatus = updateRefundFeeDTO.getApplyStatus();
//      if (applyStatus>=Constants.APPLY_REFUND_FEE_STATUS_3) {
//         throw new WorkFlowExistException();
//      }
         //驳回
      if (!StringUtil.isBlank(super.getBean().getIsReject())) {
         updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_3);
         refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
         // 开始流程${flow=='task_RefundFeeRequest'}
      } else if ("task_Request".equals(workflowTaskDefKey)) {
         updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_4);
         refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
         // 否决${flow=='refuse'}
      } else if ("refuse".equals(isReject)) {
         updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_6);
         refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
         // 走完最后一个流程时更新处理状态为已通过
      } else if ("task_FinanceSupervisorCheck".equals(workflowTaskDefKey)) {
         updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_5);
         refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
         //驳回之后执行下一个任务，修改状态为审核中
      }else if(updateRefundFeeDTO.getApplyStatus()==Constants.APPLY_REFUND_FEE_STATUS_3){
         updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_4);
         refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
      }
      return res;
   }

   @Override
   public void resetProject() {
      RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
      refundFeeQuery.setProjectId(super.getBean().getProjectId());
      refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_1);
      List<RefundFeeDTO> RefundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
      if (RefundFeeList == null || RefundFeeList.isEmpty()) {
         return;
      }
      RefundFeeDTO updateRefundFeeDTO = RefundFeeList.get(0);
      updateRefundFeeDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_1);
      refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
   }
}
