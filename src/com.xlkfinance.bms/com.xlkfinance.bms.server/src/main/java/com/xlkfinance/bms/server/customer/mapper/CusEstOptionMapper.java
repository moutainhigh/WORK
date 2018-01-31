package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusEstOption;

@MapperScan("cusEstOptionMapper")
public interface CusEstOptionMapper<T, PK> extends BaseMapper<T, PK> {
	
	public int insertCusEstOptions(List<CusEstOption> options);
	
	public int updateCusEstOptions(List<CusEstOption> options);
	
	public List<CusEstOption> getCusEstOptions(int quotaId);
	
	public int deleteEstOptions(List<CusEstOption> list);

}
