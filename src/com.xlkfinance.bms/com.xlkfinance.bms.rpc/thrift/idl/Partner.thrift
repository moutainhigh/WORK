namespace java com.xlkfinance.bms.rpc.partner
include "Common.thrift"
include "System.thrift"
include "Customer.thrift"
include "Workflow.thrift"
include "Inloan.thrift"
include "Beforeloan.thrift"

/*合作机构项目*/
struct ProjectPartner{
	1:i32 pid;
	2:i32 projectId;//项目ID
	3:string loanId;//审批编号（小赢） 
	4:i32 requestStatus;//申请状态（1：未申请 2：已申请）
	5:string partnerNo;//合作机构编号
	6:string requestFiles;//申请材料
	7:string remark;//备注
	8:string requestTime;//申请时间
	9:string updateTime;//修改时间
	10:string approvalTime;//审批时间
	11:string approvalComment;//审批意见
	12:string submitApprovalTime;//提交审核时间
	13:i32 isClosed;//是否关闭（1：未关闭2：关闭）
	14:i32 loanStatus;//放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
	15:string loanTime;//放款时间
	16:string loanResultTime;//放款结果通知时间
	17:i32 repaymentRepurchaseType;//还款、回购类型（1:还款凭证2：回购凭证）
	18:i32 repaymentRepurchaseStatus;//还款、回购状态（ 0:确认收到；1：未收到; 2：确认中）
	19:string repaymentRepurchaseTime;//还款、回购时间
	20:string rpResultTime;//还款、回购结果通知时间
	21:double loanAmount;//借款金额
	22:double totalCost;//总成本
	23:double premium;	//保费
	24:double guaranteeFee;//担保费
	25:string xiFeeRequestTime;//息费结算请求时间
	26:string xiFeeResultTime;//息费借宿确认时间
	27:i32 xiFeeStatus;//息费结算状态（1:未发送，2：已发送3:未到账4：已到账）
	28:string projectNumber;//项目编号
	29:string projectName;//项目名称
	30:i32 projectStatus;//项目状态
	31:string projectStatusTime;//项目最后状态时间
	32:list<i32> fileIds;//申请材料的文件id集合
	33:i32 approvalStatus;//审核状态（1：审核中:2：通过，3：未通过）
	34:i32 pmUserId;
	35:list<i32> userIds;
	36:i32 page;
	37:i32 rows;
	38:string repaymentVoucherPath;//还款回购凭证
	39:string xiFeeVoucherPath;//息费凭证
	40:list<string> loanIds;//查询条件
	41:i32 queryXiFeeStatus ;//（查询）息费结算状态（1:未发送，2：已发送3:未到账4：已到账）
	42:string reApplyReason; //复议理由
	43:double approveMoney;		//审批金额
	44:i32 confirmLoanStatus;	//确认要款状态 （1：未发送  2：审核中 3：审核通过 4 审核不通过 ）
	45:string confirmLoanRequestTime;	//确认要款请求时间
	46:string confirmLoanResultTime  ;	//确认要款通知时间
	47:string confirmLoanReason    ;	//确认要款复议理由
	48:string loanJusticeFiles;	//公正信息文件
	49:string loanBlankFiles;	//卡片信息文件
	50:string loanOtherFiles;	//其他信息文件
	51:string applyLoanDate;	//申请放款日期
	52:string loanRemark;	//放款备注
	53:string paymentBank;				//借款银行名称
	54:string paymentBankBranch;		//借款银行支行
	55:string paymentAcctName;			//借款开户名
	56:string paymentAcctNo;			//借款开户帐号
	57:string paymentProvinceCode;		//借款银行省份编码
	58:string paymentCityCode;			//借款银行城市编码
	59:string provinceCode;		//省份编号
	60:string cityCode;			//城市编号
	61:string loanEffeDate;		//审批放款有效期  
	62:string partnerLoanDate;	//机构放款日期
	63:string partnerLoanFile;	//机构放款凭证
	64:string partnerRealRefundDate;		//机构实际还款到帐日  
	65:string partnerRefundFile;			//机构到帐凭证 
	66:double partnerInterests;			//应补利息金额   
	67:string repaymentRepurchaseRemark;	//还款、回购备注 
	68:double refundLoanAmount;			//还款本金    
	69:double refundXifee;				//还款息费  
	70:string refundDate;				//还款日期  
	71:i32 businessCategory;				//业务类型id
	72:string businessCategoryStr;			//业务类型  
	73:string payAcctNo;			//还款帐号（公司）
	74:string payAcctName;			//还款账号户名（公司）
	75:string payBankName;			//还款银行名称（公司）
	76:string payBankCode;			//还款银行CODE（公司）
	77:string payBankBranch;		//还款银行支行（公司）
	78:string payProvinceCode;			//还款银行省份编码（公司）
	79:string payCityCode;				//还款银行城市编码（公司）
	80:string beginTime;				//开始时间
	81:string endTime;					//结束
	82:i32 orderByType;					//排序类型
	83:double confirmLoanMoney;			//确认放款金额     
	84:i32 confirmLoanDays;				//确认借款天数 
	85:i32 applyDate;					//借款期限
	86:string houseProvinceCode;		//物业省份编号
	87:string houseCityCode;			//物业城市编号 
	88:i32 projectSource;				//项目来源1、万通2、小科3、其他机构 
	
	89:string partnerOrderCode;			//资金项目编号
	90:i32 loanPeriodLimit;			//借款期限
	91:i32 isCreditLoan;				//客户在他行有无未结清的信用贷款（1：有 2：无）
	92:string paymentBankPhone;				//借款人银行预留手机号码
	93:string paymentBankLineNo;		//借款人银行联行行号
	94:list<i32> requestStatusList;		//申请状态列表
	
	95:double refundTotalAmount;			//还款总金额
	96:double refundPenalty;			//还款违约金
	97:double refundFine;			//还款罚息
	98:double refundCompdinte;			//还款复利
	99:string partnerPlatformOrderCode;		//平台申请到资方业务编号
	100:string planRefundDate;		//预计到期日期
	101:string fromPlanRefundDate;		//开始预计到期日期
	102:string toPlanRefundDate;		//结束预计到期日期
	103:i32 partnerPushAccount;			//机构推送帐号  1：华安万通  2：华安小科
	104:double partnerGrossRate;		//执行利率
	
	
}
/*机构合作项目详情*/
struct ProjectPartnerDto{
	1:i32 pid;
	2:i32 projectId;//项目ID
	3:string loanId;//审批编号（小赢）
	4:string projectNumber;//项目编号
	5:string projectName;//项目名称
	6:list<i32> fileIds;//申请材料的文件id集合
	7:string requestFiles;//申请材料
	8:string remark;//备注
	9:i32 businessType;//业务类型
	10:string city;//城市
	11:string userName;//借款人
	12:string cardNo;//身份证号
	13:string phone;//手机号
	14:double applyMoney;//借款金额
	15:i32 applyDate;//借款期限
	16:string loanDate;//请求放款时间
	17:string pmUserName;//经办人
	18:i32 pmUserId;//经办人ID
	20:string businessTypeStr;//业务类型
	21:string partnerNo;
	22:string approvalComment;	//审核内容
	23:string reApplyReason; //复议理由
	24:string loanJusticeFiles;	//公正信息文件
	25:string loanBlankFiles;	//卡片信息文件
	26:string loanOtherFiles;	//其他信息文件
	27:string confirmLoanReason;	//复议确认要款理由
	28:i32 repaymentRepurchaseType;			//还款回购类型
	29:string repaymentVoucherPath;			//还款回购文件
	30:string repaymentRepurchaseStatus;	//还款回购状态
	31:string applyLoanDate;	//申请放款日期
	32:i32 loanStatus;			//放款状态
	33:string loanRemark;		//放款备注
	
	34:string certType;					//证件类型
	35:string paymentBank;				//借款银行名称
	36:string paymentBankBranch;		//借款银行支行
	37:string paymentAcctName;			//借款开户名
	38:string paymentAcctNo;			//借款开户帐号
	39:string paymentProvinceCode;		//借款银行省份编码
	40:string paymentCityCode;			//借款银行城市编码
	
	41:string paymentAccount;			//回款账号
	42:string paymentName;				//回款户名
	43:string pmCustomerName;			//客户经理
	44:i32 sex;						//性别
	45:string liveAddr;				//居住地址
	46:string businessContacts;		//业务联系人
	47:string managers;				//项目经办人
	
	48:string oldBankName;			//原贷款银行
	49:double oldLoanMoney;			//原贷款金额
	50:string oldLoanPerson;		//原银行联系人
	51:string oldLoanPhone;			//原贷款联系电话
	52:string newBankName;			//新贷款银行
	53:double newLoanMoney;			//新贷款金额
	54:string newLoanPerson;		//新银行联系人
	55:string newLoanPhone;			//新贷款联系电话
	
	56:i32 paymentType;				//贷款方式
	57:string accumulationFundBank;	//公积金银行
	58:double accumulationFundMoney;	//公积金贷款金额
	59:string superviseDepartment;		//监管单位
	60:double fundsMoney;				//资金监管金额
	61:string notarizationDate;		//委托公证日期

	62:string houseName;				//物业名称
	63:double area;					//面积(平方)
	64:double costMoney;				//原价
	65:double tranasctionMoney;		//成交价
	66:string housePropertyCard;		//房产证号
	67:string buyerName;				//买房人名称
	68:string buyerCardNo;			//买房人身份证号码
	69:string buyerAddress;			//买房人家庭地址
	
	70:string provinceCode;	//省份编号
	71:string cityCode;	//城市编号
	72:list<Customer.CusDTO> publicManList;	//共同借款人
	73:list<Workflow.TaskHistoryDto> taskHistoryList;
	
	74:string loanEffeDate;		//审批放款有效期  
	75:string partnerLoanDate;	//机构放款日期
	76:string partnerLoanFile;	//机构放款凭证
	77:string partnerRealRefundDate;		//机构实际还款到帐日  
	78:string partnerRefundFile;			//机构到帐凭证 
	79:double partnerInterests;				//应补利息金额   
	80:string repaymentRepurchaseRemark;	//还款、回购备注 
	81:double refundLoanAmount;			//还款本金   
	82:double refundXifee;				//还款息费  
	83:string xiFeeVoucherPath;//息费凭证
	84:string refundDate;				//还款日期
	85:i32 businessCategory;				//业务类型id
	86:string businessCategoryStr;			//业务类型  
	87:string payAcctNo;			//还款帐号（公司）
	88:string payAcctName;			//还款账号户名（公司）
	89:string payBankName;			//还款银行名称（公司）
	90:string payBankCode;			//还款银行CODE（公司）
	91:string payBankBranch;		//还款银行支行（公司）
	92:string payProvinceCode;			//还款银行省份编码（公司）
	93:string payCityCode;				//还款银行城市编码（公司）
	94:i32 acctId;						//客户账户id
	95:double confirmLoanMoney;			//确认放款金额     
	96:i32 confirmLoanDays;				//确认借款天数 
	97:string houseProvinceCode;		//物业省份编号
	98:string houseCityCode;			//物业城市编号 
	99:double approveMoney;				//审批金额
	100:i32 projectSource;				//项目来源1、万通2、小科3、其他机构 
	101: list<Beforeloan.BizProjectEstate> estateList;	//项目物业信息列表
	102: Beforeloan.ProjectProperty projectProperty;	//项目物业信息主表
	
	103:string partnerOrderCode;			//资金项目编号
	104:i32 loanPeriodLimit;			//借款期限
	105:i32 isCreditLoan;				//客户在他行有无未结清的信用贷款（1：有 2：无）
	106:string paymentBankPhone;				//借款人银行预留手机号码
	107:string paymentBankLineNo;		//借款人银行联行行号
	
	108:double refundTotalAmount;		//还款总金额
	109:double refundPenalty;			//还款违约金
	110:double refundFine;				//还款罚息
	111:double refundCompdinte;			//还款复利
	112:string partnerPlatformOrderCode;		//平台申请到资方业务编号
	113:Beforeloan.ProjectForeclosure projectForeclosure;//赎楼信息
	114:i32 isPartnerOpenAccount;		//是否开户绑卡
	115:i32 partnerPushAccount;			//机构推送帐号  1：华安万通  2：华安小科
	116:double partnerGrossRate;		//执行利率
}

/*合作机构审批记录*/
struct PartnerApprovalRecord{
	1:i32 pid;
	2:i32 partnerId;//合作机构项目ID
	3:string loanId;//审批编号
	4:i32 approvalStatus;//审核状态（1：审核中:2：通过，3：未通过）
	5:double approveMoney;		//审批金额
	6:string approvalComment;	//审核意见
	7:string approvalTime;		//审核时间
	8:string submitApprovalTime;//提交审核时间
	9:string projectNumber;		//项目编号
	10:string projectName;		//项目名称
	11:string reApplyReason; 	//复议理由
	12:i32 isNotify;			//是否回调通知  1：否，2：是
	13:i32 notifyType;			//回调类型 (1: 申请 2: 复议 3: 确认要款 4: 复议确认要款) 
	14:i32 queryIsNotify;		//查询条件
}


/*项目合作机构附件表*/
struct ProjectPartnerFile{
	1:i32 pid;
	2:i32 projectId;		//项目ID
	3:string partnerNo;		//合作机构编号
	4:string accessoryType;	//附件类型
	5:string fileName;		//文件名
	6:string fileType;		//文件类型
	7:i32 fileSize;			//文件大小
	8:string fileUrl;		//文件路径
	9:string updateTime;	//更新时间
	10:i32 status;			//状态
	11:string remark; 		//备注
	12:list<string> accessoryTypes;// 文件类型list
	13:list<i32> pidList;	// id列表
	14:string accessoryChildType; 	//附件子类型
	15:string thirdFileUrl; 	//第三方平台文件URL
	16:i32 partnerId;			//合作机构项目ID
	
}


/*合作机构还款表*/
struct ProjectPartnerRefund{
	1:i32 pid;						//主键id
	2:i32 partnerId;				//机构项目id
	3:i32 projectId;				//项目ID
	4:string partnerNo;				//合作机构编号
	5:string loanId;					//第三方贷款ID
	6:i32 currNo;						//当前还款期数
	7:string currPlanRefundDate;		//当期预计还款日期
	8:double currShouldCapitalMoney;	//当期应还本金金额
	9:double currShouldXiFee;			//当期应还利息 
	10:double currShouldManageFee;		//当期应还管理费
	11:double currShouldOtherFee;		//当期应还其他费用
	12:double currShouldTotalMoney;		//当期还款总金额
	13:string currRealRefundDate;		//实际还款日期
	14:double currRealCapitalMoney; 	//当期实还本金
	15:double currRealXiFee;			//当期实还利息
	16:double currRealManageFee;		//当期实还管理费
	17:double currRealOtherFee;			//当期实还其他费用
	18:double currRealTotalMoney;		//当期实还总金额
	19:i32 currOverdueStatus;			//当前逾期状态(1:未逾期,2:逾期未结清,3:逾期已结清) 
	20:i32 currOverdueDays;				//当前逾期天数
	21:double oweCapitalMoney;			//剩余本金 
	22:double currCapitalMoneyFile;		//还款本金凭证   
	23:double currXiFeeFile;			//还款利息凭证  
	24:i32 isSettlementStatus;			//是否提前结清(1：是 2: 否)
	25:i32 isForbit;					//是否作废(1：是 2: 否)     
	26:i32 refundStatus;				//还款状态(1：未申请，2：申请中，3：还款成功，4：还款失败)
	27:string partnerRealRefundDate;	//机构实际还款到帐日
	28:string partnerRefundFile;		//机构到帐凭证
	29:string partnerInterests;			//机构应补利息金额     
	30:string refundRemark;				//还款备注
	31:string createTime;		//创建时间 
	32:string updateTime;		//更新时间
	33:list<i32> userIds;		
	34:list<i32> pids;			//主键列表
	35:list<i32> currOverdueStatusList;			//逾期状态列表
	36:list<i32> refundStatusList;				//还款状态列表
}


/*合作机构项目*/
service ProjectPartnerService{
	//查询所有已提交的审核项目
	list<ProjectPartner> findAllProjectPartner(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//查询所有已提交的审核项目总数
	i32 findAllProjectPartnerCount(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//新增审核项目
	i32 addProjectPartner(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//修改审核项目
	i32 updateProjectPartner(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//查询单个合作机构项目信息
	ProjectPartnerDto findProjectPartner(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	//查询所有已审批的贷前项目
	list<ProjectPartner> findAllProjectInfo(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//查询所有已审批的贷前项目总数
	i32 findAllProjectInfoCount(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	ProjectPartner findProjectPartnerInfo(1:ProjectPartner projectPartner);
	//查询所有审批记录（根据合作机构项目的ID以及审批编号）
	list<PartnerApprovalRecord> findAllPartnerApprovalRecord(1:i32 partnerId)throws (1:Common.ThriftServiceException e);
	//新增审批记录
	i32 addPartnerApprovalRecord(1:PartnerApprovalRecord record)throws (1:Common.ThriftServiceException e);
	//关闭单据
	i32 updatePartnerForClosed(1:string loanId)throws (1:Common.ThriftServiceException e);
	//修改放款信息
	i32 updateLoanStatus(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//修改还款回购信息
	i32 updateRepaymentInfo(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//修改息费信息
	i32 updateXiFeeInfo(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	//批量修改息费信息
	i32 updateXiFeeInfos(1:list<ProjectPartner> projectPartnerList)throws (1:Common.ThriftServiceException e);
	//修改审批记录
	i32 updatePartnerApprovalRecord(1:PartnerApprovalRecord record)throws (1:Common.ThriftServiceException e);
	
	//查询所有机构合作列表
	list<ProjectPartner> findProjectPartnerInfoList(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	
	//查询机构合作表，并懒加载项目相关数据
	ProjectPartnerDto findProjectPartnerAndLazy(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	
	//查询共同借款人
	//list<Customer.CusPerson> findPublicManByProjectId(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	//查询审批工作流记录	
	list<Workflow.TaskVo> findForeLoanWorkFlowByProjectId(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	
	//查询项目贷中办理动态状态 记录	
	list<Inloan.HandleDynamicDTO> findInLoanStatusByProjectId(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	
	//更新还款金额
	i32 updatePartnerRefundLoanMoney(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	
	
	//查询机构己申请的总额
	double findLoanAmountCountByPartnerNo(1:ProjectPartner projectPartner)throws (1:Common.ThriftServiceException e);
	
	//---------------------机构还款
	//查询所有还款记录
	list<ProjectPartnerRefund> findAllProjectPartnerRefund(1:ProjectPartnerRefund projectPartnerRefund)throws (1:Common.ThriftServiceException e);
	//根据id查询还款记录
	ProjectPartnerRefund findProjectPartnerRefund(1:ProjectPartnerRefund projectPartnerRefund)throws (1:Common.ThriftServiceException e);
	//查询所有附件总数
	i32 findAllProjectPartnerRefundCount(1:ProjectPartnerRefund projectPartnerRefund)throws (1:Common.ThriftServiceException e);
	//新增机构还款
	i32 addPartnerRefund(1:ProjectPartnerRefund projectPartnerRefund)throws (1:Common.ThriftServiceException e);
	//修改机构还款
	i32 updatePartnerRefund(1:ProjectPartnerRefund projectPartnerRefund)throws (1:Common.ThriftServiceException e);
	//新增机构还款批量
	i32 addBatchPartnerRefund(1:list<ProjectPartnerRefund> partnerRefundList)throws (1:Common.ThriftServiceException e);
	//修改机构还款批量
	i32 updateBatchPartnerRefund(1:list<ProjectPartnerRefund> partnerRefundList)throws (1:Common.ThriftServiceException e);
	//更新分期还款信息
	i32 updatePartnerRefundMoney(1:ProjectPartnerRefund projectPartnerRefund)throws (1:Common.ThriftServiceException e);
	
	//批量修改资金项目信息
	i32 updateBatchProjectParner(1:list<ProjectPartner> projectPartnerList)throws (1:Common.ThriftServiceException e);
	
}




/*合作机构项目附件*/
service ProjectPartnerFileService{
	//查询所有附件
	list<ProjectPartnerFile> findAllProjectPartnerFile(1:ProjectPartnerFile projectPartnerFile)throws (1:Common.ThriftServiceException e);
	//查询所有附件总数
	i32 findAllProjectPartnerFileCount(1:ProjectPartnerFile projectPartnerFile)throws (1:Common.ThriftServiceException e);
	//新增附件
	i32 addProjectPartnerFile(1:ProjectPartnerFile projectPartnerFile)throws (1:Common.ThriftServiceException e);
	//修改附件
	i32 updateProjectPartnerFile(1:ProjectPartnerFile projectPartnerFile)throws (1:Common.ThriftServiceException e);
	//查询附件信息表
	ProjectPartnerFile getById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	
	//清空项目机构所有附件上传第三方路径
	i32 updateAllFileUploadEmpty(1:ProjectPartnerFile projectPartnerFile)throws (1:Common.ThriftServiceException e);
}



/*
 *客户征信信息表
 *2017-02-15 18:35:29
 **/
struct CusCreditInfo{
	1: i32 pid;//主键     
	2: string partnerNo;// 资金机构    
	3: i32 acctId;//客户帐号ID  
	4: string creditDealId;//征信交易编号    
	5: string record;//征信白户识别码，人行征信为白户（未查询到客户征信记录）     
	6: string overdue;//当前逾期否决码，单笔负债（含贷记卡）当前逾期且逾期金额大于1000元   
	7: string result1;//M4否决码，近2年内单笔负债（含贷记卡）曾见4及以上    
	8: string result2;// 1年综合否决码，近1年内单笔曾见1个4、2个3、3个2或4个1及以上 
	9: string result3;//半年综合否决码，近半年内单笔曾见1个3、2个2或3个1及以上 
	10: string result4;//异常负债否决码，单笔负债曾出现“保证人代偿”、“资产处置”、“以资抵债”、“呆帐”、“冻结”、“止付”、“次级”、“可疑”、“损失”等特殊情况 
	11: string result5;// 对外担保负债否决码，单笔对外担保负债五级分类曾出现“次级”、“可疑”、“损失”等特殊情况 
	12: string result6;//失信信息否决码，列入失信被执行人名单      
	13: string result7;//查询记录否决码，近2个月内有6次查询记录且原因是“贷款审批”            
	14: string createTime;//创建时间    
	15: string updateTime;//更新时间   
	16: i32 page;
	17: i32 rows;    
	18: string chinaName;//客户名称
	19: i32 certType;//证件类型   
	20: string certNumber;//证件号码   
	21: i32 businessType;	//交易类型
	
}
/*客户征信信息表Service*/
service CusCreditInfoService{
    //根据条件查询所有客户征信信息表
	list<CusCreditInfo> getAll(1:CusCreditInfo cusCreditInfo);
	//查询客户征信信息表
	CusCreditInfo getById(1:i32 pid);
	//新增客户征信信息表
	i32 insert(1:CusCreditInfo cusCreditInfo);
	//修改客户征信信息表
	i32 update(1:CusCreditInfo cusCreditInfo);
	//删除客户征信信息表
	i32 deleteById(1:i32 pid);
	//批量删除客户征信信息表
	i32 deleteByIds(1:list<i32> pids);
	//查询客户征信列表(可分页)
	list<CusCreditInfo> selectCusCreditList(1:CusCreditInfo cusCreditInfo);
	//查询客户征信总数
	i32 selectCusCreditTotal(1:CusCreditInfo cusCreditInfo);
}


/*
 *资金机构银行信息表
 *2017-03-08 19:18:45
 **/
struct BizPartnerBankInfo{
	1: i32 pid;//主键
	2: string bankNo;//联行行号
	3: string bankName;//联行行名
	4: string remitType;//汇划种类
	5: string bankType;//行别代码
	6: string unionStatus;//联行状态
	7: string stationNo;//小站号
	8: string clearBankNo;//资金清算行代号
	9: string nodeCode;//节点代码
	10: string upBankNo;//本行上级参与者
	11: string pbcBankNo;//所属人行代码
	12: string cityCode;//所在城市代码
	13: string unionBankNameS;//联行行名简称
	14: string updateTerm;//更新期数
	15: string amt;//金额
	16: string provinceName;//所在省份名
	17: string cityName;//所在城市名
	18: string bankPhone;//联系电话
	19: string bankAddr;//联系地址
	20: string reservNum1;//保留字段1
	21: string reservStr1;//保留字段2
	22: string startDate;//起用日期
	23: string dueDate;//到期日期
	24: string useTerm;//使用期限
	25: string lastModDate;//最后变动日期
	26: string zipcode;//
	27: string email;//
	28: string remark;//
	29: i32 page;
	30: i32 rows;   
}
/*资金机构银行信息表Service*/
service BizPartnerBankInfoService{
    //根据条件查询所有资金机构银行信息表
	list<BizPartnerBankInfo> getAll(1:BizPartnerBankInfo bizPartnerBankInfo);
	//查询资金机构银行信息表
	BizPartnerBankInfo getById(1:i32 pid);
	//新增资金机构银行信息表
	i32 insert(1:BizPartnerBankInfo bizPartnerBankInfo);
	//修改资金机构银行信息表
	i32 update(1:BizPartnerBankInfo bizPartnerBankInfo);
	//删除资金机构银行信息表
	i32 deleteById(1:i32 pid);
	//批量删除资金机构银行信息表
	i32 deleteByIds(1:list<i32> pids);
	
	//查询资金机构银行列表(可分页)
	list<BizPartnerBankInfo> selectPartnerBankList(1:BizPartnerBankInfo bizPartnerBankInfo);
	//查询资金机构银行总数
	i32 selectPartnerBankTotal(1:BizPartnerBankInfo bizPartnerBankInfo);
	
}


/*
 *机构客户银行开户
 *2017-09-19 16:59:42
 **/
struct BizPartnerCustomerBank{
	1: i32 pid;					//主键
	2: string partnerNo;		//资金机构
	3: i32 acctId;				//客户帐号ID
	4: string customerIdCard;	//客户身份证
	5: string customerName;	//客户姓名
	6: string mobileNo;		//客户手机号
	7: string bankCard;		//银行卡号
	8: string bankMobileNo;	//银行预留手机号
	9: string ip;			//客户设备IP
	10: string mac;			//客户MAC地址
	11: i32 status;			//绑卡状态 1:成功   2：失败
	12: i32 operator;			//操作者
	13: string createTime;	//创建时间
	14: string updateTime;	//更新时间
	15: i32 page;
	16: i32 rows;   
	17: string beginCreateTime;		//开始创建时间
	18: string endCreateTime;		//结束创建时间
}

/*机构客户银行开户Service*/
service BizPartnerCustomerBankService{
    //根据条件查询所有机构客户银行开户
	list<BizPartnerCustomerBank> getAll(1:BizPartnerCustomerBank bizPartnerCustomerBank);
	//查询机构客户银行开户
	BizPartnerCustomerBank getById(1:i32 pid);
	//新增机构客户银行开户
	i32 insert(1:BizPartnerCustomerBank bizPartnerCustomerBank);
	//修改机构客户银行开户
	i32 update(1:BizPartnerCustomerBank bizPartnerCustomerBank);
	//删除机构客户银行开户
	i32 deleteById(1:i32 pid);
	//批量删除机构客户银行开户
	i32 deleteByIds(1:list<i32> pids);
	
	//查询机构客户银行开户列表(可分页)
	list<BizPartnerCustomerBank> selectList(1:BizPartnerCustomerBank bizPartnerCustomerBank);
	//查询机构客户银行开户总数
	i32 selectTotal(1:BizPartnerCustomerBank bizPartnerCustomerBank);
}








