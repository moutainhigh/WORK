
package com.qfang.xk.aom.server.system;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;


public class UserServiceTest {

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
	public void testName() throws Exception {
		   OrgUserInfo orgUser = new OrgUserInfo();
		client.getAll(orgUser );
	}
}
