/**
 * @Title: SysLogController.java
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysLog;
import com.xlkfinance.bms.rpc.system.SysLogService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.page.Json;

/**
 * 
 * @Title: SysLogController.java
 * @Description: 业务日志的controller
 * @author : Mr.Cai
 * @date : 2014年12月22日 下午5:09:33
 * @email: caiqing12110@vip.qq.com
 */
@Controller
@RequestMapping("/sysLogController")
public class SysLogController extends BaseController {
	// private Logger logger = LoggerFactory.getLogger(SysLogController.class);

	/**
	 * 
	 * @Description: 中转方法
	 * @return 需要跳转的页面
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 上午10:21:38
	 */
	@RequestMapping(value = "navigation")
	public String navigation() {
		return "system/index_log";
	}

	/**
	 * 
	 * @Description: 条件查询业务日志
	 * @param sysLog  业务日志对象
	 * @param response  HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午6:22:17
	 */
	@RequestMapping(value = "getAllSysLog" , method=RequestMethod.POST)
	@ResponseBody
	public void getAllSysLog(SysLog sysLog, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String,Object>();
		List<SysLog> listUser = new ArrayList<SysLog>();
		int total = 0;
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLogService");
		try {
			SysLogService.Client client = (SysLogService.Client) clientFactory.getClient();
			listUser = client.getAllSysLog(sysLog);
			total = client.getAllSysLogTotal(sysLog);
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
	 * @Description: 删除业务日志
	 * @param pid
	 *            业务日志ID数组
	 * @param response
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午6:24:10
	 */
	@RequestMapping(value = "delete")
	@ResponseBody
	public void delete(String logPids, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLogService");
		try {
			SysLogService.Client client = (SysLogService.Client) clientFactory.getClient();
			int rows = client.batchDelete(logPids);
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
	 * @Description: 新增业务日志对象
	 * @param sysLog
	 *            业务日志对象对象
	 * @param response
	 *            HttpServletResponse 对象
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午6:34:10
	 */
	@RequestMapping(value = "insert")
	@ResponseBody
	public void insert(SysLog sysLog, HttpServletResponse response) {
		Json j = super.getSuccessObj();
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLogService");
		try {
			SysLogService.Client client = (SysLogService.Client) clientFactory.getClient();
			// 调用新增方法
			int rows = client.addSysLog(sysLog);
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

}
