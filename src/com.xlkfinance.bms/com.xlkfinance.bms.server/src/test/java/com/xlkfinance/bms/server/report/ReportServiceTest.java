/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月12日下午4:55:09 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.xlkfinance.bms.server.report;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.report.BusinessApprovalDetail;
import com.xlkfinance.bms.rpc.report.BusinessSummaryReport;
import com.xlkfinance.bms.rpc.report.CollectFeeReport;
import com.xlkfinance.bms.rpc.report.FinancialStatisticsReport;
import com.xlkfinance.bms.rpc.report.HandleDifferWarnReport;
import com.xlkfinance.bms.rpc.report.ReportService;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月12日下午4:55:09 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class ReportServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private ReportService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("report", "ReportService");
      try {
         client = (ReportService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月1日下午3:03:37
    */
   @Test
   public void test_findAllHandleDifferWarnReport() {
      try {
         List<Integer> userIds = new ArrayList<Integer>();
         userIds.add(20080);
         userIds.add(20040);
         HandleDifferWarnReport dto = new HandleDifferWarnReport();
         // dto.setUserIds(userIds);
         dto.setPage(-1);
         dto.setRows(20);
         // dto.setProjectId(11269);
         // dto.setMortgageDate("2012-06-25");
         // dto.setMortgageEndDate("2019-06-25");
         int total = client.getHandleDifferWarnReportTotal(dto);
         System.out.println(total);
         List<HandleDifferWarnReport> list = client.queryHandleDifferWarnReport(dto);
         for (HandleDifferWarnReport f : list) {
            System.out.println(f);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }


   @Test
   public void test_queryBusinessSummaryReport() {
      try {
         List<Integer> userIds = new ArrayList<Integer>();
         userIds.add(20080);
         userIds.add(20040);
         BusinessSummaryReport dto = new BusinessSummaryReport();
         // dto.setUserIds(userIds);
         dto.setPage(-1);
         dto.setRows(20);
         // dto.setProjectId(11269);
         // dto.setMortgageDate("2012-06-25");
         // dto.setMortgageEndDate("2019-06-25");
         int total = client.getBusinessSummaryTotal(dto);
         System.out.println(total);
         List<BusinessSummaryReport> list = client.queryBusinessSummary(dto);
         for (BusinessSummaryReport f : list) {
            System.out.println(f);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年6月21日上午11:47:58
    */
   @Test
   public void test_queryCollectFeeReport() {
      try {
         List<Integer> userIds = new ArrayList<Integer>();
         userIds.add(20080);
         userIds.add(20040);
         CollectFeeReport dto = new CollectFeeReport();
         // dto.setUserIds(userIds);
         dto.setPage(-1);
         dto.setRows(20);
         // dto.setProjectId(11269);
         int total = client.getCollectFeeReportTotal(dto);
         System.out.println(total);
         List<CollectFeeReport> list = client.queryCollectFeeReport(dto);
         for (CollectFeeReport f : list) {
            System.out.println(f);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test
   public void test_queryFinancial(){
	   try {
		   FinancialStatisticsReport financialStatisticsReport = new FinancialStatisticsReport();
		   List<FinancialStatisticsReport> list = client.queryFinancialStatisticsReport(financialStatisticsReport );
		   
		   
	} catch (Exception e) {
		e.printStackTrace();
	}
   }
   @Test
	public void test_queryBusinessApprovalDetail() {
		try {
			BusinessApprovalDetail businessApprovalDetail = new BusinessApprovalDetail();
			List<BusinessApprovalDetail> list = client
					.queryBusinessApprovalDetail(businessApprovalDetail);
			for (BusinessApprovalDetail obj : list) {
				System.out.println(obj);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
