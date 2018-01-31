package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgAdditionalCooperationInf;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-11 09:20:02 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构合作附加信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgAdditionalCooperationInfMapper")
public interface OrgAdditionalCooperationInfMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-11 09:20:02
    */
   public List<OrgAdditionalCooperationInf> getAll(OrgAdditionalCooperationInf orgAdditionalCooperationInf);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-11 09:20:02
    */
   public OrgAdditionalCooperationInf getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-11 09:20:02
    */
   public int insert(OrgAdditionalCooperationInf orgAdditionalCooperationInf);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-11 09:20:02
    */
   public int update(OrgAdditionalCooperationInf orgAdditionalCooperationInf);

}
