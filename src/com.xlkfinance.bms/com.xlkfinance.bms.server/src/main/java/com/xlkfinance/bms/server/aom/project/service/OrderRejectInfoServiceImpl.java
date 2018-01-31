package com.xlkfinance.bms.server.aom.project.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.project.OrderRejectInfo;
import com.qfang.xk.aom.rpc.project.OrderRejectInfoService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.project.mapper.OrderRejectInfoMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-09-01 10:52:32 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务申请驳回记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("orderRejectInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.project.OrderRejectInfoService")
public class OrderRejectInfoServiceImpl implements Iface {
   @Resource(name = "orderRejectInfoMapper")
   private OrderRejectInfoMapper orderRejectInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   @Override
   public List<OrderRejectInfo> getAll(OrderRejectInfo orderRejectInfo) throws ThriftServiceException, TException {
      return orderRejectInfoMapper.getAll(orderRejectInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   @Override
   public OrderRejectInfo getById(int pid) throws ThriftServiceException, TException {
      OrderRejectInfo orderRejectInfo = orderRejectInfoMapper.getById(pid);
      if (orderRejectInfo==null) {
         return new OrderRejectInfo();
      }
      return orderRejectInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   @Override
   public int insert(OrderRejectInfo orderRejectInfo) throws ThriftServiceException, TException {
      return orderRejectInfoMapper.insert(orderRejectInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-01 10:52:32
    */
   @Override
   public int update(OrderRejectInfo orderRejectInfo) throws ThriftServiceException, TException {
      return orderRejectInfoMapper.update(orderRejectInfo);
   }


}
