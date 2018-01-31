package com.xlkfinance.bms.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xlkfinance.bms.common.util.ExceptionUtil;

/**
 * 短信接口工具类
 * 
 * @author liangyanjun
 * 
 */
public class SMSUtil {
   private static String userCode;// 帐号
   private static String userPass; // 密码
   private static String url; // 密码
   private static Logger logger = LoggerFactory.getLogger(SMSUtil.class);
   /* 初始化化短信接口参数 */
   static {
      try {
         userCode = PropertiesUtil.getValue("userCode");
         userPass = PropertiesUtil.getValue("userPass");
         url = PropertiesUtil.getValue("url");
      } catch (Exception e) {
         throw new RuntimeException("初始化化短信接口错误");
      }
   }

   /** 发送短信 **/
   public static boolean sendMessage(String mob, String msg) {
      String str = "";
      try {
         // 创建HttpClient实例
         HttpClient httpclient = new DefaultHttpClient();

         // 构造一个post对象
         HttpPost httpPost = new HttpPost(url + "/Services/MsgSend.asmx/SendMsg");
         // 添加所需要的post内容
         List<NameValuePair> nvps = new ArrayList<NameValuePair>();
         nvps.add(new BasicNameValuePair("userCode", userCode));
         nvps.add(new BasicNameValuePair("userPass", userPass));
         nvps.add(new BasicNameValuePair("DesNo", mob));
         nvps.add(new BasicNameValuePair("Msg", msg));
         nvps.add(new BasicNameValuePair("Channel", "0"));

         httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));
         HttpResponse response = httpclient.execute(httpPost);
         HttpEntity entity = response.getEntity();
         if (entity != null) {
            InputStream instreams = entity.getContent();
            str = convertStreamToString(instreams);
            // System.out.println(str);
         }

         // HttpRequestSender httpRequest = new HttpRequestSender();
         // //发送 POST 请求
         // String sr=WeixinUtil.httpRequest(
         // "http://h.1069106.com:1210/Services/MsgSend.asmx/SendMsg",
         // "POST",
         // "userCode=XXXXX&userPass=XXXXX&DesNo="+mob+"&Msg="+msg+"&Channel=1");

         Document doc = null;
         doc = DocumentHelper.parseText(str); // 将字符串转为XML

         if (doc == null)
            return false;
         Element rootElt = doc.getRootElement(); // 获取根节点
         if (rootElt == null)
            return false;
         // System.out.println("根节点：" + rootElt.getName()); // 拿到根节点的名称
         // System.out.println("根节点的值：" + rootElt.getText()); // 拿到根节点的名称
         if (rootElt.getText() == null || "".equals(rootElt.getText()))
            return false;
         if (Long.parseLong(rootElt.getText()) > 0) {
            return true;
         } else {
            return false;
         }

      }catch (Exception e) {
          logger.error("发送短信异常：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         return false;
      }
   }

   public static String convertStreamToString(InputStream is) {
      BufferedReader reader = new BufferedReader(new InputStreamReader(is));
      StringBuilder sb = new StringBuilder();

      String line = null;
      try {
         while ((line = reader.readLine()) != null) {
            sb.append(line + "\n");
         }
      } catch (IOException e) {
         e.printStackTrace();
      } finally {
         try {
            is.close();
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
      return sb.toString();
   }
   public void testSMS() {
      Boolean sendMessage = sendMessage("13145860701", "您好，贷后监控【${projectName}】，被发现为异常业务，监控说明”${remark}”，详情请与”${monitorUserName}”联系!【小科金服】");
      System.out.println(sendMessage);
   }
}
