package com.xlkfinance.bms.web.api.dc.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 接口请求头部
 * @author chenzhuzhen
 * @date 2017年5月8日 上午11:16:06
 */
public class Header implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 机构编号
	 */
	private String orgCode;
	/**机构用户*/
	private String orgUser;
	/**机构密码*/
	private String orgPwd;
	/**业务方法*/
	private String serviceMethod;
	/**平台处理时间*/
	private String platDealTime;
	/**是否重复查询 1:是  2：否*/
	private Integer isRepeat;
	
	
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getOrgUser() {
		return orgUser;
	}
	public void setOrgUser(String orgUser) {
		this.orgUser = orgUser;
	}
	public String getOrgPwd() {
		return orgPwd;
	}
	public void setOrgPwd(String orgPwd) {
		this.orgPwd = orgPwd;
	}
	public String getServiceMethod() {
		return serviceMethod;
	}
	public void setServiceMethod(String serviceMethod) {
		this.serviceMethod = serviceMethod;
	}
	public String getPlatDealTime() {
		return platDealTime;
	}
	public void setPlatDealTime(String platDealTime) {
		this.platDealTime = platDealTime;
	}
	public Integer getIsRepeat() {
	
		return isRepeat;
	}
	public void setIsRepeat(Integer isRepeat) {
	
		this.isRepeat = isRepeat;
	}
 
	
	
}
