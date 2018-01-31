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
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferDTO;
import com.xlkfinance.bms.rpc.inloan.IntermediateTransferIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeIndexDTO;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年4月2日下午2:46:19 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 中途划转<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("intermediateTransferMapper")
public interface IntermediateTransferMapper<T, PK> extends BaseMapper<T, PK> {
   /**
    * 根据条件查询中途划转（分页查询）
    *@author:liangyanjun
    *@time:2016年4月2日下午2:53:10
    *@param intermediateTransferDTO
    *@return
    */
   List<IntermediateTransferDTO> queryIntermediateTransfer(IntermediateTransferDTO intermediateTransferDTO);

   /**
    * 根据条件查询中途划转总数
    *@author:liangyanjun
    *@time:2016年4月2日下午2:53:14
    *@param intermediateTransferDTO
    *@return
    */
   int getIntermediateTransferTotal(IntermediateTransferDTO intermediateTransferDTO);

   /**
    * 添加中途划转
    *@author:liangyanjun
    *@time:2016年4月2日下午2:53:19
    *@param intermediateTransferDTO
    */
   void addIntermediateTransfer(IntermediateTransferDTO intermediateTransferDTO);

   /**
    * 根据id更新中途划转
    *@author:liangyanjun
    *@time:2016年4月2日下午2:53:23
    *@param intermediateTransferDTO
    */
   void updateIntermediateTransfer(IntermediateTransferDTO intermediateTransferDTO);

   /**
    * 根据条件查询中途划转（分页查询）
    *@author:liangyanjun
    *@time:2016年4月2日下午6:35:52
    *@param intermediateTransferIndexDTO
    *@return
    */
   List<IntermediateTransferIndexDTO> queryIntermediateTransferIndex(IntermediateTransferIndexDTO intermediateTransferIndexDTO);

   /**
    * 根据条件查询中途划转总数
    *@author:liangyanjun
    *@time:2016年4月2日下午6:35:56
    *@param intermediateTransferIndexDTO
    *@return
    */
   int getIntermediateTransferIndexTotal(IntermediateTransferIndexDTO intermediateTransferIndexDTO);

   /**
    * 根据项目id和状态（查询的条件为>=状态值） 检查是否有工作流在运行,返回值大于0则有工作流运行
    *@author:liangyanjun
    *@time:2016年4月3日上午2:26:21
    *@param intermediateTransferDTO
    *@return
    */
   int checkWorkFlowExist(IntermediateTransferDTO intermediateTransferDTO);

   /**
    * 根据条件查询中途划转申请列表（分页查询）
    *@author:liangyanjun
    *@time:2016年4月18日下午9:58:13
    *@param intermediateTransferIndexDTO
    *@return
    */
   List<IntermediateTransferIndexDTO> queryIntermediateTransferRequestIndex(IntermediateTransferIndexDTO intermediateTransferIndexDTO);

   /**
    * 根据条件查询中途划转申请列表总数
    *@author:liangyanjun
    *@time:2016年4月18日下午9:58:51
    *@param intermediateTransferIndexDTO
    *@return
    */
   int getIntermediateTransferRequestIndexTotal(IntermediateTransferIndexDTO intermediateTransferIndexDTO);
}
