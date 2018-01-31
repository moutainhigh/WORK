/**
 * @Title: SystemRoleController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2014年12月19日 上午11:04:09
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysPermission;
import com.xlkfinance.bms.rpc.system.SysRole;
import com.xlkfinance.bms.rpc.system.SysRoleService;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.system.SysUserDto;
import com.xlkfinance.bms.rpc.system.SysUserRoleSearch;
import com.xlkfinance.bms.rpc.system.SysUserService;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.controller.BaseController;

@Controller
@RequestMapping("/systemRoleController")
public class SystemRoleController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SystemRoleController.class);

	@RequestMapping(value = "toRoleMain")
	public String main(HttpServletResponse response) {
		return "system/role_list";
	}

	/**
	 * 根据角色Code集合查询
	 * 
	 * @param codes
	 */
	@RequestMapping(value = "findRolesByCodes", method = RequestMethod.POST)
	public void findRolesByCodes(@RequestParam("roleCode") String roleCodes, HttpServletResponse response) {
		List<SysRole> roles = new ArrayList<SysRole>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			String[] roleCodeArray = roleCodes.split(",");
			List<String> roleCodeList = new ArrayList<String>();
			for (String rc : roleCodeArray) {
				roleCodeList.add(rc);
			}
			SysRoleService.Client client = (SysRoleService.Client) clientFactory.getClient();
			roles = client.queryRolesByRoleCodes(roleCodeList);
		} catch (ThriftServiceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("SystemRoleController.findRolesByCodes Exception" + e.getMessage());
			}
		} catch (ThriftException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("SystemRoleController.findRolesByCodes Exception" + e.getMessage());
			}
		} catch (TException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("SystemRoleController.findRolesByCodes Exception" + e.getMessage());
			}
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("SystemRoleController.findRolesByCodes Exception" + e.getMessage());
			}
		} finally {
			if (null != clientFactory) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(roles, response);

	}

	/**
	 * 查询所有角色
	 * 
	 * @author acheivo
	 * @return
	 */
	@RequestMapping(value = "rolelist", method = RequestMethod.POST)
	public void getRoleList(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		List<SysRole> roleList = new ArrayList<SysRole>();
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			SysRole sys = new SysRole();
			sys.setPage(page);
			sys.setRows(rows);
			roleList = c.getAllRolesForPage(sys);
			int count = c.getAllRolesForPageCount();
			outputJson(getMap(count, roleList), response);
		} catch (Exception e) {
			logger.error("SystemRoleController.getRoleList Exception" + e.getMessage());
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
	 * 根据条件查询所有角色
	 * 
	 * @author acheivo
	 * @return
	 */
	@RequestMapping(value = "getRoleIdListByUserName", method = RequestMethod.POST)
	public void getRoleIdListByUserName(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("userName") String userName,@RequestParam("rows") int rows) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		List<SysRole> roleList = new ArrayList<SysRole>();
		//IdList
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			//获得该用户的角色Id列表
			roleList = c.getRoleIdListByUserName(userName,page,rows);
//			int count = roleList.size();
			int count = c.getRoleIdListByUserNameCount(userName);
			outputJson(getMap(count, roleList), response);
		} catch (Exception e) {
			logger.error("SystemRoleController.getRoleList Exception" + e.getMessage());
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
	 * 查询该角色没有的权限
	 * 
	 * @param response
	 * @param page
	 * @param rows
	 * @param pid
	 * @return
	 */
	@RequestMapping(value = "searchRoleNotHavePermis", method = RequestMethod.POST)
	public void searchRoleNotHavePermis(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("pid") int pid, @RequestParam("permisName") String permisName) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		List<SysPermission> list = new ArrayList<SysPermission>();
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			SysRole sysRole = new SysRole();
			sysRole.setPid(pid);
			sysRole.setPage(page);
			sysRole.setRows(rows);
			list = c.searchRoleNotHavePermis(sysRole,permisName);
			int count = c.searchRoleNotHavePermisCount(sysRole,permisName);
			outputJson(getMap(count, list), response);
		} catch (Exception e) {
			logger.error("SystemRoleController.searchRoleNotHavePermis Exception" + e.getMessage());
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
	 * 根据角色Id查询其拥有的权限
	 * 
	 * @author acheivo
	 * @param response
	 * @param page
	 * @param rows
	 * @param pid
	 */
	@RequestMapping(value = "getSysPermissionList", method = RequestMethod.POST)
	public void getSysPermissionList(HttpServletResponse response, @RequestParam("page") int page, @RequestParam("rows") int rows, @RequestParam("pid") int pid,@RequestParam("permisName") String permisName) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			SysRole sysRole = new SysRole();
			sysRole.setPid(pid);
			sysRole.setPage(page);
			sysRole.setRows(rows);
			List<SysPermission> list = c.getSysPermissionList(sysRole,permisName);
			int count = c.getSysPermissionListCount(sysRole,permisName);
			outputJson(getMap(count, list), response);
		} catch (Exception e) {
			logger.error("SystemRoleController.deleteRole Exception" + e.getMessage());
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
	 * 根据模糊查询
	 * 
	 * @param response
	 * @param roleNmae
	 * @param roleDesc
	 * @param pid
	 */
	@RequestMapping(value = "seachRoleByRoleName", method = RequestMethod.POST)
	public void seachRoleByRoleName(HttpServletResponse response, @RequestParam("roleName") String roleName, @RequestParam("roleDesc") String roleDesc, @RequestParam("page") int page, @RequestParam("rows") int rows) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			SysRole sysRole = new SysRole();
			sysRole.setRoleName(roleName);
			sysRole.setRoleDesc(roleDesc);
			sysRole.setPage(page);
			sysRole.setRows(rows);
			List<SysRole> list = c.searchRoleListByRoleName(sysRole);
			int count = c.searchRoleListCount(sysRole);

			outputJson(getMap(count, list), response);

		} catch (Exception e) {
			logger.error("SystemRoleController.deleteRole Exception" + e.getMessage());
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

	public static Map<String, Object> getMap(int count, List<?> list) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", list);
		map.put("total", count);
		return map;
	}

	/**
	 * 新增角色
	 * 
	 * @param roleName
	 * @param roleDesc
	 * @param response
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	public void addRole(@RequestParam("roleName") String roleName, @RequestParam("roleDesc") String roleDesc, @RequestParam("roleCode") String roleCode,@RequestParam("parentId") int parentId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		SysRole sr = new SysRole();
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			sr.setRoleName(roleName);
			sr.setRoleCode(roleCode);
			sr.setRoleDesc(roleDesc);
			sr.setParentId(parentId);
			sr = c.addSysRole(sr);
			outputJson(sr.getRoleCode(), response);
		} catch (Exception e) {
			logger.error("SystemRoleController.addRole Exception" + e.getMessage());
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
	 * 删除一个角色
	 * 
	 * @author acheivo
	 * @return
	 */
	@RequestMapping(value = "deleteRole", method = RequestMethod.POST)
	public void deleteRole(@RequestParam(value = "pid[]") int[] pid, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < pid.length; i++) {
				list.add(pid[i]);
			}
			int status = c.deleteSysRole(list);
			outputJson(status, response);

		} catch (Exception e) {
			logger.error("SystemRoleController.deleteRole Exception" + e.getMessage());
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
	 * 修改角色信息
	 * 
	 * @param roleName
	 * @param roleDesc
	 * @param pid
	 * @param response
	 */
	@RequestMapping(value = "updateRole", method = RequestMethod.POST)
	public void updateRole(@RequestParam("roleName") String roleName, @RequestParam("roleDesc") String roleDesc, @RequestParam("roleCode") String roleCode, @RequestParam("pid") int pid,@RequestParam("parentId") int parentId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		SysRole sysRole = new SysRole();
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			sysRole.setPid(pid);
			sysRole.setRoleName(roleName);
			sysRole.setRoleDesc(roleDesc);
			sysRole.setParentId(parentId);
			sysRole.setRoleCode(roleCode);
			c.updateSysRole(sysRole);
			outputJson("success", response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.updateRole Exception" + e.getMessage());
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
	 * 给角色赋予权限
	 * 
	 * @author acheivo
	 * @return
	 */
	@RequestMapping(value = "addPermisToRole", method = RequestMethod.POST)
	public void addPermisToRole(@RequestParam("roleId") long roleId, @RequestParam("permisId") long permisId, HttpServletResponse response) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("roleId", String.valueOf(roleId));
			parameter.put("permisId", String.valueOf(permisId));
			if (c.addPermisToRole(parameter) > 0) {
				outputJson("success", response);
			}

		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.addPermisToRole Exception" + e.getMessage());
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
	 * 删除某个角色的权限
	 * 
	 * @param permisId
	 * @param roleId
	 * @param response
	 */
	@RequestMapping(value = "deletePermisOfRoleById", method = RequestMethod.POST)
	public void deletePermisOfRoleById(@RequestParam("permisId") int permisId, @RequestParam("roleId") int roleId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("roleId", String.valueOf(roleId));
			parameter.put("permisId", String.valueOf(permisId));
			c.deletePermisOfRoleById(parameter);
			outputJson("success", response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.updateRole Exception" + e.getMessage());
		} finally {
			if (clientFactory != null)  		
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}


	/**
	 * 查询角色下的用户
	 * 
	 * @param response
	 * @param page
	 * @param rows
	 * @param pid
	 */
	@RequestMapping(value = "getRoleUserList", method = RequestMethod.POST)
	public void getRoleUserList(HttpServletResponse response, SysRole sysRole, String realName) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			List<SysUser> list = c.getRoleUserListByRoleId(sysRole,realName);
			int count = c.getSysUserByRoleIdCount(sysRole,realName);
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			e.printStackTrace();
			outputJson("false", response);
			logger.error("SystemRoleController.updateRole Exception" + e.getMessage());
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
	 * 删除用户和角色的关系
	 * @param permisId
	 * @param roleId
	 * @param response
	 */
	@RequestMapping(value = "deleteUserOfRole", method = RequestMethod.POST)
	public void deleteUserOfRole(@RequestParam("userId") int userId, @RequestParam("roleId") int roleId, HttpServletResponse response) {

		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			Map<String, String> parameter = new HashMap<String, String>();
			parameter.put("roleId", String.valueOf(roleId));
			parameter.put("userId", String.valueOf(userId));
			c.delUserToRole(parameter);
			outputJson("success", response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.updateRole Exception" + e.getMessage());
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
	 * 
	 * @Description: 查询用户没有的角色
	 * @param response
	 * @param page
	 * @param rows
	 * @param pid
	 * @author: Chong.Zeng
	 * @date: 2015年1月21日 下午1:50:06
	 */
	@RequestMapping(value = "getUserNotRoleList", method = RequestMethod.POST)
	public void getUserNotRoleList(HttpServletResponse response, SysUserRoleSearch userRoleSearch) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		Map<String,Object> map = new HashMap<String,Object>();
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			//List<SysRole> list = c.getRoleByUserId(page, rows, sysUser);
			List<SysRole> list = c.searchUserNotRoleList(userRoleSearch);
			int count = c.searchUserNotRoleListCount(userRoleSearch);
			map.put("rows", list);
			map.put("total", count);
			outputJson(map, response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.getUserNotRoleList Exception" + e.getMessage());
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
	 * 
	 * @Description: 增加用户角色关系
	 * @param userId
	 * @param roleId
	 * @param response
	 * @author: Chong.Zeng
	 * @date: 2015年1月21日 下午1:54:54
	 */
	@RequestMapping(value = "addUserOfRole", method = RequestMethod.POST)
	public void addUserOfRole(@RequestParam("userId") int userId, @RequestParam("roleId[]") int[] roleId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < roleId.length; i++) {
				list.add(roleId[i]);
			}
			c.addRoleToUser(userId, list);
			outputJson("success", response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.addUserOfRole Exception" + e.getMessage());
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
	 * 
	 * @Description: 查询角色没有的用户
	 * @param response
	 * @param page
	 * @param rows
	 * @param pid
	 * @author: Chong.Zeng
	 * @date: 2015年1月22日 上午9:24:10
	 */
	@RequestMapping(value = "getRoleNoUserList", method = RequestMethod.POST)
	public void getRoleNoUserList(HttpServletResponse response,SysUserRoleSearch sysUserRoleSearch) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		Map<String,Object> map = new HashMap<String, Object>();
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			List<SysUser> list = c.getRoleNoUserList(sysUserRoleSearch);
			int count = c.getRoleNoUserListCount(sysUserRoleSearch);
			map.put("rows", list);
			map.put("count", count);
			outputJson(map, response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.getRoleNoUserList Exception" + e.getMessage());
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

	@RequestMapping(value = "addRoleOfUser", method = RequestMethod.POST)
	public void addRoleOfUser(@RequestParam("roleId") int roleId, @RequestParam("userId[]") int[] userId, HttpServletResponse response) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			List<Integer> list = new ArrayList<Integer>();
			for (int i = 0; i < userId.length; i++) {
				list.add(userId[i]);
			}
			c.addUserToRole(roleId, list);
			outputJson("success", response);
		} catch (Exception e) {
			outputJson("false", response);
			logger.error("SystemRoleController.addUserOfRole Exception" + e.getMessage());
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
	 * 通过用户的部门以及需要查询的部门层次，查询部门
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月28日 下午2:37:58
	 */
	/*private List<Integer> getCityLists(){
		List<Integer> cityList = new ArrayList<Integer>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysOrgInfoService");
		try {
			//根据用户ID查出其所属城市ID
			SysOrgInfoService.Client orgService = (SysOrgInfoService.Client) clientFactory.getClient();
			SysOrgInfo sysOrgInfo = new SysOrgInfo();
			sysOrgInfo.setLayer(2);//城市或者二级部门
			sysOrgInfo.setId(getSysUser().getOrgId());//用户所在部门ID
			//通过用户的部门以及需要查询的部门层次，查询部门
			List<SysOrgInfo> orgList = orgService.listSysParentOrg(sysOrgInfo);
			if(orgList != null){
				for(SysOrgInfo org : orgList){
					cityList.add(org.getId());
				}
			}
			
		} catch (Exception e) {
			logger.error("获取二级机构列表失败：" + e.getMessage());
	        e.printStackTrace();
		}
		return cityList;
	}*/
	
	/**
	 * 根据角色查询人员列表
	  * @param response
	  * @param roleCode
	  * @author: baogang
	  * @date: 2016年5月6日 下午3:50:17
	 */
	@RequestMapping(value = "findUsersByRoleCode")
	public void findUsersByRoleCode(HttpServletResponse response, @RequestParam("roleCode") String roleCode) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysUserService");
		try {
			//SysUserService.Client c = (SysUserService.Client) clientFactory.getClient();
		    WorkflowService.Client workflowServiceClient = (WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		    SysUser user = getSysUser();
		    //根据角色字符串查询roleId
		    List<String> roleCodeList = new ArrayList<String>();
		    roleCodeList.add(roleCode);
			SysRoleService.Client client = (SysRoleService.Client) getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService").getClient();
			List<SysRole> roles = client.queryRolesByRoleCodes(roleCodeList);
		    
		    int roleId = roles.get(0).getPid();
			//根据当前用户以及下一节点角色ID查询人员列表
		    List<SysUser> list = workflowServiceClient.findBizUser(user, roleId);
			/*List<Integer> cityLists = getCityLists();
			List<SysUser> list = new ArrayList<SysUser>();
			if (cityLists != null && cityLists.size() > 0) {
				for (int orgId : cityLists) {
					list.addAll(c.getUsersByOrgIdAndRoleCode(orgId, roleCode));
				}
			} else {
				list = c.getUsersByOrgIdAndRoleCode(1, roleCode);
			}*/
			//List<SysUserDto> list = c.getUserListByRoleCode(role);
			outputJson(list, response);
		} catch (Exception e) {
			e.getStackTrace();
			outputJson("false", response);
			logger.error("SystemRoleController.getRoleNoUserList Exception" + e.getMessage());
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
	 * 根据角色编码查询角色下用户
	 * @param response
	 * @param roleCode
	 */
	@RequestMapping(value = "getUsersByRoleCode")
	public void getUsersByRoleCode(HttpServletResponse response, @RequestParam("roleCode") String roleCode) {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client c = (SysRoleService.Client) clientFactory.getClient();
			SysRole role = new SysRole();
			role.setRoleCode(roleCode);
			List<SysUserDto> list = c.getUserListByRoleCode(role);
			outputJson(list, response);
		} catch (Exception e) {
			e.getStackTrace();
			outputJson("false", response);
			logger.error("SystemRoleController.getRoleNoUserList Exception" + e.getMessage());
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
	
	@RequestMapping(value = "findUsersByRoleCodeAndProjectId")
	public void findUsersByRoleCode(HttpServletResponse response, @RequestParam("roleCode") String roleCode, @RequestParam("projectId") int projectId) {
	   try {
	      WorkflowService.Client workflowServiceClient = (WorkflowService.Client) getService(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
         ProjectService.Client projectService = (ProjectService.Client) getService(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
         SysUserService.Client sysUserService = (SysUserService.Client) getService(BusinessModule.MODUEL_SYSTEM, "SysUserService");
         
         Project project = projectService.getProjectInfoById(projectId);//项目信息
         int pmUserId = project.getPmUserId();
         SysUser pmUser = sysUserService.getSysUserByPid(pmUserId);//客户经理
	      //根据角色字符串查询roleId
	      List<String> roleCodeList = new ArrayList<String>();
	      roleCodeList.add(roleCode);
	      SysRoleService.Client client = (SysRoleService.Client) getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService").getClient();
	      List<SysRole> roles = client.queryRolesByRoleCodes(roleCodeList);
	      
	      int roleId = roles.get(0).getPid();
	      //根据当前用户以及下一节点角色ID查询人员列表
	      List<SysUser> list = workflowServiceClient.findBizUser(pmUser, roleId);
	      outputJson(list, response);
	   } catch (Exception e) {
	      e.getStackTrace();
	      outputJson("false", response);
	      logger.error("SystemRoleController.getRoleNoUserList Exception" + e.getMessage());
	   }
	}
	
	/**
	 * 所有角色下拉框
	 * 
	 * @param response
	 */
	@RequestMapping(value = "getAllRoles")
	@ResponseBody
	public void getAllRoles(HttpServletResponse response) {
		List<SysRole> list = new ArrayList<SysRole>();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysRoleService");
		try {
			SysRoleService.Client client = (SysRoleService.Client) clientFactory.getClient();
			SysRole sysRole=new SysRole();
			sysRole.setPid(0);
			sysRole.setRoleName("--请选择--");
			list.add(sysRole);
			list.addAll(client.getSysRoleList());
		} catch (Exception e) {
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
		// 输出
		outputJson(list, response);
	}
}
