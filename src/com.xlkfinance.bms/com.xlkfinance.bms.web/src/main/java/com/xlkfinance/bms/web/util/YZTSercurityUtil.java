package com.xlkfinance.bms.web.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.StringUtil;

public class YZTSercurityUtil
{
	static Logger logger = LoggerFactory.getLogger(YZTSercurityUtil.class);
	/**
	 * RSA最大解密密文大小
	 */
	public final static String CONTENT_TYPE = "UTF-8";
	public static final String KEY_ALGORITHM = "RSA";
	//Q房私钥
	private static String QFANG_PARTNER_PRIVATE_KEY = PropertiesUtil.getValue("qfang.partner.private.key");
	//Q房公钥
	private static String QFANG_PARTNER_PUBLIC_KEY = PropertiesUtil.getValue("qfang.partner.public.key");
	
	//小赢公钥
	private static String XIAOYING_PARTNER_PUBLIC_KEY = PropertiesUtil.getValue("xiaoying.partner.public.key");
	//小赢MD5
	private static String XIAOYING_PARTNER_MD5 = PropertiesUtil.getValue("xiaoying.partner.md5");
	//小赢地址
	private static String XIAOYING_URL = PropertiesUtil.getValue("xiaoying.partner.api.url");
	
	//统联MD5
	private static String TL_PARTNER_MD5 = PropertiesUtil.getValue("tl.partner.md5");
	//统联合作机构代码
	private static String TL_PARTNER_PARAM_PARTNERCODE = PropertiesUtil.getValue("tl.partner.param.partnercode");
	//统联合作机构代码（Q房提供）
	private static String TL_PARTNER_PARAM_PARTNERCODE_QFANG = PropertiesUtil.getValue("tl.partner.param.partnercode.qfang");
	
	


	/**
	 *  请求参数加密 （小赢）
	  * @param resp
	  * @param md5Key
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午8:33:15
	 */
	public static List<HttpRequestParam> encryptParamsByXiaoYing(String resp, String md5Key,String publicKey)
	{
		List<HttpRequestParam> map = new ArrayList<HttpRequestParam>();
		String randomKey128 = getRandomString(16);
		String str = resp;
		//String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfGOD8++2wg4rSm8O1V22mCwx25sviYR/VE7Okqv8Y26guprhP0AS1XeVNchHlP4t8avvocAvrDINmOouXqT+981C/Ccer2soJw0TRHIrHOO3e8ABj6xdxMSays/3ZQgs8/vVB6ZqkIayTuonbXrHj9BeWSpOcLt4yMGOBqm2MkwIDAQAB";//加密的公钥
		try
		{
			String md5KeyBase16 = Base16.encode(RSAUtil.encryptByPublicKey(randomKey128.getBytes("UTF-8"), publicKey));
			byte[] encryptStr = RC4New.encry_RC4_byte(str, randomKey128,"UTF-8");
			String encryptStrBase16 = Base16.encode(encryptStr).toLowerCase();
			String tempStr = new String(encryptStr, "ISO-8859-1") + md5Key;
			tempStr = MD5Util.MD5(tempStr, "ISO-8859-1").toLowerCase();
			HttpRequestParam keyParam = new HttpRequestParam();
			HttpRequestParam contentParam = new HttpRequestParam();
			HttpRequestParam signParam = new HttpRequestParam();
			HttpRequestParam partnerParam = new HttpRequestParam();
			
			keyParam.setParaName("key");
			keyParam.setParaValue(md5KeyBase16);
			contentParam.setParaName("content");
			contentParam.setParaValue(encryptStrBase16);
			signParam.setParaName("sign");
			signParam.setParaValue(tempStr);
			partnerParam.setParaName("partner");
			partnerParam.setParaValue("XIAOYING_BEIJING_QF");
			map.add(keyParam);
			map.add(partnerParam);
			map.add(contentParam);
			map.add(signParam);
			
			if(logger.isDebugEnabled()){
				logger.debug("加密封装参数：\n key："+md5KeyBase16+"\n content:"+encryptStrBase16+"\n sign:"+tempStr+"\n partner:XIAOYING_BEIJING_QF");
			}
			
/*			map.put("key", md5KeyBase16);
			map.put("content", encryptStrBase16);
			map.put("sign", tempStr);
			map.put("partner", "XIAOYING_BEIJING");*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("验证用户登录状态---组织数据异常", e);
		}
		return map;
	}
	
	/**
	 * 请求参数加密
	  * @param resp
	  * @param md5Key
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午8:33:15
	 */
	public static List<HttpRequestParam> encryptParams(String resp, String md5Key)
	{
		List<HttpRequestParam> map = new ArrayList<HttpRequestParam>();
		String randomKey128 = getRandomString(16);
		String str = resp;
		//String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfGOD8++2wg4rSm8O1V22mCwx25sviYR/VE7Okqv8Y26guprhP0AS1XeVNchHlP4t8avvocAvrDINmOouXqT+981C/Ccer2soJw0TRHIrHOO3e8ABj6xdxMSays/3ZQgs8/vVB6ZqkIayTuonbXrHj9BeWSpOcLt4yMGOBqm2MkwIDAQAB";//加密的公钥
		try
		{
			String md5KeyBase16 = Base16.encode(RSAUtil.encryptByPublicKey(randomKey128.getBytes("UTF-8"), XIAOYING_PARTNER_PUBLIC_KEY));
			byte[] encryptStr = RC4New.encry_RC4_byte(str, randomKey128,"UTF-8");
			String encryptStrBase16 = Base16.encode(encryptStr).toLowerCase();
			String tempStr = new String(encryptStr, "ISO-8859-1") + md5Key;
			tempStr = MD5Util.MD5(tempStr, "ISO-8859-1").toLowerCase();
			HttpRequestParam keyParam = new HttpRequestParam();
			HttpRequestParam contentParam = new HttpRequestParam();
			HttpRequestParam signParam = new HttpRequestParam();
			HttpRequestParam partnerParam = new HttpRequestParam();
			
			keyParam.setParaName("key");
			keyParam.setParaValue(md5KeyBase16);
			contentParam.setParaName("content");
			contentParam.setParaValue(encryptStrBase16);
			signParam.setParaName("sign");
			signParam.setParaValue(tempStr);
			partnerParam.setParaName("partner");
			partnerParam.setParaValue("XIAOYING_BEIJING_QF");
			map.add(keyParam);
			map.add(partnerParam);
			map.add(contentParam);
			map.add(signParam);
			
			if(logger.isDebugEnabled()){
				logger.debug("加密封装参数：\n key："+md5KeyBase16+"\n content:"+encryptStrBase16+"\n sign:"+tempStr+"\n partner:XIAOYING_BEIJING_QF");
			}
			
/*			map.put("key", md5KeyBase16);
			map.put("content", encryptStrBase16);
			map.put("sign", tempStr);
			map.put("partner", "XIAOYING_BEIJING");*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("验证用户登录状态---组织数据异常", e);
		}
		return map;
	}

	/**
	 * 回复信息加密
	  * @param resp
	  * @param md5Key
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午8:33:27
	 */
	public static JSONObject encryptParamsResp(String resp, String md5Key)
	{
		JSONObject json = new JSONObject();
		String randomKey128 = getRandomString(16);
		String str = resp;

		try
		{
			String md5KeyBase16 = Base16.encode(RSAUtil.encryptByPublicKey(randomKey128.getBytes("UTF-8"), XIAOYING_PARTNER_PUBLIC_KEY));
			byte[] encryptStr = RC4New.encry_RC4_byte(str, randomKey128,"UTF-8");
			String encryptStrBase16 = Base16.encode(encryptStr).toLowerCase();
			String tempStr = new String(encryptStr, "ISO-8859-1") + md5Key;
			tempStr = MD5Util.MD5(tempStr, "ISO-8859-1").toLowerCase();
			json.put("key", md5KeyBase16);
			json.put("content", encryptStrBase16);
			json.put("sign", tempStr);
			json.put("partner", "XIAOYING_BEIJING_QF");
		}
		catch (Exception e)
		{
			logger.error("验证用户登录状态---组织数据异常", e);
		}
		return json;
	}

	/**
	 * 回复结果解密，对方请求参数解密
	  * @param resp
	  * @param md5Key
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午8:33:40
	 */
	public static String decryptResponse(String resp, String md5Key)
	{
		String privateKey = QFANG_PARTNER_PRIVATE_KEY;
		String content = "";
		try
		{
			Map respMap = JSON.parseObject(resp);
			byte[] encryptedData = Base16.decode(getStringValueFromMap(respMap , "key").toUpperCase());//test
			byte[] decryptedData = RSAUtil.decryptByPrivateKey(encryptedData, privateKey);
			content = RC4New.decry_RC4(getStringValueFromMap(respMap, "content").toUpperCase(),
					new String(decryptedData));
			String tempStr = new String(Base16.decode(getStringValueFromMap(respMap, "content")), "ISO-8859-1")
					+ md5Key;
			tempStr = MD5Util.MD5(tempStr, "ISO-8859-1").toLowerCase();
			if (tempStr.equals(getStringValueFromMap(respMap, "sign")))
			{
				return revert(content);
			}
			else
			{
				logger.error("返回信息验证签名失败");
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("返回信息异常", e);
			return null;
		}
	}

	/**
	 * 获取随机字符串
	  * @param length
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午8:34:30
	 */
	public static String getRandomString(int length)
	{
		StringBuffer buffer = new StringBuffer(
				"`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"ZXCVBNM<>?1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm");
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		int range = buffer.length();
		for (int i = 0; i < length; i++)
		{
			sb.append(buffer.charAt(random.nextInt(range)));
		}
		return sb.toString();
	}

	public static Map jsonToMap(String msg, String errKey, String errMsg)
	{
		if (StringUtil.isBlank(errMsg))
		{
			errMsg = "网络异常，请稍后再试";
		}
		if (StringUtil.isBlank(msg))
		{
			Map<String, String> map = new HashMap<String, String>();
			map.put(errKey, errMsg);
			return map;
		}
		ObjectMapper mapper = new ObjectMapper();
		try
		{
			return mapper.readValue(msg, Map.class);
		}
		catch (Exception e)
		{
			logger.error("json is :" + msg + "\n jsonToMap exception", e);
			Map<String, String> map = new HashMap<String, String>();
			map.put(errKey, errMsg);
			return map;
		}
	}

	/**
	 * 获取map值
	  * @param map
	  * @param key
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月28日 下午8:34:46
	 */
	public static String getStringValueFromMap(Map map, Object key)
	{
		if (map == null)
		{
			return "";
		}
		else
		{
			Object o = map.get(key);
			if (o == null)
			{
				return "";
			}
			else
			{
				return o.toString();
			}
		}
	}
	
	public static String revert(String str) {  
		  
        if (str != null && str.trim().length() > 0) {  
            String un = str.trim();  
            StringBuffer sb = new StringBuffer();  
            int idx = un.indexOf("\\u");  
            while (idx >= 0) {  
                if (idx > 0) {  
                    sb.append(un.substring(0, idx));  
                }  
  
                String hex = un.substring(idx + 2, idx + 2 + 4);  
                sb.append((char) Integer.parseInt(hex, 16));  
                un = un.substring(idx + 2 + 4);  
                idx = un.indexOf("\\u");  
            }  
            sb.append(un);  
            return sb.toString();  
        }  
        return "";  
    }  
	
	
	
	/**
	 * 统一请求参数加密加密接口
	 * @param params	业务参数		
	 * @param md5Key	md5加密字符串
	 * @param publicKey	公钥
	 * @param partnerCode	机构合作code
	 * @return
	 */
	public static List<HttpRequestParam> encryptParamsComm(String params, String md5Key,String publicKey,String partnerCode){
		List<HttpRequestParam> map = new ArrayList<HttpRequestParam>();
		String randomKey128 = getRandomString(16);
		String str = params;
		try{
			String md5KeyBase16 = Base16.encode(RSAUtil.encryptByPublicKey(randomKey128.getBytes("UTF-8"), publicKey));
			byte[] encryptStr = RC4New.encry_RC4_byte(str, randomKey128,"UTF-8");
			String encryptStrBase16 = Base16.encode(encryptStr).toLowerCase();
			String tempStr = new String(encryptStr, "ISO-8859-1") + md5Key;
			tempStr = MD5Util.MD5(tempStr, "ISO-8859-1").toLowerCase();
			HttpRequestParam keyParam = new HttpRequestParam();
			HttpRequestParam contentParam = new HttpRequestParam();
			HttpRequestParam signParam = new HttpRequestParam();
			HttpRequestParam partnerParam = new HttpRequestParam();
			
			keyParam.setParaName("key");
			keyParam.setParaValue(md5KeyBase16);
			contentParam.setParaName("content");
			contentParam.setParaValue(encryptStrBase16);
			signParam.setParaName("sign");
			signParam.setParaValue(tempStr);
			partnerParam.setParaName("partner");
			partnerParam.setParaValue(partnerCode);
			map.add(keyParam);
			map.add(partnerParam);
			map.add(contentParam);
			map.add(signParam);
			
			if(logger.isDebugEnabled()){
				logger.debug("加密封装参数：\n key："+md5KeyBase16+"\n content:"+encryptStrBase16+"\n sign:"+tempStr+"\n partner:"+partnerCode);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("统一请求参数加密加密接口---数据异常", e);
		}
		return map;
	}
	
	/**
	 * 统一回复信息加密
	 * @param params	业务参数		
	 * @param md5Key	md5加密字符串
	 * @param publicKey	公钥
	 * @param partnerCode	机构合作code
	 * @return
	 */
	public static JSONObject encryptParamsRespComm(String params, String md5Key,String publicKey,String partnerCode)
	{
		JSONObject json = new JSONObject();
		String randomKey128 = getRandomString(16);
		String str = params;
		try
		{
			String md5KeyBase16 = Base16.encode(RSAUtil.encryptByPublicKey(randomKey128.getBytes("UTF-8"), publicKey));
			byte[] encryptStr = RC4New.encry_RC4_byte(str, randomKey128,"UTF-8");
			String encryptStrBase16 = Base16.encode(encryptStr).toLowerCase();
			String tempStr = new String(encryptStr, "ISO-8859-1") + md5Key;
			tempStr = MD5Util.MD5(tempStr, "ISO-8859-1").toLowerCase();
			json.put("key", md5KeyBase16);
			json.put("content", encryptStrBase16);
			json.put("sign", tempStr);
			json.put("partner", partnerCode);
		}
		catch (Exception e)
		{
			logger.error("统一回复信息加密---数据异常", e);
		}
		return json;
	}
	
	
	/**
	 * 统一请求参数解密
	 * @param params	业务参数		
	 * @param md5Key	md5加密字符串
	 * @param privateKey	私钥
	 * @return
	 */
	public static String decryptResponseComm(String params, String md5Key,String privateKey)
	{
		String content = "";
		try
		{
			Map respMap = JSON.parseObject(params);
			byte[] encryptedData = Base16.decode(getStringValueFromMap(respMap , "key").toUpperCase());//test
			byte[] decryptedData = RSAUtil.decryptByPrivateKey(encryptedData, privateKey);
			content = RC4New.decry_RC4(getStringValueFromMap(respMap, "content").toUpperCase(),
					new String(decryptedData));
			String tempStr = new String(Base16.decode(getStringValueFromMap(respMap, "content")), "ISO-8859-1")
					+ md5Key;
			tempStr = MD5Util.MD5(tempStr, "ISO-8859-1").toLowerCase();
			if (tempStr.equals(getStringValueFromMap(respMap, "sign")))
			{
				return revert(content);
			}
			else
			{
				logger.error("返回信息验证签名失败");
				return null;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			logger.error("统一请求参数解密异常", e);
			return null;
		}
	}
	
	
	
//==================================以下为测试代码=======================================================
	
	
	/**测试接口-申请*/
	public static void testApply(){
		JSONObject partnerInfo = new JSONObject();

		partnerInfo.put("type", 3);//1:交易类赎楼, 2:非交易类赎楼, 3:尾款贷
		partnerInfo.put("city", "深圳");//城市名称
		partnerInfo.put("username", "李丽");//客户名称
		partnerInfo.put("idcard_no", "362522199311084021");//身份证号
		partnerInfo.put("mobile", "15220139410");//电话号码
		partnerInfo.put("apply_money", 30000);//借款金额
		partnerInfo.put("apply_deadline", 365);//借款期限
		partnerInfo.put("add_user", "11");
		partnerInfo.put("loan_date", "2016-04-09");//申请放款日
		
/* 		JSONObject baseJson = new JSONObject();
 		baseJson.put("1", "http://andrew218.imwork.net/BMS/nfs/beforeloan/2016/03/21/aa7c7927-c2cb-4349-bdfc-1015b3ee40c2.txt");
 		baseJson.put("2", "http://andrew218.imwork.net/BMS/nfs/beforeloan/2016/03/21/aa7c7927-c2cb-4349-bdfc-1015b3ee40c2.txt");
 		JSONObject filesJson = new JSONObject();
 		filesJson.put("loaner_base_info", baseJson);*/
 		
 		partnerInfo.put("files", "{}");
		System.out.println("加密前参数:"+partnerInfo.toString());
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(partnerInfo.toString(), XIAOYING_PARTNER_MD5);
		System.out.println(paramMap);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.initConnection();
		try {
			String result = httpUtils.executeHttpPost(XIAOYING_URL+Constants.URL_XIAOYING_APPLY,paramMap,"UTF-8");
			System.out.println(result);
			String resStr = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			JSONObject respMap = JSON.parseObject(resStr);
			JSONObject dataJson = respMap.getJSONObject("data");
			int errCode = (int) dataJson.get("err_code");
			System.out.println(resStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**测试接口-批量获取息费信息*/
	public static void testXiFeeList(){
		JSONObject partnerInfo = new JSONObject();
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams("", XIAOYING_PARTNER_MD5);
		HttpUtils httpUtils = new HttpUtils();
		System.out.println("paramMap:"+JSONObject.toJSONString(paramMap));
		httpUtils.initConnection();
		try {
			String result = httpUtils.executeHttpGet(XIAOYING_URL+Constants.URL_XIAOYING_XIFEE_LIST,paramMap,"UTF-8");
			System.out.println("result:"+result);
			String resStr = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			System.out.println("resStr:"+resStr);
			
			JSONObject respMap = JSON.parseObject(resStr);
			JSONObject dataJson = respMap.getJSONObject("data");
			int errCode = (int) dataJson.get("err_code");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**测试接口-审批结果回调*/
	public static void testApprovalRes(){

		JSONObject resultObj = new JSONObject();
		resultObj.put("loan_id", "52");
		resultObj.put("err_code", 2);
		resultObj.put("approve_money", "0");
		resultObj.put("remark", "FAIL");
		resultObj.put("type", "4");
		
		System.out.println("resultObj:"+resultObj.toString());
		//Q房网公钥
		String qFangPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfGOD8++2wg4rSm8O1V22mCwx25sviYR/VE7Okqv8Y26guprhP0AS1XeVNchHlP4t8avvocAvrDINmOouXqT+981C/Ccer2soJw0TRHIrHOO3e8ABj6xdxMSays/3ZQgs8/vVB6ZqkIayTuonbXrHj9BeWSpOcLt4yMGOBqm2MkwIDAQAB";
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParamsByXiaoYing(resultObj.toString(),XIAOYING_PARTNER_MD5, qFangPublicKey);
		HttpUtils httpUtils = new HttpUtils();
		System.out.println("paramMap:"+JSONObject.toJSONString(paramMap));
		httpUtils.initConnection();
		try {
			String result = httpUtils.executeHttpPost("http://127.0.0.1:8088/BMS/openApi/approval-res.action",paramMap,"UTF-8");
			String resStr = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			System.out.println("resStr:"+resStr);
			
			JSONObject respMap = JSON.parseObject(resStr);
			JSONObject dataJson = respMap.getJSONObject("data");
			int errCode = (int) dataJson.get("err_code");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**测试接口-放款确认结果回调*/
	public static void testLoanConfirm(){
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("loan_id", "205");
		resultObj.put("err_code", 0);
		resultObj.put("remark", "success");
		
		System.out.println("resultObj:"+resultObj.toString());
		//Q房网公钥
		String qFangPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfGOD8++2wg4rSm8O1V22mCwx25sviYR/VE7Okqv8Y26guprhP0AS1XeVNchHlP4t8avvocAvrDINmOouXqT+981C/Ccer2soJw0TRHIrHOO3e8ABj6xdxMSays/3ZQgs8/vVB6ZqkIayTuonbXrHj9BeWSpOcLt4yMGOBqm2MkwIDAQAB";
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParamsByXiaoYing(resultObj.toString(),XIAOYING_PARTNER_MD5, qFangPublicKey);
		HttpUtils httpUtils = new HttpUtils();
		System.out.println("paramMap:"+JSONObject.toJSONString(paramMap));
		httpUtils.initConnection();
		try {
			String result = httpUtils.executeHttpPost("http://127.0.0.1:8088/BMS/openApi/loan-confirm.action",paramMap,"UTF-8");
			String resStr = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			System.out.println("resStr:"+resStr);
			
			JSONObject respMap = JSON.parseObject(resStr);
			JSONObject dataJson = respMap.getJSONObject("data");
			int errCode = (int) dataJson.get("err_code");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**测试接口-还款回购结果回调*/
	public static void testLoanVoucherConfirm(){
		
		JSONObject resultObj = new JSONObject();
		resultObj.put("loan_id", "46");
		resultObj.put("type", 1);
		resultObj.put("err_code", 0);
		resultObj.put("remark", "success");
		
		System.out.println("resultObj:"+resultObj.toString());
		//Q房网公钥
		String qFangPublicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDfGOD8++2wg4rSm8O1V22mCwx25sviYR/VE7Okqv8Y26guprhP0AS1XeVNchHlP4t8avvocAvrDINmOouXqT+981C/Ccer2soJw0TRHIrHOO3e8ABj6xdxMSays/3ZQgs8/vVB6ZqkIayTuonbXrHj9BeWSpOcLt4yMGOBqm2MkwIDAQAB";
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParamsByXiaoYing(resultObj.toString(),XIAOYING_PARTNER_MD5, qFangPublicKey);
		HttpUtils httpUtils = new HttpUtils();
		System.out.println("paramMap:"+JSONObject.toJSONString(paramMap));
		httpUtils.initConnection();
		try {
			String result = httpUtils.executeHttpPost("http://127.0.0.1:8088/BMS/openApi/loan-voucher-confirm.action",paramMap,"UTF-8");
			String resStr = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			System.out.println("resStr:"+resStr);
			
			JSONObject respMap = JSON.parseObject(resStr);
			JSONObject dataJson = respMap.getJSONObject("data");
			int errCode = (int) dataJson.get("err_code");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**测试回调接口*/
	public static void testNotify(String method) throws Exception{
		JSONObject paramJson = new JSONObject();

		
		if("QF_NOTIFY_APPROVE".equals(method)){
			paramJson.put("loan_id", 11111);	
			paramJson.put("err_code", 0);	
			paramJson.put("type", 1);	
			paramJson.put("loan_effe_date", "2016-07-03");	
			paramJson.put("remark", URLEncoder.encode("成功","UTF-8"));	
		}else if("QF_NOTIFY_LOAN".equals(method)){
			paramJson.put("loan_id", "801160705100004");	
			paramJson.put("err_code", 0);	
			paramJson.put("loan_date", "2016-08-04");	
			JSONObject fileJson = new JSONObject();
			fileJson.put("F50", "/home/ifsp/801161002100018/F50_20161002165543.zip");
			paramJson.put("loan_file_url", fileJson);	
			paramJson.put("remark", "success");	
		}else if("QF_NOTIFY_REFUND".equals(method)){
			paramJson.put("loan_id", "801160705100004");	
			paramJson.put("err_code", 1);	
			paramJson.put("real_refund_date", "2016-08-04");	
			JSONObject fileJson = new JSONObject();
			fileJson.put("F50", "/home/ifsp/801161002100018/F50_20161002165543.zip");
			paramJson.put("refund_file_url", fileJson);	
			paramJson.put("interests", 10);	
			paramJson.put("remark", "success");	
		}else if("QF_QUERY_INLOAN_STATUS".equals(method)){
			paramJson.put("loan_id", "801160705100004");	
		}
		
		
		
		
		
		System.out.println("加密前参数:"+paramJson.toString());
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParamsComm(paramJson.toString(), TL_PARTNER_MD5,
				QFANG_PARTNER_PUBLIC_KEY, TL_PARTNER_PARAM_PARTNERCODE_QFANG);
		
		//添加服务方法
		HttpRequestParam methodParam = new HttpRequestParam();
		methodParam.setParaName("method");
		methodParam.setParaValue(method);
		paramMap.add(methodParam);
		
		System.out.println(paramMap);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.initConnection();
		try {
			String result = httpUtils.executeHttpPost("http://127.0.0.1:8088/BMS/openApi/notify.action",paramMap,"UTF-8");
//			String result = httpUtils.executeHttpPost("http://120.24.16.234:8888/BMS/openApi/notify.action",paramMap,"UTF-8");
			System.out.println("result:"+result);
			String resStr = YZTSercurityUtil.decryptResponse(result, QFANG_PARTNER_PRIVATE_KEY);
			JSONObject respMap = JSON.parseObject(resStr);
			JSONObject dataJson = respMap.getJSONObject("data");
			int errCode = (int) dataJson.get("err_code");
			System.out.println(resStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	/**测试接口-城市列表*/
	public static void cityList(){
		
		JSONObject partnerInfo = new JSONObject();
		partnerInfo.put("add_user", "54");
 
		System.out.println("加密前参数:"+partnerInfo.toString());
		
		List<HttpRequestParam> paramMap =  YZTSercurityUtil.encryptParams(partnerInfo.toString(), XIAOYING_PARTNER_MD5);
		System.out.println(paramMap);
		HttpUtils httpUtils = new HttpUtils();
		httpUtils.initConnection();
		try {

			String result = httpUtils.executeHttpPost(XIAOYING_URL+"/city-list",paramMap,"UTF-8");
			System.out.println(result);
			String resStr = YZTSercurityUtil.decryptResponse(result, XIAOYING_PARTNER_MD5);
			System.out.println(resStr);
			JSONObject respMap = JSON.parseObject(resStr);
			JSONObject dataJson = respMap.getJSONObject("data");
			System.out.println(resStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	public static void main(String[] args)throws Exception {
		
 
		//===================小赢测试
		//testXiFeeList();
//		testApply();
		//申请结果回调
//		testApprovalRes();
		//放款回调
//		testLoanConfirm();
		//还款回购回调
//		testLoanVoucherConfirm();
		
		cityList();
 
		//==================统联测试
//		testNotify("QF_NOTIFY_APPROVE");
//		testNotify("QF_NOTIFY_LOAN");
//		testNotify("QF_NOTIFY_REFUND");
//		testNotify("QF_QUERY_INLOAN_STATUS");
		 
		
	}
	
	public static void testDesc(){
		
		
		
		
		
		JSONObject json = new JSONObject();
//		json.put("timestamp", "2016-07-05 15:41:13");
//		json.put("sign", "4f76a5e79b1b3454d47363bea7f6f7f5");
//		json.put("key", "2014f93b1de1849dcd6d3f428b65b2e8ab6e16ab93c46c847b3eea80c7116ceda6f193bb2307e19a4b7de72505c6696518d468ed16066ea6f1cb8b46a35249cff169d581c2bbf50fa492e85267d78e2605f5f7bb2ccd10acb38f1910ca0c6629864a1ff87d1ae2393b0f90b9ec3b3fe946cdd53bdc977db89a178c71593c133d");
//		json.put("content", "d6c50d190fc910ce368a96cc745109fcf637c97a96958c9cbe5568621ae5b0d16bb25825634564913c4d1784464e26a560bb4b850d174a52aacdbb53fc6cd7");
//		
		
		
		json.put("sign", "3a6136f1e91a3c406810f56cd4177c6e");
		json.put("key", "7dd46bb12f2bd99da9fe89fc45b3577332ded47885facc06798c2fdb6fa2e7edebc6b5de70b183af89db75c1103998b1487ee956ae19f4634b3ab0e3f29545a9e7f9b5c43391b7dda573d683651988e17b767be683d53d3d0fc8dcf5233072c4a6505be46f61bb4f1029245f9cd765cabb2a8eec6298e405ea058f4469af6c9d");
		json.put("content", "9bfce78eccfea8eb5da65ea6e0eabc59b3819055e7ba1347d7f18d0426ffc4655ee67020df7ea69124fc7f12b22f8cae9ab766d2fd6ede7fa63dba250da7218c1d0e8cefb424156c48");
		
		
		String descStr =  decryptResponseComm(json.toString(), TL_PARTNER_MD5, QFANG_PARTNER_PRIVATE_KEY);
		System.out.println("descStr:"+descStr);
		
		
	}
	
}