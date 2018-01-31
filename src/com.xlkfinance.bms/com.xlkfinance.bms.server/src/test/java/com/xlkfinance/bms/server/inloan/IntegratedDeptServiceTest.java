package com.xlkfinance.bms.server.inloan;

import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.List;

import org.apache.thrift.TException;
import org.junit.Before;
import org.junit.Test;

import com.achievo.framework.thrift.client.BaseClientFactory;
import com.achievo.framework.thrift.client.ThriftBinaryClientFactory;
import com.achievo.framework.thrift.exception.ThriftException;
import com.alibaba.fastjson.JSON;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFileDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService;
import com.xlkfinance.bms.rpc.inloan.PerformJobRemark;

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
public class IntegratedDeptServiceTest {
   private String serverIp = "127.0.0.1";
   private int serverPort = 19090;
   private String basePackage = "com.xlkfinance.bms.rpc";
   private IntegratedDeptService.Client client;

   public BaseClientFactory getFactory(String serviceModuel, String serviceName) {
      String service = new StringBuffer().append(basePackage).append(".").append(serviceModuel).append(".").append(serviceName).toString();
      BaseClientFactory clientFactory = new ThriftBinaryClientFactory(serverIp, serverPort, 20000, service);
      return clientFactory;
   }

   @Before
   public void init() {
      BaseClientFactory clientFactory = getFactory("inloan", "IntegratedDeptService");
      try {
         client = (IntegratedDeptService.Client) clientFactory.getClient();
      } catch (ThriftException e) {
         e.printStackTrace();
         fail(e.getMessage());
      }
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年3月12日下午3:55:25
    */
   @Test
   public void test_queryCollectFile() {
      try {
         CollectFileDTO query = new CollectFileDTO();
         List<CollectFileDTO> list = client.queryCollectFile(query);
         int total = client.getCollectFileTotal(query);
         System.out.println("total:" + total);
         for (CollectFileDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   @Test
   public void test_addCollectFile() {
      CollectFileDTO collectfileDTO = new CollectFileDTO();
      collectfileDTO.setProjectId(11386);
      collectfileDTO.setStatus(1);
      collectfileDTO.setRemark("remark");
      collectfileDTO.setCreaterId(20080);
      collectfileDTO.setUpdateId(20080);
      try {
         client.addCollectFile(collectfileDTO);
         System.out.println(collectfileDTO);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   @Test
   public void test_updateCollectFile() {
      CollectFileDTO collectfileDTO = new CollectFileDTO();
      collectfileDTO.setPid(1);
      collectfileDTO.setStatus(11);
      collectfileDTO.setRemark("111");
      collectfileDTO.setCreaterId(20081);
      collectfileDTO.setUpdateId(20081);
      try {
         client.updateCollectFile(collectfileDTO);
         System.out.println(collectfileDTO);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 查档列表(分页查询)
    *@author:liangyanjun
    *@time:2016年3月12日下午4:08:51
    */
   @Test
   public void test_queryCheckDocument() {
      try {
         //'checkDocumentId':0, 'projectId':0, 'checkStatus':0, 'approvalStatus':0, 'checkDate':null, 'remark':null, 'attachmentId':0, 'createrId':0, 'updateId':0, 'updateDate':null, 'page':'1', 'rows':'10', 'userIds':[20240, 20241, 20242, 20243, 20244, 20245, 20246, 20247, 20248, 20249, 20250, 20251, 20252, 20253, 20254, 20255, 20256, 20257, 20258, 20259, 20260, 20261, 20262, 20263, 20264, 20265, 20266, 20267, 20268, 20269, 20270, 20271, 20272, 20273, 20274, 20275, 20276, 20277, 20278, 20279, 20280, 20281, 20282, 20283, 20284, 20285, 20286, 20287, 20288, 20289, 20290, 20291, 20292, 20293, 20294, 20295, 20296, 20297, 20298, 20299, 20300, 20301, 20302, 20303, 20304, 20305, 20306, 20307, 20308, 20309, 20310, 20311, 20312, 20313, 20314, 20315, 20316, 20317, 20318, 20319, 20320, 20321, 20322, 20323, 20324, 20325, 20326, 20327, 20328, 20329, 20330, 20331, 20332, 20333, 20334, 20335, 10012, 20142, 20336, 20337, 20338, 20339, 20340, 20341, 20342, 20343, 20344, 20345, 20346, 20347, 20348, 20349, 20350, 20351, 20352, 20353, 20354, 20355, 20356, 20357, 20358, 20359, 20360, 20361, 20362, 20363, 20364, 20365, 20366, 20367, 20368, 20369, 20370, 20371, 20372, 20373, 20374, 20375, 20376, 20377, 20378, 20379, 20380, 20381, 20382, 20383, 20384, 20385, 20386, 20387, 20388, 20389, 20390, 20391, 20392, 20394, 20395, 20396, 20397, 20398, 20399, 20400, 20401, 20402, 20403, 20404, 20405, 20406, 20407, 20408, 20409, 20423], 'projectNumber':null, 'projectName':null, 'recFeeStatus':0, 'createrDate':null, 'collectFileDate':null, 'collectFileRemark':null, 'collectFileStatus':0, 'checkLitigationId':0, 'checkLitigationStatus':0, 'checkLitigationApprovalStatus':0, 'projectForeclosureStatus':0, 'performJobRemarkDate':null, 'performJobRemark':null, 'performJobRemarkStatus':0, 'reCheckStatus':0, 'reCheckRemark':null, 'refundFileStatus':0, 'houseName':null, 'buyerName':null, 'sellerName':null
         CheckDocumentDTO parseObject = JSON.parseObject("{'checkDocumentId':0, 'projectId':0, 'checkStatus':0, 'approvalStatus':0, 'checkDate':null, 'remark':null, 'attachmentId':0, 'createrId':0, 'updateId':0, 'updateDate':null, 'page':'1', 'rows':'10', 'userIds':[20240, 20241, 20242, 20243, 20244, 20245, 20246, 20247, 20248, 20249, 20250, 20251, 20252, 20253, 20254, 20255, 20256, 20257, 20258, 20259, 20260, 20261, 20262, 20263, 20264, 20265, 20266, 20267, 20268, 20269, 20270, 20271, 20272, 20273, 20274, 20275, 20276, 20277, 20278, 20279, 20280, 20281, 20282, 20283, 20284, 20285, 20286, 20287, 20288, 20289, 20290, 20291, 20292, 20293, 20294, 20295, 20296, 20297, 20298, 20299, 20300, 20301, 20302, 20303, 20304, 20305, 20306, 20307, 20308, 20309, 20310, 20311, 20312, 20313, 20314, 20315, 20316, 20317, 20318, 20319, 20320, 20321, 20322, 20323, 20324, 20325, 20326, 20327, 20328, 20329, 20330, 20331, 20332, 20333, 20334, 20335, 10012, 20142, 20336, 20337, 20338, 20339, 20340, 20341, 20342, 20343, 20344, 20345, 20346, 20347, 20348, 20349, 20350, 20351, 20352, 20353, 20354, 20355, 20356, 20357, 20358, 20359, 20360, 20361, 20362, 20363, 20364, 20365, 20366, 20367, 20368, 20369, 20370, 20371, 20372, 20373, 20374, 20375, 20376, 20377, 20378, 20379, 20380, 20381, 20382, 20383, 20384, 20385, 20386, 20387, 20388, 20389, 20390, 20391, 20392, 20394, 20395, 20396, 20397, 20398, 20399, 20400, 20401, 20402, 20403, 20404, 20405, 20406, 20407, 20408, 20409, 20423], 'projectNumber':null, 'projectName':null, 'recFeeStatus':0, 'createrDate':null, 'collectFileDate':null, 'collectFileRemark':null, 'collectFileStatus':0, 'checkLitigationId':0, 'checkLitigationStatus':0, 'checkLitigationApprovalStatus':0, 'projectForeclosureStatus':0, 'performJobRemarkDate':null, 'performJobRemark':null, 'performJobRemarkStatus':0, 'reCheckStatus':0, 'reCheckRemark':null, 'refundFileStatus':0, 'houseName':null, 'buyerName':null, 'sellerName':null}",
               CheckDocumentDTO.class);
         List<CheckDocumentDTO> list = client.queryCheckDocument(parseObject);
         int total = client.getCheckDocumentTotal(parseObject);
         System.out.println("total:" + total);
         for (CheckDocumentDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }

   /**
    * 添加查档
    *@author:liangyanjun
    *@time:2016年3月12日下午4:11:41
    */
   @Test
   public void test_addCheckDocument() {
      CheckDocumentDTO dto = new CheckDocumentDTO();
      dto.setProjectId(11386);
      dto.setCheckStatus(1);
      dto.setApprovalStatus(1);
      dto.setCheckDate("2012-12-12");
      dto.setAttachmentId(122);
      dto.setRemark("remark");
      dto.setCreaterId(20080);
      dto.setUpdateId(20080);
      try {
         client.addCheckDocument(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 根据ID更新查档
    *@author:liangyanjun
    *@time:2016年3月12日下午4:11:45
    */
   @Test
   public void test_updateCheckDocument() {
      CheckDocumentDTO dto = new CheckDocumentDTO();
      dto.setPid(1);
      dto.setCheckStatus(2);
      dto.setApprovalStatus(2);
      dto.setCheckDate("2011-11-11");
      dto.setAttachmentId(11);
      dto.setRemark("remark1");
      dto.setCreaterId(20080);
      dto.setUpdateId(20080);
      try {
         client.updateCheckDocument(dto);
         System.out.println(dto);
      } catch (TException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }

   /**
    * 查档首页列表(分页查询)
    *@author:liangyanjun
    *@time:2016年3月12日下午4:40:39
    */
   @Test
   public void test_queryCheckDocumentIndex() {
      try {
         CheckDocumentIndexDTO query = new CheckDocumentIndexDTO();
         List<Integer> userIds=Arrays.asList(20240, 20241, 20242, 20243, 20244, 20245, 20246, 20247, 20248, 20249, 20250, 20251, 20252, 20253, 20254, 20255, 20256, 20257, 20258, 20259, 20260, 20261, 20262, 20263, 20264, 20265, 20266, 20267, 20268, 20269, 20270, 20271, 20272, 20273, 20274, 20275, 20276, 20277, 20278, 20279, 20280, 20281, 20282, 20283, 20284, 20285, 20286, 20287, 20288, 20289, 20290, 20291, 20292, 20293, 20294, 20295, 20296, 20297, 20298, 20299, 20300, 20301, 20302, 20303, 20304, 20305, 20306, 20307, 20308, 20309, 20310, 20311, 20312, 20313, 20314, 20315, 20316, 20317, 20318, 20319, 20320, 20321, 20322, 20323, 20324, 20325, 20326, 20327, 20328, 20329, 20330, 20331, 20332, 20333, 20334, 20335, 10012, 20142, 20336, 20337, 20338, 20339, 20340, 20341, 20342, 20343, 20344, 20345, 20346, 20347, 20348, 20349, 20350, 20351, 20352, 20353, 20354, 20355, 20356, 20357, 20358, 20359, 20360, 20361, 20362, 20363, 20364, 20365, 20366, 20367, 20368, 20369, 20370, 20371, 20372, 20373, 20374, 20375, 20376, 20377, 20378, 20379, 20380, 20381, 20382, 20383, 20384, 20385, 20386, 20387, 20388, 20389, 20390, 20391, 20392, 20394, 20395, 20396, 20397, 20398, 20399, 20400, 20401, 20402, 20403, 20404, 20405, 20406, 20407, 20408, 20409, 20423);
         query.setUserIds(userIds);
//         20240, 20241, 20242, 20243, 20244, 20245, 20246, 20247, 20248, 20249, 20250, 20251, 20252, 20253, 20254, 20255, 20256, 20257, 20258, 20259, 20260, 20261, 20262, 20263, 20264, 20265, 20266, 20267, 20268, 20269, 20270, 20271, 20272, 20273, 20274, 20275, 20276, 20277, 20278, 20279, 20280, 20281, 20282, 20283, 20284, 20285, 20286, 20287, 20288, 20289, 20290, 20291, 20292, 20293, 20294, 20295, 20296, 20297, 20298, 20299, 20300, 20301, 20302, 20303, 20304, 20305, 20306, 20307, 20308, 20309, 20310, 20311, 20312, 20313, 20314, 20315, 20316, 20317, 20318, 20319, 20320, 20321, 20322, 20323, 20324, 20325, 20326, 20327, 20328, 20329, 20330, 20331, 20332, 20333, 20334, 20335, 10012, 20142, 20336, 20337, 20338, 20339, 20340, 20341, 20342, 20343, 20344, 20345, 20346, 20347, 20348, 20349, 20350, 20351, 20352, 20353, 20354, 20355, 20356, 20357, 20358, 20359, 20360, 20361, 20362, 20363, 20364, 20365, 20366, 20367, 20368, 20369, 20370, 20371, 20372, 20373, 20374, 20375, 20376, 20377, 20378, 20379, 20380, 20381, 20382, 20383, 20384, 20385, 20386, 20387, 20388, 20389, 20390, 20391, 20392, 20394, 20395, 20396, 20397, 20398, 20399, 20400, 20401, 20402, 20403, 20404, 20405, 20406, 20407, 20408, 20409, 20423
         List<CheckDocumentIndexDTO> list = client.queryCheckDocumentIndex(query);
         int total = client.getCheckDocumentIndexTotal(query);
         System.out.println("total:" + total);
         for (CheckDocumentIndexDTO dto : list) {
            System.out.println(dto);
         }
      } catch (TException e) {
         e.printStackTrace();
      }
   }
   
   /**
    * 添加查诉讼
    *@author:liangyanjun
    *@time:2016年3月12日下午4:11:41
    */
   @Test
   public void test_addCheckLitigation() {
      String[] projectIds="11479,11491,11472,11484,11487,11489,11496,11498,11469,11475,11473,11482,11480,11481,11483,11485,11493,11495,11477".split(",");
      for (String projectId : projectIds) {
         CheckLitigationDTO dto = new CheckLitigationDTO();
         dto.setProjectId(Integer.parseInt(projectId));
         dto.setCheckStatus(1);
         dto.setApprovalStatus(1);
         dto.setCheckDate("2012-12-12");
         dto.setAttachmentId(122);
         dto.setRemark("remark");
         dto.setCreaterId(20080);
         dto.setUpdateId(20080);
         try {
            client.addCheckLitigation(dto);
            System.out.println(dto);
         } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }
   @Test
   public void test_addPerformJobRemark() {
      //SELECT wmsys.wm_concat(t.project_id) FROM biz_loan_handle_info t
      String[] projectIds="11495,11498,11475,11484,11487,11489,11491,11521,11469,11472,11481,11482,11483,11485,11496,11477".split(",");
      for (String projectId : projectIds) {
         PerformJobRemark dto = new PerformJobRemark();
         dto.setProjectId(Integer.parseInt(projectId));
         dto.setStatus(1);
         dto.setRemark("remark");
         dto.setUpdateId(20080);
         try {
            client.addPerformJobRemark(dto);
            System.out.println(dto);
         } catch (TException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
         }
      }
   }
}
