package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusComShare;

@MapperScan("cusComShareMapper")
public interface CusComShareMapper<T, PK> extends BaseMapper<T, PK> {

	public int insertCusComShares(List<CusComShare> list);

	public List<CusComShare> selectShareById(int pid);

	public int updateCusComShares(List<CusComShare> shares);

	/**
	 * 
	 * @Description: 查询企业股东信息列表
	 * @param cusComId
	 *            企业ID
	 * @return 企业股东信息列表
	 * @author: Cai.Qing
	 * @date: 2015年1月15日 上午11:51:04
	 */
	public List<CusComShare> getShareList(int cusComId);
	public int deleteCusComShares(List<CusComShare> shares);
	
}
