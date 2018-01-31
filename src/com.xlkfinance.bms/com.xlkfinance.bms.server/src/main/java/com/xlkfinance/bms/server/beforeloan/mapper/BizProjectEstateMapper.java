package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectEstate;

/**
 *  @author： baogang <br>
 *  @time：2016-12-26 17:27:32 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 项目物业信息表<br>
 */
@MapperScan("bizProjectEstateMapper")
public interface BizProjectEstateMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:baogang
    *@time:2016-12-26 17:27:32
    */
   public List<BizProjectEstate> getAll(BizProjectEstate bizProjectEstate);
   
   
   /**
    *根据项目Id查询没有下户报告的物业
    *@author:dongbiao
    *@time:2018-1-22 11:27:32
    */
   public List<BizProjectEstate> getNoSpotInfoByProjectId(int projectId);
   
   

   /**
    *根据id查询
    *@author:baogang
    *@time:2016-12-26 17:27:32
    */
   public BizProjectEstate getById(int pid);

   /**
    *插入一条数据
    *@author:baogang
    *@time:2016-12-26 17:27:32
    */
   public int insert(BizProjectEstate bizProjectEstate);

   /**
    *根据id更新数据
    *@author:baogang
    *@time:2016-12-26 17:27:32
    */
   public int update(BizProjectEstate bizProjectEstate);

   /**
    * 根据项目ID查询物业相关信息
    * @param projectId
    * @return
    */
   public List<BizProjectEstate> getAllByProjectId(int projectId);
   
   /**
    * 批量删除物业信息
    * @param houseIds
    * @return
    */
   public int delProjectEstate(List<Integer> houseIds);

   /**
    * 根据项目ID查询物业相关下户信息
    * @param projectId
    * @return
    */
   public List<BizProjectEstate> getAllCascadeSpotInfoByProjectId(int projectId);

   
}
