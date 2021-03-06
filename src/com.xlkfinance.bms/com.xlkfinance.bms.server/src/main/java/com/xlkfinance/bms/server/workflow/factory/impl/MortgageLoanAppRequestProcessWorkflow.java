/**
 * @Title: MortgageLoanAppRequestProcessWorkflow.java
 * @Package com.xlkfinance.bms.server.workflow.factory.impl
 * @Description: 房抵贷贷款申请流程
 * Copyright: Copyright (c) 2014 
 * Company: 量度科技
 * 
 * @author: dulin
 * @date: 2017年12月18日 下午8:05:33
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.qfang.xk.aom.rpc.project.BizProject;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.MortgageDynamicConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizInterviewInfo;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.project.BizHisLoanInfo;
import com.xlkfinance.bms.rpc.project.CusHisCardInfo;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.server.aom.project.mapper.BizProjectMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizInterviewInfoMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.project.mapper.BizHisLoanInfoMapper;
import com.xlkfinance.bms.server.project.mapper.CusHisCardInfoMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
  * @ClassName: MortgageLoanAppRequestProcessWorkflow
  * @Description: 房抵贷贷款申请贷前审批流程
  * @author: dulin
  * @date: 2017年12月21日 下午8:05:52
 */
@Component("mortgageLoanAppRequestProcessWorkflow")
@Scope("prototype")
@SuppressWarnings("rawtypes")
public class MortgageLoanAppRequestProcessWorkflow extends WorkflowSpecialDispose {
	
private Logger logger = LoggerFactory.getLogger(BusinessApplyRequestProcessWrokflow.class);
	
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;
	
	@Resource(name = "sysUserMapper")
	private SysUserMapper sysUserMapper;
	
	@Resource(name = "financeHandleMapper")
	private FinanceHandleMapper financeHandleMapper;
	
	@Resource(name = "bizDynamicServiceImpl")
	private BizDynamicService.Iface bizDynamicService;

	@Resource(name = "bizProjectMapper")
	private BizProjectMapper bizProjectMapper;

	@Resource(name = "financeHandleServiceImpl")
	private FinanceHandleService.Iface financeHandleService;
	
	@Resource(name = "projectInfoServiceImpl")
	private ProjectInfoService.Iface projectInfoService;
	
	@Resource(name = "bizHisLoanInfoMapper")
	private BizHisLoanInfoMapper bizHisLoanInfoMapper;
	
	@Resource(name = "cusHisCardInfoMapper")
	private CusHisCardInfoMapper cusHisCardInfoMapper;
	
	@Resource(name = "bizInterviewInfoMapper")
	private BizInterviewInfoMapper bizInterviewInfoMapper;
	
	@Transactional
	@Override
	public int updateProjectStatus() throws TException {
		int res = 2;
		WorkflowSpecialBean bean = super.getBean();
		int projectId = bean.getProjectId();
		BizDynamic bizDynamic = new BizDynamic();
		bizDynamic.setProjectId(projectId);
		int handleAuthorId = bean.getHandleAuthorId();//操作人
		bizDynamic.setHandleAuthorId(handleAuthorId);
		bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
		bizDynamic.setRemark(bean.getMessage());
		bizDynamic.setModuelNumber(MortgageDynamicConstant.MODUEL_NUMBER_BEFORELOAN);

		// 驳回状态修改
		if (null != bean.getIsReject()) {
			// 驳回到房抵贷业务申请节点
			if ("task_MortgageLoanRequest".equals(bean.getIsReject())) {
				// 修改房抵贷业务审批状态为待提交
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_SUBMIT, DateUtils.getCurrentDateTime());
				// 机构端状态（设置为待审批）
				projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_2, DateUtils.getCurrentDateTime());
				// 驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			// 驳回到房产信息评估节点
			} else if ("task_AssessmentRequest".equals(bean.getIsReject())) {
				// 更新项目状态为待评估
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_ESTIMATE, DateUtils.getCurrentDateTime());
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			// 驳回到下户节点
			} else if ("task_InvestigationCheck".equals(bean.getIsReject())) {
				// 更新项目状态为待下户
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_SURVEY, DateUtils.getCurrentDateTime());
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_3);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			// 驳回到总部复审节点
			} else if ("task_RiskTwoCheck".equals(bean.getIsReject())) {
				// 更新项目状态为待复审
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_REAUDIT, DateUtils.getCurrentDateTime());
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_4);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}
			// 审批驳回时添加驳回记录
			recordDynamic(bizDynamic, bean.getWorkflowTaskDefKey());
		} else if (null != bean.getIsVetoProject() && !"".equals(bean.getIsVetoProject()) && "refuse".equals(bean.getIsVetoProject())) {
			// 拒单后设置项目状态为拒贷状态
			projectMapper.updateProjectRejected(projectId); 
			// 更改机构端状态为审核不通过,房抵贷申请项目状态保持当前流程节点状态
			res = projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_5, DateUtils.getCurrentDateTime());
			// 修改订单状态为撤单，关闭以及拒单
			BizProject bizProject = new BizProject();
			bizProject.setPid(projectId);	
			bizProject.setIsChechan(Constants.PROJECT_IS_CHECHAN);
			bizProject.setIsClosed(AomConstant.IS_CLOSED_1);
			bizProject.setIsReject(AomConstant.IS_REJECT_4);
			bizProjectMapper.updateProjectReject(bizProject);
			
			// 添加审批拒单的业务动态 
			bizDynamic.setModuelNumber(MortgageDynamicConstant.MODUEL_NUMBER_OTHER);
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_OTHER_10);
			bizDynamic.setDynamicName(MortgageDynamicConstant.DYNAMIC_NUMBER_OTHER_MAP.get(bizDynamic.getDynamicNumber()));
			bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
	 		bizDynamicService.addBizDynamic(bizDynamic);
	 		res = 3;
		} else if ("task_MortgageLoanRequest".equals(bean.getWorkflowTaskDefKey())) {
			// 流程开始,申请状态变为审核中
			projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_3, DateUtils.getCurrentDateTime());
			// 修改房抵贷项目状态待评估
			projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_ESTIMATE, DateUtils.getCurrentDateTime());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
			
			// 记录贷款申请信息历史-待提交节点
			recordLoanAppInfoHistory(projectId, handleAuthorId, Constants.MORTGAGE_LOAN_TO_SUBMIT);
			//添加业务动态
			addDynamic(bizDynamic);
			res = 2;
		} else if ("task_AssessmentRequest".equals(bean.getWorkflowTaskDefKey())) {
			// 更新项目状态为待下户
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_SURVEY, DateUtils.getCurrentDateTime());
			// 评估审批后-初始化财务收费数据
			FinanceHandleDTO financeQuery = new FinanceHandleDTO();
			financeQuery.setProjectId(projectId);
			List financeList = financeHandleMapper.findAllFinanceHandle(financeQuery);
			if (CollectionUtils.isEmpty(financeList)) {
				FinanceHandleDTO financeHandleDTO = new FinanceHandleDTO();
				financeHandleDTO.setProjectId(projectId);//
				financeHandleDTO.setStatus(Constants.REC_STATUS_NO_CHARGE);
				financeHandleDTO.setCreaterId(handleAuthorId);
				financeHandleService.addFinanceHandle(financeHandleDTO);
			}
			// 记录贷款申请信息历史-待评估节点
			recordLoanAppInfoHistory(projectId, handleAuthorId, Constants.MORTGAGE_LOAN_TO_ESTIMATE);
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
			//添加业务动态
			addDynamic(bizDynamic);
		} else if ("task_InvestigationCheck".equals(bean.getWorkflowTaskDefKey())) {
			// 更新项目状态为待总部复审
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_REAUDIT, DateUtils.getCurrentDateTime());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_3);
			// 记录客户银行信息历史
			recordCardHistory(projectId, handleAuthorId, Constants.MORTGAGE_LOAN_TO_SURVEY);
			// 记录贷款申请信息历史-待下户节点
			recordLoanAppInfoHistory(projectId, handleAuthorId, Constants.MORTGAGE_LOAN_TO_SURVEY);
			//添加业务动态
			addDynamic(bizDynamic);
		} else if ("task_RiskTwoCheck".equals(bean.getWorkflowTaskDefKey())) {
			// 更新项目状态为待总部终审
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_FINAL_AUDIT, DateUtils.getCurrentDateTime());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_4);
			// 记录贷款申请信息历史
			recordLoanAppInfoHistory(projectId, handleAuthorId, Constants.MORTGAGE_LOAN_TO_REAUDIT);
			//添加业务动态
			addDynamic(bizDynamic);
		} else if ("task_RiskOverCheck".equals(bean.getWorkflowTaskDefKey())) {
			// 更改项目状态为审批通过
			projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_4, DateUtils.getCurrentDateTime());
			// 流程结束, 赎楼项目状态设为已审批
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.MORTGAGE_LOAN_TO_MAKE_LOAN, DateUtils.getCurrentDateTime());
			bizDynamic.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_5);
			// 记录贷款申请信息历史
			recordLoanAppInfoHistory(projectId, handleAuthorId, Constants.MORTGAGE_LOAN_TO_FINAL_AUDIT);
			// 面签信息初始化
			BizInterviewInfo bizInterviewInfo = new BizInterviewInfo();
			bizInterviewInfo.setProjectId(projectId);
			bizInterviewInfo.setCreaterId(handleAuthorId);
			bizInterviewInfo.setCreateDate(DateUtils.getCurrentDateTime());
			bizInterviewInfoMapper.insert(bizInterviewInfo );
			//添加业务动态
			addDynamic(bizDynamic);
			res = 3;
		}
		return res;
	}
	
	/**
	 * 记录客户银行卡信息历史记录
	 * @author dulin
	 * @param projectId
	 * @param createId
	 * @param createNode
	 */
	private void recordCardHistory(int projectId, int createId, int createNode) {
		CusHisCardInfo cusHisCardInfo = new CusHisCardInfo();
		cusHisCardInfo.setProjectId(projectId);
		cusHisCardInfo.setCreaterId(createId);
		cusHisCardInfo.setCreateNode(createNode);
		cusHisCardInfoMapper.insert(cusHisCardInfo);
	}
	
	/**
	 * 记录贷款申请信息历史记录
	 * @author dulin
	 * @param projectId
	 * @param createId
	 * @param createNode
	 */
	private void recordLoanAppInfoHistory(int projectId, int createId, int createNode) {
		BizHisLoanInfo bizHisLoanInfo = new BizHisLoanInfo();
		bizHisLoanInfo.setProjectId(projectId);
		bizHisLoanInfo.setCreaterId(createId);
		bizHisLoanInfo.setCreateNode(createNode);
		bizHisLoanInfoMapper.insert(bizHisLoanInfo);
	}
	
	 
	 /**
	  * 记录审批驳回的记录
	  * @throws TException 
	  */
	 private void recordDynamic(BizDynamic bizDynamic,String workflowTaskDefKey) throws TException{
	 	BizDynamic bizDynamicInfo = bizDynamic;
	 	bizDynamicInfo.setModuelNumber(MortgageDynamicConstant.MODUEL_NUMBER_BEFORELOAN_OTHER);
	 	if(!StringUtil.isBlank(workflowTaskDefKey)){
	 		// 评估驳回
	 		if("task_AssessmentRequest".equals(workflowTaskDefKey)){
	 			bizDynamicInfo.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_10);
	 			bizDynamicInfo.setDynamicName("评估驳回");
	 		//总部复审驳回
	 		}else if("task_RiskTwoCheck".equals(workflowTaskDefKey)){
	 			bizDynamicInfo.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_20);
	 			bizDynamicInfo.setDynamicName("总部复审驳回");
	 		//总部终审驳回
	 		}else if("task_RiskOverCheck".equals(workflowTaskDefKey)){
	 			bizDynamicInfo.setDynamicNumber(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_30);
	 			bizDynamicInfo.setDynamicName("总部终审驳回");
	 		}
	 		bizDynamicInfo.setFinishDate(DateUtils.getCurrentDateTime());
	 		//添加业务动态
	 		bizDynamicService.addBizDynamic(bizDynamicInfo);
	 	}
	 }
	 
	 /**
	  * 添加业务动态
		* @param bean
		* @author: baogang
	  * @throws TException 
		* @date: 2016年5月16日 下午3:13:33
	  */
	 private void addDynamic(BizDynamic bizDynamic) throws TException{
	 	bizDynamic.setDynamicName(MortgageDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_MAP.get(bizDynamic.getDynamicNumber()));
	 	bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
	 	try {
	 		bizDynamicService.addOrUpdateBizDynamic(bizDynamic);
		} catch (Exception e) {
			logger.error("添加业务动态信息失败：" + ExceptionUtil.getExceptionMessage(e));
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	 }
	
	@Override
	public void resetProject(){
		//申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
		projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 1, "");
		//修改业务申请审批
		projectMapper.updateProjectForeStatusByPid(super.getBean().getProjectId(), 1, "");
	}
	
	/**
	 * 处理分支
	 *@author:liangyanjun
	 *@time:2016年4月21日上午10:01:57
	 *@param task
	 *@param fromVariables
	 */
	@Override
	public void handleBranch(Task task,Map<String, Object> taskVariables){
		// 评估节点
		if ("task_AssessmentRequest".equals(task.getTaskDefinitionKey())) {
			taskVariables.put("router", "refuse".equals(taskVariables.get("flow")) ? 0 : 1);
		}
		// 复审节点
		else if ("task_RiskTwoCheck".equals(task.getTaskDefinitionKey())) {
			taskVariables.put("router", "refuse".equals(taskVariables.get("flow")) ? 0 : 1);
		}
		// 终审节点
		else if ("task_RiskOverCheck".equals(task.getTaskDefinitionKey())) {
			taskVariables.put("router", "refuse".equals(taskVariables.get("flow")) ? 0 : 1);
		}
		// 更新下一级节点审批用户到项目的待办人中
		WorkflowSpecialBean bean = super.getBean();
		try {
			Object candidateUsers = taskVariables.get("candidateUsers");
			projectInfoService.setNextUserInfo(candidateUsers == null ? null : String.valueOf(candidateUsers), bean.getProjectId());
		}
		catch (Exception e) {
			logger.error("更新下一级节点审批用户到项目的待办人中：" + ExceptionUtil.getExceptionMessage(e));
		}
	}
}
