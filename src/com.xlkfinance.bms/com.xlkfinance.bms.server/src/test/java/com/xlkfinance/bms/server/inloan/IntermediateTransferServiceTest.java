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

import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferIndexDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferService;

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
public class IntermediateTransferServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private IntermediateTransferService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("inloan", "IntermediateTransferService");
      try {
         client = (IntermediateTransferService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_queryIntermediateTransferIndex() {
      try {
         IntermediateTransferIndexDTO intermediateTransferIndexDTO=new IntermediateTransferIndexDTO();
         intermediateTransferIndexDTO.setProjectName("周爽2016041101");
         List<IntermediateTransferIndexDTO> list = client.queryIntermediateTransferIndex(intermediateTransferIndexDTO);
         for (IntermediateTransferIndexDTO d : list) {
            System.out.println(d);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   @Test
   public void test_addHandleInfo() {
      try {
        // pid:0, projectId:11511, applyStatus:2, recMoney:20000.0, recAccount:121212, recDate:2016-04-28, transferMoney:20000.0, transferAccount:131313, 
         //transferDate:2016-04-28, userIds:null, createrDate:null, createrId:20317, updateId:20317, updateDate:null, page:1, rows:10, remark:001, recAccountName:张三, transferAccountName:张三, specialType:1, applyStatusList:null
            IntermediateTransferDTO intermediateTransferDTO=new IntermediateTransferDTO();
            intermediateTransferDTO.setProjectId(11511);
            intermediateTransferDTO.setRecMoney(20000);
            intermediateTransferDTO.setRecAccount("121212");
            intermediateTransferDTO.setRecAccountName("张三");
            intermediateTransferDTO.setRecDate("2016-04-28");
            intermediateTransferDTO.setTransferMoney(20000);
            intermediateTransferDTO.setTransferAccount("131313");
            intermediateTransferDTO.setTransferAccountName("张三");
            intermediateTransferDTO.setTransferDate("2016-04-28");
            intermediateTransferDTO.setCreaterId(20317);
            intermediateTransferDTO.setUpdateId(20317);
            intermediateTransferDTO.setRemark("001");
            intermediateTransferDTO.setSpecialType(1);
            client.addIntermediateTransfer(intermediateTransferDTO);
            String ff=null;
            ff.toCharArray();
      } catch (TException e) {
         e.printStackTrace();
      }
   }
}
