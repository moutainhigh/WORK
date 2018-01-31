package com.xlkfinance.bms.server.aom.finance.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.finance.OrgAssureFundRecord;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-09-02 15:44:44 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 保证金记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgAssureFundRecordMapper")
public interface OrgAssureFundRecordMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   public List<OrgAssureFundRecord> getAll(OrgAssureFundRecord orgAssureFundRecord);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   public OrgAssureFundRecord getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   public int insert(OrgAssureFundRecord orgAssureFundRecord);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   public int update(OrgAssureFundRecord orgAssureFundRecord);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-09-02 15:44:44
    */
   public int deleteByIds(List<Integer> pids);

   /**
    * 保证金记录列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月2日下午3:58:23
    *@param query
    *@return
    */
   public List<OrgAssureFundRecord> queryOrgAssureFundRecordPage(OrgAssureFundRecord query);

   /**
    *@author:liangyanjun
    *@time:2016年9月2日下午3:58:28
    *@param query
    *@return
    */
   public int getOrgAssureFundRecordTotal(OrgAssureFundRecord query);
}
