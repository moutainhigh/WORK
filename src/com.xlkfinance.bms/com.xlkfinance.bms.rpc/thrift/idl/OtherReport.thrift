namespace java com.xlkfinance.bms.rpc.otherReport
include "System.thrift"
include "Common.thrift"
include "File.thrift"

/*赎楼贷资金方业务统计详情笔数*/
struct ForeclosureCapDetailsReport{
	1:i32 projectId;//项目ID
	2:string projectName;//项目名称
	3:string projectNumber;//项目编号
	4:string pmUserName;//经办人
	5:string chinaName;//借款人
	6:string businessCategoryStr;//业务类型  
	7:string orgName;//资方机构
	8:string orgCode; //机构编码
	
	9:double loanAmount;//借款金额
	10:double approveMoney;//审批金额
	11:i32 approvalStatus;//审核状态（1：审核中:2：通过，3：未通过）
	12:i32 confirmLoanStatus;//确认要款状态 （1：未发送  2：审核中 3：审核通过 4 审核不通过 ）
	13:i32 loanStatus;//放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
	14:string approvalComment;//审批意见
	15:string approvalTime;//审批时间
	16:string loanTime;//请求放款时间
	17:i32 applyDate;//借款期限
	18:double payInterest;//付资方费用
	19:string loanResultTime;//放款结果时间
	20:string partnerLoanDate;//资方放款时间
	21:string recLoanDate;//回款时间(资产机构 )
	22:string refundDate;//我司还资方款时间
	23:list<i32> userIds;
	24:i32 page;
	25:i32 rows;

	26:i32 chooseMonthOrWeek;//0:月 1：周 2：日（仅作范围查询）
	27:string submitApprovalTime;//提交审核时间
	28:string reMonth;//日期
	29:string fromDay;//仅作查询
	30:string endDay;//仅作查询
	31:string approvalStatusName;//审批状态（导出用 审核中=1,通过=2,未通过=3）
	32:string loanStatusName;//仅作导出  放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
	33:string confirmLoanStatusName;//仅作查询  要款状态（1：未发送  2：审核中 3：审核通过 4 审核不通过）
	
}

struct ForeclosureOrgDetailsReport{
	1:i32 projectId;//项目ID
	2:string projectName;//项目名称
	3:string projectNumber;//项目编号
	4:string loanDate;//出款日期
	5:string orgOrgName;//合作机构
	6:string pmUserName;//经办人
	7:string chinaName;//借款人
	8:double loanAmount;//借款金额
	9:i32 loanDays;//借款期限
	10:double guaranteeFee;//预收咨询费
	11:double poundage;//预收手续费
	12:string paymentDate;//预计回款日期
	
	13:string newPepaymentDate;//回款日期
	14:double repaymentMoney;//回款金额
	15:string orgName;//资方机构
	16:list<i32> userIds;
	17:i32 page;
	18:i32 rows;
	19:i32 chooseMonthOrWeek;//0:月 1：周 2：日（仅作范围查询）
	20:string reMonth;//日期
	21:string fromDay;//仅作查询
	22:string endDay;//仅作查询
	23:i32 includeWt;//0:不包含万通数据；1：包括万通数据
}

service OtherReportService {
	//资金方新增详情业务统计（分页查询,含导出）
	list<ForeclosureCapDetailsReport> queryForeclosureCapNewDetails (1:ForeclosureCapDetailsReport query);
	//资金方新增详情业务统计总数
	i32 queryForeclosureCapNewDetailsTotal (1:ForeclosureCapDetailsReport query);
	
	//资金方详结清情业务统计（分页查询,含导出）
	list<ForeclosureCapDetailsReport> queryForeclosureCapSquareDetails (1:ForeclosureCapDetailsReport query);
	//资金方详情结清业务统计总数
	i32 queryForeclosureCapSquareDetailsTotal (1:ForeclosureCapDetailsReport query);
	
	//资金方详在途情业务统计（分页查询,含导出）
	list<ForeclosureCapDetailsReport> queryForeclosureCapIngDetails (1:ForeclosureCapDetailsReport query);
	//资金方详情在途业务统计总数
	i32 queryForeclosureCapIngDetailsTotal (1:ForeclosureCapDetailsReport query);
	
	//合作机构新增详情业务统计（分页查询,含导出）
	list<ForeclosureOrgDetailsReport> queryForeclosureOrgNewDetails (1:ForeclosureOrgDetailsReport query);
	//合作机构新增详情业务统计总数
	i32 queryForeclosureOrgNewDetailsTotal (1:ForeclosureOrgDetailsReport query);
	
	//合作机构结清详情业务统计（分页查询,含导出）
	list<ForeclosureOrgDetailsReport> queryForeclosureOrgSquareDetails (1:ForeclosureOrgDetailsReport query);
	//合作机构结清详情业务统计总数
	i32 queryForeclosureOrgSquareDetailsTotal (1:ForeclosureOrgDetailsReport query);
	
	//合作机构在途详情业务统计（分页查询,含导出）
	list<ForeclosureOrgDetailsReport> queryForeclosureOrgIngDetails (1:ForeclosureOrgDetailsReport query);
	//合作机构在途详情业务统计总数
	i32 queryForeclosureOrgIngDetailsTotal (1:ForeclosureOrgDetailsReport query);
}