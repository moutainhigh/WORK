package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReport;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportEditDTO;
import com.xlkfinance.bms.rpc.customer.ExcelCusComIncomeReport;
@MapperScan("cusComIncomeReportMapper")
public interface CusComIncomeReportMapper<T, PK> extends BaseMapper<T, PK> {
	public int saveCusComIncomeReport(CusComIncomeReport cusComIncomeReport);
	public List<CusComIncomeReport> selectCusComIncomeReportByReportId(int reportId);
	public int updateCusComIncomeReport(CusComIncomeReport cusComIncomeReport);
	public int deleteCusComIncomeReportByReportID(int reportID);
	public int deleteCusComIncomeReportByReportIDUseBatch(List<Object> list);
	public List<CusComIncomeReportEditDTO> initCusComIncomeReportByReportId(int reportId);
	
	//----------li,run,baobiao,daochu
	
	public List<ExcelCusComIncomeReport> excelCusComIncomeReportByReportId(List list);
	public List<ExcelCusComIncomeReport> excelCashFlowByReportId(List list);
	public List<ExcelCusComIncomeReport> exportCashFlowMaterialByReportId(List list);
}
