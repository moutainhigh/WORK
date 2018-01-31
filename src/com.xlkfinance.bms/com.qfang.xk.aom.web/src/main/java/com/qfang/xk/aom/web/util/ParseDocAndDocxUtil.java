/**
 * @Title: ParseDocAndDocxUtil.java
 * @Package com.xlkfinance.bms.web.util
 * @Description: doc解析
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2015年1月8日 上午11:40:57
 * @version V1.0
 */
package com.qfang.xk.aom.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.formula.functions.Value;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.xmlbeans.XmlException;

import com.xlkfinance.bms.rpc.contract.ContractTempLateParm;

public class ParseDocAndDocxUtil {

	/**
	 * 
	 * @Description: 解析doc文档
	 * @param type
	 * @param fileName
	 * @return
	 * @throws IOException
	 * @throws XmlException
	 * @throws OpenXML4JException
	 * @author: Chong.Zeng
	 * @date: 2015年1月8日 上午11:52:27
	 */
	@SuppressWarnings("resource")
	public static String parseDocumet(String file, HttpServletRequest request) throws IOException, XmlException, OpenXML4JException {
		file = CommonUtil.getRootPath() + file;
		String text = "";
		int index = file.lastIndexOf(".");
		String suffix = file.substring(index + 1, file.length());
		if (suffix.equalsIgnoreCase("doc")) {
			// word 2003： 图片不会被读取
			InputStream is = new FileInputStream(new File(file));
			WordExtractor ex = new WordExtractor(is);
			text = ex.getText();
		} else if (suffix.equalsIgnoreCase("docx")) {
			// word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后
			OPCPackage opcPackage = POIXMLDocument.openPackage(file);
			POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
			text = extractor.getText();
		}
		return text;
	}

	/**
	 * 
	 * @Description: 获取合同模板中的匹配符号
	 * @param text
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月8日 下午12:00:54
	 */
	public static List<String> getDocumentSign(String text) {
		List<String> list = new ArrayList<String>();
		// 匹配正则
		if (null != text && !"".equals(text)) {
			Pattern pattern = Pattern.compile("@[.[^@]]+@");
			Matcher matcher = pattern.matcher(text);

			while (matcher.find()) {
				list.add(matcher.group());
			}
		}
		return removeDuplicate(list);
	}

	/**
	 * 
	 * @Description: 去掉List重复值
	 * @param list
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月15日 上午10:35:49
	 */
	public static List<String> removeDuplicate(List<String> list) {
		HashSet<String> hashSet = new HashSet<String>(list);
		list.clear();
		list.addAll(hashSet);
		return list;
	}

	/**
	 * 
	 * @Description: 判断是否是word文档
	 * @param file
	 * @return
	 * @throws IOException
	 * @author: Chong.Zeng
	 * @date: 2015年1月10日 下午4:57:03
	 */
	public static String bytesToHexString(String file) throws Exception {
		FileInputStream is = null;
		try {
			is = new FileInputStream(file);
			byte[] src = new byte[3];
			is.read(src, 0, src.length);

			StringBuffer sb = new StringBuffer();
			if (src == null || src.length <= 0) {
				return null;
			}
			for (int i = 0; i < src.length; i++) {
				int v = src[i] & 0xFF;
				String hv = Integer.toHexString(v);
				if (hv.length() < 2) {
					sb.append(0);
				}
				sb.append(hv);
			}
			return sb.toString();
		} catch (Exception e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	/**
	 * @Description: 转换参数为的编码格式
	 * @param parm
	 * @return
	 * @throws UnsupportedEncodingException
	 * @author: Chong.Zeng
	 * @date: 2015年1月7日 下午1:23:39
	 */
	public static String getStringCode(FileItem parm) throws UnsupportedEncodingException {
		return new String(parm.getString().getBytes("iso8859-1"), "utf-8");
	}

	/**
	 * 
	 * @Description: 组装list集合
	 * @param map
	 * @return
	 * @author: Chong.Zeng
	 * @throws OpenXML4JException
	 * @throws XmlException
	 * @throws IOException
	 * @date: 2015年1月12日 上午10:08:40
	 */
	public static List<ContractTempLateParm> getParmList(Map<String, Object> map, HttpServletRequest request) throws IOException, XmlException, OpenXML4JException {
		List<ContractTempLateParm> parmList = new ArrayList<ContractTempLateParm>();
		// 解析刚刚上传的模板 获取模板类容
		String docText = ParseDocAndDocxUtil.parseDocumet(String.valueOf(map.get("path")), request);
		if (!"false".equals(docText)) {
			// 使用正则表达式截获所有匹配符号
			List<String> parm = ParseDocAndDocxUtil.getDocumentSign(docText);

			// 去掉重复的。
			HashSet<String> hashSet = new HashSet<String>(parm);
			parm.clear();
			parm.addAll(hashSet);

			Integer contractTemplateId = Integer.parseInt(String.valueOf(map.get("pid")));
			for (String matchFlag : parm) {
				ContractTempLateParm ctp = new ContractTempLateParm();
				ctp.setContractTemplateId(contractTemplateId);
				ctp.setMatchFlag(matchFlag);
				parmList.add(ctp);
			}

		}
		return parmList;
	}
	
	
	 //获取对象属性组
    public static String[] getFiledName(Object o){  
        Field[] fields=o.getClass().getDeclaredFields();  
            String[] fieldNames=new String[fields.length];  
        for(int i=0;i<fields.length;i++){  
            fieldNames[i]=fields[i].getName();  
        }  
        return fieldNames;  
    }  
    
    //通过对象属性获取值
    private static Object getFieldValueByName(String fieldName, Object o) {  
        try {    
            String firstLetter = fieldName.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + fieldName.substring(1);    
            Method method = o.getClass().getMethod(getter, new Class[] {});    
            Object value = method.invoke(o, new Object[] {});    
            return value;    
        } catch (Exception e) {    
            return null;    
        }    
    }   
}
