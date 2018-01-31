/**
 * @Title: SysMetroUiServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2014年12月23日 下午3:30:19
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.system.SysMetroUiService.Iface;
import com.xlkfinance.bms.rpc.system.SysMetroUiVo;
import com.xlkfinance.bms.server.system.mapper.SysMetroUiMapper;

@SuppressWarnings("unchecked")
@Service("sysMetroUiServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysMetroUiService")
public class SysMetroUiServiceImpl implements Iface{
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "sysMetroUiMapper")
	private SysMetroUiMapper sysMetroUiMapper;
	/**
	  * @Description: 新增磁铁菜单
	  * @param sysMetroUiVo
	  * @return int pid
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:29:15
	  */
	@Override
	public int insertSysMetroUi(SysMetroUiVo sysMetroUiVo){
		return sysMetroUiMapper.insertSysMetroUi(sysMetroUiVo);
	}
	/**
	  * @Description: 更改磁铁菜单
	  * @param sysMetroUiVo
	  * @return 更改记录条数
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:30:11
	  */
	@Override
	public int updateSysMetroUi(SysMetroUiVo sysMetroUiVo){
		return sysMetroUiMapper.updateSysMetroUi(sysMetroUiVo);
	}
	/**
	  * @Description: 查询磁铁菜单   ， 根据pid
	  * @param pid
	  * @return	SysMetroUiVo 磁铁菜单 实体类
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:31:23
	  */
	@Override
	public SysMetroUiVo getSysMetroUiByPid(int pid){
		SysMetroUiVo metroUiVo = sysMetroUiMapper.getSysMetroUiByPid(pid);
		return metroUiVo==null?(new SysMetroUiVo()):metroUiVo;
	}
	/**
	  * @Description: 分页查询磁铁菜单
	  * @param sysMetroUiVo
	  * @return List<SysMetroUiVo> 磁铁菜单 实体类集合
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:32:32
	  */
	@Override
	public List<SysMetroUiVo> findSysMetroUiPage(SysMetroUiVo sysMetroUiVo){
		List<SysMetroUiVo> list = sysMetroUiMapper.findSysMetroUiPage(sysMetroUiVo);
		return list==null?(new ArrayList<SysMetroUiVo>()):list;
	}
	/**
	  * @Description: 查询磁铁菜单表总记录条数
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午6:23:56
	  */
	@Override
	public int findSysMetroUiTotal(){
		return sysMetroUiMapper.findSysMetroUiTotal();
	}
	
	/**
	  * @Description: 删除磁铁菜单 根据pid
	  * @param pid  
	  * @return int 删除的行数
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:46:46
	  */
	@Override
	public int deleteSysMetroUi(int pid){
		return sysMetroUiMapper.deleteSysMetroUi(pid);
	}
	
	/**
	  * @Description: 查询根菜单 根据用户权限 条件用户名称
	  * @param userName
	  * @return List<SysMetroUiVo>
	  * @author: JingYu.Dai
	  * @date: 2015年5月6日 上午10:24:22
	 */
	@Override
	public List<SysMetroUiVo> selectMetroUiByUserName(String userName){
		List<SysMetroUiVo> list = sysMetroUiMapper.selectMetroUiByUserName(userName);
		return list==null?(new ArrayList<SysMetroUiVo>()):list;
	}
}
