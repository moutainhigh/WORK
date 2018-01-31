package com.xlkfinance.bms.server.aom.fee.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDTO;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetail;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailPage;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailService.Iface;
import com.qfang.xk.aom.rpc.fee.PartnerFeeSettleDetailPage;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.fee.mapper.OrgFeeSettleDetailMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-08-12 15:24:28 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 费用结算明细表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgFeeSettleDetailServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailService")
public class OrgFeeSettleDetailServiceImpl implements Iface {
   @Resource(name = "orgFeeSettleDetailMapper")
   private OrgFeeSettleDetailMapper orgFeeSettleDetailMapper;

   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   @Override
   public List<OrgFeeSettleDetail> getAll(OrgFeeSettleDetail orgFeeSettleDetail) throws ThriftServiceException, TException {
      return orgFeeSettleDetailMapper.getAll(orgFeeSettleDetail);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   @Override
   public OrgFeeSettleDetail getById(int pid) throws ThriftServiceException, TException {
      OrgFeeSettleDetail orgFeeSettleDetail = orgFeeSettleDetailMapper.getById(pid);
      if (orgFeeSettleDetail==null) {
         return new OrgFeeSettleDetail();
      }
      return orgFeeSettleDetail;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   @Override
   public int insert(OrgFeeSettleDetail orgFeeSettleDetail) throws ThriftServiceException, TException {
      return orgFeeSettleDetailMapper.insert(orgFeeSettleDetail);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   @Override
   public int update(OrgFeeSettleDetail orgFeeSettleDetail) throws ThriftServiceException, TException {
      return orgFeeSettleDetailMapper.update(orgFeeSettleDetail);
   }
   /**
    * 合伙人息费明细列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月16日下午3:08:05
    */
   @Override
   public List<PartnerFeeSettleDetailPage> queryPartnerFeeSettleDetailPage(PartnerFeeSettleDetailPage query) throws TException {
      return orgFeeSettleDetailMapper.queryPartnerFeeSettleDetailPage(query);
   }

   /**
    *@author:liangyanjun
    *@time:2016年8月16日下午3:08:05
    */
   @Override
   public int getPartnerFeeSettleDetailPageTotal(PartnerFeeSettleDetailPage query) throws TException {
      return orgFeeSettleDetailMapper.getPartnerFeeSettleDetailPageTotal(query);
   }

	@Override
	public List<OrgFeeSettleDetail> getCancelProjectList(OrgFeeSettleDTO query)
			throws TException {
		return orgFeeSettleDetailMapper.getCancelProjectList(query);
	}

	@Override
	public List<OrgFeeSettleDetail> getLoanProjectList(int settleId)
			throws TException {
		return orgFeeSettleDetailMapper.getLoanProjectList(settleId);
	}
	/**
	 * 机构端费用结算明细列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月5日下午4:25:17
    */
   @Override
   public List<OrgFeeSettleDetailPage> queryOrgFeeSettleDetailPage(OrgFeeSettleDetailPage query) throws TException {
      return orgFeeSettleDetailMapper.queryOrgFeeSettleDetailPage(query);
   }

   /**
    * 
    *@author:liangyanjun
    *@time:2016年9月5日下午4:25:17
    */
   @Override
   public int getOrgFeeSettleDetailPageTotal(OrgFeeSettleDetailPage query) throws TException {
      return orgFeeSettleDetailMapper.getOrgFeeSettleDetailPageTotal(query);
   }
}
