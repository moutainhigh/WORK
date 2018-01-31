/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-9-5
 */

package com.xlkfinance.bms.web.util;

/** 
 * @author ZhangDM(Mingly)
 * @date 2012-9-5
 * @description：请求参数
 */

public class HttpRequestParam {
	
	private String paraName;
	
	private String paraValue;

	public HttpRequestParam(){
		
	}
	
	public HttpRequestParam(String paraName, String paraValue){
		this.paraName = paraName;
		this.paraValue = paraValue;
	}
	
	
	public String getParaName() {
		return paraName;
	}

	public void setParaName(String paraName) {
		this.paraName = paraName;
	}

	public String getParaValue() {
		return paraValue;
	}

	public void setParaValue(String paraValue) {
		this.paraValue = paraValue;
	}
}
