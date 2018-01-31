/**
 * @Title: SystemPermisController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2014年12月19日 上午11:01:09
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysPermissionService;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.web.controller.BaseController;

@Controller
@RequestMapping("/systemPermisController")
public class SystemPermisController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SystemRoleController.class);

	@RequestMapping(value = "toPermisMain")
	public String main(HttpServletResponse response) {
		return "system/permis_list";
	}

	/**
	 * 查询所有的权限
	 * @param response
	 * @param page
	 * @param rows
	 */
	@RequestMapping(value = "permislist", method = RequestMethod.POST)
	public void permislist(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permis = new SysPermission();
			permis.setPage(page);
			permis.setRows(rows);
			List<SysPermission> list = c.getSysPermissionByParameter(permis);
			int count = c.getSysPermissionCount(permis);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("SystemPermisController.permislist Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 模糊查询权限
	 * 
	 * @param response
	 * @param page
	 * @param rows
	 */
	@RequestMapping(value = "searchPermisByLike", method = RequestMethod.POST)
	public void searchPermisByLike(HttpServletResponse response, 
			@RequestParam("page") int page, 
			@RequestParam("rows") int rows, 
			@RequestParam("permisName") String permisName, 
			@RequestParam("permisType") String permisType, 
			@RequestParam("permisDesc") String permisDesc, 
			@RequestParam("permisCode") String permisCode) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permis = new SysPermission();
			permis.setPage(page);
			permis.setRows(rows);
			permis.setPermisCode(permisCode);
			permis.setPermisDesc(permisDesc);
			permis.setPermisName(permisName);
			permis.setPermisType(permisType);
			List<SysPermission> list = c.getSysPermissionList(permis);
			int count = c.srarchSysPermissionCount(permis);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("SystemPermisController.permislist Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 新增权限
	 * 
	 * @param response
	 * @param permisName
	 * @param permisType
	 * @param permisDesc
	 * @param permisCode
	 */
	@RequestMapping(value = "addpermis", method = RequestMethod.POST)
	public void addpermis(HttpServletResponse response, @RequestParam("permisName") String permisName, @RequestParam("permisType") String permisType, @RequestParam("permisDesc") String permisDesc, @RequestParam("permisCode") String permisCode) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permis = new SysPermission();
			permis.setPermisCode(permisCode);
			permis.setPermisDesc(permisDesc);
			permis.setPermisName(permisName);
			permis.setPermisType(permisType);
			c.addSysPermission(permis);
			outputJson("success", response);
		} catch (Exception e) {
			logger.error("SystemPermisController.addpermis Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 更新权限信息
	 * 
	 * @param response
	 * @param permisName
	 * @param permisType
	 * @param permisDesc
	 * @param permisCode
	 * @param pid
	 */
	@RequestMapping(value = "updatepermis", method = RequestMethod.POST)
	public void updatepermis(HttpServletResponse response, @RequestParam("permisName") String permisName, @RequestParam("permisType") String permisType, @RequestParam("permisDesc") String permisDesc, @RequestParam("permisCode") String permisCode, @RequestParam("pid") int pid) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permis = new SysPermission();
			permis.setPermisCode(permisCode);
			permis.setPermisDesc(permisDesc);
			permis.setPermisName(permisName);
			permis.setPermisType(permisType);
			permis.setPid(pid);
			c.updateSysPermission(permis);
			outputJson("success", response);
		} catch (Exception e) {
			logger.error("SystemPermisController.addpermis Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除一个权限
	 * 
	 * @param response
	 * @param pid
	 */
	@RequestMapping(value = "deletePermis", method = RequestMethod.POST)
	public void deletePermis(HttpServletResponse response, @RequestParam("pid") int pid) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permis = new SysPermission();
			permis.setPid(pid);
			c.deleteSysPermission(permis);
			outputJson("success", response);
		} catch (Exception e) {
			logger.error("SystemPermisController.deletePermis Exception" + e.getMessage());
		}
	}

	/**
	 * 查询权限下的角色
	 * 
	 * @param response
	 * @param page
	 * @param rows
	 * @param pid
	 */
	@RequestMapping(value = "getRoleOfPermisList", method = RequestMethod.POST)
	public void getRoleOfPermisList(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("pid") int pid) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permis = new SysPermission();
			permis.setPid(pid);
			List<SysRole> list = c.getPermisRoleList(page, rows, permis);
			int total = c.getPermisRoleListTotal(permis);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("rows", list);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("SystemPermisController.getRoleOfPermisList Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 查询权限下没有的角色
	 * 
	 * @param response
	 * @param page
	 * @param rows
	 * @param pid
	 */
	@RequestMapping(value = "getNotHaveRoleOfPermisList", method = RequestMethod.POST)
	public void getNotHaveRoleOfPermisList(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("pid") int pid) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permis = new SysPermission();
			permis.setPid(pid);
			List<SysRole> list = c.getNotHaveRoleOfPermisList(page, rows, permis);
			outputJson(list, response);
		} catch (Exception e) {
			logger.error("SystemPermisController.getRoleOfPermisList Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 删除权限角色关系
	 * 
	 * @param response
	 * @param roleId
	 * @param permisId
	 */
	@RequestMapping(value = "deleteRoleToPermis", method = RequestMethod.POST)
	public void deleteRoleToPermis(HttpServletResponse response, @RequestParam("roleId") int roleId, @RequestParam("permisId") int permisId) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			Map<String, String> map = new HashMap<String, String>();
			map.put("roleId", String.valueOf(roleId));
			map.put("permisId", String.valueOf(permisId));
			c.deleteRoleOfPermisById(map);
			outputJson("success", response);
		} catch (Exception e) {
			logger.error("SystemPermisController.deleteRoleToPermis Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 增加权限角色关系
	 * 
	 * @param response
	 * @param roleId
	 * @param permisId
	 */
	@RequestMapping(value = "addRoleToPermis", method = RequestMethod.POST)
	public void addRoleToPermis(HttpServletResponse response, @RequestParam("roleId") int roleId, @RequestParam("permisId") int permisId) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			Map<String, String> map = new HashMap<String, String>();
			map.put("roleId", String.valueOf(roleId));
			map.put("permisId", String.valueOf(permisId));
			c.addRoleOfPermisById(map);
			outputJson("success", response);
		} catch (Exception e) {
			logger.error("SystemPermisController.addRoleToPermis Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	  * @Description: 根据权限名称(permisName)查询权限记录
	  * @param response
	  * @param permisName 权限名称
	  * @author: Dai.jingyu
	  * @date: 2015年3月25日 下午3:34:09
	  */
	@RequestMapping(value="getPermisByName",method=RequestMethod.POST)
	public void getPermisByName(HttpServletResponse response,String permisName){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysPermissionService");
		try {
			SysPermissionService.Client c = (SysPermissionService.Client) clientFactory.getClient();
			SysPermission permission = c.getPermisByName(permisName);
			outputJson(permission, response);
		} catch (Exception e) {
			logger.error("SystemPermisController.addRoleToPermis Exception" + e.getMessage());
		}finally{
			if(clientFactory!=null){
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
