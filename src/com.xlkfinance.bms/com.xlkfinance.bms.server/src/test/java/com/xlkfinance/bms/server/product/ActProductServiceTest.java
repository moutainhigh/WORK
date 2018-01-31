/**
 * @Title: ActProductServiceTest.java
 * @Package com.xlkfinance.bms.server.product
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络科技
 * 
 * @author: andrew
 * @date: 2016年1月17日 下午3:39:42
 * @version V1.0
 */
package com.xlkfinance.bms.server.product;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.product.ActProduct;
import com.xlkfinance.bms.rpc.product.ActProductService;

public class ActProductServiceTest {

	private String serverIp = "127.0.0.1";
	   private int serverPort = 19090;
	   private String basePackage = "com.xlkfinance.bms.rpc";
	   private ActProductService.Client client;
	   
	   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
		      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
		      return clientFactory;
		   }

		   @Before
		   public void init() {
		      BaseClientFactory clientFactory = getFactory("product", "ActProductService");
		      try {
		         client = (ActProductService.Client) clientFactory.getClient();
		      } catch (ThriftException e) {
		         e.printStackTrace();
		         fail(e.getMessage());
		      }
		   }
		   
		   @Test
		   public void test_addActproduct(){
			   
			   ActProduct act = new ActProduct();
			   act.setActId("loanRequestProcess:2:7528");
			   act.setProductId(10);
			   act.setActType(1);
			   try {
				client.addActProduct(act);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   
		   @Test
		   public void test_getAllAct(){
			   ActProduct act = new ActProduct();
			   act.setProductId(10);
			   act.setActType(1);
			   try {
				List<ActProduct> list = client.getAllProduct(act);
				for(ActProduct ap : list){
					System.out.println(ap);
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		   }
}
