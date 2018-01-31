package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusComTeam;

@MapperScan("cusComTeamMapper")
@SuppressWarnings("rawtypes")
public interface CusComTeamMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<GridViewDTO> getCusComTeams(CusComTeam cusComTeam);
	public CusComTeam selectCusComTeamById(int pid);
	public int updateById(CusComTeam cusComTeam);
	public int deleteCusComTeam(List list);
	public int getTotal(CusComTeam cusComTeam);

}
