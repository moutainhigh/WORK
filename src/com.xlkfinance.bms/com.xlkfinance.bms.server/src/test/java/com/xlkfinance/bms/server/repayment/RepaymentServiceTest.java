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

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.xlkfinance.bms.rpc.beforeloan.CalcOperDto;
import com.xlkfinance.bms.rpc.beforeloan.Loan;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.repayment.PreApplyRepayBaseDTO;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;

public class RepaymentServiceTest {
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
			BaseClientFactory clientFactory = getFactory("beforeloan", "ProjectService");
			ProjectService.Client client = (ProjectService.Client) clientFactory.getClient();
			
			Loan loan=new Loan();
			//loan.setPid(742);
			//loan.setRepayFun(225);
			loan.setProjectId(10375);
			loan.setPid(10152);
			loan.setRepayFun(225);
			loan.setCreditAmt(5000);
			loan.setRepayDate(15);
			loan.setPlanOutLoanDt("2015-01-05");
			loan.setPlanRepayLoanDt("2015-06-04");
			loan.setRepayCycle(5);
			loan.setRepayOption(2);
			
			
			loan.setMonthLoanInterest(100);
			loan.setMonthLoanMgr(100);//0.5
			loan.setMonthLoanOtherFee(100);
			loan.setPrepayLiqDmgProportion(100);
			
			CalcOperDto dto=new CalcOperDto(1, 100,100,100, 500, "2015-04-20", 6,1,"2015-04-20",0);
			
			
			List<RepaymentPlanBaseDTO> list= client.makeRepayMent(loan, 1,dto);
			
			for(RepaymentPlanBaseDTO rp:list){
				System.out.println("==="+
				rp.getPlanRepayDt()+"====="+
				rp.getPlanCycleNum()+"====="+
				rp.getShouldPrincipal()+"====="+
				rp.getShouldMangCost()+"====="+
				rp.getShouldOtherCost()+"====="+
				rp.getShouldInterest()+"====="+
				rp.getTotal()+"====="+
				rp.getPrincipalBalance()
						
						);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
}
