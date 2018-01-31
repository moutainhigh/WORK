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

import static org.junit.Assert.assertEquals;
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
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.SysMenu;
import com.xlkfinance.bms.rpc.system.SysOrgInfo;
import com.xlkfinance.bms.rpc.system.SysOrgInfoService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserService;

public class SysUserServiceTest {
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
	public void test_querySysUserByName() {

		try {
			BaseClientFactory clientFactory = getFactory("system", "SysUserService");
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			SysUser sysuser = client.querySysUserByName("admin");
			System.out.println(sysuser.toString());

			assertEquals("admin", sysuser.getUserName());
		} catch (ThriftException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (TException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void test_getPermissionMenuByUserName() {

		try {
			BaseClientFactory clientFactory = getFactory("system", "SysUserService");
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
			List<SysMenu> menuList = client.getPermissionMenuByUserName("admin");
			for (SysMenu sysMenu : menuList) {
				System.out.println(sysMenu.toString());
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
	public void test_getUsersByOrgIdAndRoleCode() {
	   try {
	      BaseClientFactory clientFactory = getFactory("system", "SysUserService");
	      BaseClientFactory baseClientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
	      SysOrgInfoService.Client sysOrgInfoService=(SysOrgInfoService.Client) baseClientFactory.getClient();
	      SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
         SysOrgInfo sysOrgQuery=new SysOrgInfo();
         sysOrgQuery.setLayer(2);
         sysOrgQuery.setId(55);
	      List<SysOrgInfo> listSysParentOrg = sysOrgInfoService.listSysParentOrg(sysOrgQuery);
	      SysOrgInfo sysOrgInfo = listSysParentOrg.get(0);
	      int id = sysOrgInfo.getId();
	      List<SysUser> users = client.getUsersByOrgIdAndRoleCode(id, "HOUSE_CLERK");
	      for (SysUser sysUser : users) {
            System.out.println(sysUser);
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
	public void test_getUserDetailsByPid() {
	   try {
	      BaseClientFactory clientFactory = getFactory("system", "SysUserService");
	      SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();
	      int pid = 20132;
         SysUser sysUser = client.getUserDetailsByPid(pid);
         System.out.println(sysUser);
	   } catch (ThriftException e) {
	      e.printStackTrace();
	      fail(e.getMessage());
	   } catch (TException e) {
	      e.printStackTrace();
	      fail(e.getMessage());
	   }
	   
	}
}
