/**
 * @Title: IoanRequestProcessWrokflow.java
 * @Package com.xlkfinance.bms.web.controller.task.factory.impl
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午5:08:42
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory.impl;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.rpc.repayment.AdvRepaymentBaseDTO;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepaymentMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
  * @ClassName: PrepaymentRequestProcessWrokflow
  * @Description: 提前还款申请工作流 特殊处理类
  * @author: JingYu.Dai
  * @date: 2015年4月3日 下午5:43:21
  */
@Component("prepaymentRequestProcessWrokflow")
@Scope("prototype")
public class PrepaymentRequestProcessWrokflow extends WorkflowSpecialDispose{

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "repaymentMapper")
	private RepaymentMapper repaymentMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;
	
	@Override
	public int updateProjectStatus() {
		int res = 2;
		
		if(null != super.getBean().getIsReject()){
			if("task_PrepaymentRequest".equals(super.getBean().getIsReject())){
				AdvRepaymentBaseDTO dto = new AdvRepaymentBaseDTO();
				dto.setRequestStatus(1);
				dto.setPreRepayId(super.getBean().getRefId());
				repaymentMapper.changeReqstPro(dto);
			}
		//流程开始
		}else if("task_PrepaymentRequest".equals(super.getBean().getWorkflowTaskDefKey())){
			AdvRepaymentBaseDTO dto = new AdvRepaymentBaseDTO();
			dto.setRequestStatus(2);
			dto.setPreRepayId(super.getBean().getRefId());
			repaymentMapper.changeReqstPro(dto);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),2);
			res =2;
		}else if("task_FinancialRepayment".equals(super.getBean().getWorkflowTaskDefKey())){
			AdvRepaymentBaseDTO dto = new AdvRepaymentBaseDTO();
			dto.setRequestStatus(4);
			dto.setPreRepayId(super.getBean().getRefId());
			repaymentMapper.changeReqstPro(dto);
			// 更新对账状态 
			loanMapper.updateIsReconciliationStatus(super.getBean().getProjectId());
			// 1.2  更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getProjectId());
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			
			res =3;
		} 
		return res;
	}
	@Override
	public void resetProject(){
		AdvRepaymentBaseDTO dto = new AdvRepaymentBaseDTO();
		//申请状态(申请中=0，办理中=1，审核=2)
		dto.setRequestStatus(0);
		dto.setPreRepayId(super.getBean().getRefId());
		repaymentMapper.changeReqstPro(dto);
	}
}
