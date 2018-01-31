package com.xlkfinance.bms.server.aom.fee.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDTO;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetail;
import com.qfang.xk.aom.rpc.fee.OrgFeeSettleDetailPage;
import com.qfang.xk.aom.rpc.fee.PartnerFeeSettleDetailPage;

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
@MapperScan("orgFeeSettleDetailMapper")
public interface OrgFeeSettleDetailMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   public List<OrgFeeSettleDetail> getAll(OrgFeeSettleDetail orgFeeSettleDetail);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   public OrgFeeSettleDetail getById(int pid);
   
   /**
    * 根据项目id查询
    * getByProjectId
    *@author:liangyanjun
    *@time:2016年8月16日上午9:37:18
    *@param pid
    *@return
    */
   public OrgFeeSettleDetail getByProjectId(int projectId);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   public int insert(OrgFeeSettleDetail orgFeeSettleDetail);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-08-12 15:24:28
    */
   public int update(OrgFeeSettleDetail orgFeeSettleDetail);

   /**
    * 根据机构ID查询一段时间内的放款订单信息
     * @param orgFeeSettle
     * @return
     * @author: baogang
     * @date: 2016年8月16日 上午11:20:24
    */
   public List<OrgFeeSettleDetail> getProjectListByOrgId(OrgFeeSettleDTO orgFeeSettle);
   
   /**
    * 费用明细批量入库
     * @param list
     * @return
     * @author: baogang
     * @date: 2016年8月16日 下午2:38:17
    */
   public int insertOrgFeeSettleDetail(List<OrgFeeSettleDetail> list);
   
   /**
    * 查询一段时间内解保的项目列表
     * @param orgFeeSettle
     * @return
     * @author: baogang
     * @date: 2016年8月16日 下午5:11:15
    */
   public List<OrgFeeSettleDetail> getCancelProjectList(OrgFeeSettleDTO orgFeeSettle);
   /**
    * 合伙人息费明细列表(分页查询)
    *@author:liangyanjun
    *@time:2016年8月16日下午3:09:38
    *@param query
    *@return
    */
   public List<PartnerFeeSettleDetailPage> queryPartnerFeeSettleDetailPage(PartnerFeeSettleDetailPage query);

   /**
    *@author:liangyanjun
    *@time:2016年8月16日下午3:09:43
    *@param query
    *@return
    */
   public int getPartnerFeeSettleDetailPageTotal(PartnerFeeSettleDetailPage query);

   /**
    * 查询放款详情列表
     * @param settleId
     * @return
     * @author: baogang
     * @date: 2016年8月18日 上午10:08:51
    */
   public List<OrgFeeSettleDetail> getLoanProjectList(int settleId);

   /**
    * 机构端费用结算明细列表(分页查询)
    *@author:liangyanjun
    *@time:2016年9月5日下午4:28:43
    *@param query
    *@return
    */
   public List<OrgFeeSettleDetailPage> queryOrgFeeSettleDetailPage(OrgFeeSettleDetailPage query);

   /**
    *@author:liangyanjun
    *@time:2016年9月5日下午4:28:47
    *@param query
    *@return
    */
   public int getOrgFeeSettleDetailPageTotal(OrgFeeSettleDetailPage query);
   
}
