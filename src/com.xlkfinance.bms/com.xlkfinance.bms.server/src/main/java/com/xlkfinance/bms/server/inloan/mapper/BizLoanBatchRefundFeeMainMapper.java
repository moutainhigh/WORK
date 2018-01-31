package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.BizLoanBatchRefundFeeMain;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-26 10:10:59 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 批量退费主表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizLoanBatchRefundFeeMainMapper")
public interface BizLoanBatchRefundFeeMainMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-26 10:10:59
    */
   public List<BizLoanBatchRefundFeeMain> getAll(BizLoanBatchRefundFeeMain bizLoanBatchRefundFeeMain);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-26 10:10:59
    */
   public BizLoanBatchRefundFeeMain getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-26 10:10:59
    */
   public int insert(BizLoanBatchRefundFeeMain bizLoanBatchRefundFeeMain);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-26 10:10:59
    */
   public int update(BizLoanBatchRefundFeeMain bizLoanBatchRefundFeeMain);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-26 10:10:59
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-26 10:10:59
    */
   public int deleteByIds(List<Integer> pids);

   public int getNextId();
}
