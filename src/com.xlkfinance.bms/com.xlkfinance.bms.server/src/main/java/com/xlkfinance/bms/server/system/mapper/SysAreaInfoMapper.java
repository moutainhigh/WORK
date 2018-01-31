package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;

/**   
 * 系统地区
 * @author chenzhuzhen
 * @date 2016年6月30日 下午6:37:23
 * @param <T>
 * @param <PK>
 */
@MapperScan("sysAreaInfoMapper")
public interface SysAreaInfoMapper<T, PK> extends BaseMapper<T, PK> {
	
	//查询地区数据
	public List<SysAreaInfo> getSysAreaInfo(SysAreaInfo sysAreaInfo);
	
	//根据地区编号查询一条数据
	public SysAreaInfo getSysAreaInfoByCode(String areaCode);
	
	//根据用户Id
	public SysAreaInfo getSysAreaInfoByUserId(@Param("userId") int userId);
	
	//根据地区名称查询一条数据
	public SysAreaInfo getSysAreaInfoByName(String areaName);
	
	/**
	 * 分页查询地区信息列表（根据传入条件查询省、市、 区信息）
	 * @author dulin
	 * @param sysAreaInfo
	 * @since 2017-12-04
	 * @return
	 */
	public List<SysAreaInfo> queryPagedAreaInfo(SysAreaInfo sysAreaInfo);
	
	/**
	 * 查询地区信息数量（根据传入条件查询省、市、 区信息）
	 * @author dulin
	 * @param sysAreaInfo
	 * @since 2017-12-04
	 * @return
	 */
	public Integer countAreaInfo(SysAreaInfo sysAreaInfo);
	
	/**
	 * 保存地区信息
	  * @param sysAreaInfo
	  * @return
	  * @author: dulin
	  * @date: 2017年12月04日 下午6:36:13
	 */
	public int saveAreaInfo(SysAreaInfo sysAreaInfo);
	
	/**
	 * 根据地区编码删除地区信息
	  * @param areaCodes 地区编码
	  * @return
	  * @author: dulin
	  * @date: 2017年12月04日 下午6:36:13
	 */
	public int batchDelete(String[] areaCodes);
}
