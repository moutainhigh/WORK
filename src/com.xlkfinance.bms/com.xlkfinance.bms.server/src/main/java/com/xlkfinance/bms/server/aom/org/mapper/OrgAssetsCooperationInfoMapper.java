package com.xlkfinance.bms.server.aom.org.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;
import com.qfang.xk.aom.rpc.org.PartnerOrgIndex;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-05 10:28:55 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构管理平台-资产合作信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgAssetsCooperationInfoMapper")
public interface OrgAssetsCooperationInfoMapper<T, PK> extends BaseMapper<T, PK>{
    /**
     * 根据条件获取机构集合
    *@author:liangyanjun
    *@time:2016-07-05 10:28:55
    */
   public List<OrgAssetsCooperationInfo> getAll(OrgAssetsCooperationInfo orgAssetsCooperationInfo);

   /**
    * 根据id机构
    *@author:liangyanjun
    *@time:2016-07-05 10:28:55
    */
   public OrgAssetsCooperationInfo getById(int pid);

   /**
    * 添加机构
    *@author:liangyanjun
    *@time:2016-07-05 10:28:55
    */
   public int insert(OrgAssetsCooperationInfo orgAssetsCooperationInfo);

   /**
    * 根据id更新机构
    *@author:liangyanjun
    *@time:2016-07-05 10:28:55
    */
   public int update(OrgAssetsCooperationInfo orgAssetsCooperationInfo);
   
   /**
    * ERP端分页查询机构信息
     * @param orgAssets
     * @return
     * @author: baogang
     * @date: 2016年7月5日 下午3:39:49
    */
   public List<OrgAssetsCooperationInfo> getOrgAssetsByPage(OrgAssetsCooperationInfo orgAssets);
   
   /**
    * 查询机构总数
     * @param orgAssets
     * @return
     * @author: baogang
     * @date: 2016年7月5日 下午3:42:16
    */
   public int getOrgAssetsCount(OrgAssetsCooperationInfo orgAssets);
   /**
    * 修改认证状态
     * @param pid
     * @param authStatus
     * @return
     * @author: baogang
     * @date: 2016年7月5日 下午4:57:17
    */
   public int updateAuthStatus(OrgAssetsCooperationInfo orgAssets);

   /**
    * 修改合作状态
     * @param pid
     * @param cooperateStatus
     * @return
     * @author: baogang
     * @date: 2016年7月5日 下午4:58:19
    */
   public int updateCooperateStatus(OrgAssetsCooperationInfo orgAssets);

   /**
    *@author:liangyanjun
    *@time:2016年7月8日下午3:37:52
    *@param code
    *@return
    */
   public OrgAssetsCooperationInfo getOrgByCode(String code);

   /**
    * 合伙人的客户（机构）列表（分页查询）
    *@author:liangyanjun
    *@time:2016年7月28日上午11:30:03
    *@param partnerOrgIndex
    *@return
    */
   public List<PartnerOrgIndex> findPartnerOrgIndex(PartnerOrgIndex partnerOrgIndex);

   /**
    * 合伙人的客户（机构）列表总数
    *@author:liangyanjun
    *@time:2016年7月28日上午11:30:07
    *@param partnerOrgIndex
    *@return
    */
   public int getPartnerOrgIndexTotal(PartnerOrgIndex partnerOrgIndex);

   /**
    * 根据id删除机构
    *@author:liangyanjun
    *@time:2016年7月28日下午5:07:47
    *@param orgId
    */
   
   public void deleteById(int orgId);
   /**
    * 根据id删除合作城市
    *@author:jony
    *@time:2016年7月28日下午5:07:47
    *@param pid
 * @return 
    */
   
   public int delOrgCooperateCityInfoById(int pid);
   
   
   /**
    * 
    *@Jony
    *@time:2016年7月28日下午5:07:47
    *@param orgId
    */
   
   public int updateOrgCooperateCityInfo(OrgCooperateCityInfo orgCooperateCityInfo);
   
   /**
    * 
    *@Jony
    *@time:2016年7月28日下午5:07:47
    *@param orgId
    */
   
   public List<OrgCooperateCityInfo> getOrgCityInfoListByOrgId(@Param(value = "orgId")int orgId);
   
}
