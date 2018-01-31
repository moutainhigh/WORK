package com.xlkfinance.bms.common.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.xlkfinance.bms.rpc.workday.WorkDay;
import com.xlkfinance.bms.rpc.workday.WorkDayService;

public class CheckWorkDayUtil {
	// fromDate=最新查档时间 endDate=当前时间 "2017-07-12 09"
	public static boolean checkDate(String fromDate, String endDate) {
		// 根据录单员查询是属于哪个城市
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH");
		// 通过日历获取下一天日期
		Calendar cal = Calendar.getInstance();
//		cal.setTime(sf.parse(fromDate));
		cal.add(Calendar.DAY_OF_YEAR, +1);
		String nextFromTime = sf.format(cal.getTime());
		// 获取最新查档日期的下一天 "2017-07-12"
		String nextFromDate = nextFromTime.substring(0, 10);

		// 现在去数据库校验是不是法定假日
		WorkDay workDay = new WorkDay();
		workDay.setIsHolidays(0);
		List<WorkDay> workDayList = null;
		// BaseClientFactory clientFactory = null;
		// clientFactory =
		// getFactory(BusinessModule.MODUEL_WORK_DAY,"WorkDayService");
		// WorkDayService.Client client1 = (WorkDayService.Client)
		// clientFactory.getClient();
//		System.out.println(client1);
		WorkDayService workImpl = new WorkDayService();
//		workImpl.
		// 查询出所有的法定假日
		// WorkDayService.g
		// workDayList =;
//		workDay.setCorrectDate(nextFromDate);
		// 所有的具体的法定节假日
		List<String> correctDateList = null;
		if (workDayList != null && workDayList.size() > 0) {
			for (int i = 0; i < workDayList.size(); i++) {
				correctDateList.add(workDayList.get(i).getCorrectDate());
			}
		}

		// 查询这个时间差的法定假日有多少天
		WorkDay workDay1 = new WorkDay();
		workDay1.setFromCorrectDate(fromDate.substring(0, 10));
		// 获得当前时间，放款时间
		// String nowStr = DateUtils.getCurrentDateIncludeHour().substring(0,
		// 10);
		workDay1.setToCorrectDate(endDate);
		workDay1.setIsHolidays(0);
		List<WorkDay> workDayList1 = null;
		// 得到时间差的法定假日天数
//		workDayList1 = client1.getWorkDay(workDay1);
		List<String> correctDateList1 = new ArrayList<String>();
		// 获取所有法定假日的具体日期
		if (workDayList != null && workDayList1.size() > 0) {
			for (int i = 0; i < workDayList1.size(); i++) {
				correctDateList1.add(workDayList1.get(i).getCorrectDate());
			}
		}

		// 获取所有的周末
		List<WorkDay> list2 = null;

		// 判断法定假日中有几个重复的
		int repeatTime = getIntersection(correctDateList1, correctDateList);
		System.out.println(repeatTime);

		return true;

	}

	// 获取时间差之间有几个法定节假日
	public static int getIntersection(List<String> list1, List<String> list2) {
		List<String> result = new ArrayList<String>();
		for (String integer : list2) {// 遍历list1
			if (list1.contains(integer)) {// 如果存在这个数
				result.add(integer);// 放进一个list里面，这个list就是交集
			}
		}
		return result.size();
	}

}
