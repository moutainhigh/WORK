package com.xlkfinance.bms.web.api.partnerapi.daju.model;

/**
 * 大桔（南粤银行）请求参数
 * 
 * @author chenzhuzhen
 * @date 2017年2月9日 上午9:50:25
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