package com.xlkfinance.bms.server.system.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.constant.Constants;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.system.SysSmsInfo;
import com.xlkfinance.bms.rpc.system.SysSmsInfoService.Iface;
import com.xlkfinance.bms.server.system.mapper.SysSmsInfoMapper;
import com.xlkfinance.bms.util.SMSUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-01-18 09:20:17 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 短信表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("sysSmsInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.SysSmsInfoService")
public class SysSmsInfoServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(SysSmsInfoServiceImpl.class);

   @Resource(name = "sysSmsInfoMapper")
   private SysSmsInfoMapper sysSmsInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   @Override
   public List<SysSmsInfo> getAll(SysSmsInfo sysSmsInfo) throws ThriftServiceException, TException {
      return sysSmsInfoMapper.getAll(sysSmsInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   @Override
   public SysSmsInfo getById(int pid) throws ThriftServiceException, TException {
      SysSmsInfo sysSmsInfo = sysSmsInfoMapper.getById(pid);
      if (sysSmsInfo==null) {
         return new SysSmsInfo();
      }
      return sysSmsInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   @Override
   public int insert(SysSmsInfo sysSmsInfo) throws ThriftServiceException, TException {
      return sysSmsInfoMapper.insert(sysSmsInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   @Override
   public int update(SysSmsInfo sysSmsInfo) throws ThriftServiceException, TException {
      return sysSmsInfoMapper.update(sysSmsInfo);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return sysSmsInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-01-18 09:20:17
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return sysSmsInfoMapper.deleteByIds(pids);
   }

   /**
    * 发送短信
    *@author:liangyanjun
    *@time:2017年1月18日上午10:46:56
    */
    @Override
    public boolean sendSms(SysSmsInfo sysSmsInfo) throws TException {
        try {
            boolean isSucc = SMSUtil.sendMessage(sysSmsInfo.getTelphone(), sysSmsInfo.getContent());
            if (isSucc) {
                sysSmsInfo.setStatus(Constants.SUCCEED);
            } else {
                sysSmsInfo.setStatus(Constants.FAILED);
            }
            sysSmsInfoMapper.insert(sysSmsInfo);
            return isSucc;
        } catch (Exception e) {
            logger.error("发送短信异常：入参：sysSmsInfo:"+ sysSmsInfo +"。错误：" + ExceptionUtil.getExceptionMessage(e));
            return false;
        }
    }

}
