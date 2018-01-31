package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusPerCreditAddress;

@MapperScan("cusPerCreditAddressMapper")
public interface CusPerCreditAddressMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<CusPerCreditAddress> selectAddrByCreditId(int pid);
	
	public int insertCreditAddrs(List<CusPerCreditAddress> list);
	
	public int updateCreditAddrs(List<CusPerCreditAddress> list);

}
