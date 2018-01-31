package com.xlkfinance.bms.server.aom.project.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.project.OrderRejectInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-09-01 10:52:32 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务申请驳回记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orderRejectInfoMapper")
public interface OrderRejectInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   public List<OrderRejectInfo> getAll(OrderRejectInfo orderRejectInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   public OrderRejectInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   public int insert(OrderRejectInfo orderRejectInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   public int update(OrderRejectInfo orderRejectInfo);

}
