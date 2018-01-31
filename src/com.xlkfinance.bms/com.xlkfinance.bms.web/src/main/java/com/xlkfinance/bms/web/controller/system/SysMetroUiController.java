/**
 * @Title: SysMetroUiController.java
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.system.SysMetroUiService;
import com.xlkfinance.bms.rpc.system.SysMetroUiVo;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.shiro.ShiroUser;

/**
  * @ClassName: SysMetroUiController
  * @Description: TODO
  * @author: JingYu.Dai
  * @date: 2015年4月29日 下午5:57:11
 */
@Controller
@RequestMapping("/sysMetroUiController")
public class SysMetroUiController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(SysMetroUiController.class);

	/**
	  * @Description: 新增磁铁菜单
	  * @param sysMetroUiVo
	  * @return int pid
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:29:15
	  */
	@RequestMapping(value = "/insertSysMetroUi" , method=RequestMethod.POST)
	public void insertSysMetroUi(HttpServletResponse response, SysMetroUiVo sysMetroUiVo){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMetroUiService");
		try {
			SysMetroUiService.Client c = (SysMetroUiService.Client) clientFactory.getClient();
			outputJson(c.insertSysMetroUi(sysMetroUiVo), response);
		} catch (Exception e) {
			logger.error("SysMetroUiController.insertSysMetroUi Exception" + e.getMessage());
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
	  * @Description: 更改磁铁菜单
	  * @param sysMetroUiVo
	  * @return 更改记录条数
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:30:11
	  */
	@RequestMapping(value = "/updateSysMetroUi" , method=RequestMethod.POST)
	public void updateSysMetroUi(HttpServletResponse response, SysMetroUiVo sysMetroUiVo){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMetroUiService");
		try {
			SysMetroUiService.Client c = (SysMetroUiService.Client) clientFactory.getClient();
			outputJson(c.updateSysMetroUi(sysMetroUiVo), response);
		} catch (Exception e) {
			logger.error("SysMetroUiController.updateSysMetroUi Exception" + e.getMessage());
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
	  * @Description: 查询磁铁菜单   ， 根据pid
	  * @param pid
	  * @return	SysMetroUiVo 磁铁菜单 实体类
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:31:23
	  */
	@RequestMapping(value = "/getSysMetroUiByPid" , method=RequestMethod.POST)
	public void getSysMetroUiByPid(HttpServletResponse response, int pid){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMetroUiService");
		try {
			SysMetroUiService.Client c = (SysMetroUiService.Client) clientFactory.getClient();
			outputJson(c.getSysMetroUiByPid(pid), response);
		} catch (Exception e) {
			logger.error("SysMetroUiController.getSysMetroUiByPid Exception" + e.getMessage());
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
	  * @Description: 分页查询磁铁菜单
	  * @param sysMetroUiVo
	  * @return List<SysMetroUiVo> 磁铁菜单 实体类集合
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:32:32
	  */
	@RequestMapping(value = "/findSysMetroUiPage" , method=RequestMethod.POST)
	public void findSysMetroUiPage(HttpServletResponse response, SysMetroUiVo sysMetroUiVo){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMetroUiService");
		try {
			SysMetroUiService.Client c = (SysMetroUiService.Client) clientFactory.getClient();
			Map<String , Object> map = new HashMap<String, Object>();
			List<SysMetroUiVo> list = c.findSysMetroUiPage(sysMetroUiVo);
			int total = c.findSysMetroUiTotal();
			map.put("rows", list);
			map.put("total", total);
			outputJson(map, response);
		} catch (Exception e) {
			logger.error("SysMetroUiController.findSysMetroUiPage Exception" + e.getMessage());
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
	  * @Description:  查询根菜单 根据用户权限 条件用户名称
	  * @param response
	  * @param userName 用户名称
	  * @author: JingYu.Dai
	  * @date: 2015年5月6日 上午10:33:50
	 */
	@RequestMapping(value="selectMetroUiByUserName",method=RequestMethod.POST)
	public void selectMetroUiByUserName(HttpServletResponse response){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMetroUiService");
		List<SysMetroUiVo> list = null;
		try {
			SysMetroUiService.Client c = (SysMetroUiService.Client) clientFactory.getClient();
			ShiroUser user = getShiroUser();
			list = c.selectMetroUiByUserName(user.getUserName());
		} catch (Exception e) {
			logger.error("SysMetroUiController.selectMetroUiByUserName Exception" + e.getMessage());
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(list, response);
	}
	
	/**
	  * @Description: 删除磁铁菜单 根据pid
	  * @param pid  
	  * @return int 删除的行数
	  * @author: JingYu.Dai
	  * @date: 2015年4月29日 下午5:46:46
	  */
	@RequestMapping(value = "/deleteSysMetroUi" ,method=RequestMethod.POST)
	public void deleteSysMetroUi(HttpServletResponse response,Integer pid){
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysMetroUiService");
		try {
			SysMetroUiService.Client c = (SysMetroUiService.Client) clientFactory.getClient();
			outputJson(c.deleteSysMetroUi(pid), response);
		} catch (Exception e) {
			logger.error("SysMetroUiController.deleteSysMetroUi Exception" + e.getMessage());
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

}
