package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.ApplyFinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceHandleDTO;
import com.xlkfinance.bms.rpc.inloan.FinanceIndexDTO;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月11日下午8:55:43 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务受理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("financeHandleMapper")
public interface FinanceHandleMapper<T, PK> extends BaseMapper<T, PK> {
   /**
    * 查询所有财务办理
    *@author:liangyanjun
    *@time:2016年1月12日下午3:10:10
    *@param financeHandleDTO
    *@return
    */
   public List<FinanceHandleDTO> findAllFinanceHandle(FinanceHandleDTO financeHandleDTO);

   /**
    *  查询所有财务的总数
    *@author:liangyanjun
    *@time:2016年1月12日下午3:10:13
    *@param financeHandleDTO
    *@return
    */
   public int getFinanceHandleTotal(FinanceHandleDTO financeHandleDTO);

   /**
    * 新增
    *@author:liangyanjun
    *@time:2016年1月12日下午10:53:08
    *@param financeHandleDTO
    *@return
    */
   public void addFinanceHandle(FinanceHandleDTO financeHandleDTO);

   /**
    * 根据id更新
    *@author:liangyanjun
    *@time:2016年1月12日下午5:53:45
    *@param financeHandleDTO
    */
   public void updateFinanceHandle(FinanceHandleDTO financeHandleDTO);

   /**
    * 根据ID进行查询
    *@author:liangyanjun
    *@time:2016年1月12日下午4:11:59
    *@param pid
    *@return
    */
   public FinanceHandleDTO getFinanceHandleById(int pid);

   /**
    * 查询所有财务办理
    *@author:liangyanjun
    *@time:2016年1月12日下午3:10:10
    *@param financeHandleDTO
    *@return
    */
   public List<ApplyFinanceHandleDTO> findAllApplyFinanceHandle(ApplyFinanceHandleDTO applyFinanceHandleDTO);

   /**
    * 根据条件查询申请财务受理信息条数
    *@author:liangyanjun
    *@time:2016年1月13日下午2:46:54
    *@param applyFinanceHandleDTO
    *@return
    */
   public int getApplyFinanceHandleTotal(ApplyFinanceHandleDTO applyFinanceHandleDTO);

   /**
    * 添加申请财务受理信息
    *@author:liangyanjun
    *@time:2016年1月13日下午2:46:58
    *@param applyFinanceHandleDTO
    */
   public void addApplyFinanceHandle(ApplyFinanceHandleDTO applyFinanceHandleDTO);

   /**
    * 根据id查询申请财务受理信息
    *@author:liangyanjun
    *@time:2016年1月13日下午2:47:02
    *@param applyFinanceHandleDTO
    */
   public ApplyFinanceHandleDTO getApplyFinanceHandleById(int pid);

   /**
    * 根据id修改申请财务受理信息
    *@author:liangyanjun
    *@time:2016年1月13日下午2:47:06
    *@param pid
    *@return
    */
   public void updateApplyFinanceHandle(ApplyFinanceHandleDTO applyFinanceHandleDTO);

   /**
    * 根据项目id获取最新的回款信息，如果有第二次回款则返回第二次，没有则返回第一次回款信息
    *@author:liangyanjun
    *@time:2016年3月4日下午2:12:36
    *@param applyFinanceHandleDTO
    *@return
    */
   public ApplyFinanceHandleDTO getNewRecApplyFinance(int projectId);
   /**
    * 根据项目id和收款项目集合查询金额
    *@author:liangyanjun
    *@time:2016年3月13日下午10:16:12
    *@param projectId
    *@param recPros
    *@return
    */
   public double getRecMoney(@Param(value = "projectId") int projectId, @Param(value = "recPros") List<Integer> recPros);

   /**
    *@author:liangyanjun
    *@time:2016年3月15日上午9:48:24
    *@param financeIndexDTO
    *@return
    */
   public List<FinanceIndexDTO> queryFinanceIndex(FinanceIndexDTO financeIndexDTO);

   /**
    *@author:liangyanjun
    *@time:2016年3月15日上午9:48:30
    *@param financeIndexDTO
    *@return
    */
   public int getFinanceIndexTotal(FinanceIndexDTO financeIndexDTO);

   /**
    * 根据项目ID和收款项目进行查询
    *@author:liangyanjun
    *@time:2016年8月8日上午10:30:05
    *@param projectId
    *@param recPro
    *@return
    */
   public ApplyFinanceHandleDTO getByProjectIdAndRecPro(@Param(value = "projectId")int projectId, @Param(value = "recPro")int recPro);

   /**
    *@author:liangyanjun
    *@time:2016年8月8日下午2:11:10
    *@param projectId
    *@return
    */
   public FinanceHandleDTO getFinanceHandleByProjectId(int projectId);
   /** 
    * 收费列表分页查询
    * @author:liangyanjun
    * @time:2016年10月27日上午10:09:46 */
   public List<FinanceIndexDTO> queryFinanceCollectFeeIndex(FinanceIndexDTO financeIndexDTO);
   /** 
    * 
    *  收费列表查询总数
    * @author:liangyanjun
    * @time:2016年10月27日上午10:09:46 */
   public int getFinanceCollectFeeIndexTotal(FinanceIndexDTO financeIndexDTO);
   
   /**
    * 根据项目id，产品编码查询放款记录
    *@author:liangyanjun
    *@time:2016年8月8日上午10:30:05
    *@param projectId
    *@param recPro
    *@return
    */
   public List<ApplyFinanceHandleDTO> getLoanHisByProjectId(@Param(value = "projectId")int projectId,@Param(value = "productNum")String productNum);

}
