package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.SysAppVersionInfo;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-11-01 09:49:42 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： APP版本信息（版本升级）<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("sysAppVersionInfoMapper")
public interface SysAppVersionInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-11-01 09:49:42
    */
   public List<SysAppVersionInfo> getAll(SysAppVersionInfo sysAppVersionInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-11-01 09:49:42
    */
   public SysAppVersionInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-11-01 09:49:42
    */
   public int insert(SysAppVersionInfo sysAppVersionInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-11-01 09:49:42
    */
   public int update(SysAppVersionInfo sysAppVersionInfo);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-11-01 09:49:42
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-11-01 09:49:42
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 分页查询app版本信息
    * @param query
    * @return
    */
   public List<SysAppVersionInfo> querySysAppVersionInfo(SysAppVersionInfo query);
   
   /**
    * 查询app版本总数
    * @param query
    * @return
    */
   public int getSysAppVersionInfoTotal(SysAppVersionInfo query);
}
