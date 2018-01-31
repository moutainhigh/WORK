/**
 * @Title: TaskController.java
 * @Package com.xlkfinance.bms.web.controller.task
 * @Description: 任务管理控制器
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:55:27
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.WorkflowIdConstant;
import com.xlkfinance.bms.common.exception.WorkFlowExistException;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.task.TaskService;
import com.xlkfinance.bms.rpc.task.TaskSettingVo;
import com.xlkfinance.bms.rpc.workflow.EndTaskPageVO;
import com.xlkfinance.bms.rpc.workflow.TaskHistoryDto;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.rpc.workflow.WorkflowVo;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;
import com.xlkfinance.bms.web.shiro.ShiroUser;

@Controller
@RequestMapping("/taskController")
public class TaskController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(TaskController.class);

	/**
	 * @Description: 任务管理页面
	 * @return
	 * @author: Dai.jingyu
	 * @date: 2015年2月10日 上午11:21:38
	 */
	@RequestMapping(value = "workflowManagePage")
	public String taskManagePage() {
		return "task/workflowManage";
	}

	/**
	 * @Description: 以结任务页面
	 * @return String
	 * @author: Dai.jingyu
	 * @date: 2015年2月10日 上午11:21:22
	 */
	@RequestMapping(value = "endTaskPage")
	public String endTaskPage() {
		return "task/endTask";
	}

	/**
	 * @Description: 我的任务页面
	 * @return String
	 * @author: Dai.jingyu
	 * @date: 2015年2月10日 上午11:21:01
	 */
	@RequestMapping(value = "myAgentTaskPage")
	public String myAgentTaskPage() {
		return "task/myAgentTask";
	}

	/**
	 * @Description: 任务设定
	 * @return String
	 * @author: Dai.jingyu
	 * @date: 2015年2月10日 上午11:20:31
	 */
	@RequestMapping(value = "taskAgentSettingPage")
	public String taskAgentSettingPage() {
		return "task/taskAgentSetting";
	}

	/**
	 * @Description: 执行流程
	 * @param workflowVo
	 * @param response
	 * @author: Dai.jingyu
	 * @date: 2015年2月9日 下午4:27:54
	 */
	@RequestMapping(value = "executeFlow")
	public void executeFlow(WorkflowVo workflowVo, HttpServletResponse response,HttpServletRequest request) {
		Json j = super.getSuccessObj();
		ShiroUser user = getShiroUser();
		String userName = user.getUserName();
		int userId = user.getPid();
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		BaseClientFactory userClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			SysUserService.Client userClient = (SysUserService.Client) userClientFactory.getClient();
			List<SysUser> sysUsers = null;
			// 根据用户Id集合查询用户列表
			if (null != workflowVo.getUserIds() && workflowVo.getUserIds().size() > 0 && !"".equals(workflowVo.getUserIds().get(0))) {
				sysUsers = userClient.getSysUserByPids(workflowVo.getUserIds());
			}
			//下一流程节点处理人
			List<String> nextUserList = new ArrayList<String>();
			if (null != sysUsers) {
				for (SysUser u : sysUsers) {
					nextUserList.add(u.getUserName());
				}
			}
			Map<String,String> paramMap = new HashMap<String, String>();
			paramMap.put("userName", userName);
			paramMap.put("userId", ""+userId);
			paramMap.put("taskId", workflowVo.getTaskId());
			paramMap.put("projectId", ""+workflowVo.getProjectId());
			paramMap.put("refId", ""+workflowVo.getRefId());
			paramMap.put("processDefinitionKey", workflowVo.getWorkflowId());
			paramMap.put("workflowTaskDefKey", workflowVo.getWorkflowTaskDefKey());
			paramMap.put("message", workflowVo.getIdea());
			// 流程驳回
			if (null != workflowVo.getDestTaskId() && workflowVo.getDestTaskId().trim().length() > 0) {
				paramMap.put("destTaskId", workflowVo.getDestTaskId());
				paramMap.put("isVetoProject", workflowVo.getVetoProject());
				// 流程驳回
				int res = workClient.executeTurnDown(paramMap);
				if (res != 1) {
					j.getHeader().put("success", false);
					j.getHeader().put("msg", "驳回失败！");
				}
			// 完成当前流程节点进入下以流程节点
			} else if (null != workflowVo.getTaskId() && workflowVo.getTaskId().trim().length() > 0) {
				//if(!"creditFreezeOrThawRequestProcess".equals(workflowVo.getWorkflowId())){
				if (null != workflowVo.getVetoProject() & workflowVo.getVetoProject().trim().length() > 0) {
					workflowVo.setCommitToTask(workflowVo.getVetoProject());
					paramMap.put("isVetoProject", workflowVo.getVetoProject());
				}
				//}
				Map<String, String> map = new HashMap<String, String>();
				if (null != workflowVo.getCommitToTask()) {
					if(!"TASK_FINANCIL_REPAYMENT".equals(workflowVo.getCommitToTask())){
						map.put("flow", workflowVo.getCommitToTask());
					}
				}
				// 完成当前流程节点进入下以流程节点
				workClient.executeComplete(paramMap,nextUserList, map);
			// 启动流程
			} else {
				// 启动流程
				workClient.executeStart(paramMap,nextUserList);
			}
			// 是否发送
			if (workflowVo.ifSms) {
				// 手机号码。多个手机号码以英文逗号隔开“,”
				StringBuilder mobile = new StringBuilder();
				if (sysUsers!=null&&!sysUsers.isEmpty()) {
   				for (int i = 0; i < sysUsers.size(); i++) {
   					mobile.append(sysUsers.get(i).getPhone());
   					if ((i + 1) < sysUsers.size()) {
   						mobile.append(",");
   					}
   				}
				}
				// 短信内容
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("taskNodeName", workflowVo.taskNodeName);
				map.put("sysDate", DateUtil.format(new Date()));
				// 短信内容
				String msg = templateParsing(map, "sms.ftl");
				if (null != msg) {
					// 发送短信
					sendSms(mobile.toString(), msg);
				} else {
					// TODO: 记录数据库异常日志
				}
			}
			// 是否发送邮件
			if (workflowVo.ifEmail) {
				// 主题
				String subject = "【小贷业务审批】" + workflowVo.taskNodeName;
				// 邮件内容
				// String htmlText =
				// "您有一个审批任务。前一审批人的意见如下："+workflowVo.idea+"请您尽快处理。";
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("idea", workflowVo.idea);
				map.put("sysDate", DateUtil.format(new Date()));
				map.put("subject", workflowVo.taskNodeName);
				// 邮件内容
				String htmlText = templateParsing(map, "email.ftl");
				if (sysUsers!=null&&!sysUsers.isEmpty()) {
               for (SysUser u : sysUsers) {
                  // 邮箱号
                  String emailAddress = u.getMail();
                  if (null != htmlText) {
                     // 发送邮件
                     sendMail(emailAddress, subject, htmlText);
                  } else {
                     // TODO: 记录数据库异常日志
                  }
               }
				}
			}
		} catch (WorkFlowExistException e){
         j.getHeader().put("success", false);
         j.getHeader().put("msg", "申请已提交，请勿重复提交");
		}catch (Exception e) {
			e.printStackTrace();
			logger.debug("TaskController.executeFlow Exception" + ExceptionUtil.getExceptionMessage(e));
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "提交异常,请联系管理员");
		} finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (null != userClientFactory) {
				try {
					userClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}

	/**
	 * @Description: 任务处理
	 * @param taskVo
	 * @param response
	 * @return String
	 * @author: Dai.jingyu
	 * @date: 2015年2月9日 下午4:24:36
	 */
	@RequestMapping(value = "taskDispose", method = RequestMethod.POST)
	public String taskDispose(TaskVo taskVo, HttpServletResponse response) {
		String loanRequest = taskVo.getWorkflowProcessDefkey();
		if (null != loanRequest) {
			String[] s = loanRequest.split(":");
			if (WorkflowIdConstant.LOAN_REQUEST_PROCESS.equals(s[0])) {
				System.out.println(2);
				return "redirect:/taskController/searchTaskList.action";
			}
		}
		return "redirect:/beforeLoanController/navigation.action";
	}

	/**
	 * @Description: 根据当前用户查询任务
	 * @param taskVo
	 * @param response
	 * @author: Dai.jingyu
	 * @date: 2015年2月9日 下午4:23:19
	 */
	@RequestMapping(value = "searchTaskList",method = RequestMethod.POST)
	public void searchTaskList(TaskVo taskVo, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TaskVo> tasks = new ArrayList<TaskVo>();
		// 工作流
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		// 任务
		BaseClientFactory taskClientFactory = getFactory(BusinessModule.MODUEL_TASK, "TaskService");
		ShiroUser user = getShiroUser();
		String userName = user.getUserName();
		taskVo.setTaskUserName(userName);
		
		if(taskVo.getPage() <= 0 && taskVo.getRows()<=0){
			taskVo.setPage(1);
			taskVo.setRows(1000);
		}
		
		int total = 0;
		List<TaskVo> workTasks = null;
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			// 获取工作流中的当前用户任务列表
			workTasks = workClient.queryWorkFlowTodoTask(taskVo);
			total = workClient.queryWorkFlowTodoTaskTotal(taskVo);
			// 获取BIZ_TASK表中的当前用户任务列表
			TaskService.Client client = (TaskService.Client) taskClientFactory.getClient();
			List<TaskVo> bizTasks = client.getTaskVosByTaskVo(taskVo);
			if(null != bizTasks)
			{
			    total+=bizTasks.size();
				for(TaskVo vo: bizTasks)
				{
					vo.setWorkfloPprojectName(vo.getTaskName());
					vo.setTaskName(vo.getWorkflowName());
				}	
			}
			if(null != workTasks){
				tasks.addAll(workTasks);
			}
			if(null != bizTasks){
				tasks.addAll(bizTasks);
			}
		} catch (Exception e) {
				logger.error("TaskController.seachTaskList Exception" + e.getMessage());
		} finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (null != taskClientFactory) {
				try {
					taskClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("rows", tasks);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	 * @Description: 根据当前用户查询历史任务
	 * @param taskVo
	 * @param response
	 * @author: Dai.jingyu
	 * @date: 2015年2月9日 下午4:21:26
	 */
	@RequestMapping(value = "searchHiTaskList", method = RequestMethod.POST)
	public void searchHiTaskList(String workflowInstanceId,int page,int rows, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TaskHistoryDto> taskHistorys = new ArrayList<TaskHistoryDto>();
		int total = 0;
		// 工作流
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		// 任务
		BaseClientFactory taskClientFactory = getFactory(BusinessModule.MODUEL_TASK, "TaskService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			if(!StringUtil.isBlank(workflowInstanceId)  && !workflowInstanceId.equals("-1")){
				// 获取工作流中的当前用户任务列表
				taskHistorys = workClient.queryWorkFlowHistory(workflowInstanceId,page,rows);
				total = workClient.queryWorkFlowHistoryCount(workflowInstanceId);
			}
			// 获取BIZ_TASK表中的当前用户任务列表
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("TaskController.searchHiTaskList Exception" + e.getMessage());
			}
		} finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
			if (null != taskClientFactory) {
				try {
					taskClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("rows", taskHistorys);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	  * @Description: 查询已结任务列表
	  * @param response
	  * @author: Dai.jingyu
	  * @date: 2015年3月5日 上午10:45:47
	  */
	@RequestMapping(value = "searchEndTaskList", method = RequestMethod.POST)
	public void searchEndTaskList(HttpServletResponse response,EndTaskPageVO endTaskPageVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<TaskHistoryDto> historyDtos = new ArrayList<TaskHistoryDto>();
		// 工作流
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		ShiroUser user = getShiroUser();
		int total = 0;
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			endTaskPageVO.setUserName(user.getUserName());
			// 获取工作流中的当前用户已结任务列表
			historyDtos = workClient.selectWorkflowHistoryList(endTaskPageVO);
			//historyDtos = workClient.queryWorkFlowFinishedHistory(endTaskPageVO);
			total = workClient.selectWorkflowHistoryListTotal(endTaskPageVO);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("TaskController.searchEndTaskList Exception" + e.getMessage());
			}
		} finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("rows", historyDtos);
		map.put("total", total);
		outputJson(map, response);
	}

	/**
	  * @Description: 过滤驳回的选择节点数据
	  * @param taskId
	  * @param response
	  * @author: Dai.jingyu
	  * @date: 2015年3月5日 上午10:44:10
	  */
	@RequestMapping(value = "filterTurnDownTaskNodes", method = RequestMethod.POST)
	public void filterTurnDownTaskNodes(String taskId, HttpServletResponse response) {
		List<SysLookupVal> list =  new ArrayList<SysLookupVal>();
		// 工作流
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			SysLookupVal s = new SysLookupVal();
			s.setPid(-1);
			s.setLookupVal("");
			s.setLookupDesc("--请选择--");
			list.add(s);
			list.addAll(workClient.filterTurnDownTaskNodes(taskId));
		} catch (ThriftException e) {
				logger.error("TaskController.filterTurnDownTaskNodes Exception" + e.getMessage());
		} catch (ThriftServiceException e) {
				logger.error(e.getMessage());
		} catch (TException e) {
				logger.error(e.getMessage());
		} finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(list, response);
	}

	/**
	 * @Description: 新增待办设定
	 * @param settingVo
	 * @param response
	 * @author: Dai.jingyu
	 * @date: 2015年2月9日 下午4:20:37
	 */
	@RequestMapping(value = "insertTaskSetting", method = RequestMethod.POST)
	public void insertTaskSetting(TaskSettingVo settingVo, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory taskClientFactory = getFactory(BusinessModule.MODUEL_TASK, "TaskService");
		try {
			TaskService.Client client = (TaskService.Client) taskClientFactory.getClient();
			ShiroUser user = getShiroUser();
			settingVo.setOldUserId(user.getPid());
			client.insertTaskSetting(settingVo);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("TaskController.insertTaskSetting Exception" + e.getMessage());
				j.getHeader().put("success", false);
				j.getHeader().put("msg", e.getMessage());
			}
		} finally {
			if (null != taskClientFactory) {
				try {
					taskClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}

	/**
	 * @Description: 查询当前用户 的设定任务
	 * @param response
	 * @author: Dai.jingyu
	 * @date: 2015年2月9日 下午4:19:53
	 */
	@RequestMapping(value = "searchTaskSettingList")
	public void searchTaskSettingList(HttpServletResponse response) {
		ShiroUser user = getShiroUser();
		Map<String, Object> map = new HashMap<String, Object>();
		List<TaskSettingVo> settingVos = new ArrayList<TaskSettingVo>();
		BaseClientFactory taskClientFactory = getFactory(BusinessModule.MODUEL_TASK, "TaskService");
		try {
			TaskService.Client client = (TaskService.Client) taskClientFactory.getClient();
			settingVos = client.findTaskSettingsByUserId(user.getPid());
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("TaskController.searchTaskSettingList Exception" + e.getMessage());
			}
		} finally {
			if (null != taskClientFactory) {
				try {
					taskClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		map.put("rows", settingVos);
		map.put("count", settingVos.size());
		outputJson(map, response);
	}

	/**
	 * @Description: 删除任务待办 ， 根据pid
	 * @param pid
	 * @param response
	 * @author: Daijingyu
	 * @date: 2015年2月9日 下午4:16:55
	 */
	@RequestMapping(value = "deleteTaskSetting", method = RequestMethod.POST)
	public void deleteTaskSetting(Integer pid, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory taskClientFactory = getFactory(BusinessModule.MODUEL_TASK, "TaskService");
		try {
			TaskService.Client client = (TaskService.Client) taskClientFactory.getClient();
			client.deleteTaskSetting(pid);
			outputJson(j, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("TaskController.searchTaskSettingList Exception" + e.getMessage());
				j.getHeader().put("success", false);
				j.getHeader().put("msg", e.getMessage());
			}
		} finally {
			if (null != taskClientFactory) {
				try {
					taskClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	  * @Description: 根据pid集合 批量删除任务设定
	  * @param pids
	  * @param response
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 上午11:33:12
	 */
	@RequestMapping(value = "deleteTaskSettings", method = RequestMethod.POST)
	public void deleteTaskSettings(@RequestParam(value="pids[]") int[] pids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory taskClientFactory = getFactory(BusinessModule.MODUEL_TASK, "TaskService");
		try {
			TaskService.Client client = (TaskService.Client) taskClientFactory.getClient();
			List<Integer> list = new ArrayList<Integer>();
			for (Integer i : pids) {
				list.add(i);
			}
			client.deleteTaskSettings(list);
			outputJson(j, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("TaskController.searchTaskSettingList Exception" + e.getMessage());
				j.getHeader().put("success", false);
				j.getHeader().put("msg", e.getMessage());
			}
		} finally {
			if (null != taskClientFactory) {
				try {
					taskClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
