/**
 * @Title: SysPermissionServiceImpl.java
 * @Package com.xlkfinance.bms.server.system.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2014年12月19日 下午1:30:19
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysPermissionService.Iface;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.server.system.mapper.SystemPermisMapper;
import com.xlkfinance.bms.server.system.mapper.SystemUserRoleMapper;

@SuppressWarnings("unchecked")
@Service("sysPermissionServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysPermissionService")
public class SysPermissionServiceImpl implements Iface {
	@SuppressWarnings("rawtypes")
	@Resource(name = "systemPermisMapper")
	private SystemPermisMapper systemPermisMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "systemUserRoleMapper")
	private SystemUserRoleMapper systemUserRoleMapper;

	@Override
	public int addSysPermission(SysPermission sysPermission) throws ThriftServiceException, TException {
		return systemPermisMapper.addPermission(sysPermission);
	}

	@Override
	public int deleteSysPermission(SysPermission sysPermission) throws ThriftServiceException, TException {
		systemPermisMapper.deletePermission(sysPermission);
		return systemPermisMapper.deletePermissionOfRoleRalation(sysPermission);
	}

	@Override
	public int updateSysPermission(SysPermission sysPermission) throws ThriftServiceException, TException {
		return systemPermisMapper.updatePermisInfo(sysPermission);
	}
	
	@Override
	public int updateSysPermissionByMenuId(SysPermission sysPermission) throws ThriftServiceException, TException {
		return systemPermisMapper.updateSysPermissionByMenuId(sysPermission);
	}
	
	@Override
	public int deleteSysPermissionByMenuId(SysPermission sysPermission) throws ThriftServiceException, TException {
		return systemPermisMapper.deleteSysPermissionByMenuId(sysPermission);
	}

	@Override
	public List<SysPermission> getSysPermissionByParameter(SysPermission sysPermission) throws TException {
		List<SysPermission> list = systemPermisMapper.getAllPermisForPage(sysPermission);
		return list==null?(new ArrayList<SysPermission>()):list;
	}

	@Override
	public List<SysPermission> getSysPermissionList(SysPermission sysPermission) throws ThriftServiceException, TException {
		List<SysPermission> permissions= systemPermisMapper.searchPermisByLike(sysPermission);
		return permissions==null?(new ArrayList<SysPermission>()):permissions;
	}

	@Override
	public List<SysRole> getPermisRoleList(int pageNum, int pageSize, SysPermission sysPermission) throws ThriftServiceException, TException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		map.put("pid", sysPermission.getPid());
		List<SysRole> roles = systemPermisMapper.getRoleOfPermisList(map);
		return roles==null?(new ArrayList<SysRole>()):roles;
	}

	@Override
	public List<SysRole> getNotHaveRoleOfPermisList(int pageNum, int pageSize, SysPermission sysPermission) throws ThriftServiceException, TException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		map.put("pid", sysPermission.getPid());
		List<SysRole> roles = systemPermisMapper.getNotHaveRoleOfPermisList(map);
		return roles==null?(new ArrayList<SysRole>()):roles;
	}

	@Override
	public int deleteRoleOfPermisById(Map<String, String> parameter) throws ThriftServiceException, TException {
		int roleId = Integer.parseInt(parameter.get("roleId"));
		int permisId = Integer.parseInt(parameter.get("permisId"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("permisId", permisId);
		return systemPermisMapper.deleteRoleOfPermisByPid(map);
	}

	@Override
	public int addRoleOfPermisById(Map<String, String> parameter) throws ThriftServiceException, TException {
		int roleId = Integer.parseInt(parameter.get("roleId"));
		int permisId = Integer.parseInt(parameter.get("permisId"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("permisId", permisId);
		int count = systemUserRoleMapper.searchRolePermisRalationByIds(map);
		if (count == 0) {
			return systemPermisMapper.addRolePermisRelation(map);
		}
		return 0;
	}

	@Override
	public int getSysPermissionCount(SysPermission sysPermission)
			throws TException {
		return systemPermisMapper.getAllPermisCount(sysPermission);
	}

	@Override
	public int srarchSysPermissionCount(SysPermission sysPermission)
			throws TException {
		return systemPermisMapper.searchPermisByLikeCount(sysPermission);
	}
	
	@Override
	public SysPermission getPermisByName(String permisName){
		SysPermission permission = systemPermisMapper.getPermisByName(permisName);
		return permission==null?(new SysPermission()):permission;
	}

	@Override
	public int getPermisRoleListTotal(SysPermission sysPermission)
			throws ThriftServiceException, TException {
		return systemPermisMapper.getPermisRoleListTotal(sysPermission);
	}
}
