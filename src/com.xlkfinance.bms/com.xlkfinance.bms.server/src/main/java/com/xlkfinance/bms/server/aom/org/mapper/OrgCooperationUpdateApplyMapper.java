package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApply;
/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-06 15:32:03 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构合作信息修改申请表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgCooperationUpdateApplyMapper")
public interface OrgCooperationUpdateApplyMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   public List<OrgCooperationUpdateApply> getAll(OrgCooperationUpdateApply orgCooperationUpdateApply);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   public OrgCooperationUpdateApply getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   public int insert(OrgCooperationUpdateApply orgCooperationUpdateApply);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   public int update(OrgCooperationUpdateApply orgCooperationUpdateApply);

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   public int deleteById(int pid);

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   public int deleteByIds(List<Integer> pids);

    /** 
     * 根据机构id和申请状态集合查询
     * @author:liangyanjun
     * @time:2017年1月9日上午11:01:38
     * @param orgId
     * @param applyStatusList
     * @return */
   public List<OrgCooperationUpdateApply> getByOrgIdAndApplyStatus(@Param(value = "orgId") int orgId, @Param(value = "applyStatusList") List<Integer> applyStatusList);
    /**
     * 根据机构id查询最后一次申请的机构合作信息修改申请信息
     *@author:liangyanjun
     *@time:2017年1月9日下午4:52:45
     *@param orgId
     *@return
     */
    public OrgCooperationUpdateApply getLastByOrgId(@Param(value = "orgId") int orgId);
    /**
     * 历史列表(分页查询)
     *@author:liangyanjun
     *@time:2017年1月10日下午5:10:21
     */
    public List<OrgCooperationUpdateApply> queryApplyHisIndex(OrgCooperationUpdateApply query);
    /**
     * 历史列表总数
     *@author:liangyanjun
     *@time:2017年1月10日下午5:10:21
     */
    public int getApplyHisIndexTotal(OrgCooperationUpdateApply query);
}
