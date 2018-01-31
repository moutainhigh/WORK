package com.xlkfinance.bms.server.inloan.service;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfo;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.OrgSysLogTypeConstant;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.OverdueFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentRecordDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentService.Iface;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.server.aom.system.mapper.OrgSysLogInfoMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.ForeclosureRepaymentMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月26日下午3:24:35 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：回款管理:该service对回款，回款记录，逾期费等做操作 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("repaymentServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.RepaymentService")
public class RepaymentServiceImpl implements Iface {
   
   private Logger logger = LoggerFactory.getLogger(RepaymentServiceImpl.class);
   @Resource(name = "foreclosureRepaymentMapper")
   private ForeclosureRepaymentMapper repaymentMapper;
   
   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;
   
   @Resource(name = "BizHandleServiceImpl")
   private BizHandleService.Iface bizHandleServiceImpl;
   
   @Resource(name = "sysUserMapper")
   private SysUserMapper sysUserMapper;
   
   @Resource(name = "projectMapper")
   private ProjectMapper projectMapper;
   
   @Resource(name = "orgSysLogInfoMapper")
   private OrgSysLogInfoMapper orgSysLogInfoMapper;
   
   @Resource(name = "refundFeeServiceImpl")
   private RefundFeeService.Iface refundFeeServiceImpl;
   
   @Resource(name = "orgCooperatCompanyApplyServiceImpl")
   private OrgCooperatCompanyApplyService.Iface orgCooperatCompanyApplyServiceImpl;
   /**
    * 根据条件查询回款(分页查询)
    *@author:liangyanjun
    *@time:2016年12月23日下午5:52:17
    *@param repaymentDTO
    *@return
    *@throws TException
    */
   @Override
   public List<RepaymentDTO> queryRepayment(RepaymentDTO repaymentDTO) throws TException {
      return repaymentMapper.queryRepayment(repaymentDTO);
   }
    /**
     * 根据条件查询回款总数
     *@author:liangyanjun
     *@time:2016年12月23日下午5:52:29
     *@param repaymentDTO
     *@return
     *@throws TException
     */
   @Override
   public int getRepaymentTotal(RepaymentDTO repaymentDTO) throws TException {
      return repaymentMapper.getRepaymentTotal(repaymentDTO);
   }
    /**
     * 添加回款
     *@author:liangyanjun
     *@time:2016年12月23日下午5:52:35
     *@param repaymentDTO
     *@return
     *@throws TException
     */
   @Override
   public boolean addRepayment(RepaymentDTO repaymentDTO) throws TException {
      repaymentMapper.addRepayment(repaymentDTO);
      return true;
   }
    /**
     * 根据ID获取回款
     *@author:liangyanjun
     *@time:2016年12月23日下午5:52:40
     *@param pid
     *@return
     *@throws TException
     */
   @Override
   public RepaymentDTO getRepaymentById(int pid) throws TException {
      return repaymentMapper.getRepaymentById(pid);
   }
    /**
     * 根据ID更新回款
     *@author:liangyanjun
     *@time:2016年12月23日下午5:52:45
     *@param repaymentDTO
     *@return
     *@throws TException
     */
   @Override
   public boolean updateRepayment(RepaymentDTO repaymentDTO) throws TException {
      repaymentMapper.updateRepayment(repaymentDTO);
      return true;
   }
    /**
     * 根据条件查询回款记录(分页查询)
     *@author:liangyanjun
     *@time:2016年12月23日下午5:52:50
     *@param repaymentRecordDTO
     *@return
     *@throws TException
     */
   @Override
   public List<RepaymentRecordDTO> queryRepaymentRecord(RepaymentRecordDTO repaymentRecordDTO) throws TException {
      return repaymentMapper.queryRepaymentRecord(repaymentRecordDTO);
   }
    /**
     * 根据条件查询回款记录总数
     *@author:liangyanjun
     *@time:2016年12月23日下午5:52:54
     *@param repaymentRecordDTO
     *@return
     *@throws TException
     */
   @Override
   public int getRepaymentRecordTotal(RepaymentRecordDTO repaymentRecordDTO) throws TException {
      return repaymentMapper.getRepaymentRecordTotal(repaymentRecordDTO);
   }

   /**
    * 增加回款记录
    *@author:liangyanjun
    *@time:2016年3月13日下午10:17:30
    *@param repaymentRecordDTO
    */
   @Override
   @Transactional
   public boolean addRepaymentRecord(RepaymentRecordDTO repaymentRecordDTO) throws TException {
      try {
         double repaymentMoney = repaymentRecordDTO.getRepaymentMoney();// 回款金额
         String repaymentDate = repaymentRecordDTO.getRepaymentDate();// 回款日期
         int createrId = repaymentRecordDTO.getCreaterId();// 创建人
         int projectId = repaymentRecordDTO.getProjectId();// 项目id
         boolean isRepaymen=false;//是否回已款
         Integer repaymentId = null;
         double repaymentMoneyTotal=0;//总回款金额
         // 查询放款金额
         List<Integer> recPros = Arrays.asList(Constants.REC_PRO_3, Constants.REC_PRO_5);
         double realLoan = financeHandleMapper.getRecMoney(projectId, recPros);// 放款金额
    
         RepaymentDTO repaymentQuery = new RepaymentDTO();
         repaymentQuery.setProjectId(projectId);
         List<RepaymentDTO> repaymentList = repaymentMapper.queryRepayment(repaymentQuery);
         // 回款表无数据，则说明第一次回款，需要增加一条回款数据
         if (repaymentList == null || repaymentList.isEmpty()) {
            RepaymentDTO newRepaymentDTO = new RepaymentDTO();
            newRepaymentDTO.setProjectId(projectId);
            // 回款金额大于等于放款金额，状态就是已回款
            if (repaymentMoney >= realLoan) {
               newRepaymentDTO.setStatus(Constants.REPAYMENT_STATUS_2);
               isRepaymen=true;
            } else {
               newRepaymentDTO.setStatus(Constants.REPAYMENT_STATUS_1);
            }
            newRepaymentDTO.setRepaymentMoney(repaymentMoney);
            newRepaymentDTO.setNewRepaymentDate(repaymentDate);
            newRepaymentDTO.setCreaterId(createrId);
            newRepaymentDTO.setUpdateId(createrId);
            // 执行新增回款信息
            repaymentMapper.addRepayment(newRepaymentDTO);
            repaymentId = newRepaymentDTO.getPid();
            repaymentMoneyTotal = repaymentMoney;
         } else {
            // 有回款数据，则进行修改，回款金额=新的回款金额+旧的回款金额
            RepaymentDTO oldRepaymentDTO = repaymentList.get(0);
            double oldRepaymentMoney = oldRepaymentDTO.getRepaymentMoney();// 已经回款的金额
            repaymentMoneyTotal = oldRepaymentMoney + repaymentMoney;
            // 回款金额大于等于放款金额，状态就是已回款
            if (repaymentMoneyTotal >= realLoan) {
               oldRepaymentDTO.setStatus(Constants.REPAYMENT_STATUS_2);
               isRepaymen=true;
            } else {
               oldRepaymentDTO.setStatus(Constants.REPAYMENT_STATUS_1);
            }
            oldRepaymentDTO.setRepaymentMoney(repaymentMoneyTotal);
            oldRepaymentDTO.setNewRepaymentDate(repaymentDate);
            oldRepaymentDTO.setUpdateId(createrId);
            // 执行更新回款信息
            repaymentMapper.updateRepayment(oldRepaymentDTO);
            repaymentId = oldRepaymentDTO.getPid();
         }

         // 增加回款记录
         repaymentRecordDTO.setRepaymentId(repaymentId);
         repaymentMapper.addRepaymentRecord(repaymentRecordDTO);
         
         //回款之后，把回款完成时间保存到办理动态中
         if (isRepaymen) {
            //更新赎楼办理动态的数据
            HandleInfoDTO handleInfo = bizHandleServiceImpl.getHandleInfoByProjectId(projectId);
            int handleId = handleInfo.getPid();
            HandleDynamicDTO handleDynamicQuery = new HandleDynamicDTO();
            handleDynamicQuery.setHandleId(handleId);
            handleDynamicQuery.setHandleFlowId(Constants.HANDLE_FLOW_ID_10);
            List<HandleDynamicDTO> handleDynamicList = bizHandleServiceImpl.findAllHandleDynamic(handleDynamicQuery);
            if (handleDynamicList == null || handleDynamicList.isEmpty()) {
               throw new RuntimeException("赎楼失败,办理动态数据不存在,参数:" + handleInfo);
            }
            HandleDynamicDTO updateHandleDynamicDTO = handleDynamicList.get(0);
            updateHandleDynamicDTO.setFinishDate(repaymentDate);
            SysUser sysUser = sysUserMapper.getUserDetailsByPid(createrId);
            String realName = sysUser.getRealName();
            updateHandleDynamicDTO.setOperator(realName);
            bizHandleServiceImpl.updateHandleDynamic(updateHandleDynamicDTO);
         }
         Project project = projectMapper.getProjectInfoById(projectId);
         //机构的单，回款的时候恢复可用额度
         if (project.getProjectType()==AomConstant.PROJECT_TYPE_6) {
             orgCooperatCompanyApplyServiceImpl.updateOrgCreditLimit(projectId, repaymentMoney);
             //增加日志
             OrgSysLogInfo logInfo=new OrgSysLogInfo();
             logInfo.setContent("恢复可用额度：入参：" + projectId + ",repaymentMoneyTotal：" + repaymentMoney);
             logInfo.setOperator(createrId);
             logInfo.setType(OrgSysLogTypeConstant.LOG_TYPE_UPDATE);
             logInfo.setOrderId(projectId);
             orgSysLogInfoMapper.insert(logInfo);
             logger.info("恢复可用额度：入参：" + projectId + ",repaymentMoneyTotal：" + repaymentMoney);
         }
         saveOverdueFee(repaymentMoney, projectId,isRepaymen);
      } catch (Exception e) {
         logger.error("增加回款失败：入参：" + repaymentRecordDTO.toString() + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }
    /**
     * 保存逾期费数据
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:03
     *@param repaymentMoney
     *@param projectId
     *@throws TException
     */
   private void saveOverdueFee(double repaymentMoney, int projectId,boolean isRepaymen) throws TException {
       //根据项目id和回款金额调用计算逾期费函数
       Map params = new HashMap();
       params.put("projectId", projectId);
       params.put("newRepaymentMoney", repaymentMoney);
       repaymentMapper.getOverdueFeeByParams(params);
       double calculateOverdueFee = (double) params.get("overdueFee");
       
      //逾期费自动确认：如果状态为已回款并且逾期费小于等于0，则系统自动确认逾期费
      int confirmOverdueFeeStatus=Constants.NO_CONFIRM_OVERDUE_FEE;// 逾期费确认：未确认=1，已确认=2
      if (isRepaymen&& calculateOverdueFee<=0){
    	  confirmOverdueFeeStatus=Constants.IS_CONFIRM_OVERDUE_FEE;
    	  //添加退尾款数据
          refundFeeServiceImpl.addRefundTail(projectId);
      }
      
      OverdueFeeDTO overdueFeeQuery = new OverdueFeeDTO();
      overdueFeeQuery.setProjectId(projectId);
      List<OverdueFeeDTO> overdueFeeDTOs = repaymentMapper.queryOverdueFee(overdueFeeQuery);
      if (overdueFeeDTOs != null && !overdueFeeDTOs.isEmpty()) {
         OverdueFeeDTO updateOverdueFee = overdueFeeDTOs.get(0);
         double overdueFee = updateOverdueFee.getOverdueFee();
         if (overdueFee>0){
       	  confirmOverdueFeeStatus=Constants.NO_CONFIRM_OVERDUE_FEE;
         }
         //未确认之前才可以修改逾期费确认状态
         if (updateOverdueFee.getIsConfirm()==Constants.NO_CONFIRM_OVERDUE_FEE) {
            updateOverdueFee.setIsConfirm(confirmOverdueFeeStatus);
         }
		 updateOverdueFee.setOverdueFee(calculateOverdueFee + overdueFee);
         repaymentMapper.updateOverdueFee(updateOverdueFee);
      } else {
         OverdueFeeDTO overdueFeeDTO = new OverdueFeeDTO();
         overdueFeeDTO.setProjectId(projectId);
         overdueFeeDTO.setOverdueFee(calculateOverdueFee);
         overdueFeeDTO.setStatus(Constants.STATUS_ENABLED);
         overdueFeeDTO.setIsConfirm(confirmOverdueFeeStatus);
         repaymentMapper.addOverdueFee(overdueFeeDTO);
      }
   }
    /**
     * 根据ID获取回款记录
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:11
     *@param pid
     *@return
     *@throws TException
     */
   @Override
   public RepaymentRecordDTO getRepaymentRecordById(int pid) throws TException {
      return repaymentMapper.getRepaymentRecordById(pid);
   }
    /**
     * 根据条件查询逾期费(分页查询)
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:16
     *@param overdueFeeDTO
     *@return
     *@throws TException
     */
   @Override
   public List<OverdueFeeDTO> queryOverdueFee(OverdueFeeDTO overdueFeeDTO) throws TException {
      return repaymentMapper.queryOverdueFee(overdueFeeDTO);
   }
    /**
     * 根据条件查询逾期费总数
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:22
     *@param overdueFeeDTO
     *@return
     *@throws TException
     */
   @Override
   public int getOverdueFeeTotal(OverdueFeeDTO overdueFeeDTO) throws TException {
      return repaymentMapper.getOverdueFeeTotal(overdueFeeDTO);
   }
    /**
     * 添加逾期费
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:26
     *@param overdueFeeDTO
     *@return
     *@throws TException
     */
   @Override
   public boolean addOverdueFee(OverdueFeeDTO overdueFeeDTO) throws TException {
      repaymentMapper.addOverdueFee(overdueFeeDTO);
      return true;
   }
    /**
     * 根据ID获取逾期费
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:33
     *@param pid
     *@return
     *@throws TException
     */
   @Override
   public OverdueFeeDTO getOverdueFeeById(int pid) throws TException {
      return repaymentMapper.getOverdueFeeById(pid);
   }
    /**
     * 根据项目ID获取逾期费
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:39
     *@param overdueFeeDTO
     *@return
     *@throws TException
     */
   @Override
   public boolean updateOverdueFee(OverdueFeeDTO overdueFeeDTO) throws TException {
      repaymentMapper.updateOverdueFee(overdueFeeDTO);
      return true;
   }
    /**
     * 根据条件查询回款列表(分页查询)
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:45
     *@param repaymentIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public List<RepaymentIndexDTO> queryRepaymentIndex(RepaymentIndexDTO repaymentIndexDTO) throws TException {
      return repaymentMapper.queryRepaymentIndex(repaymentIndexDTO);
   }
    /**
     * 根据条件查询回款列表总数
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:50
     *@param repaymentIndexDTO
     *@return
     *@throws TException
     */
   @Override
   public int getRepaymentIndexTotal(RepaymentIndexDTO repaymentIndexDTO) throws TException {
      return repaymentMapper.getRepaymentIndexTotal(repaymentIndexDTO);
   }
    /**
     * 根据项目ID获取回款
     *@author:liangyanjun
     *@time:2016年12月23日下午5:53:55
     *@param projectId
     *@return
     *@throws TException
     */
   @Override
   public RepaymentDTO getRepaymentByProjectId(int projectId) throws TException {
      RepaymentDTO repaymentDTO = repaymentMapper.getRepaymentByProjectId(projectId);
      if (repaymentDTO==null) {
         return new RepaymentDTO();
      }
      return repaymentDTO;
   }
    /**
     * 根据项目ID获取逾期费
     *@author:liangyanjun
     *@time:2016年12月23日下午5:54:00
     *@param projectId
     *@return
     *@throws TException
     */
   @Override
   public OverdueFeeDTO getOverdueFeeByProjectId(int projectId) throws TException {
      OverdueFeeDTO overdueFeeDTO = repaymentMapper.getOverdueFeeByProjectId(projectId);
      if (overdueFeeDTO==null) {
         return new OverdueFeeDTO();
      }
      return overdueFeeDTO;
   }

   /**
    * 逾期费确认
    *@author:liangyanjun
    *@time:2016年4月27日上午9:55:57
    */
   @Override
   @Transactional
   public boolean confirmOverdueFee(OverdueFeeDTO overdueFeeDTO) throws TException {
      double overdueFee = overdueFeeDTO.getOverdueFee();// 逾期费
      int overdueDay = overdueFeeDTO.getOverdueDay();
      String accountNo = overdueFeeDTO.getAccountNo();// 账号
      int paymentWay = overdueFeeDTO.getPaymentWay();// 付款方式
      int projectId = overdueFeeDTO.getProjectId();// 项目id
      int updateId = overdueFeeDTO.getUpdateId();
      try {
         // 查询逾期费数据
         OverdueFeeDTO overdueFeeQuery = new OverdueFeeDTO();
         overdueFeeQuery.setProjectId(projectId);
         List<OverdueFeeDTO> queryOverdueFee = repaymentMapper.queryOverdueFee(overdueFeeQuery);

         // 逾期数据不存在，则新增一条数据
         if (queryOverdueFee == null || queryOverdueFee.isEmpty()) {
            overdueFeeDTO.setStatus(Constants.STATUS_ENABLED);
            overdueFeeDTO.setIsConfirm(Constants.IS_CONFIRM_OVERDUE_FEE);
            repaymentMapper.addOverdueFee(overdueFeeDTO);
            //添加退尾款数据
            refundFeeServiceImpl.addRefundTail(projectId);
            return true;
         }

         // 获取已存在的逾期数据
         OverdueFeeDTO updateOverdueFee = queryOverdueFee.get(0);
         // 逾期数据已经存在，则进行修改
         updateOverdueFee.setIsConfirm(Constants.IS_CONFIRM_OVERDUE_FEE);
         updateOverdueFee.setOverdueFee(overdueFee);
         updateOverdueFee.setAccountNo(accountNo);
         updateOverdueFee.setPaymentWay(paymentWay);
         updateOverdueFee.setOverdueDay(overdueDay);
         updateOverdueFee.setUpdateId(updateId);
         repaymentMapper.updateOverdueFee(updateOverdueFee);
         //添加退尾款数据
         refundFeeServiceImpl.addRefundTail(projectId);
      } catch (Exception e) {
         logger.error("逾期费确认失败：入参：" + overdueFeeDTO.toString() + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }
   /**
    * 
   	* TODO 根据条件分页查询还款信息列表，用于抵押贷还款列表展示
   	* @see com.xlkfinance.bms.rpc.inloan.RepaymentService.Iface#queryRepaymentInfo(com.xlkfinance.bms.rpc.inloan.RepaymentIndexDTO)
    */
	@Override
	public List<RepaymentIndexDTO> queryRepaymentInfo (RepaymentIndexDTO query) throws TException
	{
		// 修改放款开始时间格式
		if (null != query.getReceDateStart() && !"".equals(query.getReceDateStart())) {
			String beginString = query.getReceDateStart();
			Date d = DateUtil.format(beginString, "yyyy-MM-dd");
			beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:00").toString();
			query.setReceDateStart(beginString);
		}
		// 修改放款结束时间格式
		if (null != query.getReceDateEnd() && !"".equals(query.getReceDateEnd())) {
			String endString = query.getReceDateEnd();
			Date d = DateUtil.format(endString, "yyyy-MM-dd");
			endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
			query.setReceDateEnd(endString);
		}
		return repaymentMapper.queryRepaymentInfo(query);
	}
	/**
	 * 
	 * TODO 根据条件查询还款列表总数，用于抵押贷还款列表展示
	 * @see com.xlkfinance.bms.rpc.inloan.RepaymentService.Iface#getRepaymentInfoTotal(com.xlkfinance.bms.rpc.inloan.RepaymentIndexDTO)
	 */
	@Override
	public int getRepaymentInfoTotal (RepaymentIndexDTO query) throws TException
	{
		// 修改放款开始时间格式
		if (null != query.getReceDateStart() && !"".equals(query.getReceDateStart())) {
			String beginString = query.getReceDateStart();
			Date d = DateUtil.format(beginString, "yyyy-MM-dd");
			beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:00").toString();
			query.setReceDateStart(beginString);
		}
		// 修改放款结束时间格式
		if (null != query.getReceDateEnd() && !"".equals(query.getReceDateEnd())) {
			String endString = query.getReceDateEnd();
			Date d = DateUtil.format(endString, "yyyy-MM-dd");
			endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
			query.setReceDateEnd(endString);
		}
		return repaymentMapper.getRepaymentInfoTotal(query);
	}

}
