package com.xlkfinance.bms.server.customer.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusRelation;
import com.xlkfinance.bms.rpc.customer.CusRelationService.Iface;
import com.xlkfinance.bms.server.customer.mapper.CusRelationMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-08-08 09:56:37 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 客户关系表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("cusRelationServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.customer.CusRelationService")
public class CusRelationServiceImpl implements Iface {
   @Resource(name = "cusRelationMapper")
   private CusRelationMapper cusRelationMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-08-08 09:56:37
    */
   @Override
   public List<CusRelation> getAll(CusRelation cusRelation) throws ThriftServiceException, TException {
      return cusRelationMapper.getAll(cusRelation);
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-08-08 09:56:37
    */
   @Override
   public int insert(CusRelation cusRelation) throws ThriftServiceException, TException {
      return cusRelationMapper.insert(cusRelation);
   }


	@Override
	public int delCusReltion(CusRelation cusRelation) throws TException {
		// TODO Auto-generated method stub
		return 0;
	}


}
