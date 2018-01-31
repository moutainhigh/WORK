/**
 * @Title: CooperationRequestProcessWrokflow.java
 * @Package com.xlkfinance.bms.server.workflow.factory.impl
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月12日 下午5:11:37
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory.impl;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.org.mapper.OrgAssetsCooperationInfoMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperatCompanyApplyInfMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * 合作申请工作流处理类
  * @ClassName: CooperationRequestProcessWrokflow
  * @author: baogang
  * @date: 2016年7月12日 下午5:11:45
 */
@Component("cooperationRequestProcessWorkflow")
@Scope("prototype")
public class CooperationRequestProcessWrokflow extends WorkflowSpecialDispose {

	private Logger logger = LoggerFactory.getLogger(CreditLoanRequestProcessWrokflow.class);
	
	@SuppressWarnings("rawtypes")
	@Resource
	private OrgCooperatCompanyApplyInfMapper orgCooperatMapper;
	
	@SuppressWarnings("rawtypes")
    @Resource(name = "orgAssetsCooperationInfoMapper")
    private OrgAssetsCooperationInfoMapper orgAssetsCooperationInfoMapper;
	
	@Override
	public int updateProjectStatus() throws TException {
		int res = 2;
		WorkflowSpecialBean bean = super.getBean();
		int cooperationId = bean.getProjectId();//合作申请ID
		int userId = bean.getHandleAuthorId();//操作人
		OrgCooperatCompanyApplyInf orgCooperatCompanyApplyInf = orgCooperatMapper.getById(cooperationId);
		orgCooperatCompanyApplyInf.setUpdateId(userId);
		if(!checkOrgCompanyApply(orgCooperatCompanyApplyInf)){
			throw new ThriftServiceException(ExceptionCode.SYSUSER_UPDATE, "合作信息不全，无法提交!");
		}
		
		//驳回
		if (null != bean.getIsReject()) {
			//驳回最初的节点，状态变为审核不通过
			if ("task_CooperationRequest".equals(bean.getIsReject())) {
				orgCooperatCompanyApplyInf.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_2);
				orgCooperatMapper.updateApplyStatus(orgCooperatCompanyApplyInf);
			}
			
		}else if (null != bean.getIsVetoProject() && !"".equals(bean.getIsVetoProject()) && "refuse".equals(bean.getIsVetoProject())) {
			//审核不通过
			orgCooperatCompanyApplyInf.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_5);
			orgCooperatMapper.updateApplyStatus(orgCooperatCompanyApplyInf);
		}else if("task_CooperationRequest".equals(bean.getWorkflowTaskDefKey())){
			//审核开始
			orgCooperatCompanyApplyInf.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_3);
			orgCooperatMapper.updateApplyStatus(orgCooperatCompanyApplyInf);
		}else if("task_WindControlCommissionCheck".equals(bean.getWorkflowTaskDefKey())){
			//审核结束
			orgCooperatCompanyApplyInf.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_4);
			orgCooperatMapper.updateApplyStatus(orgCooperatCompanyApplyInf);
			//修改合作状态为已合作
			int orgId = orgCooperatCompanyApplyInf.getOrgId();
			OrgAssetsCooperationInfo orgAssets = new OrgAssetsCooperationInfo();
			orgAssets.setPid(orgId);
			//合作待确认
			orgAssets.setCooperateStatus(AomConstant.ORG_COOPERATE_STATUS_4);
			orgAssetsCooperationInfoMapper.updateCooperateStatus(orgAssets );
		}
		
		return res;
	}
	
	@Override
	public void resetProject(){
		WorkflowSpecialBean bean = super.getBean();
		int cooperationId = bean.getProjectId();//合作申请ID
		int userId = bean.getHandleAuthorId();//操作人
		OrgCooperatCompanyApplyInf orgCooperatCompanyApplyInf = orgCooperatMapper.getById(cooperationId);
		orgCooperatCompanyApplyInf.setUpdateId(userId);
		orgCooperatCompanyApplyInf.setApplyStatus(AomConstant.ORG_COOPERATE_APPLY_STATUS_5);
		orgCooperatMapper.updateApplyStatus(orgCooperatCompanyApplyInf);
	}
	
	/**
	 * 判断合作申请信息是否已填写完毕
	  * @param apply
	  * @return
	  * @author: baogang
	  * @date: 2016年8月24日 上午10:37:13
	 */
	private boolean checkOrgCompanyApply(OrgCooperatCompanyApplyInf apply){
		if(apply != null && apply.getAssureMoney() >0 && apply.getAvailableLimit() >0 ){
			return true;
		}
		return false;
	}
}
