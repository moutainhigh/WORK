package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
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
 * ★☆ @ClassAnnotation：退尾款处理工作流特殊处理类 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component("refundTailMoneyProcessWrokflow")
@Scope("prototype")
public class RefundTailMoneyProcessWrokflow extends WorkflowSpecialDispose {

   @SuppressWarnings("rawtypes")
   @Resource(name = "workflowProjectMapper")
   private WorkflowProjectMapper workflowProjectMapper;

   @SuppressWarnings("rawtypes")
   @Resource(name = "refundFeeMapper")
   private RefundFeeMapper refundFeeMapper;

   @Override
   public int updateProjectStatus() {
      int res = -1;
      String isVetoProject = super.getBean().getIsVetoProject();
      String workflowTaskDefKey = super.getBean().getWorkflowTaskDefKey();
      RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
      refundFeeQuery.setProjectId(super.getBean().getProjectId());
      refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_3);
      List<RefundFeeDTO> refundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
      if (refundFeeList == null || refundFeeList.isEmpty()) {
         return res;
      }
      RefundFeeDTO updateTailMoneyDTO = refundFeeList.get(0);
//      int applyStatus = updateTailMoneyDTO.getApplyStatus();
//      if (applyStatus>=Constants.APPLY_REFUND_FEE_STATUS_3) {
//         throw new WorkFlowExistException();
//      }
          //驳回
      if (!StringUtil.isBlank(super.getBean().getIsReject())) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_3);
         refundFeeMapper.updateRefundFee(updateTailMoneyDTO);
         // 开始流程${flow=='task_Request'}
      } else if ("task_Request".equals(workflowTaskDefKey)) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_4);
         refundFeeMapper.updateRefundFee(updateTailMoneyDTO);
         // 否决${flow=='refuse'}
      } else if ("refuse".equals(isVetoProject)) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_6);
         refundFeeMapper.updateRefundFee(updateTailMoneyDTO);
         // 走完最后一个流程时更新处理状态为已通过
      } else if ("task_FinanceSupervisorCheck".equals(workflowTaskDefKey)) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_5);
         refundFeeMapper.updateRefundFee(updateTailMoneyDTO);
         //驳回之后执行下一个任务，修改状态为审核中
      }else if(updateTailMoneyDTO.getApplyStatus()==Constants.APPLY_REFUND_FEE_STATUS_3){
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_4);
         refundFeeMapper.updateRefundFee(updateTailMoneyDTO);
      }
      return res;
   }

   @Override
   public void resetProject() {
      RefundFeeDTO refundFeeQuery = new RefundFeeDTO();
      refundFeeQuery.setProjectId(super.getBean().getProjectId());
      refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_3);
      List<RefundFeeDTO> refundFeeList = refundFeeMapper.findAllRefundFee(refundFeeQuery);
      if (refundFeeList == null || refundFeeList.isEmpty()) {
         return;
      }
      RefundFeeDTO updateTailMoneyDTO = refundFeeList.get(0);
      updateTailMoneyDTO.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_1);
      refundFeeMapper.updateRefundFee(updateTailMoneyDTO);
   }
}
