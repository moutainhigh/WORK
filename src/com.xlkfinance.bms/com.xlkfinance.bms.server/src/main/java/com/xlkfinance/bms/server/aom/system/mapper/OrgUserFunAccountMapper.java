package com.xlkfinance.bms.server.aom.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.system.OrgUserFunAccount;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-06 17:02:12 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构管理平台用户资金帐户<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgUserFunAccountMapper")
public interface OrgUserFunAccountMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-06 17:02:12
    */
   public List<OrgUserFunAccount> getAll(OrgUserFunAccount orgUserFunAccount);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-06 17:02:12
    */
   public OrgUserFunAccount getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-06 17:02:12
    */
   public int insert(OrgUserFunAccount orgUserFunAccount);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-06 17:02:12
    */
   public int update(OrgUserFunAccount orgUserFunAccount);

   /**
    *@author:liangyanjun
    *@time:2016年7月28日下午5:11:04
    *@param orgId
    */
   public void deleteByUserId(int userId);
}
