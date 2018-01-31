/**
 * @Title: ApiController.java
 * @Package com.xlkfinance.bms.web.controller.partner
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月18日 下午3:58:09
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.partner;

import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.partner.PartnerApprovalRecord;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.web.api.partnerapi.daju.constant.DaJuConstant;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.RequestParams;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.ResponseParams;
import com.xlkfinance.bms.web.api.partnerapi.impl.DaJuPartnerApiImpl;
import com.xlkfinance.bms.web.api.partnerapi.impl.TLPartnerApiImpl;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.UuidUtil;
import com.xlkfinance.bms.web.util.YZTSercurityUtil;

@Controller
@RequestMapping("/openApi")
public class ApiController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	private JSONObject contentJson = new JSONObject();
	
	/**接口调用成功*/
	private final static String API_STATUS_SUCCESS="0";
	/**接口业务状态：操作失败*/
	private final static String API_ERR_CODE_FAIL = "1";
	
	//小赢MD5
	private static String XIAOYING_PARTNER_MD5 = PropertiesUtil.getValue("xiaoying.partner.md5");
	

	//统联机构合作code（Q房提供）
	private static String TL_PARTNER_PARAM_PARTNERCODE_QFANG = PropertiesUtil.getValue("tl.partner.param.partnercode.qfang");
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	
	@Resource(name = "tLPartnerApiImpl")
	private TLPartnerApiImpl tLPartnerApiImpl;		//统联接口实现类
	
	@Resource(name = "daJuPartnerApiImpl")
	private DaJuPartnerApiImpl daJuPartnerApiImpl;		//大桔（南粤银行）接口实现类
	

	/**
	 * 审批结果修改
	 * 
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author: Administrator
	 * @date: 2016年3月21日 下午4:18:45
	 */
	@RequestMapping(value = "/approval-res", method = RequestMethod.POST)
	@ResponseBody
	public String approvalRes(HttpServletRequest request,HttpServletResponse response) throws Exception {
		BaseClientFactory clientFactoryPartner = null;
		try {
			StringBuffer jb = new StringBuffer();
			String data = null;
			//读取接口参数
			BufferedReader reader = request.getReader();
			while ((data = reader.readLine()) != null){
				jb.append(data);
			}

			logger.info("method:approvalRes;data:" + jb);
			//接口参数转换成Map
			String[] str = jb.toString().split("&");
			JSONObject json = new JSONObject();
			for (int i = 0; i < str.length; i++) {
				String[] arr = str[i].split("=");
				json.put(arr[0], arr[1]);
			}
			//接口参数解密
			String resContent = YZTSercurityUtil.decryptResponse(json.toString(),XIAOYING_PARTNER_MD5);
			
			if(StringUtil.isBlank(resContent)){
				return returnFailed(response,"请求参数为空","99",null);
			}
			
			JSONObject respJson = JSONObject.parseObject(resContent);
			
			logger.info("审批结果(approval-res)回调参数： "+resContent.toString());
			
			if(respJson.get("loan_id") == null){
				return returnFailed(response,"参数loan_id为空","99",null);
			}else if(respJson.get("err_code") == null){
				return returnFailed(response,"参数err_code为空","99",null);
			}
			
			String loanId = respJson.getString("loan_id");
			int result = respJson.getIntValue("err_code");// 0，审批通过，1审批不通过
			String remark = respJson.getString("remark");
			int nofityType = respJson.getIntValue("type");//回调类型 (1: 申请 2: 复议 3: 复议确认要款 4：放款) 
			double approveMoney = respJson.get("approve_money") !=null ?respJson.getDoubleValue("approve_money"):0 ;	//审批金额
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			ProjectPartner projectPartner = new ProjectPartner(); 
			projectPartner.setLoanId(loanId); 
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(projectPartner);
			
			//数据不存在
			if(tempPartner == null || tempPartner.getPid() == 0){
				return returnFailed(response,"数据不存在","99",loanId);
			}
			
			//修改审批记录
			PartnerApprovalRecord updateRecord = new PartnerApprovalRecord();
			ProjectPartner updatePartner = new ProjectPartner();
			updatePartner.setPid(tempPartner.getPid());
			int approvalStatus = 0;
			int approvalRecordStatus = 0;	//审核记录状态
			boolean isUpdateNotify = false;	//是否更新审核记录
			Timestamp time = new Timestamp(new Date().getTime()); 
			
			//申请/复议
			if(Constants.NOTIFY_TYPE_1 == nofityType || Constants.NOTIFY_TYPE_2 == nofityType ){
				if(Constants.APPROVAL_STATUS_2 == tempPartner.getApprovalStatus()){
					return returnFailed(response,"审核己通过，不允许重复操作","99",loanId);
				}else if(Constants.APPROVAL_STATUS_4 == tempPartner.getApprovalStatus()){
					return returnFailed(response,"驳回中，不允许操作","99",loanId);
				}else if(Constants.APPROVAL_STATUS_1 != tempPartner.getApprovalStatus()){
					return returnFailed(response,"未提交申请，不允许操作","99",loanId);
				}

				if(result == Constants.ERR_CODE_0){
					approvalStatus = Constants.APPROVAL_STATUS_2;//审核通过 
				}else if(result == Constants.ERR_CODE_1){ 
					approvalStatus =Constants.APPROVAL_STATUS_3;//审核未通过 
				}else if(result == Constants.ERR_CODE_2){ 
					approvalStatus =Constants.APPROVAL_STATUS_4;//补充材料
				}
				approvalRecordStatus = approvalStatus;
				updatePartner.setApproveMoney(approveMoney);
				updatePartner.setApprovalComment(remark); 
				updatePartner.setApprovalStatus(approvalStatus);
				updateRecord.setReApplyReason(tempPartner.getReApplyReason());
				updatePartner.setApprovalTime(time.toString()); //设置审批时间
				
				isUpdateNotify = true;
			}else if(Constants.NOTIFY_TYPE_3 == nofityType ){
				
				//复议确认要款
				if(Constants.CONFIRM_LOAN_STATUS_1 == tempPartner.getConfirmLoanStatus()){
					return returnFailed(response,"未提交申请，不允许操作","99",loanId);
				}else if(Constants.CONFIRM_LOAN_STATUS_3 == tempPartner.getConfirmLoanStatus()){
					return returnFailed(response,"审核己通过，不允许操作","99",loanId);
				}else if(Constants.CONFIRM_LOAN_STATUS_4 == tempPartner.getConfirmLoanStatus()){
					return returnFailed(response,"未提交审核，不允许操作","99",loanId);
				}
				
				if(result == Constants.ERR_CODE_0){
					approvalStatus = Constants.CONFIRM_LOAN_STATUS_3;//审核通过 
					approvalRecordStatus = Constants.APPROVAL_STATUS_2;
				}else if(result == Constants.ERR_CODE_1){ 
					approvalStatus =Constants.CONFIRM_LOAN_STATUS_4;//审核未通过 
					approvalRecordStatus = Constants.APPROVAL_STATUS_3;
				}
				updatePartner.setLoanStatus(Constants.LOAN_STATUS_1); 
				updatePartner.setApproveMoney(approveMoney);
				updatePartner.setApprovalComment(remark); 
				updatePartner.setConfirmLoanReason(tempPartner.getConfirmLoanReason());
				updatePartner.setConfirmLoanResultTime(time.toString());
				updatePartner.setConfirmLoanStatus(approvalStatus);
				
				isUpdateNotify = true;
			}else if(Constants.NOTIFY_TYPE_4 == nofityType){
				//放款状态
				//放款己成功
				if(Constants.LOAN_STATUS_4 == tempPartner.getLoanStatus()){
					return returnFailed(response,"己放款成功，不允许操作","99",loanId);
				} 
				if(result == Constants.ERR_CODE_0){
					approvalStatus = Constants.LOAN_STATUS_4;//审核通过 
				}else if(result == Constants.ERR_CODE_1){ 
					approvalStatus =Constants.LOAN_STATUS_5;//审核未通过 
				}else if(result == Constants.ERR_CODE_2){ 
					approvalStatus =Constants.LOAN_STATUS_6;//补充材料
				}
				updatePartner.setLoanStatus(approvalStatus);
				updatePartner.setLoanResultTime(time.toString());
				updatePartner.setLoanRemark(remark);
				isUpdateNotify = false;
			}

			//修改审核回调信息 
			int rows = clientPartner.updateProjectPartner(updatePartner);
			
			if(isUpdateNotify){
				updateRecord.setPartnerId(tempPartner.getPid());
				updateRecord.setLoanId(loanId);
				updateRecord.setApprovalComment(remark);
				updateRecord.setApprovalTime(time.toString());
				updateRecord.setApprovalStatus(approvalRecordStatus);
				updateRecord.setIsNotify(Constants.IS_NOTIFY_2);
				updateRecord.setApproveMoney(approveMoney);
				
				updateRecord.setQueryIsNotify(Constants.IS_NOTIFY_1);
				//修改审核记录
				clientPartner.updatePartnerApprovalRecord(updateRecord);
			}
			
			if(rows != 1){
				return returnFailed(response,"操作失败","99",loanId);
			} 
			return returnSuccess(response,"操作成功",loanId);
		} catch (Exception e) {
			logger.error(">>>>>>approvalRes error", e);
			return returnFailed(response,"系统错误","99",null);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>approvalRes clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}

	/**
	 * 放款结果确认
	 * 
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author: Administrator
	 * @date: 2016年3月21日 下午4:18:45
	 */
	@RequestMapping(value = "/loan-confirm", method = RequestMethod.POST)
	@ResponseBody
	public String loanConfirm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String loanId = null;
		BaseClientFactory clientFactoryPartner = null;
		try {
			StringBuffer jb = new StringBuffer();
			String data = null;
			//读取接口参数
			BufferedReader reader = request.getReader();
			while ((data = reader.readLine()) != null){
				jb.append(data);
			}

			logger.info("method:approvalRes;data:" + jb);
			//接口参数转换成Map
			String[] str = jb.toString().split("&");
			JSONObject json = new JSONObject();
			for (int i = 0; i < str.length; i++) {
				String[] arr = str[i].split("=");
				json.put(arr[0], arr[1]);
			}
			//接口参数解密
			String resContent = YZTSercurityUtil.decryptResponse(json.toString(),XIAOYING_PARTNER_MD5);
			
			if(StringUtil.isBlank(resContent)){
				return returnFailed(response,"请求参数为空","99",null);
			}
			
			JSONObject respJson = JSONObject.parseObject(resContent);
			logger.info("放款结果确认(loan-confirm)回调参数： "+resContent.toString());
			
			if(respJson.get("loan_id") == null){
				return returnFailed(response,"参数loan_id为空","99",null);
			}else if(respJson.get("err_code") == null){
				return returnFailed(response,"参数err_code为空","99",null);
			}
			
			loanId =respJson.getString("loan_id");
			String result = respJson.getString("err_code");// 0，放款成功，1放款失败，2，放款中
			String loan_time = respJson.getString("loan_time");	//放款时间   2016-11-10 00:00:00
					
/*			if ("0".equals(result) && StringUtil.isBlank(loan_time)) {
				return returnFailed(response,"放款时间不能为空","99",loanId);
			}*/
			
			
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			//验证状态
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setLoanId(loanId);
			ProjectPartner partnerTemp = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(partnerTemp == null || partnerTemp.getPid() == 0){
				return returnFailed(response,"数据不存在","99",loanId);
			}else if(Constants.LOAN_STATUS_1 ==  partnerTemp.getLoanStatus()){
				return returnFailed(response,"未申请放款，不允许操作","99",loanId);
			}else if(Constants.LOAN_STATUS_4 ==  partnerTemp.getLoanStatus()){
				return returnFailed(response,"己放款成功，不允许操作","99",loanId);
			}

			Timestamp time = new Timestamp(new Date().getTime());
			
			ProjectPartner projectPartner = new ProjectPartner();
			projectPartner.setLoanId(loanId);

			projectPartner.setLoanResultTime(time.toString());
			if (result.equals("0")) {
				projectPartner.setLoanStatus(Constants.LOAN_STATUS_4);// 放款成功
				projectPartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_1);
				
				if (!StringUtil.isBlank(loan_time)) {
					//放款时间
					projectPartner.setPartnerLoanDate(loan_time.split(" ")[0]);
				}
				
				
			} else if (result.equals("1")) {
				projectPartner.setLoanStatus(Constants.LOAN_STATUS_5);// 放款失败
			} else if (result.equals("2")) {
				projectPartner.setLoanStatus(Constants.LOAN_STATUS_3);// 放款中
			}
			// 修改放款状态
			 int rows = clientPartner.updateLoanStatus(projectPartner);
			 
			if(rows != 1){
				return returnFailed(response,"操作失败","99",loanId);
			} 
			return returnSuccess(response,"操作成功",loanId);
		}catch(Exception e){
			logger.error(">>>>>>loanConfirm error", e);
			return returnFailed(response,"系统错误","99",loanId);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>loanConfirm clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}

	/**
	 * 还款回购确认
	 * 
	 * @param data
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 * @author: Administrator
	 * @date: 2016年3月28日 下午2:59:29
	 */
	@RequestMapping(value = "/loan-voucher-confirm", method = RequestMethod.POST)
	@ResponseBody
	public String loanVoucherConfirm(HttpServletRequest request, HttpServletResponse response) throws Exception{
		BaseClientFactory clientFactoryPartner = null;
		try {
			StringBuffer jb = new StringBuffer();
			String data = null;
			//读取接口参数
			BufferedReader reader = request.getReader();
			while ((data = reader.readLine()) != null){
				jb.append(data);
			}

			logger.info("method:approvalRes;data:" + jb);
			//接口参数转换成Map
			String[] str = jb.toString().split("&");
			JSONObject json = new JSONObject();
			for (int i = 0; i < str.length; i++) {
				String[] arr = str[i].split("=");
				json.put(arr[0], arr[1]);
			}
			//接口参数解密
			String resContent = YZTSercurityUtil.decryptResponse(json.toString(),XIAOYING_PARTNER_MD5);
			
			if(StringUtil.isBlank(resContent)){
				return returnFailed(response,"请求参数为空","99",null);
			}
			JSONObject respJson = JSONObject.parseObject(resContent);
			
			logger.info("还款回购确认(loan-voucher-confirm)回调参数： "+resContent.toString());
			
			if(respJson.get("loan_id") == null){
				return returnFailed(response,"参数loan_id为空","99",null);
			}else if(respJson.get("err_code") == null){
				return returnFailed(response,"参数err_code为空","99",null);
			}
			
			String loanId = respJson.getString("loan_id");
			String result = respJson.getString("err_code");// 0，确认收到，1未收到，2，确认中
			int type = respJson.getIntValue("type");	   // 1：还款凭证；2：回购凭证
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//验证状态
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setLoanId(loanId);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			if(tempPartner == null || tempPartner.getPid() == 0){
				return returnFailed(response,"数据不存在","99",loanId);
			}else if(Constants.REPAYMENT_REPURCHASE_STATUS_3 ==  tempPartner.getRepaymentRepurchaseStatus()){
				return returnFailed(response,"该数据己确认收到，不允许操作","99",loanId);
			}else if(tempPartner.getRepaymentRepurchaseType() != type){
				return returnFailed(response,"还款回购类型不一致，不允许操作","99",loanId);
			}
			
			ProjectPartner projectPartner = new ProjectPartner();
			projectPartner.setLoanId(loanId);
			Timestamp time = new Timestamp(new Date().getTime());
			projectPartner.setRpResultTime(time.toString());
			if (result.equals("0")) {
				projectPartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_3);// 确认收到
			} else if (result.equals("1")) {
				projectPartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_4);// 未收到
			} else if (result.equals("2")) {
				projectPartner.setRepaymentRepurchaseStatus(Constants.REPAYMENT_REPURCHASE_STATUS_5);// 确认中
			}
			 int rows = clientPartner.updateRepaymentInfo(projectPartner);

			if(rows != 1){
				return returnFailed(response,"操作失败","99",loanId);
			} 
			return returnSuccess(response,"操作成功",loanId);
			
		}catch(Exception e){
			logger.error(">>>>>>loanVoucherConfirm error", e);
			return returnFailed(response,"系统错误","99",null);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>loanVoucherConfirm clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
	}
 
	
	/**
	 * 返回成功
	 * @param response
	 * @param msg 提示
	 * @param loanId 放款id
	 * @return
	 * @throws Exception
	 */
	protected String returnSuccess(HttpServletResponse response ,String msg,String loanId) throws Exception{
		
/*	    response.setContentType("text/json;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");*/
		
		if(loanId != null){
			JSONObject dataJson = new JSONObject();
			dataJson.put("loan_id", loanId);
			dataJson.put("status", API_STATUS_SUCCESS);
			contentJson.put("data", dataJson);
		}

		contentJson.put("err_code", "0");
		contentJson.put("msg",  msg == null?"操作成功！":msg);
		
		logger.info("返回结果："+contentJson.toString());
		
		
		//测试不加密
		if("true".equals(IS_PARTNER_API_DEBUG)){
			response.getWriter().write(contentJson.toString());
			return null;
		}
		
		
		JSONObject resultJson = YZTSercurityUtil.encryptParamsResp(contentJson.toString(), XIAOYING_PARTNER_MD5);
		
		logger.info("返回成功："+resultJson.toString());
		
		response.getWriter().write(resultJson.toString());
		return null;
	}
	/**
	 * 返回失败
	 * @param response
	 * @param msg	提示
	 * @param status 接口状态
	 * @param loanId 放款id
	 * @return
	 * @throws Exception
	 */
	protected String returnFailed(HttpServletResponse response,String msg,String status,String loanId) throws Exception{
		
/*	    response.setContentType("text/json;charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");*/
		
		if(status  == null){
			status = "99";
		}
		
		if(loanId != null && !"".equals(loanId)){
			JSONObject dataJson = new JSONObject();
			dataJson.put("loan_id", loanId);
			dataJson.put("status", status);
			contentJson.put("data", dataJson);
		}
		
		contentJson.put("err_code", API_ERR_CODE_FAIL);
		contentJson.put("msg", msg == null?"接口调用失败！":msg);
		
		logger.info("返回结果："+contentJson.toString());
		
		
		//测试不加密
		if("true".equals(IS_PARTNER_API_DEBUG)){
			response.getWriter().write(contentJson.toString());
			return null;
		}
		
		
		JSONObject resultJson = YZTSercurityUtil.encryptParamsResp(contentJson.toString(), XIAOYING_PARTNER_MD5);
		logger.info("返回失败："+resultJson.toString());
		
		response.getWriter().write(resultJson.toString());
		return null;
	}
	
	
	
	/**
	 * 回调通知
	 */
	@RequestMapping(value = "/notify")
	@ResponseBody
	public String notify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		JSONObject resultJson = new JSONObject();
		try {
			
		    response.setContentType("text/json;charset=utf-8");
		    response.setHeader("Cache-Control", "no-cache");
			
			StringBuffer bodySb = new StringBuffer();
			String data = null;
			//读取接口参数
			BufferedReader reader = request.getReader();
			while ((data = reader.readLine()) != null){
				bodySb.append(data);
			}

			logger.info(">>>>>>notify bodySb:" + bodySb);
			//接口参数转换成Map
			String[] str = bodySb.toString().split("&");
			JSONObject json = new JSONObject();
			for (int i = 0; i < str.length; i++) {
				String[] arr = str[i].split("=");
				json.put(arr[0], arr[1]);
			}
			
			//统联
			if(TL_PARTNER_PARAM_PARTNERCODE_QFANG.equals(json.get("partner"))){
				resultJson = tLPartnerApiImpl.notify(json, request);
			}
			
			logger.info(">>>>>>notify resultJson:" + resultJson.toString());
			response.getWriter().write(resultJson.toString());
			return null;
		}catch(Exception e){
			logger.error("接口调用异常", e);
			resultJson.put("status", "1");
			resultJson.put("msg", "参数错误");
			response.setContentType("text/html;charset=UTF-8"); 
			response.getWriter().write(resultJson.toString());
			return null;
		}
	}
	
	
	/**
	 * 南粤银行 
	 * 2.4.资金申请处理结果回调
	 * 2.14.提前回款申请处理结果回调
	 */
	@RequestMapping(value = "/nybApplyNotify")
	@ResponseBody
	public void nybApplyNotify(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String uuid = UuidUtil.randomUUID();
//		response.setContentType("text/json;charset=utf-8");
		response.setContentType("application/x-www-form-urlencoded;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			
/* 			StringBuffer bodySb = new StringBuffer();
			String data = null;
			//读取接口参数
			BufferedReader reader = request.getReader();
			while ((data = reader.readLine()) != null){
				bodySb.append(data);
			}
			   
			logger.info("uuid:"+uuid+",nybApplyNotify bodySb:" + bodySb);
			
			String bodySb2 = bodySb.toString();
			String szrDataStr = bodySb2.split("szrData=")[1];*/
			
			String szrDataStr = request.getParameter("szrData");
			
			
			logger.info("uuid:"+uuid+",nybApplyNotify szrDataStr:" + szrDataStr);
			
			//请求参数
			RequestParams requestParams = ((RequestParams) JSONObject.parseObject(szrDataStr, RequestParams.class));
			
			ResponseParams responseParams = null;
		
			if(DaJuConstant.ServiceCode.LEND_APPLY.getCode().equals(requestParams.getHeader().getService_code())){
				 //资金申请
				 responseParams = daJuPartnerApiImpl.applyNotify(uuid, requestParams);
			}else if(DaJuConstant.ServiceCode.PREPAYMENT.getCode().equals(requestParams.getHeader().getService_code())){
				//提前还款申请
				responseParams = daJuPartnerApiImpl.prepaymentNotify(uuid, requestParams);
			}else{
				logger.info("uuid:"+uuid+",nybApplyNotify not service code:");
			}
			 
			logger.info("uuid:"+uuid+",nybApplyNotify responseParams:" + JSONObject.toJSONString(responseParams));
			response.getWriter().write(JSONObject.toJSONString(responseParams));
		}catch(Exception e){
			logger.error("uuid:"+uuid+"nybApplyNotify error",e);
			response.getWriter().write("deal params error");
		}
	}
 
	 
	
	
}
