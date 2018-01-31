/**
 * @Title: FinanceConstant.java
 * @Package com.xlkfinance.bms.server.finance.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: qiancong.Xian
 * @date: 2015年3月15日 下午4:36:24
 * @version V1.0
 */
package com.xlkfinance.bms.server.finance.util;

public class FinanceConstant {

	/**
	 * 对账单类型：计划对账单
	 */
	public final static int ACCOUNT_STATEMENT_TYPE_PLAN=1;
	/**
	 * 对账单类型：即时发生
	 */
	public final static int ACCOUNT_STATEMENT_TYPE_INSTANT=2;
	
	/**
	 * 对账单类型：减免
	 */
	public final static int ACCOUNT_STATEMENT_TYPE_REDUCTION=3;
	/**
	 * 还款计划表中的完成状态：已对账
	 */
	public final static int  REPAYMENT_RECONCILIATION_COMPLETE = 1;
	/**
	 * 还款计划表中的完成状态：未对账
	 */
	public final static int  REPAYMENT_RECONCILIATION_NOT_YET = 2;
	/**
	 * 还款计划表中的完成状态：部分对账
	 */
	public final static int  REPAYMENT_RECONCILIATION_PART = 3;
	/**
	 * 财务收款：未用完
	 */
	public final static int FINANCE_RECEIVABLES_UNUSED  = 0;
	/**
	 * 财务收款：已经用完
	 */
	public final static int FINANCE_RECEIVABLES_FINISH = 1;
}
