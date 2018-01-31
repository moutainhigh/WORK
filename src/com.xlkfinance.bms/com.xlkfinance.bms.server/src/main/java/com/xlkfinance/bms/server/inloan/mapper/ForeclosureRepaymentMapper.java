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
import java.util.Map;

import org.apache.thrift.TException;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.OverdueFeeDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentIndexDTO;
import com.xlkfinance.bms.rpc.inloan.RepaymentRecordDTO;

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
@MapperScan("foreclosureRepaymentMapper")
public interface ForeclosureRepaymentMapper<T, PK> extends BaseMapper<T, PK> {
    /**
     * 根据条件查询回款(分页查询)
     *@author:liangyanjun
     *@time:2016年12月23日下午5:52:17
     *@param repaymentDTO
     *@return
     *@throws TException
     */
   List<RepaymentDTO> queryRepayment(RepaymentDTO repaymentDTO);
   /**
    * 根据条件查询回款总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:52:29
    *@param repaymentDTO
    *@return
    *@throws TException
    */
   int getRepaymentTotal(RepaymentDTO repaymentDTO);
   /**
    * 添加回款
    *@author:liangyanjun
    *@time:2016年12月23日下午5:52:35
    *@param repaymentDTO
    *@return
    *@throws TException
    */
   void addRepayment(RepaymentDTO repaymentDTO);
   /**
    * 根据ID获取回款
    *@author:liangyanjun
    *@time:2016年12月23日下午5:52:40
    *@param pid
    *@return
    *@throws TException
    */
   RepaymentDTO getRepaymentById(int pid);
   /**
    * 根据ID更新回款
    *@author:liangyanjun
    *@time:2016年12月23日下午5:52:45
    *@param repaymentDTO
    *@return
    *@throws TException
    */
   void updateRepayment(RepaymentDTO repaymentDTO);
   /**
    * 根据条件查询回款记录(分页查询)
    *@author:liangyanjun
    *@time:2016年12月23日下午5:52:50
    *@param repaymentRecordDTO
    *@return
    *@throws TException
    */
   List<RepaymentRecordDTO> queryRepaymentRecord(RepaymentRecordDTO repaymentRecordDTO);
   /**
    * 根据条件查询回款记录总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:52:54
    *@param repaymentRecordDTO
    *@return
    *@throws TException
    */
   int getRepaymentRecordTotal(RepaymentRecordDTO repaymentRecordDTO);

   /**
    * 增加回款
    *@author:liangyanjun
    *@time:2016年3月13日下午10:17:30
    *@param repaymentRecordDTO
    */
   void addRepaymentRecord(RepaymentRecordDTO repaymentRecordDTO);
   /**
    * 根据ID获取回款记录
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:11
    *@param pid
    *@return
    *@throws TException
    */
   RepaymentRecordDTO getRepaymentRecordById(int pid);
   /**
    * 根据条件查询逾期费(分页查询)
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:16
    *@param overdueFeeDTO
    *@return
    *@throws TException
    */
   List<OverdueFeeDTO> queryOverdueFee(OverdueFeeDTO overdueFeeDTO);
   /**
    * 根据条件查询逾期费总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:22
    *@param overdueFeeDTO
    *@return
    *@throws TException
    */
   int getOverdueFeeTotal(OverdueFeeDTO overdueFeeDTO);
   /**
    * 添加逾期费
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:26
    *@param overdueFeeDTO
    *@return
    *@throws TException
    */
   void addOverdueFee(OverdueFeeDTO overdueFeeDTO);
   /**
    * 根据ID获取逾期费
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:33
    *@param pid
    *@return
    *@throws TException
    */
   OverdueFeeDTO getOverdueFeeById(int pid);
   /**
    * 根据项目ID获取逾期费
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:39
    *@param overdueFeeDTO
    *@return
    *@throws TException
    */
   void updateOverdueFee(OverdueFeeDTO overdueFeeDTO);
   /**
    * 根据条件查询回款列表(分页查询)
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:45
    *@param repaymentIndexDTO
    *@return
    *@throws TException
    */
   List<RepaymentIndexDTO> queryRepaymentIndex(RepaymentIndexDTO repaymentIndexDTO);
   /**
    * 根据条件查询回款列表总数
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:50
    *@param repaymentIndexDTO
    *@return
    *@throws TException
    */
   int getRepaymentIndexTotal(RepaymentIndexDTO repaymentIndexDTO);

   /**
    * 根据项目ID获取回款
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:55
    *@param projectId
    *@return
    *@throws TException
    */
   RepaymentDTO getRepaymentByProjectId(int projectId);

   /**
    * 根据项目id和回款金额算出逾期费
    *@author:liangyanjun
    *@time:2016年12月23日下午5:53:03
    *@param params
    *@return
    */
   void getOverdueFeeByParams(Map params);

   /**
    * 根据项目ID获取逾期费
    *@author:liangyanjun
    *@time:2016年12月23日下午5:54:00
    *@param projectId
    *@return
    *@throws TException
    */
   OverdueFeeDTO getOverdueFeeByProjectId(int projectId);
   /**
    * 
	 * queryRepaymentInfo:根据条件查询还款列表，抵押贷产品还款. <br/>
	 * @author baogang
	 * @param query
	 * @return
	 *
    */
   List<RepaymentDTO> queryRepaymentInfo(RepaymentIndexDTO query);
   /**
    * 
	 * getRepaymentInfoTotal:根据条件查询还款列表总数，抵押贷产品还款. <br/>
	 * @author baogang
	 * @param query
	 * @return
	 *
    */
   int getRepaymentInfoTotal(RepaymentIndexDTO query);
   
   /**
    * 
	 * insertPaymentRecords:批量新增还款记录信息. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param list
	 * @return
	 *
    */
   int insertPaymentRecords(List<RepaymentRecordDTO> list);
}
