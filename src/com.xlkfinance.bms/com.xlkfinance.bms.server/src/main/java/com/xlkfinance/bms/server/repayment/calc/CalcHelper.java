/**
 * @Title: CalcHelper.java
 * @Package com.xlkfinance.bms.server.repayment.calc
 * @Description: 贷款试算帮助类
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年3月14日 上午10:18:17
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment.calc;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.repayment.RealtimePlan;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanRepaymentPlanMidMapper;
import com.xlkfinance.bms.server.finance.util.FinanceTypeEnum;
import com.xlkfinance.bms.server.repayment.mapper.LoanExtensionMapper;
import com.xlkfinance.bms.server.repayment.mapper.RealtimePlanMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;
@SuppressWarnings("unchecked")
@Component("calcHelper")
public class CalcHelper {
	
	private Logger logger = LoggerFactory.getLogger(CalcHelper.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMidMapper")
	private LoanRepaymentPlanMidMapper loanRepaymentPlanMidMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanRepaymentPlanMapper")
	private LoanRepaymentPlanMapper loanRepaymentPlanMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "realtimePlanMapper")
	private RealtimePlanMapper realtimePlanMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanMapper")
	private LoanMapper loanMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "loanExtensionMapper")
	private LoanExtensionMapper loanExtensionMapper;
	
	private int dayOfM=30; //一个月=30天
	
	
	/**
	 * 
	  * @Description: 保存手续费信息
	  * @param loan
	  * @param userId
	  * @author: zhanghg
	  * @date: 2015年3月18日 下午6:31:52
	 */
	public void saveFormalitiesFee(Loan loan,int userId){
		
			List<RealtimePlan> plans=Lists.newArrayList();
			//最大版本号
			int realtimePlanVersion=realtimePlanMapper.getMaxVersion(loan.getPid());
			realtimePlanVersion++;
			//计算手续费
			double money= contrDouble(loan.getCreditAmt()*loan.getFeesProportion()/100);// 手续费比例未填
			plans.add(setRealtime(loan,loan.getPlanOutLoanDt(), FinanceTypeEnum.TYPE_10.getType(), loan.getCreditAmt(), money, userId, money,loan.getCreditAmt(), realtimePlanVersion, 0,0,isReconciliation(money)));
			realtimePlanMapper.insertRealtimePlans(plans);
		
	}
	
	private int isReconciliation(double amt){
		return amt==0?1:2;
	}
	
	/**
	 * 
	  * @Description: 查询当前流程之外的 即时发生数据
	  * @param loan 贷款信息
	  * @param operType 即时发生类型
	  * @param refId 即时 引用ID
	  * @param refundType 退款类型
	  * @param realtimePlanVersion 最大版本号
	  * @param reconType 是否坏账 
	  * @return
	  * @author: zhanghg
	  * @date: 2015年4月1日 下午2:33:54
	 */
	public List<RealtimePlan> getRealtimePlans(Loan loan,int operType,int refId,int refundType,int realtimePlanVersion,int reconType){
		//即时发生表中除当前提前还款外其他数据（逾期等。。）
				Map<String,String> map=new HashMap<String, String>();
				map.put("loanId", loan.getPid()+"");
				map.put("operType", operType+"");
				map.put("refId", refId+"");
				map.put("refundType", refundType+"");
				
				List<RealtimePlan> plans=realtimePlanMapper.selectRealtimePlanByloanId(map);
				if(plans!=null && plans.size()>0){
					for(RealtimePlan rp:plans){
						rp.setPId("0");
						rp.setPlanVersion(realtimePlanVersion);
						rp.setFreezeStatus(0);
						if(rp.getIsReconciliation() !=1 && reconType==4){
							rp.setIsReconciliation(4);
						}
					}
				}else{ 
					plans=Lists.newArrayList();
				}
				return plans;
	}
	
	public List<RealtimePlan> setRealtimePlan(List<RealtimePlan> plans,List<RepaymentPlanBaseDTO> list,Loan loan,int operType,int refId,int refundType,int realtimePlanVersion,
			double currMangCost,double currOtherCost,double currInterest,double currMoney,String repayDate,double repayAmt,int userId){
		
		double mangCost=0;
		double otherCost=0;
		double interest=0;
		if(loan.getRepayOption()==3 ||loan.getRepayOption()==4){
			List<RepaymentPlanBaseDTO> maxList=loanRepaymentPlanMidMapper.selectNoFreezeRepaymentByLoanId(loan.getPid());
			//最后一次流程完成的还款计划表利息总和
			for(RepaymentPlanBaseDTO dto: maxList){
				mangCost+=dto.getShouldMangCost();
				otherCost+=dto.getShouldOtherCost();
				interest+=dto.getShouldInterest();
			}
			//减去当前提前还款 利息总和
			for(RepaymentPlanBaseDTO dto: list){
				mangCost-=dto.getShouldMangCost();
				otherCost-=dto.getShouldOtherCost();
				interest-=dto.getShouldInterest();
			}
			
		}
		
		//退款 金额改为负数  增款未正数
		mangCost+=currMangCost;
		mangCost= mangCost<0?Math.abs(mangCost):mangCost*-1; 
		
		otherCost+=currOtherCost;
		otherCost= otherCost<0?Math.abs(otherCost):otherCost*-1; 
		
		interest+=currInterest;
		interest= interest<0?Math.abs(interest):interest*-1; 
		
		//退还金额
		if(mangCost !=0){
			plans.add(setRealtime(loan,repayDate, FinanceTypeEnum.TYPE_4.getType(), repayAmt, mangCost, userId, mangCost,currMoney, realtimePlanVersion, refId,refundType,2));
		}
		if(otherCost !=0){
			plans.add(setRealtime(loan,repayDate, FinanceTypeEnum.TYPE_5.getType(), repayAmt, otherCost, userId, otherCost,currMoney, realtimePlanVersion,refId,refundType,2));
		}
		if(interest !=0){
			plans.add(setRealtime(loan,repayDate, FinanceTypeEnum.TYPE_6.getType(), repayAmt, interest, userId, interest,currMoney, realtimePlanVersion, refId,refundType,2));		
		}
		return plans;
	}
	
	/**
	 * 
	  * @Description: 保存提前还款-及时发生表
	  * @author: zhanghg
	  * @date: 2015年3月9日 上午11:19:06
	 */
	public RealtimePlan setRealtime(Loan loan,String operRepayDt,int operType,double baseAmt,double operCost,int userId,double total,double principalBalance,int planVersion,int refId,int refundType,int isReconciliation){
		
		RealtimePlan plan=new RealtimePlan();
		plan.setLoanId(loan.getPid());
		plan.setOperRepayDt(operRepayDt);
		plan.setOperType(operType);
		plan.setBaseAmt(baseAmt);
		plan.setOperCost(operCost);
		plan.setGenDttm(DateUtil.format(new Date()));
		plan.setOperUserId(userId);
		plan.setTotal(total);
		plan.setPrincipalBalance(principalBalance);
		plan.setPlanVersion(planVersion);
		plan.setRefId(refId);
		plan.setIsReconciliation(isReconciliation);
		plan.setRefundType(refundType);
		plan.setFreezeStatus(0);
		return plan;
	
	}
	
	/**
	 * 
	  * @Description: 根据期数，贷款Id查询还款信息
	  * @param loanId
	  * @param num
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月7日 下午3:21:36           
	 */
	public RepaymentPlanBaseDTO getRepayment(int loanId,int num,int version){
		RepaymentPlanBaseDTO dto=new RepaymentPlanBaseDTO();
		dto.setLoanInfoId(loanId);
		dto.setPlanCycleNum(num);
		RepaymentPlanBaseDTO repayment=loanRepaymentPlanMidMapper.selectRepaymentByCycleNum(dto);
		if(repayment!=null){
			repayment.setGenDttm(DateUtil.format(new Date()));
			repayment.setPlanVersion(version);
			repayment.setPId(0);
			repayment.setPlanRepayDt(repayment.getPlanRepayDt());
			repayment.setFreezeStatus(0);
		}
		return repayment;
	}
	
	/**
	 * 
	  * @Description: 保存即时发生表信息
	  * @param loan 贷款信息
	  * @param list 生成的还款计划表
	  * @param repayDate （提前还款，挪用，违约，利率变更。。）日期
	  * @param repayAmt （提前还款，挪用，违约，利率变更。。）金额
	  * @param userId 用户Id
	  * @param operType 即时发生类型
	  * @param refId 引用Id
	  * @param refundType 退款类型（1=提前还款，2=利率变更）
	  * @param currMangCost 提前还款当前管理费
	  * @param currOtherCost 提前还款当前其他费用
	  * @param currInterest 提前还款当前利息
	  * @param currMoney 提前还款当前剩余本金
	  * @param isRefund 提前还款日期 是否等于 算息日期（1=否，0=是）
	  * @author: zhanghg
	  * @date: 2015年3月13日 下午3:48:47
	 */
	public void saveRealtimePlan(CalcOperDto operDto,Loan loan,List<RepaymentPlanBaseDTO> list,String repayDate,double repayAmt,int userId,int operType,int refId,int refundType,double currMangCost,double currOtherCost,double currInterest,double currMoney,int isRefund){
		
		//最大版本号
		int realtimePlanVersion=realtimePlanMapper.getMaxVersion(loan.getPid());
		realtimePlanVersion++;
		
		//即时发生表中除当前提前还款外其他数据（逾期等。。）
		List<RealtimePlan> plans=getRealtimePlans(loan,operType,refId,refundType,realtimePlanVersion,0);
		
		//判断是否退还利息
		if(getIsReturnInterest(loan.getPid())==1){
			//还款选项未一次性付清，退还至最后还款日期的利息
			if(loan.getRepayOption()==3 ||loan.getRepayOption()==4 ||isRefund==0){
				//即时发生表 退款信息
				//提前还款 -前置付息  不保存退款记录 由还款计划表处理
				if((operDto.getRepayType()==2 &&(loan.getRepayOption()==2 || loan.getRepayOption()==3)) || operDto.getRepayType() !=2){
					setRealtimePlan(plans,list,loan,operType,refId,refundType,realtimePlanVersion,
							currMangCost,currOtherCost,currInterest,currMoney,repayDate,repayAmt,userId);
				}
			}
		}
		
		switch (operDto.getRepayType()) {
		case 2:
			//提前还款罚金
			double fj=contrDouble(repayAmt*loan.getPrepayLiqDmgProportion()/100);
			//本金
			double pbje=currMoney-repayAmt;
			if(pbje<0){
				pbje=loan.getCreditAmt()-repayAmt;
			}
			plans.add(setRealtime(loan,repayDate,FinanceTypeEnum.TYPE_7.getType(), repayAmt, repayAmt, userId, repayAmt,pbje, realtimePlanVersion, refId,refundType,isReconciliation(repayAmt)));
			plans.add(setRealtime(loan,repayDate,FinanceTypeEnum.TYPE_8.getType(), repayAmt, fj, userId, fj,pbje, realtimePlanVersion, refId,refundType,isReconciliation(fj)));
			
			break;
		case 4:
			
			break;
		default:
			break;
		}
		if(plans!=null && plans.size()>0){
			realtimePlanMapper.insertRealtimePlans(plans);
		}
	}
	
	
	/**
	 * 
	  * @Description: 保存即时发生表信息
	  * @param operDto 即时发生的 DTO
	  * @param loan 贷款信息
	  * @param day 挪用天数 （如果是违约处理 则为1）
	  * @param userId 用户Id
	  * @param operType 即时发生类型
	  * @param refundType 退款类型 
	  * @author: zhanghg
	  * @date: 2015年3月25日 上午10:31:23
	 */
	public void saveRealtimePlan(CalcOperDto operDto,Loan loan,int day,int userId,int operType){
		
		//最大版本号
		int realtimePlanVersion=realtimePlanMapper.getMaxVersion(loan.getPid());
		realtimePlanVersion++;
		
		List<RealtimePlan> plans=Lists.newArrayList();
		
		plans=getRealtimePlans(loan,operType,operDto.getPid(),0,realtimePlanVersion,0);
		
		double money=operDto.getOperAmt()*day*operDto.getInterestRate()/100;
		
		plans.add(setRealtime(loan,operDto.getOperRepayDt(),operType, operDto.getOperAmt(), money, userId, money,0, realtimePlanVersion, operDto.getPid(),0,isReconciliation(money)));
		
		realtimePlanMapper.insertRealtimePlans(plans);
		
		//复制还款计划表最大版本有效数据
		copyLoanRepayData(loan.getPid());
		
	}
	
	//复制还款计划表最大版本有效数据（挪用，违约使用）
	private void copyLoanRepayData(int loanId){
		int maxVersion=loanRepaymentPlanMidMapper.getMaxVersionMid(loanId);
		maxVersion++;
		
		//根据展期项目ID  查询被展项目的还款计划表
		List<RepaymentPlanBaseDTO> list=loanRepaymentPlanMapper.getRepaymentMidsLoanId(loanId);
		if(list!=null && list.size()>0){
			for(RepaymentPlanBaseDTO dto :list){
				dto.setFreezeStatus(0);
				dto.setPlanVersion(maxVersion);
			}
			
			loanRepaymentPlanMidMapper.insertRepayments(list);

			// 还款计划表中间表 转换成还款计划表
			loanRepaymentPlanMapper.makeRepayments(loanId);
		}
	}
	
	public double contrDouble(double m){
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		return Double.parseDouble(df.format(m));
	}
	
	/**
	 * 
	  * @Description: 保存即时发生信息
	  * @param operDto
	  * @param loan
	  * @param list
	  * @param userId
	  * @param calDto
	  * @param outDt
	  * @author: zhanghg
	  * @date: 2015年3月26日 上午10:44:56
	 */
	public void saveRealtimePlan(CalcOperDto operDto,Loan loan,List<RepaymentPlanBaseDTO> list,int userId,Calendar outDt,CalcDto calDto,int isRefund){
		switch (operDto.getRepayType()) {
		case 2://提前还款
			logger.info("CalcHelper saveRealtimePlan 提前还款 保存即时信息");
			saveRealtimePlan(operDto,loan,list,operDto.getOperRepayDt(),operDto.getOperAmt(),userId,FinanceTypeEnum.TYPE_7.getType(),operDto.getPid(),1,calDto.getCurrMangCost(),calDto.getCurrOtherCost(),calDto.getCurrInterest(),calDto.getCurrMoney(),isRefund);
			break;
		case 3://利率变更
			logger.info("CalcHelper saveRealtimePlan 利率变更 保存即时信息");
			saveRealtimePlan(operDto,loan,list,operDto.getOperRepayDt(),operDto.getOperAmt(),userId,0,operDto.getPid(),2,calDto.getCurrMangCost(),calDto.getCurrOtherCost(),calDto.getCurrInterest(),calDto.getCurrMoney(),isRefund);
			break;
		case 4://挪用处理
			logger.info("CalcHelper saveRealtimePlan 挪用处理 保存即时信息");
			Calendar endDate=Calendar.getInstance();//挪用结束日期
			endDate.setTime(DateUtil.format(operDto.getEndDate(),"yyyy-MM-dd"));
			saveRealtimePlan(operDto,loan,calcNum(outDt,endDate),userId,FinanceTypeEnum.TYPE_1.getType());
			break;
		case 5://违约处理
			logger.info("CalcHelper saveRealtimePlan 违约处理 保存即时信息");
			saveRealtimePlan(operDto,loan,1,userId,FinanceTypeEnum.TYPE_2.getType());
			break;
		case 6://坏账处理      
			logger.info("CalcHelper saveRealtimePlan 坏账处理 保存即时信息");
			saveRealtimeDebt(loan,operDto,userId);
			break;
		case 7://展期处理    
			logger.info("CalcHelper saveRealtimePlan 展期处理 保存即时信息");
			saveRealtimeDebt(loan);
			break;	
		default:
			break;
		}
	}
	
	
	/**
	 * 
	  * @Description: 展期-保存原项目的还款计划表
	  * @param loan
	  * @author: zhanghg
	  * @date: 2015年5月16日 下午6:16:32
	 */
	private void saveRealtimeDebt(Loan loan){
		
		int maxVersion=loanRepaymentPlanMapper.getMaxVersionByExdProId(loan.getProjectId());
		maxVersion++;
		
		//根据展期项目ID  查询被展项目的还款计划表
		List<RepaymentPlanBaseDTO> list=loanRepaymentPlanMapper.getRepaymentsByExdProId(loan.getProjectId());
		int loanId=0;
		if(list!=null && list.size()>0){
			int cycNum=loanExtensionMapper.getCycNumByProjectId(loan.getProjectId());
			for(RepaymentPlanBaseDTO dto :list){
				dto.setFreezeStatus(0);
				dto.setPlanVersion(maxVersion);
				//原项目还款计划表 被展期数 本金减去展期项目贷款金额
				if(dto.getPlanCycleNum()==cycNum){
					dto.setShouldPrincipal(dto.getShouldPrincipal()-loan.getCreditAmt());
					dto.setTotal(dto.getTotal()-loan.getCreditAmt());
				}
			}
			loanId=list.get(0).getLoanInfoId();
			
			loanRepaymentPlanMidMapper.insertRepayments(list);

			// 还款计划表中间表 转换成还款计划表
			loanRepaymentPlanMapper.makeRepayments(loanId);
		}
	}
	
	
	/**+
	 * 
	  * @Description: 计算贷款信息 到 结束计息日期 应收金额
	  * @param loan
	  * @param operDto
	  * @author: zhanghg
	  * @date: 2015年3月31日 上午9:54:49
	 */
	public void saveRealtimeDebt(Loan loan,CalcOperDto operDto ,int userId){
		List<Loan> loans=loanMapper.getLoansByExtensionProId(loan.getProjectId());
		
		double principal=0;
		double mangCost=0;
		double otherCost=0;
		double interest=0;
		
		//损失金额
		double lossAmt=0;
		
		double divertAmt=getRealtimeNoReconAmt(loan.getPid(),FinanceTypeEnum.TYPE_1.getType());
		double violationAmt=getRealtimeNoReconAmt(loan.getPid(),FinanceTypeEnum.TYPE_2.getType());
		
		//循环当前项目包括展期 贷款信息
		for(Loan  lo: loans){
			RepaymentPlanBaseDTO rp=calDebtMoney(lo,operDto);//应收金额
			RepaymentPlanBaseDTO rp2=getReconciliationAmt(lo.getPid());//已收金额
			
			//判断是否为空
			if(rp !=null && rp2!=null){
				if(rp2.getShouldPrincipal() < rp.getShouldPrincipal()){
					principal+=(rp.getShouldPrincipal()- rp2.getShouldPrincipal());
				}
				if(rp2.getShouldMangCost() < rp.getShouldMangCost()){
					mangCost+=(rp.getShouldMangCost()- rp2.getShouldMangCost());
				}
				if(rp2.getShouldOtherCost() < rp.getShouldOtherCost()){
					otherCost+=(rp.getShouldOtherCost()- rp2.getShouldOtherCost());
				}
				if(rp2.getShouldInterest() < rp.getShouldInterest()){
					interest+=(rp.getShouldInterest()- rp2.getShouldInterest());
				}
				//累加损失金额
				lossAmt+=rp.getTotal();
			}
			
			//保存还款计划表、即时发生表数据 （对账状态改为坏账）
			saveRealtimeRepay(loan.getPid(),lo,operDto);
		}
		
		List<RealtimePlan> plans=Lists.newArrayList();
		//即时表最大版本号
		int realtimePlanVersion=realtimePlanMapper.getMaxVersion(loan.getPid());
		
		if(principal>0){//坏账 本金
			principal=contrDouble(principal);
			plans.add(setRealtime(loan,operDto.getOperRepayDt(),FinanceTypeEnum.TYPE_3.getType(), principal, principal, userId, principal,0, realtimePlanVersion, operDto.getPid(),3,2));
		}
		if(mangCost>0){//坏账管理费
			mangCost=contrDouble(mangCost);
			plans.add(setRealtime(loan,operDto.getOperRepayDt(),FinanceTypeEnum.TYPE_12.getType(), mangCost, mangCost, userId, mangCost,0, realtimePlanVersion, operDto.getPid(),3,2));
		}
		if(otherCost>0){//坏账其他费用
			otherCost=contrDouble(otherCost);
			plans.add(setRealtime(loan,operDto.getOperRepayDt(),FinanceTypeEnum.TYPE_13.getType(), otherCost, otherCost, userId, otherCost,0, realtimePlanVersion, operDto.getPid(),3,2));
		}
		if(interest>0){//坏账利息
			interest=contrDouble(interest);
			plans.add(setRealtime(loan,operDto.getOperRepayDt(),FinanceTypeEnum.TYPE_14.getType(), interest, interest, userId, interest,0, realtimePlanVersion, operDto.getPid(),3,2));
		}
		if(divertAmt>0){//坏账挪用罚息
			divertAmt=contrDouble(divertAmt);
			plans.add(setRealtime(loan,operDto.getOperRepayDt(),FinanceTypeEnum.TYPE_1.getType(), divertAmt, divertAmt, userId, divertAmt,0, realtimePlanVersion, operDto.getPid(),3,2));
		}
		if(violationAmt>0){//坏账违约处理
			violationAmt=contrDouble(violationAmt);
			plans.add(setRealtime(loan,operDto.getOperRepayDt(),FinanceTypeEnum.TYPE_2.getType(), violationAmt, violationAmt, userId, violationAmt,0, realtimePlanVersion, operDto.getPid(),3,2));
		}
		
		//保存损失金额
		lossAmt=contrDouble(lossAmt);
		plans.add(setRealtime(loan,operDto.getOperRepayDt(),FinanceTypeEnum.TYPE_11.getType(), lossAmt, lossAmt, userId, lossAmt,0, realtimePlanVersion, operDto.getPid(),3,1));
		
		//保存坏账后应收金额
		if(plans!=null && plans.size()>0){
			realtimePlanMapper.insertRealtimePlans(plans);
		}
	}
	
	
	public void saveRealtimeRepay(int loanId ,Loan lo,CalcOperDto operDto){
		//即时表最大版本号
		int realtimePlanVersion=realtimePlanMapper.getMaxVersion(lo.getPid())+1;
		
		//获取还款计划表最大版本号
		int repayPlanVersion=loanRepaymentPlanMapper.getMaxVersion(lo.getPid())+1;
		
		//退款类型
		int refundType=3;
		if(loanId !=lo.getPid()){
			refundType=-1;
		}
		//获取即时发生的数据
		List<RealtimePlan> plans=getRealtimePlans(lo,0,operDto.getPid(),refundType,realtimePlanVersion,4);
		
		if(plans!=null && plans.size()>0){
			realtimePlanMapper.insertRealtimePlans(plans);
		}
		
		//获取还款计划表数据
		List<RepaymentPlanBaseDTO> repays=loanRepaymentPlanMapper.getRepayments(lo.getPid());
		for(RepaymentPlanBaseDTO rp : repays){
			rp.setPlanVersion(repayPlanVersion);
			rp.setFreezeStatus(0);
			if(rp.getIsReconciliation() !=1){
				rp.setIsReconciliation(4);
			}
		}
		
		if(repays!=null && repays.size()>0){
			loanRepaymentPlanMapper.insertRepayments(repays);
		}
	}
	
	/**
	 * 
	  * @Description: 坏账-查询已对账金额
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月31日 下午4:32:46
	 */
	public RepaymentPlanBaseDTO getReconciliationAmt(int loanId){
		Map<String, Object> map=Maps.newHashMap();
		map.put("loanId", loanId);
		map.put("loanRepayDto",OracleTypes.CURSOR);
		loanRepaymentPlanMapper.getRepayReconciliationAmt(map);
		List<RepaymentPlanBaseDTO> rps=(List<RepaymentPlanBaseDTO>)map.get("loanRepayDto");
		if(rps!=null && rps.size()>0){
			return rps.get(0);
		}
		return new RepaymentPlanBaseDTO();
		
	}
	
	/**
	 * 
	 * @Description: 坏账-查询及时发生表未对账金额
	 * @param loanId 贷款ID
	 * @param operType 即时发生状态
	 * @return
	 * @author: zhanghg
	 * @date: 2015年3月31日 下午4:32:46
	 */
	public Double getRealtimeNoReconAmt(int loanId,int operType){
		Map<String, Object> map=Maps.newHashMap();
		map.put("loanId", loanId);
		map.put("noReconAmt",OracleTypes.DOUBLE);
		map.put("operType", operType);
		realtimePlanMapper.getRealtimeNoReconAmt(map);
		Double amt=(Double)map.get("noReconAmt");
		
		return amt;
		
	}
	
	/**
	 * 
	  * @Description: 坏账-计算贷款信息  放款日期-截止计息日期 的 应收金额
	  * @param loan
	  * @param operDto
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月31日 上午11:40:31
	 */
	public RepaymentPlanBaseDTO calDebtMoney(Loan loan,CalcOperDto operDto){
		RepaymentPlanBaseDTO rpd=new RepaymentPlanBaseDTO();
		
		String lookupVal=sysLookupValMapper.getSysLookupValByPid(loan.getRepayFun());
		int repayFun=1;
		if(lookupVal!=null && !"".equals(lookupVal)){
			repayFun=Integer.parseInt(lookupVal);
		}
		
		//获取还款计划表数据
		List<RepaymentPlanBaseDTO> list=loanRepaymentPlanMapper.getRepayments(loan.getPid());
		
		Calendar outDt=Calendar.getInstance();
		outDt.setTime(DateUtil.format(loan.getPlanOutLoanDt(),"yyyy-MM-dd"));
		
		//结束计息日期
		Calendar endDate=Calendar.getInstance();
		endDate.setTime(DateUtil.format(operDto.getEndDate(),"yyyy-MM-dd"));
		
		double total=0;
		//计算 放款日期 至 结束计息日期的期数
		int num=CalCycleNum(outDt,endDate);
		
		for(int i=0;i<list.size();i++){
			
			RepaymentPlanBaseDTO  rp=list.get(i);
				
				if(i<(num-1)){ //判断最大期数之前的数据（利息）累加
					setRepayPlan(rpd,rp);
				}else if(i==(num-1)){ //最大期数 的数据（利息） 计算并累加
					
					
					if(endDate.getTime()==getCalcInterestDt(outDt, num).getTime() || ( repayFun!=1 && repayFun!=6)){
						setRepayPlan(rpd,rp);
					}else{
						//计算不足月的利息
						if(loan.getRepayOption()==1 ||loan.getRepayOption()==2){
							int day=calcNum((getCalcInterestDt(outDt,num-1)),endDate);
							
							double sprincipal=loan.getRepayOption()==1?rp.getShouldPrincipal():0;
							
							rpd.setShouldMangCost(rpd.getShouldMangCost()+calcMoney((rp.getPrincipalBalance()+sprincipal),loan.getMonthLoanMgr(),day));
							rpd.setShouldOtherCost(rpd.getShouldOtherCost()+calcMoney((rp.getPrincipalBalance()+sprincipal),loan.getMonthLoanOtherFee(),day));
							rpd.setShouldInterest(rpd.getShouldInterest()+calcMoney((rp.getPrincipalBalance()+sprincipal),loan.getMonthLoanInterest(),day));
						}
						
					}
				}
				//前置付息时 本金累加
				rpd.setShouldPrincipal(rpd.getShouldPrincipal()+rp.getShouldPrincipal());
				
				//累加所有 管理费+其他费用+贷款利息
				total+=rp.getShouldMangCost()+rp.getShouldOtherCost()+rp.getShouldInterest();
		}
		rpd.setTotal(total-(rpd.getShouldMangCost()+rpd.getShouldOtherCost()+rpd.getShouldInterest()));
		return rpd;
	}
	
	/**
	 * 
	  * @Description: 坏账-计算利息金额
	  * @param amt
	  * @param rate
	  * @param day
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月31日 上午11:39:25
	 */
	public double calcMoney(double amt,double rate,int day){
		return amt*rate/dayOfM/100*day;
	}
	
	/**
	 * 
	  * @Description: 坏账-设置还款利息
	  * @param rpd
	  * @param rp
	  * @author: zhanghg
	  * @date: 2015年3月31日 上午11:38:31
	 */
	public void setRepayPlan(RepaymentPlanBaseDTO rpd,RepaymentPlanBaseDTO rp){
		rpd.setShouldMangCost(rpd.getShouldMangCost()+rp.getShouldMangCost());
		rpd.setShouldOtherCost(rpd.getShouldOtherCost()+rp.getShouldOtherCost());
		rpd.setShouldInterest(rpd.getShouldInterest()+rp.getShouldInterest());
	}
	
	/**
	 * 
	  * @Description: 坏账-计算截止日期 的期数
	  * @param outDt
	  * @param endDt
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月30日 下午4:46:35
	 */
	public int CalCycleNum(Calendar outDt,Calendar endDt){
		Calendar currDt=Calendar.getInstance();
		currDt.setTime(outDt.getTime());
		int i=1;
		while(currDt.getTime().getTime()<endDt.getTime().getTime()){
			currDt.setTime(getCalcInterestDt(outDt, i).getTime());
			i++;
		}
		
		return i-1;
	}
	
	/**
	 * 
	  * @Description: 坏账-计算收息日期
	  * @param cal
	  * @param month
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月31日 上午11:39:56
	 */
	public Calendar getCalcInterestDt(Calendar cal,int month){
		Calendar c=Calendar.getInstance();
		c.setTime(cal.getTime());
		c.add(Calendar.MONTH, month);
		if(c.get(Calendar.DAY_OF_MONTH)==cal.get(Calendar.DAY_OF_MONTH)){
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		return c;
	}
	
	
	/**
	 * 
	  * @Description: 计算时间差的天数
	  * @param minDt
	  * @param maxDt
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月9日 下午2:53:02
	 */
	public int calcNum(Calendar minDt, Calendar maxDt) {
		
		//计算时间差的天数
		long l = maxDt.getTimeInMillis() - minDt.getTimeInMillis();
		int d = new Long(l / (1000 * 60 * 60 * 24)).intValue();
		return d;
	}
	
	/**
	 * 
	  * @Description: 获取是否退还利息
	  * @param loanId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年3月27日 下午4:03:58
	 */
	public int getIsReturnInterest(int loanId){
		return loanRepaymentPlanMapper.getIsReturnInterest(loanId);
	}
}
