package com.xlkfinance.bms.server.beforeloan.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectRetreat;
import com.xlkfinance.bms.rpc.beforeloan.BizProjectRetreatService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.beforeloan.mapper.BizProjectRetreatMapper;

/**
 *  @author： baogang <br>
 *  @time：2016-10-24 11:43:30 <br>
 *  @version：  1.0<br>
 *  @lastMotifyTime： <br>
 *  @ClassAnnotation： 项目回撤关系表<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("bizProjectRetreatServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.beforeloan.BizProjectRetreatService")
public class BizProjectRetreatServiceImpl implements Iface {
   @Resource(name = "bizProjectRetreatMapper")
   private BizProjectRetreatMapper bizProjectRetreatMapper;

   /**
    *根据条件查询所有
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   @Override
   public List<BizProjectRetreat> getAll(BizProjectRetreat bizProjectRetreat) throws ThriftServiceException, TException {
      return bizProjectRetreatMapper.getAll(bizProjectRetreat);
   }

   /**
    *根据id查询
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   @Override
   public BizProjectRetreat getById(int pid) throws ThriftServiceException, TException {
      BizProjectRetreat bizProjectRetreat = bizProjectRetreatMapper.getById(pid);
      if (bizProjectRetreat==null) {
         return new BizProjectRetreat();
      }
      return bizProjectRetreat;
   }

   /**
    *插入一条数据
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   @Override
   public int insert(BizProjectRetreat bizProjectRetreat) throws ThriftServiceException, TException {
      return bizProjectRetreatMapper.insert(bizProjectRetreat);
   }

   /**
    *根据id更新数据
    *@author:baogang
    *@time:2016-10-24 11:43:30
    */
   @Override
   public int update(BizProjectRetreat bizProjectRetreat) throws ThriftServiceException, TException {
      return bizProjectRetreatMapper.update(bizProjectRetreat);
   }


}
