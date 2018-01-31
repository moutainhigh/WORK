package com.xlkfinance.bms.server.inloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.inloan.BizDynamic;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年5月3日上午11:02:05 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：业务动态（项目总览） <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("bizDynamicMapper")
public interface BizDynamicMapper<T, PK> extends BaseMapper<T, PK> {

   /**
    * 根据条件查询业务动态信息（分页查询）
    *@author:liangyanjun
    *@time:2016年5月3日下午2:21:23
    *@param bizDynamic
    *@return
    */
   List<BizDynamic> queryBizDynamic(BizDynamic bizDynamic);

   /**
    * 根据条件查询业务动态信息数据赎楼
    *@author:liangyanjun
    *@time:2016年5月3日下午2:21:33
    *@param bizDynamic
    *@return
    */
   int getBizDynamicTotal(BizDynamic bizDynamic);

   /**
    * 添加
    *@author:liangyanjun
    *@time:2016年5月3日下午2:21:42
    *@param bizDynamic
    */
   void addBizDynamic(BizDynamic bizDynamic);

   /**
    * 根据id修改
    *@author:liangyanjun
    *@time:2016年5月3日下午2:22:37
    *@param bizDynamic
    */
   void updateBizDynamic(BizDynamic bizDynamic);

   /**
    * 根据ID,项目ID,模块编号,删除业务动态
    *@author:liangyanjun
    *@time:2016年5月11日下午5:06:51
    *@param bizDynamic
    */
   void delBizDynamic(BizDynamic bizDynamic);

   /**
    * 根据父级动态id级联删除子节点，不包括父节点
    *@author:liangyanjun
    *@time:2016年5月11日下午5:06:56
    *@param bizDynamic
    */
   void delBizDynamicByCascade(BizDynamic bizDynamic);
   
   /**
    * 根据项目ID,模块编号,驳回的节点Id删除业务动态
     * @param bizDynamic
     * @return
     * @author: baogang
     * @date: 2016年5月16日 上午11:38:12
    */
   int delBizDynamicByLastId(BizDynamic bizDynamic);
}
