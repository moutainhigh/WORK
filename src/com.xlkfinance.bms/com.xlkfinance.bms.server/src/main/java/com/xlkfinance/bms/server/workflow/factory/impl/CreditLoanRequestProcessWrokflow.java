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
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.avalon.framework.service.ServiceException;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.util.DateUtil;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.server.aom.project.mapper.BizProjectMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.CreditMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceTransactionMapper;
import com.xlkfinance.bms.server.inloan.mapper.IntegratedDeptMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.system.mapper.SysOrgInfoMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * 深圳总部项目审批流程
  * @ClassName: CreditLoanRequestProcessWrokflow
  * @author: Administrator
  * @date: 2016年2月19日 上午9:19:31
 */
@SuppressWarnings("unchecked")
@Component("creditLoanRequestProcessWrokflow")
@Scope("prototype")
public class CreditLoanRequestProcessWrokflow extends WorkflowSpecialDispose{

   private Logger logger = LoggerFactory.getLogger(CreditLoanRequestProcessWrokflow.class);
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
	
    @Resource(name = "financeHandleServiceImpl")
    private FinanceHandleService.Iface financeHandleService;
   
    @SuppressWarnings("rawtypes")
    @Resource(name = "integratedDeptMapper")
    private IntegratedDeptMapper integratedDeptMapper;
    
    @Resource(name = "integratedDeptServiceImpl")
    private IntegratedDeptService.Iface integratedDeptServiceImpl;
   
    @SuppressWarnings("rawtypes")
    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;
   
    @SuppressWarnings("rawtypes")
    @Resource(name = "sysOrgInfoMapper")
    private SysOrgInfoMapper sysOrgInfoMapper;
   
    @Resource(name = "BizHandleServiceImpl")
    private BizHandleService.Iface bizHandleService;
    
    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
    @Resource(name = "bizProjectEstateServiceImpl")
    private BizProjectEstateService.Iface bizProjectEstateServiceImpl;
    
   	@Resource(name = "bizProjectMapper")
   	private BizProjectMapper bizProjectMapper;
    
    @Transactional
	@Override
	public int updateProjectStatus() throws TException {
		int res = 2;
		WorkflowSpecialBean bean = super.getBean();
		int projectId = bean.getProjectId();
		BizDynamic bizDynamic = new BizDynamic();
		bizDynamic.setProjectId(projectId);
		bizDynamic.setHandleAuthorId(bean.getHandleAuthorId());
		bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
		bizDynamic.setRemark(bean.getMessage());
		bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_BEFORELOAN);
		//驳回状态修改
      if (null != bean.getIsReject()) {
			if ("task_LoanRequest".equals(bean.getIsReject())) {
				projectMapper.updateProjectStatusByPrimaryKey(projectId, Constants.FORECLOSURE_STATUS_1, "");
				//修改赎楼项目状态
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_1, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}if ("task_RiskOneCheck".equals(bean.getIsReject())) {
				// 风控初审 更新项目状态为待风控复审
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_5, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			} else if ("task_RiskTwoCheck".equals(bean.getIsReject())) {
				// 风控初审 更新项目状态为待风控终审
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_6, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_3);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}else if ("task_RiskOverCheck".equals(bean.getIsReject())) {
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_7, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_4);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}
			else if ("task_ComplianceReview".equals(bean.getIsReject())) {
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_15, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2_1);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}
			//审批驳回时添加驳回记录
    	  	recordDynamic(bizDynamic,bean.getWorkflowTaskDefKey());
    	  	//风控复审以及终审否决
		} else if (null != bean.getIsVetoProject() && !"".equals(bean.getIsVetoProject()) && "refuse".equals(bean.getIsVetoProject())) {
			// 否决${flow=='refuse'}
			projectMapper.updateProjectRejected(projectId);
			//修改订单状态为关闭以及撤单
			BizProject bizProject = new BizProject();
			bizProject.setPid(projectId);	
			bizProject.setIsChechan(Constants.PROJECT_IS_CHECHAN);
//			bizProject.setIsClosed(AomConstant.IS_CLOSED_1);
			bizProjectMapper.updateProjectReject(bizProject);
			// 更改项目状态为已归档
			res = projectMapper.updateProjectStatusByPrimaryKey(projectId, 4, DateUtil.format(new Date()));
			//修改赎楼状态10、已审批
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
			//添加审批拒单的业务动态  
			bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_OTHER);
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_40);
			bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_MAP.get(bizDynamic.getDynamicNumber()));
			bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
    		//添加业务动态
    		bizDynamicService.addBizDynamic(bizDynamic);
		} else if ("task_LoanRequest".equals(bean.getWorkflowTaskDefKey())) {
			// 流程开始
			projectMapper.updateProjectStatusByPrimaryKey(projectId, 2, "");
			//修改赎楼状态
			projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_5, DateUtil.format(new Date()));
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
			//添加业务动态
			addDynamic(bizDynamic);
			//修改物业原表中的数据
			bizProjectEstateServiceImpl.updateProjectByEstate(projectId);
			
			res = 2;
		} else if ("task_RiskOneCheck".equals(bean.getWorkflowTaskDefKey())) {
			// 风控初审 更新项目状态为待合规复审
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_15, DateUtil.format(new Date()));
			 //风控初审 初始化综合部数据和财务收费数据
			CheckDocumentDTO checkDocumentQuery=new CheckDocumentDTO();
			checkDocumentQuery.setProjectId(projectId);
			List<CheckDocumentDTO> checkDocumentList = integratedDeptMapper.queryCheckDocument(checkDocumentQuery);
			if (checkDocumentList==null||checkDocumentList.isEmpty()) {
			   integratedDeptServiceImpl.initIntegratedDept(projectId);
			   addFinnanceHandle(projectId);
			}
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
			//添加业务动态
			addDynamic(bizDynamic);
		}else if ("task_ComplianceReview".equals(bean.getWorkflowTaskDefKey())) {
			// 合规复审 更新项目状态为待风控复审
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_6, DateUtil.format(new Date()));
			
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2_1);
			//添加业务动态
			addDynamic(bizDynamic);
		}  else if ("task_RiskTwoCheck".equals(bean.getWorkflowTaskDefKey())) {
			// 风控复审 ，判断借款金额是否大于500万，大于需要风控终审，不大于风控复审完成后直接结算
			double loanMoney = findProjectLoanMoney(projectId);
			if(loanMoney <= 5000000){
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
			}else{
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_7, DateUtil.format(new Date()));
			}
			
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_3);
			//添加业务动态
			addDynamic(bizDynamic);
		}else if ("task_RiskOverCheck".equals(bean.getWorkflowTaskDefKey())) {
         //判断贷款金额是否大于1000万,大于1000万返回false
			if(checkProjectLoanMoney(projectId)){
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
			}else{
				//更新项目状态为待总经理审批
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_9, DateUtil.format(new Date()));
			}
			
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_4);
			//添加业务动态
			addDynamic(bizDynamic);
		}else if ("task_GeneralCheck".equals(bean.getWorkflowTaskDefKey())) {
			// 流程结束,赎楼项目状态设为已审批
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
	        bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_10);
	 		//添加业务动态
	 		addDynamic(bizDynamic);
			
			// 归档 更改贷款信息状态
			loanMapper.updateLoanStatus(projectId, 1);
			// 更新对账状态 
			loanMapper.updateIsReconciliationStatus(projectId);
			// 更改项目状态为已放款
			res = projectMapper.updateProjectStatusByPrimaryKey(projectId, 3, DateUtil.format(new Date()));
			// 更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
			loanRepaymentPlanMapper.updateRepayFreezeStatus(projectId);
			res = 3;
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
    		//合规复审驳回
    		else if("task_ComplianceReview".equals(workflowTaskDefKey)){
    			bizDynamicInfo.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_50);
    		}
    		bizDynamicInfo.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_OTHER_MAP.get(bizDynamic.getDynamicNumber()));
    		bizDynamicInfo.setFinishDate(DateUtils.getCurrentDateTime());
    		//添加业务动态
    		bizDynamicService.addBizDynamic(bizDynamicInfo);
    	}
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
    * 当赎楼贷审批结束之后添加财务办理信息
    *@author:liangyanjun
    *@time:2016年2月25日下午2:43:40
    *@param projectId
	 * @throws ServiceException 
    */
   private void addFinnanceHandle(int projectId) throws TException {
      try {
	        Project project = projectMapper.getProjectInfoById(projectId);
	        FinanceHandleDTO financeHandleDTO = new FinanceHandleDTO();
	        financeHandleDTO.setProjectId(projectId);//
	        financeHandleDTO.setStatus(Constants.REC_STATUS_NO_CHARGE);
	        financeHandleDTO.setCreaterId(project.getPmUserId());
	        financeHandleService.addFinanceHandle(financeHandleDTO);
      } catch (TException e) {
         logger.error("当赎楼贷审批结束之后添加财务办理信息失败：" + ExceptionUtil.getExceptionMessage(e));
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
		projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 1, "");
		//修改赎楼项目状态
		projectMapper.updateProjectForeStatusByPid(super.getBean().getProjectId(), 1, "");
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
			taskVariables.put("money", findProjectLoanMoney(super.getBean().getProjectId()));
		}
		//风控终审节点判断
		if ("task_RiskOverCheck".equals(task.getTaskDefinitionKey())) {
			taskVariables.put("money", findProjectLoanMoney(super.getBean().getProjectId()));
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
