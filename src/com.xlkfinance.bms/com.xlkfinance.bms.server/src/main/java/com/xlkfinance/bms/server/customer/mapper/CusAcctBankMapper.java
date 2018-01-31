package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcctBank;
@MapperScan("cusAcctBankMapper")
public interface CusAcctBankMapper<T, PK> extends BaseMapper<T, PK> {
	public List<GridViewDTO> getCusAcctBanks(CusAcctBank cusAcctBank);
	public CusAcctBank selectCusAcctBankById(int pid);
	public int updateCusAcctBankById(CusAcctBank cusAcctBank);
	
	@SuppressWarnings("rawtypes")
	public int deleteCusAcctBank(List pid);
	public int getTotal(CusAcctBank cusAcctBank);
	/**
	  * @Description: TODO 根据客户id和账户用途查询银行账户信息
	  * @param cusAcctBank
	  * @return
	  * @author: yql
	  * @date: 2015年3月30日 下午3:37:16
	 */
	public List<GridViewDTO> getCusBankByAcctId(CusAcctBank cusAcctBank);
	
}
