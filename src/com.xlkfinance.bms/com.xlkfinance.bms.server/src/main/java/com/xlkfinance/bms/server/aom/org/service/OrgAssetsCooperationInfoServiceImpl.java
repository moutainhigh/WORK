package com.xlkfinance.bms.server.aom.org.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService.Iface;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;
import com.qfang.xk.aom.rpc.org.PartnerOrgIndex;
import com.qfang.xk.aom.rpc.partner.OrgPartnerInfo;
import com.qfang.xk.aom.rpc.system.OrgUserFunAccount;
import com.qfang.xk.aom.rpc.system.OrgUserInfo;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.DateUtils;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.common.util.MD5Util;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusComBase;
import com.xlkfinance.bms.server.aom.org.mapper.OrgAssetsCooperationInfoMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperateCityInfoMapper;
import com.xlkfinance.bms.server.aom.partner.mapper.OrgPartnerInfoMapper;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserFunAccountMapper;
import com.xlkfinance.bms.server.aom.system.mapper.OrgUserInfoMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.customer.mapper.CusComBaseMapper;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016-07-05 10:54:56 <br>
 * ★☆ @version：  1.0<br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 机构管理平台-资产合作信息<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgAssetsCooperationInfoServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.org.OrgAssetsCooperationService")
public class OrgAssetsCooperationInfoServiceImpl implements Iface {
    private Logger logger = LoggerFactory.getLogger(OrgAssetsCooperationInfoServiceImpl.class);

    @Resource(name = "orgAssetsCooperationInfoMapper")
    private OrgAssetsCooperationInfoMapper orgAssetsCooperationInfoMapper;

	@Resource(name = "cusComBaseMapper")
	private CusComBaseMapper cusComBaseMapper;
	
    @Resource(name = "orgUserInfoMapper")
    private OrgUserInfoMapper orgUserInfoMapper;
    
    @Resource(name = "orgUserFunAccountMapper")
    private OrgUserFunAccountMapper orgUserFunAccountMapper;
    
    @Resource(name = "orgPartnerInfoMapper")
    private OrgPartnerInfoMapper orgPartnerInfoMapper;
    
    @Resource(name = "cusAcctMapper")
    private CusAcctMapper cusAcctMapper;
    
	@Resource(name = "orgCooperateCityInfoMapper")
	private OrgCooperateCityInfoMapper orgCooperateCityInfoMapper;
   /**
    * 根据条件获取机构集合
    *@author:liangyanjun
    *@time:2016-07-05 10:54:56
    */
   @Override
   public List<OrgAssetsCooperationInfo> getAll(OrgAssetsCooperationInfo orgAssetsCooperationInfo) throws ThriftServiceException, TException {
      return orgAssetsCooperationInfoMapper.getAll(orgAssetsCooperationInfo);
   }

   /**
    * 根据id获取机构信息
    *@author:liangyanjun
    *@time:2016-07-05 10:54:56
    */
   @Override
   public OrgAssetsCooperationInfo getById(int pid) throws ThriftServiceException, TException {
      OrgAssetsCooperationInfo cooperationInfo = orgAssetsCooperationInfoMapper.getById(pid);
      if (cooperationInfo==null) {
         return new OrgAssetsCooperationInfo();
      }
      cooperationInfo.setOrgUserInfo(orgUserInfoMapper.getById(pid));
      return cooperationInfo;
   }

   /**新增机构信息
	*@author:liangyanjun
	*@time:2016-07-05 10:54:56
	*/
   	@Transactional
	@Override
	public int insert(OrgAssetsCooperationInfo orgAssetsCooperationInfo) throws ThriftServiceException, TException {
		int pid = 0;
		try {
			OrgUserInfo userInfo = orgAssetsCooperationInfo.getOrgUserInfo();
			
			String password = userInfo.getPassword();
			password = MD5Util.tltMd5(password);//MD5加密
			userInfo.setPassword(password);

			userInfo.setCreateId(orgAssetsCooperationInfo.getCreatorId());
			userInfo.setUserType(AomConstant.USER_TYPE_ORG);//用户类型,1表示机构,2表示合伙人,3表示员工(机构下的员工)
			/*userInfo.setPhone(orgAssetsCooperationInfo.getPhone());*/
			userInfo.setEmail(orgAssetsCooperationInfo.getEmail());
			userInfo.setRole(AomConstant.ORG_ROLE_1);//角色 ，1机构2合伙人
			userInfo.setDateScope(AomConstant.DATE_SCOPE_2);//数据权限,1表示私有,2表示所有
			userInfo.setRealName(orgAssetsCooperationInfo.getOrgName());//机构入库时，真实姓名为机构名称
			//用户表入库
			orgUserInfoMapper.insert(userInfo);
			
			pid = userInfo.getPid();
			//更新orgId
			userInfo.setOrgId(pid);
			orgUserInfoMapper.update(userInfo);
			if(pid >0){
				orgAssetsCooperationInfo.setPid(pid);
				//机构信息入库
				orgAssetsCooperationInfoMapper.insert(orgAssetsCooperationInfo);
				CusAcct cusAcct=new CusAcct();
				cusAcct.setCusType(3);
				cusAcct.setStatus(1);
				cusAcctMapper.insert(cusAcct);
				//企业表插入数据
				CusComBase comBase = new CusComBase();
				comBase.setOrgId(pid);
				comBase.setCpyName(orgAssetsCooperationInfo.getOrgName());
				comBase.setOrgCode(orgAssetsCooperationInfo.getCode());
				comBase.setBusLicCert(orgAssetsCooperationInfo.getCode());
				comBase.setStatus(1);
				comBase.setCusAcct(cusAcct);
				cusComBaseMapper.insert(comBase);
				//用户资金帐户信息入库
				OrgUserFunAccount funAccount = new OrgUserFunAccount();
				funAccount.setUserId(pid);
				funAccount.setStatus(AomConstant.STATUS_ENABLED);
				orgUserFunAccountMapper.insert(funAccount);
				
			}else{
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "新增失败,请重新操作!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("新增机构信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("新增失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		   
		return pid;
	}

   /**
    *@author:liangyanjun
    *@time:2016-07-05 10:54:56
    */
   @Override
   public int update(OrgAssetsCooperationInfo orgAssetsCooperationInfo) throws ThriftServiceException, TException {
      return orgAssetsCooperationInfoMapper.update(orgAssetsCooperationInfo);
   }

   /**
    *@author:liangyanjun
    *@time:2016-07-05 10:54:56
    */
   @Override
   public int deleteById(int pid) throws ThriftServiceException, TException {
	   return orgAssetsCooperationInfoMapper.delOrgCooperateCityInfoById(pid);
   }

   /**
    *@author:liangyanjun
    *@time:2016-07-05 10:54:56
    */
   @Override
   public int deleteByIds(List<Integer> pids) throws ThriftServiceException, TException {
      return 0;
   }

   /**
    * 分页查询机构信息
    */
	@Override
	public List<OrgAssetsCooperationInfo> getOrgAssetsByPage(
			OrgAssetsCooperationInfo orgAssets) throws ThriftServiceException,
			TException {
		List<OrgAssetsCooperationInfo> orgAssetsByPage = new ArrayList<OrgAssetsCooperationInfo>();
		try {
			orgAssetsByPage = orgAssetsCooperationInfoMapper.getOrgAssetsByPage(orgAssets);
		} catch (Exception e) {
			logger.error("分页查询机构信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return orgAssetsByPage;
	}
	
	/**
	 * 查询机构总数
	 */
	@Override
	public int getOrgAssetsCooperationCount(OrgAssetsCooperationInfo orgAssets)
			throws ThriftServiceException, TException {
		int count = 0;
		try {
			count = orgAssetsCooperationInfoMapper.getOrgAssetsCount(orgAssets);
		} catch (Exception e) {
			logger.error("查询机构总数:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return count;
	}
	
	/**
	 * 修改认证状态
	 */
	@Override
	public int updateAuditStatus(OrgAssetsCooperationInfo orgAssets)
			throws ThriftServiceException, TException {
		
		return orgAssetsCooperationInfoMapper.updateAuthStatus(orgAssets);
	}
	
	/**
	 * 修改合作状态
	 */
	@Override
	public int updateCooperateStatus(OrgAssetsCooperationInfo orgAssets)
			throws ThriftServiceException, TException {
		return orgAssetsCooperationInfoMapper.updateCooperateStatus(orgAssets);
	}
	
	/**
	 * 查询机构合作城市列表
	 */
	@Override
	public List<OrgCooperateCityInfo> getAllOrgCooperateCitys(int orgId)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 修改机构信息
	 */
	@Transactional
	@Override
	public int updateOrgAssetsInfo(OrgAssetsCooperationInfo orgAssets)
			throws ThriftServiceException, TException {
		int pid = orgAssets.getPid();
		try {
			String orgName = orgAssets.getOrgName();
			String orgCode = orgAssets.getCode();
			String email = orgAssets.getEmail();
			int status = orgAssets.getOrgUserInfo().getStatus();
			int orgId = orgAssets.getPid();
			int updateId = orgAssets.getUpdateId();
			//修改机构信息
			orgAssetsCooperationInfoMapper.update(orgAssets);
			//修改用户账户信息
			OrgUserInfo orgUser = orgUserInfoMapper.getById(orgId);
			orgUser.setUpdateDate(DateUtils.getCurrentDateTime());
			/*orgUser.setPhone(phone);*/
			orgUser.setUpdateId(updateId);
			orgUser.setEmail(email);
			orgUser.setStatus(status);
			orgUserInfoMapper.update(orgUser);
			//修改企业信息
			CusComBase comBase = cusComBaseMapper.getComBaseByOrgId(orgId);
			if(comBase == null){
				//企业表插入数据
				comBase = new CusComBase();
				comBase.setOrgId(pid);
				comBase.setCpyName(orgName);
				comBase.setOrgCode(orgCode);
				comBase.setBusLicCert(orgCode);
				comBase.setStatus(1);
				comBase.setCusAcct(new CusAcct());
				cusComBaseMapper.insert(comBase);
			}else{
				comBase.setOrgCode(orgCode);
				comBase.setBusLicCert(orgCode);
				comBase.setCpyName(orgName);
				cusComBaseMapper.updateByPrimaryKey(comBase);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("修改机构信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("修改失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return pid;
	}


	/**
	 * 根据机构代码查询
	 *@author:liangyanjun
	 *@time:2016年7月8日下午3:34:24
	 */
   @Override
   public OrgAssetsCooperationInfo getOrgByCode(String code) throws TException {
      OrgAssetsCooperationInfo org = orgAssetsCooperationInfoMapper.getOrgByCode(code);
      if (org==null) {
         return new OrgAssetsCooperationInfo();
      }
      return org;
   }

   /**
    * 检查机构代码是否已存在
    *@author:liangyanjun
    *@time:2016年7月8日下午3:54:10
    */
   @Override
   public boolean checkOrgCodeIsExist(String code) throws TException {
      return orgAssetsCooperationInfoMapper.getOrgByCode(code)!=null;
   }

   /**
    * 合伙人的客户（机构）列表（分页查询）
    *@author:liangyanjun
    *@time:2016年7月28日上午11:29:21
    */
   @Override
   public List<PartnerOrgIndex> findPartnerOrgIndex(PartnerOrgIndex partnerOrgIndex) throws TException {
      return orgAssetsCooperationInfoMapper.findPartnerOrgIndex(partnerOrgIndex);
   }

   /**
    * 合伙人的客户（机构）列表总数
    *@author:liangyanjun
    *@time:2016年7月28日上午11:29:21
    */
   @Override
   public int getPartnerOrgIndexTotal(PartnerOrgIndex partnerOrgIndex) throws TException {
      return orgAssetsCooperationInfoMapper.getPartnerOrgIndexTotal(partnerOrgIndex);
   }

 
   /**
    * 级联删除机构,未认证之前可删除，保存用户信息，机构信息，资金账户信息
    * 删除成功返回1，删除失败返回-1
    *@author:liangyanjun
    *@time:2016年7月28日下午5:06:37
    */
   @Override
   @Transactional
   public int cascadeDeleteOrg(int orgId,int loginUserId) throws TException {
      try {
         
         OrgAssetsCooperationInfo org = orgAssetsCooperationInfoMapper.getById(orgId);
         if (org == null) {
            logger.error("机构不存在：orgId=" + orgId);
            return -1;
         }
         OrgPartnerInfo orgPartnerInfo = orgPartnerInfoMapper.getByUserId(loginUserId);
         if (orgPartnerInfo.getPid()!=org.getPartnerId()) {
            logger.error("无权限删除机构：orgId=" + orgId+",loginUserId="+loginUserId);
            return -1;
         }
         if (org.getAuditStatus() == AomConstant.ORG_AUDIT_STATUS_3) {
            logger.error("已认证机构不能删除：orgId=" + orgId);
            return -1;
         }
         //删除机构信息
         orgAssetsCooperationInfoMapper.deleteById(orgId);
         //删除企业信息
         cusComBaseMapper.deleteByOrgId(orgId);
         //删除资金账户信息
         orgUserFunAccountMapper.deleteByUserId(orgId);
         //删除用户信息
         orgUserInfoMapper.deleteById(orgId);
      } catch (Exception e) {
         logger.error("机构删除失败：入参：orgId:", orgId +"。错误：" + ExceptionUtil.getExceptionMessage(e));
         return -1;
      }
      return 1;
   }
	   /**
	    * 根据orgId查询合作城市
	    *@author:jony
	    *@time:2016年7月28日下午5:07:47
	    *@param orgId
	    * @return 
	    */

	@Override
	public List<OrgCooperateCityInfo> getOrgCityInfoListByOrgId(@Param(value = "orgId")int orgId)
			throws ThriftServiceException, TException {
		  return orgAssetsCooperationInfoMapper.getOrgCityInfoListByOrgId(orgId);
	}
	/**
	 * 新增合作城市
	 *@author:jony
	 *@time:2016年7月28日下午5:07:47
	 *@param orgId
	 * @return 
	 */
	@Override
	public int insertOrgCooperateCityInfo(
			OrgCooperateCityInfo orgCooperateCityInfo)
			throws ThriftServiceException, TException {
		return orgCooperateCityInfoMapper.insert(orgCooperateCityInfo);
	}

	/**
	 * 修改合作城市
	 *@author:jony
	 *@time:2016年7月28日下午5:07:47
	 *@param orgId
	 * @return 
	 */
	@Override
	public int updateOrgCooperateCityInfo(
			OrgCooperateCityInfo orgCooperateCityInfo)
			throws ThriftServiceException, TException {
		return orgCooperateCityInfoMapper.update(orgCooperateCityInfo);
	}
}
