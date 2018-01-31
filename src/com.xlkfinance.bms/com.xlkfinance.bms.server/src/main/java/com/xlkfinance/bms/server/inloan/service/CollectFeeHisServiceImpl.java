package com.xlkfinance.bms.server.inloan.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHis;
import com.xlkfinance.bms.rpc.inloan.CollectFeeHisService.Iface;
import com.xlkfinance.bms.server.inloan.mapper.CollectFeeHisMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-03-23 14:54:36 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务收费历史表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("collectFeeHisServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.CollectFeeHisService")
public class CollectFeeHisServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(CollectFeeHisServiceImpl.class);

   @Resource(name = "collectFeeHisMapper")
   private CollectFeeHisMapper collectFeeHisMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   @Override
   public List<CollectFeeHis> getAll(CollectFeeHis collectFeeHis) throws ThriftServiceException, TException {
      return collectFeeHisMapper.getAll(collectFeeHis);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   @Override
   public CollectFeeHis getById(int pid) throws ThriftServiceException, TException {
      CollectFeeHis collectFeeHis = collectFeeHisMapper.getById(pid);
      if (collectFeeHis==null) {
         return new CollectFeeHis();
      }
      return collectFeeHis;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   @Override
   public int insert(CollectFeeHis collectFeeHis) throws ThriftServiceException, TException {
      return collectFeeHisMapper.insert(collectFeeHis);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   @Override
   public int update(CollectFeeHis collectFeeHis) throws ThriftServiceException, TException {
      return collectFeeHisMapper.update(collectFeeHis);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return collectFeeHisMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-03-23 14:54:36
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return collectFeeHisMapper.deleteByIds(pids);
   }

   /**
    * 财务收费历史列表(分页查询)
    *@author:liangyanjun
    *@time:2017年3月23日下午2:55:05
    */
    @Override
    public List<CollectFeeHis> queryCollectFeeHis(CollectFeeHis query) throws TException {
        return collectFeeHisMapper.queryCollectFeeHis(query);
    }
    
    /**
     * 财务收费历史列表总数
     *@author:liangyanjun
     *@time:2017年3月23日下午2:55:05
     */
    @Override
    public int getCollectFeeHisTotal(CollectFeeHis query) throws TException {
        return collectFeeHisMapper.getCollectFeeHisTotal(query);
    }
    /**
     * 查询收费历史金额总和(咨询费,手续费,佣金)
     *@author:liangyanjun
     *@time:2017年3月24日上午10:11:55
     *@param collectFeeHis
     *@return
     *@throws TException
     */
    @Override
    public CollectFeeHis getCollectFeeHisMoney(CollectFeeHis collectFeeHis) throws TException {
        double consultingFee = 0;//咨询费
        double poundage = 0;//手续费
        double brokerage = 0;//佣金
        List<CollectFeeHis> list = collectFeeHisMapper.getAll(collectFeeHis);
        for (CollectFeeHis his : list) {
            consultingFee+=his.getConsultingFee();
            poundage+=his.getPoundage();
            brokerage+=+his.getBrokerage();
        }
        CollectFeeHis result = new CollectFeeHis();
        result.setConsultingFee(consultingFee);
        result.setPoundage(poundage);
        result.setBrokerage(brokerage);
        return result;
    }

}
