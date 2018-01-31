/**
 * @Title: DOCWriter.java
 * @Description: 根据模板匹配符生辰文档
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年1月10日 下午2:20:45
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.contract;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.contract.ContractDynamicTableParameter;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBase;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssDtl;
import com.xlkfinance.bms.rpc.system.SysConfigService;
import com.xlkfinance.bms.web.controller.BaseController;

@Component("contractGenerator")
public class ContractGenerator {
	
	private Logger logger = LoggerFactory.getLogger(ContractGenerator.class);
	
	private List<TempLateParmDto> templateParamList;

	/**
	 * @Description: 生成合同
	 * @param templatePath
	 * @param contractFilePath
	 * @param parameterMap
	 * @return
	 * @throws Exception
	 * @author: Simon.Hoo
	 * @date: 2015年4月15日 上午2:22:34
	 */
	public boolean generate(BaseController controller,List<TempLateParmDto> templateParamList, String templatePath, String contractFilePath, Map<String, String> parameterMap,List<ContractDynamicTableParameter> contractTabs,String tableLabel) throws Exception {
		try {
			this.templateParamList = templateParamList;

			String[] sp = templatePath.split("\\.");
			String[] dp = contractFilePath.split("\\.");
			File f = new File(contractFilePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			if ((sp.length > 0) && (dp.length > 0)) {// 判断文件有无扩展名
				// 比较文件扩展名
				if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
					XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(templatePath));

					List<XWPFParagraph> paragraphList = document.getParagraphs();
					processParagraphs(paragraphList, parameterMap);
					
					//表格处理
					List<XWPFTable> tableList = document.getTables();
					// 处理固定的表格
					processFixedTables(tableList, parameterMap);
					
					if(tableList!=null && tableList.size()>0 && contractTabs!=null && contractTabs.size()>0){
						if(null == tableLabel)
						{
							processTables(tableList, contractTabs);
						}
						else
						{
							processTables(controller,tableList, contractTabs,tableLabel);
						}	
					}

					FileOutputStream outStream = null;
					outStream = new FileOutputStream(contractFilePath);
					document.write(outStream);
					outStream.close();
					return true;

				} else {
					// doc只能生成doc，如果生成docx会出错
					if ((sp[sp.length - 1].equalsIgnoreCase("doc")) && (dp[dp.length - 1].equalsIgnoreCase("doc"))) {
						HWPFDocument document = null;
						document = new HWPFDocument(new FileInputStream(templatePath));
						Range range = document.getRange();
						for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
							range.replaceText(entry.getKey(), entry.getValue());
						}
						FileOutputStream outStream = null;
						outStream = new FileOutputStream(contractFilePath);
						document.write(outStream);
						outStream.close();
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	
	/**
	 * 
	  * @Description: 文件 生成合同编号
	  * @param contractFilePath
	  * @param parameterMap
	  * @return
	  * @throws Exception
	  * @author: zhanghg
	  * @date: 2015年5月22日 下午3:24:06
	 */
	public boolean generateContractNo(String templatePath,String contractFilePath,Map<String,String> parameterMap) throws Exception {
		try {
			String[] sp = templatePath.split("\\.");
			String[] dp = contractFilePath.split("\\.");
			File f = new File(contractFilePath);
			if (!f.exists()) {
				f.createNewFile();
			}
			
			if ((sp.length > 0) && (dp.length > 0)) {// 判断文件有无扩展名
				// 比较文件扩展名
				if (sp[sp.length - 1].equalsIgnoreCase("docx")) {
					XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(templatePath));

					List<XWPFParagraph> paragraphList = document.getParagraphs();
					processParagraphs(paragraphList, parameterMap);
					
					FileOutputStream outStream = null;
					outStream = new FileOutputStream(contractFilePath);
					document.write(outStream);
					outStream.close();
					return true;

				} else {
					// doc只能生成doc，如果生成docx会出错
					if (dp[dp.length - 1].equalsIgnoreCase("doc")) {
						HWPFDocument document = null;
						document = new HWPFDocument(new FileInputStream(templatePath));
						Range range = document.getRange();
						for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
							range.replaceText(entry.getKey(), entry.getValue());
						}
						FileOutputStream outStream = null;
						outStream = new FileOutputStream(contractFilePath);
						document.write(outStream);
						outStream.close();
						return true;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return false;
	}
	
	
	public String getFilePath(String relativelyPath,String realFileName,String realPath){

		String folder = new StringBuffer().append(realPath).append("/").append(relativelyPath).toString();
		File folderFile = new File(folder);
		if (!folderFile.exists()) {
			folderFile.mkdirs();
		}

		StringBuffer contractFilePathSb = new StringBuffer();
		contractFilePathSb.append(folder);// 目录
		contractFilePathSb.append("/");
		contractFilePathSb.append(realFileName);// 文件名
		return contractFilePathSb.toString();
	}

	/**
	 * @Description: 处理段落文本
	 * @param paragraphList
	 * @param parameterMap
	 * @author: Simon.Hoo
	 * @date: 2015年4月15日 上午2:25:35
	 */
	private void processParagraphs(List<XWPFParagraph> paragraphList, Map<String, String> parameterMap) {
		if (paragraphList != null && paragraphList.size() > 0) {
			for (XWPFParagraph paragraph : paragraphList) {
				List<XWPFRun> runs = paragraph.getRuns();
				for (XWPFRun run : runs) {
					String text = run.getText(0);
					if (text != null) {
						boolean isSetText = false;
						for (Entry<String, String> entry : parameterMap.entrySet()) {
							String key = entry.getKey();
							if (text.indexOf(key) != -1) {
								isSetText = true;
								Object value = entry.getValue();
								if (value instanceof String) {// 文本替换
									text = text.replace(key, formatStringVal(key, value.toString()));
								} else if (value instanceof Map) {// 图片替换
									// 图片暂不处理
								}
							}
						}
						if (isSetText) {
							run.setText(text, 0);
						}
					}
				}
			}
		}
	}
	
	/**
	 * 处理固定的表格内容（银行账号，支票）
	  * @param tableList
	  * @param parameterMap
	  * @author: qiancong.Xian
	  * @date: 2015年7月10日 下午7:28:47
	 */
	private void processFixedTables(List<XWPFTable> tableList,Map<String, String> parameterMap)
	{
		// 没有要处理的表格
		if(null == tableList)
		{
			return;
		}
		
		for(XWPFTable xWPFTable:tableList)
		{
			// 不是要处理的固定格式的表格
			if(!isWaitProcessFixedTable(xWPFTable))
			{
				continue;
			}	
			
			processFiexTable(xWPFTable, parameterMap);
		}	
	}
	
	/**
	 * 替换固定格式的表格
	  * @Description: TODO
	  * @param xWPFTable
	  * @param parameterMap
	  * @author: qiancong.Xian
	  * @date: 2015年7月10日 下午7:44:08
	 */
    private void processFiexTable(XWPFTable xWPFTable,Map<String, String> parameterMap)
    {
    	// 单元格的值
    	String cellValue = null;
    	// 要替换的值
    	String repayValue = null;
    	
    	// 列数
    	int colNum = 0;
    	if(null !=xWPFTable.getRow(0) )
    	{
    		while(null != xWPFTable.getRow(0).getCell(colNum))
    		{
    			colNum++;
    		}	
    	}	
    	
    	// 表格的内容
    	String values[][] = new String[xWPFTable.getRows().size()][colNum];
    	// 变量当前表格的所有行
    	int rowIndex = 0; // 行号
		for(XWPFTableRow row: xWPFTable.getRows())
		{
			for(int i=0;i<colNum;i++)
			{	
				cellValue = row.getCell(i).getText();
				repayValue = parameterMap.get(cellValue);
				// 有替换的值
				if(null != repayValue)
				{
					values[rowIndex][i] = repayValue;
				}	
				else
				{
					values[rowIndex][i] = cellValue;
				}
			}
			
			rowIndex++;
		}
		
		// 移除所有的行（除了表头）
		for(int i=xWPFTable.getRows().size()-1;i>0;i--)
		{
			xWPFTable.removeRow(i);
		}	
		
		int rowIndxt = 1;
		// 表头不算
		for(int i=1;i<values.length;i++)
		{
			String rowValue[] = values[i];
			// 不需要删除的列
			if(!needRemove(rowValue))
			{
				// 新建一行
				xWPFTable.insertNewTableRow(rowIndxt);
				// 新建对应的单元格
				for(int j=0;j<rowValue.length;j++)
				{
					xWPFTable.getRow(rowIndxt).createCell().setText(rowValue[j]);
				}	
				
				rowIndxt++;
			}	
		}	
    }
    
    private boolean needRemove(String [] rowValue)
    {
    	if(null == rowValue || rowValue.length==0)
    	{
    		return true;
    	}
    	
    	for(int i=0;i<rowValue.length;i++)
    	{
    		// 只要有一个不是标签，不删除
    		if(null != rowValue[i] && rowValue[i].trim().length()>0 && !(rowValue[i].startsWith("@") && rowValue[i].endsWith("@")))
    		{
    			return false;
    		}	
    	}	
    
    	return true;
    }
	

	/**
	  * @Description: 是否待处理的固定格式的表格
	  * @param xWPFTable
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月10日 下午7:31:44
	 */
	private boolean isWaitProcessFixedTable(XWPFTable xWPFTable)
	{
		if(null == xWPFTable)
		{
			return false;
		}	
		
		String cellValue = null;
		// 变量当前表格的所有行
		for(XWPFTableRow row: xWPFTable.getRows())
		{
			int colNum= 0;
			while(null != row.getCell(colNum))
			{
				cellValue = row.getCell(colNum).getText();
				if(null != cellValue && (cellValue.startsWith("@") && cellValue.endsWith("@")))
				{
					return true;
				}	
				
				colNum++;
			}
		}

		return false;
	}
	
	/**
	 * @Description: 处理表格内容。
	 * @param tableList
	 * @param parameterMap
	 * @author: Simon.Hoo
	 * @date: 2015年4月15日 上午2:27:39
	 */
	private void processTables(List<XWPFTable> tableList, List<ContractDynamicTableParameter> parameterMap) {
		
		XWPFTable xWPFTable=tableList.get(0);
		xWPFTable.removeRow(1);
		for(int i=1;i<=parameterMap.size();i++){
			ContractDynamicTableParameter tab=parameterMap.get(i-1);
			
			//添加一行
			xWPFTable.insertNewTableRow(i);
			xWPFTable.getRow(i).createCell().setText(tab.getGoodsName());
			xWPFTable.getRow(i).createCell().setText(tab.getGoodsNumber());
			xWPFTable.getRow(i).createCell().setText(tab.getGoodsCount());
			xWPFTable.getRow(i).createCell().setText(tab.getGoodsUnit());
			xWPFTable.getRow(i).createCell().setText(tab.getGoodsAddress());
		}
	}
	
	/**
	 * 
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月10日 下午7:19:03
	 */
	private XWPFTable getXWPFTable(List<XWPFTable> tableList,String tableLabel)
	{
		if(null != tableList)
		{
			String cellValue = null;
			int colNum= 0;
			XWPFTableRow fristRow = null;
			for(XWPFTable xWPFTable:tableList)
			{
				// 获取表头
				fristRow = xWPFTable.getRow(0);
				// 表头总共多少列
				// 从左到右找内容
		        while(null != fristRow.getCell(colNum))
		        {
		        	cellValue = fristRow.getCell(colNum).getText();
		        	if(null != cellValue)
		        	{
		        		// 单元格中包含标签
		        		if(cellValue.contains(tableLabel))
		        		{
		        			return xWPFTable;
		        		}	
		        	}	
		        	colNum++;
		        }	
			}	
		}
		
		return null;
	}
	
	/**
	 * @Description: 处理表格内容(质押合同里面的列表)【获取质押数据】
	 * @param tableList
	 * @param parameterMap
	 * @author: Simon.Hoo
	 * @date: 2015年4月15日 上午2:27:39
	 */
	private void processTables(BaseController controller,List<XWPFTable> tableList, List<ContractDynamicTableParameter> parameterMap,String tableLabel) {
		
		XWPFTable xWPFTable=  getXWPFTable(tableList, tableLabel);
		// 如果没有找到标签对于的表格
		if(null == xWPFTable)
		{
			return;
		}
				
		List<String> titleList = this.getContractTableTitle(controller,tableLabel);
		// 表头总共多少列
		int colNum= 0;
        while(null != xWPFTable.getRow(0).getCell(colNum))
        {
        	colNum++;
        }	
		
        
		xWPFTable.insertNewTableRow(1);
		// 添加表头
		for(int i=0;i<titleList.size();i++)
		{
			// 如果定义的表头大于模板中的列数
			if(i>=colNum)
			{
				break;
			}	
			
			xWPFTable.getRow(1).createCell().setText(titleList.get(i));
		}
		
		// 如果定义的表头小于表格中的表头，补齐单元格
		if(colNum>titleList.size())
		{
			for(int k=0;k<(colNum-titleList.size());k++)
			{
				xWPFTable.getRow(1).createCell().setText("");
			}	
		}	
		
		for(int i=0;i<parameterMap.size();i++){
			ContractDynamicTableParameter obj=parameterMap.get(i);
			//添加一行
			xWPFTable.insertNewTableRow(i+2);
			for(int j=0;j<titleList.size();j++)
			{
				
				// 如果定义的表头大于模板中的列数
				if(j>=colNum)
				{
					break;
				}	
				
				xWPFTable.getRow(i+2).createCell().setText(this.getColValue(j, obj));
			}	
			// 如果定义的表头小于表格中的表头，补齐单元格
			if(colNum>titleList.size())
			{
				for(int k=0;k<(colNum-titleList.size());k++)
				{
					xWPFTable.getRow(i+2).createCell().setText("");
				}	
			}	
		}
		
		xWPFTable.removeRow(0);
	}
	
	/**
	 * 获取对应的值
	  * @param i  列位置
	  * @param obj
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年6月3日 下午4:20:07
	 */
	private String getColValue(int i,ContractDynamicTableParameter obj)
	{
        Class clazz = obj.getClass(); 
        Method m1;
		try {
			m1 = clazz.getDeclaredMethod("getCol"+i);
			return (String) m1.invoke(obj); 
		} catch (Exception e) {
		}
		return "";
	}
	

	/**
	 * @Description: 格式化参数
	 * @param val
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月13日 下午9:11:55
	 */
	private String formatStringVal(String key, String val) {
		int size = 20;
		String temp = "";
		if (key != null && val != null) {
			temp =val;// convertVal(val, getCurrentKeyValConvertFun(key));
		}

		if (temp.length() < size) {
			int t = (temp == null ? size : size - temp.length())/2;
			StringBuffer sb = new StringBuffer(t);
			for (int i = 0; i < t; i++) {
				sb.append(" ");
			}
			return  sb.toString()+ temp + sb.toString();
		} else {
			return temp;
		}
	}

	/**
	 * @Description: 输转换
	 * @param val
	 * @param convertFlg
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月15日 上午3:24:57
	 */
	public String convertVal(String val, int convertFlg) {
		switch (convertFlg) {
			case 1:// 日期(分隔符)
				return val;
			case 2:// 日期(中文)
				return convertDate(val);
			case 3:// 金额(大写)
				return MoneyUtil.toChinese(val);
			case 4:// 数值(大写)
				return new NumUtil(val).getCnString();
			case 5:// 千分位
				NumberFormat numberFormat = NumberFormat.getNumberInstance();
				String retVal=numberFormat.format(Double.parseDouble(val));
				int i=retVal.lastIndexOf(".");
				if(i>0){
					if(i==retVal.length()-2){
						return retVal+"0";
					}else{
						return retVal;
					}
					
				}else{
					return retVal+".00";
				}
				 
			default:// 原样反回
				return val;
		}
	}
	
	private String convertDate(String str){
		
		if(!"".equals(str)){
			Date date=DateUtil.format(str, "yyyy-MM-dd");
			
			str=DateUtil.format(date, "yyyy年MM月dd日");
			
			return str;
		}
		return "";
	}

	/**
	 * @Description: 获取当前参数的格式化输出标识
	 * @param key
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月15日 上午3:22:31
	 */
	private int getCurrentKeyValConvertFun(String key) {
		for (TempLateParmDto parm : templateParamList) {
			if (parm.getMatchFlag().equalsIgnoreCase(key)) {
				return parm.getValConvertFlag();
			}
		}
		return 0;
	}

	/**
	 * @Description: 获取String型的时间
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2015年1月7日 下午2:13:25
	 */
	public static String getDateToString(String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(new Date());
	}

	// 合同生成中的表格清单表头
	static Map<String,String> titleMap = new HashMap<String,String>();
	// 合同预览中的表格的列的名称前缀
	static String TITLECOLNAME = "col"; 
	
	// 合同生成中的表格清单输出内容
	static Map<String,String> mortgageListContent = new HashMap<String,String>();
		
	static
	{
		// 表头定义 ---------------------------------------------------------------------------
		// 抵押物清单
		titleMap.put("@mortgageList@", "抵押物名称,权利证书及编号,数量,单位,抵押物坐落");
		// 质押物清单
		titleMap.put("@pledgeList@", "质物名称,规格,数量,单位,总值,有效期");
		
		
		// 内容输出列定义,（前3列是固定的）后4列   (单位,总值,有效期) ---------------------------------------------------------------------------
		// 抵质押机械设备详细信息              型号，模型估价（万）,buyYear
		mortgageListContent.put("MORTGAGE_EQUIPMENT", "model,modelEvaluation,buyYear");
		// 抵质押房产详细信息                   *建筑面积.m2,模型估价（万），剩余年限
		mortgageListContent.put("MORTGAGE_EQUIPMENT", "publicRemarks");
		// 抵质押其他详细信息                  物件信息1,物件信息2,物件信息3,物件信息4
		mortgageListContent.put("MORTGAGE_OTHER", "pieceInformation1,pieceInformation2,pieceInformation3,pieceInformation4");
		// 抵质押其他详细信息               型号，模型估价（万），颜色及新旧描述
		mortgageListContent.put("MORTGAGE_SHIPS", "model,modelEvaluation,publicRemarks");
		// 抵质押存货详细信息              类别,模型估价（万）,颜色及新旧描述
		mortgageListContent.put("MORTGAGE_STOCK", "category,modelEvaluation,publicRemarks");
		// 抵质押车辆详细信息              型号,模型估价（万）,颜色及新旧描述
		mortgageListContent.put("MORTGAGE_VEHICLE", "model,modelEvaluation,publicRemarks");
	}
	
	
	/**
	 * 获取系统参数	
	  * @param controller
	  * @param name
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午4:52:58
	 */
	private String getSysConfigValue(BaseController controller,String name,Map<String,String> defualtMap)
	{
		
		BaseClientFactory clientFactory = controller.getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			return client.getSysConfigValueByName(name);
		} catch (Exception e) {
			logger.warn("获取系统参数名称["+name+"]的值为空，获取系统默认的数据代替");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		
		return defualtMap.get(name);
	}
	
	/**
	 *  获取合同中的表头列表中的标签
	  * @param tempLateParmDtoList
	  * @return  如：@mortgageList@
	  * @author: qiancong.Xian
	  * @date: 2015年6月3日 上午9:49:35
	 */
	public String getContractTableLabel(List<TempLateParmDto> tempLateParmDtoList)
	{
		if(null != tempLateParmDtoList)
		{
			for(TempLateParmDto tempLateParmDto: tempLateParmDtoList)
			{
				// 如果是表格
				if(tempLateParmDto.getIsTable() == 1)
				{
					// 根据标签获取对应的表格的标签
					return  tempLateParmDto.getMatchFlag();
				}	
			}	
		}
		
		return  null;
	}
	
	/**
	  * 获取合同中的表头列表表头
	  * @param tempLateParmDtoList
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年6月1日 下午8:11:39
	 */
	public List<String> getContractTableTitle(BaseController controller,String tableLabel)
	{
		List<String> titleList = new ArrayList<String>();
			// 根据标签获取对应的表头的值
			String value = getSysConfigValue(controller,tableLabel,titleMap);
			
			if(null != value)
			{
				for(String v:value.split(","))
				{
					titleList.add(v);
				}
				
				return titleList;
			}
		
		return titleList;
	}
	
	/**
	 * 获取合同中的表头列表行数据
	  * @param tableLabel 表格的标签
	  * @param list  担保的记录数据
	  * @param assDtlMap 细节详情
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年6月3日 上午9:51:13
	 */
	public List<Map<String,String>> getContractTableRowsDate(BaseController controller,String tableLabel,List<ProjectAssBase> list, Map<Integer,List<ProjectAssDtl>> assDtlMap)
	{
		List<Map<String,String>> resultMap = new ArrayList<Map<String,String>>();
		for(ProjectAssBase assbase:list)
		{
			Map<String,String> map = new HashMap<String,String>();
			// 构建详情
			buildAssDtlInfo(controller,tableLabel,assbase, map, assDtlMap.get(assbase.getPid()));
			resultMap.add(map);
		}	
		
		return resultMap;
	}
	
	private void buildAssDtlInfo(BaseController controller,String tableLabel,ProjectAssBase assbase,Map<String,String> map,List<ProjectAssDtl> dtlList)
	{
		
		// 找出对应的抵质押物配置类型 
		// 先查询指定表头的内容参数（例如：查房地产抵质押物的展现内容顺序  系统参数配置表中的定义：表头为@MORTGAGELIST@，房地产抵押物为MORTGAGE_HOUSE_PROPERTY，先查询参数：@MORTGAGELIST@MORTGAGE_HOUSE_PROPERTY，如果没有，再查询MORTGAGE_HOUSE_PROPERTY）
		String keystr = getSysConfigValue(controller,tableLabel+assbase.getMortgageGuaranteeTypeText(),mortgageListContent);
		//  如果没有知道表头的内容参数，使用公共的
		if(null == keystr)
		{
			keystr = getSysConfigValue(controller,assbase.getMortgageGuaranteeTypeText(),mortgageListContent);
		}
		
		if(null != keystr)
		{
			String keys [] = keystr.split(",");
			setColValue(keys, 0, assbase, map, dtlList);
			setColValue(keys, 1, assbase, map, dtlList);
			setColValue(keys, 2, assbase, map, dtlList);
			setColValue(keys, 3, assbase, map, dtlList);
			setColValue(keys, 4, assbase, map, dtlList);
			setColValue(keys, 5, assbase, map, dtlList);
			setColValue(keys, 6, assbase, map, dtlList);
			
		}
		
		// 页面是根据col0开始的(前3列是固定的)
		if(null == map.get(TITLECOLNAME+"0"))
		{
			map.put(TITLECOLNAME+"0", assbase.getItemName());
		}	
		if(null == map.get(TITLECOLNAME+"1"))
		{
			map.put(TITLECOLNAME+"1", assbase.getWarrantsNumber());
		}
		if(null == map.get(TITLECOLNAME+"2"))
		{
			map.put(TITLECOLNAME+"2", "1"); // 数量是1
		}
		// 默认值
		if(null == map.get(TITLECOLNAME+"3"))
		{
			map.put(TITLECOLNAME+"3", "-"); // 数量是1
		}	
		if(null == map.get(TITLECOLNAME+"4"))
		{
			map.put(TITLECOLNAME+"4", assbase.getAddressProvince()+assbase.getAddressCity()+assbase.getAddressArea()+assbase.getAddressDetail()); // // 抵押物地址
		}
	}
	
	private void setColValue(String keys [], int index,ProjectAssBase assbase, Map<String,String> map,List<ProjectAssDtl> dtlList)
	{
		// 如果不在设定的范围内
		if(keys.length < (index+1))
		{
			return;
		}
		
	   // 获取基本的信息
        try {    
            String firstLetter = keys[index].substring(0, 1).toUpperCase();    
            String getter = "get" + firstLetter + keys[index].substring(1);    
            Method method = assbase.getClass().getMethod(getter, new Class[] {});    
            Object value = method.invoke(assbase, new Object[] {});    
            map.put(TITLECOLNAME+index, String.valueOf(value));
        } catch (Exception e) {    
        } 
        
        // 如果是地址
        if("address".equals(keys[index]))
        {
			map.put(TITLECOLNAME+index, assbase.getAddressProvince()+assbase.getAddressCity()+assbase.getAddressArea()+assbase.getAddressDetail()); // 抵押物地址
        }	
        
        // 如果没有对应的值
        if(null == map.get(TITLECOLNAME+index))
        {
        	map.put(TITLECOLNAME+index, getDtlValue(keys[index], dtlList));
        }	
	}
	
	/**
	 * 获取对应的值
	  * @param key
	  * @param dtlList
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年6月5日 上午10:58:08
	 */
	private String getDtlValue(String key,List<ProjectAssDtl> dtlList)
	{
		for(ProjectAssDtl dtl : dtlList)
		{
			if(key.equals(dtl.getLookupVal()))
			{
				return dtl.getInfoVal();
			}	
		}
		
		return "";
	}
}
