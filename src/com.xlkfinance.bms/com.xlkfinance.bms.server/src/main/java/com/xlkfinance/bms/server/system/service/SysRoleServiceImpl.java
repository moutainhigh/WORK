/**
 * @Title: SysRoleServiceImpl.java
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
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.rpc.system.SysRoleService.Iface;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserDto;
import com.xlkfinance.bms.rpc.system.SysUserRoleSearch;
import com.xlkfinance.bms.server.system.mapper.SystemUserRoleMapper;

@SuppressWarnings("unchecked")
@Service("sysRoleServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysRoleService")
public class SysRoleServiceImpl implements Iface {
	@SuppressWarnings("rawtypes")
	@Resource(name = "systemUserRoleMapper")
	private SystemUserRoleMapper systemUserRoleMapper;

	@Override
	public SysRole addSysRole(SysRole sysRole) throws ThriftServiceException, TException {
		 int count = systemUserRoleMapper.getCountByRoleCode(sysRole);
		if(count>0){
			sysRole.setRoleCode("code_error");
			return sysRole; 
		}
		sysRole.setPid(systemUserRoleMapper.selectSysRoleSeq());
		systemUserRoleMapper.addSysRole(sysRole);
		return sysRole;
	}

	@Override
	public int deleteSysRole(List<Integer> pid) throws ThriftServiceException, TException {
		int status = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		for (Integer integer : pid) {
			//删除之前 查看该角色是否与用户关联
			int count = systemUserRoleMapper.searchRoleCountByRoleUserId(integer);
			//没有才能删除
			if(count==0){
				map.put("pid", integer);
				systemUserRoleMapper.deleteRoleAndPermisRalation(map);
				systemUserRoleMapper.deleteRoleAndUserRalation(map);
				status = systemUserRoleMapper.deleteRole(map);
			}else{
				status=-1;
			}
		}
		return status;
	}

	@Override
	public int updateSysRole(SysRole sysRole) throws ThriftServiceException, TException {
		return systemUserRoleMapper.updateRoleById(sysRole);
	}

	@Override
	public List<SysRole> getSysRoleList() throws TException {
		List<SysRole> sysRoles = systemUserRoleMapper.getAllRoles();
		return sysRoles==null?(new ArrayList<SysRole>()):sysRoles;
	}

	@Override
	public List<SysRole> getAllRolesForPage(SysRole sysRole) throws ThriftServiceException, TException {
		List<SysRole> roles = systemUserRoleMapper.getAllRolesForPage(sysRole);
		return roles==null?(new ArrayList<SysRole>()):roles;
	}

	@Override
	public List<SysPermission> getSysPermissionList(SysRole sysRole,String permisName) throws ThriftServiceException, TException {
		List<SysPermission> permissions = systemUserRoleMapper.getPermisByRoleId(sysRole,permisName);
		return permissions==null?(new ArrayList<SysPermission>()):permissions;
	}

	@Override
	public List<SysRole> searchRoleListByRoleName(SysRole sysRole) throws ThriftServiceException, TException {
		List<SysRole> roles = systemUserRoleMapper.searchRoleListByRoleName(sysRole);
		return roles==null?(new ArrayList<SysRole>()):roles;
	}

	@Override
	public int deleteSysPermisOfRole(Map<String, String> parameter) throws ThriftServiceException, TException {
		Map<String, Object> map = new HashMap<String, Object>();
		int roleId = Integer.parseInt(parameter.get("roleId"));
		int permisId = Integer.parseInt(parameter.get("permisId"));
		map.put("roleId", roleId);
		map.put("permisId", permisId);
		return systemUserRoleMapper.deletePermisOfRoleByPid(map);
	}

	@Override
	public List<SysPermission> searchRoleNotHavePermis(SysRole sysRole,String permisName) throws ThriftServiceException, TException {
		List<SysPermission> permissions = systemUserRoleMapper.searchRoleNotHavePermis(sysRole,permisName);
		return permissions==null?(new ArrayList<SysPermission>()):permissions;
	}

	@Override
	public int deletePermisOfRoleById(Map<String, String> parameter) throws ThriftServiceException, TException {
		int roleId = Integer.parseInt(parameter.get("roleId"));
		int permisId = Integer.parseInt(parameter.get("permisId"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("permisId", permisId);

		return systemUserRoleMapper.deletePermisOfRoleByPid(map);
	}

	@Override
	public int addPermisToRole(Map<String, String> parameter) throws ThriftServiceException, TException {
		int roleId = Integer.parseInt(parameter.get("roleId"));
		int permisId = Integer.parseInt(parameter.get("permisId"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("permisId", permisId);
		// 防止重复插入
		int count = systemUserRoleMapper.searchRolePermisRalationByIds(map);
		if (count == 0) {
			return systemUserRoleMapper.addRolePermisRelation(map);
		}
		return 0;
	}


	@Override 
	public  List<SysUser> getRoleUserListByRoleId(SysRole sysRole,String realName) throws ThriftServiceException, TException {
		List<SysUser> list = null;
		try {
			list = systemUserRoleMapper.getSysUserByRoleId(sysRole,realName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list==null?(new ArrayList<SysUser>()):list;
	}
	
	@Override
	public int getSysUserByRoleIdCount(SysRole sysRole,String realName) throws ThriftServiceException, TException {
		return systemUserRoleMapper.getSysUserByRoleIdCount(sysRole,realName);
	}
	
	
	
	@Override
	public int delUserToRole(Map<String, String> parameter) throws ThriftServiceException, TException {
		int roleId = Integer.parseInt(parameter.get("roleId"));
		int userId = Integer.parseInt(parameter.get("userId"));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("roleId", roleId);
		map.put("userId", userId);
		return systemUserRoleMapper.delUserToRole(map);
	}

	@Override
	public boolean addRoleToUser(int userId, List<Integer> roleId)
			throws ThriftServiceException, TException {
		boolean status = false;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("userId", userId);
		for (Integer integer : roleId) {
			map.put("roleId", integer);
			status = systemUserRoleMapper.addRoleToUser(map);
		}
		return status;
	}
	
	@Override
	public boolean addUserToRole(int roleId, List<Integer> userId)
			throws ThriftServiceException, TException {
		boolean status = false;
		Map<String, Integer> map = new HashMap<String, Integer>();
		map.put("roleId", roleId);
		for (Integer integer : userId) {
			map.put("userId", integer);
			status = systemUserRoleMapper.addRoleToUser(map);
		}
		return status;
	}

	@Override
	public List<SysRole> getRoleByUserId(int pageNum, int pageSize,
			SysUser sysUser) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		map.put("userId", sysUser.getPid());
		List<SysRole> roles = systemUserRoleMapper.getRoleByUserId(map);
		return roles==null?(new ArrayList<SysRole>()):roles;
	}

	@Override
	public List<SysRole> searchUserNotRoleList(SysUserRoleSearch sysUserRoleSearch) throws ThriftServiceException, TException {
		List<SysRole> list =systemUserRoleMapper.searchUserNotRoleList(sysUserRoleSearch);
		return list==null?(new ArrayList<SysRole>()):list;
	}
	
	@Override
	public int searchUserNotRoleListCount(SysUserRoleSearch sysUserRoleSearch) throws ThriftServiceException, TException {
		return systemUserRoleMapper.searchUserNotRoleListCount(sysUserRoleSearch);
	}
	
	@Override
	public List<SysUser> getRoleNoUserList(SysUserRoleSearch sysUserRoleSearch) throws ThriftServiceException, TException {
		List<SysUser> users = systemUserRoleMapper.getRoleNoUserList(sysUserRoleSearch);
		return users==null?(new ArrayList<SysUser>()):users;
	}
	
	@Override
	public int getRoleNoUserListCount(SysUserRoleSearch sysUserRoleSearch) throws ThriftServiceException, TException {
		return systemUserRoleMapper.getRoleNoUserListCount(sysUserRoleSearch);
	}
	

	@Override
	public List<SysUserDto> getUserListByRoleCode(SysRole sysRole) throws TException {
		List<SysUserDto> userDtos = systemUserRoleMapper.getUserListByRoleCode(sysRole);
		return userDtos==null?(new ArrayList<SysUserDto>()):userDtos;
	}

	@Override
	public int getAllRolesForPageCount() throws TException {
		return systemUserRoleMapper.slectAllRoleCountForPage();
	}

	@Override
	public int searchRoleListCount(SysRole sysRole) throws TException {
		return systemUserRoleMapper.searchRoleListByRoleNameCount(sysRole);
	}

	@Override
	public int searchRoleNotHavePermisCount(SysRole sysRole,String permisName)
			throws ThriftServiceException, TException {
		return systemUserRoleMapper.searchRoleNotHavePermisCount(sysRole,permisName);
	}

	@Override
	public int getSysPermissionListCount(SysRole sysRole,String permisName)
			throws ThriftServiceException, TException {
		return systemUserRoleMapper.getPerMisByRoleIdCount(sysRole,permisName);
	}

	@Override
	public List<SysRole> queryRolesByRoleCodes(List<String> roleCodes)
			throws ThriftServiceException, TException {
		List<SysRole> roles = systemUserRoleMapper.queryRolesByRoleCodes(roleCodes);
		return roles==null?(new ArrayList<SysRole>()):roles;
	}

   @Override
   public SysRole getRoleById(int pid) throws TException {
      return systemUserRoleMapper.getRoleById(pid);
   }

	@Override
	public List<SysRole> getRoleIdListByUserName(String userName,int pageNum, int pageSize) throws TException {
		List<SysRole> roleIdList = systemUserRoleMapper.getRoleIdListByUserName(userName,pageNum,pageSize);
		return roleIdList==null?(new ArrayList<SysRole>()):roleIdList;
	}

	@Override
	public int getRoleIdListByUserNameCount(String userName) throws TException {
		int count = systemUserRoleMapper.getRoleIdListByUserNameCount(userName);
		return count;
	}

}
