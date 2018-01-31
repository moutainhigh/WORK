
package com.xlkfinance.bms.server.project.calc;

import java.util.Calendar;
import java.util.List;

import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
/**
 * 
 * ClassName: MonthInterestCalPlanLine <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年12月11日 下午6:43:23 <br/>
 * 先息后本贷款还款计划表生成类
 * @author baogang
 * @version 
 *
 */
@Component("monthInterestCalculationPlan")
public class MonthInterestCalculationPlan extends CalPlanLine {
	
	@Override
	public List<RepaymentPlanBaseDTO> execute(Project project,int userId) {
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		
		int version=1;
		//实际放款日期
		ProjectForeclosure projectForeclosure = project.getProjectForeclosure();
		String receDate = projectForeclosure.getReceDate();
		//计划还款日期
		String paymentDate = project.getProjectForeclosure().getPaymentDate();
		
		//获取还款日的日期
		int repayDay = DateUtils.getDayOfMonthByDate(DateUtils.stringToDate(receDate,"yyyy-MM-dd"));
		
		
		double principal=0;//应还本金
		//项目借款信息
		ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
		double principalBalance=projectGuarantee.getLoanMoney();//本金余额
		
		Calendar outDt=Calendar.getInstance();//放款日期
		outDt.setTime(DateUtil.format(receDate,"yyyy-MM-dd"));
		
		Calendar maxrepayDt=Calendar.getInstance();//计划还款日期
		maxrepayDt.setTime(DateUtil.format(paymentDate,"yyyy-MM-dd"));
		//计算下一还款日
		Calendar nextMonthDt=Calendar.getInstance();//还款日期
		nextMonthDt.setTime(outDt.getTime());
		nextMonthDt.add(Calendar.MONTH, 1);
		nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayDay));
		
		//循环计算各期还款数据
		for(int num=1;num<=projectGuarantee.getLoanTerm();num++) {
			
			if(nextMonthDt.getTime().getTime()>maxrepayDt.getTime().getTime()){
				nextMonthDt.setTime(maxrepayDt.getTime());
			}
			//是否最后一期
			if(num<projectGuarantee.getLoanTerm()){
				//添加还款计划
				list.add(setRepayMent(project,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
			}else{
				list.add(setRepayMent(project,userId,nextMonthDt.getTime(),num,principalBalance,principal,version));
			}
			//list.add(setRepayMent(project,userId,nextMonthDt.getTime(),num,principal,principalBalance,version));
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayDay));
		}
		return list;
	}

}
