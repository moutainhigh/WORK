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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.server.beforeloan.mapper.CreditMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectSurveyReportMapper;
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
 * 赎楼项目审批处理类（万通）
  * @ClassName: ForeLoanRequestProcessWrokflow
  * @author: Administrator
  * @date: 2016年2月23日 下午2:12:48
 */
@SuppressWarnings("unchecked")
@Component("foreLoanRequestProcessWrokflow")
@Scope("prototype")
public class ForeLoanRequestProcessWrokflow extends WorkflowSpecialDispose{
   private Logger logger = LoggerFactory.getLogger(ForeLoanRequestProcessWrokflow.class);

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
    
    @Resource(name = "BizHandleServiceImpl")
    private BizHandleService.Iface bizHandleService;
   
    @SuppressWarnings("rawtypes")
    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;
   
    @SuppressWarnings("rawtypes")
    @Resource(name = "sysOrgInfoMapper")
    private SysOrgInfoMapper sysOrgInfoMapper;
   
    @SuppressWarnings("rawtypes")
    @Resource(name = "projectSurveyReportMapper")
    private ProjectSurveyReportMapper  projectSurveyReportMapper;
    
    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
    
    @Transactional
	@Override
	public int updateProjectStatus() throws TException {
		WorkflowSpecialBean bean = super.getBean();
		int projectId = bean.getProjectId();//项目ID
		double loanMoney = findProjectLoanMoney(projectId);//项目贷款金额
		boolean checkSpecialDesc = true;
		if("NoSpecial".equals(checkSpecialDesc(projectId))){
			checkSpecialDesc = false;
		}
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
				if ("task_LoanRequest".equals(bean.getIsReject())) {//11509
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
				//审查员审
				}else if("task_ExaminerCheck".equals(bean.getIsReject())){
					//待审查员审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_4, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_7);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				//业务总监审
				}else if("task_BusinessDirectorCheck".equals(bean.getIsReject())){
					//待业务总监审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_3, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_6);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				}else if("task_ReviewDepartmentCheck".equals(bean.getIsReject())){
					//待审查主管审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_14, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_8);
					bizDynamicService.delBizDynamicByLastId(bizDynamic);
				//风控总监审
				}else if("task_RiskDirectorCheck".equals(bean.getIsReject())){
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_8, DateUtil.format(new Date()));
					//驳回节点后删除多余的业务动态
					bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_9);
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
				//业务员-----审查员-----风控总监(loanMoney<=300万) 业务员-----部门经理------审查员-----风控总监（300万<loanMoney<=500万）
				//业务员-----部门经理------业务总监------审查员------风控总监------何总（loanMoney>300万）
				//修改赎楼状态1、待客户经理提交  2、待部门经理审批  3、待业务总监审批   4、待审查员审批   8、待风控总监审批   9、待总经理审批   10、已审批   11、已放款  12、业务办理已完成    13、已归档 14、待审查主管审批
				res = projectMapper.updateProjectForeStatusByPid(projectId, 12, DateUtil.format(new Date()));
			} else if ("task_LoanRequest".equals(bean.getWorkflowTaskDefKey())) {
				// 流程开始
				projectMapper.updateProjectStatusByPrimaryKey(projectId, Constants.FORECLOSURE_STATUS_2, "");
				if(loanMoney >3000000 || checkSpecialDesc){
					//修改赎楼状态    待部门经理审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_2, DateUtil.format(new Date()));
				}else{
					//审查员审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_4, DateUtil.format(new Date()));
				}
				
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
				//添加业务动态
				addDynamic(bizDynamic);
				
				res = 2;
				//部门经理审批
			}else if("task_OrgManagerCheck".equals(bean.getWorkflowTaskDefKey())){
				if(loanMoney >5000000 || checkSpecialDesc){
					//修改赎楼状态    待业务总监审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_3, DateUtil.format(new Date()));
				}else{
					//待审查员审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_4, DateUtil.format(new Date()));
				}
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_5);
				//添加业务动态
				addDynamic(bizDynamic);
				//业务总监审
			}else if("task_BusinessDirectorCheck".equals(bean.getWorkflowTaskDefKey())){
				//待审查员审批
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_4, DateUtil.format(new Date()));
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_6);
				//添加业务动态
				addDynamic(bizDynamic);
				//审查员审
			}else if("task_ExaminerCheck".equals(bean.getWorkflowTaskDefKey())){
				//初始化综合部数据和财务收费数据
				CheckDocumentDTO checkDocumentQuery=new CheckDocumentDTO();
	            checkDocumentQuery.setProjectId(projectId);
	            List<CheckDocumentDTO> checkDocumentList = integratedDeptMapper.queryCheckDocument(checkDocumentQuery);
	            if (checkDocumentList==null||checkDocumentList.isEmpty()) {
	               integratedDeptServiceImpl.initIntegratedDept(projectId);
	               addFinnanceHandle(projectId);
	            }
				if(loanMoney >3000000 || checkSpecialDesc){
					//修改赎楼状态    待审查主管审批
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_14, DateUtil.format(new Date()));
				}else{
					//待风控总监审
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_8, DateUtil.format(new Date()));
				}
				
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_7);
				//添加业务动态
				addDynamic(bizDynamic);
				//审查主管审批
			}else if("task_ReviewDepartmentCheck".equals(bean.getWorkflowTaskDefKey())){
				//待风控总监审
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_8, DateUtil.format(new Date()));
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_8);
				//添加业务动态
				addDynamic(bizDynamic);
			//风控总监审
			}else if("task_RiskDirectorCheck".equals(bean.getWorkflowTaskDefKey())){
			  
				if(loanMoney >3000000){
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_9, DateUtil.format(new Date()));
				}else{
					projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
				}
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_9);
				//添加业务动态
				addDynamic(bizDynamic);
	         
				
			}else if ("task_GeneralCheck".equals(bean.getWorkflowTaskDefKey())) {
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_10);
				//添加业务动态
				addDynamic(bizDynamic);
				// 流程结束,赎楼项目状态设为已审批
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
				// 归档 更改贷款信息状态
				loanMapper.updateLoanStatus(projectId, 1);
				// 更新对账状态 
				loanMapper.updateIsReconciliationStatus(projectId);
				// 更改项目状态为已归档
				res = projectMapper.updateProjectStatusByPrimaryKey(projectId, 4, DateUtil.format(new Date()));
				// 更新还款计划表 还款计划中间表 即时表 冻结状态为已生效
				loanRepaymentPlanMapper.updateRepayFreezeStatus(projectId);
				res = 3;
				}
			
			} catch (Exception e) {
				e.printStackTrace();
				throw new TException(ExceptionUtil.getExceptionMessage(e));
			}
		return res;
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
    * @throws TException 
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

	@Override
	public void resetProject(){
		//申请状态（申请中=1、审核中=2、已放款=3、已归档=4、已冻结=5、已解冻=6）
		projectMapper.updateProjectStatusByPrimaryKey(super.getBean().getProjectId(), 1, "");
		//修改赎楼项目状态
		projectMapper.updateProjectForeStatusByPid(super.getBean().getProjectId(), 1, "");
	}
	
	/**
	 * 判断特殊情况说明是否已填写
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年4月2日 下午4:16:58
	 */
	public String checkSpecialDesc(int projectId){
		ProjectSurveyReport projectSurveyReport = new ProjectSurveyReport();
		String special="NoSpecial";
		try {
			List<ProjectSurveyReport> list = new ArrayList<ProjectSurveyReport>();
			list = projectSurveyReportMapper.getSurveyReportByProjectId(projectId);
			// 判断是否成功
			if (list.size() > 0) {
				projectSurveyReport = list.get(0);
			}
			if(projectSurveyReport != null){
				String specialDesc = projectSurveyReport.getSpecialDesc();
				//无特殊情况
				if(StringUtil.isBlank(specialDesc)){
					special = "NoSpecial";
				}else{
					special = "Special";
				}
			}else{
				special = "NoSpecial";
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询尽职调查报告:" + e.getMessage());
			}
		}
		return special;
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
		if ("task_LoanRequest".equals(task.getTaskDefinitionKey())|| "task_OrgManagerCheck".equals(task.getTaskDefinitionKey())
				|| "task_ExaminerCheck".equals(task.getTaskDefinitionKey())|| "task_RiskDirectorCheck".equals(task.getTaskDefinitionKey())) {
			taskVariables.put("money", findProjectLoanMoney(super.getBean().getProjectId()));
			taskVariables.put("special", checkSpecialDesc(super.getBean().getProjectId()));
		}
	}
}
