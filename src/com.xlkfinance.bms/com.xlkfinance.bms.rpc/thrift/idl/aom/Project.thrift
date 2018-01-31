namespace java com.qfang.xk.aom.rpc.project
include "../Common.thrift"
include "../System.thrift"
include "../Customer.thrift"
include "../Beforeloan.thrift"
include "Page.thrift"
include "System.thrift"
/*
 *业务申请表
 *2016-07-19 11:04:49
 **/
struct BizProject{
	1: i32 pid;//
	2: i32 acctId;//
	3: i32 projectType;//项目类型（授信=1、贷款=2、额度提取=3、贷款展期=4  授信and贷款=5，6机构提交业务）
	4: string projectName;//项目名称
	5: string projectNumber;//项目编号
	6: i32 pmUserId;//提交人（ERP后台的操作人员）
	7: string businessCatelog;//
	8: string businessType;//
	9: string flowCatelog;//
	10: string myType;//
	11: string myMain;//
	12: i32 loanInterestRecord;//
	13: i32 loanMgrRecord;//
	14: i32 isAllowPrepay;//
	15: i32 isReturnInterest;//
	16: i32 loanOtherFee;//
	17: i32 requestStatus;//申请状态（1、未提交2、待审核3、审核中4、审核通过5、审核失败）
	18: string requestDttm;//申请时间
	19: string completeDttm;//
	20: i32 status;//
	21: i32 circulateType;//
	22: string surveyResult;//
	23: string comments;//
	24: i32 isRejected;//
	25: i32 productId;//
	26: i32 businessSource;//
	27: string address;//
	28: string businessContacts;//
	29: string contactsPhone;//
	30: i32 innerOrOut;//
	31: i32 businessCategory;//业务类型（1：交易，2：非交易，默认为0）
	32: i32 isNotarization;//
	33: i32 isDelete;//
	34: i32 isChechan;//
	35: string managers;//
	36: string managersPhone;//
	37: i32 foreclosureStatus;//赎楼申请状态1、待客户经理提交2、待部门经理审批3、待业务总监审批4、待审查员审批5、待风控初审6、待风控复审7、待风控终审8、待风控总监审批9、待总经理审批10、已审批11、已放款12、业务办理已完成13、已归档
	38: i32 businessSourceNo;//
	39: i32 collectFileStatus;//
	40: i32 projectSource;//
	41: string auditorOpinion;//
	42: i32 isSeller;//
	43: string declaration;//
	44: i32 refundFileStatus;//
	45: string chechanDate;//
	46: i32 chechanUserId;//
	47: string chechanCause;//
	48: i32 orgId;//机构ID
	49: string orgCustomerName;//客户姓名
	50: string orgCustomerPhone;//客户电话
	51: string orgCustomerCard;//客户身份证
	52: string planLoanDate;//希望放款日期
	53: double planLoanMoney;//借款金额
	54: double loanRate;//借款利率
	55: double maxLoanRate;//最高承受利率
	56: i32 isClosed;//是否关闭1、关闭2、正常
	57:Beforeloan.ProjectGuarantee projectGuarantee;//项目费用信息
	58:Beforeloan.ProjectProperty projectProperty;//项目物业信息 
	59:Beforeloan.ProjectForeclosure projectForeclosure;//赎楼信息
	60:string orgName;//机构名称
	61:string areaCode;//所属城市编码
	62:i32 applyUserId;//提交申请人ID（合作机构的员工ID）
	63:i32 isNeedHandle;//是否需要办理贷中1、办理2、不办理
	64:i32 isReject;//是否驳回1、完全驳回2、正常3、补充资料4、拒单
	65:i32 examineUser;//驳回操作人
	66:string examineDate;//驳回时间
	67:string examineOpinion;//驳回意见
	68:i32 isAssigned;//是否已分配1、未分配2、已分配
	69:string pids;//项目ID集合 
	70:list<string> projectIds;
	71:list<Beforeloan.BizProjectEstate> estateList;//项目物业信息
	72:string houseIds;//物业ID
	73:i32 foreAfterMonitorStatus;//赎楼贷后监控状态：无异常=1，有异常=2
	74:i32 rejectType;//拒单类型1、黑名单2、其他原因
	75:string originalLoanIds;
	76:list<Beforeloan.BizOriginalLoan> originalLoanList;//原贷款列表信息
	77:string userPids;//共同借款人IDs
}
/*业务申请列表展示*/
struct BizProjectDTO{
	1:i32 pid;
	2:string projectNumber;
	3:string projectName;
	4:string orgName;//机构名称
	5:i32 pmUserId;
	6:string pmUserName;//分配人
	7:string orgCustomerName;//客户姓名
	8:string orgCustomerPhone;//客户电话
	9:string orgCustomerCard;//客户身份证
	10:i32 orgId;//机构ID
	11:i32 requestStatus;//申请状态（1、未提交2、待审核3、审核中4、审核通过5、审核失败）
	12:i32 applyStatus;//业务申请状态:10、待客户经理提交20、待风控初审30、待风控复审40、待风控终审50、待总经理审批60、已审批70、已放款80、已回款90、已结算100、已归档
	13:string requestDttm;//申请时间
	14:double planLoanMoney;//借款金额
	15:i32 isClosed;//是否关闭1、关闭2、正常
	16:string planLoanDate;//希望放款日期
	17:i32 page;
	18:i32 rows;
	19:i32 projectType;//业务类型（授信=1、贷款=2、额度提取=3、贷款展期=4  授信and贷款=5，6机构提交业务）
	20: double loanRate;//借款利率
	21: double maxLoanRate;//最高承受利率
	22:i32 businessCategory;//业务类型（1：交易，2：非交易，默认为0）
	23:string areaCode;//所属城市编码
	24:list<string> areaCodes;//城市编码集合
	25:list<i32> requestStatuList;//申请状态集合
	26:i32 applyUserId;//提交申请人ID（合作机构的员工ID）
	27:i32 isNeedHandle;//是否需要办理贷中1、办理2、不办理
	28:i32 foreclosureStatus;
	29:i32 isReject;//是否驳回1、驳回2、正常
	30: i32 isAssigned;//是否已分配1、未分配2、已分配
	31:list<i32> userIds;//数据权限
	32: string applyUserName;//提交人
	33:string endRequestDttm;//仅用于查询
	34:i32 loanDays;//借款天数
	35:string productName;//产品名称
	36:i32 productId;//产品ID
}

/*
 *业务申请驳回记录
 *2016-09-01 10:38:34
 **/
struct OrderRejectInfo{
	1: i32 pid;//
	2: i32 orderId;//
	3: i32 examineUser;//操作人
	4: string examineDate;//操作时间
	5: string examineOpinion;//驳回意见
}
/*业务申请驳回记录Service*/
service OrderRejectInfoService{
    //根据条件查询所有业务申请驳回记录
	list<OrderRejectInfo> getAll(1:OrderRejectInfo orderRejectInfo);
	//查询业务申请驳回记录
	OrderRejectInfo getById(1:i32 pid);
	//新增业务申请驳回记录
	i32 insert(1:OrderRejectInfo orderRejectInfo);
	//修改业务申请驳回记录
	i32 update(1:OrderRejectInfo orderRejectInfo);
	
}

/*业务申请表Service*/
service BizProjectService{
    //根据条件查询所有业务申请表
	list<BizProject> getAll(1:BizProject bizProject);
	//查询业务申请表
	BizProject getById(1:i32 pid);
	//新增业务申请表
	i32 insert(1:BizProject bizProject);
	//修改业务申请表
	i32 update(1:BizProject bizProject);
	//根据条件分页查询所有业务申请表
	list<BizProjectDTO> getBizProjectByPage(1:BizProjectDTO bizProjectDTO);
	//根据条件查询所有业务申请表总数
	i32 getBizProjectCount(1:BizProjectDTO bizProjectDTO);
	//添加业务申请数据
	string addBizProject(1:BizProject bizProject)throws (1:Common.ThriftServiceException e);
	//根据项目ID查询项目详情，包括关联信息
	BizProject getProjectByPid(1:i32 pid);
	//修改业务申请状态
	i32 updateProjectStatus(1:BizProject bizProject);
	//修改业务申请数据，包括附加表数据
	string updateBizProject(1:BizProject bizProject)throws (1:Common.ThriftServiceException e);
	//关闭订单（删除订单）
	i32 updateProjectClosed(1:BizProject bizProject);
	
	list<Beforeloan.DataInfo> getProjectDataInfos(1:i32 projectId);
	
	i32 saveDataFile(1:Beforeloan.DataInfo dataInfo)throws (1:Common.ThriftServiceException e);
	//修改可用授信额度
	i32 updateAvailableLimit(1:BizProject bizProject)throws (1:Common.ThriftServiceException e);
	i32 delDataFile(1:i32 dataId);
	//修改驳回状态
	i32 updateProjectReject(1:BizProject bizProject);
	//分配订单
	i32 updateProjectAssigned(1:BizProject bizProject);
	
	//根据条件分页查询所有机构业务列表
	list<BizProjectDTO> getProjectListByPage(1:BizProjectDTO bizProjectDTO);
	//根据条件查询所有机构业务列表
	i32 getProjectListCount(1:BizProjectDTO bizProjectDTO);
	
}