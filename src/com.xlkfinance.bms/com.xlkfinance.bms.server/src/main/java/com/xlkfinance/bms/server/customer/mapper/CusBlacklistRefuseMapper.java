package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusBlacklistRefuse;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
@MapperScan("cusBlacklistRefuseMapper")
public interface CusBlacklistRefuseMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	 * 分页查询黑名单数据
	 * @param cusPerBaseDTO
	 * @return
	 */
	public List<GridViewDTO> selectBlacklists(CusPerBaseDTO cusPerBaseDTO);
	
	public List<GridViewDTO> selectRefuses(CusPerBaseDTO cusPerBaseDTO);
	
	/**
	 * 
	  * @Description: 批量解除黑名单，拒贷客户
	  * @param blacklistRefuses
	  * @author: zhanghg
	  * @date: 2015年3月3日 下午4:58:01
	 */
	public void updateBlackRefuses(List<CusBlacklistRefuse> blacklistRefuses);
	
	/**
	 * 
	  * @Description: 批量加入黑名单，拒贷客户
	  * @param blacklistRefuses
	  * @author: zhanghg
	  * @date: 2015年3月3日 下午4:58:01
	 */
	public void insertBlackRefuses(List<CusBlacklistRefuse> blacklistRefuses);
	
	public void insertBlacklistRefuse(CusBlacklistRefuse cusBlacklistRefuse);
	/**
	 * 查询黑名单总数
	 * @param cusPerBaseDTO
	 * @return
	 */
	public int getBlackListCount(CusPerBaseDTO cusPerBaseDTO);
	/**
	 * 根据证件号码判断是否为黑名单
	 * @param cusPerBaseDTO
	 * @return
	 */
	public int getBlackByCertNum(CusPerBaseDTO cusPerBaseDTO);
}
