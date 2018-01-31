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

import com.xlkfinance.bms.rpc.afterloan.LoanDivertinfo;
import com.xlkfinance.bms.server.afterloan.mapper.AfterLoanDivertMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

/**
 * @ClassName: MisappropriateRequestProcessWrokflow
 * @Description: 挪用处理工作流特殊处理类
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午5:49:06
 */
@Component("misappropriateRequestProcessWrokflow")
@Scope("prototype")
public class MisappropriateRequestProcessWrokflow extends
		WorkflowSpecialDispose {

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "afterLoanDivertMapper")
	private AfterLoanDivertMapper afterLoanDivertMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "workflowProjectMapper")
	private WorkflowProjectMapper workflowProjectMapper;

	@Override
	public int updateProjectStatus() {
		int res = 2;
		if (null != super.getBean().getIsReject()) {
			if ("task_PrepaymentRequest".equals(super.getBean().getIsReject())) {
				LoanDivertinfo dto = new LoanDivertinfo();
				dto.setDivertId(super.getBean().getRefId());
				dto.setRequestStatus(1);
				afterLoanDivertMapper.changeReqstDivert(dto);
			}
			// 否决${flow=='refuse'}
		} else if (null != super.getBean().getIsVetoProject()
				&& !"".equals(super.getBean().getIsVetoProject())
				&& "refuse".equals(super.getBean().getIsVetoProject())) {
			// 更新挪用表处理状态为已归档
			LoanDivertinfo dto = new LoanDivertinfo();
			dto.setDivertId(super.getBean().getRefId());
			dto.setRequestStatus(4);
			afterLoanDivertMapper.changeReqstDivert(dto);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			// 流程开始
		} else if ("task_PrepaymentRequest".equals(super.getBean()
				.getWorkflowTaskDefKey())) { // 提交申请
			// 更新挪用表处理状态为已归档
			LoanDivertinfo dto = new LoanDivertinfo();
			dto.setDivertId(super.getBean().getRefId());
			dto.setRequestStatus(2);
			afterLoanDivertMapper.changeReqstDivert(dto);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),6);
			res = 2;
			// 流程结束
		} else if ("task_FinancialConfirm".equals(super.getBean()
				.getWorkflowTaskDefKey())) {
			// 更新挪用表处理状态为已归档
			LoanDivertinfo dto = new LoanDivertinfo();
			dto.setDivertId(super.getBean().getRefId());
			dto.setRequestStatus(4);
			afterLoanDivertMapper.changeReqstDivert(dto);
			// 1.2 更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean()
					.getProjectId());

			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			res = 3;
		}
		return res;
	}
	@Override
	public void resetProject(){
		// 更新挪用表处理状态为已归档
		LoanDivertinfo dto = new LoanDivertinfo();
		dto.setDivertId(super.getBean().getRefId());
		dto.setRequestStatus(1);
		afterLoanDivertMapper.changeReqstDivert(dto);
	}
}
