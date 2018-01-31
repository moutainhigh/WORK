package com.xlkfinance.bms.web.util;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;

/**
 * 接口工具类
 * @author chenzhuzhen
 * @date 2017年1月13日 上午11:43:55
 */
public class ApiUtil {
	
	private static Logger logger = LoggerFactory.getLogger(ApiUtil.class);
	
	
	/**
	 * 查档接口
	 * @param apiUrl	服务器地址
	 * @param queryType	查询方式 1:分户  2:分栋
	 * @param certType  产权证书类型   1：房地产权证书 2 ： 不动产权证书
	 * @param certNo	产权证书编号
	 * @param personInfo	权利人/身份证
	 * @param year			年 
	 * @return
	 * @throws Exception
	 */
	public static String submitCheckDoc(String apiUrl , Integer queryType ,Integer certType ,
			String certNo ,String personInfo,String year) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("queryType:"+queryType).append(",certType:").append(certType).append(",certNo:").append(certNo)
			.append(",personInfo:").append(personInfo).append(",year:").append(year);
		logger.info("submitCheckDoc param:"+sb.toString());
		
		if(StringUtil.isBlank(apiUrl)){
			return null;
		}
		if(queryType == null){
			return null;
		}
		if(certType == null){
			return null;
		}
		if(StringUtil.isBlank(certNo)){
			return null;
		}
		if(StringUtil.isBlank(personInfo)){
			return null;
		}
		if(Constants.CHECK_DOC_CERT_TYPE_2 == certType  && StringUtil.isBlank(year)){
			return null;
		}
		
		List<HttpRequestParam> paramsList = new ArrayList<HttpRequestParam>();
		HttpRequestParam param1 = new HttpRequestParam("queryType",queryType+"");
		HttpRequestParam param2 = new HttpRequestParam("certType",certType+"");
		HttpRequestParam param3 = new HttpRequestParam("certNo",certNo);
		HttpRequestParam param4 = new HttpRequestParam("personInfo",personInfo);
		HttpRequestParam param5 = new HttpRequestParam("year",StringUtil.isBlank(year) ? "" : year);
 
		paramsList.add(param1);
		paramsList.add(param2);
		paramsList.add(param3);
		paramsList.add(param4);
		paramsList.add(param5);
		
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.openConnection(50000,50000);
		//
		String apiResultStr = httpUtils.executeHttpPost(apiUrl, paramsList,"UTF-8");
		httpUtils.closeConnection();
		
		logger.info(">>>>>>submitCheckDoc apiResultStr:"+apiResultStr);
		return apiResultStr;
	}
	
	
	

}
