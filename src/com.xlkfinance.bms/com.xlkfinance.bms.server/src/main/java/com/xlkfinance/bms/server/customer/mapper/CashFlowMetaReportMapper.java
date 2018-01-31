/**
 * @Title: CashFlowReportMapper.java
 * @Package com.xlkfinance.bms.server.customer.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月21日 下午5:47:09
 * @version V1.0
 */
package com.xlkfinance.bms.server.customer.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CashFlowReport;
@MapperScan("cashFlowMetaReportMapper")
public interface CashFlowMetaReportMapper<T, PK>  extends BaseMapper<T, PK> {
	public int addCusComCashFlowMetaReport(CashFlowReport cashFlowReport);
}
