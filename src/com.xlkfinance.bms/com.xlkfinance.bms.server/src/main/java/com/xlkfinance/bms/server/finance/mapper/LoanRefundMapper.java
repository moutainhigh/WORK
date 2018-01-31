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
package com.xlkfinance.bms.server.finance.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.finance.LoanRecIntoDTO;
import com.xlkfinance.bms.rpc.finance.LoanRefundDTO;
import com.xlkfinance.bms.rpc.finance.LoanRefundView;

@MapperScan("loanRefundMapper")
public interface LoanRefundMapper<T, PK> extends BaseMapper<T, PK>{

	/**add by yql*/

	/**
	 * 
	  * @Description: TODO 添加余额退还处理方法
	  * @param loanRefundDTO
	  * @return
	  * @author: yql
	  * @date: 2015年3月31日 上午10:01:48
	 */
	public int insert(LoanRefundDTO loanRefundDTO);
	/**
	 * 
	  * @Description: TODO查询某个项目的退还金额的记录
	  * @param projectId
	  * @return
	  * @author: yql
	  * @date: 2015年3月31日 上午10:01:58
	 */
	public  List<LoanRefundView> getLoanRefundList(int loanId );
	/**
	 * 
	  * @Description: TODO 财务对账余额转入收入
	  * @param loanRecInto
	  * @return
	  * @author: yequnli
	  * @date: 2015年9月18日 上午11:05:47
	 */
	public int insertLoanRecInto(LoanRecIntoDTO loanRecInto) ;
	
	/**
	 * 
	  * @Description: TODO 根据贷款id查询银行账号Id
	  * @param loanId
	  * @author: yequnli
	  * @date: 2015年9月18日 下午4:36:58
	 */
	public int getBankIdByLoanId(int loanId);
	/**add by yql*/
}
