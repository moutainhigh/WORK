package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowDTO;
import com.qfang.xk.aom.rpc.org.OrgAssureFundFlowInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-11 09:20:26 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 合作机构保证金额变更流水<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgAssureFundFlowInfoMapper")
public interface OrgAssureFundFlowInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-11 09:20:26
    */
   public List<OrgAssureFundFlowInfo> getAll(OrgAssureFundFlowInfo orgAssureFundFlowInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-11 09:20:26
    */
   public OrgAssureFundFlowInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-11 09:20:26
    */
   public int insert(OrgAssureFundFlowInfo orgAssureFundFlowInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-11 09:20:26
    */
   public int update(OrgAssureFundFlowInfo orgAssureFundFlowInfo);
   /**
    * 分页查询保证金流水记录
     * @param condition
     * @return
     * @author: baogang
     * @date: 2016年7月13日 下午3:27:31
    */
   public List<OrgAssureFundFlowDTO> getOrgAssureFundByPage(OrgAssureFundFlowDTO condition);
   
   /**
    * 查询保证金流水记录总数
     * @param condition
     * @return
     * @author: baogang
     * @date: 2016年7月13日 下午3:27:41
    */
   public int getOrgAssureFundCount(OrgAssureFundFlowDTO condition);

   /**
    * 获取申请中的保证金变更信息
    *@author:liangyanjun
    *@time:2016年7月18日下午4:03:16
    *@param orgId
    *@return
    */
   public OrgAssureFundFlowInfo getApplyOrgFundFlow(int orgId);
}
