/**
 * @Title: CreditLoanRequestProcessWrokflow.java
 * @Package com.xlkfinance.bms.server.workflow.factory.impl
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年2月19日 上午9:19:20
 * @version V1.0
 */
package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * 赎楼项目审批处理类（万通展期）
  * @ClassName: ForeLoanRequestProcessWrokflow
  * @author: Administrator
  * @date: 2016年2月23日 下午2:12:48
 */
@Component("foreExtensionRequestProcessWrokflow")
@Scope("prototype")
public class ForeExtensionRequestProcessWrokflow extends WorkflowSpecialDispose{
	private Logger logger = LoggerFactory.getLogger(ForeExtensionRequestProcessWrokflow.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;

	
	@Resource(name = "financeHandleServiceImpl")
	private FinanceHandleService.Iface financeHandleService;

    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
	
    @Resource(name = "projectServiceImpl")
    private ProjectService.Iface projectService;
    
	@Transactional
	@Override
	public int updateProjectStatus() throws TException {
		WorkflowSpecialBean bean = super.getBean();
		int projectId = bean.getRefId();//项目ID
		//业务动态
		BizDynamic bizDynamic = new BizDynamic();
		bizDynamic.setProjectId(projectId);
		bizDynamic.setHandleAuthorId(bean.getHandleAuthorId());
		bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
		bizDynamic.setRemark(bean.getMessage());
		bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_BEFORELOAN);
		int res = 2;
		try {
			if (null != bean.getIsReject()) {
				if ("task_LoanRequest".equals(bean.getIsReject())) {
					projectMapper.updateProjectStatusByPrimaryKey(projectId, 1, "");
					//修改赎楼项目状态
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_1, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				}else if("task_OrgManagerCheck".equals(bean.getIsReject())){
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_2, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_5);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				//风控总监审
				}else if("task_RiskDirectorCheck".equals(bean.getIsReject())){
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_8, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_9);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				//业务总监审
				}else if("task_BusinessDirectorCheck".equals(bean.getIsReject())){
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_3, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_6);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				}
				//审批驳回时添加驳回记录
	    	  	recordDynamic(bizDynamic,bean.getWorkflowTaskDefKey());
			} else if (null != bean.getIsVetoProject() && !"".equals(bean.getIsVetoProject()) && "refuse".equals(bean.getIsVetoProject())) {
				// 否决${flow=='refuse'}
				cusAcctMapper.updateCusStatusByProSettle(projectId);
				projectMapper.updateProjectRejected(projectId);
				// 更改项目状态为已归档
				res = projectMapper.updateProjectStatusByPrimaryKey(projectId, 4, DateUtil.format(new Date()));
				
				//业务员-----部门经理------业务总监---风控总监---何总（展期费率低于贷前需何总审）
				//修改赎楼状态1、待客户经理提交  2、待部门经理审批  3、待业务总监审批    8、待风控总监审批  9、待总经理审批   10、已审批 13、已归档
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_13, DateUtil.format(new Date()));
			} else if ("task_LoanRequest".equals(bean.getWorkflowTaskDefKey())) {
				// 流程开始
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_2, "");
				
				projectMapper.updateProjectStatusByPrimaryKey(projectId, 2, "");
				res = 2;
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
				//添加业务动态
				addDynamic(bizDynamic);
			//部门经理审批
			}else if("task_OrgManagerCheck".equals(bean.getWorkflowTaskDefKey())){
				
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_3, "");
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_5);
				//添加业务动态
				addDynamic(bizDynamic);
			}else if("task_BusinessDirectorCheck".equals(bean.getIsReject())){
				//待风控总监审批
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_8, DateUtil.format(new Date()));
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_6);
				//添加业务动态
				addDynamic(bizDynamic);
			//风控总监审
			}else if("task_RiskDirectorCheck".equals(bean.getWorkflowTaskDefKey())){
				//根据展期费率是否大于贷前费率判断是否需要总经理审
				if(checkFeeRate(projectId)){
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, "");
					//审批结束，生成财务收费数据
					addFinnanceHandle(projectId);
				}else{
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_9, "");
				}
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_9);
				//添加业务动态
				addDynamic(bizDynamic);
			//总经理审
			}else if ("task_GeneralCheck".equals(bean.getWorkflowTaskDefKey())) {
				// 流程结束,赎楼项目状态设为已审批
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, "");
				//审批结束，生成财务收费数据
				addFinnanceHandle(projectId);
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_10);
				//添加业务动态
				addDynamic(bizDynamic);
				res = 3;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		
		return res;
	}

	/**
     * 记录审批驳回的记录
     * @throws TException 
     */
    private void recordDynamic(BizDynamic bizDynamic,String workflowTaskDefKey) throws TException{
    	BizDynamic bizDynamicInfo = bizDynamic;
    	bizDynamicInfo.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_BEFORELOAN_OTHER);
    	if(!StringUtil.isBlank(workflowTaskDefKey)){
    		//风控初审驳回
    		if("task_RiskOneCheck".equals(workflowTaskDefKey)){
    			bizDynamicInfo.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_10);
    		//风控复审驳回
    		}else if("task_RiskTwoCheck".equals(workflowTaskDefKey)){
    			bizDynamicInfo.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_20);
    		//风控终审驳回
    		}else if("task_RiskOverCheck".equals(workflowTaskDefKey)){
    			bizDynamicInfo.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_30);
    		//总经理审批驳回
    		}else if("task_GeneralCheck".equals(workflowTaskDefKey)){
    			bizDynamicInfo.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_40);
    		}
    		bizDynamicInfo.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_MAP.get(bizDynamic.getDynamicNumber()));
    		bizDynamicInfo.setFinishDate(DateUtils.getCurrentDateTime());
    		//添加业务动态
    		bizDynamicService.addBizDynamic(bizDynamicInfo);
    	}
    }
    
	
	/**
	 * 添加展期收费数据
	  * @param projectId
	  * @throws TException
	  * @author: baogang
	  * @date: 2016年5月28日 下午5:29:47
	 */
	private void addFinnanceHandle(int projectId) throws TException{
		 financeHandleService.addFinanceHandleByExtension(projectId);
	}
	
	/**
	 * 判断展期费率是否大于贷前费率
	  * @param projectId
	  * @return
	  * @author: baogang
	 * @throws TException 
	  * @date: 2016年6月6日 下午2:10:07
	 */
	public boolean checkFeeRate(int projectId) throws TException{
		// 根据项目ID查询项目列表
		Project project;
		try {
			project = projectService.getLoanProjectByProjectId(projectId);
			double feeRate = project.getProjectGuarantee().getFeeRate();//贷前费率
			double extensionRate = project.getProjectForeclosure().getExtensionRate();//展期费率
			
			//四舍五入，保留两位小数
			feeRate = (double)Math.round(feeRate*100)/100;
			extensionRate = (double)Math.round(extensionRate*100)/100;
			if(extensionRate >= feeRate ){
				return true;
			}
		} catch (TException e) {
			e.printStackTrace();
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}

		
		return false;
	}
	
    /**
     * 添加业务动态
      * @param bean
      * @author: baogang
     * @throws TException 
      * @date: 2016年5月16日 下午3:13:33
     */
    private void addDynamic(BizDynamic bizDynamic) throws TException{
    	bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_MAP.get(bizDynamic.getDynamicNumber()));
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
		projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getRefId(), 1, "");
		//修改赎楼项目状态
		projectMapper.updateProjectForeStatusByPid(super.getBean().getRefId(), 1, "");
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
		//万通审批流程判断分支
		if ( "task_RiskDirectorCheck".equals(task.getTaskDefinitionKey())) {
			boolean result;
			try {
				result = checkFeeRate(super.getBean().getRefId());
				String checkFeeRate = "";
				if(result){
					checkFeeRate = "gt";
				}else{
					checkFeeRate = "less";
				}
				taskVariables.put("exRateVal", checkFeeRate);
			} catch (TException e) {
				e.printStackTrace();
			}
			
		}
	}
}
