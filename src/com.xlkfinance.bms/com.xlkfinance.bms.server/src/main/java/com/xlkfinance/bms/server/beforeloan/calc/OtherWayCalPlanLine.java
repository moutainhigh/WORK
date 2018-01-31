/**
 * @Title: MonthInterestCalPlanLine.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 其他还款方式
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
@Component("otherWayCalPlanLine")
public class OtherWayCalPlanLine extends CalPlanLine {
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@Override
	public List<RepaymentPlanBaseDTO> execute(Loan loan,int userId) {
		
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		
		int version=loanRepaymentPlanMapper.getMaxVersion(loan.getPid())+1;
		
		int repayD=loan.getRepayDate();
		
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
			
			
			RepaymentPlanBaseDTO rp=new RepaymentPlanBaseDTO();
			//还款计划表固定值
			setFixedRepayMent(rp,loan,userId,version,num,nextMonthDt.getTime());
			list.add(rp);
			
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayD));
		
		}
		return list;
	}

}
