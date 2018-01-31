package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgCooperationContract;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-14 15:54:33 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构合作合同<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgCooperationContractMapper")
public interface OrgCooperationContractMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   public List<OrgCooperationContract> getAll(OrgCooperationContract orgCooperationContract);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   public OrgCooperationContract getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   public int insert(OrgCooperationContract orgCooperationContract);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   public int update(OrgCooperationContract orgCooperationContract);

   /**
    * 根据机构id和类型查询机构合作合同
    *@author:liangyanjun
    *@time:2016年7月18日上午10:11:33
    *@param orgId
    *@param contractType
    *@return
    */
   public OrgCooperationContract getByOrgIdAndType(@Param(value = "orgId")int orgId, @Param(value = "contractType")int contractType);
   
   /**
    * 删除合作合同
     * @param pid
     * @return
     * @author: baogang
     * @date: 2016年8月26日 下午2:28:42
    */
   public int delOrgCooperate(int pid);
}
