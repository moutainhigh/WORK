/**
 * @Title: BoolUtil.java
 * @Package com.xlkfinance.bms.web.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月10日 下午9:31:18
 * @version V1.0
 */
package com.xlkfinance.bms.common.util;

public class BoolUtil {

	public static boolean isTrue(String boolStr) {
		if (boolStr.equalsIgnoreCase("true")) {
			return true;
		} else if (boolStr.equalsIgnoreCase("t")) {
			return true;
		} else if (boolStr.equalsIgnoreCase("y")) {
			return true;
		} else if (boolStr.equalsIgnoreCase("yes")) {
			return true;
		} else if (boolStr.equalsIgnoreCase("1")) {
			return true;
		} else if (boolStr.equalsIgnoreCase("ok")) {
			return true;
		} else {
			return false;
		}
	}
}
