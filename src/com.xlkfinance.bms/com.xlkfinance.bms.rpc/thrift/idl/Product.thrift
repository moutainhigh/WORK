namespace java com.xlkfinance.bms.rpc.product
include "Common.thrift"
include "System.thrift"

/*
 * 产品信息
 * 表：BIZ_PRODUCT
 */
struct Product{
	1:i32 pid;//主键
	2:i32 createrId; // 创建人ID
	3:i32 productType; // 产品类型（1、现金2、赎楼） value
	4:string productName; // 产品名称
	5:i32 cityId; // 所属地区ID
	6:string productNumber; // 产品代码
	7:string loanMoney; // 贷款金额(区间)
	8:string loanTerm; // 贷款期限
	9:double yearLoanInterest; // 年利率
	10:double monthLoanInterest; // 月利率
	11:double manageRate; // 管理费率
	12:double otherRate; // 其他费率
	13:double marketAvgInterest;//市场平均利率
	14:double floatingRate;//利率浮动比例
	15:string createDate; //创建时间
	16:string updateDate;//修改时间
	17:i32 status; //产品状态（1、有效2、无效）
	18:string remark;//详情
	
	19:string productTypeText;//产品类型文本
	20:string cityName;//城市名称
	
	21:i32 page;// 
	22:i32 rows;//
	23:list<i32> userIds;
	24:list<ActProduct> actProducts;
	25:list<i32> cityIds;
	26:string loanWorkProcessId;//贷前审批流程定义ID
	27:string bizHandleWorkProcessId;//业务办理审批流程定义ID
	28:i32 productSource;//产品来源（1、小科以及万通2、机构的产品）
	29:i32 tradeType;//交易类型
	30:i32 isForeclosure;//是否赎楼（1=是；2=否）
}
/*产品流程关系信息*/
struct ActProduct{
	1:i32 pid;//主键
	2:string actId; // 工作流ID
	3:i32 productId; // 产品Id
	4:i32 actType; // 流程类型
	5:string actTypeText;//流程类型文本
}




/*产品信息服务*/
service ProductService {

	// 查询所有有效的产品
	list<Common.GridViewDTO> getAllProduct(1:Product product)throws (1:Common.ThriftServiceException e);
	//查询所有有效的产品的总数
	i32 getAllProductCount(1:Product product);
	//通过ID查询产品
	Product getProductById(1:i32 pid);
	//新增产品
	i32 addProduct(1:Product product)throws (1:Common.ThriftServiceException e);
	//修改产品信息
	i32 updateProduct(1:Product product)throws (1:Common.ThriftServiceException e);
	//删除产品
	i32 deleteProduct(1:string pids)throws (1:Common.ThriftServiceException e);
	// 查询所有有效的产品
	list<Product> getAllProductList(1:Product product)throws (1:Common.ThriftServiceException e);
	//查询产品类型
	i32 getProductType(1:i32 pid);
	//通过项目ID查询产品信息
	Product findProductByProjectId(1:i32 projectId); 
}
/*产品流程关系服务*/
service ActProductService{
	// 根据产品ID，查询所有
	list<ActProduct> getAllProduct(1:ActProduct actProduct)throws (1:Common.ThriftServiceException e);
	//新增产品流程关系
	i32 addActProduct(1:ActProduct actProduct)throws (1:Common.ThriftServiceException e);
	//修改产品流程关系
	i32 updateActProduct(1:ActProduct actProduct)throws (1:Common.ThriftServiceException e);
	//删除产品流程关系
	i32 deleteActProduct(1:i32 pid)throws (1:Common.ThriftServiceException e);
}
