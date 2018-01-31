package com.xlkfinance.bms.server.customer.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;

@MapperScan("cusPerFamilyMapper")
public interface CusPerFamilyMapper<T, PK> extends BaseMapper<T, PK> {
	public String selectMarrStatus(int acctId);

}
