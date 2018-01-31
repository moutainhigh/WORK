package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusComBalanceSheetMeta;
@MapperScan("cusComBalanceSheetMetaMapper")
public interface CusComBalanceSheetMetaMapper<T, PK> extends BaseMapper<T, PK> {
	public List<CusComBalanceSheetMeta> selectAllCusComBalanceSheetMeta();
	public List<CusComBalanceSheetMeta> selectLeftCusComBalanceSheetMeta();
	public List<CusComBalanceSheetMeta> selectRightCusComBalanceSheetMeta();
}
