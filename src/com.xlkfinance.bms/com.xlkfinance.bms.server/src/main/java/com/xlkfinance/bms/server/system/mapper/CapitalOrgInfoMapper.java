package com.xlkfinance.bms.server.system.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.system.CapitalOrgInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-09-14 09:56:26 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 资产机构管理<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("capitalOrgInfoMapper")
public interface CapitalOrgInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   public List<CapitalOrgInfo> getAll(CapitalOrgInfo capitalOrgInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   public CapitalOrgInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   public int insert(CapitalOrgInfo capitalOrgInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   public int update(CapitalOrgInfo capitalOrgInfo);
   
   /**
    * 分页查询数据
     * @param capitalOrgInfo
     * @return
     * @author: baogang
     * @date: 2016年9月18日 上午10:29:06
    */
   public List<CapitalOrgInfo> getCapitalByPage(CapitalOrgInfo capitalOrgInfo);
   
   /**
    * 
     * @param capitalOrgInfo
     * @return
     * @author: baogang
     * @date: 2016年9月18日 上午10:29:13
    */
   public int getCapitalCount(CapitalOrgInfo capitalOrgInfo);
   /**
    * 修改放款总金额
     * @param capitalOrgInfo
     * @return
     * @author: baogang
     * @date: 2016年9月22日 下午3:24:54
    */
   public int updateLoanMoney(CapitalOrgInfo capitalOrgInfo);

   public CapitalOrgInfo getByOrgName(String orgName);
   
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-14 09:56:26
    */
   public List<CapitalOrgInfo> getAllByStatus();
   
   public CapitalOrgInfo getByOrgCode(String orgCode);
}
