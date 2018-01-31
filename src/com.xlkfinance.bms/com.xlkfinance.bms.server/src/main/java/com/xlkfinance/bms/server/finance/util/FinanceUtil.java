/**
 * @Title: FinanceUtil.java
 * @Package com.xlkfinance.bms.server.finance.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: qiancong.Xian
 * @date: 2015年3月14日 下午3:04:38
 * @version V1.0
 */
package com.xlkfinance.bms.server.finance.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 工具类
  * @ClassName: FinanceUtil
  * @Description: 对比是否逾期，日期格式 yyyy-MM-dd
  * @author: qiancong.Xian
  * @date: 2015年3月14日 下午3:04:42
 */
public class FinanceUtil {

	public static boolean isOverdue(String planData,String currentData)
	{
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return df.parse(currentData).getTime() >df.parse(planData).getTime();
		} catch (ParseException e) {
		}
		
		return false;
	}
	
	/**
	 * 计算两个任意时间中间的间隔天数
	 * @param startday Date类型(起始时间)
	 * @param endday Date类型(起始时间)
	 * @return days int类型(天数)
	 */
	public static int getIntervalDays(String limitDate,String currentDate){      
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
		Date startday = df.parse(limitDate);
		Date endday =df.parse(currentDate);
		
        if(startday.after(endday)){
            return 0;
        }        
        long sl=startday.getTime();
        long el=endday.getTime();       
        long ei=el-sl;           
        return (int)(ei/(1000*60*60*24));
		}
		catch(Exception e)
		{
			return 0;
		}
    }
	
	
	
	public static void main(String args [])
	{
		System.out.println(FinanceUtil.getIntervalDays("2015-02-15", "2015-03-16"));
	}
}
