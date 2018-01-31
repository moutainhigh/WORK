package com.xlkfinance.bms.web.api.partnerapi.dr.util;

import java.security.interfaces.RSAPrivateKey;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.web.api.partnerapi.dr.model.JWTClaim;
import com.xlkfinance.bms.web.api.partnerapi.dr.model.JWTHeader;
import com.xlkfinance.bms.web.util.HttpUtils;


/**
 * 点融生成jwt 工具类
 * @author chenzhuzhen
 * @date 2016年7月14日 下午4:41:42
 */
public class JWTGeneratorUtil {

	
	/**
	 * 获取头部base64编码
	 * @return
	 * @throws Exception
	 */
    private static String getBase64Header() throws Exception {
        JWTHeader h = new JWTHeader();
        h.setAlg("RS256");
        h.setTyp("JWT");
        String header = JSONObject.toJSONString(h);
        return Base64.encodeBase64String(header.getBytes("utf-8"));
    }

    /**
     * 获取cliaim base64 编码
     * @param clientId	第三方应用id
     * @param tokenUrl  token地址
     * @return
     * @throws Exception
     */
    private static String getBase64Claim(String clientId ,String tokenUrl) throws Exception {
        JWTClaim claim = new JWTClaim();
        claim.setClientId(clientId); // 设置clientId
        claim.setAud(tokenUrl); // 设置aud
        Long current = System.currentTimeMillis();
        claim.setIat(current);
        claim.setExp(current + 2 * 60 * 60 * 1000);
        String json = JSONObject.toJSONString(claim);
        return Base64.encodeBase64String(json.getBytes("utf-8"));
    }

    /**
     * 使用私key加密
     * @param base64Header
     * @param base64Claim
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static String createSignature(String base64Header, String base64Claim,String privateKey)
            throws Exception {
        String privateKeyStr = privateKey; // 设置privateKey
        String source = base64Header + "." + base64Claim;
        String sign = SHAUtils.encrypt(source);
        RSAPrivateKey prikey = RSAUtils.getPrivateKey(privateKeyStr);
        String encryptSign = RSAUtils.encrypt(sign, prikey);
        return encryptSign;
    }
    
    /**
     * 获取jwt 字符串
     * @param base64Header
     * @param base64Claim
     * @param sign
     * @return
     * @throws Exception
     */
    public static String getJWT(String base64Header, String base64Claim, String sign)
            throws Exception {
        String jwt = base64Header + "." + base64Claim + "." + sign;
        return jwt;
    }
    
    /**
     * 获取jwt 字符串2
     * @param clientId	第三方应用id
     * @param tokenUrl	token url
     * @param privateKey 加密私钥
     * @return
     * @throws Exception
     */
    public static String getJWT2(String clientId ,String tokenUrl, String privateKey)throws Exception {
    	String base64Header = getBase64Header();
    	String base64Claim = getBase64Claim(clientId, tokenUrl);
    	String sign = createSignature(base64Header, base64Claim, privateKey);
    	String jwt = getJWT(base64Header, base64Claim, sign);
    	return jwt;
    }
    
    

    public static void main(String[] args) throws Exception {
    	
    	String clientId = "9183628d075040e99ce1b8e322d3e2ea";
    	String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKkjUm5YybmrkrQe9ssNMQpVXqcd9ao+UPaZ4pmEcn7AxVr2sae0+qLlNwyFT68B/oxNbC2M0JqPv+582RjjDXHG5HBhWYSWWDnyqw/QKEYDBHnoNIYYf2LV8OMBBmnuvgbRV1/7z446INha/e1l7k/Dl08keW3gCxyAG96hEcbBAgMBAAECgYEAj1MmM+m7L0HrtASVHyWTi+0hoz2pAlWCKCXotixdFXnnAxkPHE0pIkZYKp0BFAz9lgi5hbPQxVjQzJgxI3yr03nxnsu9oqIN4pp33+pg/791CS+K8MV2aDcogK3NAJzC+E+fri7qdaYrALcmCNnB7m6pMZPudmLMsYtDUuPx7VECQQD8cl0P2bcWrM7i89CFoFfgh1i1cXi4a9a7TR7HEuR90ZNqqxffXkTIhXLekCBFq6N7UlDa+K0cnxx/1pF7CIV9AkEAq4TGDH6bamqd1e5SfUjDSRBVQyKQW7Sd0jfKHb3HhuU6H2u5QOqJuSWKZl42FKwyjkF6x+agQlX/DTl+xVR5lQJBAMtJsQU9lkGNQUDjhAzHVVy6wUXDraodNRs5fEplPVjQdrzUMHIwcIVlaV4ug752/DwnynK7BHRi8MlFWXvRHeUCQF5tClt2mDlN2Pcd7Wvr0xc2VrpPHQpftUM2U59sC5zINed2dta2SHwVnswcsclPw8VtZxsGbNQYZAHz1Ubt6sECQE+lQMUB/NL0m6LxTEwpSmHQzoZzwgAH/a2drAtbiD2sNperG32h802oMe9Cl+n57aPgm39DfKvYr5FIIrRPF5I=";
    	String tokenUrl = "https://loanapp-dev.dianrong.com/v1/oauth2/token";    	
    	
        String header = getBase64Header();
        String claim = getBase64Claim(clientId, tokenUrl);
        String sign = createSignature(header, claim,privateKey);
        String jwt = getJWT(header, claim, sign);
        
        System.out.println("jwt:"+jwt);
        
//		HttpUtils httpUtils = new HttpUtils();
//		httpUtils.initConnection();
//		List<HttpRequestParam> paramList = new ArrayList<HttpRequestParam>();
//		HttpRequestParam param = new HttpRequestParam("assertion", jwt);
//		paramList.add(param);
//		String result = httpUtils.executeHttpPost(tokenUrl, paramList, "UTF-8");
//        System.out.println(">>>>>>getOAuthToken http result:"+result);
        
        
      //请求获取token
      tokenUrl = tokenUrl +"?assertion="+jwt;
	  HttpUtils httpUtils = new HttpUtils();
	  JSONObject resultJson = httpUtils.httpsRequest(tokenUrl, "POST",null);
	  httpUtils.closeConnection();
      System.out.println(">>>>>>getOAuthToken http result:"+resultJson);
    }
}
