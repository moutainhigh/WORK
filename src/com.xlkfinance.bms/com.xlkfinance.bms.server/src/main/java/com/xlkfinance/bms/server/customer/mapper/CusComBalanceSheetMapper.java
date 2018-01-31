package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.BalanceSheetEditDTO;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheet;
import com.xlkfinance.bms.rpc.customer.ExcelCusComBalanceSheet;
@MapperScan("cusComBalanceSheetMapper")
public interface CusComBalanceSheetMapper<T, PK> extends BaseMapper<T, PK> {
	public int saveCusComBalanceSheet(CusComBalanceSheet cusComBalanceSheet);
	public List<CusComBalanceSheet> selectCusComBalanceSheetByReportId(int reportId);
	public int updateCusComBalanceSheet(CusComBalanceSheet cusComBalanceSheet);
	public int deleteCusComBalanceSheetByReportID(int reportID);
	public int deleteCusComBalanceSheetByReportIDUseBatch(List<Object> list);
	public List<BalanceSheetEditDTO> initLeftEditCusComBalanceSheetByReportId(int reportId);
	public List<BalanceSheetEditDTO> initRightEditCusComBalanceSheetByReportId(int reportId);
	
	public List<ExcelCusComBalanceSheet> getExcelCusComBalanceSheet(List list);
}
