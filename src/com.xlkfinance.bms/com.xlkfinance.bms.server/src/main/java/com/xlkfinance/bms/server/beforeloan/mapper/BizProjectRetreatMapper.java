package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectRetreat;

/**
 *  @author： baogang <br>
 *  @time：2016-10-24 11:43:30 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 项目回撤关系表<br>
 */
@MapperScan("bizProjectRetreatMapper")
public interface BizProjectRetreatMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   public List<BizProjectRetreat> getAll(BizProjectRetreat bizProjectRetreat);

   /**
    *根据id查询
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   public BizProjectRetreat getById(int pid);

   /**
    *插入一条数据
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   public int insert(BizProjectRetreat bizProjectRetreat);

   /**
    *根据id更新数据
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   public int update(BizProjectRetreat bizProjectRetreat);

}
