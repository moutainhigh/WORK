package com.xlkfinance.bms.server.aom.org.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.org.OrgCooperationContract;
import com.qfang.xk.aom.rpc.org.OrgCooperationContractService.Iface;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperationContractMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-14 15:54:33 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构合作合同<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgCooperationContractServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.org.OrgCooperationContractService")
public class OrgCooperationContractServiceImpl implements Iface {
   
	@Resource(name = "orgCooperationContractMapper")
   private OrgCooperationContractMapper orgCooperationContractMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   @Override
   public List<OrgCooperationContract> getAll(OrgCooperationContract orgCooperationContract) throws ThriftServiceException, TException {
      return orgCooperationContractMapper.getAll(orgCooperationContract);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   @Override
   public OrgCooperationContract getById(int pid) throws ThriftServiceException, TException {
      OrgCooperationContract orgCooperationContract = orgCooperationContractMapper.getById(pid);
      if (orgCooperationContract==null) {
         return new OrgCooperationContract();
      }
      return orgCooperationContract;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   @Override
   public int insert(OrgCooperationContract orgCooperationContract) throws ThriftServiceException, TException {
      return orgCooperationContractMapper.insert(orgCooperationContract);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   @Override
   public int update(OrgCooperationContract orgCooperationContract) throws ThriftServiceException, TException {
      return orgCooperationContractMapper.update(orgCooperationContract);
   }

   /**
    *根据id删除合作合同数据
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return orgCooperationContractMapper.delOrgCooperate(pid);
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-14 15:54:33
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return 0;
   }

   /**
    * 根据机构id和类型查询机构合作合同
    * contractType
    *@author:liangyanjun
    *@time:2016年7月18日上午10:10:45
    */
   @Override
   public OrgCooperationContract getByOrgIdAndType(int orgId, int contractType) throws TException {
      OrgCooperationContract contract = orgCooperationContractMapper.getByOrgIdAndType(orgId,contractType);
      if (contract==null) {
         return new OrgCooperationContract();
      }
      return contract;
   }

}
