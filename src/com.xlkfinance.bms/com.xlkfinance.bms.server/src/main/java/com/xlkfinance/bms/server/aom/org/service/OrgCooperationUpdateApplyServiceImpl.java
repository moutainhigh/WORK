package com.xlkfinance.bms.server.aom.org.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApply;
import com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApplyService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperationUpdateApplyMapper;

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
@Service("orgCooperationUpdateApplyServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.org.OrgCooperationUpdateApplyService")
public class OrgCooperationUpdateApplyServiceImpl implements Iface {
   @Resource(name = "orgCooperationUpdateApplyMapper")
   private OrgCooperationUpdateApplyMapper orgCooperationUpdateApplyMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   @Override
   public List<OrgCooperationUpdateApply> getAll(OrgCooperationUpdateApply orgCooperationUpdateApply) throws ThriftServiceException, TException {
      return orgCooperationUpdateApplyMapper.getAll(orgCooperationUpdateApply);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   @Override
   public OrgCooperationUpdateApply getById(int pid) throws ThriftServiceException, TException {
      OrgCooperationUpdateApply orgCooperationUpdateApply = orgCooperationUpdateApplyMapper.getById(pid);
      if (orgCooperationUpdateApply==null) {
         return new OrgCooperationUpdateApply();
      }
      return orgCooperationUpdateApply;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   @Override
   public int insert(OrgCooperationUpdateApply orgCooperationUpdateApply) throws ThriftServiceException, TException {
      orgCooperationUpdateApplyMapper.insert(orgCooperationUpdateApply);
      return orgCooperationUpdateApply.getPid();
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   @Override
   public int update(OrgCooperationUpdateApply orgCooperationUpdateApply) throws ThriftServiceException, TException {
      return orgCooperationUpdateApplyMapper.update(orgCooperationUpdateApply);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return orgCooperationUpdateApplyMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-01-06 15:32:03
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return orgCooperationUpdateApplyMapper.deleteByIds(pids);
   }

   /**
    * 根据机构id和申请状态集合查询
    *@author:liangyanjun
    *@time:2017年1月9日上午11:00:30
    */
    @Override
    public List<OrgCooperationUpdateApply> getByOrgIdAndApplyStatus(int orgId, List<Integer> applyStatusList) throws TException {
        return orgCooperationUpdateApplyMapper.getByOrgIdAndApplyStatus(orgId,applyStatusList);
    }

    /**根据机构id查询最后一次申请的机构合作信息修改申请信息
     *@author:liangyanjun
     *@time:2017年1月9日下午4:52:07
     */
    @Override
    public OrgCooperationUpdateApply getLastByOrgId(int orgId) throws TException {
        OrgCooperationUpdateApply orgCooperationUpdateApply = orgCooperationUpdateApplyMapper.getLastByOrgId(orgId);
        if (orgCooperationUpdateApply==null) {
            return new OrgCooperationUpdateApply();
        }
        return orgCooperationUpdateApply;
    }

    /**
     * 历史列表(分页查询)
     *@author:liangyanjun
     *@time:2017年1月10日下午5:10:21
     */
    @Override
    public List<OrgCooperationUpdateApply> queryApplyHisIndex(OrgCooperationUpdateApply query) throws TException {
        return orgCooperationUpdateApplyMapper.queryApplyHisIndex(query);
    }

    /**
     * 历史列表总数
     *@author:liangyanjun
     *@time:2017年1月10日下午5:10:21
     */
    @Override
    public int getApplyHisIndexTotal(OrgCooperationUpdateApply query) throws TException {
        return orgCooperationUpdateApplyMapper.getApplyHisIndexTotal(query);
    }

}
