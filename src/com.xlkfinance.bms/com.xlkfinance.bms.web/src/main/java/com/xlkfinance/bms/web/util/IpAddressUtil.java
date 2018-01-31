package com.xlkfinance.bms.web.util;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
 
/** 
 * 自定义访问对象工具类 
 * 
 * 获取对象的IP地址等信息 
 * @author X-rapido 
 * 
 */ 
public class IpAddressUtil { 
 
  /** 
   * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
   * 
   * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
   * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
   * 
   * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
   * 192.168.1.100 
   * 
   * 用户真实IP为： 192.168.1.110 
   * 
   * @param request 
   * @return 
   */ 
  public static String getIpAddress(HttpServletRequest request) { 
    String ip = request.getHeader("x-forwarded-for"); 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
      ip = request.getHeader("Proxy-Client-IP"); 
    } 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
      ip = request.getHeader("WL-Proxy-Client-IP"); 
    } 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
      ip = request.getHeader("HTTP_CLIENT_IP"); 
    } 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
      ip = request.getHeader("HTTP_X_FORWARDED_FOR"); 
    } 
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) { 
      ip = request.getRemoteAddr(); 
    } 
    return ip; 
  } 
  
  
  /**
   * 执行单条指令
   * @param cmd 命令
   * @return 执行结果
   * @throws Exception
   */
  public static String command(String cmd) throws Exception{
      Process process = Runtime.getRuntime().exec(cmd);
      process.waitFor();
      InputStream in = process.getInputStream();
      StringBuilder result = new StringBuilder();
      byte[] data = new byte[256];
      while(in.read(data) != -1){
          String encoding = System.getProperty("sun.jnu.encoding");
          result.append(new String(data,encoding));
      }
      return result.toString();
  }
  
  /**
   * 获取mac地址
   * @param ip
   * @return
   * @throws Exception 
   */
  public static String getMacAddress(String ip) throws Exception{
      String result = command("ping "+ip+" -n 2");
      if(result.contains("TTL")){
          result = command("arp -a "+ip);
      }
      String regExp = "([0-9A-Fa-f]{2})([-:][0-9A-Fa-f]{2}){5}";
      Pattern pattern = Pattern.compile(regExp);
      Matcher matcher = pattern.matcher(result);
      StringBuilder mac = new StringBuilder();
      while (matcher.find()) {
          String temp = matcher.group();
          mac.append(temp);
      }
      return mac.toString();
  }
  
  public static void main(String[] args)throws Exception {
	System.out.println("123123"+getMacAddress("127.0.0.1"));
  }
   
} 