package com.xlkfinance.bms.web.api.partnerapi.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.web.api.partnerapi.BasePartnerApi;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.YZTSercurityUtil;
/**
 * 小赢接口实现类
 * @author chenzhuzhen
 * @date 2016年11月16日 下午12:18:13
 */
@Service("xYPartnerApiImpl")
public class XYPartnerApiImpl extends BaseController implements BasePartnerApi {
	private Logger logger = LoggerFactory.getLogger(XYPartnerApiImpl.class);
	
	
	//小赢白名单操作用户
	private static String XIAOYING_PARAM_ADD_USER = PropertiesUtil.getValue("xiaoying.partner.param.add.user");
	
	//小赢接口地址
	private static String XIAOYING_PARTNER_API_URL = PropertiesUtil.getValue("xiaoying.partner.api.url");
	
	//小赢MD5
	private static String XIAOYING_PARTNER_MD5 = PropertiesUtil.getValue("xiaoying.partner.md5");
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	
	
	@Override
	public JSONObject apply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientEstateFactory = null;
		BaseClientFactory clientFactoryProject = null;
		try{
			
			//项目
			clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			
			//字典service
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			
			//多物业信息
			clientEstateFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client clientEstate = (BizProjectEstateService.Client) clientEstateFactory.getClient();
			
			//临时数据信息
			//项目信息
			Project tempProject =clientProject.getLoanProjectByProjectId(projectPartnerDto.getProjectId());
			
			
			String cityCode = null;
			//查询字典配置-------------------
			List<SysLookupVal> cityList = clientSys.getSysLookupValByLookType(Constants.SYS_LOOKUP_PARTNER_XIAOGYIN_CITY);
			if(cityList != null && cityList.size() > 0){
				String [] cityConfigArray ;
				for (SysLookupVal indexObj : cityList) {
					//格式为：小赢code_Q房code
					if(!StringUtils.isEmpty(indexObj.getLookupVal())){
						cityConfigArray = indexObj.getLookupVal().split("_");
						if(cityConfigArray.length == 2 && projectPartnerDto.getCityCode().equals(cityConfigArray[1])){
							cityCode = cityConfigArray[0];
						}
					}
				}
			}
			if(StringUtils.isEmpty(cityCode)){
				resultJson = new JSONObject();
				resultJson.put("err_code", 1);
				resultJson.put("status", 0);
				resultJson.put("err_msg","该城市暂未开放");
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 1);
				resultJson.put("data", dataJson);
				return resultJson;
			}
			
			//请求参数
			JSONObject param = new JSONObject();
			//1:交易类赎楼, 2:非交易类赎楼, 4:尾款贷
			if(1 == projectPartnerDto.getBusinessType() || 2 == projectPartnerDto.getBusinessType()){
				param.put("type", projectPartnerDto.getBusinessType());
			}else{
				if("交易".equals(projectPartnerDto.getBusinessCategoryStr())  || 
						(!StringUtil.isBlank(tempProject.getBusinessTypeText()) 
								&& tempProject.getBusinessTypeText().startsWith("交易")) ){
					param.put("type", 1);	
				}else{
					param.put("type", 2);
				}
			}
			param.put("city",cityCode);//城市名称
			param.put("username", projectPartnerDto.getUserName());//客户名称
			param.put("idcard_no", projectPartnerDto.getCardNo());//身份证号
			param.put("mobile", projectPartnerDto.getPhone());//电话号码
			param.put("apply_money", projectPartnerDto.getApplyMoney());//借款金额
			param.put("apply_deadline", projectPartnerDto.getApplyDate());//借款期限
			param.put("add_user",XIAOYING_PARAM_ADD_USER);//暂时使用测试ID，小赢接口使用提交用户白名单
			param.put("loan_date", projectPartnerDto.getApplyLoanDate());//申请放款日
			if(!StringUtil.isBlank(projectPartnerDto.getLoanId())){
				param.put("loan_id", projectPartnerDto.getLoanId());
			}
			param.put("remark", projectPartnerDto.getRemark() == null ? "" :projectPartnerDto.getRemark() );//备注
			
			//物业信息---------------------------------------------------------------
			List<BizProjectEstate> projectEstateList =  clientEstate.getAllByProjectId(projectPartnerDto.getProjectId());
			String house_card_no ="";
			for (BizProjectEstate indexObj : projectEstateList) {
				house_card_no += indexObj.getHousePropertyCard()+",";
			}
			if(!StringUtil.isBlank(house_card_no)){
				house_card_no = house_card_no.substring(0,house_card_no.length()-1);
			}
			
			param.put("house_card_no", house_card_no);	//房产证号
			
			
			String requestFiles = projectPartnerDto.getRequestFiles();//提交的文件
			JSONObject filesJson= new JSONObject();
			if(!StringUtil.isBlank(requestFiles)){
				filesJson = getRequestFileJson(requestFiles,projectPartnerDto.getProjectId(),projectPartnerDto.getPid(),getServerBaseUrl(request));
			}
			param.put("files", filesJson);
			
			////////////////////////////////////////
			logger.info(">>>>>>apply-提交审核，补充材料请求明文参数："+param.toString());
			
			
			
			if("true".equals(IS_PARTNER_API_DEBUG)){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("err_code", 0);
				resultJson.put("err_msg", "ok");
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				dataJson.put("err_msg", "ok");
				dataJson.put("loan_id", StringUtil.isBlank(projectPartnerDto.getLoanId()) 
						? new Date().getTime() : projectPartnerDto.getLoanId());
				resultJson.put("data", dataJson);
				return resultJson;
			}
			
			
			
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_APPLY, paramMap, "UTF-8");
			
			if(!StringUtil.isBlank(result)){
				String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
				logger.info(">>>>>>applyPartner-提交审核，补充材料请求返回结果resContent："+resContent);
				resultJson = JSON.parseObject(resContent);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>apply error",e);
			return null;
		}finally {
			destroyFactory(clientFactorySys);
			destroyFactory(clientEstateFactory);
			destroyFactory(clientFactoryProject);
		}
		return resultJson;
	}
	
	@Override
	public JSONObject reApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientFactorEstatey = null;
		try{
			//字典service
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			
			//多物业信息
			clientFactorEstatey = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client clientEstate = (BizProjectEstateService.Client) clientFactorEstatey.getClient();
			
			String cityCode = null;
			//查询字典配置-------------------
			List<SysLookupVal> cityList = clientSys.getSysLookupValByLookType(Constants.SYS_LOOKUP_PARTNER_XIAOGYIN_CITY);
			if(cityList != null && cityList.size() > 0){
				String [] cityConfigArray ;
				for (SysLookupVal indexObj : cityList) {
					//格式为：小赢code_Q房code
					if(!StringUtils.isEmpty(indexObj.getLookupVal())){
						cityConfigArray = indexObj.getLookupVal().split("_");
						if(cityConfigArray.length == 2 && projectPartnerDto.getCityCode().equals(cityConfigArray[1])){
							cityCode = cityConfigArray[0];
						}
					}
				}
			}
			if(StringUtils.isEmpty(cityCode)){
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("err_msg","该城市暂未开放");
				resultJson.put("err_code", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 1);
				resultJson.put("data", dataJson);
				return resultJson;
			}
			
			//请求参数
			JSONObject param = new JSONObject();
			param.put("type", projectPartnerDto.getBusinessType());//1:交易类赎楼, 2:非交易类赎楼, 4:尾款贷
			param.put("city",cityCode);//城市名称
			param.put("username", projectPartnerDto.getUserName());//客户名称
			param.put("idcard_no", projectPartnerDto.getCardNo());//身份证号
			param.put("mobile", projectPartnerDto.getPhone());//电话号码
			param.put("apply_money", projectPartnerDto.getApplyMoney());//借款金额
			param.put("apply_deadline", projectPartnerDto.getApplyDate());//借款期限
			param.put("add_user",XIAOYING_PARAM_ADD_USER);//暂时使用测试ID，小赢接口使用提交用户白名单
			param.put("loan_date", projectPartnerDto.getApplyLoanDate());//申请放款日
			if(!StringUtil.isBlank(projectPartnerDto.getLoanId())){
				param.put("loan_id", projectPartnerDto.getLoanId());
			}
			param.put("remark", projectPartnerDto.getRemark() == null ? "" :projectPartnerDto.getRemark() );//备注
 
			//复议内容不能为空
			param.put("content", projectPartnerDto.getReApplyReason());
			
			//物业信息---------------------------------------------------------------
			List<BizProjectEstate> projectEstateList =  clientEstate.getAllByProjectId(projectPartnerDto.getProjectId());
			String house_card_no ="";
			for (BizProjectEstate indexObj : projectEstateList) {
				house_card_no += indexObj.getHousePropertyCard()+",";
			}
			if(!StringUtil.isBlank(house_card_no)){
				house_card_no = house_card_no.substring(0,house_card_no.length()-1);
			}
			param.put("house_card_no", house_card_no);	//房产证号
			
			String requestFiles = projectPartnerDto.getRequestFiles();//提交的文件
			JSONObject filesJson= new JSONObject();
			if(!StringUtil.isBlank(requestFiles)){
				filesJson = getRequestFileJson(requestFiles,projectPartnerDto.getProjectId(),projectPartnerDto.getPid(),getServerBaseUrl(request));
			}
			param.put("files", filesJson);
			
			////////////////////////////////////////
			logger.info(">>>>>>reApply复议请求明文参数："+param.toString());
			
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_RE_APPLY, paramMap, "UTF-8");
			
			if(!StringUtil.isBlank(result)){
				String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
				logger.info(">>>>>>areApply复议请求返回结果resContent："+resContent);
				resultJson = JSON.parseObject(resContent);
			}
		}catch(Exception e){
			logger.error(">>>>>>apply error",e);
			return null;
		}finally {
			destroyFactory(clientFactorySys);
			destroyFactory(clientFactorEstatey);
		}
		return resultJson;
	}
	
	@Override
	public JSONObject close(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		 
		JSONObject resultJson = null;
		try{
			JSONObject param = new JSONObject();
			param.put("loan_id", projectPartnerDto.getLoanId());
			param.put("add_user", XIAOYING_PARAM_ADD_USER);
			
			logger.info(">>>>>>close 加密前参数："+param.toString());
			
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_CLOSE_APPLY, paramMap, "UTF-8");
			
			if(!StringUtil.isBlank(result)){
				String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
				logger.info("close-关闭单据请求返回结果resContent："+resContent);
				resultJson = JSON.parseObject(resContent);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>close error",e);
			return null;
		}finally {
		}
		return resultJson;
	}
	
	
	
	/**
	 * 确认要款
	 * @param projectPartnerDto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject confirmLoan(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		 
		JSONObject resultJson = null;
		try{
			JSONObject param = new JSONObject();
			param.put("loan_id", projectPartnerDto.getLoanId());
			param.put("add_user", XIAOYING_PARAM_ADD_USER);
			param.put("remark", projectPartnerDto.getConfirmLoanReason());
			
			logger.info(">>>>>>confirmLoan确认要款-加密前参数："+param.toString());
			
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_REQUEST_LOAN, paramMap, "UTF-8");
			
			if(!StringUtil.isBlank(result)){
				String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
				logger.info(">>>>>>confirmLoan确认要款请求返回结果resContent："+resContent);
				resultJson = JSON.parseObject(resContent);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>confirmLoan error",e);
			return null;
		}finally {
		}
		return resultJson;
	}
	
	
	
	/**
	 * 复议确认要款
	 * @param projectPartnerDto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject reConfirmLoan(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		 
		JSONObject resultJson = null;
		try{
			
			JSONObject param = new JSONObject();
			param.put("loan_id", projectPartnerDto.getLoanId());
			param.put("add_user", XIAOYING_PARAM_ADD_USER);
			param.put("remark", projectPartnerDto.getConfirmLoanReason());
			
			logger.info(">>>>>>reConfirmLoan确认要款-加密前参数："+param.toString());
			
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_REQUEST_LOAN_REC, paramMap, "UTF-8");
			
			if(!StringUtil.isBlank(result)){
				String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
				logger.info(">>>>>>reConfirmLoan确认要款请求返回结果resContent："+resContent);
				resultJson = JSON.parseObject(resContent);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>reConfirmLoan error",e);
			return null;
		}finally {
		}
		return resultJson;
	}
	
	
	@Override
	public JSONObject loanApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		try{
			//请求参数拼接与加密
			JSONObject param = new JSONObject();
			param.put("loan_id", projectPartnerDto.getLoanId());
			param.put("add_user", XIAOYING_PARAM_ADD_USER);
			param.put("loan_money",projectPartnerDto.getConfirmLoanMoney());
			param.put("loan_deadline", projectPartnerDto.getConfirmLoanDays());
			
			JSONObject filesJson = new  JSONObject();
			//组装文件
			if(!StringUtil.isBlank(projectPartnerDto.getLoanJusticeFiles())){
				JSONObject loanJusticeFiles_array = getFileJsonArray(projectPartnerDto.getLoanJusticeFiles(), projectPartnerDto.getProjectId(),projectPartnerDto.getPid(), getServerBaseUrl(request));
				filesJson.put("loan_justice_info", loanJusticeFiles_array);
			}
			if(!StringUtil.isBlank(projectPartnerDto.getLoanBlankFiles())){
				JSONObject loanBlankFiles_array = getFileJsonArray(projectPartnerDto.getLoanBlankFiles(), projectPartnerDto.getProjectId(),projectPartnerDto.getPid(), getServerBaseUrl(request));
				filesJson.put("loan_blank_info", loanBlankFiles_array);
			}
			if(!StringUtil.isBlank(projectPartnerDto.getLoanOtherFiles())){
				JSONObject loanOtherFiles_array = getFileJsonArray(projectPartnerDto.getLoanOtherFiles(), projectPartnerDto.getProjectId(),projectPartnerDto.getPid(), getServerBaseUrl(request));
				filesJson.put("loan_other_info", loanOtherFiles_array);
			}
			param.put("files", filesJson);
			logger.info(">>>>>>loanApply-放款申请-加密前参数："+param.toString());
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
			//发送请求
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_SEND_LOAN, paramMap, "UTF-8");
			
			if(!StringUtil.isBlank(result)){
				String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
				logger.info("sendLoan-放款申请返回结果resContent:"+resContent);
				resultJson = JSON.parseObject(resContent);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>loanApply error",e);
			return null;
		}finally {
		}
		return resultJson;
	}
	
	@Override
	public JSONObject refundApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		try{
			//请求参数拼接
			JSONObject param = new JSONObject();
			param.put("loan_id", projectPartnerDto.getLoanId());
			param.put("type", projectPartnerDto.getRepaymentRepurchaseType());
			param.put("add_user", XIAOYING_PARAM_ADD_USER);
			param.put("repay_real_amount", projectPartnerDto.getRefundLoanAmount());
			param.put("repay_real_name", projectPartnerDto.getPayAcctName());
			param.put("bank_code", projectPartnerDto.getPayBankCode());
			param.put("swiping_time", projectPartnerDto.getRefundDate());
			
			JSONObject filesJson = new JSONObject();
			filesJson.put("repay_info", getFileJsonArray(projectPartnerDto.getRepaymentVoucherPath(), projectPartnerDto.getProjectId(),projectPartnerDto.getPid(), getServerBaseUrl(request)));
			param.put("files", filesJson);
			
			logger.info(">>>>>>refundApply加密前参数："+param.toString());
			
			//加密请求参数
			List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(param.toString(), XIAOYING_PARTNER_MD5);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(XIAOYING_PARTNER_API_URL+Constants.URL_XIAOYING_SEND_LOAN_VONCHER, paramMap, "UTF-8");
			
			if(!StringUtil.isBlank(result)){
				String resContent = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
				logger.info("refundApply-还款、回购申请返回结果resContent："+resContent);
				resultJson = JSON.parseObject(resContent);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>refundApply error",e);
			return null;
		}finally {
		}
		return resultJson;
	}
	
	@Override
	public JSONObject notify(JSONObject paramJson, HttpServletRequest request)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * 获取申请文件
	  * @param requestFileIds
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月29日 下午2:17:25
	 */
	private JSONObject getRequestFileJson(String requestFileIds,int projectId,int partnerId ,String filePath){
		JSONObject filesJson = new JSONObject();
		List<ProjectPartnerFile> fileList = new ArrayList<ProjectPartnerFile>();
		BaseClientFactory clientFactoryPartnerFile = null;
		try {
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setProjectId(projectId);
			query.setPartnerId(partnerId);
			query.setPidList(CommonUtil.getSepStrtoList(requestFileIds, "Integer", ","));
			fileList = clientPartnerFile.findAllProjectPartnerFile(query);
			
			
			JSONObject jsonTemp = null;
			JSONObject jsonFileTemp = null;
			ProjectPartnerFile file = null;
			String fileKey = "";
			String path = "";
			for (int i= 0 ; i< fileList.size() ; i++ ) {
				file = fileList.get(i);
				path = file.getFileUrl();
				path = path.replaceAll("\\\\", "/");  

				jsonFileTemp = new  JSONObject();
				jsonFileTemp.put("name", file.getFileName());
				jsonFileTemp.put("url", filePath+Constants.SEPARATOR + path);
				
				if(Constants.XIAOYING_ACCESSORY_TYPE_1.equals(file.getAccessoryType()) ){
					//卖房人身份证-正
					if(Constants.PARTNER_ACCESSORY_TYPE_F00_0.equals(file.getAccessoryChildType())){
						//客户基本信息
						if(filesJson.get("id_card_front_info") == null){
							jsonTemp = new JSONObject();
							fileKey = "1";
						}else{
							jsonTemp = filesJson.getJSONObject("id_card_front_info");
							fileKey = ""+(jsonTemp.size()+1);
						}
						jsonTemp.put(fileKey, jsonFileTemp);
						filesJson.put("id_card_front_info", jsonTemp);
						
					//卖房人身份证-反
					}else if(Constants.PARTNER_ACCESSORY_TYPE_F00_1.equals(file.getAccessoryChildType())){
						//客户基本信息
						if(filesJson.get("id_card_back_info") == null){
							jsonTemp = new JSONObject();
							fileKey = "1";
						}else{
							jsonTemp = filesJson.getJSONObject("id_card_back_info");
							fileKey = ""+(jsonTemp.size()+1);
						}
						jsonTemp.put(fileKey, jsonFileTemp);
						filesJson.put("id_card_back_info", jsonTemp);
					}else {
						//客户基本信息
						if(filesJson.get("loaner_base_info") == null){
							jsonTemp = new JSONObject();
							//jsonTemp.put(""+1, filePath+Constants.SEPARATOR + path);
							fileKey = "1";
						}else{
							jsonTemp = filesJson.getJSONObject("loaner_base_info");
							//jsonTemp.put(""+(jsonTemp.size()+1), filePath+Constants.SEPARATOR + path);
							fileKey = ""+(jsonTemp.size()+1);
						}
						jsonTemp.put(fileKey, jsonFileTemp);
						filesJson.put("loaner_base_info", jsonTemp);
					}
					
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_2.equals(file.getAccessoryType()) ){
					//交易方基本信息
					if(filesJson.get("counterparty_base_info") == null){
						jsonTemp = new JSONObject();
						fileKey = "1";
					}else{
						jsonTemp = filesJson.getJSONObject("counterparty_base_info");
						fileKey = ""+(jsonTemp.size()+1);
					}
					
					jsonTemp.put(fileKey, jsonFileTemp);
					filesJson.put("counterparty_base_info", jsonTemp);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_3.equals(file.getAccessoryType()) ){
					//房产信息
					if(filesJson.get("house_info") == null){
						jsonTemp = new JSONObject();
						fileKey = "1";
					}else{
						jsonTemp = filesJson.getJSONObject("house_info");
						fileKey = ""+(jsonTemp.size()+1);
					}
					jsonTemp.put(fileKey, jsonFileTemp);
					filesJson.put("house_info", jsonTemp);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_4.equals(file.getAccessoryType()) ){
					//交易信息
					if(filesJson.get("transation_info") == null){
						jsonTemp = new JSONObject();
						fileKey = "1";
					}else{
						jsonTemp = filesJson.getJSONObject("transation_info");
						fileKey = ""+(jsonTemp.size()+1);
					}
					jsonTemp.put(fileKey, jsonFileTemp);
					filesJson.put("transation_info", jsonTemp);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_5.equals(file.getAccessoryType()) ){
					//还款来源信息
					if(filesJson.get("payment") == null){
						jsonTemp = new JSONObject();
						fileKey = "1";
					}else{
						jsonTemp = filesJson.getJSONObject("payment");
						fileKey = ""+(jsonTemp.size()+1);
					}
					jsonTemp.put(fileKey, jsonFileTemp);
					filesJson.put("payment", jsonTemp);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_6.equals(file.getAccessoryType()) ){
					//征信信息
					if(filesJson.get("credit_info") == null){
						jsonTemp = new JSONObject();
						fileKey = "1";
					}else{
						jsonTemp = filesJson.getJSONObject("credit_info");
						fileKey = ""+(jsonTemp.size()+1);
					}
					jsonTemp.put(fileKey, jsonFileTemp);
					filesJson.put("credit_info", jsonTemp);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_7.equals(file.getAccessoryType()) ){
					//合同协议信息
					if(filesJson.get("pact_info") == null){
						jsonTemp = new JSONObject();
						fileKey = "1";
					}else{
						jsonTemp = filesJson.getJSONObject("pact_info");
						fileKey = ""+(jsonTemp.size()+1);
					}
					jsonTemp.put(fileKey, jsonFileTemp);
					filesJson.put("pact_info", jsonTemp);
				}else if( Constants.XIAOYING_ACCESSORY_TYPE_8.equals(file.getAccessoryType()) ){
					//其他材料
					if(filesJson.get("other_info") == null){
						jsonTemp = new JSONObject();
						fileKey = "1";
					}else{
						jsonTemp = filesJson.getJSONObject("other_info");
						fileKey = ""+(jsonTemp.size()+1);
					}
					jsonTemp.put(fileKey, jsonFileTemp);
					filesJson.put("other_info", jsonTemp);
				}
			}
		} catch (Exception e) {
			logger.error("getRequestFileJson 转换封装附件 error",e);
		}finally {
			if (clientFactoryPartnerFile != null) {
				try {
					clientFactoryPartnerFile.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getRequestFileJson clientFactoryPartnerFile destroy thrift error：",e);
				}
			}
		}
 		return filesJson;
	}
	
	
	/**
	 *  获取文件返回数组
	  * @param FileIds
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月29日 下午2:17:25
	 */
	private JSONObject getFileJsonArray(String fileIds,int projectId,int partnerId,String filePath){
		
		JSONObject fileArrayJson = new JSONObject();
		
		List<ProjectPartnerFile> fileList = new ArrayList<ProjectPartnerFile>();
		BaseClientFactory clientFactoryPartnerFile = null;
		try {
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setProjectId(projectId);
			query.setPidList(CommonUtil.getSepStrtoList(fileIds, "Integer", ","));
			query.setPartnerId(partnerId);
			fileList = clientPartnerFile.findAllProjectPartnerFile(query);
			
			ProjectPartnerFile file = null;
			String path = "";
			JSONObject jsonObjTemp = null;
			for (int i= 0 ; i< fileList.size() ; i++ ) {
				
				jsonObjTemp = new JSONObject();
				file = fileList.get(i);
				path = file.getFileUrl();
				path = path.replaceAll("\\\\", "/");
				
				jsonObjTemp.put("name", file.getFileName());
				jsonObjTemp.put("url", filePath+Constants.SEPARATOR + path);
				
				fileArrayJson.put(i+1+"", jsonObjTemp);
			}
		} catch (Exception e) {
			logger.error("getFileJsonArray 转换封装附件 error",e);
		}finally {
			if (clientFactoryPartnerFile != null) {
				try {
					clientFactoryPartnerFile.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getFileJsonArray clientFactoryPartnerFile destroy thrift error：",e);
				}
			}
		}
 		return fileArrayJson;
	}
	
	
}
