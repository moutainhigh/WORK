package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysSmsInfo;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-18 09:20:17 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 短信表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("sysSmsInfoMapper")
public interface SysSmsInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   public List<SysSmsInfo> getAll(SysSmsInfo sysSmsInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   public SysSmsInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   public int insert(SysSmsInfo sysSmsInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   public int update(SysSmsInfo sysSmsInfo);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   public int deleteByIds(List<Integer> pids);
}
