package com.xlkfinance.bms.server.system.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserDto;
import com.xlkfinance.bms.rpc.system.SysUserRoleSearch;


@MapperScan("systemUserRoleMapper")
public interface SystemUserRoleMapper<T,PK> extends BaseMapper<T, PK>{
	 
	/**
	 * 
	  * @Description: 查询所有角色 不分页
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 上午11:58:20
	 */
	public List<SysRole> getAllRoles();
	
	/**
	 * 
	  * @Description: 分页查询所有角色
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 上午11:58:40
	 */
	public List<SysRole> getAllRolesForPage(SysRole sysRole);
	
	/**
	 * 
	  * @Description: 模糊查询角色
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 上午11:59:03
	 */
	public List<SysRole> searchRoleListByRoleName(SysRole sysRole);
	
	/**
	 * 
	  * @Description: 新增角色
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 上午11:59:19
	 */
	public int addSysRole(SysRole sysRole);
	
	/**
	 * 
	  * @Description: 根据角色Id查询角色下用户
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 上午11:59:30
	 */
	public List<SysUser> getSysUserByRoleId(@Param(value = "sysRole")SysRole sysRole, @Param(value = "realName")String realName);
	
	/**
	 * 
	  * @Description: 根据角色Id查询角色下用户总数
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 上午11:59:30
	 */
	int getSysUserByRoleIdCount(@Param(value = "sysRole")SysRole sysRole, @Param(value = "realName")String realName);
	
	/**
	 * 
	  * @Description: 根据角色ID查询角色没有的权限
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	 * @param permisName 
	  * @date: 2015年1月30日 下午12:00:02
	 */
	public List<SysPermission> searchRoleNotHavePermis(@Param(value = "sysRole")SysRole sysRole, @Param(value = "permisName")String permisName);
	
	/**
	 * 
	  * @Description: 删除角色
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:46:20
	 */
	public int deleteRole(Map<String,Long> map);
	
	/**
	 * 
	  * @Description: 删除角色和权限的关系
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:47:03
	 */
	public int deleteRoleAndPermisRalation(Map<String,Long> map);
	
	/**
	 * 
	  * @Description: 删除用户和角色的关系
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:47:20
	 */
	public int deleteRoleAndUserRalation(Map<String,Long> map);
	
	public int selectSysRoleSeq();
	
	/**
	  * @Description: 根据角色ID查询权限
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:47:39
	 */
	public List<SysPermission> getPermisByRoleId(@Param(value = "sysRole")SysRole sysRole, @Param(value = "permisName")String permisName);
	
	/**
	  * @Description: 根据角色ID删除其权限
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:47:58
	 */
	public int deletePermisOfRoleByPid(Map<String,Object> map);
	
	/**
	  * @Description: 查询所有角色记录数
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:48:23
	  */
	public int slectAllRoleCountForPage();
	
	/**
	  * @Description: 根据角色ID查询权限记录数
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:48:53
	  */
	public int getPerMisByRoleIdCount(@Param(value = "sysRole")SysRole sysRole, @Param(value = "permisName")String permisName);
	
	/**
	  * @Description: 根据角色ID查询其没有的权限
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	 * @param permisName 
	  * @date: 2015年1月30日 下午2:50:00
	  */
	public int searchRoleNotHavePermisCount(@Param(value = "sysRole")SysRole sysRole, @Param(value = "permisName")String permisName);
	
	/**
	  * @Description: 模糊查询的记录数
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:50:26
	  */
	public int searchRoleListByRoleNameCount(SysRole sysRole);
	
	/**
	  * @Description: 根据角色ID更新角色信息
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:50:52
	 */
	public int updateRoleById(SysRole sysRole);
	
	/**
	  * @Description: 跟角色添加权限
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:51:13
	  */
	public int addRolePermisRelation(Map<String,Object> map);
	
	/**
	  * @Description: 删除角色下面的用户
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:51:40
	  */
	public int delUserToRole(Map<String,Object> map);
	
	/**
	  * @Description: 根据权限ID和角色ID查询记录数
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:51:56
	  */
	public int searchRolePermisRalationByIds(Map<String,Object> map);
	
	/**
	  * @Description: 根据角色CODE查询记录数
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:55:24
	  */
	public int getCountByRoleCode(SysRole sysRole);
	
	/**
	  * @Description: 根据用户ID查询角色
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:56:38
	  */
	public List<SysRole> getRoleByUserId(Map<String,Object> map);
	/**
	 * 
	 *@author:liangyanjun
	 *@time:2016年3月24日下午3:34:06
	 *@param map
	 *@return
	 */
	public List<SysRole> getRolesByUserId(Map<String,Object> map);
	
	/**
	  * @Description: 查询此用户下没有的角色列表
	  * @param sysUserRoleSearch
	  * @return List<SysRole>
	  * @author: Dai.jingyu
	  * @date: 2015年3月16日 上午11:39:04
	  */
	public List<SysRole> searchUserNotRoleList(SysUserRoleSearch sysUserRoleSearch);
	
	/**
	  * @Description: 查询此用户下没有的角色总记录条数
	  * @param sysUserRoleSearch
	  * @return int
	  * @author: Dai.jingyu
	  * @date: 2015年3月16日 上午11:40:19
	  */
	public int searchUserNotRoleListCount(SysUserRoleSearch sysUserRoleSearch);
	
	/**
	  * @Description: 增加角色用户的关系
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:58:17
	  */
	public boolean addRoleToUser(Map<String,Integer> map);
	
	/**
	  * @Description: 查询角色没有的用户
	  * @param map
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:59:34
	  */
	public List<SysUser> getRoleNoUserList(SysUserRoleSearch sysUserRoleSearch);
	
	/**
	  * @Description: 查询角色没有的用户的总记录条数
	  * @param pid 角色ID
	  * @return int
	  * @author: Dai.jingyu
	  * @date: 2015年3月12日 下午2:04:27
	  */
	public int getRoleNoUserListCount(SysUserRoleSearch sysUserRoleSearch);
	
	/**
	  * @Description: 根据觉得编码查询用户列表
	  * @param sysRole
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午2:59:56
	  */
	public List<SysUserDto> getUserListByRoleCode(SysRole sysRole);
	
	/**
	 * 
	  * @Description: 根据角色用户关系ID查询记录数
	  * @param pid
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午3:00:58
	  */
	public int searchRoleCountByRoleUserId(int pid);
	
	/**
	 * 根据角色code查询角色列表
	 * @param roleCodes
	 * @return List<SysRole>
	 */
	public List<SysRole> queryRolesByRoleCodes(List<String> roleCodes);

   /**
    * 根据角色id查询角色信息
    *@author:liangyanjun
    *@time:2016年3月24日下午3:02:27
    *@param pid
    *@return
    */
   public SysRole getRoleById(int pid);
   /**
    * 根据角色code查询角色信息
    *@author:liangyanjun
    *@time:2017年2月6日下午5:21:34
    *@param roleCode
    *@return
    */
   public SysRole getRoleByCode(String roleCode);
   
	/**
	 * 
	  * @Description: 根据角色用户关系ID查询记录数
	  * @param userName
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午3:00:58
	  */
	public List<SysRole> getRoleIdListByUserName(@Param(value = "userName")String userName,@Param(value = "pageNum")int pageNum,@Param(value = "pageSize")int pageSize);

	/**
	 * 
	  * @Description: 根据角色用户关系ID查询记录数总数字
	  * @param userName
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午3:00:58
	  */
	public int getRoleIdListByUserNameCount(@Param(value = "userName")String userName);
	
}
