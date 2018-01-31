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
package com.xlkfinance.bms.server.inloan;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeService;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月28日下午2:42:51 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class RefundFeeServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private RefundFeeService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("inloan", "RefundFeeService");
      try {
         client = (RefundFeeService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年1月26日下午5:13:55
    *@throws TException
    */
   @Test
   public void test_findAllRefundFee() {
      try {
         List<Integer> userIds = new ArrayList<Integer>();
         userIds.add(20080);
         userIds.add(20040);
         RefundFeeDTO dto = new RefundFeeDTO();
//          dto.setUserIds(userIds);
         dto.setPage(1);
         dto.setRows(20);
         // dto.setApplyStatus(1);
          dto.setProjectId(11515);
         // dto.setApprovalStatus(1);
         int total = client.getRefundFeeTotal(dto);
         System.out.println(total);
         List<RefundFeeDTO> list = client.findAllRefundFee(dto);
         for (RefundFeeDTO f : list) {
            System.out.println(f.getPayDate());
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_findAllRefundFeeIndex() {
      try {
         List<Integer> userIds = new ArrayList<Integer>();
         userIds.add(20080);
         userIds.add(20040);
         RefundFeeIndexDTO dto = new RefundFeeIndexDTO();
//         dto.setUserIds(userIds);
         dto.setPage(1);
         dto.setRows(20);
          dto.setProjectId(10453);
         int total = client.getRefundFeeIndexTotal(dto);
         System.out.println(total);
         List<RefundFeeIndexDTO> list = client.findAllRefundFeeIndex(dto);
         for (RefundFeeIndexDTO f : list) {
            System.out.println(f);
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * 添加退尾款信息
    *@author:liangyanjun
    *@time:2016年1月26日下午5:10:54
    */
   @Test
   public void test_insertRefundFeeDTO() {
      int[] projectIds = {11424};
      try {
         for (int projectId : projectIds) {
               RefundFeeDTO dto = new RefundFeeDTO();
               dto.setProjectId(projectId);
               dto.setApplyStatus(1);
               dto.setType(2);
               dto.setCreaterId(20132);
               dto.setReturnFee(40);
               /*dto.setReturnFee(22.3+i);
               dto.setRecAccountName("setRecAccountName"+i);
               dto.setBankName("setBankName"+i);
               dto.setRecAccount("setRecAccount"+i);
               dto.setCreaterId(20122);
               dto.setUpdateId(20122);*/
               client.addRefundFee(dto);
               System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateRefundFeeDTO() {
      try {
         RefundFeeDTO dto = new RefundFeeDTO();
         dto.setPid(1);
         dto.setProjectId(10453);
         dto.setApplyStatus(1);
         dto.setProductId(1);
         dto.setTradeWay("交易方式");
         dto.setTradeDate("2001-12-09");
         dto.setMortgageNumber("setMortgageNumber");
         dto.setCustomerId(20040);
         dto.setOldHome("setOldHome");
         dto.setPmUserId(20040);
         dto.setHomeName("setHomeName");
         dto.setRecDate("2001-12-09");
         dto.setRecMoney(11.33);
         dto.setPayDate("2001-12-09");
         dto.setReturnFee(22.3);
         dto.setDeptId(1);
         dto.setRecAccountName("setRecAccountName");
         dto.setBankName("setBankName");
         dto.setRecAccount("setRecAccount");
         dto.setCreaterId(20040);
         dto.setUpdateId(20040);
         client.updateRefundFee(dto);
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   /**
    *@author:liangyanjun
 * @throws TException 
    *@time:2017年1月19日下午5:35:26
    */
   @Test
   public void addRefundTail(){
       try {
        client.addRefundTail(14009);
    } catch (TException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}
