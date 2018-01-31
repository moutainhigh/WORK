package com.xlkfinance.bms.server.report.service;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.rpc.report.BusinessApprovalBill;
import com.xlkfinance.bms.rpc.report.BusinessApprovalDetail;
import com.xlkfinance.bms.rpc.report.BusinessSummaryReport;
import com.xlkfinance.bms.rpc.report.ChechanReport;
import com.xlkfinance.bms.rpc.report.CollectFeeReport;
import com.xlkfinance.bms.rpc.report.FinancialStatisticsReport;
import com.xlkfinance.bms.rpc.report.ForeclosureCapitalReport;
import com.xlkfinance.bms.rpc.report.ForeclosureOrganizationReport;
import com.xlkfinance.bms.rpc.report.ForeclosureReport;
import com.xlkfinance.bms.rpc.report.HandleDifferWarnReport;
import com.xlkfinance.bms.rpc.report.RefundFeesReport;
import com.xlkfinance.bms.rpc.report.RefuseProjectReport;
import com.xlkfinance.bms.rpc.report.ReportService.Iface;
import com.xlkfinance.bms.rpc.report.TrackRecordReport;
import com.xlkfinance.bms.server.inloan.mapper.FinanceHandleMapper;
import com.xlkfinance.bms.server.report.mapper.ReportMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月1日上午11:14:38 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 报表service<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings("unchecked")
@Service("ReportService")
@ThriftService(service = "com.xlkfinance.bms.rpc.report.ReportService")
public class ReportServiceImpl implements Iface {
   @Resource(name = "reportMapper")
   private ReportMapper reportMapper;
   
   @Resource(name = "financeHandleMapper")
   private FinanceHandleMapper financeHandleMapper;
   /**
    * 预警统计报表（分页查询）
    * page=-1：查询所有
    *@author:liangyanjun
    *@time:2016年3月1日上午11:28:04
    *@param handleDifferWarnReport
    *@return
    */
   @Override
   public List<HandleDifferWarnReport> queryHandleDifferWarnReport(HandleDifferWarnReport handleDifferWarnReport) throws TException {
      return reportMapper.queryHandleDifferWarnReport(handleDifferWarnReport);
   }

   /**
    * 预警统计报总数
    *@author:liangyanjun
    *@time:2016年3月1日上午11:28:08
    *@param handleDifferWarnReport
    *@return
    */
   @Override
   public int getHandleDifferWarnReportTotal(HandleDifferWarnReport handleDifferWarnReport) throws TException {
      return reportMapper.getHandleDifferWarnReportTotal(handleDifferWarnReport);
   }

   @Override
   public List<BusinessSummaryReport> queryBusinessSummary(BusinessSummaryReport businessSummaryReport) throws TException {
      List<BusinessSummaryReport> list = reportMapper.queryBusinessSummary(businessSummaryReport);
      // for (BusinessSummaryReport dto : list) {
      // //根据项目id获取最新的回款信息，如果有第二次回款则返回第二次，没有则返回第一次回款信息
      // ApplyFinanceHandleDTO newRecApplyFinance = financeHandleMapper.getNewRecApplyFinance(dto.getProjectId());
      // if (newRecApplyFinance!=null) {
      // String loanDate = newRecApplyFinance.getRecDate();
      // dto.setLoanDate(loanDate);
      // }
      // }
      return list;
   }

   @Override
   public int getBusinessSummaryTotal(BusinessSummaryReport businessSummaryReport) throws TException {
      return reportMapper.getBusinessSummaryTotal(businessSummaryReport);
   }
   
   @Override
   public List<ChechanReport> queryChechan(ChechanReport chechanReport) throws TException {
      List<ChechanReport> list = reportMapper.queryChechan(chechanReport);
      // for (BusinessSummaryReport dto : list) {
      // //根据项目id获取最新的回款信息，如果有第二次回款则返回第二次，没有则返回第一次回款信息
      // ApplyFinanceHandleDTO newRecApplyFinance = financeHandleMapper.getNewRecApplyFinance(dto.getProjectId());
      // if (newRecApplyFinance!=null) {
      // String loanDate = newRecApplyFinance.getRecDate();
      // dto.setLoanDate(loanDate);
      // }
      // }
      return list;
   }

   @Override
   public int getChechanTotal(ChechanReport chechanReport) throws TException {
      return reportMapper.getChechanTotal(chechanReport);
   }
   
	@Override
	public List<TrackRecordReport> queryTrackRecordReport(
			TrackRecordReport trackRecordReport) throws TException {
		return reportMapper.queryTrackRecordReport(trackRecordReport);
	}
	
	@Override
	public int queryTrackRecordReportTotal(TrackRecordReport trackRecordReport)
			throws TException {
		return reportMapper.queryTrackRecordReportTotal(trackRecordReport);
	}
	
	@Override
	public List<TrackRecordReport> queryTrackRecordReportByIds(String ids)
			throws TException {
			String[] arr = ids.split(",");
		    List list = Lists.newArrayList();
		    for (int i = 0; i < arr.length; i++) {
		       list.add(arr[i]);
		    }
		return reportMapper.queryTrackRecordReportByIds(list);
	}

   @Override
   public List<CollectFeeReport> queryCollectFeeReport(CollectFeeReport collectFeeReport) throws TException {
      return reportMapper.queryCollectFeeReport(collectFeeReport);
   }

   @Override
   public int getCollectFeeReportTotal(CollectFeeReport collectFeeReport) throws TException {
      return reportMapper.getCollectFeeReportTotal(collectFeeReport);
   }

   @Override
   public List<CollectFeeReport> queryCollectFeeReportByIds(String ids) throws TException {
      return reportMapper.queryCollectFeeReportByIds(ids);
   }
   /**
    * 赎楼贷计报表（分页查询）
    * page=-1：查询所有
    *@author:liangyanjun
    *@time:2016年3月1日上午11:28:04
    *@param ForeclosureReport
    *@return
    */
	@Override
	public List<ForeclosureReport> queryForeclosureReport(
			ForeclosureReport foreclosureReport) throws TException {
		return reportMapper.queryForeclosureReport(foreclosureReport);
	}
	
	 /**
	    * 赎楼贷计报表查询总数
	    * page=-1：查询所有
	    *@author:liangyanjun
	    *@time:2016年3月1日上午11:28:04
	    *@param ForeclosureReport
	    *@return
	    */
	@Override
	public int getForeclosureReportTotal(ForeclosureReport foreclosureReport)
			throws TException {
		 return reportMapper.getForeclosureReportTotal(foreclosureReport);
	}
	
	   /**
	    * 财务统计报表（分页查询）
	    * page=-1：查询所有
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param FinancialStatisticsReport
	    *@return
	    */
	@Override
	public List<FinancialStatisticsReport> queryFinancialStatisticsReport(
			FinancialStatisticsReport financialStatisticsReport) throws TException {
		return reportMapper.queryFinancialStatisticsReport(financialStatisticsReport);
	}
	/**
	    * 财务统计报表总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param FinancialStatisticsReport
	    *@return
	    */
	@Override
	public int getFinancialStatisticsReportTotal(
			FinancialStatisticsReport financialStatisticsReport) throws TException {
		 return reportMapper.getFinancialStatisticsReportTotal(financialStatisticsReport);
	}
	
	   /**
	    * 收退咨询费统计报表（分页查询）
	    * page=-1：查询所有
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param RefundFeesReport
	    *@return
	    */
	@Override
	public List<RefundFeesReport> queryRefundFeesReport(
			RefundFeesReport refundFeesReport) throws TException {
		return reportMapper.queryRefundFeesReport(refundFeesReport);
	}
	/**
	    * 收退咨询费统计报表总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param RefundFeesReport
	    *@return
	    */
	@Override
	public int getRefundFeesReportTotal(RefundFeesReport refundFeesReport)
			throws TException {
		return reportMapper.getRefundFeesReportTotal(refundFeesReport);
	}
	
	 /**
	    * 合作机构统计报表（分页查询）
	    * page=-1：查询所有
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param RefundFeesReport
	    *@return
	    */
	@Override
	public List<ForeclosureOrganizationReport> queryForeclosureOrganizationReport(
			ForeclosureOrganizationReport foreclosureOrganizationReport)
			throws TException {
		 return reportMapper.queryForeclosureOrganizationReport(foreclosureOrganizationReport);
	}
	
	 /**
	    * 合作机构统计报表总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param RefundFeesReport
	    *@return
	    */
	@Override
	public int getForeclosureOrganizationReportTotal(
			ForeclosureOrganizationReport foreclosureOrganizationReport)
			throws TException {
		return reportMapper.getForeclosureOrganizationReportTotal(foreclosureOrganizationReport);
	}
	
	 /**
	    * 资金方统计报表（分页查询）
	    * page=-1：查询所有
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param ForeclosureCapitalReport
	    *@return
	    */
	@Override
	public List<ForeclosureCapitalReport> queryForeclosureCapitalReport(
			ForeclosureCapitalReport foreclosureCapitalReport) throws TException {
		 return reportMapper.queryForeclosureCapitalReport(foreclosureCapitalReport);
	}
	 /**
	    * 资金方统计报表总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param ForeclosureCapitalReport
	    *@return
	    */
	@Override
	public int getForeclosureCapitalReportTotal(
			ForeclosureCapitalReport foreclosureCapitalReport) throws TException {
		return reportMapper.getForeclosureCapitalReportTotal(foreclosureCapitalReport);
	}
	
	 /**
	    * 新增计报表（分页查询）
	    * page=-1：查询所有
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param ForeclosureReport
	    *@return
	    */
	@Override
	public List<ForeclosureReport> queryNewForeclosureReport(
			ForeclosureReport foreclosureReport) throws TException {
		return reportMapper.queryNewForeclosureReport(foreclosureReport);
	}
	 /**
	    *新增统计报表总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param ForeclosureReport
	    *@return
	    */
	@Override
	public int getNewForeclosureReportTotal(ForeclosureReport foreclosureReport)
			throws TException {
		 return reportMapper.getNewForeclosureReportTotal(foreclosureReport);
	}
	
	
	 /**
	    * 结清计报表（分页查询）
	    * page=-1：查询所有
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param ForeclosureReport
	    *@return
	    */
	@Override
	public List<ForeclosureReport> querySquareForeclosureReport(
			ForeclosureReport foreclosureReport) throws TException {
		return reportMapper.querySquareForeclosureReport(foreclosureReport);
	}
	 /**
	    *结清统计报表总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param ForeclosureReport
	    *@return
	    */
	@Override
	public int getSquareForeclosureReportTotal(ForeclosureReport foreclosureReport)
			throws TException {
		return reportMapper.getSquareForeclosureReportTotal(foreclosureReport);
	}
	
	/**
	    * 业务审批明报表（分页查询）
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param BusinessApprovalDetail
	    *@return
	    */
	@Override
	public List<BusinessApprovalDetail> queryBusinessApprovalDetail(
			BusinessApprovalDetail businessApprovalDetail) throws TException {
		return reportMapper.queryBusinessApprovalDetail(businessApprovalDetail);
	}
	/**
	    * 业务审批明细计总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param BusinessApprovalDetail
	    *@return
	    */
	@Override
	public int getBusinessApprovalDetailTotal(
			BusinessApprovalDetail businessApprovalDetail) throws TException {
		return reportMapper.getBusinessApprovalDetailTotal(businessApprovalDetail);
	}
	
	/**
	    * 业务审批台账（分页查询）
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param BusinessApprovalDetail
	    *@return
	    */
	@Override
	public List<BusinessApprovalBill> queryBusinessApprovalBill(
			BusinessApprovalBill businessApprovalBill) throws TException {
		return reportMapper.queryBusinessApprovalBill(businessApprovalBill);
	}
	/**
	    * 业务审批台账总数（分页查询）
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param BusinessApprovalDetail
	    *@return
	    */
	@Override
	public int getBusinessApprovalBillTotal(
			BusinessApprovalBill businessApprovalBill) throws TException {
		return reportMapper.getBusinessApprovalBillTotal(businessApprovalBill);
	}

	/**
	    * 业务审批台账业务笔数详情（分页查询）
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param BusinessApprovalDetail
	    *@return
	    */
	@Override
	public List<BusinessApprovalBill> queryBusinessApprovalBillCount(
			BusinessApprovalBill businessApprovalBill) throws TException {
		return reportMapper.queryBusinessApprovalBillCount(businessApprovalBill);
	}
	/**
	    * 业务审批台账业务笔数详情总数
	    *@author:Jony
	    *@time:2016年3月1日上午11:28:04
	    *@param BusinessApprovalDetail
	    *@return
	    */
	@Override
	public int getBusinessApprovalBillCountTotal(
			BusinessApprovalBill businessApprovalBill) throws TException {
		return reportMapper.getBusinessApprovalBillCountTotal(businessApprovalBill);
	}

	@Override
	public List<RefuseProjectReport> queryResuseProjectByPage (RefuseProjectReport query) throws TException
	{
		// 修改申请开始时间格式
		if (null != query.getRefuseTimeStart() && !"".equals(query.getRefuseTimeStart())) {
			String beginString = query.getRefuseTimeStart();
			Date d = DateUtil.format(beginString, "yyyy-MM-dd");
			beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:00").toString();
			query.setRefuseTimeStart(beginString);
		}
		// 修改申请结束时间格式
		if (null != query.getRefuseTimeEnd()&& !"".equals(query.getRefuseTimeEnd())) {
			String endString = query.getRefuseTimeEnd();
			Date d = DateUtil.format(endString, "yyyy-MM-dd");
			endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
			query.setRefuseTimeEnd(endString);
		}
		return reportMapper.queryRefuseProject(query);
	}

	@Override
	public int queryResuseProjectCount (RefuseProjectReport query) throws TException
	{
		// 修改申请开始时间格式
		if (null != query.getRefuseTimeStart() && !"".equals(query.getRefuseTimeStart())) {
			String beginString = query.getRefuseTimeStart();
			Date d = DateUtil.format(beginString, "yyyy-MM-dd");
			beginString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 00:00:00").toString();
			query.setRefuseTimeStart(beginString);
		}
		// 修改申请结束时间格式
		if (null != query.getRefuseTimeEnd()&& !"".equals(query.getRefuseTimeEnd())) {
			String endString = query.getRefuseTimeEnd();
			Date d = DateUtil.format(endString, "yyyy-MM-dd");
			endString = new StringBuffer().append(DateUtil.format(d, "yyyy-MM-dd").trim()).append(" 23:59:59").toString();
			query.setRefuseTimeEnd(endString);
		}
		return reportMapper.queryRefuseProjectTotal(query);
	}
	
	}
