package com.xlkfinance.bms.server.fddafterloan.service;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgage;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgageIndexPage;
import com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgageService.Iface;
import com.xlkfinance.bms.server.fddafterloan.mapper.BizProjectMortgageMapper;
import com.xlkfinance.bms.server.fddafterloan.mapper.BizStorageInfoMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-19 09:28:48 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 抵押管理信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("bizProjectMortgageServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.fddafterloan.BizProjectMortgageService")
public class BizProjectMortgageServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(BizProjectMortgageServiceImpl.class);

   @Resource(name = "bizProjectMortgageMapper")
   private BizProjectMortgageMapper bizProjectMortgageMapper;
   
   @Resource(name = "bizStorageInfoMapper")
   private BizStorageInfoMapper bizStorageInfoMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   @Override
   public List<BizProjectMortgage> getAll(BizProjectMortgage bizProjectMortgage) throws ThriftServiceException, TException {
      List list = bizProjectMortgageMapper.getAll(bizProjectMortgage);
      if (list==null||list.size()==0) {
         return new ArrayList<BizProjectMortgage>();
      }
      return list;
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   @Override
   public BizProjectMortgage getById(int pid) throws ThriftServiceException, TException {
      BizProjectMortgage bizProjectMortgage = bizProjectMortgageMapper.getById(pid);
      if (bizProjectMortgage==null) {
         return new BizProjectMortgage();
      }
      return bizProjectMortgage;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   @Override
   public int insert(BizProjectMortgage bizProjectMortgage) throws ThriftServiceException, TException {
      return bizProjectMortgageMapper.insert(bizProjectMortgage);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   @Override
   public int update(BizProjectMortgage bizProjectMortgage) throws ThriftServiceException, TException {
      return bizProjectMortgageMapper.update(bizProjectMortgage);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return bizProjectMortgageMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-19 09:28:48
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return bizProjectMortgageMapper.deleteByIds(pids);
   }

   /**
    * 抵押管理列表（分页查询）
    *@author:liangyanjun
    *@time:2017年12月19日上午11:11:35
    */
   @Override
   public List<BizProjectMortgageIndexPage> queryMortgageIndexPage(BizProjectMortgageIndexPage query) throws TException {
      return bizProjectMortgageMapper.queryMortgageIndexPage(query);
   }

   /**
    * 抵押管理条数（分页查询）
    *@author:liangyanjun
    *@time:2017年12月19日上午11:11:35
    */
   @Override
   public int getMortgageIndexPageTotal(BizProjectMortgageIndexPage query) throws TException {
      return bizProjectMortgageMapper.getMortgageIndexPageTotal(query);
   }

}
