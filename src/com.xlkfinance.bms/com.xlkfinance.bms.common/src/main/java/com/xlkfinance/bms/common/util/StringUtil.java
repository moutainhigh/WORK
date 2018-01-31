/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月13日下午6:58:03 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.xlkfinance.bms.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月13日下午6:58:03 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class StringUtil {
   public static boolean isBlank(String... strs) {
      for (String string : strs) {
         if (string == null || string.length() <= 0) {
            return true;
         }
      }
      return false;
   }
   public static List<Integer> StringToList(String strs) {
      ArrayList<Integer> arrayList = new ArrayList<>();
      if (isBlank(strs)) {
         return arrayList;
      }
      String[] split = strs.split(",");
      for (String string : split) {
         arrayList.add(Integer.parseInt(string));
      }
      return arrayList;
   }
   
   /**
    * 将map 的key拼接成字符串
    * @param map
    * @return
    */
   public static String mapKeyToString(Map<String,Object> map){
	   StringBuffer sb = new StringBuffer();
	   String resultStr = "";
	   for (String key : map.keySet()) {
		   sb.append(key).append(",");
	   }
	   //去掉最后一个逗号
	   if(sb.length() > 0){
		   resultStr = sb.toString().substring(0,sb.toString().length()-1);
	   }
	   return resultStr;
   }
   
   
   /**
    * 替换掉首尾字符串
    * @param str
    * @param seq
    * @return
    */
   public static String replayFirstAndEnd(String str,String seq){
	   str = str.replaceFirst(seq, "");
	   str = str.substring(0,str.lastIndexOf(seq));
	   return str;
   }
	
	/**
	 * lpad:(左补位). <br/>
	 * @author dulin
	 * @return
	 */
	public static String lpad(String content, String pading, int length) {
		return pad(content, pading, length, true);
	}
	
	/**
	 * rpad:(左补位). <br/>
	 * @author dulin
	 * @return
	 */
	public static String rpad(String content, String pading, int length) {
		return pad(content, pading, length, false);
	}
	
	/**
	 * pad:(补位). <br/>
	 * @author dulin
	 * @return
	 */
	public static String pad(String content, String pading, int length, boolean direction) {
		if (length > 0) {
			return pad(direction ? pading + content : content + pading, pading, length - 1, direction);
		} 
		return content;
	}
}

