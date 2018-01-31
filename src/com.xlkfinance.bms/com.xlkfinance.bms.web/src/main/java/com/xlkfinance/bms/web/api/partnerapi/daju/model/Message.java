package com.xlkfinance.bms.web.api.partnerapi.daju.model;

/**
 * 大桔（南粤银行）接口请求状态参数
 * @author chenzhuzhen
 * @date 2017年2月9日 下午5:16:28
 */
public class Message {
	
	/**状态*/
	private String status;
	/**描述*/
	private String desc;
	public  Message(){
	}
	public Message(String status, String desc){
		this.status = status;
		this.desc = desc;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
