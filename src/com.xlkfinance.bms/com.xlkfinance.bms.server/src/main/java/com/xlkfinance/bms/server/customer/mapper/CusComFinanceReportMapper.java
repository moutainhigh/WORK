package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusComFinanceReport;
@MapperScan("cusComFinanceReportMapper")
public interface CusComFinanceReportMapper<T, PK> extends BaseMapper<T, PK> {
	public CusComFinanceReport selectCusComFinanceReportByComId(int comId); 
	public int saveCusComFinanceReport(CusComFinanceReport cusComFinanceReport);
	public CusComFinanceReport selectCusComFinanceReportByComIdAndYearMonth(Map map); 
	public List<CusComFinanceReport> selectCusComFinanceReportOverviewDTOByAnnual(Map<String,Integer> map);
	public int obtainCusComFinanceReportOverviewCount(Map<String,Integer> map);
	public int deleteCusComFinanceReportByPID(int pid);
	public int deleteCusComFinanceReportByPIDUseBatch(List<Object> list);
}
