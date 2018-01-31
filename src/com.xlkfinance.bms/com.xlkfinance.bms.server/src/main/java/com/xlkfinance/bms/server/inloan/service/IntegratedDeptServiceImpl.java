package com.xlkfinance.bms.server.inloan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFileDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFileMap;
import com.xlkfinance.bms.rpc.inloan.CollectFilePrintInfo;
import com.xlkfinance.bms.rpc.inloan.HandleInfoDTO;
import com.xlkfinance.bms.rpc.inloan.IntegratedDeptService.Iface;
import com.xlkfinance.bms.rpc.inloan.PerformJobRemark;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectMapper;
import com.xlkfinance.bms.server.inloan.mapper.BizHandleMapper;
import com.xlkfinance.bms.server.inloan.mapper.IntegratedDeptMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月12日下午2:38:42 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：综合部管理（收件查档）  Service <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("integratedDeptServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.IntegratedDeptService")
public class IntegratedDeptServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(IntegratedDeptServiceImpl.class);
   @Resource(name = "integratedDeptMapper")
   private IntegratedDeptMapper integratedDeptMapper;
   
   @Resource(name = "projectMapper")
   private ProjectMapper projectMapper;
   
   @Resource(name = "bizHandleMapper")
   private BizHandleMapper bizHandleMapper;
  /**
   * 收件列表(分页查询)
   *@author:liangyanjun
   *@time:2016年12月23日下午5:26:03
   */
   @Override
   public List<CollectFileDTO> queryCollectFile(CollectFileDTO collectfileDTO) throws TException {
      return integratedDeptMapper.queryCollectFile(collectfileDTO);
   }
   /**
    * 收件列表查询总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public int getCollectFileTotal(CollectFileDTO collectfileDTO) throws TException {
      return integratedDeptMapper.getCollectFileTotal(collectfileDTO);
   }
   /**
    * 添加收件
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean addCollectFile(CollectFileDTO collectfileDTO) throws TException {
      integratedDeptMapper.addCollectFile(collectfileDTO);
      return true;
   }
   /**
    * 根据ID获取收件
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public CollectFileDTO getCollectFileById(int pid) throws TException {
      return integratedDeptMapper.getCollectFileById(pid);
   }
   /**
    * 根据ID更新收件
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean updateCollectFile(CollectFileDTO collectfileDTO) throws TException {
      integratedDeptMapper.updateCollectFile(collectfileDTO);
      return true;
   }
   /**
    * 查档列表(分页查询)
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public List<CheckDocumentDTO> queryCheckDocument(CheckDocumentDTO checkDocumentDTO) throws TException {
      return integratedDeptMapper.queryCheckDocument(checkDocumentDTO);
   }
   /**
    * 查档列表查询总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public int getCheckDocumentTotal(CheckDocumentDTO checkDocumentDTO) throws TException {
      return integratedDeptMapper.getCheckDocumentTotal(checkDocumentDTO);
   }
   /**
    * 添加查档
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean addCheckDocument(CheckDocumentDTO checkDocumentDTO) throws TException {
      integratedDeptMapper.addCheckDocument(checkDocumentDTO);
      return true;
   }
   /**
    * 根据ID获取查档
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public CheckDocumentDTO getCheckDocumentById(int pid) throws TException {
      return integratedDeptMapper.getCheckDocumentById(pid);
   }
   /**
    * 根据ID更新查档
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean updateCheckDocument(CheckDocumentDTO checkDocumentDTO) throws TException {
      integratedDeptMapper.updateCheckDocument(checkDocumentDTO);
      return true;
   }

   //如果赎楼驳回之后，并且进行查档则把赎楼状态改为未赎楼
    private void updateforeclosureStatus(int projectId) {
      HandleInfoDTO handleInfoQuery=new HandleInfoDTO();
      handleInfoQuery.setProjectId(projectId);
      handleInfoQuery.setForeclosureStatus(Constants.TURN_DOWN_FORECLOSURE);
	  List<HandleInfoDTO> handleInfoDTOs = bizHandleMapper.findAllHandleInfoDTO(handleInfoQuery);
	  if (handleInfoDTOs!=null&&!handleInfoDTOs.isEmpty()) {
		  HandleInfoDTO updateHandleInfo = handleInfoDTOs.get(0);
		  updateHandleInfo.setForeclosureStatus(Constants.NO_FORECLOSURE);
		  bizHandleMapper.updateHandleInfo(updateHandleInfo);
	  }
     }
    /**
     * 查档首页列表(分页查询)
     *@author:liangyanjun
     *@time:2016年12月23日下午5:26:03
     */
   @Override
   public List<CheckDocumentIndexDTO> queryCheckDocumentIndex(CheckDocumentIndexDTO checkDocumentIndexDTO) throws TException {
      return integratedDeptMapper.queryCheckDocumentIndex(checkDocumentIndexDTO);
   }
   /**
    * 查档首页列表总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public int getCheckDocumentIndexTotal(CheckDocumentIndexDTO checkDocumentIndexDTO) throws TException {
      return integratedDeptMapper.getCheckDocumentIndexTotal(checkDocumentIndexDTO);
   }

   /**
    * 根据projectId更新收件备注
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean updateRemarkByProjectId(CollectFileDTO collectfileDTO) throws TException {
      integratedDeptMapper.updateRemarkByProjectId(collectfileDTO);
      return true;
   }
   /**
    * 查诉讼列表(分页查询)
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public List<CheckLitigationDTO> queryCheckLitigation(CheckLitigationDTO checkLitigationDTO) throws TException {
      return integratedDeptMapper.queryCheckLitigation(checkLitigationDTO);
   }
   /**
    * 查诉讼列表查询总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public int getCheckLitigationTotal(CheckLitigationDTO checkLitigationDTO) throws TException {
      return integratedDeptMapper.getCheckLitigationTotal(checkLitigationDTO);
   }
   /**
    * 添加查诉讼
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean addCheckLitigation(CheckLitigationDTO checkLitigationDTO) throws TException {
      integratedDeptMapper.addCheckLitigation(checkLitigationDTO);
      return true;
   }
   /**
    * 根据ID获取查诉讼
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public CheckLitigationDTO getCheckLitigationById(int pid) throws TException {
      return integratedDeptMapper.getCheckLitigationById(pid);
   }
   /**
    * 根据ID更新查诉讼
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean updateCheckLitigation(CheckLitigationDTO checkLitigationDTO) throws TException {
      integratedDeptMapper.updateCheckLitigation(checkLitigationDTO);
      return true;
   }
   /**
    * 根据项目ID获取查档
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public CheckDocumentDTO getCheckDocumentByProjectId(int projectId) throws TException {
      CheckDocumentDTO checkDocumentQuery=new CheckDocumentDTO();
      checkDocumentQuery.setProjectId(projectId);
      List<CheckDocumentDTO> list = integratedDeptMapper.queryCheckDocument(checkDocumentQuery);
      if (list==null||list.isEmpty()) {
         return new CheckDocumentDTO();
      }
      return list.get(0);
   }
   /**
    * 根据项目ID获取查诉讼
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public CheckLitigationDTO getCheckLitigationByProjectId(int projectId) throws TException {
      CheckLitigationDTO checkLitigationQuery=new CheckLitigationDTO();
      checkLitigationQuery.setProjectId(projectId);
      List<CheckLitigationDTO> list = integratedDeptMapper.queryCheckLitigation(checkLitigationQuery);
      return list.get(0);
   }
   /**
    * 根据id集合查询收件列表
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
    @Override
    public List<CollectFileDTO> queryCollectFileByPids(String pids) throws TException {
        if (!StringUtil.isBlank(pids)) {
            String[] pidArr = pids.split(",");
            List<CollectFileDTO> result = integratedDeptMapper.queryCollectFileByPids(pidArr);
            return result;
        }
        return new ArrayList<CollectFileDTO>();
    }
    /**
     * 根据项目ID查询执行岗备注
     *@author:liangyanjun
     *@time:2016年12月23日下午5:26:03
     */
   @Override
   public PerformJobRemark getPerformJobRemark(int projectId) throws TException {
      PerformJobRemark performJobRemark = integratedDeptMapper.getPerformJobRemark(projectId);
      if (performJobRemark==null) {
         return new PerformJobRemark();
      }
      return performJobRemark;
   }
   /**
    * 添加执行岗备注
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public int addPerformJobRemark(PerformJobRemark performJobRemark) throws TException {
      integratedDeptMapper.addPerformJobRemark(performJobRemark);
      return performJobRemark.getPid();
   }
   /**
    * 根据项目ID更新执行岗备注
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   @Override
   public boolean updatePerformJobRemark(PerformJobRemark performJobRemark) throws TException {
      integratedDeptMapper.updatePerformJobRemark(performJobRemark);
      return true;
   }

   /**
    * 查档复核：不同意，则把查档审批状态修改为重新查档
    *@author:liangyanjun
    *@time:2016年5月6日下午3:50:00
    */
   @Override
   @Transactional
   public boolean reCheckCheckDocument(CheckDocumentDTO checkDocumentDTO) throws TException {
      int reCheckStatus = checkDocumentDTO.getReCheckStatus();
      if (reCheckStatus==Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_4) {
         checkDocumentDTO.setApprovalStatus(Constants.CHECK_DOCUMENT_APPROVAL_STATUS_4);
      }else if(reCheckStatus==Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_3){
         //如果赎楼驳回之后，并且查档复核同意则把赎楼状态改为未赎楼
         updateforeclosureStatus(checkDocumentDTO.getProjectId());
      }
      integratedDeptMapper.updateCheckDocument(checkDocumentDTO);
      return true;
   }

   /**
    * 收件
    *@author:liangyanjun
    *@time:2016年5月16日下午4:17:36
    */
   @Override
   @Transactional
   public boolean collectFile(CollectFileDTO collectfileDTO) throws TException {
      int projectId = collectfileDTO.getProjectId();//项目id
      int updateId = collectfileDTO.getUpdateId();//更新该数据的用户id
      String collectDate = collectfileDTO.getCollectDate();//收件日期
      String buyerSellerName = collectfileDTO.getBuyerSellerName();//买卖方姓名
      int buyerSellerType = collectfileDTO.getBuyerSellerType();//买卖类型：买方=1，卖方=2
      List<CollectFileMap> collectFileMapList = collectfileDTO.getCollectFileMapList();// 收件集合

      Map<String, String> codeAndRemarkMap = getCodeAndRemarkMap(collectFileMapList);
      CollectFileDTO collectfileQuery = new CollectFileDTO();
      collectfileQuery.setPage(-1);
      collectfileQuery.setProjectId(projectId);
      collectfileQuery.setBuyerSellerName(buyerSellerName);
      collectfileQuery.setBuyerSellerType(buyerSellerType);
      try {
         // 修改收件状态为已收件
         projectMapper.updatecollectFileStatusByPid(projectId, Constants.COLLECT_FILE_STATUS_2);
         List<CollectFileDTO> collectFileList = integratedDeptMapper.queryCollectFile(collectfileQuery);
         // 数据库不存在任何一条数据数时，选择的全部添加
         if (collectFileList == null || collectFileList.isEmpty()) {
            Set<String> keySet = codeAndRemarkMap.keySet();
            for (String code : keySet) {
               CollectFileDTO newCollectFile = new CollectFileDTO();
               newCollectFile.setCode(code);
               newCollectFile.setName(Constants.COLLECT_FILE_MAP.get(code));
               newCollectFile.setProjectId(projectId);
               newCollectFile.setStatus(Constants.STATUS_ENABLED);
               newCollectFile.setUpdateId(updateId);
               newCollectFile.setCreaterId(updateId);
               newCollectFile.setRemark(codeAndRemarkMap.get(code));
               newCollectFile.setBuyerSellerType(buyerSellerType);
               newCollectFile.setBuyerSellerName(buyerSellerName);
               newCollectFile.setCollectDate(collectDate);
               integratedDeptMapper.addCollectFile(newCollectFile);
            }
            // 一个都没有选择，则把该项目的的所有要件设置成无效状态
         } else if (collectFileMapList == null || collectFileMapList.isEmpty()) {
            for (CollectFileDTO dto : collectFileList) {
               dto.setRemark(codeAndRemarkMap.get(dto.getCode()));
               dto.setUpdateId(updateId);
               dto.setStatus(Constants.STATUS_DISABLED);
               dto.setCollectDate(collectDate);
               integratedDeptMapper.updateCollectFile(dto);
            }
         } else {
            // 已存在的做状态修改
            for (CollectFileDTO dto : collectFileList) {
               String code = dto.getCode();
               Integer status = dto.getStatus();
               dto.setUpdateId(updateId);
               dto.setRemark(codeAndRemarkMap.get(code));
               dto.setCollectDate(collectDate);
               // 如果已存在数据且是有效，但是页面没有选中，择把该数据设置无效
               if (!codeAndRemarkMap.containsKey(code) && status == Constants.STATUS_ENABLED) {
                  dto.setStatus(Constants.STATUS_DISABLED);
                  integratedDeptMapper.updateCollectFile(dto);
                  codeAndRemarkMap.remove(code);
                  // 如果已存在数据且是无效，但是页面有选中，择把该数据设置有效
               } else if (codeAndRemarkMap.containsKey(code) && status == Constants.STATUS_DISABLED) {
                  dto.setStatus(Constants.STATUS_ENABLED);
                  integratedDeptMapper.updateCollectFile(dto);
                  codeAndRemarkMap.remove(code);
                  // 如果已存在数据且是有效，但是页面有选中，则只更新备注
               } else if (codeAndRemarkMap.containsKey(code) && status == Constants.STATUS_ENABLED) {
                  dto.setCollectDate(null);
                  integratedDeptMapper.updateCollectFile(dto);
                  codeAndRemarkMap.remove(code);
               }
            }
            // 不存在做新增操作
            Set<String> keySet = codeAndRemarkMap.keySet();
            for (String code : keySet) {
               CollectFileDTO newCollectFile = new CollectFileDTO();
               newCollectFile.setCode(code);
               newCollectFile.setName(Constants.COLLECT_FILE_MAP.get(code));
               newCollectFile.setProjectId(projectId);
               newCollectFile.setStatus(Constants.STATUS_ENABLED);
               newCollectFile.setUpdateId(updateId);
               newCollectFile.setCreaterId(updateId);
               newCollectFile.setRemark(codeAndRemarkMap.get(code));
               newCollectFile.setBuyerSellerType(buyerSellerType);
               newCollectFile.setBuyerSellerName(buyerSellerName);
               integratedDeptMapper.addCollectFile(newCollectFile);
            }
         }
      } catch (Exception e) {
         logger.error("收件失败：入参：" + collectfileDTO.toString() + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }
   /**
    * 根据传入的收件集合，把code和Remark封装到map中，code为key，Remark为value
    *@author:liangyanjun
    *@time:2016年12月23日下午5:26:03
    */
   private Map<String, String> getCodeAndRemarkMap(List<CollectFileMap> collectFileMapList) {
      Map<String, String> codeAndRemarkMap = new HashMap<>();
      if (collectFileMapList==null) {
         return codeAndRemarkMap;
      }
      for (CollectFileMap collectFileMap : collectFileMapList) {
         if (StringUtil.isBlank(collectFileMap.getCode())) {
            continue;
         }
         codeAndRemarkMap.put(collectFileMap.getCode(), collectFileMap.getRemark());
      }
      return codeAndRemarkMap;
   }

   /**
    * 退件
    *@author:liangyanjun
    *@time:2016年5月16日下午4:17:36
    */
   @Override
   @Transactional
   public boolean refundFile(CollectFileDTO collectfileDTO) throws TException {
      int projectId = collectfileDTO.getProjectId();//
      int updateId = collectfileDTO.getUpdateId();//
      String refundDate = collectfileDTO.getRefundDate();//
      String buyerSellerName = collectfileDTO.getBuyerSellerName();//
      int buyerSellerType = collectfileDTO.getBuyerSellerType();//
      List<CollectFileMap> collectFileMapList = collectfileDTO.getCollectFileMapList();//收件集合
      Map<String, String> codeAndRemarkMap = getCodeAndRemarkMap(collectFileMapList);
      CollectFileDTO collectfileQuery = new CollectFileDTO();
      collectfileQuery.setPage(-1);
      collectfileQuery.setProjectId(projectId);
      collectfileQuery.setBuyerSellerName(buyerSellerName);
      collectfileQuery.setBuyerSellerType(buyerSellerType);
      collectfileQuery.setStatus(Constants.STATUS_ENABLED);
      try {
         List<CollectFileDTO> collectFileList = integratedDeptMapper.queryCollectFile(collectfileQuery);
        // 页面一个都没有选择，则把该项目的的所有要件设置成未退件
         if (collectFileMapList == null || collectFileMapList.isEmpty()) {
            for (CollectFileDTO c : collectFileList) {
               c.setRefundStatus(Constants.REFUND_FILE_STATUS_1);
               integratedDeptMapper.updateCollectFile(c);
            }
            return true;
         }
         // 修改退件状态为已退件
         for (CollectFileDTO dto : collectFileList) {
            String code = dto.getCode();
            dto.setUpdateId(updateId);
            //已收件，并且页面选择，则把该数据修改为已退件
           if (codeAndRemarkMap.containsKey(code)) {
              dto.setRefundDate(refundDate);
              dto.setRefundStatus(Constants.REFUND_FILE_STATUS_2);
              integratedDeptMapper.updateCollectFile(dto);
              codeAndRemarkMap.remove(code);
            // 如果数据已经退件，但是页面不选中，择把该数据设置未退件
           }else if (dto.getRefundStatus() == Constants.REFUND_FILE_STATUS_2) {
               dto.setRefundStatus(Constants.REFUND_FILE_STATUS_1);
               integratedDeptMapper.updateCollectFile(dto);
               codeAndRemarkMap.remove(code);
            }
         }
      } catch (Exception e) {
         logger.error("退件失败：入参：" + collectfileDTO.toString() + "。错误：" + ExceptionUtil.getExceptionMessage(e));
         e.printStackTrace();
         throw new TException(ExceptionUtil.getExceptionMessage(e));
      }
      return true;
   }

   /**
    * 查询要件打印信息
    *@author:liangyanjun
    *@time:2016年5月23日下午2:14:07
    */
   @Override
   public CollectFilePrintInfo getCollectFilePrintInfo(CollectFilePrintInfo collectFilePrintInfo) throws TException {
      return integratedDeptMapper.getCollectFilePrintInfo(collectFilePrintInfo);
   }
   /**
    * 初始化综合部数据
    *@author:liangyanjun
    *@time:2016年3月14日上午2:52:18
    *@param projectId
    */
   @Override
   @Transactional
   public void initIntegratedDept(int projectId) {
      // 收件
      // CollectFileDTO collectfileDTO=new CollectFileDTO();
      // collectfileDTO.setProjectId(projectId);
      // collectfileDTO.setStatus(Constants.COLLECT_FILE_STATUS_1);
      // integratedDeptMapper.addCollectFile(collectfileDTO);
      // 查档
      CheckDocumentDTO checkDocumentDTO = new CheckDocumentDTO();
      checkDocumentDTO.setApprovalStatus(Constants.CHECK_DOCUMENT_APPROVAL_STATUS_1);
      checkDocumentDTO.setCheckStatus(Constants.CHECK_DOCUMENT_STATUS_1);
      checkDocumentDTO.setReCheckStatus(Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_1);
      checkDocumentDTO.setProjectId(projectId);
      integratedDeptMapper.addCheckDocument(checkDocumentDTO);
      // 查诉讼
      CheckLitigationDTO checkLitigationDTO = new CheckLitigationDTO();
      checkLitigationDTO.setApprovalStatus(Constants.CHECK_LITIGATION_APPROVAL_STATUS_1);
      checkLitigationDTO.setCheckStatus(Constants.CHECK_LITIGATION_STATUS_1);
      checkLitigationDTO.setProjectId(projectId);
      integratedDeptMapper.addCheckLitigation(checkLitigationDTO);
      // 执行岗备注
      PerformJobRemark performJobRemark = new PerformJobRemark();
      performJobRemark.setProjectId(projectId);
      performJobRemark.setStatus(Constants.PERFORM_JOB_REMARK_STATUS_1);
      integratedDeptMapper.addPerformJobRemark(performJobRemark);
   }
}
