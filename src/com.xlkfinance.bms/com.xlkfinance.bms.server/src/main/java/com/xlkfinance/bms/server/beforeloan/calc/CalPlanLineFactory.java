/**
 * @Title: CalPlanLineFactory.java
 * @Package com.xlkfinance.bms.server.beforeloan.service
 * @Description: 贷款利息计算工厂类
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月4日 下午2:43:40
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.calc;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;

@Component("calPlanLineFactory")
public class CalPlanLineFactory {
	
	@Resource(name = "monthInterestCalPlanLine")
	private CalPlanLine monthInterestCalPlanLine;

	@Resource(name = "dayInterestCalPlanLine")
	private CalPlanLine dayInterestCalPlanLine;
	
	@Resource(name = "eqInterestCalPlanLine")
	private CalPlanLine eqInterestCalPlanLine;

	@Resource(name = "eqPrincipalCalPlanLine")
	private CalPlanLine eqPrincipalCalPlanLine;
	
	@Resource(name = "eqPrincipalInterestCalPlanLine")
	private CalPlanLine eqPrincipalInterestCalPlanLine;
	
	@Resource(name = "otherWayCalPlanLine")
	private CalPlanLine otherWayCalPlanLine;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;
		
	public CalPlanLine factory(int type){
		String lookupVal=sysLookupValMapper.getSysLookupValByPid(type);
		int index=1;
		if(lookupVal!=null && !"".equals(lookupVal)){
			index=Integer.parseInt(lookupVal);
		}
		switch (index) {
			case 1:
				return monthInterestCalPlanLine;
			case 2:
				dayInterestCalPlanLine.setDay(5);
				return dayInterestCalPlanLine;
			case 3:
				dayInterestCalPlanLine.setDay(7);
				return dayInterestCalPlanLine;
			case 4:
				dayInterestCalPlanLine.setDay(10);
				return dayInterestCalPlanLine;
			case 5:
				dayInterestCalPlanLine.setDay(15);
				return dayInterestCalPlanLine;
			case 6:
				return eqPrincipalCalPlanLine;
			case 7:
				return eqInterestCalPlanLine;
			case 8:
				return eqPrincipalInterestCalPlanLine;
			case 9:
				return otherWayCalPlanLine;
		}
		return null;
	}
}
