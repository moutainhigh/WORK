namespace java com.xlkfinance.bms.rpc.afterloan
include "System.thrift"
include "Common.thrift"
include "File.thrift"


// add by yql
/*查看项目对账信息列表查询条件*/
struct LoanProRecCondition{
    1: i32 loanId;
    2: list<string> recCycleNums;
    3: list<string> realtimeIds;
    
}
/*查看项目对账信息列表返回结果*/
struct LoanProReconciliationDtlView{
    1: i32 pid;
    2: string cycleName;
    3: string reconciliationDt;
    4: string delTypeName;
    5: double reconciliationAmt;
    6: string description;
    
}
//add by yql end 
// modify by qcxian --------------------------------------------------------------------------------------

// 修改时用到的呆账坏账记录
struct BadDebtBean{
 1:i32 pid;  //主键
 2:i32 projectId; // 项目主键
 3:string shouldDt; //应收时间
 4:string asOfDT; //计息截止日期
 5:i32 reviewStatus; //处理状态
 6:string remark; // 备注 
 7:i32 beginCycleNum;//坏账开始期数
 8:double badShouldAmt; // 坏帐处理后应收金额
 9:double badLossAmt;// 坏帐处理后损失金额
 10:i32 status; // 状态
} 
 
/*
 * 贷后管理--坏账呆账处理（页面展现）
 */
struct BadDebtBeas{
	1:i32 pid;  //主键
	2:string projectName;  //项目名称
	3:string projectNumber; //项目编号
	4:string cusType; //客户类别
	5:string badShouldAmt; //坏账处理后应收金额
	6:string shouldDt; //应收时间
	7:string badLossAmt; //坏账处理后损失金额
	8:string reviewStatus; //处理状态
	9:string requestDttm; //申请时间
	10:i32 projectId; // 项目主键
	11:i32 loanId;// 贷款Id
}
//  呆账坏账页面展现的搜索条件
struct BadDebtBeasCriteria{
	1:string projectName;  //项目名称
	2:string projectNumber; //项目编号
	3:string cusName;//客户名称
	4:string cusType; //客户类别
	5:i32 ecoTrade; //经济行业类别
	6:string reviewStatus; //处理状态
	7:string startRequestDttm;
	8:string endRequestDttm;
	9:i32 page;
	10:i32 rows;
}

// modify by qcxian --------------------------------------------------------------------------------------




/*
 * 贷后管理--违约处理
 */
struct BreachDisposeBeas{
	1:i32 pid;  //主键
	2:string projectName;  //项目名称
	3:string projectNumber; //项目编号
	4:string cusType; //客户类别
	5:string isTermination; //项目是否终止
	6:string isBacklist; //是否加入黑名单
	7:string violationAmt; //违约应收金额
	8:string violationDt;//违约金应收日期
	9:string reviewStatus; //处理状态
	10:string requestDttm; //申请时间
	11:i32 projectId;
	12:i32 violationId;//VIOLATION_ID
	15:string  completeDttm;// COMPLETE_DTTM
	
}

/*
 * 贷后管理--违约处理--条件查询
 */
struct BreachDisposeCriteria{
	1:string projectName;  //项目名称
	2:string projectNumber;  //项目编号
	3:string cusName; //客户类别
	4:string cusType; //客户类别
	5:string startRequestDttm;
	6:string endRequestDttm;
	7:string ecoTrade;
	8:string reviewStatus; //处理状态
	12:i32 violationId;//VIOLATION_ID
	15:string  completeDttm;// COMPLETE_DTTM
	16:i32 page;
	17:i32 rows;
}


/*
      违约pojo
*/
struct BizProjectViolation{
   1:i32 pid;  //主键
   2:i32 projectId;
   3:i32 isTermInAtion;//项目是否终止
   4:double loanAmt;//贷款金额
   5:double violationProportion;//违约金比例
   6:double violationAmt;//应收违约金额
   7:string violationDt;//应收日期
   8:double violationOtInterest;//违约金逾期费率
   9:i32 isBackList;//是否加入黑名单
   10:i32 reviewStatus; //违约处理审核状态
   11:string remark;
   12:i32 status;
   13:i32 requestStatus;//request_status
   14:string  requestDttm;// REQUEST_DTTM
   15:string  completeDttm;// COMPLETE_DTTM
   16:i32 regHistoryId;// 监管记录ID
   17:i32 badId; // 坏账记录ID
}

/*
违约处理说明文档 pojo
*/
struct bizProjectViolationFile {
     1:i32 pid;  //主键
     2:i32 violationId;  //违约处理ID
     3:i32 fileId;//文件ID
     4:i32 status;
}
 
 
 
/*
 * 贷后管理--催收记录
 */
struct CollectionRecord{
	1:i32 pid; //主键
	2:i32 reminderPlanId; //催收计划ID
	3:i32 cycleNum; //期数
	4:string reminderDttm;  //催收时间
	5:i32 reminderUserId;   //催收人 
	6:string reminderSubject;   //标题
	7:string reminderMsg;  //催收内容
	8:i32 methodMall;  //催收方式-邮件
	9:i32 methodSms; //催收方式-短信
	10:i32 methodPhone; //催收方式-电话
	11:i32 methodPaper;  //催收方式-纸质信件	
	12:string telephone;//联系人电话
	13:string mailAdds;//联系人邮箱地址
	14:i32 projectId;//
	15:i32 acctId;//客户编号
	16:string reminderWay; //催收方式 名称
	17:string reminderUserName //催收人员名称
	18:i32 rNum; //行号
}


/*
 * 贷后管理--还款催收任务分配
 */
struct AssignmentDistribution{
	1:i32 pid; //主键
	2:string projectName; //项目名称
	3:string projectId;//项目编号
	4:string acctType;//客户类别
	5:string appDate;//申请时间
	6:string telephone;//手机号码
	7:double creditAmt; //贷款金额
	8:double balanceAmt; //到期未收金额
	9:i32 userId; //催收业务员id
	10:string realName; //催收业务员
	11:string planOutLoanDt; //计划催收时间
	12:i32 rows;
	13:i32 reminderId;
	14:string userIdStr;
	
	
}

/*
 * 贷后管理--挪用处理列表
 * 表：biz_project 项目信息表 biz_loan 贷款信息表
 */
struct MisapprProcess{
	1:i32 pid;
	2:string projectName;
	3:string projectNumber;
	4:string cusType;
	5:string divertAmt;    //挪用金额 
	6:string divertFineEndDt;  //挪用罚息结束日期
	7:string reviewStatus;   //挪用处理审核状态
	8:string requestDttm;  //项目申请时间
	9:string divertFinePayAmt;   //挪用罚息
	10:string divertFinePayDt;   //挪用交钱日期
	11:i32 divertId;//DIVERT_ID
	12:string cusName;
	13:i32 rows;
    14:i32 page;
   	15:i32 pmUserId;
}

struct MisapprProcessCriteria{
	1:i32 pid;
	2:string projectName;
	3:string projectNumber;
	4:string cusType;
	5:string divertAmt;    //挪用金额 
	6:string divertFineEndDt;  //挪用罚息结束日期
	7:string reviewStatus;   //挪用处理审核状态
	8:string requestDttm;  //项目申请时间
	9:string divertFinePayAmt;   //挪用罚息
	10:string divertFinePayDt;   //挪用交钱日期
	11:i32 divertId;//DIVERT_ID
	12:string cusName;
	13:i32 rows;
    14:i32 page;
    15:i32 pmUserId;
    16:i32 ecoTrade; //经济行业类别
}

/*
       监管计划表 biz_project_reg_plan
*/
struct BizProjectRegPlan{
   1:i32 pid;
   2:i32 projectId;
   3:i32 regulatoryUserId;
   4:string planBeginDt;
   5:i32 assigneUserId;
   6:i32 regulatoryStatus;
   7:i32 isMail;
   8:i32 isSms;
   9:string planDt;
   10:string remark;
   11:i32 status;
}


/*监管查询条件*/
struct SupervisionSearchBean{
	1:string projectName;   // 项目名称
	2:string projectNumber;  // 项目编号
	3:string cusName;   // 客户名称
	4:i32 cusType;  // 客户类型
	5:string startRequestDttm;   // 开始时间
	6:string endRequestDttm;     // 结束时间
	7:string outRequestDttm;
	8:string startDttm;
	9:string endDttm;
	10:string startApplyDttm;
	11:string endApplyDttm;
	12:string supervisionPeople;
	13:string startSupervisionDttm;
	14:string endSupervisionDttm;
	15:i32 distributionStatus;
	16:i32 ecoTrade;//行业级别
	17:i32 id; //通用id
	18:string userName;//监管人---》系统用户
	19: i32 page;
	20: i32 rows;
	21:i32 projectId; // 项目ID
	22:i32 pid; // 计划ID
	23:i32 regulatoryStatus; //  监管状态
	24:string regulatoryUserName; // 计划监管人
	25:i32 userId;//当前登录人ID
}

/*监管业务查询结果*/
struct Supervision{
    1:i32 pid;
	2:string projectName;
	3:string projectNumber;
	4:string cusType;
	5:string startRequestDttm;//贷款申请时间
	6:string phone;
	7:double creditAmt;//贷款金额
	8:double inMoneyAndOutDttm;//到期未收金额
	9:string custypestr;
	10:string regulatoryuser;
    11:string planBeginDt;
    12:string remark;
}

/* 监管任务查询*/
struct ResultBizProjectRegPlan{
   1:i32 pid;  // 计划ID
   2:i32 projectId;
   3:i32 regulatoryUserId;  //  计划监管人ID
   4:string planBeginDt;  // 执行监管时间
   5:i32 assigneUserId;  // 执行监管的人
   6:i32 regulatoryStatus; // 监管状态  1：等待监管  2：监管中  3：等待完成
   7:i32 isMail;
   8:i32 isSms;
   9:string planDt;  // 计划监管时间
   10:string remark;
   11:i32 status;
   13:string cusTypeStr;// 客户类型
   14:string startRequestDttm;//贷款申请时间
   16:string regulatoryUser;
   17:string regulatoryResult;  // 监管结果：1：正常 2：挪用嫌疑 3：违约嫌疑 4：存在挪用行为 5:存在违约行为
   18:string userName;
   19:string actualBeginDt;
   20:string regulatoryStatusStr;  // 监管状态中文
   22:string projectName; // 项目名称
   23:string projectNumber; // 项目编号
   24:string regulatoryUserName;  //  计划监管人名称
   25:string assigneUserName;  // 执行监管名称
   26:i32 regulatoryPlanId;// 监管计划ID
}
/*
*还款计划表
*/
struct  ProjectReminderPlanDto{
	1:i32 pId;//主键
	2:i32 projectId;//项目编号
	3:i32 reminderUserId; //催收员
	4:string planBeginDt; //计划催收时间
	5:i32 isMail;//发邮件
	6:i32 isSms;//发短信
	7:string planDt; //催收时间
}


/*（执行监管） 监管记录表   */
struct BizProjectRegHistory{
   1:i32  pid;
   2:i32 regulatoryPlanId;//监管计划ID   REGULATORY_PLAN_ID
   3:i32  actualUserId; //ACTUAL_USER_ID
   4:string actualBeginDt;//实际监管时间 ACTUAL_BEGIN_DT
   5:string regualatorySubject;//监管标题 REGULATORY_SUBJECT
   6:string regualatoryContent;//监管内容 REGULATORY_CONTENT
   7:i32 regualatoryResult;//监管结果 REGULATORY_RESULT
   8:string regualatoryMsg;//监管意见 REGULATORY_MSG
   9:string regualatoryMsgOt1;//其他意见1 REGULATORY_MSG_OT1
   10:string regualatoryMsgOt2;//其他意见２ REGULATORY_MSG_OT2
   11:string remark;
   12:i32 status;
   13:string actualUserIdString;//实际监管人  ACTUAL_USER_ID_STRING
   14:string regualatoryResultStr;
}

/*监管记录资料  BIZ_PROJECT_REG_FILE*/
struct BizProjectRegFile{
   1:i32 pid;
   2:i32 regualatoryPlanId;  //REGULATORY_PLAN_ID
   3:i32 fileId;  //FILE_ID
   4:i32 status;
}

//监管结果查询--条件 
struct SupervisionResultSearch{
	1:string projectName;   // 项目名称
	2:string projectNumber;  // 项目编号
	3:string cusName;   // 客户名称
	4:i32 cusType;   // 客户类别
	5:string userName; //监管人
	6:string startRequestDttm;  // 监管时间段 -开始
	7:string endRequestDttm;    // 监管时间段 -结束
	8:i32 regulatoryResult;    // 监管结果  1、2、3、4、5  参考监管历史表中的
	9:i32 page;
	10:i32 rows;
	11: i32 projectId; // 项目Id
	12: i32 planId; // 监管计划（任务）Id
	13:string ecoTrade;
	
	
}
//监管结果查询---结果
struct SupervisionResultList{
    1:i32 resultId;  // 监管结果Pid
    2:i32 projectId; // 项目ID
    3:i32 regulatoryPlanId;// 监管计划ID
    4:string regualatorySubject;// 监管标题
    5:string regualatoryContent;// 监管内容
    6:string regualatoryMsg; // 监管意见
    7:string regualatoryMsgOt1;// 其他意见1
    8:string regualatoryMsgOt2;// 其他意见2
    9:string regulatoryResultStr; // 监管结果中文（正常、挪用、违约、xx嫌疑）
    10:string projectName;    // 项目名称
    11:string projectNumber;  // 项目编号
    12:string regulatoryStatusStr;  // 监管状态中文（待监管、监管中、监管完毕）
    13:string actualUser;  // 监管人
    14:string actualDate; // 监管日期
    15:string planUser; //  计划监管人
    16:string planDate; // 计划监管日
    17:string custType; // 客户类型
}
 
/*
 * 还款催收查询
 */
struct RepaymentCollection{
	1:i32 pid; //主键
	2:string projectName; //项目名称
	3:string projectId;//项目编号
	4:string acctType;//客户类别
	5:string appDate;//申请时间
	6:string telephone;//手机号码
	7:double creditAmt; //贷款金额
	8:double balanceAmt; //到期未收金额
	9:string realName; //催收业务员
	10:string planOutLoanDt; //计划催收时间
	11:string factReminderUser; //催收业务员
	12:string factPlanDt; //实际催收时间
	13:i32 planId;//催收计划编号
	14:i32 loanId;//款项id
	15:i32 total; //分页总数
}

/*
 * 查看还款催收记录
 */
struct CollectionRecordDto{
	1:i32 pid;//主键
	2:i32 cycleNum;//期数
	3:string reminderDt;//催收时间
	4:string reminderUser;//催收员
	5:i32 ctMethods;//催收方式
	6:string subject;//标题
	7:string content;//内容
	8:i32 count;//总记录数
}

/*
*客户信息
*/
struct CollectionCustomer{
	1:i32 projectId;//项目id
	2:i32 acctId;//客户id
	3:string acctName;//客户名称
	4:string telephone;//电话
	5:string email;//电子邮件
	6:string recipient;//收件人
	7:string address;//通信地址
	8:string postalCode;//邮政编码
	9:i32 loadId;//贷款id
	10:i32 acctType;
}

/*客户联系人信息*/
struct CollectionCusComContact {
	1:i32 pid;
	3: string cttName;//姓名
	4: i32 mainCtt;//主联系人
	5: string duty;//职务
	6: string department;//部门
	7: string remark;//备注
	8: string movePhone;//移动电话
	9: string fixedPhone;//固定电话
	10: string familyAddr;//家庭地址
	11: string comPhone;//单位办公电话
}
 
 /*
 * 贷后管理--挪用处理
 * 
 */
struct LoanDivertinfo{
	1:i32 pId;
	2:string projectName;
	3:string projectNumber;
	4:i32 loanId;
	5:i32 divertId;//挪用id
	6:i32 projectId;
	7:double divertAmt;// DIVERT_AMT挪用金额
	8:double divertFine; // DIVERT_FINE挪用罚息利率
	9:string divertFineEndDt; // DIVERT_FINE_END_DT挪用记息结束日期
	10:i32 regulatoryUserId; // REGULATORY_USER_ID挪用处理申请人
	11:string remark;//  REMARK 备注
	12:i32 reviewStatus; // REVIEW_STATUS 挪用处理审核状态
	13:i32 status;//  状态
	14:string divertFinePayDt; // DIVERT_FINE_PAY_DT   挪用交钱日期
	15:double divertFinePayAmt; // DIVERT_FINE_PAY_AMT 挪用罚息总额
	16:string planBeginDt;  // 计划监管时间
	17:i32 requestStatus;  // 
	18:string requestDttm;  // REQUEST_DTTM
	19:string completeDttm;  // COMPLETE_DTTM
	20:string divertFineBeginDt;  // DIVERT_FINE_BEGIN_DT
	21: string planOutLoanDt;//项目放款日期  用来计算挪用罚息PLAN_OUT_LOAN_DT
	
}
 
struct ProjectInfo{
   1:i32 pid;
   2:string projectName;
   3:string projectNumber;
   4:string businessCatelogName;
   5:string businessTypeName;
   6:string flowCatelogName;
   7:string accName;
}

/*
贷后还款催收单编号
*/
struct ReminderNoticeBills{
	1:i32 pid;
	2:string noticeYear;
	3:i32 noticeCurrentSeq;
}


/*
欠款清单
*/
struct ReminderNoticePart{
	1:string contractNo;
	2:double shouldAmt;
	3:string shouldDttm;
	4:double principal;//本金
	5:double interest;//利息
	6:i32 pid;
	7:i32 aType;
}


/*
贷后还款催收单
*/
struct ReminderNotice{
	1:string acctName;//客户名
	2:string accName;//小贷公司放款的主体
	3:string sysdateDttm;//当前时间
	4:string number;//编号
	5:string currency;//币种
	6:i32 shouldTotal;//债务金额总数
	7:double outStandPrincipal;//尚欠本金总额
	8:double outStandInterest;//尚欠利息总额
	9:string outStandPrincipalUp;//尚欠本金总额大写
	10:string outStandInterestUp;//尚欠利息总额大写
	11:i32 bankCardType;//主体类型
	12:string contractNO;//合同编号
}

/*
今日更新dto
*/
struct ToDayUpdateDTO{
	1:string toDate;		// 查询日期
	2:i32 type;				// 类型  1 个人 2 企业
	3:i32 businessType;		// 业务类别  1 放款 2 回本 4 退息 3 收息(DTL_TYPE 50) 收管理费(DTL_TYPE 40) 收其他费用(DTL_TYPE 60) 收逾期利息( 0 < DTL_TYPE < -1000 ) 收逾期罚息(-1000 < DTL_TYPE < -2000)
	4:i32 dtlType;			// 对账类型 对账类型 	收息(DTL_TYPE 50) 收管理费(DTL_TYPE 40) 收其他费用(DTL_TYPE 60) 收逾期利息( 0 < DTL_TYPE < -1000 ) 收逾期罚息(-1000 < DTL_TYPE < -2000)
	5:double outputAmt;		// 对账金额
	6:string outputDate;	// 对账日期
	7:string acctName;		// 客户名称
	8:i32 acctType;			// 客户类别 1 个人 2 企业 3 小计
	9:string pmUserName; 	// 业务经理 
	10:string isMortgage;	// 是否抵押 0 否 大于0 是
	11:i32 rowNum;			// 序号
	12:list<ToDayUpdateDTO> results;
}

 
/*
贷后监管
*/
service AfterLoanSupervisionService {

	// 获取今日更新数据
	list<ToDayUpdateDTO> getToDayUpdateList(1:ToDayUpdateDTO toDayUpdateDTO)throws (1:Common.ThriftServiceException e);
	
    //查询贷后监管查询
    //查询贷前贷款申请、额度提取申请且已经归档的项目
    //list<Supervision> supervisionList(1:SupervisionSearchBean supervisionSearchBean) throws (1:Common.ThriftServiceException e);
      
    list<Common.GridViewDTO> supervisionList(1:SupervisionSearchBean supervisionSearchBean) throws (1:Common.ThriftServiceException e);
    //總记录数
    i32 getTotalSupervisionList(1:SupervisionSearchBean supervisionSearchBean) throws (1:Common.ThriftServiceException e);
       
    i32 getTotalSupervisionTask(1:SupervisionSearchBean supervisionSearchBean) throws (1:Common.ThriftServiceException e);
    
     // 监管任务查询
    list<ResultBizProjectRegPlan> supervisionTaskList(1:SupervisionSearchBean supervisionSearchBean) throws (1:Common.ThriftServiceException e);
    
    //添加 监管计划
    i32 createBizProRegPlan(1:BizProjectRegPlan bizProjectRegPlan) throws (1:Common.ThriftServiceException e);
    
    //执行  监管  --添加 监管记录表
    i32 createBizProjectRegHistory(1:BizProjectRegHistory  bizProjectRegHistory)  throws (1:Common.ThriftServiceException e);
    // 打开监管页面，修改监管状态为jianguanz
    void updateBizProjectRegPlanStart(1:BizProjectRegPlan bizProjectRegPlan);
    
    // 监管记录资料---文件上传
    i32 createBizProjectRegFile(1:BizProjectRegFile bizProjectRegFile) throws (1:Common.ThriftServiceException e);
    
    //查询监管
    ResultBizProjectRegPlan getSupervisionTask(1:i32 pid) throws (1:Common.ThriftServiceException e);
    list<ResultBizProjectRegPlan> getSupervisionTaskList(1:i32 pid) throws (1:Common.ThriftServiceException e);
    //监管结果记录查询  pid--计划id
    list<BizProjectRegHistory> getBizProjectRegHistoryList(1:i32 pid) throws (1:Common.ThriftServiceException e);
    BizProjectRegHistory getBizProjectRegHistory(1:i32 pid) throws (1:Common.ThriftServiceException e);
    i32 updateBizProjectRegHistory(1:BizProjectRegHistory bizProjectRegHistory) throws (1:Common.ThriftServiceException e);
    i32 deleteResultSuperviseHistory(1:i32 pid) throws (1:Common.ThriftServiceException e);
    //获取项目
    ResultBizProjectRegPlan getProject(1:i32 pid) throws (1:Common.ThriftServiceException e);
    
    //更新监管计划
    i32 updateBizProjectRegPlan(1:BizProjectRegPlan bizProjectRegPlan) throws (1:Common.ThriftServiceException e);
    
     //更新监管计划监管状态
    i32 updateBizProjectRegPlanRegulatoryStatus(1:BizProjectRegPlan bizProjectRegPlan) throws (1:Common.ThriftServiceException e);
    
    //获取监管记录，根据项目ID或者计划ID来获取监管记录，主要用于页面的查看监管详情
    list<SupervisionResultList> getRegulatoryRecordList(1:SupervisionResultSearch supervisionResultSearch);
    //监管结果列表查询
    list<SupervisionResultList> getSupervisionResultList(1:SupervisionResultSearch supervisionResultSearch) throws (1:Common.ThriftServiceException e);
    
    
    i32 getTotalSupervisionResult(1:SupervisionResultSearch supervisionResultSearch) throws (1:Common.ThriftServiceException e);
    //更新计划
    i32 updateBizProRegPlan(1:BizProjectRegPlan bizProjectRegPlan) throws (1:Common.ThriftServiceException e);
   
    //删除监管计划
    i32 deleteSupervisePlan(1:string pids) throws (1:Common.ThriftServiceException e);
    //获取项目信息
    list<ProjectInfo> getProjectData(1:i32 pid) throws (1:Common.ThriftServiceException e);
    
     /**文件管理   --**/
    list<File.FileInfo> getFileDataList(1:File.FileInfo fileInfo);
    i32 getFileDataTotal(1:File.FileInfo fileInfo);
    bool deleteFileData(1:i32 dataId,2:i32 fileId);
    // 新增或者更改业务资料文件
    i32 saveOrUpdateFileData(1:File.FileInfo fileInfo,2:System.BizFile bizFile);
    /**文件管理 --**/
    
}


/*
 * 贷后管理--还款催收任务分配查询参数
 */
struct AssignmentDistributionSearch{
	1:i32 pid; //主键
	2:string projectName; //项目名称
	3:string projectNum;//项目编号
	4:string cusName;//客户名称
	5:i32 ecoTrade;//经济行业类别
	6:string expireStartDt;//贷款到期起始时间
	7:string expireEndt;//贷款到期截止时间
	8: i32 overdueStartDay;//逾期期数开始数
	9: i32 overdueEndDay;//逾期期数结束数
	10: string requestStartDt;//贷款申请时间开始日期
	11: string requestEndDT;//贷款申请时间结束日期
	12:i32 reminderStatus;//催收分配状态
	13:string collectionUser;//催收业务员
	14:i32 acctType;//客户类别
	15: string planStartDt;//计划催收时间开始日期
	16: string planEndDT;//计划催收时间结束日期
	17:list<AssignmentDistribution> results; 
	18: i32 rows;
    19: i32 page; 
    20:i32 indexStart;
    21:i32 indexEnd;
}


/*
 * 贷后管理--还款催收查询查询参数
 */
struct RepaymentCollectionSearch{
	1:i32 pid; //主键
	2:string projectName; //项目名称
	3:string projectNum;//项目编号
	4:string cusName;//客户名称
	5:i32 ecoTrade;//经济行业类别
	6:string expireStartDt;//贷款到期起始时间
	7:string expireEndt;//贷款到期截止时间
	8:i32 overdueStartDay;//逾期期数开始数
	9:i32 overdueEndDay;//逾期期数结束数
	10:string requestStartDt;//贷款申请时间开始日期
	11:string requestEndDT;//贷款申请时间结束日期
	12:i32 reminderStatus;//催收分配状态
	13:string planCollectionUser;//计划催收业务员
	14:i32 acctType;//客户类别
	15:string planStartDt;//计划催收时间开始日期
	16:string planEndDT;//计划催收时间结束日期
	17:string factStartDt;//实际催收时间开始日期
	18:string factEndDT;//实际催收时间结束日期
	19:string factCollectionUser;//实际催收业务员
	20:list<RepaymentCollection> results;
	21: i32 rows;
    22: i32 page; 
    23:i32 indexStart;
    24:i32 indexEnd;
    25:i32 userId;
}



/*
 * 贷后
 * 
 * 违约  Service
 */
service MisapprProcessService {
 
	list<MisapprProcess> getMisappProcess (1:MisapprProcessCriteria misapprProcessCriteria) ;
	
	i32  getMisappProcessCount(1:MisapprProcessCriteria misapprProcessCriteria) throws (1:Common.ThriftServiceException e);

	list<BreachDisposeBeas> getBreachDispose (1:BreachDisposeCriteria breachDisposeCriteria) throws (1:Common.ThriftServiceException e);
	
	i32 getBreachDisposeTotal(1:BreachDisposeCriteria breachDisposeCriteria) throws (1:Common.ThriftServiceException e);
	
	list<BadDebtBeas> getBadDebt (1:BadDebtBeasCriteria badDebtBeasCriteria) throws (1:Common.ThriftServiceException e);
	
	//根据项目ｉｄ -->贷款ＩＤ　　　　 违规处理页面
	i32 getLoanId(1:i32 pid)  throws (1:Common.ThriftServiceException e);
	
	//创建违约
	i32 createBizProjectViolation(1:BizProjectViolation bizProjectViolation,2:BadDebtBean badDebtBean);
    //更新违约
    i32 updateBizProjectViolation(1:BizProjectViolation bizProjectViolation,2:BadDebtBean badDebtBean);
    //查询违约
    BizProjectViolation searchBizProjectViolation(1:i32 pid) throws (1:Common.ThriftServiceException e);
    // 通过主键查询违约记录
    BizProjectViolation getBizProjectViolationById(1:i32 pid);

    /**文件管理   --**/
    list<File.FileInfo> getFileDataList(1:File.FileInfo fileInfo);
    i32 getFileDataTotal(1:File.FileInfo fileInfo);
    bool deleteFileData(1:i32 dataId,2:i32 fileId);
    // 新增或者更改业务资料文件
    i32 saveOrUpdateFileData(1:File.FileInfo fileInfo,2:System.BizFile bizFile);
    
   
    bool deleteFile(1:i32 array) throws  (1:Common.ThriftServiceException e);
    bool deleteFileAndViolation(1:string array) throws  (1:Common.ThriftServiceException e);
    i32 createProjectViolationFile(1:File.FileInfo fileInfo) throws  (1:Common.ThriftServiceException e);
    i32 createFile(1:File.FileInfo fileInfo) throws (1:Common.ThriftServiceException e);
    i32 editFileAndViolation(1:File.FileInfo fileInfo) throws (1:Common.ThriftServiceException e);
    File.FileInfo getBizProjectViolationFile(1:i32 pid) throws (1:Common.ThriftServiceException e);
    //删除违约处理
    i32 deleteBreachDispose(1:i32 pid,2:i32 projectId) throws (1:Common.ThriftServiceException e);
    i32 searchAcctIdByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
    i32 addBlacklistRefuse(1:i32 projectId,2:i32 treatyId)throws (1:Common.ThriftServiceException e);
    /**文件管理 --**/
    
    
    //       modify by qcxian  start              
	/*
	 *  获取坏账记录信息
	 */
	 BadDebtBean getBadDebtBean(1:i32 pid);  
	/*
	 *  更新坏账信息
	 */
	i32 updateBadDebtBean(1:BadDebtBean badDebtBean,2:i32 loanId);
	/*
	 *  新增坏账信息
	 */
	i32 createBadDebtBean(1:BadDebtBean badDebtBean);
	// 更新患者信息状态(默认创建的时候是0，违约流程审批通过后，需要)
	i32 updateBadDebtBeanStatus(1:BadDebtBean badDebtBean);
	//  modify by qcxian  end    
	
	//add by yql start
	 list<LoanProReconciliationDtlView> getProjectReconciliationList(1: LoanProRecCondition loanProRecCondition) throws  (1:Common.ThriftServiceException e);
	//add by yql end   
}

 
 
//挪用处理
service  LoanDivertService {
//保存挪用处理info
	i32 insertLoanDivertService (1:LoanDivertinfo loanDivertinfo)throws (1:Common.ThriftServiceException e);
	//查询用处理info
	LoanDivertinfo queryLoanDivertServicebyDivertId (1:i32 divertId)throws (1:Common.ThriftServiceException e);
	//更新挪用处理info
  i32 updateLoanDivertinfo(1:LoanDivertinfo loanDivertinfo)throws (1:Common.ThriftServiceException e);
 //查询用处理项目info根据挪用id
 LoanDivertinfo queryProjectDivertbyDivertId(1:i32 divertId)throws (1:Common.ThriftServiceException e);
  //删除用处理项目info根据挪用id
 i32   delectDivertbyId(1:i32 divertId,2:i32 projectId)throws (1:Common.ThriftServiceException e);
   //改变挪用的申请状态
      i32 changeReqstDivert(1:i32 reqStatus,2:i32 divertId) throws (1:Common.ThriftServiceException e);
         LoanDivertinfo queryDivertinfobyprojectId(1:i32  projectId)throws (1:Common.ThriftServiceException e);

}

/*
 * 贷后管理- 还款催收
 */
service RepaymentCollectionService{


	/*
	 * 贷后管理- 还款催收任务分配
	 */
	list<AssignmentDistribution> getAssignmentDistribution (1:AssignmentDistributionSearch assignmentDistributionSearch);


	/*
	 * 贷后管理- 催收记录  Service
	 */
	list<CollectionRecord> getCollectionRecord (1:CollectionRecord collectionRecord);
	
	/*
	*添加催收记录
	*/
	i32 insertCollectionRecord(1:CollectionRecord collectionRecord)throws (1:Common.ThriftServiceException e);
	    
    /*
    *查看催收记录
    */
    list<CollectionRecordDto> getCollectionRecordList(1:map<string,i32> myMap);
    
    /*
	 * 催收专员任务分配新增
	 */
	i32 insertReminderPlan(1:ProjectReminderPlanDto projectReminderPlanDto) throws (1:Common.ThriftServiceException e);
    /*
	 * 催收专员任务分配编辑
	 */
	i32 updateReminderPlan(1:ProjectReminderPlanDto projectReminderPlanDto) throws (1:Common.ThriftServiceException e);
	
	/*
	 *还款催收查询
	 */
	list<RepaymentCollection> getRepaymentCollectionList (1:RepaymentCollectionSearch repaymentCollection);
	
	//根据项目编号查询客户信息
	CollectionCustomer getCollectionCustomer(1:i32 projectId);
	
	/*
	 *查询客户联系人信息
	 */
	list<CollectionCusComContact> getCollectionCusComContact(1:i32 acctId);
	
	i32 getRepaymentCollectionTotal(1:RepaymentCollectionSearch repaymentCollection);
	
	i32 getAssignmentDistributionTotal(1:AssignmentDistributionSearch assignmentDistributionSearch);
	
	list<CollectionRecord> getCollectionRecordsByProjectIds(1:string pids);
	
	/*
	 *通知单序号
	 */
	i32 getNoticeCurrentSeq(1:string year) throws (1:Common.ThriftServiceException e);
	
	i32 updateNoticeCurrentSeq(1:string year) throws (1:Common.ThriftServiceException e);
	
	/*
	 *通过项目Id查询清单Id、时间
	 */
	list<ReminderNoticePart> getReminderNoticePart(1:i32 projectId,2:string nowDttm) throws (1:Common.ThriftServiceException e);
	
	ReminderNoticePart getReminderNoticePartMoney(1:ReminderNoticePart reminderNoticePart,2:string nowDttm) throws (1:Common.ThriftServiceException e);

	ReminderNotice getReminderNotice(1:i32 projectId) throws (1:Common.ThriftServiceException e);
}


