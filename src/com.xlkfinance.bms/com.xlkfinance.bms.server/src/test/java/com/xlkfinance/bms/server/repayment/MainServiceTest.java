/**
 * @Title: RunServerTest.java
 * @Package com.xlkfinance.bms.server
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年02月05日 上午10:37:05
 * @version V1.0
 */
package com.xlkfinance.bms.server.repayment;

import static org.junit.Assert.fail;

import java.util.Calendar;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.util.DateUtil;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.repayment.PreApplyRepayBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;
import com.xlkfinance.bms.server.finance.util.FinanceTypeEnum;

public class MainServiceTest {
	private String serverIp = "127.0.0.1";
	private int serverPort = 19090;
	private String basePackage = "com.xlkfinance.bms.rpc";

	public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
		String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
		BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
		return clientFactory;
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {
	}
	
	/**
	 * 
	  * @Description: 测试-生成还款计划表
	  * @author: zhanghg
	  * @date: 2015年2月5日 上午11:28:27
	 */
	@Test
	public void test_makeRepayMent() {

		try {
			Calendar currDt=Calendar.getInstance();
			currDt.setTime(DateUtil.format("2015-01-31","yyyy-MM-dd"));
			
			Calendar currDt2=Calendar.getInstance();
			currDt2.setTime(DateUtil.format("2015-02-27","yyyy-MM-dd"));
			long l = currDt2.getTimeInMillis() - currDt.getTimeInMillis();
			int d = new Long(l / (1000 * 60 * 60 * 24)).intValue(); 
			System.out.println("==="+d);
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	
	public int CalCycleNum(Calendar outDt,Calendar endDt){
		Calendar currDt=Calendar.getInstance();
		currDt.setTime(outDt.getTime());
		int i=1;
		while(currDt.getTime().getTime()<endDt.getTime().getTime()){
			currDt.setTime(getCalcInterestDt(outDt, i).getTime());
			i++;
		}
		
		return i-1;
	}
	
	public Calendar getCalcInterestDt(Calendar cal,int month){
		Calendar c=Calendar.getInstance();
		c.setTime(cal.getTime());
		int d = cal.get(Calendar.DAY_OF_MONTH);
		c.add(Calendar.MONTH, month);
		if(c.get(Calendar.DAY_OF_MONTH)==d){
			c.add(Calendar.DAY_OF_MONTH, -1);
		}
		return c;
	}
	
	
	
}
