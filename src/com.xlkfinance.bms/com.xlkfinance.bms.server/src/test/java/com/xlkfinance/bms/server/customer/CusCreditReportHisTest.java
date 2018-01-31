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

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHis;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHisService;

/**
 * 客户征信报告记录 测试类
 * @author chenzhuzhen
 * @date 2017年6月8日 上午10:30:07
 */
public class CusCreditReportHisTest {
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
	
	/**
	 * 获取列表
	 */
	@Test
	public void test_getCreditReportHisList() {

		try {
			BaseClientFactory clientFactory = getFactory("customer", "CusCreditReportHisService");
			CusCreditReportHisService.Client client = (CusCreditReportHisService.Client) clientFactory.getClient();
			 
			CusCreditReportHis query = new CusCreditReportHis();
 
			 List<CusCreditReportHis> list = client.selectList(query);
			
			System.out.println("list-begin------------------------------");
			if(list != null ){
				System.out.println(JSONObject.toJSONString(list));
			}
			System.out.println("list-end------------------------------");
			 
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		} 

	}
}
