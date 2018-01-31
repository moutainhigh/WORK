package com.xlkfinance.bms.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 
 * @author liangyanjun
 *
 */
public class DateUtils {
	// 转换[Date,Calendar,String,long]

	/**
	 * 默认时间格式
	 */
	public static final String DEFAULT_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	/**
	 * 默认日期格式
	 */
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DATE_NO_TASH_FORMAT = "yyyyMMdd";
	
	private DateUtils() {

	}

	/**
	 * Date类型的日期转换成long类型的日期
	 * 
	 * @param date
	 * @return
	 */
	public static long dateToLong(Date date) {
		long time = date.getTime();
		return time;
	}

	/**
	 * 根据传入的long型日期转成date
	 * @param l
	 * @return
	 */
	public static Date longToDate(long l) {
		return new Date(l);
	}

	/**
	 * 根据传入的Calendar转成date
	 * @param c
	 * @return
	 */
	public static Date calendarToDate(Calendar c) {
		return c.getTime();
	}

	/**
	 * 根据传入的date转成calendar
	 * @param date
	 * @return
	 */
	public static Calendar dateToCalendar(Date date) {
		Calendar c = getCalendar();
		c.setTime(date);
		return c;
	}

	/**
	 * xxxx-xx-xx xx:xx:xx
	 * 根据传入的date获取完整日期
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		String fullDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
		return fullDate;
	}
	/**
	 * 根据传入的date获取完整日期  ,  格式
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date , String format ) {
		String fullDate = new SimpleDateFormat(format).format(date);
		return fullDate;
	}

	/**
	 * 
	 * 根据传入的相应格式的字符串日期，转为date
	 * @param str
	 * 		传过来的日期格式必须是：xxxx-xx-xx xx:xx:xx 例如：2000-02-12 14:25:25
	 * @return
	 */
	public static Date stringToDate(String str) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);
		} catch (ParseException e) {
			System.out.println("传过来的日期格式错误");
		}
		return date;
	}
	
	public static Date stringToDate(String str,String format) {
	   Date date = null;
	   try {
	      date = new SimpleDateFormat(format).parse(str);
	   } catch (ParseException e) {
	      System.out.println("传过来的日期格式错误");
	   }
	   return date;
	}

	// 得到当前时间

	/**
	 * 得到一个Calendar
	 * @return
	 */
	public static Calendar getCalendar() {
		Calendar c = Calendar.getInstance();
		return c;
	}

	/**
	 * 得到当前时间：例如2104-04-04 08
	 * @return
	 * 	    返回当前日期:xxxx-xx-xx xx 例如：2012-12-25 09
	 */
	public static String getCurrentDateIncludeHour() {
		String date = new SimpleDateFormat("yyyy-MM-dd HH").format(new Date());
		return date;
	}

	
	/**
	 * 得到当前时间：例如2104-04-04
	 * @return
	 * 	    返回当前日期:xxxx-xx-xx 例如：2012-12-25
	 */
	public static String getCurrentDate() {
		String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		return date;
	}
	/**
	 * 得到当前时分秒时间：例如12:34:21
	 * @return
	 */
	public static String getCurrentTime() {
		String time = new SimpleDateFormat("HH:mm:ss").format(new Date());
		return time;
	}

	/**
	 * 得到日期和时间：例如2104-04-04 12:34:21
	 * @return
	 */
	public static String getCurrentDateTime() {
		String date = getCurrentTime();
		return getCurrentDate() + " " + date;
	}

	/**
	 * 得到当前长整型年份：例如2014
	 * @return
	 */
	public static String getCurrentLongYear() {
		String year = new SimpleDateFormat("yyyy").format(new Date());
		return year;
	}

	/**
	 * 得到当前短整型年份：例如14
	 * @return
	 */
	public static String getCurrentShortYear() {
		String year = new SimpleDateFormat("yy").format(new Date());
		return year;
	}

	/**
	 * 得到当前月份
	 * @return
	 */
	public static String getCurrentMonth() {
		String month = new SimpleDateFormat("MM").format(new Date());
		return month;
	}

	/**
	 * 得到当前星期
	 * @return
	 */
	public static String getCurrentWeek() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("E");
		String week = dateFormat.format(new Date());
		return week;
	}

	/**
	 * 得到当前日
	 * @return
	 */
	public static String getCurrentDay() {
		String day = new SimpleDateFormat("dd").format(new Date());
		return day;
	}

	// 得到一个日期里的日期时间信息

	/**
	 * 根据date返回年
	 * @param date
	 * @return
	 */
	public static int getYearByDate(Date date) {
		Calendar c = dateToCalendar(date);
		int year = c.get(Calendar.YEAR);
		return year;
	}

	/**
	 * 
	 * 根据date返货月
	 * @param date
	 * @return
	 */
	public static int getMonthByDate(Date date) {
		Calendar c = dateToCalendar(date);
		int month = c.get(Calendar.MONTH) + 1;
		return month;
	}

	/**
	 *根据date得到 一年中的第几天
	 * 
	 * @param date
	 * @return
	 */
	public static int getDayOfYearByDate(Date date) {
		Calendar c = dateToCalendar(date);
		int day = c.get(Calendar.DAY_OF_YEAR);
		return day;
	}

	/**
	 * 根据date返回月的第几周
	 * @param date
	 * @return
	 */
	public static int getWeekByDate(Date date) {
		Calendar c = dateToCalendar(date);
		int week = c.get(Calendar.WEEK_OF_YEAR);
		return week;
	}

	/**
	 * 根据date返回月的第几天
	 * @param date
	 * @return
	 */
	public static int getDayOfMonthByDate(Date date) {
		Calendar c = dateToCalendar(date);
		int day = c.get(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 *根据date获取该天是星期几
	 * @param date
	 * @return
	 */
	public static String getDayOfWeekByDate(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("E");
		String week = dateFormat.format(date);
		return week;
	}

	// 时间格式之间的转换

	/**
	 * 根据传入短整形日期，返回长整型日期。例如：传入010323，返回2001-03-23
	 * 注意：在世纪方面，如果输入的年份比当前年份小，则世纪为20世纪，如果传入的年份比当前年份大，则表明不科学，所以世纪则理所当然是19世纪
	 *     如果传入参数长度超过6位数，则返回的是以前六位为准的长整型日期
	 * 例如：
	 * 1.传入参数为010323，返回：2001-03-23
	 * 2.传入参数为：890823，返回则是：1989-08-23
	 * 3.传入参数为：82031312  返回则是：1982-03-13
	 * @param shortDate
	 * @return
	 */
	public static String shortDateToLongDate(String shortDate) {
		int year = Integer.parseInt(shortDate.substring(0, 2));// 截取传入年份
		String longDate = "";
		int currentCentury = Integer.parseInt(getCurrentShortYear());// 获取当前年份
		if (year > currentCentury) {
			longDate = "19" + shortDate;
		} else {
			longDate = "20" + shortDate;
		}
		String fullDate = longDateToShortDate(longDate);
		return fullDate;
	}

	/**
	 * 根据传入长整型日期转为短整型日期：例如传入20130212，返回为2013-02-12
	 * @param longDate
	 * @return
	 */
	public static String longDateToShortDate(String longDate) {
		Calendar calendar = getCalendar();
		String shortDate = "";
		try {
			int year = Integer.parseInt(longDate.substring(0, 4));
			int month = Integer.parseInt(longDate.substring(4, 6));
			int day = Integer.parseInt(longDate.substring(6, 8));
			calendar.set(year, month - 1, day, 0, 0);
			shortDate = dateFormatByPattern(calendar.getTime(), "yyyy-MM-dd");
		} catch (Exception e) {
			System.out.println("日期格式错误,正确格式如：20130212");
		}
		return shortDate;
	}

	/**
	 * 根据传入长整型时分秒转为正常格式显示，例如：传入13:43:12，转为  134312(注意：分隔符是可以是任意的非数字字符)
	 * @param longTime
	 * @return
	 */
	public static String longTimeToShortTime(String longTime) {
		String shortTime = "";
		try {
			shortTime = longTime.replaceAll("\\D", "");
		} catch (Exception e) {
			System.out.println("日期格式错误");
		}
		return shortTime;
	}

	/**
	 * 根据传入短整型时分秒转为正常格式显示，例如：传入134312，转为13:43:12
	 * @param shortTime
	 * @return
	 */
	public static String shortTimeToLongTime(String shortTime) {
		Calendar calendar = getCalendar();
		String longTime = "";
		try {
			int huor = Integer.parseInt(shortTime.substring(0, 2));
			int minute = Integer.parseInt(shortTime.substring(2, 4));
			int second = Integer.parseInt(shortTime.substring(4, 6));
			calendar.set(0, 0, 0, huor, minute, second);
			longTime = dateFormatByPattern(calendar.getTime(), "kk:mm:ss");
		} catch (Exception e) {
			System.out.println("日期格式错误,正确格式如：134312");
		}
		return longTime;
	}

	// other
	/**
	 * 得到年有多少天
	 * @param year
	 * @return
	 */
	public static int getDaysOfYear(Date year) {
		Calendar calendar = dateToCalendar(year);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_YEAR);
		return day;
	}

	/**
	 * 得到一年又多少星期
	 * @param year
	 * @return
	 */
	public static int getWeeksOfYear(Date year) {
		Calendar calendar = dateToCalendar(year);
		int week = calendar.getActualMaximum(Calendar.WEEK_OF_YEAR);
		return week;
	}

	/**
	 * 得到月有多少天
	 * @param month
	 * @return
	 */
	public static int getDaysOfMonth(Date month) {
		Calendar calendar = dateToCalendar(month);
		int day = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		return day;
	}

	/**
	 * 得到月有几周
	 * @param month
	 * @return
	 */
	public static int getWeeksOfMonth(Date month) {
		Calendar calendar = dateToCalendar(month);
		int week = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH);
		return week;
	}

	/**
	 * 判断是否闰年
	 * @param year
	 * @return
	 */
	public static boolean isLeapYear(Date date) {
		int year = getYearByDate(date);
		boolean isleapyear = (((((year % 4) == 0) && (((year % 100) != 0))) || ((year % 400) != 0)));
		return isleapyear;
	}

	/**
	 * 根据格式模式获得日期字符串
	 * @param pattern
	 * @return
	 */
	public static String dateFormatByPattern(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		String formatDate = dateFormat.format(date);
		return formatDate;
	}
	public static String dateFormatByPattern(String str, String pattern) {
	   if (StringUtil.isBlank(str)) {
         return str;
      }
	   SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
	   String formatDate = dateFormat.format(stringToDate(str));
	   return formatDate;
	}

	/**
	 * 获取月的最后一天
	 * @param month
	 * @return
	 */
	public static String getLastDayOfMonth(int month) {
		Calendar calendar = getCalendar();
		calendar.set(Calendar.MONTH, month - 1);
		calendar.set(Calendar.DATE, 1);
		String lastDay = String.valueOf(calendar.getActualMaximum(Calendar.DATE));
		return lastDay;
	}
	/**
	 * 获取月的第一天
	 * @param month
	 * @return
	 */
	public static Date getFirstDayOfMonth() {
	    Calendar cal = Calendar.getInstance();   
	    cal.setTime(new Date());   
	    cal.set(Calendar.DAY_OF_MONTH, 1);   
        return cal.getTime();
	}

    /** 
     * 返回该月最后一天
     * @author:liangyanjun
     * @time:2015-11-25下午6:45:51
     * @param date
     * @return */
    public static Date getLastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        final int lastDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        Date lastDate = c.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }
    /**
     * 返回该月的第一天
      * @param date
      * @return
      * @author: baogang
      * @date: 2016年8月18日 上午10:46:49
     */
    public static Date getFirstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        final int lastDay = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        Date lastDate = c.getTime();
        lastDate.setDate(lastDay);
        return lastDate;
    }
    
	/**
	 * 根据传入的两个date，计算它们的时间差（以“秒”为单位）
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int secondsDifference(Date beginDate, Date endDate) {
		long begin = beginDate.getTime();
		long end = endDate.getTime();
		long millisecond = 1000;// 一分钟的毫秒数
		int seconds = Integer.parseInt(String.valueOf((end - begin) / millisecond));
		return seconds;
	}
	/**
	 * 根据传入的两个date，计算它们的时间差（以“分钟”为单位）
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int minutesDifference(Date beginDate, Date endDate) {
		long begin = beginDate.getTime();
		long end = endDate.getTime();
		long millisecond = 60000;// 一分钟的毫秒数
		int minutes = Integer.parseInt(String.valueOf((end - begin) / millisecond));
		return minutes;
	}
	/**
	 * 根据传入的两个date，计算它们的时间差（以“天”为单位）
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int dayDifference(Date beginDate, Date endDate) {
		long begin = beginDate.getTime();
		long end = endDate.getTime();
		long millisecond = 86400000;// 一天的毫秒数
		int day = Integer.parseInt(String.valueOf((end - begin) / millisecond));
		return day;
	}

	/**
	 * 天数相加
	 * 
	 * @param date
	 * @param number
	 * @return
	 */
	public static Date addDay(Date date, Integer number) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, number);

		Date result = cal.getTime();

		return result;
	}
	
	/**
	 * 月份相加
	 * @param date
	 * @param number
	 * @return
	 */
	public static Date addMonth(Date date, Integer number){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, number);

		Date result = cal.getTime();

		return result;
	}
	
	/**
	 * 根据传入的date和格式，获取日期
	 * @param date
	 * @param format
	 * @return
	 */
	public static String dateToFormatString(Date date,String format) {
		String fullDate = new SimpleDateFormat(format).format(date);
		return fullDate;
	}
	
	/**
	 * 获取一天的最开始时间
	  * @param date
	  * @return
	  * @author: baogang
	  * @date: 2016年8月15日 上午10:14:20
	 */
	public static Date getDateStart(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 00:00:00");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDateStartStr(String dateStr) {
		if(dateStr == null || dateStr.trim().equals(""))return null;
		Date date = stringToDate(dateStr,DEFAULT_DATE_FORMAT);
		return dateToFormatString(getDateStart(date), DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 获取日期的最后时间
	  * @param date
	  * @return
	  * @author: baogang
	  * @date: 2016年8月15日 上午10:14:33
	 */
	public static Date getDateEnd(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			date = sdf.parse(formatDate(date, "yyyy-MM-dd") + " 23:59:59");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static String getDateEndStr(String dateStr) {
		if(dateStr == null || dateStr.trim().equals(""))return null;
		Date date = stringToDate(dateStr,DEFAULT_DATE_FORMAT);
		return dateToFormatString(getDateEnd(date), DEFAULT_DATETIME_FORMAT);
	}
	
	/**
	 * 得到日期字符串 默认格式（yyyy-MM-dd） pattern可以为："yyyy-MM-dd" "HH:mm:ss" "E"
	 */
	public static String formatDate(Date date, Object... pattern) {
		String formatDate = null;
		if (pattern != null && pattern.length > 0) {
			formatDate = dateFormatByPattern(date, pattern[0].toString());
		} else {
			formatDate = dateFormatByPattern(date, "yyyy-MM-dd");
		}
		return formatDate;
	}
	
	/**
	 * 根据传入的number值获取前几个月的最早一天的时间
	  * @param number
	  * @return
	  * @author: baogang
	  * @date: 2016年8月15日 上午10:25:14
	 */
	public static String getLastMonthFirstDay(int number){
		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-number);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMinimum(Calendar.DAY_OF_MONTH));  
		Date strDateTo = calendar.getTime(); 
		Date firstDate = getDateStart(strDateTo);
		return dateToString(firstDate);
	}
	
	/**
	 * 根据传入的number值获取前几个月的最后一天的时间
	  * @param number
	  * @return
	  * @author: baogang
	  * @date: 2016年8月15日 上午10:40:11
	 */
	public static String getLastMonthEndDay(int number){
		Calendar calendar = Calendar.getInstance();  
		int month = calendar.get(Calendar.MONTH);
		calendar.set(Calendar.MONTH, month-number);
		calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));  
		Date strDateTo = calendar.getTime(); 
		Date firstDate = getDateEnd(strDateTo);
		return dateToString(firstDate);
	}
	
	
	/**
	 * 获取间隔小时数
	 * @param d1
	 * @param d2
	 * @return HH:MM:SS,返回时间间隔的值，可以负数
	 */
	public static long hoursDiffs(Date d1, Date d2) {
		long diffMillis = d2.getTime() - d1.getTime();;
		long diffHours = diffMillis / (60L * 60L * 1000L);
		return diffHours;
	}
	
	 /**
     * 判断是否工作日
     * 
     * @param date
     *            应收款日期
     * @return ture:工作日(周一至周五), false:休息日(周六、周日)
     */
    public static boolean isWork(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);

        if(cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            return false;
        } else {
            return true;
        }
    }
	
	public static void main(String[] args) throws InterruptedException {

		Date date = DateUtils.stringToDate("2017-12-31", "yyyy-MM-dd");
		Date result = DateUtils.addMonth(date,2);
		System.out.println(dateToString(result));
		//System.out.println(DateUtils.getLastDayOfMonth(date));

	}
	
	/**
	 * 
	 * 根据传入的相应格式的字符串日期，转为date
	 * @param str
	 * 		传过来的日期格式必须是：xxxx-xx-xx 例如：2000-02-12 不带时分秒
	 * @return
	 */
	public static Date stringToDate1(String str) {
		Date date = null;
		try {
			date = new SimpleDateFormat("yyyy-MM-dd").parse(str);
		} catch (ParseException e) {
			System.out.println("传过来的日期格式错误");
		}
		return date;
	}
}
