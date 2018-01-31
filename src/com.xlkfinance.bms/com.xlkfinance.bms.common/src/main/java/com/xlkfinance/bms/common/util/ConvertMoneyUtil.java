package com.xlkfinance.bms.common.util;


/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月18日下午4:59:12 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 金额转换工具类，数据库金额保存的但是为“分”，页面显示单位“元”<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class ConvertMoneyUtil {
   /**
    * 金额转换率
    */
   public static final Integer MONEY_CONVERT_RATE = 100;

   /**
    * 向上转换金额
    *@author:liangyanjun
    *@time:2016年7月18日下午4:57:32
    *@param money
    *@return
    */
   public static double upConvertMoney(double money) {
      return money * MONEY_CONVERT_RATE;
   }

   /**
    * 向下转换金额
    *@author:liangyanjun
    *@time:2016年7月18日下午4:57:38
    *@param money
    *@return
    */
   public static double downConvertMoney(double money) {
      return money / MONEY_CONVERT_RATE;
   }
}
