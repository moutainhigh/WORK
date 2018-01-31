/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-08-12 15:24:28 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 费用结算明细表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.qfang.xk.aom.server.fee;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetail;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailService;
import com.xlkfinance.bms.common.util.DateUtils;

public class OrgFeeSettleDetailTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private OrgFeeSettleDetailService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("orgSystem", "OrgFeeSettleDetailService");
      try {
         client = (OrgFeeSettleDetailService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      OrgFeeSettleDetail orgFeeSettleDetail = new OrgFeeSettleDetail();
		      orgFeeSettleDetail.setPid(1);
		      orgFeeSettleDetail.setSettleId(1);
		      orgFeeSettleDetail.setProjectId(1);
		      orgFeeSettleDetail.setLoanMoney(1);
		      orgFeeSettleDetail.setPaymentMoney(1);
		      orgFeeSettleDetail.setCharge(1);
		      orgFeeSettleDetail.setRefund(1);
		      orgFeeSettleDetail.setRebateMoney(1);
		      orgFeeSettleDetail.setReturnComm(1);
		      orgFeeSettleDetail.setPaymentDate("paymentDate");
		      orgFeeSettleDetail.setLoanDate("loanDate");
		      orgFeeSettleDetail.setReturnCommRate(1);
		      orgFeeSettleDetail.setRebateRate(1);
		      orgFeeSettleDetail.setSolutionDate("solutionDate");
      client.insert(orgFeeSettleDetail);
   }
   @Test
   public void test_update() throws Exception {
      OrgFeeSettleDetail orgFeeSettleDetail = new OrgFeeSettleDetail();
		      orgFeeSettleDetail.setPid(1);
		      orgFeeSettleDetail.setSettleId(1);
		      orgFeeSettleDetail.setProjectId(1);
		      orgFeeSettleDetail.setLoanMoney(1);
		      orgFeeSettleDetail.setPaymentMoney(1);
		      orgFeeSettleDetail.setCharge(1);
		      orgFeeSettleDetail.setRefund(1);
		      orgFeeSettleDetail.setRebateMoney(1);
		      orgFeeSettleDetail.setReturnComm(1);
		      orgFeeSettleDetail.setPaymentDate("paymentDate");
		      orgFeeSettleDetail.setLoanDate("loanDate");
		      orgFeeSettleDetail.setReturnCommRate(1);
		      orgFeeSettleDetail.setRebateRate(1);
		      orgFeeSettleDetail.setSolutionDate("solutionDate");
      client.update(orgFeeSettleDetail);
   }

   @Test
   public void test_getAll() throws Exception {
      OrgFeeSettleDetail orgFeeSettleDetail = new OrgFeeSettleDetail();
      List<OrgFeeSettleDetail> list = client.getAll(orgFeeSettleDetail);
      for (OrgFeeSettleDetail obj : list) {
         System.out.println(obj);
      }
   }

   @Test
   public void test_getById() throws Exception {
      OrgFeeSettleDetail obj = client.getById(9);
      System.out.println(obj);
   }
}
