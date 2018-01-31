package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusComStaff;
import com.xlkfinance.bms.rpc.customer.CusComStaffDTO;

@MapperScan("cusComStaffMapper")
public interface CusComStaffMapper<T, PK> extends BaseMapper<T, PK> {
	
	public int insertCusComStaffs(List<CusComStaff> staffs);
	
	public List<CusComStaff> selectByComId(int comId);
	
	public int updateCusComStaffs(List<CusComStaff> staffs);
	
	public int updateCusComStaffss(CusComStaffDTO cusComStaffDTO);
	
	

}
