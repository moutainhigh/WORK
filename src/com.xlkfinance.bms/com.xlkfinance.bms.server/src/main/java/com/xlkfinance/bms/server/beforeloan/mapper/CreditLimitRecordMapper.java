package com.xlkfinance.bms.server.beforeloan.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.CreditsDTO;

@MapperScan("creditLimitRecordMapper")
public interface CreditLimitRecordMapper<T, PK> extends BaseMapper<T, PK> {
	//删除贷款项目
	int delLoanAppProject(CreditsDTO creditsDTO);
	//根据项目Id查贷款ID
	CreditsDTO queryLoanProjectbyId(CreditsDTO creditsDTO);
	
}
