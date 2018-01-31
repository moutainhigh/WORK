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
@Component("repaymentEqInterestCalPlanLine")
@Scope("prototype")
public class EqInterestCalPlanLine extends CalPlanLine {
	
	private Logger logger = LoggerFactory.getLogger(EqInterestCalPlanLine.class);
	
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
		
		logger.info("EqInterestCalPlanLine execute 等额本息  生成还款计划表");
		
		List<RepaymentPlanBaseDTO> list=Lists.newArrayList();
		int version=loanRepaymentPlanMidMapper.getMaxVersionMid(loan.getPid())+1;
		
		CalcDto calDto = new CalcDto();
		
		int repayD=loan.getRepayDate();
		
		calDto.setPrincipalBalance(loan.getCreditAmt());//本金余额
		
		Calendar outDt=Calendar.getInstance();//放款日期
		outDt.setTime(DateUtil.format(loan.getPlanOutLoanDt(),"yyyy-MM-dd"));
		
		Calendar maxrepayDt=Calendar.getInstance();//计划还款日期
		maxrepayDt.setTime(DateUtil.format(loan.getPlanRepayLoanDt(),"yyyy-MM-dd"));
		
		Calendar nextMonthDt=Calendar.getInstance();//还款日期
		nextMonthDt.setTime(outDt.getTime());
		
		calDto.getCalcInterestDt().setTime(outDt.getTime());//算息日期
		
		//保存手续费
		if(getOperDto().getRepayType()==1 || getOperDto().getRepayType()==7){
			calcHelper.saveFormalitiesFee(loan, userId);
		}else if(getOperDto().getRepayType()==3){
			//把新利率设置到贷款信息里面
			dxchangeRate(loan,getOperDto());
		}
		
		//计算等额本息 的合计金额
		calDto.setTotal(calcTotal(loan,loan.getCreditAmt(),loan.getRepayCycle()));
		
		for(int num=1;num<=loan.getRepayCycle();num++) {
			
			nextMonthDt.add(Calendar.MONTH, 1);
			nextMonthDt.set(Calendar.DAY_OF_MONTH, getDay(nextMonthDt,repayD));
			
			if(nextMonthDt.getTime().getTime()>maxrepayDt.getTime().getTime() || num ==loan.getRepayCycle()){
				nextMonthDt.setTime(maxrepayDt.getTime());
			}
			
			//算息日期是否大于最后还款日期
			if(calDto.getCalcInterestDt().getTime().getTime()>maxrepayDt.getTime().getTime()){
				calDto.getCalcInterestDt().setTime(maxrepayDt.getTime());
			}
			
			switch (getOperDto().getRepayType()) {
			case 1://贷款试算
				calcEqInterest(calDto,list,loan,userId,nextMonthDt,num,version);
				break;
			case 2://提前还款
				repayCalc(calDto,list,loan,num,userId,nextMonthDt,outDt,maxrepayDt,version);
				break;
			case 3://利率变更
				repayCalc(calDto,list,loan,num,userId,nextMonthDt,outDt,maxrepayDt,version);
				break;
			case 7://贷款展期
				calcEqInterest(calDto,list,loan,userId,nextMonthDt,num,version);
				break;
			default:
				break;
			}
			calDto.getCalcInterestDt().setTime(getCalcInterestDt(outDt,num).getTime()); //算息日期
		}
		
		//即时发生表数据
		calcHelper.saveRealtimePlan(getOperDto(),loan,list,userId,outDt,calDto,1);
		
		return list;
	}
	
	private void calcEqInterest(CalcDto calDto,List<RepaymentPlanBaseDTO> list,Loan loan,int userId,Calendar nextMonthDt,int num,int version){
		//计算利息（贷款利息，管理费利息，其他利息）
		double mangCost=calDto.getPrincipalBalance()*loan.getMonthLoanMgr()/100;
		double otherCost=calDto.getPrincipalBalance()*loan.getMonthLoanOtherFee()/100;
		double interest=calDto.getPrincipalBalance()*loan.getMonthLoanInterest()/100;
		
		//应还本金
		calDto.setPrincipal(calDto.getTotal()-mangCost-otherCost-interest);
		
		//本金余额
		calDto.setPrincipalBalance(calDto.getPrincipalBalance()-calDto.getPrincipal());
		
		list.add(setEqPrincipalInterest(loan,userId,nextMonthDt.getTime(),num,calDto.getPrincipal(),calDto.getPrincipalBalance(),version,mangCost,otherCost,interest,getOperDto().getExTarget()));
	}
	
	/**
	 * 
	  * @Description: 提前还款，生成还款计划表
	  * @param list 还款计划表集合
	  * @param loan 贷款信息
	  * @param num 期数
	  * @param userId 用户Id
	  * @param nextMonthDt 还款日期
	  * @param outDt 放款日期
	  * @param maxrepayDt 最后还款日期
	  * @param principal 应还本金
	  * @param version 版本
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月13日 下午3:47:18
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
				if(calDto.getPrincipalBalance()>0){
					calcEqInterest(calDto,list,loan,userId,nextMonthDt,num,version);
				}
			}
		}
	}
	
	
	/**
	 * 
	  * @Description: 计算等额本息 的合计金额
	  * @param loan 贷款信息
	  * @param creditAmt 计算总金额
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月16日 下午4:25:31
	 */
	private double calcTotal(Loan loan,double creditAmt,int num){
		double monthRate=(loan.getMonthLoanInterest()+loan.getMonthLoanMgr()+loan.getMonthLoanOtherFee())/100;	
		double total =creditAmt*monthRate*Math.pow((1+monthRate),num)/(Math.pow((1+monthRate),num)-1);
		return total;
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
		c.setTime(getCalcInterestDt(outDt,num).getTime());
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
					//提前还款-计算等额本息 的合计金额
					calDto.setTotal(calcTotal(loan,calDto.getPrincipalBalance(),(loan.getRepayCycle()-list.size())));
				}
			}
			if(getOperDto().getRepayType()==2){
				calDto.setPrincipalBalance(repayAmt-getOperDto().getOperAmt());//本金余额
				//提前还款-计算等额本息 的合计金额
				calDto.setTotal(calcTotal(loan,calDto.getPrincipalBalance(),(loan.getRepayCycle()-list.size())));
			}
		}
	}
	
	

}
