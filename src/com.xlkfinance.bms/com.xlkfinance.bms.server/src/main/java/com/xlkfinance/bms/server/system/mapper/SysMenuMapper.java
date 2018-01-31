/**
 * @Title: SysUserMapper.java
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
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysMenu;

@MapperScan("sysMenuMapper")
public interface SysMenuMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * @Description: 查询用户所拥有所所有菜单
	 * @param userName
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2014年12月22日 下午7:07:29
	 */
	public List<SysMenu> selectPermissionMenuByUserName(String userName);

	/**
	 * 
	 * @Description: 查询根菜单
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2014年12月16日 下午3:09:16
	 */
	public List<SysMenu> selectRootMenuByUser(String userName);

	/**
	 * @Description: 查询下一级菜单
	 * @param parameter
	 *            , USER_ID, PARENT_ID
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2014年12月16日 下午4:06:02
	 */
	public List<SysMenu> selectChildMenuByUser(Map<String, Object> parameter);
	
	/**
	 * @Description: 分页查询系统菜单
	 * @param parameter,map
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月23日 下午17:06:02
	 */
	public List<SysMenu> getMenuPageList();
	
	/**
	 * @Description: 分页模糊查询系统菜单
	 * @param parameter,map
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月23日 下午17:06:02
	 */
	public List<SysMenu> searchMenuListByName(Map<String, Object> map);
	
	/**
	 * @Description: 删除菜单
	 * @param parameter,map
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	public int delete(SysMenu menu);
	
	/**
	 * @Description: 获取所有根节点
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	public List<SysMenu> getMenuTreeList(Map<String, Object> map);

	/**
	 * @Description: 新增菜单
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	public int  insert(SysMenu sysMenu);
	
	
	
	//public int  getMenuSeq();
	
	/**
	 * @Description: 下拉框专用
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月25日 下午15:06:02
	 */
	public List<SysMenu> getComBoxList();
	
	
	/**
	 * @Description: 下拉框专用
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2014年12月25日 下午15:06:02
	 */
	public int updateByPrimaryKey(SysMenu sysMenu);
	
	/**
	  * @Description: 根据pid 查询子菜单
	  * @return List<SysMenu>
	  * @author: JingYu.Dai
	  * @date: 2015年5月5日 下午2:45:48
	 */
	public List<SysMenu> selectChildMenuByPid(int pid);
	
	/**
	 * @Description: 获取所有根节点
	 * @return
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	public List<SysMenu> getRootMenuList();
}
