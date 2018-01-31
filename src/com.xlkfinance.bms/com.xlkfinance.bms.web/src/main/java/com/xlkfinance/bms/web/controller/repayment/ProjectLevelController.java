/**
 * @Title: LoanExtensionController.java
 * @Package com.xlkfinance.bms.web.controller.repayment
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2014年12月11日 上午9:54:39
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.repayment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.repayment.ProjectLevel;
import com.xlkfinance.bms.rpc.repayment.ProjectLevelService;
import com.xlkfinance.bms.web.controller.BaseController;

@Controller
@RequestMapping("/projectLevelController")
public class ProjectLevelController extends BaseController {
	private Logger logger = LoggerFactory
			.getLogger(ProjectLevelController.class);

	// 项目五级分类页面跳转
	@RequestMapping("/toProjectLevel")
	public String loanExtension() {
		return "repayment/list_projectLevel";
	}
	
	    
	
	/**
	 * 
	  * @Description: 获取项目五级分类信息
	  * @param projectLevel
	  * @param response
	  * @author: Rain.Lv
	  * @date: 2015年6月2日 上午11:37:16
	 */
	@RequestMapping("/projectLevelList")
	public void projectLevelList(ProjectLevel projectLevel,HttpServletResponse response){
		List<ProjectLevel> list = null;
		Map<String, Object> map = null;
		BaseClientFactory clientFactory = null;
		try {
			map = new HashMap<String, Object>();
			clientFactory = getFactory( BusinessModule.MODUEL_REPAYMENT, "ProjectLevelService");
			ProjectLevelService.Client client = (ProjectLevelService.Client) clientFactory.getClient();
			list = client.getProjectLevelInfo(projectLevel);
			int total = client.getProjectLevelCount(projectLevel);
			map.put("total", total);
			map.put("rows", list);
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
		outputJson(map, response);
	}
	
	@RequestMapping(value = "saveProjectLevelInfo", method = RequestMethod.POST)
	@ResponseBody
	public void saveProjectLevelInfo(HttpServletRequest request, HttpServletResponse response, @RequestBody List<String> array) {
		BaseClientFactory c = null;
		List<ProjectLevel> list = new ArrayList<ProjectLevel>();
		try {
			c = getFactory(BusinessModule.MODUEL_REPAYMENT, "ProjectLevelService");
			ProjectLevelService.Client client = (ProjectLevelService.Client) c.getClient();
			JSONArray j = (JSONArray) JSONArray.toJSON(array);
			for (int i = 0; i < j.size(); i++) {
				JSONObject h = (JSONObject) JSONObject.toJSON(j.get(i));
				ProjectLevel projectLevel = (ProjectLevel) JSONArray.toJavaObject(h, ProjectLevel.class);
				projectLevel.setProcessUserId(getShiroUser().getPid());
				list.add(projectLevel);
			}
			boolean status = client.saveProjectLevelInfo(list);
			outputJson(status, response);
		} catch (Exception e) {
			logger.error("保存自定义级别出错" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (c != null) {
				try {
					c.destroy();
				} catch (ThriftException e) {
					e.printStackTrace();
				}
			}
		}
	}

	
}
