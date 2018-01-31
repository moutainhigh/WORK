/**
 * @Title: ExcelDownload.java
 * @Package com.xlkfinance.bms.web.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: xuweihao
 * @date: 2015年3月16日 下午8:18:55
 * @version V1.0
 */
package com.xlkfinance.bms.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xlkfinance.bms.rpc.beforeloan.LoanApprovalInvoiceInfo;

public class ExcelDownload  
{  
    
	/**
	 * 
	  * @Description: 根据模板名导出Excel 无须处理
	  * @param list——数据
	  * @param templateName——模板名
	  * @param response
	  * @throws Exception
	  * @author: xuweihao
	  * @date: 2015年3月18日 下午2:11:15
	 */
    public static void outToExcel(List list,String templateName,HttpServletResponse response,HttpServletRequest request) throws Exception  
    {  
        // 第一步，创建一个webbook，对应一个Excel文件  
        HSSFWorkbook wb = new HSSFWorkbook();  
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
        HSSFSheet sheet = wb.createSheet("sheet1");  
        
        HSSFCellStyle style = wb.createCellStyle();  
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
        //读取模板
        List<String[]> rowList = getExcelTemplate(templateName,request);
        HSSFRow row = sheet.createRow(1);  
        HSSFCell cell = null;  
        for(int i=1;i<rowList.size();i++){
        	cell = row.createCell((short) i);
        	cell.setCellValue(rowList.get(i)[2]);
        	cell.setCellStyle(style);
        }
        for (int i = 0; i < list.size(); i++){  
        	row = sheet.createRow(i + 2);  
        	String[] fieldName = getFiledName(list.get(i));
        	for(int n = 1; n < rowList.size();n++){
        		for(int m = 0; m < fieldName.length; m++){
        			if(fieldName[m].equals(rowList.get(n)[1])){
        				row.createCell((short) n).setCellValue((String)getFieldValueByName(fieldName[m],list.get(i)));
        			}
        		}
        	}
        }
        // 将文件导出  
        try  {  
        	 String filename = rowList.get(0)[0]+".xls";
             String path = createFile(filename,wb,request);
             sentFile(filename,path,response,request);
             deleteFile(path,request);
        }catch (Exception e){  
            e.printStackTrace();  
        }  
    }  
    
    /**
     * 
      * @Description: 贷款审批单，数据插入
      * @param loanApprovalInvoiceInfo
      * @param response
      * @author: xuweihao
      * @date: 2015年3月21日 上午11:06:24
     */
    public static void replaceModel(LoanApprovalInvoiceInfo loanApprovalInvoiceInfo, HttpServletResponse response,HttpServletRequest request) {    
    	try {  
    		String templateName = CommonUtil.getRootPath()+"放款通知书.xlsx";
        	//读取模板
            XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(templateName)); 
            //第一个sheet
            XSSFSheet sheet = wb.getSheetAt(0);  
            //获取最后行数
            int lastRowNum = sheet.getLastRowNum();  
            //获取审批单属性名
            String[] fieldName = getFiledName(loanApprovalInvoiceInfo);
            XSSFRow row = null; 
            XSSFCell cell = null;  
            //匹配符正则表达
            Pattern pattern = Pattern.compile("#[.[^#]]+#");
            // 循环读取  
            for (int i = 0; i <= lastRowNum; i++) {  
                row = sheet.getRow(i); 
                if (row != null) {
                	//总共24列
                	for(int m=0;m<24;m++){
                		Matcher matcher = pattern.matcher(getCellValue(row.getCell(m)));
                		cell = row.getCell(m);
                		//判断是否匹配正则表达式
                		if(matcher.find()){
                			//去除#字符号
                			String ma = matcher.group().replace("#", "");
                			String conditions = ma.substring(0, 1);
                			if(conditions.equals("率")){
                				ma = ma.substring(1);
                			}
                			for(int n=0;n<fieldName.length;n++){
                				if(ma.equals(fieldName[n])){
                					//判断数据是否为空
                					if(getFieldValueByName(fieldName[n],loanApprovalInvoiceInfo)!=null){
                						//判断数据是否是int类型
                						if(getFieldValueByName(fieldName[n],loanApprovalInvoiceInfo).getClass().getName().equals("java.lang.Integer")){
                							//判断是否是费率
                							if(conditions.equals("率")){
                								cell.setCellValue(((Integer)(getFieldValueByName(fieldName[n],loanApprovalInvoiceInfo))*1.0/100));
                                			}else{
                                				cell.setCellValue((Integer)(getFieldValueByName(fieldName[n],loanApprovalInvoiceInfo)));
                                			}
                						}else{
                							String value = (String)getFieldValueByName(fieldName[n],loanApprovalInvoiceInfo);
                							if(ma.equals("cusType")){
                        						if(value.equals("个人")){
                        							value = "个人■供应链□非供应链□\n企业□供应链□非供应链□";
                        						}else{
                        							value = "个人□供应链□非供应链□\n企业■供应链□非供应链□";
                        						} 
                            				}
                							cell.setCellValue(value);
                						}
                					}
                        		}
                			}
                			
                		}
                	}
                }
            }  
            // 输出文件     
            String filename = "放款审批单.xlsx";
            String path = createFileXlsx(filename,wb,request);
            sentFile(filename,path,response,request);          	
            deleteFile(path,request);
        } catch (Exception e) {   
            e.printStackTrace();  
        } 
    }  
    
    
    
    /**
     * 
      * @Description: 财务管理
      * @param templateName
      * @author: xuweihao
     * @throws Exception 
      * @date: 2015年3月21日 上午11:11:49
     */
    public static void financeToExcel(List list,String templateName,HttpServletResponse response,HttpServletRequest request) throws Exception{
    	try {
    		Pattern pattern = Pattern.compile("#[.[^#]]+#");
    		// 第一步，创建一个webbook，对应一个Excel文件  
    		HSSFWorkbook wb = new HSSFWorkbook();  
    		// 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet  
    		HSSFSheet sheet = wb.createSheet("sheet1");
    		HSSFCellStyle style = wb.createCellStyle();  
    		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式  
    		//读取模板
    		List<String[]> rowList = getExcelTemplate(templateName,request);
    		HSSFRow row = sheet.createRow(1);  
            HSSFCell cell = null;  
            for(int i=1;i<rowList.size();i++){
            	cell = row.createCell((short) i);
            	cell.setCellValue(rowList.get(i)[2]);
            	cell.setCellStyle(style);
            }
            String[] heji = new String[rowList.size()];
            for (int i = 0; i < list.size(); i++){  
            	row = sheet.createRow(i + 2);  
            	String[] fieldName = getFiledName(list.get(i));
            	for(int n = 1; n < rowList.size();n++){
            		Matcher matcher = pattern.matcher(rowList.get(n)[1]);
            		cell = row.createCell((short) n);
            		cell.setCellStyle(style);
            		sheet.setColumnWidth((short)n, (short)4000);
        			if(matcher.find()){
        				String ma = matcher.group().replace("#", "");
        				ma = ma.replace("=", "");
        				ma = ma.replace("1", String.valueOf(i+3));
    					cell.setCellFormula(ma);
    					heji[n]=String.valueOf(n);
    					break;
        			}else{
        				for(int m = 0; m < fieldName.length; m++){
                			if(fieldName[m].equals(rowList.get(n)[1])){
                				if(getFieldValueByName(fieldName[m],list.get(i))!=null){
            						//判断数据是否是int类型
            						if(getFieldValueByName(fieldName[m],list.get(i)).getClass().getName().equals("java.lang.Integer")){
            							cell.setCellValue((Integer)(getFieldValueByName(fieldName[m],list.get(i))));
            							heji[n]=String.valueOf(n);
            						}else{
            							String value = (String)getFieldValueByName(fieldName[m],list.get(i));
            							cell.setCellValue(value);
            						}
            					}
                			}
                		}
        			}
            	}
            	if(i+1==list.size()){
            		row = sheet.createRow(i + 3);
            		for(int j=1;j<rowList.size();j++){
                    	cell = row.createCell((short) j);
                    	if(heji[j]!=null){                    		
                    		cell.setCellFormula("SUM("+getColLetter(j)+3+":"+getColLetter(j)+(i+2+1)+")");
                    	}
                    	cell.setCellStyle(style);
                    }
            	}
            }
            String filename = rowList.get(0)[0]+".xls";
            String path = createFile(filename,wb,request);
            sentFile(filename,path,response,request);            	
            deleteFile(path,request);
    	} catch (IOException e) {
    		e.printStackTrace();
    	}
    }
    
    
    //发送文件
    public static void sentFile(String filename,String path,HttpServletResponse response,HttpServletRequest request) throws IOException{
    	try{
    		response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));
    		//path =  CommonUtil.getRootPath()+filename;
    		InputStream inputStream = new FileInputStream(new File(path));
    		OutputStream os = response.getOutputStream();
    		byte[] b = new byte[2048];
    		int length;
    		while ((length = inputStream.read(b)) > 0) {
    			os.write(b, 0, length);
    		}
    		os.close();
    		inputStream.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }
    
    
    //创建xls文件
    public static String createFile(String filename,HSSFWorkbook wb,HttpServletRequest request) throws Exception {
    	String uploadPath = CommonUtil.getRootPath()+getDateToString()+filename;
		try {
			System.out.println(uploadPath);
			File f = new File(uploadPath);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(uploadPath);
			wb.write(outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadPath;
	}
    
  //创建xlsx文件
    public static String createFileXlsx(String filename,XSSFWorkbook wb,HttpServletRequest request) throws Exception {
    	String uploadPath = CommonUtil.getRootPath()+getDateToString()+filename;
		try {
			System.out.println(uploadPath);
			File f = new File(uploadPath);
			if (!f.exists()) {
				f.createNewFile();
			}
			FileOutputStream outStream = new FileOutputStream(uploadPath);
			wb.write(outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return uploadPath;
	}
    
    //删除文件
    public static void deleteFile(String path,HttpServletRequest request){
    	try {
    		File f = new File(path);
    		if (f.exists()) {
    			f.delete();
    		}
    	}catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
    //读取模板
    public static List<String[]> getExcelTemplate(String templateName,HttpServletRequest request) throws IOException{
		String Path = CommonUtil.getRootPath()+"Excel模板.xls";
    	File file = new File(Path);  
        if (!file.exists()) {  
            throw new IOException("文件名为" + file.getName() + "Excel文件不存在！");  
        }  
        HSSFWorkbook wb = null;// 用于Workbook级的操作，创建、删除Excel  
        List<String[]> list = new ArrayList<String[]>();
        try {  
            // 读取Excel  
            wb = new HSSFWorkbook(new FileInputStream(file));
            // 读取Excel 97-03版，xls格式  
            list = readExcel(wb,templateName);
        } catch (IOException e) {  
            e.printStackTrace();  
        } 
    	return list;
    }

    
    //读取Excel方法
    public static List<String[]> readExcel(Workbook wb,String templateName) {  
    	List<String[]> list = new ArrayList<String[]>();
        Sheet sheet = null;  
      //只操作一个sheet  
        // 获取设定操作的sheet(如果设定了名称，按名称查，否则按索引值查)  
        sheet =wb.getSheet("Sheet1");
        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();  
        Row row = null; 
        // 循环读取  
        for (int i = 0; i <= lastRowNum; i++) {  
            row = sheet.getRow(i);  
            if (row != null) {   
            	if(templateName.equals(getCellValue(row.getCell(0)))){ 
            		String[] sheetName = {getCellValue(row.getCell(1))};
            		list.add(sheetName);
            		i = i+1;
            		row = sheet.getRow(i); 
            		while(row != null){
            			String[] ss = {getCellValue(row.getCell(1)),getCellValue(row.getCell(2)),getCellValue(row.getCell(3))};
            			System.out.println(getCellValue(row.getCell(1))+getCellValue(row.getCell(2))+getCellValue(row.getCell(3)));
                        list.add(ss);
                        row = sheet.getRow(++i);
            		}
            		break;
          	  	} 
            }  
        }  
        return list;  
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
    
    //通过属性组 获取 值组
    public static Object[] getFiledValues(Object o){  
        String[] fieldNames=getFiledName(o);  
        Object[] value=new Object[fieldNames.length];  
        for(int i=0;i<fieldNames.length;i++){  
            value[i]=getFieldValueByName(fieldNames[i], o);  
        }  
        return value;  
    }  
    
    //获得单元格数值
    private static String getCellValue(Cell cell) {  
        Object result = "";  
        if (cell != null) {  
            switch (cell.getCellType()) {  
            case Cell.CELL_TYPE_STRING:  
                result = cell.getStringCellValue();  
                break;  
            case Cell.CELL_TYPE_NUMERIC:  
                result = cell.getNumericCellValue();  
                break;  
            case Cell.CELL_TYPE_BOOLEAN:  
                result = cell.getBooleanCellValue();  
                break;  
            case Cell.CELL_TYPE_FORMULA:  
                cell.setCellFormula(cell.getCellFormula());  
                break;  
            case Cell.CELL_TYPE_ERROR:  
                result = cell.getErrorCellValue();  
                break;  
            case Cell.CELL_TYPE_BLANK:  
                break;  
            default:  
                break;  
            }  
        }  
        return result.toString();  
    }  
    
    
    
    
   /* public static Map convertBean(Object bean)
    		throws IntrospectionException, IllegalAccessException, InvocationTargetException {
    	Class type = bean.getClass();
    	Map returnMap = new HashMap();
    	BeanInfo beanInfo = Introspector.getBeanInfo(type);

    	PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
    	for (int i = 0; i< propertyDescriptors.length; i++) {
    		PropertyDescriptor descriptor = propertyDescriptors[i];
    		String propertyName = descriptor.getName();
    		if (!propertyName.equals("class")) {
    			Method readMethod = descriptor.getReadMethod();
    			Object result = readMethod.invoke(bean, new Object[0]);
    			if (result != null) {
    				returnMap.put(propertyName, result);
    			} else {
    				returnMap.put(propertyName, "");
    			}
    		}
    	}
    	return returnMap;
    }*/
    

   /* public static int[] isMergedRegion(HSSFSheet sheet,int row ,int column)  
    {  
    	int[] xx = new int[]{};
    	int sheetMergeCount = sheet.getNumMergedRegions();  
    	for (int i = 0; i < sheetMergeCount; i++) {  
    		Region ca = sheet.getMergedRegionAt(i);  
    		int firstColumn = ca.getColumnFrom();  
    		int lastColumn = ca.getColumnTo();  
    		int firstRow = ca.getRowFrom();  
    		int lastRow = ca.getRowTo();  
    		if(row >= firstRow && row <= lastRow)  
    		{  
    			if(column >= firstColumn && column <= lastColumn)  
    			{  
    				xx[0] = firstColumn;
    				xx[1] = lastColumn;
    				xx[2] = firstRow;
    				xx[3] = lastRow;
    			}  
    		}  
    	}  
    	return xx;  
    }  */
     
    //数字转字母
    public static String getColLetter(int colIndex)
    {
     String ch = "";
        if (colIndex  < 26)
            ch = "" + (char)((colIndex) + 65);
        else
           ch = "" + (char)((colIndex) / 26 + 65 - 1) + (char)((colIndex) % 26 + 65);
        return ch;
    }
    
    //添加时间标记
    public static String getDateToString() {
		Long time = (new Date()).getTime();
		return String.valueOf(time);
	}
    
}  
