package com.xlkfinance.bms.server.workflow;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BusinessModule;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysUser;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowService;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月21日上午9:56:38 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class WorkflowServiceImplTest {
	private String serverIp = "127.0.0.1";
	private int serverPort = 19090;
	private String basePackage = "com.xlkfinance.bms.rpc";
	private WorkflowService.Client client;

	public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
		String service = new StringBuffer().append(basePackage).append(".")
				.append(serviceModuel).append(".").append(serviceName)
				.toString();
		BaseClientFactory clientFactory = new ThriftBinaryClientFactory(
				serverIp, serverPort, 20000, service);
		return clientFactory;
	}

	@Before
	public void init() {
		BaseClientFactory clientFactory = getFactory(
				BusinessModule.MODUEL_WORKFLOW, "WorkflowService");
		try {
			client = (WorkflowService.Client) clientFactory.getClient();
		} catch (ThriftException e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}

	@Test
	public void test_start() {
		try {
			// int executeDeploy =
			// client.executeDeploy("F:/workFlow/WTRefundTailMoneyProcess.zip");
			int executeDeploy = client
					.executeDeploy("F:/workFlow/IntermediateTransferProcess.zip");
			// int executeDeploy =
			// client.executeDeploy("E:/Code/ForeLoanRequestProcess.zip");
			// int executeDeploy =
			// client.executeDeploy("F:/workFlow/RefundInterestFeeProcess.zip");
			// int executeDeploy2 =
			// client.executeDeploy("F:/workFlow/RefundTailMoneyProcess.zip");
			// int executeDeploy =
			// client.executeDeploy("F:/workFlow/NonTransactionCashRedemptionHomeProcess.zip");
			// int executeDeploy2 =
			// client.executeDeploy("F:/workFlow/TransactionCashRedemptionHomeProcess.zip");
			// client.executeDeploy("E:/Qfang/ElementLendRequestProcess.zip");
			// client.executeDeploy("E:/Qfang/ElementLendRequestProcess.zip");
			// client.executeDeploy("E:/Code/RefundCommissionProcess.zip");
			System.out.println("部署成功：");
		} catch (ThriftServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_findTaskByCandidateUser() {
		try {

			TaskVo query = new TaskVo();
			query.setTaskUserName("malie");
			query.setPage(1);
			query.setRows(100);
			List<TaskVo> queryWorkFlowTodoTask = client
					.queryWorkFlowTodoTask(query);
			int total = client.queryWorkFlowTodoTaskTotal(query);
			System.out.println(total);
			for (TaskVo taskVo : queryWorkFlowTodoTask) {
				System.out.println(taskVo);
			}
		} catch (ThriftServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @author:liangyanjun
	 * @time:2016年2月4日下午2:52:22
	 */
	@Test
	public void test_getFormValueStr() {
		try {
			String taskId = "282688";
			String formKey = "handleFlowId";
			String formValueStr = client.getFormValueStr(taskId, formKey);
			System.out.println(formValueStr);
		} catch (ThriftServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void test_findBizUser() {

		SysUser user = new SysUser();
		

//		user.setPid(20172);
		//user.setOrgId(88);
		
//		int roleId = 20061; //风控初审
//		int roleId = 20062; //风控复审
//		int roleId = 20063; //风控终审
		user.setPid(20273);
		user.setOrgId(108);
		
//		int roleId = 20030; //审查主管
		int roleId = 20033; //风控总监
//		int roleId = 20063; //风控终审
		try {
			List<SysUser> userList = client.findBizUser(user, roleId);
			
			for(SysUser u : userList){
				
				System.out.println("===========user name:"+u.getUserName()+"\n user id :"+u.getPid());
			}
			System.out.println("-----user size ::"+userList.size());
		} catch (ThriftServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	static int i=1;
	@Test
   public void test_executeStart(){
      Runnable target = new Runnable() {

         @Override
         public void run() {
            try {
               int count = i++;
               System.out.println("》》》》》》》》启动线程" + count+client);
               Map<String, String> paramMap = new HashMap<String, String>();
               paramMap.put("userName", count + "");
               paramMap.put("message", "");
               paramMap.put("processDefinitionKey", "makeLoansProcess");
               paramMap.put("refId", "9999");
               paramMap.put("projectId", "8888");
               List<String> nextUserList = new ArrayList<String>();
               client.executeStart(paramMap, nextUserList);
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      };
      new Thread(target).start();
      new Thread(target).start();
   }
	
}
