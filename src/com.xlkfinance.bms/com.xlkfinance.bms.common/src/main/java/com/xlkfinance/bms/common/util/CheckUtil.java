/**
 * @Title: CheckUtil.java
 * @Package com.xlkfinance.bms.common.util
 * @Description: 验证工具
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年4月8日 下午6:02:26
 * @version V1.0
 */
package com.xlkfinance.bms.common.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CheckUtil {

	/**
	 * @Description: 获取省
	 * @param identityCardNumber
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:21:52
	 */
	public static String getIdCardProvince(String identityCardNumber) {
		if (identityCardNumber.length() != 18) {
			return "身份证号码长度应该为18位。";
		}

		Hashtable<String, String> h = genAreaCode();
		return h.get(identityCardNumber.substring(0, 2));
	}

	/**
	 * @Description: 获取市
	 * @param identityCardNumber
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:22:03
	 */
	public static String getIdCardCity(String identityCardNumber) {
		if (identityCardNumber.length() != 18) {
			return "身份证号码长度应该为18位。";
		}

		return identityCardNumber.substring(2, 4);
	}

	/**
	 * @Description: 获取县
	 * @param identityCardNumber
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:22:18
	 */
	public static String getIdCardCounty(String identityCardNumber) {
		if (identityCardNumber.length() != 18) {
			return "身份证号码长度应该为18位。";
		}

		return identityCardNumber.substring(4, 6);
	}

	/**
	 * @Description: 获取生日
	 * @param identityCardNumber
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:22:18
	 */
	public static String getIdCardBirthday(String identityCardNumber) {
		if (identityCardNumber.length() != 18) {
			return "身份证号码长度应该为18位。";
		}

		String strYear = identityCardNumber.substring(6, 10);// 年份
		String strMonth = identityCardNumber.substring(10, 12);// 月份
		String strDay = identityCardNumber.substring(12, 14);// 月份

		return strYear + "-" + strMonth + "-" + strDay;
	}

	/**
	 * @Description: 获取性别
	 * @param identityCardNumber
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:22:18
	 */
	public static String getIdCardGender(String identityCardNumber) {
		if (identityCardNumber.length() != 18) {
			return "身份证号码长度应该为18位。";
		}
		String genderCode = identityCardNumber.substring(16, 17);
		if (genderCode.equals("1")) {
			return "男";
		} else {
			return "女";
		}
	}

	/**
	 * @Description: 判断身份证是否有效
	 * @param identityCardNumber
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:21:41
	 */
	public static boolean isValid(String identityCardNumber) {
		String[] ValCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4", "3", "2" };
		String[] Wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };

		try {
			if (identityCardNumber.length() != 18) {
				// "身份证号码长度应该为18位。";
				return false;
			}
			String tmp1 = identityCardNumber.substring(0, 17);

			if (!isNumeric(tmp1)) {
				// 前17位必须是数字
				return false;
			}

			// ================ 出生年月是否有效 ================
			String strYear = tmp1.substring(6, 10);// 年份
			String strMonth = tmp1.substring(10, 12);// 月份
			String strDay = tmp1.substring(12, 14);// 月份

			if (isDate(strYear + "-" + strMonth + "-" + strDay) == false) {
				// "身份证生日无效。";
				return false;
			} else {
				GregorianCalendar gc = new GregorianCalendar();
				SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
				if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 200 || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
					// "身份证生日不在有效范围。";
					return false;
				}

				if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
					// "身份证月份无效";
					return false;
				}
				if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
					// "身份证日期无效";
					return false;
				}
			}

			// 地区编编码校验
			Hashtable<String, String> h = genAreaCode();
			if (h.get(tmp1.substring(0, 2)) == null) {
				// "身份证省级地区编码错误。";
				return false;
			}

			// 市级和县级地区编码暂不校验

			// 最后一位校验码判断
			int totalmulAiWi = 0;
			for (int i = 0; i < 17; i++) {
				totalmulAiWi = totalmulAiWi + Integer.parseInt(String.valueOf(tmp1.charAt(i))) * Integer.parseInt(Wi[i]);
			}
			int modValue = totalmulAiWi % 11;
			String strVerifyCode = ValCodeArr[modValue];
			tmp1 = tmp1 + strVerifyCode;

			if (tmp1.equals(identityCardNumber) == false) {
				// "身份证无效，不是合法的身份证号码";
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * @Description: 是否是有效的数字
	 * @param str
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:08:35
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (isNum.matches()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @Description: 是否是有效的日期
	 * @param strDate
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月8日 下午6:09:03
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	private static Hashtable<String, String> genAreaCode() {
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}

   /**
    * 检查传入数值是否为大于零,传入列表有一个为空或小于零，返回true
    *@author:liangyanjun
    *@time:2016年6月1日上午10:24:13
    *@param integers
    *@return
    */
   public static boolean checkInteger(Integer... integers) {
      for (Integer i : integers) {
         if (i == null || i <= 0) {
            return true;
         }
      }
      return false;
   }
   public static boolean checkDouble(Double... doubles) {
      for (Double i : doubles) {
         if (i == null || i <= 0) {
            return true;
         }
      }
      return false;
   }
   
	public static void main(String[] args) {
		String identityCardNumber = "420601198610203512";
		System.out.println(isDate("2004-02-29"));
		System.out.println(isValid(identityCardNumber));
		System.out.println(getIdCardProvince(identityCardNumber));
		System.out.println(getIdCardCity(identityCardNumber));
		System.out.println(getIdCardCounty(identityCardNumber));
		System.out.println(getIdCardBirthday(identityCardNumber));
		System.out.println(getIdCardGender(identityCardNumber));
	}

}
