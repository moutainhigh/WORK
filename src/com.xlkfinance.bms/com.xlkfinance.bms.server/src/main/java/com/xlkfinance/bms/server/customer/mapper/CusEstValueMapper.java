package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusEstValue;

@MapperScan("cusEstValueMapper")
public interface CusEstValueMapper<T, PK> extends BaseMapper<T, PK> {
	
	
	public int insertCusEstValues(List<CusEstValue> vals);
	
	public int updateCusEstValues(List<CusEstValue> vals);
	
	public double getEstInfoSorce(List<CusEstValue> vals);

}
