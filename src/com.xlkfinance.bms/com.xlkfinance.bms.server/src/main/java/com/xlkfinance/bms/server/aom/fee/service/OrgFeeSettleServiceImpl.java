package com.xlkfinance.bms.server.aom.fee.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettle;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDTO;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetail;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettlePage;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleService.Iface;
import com.qfang.xk.aom.rpc.fee.PartnerFeeSettlePage;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.system.CapitalOrgInfoService;
import com.xlkfinance.bms.server.aom.fee.mapper.OrgFeeSettleDetailMapper;
import com.xlkfinance.bms.server.aom.fee.mapper.OrgFeeSettleMapper;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.system.mapper.CapitalOrgInfoMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-08-12 15:15:31 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 费用结算表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgFeeSettleServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.fee.OrgFeeSettleService")
public class OrgFeeSettleServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(OrgFeeSettleServiceImpl.class);

   @Resource(name = "orgFeeSettleMapper")
   private OrgFeeSettleMapper orgFeeSettleMapper;

   @Resource(name = "orgFeeSettleDetailMapper")
   private OrgFeeSettleDetailMapper orgFeeSettleDetailMapper;
   
   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;
   
	@Resource(name = "capitalOrgInfoServiceImpl")
	private CapitalOrgInfoService.Iface capitalOrgInfoServiceImpl;
   
	@Resource(name = "capitalOrgInfoMapper")
	private CapitalOrgInfoMapper capitalOrgInfoMapper;
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   @Override
   public List<OrgFeeSettle> getAll(OrgFeeSettle orgFeeSettle) throws ThriftServiceException, TException {
      return orgFeeSettleMapper.getAll(orgFeeSettle);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   @Override
   public OrgFeeSettle getById(int pid) throws ThriftServiceException, TException {
      OrgFeeSettle orgFeeSettle = orgFeeSettleMapper.getById(pid);
      if (orgFeeSettle==null) {
         return new OrgFeeSettle();
      }
      return orgFeeSettle;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   @Override
   public int insert(OrgFeeSettle orgFeeSettle) throws ThriftServiceException, TException {
      return orgFeeSettleMapper.insert(orgFeeSettle);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   @Override
   public int update(OrgFeeSettle orgFeeSettle) throws ThriftServiceException, TException {
      return orgFeeSettleMapper.update(orgFeeSettle);
   }

   /**
    * 初始化上个月的费用结算数据
    */
   @Transactional
   @Override
   public int initFeeSettle() throws TException {
	   int result = 0;
	   try {
		   //获取上个月的第一天和最后一天
		   String loanDateStart = DateUtils.getLastMonthFirstDay(1);
		   String loanDateEnd = DateUtils.getLastMonthEndDay(1);
		   //以机构为维度统计上个月放款的总金额
		   OrgFeeSettleDTO query = new OrgFeeSettleDTO();
		   query.setLoanDateStart(loanDateStart);
		   query.setLoanDateEnd(loanDateEnd);
		   List<OrgFeeSettle> orgFeeList = orgFeeSettleMapper.getLoanTotalMoney(query);
		   //查询当月放款信息列表数据
		   if(orgFeeList != null && orgFeeList.size()>0){
			   for(OrgFeeSettle orgFee : orgFeeList){
				   orgFee.setSettleDate(loanDateStart);
				   double loanMoneyTotal = orgFee.getLoanMoneyTotal();
				   //机构当月放款金额小于机构业务放款规模的，收费费率不变,实际收费费率为预收费费率
				   if(loanMoneyTotal < orgFee.getFundSizeMoney()){
					   orgFee.setActualFeeRate(orgFee.getExtimateFeeRate());
				   }
				   int partnerId = orgFee.getPartnerId();
				   double returnCommRate = 0.00;
				   if(partnerId >0){
					   query.setPartnerId(partnerId);
					   OrgFeeSettle orgFeeSettless = orgFeeSettleMapper.getRecMoneyByPartner(query);
					   if(orgFeeSettless != null){
						   returnCommRate = getReturnCommRate(orgFeeSettless.getLoanMoneyTotal());
						   orgFee.setReturnCommRate(returnCommRate);
					   }
				   }
				 //查询该机构结算日是否存在数据
				   int orgId = orgFee.getOrgId();
				   OrgFeeSettle orgFeeSettle = new OrgFeeSettle();
				   orgFeeSettle.setOrgId(orgId);
				   orgFeeSettle.setSettleDate(loanDateStart);
				   orgFeeSettle.setPartnerId(partnerId);
				   
				   List<OrgFeeSettle> feeList = orgFeeSettleMapper.getAll(orgFeeSettle);
				   //如果存在结算数据，做更新操作
				   if(feeList != null && feeList.size()>0){
					   orgFee.setPid(feeList.get(0).getPid());
					   result +=orgFeeSettleMapper.update(orgFee);
				   }else{
					   result +=orgFeeSettleMapper.insert(orgFee); 
				   }

				   int settleId = orgFee.getPid();
				   //初始化费用详情信息，并入库
				   if(settleId>0){
					   query.setOrgId(orgFee.getOrgId());
					   List<OrgFeeSettleDetail> orgFeeDetailList = orgFeeSettleDetailMapper.getProjectListByOrgId(query);
					   List<OrgFeeSettleDetail> feeDetailList = new ArrayList<OrgFeeSettleDetail>();
					   if(orgFeeDetailList != null && orgFeeDetailList.size()>0){
						   for(OrgFeeSettleDetail feeDetail : orgFeeDetailList){
							   int projectId = feeDetail.getProjectId();
							   feeDetail.setSettleId(settleId);
							   feeDetail.setActualFeeRate(orgFee.getActualFeeRate());
							   feeDetail.setExtimateFeeRate(orgFee.getExtimateFeeRate());
							   feeDetail.setReturnCommRate(returnCommRate);
							   feeDetailList.add(feeDetail);
								 //查询是否存在此订单的结算数据，如果存在做更新，不存在做新增
							   OrgFeeSettleDetail oldDetail = orgFeeSettleDetailMapper.getByProjectId(projectId);
							   if(oldDetail != null && oldDetail.getPid()>0){
								   feeDetail.setPid(oldDetail.getPid());
								   result +=orgFeeSettleDetailMapper.update(feeDetail);
							   }else{
								   result +=orgFeeSettleDetailMapper.insert(feeDetail);
							   }
						   }
						   //result +=orgFeeSettleDetailMapper.insertOrgFeeSettleDetail(feeDetailList);
					   }
				   }
			   }
		   }
		   OrgFeeSettleDTO dto = new OrgFeeSettleDTO();
		   dto.setSettleDate(loanDateStart);
		   dto.setSettleDateEnd(loanDateEnd);
		   //查询一段时间内已回款的项目列表
		   List<OrgFeeSettleDetail> cancelProjectList = orgFeeSettleDetailMapper.getCancelProjectList(dto);
		   if(cancelProjectList != null && cancelProjectList.size()>0){
			   for(OrgFeeSettleDetail detail : cancelProjectList){
				   int projectId = detail.getProjectId();
				   OrgFeeSettleDetail oldDetail = orgFeeSettleDetailMapper.getByProjectId(projectId);
				   if(oldDetail != null){
					   oldDetail.setPaymentMoney(detail.getPaymentMoney());
					   oldDetail.setPaymentDate(detail.getPaymentDate());
					   oldDetail.setSolutionDate(detail.getSolutionDate());
					   oldDetail.setActualLoanDays(detail.getActualLoanDays());
					   oldDetail.setReturnCommRate(detail.getReturnCommRate());
					   oldDetail.setOverDueFee(detail.getOverDueFee());
					   oldDetail.setPlanLoanDays(detail.getPlanLoanDays());
					   //计算应收金额，应退金额，合伙人返佣金额
					   operationFeeDetail(oldDetail);
					   //更新详情表数据
					   orgFeeSettleDetailMapper.update(oldDetail);
				   }
			   }
		   }
		   
		} catch (Exception e) {
			logger.error("初始化上个月的费用结算数据:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("初始化上个月的费用结算数据失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
	   
      return result;
   }

   /**
    * //根据放款总格确定返佣率，小放款量累计小于等于2亿时按5%，放款量累计大于2亿时按8%
     * @param loanMoneyTotal
     * @return
     * @author: baogang
     * @date: 2016年8月15日 下午7:22:04
    */
   private double getReturnCommRate(double loanMoneyTotal){
	   double returnCommRate =0.00;
	   if(loanMoneyTotal <= AomConstant.LOAN_MONEY_TWO*10){
		   returnCommRate= 0.05;
	   }else if(loanMoneyTotal > AomConstant.LOAN_MONEY_TWO*10){
		   returnCommRate=0.08;
	   }
	   return returnCommRate;
   }
   
   /**
    * 应收合作机构咨询费＝借款金额*协定利率/30*实际用款天数+展(逾)期费用
    * 当实际借款天数小于等于预计借款天数时，应收费为：借款金额*协定利率/30*实际用款天数，
    * 当实际借款天数大于预计借款天数时，应收费：借款金额*协定利率/30*预计用款天数+展(逾)期费用
    * 合伙人提成金额＝实际收费*提成比例（5%-8*%） 实际收费＝预收费+（展）逾期费用-退费-资金成本（付平台费用或自有资金成本）
     * @param detail
     * @author: baogang
     * @date: 2016年8月16日 下午5:39:28
    */
   private void operationFeeDetail(OrgFeeSettleDetail detail){
	   try {
		   double loanMoney= detail.getLoanMoney();//借款金额
		   int planLoanDays = detail.getPlanLoanDays();//预计借款天数
		   int actualLoanDays = detail.getActualLoanDays();//实际借款天数
		   double actualFeeRate = detail.getActualFeeRate()/100;//应收费费率
		   double returnCommRate = detail.getReturnCommRate();
		   double charge = detail.getCharge();//预收费金额
		   double overDueFee = detail.getOverDueFee();//逾期费
		   double realChargeMoney = 0.00;
		   if(actualLoanDays <= planLoanDays){
			   realChargeMoney = loanMoney*actualFeeRate*actualLoanDays/30;//应收费金额
		   }else{
			   realChargeMoney = loanMoney*actualFeeRate*planLoanDays/30 + overDueFee;//应收费金额
		   }
		   
		   
		   int projectId = detail.getProjectId();
		   //放款详情
		   ApplyFinanceHandleDTO applyFinanceHandleDTO = financeHandleMapper.getByProjectIdAndRecPro(projectId, 3);
		   //款项来源1以及金额
		   String resource = applyFinanceHandleDTO.getResource();
		   double resourceMoney = applyFinanceHandleDTO.getResourceMoney();
		   //款项来源2以及金额
		   String resourceOther = applyFinanceHandleDTO.getResource2();
		   double resourceMoneyOther = applyFinanceHandleDTO.getResourceMoney2();
		   double recMoney = applyFinanceHandleDTO.getRecMoney();
		   double capitionCost = 0.00;
		   //款项来源1计算资金成本
		   if(resourceMoney >0){
			   int orgId = capitalOrgInfoMapper.getByOrgCode(resource).getPid();
			   double yearRate = capitalOrgInfoServiceImpl.getYearRate(orgId);
			   capitionCost = resourceMoney*yearRate*actualLoanDays/360;
		   }
		   //款项来源2资金成本
		   if(resourceMoneyOther >0){
			   int orgId = capitalOrgInfoMapper.getByOrgCode(resourceOther).getPid();
			   double yearRate = capitalOrgInfoServiceImpl.getYearRate(orgId);
			   capitionCost += resourceMoneyOther*yearRate*actualLoanDays/360;
		   }
		   //老数据款项来源金额都为空
		   if(resourceMoneyOther <=0 && resourceMoney <=0){
			   int orgId = capitalOrgInfoMapper.getByOrgCode(resource).getPid();
			   double yearRate = capitalOrgInfoServiceImpl.getYearRate(orgId);
			   capitionCost += recMoney*yearRate*actualLoanDays/360;
		   }
		   
		   double refund = charge - realChargeMoney;//应退金额
		   detail.setRefund(refund);
		   detail.setRealChargeMoney(realChargeMoney);
		   detail.setReturnComm((realChargeMoney-capitionCost)*returnCommRate);
		} catch (Exception e) {
			logger.error("计算应收以及返佣出错:" + ExceptionUtil.getExceptionMessage(e));
		}
   }
 
   /**
    * 机构息费列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月15日下午3:11:28
    */
   @Override
   public List<OrgFeeSettlePage> queryOrgFeeSettlePage(OrgFeeSettlePage query) throws TException {
      return orgFeeSettleMapper.queryOrgFeeSettlePage(query);
   }

   /**
    *@author:liangyanjun
    *@time:2016年8月15日下午3:11:28
    */
   @Override
   public int getOrgFeeSettlePageTotal(OrgFeeSettlePage query) throws TException {
      return orgFeeSettleMapper.getOrgFeeSettlePageTotal(query);
   }

   /**
    * 合伙人息费列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月16日下午3:08:05
    */
   @Override
   public List<PartnerFeeSettlePage> queryPartnerFeeSettlePage(PartnerFeeSettlePage query) throws TException {
      return orgFeeSettleMapper.queryPartnerFeeSettlePage(query);
   }

   /**
    *@author:liangyanjun
    *@time:2016年8月16日下午3:08:05
    */
   @Override
   public int getPartnerFeeSettlePageTotal(PartnerFeeSettlePage query) throws TException {
      return orgFeeSettleMapper.getPartnerFeeSettlePageTotal(query);
   }

	@Override
	public List<OrgFeeSettleDTO> queryOrgFeeSettleByPage(OrgFeeSettleDTO query)
			throws TException {
		
		return orgFeeSettleMapper.queryOrgFeeSettleByPage(query);
	}
	
	@Override
	public int getOrgFeeSettleCount(OrgFeeSettleDTO query) throws TException {
		return orgFeeSettleMapper.getOrgFeeSettleCount(query);
	}
	
	/**
	 * 根据传入的参数计算前几个月的息费结算数据
	 */
	@Transactional
	@Override
	public int createLastMonthFeeSettle(int number) throws TException {
		int result = 0;
		   try {
			   //获取上个月的第一天和最后一天
			   String loanDateStart = DateUtils.getLastMonthFirstDay(number);
			   String loanDateEnd = DateUtils.getLastMonthEndDay(number);
			   //以机构为维度统计上个月放款的总金额
			   OrgFeeSettleDTO query = new OrgFeeSettleDTO();
			   query.setLoanDateStart(loanDateStart);
			   query.setLoanDateEnd(loanDateEnd);
			   List<OrgFeeSettle> orgFeeList = orgFeeSettleMapper.getLoanTotalMoney(query);
			   //查询当月放款信息列表数据
			   if(orgFeeList != null && orgFeeList.size()>0){
				   for(OrgFeeSettle orgFee : orgFeeList){
					   orgFee.setSettleDate(loanDateStart);
					   double loanMoneyTotal = orgFee.getLoanMoneyTotal();
					   int rebateType = AomConstant.REBATE_TYPE_1;
					   //机构当月放款金额小于机构业务放款规模的，收费费率不变,实际收费费率为预收费费率
					   if(loanMoneyTotal < orgFee.getFundSizeMoney()){
						   orgFee.setActualFeeRate(orgFee.getExtimateFeeRate());
					   }
					   orgFee.setRebateType(rebateType);
					   int partnerId = orgFee.getPartnerId();
					   double returnCommRate = 0.00;
					   if(partnerId >0){
						   query.setPartnerId(partnerId);
						   OrgFeeSettle orgFeeSettless = orgFeeSettleMapper.getRecMoneyByPartner(query);
						   if(orgFeeSettless != null){
							   returnCommRate = getReturnCommRate(orgFeeSettless.getLoanMoneyTotal());
							   orgFee.setReturnCommRate(returnCommRate);
						   }
					   }
					   //查询该机构结算日是否存在数据
					   int orgId = orgFee.getOrgId();
					   OrgFeeSettle orgFeeSettle = new OrgFeeSettle();
					   orgFeeSettle.setOrgId(orgId);
					   orgFeeSettle.setSettleDate(loanDateStart);
					   orgFeeSettle.setPartnerId(partnerId);
					   
					   List<OrgFeeSettle> feeList = orgFeeSettleMapper.getAll(orgFeeSettle);
					   //如果存在结算数据，做更新操作
					   if(feeList != null && feeList.size()>0){
						   orgFee.setPid(feeList.get(0).getPid());
						   result +=orgFeeSettleMapper.update(orgFee);
					   }else{
						   result +=orgFeeSettleMapper.insert(orgFee); 
					   }
					   
					   int settleId = orgFee.getPid();
					   //初始化费用详情信息，并入库
					   if(settleId>0){
						   query.setOrgId(orgId);
						   List<OrgFeeSettleDetail> orgFeeDetailList = orgFeeSettleDetailMapper.getProjectListByOrgId(query);
						   if(orgFeeDetailList != null && orgFeeDetailList.size()>0){
							   for(OrgFeeSettleDetail feeDetail : orgFeeDetailList){
								   int projectId = feeDetail.getProjectId();
								   feeDetail.setSettleId(settleId);
								   feeDetail.setActualFeeRate(orgFee.getActualFeeRate());
								   feeDetail.setExtimateFeeRate(orgFee.getExtimateFeeRate());
								   feeDetail.setReturnCommRate(returnCommRate);
								   //查询是否存在此订单的结算数据，如果存在做更新，不存在做新增
								   OrgFeeSettleDetail oldDetail = orgFeeSettleDetailMapper.getByProjectId(projectId);
								   if(oldDetail != null && oldDetail.getPid()>0){
									   feeDetail.setPid(oldDetail.getPid());
									   result +=orgFeeSettleDetailMapper.update(feeDetail);
								   }else{
									   result +=orgFeeSettleDetailMapper.insert(feeDetail);
								   }
							   }
						   }
					   }
				   }
			   }
			   OrgFeeSettleDTO dto = new OrgFeeSettleDTO();
			   dto.setSettleDate(loanDateStart);
			   dto.setSettleDateEnd(loanDateEnd);
			   //查询一段时间内已解保的项目列表
			   List<OrgFeeSettleDetail> cancelProjectList = orgFeeSettleDetailMapper.getCancelProjectList(dto);
			   if(cancelProjectList != null && cancelProjectList.size()>0){
				   for(OrgFeeSettleDetail detail : cancelProjectList){
					   int projectId = detail.getProjectId();
					   OrgFeeSettleDetail oldDetail = orgFeeSettleDetailMapper.getByProjectId(projectId);
					   if(oldDetail != null){
						   oldDetail.setPaymentMoney(detail.getPaymentMoney());
						   oldDetail.setPaymentDate(detail.getPaymentDate());
						   oldDetail.setSolutionDate(detail.getSolutionDate());
						   oldDetail.setActualLoanDays(detail.getActualLoanDays());
						   oldDetail.setReturnCommRate(detail.getReturnCommRate());
						   oldDetail.setOverDueFee(detail.getOverDueFee());
						   oldDetail.setPlanLoanDays(detail.getPlanLoanDays());
						   //计算返点金额、返佣金额
						   operationFeeDetail(oldDetail);
						   //更新详情表数据
						   result +=orgFeeSettleDetailMapper.update(oldDetail);
					   }
				   }
			   }
			} catch (Exception e) {
				logger.error("初始化上几个月的费用结算数据:" + ExceptionUtil.getExceptionMessage(e));
				throw new RuntimeException("初始化上个月的费用结算数据失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
			}
		   
	      return result;
	}

}
