namespace java com.xlkfinance.bms.rpc.system
include "Common.thrift"

/**
 *系统磁贴菜单表实体类
 */
struct SysMetroUiVo{
	1: i32 pid; 
	2: i32 locationId; //显示位置序号
	3: string showImg;	//显示图片
	4: string actionUrl;//动作URL
	5: string menuName;	//菜单名称
	6: i32 iconCls;	//图标大小
	7: i32 menuId; //菜单Id
	8: i32 parentId; //父项目ID
	9: i32 status;		
	10: i32 page;
	11: i32 rows;
}

/**
 *	修改密码需要用到的传参类
 */
struct EidtPassword{
	1: string oldPwd;	//旧密码
	2: string newPwd;	//新密码
	3: i32 uid;		//用户ID
}

/*
 * 数据字典信息
 * 表：SYS_LOOKUP
 */
struct SysLookup {
        1: i32 pid;
        2: string lookupType;
        3: string lookupName; 
        4: string lookupDesc;
        5: i32 status; 
		6: i32 page;
		7: i32 rows;
		8: string createDatetime;
}


/* 字典值
 * 表：SYS_LOOKUP_VAL
 */
struct SysLookupVal {
        1: i32 pid;
        2: i32 lookupId;
        3: string lookupVal;  
        4: string lookupDesc;
        5: i32 status;       
}
/* 系统功能权限
 * 表：SYS_PERMISSION
 */
struct SysPermission {
        1: i32 pid;
        2: string permisType;
        3: string permisName;
        4: string permisDesc;   
        5: string permisCode;   
        6: i32 menuId;
        7: i32 rows;
        8: i32 page;      
}

/* 系统角色
 * 表：SYS_ROLE
 */
struct SysRole {
        1: i32 pid;
        2: string roleName;
        3: string roleDesc;   
        4: string roleCode;
        5: i32 page;
        6: i32 rows;
        7: i32 parentId;//从属角色
}
/*
 * 系统机构
 * 表：SYS_ORG_INFO
 *
 */
struct SysOrgInfo {
 	1: i32 id;//机构编号
 	2: i32 parentId;//父级机构编号
 	3: string name;//机构名称
 	4: string code;//机构编码
 	5: i32 category;//部门类型,1表示业务线,2表示风控线,3表示财务线,默认为1
 	6: i32 layer;//机构层级
 	7: i32 status;
 	8: i32 page;
 	9: i32 rows;
}

/** 系统用户
 * 表：SYS_USER
 */
struct SysUser {
        1: i32 pid;
        2: string userName;
        3: string realName;
        4: string memberId; 
        5: i32 status;
        6: string department;
        7: string jobTitle;
        8: string mail;
        9: string pwd;
        10: string photoUrl;  
        11: string token;
        12: string personalQQ;
        13: string enterpriseQQ;
        14: string phone;
        15: string workPhone;
        16: string extension;
        17: i32 superiorId;//直属上级Id
        18: string superiorName;//直属上级名称
        19: i32 orgId;//所属机构Id
        20: i32 page; 
        21: i32 rows;
        22: string deviceToken;
        23: string orgName;
        24: list<SysRole> roles;
        25: list<i32> userIds;//查询使用
        26: string roleCode;//查询使用
        
}
/*
 * 系统用户数据权限
 * 表:SYS_USER_ORG_INFO
 *
 */
struct SysUserOrgInfo {
	1:	i32 id;
	2:	i32 orgId;
	3:  i32 userId;
	4:  i32 category;//用户类型,用户所属的机构类型即用户类型
	5:	i32 dataScope;//数据范围,1表示私有数据,2表示集体数据
	6:	i32 page;
	7:	i32 rows;
}
/*
* 数据权限展示对象
*/
struct SysDataAuthView{
	1: i32 id;
	2: i32 userId;
	3: string userName;
	4: string orgName;
	5: string dataName;
	6: string category;
	7: string dataScope;
	8: i32 page;
	9: i32 rows;
}
/*
  *用于角色配置用户的模糊查询传参
  */
struct SysUserRoleSearch {
	1: i32 rid;	//角色ID
	2: string userName; //用户名
	3: string memberId;	//员工工号
	4: i32 page;
	5: i32 rows;
	6: i32 uid; //用户ID
	7: string roleName;	//角色名称
}

/*
 *系统用户
 *表：sys_user
 */
struct SysUserDto {
        1: i32 pid;
        2: string userName;
        3: string realName;
        4: string memberId; 
        5: i32 status;
        6: string department;
        7: string jobTitle;
        8: string mail;
        9: string pwd;
        10: string photoUrl;  
        11: string token;
        12: string personalQQ;
        13: string enterpriseQQ;
        14: string phone;
        15: string workPhone;
        16: string extension;
        17: string roleName;
        18: string roleDesc;
}

/* 角色-权限关系表
 * 表：SYS_ROLE_PERMISSION
 */
struct SysRolePermission {
        1: i32 pid;
        2: i32 roleId;
        3: i32 permisId;
        4: i32 status;   
}

/* 用户角色关系表
 * 表：SYS_USER_ROLE
 */
struct SysUserRole {
        1: i32 pid;
        2: i32 userId;
        3: i32 roleId;
        4: i32 status;   
}

/* 系统日志
 * 表：SYS_LOG
 */
struct SysLog {
        1: i32 pid;
        2: string modules;
        3: string logType;
        4: string logDateTime;
        5: i32 userId;
        6: string logMsg;
        7: i32 rows;
        8: i32 page;
        9: string realName;
        10: i32 projectId;
        11: string projectName;
        12: string ipAddress;//
        13: string browser;//
}

/* 系统菜单
 * 表：SYS_MENU
 */
struct SysMenu {
        1: i32 pid;
        2: i32 parentId;
        3: string menuName;
        4: string iconCls;
        5: string menuUrl;
        6: i32 status;
        7: i32 menuIndex;
        8: bool open;
}

/* 系统参数
 * 表：SYS_CONFIG
 */
struct SysConfig {
        1: i32 pid;
        2: string configName;
        3: string configVal;
        4: i32 status;
        5: string description;
        6: i32 page;
        7: i32 rows;
}

struct SysUserRoleDTO {
		1: SysUserRole sysUserRole;
		2: list<SysPermission> sysPermissionList;
}

/*
 *pengchuntao,文件信息 
 *表：BIZ_FILE
 */
struct BizFile {
	1: i32 pid;
	2: string fileName;
	3: string fileType;
	4: i32 fileSize;
	5: string uploadDttm;
	6: i32 uploadUserId;
	7: string fileUrl;
	8: i32 status;
	9: string remark;
	10: i32 page=1; //第几页
	11: i32 rows=10; //总页数
}

/*
 *xuweihao,模板信息
 */
struct TemplateFile {
	1: i32 pid;
	2: string fileName;
	3: i32 fileType;
	4: i32 fileSize;
	5: string uploadDttm;
	6: string uploadUser;
	7: string fileUrl;
	8: i32 status;
	9: string filePropertyName;
	10: i32 fileProperty;//模板类型
	11: string fileLookupVal;//通过val找到对应类型文件
	12: i32 fileId;
	13:i32 page; // 页码（第几页）
	14:i32 rows; // 每页显示数量
}


/*
 *xuweihao,模板信息分页
 */
struct TemplateFileCount {
	1: i32 pid;
	2: string fileName;
	3: i32 fileType;
	4: i32 fileSize;
	5: string uploadDttm;
	6: string uploadUser;
	7: string fileUrl;
	8: i32 status;
	9: string filePropertyName;
	10: i32 fileProperty;//模板类型
	11: string fileLookupVal;//通过val找到对应类型文件
	12: i32 fileId;
	13:i32 page; // 页码（第几页）
	14:i32 rows; // 每页显示数量
	15: string fromUploadDttm;//上传开始时间(仅做查询)
	16: string endUploadDttm;//上传结束时间(仅做查询)
}
/**
 *	系统用户签到信息
 */
struct SysUserSign{
	1: i32 pid;	//签到编号
	2: i32 userId;	//用户编号
	3: string createDate;//签到日期
}
/**
 *	邮箱 
 */
struct SysMailInfo{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
    3: string creatorDate;//创建时间
    4: i32 creatorId;//创建人id
	5: i32 pid;	//
	6: string subject;//主题
	7: string content;//内容
	8: string sendMail;//发送邮箱
	9: string recMail;//接收邮箱
	10: i32 status;//
	11: string ipAddress;//
	12: i32 category;//
}
/**
 *	问题反馈 SYS_PROBLEM_FEEDBACK
 */
struct ProblemFeedback{
    1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
    3: string createrDate;//创建时间
    4: i32 createrId;//创建人id
	5: i32 pid;	//
	6: string feedbackContent;//反馈内容
	7: i32 problemSource;//问题来源：PC=1,安卓=2,IOS=3
}
/**
 *	广告信息 SYS_ADV_PIC_INFO
 */
struct SysAdvPicInfo{
	1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
    3: string createDate;//创建时间
    4: i32 createId;//创建人id
	5: i32 pid;	//
	6: string title;//标题
	7: string content;//内容
	8: string url;//跳转地址
	9: i32 orgId;//城市
	10: i32 status;//状态
	11: string remark;//备注
	12: string pictureUrl;//图片地址
	13: i32 orderIndex;//显示顺序
}

/**
 *	银行信息 SYS_BANK_INFO
 */
struct SysBankInfo{
	1: i32 page=1; //第几页
	2: i32 rows=10; //总页数
	3: i32 pid;//主键
	4: i32 parentId;//从属ID
	5: string bankName;//银行名称
	6: i32 type;//类型
	7: string bankShortHand;//银行简称
	8: i32 status;//状态1：显示2：不显示
	9:list<SysBankInfo> sysBanks;
}
/**
 *	银行信息 SYS_BANK_INFO返回结果
 */
struct SysBankInfoDto{
	1: i32 pid;//主键
	2: string bankName;//银行名称
}


/**
 * 系统地区信息
 * chenzhuzhen
 */
struct SysAreaInfo{
	1: string areaCode; 	//地区编码 
	2: string areaName; 	//地区名称 
	3: string parentCode;	//父级编码 
	4: string levelNo;		//级别 1:省 2：市 3：区 
	5: string areaTelCode;	//电话号码区号 
	6: string center;		//定位中心点 
	7: string oldAreaCode; //数据库原地区编码
	8: i32 page=1; //第几页
	9: i32 rows=10; //总页数 
}





/**
 * 数据字典Service
 */
service SysLookupService {
	/**
	  * @Description: 添加数据字典
	  * @param sysLookup
	  * @return int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 下午5:11:34
	  */
	i32 addSysLookup(1:SysLookup sysLookup) throws (1:Common.ThriftServiceException e);
	/**
	  * @Description: 批量删除数据字典配置
	  * @param SysLookuPids pid列表
	  * @return int
	  * @author: Dai.jingyu
	  * @date: 2015年3月11日 下午2:51:26
	  */
	i32 deleteLookup(1:list<i32> SysLookuPids) throws (1:Common.ThriftServiceException e);
	/**
	  * @Description: 根据主键更新对象
	  * @return sysLookup
	  * @author: Dai.jingyu
	  * @date: 2015年3月11日 下午2:51:26
	  */
	i32 updateSysLookup(1:SysLookup sysLookup) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 条件查询所有的数据字典配置
	 * @param sysLookup  数据字典配置对象
	 * @return 数据字典配置集合
	 * @author: Mr.Cai
	 * @date: 2014年12月24日 下午4:23:38
	 */
	list<SysLookup> getAllSysLookup(1:SysLookup sysLookup);
	/**
	  * @Description: 根据数据字典条件查询 数据总记录条数
	  * @param sysLookup
	  * @return int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 下午5:11:34
	  */
	i32 getAllSysLookupSum(1:SysLookup sysLookup) throws (1:Common.ThriftServiceException e);
	string getSysLookupValByName(1:i32 pid);
	string getSysLookupValByPid(1:i32 pid);
	/**
	 * @Description: 根据数据字典类型查询当前数据类型的值
	 * @param lookupType  数据字典类型
	 * @return 数据字典值集合
	 * @author: Cai.Qing
	 * @date: 2014年12月29日 上午11:24:40
	 */
	list<SysLookupVal> getSysLookupValByLookType(1:string lookupType);
	/**
	 * @Description: 获取当前数据字典下面的值集合
	 * @param lookupId 数据字典ID
	 * @return 数据字典值集合 List<SysLookupVal>
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 上午11:56:02
	 */
	list<SysLookupVal> getSysLookupValByLookupId(1:i32 lookupId,2:i32 page,3:i32 rows);
	/**
	  * @Description: 获取当前数据字典下面的值集合
	  * @param lookupId 数据字典ID
	  * @return int 总计录条数
	  * @author: JingYu.Dai
	  * @date: 2015年6月9日 上午11:45:37
	  */
	i32 getSysLookupValByLookupIdTotal(1:i32 lookupId);
	/**
	 * @Description: 删除数据字典值
	 * @param lookupValPids 数据字典pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:00:56
	 */
	i32 deleteLookupVal(1:string lookupValPids) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 添加数据字典值
	 * @param lookupValPids 数据字典pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:00:56
	 */
	i32 addSysLookupVal(1:SysLookupVal sysLookupVal) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 根据主键更新数据字典值
	 * @param lookupValPids 数据字典pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2014年12月26日 下午12:00:56
	 */
	i32 updateSysLookupVal(1:SysLookupVal sysLookupVal) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 根据客户类型查询资料类型
	 * @param projectId
	 * @return List<SysLookupVal>
	 * @author: xuweihao
	 * @date: 2015年3月30日 上午9:49:40
	 */
	list<SysLookupVal> getDataTypeSysLookup(1:i32 projectId);
	/**
	 * @Description: 抵质押物所需查询的详细信息数据
	 * @param lookupType 抵质押物类型值
	 * @return 抵质押物当前类型的详细信息集合
	 * @throws TException
	 * @author: Cai.Qing
	 * @date: 2015年4月23日 上午1:53:37
	 */
	list<SysLookupVal> getProjectAssDtlByLookType(1:string lookupType);
	
	
	/**
	 * @Description: 根据数据字典类型,和字典值     查询当前字典对象
	 * @param lookupType  数据字典类型  (sys_lookup)
	 * @param lookupVal  数据字典类型   (sys_lookup_val)
	 * @return 数据字典
	 * @author: chenzhuzhen
	 * @date: 2016年07月07日 上午11:24:40
	 */
	SysLookupVal getSysLookupValByChildType(1:string lookupType,2:string lookupVal);
	
	/**
	 * @Description: 根据项目类型查询资料类型
	 * @param projectId
	 * @return List<SysLookupVal>
	 * @author: baogang
	 * @date: 2017年1月17日 上午9:49:40
	 */
	list<SysLookupVal> getDataTypeByType(1:string projectTypes);
}

service SysPermissionService {
	i32 addSysPermission(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	i32 deleteSysPermission(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	i32 deleteSysPermissionByMenuId(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	i32 updateSysPermission(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	i32 updateSysPermissionByMenuId(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	list<SysPermission> getSysPermissionList(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	list<SysPermission> getSysPermissionByParameter(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	list<SysRole> getPermisRoleList(1:i32 pageNum,2:i32 pageSize,3:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	i32 getPermisRoleListTotal(1:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	list<SysRole> getNotHaveRoleOfPermisList(1:i32 pageNum,2:i32 pageSize,3:SysPermission sysPermission) throws (1:Common.ThriftServiceException e);
	i32 deleteRoleOfPermisById(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	i32 addRoleOfPermisById(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	
	i32 getSysPermissionCount(1:SysPermission sysPermission);
	
	i32 srarchSysPermissionCount(1:SysPermission sysPermission);
	
	SysPermission getPermisByName(1:string permisName);
	
}

service SysRoleService {
	SysRole addSysRole(1:SysRole sysRole) throws (1:Common.ThriftServiceException e);
	i32 deleteSysRole(1:list<i32> pid) throws (1:Common.ThriftServiceException e);
	i32 updateSysRole(1:SysRole sysRole) throws (1:Common.ThriftServiceException e);
	list<SysRole> getSysRoleList() throws (1:Common.ThriftServiceException e);
	list<SysRole> getAllRolesForPage(1:SysRole sysRole) throws (1:Common.ThriftServiceException e);
	list<SysPermission> getSysPermissionList(1:SysRole sysRole,2:string permisName) throws (1:Common.ThriftServiceException e);
	i32 getSysPermissionListCount(1:SysRole sysRole,2:string permisName) throws (1:Common.ThriftServiceException e);
	list<SysPermission> searchRoleNotHavePermis(1:SysRole sysRole,2:string permisName) throws (1:Common.ThriftServiceException e);
	i32 searchRoleNotHavePermisCount(1:SysRole sysRole,2:string permisName) throws (1:Common.ThriftServiceException e);
	list<SysRole> searchRoleListByRoleName(1:SysRole sysRole)throws (1:Common.ThriftServiceException e);
	i32 deleteSysPermisOfRole(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	i32 deletePermisOfRoleById(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	i32 addPermisToRole(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	
	
	list<SysUser> getRoleUserListByRoleId(1:SysRole sysRole,2:string realName) throws (1:Common.ThriftServiceException e);
	i32 getSysUserByRoleIdCount(1:SysRole sysRole,2:string realName) throws (1:Common.ThriftServiceException e);
	
	
	
	i32 delUserToRole(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	
	bool addRoleToUser(1:i32 userId,2:list<i32> roleId) throws (1:Common.ThriftServiceException e);
	
	bool addUserToRole(1:i32 roleId,2:list<i32> userId) throws (1:Common.ThriftServiceException e);
	
	list<SysRole> getRoleByUserId(1:i32 pageNum,2:i32 pageSize,3:SysUser sysUser);
	
	list<SysUser> getRoleNoUserList(1:SysUserRoleSearch sysUserRoleSearch) throws (1:Common.ThriftServiceException e);

	i32 getRoleNoUserListCount(1:SysUserRoleSearch sysUserRoleSearch) throws (1:Common.ThriftServiceException e);
	
	list<SysRole> searchUserNotRoleList(1:SysUserRoleSearch sysUserRoleSearch) throws (1:Common.ThriftServiceException e);
	
	i32 searchUserNotRoleListCount(1:SysUserRoleSearch sysUserRoleSearch) throws (1:Common.ThriftServiceException e);
	
	list<SysUserDto> getUserListByRoleCode(1:SysRole sysRole);
	
	i32 getAllRolesForPageCount();
	
	i32 searchRoleListCount(1:SysRole sysRole);
	
	list<SysRole> queryRolesByRoleCodes(1:list<string> rolesStr) throws (1:Common.ThriftServiceException e);
	
	SysRole getRoleById(1:i32 pid);
	
	list<SysRole> getRoleIdListByUserName(1:string userName,2:i32 pageNum,3:i32 pageSize);
	i32 getRoleIdListByUserNameCount(1:string userName);
}

service SysUserService {
	i32 addSysUser(1:SysUser sysUser) throws (1:Common.ThriftServiceException e);
	i32 deleteByUserName(1:string userNames) throws (1:Common.ThriftServiceException e);
	i32 batchUpdate(1:string userNames) throws (1:Common.ThriftServiceException e);
	i32 updateSysUser(1:SysUser sysUser) throws (1:Common.ThriftServiceException e);
	i32 updateSysUserPwd(1:SysUser sysUser) throws (1:Common.ThriftServiceException e);
	SysUser querySysUserByName(1:string userName);
	i32 updateToken(1:i32 userId,2:string token);
	SysUser getSysUserByPhone(1:string phone);
	SysUser getSysUserByMemberId(1:string memberId);
	list<SysUser> getSysUserList();
	list<SysUser> getSysUserByParameter(1:map<string,string> parameter);
	list<SysPermission> getSysUserPermissionByUserName(1:string userName);
	list<SysMenu> getPermissionMenuByUserName(1:string userName);
	list<SysMenu> getRootMenuByUserName(1:string userName);
	list<SysMenu> getChildMenuByUserName(1:string userName,2:i32 parentId);
	list<SysUser> getAllSysUser(1:SysUser sysUser);
	i32 getAllSysUserCount(1:SysUser sysUser);
	
	list<SysUser> getAllSysUserNormal(1:SysUser sysUser);
	i32 getAllSysUserNormalCount(1:SysUser sysUser);
	list<SysUser> getSysUserByPids(1:list<string> pids);
	SysUser getSysUserByPid(1:i32 pid);
	SysUser getUserDetailsByPid(1:i32 pid);
	void eidtPassword(1:EidtPassword eidtPassword);
	bool checkMemberIdIsExist(1:string memberId) throws (1:Common.ThriftServiceException e);
	bool checkUserNameIsExist(1:string userName) throws (1:Common.ThriftServiceException e);
	list<SysUser> getUsersByRoleCode(1:string roleCode);
	SysUser getuperiorSysUserByPid(1:i32 pid) throws (1:Common.ThriftServiceException e);
	i32 deleteByUserId(1:i32 userId) throws (1:Common.ThriftServiceException e);
	/**
	  * @Description: 根据机构id获取用户
	  * @param orgIds 机构编号
	  * @return  list<Integer> userIds
	  * @author: huxinlong
	  * @date: 2016年1月12日 下午4:55:03
	 */
	 list<i32> getUsersByOrgId(1:list<i32> orgIds) throws (1:Common.ThriftServiceException e);
	 list<SysUser> getUsersByOrgIdAndRoleCode(1:i32 orgId,2:string roleCode) throws (1:Common.ThriftServiceException e);
	 list<SysUser> getAcctManagerByLogin(1:list<i32> userIds) throws (1:Common.ThriftServiceException e);
	 /**
	  * @Description: 根据角色编码以及机构ID查询该机构树下所有的角色人员
	  * @param orgId 机构编号
	  * @return  list<SysUser>
	  * @author: baogang
	  * @date: 2017年1月4日 下午4:55:03
	 */
	 list<SysUser> getUserByOrg(1:i32 orgId,2:string roleCode) throws (1:Common.ThriftServiceException e);
}

service SysRolePermissionService {
	i32 addSysRolePermission(1:SysRolePermission SysRolePermission) throws (1:Common.ThriftServiceException e);
	i32 deleteSysRolePermission(1:SysRolePermission SysRolePermission) throws (1:Common.ThriftServiceException e);
	list<SysRolePermission> getSysRolePermissionByParameter(1:map<string,string> parameter);
}

service SysUserRoleService {
	i32 addSysUserRole(1:SysUserRole SysUserRole) throws (1:Common.ThriftServiceException e);
	i32 deleteSysUserRole(1:SysUserRole SysUserRole) throws (1:Common.ThriftServiceException e);
	list<SysUserRole> getSysUserRoleByParameter(1:map<string,string> parameter);
}

/**
 * @Title: SysLogMapper.java 
 * @Description: 业务日志 service
 * @author : Mr.Cai
 * @date : 2014年12月24日 下午1:54:20 
 * @email: caiqing12110@vip.qq.com
 */
service SysLogService {
	/**
	  * @Description: 新增系统日志
	  * @param sysLog 业务日志对象
	  * @return  int 总记录条数
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	 */
	i32 addSysLog(1:SysLog sysLog) throws (1:Common.ThriftServiceException e);
	/**
	  * @Description: 条件查询业务日志总记录条数
	  * @param sysLog 业务日志对象
	  * @return  int 总记录条数
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	  */
	i32 batchDelete(1:string logPids) throws (1:Common.ThriftServiceException e);
	/**
	  * @Description: 根据主键更新对象业务日志
	  * @param sysLog 业务日志对象
	  * @return  int 
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	  */
	i32 updateSysLog(1:SysLog sysLog) throws (1:Common.ThriftServiceException e);
	list<SysLog> getSysLogList();
	list<SysLog> getSysLogByParameter(1:map<string,string> parameter);
	/**
	 * @Description: 条件查询业务日志
	 * @param sysLog 业务日志对象
	 * @return 业务日志集合
	 * @author: Mr.Cai
	 * @date: 2014年12月24日 下午2:20:38
	 */
	list<SysLog> getAllSysLog(1:SysLog sysLog);
	/**
	  * @Description: 条件查询业务日志总记录条数
	  * @param sysLog 业务日志对象
	  * @return  int 总记录条数
	  * @author: Dai.jingyu
	  * @date: 2015年3月21日 下午4:55:03
	 */
	i32 getAllSysLogTotal(1:SysLog sysLog);
}

/**
  * @ClassName: SysMenuServiceImpl
  * @Description: 菜单
  * @author: JingYu.Dai
  * @date: 2015年7月29日 上午11:06:55
 */
service SysMenuService {
   /**
     * @Description: 增加权限
     * @param sysPermission
     * @return
     * @author: Chong.Zeng
     * @date: 2015年1月30日 下午3:06:40
     */
	i32 addSysMenu(1:SysMenu sysMenu) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 删除菜单
	 * @param parameter,map
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	i32 deleteSysMenu(1:SysMenu sysMenu) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 修改菜单
	 * @param parameter,map
	 * @return int
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	i32 updateSysMenu(1:SysMenu sysMenu) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 下拉框专用
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月25日 下午15:06:02
	 */
	list<SysMenu> getSysMenuList();
	/**
	 * @Description: 分页查询系统菜单
	 * @param parameter,map
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月23日 下午17:06:02
	 */
	string getMenuPageList(1:map<string,string> parameter);
	/**
	 * @Description: 获取所有根节点
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月24日 下午15:06:02
	 */
	string getTreeMenu(1:map<string,string> parameter);
	/**
	 * @Description: 分页模糊查询系统菜单
	 * @param parameter,map
	 * @return List<SysMenu>
	 * @author: Chong.Zeng
	 * @date: 2014年12月23日 下午17:06:02
	 */
	list<SysMenu>searchMenuListByName(1:map<string,string> parameter);
	/**
	  * @Description: 查询所有跟菜单
	  * @return List<SysMenu>
	  * @author: JingYu.Dai
	  * @date: 2015年5月5日 下午2:37:08
	  */
	list<SysMenu> getRootMenuList() throws (1:Common.ThriftServiceException e);
	/**
	  * @Description: 根据pid 查询当前菜单下的子菜单
	  * @param pid 
	  * @return List<SysMenu>
	  * @author: JingYu.Dai
	  * @date: 2015年5月5日 下午2:54:52
	 */
	list<SysMenu> selectChildMenuByPid(1:i32 pid) throws (1:Common.ThriftServiceException e);
}

/**
 * @Title: SysConfigMapper.java
 * @Description: 系统配置 mapper
 * @author : Mr.Cai
 * @date : 2014年12月19日 下午4:21:52
 * @email: caiqing12110@vip.qq.com
 */
service SysConfigService {
	/**
	 * @Description: 条件查询所有的系统配置
	 * @param sysConfig 系统配置对象
	 * @return 系统配置集合
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午4:20:38
	 */
	list<SysConfig> getAllSysConfig(1:SysConfig sysConfig);
	/**
	  * @Description: 条件查询所有的系统配置总记录条数
	  * @param sysConfig
	  * @return int 
	  * @author: JingYu.Dai
	  * @date: 2015年6月5日 下午3:19:32
	  */
	i32 getAllSysConfigTotal(1:SysConfig sysConfig);
	/**
	 * @Description: 添加系统配置
	 * @param sysConfig 系统配置对象
	 * @return int
	 * @author: Mr.Cai
	 * @date: 2014年12月19日 下午4:20:38
	 */
	i32 addSysConfig(1:SysConfig sysConfig) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 批量删除系统配置
	 * @param configPids
	 * @return
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午4:43:30
	 */
	i32 batchDelete(1:string configPids) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 根据主键更改数据系统配置
	 * @param configPids
	 * @return int
	 * @author: Mr.Cai
	 * @date: 2014年12月23日 下午4:43:30
	 */
	i32 updateSysConfig(1:SysConfig sysConfig) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description: 根据名称获取系统配置
	 * @param configName 系统配置名称
	 * @return 系统配置对象
	 * @author: Cai.Qing
	 * @date: 2015年1月16日 下午2:46:40
	 */
	SysConfig getByConfigName(1:string configName);
	/**
	 * @Description: 根据list对象查询
	 * @param listSysConfig list对象
	 * @return 系统配置集合 List<SysConfig>
	 * @author: Cai.Qing
	 * @date: 2015年2月2日 下午2:50:04
	 */
	list<SysConfig> getListByListSysConfig(1:list<SysConfig> listSysConfig);
	/**
	 * 根据系统参数名称获取对应的值
	  * @param name
	  * @return String
	  * @author: qiancong.Xian
	  * @date: 2015年7月7日 下午4:35:53
	 */
	string getSysConfigValueByName(1:string name);
	
}

service SysMetroUiService {
	i32 insertSysMetroUi(1: SysMetroUiVo sysMetroUiVo) throws (1:Common.ThriftServiceException e);
	i32 updateSysMetroUi(1: SysMetroUiVo sysMetroUiVo) throws (1:Common.ThriftServiceException e);
	SysMetroUiVo getSysMetroUiByPid(1:i32 pid) throws (1:Common.ThriftServiceException e);
	list<SysMetroUiVo> findSysMetroUiPage(1: SysMetroUiVo sysMetroUiVo) throws (1:Common.ThriftServiceException e);
	i32 findSysMetroUiTotal() throws (1:Common.ThriftServiceException e);
	i32 deleteSysMetroUi(1:i32 pid) throws (1:Common.ThriftServiceException e);
	list<SysMetroUiVo> selectMetroUiByUserName(1: string userName)
}

/**   
* @Title: BizFileServiceImpl.java 
* @Package com.xlkfinance.bms.server.system.service 
* @Description: 文件信息服务类，数据库表：BIZ_FILE  
* @author Ant.Peng   
* @date 2015年2月6日 下午6:06:47 
* @version V1.0   
*/ 
service BizFileService {
	/**
	 * @Description:  保存文件信息
	 * @return int
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	i32 saveBizFile(1:BizFile bizFile) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description:  删除文件信息 根据主键ID
	 * @return int
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	i32 deleteBizFileByPid(1:i32 pid) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description:  更新文件信息
	 * @return int
	 * @author: pengchuntao
	 * @date: 2014年1月15日
	 */
	i32 updateBizFile(1:BizFile bizFile) throws (1:Common.ThriftServiceException e);
	i32 obtainBizFile(1:BizFile bizFile) throws (1:Common.ThriftServiceException e);
	list<BizFile> findAllBizFile(1:BizFile bizFile) throws (1:Common.ThriftServiceException e);
	BizFile getBizFileById(1:i32 id) throws (1:Common.ThriftServiceException e);
	BizFile getBizFileByUrl(1:string fileUrl) throws (1:Common.ThriftServiceException e);
}

service TemplateFileService {
	list<TemplateFile> listTemplateFile(1:TemplateFileCount templateFileCount) throws (1:Common.ThriftServiceException e);
	i32 listTemplateFileCount(1:TemplateFileCount templateFileCount) throws (1:Common.ThriftServiceException e);
	
	i32 saveTemplateFile(1:TemplateFile templateFile) throws (1:Common.ThriftServiceException e);
	
	i32 updateTemplateFile(1:TemplateFile templateFile) throws (1:Common.ThriftServiceException e);
	
	i32 delTemplateFile(1:i32 pid) throws (1:Common.ThriftServiceException e);
	
	TemplateFile getTemplateFile(1:string fileLookupVal) throws (1:Common.ThriftServiceException e);
}
/**   
* @Title: SysOrgInfoServiceImpl.java 
* @Package com.xlkfinance.bms.server.system.service 
* @Description: 文件信息服务类，数据库表：sys_org_info  
* @author huxinlong   
* @date 2016年1月11日 下午6:06:47 
* @version V1.0   
*/ 
service SysOrgInfoService {

	/**
	 * @Description:  新增机构
	 * @return int
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	i32 saveSysOrgInfo(1:SysOrgInfo sysOrgInfo) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description:  更新机构
	 * @return int
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	i32 updateSysOrgInfo(1:SysOrgInfo sysOrgInfo) throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  删除机构
	 * @return int
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	i32 delSysOrgInfo(1:i32 orgId) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description:  根据机构Id获取机构信息
	 * @return SysOrgInfo
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	SysOrgInfo getSysOrgInfo(1:i32 orgId) throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  根据parentId获取机构列表
	 * @return List<SysOrgInfo>
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	list<SysOrgInfo> listSysOrgInfo(1:i32 parentId) throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  根据category(机构类型)机构列表
	 * @return List<SysOrgInfo>
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	list<SysOrgInfo> allSysOrgInfoList(1:i32 category) throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  根据父级机构id和机构名称获取机构信息(用于判断在同一机构下，子机构是否重复)
	 * @return SysOrgInfo
	 * @author: huxinlong
	 * @date: 2016年1月12日
	 */
	 SysOrgInfo getSysOrgInfoByName(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  根据父级机构id和机构名称判断此机构是否存在(用于判断在同一机构下，子机构是否重复)
	 * @return bool true:表示存在,false:不存在
	 * @author: huxinlong
	 * @date: 2016年1月12日
	 */
	 bool isExistSysOrgInfoByName(1:map<string,string> parameter) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description:  获取机构列表(根据layer,category,id,parent_id)
	 * @return List<SysOrgInfo>
	 * @author: huxinlong
	 * @date: 2016年1月14日
	 */
	 list<SysOrgInfo> listSysOrgByLayer(1:SysOrgInfo sysOrgInfo) throws (1:Common.ThriftServiceException e);
	/**
	 * @Description:  获取父级机构列表(根据layer,id,name)
	 * @return List<SysOrgInfo>
	 * @author: huxinlong
	 * @date: 2016年1月26日
	 */
	 list<SysOrgInfo> listSysParentOrg(1:SysOrgInfo sysOrgInfo) throws (1:Common.ThriftServiceException e);
	 	/**
	 * @Description:  更新机构树
	 * @return int
	 * @author: Jony 
	 * @date: 2017年5月11日
	 */
	i32 updateApplicationTree(1:SysOrgInfo sysOrgInfo) throws (1:Common.ThriftServiceException e);
	
	 
}

/**   
* @Title: SysUserOrgInfoServiceImpl.java 
* @Package com.xlkfinance.bms.server.system.service 
* @Description: 用户数据权限类(用户机构关联信息),数据库表：sys_user_org_info  
* @author huxinlong   
* @date 2016年1月11日 下午6:06:47 
* @version V1.0   
*/ 
service SysUserOrgInfoService {
	
	/**
	 * @Description:  新增用户数据权限
	 * @return int
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	i32 saveUserOrgInfo(1:SysUserOrgInfo sysUserOrgInfo) throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  删除用户数据权限
	 * @return int
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */
	i32 deleteUserOrgInfo(1:SysUserOrgInfo sysUserOrgInfo)	throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  根据用户Id获取数据权限列表
	 * @return List<SysUserOrgInfo>
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */	
	list<SysUserOrgInfo> listUserOrgInfo(1:i32 userId) throws (1:Common.ThriftServiceException e);
	
	/**
	 * @Description:  根据机构Id获取数据权限列表
	 * @return List<SysUserOrgInfo>
	 * @author: huxinlong
	 * @date: 2016年1月11日
	 */	
	list<SysUserOrgInfo> listUserOrgInfoByOrgId(1:list<i32> orgIds) throws (1:Common.ThriftServiceException e);
	
	
	/**
	 * 根据指定条件获取获取单条用户数据权限对象
	 * @param userOrg
	 * @return
	 */
	SysUserOrgInfo getUserOrgInfo(1:SysUserOrgInfo userOrg) throws (1:Common.ThriftServiceException e);
	
	/**
	 * 根据指定条件判断是否存在此数据权限信息
	 * 存在返回true,不存在false
	 * @param userOrg
	 * @return
	 */
	bool isExistUserOrgInfo(1:SysUserOrgInfo userOrg) throws (1:Common.ThriftServiceException e);
	/**
	 * 修改用户数据权限
	 * @param userOrg
	 * @return
	 */
	i32 updateUserOrgInfo(1:SysUserOrgInfo userOrg);
	
	/**
	 * 根据指定条件获取数据权限列表
	 *
	 */
	list<SysDataAuthView> listDataAuth(1:SysDataAuthView dataAuthView)
   /**
	* 统计数据权限列表记录数
	* @param dataAuthView
	* @return
	*
	*/
	i32 listDataAuthCount(1:SysDataAuthView dataAuthView)
	
	/**
	 * 批量删除数据权限信息
	 * @param integers
	 * @return
	 */
	i32 batchDeleteDataAuth(1:list<i32> dataIds);
	
}
/**   
* @Title: 
* @Package 
* @Description: 系统用户签到类,数据库表：sys_user_sign_info  
* @author liangyanjun   
* @date 2016年3月29日
* @version V1.0   
*/ 
service SysUserSignService {
	
    //根据用户id查询签到天数
	i32 getSignDaysByUserId (1:i32 userId) ;
    //根据用户id检查今天是否已经签到，返回值大于0，则已经签到
	i32 checkToDayIsSign (1:i32 userId) ;
    //添加系统用户签到信息 
	bool addSysUserSign (1:SysUserSign sysUserSign) ;
}

/**   
* @Title: 
* @Package 
* @Description: 邮箱
* @author liangyanjun   
* @date 2016年4月22日
*/ 
service SysMailInfoService {
	//发送邮件
	i32 sendMail (1:SysMailInfo sysMailInfo) ;
    //根据条件查询所有邮件信息表
	list<SysMailInfo> getAll(1:SysMailInfo sysMailInfo);
	//查询邮件信息表
	SysMailInfo getById(1:i32 pid);
	//新增邮件信息表
	i32 insert(1:SysMailInfo sysMailInfo);
	//修改邮件信息表
	i32 update(1:SysMailInfo sysMailInfo);
	//删除邮件信息表
	i32 deleteById(1:i32 pid);
	//批量删除邮件信息表
	i32 deleteByIds(1:list<i32> pids);
}
/**   
* @Title: 
* @Package 
* @Description: 问题反馈
* @author liangyanjun   
* @date 2016年4月23日
*/ 
service SysProblemFeedbackService {
	//添加一条邮件信息
	i32 addProblemFeedback (1:ProblemFeedback problemFeedback) ;
}
/**   
* @Title: 
* @Package 
* @Description: 广告信息
* @author baogang   
* @date 2016年4月25日
*/ 
service SysAdvPicService {
	//添加一条广告图片信息
	i32 addSysAdvPicInfo (1:SysAdvPicInfo sysAdvPicInfo) throws (1:Common.ThriftServiceException e);
	//修改广告图片
	i32 updateSysAdvPicInfo(1:SysAdvPicInfo sysAdvPicInfo) throws (1:Common.ThriftServiceException e);
	//查询广告图片
	list<SysAdvPicInfo> querySysAdvPics(1:SysAdvPicInfo sysAdvPicInfo);
	i32 getSysAdvCount(1:SysAdvPicInfo sysAdvPicInfo);
	//查询广告图片详情
	SysAdvPicInfo querySysAdvPic(1:i32 pid);
}

/**   
* @Title: 
* @Package 
* @Description: 广告信息
* @author baogang   
* @date 2016年5月11日
*/ 
service SysBankService{
	//添加一条银行信息
	i32 addSysBankInfo (1:SysBankInfo sysBankInfo) throws (1:Common.ThriftServiceException e);
	//修改银行信息
	i32 updateSysBankInfo(1:SysBankInfo sysBankInfo) throws (1:Common.ThriftServiceException e);
	//分页查询银行信息
	list<SysBankInfo> querySysBankInfos(1:SysBankInfo sysBankInfo);
	i32 getSysBankCount(1:SysBankInfo sysBankInfo);
	//查询银行信息
	SysBankInfo querySysBankInfo(1:i32 pid);
	//不分页查询银行信息
	list<SysBankInfoDto> queryAllSysBankInfo(1:SysBankInfo sysBankInfo);
	
	i32 batchDelete(1:string ids) throws (1:Common.ThriftServiceException e);
}


/**   
* @Title: 
* @Package 
* @Description: 系统地区广告
* @author chenzhuzhen   
* @date 2016年6月30日
*/ 
service SysAreaInfoService{

	//查询地区数据
	list<SysAreaInfo> getSysAreaInfo(1:SysAreaInfo sysAreaInfo);
	
	//根据地区编号查询一条数据
	SysAreaInfo getSysAreaInfoByCode(1:string areaCode);
	
	//根据地区编号查询一条数据
	SysAreaInfo getSysAreaInfoByUserId(1:i32 userId);
	
	//根据地区名称查询一条数据
	SysAreaInfo getSysAreaInfoByName(1:string areaName);
	
	// 分页查询省市区信息（条件为省、市、区，默认为省）
	list<SysAreaInfo> queryPagedAreaInfo(1:SysAreaInfo sysAreaInfo);
	
	// 查询省市区信息数量（条件为省、市、区，默认为省）
	i32 countAreaInfo(1:SysAreaInfo sysAreaInfo);
	
	// 保存省市区信息
	i32 saveAreaInfo(1:SysAreaInfo sysAreaInfo) throws (1:Common.ThriftServiceException e);
	
	// 删除省市区信息
	i32 batchDelete(1:string areaCode) throws (1:Common.ThriftServiceException e);
}

/*
 *资产机构管理
 *2016-09-14 10:10:43
 **/
struct CapitalOrgInfo{
	1: i32 pid;//主键
	2: string orgName;//机构名称
	3: string orgCode;//机构编码
	4: double loanMoneyTotal;//总放款金额
	5: i32 status;//状态
	6: string createDate;//创建时间
	7: i32 createId;//创建人
	8: i32 updateId;//修改人
	9: string updateDate;//修改时间
	10:i32 page;
	11:i32 rows;
	12: i32 isPush;	//是否推送 （1:是  2：否） 
}

/*资产机构管理Service*/
service CapitalOrgInfoService{
    //根据条件查询所有资产机构管理
	list<CapitalOrgInfo> getAll(1:CapitalOrgInfo capitalOrgInfo);
	//查询资产机构管理
	CapitalOrgInfo getById(1:i32 pid);
	CapitalOrgInfo getByOrgName(1:string orgName);
	//新增资产机构管理
	i32 insert(1:CapitalOrgInfo capitalOrgInfo);
	//修改资产机构管理
	i32 update(1:CapitalOrgInfo capitalOrgInfo);
	//分页查询
	list<CapitalOrgInfo> getCapitalByPage(1:CapitalOrgInfo capitalOrgInfo);
	i32 getCapitalCount(1:CapitalOrgInfo capitalOrgInfo);
	//获取年利率
	double getYearRate(1:i32 pid);
	//修改放款总金额
	i32 updateLoanMoney(1:i32 pid,2:double loanMoney);
	
	//查询所有启用的资产机构管理
	list<CapitalOrgInfo> getAllByStatus();
}

/*
 *机构资金成本
 *2016-09-14 14:14:40
 **/
struct OrgCapitalCost{
	1: i32 pid;//主键
	2: i32 orgId;//机构Id
	3: double yearRate;//年利率
	4: double stepAmount;//阶梯金额
	5: string createDate;//创建时间
	6: i32 createId;//
	7: i32 updateId;//
	8: string updateDate;//
}
/*机构资金成本Service*/
service OrgCapitalCostService{
    //根据条件查询所有机构资金成本
	list<OrgCapitalCost> getAll(1:OrgCapitalCost orgCapitalCost);
	//查询机构资金成本
	OrgCapitalCost getById(1:i32 pid);
	//新增机构资金成本
	i32 insert(1:OrgCapitalCost orgCapitalCost);
	//修改机构资金成本
	i32 update(1:OrgCapitalCost orgCapitalCost);
	//删除机构资金成本
	i32 delCapitionCost(1:i32 pid);
}

/*
 *APP版本信息（版本升级）
 *2016-11-01 09:29:59
 **/
struct SysAppVersionInfo{
	1: i32 pid;//
	2: string appName;//app名称
	3: string appDescription;//描述
	4: string appVersion;//版本号
	5: i32 status;//状态：1=有效，-1=无效
	6: i32 fileId;//文件ID
	7: i32 systemPlatform;//系统类型：1=Android，2=IOS
	8: i32 downloanCount;//下载次数
	9: i32 coercivenessUpgradesStatus;//强制升级状态：1=不强制升级，2强制升级
	10: string createrDate;//创建时间
	11: i32 createrId;//创建者
	12: i32 updateId;//更新者
	13: string updateDate;//更新时间
	14: i32 page=1; //第几页
	15: i32 rows=10; //总页数
	16: BizFile file;//文件
	17: i32 appCategory;//APP类型：1=erp系统
}
/*APP版本信息（版本升级）Service*/
service SysAppVersionInfoService{

	list<SysAppVersionInfo> querySysAppVersionInfo (1:SysAppVersionInfo query) ;
	i32 getSysAppVersionInfoTotal (1:SysAppVersionInfo query) ;

    //根据条件查询所有APP版本信息（版本升级）
	list<SysAppVersionInfo> getAll(1:SysAppVersionInfo sysAppVersionInfo);
	//查询APP版本信息（版本升级）
	SysAppVersionInfo getById(1:i32 pid);
	//检查版本是否最新，不是返回最新的版本信息，否则返回空对象
	SysAppVersionInfo checkVersion(1:SysAppVersionInfo sysAppVersionInfo);
	//新增APP版本信息（版本升级）
	i32 insert(1:SysAppVersionInfo sysAppVersionInfo);
	//修改APP版本信息（版本升级）
	i32 update(1:SysAppVersionInfo sysAppVersionInfo);
	//删除APP版本信息（版本升级）
	i32 deleteById(1:i32 pid);
	//批量删除APP版本信息（版本升级）
	i32 deleteByIds(1:list<i32> pids);
}
/*
 *短信表
 *2017-01-18 09:20:17
 **/
struct SysSmsInfo{
	1: i32 pid;//
	2: string telphone;//
	3: string content;//
	4: string sendDate;//
	5: string createDate;//
	6: i32 creatorId;//
	7: i32 category;//
	8: i32 status;//
	9: string ipAddress;//
}
/*短信表Service*/
service SysSmsInfoService{
    //发送短信
    bool sendSms(1:SysSmsInfo sysSmsInfo);
    //根据条件查询所有短信表
	list<SysSmsInfo> getAll(1:SysSmsInfo sysSmsInfo);
	//查询短信表
	SysSmsInfo getById(1:i32 pid);
	//新增短信表
	i32 insert(1:SysSmsInfo sysSmsInfo);
	//修改短信表
	i32 update(1:SysSmsInfo sysSmsInfo);
	//删除短信表
	i32 deleteById(1:i32 pid);
	//批量删除短信表
	i32 deleteByIds(1:list<i32> pids);
}
