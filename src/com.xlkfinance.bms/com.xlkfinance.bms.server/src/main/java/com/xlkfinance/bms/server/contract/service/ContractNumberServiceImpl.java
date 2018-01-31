/**
 * @Title: ContractNumberBuilder.java
 * @Package com.xlkfinance.bms.server.contract.service
 * @Description: 合同号生成器
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2015年4月11日 上午11:33:40
 * @version V1.0
 */

package com.xlkfinance.bms.server.contract.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.TException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.achievo.framework.annotation.ThriftService;
import com.xlkfinance.bms.common.exception.ExceptionCode;
import com.xlkfinance.bms.rpc.common.ThriftServiceException;
import com.xlkfinance.bms.rpc.contract.Contract;
import com.xlkfinance.bms.rpc.contract.ContractNumber;
import com.xlkfinance.bms.rpc.contract.ContractNumberService.Iface;
import com.xlkfinance.bms.server.contract.mapper.ContractMapper;
import com.xlkfinance.bms.server.contract.mapper.ContractNumberMapper;
import com.xlkfinance.bms.server.contract.mapper.ContractTempLateMapper;

/**
 * 
 * @ClassName: ContractNumberServiceImpl
 * @Description: 合同编号生成规则 service 实现类
 * @author: Cai.Qing
 * @date: 2015年4月19日 下午3:14:27
 */
@SuppressWarnings("unchecked")
@Component("contractNumberServiceImpl")
@ThriftService(service = "com.xlkfinance.bms.rpc.contract.ContractNumberService")
public class ContractNumberServiceImpl implements Iface {
	private Logger logger = LoggerFactory.getLogger(ContractNumberServiceImpl.class);

	@SuppressWarnings("rawtypes")
	@Resource(name = "contractMapper")
	private ContractMapper contractMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "contractTempLateMapper")
	private ContractTempLateMapper contractTempLateMapper;

	@SuppressWarnings("rawtypes")
	@Resource(name = "contractNumberMapper")
	private ContractNumberMapper contractNumberMapper;

	/**
	 * 
	 * @Description: 批量处理合同编号
	 * @param contractIds
	 *            合同信息ID字符串(1,2,3)
	 * @return 受影响的记录行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月19日 下午3:24:33
	 */
	@Override
	public int genContractNumber(String contractIds) throws ThriftServiceException, TException {
		int rows = 0;
		try {
			// 1.解析合同编号字符串
			String[] contractIdArr = contractIds.split(",");
			// 2.首先循环生成基类的合同编号
			for (int i = 0; i < contractIdArr.length; i++) {
				// 2.1 根据合同ID获取合同对象
				Contract contract = contractMapper.getContractByContractId(Integer.parseInt(contractIdArr[i]));
				// 2.2 判断是否存在父类,如果没有就生成合同编号
				if (contract.getParentId() == 0) {
					// 如果等于0,就直接生成合同编号
					makeContractNumber(contract);
				}
			}
			// 3.首先循环生成非基类的合同编号
			for (int i = 0; i < contractIdArr.length; i++) {
				// 3.1 根据合同ID获取合同对象
				Contract contract = contractMapper.getContractByContractId(Integer.parseInt(contractIdArr[i]));
				// 3.2 判断是否存在父类,如果没有就生成合同编号
				if (contract.getParentId() != 0) {
					// 如果等于0,就直接生成合同编号
					makeContractNumber(contract);
				}
			}
			rows = 1;// 标识操作成功
		} catch (ThriftServiceException e) {
			if (logger.isDebugEnabled()) {
				logger.debug("新增贷前申请:" + e.getMessage());
			}
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			if (logger.isDebugEnabled()) {
				logger.debug("批量处理合同编号:" + e.getMessage());
			}
			throw new ThriftServiceException(ExceptionCode.SYSUSER_ADD, "生成合同编号失败,请重新操作!");
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 修改合同编号信息
	 * @param contractNumber
	 *            合同编号对象
	 * @return 受影响的记录行数
	 * @throws ThriftServiceException
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月19日 下午3:25:05
	 */
	@Override
	public int updateContractNumber(ContractNumber contractNumber) throws ThriftServiceException, TException {
		int rows = 0;
		try {

		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("修改合同编号信息:" + e.getMessage());
			}
		}
		return rows;
	}

	/**
	 * 
	 * @Description: 查询当前项目的所有合同信息
	 * @param projectId
	 *            项目ID
	 * @return 合同集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月19日 下午3:26:30
	 */
	@Override
	public List<Contract> getAllContractListByProjectId(int projectId) throws TException {
		List<Contract> list = new ArrayList<Contract>();
		try {
			list = contractMapper.getAllContractListByProjectId(projectId);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug("查询当前项目下的所有合同信息:" + e.getMessage());
			}
		}
		return list;
	}

	/**
	 * 
	 * @Description: 根据合同对象生成合同编号并更新
	 * @param contract
	 *            合同对象
	 * @author: Cai.Qing
	 * @throws ThriftServiceException
	 * @date: 2015年5月18日 下午4:26:04
	 */
	public void makeContractNumber(Contract contract) throws ThriftServiceException {
		// 合同编号
		String contractNumber = "";
		// 根据合同模版ID查询合同编号生成规则
		String contractNumberFun = contractTempLateMapper.getContractNumberFunByTemplateId(contract.getContractTemplateId());
		// 只有生成合同规则不为空才进行合同生成操作
		if (null != contractNumberFun && !"".equals(contractNumberFun)) {
			// 解析合同编号生成规则
			String[] cnfArr = contractNumberFun.split("_");
			// 获取年份
			Calendar c = Calendar.getInstance();
			String yearCode = c.get(Calendar.YEAR) + "";
			// 获取组织前缀
			String orgCode = cnfArr[1];
			// 获取合同类型代码
			String contractType = cnfArr[2];
			// 父类index
			String parentIndex = "";
			// 判断是否存在父类,如果有父类就解析出父类的
			if (contract.getParentId() != 0) {
				// 父类的合同编号
				String parentContractNumber = "";
				// 根据父类ID查询出合同信息,判断父类合同的编号是否存在,如果不存在就直接抛出异常返回至前台
				Contract con = contractMapper.getContractByContractId(contract.getParentId());
				if (null != con.getContractNo() && !"".equals(con.getContractNo())) {
					// 获取父类合同编号
					parentContractNumber = con.getContractNo();
				} else {
					throw new ThriftServiceException(ExceptionCode.SYSUSER_UPDATE, "请先生成父类合同的合同编码或批量生成合同编码!");
				}
				// 解析父类合同编号
				String[] arrParent = parentContractNumber.split("-");
				// 判断第一个是数字还是字母(2014XLKJJ-1-2,2014XLKGS-JK-1-2)
				if (StringUtils.isNumeric(arrParent[1])) {
					// 如果第一个是数字,就从第一个开始
					for (int i = 1; i < arrParent.length; i++) {
						if (i == 1) {
							parentIndex = arrParent[i];
						} else {
							parentIndex += "-" + arrParent[i];
						}
					}
				} else {
					// 如果第一个不是数字,就从第二个开始
					for (int i = 2; i < arrParent.length; i++) {
						if (i == 2) {
							parentIndex = arrParent[i];
						} else {
							parentIndex += "-" + arrParent[i];
						}
					}
				}
			}
			// 将条件封装成对象
			ContractNumber cn = new ContractNumber();
			// 赋值
			cn.setYearCode(yearCode);
			cn.setOrgCode(orgCode);
			cn.setContractType(contractType);
			cn.setParentContractIndex(parentIndex);
			// 合同编号对象
			ContractNumber newCn = new ContractNumber();
			// 根据 年份and组织前缀and合同类型代码 查询合同编号对象
			newCn = contractNumberMapper.getContractIndexByContractRule(cn);
			// 获取合同序号
			int contractIndex = -1;
			if (null == newCn || newCn.getPid() == 0) {
				//判断是展期的，编号直接加一不从1开始。
				
				
				// 如果没有对象,就说明这个合同序号是从1开始,需要我们插入一条数据到数据库里面
				contractIndex = 1;
				// 赋值
				cn.setContractIndex(contractIndex);
				cn.setStatus(1);
				cn.setContractId(contract.getPid());
				// 新增
				contractNumberMapper.insert(cn);
			} else {
				// 如果有对象,就说明这个合同序号是存在的,只需要更新数据到数据库即可
				contractIndex = newCn.getContractIndex() + 1;
				// 从新赋值
				newCn.setContractIndex(contractIndex);
				// 修改数据
				contractNumberMapper.updateByPrimaryKey(newCn);
			}

			// 拼接合同编号
			contractNumber = yearCode + orgCode + contractType;
			// 判断是否有父类
			if (contract.getParentId() != 0) {
				// 拼接最后的合同序号
	
				//判断是否是展期合同截取parentIndex  
				if(contractType.indexOf("ZQ") !=-1){
					// 是展期合同修改
					String[] parentIndex1 = parentIndex.split("-");
					String indesx = parentIndex1[0];
					contractNumber+= "-" + indesx;
				}else{
					contractNumber += "-" + parentIndex;
				}
				
			}
			// 判断是否是借据合同,如果是借据合同不需要加自己的序列,如果不是需要加序列
			if (contractType.indexOf("JJ") == -1) {
				//判断是否是展期合同截取parentIndex  
				if(contractType.indexOf("ZQ")!=-1){
					// 是展期合同修改
					String[] parentIndex1 = parentIndex.split("-");
					String index ="";
					if(parentIndex1.length<=1){
						index ="0";
					}else{
						index = parentIndex1[1];
					}
					int cIndex=Integer.parseInt(index)+1;
					contractNumber+= "-" + cIndex;
				}else{
					contractNumber += "-" + contractIndex;
				}
				
			}
			// 修改合同编号到数据库
			contractMapper.updateContractNoByContractId(contract.getPid(), contractNumber);
		}
	}
}
