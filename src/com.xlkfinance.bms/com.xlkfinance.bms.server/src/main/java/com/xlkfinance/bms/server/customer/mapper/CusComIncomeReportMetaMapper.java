package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusComIncomeReportMeta;
@MapperScan("cusComIncomeReportMetaMapper")
public interface CusComIncomeReportMetaMapper<T, PK> extends BaseMapper<T, PK> {
	public List<CusComIncomeReportMeta> selectAllCusComIncomeReportMeta();
	public CusComIncomeReportMeta selectCusComIncomeReportMetaByLineNum(int lineNum);
}
