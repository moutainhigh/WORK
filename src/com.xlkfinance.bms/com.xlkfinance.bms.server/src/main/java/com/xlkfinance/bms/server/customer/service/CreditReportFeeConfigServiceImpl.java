package com.xlkfinance.bms.server.customer.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfig;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHis;
import com.xlkfinance.bms.rpc.customer.CreditReportFeeConfigService.Iface;
import com.xlkfinance.bms.server.customer.mapper.CreditReportFeeConfigMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2018-01-15 15:49:52 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 征信报告费用配置表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("creditReportFeeConfigServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.customer.CreditReportFeeConfigService")
public class CreditReportFeeConfigServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(CreditReportFeeConfigServiceImpl.class);

   @Resource(name = "creditReportFeeConfigMapper")
   private CreditReportFeeConfigMapper creditReportFeeConfigMapper;

   /**
    *根据条件查询所有
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   @Override
   public List<CreditReportFeeConfig> getAll(CreditReportFeeConfig creditReportFeeConfig) throws ThriftServiceException, TException {
      return creditReportFeeConfigMapper.getAll(creditReportFeeConfig);
   }

   /**
    *根据id查询
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   @Override
   public CreditReportFeeConfig getById(int pid) throws ThriftServiceException, TException {
      CreditReportFeeConfig creditReportFeeConfig = creditReportFeeConfigMapper.getById(pid);
      if (creditReportFeeConfig==null) {
         return new CreditReportFeeConfig();
      }
      return creditReportFeeConfig;
   }

   /**
    *插入一条数据
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   @Override
   public int insert(CreditReportFeeConfig creditReportFeeConfig) throws ThriftServiceException, TException {
	   int pid = 0 ; 
	   creditReportFeeConfigMapper.insert(creditReportFeeConfig);
	   pid = creditReportFeeConfig.getPid();
	   return pid;
   }

   /**
    *根据id更新数据
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   @Override
   public int update(CreditReportFeeConfig creditReportFeeConfig) throws ThriftServiceException, TException {
      return creditReportFeeConfigMapper.update(creditReportFeeConfig);
   }

   /**
    *根据id删除数据
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return creditReportFeeConfigMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:czz
    *@time:2018-01-15 15:49:52
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return creditReportFeeConfigMapper.deleteByIds(pids);
   }
   
	@Override
	public List<CreditReportFeeConfig> selectList(CreditReportFeeConfig creditReportFeeConfig)
			throws TException {
		return creditReportFeeConfigMapper.selectList(creditReportFeeConfig);
	}
	
	@Override
	public int selectTotal(CreditReportFeeConfig creditReportFeeConfig) throws TException {
		return creditReportFeeConfigMapper.selectTotal(creditReportFeeConfig);
	}

}
