/**
 * @Title: ContractServiceMapper.java
 * @Package com.xlkfinance.bms.server.contract.mapper
 * @Description: 合同参数
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月9日 下午19:23:35
 * @version V1.0
 */
package com.xlkfinance.bms.server.contract.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.contract.ContractParameter;

@MapperScan("contractParameterMapper")
public interface ContractParameterMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 批量新增合同参数
	 * @param contractParameter
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月9日 下午7:27:04
	 */
	public int addContractParameters(List<ContractParameter> contractParameters);

	/**
	 * @Description: 更新合同参数
	 * @param contractParameters
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月7日 下午3:35:20
	 */
	public int updateContractParameters(List<ContractParameter> contractParameters);
	
	/**
	 * 
	  * @Description:修改合同参数表数据状态为无效
	  * @param contractId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年6月8日 下午2:22:07
	 */
	public int updateContractParameterStatus(int contractId);

}
