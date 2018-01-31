package com.xlkfinance.bms.server.aom.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfo;
import com.qfang.xk.aom.rpc.system.OrgSysMenuInfoService.Iface;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.system.mapper.OrgSysMenuInfoMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-11 09:42:39 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 系统菜单信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orgSysMenuInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.system.OrgSysMenuInfoService")
public class OrgSysMenuInfoServiceImpl implements Iface {
   @Resource(name = "orgSysMenuInfoMapper")
   private OrgSysMenuInfoMapper orgSysMenuInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   @Override
   public List<OrgSysMenuInfo> getAll(OrgSysMenuInfo orgSysMenuInfo) throws ThriftServiceException, TException {
      return orgSysMenuInfoMapper.getAll(orgSysMenuInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   @Override
   public OrgSysMenuInfo getById(int pid) throws ThriftServiceException, TException {
      return orgSysMenuInfoMapper.getById(pid);
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   @Override
   public int insert(OrgSysMenuInfo orgSysMenuInfo) throws ThriftServiceException, TException {
      return orgSysMenuInfoMapper.insert(orgSysMenuInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   @Override
   public int update(OrgSysMenuInfo orgSysMenuInfo) throws ThriftServiceException, TException {
      return orgSysMenuInfoMapper.update(orgSysMenuInfo);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return orgSysMenuInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-11 09:42:39
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return orgSysMenuInfoMapper.deleteByIds(pids);
   }

   /**
    * 获取菜单树（只包含二级菜单）
    *@author:liangyanjun
    *@time:2016年7月11日上午9:44:15
    */
   @Override
   public List<OrgSysMenuInfo> getTree(OrgUserInfo orgUserInfo) throws TException {
      return orgSysMenuInfoMapper.getTree(orgUserInfo);
   }

}
