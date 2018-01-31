/**
 * @Title: CalcDto.java
 * @Package com.xlkfinance.bms.server.repayment.calc
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年3月18日 上午10:56:46
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.calc;

import java.util.Calendar;

public class CalcDto {
	double principalBalance=0;//本金余额
	
	double principal=0;//应还本金
	
	Calendar calcInterestDt=Calendar.getInstance();//算息日期
	
	double total=0;//合计
	double mangCost=0;//管理费
	double otherCost=0;//其他费用
	double interest=0;//贷款利息
	
	int isRefund=0;//是否算息日期=提前还款日期
	
	//提前还款日期与算息日期不相等   则计算当月的贷款利息
	double currMangCost=0; 
	double currOtherCost=0;
	double currInterest=0;
	double currMoney=0;
	public double getPrincipalBalance() {
		return principalBalance;
	}
	public void setPrincipalBalance(double principalBalance) {
		this.principalBalance = principalBalance;
	}
	public double getPrincipal() {
		return principal;
	}
	public void setPrincipal(double principal) {
		this.principal = principal;
	}
	public Calendar getCalcInterestDt() {
		return calcInterestDt;
	}
	public void setCalcInterestDt(Calendar calcInterestDt) {
		this.calcInterestDt = calcInterestDt;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public double getMangCost() {
		return mangCost;
	}
	public void setMangCost(double mangCost) {
		this.mangCost = mangCost;
	}
	public double getOtherCost() {
		return otherCost;
	}
	public void setOtherCost(double otherCost) {
		this.otherCost = otherCost;
	}
	public double getInterest() {
		return interest;
	}
	public void setInterest(double interest) {
		this.interest = interest;
	}
	public int getIsRefund() {
		return isRefund;
	}
	public void setIsRefund(int isRefund) {
		this.isRefund = isRefund;
	}
	public double getCurrMangCost() {
		return currMangCost;
	}
	public void setCurrMangCost(double currMangCost) {
		this.currMangCost = currMangCost;
	}
	public double getCurrOtherCost() {
		return currOtherCost;
	}
	public void setCurrOtherCost(double currOtherCost) {
		this.currOtherCost = currOtherCost;
	}
	public double getCurrInterest() {
		return currInterest;
	}
	public void setCurrInterest(double currInterest) {
		this.currInterest = currInterest;
	}
	public double getCurrMoney() {
		return currMoney;
	}
	public void setCurrMoney(double currMoney) {
		this.currMoney = currMoney;
	}
	
	
	
	
}
