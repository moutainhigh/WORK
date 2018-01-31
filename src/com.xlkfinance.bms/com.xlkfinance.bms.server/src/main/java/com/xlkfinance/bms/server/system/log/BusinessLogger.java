/**
 * @Title: BusinessLogger.java
 * @Package com.xlkfinance.bms.server.system.log
 * @Description: 业务日志工具
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月24日 下午3:09:16
 * @version V1.0
 */
package com.xlkfinance.bms.server.system.log;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xlkfinance.bms.common.config.GlobalConfig;
import com.xlkfinance.bms.rpc.system.SysLog;
import com.xlkfinance.bms.rpc.system.SysLogService.Iface;

@Component("businessLogger")
public class BusinessLogger {

	@Resource(name = "globalConfig")
	private GlobalConfig globalConfig;

	@Resource(name = "sysLogServiceImpl")
	private Iface sysLogServiceImpl;

	public void log(String moduel, SysLog sysLog) {
		try {
			if (globalConfig.isRecord(moduel)) {
				sysLogServiceImpl.addSysLog(sysLog);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
