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
package com.qfang.xk.aom.server.fee;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettle;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleService;

public class OrgFeeSettleTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private OrgFeeSettleService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("fee", "OrgFeeSettleService");
      try {
         client = (OrgFeeSettleService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      OrgFeeSettle orgFeeSettle = new OrgFeeSettle();
		      orgFeeSettle.setPid(1);
		      orgFeeSettle.setOrgId(1);
		      orgFeeSettle.setPartnerId(1);
		      orgFeeSettle.setApplyMoneyTotal(1);
		      orgFeeSettle.setLoanMoneyTotal(1);
		      orgFeeSettle.setPaymentMoneyTotal(1);
		      orgFeeSettle.setSettleDate("settleDate");
		      orgFeeSettle.setRebateRate(1);
		      orgFeeSettle.setReturnCommRate(1);
		      orgFeeSettle.setRefundMoneyTotal(1);
		      orgFeeSettle.setReturnCommTotal(1);
		      orgFeeSettle.setChargeMoneyTotal(1);
		      orgFeeSettle.setRebateMoneyTotal(1);
		      orgFeeSettle.setRebateType(1);
		      orgFeeSettle.setOrgId(37);
//		      orgFeeSettle.setPartnerId(1);
		      orgFeeSettle.setApplyMoneyTotal(10);
		      orgFeeSettle.setLoanMoneyTotal(20);
		      orgFeeSettle.setPaymentMoneyTotal(30);
		      orgFeeSettle.setSettleDate("2016-08-13 12:12:12");
		      orgFeeSettle.setRebateRate(40);
		      orgFeeSettle.setReturnCommRate(50);
		      orgFeeSettle.setRefundMoneyTotal(60);
		      orgFeeSettle.setReturnCommTotal(70);
		      orgFeeSettle.setChargeMoneyTotal(80);
		      orgFeeSettle.setRebateMoneyTotal(90);
		      orgFeeSettle.setRebateType(2);
      client.insert(orgFeeSettle);
   }
   @Test
   public void test_update() throws Exception {
      OrgFeeSettle orgFeeSettle = new OrgFeeSettle();
		      orgFeeSettle.setPid(1);
		      orgFeeSettle.setOrgId(1);
		      orgFeeSettle.setPartnerId(1);
		      orgFeeSettle.setApplyMoneyTotal(1);
		      orgFeeSettle.setLoanMoneyTotal(1);
		      orgFeeSettle.setPaymentMoneyTotal(1);
		      orgFeeSettle.setSettleDate("settleDate");
		      orgFeeSettle.setRebateRate(1);
		      orgFeeSettle.setReturnCommRate(1);
		      orgFeeSettle.setRefundMoneyTotal(1);
		      orgFeeSettle.setReturnCommTotal(1);
		      orgFeeSettle.setChargeMoneyTotal(1);
		      orgFeeSettle.setRebateMoneyTotal(1);
		      orgFeeSettle.setRebateType(1);
      client.update(orgFeeSettle);
   }

   @Test
   public void test_getAll() throws Exception {
      OrgFeeSettle orgFeeSettle = new OrgFeeSettle();
      List<OrgFeeSettle> list = client.getAll(orgFeeSettle);
      for (OrgFeeSettle obj : list) {
         System.out.println(obj);
      }
   }

   @Test
   public void test_getById() throws Exception {
      OrgFeeSettle obj = client.getById(9);
      System.out.println(obj);
   }
   
   @Test
   public void insertFeeSettle()throws Exception {
	   client.initFeeSettle();
   }
}
