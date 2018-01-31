namespace java com.xlkfinance.bms.rpc.contract
include "System.thrift"
include "Common.thrift"

//合同模板信息
struct ContractTempLate {
        1: i32 pid;
        2: i32 templateCatelog;
        3: i32 templateType;
        4: i32 templateOwner;
        5: i32 templateUseMode;
        6: string templateName; 
        7: string templateUrl;
        8: string contractNumberFun;
        9: string templateDesc;
        10: string updateDttm;       
        11: i32 status;
        12: i32 uploadUserId;
        13: i32 page;
		14: i32 rows;
		15: i32 total;
		16: string templateTypeText;
		17: string templateCatelogText;
		18: i32 templateParFun;
		19: string templateParFunText;
		20: string contractTypeCode;
		21: string comboboxTemplateText; //下拉框显示（合同模板+合同类型）
		22: i32 applyType;//适用项目类型
		23: i32 cycleType;//项目循环类型
}
//合同模板参数
struct ContractTempLateParm {
        1: i32 pid;
        2: i32 contractTemplateId;
        3: string matchFlag; 
        4: string matchName;
        5: i32 showIndex;
        6: i32 outputType;
        7: i32 isTable;   
        8: i32 valConvertFlag;
        9: i32 page;
		10: i32 rows;
		11: i32 total;
		12: i32 status;
}

//合同模板dto
struct TempLateParmDto {
        1: i32 pid;
        2: i32 contractTemplateId;
        3: string matchFlag; 
        4: string matchName;
        5: i32 showIndex;
        6: i32 outputType;      
        7: i32 valConvertFlag;
        8: string convertFormula;
        9: i32 page;
		10: i32 rows;
		11: i32 total;
		12: i32 status;
		13: string tempLateType;
		14: string tempLateName;
		15: string matchValue;
		16: i32 contractId;
		17: string fixedVal;
		18: i32 isTable;
}

//合同信息 
struct Contract{

		1: i32 pid;
        2: i32 loanId;
        3: i32 contractCatelog; 
        4: i32 contractType;
        5: i32 contractTemplateId;
        6: string contractNo;
        7: string contractTypeCode;       
        8: string contractName;
        9: string projectNuber;
        10: i32 page;
		11: i32 rows;
		12: i32 total;
		13: i32 status;
		14: i32 projectId;
		15: i32 customerId;
		16: i32 economyType;//经济行业类型
	 	17: string contractUrl;
	 	18: i32 isLegalConfirmation;
	 	19: i32 isSigned;
	 	20: string signedDate;
	 	21: string remark;
	 	22: string projectName;
	 	23: i32 customerType;//客户类型
	 	24: string tempContractNo;
	 	25: string contractCatelogKey;//英文键-对应 syslookupval.lookup_val
	 	26: string num;//合同份数
	 	27: string firstNum;//甲方
	 	28: string secondNum;//乙方
	 	29: string contractTypeText;//合同类型值
	 	30: string customerName;//客户名称
	 	31: string templateName;// 合同模版名称
	 	32: string mortgageBranch;//抵押登记部门
	 	33: i32 parentId; //父合同ID
	 	34: i32 oldProjectId;	// 老项目ID
	 	35: i32 refId; //引用ID
}

//合同项目详情
struct ContractProject{
	1: i32 pid;
	2: i32 acctId;
	3: i32 projectType;
	4: string projectName;
	5: string projectId;
	6: i32 pmUserId;
	7: i32 requestStatus;
	8: string requestDttm;
	9: string completeDttm;
	10: i32 status;
}

//合同附件
struct ContractAccessorie{
	1: i32 pid;
	2: string fileType;
	3: string fileName;
	4: i32 fileSize;
	5: string uploadDttm;
	6: string fileDesc;
	7: string fileUrl; 
	8: i32 page;
	9: i32 rows;
	10: i32 total;
	11: i32 fileId;
	12: i32 contractId;
}

//合同参数信息
struct ContractParameter{
	1: i32 pid;
	2: i32 contractId;
	3: i32 parameterId;
	4: string parameterVal;
	5: string parameterDesc;
	6: i32 status;
	7: i32 page;
	8: i32 rows;
	9: i32 total;
	10: string matchFlag; //匹配占位符
    11: string matchName; //匹配符名称
    12: i32 showIndex; //排序行号
} 

struct ContractDynamicParameter{
	1: string parameterCode;
	2: string parameterVal;
} 

struct ContractDynamicTableParameter{
	1: string goodsName;
	2: string goodsNumber;
	3: string goodsCount;
	4: string goodsUnit;
	5: string goodsAddress;
	6: string goodsValue;
	7: string goodsEffective;
	
	8: bool isGoodsName;
	9: bool isGoodsNumber;
	10: bool isGoodsCount;
	11: bool isGoodsUnit;
	12: bool isGoodsAddress;
	13: bool isGoodsValue;
	14: bool isGoodsEffective;
	
	15: i32 pid;
	16: i32 lineNumber;
	17: i32 contractId;
	18: i32 status;
	
	// 以前的设计太局限性了，使用新的
	19: string col0;  // 对应 goodsName
	20: string col1;  // 对应 goodsNumber
	21: string col2;  // 对应 goodsCount
	22: string col3;  // 对应 goodsUnit
	23: string col4;  // 对应 goodsAddress
	24: string col5;  // 对应 goodsValue
	25: string col6;  // 对应 goodsEffective
} 

/*
 * 合同编号
 * 表：BIZ_CONTRACT_NUMBER
 */
struct ContractNumber{
	1:i32 pid;// 主键
	2:i32 contractId;// 合同信息ID
	3:i32 parentContractId;// 父类合同id(如果没有父类,默认为0)
	4:string yearCode;// 年份(yyyy)
	5:string orgCode;// 组织前缀
	6:string contractType;// 合同类型代码
	7:i32 contractIndex;// 合同序号
	8:i32 status;// 状态 
	9:string parentContractIndex;// 父类合同序号
}


struct ContractAttachment{
	1:i32 pid;//主键
	2:i32 contractId;//合同信息ID
	3:string attachmentFileName;//附件名称
	4:string attachmentLocation;//线下资料位置
	5:i32 offlineCnt;//线下份数
	6:i32 onlineCnt;//线上份数
	7:string remark;//备注
	
}



service ContractTempLateService{

	i32 addContractTempLate(1:ContractTempLate contractTempLate) throws (1:Common.ThriftServiceException e);
	
	list<ContractTempLate> pageTempLateList(1:ContractTempLate contractTempLate);
	
	i32 pageTotalCount(1:ContractTempLate contractTempLate);
	
	bool updateTempLate(1:ContractTempLate contractTempLate);
	
	bool deleteTempLate(1:string pidArray);
	
	bool addTempLateParm (1:list<ContractTempLateParm> listCtp);
	
	bool updateTempLateParm (1:list<ContractTempLateParm> listCtp);
	
	bool delTempLateParm (1:i32 ContractTemplateId);
	
	bool delTempLateOneParm (1:i32 pid);
	
	list<TempLateParmDto> getTempParmList(1:TempLateParmDto tempLateParmDto);
	
	i32 getTempTotaleCount(1:TempLateParmDto tempLateParmDto);
	
	bool updateTempLateParmDto  (1:list<TempLateParmDto> listCtp);
	
}


service ContractService{
	list<Contract> pageContractList(1:Contract contract);
	
	i32 pageContractCount(1:Contract contract);
	
	list<ContractProject> getProJectInfoById(1:Contract contract);
	
	ContractTempLate getContractTempLateInfoById(1:i32 tempLateId);
	
	i32 insertContractInfo(1:Contract contract,2:map<i32,string> parameterMap) throws (1:Common.ThriftServiceException e);
	
	i32 updateContractParameter(1:list<ContractParameter> contractParameterList,2:Contract contract) throws (1:Common.ThriftServiceException e);
	
	i32 updateContractParameterData(1:list<ContractParameter> contractParameterList,2: i32 contractId) throws (1:Common.ThriftServiceException e);
	
	list<ContractAccessorie> pageFileAccessorieList(1:Contract contract);
	
	bool deleteAccessorie(1:list<i32> pid);
	
	i32 addAccessorie(1:ContractAccessorie accessorie) throws (1:Common.ThriftServiceException e);
	
	i32 editAccessorie(1:ContractAccessorie accessorie) throws (1:Common.ThriftServiceException e);
	
	i32 pageAccessorieCount(1:Contract contract);

	list<Contract> getLoanContracts(1:Contract contract);
	
	i32 getLoanContractsCount(1:Contract contract);
	 
	i32 deleteContracts(1:string pids);
	
	list<TempLateParmDto> getTempParmListByTemplateId(1:i32 templateId);
	
	list<TempLateParmDto> getAllTempParmValue(1:TempLateParmDto tempLateParmDto);
	
	list<TempLateParmDto> getTempLateParmList(1:map<string,string> paramMap);
	
	list<ContractDynamicTableParameter> getTempLateTableParmList(1:map<string,string> paramMap);

	i32 addContractTableParam(1:list<ContractDynamicTableParameter> contractTabs)throws (1:Common.ThriftServiceException e);
	
	i32 updateContractTableParam(1:list<ContractDynamicTableParameter> contractTabs)throws (1:Common.ThriftServiceException e);
	
	list<ContractDynamicTableParameter> getContractTabs(1: i32 contractId)throws (1:Common.ThriftServiceException e);
	
	//获取父合同
	list<Contract> getParentContracts(1: i32 projectId)throws (1:Common.ThriftServiceException e);
	
	i32 getCreditContractCount(1:map<string,i32> myMap)throws (1:Common.ThriftServiceException e);
	
	i32 getJkContractCount(1:map<string,i32> myMap)throws (1:Common.ThriftServiceException e);
	
	i32 getZkContractCount(1:map<string,i32> myMap)throws (1:Common.ThriftServiceException e);
	
	
	//合同附件
	ContractAttachment getContractAttachment(1:i32 contractId) throws (1:Common.ThriftServiceException e);
	
	i32 addContractAttachment(1:ContractAttachment contractAttachment) throws (1:Common.ThriftServiceException e);
	
	i32 editContractAttachment(1:ContractAttachment contractAttachment) throws (1:Common.ThriftServiceException e);
	
	Contract getContractByContractId(1: i32 contractId)throws (1:Common.ThriftServiceException e);
	
	i32 updateContractUrlOrName(1:Contract contract)throws (1:Common.ThriftServiceException e);
	
	string getChildContract(1:i32 contractId)throws (1:Common.ThriftServiceException e);
	
	list<Contract> getContractGenerateNumber(1:i32 contractId)throws (1:Common.ThriftServiceException e);
	
	string getExtensionContractNum(1:i32 projectId)throws (1:Common.ThriftServiceException e);
}

/*
 * 合同生成编号规则Service
 */
service ContractNumberService{
	// 生成and更新合同编号
	i32 genContractNumber(1:string contractIds) throws (1:Common.ThriftServiceException e);
	// 修改合同编号
	i32 updateContractNumber(1:ContractNumber contractNumber) throws (1:Common.ThriftServiceException e);
	// 根据项目ID查询当前项目的合同信息
	list<Contract> getAllContractListByProjectId(1:i32 projectId);
	
	
}
