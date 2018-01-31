/**
 * @Title: LoanRepaymentPlanMidMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * @Description: 还款计划 表-中间表
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月2日 上午11:24:27
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
@MapperScan("loanRepaymentPlanMidMapper")
public interface LoanRepaymentPlanMidMapper<T, PK> extends BaseMapper<T, PK> {
	
	
	
	/**
	 * 
	  * @Description: 保存还款计划表
	  * @param list
	  * @return
	  * @author: zhanghg
	  * @date: 2015年2月5日 上午11:17:42
	 */
	public int insertRepayments(List<RepaymentPlanBaseDTO> list);
	
	
	
	/**
	 * 
	  * @Description: 根据期数和贷款ID查询还款信息
	  * @param repayment
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月7日 下午3:01:01
	 */
	public RepaymentPlanBaseDTO selectRepaymentByCycleNum(RepaymentPlanBaseDTO repayment);
	
	/**
	 * 
	  * @Description: 查询最大版本 冻结状态为1的还款计划表
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月13日 上午10:15:15
	 */
	public List<RepaymentPlanBaseDTO> selectNoFreezeRepaymentByLoanId(Integer loanId);
	
	/**
	 * 
	  * @Description: 获取中间表最大版本号
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月28日 下午2:42:17
	 */
	public int getMaxVersionMid(int loanId);

}
