package com.xlkfinance.bms.server.aom.system.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfo;
import com.qfang.xk.aom.rpc.system.OrgSysLogInfoService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.system.mapper.OrgSysLogInfoMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-29 15:22:12 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 系统访问日志<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orgSysLogInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.system.OrgSysLogInfoService")
public class OrgSysLogInfoServiceImpl implements Iface {
   @Resource(name = "orgSysLogInfoMapper")
   private OrgSysLogInfoMapper orgSysLogInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-29 15:22:12
    */
   @Override
   public List<OrgSysLogInfo> getAll(OrgSysLogInfo orgSysLogInfo) throws ThriftServiceException, TException {
      return orgSysLogInfoMapper.getAll(orgSysLogInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-29 15:22:12
    */
   @Override
   public OrgSysLogInfo getById(int pid) throws ThriftServiceException, TException {
      OrgSysLogInfo orgSysLogInfo = orgSysLogInfoMapper.getById(pid);
      if (orgSysLogInfo==null) {
         return new OrgSysLogInfo();
      }
      return orgSysLogInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-29 15:22:12
    */
   @Override
   public int insert(OrgSysLogInfo orgSysLogInfo) throws ThriftServiceException, TException {
      return orgSysLogInfoMapper.insert(orgSysLogInfo);
   }

}
