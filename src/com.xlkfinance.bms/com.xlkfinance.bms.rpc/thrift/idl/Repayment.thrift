namespace java com.xlkfinance.bms.rpc.repayment
include "System.thrift"
include "Common.thrift"



/*利率变更申请书*/
struct InterestChgApplyView{
    1:i32 pId;
	2:i32 projectId;
	3:string projectNumber;
	4:string projectName;
	5:i32 repayCycle; //贷款期限（月）
	6:double creditAmt;//	贷款金额
	7:string requestDttm;//利率变更申请时间
	8:double monthLoanInterest;//原借利息
	9:double  cGmonthLoanInterest;//申请变更利率
    10:string realName;//申请人
    11:double monthLoanMgr;//管理费率（月）
    12:double cgmonthLoanMgr;//申请变更管理费率（月）
    14:string changeReason;//申请原因 
    15:i32 interestChgId;	
    13:string creditGuarantee;//担保方式	      信用
    16:string guaranteeGuarantee;//担保方式        保证
    17:string pledgeGuarantee;//担保方式             抵押
    18:string thPledgeGuarantee;//担保方式	质押
    19:string lookupDesc;// LOOKUP_DESC
    20:double contractInterest;//原借合同利息
   

}
/*利率变更申请书*/
struct InterestChgApplyDTO{
    1:i32 pId;
	2:i32 projectId;
	3:string projectNumber;
	4:string projectName;
	5:i32 repayCycle; //贷款期限（月）
	6:double creditAmt;//	贷款金额
	7:string requestDttm;//利率变更申请时间
	8:double monthLoanInterest;//原借合同利息
	9:double  cGmonthLoanInterest;//申请变更利率
    10:string realName;//申请人
    11:double monthLoanMgr;//管理费率（月）
    12:double cgmonthLoanMgr;//申请变更管理费率（月）
    14:string changeReason;//申请原因 	
    15:i32 interestChgId;
    13:string creditGuarantee;//担保方式	      信用
    16:string guaranteeGuarantee;//担保方式        保证
    17:string pledgeGuarantee;//担保方式             抵押
    18:string thPledgeGuarantee;//担保方式	质押
    19:string lookupDesc;// LOOKUP_DESC
    20:double contractInterest;//原借合同利息

}
/**费用减免申请书*/
struct FeeWaiverApplicationDTO{
	1: i32 projectId;				//项目ID
	2: i32 repayId;					//费用减免ID
	3: string projectNumber;		//项目编号
	4: string projectName;			//项目名称
	5: string requestDttm; 			//申请时间
	6: string completeDttm;			//申请结束时间
	7: string requestStatus;		//申请状态
	8: string waiverDttm			//减免申请开始时间
	9: string pmuserName;  			//申请人
	10: string cusName;				//客户名称
	11: string planOutLoanDt;		//借款开始日期
	12: string planRepayLoanDt;		//借款结束日期
	13: double creditAmt;			//借款金额
	14: string contractNo;			//借款合同编号
	15: double monthLoanMgr;		//管理费率（月）
	16: string lastReceiveDt;		//最后还款日期
	17: string waiverReason;		//费用减免申请原因
	18: string repayCycle;			//借款期限
	19: double monthLoanInterest;	//贷款利率（月）
	20: string lookupVal;			//担保方式
	21: double returnPrincipal;		//已归还本金
	23: double recAccrual;			//应收总计利息
	24: double derAccrual;			//减免合计利息
	25: double derLastAccrual;		//减免后应收利息
	26: double recManageFee;		//应收总计管理费
	27: double derManageFee;		//减免合计管理费
	28: double derLastManageFee;	//减免后应收管理费
	29: double recElseFee;			//应收总计其他费用
	30: double derElseFee;			//减免合计其他费用
	31: double derLastElseFee;		//减免后应收其他费用
	32: double recOveAcc;			//应收总计逾期利息
	33: double derOveAcc;			//减免合计逾期利息
	34: double derLastOveAcc;		//减免后应收逾期利息
	35: double recLateCharge;		//应收总计逾期罚息
	36: double derLateCharge;		//减免合计逾期罚息
	37: double derLastLateCharge;	//减免后应收逾期罚息
	38: double recTotal;			//应收总计合计
	39: double derTotal;			//减免合计合计
	40: double derLastTotal;		//减免后应收合计
}

/*费用减免列表*/
struct CostDerateTabulationView{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType; 
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
     9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    13:string  planOutLoanDt;
    14:string  requestStatus;
    17:i32 repayId;
    16:i32 pmUserId;
    18:string compelteAdvDttm;
    
}

/*费用减免列表*/
struct CostDerateTabulationDTO{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType;
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
    9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    15:string  planOutLoanDt;
    16:string  requestStatus;
    13:i32 rows;
    14:i32 page;
    18:i32 pmUserId;
    17:i32 repayId;
    19:string compelteAdvDttm;
}	


/*费用减免-查询及展示*/
struct CostDerateDTO{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType;
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
    9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    15:string  planOutLoanDt;
    13:i32 rows;
    14:i32 page;
    16:i32 pmUserId;
    17:i32 repayId;
}

/*费用减免-查询及展示*/
struct CostDerateView{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType; 
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
     9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    13:string  planOutLoanDt;
    16:i32 pmUserId;
    17:i32 repayId;
    
}

/*利率变更列表*/
struct AvailabilityChangeTabulationView{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusName;
	5:double creditAmt;
	6:string requestAdvDttm;/*提前申请时间*/
	7:i32 cusType;
	8:string pmuserName;
	9:i32 status;
	10:string requestAdvDttmLast;/*提前申请时间*/
	11:string compelteAdvDttm;/*提前结束时间*/
	12:string  ecoTrade;/*企业经济行业类别*/
	13:string requestLoanDttm;/*贷款开始时间*/
	14: string planRepayLoanDt;/*贷款完成时间*/
	15:string  planOutLoanDt;
	18:i32 pmUserId;
	19:i32 interestChgId;
}

/*利率变更列表*/
struct AvailabilityChangeTabulationDTO{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusName;
	5:double creditAmt;
	6:string requestAdvDttm;/*提前申请时间*/
	7:i32 cusType;
	8:string pmuserName;
	9:i32 status;
	10:string requestAdvDttmLast;/*提前申请时间*/
	11:string compelteAdvDttm;/*提前结束时间*/
	12:string  ecoTrade;/*企业经济行业类别*/
	13:string requestLoanDttm;/*贷款开始时间*/
	14: string planRepayLoanDt;/*贷款完成时间*/
	17:string  planOutLoanDt;
    15:i32 rows;
    16:i32 page;
    18:i32 pmUserId;
	19:i32 interestChgId;
}	


/*利率变更-查询及展示*/
struct AvailabilityChangeDTO{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType;
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
     9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    15:string  planOutLoanDt;
    13:i32 rows;
    14:i32 page;
    16:i32 pmUserId;
    17:i32 interestChgId;
    
}

/*利率变更-查询及展示*/
struct AvailabilityChangeView{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType; 
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
     9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    13:string  planOutLoanDt;
    16:string pmUserId;
    17:i32 interestChgId;
}

/*查询及展示*/
struct RepaymentView{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType; 
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
     9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    13:string planOutLoanDt;
    17:i32 pmUserId;
    18:i32 projectType;
}
/*查询及展示*/
struct RepaymentBaseDTO{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusType;
	5:double creditAmt;
	6:string requestDttm;
	7: string planRepayLoanDt;
    8:string pmuserName;
     9: string cusName;
 	10: string planRepayLoanDtLast;
    11:string requestDttmLast;
    12:string  ecoTrade;/*企业经济行业类别*/
    15:string  projectNum;
    16:string planOutLoanDt;
    13:i32 rows;
    14:i32 page;
    17:i32 pmUserId;
  //  RepaymentBaseDTO
}

/*提前还款列表*/
struct AdvRepaymentView{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusName;
	5:double creditAmt;
	6:string requestAdvDttm;/*提前申请时间*/
	7:i32 cusType;
	8:string pmuserName;
	9:i32 status;
	10:string requestAdvDttmLast;/*提前申请时间*/
	11:string compelteAdvDttm;/*提前结束时间*/
	12:string  ecoTrade;/*企业经济行业类别*/
	13:string requestLoanDttm;/*贷款开始时间*/
	14: string planRepayLoanDt;/*贷款完成时间*/
	18:i32 requestStatus;//requestStatus
	19:i32 preRepayId;//提前还款id
	20:i32 loanId;//LOANID
    21:string requestDttm;//request_Dttm
    22:string repayDate;//提前还贷时间
	
}

/*提前还款列表*/
struct AdvRepaymentBaseDTO{
    1: string pId;
	2:string projectId;
	3:string projectName;
	4:string cusName;
	5:double creditAmt;
	6:string requestAdvDttm;/*提前申请时间*/
	7:i32 cusType;
	8:string pmuserName;
	9:i32 status;
	10:string requestAdvDttmLast;/*提前申请时间*/
	11:string compelteAdvDttm;/*提前结束时间*/
	12:string  ecoTrade;/*企业经济行业类别*/
	13:string requestLoanDttm;/*贷款开始时间*/
	14: string planRepayLoanDt;/*贷款完成时间*/
	17: string projectNum;/*贷款完成时间*/
    15:i32 rows;
    16:i32 page;
    18:i32 requestStatus;//requestStatus
    19:i32 preRepayId;//提前还款id
    20:i32 loanId;//LOANID
    21:string requestDttm;//request_Dttm
    22:string repayDate;//提前还贷时间
    23:i32 pmUserId;
    
}	


/*提前还款信息 AvailabilityChangeTabulationView*/
struct AdvapplyRepaymentView{
    1:string pId;
	2:string planRepayDt;
	3:i32 planCycleNum;
	4:double shouldPrincipal;
	5:double shouldMangCost;
	6:double shouldInterest;
	7:double total;
	8:double principalBalance;/*本金余额*/
	9:double overdueLoanmt;/*逾期利息金额 */
	10:double overdueFineAmt;/*逾期 罚息金额*/
	11:double shouldOtherCost;/*其他费用*/
	12:string shouldOtherCostName;/*其他费用说明*/
	13:double shouldTotailAmt;/*应收合计*/
	14:double hasReceiveAmt;/*已收合计*/
	15:string receiveDt;/*收款日期*/
	16:double noReceiveAmt;/*未受合计*/
	17:i32 isReconciliation;/*是否对账成功*/
	
}

/*违约DTO*/
struct RepaymentViolationView{
    1:string pId;
	2:i32 projectId;/*项目ID*/
	3:i32 isTermination;/*是否项目终止    */
	4:double loanAmt;/*贷款金额  */
	5:double violationProportion;/*违约金比例  */
	6:double violationAmt;/*应收违约金额  */
	7:string violationDt;/*应收日期 */
	8:double violationOtInterest;/*违约金逾期费率   */
	9:i32 isBacklist;/*是否加入黑名单   */
	10:i32 reviewStatus;/*违约处理审核状态    */
	11:string remark;/*备注 */
	12:i32 isReconciliation;/*是否对账 */		
}
/*挪用DTO*/
struct RepaymentDivertView{
    1:string pId;
	2:double divertAmt;/*挪用金额  */
	3:double divertFine;/*挪用罚息利率 */
	4:string divertFineBeginDt;/*挪用罚息开始日期  */
	5:i32 projectId;/*项目ID*/
	6:i32 regulatoryUserId;/*挪用处理申请人  */
	7:string planBeginDt;/*计划监管时间  */
	8:string remark;/*备注 */
	9:i32 reviewStatus;/*挪用处理审核状态  */
	10:string divertFinePayDt;/*挪用交钱日期*/
	11:double divertFinePayAmt;/*挪用罚息 */
	12:i32 isReconciliation;/*是否对账 */		
	
}
/*坏账DTO*/
struct RepayProjectBadDTO{
    1:string pId;
	2:i32 projectId;/*项目ID*/
	3:string beginCycleNum;/*坏账开始期数  */
	4:string asOfDt;/*计息截止日期  */
	5:string badShouldAmt;/*坏帐处理后应收金额 */
	6:string ShouldDt;/*应收日期  */
	7:double badLossAmt;/*坏帐处理后损失金额  */
	8:string remark;/*备注  */
	9:i32 reviewStatus;/*坏账处理审核状态  */
 }
/*逾期利息*/
struct OverInterest{
    1: string pId;
	2:double overdueLoanInterest;/*逾期利息*/
	3:double overdueFineInterest;/*逾期fakuan 利息*/

}
/*还款对账*/
struct RepayEconciliationDTO{
    1:string pId;
	2:string loanInfoId;/*贷款id*/
	3:i32 reconciliationType;/*对账类型*/
	4:i32 reconciliationCycleNum;/*对账还款期数*/
	5:i32 realtimeId;/*对账及时发生id*/
	6:double reconciliationAmt;/*对账实收款金额*/
	7:i32 dtlType;/*类型（本金、管理费、其它费用、利息、逾期利息、逾期罚息、等）*/
	8:double dtlAmt;/*对账明细金额*/
 
 }
/*及时还款plan*/
struct RealtimePlan{
    1:string pId;
	2:i32 loanId;
	3:string operRepayDt;/*发生日期*/
	4:i32 operType;/*类型（提前还款、提前还款罚金、挪用罚息、违约罚金）*/
	5:double baseAmt;/*发生基数*/
	6:double operCost;/*发生费用*/
	7:string genDttm; /*生成时间*/
	8:i32 operUserId;/*办理人员*/
	9:double total;/*合计*/
	10:double principalBalance;/*本金余额*/
	11:i32 planVersion;/*版本*/
	12:i32 status;/*状态*/
	13:i32 refId;/*与挪用表、违约表关联的id*/
	14:i32 isReconciliation;/*是否对账*/
	15:i32 refundType; /*退款类型（提前还款=1，利息变更=2）*/ 
	16:i32 freezeStatus; /*冻结状态（0=冻结，1=解冻）*/ 
}

/*提前还款登记信息*/
struct RegAdvapplyRepayView{
    1: string pId;
    2: string contractLoadNo;/*授信合同标号*/
    3: string contractCreditNo;/*授信合同标号*/
    4:double creditAmt;
    5:i32 projectType;
    6:double monthLoanInterest;
    7:i32 repayCycle;
    8:string projectNum;
    9:string projectName;
    10:i32 projectId;
    11:double preRepayAmt;
    12:double fineRates;
    13:string repayDate;
    14:double principalBalance;
    15:double fine;
    16:i32 hasOtherLoan;
    17:i32 isRebackInterest;
    18:i32 isArrears;
    19:string reason;
    20:i32 preRepayId;
    21:i32 loanId;
    22:string planRepayLoanDt;/*剩余本金还贷日期*/
    23:double prepayLiqDmgProportion;
    24:string businessName;
    25:string creditAmtTemp;
    
    
    
    
}

/*提前还款登记信息*/
struct RegAdvapplyRepayDTO{
    1: string pId;
    2: string contractLoadNo;/*授信合同标号*/
    3: string contractCreditNo;/*授信合同标号*/
    4:double creditAmt;
    5:i32 projectType;
    6:double monthLoanInterest;
    7:i32 repayCycle;
    8:string projectId;
    9: string fileType;/*文件种类jpg  word*/
    10: string fileName;
    11:i32 fileSize;
    12:string uploadDttm;
    13:string fileFunType;/*文件类型*/
    14:string fileDesc;
    15:i32 rows;
    16:i32 page;
    17:string projectName;
    18:string filePath;
    24:string businessName;
}
/*上传文件view*/
struct RegAdvapplyFileview{
    1: i32 pId;
    2: string fileType;/*文件种类jpg  word*/
    3: string fileName;
    4:i32 fileSize;
    5:string uploadDttm;
    6:string fileFunType;/*文件类型*/
    7:string projectId;
    8:string fileDesc;
    9:string filePath;
    10:i32 interestChgId;//利息变更
    13:i32 preRepayId;//提前还款
    14:i32 repayId;//费用减免
    15:i32 divertId;//挪用id
    16:i32 baseId;//抵质押物id
    17:i32 badId;//坏账id
    18:i32 page;
	19:i32 rows;
	20:i32 pmUserId;//项目经理
	21:i32 divertPid;	//挪用 文件表PID
    
}

struct AdvapplyRepaymentBaseDTO{
    1: AdvapplyRepaymentView advapplyRepaymentView;
    2:i32 rows;
    3:i32 page;
    4:string projectId;
	5:string projectName;
   
}

struct UploadinstAdvapplyBaseDTO{

	   1:string fileType;
	   2:string fileKinds;
	   3:string fileDesc;
	   4:string filePath;
	   5:i32 projectId;
	   6:string fileName;
	   7:i32  fileSize;
	   8:string uploadUserid;
	   9:string fileDttm;
	   10 :i32 pId;
	   11 :i32 fileId;
	   12:i32 interestChgId;//利息变更
	   13:i32 preRepayId;//提前还款
	   14:i32 repayId;//费用减免
	   15:i32 divertId;//挪用id
	   16:i32 baseId;//抵质押物id
	   17:i32 badId;//坏账id
       18:i32 page;
       19:i32 rows;	
       20:i32 pmUserId;//项目经理
}

	
struct	SaveAdvRepaymentBaseDTO{
 	    1: double actualAmt;
		2:double principalBalance;
        3:double liqDmgProportion;
        4:double prepayLiqDmgProportion;
        5:string receiveDt; 
        6:string BrocontractNo;
        7:string CreditContractNo;
        8:double creditAmt;
        9:string  projectType;
       10:i32 repayCycle;
       11:double monthLoanInterest;

}


struct	ExecutiveOperBaseDTO{
     1:string applyReason ;
 	 2:string applyProgress;
	 3:string remindType ;
    

}
struct ApplyfileuploadBaseDTO{

      1:string uploadUserid ;
 	  2:string fileName;
	  3:string fileSize ;
     4:i32	 pId;
     5:string	uploadDttm;
	6:string fileType;
	7:string fileUrl;
    
}

/*提前还款申请表信息*/
struct PreApplyRepayBaseDTO{
     1:i32 pId;
 	 2:double preRepayAmt;/*实收剩余本金*/
	 3:double fineRates;/*提前还款罚金比例*/
     4:double	  fine;/*实收提前还款费*/
     5:i32	isArrears;/*是否拖欠我公司款项*/
     6:i32	isRebackInterest;/*是否退还已收取的利息*/
     7:i32	hasOtherLoan;/*在我公司是否有其他借款*/
     8:string	reason;/*申请原因*/
     9:string repayDate;/*提前还款日期*/
     10: double surplus;/*应收剩余本金*/
     11:i32 loanId;/*贷款ID*/
     12:i32 requestStatus;/*申请状态*/
     13:string requestDttm;/*申请时间*/
     14:string compelteDttm;/*完成时间*/
     16:i32 preRepayId;/*状态*/
     15:i32 status;/*状态*/
     17:string planRepayLoanDt;/*剩余本金还贷日期*/
     18:i32 projectId;	// 项目id
     //新增
     19:i32 loanPlanId;//还款计划ID
     20:double shouldPrepaymentFee;//应收提前还款费
     21: string createDate;//创建时间
	 22: i32 createrId;//创建人
	 23: i32 updateId;//修改人
	 24: string updateDate;//修改日期
}



// 还款计划表
struct RepaymentPlanBaseDTO{
    1:i32 loanInfoId;	// 贷款ID
 	2:string genDttm;
	3:i32 genUserId;
    4:string  planRepayDt;	// 还款日期
    5:i32	planCycleNum;	// 还款期数
    6:i32	planType;		// 还款类型
    7:i32	exTarget;		// 展期期数
    8:i32	exType;			// 展期类型
    9:double   shouldPrincipal;			// 应还本经
    10:double   shouldMangCost;			// 本期应付管理费
    11:double  shouldOtherCost;			// 其他费用
    12:string   shouldOtherCostName;	// 其他费用名称
    13:double    shouldInterest;		// 本期利息
    14:double   total;					// 本期合计
    15:double   principalBalance;		// 本金余额
    16:i32   thisStatus;				
    17:i32  planVersion;				// 版本好
    18:i32   status;
    19:i32   pId;
    20:i32 isReconciliation;			// 对账状态
    21:i32 freezeStatus;
    22:string planCycleName;			// 页面展示期数名称
    23:i32 operType;					// 即时表 操作类型 挪用罚息=1、违约罚金=2。坏账=3、退管理费=4、退其他费用=5、退利息=6、提前还款=7、提前还款罚金=8、利率变更=9、手续费=10
    24:double operCost;					// 即时表
    25:i32 dataType;					// 数据类型  1 即时表 2 还款计划表
	28:i32 overdueDays;					// 逾期天数
	29:double overdueInterest;				// 应收逾期利息
	30:double overdueFine;					// 应收逾期罚息
	31:double accountsTotal;				// 应收合计
	32:double receivedTotal;				// 已收合计
	33:double uncollectedTotal;			// 未收合计
	34:list<RepaymentPlanBaseDTO> results; 
	35:i32 isExtension;		// 是否展期 1 展期 2 非展期
	36:double rebateFee;//返佣利息
	37:double monthMaidRate;//月返佣费率
	38:i32 projectId;//项目ID
	39:double actualOverdueMoney;//实收逾期金额
	40:double shouldOverdueMoney;//应收逾期金额
	41:double shouldPenalty;//应收罚息
	42:double actualPenalty;//实收罚息
	43:double actualPrincipal;//实收本金
	44:double actualInterest;//实收利息
	45:double actualTotal;//实收合计
	46:double overdueMoney;//本期逾期金额
	47:i32 paymentId;//实收表ID
	48:i32 overdueId;//逾期表ID
	49:double overdueRate;//逾期日罚息率
	50:list<i32> pidList;//主键ID集合
	51:i32 loanTerm;//借款期限
	52:i32 repaymentId;//回款表ID
	53:double rentMoney;//租金
	54:double shouldPaymentMoney;//借款人应还
}
/*
 * 项目结清--客户业务查询
 * 表：biz_project 项目信息表 biz_loan 贷款信息表
 */
struct CusBusiness{
	1:i32 pid;
	2:string projectName;
	3:i32 acctType;//客户类型查询条件
	4:string acctTypeText;//客户类型查询列表
	5:double creditAmt;//贷款金额
	6:i32 pmUserId;//项目经理
	7:string planOutLoanDT;//放款日期
	8:string planRepayLoanDT;//还款日期
	9:string projectId;//项目编号
	10:string cusName;//客户名称
	11:string requestStartDt;//申请开始时间
	12:string requestEndDt;//申请结束时间
	13:string ecoTrade;//经济行业类别
	14:string pmuserName;//项目经理
	15:string settleProcessBeginDT;//结清处理开始时间
	16:string settleProcessEndDT;//结清处理结束时间
	17:string is_complete;//结清处理状态
	18:string settleDT;//结清时间
	19:i32 page;
	20:i32 rows;
	21: i32 loanId;//贷款id
}

/*
 * 结清表
 */
struct ProjectComplete{
	1:i32 pId;
	2:i32 projectId;
	3:string completeDesc;//结清意见
	4:string completeDttm;//结清时间
	5:string is_complete;//结清处理状态
}

/*
 * 结清表上传文件
 */
struct ProjectCompleteFile{
	1:i32 pId;
	2:i32 completeId;//项目结清Id
	3:string fileDesc;//上传说明
	4:i32 fileId;//文件Id
	5:i32 Legalconfirmation;//法务确认
	6:string fileType;//文件类型
	7:i32 fileSize;//文件大小
	8: string fileUrl;
	9: string uploadDttm;
	10: string fileName;
}

/*
 * 结清文件
 */
struct SettleFile{
	1:string outputDttm;
	2:string acctName;//客户名
	3:string creditAmt;
	4:string contractNum;
	5:string planRepayDttm;//计划还款日期
	6:string repayDttm;//最后还款日期
	7:string accName;//开户名
	8:string bankCardId;//账户
	9:string bankName;//开户行
	10:string outputAccName;//放款账户
	11:i32 bankCardType;//账户类型
}
/*
 * 逾期查询view
 */
struct RepayOverdueView{
	1:i32 pId;
	2:string projectName;//项目名称
	3:string projectId;//项目编号
	4:i32 acctType;//客户类型查询条件
	5:string cusName;//客户名称
	6:string requestStartDt;//贷款申请开始时间
	7:string requestStartDtLast;//贷款申请开始时间
	8:string planRepayDt;//到期时间PLAN_REPAY_DT（还款计划表中，某一期的计划还款日期在这范围内的项目查询）
	9:string planRepayDtLast;//到期时间PLAN_REPAY_DT（还款计划表中，某一期的计划还款日期在这范围内的项目查询）
	10:i32 overdueTime;//已逾期间(日)
	11:i32 overdueTimeLast;//已逾期间(日)
	12:string ecoTrade;//经济行业类别
	13:i32 isReconciliation;
	14:i32 planCycleNum;
	16:i32 rows;
    17:i32 page;
    18:i32 rowNum;
    19: list<RepayOverdueDTO> results;
	
}
//逾期中部分对账的
struct RepayOverduePartDTO{
    1:i32 pId;
	2: double shouldPrincipal;//应 还本金SHOULD_PRINCIPAL
	3: double shouldInterest;//应付利息SHOULD_INTEREST
	4: double shouldMangCost;// 应付管理费 SHOULD_MANG_COST
	5: double shouldOtherCost;//其它费用SHOULD_OTHER_COST
    6:double overdueFineInterestAmt;//未收逾期利息总额OVERDUE_FINE_INTEREST
	7:double overdueLoanInterestAmt;//未收逾期罚息总额OVERDUE_LOAN_INTEREST
	8:double overdueFineInterest;//未收逾期利息OVERDUE_FINE_INTEREST
	9:double overdueLoanInterest;//未收逾期罚息OVERDUE_LOAN_INTEREST
    10:i32 projectId;
	11:i32 planCycleNum;
}
//逾期中部分对账的
struct RepayOverduePartView{
    1:i32 pId;
	2: double shouldPrincipal;//应 还本金SHOULD_PRINCIPAL
	3: double shouldInterest;//应付利息SHOULD_INTEREST
	4: double shouldMangCost;// 应付管理费 SHOULD_MANG_COST
	5: double shouldOtherCost;//其它费用SHOULD_OTHER_COST
    6:double overdueFineInterestAmt;//未收逾期利息总额OVERDUE_FINE_INTEREST
	7:double overdueLoanInterestAmt;//未收逾期罚息总额OVERDUE_LOAN_INTEREST
	8:i32 projectId;
	9:i32 planCycleNum;
	10:double overdueFineInterest;//未收逾期利息OVERDUE_FINE_INTEREST
	11:double overdueLoanInterest;//未收逾期罚息OVERDUE_LOAN_INTEREST
}

/*
 * 逾期查询dto
 */
struct RepayOverdueDTO{
	1:i32 pId;
	2:string cusName;//客户名称
	3: string contractLoadNo;//合同编号CONTRACT_LOAD_NO
	4: double shouldPrincipal;//应 还本金SHOULD_PRINCIPAL
	5: double shouldInterest;//应付利息SHOULD_INTEREST
	6: double shouldMangCost;// 应付管理费 SHOULD_MANG_COST
	7: double shouldOtherCost;//其它费用SHOULD_OTHER_COST
	8: double hashouldPrincipal;//已还本金SHOULD_PRINCIPAL
	9: double hasshouldInterest;//已付利息SHOULD_INTEREST
	10: double hasshouldMangCost;// 已付管理费 SHOULD_MANG_COST
	11: double hasshouldOtherCost;//已付其它费用SHOULD_OTHER_COST
	12: double noshouldPrincipal;//未还本金SHOULD_PRINCIPAL
	13: double noshouldInterest;//未付利息SHOULD_INTEREST
	14: double noshouldMangCost;// 未付管理费 SHOULD_MANG_COST
	15: double noshouldOtherCost;//未付其它费用SHOULD_OTHER_COST
	16:string projectName;//项目名称PROJECT_NAME
	17:string projectNumber;//项目编号PROJECT_NUMBER
	18:double noverdueFineInterest;//未收逾期利息OVERDUE_FINE_INTEREST
	19:double noverdueLoanInterest;//未收逾期罚息OVERDUE_LOAN_INTEREST
	20:double principalBalance;//未到期本息PRINCIPAL_BALANCE
	21:double noTotal;//未收合计noTotal
	22:i32 isReconciliation;//IS_RECONCILIATION
	23:i32 planCycleNum;//PLAN_CYCLE_NUM
    24:i32 rowNum;//ROWNUM
    25:double overdueFineInterest;//逾期利息OVERDUE_FINE_INTEREST
	26:double overdueLoanInterest;//逾期罚息OVERDUE_LOAN_INTEREST
	27:string planRepayDt;//还款日期PLAN_REPAY_DT
	28:i32 loanInfoId;
	29:i32 projectId;
	30:i32 page;
	31:i32 pCount;
	32: list<RepayOverdueDTO> results;
	
}

//利率变更
 struct RepayCgInterestView{
	1:i32 pId;
	2:i32 projectId; // 项目ID
	3:double creditAmt;// 贷款金额
	4:i32 currency;// 币种 value
	5:string currencyText;// 币种 text
	6:i32 dateMode;// 日期模式 value
	7:string dateModeText;// 日期模式 text
	8:i32 repayFun;// 还款方式 value
	9:string reoayFunText;// 还款方式 text
	10:i32 repayCycleType;// 还款周期类型 value
	11:string repayCycleTypeText;// 还款周期类型 text
	12:i32 repayCycleDate;// 还款周期日期 
	13:i32 repayCycle;// 还款期限
	14:string planOutLoanDt;// 计划放款日期
	15:string planRepayLoanDt;// 计划还款日期
	16:i32 repayOption;// 还款选项
	17:i32 repayDate;// 每期还款日
	18:double monthLoanInterest;// 月贷款利率
	19:double monthLoanMgr;// 月贷款管理费率
	20:double monthLoanOtherFee;// 月其它费用费率
	21:double liqDmgProportion;// 违约金比例
	22:double overdueLoanInterest;// 逾期贷款利率
	23:double overdueFineInterest;// 逾期罚息利率
	24:double misFineInterest;// 挪用罚息利率
	25:double prepayLiqDmgProportion;// 提前还款违约金比例
	26:i32 requestStatus;// 申请状态
	27:string requestDttm;// 申请时间
	28:string completeDttm;// 完成时间
	29:i32 status;// 状态(预留字段)
	30:double yearLoanInterest;// 年贷款利率
	31:double yearLoanMgr;// 年贷款管理费率
	32:double yearLoanOtherFee;// 年其它费用费率
	33:double dayLoanInterest;// 日贷款利率
	34:double dayLoanMgr;// 日贷款管理费率
	35:double dayLoanOtherFee;// 日其它费用费率
	36:string projectName; // 项目名称
	37:string projectNumber; // 项目编号
	38:i32 interestVersion;
	39:string reason;
	40:i32 loanId;
	41:i32 interestChgId;//利息变更id
	42:i32 isReturnInterest;
	43:i32 isAllowPrepay;	
	44:string surveyResult;
	}

//利率变更
 struct RepayCgInterestDTO{
	1:i32 pId;
	2:i32 projectId; // 项目ID
	3:double creditAmt;// 贷款金额
	4:i32 currency;// 币种 value
	5:string currencyText;// 币种 text
	6:i32 dateMode;// 日期模式 value
	7:string dateModeText;// 日期模式 text
	8:i32 repayFun;// 还款方式 value
	9:string reoayFunText;// 还款方式 text
	10:i32 repayCycleType;// 还款周期类型 value
	11:string repayCycleTypeText;// 还款周期类型 text
	12:i32 repayCycleDate;// 还款周期日期 
	13:i32 repayCycle;// 还款期限
	14:string planOutLoanDt;// 计划放款日期
	15:string planRepayLoanDt;// 计划还款日期
	16:i32 repayOption;// 还款选项
	17:i32 repayDate;// 每期还款日
	18:double monthLoanInterest;// 月贷款利率
	19:double monthLoanMgr;// 月贷款管理费率
	20:double monthLoanOtherFee;// 月其它费用费率
	21:double liqDmgProportion;// 违约金比例
	22:double overdueLoanInterest;// 逾期贷款利率
	23:double overdueFineInterest;// 逾期罚息利率
	24:double misFineInterest;// 挪用罚息利率
	25:double prepayLiqDmgProportion;// 提前还款违约金比例
	26:i32 requestStatus;// 申请状态
	27:string requestDttm;// 申请时间
	28:string completeDttm;// 完成时间
	29:i32 status;// 状态(预留字段)
	30:double yearLoanInterest;// 年贷款利率
	31:double yearLoanMgr;// 年贷款管理费率
	32:double yearLoanOtherFee;// 年其它费用费率
	33:double dayLoanInterest;// 日贷款利率
	34:double dayLoanMgr;// 日贷款管理费率
	35:double dayLoanOtherFee;// 日其它费用费率
	36:string projectName; // 项目名称
	37:string projectNumber; // 项目编号
	38:i32 interestVersion;
	39:string reason;
	40:i32 loanId;
	41:i32 interestChgId;		//利息变更id
	42:i32 isReturnInterest;
	43:i32 isAllowPrepay;	
	44:string surveyResult;			
	
	
	}
	//费用减免
 struct RepayFeewdtlDatDTO{
	1:i32 pId;
	2:i32 loanId;
	3:i32 repayId;//费用减免id
	4:i32 projectId; // 项目ID
	5:string projectName; // 项目名称
	6:string projectNum; // 项目编号
	7: double shouldPrincipal;//应 还本金SHOULD_PRINCIPAL
	8: double shouldInterest;//应付利息SHOULD_INTEREST
	9: double shouldMangCost;// 应付管理费 SHOULD_MANG_COST
	10: double shouldOtherCost;//其它费用SHOULD_OTHER_COST
    11:double overdueFineInterestAmt;//未收逾期利息总额OVERDUE_FINE_INTEREST
	12:double overdueLoanInterestAmt;//未收逾期罚息总额OVERDUE_LOAN_INTEREST
	13:double total;//总数
	14:string reason;
	15:i32 status;// 状态(预留字段)
	16:i32 requestStatus;// 申请状态
	17:string requestDttm;// 申请时间
	18:string completeDttm;// 完成时间
	19:string typeName;//应收 减免后应收 减免
	20:i32 planCycleNum;//期数
	21:i32 repaymentId;//减免还款期数ID	
						
					
	}
		//费用减免
 struct RepayFeewdtlDatView{
		1:i32 pId;
	2:i32 loanId;
	3:i32 repayId;//费用减免款id
	4:i32 projectId; // 项目ID
	5:string projectName; // 项目名称
	6:string projectNum; // 项目编号
	7: double shouldPrincipal;//应 还本金SHOULD_PRINCIPAL
	8: double shouldInterest;//应付利息SHOULD_INTEREST
	9: double shouldMangCost;// 应付管理费 SHOULD_MANG_COST
	10: double shouldOtherCost;//其它费用SHOULD_OTHER_COST
    11:double overdueInterest;//未收逾期利息总额OVERDUE_FINE_INTEREST
	12:double overdueFine;//未收逾期罚息总额OVERDUE_LOAN_INTEREST
	13:double total;//总数
	14:string reason;
	15:i32 status;// 状态(预留字段)
	16:i32 requestStatus;// 申请状态
	17:string requestDttm;// 申请时间
	18:string completeDttm;// 完成时间
	19:string typeName;//应收 减免后应收 减免
	20:i32 planCycleNum;//期数
	21:i32 repaymentId;//减免还款期数ID
	22:string planRepayDt;
}

	//项目五级分类
 struct ProjectLevel{
	1:i32 pid;
	2:i32 projectId;				//项目id
	3:i32 projectLevel;				//五级分类级别
	4:i32 processUserId; 			//最后处理人
	5:string updateDttm; 			//最后处理时间
	6:i32 status; 					//状态
	7:string projectName;			//项目名称
	8:string projectNumber;			//项目编号
	9:string cusType;				//客户类别
	10:string guaranteeType;		//担保方式
    11:string requestDttm;			//申请时间
	12:i32 overduenum;				//逾期天数
	13:i32 rows;
    14:i32 page;
    15:string projectNum;			//项目编号
    16:string cusName;				//客户名称
    17:string requestDttmEnd;		//结束时间
    18:string ecoTrade;				//经济行业类别
    
}

//上传
 service ProjectLevelService {
	//获取项目分类级别表数据
	list<ProjectLevel>  getProjectLevelInfo(1:ProjectLevel projectLevel) throws (1:Common.ThriftServiceException e);
	//获取记录数
	i32  getProjectLevelCount(1:ProjectLevel projectLevel) throws (1:Common.ThriftServiceException e);
	//保存，修改 项目级别分类
	bool saveProjectLevelInfo  (1:list<ProjectLevel> listCtp);
}
service RepaymentService {
     //更具projectId查询贷款信息
    //  LoanInfo   queryloaninfobyprojectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	list<RepaymentView>  getRepaymentList(1:RepaymentBaseDTO repaymentBaseDTO) throws (1:Common.ThriftServiceException e);
	list<AvailabilityChangeView>  getChangeList(1:AvailabilityChangeDTO availabilityChangeDTO) throws (1:Common.ThriftServiceException e);
	i32  getTempTotaleCount(1:RepaymentBaseDTO repaymentBaseDTO) throws (1:Common.ThriftServiceException e);
	i32  getChangeTotaleCount(1:AvailabilityChangeDTO availabilityChangeDTO) throws (1:Common.ThriftServiceException e);
	
    list<AvailabilityChangeTabulationView> 	getAvailabilityTabulation(1:AvailabilityChangeTabulationDTO availabilityChangeTabulationDTO) throws (1:Common.ThriftServiceException e);
	i32  getAvailabilityTabulationCount(1:AvailabilityChangeTabulationDTO availabilityChangeTabulationDTO) throws (1:Common.ThriftServiceException e);
	
	
		
	list<CostDerateView> 	getCostDerate(1:CostDerateDTO costDerateDTO) throws (1:Common.ThriftServiceException e);
	i32  getCostDerateCount(1:CostDerateDTO costDerateDTO) throws (1:Common.ThriftServiceException e);
	
	
	list<CostDerateTabulationView> 	getCostDerateTabulation(1:CostDerateTabulationDTO costDerateTabulationDTO) throws (1:Common.ThriftServiceException e);
	i32  getCostDerateTabulationCount(1:CostDerateTabulationDTO costDerateTabulationDTO) throws (1:Common.ThriftServiceException e);
	
	
    list<AdvRepaymentView> 	getAdvrepaymenturl(1:AdvRepaymentBaseDTO advRepaymentBaseDTO) throws (1:Common.ThriftServiceException e);
	i32  getAdvRepayTotaleCount(1:AdvRepaymentBaseDTO advRepaymentBaseDTO) throws (1:Common.ThriftServiceException e);
    list<AdvapplyRepaymentView> getAdvapplyrepaymenturl(1:AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO) throws (1:Common.ThriftServiceException e);
	i32  getAdvapplyRepayTotaleCount(1:AdvapplyRepaymentBaseDTO advapplyRepaymentBaseDTO) throws (1:Common.ThriftServiceException e);
	i32  saveAdvRepayment(1:SaveAdvRepaymentBaseDTO saveAdvRepaymentBaseDTO) throws (1:Common.ThriftServiceException e);
     i32	uploadinstAdvapply(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws (1:Common.ThriftServiceException e);
    i32   executiveOperation(1:ExecutiveOperBaseDTO executiveOperBaseDTO) throws (1:Common.ThriftServiceException e);
    i32   delectApplyRepay(1:ApplyfileuploadBaseDTO applyfileuploadBaseDTO) throws (1:Common.ThriftServiceException e);
     i32   insertPreApplyRepay(1:PreApplyRepayBaseDTO preApplyRepayBaseDTO) throws (1:Common.ThriftServiceException e);
      RegAdvapplyRepayView    queryRegAdvapplyRepay(1:RegAdvapplyRepayDTO regAdvapplyRepayDTO) throws (1:Common.ThriftServiceException e);
     list<RegAdvapplyFileview>    queryRegAdvapplyFile(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws (1:Common.ThriftServiceException e);
    i32  queryRegAdvapplyFileCount(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws (1:Common.ThriftServiceException e);
	
    
      i32  delectFilebyId(1:string pId) throws (1:Common.ThriftServiceException e);
    //删除贷款项目
	 i32 deleteProjectbyId(1:string pId)throws (1:Common.ThriftServiceException e);
     //查询逾期列表
      list<RepayOverdueDTO>   getRepaymentOverdueList(1:RepayOverdueView repayOverdueView) throws (1:Common.ThriftServiceException e);
      //客户业务查询
	list<CusBusiness> getCusBusiness(1:CusBusiness cusBusiness)throws (1:Common.ThriftServiceException e);
	i32 getCusBusinessTotal(1:CusBusiness cusBusiness)throws (1:Common.ThriftServiceException e);
	ProjectComplete getLoanSettleOne(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	//结清办理
	i32 saveLoanSettle(1:ProjectComplete projectComplete)throws (1:Common.ThriftServiceException e);
	i32 updateLoanSettle(1:ProjectComplete projectComplete)throws (1:Common.ThriftServiceException e);
	//生成结清文件
	SettleFile getSettleFile(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	SettleFile getSettleFileRepayment(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	//结清文件上传
	i32 saveProjectCompleteFile(1:ProjectCompleteFile projectCompleteFile)throws (1:Common.ThriftServiceException e);
	list<ProjectCompleteFile> getProjectCompleteFile(1:i32 pid)throws (1:Common.ThriftServiceException e);
	bool deleteProjectCompleteFile(1:string pidArray)throws (1:Common.ThriftServiceException e);
	bool editProjectCompleteFile(1:ProjectCompleteFile projectCompleteFile)throws (1:Common.ThriftServiceException e);
	//贷款结清查询
	list<CusBusiness> getLoanSettle(1:CusBusiness cusBusiness)throws (1:Common.ThriftServiceException e);
	i32 getLoanSettleTotal(1:CusBusiness cusBusiness)throws (1:Common.ThriftServiceException e);

	//删除贷款结清列表
	i32 deleteLoanSettle(1:string pid)throws (1:Common.ThriftServiceException e);
    RegAdvapplyRepayView  getrepaydatilbyProcess(1:i32 preRepayId) throws (1:Common.ThriftServiceException e);
    RegAdvapplyRepayView     getrepayadvdatilbyProcess(1:i32 preRepayId) throws (1:Common.ThriftServiceException e);
    i32 updateadvApplyrepayment(1:PreApplyRepayBaseDTO preApplyRepayBaseDTO) throws (1:Common.ThriftServiceException e);
	
	list<RepaymentPlanBaseDTO> selectRepaymentPlanBaseDTO(1:RepaymentPlanBaseDTO dto) throws (1:Common.ThriftServiceException e);
	
	//还款催收内容
	list<RepaymentPlanBaseDTO> selectRepayReminders(1:RepaymentPlanBaseDTO dto) throws (1:Common.ThriftServiceException e);

	//改变提前还款的申请状态
      i32 changeReqstPro(1:i32 reqStatus,2:i32 preRepayId) throws (1:Common.ThriftServiceException e);
    //删除提前还款
    i32    deleteProjectbyPreRepayId(1:string preRepayId,2:string projectIds) throws (1:Common.ThriftServiceException e);
    //判断是否存在没处理的提前还款流程
     i32  checkpreRepayByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
 //查询展期总金额
      double  getExtensionamtbyprojectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
      
      // 获取贷款剩余本金
      double getOverplusAmt(1:i32 loanId) throws (1:Common.ThriftServiceException e);
      
      // 还款催收,获取已经对账的金额
      RepaymentPlanBaseDTO getReconciliationDtl(1:RepayEconciliationDTO dto) throws (1:Common.ThriftServiceException e);
}

 service RepayManageLoanService {
    //利率变更
	list<RepayCgInterestView> selectLoanInterestDetail(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
	//上穿文件
	i32 uploadinstCgapply(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws (1:Common.ThriftServiceException e);
	//	查询利率变更申请中的利息
	list<RepayCgInterestView> selectLoanRequestInterestDetail(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
	//	插入利率变更申请中的利息
	i32 insertRepayCgapplyInfo(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
	//利率变更更新利息
	i32 updateRepayCgapplyInfo(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
    //利率变更申请文件
	list<RegAdvapplyFileview> queryRepayCgapplyFile(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws (1:Common.ThriftServiceException e);
    //利率变更申请文件资料上传总数查询
	i32 queryRepayCgapplyFileCount(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO) throws (1:Common.ThriftServiceException e);
	
	
    //查询利率变更申请中的利息根据利率变更id
    list<RepayCgInterestView> selectLoanInterestDetailbyProces(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
    //通过利率id查出变更前的利率方便做展示
    list<RepayCgInterestView> queryLoanRes(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
   
    //查询利率变更申请中的利息根据利率变更id
    list<RepayCgInterestView> selectLoanRequestInterestDetailbyProces(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
	
	 //查询利率变更申请中的利息根据利率变更id并且是已归档的变更利率 
    list<RepayCgInterestView> selectLoanRequestInterestDetailbyProcesByStatus(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
	//利率变更任务流完成时候利息变更生效
    i32 intRepayCgapplyInfoEnd (1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
	//改变利率变更的申请状态
    i32 changeReqstCg(1:i32 reqStatus,2:i32 interestChgId) throws (1:Common.ThriftServiceException e);
    //删除利率变更
    i32  deleteProjectbyinterestChgId(1:string interestChgId,2:string projectId) throws (1:Common.ThriftServiceException e); 
    //判断是否存在没处理的利率变更流程
    i32  checkpreRepayByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
    /*利率变更申请书*/
    InterestChgApplyView  makeCgApplyFile(1:i32 interestChgId;) throws (1:Common.ThriftServiceException e);
    //通过项目ID得出利率变更ID
    InterestChgApplyView queryInterestChgId (1:i32 projectId)throws (1:Common.ThriftServiceException e);
    
    // 利率变更项目调查保存
    i32 saveProcedures(1:RepayCgInterestDTO repayCgInterestDTO) throws (1:Common.ThriftServiceException e);
    
    // 查询利率变更信息
    RepayCgInterestView getRepayCgInterestByPid(1:i32 pid)throws (1:Common.ThriftServiceException e);
    
    // 将利率变更是否可提前还款、是否退还利息、项目调查结论同步至项目表中
    i32 syncProcedureToProject(1:RepayCgInterestDTO repayCgInterestDTO)throws (1:Common.ThriftServiceException e);
}


//上传
 service UploadFileService {
  //抵制物压上穿文件
 i32 uploadassBaseFile(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);
       //查询抵制物压上穿文件
  list<RegAdvapplyFileview> queryAssBaseFile(1:i32 baseId)throws (1:Common.ThriftServiceException e);
   //坏账上穿文件
 i32 uploadBaddealFile(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);
       //查询坏账上穿文件
  list<RegAdvapplyFileview> queryBaddealFile(1:i32 badId)throws (1:Common.ThriftServiceException e);
    //更新提前还款上传信息
     i32 updateAdvLoadFileInfo(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);
       //更新提利息变更上传信息
      i32 updateACgLoadFileInfo(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);
  //更新提利息变更上传信息
      i32 updateFeedLoadFileInfo(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);


}
//费用减免
 service RepayFeewdtlService {
    //费用减免插入数据
    i32 insertLoanFeewdelInfo(1:string reason ,2:string  feewdel,3:i32  projectId,4:i32  loanId);
    //费用减免上穿文件
    i32 uploadinstFeewapply(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);
    //费用减免申请文件查询
  	list<RegAdvapplyFileview> queryRegFeewapplyFile(1:i32 repayId,2:i32 page,3:i32 rows)throws (1:Common.ThriftServiceException e);
     //费用减免申请文件查询总记录条数
    i32 queryRegFeewapplyFileTotal(1:i32 repayId)throws (1:Common.ThriftServiceException e);
    //初始化流程任务信息费用减免表信息
    list<RepayFeewdtlDatView>  queryRegFeewDealbyprocess(1:i32 repayId)throws (1:Common.ThriftServiceException e);
    //初始化流程任务信息贷款表信息
    string queryRegFeewReasonbyprocess(1:i32 repayId)throws (1:Common.ThriftServiceException e);
     //更新费用减免信息
    i32  updateRegFeewDealbyprocess(1:string reason ,2:string FeewInfo,3:i32 repayId)throws (1:Common.ThriftServiceException e);
    //查询费用减免项目信息更具项目id
    RepayFeewdtlDatView  queryRegFeewprojectinfobyproId (1:i32 projectId)throws (1:Common.ThriftServiceException e);
    //查询费用减免项目信息更具费用减免id
    RepayFeewdtlDatView queryRegFeewprojectinfobyrepayId (1:i32 repayId)throws (1:Common.ThriftServiceException e);
    
    //挪用上传文件
    i32 uploadinstiDvertapply(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);
    
    // 修改挪用文件描述信息
    i32 updateEmbezzleFile(1:UploadinstAdvapplyBaseDTO uploadinstAdvapplyBaseDTO)throws (1:Common.ThriftServiceException e);
    
    //查询挪用上穿文件
    list<RegAdvapplyFileview> queryRegDivertapplyFile(1:i32 DivertId)throws (1:Common.ThriftServiceException e);
    //改变挪用的申请状态
    i32 changeReqstFeewdel(1:i32 reqStatus,2:i32 repayId) throws (1:Common.ThriftServiceException e);
    i32 deleteProjectbyFeewDealList(1:list<i32> repayIds,2:list<i32> projectIds) throws (1:Common.ThriftServiceException e);
    i32 deleteProjectbyFeewDeal(1:i32 repayId,2:i32 projectId) throws (1:Common.ThriftServiceException e);
    i32 checkFeewDealByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
    i32 updateInsertRegFeewDealbyprocess(1:string reason ,2:string  feewdel,3:i32  repayId,4:i32  projectId ,5:i32 loanId) throws (1:Common.ThriftServiceException e);
	//费用减免申请书数据查询
	FeeWaiverApplicationDTO findFeeWaiverApplication(1:i32 userId,2:i32 repayId);
}
 
	
// 还款管理/展期申请查询条件dto	
struct LoanExtensionDTO{
	1:i32 projectId; // 项目ID
	2:string projectName; // 项目名称
	3:string projectNum; // 项目编号
	4:string cusName;	// 客户名称
	5:i32 cusType;		// 客户类别	
	6:string requestDttm;	// 贷款申请开始时间
	7:string requestDttmLast;	// 贷款申请结束时间
	8:string overdueStartDay; 	// 逾期期间开始日
	9:string overdueEndDay; 	// 逾期期间结束日
	10:string planRepayDt; // 到期期间开始时间
	11:string planRepayDtLast; // 到期期间结束时间
	12:string ecoTrade;	// 经济行业类别
	13:i32 page;
	14:i32 rows
	15:i32 pmUserId;
	16:list<i32> userIds;//数据权限
}	
	
// 还款管理/展期申请查询列表展示view	
struct LoanExtensionView{
	1:i32 projectId; // 项目ID
	2:string projectName; // 项目名称
	3:string projectNum; // 项目编号
	5:i32 cusType;		// 客户类别
	6:double creditAmt;	// 贷款金额
	7:string pmUserName;	// 项目经理名称
	8:string planOutLoanDt;	// 贷款开始日期
	9:string planRepayLoanDt;	// 贷款到期日期
	10:i32 page;
	11:i32 rows;
	12:string requestDttm;	//贷款申请时间
	13:i32 projectType;		//项目类型
}	
		
// 还款管理/展期申请查询条件dto	
struct LoanExtensionBaseDTO{
	1:i32 projectId; // 项目ID
	2:string projectName; // 项目名称
	3:string projectNum; // 项目编号
	4:string cusName;	// 客户名称
	5:i32 cusType;		// 客户类别
	6:string requestDttm;	// 贷款申请开始时间
	7:string requestDttmLast;	// 贷款申请结束时间
	8:i32 requestStatus;	// 展期处理状态
	9:string ecoTrade;	// 行业经济类别
	10:i32 page;
	11:i32 rows;
	12:i32 pmUserId;
	13:list<i32> userIds;//数据权限
}	
	
// 还款管理/展期申请列表展示view	
struct LoanExtensionBaseView{
	1:i32 projectId; // 项目ID
	2:string projectName; // 项目名称
	3:string projectNum; // 项目编号
	5:i32 cusType;		// 客户类别
	6:double creditAmt;	// 贷款金额
	7:string pmUserName;	// 项目经理名称
	8:string planOutLoanDt;	// 贷款开始日期
	9:string planRepayLoanDt;	// 贷款到期日期
	10:string requestStatus;	// 展期处理状态
	11:string requestDttm;		// 申请时间
	12:i32 pid;					// 展期ID
	13:i32 extensionProjectId;	// 被展期ID
	14:i32 page;
	15:i32 rows;
	16:string requestStatusVal;	// 申请单状态页面展示值
	
	17:double extensionFee;//展期费用
	18:string extensionDate;//展期日期
	19:i32 extensionDays;//展期天数
	20:i32 pmUserId;//客户经理
	21:double chargeAmount;//实际收费金额
}	

// 展期表view
struct ProjectExtensionView{
	1:i32 pid;					// pid
	2:i32 projectId;			// 项目id
	3:i32 selectCycleNum;	    // 展期期数
	4:double extensionAmt;		// 展期金额
	5:i32 status;				// 状态 
	6:i32 extensionProjectId;	// 被展期项目id
	7:double extensionFee;//展期费用
	8:string extensionDate;//展期日期
	9:double extensionRate;//展期费率
	10:i32 extensionDays;//展期天数
}
// 展期表DTO
struct ProjectExtensionDTO{
	1:i32 pid;					// pid
	2:i32 projectId;			// 项目id
	3:i32 selectCycleNum;	    // 展期期数
	4:double extensionAmt;		// 展期金额
	5:i32 status;				// 状态 
	6:i32 extensionProjectId;	// 被展期项目id
	7:double extensionFee;//展期费用
	8:string extensionDate;//展期日期
	9:i32 extensionDays;//展期天数
}

// 展期表
struct ProjectExtension{
	1:i32 pid;					// pid
	2:i32 projectId;			// 项目id
	3:i32 selectCycleNum;	    // 展期期数
	4:double extensionAmt;		// 展期金额
	5:i32 status;				// 状态 
	6:i32 extensionProjectId;	// 被展期项目id
	7:double extensionFee;//展期费用
	8:string extensionDate;//展期日期
	9:double extensionRate;//展期费率
	10:i32 extensionDays;//展期天数
}

// 展期未对账金额,期数
struct RepayNoReconciliationView{
	1:i32 extensionNum;				// 期数
	2:string extensionNumName;		    // 期数名称
	3:double noReconciliationAmt;	// 未对账金额
}
 	
// 展期未对账金额,期数
struct RepayNoReconciliationDTO{
	1:i32 planCycleNum;	// 期数
	2:i32 projectId;	// 项目ID
}	

// 展期文件列表
struct LoanExtensionFileView{
	1:string fileName;	//文件名称
	2:string fileType;	// 文件种类
	3:i32 fileSize;		// 文件大小
	4:string uploadDttm;	// 上传时间
	5:string fileDesc;		// 文件说明
	6:string fileUrl;		// 路径
	7:i32 fileId;			// 文件ID
	8:i32 pid;			// 展期文件表ID
	10:i32 fileCategory;	 // 展期类别
	11:i32 page;
	12:i32 rows;
	13:string fileCategoryName; // 展期类别名称
}

// 展期文件
struct LoanExtensionFileDTO{
	1:string fileName;	//文件名称
	2:string fileType;	// 文件类型
	3:i32 fileSize;		// 文件大小
	4:string uploadDttm;	// 上传时间
	5:string fileDesc;		// 文件说明
	6:string fileUrl;		// 路径
	7:i32 fileId;			// 文件ID
	8:i32 pid;			// 展期文件表ID
	9:i32 extensionId;		//展期ID
	10:string fileCategory;	 // 展期类别
	11:i32 page;
	12:i32 rows;
}

 /*展期*/ 
service LoanExtensionService {
	
	// 展期申请查询
	list<LoanExtensionView> selectLoanExtensionList(1:LoanExtensionDTO dto) throws (1:Common.ThriftServiceException e);
	
	i32 selectLoanExtensionListTotal(1:LoanExtensionDTO dto) throws (1:Common.ThriftServiceException e);
	
	// 展期申请列表查询
	list<LoanExtensionBaseView> selectLoanExtensionBaseList(1:LoanExtensionBaseDTO dto) throws (1:Common.ThriftServiceException e);
	
	i32 selectLoanExtensionBaseListTotal(1:LoanExtensionBaseDTO dto) throws (1:Common.ThriftServiceException e);
	
	// 展期表数据查询
	list<ProjectExtensionView> getProjectExtensionView(1:ProjectExtensionDTO dto) throws (1:Common.ThriftServiceException e);
	
	// 根据项目id 查询展期表数据
	ProjectExtensionView getByProjectId(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	
	// 根据被展期项目id 查询当前在流程中的展期表数据
	list<ProjectExtensionView> getByExtensionProjectId(1:i32 extensionProjectId) throws (1:Common.ThriftServiceException e);
	
	// 查询展期文件列表
	list<LoanExtensionFileView> getExtensionFileList(1:LoanExtensionFileDTO dto) throws (1:Common.ThriftServiceException e);
	
	i32 getExtensionFileListTotal(1:LoanExtensionFileDTO dto) throws (1:Common.ThriftServiceException e);
	
	// 展期期数,金额查询
	list<RepayNoReconciliationView> getRepayNoReconciliationList(1:RepayNoReconciliationDTO dto) throws (1:Common.ThriftServiceException e);

	// 保存上传文件
	i32 saveExtensionFile(1:LoanExtensionFileDTO dto,2:System.BizFile bizFile)throws (1:Common.ThriftServiceException e);
	
	// 更新上传文件
	i32 updateExtensionFile(1:LoanExtensionFileDTO dto)throws (1:Common.ThriftServiceException e);
	
	// 删除上传文件
	i32 batchDeleteFile(1:string pids )throws (1:Common.ThriftServiceException e);
	
	// 删除展期申请
	i32 batchDelete(1:string pids,2:string projectIds )throws (1:Common.ThriftServiceException e);
	
	// 获取展期目标历史项目ID
	string getHisProjectIds(1:i32 projectId )throws (1:Common.ThriftServiceException e);
	
	// 更新授信结束日期 
	i32 updateCreditEndDate(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	
	// 赎楼展期申请查询
	list<LoanExtensionView> selectForeLoanExtensionList(1:LoanExtensionDTO dto) throws (1:Common.ThriftServiceException e);
	
	i32 selectForeLoanExtensionListTotal(1:LoanExtensionDTO dto) throws (1:Common.ThriftServiceException e);
	
	// 赎楼展期申请列表查询
	list<LoanExtensionBaseView> selectForeLoanExtensionBaseList(1:LoanExtensionBaseDTO dto) throws (1:Common.ThriftServiceException e);
	
	i32 selectForeLoanExtensionBaseListTotal(1:LoanExtensionBaseDTO dto) throws (1:Common.ThriftServiceException e);
	i32 getCountForeExtensionByAcctId(1:i32 acctId);//根据客户ID查询当前客户是否存在正在进行的展期申请 
	i32 getForeExtensionByPid(1:i32 projectId);//根据项目ID查询正在办理中的展期次数
}
/*zhangyu end*/
//还款计划表详情列表展示
struct RepaymentDetailIndexDTO{
	1:i32 pid;	// 还款计划表ID
    2:string  planRepayDt;	// 还款日期
    3:i32	planCycleNum;	// 还款期数
    4:i32   thisStatus;				//本期还款状态
    5:i32	planType;		// 还款类型
    6:double   shouldPrincipal;			// 应还本金
    7:double    shouldInterest;		// 应还利息
    8:double    productInterest;//产品利息
    9:double rebateFee;//返佣利息
    10:double monthMaidRate;//月返佣费率
    11:double   total;					// 应还合计
    12:double actualPrincipal;//已收本金
	13:double actualInterest;//已收利息
	14:double actualTotal;//已收合计
    15:string actualRepayDt;//本息收取日（取最近一次收取时间）
    16:i32 overdueDays;//逾期天数
    17:double overdueMoney;//本期逾期金额
    18:double actualOverdueMoney;//实收逾期金额
	19:double shouldOverdueMoney;//应收逾期金额
    20:double actualPenalty;//实收罚息
    21:string actualOverdueDt;//逾期收取日
    22:double principalBalance;//应还剩余本金
    23:double shouldPrepaymentFee;//应收提前还款费
    24:double preRepayAmt;//已收剩余本金
    25:double fine;//已收提前还款费
    26:string repayDate;//提前还款日
    27:double fineRates;//提前还款罚金比例
    28:i32 paymentId;//实收表ID
	29:i32 overdueId;//逾期表ID
	30:double overdueRate;//逾期日罚息率
	31:i32 preRepayId;//提前还款ID
	32:i32 page;// 
	33:i32 rows;//
	34:i32 projectId;
	35:i32 overdueCycleNum;//开始逾期期数
	36:double shouldPenaltyTotal;//应收罚息总额
	37:double shouldPenalty;//应收罚息总额
	38:i32 repaymentType;//还款模式
	39:double repaymentMoney;//还款金额
	40:string remark;//备注
	41:string repaymentDate;//还款日期
	42:string accountNo;//还款账号
	43:string createrUser;//操作人
	44:i32 createrId;//操作人ID
}

