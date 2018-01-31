/**
 * @Title: RunServerTest.java
 * @Package com.xlkfinance.bms.server
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月20日 上午10:37:05
 * @version V1.0
 */
package com.xlkfinance.bms.server.system;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.thrift.TException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;

public class SysOrgInfoServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @AfterClass
   public static void tearDownAfterClass() throws Exception {
   }

   @Before
   public void setUp() throws Exception {

   }

   @After
   public void tearDown() throws Exception {
   }

   @Test
   public void test_insertSysOrgInfo() {

      try {
         BaseClientFactory clientFactory = getFactory("system", "SysOrgInfoService");
         SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
         SysOrgInfo org = new SysOrgInfo();

         org.setName("品牌推广部");
         org.setParentId(7);
         org.setCode("PP-FT-SZ");
         org.setCategory(1);
         org.setLayer(5);
         // org.setStatus(1);
         // System.out.println(sysuser.toString());
         client.saveSysOrgInfo(org);
         // assertEquals("admin", sysuser.getUserName());
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (TException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_getSysOrgInfo() {

      try {
         BaseClientFactory clientFactory = getFactory("system", "SysOrgInfoService");
         SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
         SysOrgInfo org = new SysOrgInfo();

         org = client.getSysOrgInfo(1);
         System.out.println("===org info ::" + org.name + " ::code:" + org.code + " :: parentId:" + org.parentId);

      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (TException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_listSysOrgByParentId() {

      try {
         BaseClientFactory clientFactory = getFactory("system", "SysOrgInfoService");
         SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();

         List<SysOrgInfo> list = client.listSysOrgInfo(2);

         for (SysOrgInfo org : list) {
            System.out.println("===org info ::" + "id:" + org.id + "	name::" + org.name + " ::code:" + org.code + ":: parentId::" + org.parentId);
         }

      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (TException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_updateSysOrgInfo() {

      try {
         BaseClientFactory clientFactory = getFactory("system", "SysOrgInfoService");
         SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();
         SysOrgInfo org = new SysOrgInfo();
         org.setId(16);
         org.setName("品牌推广部");
         org.setParentId(7);
         org.setCode("PP-FT-SZ");
         org.setCategory(1);
         org.setLayer(5);
         // org.setStatus(1);
         client.updateSysOrgInfo(org);

      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (TException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_getSysOrgInfoByCategory() {

      try {
         BaseClientFactory clientFactory = getFactory("system", "SysOrgInfoService");
         SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();

         List<SysOrgInfo> list = client.allSysOrgInfoList(1);

         for (SysOrgInfo org : list) {

            System.out.println("id:" + org.getId() + ":: name:" + org.getName());
         }
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (TException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_getSysOrgInfoByCategory2() {

      try {
         BaseClientFactory clientFactory = getFactory("system", "SysOrgInfoService");
         SysOrgInfoService.Client client = (SysOrgInfoService.Client) clientFactory.getClient();

         SysOrgInfo sysOrgQuery = new SysOrgInfo();
         sysOrgQuery.setLayer(2);
         sysOrgQuery.setId(4);
         List<SysOrgInfo> listSysParentOrg = client.listSysParentOrg(sysOrgQuery);
         for (SysOrgInfo sysOrgInfo : listSysParentOrg) {
            System.out.println(sysOrgInfo);
         }
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      } catch (TException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

}
