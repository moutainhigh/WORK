package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusEstFactorWeights;

@MapperScan("cusEstFactorWeightsMapper")
public interface CusEstFactorWeightsMapper<T, PK> extends BaseMapper<T, PK> {
	
	public int insertEstFactors(List<CusEstFactorWeights> list);
	
	public int updateEstFactors(List<CusEstFactorWeights> list);
	
	public int deleteEstFactors(List<CusEstFactorWeights> list);
	
	public List<CusEstFactorWeights> getFactorWeights(int templateId);

}
