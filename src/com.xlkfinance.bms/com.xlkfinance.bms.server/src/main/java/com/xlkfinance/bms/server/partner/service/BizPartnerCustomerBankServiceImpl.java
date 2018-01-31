package com.xlkfinance.bms.server.partner.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBank;
import com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBankService.Iface;
import com.xlkfinance.bms.server.partner.mapper.BizPartnerCustomerBankMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-09-19 19:18:59 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构客户银行开户<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("bizPartnerCustomerBankServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.partner.BizPartnerCustomerBankService")
public class BizPartnerCustomerBankServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(BizPartnerCustomerBankServiceImpl.class);

   @Resource(name = "bizPartnerCustomerBankMapper")
   private BizPartnerCustomerBankMapper bizPartnerCustomerBankMapper;

   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   @Override
   public List<BizPartnerCustomerBank> getAll(BizPartnerCustomerBank bizPartnerCustomerBank) throws ThriftServiceException, TException {
      return bizPartnerCustomerBankMapper.getAll(bizPartnerCustomerBank);
   }

   /**
    *根据id查询
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   @Override
   public BizPartnerCustomerBank getById(int pid) throws ThriftServiceException, TException {
      BizPartnerCustomerBank bizPartnerCustomerBank = bizPartnerCustomerBankMapper.getById(pid);
      if (bizPartnerCustomerBank==null) {
         return new BizPartnerCustomerBank();
      }
      return bizPartnerCustomerBank;
   }

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   @Override
   public int insert(BizPartnerCustomerBank bizPartnerCustomerBank) throws ThriftServiceException, TException {
      return bizPartnerCustomerBankMapper.insert(bizPartnerCustomerBank);
   }

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   @Override
   public int update(BizPartnerCustomerBank bizPartnerCustomerBank) throws ThriftServiceException, TException {
      return bizPartnerCustomerBankMapper.update(bizPartnerCustomerBank);
   }

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return bizPartnerCustomerBankMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-09-19 19:18:59
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return bizPartnerCustomerBankMapper.deleteByIds(pids);
   }
   
   
	@Override
	public List<BizPartnerCustomerBank> selectList(BizPartnerCustomerBank bizPartnerCustomerBank)
			throws TException {
		return bizPartnerCustomerBankMapper.selectList(bizPartnerCustomerBank);
	}
	
	@Override
	public int selectTotal(BizPartnerCustomerBank bizPartnerCustomerBank) throws TException {
		return bizPartnerCustomerBankMapper.selectTotal(bizPartnerCustomerBank);
	}
   
   
}
