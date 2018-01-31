/**
 * @Title: WorkFlowHelper.java
 * @Package com.xlkfinance.bms.server.workflow
 * @Description: 工作流处理助手
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年3月4日 下午2:41:39
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import javax.annotation.Resource;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmActivity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;
import com.xlkfinance.bms.rpc.workflow.EndTaskPageVO;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectSurveyReportMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * @ClassName: WorkFlowHelper
 * @Description: 工作流处理助手
 * @author: Simon.Hoo
 * @date: 2014年10月4日 下午2:42:00
 */
@Service("workFlowHelper")
public class WorkFlowHelper {
	private Logger logger = LoggerFactory.getLogger(WorkFlowHelper.class);

	@Resource(name = "repositoryService")
	private RepositoryService repositoryService;

	@Resource(name = "managementService")
	private ManagementService managementService;

	@Resource(name = "identityService")
	private IdentityService identityService;

	@Resource(name = "runtimeService")
	private RuntimeService runtimeService;

	@Resource(name = "taskService")
	protected TaskService taskService;

	@Resource(name = "historyService")
	private HistoryService historyService;

	@Resource(name = "formService")
	private FormService formService;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "productMapper")
	private ProductMapper productMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectSurveyReportMapper")
	private ProjectSurveyReportMapper projectSurveyReportMapper;
	
    @Resource(name = "projectServiceImpl")
    private ProjectService.Iface projectService;
	/**
	 * @Description: 发布流程
	 * @param zipFile
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月4日 下午2:45:05
	 */
	public int deploy(String zipFile) {
		try {
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(new File(zipFile)));
			String deploymentId = repositoryService.createDeployment().addZipInputStream(zipInputStream).deploy().getId();
			logger.info(zipFile + " was deployed. deploy id :" + deploymentId);
			return 1;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * @Description: 启动工作流
	 * @param userName
	 * @param processDefinitionKey
	 * @param taskVariables
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午3:10:13
	 */
	public ProcessInstance start(String userName, String processDefinitionKey, Map<String, Object> taskVariables) throws TException {
		try {
			identityService.setAuthenticatedUserId(userName);
			return runtimeService.startProcessInstanceByKey(processDefinitionKey, taskVariables);
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		} finally {
			identityService.setAuthenticatedUserId(null);
		} 
	}

	public int complete(String userName, String taskId, String candidateUsers, String message, Map<String, String> fromVariables,int projectId,WorkflowSpecialDispose workflowSpecialDispose) throws TException {
		try {
			Task task = findCurrentTaskById(taskId);
			if (task != null) {
				// 格式化任务参数
				Map<String, Object> taskVariables = new HashMap<String, Object>();
				logger.info("------------------userName:"+userName+"-----taskIdL:"+taskId+"---------candidateUsers:"+candidateUsers);
				taskVariables.put("owner", userName);
				taskVariables.put("candidateUsers", candidateUsers);
				if (null != fromVariables) {
					taskVariables.put("flow", fromVariables.get("flow"));
					// 贷款申请 合同制作与签署审批后特殊处理
					if("loanRequestProcess".equalsIgnoreCase(task.getProcessDefinitionId().split(":")[0])){
						String str = fromVariables.get("flow");
						if(null != str){
							String[] strs = str.split(",");
							if(null != strs){
								// 无抵押 
								if("exOnlyCriditOrLoan".equalsIgnoreCase(strs[0])){
									taskVariables.put("tempFlow", strs[1]);
									taskVariables.put("flow", strs[0]);
								}
								
								// 办理抵押登记审批后处理
								if("LOAN_SPECIALIST".equalsIgnoreCase(fromVariables.get("flow")) || 
										"FINANCIAL_OFFICER".equalsIgnoreCase(fromVariables.get("flow"))){
									taskVariables.put("tempFlow", fromVariables.get("flow"));
								}
							}
						}
					}
					/*if("creditLoanRequestProcess".equalsIgnoreCase(task.getProcessDefinitionId().split(":")[0])){
						String str = fromVariables.get("flow");
						if(null != str){
							String[] strs = str.split(",");
							if("GENERAL_MANAGER".equalsIgnoreCase(strs[0])){
								taskVariables.put("money", findProjectLoanMoney(projectId));
							}
						}
					}*/
					
					/*	if("foreLoanRequestProcess".equalsIgnoreCase(task.getProcessDefinitionId().split(":")[0])){
						String str = fromVariables.get("flow");
						if(null != str){
							String[] strs = str.split(",");
							if("next".equalsIgnoreCase(strs[0])||"INSPECTOR".equalsIgnoreCase(strs[0]) ||"REVIEW_DEPARTMENT_SUPERVISOR".equalsIgnoreCase(strs[0]) 
									||"RISK_DIRECTOR".equalsIgnoreCase(strs[0])|| "BIZ_DIRECTOR".equalsIgnoreCase(strs[0]) 
									|| "GENERAL_MANAGER".equalsIgnoreCase(strs[0])|| "DEPARTMENT_MANAGER".equalsIgnoreCase(strs[0])){
								taskVariables.put("money", findProjectLoanMoney(projectId));
								taskVariables.put("special", checkSpecialDesc(projectId));
							}
						}
					}*/
					
					/*if("creditExtensionRequestProcess".equalsIgnoreCase(task.getProcessDefinitionId().split(":")[0])){
						String str = fromVariables.get("flow");
						if(null != str){
							String[] strs = str.split(",");
							if("GENERAL_MANAGER".equalsIgnoreCase(strs[0])){
								taskVariables.put("money", findProjectLoanMoney(projectId));
							}
						}
					}*/
					
					/*if("foreExtensionRequestProcess".equalsIgnoreCase(task.getProcessDefinitionId().split(":")[0])){
						String str = fromVariables.get("flow");
						if(null != str){
							String[] strs = str.split(",");
							if("GENERAL_MANAGER".equalsIgnoreCase(strs[0])||"TASK_GENERAL_MANAGER".equalsIgnoreCase(strs[0])|| "pass".equalsIgnoreCase(strs[0])){
								taskVariables.put("exRateVal", checkFeeRate(projectId));
							}
						}
					}*/
					if(workflowSpecialDispose != null){
						//处理排他网关(处理分支)
						workflowSpecialDispose.handleBranch(task,taskVariables);
					}
				}
				// 簽收任務：
				taskService.claim(taskId, userName);

				// 录入审批意见：
				if (null != message && message.trim().length() > 0) {
					taskService.addComment(taskId, task.getProcessInstanceId(), message);
				}
				
				// 设置完成时间
				taskService.setDueDate(taskId, new Date());
				// 完成任务
				taskService.complete(task.getId(), taskVariables);
			}
			return 1;
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	public Task findCurrentTaskById(String taskId) throws TException {
		try {
			return taskService.createTaskQuery().taskId(taskId).singleResult();
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * 判断展期费率是否大于贷前费率
	  * @param projectId
	  * @return
	  * @author: baogang
	 * @throws TException 
	  * @date: 2016年6月6日 下午2:10:07
	 */
	public String checkFeeRate(int projectId) throws TException{
		// 根据项目ID查询项目列表
		Project project;
		try {
			project = projectService.getLoanProjectByProjectId(projectId);
			double feeRate = project.getProjectGuarantee().getFeeRate();//贷前费率
			double extensionRate = project.getProjectForeclosure().getExtensionRate();//展期费率
			if(extensionRate >= feeRate ){
				return "gt";
			}
		} catch (TException e) {
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		
		return "less";
	}
	
	/**
	 * @Description: 根据流程实例流程当前任务
	 * @param processInstanceId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午3:07:31
	 */
	public Task findCurrentTaskByInstanceId(String processInstanceId) throws TException {
		try {
			return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 根据用户查询任务
	 * @param candidateUser 当前用户名称
	 * @return List<Task> 任务列表
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午2:48:39                                                           
	 */
	public List<Task> findTaskByCandidateUser(String candidateUser,int page,int rows) throws TException {
		try {
			if(rows > 0){
				return taskService.createTaskQuery().taskCandidateUser(candidateUser).orderByTaskCreateTime().desc().listPage(((page-1)*rows), rows);
			}else{
				return taskService.createTaskQuery().taskCandidateUser(candidateUser).orderByTaskCreateTime().desc().list();
			}
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	  * @Description: 根据流程实例ID查询任务
	  * @param processInstanceId
	  * @return Task
	  * @author: JingYu.Dai
	  * @date: 2015年6月2日 上午11:40:36
	 */
	public Task getTaskByProcessInstanceId(String processInstanceId){
		return taskService.createTaskQuery().processInstanceId(processInstanceId).taskUnassigned().list().get(0);
	}
	/**
	  * @Description:  根据用户查询任务总记录条数
	  * @param candidateUser 用户名称
	  * @return int 
	  * @author: JingYu.Dai
	  * @date: 2015年5月20日 下午10:13:35
	 */
	public int findTaskByCandidateUserTotal(String candidateUser){
		return taskService.createTaskQuery().taskCandidateUser(candidateUser).list().size();
	}
	/**
	 * @Description: 根据流程实例ID查实例历史
	 * @param processInstanceId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午2:51:31
	 */
	public HistoricProcessInstance findHistoricProcessInstanceById(String processInstanceId) throws TException {
		try {
			return historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 根据流程实例ID查实例任务历史
	 * @param processInstanceId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午3:00:05
	 */
	public List<HistoricTaskInstance> findHistoricTaskInstanceById(String processInstanceId,int page,int rows) throws TException {
		try {
			if(rows > 0){
				return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().desc().listPage(((page-1)*rows), rows);
			}else{
				return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).orderByTaskCreateTime().desc().list();
			}
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}
	/**
	  * @Description:  根据流程实例ID查实例任务历史 总记录条数
	  * @param processInstanceId
	  * @return
	  * @author: JingYu.Dai
	  * @date: 2015年5月2日 下午11:58:04
	 */
	public int findHistoricTaskInstanceByIdCount(String processInstanceId){
		return historyService.createHistoricTaskInstanceQuery().processInstanceId(processInstanceId).list().size();
	}
	/**
	 * @Description: 根据用户任务完成历史
	 * @param userName
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午3:03:43
	 */
	public List<HistoricTaskInstance> findHistoricTaskInstanceByUser(EndTaskPageVO endTaskPageVO) throws TException {
		try {
			return  historyService.createHistoricTaskInstanceQuery().finished().taskAssignee(endTaskPageVO.getUserName()).list();
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}
	
	/**
	 * 
	  * @Description: TODO
	  * @param endTaskPageVO
	  * @return
	  * @author: Zhangyu.Hoo
	 * @throws TException 
	  * @date: 2015年4月21日 下午10:49:55
	 */
	public int queryWorkFlowFinishedHistoryTotal(EndTaskPageVO endTaskPageVO) throws TException {
		try {
			List<HistoricTaskInstance> list =  historyService.createHistoricTaskInstanceQuery().finished().taskAssignee(endTaskPageVO.getUserName()).list();
			if(null!=list && list.size() > 0 ){
				return list.size();
			}
			return 0;
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 根据流程实例ID，查询流程定义
	 * @param processInstanceId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午2:53:45
	 */
	public ProcessDefinition findProcessDefinitionByInstanceId(String processInstanceId) throws TException {
		try {
			HistoricProcessInstance instance = findHistoricProcessInstanceById(processInstanceId);
			return repositoryService.createProcessDefinitionQuery().processDefinitionKey(formatProcessDefinitionId(instance.getProcessDefinitionId())).latestVersion().list().get(0);
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 根据流程定义ID，获取流程定义模型
	 * @param processDefinitionId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午5:59:50
	 */
	public ProcessDefinitionEntity findProcessDefinitionByDefinitionId(String processDefinitionId) throws TException {
		try {
			return (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: TODO
	 * @param instanceId
	 * @param taskDefId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午6:05:07
	 */
	public List<HistoricTaskInstance> findFinishedTaskByInstanceAndTaskKey(String instanceId, String taskDefId) throws TException {
		try {
			return historyService.createHistoricTaskInstanceQuery().processInstanceId(instanceId).taskDefinitionKey(taskDefId).orderByDueDateNullsLast().desc().list();
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 根据任务ID获取FORM属性
	 * @param taskId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午6:20:36
	 */
	public List<FormProperty> findTaskFormPropertyListByTaskId(String taskId) throws TException {
		try {
			return formService.getTaskFormData(taskId).getFormProperties();
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 根据流程实例ID，获取所有userTask类型的任务节点实例
	 * @param processInstanceId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午6:24:18
	 */
	public List<HistoricActivityInstance> findHistoricActivityInstanceByInstanceId(String processInstanceId) throws TException {
		try {
			return historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").finished().orderByHistoricActivityInstanceEndTime().asc().list();
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	  * @Description: 根据实例ID查询出最后一条历史记录
	  * @param processInstanceId
	  * @return HistoricActivityInstance
	  * @author: JingYu.Dai
	 * @throws TException 
	  * @date: 2015年4月2日 下午5:14:44
	  */
	public HistoricActivityInstance findLastActivityInstanceByInstanceId(String processInstanceId) throws TException {
		try {
			HistoricActivityInstance h = historyService.createHistoricActivityInstanceQuery().processInstanceId(processInstanceId).activityType("userTask").finished().orderByHistoricActivityInstanceEndTime().desc().list().get(0);
			return h;
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}
	/**
	 * @Description: 根据任务ID查询任务备注。
	 * @param taskId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年3月4日 下午6:32:09
	 */
	public List<Comment> findCommnetByTaskId(String taskId) throws TException {
		try {
			return taskService.getTaskComments(taskId);
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 格式化流定定义ID，去掉后面的序号
	 * @param sourceDefinitionId
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月4日 下午7:39:10
	 */
	public String formatProcessDefinitionId(String sourceDefinitionId) {
		return sourceDefinitionId.split(":")[0];
	}
	
	/**
	  * @Description: 删除流程实例
	  * @param processInstanceId 流程实例ID
	  * @author: JingYu.Dai
	  * @date: 2015年5月21日 上午12:43:55
	 */
	public void deleteProcessInstance(String processInstanceId){
		runtimeService.deleteProcessInstance(processInstanceId, "");
	}
	
	/**
	  * @Description: 查询所有任务
	  * @return List<Task> 返回任务列表
	  * @author: JingYu.Dai
	  * @date: 2015年6月16日 下午2:32:16
	 */
	public List<Task> findAllRunTask(int page , int rows){
		return taskService.createTaskQuery().listPage(((page-1)*rows), rows);
	}
	
	/**
	  * @Description: 查询所有任务总数
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年7月8日 上午6:09:42
	 */
	public int findAllRunTaskTotal(){
		return taskService.createTaskQuery().list().size();
	}

	/**
	 * @Description: 根据流程定义获取下一节点
	 * @param activitilist
	 * @return String
	 * @author: JingYu.Dai
	 * @date: 2015年7月8日 上午6:09:32
	 */
	public String getNextTaskByActivityImpls(List<ActivityImpl> activitiList,
			String excId,String workflowTaskDefKey) {
		for (ActivityImpl activityImpl : activitiList) {
			String id = activityImpl.getId();
			if (workflowTaskDefKey.equals(id)) {
				List<PvmTransition> outTransitions = activityImpl
						.getOutgoingTransitions();// 获取从某个节点出来的所有线路
				for (PvmTransition tr : outTransitions) {
					PvmActivity ac = tr.getDestination(); // 获取线路的终点节点
					return ac.getId();
				}
				break;
			}
		}
		return "";
	}
	
	/**
	 * 获取项目贷款金额
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年2月23日 下午2:45:43
	 */
	public double findProjectLoanMoney(int projectId){
		double loanMoney=0.0;
		ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
		if(projectGuarantee != null){
			loanMoney = projectGuarantee.getLoanMoney();
		}
		BigDecimal decimal = NumberUtils.parseDecimal(String.valueOf(loanMoney));

		return decimal.doubleValue();
	}
	
	/**
	 * 判断特殊情况说明是否已填写
	  * @param projectId
	  * @return
	  * @author: Administrator
	 * @throws TException 
	  * @date: 2016年4月2日 下午4:16:58
	 */
	public String checkSpecialDesc(int projectId) throws TException{
		ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
		String special="NoSpecial";
		try {
			List<ProjectSurveyReport> list = projectSurveyReportMapper.getSurveyReportByProjectId(projectId);
			// 判断是否成功
			if (list.size() > 0) {
				projectSurveyReport = list.get(0);
			}
			if(projectSurveyReport != null){
				String specialDesc = projectSurveyReport.getSpecialDesc();
				//无特殊情况
				if(StringUtil.isBlank(specialDesc)){
					special = "NoSpecial";
				}else{
					special = "Special";
				}
			}else{
				special = "NoSpecial";
			}
		} catch (Exception e) {
			 e.printStackTrace();
	         throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return special;
	}
}
