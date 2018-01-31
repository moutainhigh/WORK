package com.xlkfinance.bms.web.api.partnerapi.daju.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 大桔（南粤银行）请求头部
 * @author chenzhuzhen
 * @date 2017年2月8日 下午5:46:51
 */
public class Header implements Serializable {

	private static final long serialVersionUID = 1L;
	/**机构代码*/
	private String org_code;
	/**机构用户*/
	private String org_user;
	/**机构密码*/
	private String org_pwd;
	/**机构请求编号*/
	private String org_req_no;
	/**接入代码*/
	private String agency_code;
	/**业务代码*/
	private String service_code;
	/**平台处理时间*/
	private Date plat_deal_time;
	/**处理id*/
	private String deal_id;
	/**交易加密串*/
	private String key;
	
	
	public String getOrg_code() {
		return org_code;
	}
	public void setOrg_code(String org_code) {
		this.org_code = org_code;
	}
	public String getOrg_user() {
		return org_user;
	}
	public void setOrg_user(String org_user) {
		this.org_user = org_user;
	}
	public String getOrg_pwd() {
		return org_pwd;
	}
	public void setOrg_pwd(String org_pwd) {
		this.org_pwd = org_pwd;
	}
	public String getOrg_req_no() {
		return org_req_no;
	}
	public void setOrg_req_no(String org_req_no) {
		this.org_req_no = org_req_no;
	}
	public String getAgency_code() {
		return agency_code;
	}
	public void setAgency_code(String agency_code) {
		this.agency_code = agency_code;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public Date getPlat_deal_time() {
		return plat_deal_time;
	}
	public void setPlat_deal_time(Date plat_deal_time) {
		this.plat_deal_time = plat_deal_time;
	}
	public String getDeal_id() {
		return deal_id;
	}
	public void setDeal_id(String deal_id) {
		this.deal_id = deal_id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	
	
	
}
