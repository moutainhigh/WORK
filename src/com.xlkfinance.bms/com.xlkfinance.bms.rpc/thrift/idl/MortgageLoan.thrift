namespace java com.xlkfinance.bms.rpc.project

/*
 *客户企业信息表
 *2017-12-12 17:24:47
 **/
struct CusEnterpriseInfo{
	1: i32 pid;//
	2: i32 projectId;//
	3: i32 acctId;//
	4: string enterpriseName;//
	5: string legalRepresentative;//
	6: string foundDate;//
	7: string regMoney;//
	8: string stockholderType;//
	9: i32 staffNum;//
	10: string operationScope;//
	11: string employSituation;//
	12: string enterpriseType;//
	13: string createDate;//
	14: i32 createrId;//
	15: i32 updateId;//
	16: string updateDate;//
}

/*
 *客户资质信息表
 *2017-12-12 19:18:43
 **/
struct CusCredentials{
	1: i32 pid;//
	2: i32 acctId;//
	3: i32 projectId;//
	4: i32 fiveClassify;//
	5: i32 overdueNumsThree;//
	6: i32 overdueNumsTwo;//
	7: i32 loanTotal;//
	8: i32 isOverdue;//
	9: double overdueMoney;//
	10: double overdueTotalMoney;//
	11: i32 overduePeriods;//
	12: i32 loanApprovalsNums;//
	13: i32 loanApprovalsNumsTwo;//
	14: i32 creditApprovalsNums;//
	15: i32 creditApprovalsNumsThree;//
	16: i32 inquiriesNums;//
	17: i32 socialSecurity;//
	18: i32 accumulationFund;//
	19: string createDate;//
	20: i32 createrId;//
	21: i32 updateId;//
	22: string updateDate;//
	23: string customerNature;//客户性质
	24: string houseName;//房产名称
	25: string landNature;//土地性质
	26: string estateUse;//房产用途
	27: string isCallPerson;//是否致电个人
	28: i32 isCallUnit;//是否致电单位
	29: i32 isCallContact;//是否致电联系人
	30: double monthlyIncome;//核定月收入
	31: double monthlyReturn;//月还款额(无抵/质押贷款)
	32: double debtRadio;//收入负债比
	33: double leaseTerm;//租赁期
	34: double trialQuota;//初审额度
	35: double loanMonthlyReturn;//贷款月还款额
	36: string riskOneOpinion;//风控初审意见
	37: string riskOverOpinion;//风控复审意见
	38: string employSituation;//雇佣情况
}

/*
 *客户银行卡信息表
 *2017-12-12 19:20:55
 **/
struct CusCardInfo{
	1: i32 pid;//
	2: i32 projectId;//
	3: i32 acctId;//
	4: string receBankCardName;//
	5: string receBankCardCode;//
	6: string receBankName;//
	7: string repaymentBankCardName;//
	8: string repaymentBankCardCode;//
	9: string repaymentBankName;//
	10: string createDate;//
	11: i32 createrId;//
	12: i32 updateId;//
	13: string updateDate;//
}

/*
 *客户银行卡历史记录表
 *2017-12-12 19:25:02
 **/
struct CusHisCardInfo{
	1: i32 pid;//
	2: i32 projectId;//
	3: i32 acctId;//
	4: string receBankCardName;//
	5: string receBankCardCode;//
	6: string receBankName;//
	7: string repaymentBankCardName;//
	8: string repaymentBankCardCode;//
	9: string repaymentBankName;//
	10: i32 createrId;//
	11: string createDate;//
	12: i32 createNode;//
	13: string createUserName;
	14: string createNodeName;
	15: i32 projectType;
}

/*
 *贷款申请信息历史记录表
 *2017-12-12 19:22:40
 **/
struct BizHisLoanInfo{
	1: i32 pid;//
	2: i32 projectId;//
	3: double loanMoney;//
	4: i32 loanTerm;//
	5: string repaymentType;//
	6: double mortgageRate;//
	7: string loanUsage;//
	8: string payment;//
	9: double monthMaidRate;//
	10: double overdueRate;//
	11: double prepaymentRate;//
	12: double feeRate;//
	13: i32 createrId;//
	14: string createDate;//
	15: i32 createNode;//
	16: string createUserName;
	17: string createNodeName;
	18: i32 projectType;
}

/*客户企业信息表Service*/
service CusEnterpriseInfoService{
    //根据条件查询所有客户企业信息表
	list<CusEnterpriseInfo> getAll(1:CusEnterpriseInfo cusEnterpriseInfo);
	//查询客户企业信息表
	CusEnterpriseInfo getById(1:i32 pid);
	//新增客户企业信息表
	i32 insert(1:CusEnterpriseInfo cusEnterpriseInfo);
	//修改客户企业信息表
	i32 update(1:CusEnterpriseInfo cusEnterpriseInfo);
}

/*客户资质信息表Service*/
service CusCredentialsService{
    //根据条件查询所有客户资质信息表
	list<CusCredentials> getAll(1:CusCredentials cusCredentials);
	//查询客户资质信息表
	CusCredentials getById(1:i32 pid);
	//新增客户资质信息表
	i32 insert(1:CusCredentials cusCredentials);
	//修改客户资质信息表
	i32 update(1:CusCredentials cusCredentials);
}

/*客户银行卡信息表Service*/
service CusCardInfoService{
    //根据条件查询所有客户银行卡信息表
	list<CusCardInfo> getAll(1:CusCardInfo cusCardInfo);
	//查询客户银行卡信息表
	CusCardInfo getById(1:i32 pid);
	//新增客户银行卡信息表
	i32 insert(1:CusCardInfo cusCardInfo);
	//修改客户银行卡信息表
	i32 update(1:CusCardInfo cusCardInfo);
}

/*客户银行卡历史记录表Service*/
service CusHisCardInfoService{
    //根据条件查询所有客户银行卡历史记录表
	list<CusHisCardInfo> getAll(1:CusHisCardInfo cusHisCardInfo);
	//查询客户银行卡历史记录表
	CusHisCardInfo getById(1:i32 pid);
	//新增客户银行卡历史记录表
	i32 insert(1:CusHisCardInfo cusHisCardInfo);
	//修改客户银行卡历史记录表
	i32 update(1:CusHisCardInfo cusHisCardInfo);
	
}

/*贷款申请信息历史记录表Service*/
service BizHisLoanInfoService{
    //根据条件查询所有贷款申请信息历史记录表
	list<BizHisLoanInfo> getAll(1:BizHisLoanInfo bizHisLoanInfo);
	//查询贷款申请信息历史记录表
	BizHisLoanInfo getById(1:i32 pid);
	//新增贷款申请信息历史记录表
	i32 insert(1:BizHisLoanInfo bizHisLoanInfo);
	//修改贷款申请信息历史记录表
	i32 update(1:BizHisLoanInfo bizHisLoanInfo);
	
}