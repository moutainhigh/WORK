/**
 * @Title: MonthInterestCalPlanLine.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 等额本金
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
@Component("eqPrincipalCalPlanLine")
public class EqPrincipalCalPlanLine extends CalPlanLine {
	
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
		
		for(;num<=loan.getRepayCycle();num++) {
			
			if(nextMonthDt.getTime().getTime()>maxrepayDt.getTime().getTime()){
				nextMonthDt.setTime(maxrepayDt.getTime());
			}
			
			
			
			switch (loan.getRepayOption()) {
			case 1:
				principalBalance-=principal;//本金余额
				//是否最后一期
				if(num<loan.getRepayCycle()){
					//添加还款计划
					list.add(setRepayMent(loan,30,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}else{
					//计算天数
					int d=calcNum(loan,outDt,maxrepayDt);
					list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}
				break;
			case 2:
				int d=30;
				double princ2=principal;
				if(num==0){
					princ2=0;
				}
				if(num==loan.getRepayCycle()-1){
					d=calcNum(loan,outDt,maxrepayDt);
				}
				if(num==loan.getRepayCycle()){
					d=0;
				}
				list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,princ2,principalBalance,version,1));
				principalBalance-=principal;
				
				break;
				
			case 3:
				
				if(num==0){
					list.add(setBeforeInterest(loan,userId,nextMonthDt.getTime(),num,0,principalBalance,version,outDt,maxrepayDt));
				}else{
					list.add(setRepayMent(loan,0,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}
				
				principalBalance-=principal;
				break;
				
			case 4:
				
				principalBalance-=principal;
				if(num==1){
					list.add(setBeforeInterest(loan,userId,nextMonthDt.getTime(),num,principal,principalBalance,version,outDt,maxrepayDt));
				}else{
					list.add(setRepayMent(loan,0,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}
				
				break;

			default:
				break;
			}
			
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayD));
		
		}
		return list;
	}

}
