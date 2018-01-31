package com.xlkfinance.bms.web.api.partnerapi.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.MoneyFormatUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoan;
import com.xlkfinance.bms.rpc.beforeloan.BizOriginalLoanService;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctService;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBank;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBankService;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
import com.xlkfinance.bms.rpc.partner.CusCreditInfoService;
import com.xlkfinance.bms.rpc.partner.PartnerApprovalRecord;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.rpc.system.SysAreaInfo;
import com.xlkfinance.bms.rpc.system.SysAreaInfoService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.web.api.partnerapi.daju.constant.DaJuConstant;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.Header;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.Message;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.RequestParams;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.ResponseParams;
import com.xlkfinance.bms.web.api.partnerapi.daju.util.DaJuUtil;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.FTPUtil;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.IDCardUtil;
import com.xlkfinance.bms.web.util.PartnerProjectUtil;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.SFTPUtil;


/**
 * 大桔接口实现类 （南粤银行）
 * @author chenzhuzhen
 * @date 2017年2月7日 下午6:07:37
 */
@Service("daJuPartnerApiImpl")
public class DaJuPartnerApiImpl extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(DaJuPartnerApiImpl.class);
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	
	public static void main(String[] args) throws Exception {
		//creditReport(1);
		
		
//		DaJuPartnerApiImpl daju  = new DaJuPartnerApiImpl();
//		daju.readLoanFile(null);
		
		String tempStr = "";
		
		String [] ttt= tempStr.split("\\|");
		
		
		System.out.println(ttt.length);
		
		
		
		
	}
	
	/**
	 * 查询征信报告
	 * @param acctId	客户账号
	 * @param uuid	 日记uuid
	 * @return
	 * @throws Exception
	 */
	public ResponseParams creditReport(String uuid , Map<String,Object> bodyMap) throws Exception {
		try{
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.NYBCREDIT_QUERY.getCode());
			
			RequestParams requestParams = new RequestParams();
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			//请求
			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			return responseParams;
		}catch(Exception e){
			logger.error("uuid:"+uuid+",creditReport error",e);
			throw e;
		}
	}
	
	
	/**
	 * 资金申请
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public JSONObject apply(String uuid ,ProjectPartnerDto projectPartnerDto ,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		BaseClientFactory clientFactoryPartner = null;
		BaseClientFactory clientFactoryCus = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientFactoryCusAcct = null;
		BaseClientFactory clientFactoryPartnerFile = null;
		BaseClientFactory clientFactoryCredit = null;
		BaseClientFactory clientEstateFactory = null;
		BaseClientFactory clientFactoryOriginalLoan = null;	//原贷款银行
		BaseClientFactory clientFactoryArea = null;
		BaseClientFactory clientFactoryProject = null;
		BaseClientFactory clientFactoryCusBank = null;
		
		try{
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//字典service
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			
			//关系人
			clientFactoryCusAcct = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusAcctService");
			CusAcctService.Client clientCusAcct =(CusAcctService.Client) clientFactoryCusAcct.getClient();
			
			//客户service
			clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientCus =(CusPerService.Client) clientFactoryCus.getClient();
			
			//资金合作项目文件
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			//客户征信
			clientFactoryCredit = getFactory(BusinessModule.MODUEL_PARTNER, "CusCreditInfoService");;
			CusCreditInfoService.Client clientCredit = (CusCreditInfoService.Client) clientFactoryCredit.getClient();
			
			//多物业信息
			clientEstateFactory = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizProjectEstateService");
			BizProjectEstateService.Client clientEstate = (BizProjectEstateService.Client) clientEstateFactory.getClient();
			
			//原贷款银行
			clientFactoryOriginalLoan = getFactory(BusinessModule.MODUEL_BEFORELOAN, "BizOriginalLoanService");
			BizOriginalLoanService.Client clientOriginalLoan = (BizOriginalLoanService.Client) clientFactoryOriginalLoan.getClient();
			
			//地区
			clientFactoryArea = getFactory(BusinessModule.MODUEL_SYSTEM, "SysAreaInfoService");
			SysAreaInfoService.Client clientArea =(SysAreaInfoService.Client) clientFactoryArea.getClient();
			
			//项目
			clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			
			//银行开户
			clientFactoryCusBank = getFactory(BusinessModule.MODUEL_PARTNER, "BizPartnerCustomerBankService");
			BizPartnerCustomerBankService.Client clientCusBank = (BizPartnerCustomerBankService.Client) clientFactoryCusBank.getClient();
			
			//查询项目，并懒加懒相关信息
/*			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setProjectId(projectPartnerDto.getProjectId());
			queryPartner.setPartnerNo(projectPartnerDto.getPartnerNo());
			ProjectPartnerDto tempProjectPartnerDto = clientPartner.findProjectPartnerAndLazy(queryPartner);*/
			
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
			
			//查询是否己经开户过了
			BizPartnerCustomerBank queryCustomerBank = new BizPartnerCustomerBank();
			queryCustomerBank.setAcctId(tempProject.getAcctId());
			queryCustomerBank.setStatus(Constants.COMM_YES);	//成功
			queryCustomerBank.setPage(-1);
			List<BizPartnerCustomerBank> tempCusBankList =  clientCusBank.selectList(queryCustomerBank);
			if(CollectionUtils.isEmpty(tempCusBankList)){
				return returnJson(0, "该客户未开户绑卡，请先开户绑卡", 1, "",null);
			}
			
			
			/**是否是交易       true： 交易    fasle 非交易*/
			boolean   isTrading  = false;
			if("交易".equals(projectPartnerDto.getBusinessCategoryStr()) || 
					(!StringUtil.isBlank(tempProject.getBusinessTypeText()) 
							&& tempProject.getBusinessTypeText().startsWith("交易"))){
				isTrading = true;
			}

			
			//业务参数
			Map<String,Object> bodyMap = new HashMap<String,Object>();
			bodyMap.put("serial_no", tempDto.getPartnerOrderCode());		//场景资金申请编号
			bodyMap.put("full_name", tempDto.getUserName());		//客户姓名
			
			//证件类型
			String cert_type="";
			if(!StringUtil.isBlank(tempDto.getCertType())){
				String	certType = clientSys.getSysLookupValByName(Integer.parseInt(tempDto.getCertType()));
				if("身份证".equals(certType)){
					cert_type=DaJuConstant.CertType.C_06000.getCode();
				}else if("护照".equals(certType)){
					cert_type=DaJuConstant.CertType.C_06001.getCode();
				}else if("驾照".equals(certType)){
					cert_type=DaJuConstant.CertType.C_06002.getCode();
				}else{
					return returnJson(0, "客户证件类型不支持", 1, "",null);
				}
				
			}
			bodyMap.put("cert_type", cert_type);					//证件类型
			bodyMap.put("cert_id", tempDto.getCardNo());			//证件号码
			bodyMap.put("birthday", IDCardUtil.parseBirthday(tempDto.getCardNo()).replaceAll("-", "/"));		//出生日期
			
			//性别
			String	sexLookUpName = clientSys.getSysLookupValByName(tempDto.getSex());
			String gender = DaJuConstant.Gender.C_UNKNOW.getCode();
			if("女".equals(sexLookUpName) ){
				gender = DaJuConstant.Gender.C_FEMALE.getCode();
			}else if("男".equals(sexLookUpName)){
				gender = DaJuConstant.Gender.C_MALE.getCode();
			}
			bodyMap.put("gender", gender);		//性别	
			
			//婚姻状况
			String	marriageLookUpName = clientSys.getSysLookupValByName(tempCusPerson.getMarrStatus());
			String marriage = DaJuConstant.Marriage.C_08001.getCode();
			if("已婚".equals(marriageLookUpName)){
				marriage = DaJuConstant.Marriage.C_08001.getCode();
			}else if("未婚".equals(marriageLookUpName)){
				marriage = DaJuConstant.Marriage.C_08000.getCode();
			}else if("离异".equals(marriageLookUpName)){
				marriage = DaJuConstant.Marriage.C_08002.getCode();
			}else if("丧偶".equals(marriageLookUpName)){
				marriage = DaJuConstant.Marriage.C_08003.getCode();
			}
			bodyMap.put("marriage", marriage);		//婚姻状况
			
			
			//婚姻状况
			String	educationLookUpName = clientSys.getSysLookupValByName(tempCusPerson.getEducation());
			String education = education = DaJuConstant.Education.C_09002.getCode();
			if("博士后研究生".equals(educationLookUpName) || "博士研究生".equals(educationLookUpName) || "研究生".equals(educationLookUpName)){
				education = DaJuConstant.Education.C_09000.getCode();
			}else if("本科".equals(educationLookUpName) || "大专".equals(educationLookUpName)){
				education = DaJuConstant.Education.C_09002.getCode();
			}else if("高中".equals(educationLookUpName) || "初中".equals(educationLookUpName) || "小学".equals(educationLookUpName)){
				education = DaJuConstant.Education.C_09004.getCode();
			}
			bodyMap.put("education", education);  //教育程度
			bodyMap.put("mobile_no", tempDto.getPhone());		//手机
			bodyMap.put("district_code", tempCusPerson.getLiveDistrictCode());	//家庭所在行政区号代码
			bodyMap.put("family_address", tempCusPerson.getLiveAddr());	//客户家庭地址
			bodyMap.put("family_zip", tempCusPerson.getLiveCode());		//客户家庭邮编	 
			bodyMap.put("corporation", tempCusPerson.getWorkUnit());	//单位名称
			
			//单位性质
			String	corp_natureLookUpName = clientSys.getSysLookupValByName(tempCusPerson.getUnitNature());
			String corp_nature = DaJuConstant.CorpNature.C_11006.getCode();
			if("私营企业".equals(corp_natureLookUpName) || "合资企业".equals(corp_natureLookUpName) || "外资企业".equals(corp_natureLookUpName) ){
				corp_nature = DaJuConstant.CorpNature.C_11001.getCode();
			}else if("国有控股企业".equals(corp_natureLookUpName)){
				corp_nature = DaJuConstant.CorpNature.C_11003.getCode();
			}else if("国有企业".equals(corp_natureLookUpName)){
				corp_nature = DaJuConstant.CorpNature.C_11000.getCode();
			}else if("个体工商户".equals(corp_natureLookUpName)){
				corp_nature = DaJuConstant.CorpNature.C_11002.getCode();
			}else if("其它".equals(corp_natureLookUpName)){
				corp_nature = DaJuConstant.CorpNature.C_11006.getCode();
			} 
			
			bodyMap.put("corp_nature", corp_nature);		//单位性质
			
			//行业类型
			String	corp_industryLookUpName = clientSys.getSysLookupValByName(tempCusPerson.getUnitNature());
			String corp_industry = DaJuConstant.CorpIndustry.C_12015.getCode();
			if("农牧业".equals(corp_industryLookUpName)  ){
				corp_industry = DaJuConstant.CorpIndustry.C_12000.getCode();
			}else if("采矿业".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12001.getCode();
			}else if("制造业".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12002.getCode();
			}else if("生产和供应业".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12003.getCode();
			}else if("建筑业".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12004.getCode();
			}else if("服务业".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12014.getCode();
			}else if("教育卫生社会工作".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12015.getCode();
			}else if("社会组织".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12016.getCode();
			}else if("国际组织".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12019.getCode();
			}else if("其它".equals(corp_industryLookUpName)){
				corp_industry = DaJuConstant.CorpIndustry.C_12015.getCode();
			} 
			bodyMap.put("corp_industry", corp_industry);	//行业类型
			bodyMap.put("occup_type", DaJuConstant.OccupType.C_13008.getCode());		//职业类型    //未知
			
			//行政类型    -职称
			String	admin_typeLookUpName = clientSys.getSysLookupValByName(tempCusPerson.getUnitNature());
			String admin_type = DaJuConstant.AdminType.C_14003.getCode();
			if("正高级".equals(admin_typeLookUpName) || "副高级".equals(admin_typeLookUpName)){
				admin_type = DaJuConstant.AdminType.C_14000.getCode();
			}else if("中级".equals(admin_typeLookUpName) ){
				admin_type = DaJuConstant.AdminType.C_14001.getCode();
			}else if("助理级".equals(admin_typeLookUpName) ||  "技术员".equals(admin_typeLookUpName)){
				admin_type = DaJuConstant.AdminType.C_14002.getCode();
			}else if("其它".equals(admin_typeLookUpName) ){
				admin_type = DaJuConstant.AdminType.C_14003.getCode();
			}
			bodyMap.put("admin_type", admin_type);		//行政类型
			bodyMap.put("job_type", DaJuConstant.JobType.C_15003.getCode());		//职务类型   	默认普通员工
			bodyMap.put("job_nature", DaJuConstant.JobNature.C_39009.getCode());		//工作性质  	 默认  其他
			bodyMap.put("employee_type", DaJuConstant.EmployeeType.C_16000.getCode());	//雇佣类型  	默认标准受薪
			
			
			int employee_time = 1;
			if(!StringUtil.isBlank(tempCusPerson.getEntryTime())){
				//计算入职时间，现当前时间的年数
				Date entryTimeDate = DateUtils.stringToDate(tempCusPerson.getEntryTime(), "yyyy-MM-dd");
				int diffDay = DateUtils.dayDifference(entryTimeDate, new Date());
				employee_time = (int) Math.ceil(((double)diffDay /365 ));
				
				if(employee_time == 0 ){
					employee_time = 1 ;
				}
			}
			bodyMap.put("employee_time", employee_time);	//现单位工作年限
			bodyMap.put("month_income", tempCusPerson.getMonthIncome()*10000);		//月收入	（原先是万元为单位）
			bodyMap.put("salary_income", tempCusPerson.getMonthIncome()*10000);		//薪资收入
			
			//居住状况    -----------------------------------------------------查询个人居住信息
			String	resident_statLookUpName = clientSys.getSysLookupValByName(1);
			String resident_stat = DaJuConstant.ResidentType.C_18006.getCode();
			if("自有居住".equals(resident_statLookUpName) ){
				resident_stat = DaJuConstant.ResidentType.C_18000.getCode();
			}else if("住房按揭".equals(resident_statLookUpName) ){
				resident_stat = DaJuConstant.ResidentType.C_18001.getCode();
			}else if("租房".equals(resident_statLookUpName) ){
				resident_stat = DaJuConstant.ResidentType.C_18004.getCode();
			}else if("其它".equals(resident_statLookUpName) ){
				resident_stat = DaJuConstant.ResidentType.C_18006.getCode();
			}
			bodyMap.put("resident_stat", resident_stat);		//居住状况
			bodyMap.put("local_house", DaJuConstant.LocalHouse.C_33000.getCode());			//家庭本地房产	默认  有
			
			//户口所在地   默认本地
			String region_code = DaJuConstant.RegionCode.C_33000.getCode();
			//判断居住地址是否和申请城市一致
			if(!tempDto.getCityCode().equals(tempCusPerson.getLiveCityCode())){
				region_code = DaJuConstant.RegionCode.C_33001.getCode();
			}
			
			bodyMap.put("region_code", region_code);			//户口所在地
			bodyMap.put("cert_address", tempCusPerson.getCertAddr());  //身份证发证机关所在地 --添加字段
			
			if(StringUtil.isBlank(tempCusPerson.getCertAddr())){
				return returnJson(0, "客户附加信息》身份证发证机关所在地不能为空", 1, "",null);
			}
			
			
			//密切联系人=========
			CusPerson queryCusPerson = new CusPerson();
			CusAcct queryCusAcct = new CusAcct();
			queryCusAcct.setPid(tempDto.getAcctId());
			queryCusPerson.setCusAcct(queryCusAcct);
			queryCusPerson.setRelationType(5);
			queryCusPerson.setPage(1);
			queryCusPerson.setRows(100);
			List<GridViewDTO> contactList = clientCusAcct.getCusPersons(queryCusPerson);
			String close_contacts = "";
			String close_relationStr = "";  //关系字符串
			String close_relation = "";		//关系code
			String close_mobile = "";
			if(!CollectionUtils.isEmpty(contactList)){
				close_contacts = contactList.get(0).getValue1();
				close_relationStr = contactList.get(0).getValue12();
				close_mobile = contactList.get(0).getValue5();
			}else if(!StringUtil.isBlank(tempProject.getContactsPhone())){
				//取业务联系人
				close_contacts = tempProject.getBusinessContacts();
				close_relationStr = DaJuConstant.Relation.C_25008.getCode();
				close_mobile = tempProject.getContactsPhone();
			}else{
				//取经办人
				close_contacts = tempProject.getManagers();
				close_relationStr = DaJuConstant.Relation.C_25008.getCode();
				close_mobile = tempProject.getManagersPhone();
			}
			if("共同借款人".equals(close_relationStr) || "产权共有人".equals(close_relationStr)){
				close_relation = DaJuConstant.Relation.C_25010.getCode();
			}else if("亲属".equals(close_relationStr)){
				close_relation = DaJuConstant.Relation.C_25004.getCode();
			}else if("朋友".equals(close_relationStr)){
				close_relation = DaJuConstant.Relation.C_25009.getCode();
			}else if("上下级".equals(close_relationStr)  || "同事".equals(close_relationStr)){
				close_relation = DaJuConstant.Relation.C_25008.getCode();
			}else{
				close_relation = DaJuConstant.Relation.C_25004.getCode();
			}
			//通过字典配置读取
			bodyMap.put("close_contacts", close_contacts);		//密切联系人姓名
			bodyMap.put("close_relation", close_relation);		//密切联系人关系
			bodyMap.put("close_mobile", close_mobile);		//密切联系人手机
			
			String loan_apply_type = "";
			
			//交易
			if(isTrading){
				loan_apply_type = DaJuConstant.BusinessType.C_51000.getCode();
			}else{
				//非交易
				loan_apply_type = DaJuConstant.BusinessType.C_51001.getCode();
			}
			
			bodyMap.put("loan_apply_type",loan_apply_type);		//贷款品种
			
			bodyMap.put("loan_apply_amt", tempDto.getApplyMoney());		//贷款申请金额
			bodyMap.put("purpose_type", DaJuConstant.PurposeType.C_19016.getCode());		//贷款用途
			bodyMap.put("purpose_detail", DaJuConstant.PurposeDetail.C_20025.getCode());		//贷款详细用途
			bodyMap.put("cust_acct_no", tempDto.getPaymentAcctNo());		//借款人银行卡账号
			bodyMap.put("cust_bank_mobile", tempDto.getPaymentBankPhone());	//借款人银行卡预留手机号
			bodyMap.put("loan_trade_amt", tempDto.getApplyMoney());		//贷款交易金额
			bodyMap.put("pay_type", DaJuConstant.PayType.C_21001.getCode() );			//支付方式    他行受托支付     
			
			bodyMap.put("period_type", DaJuConstant.PeriodType.C_22001.getCode());			//期限类型
			bodyMap.put("period_duration", tempDto.getLoanPeriodLimit());		//期限值				默认三个月
			bodyMap.put("period", tempDto.getLoanPeriodLimit());				//期数
			
			//字典service   客户设备信息
			SysLookupVal  cust_ipVal =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_PC_SERVER,Constants.SYS_LOOKUP_PARTNER_PC_SERVER_IP);
			SysLookupVal  cust_macVal =clientSys.getSysLookupValByChildType(Constants.SYS_LOOKUP_PARTNER_PC_SERVER,Constants.SYS_LOOKUP_PARTNER_PC_SERVER_MAC);
			if(cust_ipVal == null || StringUtil.isBlank(cust_ipVal.getLookupDesc())
					|| cust_macVal == null || StringUtil.isBlank(cust_macVal.getLookupDesc()) ){
				return returnJson(0, "未配置服务器客户mac设备信息，请联系管理员", 1, "",null);
				
			}
			bodyMap.put("cust_ip",cust_ipVal.getLookupDesc() );				//客户设备ip地址
			bodyMap.put("cust_mac", cust_macVal.getLookupDesc());			//客户设备mac地址
			
			
			//赎楼 默认为非自雇人士     以下为默认值   
			bodyMap.put("corp_stat", DaJuConstant.CorpStat.C_28000.getCode());	//单位工商查询结果
			bodyMap.put("social_ins", DaJuConstant.SocialIns.C_29000.getCode());//当地社保
			bodyMap.put("social_ins_stat", DaJuConstant.SocialInsStat.C_30002.getCode());		//社保记录状态
			bodyMap.put("provident_fund", DaJuConstant.ProvidentFund.C_31002.getCode());			//公积金记录状态
			
			
			bodyMap.put("graduate_year", tempDto.getIsCreditLoan());		//客户在他行有无未结清的信用/保证贷款	（1：有，2：无）
			
			//查询客户征信====================================================================
			CusCreditInfo queryCredit = new CusCreditInfo();
			queryCredit.setPage(1);
			queryCredit.setRows(1);
			queryCredit.setAcctId(tempDto.getAcctId());
			queryCredit.setPartnerNo(tempDto.getPartnerNo());
			List<CusCreditInfo> tempCusCreditList =  clientCredit.selectCusCreditList(queryCredit);
			if(CollectionUtils.isEmpty(tempCusCreditList)){
				return returnJson(0, "未查询客户征信", 1, "",null);
			}
			bodyMap.put("credit_deal_id", tempCusCreditList.get(0).getCreditDealId());		//平台返回的征信交易编号
			
			JSONObject tempJson = null; //临时共用对象
			
			//文件信息================================================
			JSONArray file_infosArray = new JSONArray();	//文件信息列表
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setProjectId(tempDto.getProjectId());
			query.setPartnerNo(tempDto.getPartnerNo());
			query.setPartnerId(tempDto.getPid());
			query.setPidList(CommonUtil.getSepStrtoList(projectPartnerDto.getRequestFiles(), "Integer", ","));
			List<ProjectPartnerFile> fileList = clientPartnerFile.findAllProjectPartnerFile(query);
			Map<String,Integer> indexMap = new HashMap<String,Integer>();
			if(!CollectionUtils.isEmpty(fileList)){
				for (ProjectPartnerFile indexObj : fileList) {
					if(!StringUtil.isBlank(indexObj.getThirdFileUrl())){
						tempJson = new JSONObject(); 
						if(indexMap.get(indexObj.getAccessoryChildType()) != null){
							indexMap.put(indexObj.getAccessoryChildType(),indexMap.get(indexObj.getAccessoryChildType())+1);
						}else{
							indexMap.put(indexObj.getAccessoryChildType(),1);
						}
						tempJson.put("file_seq", indexMap.get(indexObj.getAccessoryChildType()));
						tempJson.put("file_type", indexObj.getAccessoryChildType());
						tempJson.put("file_path", indexObj.getThirdFileUrl());
						file_infosArray.add(tempJson);
					}
				}
			}
			bodyMap.put("file_infos", file_infosArray);
			//赎楼信息================================================
			JSONArray tran_infosArray = new JSONArray();	 //赎楼信息列表
			List<BizProjectEstate> projectEstateList =  clientEstate.getAllByProjectId(tempDto.getProjectId());
			BizOriginalLoan queryOriginalLoan = new BizOriginalLoan();
			queryOriginalLoan.setProjectId(tempDto.getProjectId());
			queryOriginalLoan.setStatus(1);//有效的
			List<BizOriginalLoan> tempOriginalLoanList = clientOriginalLoan.getAllByCondition(queryOriginalLoan);
			if(CollectionUtils.isEmpty(tempOriginalLoanList) || tempOriginalLoanList.size() > 1){
				return returnJson(0, "原贷款银行只支持一个", 1, "",null);
			}
			double assessed_net_worth = 0 ;			//评估净值  
			double transaction_price = 0 ;	//买卖成交价
			double downpayment = 0 ;		//首期款金额
			double purchase_deposit = 0 ;	//购房定金
			double purchase_balance = 0 ;	//购房余款
			for (BizProjectEstate indexObj : projectEstateList) {
				assessed_net_worth += indexObj.getEvaluationNet();
				transaction_price += indexObj.getTranasctionMoney();
				if(indexObj.getEvaluationNet() == 0 ){
					return returnJson(0, "物业信息>评估净值不能为空", 1, "",null);
				}
				downpayment +=indexObj.getDownPayment();
				purchase_deposit +=indexObj.getPurchaseDeposit();
				purchase_balance += indexObj.getPurchaseBalance();
			}
			
			//借款金额不能超过评估净值的70%
			if(tempDto.getApplyMoney() / assessed_net_worth > 0.7 ){
				return returnJson(0, "借款金额不能超过总评估净值的70%", 1, "",null);
			}
			
			//贷款银行信息
			BizOriginalLoan tempOriginalLoan = tempOriginalLoanList.get(0);
			
			//不超过原贷款欠总金额的 105%  
			if(tempDto.getApplyMoney() / tempOriginalLoan.getOldOwedAmount()> 1.05){
				return returnJson(0, "借款金额不能超过原贷款欠款总金额的105%", 1, "",null);
			}
			
			
			tempJson = new JSONObject();
			
			//评估净值
			tempJson.put("assessed_net_worth", MoneyFormatUtil.format2(assessed_net_worth));
			//贷款地区
			SysAreaInfo cityAreaInfo=  clientArea.getSysAreaInfoByCode(tempDto.getCityCode());
			tempJson.put("loan_area", StringUtil.isBlank(cityAreaInfo.getAreaName()) ? "" : cityAreaInfo.getAreaName());
			
			//交易类必填
			if(isTrading){
				//买卖成交价
				tempJson.put("transaction_price", MoneyFormatUtil.format2(transaction_price));
				//首期款金额
				tempJson.put("downpayment",downpayment);
				//购房定金
				tempJson.put("purchase_deposit",purchase_deposit);
				//购房余款
				tempJson.put("purchase_balance",purchase_balance);
				//资金监管金额
				tempJson.put("supervision_amount", tempForeclosure.getFundsMoney());	
			}
			
			if(StringUtil.isBlank(tempOriginalLoan.getOldLoanAccount())){
				return returnJson(0, "原贷款银行供楼帐号不能为空", 1, "",null);
			}
			
			//原贷款银行
			tempJson.put("original_loan_bank", tempOriginalLoan.getOldLoanBankStr());
			//原贷款供楼账号
			tempJson.put("original_loan_account", tempOriginalLoan.getOldLoanAccount());
			

			
			tempJson.put("original_loan_amount", tempOriginalLoan.getOldLoanMoney());	//原贷款金额
			tempJson.put("remaining_principal", tempOriginalLoan.getOldOwedAmount());	//剩余本息


			//新贷款银行
			tempJson.put("new_loan_bank", StringUtil.isBlank(tempForeclosure.getNewBankStr())? "无" :  tempForeclosure.getNewBankStr().trim());
			
			if(!(StringUtil.isBlank(tempForeclosure.getNewBankStr()) || "无".equals(tempForeclosure.getNewBankStr()))){
				if(StringUtil.isBlank(tempForeclosure.getNewReceBank())){
					return returnJson(0, "赎楼信息>新贷款收款银行不能为空", 1, "",null);
				}
				//新贷款申请人
				tempJson.put("new_loan_applicant", tempForeclosure.getNewRecePerson());	
				//新贷款金额
				tempJson.put("new_loan_amount", tempForeclosure.getNewLoanMoney());	
			}else{
				//新贷款申请人
				tempJson.put("new_loan_applicant", "无");	
				//新贷款金额
				tempJson.put("new_loan_amount", 0);	 
			}
			
			tran_infosArray.add(tempJson);
			bodyMap.put("tran_infos", tran_infosArray);
			
			//转帐信息================================================
			JSONArray transfer_infosArray = new JSONArray();	 //转帐信息列表
			
			tempJson = new JSONObject();
			tempJson.put("transfer_sum", tempDto.getApplyMoney());			//转账金额
			tempJson.put("accountIn_name", tempDto.getPaymentAcctName());		//收款人账户名称
			tempJson.put("accountIn_no", tempDto.getPaymentAcctNo());			//收款人账户的帐号
			tempJson.put("accountIn_bankno", tempDto.getPaymentBankLineNo());	//收款人账户的联行编号
			
			String accountIn_flag = DaJuConstant.AccountNybFlag.NO.getCode();
			if(tempDto.getPaymentAcctName().contains("南粤")){
				accountIn_flag = DaJuConstant.AccountNybFlag.YES.getCode();
			}
			tempJson.put("accountIn_flag", accountIn_flag);	//收款账户是否南粤银行
			transfer_infosArray.add(tempJson);
			
			bodyMap.put("transfer_infos", transfer_infosArray);
			//备注信息================================================
			JSONArray remarks_infosArray = new JSONArray();	 //备注信息
			
			//房产证信息
			for (BizProjectEstate indexObj : projectEstateList) {
				tempJson = new JSONObject();
				tempJson.put("remark1", DaJuConstant.NybRemarkFlag.C_46000.getCode());
				tempJson.put("remark2", indexObj.getHousePropertyCard());
				tempJson.put("remark3", "");
				tempJson.put("remark4", indexObj.getHouseName());
				
				//验证是否为空
				if(StringUtil.isBlank(indexObj.getPropertyRatio())){
					return returnJson(0, "物业信息>权属人占比不能为空", 1, "",null);
				}
				tempJson.put("remark5", indexObj.getPropertyRatio().replaceAll(",", ";"));
				remarks_infosArray.add(tempJson);
			}
			
			//新贷款收款信息
			//判断是否为空或者无
			if(!(StringUtil.isBlank(tempForeclosure.getNewBankStr()) || "无".equals(tempForeclosure.getNewBankStr()))){
				tempJson = new JSONObject();
				tempJson.put("remark1", DaJuConstant.NybRemarkFlag.C_46001.getCode());
				tempJson.put("remark2", tempForeclosure.getPaymentName());
				tempJson.put("remark3", tempForeclosure.getNewLoanMoney());
				tempJson.put("remark4", tempForeclosure.getPaymentAccount());
				tempJson.put("remark5", tempForeclosure.getNewReceBank());
				remarks_infosArray.add(tempJson);
			}
			
			bodyMap.put("remarks_infos", remarks_infosArray);
			
			//封装请求参数=============================================
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.LEND_APPLY.getCode());
			
			//设置请求公共参数
			RequestParams requestParams = new RequestParams();
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			logger.info("uuid:"+uuid+",apply请求参数");
			
			
			if("true".equals(IS_PARTNER_API_DEBUG)){
				
				logger.info("uuid:"+uuid+",apply请求参数requestParams:"+JSONObject.toJSONString(requestParams));
				
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
			
			
			
			//发送请求
			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			logger.info("uuid:"+uuid+",响应参数:"+JSONObject.toJSONString(responseParams));
			
			//======================测试=====================================
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", 11111);
				dataJson.put("err_code", 0);
				resultJson.put("data", dataJson);
				return resultJson;
			}*/
			if(responseParams == null){
				return null;
			}
			resultJson = new JSONObject();
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				return returnJson(0, responseParams.getMessage().getDesc(), 1, "",null);
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))){
				return returnJson(0, bodyJson.getString("return_msg"), 1, "",null);
			}
			//成功
			return returnJson(0, responseParams.getMessage().getDesc(), 0, bodyJson.getString("deal_id"),null);
 
		}catch(Exception e){
			logger.error("uuid:"+uuid+",apply error",e);
			return null;
		}finally {
			destroyFactory(clientFactoryPartner);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
			destroyFactory(clientFactoryCusAcct);
			destroyFactory(clientFactoryPartnerFile);
			destroyFactory(clientFactoryCredit);
			destroyFactory(clientEstateFactory);
			destroyFactory(clientFactoryOriginalLoan);
			destroyFactory(clientFactoryArea);
			destroyFactory(clientFactoryProject);
		}
	}
	
	
	/**
	 * 资金申请处理结果回调
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	public ResponseParams applyNotify(String uuid ,RequestParams requestParams) throws Exception {
		
		BaseClientFactory factoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
		try{
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) factoryPartner.getClient();
			JSONObject bodyJson = DaJuUtil.decryptRequestBody(uuid, requestParams);
			requestParams.setBody(bodyJson);
			
			logger.info("uuid:"+uuid+",applyNotify requestParams:"+JSONObject.toJSONString(requestParams));
			
			//资金申请状态
			String lend_apply_stat = bodyJson.getString("lend_apply_stat");
			//平台交易编号
			String apply_deal_id = bodyJson.getString("apply_deal_id");
			//返回的消息提示
			String return_msg = bodyJson.getString("return_msg");
			
			if(StringUtil.isBlank(lend_apply_stat) ||StringUtil.isBlank(apply_deal_id) ){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "请求参数为空"));
			}
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setLoanId(apply_deal_id); 
			queryPartner.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			queryPartner.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner.getPid() == 0  ){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "该交易数据不存在"));
			}
			
			//判断该状态是否可以操作
			int  approvalStatus =  tempPartner.getApprovalStatus(); //审批状态
			int  requestStatus = tempPartner.getRequestStatus();	//申请状态
			
			if(approvalStatus == PartnerConstant.ApprovalStatus.APPROVAL_PASS.getCode()){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "己审批通过不允许操作"));
			}else if(approvalStatus == PartnerConstant.ApprovalStatus.APPROVAL_NO_PASS.getCode()){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "审批己拒绝不允许操作"));
			}else if(requestStatus == PartnerConstant.RequestStatus.APPLY_NO_PASS.getCode()){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "申请己拒绝不允许操作"));
			}
			
			if(!(requestStatus == PartnerConstant.RequestStatus.APPLY_PASS.getCode() && 
					approvalStatus == PartnerConstant.ApprovalStatus.APPROVAL_ING.getCode())){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "该状态不允许操作"));
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
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "该状态不在回调范围内"));
			}
			
			//审批意见
			updatePartner.setApprovalComment(return_msg);
			updatePartner.setApprovalTime(nowTime.toString()); //设置审批时间
			
			//修改审核回调信息 
			int rows = clientPartner.updateProjectPartner(updatePartner);
			if(rows != 1){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "操作失败"));
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
	 
			return DaJuUtil.notifyResponse(uuid, requestParams, 
					new Message(DaJuConstant.Header.SUCCESS.getCode(), "操作成功"));
		}catch(Exception e){
			logger.error("uuid:"+uuid+",applyNotify error",e);
			return DaJuUtil.notifyResponse(uuid, requestParams, 
					new Message(DaJuConstant.Header.ERROR.getCode(), "操作失败，系统异常"));
		}finally {
			 destroyFactory(factoryPartner);
		}
	}

	
	/**
	 * 资金申请状态查询
	 * @param uuid	 日记uuid
	 * @param apply_deal_id	交易唯一标识符
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryApply(String uuid , ProjectPartnerDto projectPartnerDto) throws Exception {
		
		JSONObject resultJson = null;
		try{
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.QUERY_LEND_APPLY.getCode());
			
			RequestParams requestParams = new RequestParams();
			
			Map<String,Object> bodyMap = new HashMap<String,Object>();
			bodyMap.put("apply_deal_id", projectPartnerDto.getLoanId());
			
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			//请求
 			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			
			if(responseParams == null){
				return null;
			}
			resultJson = new JSONObject();
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				return returnJson(0, responseParams.getMessage().getDesc(), 1, "",null);
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))){
				return returnJson(0, bodyJson.getString("return_msg"), 1, "",null);
			}
			
			//测试-=--------------
/*			JSONObject bodyJson  = new JSONObject(); 
			
			bodyJson.put("return_code", "0000");
			bodyJson.put("return_msg", "申请成功");
			bodyJson.put("lend_apply_stat", "03001");
			bodyJson.put("apply_deal_id", projectPartnerDto.getLoanId());*/
			
			//测试 =======================
 
			//成功
			return returnJson(0, bodyJson.getString("return_msg"), 0, bodyJson.getString("apply_deal_id"),bodyJson);
			
		}catch(Exception e){
			logger.error("uuid:"+uuid+",queryApply error",e);
			throw e;
		}
	}
	
	
	/**
	 * 出帐通知（申请放款）
	 * @param uuid	 日记uuid
	 * @param apply_deal_id	交易唯一标识符
	 * @param status	出账条件落实情况
	 * @param remark	备注
	 * @param isLoan	是否放款  （true:放款   false:撤消）
	 * @return
	 * @throws Exception
	 */
	public JSONObject loanApply(String uuid ,ProjectPartnerDto projectPartnerDto,boolean isLoan ,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		try{
			
			Map<String,Object> bodyMap = new HashMap<String,Object>();
			
			bodyMap.put("apply_deal_id", projectPartnerDto.getLoanId());
			//放款
			String status = DaJuConstant.PayStatus.C_47000.getCode();
			//撤销未出账合同
			if(isLoan == false ){
				status = DaJuConstant.PayStatus.C_47001.getCode();
			}
			bodyMap.put("status", status);
			bodyMap.put("remark", projectPartnerDto.getLoanRemark());
			
			
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.ACCOUNT_NOTIFY.getCode());
			
			RequestParams requestParams = new RequestParams();
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			logger.info("uuid:"+uuid+",loanApply请求参数:"+JSONObject.toJSONString(requestParams));
			
			//请求
			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			
			logger.info("uuid:"+uuid+",loanApply响应参数:"+JSONObject.toJSONString(responseParams));
			
			//======================测试=====================================
/*			if(true){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", 1);
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", 11111);
				dataJson.put("err_code", 0);
				resultJson.put("data", dataJson);
				return resultJson;
			} */
			
			if(responseParams == null){
				return null;
			}
			resultJson = new JSONObject();
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", responseParams.getMessage().getDesc());
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 1);
				resultJson.put("data", dataJson);
				return resultJson;
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))){
				resultJson = new JSONObject();
				resultJson.put("status", 0);
				resultJson.put("msg", bodyJson.getString("return_msg"));
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 1);
				resultJson.put("data", dataJson);
				return resultJson;
			}
			//成功
			resultJson = new JSONObject();
			resultJson.put("status", 0);
			resultJson.put("msg", responseParams.getMessage().getDesc());
			JSONObject dataJson = new JSONObject();
			dataJson.put("err_code", 0);
			resultJson.put("data", dataJson);
			return resultJson;
			
		}catch(Exception e){
			logger.error("uuid:"+uuid+",loanApply error",e);
			throw e;
		}
	}
	
	
	/**
	 * 提前还款试算
	 * @param uuid	 日记uuid
	 * @param apply_deal_id	交易唯一标识符
	 * @param prepayment_date	为空，表示当天
	 * @return
	 * @throws Exception
	 */
	public JSONObject prepaymentAdvance(String uuid , ProjectPartner partner) throws Exception {
		try{
			Map<String,Object> bodyMap = new HashMap<String,Object>();
			
			bodyMap.put("apply_deal_id", partner.getLoanId());
			bodyMap.put("prepayment_date", partner.getRefundDate());
			
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.PREPAYMENT_ADVANCE.getCode());
			
			RequestParams requestParams = new RequestParams();
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			logger.info("uuid:"+uuid+",prepaymentAdvance请求参数:"+JSONObject.toJSONString(requestParams));
			//请求
			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			
			logger.info("uuid:"+uuid+",prepaymentAdvance响应参数:"+JSONObject.toJSONString(responseParams));
			
			 
			if(responseParams == null){
				return null;
			}
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				return returnJson(0, responseParams.getMessage().getDesc(), 1, "",null);
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))){
				return returnJson(0, bodyJson.getString("return_msg"), 1, "",null);
			} 
			
			//测试-=--------------
/*			JSONObject bodyJson  = new JSONObject(); 
			
			bodyJson.put("total_amt", 1);
			bodyJson.put("capital_amt", 2);
			bodyJson.put("interests_amt", 3);
			bodyJson.put("penalty_amt", 4);
			bodyJson.put("fine_amt", 5);
			bodyJson.put("compdinte_amt", 6);
			bodyJson.put("advance_stat", 7);*/
			
			//测试 =======================
			
			
			//成功
			return returnJson(0, bodyJson.getString("return_msg"), 0, bodyJson.getString("deal_id"),bodyJson);
		}catch(Exception e){
			logger.error("uuid:"+uuid+",prepaymentAdvance error",e);
			throw e;
		}
	}
	
	/**
	 * 提前还款
	 * @param uuid	 日记uuid
	 * @param apply_deal_id	交易唯一标识符
	 * @param total_amt	应还金额
	 * @param capital_amt	其中应还本金
	 * @param interests_amt	其中应还利息
	 * @param penalty_amt	违约金金额
	 * @param fine_amt 	罚息
	 * @param compdinte_amt	复利
	 * @return
	 * @throws Exception
	 */
	public JSONObject prepayment(String uuid , ProjectPartner partner) throws Exception {
		try{
			Map<String,Object> bodyMap = new HashMap<String,Object>();
			
			//资金申请id
			bodyMap.put("apply_deal_id", partner.getLoanId());
			//应还总金额
			bodyMap.put("total_amt", MoneyFormatUtil.format2(partner.getRefundTotalAmount()));
			//其中应还本金
			bodyMap.put("capital_amt", MoneyFormatUtil.format2(partner.getRefundLoanAmount()));
			//其中应还利息
			bodyMap.put("interests_amt", MoneyFormatUtil.format2(partner.getRefundXifee()));
			//违约金金额
			bodyMap.put("penalty_amt", MoneyFormatUtil.format2(partner.getRefundPenalty()));
			//罚息
			bodyMap.put("fine_amt", MoneyFormatUtil.format2(partner.getRefundFine()));
			//复利
			bodyMap.put("compdinte_amt", MoneyFormatUtil.format2(partner.getRefundCompdinte()));
			
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.PREPAYMENT.getCode());
			
			RequestParams requestParams = new RequestParams();
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			
			logger.info("uuid:"+uuid+",prepayment请求参数:"+JSONObject.toJSONString(requestParams));
			//请求
			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			
			logger.info("uuid:"+uuid+",prepayment响应参数:"+JSONObject.toJSONString(responseParams));
			 
			if(responseParams == null){
				return null;
			}
			
			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
				return returnJson(0, responseParams.getMessage().getDesc(), 1, "",null);
			}
			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))){
				return returnJson(0, bodyJson.getString("return_msg"), 1, "",null);
			}
			
			
			//测试-=--------------
/*			JSONObject bodyJson  = new JSONObject(); 
			
			bodyJson.put("total_amt", 1);
			bodyJson.put("capital_amt", 2);
			bodyJson.put("interests_amt", 3);
			bodyJson.put("penalty_amt", 4);
			bodyJson.put("fine_amt ", 5);
			bodyJson.put("compdinte_amt", 6);
			bodyJson.put("advance_stat", 7);
			bodyJson.put("payment_stat", "04000");*/
			
			//测试 =======================
			
			//成功
			return returnJson(0, bodyJson.getString("return_msg"), 0, bodyJson.getString("deal_id"),bodyJson);
			
 
		}catch(Exception e){
			logger.error("uuid:"+uuid+",prepayment error",e);
			throw e;
		}
	}
	
	
	
	/**
	 * 提前回款申请处理结果回调
	 * @param requestParams
	 * @return
	 * @throws Exception
	 */
	public ResponseParams prepaymentNotify(String uuid ,RequestParams requestParams) throws Exception {
		
		BaseClientFactory factoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
		try{
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) factoryPartner.getClient();
			JSONObject bodyJson = DaJuUtil.decryptRequestBody(uuid, requestParams);
			requestParams.setBody(bodyJson);
			
			logger.info("uuid:"+uuid+",prepaymentNotify requestParams:"+JSONObject.toJSONString(requestParams));
			
			//提前回款申请状态
			String payment_stat = bodyJson.getString("payment_stat");
			//平台交易编号
			String apply_deal_id = bodyJson.getString("apply_deal_id");
			//返回的消息提示
			String return_msg = bodyJson.getString("return_msg");
			//返回码
			String return_code = bodyJson.getString("return_code");
			
			if(StringUtil.isBlank(payment_stat) ||StringUtil.isBlank(apply_deal_id) ){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "请求参数为空"));
			}
			
			
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setLoanId(apply_deal_id); 
			queryPartner.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			queryPartner.setIsClosed(PartnerConstant.IsClosed.NO_CLOSED.getCode());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			if(tempPartner.getPid() == 0  ){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "该交易数据不存在"));
			}
			
			//判断该状态是否可以操作
			int  repaymentRepurchaseStatus =  tempPartner.getRepaymentRepurchaseStatus(); //还款状态
			
			if(repaymentRepurchaseStatus == PartnerConstant.RepaymentRepurchaseStatus.SUCCESS.getCode()){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "己还款成功不允许操作"));
			}else if(repaymentRepurchaseStatus == PartnerConstant.RepaymentRepurchaseStatus.FAIL.getCode()
				&& payment_stat.equals(DaJuConstant.PaymentStat.FAIL.getCode())){
					return DaJuUtil.notifyResponse(uuid, requestParams, 
							new Message(DaJuConstant.Header.ERROR.getCode(), "状态未改变不允许操作"));
			}else if(repaymentRepurchaseStatus == PartnerConstant.RepaymentRepurchaseStatus.ALREADY_APPLY.getCode()
					&& payment_stat.equals(DaJuConstant.PaymentStat.DEALING.getCode())){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "状态未改变不允许操作"));
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
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "操作失败，还款状态不存在"));
			}
			 
			if(clientPartner.updateProjectPartner(updatePartner) == 0 ){
				return DaJuUtil.notifyResponse(uuid, requestParams, 
						new Message(DaJuConstant.Header.ERROR.getCode(), "操作失败，更新异常"));
			}
	 
			return DaJuUtil.notifyResponse(uuid, requestParams, 
					new Message(DaJuConstant.Header.SUCCESS.getCode(), "操作成功"));
		}catch(Exception e){
			logger.error("uuid:"+uuid+",prepaymentNotify error",e);
			return DaJuUtil.notifyResponse(uuid, requestParams, 
					new Message(DaJuConstant.Header.ERROR.getCode(), "操作失败，系统异常"));
		}finally {
			 destroyFactory(factoryPartner);
		}
	}
	
	
	
	
	/**
	 * 提前还款状态查询
	 * @param uuid	 日记uuid
	 * @param apply_deal_id	交易唯一标识符
	 * @return
	 * @throws Exception
	 */
	public JSONObject queryPrepayment(String uuid , ProjectPartnerDto projectPartnerDto) throws Exception {
		JSONObject resultJson = null;
		try{
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.QUERY_PREPAYMENT.getCode());
			
			RequestParams requestParams = new RequestParams();
			
			Map<String,Object> bodyMap = new HashMap<String,Object>();
			bodyMap.put("apply_deal_id", projectPartnerDto.getLoanId());
			
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			//请求
// 			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
//			if(responseParams == null){
//				return null;
//			}
//			resultJson = new JSONObject();
//			if(!DaJuConstant.MessageCode.SUCCESS.getCode().equals(responseParams.getMessage().getStatus())   ){
//				return returnJson(0, responseParams.getMessage().getDesc(), 1, "",null);
//			}
//			JSONObject bodyJson = JSONObject.parseObject(responseParams.getBody().toString());
//			if(!DaJuConstant.ReturnCode.SUCCESS.getCode().equals(bodyJson.getString("return_code"))){
//				return returnJson(0, bodyJson.getString("return_msg"), 1, "",null);
//			}
			
			
			//测试-=--------------
 			JSONObject bodyJson  = new JSONObject(); 
			
			bodyJson.put("return_code", "0000");
			bodyJson.put("return_msg", "还款成功");
			bodyJson.put("apply_deal_id", projectPartnerDto.getLoanId()); 
			
			bodyJson.put("act_pay_amt", "1");
			bodyJson.put("act_capital_amt", "2");
			bodyJson.put("act_interests_am", "3");
			bodyJson.put("act_penalty_amt", "4");
			bodyJson.put("act_fine_amt", "5");
			bodyJson.put("act_compdinte_amt", "6");
			bodyJson.put("payment_stat", DaJuConstant.PaymentStat.SUCCESS.getCode());	//还款成功
			
			//测试 =======================
 
			//成功
			return returnJson(0, bodyJson.getString("return_msg"), 0, projectPartnerDto.getLoanId(),bodyJson);
			
		}catch(Exception e){
			logger.error("uuid:"+uuid+",queryPrepayment error",e);
			throw e;
		}
	}
	
	
	/**
	 * 创建还扣款文件，并上传ftp （每天生成一个）
	 * @param uuid
	 * @throws Exception
	 */
	public void createPaymentFile(String uuid)throws Exception {
		BaseClientFactory partnerClientFactory = null;
		try{
/*			partnerClientFactory = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) partnerClientFactory.getClient();
			
			//查出今天天己经还款成功的数据
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setPartnerNo(PartnerConstant.PARTNER_NYYH);
			queryPartner.setRequestStatus(PartnerConstant.RequestStatus.APPLY_PASS.getCode());//已申请的项目
			queryPartner.setApprovalStatus(PartnerConstant.ApprovalStatus.APPROVAL_PASS.getCode());//审核通过
			queryPartner.setLoanStatus(PartnerConstant.LoanStatus.LOAN_SUCCESS.getCode());//放款成功
			//还款成功
			queryPartner.setRepaymentRepurchaseStatus(PartnerConstant.RepaymentRepurchaseStatus.SUCCESS.getCode());
			queryPartner.setIsClosed(1);//未关闭
			queryPartner.setPage(-1);	//不分页
			List<ProjectPartner> partnerList =  clientPartner.findAllProjectPartner(queryPartner);*/
			
			StringBuffer detailSb = new StringBuffer("");
			StringBuffer totalSb = new StringBuffer("");
/*			double totalMoney = 0 ; 
			if(!org.springframework.util.CollectionUtils.isEmpty(partnerList)){
				ProjectPartner tempPartner = null;
				for(int i = 0 ; i < partnerList.size() ; i++){
					tempPartner = partnerList.get(i);
					detailSb.append(i+1).append("|");	//序号
					detailSb.append(tempPartner.getLoanId()).append("|");	//大桔返回资金申请交易编号
					detailSb.append(tempPartner.getPartnerOrderCode()).append("|");	//场景资金申请编号
					detailSb.append("").append("|");	//平台申请到资方业务编号
					detailSb.append(tempPartner.getRefundTotalAmount()).append("|");	//还款金额
					detailSb.append(tempPartner.getRefundDate()).append("|");	//扣款日期
					detailSb.append(tempPartner.getPayAcctName()).append("|");	//还款账户名
					detailSb.append(tempPartner.getPayAcctNo()).append("|");	//还款账户
					
					if((i+1)<partnerList.size()){
						detailSb.append("\n\n");	//还行
					}
					totalMoney += tempPartner.getRefundTotalAmount();
				}
				totalSb.append(0).append("|");	//第一个
				totalSb.append(partnerList.size()).append("|"); 				 //总笔数
				totalSb.append(MoneyFormatUtil.format2(totalMoney)).append("|"); //总金额
				totalSb.append("|||||");  		 
				//换行
				totalSb.append("\n\n");
				//追加明细
				totalSb.append(detailSb.toString());
			}*/
			
			
			totalSb.append(0).append("|");	//第一个
			totalSb.append(0).append("|"); 				 //总笔数
			totalSb.append(MoneyFormatUtil.format2(0)).append("|"); //总金额
			totalSb.append("|||||");  		 
			//换行
			//totalSb.append("\n\n");
			
			
			
			Date nowDate = new Date();
			//Paymentqf20170124.txt
			//时间格式
			String nowDateStr = DateUtils.dateToFormatString(nowDate, "yyyyMMdd");
			String fileName = "Paymentqf"+nowDateStr+".txt";
			
			//系统本地路径
			String localRootPath =	CommonUtil.getRootPath();
			//系统上传根目录
			String uploadFileRoot = getUploadFilePath();
			
			String filePath = localRootPath+uploadFileRoot+"/partner/payment/"+nowDateStr+"/"+fileName;
			filePath = CommonUtil.dealPath(filePath);
			
			logger.info("uuid："+uuid+",生成扣还款文件路径filePath:"+filePath);
			logger.info("uuid："+uuid+",生成扣还款文件内容totalSb:"+totalSb.toString());
			
			//存在覆盖
			FileUtil.creatFile2(filePath);
			FileUtil.writeTxtFile(totalSb.toString(), filePath, false,"UTF-8");
			//将文件上传侄FTP服务器
			String ftpFilePath = DaJuUtil.NYYH_SFTP_DIR_BASE_PATH+"/payment/"+fileName;
			String ftpPort = DaJuUtil.NYYH_SFTP_SERVER_PORT;
			if(StringUtil.isBlank(ftpPort)){
				ftpPort = "22";	//为空默认为22
			}
			
			SFTPUtil sftpUtil = new SFTPUtil();
			sftpUtil.init(DaJuUtil.NYYH_SFTP_SERVER_IP, Integer.parseInt(ftpPort), 
					DaJuUtil.NYYH_SFTP_SERVER_USERNAME, DaJuUtil.NYYH_SFTP_SERVER_PASSWORD);
			//上传到FTP
			sftpUtil.uploadFile(filePath, ftpFilePath);
			sftpUtil.close();
			
		}catch(Exception e){
			logger.error("uuid:"+uuid+",createPaymentFile error",e);
			throw e;
		}finally{
			destroyFactory(partnerClientFactory);
		}
	}
	
	
	/**
	 * 系统读取当天放款文件
	 * @param uuid
	 * @throws Exception
	 */
	public void readLoanFile(String uuid)throws Exception {
		BaseClientFactory partnerClientFactory = null;
		try{
			partnerClientFactory = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client partnerClient =(ProjectPartnerService.Client) partnerClientFactory.getClient();
			
			Date nowDate = new Date();
			
			Date yesterdayDate  = DateUtils.addDay(nowDate, -1);
			
			//时间格式
			String nowDateStr = DateUtils.dateToFormatString(yesterdayDate, "yyyyMMdd");
			//Loanqf20170124.txt
			String fileName = "Loanqf"+nowDateStr+".txt";
			//FTP服务器文件
			String ftpFilePath = DaJuUtil.NYYH_SFTP_DIR_BASE_PATH+"/loan/"+fileName;
			//系统本地路径
			String localRootPath =	CommonUtil.getRootPath();
			//系统上传根目录
			String uploadFileRoot = getUploadFilePath();
			
			String filePath = localRootPath+uploadFileRoot+File.separator+"partner"+File.separator+"loan"+File.separator+nowDateStr+File.separator+fileName;
			filePath = CommonUtil.dealPath(filePath);
				
			
			
			String ftpPort = DaJuUtil.NYYH_SFTP_SERVER_PORT;
			if(StringUtil.isBlank(ftpPort)){
				ftpPort = "22";	//为空默认为21
			}
			
			SFTPUtil sftpUtil = new SFTPUtil();
			sftpUtil.init(DaJuUtil.NYYH_SFTP_SERVER_IP, Integer.parseInt(ftpPort), 
					DaJuUtil.NYYH_SFTP_SERVER_USERNAME, DaJuUtil.NYYH_SFTP_SERVER_PASSWORD);
			
			
			logger.info("uuid："+uuid+",系统读取当天放款文件filePath:"+filePath);
			logger.info("uuid："+uuid+",系统读取当天放款文件ftpFilePath:"+ftpFilePath);
			
			FileUtil.creatFile2(filePath);
			//下载文件
			sftpUtil.downFile(ftpFilePath , filePath );
			sftpUtil.close();
			
			String loanStr = FileUtil.readTxtFile2(filePath);
			logger.info("uuid:"+uuid+",read file loanStr:"+loanStr);
			
			
			List<ProjectPartner> updatePartnerList = new ArrayList<ProjectPartner>();
			Map<String,ProjectPartner> updatePartnerMap = new HashMap<String,ProjectPartner>();
			List<String> loanIdList = new ArrayList<String>();
			ProjectPartner tempUpdatePartner = null;
			
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			
			//处理文件
			if(!StringUtil.isBlank(loanStr)){
				String[] loanStrArray=loanStr.split("\n");
				//第一行为统计，从第二行读起
				if(loanStrArray!= null && loanStrArray.length > 0){
					for(int i = 0 ; i < loanStrArray.length ; i ++ ){
						
						try{
							String tempLoanStr = loanStrArray[i];
							if(!StringUtil.isBlank(tempLoanStr)){
								String[] loanDetailArray = tempLoanStr.split("\\|");
								if(loanDetailArray.length > 9 ){
									tempUpdatePartner = new ProjectPartner();
									//资方id
									tempUpdatePartner.setLoanId(loanDetailArray[1]);
									//申请编号
									tempUpdatePartner.setPartnerOrderCode(loanDetailArray[2]);
									//平台申请到资方业务编号
									tempUpdatePartner.setPartnerPlatformOrderCode(loanDetailArray[3]);
									//机构放款日期
									tempUpdatePartner.setPartnerLoanDate(loanDetailArray[9].replace("/", "-"));
									//放款通知时间
									tempUpdatePartner.setLoanResultTime(nowTime.toString());
									//放款状态（成功）
									tempUpdatePartner.setLoanStatus(PartnerConstant.LoanStatus.LOAN_SUCCESS.getCode());
									loanIdList.add(loanDetailArray[1]);
									updatePartnerMap.put(loanDetailArray[1], tempUpdatePartner);
								}
							}
						}catch(Exception e){
							logger.error("uuid:"+uuid+",readLoanFile deal loaninfo ["+i+"] error,and go on next",e);
							continue;
						}
					}
				}
			}
			if(!CollectionUtils.isEmpty(updatePartnerMap.keySet())){
				
				ProjectPartner query = new ProjectPartner();
				query.setLoanIds(loanIdList);
				query.setPartnerNo(PartnerConstant.PARTNER_NYYH);
				List<ProjectPartner> tempPartnerList = partnerClient.findProjectPartnerInfoList(query);
				ProjectPartner tempPartner = null;
				if(!CollectionUtils.isEmpty(tempPartnerList)){
					for (ProjectPartner indexObj : tempPartnerList) {
						tempPartner = updatePartnerMap.get(indexObj.getLoanId());
						//未放款的
						if(tempPartner != null && indexObj.getLoanStatus() != PartnerConstant.LoanStatus.LOAN_SUCCESS.getCode()){
							tempPartner.setPid(indexObj.getPid());
							updatePartnerList.add(tempPartner);
						}
					}
				}
				if(!CollectionUtils.isEmpty(updatePartnerList)){
					//批量更新
					partnerClient.updateBatchProjectParner(updatePartnerList);
				}
			}
			
		}catch(Exception e){
			logger.error("uuid:"+uuid+",readLoanFile error",e);
			throw e;
		}finally{
			destroyFactory(partnerClientFactory);
		}
	}
	
	
	
	/**
	 * 用户开户绑卡
	 * @param uuid	 日记uuid
	 * @return
	 * @throws Exception
	 */
	public ResponseParams openAccount(String uuid , Map<String,Object> bodyMap) throws Exception {
		try{
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.OPEN_ACCOUNT.getCode());
			
			RequestParams requestParams = new RequestParams();
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			//请求
			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			return responseParams;
		}catch(Exception e){
			logger.error("uuid:"+uuid+",openAccount error",e);
			throw e;
		}
	}
	
	/**
	 * 用户开户验证码获取接口
	 * @param uuid	 日记uuid
	 * @return
	 * @throws Exception
	 */
	public ResponseParams getOtp(String uuid , Map<String,Object> bodyMap) throws Exception {
		try{
			Header header = new Header();
			header.setService_code(DaJuConstant.ServiceCode.GetOtp.getCode());
			
			RequestParams requestParams = new RequestParams();
			requestParams.setHeader(header);
			requestParams.setBody(bodyMap);
			
			//请求
			ResponseParams responseParams = DaJuUtil.post(uuid,requestParams);
			return responseParams;
		}catch(Exception e){
			logger.error("uuid:"+uuid+",getOtp error",e);
			throw e;
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
