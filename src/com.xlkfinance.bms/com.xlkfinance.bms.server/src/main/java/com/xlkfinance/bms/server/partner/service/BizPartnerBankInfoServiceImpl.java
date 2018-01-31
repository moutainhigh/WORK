package com.xlkfinance.bms.server.partner.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.partner.BizPartnerBankInfo;
import com.xlkfinance.bms.rpc.partner.BizPartnerBankInfoService.Iface;
import com.xlkfinance.bms.server.partner.mapper.BizPartnerBankInfoMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-03-08 19:18:05 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 资金机构银行信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("bizPartnerBankInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.partner.BizPartnerBankInfoService")
public class BizPartnerBankInfoServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(BizPartnerBankInfoServiceImpl.class);

   @Resource(name = "bizPartnerBankInfoMapper")
   private BizPartnerBankInfoMapper bizPartnerBankInfoMapper;

   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   @Override
   public List<BizPartnerBankInfo> getAll(BizPartnerBankInfo bizPartnerBankInfo) throws ThriftServiceException, TException {
      return bizPartnerBankInfoMapper.getAll(bizPartnerBankInfo);
   }

   /**
    *根据id查询
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   @Override
   public BizPartnerBankInfo getById(int pid) throws ThriftServiceException, TException {
      BizPartnerBankInfo bizPartnerBankInfo = bizPartnerBankInfoMapper.getById(pid);
      if (bizPartnerBankInfo==null) {
         return new BizPartnerBankInfo();
      }
      return bizPartnerBankInfo;
   }

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   @Override
   public int insert(BizPartnerBankInfo bizPartnerBankInfo) throws ThriftServiceException, TException {
      return bizPartnerBankInfoMapper.insert(bizPartnerBankInfo);
   }

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   @Override
   public int update(BizPartnerBankInfo bizPartnerBankInfo) throws ThriftServiceException, TException {
      return bizPartnerBankInfoMapper.update(bizPartnerBankInfo);
   }

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return bizPartnerBankInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-03-08 19:18:05
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return bizPartnerBankInfoMapper.deleteByIds(pids);
   }

	@Override
	public List<BizPartnerBankInfo> selectPartnerBankList(
			BizPartnerBankInfo bizPartnerBankInfo) throws TException {
		 
		return bizPartnerBankInfoMapper.selectPartnerBankList(bizPartnerBankInfo);
	}
	
	@Override
	public int selectPartnerBankTotal(BizPartnerBankInfo bizPartnerBankInfo)
			throws TException {
		return bizPartnerBankInfoMapper.selectPartnerBankTotal(bizPartnerBankInfo);
	}

}
