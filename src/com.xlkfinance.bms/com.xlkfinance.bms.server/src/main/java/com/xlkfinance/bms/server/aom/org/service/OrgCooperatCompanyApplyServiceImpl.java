/**
 * @Title: OrgCooperatCompanyApplyService.java
 * @Package com.xlkfinance.bms.server.aom.org.service
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: baogang
 * @date: 2016年7月8日 下午3:58:03
 * @version V1.0
 */
package com.xlkfinance.bms.server.aom.org.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.achievo.framework.annotation.ThriftService;
import com.qfang.xk.aom.rpc.org.OrgAssetsCooperationInfo;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApply;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyInf;
import com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService.Iface;
import com.qfang.xk.aom.rpc.org.OrgCooperateCityInfo;
import com.qfang.xk.aom.rpc.org.OrgCooperationContract;
import com.qfang.xk.aom.rpc.project.BizProject;
import com.xlkfinance.bms.common.constant.AomConstant;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.ExceptionUtil;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.server.aom.org.mapper.OrgAssetsCooperationInfoMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperatCompanyApplyInfMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperateCityInfoMapper;
import com.xlkfinance.bms.server.aom.org.mapper.OrgCooperationContractMapper;
import com.xlkfinance.bms.server.aom.project.mapper.BizProjectMapper;
import com.xlkfinance.bms.server.beforeloan.mapper.ProjectGuaranteeMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;
/**
 * 机构合作申请信息Service
  * @ClassName: OrgCooperatCompanyApplyServiceImpl
  * @author: baogang
  * @date: 2016年7月8日 下午4:19:55
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("orgCooperatCompanyApplyServiceImpl")
@ThriftService(service = "com.qfang.xk.aom.rpc.org.OrgCooperatCompanyApplyService")
public class OrgCooperatCompanyApplyServiceImpl implements Iface{
	private Logger logger = LoggerFactory.getLogger(OrgCooperatCompanyApplyServiceImpl.class);
	
	@Resource(name = "orgCooperatCompanyApplyInfMapper")
	private OrgCooperatCompanyApplyInfMapper orgCooperatMapper;
	
	/**
	 * 机构信息Mapper
	 */
	@Resource(name = "orgAssetsCooperationInfoMapper")
	private OrgAssetsCooperationInfoMapper orgAssetsMapper;
	
	@Resource(name = "orgCooperateCityInfoMapper")
	private OrgCooperateCityInfoMapper orgCooperateCityInfoMapper;
	
	@Resource(name = "orgCooperationContractMapper")
	private OrgCooperationContractMapper orgCooperationContractMapper;
	
   	@Resource(name = "bizProjectMapper")
   	private BizProjectMapper bizProjectMapper;
	
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;
   	
	@Resource(name="projectGuaranteeMapper")
	private ProjectGuaranteeMapper projectGuaranteeMapper;
	
	@Override
	public List<OrgCooperatCompanyApplyInf> getAll(
			OrgCooperatCompanyApplyInf orgCooperate)
			throws ThriftServiceException, TException {
		return orgCooperatMapper.getAll(orgCooperate);
	}

	@Override
	public OrgCooperatCompanyApplyInf getById(int pid)
			throws ThriftServiceException, TException {
		return orgCooperatMapper.getById(pid);
	}

	@Override
	public int insert(OrgCooperatCompanyApplyInf orgCooperate)
			throws ThriftServiceException, TException {
		orgCooperatMapper.insert(orgCooperate);
		return orgCooperate.getPid();
	}

	@Override
	public int update(OrgCooperatCompanyApplyInf orgCooperate)
			throws ThriftServiceException, TException {
		return orgCooperatMapper.update(orgCooperate);
	}

	@Override
	public int deleteById(int pid) throws ThriftServiceException, TException {
		return 0;
	}


	/**
	 * 分页查询申请信息
	 */
	@Override
	public List<OrgCooperatCompanyApply> getOrgCooperateByPage(
			OrgCooperatCompanyApply orgCooperate)
			throws ThriftServiceException, TException {
		List<OrgCooperatCompanyApply> result = new ArrayList<OrgCooperatCompanyApply>();
		try {
			result = orgCooperatMapper.getOrgCooperateByPage(orgCooperate);
		} catch (Exception e) {
			logger.error("分页查询申请信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		
		return result;
	}

	/**
	 * 查询总数
	 */
	@Override
	public int getOrgCooperateCount(OrgCooperatCompanyApply orgCooperate)
			throws ThriftServiceException, TException {
		return orgCooperatMapper.getOrgCooperateCount(orgCooperate);
	}

	/**
	 * 根据合作申请id，查询合作申请详细信息，包括附加信息
	 */
	@Override
	public OrgCooperatCompanyApplyInf getByPid(int pid)
			throws ThriftServiceException, TException {
		OrgCooperatCompanyApplyInf result = new OrgCooperatCompanyApplyInf();
		try {
			if(pid >0){
				result = orgCooperatMapper.getById(pid);
				//机构信息
				int orgId = result.getOrgId();
				OrgAssetsCooperationInfo orgAssets = orgAssetsMapper.getById(orgId);
				result.setOrgAssetsInfo(orgAssets);
				//合作城市信息
				OrgCooperateCityInfo cityInfo = new OrgCooperateCityInfo();
				cityInfo.setOrgId(orgId);
				List<OrgCooperateCityInfo> cityList = orgCooperateCityInfoMapper.getAll(cityInfo);
				String orgCooperationCity="";
				if(cityList != null && cityList.size()>0){
					for(OrgCooperateCityInfo city : cityList){
						orgCooperationCity +=city.getAreaCode()+",";
					}
					orgCooperationCity = orgCooperationCity.substring(0, orgCooperationCity.length()-1);
				}
				result.setCooperationCitys(orgCooperationCity);
				result.setCooperateCityList(cityList);

			}
		} catch (Exception e) {
			logger.error("根据合作申请id，查询合作申请详细信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}

	/**
	 * 修改合作申请状态
	 */
	@Override
	public int updateApplyStatus(OrgCooperatCompanyApplyInf orgCooperatCompanyApplyInf)
			throws ThriftServiceException, TException {
		
		return orgCooperatMapper.updateApplyStatus(orgCooperatCompanyApplyInf);
	}

	/**
	 * 保存合作城市信息
	 */
	@Override
	public int saveOrgCooperateCitys(String cityCodes, int orgId)
			throws ThriftServiceException, TException {
		int result = 0;
		try {
			if(orgId >0){
				//删除关联的合作城市信息
				orgCooperateCityInfoMapper.deleteByOrgId(orgId);
				if(!StringUtils.isBlank(cityCodes)){
					String[] citys = cityCodes.split(",");
					List<String> condition = new ArrayList<String>();
					for(int i =0;i<citys.length;i++){
						condition.add(citys[i].trim());
					}
					//根据城市编号查询城市信息
					List<OrgCooperateCityInfo> orgCitys = orgCooperateCityInfoMapper.getCitysByCodes(condition);
					if(orgCitys != null && orgCitys.size()>0){
						for(OrgCooperateCityInfo cityInfo :orgCitys){
							cityInfo.setOrgId(orgId);
							result += orgCooperateCityInfoMapper.insert(cityInfo);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error("保存合作城市信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("保存合作城市信息失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return result;
	}


	/**
	 * 根据用户id查询
	 *@author:liangyanjun
	 *@time:2016年7月13日下午3:59:09
	 */
   @Override
   public OrgCooperatCompanyApplyInf getByUserId(int userId) throws ThriftServiceException, TException {
      OrgCooperatCompanyApplyInf applyInf = orgCooperatMapper.getByUserId(userId);
      if (applyInf == null) {
         return new OrgCooperatCompanyApplyInf();
      }
      return applyInf;
   }

   /**
    * 保存合作申请信息
    */
	@Override
	public int saveOrgCooperateApplyInfo(OrgCooperatCompanyApplyInf orgCooperate)
			throws ThriftServiceException, TException {
		int pid = orgCooperate.getPid();
		try {
			if(pid>0){
				orgCooperatMapper.update(orgCooperate);
			}else{
				orgCooperatMapper.insert(orgCooperate);
				pid = orgCooperate.getPid();
				
				//查询数据字典中合作合同类型列表,根据配置的合同类型生成合作合同初始化数据
				List<SysLookupVal> list = sysLookupValMapper.getSysLookupValByLookType(AomConstant.ORG_COOPERATE_CONTRACT_TYPE);
				if(list != null && list.size()>0){
					for(SysLookupVal lookupType : list){
						OrgCooperationContract orgCooperationContract = new OrgCooperationContract();
						orgCooperationContract.setContractName(lookupType.getLookupDesc());
						orgCooperationContract.setCooperationId(pid);
						orgCooperationContract.setCreatorId(orgCooperate.getCreatorId());
						orgCooperationContract.setContractType(Integer.parseInt(lookupType.getLookupVal()));
						orgCooperationContractMapper.insert(orgCooperationContract );
					}
				}
				
/*				//生成附件合同信息
				OrgCooperationContract orgCooperationContract = new OrgCooperationContract();
				orgCooperationContract.setContractName("合作合同");
				orgCooperationContract.setCooperationId(pid);
				orgCooperationContract.setCreatorId(orgCooperate.getCreatorId());
				orgCooperationContract.setContractType(10);
				
				
				OrgCooperationContract orgContract = new OrgCooperationContract();
				orgContract.setContractName("授信合同");
				orgContract.setCooperationId(pid);
				orgContract.setCreatorId(orgCooperate.getCreatorId());
				orgContract.setContractType(20);
				orgCooperationContractMapper.insert(orgContract );*/
			}
		} catch (Exception e) {
			logger.error("保存合作申请信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("保存合作申请信息失败,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		return pid;
	}

	/**
	 * 提交合作申请
	 *@author:liangyanjun
	 *@time:2016年7月15日上午11:39:04
	 */
	@Transactional
   @Override
   public int subApplyCooperat(OrgCooperatCompanyApplyInf orgCooperate) throws ThriftServiceException, TException {
      orgCooperationContractMapper.insert(orgCooperate);
      saveOrgCooperateApplyInfo(orgCooperate);
      return 1;
   }
	
	/**
	 * 查询合作城市列表
	 */
	@Override
	public List<OrgCooperateCityInfo> getOrgCooperateCityInfo(
			OrgCooperateCityInfo query) throws TException {
		List<OrgCooperateCityInfo> result = orgCooperateCityInfoMapper.getAll(query);
		if(result == null){
			result = new ArrayList<OrgCooperateCityInfo>();
		}
		return result;
	}

	/**
	 * 根据机构ID查询合作申请信息
	 */
	@Override
	public OrgCooperatCompanyApplyInf getByOrgId(int orgId)
			throws ThriftServiceException, TException {
		OrgCooperatCompanyApplyInf query = new OrgCooperatCompanyApplyInf();
		query.setOrgId(orgId);
		List<OrgCooperatCompanyApplyInf> result = orgCooperatMapper.getAll(query);
		OrgCooperatCompanyApplyInf orgCompanyApplyInfo = new OrgCooperatCompanyApplyInf();
		if(result != null && result.size()>0){
			orgCompanyApplyInfo = result.get(0);
		}
		
		OrgAssetsCooperationInfo orgAssets = orgAssetsMapper.getById(orgId);
		orgCompanyApplyInfo.setOrgAssetsInfo(orgAssets);
		//合作城市信息
		OrgCooperateCityInfo cityInfo = new OrgCooperateCityInfo();
		cityInfo.setOrgId(orgId);
		List<OrgCooperateCityInfo> cityList = orgCooperateCityInfoMapper.getAll(cityInfo);
		String orgCooperationCity="";
		if(cityList != null && cityList.size()>0){
			for(OrgCooperateCityInfo city : cityList){
				orgCooperationCity +=city.getAreaCode()+",";
			}
			orgCooperationCity = orgCooperationCity.substring(0, orgCooperationCity.length()-1);
		}
		orgCompanyApplyInfo.setCooperationCitys(orgCooperationCity);
		orgCompanyApplyInfo.setCooperateCityList(cityList);
		
		return orgCompanyApplyInfo;
	}

	/**
	 * 修改可用额度
	 */
	@Override
	public int updateOrgCreditLimit(int projectId, double money)
			throws ThriftServiceException, TException {
		int result = 0;
		try {
			BizProject bizProject = bizProjectMapper.getById(projectId);
			ProjectGuarantee projectGuarantee = projectGuaranteeMapper.getGuaranteeByProjectId(projectId);
			int orgId = bizProject.getOrgId();
			OrgCooperatCompanyApplyInf companyApplyInfo = getByOrgId(orgId);
			double applyMoney  = projectGuarantee.getLoanMoney();//借款金额

			double availableLimit = companyApplyInfo.getAvailableLimit();//现有可用额度
			double activateCreditLimit = companyApplyInfo.getActivateCreditLimit();//启用额度
			double usedLimit = companyApplyInfo.getUsedLimit();
			logger.info("回款后修改可用额度，orgId:"+orgId+",项目ID："+bizProject.getPid()+",借款金额："+applyMoney+",启用授信额度："+activateCreditLimit+",可用授信额度："+availableLimit+",已用授信额度："+usedLimit);
			//可用额度=启用额度-已用额度
			availableLimit = activateCreditLimit - usedLimit;

			companyApplyInfo.setAvailableLimit(availableLimit);
			result = orgCooperatMapper.update(companyApplyInfo);
			//修改未成功，回滚
			if(result == 0){
				logger.error("回款后修改可用额度出错，orgId:"+orgId+",项目ID："+bizProject.getPid()+",借款金额："+applyMoney+",启用授信额度："+activateCreditLimit+",可用授信额度："+availableLimit+",已用授信额度："+usedLimit);
				// 抛出异常处理
				throw new ThriftServiceException(ExceptionCode.SYSUSER_UPDATE, "回款确认出错,请重新操作!");
			}
		} catch (Exception e) {
			logger.error("回款后修改可用额度错误:" + ExceptionUtil.getExceptionMessage(e));
			throw new RuntimeException("回款后修改可用额度错误,请重新操作!"+ExceptionUtil.getExceptionMessage(e));
		}
		
		return result;
	}

	/**
	 * 查询合作机构列表，用于贷前业务来源信息
	 */
	@Override
	public List<OrgCooperatCompanyApply> getOrgAssetsList() throws TException {
		List<OrgCooperatCompanyApply> result = new ArrayList<OrgCooperatCompanyApply>();
		try {
			result = orgCooperatMapper.getOrgAssetsList();
		} catch (Exception e) {
			logger.error("查询贷前业务来源信息:" + ExceptionUtil.getExceptionMessage(e));
			throw new TException(ExceptionUtil.getExceptionMessage(e));
		}
		
		return result;
	}
	
}
