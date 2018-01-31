/**
 * @Title: ContractServiceMapper.java
 * @Package com.xlkfinance.bms.server.contract.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Chong.Zeng
 * @date: 2015年1月14日 下午7:23:35
 * @version V1.0
 */
package com.xlkfinance.bms.server.contract.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.contract.ContractNumber;

/**
 * 
 * @ClassName: ContractNumberMapper
 * @Description: 合同编号Mapper
 * @author: Cai.Qing
 * @date: 2015年4月19日 下午4:35:54
 */
@MapperScan("contractNumberMapper")
public interface ContractNumberMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 根据年份and组织前缀and合同类型代码查询合同序号
	 * @param contractNumber
	 *            合同编号对象
	 * @return 合同序号
	 * @author: Cai.Qing
	 * @date: 2015年4月19日 下午4:55:24
	 */
	public ContractNumber getContractIndexByContractRule(ContractNumber contractNumber);
}
