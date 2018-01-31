/**
 * @Title: Workflow.java
 * @Package com.xlkfinance.bms.web.controller.task.factory
 * @Description: 流程特殊处理工厂包
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: JingYu.Dai
 * @date: 2015年4月3日 下午4:40:02
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.workflow.BizTaskAcctProHisVo;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

/**
  * @ClassName: IWorkflow
  * @Description: 工作流特殊处理接口
  * @author: JingYu.Dai
  * @date: 2015年4月3日 下午4:51:07
 */
@SuppressWarnings("unchecked")
@Component("workflowSpecialDispose")
@Scope("prototype")
public abstract class WorkflowSpecialDispose {
	
	private WorkflowSpecialBean bean;	//工作流特殊处理参数类
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "workflowProjectMapper")
	private WorkflowProjectMapper workflowProjectMapper;
	

	/**
	  * @Description: 更改项目中的状态
	  * @return int
	  * @author: JingYu.Dai
	 * @throws TException 
	 * @throws ThriftServiceException 
	  * @date: 2015年4月3日 下午5:46:11
	  */
	public int updateProjectStatus() throws ThriftServiceException, TException{
		return 0;
	}
	
	/**
	  * @Description: 重置项目 具体看实现类
	  * @author: JingYu.Dai
	  * @date: 2015年7月9日 下午5:08:34
	 */
	public void resetProject(){}

   /**
    * 处理分支
    *@author:liangyanjun
    *@time:2016年4月21日上午10:01:57
    *@param task
    *@param taskVariables
    */
	public void handleBranch(Task task,Map<String, Object> taskVariables){}
	
	/**
	  * @Description: 是否有启动的流程   状态(0：没有流程启动、1：利率变更、2：提前还款、3：费用减免、4：展期、5：违约、6：挪用)
	  * @param projectId 项目ID
	  * @param workflowStatus 流程状态
	  * @return int 受影响行数
	  * @author: JingYu.Dai
	  * @date: 2015年5月27日 上午11:13:27
	 */
	public int  updateWorkflowStatus(int projectId,int workflowStatus){
		Map<String,Integer> map = new HashMap<String, Integer>();
		Integer loanId = loanMapper.getLoanIdByProjectId(projectId);
		map.put("workflowStatus", workflowStatus);
		map.put("loadId", loanId);
		if(loanId != null && loanId>0){
			return loanMapper.updateWorkflowStatus(map);
		}
		return 0;
	}
	
	/**
	  * @Description: 记录流程状态
	  * @param projectId 项目ID
	  * @param status 状态(1:流程执行中，2：流程已结束)
	  * @param workflowProcessDefkey (流程定义ID)
	  * @author: JingYu.Dai
	  * @date: 2015年5月13日 上午10:32:07
	 */
	public void recordWorkflowStatus(int projectId , int status , String workflowProcessDefkey){
		BizTaskAcctProHisVo hisVo = new BizTaskAcctProHisVo(); 
		hisVo.setProjectId(projectId);
		hisVo.setProjectWorkflowStatus(status);
		hisVo.setWorkflowId(workflowProcessDefkey);
		hisVo.setEndDt(DateUtil.format(new Date()));
		//修改流程记录数据
		workflowProjectMapper.updateTaskAcctProHisPWStatus(hisVo);
	}
	
	public WorkflowSpecialBean getBean() {
		return bean;
	}

	public void setBean(WorkflowSpecialBean bean) {
		this.bean = bean;
	}

}
