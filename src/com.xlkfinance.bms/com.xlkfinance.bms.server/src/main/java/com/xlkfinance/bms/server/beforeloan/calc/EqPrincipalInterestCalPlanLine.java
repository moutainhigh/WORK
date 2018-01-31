/**
 * @Title: MonthInterestCalPlanLine.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 等本等息
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月4日 下午2:42:58
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.calc;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
@Component("eqPrincipalInterestCalPlanLine")
public class EqPrincipalInterestCalPlanLine extends CalPlanLine {
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@Override
	public List<RepaymentPlanBaseDTO> execute(Loan loan,int userId) {
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		int version=loanRepaymentPlanMapper.getMaxVersion(loan.getPid())+1;
		
		int repayD=loan.getRepayDate();
		double principal=loan.getCreditAmt()/loan.getRepayCycle();//应还本金
		
		double principalBalance=loan.getCreditAmt();//本金余额
		int num=1;//期数
		
		Calendar outDt=Calendar.getInstance();//放款日期
		outDt.setTime(DateUtil.format(loan.getPlanOutLoanDt(),"yyyy-MM-dd"));
		
		Calendar maxrepayDt=Calendar.getInstance();//计划还款日期
		maxrepayDt.setTime(DateUtil.format(loan.getPlanRepayLoanDt(),"yyyy-MM-dd"));
		
		Calendar nextMonthDt=Calendar.getInstance();//还款日期
		nextMonthDt.setTime(outDt.getTime());
		
		if(loan.getRepayOption()==4 ||loan.getRepayOption()==1){
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayD));
		}else{
			num=0;
		}
		
		int t=30;//天数
		double money=loan.getCreditAmt();//本金余额
		
		//计算所有利息
		double totalmangCost=0;
		double totalotherCost=0;
		double totalinterest=0;
		for(int i=1;i<=loan.getRepayCycle();i++){
			if(i==loan.getRepayCycle()){
				t=calcNum(loan,outDt,maxrepayDt);
			}
			//天数等于30，计算月利率
			double mgrRate=loan.getDayLoanMgr()*t;
			double otherRate=loan.getDayLoanOtherFee()*t;
			double interestRate=loan.getDayLoanInterest()*t;
			if(t==30){
				mgrRate=loan.getMonthLoanMgr();
				otherRate=loan.getMonthLoanOtherFee();
				interestRate=loan.getMonthLoanInterest();
			}
			
			totalmangCost+=money*mgrRate/100;
			totalotherCost+=money*otherRate/100;
			totalinterest+=money*interestRate/100;
			money-=principal;
		}
		double mangCost=totalmangCost/loan.getRepayCycle();
		double otherCost=totalotherCost/loan.getRepayCycle();
		double interest=totalinterest/loan.getRepayCycle();
		
		for(;num<=loan.getRepayCycle();num++) {
			
			if(nextMonthDt.getTime().getTime()>maxrepayDt.getTime().getTime()){
				nextMonthDt.setTime(maxrepayDt.getTime());
			}
			
			
			principalBalance-=principal;//本金余额
			
			list.add(setEqPrincipalInterest(loan,userId,nextMonthDt.getTime(),num,principal,principalBalance,version,mangCost,otherCost,interest));
			
			
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayD));
		
		}
		return list;
	}

}
