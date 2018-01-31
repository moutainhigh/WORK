package com.xlkfinance.bms.server.fddafterloan.service;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.fddafterloan.BizStorageInfo;
import com.xlkfinance.bms.rpc.fddafterloan.BizStorageInfoService.Iface;
import com.xlkfinance.bms.server.fddafterloan.mapper.BizStorageInfoMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-19 09:32:16 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 入库材料清单<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("bizStorageInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.fddafterloan.BizStorageInfoService")
public class BizStorageInfoServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(BizStorageInfoServiceImpl.class);

   @Resource(name = "bizStorageInfoMapper")
   private BizStorageInfoMapper bizStorageInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   @Override
   public List<BizStorageInfo> getAll(BizStorageInfo bizStorageInfo) throws ThriftServiceException, TException {
      List<BizStorageInfo> list = bizStorageInfoMapper.getAll(bizStorageInfo);
      if (list==null||list.size()==0) {
         return new ArrayList<BizStorageInfo>();
      }
      return list;
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   @Override
   public BizStorageInfo getById(int pid) throws ThriftServiceException, TException {
      BizStorageInfo bizStorageInfo = bizStorageInfoMapper.getById(pid);
      if (bizStorageInfo==null) {
         return new BizStorageInfo();
      }
      return bizStorageInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   @Override
   public int insert(BizStorageInfo bizStorageInfo) throws ThriftServiceException, TException {
      return bizStorageInfoMapper.insert(bizStorageInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   @Override
   public int update(BizStorageInfo bizStorageInfo) throws ThriftServiceException, TException {
      return bizStorageInfoMapper.update(bizStorageInfo);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return bizStorageInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-19 09:32:16
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return bizStorageInfoMapper.deleteByIds(pids);
   }

   /**
    * 入库材料清单（分页查询）
    *@author:liangyanjun
    *@time:2017年12月20日上午10:52:20
    */
   @Override
   public List<BizStorageInfo> queryStorageInfo(BizStorageInfo query) throws TException {
      return bizStorageInfoMapper.queryStorageInfo(query);
   }

   /**
    * 入库材料清单条数（分页查询）
    *@author:liangyanjun
    *@time:2017年12月20日上午10:52:20
    */
   @Override
   public int getStorageInfoTotal(BizStorageInfo query) throws TException {
      return bizStorageInfoMapper.getStorageInfoTotal(query);
   }

}
