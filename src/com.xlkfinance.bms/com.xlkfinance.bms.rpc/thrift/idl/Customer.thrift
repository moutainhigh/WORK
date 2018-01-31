namespace java com.xlkfinance.bms.rpc.customer
include "System.thrift"
include "Common.thrift"


/*资产负债表导出*/
struct ExcelCusComBalanceSheet{
	
	1: string pid;
	2: string accountsCode;
	3: string accountsName;
	4: string lineNum;
	5: string showPosition;
	6: string status;
	7: string reportId;
	8: string beginVal;
	9: string endVal;
}

/*利润表导出*/
struct ExcelCusComIncomeReport{
	
	1: string pid;
	2: string reportId;
	3: string incomeItemId;
	4: string thisMonthVal;
	5: string thisYearVal;
	6: string status;
}


/*客户信息*/
struct CusAcct {
    1: i32 pid;
    2: i32 cusType;//  客户类别
    3: i32 cusStatus=1; 
    4: i32 status;// 状态
    5: i32 pmUserId;            
    6: i32 pids;            
    7: string userName;// 用户姓名 
    8: string acctName;// 客户名称
    9: string acctTypeText;// 客户类别文本
    10: i32 ecoTrade;// 经济行业类别
	11:i32 page; // 页码（第几页）
	12:i32 rows; // 每页显示数量
	13: string arrPids; // 需要排除的客户ID
	14: list<i32> userIds;
	15: list<CusPerson> customerList;
	16:i32 cusSource;//客户来源 
	17:i32 orgId;//关联机构id
}



/*个人客户信息*/
struct CusPerBase {
        1: i32 pid;
        2: CusAcct cusAcct;    
        3: string pictureUrl;
        4: string toLocalDate;
        5: i32 cusLevel;
        //6: i32 marrStatus;
        //7: string liveDate;
        //8: string socSecNumber;
        //9: string telephone;
        //10: string otherPhone;
        //11: string familyPhone;
        //12: string censusAddr;
        //13: string censusCode;
        //14: string commAddr;
        //15: string commCode;
        //16: string liveAddr;
        //17: string liveCode;
        //18: string communityName;
        6: string creditSituation;
        7: i32 status;
        8: string cusAcctManagerName;
        9: i32 page;
        10: i32 rows;
}

/*企业客户信息*/
struct CusComBase {
        1: i32 pid;
        2: CusAcct cusAcct;
        3: string cpyName;
        4: string cpyEngName;
        5: string cpyAbbrName;
        6: i32 cpyScale;
        7: i32 ecoTrade;
        8: i32 staffNum;
        9: i32 basHouStatus;
        10: i32 orgStatus;
        11: string busLicCert;
        12: string busLicCertUrl;
        13: string orgCode;
        14: string orgCodeCertUrl;
        15: string natCert;
        16: string landCert;
        17: string taxCertUrl;
        18: string creditCode;
        19: string exitCert;
        20: string regAddr
        21: string regCode
        22: string regPlan;
        23: double regMoney;
        24: i32 regCurrency;
        25: string mgeSiteType;
        26: double mgeSiteValue;
        27: double area;
        28: i32 rent;
        29: string foundDate;
        30: double shareRatio;
        31: string mgeCity;
        32: string actBizAdd;
        33: string actBizCode;
        34: string commAddr;
        35: string commCode; 
        36: string recPerson;
        37: string mail;
        38: i32 cusLevel;
        39: string cpyUrl;
        40: string cusIntro;
        41: string telephone;
        42: string fax;
        43: string loanNo;
        44: string loanCardUrl;
        45: i32 status;
        46: i32 comOwnId;
        47: i32 comAllNature;
        48: i32 unitNature;
        49: i32 cusStatus;
        50: string cusAcctManagerName;
        51: i32 page;
        52: i32 rows;
        53: i32 userId;
        54: string orgCodeCreits;
        55: string orgCodeCreitsUrl;
        56:i32 orgId;//机构管理--机构编号
        57:string comOwnName;//法人姓名
        58:string comOwnPhone;//法人电话
        59:string comOwnCard;//法人身份证
        60:list<CusComShare> cusComShares;//股东信息
}

struct ExportCusComBase {
        1: i32 pid;
        2: string cpyName;
        3: string regMoney;//注册资金
        4: string foundDate;//成立时间
        5: string telephone;//公司电话
        6: string busLicCert;//营业执照
        7: string orgCode;//组织结构代码
        8: string bankName;//基本户开户银行
        9: string loanCardId;//基本账户
        10: string ChinaName;//法人代表
        11: string mobilePhone;//法人手机号
        12: string certNumber;//身份证
        13: string liveAddr;//居住地址
        14: string regAddr;//注册地址
        15: string actBizAdd;//实际生产经营地址
        16: i32 personTotal;//职工总数
        17: double averageWage;//平均工资
        18: string ecotradeText;//经济行业
        19: double personMoneyTotal;//员工总工资
        20: string bankType;//账户类型
        21: string mgeCity;//经营所在地城市
}



/*人员信息*/
struct CusPerson {
        1: i32 pid;
        2: CusAcct cusAcct; 
        3: i32 relationType;
        4: string chinaName;
        5: string engName;
        6: i32 sex;
        7: string birthDate;
        8: string nation;
        9: string mail;
        10: string qq;
        11: string wechat;
        12: string graduSchool;
        13: string graduDt;
        14: i32 education;
        15: i32 degree;
        16: i32 polFace;
        17: string workUnit; 
        18: i32 unitNature;
        19: double regCapital;
        20: string mainBus;
        21: string unitPhone;
        22: string fax;
        23: string occupation;
        24: string workService;
        25: i32 occName;
        26: i32 servant;
        27: string deptment;
        28: i32 paySocSec;
        29: double monthIncome;
        30: i32 trade;
        31: string entryTime;
        32: i32 payWay;
        33: i32 monthPayDay;
        34: i32 staffNum;
        35: string unitAddr; 
        36: string unitCode;  
        37: i32 certType;  
        38: string certNumber;  
        39: string certUrl;
        40: i32 knowLoan;
        41: i32 status;
        42: string job;
        43: string relation;
        44: string mobilePhone;
        45: string telephone;
        46: i32 isComOwn;
        47: i32 boardMember;
        //48: i32 cusAcctManagerId;
        48: i32 pids;
        //50: string userName;
        49: i32 page;
        50: i32 rows;
        51: i32 marrStatus;
        52: i32 liveDate;
        53: string socSecNumber;
        54: string otherPhone
        55: string familyPhone;
        56: string censusAddr;
        57: string commAddr;
        58: string commCode;
        59: string liveAddr;
        60: string liveCode;
        61: string communityName;
        62: string censusCode;
        63: string relationTypeText;// 客户关系文本
        64: string realName;// 客户经理姓名
        65: string certTypeText;// 证件类型的文本
        66: string sexText;// 性别的文本
        67:  i32 age;//年龄
        68:list<i32> userIds;
        69:double proportionProperty;//产权占比
      	70: string liveProvinceCode;	// 居住地址省份
      	71: string liveCityCode;		// 居住地址城市
      	72: string liveDistrictCode;	// 居住地址区
      	73: string certAddr;	// 身份证发证机关所在地
      	74: i32 cusSource;//客户来源，用于查询
      	75: i32 orgId;//客户来源结构ID，用于查询
}


/*个人客户家庭信息*/
struct CusPerFamily {
        1: i32 pid;
        2: CusPerBase cusPerBase; 
        3: i32 houseMain;
        4: i32 childNum; 
        5: i32 liveStatus;
        6: i32 monthRent;
        7: i32 houseShape;
        8: double houseArea;
        9: i32 status;
        
}

/*个人客户-维护家庭经济情况*/
struct CusPerFamilyFinance {
        1: i32 pid;
        2: CusPerBase cusPerBase; 
        3: double totalAssets;
        4: double totalLiab;
        5: double familyAssets;
        6: double yearPay;
        7: double monthWage;
        8: double familyIncome;
        9: double familyControl;
        10: i32 status;
        11: string assetsDetail;//资产明细
        12: string liabDetail;//负债明细
        13: double monthlyPayment;//月供款（元）
        14: double overdraft;//信用卡透支金额（元）
}



/*个人客户征信记录*/
struct CusPerCredit {
        1: i32 pid;
        2: CusPerBase cusPerBase; 
        3: string creNo;
        4: string queryDate;
        5: string repQueryDate;
        6: i32 creAccNum;
        7: double creQuota;
        8: double loanSurp;
        9: i32 clearAccNum;
        10: i32 openAccNum;
        11: i32 otherAccNum;
		12: i32 status;
		13:double creditLimit;//银行信用卡授信额度
		14:double creditUsedLimit;//银行信用卡已用额度             
}

/*个人客户信用情况清单*/
struct CusPerCreditDef {
        1: i32 pid;
        2: CusPerCredit cusPerCredit; 
        3: i32 dayType;
        4: i32 dayVal;
        5: i32 dayIndex; 
        6: string dayNameDesc;  
        7: i32 status=1;                       
}

/*个人客户居住地址信息*/
struct CusPerCreditAddress {
        1: i32 pid;
        2: CusPerCredit cusPerCredit; 
        3: string province;
        4: string city;                            
        5: string county;                            
        6: string roadName;                            
        7: string roadNo;                            
        8: string cmtName;                            
        9: string zipCode;                            
        10: string liveDate;                            
        11: string addrType;   
        12: i32 status;                         
}



/*客户银行开户信息*/
struct CusAcctBank {
        1: i32 pid;
        2: CusAcct cusAcct; 
        3: string bankName;
        4: string branchName;
        5: string accArea; 
        6: string accName; 
        7: string loanCardId; 
        8: i32 accType;                            
        9: i32 bankAccCate; 
        10: i32 accUse;
        11: string useexplain;                            
        12: string remark;
        13: i32 status;
        14: i32 cusType;
		15: i32 page;
		16: i32 rows;
}

/*个人客户社保记录*/
struct CusPerSocSec {
        1: i32 pid;
        2: CusPerBase cusPerBase; 
        3: string safeUnit;
        4: string safeTime;                            
        5: double medMoney;                            
        6: double safeNum;                            
        7: double penMoney;                            
        8: i32 suspend;  
        9: i32 status;                          
        10: i32 totalSafeTime;//TOTAL_SAFE_TIME,总参保时间（月）                          
}

/*客户评估模板信息*/
struct CusEstTemplate {
        1: i32 pid;
        2: i32 modelType; 
        3: string modelName;
        4: string remark;                            
        5: string  creTime; 
        6: i32 status; 
        7: list<CusEstFactorWeights> factors;                           
                                   
}

/*客户资信评估要素权重*/
struct CusEstFactorWeights {
        1: i32 pid;
        2: CusEstTemplate cusEstTemplate; 
        3: i32 factorName;
        4: double weight;
        5: string remark;
        6: i32 status;
        7: list<CusEstQuota> quotas;
        8: string fname;
                                   
}

/*客户评估指标信息*/
struct CusEstQuota {
        1: i32 pid;
        2: CusEstFactorWeights cusEstFactorWeights;
        3: string quotaName;
        4: string remark;
        5: i32 status;                            
        6: list<CusEstOption>  options;
        7: double totalScore;
                                   
}


/*客户评估指标DTO*/
struct CusEstDTO {
        1: CusEstFactorWeights cusEstFactorWeights;
        2: CusEstQuota cusEstQuota;
        3: list<CusEstQuota> quotas;
        4: CusEstOption cusEstOption;                            
        5: list<CusEstOption>  options;
        6: CusEstTemplate  cusEstTemplate;
                                   
}


/*客户评估指标选项信息*/
struct CusEstOption {
        1: i32 pid;
        2: CusEstQuota cusEstQuota; 
        3: string optionName;
        4: double score;                            
        5: string  remark;  
        6: i32 status;                          
                                   
}

/*客户评估记录*/
struct CusEstInfo {
        1: i32 pid;
        2: CusEstTemplate cusEstTemplate; 
        3: CusAcct cusAcct; 
        4: i32 cusType;                            
        5: i32 estPerson;                            
        6: list<CusEstValue> cusEstValues;
        7: i32 status;                            
        8: double score;                            
        9: string estDate;                            
                                   
}

/*客户评估值*/
struct CusEstValue {
        1: i32 pid;
        2: CusEstInfo cusEstInfo; 
        3: CusEstOption cusEstOption;
        4: i32 status;
                                 
                                   
}


/*企业客户员工结构信息*/
struct CusComStaff {
	
	1:i32 pid;
	2: CusComBase cusComBase;
	3: string catelog;
	4: string teachType;
	5: i32 personNum;
	6: double ratio;
	7: i32 status;
	8: i32 sortNum;

}
/*企业客户员工结构信息DTO*/
struct CusComStaffDTO {
	1:list<CusComStaff> staffs;
}

/*企业现金流量表*/
struct CashFlowReport {
	
	1:i32 pid;
	2: i32 reportId;//企业财务报表ID
	3: i32 cashFlowItemId;//现金流量项目表ID
	4: double thisMonthVal;
	5: double thisYearVal;
	6: i32 status;
	7: i32 sortNum;
	8: i32 isMain;

}
/*企业财务报表*/
struct CusComFinanceReport{

	1:i32 pid;
	2: i32 comId;//企业ID
	3: i32 accountingYear;//会计年度
	4: i32 accountingMonth;//会计期间-月份
	5: string reportType;//报表类型(M:月,Y:年)
	6: string reportDttm;
	7: i32 reportUserId;
	8: string reportName;//报表名称
	9: i32 status;

}

/*企业客户财务状况查询/录入的DTO*/
struct CusComFinanceReportOverviewDTO{
	1:i32 pid;
	2:string reportPeriod;
	3:string reportName;
}



/*现金流量项目表*/
struct CashFlowItem {

	1:i32 pid;
	2: i32 lineNum;
	3: string itemName;//项目名称
	4: string itemPreFix;
	5: i32 isInput;
	6: i32 isCacl;
	7: i32 isSubtraction;
	8: i32 status;

}
/*财务模块,现金流量表的DTO*/
struct FinacialDTO1{
	
	1:list<CashFlowReport> cashFlowReport;//现金流量表 
	2:list<CashFlowItem> cashFlowItem;//现金流量项目表
	3: i32 comId;
	4: i32 lineNum;
	5: double thisMonthVal;
	6: double thisYearVal;
	7:i32 pid;
	8: string itemName;
	9:i32 cusComCashFlowId;
	10:i32 isMain;
}

struct FinacialDTO{
	
	1:list<CashFlowReport> cashFlowReport;//现金流量表 
	2:list<CashFlowItem> cashFlowItem;//现金流量项目表
	3: i32 comId;
	4: i32 lineNum;
	5:i32 pid;
	6: string itemName;
	7:i32 cusComCashFlowId;
	8:i32 isMain;
}


/*企业客户企业联系人信息*/
struct CusComContact {
	
	1:i32 pid;
	2: CusComBase cusComBase;
	3: string cttName;
	4: i32 mainCtt;
	5: string duty;
	6: string department;
	7: string remark;
	8: string movePhone;
	9: string fixedPhone;
	10: string familyAddr;
	11: string comPhone;
	12: i32 status;
	13: i32 page;
	14: i32 rows;

}

/*企业客户管理团队信息*/
struct CusComTeam {
	
	1: i32 pid;
	2: CusComBase cusComBase;
	4: string name;
	5: i32 sex;
	6: i32 certType;
	7: string certNo;
	8: i32 age;
	9: i32 education;
	10: string graSchool;
	11: string workUnit;
	12: string duty;
	13: i32 dutyYear;
	14: i32 boardMember;
	15: i32 workYear;
	16: i32 skillOcc;
	17: string tradeSuffer;
	18: string manSuffer;
	19: string record;
	20: string remark;
	21: string fixedPhone;
	22: string fax;
	3: string telephone;
	23: i32 status;
	24: i32 page;
	25: i32 rows;

}


/*企业客户债务、债权信息*/
struct CusComDebt {
	
	1: i32 pid;
	2: CusComBase cusComBase;
	4: string debtType;
	5: string debtPerson;
	6: double loanMoney;
	7: string debtExpl;
	8: string loanStartDate;
	9: string loadEndDate;
	10: double repay;
	11: double loanSurplu;
	12: string lastRepayDate;
	13: i32 repayWay;
	14: double assPayMoney;
	15: double monthPay;
	16: string warWay;
	3: string remark;
	17: i32 status;
	18: i32 vouchercWay;
	19: i32 page;
	20: i32 rows;
	21: i32 guaranteePid;

}


/*企业客户对外担保信息*/
struct CusComAssure {
	
	1: i32 pid;
	2: CusComBase cusComBase;
	4: string assObj;
	5: i32 assWay;
	6: string assContent;
	7: double assMoney;
	8: string assStartDate;
	9: i32 assDeadline;
	10: string assEndDate;
	11: double dutyRatio;
	12: string assRelCon;
	13: double dutyBalance;
	14: double assPayMoney;
	15: string assOptn;
	3: string remark;
	16: i32 status;
	17: string assWayVal;
	18: i32 page;
	19: i32 rows;

}

/*企业客户对外投资信息*/
struct CusComInvest {
	
	1: i32 pid;
	2: CusComBase cusComBase;
	3: string remark;
	4: string invObj;
	5: double invMoney;
	6: i32 invWay;
	7: double preEarn;
	8: string invStartDate;
	9: string invEndDate;
	10: double realEarn;
	11: double priorPreEarn;
	12: double priorRealEarn;
	13: double payInvest;
	14: double invEarn;
	15: i32 status;
	16: i32 page;
	17: i32 rows;

}

/*企业客户获奖信息*/
struct CusComReward {
	
	1: i32 pid;
	2: CusComBase cusComBase;
	4: string certName;
	5: string issEvent;
	6: string certNo;
	7: string issDate;
	8: string rewPerson;
	9: string issOrgan;
	3: string remark;
	10: i32 status;
	11: i32 page;
	12: i32 rows;

}

/*企业客户股东信息*/
struct CusComShare {
	
	1: i32 pid;
	2: CusComBase cusComBase;
	4: i32 shareType;
	5: string shareName;
	6: double invMoney;
	7: i32 invWay;
	8: double shareRatio;
	3: string remark;
	9: i32 status;
	10: i32 cusComId;
	11:string shareTypeName;
	12:string invWayName;

}

/*潜在客户*/
struct CusAcctPotential {
	
	1: i32 pid;
	2: string acctName;
	3: i32 acctType;
	4: string phone;
	5: i32 certType;
	6: string certNumber;
	7: i32 acctLevel;
	8: string remark;
	9: i32 status;
	10: i32 page;
  	11: i32 rows; 
  	12: i32 createUserId; 
	13: list<i32> userIds;
}

/*黑名单/拒贷客户*/
struct CusBlacklistRefuse {
	
	1: i32 pid;
	2: CusAcct cusAcct;
	3: string listType; //黑名单=1，拒贷=2
	4: string deadline;
	5: string refuseReason;
	6: i32 status;
	7: string revokeReason;
	8: string revokeDttm;

}

/*个人客户基础信息DTO*/
struct CusPerBaseDTO{

	1:CusPerBase cusPerBase;
	2:CusPerson cusPerson;
	3:CusPerSocSec cusPerSocSec;
	4:CusAcct cusAcct;
	5:string cusName;
	6: i32 sexId;
	7: i32 certTypeId;
	8: string certNumber;
	9: i32 cusType;
	10: i32 cusStatus;
	11: i32 page;
	12: i32 rows;
	13: i32 userId;
	14:list<i32> userIds;//权限列表
	15: CusRelation cusRelation;//客户关系表
	16:CusPerson spousePerson; // 配偶信息
}

/*个人客户家庭信息DTO*/
struct CusPerFamilyDTO{
	
	1:CusPerFamily cusPerFamily;
	2:CusPerFamilyFinance cusPerFamilyFinance;
	3:CusPerson cusPerson;

}

/*个人客户征信信息DTO*/
struct CusPerCreditDTO{
	
	1:CusPerCredit cusPerCredit;
	2:list<CusPerCreditAddress> creditAddrs;
	3:list<CusPerCreditDef> deficits; //透支
	4:list<CusPerCreditDef> settles;  //结清

}





/*企业客户基础信息DTO*/
struct CusComBaseDTO{
	
	1:CusComBase cusComBase;
	2:list<CusComShare> cusComShares;
	3:CusAcct cusAcct;
	4:i32 acctId;
	5:CusPerCom cusPerCom;//关联旗下公司用的

}

/*客户and个人信息DTO*/
struct CusDTO{
	1:i32 pid;// 主键
	2:i32 acctId;// 客户ID
	3:string cpyName; // 企业名称
	4:string cpyAbbrName;// 企业简称
	5:string busLicCert;//营业执照号码 
	6:string orgCode;// 组织结构代码
	7:string cusTelephone;// 企业联系电话
	8:string commAddr;// 通讯地址
	9:string chinaName;// 姓名
	10:string sexName;// 性别
	11:string certTypeName;// 证件类型文本
	12:string certNumber;// 证件号码
	13:string perTelephone;// 手机号码
	14:string cusMail;// 企业电子邮箱
	15:double cusRegMoney;// 企业工商注册资金
	16:string cusFoundDate;// 企业成立日期
	17:i32 cusStatus;// 企业状态
	18:string realName;// 客户经理姓名
	19:string curType;// 客户类别
	20:i32 ecoTrade;// 经济行业类别
	21:string marrName;// 婚姻状况
	22:string commCode;// 邮政编码
	23:i32 certType;// 证件类型value
	24:i32 sex;//  性别value
	25:string workUnit;// 工作单位
	26:i32 perStatus;// 个人状态
	27:string remark;// 备注
	28:string cusStatusVal;//客户状态value
	29:string mail;// 邮箱
	30:i32 relationVal;// 与客户关系val 
	31:string relationText;// 与客户关系文本
	32:string workService;// 职务
	33:double monthIncome;// 月收入
	34:string comAllNatureText;// 所有性质文本
	35:i32 page; // 页码（第几页）
	36:i32 rows; // 每页显示数量
	37:i32 userId; // 每页显示数量
	38:i32 projectPublicManId;// 共同借款人ID
	39:list<i32> userIds;
	40:double proportionProperty;//产权占比
	41:string liveAddr;// 居住地址
	42:string censusAddr;// 户籍地址 
	43: string certAddr;	// 身份证发证机关所在地
}

/*pengchuntao add ,企业财务报表-利润表项目表,实体 ,CUS_COM_INCOME_REPORT_META*/
struct CusComIncomeReportMeta{
	
	1: i32 pid;
	2: i32 lineNum;
	3: string itemName;
	4: string itemPrefix;
	5: i32 isSubtraction;
	6: i32 status;
}

/*资信评估DTO*/
struct CusEstInfoDTO{
	
	1:string cusName
	2:i32 cusType;
	3:i32 estPerson;
	4:i32 templateId;
	5:string estLv;
	6:i32 estStartScore;
	7:i32 estEndScore;
	8:i32 startScore;
	9:i32 endScore;
	10: i32 cusId;
	11: i32 page;
	12: i32 rows;
	13:list<i32> userIds;
}

/*pengchuntao add ,企业财务报表-利润表,实体 ,CUS_COM_INCOME_REPORT*/
struct CusComIncomeReport{
	
	1: i32 pid;
	2: i32 reportId;
	3: i32 incomeItemId;
	4: double thisMonthVal;
	5: double thisYearVal;
	6: i32 status;
}



/*pengchuntao add ,企业财务报表-利润表,数据2个来源，从前端传递数据到后台的DTO*/
struct ProfitDTO{
	
	1: i32 pid;//利润表项目表CUS_COM_INCOME_REPORT_META的PID
	2: i32 comId;
	3: i32 lineNum;
	4: double thisMonthVal;
	5: double thisYearVal;
}

/*pengchuntao add ,资产负债表报表会计科目表，实体类，CUS_COM_BALANCE_SHEET_META*/
struct CusComBalanceSheetMeta{
	
	1: i32 pid;
	2: i32 accountsCode;
	3: string accountsName;
	4: i32 lineNum;
	5: i32 isSubtraction;
	6: i32 isInput;
	7: i32 isCacl;
	8: i32 status;
	9: i32 bsReportId;
}


/*pengchuntao add ,企业财务报表-资产负债表，实体类，CUS_COM_BALANCE_SHEET*/
struct CusComBalanceSheet{
	
	1: i32 pid;
	2: i32 reportId;
	3: i32 accountsId;
	4: double beginVal;
	5: double endVal;
	6: i32 status;
}

/*pengchuntao add ,负债表新增时DTO*/
struct BalanceSheetDTO{
	
	1: i32 pid; //会计科目表
	2: double beginVal;
	3: double endVal;
}

/**编辑财务-负债报表时候使用*/
struct BalanceSheetEditDTO{
	
	1: i32 bsPid;//资产负债表,CUS_COM_BALANCE_SHEET的PID
	2: string accountsName;
	3: double beginVal;
	4: double endVal;
	5: i32 bsReportId;
}

/**编辑财务-利润报表时候使用*/
struct CusComIncomeReportEditDTO{
	
	1: i32 irPid;//利润报表,CUS_COM_INCOME_REPORT的PID
	2: string itemName;
	3: string itemPrefix;
	4: double thisMonthVal;
	5: double thisYearVal;
	6: i32 irReportId;
}

/**编辑财务-现金流量报表和现金流量补充材料时候使用*/
struct CashFlowReportEditDTO{
	
	1: i32 cfPid;//现金流量表,CUS_COM_CASH_FLOW的PID
	2: i32 lineNum;
	3: string itemName;//项目名称
	4: double thisMonthVal;
	5: double thisYearVal;
	6: i32 cfReportId;
}

/**pengchuntao ,财务信息，填写企业财务状况 ，首页面*/
struct FinancialPositionDTO{
	1: string indexCategory;
	2: string indexName;
	3: string currentAnnualReport;
	4: string lastYearReport;
	5: string lastTwoYearsReport;
	6: string lastThreeYearsReport;
}

/*计算资产负债表*/
struct CusComBalanceSheetCalculateDTO{
	
	1: i32 lineNum;
	2: string accountsName;
	3:double beginVal;
	4: double endVal;
}

/*计算利润表*/
struct CusComIncomeReportCalculateDTO{
	
	1: i32 lineNum;
	2: string itemName;
	3: double thisMonthVal;            
	4: double thisYearVal;
}

/*计算现金流量表*/
struct CusComCashFlowReportCalculateDTO{
	
	1: i32 lineNum;
	2: string itemName;
	3: double thisMonthVal;   
	4: double thisYearVal;
}
/*关联旗下公司*/
struct CusPerCom{
	
	1: i32 pid;
	2: i32 cusPerId;
	3: i32 comId;   
	4: i32 status;
}
/*查询条件DTO*/
struct QueryPersonDTO{
	1: i32 acctId;
	2: list<Common.GridViewDTO> results;
	3: i32 page;
	4: i32 rows;
	5: string cusName;
	6: string certNumber;
	7: i32 acctType;
	8: i32 businessVariet;
	9: i32 currLoginId;
	10: string isComplete;//是否结清
	
}

/*返回结果银行账户信息*/
struct CusAcctBankView {
        1: i32 pid;
        2: string bankName;
        3: string branchName;
        4: string accArea; 
        5: string accName; 
        6: string bankAccCate; 
        7: string accType;                            
        8: string loanCardId;         
        9: string accUse;
        10: string useexplain;                            
        11: string remark;
}
/*业务往来查询条件DTO*/
struct BusinessQuery{
	1: string acctName;
	2: string certNumber;
	3: i32 acctType;
	4: i32 businessVariet;
	5: i32 page;
	6: i32 rows;
} 
/*债务债权的担保方式*/
struct CusComGuaranteeType{
	1: i32 pid;
	2: i32 comAssureId;
	3: i32 guaranteeType;
	4: i32 status;
	
}

/*客户账户信息服务*/
service CusAcctService {
	CusAcct getAcctById(1:i32 id)throws (1:Common.ThriftServiceException e);
	// 查询所有有效的客户
	list<CusAcct> getAllAcct(1:CusAcct cusAcct)throws (1:Common.ThriftServiceException e);
	// 查询所有有效的客户的总数
	i32 getAllAcctCount(1:CusAcct cusAcct);
	//根据客户ID查询客户名称
	string getAcctNameById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	i32 deleteCusAcct(1:string pids)throws (1:Common.ThriftServiceException e);
	i32 deleteCusPerson(1:string pids)throws (1:Common.ThriftServiceException e);

	i32 addCusAcctBank(1:CusAcctBank cusAcctBank)throws (1:Common.ThriftServiceException e);
	i32 updateCusAcctBank(1:CusAcctBank cusAcctBank)throws (1:Common.ThriftServiceException e);
	i32 updateCusAcctBanks(1:CusAcctBank cusAcctBank)throws (1:Common.ThriftServiceException e);
	i32 deleteCusAcctBank(1:string pids)throws (1:Common.ThriftServiceException e);
	//查询客户的银行账户
	list<Common.GridViewDTO> getCusAcctBanks(1:CusAcctBank cusAcctBank)throws (1:Common.ThriftServiceException e);
	CusAcctBank getCusAcctBank(1:i32 pid);
	
	i32 addCusPerson(1:CusPerson cusPerson)throws (1:Common.ThriftServiceException e);
	i32 updateCusPerson(1:CusPerson cusPerson)throws (1:Common.ThriftServiceException e);
	//更新客户信息-供资金机构使用
	i32 updateFromPartnerById(1:CusPerson cusPerson)throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getCusPersons(1:CusPerson cusPerson);
	CusPerson getCusPerson(1:i32 pid);
	
	//增加资信评估模板
	i32 addCusEstTemplate(1:CusEstTemplate cusEstTemplate)throws (1:Common.ThriftServiceException e);
	i32 updateCusEstTemplate(1:CusEstTemplate cusEstTemplate)throws (1:Common.ThriftServiceException e);
	i32 deleteCusEstTemplate(1:string pids)throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getCusEstTemplates(1:CusEstTemplate cusEstTemplate)throws (1:Common.ThriftServiceException e);
	//获取资信评估模板
	CusEstTemplate getCusEstTemplate(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//资信评估模板制作
	CusEstTemplate getMakeCusEstTemplate(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//查询所有资信评估模板名称
	list<CusEstTemplate> selectAllEstTemplateName(1: i32 modelType) throws (1:Common.ThriftServiceException e);
	
	i32 addCusEstQuota(1:CusEstFactorWeights cusEstFactorWeights)throws (1:Common.ThriftServiceException e);
	i32 updateCusEstQuota(1:CusEstFactorWeights cusEstFactorWeights)throws (1:Common.ThriftServiceException e);
	CusEstDTO getCusEstQuotas(1:i32 factorId)throws (1:Common.ThriftServiceException e);
	
	i32 addCusEstOption(1:CusEstQuota cusEstQuota)throws (1:Common.ThriftServiceException e);
	i32 updateCusEstOption(1:CusEstQuota cusEstQuota)throws (1:Common.ThriftServiceException e);
	CusEstDTO getCusEstOptions(1:i32 quotaId)throws (1:Common.ThriftServiceException e);
	//查询资信评估模板
	list<Common.GridViewDTO> getCusEstInfos(1:CusEstInfoDTO cusEstInfoDTO)throws (1:Common.ThriftServiceException e);
	CusEstInfo getCusEstInfo(1:i32 pid)throws (1:Common.ThriftServiceException e);
	i32 addCusEstInfo(1:CusEstInfo cusEstInfo)throws (1:Common.ThriftServiceException e);
	i32 updateCusEstInfo(1:CusEstInfo cusEstInfo)throws (1:Common.ThriftServiceException e);
	i32 deleteCusEstInfos(1:string pids)throws (1:Common.ThriftServiceException e);
	
	
	i32 addCusBlacklistRefuse(1:CusBlacklistRefuse cusBlacklistRefuse,2:string acctIds)throws (1:Common.ThriftServiceException e);
	i32 updateCusBlacklistRefuse(1:CusBlacklistRefuse cusBlacklistRefuse,2:CusPerBase cusPerBase,3:string acctIds,4:string pids)throws (1:Common.ThriftServiceException e);
	//分页查询黑名单信息
	list<Common.GridViewDTO> getCusBlacklists(1:CusPerBaseDTO cusPerBaseDTO);
	list<Common.GridViewDTO> getCusRefuses(1:CusPerBaseDTO cusPerBaseDTO);
	
	i32 addCusAcctPotential(1:CusAcctPotential cusAcctPotential)throws (1:Common.ThriftServiceException e);
	i32 updateCusAcctPotential(1:CusAcctPotential cusAcctPotential)throws (1:Common.ThriftServiceException e);
	i32 deleteCusAcctPotential(1:string pids)throws (1:Common.ThriftServiceException e);
	//查询潜在客户列表
	list<Common.GridViewDTO> getCusAcctPotentials(1:CusAcctPotential cusAcctPotential);
	CusAcctPotential getCusAcctPotential(1:i32 pid);
	//分页用的
	i32 getTotal(1:CusAcctBank cusAcctBank)  throws (1:Common.ThriftServiceException e);
	i32 getTotalEst(1:CusEstInfoDTO cusEstInfoDTO)  throws (1:Common.ThriftServiceException e);
	i32 getTotalCusPersons(1:CusPerson cusPerson)  throws (1:Common.ThriftServiceException e);
	i32 getTotalPotential(1:CusAcctPotential cusAcctPotential)  throws (1:Common.ThriftServiceException e);
	//根据客户ID查银行账户
	CusAcctBankView getCusBankByAcctId(1:CusAcctBank cusAcctBank)throws (1:Common.ThriftServiceException e);
	//i32 addBlacklistRefuse(1:CusBlacklistRefuse cusBlacklistRefuse) throws (1:Common.ThriftServiceException e);
	//批量保存关系人
	string saveSpouseList(1:CusAcct cusAcct) throws (1:Common.ThriftServiceException e);
	//查询黑名单总数
	i32 getBlackListCount(1:CusPerBaseDTO cusPerBaseDTO);
	//根据证件号码判断是否为黑名单
	i32 getBlackByCertNum(1:string certNumber);
}

/*个人客户服务*/
service CusPerService {
	i32 addCusPerBase(1:CusPerBaseDTO cusPerBaseDTO)throws (1:Common.ThriftServiceException e);
	i32 updateCusPerBase(1:CusPerBaseDTO cusPerBaseDTO)throws (1:Common.ThriftServiceException e);
	//查询个人基础信息
	list<Common.GridViewDTO> getCusPerBases(1:CusPerBaseDTO cusPerBaseDTO)
	//查询Aom个人基础信息
	list<Common.GridViewDTO> getAomCusPerBases(1:CusPerBaseDTO cusPerBaseDTO)
	//查询Aom个人关系人信息
	list<CusPerson> getRelationCusPerson(1:CusPerson cusPerson);
	//查询Aom个人信息
	list<CusPerson> checkCusExist(1:map<string,string> myMap);
	//查总记录数(Aom)，分页用的
	i32 getAomTotal(1:CusPerBaseDTO cusPerBaseDTO)  throws (1:Common.ThriftServiceException e);
	CusPerBaseDTO getCusPerBase(1:i32 pid);
	//查询客户经理
	i32 selectIsAcctManager(1:i32 currUserPid) throws (1:Common.ThriftServiceException e);
	//获取客户名称
	list<CusAcct> getUserName(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	list<CusAcct> getUserNames(1:i32 cusAcctManagerId) throws (1:Common.ThriftServiceException e);
	// 条件查询所有人员信息
	list<CusPerson> getAllCusPerson(1:CusPerson cusPerson)throws (1:Common.ThriftServiceException e);
	// 条件查询所有人员总数
	i32 getAllCusPersonCount(1:CusPerson cusPerson)throws (1:Common.ThriftServiceException e);
	
	i32 addCusPerFamily(1:CusPerFamilyDTO cusPerFamilyDTO)throws (1:Common.ThriftServiceException e);
	i32 updateCusPerFamily(1:CusPerFamilyDTO cusPerFamilyDTO)throws (1:Common.ThriftServiceException e);
	CusPerFamilyDTO getCusPerFamily(1:i32 perId,2:i32 acctId);
	
	i32 addCusPerCredit(1:CusPerCreditDTO cusPerCreditDTO)throws (1:Common.ThriftServiceException e);
	i32 updateCusPerCredit(1:CusPerCreditDTO cusPerCreditDTO)throws (1:Common.ThriftServiceException e);
	i32 deleteCusPerCredit(1:string pids) throws (1:Common.ThriftServiceException e);
	//获取个人征信记录
	list<Common.GridViewDTO> getCusPerCredits(1:CusPerBase cusPerBase)throws (1:Common.ThriftServiceException e);
	CusPerCreditDTO getCusPerCredit(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//获取旗下子公司
	list<Common.GridViewDTO> getCusUnderCom(1: CusPerBase cusPerBase)throws (1:Common.ThriftServiceException e);
	//根据客户Id获取个人客户Id
	i32 selectPerIdByAcctId(1:i32 acctId)throws (1:Common.ThriftServiceException e);
	
	// 查询个人信息
	list<CusDTO> getPersonalList(1:CusDTO cusDto);
	// 贷前申请-查询个人客户信息
	list<CusDTO> getPersonalListTwo(1:CusDTO cusDto);
	// 贷前申请-查询个人客户信息总数
	i32 getPersonalListTwoCount(1:CusDTO cusDto);
	// 查询个人非配偶关系人
	list<CusDTO> getNoSpouseList(1:i32 acctId);
	// 根据pid查询个人信息
	CusDTO getPersonalListKeyPid(1:i32 pid);
	// 根据客户ID查询个人信息
	CusDTO getPersonalListByAcctId(1:i32 acctId);
	// 查询是个人客户 还是企业客户
	i32 getByAcctTypeKeyPid(1:i32 pid);
	
	list<Common.GridViewDTO> getPerBusiness(1:QueryPersonDTO queryPersonDTO) throws (1:Common.ThriftServiceException e);
	i32 selectCredit(1:i32 projectId)  throws (1:Common.ThriftServiceException e);
	//查总记录数，分页用的
	i32 getTotal(1:CusPerBaseDTO cusPerBaseDTO)  throws (1:Common.ThriftServiceException e);
	//i32 getTotals(1:QueryPersonDTO queryPersonDTO)  throws (1:Common.ThriftServiceException e);
	i32 getTotalUnderCom(1:CusPerBase cusPerBase)  throws (1:Common.ThriftServiceException e);
	i32 getTotalCusPerBase(1:CusPerBase cusPerBase)  throws (1:Common.ThriftServiceException e);
	//查询婚姻状态
	string selectMarrStatus(1:i32 acctId);
	//导出
	list<Common.GridViewDTO> searchPerExportByPid(1:string pids) throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> listUnderExcelExportList(1:string pids) throws (1:Common.ThriftServiceException e);
	// 查询个人非配偶关系人
	list<CusDTO> getNoSpouseLists(1:i32 projectId);
	i32 getPersonalListCount(1:CusDTO cusDto)  throws (1:Common.ThriftServiceException e);
	//查寻借贷客户项目是否删除
	i32 selectProjectStatus(1:i32 acctID) throws (1:Common.ThriftServiceException e);
	//根据pid批量查询关系人信息
	list<CusDTO> getNoSpouseListByPid(1:string pids)throws (1:Common.ThriftServiceException e);
	list<CusPerson> getCusPersonByNumber(1:CusPerson cusPerson)
}


/*企业客户服务*/
service CusComService {

	map<string,i32> addCusComBase(1:CusComBaseDTO cusComBaseDTO)throws (1:Common.ThriftServiceException e);
	map<string,i32> updateCusComBase(1:CusComBaseDTO cusComBaseDTO)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComBase(1:string pids)throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getCusComBases(1:CusComBase cusComBase);
	CusComBaseDTO getCusComBase(1:i32 pid);
	CusDTO selectByPidPrimaryKey(1:i32 pid);
	// 根据客户ID查询企业信息
	CusDTO getCusComByAcctId(1:i32 acctId);
	i32 addCusComStaff(1:list<CusComStaff> cusComStaffs)throws (1:Common.ThriftServiceException e);
	i32 addCusComStaffs(1:CusComStaffDTO cusComStaffDTO)throws (1:Common.ThriftServiceException e);
	i32 updateCusComStaff(1:list<CusComStaff> cusComStaffs)throws (1:Common.ThriftServiceException e);
	i32 updateCusComStaffs(1:CusComStaffDTO cusComStaffDTO)throws (1:Common.ThriftServiceException e);
	//查询员工结构
	list<CusComStaff> getCusComStaff(1:i32 comId)throws (1:Common.ThriftServiceException e);
	
	i32 addCusComContact(1:CusComContact cusComContact)throws (1:Common.ThriftServiceException e);
	i32 updateCusComContact(1:CusComContact cusComContact)throws (1:Common.ThriftServiceException e);
	i32 updateCusComContacts(1:CusComContact cusComContact)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComContact(1:string pids)throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getCusComContacts(1:CusComContact comContact);
	CusComContact getCusComContact(1:i32 pid);
	i32 selectCusComBaseById(1:i32 acctId)throws (1:Common.ThriftServiceException e);
	 
	
	i32 addCusComTeam(1:CusComTeam cusComTeam)throws (1:Common.ThriftServiceException e);
	i32 updateCusComTeam(1:CusComTeam cusComTeam)throws (1:Common.ThriftServiceException e);
	CusComTeam updateCusComTeams(1:string pid)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComTeam(1:string pids)throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getCusComTeams(1:CusComTeam cusComTeam);
	CusComTeam getCusComTeam(1:i32 pid);
	
	i32 addCusComDebt(1:CusComDebt cusComDebt)throws (1:Common.ThriftServiceException e);
	i32 updateCusComDebt(1:CusComDebt cusComDebt)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComDebt(1:string pids)throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getCusComDebts(1:CusComDebt cusComDebt);
	list<Common.GridViewDTO> getCusComDebtss(1:CusComDebt cusComDebt);
	CusComDebt getCusComDebt(1:i32 pid);
	CusComDebt getCusComDebtRight(1:i32 pid);
	
	i32 addCusComAssure(1:CusComAssure cusComAssure)throws (1:Common.ThriftServiceException e);
	i32 updateCusComAssure(1:CusComAssure cusComAssure)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComAssure(1:string pids)throws (1:Common.ThriftServiceException e);
	//查询客户对外担保信息
	list<Common.GridViewDTO> getCusComAssures(1:CusComAssure cusComAssure);
	CusComAssure getCusComAssure(1:i32 pid);
	
	i32 addCusComInvest(1:CusComInvest cusComInvest)throws (1:Common.ThriftServiceException e);
	i32 updateCusComInvest(1:CusComInvest cusComInvest)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComInvest(1:string pids)throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getCusComInvests(1:CusComInvest cusComInvest);
	CusComInvest getCusComInvest(1:i32 pid);
	
	i32 addCusComReward(1:CusComReward cusComReward)throws (1:Common.ThriftServiceException e);
	i32 updateCusComReward(1:CusComReward cusComReward)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComReward(1:string pid)throws (1:Common.ThriftServiceException e);
	//查询客户获奖信息
	list<Common.GridViewDTO> getCusComRewards(1:CusComReward cusComReward);
	CusComReward getCusComReward(1:i32 pid);

	i32 selectCusTypeByAcctId(1:i32 acctId)throws (1:Common.ThriftServiceException e);
	i32 deleteUnderCom(1:string pids)throws (1:Common.ThriftServiceException e);


	/*pengchuntao添加,利润表与利润Meta表*/
	list<CusComIncomeReportMeta> selectAllCusComIncomeReportMeta() throws (1:Common.ThriftServiceException e);
	CusComIncomeReportMeta selectCusComIncomeReportMetaByLineNum(1:i32 lineNum) throws (1:Common.ThriftServiceException e);
	i32 saveCusComIncomeReport(1:CusComIncomeReport cusComIncomeReport)throws (1:Common.ThriftServiceException e);
	list<CusComIncomeReport> selectCusComIncomeReportByReportId(1:i32 reportId)throws (1:Common.ThriftServiceException e);
	i32 updateCusComIncomeReport(1:CusComIncomeReport cusComIncomeReport) throws (1:Common.ThriftServiceException e);
	i32 deleteCusComIncomeReportByReportID(1:i32 reportID) throws (1:Common.ThriftServiceException e);
	
	
	list<ExcelCusComIncomeReport> excelCusComIncomeReportByComId(1:i32 comId)throws (1:Common.ThriftServiceException e);
	list<ExcelCusComBalanceSheet> excelCusComBalanceSheetByComId(1:i32 comId)throws (1:Common.ThriftServiceException e);
	
	list<ExcelCusComIncomeReport> excelCusComIncomeReportByReportId(1:string reportId)throws (1:Common.ThriftServiceException e);
	list<ExcelCusComIncomeReport> excelCashFlowByReportId(1:string reportId)throws (1:Common.ThriftServiceException e);
	list<ExcelCusComIncomeReport> exportCashFlowMaterialByReportId(1:string reportPid)throws (1:Common.ThriftServiceException e);
	
	list<ExcelCusComBalanceSheet> getExcelCusComBalanceSheet(1:string pids)throws (1:Common.ThriftServiceException e);
	
	
	
	// 根据当前用户ID查询下面的客户企业信息
	list<CusDTO> getEnterpriseList(1:CusDTO cusDto);
	// 根据当前用户ID查询下面的客户企业信息的总数
	i32 getEnterpriseListCount(1:CusDTO cusDto);
	// 查询所有的客户企业信息
	list<CusDTO> getEnterpriseLegalPersonList(1:CusDTO cusDto);
	// 查询所有的客户企业的数量
	i32 getEnterpriseLegalPersonCount(1:CusDTO cusDto);
	// 查询企业的股东人员列表
	list<CusComShare> getShareList(1:i32 cusComId);
	
	//财务报表操作
	CusComFinanceReport selectCusComFinanceReportByComId(1:i32 comId)throws (1:Common.ThriftServiceException e);
	list<CusComFinanceReportOverviewDTO> selectCusComFinanceReportOverviewDTOByAnnual(1:map<string,i32> myMap)throws (1:Common.ThriftServiceException e);
	i32 obtainCusComFinanceReportOverviewCount(1:map<string,i32> myMap)throws (1:Common.ThriftServiceException e);
	i32 deleteCusComFinanceReportByPIDUseBatch(1:list<CusComFinanceReportOverviewDTO> financialReportList);
	i32 deleteCusComFinanceReportByPID(1:i32 pId)throws (1:Common.ThriftServiceException e);
	
	//查询现金流量表
	CashFlowReport selectCusComCashFlowReportByComId(1:i32 comId)throws (1:Common.ThriftServiceException e);
	CashFlowItem selectCusComCashFlowItemByLineNum(1:i32 lineNum)throws (1:Common.ThriftServiceException e);
	list<FinacialDTO> getAllCusComCaseFlow(1:CashFlowItem cashFlowItem) throws (1:Common.ThriftServiceException e);
	//CashFlowReport selectCusComCaseFlowByComId(1:i32 comId)throws (1:Common.ThriftServiceException e);
	i32 saveCusComCaseFlow(1:FinacialDTO finacialDTO)throws (1:Common.ThriftServiceException e);
	list<CashFlowReport> selectCashFlowReportByReportId(1:i32 reportId)throws (1:Common.ThriftServiceException e);
	//通过reportId查询现金流量补充材料表
	list<CashFlowReport> selectCashFlowReportSupplementByReportId(1:i32 reportId)throws (1:Common.ThriftServiceException e);
	//保存现金流量表
	i32 addCusComCashFlowReport(1:CashFlowReport cashFlowReport)throws (1:Common.ThriftServiceException e);
	i32 updateCusComCashFlowReport(1:CashFlowReport cashFlowReport) throws (1:Common.ThriftServiceException e);
	i32 deleteCusComCashFlowReportByReportID(1:i32 reportID) throws (1:Common.ThriftServiceException e);

	CusComFinanceReport selectCusComFinanceReportByComIdAndYearMonth(1:i32 comId , 2:i32 accountingYear , 3:i32 accountingMonth)throws (1:Common.ThriftServiceException e);
	i32 saveCusComFinanceReport(1:CusComFinanceReport cusComFinanceReport)throws (1:Common.ThriftServiceException e);
	
	
	//查询现金流量项目表
	list<FinacialDTO> getAllCusComCaseFlowMeta(1:CashFlowItem cashFlowItem) throws (1:Common.ThriftServiceException e);
	//保存现金流量表补充材料
	i32 addCusComCashFlowMetaReport(1:CashFlowReport cashFlowReport)throws (1:Common.ThriftServiceException e);
	//pengchuntao,资产负债表报表会计科目表 ,service类
	list<CusComBalanceSheetMeta> selectAllCusComBalanceSheetMeta() throws (1:Common.ThriftServiceException e);
	list<CusComBalanceSheetMeta> selectLeftCusComBalanceSheetMeta() throws (1:Common.ThriftServiceException e);
	list<CusComBalanceSheetMeta> selectRightCusComBalanceSheetMeta() throws (1:Common.ThriftServiceException e);
	//资产负债表,Service类 ，CUS_COM_BALANCE_SHEET
	i32 saveCusComBalanceSheet(1:CusComBalanceSheet cusComBalanceSheet) throws (1:Common.ThriftServiceException e);
	list<CusComBalanceSheet> selectCusComBalanceSheetByReportId(1:i32 reportId)throws (1:Common.ThriftServiceException e);
	i32 updateCusComBalanceSheet(1:CusComBalanceSheet cusComBalanceSheet) throws (1:Common.ThriftServiceException e);
	i32 deleteCusComBalanceSheetByReportID(1:i32 reportID) throws (1:Common.ThriftServiceException e);
	
	//通过REPORT_ID批量删除资产负债表、利润表、现金流量表数据
	i32 deleteCusComBalanceSheetByReportIDUseBatch(1:list<CusComFinanceReportOverviewDTO> dtoList);
	i32 deleteCusComIncomeReportByReportIDUseBatch(1:list<CusComFinanceReportOverviewDTO> dtoList);
	i32 deleteCusComCashFlowReportByReportIDUseBatch(1:list<CusComFinanceReportOverviewDTO> dtoList);
	
	//通过reportId初始化编辑资产负债表，利润表，现金流量表，现金流量补充表
	list<BalanceSheetEditDTO> initLeftEditCusComBalanceSheetByReportId(1:i32 reportId)  throws (1:Common.ThriftServiceException e);
	list<BalanceSheetEditDTO> initRightEditCusComBalanceSheetByReportId(1:i32 reportId)  throws (1:Common.ThriftServiceException e);
	list<CusComIncomeReportEditDTO> initCusComIncomeReportByReportId(1:i32 reportId)  throws (1:Common.ThriftServiceException e);
	list<CashFlowReportEditDTO> initCashFlowReportByReportId(1:i32 reportId)  throws (1:Common.ThriftServiceException e);
	list<CashFlowReportEditDTO> initCashFlowReportSupplementByReportId(1:i32 reportId)  throws (1:Common.ThriftServiceException e);
	
	//验证证件类型和证件号码是否存在
	i32 validateCeryNumber(1:map<string,string> myMap)  throws (1:Common.ThriftServiceException e);
	//根据客户Id查percusId
	i32 selectPersonIdByAcctId(1:i32 acctId)  throws (1:Common.ThriftServiceException e);
	
	i32 addUnderCom(1:list<CusPerCom> lists) throws (1:Common.ThriftServiceException e);
	
	//导出尽职报告所需
	CusComBaseDTO selectPmUserIdByAcctId(1:i32 acctId)throws (1:Common.ThriftServiceException e);
	ExportCusComBase selectCompanyByAcctId(1:i32 acctId)throws (1:Common.ThriftServiceException e);
	i32 getAcctIdByComId(1:i32 comId)throws (1:Common.ThriftServiceException e);
	
	i32 getPersonTotal(1:i32 acctId)throws (1:Common.ThriftServiceException e);
	
	i32 getTotals(1:CusComTeam cusComTeam)  throws (1:Common.ThriftServiceException e);
	i32 getTotal(1:CusComContact comContact)  throws (1:Common.ThriftServiceException e);
	i32 getTotalDept(1:CusComDebt cusComDebt)  throws (1:Common.ThriftServiceException e);
	i32 getTotalDepts(1:CusComDebt cusComDebt)  throws (1:Common.ThriftServiceException e);
	i32 getTotalAssure(1:CusComAssure comAssure)  throws (1:Common.ThriftServiceException e);
	i32 getTotalInvest(1:CusComInvest cusComInvest)  throws (1:Common.ThriftServiceException e);
	i32 getTotalReward(1:CusComReward comReward)  throws (1:Common.ThriftServiceException e);
	i32 getTotalCusComBases(1:CusComBase cusComBase)  throws (1:Common.ThriftServiceException e);
	i32 validateBusLicCert(1:map<string,string> myMap) throws (1:Common.ThriftServiceException e);
	list<Common.GridViewDTO> getBusinessAllInfo(1:QueryPersonDTO queryPersonDTO) throws (1:Common.ThriftServiceException e);
		//验证黑名单客户是否有查询详细的权限
	i32 validateIsPermissions(1:map<string,i32> myMap)  throws (1:Common.ThriftServiceException e);

	list<CusComGuaranteeType> getGuaranteeTypeBycusComBasePid(1: i32 cusComBasePid) throws (1:Common.ThriftServiceException e);

	list<Common.GridViewDTO> searchComExportByPid(1:string pids) throws (1:Common.ThriftServiceException e);
	i32 getTotalCusAndPerBusiness(1:QueryPersonDTO queryPersonDTO)  throws (1:Common.ThriftServiceException e);
    string searcherBlackListRefuse(1:i32 pid) throws (1:Common.ThriftServiceException e);
    i32 searcherCusTypeByAcctId(1:i32 pid) throws (1:Common.ThriftServiceException e);
	//根据机构ID查询企业信息
	CusComBase getComBaseByOrgId(1:i32 orgId) throws (1:Common.ThriftServiceException e);
}


/*企业客户服务,企业财务状况计算,填写企业财务状况首页使用*/

service CusComCalculateService {
	//获得某一个企业最大期末的PID
	string obtainPIDForMaxMonth(1:i32 comId,2:i32 accountingYear) throws (1:Common.ThriftServiceException e);
	
	//通过reportId获得资产负债表字段的LINE_NUM与END_val对应关系
	list<CusComBalanceSheetCalculateDTO> obtainLeftCusComBSLCalculatesByReportId(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	list<CusComBalanceSheetCalculateDTO> obtainRightCusComBSLCalculatesByReportId(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	map<i32,double> obtainLeftCusComBSLCalculatesMap(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	map<i32,double> obtainRightCusComBSLCalculatesMap(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	
	//通过reportId获得利润表字段的LINE_NUM与THIS_YEAR_VAL对应关系
	list<CusComIncomeReportCalculateDTO> obtainIncomeReportCalculateByReportId(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	map<i32,double> obtainIncomeReportCalculateMap(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	
	//通过reportId获得现金流量表与补充材料，字段的LINE_NUM与THIS_YEAR_VAL对应关系
	list<CusComCashFlowReportCalculateDTO> obtainCashFlowCalculateByReportId(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	map<i32,double> obtainCashFlowCalculateMap(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	list<CusComCashFlowReportCalculateDTO> obtainSupplementCashFlowCalculateByReportId(1:i32 reportId) throws (1:Common.ThriftServiceException e);
	map<i32,double> obtainSupplementCashFlowCalculateMap(1:i32 reportId) throws (1:Common.ThriftServiceException e);
}


/*
 *客户关系表
 *2016-08-08 09:59:08
 **/
struct CusRelation{
	1: i32 pid;//主键
	2: i32 acctId;//客户id
	3: i32 orgId;//机构id
	4: i32 orgType;//机构类型
	5: i32 pmUserId;//所属人id
}
/*客户关系表Service*/
service CusRelationService{
    //根据条件查询所有客户关系表
	list<CusRelation> getAll(1:CusRelation cusRelation);
	//新增客户关系表
	i32 insert(1:CusRelation cusRelation);
	i32 delCusReltion(1:CusRelation cusRelation);
}



/*
 *客户征信报告记录
 *2017-06-06 16:52:22
 **/
struct CusCreditReportHis{
	1: i32 pid;			//主键
	2: i32 acctId;		//个人客户ID
	3: i32 dataSource;	//信息来源(1:鹏元征信)
	4: i32 reportType;	//报告类型
	5: string queryResonId;		//查询原因
	6: string queryName;		//姓名
	7: string queryDocumentNo;	//证件号码
	8: string queryPhone;		//手机号码
	9: i32 queryStatus;			//查询状态 (1:成功，2：失败) 
	10: string queryStatusMsg;	//查询状态信息 
	11: string reportNo;		//报告编号 
	12: string queryResult;		//查询数据结果JSON      
	13: i32 operator;			//操作人 
	14: string createTime;		//创建时间
	15: i32 creator;			//创建人 
	16: string remark;			//备注 
	17:i32 page; // 页码（第几页）
	18:i32 rows; // 每页显示数量
	19: string realName;			//用户真实姓名
	20: string beginCreateTime;		//开始创建时间
	21: string endCreateTime;		//结束创建时间
	22: i32 isRepeat;			//是否重复查询 （1：是 2：否）
	23: double unitPrice;		//单次价格
	24: i32 totalCount;			//查询次数
	25: double totalPrice;		//总价格
	26: string queryPbocStatus;	//有无人行征信  true/false/und
	27: list<i32> userIds;
}
/*客户征信报告记录Service*/
service CusCreditReportHisService{
    //根据条件查询所有客户征信报告记录
	list<CusCreditReportHis> getAll(1:CusCreditReportHis cusCreditReportHis);
	//查询客户征信报告记录
	CusCreditReportHis getById(1:i32 pid);
	//新增客户征信报告记录
	i32 insert(1:CusCreditReportHis cusCreditReportHis);
	//修改客户征信报告记录
	i32 update(1:CusCreditReportHis cusCreditReportHis);
	//删除客户征信报告记录
	i32 deleteById(1:i32 pid);
	//批量删除客户征信报告记录
	i32 deleteByIds(1:list<i32> pids);
	
	
	//查询客户征信报告记录列表(可分页)
	list<CusCreditReportHis> selectList(1:CusCreditReportHis cusCreditReportHis);
	//查询客户征信报告记录总数
	i32 selectTotal(1:CusCreditReportHis cusCreditReportHis);
	
	//查询征信报告费用统计列表(可分页)
	list<CusCreditReportHis> selectCreditReportFeeList(1:CusCreditReportHis cusCreditReportHis);
	//查询征信报告费用统计总数
	i32 selectCreditReportFeeListTotal(1:CusCreditReportHis cusCreditReportHis);
	//查询征信报告费用总和
	CusCreditReportHis selectCreditReportFeeSum(1:CusCreditReportHis cusCreditReportHis);
}


/*
 *	征信报告费用配置
 */
struct CreditReportFeeConfig{
	1: i32 pid;			//主键
	2: i32 dataSource;	//信息来源
	3: i32 reportType;	//报告类型   
	4: double unitPrice;		//单次价格
	5: i32 creator;				//创建人 
	6: string createTime;		//创建时间
	7: i32 updator;				//更新人 
	8: string updateTime;		//更新时间
	9: string remark;			//备注 
	10: string operatorName;	//操作者名称
	11:i32 page; // 页码（第几页）
	12:i32 rows; // 每页显示数量
}


/*征信报告费用配置Service*/
service CreditReportFeeConfigService{
    //根据条件查询所有征信报告费用配置
	list<CreditReportFeeConfig> getAll(1:CreditReportFeeConfig creditReportFeeConfig);
	//查询征信报告费用配置
	CreditReportFeeConfig getById(1:i32 pid);
	//新增征信报告费用配置
	i32 insert(1:CreditReportFeeConfig creditReportFeeConfig);
	//修改征信报告费用配置
	i32 update(1:CreditReportFeeConfig creditReportFeeConfig);
	//删除征信报告费用配置
	i32 deleteById(1:i32 pid);
	//批量删除征信报告费用配置
	i32 deleteByIds(1:list<i32> pids);
	
	//查询征信报告费用配置列表(可分页)
	list<CreditReportFeeConfig> selectList(1:CreditReportFeeConfig creditReportFeeConfig);
	//查询征信报告费用配置总数
	i32 selectTotal(1:CreditReportFeeConfig creditReportFeeConfig);
}