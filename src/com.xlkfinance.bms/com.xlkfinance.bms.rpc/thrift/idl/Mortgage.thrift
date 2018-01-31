namespace java com.xlkfinance.bms.rpc.mortgage
include "System.thrift"
include "Common.thrift"
include "Beforeloan.thrift"


/*
 * 担保基本信息
 * 表：BIZ_PROJECT_ASS_BASE
 */
struct ProjectAssBase{
	1:i32 pid;// 主键
	2:i32 projectId; // 项目ID
	3:i32 mortgageGuaranteeType;// 担保方式
	4:string mortgageGuaranteeTypeText;// 担保方式 text
	5:i32 mortgageType;// 抵质押物类型
	6:string mortgageTypeText;// 抵质押物类型 text
	7:i32 ownType;// 所有人类型
	8:string ownTypeText;// 所有人类型 text
	9:i32 ownName;// 所有人ID
	10:string warrantsNumber;// 权证编号
	11:string addressProvince;// 物件地点-省
	12:string addressCity;// 物件地点-市
	13:string addressArea;// 物件地点-县区
	14:string addressDetail;// 物件地点-街道详细
	15:string itemName;// 物件名称
	16:string purpose;// 用途 USE
	17:string regDt;// 登记时间
	18:double regPrice;// 登记价
	19:double assessValue;// 评估净值
	20:double mortgageValue;// 抵质押价值
	21:double mortgageRate;// 抵质押率
	22:double fairValue;// 公允价值
	23:string fairValueGetMethod;// 公允价值获取方式
	24:string saveDttm;// 保管时间
	25:string saveRemark;// 保管备注
	26:string regNumber;// 抵押登记编号
	27:string extRequestReason;// 提取申请事由
	28:string extDttm;// 提取时间
	29:string extRemark;// 提取备注
	30:string removeDttm;// 解除时间
	31:string removeRemark;// 解除备注
	32:i32 status;// 状态
	33:string remark;// 备注
	34:string projectName;//项目名称
	35:string projectNumber;//项目编号
	36:double beginMoney;//开始评估净值
	37:double endMoney;//结束评估净值 
	38:string operator;// 经办人
	39:string operatorText;// 经办人文本
	40:string regOrgName; // 登记机关名称
	41:string transactExplain;// 办理说明
	42:string dataObject;// 合同数据对象
	43:string processDt; // 处理时间
	44:string processRemark;// 处理备注
	45:string owns;// 所有公有人ID(1,2,3)
	46:string ownNameText;// 所有权人姓名
	47:i32 page;// 页码（第几页）
	48:i32 rows;// 每页显示数量
	49:string content;// 担保详细数据
	50:string constructionArea;// 公共的描述列
	51:i32 pmUserId; // 项目经理ID(用作查询当前项目经理下面的所有项目的抵质押物信息包含了下级)
	52:string proposerText;// 申请人姓名
	53:i32 proposer;// 申请人
}

/*
 * 抵质押物文件
 * 表：BIZ_PROJECT_ASS_FILE
 */
struct ProjectAssFile{
	1:i32 pid;// 主键
	2:i32 baseId;// 担保基本信息ID
	3:i32 fileId;// 文件ID
	4:i32 fileSource;// 抵质押物文件类型
	5:string fileSourceText;// 抵质押物文件类型 text
	6:string fileProperty;// 文件属性
	7:string fileDesc;// 上传说明
	8:i32 status;// 状态
	9:string fileType;//文件类型
	10:string fileName;
	11:i32 fileSize;
	12:string uploadDttm;//上传时间
	13:string fileUrl;
}

/*
 * 担保详细信息
 * 表：BIZ_PROJECT_ASS_DTL
 */
struct ProjectAssDtl{
	1:i32 pid;// 主键
	2:i32 baseId;// 担保基本信息ID
	3:i32 lookupId;// 字典ID
	4:string lookupVal;// 字典值（信息名称--name）
	5:string infoVal;// 信息值
	6:i32 status;// 状态
	7:string lookupDesc;// 文本显示值
}

/*
 * 担保共有人
 * 表：BIZ_PROJECT_ASS_OWN
 */
struct ProjectAssOwn{
	1:i32 pid;// 主键
	2:i32 baseId;// 担保基本信息ID
	3:i32 publicOwnUserId;// 共有人客户ID
	4:i32 status;// 状态
	5:string ownUserIds;// 共有人客户ID字符串(1,2,3)
	6:string acctName;// 客户名称
	7:string acctTypeText;// 客户类别
	8:i32 acctId;// 客户ID
	9:i32 cusType;// 客户类别值
	10:i32 ecoTrade;// 经济行业类别
	11:i32 page;// 页码（第几页）
	12:i32 rows;// 每页显示数量
	13:string certType;// 证件类型
	14:string certNumber;// 证件号码
}

/*
 * 抵质押物保管处理表
 * 表：BIZ_PROJECT_ASS_KEEPING
 */
 struct ProjectAssKeeping{
	1:i32 pid;// 主键
	2:i32 baseId;// 担保基本信息ID
	3:i32 saveUserId;// 保管人ID
	4:string saveUserName;// 保管人姓名
	5:i32 status;// 状态
	6:string saveDttm;// 保管时间
	7:string saveRemark;// 保管备注
	8:i32 page;// 页码（第几页）
	9:i32 rows;// 每页显示数量
	10:string itemName;// 抵质押物名称 
}

/*
 * 抵质押物提取表
 * 表：BIZ_PROJECT_ASS_EXTRACTION
 */
 struct ProjectAssExtraction{
	1:i32 pid;// 主键
	2:i32 baseId;// 担保基本信息ID
	3:i32 applyUserId;// 提取申请人ID
	4:string applyUserName;// 提取申请人姓名
	5:string applyRemark;// 提取申请备注
	6:string applyDttm;// 提取申请时间
	7:i32 handleUserId;// 提取处理人ID
	8:string handleUserName;// 提取处理人姓名
	9:string handleDttm;// 提取时间
	10:string handleRemark;// 提取处理备注
	11:i32 status;// 状态
	12:i32 page;// 页码（第几页）
	13:i32 rows;// 每页显示数量
	14:string itemName;// 抵质押物名称 
}

/*
 * 抵质押物处理表
 * 表：BIZ_PROJECT_ASS_HANDLE
 */
 struct ProjectAssHandle{
	1:i32 pid;// 主键
	2:i32 baseId;// 担保基本信息ID
	3:i32 handleUserId;// 处理人ID
	4:string handleUserName;// 处理人姓名
	5:i32 status;// 状态
	6:string handleRemark;// 处理备注
	7:string handleDttm;// 处理时间
	8:i32 page;// 页码（第几页）
	9:i32 rows;// 每页显示数量
	10:string itemName;// 抵质押物名称 
}


// Service
/*
 * 抵质押物 Service
 */
service ProjectAssBaseService {

	i32 addProjectAssBase(1:ProjectAssBase projectAssBase) throws (1:Common.ThriftServiceException e);
	i32 batchDelete(1:string pids) throws (1:Common.ThriftServiceException e);
	i32 updateProjectAssBase(1:ProjectAssBase projectAssBase) throws (1:Common.ThriftServiceException e);
	list<ProjectAssBase> getAllProjectAssBase(1:ProjectAssBase projectAssBase);
	i32 getAllProjectAssBaseCount(1:ProjectAssBase projectAssBase);
	list<ProjectAssBase> getProjectAssBaseByMortgageGuaranteeType(1:i32 mortgageGuaranteeType,2:string projectIds);
	// 办理抵质押物
	i32 transactProjectAssBase(1:ProjectAssBase projectAssBase) throws (1:Common.ThriftServiceException e);
	// 抵质押物保管处理
	i32 safekeepingProjectAssBase(1:ProjectAssBase projectAssBase) throws (1:Common.ThriftServiceException e);
	// 抵质押物提取申请
	i32 applyExtractionProjectAssBase(1:ProjectAssBase projectAssBase) throws (1:Common.ThriftServiceException e);
	// 抵质押物提取处理
	i32 applyManagetransactProjectAssBase(1:ProjectAssExtraction projectAssExtraction) throws (1:Common.ThriftServiceException e);
	// 解除抵质押物
	i32 relieveProjectAssBase(1:ProjectAssBase projectAssBase) throws (1:Common.ThriftServiceException e);
	// 批量撤销抵质押物状态(把状态修改为 等待抵质押物办理 状态)
	i32 revokeProjectAssBase(1:string pids) throws (1:Common.ThriftServiceException e);
	
	//  获取抵押物对象
	ProjectAssBase getProjectAssBaseByPid(1:i32 pid);
	// 生成合同的时候，需要根据选择的抵押物，获取同一个共有人的抵押物列表
	list<ProjectAssBase> getCommonProjectAssBaseByPid(1:i32 pid);
	
	// 更新抵押物的处理时间和备注
	i32 updateProjectAssBaseProcessing(1:ProjectAssBase projectAssBase);
	
	/** begin 担保共有人 */
	// 新增抵质押物共有人
	i32 addProjectAssOwn(1:ProjectAssOwn projectAssOwn) throws (1:Common.ThriftServiceException e);
	// 删除抵质押物共有人-根据抵质押物ID
	i32 deleteProjectAssOwn(1:i32 baseId) throws (1:Common.ThriftServiceException e);
	// 批量删除抵质押物共有人-根据主键
	i32 batchDeleteProjectAssOwn(1:string pids) throws (1:Common.ThriftServiceException e);
	// 查询抵质押物共有人-根据抵质押物ID和所有人类型
	list<ProjectAssOwn> getProjectAssOwnByBaseId(1:i32 baseId,2:i32 ownType);
	// 查询抵质押物共有人-根据类型ID和所有人类型
	list<ProjectAssOwn> getProjectAssOwnByRelationId(1:string relaIds,2:i32 ownType);
	// 查询所有抵质押物共有人信息
	list<ProjectAssOwn> getAllProjectAssOwnByOwnType(1:ProjectAssOwn projectAssOwn);
	// 查询所有抵质押物共有人数量
	i32 getAllProjectAssOwnByOwnTypeCount(1:ProjectAssOwn projectAssOwn);
	/** end 担保共有人 */
	
	/**  bigin 担保详情的接口  */
	// 根据当前抵质押物ID获取抵质押物详情信息
	list<ProjectAssDtl> getProjectAssDtlByBaseId(1:i32 baseId);
	
	
	/** end 担保详情接口  */
	/** 抵质押资料上传 */
	list<ProjectAssFile> getProjectAssFile(1:i32 baseId,2:string fileType) throws (1:Common.ThriftServiceException e);
	
	i32 saveProjectAssFile(1:ProjectAssFile projectAssFile) throws (1:Common.ThriftServiceException e);
	
	i32 editProjectAssFile(1:ProjectAssFile projectAssFile) throws (1:Common.ThriftServiceException e);
	
	i32 delProjectAssFile(1:string pids) throws (1:Common.ThriftServiceException e);
}

/*
 * 抵质押物操作 Service
 */
service ProjectAssBaseOperationService{
	// 查询所有有效的抵质押物保管信息
	list<ProjectAssKeeping> getAllProjectAssKeeping(1:ProjectAssKeeping projectAssKeeping);
	// 查询所有有效的抵质押物保管信息的数量
	i32 getAllProjectAssKeepingCount(1:ProjectAssKeeping projectAssKeeping);
	// 批量删除抵质押物保管信息
	i32 batchDeleteProjectAssKeeping(1:string pids) throws (1:Common.ThriftServiceException e);
	// 查询当前抵质押物的最新的保管信息
	ProjectAssKeeping getProjectAssKeepingByBaseId(1:i32 baseId);
	// 查询当前抵质押物的所有保管信息
	list<ProjectAssKeeping> getListProjectAssKeepingByBaseId(1:i32 baseId);
	
	// 查询所有有效的抵质押物提取信息
	list<ProjectAssExtraction> getAllProjectAssExtraction(1:ProjectAssExtraction projectAssExtraction);
	// 查询所有有效的抵质押物提取信息的数量
	i32 getAllProjectAssExtractionCount(1:ProjectAssExtraction projectAssExtraction);
	// 查询当前抵质押物的所有提取信息
	list<ProjectAssExtraction> getListProjectAssExtractionByBaseId(1:i32 baseId);
	// 批量删除抵质押物提取信息
	i32 batchDeleteProjectAssExtraction(1:string pids) throws (1:Common.ThriftServiceException e);
	
	// 查询所有有效的抵质押物处理信息
	list<ProjectAssHandle> getAllProjectAssHandle(1:ProjectAssHandle projectAssHandle);
	// 查询所有有效的抵质押物处理信息的数量
	i32 getAllProjectAssHandleCount(1:ProjectAssHandle projectAssHandle);
	// 批量删除抵质押物处理信息
	i32 batchDeleteProjectAssHandle(1:string pids) throws (1:Common.ThriftServiceException e);
	
	
}


