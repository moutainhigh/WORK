package com.xlkfinance.bms.common.util;

import java.math.BigDecimal;

public class NumberUtils {

	public static void main(String[] args) {
		double result = PMT(0.22, 22.00, 22.00);
		System.out.println(result);
	}

	/**
	 * 四舍五入保留两位小数
	 * 
	 * @param number
	 *            数字字串
	 * @return 四舍五入后两位小数字的符串
	 */
	public static String formatDecimal(String number) {
		BigDecimal decimal = new BigDecimal(number);
		return formatDecimal(decimal);
	}

	/**
	 * 四舍五入保留两位小数
	 * 
	 * @param BigDecimal
	 * @return 四舍五入后两位小数字的符串
	 */
	public static String formatDecimal(BigDecimal decimal) {
		return parseDecimal(decimal).toString();
	}

	/**
	 * 四舍五入保留两位小数
	 * 
	 * @param number
	 *            数字字串
	 * @return 四舍五入后两位小数的BigDecimal
	 */
	public static BigDecimal parseDecimal(String number) {
		BigDecimal decimal = new BigDecimal(number);
		return parseDecimal(decimal);
	}

	/**
	 * 四舍五入保留两位小数
	 * 
	 * @param BigDecimal
	 * @return BigDecimal
	 */
	public static BigDecimal parseDecimal(BigDecimal decimal) {
		return decimal.setScale(2, BigDecimal.ROUND_HALF_UP);
	}

	/**
	* 计算月供
	* @param rate 月利率
	* @param term 贷款期数，单位月
	* @param financeAmount  贷款金额
	* @return
	*/
	public static double PMT(double rate,double term,double financeAmount)
	{
	    double v = (1+(rate)); 
	    double t = (-(term/12)*12); 
	    double result=(financeAmount*(rate))/(1-Math.pow(v,t));
	    return result;
	}

}
