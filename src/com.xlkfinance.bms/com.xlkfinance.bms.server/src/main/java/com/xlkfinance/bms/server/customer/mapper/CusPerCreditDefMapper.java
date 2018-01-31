package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusPerCreditDef;

@MapperScan("cusPerCreditDefMapper")
public interface CusPerCreditDefMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<CusPerCreditDef> selectDefByCreditId(CusPerCreditDef def);
	
	public int insertCreditDefs(List<CusPerCreditDef> list);
	
	public int updateCreditDefs(List<CusPerCreditDef> list);
}
