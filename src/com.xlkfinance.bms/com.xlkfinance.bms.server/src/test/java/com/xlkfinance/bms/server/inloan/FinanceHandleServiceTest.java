/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月12日下午4:55:09 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.xlkfinance.bms.server.inloan;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleService;
import com.xlkfinance.bms.rpc.inloan.FinanceIndexDTO;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月12日下午4:55:09 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class FinanceHandleServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private FinanceHandleService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("inloan", "FinanceHandleService");
      try {
         client = (FinanceHandleService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_queryFinanceIndex() throws TException {
      List<Integer> userIds = new ArrayList<Integer>();
     // userIds.add(20080);
      // userIds.add(20040);
      FinanceIndexDTO financeIndexDTO = new FinanceIndexDTO();
      financeIndexDTO.setUserIds(userIds);
      financeIndexDTO.setPage(1);
      financeIndexDTO.setRows(20);
      financeIndexDTO.setType(2);
      List<Integer> statusList=new ArrayList<Integer>();
      statusList.add(Constants.REC_STATUS_ALREADY_CHARGE);
      statusList.add(Constants.REC_STATUS_LOAN_NO_FINISH);
      financeIndexDTO.setStatusList(statusList);
      int total = client.getFinanceIndexTotal(financeIndexDTO);
      System.out.println(total);
      List<FinanceIndexDTO> findAllFinanceHandle = client.queryFinanceIndex(financeIndexDTO);
      for (FinanceIndexDTO f : findAllFinanceHandle) {
         System.out.println(f);
      }
   }
   @Test
   public void test_findAllFinanceHandle() throws TException {
      List<Integer> userIds = new ArrayList<Integer>();
      userIds.add(20080);
      // userIds.add(20040);
      FinanceHandleDTO financeHandleDTO = new FinanceHandleDTO();
      financeHandleDTO.setUserIds(userIds);
      financeHandleDTO.setPage(1);
      financeHandleDTO.setRows(20);
      int total = client.getFinanceHandleTotal(financeHandleDTO);
      System.out.println(total);
      List<FinanceHandleDTO> findAllFinanceHandle = client.findAllFinanceHandle(financeHandleDTO);
      for (FinanceHandleDTO f : findAllFinanceHandle) {
         System.out.println(f);
      }
   }

   @Test
   public void test_getFinanceHandleById() throws TException {
      FinanceHandleDTO financeHandleById = client.getFinanceHandleById(103);
      System.out.println(financeHandleById);
   }

   @Test
   public void test_insertFinanceHandleDTO() {
      FinanceHandleDTO financeHandleDTO = new FinanceHandleDTO();
       int[] projectIds = { 10361, 10362, 10371, 10377, 10381, 10402, 10404, 10405, 10415, 10418, 10435, 10439, 10442 };
     // int[] projectIds = { 10453, 10477, 10488 };
      try {
         for (int projectId : projectIds) {
            financeHandleDTO.setProjectId(projectId);//
            financeHandleDTO.setStatus(1);
            financeHandleDTO.setCreaterId(20080);
            client.addFinanceHandle(financeHandleDTO);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年1月18日上午9:51:01
    */
   @Test
   public void test_findAllApplyFinanceHandle() {
      List<Integer> userIds = new ArrayList<Integer>();
//      userIds.add(20080);
//      userIds.add(20040);
      ApplyFinanceHandleDTO dto = new ApplyFinanceHandleDTO();
      dto.setUserIds(userIds);
      dto.setPage(1);
      dto.setRows(120);
      try {
         int total = client.getApplyFinanceHandleTotal(dto);
         System.out.println(total);
         List<ApplyFinanceHandleDTO> list = client.findAllApplyFinanceHandle(dto);
         for (ApplyFinanceHandleDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_insertApplyFinanceHandleDTO() {
      try {
         List<Integer> userIds = new ArrayList<Integer>();
         userIds.add(20080);
          userIds.add(20040);
         FinanceHandleDTO financeHandleDTO = new FinanceHandleDTO();
         financeHandleDTO.setUserIds(userIds);
         financeHandleDTO.setPage(1);
         financeHandleDTO.setRows(20);
         int total = client.getFinanceHandleTotal(financeHandleDTO);
         System.out.println(total);
         List<FinanceHandleDTO> findAllFinanceHandle = client.findAllFinanceHandle(financeHandleDTO);
         for (FinanceHandleDTO f : findAllFinanceHandle) {
            for (int i = 1; i <= 6; i++) {
               ApplyFinanceHandleDTO applyFinanceHandleDTO = new ApplyFinanceHandleDTO();
               applyFinanceHandleDTO.setFinanceHandleId(f.getPid());
               applyFinanceHandleDTO.setRecPro(i);
               //            applyFinanceHandleDTO.setRecMoney(122.333);
               //            applyFinanceHandleDTO.setRecAccount("收款账号2");
               //            applyFinanceHandleDTO.setRecDate("2016-01-12");
               //            applyFinanceHandleDTO.setResource("款项来源2");
               //            applyFinanceHandleDTO.setOperator("操作员2");
               //            applyFinanceHandleDTO.setRemark("备注");
               applyFinanceHandleDTO.setCreaterId(f.getCreaterId());
               client.addApplyFinanceHandle(applyFinanceHandleDTO);
            }
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   @Test
   public void test_insertApplyFinanceHandleDTO2() {
      try {
         for (int i = 10; i <= 11; i++) {
            ApplyFinanceHandleDTO applyFinanceHandleDTO = new ApplyFinanceHandleDTO();
            applyFinanceHandleDTO.setFinanceHandleId(6702);
            applyFinanceHandleDTO.setRecPro(i);
            applyFinanceHandleDTO.setRecMoney(0);
            applyFinanceHandleDTO.setRecAccount("");
            applyFinanceHandleDTO.setRecDate("");
            applyFinanceHandleDTO.setResource("");
            applyFinanceHandleDTO.setOperator("");
            applyFinanceHandleDTO.setRemark("备注");
            applyFinanceHandleDTO.setCreaterId(20185);
            client.addApplyFinanceHandle(applyFinanceHandleDTO);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateFinanceHandleDTO() {
      FinanceHandleDTO financeHandleDTO = new FinanceHandleDTO();
      financeHandleDTO.setPid(104);
//      financeHandleDTO.setProjectId(11032);
//      financeHandleDTO.setStatus(2);
      financeHandleDTO.setCreaterDate("2016-02-25");
      try {
         client.updateFinanceHandle(financeHandleDTO);
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateApplyFinanceHandleDTO() {
      ApplyFinanceHandleDTO applyFinanceHandleDTO = new ApplyFinanceHandleDTO();
      applyFinanceHandleDTO.setPid(9);
      applyFinanceHandleDTO.setFinanceHandleId(55);
      applyFinanceHandleDTO.setRecPro(1);
      applyFinanceHandleDTO.setRecMoney(122.333);
      applyFinanceHandleDTO.setRecAccount("收款账号3");
      applyFinanceHandleDTO.setRecDate("2016-01-13");
      applyFinanceHandleDTO.setResource("款项来源3");
      applyFinanceHandleDTO.setOperator("操作员3");
      try {
         client.makeLoans(applyFinanceHandleDTO, 1,-1,-1);
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   @Test
   public void test_getApplyFinanceHandleById() {
      try {
         
         int id=543;
         ApplyFinanceHandleDTO newRecApplyFinance = client.getApplyFinanceHandleById(id);
         System.out.println(newRecApplyFinance);
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   @Test
   public void test_getNewRecApplyFinance() {
      try {
         
         int projectId=11285;
         ApplyFinanceHandleDTO newRecApplyFinance = client.getNewRecApplyFinance(projectId);
         System.out.println(newRecApplyFinance);
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   @Test
   public void test_getRecMoney() {
      try {
         int projectId=11388;
         List<Integer> recPros = Arrays.asList(Constants.REC_PRO_3, Constants.REC_PRO_5);
         double recMoney = client.getRecMoney(projectId, recPros);
         System.out.println(recMoney);
      } catch (TException e) {
         e.printStackTrace();
      }
   }
}
