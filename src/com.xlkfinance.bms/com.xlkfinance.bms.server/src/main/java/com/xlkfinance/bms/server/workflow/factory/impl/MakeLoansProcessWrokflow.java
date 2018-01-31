package com.xlkfinance.bms.server.workflow.factory.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.BizHandleWorkflowDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.HouseBalanceDTO;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;
import com.xlkfinance.bms.rpc.product.ProductService.Client;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectForeclosureMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.product.mapper.ProductMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialBean;
import com.xlkfinance.bms.server.workflow.factory.WorkflowSpecialDispose;
import com.xlkfinance.bms.server.workflow.mapper.WorkflowProjectMapper;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月28日上午9:28:26 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：放款申请工作流特殊处理类 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Component("makeLoansProcessWrokflow")
@Scope("prototype")
public class MakeLoansProcessWrokflow extends WorkflowSpecialDispose {
   private Logger logger = LoggerFactory.getLogger(MakeLoansProcessWrokflow.class);
   @SuppressWarnings("rawtypes")
   @Resource(name = "workflowProjectMapper")
   private WorkflowProjectMapper workflowProjectMapper;

   @SuppressWarnings("rawtypes")
   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;

   @Resource(name = "projectMapper")
   private ProjectMapper projectMapper;

   @Resource(name = "BizHandleServiceImpl")
   private BizHandleService.Iface bizHandleService;
   
   @Resource(name = "bizHandleMapper")
   private BizHandleMapper bizHandleMapper;
   
   @Resource(name = "projectForeclosureMapper")
   private ProjectForeclosureMapper projectForeclosureMapper;
   
   @Resource(name = "sysUserMapper")
   private SysUserMapper sysUserMapper;
   
   @Resource(name = "bizDynamicServiceImpl")
   private BizDynamicService.Iface bizDynamicService;

   @Resource(name = "productMapper")
   private ProductMapper productMapper;
   
   @Resource(name = "projectInfoServiceImpl")
   private ProjectInfoService.Iface projectInfoService;
	
@Override
   public int updateProjectStatus() throws TException {
      int res = -1;
      String isReject = super.getBean().getIsReject();
      String workflowTaskDefKey = super.getBean().getWorkflowTaskDefKey();
      int projectId = super.getBean().getProjectId();
      int handleAuthorId = super.getBean().getHandleAuthorId();
      String productNum = "";
     
      //通过projct找产品名称
      Product product = productMapper.findProductByProjectId(projectId);//产品信息
      productNum = product.getProductNumber();
      ApplyFinanceHandleDTO applyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_3);
      int applyStatus = applyFinanceHandle.getApplyStatus();//第一次放款审核状态
      //是否为赎楼提放贷
      boolean isTfd=(Constants.JY_YSL_TFD.equals(productNum) || Constants.FJY_YSL_TFD.equals(productNum));
      //1.判断是否需要二次放款  //2.第一次放款已放完
      if((Constants.JY_YSL_TFD.equals(productNum) || Constants.FJY_YSL_TFD.equals(productNum))&&applyStatus==Constants.APPLY_STATUS_5){
    	  applyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_9);
      }
      if (applyFinanceHandle == null || applyFinanceHandle.getPid() <= 0) {
         return res;
      }
      double makeLoansMoney = applyFinanceHandle.getRecMoney();// 第一次放款金额
      WorkflowSpecialBean bean = super.getBean();
      String message = bean.getMessage();
      // 驳回
      if (!StringUtil.isBlank(bean.getIsReject())) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_3);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         // 开始流程
      } else if ("task_Request".equals(workflowTaskDefKey)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_4);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         // 否决${flow=='refuse'}
      } else if ("refuse".equals(isReject)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_6);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         // 走完最后一个流程时处理状态为已通过
         // 1.小于300w资金经理审批同意
      } else if (("task_FundManagerCheck".equals(workflowTaskDefKey) && makeLoansMoney < 3000000)) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_5);
         
         applyFinanceHandle.setFundManagerRemark(message);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         // 更新放款状态
         if (!isTfd)finishBizDynamicByInloan(handleAuthorId, projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_7_1, message);
         updateMakeLoans(projectId,applyFinanceHandle,handleAuthorId);
         // .大于等于300w,资金经理审批时保存备注
      }else if (("task_FundManagerCheck".equals(workflowTaskDefKey))) {
         applyFinanceHandle.setFundManagerRemark(message);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         if (!isTfd)finishBizDynamicByInloan(handleAuthorId, projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_7_1, message);
         // 走完最后一个流程时处理状态为已通过
         // 2.大于等于300w财务总监审批同意
      } else if ("task_FinanceDirectorCheck".equals(workflowTaskDefKey)) {
         applyFinanceHandle.setFinanceDirectorRemark(message);
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_5);
         
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
         if (!isTfd)finishBizDynamicByInloan(handleAuthorId, projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_7_2, message);
         updateMakeLoans(projectId,applyFinanceHandle,handleAuthorId);
         // 驳回之后执行下一个任务，修改状态为审核中
      } else if (applyFinanceHandle.getApplyStatus() == Constants.APPLY_STATUS_3) {
         applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_4);
         financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
      }
      return res;
   }
   /**
    * 初始业务处理信息（主表），并且启动办理动态流程
    *@author:liangyanjun
    *@time:2016年12月7日上午9:53:21
    *@param projectId
    *@param handleUser
    *@throws TException
    */
   private void addBizHandle(int projectId,SysUser handleUser) throws TException {
       Project project = projectMapper.getProjectInfoById(projectId);//项目信息
       Product product = productMapper.getProductByPid(project.getProductId());//产品信息
       String processDefinitionKey = product.getBizHandleWorkProcessId();//工作流key
       int pmUserId = project.getPmUserId();
       SysUser pmUser = sysUserMapper.getSysUserByPid(pmUserId + "");//客户经理
       //添加业务处理信息（主表），并且启动办理动态流程
       Map<String, String> params = new HashMap<String, String>();
       params.put("userName", handleUser.getUserName());
       params.put("message", "");
       params.put("processDefinitionKey", processDefinitionKey);
       params.put("pmUserName", pmUser.getUserName());
       HandleInfoDTO handleInfoDTO = new HandleInfoDTO();
       handleInfoDTO.setProjectId(projectId);
       handleInfoDTO.setApplyHandleStatus(Constants.APPLY_HANDLE_STATUS_2);
       handleInfoDTO.setRecStatus(Constants.REC_STATUS_1);
       handleInfoDTO.setCreaterId(handleUser.getPid());
       handleInfoDTO.setForeclosureStatus(Constants.NO_FORECLOSURE);
       bizHandleService.addHandleInfo(handleInfoDTO, params);
    }
   
   protected void finishBizDynamicByInloan(int userId,int projectId,String dynamicNumber,String remark) throws TException{
           BizDynamic bizDynamic=new BizDynamic();
           bizDynamic.setProjectId(projectId);
           bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_INLOAN);
           bizDynamic.setDynamicNumber(dynamicNumber);
           bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_MAP.get(dynamicNumber));
           bizDynamic.setHandleAuthorId(userId);
           bizDynamic.setFinishDate(DateUtils.getCurrentDateTime());
           bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
           bizDynamic.setRemark(remark);
           bizDynamicService.addOrUpdateBizDynamic(bizDynamic);
    }
   /**
    * 更新放款数据，以及初始化业务处理信息（主表），并且启动办理动态流程
    *@author:liangyanjun
    *@time:2016年12月7日上午9:51:42
    *@param projectId
    *@param applyFinanceHandle
    *@param handleAuthorId
    *@throws TException
    */
    private void updateMakeLoans(int projectId,ApplyFinanceHandleDTO applyFinanceHandle,int handleAuthorId) throws TException {
        SysUser loginUser = sysUserMapper.getSysUserByPid(handleAuthorId + "");//当前登录用户
        //根据项目id查询业务办理数据
        HandleInfoDTO handleInfoQuery = new HandleInfoDTO();
        handleInfoQuery.setProjectId(projectId);
        List<HandleInfoDTO> handleInfoList = bizHandleService.findAllHandleInfoDTO(handleInfoQuery);
        if (handleInfoList == null || handleInfoList.isEmpty()) {//判断是否已经初始化了，没有初始化则重新初始化
            addBizHandle(projectId,loginUser);//初始化业务处理信息（主表），并且启动办理动态流程
            handleInfoList = bizHandleService.findAllHandleInfoDTO(handleInfoQuery);
        }
        int financeHandleId = applyFinanceHandle.getFinanceHandleId();
        FinanceHandleDTO updateFinanceHandle = financeHandleMapper.getFinanceHandleByProjectId(projectId);
        updateFinanceHandle.setStatus(Constants.REC_STATUS_ALREADY_LOAN);
        financeHandleMapper.updateFinanceHandle(updateFinanceHandle);
        
        //更新业务办理的财务到账为已到账
        HandleInfoDTO handleInfoDTO = handleInfoList.get(0);
        handleInfoDTO.setRecStatus(Constants.REC_STATUS_2);
        bizHandleService.updateHandleInfo(handleInfoDTO);
        logger.info("项目ID："+projectId+"更新业务办理的财务到账为已到账成功："+ handleInfoDTO);
        int handleId = handleInfoDTO.getPid();
        //把赎楼的创建时间设置为当前时间（用于计算差异等）
        HandleDynamicDTO handleDynamicDTO=new HandleDynamicDTO();
        handleDynamicDTO.setHandleId(handleId);
        handleDynamicDTO.setHandleFlowId(Constants.HANDLE_FLOW_ID_2);
        List<HandleDynamicDTO> updateDynamicDTOs = bizHandleService.findAllHandleDynamic(handleDynamicDTO);
        if (updateDynamicDTOs!=null&&!updateDynamicDTOs.isEmpty()) {
           HandleDynamicDTO updateDynamicDTO = updateDynamicDTOs.get(0);
           updateDynamicDTO.setCreateDate(DateUtils.getCurrentDateTime());
           bizHandleMapper.updateHandleDynamic(updateDynamicDTO);
        }
        //更新发放贷款数据
        HandleDynamicDTO handleDynamicQuery=new HandleDynamicDTO();
        handleDynamicQuery.setHandleId(handleInfoDTO.getPid());
        handleDynamicQuery.setHandleFlowId(Constants.HANDLE_FLOW_ID_1);
        HandleDynamicDTO updateHandleDynamicDTO = bizHandleService.findAllHandleDynamic(handleDynamicQuery).get(0);
        updateHandleDynamicDTO.setFinishDate(applyFinanceHandle.getRecDate());
        updateHandleDynamicDTO.setOperator(loginUser.getRealName());
        updateHandleDynamicDTO.setRemark(applyFinanceHandle.getRemark());
        updateHandleDynamicDTO.setCurrentHandleUserId(loginUser.getPid());
        bizHandleService.updateHandleDynamic(updateHandleDynamicDTO);
        logger.info("项目ID："+projectId+"更新发放贷款数据成功："+ updateHandleDynamicDTO);
        
        ApplyFinanceHandleDTO query=new ApplyFinanceHandleDTO();
        query.setFinanceHandleId(financeHandleId);
        query.setRecPro(Constants.REC_PRO_3);
        List<ApplyFinanceHandleDTO> list = financeHandleMapper.findAllApplyFinanceHandle(query);
        ApplyFinanceHandleDTO result = list.get(0);
        //修改预计回款时间
        ProjectForeclosure projectForeclosure = projectForeclosureMapper.getForeclosureByProjectId(projectId);
        String recDate = result.getRecDate();
        projectForeclosure.setReceDate(recDate);
        int loanDays = projectForeclosure.getLoanDays();
        Date payDate = DateUtils.addDay(DateUtils.stringToDate(recDate, "yyyy-MM-dd"), loanDays - 1);
        // 根据财务放款日期、贷款天数计算出还款日期,修改项目原先的还款日期与放款日期
        projectForeclosure.setPaymentDate(DateUtils.dateFormatByPattern(payDate, "yyyy-MM-dd"));
        projectForeclosureMapper.updateByPrimaryKey(projectForeclosure);
        
        // 把办理动态的流程状态设置有效,此处逻辑主要是兼容旧数据
        BizHandleWorkflowDTO bizHandleWorkflowQuery = new BizHandleWorkflowDTO();
        bizHandleWorkflowQuery.setHandleId(handleId);
        bizHandleWorkflowQuery.setStatus(Constants.STATUS_DISABLED);
        List<BizHandleWorkflowDTO> handleWorkflowList = bizHandleMapper.findAllBizHandleWorkflow(bizHandleWorkflowQuery);
        if (handleWorkflowList != null &&! handleWorkflowList.isEmpty()) {
            BizHandleWorkflowDTO updateBizHandleWorkflowDTO = handleWorkflowList.get(0);
            updateBizHandleWorkflowDTO.setStatus(Constants.STATUS_ENABLED);
            bizHandleMapper.updateBizHandleWorkflow(updateBizHandleWorkflowDTO);
        }
        
        Project project = projectMapper.getProjectInfoById(projectId);
        int isNeedHandle = project.getIsNeedHandle();
        //不需要办理赎楼，则自动完成业务办理工作流
        if (isNeedHandle==Constants.IS_NEED_HANDLE_2) {
           bizHandleService.finishAllHandleDynamicTask(projectId, "");
        }
        // 修改赎楼状态 已放款
        projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_11, "");
        finishBizDynamicByInloan(handleAuthorId, projectId, BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_7_3, "");
        
        String productNum = "";
        //通过projct找产品名称
        Product product = productMapper.findProductByProjectId(projectId);//产品信息
        productNum = product.getProductNumber();
        //交易/非交易无赎楼提房贷 放款结束修改赎楼状态为已赎楼
        if(Constants.JY_WSL_TFD.equals(productNum) || Constants.FJY_WSL_TFD.equals(productNum)){
        	finishForeclosure(loginUser.getUserName(), handleId);
        }
    }

   /**
    * 重置
    */
   @Override
   public void resetProject() {
      int projectId = super.getBean().getProjectId();
      ApplyFinanceHandleDTO applyFinanceHandle = financeHandleMapper.getByProjectIdAndRecPro(projectId, Constants.REC_PRO_3);
      if (applyFinanceHandle == null || applyFinanceHandle.getPid() <= 0) {
         return;
      }
      applyFinanceHandle.setApplyStatus(Constants.APPLY_STATUS_1);
      financeHandleMapper.updateApplyFinanceHandle(applyFinanceHandle);
   }

   /**
    * 处理分支
    *@author:liangyanjun
    *@time:2016年8月8日下午3:16:26
    */
   @Override
   public void handleBranch(Task task, Map<String, Object> taskVariables) {
      // 资金主管审核节点：判断贷款金额是否需要财务总监审核
      if ("task_FundManagerCheck".equals(task.getTaskDefinitionKey())) {
         int refId = super.getBean().getRefId();
         ApplyFinanceHandleDTO applyFinanceHandle = financeHandleMapper.getApplyFinanceHandleById(refId);
         if (applyFinanceHandle == null || applyFinanceHandle.getPid() <= 0) {
            return;
         }
         double recMoney = applyFinanceHandle.getRecMoney();
         taskVariables.put("makeLoansMoney", recMoney);
      }
      
   }
    /**
     * 完成赎楼办理动态
     * @param loginUserName
     * @param handleId
     * @throws TException
     */
	private void finishForeclosure(String loginUserName,int handleId) throws TException {
		HandleDynamicDTO query=new HandleDynamicDTO();
		query.setHandleId(handleId);
		query.setHandleFlowId(Constants.HANDLE_FLOW_ID_2);
		List<HandleDynamicDTO> list = bizHandleService.findAllHandleDynamic(query);
		if (list!=null&&list.size()>0) {
			bizHandleService.finishHandleDynamicTask(list.get(0),
					"");
		}
	}
}
