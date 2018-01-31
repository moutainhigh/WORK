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

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentDTO;
import com.xlkfinance.bms.rpc.inloan.CheckDocumentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.CheckLitigationDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFileDTO;
import com.xlkfinance.bms.rpc.inloan.CollectFilePrintInfo;
import com.xlkfinance.bms.rpc.inloan.PerformJobRemark;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月12日下午2:40:46 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 综合部管理（收件查档）<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("integratedDeptMapper")
public interface IntegratedDeptMapper<T, PK> extends BaseMapper<T, PK> {

   /**
    * 收件列表(分页查询)
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:11
    *@param collectfileDTO
    *@return
    */
   List<CollectFileDTO> queryCollectFile(CollectFileDTO collectfileDTO);

   /**
    * 收件列表查询总数
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:16
    *@param collectfileDTO
    *@return
    */
   int getCollectFileTotal(CollectFileDTO collectfileDTO);

   /**
    * 添加收件
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:21
    *@param collectfileDTO
    *@return
    */
   void addCollectFile(CollectFileDTO collectfileDTO);

   /**
    * 根据ID获取收件
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:32
    *@param pid
    *@return
    */
   CollectFileDTO getCollectFileById(int pid);

   /**
    * 根据ID更新收件
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:37
    *@param collectfileDTO
    */
   void updateCollectFile(CollectFileDTO collectfileDTO);

   /**
    * 查档列表(分页查询)
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:41
    *@param checkDocumentDTO
    *@return
    */
   List<CheckDocumentDTO> queryCheckDocument(CheckDocumentDTO checkDocumentDTO);

   /**
    * 查档列表查询总数
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:46
    *@param checkDocumentDTO
    *@return
    */
   int getCheckDocumentTotal(CheckDocumentDTO checkDocumentDTO);

   /**
    * 添加查档
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:51
    *@param checkDocumentDTO
    */
   void addCheckDocument(CheckDocumentDTO checkDocumentDTO);

   /**
    * 根据ID获取查档
    *@author:liangyanjun
    *@time:2016年3月12日下午2:46:58
    *@param pid
    *@return
    */
   CheckDocumentDTO getCheckDocumentById(int pid);

   /**
    * 根据ID更新查档
    *@author:liangyanjun
    *@time:2016年3月12日下午2:47:02
    *@param checkDocumentDTO
    */
   void updateCheckDocument(CheckDocumentDTO checkDocumentDTO);

   /**
    * 查档首页列表(分页查询)
    *@author:liangyanjun
    *@time:2016年3月12日下午3:52:23
    *@param checkDocumentIndexDTO
    *@return
    */
   List<CheckDocumentIndexDTO> queryCheckDocumentIndex(CheckDocumentIndexDTO checkDocumentIndexDTO);

   /**
    * 查档首页列表总数
    *@author:liangyanjun
    *@time:2016年3月12日下午3:52:27
    *@param checkDocumentIndexDTO
    *@return
    */
   int getCheckDocumentIndexTotal(CheckDocumentIndexDTO checkDocumentIndexDTO);

   /**
    * 根据projectId更新收件备注
    *@author:liangyanjun
    *@time:2016年3月22日上午10:00:11
    *@param collectfileDTO
    */
   void updateRemarkByProjectId(CollectFileDTO collectfileDTO);

   /**
    * 查诉讼列表(分页查询)
    *@author:liangyanjun
    *@time:2016年3月30日下午3:45:04
    *@param checkLitigationDTO
    *@return
    */
   List<CheckLitigationDTO> queryCheckLitigation(CheckLitigationDTO checkLitigationDTO);

   /**
    * 查诉讼列表查询总数
    *@author:liangyanjun
    *@time:2016年3月30日下午3:45:22
    *@param checkLitigationDTO
    *@return
    */
   int getCheckLitigationTotal(CheckLitigationDTO checkLitigationDTO);

   /**
    * 添加查诉讼
    *@author:liangyanjun
    *@time:2016年3月30日下午3:46:07
    *@param checkLitigationDTO
    */
   void addCheckLitigation(CheckLitigationDTO checkLitigationDTO);

   /**
    * 根据ID获取查诉讼
    *@author:liangyanjun
    *@time:2016年3月30日下午3:46:40
    *@param pid
    *@return
    */
   CheckLitigationDTO getCheckLitigationById(int pid);

   /**
    * 根据ID更新查诉讼
    *@author:liangyanjun
    *@time:2016年3月30日下午3:46:58
    *@param checkLitigationDTO
    */
   void updateCheckLitigation(CheckLitigationDTO checkLitigationDTO);

   /**
    * 批量查询收件
     * @param pids
     * @return
     * @author: Administrator
     * @date: 2016年4月2日 下午6:01:30
    */
   List<CollectFileDTO> queryCollectFileByPids(String[] pids);

   /**
    * 根据项目ID查询执行岗备注
    *@author:liangyanjun
    *@time:2016年4月5日下午10:46:32
    *@param projectId
    *@return
    */
   PerformJobRemark getPerformJobRemark(int projectId);

   /**
    * 添加执行岗备注
    *@author:liangyanjun
    *@time:2016年4月5日下午10:47:02
    *@param performJobRemark
    */
   void addPerformJobRemark(PerformJobRemark performJobRemark);

   /**
    * 根据项目ID更新执行岗备注
    *@author:liangyanjun
    *@time:2016年4月5日下午10:47:31
    *@param performJobRemark
    */
   void updatePerformJobRemark(PerformJobRemark performJobRemark);

   /**
    * 根据projectId更新收件日期 
    *@author:liangyanjun
    *@time:2016年5月11日上午9:58:57
    *@param projectId
    *@param collectDate
    */
   void updateCollectDateByProjectId(CollectFileDTO collectfileDTO);

   /**
    * 查询要件打印信息
    *@author:liangyanjun
    *@time:2016年5月23日下午2:14:40
    *@param collectFilePrintInfo
    *@return
    */
   CollectFilePrintInfo getCollectFilePrintInfo(CollectFilePrintInfo collectFilePrintInfo);
}
