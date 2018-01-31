package com.xlkfinance.bms.server.system;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.system.ProblemFeedback;
import com.xlkfinance.bms.rpc.system.SysProblemFeedbackService;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日下午6:32:44 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class SysProblemFeedbackServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private SysProblemFeedbackService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("system", "SysProblemFeedbackService");
      try {
         client = (SysProblemFeedbackService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_addProblemFeedback() {
      try {
         ProblemFeedback problemFeedback=new ProblemFeedback();
         problemFeedback.setCreaterId(20048);
         problemFeedback.setFeedbackContent("afsdkhjfkihKJhkjhkjhk很快就把客户即可交换空间看见会看见afsdkhjfkihKJhkjhkjhk很快就把客户即可交换空间看见会看见");
         client.addProblemFeedback(problemFeedback);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
