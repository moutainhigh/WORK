namespace java com.qfang.xk.aom.rpc.finance
include "../Common.thrift"
include "../System.thrift"
include "Page.thrift"
include "System.thrift"

/*
 *机构订单数据汇总（用于机构首页的数据展现）
 *2016-08-01 14:19:00
 **/
struct OrgOrderSummary{
    1: i32 orgId;//机构id
	2: double availableMoney;//剩余可用金额
	3: double frozenMoney;//冻结金额
	4: double feeSettleTotalMoney;//息费合计
	5: i32 hisTotal;//历史成交笔数
	6: double hisTotalMoney;//历史成交总金额
	7: i32 curTotal;//当前订单总数
	8: i32 applyTotal;//申请中笔数
	9: double applyTotalMoney;//申请中金额
	10: i32 reinsuranceTotal;//再保笔数
	11: double reinsuranceTotalMoney;//再保金额
}
/*
 *合伙人订单数据汇总（用于合伙人首页的数据展现）
 *2016-08-01 17:07:00
 **/
struct PartnerOrderSummary{
    1: i32 partner;//合伙人id
	2: double notFeeSettleTotalMoney;//未结算金额
	3: double feeSettleTotalMoney;//已结算金额
    4: i32 orgTotal;//拥有机构总数
}


/*
 *机构放款列表
 *2016-07-19 11:42:06
 **/
struct OrgMakeLoansIndex{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
	3: i32 pid;//主键
    4: i32 projectId;//项目ID
    5: string projectNumber;//项目编号
    6: string projectName;//项目名称
    7: i32 status;//收费状态：未收费=1，已收费=2，已放款=3，放款未完结=4
	8: double realLoan;//放款金额
    9: double loanMoney;//贷款金额
	10: i32 projectStatus;//项目状态
	11: i32 projectSource;//项目来源
	12: i32 projectType;//项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4  授信and贷款=5）
	13: double extensionFee;//展期费
	14: string houseName;//物业名称
	15: string buyerName;//买方姓名
	16: string sellerName;//卖方姓名
	17: i32 isChechan;//是否撤单:是=1，否=0
	18: i32 makeLoansApplyFinanceId;//申请财务受理信息的第一次放款id
	19: i32 makeLoansApplyFinanceApplyStatus;//申请财务受理信息的第一次放款的申请状态
	20: i32 orgId;//机构id（作数据权限）
	21: i32 applyUserId;//订单申请用户id（作数据权限）
	22: string makeLoansDate;//放款时间
	23: string makeLoansDateEnd;//放款时间（仅作范围查询）
}

/*机构回款首页列表*/
struct OrgRepaymentIndex{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
	3: i32 orgId;//机构id（作数据权限） 
    4: i32 repaymentId;//回款ID
    5: i32 projectId;//项目ID
    6: string projectNumber;//项目编号
    7: string projectName;//项目名称
    8: double realRepaymentMoney;//实际回款金额
    9: string realRepaymentDate;//实际回款时间
    10: string realRepaymentDateEnd;//实际回款时间（仅作范围查询）
    11: double planRepaymentMoney;//预计回款金额
    12: string planRepaymentDate;//预计回款时间
    13: string planRepaymentDateEnd;//预计回款时间（仅作范围查询）
    14: i32 repaymentStatus;//回款状态：未回款=1，已回款=2
    15: double overdueFee;//逾期费
    16: i32 overdueDay;//逾期天数
    17: string houseName;//物业名称
	18: string buyerName;//买方姓名
	19: string sellerName;//卖方姓名
	20: i32 applyUserId;//订单申请用户id（作数据权限）
}
/*
 *保证金记录
 *2016-09-02 15:46:08
 **/
struct OrgAssureFundRecord{
	1: i32 pid;//
	2: double money;//金额
	3: string recDate;//到账日期
	4: string recDateEnd;//
	5: string accountName;//账户名称
	6: string account;//账号
	7: string bank;//银行
	8: i32 type;//类型：收保证金=1，退保证金=2
	9: i32 orgId;//机构ID
	10: string remark;//备注
	11: string createrDate;//创建时间
	12: i32 createrId;//创建人ID
	13: string createrAuthor;//
	14: i32 updateId;//更新人ID
	15: string updateDate;//更新时间
	16: string updateAuthor;//
	17: i32 page=1; //第几页
	18: i32 rows=10; //总页数
	19: string orgName;//机构名称
}
/*财务相关Service*/
service OrgFinanceService{
	//根据机构id查询机构订单数据汇总（用于机构首页的数据展现）
	OrgOrderSummary getOrgOrderSummaryByOrgId(1:i32 orgId);
	//根据合伙人的用户id查询合伙人订单数据汇总（用于合伙人首页的数据展现）
	PartnerOrderSummary getPartnerOrderSummary(1:i32 userId);
	//机构放款列表(分页查询)
	list<OrgMakeLoansIndex> queryOrgMakeLoansIndex(1:OrgMakeLoansIndex query);
	i32 getOrgMakeLoansIndexTotal(1:OrgMakeLoansIndex query);
	//机构回款列表(分页查询)
	list<OrgRepaymentIndex> queryOrgRepaymentIndex(1:OrgRepaymentIndex query);
	i32 getOrgRepaymentIndexTotal(1:OrgRepaymentIndex query);
}
/*保证金记录Service*/
service OrgAssureFundRecordService{
    //保证金记录列表(分页查询)
	list<OrgAssureFundRecord> queryOrgAssureFundRecordPage(1:OrgAssureFundRecord query);
	i32 getOrgAssureFundRecordTotal(1:OrgAssureFundRecord query);
    //根据条件查询所有保证金记录
	list<OrgAssureFundRecord> getAll(1:OrgAssureFundRecord orgAssureFundRecord);
	//查询保证金记录
	OrgAssureFundRecord getById(1:i32 pid);
	//新增保证金记录
	i32 insert(1:OrgAssureFundRecord orgAssureFundRecord);
	//修改保证金记录
	i32 update(1:OrgAssureFundRecord orgAssureFundRecord);
	//删除保证金记录
	i32 deleteById(1:i32 pid);
	//批量删除保证金记录
	i32 deleteByIds(1:list<i32> pids);
}