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

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.beforeloan.CreditLimitRecord;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
  * @ClassName: CreditFreezeOrThawRequestProcessWrokflow
  * @Description: 额度冻结与解冻工作流特殊处理类
  * @author: JingYu.Dai
  * @date: 2015年4月3日 下午5:53:42
 */
@Component("creditFreezeOrThawRequestProcessWrokflow")
@Scope("prototype")
public class CreditFreezeOrThawRequestProcessWrokflow extends WorkflowSpecialDispose{
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateProjectStatus() {
		int res = 2;
		//否决
		if (null != super.getBean().getIsVetoProject() && !"".equals(super.getBean().getIsVetoProject()) && "refuse".equals(super.getBean().getIsVetoProject())) {
		
		// 流程开始
		}else if("task_CreditFreezeOrThawRequest".equals(super.getBean().getWorkflowTaskDefKey())){
			res = 2;
			
		//流程已结束
		}else if("task_GeneralManagerReview".equals(super.getBean().getWorkflowTaskDefKey())){
			//总经理审核通过后更改状态 
			CreditLimitRecord creditLimitRecord = (CreditLimitRecord) creditsDTOMapper.selectByPrimaryKey(super.getBean().getRefId());
			creditLimitRecord.setStatus(1);
			creditLimitRecord.setCreDttm(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
			creditsDTOMapper.updateByPrimaryKey(creditLimitRecord);
			res = 3;
		}
		return res;
	}
	@Override
	public void resetProject(){}
}
