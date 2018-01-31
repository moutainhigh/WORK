package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHis;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-03-23 14:54:36 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务收费历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("collectFeeHisMapper")
public interface CollectFeeHisMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   public List<CollectFeeHis> getAll(CollectFeeHis collectFeeHis);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   public CollectFeeHis getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   public int insert(CollectFeeHis collectFeeHis);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   public int update(CollectFeeHis collectFeeHis);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 财务收费历史列表(分页查询)
    *@author:liangyanjun
    *@time:2017年3月23日下午2:55:05
    */
   public List<CollectFeeHis> queryCollectFeeHis(CollectFeeHis query);
   
   /**
    * 财务收费历史列表总数
    *@author:liangyanjun
    *@time:2017年3月23日下午2:55:05
    */
   public int getCollectFeeHisTotal(CollectFeeHis query);
}
