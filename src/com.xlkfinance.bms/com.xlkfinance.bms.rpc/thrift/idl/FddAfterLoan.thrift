namespace java com.xlkfinance.bms.rpc.fddafterloan
include "System.thrift"
include "Common.thrift"
include "File.thrift"


/*
 *抵押管理信息
 *2017-12-19 09:23:03
 **/
struct BizProjectMortgage{
	1: i32 pid;//主键
	2: i32 projectId;//项目ID
	3: i32 mortgageStatus;//抵押物状态(1、待登记入库2=待出库申请3=待出具注销材料4=待退证登记5=已完结)
	4: string registerTime;//入库登记时间
	5: i32 registerId;//入库登记人
	6: string issueTime;//出库申请时间
	7: i32 issueId;//出库申请人
	8: string issueUserName;//出库申请人
	9: string issueMaterial;//出库签收材料
	10: i32 cancelId;//出具注销人
	11: string cancelUserName;//出具注销人
	12: string cancelTime;//出具注销时间
	13: string cancelMaterial;//出具注销材料
	14: i32 returnId;//退证登记人
	15: string returnUserName;//退证登记人
	16: string returnTime;//退证登记时间
	17: string returnMaterial;//退证登记材料
	18: string createDate;//创建时间
	19: i32 createrId;//创建人ID
	20: i32 updateId;//更新人ID
	21: string updateDate;//更新时间
	22: i32 handleType;//办理类型：2=待出库申请，3=待出具注销材料，4=待退证登记
	23: string signerUserName;//签收人
	24: string signerFile;//签收材料
	25: string signerDate;//签收日期
}
/*
 *抵押管理列表
 *2017-12-19 09:23:03
 **/
struct BizProjectMortgageIndexPage{
	1: i32 projectId;//项目ID
	2: i32 mortgageStatus;//抵押物状态(1、待登记入库2=待出库申请3=待出具注销材料4=待退证登记5=已完结)
	3: string projectNumber;//项目编号
    4: string projectName;//项目名称
    5: i32 repaymentStatus;//回款状态
    6: string finishRepaymentDate;//结清日期
    7: string finishRepaymentEndDate;//结清日期
	8: i32 pid;//抵押ID
	9: i32 pmUserId;//客户经理ID
	10: list<i32> userIds;//
    11: i32 page=1; //第几页
	12: i32 rows=10; //总页数
	
}
/*抵押管理信息Service*/
service BizProjectMortgageService{
    //根据条件查询所有抵押管理信息
	list<BizProjectMortgage> getAll(1:BizProjectMortgage bizProjectMortgage);
	//查询抵押管理信息
	BizProjectMortgage getById(1:i32 pid);
	//新增抵押管理信息
	i32 insert(1:BizProjectMortgage bizProjectMortgage);
	//修改抵押管理信息
	i32 update(1:BizProjectMortgage bizProjectMortgage);
	//删除抵押管理信息
	i32 deleteById(1:i32 pid);
	//批量删除抵押管理信息
	i32 deleteByIds(1:list<i32> pids);
	//抵押管理列表（分页查询）
	list<BizProjectMortgageIndexPage> queryMortgageIndexPage (1:BizProjectMortgageIndexPage query) ;
	//抵押管理条数（分页查询）
	i32 getMortgageIndexPageTotal (1:BizProjectMortgageIndexPage query) ;
}

/*
 *入库材料清单
 *2017-12-19 09:32:16
 **/
struct BizStorageInfo{
	1: i32 pid;//主键
	2: i32 projectId;//项目ID
	3: i32 mortgageId;//抵押物管理ID
	4: string fileName;//资料名称
	5: string registerTime;//入库时间
	6: string fileDesc;//资料说明
	7: i32 createId;//操作人
	8: string createDate;//操作时间
	9: i32 status;//状态（1=未出库2=已出库）
	10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
	12: string createName;//操作人
}
/*入库材料清单Service*/
service BizStorageInfoService{
    //根据条件查询所有入库材料清单
	list<BizStorageInfo> getAll(1:BizStorageInfo bizStorageInfo);
	//查询入库材料清单
	BizStorageInfo getById(1:i32 pid);
	//新增入库材料清单
	i32 insert(1:BizStorageInfo bizStorageInfo);
	//修改入库材料清单
	i32 update(1:BizStorageInfo bizStorageInfo);
	//删除入库材料清单
	i32 deleteById(1:i32 pid);
	//批量删除入库材料清单
	i32 deleteByIds(1:list<i32> pids);
	//入库材料清单（分页查询）
	list<BizStorageInfo> queryStorageInfo (1:BizStorageInfo query) ;
	//入库材料清单条数（分页查询）
	i32 getStorageInfoTotal (1:BizStorageInfo query) ;
}