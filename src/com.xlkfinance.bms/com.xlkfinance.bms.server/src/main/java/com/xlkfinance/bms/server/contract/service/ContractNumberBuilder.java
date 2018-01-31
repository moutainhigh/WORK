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

import java.util.Date;

import org.springframework.stereotype.Component;

import com.achievo.framework.util.DateUtil;

/**
 * 
 * @ClassName: ContractNumberBuilder
 * @Description: 生成合同编号的Service
 * @author: Cai.Qing
 * @date: 2015年4月29日 上午11:07:00
 */
@Component("contractNumberBuilder")
public class ContractNumberBuilder {

	/**
	 * @Description: 生成临时合同号
	 * @param templateId
	 * @return
	 * @author: Simon.Hoo
	 * @date: 2015年4月11日 上午11:38:22
	 */
	public String genTempContractNumber(Integer templateId) {
		String tempContractNumber = DateUtil.format(new Date(), "yyyyMMddHHmmssSSS");
		return tempContractNumber;
	}
}
