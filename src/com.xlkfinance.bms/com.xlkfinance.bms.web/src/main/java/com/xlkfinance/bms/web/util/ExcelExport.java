/**
 * @Title: ExcelExport.java
 * @Package com.xlkfinance.bms.web.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: qiancong.Xian
 * @date: 2015年4月15日 下午2:08:45
 * @version V1.0
 */
package com.xlkfinance.bms.web.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.xlkfinance.bms.common.constant.Constants;

/**
 * 导出文件的公用类
  * @ClassName: ExcelExport
  * @Description: TODO
  * @author: qiancong.Xian
  * @date: 2015年4月15日 下午2:08:55
 */
public class ExcelExport {
	

	  public static void main(String args []) throws Exception
	  {
		  String templateFilePath = "E:\\xlk_work\\temp\\放款通知书.xlsx";
		  String filename = "test.xlsx";
		  Map<String,Object> params = new HashMap<String,Object>(); 
		   Map<String,String> param1 = new HashMap<String,String>();
		   param1.put("name", "测试1");
		   param1.put("cusType", "个人");
		   param1.put("meetingNo", "123569988");
		   param1.put("contractNo", "XL12326549833");
		   param1.put("emt", "365.33");
		   
		   params.put("bean", param1);
		   
		   List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		   params.put("aabel", list);
		   
		   for(int i=0;i<5;i++)
		   {
			   Map lb = new HashMap();
			   lb.put("lableContent","的路口的史莱克");
			   lb.put("value","sss");
			   lb.put("p1","20000.33");
			   lb.put("p2","500.25");
			   lb.put("p3","300.33");
			   lb.put("p4","620.36");
			   list.add(lb);
		   }
		   
		   
		  // System.out.println(ExcelExport.getColumnName(1));
		  outToExcel(params, templateFilePath,filename, null, null);
	  }
	
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
    public static void outToExcel(Map<String,Object> params,String templateFilePath,String outPutFileName,HttpServletResponse response,HttpServletRequest request) throws Exception  
    {  
    	Workbook wb = getHSSFWorkbook(templateFilePath);
    	Sheet sheet = wb.getSheetAt(0);  
    	
        //读取模板,获取所有标签集合
        List<LabelBean> labelBeanList = getLabelBeanList(params,wb);
        Cell cell = null;
        Row row = null;
        for(LabelBean labelBean: labelBeanList)
        {
        	// 如果是要创建行的
        	if(labelBean.createRow)
        	{
        		sheet.createRow(labelBean.getRow());
        	}
        	else
        	{
        		// 清空当前行的数据（用于循环没有数据）
        		if(labelBean.removeRow)
        		{
        			sheet.removeRow(sheet.getRow(labelBean.getRow()));
        		}
        		// 如果要创建单元格的
        		else if(labelBean.createCell)
        		{
        			row = sheet.getRow(labelBean.getRow());
        			cell = row.createCell(labelBean.getCol());
        			cell.setCellStyle(labelBean.getStyle());
        			
        			//  如果是一个计算公式
        			if(labelBean.isCellFormula())
        			{
        				cell.setCellFormula(labelBean.getValue());
        			}
        			else
        			{
        				setCellValue(cell, labelBean.getValue());
        			}
        		}
        		// 替换原来的值的
        		else
        		{
    				cell = sheet.getRow(labelBean.getRow()).getCell(labelBean.getCol());
        			setCellValue(cell, labelBean.getValue());
        		}
        	}
        }	
        
        String loaclAbsolutePath = null;
        // 将文件导出  
        try  {  
//        	  String outFilePath="E:\\xlk_work\\temp\\a";
//          	  loaclAbsolutePath =  createTestFile(outFilePath,outPutFileName, wb);
        	  loaclAbsolutePath =  createLocalFile(outPutFileName,wb,request);
              sentFile(outPutFileName,loaclAbsolutePath,response,request);
        }catch (Exception e){  
        }  
        finally
        {
        	//  最后删除临时文件
           deleteFile(loaclAbsolutePath,outPutFileName);
        }
    }  
    
    private static void setCellValue(Cell cell,String value)
    {
    	try
    	{
    		//判断value里面的值大于10位数，如果大于就不转
    		double t = Double.parseDouble(value);
    		if(t>999999999L)
    		{
    			cell.setCellValue(value);
    		}	
    		else
    		{
    			cell.setCellValue(t);
    		}
    	}
    	catch(Exception e)
    	{
    		cell.setCellValue(value);
    	}
    }
    
    public static Workbook getHSSFWorkbook(String templatePath)
    {
    	
    	Workbook wb = null;
    	try {  
    		File file = new File(templatePath);  
            // 读取Excel (2007后的版本) 
            if(templatePath.endsWith(".xlsx"))
            {
            	wb = new XSSFWorkbook(new FileInputStream(file));
            }
            //  (2003 - 2007的版本) 
            else
            {
            	wb = new HSSFWorkbook(new FileInputStream(file));
            }	
            
        } catch (IOException e) {  
        } 
    	return wb;
    }
    
    //读取模板
    public static List<LabelBean> getLabelBeanList(Map<String,Object> params,Workbook wb){
        List<LabelBean> list = new ArrayList<LabelBean>();
            list = readExcel(params,wb);
    	return list;
    }
    
    //读取Excel方法
    public static List<LabelBean> readExcel(Map<String,Object> params,Workbook wb) {  
    	List<LabelBean> labelBeanList = new ArrayList<LabelBean>();
        Sheet sheet = null;  
         //只操作一个sheet  
        sheet =wb.getSheetAt(0); 
        //获取最后行号  
        int lastRowNum = sheet.getLastRowNum();  
        Row row = null; 
        // 循环读取  
        for (int i = 0; i <= lastRowNum; i++) {  
            row = sheet.getRow(i);  
            //  行
            if (row != null) {   
            	
            	// 从左到右读取
            	int j=0;
            	int accumulativeNullNum = 0;
            	//  连续10列为空，认为是没有数据了
            	while(accumulativeNullNum<10)
            	{
            		if(null == row.getCell(j))
            		{
            			accumulativeNullNum++;
            			j++;
            			continue;
            		}
            		
            		String cellValue = getCellValue(row.getCell(j));
            		row.getCell(j).getCellStyle();
            		if(cellValue.trim().length()==0)
            		{
            			accumulativeNullNum++;
            		}	
            		else
            		{
            			CellStyle cellStyle = row.getCell(j).getCellStyle();
            			checkCellForLabelBean(labelBeanList, i, j,cellValue, cellStyle);
            			accumulativeNullNum=0;
            		}
            		
            		j++;
            	}
            }
         }
        
        labelBeanList = parseLabelBean(params, labelBeanList);
        return labelBeanList;  
    }  
    
    /**
      * @Description: 检查当前的单元格,并且解析标签
      * @param params
      * @param labelBeanList
      * @author: qiancong.Xian
      * @return 解析标签后，要增加的行数（主要是解析for标签）
      * @date: 2015年4月16日 上午10:37:05
     */
    private static void checkCellForLabelBean(List<LabelBean> labelBeanList,int rowNum,int colNum,String cellValue,CellStyle cellStyle)
    {
    	LabelBean labelBean = checkCell(rowNum, colNum, cellValue);
    	if(null != labelBean)
    	{
    		labelBean.setStyle(cellStyle);
    		labelBeanList.add(labelBean);
    	}
    }
    
    /**
     * 找出所有带标签的cell
      * @param rowNum
      * @param colNum
      * @param cellValue
      * @return
      * @author: qiancong.Xian
      * @date: 2015年4月16日 上午11:00:02
     */
    private static LabelBean checkCell(int rowNum,int colNum,String cellValue)
    {
    	// 属于标签结构${}
    	if(cellValue.startsWith("${") && cellValue.endsWith("}"))
    	{
    		LabelBean labelBean = new LabelBean();
    		
    		String labelContent = cellValue.substring(2, cellValue.length()-1);
    		labelBean.setRow(rowNum);
    		labelBean.setCol(colNum);
    		labelBean.setLableContent(labelContent);
    		
    		return labelBean;
    	}
    	
    	return null;
    }
    
    /**
     *  解析标签数组
      * @param params
      * @param labelBeanList
      * @author: qiancong.Xian
      * @date: 2015年4月16日 上午11:03:45
     */
    private static List<LabelBean>  parseLabelBean(Map<String,Object> params,List<LabelBean> labelBeanList)
    {
		// 返回解析好的标签集合
		List<LabelBean> resultList = new ArrayList<LabelBean>();
		// 如果没有标签要解析，返回
		if (labelBeanList.size() == 0) {
			return resultList;
		}

		// 当前是否是进行for循环
		boolean startFor = false;
		int totalAddRow = 0; // 累计增加的行数（for循环会增加行）
		List<Object> forList = null;// 正在循环for标签对于的Map中的list
		int cForListSize = 0; // 正在循环for标签对于的Map中的list的记录数
		int cForListRowNum = 0; // 当前循环的for标签所在的行号
		int maxColNum = 0; // 最大的列号
		for (LabelBean labelBean : labelBeanList) {
			try {
				
				// 是一个for循环标签
				if (labelBean.getLableContent().startsWith("for:")) {
					startFor = true;
					cForListRowNum = labelBean.getRow();
					// 集合的名字
					String listName = labelBean.getLableContent().substring(4, labelBean.getLableContent().length());
					forList = (List<Object>) params.get(listName);
					if (forList == null || forList.size() == 0) {
						LabelBean removeRow = new LabelBean();
						removeRow.setRow(cForListRowNum);
						removeRow.setRemoveRow(true);
						resultList.add(removeRow);
					} else {

						LabelBean newLabelBean = new LabelBean();
						newLabelBean.setCol(labelBean.getCol()); // 列号是一样的
						newLabelBean.setRow(labelBean.getRow()+totalAddRow); // 当前行号
						newLabelBean.setStyle(labelBean.getStyle());
						newLabelBean.setValue("");
						resultList.add(newLabelBean);

						cForListSize = forList.size();
						// 添加size-1行,先把for循环的各行先创建出来
						for (int i = 1; i < cForListSize; i++) {
							newLabelBean = new LabelBean();
							newLabelBean.setRow(labelBean.getRow() + totalAddRow + i);
							newLabelBean.setCreateRow(true);
							resultList.add(newLabelBean);
						}
					}
				}
				// 不是for循环标签
				else {
					// 列合计标签
					if(labelBean.getLableContent().startsWith("col:total"))
					{
						// 创建一行
						LabelBean newLabelBean = new LabelBean();
						newLabelBean.setRow(labelBean.getRow() + cForListSize-1);
						newLabelBean.setCreateRow(true);
						resultList.add(newLabelBean);
						
					    newLabelBean = new LabelBean();
						newLabelBean.setCol(labelBean.getCol()); // 列号是一样的
						newLabelBean.setRow(labelBean.getRow() + cForListSize-1); // 当前行号
						newLabelBean.setStyle(labelBean.getStyle());
						newLabelBean.setCreateCell(true);
						newLabelBean.setValue("合计");
						resultList.add(newLabelBean);
						
						int start= cForListRowNum+1;
					    int end = start+cForListSize-1;
						for(int j=labelBean.getCol()+1;j<=maxColNum;j++)
						{
							// 获取列对应的英文
							String columnName = getColumnName(j+1);
							
							newLabelBean = new LabelBean();
							newLabelBean.setCol(j); // 列号是一样的
							newLabelBean.setRow(labelBean.getRow() + cForListSize-1); // 当前行号
							newLabelBean.setStyle(labelBean.getStyle());
							newLabelBean.setCreateCell(true);
							newLabelBean.setCellFormula(true); // 使用sum公式
							
							String cellFormula = "SUM("+columnName+start+":"+columnName+end+")";
							newLabelBean.setValue(cellFormula);
							
							resultList.add(newLabelBean);
							
						}
					}	
					
					// 重新检查是否在for循环中
					if (startFor && labelBean.getRow() != cForListRowNum) {
						totalAddRow += cForListSize - 1;// 累加的行就是for循环数组-1
						maxColNum = 0;
						startFor = false;
					}
					
					// 当前正在进行for循环
					if (startFor) {
						
						// 保存循环中最大的列号
						maxColNum = maxColNum>labelBean.getCol()?maxColNum:labelBean.getCol();
						
						// 遍历集合
						for (int i = 0; i < forList.size(); i++) {
							LabelBean newLabelBean = new LabelBean();
							newLabelBean.setCol(labelBean.getCol()); // 列号是一样的
							newLabelBean.setRow(labelBean.getRow() + totalAddRow + i); // 当前行号
							newLabelBean.setStyle(labelBean.getStyle());
							newLabelBean.setCreateCell(true);

							// 序号列
							if (labelBean.getLableContent().equals(":sn")) {
								newLabelBean.setValue(String.valueOf(i + 1));
							}
							// 行统计（最后一个合计，是一个公式）
							else if(labelBean.getLableContent().startsWith("="))
							{
								String cellFormula = labelBean.getLableContent(); // 计算公式
								String forMulaRow = String.valueOf((cForListRowNum+1)); // 计算公式中的行号（公式中的行号是从1开始，java代码中从0开始的）
								cellFormula = cellFormula.replaceAll(forMulaRow,String.valueOf((newLabelBean.getRow()+1)));
								newLabelBean.setValue(cellFormula.substring(1, cellFormula.length()));
								newLabelBean.setCellFormula(true);
							}
							
							// 非序列号
							else {
								// 对于的实体对象
								Object obj = forList.get(i);
								Object cellValue = null;
								// 如果是map对象
								if (obj instanceof java.util.Map) 
								{
									cellValue = ((Map)obj).get(labelBean.getLableContent());
									
								}	
								else
								{
									cellValue = getFieldValueByName(labelBean.getLableContent(),obj);
								}
								
								newLabelBean.setValue(null == cellValue?"":cellValue.toString());
							}

							resultList.add(newLabelBean);
						}
					}
					// 非循环中
					else {
						LabelBean newLabelBean = new LabelBean();
						newLabelBean.setCol(labelBean.getCol()); // 列号是一样的
						newLabelBean.setRow(labelBean.getRow()); // 当前行号

						String paramKey = labelBean.getLableContent().split("\\.")[0];
						String fileName = labelBean.getLableContent().split("\\.")[1];
						// 对于的实体对象
						Object obj = params.get(paramKey);
						Object cellValue = null;
						// 如果是map对象
						if (obj instanceof java.util.Map) 
						{
							cellValue = ((Map)obj).get(fileName);
						}	
						else
						{
							cellValue = getFieldValueByName(fileName,obj);
						}
						
						newLabelBean.setValue(null == cellValue?"":cellValue.toString());
						
						resultList.add(newLabelBean);
					}
				}
			} catch (Exception e) {

			}
		}	
		
		return resultList;
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
    
    //通过对象属性获取值
    public static String getFieldValueByName(String fieldName, Object o) {  
        try {    
            String firstLetter = fieldName.substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + fieldName.substring(1);    
            Method method = o.getClass().getMethod(getter, new Class[] {});    
            Object value = method.invoke(o, new Object[] {});  
            if (value instanceof Double) {
            	if(ExcelExport.getNoToFormat(fieldName)){
            		DecimalFormat t = new DecimalFormat("#,##0.00");
    				return String.valueOf(t.format(value));
            	}
			}
            return value.toString();    
        } catch (Exception e) {    
        }   
        
        return "";
    } 
    
    /**
     * 
      * @Description: 过滤不格式化的数据
      * @param fieldName
      * @return
      * @author: xuweihao
      * @date: 2015年6月9日 下午5:01:26
     */
    public static boolean getNoToFormat(String fieldName) {
    	if(fieldName.equals("monthLoanInterest") || fieldName.equals("monthLoanMgr")){
    		return false;
    	}
    	return true;
    }
    
    /**
     *  通过列号获取excel对应的字母 1:A 2:B ....
      * @param columnNum
      * @return
      * @author: qiancong.Xian
      * @date: 2015年4月16日 下午8:32:59
     */
    public static String getColumnName(int columnNum) {
        int first;
        int last;
        String result = "";
        if (columnNum > 256)
                columnNum = 256;
        first = columnNum / 27;
        last = columnNum - (first * 26);
        if (first > 0)
                result = String.valueOf((char) (first + 64));
        if (last > 0)
                result = result + String.valueOf((char) (last + 64));
        return result;
  }
	    
    
    
    //创建xls文件
    public static String createTestFile(String localPath,String filename,Workbook wb) throws Exception {
		try {
			File f = createFile(localPath,filename);
			FileOutputStream outStream = new FileOutputStream(f);
			wb.write(outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
		}
		return localPath+filename;
	}
    
    //创建本地的xls文件
    public static String createLocalFile(String filename,Workbook wb,HttpServletRequest request) throws Exception {
    	String localPath = CommonUtil.getRootPath()+getDateToString();
		try {
			File f = createFile(localPath,filename);
			FileOutputStream outStream = new FileOutputStream(f);
			wb.write(outStream);
			outStream.flush();
			outStream.close();
		} catch (Exception e) {
		}
		return localPath;
	}
    
    /**
     * 创建文件
      * @param filePath
      * @author: qiancong.Xian
      * @date: 2015年4月17日 上午10:17:59
     */
    public static File createFile(String localPath,String filename)
    {
    	createFolder(localPath); // 创建父类文件夹
    	File f = new File(localPath+Constants.SEPARATOR+filename);
		if (!f.exists()) {
			
			try {
				f.createNewFile();
			} catch (Exception e) {
			}
		}
		return f;
    }
    
    private static void  createFolder(String filePath)
    {
    	File f = new File(filePath);
		if (!f.exists()) {
			//  递归创建父文件
			if(!f.getParentFile().exists())
			{
				createFolder(f.getParent());
			}
		}
		
		  f.mkdirs();
    }
    
    
    
    //删除文件
    public static void deleteFile(String path,String outPutFileName){
    	try {
    		// 删除对应的文件
    		File f = new File(path + Constants.SEPARATOR+ outPutFileName);
    		if (f.exists()) {
    			f.delete();
    		}
    		// 删除对应的文件夹
    		f = new File(path);
    		if (f.exists()) {
    			f.delete();
    		}
    	}catch (Exception e) {
    	}
    }
    
    //添加时间标记
    public static String getDateToString() {
		Long time = (new Date()).getTime();
		return String.valueOf(time);
	}
    
    //发送文件
    public static void sentFile(String filename,String localFilePath,HttpServletResponse response,HttpServletRequest request) throws IOException{
    	InputStream inputStream = null;
    	OutputStream os = null;
    	try {
			/*response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.replaceAll(" ", "").getBytes("utf-8"), "iso8859-1"));*/
			response.addHeader("Content-Disposition", "attachment;filename=" +  java.net.URLEncoder.encode(filename.replaceAll(" ", ""), "UTF-8"));
			inputStream = new FileInputStream(new File(localFilePath+Constants.SEPARATOR+filename));
			os = response.getOutputStream();
			byte[] b = new byte[2048];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(os!=null){
				os.close();
			}
			if(inputStream!=null){
				inputStream.close();
			}
		}
    }
    
    
    /**
     *  待替换的标签
      * @ClassName: LabelBean
      * @Description: TODO
      * @author: qiancong.Xian
      * @date: 2015年4月16日 上午10:11:21
     */
    static  class LabelBean{
    	private int row;  
    	private int col;
    	private String lableContent;  //  标签的内容
    	private int type;  // 标签类型
    	private String value; // 替换后的值
    	private CellStyle style; // 单元格使用的样式
    	private boolean createRow; // 是否要创建新的行
    	private boolean createCell; // 是否要创建新的单元格
    	private boolean removeRow; // 当前行是否需要删除（主要是飞来for循环中，没有值的情况）
    	private boolean cellFormula; // 是否是一个计算公式
		public int getRow() {
			return row;
		}
		public void setRow(int row) {
			this.row = row;
		}
		public int getCol() {
			return col;
		}
		public void setCol(int col) {
			this.col = col;
		}
		public String getLableContent() {
			return lableContent;
		}
		public void setLableContent(String lableContent) {
			this.lableContent = lableContent;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public CellStyle getStyle() {
			return style;
		}
		public void setStyle(CellStyle style) {
			this.style = style;
		}
		public boolean isCreateRow() {
			return createRow;
		}
		public void setCreateRow(boolean createRow) {
			this.createRow = createRow;
		}
		public boolean isCreateCell() {
			return createCell;
		}
		public void setCreateCell(boolean createCell) {
			this.createCell = createCell;
		}
		public boolean isRemoveRow() {
			return removeRow;
		}
		public void setRemoveRow(boolean removeRow) {
			this.removeRow = removeRow;
		}
		public boolean isCellFormula() {
			return cellFormula;
		}
		public void setCellFormula(boolean cellFormula) {
			this.cellFormula = cellFormula;
		}
    }
}
