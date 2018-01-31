/**
 * @Title: SysMenuServiceImpl.java
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysMenu;
import com.xlkfinance.bms.rpc.system.SysMenuService.Iface;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.server.system.mapper.SysMenuMapper;
import com.xlkfinance.bms.server.system.mapper.SystemPermisMapper;

/**
  * @ClassName: SysMenuServiceImpl
  * @Description: 菜单
  * @author: JingYu.Dai
  * @date: 2015年7月29日 上午11:06:55
 */
@SuppressWarnings("unchecked")
@Service("sysMenuServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysMenuService")
public class SysMenuServiceImpl implements Iface{
	@SuppressWarnings("rawtypes")
	@Resource(name = "sysMenuMapper")
	private SysMenuMapper sysMenuMapper;
	@SuppressWarnings("rawtypes")
	@Resource(name = "systemPermisMapper")
	private SystemPermisMapper systemPermisMapper;
	
	/**
	  * @Description: 转换菜单数据转换成权限数据
	  * @param sysMenu
	  * @return SysPermission
	  * @author: JingYu.Dai
	  * @date: 2015年4月16日 上午10:33:58
	  */
	private SysPermission convert(SysMenu sysMenu){
		SysPermission sysPermission = new SysPermission();
		sysPermission.setPermisCode("MNU");
		sysPermission.setPermisType("MNU");
		sysPermission.setPermisDesc(sysMenu.getMenuName());
		sysPermission.setPermisName(sysMenu.getMenuName());
		sysPermission.setMenuId(sysMenu.getPid());
		return sysPermission;
	}
	
	 /**
	   * @Description: 增加权限
	   * @param sysPermission
	   * @return
	   * @author: Chong.Zeng
	   * @date: 2015年1月30日 下午3:06:40
	   */
	@Override
	public int addSysMenu(SysMenu sysMenu) throws ThriftServiceException,
			TException {
		sysMenuMapper.insert(sysMenu);
		return systemPermisMapper.addPermission(convert(sysMenu));
	}

	/**
	 * @Description: 删除菜单
	 * @param parameter,map
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	@Override
	public int deleteSysMenu(SysMenu sysMenu) throws ThriftServiceException,
			TException {
		String [] menuId = sysMenu.getMenuName().split(",");
		int del = 0;
		for (String pid : menuId) {
			//如果是该节点有字节点 先删除父节点
			SysMenu s = new SysMenu();
			s.setPid(Integer.parseInt(pid.trim()));
			// 删除权限 (根据菜单ID)
			systemPermisMapper.deleteSysPermissionByMenuId(convert(s));
			//删除菜单
			del = sysMenuMapper.delete(s);
		}
		return del;
	}

	/**
	 * @Description: 修改菜单
	 * @param parameter,map
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	@Override
	public int updateSysMenu(SysMenu sysMenu) throws ThriftServiceException,
			TException {
		// 修改权限信息 （根据菜单ID）
		systemPermisMapper.updateSysPermissionByMenuId(convert(sysMenu));
		//修改下拉框专用
		return sysMenuMapper.updateByPrimaryKey(sysMenu);
	}

	/**
	 * @Description: 下拉框专用
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月25日 下午15:06:02
	 */
	@Override
	public List<SysMenu> getSysMenuList() throws TException {
		return sysMenuMapper.getComBoxList();
	}

	/**
	 * @Description: 分页查询系统菜单
	 * @param parameter,map
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月23日 下午17:06:02
	 */
	@Override
	public String getMenuPageList(Map<String, String> parameter)
			throws TException {
		List<SysMenu> list = sysMenuMapper.getMenuPageList();
		JSONArray rootArray = new JSONArray();
	    for (int i=0; i<list.size(); i++) {
	      SysMenu menu = list.get(i);
	      if (menu.getParentId() == 0) {
	        JSONObject rootObj = creatList(list, menu);
	        rootArray.add(rootObj);
	      }
	    }
	    
		return rootArray.toJSONString();
	}
	/**
	 * @Description: 分页模糊查询系统菜单
	 * @param parameter,map
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月23日 下午17:06:02
	 */
	@Override
	public List<SysMenu> searchMenuListByName(Map<String, String> parameter)
			throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		int pageNum = Integer.parseInt(parameter.get("page"));
		int pageSize = Integer.parseInt(parameter.get("rows")) ;
		String menuName = parameter.get("menuName");
		map.put("pageNum", pageNum);
		map.put("pageSize", pageSize);
		map.put("menuName", menuName);
		List<SysMenu> list = sysMenuMapper.searchMenuListByName(map);
		return list==null?(new ArrayList<SysMenu>()):list;
	}
	
	/**
	 * @Description: 获取所有根节点
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	@Override
	public String getTreeMenu(Map<String, String> parameter) throws TException {
		List<SysMenu> list = sysMenuMapper.getMenuTreeList(parameter);
		JSONArray rootArray = new JSONArray();
	    for (int i=0; i<list.size(); i++) {
	      SysMenu menu = list.get(i);
	      if (menu.getParentId() == 0) {
	        JSONObject rootObj = creatList(list, menu);
	        rootArray.add(rootObj);
	      }
	    }
		return rootArray.toJSONString();
	}
	
	/**
	 * 封装父节点下的字节点
	 * @author Chong.Zeng
	 * @param list
	 * @param menu
	 * @return
	 */
	public static JSONObject creatList (List<SysMenu> list,SysMenu menu){
		JSONObject object = (JSONObject) JSONObject.toJSON(menu);
		JSONArray childArray = new JSONArray();
		for (int i = 0; i < list.size(); i++) {
			SysMenu cm = list.get(i);
			if (String.valueOf(cm.getParentId())!=null && String.valueOf(cm.getParentId()).compareTo(String.valueOf(menu.getPid())) == 0) {
		        JSONObject childObj = creatList(list, cm);
		        childArray.add(childObj);
		}
			if (!childArray.isEmpty()) {
				object.put("children", childArray);
			    }
		}
		return object;
	}

	/**
	  * @Description: 查询所有跟菜单
	  * @return List<SysMenu>
	  * @author: JingYu.Dai
	  * @date: 2015年5月5日 下午2:37:08
	  */
	@Override
	public List<SysMenu> getRootMenuList(){
		List<SysMenu> list = sysMenuMapper.getRootMenuList();
		return list==null?(new ArrayList<SysMenu>()):list;
	}
	
	/**
	  * @Description: 根据pid 查询当前菜单下的子菜单
	  * @param pid 
	  * @return List<SysMenu>
	  * @author: JingYu.Dai
	  * @date: 2015年5月5日 下午2:54:52
	 */
	@Override
	public List<SysMenu> selectChildMenuByPid(int pid){
		List<SysMenu> list = sysMenuMapper.selectChildMenuByPid(pid);
		return list==null?(new ArrayList<SysMenu>()):list;
	}

}
