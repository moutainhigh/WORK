package com.xlkfinance.bms.server.partner.service;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
import com.xlkfinance.bms.rpc.partner.CusCreditInfoService.Iface;
import com.xlkfinance.bms.rpc.report.HandleDifferWarnReport;
import com.xlkfinance.bms.server.partner.mapper.CusCreditInfoMapper;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： czz <br>
 * ★☆ @time：2017-02-15 18:35:29 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 客户征信信息表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@Service("cusCreditInfoServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.partner.CusCreditInfoService")
public class CusCreditInfoServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(CusCreditInfoServiceImpl.class);

   @Resource(name = "cusCreditInfoMapper")
   private CusCreditInfoMapper cusCreditInfoMapper;

   /**
    *根据条件查询所有
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   @Override
   public List<CusCreditInfo> getAll(CusCreditInfo cusCreditInfo) throws ThriftServiceException, TException {
      return cusCreditInfoMapper.getAll(cusCreditInfo);
   }

   /**
    *根据id查询
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   @Override
   public CusCreditInfo getById(int pid) throws ThriftServiceException, TException {
      CusCreditInfo cusCreditInfo = cusCreditInfoMapper.getById(pid);
      if (cusCreditInfo==null) {
         return new CusCreditInfo();
      }
      return cusCreditInfo;
   }

   /**
    *插入一条数据
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   @Override
   public int insert(CusCreditInfo cusCreditInfo) throws ThriftServiceException, TException {
      return cusCreditInfoMapper.insert(cusCreditInfo);
   }

   /**
    *根据id更新数据
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   @Override
   public int update(CusCreditInfo cusCreditInfo) throws ThriftServiceException, TException {
      return cusCreditInfoMapper.update(cusCreditInfo);
   }

   /**
    *根据id删除数据
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return cusCreditInfoMapper.deleteById(pid);
   }

   /**
    *根据id集合删除
    *@author:czz
    *@time:2017-02-15 18:35:29
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return cusCreditInfoMapper.deleteByIds(pids);
   }

   /**
    * 查询客户征信列表（可分页）
    */
	@Override
	public List<CusCreditInfo> selectCusCreditList(CusCreditInfo cusCreditInfo)
			throws TException {
		return cusCreditInfoMapper.selectCusCreditList(cusCreditInfo);
	}
	
	/**
	 * 查询客户征信总数
	 */
	@Override
	public int selectCusCreditTotal(CusCreditInfo cusCreditInfo) throws TException {
		return cusCreditInfoMapper.selectCusCreditTotal(cusCreditInfo);
	}

}
