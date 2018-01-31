/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： Jony <br>
 * ★☆ @time：2017年7月1日下午4:55:09 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.xlkfinance.bms.server.workday;

import static org.junit.Assert.fail;


import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.workday.WorkDay;
import com.xlkfinance.bms.rpc.workday.WorkDayService;


public class WorkDayServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private WorkDayService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("workday", "WorkDayService");
      try {
         client = (WorkDayService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   /**
    * 
    *@author:jony
    *@time:2017年7月1日下午3:03:37
    */
   
   @Test
	public void test_getWorkDay() {
		try {
			WorkDay workDay = new WorkDay();
			List<WorkDay> list = client.getWorkDay(workDay);
			if(list!=null && list.size()>0){
				for (WorkDay obj : list) {
					System.out.println(obj);
				}
			}else{
				System.out.println("成功*****");
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
