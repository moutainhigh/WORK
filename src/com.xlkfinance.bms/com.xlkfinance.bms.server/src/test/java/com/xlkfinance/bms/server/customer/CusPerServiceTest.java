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
package com.xlkfinance.bms.server.customer;

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
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerFamilyDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;

public class CusPerServiceTest {
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
	public void test_getCusPerBases() {

		try {
			BaseClientFactory clientFactory = getFactory("customer", "CusPerService");
			CusPerService.Client client = (CusPerService.Client) clientFactory.getClient();
			CusPerFamilyDTO cusPerFamilyDTO = new CusPerFamilyDTO();
			CusPerson cusPerson = new CusPerson();
			CusAcct cusAcct =  new CusAcct();
			cusAcct.setPid(13589);
			cusPerson.setCusAcct(cusAcct );
			cusPerson.setRelationType(2);
			cusPerson.setChinaName("张三");
			cusPerson.setAge(34);
			cusPerson.setTelephone("123456789");
			cusPerson.setLiveAddr("阿达东欧胡普通人");
			cusPerson.setProportionProperty(50);
			cusPerson.setCertNumber("23123123123213");
			cusPerson.setCertType(13088);
			
			client.addCusPerFamily(cusPerFamilyDTO );
		
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 

	}
	
	@Test
	public void test_addPublicMan(){
		try {
			BaseClientFactory clientFactory = getFactory("customer", "CusAcctService");
			CusAcctService.Client client = (CusAcctService.Client) clientFactory.getClient();
			CusPerson cusPerson = new CusPerson();
			CusAcct cusAcct =  new CusAcct();
			cusAcct.setPid(13589);
			cusPerson.setCusAcct(cusAcct );
			cusPerson.setRelationType(9);
			cusPerson.setChinaName("李四");
			cusPerson.setAge(34);
			cusPerson.setTelephone("123456789");
			cusPerson.setLiveAddr("阿达东欧胡普通人");
			cusPerson.setProportionProperty(50);
			cusPerson.setCertNumber("23123123123213");
			cusPerson.setCertType(13088);
			client.addCusPerson(cusPerson);
		
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 
	}
	
}
