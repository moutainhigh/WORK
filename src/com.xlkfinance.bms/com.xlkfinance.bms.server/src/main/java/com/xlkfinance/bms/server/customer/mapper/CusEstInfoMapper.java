package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusEstInfoDTO;

@MapperScan("cusEstInfoMapper")
@SuppressWarnings("rawtypes")
public interface CusEstInfoMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<GridViewDTO> getcusEstInfos(CusEstInfoDTO cusEstInfoDTO);
	
	
	public int deleteEstInfos(List list);
	public int getTotalEst(CusEstInfoDTO cusEstInfoDTO);

}
