/**
 * @Title: FinanceBankMapper.java
 * @Package com.xlkfinance.bms.server.finance.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月26日 下午3:47:53
 * @version V1.0
 */
package com.xlkfinance.bms.server.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.beforeloan.CreditLimitRecord;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.finance.AcctProjectBalanceView;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesDTO;
import com.xlkfinance.bms.rpc.finance.LoanRealTimeDTO;
import com.xlkfinance.bms.rpc.finance.LoanRecIntoDTO;
import com.xlkfinance.bms.rpc.finance.UserCommissionView;
import com.xlkfinance.bms.rpc.finance.LoanRecIntoService.Iface;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationDTO;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationView;
import com.xlkfinance.bms.server.credits.mapper.CreditsDTOMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceBankMapper;
import com.xlkfinance.bms.server.finance.mapper.LoanRefundMapper;





@SuppressWarnings({"unchecked","rawtypes"})
@Service("LoanRecIntoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.finance.LoanRecIntoService")
public  class LoanRecIntoServiceImpl implements Iface{
	private Logger logger = LoggerFactory.getLogger(LoanRecIntoServiceImpl.class);
	@Resource(name = "loanRefundMapper")
	private LoanRefundMapper loanRefundMapper;
	
	@Resource(name = "financeBankMapper")
	private FinanceBankMapper financeBankMapper;
	@Resource(name = "creditsDTOMapper")
	private CreditsDTOMapper creditsDTOMapper;
	/**add by yql*/

	/**
	 * 
	  * @Description: TODO 添加财务对账余额转入收入
	  * @param loanRefundDTO
	  * @return
	  * @author: yql
	  * @date: 2015年3月31日 上午10:01:48
	 */
	@Override
	public int insertLoanRecInto(LoanRecIntoDTO loanRecInto)
			throws ThriftServiceException, TException {
		int result=0;
		try{
			int bankId=loanRefundMapper.getBankIdByLoanId(loanRecInto.getLoanId());
			loanRecInto.setBankId(bankId);
			result=loanRefundMapper.insertLoanRecInto(loanRecInto);
			//减去收款表的余额
			if(result>0){
				FinanceReceivablesDTO dto = new FinanceReceivablesDTO();
				dto.setAvailableBalance(loanRecInto.getActualIntoAmt());
				dto.setPid(loanRecInto.getReceivablesId());
				result=financeBankMapper.updateFinanceBalance(dto);
			}
		}catch(Exception e){
			logger.error("插入财务对账余额转入收入出错", e);
		}
		return result;
	}

	/**
	  * 
	   * @Description: TODO 根据贷款id查询有余额的收款信息
	   * @param loanId
	   * @return
	   * @author: yequnli
	   * @date: 2015年9月18日 下午1:56:40
	  */
	@Override
    public List<AcctProjectBalanceView> getBalanceByLoanId(int loanId){
		List<AcctProjectBalanceView> result=null;
		try{
			result=financeBankMapper.getBalanceByLoanId(loanId);
		}catch(Exception e){
			logger.error("插入财务对账余额转入收入出错", e);
		}
		return result!=null?result:new ArrayList<AcctProjectBalanceView>();
    }
	
	/**
	 * 
	  * @Description: TODO 反核销方法
	  * @param loanId
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: yequnli
	  * @date: 2015年10月20日 下午3:48:01
	 */
	@Override
	public int antiVerification(int loanId,int inputId,int userId)
			throws ThriftServiceException, TException {
		int result=0;
		try{

			
			//修改财务收款表状态为-2 表示为系统反核销
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("updateUser", userId);
			map.put("inputId", inputId);
			map.put("loanId", loanId);
			
			result=financeBankMapper.updateLoanInputStatus(map);
			Log.info("反核销修改收款表状态："+result+"参数："+map);
			// 修改财务交易记录表状态-2 表示为系统反核销		
			result=financeBankMapper.updateLoanFtStatus(map);
			Log.info("反核销修改财务交易记录表状态："+result+"参数："+map);
			// 修改对账明细表记录表状态-2 表示为系统反核销
			List<RepaymentReconciliationView> repaymentReconciliationDTOs=financeBankMapper.getReconciliationId(map);
						
			// 修改对账表记录表状态-2 表示为系统反核销
			result=financeBankMapper.updateReconciliationStatus(map);
			Log.info("反核销修改对账表表状态："+result+"参数："+map);
			
			for(RepaymentReconciliationView repaymentReconciliationDTO:repaymentReconciliationDTOs){

				map.put("recId", repaymentReconciliationDTO.getPid());
				
				// 查询明细那些事本金
				List<RepaymentReconciliationView>  dtls=financeBankMapper.getReconciliationDtl(map);
				if(dtls!=null && dtls.size()>0){
					for(RepaymentReconciliationView dtlViews :dtls){ //反对账本金
						// 如果是反掉本金，要把授信额度的金额填到授信金额表
						//额度使用记录表添加一条使用数据
						int creditId = creditsDTOMapper.getCreditId(loanId);
						CreditLimitRecord creditLimitRecord= new CreditLimitRecord();
						creditLimitRecord.setCreditId(creditId);
						creditLimitRecord.setReason("财务对账反核销");
						creditLimitRecord.setCreDttm(DateUtil.format(new Date(), "yyyy-MM-dd hh:mm:ss"));
						creditLimitRecord.setStatus(1);
						creditLimitRecord.setCreditUseType(2);//额度调整类型（1=授信、2=使用、3=冻结、4=解冻 、5=还款）
						creditLimitRecord.setAmt(dtlViews.getReconciliationAmt());
						creditLimitRecord.setRequestAmt(dtlViews.getReconciliationAmt());
						result=creditsDTOMapper.insert(creditLimitRecord);
						Log.info("额度使用记录表添加一条使用数据："+result+"参数："+map);
					}
				}
				result=financeBankMapper.updateReconciliationDtl(map);
				Log.info("反核销修改对账明细表表状态："+result+"参数："+map);
				if(repaymentReconciliationDTO.getRealtimeId()!=-1){//对的是及时表的数据修改及时表的对账状态
					LoanRealTimeDTO LoanRealTimeDTO=financeBankMapper.getLoanRealTime(repaymentReconciliationDTO.getRealtimeId());
					// 修改还款计划表记录表 对账状态 表示为系统反核销
					
					//拿对账明细表对的钱和还款计划表对的钱 做比较，明细表的钱减去还款计划表的钱=0 把对账状态变为未对账，小于0 部分对账
					if(repaymentReconciliationDTO.getReconciliationAmt()-LoanRealTimeDTO.getTotal()==0){
						map.put("isRec", 2);
					}else if(repaymentReconciliationDTO.getReconciliationAmt()-LoanRealTimeDTO.getTotal()<0){
						map.put("isRec", 3);
					}else{
						map.put("isRec", 3);
					}
					map.put("rtPid", LoanRealTimeDTO.getPid());
					result=financeBankMapper.updateLoanRealTimeStatus(map);
					Log.info("反核销修改及时发生表状态："+result+"参数："+map);
				}else{// 还款计划表
					// 修改及时发生表记录表对账状态表示为系统反核销
					map.put("cycleNum", repaymentReconciliationDTO.getCycleNum());
					LoanRealTimeDTO repayDto=financeBankMapper.getRepaymentPlan(map);
					if(repaymentReconciliationDTO.getReconciliationAmt()-repayDto.getTotal()==0){
						map.put("isRec", 2);
					}else if(repaymentReconciliationDTO.getReconciliationAmt()-repayDto.getTotal()<0){
						map.put("isRec", 3);
					}else{
						map.put("isRec", 3);
					}
					map.put("rpPid", repayDto.getPid());
					result=financeBankMapper.updateRepaymentPlanStatus(map);
					Log.info("反核销修改还款计划表状态："+result+"参数："+map);
				}	
			}
			
			return result;
			
		}catch(Exception e){
			logger.error("反核销方法出错", e);
		}
		return result;
	}

	
	
	
	/**add by yql*/
}
