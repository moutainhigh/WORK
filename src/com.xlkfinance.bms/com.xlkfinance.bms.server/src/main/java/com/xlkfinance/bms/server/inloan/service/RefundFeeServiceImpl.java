package com.xlkfinance.bms.server.inloan.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.inloan.BizBatchRefundFeeRelation;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.BizLoanBatchRefundFeeMain;
import com.xlkfinance.bms.rpc.inloan.OverdueFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeMap;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService.Iface;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.server.inloan.mapper.BizBatchRefundFeeRelationMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizLoanBatchRefundFeeMainMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.ForeclosureRepaymentMapper;
import com.xlkfinance.bms.server.inloan.mapper.RefundFeeMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月26日下午3:24:35 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：退手续费（退手续费，退咨询费，退尾款，退佣金）  Service <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("refundFeeServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.RefundFeeService")
public class RefundFeeServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(RefundFeeServiceImpl.class);
   
   @Resource(name = "refundFeeMapper")
   private RefundFeeMapper refundFeeMapper;

   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;
   
   @Resource(name = "workflowServiceImpl")
   private WorkflowService.Iface workflowServiceImpl;
   
   @Resource(name = "foreclosureRepaymentMapper")
   private ForeclosureRepaymentMapper repaymentMapper;
   
   @Resource(name = "BizHandleServiceImpl")
   private BizHandleService.Iface bizHandleServiceImpl;
   
   @Resource(name = "bizBatchRefundFeeRelationMapper")
   private BizBatchRefundFeeRelationMapper batchRefundFeeRelationMapper;
   
   @Resource(name = "bizLoanBatchRefundFeeMainMapper")
   private BizLoanBatchRefundFeeMainMapper batchRefundFeeMainMapper;
   
   /**
    * 根据条件查询退手续费信息（分页查询）
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:18
    *@param refundFeeDTO
    *@return
    */
   @Override
   public List<RefundFeeDTO> findAllRefundFee(RefundFeeDTO refundFeeDTO) throws TException {
      List<RefundFeeDTO> list = refundFeeMapper.findAllRefundFee(refundFeeDTO);
      if (list==null||list.isEmpty()) {
         return list;
      }
//      for (RefundFeeDTO dto : list) {
//         //根据项目id获取最新的回款信息，如果有第二次回款则返回第二次，没有则返回第一次回款信息
//         ApplyFinanceHandleDTO newRecApplyFinance = financeHandleMapper.getNewRecApplyFinance(dto.getProjectId());
//         String recDate = newRecApplyFinance.getRecDate();
//         dto.setPayDate(recDate);
//      }
      return list;
   }

   /**
    * 根据条件查询退手续费信息总数
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:22
    *@param refundFeeDTO
    *@return
    */
   @Override
   public int getRefundFeeTotal(RefundFeeDTO refundFeeDTO) throws TException {
      return refundFeeMapper.getRefundFeeTotal(refundFeeDTO);
   }

   /**
    * 添加退手续费信息
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:25
    *@param refundFeeDTO
    *@return
    */
   @Override
   public boolean addRefundFee(RefundFeeDTO refundFeeDTO) throws TException {
      refundFeeMapper.addRefundFee(refundFeeDTO);
      return true;
   }

   /**
    * 根据id更新退手续费信息
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:28
    *@param refundFeeDTO
    *@return
    */
   @Override
   public boolean updateRefundFee(RefundFeeDTO refundFeeDTO) throws TException {
      refundFeeMapper.updateRefundFee(refundFeeDTO);
      return true;
   }

   /**
    * 根据条件查询退款列表（分页查询）
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:31
    *@param refundFeeIndexDTO
    *@return
    */
   @Override
   public List<RefundFeeIndexDTO> findAllRefundFeeIndex(RefundFeeIndexDTO refundFeeIndexDTO) throws TException {
		// 修改开始时间格式
		if (null != refundFeeIndexDTO.getStartDate() && !"".equals(refundFeeIndexDTO.getStartDate())) {
			String startString = refundFeeIndexDTO.getStartDate();
			Date d = DateUtil.format(startString, "yyyy-MM-dd");
			startString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:01").toString();
			refundFeeIndexDTO.setStartDate(startString);
		}
		// 修改结束时间格式
		if (null != refundFeeIndexDTO.getEndDate()&& !"".equals(refundFeeIndexDTO.getEndDate())) {
			String endString =refundFeeIndexDTO.getEndDate();
			Date d = DateUtil.format(endString, "yyyy-MM-dd");
			endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
			refundFeeIndexDTO.setEndDate(endString);
		}
      return refundFeeMapper.findAllRefundFeeIndex(refundFeeIndexDTO);
   }

   /**
    * 根据条件查询退款列表总数
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:34
    *@param refundFeeIndexDTO
    *@return
    */
   @Override
   public int getRefundFeeIndexTotal(RefundFeeIndexDTO refundFeeIndexDTO) throws TException {
      return refundFeeMapper.getRefundFeeIndexTotal(refundFeeIndexDTO);
   }

   
   /**
    * 添加退尾款
    *@author:liangyanjun
    *@time:2016年5月5日上午10:35:53
    *@param projectId
    *@throws TException
    */
   @Override
   @Transactional
   public boolean addRefundTail(int projectId) throws TException {
      try {
         // 逾期费
    	  double overdueFee = 0;
         OverdueFeeDTO overdueFeeDTO = repaymentMapper.getOverdueFeeByProjectId(projectId);
         //如果逾期费的付款方式为从尾款中扣，则与尾款一起计算
         if (overdueFeeDTO != null&&overdueFeeDTO.getPaymentWay() == Constants.OVERDUE_FEE_PAYMENT_WAY_1) {
        	overdueFee = overdueFeeDTO.getOverdueFee();
         }
         boolean balanceConfirm = bizHandleServiceImpl.isBalanceConfirm(projectId, -1);
         if (!balanceConfirm) {
            return false;
         }
         //回款金额
         RepaymentDTO repaymentDTO = repaymentMapper.getRepaymentByProjectId(projectId);
         double repaymentMoney = 0;
         if (repaymentDTO!=null) {
        	 repaymentMoney=repaymentDTO.getRepaymentMoney();
		 }
         //赎楼金额
         double foreclosureMoney = bizHandleServiceImpl.getForeclosureMoneyByProjectId(projectId);
         //退尾款=回款金额-赎楼金额-从尾款中扣的逾期费 
         double tailMoney = repaymentMoney - foreclosureMoney - overdueFee;
         RefundFeeDTO dto = new RefundFeeDTO();
         dto.setProjectId(projectId);
         dto.setType(Constants.REFUND_FEE_TYPE_3);
         List<RefundFeeDTO> list = findAllRefundFee(dto);
         // 不存在进行添加
         if ((list == null || list.isEmpty()) && tailMoney >= 0) {
            dto.setApplyStatus(Constants.APPLY_REFUND_FEE_STATUS_1);
            dto.setReturnFee(tailMoney);
            refundFeeMapper.addRefundFee(dto);
         // 已存在进行更新金额
         } else if (tailMoney >= 0) {
            RefundFeeDTO updateRefundFeeDTO = list.get(0);
            if (updateRefundFeeDTO.getApplyStatus()!=Constants.APPLY_REFUND_FEE_STATUS_1) {
               logger.error("系统更新退尾款失败，原因：尾款已申请不能再修改,入参：projectId:"+ projectId);
               return false;
            }
            updateRefundFeeDTO.setReturnFee(tailMoney);
            refundFeeMapper.updateRefundFee(updateRefundFeeDTO);
         }
         if (tailMoney <= 0) {
            logger.info("添加退尾款数据失败，尾款小于0：projectId:"+projectId+",尾款"+tailMoney+",回款金额"+repaymentMoney+",赎楼金额"+foreclosureMoney+",从尾款中扣的逾期费"+overdueFee);
         }else{
            logger.info("添加退尾款数据成功：projectId:"+projectId+",尾款"+tailMoney+",回款金额"+repaymentMoney+",赎楼金额"+foreclosureMoney+",从尾款中扣的逾期费"+overdueFee);
         }
      } catch (Exception e) {
         logger.error("添加退尾款失败：入参：projectId:"+ projectId + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }


   /*
    * 根据ID获得退费列表
    * @see com.xlkfinance.bms.rpc.inloan.RefundFeeService.Iface#queryRefundFeeByIds(java.lang.String)
    */
	@Override
	public List<RefundFeeIndexDTO> queryRefundFeeByIds(String ids) throws TException {
		String[] arr = ids.split(",");
	    List list = Lists.newArrayList();
	    for (int i = 0; i < arr.length; i++) {
	       list.add(arr[i]);
	    }
		return refundFeeMapper.queryRefundFeeByIds(list);
	}

	/**
	 * 批量保存退费信息
	 *@author:liangyanjun
	 *@time:2017年12月29日上午11:26:55
	 */
   @Override
   @Transactional
   public int saveBatchRefundFee(RefundFeeDTO refundFeeDTO) throws TException {
      String recAccount = refundFeeDTO.getRecAccount();//
      String bankName = refundFeeDTO.getBankName();//
      String recAccountName = refundFeeDTO.getRecAccountName();//
      String batchName = refundFeeDTO.getBatchName();
      int updateId = refundFeeDTO.getUpdateId();//
      int batchRefundFeeMainId = refundFeeDTO.getBatchRefundFeeMainId();
      List<RefundFeeMap> batchRefundFeeMapList = refundFeeDTO.getBatchRefundFeeMapList();//
      boolean isFirst=false;//是否第一次保存
      //批量处理没有数据则添加一条
      if (batchRefundFeeMainId<=0) {
         BizLoanBatchRefundFeeMain newBatchRefundFeeMain = new BizLoanBatchRefundFeeMain();
         newBatchRefundFeeMain.setBatchName(batchName);
         newBatchRefundFeeMain.setApplyStatus(Constants.APPLY_STATUS_2);
         newBatchRefundFeeMain.setCreaterId(updateId);
         newBatchRefundFeeMain.setUpdateId(updateId);
         batchRefundFeeMainMapper.insert(newBatchRefundFeeMain);
         batchRefundFeeMainId=newBatchRefundFeeMain.getPid();
         isFirst=true;
      }
      
      for (RefundFeeMap refundFeeMap : batchRefundFeeMapList) {
         int pid = refundFeeMap.getPid();
         int projectId = refundFeeMap.getProjectId();
         double money = refundFeeMap.getMoney();
         
         //更新退费数据
         RefundFeeDTO updateRefundFee = refundFeeMapper.getRefundFeeById(pid);
         updateRefundFee.setReturnFee(money);
         updateRefundFee.setRecAccount(recAccount);
         updateRefundFee.setRecAccountName(recAccountName);
         updateRefundFee.setBankName(bankName);
         if (updateRefundFee.getApplyStatus()==Constants.APPLY_STATUS_1) {
            updateRefundFee.setApplyStatus(Constants.APPLY_STATUS_2);
         }
         updateRefundFee.setUpdateId(updateId);
         refundFeeMapper.updateRefundFee(updateRefundFee);
         if (isFirst) {//第一次保存才关联
            //批量退费关联
            BizBatchRefundFeeRelation batchRefundFeeRelation = new BizBatchRefundFeeRelation();
            batchRefundFeeRelation.setBatchRefundFeeMainId(batchRefundFeeMainId);
            batchRefundFeeRelation.setProjectId(projectId);
            batchRefundFeeRelation.setRefundFeeId(pid);
            batchRefundFeeRelationMapper.insert(batchRefundFeeRelation);
         }
      }
      return batchRefundFeeMainId;
   }

   /**
    * 根据项目id获取批量退费主表数据
    *@author:liangyanjun
    *@time:2017年12月29日下午3:07:50
    */
   @Override
   public BizLoanBatchRefundFeeMain getBatchRefundFeeMainById(int pid) throws TException {
      BizLoanBatchRefundFeeMain loanBatchRefundFeeMain = batchRefundFeeMainMapper.getById(pid);
      if (loanBatchRefundFeeMain==null) {
         loanBatchRefundFeeMain=new BizLoanBatchRefundFeeMain();
      }
      return loanBatchRefundFeeMain;
   }

   /**
    * 根据条件查询批量退费关联数据
    *@author:liangyanjun
    *@time:2017年12月29日下午3:19:23
    */
   @Override
   public List<BizBatchRefundFeeRelation> getAllBatchRefundFeeRelation(BizBatchRefundFeeRelation query) throws TException {
      return batchRefundFeeRelationMapper.getAll(query);
   }

   /**
    * 生成一个批量名称
    *@author:liangyanjun
    *@time:2017年12月29日下午3:53:57
    */
   @Override
   public String generatedBatchName(){
      int newId=batchRefundFeeMainMapper.getNextId();
      String batchName = "PLTF"+DateUtil.format(new Date(), "yyyyMMdd")+newId;
      return batchName;
   }
}
