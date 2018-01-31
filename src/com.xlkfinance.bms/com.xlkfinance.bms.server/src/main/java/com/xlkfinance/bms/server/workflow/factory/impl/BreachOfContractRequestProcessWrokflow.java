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

import org.apache.thrift.TException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.rpc.afterloan.BizProjectViolation;
import com.xlkfinance.bms.rpc.afterloan.MisapprProcessService;
import com.xlkfinance.bms.server.afterloan.mapper.MisapprProcessMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
  * @ClassName: BreachOfContractRequestProcessWrokflow
  * @Description: 违约处理工作流特殊处理类
  * @author: JingYu.Dai
  * @date: 2015年4月3日 下午5:54:02
 */
@Component("breachOfContractRequestProcessWrokflow")
@Scope("prototype")
public class BreachOfContractRequestProcessWrokflow extends WorkflowSpecialDispose{

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "misapprProcessMapper")
	private MisapprProcessMapper misapprProcessMapper;

	@Resource(name = "misapprProcessServiceImpl")
	private MisapprProcessService.Iface misapprProcessService;
	
	@Override
	public int updateProjectStatus() throws TException {
		int res = 2;
		//驳回
		if(null != super.getBean().getIsReject()){
			if("task_BreachOfContractRequest".equals(super.getBean().getIsReject())){
				BizProjectViolation dto = new BizProjectViolation();
				// 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
				dto.setRequestStatus(1);
				dto.setPid(super.getBean().getRefId());
				//修改违约申请状态
				misapprProcessMapper.changeReqstViolation(dto);
			}
		//否决${flow=='refuse'}	
		}else if(null != super.getBean().getIsVetoProject() 
				&& !"".equals(super.getBean().getIsVetoProject())
				&& "refuse".equals(super.getBean().getIsVetoProject())
				){
			BizProjectViolation dto = new BizProjectViolation();
			dto.setRequestStatus(4);
			dto.setPid(super.getBean().getRefId());
			misapprProcessMapper.changeReqstViolation(dto);
			//将还款计划表的冻结状态改为有效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getProjectId());
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
		//流程开始
		}else if("task_BreachOfContractRequest".equals(super.getBean().getWorkflowTaskDefKey())){
			BizProjectViolation dto = new BizProjectViolation();
			// 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
			dto.setRequestStatus(2);
			dto.setPid(super.getBean().getRefId());
			//修改违约申请状态
			misapprProcessMapper.changeReqstViolation(dto);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),5);
			res = 2;
		//流程结束
		}else if("task_FinancialConfirm".equals(super.getBean().getWorkflowTaskDefKey())){
			BizProjectViolation dto = new BizProjectViolation();
			dto.setRequestStatus(4);
			dto.setPid(super.getBean().getRefId());
			misapprProcessMapper.changeReqstViolation(dto);
			// 激活对应的呆账坏账记录
			misapprProcessMapper.actionViolationBadBebStatus(dto.getPid());
			
			//将还款计划表的冻结状态改为有效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getProjectId());
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			misapprProcessService.addBlacklistRefuse(super.getBean().getProjectId(),super.getBean().getRefId());
			res = 3;
		}
		return res;
	}
	
	@Override
	public void resetProject(){
		BizProjectViolation dto = new BizProjectViolation();
		// 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
		dto.setRequestStatus(1);
		dto.setPid(super.getBean().getRefId());
		//修改违约申请状态
		misapprProcessMapper.changeReqstViolation(dto);
	}
}
