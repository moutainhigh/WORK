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
import com.xlkfinance.bms.rpc.product.Product;
import com.xlkfinance.bms.rpc.product.ProductService;

public class ProductServiceTest {

	private String serverIp = "127.0.0.1";
	   private int serverPort = 19090;
	   private String basePackage = "com.xlkfinance.bms.rpc";
	   private ProductService.Client client;
	   
	   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
		      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
		      return clientFactory;
		   }

		   @Before
		   public void init() {
		      BaseClientFactory clientFactory = getFactory("product", "ProductService");
		      try {
		         client = (ProductService.Client) clientFactory.getClient();
		      } catch (ThriftException e) {
		         e.printStackTrace();
		         fail(e.getMessage());
		      }
		   }
		   
		   @Test
		   public void test_addProduct(){
			   Product product = new Product();
			   product.setCityId(2);
			   product.setProductName("現金贖樓");
			   product.setProductNumber("xjsl2016011722");
			   product.setStatus(1);
			   Timestamp time = new Timestamp(new Date().getTime());
				product.setUpdateDate(time.toString());
				product.setCreateDate(time.toString());
			   product.setProductType(1);
			   product.setCreaterId(20084);
			   
			   product.setLoanMoney("300万$$500万");
			   product.setLoanTerm("3个月以内");
			   product.setYearLoanInterest(12.53);
			   product.setMonthLoanInterest(1.42);
			   product.setManageRate(5.12);
			   product.setOtherRate(1.25);
			   product.setMarketAvgInterest(12.01);
			   product.setFloatingRate(1.2);
			   product.setRemark("daddamsdqwdbiqwgdgfertgjregerjgmrejoger");
			   
			   try {
				client.addProduct(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   
		   @Test
		   public void test_getAllproduct(){
			   Product product = new Product();
			   product.setCityId(2);
			   product.setPage(1);
			   product.setRows(10);
			   try {
				   List<GridViewDTO> list = client.getAllProduct(product);
				   for(GridViewDTO dto : list){
					   System.out.println(dto);
				   }
				   
			} catch (Exception e) {
				e.printStackTrace();
			}
			   
		   }

		   @Test
		   public void test_getProductCount(){
			   Product product = new Product();
			   product.setCityId(2);
			   product.setPage(1);
			   product.setRows(10);
			   try {
				   int i = client.getAllProductCount(product);
				   System.out.println(i);
			} catch (Exception e) {
				e.printStackTrace();
			}
			   
		   }
		   
		   @Test
		   public void test_getProduct(){
			   try {
				Product product = client.getProductById(12);
				System.out.println(product);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   
		   @Test
		   public void test_updateProduct(){
			   Product product = new Product();
			   product.setPid(10);
			   product.setCityId(2);
			   product.setProductName("現金贖樓");
			   product.setProductNumber("xjsl20160117123213");
			   product.setStatus(1);
			   Timestamp time = new Timestamp(new Date().getTime());
				product.setUpdateDate(time.toString());
				product.setCreateDate(time.toString());
			   product.setProductType(2);
			   product.setCreaterId(20084);
			   
			   product.setLoanMoney("100万$$500万");
			   product.setLoanTerm("4个月以内");
			   product.setYearLoanInterest(12.53);
			   product.setMonthLoanInterest(1.42);
			   product.setManageRate(5.12);
			   product.setOtherRate(1.25);
			   product.setMarketAvgInterest(12.01);
			   product.setFloatingRate(1.2);
			   product.setRemark("发旺旺发我发我范文芳问题提供天天玩太晚vrbgrvqwrqVRQWVRWRVQEQE公司给我吧沃特温柔v");
			   try {
				int result= client.updateProduct(product);
				System.out.println(result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		   }
		   
		   
}
