namespace java com.qfang.xk.aom.rpc.fee
include "../Common.thrift"
include "../System.thrift"
include "Page.thrift"
include "System.thrift"

/*
 *费用结算表
 *2016-08-12 15:16:55
 **/
struct OrgFeeSettle{
	1: i32 pid;//
	2: i32 orgId;//机构ID
	3: i32 partnerId;//合伙人ID
	4: double applyMoneyTotal;//借款金额
	5: double loanMoneyTotal;//放款金额
	6: double paymentMoneyTotal;//回款金额
	7: string settleDate;//结算日期
	8: double rebateRate;//返点率
	9: double returnCommRate;//返佣率
	10: double refundMoneyTotal;//退费金额
	11: double returnCommTotal;//返佣金额
	12: double chargeMoneyTotal;//收费金额
	13: double rebateMoneyTotal;//返点金额
	14: i32 rebateType;//返点状态
	15:double extimateFeeRate;//预计收费费率
	16:double actualFeeRate;//实际收费费率
	17:double fundSizeMoney;//机构出款规模
}
/*
 *费用结算查询条件以及分页查询结果
 *2016-08-15 15:16:55
 **/
struct OrgFeeSettleDTO{
	1: i32 pid;//
	2: i32 orgId;//机构ID
	3: i32 partnerId;//合伙人ID
	4: double applyMoneyTotal;//借款金额
	5: double loanMoneyTotal;//放款总金额
	6: double paymentMoneyTotal;//回款金额
	7: string settleDate;//结算日期
	8: double rebateRate;//返点率
	9: double returnCommRate;//返佣率
	10: double refundMoneyTotal;//退费金额
	11: double returnCommTotal;//返佣金额
	12: double chargeMoneyTotal;//收费金额
	13: double rebateMoneyTotal;//返点金额
	14: i32 rebateType;//返点状态
	15: string loanDateStart;//放款时间开始，用于查询
	16: string loanDateEnd; //放款时间结束，用于查询
	17: i32 page;
	18: i32 rows;
	19: string orgName;
	20: string partnerName;
	21: string orgCode;
	22: string settleDateEnd;//结算日期
	23:double extimateFeeRate;//预计收费费率
	24:double actualFeeRate;//实际收费费率
	25:double fundSizeMoney;//机构出款规模
}

/*
 *费用结算明细表
 *2016-08-12 15:26:00
 **/
struct OrgFeeSettleDetail{
	1: i32 pid;//
	2: i32 settleId;//费用编号
	3: i32 projectId;//项目编号
	4: double loanMoney;//放款金额
	5: double paymentMoney;//回款金额
	6: double charge;//收费
	7: double refund;//退费
	8: double rebateMoney;//返点金额
	9: double returnComm;//返佣金额
	10: string paymentDate;//回款日期
	11: string loanDate;//放款日期
	12: double returnCommRate;//返佣率
	13: double rebateRate;//返点率
	14: string solutionDate;//解保日期
	15: i32 page;
	16: i32 rows;
	17: string requestDate;//申请日期
	18: string projectName;//项目名称
	19: string projectNumber;//项目编号
	20: string customerName;//客户名称
	21: string customerCard;//客户身份证号
	22: double applyMoney;//申请金额
	23: string pmUserName;//经办人
	24: i32 planLoanDays;//借款天数
	25: double extimateFeeRate;//预计收费费率
	26: string productName;//产品名称
	27: string houseName;//物业名称
	28: i32 businessProcess;//业务流程状态
	29: string businessProcessName;//业务流程状态名称
	30: i32 actualLoanDays;//实际借款天数
	31: double actualFeeRate;//实际收费费率
	32: string orgName;//机构名称
	33: double realChargeMoney;//应收费金额
	34: double overDueFee;//逾期费
}
/*
 *机构息费列表
 *2016-08-12 15:16:55
 **/
struct OrgFeeSettlePage{
	1: i32 pid;//
	2: i32 orgId;//机构ID
	3: i32 partnerId;//合伙人ID
	4: double applyMoneyTotal;//借款金额
	5: double loanMoneyTotal;//放款金额
	6: double paymentMoneyTotal;//回款金额
	7: string settleDate;//结算日期
	8: string settleDateEnd;//结算日期
	9: double rebateRate;//返点率
	10: double refundMoneyTotal;//退费金额
	11: double chargeMoneyTotal;//收费金额
	12: double rebateMoneyTotal;//返点金额
	13: i32 rebateType;//返点状态1、有返点2、无返点
	14: i32 page=1; //第几页
	15: i32 rows=10; //总页数
}
/*
 *机构端费用结算明细
 *2016-08-12 15:26:00
 **/
struct OrgFeeSettleDetailPage{
	1: i32 pid;//
	2: i32 settleId;//费用编号
	3: i32 projectId;//项目编号
	4: string projectName;//项目名称
	5: string projectNumber;//项目编号
	6: string requestDate;//申请日期
	8: double planLoanMoney;//申请借款金额
	9: double loanMoney;//放款金额
	10: string loanDate;//放款日期
	11: double paymentMoney;//回款金额
	12: string paymentDate;//回款日期
	13: string solutionDate;//解保日期
	14: double charge;//收费
	15: double refund;//退费
	16: double rebateMoney;//返点金额
	17: double rebateRate;//返点率
	18: i32 page;
	19: i32 rows;
	20: i32 orgId;//机构id，做数据权限查询
}
/*
 *合伙人息费结算列表
 *2016-08-01 9:45:00
 **/
struct PartnerFeeSettlePage{
    1: i32 page;
	2: i32 rows;
	3: i32 feeSettleId;//
	4: i32 orgId;//机构ID
	5: string orgName;//机构名称
	6: string contact;//联系人
	7: string phone;//联系电话
	8: i32 partnerId;//合伙人ID
	9: string settleDate;//结算日期
	10: string settleDateEnd;//结算日期
	11: double returnCommTotal;//返佣金额
	12: double returnCommRate;//返佣率
	13: double applyMoneyTotal;//借款金额
	14: double loanMoneyTotal;//放款金额
	15: double paymentMoneyTotal;//回款金额
}
/*
 *合伙人息费结算明细列表
 *2016-08-01 11:28:00
 **/
struct PartnerFeeSettleDetailPage{
    1: i32 page;
	2: i32 rows;
	3: i32 feeSettleDetailId;//合伙人息费结算明细id
	4: i32 settleId;//费用主表id
	5: i32 projectId;//项目id
	6: string projectName;//项目名称
	7: double returnComm;//返佣金额
	8: double returnCommRate;//返佣率
	9: double loanMoney;//放款金额
	10: string loanDate;//放款日期
	11: double paymentMoney;//回款金额
	12: string paymentDate;//回款日期
	13: string solutionDate;//解保日期
	14: i32 partnerId;//合伙人ID(做数据权限查询)
	
}
/*费用结算表Service*/
service OrgFeeSettleService{
    //机构息费列表(分页查询)
	list<OrgFeeSettlePage> queryOrgFeeSettlePage(1:OrgFeeSettlePage query);
	i32 getOrgFeeSettlePageTotal(1:OrgFeeSettlePage query);
    //合伙人息费列表(分页查询)
	list<PartnerFeeSettlePage> queryPartnerFeeSettlePage(1:PartnerFeeSettlePage query);
	i32 getPartnerFeeSettlePageTotal(1:PartnerFeeSettlePage query);
    //根据条件查询所有费用结算表
	list<OrgFeeSettle> getAll(1:OrgFeeSettle orgFeeSettle);
	//查询费用结算表
	OrgFeeSettle getById(1:i32 pid);
	//新增费用结算表
	i32 insert(1:OrgFeeSettle orgFeeSettle);
	//修改费用结算表
	i32 update(1:OrgFeeSettle orgFeeSettle);
	//生成费用结算数据
	i32 initFeeSettle();
	
	//机构息费列表(分页查询)
	list<OrgFeeSettleDTO> queryOrgFeeSettleByPage(1:OrgFeeSettleDTO query);
	i32 getOrgFeeSettleCount(1:OrgFeeSettleDTO query);
	//根据传入参数手动进行前几个月的息费计算
	i32 createLastMonthFeeSettle(1:i32 number);
}


/*费用结算明细表Service*/
service OrgFeeSettleDetailService{
    //根据条件查询所有费用结算明细表
	list<OrgFeeSettleDetail> getAll(1:OrgFeeSettleDetail orgFeeSettleDetail);
	//查询费用结算明细表
	OrgFeeSettleDetail getById(1:i32 pid);
	//新增费用结算明细表
	i32 insert(1:OrgFeeSettleDetail orgFeeSettleDetail);
	//修改费用结算明细表
	i32 update(1:OrgFeeSettleDetail orgFeeSettleDetail);
	//合伙人息费明细列表(分页查询)
	list<PartnerFeeSettleDetailPage> queryPartnerFeeSettleDetailPage(1:PartnerFeeSettleDetailPage query);
	i32 getPartnerFeeSettleDetailPageTotal(1:PartnerFeeSettleDetailPage query);
	//查询某段时间内的解保项目
	list<OrgFeeSettleDetail> getCancelProjectList(1:OrgFeeSettleDTO query);
	//查询机构放款列表
	list<OrgFeeSettleDetail> getLoanProjectList(1:i32 settleId);
	//机构端费用结算明细(分页查询)
	list<OrgFeeSettleDetailPage> queryOrgFeeSettleDetailPage(1:OrgFeeSettleDetailPage query);
	i32 getOrgFeeSettleDetailPageTotal(1:OrgFeeSettleDetailPage query);

}