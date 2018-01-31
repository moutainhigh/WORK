package com.xlkfinance.bms.server.customer.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHis;
import com.xlkfinance.bms.rpc.customer.CusCreditReportHisService.Iface;
import com.xlkfinance.bms.server.customer.mapper.CusCreditReportHisMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-06-06 17:04:54 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 客户征信报告记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("cusCreditReportHisServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.customer.CusCreditReportHisService")
public class CusCreditReportHisServiceImpl implements Iface {
   @Resource(name = "cusCreditReportHisMapper")
   private CusCreditReportHisMapper cusCreditReportHisMapper;

   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   @Override
   public List<CusCreditReportHis> getAll(CusCreditReportHis cusCreditReportHis) throws ThriftServiceException, TException {
      return cusCreditReportHisMapper.getAll(cusCreditReportHis);
   }

   /**
    *根据id查询
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   @Override
   public CusCreditReportHis getById(int pid) throws ThriftServiceException, TException {
      CusCreditReportHis cusCreditReportHis = cusCreditReportHisMapper.getById(pid);
      if (cusCreditReportHis==null) {
         return new CusCreditReportHis();
      }
      return cusCreditReportHis;
   }

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   @Override
   public int insert(CusCreditReportHis cusCreditReportHis) throws ThriftServiceException, TException {
	   cusCreditReportHisMapper.insert(cusCreditReportHis);
	   return cusCreditReportHis.getPid();
   }

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   @Override
   public int update(CusCreditReportHis cusCreditReportHis) throws ThriftServiceException, TException {
      return cusCreditReportHisMapper.update(cusCreditReportHis);
   }

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return cusCreditReportHisMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-06-06 17:04:54
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return cusCreditReportHisMapper.deleteByIds(pids);
   }

	@Override
	public List<CusCreditReportHis> selectList(CusCreditReportHis cusCreditReportHis)
			throws TException {
		return cusCreditReportHisMapper.selectList(cusCreditReportHis);
	}
	
	@Override
	public int selectTotal(CusCreditReportHis cusCreditReportHis) throws TException {
		return cusCreditReportHisMapper.selectTotal(cusCreditReportHis);
	}
	
	@Override
	public List<CusCreditReportHis> selectCreditReportFeeList(CusCreditReportHis cusCreditReportHis)
			throws TException {
		return cusCreditReportHisMapper.selectCreditReportFeeList(cusCreditReportHis);
	}
	
	@Override
	public int selectCreditReportFeeListTotal(CusCreditReportHis cusCreditReportHis) throws TException {
		return cusCreditReportHisMapper.selectCreditReportFeeListTotal(cusCreditReportHis);
	}
	
	@Override
	public CusCreditReportHis selectCreditReportFeeSum(CusCreditReportHis cusCreditReportHis)
			throws TException {
		CusCreditReportHis his= cusCreditReportHisMapper.selectCreditReportFeeSum(cusCreditReportHis);
		if(his == null){
			his = new CusCreditReportHis();
		}
		return his;
		
	}

}
