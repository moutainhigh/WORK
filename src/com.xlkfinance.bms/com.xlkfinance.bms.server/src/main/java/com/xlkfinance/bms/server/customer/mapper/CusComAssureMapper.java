package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusComAssure;

@MapperScan("cusComAssureMapper")
public interface CusComAssureMapper<T, PK> extends BaseMapper<T, PK>  {
	
	public List<GridViewDTO> getCusComAssures(CusComAssure cusComAssure);
	public int getTotalAssure(CusComAssure cusComAssure);
	public int insertCusComGuarantee(Map<String,Integer> map);
	public int updateComGuaranteeType(Map<String,Integer> map);
	
}
