/**
 * @Title: SysConfigController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月13日 上午10:50:14
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysConfig;
import com.xlkfinance.bms.rpc.system.SysConfigService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 
 * @Title: SysConfigController.java
 * @Description: 系统配置的controller
 * @author : Mr.Cai
 * @date : 2014年12月19日 下午5:09:33
 * @email: caiqing12110@vip.qq.com
 */
@Controller
@RequestMapping("/sysConfigController")
public class SysConfigController extends BaseController {
	// private Logger logger =
	// LoggerFactory.getLogger(SysConfigController.class);

	/**
	 * 
	 * @Description: 中转方法
	 * @return 需要跳转的页面
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 上午10:21:38
	 */
	@RequestMapping(value = "navigation")
	public String navigation() {
		return "system/index_config";
	}

	/**
	 * 
	 * @Description: 条件查询系统配置
	 * @param sysConfig
	 *            系统配置对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午6:22:17
	 */
	@RequestMapping(value = "getAllSysConfig")
	@ResponseBody
	public void getAllSysConfig(SysConfig sysConfig, HttpServletResponse response) {
		List<SysConfig> listUser = new ArrayList<SysConfig>();
		Map<String,Object> map = new HashMap<String,Object>();
		int total = 0;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			listUser = client.getAllSysConfig(sysConfig);
			total = client.getAllSysConfigTotal(sysConfig);
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
		map.put("rows", listUser);
		map.put("total", total);
		// 输出
		outputJson(map, response);
	}

	/**
	 * 
	 * @Description: 删除系统配置
	 * @param pid
	 *            系统配置ID数组
	 * @param response
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午6:24:10
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public void delete(String configPids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			int rows = client.batchDelete(configPids);
			// 判断是否成功
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "操作失败,请重新操作！");
			} else {
				/*
				 * // 成功的话,就做业务日志记录 SysLog sysLog = new SysLog(); // 设置操作人
				 * sysLog.setUserId(getShiroUser().getPid()); // 设置模块名称
				 * sysLog.setModules(BusinessModule.MODUEL_SYSTEM); // 设置业务类型
				 * sysLog.setLogType(SysLogTypeConstant.LOG_TYPE_DELETE); //
				 * 设置日志内容 sysLog.setLogMsg("批量删除系统配置");
				 * recordLog(BusinessModule.MODUEL_SYSTEM, sysLog);
				 */
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "删除失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 新增系统配置对象
	 * @param sysConfig
	 *            系统配置对象对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午6:34:10
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	public void insert(SysConfig sysConfig, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			// 调用新增方法
			int rows = client.addSysConfig(sysConfig);
			if (rows == 0) {
				j.getHeader().put("success", false);
				j.getHeader().put("msg", "添加失败,请重新操作！");
			}
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "添加失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}

	/**
	 * 
	 * @Description: 修改系统配置对象信息
	 * @param sysConfig
	 *            系统配置对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月20日 下午6:39:10
	 */
	@RequestMapping(value = "update")
	@ResponseBody
	public void update(SysConfig sysConfig, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		try {
			SysConfigService.Client client = (SysConfigService.Client) clientFactory.getClient();
			// 调用修改方法
			client.updateSysConfig(sysConfig);
		} catch (ThriftServiceException tse) {
			tse.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", tse.message);
		} catch (Exception e) {
			e.printStackTrace();
			j.getHeader().put("success", false);
			j.getHeader().put("msg", "修改失败,请重新操作！");
		} finally {
			if (clientFactory != null) {
				try {
					clientFactory.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
		outputJson(j, response);
	}
	
	
	
	/**
	 * @Description: 通过配置名称查询配置
	 * @param sysConfig 系统配置对象
	 * @param response  HttpServletResponse 对象
	 */
	@RequestMapping(value = "getByConfigName")
	@ResponseBody
	public void getByConfigName(String configName, HttpServletResponse response) {
		SysConfig sysConfig = null;
		sysConfig = getByConfigName(configName);
		if(sysConfig == null){
			sysConfig = new SysConfig();
		}
		outputJson(sysConfig, response);
	}
	
	
	

}
