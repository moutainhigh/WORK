/**
 * @Title: ContractServiceMapper.java
 * @Package com.xlkfinance.bms.server.contract.mapper
 * @Description: 合同参数
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月9日 下午19:23:35
 * @version V1.0
 */
package com.xlkfinance.bms.server.mortgage.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;

/**
 * 
 * @ClassName: ProjectAssHandleMapper
 * @Description: 抵质押物处理Mapper
 * @author: Cai.Qing
 * @date: 2015年6月24日 下午6:19:11
 */
@MapperScan("projectAssHandleMapper")
public interface ProjectAssHandleMapper<T, PK> extends BaseMapper<T, PK> {

}
