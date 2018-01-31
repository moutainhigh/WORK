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

import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlDatView;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepayFeewdtlMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * @ClassName: FeeWaiversRequestProcessWrokflow
 * @Description: 费用减免工作流特殊处理类
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午5:52:03
 */
@Component("feeWaiversRequestProcessWrokflow")
@Scope("prototype")
public class FeeWaiversRequestProcessWrokflow extends WorkflowSpecialDispose {

	@SuppressWarnings("rawtypes")
	@Resource(name = "repayFeewdtlMapper")
	private RepayFeewdtlMapper repayFeewdtlMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@Override
	public int updateProjectStatus() {
		int res = 2;
		if (null != super.getBean().getIsReject()) {
			if ("task_FeeWaiversRequest".equals(super.getBean().getIsReject())) {
				RepayFeewdtlDatView view = new RepayFeewdtlDatView();
				view.setRepayId(super.getBean().getRefId());
				view.setRequestStatus(1);
				repayFeewdtlMapper.changeReqstFeewdel(view);
			}
			// 否决${flow=='refuse'}
		} else if (null != super.getBean().getIsVetoProject()
				&& !"".equals(super.getBean().getIsVetoProject())
				&& "refuse".equals(super.getBean().getIsVetoProject())) {
			RepayFeewdtlDatView view = new RepayFeewdtlDatView();
			view.setRepayId(super.getBean().getRefId());
			view.setRequestStatus(3);
			repayFeewdtlMapper.changeReqstFeewdel(view);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			// 流程开始
		} else if ("task_FeeWaiversRequest".equals(super.getBean()
				.getWorkflowTaskDefKey())) {
			RepayFeewdtlDatView view = new RepayFeewdtlDatView();
			view.setRepayId(super.getBean().getRefId());
			view.setRequestStatus(2);
			repayFeewdtlMapper.changeReqstFeewdel(view);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),3);
			res = 2;
			// 流程结束
		} else if ("task_FinancialConfirm".equals(super.getBean().getWorkflowTaskDefKey())) {
			RepayFeewdtlDatView view = new RepayFeewdtlDatView();
			view.setRepayId(super.getBean().getRefId());
			view.setRequestStatus(4);
			repayFeewdtlMapper.changeReqstFeewdel(view);
			// 把已经对账完毕的期数的减免设置为无效
			repayFeewdtlMapper.changeFeewdelStatus(view.getRepayId());
			
			// 更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getProjectId());
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			res = 3;
		}
		return res;
	}
	@Override
	public void resetProject(){
		RepayFeewdtlDatView view = new RepayFeewdtlDatView();
		view.setRepayId(super.getBean().getRefId());
		view.setRequestStatus(1);
		repayFeewdtlMapper.changeReqstFeewdel(view);
	}
}
