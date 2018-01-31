namespace java com.xlkfinance.bms.rpc.beforeloan
include "System.thrift"
include "Common.thrift"
include "Beforeloan.thrift"

/*
 * 客户授信
 * 表：BIZ_CREDIT 
 */
struct Credit{
	1:i32 pid;// 主键
	2:i32 projectId; // 项目id
	3:double creditAmt;// 授信额度
	4:string credtiStartDt;// 授信开始日期
	5:string credtiEndDt;// 授信结束日期
	6:i32 isHoop;// 是否循环
	7:i32 status;// 状态 
	8:i32 requestStatus; // 申请状态
	9:string requestDttm; // 申请时间
	10:string completeDttm; // 完成时间
	11:i32 creditStatus;//  授信状态 (0贷款项目  1 有效  2无效)
}


/*
 * 额度使用记录
 * 表：BIZ_CREDIT_LIMIT_RECORD
 */
struct CreditLimitRecord{
	1:i32 pid;// 主键
	2:i32 creditId; // 授信额度ID
	3:i32 creditUseType;// 额度调整类型（授信、使用、冻结、解冻）
	4:string creditUseTypeText;// 额度调整类型（授信、使用、冻结、解冻） text
	5:double amt;// 金额
	6:double requestAmt;// 冻结/解冻申请金额
	7:string reason;// 事由 
	8:string creDttm;// 额度变化时间 
	9:i32 status;// 状态 
	10:i32 loanId;// 财务对账需要  
}


/*
 * 尽职调查报告
 * 表：BIZ_PROJECT_SURVEY_REPORT
 */
struct ProjectSurveyReport{
	1:i32 pid;// 主键
	2:i32 projectId;// 项目ID
	3:string introduction;// 简介
	4:string projectSource;//项目来源
	5:string financialStatus;// 财务状况
	6:string loanPurposes;//贷款用途
	7:string repayAnalysis;// 还款能力分析
	8:string riskWarning;//风险提示
	9:string assuranceMeasures;//保证措施
	10:string surveyResults;// 调查结论
	11:i32 status;// 状态
	12:string specialDesc;//特殊情况说明
}

/*
 * 文件信息
 * 表：BIZ_FILE
 */
struct File{
	1:i32 pid;// 主键
	2:string fileName;// 文件名
	3:string fileType;// 文件类型 value
	4:double fileSize;// 文件大小(KB)
	5:string uploadDttm;// 上传时间
	6:i32 uploadUserId;// 上传用户ID
	7:string fileUrl;// 文件路径
}

/*
 * 授信资料文件
 * 表：BIZ_CREDIT_FILE
 */
struct CreditFile{
	1:i32 pid;// 主键
	2:i32 creditId;// 授信ID
	3:i32 fileId;// 文件ID
	4:i32 fileSource;// 授信资料文件类型
	5:string fileSourceText;// 授信资料文件类型 text
	6:string fileProperty;// 文件属性
	7:string fileDesc;// 上传说明
	8:i32 status;// 状态
}

/*
 * 线下决议会议纪要
 * 表：BIZ_CREDIT_MEETING_MINUTES
 */
struct CreditMeetingMinutes{
	1:i32 pid;// 主键
	2:i32 creditId;// 授信ID
	3:string meetingDttm;// 会议时间
	4:i32 recordUserId;// 记录人员ID
	5:string recordUserName;// 记录人员姓名
	6:string meetingLocation;// 会议地点
	7:string meetingResult;// 会议结果
	8:i32 status;// 状态
}

/*
 * 参与人员
 * 表：BIZ_MEETING_MINUTES_MEMBER
 */
struct MeetingMinutesMember{
	1:i32 pid;// 主键
	2:i32 meetingId;// 会议ID
	3:i32 meetingMemberUserId;// 参与人员用户ID
	4:string meetingMemberUserName;// 参与人员用户姓名
}

/*
 * 会议纪要附件
 * 表：BIZ_MEETING_MINUTES_FILE
 */
struct MeetingMinutesFile{
	1:i32 pid;// 主键
	2:i32 meetingId;// 会议ID
	3:i32 meetingMemberUserId;// 参与人员用户ID
	4:string meetingMemberUserName;// 参与人员用户姓名
}

/*
 * 审批信息
 * 表：BIZ_PROJECT_APPROVAL_INFO
 */
struct ProjectApprovalInfo{
	1:i32 pid;// 主键
	2:i32 projectId;// 项目ID
	3:i32 infoType;// 审批消息类型   1:放款前落实条件  2:贷后管理要求
	4:string infoTypeText;// 审批消息类型 text
	5:string infoContent;// 落实条件或管理要求
	6:i32 approvalUserId;// 审批人员ID
	7:string approvalUserName;// 审批人员姓名
	8:i32 status;// 状态
	9:i32 isConfirm;// 确认状态
	10:i32 confirmUserId;// 确认人员ID
	11:string pids;// 自定义字段 -- 所选人员的ID(1,2,3)
	12:i32 interestChgId;// 利率id
}

/*
 * 保证信息
 * 表：BIZ_PROJECT_ASSURE
 */
struct ProjectAssure{
	1:i32 pid;// 主键
	2:i32 projectId;// 项目ID
	3:i32 assureType;// 保证类型(个人、法人)
	4:string assureTypeText;// 保证类型(个人、法人) text
	5:i32 refId;// 引用ID（个人：人员ID,法人：企业ID）
	6:i32 status;// 状态
	7:string dataObject; // 数据对象(页面所需显示的值  个人:显示个人姓名  企业:显示企业名称)
	8:string refIds;// 引用ID字符串
	9:string chinaName;// 姓名（个人）
	10:string sexText;// 性别（个人）
	11:string certTypeText;// 客户关系（个人）
	12:string telephone;// 电话（个人）
	13:string mobilePhone;// 手机（个人）
	14:string occupation;// 职业（个人）
	15:string workUnit;// 单位（个人）
	16:string unitPhone;// 单位电话（个人）
	17:string unitAddr;// 单位地址（个人）
	18:string cpyName;// 企业名称 (企业)
	19:string orgCode;// 组织机构代码 (企业)
	20:string busLicCert;// 营业执照号 (企业)
	21:string comAllNatureText;// 所用制性质 (企业)
	22:string comOwnName;// 法人代表 (企业)
	23:string regMoney;// 注册资金 (企业)
	24:string cusTelephone;// 联系电话 (企业)
	25:string foundDate;// 成立日期 (企业)
	26:string cpyAbbrName;// 企业简称 (企业)
}


/*额度DTO*/
struct CreditsDTO {
    1:i32 projectId; //项目Id
    2:string projectNumber; //项目编号
    3:string projectName; //项目名称
    4:string acctName; //客户名称
    5:i32 ecoTrade; //经济行业类型
    6:i32 acctType; //客户类型
    7:i32 isHoop; //是否循环
    8:string isHoopVal; //是否循环值
    9:string credtiStartDt; //授信开始日期
    10:string credtiEndDt; //授信结束日期
	11:string beginRequestDttm; // 开始申请时间
	12:string endRequestDttm; // 结束申请时间
    13:double creditAmt; // 授信总额
    14:double extractionAmt; // 已提取额度     
    15:double freezeAmt; // 冻结额度
    16:double availableAmt; // 可用额度
	17:i32 page; // 页码
	18:i32 rows; // 每页显示数量
	19:i32 creditId; // 授信ID
	20:i32 refId; // 引用人ID
	21:i32 cusType; // 客户类型ID
	22:string dataObject;// 合同数据对象
	23:i32 pmUserId;// 项目经理ID
	24:string pmUser;// 项目经理名称
	25:string requestDttm;// 申请时间
	26:i32 relationType; //人员类型
}

/*额度提取列表*/
struct CreditsLineDTO {
	1:i32 projectId;//项目Id;
	2:string projectNumber;//项目编号
	3:string projectName;//项目名称
	4:i32 requestStatus; // 申请状态int
	5:string requestDttm;//申请时间
	6:i32 acctType; //客户类型
	7:double creditAmt;//提取金额
	8:string pmUser;//项目经理名称
	9:string planOutLoanDT;//放款日期
	10:string planRepayLoanDT;//还款日期
	11:string requestStartDt;//申请开始时间
	12:string requestEndDt;//申请结束时间
	13:i32 page;
	14:i32 rows;
	15:string requestStatusVal;//申请状态string
	16:string cusName;//客户名称
	17:i32 ecoTrade;//经济行业类型
	18:i32 oldProjectId;//授信Id
	19:i32 pmUserId;// 项目经理ID
}

/**收款信息*/

struct ProjectCreLoanDTO{
	1: i32 loanId;
	2: list<Beforeloan.Project> projects;
}

/*授信额度服务*/
service CreditsService {
	list<CreditsDTO> getAllCredits(1:CreditsDTO creditsDTO);
	i32 saveFreeze(1:CreditLimitRecord creditLimitRecord)throws (1:Common.ThriftServiceException e);//冻结、解冻
	list<CreditsDTO> getCreditsInfo(1:CreditsDTO creditsDTO);
	CreditLimitRecord getCreditLimitRecord(1: i32 pid)throws (1:Common.ThriftServiceException e);
	i32 getAllCreditsDTOCount(1:CreditsDTO creditsDTO);
	list<CreditsLineDTO> getCreditsLine(1:CreditsLineDTO creditsLineDTO)throws (1:Common.ThriftServiceException e);
	i32 getCreditsLineTotal(1:CreditsLineDTO creditsLineDTO)throws (1:Common.ThriftServiceException e);
	bool deleteCreditsLine(1:string pidArray)throws (1:Common.ThriftServiceException e);
	i32 saveCreditInfo(1:CreditLimitRecord creditLimitRecord);
	// 根据项目ID查询所有的保证信息
	list<ProjectAssure> getAssureByProjectId(1:i32 projectId);
	// 查询个人的保证信息
	list<ProjectAssure> getProjectAssureByPersonal(1:string projectIds);
	// 查询个人的保证信息
	list<ProjectAssure> getProjectAssureByEnterprise(1:string projectIds);
	// 新增担保信息
	i32 addProjectAssure(1:ProjectAssure projectAssure)throws (1:Common.ThriftServiceException e);
	// 删除担保信息
	i32 deleteProjectAssure(1:string refIds)throws (1:Common.ThriftServiceException e);
	// 查询授信合同信息
	list<CreditsDTO> getProjectAcctAndPublicManByProjectId(1:i32 projectId);
	
	i32 getCreditsLineProjectId(1:i32 projectIds)throws (1:Common.ThriftServiceException e);
}

/*
 * 尽职调查报告详情 Service
 */
service ProjectSurveyReportService{
	i32 addSurveyReport(1:ProjectSurveyReport projectSurveyReport) throws (1:Common.ThriftServiceException e);
	i32 updateSurveyReport(1:ProjectSurveyReport projectSurveyReport) throws (1:Common.ThriftServiceException e);
	ProjectSurveyReport getSurveyReportByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	
}

/*
 * 贷前申请流程 Service
 */
 service ProceduresService{
 	i32 addProjectApprovalInfo(1:ProjectApprovalInfo projectApprovalInfo) throws (1:Common.ThriftServiceException e);
 	i32 updProjectApprovalInfo(1:ProjectApprovalInfo projectApprovalInfo) throws (1:Common.ThriftServiceException e);
 	i32 deleteProjectApprovalInfo(1:string pids)throws (1:Common.ThriftServiceException e);
 	// 查询落实条件贷审会信息
 	list<ProjectApprovalInfo> getProjectApprovalLs (1:i32 projectId)throws (1:Common.ThriftServiceException e);
 	// 查询管理条件贷审会信息
 	list<ProjectApprovalInfo> getProjectApprovalGl (1:i32 projectId)throws (1:Common.ThriftServiceException e);
 	
 	// 查询利率变更落实条件贷审会信息
 	list<ProjectApprovalInfo> getProjectApprovalLsInfo (1:i32 interestChgId)throws (1:Common.ThriftServiceException e);
 	// 查询利率变更管理条件贷审会信息
 	list<ProjectApprovalInfo> getProjectApprovalGlInfo (1:i32 interestChgId)throws (1:Common.ThriftServiceException e);
 	// 修改是否确认
 	i32 updateIsConfirmPrimaryKey(1:ProjectApprovalInfo projectApprovalInfo) throws (1:Common.ThriftServiceException e);
 	//修改利率变更是否确认
 	i32 updateIsConfirmPrimaryKeyInfo(1:ProjectApprovalInfo projectApprovalInfo) throws (1:Common.ThriftServiceException e);
 	//删除项目贷审会信息
 	i32 deleteProjectApprovalByProjectId(1:i32 projectId)throws (1:Common.ThriftServiceException e);
 	//获取利率变更管理要求信息
 	list<ProjectApprovalInfo> getProjectApprovalAll (1:i32 interestChgId)throws (1:Common.ThriftServiceException e);
 	//删除利率变更贷审会信息
 	i32 deleteProjectApprovalResInfo(1:string pids)throws (1:Common.ThriftServiceException e);
 }

