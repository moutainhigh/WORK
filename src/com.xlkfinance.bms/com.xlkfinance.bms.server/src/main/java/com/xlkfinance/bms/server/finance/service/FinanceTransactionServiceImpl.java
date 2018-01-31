/**
 * @Title: FinanceTransactionServiceImpl.java
 * @Package com.xlkfinance.bms.server.finance.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: yql
 * @date: 2015年4月16日 下午4:33:35
 * @version V1.0
 */
package com.xlkfinance.bms.server.finance.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessCondition;
import com.xlkfinance.bms.rpc.finance.FinanceTDTO;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionCondition;
import com.xlkfinance.bms.rpc.finance.UserCommissionCondition;
import com.xlkfinance.bms.rpc.finance.UserCommissionView;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionService.Iface;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionView;
import com.xlkfinance.bms.rpc.finance.TransactionView;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.server.finance.mapper.FinanceBankMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceTransactionMapper;
import com.xlkfinance.bms.server.system.mapper.SysUserMapper;

/**
 *  资金头寸
  * @ClassName: FinanceTransactionServiceImpl
  * @Description: TODO
  * @author: yql
  * @date: 2015年4月16日 下午4:33:52
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Service("financeTransactionServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.finance.FinanceTransactionService")
public class FinanceTransactionServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(FinanceTransactionServiceImpl.class);

	@Resource(name = "financeTransactionMapper")
	private FinanceTransactionMapper financeTransactionMapper;
	
	 /**
	  * 根据登记类别查询资金列表
	  */
	@Override
	public List<FinanceTransactionView> getFinanceTransactionList(FinanceTransactionCondition ftcondition) throws TException {
		List<FinanceTransactionView> list=financeTransactionMapper.getFinanceTransactionList(ftcondition);
		return list!=null?list:new ArrayList<FinanceTransactionView>();
	}
	 //添加数据
	@Override
	public int insertFt(FinanceTDTO financeTDTO){
		financeTDTO.setCreDttm(DateUtil.format(new Date(),"yyyy-MM-dd hh:mm:ss"));
		int result=financeTransactionMapper.insertFt(financeTDTO);
		return result;
	}
	@Override
	public int countFinanceTransactionList(
			FinanceTransactionCondition ftcondition)
			throws ThriftServiceException, TException {
		int result=financeTransactionMapper.countFinanceTransactionList(ftcondition);
		return result;
	}
	@Override
	public FinanceTransactionView getFinanceTransactionById(int pid)
			throws ThriftServiceException, TException {
		FinanceTransactionView financeTransactionView=financeTransactionMapper.getFinanceTransactionById(pid);
		return financeTransactionView!=null?financeTransactionView:new FinanceTransactionView();
	}
	@Override
	public int updateFinanceTransaction(FinanceTDTO financeTDTO)
			throws ThriftServiceException, TException {
		int result=financeTransactionMapper.updateFinanceTransaction(financeTDTO);
		return result;
	}
	@Override
	public List<FinanceTransactionView> getFinanceFinancing(
			FinanceTransactionCondition ftcondition)
			throws ThriftServiceException, TException {
		List<FinanceTransactionView> list=financeTransactionMapper.getFinanceFinancing(ftcondition);
		List<FinanceTransactionView> result=new ArrayList<FinanceTransactionView>();
		if(list!=null){
			for(FinanceTransactionView view:list){
				view.setLoanBalance(view.getFtAmtLoan()-view.getFtAmtInput());
				result.add(view);
			}
		}
		return result!=null?result:new ArrayList<FinanceTransactionView>();
	}
	@Override
	public int countFinanceFinancing(FinanceTransactionCondition ftcondition)
			throws ThriftServiceException, TException {
		int result=0;
		result=financeTransactionMapper.countFinanceFinancing(ftcondition);
		return result;
	}
	@Override
	public int deleteFinanceFinancing(FinanceTDTO financeTDTO)
			throws ThriftServiceException, TException {
		int result=0;
		result=financeTransactionMapper.deleteFinanceFinancing(financeTDTO);
		return result;
	}
	@Override
	public List<TransactionView> getTransactionList(
			FinanceBank financeBank) throws ThriftServiceException, TException {
		List<TransactionView> resultList= new ArrayList<TransactionView>();
	
		List<TransactionView> list=financeTransactionMapper.getTransactionList(financeBank);
		if(list!=null){
			for(TransactionView transactionView : list){
				
				Map<String, Object> param = new HashMap<String, Object>();
		        param.put("results",OracleTypes.CURSOR);
		        param.put("bankId",transactionView.getPid());		
			        
		        financeTransactionMapper.getTransactionAmtList(param);
				List<TransactionView> paramList = (List<TransactionView>) param.get("results");
				TransactionView transactionViewTemp= paramList.get(0);
				if(transactionViewTemp!=null){
					transactionViewTemp.setPid(transactionView.getPid());
					transactionViewTemp.setChargeName(transactionView.getChargeName());
					transactionViewTemp.setBankCardType(transactionView.getBankCardType());
					transactionViewTemp.setBankCardTypeText(transactionView.getBankCardTypeText());
					transactionViewTemp.setDefaultAmt(transactionView.getDefaultAmt());
					transactionViewTemp.setBankNum(transactionView.getBankNum());	
//					transactionViewTemp.setWeekAmt(transactionView.getWeekAmt());//只有首页展现使用
//					transactionViewTemp.setAprilAmt(transactionView.getAprilAmt());//只有首页展现使用
//					transactionViewTemp.setInputUnrecAmt(transactionView.getInputUnrecAmt());
					resultList.add(transactionViewTemp);
				}else{
					resultList.add(transactionView);
				}
				
			}
			}
		return resultList!=null?resultList:new ArrayList<TransactionView>();
	}
	@Override
	public int countTransactionList(FinanceBank financeBank)
			throws ThriftServiceException, TException {
		int result=0;
		result=financeTransactionMapper.countTransactionList(financeBank);
		return result;
	}
	/**
	 * 
	  * @Description: TODO 统计欠款客户数
	  * @param financeBusinessCondition
	  * @return
	  * @throws ThriftServiceException
	  * @throws TException
	  * @author: yql
	  * @date: 2015年8月13日 下午12:35:55
	 */
	@Override
	public int countCustArrearsTotal()
			throws ThriftServiceException, TException {
		try {
			int result= financeTransactionMapper.countCustArrearsTotal();
			return result;
		} catch (Exception e) {
			logger.error("countCustArrearsView fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"首页数据显示-查询统计欠款客户数失败！");
			
		}
	}
	/**
	 * 提成列表
	 */
	@Override
	public List<UserCommissionView> getListUserCommission(UserCommissionCondition userCommissionCondition) throws ThriftServiceException,
			TException {
		List<UserCommissionView> baseList= null;
		List<UserCommissionView> result= new ArrayList<UserCommissionView>();
		try{
			//项目经理Id列表
			List<Integer> userIds= new ArrayList<Integer>();
			UserCommissionView userCommissionViewResult = null;
			baseList=financeTransactionMapper.getUserCommission(userCommissionCondition);
			 for(UserCommissionView userCommissionView:baseList){//循环计算应得的提成金额，
				 userCommissionViewResult= new UserCommissionView();
				 userCommissionViewResult=userCommissionView;				 
				 userCommissionViewResult.setCommissionAmt(userCommissionView.getProjectAmt()*0.08);
				 userCommissionViewResult.setRealCommissionAmt(userCommissionViewResult.getCommissionAmt()*0.75);
				 result.add(userCommissionViewResult);
				 userIds.add(userCommissionViewResult.getUserId());
			 }
			// 根据列表查询项目总监列表
			 
			 
		}catch(Exception e){
			logger.error("提成列表出错", e);
		}
		return result;
	}
	/**
	 * 提成列表总数
	 */
	@Override
	public int countListUserCommission(UserCommissionCondition userCommissionCondition) throws ThriftServiceException,
			TException {
		int result= 0;
		
		try{
			result=financeTransactionMapper.countUserCommission(userCommissionCondition);			
			
		}catch(Exception e){
			logger.error("提成列表总数出错", e);
		}
		return result;
	}
	@Override
	public List<UserCommissionView> getListUserCommissionDetail(
			UserCommissionCondition userCommissionCondition)
			throws ThriftServiceException, TException {
		List<UserCommissionView> result=null;
		
		try{
			result=financeTransactionMapper.getListUserCommissionDetail(userCommissionCondition);			
			
		}catch(Exception e){
			logger.error("提成列表总数出错", e);
		}
		return result!=null?result: new ArrayList<UserCommissionView>();
	}

}
