/**
 * @Title: GlobalConfig.java
 * @Package com.xlkfinance.bms.common.util
 * @Description: 全局配置
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月24日 下午2:01:37
 * @version V1.0
 */
package com.xlkfinance.bms.common.config;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName: GlobalConfig
 * @Description: 全局配置
 * @author: Simon.Hoo
 * @date: 2015年2月27日 上午11:47:34
 */
public class GlobalConfig implements Serializable {
	private static final long serialVersionUID = -58011626165144386L;

	// /////////////业务日志开关配置/////////////////
	private Map<String, Boolean> logSwitch;

	public boolean isRecord(String moduel) {
		return logSwitch.get(moduel);
	}

	public Map<String, Boolean> getLogSwitch() {
		return logSwitch;
	}

	public void setLogSwitch(Map<String, Boolean> logSwitch) {
		this.logSwitch = logSwitch;
	}
}
