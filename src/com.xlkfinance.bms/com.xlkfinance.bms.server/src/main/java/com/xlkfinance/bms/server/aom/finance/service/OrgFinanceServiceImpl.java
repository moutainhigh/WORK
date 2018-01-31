package com.xlkfinance.bms.server.aom.finance.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.finance.OrgFinanceService.Iface;
import com.qfang.xk.aom.rpc.finance.OrgMakeLoansIndex;
import com.qfang.xk.aom.rpc.finance.OrgOrderSummary;
import com.qfang.xk.aom.rpc.finance.OrgRepaymentIndex;
import com.qfang.xk.aom.rpc.finance.PartnerOrderSummary;
import com.xlkfinance.bms.server.aom.finance.mapper.OrgFinanceMapper;

/**
 * 
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年8月1日下午3:51:31 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：财务相关service <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgFinanceServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.finance.OrgFinanceService")
public class OrgFinanceServiceImpl implements Iface {
   @Resource(name = "orgFinanceMapper")
   private OrgFinanceMapper orgFinanceMapper;

   /**
    * 根据机构id查询机构订单数据汇总（用于机构首页的数据展现）
    *@author:liangyanjun
    *@time:2016年8月1日下午3:39:15
    */
   @Override
   public OrgOrderSummary getOrgOrderSummaryByOrgId(int orgId) throws TException {
      OrgOrderSummary orderSummary = orgFinanceMapper.getOrgOrderSummaryByOrgId(orgId);
      if (orderSummary == null) {
         return new OrgOrderSummary();
      }
      return orderSummary;
   }

   /**
    * 根据合伙人的用户id查询合伙人订单数据汇总（用于合伙人首页的数据展现）
    *@author:liangyanjun
    *@time:2016年8月1日下午5:24:27
    */
   @Override
   public PartnerOrderSummary getPartnerOrderSummary(int userId) throws TException {
      PartnerOrderSummary partnerOrderSummary = orgFinanceMapper.getPartnerOrderSummary(userId);
      if (partnerOrderSummary==null) {
         return new PartnerOrderSummary();
      }
      return partnerOrderSummary;
   }

   /**
    * 机构放款列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月10日下午3:24:24
    */
   @Override
   public List<OrgMakeLoansIndex> queryOrgMakeLoansIndex(OrgMakeLoansIndex query) throws TException {
      return orgFinanceMapper.queryOrgMakeLoansIndex(query);
   }

   /**
    * 机构放款列表查询总数
    *@author:liangyanjun
    *@time:2016年8月10日下午3:24:24
    */
   @Override
   public int getOrgMakeLoansIndexTotal(OrgMakeLoansIndex query) throws TException {
      return orgFinanceMapper.getOrgMakeLoansIndexTotal(query);
   }

   /**
    * 机构回款列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月11日上午9:46:50
    */
   @Override
   public List<OrgRepaymentIndex> queryOrgRepaymentIndex(OrgRepaymentIndex query) throws TException {
      return orgFinanceMapper.queryOrgRepaymentIndex(query);
   }

   /**
    * 机构回款列表查询总数
    *@author:liangyanjun
    *@time:2016年8月11日上午9:46:50
    */
   @Override
   public int getOrgRepaymentIndexTotal(OrgRepaymentIndex query) throws TException {
      return orgFinanceMapper.getOrgRepaymentIndexTotal(query);
   }

}
