package com.xlkfinance.bms.web.api.partnerapi.daju.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.web.api.partnerapi.daju.constant.DaJuConstant;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.Header;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.Message;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.RequestParams;
import com.xlkfinance.bms.web.api.partnerapi.daju.model.ResponseParams;
import com.xlkfinance.bms.web.util.HttpRequestParam;
import com.xlkfinance.bms.web.util.HttpUtils;
import com.xlkfinance.bms.web.util.PropertiesUtil;
import com.xlkfinance.bms.web.util.SFTPUtil;

/**
 * 大桔（南粤银行） 加密工具
 * @author chenzhuzhen
 * @date 2017年2月9日 上午10:56:08
 */
public class DaJuUtil {
	
	private static Logger logger = LoggerFactory.getLogger(DaJuUtil.class);
	
	private static String ENCODING = "UTF-8";
	
	/**机构代码*/
	private static String ORG_CODE = PropertiesUtil.getValue("nyyh.partner.org.code");
	/**机构用户*/
	private static String ORG_USER = PropertiesUtil.getValue("nyyh.partner.org.user");
	/**机构密码*/
	private static String ORG_PWD = PropertiesUtil.getValue("nyyh.partner.org.pwd");
	/**接入代码*/
	private static String AGENCY_CODE = PropertiesUtil.getValue("nyyh.partner.agency.code");
 
	/**大桔公钥*/
	private static String DAJU_QFANG_PUBLIC_KEY = PropertiesUtil.getValue("nyyh.partner.public.key");
	/**大桔API地址*/
	private static String DAJU_API_URL = PropertiesUtil.getValue("nyyh.partner.api.url");
	/**大桔API文件地址*/
	private static String DAJU_API_FILE_URL = PropertiesUtil.getValue("nyyh.partner.api.file.url");
	
	/**FTP文件服务器ip*/
	public static String NYYH_SFTP_SERVER_IP = PropertiesUtil.getValue("nyyh.sftp.server.ip");
	/**FTP文件服务器端口*/
	public static String NYYH_SFTP_SERVER_PORT = PropertiesUtil.getValue("nyyh.sftp.server.port");
	/**FTP文件服务器用户名*/
	public static String NYYH_SFTP_SERVER_USERNAME = PropertiesUtil.getValue("nyyh.sftp.server.username");
	/**FTP文件服务器密码*/
	public static String NYYH_SFTP_SERVER_PASSWORD = PropertiesUtil.getValue("nyyh.sftp.server.password");
	/**FTP文件服务器文件根目录*/
	public static String NYYH_SFTP_DIR_BASE_PATH = PropertiesUtil.getValue("nyyh.sftp.dir.base.path");
 
	/**
	 * 加密请求参数
	 * @param uuid
	 * @param requestParams	请求参数
	 * @param publicKey	加密公钥 （使用接收方提供的公钥 ，如请求大桔，使用大桔公钥，如回调加密，使用Q房公钥）
	 * @return
	 * @throws Exception
	 */
	public static List<HttpRequestParam> encryptParams(String uuid,RequestParams requestParams,String publicKey)throws Exception{
		
		List<HttpRequestParam> paramMap = new ArrayList<HttpRequestParam>();
		try{
			//数据加密key
			String key = AESUtil.getAutoCreateAESKey();
			
			Header header = requestParams.getHeader();
			header.setOrg_code(ORG_CODE);
			header.setOrg_user(ORG_USER);
			header.setAgency_code(AGENCY_CODE);
			header.setOrg_req_no(System.currentTimeMillis()+"");
			header.setKey(key);
			header.setOrg_pwd(ORG_PWD);
			requestParams.setHeader(header);
			
			logger.info("uuid:"+uuid+",明文请求参数："+JSONObject.toJSONString(requestParams));
			
			//加密key和用户密码
			String enctyptKey = RSAUtil.encryptByPublicKey(key, publicKey);
			String enctyptOrgPwd = RSAUtil.encryptByPublicKey(ORG_PWD, publicKey);
			
			header.setKey(enctyptKey);
			header.setOrg_pwd(enctyptOrgPwd);
			requestParams.setHeader(header);
			
			//加密body参数
			String enctyptBodyStr=  AESUtil.encryptByAES(JSON.toJSONString(requestParams.getBody()), key);
			requestParams.setBody(enctyptBodyStr);
			
			String paramsStr = JSONObject.toJSONString(requestParams);
			
			//封装请求参数
			HttpRequestParam keyParam = new HttpRequestParam();
			keyParam.setParaName("szrData");	// 统一参数key值
			keyParam.setParaValue(paramsStr);
			paramMap.add(keyParam);
			
			logger.info("uuid:"+uuid+",密文请求参数："+JSONObject.toJSONString(paramMap));
			
		}catch(Exception e){
			logger.error("uuid:"+uuid+",encryptParams error", e);
			throw e;
		}
		return paramMap;
	} 
	
	/**
	 * post 提交 (请求大桔接口)
	 * @param uuid
	 * @param requestParams 请求参数
	 * @return
	 * @throws Exception
	 */
	public static ResponseParams post(String uuid,RequestParams requestParams)throws Exception{
		ResponseParams responseParams = null;
		try{
			//获取加密参数
			List<HttpRequestParam> paramMap = encryptParams(uuid,requestParams,DAJU_QFANG_PUBLIC_KEY);  
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(90000,90000);
			String result = httpUtils.executeHttpPost(DAJU_API_URL, paramMap, "UTF-8");
			logger.info("uuid:"+uuid+",请求响应result："+result);
			//解析并解密
			if (!StringUtil.isBlank(result)) {
				responseParams = ((ResponseParams) JSONObject.parseObject(result, ResponseParams.class));
				Header  header = responseParams.getHeader();
				Message message = responseParams.getMessage();
				if (header != null &&  DaJuConstant.MessageCode.SUCCESS.getCode().equals(message.getStatus())) {
					String key = RSAUtil.decryptByPublicKey(header.getKey(), DAJU_QFANG_PUBLIC_KEY);
					if(responseParams.getBody() != null){
						responseParams.setBody(JSON.parseObject(AESUtil.decryptByAES(responseParams.getBody().toString(), key)));
					}
				}
			}
		}catch(Exception e){
			logger.error("uuid:"+uuid+",post error", e);
			throw e;
		}
		return responseParams;
	}
 
	
	/**
	 * post 提交
	 * @param uuid
	 * @param url 请求地址
	 * @param requestParams 请求参数
	 * @return
	 * @throws Exception
	 */
	public static ResponseParams post(String uuid,String url ,RequestParams requestParams,String publicKey)throws Exception{
		ResponseParams responseParams = null;
		try{
			//获取加密参数
			List<HttpRequestParam> paramMap = encryptParams(uuid,requestParams,publicKey);  
			
			HttpUtils httpUtils = new HttpUtils();
			httpUtils.openConnection(90000,90000);
			String result = httpUtils.executeHttpPost(url, paramMap, "UTF-8");
			logger.info("uuid:"+uuid+",请求响应result："+result);
			//解析并解密
			if (!StringUtil.isBlank(result)) {
				responseParams = ((ResponseParams) JSONObject.parseObject(result, ResponseParams.class));
				Header  header = responseParams.getHeader();
				Message message = responseParams.getMessage();
				if (header != null &&  DaJuConstant.MessageCode.SUCCESS.getCode().equals(message.getStatus())) {
					String key = RSAUtil.decryptByPublicKey(header.getKey(), publicKey);
					if(responseParams.getBody() != null){
						responseParams.setBody(JSON.parseObject(AESUtil.decryptByAES(responseParams.getBody().toString(), key)));
					}
				}
			}
		}catch(Exception e){
			logger.error("uuid:"+uuid+",post error", e);
			throw e;
		}
		return responseParams;
	}
	
	
	
	/**
	 * post 提交（上传文件）(上传至大桔)
	 * @return
	 * @throws Exception
	 */
	public static ResponseParams postFile(String uuid,File uploadfile ) throws Exception {
		ResponseParams responseParams = null;
		@SuppressWarnings("resource")
		HttpClient httpclient = new DefaultHttpClient();
		try {
			Header header = new Header();
			//数据加密key
			String key = AESUtil.getAutoCreateAESKey();
			header.setService_code(DaJuConstant.ServiceCode.FILE_UPLOAD.getCode());
			header.setOrg_code(ORG_CODE);
			header.setOrg_user(ORG_USER);
			header.setAgency_code(AGENCY_CODE);
			header.setOrg_req_no(System.currentTimeMillis()+"");
			header.setKey(key);
			header.setOrg_pwd(ORG_PWD);
			
			logger.info("uuid:"+uuid+",postFile 明文参数："+JSONObject.toJSONString(header));
			
			//加密key和用户密码
			String enctyptKey = RSAUtil.encryptByPublicKey(key, DAJU_QFANG_PUBLIC_KEY);
			String enctyptOrgPwd = RSAUtil.encryptByPublicKey(ORG_PWD, DAJU_QFANG_PUBLIC_KEY);
			header.setKey(enctyptKey);
			header.setOrg_pwd(enctyptOrgPwd);
			
			logger.info("uuid:"+uuid+",postFile 密文参数："+JSONObject.toJSONString(header));
			
			//请求参数
			MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
			meBuilder.addPart("deal_info",new StringBody(JSON.toJSONString(header),ContentType.TEXT_PLAIN));
			FileBody fileBody = new FileBody(uploadfile);
			meBuilder.addPart("file", fileBody);		//上传文件
			
			logger.info("uuid:"+uuid+",postFile meBuilder:"+JSONObject.toJSONString(meBuilder));
			
			HttpEntity reqEntity = meBuilder.build();
			HttpPost httppost = new HttpPost(DAJU_API_FILE_URL);
			httppost.setEntity(reqEntity);
			HttpResponse response = httpclient.execute(httppost);
			int statusCode = response.getStatusLine().getStatusCode();
			logger.info("uuid:"+uuid+",postFile服务器响应statusCode:"+statusCode);
			
			//响应
			if (statusCode == HttpStatus.SC_OK) {
				String result =  EntityUtils.toString(response.getEntity(), ENCODING);
				logger.info("uuid:"+uuid+",postFile请求响应result："+result);
				if (!StringUtil.isBlank(result)) {
					//上传文件无需解密
					responseParams = ((ResponseParams) JSONObject.parseObject(result, ResponseParams.class));
				}
			}
		} catch (Exception e) {
			logger.error("uuid:"+uuid+",postFile error ",e);
		} finally {
			try {
				httpclient.getConnectionManager().shutdown();
			} catch (Exception e) {
				logger.error("uuid:"+uuid+",postFile  shutdown error ",e);
			}
		}
		return responseParams;
	}
	
	/**
	 * 解密请求body 
	 * @param uuid
	 * @param requestParams
	 * @return
	 */
	public static JSONObject decryptRequestBody(String uuid ,RequestParams requestParams){
		try{
			String key = RSAUtil.decryptByPublicKey(requestParams.getHeader().getKey(), DAJU_QFANG_PUBLIC_KEY);
			JSONObject bodyJson = JSON.parseObject(AESUtil.decryptByAES(requestParams.getBody().toString(), key));
			return bodyJson;
		}catch(Exception e){
			logger.error("uuid:"+uuid +", decryptRequestBody error", e);
			return null;
		}
	}
	
	
	/**
	 * 回调处理响应
	 * @param uuid
	 * @param requestParams
	 * @param message
	 * @return
	 */
	public static ResponseParams notifyResponse(String uuid ,RequestParams requestParams,Message message){
		ResponseParams responseParams = new ResponseParams();
		responseParams.setHeader(requestParams.getHeader());
		responseParams.setMessage(message);
		return responseParams;
	}
	
		
	
	
	
	public static void main(String[] args) throws Exception  {
	
/*		Map<String,Object> keyMap =RSAUtil.genKeyPair();
		
		System.out.println("RSAPrivateKey:"+RSAUtil.getPrivateKey(keyMap));
		System.out.println("RSAPublicKey:"+RSAUtil.getPublicKey(keyMap));
		*/
		
/*	 	Map<String,Object> bodyMap = new HashMap<String,Object>();
		bodyMap.put("lend_apply_stat", DaJuConstant.ApplyStat.APPROVAL_PASS.getCode());
		bodyMap.put("apply_deal_id", "VRHfxqj05agdlkr83nLOeu8MKGG2IwfX");
		bodyMap.put("return_msg", "success");
		Header header = new Header();
		RequestParams requestParams = new RequestParams();
		requestParams.setHeader(header);
		requestParams.setBody(bodyMap);
 
		
		logger.info("requestParams:"+JSONObject.toJSONString(requestParams));
		
		String url = "http://127.0.0.1:8082/BMS/openApi/nybApplyNotify.action"; 
		
		ResponseParams responseParams = DaJuUtil.post("", url, requestParams, null);
		
		logger.info("responseParams:"+JSONObject.toJSONString(responseParams));
		*/
		 
		// String filepath = "D:\\123.zip"; 
		//postFile("1111111", new File(filepath));
		
/*		RequestParams requestParams = new RequestParams();
		Header header = new Header();
		header.setKey("CObWRhebc+8OtJ6XzISYFHD96f+VdWwih9oczTk9KxKS5KdbBueGOimH0jSefr2/ZqKGEjnWNUbI7qUdIacLlXvxz79AyexiULXkRDeEsPgyOKLmLEDo+cK6UvtmbDag7XkDNh+5wNM61q9zUnvsx8bdV593KCoJE2SPirpESU0=");
		String bodyStr= "FC0A88C7F0E3766FB5C35B7F51A1E8F9E9AAA36562A81E7CFD209CA1F45FE1DFB6F7A015FDD4C70E9B16C9636AAB10E23754A3820A832A0D33CC0E4C8A405136787B234A1C5B1816C2BEEEBE0EB24C6D82BE5DEF8C8BCBC5C01327E29984B9FD537547FD33569985BAB7B93394BC68C86A60AA9E27ED7934E30716C1712FA2A4";
		
		requestParams.setHeader(header);
		requestParams.setBody(bodyStr);
		System.out.println(decryptRequestBody("", requestParams));;*/
		
		try{
			
			SFTPUtil sftpUtil = new SFTPUtil();
			sftpUtil.init(DaJuUtil.NYYH_SFTP_SERVER_IP, 22, 
					DaJuUtil.NYYH_SFTP_SERVER_USERNAME, DaJuUtil.NYYH_SFTP_SERVER_PASSWORD);
			//下载文件
			sftpUtil.uploadFile("D:/Loanqf20170226.txt" , "/upload/bms/partner/loan/Loanqf20170226_1.txt" );
			sftpUtil.close();
		
		}catch(Exception e){
			e.printStackTrace();
		}
	}
 
	
 
	
	
}
	
	
	
 
