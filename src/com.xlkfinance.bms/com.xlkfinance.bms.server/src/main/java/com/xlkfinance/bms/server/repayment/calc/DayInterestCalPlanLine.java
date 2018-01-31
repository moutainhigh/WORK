/**
 * @Title: MonthInterestCalPlanLine.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 按日（5,7,10,15）收息-到期还本
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月4日 下午2:42:58
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.calc;

import java.util.Calendar;
import java.util.Date;
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
import com.xlkfinance.bms.server.repayment.mapper.RealtimePlanMapper;

@Component("repaymentDayInterestCalPlanLine")
@Scope("prototype")
public class DayInterestCalPlanLine extends CalPlanLine {
	
	private Logger logger = LoggerFactory.getLogger(DayInterestCalPlanLine.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMidMapper")
	private LoanRepaymentPlanMidMapper loanRepaymentPlanMidMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "realtimePlanMapper")
	private RealtimePlanMapper realtimePlanMapper;
	
	@Resource(name = "calcHelper")
	private CalcHelper calcHelper;
	
	@Override
	public List<RepaymentPlanBaseDTO> execute(Loan loan,int userId) {
		
		logger.info("DayInterestCalPlanLine execute 按天-5，7，15，20日 到期还本  生成还款计划表");
		
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		
		int version=loanRepaymentPlanMidMapper.getMaxVersionMid(loan.getPid())+1;
		
		CalcDto calDto = new CalcDto();
		
		calDto.setPrincipalBalance(loan.getCreditAmt());//本金余额
		
		Calendar outDt=Calendar.getInstance();//放款日期
		outDt.setTime(DateUtil.format(loan.getPlanOutLoanDt(),"yyyy-MM-dd"));
		
		Calendar maxrepayDt=Calendar.getInstance();//计划还款日期
		maxrepayDt.setTime(DateUtil.format(loan.getPlanRepayLoanDt(),"yyyy-MM-dd"));
		
		Calendar nextMonthDt=Calendar.getInstance();//还款日期+
		nextMonthDt.setTime(outDt.getTime());
		
		calDto.getCalcInterestDt().setTime(outDt.getTime());//算息日期
		
		//保存手续费
		if(getOperDto().getRepayType()==1 || getOperDto().getRepayType()==7){
			calcHelper.saveFormalitiesFee(loan, userId);
		}else if(getOperDto().getRepayType()==3){
			//把新利率设置到贷款信息里面
			dxchangeRate(loan,getOperDto());
		}
		
		for(int num=1;num<=loan.getRepayCycle();num++) {
			
			if(num==loan.getRepayCycle()){
				calDto.setPrincipal(calDto.getPrincipalBalance());
				calDto.setPrincipalBalance(0);
			}
			
			//算头不算尾 从放款日期开始算利息
			if(num==1){
				nextMonthDt.add(Calendar.DAY_OF_MONTH, (getDay()-1));
			}else if(num>1){
				nextMonthDt.add(Calendar.DAY_OF_MONTH, getDay());
			}
			//生成还款日期大于最后还款日期 
			if(nextMonthDt.getTime().getTime()>maxrepayDt.getTime().getTime() || num ==loan.getRepayCycle()){
				nextMonthDt.setTime(maxrepayDt.getTime());
			}
			
			//算息日期是否大于最后还款日期
			if(calDto.getCalcInterestDt().getTime().getTime()>maxrepayDt.getTime().getTime()){
				calDto.getCalcInterestDt().setTime(maxrepayDt.getTime());
			}
			
			switch (getOperDto().getRepayType()) {
			case 1://贷款试算
				list.add(setRepayMent(loan,getDay(),userId,nextMonthDt.getTime(),num,calDto.getPrincipal(),calDto.getPrincipalBalance(),version,getOperDto().getExTarget()));
				break;
			case 2://提前还款
				repayCalc(calDto,list,loan,num,userId,nextMonthDt,outDt,maxrepayDt,version);
				break;	
			case 3://利率变更
				repayCalc(calDto,list,loan,num,userId,nextMonthDt,outDt,maxrepayDt,version);
				break;	
			case 7://贷款展期
				list.add(setRepayMent(loan,getDay(),userId,nextMonthDt.getTime(),num,calDto.getPrincipal(),calDto.getPrincipalBalance(),version,getOperDto().getExTarget()));
				break;	
			default:
				break;
			}
			
			if(num==1){
				calDto.getCalcInterestDt().add(Calendar.DAY_OF_MONTH, (getDay()-1));//算息日期
			}else{
				calDto.getCalcInterestDt().add(Calendar.DAY_OF_MONTH, getDay());
			}
			
		}
		
		//即时发生表数据
		calcHelper.saveRealtimePlan(getOperDto(),loan,list,userId,outDt,calDto,1);
		
		return list;
	}
	
	/**
	 * 
	  * @Description: 提前还款生成还款计划表
	  * @param list 重新生成还款计划list
	  * @param loan 贷款信息
	  * @param num 期数
	  * @param userId 用户Id
	  * @param nextMonthDt 还款日期
	  * @param outDt 放款日期
	  * @param maxrepayDt 最后还款日期
	  * @param version 最新版本
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月14日 下午2:00:42
	 */
	public void repayCalc(CalcDto calDto,List<RepaymentPlanBaseDTO> list,Loan loan,int num,int userId,Calendar nextMonthDt,Calendar outDt,Calendar maxrepayDt,int version){
		//判断是否是提取还款贷款试算
		if (getOperDto()!=null){
			
			Date planRepayDt=DateUtil.format(getOperDto().getOperRepayDt(),"yyyy-MM-dd");//提取还款日
			
			//判断算息日期小于提前还款日期
			if(calDto.getCalcInterestDt().getTime().getTime()< planRepayDt.getTime()){
				RepaymentPlanBaseDTO repayment=calcHelper.getRepayment(loan.getPid(),num,version);
				list.add(repayment);
				calDto.setPrincipal(repayment.getShouldPrincipal());
				calDto.setPrincipalBalance(repayment.getPrincipalBalance());
				
				if(getOperDto().getRepayType()==2){//提前还款
					//计算当期未满的利息
					calcInterest(calDto,list,loan,outDt,maxrepayDt,planRepayDt,num,loan.getMonthLoanMgr(),loan.getMonthLoanOtherFee(),loan.getMonthLoanInterest());
					//提前还款当月本金余额去除提前坏款金额
					if(calDto.getCurrMoney()>0){
						repayment.setPrincipalBalance(repayment.getPrincipalBalance()-getOperDto().getOperAmt());
					}else{
						double shouldP=repayment.getShouldPrincipal();
						calDto.setCurrMoney(shouldP);
					}
					
					//最后一期应还本金减去 提前还款金额
					if(loan.getRepayCycle()==num){
						repayment.setShouldPrincipal(repayment.getShouldPrincipal()-getOperDto().getOperAmt());
						repayment.setTotal(repayment.getTotal()-getOperDto().getOperAmt());
					}
				}else if(getOperDto().getRepayType()==3){//利率变更
					getOperDto().setOperAmt(repayment.getPrincipalBalance());
					//计算当期未满的利息
					calcInterest(calDto,list,loan,outDt,maxrepayDt,planRepayDt,num,getOperDto().getMonthLoanMgr(),getOperDto().getMonthLoanOtherFee(),getOperDto().getMonthLoanInterest());
				}
			}else{
				//本金余额大于0 继续生成 提前还款之后的还款计划表
				if(calDto.getPrincipalBalance()>0 || calDto.getPrincipal()>0){
					list.add(setRepayMent(loan,getDay(),userId,nextMonthDt.getTime(),num,calDto.getPrincipal(),calDto.getPrincipalBalance(),version,getOperDto().getExTarget()));
				}
			}
		}
	}
	
	/**
	 * 
	  * @Description: 计算计息日期与提前还款日期不同时，的应退金额
	  * @param loan
	  * @author: zhanghg
	  * @date: 2015年3月12日 下午6:18:54
	 */
	private void calcInterest(CalcDto calDto,List<RepaymentPlanBaseDTO> list,Loan loan,Calendar outDt,Calendar maxrepayDt,Date planRepayDt,int num,double monthLoanMgr,double monthLoanOtherFee,double monthLoanInterest){
		//下一次算息时间
		Calendar c=Calendar.getInstance();
		c.setTime(calDto.getCalcInterestDt().getTime());
		
		if(num==1){
			c.add(Calendar.DAY_OF_MONTH, (getDay()-1));//算息日期
		}else{
			c.add(Calendar.DAY_OF_MONTH, getDay());
		}
		
		if(c.getTime().getTime()>maxrepayDt.getTime().getTime()){
			c.setTime(maxrepayDt.getTime());
		}
		//判断是否是当月的提前还款
		if(planRepayDt.getTime()<=c.getTime().getTime()){
			double currMoney=list.get(list.size()-1).getPrincipalBalance();
			calDto.setCurrMoney(currMoney);
			
			double repayAmt=realtimePlanMapper.getRepayMinAmt(loan.getPid());
			if(repayAmt==0 || repayAmt>calDto.getCurrMoney()){
				repayAmt=currMoney;
			}else{
				if(getOperDto().getRepayType()==3){
					
					calDto.setPrincipalBalance(repayAmt);//本金余额
					calDto.setPrincipalBalance(calDto.getCurrMoney()-getOperDto().getOperAmt());//本金余额
				}
			}
			if(getOperDto().getRepayType()==2){
				
				calDto.setPrincipalBalance(repayAmt-getOperDto().getOperAmt());//本金余额
				calDto.setPrincipalBalance(calDto.getCurrMoney()-getOperDto().getOperAmt());//本金余额
			}
		}
	}

}
