/**
 * @Title: ContractServiceMapper.java
 * @Package com.xlkfinance.bms.server.contract.mapper
 * @Description: 合同表格参数
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
import com.xlkfinance.bms.rpc.contract.ContractDynamicTableParameter;

@MapperScan("contractTabParameterMapper")
public interface ContractTabParameterMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 批量新增合同表格参数
	 * @param contractParameter
	 * @return
	 * @author: zhanghg
	 * @date: 2015年2月9日 下午7:27:04
	 */
	public int addContractTabParam(List<ContractDynamicTableParameter> contractParameters);

	/**
	 * @Description: 更新合同表格参数
	 * @param contractParameters
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年3月7日 下午3:35:20
	 */
	public int updateContractTabParam(List<ContractDynamicTableParameter> contractParameters);
	
	/**
	 * 
	  * @Description: 根据合同ID查询数据
	  * @param contractId
	  * @return
	  * @author: zhanghg
	  * @date: 2015年4月16日 下午5:28:54
	 */
	public List<ContractDynamicTableParameter> getContractTabs(int contractId);

}
