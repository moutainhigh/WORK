/**
 * @Title: RunServer.java
 * @Package com.xlkfinance.bms.server
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月12日 下午1:36:23
 * @version V1.0
 */
package com.xlkfinance.bms.server;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.achievo.framework.util.DateUtil;

/**
 * 类描述：<br>
 * Thrift 服务端启动
 * 
 * @author Simon.Hoo
 * @date Oct 23, 2014
 * @version v1.0
 */
public class RunServer {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(RunServer.class);
		try {
			System.out.print("服务器当前时间:" + DateUtil.format(new Date()) + "\n\r");
			new ClassPathXmlApplicationContext(new String[] { "config/app.xml" });
		} catch (SecurityException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			logger.debug(e.getMessage());
			e.printStackTrace();
		}
	}
}
