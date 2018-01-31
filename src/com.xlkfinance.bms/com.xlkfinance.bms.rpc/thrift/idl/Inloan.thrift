namespace java com.xlkfinance.bms.rpc.inloan
include "System.thrift"
include "Common.thrift"
include "File.thrift"
include "MortgageLoan.thrift"


/*财务受理首页列表*/
struct FinanceIndexDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: string projectNumber;//项目编号
    4: string projectName;//项目名称
    5: i32 status;//收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4
    6: i32 createrId;//创建者ID
    7: list<i32> userIds;//
    8: i32 page=1; //第几页
	9: i32 rows=10; //总页数
	10: string createrDate;//创建时间
	11: list<i32> statusList;//
	12: i32 houseClerkId;//
	13: string houseClerkName;//赎楼员
	14: double realLoan;//放款金额
    15: double loanMoney;//借款金额
	16: i32 projectStatus;//项目状态
	17: i32 type;//类型：财务收费=1，财务放款=2
	18: i32 checkDocumentApprovalStatus;//审批状态：未审批=1，不通过=2，通过=3
	19: i32 projectSource;//项目来源（1万通2小科）
	20: i32 projectType;//项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4  授信and贷款=5）
	21: double extensionFee;//展期费
	22: i32 bizHandleId;//业务办理ID
	23: i32 foreclosureStatus; //赎楼状态：未赎楼=1，已赎楼=2，已驳回=3
	24: string foreclosureTurnDownRemark;//赎楼驳回备注
	25: i32 checkLitigationApprovalStatus;//查诉讼审批状态：未审批=1,不通过=2,通过=3,重新查诉讼=4
	26: i32 reCheckStatus;//查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4
	27: string houseName;//物业名称
	28: string buyerName;//买方姓名
	29: string sellerName;//卖方姓名
	30: i32 isChechan;//是否撤单:是=1，否=0
	31: i32 makeLoansApplyFinanceId;//申请财务受理信息的第一次放款id
	32: i32 makeLoansApplyFinanceApplyStatus;//申请财务受理信息的第一次放款的申请状态
	33: i32 isNeedHandle;//是否需要业务办理（该字段在体系外会有值，如果为1，则需要赎楼等办理）
	34: string accountManager;//客户经理
	35: string accountDepartment;//所属部门
	36: string orgLoanDate;//机构（第三方平台）放款时间
	37: i32 queryUserSource;//当前查询用户来源：万通用户=1，小科用户=2
	38: i32 isNeedFinancial;//是否需要小科资金 1不需要2需要（万通推单新增字段）
	39: string productName;//产品名称
	40: i32 makeApplyFinanceIdTwo;//申请财务受理信息的第二次放款id
	41: string productNumber; //产品编码
	42: i32 makeApplyStatusTwo;//申请财务受理信息的第二次放款的申请状态
	43: i32 productId;//产品Id
	44: i32 collectFeeStatus;//收费状态：未收费=1，已收费=2
	45: i32 collectFeeType;//(赎楼贷)收费方式:贷前收费=1，贷后收费=2。（房抵贷）收费节点：下户前收费=1，放款前收费=2，任意节点收费=3
	46: double poundage;//手续费
	47: i32 mortgageStatus;//抵押状态:1:是    2:否
}
/*财务受理主表*/
struct FinanceHandleDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: string projectNumber;//项目编号
    4: string projectName;//项目名称
    5: i32 status;//收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4
    6: i32 createrId;//创建者ID
    7: list<i32> userIds;//
    8: i32 page=1; //第几页
	9: i32 rows=10; //总页数
	10: string createrDate;//创建时间
	11: list<i32> statusList;//
	12: i32 houseClerkId;//
	13: string houseClerkName;//赎楼员
	14: i32 collectFeeStatus;//收费状态：未收费=1，已收费=2
}
/*申请财务受理信息*/
struct ApplyFinanceHandleDTO{
    1: i32 pid;//主键
    2: i32 financeHandleId;//财务受理ID
    5: i32 recPro;//收款项目
    6: double recMoney;//收款金额
    7: string recAccount;//收款账号
    8: string recDate;//收款日期
    9: string resource;//款项来源（id）
    10: string operator;//操作员
    11: string remark;//备注
    12: i32 createrId;//创建者ID
    13: list<i32> userIds;//
    14: i32 page=1; //第几页
	15: i32 rows=1000; //总页数
	16:double poundage;//手续费（仅作数据传输）
	17:double brokerage;//佣金（仅作数据传输）
	18:double interest;//利息（仅作数据传输）
	19:i32 projectId;//项目id（仅作数据传输）
	20:list<i32> recProList;//
	21:double extensionFee;//展期费（仅作数据传输）
	22:string updateDate;//更新时间
	23: string resourceStr;//款项来源(值)
	24: double resourceMoney;//款项来源1：金额
	25: string resource2;//款项来源2(id)
	26: string resourceStr2;//款项来源2(值)
	27: double resourceMoney2;//款项来源2：金额
	28: i32 applyStatus;//申请状态：未申请=1,申请中=2,驳回=3,审核中=4,审核通过=5,审核不通过=6,已归档=7
	29: string fundManagerRemark;//资金主管审批意见
	30: string financeDirectorRemark;//财务总监审批意见
	31:double feeRate;//费率（仅作数据传输）
	32:i32 projectType;//
	
}
/*
 *财务收费历史表
 *2017-03-23 14:30:39
 **/
struct CollectFeeHis{
	1: i32 pid;//
	2: i32 projectId;//
	3: double consultingFee;//咨询费
	4: double poundage;//手续费
	5: double brokerage;//佣金
	6: string recDate;//收费日期
	7: string proResource;//款项来源
	8: i32 createrId;//操作人
	9: string createrDate;//操作日期
	10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
	12: string createrName;//操作人
	13: i32 financeHandleId;//财务受理ID
	14: string proResourcelookupVal;//款项来源字典（区分万通和小科）
	15: string recAccount;//收款账号
	16: i32 recPro;//收费项目参考BIZ_LOAN_APPLY_FINANCE_HANDLE.rec_pro
	17: string recProStr;//收费项目,字典值
	
}
/*财务收费历史表Service*/
service CollectFeeHisService{
    //根据条件查询所有财务收费历史表
	list<CollectFeeHis> getAll(1:CollectFeeHis collectFeeHis);
	//查询财务收费历史表
	CollectFeeHis getById(1:i32 pid);
	//新增财务收费历史表
	i32 insert(1:CollectFeeHis collectFeeHis);
	//修改财务收费历史表
	i32 update(1:CollectFeeHis collectFeeHis);
	//删除财务收费历史表
	i32 deleteById(1:i32 pid);
	//批量删除财务收费历史表
	i32 deleteByIds(1:list<i32> pids);
	//财务收费历史列表(分页查询)
	list<CollectFeeHis> queryCollectFeeHis(1:CollectFeeHis query) ;
	//财务收费历史列表总数
	i32 getCollectFeeHisTotal(1:CollectFeeHis query) ;
	//查询收费历史金额总和(咨询费,手续费,佣金)
	CollectFeeHis getCollectFeeHisMoney(1:CollectFeeHis query);
}
/*
 *放款挂起记录表
 *2017-03-28 09:59:43
 **/
struct LoanSuspendRecord{
	1: i32 pid;//
	2: i32 projectId;//项目ID
	3: string startDate;//开始日期
	4: string endDate;//结束日期
	5: i32 suspendDay;//挂起天数
	6: i32 createrId;//操作人
	7: string createrDate;//操作日期
	8: string remark;//备注
	9: string createrName;//创建人姓名
}
/*放款挂起记录表Service*/
service LoanSuspendRecordService{
    //根据条件查询所有放款挂起记录表
	list<LoanSuspendRecord> getAll(1:LoanSuspendRecord loanSuspendRecord);
	//查询放款挂起记录表
	LoanSuspendRecord getById(1:i32 pid);
	//新增放款挂起记录表
	i32 insert(1:LoanSuspendRecord loanSuspendRecord);
	//放款挂起
	i32 loanSuspendInfo(1:LoanSuspendRecord loanSuspendRecord);
}
//业务办理模块-----------------------start
/*业务受理列表*/
struct ApplyHandleIndexDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: string projectNumber;//项目编号
    4: string projectName;//项目名称
    5: i32 projectStatus;//项目状态
    6: string projectPassDate;//项目审批通过时间
    7: i32 recStatus;// 放款到帐状态：未到账=1，已到账=2
    8: i32 applyHandleStatus;// 申请办理状态
    9: i32 createrId;//创建者ID
    10: list<i32> userIds;//
    11: i32 page=1; //第几页
	12: i32 rows=10; //总页数
	13: string feedback;//办理反馈（仅作查询,表字段BIZ_LOAN_APPLY_HANDLE_INFO.FEEDBACK） 
	14: string handleDate;//办理时间（仅作查询,表字段BIZ_LOAN_APPLY_HANDLE_INFO.HANDLE_DATE） 
	15: string taskName;//办理动态任务节点（仅作查询,表字段ACT_RU_TASK.NAME_） 
	16: string productName;//产品名称（仅作查询,表字段BIZ_PRODUCT.PRODUCT_NAME） 
	17: double loanMoney;//贷款金额（仅作查询,表字段BIZ_PROJECT_GUARANTEE.LOAN_MONEY） 
	18: string createrDate;//创建时间
	19: i32 workFlowStatus;//办理动态工作流状态：有效=1，无效=-1
	20: i32 productId;//产品id
	21: string taskUserName; //任务处理人用户名
	22: i32 handleId; //业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
	23: i32 recFeeStatus; //收费状态：未收费=1，已收费=2，已放款=3
    24: string houseName;//物业名称
	25: string buyerName;//买方姓名
	26: string sellerName;//卖方姓名
	27: i32 isChechan;//是否撤单:是=1，否=0
	28:string oldLoanBank;//原贷款银行 
    29:string oldLoanBankBranch;//原贷款支行
    30:i32 isNeedHandle;//
    31:string pmUserName;//客户经理
}
/*业务处理信息 表：BIZ_LOAN_HANDLE_INFO*/
struct HandleInfoDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: i32 applyHandleStatus;//业务申请办理状态：未申请=1，已申请=2，已完成（已解保）=3，已归档=4
    4: i32 recStatus;//放款到帐状态：未到账=1，已到账=2
    5: i32 createrId;//创建者ID
    6: string createrDate;//创建时间
    7: list<i32> userIds;//
    8: i32 page=1; //第几页
	9: i32 rows=10; //总页数
	10: i32 foreclosureStatus; //赎楼状态：未赎楼=1，已赎楼=2，已驳回=3
	11: string foreclosureTurnDownRemark; //赎楼驳回备注
}

/*申请业务处理信息 表：APPLY_HANDLE_INFO*/
struct ApplyHandleInfoDTO{
    1: i32 pid;//主键
    2: i32 handleId;//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
    3: string subDate;//合同及放款确认书提交时间
    4: string contactPerson;//联系人
    5: string contactPhone;//联系电话
    6: string handleDate;//办理时间
    7: string specialCase;//特殊情况说明
    8: string feedback;//办理反馈
    9: string remark;//备注
    10: i32 createrId;//创建者ID
    11: list<i32> userIds;//
    12: i32 page=1; //第几页
	13: i32 rows=10; //总页数
	14:i32 projectId;//项目ID
}

/*赎楼及余额回转信息 表：BIZ_LOAN_HOUSE_BALANCE*/
struct HouseBalanceDTO{
    1: i32 pid;//主键
    2: i32 handleId;//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
    3: double principal;//赎楼本金（付款金额）
    4: string payDate;//付款日期
    5: string houseClerk;//赎楼员
    6: double interest;//利息
    7: double defaultInterest;//罚息
    8: double balance;//赎楼余额
    9: string backAccount;//转回账号
    10: i32 count;//赎楼次数
    11: string remark;//备注
    12: i32 createrId;//创建者ID
    13: list<i32> userIds;//
    14: i32 page=1; //第几页
	15: i32 rows=10; //总页数
	16: i32 balanceConfirm; //余额确认：未确认=1，已确认=2
	17: i32 handleUserId; //办理人Id
	18: string handleUserName; //办理人姓名
	19: i32 projectId; //
	20: string createrName;//创建者
	21: string createrDate;//创建时间
	22: double loanAmount;//出款金额
	23: string loanTime;//出款日期
}

/*财务退款明细 表：BIZ_LOAN_REFUND_DETAILS*/
struct RefundDetailsDTO{
    1: i32 pid;//主键
    2: i32 handleId;//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
    3: i32 refundPro;//退款项目
    4: double refundMoney;//退款金额
    5: string recAccount;//收款账号
    6: string recName;//收款户名
    7: string payDate;//付款日期
    8: string operator;//操作员
    9: i32 createrId;//创建者ID
    10: list<i32> userIds;//
    11: i32 page=1; //第几页
	12: i32 rows=10; //总页数
}

/*办理流程条目 表：BIZ_LOAN_HANDLE_FLOW*/
struct HandleFlowDTO{
    1: i32 pid;//主键
    2: string name;//办理流程名称
    3: i32 fixDay;//规定天数
    4: i32 advanceNoticeDay;//提前通知天数
    5: i32 noticeType;//提醒类型：有差异提醒备注一次=1，有差异每天都提醒写备注直到完成=2
    6: i32 oneLevel;//一级预警
    7: i32 twoLevel;//二级预警
    8: i32 threeLevel;//三级预警
    9: bool canHandle=false;//是否可处理该办理流程（该变量用作页面控制，控制这条办理流程当前登录用户是否可以进行办理）
    10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
}

/*办理动态 表：BIZ_LOAN_HANDLE_DYNAMIC*/
struct HandleDynamicDTO{
    1: i32 pid;//主键
    2: i32 handleId;//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
    3: i32 handleFlowId;//办理流程条目ID（关联BIZ_LOAN_HANDLE_FLOW主键）
    4: string finishDate;//完成日期
    5: i32 handleDay;//操作天数
    6: string operator;//操作人
    7: i32 differ;//差异
    8: i32 differMonitorCount;//差异跟进次数
    9: string createDate;//创建时间
    10: string remark;//备注
    11: i32 createrId;//创建者ID
    12: list<i32> userIds;//
    13: i32 page=1; //第几页
	14: i32 rows=10; //总页数
	15: string taskUserName; //任务处理人用户名
	16: i32 fixDay;//规定天数
	17: i32 currentHandleUserId;//该办理动态的办理人
	18: string currentHandleUserName;//该办理动态的办理人姓名
}
/*业务办理和任务关联 表：BIZ_HANDLE_WORKFLOW*/
struct BizHandleWorkflowDTO{
    1: i32 pid;//主键
    2: i32 handleId;//赎楼业务处理ID（关联BIZ_LOAN_HANDLE_INFO主键）
    3: string executionId;//流程实例ID（关联ACT_RU_EXECUTION主键）
    4: string taskId;//任务ID（仅作查询使用）
    5: i32 status;//状态：有效=1，无效=-1
    6: i32 page=1; //第几页
	7: i32 rows=10; //总页数
	8: string userName;//任务处理人（仅作查询使用）
	9: i32 projectId;//项目id
}

/*差异预警处理表 表：BIZ_LOAN_HANDLE_DIFFER_WARN*/
struct HandleDifferWarnDTO{
    1: i32 pid;//主键
    2: i32 handleDynamicId;//办理动态ID（关联BIZ_LOAN_HANDLE_DYNAMIC主键）
    3: i32 differ;//差异
    4: i32 status;//状态：失效=-1,未处理=1，已处理=2
    5: string handleDate;//处理时间
    6: i32 handleAuthor;//处理者id
    7: string createDate;//创建时间
    8: string remark;//备注
    9: list<i32> userIds;//
    10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
	12: i32 projectId; //项目id
	13: string projectName; //项目名称
	14: string flowName; //办理流程条目名称(仅作查询,表字段BIZ_LOAN_HANDLE_FLOW.NAME)
	15: string handleAuthorName;//处理者姓名
	16: i32 handleType;//处理类型：必须处理=1，非必须处理=2
	17: i32 flowId;//办理流程条目ID
    18: list<i32> handleTypeList;	//处理类型列表
}
struct HandleDifferWarnIndexDTO{
    1: i32 pid;//主键
    2: i32 handleDynamicId;//办理动态ID（关联BIZ_LOAN_HANDLE_DYNAMIC主键）
    3: i32 differ;//差异
    4: i32 status;//状态：失效=-1,未处理=1，已处理=2
    5: string handleDate;//处理时间
    6: i32 handleAuthor;//处理者id
    7: string createDate;//创建时间
    8: string remark;//备注
    9: list<i32> userIds;//
    10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
	12: i32 projectId; //项目id
	13: string projectName; //项目名称
	14: string flowName; //办理流程条目名称(仅作查询,表字段BIZ_LOAN_HANDLE_FLOW.NAME)
	15: string handleAuthorName;//处理者姓名
	16: i32 fixDay;//固定天数
	17: string projectNumber;//项目编号
	18: string houseName;//物业名称
	19: string buyerName;//买方姓名
	20: string sellerName;//卖方姓名
	21: string pmUserName;//客户经理
}

/*办理动态文件关联 表：BIZ_LOAN_HANDLE_DYNAMIC_FILE*/
struct HandleDynamicFileDTO{
    1: i32 pid;//主键
    2: i32 handleDynamicId;//办理动态ID（关联BIZ_LOAN_HANDLE_DYNAMIC主键）
    3: i32 fileId;//文件id
    4: i32 status;//状态：有效=1，无效=-1
    5: string remark;//备注（仅作查询,表字段BIZ_FILE.REMARK） 
    6: i32 uploadUserId;//上传用户ID（仅作查询,表字段BIZ_FILE.UPLOAD_USER_ID） 
    7: list<i32> userIds;// 
    8: string fileName;//文件名（仅作查询,表字段BIZ_FILE.FILE_NAME） 
    9: string fileType;//文件类型 （仅作查询,表字段BIZ_FILE.FILE_TYPE） 
    10: i32 fileSize;//文件大小 （仅作查询,表字段BIZ_FILE.FILE_SIZE） 
    11: string fileUrl;//文件路径 （仅作查询,表字段BIZ_FILE.FILE_URL） 
    12: i32 handleId;//业务办理主表ID（仅作数据传输,检验办理动态权限时用到）
    13: i32 page=1; //第几页
	14: i32 rows=10; //总页数
	15: string uploadUserName; //上传用户名
}
struct HandleDynamicMap{
    1: i32 count;
    2: string dynamicName;
}
//业务办理模块-----------------------end
/*退款首页列表*/
struct RefundFeeIndexDTO{
	1: i32 pid;//主键
	2: i32 projectId;//项目id
	3: string projectNumber;//项目编号
	4: string projectName;//项目名称
	5: i32 bizApplyHandleStatus;//业务办理状态
	6: i32 recStatus;//收款状态
	7: i32 backFeeApplyHandleStatus;//退款申请状态:未申请=1，申请中=2，待审核=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
	8: string cancelGuaranteeDate;//解保日期（回款时间）
	9: list<i32> userIds;// 
	10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
	12: string customerName; //借款申请人姓名
	13: string oldHome; //原业主
	14: string projectPassDate; //贷前审批通过时间（取财务受理的创建时间，因为贷前审批一完成，就会添加财务受理信息）
	15: i32 type; //退款类型：退手续费=1，退利息=2，退款=3，退佣金=4
	16: double loanMoney; //贷款金额
	17: i32 productType; //产品类型(1:信用 2:赎楼)
	18: i32 productId; //产品ID
	19: string productName; //产品名称
	20: string requestDate; //贷款申请时间
	21: list<i32> backFeeApplyHandleStatusList;// 退款申请状态集合:未申请=1，申请中=2，待审核=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
	22: double confirmMoney;//确认金额
    23: i32 isConfirm;//退款确认：未确认=1，已确认=2
    24: string confirmDate;//确认日期
    
    25: double returnFee;//退款金额
    
    26: string houseName;//物业名称
	27: string buyerName;//买方姓名
	28: string sellerName;//卖方姓名
    29: i32 isChechan;//是否撤单:是=1，否=0
    30: i32 pmUserId;//客户经理
    31: i32 orgId;//部门
    32: string startDate;//开始日期
    33: string endDate;//结束日期
    34: i32 businessSourceNo;//业务来源数据
    35: i32 businessCatelog;//业务类别
    36: string orgName;//业务部门
    37: string pmName;//客户经理
    38: string recAccountName;//收费账户
    39: double guaranteeFee;//担保费
    40: double receMoney;//应收担保费
    41: double finPoundage;//手续费
    42: i32 businessSource;//业务来源
    43: i32 innerOrOut;//内外单类型
    44: string InnerOrOutName;//内外单名
    45: string businessSourceName;//业务来源名
    46: string bizApplyHandleStatusName;//业务状态名
    47: string backFeeApplyHandleStatusName;//退费状态名
    48: double foreclosureFloorMoney;//赎楼金额
    49: double finInterest;//实收利息
    50: string bankName;//开户行
    51: i32 batchRefundFeeMainId;//批量退费主表id
    52: string batchName;//批量名称
    53: string businessSourceStr;//业务来源
    54: string repaymentDate;//回款日期
    55: string repaymentDateEnd;//回款日期（范围查询）
    56: double repaymentMoney;//回款金额
    57: double notRepaymentMoney;//未回款金额
    58: double extensionFee;//已收展期费
}
/*退费map，用来做批量退费的数据传输*/
struct RefundFeeMap{
  1: i32 pid;//退费表ID
  2: i32 projectId;//项目ID
  3: double money;//退费金额
}
/*退款 表：BIZ_LOAN_REFUND_FEE*/
struct RefundFeeDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: i32 applyStatus;//申请状态：未申请=1，申请中=2，待审核=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
    5: i32 productId;//产品类型
    6: string tradeWay;//交易方式
    7: string tradeDate;//交易日期
    8: string mortgageNumber;//抵押编号
    9: i32 customerId;//借款申请人
    10: string oldHome;//原业主
    11: i32 pmUserId;//经办人
    12: string homeName;//房产名称
    13: double guaranteeMoney;//担保金额(贷款金额)
    14: double extractMoney;//出账金额(财务受理的放款金额)
    15: string lendBank;//放款银行
    16: string foreclosureFloorBank;//赎楼银行
    17: double bankLendTotalAmount;//银行放款金额合计
    18: i32 deptId;//业务部门
    19: double recGuaranteeMoney;//已收利息(财务受理的利息费)
    20: double foreclosureFloorMoney;//赎楼金额本金
    21: double defaultInterest;//罚息
    22: double payTotal;//付款合计
    23: double returnFee;//退费金额
    24: string recAccountName;//收款人户名
    25: string bankName;//开户行
    26: string recAccount;//收款人账号
    27: i32 page=1; //第几页
	28: i32 rows=10; //总页数
	29: list<i32> userIds;// 
    30: string createrDate;//创建时间
    31: i32 createrId;//创建人id
    32: i32 updateId;//更新人id
    33: string updateDate;//更新时间
    34: string projectName;//项目名称（仅作查询,表字段BIZ_PROJECT.PROJECT_NAME） 
    35: string projectNumber;//项目编号（仅作查询,表字段BIZ_PROJECT.PROJECT_NUMBER） 
    36: string productName;//产品类型
    37: string customerName;//借款申请人
    38: string pmUserName;//经办人
    39: string deptName;//业务部门
    40: double interest;//利息
    41:i32 foreclosureStatus;//赎楼申请状态1、待客户经理提交2、待部门经理审批3、待业务总监审批4、待审查员审批5、待风控初审6、待风控复审7、待风控终审8、待风控总监审批9、待总经理审批10、已审批11、已放款12、业务办理已完成13、已归档
   
    42: string recDate;//收费日期
    43: double recMoney;//收费金额
    44: string cancelGuaranteeDate;//解保日期（回款时间）
    45: string payDate;//出款日期
 
    46: double bankLendTotalMoney;//银行放款金额合计
    47: i32 type;//
    
    48: double confirmMoney;//确认金额
    49: i32 isConfirm;//退款确认：未确认=1，已确认=2
    50: string confirmDate;//确认日期
    51: i32 realUseDays;//实际用款天数
    52: double repaymentMoney;//回款金额
    53: double feeRate;//费率
    54: string repaymentDate;//回款日期
    55: list<i32> projectList;//项目id集合
    56: list<RefundFeeMap> batchRefundFeeMapList;//批量退费集合（收费金额和项目对应）
    57: i32 batchRefundFeeMainId;//批量id
    58: string batchName;//批量名称
}


/*赎楼列表 */
struct ForeclosureIndexDTO{
    1: i32 bizHandleId;//业务主表id
    2: i32 projectId;//项目ID
    3: string projectNumber;//项目编号
    4: string projectName;//项目名称
    5: i32 recFeeStatus;//收费状态：未收费=1，已收费=2，已放款=3
    6: list<i32> userIds;//
    7: i32 page=1; //第几页
	8: i32 rows=10; //总页数
	9: list<i32> recFeeStatusList;//
	10:i32 houseClerkId;//
	11:string houseClerkName;//赎楼员
	12:i32 foreclosureStatus; //赎楼状态：未赎楼=1，已赎楼=2，已驳回=3(BIZ_LOAN_HANDLE_INFO.foreclosure_status)
	13:i32 balanceConfirm; //余额确认：未确认=1，已确认=2(BIZ_LOAN_HOUSE_BALANCE.balance_confirm)
	14:double realLoan;//放款金额合计（财务受理的）
	15:double balance;//未收余款合计
	16:string foreclosureTurnDownRemark;//赎楼驳回原因
	17:double oneForeclosureMoney;//第一次赎楼金额
	18:double twoForeclosureMoney;//第二次赎楼金额
	19:double foreclosureMoney;//赎楼金额合计
	
	20:double oneRealLoan;//第一次放款金额（财务受理的）
	21:double twoRealLoan;//第二次放款金额（财务受理的）
	22:double oneBalance;//第一次赎楼未收余款
	23:double twoBalance;//第二次赎楼未收余款
	24:i32 projectSource;//项目来源（1万通2小科）
	25: string houseName;//物业名称
	26: string buyerName;//买方姓名
	27: string sellerName;//卖方姓名
	28: string payDate;//赎楼日期
	29: string fromPayDate;//赎楼开始日期(仅做查询)
	30: string endPayDate;//赎楼结束日期(仅做查询)
	31: string handleUserName;//办理人姓名
	32: i32 productId;//产品Id
	33: string productName;//产品名称
	
}
/*余款确认 */
struct BalanceConfirmDTO{
    1:i32 handleId;//业务主表id
	2:double firstBalance;//一次赎楼余额
	3:string firstBackAccount;//一次赎楼余额转回帐号
	4:double secondBalance;//二次赎楼余额
	5:string secondBackAccount;//二次赎楼余额转回帐号
}
struct CollectFileMap{
    1: string code;//
    2: string remark;//备注
}
/*收件 表：BIZ_COLLECT_FILES*/
struct CollectFileDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: i32 status;//状态：有效=-1，失效=1
    4: string code;//
    5: string name;//
    6: string remark;//备注
    7: string createrDate;//
    8: i32 createrId;//创建人id
    9: i32 updateId;//更新人id
    10: string updateDate;//更新时间
    11: i32 page=1; //第几页
	12: i32 rows=10; //总页数
	13: list<i32> userIds;// 
	14: list<CollectFileMap> collectFileMapList;// 收件集合
    15: string collectDate;//收件时间
    16: i32 buyerSellerType;//买卖类型：买方=1，卖方=2
    17: string buyerSellerName;//买卖方姓名
    18: string refundDate;//退件时间
    19: i32 refundStatus;//退件状态：未退件=1，已退件=2
    20: i32 refundFinishStatus;//退件完结状态：未完结=1，已完结=2
}
/*要件打印信息*/
struct CollectFilePrintInfo{ 
    1: i32 projectId;//项目ID
    2: string projectName;//项目名
    3: string deptName;//部门
    4: string pmName;//经办人
    5: i32 buyerSellerType;//买卖类型：买方=1，卖方=2
    6: string buyerName;//买方姓名
    7: string sellerName;//卖方姓名
    8: string houseName;//物业名称
    9: double loanMoney;//贷款金额
    10: double realLoanMoney;//放款金额
    11: double foreclosureMoney;//赎楼金额
    12: double refundTailMoney;//退尾款金额
	13: list<CollectFileDTO> collectFileList;//
	14: i32 printType;//打印类型：收件打印=1，退要件打印=2
	15: string printLoanMoney;//物业名称

}
/*要件查档表：BIZ_CHECK_DOCUMENT*/
struct CheckDocumentDTO{
    1: i32 pid;//主键
    3: i32 projectId;//项目ID
    4: i32 checkStatus;//查档状态：未查档=1，抵押=2，有效=3，无效=4，查封=5，抵押查封=6，轮候查封=7
    5: i32 approvalStatus;//审批状态：未审批=1，不通过=2，通过=3
    6: string checkDate;//查档时间
    7: string remark;//审批意见
    8: i32 attachmentId;//附件id
    9: i32 createrId;//创建人id
    10: i32 updateId;//更新人id
    11: string updateDate;//更新时间
    12: string createrDate;//创建时间
    13: i32 page=1; //第几页
	14: i32 rows=10; //总页数
	15: list<i32> userIds;// 
	16: i32 reCheckStatus;//查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4
	17: string reCheckRemark;//查档复核备注(BIZ_CHECK_DOCUMENT.RE_CHECK_REMARK)
	18: string reCheckDate;//查档复核时间
	19: i32 reCheckUserId;//查档复核人id
	20: string reCheckUserName;//查档复核人姓名
	21: string updateName;//更新人
	22: i32 houseId; //物业id
	23: i32 checkWay; //查档方式：1=人工查询,2=系统查询
	24: string createrName;//创建人姓名
	25: string houseName;//物业名称
	26: string housePropertyCard;//房产证号
	27: string checkStatusStr;//查档状态字符串
	28: string checkHours;//查档精确到时
}
/*查诉讼表：BIZ_CHECK_LITIGATION*/
struct CheckLitigationDTO{
    1: i32 pid;//主键
    3: i32 projectId;//项目ID
    4: i32 checkStatus;//诉讼状态：无新增诉讼=1，有新增诉讼非本人=2
    5: i32 approvalStatus;//审批状态：未审批=1，不通过=2，通过=3
    6: string checkDate;//查法院网时间
    7: string remark;//审批意见
    8: i32 attachmentId;//附件id
    9: i32 createrId;//创建人id
    10: i32 updateId;//更新人id
    11: string updateDate;//更新时间
    12: string createrDate;//创建时间
    13: i32 page=1; //第几页
	14: i32 rows=10; //总页数
	15: list<i32> userIds;// 
    16: i32 checkWay; //查档方式：1=人工查询,2=系统查询
    17: string createrName;//创建人姓名
    18: string checkHours;//查档精确到时
}
/*要件查档首页列表*/
struct CheckDocumentIndexDTO{
    1: i32 checkDocumentId;//要件查档ID
    3: i32 projectId;//项目ID
    4: i32 checkStatus;//查档状态：未查档=1,抵押=2,有效=3,无效=4,查封=5,抵押查封=6,轮候查封=7
    5: i32 approvalStatus;//未审批=1,不通过=2,通过=3,重新查档=4
    6: string checkDate;//查档时间
    7: string remark;//审批意见
    8: i32 attachmentId;//附件id
    9: i32 createrId;//创建人id
    10: i32 updateId;//更新人id
    11: string updateDate;//更新时间
    12: i32 page=1; //第几页
	13: i32 rows=10; //总页数
	14: list<i32> userIds;// 
	15: string projectNumber;//项目编号
    16: string projectName;//项目名称
    17: i32 recFeeStatus;//收费状态：未收费=1，已收费=2，已放款=3
    18: string createrDate;//创建时间
    19: string collectFileDate;//收件时间（BIZ_COLLECT_FILES.UPDATE_DATE）
    20: string collectFileRemark;//收件备注（BIZ_COLLECT_FILES.REMARK）
    21: i32 collectFileStatus;//收件状态，未收件=1，已收件=2（BIZ_PROJECT.COLLECT_FILE_STATUS）
    22: i32 checkLitigationId;//查诉讼id
    23: i32 checkLitigationStatus;//查诉讼状态：未查诉讼=1,无新增诉讼=2,有增诉讼=3,有新增诉讼非本人=4
    24: i32 checkLitigationApprovalStatus;//查诉讼审批状态：未审批=1，不通过=2，通过=3
    25: i32 projectForeclosureStatus;//赎楼申请状态1、待客户经理提交2、待部门经理审批3、待业务总监审批4、待审查员审批5、待风控初审6、待风控复审7、待风控终审8、待风控总监审批9、待总经理审批10、已审批11、已放款12、业务办理已完成13、已归档
    26: string performJobRemarkDate;//执行岗备注时间（BIZ_LOAN_PERFORM_JOB_REMARK.REMARK_DATE）
    27: string performJobRemark;//执行岗备注（BIZ_LOAN_PERFORM_JOB_REMARK.REMARK）
    28: i32 performJobRemarkStatus;//执行岗备注状态:未备注=1，已备注=2（BIZ_LOAN_PERFORM_JOB_REMARK.STATUS）
    29: i32 reCheckStatus;//查档复核状态：未复核=1，重新复核=2，同意=3，不同意=4(BIZ_CHECK_DOCUMENT.RE_CHECK_STATUS)
    30: string reCheckRemark;//查档复核备注(BIZ_CHECK_DOCUMENT.RE_CHECK_REMARK)
    31: i32 refundFileStatus;//退件完结状态：未退件=1，已退件=2(BIZ_PROJECT.REFUND_FILE_STATUS)
    32: string houseName;//物业名称
	33: string buyerName;//买方姓名
	34: string sellerName;//卖方姓名
	35: i32 isChechan;//是否撤单:是=1，否=0
	36: string housePropertyCard;//房产证号
	37: double loanMoney;//借款金额
	38: i32 loanDays;//借款天数
	39: i32 loanRepaymentStatus;//回款状态：未回款=1 / null,已回款=2 
	40: string checkStatusStr;//查档状态（每个物业最新的查档状态，以逗号隔开）
	41: string pmUserName;//客户经理
	42: i32 pmUserId;  //客户经理ID
	43: i32 projectSource;  //项目来源1、万通2、小科3、其他机构  
	44: i32 productId;//产品ID
	45: string productName;//业务方案
}
/*执行岗备注表：BIZ_LOAN_PERFORM_JOB_REMARK*/
struct PerformJobRemark{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: i32 status;//
    4: string remark;//
    5: string remarkDate;//
    6: i32 updateId;//更新人id
    7: string updateDate;//更新时间
    8: i32 page=1; //第几页
	9: i32 rows=10; //总页数
	10: list<i32> userIds;// 
}
/*回款首页列表*/
struct RepaymentIndexDTO{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
	3: list<i32> userIds;// 
    4: i32 repaymentId;//回款ID
    5: i32 projectId;//项目ID
    6: string projectNumber;//项目编号
    7: string projectName;//项目名称
    8: double realRepaymentMoney;//实际回款金额
    9: string realRepaymentDate;//实际回款时间
    10: double planRepaymentMoney;//预计回款金额
    11: string planRepaymentDate;//预计回款时间
    12: i32 repaymentStatus;//回款状态：未回款=1，已回款=2
    13: double overdueFee;//逾期费
    14: i32 overdueDay;//逾期天数
    15: i32 overdueFeeConfirm;//逾期费确认：未确认=1，已确认=2
    16: i32 projectSource;//项目来源（1万通2小科）
    17: string houseName;//物业名称
	18: string buyerName;//买方姓名
	19: string sellerName;//卖方姓名
	20: i32 overdueFeePaymentWay;//逾期费付款方式：从尾款扣=1，转账收费=2
	21: string overdueFeeAccountNo;//逾期费付款账号
	22: string acctName;//借款人姓名
	23: string receDate;//放款时间
	24: string receDateStart;//放款时间开始（仅用作查询）
	25: string receDateEnd;//放款时间结束（仅用作查询）
	26: double applyLoanMoney;//申请的借款金额
}
/*回款表：BIZ_LOAN_REPAYMENT*/
struct RepaymentDTO{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
	3: list<i32> userIds;// 
    4: i32 pid;//主键
    5: i32 projectId;//项目ID
    6: double repaymentMoney;//回款金额
    7: string newRepaymentDate;//最新的回款时间
    8: i32 status;//状态：未回款=1，已回款=2
    9: i32 createrId;//创建人id
    10: i32 updateId;//更新人id
    11: string updateDate;//更新时间
    12: string createrDate;//创建时间
    13: string remark;//备注
}
/*回款记录表：BIZ_LOAN_REPAYMENT_RECORD*/
struct RepaymentRecordDTO{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
	3: list<i32> userIds;// 
    4: i32 pid;//主键
    5: i32 projectId;//项目ID
    6: i32 repaymentId;//回款ID
    7: double repaymentMoney;//回款金额
    8: string repaymentDate;//回款时间
    9: i32 createrId;//创建人id
    10: string createrDate;//创建时间
    11: string remark;//备注
    12: string accountNo;//回款账号
    13: i32 orgId;//机构id
    14: string repaymentType;//回款类型
    15: i32 planCycleNum;//回款期数
    16: string createrName;//操作人姓名
}

/*逾期费表：BIZ_LOAN_OVERDUE_FEE*/
struct OverdueFeeDTO{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
	3: list<i32> userIds;// 
    4: i32 pid;//主键
    5: i32 projectId;//项目ID
    6: string accountName;//户名
    7: string bankName;//开户行
    8: string accountNo;//账号
    9: double overdueFee;//逾期费
    10: double overdueRate;//逾期费率
    11: i32 overdueDay;//逾期天数
    12: i32 paymentWay;//逾期费付款方式：从尾款扣=1，转账收费=2
    13: i32 status;//状态：无效=-1，有效=1
    14: i32 createrId;//创建人id
    15: i32 updateId;//更新人id
    16: string updateDate;//更新时间
    17: string createrDate;//创建时间
    18: string remark;//备注
    19: i32 isConfirm;//逾期费确认：未确认=1，已确认=2
}
/**
 *	业务办理动态评级管理,表:BIZ_DYNAMIC_EVALUATE_INFO
 */
struct BizDynamicEvaluateInfo{
	1: i32 pid;	//评价编号
	2: i32 projectId;	//项目编号
	3: i32 bizMangerId;	//业务经理(评价人)
	4: i32 handleDynamicId;	//办理流程编号,对应办理动态ID
	5: i32 category;	//评价类型,1表示点赞,2表示差评
	6: string createDate;//评价日期
}
struct BizEvaluateMap{
	1: i32 like;	//
	2: i32 disLike;	//
}
/**
 *	中途划转,表:BIZ_INTERMEDIATE_TRANSFER
 */
struct IntermediateTransferDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: i32 applyStatus;//申请状态：未申请=1，申请中=2，待审核=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
    4: double recMoney;//到账金额
    5: string recAccount;//到账账号
    6: string recDate;//到账时间
    7: double transferMoney;//划出金额
    8: string transferAccount;//划出账号
    9: string transferDate;//划出时间
	10: list<i32> userIds;// 
    11: string createrDate;//创建时间
    12: i32 createrId;//创建人id
    13: i32 updateId;//更新人id
    14: string updateDate;//更新时间
    15: i32 page=1; //第几页
	16: i32 rows=10; //总页数
	17: string remark;//备注
	18: string recAccountName;//到账账号户名
	19: string transferAccountName;//划出账号户名
	20: i32 specialType;//特殊情况类型：客户自由资金到账=1,客户首期款优先=2
	21: list<i32> applyStatusList;//申请状态集合（仅作查询条件）
}
/**
 *	中途划转首页列表
 */
struct IntermediateTransferIndexDTO{
	1: i32 pid;//主键
	2: i32 projectId;//项目id
	3: string projectNumber;//项目编号
	4: string projectName;//项目名称
	5: i32 bizApplyHandleStatus;//业务办理状态
	6: i32 recStatus;//收款状态
	7: i32 applyHandleStatus;//申请状态:未申请=1，申请中=2，待审核=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
	8: string cancelGuaranteeDate;//解保日期（回款时间）
	9: list<i32> userIds;// 
	10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
	12: string customerName; //借款申请人姓名
	13: string oldHome; //原业主
	14: string projectPassDate; //贷前审批通过时间（取财务受理的创建时间，因为贷前审批一完成，就会添加财务受理信息）
	15: double loanMoney; //贷款金额
	16: i32 productType; //产品类型(1:信用 2:赎楼)
	17: i32 productId; //产品ID
	18: string productName; //产品名称
	19: string requestDate; //贷款申请时间
	20: double realLoan;//放款金额
	21: i32 type;//列表数据类型：中途划转查询=1，中途划转列表=2（仅做判断条件）
	22: double recMoney;//到账金额
    23: string recAccount;//到账账户
    24: string recDate;//到账时间
    25: double transferMoney;//划出金额
    26: string transferAccount;//划出账户
    27: string transferDate;//划出时间
    28: i32 specialType;//特殊情况类型：客户自由资金到账=1,客户首期款优先=2
    29: string houseName;//物业名称
	30: string buyerName;//买方姓名
	31: string sellerName;//卖方姓名
	32: i32 isChechan;//是否撤单:是=1，否=0
	33: i32 pmUserId;//客户经理
}
/**
 *	业务动态 表BIZ_DYNAMIC
 */
struct BizDynamic{
	1: list<i32> userIds;// 
	2: i32 page=1; //第几页
	3: i32 rows=10; //总页数
	4: i32 pid;//主键
	5: i32 projectId;//项目id
	6: string moduelNumber;//模块编号
	7: string dynamicNumber;//动态编号
	8: string parentDynamicNumber;//父级动态编号
	9: i32 status;//状态：未完成=1，进行中=2，已完成=3，失效=4
	10: string finishDate;//完成时间
	11: string remark;//备注
	12: string createrDate;//创建时间
	13: string updateDate;//更新时间
	14: i32 handleAuthorId;//处理用户id
	15: string dynamicName;//动态名
	16: string handleAuthorName;//操作人姓名
	17: string createrDateStr;//格式化后的时间字符串 yyyy-MM-dd
}
/*机构的保后监管列表*/
struct OrgBizHandlePage{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
    3: i32 projectId;//项目ID
    4: string projectNumber;//项目编号
    5: string projectName;//项目名称
    6: double realLoan;//放款金额
    7: double loanMoney;//贷款金额
	8: i32 projectStatus;//项目状态
    9: string houseName;//物业名称
	10: string buyerName;//买方姓名
	11: string sellerName;//卖方姓名
	12: i32 orgId;//机构id（作数据权限） 
	13: i32 applyUserId;//订单申请用户id（作数据权限）
	14: string applyUserName;//订单申请用户姓名
	15: i32 bizHandleId;//
}
/*
 * 贷中
 * 
 * 财务受理  Service
 */
service FinanceHandleService {
	//查询财务放款列表（分页查询）
	list<FinanceIndexDTO> queryFinanceIndex (1:FinanceIndexDTO financeIndexDTO) ;
	//查询财务放款条数
	i32 getFinanceIndexTotal (1:FinanceIndexDTO financeIndexDTO) ;
	
	//查询财务收费列表（分页查询）
	list<FinanceIndexDTO> queryFinanceCollectFeeIndex (1:FinanceIndexDTO financeIndexDTO) ;
	//查询财务收费条数
	i32 getFinanceCollectFeeIndexTotal (1:FinanceIndexDTO financeIndexDTO) ;
	
	//根据条件查询财务受理主表信息
	list<FinanceHandleDTO> findAllFinanceHandle (1:FinanceHandleDTO financeHandleDTO) ;
	//根据条件查询财务受理主条数
	i32 getFinanceHandleTotal (1:FinanceHandleDTO financeHandleDTO) ;
	//添加财务受理主句
	bool addFinanceHandle (1:FinanceHandleDTO financeHandleDTO) ;
	//根据id查询财务受理信息
	FinanceHandleDTO getFinanceHandleById (1:i32 pid) ;
	//根据id修改财务受理信息
	bool updateFinanceHandle (1:FinanceHandleDTO financeHandleDTO) ;
	
	//根据条件查询申请财务受理信息（分页查询）
	list<ApplyFinanceHandleDTO> findAllApplyFinanceHandle (1:ApplyFinanceHandleDTO applyFinanceHandleDTO) ;
	//根据条件查询申请财务受理信息条数
	i32 getApplyFinanceHandleTotal (1:ApplyFinanceHandleDTO applyFinanceHandleDTO) ;
	//添加申请财务受理信息
	bool addApplyFinanceHandle (1:ApplyFinanceHandleDTO applyFinanceHandleDTO) ;
	//根据id查询申请财务受理信息
	ApplyFinanceHandleDTO getApplyFinanceHandleById (1:i32 pid) ;
	//根据项目id查询财务受理信息
	FinanceHandleDTO getFinanceHandleByProjectId(1:i32 projectId) ;
	//根据项目ID和收款项目进行查询申请财务受理信息
	ApplyFinanceHandleDTO getByProjectIdAndRecPro(1:i32 projectId,2:i32 recPro) ;
	//财务放款
	bool makeLoans (1:ApplyFinanceHandleDTO applyFinanceHandleDTO,2:i32 userId,3:i32 isLoanFinish,4:i32 houseClerkId) ;
	//根据id修改申请财务受理信息
	bool updateApplyFinanceHandle (1:ApplyFinanceHandleDTO applyFinanceHandleDTO) ;
	//根据项目id获取最新的回款信息，如果有第二次回款则返回第二次，没有则返回第一次回款信息
	ApplyFinanceHandleDTO getNewRecApplyFinance (1:i32 projectId) ;
	//根据项目id和收款项目集合查询金额
	double getRecMoney (1:i32 projectId,2:list<i32> recPros) ;
	//添加展期收费数据
	i32 addFinanceHandleByExtension(1:i32 projectId);
	//财务收展期费
	i32 collectExtensionFee(1:FinanceHandleDTO financeHandleDTO,2:ApplyFinanceHandleDTO applyFinanceHandleDTO);
	//财务收费(赎楼贷)
	bool collectFee(1:list<ApplyFinanceHandleDTO> applyFinanceHandleList);
	//财务收费(房抵贷贷)
	bool collectFddFee(1:ApplyFinanceHandleDTO applyFinanceHandle);
	//根据项目id，产品名称查询放款记录
	list<ApplyFinanceHandleDTO> getLoanHisByProjectId(1:i32 projectId,2:string productNum) ;
}

/*
 * 贷中
 * 
 * 业务受理  Service
 */
service BizHandleService {
    //业务办理首页列表
	list<ApplyHandleIndexDTO> findAllApplyHandleIndex (1:ApplyHandleIndexDTO applyHandleIndexDTO) ;
    //业务办理总数
	i32 getApplyHandleIndexTotal (1:ApplyHandleIndexDTO applyHandleIndexDTO) ;
	//查询办理动态办理条数
	list<HandleDynamicMap> qeuryHandleDynamicCountMapList (1:ApplyHandleIndexDTO applyHandleIndexDTO) ;
	
	//业务处理信息列表
	list<HandleInfoDTO> findAllHandleInfoDTO (1:HandleInfoDTO handleInfoDTO) ;
    //业务处理信息总数
	i32 getHandleInfoDTOTotal (1:HandleInfoDTO handleInfoDTO) ;
    //添加业务处理信息（主表）
	bool addHandleInfo (1:HandleInfoDTO handleInfoDTO,2:map<string,string> paramMap) ;
    //根据ID获取业务处理信息
	HandleInfoDTO getHandleInfoById (1:i32 pid) ;
    //根据项目ID获取业务处理信息
	HandleInfoDTO getHandleInfoByProjectId (1:i32 projectId) ;
    //根据ID更新业务处理信息
	bool updateHandleInfo (1:HandleInfoDTO handleInfoDTO) ;
    //赎楼驳回
	bool foreclosureTurnDown (1:HandleInfoDTO handleInfoDTO) ;
    //根据项目id或者业务处理id，判断该项目的赎楼余额是否已确认
	bool isBalanceConfirm(1:i32 projectId, 2:i32 handleId) ;
	
	//查询业务处理信息
	list<ApplyHandleInfoDTO> findAllApplyHandleInfo (1:ApplyHandleInfoDTO applyHandleInfoDTO) ;
    //查询业务处理信息总数
	i32 getApplyHandleInfoTotal (1:ApplyHandleInfoDTO applyHandleInfoDTO) ;
    //添加申请业务处理信息 
	bool addApplyHandleInfo (1:ApplyHandleInfoDTO applyHandleInfoDTO) ;
    //根据ID获取申请业务处理信息 
	ApplyHandleInfoDTO getApplyHandleInfoById (1:i32 pid) ;
    //根据ID更新申请业务处理信息 
	bool updateApplyHandleInfo (1:ApplyHandleInfoDTO applyHandleInfoDTO) ;
	
	//查询赎楼及余额回转信息 
	list<HouseBalanceDTO> findAllHouseBalance (1:HouseBalanceDTO houseBalanceDTO) ;
    //查询赎楼及余额回转信息总数
	i32 getHouseBalanceTotal (1:HouseBalanceDTO houseBalanceDTO) ;
	//根据项目id 查询赎楼及余额回转信息
	list<HouseBalanceDTO> getHouseBalanceListByProjectId (1:i32 projectId) ;
	//根据项目id 查询赎楼金额
	double getForeclosureMoneyByProjectId (1:i32 projectId) ;
    //添加赎楼及余额回转信息 
	bool addHouseBalance (1:HouseBalanceDTO houseBalanceDTO) ;
    //根据ID获取赎楼及余额回转信息
	HouseBalanceDTO getHouseBalanceById (1:i32 pid) ;
    //根据ID更新赎楼及余额回转信息
	bool updateHouseBalance (1:HouseBalanceDTO houseBalanceDTO) ;
    //赎楼余额确认
	bool balanceConfirm (1:HouseBalanceDTO houseBalanceDTO) ;
	//获取办理动态办理人
    set<map<string,string>> getHandleUser(1:i32 handleId,2:i32 handleFlowId,3:string loginUserName);
    
	i32 getProjectIdByHandleId(1:i32 handleId) ;
	
	//查询财务退款明细 
	list<RefundDetailsDTO> findAllRefundDetails (1:RefundDetailsDTO refundDetailsDTO) ;
    //查询财务退款明细 总数
	i32 getRefundDetailsTotal (1:RefundDetailsDTO refundDetailsDTO) ;
	//根据项目id 查询财务退款明细 
	list<RefundDetailsDTO> getRefundDetailsListByProjectId (1:i32 projectId,2:list<i32> refundPros) ;
    //添加财务退款明细 
	bool addRefundDetails (1:RefundDetailsDTO refundDetailsDTO) ;
    //根据ID获取财务退款明细
	RefundDetailsDTO getRefundDetailsById (1:i32 pid) ;
    //根据ID更新财务退款明细
	bool updateRefundDetails (1:RefundDetailsDTO refundDetailsDTO) ;
	
	//查询办理流程条目
	list<HandleFlowDTO> findAllHandleFlow (1:HandleFlowDTO handleFlowDTO) ;
	//查询办理流程条目总数
	i32 getHandleFlowTotal (1:HandleFlowDTO handleFlowDTO) ;
	
	//查询办理动态
	list<HandleDynamicDTO> findAllHandleDynamic (1:HandleDynamicDTO handleDynamicDTO) ;
    //查询办理动态总数
	i32 getHandleDynamicTotal (1:HandleDynamicDTO handleDynamicDTO) ;
	//根据项目id查询办理总历时天数
	i32 gethandleDaysByProjectId (1:i32 projectId) ;
    //添加办理动态
	bool addHandleDynamic (1:HandleDynamicDTO handleDynamicDTO) ;
    //根据ID获取办理动态
	HandleDynamicDTO getHandleDynamicById (1:i32 pid) ;
    //根据ID更新办理动态
	bool updateHandleDynamic (1:HandleDynamicDTO handleDynamicDTO) ;
	bool updateHandleDynamicAndFinishTask (1:HandleDynamicDTO handleDynamicDTO,2:string loginUserName) ;
	bool finishHandleDynamicTask (1:HandleDynamicDTO handleDynamicDTO,2:string loginUserName) ;
	//自动完成业务办理工作流程(除放款外)
	bool finishAllHandleDynamicTask (1:i32 projectId,2:string loginUserName) ;
	
	//查询业务办理和任务关联
	list<BizHandleWorkflowDTO> findAllBizHandleWorkflow (1:BizHandleWorkflowDTO bizHandleWorkflowDto) ;
    //添加业务办理和任务关联
	bool addBizHandleWorkflow (1:BizHandleWorkflowDTO bizHandleWorkflowDto) ;
    //根据ID更新业务办理和任务关联
	bool updateBizHandleWorkflow (1:BizHandleWorkflowDTO bizHandleWorkflowDto) ;
	
	//查询差异预警处理
	list<HandleDifferWarnDTO> findAllHandleDifferWarn (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
    //查询差异预警处理总数
	i32 getHandleDifferWarnTotal (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
	
    //查询历史预警处理总数
	i32 getHisHandleDifferWarnCount (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
	
	//查询(首页)差异预警处理
	list<HandleDifferWarnDTO> findIndexHandleDifferWarn(1:i32 userId) ;
	//查询(首页)差异预警处理总数
	i32 getIndexHandleDifferWarnTotal(1:HandleDifferWarnDTO handleDifferWarnDTO) ;
    //添加差异预警处理表
	bool addHandleDifferWarn (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
    //根据id删除差异预警处理
	bool delHandleDifferWarn (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
    //根据ID获取差异预警处理表
	HandleDifferWarnDTO getHandleDifferWarnById (1:i32 pid) ;
    //根据ID更新差异预警处理表
	bool updateHandleDifferWarn (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
	
    //添加差异预警历史处理表
	bool addHisHandleDifferWarn (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
	//查询差异预警历史处理
	list<HandleDifferWarnDTO> findAllHisHandleDifferWarn (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
    //查询差异预警处理总数
	i32 getHisHandleDifferWarnTotal (1:HandleDifferWarnDTO handleDifferWarnDTO) ;
	
	//查询办理动态文件关联
	list<HandleDynamicFileDTO> findAllHandleDynamicFile (1:HandleDynamicFileDTO handleDynamicFileDTO) ;
    //查询办理动态文件关联总数
	i32 getHandleDynamicFileTotal (1:HandleDynamicFileDTO handleDynamicFileDTO) ;
    //添加办理动态文件关联
	bool addHandleDynamicFile (1:HandleDynamicFileDTO handleDynamicFileDTO,2:System.BizFile bizFile) ;
    //添加办理动态文件关联
	bool addHandleDynamicFileOfComm (1:HandleDynamicFileDTO handleDynamicFileDTO) ;
    //根据ID获取办理动态文件关联
	HandleDynamicFileDTO getHandleDynamicFileById (1:i32 pid) ;
    //根据ID更新办理动态文件关联
	bool updateHandleDynamicFile (1:HandleDynamicFileDTO handleDynamicFileDTO) ;
	
	//根据业务处理ID和用户名，获取可以处理的办理流程条目ID集合
	list<i32> getCanHandleFlowByHandleId(1:i32 handleId,2:string userName);
	//根据用户名(任务处理人)，获取可处理的办理动态id的list
	list<i32> getCanHandleDynamicIds(1:string userName);
	//根据用户id（任务发起人），获取未处理（需要预警）的办理办理信息集合，用途：业务经理在登录系统的时候，查看自己申请的业务办理有没有未完成的办理动态
	list<HandleDifferWarnDTO> getNeedHandleWarn(1:i32 userId);
	//查询需要处理的预警列表（分页查询）
	list<HandleDifferWarnIndexDTO> queryNeedHandleWarnIndex(1:HandleDifferWarnIndexDTO handleDifferWarnIndexDTO);
	//查询需要处理的预警列表数量
	i32 getNeedHandleWarnIndexTotal(1:HandleDifferWarnIndexDTO handleDifferWarnIndexDTO);
	
    //查询赎楼列表（分页）
	list<ForeclosureIndexDTO> queryForeclosureIndex(1:ForeclosureIndexDTO foreclosureIndexDTO) ;
    //查询赎楼总数
	i32 getForeclosureIndexTotal (1:ForeclosureIndexDTO foreclosureIndexDTO) ;
	
	//赎楼
	bool foreclosure (1:HouseBalanceDTO houseBalanceDTO,2:HandleInfoDTO handleInfoDTO,3:HandleDynamicDTO handleDynamicDTO,4:string loginUserName) ;
	
	//机构的保后监管列表(分页查询)
	list<OrgBizHandlePage> queryOrgBizHandlePage(1:OrgBizHandlePage query);
	i32 getOrgBizHandlePageTotal(1:OrgBizHandlePage query);
	
	
	//查询预警列表
	list<HandleDifferWarnDTO> getHandleDifferWarnList(1:HandleDifferWarnDTO handleDifferWarnDTO);
	
	//查询办理动态文件关联 
	list<HandleDynamicFileDTO> findHandleDynamicFileByProjectId (1:i32 projectId) ;
}
/*
 * 
 * 综合部管理  Service
 * 包括：收件，查档
 */
service IntegratedDeptService {
    //收件列表(分页查询)
	list<CollectFileDTO> queryCollectFile (1:CollectFileDTO collectfileDTO) ;
    //收件总数
	i32 getCollectFileTotal (1:CollectFileDTO collectfileDTO) ;
    //添加收件
	bool addCollectFile (1:CollectFileDTO collectfileDTO) ;
    //根据ID获取收件
	CollectFileDTO getCollectFileById (1:i32 pid) ;
    //根据ID更新收件
	bool updateCollectFile (1:CollectFileDTO collectfileDTO) ;
    //根据projectId更新收件备注
	bool updateRemarkByProjectId (1:CollectFileDTO collectfileDTO) ;
    //收件
	bool collectFile (1:CollectFileDTO collectfileDTO) ;
    //退件
	bool refundFile (1:CollectFileDTO collectfileDTO) ;
	//查询要件打印信息
	CollectFilePrintInfo getCollectFilePrintInfo (1:CollectFilePrintInfo collectFilePrintInfo) ;
    //查档列表(分页查询)
	list<CheckDocumentDTO> queryCheckDocument (1:CheckDocumentDTO checkDocumentDTO) ;
    //查档总数 
	i32 getCheckDocumentTotal (1:CheckDocumentDTO checkDocumentDTO) ;
    //添加查档
	bool addCheckDocument (1:CheckDocumentDTO checkDocumentDTO) ;
    //根据ID获取查档
	CheckDocumentDTO getCheckDocumentById (1:i32 pid) ;
	//根据项目ID获取查档
	CheckDocumentDTO getCheckDocumentByProjectId (1:i32 projectId) ;
    //根据ID更新查档
	bool updateCheckDocument (1:CheckDocumentDTO checkDocumentDTO) ;
    //查档复核
	bool reCheckCheckDocument (1:CheckDocumentDTO checkDocumentDTO) ;
	
	 //查诉讼列表(分页查询)
	list<CheckLitigationDTO> queryCheckLitigation (1:CheckLitigationDTO checkLitigationDTO) ;
    //查诉讼总数
	i32 getCheckLitigationTotal (1:CheckLitigationDTO checkLitigationDTO) ;
    //添加查诉讼
	bool addCheckLitigation (1:CheckLitigationDTO checkLitigationDTO) ;
    //根据ID获取查诉讼
	CheckLitigationDTO getCheckLitigationById (1:i32 pid) ;
	//根据项目ID获取查诉讼
	CheckLitigationDTO getCheckLitigationByProjectId (1:i32 projectId) ;
    //根据ID更新查诉讼
	bool updateCheckLitigation (1:CheckLitigationDTO checkLitigationDTO) ;
	
    //查档首页列表(分页查询)
	list<CheckDocumentIndexDTO> queryCheckDocumentIndex (1:CheckDocumentIndexDTO checkDocumentIndexDTO) ;
    //查档首页列表总数
	i32 getCheckDocumentIndexTotal (1:CheckDocumentIndexDTO checkDocumentIndexDTO) ;
	//根据主键集合查询收件列表
	list<CollectFileDTO> queryCollectFileByPids(1:string pids);
	
	 //根据项目ID查询执行岗备注
	PerformJobRemark getPerformJobRemark (1:i32 projectId) ;
	//添加执行岗备注
	i32 addPerformJobRemark (1:PerformJobRemark performJobRemark) ;
	//根据项目ID更新执行岗备注
	bool updatePerformJobRemark (1:PerformJobRemark performJobRemark) ;
	void initIntegratedDept (1:i32 projectId) ;
}
/*
 * 
 * 回款管理  Service
 * 该service对回款，回款记录，逾期费等做操作 
 */
service RepaymentService {
	//根据条件查询回款列表(分页查询)
	list<RepaymentIndexDTO> queryRepaymentIndex (1:RepaymentIndexDTO repaymentIndexDTO) ;
    //根据条件查询回款列表总数
	i32 getRepaymentIndexTotal (1:RepaymentIndexDTO repaymentIndexDTO) ;
	
	//根据条件查询回款(分页查询)
	list<RepaymentDTO> queryRepayment (1:RepaymentDTO repaymentDTO) ;
    //根据条件查询回款总数
	i32 getRepaymentTotal (1:RepaymentDTO repaymentDTO) ;
    //添加回款
	bool addRepayment (1:RepaymentDTO repaymentDTO) ;
    //根据ID获取回款
	RepaymentDTO getRepaymentById (1:i32 pid) ;
    //根据项目ID获取回款
	RepaymentDTO getRepaymentByProjectId (1:i32 projectId) ;
    //根据ID更新回款
	bool updateRepayment (1:RepaymentDTO repaymentDTO) ;
	
	//根据条件查询回款记录(分页查询)
	list<RepaymentRecordDTO> queryRepaymentRecord (1:RepaymentRecordDTO repaymentRecordDTO) ;
    //根据条件查询回款记录总数
	i32 getRepaymentRecordTotal (1:RepaymentRecordDTO repaymentRecordDTO) ;
    //添加回款记录
	bool addRepaymentRecord (1:RepaymentRecordDTO repaymentRecordDTO) ;
    //根据ID获取回款记录
	RepaymentRecordDTO getRepaymentRecordById (1:i32 pid) ;
	
	//根据条件查询逾期费(分页查询)
	list<OverdueFeeDTO> queryOverdueFee (1:OverdueFeeDTO overdueFeeDTO) ;
    //根据条件查询逾期费总数
	i32 getOverdueFeeTotal (1:OverdueFeeDTO overdueFeeDTO) ;
    //添加逾期费
	bool addOverdueFee (1:OverdueFeeDTO overdueFeeDTO) ;
    //根据ID获取逾期费
	OverdueFeeDTO getOverdueFeeById (1:i32 pid) ;
    //根据项目ID获取逾期费
	OverdueFeeDTO getOverdueFeeByProjectId (1:i32 projectId) ;
    //根据ID更新逾期费
	bool updateOverdueFee (1:OverdueFeeDTO overdueFeeDTO) ;
    //逾期费确认
	bool confirmOverdueFee (1:OverdueFeeDTO overdueFeeDTO) ;
	
	//根据条件查询回款列表(分页查询)--抵押贷回款管理
	list<RepaymentIndexDTO> queryRepaymentInfo (1:RepaymentIndexDTO repaymentIndexDTO) ;
    //根据条件查询回款列表总数--抵押贷回款管理
	i32 getRepaymentInfoTotal (1:RepaymentIndexDTO repaymentIndexDTO) ;
}
/*
 * 贷中
 * 
 * 退手续费  Service
 */
service RefundFeeService {
    //根据条件查询退款列表（分页查询）
	list<RefundFeeIndexDTO> findAllRefundFeeIndex (1:RefundFeeIndexDTO refundFeeIndexDTO) ;
    //根据条件查询退款列表总数
	i32 getRefundFeeIndexTotal (1:RefundFeeIndexDTO refundFeeIndexDTO) ;

    //根据条件查询退手续费信息（分页查询）
	list<RefundFeeDTO> findAllRefundFee (1:RefundFeeDTO refundFeeDTO) ;
	//根据条件查询退手续费信息总数
	i32 getRefundFeeTotal (1:RefundFeeDTO refundFeeDTO) ;
	//添加退手续费信息
	bool addRefundFee (1:RefundFeeDTO refundFeeDTO) ;
	//根据id更新退手续费信息
	bool updateRefundFee (1:RefundFeeDTO refundFeeDTO) ;
    //添加退尾款
	bool addRefundTail (1:i32 projectId) ;
	
	//根据id集合查询退费报表 
	list<RefundFeeIndexDTO> queryRefundFeeByIds (1:string ids) ;
	//批量保存退费信息
	i32 saveBatchRefundFee (1:RefundFeeDTO refundFeeDTO) ;
	//根据id获取批量退费主表数据
	BizLoanBatchRefundFeeMain getBatchRefundFeeMainById (1:i32 pid) ;
	//根据条件查询批量退费关联数据
	list<BizBatchRefundFeeRelation> getAllBatchRefundFeeRelation (1:BizBatchRefundFeeRelation query) ;
	//生成一个批量名称
	string generatedBatchName() ;
}
/*
 * 贷中
 * 
 * 中途划转  Service
 */
service IntermediateTransferService {
    //根据条件查询中途划转（分页查询）
	list<IntermediateTransferIndexDTO> queryIntermediateTransferIndex (1:IntermediateTransferIndexDTO intermediateTransferIndexDTO) ;
	//根据条件查询中途划转总数
	i32 getIntermediateTransferIndexTotal (1:IntermediateTransferIndexDTO intermediateTransferIndexDTO) ;
	
    //根据条件查询中途划转申请列表（分页查询）
	list<IntermediateTransferIndexDTO> queryIntermediateTransferRequestIndex (1:IntermediateTransferIndexDTO intermediateTransferIndexDTO) ;
	//根据条件查询中途划转申请列表总数
	i32 getIntermediateTransferRequestIndexTotal (1:IntermediateTransferIndexDTO intermediateTransferIndexDTO) ;
	
    //根据条件查询中途划转（分页查询）
	list<IntermediateTransferDTO> queryIntermediateTransfer (1:IntermediateTransferDTO intermediateTransferDTO) ;
	//根据条件查询中途划转总数
	i32 getIntermediateTransferTotal (1:IntermediateTransferDTO intermediateTransferDTO) ;
	//根据id获取中途划转
	IntermediateTransferDTO getIntermediateTransferById (1:i32 pid) ;
	//添加中途划转
	i32 addIntermediateTransfer (1:IntermediateTransferDTO intermediateTransferDTO) ;
	//根据id更新中途划转
	bool updateIntermediateTransfer (1:IntermediateTransferDTO intermediateTransferDTO) ;
	//根据项目id 检查是否有工作流在运行,返回值大于0则有工作流运行
	i32 checkWorkFlowExist (1:IntermediateTransferDTO intermediateTransferDTO) ;
}
/*
 * 贷中
 * 
 * 业务办理动态评级管理  Service
 */
service BizDynamicEvaluateService {
    //根据用户id和办理动态id检查是否已经评分，返回值大于0，则已经评分
	i32 checkIsEvaluate (1:BizDynamicEvaluateInfo bizDynamicEvaluateInfo) ;
	//查询办理动态的差评和点赞数量
	BizEvaluateMap queryBizEvaluateMap (1:i32 handleDynamicId) ;
    //添加评分
	bool addEvaluate (1:BizDynamicEvaluateInfo bizDynamicEvaluateInfo) ;
    //更新
	bool updateEvaluate (1:BizDynamicEvaluateInfo bizDynamicEvaluateInfo) ;
}

/*
 * 业务动态  Service
 */
service BizDynamicService {
    //根据条件查询业务动态信息（分页查询）
	list<BizDynamic> queryBizDynamic (1:BizDynamic bizDynamic) ;
	//根据条件查询业务动态信息总数
	i32 getBizDynamicTotal (1:BizDynamic bizDynamic) ;
    //添加
	bool addBizDynamic (1:BizDynamic bizDynamic) ;
    //更新
	bool updateBizDynamic (1:BizDynamic bizDynamic) ;
    //添加或更新：不存在则添加，存在则更新
	bool addOrUpdateBizDynamic (1:BizDynamic bizDynamic) ;
    //根据ID,项目ID,模块编号,删除业务动态
	bool delBizDynamic (1:BizDynamic bizDynamic) ;
    //根据父级动态id级联删除子节点，不包括父节点
	bool delBizDynamicByCascade (1:BizDynamic bizDynamic) ;
	//初始化数据
	bool initBizDynamic (1:i32 projectId) ;
	//根据驳回的节点删除业务动态
	i32 delBizDynamicByLastId(1:BizDynamic bizDynamic);
}
/*
 *查档文件关联
 *2016-10-14 14:50:44
 **/
struct CheckDocumentFile{
	1: i32 pid;//
	2: i32 fileCategory;//
	3: i32 projectId;//
	4: i32 fileId;//
	5:System.BizFile file;
	6: i32 page=1; //第几页
	7: i32 rows=10; //总页数
}
/*查档文件关联Service*/
service CheckDocumentFileService{
	list<CheckDocumentFile> queryCheckDocumentFile (1:CheckDocumentFile query) ;
	i32 getCheckDocumentFileTotal (1:CheckDocumentFile query) ;
    //根据条件查询所有查档文件关联
	list<CheckDocumentFile> getAll(1:CheckDocumentFile checkDocumentFile);
	//查询查档文件关联
	CheckDocumentFile getById(1:i32 pid);
	//新增查档文件关联
	i32 insert(1:CheckDocumentFile checkDocumentFile);
	//修改查档文件关联
	i32 update(1:CheckDocumentFile checkDocumentFile);
	//删除查档文件关联
	i32 deleteById(1:i32 pid);
	//批量删除查档文件关联
	i32 deleteByIds(1:list<i32> pids);
	i32 saveCheckDocumentFile(1:CheckDocumentFile checkDocumentFile,2:list<System.BizFile> fileList);
}

/*查档历史表Service*/
service CheckDocumentHisService{
    //根据条件查询所有查档历史表
	list<CheckDocumentDTO> getAll(1:CheckDocumentDTO checkDocument);
	//根据项目Id查询所有查档历史表
	list<CheckDocumentDTO> getAllCdtoByProjectId(1:i32 projectId);
	//查询查档历史表
	CheckDocumentDTO getById(1:i32 pid);
	//新增查档历史表
	i32 insert(1:CheckDocumentDTO checkDocument);
	//修改查档历史表
	i32 update(1:CheckDocumentDTO checkDocument);
	//删除查档历史表
	i32 deleteById(1:i32 pid);
	//批量删除查档历史表
	i32 deleteByIds(1:list<i32> pids);
	//查档历史列表(分页查询)
	list<CheckDocumentDTO> queryCheckDocumentHisIndex (1:CheckDocumentDTO checkDocument) ;
	//查档历史列表总数
	i32 getCheckDocumentHisIndexTotal (1:CheckDocumentDTO checkDocument) ;
	// 根据项目id查询该项目的所有物业是否已查档
    //返回结果为：未查档的物业，多个物业以英文逗号隔开
	string getNotCheckDocumentHouseName(1:i32 projectId);
	//查档历史列表(分页查询,根据查档时间降序排列)
	list<CheckDocumentDTO> queryCheckDocumentHisIndex1 (1:CheckDocumentDTO checkDocument) ;
}

/*差诉讼历史表Service*/
service CheckLitigationHisService{
    //根据条件查询所有差诉讼历史表
	list<CheckLitigationDTO> getAll(1:CheckLitigationDTO checkLitigation);
	//查询差诉讼历史表
	CheckLitigationDTO getById(1:i32 pid);
	//新增差诉讼历史表
	i32 insert(1:CheckLitigationDTO checkLitigation);
	//修改差诉讼历史表
	i32 update(1:CheckLitigationDTO checkLitigation);
	//删除差诉讼历史表
	i32 deleteById(1:i32 pid);
	//批量删除差诉讼历史表
	i32 deleteByIds(1:list<i32> pids);
	//差诉讼历史列表(分页查询)
	list<CheckLitigationDTO> queryCheckLitigationHisIndex (1:CheckLitigationDTO query) ;
	//差诉讼历史列表总数
	i32 getCheckLitigationHisIndexTotal (1:CheckLitigationDTO query) ;
	//查诉讼历史列表(分页查询，根据查诉讼时间降序排列)
	list<CheckLitigationDTO> queryCheckLitigationHisIndex1 (1:CheckLitigationDTO query) ;
}
/*
 *资方选择记录
 *2017-12-13 16:42:44
 **/
struct BizCapitalSelRecord{
	1: i32 pid;//
	2: i32 projectId;//项目ID
	3: string capitalName;//资方名称
	4: i32 capitalApprovalStatus;//资方审批状态
	5: i32 capitalLoanStatus;//资方放款状态
	6: i32 createrId;//操作人
	7: string createDate;//操作时间
	8: i32 reviewId;//复核人
	9: string reviewTime;//复核时间
	10: string createrName;
	11: string reviewName;
}
/*资方选择记录Service*/
service BizCapitalSelRecordService{
    //根据条件查询所有资方选择记录
	list<BizCapitalSelRecord> getAll(1:BizCapitalSelRecord bizCapitalSelRecord);
	//查询资方选择记录
	BizCapitalSelRecord getById(1:i32 pid);
	//新增资方选择记录
	i32 insert(1:BizCapitalSelRecord bizCapitalSelRecord);
	//修改资方选择记录
	i32 update(1:BizCapitalSelRecord bizCapitalSelRecord);
	//删除资方选择记录
	i32 deleteById(1:i32 pid);
	//批量删除资方选择记录
	i32 deleteByIds(1:list<i32> pids);
	//根据项目ID查询项目相关资方选择记录
	list<BizCapitalSelRecord> getAllByProjectId(1:i32 projectId);
}

/*
 *面签Index管理DTO
 *2017-12-20 09:31:25
 **/
struct BizInterviewInfoIndexDTO{
    1: i32 pid;//主键
    2: i32 projectId;//项目ID
    3: string projectNumber;//项目编号
    4: string projectName;//项目名称
    5: i32 wfStatus;//流程状态
    6: i32 createrId;//创建者ID
    7: list<i32> userIds;//
    8: i32 page=1; //第几页
    9: i32 rows=10; //总页数
    10: string createrDate;//创建时间
    11: string acctName;//客户名称
    12: double loanMoney;//借款金额
    13: i32 foreclosureStatus;//办理节点
    14: i32 interviewStatus;//面签状态
    15: i32 notarizationStatus;//公证状态
    16: string notarizator;//公证人员
    17: i32 mortgageStatus;//抵押状态
    18: string mortgator;//抵押人员
    19: string hisWarrantTime;//领他证时间
    20: string hisWarrantUserName;//经办人
    21: i32 updateId;
    //额外字段
    22: string hisWarrantStartTime;
    23: string hisWarrantEndTime;
    24: i32 recordClerkId;//登陆人
    25: i32 isChechan;//是否撤单
    26: i32 productType;//产品类型
    27: string interviewer;//面签人员
    28: i32 interviewId;//面签ID
    29: i32 pmUserId;//收单人员
    30: string pmUserName;//收单人名称
    31: string interviewTime;//面签时间
    32: MortgageLoan.CusCardInfo cusCardInfo;//银行卡信息
    33: string interviewPlace;//面签地址
    34: i32 notarizationId;//公证人员ID
    35: i32 mortgageUser;//抵押人员ID
    36: i32 projectType;//项目类型
}

/*
 *面签管理信息
 *2017-12-20 09:31:25
 **/
struct BizInterviewInfo{
    1: i32 pid;//
    2: i32 projectId;//
    3: i32 interviewId;//
    4: string interviewTime;//
    5: string interviewPlace;//
    6: string notarizationType;//
    7: string receiveTime;//
    8: i32 notarizationId;//
    9: string handingTime;//
    10: string mortgageName;//
    11: string mortgageCode;//
    12: string mortgageTime;//
    13: i32 mortgageUser;//
    14: string mortgageHandTime;//
    15: string hisWarrant;//
    16: string hisWarrantTime;//
    17: i32 hisWarrantUser;//
    18: i32 interviewStatus;//
    19: i32 notarizationStatus;//
    20: i32 mortgageStatus;//
    21: string createDate;//
    22: i32 createrId;//
    23: i32 updateId;//
    24: string updateDate;//
    //附加字段
    //面签人员
    25: string interviewer;
    //公证人员
    26: string notarizator;
    //抵押人员
    27: string mortgator;
    //经办人
    28: string hisWarrantUserName;
    //面签他证影象
    30: BizInterviewFile interviewFile;
}
/*面签管理信息Service*/
service BizInterviewInfoService{
    //根据条件查询所有面签管理信息
    list<BizInterviewInfo> getAll(1:BizInterviewInfo bizInterviewInfo);
    //查询面签管理信息
    BizInterviewInfo getById(1:i32 pid);
    //新增面签管理信息
    i32 insert(1:BizInterviewInfo bizInterviewInfo);
    //修改面签管理信息
    i32 update(1:BizInterviewInfo bizInterviewInfo);
    //根据项目ID查询面签信息
    BizInterviewInfo getByProjectId(1:i32 projectId);

    //面签首页列表
    list<BizInterviewInfoIndexDTO> getProjectByPage(1:BizInterviewInfoIndexDTO info);
    //面签首页列表分页
    i32 getProjectCount(1:BizInterviewInfoIndexDTO info);
    //面签指派
    i32 asign(1:BizInterviewInfo bizInterviewInfo);
    //获取客户银行卡信息
    MortgageLoan.CusCardInfo getCardInfoByProjectId(1:i32 projectId);
    //面签-面签
    i32 interivew(1:BizInterviewInfoIndexDTO indexInfo);
    //面签-公证
    i32 notarization(1:BizInterviewInfo bizInterviewInfo);
    //面签-抵押
    i32 mortgage(1:BizInterviewInfo bizInterviewInfo);
    //面签-领他物
    i32 his(1:BizInterviewInfo bizInterviewInfo);
}

/*
 *面签管理文件
 *2017-12-20 09:36:10
 **/
struct BizInterviewFile{
    1: i32 pid;//
    2: i32 interviewId;//
    3: string uploadCode;//
    4: i32 fileId;//
    5: i32 status;//

    6: string fileName;
    7: string fileUrl;
}
/*面签管理文件Service*/
service BizInterviewFileService{
    //根据条件查询所有面签管理文件
    list<BizInterviewFile> getAll(1:BizInterviewFile bizInterviewFile);
    //查询面签管理文件
    BizInterviewFile getById(1:i32 pid);
    //新增面签管理文件
    i32 insert(1:BizInterviewFile bizInterviewFile);
    //修改面签管理文件
    i32 update(1:BizInterviewFile bizInterviewFile);
    
}
/*
 *批量退费主表
 *2017-12-26 10:10:59
 **/
struct BizLoanBatchRefundFeeMain{
	1: i32 pid;//
	2: string batchName;//批量名
	3: i32 applyStatus;//申请状态：未申请=1，申请中=2，驳回=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
	4: string createDate;//
	5: i32 createrId;//
	6: i32 updateId;//
	7: string updateDate;//
}
/*
 *批量退费关联表
 *2017-12-26 10:09:54
 **/
struct BizBatchRefundFeeRelation{
	1: i32 pid;//
	2: i32 batchRefundFeeMainId;//批量退费主表id
	3: i32 projectId;//项目id
	4: i32 refundFeeId;//退费id
}
