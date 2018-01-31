package com.xlkfinance.bms.server.fddafterloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.fddafterloan.BizStorageInfo;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-19 09:32:16 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 入库材料清单<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizStorageInfoMapper")
public interface BizStorageInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   public List<BizStorageInfo> getAll(BizStorageInfo bizStorageInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   public BizStorageInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   public int insert(BizStorageInfo bizStorageInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   public int update(BizStorageInfo bizStorageInfo);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 入库材料清单（分页查询）
    *@author:liangyanjun
    *@time:2017年12月20日上午10:52:20
    */
   public List<BizStorageInfo> queryStorageInfo(BizStorageInfo query);

   /**
    * 入库材料清单条数（分页查询）
    *@author:liangyanjun
    *@time:2017年12月20日上午10:52:20
    */
   public int getStorageInfoTotal(BizStorageInfo query);
}
