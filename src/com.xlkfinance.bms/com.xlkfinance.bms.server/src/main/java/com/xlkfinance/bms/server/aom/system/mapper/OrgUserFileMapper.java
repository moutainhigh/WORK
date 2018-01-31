package com.xlkfinance.bms.server.aom.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.system.OrgUserFile;
import com.xlkfinance.bms.rpc.system.BizFile;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-09-01 10:47:05 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构用户资料附件<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgUserFileMapper")
public interface OrgUserFileMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   public List<OrgUserFile> getAll(OrgUserFile orgUserFile);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   public OrgUserFile getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   public int insert(OrgUserFile orgUserFile);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   public int update(OrgUserFile orgUserFile);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-09-01 10:47:05
    */
   public int deleteByIds(List<Integer> pids);

   /**
    * 根据用户id机构用户资料附件
    *@author:liangyanjun
    *@time:2016年9月1日上午10:52:10
    *@param userId
    *@return
    */
   public List<OrgUserFile> getByUserId(int userId);

   /**
    * 机构用户资料列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月2日上午10:07:24
    *@param query
    *@return
    */
   public List<BizFile> queryOrgUserFilePage(OrgUserFile query);

   /**
    * 机构用户资料列表查询总数
    *@author:liangyanjun
    *@time:2016年9月2日上午10:07:29
    *@param query
    *@return
    */
   public int getOrgUserFileTotal(OrgUserFile query);
}
