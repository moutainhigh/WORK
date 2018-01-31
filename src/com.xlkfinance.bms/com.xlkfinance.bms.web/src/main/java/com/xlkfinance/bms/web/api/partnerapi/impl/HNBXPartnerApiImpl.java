package com.xlkfinance.bms.web.api.partnerapi.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.thrift.TApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.PartnerConstant;
import com.xlkfinance.bms.common.constant.SysConfigConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.DesUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerFamilyDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.rpc.system.SysConfigService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.web.api.partnerapi.daju.util.DaJuUtil;
import com.xlkfinance.bms.web.api.partnerapi.hnbx.constant.HNBXConstant;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.FTPUtil;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.PartnerProjectUtil;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.SFTPUtil;

/**
 * 华安保险接口实现类
 * @author chenzhuzhen
 * @date 2017年7月4日 下午5:10:59
 */
@Service("hNBXPartnerApiImpl")
public class HNBXPartnerApiImpl extends BaseController{
	
	private Logger logger = LoggerFactory.getLogger(HNBXPartnerApiImpl.class);
	

	
	//------------------------------------万通配置---------------------------------------------
	/**api地址*/
	private static String HNBX_API_URL = PropertiesUtil.getValue("hnbx.partner.api.url");
	/**接口类型*/
	private static String HNBX_PARTNER_TYPE = PropertiesUtil.getValue("hnbx.partner.type");
	/**合作方的唯一标识*/
	private static String HNBX_PARTNER_ACCID = PropertiesUtil.getValue("hnbx.partner.accid");
	/**参数加密码md5*/
	private static String HNBX_PARTNER_MD5 = PropertiesUtil.getValue("hnbx.partner.md5");
	
	//华安保险SFTP服务配置信息
	/**FTP文件服务器ip*/
	public static String HNBX_SFTP_SERVER_IP = PropertiesUtil.getValue("hnbx.sftp.server.ip");
	/**FTP文件服务器端口*/
	public static String HNBX_SFTP_SERVER_PORT = PropertiesUtil.getValue("hnbx.sftp.server.port");
	/**FTP文件服务器用户名*/
	public static String HNBX_SFTP_SERVER_USERNAME = PropertiesUtil.getValue("hnbx.sftp.server.username");
	/**FTP文件服务器密码*/
	public static String HNBX_SFTP_SERVER_PASSWORD = PropertiesUtil.getValue("hnbx.sftp.server.password");
	/**FTP文件服务器文件根目录*/
	public static String HNBX_SFTP_DIR_BASE_PATH = PropertiesUtil.getValue("hnbx.sftp.dir.base.path");
	
	
	//------------------------------------小科配置---------------------------------------------
	/**api地址2*/
	private static String HNBX2_API_URL = PropertiesUtil.getValue("hnbx2.partner.api.url");
	/**接口类型2*/
	private static String HNBX2_PARTNER_TYPE = PropertiesUtil.getValue("hnbx2.partner.type");
	/**合作方的唯一标识2*/
	private static String HNBX2_PARTNER_ACCID = PropertiesUtil.getValue("hnbx2.partner.accid");
	/**参数加密码md52*/
	private static String HNBX2_PARTNER_MD5 = PropertiesUtil.getValue("hnbx2.partner.md5");
	
	//华安保险SFTP服务配置信息
	/**FTP文件服务器ip2*/
	public static String HNBX2_SFTP_SERVER_IP = PropertiesUtil.getValue("hnbx2.sftp.server.ip");
	/**FTP文件服务器端口2*/
	public static String HNBX2_SFTP_SERVER_PORT = PropertiesUtil.getValue("hnbx2.sftp.server.port");
	/**FTP文件服务器用户名2*/
	public static String HNBX2_SFTP_SERVER_USERNAME = PropertiesUtil.getValue("hnbx2.sftp.server.username");
	/**FTP文件服务器密码2*/
	public static String HNBX2_SFTP_SERVER_PASSWORD = PropertiesUtil.getValue("hnbx2.sftp.server.password");
	/**FTP文件服务器文件根目录2*/
	public static String HNBX2_SFTP_DIR_BASE_PATH = PropertiesUtil.getValue("hnbx2.sftp.dir.base.path");
	
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	

	
	/**
	 * 申请进件
	 */
	public JSONObject apply(String uuid , ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		
		BaseClientFactory clientFactoryPartner = null;
		BaseClientFactory clientFactoryCus = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientFactoryCusAcct = null;
		BaseClientFactory clientFactoryPartnerFile = null;
		BaseClientFactory clientEstateFactory = null;
		BaseClientFactory clientFactoryProject = null;
		BaseClientFactory clientFactorySysConfig = null;
		
		try{
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//字典service
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
 
			//客户service
			clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientCus =(CusPerService.Client) clientFactoryCus.getClient();
			
			//资金合作项目文件
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
 
			//多物业信息
			clientEstateFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client clientEstate = (BizProjectEstateService.Client) clientEstateFactory.getClient();
			
			//项目
			clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			
			//系统配置
			clientFactorySysConfig = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
			SysConfigService.Client clientSysConfig =(SysConfigService.Client) clientFactorySysConfig.getClient();
			
			
			//临时数据信息
			//项目信息
			Project tempProject =clientProject.getLoanProjectByProjectId(projectPartnerDto.getProjectId());
			//赎楼信息----------------
			ProjectForeclosure tempForeclosure =clientProject.getForeclosureByProjectId(projectPartnerDto.getProjectId());
			//物业信息
			List<BizProjectEstate> tempEstateList =  clientEstate.getAllByProjectId(projectPartnerDto.getProjectId());
			//收费
			ProjectGuarantee tempGuarantee = clientProject.getGuaranteeByProjectId(projectPartnerDto.getProjectId());
			
			//资金项目信息
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setPid(projectPartnerDto.getPid());
			queryPartner.setPartnerNo(projectPartnerDto.getPartnerNo());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);

			//查询客户基本信息
			Integer perId = clientCus.selectPerIdByAcctId(tempProject.getAcctId());
			CusPerBaseDTO tempCusPerBaseDto = clientCus.getCusPerBase(perId);
			CusPerson tempCusPerson = tempCusPerBaseDto.getCusPerson();
			
			ProjectPartnerDto tempDto = new ProjectPartnerDto();
			//设置多物业信息
			tempDto.setEstateList(tempEstateList);
			//设置赎楼信息
			tempDto.setProjectForeclosure(tempForeclosure);
			//设置物业信息
			tempDto.setProjectProperty(tempProject.getProjectProperty());
			
			//转换对象值
			tempDto = PartnerProjectUtil.convertBean(tempDto, tempProject, tempGuarantee, tempPartner, tempCusPerson);
			
			//如果地区为空，返回去填写
			if(StringUtil.isBlank(tempCusPerson.getLiveDistrictCode())){
				return returnJson(0, "客户居住地址省市区信息不全，请返回客户资料补充完整", 1, "",null);
			}
			
			/**是否是交易       true： 交易    fasle 非交易*/
			boolean   isTrading  = false;
			if("交易".equals(projectPartnerDto.getBusinessCategoryStr()) || 
					(!StringUtil.isBlank(tempProject.getBusinessTypeText()) 
							&& tempProject.getBusinessTypeText().startsWith("交易")) ){
				isTrading = true;
			}
			
			//===================================封装请求参数=============================
			//业务参数
			JSONObject bodyJson = new JSONObject();
			
			//客户信息
			JSONObject cusInfoJson = new JSONObject();
			cusInfoJson.put("cusName", tempDto.getUserName());					//客户姓名
			cusInfoJson.put("cusType", HNBXConstant.CusType.C_110.getCode());	//客户类型
			
			//证件类型
			String cert_type="";
			if(!StringUtil.isBlank(tempDto.getCertType())){
				String	certType = clientSys.getSysLookupValByName(Integer.parseInt(tempDto.getCertType()));
				if("身份证".equals(certType)){
					cert_type=HNBXConstant.CertType.C_10.getCode();
				}else{
					return returnJson(0, "客户证件类型不支持，只支持身份证", 1, "",null);
				}
			}
			
			cusInfoJson.put("certType", cert_type);						//客户证件类型
			cusInfoJson.put("certCode", tempDto.getCardNo());			//客户证件号码
			cusInfoJson.put("phone", tempDto.getPhone());				//手机号
			
			//如果地区为空，返回去填写
			if(StringUtil.isBlank(tempCusPerson.getLiveDistrictCode())){
				return returnJson(0, "客户居住地址省市区信息不全，请返回客户资料补充完整", 1, "",null);
			}
			
			cusInfoJson.put("indivRsdPle", tempCusPerson.getLiveDistrictCode());		//居住地址区划名称 参照全国省市区代码
			cusInfoJson.put("indivRsdAddr", tempCusPerson.getLiveAddr());				//居住地址
			cusInfoJson.put("indivRsdSt", HNBXConstant.IndivRsdStatus.C_7.getCode());	//居住状况
			cusInfoJson.put("indivComName", "");										//单位名称
			
			//婚姻状况
			String	marriageLookUpName = clientSys.getSysLookupValByName(tempCusPerson.getMarrStatus());
			String indivMarSt = HNBXConstant.MarryStatus.C_90.getCode();
			if("已婚".equals(marriageLookUpName)){
				indivMarSt = HNBXConstant.MarryStatus.C_20.getCode();
			}else if("未婚".equals(marriageLookUpName)){
				indivMarSt = HNBXConstant.MarryStatus.C_10.getCode();
			}else if("离异".equals(marriageLookUpName)){
				indivMarSt = HNBXConstant.MarryStatus.C_22.getCode();
			}else if("丧偶".equals(marriageLookUpName)){
				indivMarSt = HNBXConstant.MarryStatus.C_30.getCode();
			}
			cusInfoJson.put("indivMarSt", indivMarSt);			//婚姻状况
			
			//已婚需要填写配偶信息
			if(HNBXConstant.MarryStatus.C_21.getCode().equals(indivMarSt) || HNBXConstant.MarryStatus.C_20.getCode().equals(indivMarSt)){
			   //家庭情况
			   CusPerFamilyDTO cusPerFamilyDTO = clientCus.getCusPerFamily(perId, tempProject.getAcctId());
			   
			   //己婚有孩子
			   if(cusPerFamilyDTO != null && cusPerFamilyDTO.getCusPerFamily() != null && cusPerFamilyDTO.getCusPerFamily().getChildNum() > 0){
				   indivMarSt = HNBXConstant.MarryStatus.C_21.getCode();
			   }
			   //更新婚姻状况
			   cusInfoJson.put("indivMarSt", indivMarSt);			//婚姻状况
			   
			   //配偶信息
			   CusPerson spousePerson = cusPerFamilyDTO.getCusPerson();
			   if(spousePerson == null || spousePerson.getPid() == 0 ){
				   return returnJson(0, "己婚人士配偶信息不能为空，请返回客户>个人家庭信息补充完整", 1, "",null);
			   }
			   if(spousePerson.getCertType() == 0){
				   return returnJson(0, "客户配偶证件类型不能为空，请返回客户>个人家庭信息补充完整", 1, "",null);
			   }
			   if(StringUtil.isBlank(spousePerson.getCertNumber())){
				   return returnJson(0, "客户配偶证件号码不能为空，请返回客户>个人家庭信息补充完整", 1, "",null);
			   }
			   if(StringUtil.isBlank(spousePerson.getTelephone())){
				   return returnJson(0, "客户配偶手机号码不能为空，请返回客户>个人家庭信息补充完整", 1, "",null);
			   }
			
				//证件类型
			    String indivSpsldTyp="";
				indivSpsldTyp = clientSys.getSysLookupValByName(spousePerson.getCertType());
				if("身份证".equals(indivSpsldTyp)){
					indivSpsldTyp=HNBXConstant.CertType.C_10.getCode();
				}else{
					return returnJson(0, "客户配偶证件类型不支持，只支持身份证", 1, "",null);
				}
				cusInfoJson.put("indivSpsName", spousePerson.getChinaName());			//配偶姓名
				cusInfoJson.put("indivSpsIdTyp", indivSpsldTyp);						//配偶证件类型
				cusInfoJson.put("indivSpsIdCode", spousePerson.getCertNumber());		//配偶证件号码
				cusInfoJson.put("indivSpsMphn", spousePerson.getTelephone());			//配偶手机号码
			}
			
			
			cusInfoJson.put("email", tempCusPerson.getMail());					//邮箱
			cusInfoJson.put("weChat", tempCusPerson.getWechat());				//微信
			cusInfoJson.put("qq", tempCusPerson.getQq());						//QQ
			
			
			//贷款信息
			JSONObject loanInfoJson = new JSONObject();
			loanInfoJson.put("loanType", HNBXConstant.LoanType.C_1.getCode());	//贷款品种
			
			//默认非交易类现金赎楼
			String ransomType = HNBXConstant.RansomType.C_2.getCode();
			if(isTrading){
				//交易类
				ransomType = HNBXConstant.RansomType.C_1.getCode();
			}
			
			loanInfoJson.put("ransomType", ransomType);							//赎楼类型
			loanInfoJson.put("applyAmt", tempDto.getApplyMoney());				//申请金额（元）
			loanInfoJson.put("oldHouseAmt", tempForeclosure.getOldOwedAmount());//原房屋贷款余额（元）
			
			
			
			int partnerPushAccount = tempDto.getPartnerPushAccount();
			
			//费率
			String TEMP_HNBX_PARTNER_RATE = SysConfigConstant.HNBX2_PARTNER_RATE;
			//执行利率
			String TEPM_HNBX_PARTNER_GROSS_RATE = SysConfigConstant.HNBX2_PARTNER_GROSS_RATE;
			//加密md5
			String TEPM_HNBX_PARTNER_MD5 = HNBX2_PARTNER_MD5;
			//机构帐号accid
			String TEPM_HNBX_PARTNER_ACCID = HNBX2_PARTNER_ACCID;
			//接口地址
			String TEPM_HNBX_API_URL = HNBX2_API_URL;
			//接口类型
			String TEPM_HNBX_PARTNER_TYPE = HNBX2_PARTNER_TYPE;
			
			
			
			if(partnerPushAccount  ==  PartnerConstant.PartnerPushAccount.C_1.getCode()){
				TEMP_HNBX_PARTNER_RATE = SysConfigConstant.HNBX_PARTNER_RATE;
				TEPM_HNBX_PARTNER_GROSS_RATE = SysConfigConstant.HNBX_PARTNER_GROSS_RATE;
				TEPM_HNBX_PARTNER_MD5 = HNBX_PARTNER_MD5;
				TEPM_HNBX_PARTNER_ACCID = HNBX_PARTNER_ACCID;
				TEPM_HNBX_API_URL = HNBX_API_URL;
				TEPM_HNBX_PARTNER_TYPE = HNBX_PARTNER_TYPE;
			}
			
			
			
			
			//从系统配置查询
			String rate = "";
			String grossRate = "";
			try{
				rate = clientSysConfig.getSysConfigValueByName(TEMP_HNBX_PARTNER_RATE);
			}catch(TApplicationException e){
				return returnJson(0, "华安保险接口费率未配置", 1, "",null);
			}
			
			//如果没有保存，从配置获取
			if(tempDto.getPartnerGrossRate() > 0){
				grossRate = tempDto.getPartnerGrossRate()+"";
			}else{
				try{
					grossRate = clientSysConfig.getSysConfigValueByName(TEPM_HNBX_PARTNER_GROSS_RATE);
				}catch(TApplicationException e){
					return returnJson(0, "华安保险接口执行利率未配置", 1, "",null);
				}
			}
			
			
/*			if(StringUtil.isBlank(rate)){
				return returnJson(0, "华安保险接口费率未配置", 1, "",null);
			}*/
			if(StringUtil.isBlank(grossRate)){
				return returnJson(0, "华安保险接口执行利率未配置", 1, "",null);
			}
			
			loanInfoJson.put("rate", rate);										//费率
			loanInfoJson.put("grossRate", grossRate);									//执行利率
			
			loanInfoJson.put("termType", HNBXConstant.TermType.C_1.getCode());	//期限类型
			loanInfoJson.put("term", tempDto.getApplyDate());					//申请期限
			loanInfoJson.put("repaymentMode", HNBXConstant.RepaymentMode.C_7.getCode());	//还款方式
			
			loanInfoJson.put("repayAccountName", tempDto.getPayAcctName());		//还款账号户名
			loanInfoJson.put("repayBank", tempDto.getPayBankName());			//还款账号开户行
			loanInfoJson.put("repayAccount", tempDto.getPayAcctNo());			//还款卡号
			
			
			loanInfoJson.put("payeeAccountName", tempDto.getPaymentAcctName());		//收款帐号户名
			loanInfoJson.put("payeeBank", tempDto.getPaymentBank());				//收款帐号开户行
			loanInfoJson.put("payeeAccount", tempDto.getPaymentAcctNo());			//收款帐号
			
			loanInfoJson.put("assureMeans", HNBXConstant.AssureMeans.C_00.getCode());			//担保方式
			loanInfoJson.put("accAgreeNo", "");				//借款协议编号
			loanInfoJson.put("inputBrId", "01080004");				//业务归属机构
			loanInfoJson.put("partnerNo", "");				//渠道合作方
			loanInfoJson.put("useDate", tempDto.getApplyLoanDate());				//用款时间
			
			
			if(tempEstateList.size() > 1){
				return returnJson(0, "不支持多物业", 1, "",null);
			}
			
			
			BizProjectEstate tempEstate = tempEstateList.get(0);
			ProjectProperty tempProperty =tempProject.getProjectProperty();
			//房产信息
			JSONObject houseInfoJson = new JSONObject();
			houseInfoJson.put("houseAddr",  (StringUtil.isBlank(tempEstate.getHouseAddress()) ? "" :tempEstate.getHouseAddress()) + tempEstate.getHouseName());		//房产地址
			houseInfoJson.put("houseOwnerName", tempProperty.getSellerName().split(",")[0].trim());			//房产权属人
			houseInfoJson.put("houseOwnerCertCode", tempProperty.getSellerCardNo().split(",")[0].trim());	//权属人证件号码
			houseInfoJson.put("houseCertNo", tempEstate.getHousePropertyCard());							//房地产权证号
			houseInfoJson.put("housePurpose", "");															//房屋规划用途
			
			//交易类赎楼信息 ， 买家贷款信息，卖家信息，买家信息
			//买家贷款信息
			JSONObject buyerLoanInfoJson = new JSONObject();
			buyerLoanInfoJson.put("buyerFirstAmount", tempForeclosure.getFundsMoney());			//首期款监管额
			buyerLoanInfoJson.put("buyerAmount", tempForeclosure.getNewLoanMoney());			//买方贷款金额
			buyerLoanInfoJson.put("buyerBankName", tempForeclosure.getNewReceBank());			//买方贷款申请银行
			buyerLoanInfoJson.put("buyerBankCode", tempForeclosure.getPaymentAccount());		//买方贷款申请银行卡号
			
			//卖家信息
			JSONObject sellerInfoJson = new JSONObject();
			sellerInfoJson.put("cusName", tempProperty.getSellerName().split(",")[0].trim());			//姓名
			sellerInfoJson.put("cusType", HNBXConstant.CusType.C_110.getCode());						//客户类型
			sellerInfoJson.put("certType", HNBXConstant.CertType.C_10.getCode());						//证件类型
			sellerInfoJson.put("certCode", tempProperty.getSellerCardNo().split(",")[0].trim());		//证件号码
			sellerInfoJson.put("phone", tempProperty.getSellerPhone().split(",")[0].trim());			//联系电话
			
			//买家信息
			JSONObject buyerInfoJson = new JSONObject();
			buyerInfoJson.put("cusName", StringUtil.isBlank(tempProperty.getBuyerName()) ? "" : tempProperty.getBuyerName().split(",")[0].trim());		//姓名
			buyerInfoJson.put("cusType", HNBXConstant.CusType.C_110.getCode());			//客户类型
			buyerInfoJson.put("certType", HNBXConstant.CertType.C_10.getCode());		//证件类型
			buyerInfoJson.put("certCode", StringUtil.isBlank(tempProperty.getBuyerCardNo()) ? "" : tempProperty.getBuyerCardNo().split(",")[0].trim());		//证件号码
			buyerInfoJson.put("phone", StringUtil.isBlank(tempProperty.getBuyerPhone()) ? "" : tempProperty.getBuyerPhone().split(",")[0].trim());			//联系电话
		 
			//保证人信息 guarInfo  现金赎楼不需要
			
			//资料信息
			JSONArray fileInfosArrayJson = new JSONArray();
			
			//查询所有的文件
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setProjectId(tempDto.getProjectId());
			query.setPartnerNo(tempDto.getPartnerNo());
			query.setPartnerId(tempDto.getPid());
			query.setStatus(Constants.COMM_YES);
			List<ProjectPartnerFile> fileList = clientPartnerFile.findAllProjectPartnerFile(query);
			
			//资料路径
			String filePath="";
			if(!CollectionUtils.isEmpty(fileList)){
				for (ProjectPartnerFile indexObj : fileList) {
					if(!StringUtil.isBlank(indexObj.getThirdFileUrl())){
						JSONObject fileJson1= new JSONObject();
						//需与打包文件名一致，否则对方匹配不了类型
						fileJson1.put("fileName", indexObj.getFileName()+"_"+indexObj.pid+"."+indexObj.getFileType());	//文件名称
						fileJson1.put("fileType", indexObj.getAccessoryChildType());		//文件类型
						fileInfosArrayJson.add(fileJson1);
						
						if(StringUtil.isBlank(filePath)){
							filePath = indexObj.getThirdFileUrl();
						}
					}
				}
			}
			
			if(fileInfosArrayJson.size() == 0 ){
				return returnJson(0, "请上传文件资料，并上传到第三方系统", 1, "",null);
			}
					
			bodyJson.put("cusInfo", cusInfoJson);				//客户信息
			bodyJson.put("loanInfo", loanInfoJson);				//贷款信息
			bodyJson.put("houseInfo", houseInfoJson);			//房产信息
			
			//交易类才需要
			if(isTrading){
				bodyJson.put("buyerLoanInfo", buyerLoanInfoJson);	//买家贷款信息
				bodyJson.put("sellerInfo", sellerInfoJson);			//卖家信息
				bodyJson.put("buyerInfo", buyerInfoJson);			//买家信息
			}
			bodyJson.put("fileInfos", fileInfosArrayJson);			//资料信息
			bodyJson.put("filePath", filePath);						//资料路径
			
			logger.info("uuid:"+uuid+",apply bodyJson:"+bodyJson.toString());
			
			//加密参数
			DesUtil desUtil = new DesUtil(TEPM_HNBX_PARTNER_MD5); 
			String encryptBodyJson= desUtil.encrypt(bodyJson.toString());
			
			logger.info("uuid:"+uuid+",apply encryptBodyJson:"+encryptBodyJson);
			
			//--------------------------------调试模式begin-------------------------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", "操作成功");
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				dataJson.put("loan_id", tempDto.getPid());
				resultJson.put("data", dataJson);
				return resultJson;
			}
			//--------------------------------调试模式end-------------------------------------------------
			
			JSONObject paramJson = new JSONObject();
			
			paramJson.put("accid", TEPM_HNBX_PARTNER_ACCID);
			paramJson.put("type", TEPM_HNBX_PARTNER_TYPE);
			paramJson.put("params", encryptBodyJson);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.post(TEPM_HNBX_API_URL, paramJson, null);
			
			logger.info("uuid:"+uuid+",apply http resultHttpJson:"+resultHttpJson);
			
			//发送请求
			if(resultHttpJson != null){
				// 
				String repCod = resultHttpJson.getString("rspCod");	//返回码
				String rspMsg = resultHttpJson.getString("rspMsg");	//返回信息
				
				if(HNBXConstant.RspCod.SUCCESS.getCode().equals(repCod)){
					//转换格式
					resultJson = new JSONObject();
					resultJson.put("status", 0);
					resultJson.put("msg",rspMsg);
					
					JSONObject dataJson = new JSONObject();
					dataJson.put("loan_id", resultHttpJson.getString("serNo"));		//业务编号
					dataJson.put("err_code", 0);
					resultJson.put("data", dataJson);
				}else{
					return returnJson(0, rspMsg, 1, "",null);
				} 
			}
		}catch(Exception e){
			logger.error(">>>>>>apply error",e);
			return null;
		}finally{
			destroyFactory(clientFactoryPartner);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
			destroyFactory(clientFactoryCusAcct);
			destroyFactory(clientFactoryPartnerFile);
			destroyFactory(clientEstateFactory);
			destroyFactory(clientFactoryProject);
		}
		return resultJson;
	}

	
	/**
	 * 打发包上传文件
	 * @return
	 * @throws Exception
	 */
	public JSONObject uploadFile(String uuid,ProjectPartner projectPartner)throws Exception {
		
		BaseClientFactory clientFactoryPartnerFile = null;
		try{
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();

			List<Integer> pidFileList = CommonUtil.getSepStrtoList(projectPartner.getRequestFiles(), "Integer", ",");
			
			//查询文件
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setPartnerId(projectPartner.getPid());
			query.setPidList(pidFileList);
			List<ProjectPartnerFile> fileList = clientPartnerFile.findAllProjectPartnerFile(query);
			
			if(fileList.size() != pidFileList.size()){
				logger.info("uuid:"+uuid+",fileList.size："+fileList.size()+",pidFileList.size:"+pidFileList.size());
				return returnJson(0, "上传失败，文件数时不一致", 1, "",null);
			}
			
			//本地文件路径
			List<String> fileUrlList = new ArrayList<String>();
			//本地文件名称
			List<String> fileNameList = new ArrayList<String>();
			for(ProjectPartnerFile indexObj : fileList){
				fileUrlList.add(indexObj.getFileUrl());
				//需与发送文件名一致，否则对方匹配不了类型
				fileNameList.add(indexObj.getFileName()+"_"+indexObj.getPid());
			}
			
			//时间格式
			String nowDateStr = DateUtils.dateToFormatString(new Date(), "yyyyMMdd");
			//时间格式
			String nowTimeStr = DateUtils.dateToFormatString(new Date(), "yyyyMMddHHmmssSSS");
		
			//系统本地路径
			String localRootPath =	CommonUtil.getRootPath();
			//系统上传根目录
			String uploadFileRoot = getUploadFilePath();
			//zip相对目录
			String relativeZipPath = "partner"+File.separator+nowDateStr+File.separator+projectPartner.getPartnerNo();
			//zip文件名
			String zipFileName = nowTimeStr+"_"+projectPartner.getPid()+".zip";
			
			//组装全文件路径
			Map<String,Object> returnMap  = FileUtil.packageZipFile(fileUrlList, fileNameList, localRootPath, uploadFileRoot, relativeZipPath, zipFileName,false);
			String 	zipFilePath = (String) returnMap.get("zipFilePath");
			
			//上传ftp-------------------------------------------------------
			String filePath = localRootPath+zipFilePath;
			filePath = CommonUtil.dealPath(filePath);
			
			logger.info("uuid："+uuid+",上传打包本地文件数理："+fileList.size()+", 文件路径filePath:"+filePath);
			
			String ftpPort = HNBX_SFTP_SERVER_PORT;
			String hnbxSftpServerIp = HNBX_SFTP_SERVER_IP;
			String hnbxSftpServerUsername = HNBX_SFTP_SERVER_USERNAME;
			String hnbxSftpServerPassword = HNBX_SFTP_SERVER_PASSWORD;
			String hnbxSftpDirBasePath = HNBX_SFTP_DIR_BASE_PATH;
			
			//小科FTP帐号
			if(projectPartner.getPartnerPushAccount() == PartnerConstant.PartnerPushAccount.C_2.getCode()){
				ftpPort = HNBX2_SFTP_SERVER_PORT;
				hnbxSftpServerIp = HNBX2_SFTP_SERVER_IP;
				hnbxSftpServerUsername = HNBX2_SFTP_SERVER_USERNAME;
				hnbxSftpServerPassword = HNBX2_SFTP_SERVER_PASSWORD;
				hnbxSftpDirBasePath = HNBX2_SFTP_DIR_BASE_PATH;
			}
			
			
			//将文件上传侄FTP服务器
			String ftpFilePath = hnbxSftpDirBasePath+"/"+zipFileName;
			
			if(StringUtil.isBlank(ftpPort)){
				ftpPort = "21";	//为空默认为21
			}
			
			//文件上传到ftp后，后面打包的zip文件可从服务器删除，暂时临时保存，每隔时段清理文件
			File zipFile = new File(filePath);
			//上传到FTP
			FTPUtil ftpUtil = new FTPUtil();
			ftpUtil.init(hnbxSftpServerIp, Integer.parseInt(ftpPort), hnbxSftpServerUsername, hnbxSftpServerPassword);
			ftpUtil.upload(zipFile, hnbxSftpDirBasePath);
			ftpUtil.close();
			
			logger.info("uuid："+uuid+",上传FTP文件路径ftpFilePath:"+ftpFilePath+",推送帐号："+projectPartner.getPartnerPushAccount());
			
			//转换格式
			JSONObject resultJson = new JSONObject();
			resultJson.put("status", 0);
			resultJson.put("msg","操作成功,上传文件数量："+fileList.size());
			
			JSONObject dataJson = new JSONObject();
			dataJson.put("filePath", ftpFilePath);		//ftp路径
			dataJson.put("err_code", 0);
			resultJson.put("data", dataJson);
			 
			return resultJson;
			
		}catch(Exception e){
			logger.error("uuid:"+uuid+",updateFtpFile error",e);
			return null;
		}finally{
			destroyFactory(clientFactoryPartnerFile);
		}
		
	}
	
	
	/**
	 * 返回整合信息
	 * @param status	0成功  1：失败
	 * @param msg	提示消息
	 * @param err_code  0 成功，其它失败
	 * @param loan_id   第三方主键
	 * @return
	 */
	public JSONObject returnJson(Integer status , String msg , Integer err_code,String loan_id,JSONObject dataJson){
		JSONObject resultJson = new JSONObject();
		resultJson.put("status", status);
		resultJson.put("msg",msg);
		if(dataJson == null){
			dataJson = new JSONObject();
		}
		dataJson.put("loan_id", loan_id);
		dataJson.put("err_code", err_code);
		resultJson.put("data", dataJson);
		return resultJson;
	}
	 

}
