
package com.qfang.xk.aom.server.system;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.xlkfinance.bms.common.util.DateUtils;

public class OrgUserServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private OrgUserService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("system", "OrgUserService");
      try {
         client = (OrgUserService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      OrgUserInfo orgUser = new OrgUserInfo();
      orgUser.setUserName("dsfsd7");
      orgUser.setPassword("E10ADC3949BA59ABBE56E057F20F883E");
      orgUser.setRealName("martian");
      orgUser.setNickName("setNickName");
      orgUser.setJobNo("setJobNo");
      orgUser.setPhone("setPhone");
      orgUser.setEmail("setEmail");
      orgUser.setOrgId(1);
      orgUser.setRole(1);
      orgUser.setStatus(1);
      orgUser.setUserType(1);
      orgUser.setDateScope(1);
      orgUser.setCreateId(1);
      orgUser.setCreateDate(DateUtils.getCurrentDateTime());
      orgUser.setUpdateId(1);
      orgUser.setUpdateDate(DateUtils.getCurrentDateTime());
      client.insert(orgUser);
   }
   @Test
   public void test_update() throws Exception {
      OrgUserInfo orgUser = new OrgUserInfo();
      orgUser.setPid(9);
      orgUser.setUserName("setUserName2");
      orgUser.setPassword("setPassword2");
      orgUser.setRealName("setRealName2");
      orgUser.setNickName("setNickName2");
      orgUser.setJobNo("setJobNo2");
      orgUser.setPhone("setPhone2");
      orgUser.setEmail("setEmail2");
      orgUser.setOrgId(2);
      orgUser.setRole(2);
      orgUser.setStatus(2);
      orgUser.setUserType(2);
      orgUser.setDateScope(2);
      orgUser.setCreateId(2);
      orgUser.setCreateDate(DateUtils.getCurrentDateTime());
      orgUser.setUpdateId(2);
      orgUser.setUpdateDate(DateUtils.getCurrentDateTime());
      client.update(orgUser);
   }

   @Test
   public void test_getAll() throws Exception {

      OrgUserInfo orgUser = new OrgUserInfo();
      List<OrgUserInfo> list = client.getAll(orgUser);
      for (OrgUserInfo orgUserInfo : list) {
         System.out.println(orgUserInfo);
      }
   }

   @Test
   public void test_getById() throws Exception {
      OrgUserInfo userInfo = client.getById(9);
      System.out.println(userInfo);
   }
   @Test
   public void test_queryOrgUserByName() throws Exception {
      OrgUserInfo userInfo = client.queryOrgUserByName("admin");
      System.out.println(userInfo);
   }

   @Test
   public void test_deleteById() throws Exception {
      client.deleteById(8);
   }

   @Test
   public void test_deleteByIds() throws Exception {
      List<Integer> pids = new ArrayList<Integer>();
      pids.add(6);
      pids.add(7);
      client.deleteByIds(pids);
   }
}
