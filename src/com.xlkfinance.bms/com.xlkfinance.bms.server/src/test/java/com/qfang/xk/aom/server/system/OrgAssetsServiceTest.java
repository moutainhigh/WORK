
package com.qfang.xk.aom.server.system;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.qfang.xk.aom.rpc.system.OrgUserService;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.rpc.system.SysUserOrgInfoService;

public class OrgAssetsServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private OrgAssetsCooperationService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   private String basePackageBms = "com.xlkfinance.bms.rpc";

	public BaseClientFactory getBmsFactory(String serviceModuel, String serviceName) {
		String service = new StringBuffer().append(basePackageBms).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
		return clientFactory;
	}
   
   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("org", "OrgAssetsCooperationService");
      try {
         client = (OrgAssetsCooperationService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void insert(){
	   BaseClientFactory clientFactory = getBmsFactory("system", "SysLookupService");
	   try {
		SysLookupService.Client lookclient = (SysLookupService.Client) clientFactory.getClient();
		List<SysLookupVal> list = lookclient.getSysLookupValByLookType("PARTNER_NAME");
		OrgAssetsCooperationInfo org = new OrgAssetsCooperationInfo();
		List<String> orgNameList = new ArrayList<String>();
		orgNameList.add("华盛汇融");
		orgNameList.add("前海壹号");
		orgNameList.add("前海财友");
		orgNameList.add("广州升锦");
		orgNameList.add("融安");
		orgNameList.add("亚桐");
		int i=0;
		for(String orgName : orgNameList){
			i++;
			org.setOrgName(orgName);
			org.setCode("12345-"+i);
			org.setAuditStatus(3);
			OrgUserInfo userInfo = new OrgUserInfo();
			userInfo.setPassword("123456");
			org.setOrgUserInfo(userInfo);
			client.insert(org);
		}
		
		
	   } catch (Exception e) {
		e.printStackTrace();
		fail(e.getMessage());
	}
   }
}
