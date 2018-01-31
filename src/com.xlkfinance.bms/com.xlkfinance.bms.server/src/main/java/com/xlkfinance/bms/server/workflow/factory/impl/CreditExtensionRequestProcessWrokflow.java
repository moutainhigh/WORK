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

import java.math.BigDecimal;
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
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.server.beforeloan.mapper.CreditMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceTransactionMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * 深圳总部项目审批流程（展期）
  * @ClassName: CreditLoanRequestProcessWrokflow
  * @author: Administrator
  * @date: 2016年2月19日 上午9:19:31
 */
@SuppressWarnings("unchecked")
@Component("creditExtensionRequestProcessWrokflow")
@Scope("prototype")
public class CreditExtensionRequestProcessWrokflow extends WorkflowSpecialDispose{
	private Logger logger = LoggerFactory.getLogger(CreditExtensionRequestProcessWrokflow.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "creditMapper")
	private CreditMapper creditMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "financeTransactionMapper")
	private FinanceTransactionMapper financeTransactionMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "productMapper")
	private ProductMapper productMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
	

    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
	
    @Resource(name = "financeHandleServiceImpl")
    private FinanceHandleService.Iface financeHandleService;
    
    @Transactional
	@Override
	public int updateProjectStatus() throws TException {
		int res = 2;
		WorkflowSpecialBean bean = super.getBean();
		int projectId = bean.getRefId();
		
		BizDynamic bizDynamic = new BizDynamic();
		bizDynamic.setProjectId(projectId);
		bizDynamic.setHandleAuthorId(bean.getHandleAuthorId());
		bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
		bizDynamic.setRemark(bean.getMessage());
		bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_BEFORELOAN);
		try {
			if (null != bean.getIsReject()) {
				if ("task_LoanRequest".equals(bean.getIsReject())) {
					projectMapper.updateProjectStatusByPrimaryKey(projectId, 1, "");
					//修改赎楼项目状态
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_1, "");
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				}if ("task_RiskOneCheck".equals(bean.getIsReject())) {
					// 风控初审 更新项目状态为待风控复审
					res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_5, "");
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				} else if ("task_RiskTwoCheck".equals(bean.getIsReject())) {
					// 风控初审 更新项目状态为待风控终审
					res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_6, "");
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_3);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				}else if ("task_RiskOverCheck".equals(bean.getIsReject())) {
					res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_7, "");
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_4);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				}
			} else if (null != bean.getIsVetoProject() && !"".equals(bean.getIsVetoProject()) && "refuse".equals(bean.getIsVetoProject())) {
				// 否决${flow=='refuse'}
				cusAcctMapper.updateCusStatusByProSettle(projectId);
				projectMapper.updateProjectRejected(projectId);
				// 根据项目ID修改项目授信状态为失效,如果是贷款项目就不做修改
				creditMapper.updateCreditStatusByProjectId(projectId);
				
				// 项目否决,删除对应的财务放款记录
				financeTransactionMapper.deleteFinanceRecords(projectId);
				
				// 更改项目状态为已归档
				res = projectMapper.updateProjectStatusByPrimaryKey(projectId, 4, DateUtil.format(new Date()));
				//修改赎楼状态1、待客户经理提交5、待风控初审6、待风控复审7、待风控终审9、待总经理审批10、已审批11、已放款12、已归档（深圳总部项目）
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_13, DateUtil.format(new Date()));
			} else if ("task_LoanRequest".equals(bean.getWorkflowTaskDefKey())) {
				// 流程开始
				projectMapper.updateProjectStatusByPrimaryKey(projectId, 2, "");
				//修改赎楼状态
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_5, "");
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
				//添加业务动态
				addDynamic(bizDynamic);
				res = 2;
			} else if ("task_RiskOneCheck".equals(bean.getWorkflowTaskDefKey())) {
				// 风控初审 更新项目状态为待风控复审
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_6, "");
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
				//添加业务动态
				addDynamic(bizDynamic);
			} else if ("task_RiskTwoCheck".equals(bean.getWorkflowTaskDefKey())) {
				// 风控复审 更新项目状态为待风控终审
				double loanMoney = findProjectLoanMoney(projectId);
				//判断贷款金额是否大于300万,大于300万则需要风控终审，不大于300万则审批结束
				if(loanMoney>3000000){
					res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_7, "");
				}else{
					res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, "");
					addFinnanceHandle(projectId);
				}
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_3);
				//添加业务动态
				addDynamic(bizDynamic);
			}else if ("task_RiskOverCheck".equals(bean.getWorkflowTaskDefKey())) {
				//判断贷款金额是否大于1000万,大于1000万返回false
				if(checkProjectLoanMoney(projectId)){
					res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, "");
					addFinnanceHandle(projectId);
				}else{
					// 风控初审 更新项目状态为待总经理审批
					res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_9, "");
				}
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_4);
				//添加业务动态
				addDynamic(bizDynamic);
			}else if ("task_GeneralCheck".equals(bean.getWorkflowTaskDefKey())) {
				// 流程结束,赎楼项目状态设为已审批
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, "");

				res = projectMapper.updateProjectStatusByPrimaryKey(projectId, 3, DateUtil.format(new Date()));
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
     * 添加展期收费数据
      * @param projectId
      * @throws TException
      * @author: baogang
      * @date: 2016年5月28日 下午5:39:54
     */
	private void addFinnanceHandle(int projectId) throws TException{
		 financeHandleService.addFinanceHandleByExtension(projectId);
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
	
	/**
	 * 判断贷款金额是否大于1000万
	  * @param projectId
	  * @return 大于1000万返回false 否则返回true
	  * @author: Administrator
	  * @date: 2016年2月19日 下午2:15:31
	 */
	public boolean checkProjectLoanMoney(int projectId){
		//获取项目类型（信用、赎楼）
		//int productType = productMapper.getProductType(project.getProductId());
		double loanMoney=0.0;

		ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
		if(projectGuarantee != null){
				loanMoney = projectGuarantee.getLoanMoney();
		}
		BigDecimal decimal = NumberUtils.parseDecimal(String.valueOf(loanMoney));
		if(decimal.doubleValue()>10000000){
			return false;
		}
		
		return true;
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
			//风控复审节点判断
			if ("task_RiskTwoCheck".equals(task.getTaskDefinitionKey())) {
				taskVariables.put("money", findProjectLoanMoney(super.getBean().getRefId()));
			}
			//风控终审节点判断
			if ("task_RiskOverCheck".equals(task.getTaskDefinitionKey())) {
				taskVariables.put("money", findProjectLoanMoney(super.getBean().getRefId()));
			}
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
}
