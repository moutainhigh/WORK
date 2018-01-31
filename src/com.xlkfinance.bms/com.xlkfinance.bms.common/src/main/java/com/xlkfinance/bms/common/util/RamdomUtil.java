package com.xlkfinance.bms.common.util;

/**
 * 生成随机数工具类
 * @author Administrator
 *
 */
public class RamdomUtil {

	/**
	 * 生成指定位数随机数
	 * 
	 * @param bit
	 *            位数
	 * @return
	 */
	public static Integer getRandom(int bit) {

		if (bit <= 1) {
			bit = 5;

		} else {

			bit -= 1;
		}
		int num = (int) ((Math.random() * 9 + 1) * Math.pow(10, bit));
		return num;
	}
}
