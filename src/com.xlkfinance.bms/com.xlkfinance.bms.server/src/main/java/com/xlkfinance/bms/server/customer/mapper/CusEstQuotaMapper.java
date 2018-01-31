package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusEstQuota;

@MapperScan("cusEstQuotaMapper")
public interface CusEstQuotaMapper<T, PK> extends BaseMapper<T, PK> {
	
	public int insertEstQuotas(List<CusEstQuota> cusEstQuotas);
	
	public int updateEstQuotas(List<CusEstQuota> cusEstQuotas);
	
	public int deleteEstQuotas(List<CusEstQuota> cusEstQuotas);
	
	public List<CusEstQuota> getEstQuotas(int factorId);
}
