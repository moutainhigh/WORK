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

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.PartnerConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.partner.PartnerApprovalRecord;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.web.api.partnerapi.daju.constant.DaJuConstant;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.YZTSercurityUtil;

/**
 * 测试资金机构API
 * @author chenzhuzhen
 * @date 2016年11月8日 上午11:36:20
 */
@Controller
@RequestMapping("/testOpenApi")
public class TestApiController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(TestApiController.class);
	 
	/**
	 * RSA最大解密密文大小
	 */
	public final static String CONTENT_TYPE = "UTF-8";
	public static final String KEY_ALGORITHM = "RSA";
	//Q房私钥
	private static String QFANG_PARTNER_PRIVATE_KEY = PropertiesUtil.getValue("qfang.partner.private.key");
	//Q房公钥
	private static String QFANG_PARTNER_PUBLIC_KEY = PropertiesUtil.getValue("qfang.partner.public.key");
	//小赢MD5
	private static String XIAOYING_PARTNER_MD5 = PropertiesUtil.getValue("xiaoying.partner.md5");
	
	//统联合作机构代码
	private static String TL_PARTNER_PARAM_PARTNERCODE = PropertiesUtil.getValue("tl.partner.param.partnercode.qfang");
	
	/**大桔公钥*/
	private static String DAJU_QFANG_PUBLIC_KEY = PropertiesUtil.getValue("nyyh.partner.public.key");

	
	
	/**
	 * 加密小赢
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "encryptParamsByXy")
	public void encryptParamsByXy(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info(">>>>>>params:"+params);
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParamsComm(params.toString(), XIAOYING_PARTNER_MD5,
				QFANG_PARTNER_PUBLIC_KEY, "XIAOYING_BEIJING_QF");
		
		logger.info(">>>>>>paramMap:"+JSONObject.toJSONString(paramMap));
		
		
		StringBuffer sb = new StringBuffer();
		for (HttpRequestParam httpRequestParam : paramMap) {
			sb.append("&").append(httpRequestParam.getParaName()).append("=").append(httpRequestParam.getParaValue());
		}
		resultMap.put("paramList", sb.substring(1));
		
		fillReturnJson(response, true, "操作成功!",resultMap);
		return;
	}
	/**
	 * 加密统联
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "encryptParamsByTl")
	public void encryptParamsByTl(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info(">>>>>>params:"+params);
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParamsComm(params.toString(), XIAOYING_PARTNER_MD5,
				QFANG_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE);
		
		logger.info(">>>>>>paramMap:"+JSONObject.toJSONString(paramMap));
		
		StringBuffer sb = new StringBuffer();
		for (HttpRequestParam httpRequestParam : paramMap) {
			sb.append("&").append(httpRequestParam.getParaName()).append("=").append(httpRequestParam.getParaValue());
		}
		if(!StringUtil.isBlank(params.getString("method"))){
			sb.append("&").append("method").append("=").append(params.getString("method"));
		}
		
		resultMap.put("paramList", sb.substring(1));
		
		fillReturnJson(response, true, "操作成功!",resultMap);
		return;
	}
	
	
	/**
	 * 设置点融贷款状态（到文件中）
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "setLoanStatusByDr")
	public void setLoanStatusByDr(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info(">>>>>>params:"+params);
		
		try{
			String filePath  = getClassPath()  +"dr_status.txt";
			logger.info(">>>>>>filePath:"+filePath);
			
			FileUtil.creatFile(filePath);
			
			FileUtil.writeTxtFile(params.toString(), filePath,false,"UTF-8");
		}catch(Exception e){
			logger.error("error", e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}
		
		fillReturnJson(response, true, "操作成功!");
		return;
	}
	
	
	/**
	 * 设置点融提前还款列表
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "setBeforeRefundListByDr")
	public void setBeforeRefundListByDr(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info(">>>>>>params:"+params);
		
		try{
			String filePath  = getClassPath()  +"dr_before_refund_list.txt";
			logger.info(">>>>>>filePath:"+filePath);
			
			FileUtil.creatFile(filePath);
			
			FileUtil.writeTxtFile(params.toString(), filePath,false,"UTF-8");
		}catch(Exception e){
			logger.error("error", e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}
		fillReturnJson(response, true, "操作成功!");
		return;
	}
	
	/**
	 * 设置点融分期还款计划
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "setPaymentPlanByDr")
	public void setPaymentPlanByDr(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		logger.info(">>>>>>params:"+params);
		try{
			String filePath  = getClassPath() +"dr_payment_plan.txt";
			
			logger.info(">>>>>>filePath:"+filePath);
			
			FileUtil.creatFile(filePath);
			
			FileUtil.writeTxtFile(params.toString(), filePath,false,"UTF-8");
		}catch(Exception e){
			logger.error("error", e);
			fillReturnJson(response, false, "操作失败!");
			return;
		}
		fillReturnJson(response, true, "操作成功!");
		return;
	}
	
	
	
	
	
	/**
	 * 审批回调
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "applyNotifyByNyyh")
	public void applyNotifyByNyyh(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
		
		BaseClientFactory factoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
		try{
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) factoryPartner.getClient();
			
			//资金申请状态
			String lend_apply_stat = params.getString("lend_apply_stat");
			//平台交易编号
			String apply_deal_id = params.getString("apply_deal_id");
			//返回的消息提示
			String return_msg = params.getString("return_msg");
			
			if(StringUtil.isBlank(lend_apply_stat) ||StringUtil.isBlank(apply_deal_id) ){
				fillReturnJson(response, false, "操作失败!");
				return;
			}
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setLoanId(apply_deal_id); 
			queryPartner.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			queryPartner.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner.getPid() == 0  ){
				fillReturnJson(response, false, "该交易数据不存在");
				return;
			}
			
			//判断该状态是否可以操作
			int  approvalStatus =  tempPartner.getApprovalStatus(); //审批状态
			int  requestStatus = tempPartner.getRequestStatus();	//申请状态
			
			if(approvalStatus == PartnerConstant.ApprovalStatus.APPROVAL_PASS.getCode()){
				fillReturnJson(response, false, "己审批通过不允许操作");
				return;
			}else if(approvalStatus == PartnerConstant.ApprovalStatus.APPROVAL_NO_PASS.getCode()){
				fillReturnJson(response, false, "审批己拒绝不允许操作");
				return;
			}else if(requestStatus == PartnerConstant.RequestStatus.APPLY_NO_PASS.getCode()){
				fillReturnJson(response, false, "申请己拒绝不允许操作");
				return;
			}
			
			if(!(requestStatus == PartnerConstant.RequestStatus.APPLY_PASS.getCode() && 
					approvalStatus == PartnerConstant.ApprovalStatus.APPROVAL_ING.getCode())){
				fillReturnJson(response, false, "该状态不允许操作");
				return;
			}
			
			//更新数据----------------------------------------------------------------
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			ProjectPartner updatePartner = new ProjectPartner();	
			updatePartner.setPid(tempPartner.getPid());
			boolean isUpdateNotify = true;	//是否更新审核记录
			if(lend_apply_stat.equals(DaJuConstant.ApplyStat.APPLY_NO_PASS.getCode())  ){
				//申请不通过
				updatePartner.setRequestStatus(PartnerConstant.RequestStatus.APPLY_NO_PASS.getCode());
				updatePartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_NO_PASS.getCode());
			}else if(lend_apply_stat.equals(DaJuConstant.ApplyStat.APPROVAL_NO_PASS.getCode())){
				//审批不通过
				updatePartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_NO_PASS.getCode());
			}else if(lend_apply_stat.equals(DaJuConstant.ApplyStat.APPROVAL_PASS.getCode())){
				//审批通过
				updatePartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_PASS.getCode());
				updatePartner.setApproveMoney(tempPartner.getLoanAmount());
				
				//默认设置确认在款
				updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
				updatePartner.setConfirmLoanDays(tempPartner.getApplyDate());
				updatePartner.setConfirmLoanMoney(tempPartner.getLoanAmount());
				updatePartner.setConfirmLoanRequestTime(nowTime.toString());
				updatePartner.setConfirmLoanResultTime(nowTime.toString());
				
			}else{
				fillReturnJson(response, false, "该状态不在回调范围内");
				return;
			}
			
			//审批意见
			updatePartner.setApprovalComment(return_msg);
			updatePartner.setApprovalTime(nowTime.toString()); //设置审批时间
			
			//修改审核回调信息 
			int rows = clientPartner.updateProjectPartner(updatePartner);
			if(rows != 1){
				fillReturnJson(response, false, "操作失败");
				return;
			} 
			
			if(isUpdateNotify){
				PartnerApprovalRecord updateRecord = new PartnerApprovalRecord();
				updateRecord.setPartnerId(tempPartner.getPid());
				updateRecord.setLoanId(apply_deal_id);
				updateRecord.setApprovalComment(return_msg);
				updateRecord.setApprovalTime(nowTime.toString());
				//审批状态保持一致
				updateRecord.setApprovalStatus(updatePartner.getApprovalStatus());
				updateRecord.setIsNotify(Constants.IS_NOTIFY_2);
				updateRecord.setApproveMoney(tempPartner.getLoanAmount());
				updateRecord.setQueryIsNotify(Constants.IS_NOTIFY_1);
				//修改审核记录
				clientPartner.updatePartnerApprovalRecord(updateRecord);
			}
	 
			 fillReturnJson(response, true, "操作成功");
			 return;
		}catch(Exception e){
			logger.error("applyNotify error",e);
			fillReturnJson(response, true, "操作失败，系统异常");
			return;
		}finally {
			 destroyFactory(factoryPartner);
		}
	}
	
	/**
	 * 放款通知(南粤银行)
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "loanNotifyByNyyh")
	public void loanNotifyByNyyh(@RequestBody JSONObject params,HttpServletRequest request, HttpServletResponse response){
		
		BaseClientFactory factoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
		try{
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) factoryPartner.getClient();
		 
			String apply_deal_id = params.getString("apply_deal_id");
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			
			ProjectPartner updatePartner = new ProjectPartner();
			//资方id
			updatePartner.setLoanId(apply_deal_id);
			//平台申请到资方业务编号
			updatePartner.setPartnerPlatformOrderCode(System.currentTimeMillis()+"");
			//机构放款日期
			updatePartner.setPartnerLoanDate(DateUtils.getCurrentDate());
			//放款通知时间
			updatePartner.setLoanResultTime(nowTime.toString());
			//放款状态（成功）
			updatePartner.setLoanStatus(PartnerConstant.LoanStatus.LOAN_SUCCESS.getCode());
			
			
			ProjectPartner query = new ProjectPartner();
			query.setLoanId(apply_deal_id);
			query.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			List<ProjectPartner> tempPartnerList = clientPartner.findProjectPartnerInfoList(query);
			
			if(CollectionUtils.isEmpty(tempPartnerList)){
				fillReturnJson(response, false, "数据不存在");
				return;
			}
			updatePartner.setPid(tempPartnerList.get(0).getPid());
			clientPartner.updateProjectPartner(updatePartner);
			 fillReturnJson(response, true, "操作成功");
			 return;
		}catch(Exception e){
			logger.error("loanNotify_nyyh error",e);
			fillReturnJson(response, false, "操作失败，系统异常");
			return;
		}finally {
			 destroyFactory(factoryPartner);
		}
	}
	
	/**
	 * 还款通知(南粤银行)
	 * @param params
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "prepaymentNotifyByNyyh")
	public void prepaymentNotifyByNyyh(@RequestBody JSONObject bodyJson,HttpServletRequest request, HttpServletResponse response){
		
		BaseClientFactory factoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
		try{
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) factoryPartner.getClient();
			 
			//提前回款申请状态
			String payment_stat = bodyJson.getString("payment_stat");
			//平台交易编号
			String apply_deal_id = bodyJson.getString("apply_deal_id");
			//返回的消息提示
			String return_msg = bodyJson.getString("return_msg");
			//返回码
			String return_code = bodyJson.getString("return_code");
			
			if(StringUtil.isBlank(payment_stat) ||StringUtil.isBlank(apply_deal_id) ){
				
				fillReturnJson(response, false, "操作失败，请求参数为空");
				return ;
			}
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setLoanId(apply_deal_id); 
			queryPartner.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			queryPartner.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner.getPid() == 0  ){
				fillReturnJson(response, false, "操作失败，该交易数据不存在");
				return ;
			}
			
			//判断该状态是否可以操作
			int  repaymentRepurchaseStatus =  tempPartner.getRepaymentRepurchaseStatus(); //还款状态
			
			if(repaymentRepurchaseStatus == PartnerConstant.RepaymentRepurchaseStatus.SUCCESS.getCode()){
				
				fillReturnJson(response, false, "操作失败，己还款成功不允许操作");
				return ;
			}
			
			if(repaymentRepurchaseStatus == PartnerConstant.RepaymentRepurchaseStatus.FAIL.getCode()
				&& payment_stat.equals(DaJuConstant.PaymentStat.FAIL.getCode())){
				fillReturnJson(response, false, "操作失败，状态未改变不允许操作");
				return ;
			}
			
			//更新数据----------------------------------------------------------------
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			ProjectPartner updatePartner = new ProjectPartner();	
			updatePartner.setPid(tempPartner.getPid());
			
			//还款通知时间
			updatePartner.setRpResultTime(nowTime.toString());
			//机构实际还款到帐日(默认为还款日)
			updatePartner.setPartnerRealRefundDate(tempPartner.getRefundDate());
			
			//还款成功
			if(DaJuConstant.PaymentStat.SUCCESS.getCode().equals(payment_stat)){
				updatePartner.setRepaymentRepurchaseStatus(PartnerConstant.RepaymentRepurchaseStatus.SUCCESS.getCode());
			}else if(DaJuConstant.PaymentStat.FAIL.getCode().equals(payment_stat)){
				//还款失败
				updatePartner.setRepaymentRepurchaseStatus(PartnerConstant.RepaymentRepurchaseStatus.FAIL.getCode());
			}else{
				
				fillReturnJson(response, false, "操作失败，还款状态不存在");
				return ;
			}
			 
			if(clientPartner.updateProjectPartner(updatePartner) == 0 ){
				fillReturnJson(response, false, "操作失败，更新异常");
				return ;
			}
			
			fillReturnJson(response, true, "操作成功");
			return;
		}catch(Exception e){
			logger.error("prepaymentNotifyByNyyh error",e);
			fillReturnJson(response, false, "操作失败，系统异常");
			return;
		}finally {
			destroyFactory(factoryPartner);
		}
	}
	
	
	
	
	
	
	
	
	
	
	  	
}
