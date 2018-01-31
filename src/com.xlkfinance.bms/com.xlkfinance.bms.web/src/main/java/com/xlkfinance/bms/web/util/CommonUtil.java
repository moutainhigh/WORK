package com.xlkfinance.bms.web.util;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
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
      String classPath = CommonUtil.class.getClassLoader().getResource("/").getPath();
      String rootPath = "";
      // windows下
      if ("\\".equals(File.separator)) {
         // System.out.println("windows");
         rootPath = classPath.substring(1, classPath.indexOf("/WEB-INF/classes"));
         rootPath = rootPath.replace("/", "\\");
      }
      // linux下
      if ("/".equals(File.separator)) {
         // System.out.println("linux");
         rootPath = classPath.substring(0, classPath.indexOf("/WEB-INF/classes"));
         rootPath = rootPath.replace("\\", "/");
      }
      rootPath = rootPath.substring(0, rootPath.lastIndexOf(File.separator)+1);
      //rootPath=FileUtil.handlePathByLevel(rootPath, 2)+"erpFile\\";
      return rootPath;
   }
   
   /**
    * 处理系统路径
    * @param path
    * @return
    */
   public static String dealPath(String path) {
	      // windows下
	      if ("\\".equals(File.separator)) {
	    	  path = path.replace("/", "\\");
	      }
	      // linux下
	      if ("/".equals(File.separator)) {
	    	  path = path.replace("\\", "/");
	      }
	      return path;
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
	
	
	/**
	 * 格式化数字，防止数值长变成科学计数法显示
	 * @param value
	 * @return
	 */
    public static String formatDoubleNumber(Double value) {
        if(value != null){
            if(value.doubleValue() != 0.00){
                java.text.DecimalFormat df = new java.text.DecimalFormat("########.00");
                return df.format(value.doubleValue());
            }else{
                return "0.00";
            }
        }
        return "";
    }
    
    
    //去除数组中重复的记录,以逗号隔开 
    public static String array_unique(String[] a) {  
        // array_unique  
        List<String> list = new LinkedList<String>();  
        for(int i = 0; i < a.length; i++) {  
            if(!list.contains(a[i])) {  
                list.add(a[i]);  
            }  
        }  
        
        String returnStr = "";
        for (int i = 0; i < list.size(); i++) {
			
        	if(i == 0){
        		returnStr += list.get(i);
        	}else{
        		returnStr += ","+list.get(i);
        	}
        	
		}
        return returnStr;  
    }  
    
    
    
    public static void main(String[] args) {
		
    	String aaa= "";
    	System.out.println(array_unique(aaa.split(",")));
	}
}
