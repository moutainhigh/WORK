package com.xlkfinance.bms.server.aom.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.system.OrgUserMenuInfo;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-26 14:36:25 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 用户与菜单关联表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgUserMenuInfoMapper")
public interface OrgUserMenuInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   public List<OrgUserMenuInfo> getAll(OrgUserMenuInfo orgUserMenuInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   public OrgUserMenuInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   public int insert(OrgUserMenuInfo orgUserMenuInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   public int update(OrgUserMenuInfo orgUserMenuInfo);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   public int deleteByIds(List<Integer> pids);
}
