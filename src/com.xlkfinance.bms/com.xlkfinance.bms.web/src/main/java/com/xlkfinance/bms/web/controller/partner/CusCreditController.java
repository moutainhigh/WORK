/**
 * @Title: ProjectPartnerController.java
 * @Package com.xlkfinance.bms.web.controller
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月18日 下午3:56:31
 * @version V1.0
 */
package com.xlkfinance.bms.web.controller.partner;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.PartnerConstant;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
import com.xlkfinance.bms.rpc.partner.CusCreditInfoService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.web.api.partnerapi.daju.constant.DaJuConstant;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.Message;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.ResponseParams;
import com.xlkfinance.bms.web.api.partnerapi.impl.DaJuPartnerApiImpl;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.UuidUtil;


/**
 * 客户征信信息表
 * @author chenzhuzhen
 * @date 2017年2月15日 下午7:36:50
 */
@Controller
@RequestMapping("/cusCreditController")
public class CusCreditController extends BaseController{

	private Logger logger = LoggerFactory.getLogger(CusCreditController.class);
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	
	@Resource(name = "daJuPartnerApiImpl")
	private DaJuPartnerApiImpl daJuPartnerApiImpl;		//大桔接口实现类
	
	/**
	 * 客户征信列表页面
	 */
	@RequestMapping(value = "/cusCreditIndex")
	public String cusCreditIndex(){
		return "partner/cus_credit_list";
	}
	
	
	/**
	 * 查询客户片信列表
	 */
	@RequestMapping(value = "/cusCreditList")
	@ResponseBody
	public void cusCreditList(CusCreditInfo cusCreditInfo, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询客户片信列表，入参："+cusCreditInfo);
		Map<String, Object> map = new HashMap<String, Object>();
		List<CusCreditInfo> result = null;
		int total = 0;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_PARTNER, "CusCreditInfoService");
			CusCreditInfoService.Client client =(CusCreditInfoService.Client) clientFactory.getClient();
			
			result = client.selectCusCreditList(cusCreditInfo);
			total = client.selectCusCreditTotal(cusCreditInfo);
		} catch (Exception e) {
			logger.error("获取机构合作项目列表失败：" + e.getMessage());
		}finally {
			destroyFactory(clientFactory);
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	
	/**
	 * 查询客户征信信息(并添加征信记录)
	 * @param cusPerson
	 * @param cusPerson
	 * @param response
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/searchCusCredit", method = RequestMethod.POST)
	public void searchCusCredit(int acctId, int projectId, int businessType, HttpServletResponse response) throws Exception {
		
		
		String uuid = UuidUtil.randomUUID();
		
		
		
		BaseClientFactory clientFactoryCredit = getFactory(BusinessModule.MODUEL_PARTNER, "CusCreditInfoService");;
		BaseClientFactory clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
		BaseClientFactory clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
		BaseClientFactory clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
		try {
			CusCreditInfoService.Client clientCredit = (CusCreditInfoService.Client) clientFactoryCredit.getClient();
			CusPerService.Client clientCus = (CusPerService.Client) clientFactoryCus.getClient();
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			
			
			
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
			
			//默认交易
			String business_type = DaJuConstant.BusinessType.C_51000.getCode();
			if(businessType == 0 ){
				businessType = PartnerConstant.BUSINESS_TYPE_1;
			}
			if(businessType == 1){
				//交易
				business_type = DaJuConstant.BusinessType.C_51000.getCode();
				businessType = PartnerConstant.BUSINESS_TYPE_1;
			}else if(businessType == 2){
				//非交易
				business_type = DaJuConstant.BusinessType.C_51001.getCode();	//非交易
				businessType = PartnerConstant.BUSINESS_TYPE_2;
			}else{
				if(projectId > 0 ){
					Project tempProject =  clientProject.getProjectInfoById(projectId);
					if(!StringUtil.isBlank(tempProject.getBusinessTypeText()) 
							&& tempProject.getBusinessTypeText().contains("非交易")){
						business_type = DaJuConstant.BusinessType.C_51001.getCode();	//非交易
						businessType = PartnerConstant.BUSINESS_TYPE_2;
					} 
				}
			}
			
			
			//-----------------------------华丽的分割线-------------------测试-----------------------------------
			ResponseParams responseParams = null;
			if("true".equals(IS_PARTNER_API_DEBUG)){
				
				responseParams = new ResponseParams();
				
				Message message = new Message();
				message.setStatus(DaJuConstant.MessageCode.SUCCESS.getCode());
				message.setDesc("ok");
				
				responseParams.setMessage(message);
				
				Map<String,Object> bodyMap = new HashMap<String,Object>();
				
				bodyMap.put("credit_deal_id", new Date().getTime());
				bodyMap.put("overdue", "N");
				bodyMap.put("result1", "N");
				bodyMap.put("result2", "N");
				bodyMap.put("result3", "N");
				bodyMap.put("result4", "N");
				bodyMap.put("result5", "N");
				bodyMap.put("result6", "N");
				bodyMap.put("return_code", DaJuConstant.ReturnCode.SUCCESS_CREDIT.getCode());
				
				responseParams.setBody(JSONObject.toJSONString(bodyMap));
				
			}else{
			
				Map<String,Object> bodyMap = new HashMap<String,Object>();
				bodyMap.put("cert_type", cert_type);
				bodyMap.put("cert_id", cusDTO.getCertNumber());
				bodyMap.put("full_name", cusDTO.getChinaName());
				bodyMap.put("query_reason", DaJuConstant.QueryReason.C_35000.getCode());	//默认，贷款审批
				bodyMap.put("business_type", business_type);	 
				
				responseParams = daJuPartnerApiImpl.creditReport(uuid,bodyMap);
				logger.info("uuid:"+uuid+",searchCusCredit 响应参数:"+JSONObject.toJSONString(responseParams));
				
			}
			
			
			if(responseParams == null){
				fillReturnJson(response, false,"查询失败，网络请求失败");
				return;
			}
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				fillReturnJson(response, false,"查询失败，"+responseParams.getMessage().getDesc());
				return;
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			if(!DaJuConstant.ReturnCode.SUCCESS_CREDIT.getCode().equals(bodyJson.getString("return_code"))){
				fillReturnJson(response, false,"查询失败，"+ bodyJson.getString("return_msg"));
				return;
			}
			
			//添加客户征信信息
			CusCreditInfo insertCusCreditInfo = new CusCreditInfo();
			insertCusCreditInfo.setAcctId(acctId);
			insertCusCreditInfo.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			insertCusCreditInfo.setCreditDealId(bodyJson.getString("credit_deal_id"));	//征信交易编号
//			insertCusCreditInfo.setRecord(bodyJson.getString("record"));	//征信白户识别码，人行征信为白户
			
			insertCusCreditInfo.setOverdue(bodyJson.getString("overdue"));	//当前逾期否决码
			insertCusCreditInfo.setResult1(bodyJson.getString("result1"));	//M4否决码
			insertCusCreditInfo.setResult2(bodyJson.getString("result2"));	//3个月综合否决码
			insertCusCreditInfo.setResult3(bodyJson.getString("result3"));	//异常负债否决码
			insertCusCreditInfo.setResult4(bodyJson.getString("result4"));	//对外担保负债否决码
			insertCusCreditInfo.setResult5(bodyJson.getString("result5"));	//失信信息否决码
			insertCusCreditInfo.setResult6(bodyJson.getString("result6"));	//查询记录否决码
			insertCusCreditInfo.setBusinessType(businessType);				//业务类型
			
			StringBuffer remarkSb = new StringBuffer();
			remarkSb.append("</br>");
			remarkSb.append("征信交易编号:").append(bodyJson.getString("credit_deal_id")).append("</br>");
//			remarkSb.append("征信白户识别码:").append(bodyJson.getString("record")).append("</br>");
			remarkSb.append("当前逾期否决码:").append(bodyJson.getString("overdue")).append("</br>");
			remarkSb.append("M4否决码:").append(bodyJson.getString("result1")).append("</br>");
			remarkSb.append("3个月综合否决码:").append(bodyJson.getString("result2")).append("</br>");
			remarkSb.append("异常负债否决码:").append(bodyJson.getString("result3")).append("</br>");
			remarkSb.append("对外担保负债否决码:").append(bodyJson.getString("result4")).append("</br>");
			remarkSb.append("失信信息否决码:").append(bodyJson.getString("result5")).append("</br>");
			remarkSb.append("查询记录否决码:").append(bodyJson.getString("result6")).append("</br>");
			
			clientCredit.insert(insertCusCreditInfo);
			fillReturnJson(response, true, "操作成功,"+remarkSb);
			return ;
		} catch (Exception e) {
			logger.error("uuid:"+uuid+",searchCusCredit error：" + e);
		} finally {
			destroyFactory(clientFactoryCredit);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
		}
	}
	
	
	
	
	
	
	
}
