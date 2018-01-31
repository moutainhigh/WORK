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
package com.xlkfinance.bms.server.repayment.calc;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;

public abstract class CalPlanLine {
	public abstract List<RepaymentPlanBaseDTO> execute(Loan loan, int userId);

	private int day;
	
	private int dayOfM=30; //一个月=30天
	
	private CalcOperDto operDto;
	
	
	public int getDayOfM() {
		return dayOfM;
	}

	public void setDayOfM(int dayOfM) {
		this.dayOfM = dayOfM;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}
	

	public CalcOperDto getOperDto() {
		return operDto;
	}

	public void setOperDto(CalcOperDto operDto) {
		this.operDto = operDto;
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
	
	
	public Calendar getCalcInterestDt(Calendar cal,int month){
		Calendar c=Calendar.getInstance();
		c.setTime(cal.getTime());
		c.add(Calendar.MONTH, month);
		if(c.get(Calendar.DAY_OF_MONTH)==cal.get(Calendar.DAY_OF_MONTH)){
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		return c;
	}
	
	/**
	 * 
	  * @Description: 组装还款计划信息添加到list
	  * @param list
	  * @param loan
	  * @param num
	  * @param userId
	  * @param nextMonthDt
	  * @param outDt
	  * @param maxrepayDt
	  * @param principal
	  * @param principalBalance
	  * @param version
	  * @author: zhanghg
	  * @date: 2015年3月7日 下午3:13:14
	 */
	public void calcRepay(List<RepaymentPlanBaseDTO> list,Loan loan,int num,int userId,Calendar nextMonthDt,Calendar outDt,Calendar maxrepayDt,double principal,double principalBalance,int version,int exTarget){
			int d=getDayOfM();
			//是否最后一期
			if(num==loan.getRepayCycle()){
				d=calcNum(loan,outDt,maxrepayDt);//计算天数 是不是整月
			}
			list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,principal,principalBalance,version,exTarget));
		
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
			double principalBalance, int version,int exTarget) {
		
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();

		// 还款计划表固定值
		setFixedRepayMent(rp, loan, userId, version, stageNum, nextMonthDt,exTarget);

		// 天数等于30，计算月利率
		double mgrRate = loan.getMonthLoanMgr()/getDayOfM() * num;
		double otherRate = loan.getMonthLoanOtherFee()/getDayOfM() * num;
		double interestRate = loan.getMonthLoanInterest()/getDayOfM() * num;
		if (num == getDayOfM()) {
			mgrRate = loan.getMonthLoanMgr();
			otherRate = loan.getMonthLoanOtherFee();
			interestRate = loan.getMonthLoanInterest();
		}
		if(num>getDayOfM()){
			mgrRate=(loan.getMonthLoanMgr()*(num/getDayOfM()))+(loan.getMonthLoanMgr()/getDayOfM() * (num%getDayOfM())); 
			otherRate=(loan.getMonthLoanOtherFee()*(num/getDayOfM()))+(loan.getMonthLoanOtherFee()/getDayOfM() * (num%getDayOfM())); 
			interestRate=(loan.getMonthLoanInterest()*(num/getDayOfM()))+(loan.getMonthLoanInterest()/getDayOfM() * (num%getDayOfM())); 
		}
		
		
		rp.setShouldPrincipal(contrDouble(principal));// 上一期本金余额
		rp.setShouldMangCost(contrDouble((principalBalance + principal) * mgrRate / 100));//管理费
		rp.setShouldOtherCost(contrDouble((principalBalance + principal) * otherRate / 100));//其他费用
		rp.setShouldInterest(contrDouble((principalBalance + principal) * interestRate / 100));//利息
		rp.setTotal(contrDouble(rp.getShouldMangCost() + rp.getShouldOtherCost()
				+ rp.getShouldInterest() + principal));//合计
		rp.setPrincipalBalance(contrDouble(principalBalance));//本金余额
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
			int userId, int version, int stageNum, Date nextMonthDt,int exTarget) {
		rp.setLoanInfoId(loan.getPid());
		rp.setGenUserId(userId);
		rp.setGenDttm(DateUtil.format(new Date()));
		rp.setPlanType(1);
		rp.setExTarget(0);
		rp.setExType(1);
		rp.setThisStatus(0);
		rp.setFreezeStatus(0);
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
			Calendar maxRepayDt,int exTarget) {
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
		// 还款计划表固定值
		setFixedRepayMent(rp, loan, userId, version, stageNum, nextMonthDt,exTarget);
		// 计算金额值

		int t = getDayOfM();// 天数
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
			double mgrRate = loan.getMonthLoanMgr()/getDayOfM() * t;
			double otherRate = loan.getMonthLoanOtherFee()/getDayOfM() * t;
			double interestRate = loan.getMonthLoanInterest()/getDayOfM() * t;
			if (t == getDayOfM()) {
				mgrRate = loan.getMonthLoanMgr();
				otherRate = loan.getMonthLoanOtherFee();
				interestRate = loan.getMonthLoanInterest();
			}

			mangCost += money * mgrRate / 100;
			otherCost += money * otherRate / 100;
			interest += money * interestRate / 100;
			money -= princ2;
		}
		rp.setShouldPrincipal(contrDouble(principal));

		rp.setShouldMangCost(contrDouble(mangCost));
		rp.setShouldOtherCost(contrDouble(otherCost));
		rp.setShouldInterest(contrDouble(interest));
		rp.setTotal(contrDouble(rp.getShouldMangCost() + rp.getShouldOtherCost()
				+ rp.getShouldInterest() + principal));

		rp.setPrincipalBalance(contrDouble(principalBalance));
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
			double otherCost, double interest,int exTarget) {
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
		
		// 还款计划表固定值
		setFixedRepayMent(rp, loan, userId, version, stageNum, nextMonthDt,exTarget);
		// 计算金额值

		rp.setShouldPrincipal(contrDouble(principal));

		rp.setShouldMangCost(contrDouble(mangCost));
		rp.setShouldOtherCost(contrDouble(otherCost));
		rp.setShouldInterest(contrDouble(interest));

		rp.setTotal(contrDouble(rp.getShouldMangCost() + rp.getShouldOtherCost()
				+ rp.getShouldInterest() + principal));

		rp.setPrincipalBalance(contrDouble(principalBalance));
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
		int d = getDayOfM();
		int m = loan.getRepayCycle() - 1;
		
		if(m !=0){
			currD.setTime(calcInterestDay(outDt,m).getTime());
		}
		
		// 日期相等 为30天 否则计算时间差的天数
		if (maxrepayDt.get(Calendar.DAY_OF_MONTH) != calcInterestDay(outDt,loan.getRepayCycle()).get(Calendar.DAY_OF_MONTH)) {
			if(m==0){
				d =calcNum(outDt,maxrepayDt);
			}else{
				d =calcNum(currD,maxrepayDt);
			}
		}
		if(d>=getDayOfM()){
			d=getDayOfM();
		}else{ 
			int currMd = maxrepayDt.getActualMaximum(Calendar.DATE);
			if(currD.get(Calendar.MONTH)!=maxrepayDt.get(Calendar.MONTH) && maxrepayDt.get(Calendar.DATE) ==currMd){
				d=getDayOfM();
			}
		}
		return d;
	}
	
	/**
	 * 
	  * @Description: 算息日期
	  * @param outDt
	  * @param num
	  * @return
	  * @author: zhanghg
	  * @date: 2015年4月18日 下午4:29:21
	 */
	public Calendar calcInterestDay(Calendar outDt,int num){
		Calendar currD = Calendar.getInstance();
		currD.setTime(outDt.getTime());
		currD.add(Calendar.MONTH, num);
		//判断放款日 与算息日是否相等
		if(currD.get(Calendar.DAY_OF_MONTH)==outDt.get(Calendar.DAY_OF_MONTH)){
			currD.add(Calendar.DAY_OF_MONTH, -1);
		}
		return currD;
	}
	
	/**
	 * 
	  * @Description: 计算时间差的天数
	  * @param minDt
	  * @param maxDt
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月9日 下午2:53:02
	 */
	public int calcNum(Calendar minDt, Calendar maxDt) {
		
		//计算时间差的天数
		long l = maxDt.getTimeInMillis() - minDt.getTimeInMillis();
		int d = new Long(l / (1000 * 60 * 60 * 24)).intValue();
		return d;
	}
	
	
	
	public double contrDouble(double m){
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return Double.parseDouble(df.format(m));
	}
	
	/**
	 * 
	  * @Description: 利率变更-交换新利率与原始利率
	  * @param loan
	  * @param cod
	  * @author: zhanghg
	  * @date: 2015年3月20日 上午10:30:28
	 */
	public void dxchangeRate(Loan loan,CalcOperDto cod){
		  double monthLoanMgr=loan.getMonthLoanMgr(); 
		  double monthLoanOtherFee=loan.getMonthLoanOtherFee(); 
		  double monthLoanInterest=loan.getMonthLoanInterest(); 
		  
		  loan.setMonthLoanMgr(cod.getMonthLoanMgr());
		  loan.setMonthLoanOtherFee(cod.getMonthLoanOtherFee());
		  loan.setMonthLoanInterest(cod.getMonthLoanInterest());
		  
		  cod.setMonthLoanMgr(monthLoanMgr-cod.getMonthLoanMgr());
		  cod.setMonthLoanOtherFee(monthLoanOtherFee-cod.getMonthLoanOtherFee());
		  cod.setMonthLoanInterest(monthLoanInterest-cod.getMonthLoanInterest());
	}
}	
