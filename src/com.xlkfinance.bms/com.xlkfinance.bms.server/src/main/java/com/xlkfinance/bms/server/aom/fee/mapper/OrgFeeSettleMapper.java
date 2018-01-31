package com.xlkfinance.bms.server.aom.fee.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettle;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDTO;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettlePage;
import com.qfang.xk.aom.rpc.fee.PartnerFeeSettlePage;


/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： baogang <br>
 * ★☆ @time：2016-08-12 15:15:31 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 费用结算表<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgFeeSettleMapper")
public interface OrgFeeSettleMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   public List<OrgFeeSettle> getAll(OrgFeeSettle orgFeeSettle);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   public OrgFeeSettle getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   public int insert(OrgFeeSettle orgFeeSettle);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-08-12 15:15:31
    */
   public int update(OrgFeeSettle orgFeeSettle);
   
   /**
    * 查询一段时间内机构的提单放款金额总和
     * @param orgFeeSettle
     * @return
     * @author: baogang
     * @date: 2016年8月15日 下午3:57:13
    */
   public List<OrgFeeSettle> getLoanTotalMoney(OrgFeeSettleDTO orgFeeSettle);
   
   /**
    * 费用信息批量入库
     * @param list
     * @return
     * @author: baogang
     * @date: 2016年8月15日 下午6:44:51
    */
   public int insertOrgFeeSettle(List<OrgFeeSettle> list);
   
   /**
    * 查询一段时间内合伙人所属机构放款金额
     * @param orgFeeSettle
     * @return
     * @author: baogang
     * @date: 2016年8月15日 下午7:08:42
    */
   public OrgFeeSettle getRecMoneyByPartner(OrgFeeSettleDTO orgFeeSettle);
   /**
    *@author:liangyanjun
    *@time:2016年8月15日下午3:12:19
    *@param query
    *@return
    */
   public List<OrgFeeSettlePage> queryOrgFeeSettlePage(OrgFeeSettlePage query);

   /**
    *@author:liangyanjun
    *@time:2016年8月15日下午3:12:25
    *@param query
    *@return
    */
   public int getOrgFeeSettlePageTotal(OrgFeeSettlePage query);

   /**
    * 合伙人息费列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月16日下午3:09:27
    *@param query
    *@return
    */
   public List<PartnerFeeSettlePage> queryPartnerFeeSettlePage(PartnerFeeSettlePage query);

   /**
    *@author:liangyanjun
    *@time:2016年8月16日下午3:09:31
    *@param query
    *@return
    */
   public int getPartnerFeeSettlePageTotal(PartnerFeeSettlePage query);

   /**
    * ERP后台费用结算信息分页查询
     * @param query
     * @return
     * @author: baogang
     * @date: 2016年8月17日 上午11:16:27
    */
   public List<OrgFeeSettleDTO> queryOrgFeeSettleByPage(OrgFeeSettleDTO query);
   
   /**
    * ERP后台费用结算总数
     * @param query
     * @return
     * @author: baogang
     * @date: 2016年8月17日 上午11:16:52
    */
   public int getOrgFeeSettleCount(OrgFeeSettleDTO query);
   
}
