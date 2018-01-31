package com.qfang.xk.aom.web.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 配置文件解析
 * @author huxl
 *
 */
public class PropertiesUtil {

	private static String CONFIG_DIR = "mail-config.properties";
	private static Properties properties = new Properties();
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	static{
		
		try {
			logger.info("init mq system config file start");
			String os = System.getProperty("os.name");
			String path = System.getProperty("user.dir");
			
			if(os.indexOf("Windows") != -1){
				
				path = path.replaceAll("\\\\", "\\/");
			}
			
			InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(CONFIG_DIR); //new FileInputStream(path+getCONFIG_DIR());
			properties.load(in);
			in.close();
			logger.info("mq system config file init ok");
		} catch (IOException e) {
			
			e.printStackTrace();
			logger.error("mq system config file init error : "+e.getMessage());
		}
	}
	
	/**
	 * 根据key获取其
	 * 对应的value
	 * @param key
	 * @return
	 */
	public static String getValue(String key){
		
		String value = properties.getProperty(key);
		return value;
	}
	public static int getIntValue(String key){
		
		String value = properties.getProperty(key);
		return Integer.valueOf(value);
	}
	public static boolean getBooleanValue(String key){
		
		String value = properties.getProperty(key);
		return Boolean.valueOf(value);
	}
	
	
	public static String getCONFIG_DIR() {
		return CONFIG_DIR;
	}
	public static void setCONFIG_DIR(String cONFIG_DIR) {
		CONFIG_DIR = cONFIG_DIR;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("-----------"+PropertiesUtil.getValue("xg.push.secret.key"));
		Set<Object> keySet = properties.keySet();
		
		Iterator it = keySet.iterator();
		
		while(it.hasNext()){
			Object key = it.next();
			System.out.println("===key:"+key+"  ====value:"+properties.getProperty((String)key));
		}
	}

	
}
