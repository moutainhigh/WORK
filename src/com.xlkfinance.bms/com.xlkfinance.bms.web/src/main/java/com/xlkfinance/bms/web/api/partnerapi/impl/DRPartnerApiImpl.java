package com.xlkfinance.bms.web.api.partnerapi.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerRefund;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.web.api.partnerapi.BasePartnerApi;
import com.xlkfinance.bms.web.api.partnerapi.dr.util.JWTGeneratorUtil;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.PartnerProjectUtil;
import com.xlkfinance.bms.web.util.PropertiesUtil;

/**
 * 点融接口实现类
 * @author chenzhuzhen
 * @date 2016年6月27日 下午3:28:25
 */
@Service("dRPartnerApiImpl")
public class DRPartnerApiImpl extends BaseController implements BasePartnerApi {
	
	private Logger logger = LoggerFactory.getLogger(DRPartnerApiImpl.class);
	
	//点融clientId
	private static String DR_PARTNER_CLIENTID = PropertiesUtil.getValue("dr.partner.clientId");
	//点融privateKey
	private static String DR_PARTNER_PRIVATEKEY = PropertiesUtil.getValue("dr.partner.privateKey");
	//点融api域名
	private static String DR_PARTNER_API_BASE_URL = PropertiesUtil.getValue("dr.partner.api.base.url");
	//点融api token 方法名
	private static String DR_PARTNER_API_TOKEN_URL = PropertiesUtil.getValue("dr.partner.api.token.url");
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	
	
	/**
	 *  申请
	 *  返回格式：｛status:xx,msg:xxx,data{loan_id:xxx,err_code:xxx}｝
	 */
	@Override
	public JSONObject apply(ProjectPartnerDto projectPartnerDto ,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		BaseClientFactory clientFactoryProject = null;
		BaseClientFactory clientFactoryPartner = null;
		BaseClientFactory clientFactoryCus = null;
		BaseClientFactory clientFactoryCusAcct = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientFactoryArea = null;
		BaseClientFactory clientEstateFactory = null;
		try{
			//项目
			clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			//项目机构合作
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			//客户
			clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientCus =(CusPerService.Client) clientFactoryCus.getClient();
			//关系人
			clientFactoryCusAcct = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
			CusAcctService.Client clientCusAcct =(CusAcctService.Client) clientFactoryCusAcct.getClient();
			//字典service
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			//地区
			clientFactoryArea = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client clientArea =(SysAreaInfoService.Client) clientFactoryArea.getClient();
			
			//多物业信息
			clientEstateFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client clientEstate = (BizProjectEstateService.Client) clientEstateFactory.getClient();
			
			//项目信息
			Project tempProject =clientProject.getLoanProjectByProjectId(projectPartnerDto.getProjectId());
			//赎楼信息----------------
			ProjectForeclosure tempForeclosure =clientProject.getForeclosureByProjectId(projectPartnerDto.getProjectId());
			//物业信息
			List<BizProjectEstate> tempEstateList =  clientEstate.getAllByProjectId(projectPartnerDto.getProjectId());
			//收费
			ProjectGuarantee tempGuarantee = clientProject.getGuaranteeByProjectId(projectPartnerDto.getProjectId());
			
			//客户信息
			int perId = clientCus.selectPerIdByAcctId(tempProject.getAcctId());
			CusPerBaseDTO cusPerBaseDTO = clientCus.getCusPerBase(perId);
			CusPerson tempCusPerson = cusPerBaseDTO.getCusPerson();
			
			//资金项目信息
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setPid(projectPartnerDto.getPid());
			queryPartner.setPartnerNo(projectPartnerDto.getPartnerNo());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			//查询项目，并懒加懒相关信息
/*			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setProjectId(projectPartnerDto.getProjectId());
			queryPartner.setPartnerNo(projectPartnerDto.getPartnerNo());
			ProjectPartnerDto tempDto = clientPartner.findProjectPartnerAndLazy(queryPartner);*/
			
			ProjectPartnerDto tempDto = new ProjectPartnerDto();
			
			//设置多物业信息
			tempDto.setEstateList(tempEstateList);
			//设置赎楼信息
			tempDto.setProjectForeclosure(tempForeclosure);
			//设置物业信息
			tempDto.setProjectProperty(tempProject.getProjectProperty());
			
			//转换对象值
			tempDto = PartnerProjectUtil.convertBean(tempDto, tempProject, tempGuarantee, tempPartner, tempCusPerson);
			
			
			// 城市
			SysAreaInfo cityAreaInfo=  clientArea.getSysAreaInfoByCode(tempDto.getCityCode());
			
			//关系人=========
			CusPerson queryCusPerson = new CusPerson();
			CusAcct queryCusAcct = new CusAcct();
			queryCusAcct.setPid(tempDto.getAcctId());
			queryCusPerson.setCusAcct(queryCusAcct);
			queryCusPerson.setRelationType(5);
			queryCusPerson.setPage(1);
			queryCusPerson.setRows(100);
			List<GridViewDTO> contactList = clientCusAcct.getCusPersons(queryCusPerson);
			
			//如果关系人为空，设置为经办人，身份证为借款人（系统用户没有身份证，与点融沟通结果）
			if(contactList == null || contactList.size() == 0){
				
				contactList = new ArrayList<GridViewDTO>();
				GridViewDTO gridViewDTO = new GridViewDTO();
				gridViewDTO.setValue1(tempProject.getManagers());	//姓名
				//手机号
				gridViewDTO.setValue5(StringUtil.isBlank(tempProject.getManagersPhone()) ? tempDto.getPhone() :tempProject.getManagersPhone());	
				gridViewDTO.setValue10("ID_CARD"); //证件类型
				gridViewDTO.setValue11(tempDto.getCardNo().trim()); //证件号码
				gridViewDTO.setValue12("同事");	//关系
				contactList.add(gridViewDTO);
			}
			
			//如果地区为空，返回去填写
			if(StringUtil.isBlank(cusPerBaseDTO.getCusPerson().getLiveDistrictCode())){
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg","客户居住地址省市区信息不全，请返回客户资料补充完整");
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 1);
				resultJson.put("data", dataJson);
				return resultJson;
			}
			
			JSONObject paramJson = new JSONObject();
			
			//贷款信息====================================
			JSONObject loanAppJson = new JSONObject();
			loanAppJson.put("externalId", tempDto.getPid());						//第三方借款编号
			loanAppJson.put("appAmount", (int)tempDto.getApplyMoney());				//申请金额-精确到整十
			
			
			/*	子产品类型 
			 *  交易类	PRODUCT_A
			 *  非交易类	PRODUCT_B
			 */
			if("交易".equals(projectPartnerDto.getBusinessCategoryStr()) || 
					(!StringUtil.isBlank(tempProject.getBusinessTypeText()) 
							&& tempProject.getBusinessTypeText().startsWith("交易")) ){
				loanAppJson.put("channelProduct","PRODUCT_A");	
				
				if(tempDto.getApplyDate() > 90){
					resultJson = new JSONObject();
					resultJson.put("status", 0);
					resultJson.put("msg","交易类贷款期限为1-90天");
					JSONObject dataJson = new JSONObject();
					dataJson.put("err_code", 1);
					resultJson.put("data", dataJson);
					return resultJson;
				}
			}else{
				loanAppJson.put("channelProduct", "PRODUCT_B");
				if(tempDto.getApplyDate() > 30){
					resultJson = new JSONObject();
					resultJson.put("status", 0);
					resultJson.put("msg","非交易类贷款期限为1-30天");
					JSONObject dataJson = new JSONObject();
					dataJson.put("err_code", 1);
					resultJson.put("data", dataJson);
					return resultJson;
				}
			}
			
			
			
			//默认以31天计算
//			int loanMonth= (int)Math.ceil(((double)tempDto.getApplyDate())/31);	//贷款期限(月)
			loanAppJson.put("loanMaturityByDay", tempDto.getApplyDate());		//单位天，交易类填1-90，非交易类填1-30		
			loanAppJson.put("scene", "HOUSE");									//场景
			loanAppJson.put("purpose", "FUND_FLOW");								//贷款目的
//			loanAppJson.put("paymentMethod", "PAY_AT_THE_END");						//还款方式
			loanAppJson.put("paymentMethod", "BULLET_DYNAMIC");						//还款方式-一次性还本付息-活期
			
			
			//放款时间在申请放款的时候再传递
			//loanAppJson.put("desiredIssueDate", tempDto.getApplyLoanDate());		//期望放款时间
			
			//申请城市
			loanAppJson.put("appCity", StringUtil.isBlank(cityAreaInfo.getAreaName()) ? "" : cityAreaInfo.getAreaName());	
			paramJson.put("loanApp", loanAppJson);
			
			//个人信息====================================
			JSONObject personalInfoJson = new JSONObject();
			personalInfoJson.put("fullName", tempDto.getUserName());				//姓名
			personalInfoJson.put("idCard", tempDto.getCardNo().trim());					//身份证号码	
			personalInfoJson.put("mobilePhone", tempDto.getPhone());			//手机号码	
			personalInfoJson.put("email", StringUtil.isBlank(tempCusPerson.getMail()) ? "" : tempCusPerson.getMail());					//邮箱
			//personalInfoJson.put("annualIncome", cusPerson.getMonthIncome()*12);			//年收入
			personalInfoJson.put("houseNum", "");				//房产数量
			personalInfoJson.put("residenceYearsLimit", "");	//居住年限
			//居住地址
			JSONObject residenceAddressJson = new JSONObject();
			residenceAddressJson.put("province", clientArea.getSysAreaInfoByCode(tempCusPerson.getLiveProvinceCode()).getAreaName());				//省
			residenceAddressJson.put("city",  clientArea.getSysAreaInfoByCode(tempCusPerson.getLiveCityCode()).getAreaName());					//市
			residenceAddressJson.put("district",  clientArea.getSysAreaInfoByCode(tempCusPerson.getLiveDistrictCode()).getAreaName());				//区
			residenceAddressJson.put("detailedAddress", StringUtil.isBlank(tempCusPerson.getLiveAddr()) ? "" : tempCusPerson.getLiveAddr());		//详细地址
			personalInfoJson.put("residenceAddress",residenceAddressJson);
			//户籍地址
/*			JSONObject permanentAddressJson = new JSONObject();
			permanentAddressJson.put("province", "");				//省
			permanentAddressJson.put("city", "");					//市
			permanentAddressJson.put("district", "");				//区
			permanentAddressJson.put("detailedAddress", StringUtil.isBlank(cusPerson.getCensusAddr()) ? "" : cusPerson.getCensusAddr());		//详细地址
			personalInfoJson.put("permanentAddress",permanentAddressJson);*/
			
			paramJson.put("personalInfo", personalInfoJson);
			
			//打款帐户信息====================================
			JSONObject bankAccountInfoJson = new JSONObject();
			bankAccountInfoJson.put("accountNum", tempDto.getPayAcctNo().trim());		//放扣款银行卡号
			bankAccountInfoJson.put("accountName",tempDto.getPayAcctName());			//放扣款银行卡户名
			bankAccountInfoJson.put("bank", tempDto.getPayBankName());					//开户银行
			bankAccountInfoJson.put("branch", tempDto.getPayBankBranch());				//支行
			bankAccountInfoJson.put("province",clientArea.getSysAreaInfoByCode(tempDto.getPayProvinceCode()).getAreaName());				//省
			bankAccountInfoJson.put("city", clientArea.getSysAreaInfoByCode(tempDto.getPayCityCode()).getAreaName());					//城市
			bankAccountInfoJson.put("bankPhone", "");				//放扣款银行卡用户手机号
			bankAccountInfoJson.put("bankIdCard", "");				//放扣款银行卡户名身份证号码
			paramJson.put("bankAccountInfo", bankAccountInfoJson);
			
			//申请人银行账户信息====================================
			JSONObject borrowerBankAccountInfoJson = new JSONObject();
			borrowerBankAccountInfoJson.put("accountNum", tempDto.getPaymentAcctNo().trim());				//放扣款银行卡号
			borrowerBankAccountInfoJson.put("accountName", StringUtil.isBlank(tempDto.getPaymentAcctName()) ? "" : tempDto.getPaymentAcctName());				//放扣款银行卡户名
			borrowerBankAccountInfoJson.put("bank", tempDto.getPaymentBank());					//开户银行
			borrowerBankAccountInfoJson.put("province", "");				//省
			borrowerBankAccountInfoJson.put("city", "");					//城市
			borrowerBankAccountInfoJson.put("branch", "");					//支行
			borrowerBankAccountInfoJson.put("bankPhone", tempDto.getPhone());				//放扣款银行卡用户手机号
			borrowerBankAccountInfoJson.put("bankIdCard", tempDto.getCardNo().trim());				//放扣款银行卡户名身份证号码
			paramJson.put("borrowerBankAccountInfo", borrowerBankAccountInfoJson);
			
			//联系人信息====================================
			JSONArray contactInfoArray = new JSONArray();
			if(contactList !=null && contactList.size() > 0){
				JSONObject tempContactJson = null;
				JSONObject tempAddressJson = null;
				String tempRelation = "";
				for (GridViewDTO indexObj : contactList) {
					
					tempContactJson = new JSONObject();
					tempContactJson.put("name", indexObj.getValue1());				//联系人姓名
					tempContactJson.put("phone",indexObj.getValue5());				//联系电话
					
					
					if("共同借款人".equals(indexObj.getValue12())){
						tempRelation = "FRIEND";
					}else if("产权共有人".equals(indexObj.getValue12())){
						tempRelation = "FRIEND";
					}else if("亲属".equals(indexObj.getValue12())){
						tempRelation = "COLLATERAL";	// 父母:PARENT 配偶:COUPLE 子女:CHILD 兄弟姐妹:SIBLING	   旁系亲属:COLLATERAL
					}else if("朋友".equals(indexObj.getValue12())){
						tempRelation = "FRIEND";
					}else if("上下级".equals(indexObj.getValue12()) || "同事".equals(indexObj.getValue12()) ){
						tempRelation = "COLLEAGUE";
					}else{
						tempRelation = "FRIEND";
					}
					
					tempContactJson.put("relation", tempRelation);				//联系人关系
					tempContactJson.put("cardType", "ID_CARD");				//证件类型
					tempContactJson.put("cardNum",StringUtil.isBlank(indexObj.getValue11()) ? "" : indexObj.getValue11().trim());  //证件号码
					tempContactJson.put("companyName", "");				//公司名称
					tempContactJson.put("jobTitle", "");				//职位
					tempContactJson.put("addressType", "");				//地址类型
					//家庭或工作地址	
					tempAddressJson = new JSONObject();
					tempAddressJson.put("province", "");					//省
					tempAddressJson.put("city", "");						//市
					tempAddressJson.put("district", "");					//区
					tempAddressJson.put("detailedAddress", "");				//详细地址
					tempContactJson.put("address", tempAddressJson);		 
					contactInfoArray.add(tempContactJson);
				}
			}
			paramJson.put("contactInfo", contactInfoArray);
			
			//附加信息====================================
			JSONObject extrasJson = new JSONObject();
			//文件地址
			extrasJson.put("file", getRequestFileJsonAndPackage(projectPartnerDto.getRequestFiles(), tempDto.getProjectId(), tempDto.getPartnerNo(),tempDto.getPid(), getServerBaseUrl(request)));				
			
			//产品信息
			JSONObject businessJson = new JSONObject();
			businessJson.put("productName", tempDto.getBusinessTypeStr());				//产品名称
			businessJson.put("businessType", tempDto.getBusinessCategoryStr());				//业务类型（交易非交易）
			
//			String	businessSource = clientSys.getSysLookupValByName(tempProject.getBusinessSource());
			//业务来源 - 内单外单
			businessJson.put("businessSource", tempProject.getInnerOrOut() == 2 ? "外单" : "内单");			
			//经办人
			businessJson.put("operatorName", tempProject.getManagers() );				
			//经办人电话
			businessJson.put("operatorPhone", StringUtil.isBlank(tempProject.getManagersPhone()) ? tempDto.getPhone() :tempProject.getManagersPhone());				
			//业务联系人
//			businessJson.put("businessContactsName", tempProject.getBusinessContacts());		
//			businessJson.put("businessContactsPhone", tempProject.getContactsPhone());		//业务联系人电话
			businessJson.put("businessContactsName", "");		 
			businessJson.put("businessContactsPhone", "");		//业务联系人电话
			extrasJson.put("business", businessJson);
			
			
			//物业信息---------------------------------------------------------------
			
			String property_name ="";		//物业名称
			String house_card_no ="";		//房产证号
			double total_area = 0 ; 		//面积
			double evaluating_price = 0;	//评估价(元) 
			double transaction_price = 0 ;	//成交价(元) 
			double original_price = 0 ; 	//登记价(元) 原价
			for (BizProjectEstate indexObj : tempEstateList) {
				property_name += indexObj.getHouseName()+",";
				house_card_no += indexObj.getHousePropertyCard()+",";
				total_area += indexObj.getArea();
				transaction_price += indexObj.getTranasctionMoney();
				evaluating_price += indexObj.getEvaluationPrice();
				original_price += indexObj.getCostMoney();
			}
			
			if(!StringUtil.isBlank(property_name)){
				property_name = property_name.substring(0,property_name.length()-1);
				house_card_no = house_card_no.substring(0,house_card_no.length()-1);
			}
			
			ProjectProperty tempProperty =tempProject.getProjectProperty();
			JSONObject propertyJson = new JSONObject();
			propertyJson.put("name", property_name);				//物业名称
			propertyJson.put("district", clientArea.getSysAreaInfoByCode(tempDto.getHouseCityCode()).getAreaName());				//区域
			propertyJson.put("area", total_area);							//面积
			propertyJson.put("certificateNum", house_card_no);				//房产证号
			propertyJson.put("originalPrice", original_price);				//原价
			propertyJson.put("evaluatingPrice", evaluating_price);			//评估价
			propertyJson.put("transactionPrice", transaction_price);		//成交价
			//赎楼成数
			propertyJson.put("ransomRate", tempProperty.getForeRate() == 0 ? "" : tempProperty.getForeRate());					
			extrasJson.put("property", propertyJson);
			
			//业主信息
			JSONObject ownerJson = new JSONObject();
			//业主姓名
			ownerJson.put("fullName",  StringUtil.isBlank(tempProperty.getSellerName()) ? "" : tempProperty.getSellerName().split(",")[0].trim());				
			ownerJson.put("idCard", StringUtil.isBlank(tempProperty.getSellerCardNo()) ? "" : tempProperty.getSellerCardNo().split(",")[0].trim());				//业主身份证号
			ownerJson.put("phone", StringUtil.isBlank(tempProperty.getSellerPhone()) ? "" : tempProperty.getSellerPhone().split(",")[0].trim());					//业主联系电话
			ownerJson.put("address", StringUtil.isBlank(tempProperty.getSellerAddress()) ? "" : tempProperty.getSellerAddress().split(",")[0].trim());				//业主地址
			extrasJson.put("owner", ownerJson);
			
			//买方信息
			JSONObject buyerJson = new JSONObject();
			buyerJson.put("fullName",  StringUtil.isBlank(tempProperty.getBuyerName()) ? "" : tempProperty.getBuyerName().split(",")[0].trim());				//业主姓名
			buyerJson.put("idCard",  StringUtil.isBlank(tempProperty.getBuyerCardNo()) ? "" : tempProperty.getBuyerCardNo().split(",")[0].trim());				//业主身份证号
			buyerJson.put("phone",  StringUtil.isBlank(tempProperty.getBuyerPhone()) ? "" : tempProperty.getBuyerPhone().split(",")[0].trim());					//业主联系电话
			buyerJson.put("address",StringUtil.isBlank(tempProperty.getBuyerAddress()) ? "" : tempProperty.getBuyerAddress().split(",")[0].trim());				//业主地址
			extrasJson.put("buyer", buyerJson);
			
			//原贷款银行信息
			JSONObject originalLoanJson = new JSONObject();
 
			//原贷款银行
			originalLoanJson.put("bankName", StringUtil.isBlank(tempForeclosure.getOldBankStr()) ? "" : tempForeclosure.getOldBankStr());				
			originalLoanJson.put("amount", tempForeclosure.getOldLoanMoney());					//原贷款金额
			originalLoanJson.put("balance", tempForeclosure.getOldOwedAmount());					//原贷款欠款金额
			originalLoanJson.put("dueDate", StringUtil.isBlank(tempForeclosure.getOldLoanTime()) ? "" : tempForeclosure.getOldLoanTime());					//贷款结束时间
			originalLoanJson.put("bankContactsName", StringUtil.isBlank(tempForeclosure.getOldLoanPerson()) ? "" : tempForeclosure.getOldLoanPerson().trim());			//银行联系人
			originalLoanJson.put("bankContactsPhone", StringUtil.isBlank(tempForeclosure.getOldLoanPhone()) ? "" : tempForeclosure.getOldLoanPhone().trim());			//联系电话
			extrasJson.put("originalLoan", originalLoanJson);
			
			//第三方借款人信息
			JSONObject thirdBorrowerJson = new JSONObject();
			//第三人借款人
			thirdBorrowerJson.put("fullName", StringUtil.isBlank(tempForeclosure.getThirdBorrower()) ? "" : tempForeclosure.getThirdBorrower().trim());				
			//身份证号
			thirdBorrowerJson.put("idCard", StringUtil.isBlank(tempForeclosure.getThirdBorrowerCard()) ? "" : tempForeclosure.getThirdBorrowerCard().trim());							
			//手机
			thirdBorrowerJson.put("phone", StringUtil.isBlank(tempForeclosure.getThirdBorrowerPhone()) ? "" : tempForeclosure.getThirdBorrowerPhone().trim());					
			//居住地址
			thirdBorrowerJson.put("address", StringUtil.isBlank(tempForeclosure.getThirdBorrowerAddress()) ? "" : tempForeclosure.getThirdBorrowerAddress().trim());				
			extrasJson.put("thirdBorrower", thirdBorrowerJson);
			
			//新贷款银行信息
			JSONObject currentLoanJson = new JSONObject();
			//新贷款银行 
			currentLoanJson.put("bankName", StringUtil.isBlank(tempForeclosure.getNewBankStr())? "" :  tempForeclosure.getNewBankStr().trim());				
			currentLoanJson.put("amount", tempForeclosure.getNewLoanMoney());					//新贷款金额
			currentLoanJson.put("bankContactsName", StringUtil.isBlank(tempForeclosure.getNewLoanPerson()) ? "" : tempForeclosure.getNewLoanPerson().trim());			//银行联系人
			currentLoanJson.put("bankContactsPhone", StringUtil.isBlank(tempForeclosure.getNewLoanPhone()) ? "" : tempForeclosure.getNewLoanPhone().trim());			//联系电话
			extrasJson.put("currentLoan", currentLoanJson);
			
			//监管账号信息
			JSONObject regulatoryBankJson = new JSONObject();
			regulatoryBankJson.put("bankName",StringUtil.isBlank(tempForeclosure.getSuperviseDepartmentStr()) ? "" :  tempForeclosure.getSuperviseDepartmentStr().trim());				
			regulatoryBankJson.put("amount", tempForeclosure.getFundsMoney() == 0 ? "" : tempForeclosure.getFundsMoney());					//资金监管金额
			extrasJson.put("regulatoryBank", regulatoryBankJson);
			
			//公积金银行信息
			JSONObject housingFundJson = new JSONObject();
			housingFundJson.put("bankName",StringUtil.isBlank(tempForeclosure.getAccumulationFundBankStr()) ? "" : tempForeclosure.getAccumulationFundBankStr() );				//公积金银行
			housingFundJson.put("amount", tempForeclosure.getAccumulationFundMoney() == 0 ? "" : tempForeclosure.getAccumulationFundMoney());					//公积金贷款金额
			extrasJson.put("housingFund", housingFundJson);
			
			extrasJson.put("entrustNotarialDate",StringUtil.isBlank(tempForeclosure.getNotarizationDate()) ? "" : tempForeclosure.getNotarizationDate());		//委托公证日期
			
			String paymentMethod = "";
			if(tempForeclosure.getPaymentType() == 1){
				paymentMethod = "按揭";
			}else if(tempForeclosure.getPaymentType() == 2){
				paymentMethod = "组合贷";
			}else if(tempForeclosure.getPaymentType() == 3){
				paymentMethod = "公积金贷";
			}else if(tempForeclosure.getPaymentType() == 4){
				paymentMethod = "一次性付款";
			}else if(tempForeclosure.getPaymentType() == 5){
				paymentMethod = "经营贷";
			}else if(tempForeclosure.getPaymentType() == 6){
				paymentMethod = "消费贷";
			}
			extrasJson.put("paymentMethod", paymentMethod);			//借款方式
			
			//赎楼帐号
			extrasJson.put("ransomBankAccount", StringUtil.isBlank(tempForeclosure.getForeAccount()) ? "" : tempForeclosure.getForeAccount().trim());
			
			paramJson.put("extras", extrasJson);
			
			logger.info(">>>>>>apply 明文  paramJson:"+paramJson.toString());
			
			
			
			
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
			
			
			
			
			//请求获取token
			List<HttpRequestParam> headParamList = new ArrayList<HttpRequestParam>();
			String accessToken = getAccessToken(request);
			
			logger.info(">>>>>>apply accessToken:"+accessToken);
			
			HttpRequestParam authorizationParam = new HttpRequestParam("Authorization",accessToken);
			HttpRequestParam contentTypeParam = new HttpRequestParam("Content-Type","application/json");
			headParamList.add(authorizationParam);
			headParamList.add(contentTypeParam);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.post(DR_PARTNER_API_BASE_URL+"/v2/qfang/apps", paramJson, headParamList);
			httpUtils.closeConnection();
			
			logger.info(">>>>>>apply http resultHttpJson:"+resultHttpJson);
			
			//======================测试=====================================
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", "20161024001");
				dataJson.put("err_code", 0);
				resultJson.put("data", dataJson);
				return resultJson;
			}*/
			
			if(resultHttpJson!=null){
				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg",resultHttpJson.getString("message"));
				JSONObject dataJson = new JSONObject();
				
				//成功
				if("20000".equals(resultHttpJson.getString("code"))){
					JSONObject dataJaonTemp = resultHttpJson.getJSONObject("data");
					dataJson.put("loan_id", dataJaonTemp.get("loanAppId"));
					dataJson.put("err_code", 0);
				}else{
					dataJson.put("err_code", 1);
				}
				resultJson.put("data", dataJson);
			}
		}catch(Exception e){
			logger.error(">>>>>>apply error",e);
			return null;
		}finally {
			destroyFactory(clientFactoryProject);
			destroyFactory(clientFactoryPartner);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
			destroyFactory(clientFactoryCusAcct);
			destroyFactory(clientFactoryArea);
			destroyFactory(clientEstateFactory);
		}
		return resultJson;
	}

	/**
	 * 查询第三方贷款状态
	 * @param projectPartnerDto
	 * @param includePostLoanStatus	是否包含贷后的状态｜true/false	
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryLoanStatus(ProjectPartnerDto projectPartnerDto,boolean includePostLoanStatus,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		try{
			//测试-------------------------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
 
				String filePath  = this.getClassPath() +"dr_status.txt";
				logger.info(">>>>>>filePath:"+filePath);
				String testResultStr = FileUtil.readTxtFile(filePath);
				return JSONObject.parseObject(testResultStr);
			}
			
			
			String url = DR_PARTNER_API_BASE_URL+"/v2/qfang/apps/status?extId="+projectPartnerDto.getPid()+"&includePostLoanStatus="+includePostLoanStatus;
			
			logger.info(">>>>>>queryLoanStatus url:"+url);
			
			//请求获取token
			List<HttpRequestParam> headParamList = new ArrayList<HttpRequestParam>();
			HttpRequestParam authorizationParam = new HttpRequestParam("Authorization",getAccessToken(request));
			headParamList.add(authorizationParam);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.get(url, headParamList);
			httpUtils.closeConnection();
			
			logger.info(">>>>>>queryLoanStatus http resultHttpJson:"+resultHttpJson);
			
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("externalId", projectPartnerDto.getPid());
				dataJson.put("status", "SETTLED");
				dataJson.put("statusD","1477292471000");
				dataJson.put("message", "OK");
				dataJson.put("loanAppId", projectPartnerDto.getLoanId());
				resultJson.put("data", dataJson);
				return resultJson;
			}*/
			
			if(resultHttpJson!=null){
				
				//转换格式
				resultJson = new JSONObject();
				JSONObject dataJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg",resultHttpJson.getString("message"));
				
				//成功
				if("20000".equals(resultHttpJson.getString("code"))){
					dataJson = resultHttpJson.getJSONObject("data");
					dataJson.put("err_code", 0);
				}else{
					dataJson.put("err_code", 1);
				}
				resultJson.put("data", dataJson);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>queryLoanStatus error",e);
			return null;
		}
		return resultJson;
	}
	
	
	
	/**
	 *  上标通知 
	 *  点融收到通知后，直接上标
	 */
	public JSONObject notifyShelf(ProjectPartnerDto projectPartnerDto ,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		try{
 
			JSONObject paramJson = new JSONObject();
			
			String url = DR_PARTNER_API_BASE_URL+"/v1/qfang/apps/financing/confirmation?extId="+projectPartnerDto.getPid();
 
			logger.info(">>>>>>notifyShelf 明文  url:"+url);
			
			//请求获取token
			List<HttpRequestParam> headParamList = new ArrayList<HttpRequestParam>();
			String accessToken = getAccessToken(request);
			
			logger.info(">>>>>>notifyShelf accessToken:"+accessToken);
			
			HttpRequestParam authorizationParam = new HttpRequestParam("Authorization",accessToken);
			HttpRequestParam contentTypeParam = new HttpRequestParam("Content-Type","application/json");
			headParamList.add(authorizationParam);
			headParamList.add(contentTypeParam);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.post(url, paramJson, headParamList);
			httpUtils.closeConnection();
			
			logger.info(">>>>>>notifyShelf http resultHttpJson:"+resultHttpJson);
			
			//======================测试=====================================
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				resultJson.put("data", dataJson);
				return resultJson;
			}*/
			
			if(resultHttpJson!=null){
				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg",resultHttpJson.getString("message"));
				JSONObject dataJson = new JSONObject();
				
				//成功
				if("20000".equals(resultHttpJson.getString("code"))){
					dataJson.put("err_code", 0);
				}else{
					dataJson.put("err_code", 1);
				}
				resultJson.put("data", dataJson);
			}
		}catch(Exception e){
			logger.error(">>>>>>notifyShelf error",e);
			return null;
		} 
		return resultJson;
	}
	
	
	
	/**
	 * 根据第三方贷款提供的贷款id查询还款计划
	 * @param projectPartnerDto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryPaymentPlan(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		try{
			
			//测试-------------------------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				String filePath  = this.getClassPath() +"dr_payment_plan.txt";
				logger.info(">>>>>>filePath:"+filePath);
				String testResultStr = FileUtil.readTxtFile(filePath);
				return JSONObject.parseObject(testResultStr);
			}
			
			String url = DR_PARTNER_API_BASE_URL+"/v2/qfang/loans/paymentPlan?extId="+projectPartnerDto.getPid();
			
			logger.info(">>>>>>queryPaymentPlan url:"+url);
			
			//请求获取token
			List<HttpRequestParam> headParamList = new ArrayList<HttpRequestParam>();
			HttpRequestParam authorizationParam = new HttpRequestParam("Authorization",getAccessToken(request));
			headParamList.add(authorizationParam);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.get(url, headParamList);
			httpUtils.closeConnection();
			
			logger.info(">>>>>>queryPaymentPlan http resultHttpJson:"+resultHttpJson);
			
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				JSONObject loan = new JSONObject();
				JSONObject content = new JSONObject();
				
				JSONArray planArray = new JSONArray();
				
				JSONObject plan1 = new JSONObject();
				plan1.put("dueDate", "1477324800000");
				plan1.put("duePrincipal", 8996.67);
				plan1.put("estimatedDueInterest", 3125);
				plan1.put("dueManagementFee", 377.5);
				plan1.put("estimatedOtherFee", 0);
				plan1.put("index", 1);
				plan1.put("totalAmount", 12499.17);
				plan1.put("principalOut", 241003.33);
				
				JSONObject plan2 = new JSONObject();
				plan2.put("dueDate", "1477411200000");
				plan2.put("duePrincipal", 9109.13);
				plan2.put("estimatedDueInterest", 3012.55);
				plan2.put("dueManagementFee", 377.5);
				plan2.put("estimatedOtherFee", 0);
				plan2.put("index", 2);
				plan2.put("totalAmount", 12499.18);
				plan2.put("principalOut", 231894.2);
				planArray.add(plan1);
				planArray.add(plan2);
				
				content.put("paymentPlans", planArray);
				
				loan.put("id", projectPartnerDto.getPid());
				loan.put("loanAppId", projectPartnerDto.getLoanId());
				loan.put("content", content);
				
				dataJson.put("loan", loan);
				
				resultJson.put("data", dataJson);
				return resultJson;
			}	*/	
			
			if(resultHttpJson!=null){
				//转换格式
				resultJson = new JSONObject();
				JSONObject dataJson = new JSONObject();
				
				resultJson.put("status", 0);
				resultJson.put("msg",resultHttpJson.getString("message"));
				//成功
				if("20000".equals(resultHttpJson.getString("code"))){
					dataJson = resultHttpJson.getJSONObject("data");
					dataJson.put("err_code", 0);
				}else{
					dataJson.put("err_code", 1);
				}
				resultJson.put("data", dataJson);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>queryPaymentPlan error",e);
			return null;
		}
		return resultJson;
	}
	
	
	
	/**
	 * 借款人提交渠道实际还款信息(点融)
	 * @param projectPartnerDto
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject applyPayment(List<ProjectPartnerRefund> refundList,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		BaseClientFactory clientFactoryPartner = null;
		try{
			
			/*{
			    "code": 20000,
			    "data": {
			        "loans": [
			            {
			                "currentNum": 1,
			                "id": "123",
			                "message": "OK"
			            }
			        ]
			    },
			    "message": "OK"
			}*/
			
			//测试-------------------------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				
				JSONArray arrays = new JSONArray();
				for (ProjectPartnerRefund indexObj  : refundList) {
					JSONObject indesJson = new JSONObject();
					indesJson.put("currentNum", indexObj.getCurrNo());
					indesJson.put("message", "OK");
					indesJson.put("id",indexObj.getPid());
					arrays.add(indesJson);
				}
				dataJson.put("loans", arrays);
				resultJson.put("data", dataJson);
				return resultJson;
			}
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//查询项目，并懒加懒相关信息
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setProjectId(refundList.get(0).getProjectId());
			queryPartner.setPartnerNo(refundList.get(0).getPartnerNo());
			ProjectPartnerDto tempDto = clientPartner.findProjectPartnerAndLazy(queryPartner);
			
			JSONObject paramJson = new JSONObject();
			JSONArray loanArray = new JSONArray();
			JSONObject loanJson = null;
			
			for (ProjectPartnerRefund indexObj : refundList) {
				loanJson = new JSONObject();
				loanJson.put("id", indexObj.getPartnerId());
				loanJson.put("name", tempDto.getUserName());
				loanJson.put("dueDate", indexObj.getCurrPlanRefundDate());
				loanJson.put("paymentDate", indexObj.getCurrRealRefundDate());
				loanJson.put("currentNumber", indexObj.getCurrNo());
				loanJson.put("settlementStatus", indexObj.getIsSettlementStatus());
				loanJson.put("recTotalAmount", indexObj.getCurrShouldTotalMoney());
				loanJson.put("recPrincipal", indexObj.getCurrShouldCapitalMoney());
				loanJson.put("recInterest", indexObj.getCurrShouldXiFee());
				loanJson.put("recMgmtFee", indexObj.getCurrShouldManageFee());
				loanJson.put("payoffTotalAmount", indexObj.getCurrRealTotalMoney());
				loanJson.put("payoffPrincipal", indexObj.getCurrRealCapitalMoney());
				loanJson.put("payoffInterest", indexObj.getCurrRealXiFee());
				loanJson.put("payoffMgmtFee", indexObj.getCurrRealManageFee());
				loanJson.put("overdueStatus", indexObj.getCurrOverdueStatus());
				loanJson.put("overdueDays", indexObj.getCurrOverdueDays());
				loanJson.put("outstandingPrincipal", indexObj.getOweCapitalMoney());
				
				loanArray.add(loanJson);
			}
			paramJson.put("loans", loanArray);
			
			logger.info(">>>>>>applyPayment 明文  paramJson:"+paramJson.toString());
			
			//请求获取token
			List<HttpRequestParam> headParamList = new ArrayList<HttpRequestParam>();
			HttpRequestParam authorizationParam = new HttpRequestParam("Authorization",getAccessToken(request));
			headParamList.add(authorizationParam);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.post(DR_PARTNER_API_BASE_URL+"/v1/qfang/loans/payment", paramJson, headParamList);
			httpUtils.closeConnection();
			
			logger.info(">>>>>>queryLoanStatus http resultHttpJson:"+resultHttpJson);
			
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				JSONArray loanArray1 = new JSONArray();
				for (ProjectPartnerRefund indexObj : refundList) {
					JSONObject loan  = new JSONObject();
					
					loan.put("id", tempDto.getPid());
					loan.put("currentNum", indexObj.getCurrNo());
					loan.put("message", "OK");
					loanArray1.add(loan);
				}
				dataJson.put("loans", loanArray1);
				
				dataJson.put("err_code", 0);
				resultJson.put("data", dataJson);
				return resultJson;
			}	*/ 
			
			if(resultHttpJson!=null){
				//转换格式
				resultJson = new JSONObject();
				JSONObject dataJson = new JSONObject();
				
				resultJson.put("status", 0);
				resultJson.put("msg",resultHttpJson.getString("message"));
				//成功
				if("20000".equals(resultHttpJson.getString("code"))){
					dataJson = resultHttpJson.getJSONObject("data");
					dataJson.put("err_code", 0);
				}else{
					dataJson.put("err_code", 1);
				}
				resultJson.put("data", dataJson);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>applyPayment error",e);
			return null;
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>applyPayment clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		return resultJson;
	}
	
	
	/**
	 * 查询提前还款信息
	 * @param pid
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public JSONObject getAdvancedSettlement(ProjectPartnerDto projectPartnerDto,HttpServletRequest request)throws Exception{
		JSONObject resultJson = null;
		try{
			
			//测试-------------------------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				String filePath  = this.getClassPath() +"dr_before_refund_list.txt";
				logger.info(">>>>>>filePath:"+filePath);
				String testResultStr = FileUtil.readTxtFile(filePath);
				return JSONObject.parseObject(testResultStr);
			}
			
			String url = DR_PARTNER_API_BASE_URL+"/v2/qfang/loans/advancedSettlement?extId="+projectPartnerDto.getPid();
			
			logger.info(">>>>>>getAdvancedSettlement url:"+url);
			
			//请求获取token
			List<HttpRequestParam> headParamList = new ArrayList<HttpRequestParam>();
			HttpRequestParam authorizationParam = new HttpRequestParam("Authorization",getAccessToken(request));
			headParamList.add(authorizationParam);
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.get(url, headParamList);
			httpUtils.closeConnection();
			
			logger.info(">>>>>>getAdvancedSettlement http resultHttpJson:"+resultHttpJson);
			
/* 			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", "ok");
				JSONObject dataJson = new JSONObject();
				JSONObject content = new JSONObject();
				
				JSONArray paymentArray = new JSONArray();
				
				JSONObject payment1 = new JSONObject();
				payment1.put("date", "2016-12-21");
				payment1.put("amount", 1000550);
				
				JSONObject payment2 = new JSONObject();
				payment2.put("date", "2016-12-22");
				payment2.put("amount", 9109.13);
 
				paymentArray.add(payment1);
				paymentArray.add(payment2);
				
				dataJson.put("externalId", projectPartnerDto.getPid());
				dataJson.put("loanAppId", projectPartnerDto.getLoanId());
				dataJson.put("payment", paymentArray);
				
				resultJson.put("data", dataJson);
				return resultJson;
			}	*/
			
			if(resultHttpJson!=null){
				//转换格式
				resultJson = new JSONObject();
				JSONObject dataJson = new JSONObject();
				
				resultJson.put("status", 0);
				resultJson.put("msg",resultHttpJson.getString("message"));
				//成功
				if("20000".equals(resultHttpJson.getString("code"))){
					dataJson = resultHttpJson.getJSONObject("data");
					dataJson.put("err_code", 0);
				}else{
					dataJson.put("err_code", 1);
				}
				resultJson.put("data", dataJson);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>getAdvancedSettlement error",e);
			return null;
		}
		return resultJson;
	}
	
	
	/**
	 *  渠道提前还款提交
	 */
	public JSONObject advancedSettlement(ProjectPartnerDto projectPartnerDto ,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		try{
			
			//测试-------------------------------------------------
			if("true".equals(IS_PARTNER_API_DEBUG)){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				dataJson.put("loanAppId", projectPartnerDto.getLoanId());
				dataJson.put("externalId", projectPartnerDto.getPid());
				
				resultJson.put("data", dataJson);
				return resultJson;
			}
			
			
			String url = DR_PARTNER_API_BASE_URL+"/v2/qfang/loans/advancedSettlement";
			
			url += "?extId="+projectPartnerDto.getPid()+"&date="+projectPartnerDto.getRefundDate()
					+"&amount="+CommonUtil.formatDoubleNumber(projectPartnerDto.getRefundLoanAmount());
 
			JSONObject paramJson = new JSONObject();
			
			logger.info(">>>>>>advancedSettlement 明文  url:"+url);
			
			//请求获取token
			List<HttpRequestParam> headParamList = new ArrayList<HttpRequestParam>();
			String accessToken = getAccessToken(request);
			
			logger.info(">>>>>>advancedSettlement accessToken:"+accessToken);
			
			HttpRequestParam authorizationParam = new HttpRequestParam("Authorization",accessToken);
			HttpRequestParam contentTypeParam = new HttpRequestParam("Content-Type","application/json");
			headParamList.add(authorizationParam);
			headParamList.add(contentTypeParam);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			JSONObject resultHttpJson = httpUtils.post(url, paramJson, headParamList);
			httpUtils.closeConnection();
			
			logger.info(">>>>>>advancedSettlement http resultHttpJson:"+resultHttpJson);
			
			//======================测试=====================================
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				
				dataJson.put("err_code", 0);
				dataJson.put("loanAppId", projectPartnerDto.getLoanId());
				dataJson.put("externalId", projectPartnerDto.getPid());
				
				resultJson.put("data", dataJson);
				return resultJson;
			} */
			
			if(resultHttpJson!=null){
				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg",resultHttpJson.getString("message"));
				JSONObject dataJson = new JSONObject();
				
				//成功
				if("20000".equals(resultHttpJson.getString("code"))){
					JSONObject dataJaonTemp = resultHttpJson.getJSONObject("data");
					dataJson.put("loanAppId", dataJaonTemp.getString("loanAppId"));
					dataJson.put("externalId", dataJaonTemp.getString("externalId"));
					dataJson.put("err_code", 0);
				}else{
					dataJson.put("err_code", 1);
				}
				resultJson.put("data", dataJson);
			}
		}catch(Exception e){
			logger.error(">>>>>>advancedSettlement error",e);
			return null;
		} 
		return resultJson;
	}
	
	
	
	
	
	
	
	
	/**
	 * 复议申请
	 */
	public JSONObject reApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		
		try{
			 
		}catch(Exception e){
			logger.error(">>>>>>reApply error",e);
			return null;
		}
		return resultJson;
	}

	
	/**
	 * 关闭
	 */
	public JSONObject close(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		try{
			 
		}catch(Exception e){
			logger.error(">>>>>>close error",e);
			return null;
		}
		return resultJson;
	}
	
	
	/**
	 * 放款申请
	 */
	public JSONObject loanApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		try{
 
			 
		}catch(Exception e){
			logger.error(">>>>>>loanApply error",e);
			return null;
		}
		return resultJson;
	}

	/**
	 * 还款申请
	 */
	public JSONObject refundApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request)throws Exception {
		 
		JSONObject resultJson = null;
		try{
			  
		}catch(Exception e){
			logger.error(">>>>>>refundApply error",e);
			return null;
		}
		return resultJson;
	}

	
	/**
	 * 获取申请文件,并打包文件生成zip包
	 * @param requestFileIds
	 * @param projectId
	 * @param partnerNo
	 * @param filePath
	 * @return
	 */
	private String getRequestFileJsonAndPackage(String requestFileIds,int projectId,String partnerNo,int partnerId,String serverUrl){
		
		String returnUrl = "";
		if(StringUtil.isBlank(requestFileIds)){
			return returnUrl;
		}
		BaseClientFactory clientFactoryPartnerFile = null;
		List<ProjectPartnerFile> fileList = new ArrayList<ProjectPartnerFile>();
		try {
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setPartnerId(partnerId);
			query.setProjectId(projectId);
			query.setPartnerNo(partnerNo);
			query.setPidList(CommonUtil.getSepStrtoList(requestFileIds, "Integer", ","));
			fileList = clientPartnerFile.findAllProjectPartnerFile(query);
	 
			//打包封装参数
			if(fileList != null){
				returnUrl =  getPackageFileUrl(fileList,projectId,serverUrl) ;
			}
			
		}catch(Exception e){
			logger.error(">>>>>>>>>getRequestFileJsonAndPackage error",e);
		}finally {
			if (clientFactoryPartnerFile != null) {
				try {
					clientFactoryPartnerFile.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>getRequestFileJsonAndPackage clientFactoryPartnerFile destroy thrift error：",e);
				}
			}
		}
		return returnUrl;
	}
	
	
	/**
	 * 打包集合文件，并生成打包路径
	 * @param fileList
	 * @param fileList
	 * @param projectId
	 * @param serverUrl
	 * @return
	 * @throws Exception
	 */
	public String getPackageFileUrl(List<ProjectPartnerFile> fileList,int projectId ,String serverUrl) throws Exception{
		
		String returnUrl = "";
		
		//时间格式
		String nowTimeStr = DateUtils.dateToFormatString(new Date(), "yyyyMMddHHmmss");
		
		String zipUrl= null;		//相对路径
		String zipLocalUrl= null;	//服务绝对路径
		String zipFileName = null;	//文件名
		
		String [] fileUrls = null;
		String temp_uploadFilePath =getUploadFilePath();
		File temp_file = null;
		byte[] buffer = new byte[1024];   
		
		
		zipUrl = temp_uploadFilePath+"/partner/"+projectId+"/dr/";
		zipLocalUrl = CommonUtil.getRootPath() + zipUrl;
		zipFileName ="all_"+nowTimeStr+".zip";
		//fileUrls = entry.getValue().split(",");
		
		
		//创建目录
		File zipFilePath = new File(zipLocalUrl);
		if (!zipFilePath.exists()) {
			zipFilePath.mkdirs();
		}
		//创建文件
		File zipFile = new File(zipLocalUrl,zipFileName);
		if (!zipFile.exists()) {
			zipFile.createNewFile();
		}
		//全路径
		zipLocalUrl += "/"+zipFileName;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipLocalUrl)); 
		 
		boolean isHasZip = false;		//是否己打包
		int fileIndex = 1;
		for (ProjectPartnerFile parFile : fileList) {
			temp_file = new File(CommonUtil.getRootPath()+parFile.getFileUrl());
			if(temp_file.exists()){
				FileInputStream fis = new FileInputStream(temp_file);  
				out.putNextEntry(new ZipEntry(parFile.getAccessoryType()+"_"+fileIndex+"."+FileUtil.getFileType(temp_file.getName())));   
				int len;
				// 读入需要下载的文件的内容，打包到zip文件
				while ((len = fis.read(buffer)) > 0) {
					out.write(buffer, 0, len);
				}
				out.closeEntry();
				fis.close();
				isHasZip = true;
				fileIndex++;
			}
		}
		out.close();
		if(isHasZip){
			returnUrl = serverUrl+"/"+zipUrl+zipFileName;
			logger.info(">>>>>>getPackageFileUrl package zip url:"+serverUrl+"/"+zipUrl+zipFileName);
		}
		return returnUrl;
	}

	/**
	 * 回调通知
	 */
	public JSONObject notify(JSONObject paramJson, HttpServletRequest request) throws Exception {
		try{
			 
			
		}catch(Exception e){
			logger.error(">>>>>>notify error",e);
		}
		return returnResult(null, "操作失败", 1, 1,null);
	}
	
	
	
	/**
	 * 返回结果
	 * @param loanId	审批编号
	 * @param msg		提示信息
	 * @param status	请求状态 0：成功     1：失败
	 * @param err_code	业务状态 0：成功   	 非0： 失败
	 * @param keyValMap	自定义键值对
	 * @return
	 */
	protected JSONObject returnResult(String loan_id ,String msg,int status,int err_code,Map<String,Object> keyValMap) throws Exception{
		
		JSONObject contentJson = new JSONObject();
		
		return contentJson;
	}

	/**
	 * 生成token ,2小时过期，重新生成，缓存于上下文中 
	 * 	{"code":20000,
	 * 		"data":{"access_token":"ZTUyN2U2ZWRiMTZkNGJiOGE3ZjgzYTc4ZjYyYTU3OTg",
	 * 				"expires_in":7200,"token_type":"Bearer"},
	 *	"message":"OK"}
	 * @param request
	 * @return
	 * @throws Exception
	 */
	protected String getAccessToken(HttpServletRequest request) throws Exception {
		
		String accessToken = "";
		JSONObject tokenJson = null;
 
		 
		String tokenUrl = DR_PARTNER_API_BASE_URL+DR_PARTNER_API_TOKEN_URL;
		String jwt = JWTGeneratorUtil.getJWT2(DR_PARTNER_CLIENTID, tokenUrl, DR_PARTNER_PRIVATEKEY);
		logger.info(">>>>>>getOAuthToken jwt:"+jwt);
		
		//请求获取token
		List<HttpRequestParam> paramList = new ArrayList<HttpRequestParam>();
		HttpRequestParam assertionParam = new HttpRequestParam("assertion",jwt);
		paramList.add(assertionParam);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.openConnection(60000,60000);
		String result = httpUtils.executeHttpPost(tokenUrl, paramList, "UTF-8");
		httpUtils.closeConnection();
		
		logger.info(">>>>>>getOAuthToken http result:"+result);
		
		if(!StringUtil.isBlank(result)){
			tokenJson = JSONObject.parseObject(result);
		}
		
		if(tokenJson != null && "20000".equals(tokenJson.getString("code"))){
			accessToken =tokenJson.getJSONObject("data").getString("token_type")+" "+ tokenJson.getJSONObject("data").getString("access_token");
		}
		return accessToken;
	}
	
	
	
	public static void main(String[] args) {
		
		int aaa= 0;
		
		System.out.println((int)Math.ceil(((double)aaa)/30));
		
		
	}
	
	
	

}
