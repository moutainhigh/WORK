package com.qfang.xk.aom.server.finance;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-09-02 17:42:54 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 保证金记录<br>
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
import com.qfang.xk.aom.rpc.finance.OrgAssureFundRecord;
import com.qfang.xk.aom.rpc.finance.OrgAssureFundRecordService;
import com.xlkfinance.bms.common.util.DateUtils;

public class OrgAssureFundRecordServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.qfang.xk.aom.rpc";
   private OrgAssureFundRecordService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("finance", "OrgAssureFundRecordService");
      try {
         client = (OrgAssureFundRecordService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_insert() throws Exception {
      OrgAssureFundRecord orgAssureFundRecord = new OrgAssureFundRecord();
      orgAssureFundRecord.setPid(1);
      orgAssureFundRecord.setMoney(1);
      orgAssureFundRecord.setRecDate(DateUtils.getCurrentDateTime());
      orgAssureFundRecord.setAccountName("accountName");
      orgAssureFundRecord.setAccount("飞洒发沙发上打扫打扫打扫打扫大是大飒飒的阿萨德撒");
      orgAssureFundRecord.setBank("bank");
      orgAssureFundRecord.setType(1);
      orgAssureFundRecord.setOrgId(3);
      orgAssureFundRecord.setRemark("remark");
      orgAssureFundRecord.setCreaterDate("createrDate");
      orgAssureFundRecord.setCreaterId(1);
      orgAssureFundRecord.setUpdateId(1);
      orgAssureFundRecord.setUpdateDate("updateDate");
      client.insert(orgAssureFundRecord);
   }

   @Test
   public void test_update() throws Exception {
      OrgAssureFundRecord orgAssureFundRecord = new OrgAssureFundRecord();
      orgAssureFundRecord.setPid(1);
      orgAssureFundRecord.setMoney(1);
      orgAssureFundRecord.setRecDate("recDate");
      orgAssureFundRecord.setAccountName("accountName");
      orgAssureFundRecord.setAccount("account");
      orgAssureFundRecord.setBank("bank");
      orgAssureFundRecord.setType(1);
      orgAssureFundRecord.setOrgId(1);
      orgAssureFundRecord.setRemark("remark");
      orgAssureFundRecord.setCreaterDate("createrDate");
      orgAssureFundRecord.setCreaterId(1);
      orgAssureFundRecord.setUpdateId(1);
      orgAssureFundRecord.setUpdateDate("updateDate");
      client.update(orgAssureFundRecord);
   }

   @Test
   public void test_getAll() throws Exception {
      OrgAssureFundRecord orgAssureFundRecord = new OrgAssureFundRecord();
      List<OrgAssureFundRecord> list = client.getAll(orgAssureFundRecord);
      for (OrgAssureFundRecord obj : list) {
         System.out.println(obj);
      }
   }

   @Test
   public void test_getById() throws Exception {
      OrgAssureFundRecord obj = client.getById(9);
      System.out.println(obj);
   }

   @Test
   public void test_deleteById() throws Exception {
      client.deleteById(3);
   }

   @Test
   public void test_deleteByIds() throws Exception {
      List<Integer> pids = new ArrayList<Integer>();
      pids.add(6);
      pids.add(7);
      client.deleteByIds(pids);
   }
}