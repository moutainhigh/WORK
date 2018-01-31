/**
 * @Title: CashFlowItemMapper.java
 * @Package com.xlkfinance.bms.server.customer.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月19日 下午8:57:25
 * @version V1.0
 */
package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CashFlowItem;
import com.xlkfinance.bms.rpc.customer.CashFlowReport;
import com.xlkfinance.bms.rpc.customer.FinacialDTO;
@MapperScan("CashFlowItemMapper")
public interface CashFlowItemMapper<T, PK>  extends BaseMapper<T, PK>{

	public List<FinacialDTO> getAllCusComCaseFlow(CashFlowItem cashFlowItem);
	
	public int insertCashFlowReport(List<CashFlowReport> list);
	
	public CashFlowReport selectCusComCashFlowReportByComId(int comId);
	
	public CashFlowItem selectCusComCashFlowItemByLineNum(int lineId);
	
	//public int addCusComCashFlowReport(CashFlowReport cashFlowReport); 
	

}
