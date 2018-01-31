package com.xlkfinance.bms.server.customer.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;

@MapperScan("cusPerSocSecMapper")
public interface CusPerSocSecMapper<T, PK> extends BaseMapper<T, PK> {

}
