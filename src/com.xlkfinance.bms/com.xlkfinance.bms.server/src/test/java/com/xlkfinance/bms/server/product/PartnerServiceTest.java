/**
 * @Title: ProductServiceTest.java
 * @Package com.xlkfinance.bms.server.product
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年1月15日 下午2:46:28
 * @version V1.0
 */
package com.xlkfinance.bms.server.product;

import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;

public class PartnerServiceTest {

	private String serverIp = "127.0.0.1";
	   private int serverPort = 19090;
	   private String basePackage = "com.xlkfinance.bms.rpc";
	   private ProjectPartnerService.Client client;
	   
	   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
		      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
		      return clientFactory;
		   }

		   @Before
		   public void init() {
		      BaseClientFactory clientFactory = getFactory("partner", "ProjectPartnerService");
		      try {
		         client = (ProjectPartnerService.Client) clientFactory.getClient();
		      } catch (ThriftException e) {
		         e.printStackTrace();
		         fail(e.getMessage());
		      }
		   }
		   
		   @Test
		   public void test_addProduct(){
			   ProjectPartner partner = new ProjectPartner(); 
			   partner.setProjectId(11439);
			   partner.setIsClosed(1);
			   partner.setRequestStatus(2);
			   partner.setPmUserId(20084);
			   partner.setPartnerNo("13784");
			   partner.setLoanStatus(1);
			   try {
				   client.addProjectPartner(partner);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   
		   @Test
		   public void test_getAllproduct(){
			   ProjectPartner partner = new ProjectPartner();
			   partner.setPage(1);
			   partner.setRows(10);
			   try {
				  List<ProjectPartner> result = client.findAllProjectPartner(partner);
				  System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
			   
		   }

		   @Test
		   public void test_getProductCount(){

			   try {

			} catch (Exception e) {
				e.printStackTrace();
			}
			   
		   }
		   
		   @Test
		   public void test_getProduct(){
			   ProjectPartner partner = new ProjectPartner();
			   partner.setPage(1);
			   partner.setRows(10);
			   try {
				 ProjectPartnerDto result = client.findProjectPartner(11470);
				  System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   
		   @Test
		   public void test_updateProduct(){

			   try {
				   ProjectPartner partner = new ProjectPartner(); 
				   Timestamp time = new Timestamp(new Date().getTime());
				   partner.setProjectId(11439);
				   partner.setIsClosed(1);
				   partner.setRequestStatus(2);
				   partner.setPmUserId(20084);
				   partner.setPartnerNo("13784");
				   partner.setLoanStatus(1);
				   partner.setLoanId("123");
				   partner.setLoanStatus(1);
				   String timeStr = time.toString();
				partner.setLoanTime(timeStr);
				   partner.setLoanResultTime(timeStr);
				   partner.setRepaymentRepurchaseTime(timeStr);
				   partner.setRpResultTime(timeStr);
				   partner.setXiFeeRequestTime(timeStr);
				   partner.setXiFeeResultTime(timeStr);
				   
				   client.updateLoanStatus(partner);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   
		   
}
