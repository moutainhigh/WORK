package com.xlkfinance.bms.server.aom.system.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.system.OrgUserMenuInfo;
import com.qfang.xk.aom.rpc.system.OrgUserMenuInfoService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserMenuInfoMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-26 14:36:25 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 用户与菜单关联表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orgUserMenuInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.system.OrgUserMenuInfoService")
public class OrgUserMenuInfoServiceImpl implements Iface {
   @Resource(name = "orgUserMenuInfoMapper")
   private OrgUserMenuInfoMapper orgUserMenuInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   @Override
   public List<OrgUserMenuInfo> getAll(OrgUserMenuInfo orgUserMenuInfo) throws ThriftServiceException, TException {
      return orgUserMenuInfoMapper.getAll(orgUserMenuInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   @Override
   public OrgUserMenuInfo getById(int pid) throws ThriftServiceException, TException {
      OrgUserMenuInfo orgUserMenuInfo = orgUserMenuInfoMapper.getById(pid);
      if (orgUserMenuInfo==null) {
         return new OrgUserMenuInfo();
      }
      return orgUserMenuInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   @Override
   public int insert(OrgUserMenuInfo orgUserMenuInfo) throws ThriftServiceException, TException {
      return orgUserMenuInfoMapper.insert(orgUserMenuInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   @Override
   public int update(OrgUserMenuInfo orgUserMenuInfo) throws ThriftServiceException, TException {
      return orgUserMenuInfoMapper.update(orgUserMenuInfo);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return orgUserMenuInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-26 14:36:25
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return orgUserMenuInfoMapper.deleteByIds(pids);
   }

}
