package com.xlkfinance.bms.server.aom.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-29 15:22:12 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 系统访问日志<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgSysLogInfoMapper")
public interface OrgSysLogInfoMapper<T, PK> extends BaseMapper<T, PK> {
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-29 15:22:12
    */
   public List<OrgSysLogInfo> getAll(OrgSysLogInfo orgSysLogInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-29 15:22:12
    */
   public OrgSysLogInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-29 15:22:12
    */
   public int insert(OrgSysLogInfo orgSysLogInfo);

}
