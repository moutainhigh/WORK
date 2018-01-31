package com.xlkfinance.bms.web.controller.workflow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.workflow.BizTaskAcctProHisVo;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.controller.task.TaskController;

@Controller
@RequestMapping("/workflowController")
public class WorkflowController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@RequestMapping(value = "loanWorkflowPage")
	public String loanWorkflowPage() {
		return "commonality/loanWorkflow";
	}

	@RequestMapping(value = "deployWorkFlowDef", method = RequestMethod.POST)
	public void deployWorkFlowDef(String zipFile, HttpServletResponse response) {
		// 发布工作流。
	}
	
	@RequestMapping(value="findWorkflowProject" , method=RequestMethod.GET)
	public void findWorkflowProject(WorkflowProjectVo vo, HttpServletResponse response){
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		WorkflowProjectVo w =null;
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			w = workClient.findWorkflowProject(vo);
			// 有效的工作流对象
			if(null != w && w.getPid()>0  ){
				String workflowTaskDefKey = workClient.getRunLastTaskKeyByWIId(w.getWorkflowInstanceId());
				w.setWorkflowTaskDefKey(workflowTaskDefKey);
			}
		} catch (Exception e) {
				logger.error("WorkflowController.findWorkflowProject Exception" + e.getMessage());
		}finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(w, response);
	}
	@RequestMapping(value = "isAllowStartWorkflowByProjectId")
	public void isAllowStartWorkflow(int projectId , HttpServletResponse response){
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			boolean falg = workClient.isAllowStartWorkflow(projectId);
			outputJson(falg, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("WorkflowController.selectTaskAcctProHis Exception" + e.getMessage());
			}
		}finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	@RequestMapping(value = "isAllowStartWorkflowByAcctId")
	public void isAllowStartWorkflowByAcctId(int acctId , HttpServletResponse response){
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			boolean falg = workClient.isAllowStartWorkflowByAcctId(acctId);
			outputJson(falg, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("WorkflowController.selectTaskAcctProHis Exception" + e.getMessage());
			}
		}finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	  * @Description: 根据项目ID查询流程状态  是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
	  * @param projectId 项目ID
	  * @param response 
	  * @author: JingYu.Dai
	  * @date: 2015年5月27日 下午4:39:59
	 */
	@RequestMapping(value = "getWorkflowStatusByProjectId",method=RequestMethod.POST)
	public void getWorkflowStatusByProjectId(int projectId , HttpServletResponse response){
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			int workflowStatus = workClient.getWorkflowStatusByProjectId(projectId);
			outputJson(workflowStatus, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("WorkflowController.selectTaskAcctProHis Exception" + e.getMessage());
			}
		}finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	@RequestMapping(value = "selectTaskAcctProHis")
	public void selectTaskAcctProHis(int projectId, String workflowId, HttpServletResponse response){
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			BizTaskAcctProHisVo acctProHisVo = new BizTaskAcctProHisVo();
			acctProHisVo.setProjectId(projectId);
			acctProHisVo.setWorkflowId(workflowId);
			int count = workClient.selectTaskAcctProHis(acctProHisVo);
			outputJson(count, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("WorkflowController.selectTaskAcctProHis Exception" + e.getMessage());
			}
		}finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	  * @Description: 根据流程Id集合  删除流程
	  * @param workflowInstanceIds 流程实例ID字符串多个以(,)英文逗号分割
	  * @param response
	  * @author: JingYu.Dai
	  * @date: 2015年5月21日 上午1:05:22
	 */
	@RequestMapping(value="deleteWorkflowInstanceIds",method=RequestMethod.POST)
	public void deleteWorkflow(String workflowInstanceIds,HttpServletResponse response){
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			workClient.deleteProcessInstances(workflowInstanceIds);
		} catch (Exception e) {
			outputJson("false",response);
			if (logger.isDebugEnabled()) {
				logger.error("WorkflowController.selectTaskAcctProHis Exception" + e.getMessage());
			}
		}finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson("true",response);
	}
	
	/**
	  * @Description: 根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务 
	  * @param workflowProcessDefkey 流程实例定义ID
	  * @param refId 引用ID
	  * @param response 
	  * @author: JingYu.Dai
	  * @date: 2015年6月2日 下午3:19:31
	  */
	@RequestMapping(value = "getTaskVoByWPDefKeyAndRefId" , method = RequestMethod.POST)
	public void getTaskVoByWPDefKeyAndRefId(String workflowProcessDefkey,int refId,HttpServletResponse response){
		BaseClientFactory workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		TaskVo taskVo = null;
		try {
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			taskVo = workClient.getTaskVoByWPDefKeyAndRefId(workflowProcessDefkey, refId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.error("WorkflowController.selectTaskAcctProHis Exception" + e.getMessage());
			}
		}finally {
			if (null != workClientFactory) {
				try {
					workClientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(taskVo, response);
	}
	
	@RequestMapping(value="queryAllRunTask" ,method = RequestMethod.POST)
	public void queryAllRunTask(TaskVo taskVo,HttpServletResponse response){
		Map<String, Object> map = new HashMap<String, Object>();
		List<TaskVo> tasks = new ArrayList<TaskVo>();
		// 工作流
		BaseClientFactory workClientFactory = null;
		int total = 0;
		try {
			workClientFactory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
			WorkflowService.Client workClient = (WorkflowService.Client) workClientFactory.getClient();
			// 获取工作流中的当前用户已结任务列表
			tasks = workClient.queryAllRunTask(taskVo);
			total = workClient.queryAllRunTaskTotal(taskVo);
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
		map.put("rows", tasks);
		map.put("total", total);
		outputJson(map, response);
	}
	/**
	 * 根据流程的key和项目id，查询是否有工作流在运行
	 * 有运行工作流=true
	 * 无运行工作流=false
	 *@author:liangyanjun
	 *@time:2016年3月4日下午5:55:35
	 *@param workflowProjectVo
	 *@param request
	 *@param response
	 *@return
	 */
   @RequestMapping(value = "/checkWorkFlowIsRun")
   public void checkWorkFlowIsRun(WorkflowProjectVo workflowProjectVo, HttpServletRequest request, HttpServletResponse response) {
	   BaseClientFactory factory = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
      try {
    	 WorkflowService.Client service = (WorkflowService.Client) factory.getClient();
         List<WorkflowProjectVo> list = service.getRunWorkflowProject(workflowProjectVo);
         if (list!=null&&!list.isEmpty()) {
            fillReturnJson(response, true, "提交失败，流程正在审核中，请审核完再提交");
            return;
         }
      } catch (Exception e) {
         e.printStackTrace();
      }finally{
    	  destroyFactory(factory);
      }
      fillReturnJson(response, false, "提交成功");
   }
}
