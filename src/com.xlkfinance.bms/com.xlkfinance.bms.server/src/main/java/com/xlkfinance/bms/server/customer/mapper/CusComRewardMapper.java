package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusComReward;

@MapperScan("cusComRewardMapper")
public interface CusComRewardMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<GridViewDTO> getCusComRewards(CusComReward cusComReward);
	public int getTotalReward(CusComReward comReward);

}
