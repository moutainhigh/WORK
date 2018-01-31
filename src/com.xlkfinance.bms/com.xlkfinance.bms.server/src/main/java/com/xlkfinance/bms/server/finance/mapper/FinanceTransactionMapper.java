/**
 * @Title: FinanceTransactionMapper.java
 * @Package com.xlkfinance.bms.server.finance.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: yql
 * @date: 2015年4月16日 下午3:25:04
 * @version V1.0
 */
package com.xlkfinance.bms.server.finance.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceTDTO;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionCondition;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionView;
import com.xlkfinance.bms.rpc.finance.TransactionView;
import com.xlkfinance.bms.rpc.finance.UserCommissionCondition;
import com.xlkfinance.bms.rpc.finance.UserCommissionView;

/**
 * 财务资金头寸
  * @ClassName: FinanceTransactionMapper
  * @Description: TODO
  * @author: yql
  * @date: 2015年4月16日 下午3:25:10
 */
@MapperScan("financeTransactionMapper")
public interface FinanceTransactionMapper<T, PK> extends BaseMapper<T, PK> {
	//根据登记类别查询资金列表
	public List<FinanceTransactionView> getFinanceTransactionList(FinanceTransactionCondition ftcondition) ;
	//统计根据登记类别查询资金列表的数据
	public int countFinanceTransactionList(FinanceTransactionCondition ftcondition);
	 //添加数据
	public int insertFt (FinanceTDTO financeTDTO) ;
	//根据id查询一条数据的详细信息
	public FinanceTransactionView getFinanceTransactionById(int pid) ;
	//修改资金头寸信息
	public int updateFinanceTransaction (FinanceTDTO financeTDTO) ;
	//融资列表查询
	public List<FinanceTransactionView> getFinanceFinancing(FinanceTransactionCondition ftcondition);
	 //融资列表数据统计
	int countFinanceFinancing(FinanceTransactionCondition ftcondition);
	//删除融资借款信息
	int deleteFinanceFinancing(FinanceTDTO financeTDTO);
	//资金头寸查询页面数据
	public List <TransactionView> getTransactionList(FinanceBank financeBank) ;
	 //资金头寸查询页面数据统计
	int countTransactionList(FinanceBank financeBank);
	//获取资金头寸的金额信息
	public List <TransactionView> getTransactionAmtList(Map<String, Object> param );
	
	// 项目否决,删除对应的财务放款记录
	public void deleteFinanceRecords(int projectId);
	// 统计欠款客户数
	public int countCustArrearsTotal();

	//查询员工本月项目的收回费率总和
	public List<UserCommissionView> getUserCommission(UserCommissionCondition userCommissionCondition);
	//查询员工本月项目的收回费率总和
    public int countUserCommission(UserCommissionCondition userCommissionCondition);
   
    //查询员工本月提成详情
 	public List<UserCommissionView> getListUserCommissionDetail(UserCommissionCondition userCommissionCondition);
	//查询员工提成
//    i32 getListUserCommission(1:i32 month)  throws (1: Common.ThriftServiceException e);
}
