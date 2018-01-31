package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInf;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInfPage;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-09-08 09:55:23 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 商务顾问分配<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgBizAdviserAllocationInfMapper")
public interface OrgBizAdviserAllocationInfMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   public List<OrgBizAdviserAllocationInf> getAll(OrgBizAdviserAllocationInf orgBizAdviserAllocationInf);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   public OrgBizAdviserAllocationInf getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   public int insert(OrgBizAdviserAllocationInf orgBizAdviserAllocationInf);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   public int update(OrgBizAdviserAllocationInf orgBizAdviserAllocationInf);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   public int deleteByIds(List<Integer> pids);

   /**
    * 机构分配列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月8日上午11:11:58
    *@param query
    *@return
    */
   public List<OrgBizAdviserAllocationInfPage> queryOrgBizAdviserAllocationInfPage(OrgBizAdviserAllocationInfPage query);

   /**
    * 机构分配列表查询总数
    *@author:liangyanjun
    *@time:2016年9月8日上午11:12:05
    *@param query
    *@return
    */
   public int getOrgBizAdviserAllocationInfPageTotal(OrgBizAdviserAllocationInfPage query);
}
