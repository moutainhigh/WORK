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
package com.xlkfinance.bms.server.repayment.calc;

import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMidMapper;
@Component("repaymentOtherWayCalPlanLine")
@Scope("prototype")
public class OtherWayCalPlanLine extends CalPlanLine {
	private Logger logger = LoggerFactory.getLogger(OtherWayCalPlanLine.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMidMapper")
	private LoanRepaymentPlanMidMapper loanRepaymentPlanMidMapper;
	
	@Resource(name = "calcHelper")
	private CalcHelper calcHelper;
	
	@Override
	public List<RepaymentPlanBaseDTO> execute(Loan loan,int userId) {
		logger.info("OtherWayCalPlanLine execute 其他还款方式 生成还款计划表");
		
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		
		CalcDto calDto = new CalcDto();
		
		int version=loanRepaymentPlanMidMapper.getMaxVersionMid(loan.getPid())+1;
		
		int repayD=loan.getRepayDate();
		
		Calendar outDt=Calendar.getInstance();//放款日期
		outDt.setTime(DateUtil.format(loan.getPlanOutLoanDt(),"yyyy-MM-dd"));
		
		Calendar maxrepayDt=Calendar.getInstance();//计划还款日期
		maxrepayDt.setTime(DateUtil.format(loan.getPlanRepayLoanDt(),"yyyy-MM-dd"));
		
		Calendar nextMonthDt=Calendar.getInstance();//还款日期
		nextMonthDt.setTime(outDt.getTime());
		
		//保存手续费
		if(getOperDto().getRepayType()==1 || getOperDto().getRepayType()==7){
			calcHelper.saveFormalitiesFee(loan, userId);
		}
		
		// update by zhangyu 屏蔽 其它还款方式只有变动还款期限的情况下才生成还款计划表
		//if(loan.getJudgeRepayCycle()==1){
			for(int num=1;num<=loan.getRepayCycle();num++) {
				
				nextMonthDt.add(Calendar.MONTH, 1);
				nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayD));
				
				if(nextMonthDt.getTime().getTime()>maxrepayDt.getTime().getTime() || num ==loan.getRepayCycle()){
					nextMonthDt.setTime(maxrepayDt.getTime());
				}
				if (getOperDto().getRepayType()==1 ||getOperDto().getRepayType()==7) {
					RepaymentPlanBaseDTO rp=new RepaymentPlanBaseDTO();
					//还款计划表固定值
					setFixedRepayMent(rp,loan,userId,version,num,nextMonthDt.getTime(),getOperDto().getExTarget());
					list.add(rp);
				}
				
			
			}
		//}
		
		
		//即时发生表数据
		calcHelper.saveRealtimePlan(getOperDto(),loan,list,userId,outDt,calDto,1);
				
		return list;
	}

}
