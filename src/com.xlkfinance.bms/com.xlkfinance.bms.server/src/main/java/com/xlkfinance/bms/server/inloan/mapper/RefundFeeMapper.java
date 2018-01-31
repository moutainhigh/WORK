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
import com.xlkfinance.bms.rpc.inloan.RefundFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RefundFeeIndexDTO;
import com.xlkfinance.bms.rpc.report.ChechanReport;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年1月15日上午10:55:48 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：退手续费（退手续费，退咨询费，退尾款，退佣金）<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("refundFeeMapper")
public interface RefundFeeMapper<T, PK> extends BaseMapper<T, PK> {
   /**
    * 根据条件查询退手续费信息（分页查询）
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:18
    *@param refundFeeDTO
    *@return
    */
   public List<RefundFeeDTO> findAllRefundFee(RefundFeeDTO refundFeeDTO);

   /**
    * 根据条件查询退手续费信息总数
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:22
    *@param refundFeeDTO
    *@return
    */
   public int getRefundFeeTotal(RefundFeeDTO refundFeeDTO);

   /**
    * 添加退手续费信息
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:25
    *@param refundFeeDTO
    *@return
    */
   public boolean addRefundFee(RefundFeeDTO refundFeeDTO);

   /**
    * 根据id更新退手续费信息
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:28
    *@param refundFeeDTO
    *@return
    */
   public boolean updateRefundFee(RefundFeeDTO refundFeeDTO);

   /**
    * 根据条件查询退款列表（分页查询）
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:31
    *@param refundFeeIndexDTO
    *@return
    */
   public List<RefundFeeIndexDTO> findAllRefundFeeIndex(RefundFeeIndexDTO refundFeeIndexDTO);

   /**
    * 根据条件查询退款列表总数
    *@author:liangyanjun
    *@time:2016年1月28日下午2:49:34
    *@param refundFeeIndexDTO
    *@return
    */
   public int getRefundFeeIndexTotal(RefundFeeIndexDTO refundFeeIndexDTO);
   
   
   /**
    * 
     * @Description: 根据ID获取退费列表
     * @param list
     * @return
     * @author: xiayt
     * @date: 2016年6月22日 下午3:06:37
    */
   List<RefundFeeIndexDTO> queryRefundFeeByIds(List list);

   RefundFeeDTO getRefundFeeById(int pid);
}
