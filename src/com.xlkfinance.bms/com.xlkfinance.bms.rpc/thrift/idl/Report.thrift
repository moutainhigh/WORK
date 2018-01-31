namespace java com.xlkfinance.bms.rpc.report
include "System.thrift"
include "Common.thrift"
include "File.thrift"



/*预警统计报表*/
struct HandleDifferWarnReport{
	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: string projectNumber;//项目编号
	4: i32 businessSource;//业务来源
	5: string deptName;//业务部门
	6: string pmUserName; // 业务经理 
	7: string applyUserName;// 申请人姓名
	8: string loanBank;//贷款银行（新贷款银行）
	9: double loanAmt;// 贷款金额
	10: string houseName;//物业名称
	11: string houseType;//物业类型
	12: string loanWay;//待款方式
	13: string foreclosureFloorDate;//赎楼日期
	14: string cancelMortgageDate;//注销抵押登记日期（注销抵押）
	15: string transferDate;//过户申请递件日期（过户）
	16: string getNewLicenseDate;//领新房产证日期（取新证）
	17: string mortgageDate;//抵押递件日期（抵押）
	18: string cancelGuaranteeDate;//解除担保日期（解保日期）
	19: string transferOperator;//过户专员
	20: string warnTag;//预警跟进标记
	21: i32 page=1; //第几页
	22: i32 rows=10; //总页数
	23: string sellerName;//卖方姓名
	24: string buyerName;//买方姓名
	25: string productName;//产品名称
	26: string foreclosureFloorEndDate;//赎楼日期(仅作查询条件)
	27: string cancelMortgageEndDate;//注销抵押登记日期（注销抵押，仅作查询条件）
	28: string transferEndDate;//过户申请递件日期（过户，仅作查询条件）
	29: string getNewLicenseEndDate;//领新房产证日期（取新证，仅作查询条件）
	30: string mortgageEndDate;//抵押递件日期（抵押，仅作查询条件）
	31: string cancelGuaranteeEndDate;//解除担保日期（解保日期，仅作查询条件）
	32: list<i32> userIds;//
	33: string businessSourceName;//业务来源
	34: string businessSourceStr;//业务来源子节点
}
/*业务汇总报表*/
struct BusinessSummaryReport{
	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: string projectNumber;//项目编号
	4: i32 businessSource;//业务来源
	5: string deptName;//业务部门
	6: i32 deptId;//业务部门
	7: string pmUserName; // 业务经理 
	8: i32 pmUserId; // 业务经理 
	9: i32 page=1; //第几页
	10: i32 rows=10; //总页数
	11: list<i32> userIds;//
	12:string housePropertyCard;//房产证号
	13: string sellerName;//卖方姓名
	14: string buyerName;//买方姓名
	15: double loanAmt;// 贷款金额BIZ_PROJECT_GUARANTEE.LOAN_MONEY
	16:double interest;//利息
	17:string recInterestDate;//收利息日期
	18:string loanDate; // 放款日期
	19:string planRecLoanDate; // 应回款日期
	20:double recMoney; // 已回款金额
	21:string recLoanDate; // 回款日期(解除担保日期（解保日期）)
	22:double remainingRecMoney; //未回款金额
	23:double overdueFee;//逾期费
	24:i32 overdueDay;//逾期天数
	25:double extensionFee;//展期费用
	26:string extensionDate;//展期日期
	27:double feeTotal;//费用合计
	28:string oldLoanBank;//原按揭银行
	29:string productName;//产品名称
	
	30:i32 innerOrOut;//订单类型（内外单）BIZ_PROJECT.INNER_OR_OUT
	31:i32 foreclosureStatus;//赎楼状态BIZ_PROJECT.FORECLOSURE_STATUS
	32:i32 recFeeStatus;//收费状态biz_loan_finance_handle.status
	33:string bizHandleDynamic;//业务办理动态
	
	34:double realLoan;//放款金额（财务受理的）
	35:double poundage;//手续费（财务受理的）
	36:double loanAmtMax;// 贷款金额(仅作范围查询)
	37:i32 handleFlowId;// 
	38:string handleFlowStartDate;// 
	39:string handleFlowEndDate;// 
	
	40: string foreclosureFloorDate;//赎楼日期
	41: string getOldLicenseDate;//取旧证
	42: string cancelMortgageDate;//注销抵押登记日期（注销抵押）
	43: string transferDate;//过户申请递件日期（过户）
	44: string getNewLicenseDate;//领新房产证日期（取新证）
	45: string mortgageDate;//抵押递件日期（抵押）
	
	46:string innerOrOutName;//订单类型名称
	47:string foreclosureStatusName;//赎楼状态
	48:string businessSourceName;//业务来源
	49:string loanEndDate; // 放款日期（仅作查询条件）
    50: string businessSourceStr;//业务来源子节点
    51: i32 repaymentStatus;//回款状态：未回款=1,已回款=2
}


/*撤单业务汇总报表*/
struct ChechanReport{
	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: string projectNumber;//项目编号
	4: i32 businessSource;//业务来源
	5: string deptName;//业务部门
	6: i32 deptId;//业务部门
	7: string pmUserName; // 业务经理 
	8: i32 pmUserId; // 业务经理 
	9: i32 page=1; //第几页
	10: i32 rows=10; //总页数
	11: list<i32> userIds;//
	12:string housePropertyCard;//房产证号
	13: string sellerName;//卖方姓名
	14: string buyerName;//买方姓名
	15: double loanAmt;// 贷款金额BIZ_PROJECT_GUARANTEE.LOAN_MONEY
	16:double interest;//利息
	17:string recInterestDate;//收利息日期
	18:string loanDate; // 放款日期
	19:string planRecLoanDate; // 应回款日期
	20:double recMoney; // 已回款金额
	21:string recLoanDate; // 回款日期(解除担保日期（解保日期）)
	22:double remainingRecMoney; //未回款金额
	23:double overdueFee;//逾期费
	24:i32 overdueDay;//逾期天数
	25:double extensionFee;//展期费用
	26:string extensionDate;//展期日期
	27:double feeTotal;//费用合计
	28:string oldLoanBank;//原按揭银行
	29:string productName;//产品名称
	
	30:i32 innerOrOut;//订单类型（内外单）BIZ_PROJECT.INNER_OR_OUT
	31:i32 foreclosureStatus;//赎楼状态BIZ_PROJECT.FORECLOSURE_STATUS
	32:i32 recFeeStatus;//收费状态biz_loan_finance_handle.status
	33:string bizHandleDynamic;//业务办理动态
	
	34:double realLoan;//放款金额（财务受理的）
	35:double poundage;//手续费（财务受理的）
	36:double loanAmtMax;// 贷款金额(仅作范围查询)
	37:i32 handleFlowId;// 
	38:string handleFlowStartDate;// 
	39:string handleFlowEndDate;// 
	
	40: string foreclosureFloorDate;//赎楼日期
	41: string getOldLicenseDate;//取旧证
	42: string cancelMortgageDate;//注销抵押登记日期（注销抵押）
	43: string transferDate;//过户申请递件日期（过户）
	44: string getNewLicenseDate;//领新房产证日期（取新证）
	45: string mortgageDate;//抵押递件日期（抵押）
	
	46:string innerOrOutName;//订单类型名称
	47:string foreclosureStatusName;//赎楼状态
	48:string businessSourceName;//业务来源
	49:string loanEndDate; // 放款日期（仅作查询条件）
    50: string businessSourceStr;//业务来源子节点
    51:i32 isChechan;//是否已撤单
    52:string chechanCause;//撤单原因
    53:string chechanDate;//撤单日期
    54:i32 chechanUserId;//撤单用户ID
    55:string newLoanBank;//新贷款银行 
    56:string newLoanBankBranch;//新贷款支行
    57:string applyName;//申请人
    58:string paymentType;//贷款方式
    59:string chechanUser;//撤单用户
    
}
/*收费统计报表*/
struct CollectFeeReport{
	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: string projectNumber;//项目编号
	4: i32 businessSource;//业务来源
	5: string businessSourceName;//业务来源
    24: string businessSourceStr;//业务来源子节点
	6: string deptName;//业务部门
	7: i32 deptId;//业务部门
	8: string pmUserName; // 业务经理 
	9: i32 pmUserId; // 业务经理 
	10: double loanAmt;// 贷款金额BIZ_PROJECT_GUARANTEE.LOAN_MONEY
	11: string foreclosureAmt;//赎楼金额
	12: double consultFee;//咨询费（财务受理的）
	13: double poundage;//手续费（财务受理的）
	14: double brokerage;//佣金（财务受理的）
	15: string collectFeeDate;//收费日期
	16: string maxCollectFeeDate;//收费日期(仅作范围查询)
	17: i32 page=1; //第几页
	18: i32 rows=10; //总页数
	19: list<i32> userIds;//
	20: string housePropertyCard;//房产证号
	21: string homeName;//房产名称
	22: string sellerName;//卖方姓名
	23: string buyerName;//买方姓名
	25:i32 foreclosureStatus;//赎楼状态BIZ_PROJECT.FORECLOSURE_STATUS
	26:i32 innerOrOut;//单据类型（内单或外单）
	27:string productName;//
	
}
/*业绩一览表*/
struct TrackRecordReport{
	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: string projectNumber;//项目编号
	4:string productName;//产品名称
	5: string deptName;//业务部门
	6: i32 deptId;//业务部门
	7: string pmUserName; // 业务经理 
	8: i32 pmUserId; // 业务经理 
	9: i32 page=1; //第几页
	10: i32 rows=10; //总页数
	11: list<i32> userIds;//
	12:string housePropertyCard;//房产证号
	13: string sellerName;//卖方姓名
	14: string buyerName;//买方姓名
	15:string newLoanBank;//新贷款银行
	16:string oldLoanBank;//原按揭银行
	17:i32 productId;//产品ID
	18:double loanAmt;// 贷款金额BIZ_PROJECT_GUARANTEE.LOAN_MONEY
	19:double interest;//实收费用
	20:string requestDate;//报单日期（申请日期）
	21:string loanDate;//放款日期
	22: string foreclosureFloorDate;//赎楼日期
	23: string getOldLicenseDate;//取旧证
	24: string cancelMortgageDate;//注销抵押登记日期（注销抵押）
	25: string transferDate;//过户申请递件日期（过户）
	26: string getNewLicenseDate;//领新房产证日期（取新证）
	27: string mortgageDate;//抵押递件日期（抵押）
	28:double overdueFee;//逾期费
	29:i32 overdueDay;//逾期天数
	30:double extensionFee;//展期费用
	31:i32 extensionDays;//展期天数
	32:string recLoanDate;//解保日期
	33:string declaration;//报单员
	34:string houseClerkName;//赎楼员
	35:string logoutName;//注销员
	36:string assignedName;//过户人
	37:string newCardName;//取新证人员
	38:string mortgageName;//抵押人
	39:i32 innerOrOut;//单据类型（内单或外单）
	40:string condition;//查询条件
	41:string loanEndDate; // 放款日期（仅作查询条件）
	42:string handleFlowStartDate;// 
	43:string handleFlowEndDate;//
	44:i32 handleFlowId;
	45:string innerOrOutName;//订单类型名称
} 


/*赎楼贷业务统计*/
struct ForeclosureReport{
	1: string rePaymentMonthId;//月ID
	2: i32 newCount;//新增笔数
	3: double newMoney;//新增金额
	4: i32 squareCount;//结清笔数
	5: double squareMoney; // 结清金额 
	6: string ingCount;//在途笔数
	7: string ingMoney;//在途金额
	8: i32 page=1; //第几页
	9: i32 rows=10; //总页数
	10: list<i32> userIds;//
	11:string reMonth;//统计周期月/周(仅作范围查询)
	12:string sumNewCount;//累计笔数
	13:string sumNewMoney;//累计金额
	14:i32 chooseMonthOrWeek;//0:月 1：周  2：日（仅作范围查询）
	15: string fromDate;//开始日(仅作范围查询)
	16: string endDate;//结束日(仅作范围查询)
	17: i32 projectId;//项目ID
	18: string projectName;//项目名称
	19: string projectNumber;//项目编号
	20: i32 businessSource;//业务来源
	21: string pmUserName; // 业务经理 
	22: i32 pmUserId; // 业务经理 
	23: string applyLoanDate;//贷款申请时间
	24: double loanMoney;//贷款金额
	25:i32 includeWt; //0:不包含万通数据；1：包括万通数据
	26: string businessSourceStr;//业务来源精确
}

/*赎楼贷合作机构业务统计*/
struct ForeclosureOrganizationReport{
	1: string rePaymentMonthId;//月ID
	2: i32 newCount;//新增笔数
	3: double newMoney;//新增金额
	4: i32 squareCount;//结清笔数
	5: double squareMoney; // 结清金额 
	6: string ingCount;//在途笔数
	7: string ingMoney;//在途金额
	8: i32 page=1; //第几页
	9: i32 rows=10; //总页数
	10: list<i32> userIds;//
	11:string reMonth;//统计周期月/周(仅作范围查询)
	12:string orgName;//合作机构
	13:i32 chooseMonthOrWeek;//0:月 1：周 2：日（仅作范围查询）
	14: string fromDate;//开始日(仅作范围查询)
	15: string endDate;//结束日(仅作范围查询)
	16:string availableLimit;//启用额度
	17:string singeUpperLimit;//单笔上线
	18:string assureMoney;//保证金
	//19: string totalCount;//累计笔数
	//20: string totalMoney; // 累计金额 
	19:i32 includeWt; //0:不包含万通数据；1：包括万通数据
	20: string sumNewCount;//累计笔数
	21: string sumNewMoney;//累计金额
}

/*资金方业务统计*/
struct ForeclosureCapitalReport{
	1: i32 rePaymentMonthId;//月ID
	2: i32 newCount;//已放款笔数
	3: double newMoney;//已放款金额
	4: i32 squareCount;//已还款笔数
	5: double squareMoney; //已还款金额 
	6: string ingCount;//未还款笔数
	7: string ingMoney;//未还款金额
	8: i32 page=1; //第几页
	9: i32 rows=10; //总页数
	10: list<i32> userIds;//
	11:string reMonth;//统计周期月/周(仅作范围查询)
	12:string totalCount;//累计笔数
	13:string totalMoney;//累计金额
	14:string orgName;//资金方
	15:i32 chooseMonthOrWeek;//0:月 1：周 2：日（仅作范围查询）
	16:string fromDate;//开始日(仅作范围查询)
	17:string endDate;//结束日(仅作范围查询)
	18:i32 includeWt; //0:不包含万通数据；1：包括万通数据
}

/*
 * 
 * 财务统计报表  Service
 */

  struct FinancialStatisticsReport{  
  	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: i32 businessSource;//业务来源
	4: i32 page=1; //第几页
	5: i32 rows=10; //总页数
	6: list<i32> userIds;//
	7: double loanAmt;// 贷款金额
	8: double realLoan;//放款金额
	9:string loanDate;//放款日期
	10:double recMoney;//已回款金额
	11:string recLoanDate;//回款日期
	12:string planLoanDate;//应回款日期 
	13:double needRecMoney;//应回款金额
	14:string orgName;//资方来源
	15:string orgLoanDate;//资方放款时间
	16:string orgReDate;//还资方时间
	17:double poundage; //手续费
	18:double rePoundage; //退手续费
	19:double interest; //咨询费
	20:double reInterest; //退咨询费
	21:double payInterest; //付平台息费
	22:string loanEndDate;//仅作查询 （放款截止日期）
	23:string planLoanEndDate;//仅作查询 （应回截止日期）
	24:string recLoanEndDate;//仅作查询 （回款截止日期）
	25:string businessSourceStr;//列表中的业务来源
	26: string projectNumber;//项目编号
	27: string pmUserName;//经办人
  }
  
  /*
 * 
 * 收退咨询费报表  Service
 */
  struct RefundFeesReport{
  	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: i32 businessSource;//业务来源
	4: i32 page=1; //第几页
	5: i32 rows=10; //总页数
	6: list<i32> userIds;//
	7:double returnFee; //咨询费
	8:double confirmMoney; //退咨询费
	9:string confirmDate;//仅作查询 （退咨询费日期）
	10:string confirmEndDate;//仅作查询 （退咨询费截止日期）
	11:string businessSourceStr;//详细业务来源
	12:string collectFeesDate;//仅作查询 （收咨询费截止日期）
	13:string collectFeesEndDate;//仅作查询 （收咨询费截止日期）
	14:string pmUserName;//经办人
	15:string chinaName;//借款人
	16:double loanAmount;//借款金额
	17:i32 loanDays;//借款期限
	18:double poundage;//预收手续费
	19:string projectNumber;//项目编号
	20:i32 isChechan;//是否撤单
	
	21:string sumReturnFee;//合计咨询费
	22:string recLoanDate;//客户回款日期
	23:string recInterestDate;//收展期费日期
	24:double extensionFee;//展期费
	
	25:double extensionFeeRate;//展期费率
	26:double consultingfeeRate;//月咨询费率
	27:i32 extensionDays;//展期天数
	
	28:double planConsultingMoney;//应收咨询费
	29:double planExtensionMoney,//应收展期费
	30:double planRetrunConsulting,//应退咨询费
	31:string loanDate;//放款日期
	32:double recMoney;//客户回款金额
	33:double acExtensionFee//应收展期费
	34:i32 realLoanDays;//实际用款天数
	}
	/*
 * 
 * 业务审批明细  Service
 */
  struct BusinessApprovalDetail{ 
  	1: i32 projectId;//项目ID
	2: string projectName;//项目名称
	3: i32 businessSource;//业务来源
	4: string projectNumber;//项目编号
	5: string customerName;//客户名称
	6: double realLoan;//放款金额
	7: string businessCatelog;//业务类型
	8: string createDate;//提单时间
	9: string approvalStep;//操作步骤
	10: i32 approvalStatus;//操作状态
	11: string approvalPerson;//操作人
	12: string approvalDate;//操作时间
	13: i32 page=1; //第几页
	14: i32 rows=10; //总页数
	15: list<i32> userIds;
	16: string createEndDate;//提单时间（仅做查询）
	17: string approvalEndDate;//操作结束时间（仅做查询）
	18: string businessSourceStr;//业务来源(详细)
	19: string department//部门
	20: string approvalStatusName;//操作通过or不通过
	}
	
/*
 * 
 * 业务审批台账  Service
 */
  struct BusinessApprovalBill{ 
  	1: string dateId;//日期ID
	2: string approvalStep;//操作节点
	3: string department;//操作部门
	4: string approvalPerson;//操作人
	5: i32 totalCount;//业务笔数
	6: double totalMoney;//业务金额
	7: i32 page=1; //第几页
	8: i32 rows=10; //总页数
	9: list<i32> userIds;
	10:string fromDate;//开始日(仅作范围查询)
	11:string endDate;//结束日(仅作范围查询)
	12:i32 chooseMonthOrWeek;//0:月 1：周 2：日（仅作范围查询）
	13: i32 businessSource;//业务来源
	14: string businessSourceStr;//业务来源(详细)
	15: i32 projectId;//项目ID
	16: string projectName;//项目名称
	17:string customerName;//客户名称
	18:string businessCatelog;//业务类型
	19:string approvalDate;//操作时间
	20: double realLoan;//放款金额
	21:i32 includeWt; //0:不包含万通数据；1：包括万通数据
	}
//拒单列表
 struct RefuseProjectReport{
 	1: i32 projectId;
 	2: string projectName;//项目名称
 	3: string projectNumber;//项目编号
 	4: string businessSourceStr;//业务来源
 	5: string productName;//产品名称
 	6: i32 productId;//产品ID
 	7: string acctName;//客户名称
 	8: double loanMoney;//借款金额
 	9: string refuseUserName;//拒单操作人
 	10: string refuseTime;//拒单时间
 	11: string refuseReason;//拒单原因
 	12: string refuseTimeStart;//拒单时间开始
 	13:string refuseTimeEnd;//拒单时间结束
 	14: i32 page = 1;
 	15: i32 rows =10;
 	16:list<i32> userIds;//数据权限
 }

/*
 * 
 * 统计分析报表  Service
 */
service ReportService {
    //预警统计报表（分页查询，含导出）
	list<HandleDifferWarnReport> queryHandleDifferWarnReport (1:HandleDifferWarnReport handleDifferWarnReport) ;
    //预警统计报总数
	i32 getHandleDifferWarnReportTotal (1:HandleDifferWarnReport handleDifferWarnReport) ;
    
    //业务汇总报表（分页查询）
	list<BusinessSummaryReport> queryBusinessSummary (1:BusinessSummaryReport businessSummaryReport) ;
    //业务汇总总数
	i32 getBusinessSummaryTotal (1:BusinessSummaryReport businessSummaryReport) ;
	//撤单汇总报表（分页查询）
	list<ChechanReport> queryChechan (1:ChechanReport chechanReport) ;
    //撤单汇总总数
	i32 getChechanTotal (1:ChechanReport chechanReport) ;
	
    //收费统计报表（分页查询）
	list<CollectFeeReport> queryCollectFeeReport (1:CollectFeeReport collectFeeReport) ;
    //收费统计报表总数
	i32 getCollectFeeReportTotal (1:CollectFeeReport collectFeeReport) ;
    //根据id集合查询收费统计报表
	list<CollectFeeReport> queryCollectFeeReportByIds (1:string ids) ;
	
	//业绩一览表
	list<TrackRecordReport> queryTrackRecordReport(1:TrackRecordReport trackRecordReport);
	//业绩总数
	i32 queryTrackRecordReportTotal(1:TrackRecordReport trackRecordReport);
	//根据ID集合查询业绩，用于报表导出
	list<TrackRecordReport> queryTrackRecordReportByIds(1:string ids);
	
	//赎楼贷业务统计（分页查询，含导出）
	list<ForeclosureReport> queryForeclosureReport (1:ForeclosureReport foreclosureReport);
    //赎楼贷业务统计总数
	i32 getForeclosureReportTotal (1:ForeclosureReport foreclosureReport);
	
	//赎楼贷业务统计新增列表（分页查询，含导出）
	list<ForeclosureReport> queryNewForeclosureReport (1:ForeclosureReport foreclosureReport);
    //赎楼贷业务统计总数新增列表
	i32 getNewForeclosureReportTotal (1:ForeclosureReport foreclosureReport);
	
	//赎楼贷业务统计结清列表（分页查询,含导出）
	list<ForeclosureReport> querySquareForeclosureReport (1:ForeclosureReport foreclosureReport);
    //赎楼贷业务统计总数结清列表
	i32 getSquareForeclosureReportTotal (1:ForeclosureReport foreclosureReport);
	
	//赎楼贷合作机构业务统计（分页查询、包含导出）
	list<ForeclosureOrganizationReport> queryForeclosureOrganizationReport (1:ForeclosureOrganizationReport foreclosureOrganizationReport);
    //赎楼贷合作机构业务统计总数
	i32 getForeclosureOrganizationReportTotal (1:ForeclosureOrganizationReport foreclosureOrganizationReport);
	
	//资金方业务统计（分页查询,含导出）
	list<ForeclosureCapitalReport> queryForeclosureCapitalReport (1:ForeclosureCapitalReport foreclosureCapitalReport);
    //资金方业务统计总数
	i32 getForeclosureCapitalReportTotal (1:ForeclosureCapitalReport foreclosureCapitalReport);
	
	//财务统计报表（分页查询、包含导出）
	list<FinancialStatisticsReport> queryFinancialStatisticsReport (1:FinancialStatisticsReport financialStatisticsReport);
    //财务统计报表总数
	i32 getFinancialStatisticsReportTotal (1:FinancialStatisticsReport financialStatisticsReport);
	
	//退咨询费统计报表（分页查询，含导出）
	list<RefundFeesReport> queryRefundFeesReport(1:RefundFeesReport refundFeesReport);
    //退咨询费统计表总数
	i32 getRefundFeesReportTotal (1:RefundFeesReport refundFeesReport);
	
	//业务审批明细 （分页查询,含导出）
	list<BusinessApprovalDetail> queryBusinessApprovalDetail(1:BusinessApprovalDetail businessApprovalDetail);
    //业务审批明细 总数
	i32 getBusinessApprovalDetailTotal (1:BusinessApprovalDetail businessApprovalDetail);
	
	//业务审批台账（分页查询,含导出）
	list<BusinessApprovalBill> queryBusinessApprovalBill(1:BusinessApprovalBill businessApprovalBill);
    //业务审批台账总数
	i32 getBusinessApprovalBillTotal (1:BusinessApprovalBill businessApprovalBill);
	
	//业务审批台账统计业务详情（分页查询,含导出）
	list<BusinessApprovalBill> queryBusinessApprovalBillCount(1:BusinessApprovalBill businessApprovalBill);
    //业务审批台账统计业务详情总数
	i32 getBusinessApprovalBillCountTotal (1:BusinessApprovalBill businessApprovalBill);
	//查询拒单列表
	list<RefuseProjectReport> queryResuseProjectByPage(1:RefuseProjectReport query);
	//查询拒单总数
	i32 queryResuseProjectCount(1:RefuseProjectReport query);
}

