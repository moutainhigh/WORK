/**
 * @Title: sysMetroUiMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 下午7:22:11
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysMetroUiVo;

@MapperScan("sysMetroUiMapper")
public interface SysMetroUiMapper<T, PK> extends BaseMapper<T, PK> {
	/**
	  * @Description: 新增磁铁菜单
	  * @param sysMetroUiVo
	  * @return int pid
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:29:15
	  */
	public int insertSysMetroUi(SysMetroUiVo sysMetroUiVo);
	/**
	  * @Description: 更改磁铁菜单
	  * @param sysMetroUiVo
	  * @return 更改记录条数
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:30:11
	  */
	public int updateSysMetroUi(SysMetroUiVo sysMetroUiVo);
	/**
	  * @Description: 查询磁铁菜单   ， 根据pid
	  * @param pid
	  * @return	SysMetroUiVo 磁铁菜单 实体类
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:31:23
	  */
	public SysMetroUiVo getSysMetroUiByPid(int pid);
	/**
	  * @Description: 分页查询磁铁菜单
	  * @param sysMetroUiVo
	  * @return List<SysMetroUiVo> 磁铁菜单 实体类集合
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:32:32
	  */
	public List<SysMetroUiVo> findSysMetroUiPage(SysMetroUiVo sysMetroUiVo);
	
	/**
	  * @Description: 查询磁铁菜单表总记录条数
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午6:23:56
	  */
	public int findSysMetroUiTotal();
	
	/**
	  * @Description: 删除磁铁菜单 根据pid
	  * @param pid  
	  * @return int 删除的行数
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:46:46
	  */
	public int deleteSysMetroUi(int pid);
	
	/**
	  * @Description:  查询根菜单 根据用户权限 条件用户名称
	  * @param userName
	  * @return List<SysMetroUiVo>
	  * @author: JingYu.Dai
	  * @date: 2015年5月6日 上午10:22:55
	 */
	public List<SysMetroUiVo> selectMetroUiByUserName(String userName);
}
