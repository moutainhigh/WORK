/**
 * @Title: Test.java
 * @Package com.xlkfinance.bms
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * 
 * @author: yql
 * @date: 2015年7月7日 下午6:53:05
 * @version V1.0
 */
package com.xlkfinance.bms;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import com.xlkfinance.bms.common.util.DateUtils;

public class Test {

	public static void main(String[] args) throws Throwable {
		
		 Calendar c_begin = new GregorianCalendar();
	     Calendar c_end = new GregorianCalendar();
//	     DateFormatSymbols dfs = new DateFormatSymbols();
	     
	     String fromDate = "2017-07-12";
	     String endDate = "2017-07-17";
	     int beginYear = Integer.parseInt(fromDate.substring(0, 4));
	     int beginMonth = Integer.parseInt(fromDate.substring(6,7));
	     int beginDate = Integer.parseInt(fromDate.substring(8));
	     
	     int endYear = Integer.parseInt(endDate.substring(0, 4));
	     int endMonth = Integer.parseInt(endDate.substring(6,7));
	     int endDate1 = Integer.parseInt(endDate.substring(8));
	     
	     
	     
	     
	     List<String> weekDays =  new ArrayList<String>();
	     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	     c_begin.set(beginYear, beginMonth, beginDate); //
	     c_end.set(endYear, endMonth, endDate1); 
	     List<String> weekDays1 =  new ArrayList<String>();
	 
	     c_end.add(Calendar.DAY_OF_YEAR, 1);  //结束日期下滚一天是为了包含最后一天
	     while(c_begin.before(c_end)){
	    	   weekDays.add(sdf.format(c_begin.getTime()));
	      c_begin.add(Calendar.DAY_OF_YEAR, 1);
	     }
	     
	     Calendar cal = Calendar.getInstance();
	     
	     for(int i=0;i<weekDays.size();i++){
	    	 cal.setTime(DateUtils.stringToDate1(weekDays.get(i)));
	    	String f =  getWeek(DateUtils.stringToDate1(weekDays.get(i)));
	    	 if ("星期六".equals(f)||"星期日".equals(f)) {
	    		 weekDays1.add(weekDays.get(i));
	       }
	     }
	     
	     System.out.println(weekDays1);
		
	}
	
	public static String getWeek(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");  
        String week = sdf.format(date);  
        return week;  
    }  
}
