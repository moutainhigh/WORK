package com.xlkfinance.bms.server.aom.finance.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.finance.OrgMakeLoansIndex;
import com.qfang.xk.aom.rpc.finance.OrgOrderSummary;
import com.qfang.xk.aom.rpc.finance.OrgRepaymentIndex;
import com.qfang.xk.aom.rpc.finance.PartnerOrderSummary;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年8月1日下午3:51:57 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 财务相关Mapper<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("financeMapper")
public interface OrgFinanceMapper<T, PK> extends BaseMapper<T, PK> {

   /**
    * 根据机构id查询机构订单数据汇总（用于机构首页的数据展现）
    *@author:liangyanjun
    *@time:2016年8月1日下午3:39:49
    *@param orgId
    *@return
    */
   public OrgOrderSummary getOrgOrderSummaryByOrgId(int orgId);

   /**
    * 根据合伙人的用户id查询合伙人订单数据汇总（用于合伙人首页的数据展现）
    *@author:liangyanjun
    *@time:2016年8月1日下午5:24:50
    *@param partnerId
    *@return
    */
   public PartnerOrderSummary getPartnerOrderSummary(int userId);

   /**
    * 机构放款列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月10日下午3:25:07
    *@param query
    *@return
    */
   public List<OrgMakeLoansIndex> queryOrgMakeLoansIndex(OrgMakeLoansIndex query);

   /**
    *@author:liangyanjun
    *@time:2016年8月10日下午3:25:11
    *@param query
    *@return
    */
   public int getOrgMakeLoansIndexTotal(OrgMakeLoansIndex query);

   /**
    * 机构回款列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月11日上午9:47:43
    *@param query
    *@return
    */
   public List<OrgRepaymentIndex> queryOrgRepaymentIndex(OrgRepaymentIndex query);

   /**
    * 机构回款列表查询总数
    *@author:liangyanjun
    *@time:2016年8月11日上午9:47:47
    *@param query
    *@return
    */
   public int getOrgRepaymentIndexTotal(OrgRepaymentIndex query);

}
