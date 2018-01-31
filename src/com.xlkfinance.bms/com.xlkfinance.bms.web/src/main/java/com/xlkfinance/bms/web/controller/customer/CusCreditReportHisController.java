package com.xlkfinance.bms.web.controller.customer;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.constant.CusCreditConstant;
import com.xlkfinance.bms.common.constant.SysConfigConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfig;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfigService;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHis;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHisService;
import com.xlkfinance.bms.rpc.system.SysConfigService;
import com.xlkfinance.bms.web.api.dc.model.Header;
import com.xlkfinance.bms.web.api.dc.model.RequestParams;
import com.xlkfinance.bms.web.api.dc.model.ResponseParams;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.MD5Util;

/**
 * 客户征信报告历史控制器
 * @author chenzhuzhen
 * @date 2017年6月7日 下午5:32:55
 */
@Controller
@RequestMapping("/cusCreditReportHisController")
public class CusCreditReportHisController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CusCreditReportHisController.class);

	
	/**
	 * 客户征信报告列表页面
	 */
	@RequestMapping(value = "/creditReportHisList_index")
	public String creditReportHisList_index(){
		return "customer/credit/credit_report_his_list";
	}
	
	
	/**
	 * 查询客户征信报告列表
	 */
	@RequestMapping(value = "/getCreditReportHisList")
	@ResponseBody
	public void getCreditReportHisList(CusCreditReportHis cusCreditReportHis, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询客户片信列表，入参："+cusCreditReportHis);
		List<CusCreditReportHis> result = null;
		
		Map<String, Object> map = new HashMap<String, Object>();
		int total = 0;
		BaseClientFactory clientFactory = null;
		try {
			
			if(!StringUtil.isBlank(cusCreditReportHis.getBeginCreateTime())){
				cusCreditReportHis.setBeginCreateTime(cusCreditReportHis.getBeginCreateTime() + " 00:00:00");
			}
			if(!StringUtil.isBlank(cusCreditReportHis.getEndCreateTime())){
				cusCreditReportHis.setEndCreateTime(cusCreditReportHis.getEndCreateTime() + " 23:59:59");
			}
			
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusCreditReportHisService");
			CusCreditReportHisService.Client client =(CusCreditReportHisService.Client) clientFactory.getClient();
			result = client.selectList(cusCreditReportHis);
			total = client.selectTotal(cusCreditReportHis);
		} catch (Exception e) {
			logger.error("查询客户征信报告列表失败：" + e.getMessage());
		}finally {
			destroyFactory(clientFactory);
		}
		map.put("rows", result);
		map.put("total", total);
		outputJson(map, response);
	}
	
	
	
	/**
	 * 客户征信报告列表页面
	 */
	@RequestMapping(value = "/creditReportDetails_index")
	public String creditReportDetails_index(){
		return "customer/credit/credit_report_his_details";
	}
	
	/**
	 * 查询客户征信报告详情
	 */
	@RequestMapping(value = "/getCreditReportDetails")
	@ResponseBody
	public void getCreditReportDetails(Integer pid, HttpServletRequest request, HttpServletResponse response){
		logger.info("查询客户征信报告详情，入参："+pid);
		CusCreditReportHis cusCreditReportHis = null;
		BaseClientFactory clientFactory = null;
		try {
			clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusCreditReportHisService");
			CusCreditReportHisService.Client client =(CusCreditReportHisService.Client) clientFactory.getClient();
			cusCreditReportHis = client.getById(pid);
		} catch (Exception e) {
			logger.error("查询客户征信报告详情：" + e.getMessage());
		}finally {
			destroyFactory(clientFactory);
		}
		outputJson(cusCreditReportHis, response);
	}
	
	
	/**
	 * 报告查询首页
	 */
	@RequestMapping(value = "/queryReport_index")
	public String queryReport_index(){
		return "customer/credit/query_report";
	}
	
	
	/**
	 * 发送查询报告（接口调用）
	 * 先查询配置地址，再发送请求
	 */
	@RequestMapping(value = "/sentReport")
	@ResponseBody
	public void sentReport(CusCreditReportHis query ,HttpServletResponse response ){
		logger.info("发送查询报告（接口调用），query入参："+JSONObject.toJSONString(query));
		
		BaseClientFactory sysConfigFactory = getFactory(BusinessModule.MODUEL_SYSTEM, "SysConfigService");
		BaseClientFactory cusCreditFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusCreditReportHisService");
		BaseClientFactory creditFeeConfigFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CreditReportFeeConfigService");
		
		if(query.dataSource == 0){
			fillReturnJson(response, false, "参数信息来源错误");
			return ;
		}else if(query.getReportType() == 0){
			fillReturnJson(response, false, "参数报告类型错误");
			return ;
		}else if(StringUtil.isBlank(query.getQueryResonId()) || query.getQueryResonId().equals("0")){
			fillReturnJson(response, false, "参数查询原因错误");
			return ;
		}else if(StringUtil.isBlank(query.getQueryName())){
			fillReturnJson(response, false, "参数姓名错误");
			return ;
		}else if(StringUtil.isBlank(query.getQueryDocumentNo())){
			fillReturnJson(response, false, "参数证件号码错误");
			return ;
		}
		
		//个人反欺诈，要校验手机号码
		if(query.getReportType() == CusCreditConstant.ReportType.C_3.getCode() 
				||query.getReportType() == CusCreditConstant.ReportType.C_5.getCode()){
			if(StringUtil.isBlank(query.getQueryPhone())){
				fillReturnJson(response, false, "参数手机号码错误");
				return ;
			}
		}
		if(query.getReportType() == CusCreditConstant.ReportType.C_5.getCode()){
			if(StringUtil.isBlank(query.getQueryPbocStatus()) || query.getQueryPbocStatus().equals("0")){
				fillReturnJson(response, false, "参数有无人行征信错误");
				return ;
			}
		}
		
		try{
			SysConfigService.Client sysConfigClient =(SysConfigService.Client) sysConfigFactory.getClient();
			CusCreditReportHisService.Client cusCreditClient =(CusCreditReportHisService.Client) cusCreditFactory.getClient();
			CreditReportFeeConfigService.Client creditFeeConfig =(CreditReportFeeConfigService.Client) creditFeeConfigFactory.getClient();
			String dcServerUrl = null;
			try{
				dcServerUrl = sysConfigClient.getSysConfigValueByName(SysConfigConstant.DC_SERVER_URL);
			}catch(TApplicationException e){
				fillReturnJson(response, false, "数据中心接口地址未配置");
				return ;
			}
			if(StringUtil.isBlank(dcServerUrl)){
				fillReturnJson(response, false, "数据中心接口地址未配置");
				return ;
			}
			
			//--------------------------------发送接口begin------------------------------------
			//获取封装参数
			RequestParams requestParams = getApiParam(query);
			JSONObject requestParamsJson = (JSONObject) JSONObject.toJSON(requestParams);
			logger.info(">>>>>>sentReport requestParams:"+requestParamsJson.toJSONString());
			
			
			List<HttpRequestParam> bodyParamList = new ArrayList<HttpRequestParam>();
			HttpRequestParam bodyParam = new HttpRequestParam("requestDate",requestParamsJson.toString());
			bodyParamList.add(bodyParam);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(80000,80000);
			String resultHttpStr = httpUtils.executeHttpPost(dcServerUrl, bodyParamList, "UTF-8");
			httpUtils.closeConnection();
			
			//--------------------------------发送接口over------------------------------------
			
			logger.info(">>>>>>sentReport resultHttpStr:"+resultHttpStr);
			//处理响应参数
			if(StringUtil.isBlank(resultHttpStr)){
				fillReturnJson(response, false, "查询失败，请稍后再试");
				return ;
			}
			
			logger.info(">>>>>>sentReport resultHttpStr转化json");
			JSONObject resultHttpJson = JSONObject.parseObject(resultHttpStr);
			ResponseParams responseParams = (ResponseParams) JSONObject.parseObject(resultHttpJson.toString(), ResponseParams.class);

			if(responseParams == null ){
				fillReturnJson(response, false, "查询失败，请稍后再试");
				return ;
			}
			
			if(!CusCreditConstant.ErrorCode.SUCCESS.getCode().equals(responseParams.getErrorCode())){
				fillReturnJson(response, false, "查询失败，"+responseParams.getErrorMsg());
				return ;
			}
			
			//查询报告价格
			double unitPrice = 0 ; 
			try{
				CreditReportFeeConfig queryFeeConfig = new CreditReportFeeConfig();
				queryFeeConfig.setPage(-1);
				queryFeeConfig.setDataSource(query.getDataSource());
				queryFeeConfig.setReportType(query.getReportType());
				List<CreditReportFeeConfig> tempPriceList =creditFeeConfig.selectList(queryFeeConfig);
				
				if(!CollectionUtils.isEmpty(tempPriceList)){
					unitPrice =  tempPriceList.get(0).getUnitPrice();
				}
			}catch(Exception e){
				logger.error("查询接口费用配置错误，继续执行",e);
			}
			
			//是否重复查询，默认为否
			int isRepeat = Constants.COMM_NO;
			//查询状态
			Integer queryStatus = Constants.COMM_YES;
			
			if(responseParams.getHeader().getIsRepeat() != null){
				isRepeat = responseParams.getHeader().getIsRepeat();
			}
			
			//添加数据据更新------------------------------------
			Integer userId = getShiroUser().getPid();
			Timestamp nowTime = new Timestamp(new Date().getTime());
			CusCreditReportHis insert = new CusCreditReportHis();
			 
			 
			insert.setDataSource(query.getDataSource());
			insert.setReportType(query.getReportType());
			insert.setQueryResonId(query.getQueryResonId());
			insert.setQueryName(query.getQueryName());
			insert.setQueryDocumentNo(query.getQueryDocumentNo());
			insert.setQueryPhone(query.getQueryPhone());
			
			insert.setQueryStatus(queryStatus);
			insert.setQueryStatusMsg(responseParams.getErrorMsg());
			insert.setOperator(userId);
			insert.setCreateTime(nowTime.toString());
			insert.setCreator(userId);
			insert.setQueryResult(responseParams.getBody() == null ? "" : JSONObject.toJSONString(responseParams.getBody()));
			insert.setUnitPrice(unitPrice);
			insert.setIsRepeat(isRepeat);
			
			//"0"：请选择，不保存
			if(!"0".equals(query.getQueryPbocStatus())){
				insert.setQueryPbocStatus(query.getQueryPbocStatus());
			}
			
			Object reportNo ="";
			if(responseParams.getBody() != null){
				if(CusCreditConstant.DataSource.C_1.getCode() == query.getDataSource()){
					Map<String, Object> dataMap = JSONPath.paths(JSONObject.toJSON(responseParams.getBody()));
					reportNo = dataMap.get("/cisReport/reportID");
				}else if(CusCreditConstant.DataSource.C_3.getCode() == query.getDataSource()){
					Map<String, Object> dataMap = JSONPath.paths(JSONObject.toJSON(responseParams.getBody()));
					reportNo = dataMap.get("/scoreID");
				}
			}
			insert.setReportNo(reportNo == null ? "" : reportNo.toString());
			
			//返回主键
			Integer pid = cusCreditClient.insert(insert);
			Map<String,Object> body =new HashMap<String,Object>();
			if(pid >0){
				body.put("pid", pid);
				fillReturnJson(response, true, "操作成功!",body);
				return;
			}else {
				// 失败的话,做提示处理
				fillReturnJson(response, false, "新增失败，请重新操作!");
				return;
			}
		}catch(Exception e){
			logger.error("发送查询报告（接口调用）error", e);
			fillReturnJson(response, false, "系统错误!");
			return;
		}finally{
			destroyFactory(sysConfigFactory);
			destroyFactory(cusCreditFactory);
			destroyFactory(creditFeeConfigFactory);
		}
	}
	
	
	/**
	 * 查询客户征信报告记录详情
	 * @param pid
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getReportDetails")
	public String getReportDetails( Integer pid,HttpServletRequest request, HttpServletResponse response, ModelMap model) throws Exception {
		BaseClientFactory clientFactory = getFactory(BusinessModule.MODUEL_CUSTOMER, "CusCreditReportHisService");
		CusCreditReportHis cusCreditReportHis = null;
		try {
			CusCreditReportHisService.Client client = (CusCreditReportHisService.Client) clientFactory.getClient();
			cusCreditReportHis = client.getById(pid);
			
			logger.info(cusCreditReportHis.getQueryResult());
			
			model.put("cusCreditReportHis", cusCreditReportHis);
		} catch (Exception e) {
			logger.error("查询客户征信报告记录详情失败：" , e);
		} finally {
			destroyFactory(clientFactory);
		}
		
		if(CusCreditConstant.ReportType.C_1.getCode() == cusCreditReportHis.getReportType() ){
			//个人信用报告
			return "customer/credit/credit_report_grxy";
		}else if(CusCreditConstant.ReportType.C_2.getCode() == cusCreditReportHis.getReportType() ){
			//个人风险汇总
			return "customer/credit/credit_report_grfxhz";
		}else if(CusCreditConstant.ReportType.C_3.getCode() == cusCreditReportHis.getReportType() ){
			//个人反欺诈分析
			return "customer/credit/credit_report_grfqz";
		}else if(CusCreditConstant.ReportType.C_4.getCode() == cusCreditReportHis.getReportType()){
			//个人刑事案底
			return "customer/credit/credit_report_xsad";
		}else if(CusCreditConstant.ReportType.C_5.getCode() == cusCreditReportHis.getReportType()){
			//大数据信用评分
			return "customer/credit/credit_report_dsjxypf";
		}
		return "404";
	}
	
	
	/**
	 * 根据接口，封装对应请求参数
	 * 
	 * @param query
	 * @param jsonObject
	 * @return
	 */
	public RequestParams getApiParam(CusCreditReportHis query){
		
		RequestParams requestParams = new RequestParams();
		JSONObject body = new JSONObject();
		
		//-----------头部信息
		Header header = new Header();
		header.setOrgCode("bms_erp");
		header.setOrgUser("bms_erp");
		header.setOrgPwd("123456");
		//获取服务对应方法
		header.setServiceMethod(CusCreditConstant.ReportTypeServiceMethod.getValue(query.getReportType()));
		
		requestParams.setHeader(header);
		
		//----------body信息
		body.put("queryType", CusCreditConstant.ReportTypeQueryType.getValue(query.getReportType()));
		body.put("name", query.getQueryName());
		
		//优分数据 身份证 参数
		if(query.getReportType() == CusCreditConstant.ReportType.C_4.getCode()){
			body.put("idcard", query.getQueryDocumentNo());
		}else if(query.getReportType() == CusCreditConstant.ReportType.C_5.getCode()){
			//FICO大数据评分
			body.put("idCard", MD5Util.tltMd5(query.getQueryDocumentNo()));
			body.put("idCardNum", query.getQueryDocumentNo());
			body.put("mobile", MD5Util.tltMd5(query.getQueryPhone()));
			body.put("mobileNum", query.getQueryPhone());
			body.put("mobHeader", query.getQueryPhone().substring(0, 3));
			body.put("serviceCode", CusCreditConstant.ReportTypeQueryType.getValue(query.getReportType()));
			body.put("pboc", query.getQueryPhone());
			
		}else{
			body.put("documentNo", query.getQueryDocumentNo());
		}
		
		
		body.put("subreportIDs", CusCreditConstant.ReportTypeSubreportIDs.getValue(query.getReportType()));
		body.put("queryReasonID", query.getQueryResonId());
		body.put("refID", DateUtils.dateToString(new Date(),"yyyyMMddHHmmssSSSS"));
		//个人反欺诈，要校验手机号码
		if(query.getReportType() == CusCreditConstant.ReportType.C_3.getCode()){
			body.put("phone", query.getQueryPhone());
		}
		requestParams.setBody(body);
		
		return requestParams;
	}
}
