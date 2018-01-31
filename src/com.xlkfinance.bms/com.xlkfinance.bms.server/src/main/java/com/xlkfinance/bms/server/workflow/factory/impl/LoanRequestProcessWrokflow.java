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
import com.xlkfinance.bms.server.beforeloan.mapper.CreditMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceTransactionMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * @ClassName: IoanRequestProcessWrokflow
 * @Description: 贷前申请工作流特殊处理类
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午5:49:49
 */
@SuppressWarnings("unchecked")
@Component("loanRequestProcessWrokflow")
@Scope("prototype")
public class LoanRequestProcessWrokflow extends WorkflowSpecialDispose {

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "creditMapper")
	private CreditMapper creditMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "financeTransactionMapper")
	private FinanceTransactionMapper financeTransactionMapper;

	@Override
	public int updateProjectStatus() {
		int res = 2;
		if (null != super.getBean().getIsReject()) {
			if ("task_LoanRequest".equals(super.getBean().getIsReject())) {
				projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 1, "");
			}
		} else if (null != super.getBean().getIsVetoProject() && !"".equals(super.getBean().getIsVetoProject()) && "refuse".equals(super.getBean().getIsVetoProject())) {
			// 否决${flow=='refuse'}
			cusAcctMapper.updateCusStatusByProSettle(super.getBean().getProjectId());
			projectMapper.updateProjectRejected(super.getBean().getProjectId());
			// 根据项目ID修改项目授信状态为失效,如果是贷款项目就不做修改
			creditMapper.updateCreditStatusByProjectId(super.getBean().getProjectId());
			
			// 项目否决,删除对应的财务放款记录
			financeTransactionMapper.deleteFinanceRecords(super.getBean().getProjectId());

			// 更改项目状态为已归档
			res = projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 4, DateUtil.format(new Date()));
		} else if ("task_LoanRequest".equals(super.getBean().getWorkflowTaskDefKey())) {
			// 流程开始
			projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 2, "");

			res = 2;
		} else if ("task_FinancialLoans".equals(super.getBean().getWorkflowTaskDefKey())) {
			// 1.1 财务审批 更新项目状态为已放款
			res = projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 3, "");
		} else if ("task_ProjectArchive".equals(super.getBean().getWorkflowTaskDefKey())) {
			// 流程结束

			// 归档 更改贷款信息状态
			loanMapper.updateLoanStatus(super.getBean().getProjectId(), 1);
			// 更新对账状态 
			loanMapper.updateIsReconciliationStatus(super.getBean().getProjectId());
			// 更改项目状态为已归档
			res = projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 4, DateUtil.format(new Date()));
			// 更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getProjectId());
			// 获取项目ID
			int projectId = super.getBean().getProjectId();
			// 生成额度使用记录数据
			addCreditLimitRecord(projectId);

			res = 3;
		}
		return res;
	}

	/**
	 * 
	 * @Description: 生成额度使用记录数据
	 * @param projectId
	 *            项目ID
	 * @author: Cai.Qing
	 * @date: 2015年5月2日 下午4:59:47
	 */
	public void addCreditLimitRecord(int projectId) {
		// 根据项目ID查询项目详细信息
		Project project = new Project();
		// 根据项目ID查询项目列表
		List<Project> list = projectMapper.getLoanProjectByProjectId(projectId);
		// 判断是否存在数据
		if (list.size() > 0) {
			project = list.get(0);
		}
		// 新增额度授信记录
		// 创建额度使用记录对象
		CreditLimitRecord clr = new CreditLimitRecord();
		// 赋值
		clr.setCreditId(project.getCreditId());// 授信ID
		clr.setCreditUseType(1);// 设置额度调整类型 1=授信、2=使用、3=冻结、4=解冻 、5=还款
		// 根据状态来判断是否存在授信金额
		if (project.getProjectType() == 2) {
			// 如果只是贷款申请
			clr.setRequestAmt(project.getLoanAmt());// 贷款项目,授信金额就是贷款金额
			clr.setAmt(project.getLoanAmt());// 贷款项目,授信金额就是贷款金额
			clr.setReason("贷款项目,授信金额就是贷款金额");// 事由
		} else if (project.getProjectType() == 1 || project.getProjectType() == 5) {
			// 如果是授信或者是授信的同时贷款
			clr.setRequestAmt(project.getCreditAmt());// 授信或是授信的同时贷款的项目,授信金额就是用户录入的金额
			clr.setAmt(project.getCreditAmt());// 授信或是授信的同时贷款的项目,授信金额就是用户录入的金额
			clr.setReason("授信或是授信的同时贷款的项目,授信金额就是用户录入的金额");// 事由
		}
		clr.setCreDttm(DateUtil.format(new Date()));// 设置为当前时间
		clr.setStatus(1);// 状态设置为1，正常
		// 新增额度使用记录数据
		creditsDTOMapper.insert(clr);

		// 新增额度提取记录
		// 创建额度使用记录对象
		CreditLimitRecord clrd = new CreditLimitRecord();
		// 赋值
		clrd.setCreditId(project.getCreditId());// 授信ID
		clrd.setCreditUseType(2);// 设置额度调整类型 1=授信、2=使用、3=冻结、4=解冻
		// 根据状态来判断是否存在授信金额
		if (project.getProjectType() == 2 || project.getProjectType() == 5) {
			// 如果只是贷款申请或者是授信的同时贷款
			clrd.setRequestAmt(project.getLoanAmt());// 贷款或是授信的同时贷款的项目,额度金额就是用户录入的额度金额
			clrd.setAmt(project.getLoanAmt());// 贷款或是授信的同时贷款的项目,额度金额就是用户录入的额度金额
			clrd.setReason("贷款或是授信的同时贷款的项目,额度金额就是用户录入的额度金额");// 事由
		} else if (project.getProjectType() == 1) {
			// 如果是授信
			clrd.setRequestAmt(0);// 授信项目,贷款金额就为0
			clrd.setAmt(0);// 授信项目,贷款金额就为0
			clrd.setReason("授信项目,额度使用金额就为0");// 事由
		}
		clrd.setCreDttm(DateUtil.format(new Date()));// 设置为当前时间
		clrd.setStatus(1);// 状态设置为1，正常
		// 新增额度使用记录数据
		creditsDTOMapper.insert(clrd);
	}
	@Override
	public void resetProject(){
		//申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
		projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 1, "");
	}
}
