package com.xlkfinance.bms.web.api.partnerapi.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.PartnerConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstateService;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.customer.CusDTO;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerService;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.partner.PartnerApprovalRecord;
import com.xlkfinance.bms.rpc.partner.ProjectPartner;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerDto;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFileService;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerService;
import com.xlkfinance.bms.rpc.system.SysLookupService;
import com.xlkfinance.bms.rpc.workflow.TaskHistoryDto;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;
import com.xlkfinance.bms.web.api.partnerapi.BasePartnerApi;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.CommonUtil;
import com.xlkfinance.bms.web.util.FileUtil;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.PartnerProjectUtil;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.SFTPUtil;
import com.xlkfinance.bms.web.util.YZTSercurityUtil;

/**
 * 统联金融接口实现类
 * @author chenzhuzhen
 * @date 2016年6月27日 下午3:28:25
 */
@Service("tLPartnerApiImpl")
public class TLPartnerApiImpl extends BaseController implements BasePartnerApi {
	
	private Logger logger = LoggerFactory.getLogger(TLPartnerApiImpl.class);
	
	//Q房私钥
	private static String QFANG_PARTNER_PRIVATE_KEY = PropertiesUtil.getValue("qfang.partner.private.key");
	//统联加密公钥
	private static String TL_PARTNER_PUBLIC_KEY = PropertiesUtil.getValue("tl.partner.public.key");
	//MD5
	private static String TL_PARTNER_MD5 = PropertiesUtil.getValue("tl.partner.md5");
	//机构合作code 请求
	private static String TL_PARTNER_PARAM_PARTNERCODE = PropertiesUtil.getValue("tl.partner.param.partnercode");
	//机构合作code（Q房提供）
	private static String TL_PARTNER_PARAM_PARTNERCODE_QFANG = PropertiesUtil.getValue("tl.partner.param.partnercode.qfang");
	//接口请求地址
	private static String TL_PARTNER_API_URL = PropertiesUtil.getValue("tl.partner.api.url");
	
	//机构代码-IFSP给资产方分配
	private static String TL_PARTNER_PARAM_INST_CODE = PropertiesUtil.getValue("tl.partner.param.inst.code");
	
	//资金方代码
	private static String TL_PARTNER_PARAM_FUND_CODE = PropertiesUtil.getValue("tl.partner.param.fund.code");
	//统联白名单
	private static String TL_PARTNER_PARAM_ADD_USER = PropertiesUtil.getValue("tl.partner.param.add.user");
	
	//统联SFTP服务配置信息
	//SFTP地址
	private static String TL_SFTP_HOST = PropertiesUtil.getValue("tl.sftp.host");
	//SFTP端口号
	private static String TL_SFTP_PORT = PropertiesUtil.getValue("tl.sftp.port");
	//SFTP用户名
	private static String TL_SFTP_USERNAME = PropertiesUtil.getValue("tl.sftp.username");
	//SFTP密码
	private static String TL_SFTP_PWD = PropertiesUtil.getValue("tl.sftp.pwd");
	
	/**是否开启机构调试模式-debug*/
	private static String IS_PARTNER_API_DEBUG = PropertiesUtil.getValue("is.partner.api.debug");
	
	
	
	
	/**
	 *  申请/补充材料
	 *  返回格式：｛status:xx,msg:xxx,data{loan_id:xxx,err_code:xxx}｝
	 */
	@Override
	public JSONObject apply(ProjectPartnerDto projectPartnerDto ,HttpServletRequest request) throws Exception {
		
		JSONObject resultJson = null;
		BaseClientFactory clientFactoryProject = null;
		BaseClientFactory clientFactoryPartner = null;
		BaseClientFactory clientFactoryCus = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientFactoryWorkflow = null;
		BaseClientFactory clientEstateFactory = null;
		
		try{
			
			//项目
			clientFactoryProject = getFactory(BusinessModule.MODUEL_BEFORELOAN, "ProjectService");
			ProjectService.Client clientProject =(ProjectService.Client) clientFactoryProject.getClient();
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//字典service
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			
			//客户service
			clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientCus =(CusPerService.Client) clientFactoryCus.getClient();
			
			clientFactoryWorkflow = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
			WorkflowService.Client clientWorkflow =(WorkflowService.Client) clientFactoryWorkflow.getClient();
			
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
			//查询共同借款人
			tempDto.setPublicManList(clientCus.getNoSpouseLists(projectPartnerDto.getProjectId()));
			//查询审批工作流记录
			//creditLoanRequestProcess 小科   foreLoanRequestProcess 万通    
			WorkflowProjectVo queryWorkFlow= new WorkflowProjectVo();
			queryWorkFlow.setProjectId(projectPartnerDto.getProjectId());
			queryWorkFlow.setProcessDefinitionKey("creditLoanRequestProcess");
			WorkflowProjectVo tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			
			//小科为空，查询万通
			if(tempW == null || tempW.getPid() == 0){
				queryWorkFlow.setProcessDefinitionKey("foreLoanRequestProcess");
				tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			}
			//机构合伙人
			if(tempW == null || tempW.getPid() == 0){
				queryWorkFlow.setProcessDefinitionKey("businessApplyRequestProcess");
				tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			}
			List<TaskHistoryDto> taskHistorys = new ArrayList<TaskHistoryDto>();
			// 有效的工作流对象
			if(null != tempW && tempW.getPid()>0  ){
				String workflowInstanceId = tempW.getWorkflowInstanceId();
				taskHistorys = clientWorkflow.queryWorkFlowHistory(workflowInstanceId,1,100);
			}
			tempDto.setTaskHistoryList(taskHistorys);
			
			//转换对象值
			tempDto = PartnerProjectUtil.convertBean(tempDto, tempProject, tempGuarantee, tempPartner, tempCusPerson);
			
			//通过审核状态判断申请，还是补充材料
			
			//封装请求参数=============================================
			JSONObject paramJson = new JSONObject();
			paramJson.put("loan_id", StringUtil.isBlank(tempDto.getLoanId()) ? "" : tempDto.getLoanId());	//业务流水号
			paramJson.put("inst_code", TL_PARTNER_PARAM_INST_CODE);				//机构代码-IFSP给资产方分配的机构代码
			paramJson.put("fund_code", TL_PARTNER_PARAM_FUND_CODE);				//资金方代码-申请时必填,资料补充时不填
			paramJson.put("type", Constants.PARTNER_TL_TYPE_1);					//业务类别       一期只支持交易类赎楼
			paramJson.put("add_user", TL_PARTNER_PARAM_ADD_USER);			//录入人
			paramJson.put("city", tempDto.getCityCode());					//城市
			paramJson.put("username", tempDto.getUserName());				//客户名称
			
			String id_type=Constants.PARTNER_ACCESSORY_TYPE_07;
			if(!StringUtil.isBlank(tempDto.getCertType())){
				String	certType = clientSys.getSysLookupValByName(Integer.parseInt(tempDto.getCertType()));
				if("身份证".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_00;
				}else if("护照".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
				}else if("军官证".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
				}else if("台胞证".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_10;
				}
			}
			paramJson.put("id_type", id_type);								//证件类型
			paramJson.put("idcard_no", tempDto.getCardNo());				//证件号码
			paramJson.put("apply_money", (int)(tempDto.getApplyMoney()*100));	//借款金额(分)
			paramJson.put("apply_deadline", tempDto.getApplyDate());		//借款期限(天)
			paramJson.put("loan_date", tempDto.getApplyLoanDate());			//预计放款日
			paramJson.put("cust_manager", tempDto.getPmCustomerName() == null ? "" : tempDto.getPmCustomerName().trim() );			//客户经理
			
			String	lookUpName = clientSys.getSysLookupValByName(tempDto.getSex());
			String gender = "1";
			if("女".equals(lookUpName) ){
				gender = "2";
			}
			paramJson.put("gender", gender);									//性别	1-男;2-女
			paramJson.put("present_addr", tempDto.getLiveAddr() == null ? "" : tempDto.getLiveAddr());			//现居住地址
			
			paramJson.put("receive_acct_no", tempDto.getPaymentAcctNo());		//借款人收款账号
			paramJson.put("receive_acct_name", tempDto.getPaymentAcctName()  == null ? "" : tempDto.getPaymentAcctName().trim() );		//借款人收款账户名称
			paramJson.put("receive_bank_no", "");								//借款人收款开户行号
			paramJson.put("receive_bank_name", tempDto.getPaymentBank());		//借款人收款开户行名
			paramJson.put("pay_acct_no", tempDto.getPaymentAcctNo());			//借款人还款账号
			paramJson.put("pay_acct_name",tempDto.getPaymentAcctName() == null ? "" : tempDto.getPaymentAcctName().trim() );		//借款人还款账户名称
			paramJson.put("pay_bank_no", "");									//借款人还款开户行号
			paramJson.put("pay_bank_name", tempDto.getPaymentBank());			//借款人还款开户行名
	
			paramJson.put("receipt", "Y");				//内外单		Y=内单，N=外单
			paramJson.put("source", "Q房网");				//业务来源
			paramJson.put("busi_contacts", tempDto.getBusinessContacts() == null ? "" : tempDto.getBusinessContacts().trim() );			//业务联系人
			paramJson.put("transactor", tempDto.getManagers() == null ? "" : tempDto.getManagers().trim() );			//经办人
			
			//共同借款人
			JSONArray publicManJsonArray = new JSONArray();
			List<CusDTO> publicManList = tempDto.getPublicManList();
			if(publicManList !=null && publicManList.size() > 0){
				JSONObject tempJson = null;
				for (CusDTO indexObj : publicManList) {
					id_type=Constants.PARTNER_ACCESSORY_TYPE_07;
					 
					if("身份证".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_00;
					}else if("护照".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
					}else if("军官证".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
					}else if("台胞证".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_10;
					}
					
					gender = "1";
					if("女".equals(indexObj.getSexName()) ){
						gender = "2";
					}
					tempJson = new JSONObject();
					tempJson.put("name", indexObj.getChinaName());			//姓名
					tempJson.put("gender", gender);							//性别
					tempJson.put("id_type", id_type);						//证件类型
					tempJson.put("idcard_no", indexObj.getCertNumber());	//证件号码
					tempJson.put("relation", indexObj.getRelationText());	//关系
					tempJson.put("position", indexObj.getWorkService());	//职务
					tempJson.put("income", (int)(indexObj.getMonthIncome()*100));	//月收入
					publicManJsonArray.add(tempJson);
				}
			}
			paramJson.put("common_loan_people", publicManJsonArray);
			
			//贷款银行
			paramJson.put("ori_loan_bank", tempForeclosure.getOldBankStr());				//原贷款银行
			paramJson.put("ori_contacts", tempForeclosure.getOldLoanPerson());				//原银行联系人
			paramJson.put("ori_loan_money", (int)(tempForeclosure.getOldLoanMoney()*100));	//原贷款金额
			paramJson.put("ori_phone_no", tempForeclosure.getOldLoanPhone());				//原贷款联系电话
			paramJson.put("new_loan_bank", tempForeclosure.getNewBankStr());				//新贷款银行
			paramJson.put("new_contacts", tempForeclosure.getNewLoanPerson());				//新银行联系人
			paramJson.put("new_loan_money", (int)(tempForeclosure.getNewLoanMoney()*100));	//新贷款金额
			paramJson.put("new_phone_no", tempForeclosure.getNewLoanPhone());				//新贷款联系电话
			
			String  loan_mode = "1";		//			QF系统 1按揭 2组合贷 3公积金贷 4一次性付款 5 经营贷 6 消费贷
			if(tempForeclosure.getPaymentType() == 1 || tempForeclosure.getPaymentType() ==5 || tempForeclosure.getPaymentType() ==6){
				loan_mode = "2";
			}else if(tempForeclosure.getPaymentType() == 2 ){
				loan_mode = "3";
			}else if(tempForeclosure.getPaymentType() == 3){
				loan_mode = "1";
			}
			paramJson.put("loan_mode", loan_mode);			//贷款方式	1-公积金贷款;2-商业贷款;3-组合贷款
			paramJson.put("pro_bank", tempForeclosure.getAccumulationFundBankStr());					//公积金银行
			paramJson.put("pro_loan_money", (int)(tempForeclosure.getAccumulationFundMoney()*100));	//公积金贷款金额
			paramJson.put("regulatory_money", (int)(tempForeclosure.getFundsMoney()*100));			//资金监管金额
			paramJson.put("regulatory_unit", tempForeclosure.getSuperviseDepartmentStr());				//监管单位
			paramJson.put("just_dt", tempForeclosure.getNotarizationDate());						//委托公证日期
			
			//物业信息---------------------------

			String property_name ="";		//物业名称
			String house_card_no ="";		//房产证号
			double total_area = 0 ; 		//面积
			double transaction_price = 0 ;	//成交价(元) 
			double original_price = 0 ; 	//登记价(元) 
			for (BizProjectEstate indexObj : tempEstateList) {
				property_name += indexObj.getHouseName()+",";
				house_card_no += indexObj.getHousePropertyCard()+",";
				total_area += indexObj.getArea();
				transaction_price += indexObj.getTranasctionMoney();
				original_price += indexObj.getCostMoney();
			}
			if(!StringUtil.isBlank(property_name)){
				property_name = property_name.substring(0,property_name.length()-1);
				house_card_no = house_card_no.substring(0,house_card_no.length()-1);
			}
			
			
			paramJson.put("property_name",property_name);					//物业名称
			paramJson.put("acreage", total_area);							//面积(平方)
			paramJson.put("price", (int)(original_price*100));				//原价
			paramJson.put("final_price", (int)(transaction_price*100));		//成交价
			paramJson.put("property_no", house_card_no);					//房产证号
			
			//如果有多个，默认以第一个为准
			ProjectProperty tempProperty =tempProject.getProjectProperty();
			//买房人名称
			paramJson.put("buy_house_name", StringUtil.isBlank(tempProperty.getBuyerName()) ? "" : tempProperty.getBuyerName().split(",")[0]);			
			paramJson.put("buy_id_type", Constants.PARTNER_ACCESSORY_TYPE_00);	//买房人证件类型
			//买房人证件号码
			paramJson.put("buy_card_no", StringUtil.isBlank(tempProperty.getBuyerCardNo()) ? "" : tempProperty.getBuyerCardNo().split(",")[0]);			
			//买房人家庭地址
			paramJson.put("family_addr", StringUtil.isBlank(tempProperty.getBuyerAddress()) ? "" : tempProperty.getBuyerAddress().split(",")[0]);			
			paramJson.put("introduction", tempDto.getRemark());					//特殊情况说明
			
			//内部审批流
			JSONArray workflowJsonArray = new JSONArray();
			List<TaskHistoryDto> workflowList = tempDto.getTaskHistoryList();
			if(workflowList !=null && workflowList.size() > 0){
				JSONObject tempJson = null;
				String completeDetmTemp ="";
				for (TaskHistoryDto indexObj : workflowList) {
					tempJson = new JSONObject();
					completeDetmTemp = indexObj.getExecuteDttm();
					if(!StringUtil.isBlank(completeDetmTemp)){
						completeDetmTemp = completeDetmTemp.split(" ")[0];
					}else{
						completeDetmTemp = "";
					}
					tempJson.put("task_depict", indexObj.getTaskName());	//任务描述
					tempJson.put("operator", indexObj.getExecuteUserRealName());	//操作人
					tempJson.put("operator_time", completeDetmTemp);		//操作时间
					tempJson.put("opinion", indexObj.getMessage());	//意见及说明
					workflowJsonArray.add(tempJson);
				}
			}
			paramJson.put("approval_process", workflowJsonArray);
			//文件-文件先打成zip包，再传值
			paramJson.put("files", getRequestFileJsonAndPackage(projectPartnerDto.getRequestFiles(), tempDto.getProjectId(), tempDto.getPartnerNo(),tempDto.getPid(), getServerBaseUrl(request)));
			
			logger.info(">>>>>>apply paramJson:"+paramJson.toString());
			
			//参数加密
			List<HttpRequestParam> paramList= YZTSercurityUtil.encryptParamsComm(paramJson.toString(), TL_PARTNER_MD5, TL_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE);
			
			HttpRequestParam signMethodParam = new HttpRequestParam("sign_method","md5");
			HttpRequestParam timestampParam = new HttpRequestParam("timestamp",DateUtils.dateToFormatString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			HttpRequestParam formatParam = new HttpRequestParam("format","json");
			HttpRequestParam vParam = new HttpRequestParam("v","1.0");
			HttpRequestParam methodParam = new HttpRequestParam("method","tlf.ifsp.busi.bps.Q201");
			paramList.add(signMethodParam);
			paramList.add(timestampParam);
			paramList.add(formatParam);
			paramList.add(vParam);
			paramList.add(methodParam);
			
			StringBuffer tempSb = new StringBuffer();
			for (HttpRequestParam indexObj : paramList) {
				tempSb.append(indexObj.getParaName()).append(":").append(indexObj.getParaValue()).append(",");
			}
			logger.info(">>>>>>apply paramList:"+tempSb.toString());
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(TL_PARTNER_API_URL, paramList, "UTF-8");
			logger.info(">>>>>>apply http result:"+result);
			
			//======================测试=====================================
//			if(true){
//				//测试成功
//				resultJson = new JSONObject();
//				resultJson.put("status", 0);
//				resultJson.put("msg", 1);
//				JSONObject dataJson = new JSONObject();
//				dataJson.put("loan_id", 11111);
//				dataJson.put("err_code", 0);
//				resultJson.put("data", dataJson);
//				return resultJson;
//			}
			
			if(!StringUtil.isBlank(result)){
				//解密
				String resContent = YZTSercurityUtil.decryptResponseComm(result, TL_PARTNER_MD5,QFANG_PARTNER_PRIVATE_KEY);

				logger.info(">>>>>>apply 明文 resContent:"+resContent);
				resContent = URLDecoder.decode(resContent,"UTF-8");
				logger.info(">>>>>>notify 明文转码后resContent::"+resContent);
				
				JSONObject respMap = JSON.parseObject(resContent);

				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", respMap.get("status"));
				resultJson.put("msg",respMap.getString("msg"));
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", respMap.get("loan_id"));
				dataJson.put("err_code", respMap.get("err_code"));
				resultJson.put("data", dataJson);
			}
		}catch(Exception e){
			logger.error(">>>>>>apply error",e);
			return null;
		}finally {
			destroyFactory(clientFactoryPartner);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
			destroyFactory(clientFactoryWorkflow);
			destroyFactory(clientEstateFactory);
		}
		return resultJson;
	}

	/**
	 * 复议申请
	 */
	public JSONObject reApply(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		
		BaseClientFactory clientFactoryPartner = null;
		BaseClientFactory clientFactoryCus = null;
		BaseClientFactory clientFactorySys = null;
		BaseClientFactory clientFactoryWorkflow = null;
		
		try{
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//字典service
			clientFactorySys = getFactory(BusinessModule.MODUEL_SYSTEM, "SysLookupService");
			SysLookupService.Client clientSys =(SysLookupService.Client) clientFactorySys.getClient();
			
			//客户service
			clientFactoryCus = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusPerService");
			CusPerService.Client clientCus =(CusPerService.Client) clientFactoryCus.getClient();
			
			clientFactoryWorkflow = getFactory(BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
			WorkflowService.Client clientWorkflow =(WorkflowService.Client) clientFactoryWorkflow.getClient();
			
			
			//查询项目，并懒加懒相关信息
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setProjectId(projectPartnerDto.getProjectId());
			queryPartner.setPartnerNo(projectPartnerDto.getPartnerNo());
			
			ProjectPartnerDto tempDto = clientPartner.findProjectPartnerAndLazy(queryPartner);
			//查询共同借款人
			tempDto.setPublicManList(clientCus.getNoSpouseLists(projectPartnerDto.getProjectId()));
			
			
			//查询审批工作流记录
			//creditLoanRequestProcess 小科  
			//foreLoanRequestProcess 万通    
			WorkflowProjectVo queryWorkFlow= new WorkflowProjectVo();
			queryWorkFlow.setProjectId(projectPartnerDto.getProjectId());
			queryWorkFlow.setProcessDefinitionKey("creditLoanRequestProcess");
			WorkflowProjectVo tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			
			//小科为空，查询万通
			if(tempW == null || tempW.getPid() == 0){
				queryWorkFlow.setProcessDefinitionKey("foreLoanRequestProcess");
				tempW = clientWorkflow.findWorkflowProject(queryWorkFlow);
			}
			
			List<TaskHistoryDto> taskHistorys = new ArrayList<TaskHistoryDto>();
			// 有效的工作流对象
			if(null != tempW && tempW.getPid()>0  ){
				String workflowInstanceId = tempW.getWorkflowInstanceId();
				taskHistorys = clientWorkflow.queryWorkFlowHistory(workflowInstanceId,1,100);
			}
			tempDto.setTaskHistoryList(taskHistorys);
			
			
			//封装请求参数=============================================
			JSONObject paramJson = new JSONObject();
			
			paramJson.put("loan_id", tempDto.getLoanId());	//业务流水号
			paramJson.put("inst_code", TL_PARTNER_PARAM_INST_CODE);				//机构代码-IFSP给资产方分配的机构代码
			paramJson.put("fund_code", TL_PARTNER_PARAM_FUND_CODE);				//资金方代码-申请时必填,资料补充时不填
			
			//复议理由
			paramJson.put("reason", StringUtil.isBlank(tempDto.getReApplyReason()) ? "" : tempDto.getReApplyReason());				
			
			paramJson.put("type", Constants.PARTNER_TL_TYPE_1);				//业务类别       一期只支持交易类赎楼
			paramJson.put("add_user", TL_PARTNER_PARAM_ADD_USER);			//录入人
			paramJson.put("city", tempDto.getCityCode());					//城市
			paramJson.put("username", tempDto.getUserName());				//客户名称
			
			String id_type=Constants.PARTNER_ACCESSORY_TYPE_07;
			if(!StringUtil.isBlank(tempDto.getCertType())){
				String	certType = clientSys.getSysLookupValByName(Integer.parseInt(tempDto.getCertType()));
				if("身份证".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_00;
				}else if("护照".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
				}else if("军官证".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
				}else if("台胞证".equals(certType)){
					id_type=Constants.PARTNER_ACCESSORY_TYPE_10;
				}
			}
			paramJson.put("id_type", id_type);								//证件类型
			paramJson.put("idcard_no", tempDto.getCardNo());				//证件号码
			paramJson.put("apply_money", (int)tempDto.getApplyMoney()*100);	//借款金额(分)
			paramJson.put("apply_deadline", tempDto.getApplyDate());		//借款期限(天)
			paramJson.put("loan_date", tempDto.getApplyLoanDate());			//预计放款日
			//客户经理
			paramJson.put("cust_manager", tempDto.getPmCustomerName() == null ? "" : tempDto.getPmCustomerName().trim() );			
			
			String	lookUpName = clientSys.getSysLookupValByName(tempDto.getSex());
			String gender = "1";
			if("女".equals(lookUpName) ){
				gender = "2";
			}
			paramJson.put("gender", gender);								//性别	1-男;2-女
			paramJson.put("present_addr", tempDto.getLiveAddr() == null ? "" : tempDto.getLiveAddr());			//现居住地址
			
			paramJson.put("receive_acct_no", tempDto.getPaymentAcctNo());	//借款人收款账号
			paramJson.put("receive_acct_name", tempDto.getPaymentBank()  == null ? "" : tempDto.getPaymentBank().trim() );		//借款人收款账户名称
			paramJson.put("receive_bank_no", "");							//借款人收款开户行号
			paramJson.put("receive_bank_name", tempDto.getPaymentBank());	//借款人收款开户行名
			paramJson.put("pay_acct_no", tempDto.getPaymentAcctNo());		//借款人还款账号
			paramJson.put("pay_acct_name",tempDto.getPaymentAcctName() == null ? "" : tempDto.getPaymentAcctName().trim() );		//借款人还款账户名称
			paramJson.put("pay_bank_no", "");								//借款人还款开户行号
			paramJson.put("pay_bank_name", tempDto.getPaymentBank());		//借款人还款开户行名
	
			paramJson.put("receipt", "Y");				//内外单		Y=内单，N=外单
			paramJson.put("source", "Q房网");				//业务来源
			paramJson.put("busi_contacts", tempDto.getBusinessContacts() == null ? "" : tempDto.getBusinessContacts().trim() );			//业务联系人
			paramJson.put("transactor", tempDto.getManagers() == null ? "" : tempDto.getManagers().trim() );			//经办人
			
			//共同借款人
			JSONArray publicManJsonArray = new JSONArray();
			List<CusDTO> publicManList = tempDto.getPublicManList();
			if(publicManList !=null && publicManList.size() > 0){
				JSONObject tempJson = null;
				for (CusDTO indexObj : publicManList) {
					id_type=Constants.PARTNER_ACCESSORY_TYPE_07;
					if("身份证".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_00;
					}else if("护照".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
					}else if("军官证".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_02;
					}else if("台胞证".equals(indexObj.getCertTypeName())){
						id_type=Constants.PARTNER_ACCESSORY_TYPE_10;
					}
					
					gender = "1";
					if("女".equals(indexObj.getSexName()) ){
						gender = "2";
					}
					tempJson = new JSONObject();
					tempJson.put("name", indexObj.getChinaName());			//姓名
					tempJson.put("gender", gender);							//性别
					tempJson.put("id_type", id_type);						//证件类型
					tempJson.put("idcard_no", indexObj.getCertNumber());	//证件号码
					tempJson.put("relation", indexObj.getRelationText());	//关系
					tempJson.put("position", indexObj.getWorkService());	//职务
					tempJson.put("income", (int)(indexObj.getMonthIncome()*100));	//月收入
					publicManJsonArray.add(tempJson);
				}
			}
			paramJson.put("common_loan_people", publicManJsonArray);
			
			//贷款银行
			paramJson.put("ori_loan_bank", tempDto.getOldBankName());				//原贷款银行
			paramJson.put("ori_contacts", tempDto.getOldLoanPerson());				//原银行联系人
			paramJson.put("ori_loan_money", (int)(tempDto.getOldLoanMoney()*100));	//原贷款金额
			paramJson.put("ori_phone_no", tempDto.getOldLoanPhone());				//原贷款联系电话
			paramJson.put("new_loan_bank", tempDto.getNewBankName());				//新贷款银行
			paramJson.put("new_contacts", tempDto.getNewLoanPerson());				//新银行联系人
			paramJson.put("new_loan_money", (int)(tempDto.getNewLoanMoney()*100));	//新贷款金额
			paramJson.put("new_phone_no", tempDto.getNewLoanPhone());				//新贷款联系电话
			
			String  loan_mode = "1";		//			QF系统 1按揭 2组合贷 3公积金贷 4一次性付款
			if(tempDto.getPaymentType() == 1){
				loan_mode = "2";
			}else if(tempDto.getPaymentType() == 2){
				loan_mode = "3";
			}else if(tempDto.getPaymentType() == 3){
				loan_mode = "1";
			}
			paramJson.put("loan_mode", loan_mode);			//贷款方式	1-公积金贷款;2-商业贷款;3-组合贷款
			paramJson.put("pro_bank", tempDto.getAccumulationFundBank());					//公积金银行
			paramJson.put("pro_loan_money", (int)(tempDto.getAccumulationFundMoney()*100));	//公积金贷款金额
			paramJson.put("regulatory_money", (int)(tempDto.getFundsMoney()*100));			//资金监管金额
			paramJson.put("regulatory_unit", tempDto.getSuperviseDepartment());				//监管单位
			paramJson.put("just_dt", tempDto.getNotarizationDate());						//委托公证日期
			
			//物业信息
			paramJson.put("property_name",tempDto.getHouseName());			//物业名称
			paramJson.put("acreage", tempDto.getArea());					//面积(平方)
			paramJson.put("price", (int)(tempDto.getCostMoney()*100));				//原价
			paramJson.put("final_price", (int)(tempDto.getTranasctionMoney()*100));	//成交价
			paramJson.put("property_no", tempDto.getHousePropertyCard());			//房产证号
			
			//如果有多个，默认以第一个为准
			//买房人名称
			paramJson.put("buy_house_name", StringUtil.isBlank(tempDto.getBuyerName()) ? "" : tempDto.getBuyerName().split(",")[0]);			
			paramJson.put("buy_id_type", Constants.PARTNER_ACCESSORY_TYPE_00);	//买房人证件类型
			//买房人证件号码
			paramJson.put("buy_card_no", StringUtil.isBlank(tempDto.getBuyerCardNo()) ? "" : tempDto.getBuyerCardNo().split(",")[0]);			
			//买房人家庭地址
			paramJson.put("family_addr", StringUtil.isBlank(tempDto.getBuyerAddress()) ? "" : tempDto.getBuyerAddress().split(",")[0]);
			
			paramJson.put("introduction", tempDto.getRemark());			//特殊情况说明
			
			
			
			//内部审批流
			JSONArray workflowJsonArray = new JSONArray();
			List<TaskHistoryDto> workflowList = tempDto.getTaskHistoryList();
			if(workflowList !=null && workflowList.size() > 0){
				JSONObject tempJson = null;
				String completeDetmTemp = "";
				for (TaskHistoryDto indexObj : workflowList) {
					tempJson = new JSONObject();
					
					completeDetmTemp = indexObj.getExecuteDttm();
					if(!StringUtil.isBlank(completeDetmTemp)){
						completeDetmTemp = completeDetmTemp.split(" ")[0];
					}else{
						completeDetmTemp = "";
					}
					
					tempJson.put("task_depict", indexObj.getTaskName());
					tempJson.put("operator", indexObj.getExecuteUserRealName());
					tempJson.put("operator_time", completeDetmTemp);
					tempJson.put("opinion", indexObj.getMessage());
					workflowJsonArray.add(tempJson);
				}
			}
			paramJson.put("approval_process", workflowJsonArray);
			//文件-文件先打成zip包，再传值
			paramJson.put("files", getRequestFileJsonAndPackage(projectPartnerDto.getRequestFiles(), tempDto.getProjectId(), tempDto.getPartnerNo(),tempDto.getPid(), getServerBaseUrl(request)));
			
			
			logger.info(">>>>>>reApply paramJson:"+paramJson.toString());
			
			//参数加密
			List<HttpRequestParam> paramList= YZTSercurityUtil.encryptParamsComm(paramJson.toString(), TL_PARTNER_MD5, TL_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE);
			
			HttpRequestParam signMethodParam = new HttpRequestParam("sign_method","md5");
			HttpRequestParam timestampParam = new HttpRequestParam("timestamp",DateUtils.dateToFormatString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			HttpRequestParam formatParam = new HttpRequestParam("format","json");
			HttpRequestParam vParam = new HttpRequestParam("v","1.0");
			HttpRequestParam methodParam = new HttpRequestParam("method","tlf.ifsp.busi.bps.Q202");
			paramList.add(signMethodParam);
			paramList.add(timestampParam);
			paramList.add(formatParam);
			paramList.add(vParam);
			paramList.add(methodParam);
			
			
			StringBuffer tempSb = new StringBuffer();
			for (HttpRequestParam indexObj : paramList) {
				tempSb.append(indexObj.getParaName()).append(":").append(indexObj.getParaValue()).append(",");
			}
			logger.info(">>>>>>reApply paramList:"+tempSb.toString());
			
			
			if("true".equals(IS_PARTNER_API_DEBUG)){
				//测试成功
				resultJson = new JSONObject();
				resultJson.put("err_code", 0);
				resultJson.put("err_msg", "ok");
				JSONObject dataJson = new JSONObject();
				dataJson.put("err_code", 0);
				dataJson.put("err_msg", "ok");
				dataJson.put("loan_id", StringUtil.isBlank(tempDto.getLoanId()) 
						? new Date().getTime() : tempDto.getLoanId());
				resultJson.put("data", dataJson);
				return resultJson;
			}
			
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(TL_PARTNER_API_URL, paramList, "UTF-8");
			logger.info(">>>>>>reApply http result:"+result);
			
			//======================测试=====================================
//			if(true){
//				//测试成功
//				resultJson = new JSONObject();
//				resultJson.put("status", 0);
//				resultJson.put("msg", 1);
//				JSONObject dataJson = new JSONObject();
//				dataJson.put("loan_id", 11111);
//				dataJson.put("err_code", 0);
//				resultJson.put("data", dataJson);
//				return resultJson;
//			}
			
			if(!StringUtil.isBlank(result)){
				//解密
				String resContent = YZTSercurityUtil.decryptResponseComm(result, TL_PARTNER_MD5,QFANG_PARTNER_PRIVATE_KEY);
				
				logger.info(">>>>>>reApply 明文 resContent:"+resContent);
				resContent = URLDecoder.decode(resContent,"UTF-8");
				logger.info(">>>>>>notify 明文转码后resContent::"+resContent);
				
				JSONObject respMap = JSON.parseObject(resContent);

				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", respMap.get("status"));
				resultJson.put("msg",respMap.getString("msg"));
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", respMap.get("loan_id"));
				dataJson.put("err_code", respMap.get("err_code"));
				resultJson.put("data", dataJson);
				
				logger.info(">>>>>>reApply 整合格式后:"+resultJson);
			}
		}catch(Exception e){
			logger.error(">>>>>>reApply error",e);
			return null;
		}finally {
			destroyFactory(clientFactoryPartner);
			destroyFactory(clientFactoryCus);
			destroyFactory(clientFactorySys);
			destroyFactory(clientFactoryWorkflow);
		}
		return resultJson;
	}

	
	/**
	 * 关闭
	 */
	public JSONObject close(ProjectPartnerDto projectPartnerDto,HttpServletRequest request) throws Exception {
		JSONObject resultJson = null;
		try{
			//封装请求参数=============================================
			JSONObject paramJson = new JSONObject();
			paramJson.put("loan_id", projectPartnerDto.getLoanId());		//业务流水号
			paramJson.put("inst_code", TL_PARTNER_PARAM_INST_CODE);			//机构代码-IFSP给资产方分配的机构代码
			paramJson.put("add_user", TL_PARTNER_PARAM_ADD_USER);			//录入人
			
			logger.info(">>>>>>close paramJson:"+paramJson.toString());
			//参数加密
			List<HttpRequestParam> paramList= YZTSercurityUtil.encryptParamsComm(paramJson.toString(), TL_PARTNER_MD5, TL_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE);
			
			HttpRequestParam signMethodParam = new HttpRequestParam("sign_method","md5");
			HttpRequestParam timestampParam = new HttpRequestParam("timestamp",DateUtils.dateToFormatString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			HttpRequestParam formatParam = new HttpRequestParam("format","json");
			HttpRequestParam vParam = new HttpRequestParam("v","1.0");
			HttpRequestParam methodParam = new HttpRequestParam("method","tlf.ifsp.busi.bps.Q206");
			paramList.add(signMethodParam);
			paramList.add(timestampParam);
			paramList.add(formatParam);
			paramList.add(vParam);
			paramList.add(methodParam);
			
			StringBuffer tempSb = new StringBuffer();
			for (HttpRequestParam indexObj : paramList) {
				tempSb.append(indexObj.getParaName()).append(":").append(indexObj.getParaValue()).append(",");
			}
			logger.info(">>>>>>close paramList:"+tempSb.toString());
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(TL_PARTNER_API_URL, paramList, "UTF-8");
			logger.info(">>>>>>close http result:"+result);
			//======================测试=====================================
//			if(true){
//				//测试成功
//				resultJson = new JSONObject();
//				resultJson.put("status", 0);
//				resultJson.put("msg", 1);
//				JSONObject dataJson = new JSONObject();
//				dataJson.put("loan_id", 11111);
//				dataJson.put("err_code", 0);
//				resultJson.put("data", dataJson);
//				return resultJson;
//			}
			
			if(!StringUtil.isBlank(result)){
				//解密
				String resContent = YZTSercurityUtil.decryptResponseComm(result, TL_PARTNER_MD5,QFANG_PARTNER_PRIVATE_KEY);
				
				logger.info(">>>>>>close 明文 resContent:"+resContent);
				resContent = URLDecoder.decode(resContent,"UTF-8");
				logger.info(">>>>>>notify 明文转码后resContent::"+resContent);
				
				JSONObject respMap = JSON.parseObject(resContent);

				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", respMap.get("status"));
				resultJson.put("msg",respMap.getString("msg"));
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", respMap.get("loan_id"));
				dataJson.put("err_code", respMap.get("err_code"));
				resultJson.put("data", dataJson);
				
				logger.info(">>>>>>close 整合格式后:"+resultJson);
			}
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
 
			//封装请求参数=============================================
			JSONObject paramJson = new JSONObject();
			paramJson.put("loan_id", projectPartnerDto.getLoanId());		//业务流水号
			paramJson.put("inst_code", TL_PARTNER_PARAM_INST_CODE);			//机构代码-IFSP给资产方分配的机构代码
			paramJson.put("add_user", TL_PARTNER_PARAM_ADD_USER);			//录入人
			
			logger.info(">>>>>>loanApply paramJson:"+paramJson.toString());
			//参数加密
			List<HttpRequestParam> paramList= YZTSercurityUtil.encryptParamsComm(paramJson.toString(), TL_PARTNER_MD5, TL_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE);
			
			HttpRequestParam signMethodParam = new HttpRequestParam("sign_method","md5");
			HttpRequestParam timestampParam = new HttpRequestParam("timestamp",DateUtils.dateToFormatString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			HttpRequestParam formatParam = new HttpRequestParam("format","json");
			HttpRequestParam vParam = new HttpRequestParam("v","1.0");
			HttpRequestParam methodParam = new HttpRequestParam("method","tlf.ifsp.busi.bps.Q203");
			paramList.add(signMethodParam);
			paramList.add(timestampParam);
			paramList.add(formatParam);
			paramList.add(vParam);
			paramList.add(methodParam);
			
			StringBuffer tempSb = new StringBuffer();
			for (HttpRequestParam indexObj : paramList) {
				tempSb.append(indexObj.getParaName()).append(":").append(indexObj.getParaValue()).append(",");
			}
			logger.info(">>>>>>loanApply paramList:"+tempSb.toString());
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(TL_PARTNER_API_URL, paramList, "UTF-8");
			logger.info(">>>>>>loanApply http result:"+result);
			//======================测试=====================================
//			if(true){
//				//测试成功
//				resultJson = new JSONObject();
//				resultJson.put("status", 0);
//				resultJson.put("msg", 1);
//				JSONObject dataJson = new JSONObject();
//				dataJson.put("loan_id", 11111);
//				dataJson.put("err_code", 0);
//				resultJson.put("data", dataJson);
//				return resultJson;
//			}
			
			if(!StringUtil.isBlank(result)){
				//解密
				String resContent = YZTSercurityUtil.decryptResponseComm(result, TL_PARTNER_MD5,QFANG_PARTNER_PRIVATE_KEY);
				
				logger.info(">>>>>>loanApply 明文 resContent:"+resContent);
				resContent = URLDecoder.decode(resContent,"UTF-8");
				logger.info(">>>>>>notify 明文转码后resContent::"+resContent);
				JSONObject respMap = JSON.parseObject(resContent);

				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", respMap.get("status"));
				resultJson.put("msg",respMap.getString("msg"));
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", respMap.get("loan_id"));
				dataJson.put("err_code", respMap.get("err_code"));
				resultJson.put("data", dataJson);
				
				logger.info(">>>>>>loanApply 整合格式后:"+resultJson);
			}
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
		BaseClientFactory clientFactoryPartner = null;
		try{
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//资金项目信息
			ProjectPartner queryPartner = new ProjectPartner();
			queryPartner.setPid(projectPartnerDto.getPid());
			queryPartner.setPartnerNo(projectPartnerDto.getPartnerNo());
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			
			//封装请求参数=============================================
			JSONObject paramJson = new JSONObject();
			paramJson.put("loan_id", projectPartnerDto.getLoanId());		//业务流水号
			paramJson.put("inst_code", TL_PARTNER_PARAM_INST_CODE);			//机构代码-IFSP给资产方分配的机构代码
			paramJson.put("add_user", TL_PARTNER_PARAM_ADD_USER);			//录入人
			paramJson.put("busi_dt", TL_PARTNER_PARAM_ADD_USER);			//还款日期
			paramJson.put("type", tempPartner.getRepaymentRepurchaseType());	//还款方式
			
			paramJson.put("busi_dt", tempPartner.getRefundDate());					//还款日期
			paramJson.put("principal", (int)(tempPartner.getRefundLoanAmount()*100));	//还款本金
			paramJson.put("interest", (int)(tempPartner.getRefundXifee()*100));		//还款利息
			paramJson.put("remark", projectPartnerDto.getRemark() == null ? "" : projectPartnerDto.getRemark().trim() );				//还款利息
 
			//还款凭证
			if(!StringUtil.isBlank(projectPartnerDto.getRepaymentVoucherPath())){
				
				JSONObject repay_voucherJson = getRequestFileJsonAndPackage(projectPartnerDto.getRepaymentVoucherPath(), tempPartner.getProjectId(), tempPartner.getPartnerNo(),tempPartner.getPid(), getServerBaseUrl(request));
				if(repay_voucherJson.get("payment") != null){
					paramJson.put("repay_voucher", repay_voucherJson.getJSONObject("payment"));
				}
			}
			//利息凭证
			if(!StringUtil.isBlank(projectPartnerDto.getXiFeeVoucherPath())){
				JSONObject interest_voucherJson = getRequestFileJsonAndPackage(projectPartnerDto.getXiFeeVoucherPath(), tempPartner.getProjectId(), tempPartner.getPartnerNo(),tempPartner.getPid(), getServerBaseUrl(request));
				if(interest_voucherJson.get("payment") != null){
					paramJson.put("interest_voucher", interest_voucherJson.getJSONObject("payment"));
				}
			}
			
			//资产方回购时必填,贷款人还款时可不填
			if(Constants.REPAYMENT_REPURCHASE_TYPE_2 == tempPartner.getRepaymentRepurchaseType()){
				paramJson.put("pay_acct_no", tempPartner.getPayAcctNo() == null ? "" : tempPartner.getPayAcctNo().trim() );	//还款账号
				paramJson.put("pay_acct_name",tempPartner.getPayAcctName() == null ? "" : tempPartner.getPayAcctName().trim() );		//还款账户名称
				paramJson.put("pay_bank_no", "");								//还款开户行号
				paramJson.put("pay_bank_name", tempPartner.getPayBankName());		//还款开户行名
			}
			
			logger.info(">>>>>>refundApply paramJson:"+paramJson.toString());
			//参数加密
			List<HttpRequestParam> paramList= YZTSercurityUtil.encryptParamsComm(paramJson.toString(), TL_PARTNER_MD5, TL_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE);
			
			HttpRequestParam signMethodParam = new HttpRequestParam("sign_method","md5");
			HttpRequestParam timestampParam = new HttpRequestParam("timestamp",DateUtils.dateToFormatString(new Date(), "yyyy-MM-dd HH:mm:ss"));
			HttpRequestParam formatParam = new HttpRequestParam("format","json");
			HttpRequestParam vParam = new HttpRequestParam("v","1.0");
			HttpRequestParam methodParam = new HttpRequestParam("method","tlf.ifsp.busi.bps.Q208");
			paramList.add(signMethodParam);
			paramList.add(timestampParam);
			paramList.add(formatParam);
			paramList.add(vParam);
			paramList.add(methodParam);
			
			StringBuffer tempSb = new StringBuffer();
			for (HttpRequestParam indexObj : paramList) {
				tempSb.append(indexObj.getParaName()).append(":").append(indexObj.getParaValue()).append(",");
			}
			logger.info(">>>>>>refundApply paramList:"+tempSb.toString());
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(60000,60000);
			String result = httpUtils.executeHttpPost(TL_PARTNER_API_URL, paramList, "UTF-8");
			logger.info(">>>>>>refundApply http result:"+result);
			//======================测试=====================================
//			if(true){
//				//测试成功
//				resultJson = new JSONObject();
//				resultJson.put("status", 0);
//				resultJson.put("msg", 1);
//				JSONObject dataJson = new JSONObject();
//				dataJson.put("loan_id", 11111);
//				dataJson.put("err_code", 0);
//				resultJson.put("data", dataJson);
//				return resultJson;
//			}
			
			if(!StringUtil.isBlank(result)){
				//解密
				String resContent = YZTSercurityUtil.decryptResponseComm(result, TL_PARTNER_MD5,QFANG_PARTNER_PRIVATE_KEY);
				
				logger.info(">>>>>>refundApply 明文 resContent:"+resContent);
				resContent = URLDecoder.decode(resContent,"UTF-8");
				logger.info(">>>>>>notify 明文转码后resContent::"+resContent);
				
				JSONObject respMap = JSON.parseObject(resContent);

				//转换格式
				resultJson = new JSONObject();
				resultJson.put("status", respMap.get("status"));
				resultJson.put("msg",respMap.getString("msg"));
				
				JSONObject dataJson = new JSONObject();
				dataJson.put("loan_id", respMap.get("loan_id"));
				dataJson.put("err_code", respMap.get("err_code"));
				resultJson.put("data", dataJson);
				logger.info(">>>>>>refundApply 整合格式后:"+resultJson);
			}
		}catch(Exception e){
			logger.error(">>>>>>refundApply error",e);
			return null;
		}finally {
			destroyFactory(clientFactoryPartner);
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
	private JSONObject getRequestFileJsonAndPackage(String requestFileIds,int projectId,String partnerNo,int partnerId,String serverUrl){
		JSONObject filesJson = new JSONObject();
		
		if(StringUtil.isBlank(requestFileIds)){
			return filesJson;
		}
		BaseClientFactory clientFactoryPartnerFile = null;
		List<ProjectPartnerFile> fileList = new ArrayList<ProjectPartnerFile>();
		try {
			clientFactoryPartnerFile = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
			ProjectPartnerFileService.Client clientPartnerFile =(ProjectPartnerFileService.Client) clientFactoryPartnerFile.getClient();
			
			ProjectPartnerFile query = new ProjectPartnerFile();
			query.setProjectId(projectId);
			query.setPartnerNo(partnerNo);
			query.setPartnerId(partnerId);
			query.setPidList(CommonUtil.getSepStrtoList(requestFileIds, "Integer", ","));
			fileList = clientPartnerFile.findAllProjectPartnerFile(query);
			
			//先定义9种类型文件，并封装list，再进行
			List<ProjectPartnerFile> list1 = null;	//客户基本信息
			List<ProjectPartnerFile> list2 = null;	//交易方基本信息	
			List<ProjectPartnerFile> list3 = null;	//房产信息
			List<ProjectPartnerFile> list4 = null;	//交易信息
			List<ProjectPartnerFile> list5 = null;	//还款来源信息
			List<ProjectPartnerFile> list6 = null;	//征信信息
			List<ProjectPartnerFile> list7 = null;	//合同协议信息
			List<ProjectPartnerFile> list8 = null;	//其他材料
			List<ProjectPartnerFile> list9 = null;	//居间服务协议
			
			//类型-打包的文件路径
			Map<String,Object> typeUrlMap = new HashMap<String,Object>();
			
			ProjectPartnerFile tempFile = null;
			for (int i= 0 ; i< fileList.size() ; i++ ) {
				tempFile = fileList.get(i);
				//客户基本信息
				if(Constants.XIAOYING_ACCESSORY_TYPE_1.equals(tempFile.getAccessoryType()) ){
					if(list1 == null){
						list1 = new ArrayList<ProjectPartnerFile>();
					}
					list1.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_2.equals(tempFile.getAccessoryType()) ){
					if(list2 == null){
						list2 = new ArrayList<ProjectPartnerFile>();
					}
					list2.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_3.equals(tempFile.getAccessoryType()) ){
					if(list3 == null){
						list3 = new ArrayList<ProjectPartnerFile>();
					}
					list3.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_4.equals(tempFile.getAccessoryType()) ){
					if(list4 == null){
						list4 = new ArrayList<ProjectPartnerFile>();
					}
					list4.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_5.equals(tempFile.getAccessoryType()) ){
					if(list5 == null){
						list5 = new ArrayList<ProjectPartnerFile>();
					}
					list5.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_6.equals(tempFile.getAccessoryType()) ){
					if(list6 == null){
						list6 = new ArrayList<ProjectPartnerFile>();
					}
					list6.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_7.equals(tempFile.getAccessoryType()) ){
					if(list7 == null){
						list7 = new ArrayList<ProjectPartnerFile>();
					}
					list7.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_8.equals(tempFile.getAccessoryType()) ){
					if(list8 == null){
						list8 = new ArrayList<ProjectPartnerFile>();
					}
					list8.add(tempFile);
				}else if(Constants.XIAOYING_ACCESSORY_TYPE_9.equals(tempFile.getAccessoryType()) ){
					if(list9 == null){
						list9 = new ArrayList<ProjectPartnerFile>();
					}
					list9.add(tempFile);
				}
			}
			//打包封装参数
			if(list1 != null){
				filesJson.put("loaner_base_info", getPackageFileUrl(list1,projectId,serverUrl)); 
			}
			if(list2 != null){
				filesJson.put("counterparty_base_info", getPackageFileUrl(list2,projectId,serverUrl)); 
			}
			if(list3 != null){
				filesJson.put("house_info", getPackageFileUrl(list3,projectId,serverUrl)); 
			}
			if(list4 != null){
				filesJson.put("transation_infoarray", getPackageFileUrl(list4,projectId,serverUrl)); 
			}
			if(list5 != null){
				filesJson.put("payment", getPackageFileUrl(list5,projectId,serverUrl)); 
			}
			if(list6 != null){
				filesJson.put("credit_info", getPackageFileUrl(list6,projectId,serverUrl)); 
			}
			if(list7 != null){
				filesJson.put("pact_info", getPackageFileUrl(list7,projectId,serverUrl)); 
			}
			if(list8 != null){
				filesJson.put("other_info", getPackageFileUrl(list8,projectId,serverUrl)); 
			}
			if(list9 != null){
				filesJson.put("agreement_info", getPackageFileUrl(list9,projectId,serverUrl)); 
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
		return filesJson;
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
	public JSONObject getPackageFileUrl(List<ProjectPartnerFile> fileList,int projectId ,String serverUrl) throws Exception{
		
		JSONObject typeUrlJson = new JSONObject();
		//key:子类型，value:多个url，以逗号隔开
		Map<String,String> fileMap = new HashMap<String,String>();
		String tempFileUrl = null;
		for (ProjectPartnerFile file : fileList) {
			if(fileMap.containsKey(file.getAccessoryChildType())){
				tempFileUrl = fileMap.get(file.getAccessoryChildType());
				fileMap.put(file.getAccessoryChildType(), tempFileUrl+","+file.getFileUrl());
			}else{
				fileMap.put(file.getAccessoryChildType(), file.getFileUrl());
			}
		}
		
		//时间格式
		String nowTimeStr = DateUtils.dateToFormatString(new Date(), "yyyyMMddHHmmss");
		
		if(fileMap.size() > 0 ){
			String type = null;
			String zipUrl= null;		//相对路径
			String zipLocalUrl= null;	//服务绝对路径
			String zipFileName = null;	//文件名
			
			String [] fileUrls = null;
			String temp_uploadFilePath =getUploadFilePath();
			File temp_file = null;
			byte[] buffer = new byte[1024];   
			
			//循环MAP子类型，并进行打包分类
			for (Map.Entry<String, String> entry : fileMap.entrySet()) {
				type = entry.getKey();
				zipUrl = temp_uploadFilePath+"/partner/"+projectId+"/tl/";
				zipLocalUrl = CommonUtil.getRootPath() + zipUrl;
				zipFileName =type+"_"+nowTimeStr+".zip";
				fileUrls = entry.getValue().split(",");
				
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
				
				List<File> temp_fileList = new ArrayList<File>();
				boolean isHasZip = false;		//是否己打包
				int fileIndex = 1;
				for (int i = 0; i < fileUrls.length; i++) {
					temp_file = new File(CommonUtil.getRootPath()+fileUrls[i]);
					if(temp_file.exists()){
						temp_fileList.add(temp_file);
						
						FileInputStream fis = new FileInputStream(temp_file);  
						out.putNextEntry(new ZipEntry(type+"_"+fileIndex+"."+FileUtil.getFileType(temp_file.getName())));   
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
					typeUrlJson.put(type, serverUrl+"/"+zipUrl+zipFileName);
					logger.info(">>>>>>getPackageFileUrl package zip url:"+serverUrl+"/"+zipUrl+zipFileName);
				}
			}
		}
		return typeUrlJson;
	}

	/**
	 * 回调通知
	 */
	public JSONObject notify(JSONObject paramJson, HttpServletRequest request) throws Exception {
		try{
			String key = paramJson.getString("key");
			String content = paramJson.getString("content");
			String sign = paramJson.getString("sign");
			String partner = paramJson.getString("partner");
			String method = paramJson.getString("method");
			
			//先解密content
			String resContent =YZTSercurityUtil.decryptResponseComm(paramJson.toString(), TL_PARTNER_MD5, QFANG_PARTNER_PRIVATE_KEY);
			
			logger.info(">>>>notify 明文resContent:"+resContent);
			
			if(StringUtil.isBlank(resContent)){
				return returnResult(null, "解密失败", 1, 1,null);
			}
			resContent = URLDecoder.decode(resContent,"UTF-8");
			logger.info(">>>>>>notify 明文转码后resContent::"+resContent);
			
			//业务参数
			JSONObject contentJson = JSONObject.parseObject(resContent);
			
			if("QF_NOTIFY_APPROVE".equals(method)){
				//申请审批回调
				return notify_approve(contentJson);
			}else if("QF_NOTIFY_LOAN".equals(method)){
				//放款申请结果回调
				return notify_loan(contentJson, request);
			}else if("QF_NOTIFY_REFUND".equals(method)){
				//还款申请结果回调
				return notify_refund(contentJson);
			}else if("QF_QUERY_INLOAN_STATUS".equals(method)){
				//贷中业务状态查询
				return notify_inloan_status(contentJson,request);
			}else{
				return returnResult(null, "服务方法不存在", 1, 1,null);
			}
			
		}catch(Exception e){
			logger.error(">>>>>>notify error",e);
		}
		return returnResult(null, "操作失败", 1, 1,null);
	}
	
	
	
	/**
	 * 回调通知-申请审批回调
	 */
	public JSONObject notify_approve(JSONObject contentJson) throws Exception   {
		BaseClientFactory clientFactoryPartner = null;
		try{
			//判断参数是否为空
			if(contentJson.get("loan_id") == null || contentJson.get("err_code") == null ||
					contentJson.get("type") == null){
				return returnResult(null, "参数不存在", 1, 1,null);
			}
			String loan_id = contentJson.getString("loan_id");		//审批id
			int err_code = contentJson.getIntValue("err_code");		//申请状态	0：审批通过 1：不通过 2：驳回补件
			int type = contentJson.getIntValue("type");				//1：申请 2：复议申请
			// 审批放款有效期 yyyy-MM-dd 审批通过不为空
			String loan_effe_date = contentJson.get("loan_effe_date") == null ? "" : contentJson.getString("loan_effe_date");
			//备注结果
			String remark = contentJson.getString("remark") == null ? "" : contentJson.getString("remark");
			
			if(err_code == 0  && ( StringUtil.isBlank(loan_effe_date) )){
				return returnResult(loan_id, "loan_effe_date参数错误", 1, 1,null);
			}
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//判断当前项目的状态
			ProjectPartner projectPartner = new ProjectPartner(); 
			projectPartner.setLoanId(loan_id); 
			projectPartner.setPartnerNo(PartnerConstant.PARTNER_TL);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(projectPartner);
			
			//数据不存在
			if(tempPartner == null || tempPartner.getPid() == 0){
				return returnResult(loan_id, "信息不存在", 0, 99,null);
			}
			
			if(Constants.APPROVAL_STATUS_2 == tempPartner.getApprovalStatus()){
				return returnResult(loan_id, "审核己通过，不允许重复操作", 0, 2,null);
			}else if(Constants.APPROVAL_STATUS_1 != tempPartner.getApprovalStatus()){
				return returnResult(loan_id, "未提交申请，不允许操作", 0, 2,null);
			}
			
			ProjectPartner updatePartner = new ProjectPartner();
			//当前时间
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			//审核状态
			int approvalStatus = 0;
			if(err_code == 0){
				approvalStatus = Constants.APPROVAL_STATUS_2;//审核通过 
				updatePartner.setLoanEffeDate(loan_effe_date);
				//审核通过，默认确认要款状态为通过
				updatePartner.setApproveMoney(tempPartner.getLoanAmount());
				updatePartner.setConfirmLoanStatus(Constants.CONFIRM_LOAN_STATUS_3);
				updatePartner.setConfirmLoanDays(tempPartner.getApplyDate());
				updatePartner.setConfirmLoanMoney(tempPartner.getLoanAmount());
				updatePartner.setConfirmLoanRequestTime(nowTime.toString());
				updatePartner.setConfirmLoanResultTime(nowTime.toString());
				
			}else if(err_code == 1){
				approvalStatus =Constants.APPROVAL_STATUS_3;//审核未通过 
			}else if(err_code == 2){
				approvalStatus =Constants.APPROVAL_STATUS_4;//驳回补件
			}else{
				return returnResult(null, "err_code参数错误", 0, 1,null);
			}
			
			updatePartner.setPid(tempPartner.getPid());
			updatePartner.setApprovalComment(remark); 
			updatePartner.setApprovalStatus(approvalStatus);
			updatePartner.setApprovalTime(nowTime.toString()); 	//设置审批时间
			
			//更新项目机构合作表状态
			int rows = clientPartner.updateProjectPartner(updatePartner);
			
			if(rows == 0){
				return returnResult(loan_id, "操作失败", 0, 1,null);
			}
			
			//更新审核记录表状态
			PartnerApprovalRecord updateRecord = new PartnerApprovalRecord();
			updateRecord.setReApplyReason(tempPartner.getReApplyReason());
			updateRecord.setPartnerId(tempPartner.getPid());
			updateRecord.setLoanId(loan_id);
			updateRecord.setApprovalComment(remark);
			updateRecord.setApprovalTime(nowTime.toString());
			updateRecord.setApprovalStatus(approvalStatus);
			updateRecord.setIsNotify(Constants.IS_NOTIFY_2);
			
			if(approvalStatus == Constants.APPROVAL_STATUS_2){
				updateRecord.setApproveMoney(tempPartner.getLoanAmount());
			}else{
				updateRecord.setApproveMoney(0);
			}
			
			updateRecord.setQueryIsNotify(Constants.IS_NOTIFY_1);
			clientPartner.updatePartnerApprovalRecord(updateRecord);
			
			return returnResult(loan_id, "操作成功", 0, 0,null);
		}catch(Exception e){
			logger.error(">>>>>>notify error",e);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>notify_approve clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		return returnResult(null, "操作失败", 1, 1,null);
	}
	
	
	/**
	 * 回调通知-放款回调
	 */
	public JSONObject notify_loan(JSONObject contentJson ,HttpServletRequest request) throws Exception   {
		BaseClientFactory clientFactoryPartner = null;
		String loan_id ="";
		try{
	
			//判断参数是否为空
			if(contentJson.get("loan_id") == null || contentJson.get("err_code") == null ){
				return returnResult(null, "参数错误", 1, 1,null);
			}
			loan_id = contentJson.getString("loan_id");		//审批id
			int err_code = contentJson.getIntValue("err_code");		//申请状态 ：0：放款成功 1：放款失败
			
			// 审批放款有效期 yyyy-MM-dd 审批通过不为空
			String loan_date = contentJson.get("loan_date") == null ? "" : contentJson.getString("loan_date");
			// 放款凭证
			String loan_file_url = contentJson.get("loan_file_url") == null ? "" : contentJson.getString("loan_file_url");
			//备注结果
			String remark = contentJson.getString("remark") == null ? "" : contentJson.getString("remark");
			
			if(err_code == 0 ){
				if(StringUtil.isBlank(loan_date)){
					return returnResult(loan_id, "loan_date参数错误", 1, 1,null);
				}else if(StringUtil.isBlank(loan_file_url)){
					return returnResult(loan_id, "loan_file_url参数错误", 1, 1,null);
				}
			}
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//判断当前项目的状态
			ProjectPartner queryPartner = new ProjectPartner(); 
			queryPartner.setLoanId(loan_id); 
			queryPartner.setPartnerNo(PartnerConstant.PARTNER_TL);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(queryPartner);
			
			//数据不存在
			if(tempPartner == null || tempPartner.getPid() == 0){
				return returnResult(loan_id, "信息不存在", 0, 99,null);
			}else if(Constants.LOAN_STATUS_4 ==  tempPartner.getLoanStatus()){
				return returnResult(loan_id, "己放款成功，不允许操作", 0, 2,null);
			}else if(Constants.LOAN_STATUS_2 !=  tempPartner.getLoanStatus() ){
				return returnResult(loan_id, "未申请放款，不允许操作", 0, 2,null);
			}
	 
			ProjectPartner updatePartner = new ProjectPartner();
			updatePartner.setPid(tempPartner.getPid());
			//审核状态
			int loanStatus = 0;
			if(err_code == 0){
				loanStatus = Constants.LOAN_STATUS_4;//放款成功
				updatePartner.setPartnerLoanDate(loan_date);
			}else if(err_code == 1){
				loanStatus = Constants.LOAN_STATUS_5;//放款失败
			}else{
				return returnResult(null, "err_code参数错误", 0, 1,null);
			}
			//当前时间
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			updatePartner.setLoanStatus(loanStatus);
			updatePartner.setLoanResultTime(nowTime.toString());
			updatePartner.setLoanRemark(remark);
			
			//放款凭证更新到Q房系统
			if(!StringUtil.isBlank(loan_file_url)){
				try{
					ProjectPartnerFileService.Client clientFile = (ProjectPartnerFileService.Client)
							getClient(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
					
					JSONObject loan_file_Json = JSONObject.parseObject(loan_file_url);
					String loan_file_temp = "";
					int pid_file_temp = 0 ;
					String partner_loan_file ="";
					for (String key : loan_file_Json.keySet()) {
						
						loan_file_temp = loan_file_Json.getString(key);
						String [] fileArray = loan_file_temp.split("/");
						String fileName="notify_"+ fileArray[fileArray.length-1];
						String fileUrl =  getUploadFilePath()+"/partner/"+tempPartner.getProjectId()+"/tl/"+fileName;
						
						logger.info(">>>>>>notify_loan 下载文件：loan_file_temp："+loan_file_temp+"到fileUrl："+fileUrl);
						
						SFTPUtil sftpUtil = new SFTPUtil();
						sftpUtil.init(TL_SFTP_HOST, Integer.parseInt(TL_SFTP_PORT), TL_SFTP_USERNAME, TL_SFTP_PWD);
						sftpUtil.downFile(loan_file_temp,(CommonUtil.getRootPath()+fileUrl));
						sftpUtil.close();
						
						File tempFile = new File(CommonUtil.getRootPath()+fileUrl);
						
						ProjectPartnerFile projectPartnerFile = new ProjectPartnerFile();
						projectPartnerFile.setUpdateTime(nowTime.toString());
						projectPartnerFile.setProjectId(tempPartner.getProjectId());
						projectPartnerFile.setPartnerNo(tempPartner.getPartnerNo());
						projectPartnerFile.setFileName(FileUtil.getFileName(tempFile.getName()));
						projectPartnerFile.setFileSize((int)tempFile.length());
						projectPartnerFile.setFileType(FileUtil.getFileType(tempFile.getName()));
						projectPartnerFile.setStatus(Constants.COMM_YES);
						projectPartnerFile.setAccessoryType(Constants.XIAOYING_ACCESSORY_TYPE_4);
						projectPartnerFile.setAccessoryChildType(key);
						projectPartnerFile.setProjectId(tempPartner.getProjectId());
						projectPartnerFile.setFileUrl(fileUrl);
						projectPartnerFile.setRemark("回调系统下载");
						pid_file_temp = clientFile.addProjectPartnerFile(projectPartnerFile);
						
						if(StringUtil.isBlank(partner_loan_file)){
							partner_loan_file += pid_file_temp;
						}else{
							partner_loan_file += ","+pid_file_temp;
						}
					}
					updatePartner.setPartnerLoanFile(partner_loan_file);
					
				}catch(Exception e){
					logger.error(">>>>>>notify_refund error 下载文件出错",e);
				}
			}
			
			
			//更新项目机构合作表状态
			int rows = clientPartner.updateProjectPartner(updatePartner);
			if(rows == 0){
				return returnResult(loan_id, "操作失败", 0, 1,null);
			}
			return returnResult(loan_id, "操作成功", 0, 0,null);
		}catch(Exception e){
			logger.error(">>>>>>notify error",e);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>notify_approve clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		return returnResult(null, "操作失败", 1, 1,null);
	}
	
	/**
	 * 回调通知-还款回调
	 */
	public JSONObject notify_refund(JSONObject contentJson) throws Exception   {
		BaseClientFactory clientFactoryPartner = null;
		try{
			

			//判断参数是否为空
			if(contentJson.get("loan_id") == null || contentJson.get("err_code") == null ){
				return returnResult(null, "参数不存在", 1, 1,null);
			}
			String loan_id = contentJson.getString("loan_id");		//审批id
			int err_code = contentJson.getIntValue("err_code");		//申请状态 ：0：还款成功 1：还款失败
			
			// 实际还款到帐日
			String real_refund_date = contentJson.get("real_refund_date") == null ? "" : contentJson.getString("real_refund_date");
			// 到帐凭证
			String refund_file_url = contentJson.get("refund_file_url") == null ? "" : contentJson.getString("refund_file_url");
			// 应补利息金额
			int interests = contentJson.get("interests") == null ? 0 : contentJson.getIntValue("interests");
			//备注结果
			String remark = contentJson.getString("remark") == null ? "" : contentJson.getString("remark");
	//		String remark = contentJson.getString("remark") == null ? "" : URLDecoder.decode(contentJson.getString("remark"),"UTF-8");
			
			if(err_code == 0){
				if(StringUtil.isBlank(real_refund_date)){
					return returnResult(loan_id, "real_refund_date参数错误", 1, 1,null);
				}else if(StringUtil.isBlank(refund_file_url)){
					return returnResult(loan_id, "refund_file_url参数错误", 1, 1,null);
				}
			}
			
			clientFactoryPartner = getFactory(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			ProjectPartnerService.Client clientPartner =(ProjectPartnerService.Client) clientFactoryPartner.getClient();
			
			//判断当前项目的状态
			ProjectPartner projectPartner = new ProjectPartner(); 
			projectPartner.setLoanId(loan_id); 
			projectPartner.setPartnerNo(PartnerConstant.PARTNER_TL);
			ProjectPartner tempPartner = clientPartner.findProjectPartnerInfo(projectPartner);
			
			//数据不存在
			if(tempPartner == null || tempPartner.getPid() == 0){
				return returnResult(loan_id, "信息不存在", 0, 99,null);
			}else if(Constants.REPAYMENT_REPURCHASE_STATUS_3 ==  tempPartner.getRepaymentRepurchaseStatus()){
				return returnResult(loan_id, "己还款成功，不允许操作", 0, 2,null);
			}else if(Constants.REPAYMENT_REPURCHASE_STATUS_2 !=  tempPartner.getRepaymentRepurchaseStatus()){
				return returnResult(loan_id, "未申请还款，不允许操作", 0, 2,null);
			}
	 
			ProjectPartner updatePartner = new ProjectPartner();
			//审核状态
			int repaymentRepurchaseStatus = 0;
			
			if(err_code == 0){
				repaymentRepurchaseStatus = Constants.REPAYMENT_REPURCHASE_STATUS_3;//还款成功
			}else if(err_code == 1){
				repaymentRepurchaseStatus = Constants.REPAYMENT_REPURCHASE_STATUS_4;//还款失败
			}else{
				return returnResult(null, "err_code参数错误", 0, 1,null);
			}
			//当前时间
			Timestamp nowTime = new Timestamp(new Date().getTime()); 
			updatePartner.setPid(tempPartner.getPid());
			updatePartner.setRepaymentRepurchaseStatus(repaymentRepurchaseStatus);
			updatePartner.setRpResultTime(nowTime.toString());
			updatePartner.setRepaymentRepurchaseRemark(remark);
			
			updatePartner.setPartnerRealRefundDate(real_refund_date);
			updatePartner.setPartnerInterests(((double)interests)/100);
			
			//到帐凭证更新到Q房系统
			if(!StringUtil.isBlank(refund_file_url)){
				try{
					
					ProjectPartnerFileService.Client clientFile = (ProjectPartnerFileService.Client)
							getClient(BusinessModule.MODUEL_PARTNER, "ProjectPartnerFileService");
					
					JSONObject refund_file_Json = JSONObject.parseObject(refund_file_url);
					String refund_file_temp = "";
					int pid_file_temp = 0 ;
					String partner_refund_file ="";
					for (String key : refund_file_Json.keySet()) {
						
						refund_file_temp = refund_file_Json.getString(key);
						
						String [] fileArray = refund_file_temp.split("/");
						String fileName="notify_"+ fileArray[fileArray.length-1];
						String fileUrl =  getUploadFilePath()+"/partner/"+tempPartner.getProjectId()+"/tl/"+fileName;
						
						logger.info(">>>>>>notify_refund 下载文件：refund_file_temp："+refund_file_temp+"到fileUrl："+fileUrl);
						
						SFTPUtil sftpUtil = new SFTPUtil();
						sftpUtil.init(TL_SFTP_HOST, Integer.parseInt(TL_SFTP_PORT), TL_SFTP_USERNAME, TL_SFTP_PWD);
						sftpUtil.downFile(refund_file_temp,(CommonUtil.getRootPath()+fileUrl));
						sftpUtil.close();
						
						File tempFile = new File(CommonUtil.getRootPath()+fileUrl);
						
						ProjectPartnerFile projectPartnerFile = new ProjectPartnerFile();
						projectPartnerFile.setUpdateTime(nowTime.toString());
						projectPartnerFile.setProjectId(tempPartner.getProjectId());
						projectPartnerFile.setPartnerNo(tempPartner.getPartnerNo());
						projectPartnerFile.setFileName(FileUtil.getFileName(tempFile.getName()));
						projectPartnerFile.setFileSize((int)tempFile.length());
						projectPartnerFile.setFileType(FileUtil.getFileType(tempFile.getName()));
						projectPartnerFile.setStatus(Constants.COMM_YES);
						projectPartnerFile.setAccessoryType(Constants.XIAOYING_ACCESSORY_TYPE_5);
						projectPartnerFile.setAccessoryChildType(key);
						projectPartnerFile.setProjectId(tempPartner.getProjectId());
						projectPartnerFile.setFileUrl(fileUrl);
						projectPartnerFile.setRemark("回调系统下载");
						pid_file_temp = clientFile.addProjectPartnerFile(projectPartnerFile);
						
						if(StringUtil.isBlank(partner_refund_file)){
							partner_refund_file += pid_file_temp;
						}else{
							partner_refund_file += ","+pid_file_temp;
						}
					}
					updatePartner.setPartnerRefundFile(partner_refund_file);
					
				}catch(Exception e){
					logger.error(">>>>>>notify_refund error 下载文件出错",e);
				}
			}
			
			//更新项目机构合作表状态
			int rows = clientPartner.updateProjectPartner(updatePartner);
			if(rows == 0){
				return returnResult(loan_id, "操作失败", 0, 1,null);
			}
			return returnResult(loan_id, "操作成功", 0, 0,null);
		}catch(Exception e){
			logger.error(">>>>>>notify error",e);
		}finally {
			if (clientFactoryPartner != null) {
				try {
					clientFactoryPartner.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>notify_refund clientFactoryPartner destroy thrift error：",e);
				}
			}
		}
		return returnResult(null, "操作失败", 1, 1,null);
	}
	
	
	
	/**
	 * 回调通知-贷中业务状态查询
	 */
	public JSONObject notify_inloan_status(JSONObject contentJson,HttpServletRequest request) throws Exception   {
		
		BaseClientFactory clientFactoryHand = null;
		try{
			//判断参数是否为空
			if(contentJson.get("loan_id") == null ){
				return returnResult(null, "参数不存在", 1, 1,null);
			}
			String loan_id = contentJson.getString("loan_id");		//审批id
			
			ProjectPartnerService.Client client = (ProjectPartnerService.Client) 
					getClient(BusinessModule.MODUEL_PARTNER, "ProjectPartnerService");
			
			//判断当前项目的状态
			ProjectPartner projectPartner = new ProjectPartner(); 
			projectPartner.setLoanId(loan_id); 
			projectPartner.setPartnerNo(PartnerConstant.PARTNER_TL);
			ProjectPartner tempPartner = client.findProjectPartnerInfo(projectPartner);
			
			//数据不存在
			if(tempPartner == null || tempPartner.getPid() == 0){
				return returnResult(loan_id, "信息不存在", 0, 99,null);
			}
			
			//查出当前所有的
			List<HandleDynamicDTO> listStatus = client.findInLoanStatusByProjectId(tempPartner.getProjectId());
			
			JSONArray process_list_json = new JSONArray();
			
			if(listStatus != null && listStatus.size()> 0 ){
				
				
				clientFactoryHand = getFactory(BusinessModule.MODUEL_INLOAN, "BizHandleService");
				BizHandleService.Client clientHand =(BizHandleService.Client) clientFactoryHand.getClient();
	 
				JSONObject temp_process_json  =null;
				JSONObject temp_file  =null;
				JSONArray temp_file_list_json = null;
				HandleDynamicFileDTO queryHandleDynamicFile = new HandleDynamicFileDTO();
				String process_code = "";
				for (HandleDynamicDTO indexObj : listStatus) {
					
					temp_process_json = new JSONObject();
					
					if(indexObj.getHandleFlowId() == 1){			//发放贷款
						process_code = "01";	
					}else if(indexObj.getHandleFlowId() == 2){		//赎楼
						process_code = "02";
					}else if(indexObj.getHandleFlowId() == 3){		//取旧证时间
						process_code = "03";
					}else if(indexObj.getHandleFlowId() == 4){		//注销抵押登记
						process_code = "04";
					}else if(indexObj.getHandleFlowId() == 5){		//过户申请递证
						process_code = "05";
					}else if(indexObj.getHandleFlowId() == 6){		//领取房产证
						process_code = "06";	
					}else if(indexObj.getHandleFlowId() == 7){		//再抵押递件
						process_code = "07";
					}else if(indexObj.getHandleFlowId() == 10){		//回款
						process_code = "10";
					}
					
					temp_process_json.put("process_code", process_code);
					temp_process_json.put("process_name", indexObj.getTaskUserName());
					temp_process_json.put("end_date", indexObj.getFinishDate());
					temp_process_json.put("day", indexObj.getHandleDay());
					temp_process_json.put("operator", indexObj.getOperator());
					temp_process_json.put("remark", indexObj.getRemark());
					
			         // 根据办理动态id查询办理动态文件列表
					queryHandleDynamicFile.setHandleDynamicId(indexObj.getPid());
					queryHandleDynamicFile.setStatus(Constants.STATUS_ENABLED);
					queryHandleDynamicFile.setRows(100);
			        List<HandleDynamicFileDTO> list = clientHand.findAllHandleDynamicFile(queryHandleDynamicFile);
			        //贷中文件
			        temp_file_list_json = new JSONArray();
			        if(list != null && list.size() > 0){
			        	for (HandleDynamicFileDTO fileObj : list) {
			        		temp_file = new JSONObject();
			        		temp_file.put("name", fileObj.getFileName());
			        		temp_file.put("url", getServerBaseUrl(request)+"/"+fileObj.getFileUrl());
			        		temp_file_list_json.add(temp_file);
						}
			        }
			        temp_process_json.put("file", temp_file_list_json);
			        process_list_json.add(temp_process_json);
				}
			}
			
			//查询贷中的相关状态信息
			Map<String,Object> keyValMap = new HashMap<String,Object>();
			keyValMap.put("process_list", process_list_json);
			
			return returnResult(loan_id, "操作成功", 0, 0,keyValMap);
		}catch(Exception e){
			logger.error(">>>>>>notify error",e);
		}finally {
			if (clientFactoryHand != null) {
				try {
					clientFactoryHand.destroy();
				} catch (ThriftException e) {
					logger.error(">>>>>>notify_inloan_status clientFactoryPartner destroy thrift error：",e);
				}
			}
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
		JSONObject dataJson = new JSONObject();
		
		//请求状态成功
		if(status == 0){
			msg = msg == null  ? "操作成功" : msg ;
		}else{
			msg = msg == null  ? "操作失败" : msg ;
		}
 
		contentJson.put("status", status);
		contentJson.put("msg", msg);
		
		dataJson.put("loan_id", loan_id == null ? "" : loan_id);
		dataJson.put("err_code", err_code);
		
		//自定义key到  content中
		if(keyValMap != null && keyValMap.size() > 0){
			for (Map.Entry<String, Object> entry : keyValMap.entrySet()) {
				dataJson.put(entry.getKey(), entry.getValue());
			}
		}
		contentJson.put("data", dataJson);
		
		
		logger.info(">>>>>>returnResult 明文contentJson："+contentJson.toString());
		
		
		//测试不加密
		if("true".equals(IS_PARTNER_API_DEBUG)){
			return contentJson;
		}
		
		
		//返回结果加密
		JSONObject resultJson = YZTSercurityUtil.encryptParamsRespComm(contentJson.toString(),
				TL_PARTNER_MD5, TL_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE_QFANG);
		
		return resultJson;
	}


}
