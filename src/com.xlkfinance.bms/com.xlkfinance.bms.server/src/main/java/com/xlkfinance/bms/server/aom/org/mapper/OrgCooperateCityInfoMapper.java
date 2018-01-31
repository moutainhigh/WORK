package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-11 09:20:44 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 合作城市信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgCooperateCityInfoMapper")
public interface OrgCooperateCityInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-11 09:20:44
    */
   public List<OrgCooperateCityInfo> getAll(OrgCooperateCityInfo orgCooperateCityInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-11 09:20:44
    */
   public OrgCooperateCityInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-11 09:20:44
    */
   public int insert(OrgCooperateCityInfo orgCooperateCityInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-11 09:20:44
    */
   public int update(OrgCooperateCityInfo orgCooperateCityInfo);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-11 09:20:44
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-11 09:20:44
    */
   public int deleteByIds(List<Integer> pids);
   /**
    * 根据机构ID删除数据
     * @param orgId
     * @return
     * @author: baogang
     * @date: 2016年7月13日 上午9:18:17
    */
   public int deleteByOrgId(int orgId);
   
   /**
    * 根据城市编码查询城市信息
     * @param cityCodes
     * @return
     * @author: baogang
     * @date: 2016年7月13日 上午10:41:50
    */
   public List<OrgCooperateCityInfo> getCitysByCodes(List<String> cityCodes);
   
}
