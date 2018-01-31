package com.xlkfinance.bms.server.inloan;

import static org.junit.Assert.fail;

import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.common.constant.BizDynamicConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleIndexDTO;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;
import com.xlkfinance.bms.rpc.inloan.BizDynamicService;
import com.xlkfinance.bms.rpc.inloan.BizHandleService;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月28日下午2:42:51 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class BizDynamicServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private BizDynamicService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("inloan", "BizDynamicService");
      try {
         client = (BizDynamicService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年1月26日下午5:13:55
    *@throws TException
    */
   @Test
   public void test_queryBizDynamic() {
      try {
         BizDynamic dto = new BizDynamic();
         dto.setPage(-1);
         dto.setProjectId(11590);
         dto.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_INLOAN);
         dto.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_15);
//         int total = client.getBizDynamicTotal(dto);
//         System.out.println(total);
         List<BizDynamic> list = client.queryBizDynamic(dto);
//         for (BizDynamic f : list) {
//            System.out.println(f);
//         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_addBizDynamic() {
      try {
         BizDynamic dto = new BizDynamic();
         dto.setProjectId(11111);
         dto.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_INLOAN);
         dto.setDynamicNumber("3");
         dto.setParentDynamicNumber("2");
         dto.setStatus(1);
         dto.setFinishDate(DateUtils.getCurrentDate());
         dto.setRemark("setRemark");
         client.addBizDynamic(dto);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   @Test
   public void test_initBizDynamic() {
      try {
         BaseClientFactory clientFactory = getFactory("inloan", "BizHandleService");
         BizHandleService.Client bizHandleServiceClient=(BizHandleService.Client) clientFactory.getClient();
         ApplyHandleIndexDTO applyHandleIndexDTO=new ApplyHandleIndexDTO();
         applyHandleIndexDTO.setPage(1);
         applyHandleIndexDTO.setRows(10000000);
         List<ApplyHandleIndexDTO> list = bizHandleServiceClient.findAllApplyHandleIndex(applyHandleIndexDTO);
         for (ApplyHandleIndexDTO p : list) {
            System.out.println(p.getProjectId());
            client.initBizDynamic(p.getProjectId());
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateBizDynamic() {
      try {
         BizDynamic dto = new BizDynamic();
         dto.setPid(1);
         dto.setProjectId(11111);
         dto.setDynamicNumber("1");
         dto.setStatus(1);
         dto.setFinishDate(DateUtils.getCurrentDate());
         dto.setRemark("1111");
         client.updateBizDynamic(dto);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   @Test
   public void test_addOrUpdateBizDynamic() {
      try {
         BizDynamic bizDynamic=new BizDynamic();
         bizDynamic.setProjectId(11590);
         bizDynamic.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_INLOAN);
         bizDynamic.setDynamicNumber(BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_15);
         bizDynamic.setDynamicName(BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_MAP.get(BizDynamicConstant.DYNAMIC_NUMBER_INLOAN_15));
         bizDynamic.setHandleAuthorId(20224);
         bizDynamic.setFinishDate(DateUtils.getCurrentDate());
         bizDynamic.setStatus(BizDynamicConstant.STATUS_3);
         bizDynamic.setRemark("dddddd");
         client.addOrUpdateBizDynamic(bizDynamic);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_delBizDynamic() {
      try {
         BizDynamic dto = new BizDynamic();
         dto.setPid(2);
         dto.setProjectId(2222);
         dto.setDynamicNumber("222");
         client.delBizDynamic(dto);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_delBizDynamicByCascade() {
      try {
         BizDynamic dto = new BizDynamic();
         dto.setProjectId(11111);
         dto.setModuelNumber(BizDynamicConstant.MODUEL_NUMBER_INLOAN);
         dto.setParentDynamicNumber("1");
         client.delBizDynamicByCascade(dto);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

}
