/**
 * @Title: PartnerCustomerBankController.java
 * @Package com.xlkfinance.bms.web.controller
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: CZZ
 * @date: 2017年9月19日 下午7:52:59
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.partner;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.PartnerConstant;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBank;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBankService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.web.api.partnerapi.daju.constant.DaJuConstant;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.Message;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.ResponseParams;
import com.xlkfinance.bms.web.api.partnerapi.impl.DaJuPartnerApiImpl;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.UuidUtil;


/**
 * 机构客户银行开户
 * @author chenzhuzhen
 * @date 2017年9月19日 下午7:52:59
 */
@Controller
@RequestMapping("/partnerCustomerBankController")
public class PartnerCustomerBankController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(PartnerCustomerBankController.class);
	
	@Resource(name = "daJuPartnerApiImpl")
	private DaJuPartnerApiImpl daJuPartnerApiImpl;		//大桔接口实现类
	
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");

	/**
	 * 列表页面
	 */
	@RequestMapping(value = "/partnerCustomerBankList_index")
	public String partnerCustomerBankList_index(){
		return "partner/partner_customer_bank_list";
	}
	
	
	/**
	 * 列表数据
	 */
	@RequestMapping(value = "/getPartnerCustomerBankList")
	@ResponseBody
	public void getPartnerCustomerBankList(BizPartnerCustomerBank bizPartnerCustomerBank, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询机构客户银行开户列表，入参："+bizPartnerCustomerBank);
		List<BizPartnerCustomerBank> result = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		BaseClientFactory clientFactory = null;
		try {
			
			clientFactory = getFactory(BusinessModule.MODUEL_PARTNER, "BizPartnerCustomerBankService");
			BizPartnerCustomerBankService.Client client =(BizPartnerCustomerBankService.Client) clientFactory.getClient();
			result = client.selectList(bizPartnerCustomerBank);
			total = client.selectTotal(bizPartnerCustomerBank);
		} catch (Exception e) {
			logger.error("查询机构客户银行开户列表失败：" + e.getMessage());
		}finally {
			destroyFactory(clientFactory);
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	

	
	
	/**
	 * 用户开户验证码获取接口
	 * @param bankMobileNo   借款人银行预留手机号码
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getOtp", method = RequestMethod.POST)
	public void openAccount(String bankMobileNo ,  HttpServletResponse response) throws Exception {
		
		String uuid = UuidUtil.randomUUID();
		
		if(StringUtils.isEmpty(bankMobileNo)){
			fillReturnJson(response, false, "参数bankMobileNo不能为空!");
			return;
		}
		
		ResponseParams responseParams = null;
		try {
			if("true".equals(IS_PARTNER_API_DEBUG)){
				responseParams = new ResponseParams();
				Message message = new Message();
				message.setStatus(DaJuConstant.MessageCode.SUCCESS.getCode());
				message.setDesc("ok");
				responseParams.setMessage(message);
				Map<String,Object> bodyMap = new HashMap<String,Object>();
				bodyMap.put("return_code", DaJuConstant.ReturnCode.SUCCESS.getCode());
				bodyMap.put("return_msg", "操作成功");
				responseParams.setBody(JSONObject.toJSONString(bodyMap));
			}else{
			 
				Map<String,Object> bodyMap = new HashMap<String,Object>();
				bodyMap.put("mobile_no", bankMobileNo);
				responseParams = daJuPartnerApiImpl.getOtp(uuid,bodyMap);
				logger.info("uuid:"+uuid+",getOtp响应参数:"+JSONObject.toJSONString(responseParams));
			}
			if(responseParams == null){
				fillReturnJson(response, false,"查询失败，网络请求失败");
				return;
			}
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				fillReturnJson(response, false, responseParams.getMessage().getDesc());
				return;
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			//只要一个失败，即都失败
			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))
					|| DaJuConstant.IdCheck.C_48001.getCode().equals(bodyJson.getString("id_check"))
					|| DaJuConstant.AccountOpen.C_49001.getCode().equals(bodyJson.getString("account_open"))
					|| DaJuConstant.CardBinding.C_50001.getCode().equals(bodyJson.getString("card_binding"))){
				fillReturnJson(response, false, bodyJson.getString("return_msg"));
				return;
			}
			fillReturnJson(response, true, bodyJson.getString("return_msg"));
			return ;
		} catch (Exception e) {
			logger.error("uuid:"+uuid+",getOtp error：" + e);
		} finally {
		}
	}
	
	
	
	/**
	 * 用户开户绑卡接口
	 * @param acctId
	 * @param bankCard
	 * @param bankMobileNo
	 * @param otp 验证码
	 * @param response
	 * @throws Exception
	 *
	 */
	@ResponseBody
	@RequestMapping(value = "/openAccount", method = RequestMethod.POST)
	public void openAccount(int acctId, String bankCard,String bankMobileNo ,String otp,  HttpServletResponse response) throws Exception {
		
		String uuid = UuidUtil.randomUUID();
		
		if(!(acctId > 0)){
			fillReturnJson(response, false, "参数acctId不能为空!");
			return;
		}else if(StringUtils.isEmpty(bankCard)){
			fillReturnJson(response, false, "参数bankCard不能为空!");
			return;
		}else if(StringUtils.isEmpty(bankMobileNo)){
			fillReturnJson(response, false, "参数bankMobileNo不能为空!");
			return;
		}else if(StringUtils.isEmpty(otp)){
			fillReturnJson(response, false, "参数otp不能为空!");
			return;
		}
		
		BaseClientFactory clientFactoryCusBank = getFactory(BusinessModule.MODUEL_PARTNER, "BizPartnerCustomerBankService");
		BaseClientFactory clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		BaseClientFactory clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
		try {
			BizPartnerCustomerBankService.Client clientCusBank = (BizPartnerCustomerBankService.Client) clientFactoryCusBank.getClient();
			CusPerService.Client clientCus = (CusPerService.Client) clientFactoryCus.getClient();
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			
			//查询是否己经开户过了
			BizPartnerCustomerBank queryCustomerBank = new BizPartnerCustomerBank();
			queryCustomerBank.setAcctId(acctId);
			queryCustomerBank.setStatus(Constants.COMM_YES);	//成功
			queryCustomerBank.setPage(-1);
			
			List<BizPartnerCustomerBank> tempList =  clientCusBank.selectList(queryCustomerBank);
			if(!CollectionUtils.isEmpty(tempList)){
				fillReturnJson(response, false,"操作失败，该用户己经开户绑卡，请刷新重新操作");
				return;
			}
			
			
			CusDTO  cusDTO = clientCus.getPersonalListByAcctId(acctId);
			//字典service
			if(cusDTO.getCertType() == 0){
				fillReturnJson(response, false, "客户证件类型不能为空!");
				return;
			}
			//证件类型
			String cert_type="";
			String	certType = clientSys.getSysLookupValByName(cusDTO.getCertType());
			if("身份证".equals(certType)){
				cert_type=DaJuConstant.CertType.C_06000.getCode();
			}else if("护照".equals(certType)){
				cert_type=DaJuConstant.CertType.C_06001.getCode();
			}else if("驾照".equals(certType)){
				cert_type=DaJuConstant.CertType.C_06002.getCode();
			}else{
				fillReturnJson(response, false, "客户证件类型不能为空!");
				return;
			}
			//-----------------------------华丽的分割线-------------------测试-----------------------------------
			ResponseParams responseParams = null;
			SysLookupVal  cust_ipVal = null;
			SysLookupVal  cust_macVal = null;
			if("true".equals(IS_PARTNER_API_DEBUG)){
				responseParams = new ResponseParams();
				Message message = new Message();
				message.setStatus(DaJuConstant.MessageCode.SUCCESS.getCode());
				message.setDesc("ok");
				responseParams.setMessage(message);
				Map<String,Object> bodyMap = new HashMap<String,Object>();
				
				bodyMap.put("id_check", DaJuConstant.IdCheck.C_48000.getCode());
				bodyMap.put("account_open", DaJuConstant.AccountOpen.C_49000.getCode());
				bodyMap.put("card_binding", DaJuConstant.CardBinding.C_50000.getCode());
				bodyMap.put("return_code", DaJuConstant.ReturnCode.SUCCESS.getCode());
				bodyMap.put("return_msg", "操作成功");
				responseParams.setBody(JSONObject.toJSONString(bodyMap));
				
			}else{
				//字典service   客户设备信息
				cust_ipVal =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_PC_SERVER,Constants.SYS_LOOKUP_PARTNER_PC_SERVER_IP);
				cust_macVal =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_PC_SERVER,Constants.SYS_LOOKUP_PARTNER_PC_SERVER_MAC);
				if(cust_ipVal == null || StringUtil.isBlank(cust_ipVal.getLookupDesc())
						|| cust_macVal == null || StringUtil.isBlank(cust_macVal.getLookupDesc()) ){
					fillReturnJson(response, false, "未配置服务器客户mac设备信息，请联系管理员");
					return ;
				}
			
				Map<String,Object> bodyMap = new HashMap<String,Object>();
				bodyMap.put("cert_type", cert_type);
				bodyMap.put("cert_id", cusDTO.getCertNumber());
				bodyMap.put("full_name", cusDTO.getChinaName());
				bodyMap.put("mobile_no", cusDTO.getPerTelephone());	 
				bodyMap.put("cust_acct_no", bankCard);	 			//借款人银行卡账号
				bodyMap.put("cust_bank_mobile", bankMobileNo);	 	//借款人银行卡预留手机号
				bodyMap.put("cust_ip", cust_ipVal.getLookupDesc() );	 		//客户设备ip地址
				bodyMap.put("cust_mac", cust_macVal.getLookupDesc());	 	//客户设备mac地址
				bodyMap.put("otp", otp);	 	//验证码
				
				responseParams = daJuPartnerApiImpl.openAccount(uuid,bodyMap);
				logger.info("uuid:"+uuid+",openAccount 响应参数:"+JSONObject.toJSONString(responseParams));
			}
			
			if(responseParams == null){
				fillReturnJson(response, false,"查询失败，网络请求失败");
				return;
			}
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				fillReturnJson(response, false, responseParams.getMessage().getDesc());
				return;
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			//只要一个失败，即都失败
			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))
					|| DaJuConstant.IdCheck.C_48001.getCode().equals(bodyJson.getString("id_check"))
					|| DaJuConstant.AccountOpen.C_49001.getCode().equals(bodyJson.getString("account_open"))
					|| DaJuConstant.CardBinding.C_50001.getCode().equals(bodyJson.getString("card_binding"))){
				fillReturnJson(response, false, bodyJson.getString("return_msg"));
				return;
			}
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			//添加客户开户绑卡信息
			BizPartnerCustomerBank insertCusBank = new BizPartnerCustomerBank();
			insertCusBank.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			insertCusBank.setAcctId(acctId);
			insertCusBank.setCustomerIdCard(cusDTO.getCertNumber());
			insertCusBank.setCustomerName(cusDTO.getChinaName());
			insertCusBank.setMobileNo(cusDTO.getPerTelephone());
			insertCusBank.setBankCard(bankCard);
			insertCusBank.setBankMobileNo(bankMobileNo);
			insertCusBank.setIp(cust_ipVal != null ? cust_ipVal.getLookupDesc() : "");
			insertCusBank.setMac(cust_macVal != null ? cust_macVal.getLookupDesc(): "");
			insertCusBank.setStatus(Constants.COMM_YES);
			insertCusBank.setOperator(getShiroUser().getPid());
			insertCusBank.setCreateTime(nowTime.toString());
			insertCusBank.setUpdateTime(nowTime.toString());
			
			fillReturnJson(response, true, bodyJson.getString("return_msg"));
			return ;
		} catch (Exception e) {
			logger.error("uuid:"+uuid+",openAccount error：" + e);
		} finally {
			destroyFactory(clientFactoryCusBank);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
		}
	}
}
