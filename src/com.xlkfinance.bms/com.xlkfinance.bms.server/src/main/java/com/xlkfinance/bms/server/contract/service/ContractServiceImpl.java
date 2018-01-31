/**
 * @Title: ContractServiceImpl.java
 * @Package com.xlkfinance.bms.server.contract.service
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2015年1月14日 下午7:22:32
 * @version V1.0
 */
package com.xlkfinance.bms.server.contract.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.cxf.common.util.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.achievo.framework.annotation.ThriftService;
import com.google.common.collect.Lists;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.Contract;
import com.xlkfinance.bms.rpc.contract.ContractAccessorie;
import com.xlkfinance.bms.rpc.contract.ContractAttachment;
import com.xlkfinance.bms.rpc.contract.ContractDynamicParameter;
import com.xlkfinance.bms.rpc.contract.ContractDynamicTableParameter;
import com.xlkfinance.bms.rpc.contract.ContractParameter;
import com.xlkfinance.bms.rpc.contract.ContractProject;
import com.xlkfinance.bms.rpc.contract.ContractService.Iface;
import com.xlkfinance.bms.rpc.contract.ContractTempLate;
import com.xlkfinance.bms.rpc.contract.TempLateParmDto;
import com.xlkfinance.bms.server.contract.mapper.ContractMapper;
import com.xlkfinance.bms.server.contract.mapper.ContractParameterMapper;
import com.xlkfinance.bms.server.contract.mapper.ContractTabParameterMapper;
import com.xlkfinance.bms.server.contract.mapper.ContractTempLateMapper;
import com.xlkfinance.bms.server.system.mapper.SysLookupValMapper;

@Service("contractServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.contract.ContractService")
public class ContractServiceImpl implements Iface {
	
	private Logger logger = LoggerFactory.getLogger(ContractServiceImpl.class);
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "contractMapper")
	private ContractMapper contractMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "contractParameterMapper")
	private ContractParameterMapper contractParameterMapper;
	
	@SuppressWarnings("rawtypes")
	@Resource(name = "contractTabParameterMapper")
	private ContractTabParameterMapper contractTabParameterMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "contractTempLateMapper")
	private ContractTempLateMapper contractTempLateMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "sysLookupValMapper")
	private SysLookupValMapper sysLookupValMapper;

	@Resource(name = "contractNumberBuilder")
	private ContractNumberBuilder contractNumberBuilder;

	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> pageContractList(Contract contract) throws TException {
		return contractMapper.pageContractList(contract);
	}

	@Override
	public int pageContractCount(Contract contract) throws TException {
		return contractMapper.pageContractCount(contract);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractProject> getProJectInfoById(Contract contract) throws TException {
		return (List<ContractProject>) contractMapper.getProJectInfoById(contract);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractAccessorie> pageFileAccessorieList(Contract contract) throws TException {
		return contractMapper.pageFileAccessorieList(contract);
	}

	@Override
	public boolean deleteAccessorie(List<Integer> list) throws TException {
		boolean status = false;
		for (Integer pid : list) {
			ContractAccessorie c = new ContractAccessorie();
			c.setPid(pid);
			status = contractMapper.deleteAccessorie(c);
		}
		return status;
	}

	@Override
	public int pageAccessorieCount(Contract contract) throws TException {
		return contractMapper.pageAccessorieCount(contract);
	}

	@Override
	public ContractTempLate getContractTempLateInfoById(int tempLateId) {
		logger.info("查询合同模板信息   ContractServiceImpl.getContractTempLateInfoById tempLateId["+tempLateId+"]");
		
		ContractTempLate contractTempLate = contractTempLateMapper.getContractTempLateInfoById(tempLateId);
		return contractTempLate != null ? contractTempLate : new ContractTempLate();
	}

	/**
	 * 查询贷款合同
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> getLoanContracts(Contract contract) throws TException {
		return contractMapper.getLoanContracts(contract);
	}

	/**
	 * 查询贷款合同总数
	 */
	@Override
	public int getLoanContractsCount(Contract contract) throws TException {
		return contractMapper.getLoanContractsCount(contract);
	}

	/**
	 * 
	 * @Description: 批量删除合同
	 * @param pids
	 * @return
	 * @throws TException
	 * @author: zhanghg
	 * @date: 2015年2月10日 下午3:37:39
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public int deleteContracts(String pids) throws TException {
		logger.info("批量删除合同信息   ContractServiceImpl.deleteContracts pids["+pids+"]");
		String[] arr = pids.split(",");
		List list = Lists.newArrayList();
		for (int i = 0; i < arr.length; i++) {
			list.add(arr[i]);
		}
		contractMapper.deleteContracts(list);
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertContractInfo(Contract contract, Map<Integer, String> parameterMap) throws ThriftServiceException, TException {
		logger.info("新增合同信息   ContractServiceImpl.insertContractInfo pid["+contract.getPid()+"] contractName["+contract.getContractName()+"] contractUrl["+contract.getContractUrl()+"]");
		try {
			// 插入主表：合同信息
			contractMapper.addContract(contract);

			List<ContractParameter> contractParameterList = Lists.newArrayList();
			for (Integer key : parameterMap.keySet()) {
				ContractParameter contractParameter = new ContractParameter();
				contractParameter.setContractId(contract.getPid());
				contractParameter.setParameterId(key);
				contractParameter.setParameterVal(parameterMap.get(key));
				contractParameter.setStatus(1);
				contractParameterList.add(contractParameter);
			}

			// 插入子表：保存合同参数信息
			if(contractParameterList !=null && contractParameterList.size()>0){
				contractParameterMapper.addContractParameters(contractParameterList);
			}
			return contract.getPid();
		} catch (Exception e) {
			logger.error("保存合同信息失败");
			throw new TException(e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateContractParameter(List<ContractParameter> contractParameterList,Contract contract) throws ThriftServiceException, TException {
		logger.info("新增合同信息   ContractServiceImpl.updateContractParameter contractId["+contract.getPid()+"] contractName["+contract.getContractName()+"]");
		//修改合同参数
		 contractParameterMapper.updateContractParameters(contractParameterList);
		 //修改合同名称
		 contractMapper.updateContractUrlOrName(contract);
		 return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TempLateParmDto> getTempParmListByTemplateId(int templateId) throws TException {
		logger.info("查询合同模板参数信息   ContractServiceImpl.getTempParmListByTemplateId templateId["+templateId+"]");
		
		List<TempLateParmDto> contractTemplateParamList = contractTempLateMapper.getTempLateParmListTid(templateId);
		return contractTemplateParamList == null ? new ArrayList<TempLateParmDto>() : contractTemplateParamList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TempLateParmDto> getAllTempParmValue(TempLateParmDto tempLateParmDto) throws TException {
		return contractTempLateMapper.getParmValueByCidAndPid(tempLateParmDto);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TempLateParmDto> getTempLateParmList(Map<String, String> paramMap) throws TException {
		logger.info("合同模板 调用存储过程 读取合同数据   ContractServiceImpl.getTempLateParmList tempLateId["+paramMap.get("tempLateId")+"] debtorProjectId["+paramMap.get("debtorProjectId")
				+"] creditProjectId["+paramMap.get("creditProjectId")+"] projectId["+paramMap.get("projectId")+"] mortgagorId["+paramMap.get("mortgagorId")
				+"] suretyId["+paramMap.get("suretyId")+"] exdProjectId["+paramMap.get("exdProjectId")+"] rateChgId["+paramMap.get("rateChgId")+"] commonDebtorPids["+paramMap.get("commonDebtorPids")+"]");
		
		
		// 获取该合同模板的参数列表
		List<TempLateParmDto> tempLateParameterList = contractTempLateMapper.getTempLateParmListNotTable(Integer.valueOf(paramMap.get("tempLateId")));

		ContractTempLate contractTempLate = contractTempLateMapper.getContractTempLateInfoById(Integer.valueOf(paramMap.get("tempLateId")));

		// 获取当前合同模板所要使用的自动数据提取算
		String procedureName = sysLookupValMapper.getSysLookupValByPid(contractTempLate.getTemplateParFun());
		if (!StringUtils.isEmpty(procedureName)) {
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			parameterMap.put("procedureName", "PKG_CONTRACT." + procedureName);
			parameterMap.put("parameterCursor", new ArrayList<ContractDynamicParameter>());// 传入一个jdbc游标，用于接收返回参数

			if ("PRO_DEBTOR_CONTRACT_DATA".equalsIgnoreCase(procedureName)) {
				// 借款类合同
				parameterMap.put("debtorProjectId", Integer.valueOf(paramMap.get("debtorProjectId")));// P_DEBTOR_PROJECT_ID,当前借款项目ID
				parameterMap.put("creditProjectId", Integer.valueOf(paramMap.get("creditProjectId")));// P_CREDIT_PROJECT_ID,当前借款项目对应的授信项目ID
				parameterMap.put("commonDebtorPids",paramMap.get("commonDebtorPids")); //共同贷款人ids
				// 调用动态存储过程获取非表格中的参数值；
				contractMapper.getDebtorContractDynamicParameter(parameterMap);
			} else if ("PRO_MORTGAGOR_CONTRACT_DATA".equalsIgnoreCase(procedureName)) {
				// 抵押、质押类合同
				parameterMap.put("projectId", Integer.valueOf(paramMap.get("projectId")));// P_PROJECT_ID,项目ID
				parameterMap.put("mortgagorId", Integer.valueOf(paramMap.get("mortgagorId")));// P_MORTGAGOR_ID,抵质押物ID

				// 调用动态存储过程获取非表格中的参数值；
				contractMapper.getMortgagorContractDynamicParameter(parameterMap);
			} else if ("PRO_SURETY_CONTRACT_DATA".equalsIgnoreCase(procedureName)) {
				// 保证类合同
				parameterMap.put("projectId", Integer.valueOf(paramMap.get("projectId")));// P_PROJECT_ID,项目ID
				parameterMap.put("suretyId", Integer.valueOf(paramMap.get("suretyId")));// P_SURETY_ID,保证ID

				// 调用动态存储过程获取非表格中的参数值；
				contractMapper.getSuretyContractDynamicParameter(parameterMap);
			} else if ("PRO_EXTENSION_CONTRACT_DATA".equalsIgnoreCase(procedureName)) {
				// 展期类合同
				parameterMap.put("projectId", Integer.valueOf(paramMap.get("projectId")));// P_PROJECT_ID,展期项目ID
				parameterMap.put("exdProjectId", Integer.valueOf(paramMap.get("exdProjectId")));// P_EXD_PROJECT_ID,被展期的项目ID
				parameterMap.put("suretyId", Integer.valueOf(paramMap.get("suretyId")!=null?paramMap.get("suretyId"):"0"));// P_SURETY_ID,保证ID
				parameterMap.put("commonDebtorPids",paramMap.get("commonDebtorPids"));//共同借款人

				// 调用动态存储过程获取非表格中的参数值；
				contractMapper.getExtensionContractDynamicParameter(parameterMap);
			} else if ("PRO_RATE_CONTRACT_DATA".equalsIgnoreCase(procedureName)) {
				
				parameterMap.put("projectId", Integer.valueOf(paramMap.get("projectId")));// P_PROJECT_ID,项目ID
				parameterMap.put("rateChgId", Integer.valueOf(paramMap.get("rateChgId")));// P_RATE_CHG_ID,利率变更ID
				parameterMap.put("commonDebtorPids",paramMap.get("commonDebtorPids"));//共同借款人
				
				// 调用动态存储过程获取非表格中的参数值；
				contractMapper.getRateChgContractDynamicParameter(parameterMap);
			}

			// 自动获取动态参数值
			List<ContractDynamicParameter> dynamicParameterValMapList = (ArrayList<ContractDynamicParameter>) parameterMap.get("parameterCursor");

			// 匹配自动获取的参数值与合同参数
			if (dynamicParameterValMapList != null && dynamicParameterValMapList.size() > 0) {
				for (TempLateParmDto dto : tempLateParameterList) {
					// 生成临时合同号
					if (dto.getMatchFlag().equalsIgnoreCase("@CONTRACT_NUMBER@")) {
						dto.setFixedVal(contractNumberBuilder.genTempContractNumber(Integer.valueOf(paramMap.get("tempLateId"))));
					}

					// 将动态值绑定到固定值输出
					String matchVal ="";
					if(dto.getOutputType()==3){
						matchVal =matchDynamicParameterVal(dto.getMatchFlag(), dynamicParameterValMapList);
					}
					if (!StringUtils.isEmpty(matchVal)) {
						dto.setFixedVal(matchVal);
					}
				}
			}
		}

		return tempLateParameterList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractDynamicTableParameter> getTempLateTableParmList(Map<String, String> paramMap) throws TException {
		logger.info("合同模板 调用存储过程 读取合同表格数据   ContractServiceImpl.getTempLateTableParmList projectId["+paramMap.get("projectId")+"] mortgagorType["+paramMap.get("mortgagorType")+"]");
		
		
		List<ContractDynamicTableParameter> dynamicTableParameterValMapList = null;
		// 调用动态存储过程获取表格中的参数值
		List<TempLateParmDto> tempLateTableParameterList = contractTempLateMapper.getTempLateParmListIsTable(Integer.valueOf(paramMap.get("tempLateId")));
		if (tempLateTableParameterList != null && tempLateTableParameterList.size() > 0) {
			Map<String, Object> parameterTableMap = new HashMap<String, Object>();
			parameterTableMap.put("procedureName", "PKG_CONTRACT.PRO_TABLE_CONTRACT_DATA");
			parameterTableMap.put("parameterCursor", new ArrayList<ContractDynamicTableParameter>());// 传入一个jdbc游标，用于接收返回参数
			parameterTableMap.put("projectId", Integer.valueOf(paramMap.get("projectId")));
			parameterTableMap.put("mortgagorType", Integer.valueOf(paramMap.get("mortgagorType")));

			// 调用动态存储过程获取非表格中的参数值；
			contractMapper.getContractDynamicTableParameter(parameterTableMap);

			dynamicTableParameterValMapList = (ArrayList<ContractDynamicTableParameter>) parameterTableMap.get("parameterCursor");

			for (ContractDynamicTableParameter tableParameter : dynamicTableParameterValMapList) {
				tableParameter.setIsGoodsName(hasMatchInParameter("@GOODS_NAME@", tempLateTableParameterList));
				tableParameter.setIsGoodsNumber(hasMatchInParameter("@GOODS_NUMBER@", tempLateTableParameterList));
				tableParameter.setIsGoodsCount(hasMatchInParameter("@GOODS_COUNT@", tempLateTableParameterList));
				tableParameter.setIsGoodsUnit(hasMatchInParameter("@GOODS_UNIT@", tempLateTableParameterList));
				tableParameter.setIsGoodsAddress(hasMatchInParameter("@GOODS_ADDRESS@", tempLateTableParameterList));
				tableParameter.setIsGoodsValue(hasMatchInParameter("@GOODS_VALUE@", tempLateTableParameterList));
				tableParameter.setIsGoodsEffective(hasMatchInParameter("@GOODS_EFFECTIVE@", tempLateTableParameterList));
			}
		}

		return dynamicTableParameterValMapList;
	}

	private boolean hasMatchInParameter(String matchFlg, List<TempLateParmDto> tempLateTableParameterList) {
		for (TempLateParmDto dto : tempLateTableParameterList) {
			if (dto.getMatchFlag().equalsIgnoreCase(matchFlg)) {
				return true;
			}
		}
		return false;
	}

	private String matchDynamicParameterVal(String matchFlg, List<ContractDynamicParameter> dynamicParameterValMapList) {
		for (ContractDynamicParameter dp : dynamicParameterValMapList) {
			if(dp!=null && dp.getParameterCode()!=null){
				if (dp.getParameterCode().equals(matchFlg)) {
					return dp.getParameterVal()==null?"":dp.getParameterVal();
				}
			}
			
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addContractTableParam(List<ContractDynamicTableParameter> contractTabs)
			throws ThriftServiceException, TException {
		
		try {
			for(int i=0;i<contractTabs.size();i++){
				contractTabs.get(i).setLineNumber(i+1);
			}
			contractTabParameterMapper.addContractTabParam(contractTabs);
		} catch (Exception e) {
			logger.error("批量新增合同表格参数信息失败！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_TAB_PARAMETER_ADD, "批量新增合同表格参数信息失败！");
		}
		
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateContractTableParam(List<ContractDynamicTableParameter> contractTabs)
			throws ThriftServiceException, TException {
		
		try {
			for(int i=0;i<contractTabs.size();i++){
				contractTabs.get(i).setLineNumber(i+1);
			}
			contractTabParameterMapper.updateContractTabParam(contractTabs);
		} catch (Exception e) {
			logger.error("批量修改合同表格参数信息失败！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_TAB_PARAMETER_UPDATE, "批量修改合同表格参数信息失败！");
		}
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContractDynamicTableParameter> getContractTabs(int contractId)
			throws ThriftServiceException, TException {
		List<ContractDynamicTableParameter> list=Lists.newArrayList();
		try {
			list=contractTabParameterMapper.getContractTabs(contractId);
		} catch (Exception e) {
			logger.error("查询合同表格参数信息失败！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_TAB_PARAMETER_QUERY, "查询合同表格参数信息失败！");
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> getParentContracts(int projectId)
			throws ThriftServiceException, TException {
		List<Contract> list=Lists.newArrayList();
		try {
			list=contractMapper.getParentContracts(projectId);
		} catch (Exception e) {
			logger.error("查询父合同信息失败！");
			e.printStackTrace();
			throw new ThriftServiceException(ExceptionCode.CONTRACT_QUERY, "查询父合同信息失败！");
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getCreditContractCount(Map<String,Integer> map)
			throws ThriftServiceException, TException {
		return contractMapper.getCreditContractCount(map);
	}

	@Override
	public ContractAttachment getContractAttachment(int contractId)
			throws ThriftServiceException, TException {
		ContractAttachment contractAttachment = new ContractAttachment();
		contractAttachment = contractMapper.getContractAttachment(contractId);
		return contractAttachment==null?new ContractAttachment():contractAttachment;
	}

	@Override
	public int addContractAttachment(ContractAttachment contractAttachment)
			throws ThriftServiceException, TException {
		int count = contractMapper.addContractAttachment(contractAttachment);
		return count;
	}

	@Override
	public int editContractAttachment(ContractAttachment contractAttachment)
			throws ThriftServiceException, TException {
		int count = contractMapper.editContractAttachment(contractAttachment);
		return count;
	}

	@Override
	public int addAccessorie(ContractAccessorie accessorie)
			throws ThriftServiceException, TException {
		int count = contractMapper.addAccessorie(accessorie);
		return count;
	}

	@Override
	public int editAccessorie(ContractAccessorie accessorie)
			throws ThriftServiceException, TException {
		int count = contractMapper.editAccessorie(accessorie);
		return count;
	}

	@Override
	public Contract getContractByContractId(int contractId)
			throws ThriftServiceException, TException {
		
		return contractMapper.getContractByContractId(contractId);
	}

	@Override
	public int updateContractUrlOrName(Contract contract)
			throws ThriftServiceException, TException {
		contractMapper.updateContractUrlOrName(contract);
		return 0;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getJkContractCount(Map<String, Integer> myMap)
			throws ThriftServiceException, TException {
		return contractMapper.getJkContractCount(myMap);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int getZkContractCount(Map<String, Integer> myMap)
			throws ThriftServiceException, TException {
		int i= contractMapper.getZkContractCount(myMap);
		return i;
	}

	@Override
	public String getChildContract(int contractId)
			throws ThriftServiceException, TException {
		String contractName=contractMapper.getChildContract(contractId);
		if(contractName!=null && !"".equals(contractName)){
			return contractName;
		}
		return "";
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public List<Contract> getContractGenerateNumber(int contractId)
			throws ThriftServiceException, TException {
		logger.info("修改相关合同引用合同编号   ContractServiceImpl.getContractGenerateNumber contractId["+contractId+"]");
		
		Map<String, Object> param = new HashMap<String, Object>();
        param.put("results",OracleTypes.CURSOR);
        param.put("contractId",contractId);	
        contractMapper.getContractGenerateNumber(param);
        List<Contract> contracts=(List<Contract>)param.get("results");
        if(contracts==null){
        	return Lists.newArrayList();
        }
		return contracts;
	}
	
	@Override
	public String getExtensionContractNum(int projectId)throws ThriftServiceException, TException {
		String contractNum = contractMapper.getExtensionContractNum(projectId);
		if(null!=contractNum && !"".equals(contractNum)){
			return contractNum;
		}
		return "";
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateContractParameterData(
			List<ContractParameter> contractParameterList, int contractId)
			throws ThriftServiceException, TException {
		
		//修改合同参数数据为无效
		contractParameterMapper.updateContractParameterStatus(contractId);
		
		//批量新增合同参数
		contractParameterMapper.addContractParameters(contractParameterList);
		return 0;
	}

}
