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

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.beforeloan.CreditLimitRecord;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceTransactionMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * @ClassName: CreditWithdrawalRequestProcessWrokflow
 * @Description: 额度提款申请工作流特殊处理类
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午5:53:12
 */
@SuppressWarnings("unchecked")
@Component("creditWithdrawalRequestProcessWrokflow")
@Scope("prototype")
public class CreditWithdrawalRequestProcessWrokflow extends WorkflowSpecialDispose {

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "financeTransactionMapper")
	private FinanceTransactionMapper financeTransactionMapper;

	@Override
	public int updateProjectStatus() {
		int res = 2;
		// 驳回
		if (null != super.getBean().getIsReject()) {
			if ("task_CreditWithdrawalRequest".equals(super.getBean().getIsReject())) {
				// 更改贷款信息状态
				loanMapper.updateLoanStatus(super.getBean().getRefId(), 1);
				projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 1, "");
			}
			// 否决${flow=='refuse'}
		} else if (null != super.getBean().getIsVetoProject() && !"".equals(super.getBean().getIsVetoProject()) && "refuse".equals(super.getBean().getIsVetoProject())) {
			// 更改项目状态为已归档
			projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 4, DateUtil.format(new Date()));
			projectMapper.updateProjectRejected(super.getBean().getRefId());
			
			// 项目否决,删除对应的财务放款记录
			financeTransactionMapper.deleteFinanceRecords(super.getBean().getRefId());
			
			// 流程开始
		} else if ("task_CreditWithdrawalRequest".equals(super.getBean().getWorkflowTaskDefKey())) {
			// 更改贷款信息状态
			loanMapper.updateLoanStatus(super.getBean().getRefId(), 2);
			projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 2, "");
			res = 2;
			// 财务放款
		} else if ("task_FinancialLoans".equals(super.getBean().getWorkflowTaskDefKey())) {
			// 更改贷款信息状态
			loanMapper.updateLoanStatus(super.getBean().getRefId(), 3);
			projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 3, "");
			// 流程结束
		} else if ("task_ProjectArchive".equals(super.getBean().getWorkflowTaskDefKey())) {
			// 获取新老项目ID
			int projectId = super.getBean().getProjectId();
			int newProjectId = super.getBean().getRefId();
			// 更改贷款信息状态
			loanMapper.updateLoanStatus(newProjectId, 1);
			// 更新对账状态 
			loanMapper.updateIsReconciliationStatus(newProjectId);
			// 将还款计划表的冻结状态改为有效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(newProjectId);
			// 更改项目状态为已归档
			projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 4, DateUtil.format(new Date()));
			// 创建额度使用记录对象
			CreditLimitRecord clr = new CreditLimitRecord();
			// 根据项目ID查询项目详细信息
			Project project = new Project();
			// 根据项目ID查询项目列表
			List<Project> list = projectMapper.getLoanProjectByProjectId(projectId);
			// 判断是否存在数据
			if (list.size() > 0) {
				project = list.get(0);
				// 获取授信ID
				int creditId = project.getCreditId();
				clr.setCreditId(creditId);// 授信ID
			}
			// 获取新项目输入的贷款金额
			List<Project> newList = projectMapper.getLoanProjectByProjectId(newProjectId);
			// 新项目对象
			Project newProject = new Project();
			if (newList.size() > 0) {
				newProject = newList.get(0);
				// 赋值
				clr.setCreditUseType(2);// 设置额度调整类型 1=授信、2=使用、3=冻结、4=解冻 、5=还款
				clr.setRequestAmt(newProject.getLoanAmt());// 额度提取,贷款金额就是提取金额
				clr.setAmt(newProject.getLoanAmt());// 额度提取,贷款金额就是提取金额
				clr.setReason("额度提取,贷款金额就是提取金额");// 事由
				clr.setCreDttm(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));// 设置为当前时间
				clr.setStatus(1);// 状态设置为1，正常
				// 新增额度使用记录数据
				creditsDTOMapper.insert(clr);
			}
		}
		return res;
	}
	
	@Override
	public void resetProject(){
		loanMapper.updateLoanStatus(super.getBean().getRefId(), 1);
	}
}
