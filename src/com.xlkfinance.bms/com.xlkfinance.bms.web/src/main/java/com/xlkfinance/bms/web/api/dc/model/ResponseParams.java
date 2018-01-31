package com.xlkfinance.bms.web.api.dc.model;

/**
 * 接口响应参数
 * @author chenzhuzhen
 * @date 2017年5月8日 上午11:18:08
 */
public class ResponseParams {
	
	/** 头部 */
	private Header header;
	/** 消息体 */
	private Object body;
	
	/**错误码*/
	private String errorCode;
	
	/**错误提示*/
	private String errorMsg;
	
	public  ResponseParams(){
	}
	
	
	public  ResponseParams(String errorCode,String errorMsg ,Object body){
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.body = body;
	}
	
	
	public  ResponseParams(String errorCode,String errorMsg,Header header ,Object body){
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
		this.header = header;
		this.body = body;
	}
	

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	 
}