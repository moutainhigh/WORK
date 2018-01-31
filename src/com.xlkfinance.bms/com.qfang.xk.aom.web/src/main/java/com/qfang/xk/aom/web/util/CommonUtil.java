package com.qfang.xk.aom.web.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月30日上午10:46:19 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class CommonUtil {
   public static String getRootPath() {
//      String classPath = CommonUtil.class.getClassLoader().getResource("/").getPath();
      String rootPath = "";
//      // windows下
//      if ("\\".equals(File.separator)) {
//         // System.out.println("windows");
//         rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
//         rootPath = rootPath.replace("/", "\\");
//      }
//      // linux下
//      if ("/".equals(File.separator)) {
//         // System.out.println("linux");
//         rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
//         rootPath = rootPath.replace("\\", "/");
//      }
//      rootPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator)+1);
      rootPath=PropertiesUtil.getValue("uploanFilePath");
      return rootPath;
   }
   public static void main(String[] args) {
      String rootPath="G:/apache-tomcat-7.0.54-windows-i64_eclipse_2/apache-tomcat-7.0.54//webapps//";
      StringBuffer sb=new StringBuffer();
      String[] strings = rootPath.split("/");
      for (int i = 0; i < strings.length-2; i++) {
         sb.append(strings[i]);
         sb.append(File.separator);
      }
      System.out.println(sb.toString());
   }
   
	/**
	 * 字符串集合转换成list
	 * @param str 	字符串
	 * @param type	类型 Integer String
	 * @param separator 分割符 如 1,2,3
	 * @return List
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List getSepStrtoList(String str, String type, String separator) {

		List list = new ArrayList();
		if (str != null && str.length() > 0) {
			String strArray[] = str.split(separator);
			for (String string : strArray) {
				if (string != null && string!="") {
					// Integer 类型
					if ("Integer".equals(type)) {
						list.add(Integer.parseInt(string.trim()));
					}
					// 字符串类型
					else if ("String".equals(type)) {
						list.add(string);
					}
				}
			}
		}
		return list;
	}
}
