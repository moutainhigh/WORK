package com.xlkfinance.bms.server.system.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.OrgCapitalCost;
import com.xlkfinance.bms.rpc.system.OrgCapitalCostService.Iface;
import com.xlkfinance.bms.server.system.mapper.OrgCapitalCostMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-09-14 14:27:40 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构资金成本<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgCapitalCostServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.system.OrgCapitalCostService")
public class OrgCapitalCostServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(OrgCapitalCostServiceImpl.class);

   @Resource(name = "orgCapitalCostMapper")
   private OrgCapitalCostMapper orgCapitalCostMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   @Override
   public List<OrgCapitalCost> getAll(OrgCapitalCost orgCapitalCost) throws ThriftServiceException, TException {
      return orgCapitalCostMapper.getAll(orgCapitalCost);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   @Override
   public OrgCapitalCost getById(int pid) throws ThriftServiceException, TException {
      OrgCapitalCost orgCapitalCost = orgCapitalCostMapper.getById(pid);
      if (orgCapitalCost==null) {
         return new OrgCapitalCost();
      }
      return orgCapitalCost;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   @Override
   public int insert(OrgCapitalCost orgCapitalCost) throws ThriftServiceException, TException {
	   try {
		   orgCapitalCostMapper.insert(orgCapitalCost);
		} catch (Exception e) {
			logger.error("添加资金成本信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
	   return orgCapitalCost.getPid();
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-09-14 14:27:40
    */
   @Override
   public int update(OrgCapitalCost orgCapitalCost) throws ThriftServiceException, TException {
      return orgCapitalCostMapper.update(orgCapitalCost);
   }

   @Override
   public int delCapitionCost(int pid) throws TException {
		return orgCapitalCostMapper.delCapitionCost(pid);
   }


}
