/**
 * @Title: SystemController.java
 * @Package com.xlkfinance.bms.web.controller.system
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:55:05
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.system;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xlkfinance.bms.web.controller.BaseController;

/**
 * @ClassName: SystemController
 * @Description: 数据字典、系统配置、日志查看
 * @author: Simon.Hoo
 * @date: 2014年12月13日 上午10:59:52
 */
@Controller
@RequestMapping("/systemController")
public class SystemController extends BaseController {
	private String page;

	@RequestMapping(value = "navigation")
	public String navigation() {
		return "index";
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

}
