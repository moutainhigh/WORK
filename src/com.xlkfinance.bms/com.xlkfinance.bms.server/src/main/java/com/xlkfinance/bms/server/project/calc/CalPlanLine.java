/**
 * 贷款利息计算抽象类
 */
package com.xlkfinance.bms.server.project.calc;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
/**
 * 
 * ClassName: CalPlanLine <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年12月11日 下午6:42:18 <br/>
 * 贷款利息计算抽象类
 * @author baogang
 * @version V1.0
 *
 */
public abstract class CalPlanLine {
	public abstract List<RepaymentPlanBaseDTO> execute(Project project, int userId);

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
	 * 计算首期还款日，如果放款时间为1号，首次还款时间为当月28号，如果放款时间为29、30、31日的，首次还款时间为次月28日，其余放款时间，首次还款日为次月时间减去一天
	 * 如：放款日为5月8日，首次还款日为6月7日
	 * @return
	 */
	public static String getFirstRepaymentDate(String receDate){
		//获取放款的日期
		Date receDt = DateUtils.stringToDate(receDate,"yyyy-MM-dd");//放款时间
		int repayDay = DateUtils.getDayOfMonthByDate(receDt);
		Date paymentDate = null;
		Calendar outDt=Calendar.getInstance();//放款日期
		outDt.setTime(receDt);
		Calendar nextMonthDt=Calendar.getInstance();//还款日期
		if(repayDay == 1){
			nextMonthDt.setTime(outDt.getTime());
			nextMonthDt.set(Calendar.DAY_OF_MONTH, 28);
			paymentDate = nextMonthDt.getTime();
		}else if(repayDay>28){
			nextMonthDt.setTime(outDt.getTime());
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, 28);
			paymentDate = nextMonthDt.getTime();
		}else{
			nextMonthDt.setTime(outDt.getTime());
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, repayDay-1);
			paymentDate = nextMonthDt.getTime();
		}
		
		if(paymentDate != null){
			return DateUtils.dateToString(paymentDate, "yyyy-MM-dd");
		}
		return "";
	}
	
	
	/**
	 * 
	 * setRepayMent:先息后本利息计算. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param project
	 * @param userId
	 * @param nextMonthDt
	 * @param stageNum
	 * @param principal
	 * @param principalBalance
	 * @param version
	 * @return
	 *
	 */
	public RepaymentPlanBaseDTO setRepayMent(Project project, int userId,
			Date nextMonthDt, int stageNum, double principal,
			double principalBalance, int version) {
		RepaymentPlanBaseDTO rp = new RepaymentPlanBaseDTO();
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);

		// 还款计划表固定值
		setFixedRepayMent(rp, project, userId, version, stageNum, nextMonthDt);

		// 设置计算利息值
		rp.setShouldPrincipal(Double.parseDouble(df.format(principal)));

		//项目贷款信息
		ProjectGuarantee projectGuarantee = project.getProjectGuarantee();
		//月利率
		double interestRate = projectGuarantee.getFeeRate();
		//月返佣率
		double monthMaidRate = projectGuarantee.getMonthMaidRate();
		//应还利息 = 本金余额*月利率
		rp.setShouldInterest(Double.parseDouble(df
				.format((principalBalance+principal) * interestRate / 100)));
		//月返佣率
		rp.setMonthMaidRate(monthMaidRate);
		//返佣利息=月返佣率*（应还本金+本金余额）
		rp.setRebateFee(Double.parseDouble(df
				.format((principalBalance+principal) * monthMaidRate / 100)));
		
		//应还合计= 应还本金+应还利息
		rp.setTotal(Double.parseDouble(df.format(rp.getShouldInterest() + principal)));
		//本金余额，最后一期本金余额等于本金
		rp.setPrincipalBalance(Double.parseDouble(df.format(principalBalance)));
		return rp;
	}

	/**
	 * 
	 * setFixedRepayMent:设置还款计划表固定值. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param rp 还款计划
	 * @param project 项目信息
	 * @param userId 创建人
	 * @param version 版本
	 * @param stageNum 期数
	 * @param nextMonthDt 还款日
	 *
	 */
	public void setFixedRepayMent(RepaymentPlanBaseDTO rp, Project project,
			int userId, int version, int stageNum, Date nextMonthDt) {
		rp.setProjectId(project.getPid());
		rp.setGenUserId(userId);
		rp.setGenDttm(DateUtil.format(new Date()));
		rp.setPlanType(1);
		rp.setExTarget(0);
		rp.setExType(0);
		rp.setThisStatus(Constants.REPAYMENT_PLAN_STATUS_1);//设置还款计划期数中的状态为1：正常未还
		rp.setPlanVersion(version);
		rp.setStatus(1);
		rp.setIsReconciliation(2);// 未对账
		rp.setShouldOtherCostName("其他费用");
		rp.setPlanCycleNum(stageNum);
		if(nextMonthDt != null){
			rp.setPlanRepayDt(DateUtils.dateFormatByPattern(nextMonthDt, "yyyy-MM-dd"));
		}
		rp.setMonthMaidRate(project.getProjectGuarantee().getMonthMaidRate());//月返佣率
	}
	
	/**
	 * 
	 * contrDouble:double类型格式化. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param m
	 * @return
	 *
	 */
	public double contrDouble(double m){
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return Double.parseDouble(df.format(m));
	}
}
