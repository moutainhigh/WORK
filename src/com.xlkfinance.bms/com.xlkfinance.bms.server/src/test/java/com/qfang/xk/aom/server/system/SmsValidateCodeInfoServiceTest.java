/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-07 09:23:22 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 短信验证码信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.qfang.xk.aom.server.system;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfo;
import com.qfang.xk.aom.rpc.system.SmsValidateCodeInfoService;
import com.xlkfinance.bms.common.util.DateUtils;

public class SmsValidateCodeInfoServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private SmsValidateCodeInfoService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("system", "SmsValidateCodeInfoService");
      try {
         client = (SmsValidateCodeInfoService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      SmsValidateCodeInfo smsValidateCodeInfo = new SmsValidateCodeInfo();
      smsValidateCodeInfo.setTelphone("13145860701");
      smsValidateCodeInfo.setCode("code3");
      smsValidateCodeInfo.setSendDate(DateUtils.getCurrentDateTime());
      smsValidateCodeInfo.setCreateDate(DateUtils.getCurrentDateTime());
      smsValidateCodeInfo.setCreatorId(2);
      smsValidateCodeInfo.setCategory(1);
      smsValidateCodeInfo.setStatus(2);
      client.insert(smsValidateCodeInfo);
   }

   @Test
   public void test_update() throws Exception {
      SmsValidateCodeInfo smsValidateCodeInfo = new SmsValidateCodeInfo();
      smsValidateCodeInfo.setPid(1);
      smsValidateCodeInfo.setTelphone("telphone3");
      smsValidateCodeInfo.setCode("code2");
      smsValidateCodeInfo.setSendDate(DateUtils.getCurrentDateTime());
      smsValidateCodeInfo.setCreateDate(DateUtils.getCurrentDateTime());
      smsValidateCodeInfo.setCreatorId(2);
      smsValidateCodeInfo.setCategory(2);
      smsValidateCodeInfo.setStatus(2);
      client.update(smsValidateCodeInfo);
   }

   @Test
   public void test_getAll() throws Exception {
      SmsValidateCodeInfo smsValidateCodeInfo = new SmsValidateCodeInfo();
      // smsValidateCodeInfo.setStatus(1);
//      smsValidateCodeInfo.setCode("code2");
      List<SmsValidateCodeInfo> list = client.getAll(smsValidateCodeInfo);
      for (SmsValidateCodeInfo obj : list) {
         System.out.println(obj);
      }
   }

   @Test
   public void test_getCount() throws Exception {
      SmsValidateCodeInfo smsValidateCodeInfo = new SmsValidateCodeInfo();
      // smsValidateCodeInfo.setStatus(1);
      smsValidateCodeInfo.setCode("code2");
      int count = client.getCount(smsValidateCodeInfo);
      System.out.println(count);
   }
   @Test
   public void test_getTodayMsgCountByPhone() throws Exception {
      int count = client.getTodayMsgCountByPhone("13145860701", 2);
      System.out.println(count);
   }

   @Test
   public void test_getById() throws Exception {
      SmsValidateCodeInfo obj = client.getById(2);
      System.out.println(obj);
   }

   @Test
   public void test_deleteById() throws Exception {
      client.deleteById(2);
   }

   @Test
   public void test_deleteByIds() throws Exception {
      List<Integer> pids = new ArrayList<Integer>();
      pids.add(3);
      pids.add(4);
      client.deleteByIds(pids);
   }
}