package com.xlkfinance.bms.server.customer.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.achievo.framework.util.DateUtil;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.common.util.StringUtil;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.customer.CusAcct;
import com.xlkfinance.bms.rpc.customer.CusAcctBank;
import com.xlkfinance.bms.rpc.customer.CusAcctBankView;
import com.xlkfinance.bms.rpc.customer.CusAcctPotential;
import com.xlkfinance.bms.rpc.customer.CusAcctService.Iface;
import com.xlkfinance.bms.rpc.customer.CusBlacklistRefuse;
import com.xlkfinance.bms.rpc.customer.CusEstDTO;
import com.xlkfinance.bms.rpc.customer.CusEstFactorWeights;
import com.xlkfinance.bms.rpc.customer.CusEstInfo;
import com.xlkfinance.bms.rpc.customer.CusEstInfoDTO;
import com.xlkfinance.bms.rpc.customer.CusEstOption;
import com.xlkfinance.bms.rpc.customer.CusEstQuota;
import com.xlkfinance.bms.rpc.customer.CusEstTemplate;
import com.xlkfinance.bms.rpc.customer.CusEstValue;
import com.xlkfinance.bms.rpc.customer.CusPerBase;
import com.xlkfinance.bms.rpc.customer.CusPerBaseDTO;
import com.xlkfinance.bms.rpc.customer.CusPerson;
import com.xlkfinance.bms.rpc.system.SysConfig;
import com.xlkfinance.bms.rpc.system.SysLookupVal;
import com.xlkfinance.bms.server.customer.mapper.CusAcctBankMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctMapper;
import com.xlkfinance.bms.server.customer.mapper.CusAcctPotentialMapper;
import com.xlkfinance.bms.server.customer.mapper.CusBlacklistRefuseMapper;
import com.xlkfinance.bms.server.customer.mapper.CusEstFactorWeightsMapper;
import com.xlkfinance.bms.server.customer.mapper.CusEstInfoMapper;
import com.xlkfinance.bms.server.customer.mapper.CusEstOptionMapper;
import com.xlkfinance.bms.server.customer.mapper.CusEstQuotaMapper;
import com.xlkfinance.bms.server.customer.mapper.CusEstTemplateMapper;
import com.xlkfinance.bms.server.customer.mapper.CusEstValueMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPerBaseMapper;
import com.xlkfinance.bms.server.customer.mapper.CusPersonMapper;
import com.xlkfinance.bms.server.system.mapper.SysConfigMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;

/**
 * 类描述<br>
 * 客户账号实现类
 * 
 * @author zhanghg
 * @version v1.0
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
@Service("cusAcctServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.customer.CusAcctService")
public class CusAcctServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(CusAcctServiceImpl.class);

	@Resource(name = "cusPerBaseMapper")
	private CusPerBaseMapper cusPerBaseMapper;

	@Resource(name = "cusAcctBankMapper")
	private CusAcctBankMapper cusAcctBankMapper;

	@Resource(name = "cusPersonMapper")
	private CusPersonMapper cusPersonMapper;

	@Resource(name = "cusEstFactorWeightsMapper")
	private CusEstFactorWeightsMapper cusEstFactorWeightsMapper;

	@Resource(name = "cusEstQuotaMapper")
	private CusEstQuotaMapper cusEstQuotaMapper;

	@Resource(name = "cusEstOptionMapper")
	private CusEstOptionMapper cusEstOptionMapper;

	@Resource(name = "cusEstInfoMapper")
	private CusEstInfoMapper cusEstInfoMapper;

	@Resource(name = "cusEstTemplateMapper")
	private CusEstTemplateMapper cusEstTemplateMapper;

	@Resource(name = "cusEstValueMapper")
	private CusEstValueMapper cusEstValueMapper;

	@Resource(name = "cusBlacklistRefuseMapper")
	private CusBlacklistRefuseMapper cusBlacklistRefuseMapper;

	@Resource(name = "cusAcctPotentialMapper")
	private CusAcctPotentialMapper cusAcctPotentialMapper;

	@Resource(name = "cusAcctMapper")
	private CusAcctMapper cusAcctMapper;

	@Resource(name = "sysConfigMapper")
	private SysConfigMapper sysConfigMapper;

	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;
	/**
	 * 删除客户
	 */
	@Override
	public int deleteCusAcct(String pids) throws ThriftServiceException, TException {
		String[] arr = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		if(list != null && list.size()>0){
			return cusAcctMapper.deleteCusAcct(list);
		}else{
			return 0;
		}
	}
	/**
	 * 新增客户银行账户
	 */
	@Override
	public int addCusAcctBank(CusAcctBank cusAcctBank) throws ThriftServiceException, TException {
		return cusAcctBankMapper.insert(cusAcctBank);
	}
	/**
	 * 修改客户银行账户
	 */
	@Override
	public int updateCusAcctBank(CusAcctBank cusAcctBank) throws ThriftServiceException, TException {

		return cusAcctBankMapper.updateByPrimaryKey(cusAcctBank);
	}
	/**
	 * 删除客户银行账户
	 */
	@Override
	public int deleteCusAcctBank(String pid) throws ThriftServiceException, TException {
		List<String> list = new ArrayList<String>();
		String[] s = pid.split(",");
		for (String string : s) {
			list.add(string);
		}
		return cusAcctBankMapper.deleteCusAcctBank(list);
		// return 0;
	}

	@Override
	public List<GridViewDTO> getCusAcctBanks(CusAcctBank cusAcctBank) throws TException {
		List<GridViewDTO> list = new ArrayList<GridViewDTO>();
		try {
			list = cusAcctBankMapper.getCusAcctBanks(cusAcctBank);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(list.size()==0){
			return new ArrayList<GridViewDTO>(); 
		}
		return list;
	}
	/**
	 * 查询客户银行账户列表
	 */
	@Override
	public CusAcctBank getCusAcctBank(int pid) throws TException {
		// 根号ID查询所有列
		CusAcctBank cusAcctBank = cusAcctBankMapper.selectCusAcctBankById(pid);
		if(null==cusAcctBank){
			return new CusAcctBank();
		}
		return cusAcctBank;

	}
	/**
	 * 新增客户信息
	 */
	@Override
	public int addCusPerson(CusPerson cusPerson) throws ThriftServiceException, TException {
		cusPersonMapper.insert(cusPerson);
		return cusPerson.getPid();
	}
	/**
	 *  修改客户信息
	 */
	@Override
	public int updateCusPerson(CusPerson cusPerson) throws ThriftServiceException, TException {

		cusPersonMapper.updateByPrimaryKey(cusPerson);
		return cusPerson.getPid();
	}
	/**
	 * 更新客户信息-供资金机构使用
	 */
	@Override
	public int updateFromPartnerById(CusPerson cusPerson) throws ThriftServiceException, TException {
		cusPersonMapper.updateFromPartnerById(cusPerson);
		return cusPerson.getPid();
	}
	
	
	
	/**
	 *  查询客户列表信息
	 */
	@Override
	public List<GridViewDTO> getCusPersons(CusPerson cusPerson) throws TException {
		return cusPersonMapper.getCusPersonByAcctIdAndStatus(cusPerson);
	}
	/**
	 *  根据ID查询客户
	 */
	@Override
	public CusPerson getCusPerson(int pid) throws TException {
		CusPerson cusPerson = (CusPerson)cusPersonMapper.selectByPrimaryKey(pid);
		if(null==cusPerson){
			return new CusPerson();
		}
		return cusPerson;
	}
	/**
	 *  添加黑名单客户信息
	 */
	@Override
	public int addCusBlacklistRefuse(CusBlacklistRefuse cusBlacklistRefuse, String acctIds) throws ThriftServiceException, TException {

		String[] arr = acctIds.split(",");
		List<CusAcct> cusAccts = Lists.newArrayList();
		List<CusBlacklistRefuse> blackRefuses = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {

			CusAcct cusAcct = new CusAcct();
			cusAcct.setPid(Integer.parseInt(arr[i]));
			cusAcct.setCusStatus(Integer.parseInt(cusBlacklistRefuse.getListType()));
			cusAccts.add(cusAcct);

			CusBlacklistRefuse blackRefuse = new CusBlacklistRefuse();
			blackRefuse.setCusAcct(cusAcct);
			blackRefuse.setListType(cusBlacklistRefuse.getListType());
			blackRefuse.setDeadline(cusBlacklistRefuse.getDeadline());
			blackRefuse.setRefuseReason(cusBlacklistRefuse.getRefuseReason());
			blackRefuses.add(blackRefuse);

		}

		// 修改客户状态
		cusAcctMapper.updateBatchCusStatus(cusAccts);

		cusBlacklistRefuseMapper.insertBlackRefuses(blackRefuses);
		return 0;
	}
	/**
	 *  修改黑名单客户信息
	 */
	@Override
	public int updateCusBlacklistRefuse(CusBlacklistRefuse cusBlacklistRefuse, CusPerBase cusPerBase, String acctIds, String pids){
		try{
			String[] arr = pids.split(",");
			String[] arrAcct = acctIds.split(",");
			List<CusAcct> cusAccts = Lists.newArrayList();
			List<CusBlacklistRefuse> blackRefuses = Lists.newArrayList();
			List<CusPerBase> perBases = Lists.newArrayList();
			for (int i = 0; i < arr.length; i++) {
	
				CusAcct cusAcct = new CusAcct();
				cusAcct.setPid(Integer.parseInt(arrAcct[i]));
				cusAcct.setCusStatus(Integer.parseInt(cusBlacklistRefuse.getListType()));
				cusAccts.add(cusAcct);
	
				CusBlacklistRefuse blackRefuse = new CusBlacklistRefuse();
				blackRefuse.setPid(Integer.parseInt(arr[i]));
				blackRefuse.setListType(cusBlacklistRefuse.getListType());
				blackRefuse.setRevokeDttm(cusBlacklistRefuse.getRevokeDttm());
				blackRefuse.setRevokeReason(cusBlacklistRefuse.getRevokeReason());
				blackRefuses.add(blackRefuse);
	
				CusPerBase cusPer = new CusPerBase();
				cusPer.setCusAcct(cusAcct);
				cusPer.setCusLevel(cusPerBase.getCusLevel());
				perBases.add(cusPer);
	
			}
			// 修改客户状态
			cusAcctMapper.updateBackBatchCusStatus(cusAccts);
	
			// 修改客户级别
			cusPerBaseMapper.updateBatchCusLevel(perBases);
	
			cusBlacklistRefuseMapper.updateBlackRefuses(blackRefuses);
		}catch(Exception e){
			e.printStackTrace();
			Log.debug(e.getMessage()+"更新失败");
		}
		return 0;
	}
	/**
	 *  查询黑名单客户信息
	 */
	@Override
	public List<GridViewDTO> getCusBlacklists(CusPerBaseDTO cusPerBaseDTO) throws TException {
		List<GridViewDTO> listResult = cusBlacklistRefuseMapper.selectBlacklists(cusPerBaseDTO);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}
	
	@Override
	public List<GridViewDTO> getCusRefuses(CusPerBaseDTO cusPerBaseDTO) throws TException {
		List<GridViewDTO> listResult = cusBlacklistRefuseMapper.selectRefuses(cusPerBaseDTO);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}
	/**
	 * 添加潜在客户
	 */
	@Override
	public int addCusAcctPotential(CusAcctPotential cusAcctPotential) throws ThriftServiceException, TException {
		int flag = cusAcctPotentialMapper.insert(cusAcctPotential);
		int cusAcctPotenPid = cusAcctPotential.getPid();
		cusAcctPotential.setCreateUserId(cusAcctPotenPid);
		cusAcctPotentialMapper.insertsysUserId(cusAcctPotential);
		return flag;
	}
	/**
	 * 修改潜在客户
	 */
	@Override
	public int updateCusAcctPotential(CusAcctPotential cusAcctPotential) throws ThriftServiceException, TException {
		return cusAcctPotentialMapper.updateByPrimaryKey(cusAcctPotential);
	}
	/**
	 * 删除潜在客户
	 */
	@Override
	public int deleteCusAcctPotential(String pids) throws ThriftServiceException, TException {
		String[] arr = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return cusAcctPotentialMapper.deleteCusAcctPotential(list);
	}
	/**
	 * 查询潜在客户列表
	 */
	@Override
	public List<GridViewDTO> getCusAcctPotentials(CusAcctPotential cusAcctPotential) throws TException {
		List<GridViewDTO> listResult = cusAcctPotentialMapper.getCusAcctPotentials(cusAcctPotential);
		if(listResult.size()==0){
			return new ArrayList<GridViewDTO>();
		}
		return listResult;
	}
	/**
	 * 根据ID查潜在客户
	 */
	@Override
	public CusAcctPotential getCusAcctPotential(int pid) throws TException {
		CusAcctPotential cusAcctPotential = cusAcctPotentialMapper.selectByIdPotential(pid);
		if(null==cusAcctPotential){
			return new CusAcctPotential();
		}
		return cusAcctPotential;
	}
	/**
	 * 删除客户信息
	 */
	@Override
	public int deleteCusPerson(String pids) throws ThriftServiceException, TException {
		String[] arr = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		return cusPersonMapper.deleteCusPerson(list);
	}
	/**
	 * 修改客户银行账户
	 */
	@Override
	public int updateCusAcctBanks(CusAcctBank cusAcctBank) throws ThriftServiceException, TException {
		return cusAcctBankMapper.updateCusAcctBankById(cusAcctBank);

	}
	/**
	 *添加资信评估模板
	 */
	@Override
	public int addCusEstTemplate(CusEstTemplate cusEstTemplate) throws ThriftServiceException, TException {

		try {
			// 新增资信评估模板信息
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			cusEstTemplate.setCreTime(sdf.format(new Date()));
			cusEstTemplateMapper.insert(cusEstTemplate);

			List<CusEstFactorWeights> listFactors = Lists.newArrayList();
			for (CusEstFactorWeights factor : cusEstTemplate.getFactors()) {
				if (factor != null && factor.getFactorName() > 0) {
					factor.setCusEstTemplate(cusEstTemplate);
					listFactors.add(factor);
				}
			}
			// 新增 要素权重信息
			if (listFactors.size() > 0) {
				cusEstFactorWeightsMapper.insertEstFactors(listFactors);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_TEMPLATE_ADD, "新增资信评估模板信息失败！");
		}

		return cusEstTemplate.getPid();
	}
	/**
	 *修改资信评估模板
	 */
	@Override
	public int updateCusEstTemplate(CusEstTemplate cusEstTemplate) throws ThriftServiceException, TException {

		try {
			// 修改资信评估模板信息
			cusEstTemplateMapper.updateByPrimaryKey(cusEstTemplate);

			List<CusEstFactorWeights> listFactors = Lists.newArrayList();
			List<CusEstFactorWeights> listFactors2 = Lists.newArrayList();
			for (CusEstFactorWeights factor : cusEstTemplate.getFactors()) {
				if (factor.getPid() > 0) {
					listFactors2.add(factor);
				} else {
					if (factor != null && factor.getFactorName() > 0) {
						factor.setCusEstTemplate(cusEstTemplate);
						listFactors.add(factor);
					}
				}

			}
			// 修改要素权重
			if (listFactors2.size() > 0) {
				// 删除除listFactors2之外的要素
				cusEstFactorWeightsMapper.deleteEstFactors(listFactors2);

				cusEstFactorWeightsMapper.updateEstFactors(listFactors2);
			}
			// 新增要素权重
			if (listFactors.size() > 0) {
				cusEstFactorWeightsMapper.insertEstFactors(listFactors);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_TEMPLATE_UPDATE, "修改资信评估模板信息失败！");
		}
		return cusEstTemplate.getPid();
	}
	/**
	 *删除资信评估模板
	 */
	@Override
	public int deleteCusEstTemplate(String pids) throws ThriftServiceException, TException {
		try {
			String[] arr = pids.split(",");
			List list = Lists.newArrayList();
			for (int i = 0; i < arr.length; i++) {
				list.add(arr[i]);
			}
			return cusEstTemplateMapper.deleteEstTemplates(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_TEMPLATE_DELETE, "删除资信评估模板信息失败！");
		}
	}
	/**
	 *查询资信评估模板列表
	 */
	@Override
	public List<GridViewDTO> getCusEstTemplates(CusEstTemplate cusEstTemplate) throws ThriftServiceException, TException {
		List<GridViewDTO> listResult = cusEstTemplateMapper.getCusEstTemplates(cusEstTemplate);
		try {
			if(null==listResult){
				return new ArrayList<GridViewDTO>();
			}
			return listResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_TEMPLATE_QUERY, "查询资信评估模板列表失败！");
		}
	}
	/**
	 *根据ID查资信评估模板
	 */
	@Override
	public CusEstTemplate getCusEstTemplate(int pid) throws ThriftServiceException, TException {
		try {
			CusEstTemplate estTemp = (CusEstTemplate) cusEstTemplateMapper.selectByPrimaryKey(pid);
			if(null==estTemp){
				return new CusEstTemplate();
			}
			estTemp.setFactors(cusEstFactorWeightsMapper.getFactorWeights(estTemp.getPid()));
			return estTemp;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_TEMPLATE_QUERY, "查询资信评估模板详细信息失败！");
		}
	}
	/**
	 * 获取已制作的模板
	 */
	@Override
	public CusEstTemplate getMakeCusEstTemplate(int pid) throws ThriftServiceException, TException {
		CusEstTemplate cusEstTemplate = cusEstTemplateMapper.getMakeEstTemplate(pid);
		try {
			if(null==cusEstTemplate){
				return new CusEstTemplate();
			}
			return cusEstTemplate;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_TEMPLATE_QUERY, "查询资信评估生成模板详细信息失败！");
		}
	}
	/**
	 * 新增资信评估指标信息
	 */
	@Override
	public int addCusEstQuota(CusEstFactorWeights cusEstFactorWeights) throws ThriftServiceException, TException {
		try {
			List<CusEstQuota> listQuotas = Lists.newArrayList();
			for (CusEstQuota quota : cusEstFactorWeights.getQuotas()) {
				if (quota != null && quota.getQuotaName() != null && !"".equals(quota.getQuotaName())) {
					listQuotas.add(quota);
				}
			}
			// 新增资信评估指标信息
			if (listQuotas.size() > 0) {
				cusEstQuotaMapper.insertEstQuotas(listQuotas);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_QUOTA_ADD, "新增资信评估指标失败！");
		}
		return 0;
	}
	/**
	 * 修改资信评估指标信息
	 */
	@Override
	public int updateCusEstQuota(CusEstFactorWeights cusEstFactorWeights) throws ThriftServiceException, TException {
		try {
			List<CusEstQuota> listQuotas = Lists.newArrayList();
			List<CusEstQuota> listQuotas2 = Lists.newArrayList();
			for (CusEstQuota quota : cusEstFactorWeights.getQuotas()) {
				if (quota.getPid() > 0) {
					listQuotas2.add(quota);
				} else {
					if (quota.getQuotaName() != null && !"".equals(quota.getQuotaName())) {
						listQuotas.add(quota);
					}
				}

			}

			// 修改资信评估指标信息
			if (listQuotas2.size() > 0) {
				// 删除除listQuotas之外的指标
				cusEstQuotaMapper.deleteEstQuotas(listQuotas2);

				cusEstQuotaMapper.updateEstQuotas(listQuotas2);
			}
			// 新增资信评估指标信息
			if (listQuotas.size() > 0) {
				cusEstQuotaMapper.insertEstQuotas(listQuotas);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_QUOTA_UPDATE, "修改资信评估指标失败！");
		}
		return 0;
	}
	/**
	 * 获取资信评估指标信息列表
	 */
	@Override
	public CusEstDTO getCusEstQuotas(int factorId) throws ThriftServiceException, TException {
		try {
			CusEstDTO cusEstDTO = new CusEstDTO();

			// 根据要素Id查询指标信息
			List<CusEstQuota> quotas = cusEstQuotaMapper.getEstQuotas(factorId);
			cusEstDTO.setQuotas(quotas);
			// 查询要素权重信息
			Object factor = cusEstFactorWeightsMapper.selectByPrimaryKey(factorId);
			cusEstDTO.setCusEstFactorWeights(factor == null ? null : (CusEstFactorWeights) factor);
			cusEstDTO.setCusEstTemplate(cusEstTemplateMapper.selectModelName(cusEstDTO.getCusEstFactorWeights().getCusEstTemplate().getPid()));
			return cusEstDTO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_QUOTA_QUERY, "查询资信评估指标失败！");
		}
	}
	/**
	 * 新增资信评估指标选项
	 */
	@Override
	public int addCusEstOption(CusEstQuota cusEstQuota) throws ThriftServiceException, TException {
		try {
			List<CusEstOption> cusEstOptions = Lists.newArrayList();
			for (CusEstOption cusEstOption : cusEstQuota.getOptions()) {
				if (cusEstOption != null && cusEstOption.getOptionName() != null && !"".equals(cusEstOption.getOptionName())) {
					cusEstOptions.add(cusEstOption);
				}
			}
			// 新增资信评估选项信息
			if (cusEstOptions.size() > 0) {
				cusEstOptionMapper.insertCusEstOptions(cusEstOptions);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_OPTION_ADD, "新增资信评估指标选项失败！");
		}
		return 0;
	}
	/**
	 * 修改资信评估指标选项
	 */
	@Override
	public int updateCusEstOption(CusEstQuota cusEstQuota) throws ThriftServiceException, TException {

		try {
			List<CusEstOption> cusEstOptions = Lists.newArrayList();
			List<CusEstOption> cusEstOptions2 = Lists.newArrayList();
			for (CusEstOption cusEstOption : cusEstQuota.getOptions()) {
				if (cusEstOption.getPid() > 0) {
					cusEstOptions2.add(cusEstOption);
				} else {
					if (cusEstOption.getOptionName() != null && !"".equals(cusEstOption.getOptionName())) {
						cusEstOptions.add(cusEstOption);
					}
				}

			}

			// 修改资信评估选项信息
			if (cusEstOptions2.size() > 0) {
				// 删除除cusEstOptions之外的选项
				cusEstOptionMapper.deleteEstOptions(cusEstOptions2);

				cusEstOptionMapper.updateCusEstOptions(cusEstOptions2);
			}
			// 新增资信评估选项信息
			if (cusEstOptions.size() > 0) {
				cusEstOptionMapper.insertCusEstOptions(cusEstOptions);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_OPTION_UPDATE, "修改资信评估指标选项失败！");
		}
		return 0;
	}

	/**
	 * @Description: 根据指标ID查询指标选项信息
	 * @param quotaId 
	 * @return CusEstDTO
	 * @author: Cai.Qing
	 * @date: 2015年1月16日 上午11:04:57
	 */
	@Override
	public CusEstDTO getCusEstOptions(int quotaId) throws ThriftServiceException, TException {
		try {

			CusEstDTO cusEstDTO = new CusEstDTO();
			// 根据指标ID查询指标信息
			Object obj = cusEstQuotaMapper.selectByPrimaryKey(quotaId);
			if (obj != null) {
				cusEstDTO.setCusEstQuota((CusEstQuota) obj);
				// 根据指标ID查询要素权重信息
				Object factor = cusEstFactorWeightsMapper.selectByPrimaryKey(cusEstDTO.getCusEstQuota().getCusEstFactorWeights().getPid());
				cusEstDTO.setCusEstFactorWeights(factor == null ? null : (CusEstFactorWeights) factor);
				cusEstDTO.setCusEstTemplate(cusEstTemplateMapper.selectModelName(cusEstDTO.getCusEstFactorWeights().getCusEstTemplate().getPid()));
			}
			// 根据指标ID查询指标选项信息
			cusEstDTO.setOptions(cusEstOptionMapper.getCusEstOptions(quotaId));
			return cusEstDTO;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_OPTION_QUERY, "查询资信评估指标选项失败！");
		}
	}

	@Override
	public List<CusEstTemplate> selectAllEstTemplateName(int modelType) throws ThriftServiceException, TException {
		try {
			// 获取所有模板
			CusEstTemplate cet = new CusEstTemplate();
			cet.setModelType(modelType);
			List<CusEstTemplate> listResult = cusEstTemplateMapper.selectAllEstTemplateName(cet);
			if(null==listResult){
				return new ArrayList<CusEstTemplate>();
			}
			return listResult;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_TEMPLATE_QUERY, "查询资信评估所有模板名称失败！");
		}
	}
	/**
	 * 查询资信评估指标信息
	 */
	/**
	 * @Description: 根据名称查询
	 * @param configName 系统配置名称数组
	 * @return 系统配置集合
	 * @author: Cai.Qing
	 * @date: 2015年1月16日 上午11:04:57
	 */
	@Override
	public List<GridViewDTO> getCusEstInfos(CusEstInfoDTO cusEstInfoDTO) throws ThriftServiceException, TException {
		try {
			List<SysLookupVal> crelvs = sysLookupValMapper.getSysLookupValByLookType("CREDIT_LEVEL");
			String[] creLvStr = getStr(crelvs);
			List<SysConfig> lvcfgs = creLvStr == null ? null : sysConfigMapper.getSysConfigByConfigName(creLvStr);
			List<SysLookupVal> creMeans = sysLookupValMapper.getSysLookupValByLookType("CREDIT_MEAN");
			String[] creMeanStr = getStr(creMeans);
			List<SysConfig> meancfgs = creMeanStr == null ? null : sysConfigMapper.getSysConfigByConfigName(creMeanStr);
			if (cusEstInfoDTO.getEstLv() != null && !"".equals(cusEstInfoDTO.getEstLv())) {
				List<SysConfig> cfgs = sysConfigMapper.getSysConfigByConfigName(new String[] { cusEstInfoDTO.getEstLv() });
				String cfgCal = "";
				if (cfgs != null && cfgs.size() > 0) {
					cfgCal = cfgs.get(0).getConfigVal();
				}

				int index = cfgCal.indexOf("-");
				String val1 = index == -1 ? null : cfgCal.substring(0, index);
				String val2 = index == -1 ? null : cfgCal.substring(index + 1);
				cusEstInfoDTO.setEstStartScore(val1 != null ? Integer.parseInt(val1) : 0);
				cusEstInfoDTO.setEstEndScore(val2 != null ? Integer.parseInt(val2) : 0);
			}
			List<GridViewDTO> list = cusEstInfoMapper.getcusEstInfos(cusEstInfoDTO);
			List<GridViewDTO> list2 = Lists.newArrayList();
			for (GridViewDTO gv : list) {
				String score = gv.getValue5();
				gv.setValue6(getEstLv(lvcfgs, score));
				gv.setValue7(getEstLv(meancfgs, score));
				list2.add(gv);
			}
			return list2;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_INFO_QUERY, "查询资信评估列表失败！");
		}
	}

	private String[] getStr(List<SysLookupVal> slvs) {
		if (slvs != null && slvs.size() > 0) {
			String[] lookuplv = new String[slvs.size()];
			int i = 0;
			for (SysLookupVal lk1 : slvs) {
				lookuplv[i] = lk1.getLookupVal();
				i++;
			}
			return lookuplv;
		}

		return null;
	}
   /**
    * 获取资信评级
     * @Description: TODO
     * @param cfgs
     * @param score
     * @return
     * @author: Heng.Wang
     * @date: 2015年8月3日 下午5:43:40
    */
	private String getEstLv(List<SysConfig> cfgs, String score) {
		if (score == null || "".equals(score)) {
			return "";
		}
		for (SysConfig cfg : cfgs) {
			String cfgCal = cfg.getConfigVal();
			int index = cfgCal.indexOf("-");
			if (index == -1) {
				continue;
			}
			String val1 = cfgCal.substring(0, index);
			String val2 = cfgCal.substring(index + 1);
			int score1 = val1 != null ? Integer.parseInt(val1) : 0;
			int score2 = val2 != null ? Integer.parseInt(val2) : 0;
			if (Double.parseDouble(score) >= score1 && Double.parseDouble(score) <= score2) {
				return cfg.getConfigName();
			}
		}
		return "";

	}
	/**
	 * 根据ID查资信评估信息
	 */
	@Override
	public CusEstInfo getCusEstInfo(int pid) throws ThriftServiceException, TException {
		try {
			CusEstInfo cusEstInfo = (CusEstInfo) cusEstInfoMapper.selectByPrimaryKey(pid);
			if(null==cusEstInfo){
				return new CusEstInfo();
			}
			return cusEstInfo;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_INFO_QUERY, "查询资信评估详细信息失败！");
		}
	}
	/**
	 * 添加评估信息
	 */
	@Override
	public int addCusEstInfo(CusEstInfo cusEstInfo) throws ThriftServiceException, TException {
		try {
			// //计算总分数
			cusEstInfo.setScore(cusEstValueMapper.getEstInfoSorce(cusEstInfo.getCusEstValues()));
			cusEstInfo.setEstDate(DateUtil.format(new Date(), "yyyy-MM-dd"));
			// 保存资信评估信息
			cusEstInfoMapper.insert(cusEstInfo);

			List<CusEstValue> values = Lists.newArrayList();
			for (CusEstValue cev : cusEstInfo.getCusEstValues()) {
				cev.setCusEstInfo(cusEstInfo);
				values.add(cev);
			}
			// 新增评估值信息
			cusEstValueMapper.insertCusEstValues(values);

		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_INFO_ADD, "新增资信评估信息失败！");
		}
		return 0;
	}
	/**
	 * 添加评估信息
	 */
	/**
	 * 修改评估值信息
	 * @param pid
	 * @return String
	 */
	@Override
	public int updateCusEstInfo(CusEstInfo cusEstInfo) throws ThriftServiceException, TException {
		try {
			// 计算总分数
			cusEstInfo.setScore(cusEstValueMapper.getEstInfoSorce(cusEstInfo.getCusEstValues()));
			// 修改资信评估信息
			cusEstInfoMapper.updateByPrimaryKey(cusEstInfo);
			// 修改评估值信息
			cusEstValueMapper.updateCusEstValues(cusEstInfo.getCusEstValues());

		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_INFO_UPDATE, "修改资信评估信息失败！");
		}
		return 0;
	}
	/**
	 * 根据ID查客户名称
	 */
	/**
	 * 根据客户ID获取客户名称
	 * @param pid
	 * @return String
	 */
	@Override
	public String getAcctNameById(int pid) throws ThriftServiceException, TException {
		try {
			String result = cusAcctMapper.getAcctNameById(pid);
			if("".equals(result) && null==result){
				return "";
			}
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUS_ACCT_QUERY, "修改客户名称失败！");
		}
	}
	/**
	 * 删除资信评估信息
	 */
	@Override
	public int deleteCusEstInfos(String pids) throws ThriftServiceException, TException {
		try {
			String[] arr = pids.split(",");
			List list = Lists.newArrayList();
			for (int i = 0; i < arr.length; i++) {
				list.add(arr[i]);
			}
			return cusEstInfoMapper.deleteEstInfos(list);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUSEST_INFO_DELETE, "删除资信评估信息失败！");
		}
	}
	/**
	 *查客户银行的总记录数
	 */
	@Override
	public int getTotal(CusAcctBank cusAcctBank) throws ThriftServiceException, TException {
		return cusAcctBankMapper.getTotal(cusAcctBank);
	}
	/**
	 *查资信评估的总记录数
	 */
	@Override
	public int getTotalEst(CusEstInfoDTO cusEstInfoDTO) throws ThriftServiceException, TException {
		return cusEstInfoMapper.getTotalEst(cusEstInfoDTO);
	}
	/**
	 *查客户的总记录数
	 */
	@Override
	public int getTotalCusPersons(CusPerson cusPerson) throws ThriftServiceException, TException {
		return cusPersonMapper.getTotalCusPersons(cusPerson);
	}

	@Override
	public int getTotalPotential(CusAcctPotential cusAcctPotential) throws ThriftServiceException, TException {
		return cusAcctPotentialMapper.getTotalPotential(cusAcctPotential);
	}
	/**
	 *根据客户ID查客户银行账户
	 */
	/**
	  * @Description: 根据客户id和账户用途查询银行账户信息
	  * @param cusAcctBank
	  * @return
	  * @author: yql
	  * @date: 2015年3月30日 下午3:37:16
	 */
	@Override
	public CusAcctBankView getCusBankByAcctId(CusAcctBank cusAcctBank) throws ThriftServiceException, TException {
		List<CusAcctBankView> list = new ArrayList<CusAcctBankView>();
		CusAcctBankView cusAcctBankView = null;
		try {
			list = cusAcctBankMapper.getCusBankByAcctId(cusAcctBank);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					cusAcctBankView = list.get(i);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cusAcctBankView != null ? cusAcctBankView : new CusAcctBankView();
	}

	/**
	 * 
	 * @Description: 查询所有有效的客户信息
	 * @param cusAcct 客户对象
	 * @return 客户集合
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月20日 下午3:08:07
	 */
	@Override
	public List<CusAcct> getAllAcct(CusAcct cusAcct) throws ThriftServiceException, TException {
		List<CusAcct> list = new ArrayList<CusAcct>();
		try {
			list = cusAcctMapper.getAllAcct(cusAcct);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询有效客户信息:" + e.getMessage());
			}
		}
		if(list.size()==0){
			return new ArrayList<CusAcct>();
		}
		return list;
	}

	/**
	 * 
	 * @Description: 查询所有有效的客户信息 的总数
	 * @param cusAcct
	 *            客户对象
	 * @return 所有有效的客户的总数
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月28日 上午3:03:30
	 */
	@Override
	public int getAllAcctCount(CusAcct cusAcct) throws TException {
		int count = 0;
		try {
			count = cusAcctMapper.getAllAcctCount(cusAcct);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询所有有效的客户信息 的总数:" + e.getMessage());
			}
		}
		return count;
	}
	@Override
	public String saveSpouseList(CusAcct cusAcct) throws ThriftServiceException,
			TException {
		String str = "";
		try {
			List<CusPerson> list = cusAcct.getCustomerList();
			if(list != null && list.size()>0){
				for(CusPerson cusPerson : list){
					if(cusPerson != null && cusPerson.getStatus()>0 && !StringUtil.isBlank(cusPerson.getCertNumber())){
						cusPerson.setCusAcct(cusAcct);
						cusPerson.setStatus(1);
						if(cusPerson.getPid() >0){
							int relationType = cusPerson.getRelationType();
							if(relationType ==2 && !checkPersonFamily(cusPerson)){//配偶,并且已存在配偶信息，进行校验
								throw new RuntimeException("配偶信息已存在，无法保存!");
							}
							cusPersonMapper.updateByPrimaryKey(cusPerson);
						}else{
							cusPersonMapper.insert(cusPerson);
						}
						
						str += cusPerson.getPid()+",";
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CUS_ACCT_ADD, "保存关系人信息失败！");
		}
		return str;
	}
	
	/**
	 * 判断此用户配偶信息，如已存在，则返回FALSE
	 * @param cusPerson
	 * @return
	 */
	private boolean checkPersonFamily(CusPerson cusPerson){
		CusPerson query = new CusPerson();
		query.setCusAcct(cusPerson.getCusAcct());
		query.setRelationType(2);
		int total = cusPersonMapper.getTotalCusPersons(query );
		if(total >1){
			return false;
		}else if(total ==1){
			List<CusPerson> list = cusPersonMapper.getCusPersonByMarr(query);
			CusPerson result = list.get(0);
			if(result.getPid() != cusPerson.getPid()){
				return false;
			}
		}
		return true;
	}
	
	/**
	 *@author:liangyanjun
	 *@time:2016年9月11日下午7:10:49
	 */
   @Override
   public CusAcct getAcctById(int id) throws ThriftServiceException, TException {
      CusAcct acct = cusAcctMapper.getAcctById(id);
      if (acct==null) {
         return new CusAcct();
      }
      return acct;
   }
   	
   	/**
   	 * 查询黑名单总数
   	 */
	@Override
	public int getBlackListCount(CusPerBaseDTO cusPerBaseDTO) throws TException {
		
		return cusBlacklistRefuseMapper.getBlackListCount(cusPerBaseDTO);
	}
	/**
	 * 根据证件号码判断是否加入黑名单
	 */
	@Override
	public int getBlackByCertNum(String certNumber) throws TException {
		int result = 0;
		if(!StringUtil.isBlank(certNumber)){
			CusPerBaseDTO cusPerBaseDTO = new CusPerBaseDTO();
			cusPerBaseDTO.setCertNumber(certNumber);
			CusAcct cusAcct = new CusAcct();
			cusPerBaseDTO.setCusAcct(cusAcct);
			result = cusBlacklistRefuseMapper.getBlackByCertNum(cusPerBaseDTO);
		}
		return result;
	}

}
