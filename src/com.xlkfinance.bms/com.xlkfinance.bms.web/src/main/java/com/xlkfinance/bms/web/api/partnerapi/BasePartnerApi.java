package com.xlkfinance.bms.web.api.partnerapi;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;


/**
 * 合作机构接口基类
 * @author chenzhuzhen
 * @date 2016年6月24日 上午11:03:10
 */
public interface BasePartnerApi {
	
	
	/***
	 * 申请/补充材料共用
	 * @return
	 * @throws Exception
	 */
	public JSONObject apply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception ;
 
	/***
	 * 复议
	 * @return
	 * @throws Exception
	 */
	public JSONObject reApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception ;
	
	/***
	 * 关闭
	 * @return
	 * @throws Exception
	 */
	public JSONObject close(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception ;
	
	
	/***
	 * 放款申请
	 * @return
	 * @throws Exception
	 */
	public JSONObject loanApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception ;
	/***
	 * 还款申请
	 * @return
	 * @throws Exception
	 */
	public JSONObject refundApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception ;
	
	
	
	
	/**
	 * 回调通知
	 * @param paramJson
	 * @param request
	 * @return
	 */
	public JSONObject notify(JSONObject paramJson ,HttpServletRequest request)throws Exception ;

}
