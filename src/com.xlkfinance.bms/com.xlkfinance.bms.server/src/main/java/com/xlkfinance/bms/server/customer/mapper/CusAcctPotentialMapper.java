package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcctPotential;

@MapperScan("cusAcctPotentialMapper")
public interface CusAcctPotentialMapper<T, PK> extends BaseMapper<T, PK> {
	public List<GridViewDTO> getCusAcctPotentials(CusAcctPotential cusAcctPotential);
	public CusAcctPotential selectByIdPotential(int pid);
	public int deleteCusAcctPotential(List pids);
	public int getTotalPotential(CusAcctPotential cusAcctPotential);
	public int insertsysUserId(CusAcctPotential cusAcctPotential);

}
