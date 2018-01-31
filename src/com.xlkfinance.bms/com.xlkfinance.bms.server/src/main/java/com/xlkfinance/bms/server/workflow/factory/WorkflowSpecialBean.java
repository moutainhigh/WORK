/**
 * @Title: WorkflowSpecialBean.java
 * @Package com.xlkfinance.bms.server.workflow
 * @Description: 流程特殊处理工厂包
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: JingYu.Dai
 * @date: 2015年4月13日 上午10:45:50
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory;

/**
 * @ClassName: WorkflowSpecialBean
 * @Description: 工作流特殊处理参数类
 * @author: JingYu.Dai
 * @date: 2015年4月13日 上午10:46:40
 */
public class WorkflowSpecialBean {

	private String workflowTaskDefKey; // 任务节点定义ID
	private String workflowProcessDefkey; // 流程实例定义ID
	private int projectId; // 项目ID
	private int refId; // 引用ID
	private String isVetoProject; // 项目是否否决
	private String isReject; // 是否是驳回
	private String message;//处理意见
	private int handleAuthorId;//处理人Id

	public String getWorkflowProcessDefkey() {
		return workflowProcessDefkey;
	}

	public void setWorkflowProcessDefkey(String workflowProcessDefkey) {
		this.workflowProcessDefkey = workflowProcessDefkey;
	}

	public String getWorkflowTaskDefKey() {
		return workflowTaskDefKey;
	}

	public void setWorkflowTaskDefKey(String workflowTaskDefKey) {
		this.workflowTaskDefKey = workflowTaskDefKey;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public String getIsVetoProject() {
		return isVetoProject;
	}

	public void setIsVetoProject(String isVetoProject) {
		this.isVetoProject = isVetoProject;
	}

	public int getRefId() {
		return refId;
	}

	public void setRefId(int refId) {
		this.refId = refId;
	}

	public WorkflowSpecialBean() {
	}

	public String getIsReject() {
		return isReject;
	}

	public void setIsReject(String isReject) {
		this.isReject = isReject;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getHandleAuthorId() {
		return handleAuthorId;
	}

	public void setHandleAuthorId(int handleAuthorId) {
		this.handleAuthorId = handleAuthorId;
	}

	public WorkflowSpecialBean(String workflowTaskDefKey, int projectId,
			int refId, String isVetoProject, String workflowProcessDefkey,
			String isReject,String message,int handleAuthorId) {
		this.isVetoProject = isVetoProject;
		this.projectId = projectId;
		this.workflowTaskDefKey = workflowTaskDefKey;
		this.refId = refId;
		this.workflowProcessDefkey = workflowProcessDefkey;
		this.isReject = isReject;
		this.message = message;
		this.handleAuthorId = handleAuthorId;
	}
	
	public WorkflowSpecialBean(int projectId, int refId, String workflowProcessDefkey){
		this.workflowProcessDefkey = workflowProcessDefkey;
		this.refId = refId;
		this.projectId = projectId;
	}
}
