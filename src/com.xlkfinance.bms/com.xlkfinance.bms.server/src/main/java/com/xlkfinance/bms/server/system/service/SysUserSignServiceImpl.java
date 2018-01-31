package com.xlkfinance.bms.server.system.service;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.system.SysUserSign;
import com.xlkfinance.bms.rpc.system.SysUserSignService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysUserSignMapper;

/**   
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年3月29日上午10:13:43 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("SysUserSignServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysUserSignService")
public class SysUserSignServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(SysUserSignServiceImpl.class);
   @Resource(name = "sysUserSignMapper")
   private SysUserSignMapper signMapper;

   @Override
   public int getSignDaysByUserId(int userId) throws TException {
      return signMapper.getSignDaysByUserId(userId);
   }

   @Override
   public boolean addSysUserSign(SysUserSign sysUserSign) throws TException {
      signMapper.addSysUserSign(sysUserSign);
      return true;
   }

   /**
    * 根据用户id检查今天是否已经签到，返回值大于0，则已经签到
    *@author:liangyanjun
    *@time:2016年3月29日上午10:54:32
    *@param userId
    *@return
    */
   @Override
   public int checkToDayIsSign(int userId) throws TException {
      return signMapper.checkToDayIsSign(userId);
   }

}
