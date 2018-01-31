/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日下午6:32:44 <br>
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleIndexDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;
import com.xlkfinance.bms.rpc.inloan.BizHandleWorkflowDTO;
import com.xlkfinance.bms.rpc.inloan.ForeclosureIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicMap;
import com.xlkfinance.bms.rpc.inloan.HandleFlowDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.HouseBalanceDTO;
import com.xlkfinance.bms.rpc.inloan.RefundDetailsDTO;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日下午6:32:44 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class BizHandleServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private BizHandleService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("inloan", "BizHandleService");
      try {
         client = (BizHandleService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void testName() throws Exception {
      int projectId = 11428;
      String loginUserName = "";
      client.finishAllHandleDynamicTask(projectId, loginUserName);
   }
   /**
    * <!-- 业务受理列表分页查询 -->
    *@author:liangyanjun
    *@time:2016年1月16日下午3:18:22
    */
   @Test
   public void test_findAllApplyHandleIndex() {
//      ApplyHandleIndexDTO parseObject = JSONObject.parseObject("{'pid': 0, 'projectId': 0, 'projectNumber': null, 'projectName': null, 'projectStatus': 0, 'projectPassDate': null, 'recStatus': 0, 'applyHandleStatus': 0, 'createrId': 0, 'userIds': null, 'page': 1, 'rows': '10', 'feedback': null, 'handleDate': null, 'taskName': null, 'productName': null, 'loanMoney': '0.0', 'createrDate': null, 'workFlowStatus': 0, 'productId': 0, 'taskUserName': null, 'handleId': 0, 'recFeeStatus': 0, 'houseName': null, 'buyerName': null, 'sellerName': null}", ApplyHandleIndexDTO.class);
//      System.out.println(parseObject);
      ApplyHandleIndexDTO applyHandleIndexDTO = new ApplyHandleIndexDTO();
      List<Integer> userIds = new ArrayList<Integer>();
       userIds.add(20132);
      // userIds.add(20040);
      applyHandleIndexDTO.setUserIds(null);
      applyHandleIndexDTO.setProjectName("000000000");
//      applyHandleIndexDTO.setWorkFlowStatus(-1);
      applyHandleIndexDTO.setPage(1);
      applyHandleIndexDTO.setRows(10);
      //pid:0, projectId:0, projectNumber:null, projectName:null, projectStatus:0, projectPassDate:null, recStatus:0, applyHandleStatus:0, createrId:0, userIds:null, page:1, rows:10, feedback:null, handleDate:null, taskName:null, productName:null, loanMoney:0.0, createrDate:null, workFlowStatus:0, productId:0, taskUserName:null, handleId:0, recFeeStatus:0, houseName:null, buyerName:null, sellerName:null
      try {
         List<ApplyHandleIndexDTO> findAllApplyHandleIndex = client.findAllApplyHandleIndex(applyHandleIndexDTO);
         System.out.println(findAllApplyHandleIndex.size());
         for (ApplyHandleIndexDTO a : findAllApplyHandleIndex) {
            System.out.println(a);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 业务受理总数
    *@author:liangyanjun
    *@time:2016年1月16日下午3:18:35
    */
   @Test
   public void test_getApplyHandleIndexTotal() {
      ApplyHandleIndexDTO applyHandleIndexDTO = new ApplyHandleIndexDTO();
      List<Integer> userIds = new ArrayList<Integer>();
      userIds.add(20132);
      // userIds.add(20040);
      applyHandleIndexDTO.setUserIds(userIds);
      applyHandleIndexDTO.setPage(1);
      applyHandleIndexDTO.setRows(10);
      try {
         int findAllApplyHandleIndexTotal = client.getApplyHandleIndexTotal(applyHandleIndexDTO);
         System.out.println(findAllApplyHandleIndexTotal);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 业务处理信息列表分页查询
    *@author:liangyanjun
    *@time:2016年1月18日下午5:27:26
    */
   @Test
   public void test_findAllHandleInfoDTO() {
      HandleInfoDTO dto = new HandleInfoDTO();
      List<Integer> userIds = new ArrayList<Integer>();
      userIds.add(20080);
      // userIds.add(20040);
      dto.setUserIds(userIds);
      dto.setPid(139);
      try {
         List<HandleInfoDTO> list = client.findAllHandleInfoDTO(dto);
         int total = client.getHandleInfoDTOTotal(dto);
         System.out.println(total);
         for (HandleInfoDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 添加业务处理信息（主表）
    *@author:liangyanjun
    *@time:2016年1月18日上午10:37:46
    */
   @Test
   public void test_addHandleInfo() {
      // 20080
      int[] projectIds = {11409};
      // 20040
      // int[] projectIds = { 10453, 10477, 10488 };
      try {
         Map<String, String> params = new HashMap<String, String>();
         params.put("userName", "baogang");
         params.put("message", "message");
         params.put("processDefinitionKey", "nonTransactionCashRedemptionHomeProcess");
         params.put("orgId", "4");
         for (int projectId : projectIds) {
            HandleInfoDTO dto = new HandleInfoDTO();
            dto.setProjectId(projectId);
            dto.setApplyHandleStatus(1);
            dto.setRecStatus(1);
            dto.setCreaterId(20084);
            client.addHandleInfo(dto, params);
         }
         System.out.println("add succeed");
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 根据ID更新业务处理信息
    *@author:liangyanjun
    *@time:2016年1月18日上午10:38:16
    */
   @Test
   public void test_updateHandleInfo() {
      try {
         HandleInfoDTO dto = new HandleInfoDTO();
         dto.setPid(1);
         dto.setProjectId(10361);
         dto.setApplyHandleStatus(1);
         dto.setRecStatus(1);
         // dto.setCreaterId(20040);
         client.updateHandleInfo(dto);
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 业务受理列表分页查询
    *@author:liangyanjun
    *@time:2016年1月16日下午3:15:30
    */
   @Test
   public void test_findAllApplyHandleInfo() {
      ApplyHandleInfoDTO dto = new ApplyHandleInfoDTO();
      dto.setProjectId(11515);
      dto.setPage(1);
      dto.setRows(10);
      try {
         int total = client.getApplyHandleInfoTotal(dto);
         System.out.println(total);
         List<ApplyHandleInfoDTO> list = client.findAllApplyHandleInfo(dto);
         for (ApplyHandleInfoDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 添加申请业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:05:45
    */
   @Test
   public void test_addApplyHandleInfo() {
      try {
       
    	  
            ApplyHandleInfoDTO dto = new ApplyHandleInfoDTO();
           
             dto.setSubDate("2015-12-12");
             dto.setContactPerson("ContactPerson2");
             dto.setContactPhone("ContactPhone2");
             dto.setHandleDate("2015-01-01");
             dto.setSpecialCase("SpecialCase2");
             dto.setRemark("Remark2");
            dto.setCreaterId(20084);
            client.addApplyHandleInfo(dto);
         
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 根据ID更新申请业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:05:45
    */
   @Test
   public void test_updateApplyHandleInfo() {
      ApplyHandleInfoDTO dto = new ApplyHandleInfoDTO();
      dto.setPid(3);
      // dto.setHandleId(3);
      dto.setSubDate("2015-12-12");
      dto.setContactPerson("ContactPerson1");
      dto.setContactPhone("ContactPhone1");
      dto.setHandleDate("2015-01-03");
      dto.setSpecialCase("SpecialCase1");
      dto.setRemark("备注");
      // dto.setCreaterId(20040);
      try {
         client.updateApplyHandleInfo(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 查询赎楼及余额回转信息 分页查询
    *@author:liangyanjun
    *@time:2016年1月16日下午3:15:30
    */
   @Test
   public void test_findAllHouseBalance() {
      HouseBalanceDTO dto = new HouseBalanceDTO();
      List<Integer> userIds = new ArrayList<Integer>();
      // userIds.add(20080);
      // userIds.add(20040);
      dto.setUserIds(userIds);
      dto.setPage(1);
      dto.setRows(1000);
      // dto.setHouseClerk("A");
      // dto.setCount(2);
      // dto.setCreaterId(20040);
      // dto.setHandleId(1);
      // dto.setPid(1);
      try {
         int total = client.getHouseBalanceTotal(dto);
         List<HouseBalanceDTO> list = client.findAllHouseBalance(dto);
         System.out.println(total);
         for (HouseBalanceDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   @Test
   public void test_getHouseBalanceByProjectId() {
      try {
         int projectId=11415;
         List<HouseBalanceDTO> list = client.getHouseBalanceListByProjectId(projectId);
         for (HouseBalanceDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   @Test
   public void test_getRefundDetailsListByProjectId() {
      try {
         Integer projectId=11415;
         List<Integer> refundPros=Arrays.asList(1,2);
         List<RefundDetailsDTO> list = client.getRefundDetailsListByProjectId(projectId, refundPros);
         for (RefundDetailsDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 添加赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:05:45
    */
   @Test
   public void test_addHouseBalance() {
      ApplyHandleIndexDTO applyHandleIndexDTO = new ApplyHandleIndexDTO();
      List<Integer> userIds = new ArrayList<Integer>();
      // userIds.add(20080);
      // userIds.add(20040);
      applyHandleIndexDTO.setUserIds(userIds);
      applyHandleIndexDTO.setPage(1);
      applyHandleIndexDTO.setRows(1000);
      try {
         List<ApplyHandleIndexDTO> findAllApplyHandleIndex = client.findAllApplyHandleIndex(applyHandleIndexDTO);
         for (ApplyHandleIndexDTO a : findAllApplyHandleIndex) {
            System.out.println(a);
            for (int i = 1; i < 3; i++) {
               HouseBalanceDTO dto = new HouseBalanceDTO();
               dto.setHandleId(a.getPid());
               dto.setPrincipal(222.22);
               dto.setPayDate("2015-12-03");
               dto.setHouseClerk("HouseClerk");
               dto.setInterest(2.03);
               dto.setDefaultInterest(1.23);
               dto.setBalance(9999.33);
               dto.setBackAccount("backAccount");
               dto.setCount(i);
               dto.setCreaterId(a.getCreaterId());
               client.addHouseBalance(dto);
            }
         }
      } catch (TException e) {
         e.printStackTrace();
      }

   }

   /**
    * 根据ID更新赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午1:02:23
    */
   @Test
   public void test_updateHouseBalance() {
      HouseBalanceDTO dto = new HouseBalanceDTO();
      dto.setPid(592);
//      dto.setHandleId(2);
      dto.setPrincipal(43);
      dto.setPayDate("2015-03-03");
      dto.setHouseClerk("HouseClerk");
      dto.setInterest(4.33);
      dto.setDefaultInterest(4.33);
      dto.setBalance(4.33);
      dto.setBackAccount("backAccount");
      dto.setCount(2);
//      dto.setCreaterId(20040);
      try {
         client.updateHouseBalance(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 查询财务退款明细 分页查询
    *@author:liangyanjun
    *@time:2016年1月17日上午2:25:44
    */
   @Test
   public void test_findAllRefundDetails() {
      RefundDetailsDTO dto = new RefundDetailsDTO();
      List<Integer> userIds = new ArrayList<Integer>();
      // userIds.add(20080);
      // userIds.add(20040);
      dto.setUserIds(userIds);
      dto.setPage(1);
      dto.setRows(10);
      // dto.setCreaterId(20040);
      dto.setRefundPro(1);
      // dto.setHandleId(2);
      // dto.setPid(2);
      try {
         List<RefundDetailsDTO> list = client.findAllRefundDetails(dto);
         int total = client.getRefundDetailsTotal(dto);
         System.out.println(total);
         for (RefundDetailsDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 添加财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:27:58
    */
   @Test
   public void test_addRefundDetails() {
      ApplyHandleIndexDTO applyHandleIndexDTO = new ApplyHandleIndexDTO();
      List<Integer> userIds = new ArrayList<Integer>();
      // userIds.add(20080);
      // userIds.add(20040);
      applyHandleIndexDTO.setUserIds(userIds);
      applyHandleIndexDTO.setPage(1);
      applyHandleIndexDTO.setRows(1000);
      try {
         List<ApplyHandleIndexDTO> findAllApplyHandleIndex = client.findAllApplyHandleIndex(applyHandleIndexDTO);
         for (ApplyHandleIndexDTO a : findAllApplyHandleIndex) {
            System.out.println(a);
            for (int i = 1; i < 5; i++) {
               RefundDetailsDTO dto = new RefundDetailsDTO();
               dto.setHandleId(a.getPid());
               dto.setRefundPro(i);
               dto.setRefundMoney(11.23);
               dto.setRecAccount("RecAccount");
               dto.setRecName("RecName");
               dto.setPayDate("2015-12-03");
               dto.setOperator("Operator");
               dto.setCreaterId(a.getCreaterId());
               client.addRefundDetails(dto);
            }
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 根据ID更新财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:36:39
    */
   @Test
   public void test_updateRefundDetails() {
      RefundDetailsDTO dto = new RefundDetailsDTO();
      dto.setHandleId(2);
      dto.setRefundPro(2);
      dto.setRefundMoney(21);
      dto.setRecAccount("RecAccount21");
      dto.setRecName("RecName21");
      dto.setPayDate("2015-12-21");
      dto.setOperator("Operator21");
      dto.setCreaterId(20040);
      dto.setPid(2);
      try {
         client.updateRefundDetails(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 查询办理流程条目 分页查询
    *@author:liangyanjun
    *@time:2016年1月17日上午2:25:44
    */
   @Test
   public void test_findAllHandleFlow() {
      HandleFlowDTO dto = new HandleFlowDTO();
      dto.setPage(1);
      dto.setRows(10);
      dto.setPid(2);
      try {
         List<HandleFlowDTO> list = client.findAllHandleFlow(dto);
         int total = client.getHandleFlowTotal(dto);
         System.out.println(total);
         for (HandleFlowDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 查询办理动态 分页查询
    *@author:liangyanjun
    *@time:2016年1月17日下午4:08:04
    */
   @Test
   public void test_findAllHandleDynamic() {
      HandleDynamicDTO dto = new HandleDynamicDTO();
      List<Integer> userIds = new ArrayList<Integer>();
      // userIds.add(20080);
      // userIds.add(20040);
      dto.setUserIds(userIds);
      dto.setPage(1);
      dto.setRows(10);
      dto.setHandleId(2);
      // dto.setHandleFlowId(1);
      dto.setCreaterId(20040);
      // dto.setPid(5);
      try {
         List<HandleDynamicDTO> list = client.findAllHandleDynamic(dto);
         int total = client.getHandleDynamicTotal(dto);
         System.out.println(total);
         for (HandleDynamicDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 添加办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午2:01:29
    */
   @Test
   public void test_addHandleDynamic() {
      HandleInfoDTO dto1 = new HandleInfoDTO();
      // List<Integer> userIds = new ArrayList<Integer>();
      // userIds.add(20080);
      // userIds.add(20040);
      // dto.setUserIds(userIds);
      dto1.setPage(1);
      dto1.setRows(1000);
      // dto.setPid(1);
      try {
         List<HandleInfoDTO> list = client.findAllHandleInfoDTO(dto1);
         int total = client.getHandleInfoDTOTotal(dto1);
         System.out.println(total);
         for (HandleInfoDTO obj : list) {
            System.out.println(obj);
            for (int i = 1; i <= 10; i++) {
               HandleDynamicDTO dto = new HandleDynamicDTO();
               dto.setHandleId(obj.getPid());
               dto.setHandleFlowId(i);
               dto.setFinishDate("2011-03-12");
               dto.setHandleDay(1);
               dto.setOperator("Operator");
               dto.setDiffer(1);
               dto.setDifferMonitorCount(2);
               dto.setCreateDate("2011-03-12");
               dto.setRemark("Remark");
               dto.setCreaterId(obj.getCreaterId());
               client.addHandleDynamic(dto);
            }
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 根据ID更新办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午4:07:46
    */
   @Test
   public void test_updateHandleDynamic() {
      HandleDynamicDTO dto = new HandleDynamicDTO();
      dto.setHandleId(2);
      dto.setHandleFlowId(1);
      dto.setFinishDate("2011-03-12");
      dto.setHandleDay(1);
      dto.setOperator("Operator1");
      dto.setDiffer(2);
      dto.setDifferMonitorCount(3);
      dto.setCreateDate("2011-03-12");
      dto.setRemark("Remark1");
      dto.setCreaterId(20040);
      dto.setPid(5);
      try {
         client.updateHandleDynamic(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 查询差异预警处理 分页查询
    *@author:liangyanjun
    *@time:2016年1月17日下午4:08:50
    */
   @Test
   public void test_findAllHandleDifferWarn() {
      HandleDifferWarnDTO dto = new HandleDifferWarnDTO();
      dto.setPage(-1);
      dto.setRows(2);
//      dto.setPid(24);
      try {
         List<HandleDifferWarnDTO> list = client.findAllHandleDifferWarn(dto);
         int total = client.getHandleDifferWarnTotal(dto);
         System.out.println(total);
         for (HandleDifferWarnDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_findAllHisHandleDifferWarn() {
      HandleDifferWarnDTO dto = new HandleDifferWarnDTO();
      dto.setPage(1);
      dto.setRows(10);
      // dto.setPid(24);
      try {
         List<HandleDifferWarnDTO> list = client.findAllHisHandleDifferWarn(dto);
         int total = client.getHisHandleDifferWarnTotal(dto);
         System.out.println(total);
         for (HandleDifferWarnDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_addHisHandleDifferWarn() {
      HandleDifferWarnDTO dto = new HandleDifferWarnDTO();
      dto.setHandleDynamicId(1347);
      dto.setProjectId(10361);
      dto.setProjectName("陈莹娣2015052702");
      dto.setDiffer(2);
      dto.setHandleDate("2011-02-02");
      dto.setHandleAuthor(20040);
      dto.setCreateDate("2011-01-12");
      dto.setRemark("Remark2");
      try {
         client.addHisHandleDifferWarn(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_delHandleDifferWarn() {
      HandleDifferWarnDTO dto = new HandleDifferWarnDTO();
      dto.setPid(85);
      try {
         client.delHandleDifferWarn(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 添加差异预警处理表
    *@author:liangyanjun
    *@time:2016年1月17日下午4:24:45
    */
   @Test
   public void test_addHandleDifferWarn() {
      HandleDifferWarnDTO dto = new HandleDifferWarnDTO();
      dto.setHandleDynamicId(1347);
      dto.setProjectId(10361);
      dto.setProjectName("陈莹娣2015052702");
      dto.setDiffer(2);
      dto.setStatus(2);
      dto.setHandleDate("2011-02-02");
      dto.setHandleAuthor(20040);
      dto.setCreateDate("2011-01-12");
      dto.setRemark("Remark2");
      try {
         client.addHandleDifferWarn(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateHandleDifferWarn() {
      HandleDifferWarnDTO dto = new HandleDifferWarnDTO();
      dto.setPid(24);
      // dto.setHandleDynamicId(1);
      dto.setDiffer(21);
      dto.setStatus(21);
      dto.setHandleDate("2011-02-21");
      dto.setHandleAuthor(20040);
      dto.setCreateDate("2011-01-12");
      dto.setRemark("Remark21");
      try {
         client.updateHandleDifferWarn(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_findAllBizHandleWorkflow() {
      BizHandleWorkflowDTO dto = new BizHandleWorkflowDTO();
      dto.setPage(1);
      dto.setRows(10);
      // dto.setPid(1);
      // dto.setExecutionId("");
      dto.setHandleId(32);
      dto.setUserName("penghl");
      dto.setStatus(Constants.STATUS_ENABLED);
      try {
         List<BizHandleWorkflowDTO> list = client.findAllBizHandleWorkflow(dto);
         for (BizHandleWorkflowDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年1月21日下午7:10:59
    */
   @Test
   public void test_addBizHandleWorkflow() {
      BizHandleWorkflowDTO dto = new BizHandleWorkflowDTO();
      dto.setHandleId(32);
      // dto.setUserName("");
      // dto.setExecutionId("222");
      dto.setStatus(1);
      try {
         client.addBizHandleWorkflow(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年1月21日下午7:10:55
    */
   @Test
   public void test_updateBizHandleWorkflow() {
      BizHandleWorkflowDTO dto = new BizHandleWorkflowDTO();
      // dto.setPid(2);
      dto.setHandleId(32);
      // dto.setUserName("")
      // dto.setExecutionId("222");
      dto.setStatus(1);
      try {
         client.updateBizHandleWorkflow(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_addHandleDynamicFile() {
      HandleDynamicFileDTO dto = new HandleDynamicFileDTO();
      dto.setHandleDynamicId(450);
      dto.setStatus(1);
      dto.setFileId(10726);
      dto.setRemark("setRemark");
      try {
         client.addHandleDynamicFile(dto, null);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年1月25日下午5:59:19
    */
   @Test
   public void test_findAllHandleDynamicFile() {
      HandleDynamicFileDTO dto = new HandleDynamicFileDTO();
      // dto.setHandleDynamicId(450);
      dto.setStatus(1);
      // dto.setFileId(10726);
      // dto.setUploadUserId(222);
      try {
         List<HandleDynamicFileDTO> list = client.findAllHandleDynamicFile(dto);
         int total = client.getHandleDynamicFileTotal(dto);
         System.out.println(total);
         for (HandleDynamicFileDTO obj : list) {
            System.out.println(obj);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 根据用户名，获取可处理的办理动态id的list
    *@author:liangyanjun
    *@time:2016年2月4日下午3:50:36
    */
   @Test
   public void test_getCanHandleDynamicIds() {
      try {
         String userName = "lincaihong";
         List<Integer> dynamicIds = client.getCanHandleDynamicIds(userName);
         System.out.println("size:" + dynamicIds.size());
         for (Integer id : dynamicIds) {
            System.out.println(id);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_getNeedHandleWarn() {
      try {
         int userId = 20080;
         long begin = System.currentTimeMillis();
         List<HandleDifferWarnDTO> list = client.getNeedHandleWarn(userId);
         long end = System.currentTimeMillis();
         System.out.println(end - begin);
         System.out.println("size:" + list.size());
         for (HandleDifferWarnDTO handleDifferWarnDTO : list) {
            System.out.println(handleDifferWarnDTO);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_findIndexHandleDifferWarn() {
      try {
         int userId = 20080;
         List<HandleDifferWarnDTO> list = client.findIndexHandleDifferWarn(userId);
         System.out.println("size:" + list.size());
         for (HandleDifferWarnDTO handleDifferWarnDTO : list) {
            System.out.println(handleDifferWarnDTO);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月11日下午4:25:35
    */
   @Test
   public void test_queryForeclosureIndex() {
      try {
         ForeclosureIndexDTO foreclosureIndexDTO=new ForeclosureIndexDTO();
         List<ForeclosureIndexDTO> list = client.queryForeclosureIndex(foreclosureIndexDTO);
         int total = client.getForeclosureIndexTotal(foreclosureIndexDTO);
         System.out.println("total:" + total);
         for (ForeclosureIndexDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   @Test
   public void test_getForeclosureMoneyByProjectId() {
      try {
         int projectId=11406;
         double money = client.getForeclosureMoneyByProjectId(projectId);
         System.out.println(money);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   @Test
   public void test_getDynamicCount() {
      try {
         ApplyHandleIndexDTO applyHandleIndexDTO=new ApplyHandleIndexDTO();
         List<HandleDynamicMap> handleDynamicMapList = client.qeuryHandleDynamicCountMapList(applyHandleIndexDTO);
         for (HandleDynamicMap handleDynamicMap : handleDynamicMapList) {
            System.out.println(handleDynamicMap);
         }
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
}
