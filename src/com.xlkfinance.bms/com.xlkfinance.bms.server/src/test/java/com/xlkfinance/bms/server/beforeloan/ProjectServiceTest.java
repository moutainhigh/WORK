/**
 * @Title: RunServerTest.java
 * @Package com.xlkfinance.bms.server
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年02月05日 上午10:37:05
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;

public class ProjectServiceTest {
	private String serverIp = "127.0.0.1";
	   private int serverPort = 19090;
	   private String basePackage = "com.xlkfinance.bms.rpc";
	   private ProjectService.Client client;
	   
	   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
		      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
		      return clientFactory;
		   }

	   @Before
	   public void init() {
	      BaseClientFactory clientFactory = getFactory("beforeloan", "ProjectService");
	      try {
	         client = (ProjectService.Client) clientFactory.getClient();
	      } catch (ThriftException e) {
	         e.printStackTrace();
	         fail(e.getMessage());
	      }
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
	 * 
	  * @Description: 测试-生成还款计划表
	  * @author: zhanghg
	  * @date: 2015年2月5日 上午11:28:27
	 */
	@Test
	public void test_makeRepayMent() {

		try {
			BaseClientFactory clientFactory = getFactory("beforeloan", "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			String project = client.findProjectLoanMoney(11593);
			System.out.println(project);
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	@Test
	public void test_getProjectInfoById() {
	   
	   try {
	      BaseClientFactory clientFactory = getFactory("beforeloan", "ProjectService");
	      ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
	      Project project = client.getProjectInfoById(11435);
	      System.out.println(project.getIsChechan());
	   } catch (Exception e) {
	      e.printStackTrace();
	      fail(e.getMessage());
	   }
	   
	}
	
	@Test
	public void test_getAll(){
		try {
			Project project = client.getLoanProjectByProjectId(11401);
			ProjectForeclosure fore = project.getProjectForeclosure();
			fore.setOldLoanTime("2041-01-10");
			
			ProjectProperty property = project.getProjectProperty();
			property.setHouseName("嘉洲豪园二期1栋5-C");
			property.setArea(80.67);
			property.setCostMoney(410874);
			property.setHousePropertyCard("3000639138");
			property.setTranasctionMoney(3530000);
			property.setEvaluationPrice(3646284);
			property.setSellerName("胡益、卓秋雪");
			property.setSellerCardNo("362401198211023236/44030719850202482X");
			property.setSellerPhone("15817213914");
			property.setSellerAddress("广东省深圳市嘉洲豪园503");
			property.setBuyerName("李赟");
			property.setBuyerCardNo("440306198910210051");
			
			ProjectGuarantee guarantee = project.getProjectGuarantee();
			guarantee.setGuaranteeFee(4538);
			
			project.setProjectForeclosure(fore);
			project.setProjectGuarantee(guarantee);
			project.setProjectProperty(property);
			client.saveProjectInfo(project);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		@Test
   public void test_updatecollectFileStatusByPid() {
      BaseClientFactory clientFactory = getFactory("beforeloan", "ProjectService");
      try {
         ProjectService.Client projectServiceClient = (ProjectService.Client) clientFactory.getClient();
         int pid = 11386;
         int collectFileStatus = 1;
         projectServiceClient.updatecollectFileStatusByPid(pid, collectFileStatus);
      } catch (Exception e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }
	
	
}
