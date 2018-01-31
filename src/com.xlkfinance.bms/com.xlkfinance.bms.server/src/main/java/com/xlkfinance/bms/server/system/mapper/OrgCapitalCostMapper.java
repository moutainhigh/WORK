package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.OrgCapitalCost;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-09-14 14:27:40 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构资金成本<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgCapitalCostMapper")
public interface OrgCapitalCostMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   public List<OrgCapitalCost> getAll(OrgCapitalCost orgCapitalCost);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   public OrgCapitalCost getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   public int insert(OrgCapitalCost orgCapitalCost);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   public int update(OrgCapitalCost orgCapitalCost);
   /**
    * 删除数据
     * @param pid
     * @return
     * @author: baogang
     * @date: 2016年9月18日 上午10:28:05
    */
   public int delCapitionCost(int pid);

   /**
    * 获取年利率，当资金机构存在不止一天资金成本记录时使用
     * @param pid
     * @return
     * @author: baogang
     * @date: 2016年9月18日 下午5:13:52
    */
   public List<OrgCapitalCost> getYearRate(int pid);
}
