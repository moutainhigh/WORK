package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.BizBatchRefundFeeRelation;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-26 10:09:54 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 批量退费关联表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizBatchRefundFeeRelationMapper")
public interface BizBatchRefundFeeRelationMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-26 10:09:54
    */
   public List<BizBatchRefundFeeRelation> getAll(BizBatchRefundFeeRelation bizBatchRefundFeeRelation);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-26 10:09:54
    */
   public BizBatchRefundFeeRelation getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-26 10:09:54
    */
   public int insert(BizBatchRefundFeeRelation bizBatchRefundFeeRelation);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-26 10:09:54
    */
   public int update(BizBatchRefundFeeRelation bizBatchRefundFeeRelation);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-26 10:09:54
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-26 10:09:54
    */
   public int deleteByIds(List<Integer> pids);
}
