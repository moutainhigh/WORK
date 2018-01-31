namespace java com.xlkfinance.bms.rpc.checkapi
include "Common.thrift"
 

/*房产查档记录 - BIZ_HOUSE_CHECK_DOC*/
struct HouseCheckDoc{
	1:i32 page;					//页数
	2:i32 rows; 				//行数
	3:i32 pid;					//主键
	4:i32 userId;				//用户id
	5:i32 queryType;			//查档类型（1：分户,2：分栋）
	6:i32 certType;				//产权证书类型（1：房地产权证 书,2：不动产权证书）
	7:string year;				//不动产权证书时的年份
	8:string certNo;			//房产证号
	9:string personInfo;		//身份证号/权利人姓名
	10:string createTime;		//创建时间
	11:string updateTime;		//更新时间
	12:i32 isDel;				//是否删除  1：是  2：否
	13:list<HouseCheckDocDetails> detailsList ;	//查档详情列表
	14:i32 checkStatus;			//查档状态   查档状态：1：成功 2：失败 
	15:string checkDocTime;		//查档时间
}
 

/*房产查档记录详情 - BIZ_HOUSE_CHECK_DETAILS*/
struct HouseCheckDocDetails{
	1:i32 page;					//页数
	2:i32 rows; 				//行数
	3:i32 pid;					//主键
	4:i32 houseCheckDocId;		//用户id
	5:string checkDocTime;		//查档时间
	6:string checkDocContent;	//查档内容
	7:i32 checkStatus;			//查档状态   查档状态：1：成功 2：失败 
	8:string createTime;		//创建时间
}


/*房产查档记务业务类*/
service HouseCheckDocService{

	//查询房产查档日记
	HouseCheckDoc getHouseCheckDocById(1:i32 pid)throws (1:Common.ThriftServiceException e);

	//查询房产查档日记列表
	list<HouseCheckDoc> getHouseCheckDocs(1:HouseCheckDoc houseCheckDoc)throws (1:Common.ThriftServiceException e);
	//查询房产查档日记总数
	i32 getHouseCheckDocCount(1:HouseCheckDoc houseCheckDoc)throws (1:Common.ThriftServiceException e);
	
	//新增房产查档日记
	i32 addHouseCheckDoc(1:HouseCheckDoc houseCheckDoc)throws (1:Common.ThriftServiceException e);
	
	//修改房产查档日记
	i32 updateHouseCheckDoc(1:HouseCheckDoc houseCheckDoc)throws (1:Common.ThriftServiceException e);
	
	//查询最后一条查档详情记录
	HouseCheckDocDetails getLastOneCheckDocDetails(1:map<string,string> queryMap)throws (1:Common.ThriftServiceException e);
	
	//查询查档详情列表
	list<HouseCheckDocDetails> getCheckDocDetailsList(1:HouseCheckDocDetails houseCheckDocDetails)throws (1:Common.ThriftServiceException e);
	
}

