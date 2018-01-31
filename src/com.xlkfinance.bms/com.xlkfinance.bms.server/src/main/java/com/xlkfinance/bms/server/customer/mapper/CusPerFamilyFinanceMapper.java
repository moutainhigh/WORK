package com.xlkfinance.bms.server.customer.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;

@MapperScan("cusPerFamilyFinanceMapper")
public interface CusPerFamilyFinanceMapper<T, PK> extends BaseMapper<T, PK> {

}
