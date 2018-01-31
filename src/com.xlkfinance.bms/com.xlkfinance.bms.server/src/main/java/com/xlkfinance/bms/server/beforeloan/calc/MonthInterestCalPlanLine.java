/**
 * @Title: MonthInterestCalPlanLine.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 按月收息 -先息后本
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
@Component("monthInterestCalPlanLine")
public class MonthInterestCalPlanLine extends CalPlanLine {
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@Override
	public List<RepaymentPlanBaseDTO> execute(Loan loan,int userId) {
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		
		int version=loanRepaymentPlanMapper.getMaxVersion(loan.getPid())+1;
		
		int repayD=loan.getRepayDate();
		double principal=0;//应还本金
		
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
				//是否最后一期
				if(num<loan.getRepayCycle()){
					//添加还款计划
					list.add(setRepayMent(loan,30,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}else{
					//计算天数
					int d=calcNum(loan,outDt,maxrepayDt);
					list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,principalBalance,principal,version));
				}
				break;
			case 2:
				
				if(num==loan.getRepayCycle()){//本金
					//添加还款计划
					list.add(setRepayMent(loan,0,userId,nextMonthDt.getTime(),num,principalBalance,principal,version));
				}else if(num==loan.getRepayCycle()-1){//最后一期利息
					//计算天数
					int d=calcNum(loan,outDt,maxrepayDt);
					list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}else{
					list.add(setRepayMent(loan,30,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}
				break;
				
			case 3:
				if(num==loan.getRepayCycle()){//本金
					//添加还款计划
					list.add(setRepayMent(loan,0,userId,nextMonthDt.getTime(),num,principalBalance,principal,version));
				}else if(num==0){//第一期  前置付息
					//最后一期利息 天数
					int d=calcNum(loan,outDt,maxrepayDt);
					d+=(loan.getRepayCycle()-1)*30; 
					list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}else{
					list.add(setRepayMent(loan,0,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
				}
				break;
				
			case 4:
				
				if(loan.getRepayCycle()>1){
					if(num==loan.getRepayCycle()){//本金
						//添加还款计划
						list.add(setRepayMent(loan,0,userId,nextMonthDt.getTime(),num,principalBalance,principal,version));
					}else if(num==1){//第一期  前置付息
						//最后一期利息 天数
						int d=calcNum(loan,outDt,maxrepayDt);
						d+=(loan.getRepayCycle()-1)*30; 
						list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
					}else{
						list.add(setRepayMent(loan,0,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
					}
				}else{
					int d=calcNum(loan,outDt,maxrepayDt);
					list.add(setRepayMent(loan,d,userId,nextMonthDt.getTime(),num,principalBalance,principal,version));
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
