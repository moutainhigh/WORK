package com.xlkfinance.bms.server.report.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
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
import com.xlkfinance.bms.rpc.report.TrackRecordReport;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月1日上午11:14:20 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("reportMapper")
public interface ReportMapper<T, PK> extends BaseMapper<T, PK> {

   /**
    * 预警统计报表（分页查询）
    * page=-1：查询所有
    *@author:liangyanjun
    *@time:2016年3月1日上午11:28:04
    *@param handleDifferWarnReport
    *@return
    */
   List<HandleDifferWarnReport> queryHandleDifferWarnReport(HandleDifferWarnReport handleDifferWarnReport);

   /**
    * 预警统计报总数
    *@author:liangyanjun
    *@time:2016年3月1日上午11:28:08
    *@param handleDifferWarnReport
    *@return
    */
   int getHandleDifferWarnReportTotal(HandleDifferWarnReport handleDifferWarnReport);


   /**
    *@author:liangyanjun
    *@time:2016年3月7日下午2:23:23
    *@param list
    *@return
    */
   List<HandleDifferWarnReport> queryBusinessSummaryByIds(List list);
   /**
    *@author:liangyanjun
    *@time:2016年3月7日下午2:23:28
    *@param businessSummaryReport
    *@return
    */
   int getBusinessSummaryTotal(BusinessSummaryReport businessSummaryReport);

   /**
    *@author:liangyanjun
    *@time:2016年3月7日下午2:24:00
    *@param businessSummaryReport
    *@return
    */
   List<BusinessSummaryReport> queryBusinessSummary(BusinessSummaryReport businessSummaryReport);
   
   /**
    *@author:liangyanjun
    *@time:2016年3月7日下午2:23:28
    *@param chechanReport
    *@return
    */
   int getChechanTotal(ChechanReport chechanReport);

   /**
    *@author:liangyanjun
    *@time:2016年3月7日下午2:24:00
    *@param chechanReport
    *@return
    */
   List<ChechanReport> queryChechan(ChechanReport chechanReport);

   /**
    * 业绩一览分页显示
     * @param trackRecordReport
     * @return
     * @author: baogang
     * @date: 2016年6月20日 下午3:48:54
    */
   List<TrackRecordReport> queryTrackRecordReport(TrackRecordReport trackRecordReport);
   
   /**
    * 业绩一览总数
     * @param trackRecordReport
     * @return
     * @author: baogang
     * @date: 2016年6月20日 下午3:49:00
    */
   int queryTrackRecordReportTotal(TrackRecordReport trackRecordReport);
   
   /**
    * 通过IDS查询业绩一览，用于导出
     * @param ids
     * @return
     * @author: baogang
     * @date: 2016年6月20日 下午3:49:06
    */
   List<TrackRecordReport> queryTrackRecordReportByIds(List ids );

   /**
    *@author:liangyanjun
    *@time:2016年6月21日上午11:04:26
    *@param collectFeeReport
    *@return
    */
   List<CollectFeeReport> queryCollectFeeReport(CollectFeeReport collectFeeReport);

   /**
    *@author:liangyanjun
    *@time:2016年6月21日上午11:04:31
    *@param collectFeeReport
    *@return
    */
   int getCollectFeeReportTotal(CollectFeeReport collectFeeReport);

   /**
    *@author:liangyanjun
    *@time:2016年6月21日上午11:04:38
    *@param ids
    *@return
    */
   List<CollectFeeReport> queryCollectFeeReportByIds(String ids);
   
   /**
    *@author:Jony
    *@time:2016年11月1日上午11:04:26
    *@param foreclosureReport
    *@return
    */
   List<ForeclosureReport> queryForeclosureReport(ForeclosureReport foreclosureReport);

   /**
    *@author:jony
    *@time:2016年11月5日上午11:04:31
    *@param foreclosureReport
    *@return
    */
   int getForeclosureReportTotal(ForeclosureReport foreclosureReport);
   
   /**
    *@author:Jony
    *@time:2016年11月1日上午11:04:26
    *@param foreclosureReport
    *@return
    */
   List<ForeclosureReport> queryNewForeclosureReport(ForeclosureReport foreclosureReport);

   /**
    *@author:jony
    *@time:2016年11月5日上午11:04:31
    *@param foreclosureReport
    *@return
    */
   int getNewForeclosureReportTotal(ForeclosureReport foreclosureReport);
   /**
    *@author:Jony
    *@time:2016年11月1日上午11:04:26
    *@param foreclosureReport
    *@return
    */
   List<ForeclosureReport> querySquareForeclosureReport(ForeclosureReport foreclosureReport);

   /**
    *@author:jony
    *@time:2016年11月5日上午11:04:31
    *@param foreclosureReport
    *@return
    */
   int getSquareForeclosureReportTotal(ForeclosureReport foreclosureReport);

   /**
    *@author:Jony
    *@time:2016年11月1日上午11:04:26
    *@param foreclosureReport
    *@return
    */
   List<ForeclosureOrganizationReport> queryForeclosureOrganizationReport(ForeclosureOrganizationReport foreclosureOrganizationReport);

   /**
    *@author:jony
    *@time:2016年11月5日上午11:04:31
    *@param foreclosureReport
    *@return
    */
   int getForeclosureOrganizationReportTotal(ForeclosureOrganizationReport foreclosureOrganizationReport);

   /**
    *@author:Jony
    *@time:2016年11月1日上午11:04:26
    *@param foreclosureReport
    *@return
    */
   List<ForeclosureCapitalReport> queryForeclosureCapitalReport(ForeclosureCapitalReport foreclosureCapitalReport);

   /**
    *@author:jony
    *@time:2016年11月5日上午11:04:31
    *@param foreclosureReport
    *@return
    */
   int getForeclosureCapitalReportTotal(ForeclosureCapitalReport foreclosureCapitalReport);

   /**
    *@author:Jony
    *@time:2016年11月5日上午11:04:26
    *@param financialStatisticsReport
    *@return
    */
   List<FinancialStatisticsReport> queryFinancialStatisticsReport(FinancialStatisticsReport financialStatisticsReport);

   /**
    *@author:jony
    *@time:2016年11月10日上午11:04:31
    *@param financialStatisticsReport
    *@return
    */
   int getFinancialStatisticsReportTotal(FinancialStatisticsReport financialStatisticsReport);

   /**
    *@author:Jony
    *@time:2016年11月11日上午11:04:26
    *@param refundFeesReport
    *@return
    */
   List<RefundFeesReport> queryRefundFeesReport(RefundFeesReport refundFeesReport);

   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param refundFeesReport
    *@return
    */
   int getRefundFeesReportTotal(RefundFeesReport refundFeesReport);
   /**
    *@author:Jony
    *@time:2016年11月11日上午11:04:26
    *@param BusinessApprovalDetail
    *@return
    */
   List<BusinessApprovalDetail> queryBusinessApprovalDetail(BusinessApprovalDetail businessApprovalDetail);

   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param BusinessApprovalDetail
    *@return
    */
   int getBusinessApprovalDetailTotal(BusinessApprovalDetail businessApprovalDetail);

   /**
    *@author:Jony
    *@time:2016年11月11日上午11:04:26
    *@param BusinessApprovalBill
    *@return
    */
   List<BusinessApprovalBill> queryBusinessApprovalBill(BusinessApprovalBill businessApprovalBill);

   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param BusinessApprovalBill
    *@return
    */
   int getBusinessApprovalBillTotal(BusinessApprovalBill businessApprovalBill);

   /**
    *@author:Jony
    *@time:2016年11月11日上午11:04:26
    *@param BusinessApprovalDetail
    *@return
    */
   List<BusinessApprovalBill> queryBusinessApprovalBillCount(BusinessApprovalBill businessApprovalBill);

   /**
    *@author:jony
    *@time:2016年11月12日上午11:04:31
    *@param BusinessApprovalDetail
    *@return
    */
   int getBusinessApprovalBillCountTotal(BusinessApprovalBill businessApprovalBill);
   
   /**
    *  
	 * queryRefuseProject:分页查询拒单列表 <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param query
	 * @return
	 *
    */
   public List<RefuseProjectReport> queryRefuseProject(RefuseProjectReport query);
   
   /**
    * 
	 * queryRefuseProjectTotal:查询拒单总数 <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param query
	 * @return
	 *
    */
   public int queryRefuseProjectTotal(RefuseProjectReport query);
}
