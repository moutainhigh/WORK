/**
 * @Title: ShiroUser.java
 * @Package com.xlkfinance.bms.web.shiro
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月10日 下午6:13:00
 * @version V1.0
 */
package com.xlkfinance.bms.web.shiro;

import java.io.Serializable;

public class ShiroUser implements Serializable {
	private static final long serialVersionUID = -1748602382963711884L;
	private Integer pid;
	private String userName;
	private String realName;
	private String token;

	public ShiroUser(Integer pid, String userName, String realName, String token) {
		super();
		this.pid = pid;
		this.userName = userName;
		this.realName = realName;
		this.token = token;
	}

	/**
	 * 本函数输出将作为默认的<shiro:principal/>输出.
	 */
	public String toString() {
		return userName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

}
