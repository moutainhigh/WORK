package com.xlkfinance.bms.server.aom.partner.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.partner.OrgPartnerDTO;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService.Iface;
import com.qfang.xk.aom.rpc.system.OrgUserFunAccount;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.MD5Util;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.server.aom.partner.mapper.OrgPartnerInfoMapper;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserFunAccountMapper;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserInfoMapper;

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
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgPartnerInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.partner.OrgPartnerInfoService")
public class OrgPartnerInfoServiceImpl implements Iface {
   private Logger logger = LoggerFactory.getLogger(OrgPartnerInfoServiceImpl.class);
   
   @Resource(name = "orgPartnerInfoMapper")
   private OrgPartnerInfoMapper orgPartnerInfoMapper;

   @Resource(name = "orgUserInfoMapper")
   private OrgUserInfoMapper orgUserInfoMapper;
   
   @Resource(name = "orgUserFunAccountMapper")
   private OrgUserFunAccountMapper orgUserFunAccountMapper;
   /**
    *根据条件查询所有
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   @Override
   public List<OrgPartnerInfo> getAll(OrgPartnerInfo orgPartnerInfo) throws ThriftServiceException, TException {
      return orgPartnerInfoMapper.getAll(orgPartnerInfo);
   }

   /**
    *根据id查询
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   @Override
   public OrgPartnerInfo getById(int pid) throws ThriftServiceException, TException {
      OrgPartnerInfo orgPartnerInfo = orgPartnerInfoMapper.getById(pid);
      if (orgPartnerInfo==null) {
         return new OrgPartnerInfo();
      }
      //用户账户信息
      int userId = orgPartnerInfo.getUserId();
      OrgUserInfo userInfo= orgUserInfoMapper.getById(userId);
      orgPartnerInfo.setOrgUserInfo(userInfo);
      return orgPartnerInfo;
   }

   /**
    *插入一条数据
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   @Override
   public int insert(OrgPartnerInfo orgPartnerInfo) throws ThriftServiceException, TException {
      return orgPartnerInfoMapper.insert(orgPartnerInfo);
   }

   /**
    *根据id更新数据
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   @Override
   public int update(OrgPartnerInfo orgPartnerInfo) throws ThriftServiceException, TException {
      return orgPartnerInfoMapper.update(orgPartnerInfo);
   }

   /**
    *根据id删除数据
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
      return 0;
   }

   /**
    *根据id集合删除
    *@author:liangyanjun
    *@time:2016-07-15 09:23:56
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return 0;
   }

   /**
    * 分页查询合伙人信息
    */
	@Override
	public List<OrgPartnerDTO> getOrgPartnerByPage(OrgPartnerDTO orgPartner)
			throws ThriftServiceException, TException {
		List<OrgPartnerDTO> result = new ArrayList<OrgPartnerDTO>();
		try {
			result = orgPartnerInfoMapper.getOrgPartnerByPage(orgPartner);
		} catch (Exception e) {
			logger.error("分页查询申请信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		
		return result;
	}
	
	/**
	 * 查询合伙人总数
	 */
	@Override
	public int getOrgPartnerInfoCount(OrgPartnerDTO orgPartner)
			throws ThriftServiceException, TException {
		return orgPartnerInfoMapper.getOrgPartnerInfoCount(orgPartner);
	}
	
	/**
	 * 修改认证状态
	 */
	@Override
	public int updateAuditStatus(OrgPartnerInfo orgPartner)
			throws ThriftServiceException, TException {
		return orgPartnerInfoMapper.updateAuditStatus(orgPartner);
	}
	
	/**
	 * 修改合作状态
	 */
	@Override
	public int updateCooperateStatus(OrgPartnerInfo orgPartner)
			throws ThriftServiceException, TException {
		
		return orgPartnerInfoMapper.updateCooperateStatus(orgPartner);
	}

	/**
	 * 保存合伙人信息
	 */
	@Override
	public String saveOrUpdateOrgPartner(OrgPartnerInfo orgPartner)
			throws ThriftServiceException, TException {
		String result = "";
		try {
			int pid = orgPartner.getPid();
			int userId = orgPartner.getUserId();
			OrgUserInfo userInfo = orgPartner.getOrgUserInfo();
			if(pid >0){
				
				orgPartnerInfoMapper.update(orgPartner);
				userInfo.setUpdateId(orgPartner.getUpdateId());
				orgUserInfoMapper.update(userInfo);
			}else{
				//用户账户入库
				userInfo.setUserType(AomConstant.USER_TYPE_PARTNER);
				userInfo.setDateScope(AomConstant.DATE_SCOPE_2);
				userInfo.setRole(AomConstant.ORG_ROLE_2);
				userInfo.setCreateId(orgPartner.getCreatorId());
				String password = userInfo.getPassword();
				password = MD5Util.tltMd5(password);//MD5加密
				userInfo.setPassword(password);
				
				orgUserInfoMapper.insert(userInfo);
				//合伙人信息入库
				userId = userInfo.getPid();
				orgPartner.setUserId(userId);
				orgPartnerInfoMapper.insert(orgPartner);
				pid = orgPartner.getPid();
				
				//用户资金帐户信息入库
				OrgUserFunAccount funAccount = new OrgUserFunAccount();
				funAccount.setUserId(userId);
				funAccount.setStatus(AomConstant.STATUS_ENABLED);
				orgUserFunAccountMapper.insert(funAccount);
			}
			result = pid+","+userId;
		} catch (Exception e) {
			logger.error("保存合伙人信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("保存合伙人信息失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 修改合伙人合作申请信息
	 */
	@Override
	public int updateReviewStatus(OrgPartnerInfo orgPartner)
			throws ThriftServiceException, TException {
		int pid = orgPartner.getPid();
		try {
			OrgPartnerInfo oldPartnerInfo = orgPartnerInfoMapper.getById(pid);
			//当申请时间不存在时，设置申请时间为当前时间
			if(StringUtil.isBlank(oldPartnerInfo.getApplyTime())){
				orgPartner.setApplyTime(DateUtils.getCurrentDateTime());
			}
			int reviewStatus = orgPartner.getReviewStatus();
			//当状态为审核通过时，修改合伙人合作状态为确认合作
			if(reviewStatus == AomConstant.ORG_PARTNER_COOPERATE_APPLY_STATUS_20){
				orgPartner.setCooperationStatus(AomConstant.ORG_COOPERATE_STATUS_4);
			}
			
			orgPartnerInfoMapper.update(orgPartner);
			
		} catch (Exception e) {
			logger.error("修改合伙人合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("修改合伙人信息失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return pid;
	}

	/**
	 * 根据用户id查询
	 *@author:liangyanjun
	 *@time:2016年7月27日下午3:00:02
	 */
   @Override
   public OrgPartnerInfo getByUserId(int userId) throws ThriftServiceException, TException {
      OrgPartnerInfo partnerInfo = orgPartnerInfoMapper.getByUserId(userId);
      if (partnerInfo==null) {
         return new OrgPartnerInfo();
      }
      //用户账户信息
      OrgUserInfo userInfo= orgUserInfoMapper.getById(userId);
      partnerInfo.setOrgUserInfo(userInfo);
      return partnerInfo;
   }

}
