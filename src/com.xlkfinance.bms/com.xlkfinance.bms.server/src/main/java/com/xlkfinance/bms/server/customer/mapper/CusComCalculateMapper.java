package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheetCalculateDTO;
import com.xlkfinance.bms.rpc.customer.CusComCashFlowReportCalculateDTO;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportCalculateDTO;
@MapperScan("cusComCalculateMapper")
public interface CusComCalculateMapper<T, PK> extends BaseMapper<T, PK> {
	public String obtainPIDForMaxMonth(Map<String,Integer> map);
	public List<CusComBalanceSheetCalculateDTO> obtainLeftCusComBSLCalculatesByReportId(int reportId);
	public List<CusComBalanceSheetCalculateDTO> obtainRightCusComBSLCalculatesByReportId(int reportId);
	public List<CusComIncomeReportCalculateDTO> obtainIncomeReportCalculateByReportId(int reportId);
	public List<CusComCashFlowReportCalculateDTO> obtainCashFlowCalculateByReportId(int reportId);
	public List<CusComCashFlowReportCalculateDTO> obtainSupplementCashFlowCalculateByReportId(int reportId);
	
	
}
