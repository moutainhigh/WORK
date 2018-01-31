/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-08-08 09:56:37 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 客户关系表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.xlkfinance.bms.server.customer;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusRelation;
import com.xlkfinance.bms.rpc.customer.CusRelationService;

public class CusRelationServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private CusRelationService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("customer", "CusRelationService");
      try {
         client = (CusRelationService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      CusRelation cusRelation = new CusRelation();
		      cusRelation.setPid(1);
		      cusRelation.setAcctId(1);
		      cusRelation.setOrgId(1);
		      cusRelation.setOrgType(1);
		      cusRelation.setPmUserId(1);
      client.insert(cusRelation);
   }

   @Test
   public void test_getAll() throws Exception {
      CusRelation cusRelation = new CusRelation();
      List<CusRelation> list = client.getAll(cusRelation);
      for (CusRelation obj : list) {
         System.out.println(obj);
      }
   }
   
   @Test
   public void initData() throws Exception {
	   BaseClientFactory clientFactory = getFactory("customer", "CusPerService");
		CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
	   
   }
}
