
package com.qfang.xk.aom.server.system;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.org.OrgCooperationContract;
import com.qfang.xk.aom.rpc.org.OrgCooperationContractService;

public class OrgCooperationContractServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private OrgCooperationContractService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("org", "OrgCooperationContractService");
      try {
         client = (OrgCooperationContractService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      OrgCooperationContract orgCooperationContract = new OrgCooperationContract();
      orgCooperationContract.setCooperationId(1);
      orgCooperationContract.setContractName("contractName2");
      orgCooperationContract.setContractNo("contractNo2");
      orgCooperationContract.setFileId(10725);
      orgCooperationContract.setContractType(1);
      orgCooperationContract.setCreatorId(1);
      orgCooperationContract.setCreatedDatetime("createdDatetime2");
      orgCooperationContract.setUpdateId(1);
      orgCooperationContract.setUpdateDate("updateDate2");
      orgCooperationContract.setRemark("remark2");
      client.insert(orgCooperationContract);
   }

   @Test
   public void test_update() throws Exception {
      OrgCooperationContract orgCooperationContract = new OrgCooperationContract();
      orgCooperationContract.setPid(4);
      orgCooperationContract.setCooperationId(1);
      orgCooperationContract.setContractName("contractName");
      orgCooperationContract.setContractNo("contractNo");
      orgCooperationContract.setFileId(10725);
      orgCooperationContract.setContractType(1);
      orgCooperationContract.setCreatorId(1);
      orgCooperationContract.setCreatedDatetime("createdDatetime");
      orgCooperationContract.setUpdateId(1);
      orgCooperationContract.setUpdateDate("updateDate");
      orgCooperationContract.setRemark("remark");
      client.update(orgCooperationContract);
   }

   @Test
   public void test_getAll() throws Exception {
      OrgCooperationContract orgCooperationContract = new OrgCooperationContract();
      List<OrgCooperationContract> list = client.getAll(orgCooperationContract);
      for (OrgCooperationContract obj : list) {
         System.out.println(obj);
      }
   }

   @Test
   public void test_getById() throws Exception {
      OrgCooperationContract obj = client.getById(2);
      System.out.println(obj);
   }

   @Test
   public void test_deleteById() throws Exception {
      client.deleteById(4);
   }

   @Test
   public void test_deleteByIds() throws Exception {
      List<Integer> pids = new ArrayList<Integer>();
      pids.add(6);
      pids.add(7);
      client.deleteByIds(pids);
   }
}
