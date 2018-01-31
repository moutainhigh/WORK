package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.BizCapitalSelRecord;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-13 17:01:35 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 资方选择记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizCapitalSelRecordMapper")
public interface BizCapitalSelRecordMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   public List<BizCapitalSelRecord> getAll(BizCapitalSelRecord bizCapitalSelRecord);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   public BizCapitalSelRecord getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   public int insert(BizCapitalSelRecord bizCapitalSelRecord);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   public int update(BizCapitalSelRecord bizCapitalSelRecord);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   public int deleteByIds(List<Integer> pids);
   
   /**
    * 根据项目ID查询资方选择记录
	 * getAllByProjectId:(这里用一句话描述这个方法的作用). <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * @author baogang
	 * @param projectId
	 * @return
	 *
    */
   public List<BizCapitalSelRecord> getAllByProjectId (int projectId);
}
