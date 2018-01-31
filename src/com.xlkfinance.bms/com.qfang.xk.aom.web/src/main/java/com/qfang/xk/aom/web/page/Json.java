/**
 * @Title: Json.java
 * @Package com.xlkfinance.bms.web.page
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 上午9:47:35
 * @version V1.0
 */
package com.qfang.xk.aom.web.page;

import java.util.HashMap;
import java.util.Map;

public class Json {
	private Map<String, Object> header = new HashMap<String, Object>();

	private Map<String, Object> body = new HashMap<String, Object>();

	public Map<String, Object> getHeader() {
		return header;
	}

	public void setHeader(Map<String, Object> header) {
		this.header = header;
	}

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> body) {
		this.body = body;
	}
}
