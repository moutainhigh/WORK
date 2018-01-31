package com.xlkfinance.bms.server.aom.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.qfang.xk.aom.rpc.partner.OrgPartnerDTO;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-15 09:23:56 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构合伙人<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@MapperScan("orgPartnerInfoMapper")
public interface OrgPartnerInfoMapper<T, PK> extends BaseMapper<T, PK>{
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   public List<OrgPartnerInfo> getAll(OrgPartnerInfo orgPartnerInfo);

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   public OrgPartnerInfo getById(int pid);

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   public int insert(OrgPartnerInfo orgPartnerInfo);

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   public int update(OrgPartnerInfo orgPartnerInfo);
   
   /**
    * 分页查询合伙人信息
     * @param orgPartner
     * @return
     * @author: baogang
     * @date: 2016年7月15日 上午10:13:30
    */
   public List<OrgPartnerDTO> getOrgPartnerByPage(OrgPartnerDTO orgPartner);
   
   /**
    * 查询合伙人总数
     * @param orgPartner
     * @return
     * @author: baogang
     * @date: 2016年7月15日 上午10:13:33
    */
   public int getOrgPartnerInfoCount(OrgPartnerDTO orgPartner);
   
   /**
    * 修改认证状态
     * @param orgPartner
     * @return
     * @author: baogang
     * @date: 2016年7月15日 上午10:13:39
    */
   public int updateAuditStatus(OrgPartnerInfo orgPartner);
   
   /**
    * 修改合作状态
     * @param orgPartner
     * @return
     * @author: baogang
     * @date: 2016年7月15日 上午10:13:48
    */
   public int updateCooperateStatus(OrgPartnerInfo orgPartner);

   /**
    * 根据用户id查询
    *@author:liangyanjun
    *@time:2016年7月27日下午3:00:38
    *@param userId
    *@return
    */
   public OrgPartnerInfo getByUserId(int userId);
}
