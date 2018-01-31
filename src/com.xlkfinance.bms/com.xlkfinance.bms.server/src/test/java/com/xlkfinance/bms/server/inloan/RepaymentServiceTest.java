package com.xlkfinance.bms.server.inloan;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.inloan.OverdueFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentRecordDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentService;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日下午6:32:44 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class RepaymentServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private RepaymentService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("inloan", "RepaymentService");
      try {
         client = (RepaymentService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_queryRepaymentIndex() {
      try {
         RepaymentIndexDTO query = new RepaymentIndexDTO();
         query.setRepaymentStatus(2);
         List<RepaymentIndexDTO> list = client.queryRepaymentIndex(query);
         int total = client.getRepaymentIndexTotal(query);
         System.out.println("total:" + total);
         for (RepaymentIndexDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月12日下午3:55:25
    */
   @Test
   public void test_queryRepayment() {
      try {
         RepaymentDTO query = new RepaymentDTO();
         List<RepaymentDTO> list = client.queryRepayment(query);
         int total = client.getRepaymentTotal(query);
         System.out.println("total:" + total);
         for (RepaymentDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_addRepayment() {
      RepaymentDTO dto = new RepaymentDTO();
      dto.setProjectId(11388);
      dto.setStatus(1);
      dto.setRepaymentMoney(111);
      dto.setNewRepaymentDate("2012-12-12");
      dto.setRemark("remark");
      dto.setCreaterId(20080);
      dto.setUpdateId(20080);
      try {
         client.addRepayment(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateRepayment() {
      RepaymentDTO dto = new RepaymentDTO();
      dto.setPid(5);
      dto.setStatus(1);
      dto.setRepaymentMoney(111);
      dto.setNewRepaymentDate("2016-06-12");
      dto.setRemark("111");
      dto.setCreaterId(20080);
      dto.setUpdateId(20080);
      try {
         client.updateRepayment(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月12日下午3:55:25
    */
   @Test
   public void test_queryRepaymentRecord() {
      try {
         RepaymentRecordDTO query = new RepaymentRecordDTO();
         List<RepaymentRecordDTO> list = client.queryRepaymentRecord(query);
         int total = client.getRepaymentRecordTotal(query);
         System.out.println("total:" + total);
         for (RepaymentRecordDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_addRepaymentRecord() {
      RepaymentRecordDTO dto = new RepaymentRecordDTO();
      dto.setProjectId(11388);
      dto.setRepaymentId(5);
      dto.setRepaymentMoney(111);
      dto.setRepaymentDate("2012-12-12");
      dto.setRemark("remark");
      dto.setCreaterId(20080);
      try {
         client.addRepaymentRecord(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月12日下午3:55:25
    */
   @Test
   public void test_queryOverdueFee() {
      try {
         OverdueFeeDTO query = new OverdueFeeDTO();
         List<OverdueFeeDTO> list = client.queryOverdueFee(query);
         int total = client.getOverdueFeeTotal(query);
         System.out.println("total:" + total);
         for (OverdueFeeDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_addOverdueFee() {
      OverdueFeeDTO dto = new OverdueFeeDTO();
      dto.setProjectId(11388);
      dto.setStatus(1);
      dto.setAccountName("setAccountName");
      dto.setBankName("setBankName");
      dto.setAccountNo("setAccountNo");
      dto.setOverdueFee(111);
      dto.setOverdueRate(2.3);
      dto.setOverdueDay(2);
      dto.setPaymentWay(1);
      dto.setRemark("remark");
      dto.setCreaterId(20080);
      dto.setUpdateId(20080);
      try {
         client.addOverdueFee(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateOverdueFee() {
      OverdueFeeDTO dto = new OverdueFeeDTO();
      dto.setPid(1);
      dto.setStatus(1);
      dto.setAccountName("setAccountName1");
      dto.setBankName("setBankName1");
      dto.setAccountNo("setAccountNo1");
      dto.setOverdueFee(222);
      dto.setOverdueRate(2.222);
      dto.setOverdueDay(22);
      dto.setPaymentWay(22);
      dto.setRemark("remark2");
      dto.setCreaterId(20081);
      dto.setUpdateId(20081);
      try {
         client.updateOverdueFee(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
