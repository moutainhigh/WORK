namespace java com.xlkfinance.bms.rpc.beforeloan
include "System.thrift"
include "Common.thrift"

/*
 *修改记录信息
 *2017-01-04 17:13:30
 **/
struct BizChangeRecord{
	1: i32 pid;//主键
	2: i32 relationId;//变更关联ID
	3: i32 modifyUser;//修改人
	4: string modifyTime;//修改时间
	5: string modifyReason;//修改原因
	6: string oldValue;//原值
	7: string newValue;//变更值
	8: i32 valueType;//值类型
	9: i32 changeType;//变更类型
	10: string operationIp;//操作IP
}

struct BizChangeRecordDTO{
	1: i32 relationId;//变更关联ID
	2: i32 modifyUser;//修改人
	3: string modifyTime;//修改时间
	4: string modifyReason;//修改原因
	5: string operationIp;//操作IP
	6: i32 page;
	7: i32 rows;
	8: string modifyUserName;//修改人姓名
	9:double oldLoanMoney;//借款金额
	10:double newLoanMoney;
	11:i32 oldLoanDays;//借款天数
	12:i32 newLoanDays;
	13:double oldFeeRate;//费率
	14:double newFeeRate;
	15:double oldGuaranteeFee;//咨询费
	16:double newGuaranteeFee;
}

/*修改记录信息Service*/
service BizChangeRecordService{
    //根据条件查询所有修改记录信息
	list<BizChangeRecord> getAll(1:BizChangeRecord bizChangeRecord);
	//查询修改记录信息
	BizChangeRecord getById(1:i32 pid);
	//新增修改记录信息
	i32 insert(1:BizChangeRecord bizChangeRecord);
	//修改修改记录信息
	i32 update(1:BizChangeRecord bizChangeRecord);
	//新增修改记录
	i32 insertChangeRecod(1:BizChangeRecordDTO recordDto);
}
