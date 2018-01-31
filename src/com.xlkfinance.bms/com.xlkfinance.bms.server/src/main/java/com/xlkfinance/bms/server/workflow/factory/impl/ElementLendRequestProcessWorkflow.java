/**
 * @Title: ElementLendRequestProcessWorkflow.java
 * @Package com.xlkfinance.bms.server.workflow.factory.impl
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络科技
 * 
 * @author: andrew
 * @date: 2016年2月27日 下午8:05:33
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.server.beforeloan.mapper.ElementLendMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * 要件借出审批处理流
  * @ClassName: ElementLendRequestProcessWorkflow
  * @Description: TODO
  * @author: andrew
  * @date: 2016年2月27日 下午8:05:52
 */
@Component("elementLendRequestProcessWorkflow")
@Scope("prototype")
public class ElementLendRequestProcessWorkflow extends WorkflowSpecialDispose{
	
	@SuppressWarnings("rawtypes")
	@Resource(name="elementLendMapper")
	private ElementLendMapper elementLendMapper;
	
	@Override
	public int updateProjectStatus() {
		int res = 2;
	    int pid = super.getBean().getRefId();
	    if (null != super.getBean().getIsReject()) {
			if ("task_ElementLendProcess".equals(super.getBean().getIsReject())) {
				elementLendMapper.updateLendStateByPid(pid, 1,DateUtil.format(new Date()));
			}
		}else if (null != super.getBean().getIsVetoProject() && !"".equals(super.getBean().getIsVetoProject()) && "refuse".equals(super.getBean().getIsVetoProject())) {
			elementLendMapper.updateLendStateByPid(pid, 1,DateUtil.format(new Date()));
		} else if ("task_ElementLendProcess".equals(super.getBean().getWorkflowTaskDefKey())) {
			elementLendMapper.updateLendStateByPid(pid, 2,DateUtil.format(new Date()));
			res = 2;
		}else if("task_Supervisor".equals(super.getBean().getWorkflowTaskDefKey())){
			elementLendMapper.updateLendStateByPid(pid, 3,DateUtil.format(new Date()));
			res = 2;
		}
		return res;
	}
	
	@Override
	public void resetProject(){
		//申请状态（申请中=1、审核中=2 已审批=3 已归还=4 已签收=5）
		elementLendMapper.updateLendStateByPid(super.getBean().getRefId(), 1,DateUtil.format(new Date()));
	}

}
