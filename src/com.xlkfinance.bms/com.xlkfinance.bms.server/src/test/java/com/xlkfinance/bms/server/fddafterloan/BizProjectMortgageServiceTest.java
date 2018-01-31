package com.xlkfinance.bms.server.fddafterloan;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-19 14:48:40 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 抵押管理信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgage;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgageService;
import com.xlkfinance.bms.common.util.DateUtils;

public class BizProjectMortgageServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private BizProjectMortgageService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("fddafterloan", "BizProjectMortgageService");
      try {
         client = (BizProjectMortgageService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-19 14:48:40
    */
   @Test
   public void test_insert() throws Exception {
      int[] projectIds = { 11389, 11390, 11394, 11395, 11396, 11398, 11399, 11400, 11401};
      for (int projectId : projectIds) {

         BizProjectMortgage bizProjectMortgage = new BizProjectMortgage();
         bizProjectMortgage.setProjectId(projectId);
         bizProjectMortgage.setMortgageStatus(1);
         bizProjectMortgage.setRegisterTime(DateUtils.getCurrentDateTime());
         bizProjectMortgage.setRegisterId(20224);
         bizProjectMortgage.setIssueTime(DateUtils.getCurrentDateTime());
         bizProjectMortgage.setIssueId(20224);
         bizProjectMortgage.setIssueMaterial("issueMaterial");
         bizProjectMortgage.setCancelId(20224);
         bizProjectMortgage.setCancelTime(DateUtils.getCurrentDateTime());
         bizProjectMortgage.setCancelMaterial("cancelMaterial");
         bizProjectMortgage.setReturnId(20224);
         bizProjectMortgage.setReturnTime(DateUtils.getCurrentDateTime());
         bizProjectMortgage.setReturnMaterial("returnMaterial");
         bizProjectMortgage.setCreaterId(20224);
         bizProjectMortgage.setUpdateId(20224);
         client.insert(bizProjectMortgage);
      }
   }

   /**
   *根据id更新数据
   *@author:liangyanjun
   *@time:2017-12-19 14:48:40
   */
   @Test
   public void test_update() throws Exception {
      BizProjectMortgage bizProjectMortgage = new BizProjectMortgage();
      bizProjectMortgage.setPid(1);
      bizProjectMortgage.setProjectId(1);
      bizProjectMortgage.setMortgageStatus(1);
      bizProjectMortgage.setRegisterTime(DateUtils.getCurrentDateTime());
      bizProjectMortgage.setRegisterId(1);
      bizProjectMortgage.setIssueTime(DateUtils.getCurrentDateTime());
      bizProjectMortgage.setIssueId(1);
      bizProjectMortgage.setIssueMaterial("issueMaterial");
      bizProjectMortgage.setCancelId(1);
      bizProjectMortgage.setCancelTime(DateUtils.getCurrentDateTime());
      bizProjectMortgage.setCancelMaterial("cancelMaterial");
      bizProjectMortgage.setReturnId(1);
      bizProjectMortgage.setReturnTime(DateUtils.getCurrentDateTime());
      bizProjectMortgage.setReturnMaterial("returnMaterial");
      bizProjectMortgage.setCreateDate(DateUtils.getCurrentDateTime());
      bizProjectMortgage.setCreaterId(1);
      bizProjectMortgage.setUpdateId(1);
      bizProjectMortgage.setUpdateDate(DateUtils.getCurrentDateTime());
      client.update(bizProjectMortgage);
   }

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-19 14:48:40
    */
   @Test
   public void test_getAll() throws Exception {
      BizProjectMortgage bizProjectMortgage = new BizProjectMortgage();
      List<BizProjectMortgage> list = client.getAll(bizProjectMortgage);
      for (BizProjectMortgage obj : list) {
         System.out.println(obj);
      }
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-19 14:48:40
    */
   @Test
   public void test_getById() throws Exception {
      BizProjectMortgage obj = client.getById(9);
      System.out.println(obj);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-19 14:48:40
    */
   @Test
   public void test_deleteById() throws Exception {
      client.deleteById(8);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-19 14:48:40
    */
   @Test
   public void test_deleteByIds() throws Exception {
      List<Integer> pids = new ArrayList<Integer>();
      pids.add(6);
      pids.add(7);
      client.deleteByIds(pids);
   }
}