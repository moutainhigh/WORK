package com.xlkfinance.bms.server.aom.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfo;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-11 09:42:39 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 系统菜单信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgSysMenuInfoMapper")
public interface OrgSysMenuInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   public List<OrgSysMenuInfo> getAll(OrgSysMenuInfo orgSysMenuInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   public OrgSysMenuInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   public int insert(OrgSysMenuInfo orgSysMenuInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   public int update(OrgSysMenuInfo orgSysMenuInfo);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   public int deleteByIds(List<Integer> pids);

   /**
    * 获取菜单树（只包含二级菜单）
    *@author:liangyanjun
    *@time:2016年7月11日上午9:45:06
    *@param pid
    *@return
    */
   public List<OrgSysMenuInfo> getTree(OrgUserInfo orgUserInfo);
}
