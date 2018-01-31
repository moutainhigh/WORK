
package com.qfang.xk.aom.server.system;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfo;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfoService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;

public class OrgSysMenuInfoServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private OrgSysMenuInfoService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("system", "OrgSysMenuInfoService");
      try {
         client = (OrgSysMenuInfoService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      OrgSysMenuInfo orgSysMenuInfo = new OrgSysMenuInfo();
      orgSysMenuInfo.setMenuName("息费结算");
      orgSysMenuInfo.setParentId(18);
      orgSysMenuInfo.setLevel(2);
      orgSysMenuInfo.setUrl("url");
      orgSysMenuInfo.setStatus(1);
      orgSysMenuInfo.setMenuIndex(1);
      orgSysMenuInfo.setUserType(2);
      orgSysMenuInfo.setIconCls("fa fa-home");
      client.insert(orgSysMenuInfo);
   }

   @Test
   public void test_update() throws Exception {
      OrgSysMenuInfo orgSysMenuInfo = new OrgSysMenuInfo();
      orgSysMenuInfo.setPid(1);
      orgSysMenuInfo.setMenuName("menuName");
      orgSysMenuInfo.setParentId(1);
      orgSysMenuInfo.setLevel(1);
      orgSysMenuInfo.setUrl("url");
      orgSysMenuInfo.setStatus(1);
      orgSysMenuInfo.setMenuIndex(1);
      orgSysMenuInfo.setIconCls("iconCls");
      client.update(orgSysMenuInfo);
   }

   @Test
   public void test_getAll() throws Exception {
      OrgSysMenuInfo orgSysMenuInfo = new OrgSysMenuInfo();
      List<OrgSysMenuInfo> list = client.getAll(orgSysMenuInfo);
      for (OrgSysMenuInfo obj : list) {
         System.out.println(obj);
      }
   }
   @Test
   public void test_getTree() throws Exception {
      List<OrgSysMenuInfo> list = client.getTree(new OrgUserInfo());
      for (OrgSysMenuInfo obj : list) {
         System.out.println(obj);
      }
   }

   @Test
   public void test_getById() throws Exception {
      OrgSysMenuInfo obj = client.getById(2);
      System.out.println(obj);
   }

   @Test
   public void test_deleteById() throws Exception {
      client.deleteById(2);
   }

   @Test
   public void test_deleteByIds() throws Exception {
      List<Integer> pids = new ArrayList<Integer>();
      pids.add(6);
      pids.add(7);
      client.deleteByIds(pids);
   }
}
