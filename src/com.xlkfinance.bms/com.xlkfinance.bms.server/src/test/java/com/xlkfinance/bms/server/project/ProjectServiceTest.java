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
package com.xlkfinance.bms.server.project;

import static org.junit.Assert.fail;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.beforeloan.Project;
import com.xlkfinance.bms.rpc.beforeloan.ProjectService;
import com.xlkfinance.bms.rpc.project.BizCalLoanMoney;
import com.xlkfinance.bms.rpc.project.ProjectInfoService;
import com.xlkfinance.bms.rpc.repayment.RepaymentPlanBaseDTO;

public class ProjectServiceTest {
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
			BaseClientFactory clientFactory = getFactory("project", "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			
			List<RepaymentPlanBaseDTO> list= client.makeRepaymentPlan(18729, 20172);
			
			for(RepaymentPlanBaseDTO rp:list){
				System.out.println("还款日："+
				rp.getPlanRepayDt()+"  期数："+
				rp.getPlanCycleNum()+"  应还本金："+
				rp.getShouldPrincipal()+"  应还利息："+
				rp.getShouldInterest()+"  应还合计："+
				rp.getTotal()+"  本金余额："+
				rp.getPrincipalBalance()
						
						);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}

	}
	@Test
	public void makeOverdue(){
		try
		{
			BaseClientFactory clientFactory = getFactory("project", "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			String planRepayDt =DateUtils.dateToFormatString(new Date(), "yyyy-MM-dd");
			
			client.makeProjectOverdue(planRepayDt );
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testmakeBizCalLoanMoney(){

		try {
			BaseClientFactory clientFactory = getFactory("project", "ProjectInfoService");
			ProjectInfoService.Client client = (ProjectInfoService.Client) clientFactory.getClient();
			
			BizCalLoanMoney condition = new BizCalLoanMoney();
			condition.setFeeRate(2);
			condition.setRentMoney(5000);
			condition.setLoanTerm(24);
			condition.setLoanCoef(0.5);
			List<BizCalLoanMoney> list= client.makeBizCalLoanMoney(condition );
			
			for(BizCalLoanMoney rp:list){
				System.out.println("租赁期限："+rp.getRentTerm()+" 签约租金: "+rp.getRentMoney()
						+" 租金回报 : "+rp.getRentalReturn()+" 可贷系数: "+rp.getLoanCoef()+
						" 贷款金额: "+rp.getLoanMoney()+" 贷款期限: "+rp.getLoanTerm()+
						" 总还款: "+rp.getRepaymentMoneyTotal()+" 月还款: "+rp.getMonthlyRepaymentMoney()+
						" 借款人承担: "+rp.getPayMoney()+" 租金回报-总还款: "+rp.getResultMoney());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
	
	
}
