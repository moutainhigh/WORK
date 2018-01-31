/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日上午10:55:48 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleIndexDTO;
import com.xlkfinance.bms.rpc.inloan.ApplyHandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.BizHandleWorkflowDTO;
import com.xlkfinance.bms.rpc.inloan.ForeclosureIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDifferWarnIndexDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicFileDTO;
import com.xlkfinance.bms.rpc.inloan.HandleDynamicMap;
import com.xlkfinance.bms.rpc.inloan.HandleFlowDTO;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.HouseBalanceDTO;
import com.xlkfinance.bms.rpc.inloan.OrgBizHandlePage;
import com.xlkfinance.bms.rpc.inloan.RefundDetailsDTO;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日上午10:55:48 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务办理Mapper<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizHandleMapper")
public interface BizHandleMapper<T, PK> extends BaseMapper<T, PK> {
   /**
    * 业务受理列表
    *@author:liangyanjun
    *@time:2016年1月15日下午6:30:31
    *@param financeHandleDTO
    *@return
    */
   public List<ApplyHandleIndexDTO> findAllApplyHandleIndex(ApplyHandleIndexDTO applyHandleIndexDTO);

   /**
    * 业务受理总数
    *@author:liangyanjun
    *@time:2016年1月15日下午6:30:35
    *@param financeHandleDTO
    *@return
    */
   public int getApplyHandleIndexTotal(ApplyHandleIndexDTO applyHandleIndexDTO);

   /**
    * 业务处理信息列表
    *@author:liangyanjun
    *@time:2016年1月18日下午5:14:06
    *@param handleInfoDTO
    *@return
    */
   public List<HandleInfoDTO> findAllHandleInfoDTO(HandleInfoDTO handleInfoDTO);

   /**
    * 业务处理信息总数
    *@author:liangyanjun
    *@time:2016年1月18日下午5:14:16
    *@param handleInfoDTO
    *@return
    */
   public int getHandleInfoDTOTotal(HandleInfoDTO handleInfoDTO);

   /**
    * 添加业务处理信息（主表）
    *@author:liangyanjun
    *@time:2016年1月18日上午10:26:12
    *@param handleInfoDTO
    */
   public void addHandleInfo(HandleInfoDTO handleInfoDTO);

   // 根据ID获取业务处理信息
   public HandleInfoDTO getHandleInfoById(int pid);

   /**
    * 根据ID更新业务处理信息
    *@author:liangyanjun
    *@time:2016年1月18日上午10:26:59
    *@param handleInfoDTO
    */
   public void updateHandleInfo(HandleInfoDTO handleInfoDTO);

   /**
    * 查询业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:01:17
    *@param applyHandleInfoDTO
    *@return
    */
   public List<ApplyHandleInfoDTO> findAllApplyHandleInfo(ApplyHandleInfoDTO applyHandleInfoDTO);

   /**
    * 查询业务处理信息总数
    *@author:liangyanjun
    *@time:2016年1月17日上午12:01:30
    *@param applyHandleInfoDTO
    *@return
    */
   public int getApplyHandleInfoTotal(ApplyHandleInfoDTO applyHandleInfoDTO);

   /**
    * 添加申请业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:02:02
    *@param applyHandleInfoDTO
    */
   public int addApplyHandleInfo(ApplyHandleInfoDTO applyHandleInfoDTO);

   // 根据ID获取申请业务处理信息
   public ApplyHandleInfoDTO getApplyHandleInfoById(int pid);

   /**
    * 根据ID更新申请业务处理信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:02:57
    *@param applyHandleInfoDTO
    */
   public int updateApplyHandleInfo(ApplyHandleInfoDTO applyHandleInfoDTO);

   /**
    * 查询赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午1:12:08
    *@param houseBalanceDTO
    *@return
    */
   public List<HouseBalanceDTO> findAllHouseBalance(HouseBalanceDTO houseBalanceDTO);

   /**
    * 查询赎楼及余额回转信息总数
    *@author:liangyanjun
    *@time:2016年1月17日上午1:12:55
    *@param houseBalanceDTO
    *@return
    */
   public int getHouseBalanceTotal(HouseBalanceDTO houseBalanceDTO);
   
   /**
    * 根据项目id 查询赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年3月14日上午10:33:04
    *@param projectId
    *@return
    */
   public List<HouseBalanceDTO> getHouseBalanceListByProjectId(int projectId);

   /**
    * 添加赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午12:46:39
    *@param houseBalanceDTO
    */
   public void addHouseBalance(HouseBalanceDTO houseBalanceDTO);

   // 根据ID获取赎楼及余额回转信息
   public HouseBalanceDTO getHouseBalanceById(int pid);

   /**
    * 根据ID更新赎楼及余额回转信息
    *@author:liangyanjun
    *@time:2016年1月17日上午1:01:24
    *@param houseBalanceDTO
    */
   public void updateHouseBalance(HouseBalanceDTO houseBalanceDTO);

   /**
    * 查询财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:18:57
    *@param refundDetailsDTO
    *@return
    */
   public List<RefundDetailsDTO> findAllRefundDetails(RefundDetailsDTO refundDetailsDTO);

   /**
    * 查询财务退款明细 总数
    *@author:liangyanjun
    *@time:2016年1月17日上午2:19:35
    *@param refundDetailsDTO
    *@return
    */
   public int getRefundDetailsTotal(RefundDetailsDTO refundDetailsDTO);
   
   /**
    * 根据项目id 查询财务退款明细
    *@author:liangyanjun
    *@time:2016年3月14日上午10:51:09
    *@param projectId
    *@return
    */
   public List<RefundDetailsDTO> getRefundDetailsListByProjectId(@Param(value = "projectId") int projectId, @Param(value = "refundPros") List<Integer> refundPros);
   /**
    * 添加财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:20:16
    *@param refundDetailsDTO
    */
   public void addRefundDetails(RefundDetailsDTO refundDetailsDTO);

   // 根据ID获取财务退款明细
   public RefundDetailsDTO getRefundDetailsById(int pid);

   /**
    * 根据ID更新财务退款明细
    *@author:liangyanjun
    *@time:2016年1月17日上午2:20:52
    *@param refundDetailsDTO
    */
   public void updateRefundDetails(RefundDetailsDTO refundDetailsDTO);

   /**
    * 查询办理流程条目
    *@author:liangyanjun
    *@time:2016年1月17日上午2:24:33
    *@param handleFlowDTO
    *@return
    */
   public List<HandleFlowDTO> findAllHandleFlow(HandleFlowDTO handleFlowDTO);

   /**
    * 查询办理流程条目总数
    *@author:liangyanjun
    *@time:2016年1月17日上午2:42:49
    *@param handleFlowDTO
    *@return
    */
   public int getHandleFlowTotal(HandleFlowDTO handleFlowDTO);

   /**
    * 查询办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午1:58:56
    *@param handleDynamicDTO
    *@return
    */
   public List<HandleDynamicDTO> findAllHandleDynamic(HandleDynamicDTO handleDynamicDTO);

   /**
    * 查询办理动态总数
    *@author:liangyanjun
    *@time:2016年1月17日下午1:59:29
    *@param handleDynamicDTO
    *@return
    */
   public int getHandleDynamicTotal(HandleDynamicDTO handleDynamicDTO);

   /**
    * 添加办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午2:00:06
    *@param handleDynamicDTO
    */
   public void addHandleDynamic(HandleDynamicDTO handleDynamicDTO);

   /**
    * 根据ID更新办理动态
    *@author:liangyanjun
    *@time:2016年1月17日下午2:00:39
    *@param handleDynamicDTO
    */
   public void updateHandleDynamic(HandleDynamicDTO handleDynamicDTO);

   /**
    * 查询差异预警处理
    *@author:liangyanjun
    *@time:2016年1月17日下午4:00:38
    *@param handleDifferWarnDTO
    *@return
    */
   public List<HandleDifferWarnDTO> findAllHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO);

   /**
    * 查询差异预警处理总数
    *@author:liangyanjun
    *@time:2016年1月17日下午4:01:08
    *@param handleDifferWarnDTO
    *@return
    */
   public int getHandleDifferWarnTotal(HandleDifferWarnDTO handleDifferWarnDTO);
   
   /**
    * 查询历史预警总数
    */
   public int getHisHandleDifferWarnCount(HandleDifferWarnDTO handleDifferWarnDTO);
   

   /**
    * 添加差异预警处理表
    *@author:liangyanjun
    *@time:2016年1月17日下午4:01:52
    *@param handleDifferWarnDTO
    */
   public void addHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO);

   // 根据ID获取差异预警处理表
   public HandleDifferWarnDTO getHandleDifferWarnById(int pid);

   /**
    * 根据ID更新差异预警处理表
    *@author:liangyanjun
    *@time:2016年1月17日下午4:02:30
    *@param handleDifferWarnDTO
    */
   public void updateHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO);

   /**
    * 查询办理动态文件关联
    *@author:liangyanjun
    *@time:2016年1月25日下午6:00:32
    *@param handleDynamicFileDTO
    *@return
    */
   public List<HandleDynamicFileDTO> findAllHandleDynamicFile(HandleDynamicFileDTO handleDynamicFileDTO);

   /**
    * 查询办理动态文件关联总数
    *@author:liangyanjun
    *@time:2016年1月25日下午6:01:07
    *@param handleDynamicFileDTO
    *@return
    */
   public int getHandleDynamicFileTotal(HandleDynamicFileDTO handleDynamicFileDTO);

   /**
    * 添加办理动态文件关联
    *@author:liangyanjun
    *@time:2016年1月25日下午6:01:48
    *@param handleDynamicFileDTO
    */
   public void addHandleDynamicFile(HandleDynamicFileDTO handleDynamicFileDTO);

   // 根据ID获取办理动态文件关联
   public HandleDynamicFileDTO getHandleDynamicFileById(int pid);

   /**
    * 根据ID更新办理动态文件关联
    *@author:liangyanjun
    *@time:2016年1月25日下午6:02:26
    *@param handleDynamicFileDTO
    */
   public void updateHandleDynamicFile(HandleDynamicFileDTO handleDynamicFileDTO);

   /**
    * 查询业务办理和任务关联
    *@author:liangyanjun
    *@time:2016年1月21日下午7:15:20
    *@param bizHandleWorkflowDTO
    *@return
    */
   public List<BizHandleWorkflowDTO> findAllBizHandleWorkflow(BizHandleWorkflowDTO bizHandleWorkflowDTO);

   /**
    * 添加业务办理和任务关联
    *@author:liangyanjun
    *@time:2016年1月21日下午7:15:17
    *@param bizHandleWorkflowDTO
    *@return
    */
   public boolean addBizHandleWorkflow(BizHandleWorkflowDTO bizHandleWorkflowDTO);

   /**
    * 根据ID更新业务办理和任务关联
    *@author:liangyanjun
    *@time:2016年1月21日下午7:15:14
    *@param bizHandleWorkflowDTO
    *@return
    */
   public boolean updateBizHandleWorkflow(BizHandleWorkflowDTO bizHandleWorkflowDTO);

   /**
    * 根据用户名(任务处理人)，获取可处理的办理动态id的list
    *@author:liangyanjun
    *@time:2016年2月4日下午3:48:23
    *@param userName
    *@return
    */
   public List<Integer> getCanHandleDynamicIds(String userName);

   /**
    * 根据用户id（任务发起人），获取未处理（需要预警）的办理办理信息集合，用途：业务经理在登录系统的时候，查看自己申请的业务办理有没有未完成的办理动态
    *@author:liangyanjun
    *@time:2016年2月4日下午6:58:00
    *@param userId
    *@return
    */
   public List<HandleDifferWarnDTO> getNeedHandleWarn(int userId);

   /**
    * 查询(首页)差异预警处理
    *@author:liangyanjun
    *@time:2016年2月17日下午5:46:08
    *@param userId
    *@return
    */
   public List<HandleDifferWarnDTO> findIndexHandleDifferWarn(int userId);

   /**
    * 根据id删除差异预警处理
    *@author:liangyanjun
    *@time:2016年2月23日上午1:19:17
    *@param handleDifferWarnDTO
    */
   public void delHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO);

   /**
    * 添加差异预警历史处理
    *@author:liangyanjun
    *@time:2016年2月23日上午1:19:20
    *@param handleDifferWarnDTO
    */
   public void addHisHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO);

   /**
    * 查询差异预警历史处理
    *@author:liangyanjun
    *@time:2016年2月23日上午1:19:24
    *@param handleDifferWarnDTO
    *@return
    */
   public List<HandleDifferWarnDTO> findAllHisHandleDifferWarn(HandleDifferWarnDTO handleDifferWarnDTO);

   /**
    * 查询差异预警处理总数
    *@author:liangyanjun
    *@time:2016年2月23日上午1:19:27
    *@param handleDifferWarnDTO
    *@return
    */
   public int getHisHandleDifferWarnTotal(HandleDifferWarnDTO handleDifferWarnDTO);

   /**
    * 查询赎楼列表（分页）
    *@author:liangyanjun
    *@time:2016年3月11日下午4:15:04
    *@param foreclosureIndexDTO
    *@return
    */
   public List<ForeclosureIndexDTO> queryForeclosureIndex(ForeclosureIndexDTO foreclosureIndexDTO);

   /**
    * 查询赎楼列表数据总数
    *@author:liangyanjun
    *@time:2016年3月11日下午4:15:18
    *@param foreclosureIndexDTO
    *@return
    */
   public int getForeclosureIndexTotal(ForeclosureIndexDTO foreclosureIndexDTO);

   /**
    * 查询(首页)差异预警处理总数
    *@author:liangyanjun
    *@time:2016年3月16日上午11:38:39
    *@param handleDifferWarnDTO
    *@return
    */
   public int getIndexHandleDifferWarnTotal(HandleDifferWarnDTO handleDifferWarnDTO);

   /**
    * 根据项目id获取赎楼金额
    *@author:liangyanjun
    *@time:2016年3月22日下午2:57:53
    *@param projectId
    *@return
    */
   public double getForeclosureMoneyByProjectId(int projectId);

   /**
    * 查询办理动态办理条数
    *@author:liangyanjun
    *@time:2016年3月25日下午3:05:24
    *@return
    */
   public List<HandleDynamicMap> qeuryHandleDynamicCountMapList(ApplyHandleIndexDTO applyHandleIndexDTO);

   /**
    * 根据项目id查询办理总历时天数
    *@author:liangyanjun
    *@time:2016年3月29日上午10:32:33
    *@param projectId
    *@return
    */
   public int gethandleDaysByProjectId(int projectId);

   /**
    * 根据办理人ID删除差异预警处理
    *@author:liangyanjun
    *@time:2016年5月9日下午6:28:04
    *@param userId
    */
   public void delHandleDifferWarnByUserId(int userId);

   /**
    * 查询需要处理的预警列表（分页查询）
    *@author:liangyanjun
    *@time:2016年5月14日上午10:06:04
    *@param handleDifferWarnIndexDTO
    *@return
    */
   public List<HandleDifferWarnIndexDTO> queryNeedHandleWarnIndex(HandleDifferWarnIndexDTO handleDifferWarnIndexDTO);

   /**
    * 查询需要处理的预警列表数量
    *@author:liangyanjun
    *@time:2016年5月14日上午10:06:11
    *@param handleDifferWarnIndexDTO
    *@return
    */
   public int getNeedHandleWarnIndexTotal(HandleDifferWarnIndexDTO handleDifferWarnIndexDTO);
   /**
    * 根据办理id查询办理人用户名
    *@author:liangyanjun
    *@time:2016年8月9日下午4:48:39
    *@param handleId
    *@return
    */
   public String getHandleUserName(int handleId);

   /**
    * 机构的保后监管列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月31日上午11:24:19
    *@param query
    *@return
    */
   public List<OrgBizHandlePage> queryOrgBizHandlePage(OrgBizHandlePage query);

   /**
    * 机构的保后监管列表总数
    *@author:liangyanjun
    *@time:2016年8月31日上午11:24:24
    *@param query
    *@return
    */
   public int getOrgBizHandlePageTotal(OrgBizHandlePage query);
   
 
   /**
    * 查询预警列表
    * @param handleDifferWarnDTO
    * @return
    */
   public List<HandleDifferWarnDTO> getHandleDifferWarnList(HandleDifferWarnDTO handleDifferWarnDTO);
   /**
    * 办理动态列表
    * @param projectId
    * @return
    */
   public List<HandleDynamicFileDTO> findHandleDynamicFileByProjectId(@Param(value = "projectId")int projectId);   

}
