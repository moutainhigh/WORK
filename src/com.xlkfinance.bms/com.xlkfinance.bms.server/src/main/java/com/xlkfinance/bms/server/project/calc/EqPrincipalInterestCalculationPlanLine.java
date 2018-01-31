/**
 * EqPrincipalInterestCalculationPlanLine  等额本息计算
 */
package com.xlkfinance.bms.server.project.calc;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.NumberUtils;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
/**
 * 
 * ClassName: EqPrincipalInterestCalculationPlanLine <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2018年1月3日 下午3:37:26 <br/>
 *	等额本息贷款还款计划表生成类
 * @author baogang
 * @version 
 *
 */
@Component("eqPrincipalInterestCalculationPlanLine")
@Scope("prototype")
public class EqPrincipalInterestCalculationPlanLine extends CalPlanLine {
	
	@Override
	public List<RepaymentPlanBaseDTO> execute (Project project, int userId)
	{
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		
		int version=1;
		//实际放款日期
		ProjectForeclosure projectForeclosure = project.getProjectForeclosure();
		String receDate = projectForeclosure.getReceDate();
		String firstMonthDate = null;
		//计算下一还款日
		Calendar nextMonthDt = null;
		int repayDay = 0;
		if(receDate != null){
			firstMonthDate = getFirstRepaymentDate(receDate);
			Calendar outDt=Calendar.getInstance();
			outDt.setTime(DateUtil.format(firstMonthDate,"yyyy-MM-dd"));
			//获取首次还款日的日期
			repayDay = DateUtils.getDayOfMonthByDate(DateUtils.stringToDate(firstMonthDate,"yyyy-MM-dd"));
			//计算下一还款日
			nextMonthDt=Calendar.getInstance();//还款日期
			nextMonthDt.setTime(outDt.getTime());
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayDay));
		}

		//项目借款信息
		ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
		double principalBalance=projectGuarantee.getLoanMoney();//本金余额=终审的借款金额
		
		double feeRate = projectGuarantee.getFeeRate()/100;//贷款月利率
		int loanTerm = projectGuarantee.getLoanTerm();//贷款期限
		double loanMoney = projectGuarantee.getLoanMoney();//贷款金额
		double loanMonthlyReturn = NumberUtils.PMT(feeRate, loanTerm, loanMoney);//每月还款金额=应还本金+应还利息
		double shouldPrincipal = 0;
		//循环计算各期还款数据
		for(int num=1;num<=projectGuarantee.getLoanTerm();num++) {
			
			RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
			
			// 还款计划表固定值
			setFixedRepayMent(rp, project, userId, version, num, null);
			
			double interest = principalBalance*feeRate;//应还利息等于应还本金余额*月利率
			shouldPrincipal = loanMonthlyReturn - interest;//本期应还总计-应还利息等于本期应还本金
			
			if(nextMonthDt != null && repayDay>0){
				rp.setPlanRepayDt(DateUtils.dateFormatByPattern(nextMonthDt.getTime(), "yyyy-MM-dd"));
				nextMonthDt.add(Calendar.MONTH, 1);
				nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayDay));
			}
			rp.setShouldPrincipal(Double.parseDouble(df.format(shouldPrincipal)));
			rp.setShouldInterest(Double.parseDouble(df.format(interest)));
			rp.setTotal(Double.parseDouble(df.format(loanMonthlyReturn)));
			
			rp.setPrincipalBalance(Double.parseDouble(df.format(principalBalance)));
			list.add(rp);
			
			//本金余额
			principalBalance-=shouldPrincipal;
			
			
		}
		return list;
	}
}
