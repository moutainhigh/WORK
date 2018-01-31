namespace java com.xlkfinance.bms.rpc.finance
include "Common.thrift"
include "Beforeloan.thrift"

/*财务账户*/
struct FinanceBank {
	
	1: i32 pid;
	2: string chargeName;
	3: i32 bankCardType;
	4: i32 bank;
	5: string bankNum;
	6: string bankUserName;
	7: double defaultAmt;
	8: i32 isOpen;
	9: string remark;
	10: string creDttm;
	11: i32 status;
	12: string isOpenText;
	13: string bankCardTypeText;
	14: i32 showSeq;
	15: string bankText;
	16: i32 rows;
    17: i32 page; 
}


/*多付金额退还处理*/
struct ProjectRefund{
	1: i32 pid;
	2: i32 loanId;
	3: i32 inputId;
	4: double refundAmt;
	5: double BalanceAmt;
	6: string remark;
	7: string refundBankNum;
	8: string refundBankUser;
	9: string refundDt;
	10: i32 status;
}

/*提前还款收款*/
struct LoanPreRepayInput{
	1: i32 pid;
	2: i32 preRepayId;
	3: i32 inputId;
	4: double receivedAmt;
	5: double receivedFines;
	6: double uncollectedAmt;
	7: double uncollectedFines;
	8: string remark;
	9: i32 status;
}

/*财务交易记录*/
struct loanFt{
	1: i32 pid;
	2: i32 refId;
	3: string ftType;
	4: string tenderType;
	5: double ftAmt;
	6: string ftDate;
	7: i32 ftBankAcctId;
	8: i32 ftUserId;
	9: i32 status;
}

/*还款对账*/
struct loanReconciliation{
	1: i32 pid;
	2: i32 loan_info_id;
	3: i32 reconciliationType;
	4: i32 reconciliationCycleNum;
	5: i32 realtimeId;
	6: double reconciliationAmt;
	7: string reconciliationDt;
	8: string genDttm;
	9: i32 reconciliationUserId;
	10: i32 status;
}
/** add by yql*/
/*财务支出，理财利息，融资借贷返回结果*/
struct FinanceTransactionView{
	1: i32 pid;
	2: i32 bankAcctId;//财务账户ID
	3: string creDttm;//登记时间
	4: i32 creUserId;//登记人
	5: double ftAmt;//金额
	6: string ftDt;//财务日期
	7: i32 regCategory;//登记类别（融资借款:1、融资还款:2、理财收入3:、使用支出4:）
	8: i32 ftType;//财务类型（收入、支出）
	9: string repayDt;//借款应还日期
	10: i32 repayType;//还款类型（还款、付息）
	11: i32 incomeType;//理财收入类型（利息）
	12: i32 usedType;//使用支出类型（字典N种）
	13: string remark;
	14: string usedTypeText;//支出类型名称
	15: double ftAmtLoan;//借款金额
	16: double ftAmtInput;//已还金额
	17: double loanBalance;//借款金额
	18: double ftAmtInterest;//支出利息
	19:double inputUnrecAmt;//收款未对账金额
	
}
/*财务支出，理财利息，融资借贷查询参数*/
struct FinanceTransactionCondition{
	1: i32 regCategory;//登记类别（融资借款:1、融资还款:2、理财收入3:、使用支出4:）
	2: i32 usedType;//使用支出类型（字典N种）
	3: i32 bankAcctId;//财务账户ID
	4: string ftStartDt;//查询期间开始
	5: string ftEndDt;//查询期间结束
	6: i32 rows;
    7: i32 page;
    8: i32 pid;//主键
    9: i32 repayType;//还款类型（还款、付息）
}	
/*财务支出，理财利息，融资借贷添加传递的DTO*/
struct FinanceTDTO{
	1: i32 bankAcctId;//财务账户ID
	2: i32 creUserId;//登记人
	3: double ftAmt;//金额
	4: string ftDt;//财务日期
	5: i32 usedType;//使用支出类型（字典N种）
	6: string remark;
	7: i32 regCategory;//登记类别（融资借款:1、融资还款:2、理财收入3:、使用支出4:）
	8: i32 ftType;//财务类型（收入、支出）
	9: string repayDt;//借款应还日期
	10: i32 repayType;//还款类型（还款、付息）
	11: i32 incomeType;//理财收入类型（利息）
	12: i32 pid;//主键
	13: i32 status;//数据状态
	14: i32 parentId; //融资借款Id
	15: string creDttm;//登记时间
}  
/*资金头寸返回结果*/  
struct TransactionView{
    1: i32 pid;
    2: string chargeName;
	3: i32 bankCardType;
	4: double defaultAmt;
	5: string bankCardTypeText;
	6: double borrowLoanBalance;//借款余额
	7: double loanTotal;//借款总额
 	8: double loanHasAlsoTotal;//借款已还总额
 	9: double loanBalance;//贷款
	10: double loanPrincipalTotal;//放款本金总额
	11: double takeBackPrincipalTotal;//收回本金总额
	12: double rateTakeBacTotal;//费利收回总额
	13: double financialDepositInterest;//理财存款利息
	14: double expensesCostTotal;//支出费用总额
	15: double availableFundBalance;//支出费用总额	
	16: i32 rows;
    17: i32 page; 
    18: string bankNum;//银行账号
    19:double weekAmt;//一周应收金额
    20:double aprilAmt;//一个月应收金额
    21:double inputUnrecAmt;//收款未对账金额
}
/*客户财务业务查询参数*/
struct FinanceBusinessCondition{	
	1: string projectName;
	2: string projectNumber;//项目编号;
	3: string cusName;//客户名称
	4: i32 cusType;//客户类型
	5: i32 ecoTrade;//经济行业类型	
	6: string expireStartDt;//到期开始时间
	7: string expireEndDt;//到期结束时间	
	8: i32 rows;
    9: i32 page;
    10: i32 loanId; // 贷款ID
    11: string requestStartDt;//申请时间开始
    12: string requestEndDt;//申请时间结束
    13: string overdueStartDay;//过期天数开始
    14: string overdueEndDay;//过期天数结束
}
/*客户财务业务查询结果*/
struct FinanceBusinessView{
	
	1: i32 pid;
	2: i32 loanId;
	3: string projectName;
	4: string projectId;
	5: string cusType;//客户类型
	6: string projectNumber;//项目编号
	7: double creditAmt;
	8: double dueUnreceived;//到期未收金额
	9: double unDue;//未到期金额
	10: double reconciliationAmt;//已对账金额
	11: double unReconciliationAmt;//未对账金额
	12: string cusName;//客户名称
	13: string requestDttm;//申请时间
	14: double extensionAmt; // 展期总金额
	
}
/*项目总流水账查看查询结果*/
struct ProjectTotalDetailView{	
	1: i32 pid;
	2: i32 loanId;
	3: string receiveDt;
	4: double actualAmt;
	5: double availableBalance;//账户余额
	6: double reconciliationAmt;//
	7: double useBalAmt;//用户使用的余额
	8: i32 dataVersion; // 版本号
	9: bool deleteAble; // 是否可删除  true 可删除，false：不可删除
}
        
/*批量还款的查询结果*/
struct BatchRepaymentView{
	1: i32 loanId;// 贷款ID
	2: i32 pid;// 项目ID
	3: string projectName;// 项目名称
	4: string projectNumber;// 项目编号
	5: string repayDt;// 应收日期
	6: string actualReceDt;// 实收日期
}

/*客户欠款明细查询结果*/
struct CustArrearsView{
	1: i32 pid;
	2: i32 loanId;
	3: string projectName;
	4: string projectNumber;
	5: string contractNo;//合同号
	6: string contractId;//合同ID
	7: string cusName;
	8: i32 acctId;//客户id
	9: double receivablePrincipal;//应收本金金额 
	10: double receivableInterest;//应收利息
	11: double receivableMangCost;//应收管理费
	12: double receivableOtherCost;//应收其他费
	13: double receivedPrincipal;//已收本金
	14: double receivedInterest;//已收利息
	15: double receivedMangCost;//已收管理费
	16: double receivedOtherCost;//已收其他费
	17: double receivedExpireInterest;//已收逾期利息
	18: double receivedOverduePenalty;//已收逾期罚息		
	19: double unReceivedPrincipal;//到期未收本金
	20: double unReceivedInterest;//到期未收利息
	21: double unReceivedMangCost;//到期未收管理费
	22: double unReceivedOtherCost;//到期未收其他费
	23: double unReceivedOverdueInterest;//未收逾期利息
	24: double unReceivedOverduePunitive;//未收逾期罚息		
	25: double receiveTotalAmt;//已收小计
	26: double dueUnReceivedTotal;//到期未收小计
	27: double noReceiveTotalAmt;//未收合计
	28: double outstandingTotal;//未到期本息
	29: i32 comId;//企业客户的企业基本信息的主键id
	30: i32 cusType;//客户类型
	31: double noReceiveTotalAmt_im; // 即时发生数据的剩余未收
	32: double principalSurplus; // 剩余未收本金总和
	33: i32 overdueCount; // 一些页面的收息表数据-- 逾期未还款项n笔
	34: string receivablePrincipalStr; // 一些页面的收息表数据 -- 贷款本金总额
	35: string principalSurplusStr; // 一些页面的收息表数据 -- 未还贷款本金
	36: string totalFeedStr; // 一些页面的收息表数据 -- 应收费用总额
	37: string noReceiveTotalAmtStr; // 一些页面的收息表数据 -- 到期未收费用总额
	38: string unReceivedOverdueInterestStr; // 一些页面的收息表数据 -- 逾期未还款项总额
	39: string unReceivedOverduePunitiveStr; // 一些页面的收息表数据 --逾期违约金总额
	40: string contractUrl;//合同路径

}
/*财务总账查询参数*/
struct FinanceAcctTotalCondition{	
	1: string bankCardType;//类型
	2: string chargeName;//收取单位
	3: string bankUserName;
	4: string bankNum;//客户类型	
	5: string searcherPeriodStart;//查询期间
	6: string searcherPeriodEnd;//查询期间	
	7: i32 rows;
    8: i32 page; 
    9: i32 pid;
}
/*财务总账查询返回结果*/
struct FinanceAcctTotalView{	
    1: string pid;
    2: string chargeName;//收取单位
	3: string bankCardTypeText;//类型
	4: string bankNum;//账号	
	5: double initialAmt;//初始金额
	6: double incomeAccount;//账户收入
	7: double accountOut ;//账户支出
	8: double periodBalance;//期间金额
	9: string bankUserName;
	10: string ftType;
	11:string remark; // 备注
	
}
/*财务总账财务明细列表*/
struct FinanceAcctTotalDetailView{	
    1: string pid;	
	2: double initialAmt;//初始金额
	3: double incomeAccount;//账户收入
	4: double accountOut ;//账户支出
	5: double gapAmt;//支出差
	6: double terminalBalance;//期末余额
	7: string remarks;//备注
	8: double ftAmt;//交易金额
	9: string ftType;//交易类型
	10: string ftDate;//交易时间
}
/*查看对账信息列表返回结果*/
struct LoanReconciliationDtlView{
    1: i32 pid;
    2: string cycleName;
    3: string reconciliationDt;
    4: string delTypeName;
    5: double reconciliationAmt;
    6: string description;
    7: i32 type; // 类型
    
}
struct AcctProjectBalanceDTO{
	1: i32 acctId;//客户ID
	3: string acctName;//客户名称
	4: i32 projectId;//项目ID
	5: string projectName;//项目名称
	6: double balanceAmt;//金额
	7: string remark;//处理备注
	8: i32 balanceType;//期余额类型（转入、使用）
	9: string balanceDt;//退还日期
	10: i32 pid;
	11: i32 receivablesId; //收款id
	12: i32 dateVersion;//数据版本
}
struct AcctProjectBalanceView{
	1: i32 pid;
	2: i32 acctId;//客户ID	
	3: double acctAmt;//金额
	4: double balanceAmt;//金额
	5: i32 projectId;//项目ID
	6: string acctName;//客户名称
	7: i32 dateVersion;//数据版本
	8: i32 receivablesId; //收款id
}  
struct LoanRefundDTO{
    1: i32 pid;
    2: double actualRefundAmt;
    3: double refundDifferenceAmt;
    4: string refundDt;
    5: string refundBankId;
    6: string remark;
    7: i32 projectId;
    8: double payableRefundAmt;
    9: i32 status;
    10: i32 refundUserId;
    11: i32 dateVersion;//数据版本
	12: i32 receivablesId; //收款id
	13: i32 loanId;
}
struct LoanRefundView{
	1: i32 pid;
    2: double actualRefundAmt;
    3: double refundDifferenceAmt;
    4: string refundDt;
    5: string refundBankId;
    6: string remark;
    7: i32 projectId;
    8: double payableRefundAmt;
    9: i32 status;
    10: i32 refundUserId;
    11: string refundUserName;
 
}
//未对账项目查询
struct UnReconciliationCondition{
	1: string projectName;
	2: string projectNumber;//项目编号;//项目编号;
	3: string cusName;//客户名称
	4: i32 cusType;//客户类型
	5: i32 ecoTrade;//经济行业类型	
	6: string receiveStartDt;//收款开始时间
	7: string receiveEndDt;//收款结束时间	
	8: i32 rows;
    9: i32 page;
}
//未对账项目查询
struct UnReconciliationView{
	1: i32 pid;
    2: i32 projectId;
    3: string projectName;
    4: string projectNumber;
    5: i32 loanId;
    6: string receiveDt;
    7: double actualAmt;  // 收款金额
    8: double availableBalance;
    9: double reconciliationAmt;  // 已对账
    10: double unReconciliationAmt;  // 未对账
    11: i32 acctId;
    12: i32 dataVersion;
   
}
/** end by yql*/

/* add by qcxian  */
/* 财务管理-财务收款数据DTO */
struct FinanceReceivablesDTO{
    1: i32 pid; // 主键
    2: i32 loanId; // 项目主键
    3: double paymentAmount;// 收款金额
    4: string paymentDttm;// 收款日期
    5: double useBalance;//使用客户账户余额
    6: i32 reconciliation; // 对账结果
    7: i32 status;// 状态
    8: i32 version;// 数据版本
    9: double availableBalance;//剩余可用（默认=收款+使用客户账户余额）
    //10: double useBalAmt;//客户已使用余额
    10: double reconciliationAmt;//已对账金额
    11:string createUser;	// 创建人
    12:string createDate;	// 创建时间
}
/* 财务管理-财务收款数据查询条件对象 */
struct FinanceReceivablesCondition{
    1:string pid; // 主键
    2:string loanId; // 贷款主键
    3:string paymentAmount;// 收款金额
    4:string paymentDttm;// 收款日期
    5:string useBalance;//使用余额
    6:string reconciliation; // 对账结果
    7:string status;// 状态
}


/* 财务管理-财务收款页面使用的数据对象*/
struct FinanceReceivablesView{
  1:i32 loanId; // 贷款主键
  2:string projectName; // 项目名称
  3:string projectNumber; // 项目编号
  4:string businessCatelog;//业务类别
  5:string businessType; //业务品种
  6:string flowCatelog; //流程类别
  7:string realName; // 项目经理姓名
  8:double customerBalance;// 客户余额
  9:FinanceReceivablesDTO financeReceivablesDTO;//关联的财务收款DTO
  10:i32 loanInterestRecord; //贷款利息收取单位主键
  11:i32 loanMgrRecord; //贷款管理费收取单位主键
  12:i32 loanOtherFee;//其它费用收取单位主键
  13: string loanInterestRecordNo;//贷款利息收取单位账号
  14: string loanMgrRecordNo;//贷款管理费收取单位账号
  15: string loanOtherFeeNo;//其它费用收取单位账号
  16: i32 projectId;//项目id
  17: double waitReconciliationAmount;//对账金额
  18: double hedgingAmount;//平账金额
  19: double availableReconciliationAmount;//未对账金额
  20: i32 acctId; // 客户Id
}

/* 财务管理 -收款完成后，对账选择 */
struct ReconciliationOptionsView{
  1:i32 refId; // 还款计划表中或者即时发生表中的主键
  2:i32 type; // 类型： 1：计划还款期数  2：即时发生的还款选项
  3:string name; // 页面选项名称
  4:i32 refNum;// 正常还款计划的期数 或者 即时发生类型的id
  5:string repayDt;// 还款期限
  6:i32 overdue;//逾期天数
  7:bool hasFeew; // 是否有费用减免记录
}

/* 财务管理-生成对账表中的每一项内容 */
struct ReconciliationItem {
1:string pepayDt; // 还款时间
2:double principal;// 本金
3:double principal_yl;// 本金逾期利息
4:double principal_yf;// 本金逾期罚息
5:double mangCost;// 管理费
6:double mangCost_yl;// 管理费逾期利息
7:double mangCost_yf;// 管理费逾期罚息
8:double interest;// 利息
9:double interest_yl;// 利息逾期利息
10:double interest_yf;// 利息逾期罚息
11:double otherCost;// 其他费用
12:double otherCost_yl;// 其他费用逾期利息
13:double otherCost_yf;// 其他费用逾期罚息
14:double realtimePlan;// 即时发生费用
15:double realtimePlan_yl;// 即时发生费用逾期利息
16:double realtimePlan_yf;// 即时发生费用逾期罚息
17:bool overdue;// 是否逾期
18:i32 operType;//即时发生表中的类型
19:string realtimePlanName;//即时发生表中的类型名称
20:i32 refPid; // 对应的主键
21: i32 cycleNum;// 对账还款期数
22: i32 loanId;// 贷款ID
}

/* 财务管理 - 费用减免结构体，用于对账计算应收（原来的应收-对应的费用减免） */
struct LoanFeew{
 1: double mgr;    // 减免的管理费
 2: double other;  // 减免的其他费用
 3: double interest;  // 减免的利息
 4: double overduinterest;  // 减免的逾期利息
 5: double fineinterest;   // 减免的逾期罚息
 6: double totalAmt; // 减免总额
}

/* 财务管理-查询单个还款对账单中的逾期信息需要传递的参数 */
struct OverdueDataCondition{
1:i32 type;  // 类型：参考 FinanceTypeEnum.java 中的定义
2:i32 pid;   // 表中的主键（还款计划表 或者即时发生计划表中的）
3:string currentDt; // 要计算的当时时间
}

/* 财务管理-还款对账表的DTO*/
struct RepaymentReconciliationDTO{
    1: i32 pid;   // 主键
	2: i32 loanId; // 贷款ID
	3: i32 type; // 发生类型 （1:按期、2:即时发生、3:假时减免） 参照FinanceConstant..java
	4: i32 cycleNum;// 对账还款期数
	5: i32 realtimeId; // 即时发生计划表中对应的 
	6: double reconciliationAmt;// 对账总金额
	7: string reconciliationDt; // 还款日期
	8: i32 userId; // 对账人员Id
	9: i32 status;
	10: i32 receivablesId;// 使用的收款记录ID
	11:string createUser;	// 创建人
    12:string createDate;	// 创建时间
}

/* 财务管理-还款对账明细表的DTO*/
struct RepaymentReconciliationDetailDTO{
    1: i32 pid;   // 主键
	2: i32 repaymentReconciliationId; // 对账单的ID
	3: i32 detailType;//对账项类型，参考FinanceTypeEnum.java 中定义的
	4: double reconciliationAmt; // 对账金额
	5: i32 status; // 状态
	6: string description;// 对账描述
	7: string detailTypeName; //对应的类型名称
	8: i32 bankId;  // 对账入账对应的银行Id
	9: double totalAmt; // 应收金额
	10:string createUser;	// 创建人
    11:string createDate;	// 创建时间
}

/* 财务交易记录表DTO*/
struct FinanceTransactionDTO{
1:i32 pid;  // 主键
2:i32 refId; //引用ID(放款/收款）
3:i32 ftType;//  1代表放款2代表收款
4:string tenderType;// 保留字段
5:double ftAmt;// 金额
6:string ftDate; // 日期
7:i32 bankAcctId;//银行账户主键
8:i32 userId;// 操作人员ID
9:i32 status;// 状态
10: i32 specialType;// 1: 剩余的费用收款   2: 管理费和逾期利息收款  3: 其他费用和逾期利息收款
11:string remark; // 备注
12:string createUser;	// 创建人
13:string createDate;	// 创建时间
}

/* 财务对账 -- 保存财务对账时候，传输到service的数据结构*/
struct RepaymentReconciliationBean{
   1:RepaymentReconciliationDTO repaymentReconciliation;  // 财务对账记录
   2:list<RepaymentReconciliationDetailDTO> detaiList;    // 财务对账记录细节
   3:i32 reconciliationType; // 1=已对账(全部完成)，2=未对账(未开始)，3=部分对账(部分完成);
   4:i32 refId; // 关联表主键（还款计划表或者即时还款计划表）
   5:i32 type; // 发生类型 （1:按期、2:即时发生、3:假时减免） 参照FinanceConstant..java
}

/*批量还款*/
struct BatchRrepaymentView{
	1: i32 pid;
	2: i32 loan_info_id;
	3: i32 reconciliationType;
	4: i32 reconciliationCycleNum;
	5: i32 realtimeId;
	6: double reconciliationAmt;
	7: string reconciliationDt;
	8: string genDttm;
	9: i32 reconciliationUserId;
	10: i32 status;
}

/* 批量还款预览的一行数据 */
struct BatchRepaymentItem{
 1: i32 loanId ; // 贷款ID
 2: string projectName;// 项目名称
 3: string projectNum; // 项目编号
 4: string limitDate; // 还款期限
 5: i32 refPid; // 引用的主键
 6: i32 sType; // 查询类型 1：还款计划表 2：即时发生计划表
 7: string itemName; // 页面显示 第几期 或者 挪用罚金  （）
 8: i32 financeType; // 对应的类型    如果是还款计划表的话 就是期数     即时发生  就是类型
 9: double totalAmt; // 应还金额
 10:double balanceAmt;// 实收金额
 11:double remainingAmt; // 未对账金额
 12:i32 overdueDays; // 逾期天数
 13:double unBalanceAmt; // 未对账金额
}

// 批量还款提交到service的数据结构
struct BatchRepaymentBean{
1: i32 loanId ; // 贷款ID
2:string receiptDate; // 收款日期
3:double amount; // 收款总金额
4:double remainingAmt; // 对账后剩余金额
5:list<BatchRepaymentItem> details;
}

/*  用于批量对账中，查找项目中的银行账户 */
struct LoanBankAccountBean{
1: i32 loanInterestRecord;// 贷款用于收起剩余费用的账号
2: i32 loanMgrRecord; // 贷款用于收取管理费和相关逾期利息和罚息的账号
3: i32 loanOtherfee; // 贷款用于收取其他费用和相关逾期利息和罚息的账号
}

// 项目结清办理中要展现的项目基本数据
struct LoanBaseDataBean{
1:double totalAmt; // 贷款金额
2:double rprincipal;  // 应收本金
3:double rmangCost; // 应收管理费
4:double rinterest; // 应收利息
5:double rotherCost; // 应收其他费用
6:double uprincipal; // 未还本金
7:double umangCost; // 未收本金
8:double uinterest; // 未收本金
9:double uotherCost; // 未收本金
10:i32 loanId; // 贷款ID
}

// 坏账呆账结算后要显示的数据
struct BadDebtDataBean{
1:double totalAmt; // 金额
2:i32 operType; // 类型 参考FinanceTypeEnum.java 中定义的
}

// 计算月报表的计划表中的基本数据
struct MonthlyReportBasePlan{
1: i32 loanId;
2: string repayDt; // 还款期限
3: i32 cycleNmu; // 期数
4: double principal; // 应还本金
5: double mangCost; // 应还管理费
6: double otherCost; // 应还其他费用 
7: double interest; // 应还其他利息
8: i32 pid; // 主键
9: double currentMonth; // 当前换款期限所在的月份所占应还的比例
10:double lastMonth; // 上个月份所占应还比例
11:double nextNomth; // 下一个月所占应还比例
12:bool preposition; // 当前还款是前置付息
13:string planOutDt; // 放款日期

14:double crossMonthMangCost;  // 跨月的管理费部分（后置付息的当月记录有一部分在下一个月内）
15:double crossMonthOtherCost; // 跨月的其他费部分（后置付息的当月记录有一部分在下一个月内）
16:double crossMonthInterest;  // 跨月的利息费部分（后置付息的当月记录有一部分在下一个月内）

17: double o_mangCost; // 应还管理费(原来的)
18: double o_otherCost; // 应还其他费用 (原来的)
19: double o_interest; // 应还其他利息(原来的)

20: double t_mangCost; // 应还管理费(合计)
21: double t_otherCost; // 应还其他费用 (合计)
22: double t_interest; // 应还其他利息(合计)

23:bool isLastPlan; // 最后一期
}

// 计算月报表的计划表中的基本数据
struct MonthlyReportBasePlanIm{
1: i32 loanId;
2: string repayDt; // 还款期限
3: i32 opterType; // 类型
4: double operCost; // 应还金额
5: i32 refId; // 关联的业务主键
6: i32 pid; // 主键
}

 // 应收月报表查询条件
 struct MonthlyReportRecordCondition{
  1:string month; // 月份
  2:string projectName;
  3:string projectNo; 
  4:i32 status;
  5:string pids;
  6:string assWay;
  7: i32 rows;
  8: i32 page;
  9:string projectManage; // 项目经理
}

// 月报表行数据计算细节
struct MonthlyReportRecordCalculateDetail
{
1:i32 pid;
2:i32 loanId;
3:string startDate; // 开始时间
4:string endDate; // 结束日期
5:string month; // 月份如：201502
6:string content; // 计算细节描述
7:i32 monthlyReportRecordId; // 对于的月报表Id
8:string content1; // 计算细节描述
}

// 月报表的行数据（保存到数据库的行数据）
struct MonthlyReportRecord
{
  1:i32 loanId;        // 贷款ID
  2:i32 projectId;       // 项目ID
  3:string projectName;  // 项目名称
  4:string projectNo;  // 项目编号
  5:string loanDate; // 放款日期
  6:double interest; // 利息
  7:double mangCost; // 管理费
  8:double otherCost; // 其他费用
  9:double  theRestCost; // 其余的费用
  10:double totalCost; // 合计费用
  11:string startDate; // 开始时间
  12:string endDate; // 结束日期
  13:string month; // 月份如：201502
  14:i32 pid; // 主键
  15:string  manager; // 项目经理
  16:i32 status; // 数据状态：0 未锁定 1：已锁定
  17:double loanAmt; // 贷款金额
  18:MonthlyReportRecordCalculateDetail detail; // 月报表行数据计算细节
  19:string projectManage; // 项目经理
  21:string mark; // 备注
  20:bool isLastPlan; // 最后一期
 
}

// 月报表中   需要查询记录的应收逾期利息和应收逾期罚息
struct QueryOverdueReceivablesBean{
	1:i32 searchType; // 查询类型 2：还款计划表  1：即时发生还款
	2:i32 pid; //   对应的主键
	3:string limtDate; // 期限日期
	4:double receivableOverdueInterest; // 应收逾期利息
	5:double receivableOverduePunitive; // 应收逾期发现
	
	6:list<QueryOverdueReceivablesBean> result; // 调用存储过程后的结果
}

// 月报表中的费用减免记录
 struct CostReduction{
	1:i32 pId;
	2:i32 loanId;  
	3:i32 cycleNum; //期数
	7:double mangCost; // 管理费
    8:double otherCost; // 其他费用
    9:double  interest; // 利息
    10:double overdueInterest; //逾期利息
    11:double overduePunitive; //逾期罚息
    12:string repayDt;  // 对应的还款计划表中的还款期限
}
// 
 struct LoanCycleNumView{
	1:i32 rpId;//减免id
	2:i32 planCycleNum; //期数
	3:double overdueAmt; // 减免的金额
  
}
/** add by qcxian  */
/** add by yql  */
/** 财务对账余额转入收入 Bean
*/
struct LoanRecIntoDTO{
	1: i32 pid;//主键id
	2: double actualIntoAmt;//转入金额
	3: string intoDt ;// 转入时间
	4: string remark; //备注
	5: i32 loanId;
	6: i32 status;
	7: i32 userId; //操作人id
	8: i32 receivablesId; //收款id
	9: i32 bankId;//银行id
}
struct LoanRealTimeDTO{
	1: i32 pid;//主键id
	2: double total;//本期应付金额
	3: i32 loanId ;// 转入时间
	
}
struct RepaymentReconciliationView{
	1: i32 pid;//主键id
	2: i32 cycleNum;//本期应付金额
	3: i32 realtimeId ;// 转入时间
	4: double reconciliationAmt;// 对账金额
	
}
struct UserCommissionView{
   1: i32 loanId
   2: i32 projectId;
   3: i32 userId;
   4: string memberId;//工号
   5: string realName;//姓名
   6: double commissionAmt;//提成基数
   7: string projectName;
   8: string reconciltaionDt;
   9:double projectAmt; //回款金额
   10: double realCommissionAmt;//实发金额
   11: i32 superiorUserId;//直属上级id
   12: string superiorUserName;//直属上级名称
}
struct UserCommissionCondition{
   1: string memberId;//工号
   2: string realName;//姓名
   3: i32 rows;
   4: i32 page; 
   5:i32 userId;
   6: string date;//查询的月份
}



/** end by yql  */
/*财务账户*/
service FinanceService {
	/**add by yql */
  i32 addFinanceAcctManager(1:FinanceBank financeBank)throws (1:Common.ThriftServiceException e);
  //财务账户查询列表
  list<FinanceBank> getFinanceActtManager(1:FinanceBank financeBank);
  
  i32 countFinanceActtManager(1:FinanceBank financeBank);
  //财务账户详细信息
  FinanceBank getFinanceActtManagerById(1:i32 pid);
  i32 updateFinanceAcctManager(1:FinanceBank financeBank);
  i32  deleteFinanceAcctManager(1:string pid)throws (1:Common.ThriftServiceException e);
  //客户业务查询
  list<FinanceBusinessView> getCusBusiness(1:FinanceBusinessCondition financeBusinessCondition)throws (1:Common.ThriftServiceException e);
  //客户业务查询
  i32 countCusBusiness(1:FinanceBusinessCondition financeBusinessCondition)throws (1:Common.ThriftServiceException e);
  Beforeloan.Project getProCreLoans(1:i32 loanId)throws (1:Common.ThriftServiceException e);
  //批量还款查询列表
  list<BatchRepaymentView> getAcctBatchRepayment(1:FinanceBusinessCondition financeBusinessCondition)throws (1:Common.ThriftServiceException e);	
  //统计
  i32 countAcctBatchRepayment(1:FinanceBusinessCondition financeBusinessCondition)throws (1:Common.ThriftServiceException e);
 	//客户欠款明细
  list<CustArrearsView> getCustArrearsView(1:FinanceBusinessCondition financeBusinessCondition)throws (1:Common.ThriftServiceException e);	
  //统计
  i32 countCustArrearsView(1:FinanceBusinessCondition financeBusinessCondition)throws (1:Common.ThriftServiceException e);
  //财务账户总账查询列表
  list<FinanceAcctTotalView> getFinanceAcctTotalView(1:FinanceAcctTotalCondition financeAcctTotalCondition)throws (1:Common.ThriftServiceException e);	
  //统计
  i32 countFinanceAcctTotal(1:FinanceAcctTotalCondition financeAcctTotalCondition)throws (1:Common.ThriftServiceException e);
  //对账流水列表
  list<FinanceAcctTotalDetailView> getFinanceAcctTotalDetail(1:FinanceAcctTotalCondition financeAcctTotalCondition)throws (1:Common.ThriftServiceException e);	
  //项目总账流水查看
  list<ProjectTotalDetailView> getProjectTotalDetailList(1:FinanceBusinessCondition financeBusinessCondition)throws (1:Common.ThriftServiceException e);	
   //查看对账信息
  list<LoanReconciliationDtlView> getLoanReconciliationDtl(1:i32 loanId)throws (1:Common.ThriftServiceException e);
  
  //多付金额转入客户余额处理  
  i32 addAcctProjectBalance(1:AcctProjectBalanceDTO acctProjectBalanceDTO)throws (1:Common.ThriftServiceException e);
  //根据客户id查询客户的名称和客户余额
  AcctProjectBalanceView getAcctProjectBalanceByLoanId(1: i32 loanId )throws (1:Common.ThriftServiceException e);
  //根据收款id查询客户可转让的余额
  AcctProjectBalanceView getBalanceByReceId(1: i32 receId )throws (1:Common.ThriftServiceException e);
  //添加余额退还处理方法
  i32 addLoanRefund(1:LoanRefundDTO loanRefundDTO)throws (1:Common.ThriftServiceException e);
  //查询某个项目的退还金额的记录
  list<LoanRefundView> getLoanRefundList(1: i32 loanId )throws (1:Common.ThriftServiceException e);
  //查询未对账金额查询列表
  list<UnReconciliationView> getListUnReconciliation(1: UnReconciliationCondition unReconciliationCondition)throws (1: Common.ThriftServiceException e);
  //统计未对账金额查询
  i32 countUnReconciliation(1: UnReconciliationCondition unReconciliationCondition)throws (1: Common.ThriftServiceException e);
  //根据收款id查询一条未对账的收款信息
  UnReconciliationView findUnReconciliationInfo(1: i32 inputId)throws (1: Common.ThriftServiceException e);
  /**add by yql */

	/** add by qcxian  */
   // 删除财务收款
   bool deleteLoanInputDate(1:i32 pid);
   // 获取财务收款页面展现需要的数据对象 
   FinanceReceivablesView getFinanceReceivablesView(1:i32 loanId);
   // 保存财务收款
   FinanceReceivablesDTO saveFinanceReceivables(1:FinanceReceivablesDTO dto,2:i32 projectId,3:i32 acctId);
   // 保存可以使用余额（使用旧的收款继续对账，再次使用客户余额）
   FinanceReceivablesDTO saveUseBalance(1:FinanceReceivablesDTO dto,2:i32 projectId,3:i32 acctId) throws (1: Common.ThriftServiceException e);
    // 获取财务财务收款记录
   FinanceReceivablesDTO getFinanceReceivables(1:i32 financeReceivablesId);
   // 获取项目可以对账的选项
   list<ReconciliationOptionsView> getReconciliationOptionsList(1:i32 loanId,2:string currentDate);
   // 获取用于生成对账表的一期
   ReconciliationItem getReconciliationItem(1:i32 type,2:i32 pid,3:string currentDt);
   // 保存对账单	
   i32 saveRepaymentReconciliation(1:list<RepaymentReconciliationBean> repaymentReconciliationBeanList,2:i32 receivablesVersion,3:double availableReconciliationAmount,4:i32 receivablesId,5:i32 loanInterestRecord, 6:i32 loanMgrRecord, 7:i32 loanOtherFee);
   // 获取批量还款对账要展现的列
   list<BatchRepaymentItem> getBatchRepaymentLoanItemList(1:i32 loanId,2:string receivablesDate,3:double receivablesAmt);
   // 保存批量对账   返回 错误的message提示 如果为null：保存成功
   void saveBatchRepayment(1:list<BatchRepaymentBean> batchRepaymentBeanList,2:i32 userId);
   // 查询 项目结清办理中要展现的项目基本数据
   LoanBaseDataBean getLoanBaseDataBean(1:i32 projectId);
   //  获取坏账需要展现的数据
   list<BadDebtDataBean> getBadDebtDataBean(1:i32 loanId);
   //  激活最新的计划版本
   i32 activateTheLatestPlan(1:i32 loanId);
   // 获取某一天的逾期罚息或者逾期利息（对账中的费用减免使用）
   double getOverdueByDate(1:i32 loanId,2:i32 p_pid,3:string cDate);

/** add by qcxian  */
//  查询 项目的未还贷款本金      应收费用总额：         未收费用总额  逾期未还款项                逾期未还款项总额:         逾期违约金总额：
     CustArrearsView getCustArrearsbyProjectView(1:i32 projectId)throws (1: Common.ThriftServiceException e);
     
     // 运行财务月报表的数据
     void addFinanceMonthlyReport(1:string startDate,2:string endDate);
     //  财务应收报表列表展现
    list<MonthlyReportRecord> listMonthlyReportRecords(1:MonthlyReportRecordCondition condition);
    
    //  财务应收报表列表展现Total
    i32 listMonthlyReportRecordsTotal(1:MonthlyReportRecordCondition condition);  
    
    //  财务应收报表锁定解除
    i32 updateStatus(1:MonthlyReportRecordCondition condition);  
    
    //  应收月报表数据删除
    i32 deleteMonthlyReportRecordsById(1:string ids);  
    
    // 根据项目编号检查是否存在当前项目
    i32 checkLoanIdByProjectNo(1:string projectNo);
    // 保存财务应收记录
    void saveMonthlyReportRecord(1:MonthlyReportRecord monthlyReportRecord);
    
    /**
	 * 
	  * @Description: 获取项目的到期未收金额
	  * @param map
	  * @return
	  * @author: yequnli
	  * @date: 2015年9月15日 下午3:26:48
	 */
	i32 updateRepaymentPlan(1: i32 redId) throws (1: Common.ThriftServiceException e);
	/**
	 * 
	  * @Description: TODO 查询部分对账的最大期数
	  * @param loanId
	  * @return
	  * @author: yequnli
	  * @date: 2015年9月15日 下午1:34:49
	 */
	LoanCycleNumView getLoanCycleNumAndAmt(1:i32 loanId);
     
}
/*财务账户资金头寸*/
service FinanceTransactionService {
 //根据登记类别查询资金列表
 list<FinanceTransactionView> getFinanceTransactionList(1: FinanceTransactionCondition ftcondition)  throws (1: Common.ThriftServiceException e);
 //统计根据登记类别数据的数量
 i32 countFinanceTransactionList(1: FinanceTransactionCondition ftcondition)  throws (1: Common.ThriftServiceException e);
 //添加数据
 i32 insertFt(1: FinanceTDTO financeTDTO)  throws (1: Common.ThriftServiceException e);
 //根据id查询一条数据的详细信息
 FinanceTransactionView getFinanceTransactionById(1: i32 pid) throws (1: Common.ThriftServiceException e);
 //修改方法
 i32 updateFinanceTransaction (1: FinanceTDTO financeTDTO)  throws (1: Common.ThriftServiceException e);
 //融资列表查询
 list<FinanceTransactionView> getFinanceFinancing(1: FinanceTransactionCondition ftcondition)  throws (1: Common.ThriftServiceException e);
 //融资列表数据统计
 i32 countFinanceFinancing(1: FinanceTransactionCondition ftcondition)  throws (1: Common.ThriftServiceException e);
 //删除财务融资借款信息
 i32 deleteFinanceFinancing(1: FinanceTDTO financeTDTO) throws (1: Common.ThriftServiceException e);
 //资金头寸查询页面数据
 list <TransactionView> getTransactionList(1:FinanceBank financeBank) throws (1: Common.ThriftServiceException e);
 //资金头寸查询页面数据统计
 i32 countTransactionList(1:FinanceBank financeBank) throws (1: Common.ThriftServiceException e);
 //统计欠款客户数
 i32 countCustArrearsTotal()throws (1: Common.ThriftServiceException e);
 //查询员工提成
list<UserCommissionView> getListUserCommission(1:UserCommissionCondition userCommissionCondition)  throws (1: Common.ThriftServiceException e);
//查询员工提成,总数
i32 countListUserCommission(1:UserCommissionCondition userCommissionCondition)  throws (1: Common.ThriftServiceException e);

list<UserCommissionView> getListUserCommissionDetail(1:UserCommissionCondition userCommissionCondition)throws (1: Common.ThriftServiceException e);
}

service LoanRecIntoService{
	//插入数据
	i32 insertLoanRecInto(1:LoanRecIntoDTO loanRecInto)  throws (1: Common.ThriftServiceException e);
	list<AcctProjectBalanceView> getBalanceByLoanId(1: i32 loanId)  throws (1: Common.ThriftServiceException e);
	//反核销方法
	i32 antiVerification(1:i32 loanId,2:i32 inputId,3:i32 userId)  throws (1: Common.ThriftServiceException e);
	
	
}

