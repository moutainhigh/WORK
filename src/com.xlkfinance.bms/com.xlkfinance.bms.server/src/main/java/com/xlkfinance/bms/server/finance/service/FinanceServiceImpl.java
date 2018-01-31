/**
 * @Title: FinanceAcctManagerServiceImpl.java
 * @Package com.xlkfinance.bms.server.finance.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月26日 下午3:34:03
 * @version V1.0
 */
package com.xlkfinance.bms.server.finance.service;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.thrift.TException;
import org.odftoolkit.odfdom.converter.core.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.beforeloan.CreditLimitRecord;
import com.xlkfinance.bms.rpc.beforeloan.CreditsService;
import com.xlkfinance.bms.rpc.beforeloan.LoanOutputInfoImplDTO;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.finance.AcctProjectBalanceDTO;
import com.xlkfinance.bms.rpc.finance.AcctProjectBalanceView;
import com.xlkfinance.bms.rpc.finance.BadDebtDataBean;
import com.xlkfinance.bms.rpc.finance.BatchRepaymentBean;
import com.xlkfinance.bms.rpc.finance.BatchRepaymentItem;
import com.xlkfinance.bms.rpc.finance.BatchRepaymentView;
import com.xlkfinance.bms.rpc.finance.CostReduction;
import com.xlkfinance.bms.rpc.finance.CustArrearsView;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalCondition;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalDetailView;
import com.xlkfinance.bms.rpc.finance.FinanceAcctTotalView;
import com.xlkfinance.bms.rpc.finance.FinanceBank;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessCondition;
import com.xlkfinance.bms.rpc.finance.FinanceBusinessView;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesDTO;
import com.xlkfinance.bms.rpc.finance.FinanceReceivablesView;
import com.xlkfinance.bms.rpc.finance.FinanceService.Iface;
import com.xlkfinance.bms.rpc.finance.FinanceTransactionDTO;
import com.xlkfinance.bms.rpc.finance.LoanBankAccountBean;
import com.xlkfinance.bms.rpc.finance.LoanBaseDataBean;
import com.xlkfinance.bms.rpc.finance.LoanCycleNumView;
import com.xlkfinance.bms.rpc.finance.LoanFeew;
import com.xlkfinance.bms.rpc.finance.LoanReconciliationDtlView;
import com.xlkfinance.bms.rpc.finance.LoanRefundDTO;
import com.xlkfinance.bms.rpc.finance.LoanRefundView;
import com.xlkfinance.bms.rpc.finance.MonthlyReportBasePlan;
import com.xlkfinance.bms.rpc.finance.MonthlyReportBasePlanIm;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecord;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecordCalculateDetail;
import com.xlkfinance.bms.rpc.finance.MonthlyReportRecordCondition;
import com.xlkfinance.bms.rpc.finance.OverdueDataCondition;
import com.xlkfinance.bms.rpc.finance.ProjectTotalDetailView;
import com.xlkfinance.bms.rpc.finance.QueryOverdueReceivablesBean;
import com.xlkfinance.bms.rpc.finance.ReconciliationItem;
import com.xlkfinance.bms.rpc.finance.ReconciliationOptionsView;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationBean;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationDTO;
import com.xlkfinance.bms.rpc.finance.RepaymentReconciliationDetailDTO;
import com.xlkfinance.bms.rpc.finance.UnReconciliationCondition;
import com.xlkfinance.bms.rpc.finance.UnReconciliationView;
import com.xlkfinance.bms.rpc.repayment.RepayFeewdtlService;
import com.xlkfinance.bms.server.beforeloan.mapper.LoanOutputInfoMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.finance.mapper.FinanceBankMapper;
import com.xlkfinance.bms.server.finance.mapper.LoanRefundMapper;
import com.xlkfinance.bms.server.finance.util.FinanceConstant;
import com.xlkfinance.bms.server.finance.util.FinanceTypeEnum;
import com.xlkfinance.bms.server.finance.util.FinanceUtil;
/**
 * 类描述<br>
 * 财务账户管理实现类
 * 
 * @author wangheng
 * @version v1.0
 */
@SuppressWarnings({"unchecked","rawtypes"})
@Service("financeServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.finance.FinanceService")
public class FinanceServiceImpl implements Iface{
	private Logger logger = LoggerFactory.getLogger(FinanceServiceImpl.class);

	@Resource(name = "financeBankMapper")
	private FinanceBankMapper financeBankMapper;
	
	@Resource(name = "projectMapper")
	private ProjectMapper projectMapper;

	@Resource(name = "loanRefundMapper")
	private LoanRefundMapper LoanRefundMapper;
	
	@Resource(name = "loanOutputInfoMapper")
	private LoanOutputInfoMapper LoanOutputInfoMapper;
	
	@Resource(name = "repayFeewdtlService")
	private RepayFeewdtlService.Iface repayFeewdtlService;
	
	@Resource(name = "creditsServiceImpl")
	private CreditsService.Iface creditsService;
	
	@Override
	public int addFinanceAcctManager(FinanceBank financeBank)
			throws ThriftServiceException, TException {
		financeBank.setStatus(1);
		int result= financeBankMapper.insert(financeBank);
		return result;
	}

	@Override
	public List<FinanceBank> getFinanceActtManager(FinanceBank financeBank)
			throws TException {
		List<FinanceBank> list= financeBankMapper.getFinanceAcctManager(financeBank);
		return list!=null?list:new ArrayList<FinanceBank>();
	}

	@Override
	public FinanceBank getFinanceActtManagerById(int pid) throws TException {
		FinanceBank financeBank= financeBankMapper.getFinanceAcctManagerById(pid);
		return financeBank!=null?financeBank:new FinanceBank();
	}

	
	@Override
	public int updateFinanceAcctManager(FinanceBank financeBank)
			throws TException {
		int result= financeBankMapper.updateFinanceAcctManager(financeBank);
		return result;
	}

	@Override
	public int deleteFinanceAcctManager(String pid)
			throws ThriftServiceException, TException {
		List<String> list = new ArrayList<String>();
		String[] s = pid.split(",");
		for (String string : s) {
			list.add(string);
		}
		return financeBankMapper.deleteFinanceAcctManager(list);
	}
	//add by yql
	/**
	 * 财务-查询客户业务信息
	 */
	@Override
	public List<FinanceBusinessView> getCusBusiness(
			FinanceBusinessCondition financeBusinessCondition)
			throws ThriftServiceException, TException {
		 try {
			 
			 List<FinanceBusinessView> financeBusinessList=financeBankMapper.getFinanceCusBusiness(financeBusinessCondition);
			 List<FinanceBusinessView> financeBusinessResultList= new ArrayList<FinanceBusinessView>();
			 if(financeBusinessList!=null){
				 for(FinanceBusinessView financeBusinessView:financeBusinessList){
					// 金额的计算
					 	FinanceBusinessView fView = null;
						Map<String, Object> param = new HashMap<String, Object>();
				        param.put("results",OracleTypes.CURSOR);
				        param.put("projectId",financeBusinessView.getPid());		
				        param.put("currentDate", DateUtil.fmtDateToStr(new Date(), "yyyy-MM-dd") );
					        
						financeBankMapper.getProGetPlanProject(param);
						List<FinanceBusinessView> resultList = (List<FinanceBusinessView>) param.get("results");
						fView= resultList.get(0);
						financeBusinessView.setDueUnreceived(fView.getDueUnreceived());
						financeBusinessView.setUnDue(fView.getUnDue());
						financeBusinessView.setReconciliationAmt(fView.getReconciliationAmt());
						financeBusinessView.setUnReconciliationAmt(fView.getUnReconciliationAmt());
						
						// 贷款金额 = 开始的贷款金额 - 展期的金额
						financeBusinessView.setCreditAmt(financeBusinessView.getCreditAmt()- financeBusinessView.getExtensionAmt());
						
						financeBusinessResultList.add(financeBusinessView);
				 }
			 }
			 
			 	
			 return financeBusinessResultList;
		} catch (Exception e) {
			logger.error("财务-查询客户业务信息失败",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-查询客户业务失败！");
			
		}
	}
	@Override
	public int countCusBusiness(
			FinanceBusinessCondition financeBusinessCondition)
			throws ThriftServiceException, TException {
		try {
			int result= financeBankMapper.countFinanceCusBusiness(financeBusinessCondition);
			return result;
		} catch (Exception e) {
			logger.error("查询客户业务数量失败",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-查询客户统计数量业务失败！");
			
		}
	}
	@Override
	public int countFinanceActtManager(FinanceBank financeBank)
			throws TException {
		try {
			int result= financeBankMapper.countFinanceAcctManager(financeBank);
			return result;
		} catch (Exception e) {
			logger.error("countFinanceActtManager fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-查询银行账户统计数量业务失败！");
			
		}
	}
	@Override
	public int addAcctProjectBalance(
			AcctProjectBalanceDTO acctProjectBalanceDTO)
			throws ThriftServiceException, TException {
		try {
			int result= financeBankMapper.addAcctProjectBalance(acctProjectBalanceDTO);
			if(result>0){
				//更新财务收款记录
				boolean bool=this.updateReceivablesData(acctProjectBalanceDTO.getReceivablesId(), acctProjectBalanceDTO.getDateVersion(), 0.0);
			    if(!bool){
			    	return -1;
			    }
			    
			}
			
			return result;
		} catch (Exception e) {
			logger.error("addAcctProjectBalance fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-多付金额转入客户余额处理业务失败！");
			
		}
	}
	@Override
	public AcctProjectBalanceView getAcctProjectBalanceByLoanId(
			int loanId) throws ThriftServiceException, TException {
		try {
			AcctProjectBalanceView acctProjectBalanceView= financeBankMapper.getAcctProjectBalanceByLoand(loanId);
			return acctProjectBalanceView!=null ?acctProjectBalanceView:new AcctProjectBalanceView();
		} catch (Exception e) {
			logger.error("getAcctProjectBalanceByLoanId fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-多付金额转入客户余额查询业务失败！");
			
		}
	}

	@Override
	public AcctProjectBalanceView getBalanceByReceId(int receId)
			throws ThriftServiceException, TException {
		try {
			AcctProjectBalanceView acctProjectBalanceView= financeBankMapper.getBalanceByReceId(receId);
			return acctProjectBalanceView!=null ?acctProjectBalanceView:new AcctProjectBalanceView();
		} catch (Exception e) {
			logger.error("getBalanceByReceId fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-多付金额转入客户余额查询业务失败！");
			
		}
	}
	
	//add by yql
	@Override
	public List<BatchRepaymentView>  getAcctBatchRepayment(
			FinanceBusinessCondition financeBusinessCondition)
			throws ThriftServiceException, TException {
		try {
			List<BatchRepaymentView> list= financeBankMapper.getAcctBatchRepayment(financeBusinessCondition);
			return list!=null?list:new ArrayList<BatchRepaymentView>();
		} catch (Exception e) {
			logger.error("getAcctBatchRepayment fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-查询批量对账业务失败！");
			
		}
	}
	@Override
	public int countAcctBatchRepayment(
			FinanceBusinessCondition financeBusinessCondition)
			throws ThriftServiceException, TException {
		try {
			int result=financeBankMapper.countAcctBatchRepayment(financeBusinessCondition);
			return result;
		} catch (Exception e) {
			logger.error("countAcctBatchRepayment fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-查询批量对账统计数量业务失败！");
			
		}
	}
	@Override
	public List<CustArrearsView> getCustArrearsView(
			FinanceBusinessCondition financeBusinessCondition)
			throws ThriftServiceException, TException {
		try {
			List<CustArrearsView> list=financeBankMapper.getCustArrearsView(financeBusinessCondition);
			List<CustArrearsView> resultList= new ArrayList<CustArrearsView>();
			//循环取金额的值
			double receiveTotalAmt=0.0;//已收小计
			double noReceiveTotalAmt=0.0;//未收合计
			if(list!=null){
				for(CustArrearsView custArrearsView : list){
					
					Map<String, Object> param = new HashMap<String, Object>();
			        param.put("results",OracleTypes.CURSOR);
			        param.put("projectId",custArrearsView.getPid());		
			        param.put("currentDate", DateUtil.fmtDateToStr(new Date(), "yyyy-MM-dd") );
					
				        
					financeBankMapper.getProGetCustProjectDetail(param);
					List<CustArrearsView> paramList = (List<CustArrearsView>) param.get("results");
					CustArrearsView custAView= paramList.get(0);
					if(custAView!=null){
						custAView.setPid(custArrearsView.getPid());
						custAView.setProjectName(custArrearsView.getProjectName());
						custAView.setProjectNumber(custArrearsView.getProjectNumber());
						custAView.setLoanId(custArrearsView.getLoanId());
						custAView.setContractId(custArrearsView.getContractId());
						custAView.setContractNo(custArrearsView.getContractNo());
						custAView.setCusName(custArrearsView.getCusName());
						custAView.setAcctId(custArrearsView.getAcctId());	
						custAView.setCusType(custArrearsView.getCusType());
						custAView.setComId(custArrearsView.getComId());
						custAView.setContractUrl(custArrearsView.getContractUrl());
						//已收小计
						receiveTotalAmt=custAView.getReceivedPrincipal()+custAView.getReceivedInterest()+custAView.getReceivedMangCost()
											+custAView.getReceivedOtherCost()+custAView.getReceivedExpireInterest()+custAView.getReceivedOverduePenalty();
						custAView.setReceiveTotalAmt(receiveTotalAmt);				
		                 
//						noReceiveTotalAmt = custAView.getUnReceivedPrincipal() + custAView.getUnReceivedInterest() + custAView.getUnReceivedMangCost() + custAView.getUnReceivedOtherCost() + custAView.getUnReceivedOverdueInterest() + custAView.getUnReceivedOverdueInterest() + custAView.getNoReceiveTotalAmt_im();
//											custAView.setExpireNoReceiveTotalAmt(expireNoReceiveTotalAmt);
////						custAView.setNoReceiveTotalAmt(noReceiveTotalAmt);
						
						resultList.add(custAView);
					}else{
						resultList.add(custArrearsView);
					}
					
				}
			}
			
			return resultList;
		} catch (Exception e) {
			logger.error("getCustArrearsView fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-查询欠款明细列表业务失败！");
			
		}
	}

	@Override
	public int countCustArrearsView(
			FinanceBusinessCondition financeBusinessCondition)
			throws ThriftServiceException, TException {
		try {
			int result= financeBankMapper.countCustArrearsView(financeBusinessCondition);
			return result;
		} catch (Exception e) {
			logger.error("countCustArrearsView fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-查询欠款明细列表统计数量业务失败！");
			
		}
	}

	@Override
	public List<FinanceAcctTotalView> getFinanceAcctTotalView(
			FinanceAcctTotalCondition financeAcctTotalCondition)
			throws ThriftServiceException, TException {
		try {
			List<FinanceAcctTotalView> list= financeBankMapper.getFinanceAcctTotalView(financeAcctTotalCondition);
			List<FinanceAcctTotalView> resultList=new ArrayList<FinanceAcctTotalView>();
			//循环取值
			if(list!=null ){
				for(FinanceAcctTotalView financeAcctTotalView:list){
					Map<String, Object> param = new HashMap<String, Object>();
//					List<FinanceAcctTotalView> paramList= new ArrayList<FinanceAcctTotalView>();
			        param.put("results",OracleTypes.CURSOR);
			        param.put("bankId",financeAcctTotalView.getPid());	
			        if(StringUtils.isEmpty(financeAcctTotalCondition.getSearcherPeriodStart())){
						FinanceBank financeBank= financeBankMapper.getFinanceAcctManagerById(Integer.parseInt(financeAcctTotalView.getPid()));
						param.put("searcherPeriodStart",financeBank.getCreDttm());
					}else{
						param.put("searcherPeriodStart",financeAcctTotalCondition.getSearcherPeriodStart());
					}
			        
			        if(StringUtils.isEmpty(financeAcctTotalCondition.getSearcherPeriodEnd())){
			        	param.put("searcherPeriodEnd",DateUtil.format(new Date(), "yyyy-MM-dd"));	
			        }else{
			        	param.put("searcherPeriodEnd",financeAcctTotalCondition.getSearcherPeriodEnd());	
			        }
			        
			       financeBankMapper.getFinanceTotalDatePro(param);
					List<FinanceAcctTotalView> paramList = (List<FinanceAcctTotalView>) param.get("results");
					FinanceAcctTotalView tempview= paramList.get(0);
					if(tempview!=null){
						financeAcctTotalView.setAccountOut(tempview.getAccountOut());
						financeAcctTotalView.setInitialAmt(tempview.getInitialAmt());
						financeAcctTotalView.setIncomeAccount(tempview.getIncomeAccount());
						financeAcctTotalView.setPeriodBalance(tempview.getPeriodBalance());
					}
					
					resultList.add(financeAcctTotalView);
				}
			}
			
			return resultList!=null?resultList:new ArrayList<FinanceAcctTotalView>();
		} catch (Exception e) {
			logger.error("getFinanceAcctTotalView fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-财务总账列表业务失败！");
			
		}
	}

	@Override
	public int countFinanceAcctTotal(
			FinanceAcctTotalCondition financeAcctTotalCondition)
			throws ThriftServiceException, TException {
		try {
			int result= financeBankMapper.countFinanceAcctTotal(financeAcctTotalCondition);
			return result;
		} catch (Exception e) {
			logger.error("countFinanceAcctTotal fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-财务总账列表统计数量业务失败！");
			
		}
	}

	@Override
	public List<FinanceAcctTotalDetailView> getFinanceAcctTotalDetail(
			FinanceAcctTotalCondition financeAcctTotalCondition)
			throws ThriftServiceException, TException {
		try {
			if(StringUtils.isEmpty(financeAcctTotalCondition.getSearcherPeriodStart())){
				FinanceBank financeBank= financeBankMapper.getFinanceAcctManagerById(financeAcctTotalCondition.getPid());
				financeAcctTotalCondition.setSearcherPeriodStart(financeBank.getCreDttm());
			}
			 if(StringUtils.isEmpty(financeAcctTotalCondition.getSearcherPeriodEnd())){
				 financeAcctTotalCondition.setSearcherPeriodEnd(DateUtil.format(new Date(), "yyyy-MM-dd"));	
	        }
			List<FinanceAcctTotalDetailView> list= financeBankMapper.getFinanceAcctTotalDetailView(financeAcctTotalCondition);
		    return list!=null?list:new ArrayList<FinanceAcctTotalDetailView>();
		} catch (Exception e) {
			logger.error("getFinanceAcctTotalDetail fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-财务总账列表财务明细列表业务失败！");
			
		}
	}
	@Override
	public List<ProjectTotalDetailView> getProjectTotalDetailList(
			FinanceBusinessCondition financeBusinessCondition)
			throws ThriftServiceException, TException {
		try {
			List<ProjectTotalDetailView> list= financeBankMapper.getProjectTotalDetailList(financeBusinessCondition);
			return list!=null?list:new ArrayList<ProjectTotalDetailView>();
		} catch (Exception e) {
			logger.error("getProjectTotalDetailList fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-财务客户业务查询，项目总流水列表业务失败！");
			
		}
	}
	@Override
	public List<LoanReconciliationDtlView> getLoanReconciliationDtl(
			int receId) throws ThriftServiceException, TException {
		try {
			List<LoanReconciliationDtlView> list= financeBankMapper.getLoanReconciliationDtl(receId);
			return list!=null?list:new ArrayList<LoanReconciliationDtlView>();
		} catch (Exception e) {
			logger.error("getLoanReconciliationDtl fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-财务客户业务查询，项目总流水的查看对账信息业务失败！");
			
		}
	}
	@Override
	public int addLoanRefund(LoanRefundDTO loanRefundDTO)
			throws ThriftServiceException, TException {
		int result=0;
		try {
			LoanRefundMapper.insert(loanRefundDTO);//返回退款id
			result=loanRefundDTO.getPid();
			//更新财务收款记录
			if(result>0){
				double ble=loanRefundDTO.getPayableRefundAmt()-loanRefundDTO.getActualRefundAmt();
				// 更新财务收款记录
				boolean bool=this.updateReceivablesData(loanRefundDTO.getReceivablesId(), loanRefundDTO.getDateVersion(), ble);
			    if(!bool){
			    	return -1;
			    }
			    LoanOutputInfoImplDTO loanOutputInfoImplDTO = new LoanOutputInfoImplDTO();
			    loanOutputInfoImplDTO.setRefId(loanRefundDTO.getPid());//返回退款id
			    loanOutputInfoImplDTO.setFtType(3);//退款
			    loanOutputInfoImplDTO.setTenderType(2);
			    loanOutputInfoImplDTO.setFtAmt(loanRefundDTO.getActualRefundAmt());
			    
			    loanOutputInfoImplDTO.setFtDate(DateUtil.format(new Date(),"yyyy-MM-dd"));
			    loanOutputInfoImplDTO.setFtBankAcctId(loanRefundDTO.getRefundBankId());
			    loanOutputInfoImplDTO.setFtUserId(loanRefundDTO.getRefundUserId());
			    result=LoanOutputInfoMapper.insertLoadFTinfo(loanOutputInfoImplDTO);//财务交易记录表
			}
			
			return result;
		} catch (Exception e) {
			logger.error("addLoanRefund fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-财务对账余额退还处理插入失败！");
			
		}
	}

	@Override
	public List<LoanRefundView> getLoanRefundList(int projectId)
			throws ThriftServiceException, TException {
		try {
			List<LoanRefundView> list= LoanRefundMapper.getLoanRefundList(projectId);
			return list!=null?list:new ArrayList<LoanRefundView>();
		} catch (Exception e) {
			logger.error("getLoanRefundList fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-财务对账余额退还处理业务失败！");
			
		}
	}
	@Override
	public List<UnReconciliationView> getListUnReconciliation(
			UnReconciliationCondition unReconciliationCondition)
			throws ThriftServiceException, TException {
		try {
			List<UnReconciliationView> list= financeBankMapper.getListUnReconciliation(unReconciliationCondition);
			return list!=null?list:new ArrayList<UnReconciliationView>();
		} catch (Exception e) {
			logger.error("getListUnReconciliation fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-未对账项目查询业务失败！");
			
		}
	}

	@Override
	public int countUnReconciliation(
			UnReconciliationCondition unReconciliationCondition)
			throws ThriftServiceException, TException {
		try {
			int result= financeBankMapper.countUnReconciliation(unReconciliationCondition);
			return result;
		} catch (Exception e) {
			logger.error("countUnReconciliation fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-未对账项目查询统计数量业务失败！");
			
		}
	}
	@Override
	public UnReconciliationView findUnReconciliationInfo(int inputId)
			throws ThriftServiceException, TException {
		try {
			UnReconciliationView unReconciliationView= financeBankMapper.findUnReconciliationInfo(inputId);
			return unReconciliationView!=null?unReconciliationView:new UnReconciliationView();
		} catch (Exception e) {
			logger.error("findUnReconciliationInfo fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY,"财务管理-根据收款id查询未对账项目查询相信信息业务失败！");
			
		}
	}

	//add by yql
	@Override
	public Project getProCreLoans(int loanId) throws ThriftServiceException,
			TException {
		
		try {
			Project project=projectMapper.getProjectCreLoans(loanId);
			return project;
		} catch (Exception e) {
			logger.error("getProCreLoans fail",e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_RECONCILIATION_QUERY,"财务管理-查询手动对账收款信息错误！");
		}
		
	}
	// add by qcxian
		// ================================================================================================================
		/**
		 * 获取用户财务收款页面的数据对象
		 */
		@Override
		public FinanceReceivablesView getFinanceReceivablesView(int loanId) throws TException {
			// TODO Auto-generated method stub

			// 新的收款对账是不需要查询财务收款记录
			// // 根据项目来查询出对应的财务收款记录
			// FinanceReceivablesCondition condition = new
			// FinanceReceivablesCondition();
			// condition.setProjectId(String.valueOf(projectId));
			// List<FinanceReceivablesDTO> dtoList =
			// financeBankMapper.getFinanceReceivablesDTOList(condition);
			FinanceReceivablesView view = financeBankMapper.getFinanceReceivablesView(loanId);

			return view!=null?view:new FinanceReceivablesView();
		}

		/**
		 * 保存财务收款
		 * 
		 * @Description: 保存财务收款
		 * @param dto
		 *            财务收款对象
		 * @return
		 * @throws TException
		 * @author: qiancong.Xian
		 * @date: 2015年3月12日 下午4:14:14
		 */
		@Override
		public FinanceReceivablesDTO saveFinanceReceivables(FinanceReceivablesDTO dto,int projectId,int acctId) {
			
			logger.info("财务收款 FinanceServiceImpl.saveFinanceReceivables ：projectId["+projectId+"] acctId ["+acctId+"] loanId["+dto.getLoanId()+"] paymentAmount["+dto.getPaymentAmount()+"] paymentDttm["+dto.getPaymentDttm()+"] useBalance["+dto.getUseBalance()+"] reconciliation["+dto.getReconciliation()+"] status["+dto.getStatus()+"] version["+dto.getVersion()+"] availableBalance["+dto.getAvailableBalance()+"] reconciliationAmt["+dto.getReconciliation()+"]" );

			// 有主键，修改
			if (dto.getPid() > 0) {
			} else {
				financeBankMapper.insertFinanceReceivables(dto);
			}

			//  有使用客户余额的情况,保存用户余额使用记录
			if(dto.getUseBalance()>0)
			{
				AcctProjectBalanceDTO acctProjectBalanceDTO = new AcctProjectBalanceDTO();
				acctProjectBalanceDTO.setAcctId(acctId);
				acctProjectBalanceDTO.setBalanceAmt(dto.getUseBalance());
				acctProjectBalanceDTO.setBalanceDt(dto.getPaymentDttm());
				acctProjectBalanceDTO.setBalanceType(2); //  根据数据库描述，2为使用
				acctProjectBalanceDTO.setProjectId(projectId);
				acctProjectBalanceDTO.setReceivablesId(dto.getPid());
				financeBankMapper.addAcctProjectBalance(acctProjectBalanceDTO);
			}	
			
			// 更新没有金额的记录为已对账状态(避免项目结不了项目)
			financeBankMapper.updateNullValuePlan(dto.getLoanId());
			financeBankMapper.updateNullValueIMPlan(dto.getLoanId());
			
			return dto;
		}
		
		@Override
		public FinanceReceivablesDTO saveUseBalance(FinanceReceivablesDTO dto, int projectId, int acctId) throws TException {
			
			logger.info("基于旧的收款对账时候，继续使用客户余额 FinanceServiceImpl.saveUseBalance ：projectId["+projectId+"] acctId ["+acctId+"] loanId["+dto.getLoanId()+"] paymentAmount["+dto.getPaymentAmount()+"] paymentDttm["+dto.getPaymentDttm()+"] useBalance["+dto.getUseBalance()+"] reconciliation["+dto.getReconciliation()+"] status["+dto.getStatus()+"] version["+dto.getVersion()+"] availableBalance["+dto.getAvailableBalance()+"] reconciliationAmt["+dto.getReconciliation()+"]" );
			// 有主键，修改
			int row = financeBankMapper.updateFinanceReceivablesUseBalance(dto);
			dto.setVersion(dto.getVersion()+1); // 版本号+1
			// 没有更新到数据
			if(row ==0)
			{
				throw new TException("数据发生了改变，请关刷新后重试.");
			}
	
			//  有使用客户余额的情况,保存用户余额使用记录
			if(dto.getUseBalance()>0)
			{
				AcctProjectBalanceDTO acctProjectBalanceDTO = new AcctProjectBalanceDTO();
				acctProjectBalanceDTO.setAcctId(acctId);
				acctProjectBalanceDTO.setBalanceAmt(dto.getUseBalance());
				acctProjectBalanceDTO.setBalanceDt(dto.getPaymentDttm());
				acctProjectBalanceDTO.setBalanceType(2); //  根据数据库描述，2为使用
				acctProjectBalanceDTO.setProjectId(projectId);
				acctProjectBalanceDTO.setReceivablesId(dto.getPid());
				financeBankMapper.addAcctProjectBalance(acctProjectBalanceDTO);
			}	
			
			return dto;
		}
		
		/**
		 * @Description: 获取当前项目可以对账的选项
		 * @param projectId
		 *            项目ID
		 * @return
		 * @throws TException
		 * @author: qiancong.Xian
		 * @date: 2015年3月13日 上午11:10:11
		 */
		@Override
		public List<ReconciliationOptionsView> getReconciliationOptionsList(int loanId, String currentDate) throws TException {

			List<ReconciliationOptionsView> list = financeBankMapper.getReconciliationOptions(loanId);

			if (null != list) {
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("results", OracleTypes.CURSOR);
				param.put("loanId", loanId);
				
				// 设置页面要要展现的名词
				for (ReconciliationOptionsView vo : list) {
					// 正常还款计划
					if (vo.getType() == FinanceConstant.ACCOUNT_STATEMENT_TYPE_PLAN) {
						vo.setName("收息第" + vo.getRefNum() + "期");
						// 还款期数
						param.put("cycleNum", vo.getRefNum());

						// 获取对应的减免数据
						financeBankMapper.getRepaymentPlanLoanFeew(param);
						List<LoanFeew> resultList = (List<LoanFeew>) param.get("results");
						LoanFeew result = resultList.get(0);
						if(null != result && result.getTotalAmt()>0)
						{
							vo.setHasFeew(true);
						}	
					}
					// 即时还款计划
					else {
						vo.setName(FinanceTypeEnum.getName(vo.getRefNum()));
					}

					vo.setOverdue(FinanceUtil.getIntervalDays(vo.repayDt, currentDate));
				}
			}

			return list;
		}

		@Override
		public ReconciliationItem getReconciliationItem(int type, int pid, String currentDt){
			// 计划还款
			ReconciliationItem result = null;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("results", OracleTypes.CURSOR);
			param.put("pid", pid);

			if (type == FinanceConstant.ACCOUNT_STATEMENT_TYPE_PLAN) {

				financeBankMapper.getRepaymentPlanReconciliationItem(param);
				List<ReconciliationItem> resultList = (List<ReconciliationItem>) param.get("results");
				result = resultList.get(0);

			}
			// 即时计划还款
			else {
				financeBankMapper.getRealtimePlanReconciliationItem(param);
				List<ReconciliationItem> resultList = (List<ReconciliationItem>) param.get("results");
				result = resultList.get(0);
				result.setRealtimePlanName(FinanceTypeEnum.getName(result.getOperType()));
			}

			// 计算逾期相关项
			calculateOverdue(type, pid, result, currentDt);
			// 计算费用减免部分
			calculateLoanFeew(type, pid, result);
			return result;
		}
		
		/**
		 * 构造批量还款对账细节中，费用减免
		  * @param item
		  * @author: qiancong.Xian
		  * @date: 2015年6月11日 下午3:36:21
		 */
		private void calculateLoanFeew(BatchRepaymentItem item)
		{
			// 非还款计划表中的数据，返回
			if (item.getSType()== FinanceConstant.ACCOUNT_STATEMENT_TYPE_INSTANT) {
                return;
			}
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("results", OracleTypes.CURSOR);
				param.put("loanId", item.getLoanId());
				param.put("cycleNum", item.getFinanceType());

				// 获取对应的减免数据
				financeBankMapper.getRepaymentPlanLoanFeew(param);
				List<LoanFeew> resultList = (List<LoanFeew>) param.get("results");
				LoanFeew result = resultList.get(0);
				if(null == result)
				{
					return;
				}
				
				// 应还金额
				double totalAmt = item.getTotalAmt();
				
				// 减除费用减免部分
				totalAmt = totalAmt - result.getInterest();
				totalAmt = totalAmt -  result.getMgr();
				totalAmt = totalAmt - result.getOther();
				
				totalAmt = totalAmt - result.getOverduinterest();
				totalAmt = totalAmt - result.getFineinterest();
				
				item.setTotalAmt(totalAmt);
		}
		
		/**
		 * 计算费用减免部分
		  * @param type
		  * @param pid
		  * @param item
		  * @author: qiancong.Xian
		  * @date: 2015年5月18日 下午3:48:03
		 */
		private void calculateLoanFeew(int type, int pid, ReconciliationItem item)
		{
			// 非还款计划表中的数据，返回
			if (type != FinanceConstant.ACCOUNT_STATEMENT_TYPE_PLAN) {
                return;
			}
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("results", OracleTypes.CURSOR);
			param.put("loanId", item.getLoanId());
			param.put("cycleNum", item.getCycleNum());

			// 获取对应的减免数据
			financeBankMapper.getRepaymentPlanLoanFeew(param);
			List<LoanFeew> resultList = (List<LoanFeew>) param.get("results");
			LoanFeew result = resultList.get(0);
			if(null == result)
			{
				return;
			}
			
			// 减除费用减免部分
			item.setInterest(item.getInterest() - result.getInterest());
			item.setMangCost(item.getMangCost() - result.getMgr());
			item.setOtherCost(item.getOtherCost() - result.getOther());
			
			// 可以减免的逾期利息
			double feewAmt = result.getOverduinterest();

			// 计算本金逾期利息
			// 减免的金额大于要减免金额 
			if(feewAmt>item.getPrincipal_yl())
			{
				feewAmt = feewAmt-item.getPrincipal_yl();
				item.setPrincipal_yl(0);
			}
			else
			{
				item.setPrincipal_yl(item.getPrincipal_yl()-feewAmt);
				feewAmt = 0;
			}	
			
			// 计算管理费逾期利息
			if(feewAmt>item.getMangCost_yl())
			{
				feewAmt = feewAmt-item.getMangCost_yl();
				item.setMangCost_yl(0);
			}
			else
			{
				item.setMangCost_yl(item.getMangCost_yl()-feewAmt);
				feewAmt = 0;
			}	
			
			// 计算利息逾期利息
			if(feewAmt>item.getInterest_yl())
			{
				feewAmt = feewAmt-item.getInterest_yl();
				item.setInterest_yl(0);
			}
			else
			{
				item.setInterest_yl(item.getInterest_yl()-feewAmt);
				feewAmt = 0;
			}	
			
			// 计算其他费用逾期利息
			if(feewAmt>item.getOtherCost_yl())
			{
				feewAmt = feewAmt-item.getOtherCost_yl();
				item.setOtherCost_yl(0);
			}
			else
			{
				item.setOtherCost_yl(item.getOtherCost_yl()-feewAmt);
				feewAmt = 0;
			}	
		}

		// 计算逾期费用
		private void calculateOverdue(int type, int pid, ReconciliationItem item, String currentDt) {
			if (null == item) {
				return;
			}
			// 判读时间是否逾期
			item.setOverdue(FinanceUtil.isOverdue(item.getPepayDt(), currentDt));
			// 没有逾期，直接返回
			if (!item.isOverdue()) {
				return;
			}

			if (type == FinanceConstant.ACCOUNT_STATEMENT_TYPE_PLAN) {
				calculateOverduePrincipal(item, pid, currentDt); // 计算本金逾期
				calculateOverdueMangCost(item, pid, currentDt); // 计算管理费逾期
				calculateOverdueInterest(item, pid, currentDt); // 计算利息逾期
				calculateOverdueOtherCost(item, pid, currentDt); // 计算其他费用逾期
			}
			// 即时计划还款
			else if (type == FinanceConstant.ACCOUNT_STATEMENT_TYPE_INSTANT) {
				calculateOverdueRealtime(item, pid, currentDt);// 即时逾期罚息
			}
		}

		/**
		 * 计算计算发生的费用对应的逾期罚息
		 * 
		 * @Description: TODO
		 * @param item
		 * @param pid
		 * @param currentDt
		 * @author: qiancong.Xian
		 * @date: 2015年3月16日 下午4:43:08
		 */
		private void calculateOverdueRealtime(ReconciliationItem item, int pid, String currentDt) {
			OverdueDataCondition condition = new OverdueDataCondition();
			condition.setType(item.getOperType());
			condition.setPid(pid);
			condition.setCurrentDt(currentDt);

			item.setRealtimePlan_yf(financeBankMapper.getOverdueRealtimeFine(condition));

		}

		/**
		 * 
		 * @Description: 计算本金逾期相关（逾期利息、逾期罚息）
		 * @param item
		 * @param currentDt
		 * @author: qiancong.Xian
		 * @date: 2015年3月14日 下午3:22:56
		 */
		private void calculateOverduePrincipal(ReconciliationItem item, int pid, String currentDt) {
			OverdueDataCondition condition = new OverdueDataCondition();
			condition.setType(FinanceTypeEnum.TYPE_30.getType());
			condition.setPid(pid);
			condition.setCurrentDt(currentDt);

			item.setPrincipal_yl(financeBankMapper.getOverdueRepaymentFine(condition));
		}

		/**
		 * 
		 * @Description: 计算管理费逾期相关（逾期利息、逾期罚息）
		 * @param item
		 * @param currentDt
		 * @author: qiancong.Xian
		 * @date: 2015年3月14日 下午3:22:56
		 */
		private void calculateOverdueMangCost(ReconciliationItem item, int pid, String currentDt) {
			OverdueDataCondition condition = new OverdueDataCondition();
			condition.setType(FinanceTypeEnum.TYPE_40.getType());
			condition.setPid(pid);
			condition.setCurrentDt(currentDt);
			item.setMangCost_yl(financeBankMapper.getOverdueRepaymentFine(condition));
		}

		/**
		 * 
		 * @Description: 计算利息逾期相关（逾期利息、逾期罚息）
		 * @param item
		 * @param currentDt
		 * @author: qiancong.Xian
		 * @date: 2015年3月14日 下午3:22:56
		 */
		private void calculateOverdueInterest(ReconciliationItem item, int pid, String currentDt) {
			OverdueDataCondition condition = new OverdueDataCondition();
			condition.setType(FinanceTypeEnum.TYPE_50.getType());
			condition.setPid(pid);
			condition.setCurrentDt(currentDt);

			item.setInterest_yl(financeBankMapper.getOverdueRepaymentFine(condition));
		}

		/**
		 * 
		 * @Description: 计算其他费用逾期相关（逾期利息、逾期罚息）
		 * @param item
		 * @param currentDt
		 * @author: qiancong.Xian
		 * @date: 2015年3月14日 下午3:22:56
		 */
		private void calculateOverdueOtherCost(ReconciliationItem item, int pid, String currentDt) {
			OverdueDataCondition condition = new OverdueDataCondition();
			condition.setType(FinanceTypeEnum.TYPE_60.getType());
			condition.setPid(pid);
			condition.setCurrentDt(currentDt);

			item.setOtherCost_yl(financeBankMapper.getOverdueRepaymentFine(condition));
		}

		/**
		 * @Description: 更新财务收款记录
		 * @param receivablesId
		 *            主键
		 * @param receivablesVersion
		 *            数据版本
		 * @param availableReconciliationAmount
		 *            剩余可用
		 * @return 是否成功
		 * @author: qiancong.Xian
		 * @date: 2015年3月24日 上午9:34:56
		 */
		public boolean updateReceivablesData(int receivablesId, int receivablesVersion, double availableReconciliationAmount) {
			// TODO 记录财务交易记录
			int receivablesReconciliation = availableReconciliationAmount > 0 ? FinanceConstant.FINANCE_RECEIVABLES_UNUSED : FinanceConstant.FINANCE_RECEIVABLES_FINISH;
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("receivablesReconciliation", receivablesReconciliation);
			param.put("receivablesId", receivablesId);
			param.put("receivablesVersion", receivablesVersion);
			param.put("availableReconciliationAmount", availableReconciliationAmount);

			int status = financeBankMapper.updateReceivablesData(param);
			return status > 0;
		}

		/**
		 * 保存财务对账
		 */
		@Override
		public int saveRepaymentReconciliation(List<RepaymentReconciliationBean> repaymentReconciliationBeanList, int receivablesVersion, double availableReconciliationAmount, int receivablesId, int loanInterestRecord, int loanMgrRecord, int loanOtherFee){

			logger.info("listSize["+repaymentReconciliationBeanList.size()+"] receivablesVersion["+receivablesVersion+"] availableReconciliationAmount["+availableReconciliationAmount+"] receivablesId["+receivablesId+"] loanInterestRecord["+loanInterestRecord+"] loanMgrRecord["+loanMgrRecord+"] loanOtherFee["+loanOtherFee+"]");
			// 更改财务收款信息
			boolean status = updateReceivablesData(receivablesId, receivablesVersion, availableReconciliationAmount);
			// 数据已更新
			if (!status) {
				logger.error("保存财务对账失败，财务收款数据已经变更");
				return 1;
			}

			return saveBatchRepaymentReconciliation(repaymentReconciliationBeanList, loanInterestRecord, loanMgrRecord, loanOtherFee);
		}
		
		/**
		 * 保存当前财务收款用于对账的所有对账单记录状态
		  * @param repaymentReconciliationBeanList
		  * @param loanInterestRecord
		  * @param loanMgrRecord
		  * @param loanOtherFee
		  * @return
		  * @author: qiancong.Xian
		  * @date: 2015年3月31日 下午8:18:37
		 */
		private int saveBatchRepaymentReconciliation(List<RepaymentReconciliationBean> repaymentReconciliationBeanList, int loanInterestRecord, int loanMgrRecord, int loanOtherFee)
		{
			if (null == repaymentReconciliationBeanList) {
				return -1;
			}
			
			try {
				// 保存财务交易记录
				saveFinanceTransactionRecords(repaymentReconciliationBeanList, loanInterestRecord, loanMgrRecord, loanOtherFee);
			} catch (Exception e) {
				logger.error("保存财务对账失败", e);
				throw new RuntimeException(e);
			}
			
			return 0;
		}

		/**
		 * 保存财务交易记录
		 * 
		 * @param repaymentReconciliationBeanList
		 * @param loanInterestRecord
		 * @param loanMgrRecord
		 * @param loanOtherFee
		 * @author: qiancong.Xian
		 * @date: 2015年3月25日 下午5:55:40
		 */
		private void saveFinanceTransactionRecords(List<RepaymentReconciliationBean> repaymentReconciliationBeanList, int loanInterestRecord, int loanMgrRecord, int loanOtherFee) throws Exception {
			// 初始化3个对象
			List<List<FinanceTransactionDTO>> financeTransactionDTOList = new ArrayList<List<FinanceTransactionDTO>>();
			financeTransactionDTOList.add(new ArrayList<FinanceTransactionDTO>());
			financeTransactionDTOList.add(new ArrayList<FinanceTransactionDTO>());
			financeTransactionDTOList.add(new ArrayList<FinanceTransactionDTO>());
			for (RepaymentReconciliationBean record : repaymentReconciliationBeanList) {
				List<RepaymentReconciliationDetailDTO> detailList = record.getDetaiList(); // 对应的细节
				for (RepaymentReconciliationDetailDTO detailDTO : detailList) {
					getFinanceTransactionDTO(record, detailDTO, financeTransactionDTOList, loanInterestRecord, loanMgrRecord, loanOtherFee);
				}
			}

			// 循环保存每一期的对账
			for (RepaymentReconciliationBean record : repaymentReconciliationBeanList) {
				saveOneRepaymentReconciliation(record);
			}
			
			// 保存财务交易记录
			for(List<FinanceTransactionDTO> dtoList :financeTransactionDTOList)
			{
				for (FinanceTransactionDTO financeTransactionDTO : dtoList) {
					if (financeTransactionDTO.getFtAmt() != 0) {
						financeTransactionDTO.setCreateDate(DateUtil.format(DateUtil.getToday()));
						financeTransactionDTO.setCreateUser(financeTransactionDTO.getUserId()+"");
						financeBankMapper.insertFinanceTransaction(financeTransactionDTO);
					}
				}
			}
		}

		/**
		 * @ 财务对账细节数据分发
		 * 
		 * @param record
		 * @return
		 * @author: qiancong.Xian
		 * @date: 2015年3月26日 下午1:55:56
		 */
		private void getFinanceTransactionDTO(RepaymentReconciliationBean record, RepaymentReconciliationDetailDTO detailDto, List<List<FinanceTransactionDTO>> list, int loanInterestRecord, int loanMgrRecord, int loanOtherFee) {
			// List位置 0： 剩余的数据都记这里 1： 管理费及其对应的逾期的利息、逾期的罚息等记入到这个账 2:
			// 其他费用及对应的费逾期利息、逾期罚息等记入到这个账户
			
			List<FinanceTransactionDTO> dtoList = null;
			// 管理费相关(包括退管理费)
			if (detailDto.getDetailType() == FinanceTypeEnum.TYPE_40.getType() || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_40.getType() - 1000) || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_40.getType() - 2000)
			 || detailDto.getDetailType() == FinanceTypeEnum.TYPE_4.getType() || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_4.getType() - 1000) || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_4.getType() - 2000)	
					) {
				dtoList = list.get(1);
				dtoList.add(buildFinanceTransactionDTO(record, detailDto, loanMgrRecord, 2));
				detailDto.setBankId(loanMgrRecord);
			}
			// 其他费用相关(包括退其他费用)
			else if (detailDto.getDetailType() == FinanceTypeEnum.TYPE_60.getType() || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_60.getType() - 1000) || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_60.getType() - 2000)
			 || detailDto.getDetailType() == FinanceTypeEnum.TYPE_5.getType() || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_5.getType() - 1000) || detailDto.getDetailType() == (FinanceTypeEnum.TYPE_5.getType() - 2000)		
					) {
				dtoList = list.get(2);
				dtoList.add(buildFinanceTransactionDTO(record, detailDto, loanOtherFee,3));
				
				detailDto.setBankId(loanOtherFee);
				
			} else {
				dtoList = list.get(0);
				dtoList.add(buildFinanceTransactionDTO(record, detailDto, loanInterestRecord,1));
				detailDto.setBankId(loanInterestRecord);
			}
		}
		
		/**
		 *   构造财务交易记录对象
		  * @param record
		  * @param detailDto
		  * @param bankAcctId
		  * @param specialType
		  * @return
		  * @author: qiancong.Xian
		  * @date: 2015年5月7日 下午4:07:43
		 */
		private FinanceTransactionDTO buildFinanceTransactionDTO(RepaymentReconciliationBean record,RepaymentReconciliationDetailDTO detailDto,int bankAcctId,int specialType)
		{
			FinanceTransactionDTO dto = new FinanceTransactionDTO();
			
			dto.setRefId(record.getRepaymentReconciliation().getReceivablesId()); // 财务对账记录
			dto.setUserId(record.getRepaymentReconciliation().getUserId());
			dto.setFtType(2); // 1：放款 2：收款
			dto.setTenderType("");// 预留字段
			dto.setFtDate(record.getRepaymentReconciliation().getReconciliationDt());
			dto.setFtAmt(detailDto.getReconciliationAmt());
			dto.setBankAcctId(bankAcctId);
			dto.setSpecialType(specialType);
			
			int cycleNum = record.getRepaymentReconciliation().getCycleNum();
			//  备注
			dto.setRemark(cycleNum>0?cycleNum+"期："+detailDto.getDetailTypeName():detailDto.getDetailTypeName());
			return dto;
		}

		/**
		 * 保存一期财务对账
		 * 
		 * @param record
		 * @author: qiancong.Xian
		 * @throws TException 
		 * @date: 2015年3月17日 下午3:28:22
		 */
		private void saveOneRepaymentReconciliation(RepaymentReconciliationBean record) throws Exception {
			// 保存对账主记录
			financeBankMapper.insertRepaymentReconciliation(record.getRepaymentReconciliation());
			int recordPid = record.getRepaymentReconciliation().getPid(); // 获取对账记录主键 
			List<RepaymentReconciliationDetailDTO> detailList = record.getDetaiList(); // 对应的细节
			for (RepaymentReconciliationDetailDTO dto : detailList) {
				// 如果对账金额为0,不记录对应的记录
				if(dto.getReconciliationAmt()==0)
				{
					continue;
				}	
				
				// 如果是费用减免，保存对应的费用减免记录
				if(dto.getDetailType() == FinanceTypeEnum.TYPE_100.getType())
				{
					// 费用减免接口： 无效字段,期数,无效字段,利息,其他费用，管理费，逾期罚息，逾期利息     费用减免页面是个负数，所以要去绝对值
					// RepayFeewdtlServiceImpl 在的特殊处理，完全按照RepayFeewdtlServiceImpl中的要求拼接json，如有疑问。
					String feewdel="[{},{typeName:'减免',repaymentId:0,planCycleNum:"+record.getRepaymentReconciliation().getCycleNum()+",shouldOtherCost:0,shouldMangCost:0,shouldInterest:0,overdueFine:0,overdueInterest:"+ Math.abs(dto.getTotalAmt())+"}]";
					repayFeewdtlService.insertLoanFeewdelInfo("对账临时减免逾期利息", feewdel, -1, record.getRepaymentReconciliation().getLoanId());
				   
					// 如果是费用减免不记录到细节中（因为对账时候已经减掉了）
					continue;
				}	
				
				dto.setRepaymentReconciliationId(recordPid); // 设置主记录ID
				// dto.setDescription(description); // 备注，预留，有报表直接展现需要
				try{
					dto.setCreateDate(DateUtil.format(DateUtil.getToDay()));
					dto.setCreateUser(record.getRepaymentReconciliation().getUserId()+"");
				}catch(Exception e){
				}
				financeBankMapper.insertRepaymentReconciliationDetail(dto);
				
				
				// 如果是本金或者提前换款（提前换款也是本金），回填对应的额度
				if(dto.getDetailType() == FinanceTypeEnum.TYPE_30.getType() || dto.getDetailType() == FinanceTypeEnum.TYPE_7.getType())
				{
					addCreditLimitRecord(record.getRepaymentReconciliation().getLoanId(),dto.getReconciliationAmt());
				}
				
			}
			
			// 更新对应的对账单的状态
			Map<String, Integer> paramMap = new HashMap<String, Integer>();
			paramMap.put("pid", record.getRefId());
			paramMap.put("reconciliationType", record.getReconciliationType());
			// 计划还款
			if (record.getType() == FinanceConstant.ACCOUNT_STATEMENT_TYPE_PLAN) {
				financeBankMapper.updateRepaymentPlanReconciliation(paramMap);
			}
			// 即时发生
			else if (record.getType() == FinanceConstant.ACCOUNT_STATEMENT_TYPE_INSTANT) {
				financeBankMapper.updateRealtimePlanReconciliation(paramMap);
			}
		}

		/**
		 *  对账本金，回填对应的额度
		  * @param loandId
		  * @param amt
		  * @author: qiancong.Xian
		  * @date: 2015年7月7日 上午10:23:21
		 */
		private void addCreditLimitRecord(int loandId,double amt) throws Exception
		{
			CreditLimitRecord creditLimitRecord = new CreditLimitRecord();
			creditLimitRecord.setLoanId(loandId);
			creditLimitRecord.setAmt(amt);
			creditLimitRecord.setRequestAmt(amt);
			creditsService.saveCreditInfo(creditLimitRecord);
		}
		
		/**
		 *  获取用于批量款款预处理结果展现的一个贷款中的待还款计划
		 *  @param loanId 贷款ID
		 *  @param receivablesDate 还款日期
		 *  @param receivablesAmt 还款总金额
		 */
		@Override
		public List<BatchRepaymentItem> getBatchRepaymentLoanItemList(int loanId, String receivablesDate, double receivablesAmt){
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("results", OracleTypes.CURSOR);
			param.put("loanId", loanId);
			param.put("currentDate", receivablesDate);
			
			List<BatchRepaymentItem> resultList = new ArrayList<BatchRepaymentItem>();
			// 查询待还的所有计划
			financeBankMapper.getBatchRepaymentLoanItem(param);
			// 查询出来的结果集
			List<BatchRepaymentItem> searchList = (List<BatchRepaymentItem>) param.get("results");
			
			if(null != searchList)
			{
				// 遍历所有的计划
				for(BatchRepaymentItem item:searchList)
				{
					calculateLoanFeew(item);
					item.setItemName(item.getSType()== FinanceConstant.ACCOUNT_STATEMENT_TYPE_PLAN?String.valueOf(item.getFinanceType()):FinanceTypeEnum.getName(item.getFinanceType()));
					// 待收金额小于可对账金额,全部还清
					if(item.getTotalAmt()<=receivablesAmt)
					{
						item.setBalanceAmt(item.getTotalAmt());
						receivablesAmt = receivablesAmt-item.getTotalAmt();
						resultList.add(item);
					}
					// 剩余金额不够还当期的钱,全部付清,不够付的计划就不要了
					else
					{
						item.setBalanceAmt(receivablesAmt);
						item.setUnBalanceAmt(item.getTotalAmt()- item.getBalanceAmt());
						receivablesAmt = 0;
						resultList.add(item);
						break;
					}
				}
			}
			
			// 如果没有查询到要还的还款记录
			if(null == searchList || searchList.size()==0)
			{
				BatchRepaymentItem item = new BatchRepaymentItem();
				item.setLoanId(loanId);
				item.setItemName("--");
				item.setProjectName("--");
				item.setProjectNum("--");
				item.setRemainingAmt(receivablesAmt);
				resultList.add(item);
			}
			// 如果还有可以余额，放到最后一个计划的未对账金额中
			if(receivablesAmt>0)
			{
				resultList.get(resultList.size()-1).setRemainingAmt(receivablesAmt);
			}
			
			return resultList;
		}

		@Override
		public void saveBatchRepayment(List<BatchRepaymentBean> batchRepaymentBeanList,int userId){
			
			logger.info("批量对账：batchRepaymentBeanList.size["+batchRepaymentBeanList.size()+"] userId["+userId+"]");
			for(BatchRepaymentBean bean : batchRepaymentBeanList)
			{
			   saveBatchRepaymentBean(bean, userId);
			}
		}
		
		/**
		 *  保存批量对账中的一个贷款项目
		  * @param bean
		  * @author: qiancong.Xian
		 * @throws TException 
		  * @date: 2015年3月31日 下午7:33:24
		 */
		private String saveBatchRepaymentBean(BatchRepaymentBean bean,int userId)
		{
			double paymentAmount =  bean.getAmount();  // 收款总金额
		    int loanId = bean.getLoanId();  // 贷款ID
			String receiptDate =  bean.getReceiptDate(); // 收款日期
			double availableBalance = bean.getRemainingAmt();  // 对账后剩余金额
			
			// 构建一个财务收款记录
			FinanceReceivablesDTO receivablesDTO = new FinanceReceivablesDTO();
			receivablesDTO.setLoanId(loanId);   // 贷款ID
			receivablesDTO.setPaymentAmount(paymentAmount);  // 收款金额
			receivablesDTO.setAvailableBalance(availableBalance);  // 对账后剩余金额
			receivablesDTO.setPaymentDttm(receiptDate); // 收款日期
			// 对账结果
			receivablesDTO.setReconciliation(availableBalance > 0 ? FinanceConstant.FINANCE_RECEIVABLES_UNUSED : FinanceConstant.FINANCE_RECEIVABLES_FINISH);
			// 保存财务收款
			this.saveFinanceReceivables(receivablesDTO,0,0);
			
			// 查询对应的银行账户
			 LoanBankAccountBean loanBankAccountBean =  financeBankMapper.getLoanBankAccountBean(loanId);
			 
			int loanInterestRecord = loanBankAccountBean.getLoanInterestRecord();
			int loanMgrRecord = loanBankAccountBean.getLoanMgrRecord();
			int loanOtherFee= loanBankAccountBean.getLoanOtherfee();
			
			// 当前贷款所有要对账的对账单集合
			List<RepaymentReconciliationBean> repaymentReconciliationBeanList = new ArrayList<RepaymentReconciliationBean>();
			// 获取还款计划集合
			List<BatchRepaymentItem> itemList = bean.getDetails();
			logger.info("批量对账：loanId["+loanId+"]  receiptDate["+receiptDate+"] amount["+bean.getAmount()+"] remainingAmt["+bean.getRemainingAmt()+"]  userId["+userId+"]");
			// 遍历当前贷款下的还款的对账单
		    for(BatchRepaymentItem itemBean:itemList)
		    {
				logger.info("批量对账细节：loanId["+itemBean.getLoanId()+"] projectName["+itemBean.getProjectName()+"] projectNum["+itemBean.getProjectNum()+"] limitDate["+itemBean.getLimitDate()+"] refPid["+itemBean.getRefPid()+"] sType["+itemBean.getSType()+"] itemName["+itemBean.getItemName()+"] financeType["+itemBean.getFinanceType()+"] totalAmt["+itemBean.getTotalAmt()+"] balanceAmt["+itemBean.getBalanceAmt()+"] remainingAmt["+itemBean.getRemainingAmt()+"] overdueDays["+itemBean.getOverdueDays()+"] unBalanceAmt["+itemBean.getUnBalanceAmt()+"]");
		    	// 保存其中一个还款计划（或者一个即时发生的项）
		    	repaymentReconciliationBeanList.add(getReconciliationItem(itemBean,receiptDate,receivablesDTO.getPid(),userId));
		    }	
			
		    // 保存当前项目所有的对账单
			saveBatchRepaymentReconciliation(repaymentReconciliationBeanList, loanInterestRecord, loanMgrRecord, loanOtherFee);
			
			return null;
		}
		
		/**
		 * 保存批量还款中的一个项目下其中一期
		  * @param reconciliationItem
		  * @author: qiancong.Xian
		 * @throws TException 
		  * @date: 2015年3月31日 下午7:51:16
		 */
		private RepaymentReconciliationBean getReconciliationItem(BatchRepaymentItem batchItem,String receiptDate,int receivablesId,int userId)
		{
			
			// 页面传过来的应收
			double  view_receivable = batchItem.getTotalAmt();
			// 页面传过来的实收
			double  view_balanceAmt = batchItem.getBalanceAmt();
			
	    	// 获取当前还款计划（当前期的，或者即时发生的其中一项）
	    	ReconciliationItem reconciliationItem = this.getReconciliationItem(batchItem.getSType(), batchItem.getRefPid(), receiptDate);
	    	// 当前应收  
	    	double now_receivable = reconciliationItem.getInterest()+ reconciliationItem.getInterest_yf()+reconciliationItem.getInterest_yl();
	    	now_receivable += reconciliationItem.getPrincipal() + reconciliationItem.getPrincipal_yf() + reconciliationItem.getPrincipal_yl();
	    	now_receivable += reconciliationItem.getMangCost() + reconciliationItem.getMangCost_yf() +reconciliationItem.getMangCost_yl();
	    	now_receivable+= reconciliationItem.getOtherCost() +reconciliationItem.getOtherCost_yf() +reconciliationItem.getOtherCost_yl();
	    	now_receivable+= reconciliationItem.getRealtimePlan() + reconciliationItem.getRealtimePlan_yf()+reconciliationItem.getRealtimePlan_yl();
	    	// 取小数点后2位
	    	now_receivable = ((int)(now_receivable*100))/100.0;
	    	// 如果页面传过来的应收跟当前的应收不一样的话
	    	if(view_receivable != now_receivable)
	    	{
	    		throw new RuntimeException("当前的应收["+now_receivable+"] 与 前台提交过来的应收["+view_receivable+"]不一致");
	    	}	
	    	
	    	RepaymentReconciliationBean repaymentReconciliationBean = new RepaymentReconciliationBean();
	    	repaymentReconciliationBean.setReconciliationType(view_balanceAmt == view_receivable?1:3); // 1=已对账(全部完成)，2=未对账(未开始)，3=部分对账(部分完成);
	    	repaymentReconciliationBean.setRefId(batchItem.getRefPid()); // 关联表主键（还款计划表或者即时还款计划表）
	    	repaymentReconciliationBean.setType(batchItem.getSType()); // 发生类型 （1:按期、2:即时发生、3:假时减免） 参照FinanceConstant.java
	    
	    	RepaymentReconciliationDTO repaymentReconciliation  = new RepaymentReconciliationDTO(); // 财务对账总记录
	    	repaymentReconciliation.setLoanId(batchItem.getLoanId());  // 贷款ID
	    	repaymentReconciliation.setType(batchItem.getSType()); // 发生类型 （1:按期、2:即时发生、3:假时减免） 参照FinanceConstant..java
	    	repaymentReconciliation.setCycleNum(batchItem.getSType()==1?batchItem.getFinanceType():-1); // 对账还款期数
	    	repaymentReconciliation.setRealtimeId(batchItem.getSType()==1?-1:batchItem.getRefPid()); // 即时发生计划表中对应的 
	    	repaymentReconciliation.setReconciliationAmt(view_balanceAmt); // 对账总金额
	    	repaymentReconciliation.setReconciliationDt(receiptDate); // 还款日期
	    	repaymentReconciliation.setUserId(userId); // 对账人员Id
	    	repaymentReconciliation.setReceivablesId(receivablesId); // 使用的收款记录ID
	    	repaymentReconciliation.setCreateDate(DateUtil.format(DateUtil.getToDay()));
	    	repaymentReconciliation.setCreateUser(userId+"");
	    	
	    	
	    	repaymentReconciliationBean.setRepaymentReconciliation(repaymentReconciliation);
	    	
	    	List<RepaymentReconciliationDetailDTO> detaiList = new ArrayList<RepaymentReconciliationDetailDTO>();    // 财务对账记录细节
	    	repaymentReconciliationBean.setDetaiList(detaiList);
	    	
	    	// 构造对账细节
	    	detaiList.addAll(getRepaymentReconciliationDetailDTOList(reconciliationItem,view_balanceAmt,batchItem));
	    	
	    	return repaymentReconciliationBean;
		}
		
		/**
		 *  构建批量对账中的一期计划下的所有细节（本金、管理费、利息、其他费用以及 对应的逾期利息）
		  * @param reconciliationItem
		  * @param view_balanceAmt 页面用于当前还款计划的实收总金额
		  * @return
		  * @author: qiancong.Xian
		  * @date: 2015年3月31日 下午8:45:25
		 */
		private List<RepaymentReconciliationDetailDTO> getRepaymentReconciliationDetailDTOList(ReconciliationItem reconciliationItem,double view_balanceAmt,BatchRepaymentItem batchItem)
		{
			List<RepaymentReconciliationDetailDTO>  detailList = new ArrayList<RepaymentReconciliationDetailDTO>();
			// 可对账金额
			double availableBalance = view_balanceAmt;
			// 即时发生
			if(batchItem.getSType() == FinanceConstant.ACCOUNT_STATEMENT_TYPE_INSTANT)
			{
				// 即使发生的类型
				availableBalance = addDetailToList(batchItem.getFinanceType(), reconciliationItem.getRealtimePlan(), availableBalance, detailList);
				// 对应的逾期利息
				availableBalance = addDetailToList(FinanceTypeEnum.getOverdueinterestType(batchItem.getFinanceType()), reconciliationItem.getRealtimePlan_yl(), availableBalance, detailList);
				// 对应的逾期罚息
				availableBalance = addDetailToList(FinanceTypeEnum.getOverduePenaltyType(batchItem.getFinanceType()), reconciliationItem.getRealtimePlan_yf(), availableBalance, detailList);

			}	
			// 还款计划的
			else
			{
				// 基本的
				availableBalance = addDetailToList(FinanceTypeEnum.TYPE_30.getType(), reconciliationItem.getPrincipal(), availableBalance, detailList);
				availableBalance = addDetailToList(FinanceTypeEnum.TYPE_40.getType(), reconciliationItem.getMangCost(), availableBalance, detailList);
				availableBalance = addDetailToList(FinanceTypeEnum.TYPE_50.getType(), reconciliationItem.getInterest(), availableBalance, detailList);
				availableBalance = addDetailToList(FinanceTypeEnum.TYPE_60.getType(), reconciliationItem.getOtherCost(), availableBalance, detailList);
				// 对应的逾期利息和罚息
				availableBalance = addDetailToList(FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_30.getType()), reconciliationItem.getPrincipal_yl(), availableBalance, detailList);
				availableBalance = addDetailToList(FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_30.getType()), reconciliationItem.getPrincipal_yf(), availableBalance, detailList);
				
				availableBalance = addDetailToList(FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_40.getType()), reconciliationItem.getMangCost_yl(), availableBalance, detailList);
				availableBalance = addDetailToList(FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_40.getType()), reconciliationItem.getMangCost_yf(), availableBalance, detailList);

				availableBalance = addDetailToList(FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_50.getType()), reconciliationItem.getInterest_yl(), availableBalance, detailList);
				availableBalance = addDetailToList(FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_50.getType()), reconciliationItem.getInterest_yf(), availableBalance, detailList);
				
				availableBalance = addDetailToList(FinanceTypeEnum.getOverdueinterestType(FinanceTypeEnum.TYPE_60.getType()), reconciliationItem.getOtherCost_yl(), availableBalance, detailList);
				availableBalance = addDetailToList(FinanceTypeEnum.getOverduePenaltyType(FinanceTypeEnum.TYPE_60.getType()), reconciliationItem.getOtherCost_yf(), availableBalance, detailList);

			}	
						
			return detailList;
		}
		
		/**
		 * 
		  * @param detailType 细节分类（本金、管理费等）
		  * @param receivableAmt 应收金额
		  * @param availableBalance 可以余额
		  * @param detailList
		  * @return 剩余可用余额
		  * @author: qiancong.Xian
		  * @date: 2015年4月1日 上午9:43:07
		 */
		private double addDetailToList(int detailType, double receivableAmt, double availableBalance,List<RepaymentReconciliationDetailDTO>  detailList)
		{
			// 如果应收=0,或者可以余额=0，直接返回
			if(receivableAmt ==0 || availableBalance==0)
			{
				return availableBalance;
			}	
	        RepaymentReconciliationDetailDTO detailDTO = new RepaymentReconciliationDetailDTO();
			detailDTO.setDetailType(detailType); // 对应的类型
			// 如果应收大于可用，收款=可用余额
			detailDTO.setReconciliationAmt(receivableAmt>availableBalance?availableBalance:receivableAmt);
			detailDTO.setDetailTypeName(FinanceTypeEnum.getName(detailDTO.getDetailType()));
			detailList.add(detailDTO);
			return receivableAmt>availableBalance?0:(availableBalance-receivableAmt);
		}

		@Override
		public LoanBaseDataBean getLoanBaseDataBean(int projectId) throws TException {

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("results", OracleTypes.CURSOR);
			param.put("projectId", projectId);
			
			// 查询待还的所有计划
			financeBankMapper.getLoanBaseDataBean(param);
			// 查询出来的结果集
			List<LoanBaseDataBean> searchList = (List<LoanBaseDataBean>) param.get("results");
			
			return null == searchList? null: searchList.get(0);
		}

		@Override
		public FinanceReceivablesDTO getFinanceReceivables(int financeReceivablesId) throws TException {
			FinanceReceivablesDTO financeReceivablesDTO=financeBankMapper.getFinanceReceivables(financeReceivablesId);
			 return financeReceivablesDTO!=null?financeReceivablesDTO:new FinanceReceivablesDTO();
		}
		
		@Override
		public List<BadDebtDataBean> getBadDebtDataBean(int loanId) throws TException {
			return financeBankMapper.getBadDebtDataBean(loanId);
		}
		
		@Override
		public int activateTheLatestPlan(int loanId) throws TException {

		financeBankMapper.activateRealtimePlan(loanId);
		return financeBankMapper.activateRepaymentPlan(loanId);
		
		}
		
		@Override
		public double getOverdueByDate(int loanId, int p_pid, String cDate) throws TException {
			
			Map<String,Object>  params = new HashMap<String,Object>();
			params.put("pid", p_pid);
			params.put("loanId", loanId);
			params.put("currentDate", cDate);
			
			if(p_pid>0)
			{
				return financeBankMapper.getPlanOverdueByDate(params);
			}
			else
			{
				return financeBankMapper.getIMMOverdueByDate(params);
			}	
		}
		
		
		// add by qcxian
		// ================================================================================================================

	/**
	 * 
	 * @Description:查询 项目的未还贷款本金      应收费用总额：         未收费用总额  逾期未还款项                逾期未还款项总额:         逾期违约金总额：
	 * @param projectId
	 * @return
	 * @throws ThriftServiceException
	 * @author: gaoWen
	 * @date: 2015年4月8日 上午11:10:51
	 */
	@Override
	public CustArrearsView getCustArrearsbyProjectView(int projectId) throws ThriftServiceException {
		   DecimalFormat df = new DecimalFormat("#.00");
		try {
			double receiveTotalAmt = 0.0;// 已收小计
			double noReceiveTotalAmt = 0.0;// 未收合计

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("results", OracleTypes.CURSOR);
			param.put("projectId", projectId);
			param.put("currentDate", DateUtil.fmtDateToStr(new Date(), "yyyy-MM-dd"));
			financeBankMapper.getProGetCustProjectDetail(param);
			List<CustArrearsView> paramList = (List<CustArrearsView>) param.get("results");
			CustArrearsView custAView = paramList.get(0);
			CustArrearsView custArrearsView=new CustArrearsView();
			if (custAView != null) {
				
				// 已收小计
				receiveTotalAmt = custAView.getReceivedPrincipal() + custAView.getReceivedInterest() + custAView.getReceivedMangCost() + custAView.getReceivedOtherCost() + custAView.getReceivedExpireInterest() + custAView.getReceivedOverduePenalty();
				custAView.setReceiveTotalAmt(receiveTotalAmt);
			 
				// 到期未收费用总额
				noReceiveTotalAmt = custAView.getUnReceivedPrincipal() + custAView.getUnReceivedInterest() + custAView.getUnReceivedMangCost() + custAView.getUnReceivedOtherCost() + custAView.getUnReceivedOverdueInterest() + custAView.getUnReceivedOverduePunitive()+ custAView.getNoReceiveTotalAmt_im();
				// custAView.setExpireNoReceiveTotalAmt(expireNoReceiveTotalAmt);
				custAView.setNoReceiveTotalAmt(noReceiveTotalAmt);
			}
			//转化科学技术法和改变小数点位数
			custArrearsView=custAView;
			custArrearsView.setUnReceivedOverduePunitive(Double.parseDouble(df.format(Double.parseDouble(new BigDecimal(custAView.getUnReceivedOverduePunitive()).toPlainString()))));
			custArrearsView.setUnReceivedOverdueInterest(Double.parseDouble(df.format(Double.parseDouble(new BigDecimal(custAView.getUnReceivedOverdueInterest()).toPlainString()))));
			custArrearsView.setNoReceiveTotalAmt(Double.parseDouble(df.format(Double.parseDouble(new BigDecimal(custAView.getNoReceiveTotalAmt()).toPlainString()))));
			custArrearsView.setUnReceivedPrincipal(Double.parseDouble(df.format(Double.parseDouble(new BigDecimal(custAView.getUnReceivedPrincipal()).toPlainString()))));
			custArrearsView.setReceivablePrincipal(Double.parseDouble(df.format(Double.parseDouble(new BigDecimal(custAView.getReceivablePrincipal()).toPlainString()))));
			return custArrearsView;
		} catch (Exception e) {
			logger.error("查询 项目的未还贷款本金      应收费用总额：         未收费用总额  逾期未还款项                逾期未还款项总额:         逾期违约金总额保存：", e);
			throw new ThriftServiceException(ExceptionCode.FINANCE_CUS_BUSINESS_QUERY, "财务管理-查询欠款明细列表业务失败！");
		}
	}

	@Override
	public boolean deleteLoanInputDate(int pid) throws TException {
		return financeBankMapper.deleteLoanInputDate(pid)>0;
	}

	/**
	 * 获取年月份格式
	  * @param queryDate
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午8:23:27
	 */
	private String getMonth(String queryDate)
	{
		//  标准的日期格式 2015-07-06  ， 返回201507
		try
		{
			return queryDate.substring(0, 7).replaceAll("[-\\s:]","");
		}
		catch(Exception e)
		{
			return queryDate;
		}
	}
	
	// 任务的队列
	private java.util.Queue<String> taskQueue = new LinkedList<String>();
	//  任务是否运行中
	private boolean running= false; 
	
	@Override
	public void addFinanceMonthlyReport(String startDate, String endDate) throws TException
	{
		String key = startDate+"@"+endDate;
		
		// 如果队列中已经有了
		if(taskQueue.contains(key))
		{
			return;
		}	
		// 添加到队列中 
		taskQueue.add(key);
		if(running)
		{
			return;
		}	
		// 标示正在运行
		running = true;
		
		// 启动线程来处理
		new Thread(new Runnable() {
			@Override
			public void run() {
				
				String value = taskQueue.poll();
				while(null != value)
				{
					String startDate= value.split("@")[0];
					String endDate= value.split("@")[1];
					try
					{
						doAddFinanceMonthlyReport(startDate, endDate);
					}
					catch(Exception e)
					{
						logger.error("财务应收月报表计算失败startDate["+startDate+"] endDate["+endDate+"]", e);
					}
					value = taskQueue.poll();
				}	
				
				running = false;
			}
		}).start();
	}
	
	private void doAddFinanceMonthlyReport(String startDate, String endDate){
		
		logger.info("开始统计计算月报表行数据startDate["+startDate+"] endDate["+endDate+"]");
		
		// 获取计划表的基础数据（结束时间之前的所有有效的记录）,由于计算还款计划表的利息等费用，当月费用可能会跨月，所以要把下个月的数据一并查询出来
		List<MonthlyReportBasePlan> basePlanList =  financeBankMapper.getMonthlyReportBasePlan(getNextMonth(endDate)+" 23:59:59");
		// 补齐相关月份数据
		polishBasePlanList(basePlanList,startDate,endDate);
		
		
		// 获取即时发生的基础时间（结束时间之前的所有有效的记录）
		List<MonthlyReportBasePlanIm> basePlanImList =  financeBankMapper.getMonthlyReportBasePlanIm(endDate+" 23:59:59");
		// 月报表生成是所有记录Map
		Map<String,MonthlyReportRecord> monthlyReportRecordMap = new HashMap<String,MonthlyReportRecord>();
		
		// 处理还款计划表中的数据
		dealWithBasePlanList(basePlanList, monthlyReportRecordMap, startDate,  endDate);
		// 处理即时发生表中的数据
		dealWithBasebasePlanImList(basePlanImList, monthlyReportRecordMap, startDate,  endDate);
		
		// 保存对应的记录
		saveMonthlyReportRecordMap(monthlyReportRecordMap,startDate,  endDate);
	}
	
	/**
	 *  由于查询月份在计划表中刚好没有计划数据，但是如果是后置付息的，当月数据在下一个月份当中，所以需要虚构一个当月份记录来存放计算的数据
	 *  如：查询的是5月份的数据，但是第一期收利在6月份，后置付息，数据库中是没有5月份的数据
	  * @param basePlanList
	  * @param startDate
	  * @param endDate
	  * @author: qiancong.Xian
	  * @date: 2015年7月28日 下午6:06:48
	 */
	private void polishBasePlanList(List<MonthlyReportBasePlan> basePlanList,String startDate,String endDate)
	{
		List<MonthlyReportBasePlan> resultList = new ArrayList<MonthlyReportBasePlan>();
		// 前置付息，默认是非前置付息
		boolean preposition = false;
		int loanId = -1;
		// 遍历所有的计划
		for(int i=0;i<basePlanList.size();i++)
		{
			
			MonthlyReportBasePlan cplan = basePlanList.get(i);
			if(cplan.getLoanId()== 10484)
			{
				System.out.print(1);
			}	
			
			// 另外一个项目了
			if(cplan.getLoanId() != loanId)
			{
				loanId = cplan.getLoanId();
				// 如果是从第0期开始的，就是前置付息
				preposition = cplan.getCycleNmu()==0;
				cplan.setPlanOutDt(cplan.getPlanOutDt().split(" ")[0]);
				
				// 如果是后置付息 并且当前要统计上一个月的数据(由于当前项目是以当前统计月份的下一个开始的，当前统计月份肯定在还款计划中没有记录)
				// 手动制作一个当前月份的记录来存放数据
				//if(!preposition && isTheNextMonthDate(endDate, cplan.getRepayDt()))
				// 放款日在统计月份之前或者是放款统计月份中
				if(!preposition && (isBetweenTime(cplan.getPlanOutDt(), startDate, endDate) || isAfterAndDate(startDate, cplan.getPlanOutDt())))
				{
					MonthlyReportBasePlan addPlan = new MonthlyReportBasePlan();
					addPlan.setLoanId(loanId);
					addPlan.setCycleNmu(-1);
					// 使用放款日作为还款期限
					addPlan.setRepayDt(cplan.getPlanOutDt());
					addPlan.setPlanOutDt(cplan.getPlanOutDt());
					resultList.add(addPlan);
				}	
			}	
			
			resultList.add(cplan);
		}	
		
		basePlanList.clear();
		basePlanList.addAll(resultList);
	}
	
	private boolean isTheNextMonthDate(String endDate,String cplanDate)
	{
		try
		{
			// 统计月份的下一个月(如果到月份部分的一样的，就ok)
			String nextMonth = getNextMonth(endDate);
			if(nextMonth.substring(0, 7).equals(cplanDate.substring(0, 7)))
			{
				return true;
			}
		}
		catch(Exception e)
		{
			logger.error("判断当前统计的月份是否是一个统一的月份前失败 endDate["+endDate+"] cplanDate["+cplanDate+"]");
		}
	
		return false;
	}
	
	
	/**
	 *  处理费用减免的记录
	  * @param monthlyReportRecordMap
	  * @author: qiancong.Xian
	  * @date: 2015年7月14日 上午10:54:59
	 */
	private void dealWithCostReduction(Map<String,MonthlyReportRecord> monthlyReportRecordMap,Map<String,CostReduction> costReductionMap)
	{
		// 遍历所有记录
		for (MonthlyReportRecord monthlyReportRecord : monthlyReportRecordMap.values()) {
			// 查询该项目的所有有效的费用减免记录
			List<CostReduction> list = financeBankMapper.getCostReductions(monthlyReportRecord.getLoanId());
	        if(null != list && list.size()>0)
	        {
	        	for(CostReduction costReduction:list)
	        	{
	        		/*// 如果是当前周期的费用减免记录
	        		if(isBetweenTime(costReduction.getRepayDt(), monthlyReportRecord.getStartDate(), monthlyReportRecord.getEndDate()))
	        		{
	        			//  对应的想，扣除费用减免的记录
	        			monthlyReportRecord.setInterest(monthlyReportRecord.getInterest()- costReduction.getInterest());
	        			monthlyReportRecord.setMangCost(monthlyReportRecord.getMangCost()-costReduction.getMangCost());
	        			monthlyReportRecord.setOtherCost(monthlyReportRecord.getOtherCost() - costReduction.getOtherCost());
	        			monthlyReportRecord.setTheRestCost(monthlyReportRecord.getTheRestCost()- costReduction.getOverdueInterest() - costReduction.getOverduePunitive());
	        		
						String context = monthlyReportRecord.getDetail().getContent();
						context = context+"(-)costReduction pid["+costReduction.getPId()+"]cycleNum["+costReduction.getCycleNum()+"]Interest["+costReduction.getInterest()+"]MangCost["+costReduction.getMangCost()+"]OtherCost["+costReduction.getOtherCost()+"]"+"OverdueInterest["+costReduction.getOverdueInterest()+"]OverduePunitive["+costReduction.getOverduePunitive()+"]\n";
						monthlyReportRecord.getDetail().setContent(context);
	        		}*/
	        		
	        		// 先记录起来
	        		costReductionMap.put(monthlyReportRecord.getLoanId()+"@"+costReduction.getCycleNum(), costReduction);
	        	}
	        }	
		}
	}
	
	/**
	  * 保存对应的记录
	  * @param monthlyReportRecordMap
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午8:35:36
	 */
	private void saveMonthlyReportRecordMap(Map<String,MonthlyReportRecord> monthlyReportRecordMap,String startDate, String endDate)
	{
		MonthlyReportRecord condition = new MonthlyReportRecord();
		
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		List<MonthlyReportBasePlanIm> list = financeBankMapper.getChangeIntLoanId(paramMap);
		// 存放变更的loanId记录
		List<String> recordList = new ArrayList<String>();
		if(null != list)
		{
			for(MonthlyReportBasePlanIm bean:list)
			{
				recordList.add(String.valueOf(bean.getLoanId()));
			}
		}	

		// 遍历添加利率变更备注
		for (MonthlyReportRecord monthlyReportRecord : monthlyReportRecordMap.values()) {
			// 有效的利率变更记录
			if(recordList.contains(String.valueOf(monthlyReportRecord.getLoanId())))
			{ 
				if(!monthlyReportRecord.getMark().contains("["+monthlyReportRecord.getMonth()+"有利率变更]"))
				{
					monthlyReportRecord.setMark(monthlyReportRecord.getMark()+"["+monthlyReportRecord.getMonth()+"有利率变更]");
				}	
			}
		}
		
		// 遍历保存
		for (MonthlyReportRecord monthlyReportRecord : monthlyReportRecordMap.values()) {
			

			if(monthlyReportRecord.getLoanId()== 10484)
			{
				System.out.print(1);
			}	
			
			monthlyReportRecord.setTotalCost(monthlyReportRecord.getInterest()+monthlyReportRecord.getMangCost()+monthlyReportRecord.getOtherCost()+monthlyReportRecord.getTheRestCost());
			
			//  如果没有数据，跳过
			if(monthlyReportRecord.getTotalCost()==0)
			{  
				continue;
			}	
			
			// 查询当前项目以前的所有记录
			condition.setLoanId(monthlyReportRecord.getLoanId());
			condition.setEndDate(monthlyReportRecord.getEndDate());
			
			List<MonthlyReportRecord> beforeRecordList = financeBankMapper.getMonthlyReportRecords(condition);
			if(null != beforeRecordList && beforeRecordList.size()>0)
			{
			    // 扣除历史的记录
				for(MonthlyReportRecord beforeRecord : beforeRecordList)
				{
					// 备注
					if(null != beforeRecord.getMark() && beforeRecord.getMark().trim().length()>0)
					{
						monthlyReportRecord.setMark(beforeRecord.getMark()+monthlyReportRecord.getMark());
						// 避免重复，去重
						if(null != monthlyReportRecord.getMark())
						{
							String [] marks = monthlyReportRecord.getMark().split("]");
							monthlyReportRecord.setMark("");
							for(String m:marks)
							{
								if(!monthlyReportRecord.getMark().contains(m))
								{
									monthlyReportRecord.setMark(monthlyReportRecord.getMark()+m+"]");
								}
							}	
						}	
					}	
					
					// 如果是相同月份的数据,只更新状态，其他，需要减掉以前的应收
					if(beforeRecord.getMonth().equals(monthlyReportRecord.getMonth()))
					{
						monthlyReportRecord.setStatus(beforeRecord.getStatus());
					}	
					else
					{
						
						monthlyReportRecord.setInterest(monthlyReportRecord.getInterest()-beforeRecord.getInterest());
						monthlyReportRecord.setMangCost(monthlyReportRecord.getMangCost()-beforeRecord.getMangCost());
						monthlyReportRecord.setOtherCost(monthlyReportRecord.getOtherCost()-beforeRecord.getOtherCost());
						monthlyReportRecord.setTheRestCost(monthlyReportRecord.getTheRestCost()-beforeRecord.getTheRestCost());
						monthlyReportRecord.setTotalCost(monthlyReportRecord.getTotalCost()-beforeRecord.getTotalCost());
						
						String context = monthlyReportRecord.getDetail().getContent();
						context = context+"(-)HistoricalRecords month["+beforeRecord.getMonth()+"]pid["+beforeRecord.getPid()+"]StartDate["+beforeRecord.getStartDate()+"]EndDate["+beforeRecord.getEndDate()+"]"+"Interest["+beforeRecord.getInterest()+"]MangCost["+beforeRecord.getMangCost()+"]OtherCost["+beforeRecord.getOtherCost()+"]TheRestCost["+beforeRecord.getTheRestCost()+"]TotalCost["+beforeRecord.getTotalCost()+"]\n";
						monthlyReportRecord.getDetail().setContent(context);
					}	
				}
			}	
			
		//  如果没有数据，跳过
			if(monthlyReportRecord.getTotalCost()==0)
			{
				continue;
			}	
			
			// 数据未锁定，如果锁定了，维持原样不变
			if(monthlyReportRecord.getStatus()==0)
			{
				// 删除旧的计算细节数据
				financeBankMapper.deleteMonthlyReportRecordsDetail(monthlyReportRecord);
				// 先删除就的数据，再新增
				financeBankMapper.deleteMonthlyReportRecords(monthlyReportRecord);
				try
				{
				financeBankMapper.addMonthlyReportRecord(monthlyReportRecord);
				}
				catch(Exception e)
				{
					logger.error("",e);
				}
				
				monthlyReportRecord.getDetail().setMonthlyReportRecordId(monthlyReportRecord.getPid());
				// 设置计算细节明显
				splitMonthlyReportRecordContent(monthlyReportRecord.getDetail().getContent(), monthlyReportRecord);
				// 保存计算细节数据
				financeBankMapper.addMonthlyReportRecordCalculateDetail(monthlyReportRecord.getDetail());
			}	
		}
	}
	
	@Override
	public void saveMonthlyReportRecord(MonthlyReportRecord monthlyReportRecord) throws TException {
		
		monthlyReportRecord.setTotalCost(monthlyReportRecord.getInterest()+monthlyReportRecord.getMangCost()+monthlyReportRecord.otherCost+monthlyReportRecord.theRestCost);
		
		// 删除旧的计算细节数据
		financeBankMapper.deleteMonthlyReportRecordsDetail(monthlyReportRecord);
		// 先删除就的数据，再新增
		financeBankMapper.deleteMonthlyReportRecords(monthlyReportRecord);
		financeBankMapper.addMonthlyReportRecord(monthlyReportRecord);
		
		// 细节对象
		MonthlyReportRecordCalculateDetail detail = new MonthlyReportRecordCalculateDetail();
		detail.setMonthlyReportRecordId(monthlyReportRecord.getPid());
		detail.setLoanId(monthlyReportRecord.getLoanId());
		detail.setMonth(monthlyReportRecord.getMonth());
		detail.setStartDate(monthlyReportRecord.getStartDate());
		detail.setEndDate(monthlyReportRecord.getEndDate());
		detail.setLoanId(monthlyReportRecord.getLoanId());
		detail.setContent("手动设置");
		detail.setContent1("\n");
					
		// 保存计算细节数据
		financeBankMapper.addMonthlyReportRecordCalculateDetail(detail);
	}
	
	
	private void splitMonthlyReportRecordContent(String content,MonthlyReportRecord monthlyReportRecord)
	{
		// 如果过长，分为2部分
		if(content.length()>3995)
		{
			monthlyReportRecord.getDetail().setContent(content.substring(0, 3995));

           // 避免超长，多余的截掉，免得保存失败影响逻辑
			int end = content.length()>7990?7990:content.length();
			monthlyReportRecord.getDetail().setContent1(content.substring(3995, end));
		}
		else
		{
			monthlyReportRecord.getDetail().setContent1("\n");
		}
	}
	
	/**
	 * 重新计算还款计划表中的数据
	  *  由于还款计划表中的当前应收可能跨月，计算当前月的费用由可能涉及到2个周期（如：    1期应收期限  20150615  2期应收期限 20150715  如果是后置付息 ，计算6月份的应收是： 1期的后部分 +2期的前部分（也就是说：2期里面的费用，有一部分是6月份产生的，另一部分是7月产生的） ）
	  *  当前方法的作用，按照还款期限，来计算当月的费用（如：后置付息 还款款期限为（第6期） 20150612，计算为201506月份， 应收1000，后一期（第7期）：20150712，应收是800
	  *  计算当前期：201506，应收为：5期-6期月份共31天，6期-7期月份共30天，6月份占了6期中的 12/31，占了7期的18/30，应收修改为：1000*12/31+800*18/31）
	  *  
	  * @param basePlanLis
	  * @author: qiancong.Xian
	  * @date: 2015年7月17日 上午9:59:34
	 */
	private void reSetBasePlanListData(List<MonthlyReportBasePlan> basePlanList,String startDate, String endDate)
	{
		if(null == basePlanList || basePlanList.size()==0)
		{
			return;
		}
		
		
		// 前置付息，默认是非前置付息
		boolean preposition = false;
		int loanId = -1;
		// 遍历所有的计划
		for(int i=0;i<basePlanList.size();i++)
		{
			MonthlyReportBasePlan cplan = basePlanList.get(i);
			// 另外一个项目了
			if(cplan.getLoanId() != loanId)
			{
				loanId = cplan.getLoanId();
				// 如果是从第0期开始的，就是前置付息
				preposition = cplan.getCycleNmu()==0;
			}	
			// 获取当前计划的 上一期
			MonthlyReportBasePlan plan_last = i>0?basePlanList.get(i-1):null;
			// 获取当前计划的 下一期
			MonthlyReportBasePlan plan_next = i<(basePlanList.size()-1)?basePlanList.get(i+1):null;
			// 如果loanId不一样，就是空的
			plan_last = (null != plan_last && plan_last.getLoanId()== loanId)?plan_last:null;
			plan_next = (null != plan_next && plan_next.getLoanId()== loanId)?plan_next:null;
			
			// 重算还款计划表中的数据
			reSetBasePlanListData(cplan, plan_last, plan_next, preposition, startDate, endDate);
		}	
		
		// 计算过后，把大于当前月份的记录去除
		List<MonthlyReportBasePlan> basePlanList_new = new ArrayList<MonthlyReportBasePlan>();
		long endDatelong = Long.valueOf(endDate.replaceAll("[-\\s:]","")); 
		for(MonthlyReportBasePlan plan : basePlanList)
		{
			if(plan.getLoanId()== 10484)
			{
				System.out.print(1);
			}	
			
			long repayDtlong = Long.valueOf(plan.getRepayDt().split(" ")[0].replaceAll("[-\\s:]",""));
			if(repayDtlong>endDatelong)
			{ 
				continue;
			}	
			
			basePlanList_new.add(plan);
		}	
		
		basePlanList.clear();
		basePlanList.addAll(basePlanList_new);
	}
	
	/**
	 * 重算还款计划表中的数据
	  * @param cplan
	  * @param plan_last
	  * @param plan_next
	  * @param preposition
	  * @author: qiancong.Xian
	  * @date: 2015年7月17日 上午11:21:53
	 */
	private void reSetBasePlanListData(MonthlyReportBasePlan cplan,MonthlyReportBasePlan plan_last,MonthlyReportBasePlan plan_next,boolean preposition,String startDate, String endDate)
	{
		cplan.setCurrentMonth(1);
		cplan.setPreposition(preposition);
		
		// 记录原始数据
		cplan.setO_interest(cplan.getInterest());
		cplan.setO_mangCost(cplan.getMangCost());
		cplan.setO_otherCost(cplan.getOtherCost());
		
		if(cplan.getLoanId()== 10484)
		{
			System.out.print(1);
		}
		
		// 记录总数
		cplan.setT_interest(cplan.getT_interest()+cplan.getO_interest());
		cplan.setT_mangCost(cplan.getT_mangCost()+cplan.getO_mangCost());
		cplan.setT_otherCost(cplan.getT_otherCost()+cplan.getO_otherCost());
					
		// 下一期为空，最后一期
		if(null == plan_next)
		{
			cplan.setIsLastPlan(true);
		}
		else
		{
			// 记录总数
			plan_next.setT_interest(cplan.getT_interest());
			plan_next.setT_mangCost(cplan.getT_mangCost());
			plan_next.setT_otherCost(cplan.getT_otherCost());
		}
		
		// 如果是当前统计月份之后的数据，不需要管了
		if(isAfterAndDate(cplan.getRepayDt(), endDate))
		{
			return;
		}	
		
		//  如果不是第一期的话，不重新这句
		if(null != plan_last)
		{
			return;
		}	
		
		
		// 如果是前置付息，并且下一个周期为空（当前是最后一个周期了），或者下一个周期也在当前计算的月份内，当前应收的全部都属于当月
	/*	if(preposition && null == plan_next)
		//if(preposition && (null == plan_next || isBetweenTime(plan_next.getRepayDt(), startDate, endDate)))
		{
			return;
		}	*/
	
		// 由于后置付息中，当前统计的月份不是第一个周期（手动构造出来的）当前统计的数据有一部落在下一个月中，如统计的是5月份，但是6月份是第一个还款期，所以当前这个判断不成立，需要屏蔽
	/*	// 如果是后置付息，并且下上一个周期为空（当前是第一个周期），或者上一个周期也在当前计算的月份内，当前应收的全部都属于当月
		if(!preposition && (null ==plan_last || isBetweenTime(plan_last.getRepayDt(), startDate, endDate)))
		{
			return;
		}	
		*/
	
		
		int all =1;
		double part1 = 1;
		double part2 = 1;
		
		// 如果是前置付息
		if(preposition)
		{
			// 如果下一个月有记录的话，需要从本月的费用分一部分到下个月，否则都是当月的奋勇
			if(null != plan_next)
			{
				//  这一部分的是本月的所占部分（如：本期期限是1月份18号，是  1月18号-1月31号的费用）
				
				// 前置付息跟下一个周期日期比较
				 all = getBeteewData(cplan.getRepayDt(), plan_next.getRepayDt());
				//  下一个月的月初到下一个的期限
				part2 = getBeteewData(plan_next.getRepayDt().substring(0, plan_next.getRepayDt().length()-2)+"01", plan_next.getRepayDt())+1;
				part1 = all-part2;// 前面部分
				// 当前计划的应收落在本月的比例
				// 如果下个周期跟当前周期在同一月份
				if(part1<0)
				{
					cplan.setCurrentMonth(1);
				}	
				else
				{
					cplan.setCurrentMonth(all==0?1:part1/all); 
				}
			}	
		
			// 当月应收 = 当前周期的前半部分 
			cplan.setInterest(cplan.getInterest()*cplan.getCurrentMonth());
			cplan.setMangCost(cplan.getMangCost()*cplan.getCurrentMonth());
			cplan.setOtherCost(cplan.getOtherCost()*cplan.getCurrentMonth());
			
	    	//  这一部分的是本月的所占部分（本期期限是1月份18号，，是  1月1号到 1月18号的费用）
			// 如果上个一个周期有数据(需要加上上一个周期的后部分)
			if(null != plan_last)
			{
				// 如果上一个周期不是在当前统计月份
				//if(!isBetweenTime(plan_last.getRepayDt(), startDate, endDate))
				//{
					// 上个月的期限到这个月的期限
					all = getBeteewData(plan_last.getRepayDt(), cplan.getRepayDt());
					//  当前月份所占上一个周期的天数
					part2 = getBeteewData(cplan.getRepayDt().substring(0, cplan.getRepayDt().length()-2)+"01", cplan.getRepayDt())+1;
					part1 = all-part2;// 上个周期的后半部分
				//}
			
				cplan.setLastMonth(all==0?1:part2/all); // 上个周期落在本月的比例
				// 如果上一个周期也在当前周期的相同月份内，不需要从上一个周期拿数据
				cplan.setLastMonth(cplan.getLastMonth()>1?0:cplan.getLastMonth());
				// 当月应收 = 当前周期的前半部分 + 上个周期的后半部分
				cplan.setInterest(cplan.getInterest() + plan_last.getO_interest() * cplan.getLastMonth());
				cplan.setMangCost(cplan.getMangCost() + plan_last.getO_mangCost() * cplan.getLastMonth());
				cplan.setOtherCost(cplan.getOtherCost() + plan_last.getO_otherCost() * cplan.getLastMonth());
			}	
		}	
		//  后置付息
		else
		{
		
			//  有上个周期
			if(plan_last != null)
			{
				// 如果上一个周期不是在当前统计月份（需要把上个周期的数据减掉，如果上个周期在统计月份内，上个周期已经把这一部分数据统计了，不需要重复统计）
				if(!isBetweenTime(plan_last.getRepayDt(), startDate, endDate))
				{
					// 后置付息跟前个周期日期比较
					 all = getBeteewData(plan_last.getRepayDt(),cplan.getRepayDt());
					//  月初到还款期限至
					part1 = getBeteewData(cplan.getRepayDt().substring(0, cplan.getRepayDt().length()-2)+"01", cplan.getRepayDt())+1;
					part2 = all-part1;//后面部分
				    // 当前计划的应收落在本月的比例
					cplan.setCurrentMonth(all==0?1:part1/all);
					// 如果超过1了（上一个周期和本周期在同一个月份内）
					cplan.setCurrentMonth(cplan.getCurrentMonth()>1?1:cplan.getCurrentMonth());
				}
			}	
			// 没有上个周期，当前统计月份是第一个周期，上个周期是虚拟出来的，所以没有记录，但是要计算（按照放款日来计算）
			else
			{
				all = getBeteewData(cplan.getPlanOutDt(),cplan.getRepayDt());
			    //  月初到还款期限至
				part1 = getBeteewData(cplan.getRepayDt().substring(0, cplan.getRepayDt().length()-2)+"01", cplan.getRepayDt())+1;
				part2 = all-part1;//后面部分
			    // 当前计划的应收落在本月的比例
				cplan.setCurrentMonth(all==0?1:(part1/all));
			}
			
			
			// 当月应收 = 当前周期的前半部分 
			cplan.setInterest(cplan.getInterest()*cplan.getCurrentMonth());
			cplan.setMangCost(cplan.getMangCost()*cplan.getCurrentMonth());
			cplan.setOtherCost(cplan.getOtherCost()*cplan.getCurrentMonth());
			// 如果下一个周期有数据(需要加上下一个周期的前部分),并且是 手动添加的数据或者是下个周期在统计月份的下一个月（需要把下个周期的数据拿过来，否则下个周期会自动加到这个周期去了）
			//if(null != plan_next && (cplan.getCycleNmu() == -1 || isTheNextMonthDate(endDate, plan_next.getRepayDt())))
			if(null != plan_next)
			{
				// 这个月的期限 到下一个月时间间隔，就是下一个周期的总天数
				all = getBeteewData(cplan.getRepayDt(),plan_next.getRepayDt());
				//  下一个月的月初，到下一个月的期限（下个月的前部分）
				part2 = getBeteewData(plan_next.getRepayDt().substring(0, plan_next.getRepayDt().length()-2)+"01", plan_next.getRepayDt())+1;
				part1 = all - part2;
				// 如果part1<0,下一个周期也是在同一个月份内
				if(part1<0)
				{
					cplan.setNextNomth(0);
				}	
				else
				{
					cplan.setNextNomth(0== all?1:part1/all); // 下个周期的落在本月的部分比例
					// 如果小于0，（与下一个周期在同一个月份内，不需计算下一周期的数据）
					cplan.setNextNomth(cplan.getNextNomth()<0?0:cplan.getNextNomth());
				}
				
				// 当月应收 = 当前周期的后半部分 + 下个周期的前半部分
				cplan.setInterest(cplan.getInterest() + plan_next.getInterest() * cplan.getNextNomth());
				cplan.setMangCost(cplan.getMangCost() + plan_next.getMangCost() * cplan.getNextNomth());
				cplan.setOtherCost(cplan.getOtherCost() + plan_next.getOtherCost() * cplan.getNextNomth());
				
				// 非虚拟的记录并且如果是要计算的当前月份的数据，登记跨月的部分数据
				if(cplan.getCycleNmu() != -1 && isBetweenTime(cplan.getRepayDt(), startDate, endDate))
				{
					cplan.setCrossMonthInterest(plan_next.getInterest() * cplan.getNextNomth());
					cplan.setCrossMonthMangCost(plan_next.getMangCost() * cplan.getNextNomth());
					cplan.setCrossMonthOtherCost(plan_next.getOtherCost() * cplan.getNextNomth());
				}	
				
			}
		}	
	}
	
	/**
	 * 处理还款计划表中的数据
	  * @param basePlanList
	  * @param monthlyReportRecordMap
	  * @author: qiancong.Xian
	  * @date: 2015年7月6日 下午5:18:16
	 */
	private void dealWithBasePlanList(List<MonthlyReportBasePlan> basePlanList,Map<String,MonthlyReportRecord> monthlyReportRecordMap,String startDate, String endDate)
	{
		if (null == basePlanList || basePlanList.size() == 0) {
			return;
		}
		
		// 先把所有要计算的loaId记录初始化了
		for(MonthlyReportBasePlan record:basePlanList)
		{
			getMonthlyReporRecord(record.getLoanId(), monthlyReportRecordMap, startDate, endDate);
		}	
		
		// 有效的费用减免(key: loanId@cycleNum)
		Map<String,CostReduction> costReductionMap = new HashMap<String,CostReduction>();
		// 获取费用减免的数据
		dealWithCostReduction(monthlyReportRecordMap,costReductionMap);
		// 把费用减免的数据扣除
		for(MonthlyReportBasePlan record:basePlanList)
		{
			MonthlyReportRecord monthlyReportRecord = getMonthlyReporRecord(record.getLoanId(), monthlyReportRecordMap, startDate, endDate);
		//  费用减免记录在 costReductionMap 中的key
			String key = record.getLoanId()+"@"+ record.getCycleNmu();
			// 对应的费用减免记录
			CostReduction costReduction = costReductionMap.get(key);
			// 对应的费用减免
			if(null != costReduction)
			{
				record.setInterest(record.getInterest()- costReduction.interest);
				record.setMangCost(record.getMangCost() - costReduction.mangCost);
				record.setOtherCost(record.getOtherCost() - costReduction.otherCost);
				
				String context = monthlyReportRecord.getDetail().getContent();
				context = context+"(-)costReduction pid["+costReduction.getPId()+"]cycleNum["+costReduction.getCycleNum()+"]Interest["+costReduction.getInterest()+"]MangCost["+costReduction.getMangCost()+"]OtherCost["+costReduction.getOtherCost()+"]"+"OverdueInterest["+costReduction.getOverdueInterest()+"]OverduePunitive["+costReduction.getOverduePunitive()+"]\n";
				monthlyReportRecord.getDetail().setContent(context);
			}	
		}	
		
		// 重新计算应收
		reSetBasePlanListData(basePlanList, startDate, endDate);
		
		for(MonthlyReportBasePlan record:basePlanList)
		{
			dealWithBasePlanRecord(record, monthlyReportRecordMap, startDate,  endDate,costReductionMap);
		}	
	}
	
	private void dealWithBasePlanRecord(MonthlyReportBasePlan record,Map<String,MonthlyReportRecord> monthlyReportRecordMap,String startDate, String endDate,Map<String,CostReduction> costReductionMap)
	{
		if(record.getLoanId()== 10484)
		{
			System.out.print(1);
		}	
		
		
		//  根据loadId来区分
		MonthlyReportRecord monthlyReportRecord = getMonthlyReporRecord(record.getLoanId(), monthlyReportRecordMap, startDate, endDate);
		
		// 如果是最后一期的话，使用计划表中的合计
		if(record.isLastPlan)
		{
			monthlyReportRecord.setInterest(record.getT_interest());
			monthlyReportRecord.setMangCost(record.getT_mangCost());
			monthlyReportRecord.setOtherCost(record.getT_otherCost());
			monthlyReportRecord.setIsLastPlan(record.isLastPlan);
		}	
		else
		{
			monthlyReportRecord.setInterest(monthlyReportRecord.getInterest()+record.getInterest());
			monthlyReportRecord.setMangCost(monthlyReportRecord.getMangCost()+record.getMangCost());
			monthlyReportRecord.setOtherCost(monthlyReportRecord.getOtherCost()+record.getOtherCost());
		}
		
		//monthlyReportRecord.setTakeBackCost(monthlyReportRecord.getTakeBackCost()+record.getPrincipal());
		
		// 当前记录没有实际的金额,并且没有有效的费用减免记录
		if((record.getPrincipal()+ record.getInterest()+record.getMangCost()+record.getOtherCost()) ==0)
		{
			return;
		}	
		
		// 如果当前的记录是要查询的区间内时间
//		if(isBetweenTime(record.getRepayDt(), startDate, endDate))
//		{
		   
		
			//  查询期限日期的逾期
			QueryOverdueReceivablesBean queryOverdueReceivablesBean = new QueryOverdueReceivablesBean();
			queryOverdueReceivablesBean.setPid(record.getPid());
			queryOverdueReceivablesBean.setSearchType(2); // 查询类型 2：还款计划表  1：即时发生还款
			queryOverdueReceivablesBean.setLimtDate(endDate);
			 
			// 不是构造出来的数据
			if(record.getCycleNmu() !=-1)
		    {  
			// 调用存储过程
			 financeBankMapper.queryOverdueReceivablesBean(queryOverdueReceivablesBean);
			 queryOverdueReceivablesBean =queryOverdueReceivablesBean.result.get(0);
			
			// 应收逾期利息和罚息记到 其他中
			monthlyReportRecord.setTheRestCost(monthlyReportRecord.getTheRestCost() + queryOverdueReceivablesBean.getReceivableOverdueInterest() + queryOverdueReceivablesBean.getReceivableOverduePunitive());
		   }
//		}	
			
			// 对于的费用减免记录
			String key = record.getLoanId()+"@"+ record.getCycleNmu();
			CostReduction costReduction = costReductionMap.get(key);
			// 减免的逾期利息和逾期罚息
			if(null != costReduction)
			{
				monthlyReportRecord.setTheRestCost(monthlyReportRecord.getTheRestCost() - costReduction.getOverdueInterest() -  costReduction.getOverduePunitive());
			}	
			
			// 细节过程
			String context = monthlyReportRecord.getDetail().getContent();
			context = context+"(+)repaymentPlan pid["+record.getPid()+"]CycleNmu["+record.getCycleNmu()+"]RepayDt["+record.getRepayDt()+"]Interest["+record.getInterest()+"]MangCost["+record.getMangCost()+"]OtherCost["+record.getOtherCost()+"]OverdueInterest["+queryOverdueReceivablesBean.getReceivableOverdueInterest()+"]OverduePunitive["+queryOverdueReceivablesBean.getReceivableOverduePunitive()+"]\n";
			monthlyReportRecord.getDetail().setContent(context);
	}
	
	/**
	 * 处理还款计划表中的数据
	  * @param basePlanList
	  * @param monthlyReportRecordMap
	  * @author: qiancong.Xian
	  * @date: 2015年7月6日 下午5:18:16
	 */
	private void dealWithBasebasePlanImList(List<MonthlyReportBasePlanIm> basePlanImList,Map<String,MonthlyReportRecord> monthlyReportRecordMap,String startDate, String endDate)
	{
		if (null == basePlanImList || basePlanImList.size() == 0) {
			return;
		}
		
		for(MonthlyReportBasePlanIm record:basePlanImList)
		{
			dealWithBasePlanImRecord(record, monthlyReportRecordMap, startDate,  endDate);
		}	
	}
	
	private void dealWithBasePlanImRecord(MonthlyReportBasePlanIm record,Map<String,MonthlyReportRecord> monthlyReportRecordMap,String startDate, String endDate)
	{
		// 当前记录没有实际的金额
		if(record.getOperCost() ==0)
		{
			return;
		}	
		
		//  根据loadId来区分
		MonthlyReportRecord monthlyReportRecord = getMonthlyReporRecord(record.getLoanId(), monthlyReportRecordMap, startDate, endDate);
	
		// 有提前还款
		if(record.getOpterType()== FinanceTypeEnum.TYPE_7.getType())
		{
			// 当月有退费，登记备注
			if(this.isBetweenTime(record.getRepayDt(), monthlyReportRecord.getStartDate(), monthlyReportRecord.getEndDate()))
			{ 
				if(!monthlyReportRecord.getMark().contains("["+monthlyReportRecord.getMonth()+"有提前还款]"))
				{
					monthlyReportRecord.setMark(monthlyReportRecord.getMark()+"["+monthlyReportRecord.getMonth()+"有提前还款]");
				}	
			}	
		}	
		
		// 坏账应收本金、提前还款（算本金）、坏账损失金额，费用减免   不计算在内
		if(record.getOpterType()== FinanceTypeEnum.TYPE_3.getType() || record.getOpterType()== FinanceTypeEnum.TYPE_7.getType() || record.getOpterType()== FinanceTypeEnum.TYPE_11.getType() || record.getOpterType()== FinanceTypeEnum.TYPE_100.getType())
		{
			  return;
		}	
		
		// 如果是退管理费、退利息、退其他费用（在最后一期扣除）
		if(record.getOpterType()== FinanceTypeEnum.TYPE_4.getType() || record.getOpterType()== FinanceTypeEnum.TYPE_5.getType() || record.getOpterType()== FinanceTypeEnum.TYPE_6.getType())
		{
			
			// 如果是最后一期,扣除相关的退费
			if(monthlyReportRecord.isLastPlan)
			{
				// 退管理费
				if(record.getOpterType()== FinanceTypeEnum.TYPE_4.getType())
				{
					monthlyReportRecord.setMangCost(monthlyReportRecord.getMangCost()+ record.getOperCost());
				}	
				
				// 退其他费用
				if(record.getOpterType()== FinanceTypeEnum.TYPE_5.getType())
				{
					monthlyReportRecord.setOtherCost(monthlyReportRecord.getOtherCost()+ record.getOperCost());
				}	
				
				// 退利息
				if(record.getOpterType()== FinanceTypeEnum.TYPE_6.getType())
				{
					monthlyReportRecord.setInterest(monthlyReportRecord.getInterest()+ record.getOperCost());
				}	
			}	
			
			// 退费不计算在其他费用中
			return;
		}
		
		monthlyReportRecord.setTheRestCost(monthlyReportRecord.getTheRestCost()+record.getOperCost());
		
		// 如果当前的记录是要查询的区间内时间
//		if(isBetweenTime(record.getRepayDt(), startDate, endDate))
//		{
		
			//  查询期限日期的逾期
			QueryOverdueReceivablesBean queryOverdueReceivablesBean = new QueryOverdueReceivablesBean();
			queryOverdueReceivablesBean.setPid(record.getPid());
			queryOverdueReceivablesBean.setSearchType(1); // 查询类型 2：还款计划表  1：即时发生还款
			queryOverdueReceivablesBean.setLimtDate(endDate);
			
			// 调用存储过程
			 financeBankMapper.queryOverdueReceivablesBean(queryOverdueReceivablesBean);
			 queryOverdueReceivablesBean =queryOverdueReceivablesBean.result.get(0);
				
			// 应收逾期利息和罚息记到 其他中
			monthlyReportRecord.setTheRestCost(monthlyReportRecord.getTheRestCost() + queryOverdueReceivablesBean.getReceivableOverdueInterest() + queryOverdueReceivablesBean.getReceivableOverduePunitive());
//		}	
			
			// 细节过程
			String context = monthlyReportRecord.getDetail().getContent();
			context = context+"(+)realtimePlanpid["+record.getPid()+"]OpterType["+record.getOpterType()+"]refId["+record.getRefId()+"]RepayDt["+record.getRepayDt()+"]"+"OperCost["+record.getOperCost()+"]OverdueInterest["+queryOverdueReceivablesBean.getReceivableOverdueInterest()+"]OverduePunitive["+queryOverdueReceivablesBean.getReceivableOverduePunitive()+"]\n";
			monthlyReportRecord.getDetail().setContent(context);
			
	}

	private MonthlyReportRecord getMonthlyReporRecord(int loadId, Map<String, MonthlyReportRecord> monthlyReportRecordMap, String startDate, String endDate) {
		MonthlyReportRecord monthlyReportRecord = monthlyReportRecordMap.get(String.valueOf(loadId));
		if(null == monthlyReportRecord)
		{
			monthlyReportRecord = new MonthlyReportRecord();
			monthlyReportRecordMap.put(String.valueOf(loadId), monthlyReportRecord);
			monthlyReportRecord.setLoanId(loadId);
			monthlyReportRecord.setStartDate(startDate);
			monthlyReportRecord.setEndDate(endDate);
			monthlyReportRecord.setMonth(this.getMonth(startDate));
			monthlyReportRecord.setMark("");
			
			// 细节对象
			MonthlyReportRecordCalculateDetail detail = new MonthlyReportRecordCalculateDetail();
			detail.setMonth(monthlyReportRecord.getMonth());
			detail.setStartDate(monthlyReportRecord.getStartDate());
			detail.setEndDate(monthlyReportRecord.getEndDate());
			detail.setLoanId(monthlyReportRecord.getLoanId());
			detail.setContent("");
			monthlyReportRecord.setDetail(detail);
		}
		return monthlyReportRecord;
	}
	
	/**
	 * 获取下一个月的最后一天
	  * @Description: TODO
	  * @param monthStr
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月17日 上午9:46:11
	 */
	private String getNextMonth(String monthStr)
	{
		int year = this.getIntValue(monthStr.substring(0, 4), 0);
		String smonth = monthStr.substring(5, 7);
		int month = this.getIntValue(smonth, 0) ;
		// 如果是以0开头
		if(smonth.startsWith("0"))
		{
			month = this.getIntValue(smonth.substring(1, smonth.length()), 0) ;
		}
		
		// 如果是12月
		if(month==12)
		{
			year=year+1;
			month = 1;
		}
		else
		{
			month = month+1;
		}
		
		Calendar calendar = Calendar.getInstance();   
		calendar.set(year, month, 0);
		
		int maxDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);   
		java.text.Format formatter2=new java.text.SimpleDateFormat("yyyy-MM-"+maxDay);   
		
		return formatter2.format(calendar.getTime());
	}
	
	private int getIntValue(String str,int defulat)
	{
		try
		{
			return Integer.parseInt(str);
		}
		catch(Exception e)
		{
			
		}
		
		return defulat;
	}
	
	/**
	  * @param smallDate  小一点的日期
	  * @param biggerData  大一点的日期
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月17日 下午2:14:49
	 */
	private int getBeteewData(String smallDate,String biggerData)
	{
		long days = 0L;
		
		String t1 = "2015-02-11";
		String t2 = "2015-03-01";
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
		    Date d1 = df.parse(biggerData);
		    Date d2 = df.parse(smallDate);

		    long diff = d1.getTime() - d2.getTime();
		    days = diff / (1000 * 60 * 60 * 24);
		}
		catch(Exception e)
		{
		}
		
		return (int)days;
	}
	
	
	
	/**
	 * 判断还款期限是否在指定是时间内
	  * @param repayDt
	  * @param startDate
	  * @param endDate
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午3:32:09
	 */
	private boolean isBetweenTime(String repayDt,String startDate, String endDate)
	{
		long repayDtlong = Long.valueOf(repayDt.split(" ")[0].replaceAll("[-\\s:]",""));
		long startDatelong = Long.valueOf(startDate.replaceAll("[-\\s:]","")); 
		long endDatelong = Long.valueOf(endDate.replaceAll("[-\\s:]","")); 
		
		if(repayDtlong>=startDatelong && repayDtlong<=endDatelong)
		{
			return true;
		}	
		
		return false;
	}
	
	/**
	 * 是否是在结束时间之后
	  * @param repayDt
	  * @param endDate
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月29日 下午4:19:45
	 */
	private boolean isAfterAndDate(String repayDt,String endDate)
	{
		 long repayDtlong = Long.valueOf(repayDt.split(" ")[0].replaceAll("[-\\s:]",""));
	     long endDatelong = Long.valueOf(endDate.replaceAll("[-\\s:]","")); 
		
		if(repayDtlong>endDatelong)
		{
			return true;
		}	
		
		return false;
	}
	

	@Override
	public List<MonthlyReportRecord> listMonthlyReportRecords(MonthlyReportRecordCondition condition) throws TException {
		Map map = getListMonthlyPramMap(condition);
		return financeBankMapper.listMonthlyReportRecords(map);
	}
	
	/**
	 * 
	  * @Description: 财务总账查询参数map
	  * @param condition
	  * @return
	  * @author: Zhangyu.Hoo
	  * @date: 2015年7月14日 下午4:58:59
	 */
	private Map getListMonthlyPramMap(MonthlyReportRecordCondition condition) {
		Map map = new HashMap();
		map.put("month", condition.getMonth());
		map.put("projectName", condition.getProjectName());
		map.put("projectNo", condition.getProjectNo());
		map.put("projectManage", condition.getProjectManage());
		if(null!=condition.getAssWay() && !"".equals(condition.getAssWay())){
			map.put("assWay", Arrays.asList(condition.getAssWay().split(",")));
		}else{
			map.put("assWay","");
		}
		map.put("page", condition.getPage());
		map.put("rows", condition.getRows());
		return map;
	}
	
	@Override
	public int listMonthlyReportRecordsTotal(MonthlyReportRecordCondition condition) throws TException {
		Map map = getListMonthlyPramMap(condition);
		return financeBankMapper.listMonthlyReportRecordsTotal(map);
	}
	
	@Override
	public int updateStatus(MonthlyReportRecordCondition condition) throws TException {
		if(condition.getStatus() == 1){
			return financeBankMapper.updateStatusLock(condition.getPids().split(","));
		}else{
			return financeBankMapper.updateStatusNoLock(condition.getPids().split(","));
		}
	}
	
	@Override
	public int deleteMonthlyReportRecordsById(String ids) throws TException {
		financeBankMapper.deleteMonthlyReportRecordsDetailByMonthId(ids.split(","));
		return financeBankMapper.deleteMonthlyReportRecordsById(ids.split(","));
	}

	@Override
	public int checkLoanIdByProjectNo(String projectNo) throws TException {
	
		try
		{
			return financeBankMapper.checkLoanIdByProjectNo(projectNo);
		}
		catch(Exception e)
		{
			logger.error("查询项目Id失败", e);
		}
		return -1;
	}

	//系统平账调用的修改还款计划表的对账状态为已对账
	@Override
	public int updateRepaymentPlan(int redId) throws ThriftServiceException {
		
		try
		{
			// 更新对应的对账单的状态
			Map<String, Integer> paramMap = new HashMap<String, Integer>();
			paramMap.put("pid", redId);
			paramMap.put("reconciliationType",1);
			// 计划还款
			financeBankMapper.updateRepaymentPlanReconciliation(paramMap);
			return 1;
		}
		catch(Exception e)
		{
			
			logger.error("查询项目的到期未收金额失败", e);
			return 0;
		}
		
	}
	

	@Override
	public LoanCycleNumView getLoanCycleNumAndAmt(int loanId) throws TException {
		LoanCycleNumView reuslt=null;
		try
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("loanId", loanId);
			map.put("currentDate", DateUtil.format(new Date(), "yyyy-MM-dd"));
			reuslt= financeBankMapper.getLoanCycleNumAndAmt(map);
			//double planCycleNum=Double.parseDouble(reuslt.get("planCycleNum"));
					
			//double shouldInterest=reuslt.get("overdueAmt");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error("查询项目的部分还款的期数失败", e);
		}
		return reuslt!=null?reuslt:new  LoanCycleNumView();
//		return null;
	}

	
	
	
}
