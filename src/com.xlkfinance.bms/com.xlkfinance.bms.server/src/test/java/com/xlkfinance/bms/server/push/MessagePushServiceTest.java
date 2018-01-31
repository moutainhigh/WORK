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
package com.xlkfinance.bms.server.push;

import static org.junit.Assert.fail;

import org.apache.thrift.TException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.push.MessagePush;
import com.xlkfinance.bms.rpc.push.MessagePushService;

public class MessagePushServiceTest {
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
	public void test_insertMessagePush() {

		try {
			BaseClientFactory clientFactory = getFactory("push", "MessagePushService");
			MessagePushService.Client client = (MessagePushService.Client) clientFactory.getClient();
			MessagePush msg = new MessagePush();
			msg.setTitle("推送标题11");
			msg.setContent("推送嘻嘻嘻11");
			msg.setDeviceType(1);
			msg.setUserName("najinjing");
			msg.setBizType(1);
			msg.setRemark("rrrrrrrrrrrrr");
			
			client.saveMessagePush(msg);
		} catch (ThriftException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch (TException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	

	
}
