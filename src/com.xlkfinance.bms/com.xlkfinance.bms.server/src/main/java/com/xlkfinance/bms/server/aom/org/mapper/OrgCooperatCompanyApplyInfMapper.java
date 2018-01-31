package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApply;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-11 09:18:59 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构管理平台---机构合作申请信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgCooperatCompanyApplyInfMapper")
public interface OrgCooperatCompanyApplyInfMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-11 09:18:59
    */
   public List<OrgCooperatCompanyApplyInf> getAll(OrgCooperatCompanyApplyInf orgCooperatCompanyApplyInf);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-11 09:18:59
    */
   public OrgCooperatCompanyApplyInf getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-11 09:18:59
    */
   public int insert(OrgCooperatCompanyApplyInf orgCooperatCompanyApplyInf);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-11 09:18:59
    */
   public int update(OrgCooperatCompanyApplyInf orgCooperatCompanyApplyInf);
   
   /**
    * 分页查询申请信息
     * @param orgCooperatCompanyApply
     * @return
     * @author: baogang
     * @date: 2016年7月11日 上午11:59:37
    */
   public List<OrgCooperatCompanyApply> getOrgCooperateByPage(OrgCooperatCompanyApply orgCooperatCompanyApply);
   
   /**
    * 查询申请信息总数
     * @param orgCooperatCompanyApply
     * @return
     * @author: baogang
     * @date: 2016年7月11日 上午11:59:50
    */
   public int getOrgCooperateCount(OrgCooperatCompanyApply orgCooperatCompanyApply);
   
   /**
    * 修改申请状态
     * @param orgCooperatCompanyApplyInf
     * @return
     * @author: baogang
     * @date: 2016年7月12日 上午9:52:33
    */
   public int updateApplyStatus(OrgCooperatCompanyApplyInf orgCooperatCompanyApplyInf);
   
   /**
    * 通过pid查询机构名称
     * @param pid
     * @return
     * @author: baogang
     * @date: 2016年7月12日 下午6:40:22
    */
   public String getOrgNameByCid(@Param(value = "pid") int pid);

   /**
    * 根据用户id查询
    *@author:liangyanjun
    *@time:2016年7月13日下午3:59:56
    *@param userId
    *@return
    */
   public OrgCooperatCompanyApplyInf getByUserId(int userId);
   
   /**
    * 贷前业务来源数据查询
     * @return
     * @author: baogang
     * @date: 2016年10月14日 下午4:33:41
    */
   public List<OrgCooperatCompanyApply> getOrgAssetsList();
}
