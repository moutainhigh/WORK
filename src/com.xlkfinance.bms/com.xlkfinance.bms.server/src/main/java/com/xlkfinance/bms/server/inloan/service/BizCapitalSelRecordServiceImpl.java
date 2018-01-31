package com.xlkfinance.bms.server.inloan.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.inloan.BizCapitalSelRecord;
import com.xlkfinance.bms.rpc.inloan.BizCapitalSelRecordService.Iface;
import com.xlkfinance.bms.server.inloan.mapper.BizCapitalSelRecordMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2017-12-13 17:01:35 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 资方选择记录<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("bizCapitalSelRecordServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.inloan.BizCapitalSelRecordService")
public class BizCapitalSelRecordServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(BizCapitalSelRecordServiceImpl.class);

   @Resource(name = "bizCapitalSelRecordMapper")
   private BizCapitalSelRecordMapper bizCapitalSelRecordMapper;
   
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   @Override
   public List<BizCapitalSelRecord> getAll(BizCapitalSelRecord bizCapitalSelRecord) throws ThriftServiceException, TException {
      return bizCapitalSelRecordMapper.getAll(bizCapitalSelRecord);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   @Override
   public BizCapitalSelRecord getById(int pid) throws ThriftServiceException, TException {
      BizCapitalSelRecord bizCapitalSelRecord = bizCapitalSelRecordMapper.getById(pid);
      if (bizCapitalSelRecord==null) {
         return new BizCapitalSelRecord();
      }
      return bizCapitalSelRecord;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   @Override
   public int insert(BizCapitalSelRecord bizCapitalSelRecord) throws ThriftServiceException, TException {
      return bizCapitalSelRecordMapper.insert(bizCapitalSelRecord);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   @Override
   public int update(BizCapitalSelRecord bizCapitalSelRecord) throws ThriftServiceException, TException {
      return bizCapitalSelRecordMapper.update(bizCapitalSelRecord);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return bizCapitalSelRecordMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2017-12-13 17:01:35
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return bizCapitalSelRecordMapper.deleteByIds(pids);
   }

	@Override
	public List<BizCapitalSelRecord> getAllByProjectId (int projectId) throws TException
	{
	
		return bizCapitalSelRecordMapper.getAllByProjectId(projectId);
			
	}

}
