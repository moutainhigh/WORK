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

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.xlkfinance.bms.rpc.beforeloan.ProjectApprovalInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.repayment.RepayCgInterestDTO;
import com.xlkfinance.bms.rpc.repayment.RepayManageLoanService;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectApprovalInfoMapper;
import com.xlkfinance.bms.server.repayment.mapper.RepayManageLoanMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * @ClassName: InterestChangeRequestProcessWrokflow
 * @Description: 利率变更流程特殊处理类
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午5:51:35
 */
@Component("interestChangeRequestProcessWrokflow")
@Scope("prototype")
public class InterestChangeRequestProcessWrokflow extends
		WorkflowSpecialDispose {

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "repayManageLoanMapper")
	private RepayManageLoanMapper repayManageLoanMapper;
	
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "projectApprovalInfoMapper")
	private ProjectApprovalInfoMapper projectApprovalInfoMapper;
	
	@Resource(name = "repayManageLoanService")
	private RepayManageLoanService.Iface repayManageLoanServiceImpl;

	@SuppressWarnings("unchecked")
	@Override
	public int updateProjectStatus() throws ThriftServiceException, TException {
		int res = 2;
		if (null != super.getBean().getIsReject()) {
			if ("task_InterestChangeRequest".equals(super.getBean()
					.getIsReject())) {
				RepayCgInterestDTO dto = new RepayCgInterestDTO();
				dto.setInterestChgId(super.getBean().getRefId());
				// 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
				dto.setRequestStatus(1);
				repayManageLoanMapper.changeReqstCg(dto);
			}
			// 否决${flow=='refuse'}
		} else if (null != super.getBean().getIsVetoProject()
				&& !"".equals(super.getBean().getIsVetoProject())
				&& "refuse".equals(super.getBean().getIsVetoProject())) {
			//否定之前保存变更前的利率到历史表，方便查看显示变更前的数据
			repayManageLoanMapper.addLoanResInfo(super.getBean().getRefId());
			// 更新挪用表处理状态为已归档
			RepayCgInterestDTO dto = new RepayCgInterestDTO();
			dto.setInterestChgId(super.getBean().getRefId());
			dto.setRequestStatus(3); // 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
			repayManageLoanMapper.changeReqstCg(dto);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			// 流程开始
		} else if ("task_InterestChangeRequest".equals(super.getBean()
				.getWorkflowTaskDefKey())) {
			// 提交申请
			RepayCgInterestDTO dto = new RepayCgInterestDTO();
			dto.setInterestChgId(super.getBean().getRefId());
			// 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
			dto.setRequestStatus(2);
			repayManageLoanMapper.changeReqstCg(dto);
			
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),1);
			res = 2;
			// 流程结束
		} else if ("task_FinancialConfirm".equals(super.getBean()
				.getWorkflowTaskDefKey())) {
			//确认之前保存变更前的利率到历史表
			repayManageLoanMapper.addLoanResInfo(super.getBean().getRefId());
			// 更新挪用表处理状态为已归档
			RepayCgInterestDTO dto = new RepayCgInterestDTO();
			dto.setInterestChgId(super.getBean().getRefId());
			dto.setRequestStatus(4); // 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
			repayManageLoanMapper.changeReqstCg(dto);
			// 更新对账状态 
			loanMapper.updateIsReconciliationStatus(super.getBean().getProjectId());
			// 将还款计划表的冻结状态改为有效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getProjectId());
			repayManageLoanMapper.updateLoanbyintCg(dto.getInterestChgId());
			
			//通过项目id删除贷审会信息（把原来的贷审会信息删掉替换新表中的利率变更贷审会信息）
			projectApprovalInfoMapper.deleteProjectApprovalByProjectId(super.getBean().getProjectId());
			
			List<ProjectApprovalInfo>  list = null;
			//通过项目id把贷审会要求信息保存到新表中 利率变更与其它流程不同
			list = projectApprovalInfoMapper.getProjectApprovalAll(super.getBean().getRefId());
			if(list.size()!=0){
				for (ProjectApprovalInfo projectApprovalInfo : list) {
					projectApprovalInfo.setProjectId(super.getBean().getProjectId());
					projectApprovalInfoMapper.insert(projectApprovalInfo);
				}
			}  
			//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
			updateWorkflowStatus(super.getBean().getProjectId(),0);
			
			// 将利率变更是否可提前还款、是否退还利息、项目调查结论同步至项目表中
			dto.setProjectId(super.getBean().getProjectId());
			repayManageLoanServiceImpl.syncProcedureToProject(dto);
			res = 3;
		}
		return res;
	}
	@Override
	public void resetProject(){
		RepayCgInterestDTO dto = new RepayCgInterestDTO();
		dto.setInterestChgId(super.getBean().getRefId());
		// 申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
		dto.setRequestStatus(1);
		repayManageLoanMapper.changeReqstCg(dto);
	}
}
