package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusComDebt;
import com.xlkfinance.bms.rpc.customer.CusComGuaranteeType;

@MapperScan("cusComDebtMapper")
@SuppressWarnings("rawtypes")
public interface CusComDebtMapper<T, PK> extends BaseMapper<T, PK> {
	
	public List<GridViewDTO> getCusComDebts(CusComDebt cusComDebt);
	public List<GridViewDTO> getCusComDebtss(CusComDebt cusComDebt);
	public CusComDebt selectByPrimaryKeys(int pid);
	public int deleteCusDebt(List list);
	public int getTotalDept(CusComDebt cusComDebt);
	public int getTotalDepts(CusComDebt cusComDebt);
	public int insertDebtGuarType(CusComGuaranteeType cusComGuaranteeType);
	public int updateDebtGuarType(CusComGuaranteeType cusComGuaranteeType);
	public int deleteDebtGuarType(int comAssureId);
	public List<CusComGuaranteeType> getGuaranteeTypeBycusComBasePid(int cusComBasePid);
}
