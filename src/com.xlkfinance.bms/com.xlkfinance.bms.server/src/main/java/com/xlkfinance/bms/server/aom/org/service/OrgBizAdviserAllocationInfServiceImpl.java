package com.xlkfinance.bms.server.aom.org.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInf;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInfPage;
import com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInfService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.org.mapper.OrgBizAdviserAllocationInfMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-09-08 09:55:23 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 商务顾问分配<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orgBizAdviserAllocationInfServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.org.OrgBizAdviserAllocationInfService")
public class OrgBizAdviserAllocationInfServiceImpl implements Iface {
   @Resource(name = "orgBizAdviserAllocationInfMapper")
   private OrgBizAdviserAllocationInfMapper orgBizAdviserAllocationInfMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   @Override
   public List<OrgBizAdviserAllocationInf> getAll(OrgBizAdviserAllocationInf orgBizAdviserAllocationInf) throws ThriftServiceException, TException {
      return orgBizAdviserAllocationInfMapper.getAll(orgBizAdviserAllocationInf);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   @Override
   public OrgBizAdviserAllocationInf getById(int pid) throws ThriftServiceException, TException {
      OrgBizAdviserAllocationInf orgBizAdviserAllocationInf = orgBizAdviserAllocationInfMapper.getById(pid);
      if (orgBizAdviserAllocationInf==null) {
         return new OrgBizAdviserAllocationInf();
      }
      return orgBizAdviserAllocationInf;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   @Override
   public int insert(OrgBizAdviserAllocationInf orgBizAdviserAllocationInf) throws ThriftServiceException, TException {
      return orgBizAdviserAllocationInfMapper.insert(orgBizAdviserAllocationInf);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   @Override
   public int update(OrgBizAdviserAllocationInf orgBizAdviserAllocationInf) throws ThriftServiceException, TException {
      return orgBizAdviserAllocationInfMapper.update(orgBizAdviserAllocationInf);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return orgBizAdviserAllocationInfMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-09-08 09:55:23
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return orgBizAdviserAllocationInfMapper.deleteByIds(pids);
   }

   /**
    * 机构分配列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月8日上午11:11:03
    */
   @Override
   public List<OrgBizAdviserAllocationInfPage> queryOrgBizAdviserAllocationInfPage(OrgBizAdviserAllocationInfPage query) throws TException {
      return orgBizAdviserAllocationInfMapper.queryOrgBizAdviserAllocationInfPage(query);
   }

   /**
    * 机构分配列表查询总数
    *@author:liangyanjun
    *@time:2016年9月8日上午11:11:03
    */
   @Override
   public int getOrgBizAdviserAllocationInfPageTotal(OrgBizAdviserAllocationInfPage query) throws TException {
      return orgBizAdviserAllocationInfMapper.getOrgBizAdviserAllocationInfPageTotal(query);
   }

}
