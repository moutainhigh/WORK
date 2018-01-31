package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusComContact;
@MapperScan("cusComContactMapper")
@SuppressWarnings("rawtypes")
public  interface CusComContactMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<GridViewDTO> getCusComContacts(CusComContact comContact);
	public int updateCusComContacts(CusComContact CusComContact);
	public int deleteComContact(List pid);
	public int selectCusComContactById(int acctId);
	public int getTotal(CusComContact cusComContact);
	
	
}
