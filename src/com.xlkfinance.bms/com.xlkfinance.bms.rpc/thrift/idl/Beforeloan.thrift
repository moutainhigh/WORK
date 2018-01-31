namespace java com.xlkfinance.bms.rpc.beforeloan
include "System.thrift"
include "Common.thrift"
include "Repayment.thrift"
include "Inloan.thrift"
include "MortgageLoan.thrift"

/*
 * 项目信息
 * 表：BIZ_PROJECT
 */
struct Project{
	1:i32 pid; // 主键
	2:i32 acctId; // 客户账号
	3:i32 projectType; // 项目类型（授信、贷款） value
	4:string projectTypeText; // 项目类型（授信、贷款） text
	5:string projectName; // 项目名称
	6:string projectNumber; // 项目编号
	7:i32 pmUserId; // 项目经理ID
	8:string realName; // 项目经理姓名
	9:i32 requestStatus; // 申请状态
	10:string requestDttm; // 申请时间
	11:string completeDttm; // 完成时间
	12:i32 status; // 状态（预留状态）
	13:i32 businessCatelog;//业务类别
	14:string businessCatelogText;
	15:i32 businessType; //业务品种
	16:string businessTypeText;
	17:i32 flowCatelog; //流程类别
	18:string flowCatelogText;
	19:i32 myType;//我方类型
	20:string myTypeText;//我方类型 文本
	21:string myMain; //我方主体
	22:string myMainText; //我方主体 文本
	23:i32 loanInterestRecord; //贷款利息收取单位
	24:string loanInterestRecordText; //贷款利息收取单位Text
	25:string loanInterestRecordNo; //贷款利息收取单位账号
	26:i32 loanMgrRecord; //贷款管理费收取单位
	27:string loanMgrRecordText; //贷款管理费收取单位 Text
	28:string loanMgrRecordNo; //贷款管理费收取单位账号
	29:i32 loanOtherFee;//其它费用收取单位
	30:string loanOtherFeeText;//其它费用收取单位 Text
	31:string loanOtherFeeNo;//其它费用收取单位账号
	32:i32 isAllowPrepay; //是否可提前还款
	33:string isAllowPrepayText; //是否可提前还款Text
	34:i32 isReturnInterest;//是否退还利息
	35:string isReturnInterestText;//是否退还利息Text
	36:string personIds; // 共同借款人ID
	37:string beginRequestDttm;// 开始申请时间
	38:string endRequestDttm;// 结束申请时间
	39:string acctName;// 客户名称
	40:i32 acctType;// 客户类别 
	41:string acctTypeText;// 客户类别 Text
	42:string credtiStartDt;// 授信开始日期
	43:string credtiEndDt;// 授信结束日期
	44:double creditAmt; // 授信额度
	45:i32 isHoop;// 是否循环
	46:double loanAmt;// 贷款金额
	47:i32 currency;// 币种 value
	48:i32 dateMode;// 日期模式 value
	49:i32 repayFun;// 还款方式 value
	50:i32 repayCycleType;// 还款周期类型 value
	51:i32 repayCycleDate;// 还款周期日期 
	52:i32 repayCycle;// 还款期限
	53:string planOutLoanDt;// 计划放款日期
	54:string planRepayLoanDt;// 计划还款日期
	55:i32 repayOption;// 还款选项
	56:i32 repayDate;// 每期还款日
	57:double monthLoanInterest;// 月贷款利率
	58:double monthLoanMgr;// 月贷款管理费率
	59:double monthLoanOtherFee;// 月其它费用费率
	60:double yearLoanInterest;// 年贷款利率
	61:double yearLoanMgr;// 年贷款管理费率
	62:double yearLoanOtherFee;// 年其它费用费率
	63:double dayLoanInterest;// 日贷款利率
	64:double dayLoanMgr;// 日贷款管理费率
	65:double dayLoanOtherFee;// 日其它费用费率
	66:double liqDmgProportion;// 违约金比例
	67:double overdueLoanInterest;// 逾期贷款利率
	68:double overdueFineInterest;// 逾期罚息利率
	69:double misFineInterest;// 挪用罚息利率
	70:double prepayLiqDmgProportion;// 提前还款违约金比例
	71:i32 circulateType;// 循环类型
	72:string abbreviation;// 简称
	73:string userPids;// 共同借款人pid
	74:i32 cusType;// 客户类型
	75:string surveyResult;// 调查结论
	76:string requestStatusVal;// 申请状态 Text
	77:string ecoTradeText;// 经济行业类别 Text
	78:string ecoTrade;// 经济行业类别
	79:string assWay;// 担保方式
	80:i32 loanId;// 贷款ID
	81:i32 page;// 
	82:i32 rows;// 
	83:string beginCompleteDttm; //开始 完成时间
	84:string endCompleteDttm; //结束 完成时间
 	85:string comments;
	86:i32	repayOptionTest;//还款选项临时字段
	87:i32  eachissueOption//每期还款日选择
	88:double feesProportion;// 手续费比例
	89:i32 creditId;// 授信ID
	90:i32 extensionNum;	// 展期期数
	91:double extensionAmt; 	// 展期金额
	92:i32 limitId;// 额度使用记录ID
	93:i32 nowUserId;//当前用户Id
	94:i32 creditStatus;// 授信状态 (0贷款项目  1 有效  2无效)
	95:string repayFunText;//还款方式
	96:string assWayText;// 担保方式Text(抵押,质押,保证,信用)
	97:i32 judgeRepayCycle;// 判断还款期限是否相等(1:不相等 -1:相等)
	98:i32 oldProjectId;//项目id 用于提款项目时存放授信项目id
	
	99:ProjectGuarantee projectGuarantee;//项目担保信息
	100:ProjectProperty projectProperty;//项目物业信息
	101:ProjectForeclosure projectForeclosure;//赎楼信息
	102:i32 productId;//产品ID 
	103:i32 businessSource;//业务来源
	104:string address;//区域
	105:string businessContacts;//业务联系人
	106:string contactsPhone;//联系人电话
	107:i32 innerOrOut;//内外单
	108:i32 businessCategory;//业务类型（1：交易，0：非交易，默认为0）
	109:i32 isNotarization;//是否公证（1：是，0：不是，默认为0）
	110:i32 isDelete;//是否删除（1：是，0：不是，默认为0）
	111:i32 isChechan;//是否撤单（1：是，0：不是，默认为0）
	112:i32 productType;//产品类型（1、信用2、赎楼）
	113:string managers;//经办人
	114:string managersPhone;//经办人电话
	115:list<i32> userIds;//数据权限
	116:i32 foreclosureStatus;//赎楼状态   1、待客户经理提交2、待部门经理审批3、待业务总监审批4、待审查员审批5、待风控初审6、待风控复审7、待风控终审8、待风控总监审批9、待总经理审批10、已审批11、已放款12、已归档
	117:string oldLoanBank;
	118:i32 orgId;
	119:double extensionFee;//展期费用
	120:string extensionDate;//展期结束时间
	121:double extensionRate;//展期费率
	122:i32 businessSourceNo;//业务来源具体数据
	123:i32 collectFileStatus;//收件状态，未收件=1，已收件=2
	124:i32 projectSource;//项目来源（万通或者小科）
	125:string businessSourceStr;//业务来源具体值
	126:list<ProjectForeInformation> foreList;//赎楼清单列表
	127:string auditorOpinion;//审批员意见
	128:string specialDesc;//特殊情况说明
	129:string declaration;//报单员,录单员
	130:i32 isSeller;//是否为买卖方
	131: i32 refundFileStatus;//退件完结状态：未完结=1，已完结=2
	132: string chechanDate;//撤单时间
	133: i32 chechanUserId;//撤单用户id
	134: string chechanUserName;//撤单用户姓名
	135: string chechanCause;//撤单原因
	136:i32 extensionDays;
	137:string sourceStr;//具体的来源名称
	140: string orgCustomerName;//客户姓名
	141: string orgCustomerPhone;//客户电话
	142: string orgCustomerCard;//客户身份证
	143: string planLoanDate;//希望放款日期
	144: double planLoanMoney;//借款金额
	145: double loanRate;//借款利率
	146: double maxLoanRate;//最高承受利率
	147: i32 isClosed;//是否关闭1、关闭2、正常
	148:string orgName;//机构名称
	149:string areaCode;//所属城市编码
	150:i32 applyUserId;//提交申请人ID（合作机构的员工ID）
	151:i32 isNeedHandle;//是否需要办理贷中1、办理2、不办理
	152:string cancelGuaranteeDate;//解保时间
	153:i32 isNeedFinancial;//是否需要小科资金1、不需要2、需要
	154:list<BizProjectEstate> estateList;//项目物业信息
	155:string houseIds;//页面传输的物业ID集合
	156:i32 isReject;//是否驳回
	157:i32 isAssigned;//是否已分配1、未分配2、已分配
	158:i32 recordClerkId;//录单员ID
	159:i32 foreAfterMonitorStatus;//赎楼贷后监控状态：无异常=1，有异常=2
	160:list<BizOriginalLoan> originalLoanList;//项目原贷款信息
	161:string originalLoanIds;//页面传输的原贷款ID集合
	162:MortgageLoan.CusEnterpriseInfo cusEnterpriseInfo; // 客户企业信息
	163:MortgageLoan.CusCredentials cusCredentials; // 客户资质信息
	164:MortgageLoan.CusCardInfo cusCardInfo; // 客户银行卡信息
	165:string capitalName;//资金方
	166:string loanType;//放款条件
	167:string nextUserId; // 待办用户
	168:string proContactIds;//页面传输的项目联系人ID集合
}

/*
 * 任务记录
 * 
 */
struct ProjectRecord{
	1:i32 pid;//主键
	2:i32 projectId;//项目id
	3:i32 processUserId;//处理人
	4:i32 processType;//处理类型
	5:string completeDttm;//申请结束时间
}

/*
 * 财务放款
 * 
 */
struct LoanOutputInfo{
	1:i32 pId;// 主键
	2:i32 loanId;// 贷款ID
	3:double shouldAmt;// 
	4:string shouldDate;//应放日期
	5:double difAmt;// 
	6:string outputDesc;//放款备注
	7:i32 refId;// 引用id(放款/收款）
	8:string ftType;// 财务交易类型
	9:string tenderType;// 款项类型
	10:double ftAmt;// 财务交易金额
	11:string ftDate;//交易日期
	12:string ftBankAcctId;//交易银行账户id
	13:i32 ftUserId;// 操作人员id
	14:string ftUserName;// 操作人员id
	15:i32 status;
}

/*
 * 财务放款实施
 * 
 */
struct LoanOutputInfoImpl{
	1:i32 pId;// 主键
	2:i32 loanId;// 贷款ID
	3:i32 projectType;// 项目类型（授信、授信贷款、额度贷款、贷款展期）
	4:double creditAmt;//贷款金额
	5:double shouldAmt;//应放款金额 
	6:double difAmt;//差额
	7:string shouldDate;// 应放日期
	8:string repayCycle;//还款期限
	9:string ftDate;// 交易日期
	10:string repayCycleDate;// 还款周期日期
	12:i32 accType;//账户类型
	13:string outputDesc;//放款备注
	14:i64 bank;//小贷银行账号
	15:string bankNum;//小贷银行账号
	16:string bankUserName;//小贷开户名
	17:string bankName;//银行名称
	18:string branchName;//网点名称
	19:string accArea;//开户区域
	20:string accName;//户名
	21:i32 bankAccCate;//开户类别
	23:i32 refId;//引用id
	24:i32 status;
	25:i32 ftType;//财务交易类型
	26:i32 tenderType;//款项类型
	27:i32 ftAmt;//财务交易金额
	28:string ftBankAcctId;//交易银行账户id
	29:i32 ftUserId;// 操作人员id
	30:string remark; //备注REMARK
	31:string loanCardId;// 放款账户
	32:string planOutLoanDt; //应放款日期
	33:string planRepayLoanDt; //PLAN_REPAY_LOAN_DT
	

	
	
}

struct LoanOutputInfoImplDTO{
	1:i32 pId;// 主键
	2:i32 loanId;// 贷款ID
	3:i32 projectType;// 项目类型（授信、授信贷款、额度贷款、贷款展期）
	4:double creditAmt;//贷款金额
	5:double shouldAmt;//应放款金额 
	6:double difAmt;//差额
	7:string shouldDate;// 应放日期
	8:string repayCycle;//还款期限
	9:string ftDate;// 交易日期
	10:string repayCycleDate;// 还款周期日期
	13:string outputDesc;//放款备注
	14:i64 bank;//小贷银行账号
	15:string bankNum;//小贷银行账号
	16:string bankUserName;//小贷开户名
	17:string bankName;//银行名称
	18:string branchName;//网点名称
	19:string accArea;//开户区域
	20:string accName;//户名
	21:i32 bankAccCate;//开户类别
	22:i32 accType;//账户类型
	23:i32 refId;//引用id
	24:i32 status;
	25:i32 ftType;//财务交易类型
	26:i32 tenderType;//款项类型
	27:double ftAmt;//财务交易金额
	28:string ftBankAcctId;//交易银行账户id
	29:i32 ftUserId;// 操作人员idftBankBcctId
	30:string loanCardId;// 放款账户
	31:string remark; //备注REMARK
	32:string planOutLoanDt; //应放款日期
	33:string planRepayLoanDt; //PLAN_REPAY_LOAN_DT
	34:string creditEndDate;//授信结束日期(财务节点的时候修改字段)
	35:string createDate;
	36:string createUser;
	
	
	
}

/*
 * 贷款信息
 * 表：BIZ_LOAN
 */
struct Loan {
	1:i32 pid;
	2:i32 projectId; // 项目ID
	3:double creditAmt;// 贷款金额
	4:i32 currency;// 币种 value
	5:string currencyText;// 币种 text
	6:i32 dateMode;// 日期模式 value
	7:string dateModeText;// 日期模式 text
	8:i32 repayFun;// 还款方式 value
	9:string reoayFunText;// 还款方式 text
	10:i32 repayCycleType;// 还款周期类型 value
	11:string repayCycleTypeText;// 还款周期类型 text
	12:i32 repayCycleDate;// 还款周期日期 
	13:i32 repayCycle;// 还款期限
	14:string planOutLoanDt;// 计划放款日期
	15:string planRepayLoanDt;// 计划还款日期
	16:i32 repayOption;// 还款选项
	17:i32 repayDate;// 每期还款日
	18:double monthLoanInterest;// 月贷款利率
	19:double monthLoanMgr;// 月贷款管理费率
	20:double monthLoanOtherFee;// 月其它费用费率
	21:double liqDmgProportion;// 违约金比例
	22:double overdueLoanInterest;// 逾期贷款利率
	23:double overdueFineInterest;// 逾期罚息利率
	24:double misFineInterest;// 挪用罚息利率
	25:double prepayLiqDmgProportion;// 提前还款违约金比例
	26:i32 requestStatus;// 申请状态
	27:string requestDttm;// 申请时间
	28:string completeDttm;// 完成时间
	29:i32 status;// 状态(预留字段)
	30:double yearLoanInterest;// 年贷款利率
	31:double yearLoanMgr;// 年贷款管理费率
	32:double yearLoanOtherFee;// 年其它费用费率
	33:double dayLoanInterest;// 日贷款利率
	34:double dayLoanMgr;// 日贷款管理费率
	35:double dayLoanOtherFee;// 日其它费用费率
	36:i32 eachissueOption;// 每期还款选项（0，固定 1，按实际放款日对日还款）
	37:double feesProportion;// 手续费比例
	38:i32 frozenStatus; //冻结状态
	39:i32 interestVersion; //利率版本
	40:i32 judgeRepayCycle;// 判断还款期限是否相等(1:不相等 -1:相等)
	41:string creditEndDate;//授信结束日期(财务节点的时候修改字段)
}

/*
 * 共同借款人信息
 * 表：BIZ_PROJECT_PUBLIC_MAN
 */
struct ProjectPublicMan{
	1:i32 pid; // 主键
	2:i32 projectId;// 项目ID
	3:i32 personId;// 关系人ID
	4:i32 status;// 状态
	5:string userPids;// 关系人ID(1,2,3)
}


/*
 * 提前还款
 * 表：BIZ_LOAN_PRE_REPAY
 */
struct LoanCalFactor{
	1:i32 pid;// 主键
	2:i32 loanId;// 贷款ID
	3:i32 inputId;// 收款ID
	4:double preRepayAmt;// 提前还贷金额
	5:double surplus;// 剩余贷款本金
	6:double fineRates;// 提前还款罚金比例
	7:double fine;// 提前还款罚金
	8:string repayDate;// 提前还贷日期
	9:i32 isArrears;// 是否拖欠我公司款项
	10:i32 isRebackInterest;// 是否退还已收取的利息
	11:i32 hasOtherLoan;// 在我公司是否有其他借款
	12:string reason;// 申请原因
	13:i32 requestStatus;// 申请状态
	14:string requestDttm;// 申请时间
	15:string compelteDttm;// 完成时间
	16:i32 status;// 状态
}

/*查询条件DTO*/
struct QueryConDTO{
	
	1: string projectName;
	2: string projectId;
	3: string acctName;
	4: i32 acctType;
	5: i32 ecoTrade;
	6: string expireStartDt;
	7: string expireEndDt;
	8: i32 overdueStartDay;
	9: i32 overdueEndDay;
	10: string requestStartDt;
	11: string requestEndDT;
	12: string actualRepaymentStartDt;//实际还款开始日期
	13: string actualRepaymentEndDt;//实际还款结束日期
	14: double shouldRepayStartAmt;//应还款开始金额
	15: double shouldRepayEndAmt;//应还款结束金额
	16: list<FinanceDTO> results;
}


/*查询结果DTO*/
struct FinanceDTO{
	
	1: i32 pid;
	2: i32 loanId;
	3: string projectName;
	4: string projectId;
	5: string acctType;
	6: double creditAmt;
	7: double expireNoRecAmt;
	8: double noExpireAmt;
	9: double reconciliationAmt;
	10: double noReconciliationAmt;
	11: string dueDate;//应收日期
	12: double totalReceivables;//应收合计
	13: double uncollTogether;//未收合计
	14: string actualReceDt;//实收日期
	15: double actualAmt;//实收金额
	16: double reconcilAmt;//对账金额
	17: double havaReconciled;//已对账
	18: double noReconciled;//未对账
	19: string reconciRust;//对账结果
	20: string operation;//操作
	21: string acctName;//客户名称

}

/** 移动端要件申请结果*/
struct GridViewMobileDto{
	1:i32 pid;//项目ID
	2:string projectName;//项目名称
	3:string productName;//产品名称
	4:double loanMoney;//金额
	5:string createrDate;//贷款提交申请时间
	6:i32 applyStatus;//申请状态
	7: i32 page=1; //第几页
	8: i32 rows=10; //总页数
	9:i32 lendId; //借件ID
	10:i32 lendState;//借件状态
	11:i32 orgId; //机构ID
	12:i32 lendFilesCount; //未归还要件数
	13:string updateTime;//更新时间
}

/** 移动端要件查询申请*/
struct ElementMobileDto{
	1:i32 pid;//项目ID
	2:string projectName;//项目名称
	3:i32 productId;//产品ID
	4:i32 applyStatus;//申请状态
	5:i32 page=1; //第几页
	6:i32 rows=10; //总页数
	7:list<i32> userIds;//数据权限
}



//pengchuntao,线下决议会议纪要,BIZ_PROJECT_MEETING 
struct BizProjectMeeting{
	1: i32 pid;
	2: i32 projectId;
	3: string meetingDttm;
	4: i32 recordUserId;
	5: string meetingLocation;
	6: string meetingResult;
	7: i32 status;
	8: string meetingNum;
}

//pengchuntao,线下决议会议纪要,初始化时候使用
struct BizProjectMeetingDTO{
	1: i32 pid;
	2: i32 projectId;
	3: string meetingDttm;
	4: i32 recordUserId;
	5: string allMeetingMembersPID;
	6: string allBizMeetingMinutesMember;
	7: string meetingLocation;
	8: string meetingResult;
	9: string userName;
	10: string meetingNum;
	11: i32 meetingId;
}

//pengchuntao,线下决议会议纪要,BIZ_PROJECT_MEETING_RST
struct BizProjectMeetingRST{
	1: i32 pid;
	2: i32 meetingId;
	3: string meetingDttm;
	4: i32 recordUserId;
	5: string meetingLocation;
	6: string meetingResult;
	7: i32 status;
}


//pengchuntao,参与人员BIZ_MEETING_MINUTES_MEMBER
struct BizMeetingMinutesMember{
	1: i32 pid;
	2: i32 meetingId;
	3: i32 meetingMemberUserId;
	4: i32 participateStatus;
	5: i32 status;
}

//pengchuntao,会议纪要附件BIZ_MEETING_MINUTES_FILE
struct BizMeetingMinutesFile{
	1: i32 pid;
	2: i32 meetingId;
	3: i32 fileId;
	4: i32 status;
}

//pengchuntao,放款审批单，BIZ_LOAN_APPROVAL_INVOICE
struct BizLoanApprovalInvoice{
	1: i32 pid;
	2: i32 loanId;
	3: i32 fileId;
	4: i32 status;
}

//xuweihao 放款审批单生成
struct LoanApprovalInvoiceInfo{
	1: string name;//借款人名称
	2: string contractNo;//合同编号
	3: string cusType;//客户类型
	4: string meetingNo;//待审会编号
	5: i32 repayCycle;//贷款期限
	6: string pmUser;//客户经理
	7: string guaranteeType;//担保方式
	8: i32 creditAmt;//贷款金额
	9: double monthLoanInterest;//月利率
	10: string shouldPrincipal;//每月应付利息金额
	11: double monthLoanMgr;//月费率
	12: string shouldMangCost;//每月应付管理费
	13: string planOutLoanDT//贷款开始时间
	14: string planRepayLoanDT//贷款结束时间
	15: i32 repayDate//每月收息日
	16: string repayFun//还款方式
	17: string accName//户名
	18: string bankName//开户行
	19: string loanCardId//银行账户
}

//pengchuntao,项目归档资料,BIZ_PROJECT_ARCHIVE
struct BizProjectArchive{
	1: i32 pid;
	2: i32 projectId;
	3: i32 archiveCatelog;
	4: string archiveCatelogValue;//归档类别的字符串值，因为在本表中保存的是PID，联合查询转换使用
	5: string archiveFileName;
	6: string archiveLocation;
	7: i32 offlineCnt;
	8: i32 onlineCnt;
	9: i32 isArchive;
	10: string isArchiveValue;
	11: string remark; 
	12: string createDttm;
	13: i32 status;
	14: i32 archiveProjectId;//项目ID，因为projectId在JSP页面有重复，设该值代替，saveBizProjectArchive方法用到
	15: i32 projectArchiveId;//BIZ_PROJECT_ARCHIVE的PID，与本类中PID同一个东西，用于界面PID的转换，在JSP界面定义
	16: string archiveCatelogName;
}

//pengchuntao,项目归档资料与对应文件关联表，BIZ_PROJECT_ARCHIVE_FILE
struct BizProjectArchiveFile{
	1: i32 pid;
	2: i32 archiveId;
	3: i32 fileId;
	4: i32 fileBusType;
	5: string fileBusTypeValue;
	6: string fileRemark;
	7: i32 status;
	8: i32 fileArchiveId;//用于界面BIZ_PROJECT_ARCHIVE的PID的转换，在JSP界面定义
}


//pengchuntao,单一项目归档资料文件信息，包括备注
struct ProjectArchiveFileDTO{
	1: i32 bizFilePid;
	2: string fileName;
	3: string fileType;
	4: i32 fileSize;
	5: string uploadDttm;
	6: string fileUrl;
	7: i32 fileBusType;//BIZ_PROJECT_ARCHIVE_FILE的
	8: string fileBusTypeValue;
	9: string fileRemark; //BIZ_PROJECT_ARCHIVE_FILE的
	10: string projectArchiveFileName;//BIZ_FILE的FILE_NAME,从Action传值到界面使用
}

//pengchuntao,项目归档资料,包括文件的时间
struct BizProjectFileArchiveAndTimeDTO{
	1: i32 projectFileArchivePid;
	2: i32 projectId;
	3: i32 fileId;
	4: string archiveCatelog;
	5: string archiveFileName;
	6: string archiveLocation;
	7: i32 offlineCnt;
	8: i32 onlineCnt;
	9: i32 isArchive;
	10: string isArchiveVal;
	11: string remark; 
	12: i32 status;
	13: string lookupVal;
	14: i32 bizFilePid;
	15: string fileUrl;
	16: string uploadDttm;
}



//还款计划表-贷款信息
struct RepaymentLoanInfo{
	1:string acctName;
	2:string contractNo;
	3:string loanOutDt;
	4:string loanRepayDt;
	5:i32 repayCycle;
	6:double creditAmt;
	7:double loanInterest;
	8:double repaymentAmt;
	9:i32 repayFun;
	10:string otherCostName;
	11: string repayFunVal;//还款方式类型
	12:double loanMgr;	//管理费率
	13:double loanOtherFee;	// 其它费用率
	14:double feesProportion;	// 手续费率
	15:string formatAmt;
	16:string loanMgrStr;
	17:string loanOtherFeeStr;
	18:string loanInterestStr;
	19:string feesProportionStr;
	20: i32 projectId;
}

//xuweihao 资料上传-资料信息
struct DataInfo{
	1: i32 dataId;
	2: i32 projectId;
	3: i32 fileId;
	4: i32 userId;
	5: i32 fileProperty;//文件类型
	6: string filePropertyName;
	7: string fileDesc;
	8: i32 status;
	9: string fileName;
	10: string fileType;
	11: i32 fileSize;
	12: string fileUrl;
	13: string uploadDttm;
	14: string cusType;
	15: i32 page;
	16: i32 rows;
	17: i32 collectFileStatus;//收件状态有效=-1，失效=1（表：biz_Collect_files.status）,该字段仅作查询
	18:list<string> projectTypes;//项目类型关联的文件类型
	19:string createUserName;//创建人
	20:i32 createNode;//创建节点
}

/*
 *即时发生DTO
 */
struct CalcOperDto{
	1:i32 pid=0;
	2:double monthLoanMgr=0.0000; //管理费利率
	3:double monthLoanOtherFee=0.0000; //其他费用利率
	4:double monthLoanInterest=0.0000; //贷款利率
	5:double operAmt=0.00; //发生金额
	6:string operRepayDt=""; //发生时间
	7:i32 repayType;//还款类型(贷款试算=1、提前还款=2，利率变更=3、挪用处理=4、违约处理=5、坏账处理=6、贷款展期=7)
	8:double interestRate=0.0000;// 挪用利率，违约利率
	9:string endDate;//挪用结束日期 、 坏账止息日期
	10:i32 exTarget=0; //展期目标 (repayType 不等于7默认为0)
}

/*
 * 担保方式
 * 表：BIZ_PROJECT_GUARANTEE_TYPE
 */
struct ProjectGuaranteeType{
	1:i32 pid;// 主键
	2:i32 projectId;// 项目ID
	3:i32 guaranteeType;// 担保方式
	4:i32 status;// 状态
	5:string guaranteeTypeText;// 担保方式名字
}


/*
 * 组织召开贷审会
 */
struct OrganizationCommission{
	1:i32 pid;//贷审会ID
	2:i32 projectId;//项目ID
	3:i32 conveneUserId;//组织人员ID
	4:string meetingMenberUser;//参与人员
	5:i32 meetingMenberUserId;//参与人员ID;
	6:string meetingMenberAllUserId;
}

/*
 * 项目关系（BIZ_PROJECT_RELATION）
 */
struct ProjectRelation{
	1:i32 pid;//项目关系主键ID
	2:i32 projectId;//项目ID
	3:i32 refProjectId;//关联项目Id（原项目ID）
	4:i32 refType;//关联类型(1:授信,2:展期）
	5:i32 status;// 状态
}

/*
 * 贷后归档
 */
struct AfterLoanArchive{
	1:i32 pid;//表主键
	2:i32 projectId;//项目ID
	3:i32 afterloanStatus;//归档状态
	4:string afterloanDttm;//归档时间
	5:string afterloanComments;//归档备注
	6:i32 status;// 状态
	7:string projectName;//项目名称
	8:string projectNumber;//项目编号
	9:i32 cusType;//客户类型
	10:string afterloanStatusText;//归档状态
	11:string requestDttm;//申请时间
	12:double loanAmt;//贷款金额
	13:string pmUserName;//业务经理
	14:string settleDttm;//结清时间
}
/*
struct SurveyReport{
	1:i32 acctName;//借款人名称
}*/
/*项目費用信息（新增）*/
struct ProjectGuarantee{
	1:i32 pid;//表主键
	2:i32 projectId;//项目ID
	3:double guaranteeMoney;//担保金额（无用）
	4:double guaranteeFee;//咨询费
	5:double poundage;//手续费(收费金额)
	6:i32 chargesType;// 收费方式（收费节点）
	7:double loanMoney;//贷款金额（借款金额）
	8:double deptMoney;//欠款金额
	9:double chargesSubsidized ;//手续费补贴
	10:double receMoney;//应收佣金
	11:string payBank;//付费银行
	12:double feeRate;//費率（月利率）
	13:double turnoverMoney;//周转金额
	14:double foreclosureMoney;//赎楼金额
	15:i32 chargeType;// 收费类型1:标准2：非标准业务
	16:i32 loanTerm;//借款期限
	17:string repaymentType;//还款方式
	18:double mortgageRate;//抵押率
	19:string loanUsage;//借款用途
	20:string paymentSource;//还款来源
	21:double monthMaidRate;//月返佣费率
	22:double overdueRate;//逾期日罚息率
	23:double prepaymentRate;//提前还款费率
	24:string createDate;//创建时间
	25:i32 createrId;//创建人
	26:string updateDate;//修改时间
	27:i32 updateId;//修改人
	28:double rentRetrialPrice;//租金复审价格
	29:double contractPrice;//租物业最终签约价格
	30:double debtRadio;//复审收入负债比
	31:double leaseTerm;//租赁期限
	32:double monthlyReturnMoney;//复审月还款额
	33:double loanRadio;//贷款系数
	34:double estimateMonthlyIncome;//评估月收入
}


/*物业买卖双方信息（新增）*/
struct ProjectProperty{
	1:i32 pid;//表主键
	2:i32 projectId;//项目ID
	3:string houseName;//物业名称
	4:double area;//面积
	5:double costMoney;//原价
	6:double tranasctionMoney;// 成交价
	7:string sellerName;//卖方姓名
	8:string sellerCardNo;//卖方身份证号
	9:string sellerPhone ;//卖方联系电话
	10:string sellerAddress;//卖方地址
	11:string buyerName;//买方姓名
	12:string buyerCardNo;//买方身份证号
	13:string buyerPhone ;//买方联系电话
	14:string buyerAddress;//买方地址
	15:string housePropertyCard;//房产证号
	16:double foreRate;//赎楼成数
	17:double evaluationPrice;//评估价格
	18:string purpose;//物业用途
	19:double evaluationNet;//评估净值
	20:double houseRentTotal;//物业总租金
	
}

/*赎楼项目信息（新增）*/
struct ProjectForeclosure{
	1:i32 pid;//表主键
	2:i32 projectId;//项目ID
	3:string oldLoanBank;//原贷款银行
	4:string oldLoanPerson;//原贷款银行联系人
	5:double oldLoanMoney;//原贷款金额
	6:string oldLoanPhone;// 原贷款银行联系电话
	7:string newLoanBank;//新贷款银行
	8:string newLoanPerson;//新贷款银行联系人
	9:double newLoanMoney ;//新贷款金额
	10:string newLoanPhone;//新银行联系电话
	11:double fundsMoney;//资金监管金额
	12:string superviseDepartment;//监管单位
	13:string notarizationDate ;//公证日期
	14:string signDate;//签署合同日期
	15:i32 paymentType;//付款方式(1:按揭2:组合贷3:公积金贷4:一次性付款)
	16:string remark;//备注
	17:string accumulationFundBank;//公积金银行
	18:double accumulationFundMoney;//公积金贷款金额
	19:i32 loanDays;// 贷款天数
	20:string paymentName;//回款户名
	21:string paymentAccount;//回款账号
	22:i32 overdueDays ;//逾期天数
	23:double overdueRate;//逾期费率
	24:double overdueFee;//逾期费
	25:i32 extensionDays;//展期天数
	26:double extensionRate ;//展期费率
	27:double extensionFee;//展期费
	28:string paymentDate;//应回款日期
	29:string receDate;//放款日期
	30:double oldOwedAmount;//原贷款欠款金额
	31:string oldLoanTime;//原贷款结束时间
	32:string superviseAccount;//监管账号
	33:string thirdBorrower;//第三人借款人
	34:string thirdBorrowerCard;//第三人借款人身份证
	35:string thirdBorrowerPhone;//第三人借款人手机
	36:string thirdBorrowerAddress;//第三人借款人地址
	37:string foreAccount;//赎楼账号
	38:string newReceAccount;//新贷款收款账号
	39:string newReceName;//新贷款收款户名
	40:string newReceBank;//新贷款收款开户行
	41:string supersionReceAccount;//资金监管收款账号
	42:string supersionReceName;//资金监管收款户名
	43:string supersionReceBank;//资金监管收款开户行
	44:string oldLoanBankBranch;//旧贷款支行
	45:string newLoanBankBranch;//新贷款支行
	46:string superviseDepartmentBranch;//监管银行支行
	47:string oldBankStr;//旧贷款银行中文
	48:string oldBankBranchStr;
	49:string newBankStr;
	50:string newBankBranchStr;
	51:string superviseDepartmentStr;
	52:string superviseDepartmentBranchStr;
	53:string accumulationFundBankStr;
	54:string newRecePerson;	//新贷款申请人
	55:string idCardNumber; //回款人身份证
    56:double downPayment;//首付款金额
    57:string turnoverCapitalName;	//周转资金户名
	58:string turnoverCapitalAccount; //周转资金账号
    59:string turnoverCapitalBank;//周转资金开户行
}


/*要件借出（新增）*/
struct ElementLend{
	1:i32 pid;//表主键
	2:i32 projectId;//项目ID
	3:i32 lendUserId;//借出人
	4:i32 orgId;//借出人部门
	5:string lendFilesId;//借出要件
	6:string returnFilesId;// 归还要件
	7:string lendTime;//借出时间
	8:string originalReturnTime;//原归还日期
	9:string actualReturnTime ;//实际归还日期
	10:string signTime;//签收时间
	11:string signUser;//签收人
	12:i32 lendState;//借出状态
	13:string remark ;//备注
	14:string updateTime;//修改时间
	15:list<string> lendFiles;//借出要件集合
	16:list<string> returnFiles;//归还要件集合
	17:i32 productId;//产品名称
	18:string projectName;//项目名称
	
	19:string actualReturnTimeEnd;//归还日期（查询条件）
	20:string lendTimeEnd ;//借出日期（查询条件）
	21:string condition;//物业或者买卖双方
	22:list<i32> userIds;//数据权限
	23:string productName;//产品名称
	24:string orgName;//部门名称
	25:string realName;//借出人姓名
	26:string porpuse;//用途
	
	27:i32 page;// 
	28:i32 rows;//
}

/*要件借出详情*/
struct ElementLendDetails{
	1:i32 pid;//主键
	2:i32 lendId;//要件借出ID
	3:i32 elementFileId;//要件ID
	4:string elementFileName;//要件名称
	5:string lendTime;//借出时间
	6:string returnTime;//归还时间
	7:i32 status;//状态
	8:i32 buyerSellerType;//买卖类型：买方=1，卖方=2
    9:string buyerSellerName;//买卖方姓名
    10:string remark;
    11:list<string> returnFileIds;
    12:string code;
}

/*出账单打印*/
struct ProjectDto{
	1:i32 projectId;
	2:string orgName;
	3:string managerName;
	4:string pmUserName;
	5:string acctName;
	6:i32 loanMoney;
	7:string sellerName;
	8:double promiseMoney;
	9:string productName;
	10:string loanDay;
	11:string paymentBank;
	12:double paymentMoney;
	13:string foreBank;
	14:string houseCardNo;
	15:string houseName;
	16:double loanFee;
	17:double poundage;
	18:string chargeSituation;//收费情况
	19:string loanRequestProcess;
	20:string projectNumber;
	21:string loanMoneyStr;
	22:string promiseMoneyStr;
	23:string paymentMoneyStr;
	24:string managerPhone;
	25:string sellerPhone;
	26:string bankUser;
	27:string bankPhone;
	28:string projectName;
	29:i32 projectSource;//项目来源：万通=1，小科=2
	30:double brokerage;//佣金
	31:i32 isNeedHandle;//
	32:string paymentName;//回款账户名称
	33:string paymentAccount;//回款账户
	34:string businessSourceStr;//业务来源
	35:string foreAccount;//赎楼账号
	
}
//赎楼资料清单
struct ProjectForeInformation{
	1:i32 pid;//主键
	2:i32 projectId;//项目ID
	3:i32 foreId;//赎楼清单ID
	4:i32 originalNumber;//原件份数
	5:i32 copyNumber;//复印件份数
	6:string remark;//备注
	7:string foreInformationName;//赎楼资料名称
}
//查询单笔上限，存入保证金等
struct OrgCooperat{
	1:i32 projectId;//项目id 
	2:double availableLimit;//授信额度
	3:double singleUpperLimit;//单笔上限
	4:double assureMoney;//启用额度
}

// Service
/*
 * 要件借出申请 Service
 */
service ElementLendService {
	// 查询所有要件借出申请
	list<Common.GridViewDTO> getAllElementLend(1:ElementLend elementLend)throws (1:Common.ThriftServiceException e);
	//查询所有要件借出申请的总数
	i32 getAllElementLendCount(1:ElementLend elementLend);
	//通过ID查询要件借出申请
	ElementLend getElementLendById(1:i32 pid);
	//新增要件借出申请
	i32 addElementLend(1:ElementLend elementLend)throws (1:Common.ThriftServiceException e);
	//修改要件借出申请信息
	i32 updateElementLend(1:ElementLend elementLend)throws (1:Common.ThriftServiceException e);
	//修改要件借出申请和详情信息
	i32 batchUpdateElementLendDetails(1:ElementLend elementLend, 2:list<ElementLendDetails> elementLendDetailsList)throws (1:Common.ThriftServiceException e);
	//修改要件借出申请和详情信息
	i32 updateElementLendDetails(1:ElementLend elementLend, 2:list<ElementLendDetails> elementLendDetailsList)throws (1:Common.ThriftServiceException e);
	//查询项目相关文件列表
	list<DataInfo> findProjectFilesByfileIds(1:i32 projectId,2:list<i32> fileIds);
	//通过主键修改借出状态
	i32 updateLendStateByPid(1:i32 pid,2:i32 lendState,3:string updateTime)throws (1:Common.ThriftServiceException e);
	list<ElementLendDetails> queryElementLendDetails(1:ElementLendDetails details);
	// 移动端查询要件借出申请
	list<GridViewMobileDto> queryElementList(1:ElementMobileDto elementMobileDto)throws (1:Common.ThriftServiceException e);
	//移动端查询要件借出申请的总数
	i32 getTotalElement(1:ElementMobileDto elementMobileDto);
	
	//查询项目相关文件列表
	list<DataInfo> findProjectFiles(1:i32 projectId);
}


/*
 * 贷款申请 Service
 */
service ProjectService {
	string addProject(1:Project project) throws (1:Common.ThriftServiceException e);
	i32 batchDelete(1:string project) throws (1:Common.ThriftServiceException e);
	i32 updateProject(1:Project project) throws (1:Common.ThriftServiceException e);
	list<Project> getAllProject(1:Project project);
	i32 getAllProjectCount(1:Project project);
	string addNewProject(1:Project project) throws (1:Common.ThriftServiceException e);//额度生成新项目
	string addExtensionProject(1:Project project) throws (1:Common.ThriftServiceException e);//展期生成新项目
	list<Repayment.RepaymentPlanBaseDTO> makeRepayMent(1:Loan loan,2:i32 userId,3:CalcOperDto calcOperDto) throws (1:Common.ThriftServiceException e);//生成还款计划表
	
	i32 editRepayMent(1:list<Repayment.RepaymentPlanBaseDTO> repayments) throws (1:Common.ThriftServiceException e);//修改还款计划表
	
	list<Repayment.RepaymentPlanBaseDTO> getRepayMents(1:i32 loanId) throws (1:Common.ThriftServiceException e);//修改还款计划表
	i32 updateRepaymentOtherCostName(1:Repayment.RepaymentPlanBaseDTO repayment)throws (1:Common.ThriftServiceException e);//修改还款计划表其他费用名称
	RepaymentLoanInfo getRepaymentLoanInfo (1:i32 loanId)throws (1:Common.ThriftServiceException e); //查询还款计划表贷款基础信息
	// 贷前的新增授信信息和项目详情
	i32 addInformation(1:Project project) throws (1:Common.ThriftServiceException e);
	// 额度的新增授信信息和项目详情
	i32 addCreditsInformation(1:Project project) throws (1:Common.ThriftServiceException e);
	
	// 保存展期基本信息
	i32 addExtensionInformation(1:Project project) throws (1:Common.ThriftServiceException e);
	
	Project getLoanProjectByProjectId(1:i32 projectId);
	//根据项目id获取担保信息
	ProjectGuarantee getGuaranteeByProjectId(1:i32 projectId);
	//根据项目id获取赎楼信息
	ProjectForeclosure getForeclosureByProjectId(1:i32 projectId);
	
	//根据项目id 查询物业买卖双方信息 add by liangyanjun
	ProjectProperty getPropertyByProjectId(1:i32 projectId);
	
	Project getProjectNameOrNumber(1:i32 projectId);
	
	//获取授信开始结束时间
	Project getProjectCredtiEndDt(1:i32 projectId);
	
	// 根据项目ID查询贷款ID
	i32 getLoanIdByProjectId(1:i32 projectId);
	// 根据项目ID查询贷款ID
	i32 getProjectIdByLoanId(1:i32  loanId)
	
	// 根据项目ID查询授信ID
	i32 getCreditIdByProjectId(1:i32 projectId);
	
 	i32 addProcedures(1:Project project)throws (1:Common.ThriftServiceException e);
 	
 	i32 updateProjectStatusByPrimaryKey(1:i32 pid,2:i32 requestStatus)throws (1:Common.ThriftServiceException e);
 	
 	list<Project> getAfterLoadFileList(1:Project project); // 归档信息查询 
 	
 	AfterLoanArchive getAfterLoadFileOne(1:i32 projectId); // 单条归档信息查询 
 	i32 updateAfterLoanArchive(1:AfterLoanArchive afterLoanArchive); // 归档信息提交查询 
 	i32 addAfterLoanArchive(1:AfterLoanArchive afterLoanArchive); // 归档信息提交查询 
 	i32 getProjectFileStatus(1:i32 projectId); // 归档信息提交查询 
 	i32 getAfterLoadFileCount(1:Project project);
 	
 	list<ProjectGuaranteeType> getGuaranteeTypeByProjectId(1:i32 projectId);// 根据项目ID查询项目的担保方式
    // 根据项目ID查询项目查询贷款信息
    Loan getLoanInfobyProjectId(1:i32 projectId)throws (1:Common.ThriftServiceException e);
    
    // 批量设置授信项目为无效
    i32 updateProjectInvalid(1:string pids)throws (1:Common.ThriftServiceException e);
    // 设置授信项目为有效
    i32 setProjectEffective(1:i32 projectId)throws (1:Common.ThriftServiceException e);
    
    Project getProjectById(1:i32 projectId)throws (1:Common.ThriftServiceException e);
    Project getProjectInfoById(1:i32 projectId)throws (1:Common.ThriftServiceException e);
    
    // 新增共同借款人
    i32 addProjectPublicMan(1:ProjectPublicMan projectPublicMan)throws (1:Common.ThriftServiceException e);
	// 批量删除共同借款人
	i32 batchDeleteProjectPublicMan(1:string userPids)throws (1:Common.ThriftServiceException e);
	
	// 获取已过期的授信ID
	string getCreaditInvalidIds(1:string creditEndDt);
	
	Project getCheckAcctProject(1: Project secProject) throws (1:Common.ThriftServiceException e);
	
	i32 updateProjectName(1:Project project) throws (1:Common.ThriftServiceException e);//修改项目名称
	string saveForeProject(1:Project project) throws (1:Common.ThriftServiceException e);//保存赎楼项目相关信息
	i32 addGuarantee(1:Project project) throws (1:Common.ThriftServiceException e);//保存项目担保信息
	list<Common.GridViewDTO> getAllForeProjects(1:Project project);//赎楼项目查询
	i32 getAllForeProjectCount(1:Project project);//查询赎楼项目总数
	bool updateProjectChechan(1:Project project) throws (1:Common.ThriftServiceException e);//项目撤单
	i32 setProjectChechan(1:string pids) throws (1:Common.ThriftServiceException e);//恢复赎楼项目撤单状态
	i32 updateProjectForeStatusByPid(1:i32 pid,2:i32 foreclosureStatus)throws (1:Common.ThriftServiceException e);
	string findProjectLoanMoney(1:i32 projectId);//查询项目贷款金额
	list<Project> findProjectInfo(1:Project project);
	i32 addForeExtensionInformation(1:Project project)throws (1:Common.ThriftServiceException e);//添加赎楼展期信息
	ProjectDto printProjectInfo(1:i32 projectId);//打印
	i32 updatecollectFileStatusByPid(1:i32 pid,2:i32 collectFileStatus)throws (1:Common.ThriftServiceException e);//修改收件状态
	i32 updaterefundFileStatusByPid(1:i32 pid,2:i32 refundFileStatus)throws (1:Common.ThriftServiceException e);//修改收件状态
	i32 cancelGuarantee(1:i32 pid,2:string cancelGuaranteeDate)throws (1:Common.ThriftServiceException e);//项目解保
	//查询赎楼清单
	list<ProjectForeInformation> queryForeInformations(1:i32 projectId);
	//新增赎楼清单
	i32 addProjectForeInformation(1:ProjectForeInformation projectForeInformation)throws (1:Common.ThriftServiceException e);
	//修改赎楼清单
	i32 updateProjectForeInformation(1:list<ProjectForeInformation> projectForeInformations)throws (1:Common.ThriftServiceException e);
	//保存审查员意见
	i32 updateAuditorOpinion(1:Project project);
	//app端的项目保存
	i32 saveProjectInfoByMobile(1:Project project,2:string param);
	//校验项目信息是否重复
	i32 checkProjectAcct(1: Project secProject) throws (1:Common.ThriftServiceException e);
	//一键保存项目信息
	string saveProjectInfo(1: Project secProject) throws (1:Common.ThriftServiceException e);
	//打印赎楼清单
	ProjectDto printForeInfo(1:i32 projectId);
	//回撤
	i32 retreatProject(1:i32 oldProjectId,2:i32 retreatUserId);	
	//万通推送业务给小科
	i32 updateProjectNeed(1:Project project);
	//修改赎楼贷后监控状态：无异常=1，有异常=2，异常转正常=3
	i32 updateForeAfterMonitorStatus(1:i32 projectId,2:i32 foreAfterMonitorStatus);
	
	//修改赎楼贷后监控状态并且修改回归正常说明：无异常=1，有异常=2，异常转正常=3
	i32 updateMonitorStatusAndReturnNormalRemark(1:i32 projectId,2:i32 foreAfterMonitorStatus,3:string returnNormalRemark);
	//修改赎楼贷后监控状态：新增=1，修改=2
	i32 updateForeAfterReportStatus(1:i32 projectId,2:i32 foreAfterReportStatus);
	//获取单笔上限等数据
	OrgCooperat getOrgCooperatById(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	
	// 修改赎楼信息（资金机构）
    i32 updateForeclosureByPartner(1:ProjectForeclosure projectForeclosure)throws (1:Common.ThriftServiceException e);
   /**
 	*根据项目ID查询项目是否处于资金放款申请中或者放款成功
 	*
 	**/
    i32 getPartnerLoanCount(1:i32 projectId);
    
    /**
 	*根据项目区域code以及业务来源查询该项目是不是有业务
 	**/
    list<Project> checkProjectByNoAndCode(1:Project project);
    i32 updateCapitalNameInfo(1:Project project,2:i32 userId);
    
}


service BeforloadOutputSerice {
	list<LoanOutputInfo> getLoadOutputList(1:i32 projectId) throws (1:Common.ThriftServiceException e);
     LoanOutputInfoImpl   getLoadOutputListImpl(1:Project project) throws (1:Common.ThriftServiceException e);
      i32   insertLoadOutputinfo(1:LoanOutputInfoImplDTO loanOutputInfoImplDTO) throws (1:Common.ThriftServiceException e);
    list<LoanOutputInfo> getLoadOutputinfoList(1:Project project) throws (1:Common.ThriftServiceException e);
     LoanOutputInfoImpl   getLoadOutputinfo(1:Project project) throws (1:Common.ThriftServiceException e);
     i32 updateLoadOutputinfo(1:LoanOutputInfoImplDTO loanOutputInfoImplDTO);
 	i32 deleteLoadOutputinfo(1:i32 pId);
    Loan updateRepaymentPlan (1:LoanOutputInfoImplDTO loanOutputInfoImplDTO) throws (1:Common.ThriftServiceException e);
       string queryloanEndDtInfo(1:i32 loanId,2:string loanOutDt) throws (1:Common.ThriftServiceException e);
         string queryloanRepayFun(1:i32 repayFunId) throws (1:Common.ThriftServiceException e);
}

service OfflineMeetingService{
	i32 saveOrganizationCommission(1:OrganizationCommission organizationCommission) throws (1:Common.ThriftServiceException e);
	//list<SysUser> obtainSysUsers() throws (1:Common.ThriftServiceException e);
	i32 saveBizProjectMeeting(1:BizProjectMeeting bizProjectMeeting) throws (1:Common.ThriftServiceException e);
	i32 saveBizProjectMeetingRST(1:BizProjectMeetingRST bizProjectMeetingRST) throws (1:Common.ThriftServiceException e);
	i32 saveBizMeetingMinutesMember(1:BizMeetingMinutesMember bizMeetingMinutesMember) throws (1:Common.ThriftServiceException e);
	i32 saveBizMeetingMinutesFile(1:BizMeetingMinutesFile bizMeetingMinutesFile) throws (1:Common.ThriftServiceException e);
	
	//初始化。进入线下贷审会会议纪要页面使用
	BizProjectMeeting obtainBizProjectMeetingByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	BizProjectMeetingRST obtainBizProjectMeetingRSTByMeetingId(1:i32 meetingId) throws (1:Common.ThriftServiceException e);
	list<BizMeetingMinutesMember> obtainBizMeetingMinutesMemberByMeetingId(1:i32 meetingId) throws (1:Common.ThriftServiceException e);
	
	//更新会议信息3张表
	i32 updateBizProjectMeeting(1:BizProjectMeeting bizProjectMeeting) throws (1:Common.ThriftServiceException e);
	i32 updateBizProjectMeetingRST(1:BizProjectMeetingRST bizProjectMeetingRST) throws (1:Common.ThriftServiceException e);
	i32 updateBizMeetingMinutesMember(1:BizMeetingMinutesMember bizMeetingMinutesMember) throws (1:Common.ThriftServiceException e);
	
	//删除会议信息3张表
	i32 deleteBizProjectMeetingByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	i32 deletteBizProjectMeetingRSTByMeetingId(1:i32 meetingId) throws (1:Common.ThriftServiceException e);
	i32 deleteBizMeetingMinutesMemberByMeetingId(1:i32 meetingId) throws (1:Common.ThriftServiceException e);
	
	//获得该项目所有会议纪要文件信息
	list<System.BizFile> obtainBizFileForOfflineMeeting(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	
	//保存贷审会信息 
	i32 saveOfflineMeetingInfo(1:BizProjectMeetingDTO bizProjectMeetingDTO) throws (1:Common.ThriftServiceException e);
	
	//修改贷审会信息
	i32 updateOfflineMeetingInfo(1:BizProjectMeetingDTO bizProjectMeetingDTO) throws (1:Common.ThriftServiceException e);
}

//放款审批单服务类
service LoanApprovalPaperService{
	LoanApprovalInvoiceInfo listApprovalInvoiceInfo(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	i32 saveBizLoanApprovalInvoice(1:BizLoanApprovalInvoice bizLoanApprovalInvoice) throws (1:Common.ThriftServiceException e);
	i32 saveBizLoanApprovalInvoiceByProjectId(1:i32 projectId,2:BizLoanApprovalInvoice bizLoanApprovalInvoice) throws (1:Common.ThriftServiceException e);
	list<System.BizFile> obtainLoanApprovalPaperFile(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	i32 obtainLoanApprovalPaperFileTotal(1:i32 projectId) throws (1:Common.ThriftServiceException e);
}

//项目归档资料
service ProjectArchiveService{
	i32 saveBizProjectArchive(1:BizProjectArchive bizProjectArchive) throws (1:Common.ThriftServiceException e);
	i32 updateBizProjectArchive(1:BizProjectArchive bizProjectArchive) throws (1:Common.ThriftServiceException e);
	BizProjectArchive obtainBizProjectArchiveByPID(1:i32 pid) throws (1:Common.ThriftServiceException e);
	list<BizProjectArchive> obtainBizProjectArchiveByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	i32 deleteBizProjectArchiveByPid(1:i32 pid) throws (1:Common.ThriftServiceException e);
	
	
	//单一项目归档资料对应的文件操作
	i32 saveBizProjectArchiveFile(1:BizProjectArchiveFile bizProjectArchiveFile) throws (1:Common.ThriftServiceException e);
	list<ProjectArchiveFileDTO> obtainBizProjectArchiveFileByArchiveId(1:i32 archiveId) throws (1:Common.ThriftServiceException e);
	ProjectArchiveFileDTO obtainProjectArchiveFileDTOByFileId(1:i32 fileId) throws (1:Common.ThriftServiceException e);
	i32 updateProjectArchiveFile(1:BizProjectArchiveFile bizProjectArchiveFile) throws (1:Common.ThriftServiceException e);
	
	
	//表BIZ_PROJECT_ARCHIVE_FILE操作
	i32 deleteBizProjectArchiveFileByArchiveId(1:i32 archiveId) throws (1:Common.ThriftServiceException e);
	i32 deleteBizProjectArchiveFileByFileId(1:i32 fileId) throws (1:Common.ThriftServiceException e);
	
	//list<System.BizFile> obtainBizProjectArchiveFileInfo(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	//list<ProjectArchiveFileDTO> obtainFileAndRemarkByArchivePid(1:i32 projectFileArchivePid) throws (1:Common.ThriftServiceException e);
	//list<BizProjectFileArchiveAndTimeDTO> obtainBizProjectFileArchiveAndTime(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	//i32 updataBizProjectFileArchive(1:BizProjectFileArchive bizProjectFileArchive) throws (1:Common.ThriftServiceException e);
	//i32 deleteProjectFileArchiveByFileId(1:i32 fileId) throws (1:Common.ThriftServiceException e);
	
}

//贷前资料上传 xuweihao
service DataUploadService{
	list<DataInfo> dataList(1:DataInfo dataInfo)throws (1:Common.ThriftServiceException e);
	
	i32 getDataTotal(1:DataInfo dataInfo)throws (1:Common.ThriftServiceException e);
	
	i32 saveData(1:DataInfo dataInfo)throws (1:Common.ThriftServiceException e);
	
	bool deleteData(1:string pidArray)throws (1:Common.ThriftServiceException e);
	
	i32 editData(1:DataInfo dataInfo)throws(1:Common.ThriftServiceException e);
	
	i32 findUserType(1:i32 projectId)throws(1:Common.ThriftServiceException e);
	
	i32 saveDataList(1:list<DataInfo> dataInfoList)throws (1:Common.ThriftServiceException e);
	//根据项目类型获取资料类型列表
	list<DataInfo> dataListByType(1:DataInfo dataInfo)throws (1:Common.ThriftServiceException e);
	//根据项目类型获取资料总数
	i32 getDataTotalByType(1:DataInfo dataInfo)throws (1:Common.ThriftServiceException e);
}
//项目担保信息Service
service ProjectGuaranteeService{
	i32 saveProjectGuarantee(1:ProjectGuarantee projectGuarantee )throws (1:Common.ThriftServiceException e);
	i32 updateProjectGuarantee(1:ProjectGuarantee projectGuarantee )throws (1:Common.ThriftServiceException e);
	ProjectGuarantee getProjectGuarantee(1:i32 projectId)throws(1:Common.ThriftServiceException e);
}
//物业双方信息Service
service ProjectPropertyService{
	i32 saveProjectProperty(1:ProjectProperty projectProperty )throws (1:Common.ThriftServiceException e);
	i32 updateProjectProperty(1:ProjectProperty projectProperty )throws (1:Common.ThriftServiceException e);
	ProjectProperty getProjectProperty(1:i32 projectId)throws(1:Common.ThriftServiceException e);
}
//赎楼信息Service
service ProjectForeclosureService{
	i32 saveProjectForeclosure(1:ProjectForeclosure projectForeclosure )throws (1:Common.ThriftServiceException e);
	i32 updateProjectForeclosure(1:ProjectForeclosure projectForeclosure )throws (1:Common.ThriftServiceException e);
	ProjectForeclosure getProjectForeclosure(1:i32 projectId)throws(1:Common.ThriftServiceException e);
}

/*
 *项目回撤关系表
 *2016-10-24 11:46:08
 **/
struct BizProjectRetreat{
	1: i32 pid;//
	2: i32 oldProjectId;//
	3: i32 newProjectId;//
	4: i32 retreatUserId;//
	5: string retreatTime;//
}
/*项目回撤关系表Service*/
service BizProjectRetreatService{
    //根据条件查询所有项目回撤关系表
	list<BizProjectRetreat> getAll(1:BizProjectRetreat bizProjectRetreat);
	//查询项目回撤关系表
	BizProjectRetreat getById(1:i32 pid);
	//新增项目回撤关系表
	i32 insert(1:BizProjectRetreat bizProjectRetreat);
	//修改项目回撤关系表
	i32 update(1:BizProjectRetreat bizProjectRetreat);
	
}


/*
 *项目物业信息表
 *2016-12-26 17:20:11
 **/
struct BizProjectEstate{
	1: i32 houseId;//主键
	2: i32 projectId;//项目ID
	3: string houseName;//物业名称
	4: double costMoney;//登记价
	5: double area;//面积
	6: string housePropertyCard;//房产证号
	7: string purpose;//用途
	8: double tranasctionMoney;//成交价
	9: double evaluationPrice;//评估价
	10: string houseProvinceCode;//省份编码
	11: string houseCityCode;//市级编码
	12: string houseDistrictCode;//区编码
	13: i32 status;//数据状态1、有效2、无效
	14: string createrDate;//创建时间
	15: i32 createrId;//创建人ID
	16: i32 updateId;//更新人ID
	17: string updateDate;//更新时间
	18: list<i32> houseIds;//物业ID集合，用于查询
	19: string houseAddress;//物业地址
	20:double evaluationNet;//评估净值
	21:string propertyRatio;//权属占比
	22:double downPayment;			//首期款金额
	23:double purchaseDeposit;		//购房定金
	24:double purchaseBalance;		//购房余款
	//房抵贷新增字段
	25:string landUse;//土地用途
	26:string estateUse;//房产用途
	27:string houseAge;//房龄
	28:string propertyLife;//产权年限
	29:string landSurplusLife;//土地剩余年限
	30:double useArea;//使用面积
	31:BizSpotInfo bizSpotInfo;//下户调查
	32: double houseRent;//物业租金
	
}
/*项目物业信息表Service*/
service BizProjectEstateService{
    //根据条件查询所有项目物业信息表
	list<BizProjectEstate> getAll(1:BizProjectEstate bizProjectEstate);
	//查询项目物业信息表
	BizProjectEstate getById(1:i32 houseId);
	//新增项目物业信息表
	i32 insert(1:BizProjectEstate bizProjectEstate);
	//修改项目物业信息表
	i32 update(1:BizProjectEstate bizProjectEstate);
	//新增项目物业信息表
	i32 originInsert(1:BizProjectEstate bizProjectEstate);
	//修改项目物业信息表
	i32 originUpdate(1:BizProjectEstate bizProjectEstate);
	//删除项目物业信息
	i32 delProjectEstate(1:list<i32> houseIds);
	//抵押贷删除项目物业信息
	i32 originDelProjectEstate(1:list<i32> houseIds);
	//在物业信息变更时变更项目信息，防止页面添加或修改物业后未保存项目信息
	i32 updateProjectByEstate(1:i32 projectId);
	//通过projectID查询物业信息
	list<BizProjectEstate> getAllByProjectId(1:i32 projectId);
	//抵押贷通过projectId查询物业信息
	list<BizProjectEstate> getAllCascadeSpotInfoByProjectId(1:i32 projectId);
	//是否已经评估
	i32 isPledgeEval(1:i32 projectId);
}

/*
 *原贷款信息
 *2017-02-06 11:18:33
 **/
struct BizOriginalLoan{
	1: i32 originalLoanId;//原贷款ID
	2: i32 projectId;//项目ID
	3: i32 loanType;//贷款类型
	4: string oldLoanBank;//原贷款银行
	5: string oldLoanBankBranch;//原贷款分行
	6: double oldLoanMoney;//原贷款金额
	7: double oldOwedAmount;//原贷款欠款金额
	8: string oldLoanTime;//原贷款结束时间
	9: string oldLoanStartTime;//原贷款开始时间
	10: string oldLoanPerson;//原贷款联系人
	11: string oldLoanPhone;//原贷款联系电话
	12: string oldLoanAccount;//原贷款账号
	13: string createrDate;//创建时间
	14: i32 createrId;//创建人
	15: i32 updateId;//修改人
	16: string updateDate;//修改时间
	17: i32 status;//数据状态1、有效2、无效
	18: list<i32> originalLoanIds;//原贷款ID集合，用于查询
	19: string loanTypeStr;//贷款类型字符串
	20: string oldLoanBankStr;//原贷款银行字符串
	21: i32 estateId;//关联的物业ID
	22: string estateName;//关联的物业名称
}
/*原贷款信息Service*/
service BizOriginalLoanService{
    //根据条件查询所有原贷款信息
	list<BizOriginalLoan> getAll(1:BizOriginalLoan bizOriginalLoan);
	//查询原贷款信息
	BizOriginalLoan getById(1:i32 pid);
	//新增原贷款信息
	i32 insert(1:BizOriginalLoan bizOriginalLoan);
	//修改原贷款信息
	i32 update(1:BizOriginalLoan bizOriginalLoan);
	//删除项目原贷款信息
	i32 delOriginalLoan(1:list<i32> originalLoanIds);
	//在原贷款信息变更时变更项目信息，防止页面添加或修改原贷款后未保存项目信息
	i32 updateProjectByOriginalLoan(1:i32 projectId);
	//通过条件查询原贷款列表信息
	list<BizOriginalLoan> getAllByCondition(1:BizOriginalLoan bizOriginalLoan);
}

/*
 *抵押评估信息
 *2017-12-14 16:57:50
 **/
struct BizMortgageEvaluation{
	1: i32 pid;//
	2: i32 estate;//
	3: double evaluationPrice;//
	4: string evaluationSource;//
	5: double evaluationProportion;//
	6: string createDate;//
	7: i32 createrId;//
	8: i32 updateId;//
	9: string updateDate;//
	//格外字段
	//项目ID
	10: i32 projectId;
	11: list<BizMortgageEvaluation> evaluations;//
	//预计下户时间
	12: string shouldSpotTime;
	
}
/*抵押评估信息Service*/
service BizMortgageEvaluationService{
    //根据条件查询所有抵押物评估信息
	list<BizMortgageEvaluation> getAll(1:BizMortgageEvaluation bizMortgageEvaluation);
	//查询抵押物评估信息
	BizMortgageEvaluation getById(1:i32 pid);
	//新增抵押物评估信息
	i32 insert(1:BizMortgageEvaluation bizMortgageEvaluation);
	//修改抵押物评估信息
	i32 update(1:BizMortgageEvaluation bizMortgageEvaluation);
	//进行抵押物评估
	i32 eval(1:BizMortgageEvaluation bizMortgageEvaluation);

}

/*
 *下户调查信息
 *2017-12-15 15:01:07
 **/
struct BizSpotInfo{
	1: i32 pid;//
	2: i32 projectId;//
	3: i32 eastateId;//
	4: string shouldSpotTime;//
	5: string actualSpotTime;//
	6: string decorationSituation;//
	7: i32 houseTotals;//
	8: i32 floorTotals;//
	9: string houseStructure;//
	10: i32 elevatorsNums;//
	11: i32 householdNums;//
	12: string housePattern;//
	13: i32 shoppingNums;//
	14: i32 schoolNums;//
	15: i32 bankNums;//
	16: i32 hospitalNums;//
	17: string trafficSituation;//
	18: string aroundEnvironmental;//
	19: double agencyPrice;//
	20: double totalPrice;//
	21: string useSituation;//
	22: string actualPurpose;//
	23: string userType;//
	24: string userSituation;//
	25: string leaseUse;//
	26: string updateDate;//
	27: string leaseTimeStart;//
	28: string leaseTimeEnd;//
	29: string lessee;//
	30: string othenRemark;//
	31: string createDate;//
	32: i32 createrId;//
	33: i32 updateId;//
	34: list<BizSpotInfo> spotInfos;//
	35: list<BizSpotFile> spotFiles;
	36: string housePatternDetail;
	37: string rentPaymentWay;
	38: string leaseUsePhone;
	39: double rentPrice;
	40: double rentEvaluationPrice;
	41: string evaluationSource;
	42: string evaluationSourceDetail;
	43: double vacantDays;
	44: double vacantRate;
}
/*下户调查信息Service*/
service BizSpotInfoService{
    //根据条件查询所有下户调查信息
	list<BizSpotInfo> getAll(1:BizSpotInfo bizSpotInfo);
	//查询下户调查信息
	BizSpotInfo getById(1:i32 pid);
	//新增下户调查信息
	i32 insert(1:BizSpotInfo bizSpotInfo);
	//修改下户调查信息
	i32 update(1:BizSpotInfo bizSpotInfo);
	//批量新增
	i32 save(1:list<BizSpotInfo> spotInfos);
	//是否已下户调查
	i32 isSave(1:i32 projectId);
}

/*
 *下户调查文件信息
 *2017-12-18 18:07:14
 **/
struct BizSpotFile{
	1: i32 pid;//
	2: i32 spotId;//
	3: i32 fileId;//
	4: i32 status;//
	//额外字段
	//文件名称
	5: string fileName;
	6: string fileUrl;
	7: string fileType;
	8: i32 page;
	9: i32 rows;
}

/*下户调查信息Service*/
service BizSpotFileService{
    //根据条件查询所有下户调查信息
	list<BizSpotFile> getAll(1:BizSpotFile bizSpotFile);
    //根据条件查询所有下户调查信息记录
    i32 getAllCount(1:BizSpotFile bizSpotFile);
	//查询下户调查信息
	BizSpotFile getById(1:i32 pid);
	//新增下户调查信息
	i32 insert(1:BizSpotFile bizSpotFile);
	//修改下户调查信息
	i32 update(1:BizSpotFile bizSpotFile);
	//根据ID删除
	i32 deleteById(1:BizSpotFile bizSpotFile);
	//根据IDs删除
	i32 deleteByIds(1:list<string> ids);
}
