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

import org.apache.thrift.TException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.repayment.LoanExtensionService;
import com.xlkfinance.bms.rpc.repayment.ProjectExtensionView;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentService;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

/**
 * @ClassName: ExtensionRequestProcessWrokflow
 * @Description: 展期申请工作流特殊处理类
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午5:52:36
 */
@Component("extensionRequestProcessWrokflow")
@Scope("prototype")
public class ExtensionRequestProcessWrokflow extends WorkflowSpecialDispose {

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "workflowProjectMapper")
	private WorkflowProjectMapper workflowProjectMapper;
	
	@Resource(name = "loanExtensionService")
	private LoanExtensionService.Iface loanExtensionService;
	
	@Resource(name = "repaymentServicerImpl")
	private RepaymentService.Iface repaymentService;
	
	@Override
	public int updateProjectStatus() throws ThriftServiceException, TException {
		int res = 2;
		try {
			if (null != super.getBean().getIsReject()) {
				if ("task_ExtensionRequest".equals(super.getBean().getIsReject())) {
					projectMapper.updateProjectStatusByPrimaryKey(super.getBean()
							.getRefId(), 1, "");
				}
				// 否决${flow=='refuse'}
			} else if (null != super.getBean().getIsVetoProject()
					&& !"".equals(super.getBean().getIsVetoProject())
					&& "refuse".equals(super.getBean().getIsVetoProject())) {
				
				projectMapper.updateProjectStatusByPrimaryKey(super.getBean()
						.getRefId(), 4, DateUtil.format(new Date()));
				projectMapper.updateProjectRejected(super.getBean().getRefId());
				
				//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
				updateWorkflowStatus(super.getBean().getProjectId(),0);
				// 流程开始
			} else if ("task_ExtensionRequest".equals(super.getBean().getWorkflowTaskDefKey())) {
				// 提交申请,更新项目状态为审核中
				projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 2, "");
				
				//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
				updateWorkflowStatus(super.getBean().getProjectId(),4);
				res = 2;
				// 流程结束
			} else if ("task_ProjectArchive".equals(super.getBean().getWorkflowTaskDefKey())) {
				// 归档 ,更新项目为已结束
				loanMapper.updateLoanStatus(super.getBean().getRefId(), 1);
				// 归档 ,更新项目为已结束
				loanMapper.updateLoanStatus(super.getBean().getProjectId(), 1);
				
				projectMapper.updateProjectStatusByPrimaryKey(super.getBean()
						.getRefId(), 4, DateUtil.format(new Date()));
				
				// 更新对账状态 
				loanMapper.updateIsReconciliationStatus(super.getBean().getRefId());
				
				// 1.2 更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
				loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getRefId());
				// 1.2 更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
				loanRepaymentPlanMapper.updateRepayFreezeStatus(super.getBean().getProjectId());
				
				
				// 查询原收息表，判断被展期数 应收金额 - 已收金额是否为0 为0 则将对账状态改为已对账
				updateOldProjectFreezeStatus();
				
				//是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
				updateWorkflowStatus(super.getBean().getProjectId(),0);
				
				// 展期结束日期大于授信展期结束日期,展期审核归档流程提交时，把授信项目授信结束日期更改成当前展期项目的展期结束日期
				loanExtensionService.updateCreditEndDate(super.getBean().getRefId());
				
				
				res = 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return res;
	}
	
	/**
	 * 
	  * @Description: 查询原收息表，判断被展期数 应收金额 - 已收金额是否为0 为0 则将对账状态改为已对账
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: Zhangyu.Hoo
	  * @date: 2015年8月31日 下午2:16:40
	 */
	private void updateOldProjectFreezeStatus() throws ThriftServiceException,
			TException {
		Integer loanId = loanMapper.getLoanIdByProjectId(super.getBean().getProjectId());
		RepaymentPlanBaseDTO baseDto = new RepaymentPlanBaseDTO();
		baseDto.setLoanInfoId(loanId);
		baseDto.setFreezeStatus(0);
		List<RepaymentPlanBaseDTO> list = repaymentService.selectRepaymentPlanBaseDTO(baseDto);
		ProjectExtensionView view = loanExtensionService.getByProjectId(super.getBean().getRefId());
		for(RepaymentPlanBaseDTO dto : list){
			if(dto.getIsExtension() == 1 && dto.getPlanCycleNum() == view.getSelectCycleNum()){
				if( (dto.getAccountsTotal()- dto.getReceivedTotal()) <= 0){
					loanRepaymentPlanMapper.updateRepayFreezeStatusByPid(dto);
					break;
				}
			}
		}
	}
	@Override
	public void resetProject(){
		// 提交申请,更新项目状态为审核中
		projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 1, "");
	}
}
