package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferDTO;
import com.xlkfinance.bms.server.inloan.mapper.IntermediateTransferMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年4月2日下午5:11:49 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 中途划转<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component("intermediateTransferProcessWrokflow")
@Scope("prototype")
public class IntermediateTransferProcessWrokflow extends WorkflowSpecialDispose {

   @SuppressWarnings("rawtypes")
   @Resource(name = "workflowProjectMapper")
   private WorkflowProjectMapper workflowProjectMapper;

   @SuppressWarnings("rawtypes")
   @Resource(name = "intermediateTransferMapper")
   private IntermediateTransferMapper intermediateTransferMapper;

   @Override
   public int updateProjectStatus() {
      int res = -1;
      String isVetoProject = super.getBean().getIsVetoProject();
      String workflowTaskDefKey = super.getBean().getWorkflowTaskDefKey();
      IntermediateTransferDTO intermediateTransferQuery = new IntermediateTransferDTO();
      intermediateTransferQuery.setProjectId(super.getBean().getProjectId());
      intermediateTransferQuery.setPid(super.getBean().getRefId());
      List<IntermediateTransferDTO> intermediateTransferList = intermediateTransferMapper.queryIntermediateTransfer(intermediateTransferQuery);
      if (intermediateTransferList == null || intermediateTransferList.isEmpty()) {
         return res;
      }
      IntermediateTransferDTO updateTailMoneyDTO = intermediateTransferList.get(0);
      // int applyStatus = updateTailMoneyDTO.getApplyStatus();
      // if (applyStatus>=Constants.APPLY_STATUS_3) {
      // throw new WorkFlowExistException();
      // }
      // 驳回
      if (!StringUtil.isBlank(super.getBean().getIsReject())) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_STATUS_3);
         intermediateTransferMapper.updateIntermediateTransfer(updateTailMoneyDTO);
         // 开始流程${flow=='task_Request'}
      } else if ("task_Request".equals(workflowTaskDefKey)) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_STATUS_4);
         intermediateTransferMapper.updateIntermediateTransfer(updateTailMoneyDTO);
         // 否决${flow=='refuse'}
      } else if ("refuse".equals(isVetoProject)) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_STATUS_6);
         intermediateTransferMapper.updateIntermediateTransfer(updateTailMoneyDTO);
         // 走完最后一个流程时更新处理状态为已通过
      } else if ("task_FinanceSupervisorCheck".equals(workflowTaskDefKey)) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_STATUS_5);
         intermediateTransferMapper.updateIntermediateTransfer(updateTailMoneyDTO);
         // 驳回之后执行下一个任务，修改状态为审核中
      } else if (updateTailMoneyDTO.getApplyStatus() == Constants.APPLY_STATUS_3) {
         updateTailMoneyDTO.setApplyStatus(Constants.APPLY_STATUS_4);
         intermediateTransferMapper.updateIntermediateTransfer(updateTailMoneyDTO);
      }
      return res;
   }

   @Override
   public void resetProject() {
      IntermediateTransferDTO intermediateTransferQuery = new IntermediateTransferDTO();
      intermediateTransferQuery.setProjectId(super.getBean().getProjectId());
      intermediateTransferQuery.setPid(super.getBean().getRefId());
      List<IntermediateTransferDTO> intermediateTransferList = intermediateTransferMapper.queryIntermediateTransfer(intermediateTransferQuery);
      if (intermediateTransferList == null || intermediateTransferList.isEmpty()) {
         return;
      }
      IntermediateTransferDTO updateTailMoneyDTO = intermediateTransferList.get(0);
      updateTailMoneyDTO.setApplyStatus(Constants.APPLY_STATUS_2);
      intermediateTransferMapper.updateIntermediateTransfer(updateTailMoneyDTO);
   }
   /**
    * 处理分支
    *@author:liangyanjun
    *@time:2016年4月21日上午10:01:57
    *@param task
    *@param fromVariables
    */
   @Override
   public void handleBranch(Task task,Map<String, Object> taskVariables) {
      //业务总监审核节点：判断类型
      if ("task_BizDirectorCheck".equals(task.getTaskDefinitionKey())) {
         IntermediateTransferDTO intermediateTransferQuery = new IntermediateTransferDTO();
         intermediateTransferQuery.setProjectId(super.getBean().getProjectId());
         intermediateTransferQuery.setPid(super.getBean().getRefId());
         List<IntermediateTransferDTO> intermediateTransferList = intermediateTransferMapper.queryIntermediateTransfer(intermediateTransferQuery);
         if (intermediateTransferList == null || intermediateTransferList.isEmpty()) {
            return;
         }
         IntermediateTransferDTO tailMoneyDTO = intermediateTransferList.get(0);
         int specialType = tailMoneyDTO.getSpecialType();
         taskVariables.put("specialType", specialType);
      }
   }
   
}
