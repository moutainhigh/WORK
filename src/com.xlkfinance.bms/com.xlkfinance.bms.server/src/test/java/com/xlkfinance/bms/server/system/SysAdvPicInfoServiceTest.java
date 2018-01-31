package com.xlkfinance.bms.server.system;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.system.SysAdvPicInfo;
import com.xlkfinance.bms.rpc.system.SysAdvPicService;

/**
 * 
  * @ClassName: SysAdvPicInfoServiceTest
  * @author: baogang
  * @date: 2016年4月25日 上午11:43:59
 */
public class SysAdvPicInfoServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private SysAdvPicService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("system", "SysAdvPicService");
      try {
         client = (SysAdvPicService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   @Test
   public void test_addSysAdvPicInfo() {
      try {
         SysAdvPicInfo picInfo = new SysAdvPicInfo();
         picInfo.setTitle("test1223");
         picInfo.setContent("本报讯(记者万勤 通讯员李鹏程)快递哥上门收包裹听到包裹内有手机铃声，安检筛查时发现15部手机中有一条被盗手机信息，这一重大发现帮民警揪出一个江西籍盗销手机一条龙团伙，民警扮快递员在他们将扒窃的手机准备再次寄往外地时，将其当场擒获。3月31日上午9点左右，寄件人李某电话联系一快递公司，要求快递员前往江夏区大学园路清风别墅上门收包裹。快递员上门收件时发现一包裹内不时发出手机铃声，李某解释说是寄给深圳朋友的手机。");
         picInfo.setUrl("http://www.baixu.com");
         picInfo.setPictureUrl("");
         picInfo.setOrderIndex(2);
         picInfo.setCreateId(20084); 
         picInfo.setPid(3);
         picInfo.setStatus(2);
         client.updateSysAdvPicInfo(picInfo);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
   
   @Test
   public void test_queryAll(){
	   try {
	         SysAdvPicInfo picInfo = new SysAdvPicInfo();
	          
	         picInfo.setPid(3);
	         picInfo.setStatus(2);
	         List<SysAdvPicInfo> list = client.querySysAdvPics(picInfo);
	         int count = client.getSysAdvCount(picInfo);
	         for(SysAdvPicInfo s : list){
	        	 System.out.println(s);
	         }
	         System.out.println(count);
	         SysAdvPicInfo querySysAdvPic = client.querySysAdvPic(3);
	         System.out.println(querySysAdvPic);
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
   }

}
