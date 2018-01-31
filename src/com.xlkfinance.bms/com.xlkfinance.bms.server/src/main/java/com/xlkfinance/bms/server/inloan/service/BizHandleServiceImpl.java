package com.xlkfinance.bms.server.inloan.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.activiti.engine.task.Task;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleIndexDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizHandleService.Iface;
import com.xlkfinance.bms.rpc.inloan.BizHandleWorkflowDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.ForeclosureIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicMap;
import com.xlkfinance.bms.rpc.inloan.HandleFlowDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.HouseBalanceDTO;
import com.xlkfinance.bms.rpc.inloan.OrgBizHandlePage;
import com.xlkfinance.bms.rpc.inloan.RefundDetailsDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService;
import com.xlkfinance.bms.rpc.system.BizFile;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.ForeclosureRepaymentMapper;
import com.xlkfinance.bms.server.inloan.mapper.IntegratedDeptMapper;
import com.xlkfinance.bms.server.system.mapper.BizFileMapper;
import com.xlkfinance.bms.server.system.mapper.SysOrgInfoMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;
import com.xlkfinance.bms.server.workflow.WorkFlowHelper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日下午6:34:02 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务办理：<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("BizHandleServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.BizHandleService")
public class BizHandleServiceImpl implements Iface {
   
   private Logger logger = LoggerFactory.getLogger(BizHandleServiceImpl.class);
   
   @Resource(name = "bizHandleMapper")
   private BizHandleMapper bizHandleMapper;
   
   @Resource(name = "bizFileMapper")
   private BizFileMapper bizFileMapper;
   
   @Resource(name = "workflowServiceImpl")
   private WorkflowService.Iface workflowServiceImpl;
   
   @Resource(name = "workFlowHelper")
   private WorkFlowHelper workFlowHelper;
   
   @Resource(name = "projectMapper")
   private ProjectMapper projectMapper;
   
   @Resource(name = "sysUserMapper")
   private SysUserMapper sysUserMapper;
   
   @Resource(name = "sysUserServiceImpl")
   private SysUserService.Iface SysUserServiceImpl;
   
   @SuppressWarnings("rawtypes")
   @Resource(name = "sysOrgInfoMapper")
   private SysOrgInfoMapper sysOrgInfoMapper;
   
   @Resource(name = "refundFeeServiceImpl")
   private RefundFeeService.Iface refundFeeServiceImpl;
   
   @Resource(name="projectServiceImpl")
   private ProjectService.Iface projectServiceImpl;
   
   @Resource(name = "foreclosureRepaymentMapper")
   private ForeclosureRepaymentMapper repaymentMapper;
   
   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;
   
   @Resource(name = "integratedDeptMapper")
   private IntegratedDeptMapper integratedDeptMapper;
   /**
    * 业务受理列表
    *@author:liangyanjun
    *@time:2016年1月15日下午6:30:31
    *@param financeHandleDTO
    *@return
    */
   @Override
   public List<ApplyHandleIndexDTO> findAllApplyHandleIndex(ApplyHandleIndexDTO applyHandleIndexDTO) throws TException {
      return bizHandleMapper.findAllApplyHandleIndex(applyHandleIndexDTO);
   }

   /**
    * 业务受理总数
    *@author:liangyanjun
    *@time:2016年1月15日下午6:30:35
    *@param financeHandleDTO
    *@return
    */
   @Override
   public int getApplyHandleIndexTotal(ApplyHandleIndexDTO applyHandleIndexDTO) throws TException {
      return bizHandleMapper.getApplyHandleIndexTotal(applyHandleIndexDTO);
   }

   /**
    * 业务处理信息列表
    *@author:liangyanjun
    *@time:2016年1月18日下午5:14:06
    *@param handleInfoDTO
    *@return
    */
   @Override
   public List<HandleInfoDTO> findAllHandleInfoDTO(HandleInfoDTO handleInfoDTO) throws TException {
      return bizHandleMapper.findAllHandleInfoDTO(handleInfoDTO);
   }

   /**
    * 业务处理信息总数
    *@author:liangyanjun
    *@time:2016年1月18日下午5:14:16
    *@param handleInfoDTO
    *@return
    */
   @Override
   public int getHandleInfoDTOTotal(HandleInfoDTO handleInfoDTO) throws TException {
      return bizHandleMapper.getHandleInfoDTOTotal(handleInfoDTO);
   }

   /**
    * 添加业务处理信息（主表）
    *@author:liangyanjun
    *@time:2016年1月18日上午10:26:12
    *@param handleInfoDTO
    */
   @Override
   public boolean addHandleInfo(HandleInfoDTO handleInfoDTO,Map<String,String> paramMap) throws TException {
      logger.info("添加业务处理信息（主表）：入参:"+ handleInfoDTO.toString() +",paramMap:"+paramMap);
      try {
         //添加业务处理信息（主表）
         bizHandleMapper.addHandleInfo(handleInfoDTO);
         int handleId = handleInfoDTO.getPid();
         int createrId = handleInfoDTO.getCreaterId();
         int projectId = handleInfoDTO.getProjectId();
         //启动工作流
         String processInstanceId = workflowServiceImpl.executeStartOfCommon(paramMap);
        
         //添加业务办理和任务关联(初始化的时候状态是无效的，在业务受理申请的时候设置成有效)
         BizHandleWorkflowDTO bizHandleWorkflowDTO = new BizHandleWorkflowDTO();
         bizHandleWorkflowDTO.setHandleId(handleId);
         bizHandleWorkflowDTO.setExecutionId(processInstanceId);
         bizHandleWorkflowDTO.setProjectId(projectId);
         bizHandleWorkflowDTO.setStatus(Constants.STATUS_ENABLED);
         bizHandleMapper.addBizHandleWorkflow(bizHandleWorkflowDTO);
       
         //关联贷前申请业务处理信息
         ApplyHandleInfoDTO applyHandleInfoQuery=new ApplyHandleInfoDTO();
         applyHandleInfoQuery.setProjectId(projectId);
         List<ApplyHandleInfoDTO> applyHandleInfoDTOs = bizHandleMapper.findAllApplyHandleInfo(applyHandleInfoQuery);
         if (applyHandleInfoDTOs==null||applyHandleInfoDTOs.isEmpty()) {
            ApplyHandleInfoDTO newApplyHandleInfoDTO = new ApplyHandleInfoDTO();
            newApplyHandleInfoDTO.setProjectId(projectId);
            newApplyHandleInfoDTO.setHandleId(handleId);
            newApplyHandleInfoDTO.setCreaterId(createrId);
            bizHandleMapper.addApplyHandleInfo(newApplyHandleInfoDTO);
         }else{
            ApplyHandleInfoDTO updateApplyHandleInfoDTO = applyHandleInfoDTOs.get(0);
            updateApplyHandleInfoDTO.setHandleId(handleId);
            bizHandleMapper.updateApplyHandleInfo(updateApplyHandleInfoDTO);
         }
         //添加两次赎楼及余额回转信息
         for (int count = 1; count <= 2; count++) {
            HouseBalanceDTO houseBalanceDTO = new HouseBalanceDTO();
            houseBalanceDTO.setHandleId(handleId);
            houseBalanceDTO.setCreaterId(createrId);
            houseBalanceDTO.setBalanceConfirm(Constants.NO_BALANCE_CONFIRM);
            houseBalanceDTO.setCount(count);
            bizHandleMapper.addHouseBalance(houseBalanceDTO);
         }
         //添加办理动态
         HandleFlowDTO handleFlowQuery = new HandleFlowDTO();
         handleFlowQuery.setRows(bizHandleMapper.getHandleFlowTotal(handleFlowQuery));
         List<HandleFlowDTO> handleFlowList = bizHandleMapper.findAllHandleFlow(handleFlowQuery);
         for (HandleFlowDTO hf : handleFlowList) {
            HandleDynamicDTO handleDynamicDTO = new HandleDynamicDTO();
            handleDynamicDTO.setHandleId(handleId);
            handleDynamicDTO.setHandleFlowId(hf.getPid());
            handleDynamicDTO.setCreaterId(createrId);
            bizHandleMapper.addHandleDynamic(handleDynamicDTO);
         }
        
         //添加财务退款明细
         for (int i = 1; i <= Constants.REFUND_PRO_MAP.size(); i++) {
            RefundDetailsDTO refundDetailsDTO = new RefundDetailsDTO();
            refundDetailsDTO.setHandleId(handleId);
            refundDetailsDTO.setCreaterId(createrId);
            refundDetailsDTO.setRefundPro(i);
            bizHandleMapper.addRefundDetails(refundDetailsDTO);
         }
      } catch (Exception e) {
         logger.error("添加业务处理信息（主表）：入参：handleInfoDTO:"+ handleInfoDTO +",paramMap:"+paramMap+"。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }

   @Override
   public HandleInfoDTO getHandleInfoById(int pid) throws TException {
      HandleInfoDTO handleInfoDTO = bizHandleMapper.getHandleInfoById(pid);
      if (handleInfoDTO==null) {
         return new HandleInfoDTO();
      }
      return handleInfoDTO;
   }
   /**
    * 根据项目ID获取业务处理信息
    *@author:liangyanjun
    *@time:2016年12月26日上午9:51:18
    *@param projectId
    *@return
    *@throws TException
    */
   @Override
   public HandleInfoDTO getHandleInfoByProjectId(int projectId) throws TException {
      HandleInfoDTO handleInfoDTO=new HandleInfoDTO();
      handleInfoDTO.setProjectId(projectId);
      List<HandleInfoDTO> list = bizHandleMapper.findAllHandleInfoDTO(handleInfoDTO);
      if (list==null||list.isEmpty()) {
         return new HandleInfoDTO();
      }
      return list.get(0);
   }

   /**
    * 根据ID更新业务处理信息
    *@author:liangyanjun
    *@time:2016年1月18日上午10:26:59
    *@param handleInfoDTO
    */
   @Override
   public boolean updateHandleInfo(HandleInfoDTO handleInfoDTO) throws TException {
      bizHandleMapper.updateHandleInfo(handleInfoDTO);
      //业务办理归档时，同时修改赎楼状态（全局的）   已归档
      if (handleInfoDTO.getApplyHandleStatus() == Constants.APPLY_HANDLE_STATUS_4) {
         int projectId = handleInfoDTO.getProjectId();
         //修改赎楼状态（全局的）   已归档
         projectMapper.updateProjectForeStatusByPid(projectId, Constants.FORECLOSURE_STATUS_13, "");
      } 
      return true;
   }

   /**
    * 查询业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:01:17
    *@param applyHandleInfoDTO
    *@return
    */
   @Override
   public List<ApplyHandleInfoDTO> findAllApplyHandleInfo(ApplyHandleInfoDTO applyHandleInfoDTO) throws TException {
      return bizHandleMapper.findAllApplyHandleInfo(applyHandleInfoDTO);
   }

   /**
    * 查询业务处理信息总数
    *@author:liangyanjun
    *@time:2016年1月17日上午12:01:30
    *@param applyHandleInfoDTO
    *@return
    */
   @Override
   public int getApplyHandleInfoTotal(ApplyHandleInfoDTO applyHandleInfoDTO) throws TException {
      return bizHandleMapper.getApplyHandleInfoTotal(applyHandleInfoDTO);
   }

   /**
    * 添加申请业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:02:02
    *@param applyHandleInfoDTO
    */
   @Override
   public boolean addApplyHandleInfo(ApplyHandleInfoDTO applyHandleInfoDTO) throws TException {
      int row = 0;
      try {
    	  row = bizHandleMapper.addApplyHandleInfo(applyHandleInfoDTO);
		} catch (Exception e) {
			 logger.error("添加申请业务处理信息：入参：applyHandleInfoDTO:"+ applyHandleInfoDTO+"。错误：" + ExceptionUtil.getExceptionMessage(e));
	         e.printStackTrace();
	         throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
		}
      
      return row>0;
   }

   @Override
   public ApplyHandleInfoDTO getApplyHandleInfoById(int pid) throws TException {
	   ApplyHandleInfoDTO applyHandleInfoDTO = new ApplyHandleInfoDTO();
	   applyHandleInfoDTO.setPid(pid);
	   applyHandleInfoDTO.setPage(1);
	   applyHandleInfoDTO.setRows(100);
	   List<ApplyHandleInfoDTO> result = bizHandleMapper.findAllApplyHandleInfo(applyHandleInfoDTO);
	   if(result != null && result.size()>0){
		   return result.get(0);
	   }
	   return new ApplyHandleInfoDTO();
   }

   /**
    * 根据ID更新申请业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:02:57
    *@param applyHandleInfoDTO
    */
   @Override
   public boolean updateApplyHandleInfo(ApplyHandleInfoDTO applyHandleInfoDTO) throws TException {
      logger.info("根据ID更新申请业务处理信息：入参:"+ applyHandleInfoDTO.toString() );
      try {
         //更新申请业务处理信息
         bizHandleMapper.updateApplyHandleInfo(applyHandleInfoDTO);
         /*//查询业务办理处理信息
         int handleId = applyHandleInfoDTO.getHandleId();
         HandleInfoDTO handleInfoQuery = new HandleInfoDTO();
         handleInfoQuery.setPid(handleId);
         List<HandleInfoDTO> handleInfoList = bizHandleMapper.findAllHandleInfoDTO(handleInfoQuery);
         if (handleInfoList == null || handleInfoList.isEmpty()) {
            throw new RuntimeException("业务办理处理信息不存在，入参：" + applyHandleInfoDTO);
         }
         HandleInfoDTO updateHandleInfo = handleInfoList.get(0);
         if (Constants.APPLY_HANDLE_STATUS_1 == updateHandleInfo.getApplyHandleStatus()) {
            //更新业务办理处理信息的申请状态为“已申请”
            updateHandleInfo.setApplyHandleStatus(Constants.APPLY_HANDLE_STATUS_2);
         }
         bizHandleMapper.updateHandleInfo(updateHandleInfo);*/
      } catch (Exception e) {
         logger.error("根据ID更新申请业务处理信息：入参：applyHandleInfoDTO:"+ applyHandleInfoDTO+"。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }
   /**
    * 查询赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午1:12:08
    *@param houseBalanceDTO
    *@return
    */
   @Override
   public List<HouseBalanceDTO> findAllHouseBalance(HouseBalanceDTO houseBalanceDTO) throws TException {
      return bizHandleMapper.findAllHouseBalance(houseBalanceDTO);
   }

   /**
    * 查询赎楼及余额回转信息总数
    *@author:liangyanjun
    *@time:2016年1月17日上午1:12:55
    *@param houseBalanceDTO
    *@return
    */
   @Override
   public int getHouseBalanceTotal(HouseBalanceDTO houseBalanceDTO) throws TException {
      return bizHandleMapper.getHouseBalanceTotal(houseBalanceDTO);
   }
   /**
    * 根据项目id 查询赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年3月14日上午10:33:04
    *@param projectId
    *@return
    */
   @Override
   public List<HouseBalanceDTO> getHouseBalanceListByProjectId(int projectId){
      return bizHandleMapper.getHouseBalanceListByProjectId(projectId);
   }
   /**
    * 添加赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:46:39
    *@param houseBalanceDTO
    */
   @Override
   public boolean addHouseBalance(HouseBalanceDTO houseBalanceDTO) throws TException {
      bizHandleMapper.addHouseBalance(houseBalanceDTO);
      return true;
   }

   @Override
   public HouseBalanceDTO getHouseBalanceById(int pid) throws TException {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * 根据ID更新赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午1:01:24
    *@param houseBalanceDTO
    */
   @Override
   public boolean updateHouseBalance(HouseBalanceDTO houseBalanceDTO) throws TException {
      bizHandleMapper.updateHouseBalance(houseBalanceDTO);
      return true;
   }

   /**
    * 查询财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:18:57
    *@param refundDetailsDTO
    *@return
    */
   @Override
   public List<RefundDetailsDTO> findAllRefundDetails(RefundDetailsDTO refundDetailsDTO) throws TException {
      return bizHandleMapper.findAllRefundDetails(refundDetailsDTO);
   }

   /**
    * 查询财务退款明细 总数
    *@author:liangyanjun
    *@time:2016年1月17日上午2:19:35
    *@param refundDetailsDTO
    *@return
    */
   @Override
   public int getRefundDetailsTotal(RefundDetailsDTO refundDetailsDTO) throws TException {
      return bizHandleMapper.getRefundDetailsTotal(refundDetailsDTO);
   }
   
   /**
    * 根据项目id 查询财务退款明细
    *@author:liangyanjun
    *@time:2016年3月14日上午10:51:09
    *@param projectId
    *@return
    */
   @Override
   public List<RefundDetailsDTO> getRefundDetailsListByProjectId(int projectId,List<Integer> refundPros) throws TException {
      return bizHandleMapper.getRefundDetailsListByProjectId(projectId,refundPros);
   }

   /**
    * 添加财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:20:16
    *@param refundDetailsDTO
    */
   @Override
   public boolean addRefundDetails(RefundDetailsDTO refundDetailsDTO) throws TException {
      bizHandleMapper.addRefundDetails(refundDetailsDTO);
      return true;
   }

   @Override
   public RefundDetailsDTO getRefundDetailsById(int pid) throws TException {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * 根据ID更新财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:20:52
    *@param refundDetailsDTO
    */
   @Override
   public boolean updateRefundDetails(RefundDetailsDTO refundDetailsDTO) throws TException {
      bizHandleMapper.updateRefundDetails(refundDetailsDTO);
      return true;
   }

   /**
    * 查询办理流程条目
    *@author:liangyanjun
    *@time:2016年1月17日上午2:24:33
    *@param handleFlowDTO
    *@return
    */
   @Override
   public List<HandleFlowDTO> findAllHandleFlow(HandleFlowDTO handleFlowDTO) throws TException {
      return bizHandleMapper.findAllHandleFlow(handleFlowDTO);
   }

   /**
    * 查询办理流程条目总数
    *@author:liangyanjun
    *@time:2016年1月17日上午2:42:49
    *@param handleFlowDTO
    *@return
    */
   @Override
   public int getHandleFlowTotal(HandleFlowDTO handleFlowDTO) throws TException {
      return bizHandleMapper.getHandleFlowTotal(handleFlowDTO);
   }

   /**
    * 查询办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午1:58:56
    *@param handleDynamicDTO
    *@return
    */
   @Override
   public List<HandleDynamicDTO> findAllHandleDynamic(HandleDynamicDTO handleDynamicDTO) throws TException {
      return bizHandleMapper.findAllHandleDynamic(handleDynamicDTO);
   }

   /**
    * 查询办理动态总数
    *@author:liangyanjun
    *@time:2016年1月17日下午1:59:29
    *@param handleDynamicDTO
    *@return
    */
   @Override
   public int getHandleDynamicTotal(HandleDynamicDTO handleDynamicDTO) throws TException {
      return bizHandleMapper.getHandleDynamicTotal(handleDynamicDTO);
   }

   /**
    * 添加办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午2:00:06
    *@param handleDynamicDTO
    */
   @Override
   public boolean addHandleDynamic(HandleDynamicDTO handleDynamicDTO) throws TException {
      bizHandleMapper.addHandleDynamic(handleDynamicDTO);
      return true;
   }

   @Override
   public HandleDynamicDTO getHandleDynamicById(int pid) throws TException {
      HandleDynamicDTO handleDynamicQuery=new HandleDynamicDTO();
      handleDynamicQuery.setPid(pid);
      List<HandleDynamicDTO> list = bizHandleMapper.findAllHandleDynamic(handleDynamicQuery);
      
      if (list==null||list.isEmpty()) {
         return new HandleDynamicDTO();
      }
      return list.get(0);
   }

   /**
    * 根据ID更新办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午2:00:39
    *@param handleDynamicDTO
    */
   @Override
   public boolean updateHandleDynamic(HandleDynamicDTO handleDynamicDTO) throws TException {
      bizHandleMapper.updateHandleDynamic(handleDynamicDTO);
      return true;
   }
   
   /**
    * 根据ID更新办理动态并且完成任务
    *@author:liangyanjun
    *@time:2016年1月17日下午2:00:39
    *@param handleDynamicDTO
    */
   @Override
   public boolean updateHandleDynamicAndFinishTask(HandleDynamicDTO handleDynamicDTO,String loginUserName) throws TException {
      try {
         logger.info("根据ID更新办理动态并且完成任务：入参:"+ handleDynamicDTO.toString() +",loginUserName:"+loginUserName);
         //根据ID更新办理动态
         bizHandleMapper.updateHandleDynamic(handleDynamicDTO);
         
         finishHandleDynamicTask(handleDynamicDTO, loginUserName);
      } catch (Exception e) {
         logger.error("修改办理状态失败：入参：handleDynamicDTO:"+ handleDynamicDTO +",loginUserName:"+loginUserName+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
      }
      //业务办理和任务关联的状态设置为失效
//      bizHandleWorkflowDTO.setStatus(Constants.STATUS_DISABLED);
//      bizHandleMapper.updateBizHandleWorkflow(bizHandleWorkflowDTO);
      return true;
   }
   /**
    * 自动完成业务办理工作流程(除放款外)
    *@author:liangyanjun
    *@time:2016年8月9日下午4:48:39
    *@param projectId
    *@param loginUserName
    *@return
    *@throws TException
    */
   @Override
   public boolean finishAllHandleDynamicTask(int projectId, String loginUserName) throws TException{
         int handleId = getHandleInfoByProjectId(projectId).getPid();
         HandleDynamicDTO query = new HandleDynamicDTO();
         query.setHandleId(handleId);
         List<HandleDynamicDTO> list = bizHandleMapper.findAllHandleDynamic(query);
         for (HandleDynamicDTO dto : list) {
            int handleFlowId = dto.getHandleFlowId();
            if (handleFlowId==Constants.HANDLE_FLOW_ID_1||handleFlowId==Constants.HANDLE_FLOW_ID_10) {
               continue;
            }
            try {
               loginUserName = bizHandleMapper.getHandleUserName(handleId);
            finishHandleDynamicTask(dto, loginUserName);
            } catch (Exception e) {
               logger.error("自动完成业务办理工作流程失败：入参：projectId:"+projectId +",loginUserName:"+loginUserName+",handleFlowId:"+handleFlowId+"。错误：" + ExceptionUtil.getExceptionMessage(e));
            }
         }
      return true;
   }
   /**
    * 完成办理动态
    *@author:liangyanjun
    *@time:2016年5月9日下午5:29:29
    *@param handleDynamicDTO
    *@param loginUserName
    *@throws TException
    *@throws ThriftServiceException
    */
   @Override
   @Transactional
   public boolean finishHandleDynamicTask(HandleDynamicDTO handleDynamicDTO, String loginUserName) throws TException, ThriftServiceException {
      //查询当前业务办理的任务节点
      BizHandleWorkflowDTO bizHandleWorkflowQuery = new BizHandleWorkflowDTO();
      int handleId = handleDynamicDTO.getHandleId();
      bizHandleWorkflowQuery.setHandleId(handleId);
      bizHandleWorkflowQuery.setUserName(loginUserName);
      bizHandleWorkflowQuery.setStatus(Constants.STATUS_ENABLED);
      List<BizHandleWorkflowDTO> handleWorkflowList = bizHandleMapper.findAllBizHandleWorkflow(bizHandleWorkflowQuery);
      BizHandleWorkflowDTO bizHandleWorkflowDTO = handleWorkflowList.get(0);
      
      //获取项目信息
      int projectId = bizHandleWorkflowDTO.getProjectId();
      Project project = projectServiceImpl.getProjectInfoById(projectId);//项目信息
      int pmUserId = project.getPmUserId();
      SysUser pmUser = sysUserMapper.getSysUserByPid(pmUserId + "");//客户经理
      //设置任务参数
       String executionId = bizHandleWorkflowDTO.getExecutionId();
       String taskId = bizHandleWorkflowDTO.getTaskId();
       //如果是不需要业务办理时，系统会自动完成任务，在最后的任务taskId为空，此判断为了阻止异常
      int isNeedHandle = project.getIsNeedHandle();
      if (isNeedHandle==Constants.IS_NEED_HANDLE_1||(isNeedHandle == Constants.IS_NEED_HANDLE_2 && !StringUtil.isBlank(taskId))) {
         Map<String, String> paramMap = new HashMap<String, String>();
         paramMap.put("userName", pmUser.getUserName());
         paramMap.put("message", "");
         paramMap.put("taskId", taskId);
         paramMap.put("executionId", executionId);
         // 完成当前办理动态任务
         workflowServiceImpl.completeOfCommon(paramMap);
      }
      //办理动态任务走到末尾，则把办理动态的申请状态改为已完成,修改项目状态为业务办理已完成
      Task task = null;
      try {
         //走到流程结尾，这行代码会抛出IndexOutOfBoundsException异常
         task = workFlowHelper.getTaskByProcessInstanceId(executionId);
         //查询下一个办理动态数据
         String handleFlowId = workflowServiceImpl.getFormValueStr(task.getId(), "handleFlowId");
         HandleDynamicDTO handleDynamicQuery = new HandleDynamicDTO();
         handleDynamicQuery.setHandleFlowId(Integer.parseInt(handleFlowId));
         handleDynamicQuery.setHandleId(handleId);
         List<HandleDynamicDTO> nextDynamicList = bizHandleMapper.findAllHandleDynamic(handleDynamicQuery);
         if (nextDynamicList != null && !nextDynamicList.isEmpty()) {
            //设置下一个办理动态数据的创建时间为当前时间（该时间的作用，计算差异预警的天数）
            HandleDynamicDTO nextDynamic = nextDynamicList.get(0);
            nextDynamic.setCreateDate(DateUtils.getCurrentDateTime());
            bizHandleMapper.updateHandleDynamic(nextDynamic);
         }
      } catch (IndexOutOfBoundsException e) {
      } finally {
         if (task == null) {
            HandleInfoDTO handleInfoQuery = new HandleInfoDTO();
            handleInfoQuery.setPid(handleId);
            List<HandleInfoDTO> handleInfoList = bizHandleMapper.findAllHandleInfoDTO(handleInfoQuery);
            if (handleInfoList == null || handleInfoList.size() == 0) {
               throw new RuntimeException("handleInfoList is null:params=" + handleId);
            }
           
            //修改 办理动态的申请状态改为已完成
            HandleInfoDTO updateInfoDTO = handleInfoList.get(0);
            updateInfoDTO.setApplyHandleStatus(Constants.APPLY_HANDLE_STATUS_3);
            bizHandleMapper.updateHandleInfo(updateInfoDTO);

            //修改项目状态为业务办理已完成
            projectMapper.updateProjectForeStatusByPid(updateInfoDTO.getProjectId(), Constants.FORECLOSURE_STATUS_12, "");
            RefundFeeDTO refundFeeQuery=new RefundFeeDTO();
            refundFeeQuery.setType(Constants.REFUND_FEE_TYPE_1);
            refundFeeQuery.setProjectId(projectId);
            List<RefundFeeDTO> refundFeeList = refundFeeServiceImpl.findAllRefundFee(refundFeeQuery);
            if (refundFeeList==null||refundFeeList.isEmpty()) {
               int innerOrOut = project.getInnerOrOut();//内外单  1：内单 2：外单
               //业务办理完成之后，给退手续费增加一条初始化数据:手续费（内单）=（财务收费）手续费 *0.5、手续费（外单）=（财务收费）手续费 *1
               RefundFeeDTO dto = new RefundFeeDTO();
               dto.setProjectId(projectId);
               dto.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_1);
               dto.setType(Constants.REFUND_FEE_TYPE_1);
               List recPros = Arrays.asList(Constants.REC_PRO_2);
               double poundage = financeHandleMapper.getRecMoney(projectId, recPros);
               if (innerOrOut == Constants.INNER_ORDER) {
                  dto.setReturnFee(poundage * 0.5);
               } else {
                  dto.setReturnFee(poundage);
               }
               refundFeeServiceImpl.addRefundFee(dto);
            }
         }

      }
      return true;
   }

   /**
    * 查询差异预警处理
    *@author:liangyanjun
    *@time:2016年1月17日下午4:00:38
    *@param handleDifferWarnDTO
    *@return
    */
   @Override
   public List<HandleDifferWarnDTO> findAllHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      return bizHandleMapper.findAllHandleDifferWarn(handleDifferWarnDTO);
   }

   /**
    * 查询差异预警处理总数
    *@author:liangyanjun
    *@time:2016年1月17日下午4:01:08
    *@param handleDifferWarnDTO
    *@return
    */
   @Override
   public int getHandleDifferWarnTotal(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      return bizHandleMapper.getHandleDifferWarnTotal(handleDifferWarnDTO);
   }
   
   
   /**
    * 查询历史预警总数
    */
	public int getHisHandleDifferWarnCount(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
		return bizHandleMapper.getHisHandleDifferWarnCount(handleDifferWarnDTO);
	}
   

   /**
    * 添加差异预警处理表
    *@author:liangyanjun
    *@time:2016年1月17日下午4:01:52
    *@param handleDifferWarnDTO
    */
   @Override
   public boolean addHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      bizHandleMapper.addHandleDifferWarn(handleDifferWarnDTO);
      return true;
   }

   @Override
   public HandleDifferWarnDTO getHandleDifferWarnById(int pid) throws TException {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * 根据ID更新差异预警处理表
    *@author:liangyanjun
    *@time:2016年1月17日下午4:02:30
    *@param handleDifferWarnDTO
    */
   @Override
   public boolean updateHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      bizHandleMapper.updateHandleDifferWarn(handleDifferWarnDTO);
      return true;
   }
   
   /**
    * 查询办理动态文件关联
    *@author:liangyanjun
    *@time:2016年1月25日下午6:00:32
    *@param handleDynamicFileDTO
    *@return
    */
   @Override
   public List<HandleDynamicFileDTO> findAllHandleDynamicFile(HandleDynamicFileDTO handleDynamicFileDTO) throws TException {
      return bizHandleMapper.findAllHandleDynamicFile(handleDynamicFileDTO);
   }
   
   /**
    * 查询办理动态文件关联总数
    *@author:liangyanjun
    *@time:2016年1月25日下午6:01:07
    *@param handleDynamicFileDTO
    *@return
    */
   @Override
   public int getHandleDynamicFileTotal(HandleDynamicFileDTO handleDynamicFileDTO) throws TException {
      return bizHandleMapper.getHandleDynamicFileTotal(handleDynamicFileDTO);
   }

   /**
    * 添加办理动态文件关联
    *@author:liangyanjun
    *@time:2016年1月25日下午6:01:48
    *@param handleDynamicFileDTO
    */
   @Override
   public boolean addHandleDynamicFile(HandleDynamicFileDTO handleDynamicFileDTO, BizFile bizFile) throws TException {
      // 保存文件信息
      bizFileMapper.saveBizFile(bizFile);
      handleDynamicFileDTO.setFileId(bizFile.getPid());
      // 保存办理动态文件关联
      bizHandleMapper.addHandleDynamicFile(handleDynamicFileDTO);
      return true;
   }
   @Override
   public boolean addHandleDynamicFileOfComm(HandleDynamicFileDTO handleDynamicFileDTO) throws TException {
      // 保存办理动态文件关联
      bizHandleMapper.addHandleDynamicFile(handleDynamicFileDTO);
      return true;
   }

   @Override
   public HandleDynamicFileDTO getHandleDynamicFileById(int pid) throws TException {
      // TODO Auto-generated method stub
      return null;
   }

   /**
    * 根据ID更新办理动态文件关联
    *@author:liangyanjun
    *@time:2016年1月25日下午6:02:26
    *@param handleDynamicFileDTO
    */
   @Override
   public boolean updateHandleDynamicFile(HandleDynamicFileDTO handleDynamicFileDTO) throws TException {
      bizHandleMapper.updateHandleDynamicFile(handleDynamicFileDTO);
      return true;
   }
   /**
    * 查询业务办理和任务关联
    *@author:liangyanjun
    *@time:2016年1月21日下午7:15:20
    *@param bizHandleWorkflowDTO
    *@return
    */
   @Override
   public List<BizHandleWorkflowDTO> findAllBizHandleWorkflow(BizHandleWorkflowDTO bizHandleWorkflowDTO) throws TException {
      return bizHandleMapper.findAllBizHandleWorkflow(bizHandleWorkflowDTO);
   }
   /**
    * 添加业务办理和任务关联
    *@author:liangyanjun
    *@time:2016年1月21日下午7:15:17
    *@param bizHandleWorkflowDTO
    *@return
    */
   @Override
   public boolean addBizHandleWorkflow(BizHandleWorkflowDTO bizHandleWorkflowDTO) throws TException {
      bizHandleMapper.addBizHandleWorkflow(bizHandleWorkflowDTO);
      return true;
   }
   /**
    * 根据ID更新业务办理和任务关联
    *@author:liangyanjun
    *@time:2016年1月21日下午7:15:14
    *@param bizHandleWorkflowDTO
    *@return
    */
   @Override
   public boolean updateBizHandleWorkflow(BizHandleWorkflowDTO bizHandleWorkflowDTO) throws TException {
      bizHandleMapper.updateBizHandleWorkflow(bizHandleWorkflowDTO);
      return true;
   }
   
   /**
    * 根据业务处理ID和用户名，获取可以处理的办理流程条目ID集合
    *@author:liangyanjun
    *@time:2016年1月22日上午10:39:40
    *@param handleId
    *@param userName
    *@return
    */
   @Override
   public List<Integer> getCanHandleFlowByHandleId(int handleId,String userName) throws TException {
      List<Integer> resultList = new ArrayList<Integer>();
      try {
         //
         BizHandleWorkflowDTO bizHandleWorkflowQuery = new BizHandleWorkflowDTO();
         bizHandleWorkflowQuery.setHandleId(handleId);
         bizHandleWorkflowQuery.setUserName(userName);
         bizHandleWorkflowQuery.setStatus(Constants.STATUS_ENABLED);
         List<BizHandleWorkflowDTO> bizHandleWorkflowList = bizHandleMapper.findAllBizHandleWorkflow(bizHandleWorkflowQuery);
         if (bizHandleWorkflowList == null || bizHandleWorkflowList.isEmpty()) {
            return resultList;
         }
         //
         for (BizHandleWorkflowDTO dto : bizHandleWorkflowList) {
            String handleFlowId = workflowServiceImpl.getFormValueStr(dto.getTaskId(), "handleFlowId");
            if (StringUtil.isBlank(handleFlowId))return null;
            resultList.add(Integer.parseInt(handleFlowId));
         }
      } catch (Exception e) {
         e.printStackTrace();
         throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
      }
      return resultList;
   }

   /**
    * 根据用户名(任务处理人)，获取可处理的办理动态id的list
    *@author:liangyanjun
    *@time:2016年2月4日下午3:48:23
    *@param userName
    *@return
    */
   @Override
   public List<Integer> getCanHandleDynamicIds(String userName) throws TException {
      return bizHandleMapper.getCanHandleDynamicIds(userName);
   }
   /**
    * 根据用户id（任务发起人），获取未处理（需要预警）的办理办理信息集合，用途：业务经理在登录系统的时候，查看自己申请的业务办理有没有未完成的办理动态
    *@author:liangyanjun
    *@time:2016年2月4日下午6:58:00
    *@param userId
    *@return
    */
   @Override
   public List<HandleDifferWarnDTO> getNeedHandleWarn(int userId) throws TException {
      return bizHandleMapper.getNeedHandleWarn(userId);
   }
   /**
    *@author:liangyanjun
    *@time:2016年5月14日上午10:04:37
    */
   @Override
   public List<HandleDifferWarnIndexDTO> queryNeedHandleWarnIndex(HandleDifferWarnIndexDTO handleDifferWarnIndexDTO) throws TException {
      return bizHandleMapper.queryNeedHandleWarnIndex(handleDifferWarnIndexDTO);
   }

   /**
    *@author:liangyanjun
    *@time:2016年5月14日上午10:04:37
    */
   @Override
   public int getNeedHandleWarnIndexTotal(HandleDifferWarnIndexDTO handleDifferWarnIndexDTO) throws TException {
      return bizHandleMapper.getNeedHandleWarnIndexTotal(handleDifferWarnIndexDTO);
   }
   /**
    * 查询(首页)差异预警处理
    *@author:liangyanjun
    *@time:2016年2月17日下午5:46:08
    *@param userId
    *@return
    */
   @Override
   public List<HandleDifferWarnDTO> findIndexHandleDifferWarn(int userId) throws TException {
      return bizHandleMapper.findIndexHandleDifferWarn(userId);
   }
    /**
     * 根据id删除差异预警处理
     *@author:liangyanjun
     *@time:2016年12月26日上午9:50:11
     *@param handleDifferWarnDTO
     *@return
     *@throws TException
     */
   @Override
   public boolean delHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      bizHandleMapper.delHandleDifferWarn(handleDifferWarnDTO);
      return true;
   }
    /**
     * 添加差异预警历史处理
     *@author:liangyanjun
     *@time:2016年12月26日上午9:50:07
     *@param handleDifferWarnDTO
     *@return
     *@throws TException
     */
   @Override
   public boolean addHisHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      bizHandleMapper.addHisHandleDifferWarn(handleDifferWarnDTO);
      return true;
   }
    /**
     * 查询差异预警历史处理
     *@author:liangyanjun
     *@time:2016年12月26日上午9:50:03
     *@param handleDifferWarnDTO
     *@return
     *@throws TException
     */
   @Override
   public List<HandleDifferWarnDTO> findAllHisHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      return bizHandleMapper.findAllHisHandleDifferWarn(handleDifferWarnDTO);
   }
    /**
     * 查询差异预警处理总数
     *@author:liangyanjun
     *@time:2016年12月26日上午9:49:55
     *@param handleDifferWarnDTO
     *@return
     *@throws TException
     */
   @Override
   public int getHisHandleDifferWarnTotal(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      return bizHandleMapper.getHisHandleDifferWarnTotal(handleDifferWarnDTO);
   }
    /**
     * 查询赎楼列表（分页）
     *@author:liangyanjun
     *@time:2016年12月26日上午9:49:15
     *@param foreclosureIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public List<ForeclosureIndexDTO> queryForeclosureIndex(ForeclosureIndexDTO foreclosureIndexDTO) throws TException {
      return bizHandleMapper.queryForeclosureIndex(foreclosureIndexDTO);
   }
    /**
     * 查询赎楼列表数据总数
     *@author:liangyanjun
     *@time:2016年12月26日上午9:49:22
     *@param foreclosureIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public int getForeclosureIndexTotal(ForeclosureIndexDTO foreclosureIndexDTO) throws TException {
      return bizHandleMapper.getForeclosureIndexTotal(foreclosureIndexDTO);
   }


   /**
    * 赎楼
    *@author:liangyanjun
    *@time:2016年3月11日下午6:59:30
    */
   @Override
   @Transactional
   public boolean foreclosure(HouseBalanceDTO houseBalanceDTO,HandleInfoDTO handleInfoDTO,HandleDynamicDTO handleDynamicDTO,String loginUserName) throws TException {
      logger.info("办理赎楼：入参:houseBalanceDTO："+ houseBalanceDTO.toString() +",handleInfoDTO:"+handleInfoDTO.toString()+",handleDynamicDTO:"+handleDynamicDTO.toString()+",loginUserName:"+loginUserName);
      bizHandleMapper.updateHouseBalance(houseBalanceDTO);
      //如果未赎楼，则修改为已赎楼状态
      if (handleInfoDTO.getForeclosureStatus()==Constants.NO_FORECLOSURE) {
         handleInfoDTO.setForeclosureStatus(Constants.IS_FORECLOSURE);
         bizHandleMapper.updateHandleInfo(handleInfoDTO);
      }
      //更新赎楼办理动态的数据
      updateHandleDynamic(handleDynamicDTO);
      logger.info("更新赎楼数据成功：入参:"+ handleInfoDTO.toString() +",handleInfoDTO:"+handleInfoDTO.toString()+",handleDynamicDTO:"+handleDynamicDTO.toString()+",loginUserName:"+loginUserName);
      //如果赎楼余额确认，则完成赎楼办理动态任务
      if (houseBalanceDTO.getBalanceConfirm()==Constants.IS_BALANCE_CONFIRM) {
         try {
            finishHandleDynamicTask(handleDynamicDTO, loginUserName);
            logger.info("完成赎楼任务成功：入参：handleDynamicDTO:"+ handleDynamicDTO +",loginUserName:"+loginUserName);
        } catch (Exception e) {
            logger.error("完成赎楼任务失败：入参：handleDynamicDTO:"+ handleDynamicDTO +",loginUserName:"+loginUserName+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
            throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
        }
      }
      return true;
   }

   /**
    * 查询(首页)差异预警处理总数
    *@author:liangyanjun
    *@time:2016年3月16日上午11:38:39
    *@param handleDifferWarnDTO
    *@return
    */
   @Override
   public int getIndexHandleDifferWarnTotal(HandleDifferWarnDTO handleDifferWarnDTO) throws TException {
      return bizHandleMapper.getIndexHandleDifferWarnTotal(handleDifferWarnDTO);
     
   }

   /**
    * 根据项目id获取赎楼金额
    *@author:liangyanjun
    *@time:2016年3月22日下午2:57:53
    *@param projectId
    *@return
    */
   @Override
   public double getForeclosureMoneyByProjectId(int projectId) throws TException {
      return bizHandleMapper.getForeclosureMoneyByProjectId(projectId);
   }
    /**
     * 查询办理动态办理条数
     *@author:liangyanjun
     *@time:2016年12月26日上午9:48:05
     *@param applyHandleIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public List<HandleDynamicMap> qeuryHandleDynamicCountMapList(ApplyHandleIndexDTO applyHandleIndexDTO) throws TException {
      return bizHandleMapper.qeuryHandleDynamicCountMapList(applyHandleIndexDTO);
   }
   /**
    * 根据项目id查询办理总历时天数
    *@author:liangyanjun
    *@time:2016年3月29日上午10:32:33
    *@param projectId
    *@return
    */
   @Override
   public int gethandleDaysByProjectId(int projectId) throws TException {
      return bizHandleMapper.gethandleDaysByProjectId(projectId);
   }

   /**
    * 赎楼驳回
    */
   @Override
   @Transactional
   public boolean foreclosureTurnDown(HandleInfoDTO handleInfoDTO) throws TException {
      //修改赎楼状态为“已驳回”
      bizHandleMapper.updateHandleInfo(handleInfoDTO);
      //修改查档审批状态为"重新查档",查档复核状态为"重新复核"
      CheckDocumentDTO checkDocumentQuery=new CheckDocumentDTO();
      checkDocumentQuery.setProjectId(handleInfoDTO.getProjectId());
      List<CheckDocumentDTO> list = integratedDeptMapper.queryCheckDocument(checkDocumentQuery);
      CheckDocumentDTO checkDocumentDTO = list.get(0);
      checkDocumentDTO.setApprovalStatus(Constants.CHECK_DOCUMENT_APPROVAL_STATUS_4);
      checkDocumentDTO.setReCheckStatus(Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_2);
      integratedDeptMapper.updateCheckDocument(checkDocumentDTO);
      
      //修改查诉讼审批状态为"重新查诉讼"
      CheckLitigationDTO checkLitigationQuery=new CheckLitigationDTO();
      checkLitigationQuery.setProjectId(handleInfoDTO.getProjectId());
      List<CheckLitigationDTO> litigationList = integratedDeptMapper.queryCheckLitigation(checkLitigationQuery);
      CheckLitigationDTO checkLitigationDTO = litigationList.get(0);
      checkLitigationDTO.setApprovalStatus(Constants.CHECK_LITIGATION_APPROVAL_STATUS_4);
      integratedDeptMapper.updateCheckLitigation(checkLitigationDTO);
      return true;
   }

   /**
    * 根据项目id或者业务处理id，判断该项目的赎楼余额是否已确认
    *@author:liangyanjun
    *@time:2016年5月5日上午10:14:14
    *@param projectId
    *@param handleId
    *@return
    *@throws TException
    */
   @Override
   public boolean isBalanceConfirm(int projectId, int handleId) throws TException {
      try {
         //
         if (handleId <= 0 && projectId > 0) {
            HandleInfoDTO handleInfoQuery = new HandleInfoDTO();
            handleInfoQuery.setProjectId(projectId);
            List<HandleInfoDTO> list = bizHandleMapper.findAllHandleInfoDTO(handleInfoQuery);
            handleId = list.get(0).getPid();
         }
         //
         HouseBalanceDTO houseBalanceQuery = new HouseBalanceDTO();
         houseBalanceQuery.setHandleId(handleId);
         List<HouseBalanceDTO> houseBalanceList = bizHandleMapper.findAllHouseBalance(houseBalanceQuery);
         if (houseBalanceList == null || houseBalanceList.isEmpty()) {
            return false;
         }
         for (HouseBalanceDTO houseBalanceDTO : houseBalanceList) {
            double principal = houseBalanceDTO.getPrincipal();//赎楼本金
            int balanceConfirm = houseBalanceDTO.getBalanceConfirm();//余额确认状态
            // 赎楼本金大于0，并且余额确认状态为“未确认”，则说明未确认
            // 赎楼本金小于等于0，则说明没有二次赎楼，所以不检查
            if (principal > 0 && balanceConfirm == Constants.NO_BALANCE_CONFIRM) {
               return false;
            }
         }
      } catch (Exception e) {
         logger.error("根据项目id或者业务处理id，判断该项目的赎楼余额是否已确认失败：入参：projectId:"+projectId +",handleId:"+handleId+ "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }

   /**
    * 赎楼余额确认
    *@author:liangyanjun
    *@time:2016年5月5日上午11:42:57
    */
   @Override
   @Transactional
   public boolean balanceConfirm(HouseBalanceDTO houseBalanceDTO) throws TException {
      try {
        bizHandleMapper.updateHouseBalance(houseBalanceDTO);
        int projectId = getHandleInfoById(houseBalanceDTO.getHandleId()).getProjectId();
        refundFeeServiceImpl.addRefundTail(projectId);
    } catch (Exception e) {
        logger.error("赎楼余额确认失败：入参：houseBalanceDTO:"+houseBalanceDTO + "。错误：" + ExceptionUtil.getExceptionMessage(e));
        e.printStackTrace();
        throw new RuntimeException(ExceptionUtil.getExceptionMessage(e));
    }
      return true;
   }
   /**
    * 获取办理动态办理人
    *@author:liangyanjun
    *@time:2016年5月31日上午9:44:36
    *@param handleId
    *@param handleFlowId
    *@param loginUserName
    *@return
    *@throws TException
    *@throws ThriftServiceException
    */
   @Override
   public Set<Map<String,String>> getHandleUser(int handleId,int handleFlowId,String loginUserName) throws TException, ThriftServiceException {
      Set<Map<String,String>> handleUserSet=null;
      try {
         String userName = loginUserName;
         
         int projectId = getProjectIdByHandleId(handleId);
         Project project = projectServiceImpl.getProjectInfoById(projectId);//项目信息
         int pmUserId = project.getPmUserId();
         SysUser pmUser = SysUserServiceImpl.getSysUserByPid(pmUserId);//客户经理
         
         //查询当前业务办理的任务节点
         BizHandleWorkflowDTO bizHandleWorkflowQuery = new BizHandleWorkflowDTO();
         bizHandleWorkflowQuery.setHandleId(handleId);
         bizHandleWorkflowQuery.setUserName(userName);
         bizHandleWorkflowQuery.setStatus(Constants.STATUS_ENABLED);
         List<BizHandleWorkflowDTO> handleWorkflowList =findAllBizHandleWorkflow(bizHandleWorkflowQuery);
         if (handleWorkflowList==null||handleWorkflowList.isEmpty()) {
            return new TreeSet<Map<String, String>>();
         }
         BizHandleWorkflowDTO bizHandleWorkflowDTO = handleWorkflowList.get(0);
         String executionId = bizHandleWorkflowDTO.getExecutionId();
         String taskId = workflowServiceImpl.getTaskIdByProcessInstanceId(executionId);
         //根据任务节点获取form的currentRole的值
         String currentRoles = workflowServiceImpl.getFormValueStr(taskId, "currentRole");
         //就得流程因为没有currentRole属性，此时从常量中获取currentRole
         if (StringUtil.isBlank(currentRoles)) {
            currentRoles = com.xlkfinance.bms.common.constant.Constants.HANDLE_DYNAMIC_ROLE_MAP.get(handleFlowId);
         }
         if (StringUtil.isBlank(currentRoles)) {
            return new TreeSet<Map<String, String>>();
         }
         handleUserSet = workflowServiceImpl.getUserMapByRoleCodes(currentRoles, pmUser);
         
      } catch (Exception e) {
         logger.error("获取办理动态办理人失败：入参："+ handleId + "," + handleFlowId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
      }
      return handleUserSet == null ? new TreeSet<Map<String, String>>() : handleUserSet;
   }
   /**
    * 根据项目id获取办理动态主表id
    *@author:liangyanjun
    *@time:2016年12月26日上午9:47:20
    *@param handleId
    *@return
    *@throws TException
    */
   @Override
   public int getProjectIdByHandleId(int handleId) throws TException {
      HandleInfoDTO handleInfoQuery=new HandleInfoDTO();
      handleInfoQuery.setPid(handleId);
      List<HandleInfoDTO> handleInfoDTO = findAllHandleInfoDTO(handleInfoQuery);
      int projectId = handleInfoDTO.get(0).getProjectId();
      return projectId;
   }

   /**
    * 机构的保后监管列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月31日上午11:23:21
    */
   @Override
   public List<OrgBizHandlePage> queryOrgBizHandlePage(OrgBizHandlePage query) throws TException {
      return bizHandleMapper.queryOrgBizHandlePage(query);
   }

   /**
    * 机构的保后监管列表总数
    *@author:liangyanjun
    *@time:2016年8月31日上午11:23:21
    */
   @Override
   public int getOrgBizHandlePageTotal(OrgBizHandlePage query) throws TException {
      return bizHandleMapper.getOrgBizHandlePageTotal(query);
   }

   
   
   /**
    * 查询预警列表
    * @param handleDifferWarnDTO
    * @return
    */
   @Override
   public List<HandleDifferWarnDTO> getHandleDifferWarnList(HandleDifferWarnDTO handleDifferWarnDTO)  throws TException {
	   return bizHandleMapper.getHandleDifferWarnList(handleDifferWarnDTO);
   }

	@Override
	public List<HandleDynamicFileDTO> findHandleDynamicFileByProjectId(int projectId)
			throws TException {
		  return bizHandleMapper.findHandleDynamicFileByProjectId(projectId);
	}

}
