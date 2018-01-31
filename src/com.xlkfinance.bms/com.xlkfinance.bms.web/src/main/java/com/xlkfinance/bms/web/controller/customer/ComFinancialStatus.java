package com.xlkfinance.bms.web.controller.customer;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheetCalculateDTO;
import com.xlkfinance.bms.rpc.customer.CusComCalculateService;
import com.xlkfinance.bms.rpc.customer.CusComService;
import com.xlkfinance.bms.rpc.customer.ExcelCusComBalanceSheet;
import com.xlkfinance.bms.rpc.customer.ExcelCusComIncomeReport;
import com.xlkfinance.bms.rpc.customer.FinancialPositionDTO;
import com.xlkfinance.bms.rpc.system.TemplateFile;
import com.xlkfinance.bms.rpc.system.TemplateFileService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.ExcelExport;

@Controller
@RequestMapping("/comFinancialStatus")
public class ComFinancialStatus extends BaseController{
	private static final String serviceModuel = "customer";
	private static final String cuscom = "customer/com/";
	
	

	/**
	 * 
	 * @Description: 资产负债表导出
	 * @return 资产负债表导出
	 * @author: min
	 * @date: 
	 */
	@RequestMapping(value = "excelExcelCusComBalanceSheet")
	@ResponseBody
	public void ExcelExcelCusComBalanceSheet(String pids,HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			
			List<ExcelCusComBalanceSheet> list = client.getExcelCusComBalanceSheet(pids);	
			for (int i = 0; i < list.size(); i++) {
				map.put("bean"+i, list.get(i));
			}
			
			TemplateFile template = cl.getTemplateFile("BALANCE");

			if (!template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "资产负债表.xls"+"."+template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".")+1), response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 
	 * @Description: 利润报表导出
	 * @return 利润报表导出
	 * @author: min
	 * @date: 
	 */
	@RequestMapping(value = "excelCusComIncomeReportByReportId")
	@ResponseBody
	public void ExcelCusComIncomeReportByReportId(String reportId,HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
		BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
			CusComService.Client client = (CusComService.Client) clientFactory.getClient();
			
			List<ExcelCusComIncomeReport> list = client.excelCusComIncomeReportByReportId(reportId);	
			for (int i = 0; i < list.size(); i++) {
				map.put("bean"+i, list.get(i));
			}
			
			TemplateFile template = cl.getTemplateFile("PROFITS");

			if (!template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "利润表.xls"+"."+template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".")+1), response, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	/**
	 *  现金流量表导出
	 */ 
	 @RequestMapping(value = "excelCashFlowByReportId")
		@ResponseBody
		public void excelCashFlowByReportId(String reportId,HttpServletRequest request, HttpServletResponse response) {
			BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
			BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
				CusComService.Client client = (CusComService.Client) clientFactory.getClient();
				
				List<ExcelCusComIncomeReport> list = client.excelCashFlowByReportId(reportId);	
				for(int i=1;i<list.size();i++){
					map.put("bean"+i, list.get(i));
					
				}
				TemplateFile template = cl.getTemplateFile("CASHFLOW");
				if (!template.getFileUrl().equals("")) {
					String path = CommonUtil.getRootPath() + template.getFileUrl();
					ExcelExport.outToExcel(map, path, "现金流量表.xls"+"."+template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".")+1), response, request);
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (clientFactory != null) {
					try {
						clientFactory.destroy();
					} catch (ThriftException e) {
						e.printStackTrace();
					}
				}
			}
		}
		/**
		 *  现金流量表材料导出
		 */ 
		 @RequestMapping(value = "exportCashFlowMaterialByReportId")
			@ResponseBody
			public void exportCashFlowMaterialByReportId(String reportPid,HttpServletRequest request, HttpServletResponse response) {
				BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComService");
				BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
				Map<String, Object> map = new HashMap<String, Object>();
				try {
					TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
					CusComService.Client client = (CusComService.Client) clientFactory.getClient();
					
					List<ExcelCusComIncomeReport> list = client.exportCashFlowMaterialByReportId(reportPid);	
					for(int i=0;i<list.size();i++){
						map.put("bean"+i, list.get(i));
						
					}
					TemplateFile template = cl.getTemplateFile("CASHFLOWMATER");
					if (!template.getFileUrl().equals("")) {
						String path = CommonUtil.getRootPath() + template.getFileUrl();
						ExcelExport.outToExcel(map, path, "现金流量表补充资料导出.xls"+"."+template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".")+1), response, request);
					}

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (clientFactory != null) {
						try {
							clientFactory.destroy();
						} catch (ThriftException e) {
							e.printStackTrace();
						}
					}
				}
			}
	/**
	 * 
	 * @Description: 填写企业财务状况
	 * @return 填写企业财务状况页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/gotoComFinancialStatus")
	public String gotoComFinancialStatus(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "comId", required = false) Integer comId, @RequestParam(value = "acctId", required = false) Integer acctId, ModelMap modeld) throws Exception {
		modeld.put("acctId", acctId);
		modeld.put("comId", comId);

		return cuscom + "list_com_financialStatus";
	}

	

	/**
	 * 
	 * @Description: 填写企业财务状况
	 * @return 填写企业财务状况页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/listComFinancialStatus")
	@ResponseBody
	public void listComFinancialStatus(HttpServletRequest request,
			HttpServletResponse response,
			@RequestParam(value = "comId", required = false) Integer comId,
			@RequestParam(value = "acctId", required = false) Integer acctId,
			ModelMap modeld) throws Exception {
		   modeld.put("acctId", acctId);
		   modeld.put("comId", comId);



		   BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComCalculateService");
			try {

				CusComCalculateService.Client client = (CusComCalculateService.Client) clientFactory.getClient();

				Calendar rightNow = Calendar.getInstance();
				int year11 = rightNow.get(Calendar.YEAR);//当前年度
				int year22 = year11-1;//前一年年度
				int year33 = year11-2;//前两年年度
				int year44 = year11-3;//前三年年度



				
				String year1 =  Integer.toString(year11);
				String year2 =  Integer.toString(year22);
				String year3 =  Integer.toString(year33);
				String year4 =  Integer.toString(year44);
				
				String result_str1 = client.obtainPIDForMaxMonth(comId,year11);
				String result_str2 = client.obtainPIDForMaxMonth(comId,year22);
				String result_str3 = client.obtainPIDForMaxMonth(comId,year33); 
				String result_str4 = client.obtainPIDForMaxMonth(comId,year44);
				
				String[] strArray1 = result_str1.split(":");
				String[] strArray2 = result_str2.split(":");
				String[] strArray3 = result_str3.split(":");
				String[] strArray4 = result_str4.split(":");
				
				int reportId1 = Integer.parseInt(strArray1[0]);
				int reportId2 = Integer.parseInt(strArray2[0]);
				int reportId3 = Integer.parseInt(strArray3[0]);
				int reportId4 = Integer.parseInt(strArray4[0]);
				
				String month1 = "-1".equals(strArray1[1])?"":"13".equals(strArray1[1])?"年度":strArray1[1]+"月";
				String month2 = "-1".equals(strArray2[1])?"":"13".equals(strArray2[1])?"年度":strArray2[1]+"月";
				String month3 = "-1".equals(strArray3[1])?"":"13".equals(strArray3[1])?"年度":strArray3[1]+"月";
				String month4 = "-1".equals(strArray4[1])?"":"13".equals(strArray4[1])?"年度":strArray4[1]+"月";
				
				year1 = "".equals(month1)?year1+"年无":year1 + "年" + month1;
				year2 = "".equals(month2)?year2+"年无":year2 + "年" + month2;
				year3 = "".equals(month3)?year3+"年无":year3 + "年" + month3;
				year4 = "".equals(month4)?year4+"年无":year4 + "年" + month4;

				
				//取资产负债左表
				Map<Integer,Double> leftResultLeft1 = client.obtainLeftCusComBSLCalculatesMap(reportId1); 
				Map<Integer,Double> leftResultLeft2 = client.obtainLeftCusComBSLCalculatesMap(reportId2);
				Map<Integer,Double> leftResultLeft3 = client.obtainLeftCusComBSLCalculatesMap(reportId3);
				Map<Integer,Double> leftResultLeft4 = client.obtainLeftCusComBSLCalculatesMap(reportId4);

				//取资产负债右表
				Map<Integer,Double> RightResultRight1 = client.obtainRightCusComBSLCalculatesMap(reportId1);
				Map<Integer,Double> RightResultRight2 = client.obtainRightCusComBSLCalculatesMap(reportId2);
				Map<Integer,Double> RightResultRight3 = client.obtainRightCusComBSLCalculatesMap(reportId3);
				Map<Integer,Double> RightResultRight4 = client.obtainRightCusComBSLCalculatesMap(reportId4);

				//取利润表
				Map<Integer,Double> profitReport1 = client.obtainIncomeReportCalculateMap(reportId1);
				Map<Integer,Double> profitReport2 = client.obtainIncomeReportCalculateMap(reportId2);
				Map<Integer,Double> profitReport3 = client.obtainIncomeReportCalculateMap(reportId3);
				Map<Integer,Double> profitReport4 = client.obtainIncomeReportCalculateMap(reportId4);
				
				//拿产负债左表年初数
				List<CusComBalanceSheetCalculateDTO> leftBSL1 = client.obtainLeftCusComBSLCalculatesByReportId(reportId1);
				List<CusComBalanceSheetCalculateDTO> leftBSL2 = client.obtainLeftCusComBSLCalculatesByReportId(reportId2);
				List<CusComBalanceSheetCalculateDTO> leftBSL3 = client.obtainLeftCusComBSLCalculatesByReportId(reportId3);
				List<CusComBalanceSheetCalculateDTO> leftBSL4 = client.obtainLeftCusComBSLCalculatesByReportId(reportId4);
				
				double averageProperty1 = 0;
				double averageProperty2 = 0;
				double averageProperty7 = 0;
				double constructFundTwo1 = 0;
				double beginVal11 = 0;
				double endVal11 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL1) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						beginVal11 = cusComBalanceSheetCalculateDTO.getBeginVal();
						endVal11 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty1 = (beginVal11 + endVal11) / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty2 = (beginVal + endVal) / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty7 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo1 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty11 = 0;
				double averageProperty22 = 0;
				double averageProperty77 = 0;
				double endValTk2 = 0;
				double beginValTk2 = 0;
				double constructFundTwo2 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL2) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
 					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						endValTk2 = cusComBalanceSheetCalculateDTO.getEndVal();
						beginValTk2 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty11 = (beginVal + endVal) / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty22 = (beginVal + endVal) / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty77 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo2 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}

				double averageProperty111 = 0;
				double averageProperty222 = 0;
				double averageProperty777 = 0;
				double endValTk3 = 0;
				double beginValTk3 = 0;
				double constructFundTwo3 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL3) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						endValTk3 = cusComBalanceSheetCalculateDTO.getEndVal();
						beginValTk3 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty111 = (beginVal + endVal) / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty222 = (beginVal + endVal) / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty777 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo3 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty1111 = 0;
				double averageProperty2222 = 0;
				double averageProperty7777 = 0;
				double endValTk4 = 0;
				double beginValTk4 = 0;
				double constructFundTwo4 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL4) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						endValTk4 = cusComBalanceSheetCalculateDTO.getEndVal();
						beginValTk4 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty1111 = beginVal + endVal / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty2222 = beginVal + endVal / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty7777 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo4 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				//拿产负债右表年初数
				List<CusComBalanceSheetCalculateDTO> rightBSL1 = client.obtainRightCusComBSLCalculatesByReportId(reportId1);
				List<CusComBalanceSheetCalculateDTO> rightBSL2 = client.obtainRightCusComBSLCalculatesByReportId(reportId2);
				List<CusComBalanceSheetCalculateDTO> rightBSL3 = client.obtainRightCusComBSLCalculatesByReportId(reportId3);
				List<CusComBalanceSheetCalculateDTO> rightBSL4 = client.obtainRightCusComBSLCalculatesByReportId(reportId4);

				double averageProperty101 =0;
				double averageProperty102 =0;
				double constructFundTwo11 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL1) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty101 = beginVal11 - beginVal;
						averageProperty102 = endVal11 - endVal;
					}if(lineNum16==16){
						constructFundTwo11 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty1011 =0;
				double averageProperty1022 =0;
				double constructFundTwo22 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL2) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty1011 = beginValTk2 - beginVal;
						averageProperty1022 = endValTk2 - endVal;
					}if(lineNum16==16){
						constructFundTwo22 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty10111 =0;
				double averageProperty10222 =0;
				double constructFundTwo33 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL3) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty10111 = beginValTk3 - beginVal;
						averageProperty10222 = endValTk3 - endVal;
					}if(lineNum16==16){
						constructFundTwo33 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty101111 =0;
				double averageProperty102222 =0;
				double constructFundTwo44 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL4) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty101111 = beginValTk4 - beginVal;
						averageProperty102222 = endValTk4 - endVal;
					}if(lineNum16==16){
						constructFundTwo44 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}

				//1.总资产：资产负债表的资产总计；
				double result1 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if(i==37){
						result1 = result1 + leftResultLeft1.get(i);
					}
				}
				double result2 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if(i==37){
						result2 = result2 + leftResultLeft2.get(i);
					}
				}
				double result3 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if(i==37){
						result3 = result3 + leftResultLeft3.get(i);
					}
				}
				double result4 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if(i==37){
						result4 = result4 + leftResultLeft4.get(i);
					}
				}

				//2.净资产：总资产-负债总计
				double purgPproperty1 = 0;
				double purgPproperty2 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==37) {
						purgPproperty1 = purgPproperty1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==26) {
						purgPproperty2 = purgPproperty2 + RightResultRight1.get(j);
					}
				}
				double purgPproperty3 = purgPproperty1 - purgPproperty2;

				double purgPproperty11 = 0;
				double purgPproperty22 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==37) {
						purgPproperty11 = purgPproperty11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==26) {
						purgPproperty22 = purgPproperty22 + RightResultRight2.get(j);
					}
				}
				double purgPproperty33 = purgPproperty11 - purgPproperty22;

				double purgPproperty111 = 0;
				double purgPproperty222 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==37) {
						purgPproperty111 = purgPproperty111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==26) {
						purgPproperty222 = purgPproperty222 + RightResultRight3.get(j);
					}
				}
				double purgPproperty333 = purgPproperty111 - purgPproperty222;

				double purgPproperty1111 = 0;
				double purgPproperty2222 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==37) {
						purgPproperty1111 = purgPproperty1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==26) {
						purgPproperty2222 = purgPproperty2222 + RightResultRight4.get(j);
					}
				}
				double purgPproperty3333 = purgPproperty1111 - purgPproperty2222;

				//3.流动资产：资产负债表的流动资产合计
				double flowPproperty1 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==15) {
						flowPproperty1 = flowPproperty1 + leftResultLeft1.get(i);
					}
				}
				double flowPproperty2 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==15) {
						flowPproperty2 = flowPproperty2 + leftResultLeft2.get(i);
					}
				}
				double flowPproperty3 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==15) {
						flowPproperty3 = flowPproperty3 + leftResultLeft3.get(i);
					}
				}
				double flowPproperty4 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==15) {
						flowPproperty4 = flowPproperty4 + leftResultLeft4.get(i);
					}
				}
				//4.流动负债：资产负债表的流动负债合计；
				double flowBurden1 = 0;
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==16) {
						flowBurden1 = flowBurden1 + RightResultRight1.get(j);
					}
				}
				double flowBurden2 = 0;
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==16) {
						flowBurden2 = flowBurden2 + RightResultRight2.get(j);
					}
				}
				double flowBurden3 = 0;
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==16) {
						flowBurden3 = flowBurden3 + RightResultRight3.get(j);
					}
				}
				double flowBurden4 = 0;
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==16) {
						flowBurden4 = flowBurden4 + RightResultRight4.get(j);
					}
				}

				//5.营运资金：流动资产-流动负债；
				double constructFund1 = 0;
				double constructFund2 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==15) {
						constructFund1 = constructFund1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==16) {
						constructFund2 = constructFund2 + RightResultRight1.get(j);
					}
				}
				double constructFund3 = constructFund1 - constructFund2;

				double constructFund11 = 0;
				double constructFund22 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==15) {
						constructFund11 = constructFund11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==16) {
						constructFund22 = constructFund22 + RightResultRight2.get(j);
					}
				}
				double constructFund33 = constructFund11 - constructFund22;
				
				double constructFund111 = 0;
				double constructFund222 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==15) {
						constructFund111 = constructFund111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==16) {
						constructFund222 = constructFund222 + RightResultRight3.get(j);
					}
				}
				double constructFund333 = constructFund111 - constructFund222;
				
				double constructFund1111 = 0;
				double constructFund2222 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==15) {
						constructFund1111 = constructFund1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==16) {
						constructFund2222 = constructFund2222 + RightResultRight4.get(j);
					}
				}
				double constructFund3333 = constructFund1111 - constructFund2222;


				//6.流动比率：流动资产/流动负债*100%
				double constructFundTwo301 = 0;
				if( constructFundTwo11==0){
				}else{
					constructFundTwo301 = constructFundTwo1 / constructFundTwo11 * 100;
				}
				double constructFundTwo302 = 0;
				if( constructFundTwo22==0){
				}else{
					constructFundTwo302 = constructFundTwo2 / constructFundTwo22 * 100;
				}
				double constructFundTwo303 = 0;
				if(constructFundTwo33==0){
				}else{
					constructFundTwo303 = constructFundTwo3 / constructFundTwo33 * 100;
				}
				double constructFundTwo304 = 0;
				if(constructFundTwo44==0){
				}else{
					constructFundTwo304 = constructFundTwo4 / constructFundTwo44 * 100;
				}
				
				//7.速动比率：（流动资产-存货）/流动负债*100%   存货：资产负债表的存货；
				double velocityRatio1 = 0;
				double velocityRatio2 = 0;
				double velocityRatio3 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==15) {
						velocityRatio1 = velocityRatio1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft1.size(); j++) {
					if (j==11) {
						velocityRatio2 = velocityRatio2 + leftResultLeft1.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight1.size(); y++) {
					if (y==16) {
						velocityRatio3 = velocityRatio3 + RightResultRight1.get(y);
					}
				}
				
				double velocityRatio4 = 0;
				if(velocityRatio3==0){
				}else{
					velocityRatio4 = (velocityRatio1 - velocityRatio2) / velocityRatio3 * 100;
				}
				
				double velocityRatio11 = 0;
				double velocityRatio22 = 0;
				double velocityRatio33 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==15) {
						velocityRatio11 = velocityRatio11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft2.size(); j++) {
					if (j==11) {
						velocityRatio22 = velocityRatio22 + leftResultLeft2.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight2.size(); y++) {
					if (y==16) {
						velocityRatio33 = velocityRatio33 + RightResultRight2.get(y);
					}
				}
				double velocityRatio44 = 0;
				if(velocityRatio33==0){
				}else{
					velocityRatio44 = (velocityRatio11 - velocityRatio22) / velocityRatio33 * 100;
				}
				
				double velocityRatio111 = 0;
				double velocityRatio222 = 0;
				double velocityRatio333 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==15) {
						velocityRatio111 = velocityRatio111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft3.size(); j++) {
					if (j==11) {
						velocityRatio222 = velocityRatio222 + leftResultLeft3.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight3.size(); y++) {
					if (y==16) {
						velocityRatio333 = velocityRatio333 + RightResultRight3.get(y);
					}
				}
				double velocityRatio444 = 0;
				if(velocityRatio333==0){
				}else{
					velocityRatio444 =  (velocityRatio111 - velocityRatio222) / velocityRatio333 * 100;
				}
				
				double velocityRatio1111 = 0;
				double velocityRatio2222 = 0;
				double velocityRatio3333 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==15) {
						velocityRatio1111 = velocityRatio1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft4.size(); j++) {
					if (j==11) {
						velocityRatio2222 = velocityRatio2222 + leftResultLeft4.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight4.size(); y++) {
					if (y==16) {
						velocityRatio3333 = velocityRatio3333 + RightResultRight4.get(y);
					}
				}
				double velocityRatio4444 = 0;
				if(velocityRatio3333==0){
				}else{
					velocityRatio444 =  (velocityRatio1111 - velocityRatio2222) / velocityRatio3333 * 100;
				}
				
				
				//8.	资产与负债率：负债合计/资产总计*100%   
				double propertyBearRate1 = 0;
				double propertyBearRate2 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==37) {
						propertyBearRate1 = propertyBearRate1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==26) {
						propertyBearRate2 = propertyBearRate2 + RightResultRight1.get(j);
					}
				}
				
				double propertyBearRate3 = 0;
				if(propertyBearRate1==0){
				}else{
					propertyBearRate3 =  propertyBearRate2 / propertyBearRate1 * 100;
				}

				double propertyBearRate11 = 0;
				double propertyBearRate22 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==37) {
						propertyBearRate11 = propertyBearRate11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==26) {
						propertyBearRate22 = propertyBearRate22 + RightResultRight2.get(j);
					}
				}
				double propertyBearRate33 = 0;
				if(propertyBearRate11==0){
				}else{
					propertyBearRate33 =  propertyBearRate22 / propertyBearRate11 * 100;
				}

				
				double propertyBearRate111 = 0;
				double propertyBearRate222 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==37) {
						propertyBearRate111 = propertyBearRate111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==26) {
						propertyBearRate222 = propertyBearRate222 + RightResultRight3.get(j);
					}
				}

				double propertyBearRate333 = 0;
				if(propertyBearRate111==0){
				}else{
					propertyBearRate333 = propertyBearRate222 / propertyBearRate111 * 100;
				}
				
				double propertyBearRate1111 = 0;
				double propertyBearRate2222 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==37) {
						propertyBearRate1111 = propertyBearRate1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==26) {
						propertyBearRate2222 = propertyBearRate2222 + RightResultRight4.get(j);
					}
				}

				double propertyBearRate3333 = 0;
				if(propertyBearRate1111==0){
				}else{
					propertyBearRate3333 = propertyBearRate2222 / propertyBearRate1111 * 100;
				}
				
				//9.	负债与所有者权益比例：负债合计/所有者权益合计；
				double weightScale1 = 0;
				if(RightResultRight1.size()>0){
					if(RightResultRight1.get(36)==0){
					}else{
						weightScale1 = RightResultRight1.get(26) / RightResultRight1.get(36);
					}
				}

				double weightScale2 = 0;
				if(RightResultRight2.size()>0){
					if(RightResultRight2.get(36)==0){
					}else{
						weightScale2 = RightResultRight2.get(26) / RightResultRight2.get(36);
					}
				}
					

				double weightScale3 = 0;
				if(RightResultRight3.size()>0){
					if(RightResultRight3.get(36)==0){
					}else{
						weightScale3 = RightResultRight3.get(26) / RightResultRight3.get(36);
					}
				}
				
				double weightScale4 = 0;
				/*for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==26 || j==36) {*/
					if(RightResultRight4.size()>0){ 
						if(RightResultRight4.get(36)==0){
						}else{
							weightScale4 = RightResultRight4.get(26) / RightResultRight4.get(36);
						}
					}
					/*}
				}*/
				
				//10.	利息保障倍数：（企业的净利润(净利润)+企业支付的利息费用(财务费用)+企业支付的所得税）/利息费用	
				double accrualEnsure1 = 0;
				double accrualEnsure2 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure1 = accrualEnsure1 + profitReport1.get(l);
					}
				}
				for (int k = 1; k <= profitReport1.size(); k++) {
					if (k==8) {
						accrualEnsure2 = accrualEnsure2 + profitReport1.get(k);
					}
				}
				double accrualEnsure3 = 0;
				if(accrualEnsure2==0){
				}else{
					 accrualEnsure3 = accrualEnsure1 / accrualEnsure2;
				}
			
				double accrualEnsure11 = 0;
				double accrualEnsure22 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure11 = accrualEnsure11 + profitReport2.get(l);
					}
				}
				for (int k = 1; k <= profitReport2.size(); k++) {
					if (k==8) {
						accrualEnsure22 = accrualEnsure22 + profitReport2.get(k);
					}
				}
				double accrualEnsure33 = 0;
				if(accrualEnsure22==0){
				}else{
					 accrualEnsure33 = accrualEnsure11 / accrualEnsure22;
				}
				
				double accrualEnsure111 = 0;
				double accrualEnsure222 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure111 = accrualEnsure111 + profitReport3.get(l);
					}
				}
				for (int k = 1; k <= profitReport3.size(); k++) {
					if (k==8) {
						accrualEnsure222 = accrualEnsure222 + profitReport3.get(k);
					}
				}
				double accrualEnsure333 = 0;
				if(accrualEnsure222==0){
				}else{
					accrualEnsure333 = accrualEnsure111 / accrualEnsure222;
				}
				
				double accrualEnsure1111 = 0;
				double accrualEnsure2222 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure1111 = accrualEnsure1111 + profitReport4.get(l);
					}
				}
				for (int k = 1; k <= profitReport3.size(); k++) {
					if (k==8) {
						accrualEnsure2222 = accrualEnsure2222 + profitReport4.get(k);
					}
				}
				double accrualEnsure3333 = 0;
				if(accrualEnsure2222==0){
				}else{
					accrualEnsure3333 = accrualEnsure1111 / accrualEnsure2222;
				}
				
				//11.主营业务收入：取自利润表；
				double mainBusiness1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==1) {
						mainBusiness1 = mainBusiness1 + profitReport1.get(l);
					}
				}
				double mainBusiness2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==1) {
						mainBusiness2 = mainBusiness2 + profitReport2.get(l);
					}
				}
				double mainBusiness3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==1) {
						mainBusiness3 = mainBusiness3 + profitReport3.get(l);
					}
				}
				double mainBusiness4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==1) {
						mainBusiness4 = mainBusiness4 + profitReport4.get(l);
					}
				}
				
				//12.主营业务成本：取自利润表；
				double mainBusinessCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==2) {
						mainBusinessCost1 = mainBusinessCost1 + profitReport1.get(l);
					}
				}
				double mainBusinessCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==2) {
						mainBusinessCost2 = mainBusinessCost2 + profitReport2.get(l);
					}
				}
				double mainBusinessCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==2) {
						mainBusinessCost3 = mainBusinessCost3 + profitReport3.get(l);
					}
				}
				double mainBusinessCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==2) {
						mainBusinessCost4 = mainBusinessCost4 + profitReport4.get(l);
					}
				}
				//13.营业费用：取自利润表；
				double BusinessCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==6) {
						BusinessCost1 = BusinessCost1 + profitReport1.get(l);
					}
				}
				double BusinessCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==6) {
						BusinessCost2 = BusinessCost2 + profitReport2.get(l);
					}
				}
				double BusinessCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==6) {
						BusinessCost3 = BusinessCost3 + profitReport3.get(l);
					}
				}
				double BusinessCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==6) {
						BusinessCost4 = BusinessCost4 + profitReport4.get(l);
					}
				}
				//14.管理费用：取自利润表；
				double superviseCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==7) {
						superviseCost1 = superviseCost1 + profitReport1.get(l);
					}
				}
				double superviseCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==7) {
						superviseCost2 = superviseCost2 + profitReport2.get(l);
					}
				}
				double superviseCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==7) {
						superviseCost3 = superviseCost3 + profitReport3.get(l);
					}
				}
				double superviseCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==7) {
						superviseCost4 = superviseCost4 + profitReport4.get(l);
					}
				}
				//15.财务费用：取自利润表；
				double financeCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==8) {
						financeCost1 = financeCost1 + profitReport1.get(l);
					}
				}
				double financeCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==8) {
						financeCost2 = financeCost2 + profitReport2.get(l);
					}
				}
				double financeCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==8) {
						financeCost3 = financeCost3 + profitReport3.get(l);
					}
				}
				double financeCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==8) {
						financeCost4 = financeCost4 + profitReport4.get(l);
					}
				}
				//16.营业利润：取自利润表；
				double businessProfit1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==9) {
						businessProfit1 = businessProfit1 + profitReport1.get(l);
					}
				}
				double businessProfit2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==9) {
						businessProfit2 = businessProfit2 + profitReport2.get(l);
					}
				}
				double businessProfit3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==9) {
						businessProfit3 = businessProfit3 + profitReport3.get(l);
					}
				}
				double businessProfit4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==9) {
						businessProfit4 = businessProfit4 + profitReport4.get(l);
					}
				}
				//17.利润总额：取自利润表；
				double profitCount1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==15) {
						profitCount1 = profitCount1 + profitReport1.get(l);
					}
				}
				double profitCount2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==15) {
						profitCount2 = profitCount2 + profitReport2.get(l);
					}
				}
				double profitCount3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==15) {
						profitCount3 = profitCount3 + profitReport3.get(l);
					}
				}
				double profitCount4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==15) {
						profitCount4 = profitCount4 + profitReport4.get(l);
					}
				}
				
				//18.净利润：取自利润表
				double purifyProfit1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==18) {
						purifyProfit1 = purifyProfit1 + profitReport1.get(l);
					}
				}
				double purifyProfit2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==18) {
						purifyProfit2 = purifyProfit2 + profitReport2.get(l);
					}
				}
				double purifyProfit3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==18) {
						purifyProfit3 = purifyProfit3 + profitReport3.get(l);
					}
				}
				double purifyProfit4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==18) {
						purifyProfit4 = purifyProfit4 + profitReport4.get(l);
					}
				}
				
				//19.毛利率：（主营业务收入mainBusiness1-主营业务成本mainBusinessCost1）/主营业务收入mainBusiness1*100%； 
				double hairProfit1 = 0;
				if(mainBusiness1==0){
				}else{
					 hairProfit1 = (mainBusiness1 - mainBusinessCost1) / mainBusiness1 * 100;
				}
				
				double hairProfit2 = 0;
				if(mainBusiness2==0){
				}else{
					hairProfit2 = (mainBusiness2 - mainBusinessCost2) / mainBusiness2 * 100;
				}
				
				double hairProfit3 = 0;
				if(mainBusiness3==0){
				}else{
					hairProfit3 = (mainBusiness3 - mainBusinessCost3) / mainBusiness3 * 100;
				}
				
				double hairProfit4 = 0;
				if(mainBusiness4==0){
				}else{
					 hairProfit4 = (mainBusiness4 - mainBusinessCost4) / mainBusiness4 * 100;
				}

				//20.营业利润率：营业利润businessProfit1 /主营业务收入mainBusiness1 *100%；
				double businessProfitRate1 = 0;
				if(mainBusiness1==0){
				}else{
					businessProfitRate1 = businessProfit1 / mainBusiness1 * 100;
				}
				
				double businessProfitRate2 = 0;
				if(mainBusiness2==0){
				}else{
					businessProfitRate2 = businessProfit2 / mainBusiness2 * 100;
				}
				
				double businessProfitRate3 = 0;
				if(mainBusiness3==0){
				}else{
					businessProfitRate3 = businessProfit3 / mainBusiness3 * 100;
				}
				
				double businessProfitRate4 = 0;
				if(mainBusiness4==0){
				}else{
					businessProfitRate4 = businessProfit4 / mainBusiness4 * 100;
				}
				

				//21.销售利润率：利润总额profitCount1/主营业务收入mainBusiness1*100%；
				double sellProfitRate1 = 0;
				if(mainBusiness1==0){
				}else{
					sellProfitRate1 = profitCount1 / mainBusiness1 * 100;
				}
				
				double sellProfitRate2 = 0;
				if(mainBusiness2==0){
				}else{
					sellProfitRate2 = profitCount2 / mainBusiness2 * 100;
				}
				
				double sellProfitRate3 = 0;
				if( mainBusiness3==0){
				}else{
					sellProfitRate3 = profitCount3 / mainBusiness3 * 100;
				}
				
				double sellProfitRate4 = 0;
				if( mainBusiness4==0){
				}else{
					sellProfitRate4 = profitCount4 / mainBusiness4 * 100;
				}
				

				//22.净利润率：净利润purifyProfit1/主营业务收入mainBusiness1*100%；
				double purifyProfitRate1 = 0;
				if(mainBusiness1==0){
				}else{
					purifyProfitRate1 = purifyProfit1 / mainBusiness1 * 100;
				}
				
				double purifyProfitRate2 = 0;
				if(mainBusiness2==0){
				}else{
					purifyProfitRate2 = purifyProfit2 / mainBusiness2 * 100;
				}
				
				double purifyProfitRate3 = 0;
				if( mainBusiness3==0){
				}else{
					purifyProfitRate3 = purifyProfit3 / mainBusiness3 * 100;
				}
				
				double purifyProfitRate4 = 0;
				if(mainBusiness4==0){
				}else{
					purifyProfitRate4 = purifyProfit4 / mainBusiness4 * 100;
				}
				
				
				//23.净资产收益率：税后利润/所有者权益；
				double propertyIncomeRate1 = 0;
				double propertyIncomeRate11 = 0;
				for (int j = 1; j <= profitReport1.size(); j++) {
					if (j==16) {
						propertyIncomeRate1 = propertyIncomeRate1 + profitReport1.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight1.size(); r++) {
					if (r==36) {
						propertyIncomeRate11 = propertyIncomeRate11 + RightResultRight1.get(r);
					}
				}
				
				double propertyIncomeRate2 = 0;
				double propertyIncomeRate22 = 0;
				for (int j = 1; j <= profitReport2.size(); j++) {
					if (j==16) {
						propertyIncomeRate2 = propertyIncomeRate2 + profitReport2.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight2.size(); r++) {
					if (r==36) {
						propertyIncomeRate22 = propertyIncomeRate22 + RightResultRight2.get(r);
					}
				}
				
				double propertyIncomeRate3 = 0;
				double propertyIncomeRate33 = 0;
				for (int j = 1; j <= profitReport3.size(); j++) {
					if (j==16) {
						propertyIncomeRate3 = propertyIncomeRate3 + profitReport3.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight3.size(); r++) {
					if (r==36) {
						propertyIncomeRate33 = propertyIncomeRate33 + RightResultRight3.get(r);
					}
				}
				
				double propertyIncomeRate4 = 0;
				double propertyIncomeRate44 = 0;
				for (int j = 1; j <= profitReport4.size(); j++) {
					if (j==16) {
						propertyIncomeRate4 = propertyIncomeRate4 + profitReport4.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight4.size(); r++) {
					if (r==36) {
						propertyIncomeRate44 = propertyIncomeRate44 +RightResultRight4.get(r);
					}
				}
				
				double propertyIncomeRate101 = 0;
				if( propertyIncomeRate11==0){
				}else{
					propertyIncomeRate101 = propertyIncomeRate1 / propertyIncomeRate11;
				}
				
				double propertyIncomeRate102 = 0;
				if(propertyIncomeRate22==0){
				}else{
					propertyIncomeRate102 = propertyIncomeRate2 / propertyIncomeRate22;
				}
				
				double propertyIncomeRate103 = 0;
				if(propertyIncomeRate33==0){
				}else{
					propertyIncomeRate103 = propertyIncomeRate3 / propertyIncomeRate33;
				}
				
				double propertyIncomeRate104 = 0;
				if( propertyIncomeRate44==0){
				}else{
					propertyIncomeRate104 = propertyIncomeRate4 / propertyIncomeRate44;
				}
				
				
				//24.成本费用利润率：利润总额profitCount1/成本费用总额*100%；
				double primeCostProfit1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit1 = primeCostProfit1 + profitReport1.get(l);
					}
				}
				double primeCostProfit2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit2 = primeCostProfit2 + profitReport2.get(l);
					}
				}
				double primeCostProfit3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit3 = primeCostProfit3 + profitReport3.get(l);
					}
				}
				double primeCostProfit4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit4 = primeCostProfit4 + profitReport4.get(l);
					}
				}
				
				double primeCostProfit101 = 0;
				if(primeCostProfit1==0){
				}else{
					primeCostProfit101 = profitCount1 / primeCostProfit1 * 100;
				}
				
				double primeCostProfit102 = 0;
				if(primeCostProfit2==0){
				}else{
					primeCostProfit102 = profitCount2 / primeCostProfit2 * 100;
				}
				
				double primeCostProfit103 = 0;
				if(primeCostProfit3==0){
				}else{
					primeCostProfit103 = profitCount3 / primeCostProfit3 * 100;
				}
				
				double primeCostProfit104 = 0;
				if(primeCostProfit4==0){
				}else{
					 primeCostProfit104 = profitCount4 / primeCostProfit4 * 100;
				}
				

				//25.	总资产报酬率：（利润总额profitCount1+利息支出financeCost1）/平均资产总额X100%
				double countProperty1 = 0;
				if(averageProperty1==0){
				}else{
					countProperty1 = (profitCount1 + financeCost1) / averageProperty1 * 100;
				}
				
				double countProperty2 = 0;
				if(averageProperty11==0){
				}else{
					countProperty2 = (profitCount2 + financeCost2) / averageProperty11 * 100;
				}
				
				double countProperty3 = 0;
				if(averageProperty111==0){
				}else{
					countProperty3 = (profitCount3 + financeCost3) / averageProperty111 * 100;
				}
				
				double countProperty4 = 0;
				if(averageProperty1111==0){
				}else{
					countProperty4 = (profitCount4 + financeCost4) / averageProperty1111 * 100;
				}
				
						
				//26.	存货：取自资产负债表； 已写入
				//27.	应收账款：取自资产负债表；
				double incomeCredit1 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if(i==7){
						incomeCredit1 = incomeCredit1 + leftResultLeft1.get(i);
					}
				}
				double incomeCredit2 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if(i==7){
						incomeCredit2 = incomeCredit2 + leftResultLeft2.get(i);
					}
				}
				double incomeCredit3 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if(i==7){
						incomeCredit3 = incomeCredit3 + leftResultLeft3.get(i);
					}
				}
				double incomeCredit4 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if(i==7){
						incomeCredit4 = incomeCredit4 + leftResultLeft4.get(i);
					}
				}

				//28.	总资产周转率：销售收入mainBusiness1/总资产purgPproperty1...；
				double turnover1 = 0;
				if( purgPproperty1==0){
				}else{
					turnover1 = mainBusiness1 / purgPproperty1;
				}
				
				double turnover2 = 0;
				if( purgPproperty11==0){
				}else{
					turnover2 = mainBusiness2 / purgPproperty11;
				}
				
				double turnover3 = 0;
				if( purgPproperty111==0){
				}else{
					turnover3 = mainBusiness3 / purgPproperty111;
				}
				
				double turnover4 = 0;
				if( purgPproperty1111==0){
				}else{
					turnover4 = mainBusiness4 / purgPproperty1111;
				}

				//29.存货周转率：营业收入mainBusiness1/存货平均余额averageProperty1
				double stock1 = 0;
				if( averageProperty2==0){
				}else{
					stock1 = mainBusiness1 / averageProperty2;
				}
				
				double stock2 =0;
				if(averageProperty22==0){
				}else{
					stock2 = mainBusiness2 / averageProperty22;
				}
				
				double stock3 = 0;
				if(averageProperty222==0){
				}else{
					stock3 = mainBusiness3 / averageProperty222;
				}
				
				double stock4 = 0;
				if(averageProperty2222==0){
				}else{
					stock4 = mainBusiness4 / averageProperty2222;
				}
				
				
				//30.应收账款周转率=（期初应收账款余额 + 期末应收账款余额）/ 2  以写入
				//31.净资产周转率=销售收入/『（期初净资产总额+期末净资产总额）/2』
				double purifyStock1 = 0;
				if((averageProperty101 + averageProperty102)==0){
				}else{
					purifyStock1 = mainBusiness1 / ((averageProperty101 + averageProperty102) / 2);
				}
				
				double purifyStock2 = 0;
				if((averageProperty1011 + averageProperty1022)==0){
				}else{
					purifyStock2 = mainBusiness2 / ((averageProperty1011 + averageProperty1022) / 2);
				}
				
				double purifyStock3 =0;
				if((averageProperty10111 + averageProperty10222) ==0){
				}else{
					 purifyStock3 = mainBusiness3 / ((averageProperty10111 + averageProperty10222) / 2);
				}
				
				double purifyStock4 = 0;
				if((averageProperty101111 + averageProperty102222)==0){
				}else{
					purifyStock4 = mainBusiness4 / ((averageProperty101111 + averageProperty102222) / 2);
				}

		   FinancialPositionDTO line1 = new FinancialPositionDTO("","报表期间",obtainPne(year1),obtainPne(year2),obtainPne(year3),obtainPne(year4));

			NumberFormat numberFormat = NumberFormat.getNumberInstance();

		   BigDecimal bg5 = new BigDecimal(result1);
	       double a5 = bg5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg6 = new BigDecimal(result2);
	       double a6 = bg6.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg7 = new BigDecimal(result3);
	       double a7 = bg7.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg8 = new BigDecimal(result4);
	       double a8 = bg8.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates5 = numberFormat.format(a5);
	       	String rates6 = numberFormat.format(a6);
	       	String rates7 = numberFormat.format(a7);
	       	String rates8 = numberFormat.format(a8);
	       	
		   FinancialPositionDTO line2 = new FinancialPositionDTO("偿债能力：","总资产(万元)",obtainStr(rates5),obtainStr(rates6),obtainStr(rates7),obtainStr(rates8));

		   BigDecimal bg9 = new BigDecimal(purgPproperty3);
	       double a9 = bg9.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg10 = new BigDecimal(purgPproperty33);
	       double a10 = bg10.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg11 = new BigDecimal(purgPproperty333);
	       double a11 = bg11.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg12 = new BigDecimal(purgPproperty3333);
	       double a12 = bg12.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates9 = numberFormat.format(a9);
	       	String rates10 = numberFormat.format(a10);
	       	String rates11 = numberFormat.format(a11);
	       	String rates12 = numberFormat.format(a12);
	       	
		   FinancialPositionDTO line3 = new FinancialPositionDTO("","净资产(万元)",obtainStr(rates9),obtainStr(rates10),obtainStr(rates11),obtainStr(rates12));

		   BigDecimal bg13 = new BigDecimal(flowPproperty1);
	       double a13 = bg13.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg14 = new BigDecimal(flowPproperty2);
	       double a14 = bg14.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg15 = new BigDecimal(flowPproperty3);
	       double a15 = bg15.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg16 = new BigDecimal(flowPproperty4);
	       double a16 = bg16.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates13 = numberFormat.format(a13);
	       	String rates14 = numberFormat.format(a14);
	       	String rates15 = numberFormat.format(a15);
	       	String rates16 = numberFormat.format(a16);
	       	
		   FinancialPositionDTO line4 = new FinancialPositionDTO("","流动资产(万元)",obtainStr(rates13),obtainStr(rates14),obtainStr(rates15),obtainStr(rates16));
		   
		   BigDecimal bg17 = new BigDecimal(flowBurden1);
	       double a17 = bg17.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg18 = new BigDecimal(flowBurden2);
	       double a18 = bg18.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg19 = new BigDecimal(flowBurden3);
	       double a19 = bg19.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg20 = new BigDecimal(flowBurden4);
	       double a20 = bg20.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates17 = numberFormat.format(a17);
	       	String rates18 = numberFormat.format(a18);
	       	String rates19 = numberFormat.format(a19);
	       	String rates20 = numberFormat.format(a20);
	       	
		   FinancialPositionDTO line5 = new FinancialPositionDTO("","流动负债(万元)",obtainStr(rates17),obtainStr(rates18),obtainStr(rates19),obtainStr(rates20));
		   
		   BigDecimal bg21 = new BigDecimal(constructFund3);
	       double a21 = bg21.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg22 = new BigDecimal(constructFund33);
	       double a22 = bg22.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg23 = new BigDecimal(constructFund333);
	       double a23 = bg23.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg24 = new BigDecimal(constructFund3333);
	       double a24 = bg24.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates21 = numberFormat.format(a21);
	       	String rates22 = numberFormat.format(a22);
	       	String rates23 = numberFormat.format(a23);
	       	String rates24 = numberFormat.format(a24);
	       	
		   FinancialPositionDTO line6 = new FinancialPositionDTO("","营运资金(万元)",obtainStr(rates21),obtainStr(rates22),obtainStr(rates23),obtainStr(rates24));
		   
		   BigDecimal bg25 = new BigDecimal(constructFundTwo301);
	       double a25 = bg25.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg26 = new BigDecimal(constructFundTwo302);
	       double a26 = bg26.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg27 = new BigDecimal(constructFundTwo303);
	       double a27 = bg27.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg28 = new BigDecimal(constructFundTwo304);
	       double a28 = bg28.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates25 = numberFormat.format(a25* 100);
	       	String rates26 = numberFormat.format(a26* 100);
	       	String rates27 = numberFormat.format(a27* 100);
	       	String rates28 = numberFormat.format(a28* 100);
	       	
		   FinancialPositionDTO line7 = new FinancialPositionDTO("","流动比率(%)",obtainStr(rates25),obtainStr(rates26),obtainStr(rates27),obtainStr(rates28));
		 
		   BigDecimal bg29 = new BigDecimal(velocityRatio4);
	       double a29 = bg29.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg30 = new BigDecimal(velocityRatio44);
	       double a30 = bg30.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg31 = new BigDecimal(velocityRatio444);
	       double a31 = bg31.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg32 = new BigDecimal(velocityRatio4444);
	       double a32 = bg32.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates29 = numberFormat.format(a29* 100);
	       	String rates30 = numberFormat.format(a30* 100);
	       	String rates31 = numberFormat.format(a31* 100);
	       	String rates32 = numberFormat.format(a32* 100);
	       	
		   FinancialPositionDTO line8 = new FinancialPositionDTO("","速动比率(%)",obtainStr(rates29),obtainStr(rates30),obtainStr(rates31),obtainStr(rates32));
		   

		   BigDecimal bg33 = new BigDecimal(propertyBearRate3);
	       double a33 = bg33.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg34 = new BigDecimal(propertyBearRate33);
	       double a34 = bg34.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg35 = new BigDecimal(propertyBearRate333);
	       double a35 = bg35.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg36 = new BigDecimal(propertyBearRate3333);
	       double a36 = bg36.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates33 = numberFormat.format(a33* 100);
	       	String rates34 = numberFormat.format(a34* 100);
	       	String rates35 = numberFormat.format(a35* 100);
	       	String rates36 = numberFormat.format(a36* 100);
	       	
		   FinancialPositionDTO line9 = new FinancialPositionDTO("","资产与负债率(%)",obtainStr(rates33),obtainStr(rates34),obtainStr(rates35),obtainStr(rates36));

		   BigDecimal bg37 = new BigDecimal(weightScale1);
	       double a37 = bg37.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg38 = new BigDecimal(weightScale2);
	       double a38 = bg38.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg39 = new BigDecimal(weightScale3);
	       double a39 = bg39.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg40 = new BigDecimal(weightScale4);
	       double a40 = bg40.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates37 = numberFormat.format(a37);
	       	String rates38 = numberFormat.format(a38);
	       	String rates39 = numberFormat.format(a39);
	       	String rates40 = numberFormat.format(a40);
	       	
		   FinancialPositionDTO line10 = new FinancialPositionDTO("","负债与所有者权益比例",obtainStr(rates37),obtainStr(rates38),obtainStr(rates39),obtainStr(rates40));
		   
		   BigDecimal bg41 = new BigDecimal(accrualEnsure3);
	       double a41 = bg41.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg42 = new BigDecimal(accrualEnsure33);
	       double a42 = bg42.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg43 = new BigDecimal(accrualEnsure333);
	       double a43 = bg43.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg44 = new BigDecimal(accrualEnsure3333);
	       double a44 = bg44.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates41 = numberFormat.format(a41);
	       	String rates42 = numberFormat.format(a42);
	       	String rates43 = numberFormat.format(a43);
	       	String rates44 = numberFormat.format(a44);
		   FinancialPositionDTO line11 = new FinancialPositionDTO("","利息保障倍数",obtainStr(rates41),obtainStr(rates42),obtainStr(rates43),obtainStr(rates44));
		   
		   BigDecimal bg45 = new BigDecimal(mainBusiness1);
	       double a45 = bg45.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg46 = new BigDecimal(mainBusiness2);
	       double a46 = bg46.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg47 = new BigDecimal(mainBusiness3);
	       double a47 = bg47.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg48 = new BigDecimal(mainBusiness4);
	       double a48 = bg48.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates45 = numberFormat.format(a45);
	       	String rates46 = numberFormat.format(a46);
	       	String rates47 = numberFormat.format(a47);
	       	String rates48 = numberFormat.format(a48);
	       	
		   FinancialPositionDTO line12 = new FinancialPositionDTO("盈利能力：","主营业务收入(万元)",obtainStr(rates45),obtainStr(rates46),obtainStr(rates47),obtainStr(rates48));

		   BigDecimal bg49 = new BigDecimal(mainBusinessCost1);
	       double a49 = bg49.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg50 = new BigDecimal(mainBusinessCost2);
	       double a50 = bg50.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg51 = new BigDecimal(mainBusinessCost3);
	       double a51 = bg51.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg52 = new BigDecimal(mainBusinessCost4);
	       double a52 = bg52.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates49 = numberFormat.format(a49);
	       	String rates50 = numberFormat.format(a50);
	       	String rates51 = numberFormat.format(a51);
	       	String rates52 = numberFormat.format(a52);
	       	
		   FinancialPositionDTO line13 = new FinancialPositionDTO("","主营业务成本(万元)",obtainStr(rates49),obtainStr(rates50),obtainStr(rates51),obtainStr(rates52));

		   BigDecimal bg53 = new BigDecimal(BusinessCost1);
	       double a53 = bg53.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg54 = new BigDecimal(BusinessCost2);
	       double a54 = bg54.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg55 = new BigDecimal(BusinessCost3);
	       double a55 = bg55.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg56 = new BigDecimal(BusinessCost4);
	       double a56 = bg56.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates53 = numberFormat.format(a53);
	       	String rates54 = numberFormat.format(a54);
	       	String rates55 = numberFormat.format(a55);
	       	String rates56 = numberFormat.format(a56);
	       	
		   FinancialPositionDTO line14 = new FinancialPositionDTO("","营业费用(万元)",obtainStr(rates53),obtainStr(rates54),obtainStr(rates55),obtainStr(rates56));

		   BigDecimal bg57 = new BigDecimal(superviseCost1);
	       double a57 = bg57.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg58 = new BigDecimal(superviseCost2);
	       double a58 = bg58.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg59 = new BigDecimal(superviseCost3);
	       double a59 = bg59.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg60 = new BigDecimal(superviseCost4);
	       double a60 = bg60.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates57 = numberFormat.format(a57);
	       	String rates58 = numberFormat.format(a58);
	       	String rates59 = numberFormat.format(a59);
	       	String rates60 = numberFormat.format(a60);
	       	
		   FinancialPositionDTO line15 = new FinancialPositionDTO("","管理费用(万元)",obtainStr(rates57),obtainStr(rates58),obtainStr(rates59),obtainStr(rates60));

		   BigDecimal bg61 = new BigDecimal(financeCost1);
	       double a61 = bg61.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg62 = new BigDecimal(financeCost2);
	       double a62 = bg62.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg63 = new BigDecimal(financeCost3);
	       double a63 = bg63.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg64 = new BigDecimal(financeCost4);
	       double a64 = bg64.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates61 = numberFormat.format(a61);
	       	String rates62 = numberFormat.format(a62);
	       	String rates63 = numberFormat.format(a63);
	       	String rates64 = numberFormat.format(a64);
	       	
		   FinancialPositionDTO line16 = new FinancialPositionDTO("","财务费用(万元)",obtainStr(rates61),obtainStr(rates62),obtainStr(rates63),obtainStr(rates64));

		   BigDecimal bg65 = new BigDecimal(businessProfit1);
	       double a65 = bg65.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg66 = new BigDecimal(businessProfit2);
	       double a66 = bg66.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg67 = new BigDecimal(businessProfit3);
	       double a67 = bg67.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg68 = new BigDecimal(businessProfit4);
	       double a68 = bg68.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates65 = numberFormat.format(a65);
	       	String rates66 = numberFormat.format(a66);
	       	String rates67 = numberFormat.format(a67);
	       	String rates68 = numberFormat.format(a68);
	       	
		   FinancialPositionDTO line17 = new FinancialPositionDTO("","营业利润(万元)",obtainStr(rates65),obtainStr(rates66),obtainStr(rates67),obtainStr(rates68));

		   BigDecimal bg69 = new BigDecimal(profitCount1);
	       double a69 = bg69.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg70 = new BigDecimal(profitCount2);
	       double a70 = bg70.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg71 = new BigDecimal(profitCount3);
	       double a71 = bg71.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg72 = new BigDecimal(profitCount4);
	       double a72 = bg72.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates69 = numberFormat.format(a69);
	       	String rates70 = numberFormat.format(a70);
	       	String rates71 = numberFormat.format(a71);
	       	String rates72 = numberFormat.format(a72);
	       	
		   FinancialPositionDTO line18 = new FinancialPositionDTO("","利润总额(万元)",obtainStr(rates69),obtainStr(rates70),obtainStr(rates71),obtainStr(rates72));

		   BigDecimal bg73 = new BigDecimal(purifyProfit1);
	       double a73 = bg73.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg74 = new BigDecimal(purifyProfit2);
	       double a74 = bg74.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg75 = new BigDecimal(purifyProfit3);
	       double a75 = bg75.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg76 = new BigDecimal(purifyProfit4);
	       double a76 = bg76.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates73 = numberFormat.format(a73);
	       	String rates74 = numberFormat.format(a74);
	       	String rates75 = numberFormat.format(a75);
	       	String rates76 = numberFormat.format(a76);
	       	
		   FinancialPositionDTO line19 = new FinancialPositionDTO("","净利润(万元)",obtainStr(rates73),obtainStr(rates74),obtainStr(rates75),obtainStr(rates76));

		   BigDecimal bg77 = new BigDecimal(hairProfit1);
	       double a77 = bg77.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg78 = new BigDecimal(hairProfit2);
	       double a78 = bg78.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg79 = new BigDecimal(hairProfit3);
	       double a79 = bg79.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg80 = new BigDecimal(hairProfit4);
	       double a80 = bg80.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates77 = numberFormat.format(a77* 100);
	       	String rates78 = numberFormat.format(a78* 100);
	       	String rates79 = numberFormat.format(a79* 100);
	       	String rates80 = numberFormat.format(a80* 100);
	       	
		   FinancialPositionDTO line20 = new FinancialPositionDTO("","毛利率(%)",obtainStr(rates77),obtainStr(rates78),obtainStr(rates79),obtainStr(rates80));

		   BigDecimal bg81 = new BigDecimal(businessProfitRate1);
	       double a81 = bg81.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg82 = new BigDecimal(businessProfitRate2);
	       double a82 = bg82.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg83 = new BigDecimal(businessProfitRate3);
	       double a83 = bg83.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg84 = new BigDecimal(businessProfitRate4);
	       double a84 = bg84.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates81 = numberFormat.format(a81* 100);
	       	String rates82 = numberFormat.format(a82* 100);
	       	String rates83 = numberFormat.format(a83* 100);
	       	String rates84 = numberFormat.format(a84* 100);

		   FinancialPositionDTO line21 = new FinancialPositionDTO("","营业利润率(%)",obtainStr(rates81),obtainStr(rates82),obtainStr(rates83),obtainStr(rates84));

		   BigDecimal bg85 = new BigDecimal(sellProfitRate1);
	       double a85 = bg85.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg86 = new BigDecimal(sellProfitRate2);
	       double a86 = bg86.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg87 = new BigDecimal(sellProfitRate3);
	       double a87 = bg87.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg88 = new BigDecimal(sellProfitRate4);
	       double a88 = bg88.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
	       
	       	String rates85 = numberFormat.format(a85* 100);
	       	String rates86 = numberFormat.format(a86* 100);
	       	String rates87 = numberFormat.format(a87* 100);
	       	String rates88 = numberFormat.format(a88* 100);
	       	
		   FinancialPositionDTO line22 = new FinancialPositionDTO("","销售利润率(%)",obtainStr(rates85),obtainStr(rates86),obtainStr(rates87),obtainStr(rates88));
		   
		   BigDecimal bg89 = new BigDecimal(purifyProfitRate1);
	       double a89 = bg89.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg90 = new BigDecimal(purifyProfitRate2);
	       double a90 = bg90.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg91 = new BigDecimal(purifyProfitRate3);
	       double a91 = bg91.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg92 = new BigDecimal(purifyProfitRate4);
	       double a92 = bg92.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates89 = numberFormat.format(a89* 100);
	       	String rates90 = numberFormat.format(a90* 100);
	       	String rates91 = numberFormat.format(a91* 100);
	       	String rates92 = numberFormat.format(a92* 100);
	       	
		   FinancialPositionDTO line23 = new FinancialPositionDTO("","净利润率(%)",obtainStr(rates89),obtainStr(rates90),obtainStr(rates91),obtainStr(rates92));
		   
		   BigDecimal bg93 = new BigDecimal(propertyIncomeRate101);
	       double a93 = bg93.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg94 = new BigDecimal(propertyIncomeRate102);
	       double a94 = bg94.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg95 = new BigDecimal(propertyIncomeRate103);
	       double a95 = bg95.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg96 = new BigDecimal(propertyIncomeRate104);
	       double a96 = bg96.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates93 = numberFormat.format(a93* 100);
	       	String rates94 = numberFormat.format(a94* 100);
	       	String rates95 = numberFormat.format(a95* 100);
	       	String rates96 = numberFormat.format(a96* 100);
		   FinancialPositionDTO line24 = new FinancialPositionDTO("","净资产收益率(%)",obtainStr(rates93),obtainStr(rates94),obtainStr(rates95),obtainStr(rates96));
		  
		   BigDecimal bg97 = new BigDecimal(primeCostProfit101);
	       double a97 = bg97.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg98 = new BigDecimal(primeCostProfit102);
	       double a98 = bg98.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg99 = new BigDecimal(primeCostProfit103);
	       double a99 = bg99.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg100 = new BigDecimal(primeCostProfit104);
	       double a100 = bg100.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       	
	       	String rates97 = numberFormat.format(a97* 100);
	       	String rates98 = numberFormat.format(a98* 100);
	       	String rates99 = numberFormat.format(a99* 100);
	       	String rates100 = numberFormat.format(a100* 100);
		   FinancialPositionDTO line25 = new FinancialPositionDTO("","成本费用利润率(%)",obtainStr(rates97),obtainStr(rates98),obtainStr(rates99),obtainStr(rates100));
		 
		   BigDecimal bg101 = new BigDecimal(countProperty1);
	       double a101 = bg101.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg102 = new BigDecimal(countProperty2);
	       double a102 = bg102.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg103 = new BigDecimal(countProperty3);
	       double a103 = bg103.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg104 = new BigDecimal(countProperty4);
	       double a104 = bg104.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates101 = numberFormat.format(a101* 100);
	       	String rates102 = numberFormat.format(a102* 100);
	       	String rates103 = numberFormat.format(a103* 100);
	       	String rates104 = numberFormat.format(a104* 100);
	       	
		   FinancialPositionDTO line26 = new FinancialPositionDTO("","总资产报酬率(%)",obtainStr(rates101),obtainStr(rates102),obtainStr(rates103),obtainStr(rates104));
		   
		   BigDecimal bg105 = new BigDecimal(velocityRatio2);
	       double a105 = bg105.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg106 = new BigDecimal(velocityRatio22);
	       double a106 = bg106.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg107 = new BigDecimal(velocityRatio222);
	       double a107 = bg107.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg108 = new BigDecimal(velocityRatio2222);
	       double a108 = bg108.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates105 = numberFormat.format(a105);
	       	String rates106 = numberFormat.format(a106);
	       	String rates107 = numberFormat.format(a107);
	       	String rates108 = numberFormat.format(a108);
		   FinancialPositionDTO line27 = new FinancialPositionDTO("营运能力：","存货(万元)",obtainStr(rates105),obtainStr(rates106),obtainStr(rates107),obtainStr(rates108));
		 
		   BigDecimal bg109 = new BigDecimal(incomeCredit1);
	       double a109 = bg109.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg110 = new BigDecimal(incomeCredit2);
	       double a110 = bg110.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg111 = new BigDecimal(incomeCredit3);
	       double a111 = bg111.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg112 = new BigDecimal(incomeCredit4);
	       double a112 = bg112.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates109 = numberFormat.format(a109);
	       	String rates110 = numberFormat.format(a110);
	       	String rates111 = numberFormat.format(a111);
	       	String rates112 = numberFormat.format(a112);
		   FinancialPositionDTO line28 = new FinancialPositionDTO("","应收账款(万元)",obtainStr(rates109),obtainStr(rates110),obtainStr(rates111),obtainStr(rates112));
		  
		   BigDecimal bg113 = new BigDecimal(turnover1);
	       double a113 = bg113.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg114 = new BigDecimal(turnover2);
	       double a114 = bg114.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg115 = new BigDecimal(turnover3);
	       double a115 = bg115.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg116 = new BigDecimal(turnover4);
	       double a116 = bg116.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates113 = numberFormat.format(a113* 100);
	       	String rates114 = numberFormat.format(a114* 100);
	       	String rates115 = numberFormat.format(a115* 100);
	       	String rates116 = numberFormat.format(a116* 100);
		   FinancialPositionDTO line29 = new FinancialPositionDTO("","总资产周转率(%)",obtainStr(rates113),obtainStr(rates114),obtainStr(rates115),obtainStr(rates116));
		   
		   BigDecimal bg117 = new BigDecimal(stock1);
	       double a117 = bg117.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg118 = new BigDecimal(stock2);
	       double a118 = bg118.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg119 = new BigDecimal(stock3);
	       double a119 = bg119.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg120 = new BigDecimal(stock4);
	       double a120 = bg120.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates117 = numberFormat.format(a117* 100);
	       	String rates118 = numberFormat.format(a118* 100);
	       	String rates119 = numberFormat.format(a119* 100);
	       	String rates120 = numberFormat.format(a120* 100);
		   FinancialPositionDTO line30 = new FinancialPositionDTO("","存货周转率(%)",obtainStr(rates117),obtainStr(rates118),obtainStr(rates119),obtainStr(rates120));
		   
		   BigDecimal bg121 = new BigDecimal(averageProperty7);
	       double a121 = bg121.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg122 = new BigDecimal(averageProperty77);
	       double a122 = bg122.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg123 = new BigDecimal(averageProperty777);
	       double a123 = bg123.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg124 = new BigDecimal(averageProperty7777);
	       double a124 = bg124.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates121 = numberFormat.format(a121* 100);
	       	String rates122 = numberFormat.format(a122* 100);
	       	String rates123 = numberFormat.format(a123* 100);
	       	String rates124 = numberFormat.format(a124* 100);
		   FinancialPositionDTO line31 = new FinancialPositionDTO("","应收账款周转率(%)",obtainStr(rates121),obtainStr(rates122),obtainStr(rates123),obtainStr(rates124));

		   BigDecimal bg125 = new BigDecimal(purifyStock1);
	       double a125 = bg125.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg126 = new BigDecimal(purifyStock2);
	       double a126 = bg126.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg127 = new BigDecimal(purifyStock3);
	       double a127 = bg127.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg128 = new BigDecimal(purifyStock4);
	       double a128 = bg128.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates125 = numberFormat.format(a125* 100);
	       	String rates126 = numberFormat.format(a126* 100);
	       	String rates127 = numberFormat.format(a127* 100);
	       	String rates128 = numberFormat.format(a128* 100);
		   FinancialPositionDTO line32 = new FinancialPositionDTO("","净资产周转率(%)",obtainStr(rates125),obtainStr(rates126),obtainStr(rates127),obtainStr(rates128));

			List<FinancialPositionDTO> financialPositionDTOList = new ArrayList<FinancialPositionDTO>();

			financialPositionDTOList.add(line1);
			financialPositionDTOList.add(line2);
			financialPositionDTOList.add(line3);
			financialPositionDTOList.add(line4);
			financialPositionDTOList.add(line5);
			financialPositionDTOList.add(line6);
			financialPositionDTOList.add(line7);
			financialPositionDTOList.add(line8);
			financialPositionDTOList.add(line9);
			financialPositionDTOList.add(line10);
			financialPositionDTOList.add(line11);
			financialPositionDTOList.add(line12);
			financialPositionDTOList.add(line13);
			financialPositionDTOList.add(line14);
			financialPositionDTOList.add(line15);
			financialPositionDTOList.add(line16);
			financialPositionDTOList.add(line17);
			financialPositionDTOList.add(line18);
			financialPositionDTOList.add(line19);
			financialPositionDTOList.add(line20);
			financialPositionDTOList.add(line21);
			financialPositionDTOList.add(line22);
			financialPositionDTOList.add(line23);
			financialPositionDTOList.add(line24);
			financialPositionDTOList.add(line25);
			financialPositionDTOList.add(line26);
			financialPositionDTOList.add(line27);
			financialPositionDTOList.add(line28);
			financialPositionDTOList.add(line29);
			financialPositionDTOList.add(line30);
			financialPositionDTOList.add(line31);
			financialPositionDTOList.add(line32);

			outputJson(financialPositionDTOList, response);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}



	private String obtainPne(String str){
		if("0".equals(str)){
			return "";
		}else{
			return "<p align='center'>"+str+"</p>";
		}
	}
	
	private String obtainStr(String str){
		if("0".equals(str)){
			return "";
		}else{
			return "<p align='right'>"+str+".00"+"</p>";
		}
	}
	

	
	private String obtainPne1(String str){
		if("0".equals(str)){
			return "";
		}else{
			return str;
		}
	}
	
	private String obtainStr1(String str){
		if("0".equals(str)){
			return "";
		}else{
			return str;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


	/**
	 * 
	 * @Description: 填写企业财务状况
	 * @return 填写企业财务状况页面
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	@RequestMapping(value = "/excelComFinancialStatus")
	@ResponseBody
	public void excelComFinancialStatus(HttpServletRequest request,
			HttpServletResponse response,
			String indexName,
			@RequestParam(value = "comId", required = false) Integer comId,
			@RequestParam(value = "acctId", required = false) Integer acctId,
			ModelMap modeld) throws Exception {
		   modeld.put("acctId", acctId);
		   modeld.put("comId", comId);



		   BaseClientFactory clientFactory = getFactory(serviceModuel, "CusComCalculateService");
			BaseClientFactory c = getFactory(BusinessModule.MODUEL_SYSTEM, "TemplateFileService");
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				TemplateFileService.Client cl = (TemplateFileService.Client) c.getClient();
				CusComCalculateService.Client client = (CusComCalculateService.Client) clientFactory.getClient();

				Calendar rightNow = Calendar.getInstance();
				int year11 = rightNow.get(Calendar.YEAR);//当前年度
				int year22 = year11-1;//前一年年度
				int year33 = year11-2;//前两年年度
				int year44 = year11-3;//前三年年度



				
				String year1 =  Integer.toString(year11);
				String year2 =  Integer.toString(year22);
				String year3 =  Integer.toString(year33);
				String year4 =  Integer.toString(year44);
				
				String result_str1 = client.obtainPIDForMaxMonth(comId,year11);
				String result_str2 = client.obtainPIDForMaxMonth(comId,year22);
				String result_str3 = client.obtainPIDForMaxMonth(comId,year33); 
				String result_str4 = client.obtainPIDForMaxMonth(comId,year44);
				
				String[] strArray1 = result_str1.split(":");
				String[] strArray2 = result_str2.split(":");
				String[] strArray3 = result_str3.split(":");
				String[] strArray4 = result_str4.split(":");
				
				int reportId1 = Integer.parseInt(strArray1[0]);
				int reportId2 = Integer.parseInt(strArray2[0]);
				int reportId3 = Integer.parseInt(strArray3[0]);
				int reportId4 = Integer.parseInt(strArray4[0]);
				
				String month1 = "-1".equals(strArray1[1])?"":"13".equals(strArray1[1])?"年度":strArray1[1]+"月";
				String month2 = "-1".equals(strArray2[1])?"":"13".equals(strArray2[1])?"年度":strArray2[1]+"月";
				String month3 = "-1".equals(strArray3[1])?"":"13".equals(strArray3[1])?"年度":strArray3[1]+"月";
				String month4 = "-1".equals(strArray4[1])?"":"13".equals(strArray4[1])?"年度":strArray4[1]+"月";
				
				year1 = "".equals(month1)?year1+"年无":year1 + "年" + month1;
				year2 = "".equals(month2)?year2+"年无":year2 + "年" + month2;
				year3 = "".equals(month3)?year3+"年无":year3 + "年" + month3;
				year4 = "".equals(month4)?year4+"年无":year4 + "年" + month4;

				
				//取资产负债左表
				Map<Integer,Double> leftResultLeft1 = client.obtainLeftCusComBSLCalculatesMap(reportId1); 
				Map<Integer,Double> leftResultLeft2 = client.obtainLeftCusComBSLCalculatesMap(reportId2);
				Map<Integer,Double> leftResultLeft3 = client.obtainLeftCusComBSLCalculatesMap(reportId3);
				Map<Integer,Double> leftResultLeft4 = client.obtainLeftCusComBSLCalculatesMap(reportId4);

				//取资产负债右表
				Map<Integer,Double> RightResultRight1 = client.obtainRightCusComBSLCalculatesMap(reportId1);
				Map<Integer,Double> RightResultRight2 = client.obtainRightCusComBSLCalculatesMap(reportId2);
				Map<Integer,Double> RightResultRight3 = client.obtainRightCusComBSLCalculatesMap(reportId3);
				Map<Integer,Double> RightResultRight4 = client.obtainRightCusComBSLCalculatesMap(reportId4);

				//取利润表
				Map<Integer,Double> profitReport1 = client.obtainIncomeReportCalculateMap(reportId1);
				Map<Integer,Double> profitReport2 = client.obtainIncomeReportCalculateMap(reportId2);
				Map<Integer,Double> profitReport3 = client.obtainIncomeReportCalculateMap(reportId3);
				Map<Integer,Double> profitReport4 = client.obtainIncomeReportCalculateMap(reportId4);
				
				//拿产负债左表年初数
				List<CusComBalanceSheetCalculateDTO> leftBSL1 = client.obtainLeftCusComBSLCalculatesByReportId(reportId1);
				List<CusComBalanceSheetCalculateDTO> leftBSL2 = client.obtainLeftCusComBSLCalculatesByReportId(reportId2);
				List<CusComBalanceSheetCalculateDTO> leftBSL3 = client.obtainLeftCusComBSLCalculatesByReportId(reportId3);
				List<CusComBalanceSheetCalculateDTO> leftBSL4 = client.obtainLeftCusComBSLCalculatesByReportId(reportId4);
				
				double averageProperty1 = 0;
				double averageProperty2 = 0;
				double averageProperty7 = 0;
				double constructFundTwo1 = 0;
				double beginVal11 = 0;
				double endVal11 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL1) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						beginVal11 = cusComBalanceSheetCalculateDTO.getBeginVal();
						endVal11 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty1 = (beginVal11 + endVal11) / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty2 = (beginVal + endVal) / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty7 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo1 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty11 = 0;
				double averageProperty22 = 0;
				double averageProperty77 = 0;
				double endValTk2 = 0;
				double beginValTk2 = 0;
				double constructFundTwo2 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL2) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
 					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						endValTk2 = cusComBalanceSheetCalculateDTO.getEndVal();
						beginValTk2 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty11 = (beginVal + endVal) / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty22 = (beginVal + endVal) / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty77 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo2 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}

				double averageProperty111 = 0;
				double averageProperty222 = 0;
				double averageProperty777 = 0;
				double endValTk3 = 0;
				double beginValTk3 = 0;
				double constructFundTwo3 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL3) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						endValTk3 = cusComBalanceSheetCalculateDTO.getEndVal();
						beginValTk3 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty111 = (beginVal + endVal) / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty222 = (beginVal + endVal) / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty777 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo3 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty1111 = 0;
				double averageProperty2222 = 0;
				double averageProperty7777 = 0;
				double endValTk4 = 0;
				double beginValTk4 = 0;
				double constructFundTwo4 = 0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : leftBSL4) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum2 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum7 = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum15 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==37){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						endValTk4 = cusComBalanceSheetCalculateDTO.getEndVal();
						beginValTk4 = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty1111 = beginVal + endVal / 2;
					}if(lineNum2==11){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty2222 = beginVal + endVal / 2;
					}if(lineNum7==7){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty7777 = (beginVal + endVal) / 2;
					}if(lineNum15==15){
						constructFundTwo4 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				//拿产负债右表年初数
				List<CusComBalanceSheetCalculateDTO> rightBSL1 = client.obtainRightCusComBSLCalculatesByReportId(reportId1);
				List<CusComBalanceSheetCalculateDTO> rightBSL2 = client.obtainRightCusComBSLCalculatesByReportId(reportId2);
				List<CusComBalanceSheetCalculateDTO> rightBSL3 = client.obtainRightCusComBSLCalculatesByReportId(reportId3);
				List<CusComBalanceSheetCalculateDTO> rightBSL4 = client.obtainRightCusComBSLCalculatesByReportId(reportId4);

				double averageProperty101 =0;
				double averageProperty102 =0;
				double constructFundTwo11 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL1) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty101 = beginVal11 - beginVal;
						averageProperty102 = endVal11 - endVal;
					}if(lineNum16==16){
						constructFundTwo11 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty1011 =0;
				double averageProperty1022 =0;
				double constructFundTwo22 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL2) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty1011 = beginValTk2 - beginVal;
						averageProperty1022 = endValTk2 - endVal;
					}if(lineNum16==16){
						constructFundTwo22 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty10111 =0;
				double averageProperty10222 =0;
				double constructFundTwo33 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL3) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty10111 = beginValTk3 - beginVal;
						averageProperty10222 = endValTk3 - endVal;
					}if(lineNum16==16){
						constructFundTwo33 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}
				
				double averageProperty101111 =0;
				double averageProperty102222 =0;
				double constructFundTwo44 =0;
				for (CusComBalanceSheetCalculateDTO cusComBalanceSheetCalculateDTO : rightBSL4) {
					int lineNum = cusComBalanceSheetCalculateDTO.getLineNum();
					int lineNum16 = cusComBalanceSheetCalculateDTO.getLineNum();
					if(lineNum==26){
						double beginVal = cusComBalanceSheetCalculateDTO.getBeginVal();
						double endVal = cusComBalanceSheetCalculateDTO.getEndVal();
						averageProperty101111 = beginValTk4 - beginVal;
						averageProperty102222 = endValTk4 - endVal;
					}if(lineNum16==16){
						constructFundTwo44 = cusComBalanceSheetCalculateDTO.getEndVal();
					}
				}

				//1.总资产：资产负债表的资产总计；
				double result1 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if(i==37){
						result1 = result1 + leftResultLeft1.get(i);
					}
				}
				double result2 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if(i==37){
						result2 = result2 + leftResultLeft2.get(i);
					}
				}
				double result3 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if(i==37){
						result3 = result3 + leftResultLeft3.get(i);
					}
				}
				double result4 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if(i==37){
						result4 = result4 + leftResultLeft4.get(i);
					}
				}

				//2.净资产：总资产-负债总计
				double purgPproperty1 = 0;
				double purgPproperty2 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==37) {
						purgPproperty1 = purgPproperty1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==26) {
						purgPproperty2 = purgPproperty2 + RightResultRight1.get(j);
					}
				}
				double purgPproperty3 = purgPproperty1 - purgPproperty2;

				double purgPproperty11 = 0;
				double purgPproperty22 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==37) {
						purgPproperty11 = purgPproperty11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==26) {
						purgPproperty22 = purgPproperty22 + RightResultRight2.get(j);
					}
				}
				double purgPproperty33 = purgPproperty11 - purgPproperty22;

				double purgPproperty111 = 0;
				double purgPproperty222 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==37) {
						purgPproperty111 = purgPproperty111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==26) {
						purgPproperty222 = purgPproperty222 + RightResultRight3.get(j);
					}
				}
				double purgPproperty333 = purgPproperty111 - purgPproperty222;

				double purgPproperty1111 = 0;
				double purgPproperty2222 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==37) {
						purgPproperty1111 = purgPproperty1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==26) {
						purgPproperty2222 = purgPproperty2222 + RightResultRight4.get(j);
					}
				}
				double purgPproperty3333 = purgPproperty1111 - purgPproperty2222;

				//3.流动资产：资产负债表的流动资产合计
				double flowPproperty1 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==15) {
						flowPproperty1 = flowPproperty1 + leftResultLeft1.get(i);
					}
				}
				double flowPproperty2 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==15) {
						flowPproperty2 = flowPproperty2 + leftResultLeft2.get(i);
					}
				}
				double flowPproperty3 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==15) {
						flowPproperty3 = flowPproperty3 + leftResultLeft3.get(i);
					}
				}
				double flowPproperty4 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==15) {
						flowPproperty4 = flowPproperty4 + leftResultLeft4.get(i);
					}
				}
				//4.流动负债：资产负债表的流动负债合计；
				double flowBurden1 = 0;
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==16) {
						flowBurden1 = flowBurden1 + RightResultRight1.get(j);
					}
				}
				double flowBurden2 = 0;
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==16) {
						flowBurden2 = flowBurden2 + RightResultRight2.get(j);
					}
				}
				double flowBurden3 = 0;
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==16) {
						flowBurden3 = flowBurden3 + RightResultRight3.get(j);
					}
				}
				double flowBurden4 = 0;
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==16) {
						flowBurden4 = flowBurden4 + RightResultRight4.get(j);
					}
				}

				//5.营运资金：流动资产-流动负债；
				double constructFund1 = 0;
				double constructFund2 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==15) {
						constructFund1 = constructFund1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==16) {
						constructFund2 = constructFund2 + RightResultRight1.get(j);
					}
				}
				double constructFund3 = constructFund1 - constructFund2;

				double constructFund11 = 0;
				double constructFund22 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==15) {
						constructFund11 = constructFund11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==16) {
						constructFund22 = constructFund22 + RightResultRight2.get(j);
					}
				}
				double constructFund33 = constructFund11 - constructFund22;
				
				double constructFund111 = 0;
				double constructFund222 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==15) {
						constructFund111 = constructFund111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==16) {
						constructFund222 = constructFund222 + RightResultRight3.get(j);
					}
				}
				double constructFund333 = constructFund111 - constructFund222;
				
				double constructFund1111 = 0;
				double constructFund2222 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==15) {
						constructFund1111 = constructFund1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==16) {
						constructFund2222 = constructFund2222 + RightResultRight4.get(j);
					}
				}
				double constructFund3333 = constructFund1111 - constructFund2222;


				//6.流动比率：流动资产/流动负债*100%
				double constructFundTwo301 = 0;
				if( constructFundTwo11==0){
				}else{
					constructFundTwo301 = constructFundTwo1 / constructFundTwo11 * 100;
				}
				double constructFundTwo302 = 0;
				if( constructFundTwo22==0){
				}else{
					constructFundTwo302 = constructFundTwo2 / constructFundTwo22 * 100;
				}
				double constructFundTwo303 = 0;
				if(constructFundTwo33==0){
				}else{
					constructFundTwo303 = constructFundTwo3 / constructFundTwo33 * 100;
				}
				double constructFundTwo304 = 0;
				if(constructFundTwo44==0){
				}else{
					constructFundTwo304 = constructFundTwo4 / constructFundTwo44 * 100;
				}
				
				//7.速动比率：（流动资产-存货）/流动负债*100%   存货：资产负债表的存货；
				double velocityRatio1 = 0;
				double velocityRatio2 = 0;
				double velocityRatio3 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==15) {
						velocityRatio1 = velocityRatio1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft1.size(); j++) {
					if (j==11) {
						velocityRatio2 = velocityRatio2 + leftResultLeft1.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight1.size(); y++) {
					if (y==16) {
						velocityRatio3 = velocityRatio3 + RightResultRight1.get(y);
					}
				}
				
				double velocityRatio4 = 0;
				if(velocityRatio3==0){
				}else{
					velocityRatio4 = (velocityRatio1 - velocityRatio2) / velocityRatio3 * 100;
				}
				
				double velocityRatio11 = 0;
				double velocityRatio22 = 0;
				double velocityRatio33 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==15) {
						velocityRatio11 = velocityRatio11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft2.size(); j++) {
					if (j==11) {
						velocityRatio22 = velocityRatio22 + leftResultLeft2.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight2.size(); y++) {
					if (y==16) {
						velocityRatio33 = velocityRatio33 + RightResultRight2.get(y);
					}
				}
				double velocityRatio44 = 0;
				if(velocityRatio33==0){
				}else{
					velocityRatio44 = (velocityRatio11 - velocityRatio22) / velocityRatio33 * 100;
				}
				
				double velocityRatio111 = 0;
				double velocityRatio222 = 0;
				double velocityRatio333 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==15) {
						velocityRatio111 = velocityRatio111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft3.size(); j++) {
					if (j==11) {
						velocityRatio222 = velocityRatio222 + leftResultLeft3.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight3.size(); y++) {
					if (y==16) {
						velocityRatio333 = velocityRatio333 + RightResultRight3.get(y);
					}
				}
				double velocityRatio444 = 0;
				if(velocityRatio333==0){
				}else{
					velocityRatio444 =  (velocityRatio111 - velocityRatio222) / velocityRatio333 * 100;
				}
				
				double velocityRatio1111 = 0;
				double velocityRatio2222 = 0;
				double velocityRatio3333 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==15) {
						velocityRatio1111 = velocityRatio1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= leftResultLeft4.size(); j++) {
					if (j==11) {
						velocityRatio2222 = velocityRatio2222 + leftResultLeft4.get(j);
					}
				}
				for (int y = 1; y <= RightResultRight4.size(); y++) {
					if (y==16) {
						velocityRatio3333 = velocityRatio3333 + RightResultRight4.get(y);
					}
				}
				double velocityRatio4444 = 0;
				if(velocityRatio3333==0){
				}else{
					velocityRatio444 =  (velocityRatio1111 - velocityRatio2222) / velocityRatio3333 * 100;
				}
				
				
				//8.	资产与负债率：负债合计/资产总计*100%   
				double propertyBearRate1 = 0;
				double propertyBearRate2 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if (i==37) {
						propertyBearRate1 = propertyBearRate1 + leftResultLeft1.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight1.size(); j++) {
					if (j==26) {
						propertyBearRate2 = propertyBearRate2 + RightResultRight1.get(j);
					}
				}
				
				double propertyBearRate3 = 0;
				if(propertyBearRate1==0){
				}else{
					propertyBearRate3 =  propertyBearRate2 / propertyBearRate1 * 100;
				}

				double propertyBearRate11 = 0;
				double propertyBearRate22 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if (i==37) {
						propertyBearRate11 = propertyBearRate11 + leftResultLeft2.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight2.size(); j++) {
					if (j==26) {
						propertyBearRate22 = propertyBearRate22 + RightResultRight2.get(j);
					}
				}
				double propertyBearRate33 = 0;
				if(propertyBearRate11==0){
				}else{
					propertyBearRate33 =  propertyBearRate22 / propertyBearRate11 * 100;
				}

				
				double propertyBearRate111 = 0;
				double propertyBearRate222 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if (i==37) {
						propertyBearRate111 = propertyBearRate111 + leftResultLeft3.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight3.size(); j++) {
					if (j==26) {
						propertyBearRate222 = propertyBearRate222 + RightResultRight3.get(j);
					}
				}

				double propertyBearRate333 = 0;
				if(propertyBearRate111==0){
				}else{
					propertyBearRate333 = propertyBearRate222 / propertyBearRate111 * 100;
				}
				
				double propertyBearRate1111 = 0;
				double propertyBearRate2222 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if (i==37) {
						propertyBearRate1111 = propertyBearRate1111 + leftResultLeft4.get(i);
					}
				}
				for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==26) {
						propertyBearRate2222 = propertyBearRate2222 + RightResultRight4.get(j);
					}
				}

				double propertyBearRate3333 = 0;
				if(propertyBearRate1111==0){
				}else{
					propertyBearRate3333 = propertyBearRate2222 / propertyBearRate1111 * 100;
				}
				
				//9.	负债与所有者权益比例：负债合计/所有者权益合计；
				double weightScale1 = 0;
				if(RightResultRight1.size()>0){
					if(RightResultRight1.get(36)==0){
					}else{
						weightScale1 = RightResultRight1.get(26) / RightResultRight1.get(36);
					}
				}

				double weightScale2 = 0;
				if(RightResultRight2.size()>0){
					if(RightResultRight2.get(36)==0){
					}else{
						weightScale2 = RightResultRight2.get(26) / RightResultRight2.get(36);
					}
				}
					

				double weightScale3 = 0;
				if(RightResultRight3.size()>0){
					if(RightResultRight3.get(36)==0){
					}else{
						weightScale3 = RightResultRight3.get(26) / RightResultRight3.get(36);
					}
				}
				
				double weightScale4 = 0;
				/*for (int j = 1; j <= RightResultRight4.size(); j++) {
					if (j==26 || j==36) {*/
					if(RightResultRight4.size()>0){ 
						if(RightResultRight4.get(36)==0){
						}else{
							weightScale4 = RightResultRight4.get(26) / RightResultRight4.get(36);
						}
					}
					/*}
				}*/
				
				//10.	利息保障倍数：（企业的净利润(净利润)+企业支付的利息费用(财务费用)+企业支付的所得税）/利息费用	
				double accrualEnsure1 = 0;
				double accrualEnsure2 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure1 = accrualEnsure1 + profitReport1.get(l);
					}
				}
				for (int k = 1; k <= profitReport1.size(); k++) {
					if (k==8) {
						accrualEnsure2 = accrualEnsure2 + profitReport1.get(k);
					}
				}
				double accrualEnsure3 = 0;
				if(accrualEnsure2==0){
				}else{
					 accrualEnsure3 = accrualEnsure1 / accrualEnsure2;
				}
			
				double accrualEnsure11 = 0;
				double accrualEnsure22 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure11 = accrualEnsure11 + profitReport2.get(l);
					}
				}
				for (int k = 1; k <= profitReport2.size(); k++) {
					if (k==8) {
						accrualEnsure22 = accrualEnsure22 + profitReport2.get(k);
					}
				}
				double accrualEnsure33 = 0;
				if(accrualEnsure22==0){
				}else{
					 accrualEnsure33 = accrualEnsure11 / accrualEnsure22;
				}
				
				double accrualEnsure111 = 0;
				double accrualEnsure222 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure111 = accrualEnsure111 + profitReport3.get(l);
					}
				}
				for (int k = 1; k <= profitReport3.size(); k++) {
					if (k==8) {
						accrualEnsure222 = accrualEnsure222 + profitReport3.get(k);
					}
				}
				double accrualEnsure333 = 0;
				if(accrualEnsure222==0){
				}else{
					accrualEnsure333 = accrualEnsure111 / accrualEnsure222;
				}
				
				double accrualEnsure1111 = 0;
				double accrualEnsure2222 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==18 || l==8 || l==16) {
						accrualEnsure1111 = accrualEnsure1111 + profitReport4.get(l);
					}
				}
				for (int k = 1; k <= profitReport3.size(); k++) {
					if (k==8) {
						accrualEnsure2222 = accrualEnsure2222 + profitReport4.get(k);
					}
				}
				double accrualEnsure3333 = 0;
				if(accrualEnsure2222==0){
				}else{
					accrualEnsure3333 = accrualEnsure1111 / accrualEnsure2222;
				}
				
				//11.主营业务收入：取自利润表；
				double mainBusiness1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==1) {
						mainBusiness1 = mainBusiness1 + profitReport1.get(l);
					}
				}
				double mainBusiness2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==1) {
						mainBusiness2 = mainBusiness2 + profitReport2.get(l);
					}
				}
				double mainBusiness3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==1) {
						mainBusiness3 = mainBusiness3 + profitReport3.get(l);
					}
				}
				double mainBusiness4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==1) {
						mainBusiness4 = mainBusiness4 + profitReport4.get(l);
					}
				}
				
				//12.主营业务成本：取自利润表；
				double mainBusinessCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==2) {
						mainBusinessCost1 = mainBusinessCost1 + profitReport1.get(l);
					}
				}
				double mainBusinessCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==2) {
						mainBusinessCost2 = mainBusinessCost2 + profitReport2.get(l);
					}
				}
				double mainBusinessCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==2) {
						mainBusinessCost3 = mainBusinessCost3 + profitReport3.get(l);
					}
				}
				double mainBusinessCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==2) {
						mainBusinessCost4 = mainBusinessCost4 + profitReport4.get(l);
					}
				}
				//13.营业费用：取自利润表；
				double BusinessCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==6) {
						BusinessCost1 = BusinessCost1 + profitReport1.get(l);
					}
				}
				double BusinessCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==6) {
						BusinessCost2 = BusinessCost2 + profitReport2.get(l);
					}
				}
				double BusinessCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==6) {
						BusinessCost3 = BusinessCost3 + profitReport3.get(l);
					}
				}
				double BusinessCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==6) {
						BusinessCost4 = BusinessCost4 + profitReport4.get(l);
					}
				}
				//14.管理费用：取自利润表；
				double superviseCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==7) {
						superviseCost1 = superviseCost1 + profitReport1.get(l);
					}
				}
				double superviseCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==7) {
						superviseCost2 = superviseCost2 + profitReport2.get(l);
					}
				}
				double superviseCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==7) {
						superviseCost3 = superviseCost3 + profitReport3.get(l);
					}
				}
				double superviseCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==7) {
						superviseCost4 = superviseCost4 + profitReport4.get(l);
					}
				}
				//15.财务费用：取自利润表；
				double financeCost1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==8) {
						financeCost1 = financeCost1 + profitReport1.get(l);
					}
				}
				double financeCost2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==8) {
						financeCost2 = financeCost2 + profitReport2.get(l);
					}
				}
				double financeCost3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==8) {
						financeCost3 = financeCost3 + profitReport3.get(l);
					}
				}
				double financeCost4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==8) {
						financeCost4 = financeCost4 + profitReport4.get(l);
					}
				}
				//16.营业利润：取自利润表；
				double businessProfit1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==9) {
						businessProfit1 = businessProfit1 + profitReport1.get(l);
					}
				}
				double businessProfit2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==9) {
						businessProfit2 = businessProfit2 + profitReport2.get(l);
					}
				}
				double businessProfit3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==9) {
						businessProfit3 = businessProfit3 + profitReport3.get(l);
					}
				}
				double businessProfit4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==9) {
						businessProfit4 = businessProfit4 + profitReport4.get(l);
					}
				}
				//17.利润总额：取自利润表；
				double profitCount1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==15) {
						profitCount1 = profitCount1 + profitReport1.get(l);
					}
				}
				double profitCount2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==15) {
						profitCount2 = profitCount2 + profitReport2.get(l);
					}
				}
				double profitCount3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==15) {
						profitCount3 = profitCount3 + profitReport3.get(l);
					}
				}
				double profitCount4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==15) {
						profitCount4 = profitCount4 + profitReport4.get(l);
					}
				}
				
				//18.净利润：取自利润表
				double purifyProfit1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==18) {
						purifyProfit1 = purifyProfit1 + profitReport1.get(l);
					}
				}
				double purifyProfit2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==18) {
						purifyProfit2 = purifyProfit2 + profitReport2.get(l);
					}
				}
				double purifyProfit3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==18) {
						purifyProfit3 = purifyProfit3 + profitReport3.get(l);
					}
				}
				double purifyProfit4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==18) {
						purifyProfit4 = purifyProfit4 + profitReport4.get(l);
					}
				}
				
				//19.毛利率：（主营业务收入mainBusiness1-主营业务成本mainBusinessCost1）/主营业务收入mainBusiness1*100%； 
				double hairProfit1 = 0;
				if(mainBusiness1==0){
				}else{
					 hairProfit1 = (mainBusiness1 - mainBusinessCost1) / mainBusiness1 * 100;
				}
				
				double hairProfit2 = 0;
				if(mainBusiness2==0){
				}else{
					hairProfit2 = (mainBusiness2 - mainBusinessCost2) / mainBusiness2 * 100;
				}
				
				double hairProfit3 = 0;
				if(mainBusiness3==0){
				}else{
					hairProfit3 = (mainBusiness3 - mainBusinessCost3) / mainBusiness3 * 100;
				}
				
				double hairProfit4 = 0;
				if(mainBusiness4==0){
				}else{
					 hairProfit4 = (mainBusiness4 - mainBusinessCost4) / mainBusiness4 * 100;
				}

				//20.营业利润率：营业利润businessProfit1 /主营业务收入mainBusiness1 *100%；
				double businessProfitRate1 = 0;
				if(mainBusiness1==0){
				}else{
					businessProfitRate1 = businessProfit1 / mainBusiness1 * 100;
				}
				
				double businessProfitRate2 = 0;
				if(mainBusiness2==0){
				}else{
					businessProfitRate2 = businessProfit2 / mainBusiness2 * 100;
				}
				
				double businessProfitRate3 = 0;
				if(mainBusiness3==0){
				}else{
					businessProfitRate3 = businessProfit3 / mainBusiness3 * 100;
				}
				
				double businessProfitRate4 = 0;
				if(mainBusiness4==0){
				}else{
					businessProfitRate4 = businessProfit4 / mainBusiness4 * 100;
				}
				

				//21.销售利润率：利润总额profitCount1/主营业务收入mainBusiness1*100%；
				double sellProfitRate1 = 0;
				if(mainBusiness1==0){
				}else{
					sellProfitRate1 = profitCount1 / mainBusiness1 * 100;
				}
				
				double sellProfitRate2 = 0;
				if(mainBusiness2==0){
				}else{
					sellProfitRate2 = profitCount2 / mainBusiness2 * 100;
				}
				
				double sellProfitRate3 = 0;
				if( mainBusiness3==0){
				}else{
					sellProfitRate3 = profitCount3 / mainBusiness3 * 100;
				}
				
				double sellProfitRate4 = 0;
				if( mainBusiness4==0){
				}else{
					sellProfitRate4 = profitCount4 / mainBusiness4 * 100;
				}
				

				//22.净利润率：净利润purifyProfit1/主营业务收入mainBusiness1*100%；
				double purifyProfitRate1 = 0;
				if(mainBusiness1==0){
				}else{
					purifyProfitRate1 = purifyProfit1 / mainBusiness1 * 100;
				}
				
				double purifyProfitRate2 = 0;
				if(mainBusiness2==0){
				}else{
					purifyProfitRate2 = purifyProfit2 / mainBusiness2 * 100;
				}
				
				double purifyProfitRate3 = 0;
				if( mainBusiness3==0){
				}else{
					purifyProfitRate3 = purifyProfit3 / mainBusiness3 * 100;
				}
				
				double purifyProfitRate4 = 0;
				if(mainBusiness4==0){
				}else{
					purifyProfitRate4 = purifyProfit4 / mainBusiness4 * 100;
				}
				
				
				//23.净资产收益率：税后利润/所有者权益；
				double propertyIncomeRate1 = 0;
				double propertyIncomeRate11 = 0;
				for (int j = 1; j <= profitReport1.size(); j++) {
					if (j==16) {
						propertyIncomeRate1 = propertyIncomeRate1 + profitReport1.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight1.size(); r++) {
					if (r==36) {
						propertyIncomeRate11 = propertyIncomeRate11 + RightResultRight1.get(r);
					}
				}
				
				double propertyIncomeRate2 = 0;
				double propertyIncomeRate22 = 0;
				for (int j = 1; j <= profitReport2.size(); j++) {
					if (j==16) {
						propertyIncomeRate2 = propertyIncomeRate2 + profitReport2.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight2.size(); r++) {
					if (r==36) {
						propertyIncomeRate22 = propertyIncomeRate22 + RightResultRight2.get(r);
					}
				}
				
				double propertyIncomeRate3 = 0;
				double propertyIncomeRate33 = 0;
				for (int j = 1; j <= profitReport3.size(); j++) {
					if (j==16) {
						propertyIncomeRate3 = propertyIncomeRate3 + profitReport3.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight3.size(); r++) {
					if (r==36) {
						propertyIncomeRate33 = propertyIncomeRate33 + RightResultRight3.get(r);
					}
				}
				
				double propertyIncomeRate4 = 0;
				double propertyIncomeRate44 = 0;
				for (int j = 1; j <= profitReport4.size(); j++) {
					if (j==16) {
						propertyIncomeRate4 = propertyIncomeRate4 + profitReport4.get(j);
					}
				}
				for (int r = 1; r <= RightResultRight4.size(); r++) {
					if (r==36) {
						propertyIncomeRate44 = propertyIncomeRate44 +RightResultRight4.get(r);
					}
				}
				
				double propertyIncomeRate101 = 0;
				if( propertyIncomeRate11==0){
				}else{
					propertyIncomeRate101 = propertyIncomeRate1 / propertyIncomeRate11;
				}
				
				double propertyIncomeRate102 = 0;
				if(propertyIncomeRate22==0){
				}else{
					propertyIncomeRate102 = propertyIncomeRate2 / propertyIncomeRate22;
				}
				
				double propertyIncomeRate103 = 0;
				if(propertyIncomeRate33==0){
				}else{
					propertyIncomeRate103 = propertyIncomeRate3 / propertyIncomeRate33;
				}
				
				double propertyIncomeRate104 = 0;
				if( propertyIncomeRate44==0){
				}else{
					propertyIncomeRate104 = propertyIncomeRate4 / propertyIncomeRate44;
				}
				
				
				//24.成本费用利润率：利润总额profitCount1/成本费用总额*100%；
				double primeCostProfit1 = 0;
				for (int l = 1; l <= profitReport1.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit1 = primeCostProfit1 + profitReport1.get(l);
					}
				}
				double primeCostProfit2 = 0;
				for (int l = 1; l <= profitReport2.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit2 = primeCostProfit2 + profitReport2.get(l);
					}
				}
				double primeCostProfit3 = 0;
				for (int l = 1; l <= profitReport3.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit3 = primeCostProfit3 + profitReport3.get(l);
					}
				}
				double primeCostProfit4 = 0;
				for (int l = 1; l <= profitReport4.size(); l++) {
					if (l==2 || l==3 || l==6 || l==7 || l==8) {
						primeCostProfit4 = primeCostProfit4 + profitReport4.get(l);
					}
				}
				
				double primeCostProfit101 = 0;
				if(primeCostProfit1==0){
				}else{
					primeCostProfit101 = profitCount1 / primeCostProfit1 * 100;
				}
				
				double primeCostProfit102 = 0;
				if(primeCostProfit2==0){
				}else{
					primeCostProfit102 = profitCount2 / primeCostProfit2 * 100;
				}
				
				double primeCostProfit103 = 0;
				if(primeCostProfit3==0){
				}else{
					primeCostProfit103 = profitCount3 / primeCostProfit3 * 100;
				}
				
				double primeCostProfit104 = 0;
				if(primeCostProfit4==0){
				}else{
					 primeCostProfit104 = profitCount4 / primeCostProfit4 * 100;
				}
				

				//25.	总资产报酬率：（利润总额profitCount1+利息支出financeCost1）/平均资产总额X100%
				double countProperty1 = 0;
				if(averageProperty1==0){
				}else{
					countProperty1 = (profitCount1 + financeCost1) / averageProperty1 * 100;
				}
				
				double countProperty2 = 0;
				if(averageProperty11==0){
				}else{
					countProperty2 = (profitCount2 + financeCost2) / averageProperty11 * 100;
				}
				
				double countProperty3 = 0;
				if(averageProperty111==0){
				}else{
					countProperty3 = (profitCount3 + financeCost3) / averageProperty111 * 100;
				}
				
				double countProperty4 = 0;
				if(averageProperty1111==0){
				}else{
					countProperty4 = (profitCount4 + financeCost4) / averageProperty1111 * 100;
				}
				
						
				//26.	存货：取自资产负债表； 已写入
				//27.	应收账款：取自资产负债表；
				double incomeCredit1 = 0;
				for (int i = 1; i <= leftResultLeft1.size(); i++) {
					if(i==7){
						incomeCredit1 = incomeCredit1 + leftResultLeft1.get(i);
					}
				}
				double incomeCredit2 = 0;
				for (int i = 1; i <= leftResultLeft2.size(); i++) {
					if(i==7){
						incomeCredit2 = incomeCredit2 + leftResultLeft2.get(i);
					}
				}
				double incomeCredit3 = 0;
				for (int i = 1; i <= leftResultLeft3.size(); i++) {
					if(i==7){
						incomeCredit3 = incomeCredit3 + leftResultLeft3.get(i);
					}
				}
				double incomeCredit4 = 0;
				for (int i = 1; i <= leftResultLeft4.size(); i++) {
					if(i==7){
						incomeCredit4 = incomeCredit4 + leftResultLeft4.get(i);
					}
				}

				//28.	总资产周转率：销售收入mainBusiness1/总资产purgPproperty1...；
				double turnover1 = 0;
				if( purgPproperty1==0){
				}else{
					turnover1 = mainBusiness1 / purgPproperty1;
				}
				
				double turnover2 = 0;
				if( purgPproperty11==0){
				}else{
					turnover2 = mainBusiness2 / purgPproperty11;
				}
				
				double turnover3 = 0;
				if( purgPproperty111==0){
				}else{
					turnover3 = mainBusiness3 / purgPproperty111;
				}
				
				double turnover4 = 0;
				if( purgPproperty1111==0){
				}else{
					turnover4 = mainBusiness4 / purgPproperty1111;
				}

				//29.存货周转率：营业收入mainBusiness1/存货平均余额averageProperty1
				double stock1 = 0;
				if( averageProperty2==0){
				}else{
					stock1 = mainBusiness1 / averageProperty2;
				}
				
				double stock2 =0;
				if(averageProperty22==0){
				}else{
					stock2 = mainBusiness2 / averageProperty22;
				}
				
				double stock3 = 0;
				if(averageProperty222==0){
				}else{
					stock3 = mainBusiness3 / averageProperty222;
				}
				
				double stock4 = 0;
				if(averageProperty2222==0){
				}else{
					stock4 = mainBusiness4 / averageProperty2222;
				}
				
				
				//30.应收账款周转率=（期初应收账款余额 + 期末应收账款余额）/ 2  以写入
				//31.净资产周转率=销售收入/『（期初净资产总额+期末净资产总额）/2』
				double purifyStock1 = 0;
				if((averageProperty101 + averageProperty102)==0){
				}else{
					purifyStock1 = mainBusiness1 / ((averageProperty101 + averageProperty102) / 2);
				}
				
				double purifyStock2 = 0;
				if((averageProperty1011 + averageProperty1022)==0){
				}else{
					purifyStock2 = mainBusiness2 / ((averageProperty1011 + averageProperty1022) / 2);
				}
				
				double purifyStock3 =0;
				if((averageProperty10111 + averageProperty10222) ==0){
				}else{
					 purifyStock3 = mainBusiness3 / ((averageProperty10111 + averageProperty10222) / 2);
				}
				
				double purifyStock4 = 0;
				if((averageProperty101111 + averageProperty102222)==0){
				}else{
					purifyStock4 = mainBusiness4 / ((averageProperty101111 + averageProperty102222) / 2);
				}

		   FinancialPositionDTO line1 = new FinancialPositionDTO("","报表期间",obtainPne1(year1),obtainPne1(year2),obtainPne1(year3),obtainPne1(year4));

			NumberFormat numberFormat = NumberFormat.getNumberInstance();

		   BigDecimal bg5 = new BigDecimal(result1);
	       double a5 = bg5.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg6 = new BigDecimal(result2);
	       double a6 = bg6.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg7 = new BigDecimal(result3);
	       double a7 = bg7.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg8 = new BigDecimal(result4);
	       double a8 = bg8.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates5 = numberFormat.format(a5);
	       	String rates6 = numberFormat.format(a6);
	       	String rates7 = numberFormat.format(a7);
	       	String rates8 = numberFormat.format(a8);
	       	
		   FinancialPositionDTO line2 = new FinancialPositionDTO("偿债能力：","总资产(万元)",obtainStr1(rates5),obtainStr1(rates6),obtainStr1(rates7),obtainStr1(rates8));

		   BigDecimal bg9 = new BigDecimal(purgPproperty3);
	       double a9 = bg9.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg10 = new BigDecimal(purgPproperty33);
	       double a10 = bg10.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg11 = new BigDecimal(purgPproperty333);
	       double a11 = bg11.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg12 = new BigDecimal(purgPproperty3333);
	       double a12 = bg12.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates9 = numberFormat.format(a9);
	       	String rates10 = numberFormat.format(a10);
	       	String rates11 = numberFormat.format(a11);
	       	String rates12 = numberFormat.format(a12);
	       	
		   FinancialPositionDTO line3 = new FinancialPositionDTO("","净资产(万元)",obtainStr1(rates9),obtainStr1(rates10),obtainStr1(rates11),obtainStr1(rates12));

		   BigDecimal bg13 = new BigDecimal(flowPproperty1);
	       double a13 = bg13.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg14 = new BigDecimal(flowPproperty2);
	       double a14 = bg14.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg15 = new BigDecimal(flowPproperty3);
	       double a15 = bg15.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg16 = new BigDecimal(flowPproperty4);
	       double a16 = bg16.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates13 = numberFormat.format(a13);
	       	String rates14 = numberFormat.format(a14);
	       	String rates15 = numberFormat.format(a15);
	       	String rates16 = numberFormat.format(a16);
	       	
		   FinancialPositionDTO line4 = new FinancialPositionDTO("","流动资产(万元)",obtainStr1(rates13),obtainStr1(rates14),obtainStr1(rates15),obtainStr1(rates16));
		   
		   BigDecimal bg17 = new BigDecimal(flowBurden1);
	       double a17 = bg17.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg18 = new BigDecimal(flowBurden2);
	       double a18 = bg18.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg19 = new BigDecimal(flowBurden3);
	       double a19 = bg19.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg20 = new BigDecimal(flowBurden4);
	       double a20 = bg20.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates17 = numberFormat.format(a17);
	       	String rates18 = numberFormat.format(a18);
	       	String rates19 = numberFormat.format(a19);
	       	String rates20 = numberFormat.format(a20);
	       	
		   FinancialPositionDTO line5 = new FinancialPositionDTO("","流动负债(万元)",obtainStr1(rates17),obtainStr1(rates18),obtainStr1(rates19),obtainStr1(rates20));
		   
		   BigDecimal bg21 = new BigDecimal(constructFund3);
	       double a21 = bg21.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg22 = new BigDecimal(constructFund33);
	       double a22 = bg22.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg23 = new BigDecimal(constructFund333);
	       double a23 = bg23.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg24 = new BigDecimal(constructFund3333);
	       double a24 = bg24.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates21 = numberFormat.format(a21);
	       	String rates22 = numberFormat.format(a22);
	       	String rates23 = numberFormat.format(a23);
	       	String rates24 = numberFormat.format(a24);
	       	
		   FinancialPositionDTO line6 = new FinancialPositionDTO("","营运资金(万元)",obtainStr1(rates21),obtainStr1(rates22),obtainStr1(rates23),obtainStr1(rates24));
		   
		   BigDecimal bg25 = new BigDecimal(constructFundTwo301);
	       double a25 = bg25.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg26 = new BigDecimal(constructFundTwo302);
	       double a26 = bg26.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg27 = new BigDecimal(constructFundTwo303);
	       double a27 = bg27.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg28 = new BigDecimal(constructFundTwo304);
	       double a28 = bg28.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates25 = numberFormat.format(a25* 100);
	       	String rates26 = numberFormat.format(a26* 100);
	       	String rates27 = numberFormat.format(a27* 100);
	       	String rates28 = numberFormat.format(a28* 100);
	       	
		   FinancialPositionDTO line7 = new FinancialPositionDTO("","流动比率(%)",obtainStr1(rates25),obtainStr1(rates26),obtainStr1(rates27),obtainStr1(rates28));
		 
		   BigDecimal bg29 = new BigDecimal(velocityRatio4);
	       double a29 = bg29.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg30 = new BigDecimal(velocityRatio44);
	       double a30 = bg30.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg31 = new BigDecimal(velocityRatio444);
	       double a31 = bg31.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg32 = new BigDecimal(velocityRatio4444);
	       double a32 = bg32.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates29 = numberFormat.format(a29* 100);
	       	String rates30 = numberFormat.format(a30* 100);
	       	String rates31 = numberFormat.format(a31* 100);
	       	String rates32 = numberFormat.format(a32* 100);
	       	
		   FinancialPositionDTO line8 = new FinancialPositionDTO("","速动比率(%)",obtainStr1(rates29),obtainStr1(rates30),obtainStr1(rates31),obtainStr1(rates32));
		   

		   BigDecimal bg33 = new BigDecimal(propertyBearRate3);
	       double a33 = bg33.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg34 = new BigDecimal(propertyBearRate33);
	       double a34 = bg34.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg35 = new BigDecimal(propertyBearRate333);
	       double a35 = bg35.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg36 = new BigDecimal(propertyBearRate3333);
	       double a36 = bg36.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates33 = numberFormat.format(a33* 100);
	       	String rates34 = numberFormat.format(a34* 100);
	       	String rates35 = numberFormat.format(a35* 100);
	       	String rates36 = numberFormat.format(a36* 100);
	       	
		   FinancialPositionDTO line9 = new FinancialPositionDTO("","资产与负债率(%)",obtainStr1(rates33),obtainStr1(rates34),obtainStr1(rates35),obtainStr1(rates36));

		   BigDecimal bg37 = new BigDecimal(weightScale1);
	       double a37 = bg37.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg38 = new BigDecimal(weightScale2);
	       double a38 = bg38.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg39 = new BigDecimal(weightScale3);
	       double a39 = bg39.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg40 = new BigDecimal(weightScale4);
	       double a40 = bg40.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates37 = numberFormat.format(a37);
	       	String rates38 = numberFormat.format(a38);
	       	String rates39 = numberFormat.format(a39);
	       	String rates40 = numberFormat.format(a40);
	       	
		   FinancialPositionDTO line10 = new FinancialPositionDTO("","负债与所有者权益比例",obtainStr1(rates37),obtainStr1(rates38),obtainStr1(rates39),obtainStr1(rates40));
		   
		   BigDecimal bg41 = new BigDecimal(accrualEnsure3);
	       double a41 = bg41.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg42 = new BigDecimal(accrualEnsure33);
	       double a42 = bg42.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg43 = new BigDecimal(accrualEnsure333);
	       double a43 = bg43.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg44 = new BigDecimal(accrualEnsure3333);
	       double a44 = bg44.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates41 = numberFormat.format(a41);
	       	String rates42 = numberFormat.format(a42);
	       	String rates43 = numberFormat.format(a43);
	       	String rates44 = numberFormat.format(a44);
		   FinancialPositionDTO line11 = new FinancialPositionDTO("","利息保障倍数",obtainStr1(rates41),obtainStr1(rates42),obtainStr1(rates43),obtainStr1(rates44));
		   
		   BigDecimal bg45 = new BigDecimal(mainBusiness1);
	       double a45 = bg45.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg46 = new BigDecimal(mainBusiness2);
	       double a46 = bg46.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg47 = new BigDecimal(mainBusiness3);
	       double a47 = bg47.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg48 = new BigDecimal(mainBusiness4);
	       double a48 = bg48.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates45 = numberFormat.format(a45);
	       	String rates46 = numberFormat.format(a46);
	       	String rates47 = numberFormat.format(a47);
	       	String rates48 = numberFormat.format(a48);
	       	
		   FinancialPositionDTO line12 = new FinancialPositionDTO("盈利能力：","主营业务收入(万元)",obtainStr1(rates45),obtainStr1(rates46),obtainStr1(rates47),obtainStr1(rates48));

		   BigDecimal bg49 = new BigDecimal(mainBusinessCost1);
	       double a49 = bg49.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg50 = new BigDecimal(mainBusinessCost2);
	       double a50 = bg50.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg51 = new BigDecimal(mainBusinessCost3);
	       double a51 = bg51.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg52 = new BigDecimal(mainBusinessCost4);
	       double a52 = bg52.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates49 = numberFormat.format(a49);
	       	String rates50 = numberFormat.format(a50);
	       	String rates51 = numberFormat.format(a51);
	       	String rates52 = numberFormat.format(a52);
	       	
		   FinancialPositionDTO line13 = new FinancialPositionDTO("","主营业务成本(万元)",obtainStr1(rates49),obtainStr1(rates50),obtainStr1(rates51),obtainStr1(rates52));

		   BigDecimal bg53 = new BigDecimal(BusinessCost1);
	       double a53 = bg53.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg54 = new BigDecimal(BusinessCost2);
	       double a54 = bg54.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg55 = new BigDecimal(BusinessCost3);
	       double a55 = bg55.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg56 = new BigDecimal(BusinessCost4);
	       double a56 = bg56.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates53 = numberFormat.format(a53);
	       	String rates54 = numberFormat.format(a54);
	       	String rates55 = numberFormat.format(a55);
	       	String rates56 = numberFormat.format(a56);
	       	
		   FinancialPositionDTO line14 = new FinancialPositionDTO("","营业费用(万元)",obtainStr1(rates53),obtainStr1(rates54),obtainStr1(rates55),obtainStr1(rates56));

		   BigDecimal bg57 = new BigDecimal(superviseCost1);
	       double a57 = bg57.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg58 = new BigDecimal(superviseCost2);
	       double a58 = bg58.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg59 = new BigDecimal(superviseCost3);
	       double a59 = bg59.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg60 = new BigDecimal(superviseCost4);
	       double a60 = bg60.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates57 = numberFormat.format(a57);
	       	String rates58 = numberFormat.format(a58);
	       	String rates59 = numberFormat.format(a59);
	       	String rates60 = numberFormat.format(a60);
	       	
		   FinancialPositionDTO line15 = new FinancialPositionDTO("","管理费用(万元)",obtainStr1(rates57),obtainStr1(rates58),obtainStr1(rates59),obtainStr1(rates60));

		   BigDecimal bg61 = new BigDecimal(financeCost1);
	       double a61 = bg61.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg62 = new BigDecimal(financeCost2);
	       double a62 = bg62.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg63 = new BigDecimal(financeCost3);
	       double a63 = bg63.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg64 = new BigDecimal(financeCost4);
	       double a64 = bg64.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates61 = numberFormat.format(a61);
	       	String rates62 = numberFormat.format(a62);
	       	String rates63 = numberFormat.format(a63);
	       	String rates64 = numberFormat.format(a64);
	       	
		   FinancialPositionDTO line16 = new FinancialPositionDTO("","财务费用(万元)",obtainStr1(rates61),obtainStr1(rates62),obtainStr1(rates63),obtainStr1(rates64));

		   BigDecimal bg65 = new BigDecimal(businessProfit1);
	       double a65 = bg65.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg66 = new BigDecimal(businessProfit2);
	       double a66 = bg66.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg67 = new BigDecimal(businessProfit3);
	       double a67 = bg67.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg68 = new BigDecimal(businessProfit4);
	       double a68 = bg68.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates65 = numberFormat.format(a65);
	       	String rates66 = numberFormat.format(a66);
	       	String rates67 = numberFormat.format(a67);
	       	String rates68 = numberFormat.format(a68);
	       	
		   FinancialPositionDTO line17 = new FinancialPositionDTO("","营业利润(万元)",obtainStr1(rates65),obtainStr1(rates66),obtainStr1(rates67),obtainStr1(rates68));

		   BigDecimal bg69 = new BigDecimal(profitCount1);
	       double a69 = bg69.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg70 = new BigDecimal(profitCount2);
	       double a70 = bg70.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg71 = new BigDecimal(profitCount3);
	       double a71 = bg71.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg72 = new BigDecimal(profitCount4);
	       double a72 = bg72.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates69 = numberFormat.format(a69);
	       	String rates70 = numberFormat.format(a70);
	       	String rates71 = numberFormat.format(a71);
	       	String rates72 = numberFormat.format(a72);
	       	
		   FinancialPositionDTO line18 = new FinancialPositionDTO("","利润总额(万元)",obtainStr1(rates69),obtainStr1(rates70),obtainStr1(rates71),obtainStr1(rates72));

		   BigDecimal bg73 = new BigDecimal(purifyProfit1);
	       double a73 = bg73.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg74 = new BigDecimal(purifyProfit2);
	       double a74 = bg74.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg75 = new BigDecimal(purifyProfit3);
	       double a75 = bg75.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg76 = new BigDecimal(purifyProfit4);
	       double a76 = bg76.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates73 = numberFormat.format(a73);
	       	String rates74 = numberFormat.format(a74);
	       	String rates75 = numberFormat.format(a75);
	       	String rates76 = numberFormat.format(a76);
	       	
		   FinancialPositionDTO line19 = new FinancialPositionDTO("","净利润(万元)",obtainStr1(rates73),obtainStr1(rates74),obtainStr1(rates75),obtainStr1(rates76));

		   BigDecimal bg77 = new BigDecimal(hairProfit1);
	       double a77 = bg77.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg78 = new BigDecimal(hairProfit2);
	       double a78 = bg78.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg79 = new BigDecimal(hairProfit3);
	       double a79 = bg79.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg80 = new BigDecimal(hairProfit4);
	       double a80 = bg80.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates77 = numberFormat.format(a77* 100);
	       	String rates78 = numberFormat.format(a78* 100);
	       	String rates79 = numberFormat.format(a79* 100);
	       	String rates80 = numberFormat.format(a80* 100);
	       	
		   FinancialPositionDTO line20 = new FinancialPositionDTO("","毛利率(%)",obtainStr1(rates77),obtainStr1(rates78),obtainStr1(rates79),obtainStr1(rates80));

		   BigDecimal bg81 = new BigDecimal(businessProfitRate1);
	       double a81 = bg81.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg82 = new BigDecimal(businessProfitRate2);
	       double a82 = bg82.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg83 = new BigDecimal(businessProfitRate3);
	       double a83 = bg83.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg84 = new BigDecimal(businessProfitRate4);
	       double a84 = bg84.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates81 = numberFormat.format(a81* 100);
	       	String rates82 = numberFormat.format(a82* 100);
	       	String rates83 = numberFormat.format(a83* 100);
	       	String rates84 = numberFormat.format(a84* 100);

		   FinancialPositionDTO line21 = new FinancialPositionDTO("","营业利润率(%)",obtainStr1(rates81),obtainStr1(rates82),obtainStr1(rates83),obtainStr1(rates84));

		   BigDecimal bg85 = new BigDecimal(sellProfitRate1);
	       double a85 = bg85.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg86 = new BigDecimal(sellProfitRate2);
	       double a86 = bg86.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg87 = new BigDecimal(sellProfitRate3);
	       double a87 = bg87.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg88 = new BigDecimal(sellProfitRate4);
	       double a88 = bg88.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
	       
	       	String rates85 = numberFormat.format(a85* 100);
	       	String rates86 = numberFormat.format(a86* 100);
	       	String rates87 = numberFormat.format(a87* 100);
	       	String rates88 = numberFormat.format(a88* 100);
	       	
		   FinancialPositionDTO line22 = new FinancialPositionDTO("","销售利润率(%)",obtainStr1(rates85),obtainStr1(rates86),obtainStr1(rates87),obtainStr1(rates88));
		   
		   BigDecimal bg89 = new BigDecimal(purifyProfitRate1);
	       double a89 = bg89.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg90 = new BigDecimal(purifyProfitRate2);
	       double a90 = bg90.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg91 = new BigDecimal(purifyProfitRate3);
	       double a91 = bg91.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg92 = new BigDecimal(purifyProfitRate4);
	       double a92 = bg92.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates89 = numberFormat.format(a89* 100);
	       	String rates90 = numberFormat.format(a90* 100);
	       	String rates91 = numberFormat.format(a91* 100);
	       	String rates92 = numberFormat.format(a92* 100);
	       	
		   FinancialPositionDTO line23 = new FinancialPositionDTO("","净利润率(%)",obtainStr1(rates89),obtainStr1(rates90),obtainStr1(rates91),obtainStr1(rates92));
		   
		   BigDecimal bg93 = new BigDecimal(propertyIncomeRate101);
	       double a93 = bg93.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg94 = new BigDecimal(propertyIncomeRate102);
	       double a94 = bg94.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg95 = new BigDecimal(propertyIncomeRate103);
	       double a95 = bg95.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg96 = new BigDecimal(propertyIncomeRate104);
	       double a96 = bg96.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates93 = numberFormat.format(a93* 100);
	       	String rates94 = numberFormat.format(a94* 100);
	       	String rates95 = numberFormat.format(a95* 100);
	       	String rates96 = numberFormat.format(a96* 100);
		   FinancialPositionDTO line24 = new FinancialPositionDTO("","净资产收益率(%)",obtainStr1(rates93),obtainStr1(rates94),obtainStr1(rates95),obtainStr1(rates96));
		  
		   BigDecimal bg97 = new BigDecimal(primeCostProfit101);
	       double a97 = bg97.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg98 = new BigDecimal(primeCostProfit102);
	       double a98 = bg98.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg99 = new BigDecimal(primeCostProfit103);
	       double a99 = bg99.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg100 = new BigDecimal(primeCostProfit104);
	       double a100 = bg100.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       	
	       	String rates97 = numberFormat.format(a97* 100);
	       	String rates98 = numberFormat.format(a98* 100);
	       	String rates99 = numberFormat.format(a99* 100);
	       	String rates100 = numberFormat.format(a100* 100);
		   FinancialPositionDTO line25 = new FinancialPositionDTO("","成本费用利润率(%)",obtainStr1(rates97),obtainStr1(rates98),obtainStr1(rates99),obtainStr1(rates100));
		 
		   BigDecimal bg101 = new BigDecimal(countProperty1);
	       double a101 = bg101.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg102 = new BigDecimal(countProperty2);
	       double a102 = bg102.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg103 = new BigDecimal(countProperty3);
	       double a103 = bg103.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg104 = new BigDecimal(countProperty4);
	       double a104 = bg104.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates101 = numberFormat.format(a101* 100);
	       	String rates102 = numberFormat.format(a102* 100);
	       	String rates103 = numberFormat.format(a103* 100);
	       	String rates104 = numberFormat.format(a104* 100);
	       	
		   FinancialPositionDTO line26 = new FinancialPositionDTO("","总资产报酬率(%)",obtainStr1(rates101),obtainStr1(rates102),obtainStr1(rates103),obtainStr1(rates104));
		   
		   BigDecimal bg105 = new BigDecimal(velocityRatio2);
	       double a105 = bg105.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg106 = new BigDecimal(velocityRatio22);
	       double a106 = bg106.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg107 = new BigDecimal(velocityRatio222);
	       double a107 = bg107.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg108 = new BigDecimal(velocityRatio2222);
	       double a108 = bg108.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       

	       	String rates105 = numberFormat.format(a105);
	       	String rates106 = numberFormat.format(a106);
	       	String rates107 = numberFormat.format(a107);
	       	String rates108 = numberFormat.format(a108);
		   FinancialPositionDTO line27 = new FinancialPositionDTO("营运能力：","存货(万元)",obtainStr1(rates105),obtainStr1(rates106),obtainStr1(rates107),obtainStr1(rates108));
		 
		   BigDecimal bg109 = new BigDecimal(incomeCredit1);
	       double a109 = bg109.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg110 = new BigDecimal(incomeCredit2);
	       double a110 = bg110.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg111 = new BigDecimal(incomeCredit3);
	       double a111 = bg111.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg112 = new BigDecimal(incomeCredit4);
	       double a112 = bg112.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates109 = numberFormat.format(a109);
	       	String rates110 = numberFormat.format(a110);
	       	String rates111 = numberFormat.format(a111);
	       	String rates112 = numberFormat.format(a112);
		   FinancialPositionDTO line28 = new FinancialPositionDTO("","应收账款(万元)",obtainStr1(rates109),obtainStr1(rates110),obtainStr1(rates111),obtainStr1(rates112));
		  
		   BigDecimal bg113 = new BigDecimal(turnover1);
	       double a113 = bg113.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg114 = new BigDecimal(turnover2);
	       double a114 = bg114.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg115 = new BigDecimal(turnover3);
	       double a115 = bg115.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg116 = new BigDecimal(turnover4);
	       double a116 = bg116.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates113 = numberFormat.format(a113* 100);
	       	String rates114 = numberFormat.format(a114* 100);
	       	String rates115 = numberFormat.format(a115* 100);
	       	String rates116 = numberFormat.format(a116* 100);
		   FinancialPositionDTO line29 = new FinancialPositionDTO("","总资产周转率(%)",obtainStr1(rates113),obtainStr1(rates114),obtainStr1(rates115),obtainStr1(rates116));
		   
		   BigDecimal bg117 = new BigDecimal(stock1);
	       double a117 = bg117.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg118 = new BigDecimal(stock2);
	       double a118 = bg118.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg119 = new BigDecimal(stock3);
	       double a119 = bg119.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg120 = new BigDecimal(stock4);
	       double a120 = bg120.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	
	       	String rates117 = numberFormat.format(a117* 100);
	       	String rates118 = numberFormat.format(a118* 100);
	       	String rates119 = numberFormat.format(a119* 100);
	       	String rates120 = numberFormat.format(a120* 100);
		   FinancialPositionDTO line30 = new FinancialPositionDTO("","存货周转率(%)",obtainStr1(rates117),obtainStr1(rates118),obtainStr1(rates119),obtainStr1(rates120));
		   
		   BigDecimal bg121 = new BigDecimal(averageProperty7);
	       double a121 = bg121.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg122 = new BigDecimal(averageProperty77);
	       double a122 = bg122.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg123 = new BigDecimal(averageProperty777);
	       double a123 = bg123.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg124 = new BigDecimal(averageProperty7777);
	       double a124 = bg124.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       
	       	String rates121 = numberFormat.format(a121* 100);
	       	String rates122 = numberFormat.format(a122* 100);
	       	String rates123 = numberFormat.format(a123* 100);
	       	String rates124 = numberFormat.format(a124* 100);
		   FinancialPositionDTO line31 = new FinancialPositionDTO("","应收账款周转率(%)",obtainStr1(rates121),obtainStr1(rates122),obtainStr1(rates123),obtainStr1(rates124));

		   BigDecimal bg125 = new BigDecimal(purifyStock1);
	       double a125 = bg125.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg126 = new BigDecimal(purifyStock2);
	       double a126 = bg126.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg127 = new BigDecimal(purifyStock3);
	       double a127 = bg127.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	       BigDecimal bg128 = new BigDecimal(purifyStock4);
	       double a128 = bg128.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

	       	String rates125 = numberFormat.format(a125* 100);
	       	String rates126 = numberFormat.format(a126* 100);
	       	String rates127 = numberFormat.format(a127* 100);
	       	String rates128 = numberFormat.format(a128* 100);
		   FinancialPositionDTO line32 = new FinancialPositionDTO("","净资产周转率(%)",obtainStr1(rates125),obtainStr1(rates126),obtainStr1(rates127),obtainStr1(rates128));

			List<FinancialPositionDTO> financialPositionDTOList = new ArrayList<FinancialPositionDTO>();

			financialPositionDTOList.add(line1);
			financialPositionDTOList.add(line2);
			financialPositionDTOList.add(line3);
			financialPositionDTOList.add(line4);
			financialPositionDTOList.add(line5);
			financialPositionDTOList.add(line6);
			financialPositionDTOList.add(line7);
			financialPositionDTOList.add(line8);
			financialPositionDTOList.add(line9);
			financialPositionDTOList.add(line10);
			financialPositionDTOList.add(line11);
			financialPositionDTOList.add(line12);
			financialPositionDTOList.add(line13);
			financialPositionDTOList.add(line14);
			financialPositionDTOList.add(line15);
			financialPositionDTOList.add(line16);
			financialPositionDTOList.add(line17);
			financialPositionDTOList.add(line18);
			financialPositionDTOList.add(line19);
			financialPositionDTOList.add(line20);
			financialPositionDTOList.add(line21);
			financialPositionDTOList.add(line22);
			financialPositionDTOList.add(line23);
			financialPositionDTOList.add(line24);
			financialPositionDTOList.add(line25);
			financialPositionDTOList.add(line26);
			financialPositionDTOList.add(line27);
			financialPositionDTOList.add(line28);
			financialPositionDTOList.add(line29);
			financialPositionDTOList.add(line30);
			financialPositionDTOList.add(line31);
			financialPositionDTOList.add(line32);
			
			
			for(int i=0;i<financialPositionDTOList.size();i++){
				map.put("bean"+i, financialPositionDTOList.get(i));
			}
			TemplateFile template = cl.getTemplateFile("FINANCE");
			if (!template.getFileUrl().equals("")) {
				String path = CommonUtil.getRootPath() + template.getFileUrl();
				ExcelExport.outToExcel(map, path, "企业财务状况导出.xls"+"."+template.getFileUrl().substring(template.getFileUrl().lastIndexOf(".")+1), response, request);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
