namespace java com.xlkfinance.bms.rpc.foreafterloan
include "System.thrift"
include "Common.thrift"
include "File.thrift"


/*在途业务监控分页列表*/
struct TransitMonitorIndex {
	1: string projectName;	//项目名称
	2: string receDate;	//放款日期
	3: i32 loanDays;	//借款天数
	4: double loanMoney;	//借款金额
	5: string businessSourceStr;	//业务来源
	6: string buyerName;	//买方
	7: string sellerName;	//卖方
	8: string houseName;	//物业名称
	9: string housePropertyCard;	//房产证号
	10: string productName;	//产品名称
	11: string planRepaymentDate;	//预计回款日期
	12: i32 repaymentDateDiff;	//距离回款天数
	13: i32 page=1; //第几页
	14: i32 rows=10; //总页数
	15: list<i32> userIds;//
	16: string repaymentDate;	//回款日期
	17: string repaymentEndDate;	//距离回款天数
	18: string planRepaymentEndDate;	//
	19: i32 foreAfterMonitorStatus;	//赎楼贷后监控状态：无异常=1，有异常=2,异常转正常=3
	20: i32 repaymentStatus;	//回款状态：未回款=1,已回款=2
	21: i32 projectId;	//项目id
	22: string makeLoansRes;	//
	23: string foreAfterMonitorStatusName;//异常名称
}
/*异常业务列表*/
struct TransitExceptionIndex {
	1: string projectName;	//项目名称
	2: string receDate;	//放款日期
	3: i32 loanDays;	//借款天数
	4: double loanMoney;	//借款金额
	5: string businessSourceStr;	//业务来源
	6: string buyerName;	//买方
	7: string sellerName;	//卖方
	8: string houseName;	//物业名称
	9: string housePropertyCard;	//房产证号
	10: string productName;	//产品名称(业务类型)
	11: string planRepaymentDate;	//预计回款日期
	12: i32 repaymentDateDiff;	//距离回款天数
	13: i32 page=1; //第几页
	14: i32 rows=10; //总页数
	15: list<i32> userIds;//
	16: string repaymentDate;	//回款日期
	17: string repaymentEndDate;	//距离回款天数
	18: string planRepaymentEndDate;	//
	19: i32 monitorStatus;	//异常监控状态：正常=1,异常=2,异常转正常=3
	20: i32 repaymentStatus;	//回款状态：未回款=1,已回款=2
	21: i32 projectId;	//项目id
	22: string orgName;	//资方来源
	23: i32 applyStatus;//新增or修改报告
	24: i32 dangerLevel;//风险等级
	25: string remark;	//监控说明
	26: string makeLoansRes;	//资方来源
	27: i32 businessSource;//业务来源
	28: string cpyName;//关联公司
	29: string partnerLoanDate;//上资方时间
	30: string updateDate;//我司出款情况
	31: string payDate;//赎楼日期
	32: i32 totalCount;//在保笔数
	33: double totalMoney;//在保金额
	34: string certNumber;//身份证号
	35: string chinaName;//借款人姓名
	36: double availableLimit; //存入保证金
	37: double singleUpperLimit; //单笔上线
	38: double assureMoney;//授信额度 
	39: string monitorStatusName;//监控状态（为导出设计）
	40: string dangerLevelName;//风险等级名称（为导出设计）
	41: string returnNormalRemark;//回归正常说明
	
}

/*异常业务监控*/
struct ExceptionMonitorIndex {
	1:i32 pid  ;//主键
	2:i32 exceptionId  ;//管理异常表Id
	3:i32 projectId  ;//项目Id
	4:string monitorDate; //监控日期
	5:string monitorTitle ;//监控标题
	6:string monitorContent;//监控内容
	7:string monitorResult ;//监控结果
	8:string monitorOpinion ;//监控意见
	9:i32 dangerLevel  ;//风险等级
	10:i32 followStatus  ;//根据状态
	11:string nextMonitorDate;//下次跟进时间
	12:i32 followId  ;//跟进人Id
	13:i32 nextFollowId  ;//下次跟进人Id
	14:string noticeWay  ;//通知方式
	15:i32 status  ;//状态
	16: i32 page=1; //第几页
	17: i32 rows=10; //总页数
	18: list<i32> userIds;//
	19: string createDate;	//创建时间
	20: string createId;	//创建人Id
	21: string updateDate;	//更新日期
	22: string updateId;	//更新人Id
	23: string remark;	//备注
	24: string followName;	//跟进人名字
}



/*申请报告列表*/
struct TransitApplyReportIndex {
	1: i32 pid;//项目Id
	2: i32 exceptionId;//异常表Id
	3: i32 userId;//报告人Id
	4: string reportDate;//报告时间
	5: string unAssureCondition;//反担保情况
	6: string houseProperyCondition;//房产情况
	7: string remark;	//备注
	8: string createDate;	//创建时间
	9: string createId;	//创建人Id
	10: string updateDate;	//更新日期
	11: string updateId;	//更新人Id
	12: i32 projectId;	//项目Id
	13: list<LegalIndex> legalList;
}

/*诉讼表*/
struct LegalIndex {
	1: i32 pid;//主键
	2: i32 reportId;//关联异常表异常表Id
	3: i32 status;//个人诉讼=1，企业诉讼=2
	4: string legalContent;//诉讼内容
	5: string createDate;	//创建时间
	6: string createId;	//创建人Id
	7: string updateDate;	//更新日期
	8: string updateId;	//更新人Id
}


/*赎楼贷后模块Service*/
service ForeAfterLoanService{
	//在途业务监控列表(分页查询)
	list<TransitMonitorIndex> queryTransitMonitorIndex(1:TransitMonitorIndex query);
	//在途业务监控列表总数
	i32 getTransitMonitorIndexTotal(1:TransitMonitorIndex query);
	
	//异常业务监控列表(分页查询)
	list<TransitExceptionIndex> queryTransitExceptionIndex(1:TransitExceptionIndex query);
	//异常业务监控总数
	i32 getTransitExceptionIndexTotal(1:TransitExceptionIndex query);
	//根据Id修改异常表的申请状态
	i32 updateExceptionIndex(1:string id);
	
	//根据项目Id查询异常列表
	TransitExceptionIndex getTransitExceptionIndexById(1:i32 projectId);
	
	// 增加和更新申请报告
	i32 addTransitApplyReportIndex(1:TransitApplyReportIndex query);
	i32 updateTransitApplyReportIndex(1:TransitApplyReportIndex query);
	//根据Id查询申请报告表
	TransitApplyReportIndex getById(1:i32 projectId);
	
	// 增加和更新诉讼列表
	i32 addLegalIndex(1:LegalIndex query);
	i32 updateLegalIndex(1:string id);
	//根据异常ID查诉讼表
	list<LegalIndex> getLegalListByReport(1:i32 reportId);
	// 根据pid删除诉讼列表
	i32 deleteLegalIndexByIds(1:list<i32> pids);
	// 修改诉讼表内容及状态
	i32 updateLegal(1:LegalIndex query);
	
	// 增加异常监控
	i32 addExceptionMonitorIndex(1:ExceptionMonitorIndex query);
	//查询异常业务监控
	list<ExceptionMonitorIndex> queryExceptionMonitorIndex(1:ExceptionMonitorIndex query);
	//查询异常业务监控总数
	i32 getExceptionMonitorIndexTotal(1:ExceptionMonitorIndex query);	
	//根据项目ID查询异常业务监控
	list<ExceptionMonitorIndex> getExceptionMonitorIndexById(1:i32 projectId);	
}
/*
 *贷后异常上报表
 *2017-01-16 10:43:25
 **/
struct AfterExceptionReport{
	1: i32 pid;//
	2: i32 projectId;//项目ID
	3: i32 monitorUserId;//监控人Id
	4: i32 exceptionType;//异常类型：1=展期，2=逾期，3=房产查封，4=新增诉讼，100=其他
	5: i32 status;//状态
	6: string monitorDate;//监控时间
	7: string noticeWay;//通知方式（多个以英文逗号隔开）：1=短信，2=邮件
	8: string remark;//备注
	9: i32 attachmentId;//附件id
	10: string createrDate;//创建时间
	11: i32 createrId;//创建人ID
	12: i32 updateId;//更新人ID
	13: string updateDate;//更新时间
	14: string createrName;//创建人姓名
    15: i32 page=1; //第几页
	16: i32 rows=10; //总页数
}
/*贷后异常上报表Service*/
service AfterExceptionReportService{
    //根据条件查询所有贷后异常上报表
	list<AfterExceptionReport> getAll(1:AfterExceptionReport afterExceptionReport);
	//查询贷后异常上报表
	AfterExceptionReport getById(1:i32 pid);
	//新增贷后异常上报表
	i32 insert(1:AfterExceptionReport afterExceptionReport);
	//修改贷后异常上报表
	i32 update(1:AfterExceptionReport afterExceptionReport);
	//删除贷后异常上报表
	i32 deleteById(1:i32 pid);
	//批量删除贷后异常上报表
	i32 deleteByIds(1:list<i32> pids);
    //贷后异常上报列表(分页查询)
	list<AfterExceptionReport> queryAfterExceptionReportHisList (1:AfterExceptionReport query) ;
	//贷后异常上报列表总数
	i32 getAfterExceptionReportHisTotal (1:AfterExceptionReport query) ;
	//根据项目Id查询异常上报列表
	list<AfterExceptionReport> getByProjectId(1:i32 projectId);
	
}
