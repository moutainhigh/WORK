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
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.qfang.xk.aom.rpc.project.OrderRejectInfo;
import com.xlkfinance.bms.common.constant.AomConstant;
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
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.inloan.PerformJobRemark;
import com.xlkfinance.bms.server.aom.project.mapper.BizProjectMapper;
import com.xlkfinance.bms.server.aom.project.mapper.OrderRejectInfoMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.IntegratedDeptMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.system.mapper.SysOrgInfoMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;

/**
 * 业务申请审批工作流
  * @ClassName: BusinessApplyRequestProcessWrokflow
  * @author: baogang
  * @date: 2016年7月22日 下午3:53:08
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Component("businessApplyRequestProcessWrokflow")
@Scope("prototype")
public class BusinessApplyRequestProcessWrokflow extends WorkflowSpecialDispose{

   private Logger logger = LoggerFactory.getLogger(BusinessApplyRequestProcessWrokflow.class);
	
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;
	
	
	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
   
    @Resource(name = "BizHandleServiceImpl")
    private BizHandleService.Iface bizHandleService;
    
    @Resource(name = "sysUserMapper")
    private SysUserMapper sysUserMapper;
   
    @Resource(name = "sysOrgInfoMapper")
    private SysOrgInfoMapper sysOrgInfoMapper;
    
    @Resource(name = "bizDynamicServiceImpl")
    private BizDynamicService.Iface bizDynamicService;
    
    @Resource(name = "orderRejectInfoMapper")
    private OrderRejectInfoMapper orderRejectInfoMapper;
    
    @Resource(name = "bizProjectMapper")
    private BizProjectMapper bizProjectMapper;
    
    @Resource(name = "financeHandleServiceImpl")
    private FinanceHandleService.Iface financeHandleService;
   
    @Resource(name = "integratedDeptMapper")
    private IntegratedDeptMapper integratedDeptMapper;
    
	@Resource(name = "productMapper")
	private ProductMapper productMapper;
    
	@Resource(name = "bizProjectEstateServiceImpl")
    private BizProjectEstateService.Iface bizProjectEstateServiceImpl;
	
	@Resource(name = "orgCooperatCompanyApplyServiceImpl")
	private OrgCooperatCompanyApplyService.Iface orgCooperatCompanyApplyServiceImpl;
	
    @Transactional
	@Override
	public int updateProjectStatus() throws TException {
		int res = 2;
		WorkflowSpecialBean bean = super.getBean();
		int projectId = bean.getProjectId();
		BizDynamic bizDynamic = new BizDynamic();
		bizDynamic.setProjectId(projectId);
		int handleAuthorId = bean.getHandleAuthorId();//操作人
		bizDynamic.setHandleAuthorId(handleAuthorId);
		bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
		bizDynamic.setRemark(bean.getMessage());
		bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_BEFORELOAN);
		
		updatePmuserId(projectId,handleAuthorId);
		//驳回状态修改
      if (null != bean.getIsReject()) {
			if ("task_BusinessRequest".equals(bean.getIsReject())) {
				//修改业务审批状态为待客户经理提交
				projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_1, DateUtil.format(new Date()));
				
				projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_2, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_1);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}if ("task_RiskOneCheck".equals(bean.getIsReject())) {
				// 风控初审 更新项目状态为待风控初审
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_5, DateUtil.format(new Date()));
				
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			} else if ("task_RiskTwoCheck".equals(bean.getIsReject())) {
				// 风控复审 更新项目状态为待风控复审
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_6, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_3);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}else if ("task_RiskOverCheck".equals(bean.getIsReject())) {
				// 风控终审 更新项目状态为待风控终审
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_7, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_4);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}else if ("task_ComplianceReview".equals(bean.getIsReject())) {
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_15, DateUtil.format(new Date()));
				//驳回节点后删除多余的业务动态
				bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2_1);
				bizDynamicService.delBizDynamicByLastId(bizDynamic);
			}
			
			//审批驳回时添加驳回记录
    	  	recordDynamic(bizDynamic,bean.getWorkflowTaskDefKey());
		} else if (null != bean.getIsVetoProject() && !"".equals(bean.getIsVetoProject()) && "refuse".equals(bean.getIsVetoProject())) {

			projectMapper.updateProjectRejected(projectId); 
			
			// 更改业务主体为审核不通过
			res = projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_5, DateUtil.format(new Date()));
			//修改赎楼状态1、待客户经理提交5、待风控初审6、待风控复审7、待风控终审9、待总经理审批10、已审批11、已放款12、已归档（深圳总部项目）
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
			
			//审批拒单后恢复机构可用额度
			double loanMoney = findProjectLoanMoney(projectId);
			orgCooperatCompanyApplyServiceImpl.updateOrgCreditLimit(projectId, loanMoney);
			
			//修改订单状态为撤单，关闭以及拒单
			BizProject bizProject = new BizProject();
			bizProject.setPid(projectId);	
			bizProject.setIsChechan(Constants.PROJECT_IS_CHECHAN);
			bizProject.setIsClosed(AomConstant.IS_CLOSED_1);
			bizProject.setIsReject(AomConstant.IS_REJECT_4);
			bizProjectMapper.updateProjectReject(bizProject);
			
			OrderRejectInfo rejectInfo = new OrderRejectInfo();
			rejectInfo.setOrderId(projectId);
			rejectInfo.setExamineOpinion(bean.getMessage());
			rejectInfo.setExamineUser(bean.getHandleAuthorId());
			orderRejectInfoMapper.insert(rejectInfo);
			
			//添加审批拒单的业务动态  
			bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_OTHER);
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_40);
			bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_OTHER_MAP.get(bizDynamic.getDynamicNumber()));
			bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
    		//添加业务动态
    		bizDynamicService.addBizDynamic(bizDynamic);
		} else if ("task_BusinessRequest".equals(bean.getWorkflowTaskDefKey())) {
			// 流程开始,申请状态变为审核中
			projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_3, "");
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
			   addIntegratedDept(projectId);
			   addFinnanceHandle(projectId);
			}
			
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2);
			//添加业务动态
			addDynamic(bizDynamic);
		} else if ("task_ComplianceReview".equals(bean.getWorkflowTaskDefKey())) {
			// 风控初审 更新项目状态为待风控复审
			res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_6, DateUtil.format(new Date()));
			
			bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_BEFORELOAN_2_1);
			//添加业务动态
			addDynamic(bizDynamic);
		}else if ("task_RiskTwoCheck".equals(bean.getWorkflowTaskDefKey())) {
			// 风控复审 ，判断借款金额是否大于300万，大于需要风控终审，不大于风控复审完成后直接结算
			double loanMoney = findProjectLoanMoney(projectId);
			if(loanMoney <= 5000000){
				res = projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_10, DateUtil.format(new Date()));
				// 更改项目状态为审批通过
				projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_4, DateUtil.format(new Date()));
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
				// 更改项目状态为审批通过
				projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_4, DateUtil.format(new Date()));

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
			
			// 更改项目状态为审批通过
			res = projectMapper.updateProjectStatusByPrimaryKey(projectId, AomConstant.BUSINESS_REQUEST_STATUS_4, DateUtil.format(new Date()));

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
	 * 添加综合部数据
	 *@author:liangyanjun
	 *@time:2016年3月14日上午2:52:18
	 *@param projectId
	 */
	private void addIntegratedDept(int projectId) {
      //查档
      CheckDocumentDTO checkDocumentDTO=new CheckDocumentDTO();
      checkDocumentDTO.setApprovalStatus(Constants.CHECK_DOCUMENT_APPROVAL_STATUS_1);
      checkDocumentDTO.setCheckStatus(Constants.CHECK_DOCUMENT_STATUS_1);
      checkDocumentDTO.setReCheckStatus(Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_1);
      checkDocumentDTO.setProjectId(projectId);
      integratedDeptMapper.addCheckDocument(checkDocumentDTO);
      //查诉讼
      CheckLitigationDTO checkLitigationDTO=new CheckLitigationDTO();
      checkLitigationDTO.setApprovalStatus(Constants.CHECK_LITIGATION_APPROVAL_STATUS_1);
      checkLitigationDTO.setCheckStatus(Constants.CHECK_LITIGATION_STATUS_1);
      checkLitigationDTO.setProjectId(projectId);
      integratedDeptMapper.addCheckLitigation(checkLitigationDTO);
      //执行岗备注
      PerformJobRemark performJobRemark=new PerformJobRemark();
      performJobRemark.setProjectId(projectId);
      performJobRemark.setStatus(Constants.PERFORM_JOB_REMARK_STATUS_1);
      integratedDeptMapper.addPerformJobRemark(performJobRemark);
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
            //初始化财务收款项目为利息和手续费的值
            //            ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
            //            double guaranteeFee = projectGuarantee.getGuaranteeFee();//利息
            //            double poundage = projectGuarantee.getPoundage();//手续费
            //            double receMoney = projectGuarantee.getReceMoney();//佣金
            //            ApplyFinanceHandleDTO applyFinanceHandleQuery=new ApplyFinanceHandleDTO();
            //            applyFinanceHandleQuery.setFinanceHandleId(financeHandleDTO.getPid());
            //            List<ApplyFinanceHandleDTO> applyFinanceHandleList = financeHandleService.findAllApplyFinanceHandle(applyFinanceHandleQuery);
            //            for (ApplyFinanceHandleDTO applyFinanceHandleDTO : applyFinanceHandleList) {
            //               int recPro = applyFinanceHandleDTO.getRecPro();
            //               if (Constants.REC_PRO_1==recPro) {
            //                  applyFinanceHandleDTO.setRecMoney(guaranteeFee);
            //                  financeHandleService.updateApplyFinanceHandle(applyFinanceHandleDTO);
            //               }else if(Constants.REC_PRO_2==recPro){
            //                  applyFinanceHandleDTO.setRecMoney(poundage);
            //                  financeHandleService.updateApplyFinanceHandle(applyFinanceHandleDTO);
            //               }else if(Constants.REC_PRO_7==recPro){
            //                  applyFinanceHandleDTO.setRecMoney(receMoney);
            //                  financeHandleService.updateApplyFinanceHandle(applyFinanceHandleDTO);
            //               }
            //            }
      } catch (TException e) {
         logger.error("当业务审批结束之后添加财务办理信息失败：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
   }
   /**
    * 设置客户经理
     * @param projectId
     * @param handleAuthorId
     * @author: baogang
     * @date: 2016年8月12日 上午10:34:29
    */
   private void updatePmuserId(int projectId,int handleAuthorId){
	   Project project = projectMapper.getProjectInfoById(projectId);
	   Integer pmUserId = project.getPmUserId();
	   if(pmUserId == null || pmUserId == 0 ){
		   BizProject bizProject = new BizProject();
		   bizProject.setPid(projectId);
		   bizProject.setPmUserId(handleAuthorId);
		   bizProjectMapper.update(bizProject);
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
		//修改业务申请审批
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
