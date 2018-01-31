/**
 * @Title: CalPlanLine.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 贷款利息计算抽象类
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月4日 下午2:39:47
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.calc;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;

public abstract class CalPlanLine {
	public abstract List<RepaymentPlanBaseDTO> execute(Loan loan, int userId);

	private int day;

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * 
	 * @Description: 获取日期
	 * @param c
	 * @param repayD
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月3日 下午3:45:06
	 */
	public int getDay(Calendar c, int repayD) {
		int d = c.getActualMaximum(Calendar.DATE);
		if (repayD > d) {
			return d;
		} else {
			return repayD;
		}

	}

	/**
	 * 
	 * @Description: 计算利息
	 * @param principal
	 * @param mangCost
	 * @param otherCost
	 * @param interest
	 * @param total
	 * @param principalBalance
	 * @param loan
	 * @param num
	 * @author: zhanghg
	 * @date: 2015年2月3日 下午3:44:54
	 */
	public RepaymentPlanBaseDTO setRepayMent(Loan loan, int num, int userId,
			Date nextMonthDt, int stageNum, double principal,
			double principalBalance, int version) {
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);

		// 还款计划表固定值
		setFixedRepayMent(rp, loan, userId, version, stageNum, nextMonthDt);

		// 设置计算利息值
		rp.setShouldPrincipal(Double.parseDouble(df.format(principal)));

		// 天数等于30，计算月利率
		double mgrRate = loan.getDayLoanMgr() * num;
		double otherRate = loan.getDayLoanOtherFee() * num;
		double interestRate = loan.getDayLoanInterest() * num;
		if (num == 30) {
			mgrRate = loan.getMonthLoanMgr();
			otherRate = loan.getMonthLoanOtherFee();
			interestRate = loan.getMonthLoanInterest();
		}
		if(num>30){
			mgrRate=(loan.getMonthLoanMgr()*(num/30))+(loan.getDayLoanMgr() * (num%30)); 
			otherRate=(loan.getMonthLoanOtherFee()*(num/30))+(loan.getDayLoanOtherFee() * (num%30)); 
			interestRate=(loan.getMonthLoanInterest()*(num/30))+(loan.getDayLoanInterest() * (num%30)); 
		}
		rp.setShouldMangCost(Double.parseDouble(df
				.format((principalBalance + principal) * mgrRate / 100)));
		rp.setShouldOtherCost(Double.parseDouble(df
				.format((principalBalance + principal) * otherRate / 100)));
		rp.setShouldInterest(Double.parseDouble(df
				.format((principalBalance + principal) * interestRate / 100)));
		rp.setTotal(Double.parseDouble(df.format(rp.getShouldMangCost() + rp.getShouldOtherCost()
				+ rp.getShouldInterest() + principal)));
		rp.setPrincipalBalance(Double.parseDouble(df.format(principalBalance)));
		return rp;
	}

	/**
	 * 
	 * @Description: 计算利息
	 * @param principal
	 * @param mangCost
	 * @param otherCost
	 * @param interest
	 * @param total
	 * @param principalBalance
	 * @param loan
	 * @param type
	 *            类型-是否前置
	 * @param num
	 * @author: zhanghg
	 * @date: 2015年2月3日 下午3:44:54
	 */
	public RepaymentPlanBaseDTO setRepayMent(Loan loan, int num, int userId,
			Date nextMonthDt, int stageNum, double principal,
			double principalBalance, int version, int type) {
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);

		// 还款计划表固定值
		setFixedRepayMent(rp, loan, userId, version, stageNum, nextMonthDt);
		// 计算金额值
		rp.setShouldPrincipal(Double.parseDouble(df
				.format(principal)));

		// 天数等于30，计算月利率
		double mgrRate = loan.getDayLoanMgr() * num;
		double otherRate = loan.getDayLoanOtherFee() * num;
		double interestRate = loan.getDayLoanInterest() * num;
		if (num == 30) {
			mgrRate = loan.getMonthLoanMgr();
			otherRate = loan.getMonthLoanOtherFee();
			interestRate = loan.getMonthLoanInterest();
		}
		if(num>30){
			mgrRate=(loan.getMonthLoanMgr()*(num/30))+(loan.getDayLoanMgr() * (num%30)); 
			otherRate=(loan.getMonthLoanOtherFee()*(num/30))+(loan.getDayLoanOtherFee() * (num%30)); 
			interestRate=(loan.getMonthLoanInterest()*(num/30))+(loan.getDayLoanInterest() * (num%30)); 
		}
		rp.setShouldMangCost(Double.parseDouble(df.format(principalBalance * mgrRate / 100)));
		rp.setShouldOtherCost(Double.parseDouble(df.format(principalBalance * otherRate / 100)));
		rp.setShouldInterest(Double.parseDouble(df.format(principalBalance * interestRate / 100)));
		rp.setTotal(Double.parseDouble(df.format(rp.getShouldMangCost() + rp.getShouldOtherCost()+ rp.getShouldInterest() + principal)));

		rp.setPrincipalBalance(Double.parseDouble(df.format(principalBalance)));
		return rp;
	}

	/**
	 * 
	 * @Description: 设置还款计划表固定值
	 * @param rp
	 * @param loan
	 * @param userId
	 * @param version
	 * @param stageNum
	 * @param nextMonthDt
	 * @author: zhanghg
	 * @date: 2015年2月4日 上午11:19:12
	 */
	public void setFixedRepayMent(RepaymentPlanBaseDTO rp, Loan loan,
			int userId, int version, int stageNum, Date nextMonthDt) {
		rp.setLoanInfoId(loan.getPid());
		rp.setGenUserId(userId);
		rp.setGenDttm(DateUtil.format(new Date()));
		rp.setPlanType(1);
		rp.setExTarget(0);
		rp.setExType(0);
		rp.setThisStatus(0);
		rp.setPlanVersion(version);
		rp.setStatus(1);
		rp.setIsReconciliation(2);// 未对账
		rp.setShouldOtherCostName("其他费用");
		rp.setPlanCycleNum(stageNum);
		rp.setPlanRepayDt(DateUtil.format(nextMonthDt, "yyyy-MM-dd"));

	}

	/**
	 * 
	 * @Description: 等额本金，等额本息一次性付清 利息
	 * @param loan
	 * @author: zhanghg
	 * @date: 2015年2月4日 上午11:25:21
	 */

	public RepaymentPlanBaseDTO setBeforeInterest(Loan loan, int userId,
			Date nextMonthDt, int stageNum, double principal,
			double principalBalance, int version, Calendar outDt,
			Calendar maxRepayDt) {
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		// 还款计划表固定值
		setFixedRepayMent(rp, loan, userId, version, stageNum, nextMonthDt);
		// 计算金额值

		int t = 30;// 天数
		double money = loan.getCreditAmt();// 本金余额
		double princ2 = money / loan.getRepayCycle();// 每期还的本金
		double mangCost = 0;
		double otherCost = 0;
		double interest = 0;
		for (int i = 1; i <= loan.getRepayCycle(); i++) {
			if (i == loan.getRepayCycle()) {
				t = calcNum(loan, outDt, maxRepayDt);
			}

			// 天数等于30，计算月利率
			double mgrRate = loan.getDayLoanMgr() * t;
			double otherRate = loan.getDayLoanOtherFee() * t;
			double interestRate = loan.getDayLoanInterest() * t;
			if (t == 30) {
				mgrRate = loan.getMonthLoanMgr();
				otherRate = loan.getMonthLoanOtherFee();
				interestRate = loan.getMonthLoanInterest();
			}

			mangCost += money * mgrRate / 100;
			otherCost += money * otherRate / 100;
			interest += money * interestRate / 100;
			money -= princ2;
		}
		rp.setShouldPrincipal(Double.parseDouble(df.format(principal)));

		rp.setShouldMangCost(Double.parseDouble(df.format(mangCost)));
		rp.setShouldOtherCost(Double.parseDouble(df.format(otherCost)));
		rp.setShouldInterest(Double.parseDouble(df.format(interest)));
		rp.setTotal(Double.parseDouble(df.format(rp.getShouldMangCost() + rp.getShouldOtherCost()
				+ rp.getShouldInterest() + principal)));

		rp.setPrincipalBalance(Double.parseDouble(df.format(principalBalance)));
		return rp;
	}

	/**
	 * 
	 * @Description:等本等息
	 * @param loan
	 * @author: zhanghg
	 * @date: 2015年2月4日 上午11:25:21
	 */

	public RepaymentPlanBaseDTO setEqPrincipalInterest(Loan loan, int userId,
			Date nextMonthDt, int stageNum, double principal,
			double principalBalance, int version, double mangCost,
			double otherCost, double interest) {
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		// 还款计划表固定值
		setFixedRepayMent(rp, loan, userId, version, stageNum, nextMonthDt);
		// 计算金额值

		rp.setShouldPrincipal(Double.parseDouble(df.format(principal)));

		rp.setShouldMangCost(Double.parseDouble(df.format(mangCost)));
		rp.setShouldOtherCost(Double.parseDouble(df.format(otherCost)));
		rp.setShouldInterest(Double.parseDouble(df.format(interest)));

		rp.setTotal(Double.parseDouble(df.format(rp.getShouldMangCost() + rp.getShouldOtherCost()
				+ rp.getShouldInterest() + principal)));

		rp.setPrincipalBalance(Double.parseDouble(df.format(principalBalance)));
		return rp;
	}

	/**
	 * 
	 * @Description: 计算天数
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月3日 下午4:43:34
	 */
	public int calcNum(Loan loan, Calendar outDt, Calendar maxrepayDt) {
		Calendar currD = Calendar.getInstance();
		currD.setTime(outDt.getTime());
		int m = loan.getRepayCycle() - 1;
		currD.add(Calendar.MONTH, m);
		currD.add(Calendar.DAY_OF_MONTH, -1);
		int d = 30;
		// 日期相等 为30天 否则计算时间差的天数
		if (currD.get(Calendar.DAY_OF_MONTH) != maxrepayDt
				.get(Calendar.DAY_OF_MONTH)) {
			long l = maxrepayDt.getTimeInMillis() - currD.getTimeInMillis();
			d = new Long(l / (1000 * 60 * 60 * 24)).intValue();
		}
		return d;
	}
}
