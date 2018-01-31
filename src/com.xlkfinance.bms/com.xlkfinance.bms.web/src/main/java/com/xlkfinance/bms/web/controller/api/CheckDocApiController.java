package com.xlkfinance.bms.web.controller.api;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDoc;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDocDetails;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDocService;
import com.xlkfinance.bms.web.controller.BaseController;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.PropertiesUtil;


/**
 * 查档api
 * @author chenzhuzhen
 * @date 2016年10月18日 上午10:23:48
 */
@Controller
@RequestMapping("/checkDocApi")
public class CheckDocApiController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(CheckDocApiController.class);
	
	/**
	 * 获取第三方配置url(从配置文件)
	 * @param configType 1:查档
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getConfigUrl")
	@ResponseBody
	public void getConfigUrl(Integer configType ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		try{
			if(configType == null){
				fillReturnJson(response, false, "参数为空!");
				return;
			}
			String url = null;
			//查档配置
			if(configType == 1){
				url = PropertiesUtil.getValue("api.check.doc.url");
			}
			
			if(StringUtils.isBlank(url)){
				fillReturnJson(response, false, "配置不存在");
				return;
			}
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("url", url);
			fillReturnJson(response, true, "操作成功", body);
		}catch(Exception e){
			logger.error(">>>>>>getConfigUrl error",e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}
	}
	
	
	
	/**
	 * 查存查档记录
	 * @param configType
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/saveHouseCheckDoc")
	@ResponseBody
	public void saveHouseCheckDoc(HouseCheckDoc houseCheckDoc ,HttpServletRequest request,HttpServletResponse response) throws Exception {
		
		BaseClientFactory clientFactoryCheckDoc = null;
		try{
				
			if (houseCheckDoc.getQueryType()  == 0 || houseCheckDoc.getCertType() == 0 || 
					StringUtil.isBlank(houseCheckDoc.getCertNo(),houseCheckDoc.getPersonInfo())) {
				fillReturnJson(response, false, "参数为空!");
				return;
			}
			if(houseCheckDoc.getCertType() == 2  && StringUtil.isBlank(houseCheckDoc.getYear())){
				fillReturnJson(response, false, "参数年份不能为空!");
				return;
			}
			
			//查档配置
			String	apiCheckDocUrl = PropertiesUtil.getValue("api.check.doc.url");
			if(StringUtils.isBlank(apiCheckDocUrl)){
				fillReturnJson(response, false, "配置不存在");
				return;
			}
			
			clientFactoryCheckDoc = getFactory(BusinessModule.MODUEL_CHECKAPI, "HouseCheckDocService");
			HouseCheckDocService.Client clientCheckDoc =(HouseCheckDocService.Client) clientFactoryCheckDoc.getClient();
			
			
			List<HttpRequestParam> paramsList = new ArrayList<HttpRequestParam>();
			HttpRequestParam param1 = new HttpRequestParam("queryType",houseCheckDoc.getQueryType()+"");
			HttpRequestParam param2 = new HttpRequestParam("certType",houseCheckDoc.getCertType()+"");
			HttpRequestParam param3 = new HttpRequestParam("certNo",houseCheckDoc.getCertNo());
			HttpRequestParam param4 = new HttpRequestParam("personInfo",houseCheckDoc.getPersonInfo());
			HttpRequestParam param5 = new HttpRequestParam("year",StringUtil.isBlank(houseCheckDoc.getYear()) ? "" : houseCheckDoc.getYear());
			
			paramsList.add(param1);
			paramsList.add(param2);
			paramsList.add(param3);
			paramsList.add(param4);
			paramsList.add(param5);
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(50000,50000);
			//
			String apiResultStr = httpUtils.executeHttpPost(apiCheckDocUrl, paramsList,"UTF-8");
			httpUtils.closeConnection();
			
			logger.info(">>>>>>getHouseCheckDoc apiResultStr:"+apiResultStr);
			
			if(StringUtil.isBlank(apiResultStr)){
				returnJsonOfMobile(response, false, "查询失败",ExceptionCode.MOBILE_SERVER_ERROR);
				return;
			}
			
			JSONObject apiResultJson = JSONObject.parseObject(apiResultStr);
			Map<String,Object> result = new HashMap<String,Object>();
			String msg = apiResultJson.getString("msg");
			String code = apiResultJson.getString("code");
			JSONObject dataJson = apiResultJson.getJSONObject("data");
			
			
			Integer userId =  this.getShiroUser().getPid();
			Map<String,String> queryMap = new HashMap<String,String>();
			queryMap.put("queryType", houseCheckDoc.getQueryType()+"");
			queryMap.put("certType", houseCheckDoc.getCertType()+"");
			queryMap.put("certNo", houseCheckDoc.getCertNo());
			queryMap.put("personInfo", houseCheckDoc.getPersonInfo());
			queryMap.put("year", houseCheckDoc.getYear());
			queryMap.put("userId", userId.toString());
			
			Date nowDate = new Date();
			
			//查询最后一条记录，判断时间查询
			HouseCheckDocDetails tempDoc = clientCheckDoc.getLastOneCheckDocDetails(queryMap);
			
			Timestamp nowTime = new Timestamp(new Date().getTime());
			houseCheckDoc.setUserId(userId);
			houseCheckDoc.setIsDel(Constants.COMM_NO);
			houseCheckDoc.setCreateTime(nowTime.toString());
			houseCheckDoc.setUpdateTime(nowTime.toString());
			
			HouseCheckDocDetails insertDetails = new HouseCheckDocDetails();
			String checkDocTime = DateUtils.dateToString(nowDate, "yyyy-MM-dd HH:mm");
			insertDetails.setCheckDocTime(checkDocTime);
			insertDetails.setCheckStatus(Constants.COMM_NO);
			String checkDocContent = "";
			
			if(tempDoc != null && tempDoc.getPid() > 0 ){
				houseCheckDoc.setPid(tempDoc.getPid());
			}
			if("ok".equals(code)){
				insertDetails.setCheckStatus(Constants.COMM_YES);
				if(dataJson != null && dataJson.get("result")!= null){
					checkDocContent = dataJson.getString("result");
					insertDetails.setCheckDocContent(checkDocContent);
				}
			}
			houseCheckDoc.setDetailsList(Arrays.asList(insertDetails));
			//添加到数据库,如果存在则不增加
			clientCheckDoc.addHouseCheckDoc(houseCheckDoc);
			
			if(!"ok".equals(code)){
				fillReturnJson(response, false, StringUtil.isBlank(msg)?"查询失败":msg);
				return;
			}
			
			Map<String,Object> body = new HashMap<String,Object>();
			body.put("checkDocTime", checkDocTime);
			body.put("checkDocContent", checkDocContent);
			
			fillReturnJson(response, true, "操作成功", body);
			return ;
			
 
		}catch(Exception e){
			logger.error(">>>>>>saveHouseCheckDoc error",e);
			fillReturnJson(response, false, "操作失败,请重新操作!");
		}
	}
	
	
	
	
	

}
