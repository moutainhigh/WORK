package com.xlkfinance.bms.server.system.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysRole;


@MapperScan("systemPermisMapper")
public interface SystemPermisMapper<T,PK> extends BaseMapper<T, PK>{
	
	/**
	 * 
	  * @Description: 分页查询权限
	  * @param permission
	  * @return
	  * @author: Chong.Zeng
	  * @date: 2015年1月30日 下午3:06:00
	 */
	 public List<SysPermission> getAllPermisForPage(SysPermission permission);
	 
	 /**
	  * 
	   * @Description: 按条件分页查询权限
	   * @param permission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:06:16
	  */
	 public List<SysPermission> searchPermisByLike(SysPermission permission);
	 
	 /**
	   * @Description: 增加权限
	   * @param sysPermission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:06:40
	   */
	 public int addPermission(SysPermission sysPermission);
	 
	 /**
	  * 
	   * @Description: 修改权限信息
	   * @param sysPermission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:06:52
	  */
	 public int updatePermisInfo(SysPermission sysPermission);
	 
	 /**
	   * @Description: 修改权限信息 （根据菜单ID）
	   * @return int
	   * @author: JingYu.Dai
	   * @date: 2015年4月16日 上午10:07:33
	   */
	 public int updateSysPermissionByMenuId(SysPermission sysPermission);
	 
	 /**
	  * 
	   * @Description: 删除权限角色关系
	   * @param sysPermission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:07:02
	  */
	 public int deletePermissionOfRoleRalation(SysPermission sysPermission);
	 
	 /**
	  * 
	   * @Description: 删除权限
	   * @param sysPermission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:07:16
	  */
	 public int deletePermission(SysPermission sysPermission);
	 
	 /**
	   * @Description: 删除权限 (根据菜单ID)
	   * @param sysPermission
	   * @return
	   * @author: JingYu.Dai
	   * @date: 2015年4月16日 上午10:09:39
	   */
	 public int deleteSysPermissionByMenuId(SysPermission sysPermission);
	 /**
	  * 
	   * @Description: 查询权限下的用户
	   * @param map
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:07:26
	  */
	 public List<SysRole> getRoleOfPermisList(Map<String, Object> map);
	 
	 /**
	  * 
	   * @Description: 获取用户没有的权限
	   * @param map
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:07:45
	  */
	 public List<SysRole> getNotHaveRoleOfPermisList(Map<String, Object> map); 
	 
	 /**
	  * 
	   * @Description: 增加权限角色关系
	   * @param map
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:07:56
	  */
	 public int addRolePermisRelation(Map<String, Object> map);
	 
	 /**
	  * 
	   * @Description: 删除角色权限关系
	   * @param map
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:08:07
	  */
	 public int deleteRoleOfPermisByPid(Map<String, Object> map);
	 
	 /**
	  * 
	   * @Description: 获取所有权限记录数
	   * @param permission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:08:18
	  */
	 public int getAllPermisCount(SysPermission permission);
	 
	 /**
	   * @Description: 按条件查询的记录数
	   * @param permission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:08:31
	   */
	 public int searchPermisByLikeCount(SysPermission permission);
	 
	 /**
	   * @Description: 根据权限名称   查询权限记录
	   * @param permisName 权限名称
	   * @return SysPermission
	   * @author: Dai.jingyu
	   * @date: 2015年3月25日 下午4:44:08
	  */
	 public SysPermission getPermisByName(String permisName);
	 /**
	   * @Description: 总数
	   * @param permisName 权限名称
	   * @return SysPermission
	   * @author: Jony
	   * @date: 2015年3月25日 下午4:44:08
	  */
	 public int getPermisRoleListTotal(SysPermission sysPermission);
}
