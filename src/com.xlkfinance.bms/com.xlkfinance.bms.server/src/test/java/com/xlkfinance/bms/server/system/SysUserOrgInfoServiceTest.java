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

import java.util.ArrayList;
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
import com.xlkfinance.bms.rpc.system.SysUserOrgInfo;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService;

public class SysUserOrgInfoServiceTest {
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

	//@Test
	public void test_insertSysUserOrgInfo() {

		try {
			BaseClientFactory clientFactory = getFactory("system", "SysUserOrgInfoService");
			SysUserOrgInfoService.Client client = (SysUserOrgInfoService.Client) clientFactory.getClient();
			SysUserOrgInfo org = new SysUserOrgInfo();
			
			org.setOrgId(1);
			org.setUserId(20005);
			org.setCategory(1);
			org.setDataScope(1);
			client.saveUserOrgInfo(org);
		} catch (ThriftException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (TException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	@Test
	public void test_listOrgByUserId() {
		
		try {
			BaseClientFactory clientFactory = getFactory("system", "SysUserOrgInfoService");
			SysUserOrgInfoService.Client client = (SysUserOrgInfoService.Client) clientFactory.getClient();
			
			List<SysUserOrgInfo> list = client.listUserOrgInfo(20084);
			
			for(SysUserOrgInfo org : list){
				
				System.out.println("id:"+org.getId()+" userId:"+org.getUserId());
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
	public void test_listUserByOrgId() {
		
		try {
			BaseClientFactory clientFactory = getFactory("system", "SysUserOrgInfoService");
			SysUserOrgInfoService.Client client = (SysUserOrgInfoService.Client) clientFactory.getClient();
			
			List list = new ArrayList<Integer>();
			list.add(1);
			list.add(3);
			list.add(2);
			list.add(6);
			List<SysUserOrgInfo> lists = client.listUserOrgInfoByOrgId(list);
			
			for(SysUserOrgInfo org : lists){
				
				System.out.println("id:"+org.getId()+" userId:"+org.getUserId());
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
	public void test_getSysUserOrgInfo() {
		
		try {
			BaseClientFactory clientFactory = getFactory("system", "SysUserOrgInfoService");
			SysUserOrgInfoService.Client client = (SysUserOrgInfoService.Client) clientFactory.getClient();
			
			SysUserOrgInfo userOrg = new SysUserOrgInfo();
			
			userOrg.setUserId(10005);
			userOrg.setOrgId(4);
			SysUserOrgInfo org = client.getUserOrgInfo(userOrg);
			
				
				System.out.println("id:"+org.getId()+" userId:"+org.getUserId());
		} catch (ThriftException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (TException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	

	
	
}
