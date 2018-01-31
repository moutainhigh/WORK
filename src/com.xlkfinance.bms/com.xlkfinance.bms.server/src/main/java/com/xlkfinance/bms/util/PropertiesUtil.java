package com.xlkfinance.bms.util;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * properties file parser
 * 
 * @author huxl
 * 
 */
public class PropertiesUtil {
   private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
   // 默认从classpath下读取
   private static final String FILE_NAME = "/config.properties";
   private static Properties prop = null;

   static {
      load();
   }

   /**
    * 根据key获取对应的value
    * 
    * @param key
    * @return
    */
   public static String getValue(String key) {

      if (prop == null) {
         load();
      }
      return prop.getProperty(key);
   }

   /**
    * 根据key获取对应的value
    * 
    * @param key
    * @return
    */
   public static int getIntValue(String key) {
      if (prop == null) {
         load();
      }
      return Integer.valueOf(prop.getProperty(key));
   }

   /**
    * 根据key和绑定变量取value
    * 
    * @author liangyanjun
    * @param key
    * @param bodel
    * @return
    */
   public static String getValueByBodel(String key, String bodel) {
      if (prop == null) {
         load();
      }
      return MessageFormat.format(prop.getProperty(key), bodel);
   }

   private static void load() {

      try {
         prop = new Properties();
         InputStream inputStream = PropertiesUtil.class.getResourceAsStream(FILE_NAME);
         prop.load(inputStream);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
         logger.error("application.properties file parser error:" + e);
      }
   }

   public void testName() throws Exception {
      String value = getValue("mail.host");
      System.out.println(value);
   }
}
