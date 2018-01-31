package com.xlkfinance.bms.server.beforeloan.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;

@MapperScan("projectRelationMapper")
public interface ProjectRelationMapper<T, PK> extends BaseMapper<T, PK> {

}
