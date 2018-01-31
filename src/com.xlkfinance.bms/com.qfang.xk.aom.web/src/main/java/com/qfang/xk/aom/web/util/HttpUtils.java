/**
 * 版权所有(C) 2012 深圳市雁联计算系统有限公司
 * 创建:ZhangDM(Mingly) 2012-9-5
 */

package com.qfang.xk.aom.web.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.xlkfinance.bms.common.constant.Constants;


/** 
 * @author ZhangDM(Mingly)
 * @date 2012-9-5
 * @description：http辅助类
 */

public class HttpUtils {
	
	private HttpClient httpClient = null;
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	/**
	 * @description 打开链接
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public void openConnection(){
		HttpParams params = new BasicHttpParams();
		ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();
		// 增加最大连接到400
		threadSafeClientConnManager.setMaxTotal(400);		
		// 增加每个路由的默认最大连接到400
		threadSafeClientConnManager.setDefaultMaxPerRoute(400);
		httpClient = new DefaultHttpClient(threadSafeClientConnManager, params); 
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000 );
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
	}
	
	public void openQueryConnection(){
		HttpParams params = new BasicHttpParams();
		ThreadSafeClientConnManager threadSafeClientConnManager = new ThreadSafeClientConnManager();
		// 增加最大连接到400
		threadSafeClientConnManager.setMaxTotal(400);		
		// 增加每个路由的默认最大连接到400
		threadSafeClientConnManager.setDefaultMaxPerRoute(400);
		httpClient = new DefaultHttpClient(threadSafeClientConnManager, params); 
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000 );
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
	}
	
	/**
	 * @description 连接和读取时间设置
	 * @param connTimeOutMillis
	 * @param readTimeOutMillis 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public void openConnection(int connTimeOutMillis,int readTimeOutMillis){
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, connTimeOutMillis);
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, readTimeOutMillis);
	}
	
	public void initConnection(){
		httpClient = new DefaultHttpClient();
		httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000 );
		httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);
	}
	
	/**
	 * @description HTTP GET 提交请求，注意服务器要将获取到的参数ISO-8859-1 转化成encoding
	 * @param url
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpGet(String url,String encoding) throws HttpException{
		try{
			HttpGet httpGet = new HttpGet(url);
			HttpResponse response = this.httpClient.execute(httpGet);
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}else{
				logger.error("executeHttpGet response code:"+response.getStatusLine().getStatusCode());
			}
			return null;
		}catch (Exception e) {
			throw new HttpException(Constants.ERROR_SENDFAIL);
		}
	}
	
	
	/**
	 * @description HTTP GET 提交请求并传递参数，注意服务器要将ISO-8859-1 转化成encoding
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpGet(String url,List<HttpRequestParam> params,String encoding) throws HttpException{
		try{
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			for(HttpRequestParam param : params){
				qparams.add(new BasicNameValuePair(param.getParaName(), param.getParaValue()));
			}
			
			String qry = URLEncodedUtils.format(qparams, encoding);
			String getUrl = url + "?" + qry;
			HttpGet httpGet = new HttpGet(getUrl);
			HttpResponse response = this.httpClient.execute(httpGet);
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}else{
				logger.error("executeHttpGet response code:"+response.getStatusLine().getStatusCode());
			}
			return null;
		}catch (Exception e) {
			throw new HttpException(Constants.ERROR_SENDFAIL);
		}
	}
	
	/**
	 * @description http post 提交请求
	 * @param url
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpPost(String url,String encoding) throws HttpException{
		try{
			HttpPost httpPost = new HttpPost(url);
			HttpResponse response = this.httpClient.execute(httpPost);
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}else{
				logger.error("executeHttpPost response code:"+response.getStatusLine().getStatusCode());
			}
			return null;
		}catch (Exception e) {
			throw new HttpException(Constants.ERROR_SENDFAIL);
		}
	}
	
	/**
	 * @description http post 提交请求，传递参数
	 * @param url
	 * @param params
	 * @param encoding
	 * @return
	 * @throws HttpException 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public String executeHttpPost(String url,List<HttpRequestParam> params,String encoding) throws HttpException{
		try{
			List<NameValuePair> qparams = new ArrayList<NameValuePair>();
			for(HttpRequestParam param:params){
				qparams.add(new BasicNameValuePair(param.getParaName(), param.getParaValue()));
			}
			
			HttpPost httpPost = new HttpPost(url);
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(qparams,encoding);
			httpPost.setEntity(entity);
			
			HttpResponse response = this.httpClient.execute(httpPost);
			
			if(HttpStatus.SC_OK==response.getStatusLine().getStatusCode()){
				return EntityUtils.toString(response.getEntity(), encoding);
			}else{
				logger.error("executeHttpPost response code:"+response.getStatusLine().getStatusCode());
			}
			return null;
		}catch (Exception e) {
		   e.printStackTrace();
			throw new HttpException(Constants.ERROR_SENDFAIL);
		}
	}
	
	
	/**
	 * @description 关闭链接 
	 * @author ZhangDM(Mingly)
	 * @date 2012-9-5
	 */
	public void closeConnection(){
		if(null != httpClient){
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	/**
	 * post发送请求json数据
	  * @param url
	  * @param json
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月21日 上午10:04:56
	 */
	public static JSONObject post(String url, JSONObject json) {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(url);
		JSONObject resJson = null;
		try {
			StringEntity s = new StringEntity(json.toString());
			s.setContentEncoding("UTF-8");
			s.setContentType("application/json");
			post.setEntity(s);
			HttpResponse result = client.execute(post);
			if (result.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 请求结束，返回结果
				String resData = EntityUtils.toString(result.getEntity());
				resJson = json.parseObject(resData);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally{
			client.getConnectionManager().shutdown();
		}
		return resJson;
	}
	
	/**
	 * get请求 json数据
	  * @param url
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月21日 上午10:10:12
	 */
	public static JSONObject get(String url) {  
        HttpClient client = new DefaultHttpClient();  
        HttpGet get = new HttpGet(url);  
        JSONObject json = null;  
        try {  
            HttpResponse res = client.execute(get);  
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {  
                HttpEntity entity = res.getEntity();  
             // 请求结束，返回结果
				String resData = EntityUtils.toString(entity);
				json = json.parseObject(resData);
            }  
        } catch (Exception e) {  
            throw new RuntimeException(e);  
              
        } finally{  
            //关闭连接 ,释放资源  
            client.getConnectionManager().shutdown();  
        }  
        return json;  
    }  
}
