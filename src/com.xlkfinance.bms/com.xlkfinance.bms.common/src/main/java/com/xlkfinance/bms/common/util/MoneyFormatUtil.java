package com.xlkfinance.bms.common.util;

import java.text.DecimalFormat;

/** ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年8月1日下午4:28:06 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 金额格式化<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br> */
public class MoneyFormatUtil {

    public static String format(double money) {
        DecimalFormat format = new DecimalFormat();
        format.applyPattern("##,###.00");
        String formatMoney = format.format(money);
        if (".00".equals(formatMoney)) {
            formatMoney = "0";
        }
        return formatMoney;
    }
    /**
     * 不带逗号
     * @param money
     * @return
     */
    public static String format2(double money) {
    	DecimalFormat format = new DecimalFormat();
    	format.applyPattern("######.00");
    	String formatMoney = format.format(money);
    	if (".00".equals(formatMoney)) {
    		formatMoney = "0";
    	}
    	return formatMoney;
    }

}
