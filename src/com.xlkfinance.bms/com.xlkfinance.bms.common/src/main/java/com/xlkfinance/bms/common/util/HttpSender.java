package com.xlkfinance.bms.common.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;


public class HttpSender {
	private static TrustManager[] xtmArray = new MytmArray[]{ new MytmArray()};
	public static CookieManager cookieManager;
	private String method;
	private String url;
	private String protocol;
	private String requestBody;
	private Map<String, String> headers;
	
	static {
		cookieManager = new java.net.CookieManager();
		CookieHandler.setDefault(cookieManager);
	}
	
	public HttpSender(String url, String method, String protocol) {
		this.url = url;
		this.method = method;
		this.protocol = protocol;
	}
	
	public byte[] execute() throws Exception {
		byte[] result = null;
		HttpURLConnection conn = null;
		try {
			conn = req();
			BufferedInputStream in = new BufferedInputStream(conn.getInputStream());
			result = new byte[in.available()];
			in.read(result);
			in.close();
		} catch (IOException e) {
			throw new Exception("网络繁忙，请稍后再试！");
		} finally {
			if(null != conn) {
				conn.disconnect();
			}
		}
		return result;
	}
	
	public String request(String requestBody, Map<String, String> headers) throws Exception{
		HttpURLConnection conn = null;
		this.setHeaders(headers);
		this.setRequestBody(requestBody);
		try {
			conn = req();
			InputStreamReader reader = new InputStreamReader(conn.getInputStream());
			StringBuilder str = new StringBuilder();
			char[] buffer = new char[1024 * 8];
			int size = -1;
			while((size = reader.read(buffer)) != -1) {
				str.append(buffer, 0 , size);
			}
			reader.close();
			return str.toString();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("网络繁忙，请稍后再试！");
		} finally {
			if(null != conn) {
				conn.disconnect();
			}
		}
	}
	
	private HttpURLConnection req() throws Exception {
		try {
			URL url = new URL(this.url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			if(null != headers && !headers.isEmpty()) {
				Set<String> keyset = headers.keySet();
				for (Iterator<String> it = keyset.iterator(); it.hasNext();) {
					String key = it.next();
					conn.setRequestProperty(key, headers.get(key));
				}
			}
			conn.setRequestMethod(method);
			conn.setConnectTimeout(5 * 1000);
			conn.setDoInput(true);
			if("https".equals(protocol)) {
				HttpsURLConnection httpsConn = (HttpsURLConnection)conn;
				httpsConn.setSSLSocketFactory(getSSLSocketFactory());
				httpsConn.setHostnameVerifier(new HostnameVerifier() {
					public boolean verify(String hostname, SSLSession session) {
						return true;
					}
				});
			}
			if(null != requestBody) {
				conn.setDoOutput(true);
				OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
				writer.write(requestBody);
				writer.flush();
			}
			if(conn.getResponseCode() != 200) {
				throw new Exception("服务器异常");
			}
			return conn;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("网络繁忙，请稍后再试！"+e.getMessage());
		} 
	}
	
	private SSLSocketFactory getSSLSocketFactory() throws Exception {
		SSLContext context = SSLContext.getInstance("TLS");
		context.init(null, xtmArray, new java.security.SecureRandom());
		return context.getSocketFactory();
	}
	
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
	private static class MytmArray implements X509TrustManager {
	    public X509Certificate[] getAcceptedIssuers() {  
	        return new X509Certificate[] {};  
	    }  
	    public void checkClientTrusted(X509Certificate[] chain, String authType)  
	            throws CertificateException {  
	    }  
	  
	    public void checkServerTrusted(X509Certificate[] chain, String authType)  
	            throws CertificateException {  
	    }  
	};  
}