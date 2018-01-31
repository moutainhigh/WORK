/**
 * @Title: WorkflowServiceImpl.java
 * @Package com.xlkfinance.bms.server.workflow.service
 * @Description: 工作流业务处理
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:45:40
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.exception.WorkFlowExistException;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectRecord;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizHandleWorkflowDTO;
import com.xlkfinance.bms.rpc.inloan.BizLoanBatchRefundFeeMain;
import com.xlkfinance.bms.rpc.system.SysConfig;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.task.TaskSettingVo;
import com.xlkfinance.bms.rpc.workflow.BizTaskAcctProHisVo;
import com.xlkfinance.bms.rpc.workflow.EndTaskPageVO;
import com.xlkfinance.bms.rpc.workflow.TaskHistoryDto;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService.Iface;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperatCompanyApplyInfMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizLoanBatchRefundFeeMainMapper;
import com.xlkfinance.bms.server.system.mapper.SysConfigMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;
import com.xlkfinance.bms.server.system.mapper.SysOrgInfoMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
import com.xlkfinance.bms.server.system.mapper.SystemUserRoleMapper;
import com.xlkfinance.bms.server.task.mapper.TaskMapper;
import com.xlkfinance.bms.server.workflow.WorkFlowHelper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDisposeFactory;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

@SuppressWarnings("unchecked")
@Service("workflowServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.workflow.WorkflowService")
public class WorkflowServiceImpl extends WorkFlowHelper implements Iface {
	private Logger logger = LoggerFactory.getLogger(WorkflowServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysUserMapper")
	private SysUserMapper sysUserMapper;

	@Resource(name = "sysOrgInfoMapper")
	private SysOrgInfoMapper sysOrgInfoMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "taskMapper")
	private TaskMapper taskMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "workflowProjectMapper")
	private WorkflowProjectMapper workflowProjectMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@Resource(name = "workflowSpecialDisposeFactory")
	private WorkflowSpecialDisposeFactory workflowSpecialDisposeFactory;

	@SuppressWarnings("rawtypes")
	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysConfigMapper")
	private SysConfigMapper sysConfigMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "systemUserRoleMapper")
	private SystemUserRoleMapper systemUserRoleMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "bizHandleMapper")
	private BizHandleMapper bizHandleMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource
	private OrgCooperatCompanyApplyInfMapper orgCooperatMapper;
	
   @Resource(name = "bizLoanBatchRefundFeeMainMapper")
   private BizLoanBatchRefundFeeMainMapper batchRefundFeeMainMapper;
	/**
	 * @Description: 發布工作流模板
	 * @param zipFile
	 * @author: Simon.Hoo
	 * @date: 2015年1月30日 下午3:28:24
	 */
	@Override
	public int executeDeploy(String zipFile) {
		return super.deploy(zipFile);
	}

	/**
	 * 启动工作流(该方法通用) 入参说明： Map<String, String> paramMap=new HashMap<String,
	 * String>(); paramMap.put("userName", loginUserName);//登录用户名。用途：记录该任务的用户
	 * paramMap.put("message", "");//备注 params.put("processDefinitionKey",
	 * "nonTransactionCashRedemptionHomeProcess");//流程定义key params.put("orgId",
	 * "4");//机构id。用途：查询该机构的角色（任务处理人）
	 *
	 * @author:liangyanjun
	 * @time:2016年1月29日下午4:10:00
	 * @param params
	 * @return
	 * @throws TException
	 */
	@Override
	public String executeStartOfCommon(Map<String, String> paramMap)
			throws TException {
		String userName = paramMap.get("userName");
		String message = paramMap.get("message");
		String processDefinitionKey = paramMap.get("processDefinitionKey");
		try {
			Map<String, Object> taskVariables = new HashMap<String, Object>();
			taskVariables.put("owner", userName);// 创建者
			taskVariables.put("message", message);
			taskVariables.put("candidateUsers", new ArrayList<String>());// 下一流程节点处理人
			// 启动工作流
			ProcessInstance processInstance = super.start(userName,
					processDefinitionKey, taskVariables);
			String processInstanceId = processInstance.getId();

			// 获取自已本身的节点
			Task task = super.findCurrentTaskByInstanceId(processInstance
					.getId());
			// 完成下一个节点
			paramMap.put("taskId", task.getId());
			paramMap.put("processInstanceId", processInstanceId);
			completeOfCommon(paramMap);
			return processInstanceId;
		} catch (Exception e) {
			logger.error("启动工作流失败：" + ExceptionUtil.getExceptionMessage(e)
					+ ",入参：" + paramMap);
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * 完成任务(该方法通用) 入参说明： Map<String, String> paramMap=new HashMap<String,
	 * String>(); paramMap.put("userName", loginUserName);//登录用户名。用途：记录该任务的用户
	 * paramMap.put("message", "");//备注 paramMap.put("taskId",
	 * bizHandleWorkflowDTO.getTaskId());//任务id paramMap.put("executionId",
	 * bizHandleWorkflowDTO.getExecutionId());//流程实例id paramMap.put("orgId",
	 * orgId+"");//机构id。用途：查询该机构的角色（任务处理人）
	 * 
	 * @author:liangyanjun
	 * @time:2016年1月29日下午4:10:00
	 * @param params
	 * @return
	 * @throws TException 
	 * @throws ThriftServiceException 
	 */
	@Override
	public void completeOfCommon(Map<String, String> paramMap) throws ThriftServiceException, TException {
		String userName = paramMap.get("userName");
		String message = paramMap.get("message");
		String taskId = paramMap.get("taskId");
		
		List<String> nextUserList = getNextUserList(paramMap);
		
		Map<String, Object> taskVariables = new HashMap<String, Object>();
		taskVariables.put("owner", userName);// 创建者
		taskVariables.put("message", message);
		taskVariables.put("candidateUsers", nextUserList);// 下一流程节点处理人
		//projectId不为空时，调用特殊处理类
      if (paramMap.get("projectId") != null) {
         Task task = super.findCurrentTaskByInstanceId(paramMap.get("processInstanceId"));
         int projectId = new Integer(paramMap.get("projectId"));
         int refId = new Integer(paramMap.get("refId"));
         int userId = new Integer(paramMap.get("userId"));
         // 根据不同的流程定义ID来创建流程特殊处理类
         WorkflowSpecialDispose workflowSpecialDispose = workflowSpecialDisposeFactory.factory(paramMap.get("processDefinitionKey"), paramMap.get("startTaskDefKey"),
               projectId, refId, null, null, message, userId);
         if (workflowSpecialDispose != null) {
            workflowSpecialDispose.handleBranch(task, taskVariables);
            workflowSpecialDispose.updateProjectStatus();
         }
      }
      // 簽收任務：
		taskService.claim(taskId, userName);
		// 设置完成时间
		taskService.setDueDate(taskId, new Date());
		// 完成任务
		taskService.complete(taskId, taskVariables);
	}

   /**
    * 获取下一个节点的办理人
    *@author:liangyanjun
    *@time:2016年6月2日下午4:32:44
    *@param userName
    *@param taskId
    *@return
    *@throws TException
    *@throws ThriftServiceException
    */
   private List<String> getNextUserList(Map<String, String> paramMap) throws TException, ThriftServiceException {
      List<String> nextUserList = new ArrayList<String>();
      
      String processDefinitionKey =  paramMap.get("processDefinitionKey");//当前流程定义key
      String startTaskDefKey =  paramMap.get("startTaskDefKey");//开始任务节点定义key：这个值只有在任务启动的时候才有，其他情况为空
//      String userName = paramMap.get("userName");
      String pmUserName = StringUtil.isBlank(paramMap.get("pmUserName"))?paramMap.get("userName"):paramMap.get("pmUserName");
	  String taskId = paramMap.get("taskId");
	  
		// 设置下一个节点的办理人
		String nextRoles = getFormValue(taskId, "nextRole").toString();
		SysUser pmUser = sysUserMapper.selectSysUserByUserName(pmUserName);
		//SysUser sysUser = sysUserMapper.selectSysUserByUserName(userName);
		Set<Map<String, String>> userMap=new HashSet<Map<String,String>>();
		String[] split = nextRoles.split(",");
		if(processDefinitionKey != null && processDefinitionKey.equals("foreLoanRequestProcess")){
			int projectId = new Integer(paramMap.get("projectId"));
			String special = super.checkSpecialDesc(projectId);
			double loanMoney = super.findProjectLoanMoney(projectId);
			//大于300万或者有特殊情况走部门经理
			if(special.equals("Special") || loanMoney>3000000){
				userMap.addAll(getUserMapByRoleCodes("DEPARTMENT_MANAGER", pmUser));
			}else{
				userMap.addAll(getUserMapByRoleCodes("INSPECTOR", pmUser));
			}
			//退咨询费选下一步处理人的特殊处理，入口是第一个节点，万通的则是“部门经理审核”，小科和机构是“运营经理审核”
		}else if("refundInterestFeeProcess".equals(processDefinitionKey)&&"task_Request".equals(startTaskDefKey)){
		    int projectId = new Integer(paramMap.get("projectId"));
		    Project project = projectMapper.getProjectInfoById(projectId);
		    int projectSource = project.getProjectSource();//项目来源：万通=1，小科=2，机构=3
		    if (Constants.PROJECT_SOURCE_WT==projectSource) {
		        nextRoles="DEPARTMENT_MANAGER";//部门经理审核
            }else{
                nextRoles="OPERATE_DEPARTMENT";//运营经理审核
            }
		    userMap.addAll(getUserMapByRoleCodes(nextRoles, pmUser));
		}else{
			for (String nextRole : split) {
				   //如果办理角色是客户经理，则选择为客户经理自己（比如客户经理自己提的单，在取旧证的时候办理角色是客户经理，此时获取的用户就是自己，不包括其他的客户经理）
				   if ("JUNIOR_ACCOUNT_MANAGER".equals(nextRole)) {
				      nextUserList.add(pmUserName);
				      continue;
				   }
				   userMap.addAll(getUserMapByRoleCodes(nextRole, pmUser));
				}
		}
		
		
		for (Map<String, String> map : userMap) {
		   nextUserList.add(map.get("userName"));
		}
      return nextUserList;
   }

	@Override
	public String executeStart(Map<String, String> paramMap,
			List<String> nextUserList) throws TException {
	   logger.info("流程启动开始executeStart-->paramMap:"+paramMap+","+nextUserList);
		String userName = paramMap.get("userName");
		String message = paramMap.get("message");
		String processDefinitionKey = paramMap.get("processDefinitionKey");
		// String workflowTaskDefKey = paramMap.get("workflowTaskDefKey");
		int refId = new Integer(paramMap.get("refId"));
		int projectId = new Integer(paramMap.get("projectId"));
		try {
		   //检查流程审批是否重复提交
	      WorkflowProjectVo queryProjectVo = new WorkflowProjectVo();
         queryProjectVo.setProjectId(projectId);
         queryProjectVo.setRefId(refId);
         queryProjectVo.setProcessDefinitionKey(processDefinitionKey);
         List<WorkflowProjectVo> WorkflowProjectVoList = workflowProjectMapper.findWorkflowProjectByWhere(queryProjectVo);
         if (WorkflowProjectVoList != null && WorkflowProjectVoList.size() > 0) {
            throw new WorkFlowExistException();
         }
			// 启动工作流
			Map<String, Object> taskVariables = new HashMap<String, Object>();
			taskVariables.put("owner", userName);
			taskVariables.put("message", message);
			taskVariables.put("candidateUsers", nextUserList);

			// 启动工作流
			ProcessInstance processInstance = super.start(userName,
					processDefinitionKey, taskVariables);
			logger.info("流程启动-->processInstance:"+processInstance);
			String processInstanceId = processInstance.getId();

			// 记录项目与流程的关系
			WorkflowProjectVo workflowProjectVo = new WorkflowProjectVo();
			workflowProjectVo.setRefId(refId);
			workflowProjectVo.setProjectId(projectId);
			workflowProjectVo.setWorkflowInstanceId(processInstanceId);
			workflowProjectVo.setProcessDefinitionKey(processDefinitionKey);
			workflowProjectMapper.insertWorkflowProject(workflowProjectVo);
			logger.info("记录项目与流程的关系-->workflowProjectVo:"+workflowProjectVo);
			// 获取自已本身的节点
			Task task = super.findCurrentTaskByInstanceId(processInstance.getId());

			// 完成第一个节点
			Map<String, String> fromVariables = new HashMap<String, String>();
			fromVariables.put("flow", "next");
			paramMap.put("taskId", task.getId());
			// 预处理下一节点候先人，包括处理代办人员转换
			// String candidateUsers =
			// preCandidateUsers(nextUserList,processDefinitionKey,workflowTaskDefKey);
			executeComplete(paramMap, nextUserList, fromVariables);
			logger.info("完成第一个节点-->executeComplete");
			if (logger.isDebugEnabled()) {
				logger.debug(">>> Work flow: " + processDefinitionKey
						+ " is started. Process instance id: "
						+ processInstanceId);
			}
			logger.info("流程启动成功executeStart-->paramMap:"+paramMap+","+nextUserList);
			return processInstanceId;
		} catch (Exception e) {
			logger.error("流程启动失败executeStart-->paramMap:"+paramMap+","+nextUserList);
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 完成任務
	 * @param userName
	 * @param taskId
	 * @param nextUserList
	 * @author: Simon.Hoo
	 * @throws TException
	 * @date: 2015年1月30日 下午3:33:39
	 */
	@Override
	public int executeComplete(Map<String, String> paramMap,
			List<String> nextUserList, Map<String, String> fromVariables)
			throws TException {
		String userName = paramMap.get("userName");
		String taskId = paramMap.get("taskId");
		String message = paramMap.get("message");
		int projectId = new Integer(paramMap.get("projectId"));
		int refId = new Integer(paramMap.get("refId"));
		String processDefinitionKey = paramMap.get("processDefinitionKey");
		String workflowTaskDefKey = paramMap.get("workflowTaskDefKey");
		String isVetoProject = paramMap.get("isVetoProject");
		int userId = new Integer(paramMap.get("userId"));
		ProjectRecord projectRecord = new ProjectRecord();
		projectRecord.setProjectId(projectId);
		projectRecord.setCompleteDttm(DateUtil.format(new Date()));
		projectRecord.setProcessUserId(userId);
		try {
			// 根据不同的流程定义ID来创建流程特殊处理类
			WorkflowSpecialDispose workflowSpecialDispose = workflowSpecialDisposeFactory
					.factory(processDefinitionKey, workflowTaskDefKey,
							projectId, refId, isVetoProject, null,message,userId);
			if(workflowSpecialDispose != null){
				// 更改项目状态 1：创建，2：审批，3：终结）
				int res = workflowSpecialDispose.updateProjectStatus();
				if (res > -1) {
					projectRecord.setProcessType(res);
					projectMapper.addProjectRecord(projectRecord);
				}
			}
			
			// 获得当前任务的对应实列
			Task task = super.findCurrentTaskById(taskId);
			// 获得当前流程的定义模型
			ProcessDefinitionEntity processDefinition = super
					.findProcessDefinitionByDefinitionId(task
							.getProcessDefinitionId());
			// 获得当前流程定义模型的所有任务节点
			List<ActivityImpl> activitilist = processDefinition.getActivities();
			String nextWorkflowTaskDefKey = super.getNextTaskByActivityImpls(
					activitilist, taskId, workflowTaskDefKey);
			// 预处理下一节点候先人，包括处理代办人员转换
			String candidateUsers = preCandidateUsers(nextUserList,
					processDefinitionKey, nextWorkflowTaskDefKey);
			return super.complete(userName, taskId, candidateUsers, message,
					fromVariables, projectId, workflowSpecialDispose);

		} catch (Exception e) {
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 驳回任務
	 * @param userName
	 * @param taskId
	 * @param destTaskId
	 * @param message
	 * @author: Simon.Hoo
	 * @throws TException
	 * @date: 2015年1月30日 下午3:33:39
	 */
	@Override
	public int executeTurnDown(Map<String, String> paramMap) throws TException {
		String userName = paramMap.get("userName");
		String destTaskId = paramMap.get("destTaskId");
		String taskId = paramMap.get("taskId");
		String message = paramMap.get("message");
		int projectId = new Integer(paramMap.get("projectId"));
		int refId = new Integer(paramMap.get("refId"));
		String processDefinitionKey = paramMap.get("processDefinitionKey");
		String workflowTaskDefKey = paramMap.get("workflowTaskDefKey");
		int userId = new Integer(paramMap.get("userId"));
		String isVetoProject = paramMap.get("isVetoProject");
		try {
			// 根据不同的流程定义ID来创建流程特殊处理类
			WorkflowSpecialDispose workflowSpecialDispose = workflowSpecialDisposeFactory
					.factory(processDefinitionKey, workflowTaskDefKey,
							projectId, refId, isVetoProject, destTaskId,message,userId);
			// 更改项目状态
			workflowSpecialDispose.updateProjectStatus();

			// 获得当前任务的对应实列
			Task task = super.findCurrentTaskById(taskId);

			// 当前任务key
			String taskDefKey = task.getTaskDefinitionKey();

			// 获得当前流程的定义模型
			ProcessDefinitionEntity processDefinition = super
					.findProcessDefinitionByDefinitionId(task
							.getProcessDefinitionId());

			// 获得当前流程定义模型的所有任务节点
			List<ActivityImpl> activitilist = processDefinition.getActivities();

			// 获得当前活动节点和驳回的目标节点
			ActivityImpl currActiviti = null;// 当前活动节点
			ActivityImpl destActiviti = null;// 驳回目标节点

			int sign = 0;
			for (ActivityImpl activityImpl : activitilist) {
				// 确定当前活动activiti节点
				if (taskDefKey.equals(activityImpl.getId())) {
					currActiviti = activityImpl;
					sign++;
				} else if (destTaskId.equals(activityImpl.getId())) {
					destActiviti = activityImpl;
					sign++;
				}

				if (sign == 2) {
					break;// 如果两个节点都获得,退出跳出循环
				}
			}

			// 保存当前活动节点的流程想参数
			List<PvmTransition> hisPvmTransitionList = new ArrayList<PvmTransition>(
					0);

			for (PvmTransition pvmTransition : currActiviti
					.getOutgoingTransitions()) {
				hisPvmTransitionList.add(pvmTransition);
			}

			// 清空当前活动节点的所有流出项
			currActiviti.getOutgoingTransitions().clear();

			// 为当前节点动态创建新的流出项
			TransitionImpl newTransitionImpl = currActiviti
					.createOutgoingTransition();

			// 为当前活动节点新的流出目标指定流程目标
			newTransitionImpl.setDestination(destActiviti);

			// 查询已完成的历史节点
			List<HistoricTaskInstance> hisTaskInstanceList = super
					.findFinishedTaskByInstanceAndTaskKey(
							task.getProcessInstanceId(), destTaskId);

			// 为下一节点，预处理侯先人，包括代办其内的用户
			List<String> backCandidateUserList = new ArrayList<String>();
			backCandidateUserList.add(hisTaskInstanceList.get(0).getAssignee());
			String nextWorkflowTaskDefKey = super.getNextTaskByActivityImpls(
					activitilist, taskId, workflowTaskDefKey);
			String candidateUsers = preCandidateUsers(backCandidateUserList,
					processDefinitionKey, nextWorkflowTaskDefKey);

			// 设置下一步
			Map<String, String> fromVariables = new HashMap<String, String>();
			fromVariables.put("flow", "next");

			// 执行当前任务驳回到目标任务
			super.complete(userName, taskId, candidateUsers, message,
					fromVariables, projectId, workflowSpecialDispose);

			// 清除目标节点的新流入项
			destActiviti.getIncomingTransitions().remove(newTransitionImpl);

			// 清除原活动节点的临时流程项
			currActiviti.getOutgoingTransitions().clear();

			// 还原原活动节点流出项参数
			currActiviti.getOutgoingTransitions().addAll(hisPvmTransitionList);
			return 1;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	}

	/**
	 * @Description: 查詢當前用戶的待處理任務列表（僅工作流）
	 * @param candidateUser
	 * @return 工作流任務列表
	 * @author: Simon.Hoo
	 * @date: 2015年1月30日 下午3:27:56
	 */
	@Override
	public List<TaskVo> queryWorkFlowTodoTask(TaskVo taskVo) {
		List<TaskVo> taskVoWorkFlowList = new ArrayList<TaskVo>();
		try {
			List<GridViewDTO> taskList = workflowProjectMapper.queryTaskList(
					taskVo.getTaskUserName(), taskVo.getWorkfloPprojectName(),
					taskVo.getPage(), taskVo.getRows());
			if (taskList != null && taskList.size() > 0) {
				taskVoWorkFlowList = new ArrayList<TaskVo>();
				for (GridViewDTO task : taskList) {
					ProcessDefinition processDefinition = super
							.findProcessDefinitionByInstanceId(task
									.getValue11());

					TaskVo vo = new TaskVo();
					vo.setTaskUserName(taskVo.getTaskUserName());
					SysUser sysUser = sysUserMapper
							.selectSysUserByUserName(taskVo.getTaskUserName());
					vo.setTaskUserRealName(sysUser.getRealName());

					vo.setTaskType(1);// 1:工作流
					vo.setWorkflowName(processDefinition.getName());
					vo.setTaskName(task.getValue2());
					vo.setAllocationDttm(task.getValue6());
					vo.setTaskStatus(1);
					vo.setWorkflowProcessDefkey(super
							.formatProcessDefinitionId(processDefinition
									.getId()));
					vo.setWorkflowInstanceId(task.getValue11());
					vo.setWorkflowTaskDefKey(task.getValue13());
					vo.setWorkflowTaskId(task.getPid());

					vo.setNextRoleCode(getFormValue(task.getPid(), "nextRole")
							.toString());
					vo.setAllowTurnDown(getFormValue(vo.getWorkflowTaskId(),
							"allowTurnDown").toString());
					addTaskVoList(taskVoWorkFlowList, vo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("工作流查询:" + ExceptionUtil.getExceptionMessage(e));
		}
		return taskVoWorkFlowList;
	}

	@Override
	public int queryWorkFlowTodoTaskTotal(TaskVo taskVo) {
		/*
		 * List<Task> taskList =
		 * super.findTaskByCandidateUserTotal(candidateUser); List<TaskVo>
		 * taskVoWorkFlowList = new ArrayList<TaskVo>(); if (taskList != null &&
		 * taskList.size() > 0) { for (Task task : taskList) { ProcessDefinition
		 * processDefinition = super .findProcessDefinitionByInstanceId(task
		 * .getProcessInstanceId()); TaskVo vo = new TaskVo();
		 * vo.setWorkflowInstanceId(task.getProcessInstanceId());
		 * vo.setWorkflowProcessDefkey(super
		 * .formatProcessDefinitionId(processDefinition .getId()));
		 * vo.setWorkflowTaskId(task.getId());
		 * addTaskVoList(taskVoWorkFlowList,vo); } } return
		 * taskVoWorkFlowList.size();
		 */
		// return super.findTaskByCandidateUserTotal(candidateUser);
		taskVo.setPage(1);
		taskVo.setRows(1000);
		List<GridViewDTO> taskList = workflowProjectMapper.queryTaskList(
				taskVo.getTaskUserName(), taskVo.getWorkfloPprojectName(),
				taskVo.getPage(), taskVo.getRows());
		return taskList.size();
	}

	private void addTaskVoList(List<TaskVo> taskVoWorkFlowList, TaskVo vo) {
		WorkflowProjectVo wpv = new WorkflowProjectVo();
		wpv.setWorkflowInstanceId(vo.getWorkflowInstanceId());
		String workflowProcessDefkey = vo
      		.getWorkflowProcessDefkey();
      wpv.setProcessDefinitionKey(workflowProcessDefkey);
		// 项目ID
		WorkflowProjectVo workflowProjectVo = workflowProjectMapper
				.findWorkflowProject(wpv);

		if (null != workflowProjectVo) {
			int refId = workflowProjectVo.getRefId();
         vo.setRefId(refId);
			int projectId = workflowProjectVo
         		.getProjectId();
         vo.setProjectId(projectId);
			if ("creditFreezeOrThawRequestProcess".equals(workflowProcessDefkey)) {
				// (cType:额度调整类型（1=授信、2=使用、3=冻结、4=解冻 、5=还款）)
				int cType = creditsDTOMapper
						.getCreditLimitTypeByPid(refId);
				if (3 == cType) {
					vo.setWorkflowName("额度冻结工作流");
				} else if (4 == cType) {
					vo.setWorkflowName("额度解冻工作流");
				}

			}
			// projectName
			Project project = null;

			// 展期申请 需要使用refid 查询项目名称
			if ("creditExtensionRequestProcess".equalsIgnoreCase(workflowProcessDefkey)
					|| "foreExtensionRequestProcess".equals(workflowProcessDefkey)) {
				project = projectMapper.getProjectInfoById(refId);
				if (null == project) {
					vo.setWorkfloPprojectName("");
				} else {
					vo.setWorkfloPprojectName(project.getProjectName());
					taskVoWorkFlowList.add(vo);
				}
				//合作审请,修改机构合作信息工作流
			}else if("cooperationRequestProcess".equalsIgnoreCase(workflowProcessDefkey)||"cooperationUpdateProcess".equalsIgnoreCase(workflowProcessDefkey)){
				//合作申请ID
				int cooperationId = projectId;
				String orgName = orgCooperatMapper.getOrgNameByCid(cooperationId);
				vo.setWorkfloPprojectName(orgName);
				taskVoWorkFlowList.add(vo);
				//退费
			}else if("refundInterestFeeProcess".equalsIgnoreCase(workflowProcessDefkey)){
			   BizLoanBatchRefundFeeMain batchRefundFeeMain = batchRefundFeeMainMapper.getById(projectId);
            if (batchRefundFeeMain!=null) {
               vo.setWorkfloPprojectName("批量退费"+batchRefundFeeMain.getBatchName());
            }else{
               project = projectMapper.getProjectInfoById(workflowProjectVo.getProjectId());
               vo.setWorkfloPprojectName(project.getProjectName());
            }
            taskVoWorkFlowList.add(vo);
			} else {
				project = projectMapper.getProjectInfoById(workflowProjectVo
						.getProjectId());
				if (null == project) {
					vo.setWorkfloPprojectName("");
				} else {
					vo.setWorkfloPprojectName(project.getProjectName());
					taskVoWorkFlowList.add(vo);
				}
			}
			
		}
		// 如果是业务办理的办理动态工作流，则从工作流关联表获取项目的信息
		if (workflowProjectVo == null) {
			// 根据流程实例id获取项目id
			BizHandleWorkflowDTO bizHandleWorkflowQuery = new BizHandleWorkflowDTO();
			bizHandleWorkflowQuery.setExecutionId(vo.getWorkflowInstanceId());
			bizHandleWorkflowQuery.setStatus(Constants.STATUS_ENABLED);
			List<BizHandleWorkflowDTO> handleWorkflowList = bizHandleMapper
					.findAllBizHandleWorkflow(bizHandleWorkflowQuery);
			if (handleWorkflowList == null || handleWorkflowList.isEmpty()) {
				return;
			}
			BizHandleWorkflowDTO bizHandleWorkflowDTO = handleWorkflowList
					.get(0);
			int projectId = bizHandleWorkflowDTO.getProjectId();
			// 根据项目id获取项目信息
			Project project = projectMapper.getProjectInfoById(projectId);
			vo.setProjectId(project.getPid());
			vo.setWorkfloPprojectName(project.getProjectName());
			taskVoWorkFlowList.add(vo);
		}
	}

	/**
	 * @Description: 根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
	 * @param workflowProcessDefkey
	 *            流程定义Key
	 * @param refId
	 *            引用ID
	 * @return TaskVo
	 * @author: JingYu.Dai
	 * @throws TException 
	 * @date: 2015年6月2日 下午2:48:30
	 */
	@Override
	public TaskVo getTaskVoByWPDefKeyAndRefId(String workflowProcessDefkey,
			int refId) throws TException {
		TaskVo vo = new TaskVo();
		WorkflowProjectVo wpv = new WorkflowProjectVo();
		wpv.setRefId(refId);
		wpv.setProcessDefinitionKey(workflowProcessDefkey);
		String processInstanceId = null;
		// 项目ID
		WorkflowProjectVo workflowProjectVo = workflowProjectMapper
				.findWorkflowProject(wpv);
		if (null != workflowProjectVo) {
			vo.setRefId(workflowProjectVo.getRefId());
			vo.setProjectId(workflowProjectVo.getProjectId());
			processInstanceId = workflowProjectVo.getWorkflowInstanceId();
			Task task = super.getTaskByProcessInstanceId(processInstanceId);
			ProcessDefinition processDefinition = super
					.findProcessDefinitionByInstanceId(processInstanceId);
			vo.setTaskType(1);// 1:工作流
			vo.setWorkflowName(processDefinition.getName());
			vo.setTaskName(task.getName());
			vo.setAllocationDttm(DateUtil.format(task.getCreateTime()));
			vo.setTaskStatus(1);
			vo.setWorkflowProcessDefkey(super
					.formatProcessDefinitionId(processDefinition.getId()));
			vo.setWorkflowInstanceId(task.getProcessInstanceId());
			vo.setWorkflowTaskDefKey(task.getTaskDefinitionKey());
			vo.setWorkflowTaskId(task.getId());
			vo.setNextRoleCode(getFormValue(task.getId(), "nextRole")
					.toString());
			vo.setAllowTurnDown(getFormValue(task.getId(), "allowTurnDown")
					.toString());
		}
		return vo;
	}

	@Override
	public List<TaskHistoryDto> queryWorkFlowHistory(String processInstanceId,
			int page, int rows) {
		List<TaskHistoryDto> hisList = null;
		try {
			if (processInstanceId == null) {
				return null;
			}
			hisList = new ArrayList<TaskHistoryDto>();

			List<HistoricTaskInstance> taskList = super
					.findHistoricTaskInstanceById(processInstanceId, page, rows);

			ProcessDefinition processDefinition = super
					.findProcessDefinitionByInstanceId(processInstanceId);

			for (HistoricTaskInstance hti : taskList) {
				TaskHistoryDto dto = new TaskHistoryDto();
				dto.setWorkflowTaskDefKey(super
						.formatProcessDefinitionId(processDefinition.getId()));
				dto.setWorkflowInstanceId(processInstanceId);
				dto.setWorkflowTaskId(hti.getId());
				dto.setTaskName(hti.getName());
				dto.setWorkflowProcessName(processDefinition.getName());
				Date endTime = hti.getEndTime();
				if(endTime == null){
					endTime = hti.getStartTime();
				}
				dto.setExecuteDttm(DateUtil.format(endTime));
				dto.setApprovalStatus(preTaskStatus(hti.getDeleteReason()));
				dto.setExecuteUserName(hti.getAssignee());

				dto.setExecuteUserRealName(hti.getAssignee() == null ? getUserNamesByTaskId(hti
						.getId()) : sysUserMapper.selectSysUserByUserName(
						hti.getAssignee()).getRealName());

				dto.setMessage(getTaskComments(processInstanceId, hti.getId()));

				hisList.add(dto);
			}

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return hisList;
	}

	/**
	 * @Description: 根据任务Id查询用户名集合字符窜
	 * @param taskId
	 *            任务ID
	 * @author: JingYu.Dai
	 * @date: 2015年6月8日 下午4:57:33
	 */
	private String getUserNamesByTaskId(String taskId) {
		return workflowProjectMapper.getUserNamesByTaskId(taskId);
	}

	@Override
	public List<TaskHistoryDto> queryWorkFlowFinishedHistory(
			EndTaskPageVO endTaskPageVO) throws ThriftServiceException,
			TException {
		List<TaskHistoryDto> hisList = null;
		try {
			if (endTaskPageVO == null || endTaskPageVO.getUserName() == null) {
				return null;
			}
			hisList = new ArrayList<TaskHistoryDto>();
			// String mess = getTaskComments(117622+"",117660+"");
			List<HistoricTaskInstance> taskList = super
					.findHistoricTaskInstanceByUser(endTaskPageVO);
			for (HistoricTaskInstance hti : taskList) {
				TaskHistoryDto dto = new TaskHistoryDto();
				dto.setWorkflowProcessDefkey(hti.getProcessDefinitionId()
						.split(":")[0]);

				HistoricActivityInstance historicActivityInstance = findLastActivityInstanceByInstanceId(hti
						.getProcessInstanceId());
				dto.setWorkflowTaskDefKey(historicActivityInstance
						.getActivityId());

				dto.setWorkflowInstanceId(hti.getProcessInstanceId());
				dto.setWorkflowTaskId(hti.getId());
				dto.setTaskName(hti.getName());

				ProcessDefinition processDefinition = super
						.findProcessDefinitionByInstanceId(hti
								.getProcessInstanceId());

				dto.setWorkflowProcessName(processDefinition.getName());
				dto.setExecuteDttm(DateUtil.format(hti.getDueDate()));
				dto.setApprovalStatus(preTaskStatus(hti.getDeleteReason()));

				dto.setExecuteUserName(hti.getAssignee());
				SysUser sysUser = sysUserMapper.selectSysUserByUserName(hti
						.getAssignee());
				dto.setExecuteUserRealName(sysUser.getRealName());

				dto.setMessage(getTaskComments(hti.getProcessInstanceId(),
						hti.getId()));

				WorkflowProjectVo wpv = new WorkflowProjectVo();
				wpv.setWorkflowInstanceId(hti.getProcessInstanceId());
				wpv.setProcessDefinitionKey(dto.getWorkflowProcessDefkey());
				// 项目ID
				WorkflowProjectVo workflowProjectVo = workflowProjectMapper
						.findWorkflowProject(wpv);
				dto.setRefId(workflowProjectVo != null ? workflowProjectVo
						.getRefId() : 0);
				dto.setProjectId(workflowProjectVo != null ? workflowProjectVo.getProjectId() : 0);
				// projectName
				Project project = null;
				List<Project> list = new ArrayList<Project>();

				// 展期申请 需要使用refid 查询项目名称
				if ("extensionRequestProcess".equalsIgnoreCase(dto
						.getWorkflowProcessDefkey())
						|| "creditWithdrawalRequestProcess".equals(dto
								.getWorkflowProcessDefkey())) {
					list = projectMapper
							.getLoanProjectByProjectId(workflowProjectVo
									.getRefId());
				} else {
					list = projectMapper
							.getLoanProjectByProjectId(workflowProjectVo
									.getProjectId());
				}
				if (list.size() > 0) {
					project = list.get(0);
				}
				if (null == project) {
					dto.setProjectName("");
				} else {
					dto.setProjectName(project.getProjectName());
					hisList.add(dto);
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}

		return hisList;
	}

	@Override
	public int queryWorkFlowFinishedHistoryTotal(EndTaskPageVO endTaskPageVO) throws TException {
		return super.queryWorkFlowFinishedHistoryTotal(endTaskPageVO);
	}

	@Override
	public List<SysLookupVal> filterTurnDownTaskNodes(String taskId) throws TException {
		List<SysLookupVal> list = new ArrayList<SysLookupVal>();
		if (taskId != null && taskId.trim().length() > 0) {
			// 获取任务对象
			Task task = super.findCurrentTaskById(taskId);
			if (task != null) {
				List<HistoricActivityInstance> hisActivityInstanceList = super
						.findHistoricActivityInstanceByInstanceId(task
								.getProcessInstanceId());
				HistoricActivityInstance requestNode = hisActivityInstanceList
						.get(0);
				SysLookupVal requestNodeVal = new SysLookupVal();
				requestNodeVal.setLookupVal(requestNode.getActivityId());
				requestNodeVal.setLookupDesc(requestNode.getActivityName());
				list.add(requestNodeVal);
				List<HistoricActivityInstance> splitList = new ArrayList<HistoricActivityInstance>();
				// 用来判断当前节点是否在历史中出现过
				int j = 0;
				for (HistoricActivityInstance in : hisActivityInstanceList) {
					if (!in.getActivityId().equalsIgnoreCase(
							task.getTaskDefinitionKey())) {
						splitList.add(in);
					} else {
						j++;
						splitList.add(in);
						break;
					}
				}
				int afterNodeIndex = (splitList.size() - 1) <= 0 ? 0
						: splitList.size() - 1;
				if (afterNodeIndex > 2) {
					HistoricActivityInstance afterNode = splitList
							.get(afterNodeIndex - j);
					SysLookupVal afterNodeVal = new SysLookupVal();
					afterNodeVal.setLookupVal(afterNode.getActivityId());
					afterNodeVal.setLookupDesc(afterNode.getActivityName());
					list.add(afterNodeVal);
				} else if (afterNodeIndex > 1) {
					HistoricActivityInstance afterNode = splitList
							.get(afterNodeIndex - j);
					SysLookupVal afterNodeVal = new SysLookupVal();
					afterNodeVal.setLookupVal(afterNode.getActivityId());
					afterNodeVal.setLookupDesc(afterNode.getActivityName());
					list.add(afterNodeVal);
				} else if (j == 0 && afterNodeIndex > 0) {
					HistoricActivityInstance afterNode = splitList
							.get(afterNodeIndex);
					SysLookupVal afterNodeVal = new SysLookupVal();
					afterNodeVal.setLookupVal(afterNode.getActivityId());
					afterNodeVal.setLookupDesc(afterNode.getActivityName());
					list.add(afterNodeVal);
				}
			}
		}
		return list;
	}

	/**
	 * @Description: 預處理下一任務侯先人名單
	 * @param nextUserList
	 *            处理人列表
	 * @param workflowProcessDefId
	 *            流程定义ID
	 * @param workflowTaskId
	 *            任务定义ID
	 * @return String
	 * @author: JingYu.Dai
	 * @date: 2015年5月15日 上午11:35:35
	 */
	private String preCandidateUsers(List<String> nextUserList,
			String workflowProcessDefId, String workflowTaskId) {
		StringBuffer sb = new StringBuffer();
		if (null != nextUserList)
			for (int i = 0; i < nextUserList.size(); i++) {
				sb.append(nextUserList.get(i));
				sb.append(",");
				// 處理代辦任務指定人：
				String insteadUsers = getInsteadUsers(nextUserList.get(i),
						workflowProcessDefId, workflowTaskId);
				// 如果當前用戶是代辦，則將代辦人員加入到侯先人名單中，否則就還是他自己
				if (!nextUserList.get(i).equals(insteadUsers)) {
					sb.append(insteadUsers);
					sb.append(",");
				}
			}
		return nextUserList.size() > 0 ? (sb.substring(0, sb.length() - 1))
				.toString() : null;
	}

	/**
	 * @Description: 查詢當前用戶是否處于代辦狀態，如果是則返回代辦人員，否則返回NULL
	 * @param userName
	 *            用户名
	 * @param workflowProcessDefId
	 *            流程定义ID
	 * @param workflowTaskId
	 *            任务定义ID
	 * @return
	 * @author: JingYu.Dai
	 * @date: 2015年5月15日 上午11:38:31
	 */
	private String getInsteadUsers(String userName,
			String workflowProcessDefId, String workflowTaskId) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userName", userName);
		map.put("workflowProcessDefId", workflowProcessDefId);
		map.put("workflowTaskId", workflowTaskId);
		List<TaskSettingVo> taskSettingVoList = taskMapper
				.findTaskSettingsByUserIdInNow(map);
		if (null != taskSettingVoList && taskSettingVoList.size() > 0) {
			StringBuffer sb = new StringBuffer(taskSettingVoList.size());
			for (int i = 0; i < taskSettingVoList.size(); i++) {
				SysUser sysUser = sysUserMapper.getSysUserByPid(String
						.valueOf(taskSettingVoList.get(i).getAgencyUserId()));
				if (!userName.equals(sysUser.getUserName())) {
					sb.append(sysUser.getUserName());
					sb.append(",");
				}
			}
			return (sb.substring(0, sb.length() - 1)).toString();
		} else {
			return userName;
		}
	}

	/**
	 * 根据任务ID，FORM ID获取FORM值。
	 * 
	 * @author:liangyanjun
	 * @throws TException 
	 */
	@Override
	public String getFormValueStr(String taskId, String formKey) throws TException {
		return getFormValue(taskId, formKey).toString();
	}

	/**
	 * @Description: 根据任务ID，FORM ID获取FORM值。
	 * @param taskId
	 * @param formKey
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年2月5日 上午9:43:12
	 */
	private Object getFormValue(String taskId, String formKey) throws TException {
		
		List<FormProperty> formProperties = super.findTaskFormPropertyListByTaskId(taskId);
		for (FormProperty formProperty : formProperties) {
			
			if (formKey.equalsIgnoreCase(formProperty.getId())) {
				return formProperty.getValue();
			}
		}
		return "";
	}

	/**
	 * @Description: 根据实例IDT和任务ID，获取审批意见。
	 * @param processInstanceId
	 * @param taskId
	 * @return
	 * @author: Simon.Hoo
	 * @throws TException 
	 * @date: 2015年2月5日 上午9:40:59
	 */
	private String getTaskComments(String processInstanceId, String taskId) throws TException {
		List<HistoricActivityInstance> hais = super
				.findHistoricActivityInstanceByInstanceId(processInstanceId);
		for (HistoricActivityInstance ha : hais) {
			if (ha.getTaskId().equalsIgnoreCase(taskId)) {
				List<Comment> commentList = super.findCommnetByTaskId(ha
						.getTaskId());
				if (null != commentList && commentList.size() > 0) {
					StringBuffer sb = new StringBuffer();
					for (Comment c : commentList) {
						sb.append(c.getFullMessage()).append("\r\n");
					}
					return sb.toString().trim();
				}
				break;
			}
		}
		return "";
	}

	private String preTaskStatus(String deleteReason) {
		if (null != deleteReason) {
			if ("completed".equalsIgnoreCase(deleteReason)) {
				return "任务完成";
			} else if ("deleted".equalsIgnoreCase(deleteReason)) {
				return "任务删除";
			} else {
				return deleteReason;
			}
		} else {
			return "未完成";
		}
	}

	/**
	 * @Description: 根据流程与项目映射类查询映射数据
	 * @param workflowProjectVo
	 * @return WorkflowProjectVo
	 * @author: JingYu.Dai
	 * @date: 2015年4月10日 上午11:22:30
	 */
	@Override
	public WorkflowProjectVo findWorkflowProject(
			WorkflowProjectVo workflowProjectVo) {
		WorkflowProjectVo vo = workflowProjectMapper
				.findWorkflowProject(workflowProjectVo);
		return vo == null ? (new WorkflowProjectVo()) : vo;
	}

	/**
	 * @Description: 根据项目ID和流程实例ID查询数据
	 * @param projectId
	 *            项目ID
	 * @return boolean (true:此客戶下沒有執行的流程，false:此客戶下面已有流程在執行)
	 * @author: JingYu.Dai
	 * @date: 2015年4月22日 下午8:22:01
	 */
	@Override
	public boolean isAllowStartWorkflow(int projectId)
			throws ThriftServiceException, TException {
		boolean falg = true;
		int count = workflowProjectMapper
				.getTaskAcctProHisByProjectId(projectId);
		if (count > 0) {
			falg = false;
		}
		return falg;
	}

	/**
	 * @Description: 根据客户ID和流程实例ID查询数据
	 * @param acctId
	 * @return boolean (true:此客戶下沒有執行的流程，false:此客戶下面已有流程在執行)
	 * @author: JingYu.Dai
	 * @date: 2015年5月2日 下午2:40:18
	 */
	@Override
	public boolean isAllowStartWorkflowByAcctId(int acctId) {
		boolean falg = true;
		int count = workflowProjectMapper.getTaskAcctProHisByAcctId(acctId);
		if (count > 0) {
			falg = false;
		}
		return falg;
	}

	/**
	 * @Description: 根据项目Id查询流程状态
	 * @param projectId
	 *            项目ID
	 * @return 流程状态 (0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
	 * @author: JingYu.Dai
	 * @date: 2015年5月27日 下午4:28:37
	 */
	@Override
	public int getWorkflowStatusByProjectId(int projectId) {
		// 查询系统配置
		SysConfig sysConfig = sysConfigMapper
				.getByConfigName("IS_WHETHER_OPENING_PROCESS");
		if (null != sysConfig) {
			if ("1".equals(sysConfig.getConfigVal())) {
				Integer loanId = loanMapper.getLoanIdByProjectId(projectId);
				if (loanId != null && loanId > 0) {
					return loanMapper.getWorkflowStatusByPid(loanId);
				}
				return 0;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	/**
	 * @Description: 查询流程记录数据
	 * @param acctProHisVo
	 * @return int
	 * @author: JingYu.Dai
	 * @date: 2015年4月22日 下午8:22:41
	 */
	@Override
	public int selectTaskAcctProHis(BizTaskAcctProHisVo acctProHisVo)
			throws ThriftServiceException, TException {
		return workflowProjectMapper.selectTaskAcctProHis(acctProHisVo);
	}

	@Override
	public List<TaskHistoryDto> selectWorkflowHistoryList(
			EndTaskPageVO endTaskPageVO) throws ThriftServiceException,
			TException {
		List<TaskHistoryDto> tasks = workflowProjectMapper
				.selectWorkflowHistoryList(endTaskPageVO);
		return tasks == null ? (new ArrayList<TaskHistoryDto>()) : tasks;
	}

	@Override
	public int selectWorkflowHistoryListTotal(EndTaskPageVO endTaskPageVO)
			throws ThriftServiceException, TException {
		return workflowProjectMapper.countWorkflowHistoryList(endTaskPageVO);
	}

	@Override
	public String getRunLastTaskKeyByWIId(String workflowInstanceId)
			throws ThriftServiceException, TException {
		return workflowProjectMapper
				.getRunLastTaskKeyByWIId(workflowInstanceId);
	}

	@Override
	public int queryWorkFlowHistoryCount(String workflowInstanceId) {
		return super.findHistoricTaskInstanceByIdCount(workflowInstanceId);
	}

	/**
	 * @Description: 删除流程数据
	 * @param refId
	 *            引用Id
	 * @param projectId
	 *            项目ID
	 * @param workflowKey
	 *            流程定义Key
	 * @author: JingYu.Dai
	 * @date: 2015年6月3日 上午10:57:37
	 */
	@Override
	public void deleteProcessInstance(int refId, int projectId,
			String workflowKey) {
		WorkflowProjectVo wpv = new WorkflowProjectVo();
		wpv.setRefId(refId);
		wpv.setProcessDefinitionKey(workflowKey);
		wpv.setProjectId(projectId);
		WorkflowProjectVo workflowProjectVo = workflowProjectMapper
				.findWorkflowProject(wpv);
		/*
		 * if (projectId > 0) { Integer loadId =
		 * loanMapper.getLoanIdByProjectId(projectId); map.put("workflowStatus",
		 * 0); map.put("loadId", loadId); loanMapper.updateWorkflowStatus(map);
		 * }
		 */
		if (null != workflowProjectVo) {
			super.deleteProcessInstance(workflowProjectVo
					.getWorkflowInstanceId());
		}
	}

	/**
	 * @Description: 查询所有运行任务
	 * @param taskVo
	 * @return List<TaskVo>
	 * @author: JingYu.Dai
	 * @date: 2015年6月19日 下午5:11:23
	 */
	@Override
	public List<TaskVo> queryAllRunTask(TaskVo taskVo) {
		List<TaskVo> taskVoWorkFlowList = new ArrayList<TaskVo>();
		try {
			List<TaskVo> taskList = workflowProjectMapper
					.findAllRunTask(taskVo);
			if (taskList != null && taskList.size() > 0) {
				taskVoWorkFlowList = new ArrayList<TaskVo>();
				for (TaskVo task : taskList) {
					if (task.getTaskStatus() != 2) {
						task.setNextRoleCode(getFormValue(
								task.getWorkflowTaskId(), "nextRole")
								.toString());
						task.setAllowTurnDown(getFormValue(
								task.getWorkflowTaskId(), "allowTurnDown")
								.toString());
					}
					taskVoWorkFlowList.add(task);
				}
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
		return taskVoWorkFlowList;
	}

	/**
	 * @Description: TODO
	 * @return
	 * @author: JingYu.Dai
	 * @date: 2015年6月16日 下午3:04:19
	 */
	@Override
	public int queryAllRunTaskTotal(TaskVo taskVo) {
		return workflowProjectMapper.findAllRunTaskTotal(taskVo);
	}

	@Override
	public void deleteProcessInstances(String workflowInstanceIds)
			throws ThriftServiceException, TException {
		String[] ids = workflowInstanceIds.split(",");
		for (int i = 0; i < ids.length; i++) {
			if (!"".equals(ids[i])) {
				super.deleteProcessInstance(ids[i]);

				WorkflowProjectVo wpv = new WorkflowProjectVo();
				wpv.setWorkflowInstanceId(ids[i]);
				WorkflowProjectVo workflowProjectVo = workflowProjectMapper
						.findWorkflowProject(wpv);
				if (workflowProjectVo != null) {
					// 根据不同的流程定义ID来创建流程特殊处理类
					WorkflowSpecialDispose workflowSpecialDispose = workflowSpecialDisposeFactory
							.factory(
									workflowProjectVo.getProcessDefinitionKey(),
									workflowProjectVo.getProjectId(),
									workflowProjectVo.getRefId());
					// 重置项目状态
					workflowSpecialDispose.resetProject();
				}
			}
		}

	}

	@Override
	public List<WorkflowProjectVo> getRunWorkflowProject(
			WorkflowProjectVo workflowProjectVo) throws TException {
		return workflowProjectMapper.getRunWorkflowProject(workflowProjectVo);
	}

   /**
    * 获取机构下的用户map
    *@author:liangyanjun
    *@time:2016年5月13日上午10:21:22
    *@param roleCodes
    *@param loginUser
    *@return
    *@throws TException
    *@throws ThriftServiceException
    */
   @Override
   public Set<Map<String,String>> getUserMapByRoleCodes(String roleCodes, SysUser loginUser) throws TException, ThriftServiceException {
      // 根据roleCodes查询角色
      List<SysRole> sysRoles = systemUserRoleMapper.queryRolesByRoleCodes(Arrays.asList(roleCodes.split(",")));
      Set<Map<String,String>> handleUserSet = new HashSet<>();
      for (SysRole sysRole : sysRoles) {
         int roleId = sysRole.getPid();
         List<SysUser> findBizUser = findBizUser(loginUser, roleId);
         //使用set集合，避免重复数据
         for (SysUser sysUser : findBizUser) {
            Map<String,String> userMap = new HashMap<>();
            userMap.put("id", sysUser.getPid()+"");
            userMap.put("realName", sysUser.getRealName());
            userMap.put("userName", sysUser.getUserName());
            handleUserSet.add(userMap);
         }
      }
      return handleUserSet;
   }
   /**
    *@author:liangyanjun
    *@time:2017年2月6日下午5:14:06
    */
   @Override
   public List<SysUser> findBizUserByRoleCode(SysUser user, String roleCode) throws ThriftServiceException, TException {
       SysRole roleByCode = systemUserRoleMapper.getRoleByCode(roleCode);
       return findBizUser(user, roleByCode.getPid());
   }

	@Override
	public List<SysUser> findBizUser(SysUser user, int roleId)
			throws ThriftServiceException, TException {

		HashMap<String, Integer> params = new HashMap<String, Integer>();
		// 保存已查找的机构
		List<SysOrgInfo> findList = new ArrayList<SysOrgInfo>();
		// 获取当前用所在机构
		SysOrgInfo currenOrgInfo = sysOrgInfoMapper.getSysOrgInfo(user
				.getOrgId());

		params.put("orgId", user.getOrgId());
		params.put("roleId", roleId);

		// 根据机构编号和角色编号获取当前机构下对应角色的用户列表
		List<SysUser> roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);

		findList.add(currenOrgInfo);

		// 在当前机构中没有找到此角色对应的用户时,继续查找上级机构直属角色用户
		if (roleUserList == null || roleUserList.isEmpty()) {

			params.put("orgId", currenOrgInfo.getParentId());
			// 查找当前用户的父级机构直属角色用户
			roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);

			// 当父级机构直属角色用户不存在时,查找父级机构下的直属子机构，但不查找当前用户所在的机构
			if (roleUserList == null || roleUserList.isEmpty()) {

				SysOrgInfo currentOrgParent = sysOrgInfoMapper.getSysOrgInfo(currenOrgInfo.getParentId());

				findList.add(currentOrgParent);

				SysOrgInfo tmpOrg = new SysOrgInfo();

				tmpOrg.setParentId(currenOrgInfo.getParentId());
				//tmpOrg.setLayer(currentOrgParent.getLayer());
				
				// 查找兄弟机构,根据当前部门的父节点查询
				List<SysOrgInfo> brotherOrgList = sysOrgInfoMapper.listSysOrgByLayer(tmpOrg);

				if (brotherOrgList != null && brotherOrgList.size() > 1) {

					// 去除当前用户所在机构
					brotherOrgList.remove(currenOrgInfo);
					
					// 循环遍历兄弟机构
					for (SysOrgInfo org : brotherOrgList) {

						params.put("orgId", org.getId());
						roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);
						findList.add(org);
						
						// 找到所需角色对应的用户后不再查找
						if (roleUserList != null && !roleUserList.isEmpty()) {

							return roleUserList;
						}
					}
				}
				// 若当前用户所在机构无兄弟机构时,直接查找父级机构的上级机构
				if(roleUserList == null || roleUserList.isEmpty()){

					SysOrgInfo tempOrg =  new SysOrgInfo();
					SysOrgInfo pOrg =  new SysOrgInfo();
				
					// 查找父级机构的上级机构
					SysOrgInfo parentUpOrg = sysOrgInfoMapper.getSysOrgInfo(currentOrgParent.getParentId());
					params.put("orgId", parentUpOrg.getId());
					roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);
					findList.add(parentUpOrg);
					//祖父机构下没有对应的角色用户时,获取祖父机构兄弟机构
					if(roleUserList == null || roleUserList.isEmpty()){
						
						tempOrg.setParentId(parentUpOrg.getParentId());
						//tempOrg.setLayer(parentUpOrg.getLayer()-1);
						// 查找祖父机构的兄弟机构
						List<SysOrgInfo> gradeBrotherOrgList = sysOrgInfoMapper.listSysOrgByLayer(tempOrg);
						//删除已查找过的机构
						gradeBrotherOrgList.removeAll(findList);
						
						for(SysOrgInfo org : gradeBrotherOrgList){
							
							findList.add(org);
							
							params.put("orgId", org.getId());
							
							roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);
							// 查到后直接返回,不再继续查找
							if (roleUserList != null && !roleUserList.isEmpty()) {

								return roleUserList;
							}
							
							//查找gradeBrother的所有子节点
							List<SysOrgInfo> subGradeBrotherOrgList = sysOrgInfoMapper.listSysOrgByParentId(org.getId());
							subGradeBrotherOrgList.removeAll(findList);
							//查找祖父节点的兄弟节点下子节点机构是否存在对应角色用户
							for(SysOrgInfo sub : subGradeBrotherOrgList){
								
								params.put("orgId",sub.getId());
								
								roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);
								
								// 查到后直接返回,不再继续查找
								if (roleUserList != null && !roleUserList.isEmpty()) {

									return roleUserList;
								}
							}
						}
						pOrg.setParentId(parentUpOrg.getParentId());
						pOrg.setLayer(2);
						
						//获取当前用户的终极机构,但不包含根节点(Q房集团)
						List<SysOrgInfo> allParentOrgList = sysOrgInfoMapper.listSysParentOrg(tempOrg);
						
						for(SysOrgInfo root : allParentOrgList){
							
							params.put("orgId", root.getId());
							roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);
							// 查到后直接返回,不再继续查找
							if (roleUserList != null && !roleUserList.isEmpty()) {

								return roleUserList;
							}
						}
						//获取根节点(Q房集团)
						pOrg.setParentId(Constants.COMMONE_ZERO);
						pOrg.setLayer(1);
						List<SysOrgInfo> qfangGroupList = sysOrgInfoMapper.listSysOrgByLayer(pOrg);
						params.put("orgId", qfangGroupList.get(0).getId());
						//获取集团直属角色用户
						roleUserList = sysUserMapper.findBizUserByOrgIdAndRoleId(params);
					}
				}
			}
		}
		
		return roleUserList;
	}

	/**
	 * 根据流程实例ID查询任务id
	 *@author:liangyanjun
	 *@time:2016年5月7日上午10:06:25
	 */
   @Override
   public String getTaskIdByProcessInstanceId(String processInstanceId) throws TException {
      return getTaskByProcessInstanceId(processInstanceId).getId();
   }
   
   /**
    * 根据流程实例ID查询任务基本信息map
    *@author:liangyanjun
    *@time:2017年12月15日下午2:35:32
    */
   @Override
   public Map<String, String> getTaskMapByProcessInstanceId(String processInstanceId) throws TException {
      Task task = getTaskByProcessInstanceId(processInstanceId);
      if (task==null) {
         return new HashMap<String, String>();
      }
      HashMap<String, String> map = new HashMap<>();
      map.put("id", task.getId());
      map.put("name", task.getName());
      map.put("taskDefinitionKey", task.getTaskDefinitionKey());
      return map;
   }
   /**
    * 执行流程(该方法使用移动端工作流)
    *@author:liangyanjun
    *@time:2016年6月2日上午9:54:53
    *@param userId
    *@param refId
    *@param projectId
    *@param processDefinitionKey
    *@param startTaskDefKey
    *@param message
    *@throws ThriftServiceException
    *@throws TException
    */
   @Override
   public int executeFlow(int userId, int refId, int projectId, String processDefinitionKey, String startTaskDefKey, String message) throws TException {
      try {
         SysUser sysUser = sysUserMapper.getSysUserByPid(userId + "");
         String userName = sysUser.getUserName();
         Map<String, String> paramMap = new HashMap<String, String>();
         paramMap.put("userName", userName);
         paramMap.put("userId", ""+userId);
         paramMap.put("projectId", ""+projectId);
         paramMap.put("refId", ""+refId);
         paramMap.put("processDefinitionKey", processDefinitionKey);
         paramMap.put("message", "");
         WorkflowProjectVo vo = new WorkflowProjectVo();
         vo.setRefId(refId);
         vo.setProjectId(projectId);
         vo.setProcessDefinitionKey(processDefinitionKey);
         WorkflowProjectVo w = findWorkflowProject(vo);
         // 启动流程
         if (w == null || StringUtil.isBlank(w.processDefinitionKey)) {
            paramMap.put("startTaskDefKey", startTaskDefKey);
            String processInstanceId = executeStartOfCommon(paramMap);
            // 记录项目与流程的关系
            WorkflowProjectVo workflowProjectVo = new WorkflowProjectVo();
            workflowProjectVo.setRefId(refId);
            workflowProjectVo.setProjectId(projectId);
            workflowProjectVo.setWorkflowInstanceId(processInstanceId);
            workflowProjectVo.setProcessDefinitionKey(processDefinitionKey);
            workflowProjectMapper.insertWorkflowProject(workflowProjectVo);
            return 1;
         }
         // 执行下一步任务
         String instanceId = w.getWorkflowInstanceId();
         Task task = getTaskByProcessInstanceId(instanceId);
         paramMap.put("taskId", task.getId());
         paramMap.put("workflowTaskDefKey", task.getTaskDefinitionKey());
         paramMap.put("executionId", instanceId);
         List<String> nextUserList = getNextUserList(paramMap);
         executeComplete(paramMap, nextUserList, new HashMap<String, String>());
      } catch (Exception e) {
         logger.error("执行流程失败：入参：userId:", userId + ",refId:" + refId + ",processDefinitionKey:" + processDefinitionKey + ",startTaskDefKey:" + startTaskDefKey+ ",message:" + message + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return 1;
   }

}
