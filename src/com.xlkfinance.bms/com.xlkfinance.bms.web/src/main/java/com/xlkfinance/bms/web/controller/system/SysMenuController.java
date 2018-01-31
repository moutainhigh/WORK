/**
 * @Title: SysMenuController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月13日 上午11:01:09
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.SysMenu;
import com.xlkfinance.bms.rpc.system.SysMenuService;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
 * @ClassName: SysMenuController
 * @Description: 菜单管理（添加，维护，删除），菜单展示（树型菜单），Win8磁贴菜单（Metro)
 * @author: Simon.Hoo
 * @date: 2014年12月13日 上午11:01:44
 */
@Controller
@RequestMapping("/sysMenuController")
public class SysMenuController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SysMenuController.class);
	private String id;

	/**
	 * 菜单树输出
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/queryMenuTree")
	public void queryMenuTree(HttpServletRequest request, HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
			SysUserService.Client client = (SysUserService.Client) clientFactory.getClient();

			ShiroUser shiroUser = getShiroUser();
			String userName = shiroUser.getUserName();

			List<SysMenu> menuList = client.getPermissionMenuByUserName(userName);
			List<TreeMenuDTO> menuDtoList = Lists.newArrayList();

			for (SysMenu sysMenu : menuList) {
				TreeMenuDTO dto = new TreeMenuDTO();
				dto.setId(sysMenu.getPid());
				dto.setpId(sysMenu.getParentId());
				dto.setName(sysMenu.getMenuName());
				dto.setUrl(sysMenu.getMenuUrl());
				dto.setIconCls(sysMenu.getIconCls());
				dto.setOpen(sysMenu.getParentId() == 0 && sysMenu.getMenuIndex() == 1 ? true : false);
				menuDtoList.add(dto);
			}

			outputJson(menuDtoList, response);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
			e.printStackTrace();
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 跳转到菜单管理页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "toMenuMain")
	public String toMenuList() {
		return "system/menu_list";
	}

	/**
	 * 查询所有系统菜单
	 * 
	 * @author Chong.Zeng
	 * @param response
	 * @param page
	 * @param rows
	 */
	@RequestMapping(value = "pageMenulist", method = RequestMethod.POST)
	public void pageMenulist(HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
		try {
			SysMenuService.Client c = (SysMenuService.Client) clientFactory.getClient();
			Map<String, String> map = new HashMap<String, String>();
			map.put("page", String.valueOf(0));
			map.put("rows", String.valueOf(0));
			String list = c.getMenuPageList(map);
			response.setContentType("text/html;charset=utf-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter pw = response.getWriter();
			// 输出JSON字符串
			pw.print(list);
			pw.flush();
			pw.close();
		} catch (Exception e) {
			logger.error("SystemMenuController.pageMenulist Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 模糊查询菜单列表
	 * 
	 * @author Chong.Zeng
	 * @param response
	 * @param page
	 * @param rows
	 * @param menuName
	 */
	@RequestMapping(value = "searchMenu", method = RequestMethod.POST)
	public void searchMenu(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("menuName") String menuName) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
		try {
			SysMenuService.Client c = (SysMenuService.Client) clientFactory.getClient();
			Map<String, String> map = new HashMap<String, String>();
			map.put("page", String.valueOf(page));
			map.put("rows", String.valueOf(rows));
			map.put("menuName", menuName);
			outputJson(c.searchMenuListByName(map), response);
		} catch (Exception e) {
			logger.error("SystemMenuController.searchMenu Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除菜单
	 * 
	 * @author Chong.Zeng
	 * @param response
	 * @param menuId
	 */
	@RequestMapping(value = "deleteMenu", method = RequestMethod.POST)
	public void deleteMenu(HttpServletResponse response, @RequestParam("menuId") String menuId) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
			SysMenuService.Client c = (SysMenuService.Client) clientFactory.getClient();
			SysMenu sysMenu = new SysMenu();
			sysMenu.setMenuName(menuId);
			int flag = c.deleteSysMenu(sysMenu);
			outputJson(flag, response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemMenuController.searchMenu Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取所有菜单
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "menulist", method = RequestMethod.POST)
	public void menulist(HttpServletResponse response) {
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
			SysMenuService.Client client = (SysMenuService.Client) clientFactory.getClient();
			List<SysMenu> list = client.getSysMenuList();
			for (SysMenu sysMenu : list) {
				sysMenu.setMenuName(sysMenu.getMenuName().replace(" ", "&nbsp;"));
			}
			outputJson(list, response);
		} catch (Exception e) {
			logger.error("SystemMenuController.getTreeMenu Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 新增
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addmenu", method = RequestMethod.POST)
	public void addmenu(HttpServletResponse response, @RequestParam("menuName") String menuName, @RequestParam("parentId") int parentId, @RequestParam("iconCls") String iconCls, @RequestParam("menuUrl") String menuUrl, @RequestParam("menuIndex") int menuIndex) {
		int flag = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
			SysMenuService.Client client = (SysMenuService.Client) clientFactory.getClient();
			SysMenu sysMenu = new SysMenu();
			sysMenu.setMenuUrl(menuUrl);
			sysMenu.setIconCls(iconCls);
			sysMenu.setParentId(parentId);
			sysMenu.setMenuName(menuName);
			sysMenu.setStatus(1);
			sysMenu.setMenuIndex(menuIndex);
			flag = client.addSysMenu(sysMenu);
			outputJson(flag, response);
		} catch (Exception e) {
			outputJson(flag, response);
			logger.error("SystemMenuController.getTreeMenu Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 新增
	 * 
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateMenu", method = RequestMethod.POST)
	public void updateMenu(HttpServletResponse response, @RequestParam("menuName") String menuName, @RequestParam("parentId") int parentId, @RequestParam("iconCls") String iconCls, @RequestParam("menuUrl") String menuUrl, @RequestParam("menuIndex") int menuIndex, @RequestParam("pid") int pid) {
		int flag = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
			SysMenuService.Client client = (SysMenuService.Client) clientFactory.getClient();
			SysMenu sysMenu = new SysMenu();
			sysMenu.setMenuUrl(menuUrl);
			sysMenu.setIconCls(iconCls);
			sysMenu.setParentId(parentId);
			sysMenu.setMenuName(menuName);
			sysMenu.setPid(pid);
			sysMenu.setStatus(1);
			sysMenu.setMenuIndex(menuIndex);
			flag = client.updateSysMenu(sysMenu);
			outputJson(flag, response);
		} catch (Exception e) {
			outputJson(flag, response);
			logger.error("SystemMenuController.getTreeMenu Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

	}
	/**
	  * @Description: 查询所有跟菜单
	  * @param response
	  * @author: JingYu.Dai
	  * @date: 2015年5月5日 下午3:18:08
	 */
	@RequestMapping(value = "getRootMenuList")
	public void getRootMenuList(HttpServletResponse response) {
		List<SysMenu> list = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
			SysMenuService.Client client = (SysMenuService.Client) clientFactory.getClient();
			list = client.getRootMenuList();
			outputJson(list, response);
		} catch (Exception e) {
			logger.error("SystemMenuController.selectChildMenuByPid Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	  * @Description: 查询子菜单 根据菜单表pid
	  * @param response
	  * @param pid
	  * @author: JingYu.Dai
	  * @date: 2015年5月5日 下午3:18:42
	  */
	@RequestMapping(value = "selectChildMenuByPid")
	public void selectChildMenuByPid(HttpServletResponse response, int pid) {
		List<SysMenu> list = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMenuService");
			SysMenuService.Client client = (SysMenuService.Client) clientFactory.getClient();
			list = client.selectChildMenuByPid(pid);
			outputJson(list, response);
		} catch (Exception e) {
			logger.error("SystemMenuController.getTreeMenu Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
