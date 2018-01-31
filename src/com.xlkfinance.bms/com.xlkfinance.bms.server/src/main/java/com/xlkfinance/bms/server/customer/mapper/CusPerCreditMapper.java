package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBase;

@MapperScan("cusPerCreditMapper")
public interface CusPerCreditMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<GridViewDTO> getCusPerCredits(CusPerBase cusPerBase);
	
	@SuppressWarnings("rawtypes")
	public int deleteCusPerCredit(List pids);
	public int getTotalCusPerBase(CusPerBase cusPerBase);

}
