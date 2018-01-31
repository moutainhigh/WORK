package com.xlkfinance.bms.server.system;

import static org.junit.Assert.fail;

import java.util.List;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.xlkfinance.bms.rpc.system.SysBankInfo;
import com.xlkfinance.bms.rpc.system.SysBankInfoDto;
import com.xlkfinance.bms.rpc.system.SysBankService;

/**
 * 
  * @ClassName: SysAdvPicInfoServiceTest
  * @author: baogang
  * @date: 2016年4月25日 上午11:43:59
 */
public class SysBankInfoServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private SysBankService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("system", "SysBankService");
      try {
         client = (SysBankService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

 /*  @Test
   public void test_addSysAdvPicInfo() {
	   try {
	         // 构建Workbook对象, 只读Workbook对象
	         // 直接从本地文件创建Workbook
	         InputStream instream = new FileInputStream("E:\\12.xls");
	         jxl.Workbook readwb = Workbook.getWorkbook(instream);
	         // Sheet的下标是从0开始
	         // 获取第一张Sheet表
	         Sheet readsheet = readwb.getSheet(0);
	         // 获取Sheet表中所包含的总列数
	         int rsColumns = readsheet.getColumns();
	         if (rsColumns < 3) {
	            return;
	         }
	         // 获取Sheet表中所包含的总行数
	         int rsRows = readsheet.getRows();
	         if (rsRows < 2) {
	            return;
	         }
	         Map<String, String> WorkOvertime = new TreeMap<>();
	         Map<String, String> late = new TreeMap<>();
	         // 获取指定单元格的对象引用
	         for (int i = 1; i < rsRows; i++) {
	            String parentId = readsheet.getCell(1, i).getContents();
	            String bankName = readsheet.getCell(2, i).getContents();
	            SysBankInfo bankInfo = new SysBankInfo();
	            bankInfo.setType(1);
	            bankInfo.setStatus(1);
	            bankInfo.setParentId(Integer.parseInt(parentId));
	            bankInfo.setBankName(bankName);
	            bankInfo.setBankShortHand(bankName);
	            client.addSysBankInfo(bankInfo);
	         }
	        
	         readwb.close();
	      } catch (Exception e) {
	         e.printStackTrace();
	      }
   }*/
   @Test
   public void test_getAllBank(){
	   try {
		   	SysBankInfo query = new SysBankInfo();
			query.setParentId(0);
			List<SysBankInfoDto> list = client.queryAllSysBankInfo(query);
			for(SysBankInfoDto bankInfo: list){
				System.out.println(bankInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
   }

}
