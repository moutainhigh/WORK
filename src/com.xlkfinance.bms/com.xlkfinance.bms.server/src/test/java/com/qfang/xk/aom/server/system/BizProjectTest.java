/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-19 11:43:41 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务申请表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.qfang.xk.aom.server.system;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.qfang.xk.aom.rpc.project.BizProjectService;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.beforeloan.DataInfo;

public class BizProjectTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private BizProjectService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("project", "BizProjectService");
      try {
         client = (BizProjectService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      BizProject bizProject = new BizProject();
		      bizProject.setPid(1);
		      bizProject.setAcctId(1);
		      bizProject.setProjectType(1);
		      bizProject.setProjectName("projectName");
		      bizProject.setProjectNumber("projectNumber");
		      bizProject.setPmUserId(1);
		      bizProject.setBusinessCatelog("businessCatelog");
		      bizProject.setBusinessType("businessType");
		      bizProject.setFlowCatelog("flowCatelog");
		      bizProject.setMyType("myType");
		      bizProject.setMyMain("myMain");
		      bizProject.setLoanInterestRecord(1);
		      bizProject.setLoanMgrRecord(1);
		      bizProject.setIsAllowPrepay(1);
		      bizProject.setIsReturnInterest(1);
		      bizProject.setLoanOtherFee(1);
		      bizProject.setRequestStatus(1);
		      bizProject.setRequestDttm("requestDttm");
		      bizProject.setCompleteDttm("completeDttm");
		      bizProject.setStatus(1);
		      bizProject.setCirculateType(1);
		      bizProject.setSurveyResult("surveyResult");
		      bizProject.setComments("comments");
		      bizProject.setIsRejected(1);
		      bizProject.setProductId(1);
		      bizProject.setBusinessSource(1);
		      bizProject.setAddress("address");
		      bizProject.setBusinessContacts("businessContacts");
		      bizProject.setContactsPhone("contactsPhone");
		      bizProject.setInnerOrOut(1);
		      bizProject.setBusinessCategory(1);
		      bizProject.setIsNotarization(1);
		      bizProject.setIsDelete(1);
		      bizProject.setIsChechan(1);
		      bizProject.setManagers("managers");
		      bizProject.setManagersPhone("managersPhone");
		      bizProject.setForeclosureStatus(1);
		      bizProject.setBusinessSourceNo(1);
		      bizProject.setCollectFileStatus(1);
		      bizProject.setProjectSource(1);
		      bizProject.setAuditorOpinion("auditorOpinion");
		      bizProject.setIsSeller(1);
		      bizProject.setDeclaration("declaration");
		      bizProject.setRefundFileStatus(1);
		      bizProject.setChechanDate("chechanDate");
		      bizProject.setChechanUserId(1);
		      bizProject.setChechanCause("chechanCause");
		      bizProject.setOrgId(1);
		      bizProject.setOrgCustomerName("orgCustomerName");
		      bizProject.setOrgCustomerPhone("orgCustomerPhone");
		      bizProject.setOrgCustomerCard("orgCustomerCard");
		      bizProject.setPlanLoanDate("planLoanDate");
		      bizProject.setPlanLoanMoney(1);
		      bizProject.setLoanRate(1);
		      bizProject.setMaxLoanRate(1);
		      bizProject.setIsClosed(1);
      client.insert(bizProject);
   }
   @Test
   public void test_update() throws Exception {
      BizProject bizProject = new BizProject();
		      bizProject.setPid(1);
		      bizProject.setAcctId(1);
		      bizProject.setProjectType(1);
		      bizProject.setProjectName("projectName");
		      bizProject.setProjectNumber("projectNumber");
		      bizProject.setPmUserId(1);
		      bizProject.setBusinessCatelog("businessCatelog");
		      bizProject.setBusinessType("businessType");
		      bizProject.setFlowCatelog("flowCatelog");
		      bizProject.setMyType("myType");
		      bizProject.setMyMain("myMain");
		      bizProject.setLoanInterestRecord(1);
		      bizProject.setLoanMgrRecord(1);
		      bizProject.setIsAllowPrepay(1);
		      bizProject.setIsReturnInterest(1);
		      bizProject.setLoanOtherFee(1);
		      bizProject.setRequestStatus(1);
		      bizProject.setRequestDttm("requestDttm");
		      bizProject.setCompleteDttm("completeDttm");
		      bizProject.setStatus(1);
		      bizProject.setCirculateType(1);
		      bizProject.setSurveyResult("surveyResult");
		      bizProject.setComments("comments");
		      bizProject.setIsRejected(1);
		      bizProject.setProductId(1);
		      bizProject.setBusinessSource(1);
		      bizProject.setAddress("address");
		      bizProject.setBusinessContacts("businessContacts");
		      bizProject.setContactsPhone("contactsPhone");
		      bizProject.setInnerOrOut(1);
		      bizProject.setBusinessCategory(1);
		      bizProject.setIsNotarization(1);
		      bizProject.setIsDelete(1);
		      bizProject.setIsChechan(1);
		      bizProject.setManagers("managers");
		      bizProject.setManagersPhone("managersPhone");
		      bizProject.setForeclosureStatus(1);
		      bizProject.setBusinessSourceNo(1);
		      bizProject.setCollectFileStatus(1);
		      bizProject.setProjectSource(1);
		      bizProject.setAuditorOpinion("auditorOpinion");
		      bizProject.setIsSeller(1);
		      bizProject.setDeclaration("declaration");
		      bizProject.setRefundFileStatus(1);
		      bizProject.setChechanDate("chechanDate");
		      bizProject.setChechanUserId(1);
		      bizProject.setChechanCause("chechanCause");
		      bizProject.setOrgId(1);
		      bizProject.setOrgCustomerName("orgCustomerName");
		      bizProject.setOrgCustomerPhone("orgCustomerPhone");
		      bizProject.setOrgCustomerCard("orgCustomerCard");
		      bizProject.setPlanLoanDate("planLoanDate");
		      bizProject.setPlanLoanMoney(1);
		      bizProject.setLoanRate(1);
		      bizProject.setMaxLoanRate(1);
		      bizProject.setIsClosed(1);
      client.update(bizProject);
   }

   @Test
   public void test_getAll() throws Exception {
	   List<DataInfo> projectDataInfos = client.getProjectDataInfos(12229);
	   System.out.println(projectDataInfos);
   }

   @Test
   public void test_getById() throws Exception {
      BizProject obj = client.getById(9);
      System.out.println(obj);
   }
   
   @Test
   public void test_updateAvailableLimit(){
	   try {
		BizProject project = client.getById(12672);
		project.setPlanLoanMoney(5000000);
		client.updateAvailableLimit(project);
	} catch (TException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
   }
}
