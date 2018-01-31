/**
 * @Title: MonthInterestCalPlanLine.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 等额本息
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月4日 下午2:42:58
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.calc;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
@Component("eqInterestCalPlanLine")
public class EqInterestCalPlanLine extends CalPlanLine {
	
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	
	@Override
	public List<RepaymentPlanBaseDTO> execute(Loan loan,int userId) {
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		int version=loanRepaymentPlanMapper.getMaxVersion(loan.getPid())+1;
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		int repayD=loan.getRepayDate();
		
		double principalBalance=loan.getCreditAmt();//本金余额
		int num=1;//期数
		
		Calendar outDt=Calendar.getInstance();//放款日期
		outDt.setTime(DateUtil.format(loan.getPlanOutLoanDt(),"yyyy-MM-dd"));
		
		Calendar maxrepayDt=Calendar.getInstance();//计划还款日期
		maxrepayDt.setTime(DateUtil.format(loan.getPlanRepayLoanDt(),"yyyy-MM-dd"));
		
		Calendar nextMonthDt=Calendar.getInstance();//还款日期
		nextMonthDt.setTime(outDt.getTime());
		
		double monthRate=(loan.getMonthLoanInterest()+loan.getMonthLoanMgr()+loan.getMonthLoanOtherFee())/100;	
		
		
		double total =loan.getCreditAmt()*monthRate*Math.pow((1+monthRate),loan.getRepayCycle())/(Math.pow((1+monthRate),loan.getRepayCycle())-1);
		for(;num<=loan.getRepayCycle();num++) {
			
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayD));
			
			if(nextMonthDt.getTime().getTime()>maxrepayDt.getTime().getTime()){
				nextMonthDt.setTime(maxrepayDt.getTime());
			}
			RepaymentPlanBaseDTO rp=new RepaymentPlanBaseDTO();
			
			//还款计划表固定值
			setFixedRepayMent(rp,loan,userId,version,num,nextMonthDt.getTime());
			
			//计算利息（贷款利息，管理费利息，其他利息）
			double mangCost=principalBalance*loan.getMonthLoanMgr()/100;
			double otherCost=principalBalance*loan.getMonthLoanOtherFee()/100;
			double interest=principalBalance*loan.getMonthLoanInterest()/100;
			
			//应还本金
			double principal=total-mangCost-otherCost-interest;
			
			//本金余额
			principalBalance-=principal;
			
			rp.setShouldPrincipal(Double.parseDouble(df.format(principal)));
			
			rp.setShouldMangCost(Double.parseDouble(df.format(mangCost)));
			rp.setShouldOtherCost(Double.parseDouble(df.format(otherCost)));
			rp.setShouldInterest(Double.parseDouble(df.format(interest)));
			rp.setTotal(Double.parseDouble(df.format(total)));
			
			rp.setPrincipalBalance(Double.parseDouble(df.format(principalBalance)));
			
			
			list.add(rp);
				
			
		
		}
		return list;
	}

}
