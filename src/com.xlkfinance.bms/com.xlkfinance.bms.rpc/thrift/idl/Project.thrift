namespace java com.xlkfinance.bms.rpc.project
include "System.thrift"
include "Common.thrift"
include "Repayment.thrift"
include "Beforeloan.thrift"


struct ProjectDTO{
	1:i32 pid; // 主键
	2:i32 acctId; // 客户账号
	3:i32 projectType; // 项目类型（贷款=2、贷款展期=4 ,6机构提交业务 8=抵押贷）
	4:string projectName; // 项目名称
	5:string projectNumber; // 项目编号
	6:i32 pmUserId; // 项目经理ID
	7:string realName; // 项目经理姓名
	8:string beginRequestDttm;// 开始申请时间
	9:string endRequestDttm;// 结束申请时间
	10:string acctName;// 客户名称
	11:string businessSourceStr;//具体的来源名称
	12:double applyLoanMoney;//项目申请金额
	13:i32 productId;//产品ID 
	14:i32 productType;//产品类型（1、信用2、赎楼）
	15:list<i32> userIds;//数据权限
	16:i32 foreclosureStatus;//项目状态   房抵贷项目：1=待提交2=待评估3=带下户6=待复审7=待终审8=待放款申请9=待放款复核10=待资金放款11=还款中12=已结清
	17:i32 businessSourceNo;//业务来源具体数据
	18:string declaration;//录单员
	19:double loanMoney;//放款金额
	20:string nextUsers;//待办人
	21:string loanType;//放款条件
	22:string capitalName;//资方名称
	23:i32 page;// 
	24:i32 rows;// 
	25:i32 isChechan;//是否撤单（1：是，0：不是，默认为0）
	26:i32 recordClerkId;//录单员ID
	27:string requestDttm; // 申请时间
	28:string productName;//产品名称
	29:string loanWorkProcess;//贷前审批流程KEY值
	30:string nextUserName;//下一节点待办人
}

/*
 *项目联系人信息
 *2018-01-03 14:58:47
 **/
struct BizProjectContacts{
	1: i32 pid;
	2: i32 projectId;
	3: i32 acctId;//
	4: string contactsName;
	5: string contactsPhone;
	6: string contactsRalation;
	7: string createDate;
	8: i32 createrId;
	9: i32 updateId;
	10: string updateDate;
	11:i32 page;// 
	12:i32 rows;//
	13:list<i32> contactIdList;//
}

/*
 *逾期信息表
 *2017-12-12 15:54:54
 **/
struct BizProjectOverdue{
	1: i32 pid;//主键
	2: i32 projectId;//项目ID
	3: i32 loanPlanId;//还款计划ID
	4: double overdueMoney;//本期逾期金额
	5: double shouldPenalty;//应收罚息
	6: double actualPenalty;//实收罚息
	7: string actualPenaltyTime;//实收日期
	8: double actualOverdueMoney;//已收逾期金额
	9: double shouldOverdueMoney;//应收逾期金额
	10: string createDate;//创建时间
	11: i32 createrId;//创建人
	12: i32 updateId;//修改人
	13: string updateDate;//修改日期
	14: double overdueRate;//预期日罚息率
	15: i32 overdueDay;//逾期天数
	16:string remark;//备注
}

/*
 *正常还款信息表
 *2017-12-12 17:31:43
 **/
struct BizPaymentInfo{
	1: i32 pid;//主键
	2: i32 projectId;//项目ID
	3: i32 loanPlanId;//还款计划表ID
	4: double actualPrincipal;//实收本金
	5: double actualInterest;//实收利息
	6: double actualTotal;//实收合计
	7: string actualPrincipalTime;//实收日期
	8: string createDate;//创建时间
	9: i32 createrId;//创建人
	10: i32 updateId;//修改人
	11: string updateDate;//修改时间
}
/*
 * 可贷金额计算
 */
struct BizCalLoanMoney{
	1: double rentMoney;//租金
	2: double feeRate;//月利率
	3: i32 repaymentType;//还款方式
	4: i32 loanTerm;//借款期限
	5: double loanCoef;//贷款系数
	6: double rentalReturn;//租金回报
	7: double loanMoney;//借款金额
	8: double repaymentMoneyTotal;//总还款金额
	9: double monthlyRepaymentMoney;//月还款金额
	10: double payMoney;//借款人承担金额
	11: double resultMoney;//租金回报-总还款
	12: i32 rentTerm;//租赁期限
}

/*
 * 房抵贷贷款申请 Service
 */
service ProjectInfoService {
	//分页查询
	list<ProjectDTO> getProjectByPage(1:ProjectDTO query);
	//查询总数
	i32 getProjectCount(1:ProjectDTO query);
	 //生成还款计划表
	list<Repayment.RepaymentPlanBaseDTO> makeRepaymentPlan(1:i32 projectId,2:i32 userId) throws (1:Common.ThriftServiceException e);//生成还款计划表
	//根据项目ID查询该项目的还款计划表
	list<Repayment.RepaymentPlanBaseDTO> getRepaymentsByProjectId(1:i32 projectId);
	//根据当前时间生成逾期信息，处理逾期信息
	i32 makeProjectOverdue(1:string planRepayDt);
	//财务明细还款计划列表查询
	list<Repayment.RepaymentDetailIndexDTO> queryRepayment(1:Repayment.RepaymentDetailIndexDTO query);
	//财务收取弹窗页面逾期以及收取的详情
	Repayment.RepaymentDetailIndexDTO queryOverdueByProject(1:i32 projectId);
	//保存抵押贷还款信息
	i32 saveRepaymentDetailInfo(1:Repayment.RepaymentDetailIndexDTO result);
	
	// 保存房抵贷贷款申请信息
	map<string, string> saveProjectInfo(1:Beforeloan.Project project) throws (1:Common.ThriftServiceException e);
	// 保存消费贷贷款申请信息
	map<string, string> saveConsumeProjectInfo(1:Beforeloan.Project project) throws (1:Common.ThriftServiceException e);
	
	// 根据抵押贷项目id获取贷款详细信息
	Beforeloan.Project getLoanProjectByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	//保存或者更新提前还款信息，根据抵押贷还款规则，一个项目只能有一条提前还款信息
	i32 saveOrUpdateRepayInfo(1:Repayment.PreApplyRepayBaseDTO repayInfo);
	/*
	 * 可贷金额计算
	 */
	list<BizCalLoanMoney> makeBizCalLoanMoney(1:BizCalLoanMoney condition);
	// 保存下一任务节点处理人信息
	i32 setNextUserInfo(1:string candidateUsers, 2:i32 projectId) throws (1:Common.ThriftServiceException e);
	//生成消费贷的还款计划表
	list<Repayment.RepaymentPlanBaseDTO> queryRepaymentPlan(1:i32 projectId,2:i32 userId,3:string receDate) throws (1:Common.ThriftServiceException e);//生成还款计划表
	
}

/*逾期信息表Service*/
service BizProjectOverdueService{
    //根据条件查询所有逾期信息表
	list<BizProjectOverdue> getAll(1:BizProjectOverdue query);
	//查询逾期信息表
	BizProjectOverdue getById(1:i32 pid);
	//新增逾期信息表
	i32 insert(1:BizProjectOverdue bizProjectOverdue);
	//修改逾期信息表
	i32 update(1:BizProjectOverdue bizProjectOverdue);
}


/*项目联系人信息Service*/
service BizProjectContactsService{
    //根据条件查询所有项目联系人信息
	list<BizProjectContacts> getAll(1:BizProjectContacts bizProjectContacts);
	//查询项目联系人信息
	BizProjectContacts getById(1:i32 pid);
	//新增项目联系人信息
	i32 insert(1:BizProjectContacts bizProjectContacts);
	//修改项目联系人信息
	i32 update(1:BizProjectContacts bizProjectContacts);
	//根据项目id查找项目联系人
	list<BizProjectContacts> getContactsByProjectId(1:i32 projectId);
	//删除联系人
	i32 deleteProjectContact(1:list<i32> contactsId);
}

/*正常还款信息表Service*/
service BizPaymentInfoService{
    //根据条件查询所有正常还款信息表
	list<BizPaymentInfo> getAll(1:BizPaymentInfo bizPaymentInfo);
	//查询正常还款信息表
	BizPaymentInfo getById(1:i32 pid);
	//新增正常还款信息表
	i32 insert(1:BizPaymentInfo bizPaymentInfo);
	//修改正常还款信息表
	i32 update(1:BizPaymentInfo bizPaymentInfo);	
}