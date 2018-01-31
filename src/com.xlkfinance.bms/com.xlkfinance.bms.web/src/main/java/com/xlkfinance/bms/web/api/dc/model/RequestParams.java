package com.xlkfinance.bms.web.api.dc.model;

/**
 * 接口请求参数
 * @author chenzhuzhen
 * @date 2017年5月8日 上午11:17:58
 */
public class RequestParams {
	
	/**头部*/
	private Header header;
	
	/**消息体*/
	private Object body;

	public Header getHeader() {
		return this.header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return this.body;
	}

	public void setBody(Object body) {
		this.body = body;
	}
}