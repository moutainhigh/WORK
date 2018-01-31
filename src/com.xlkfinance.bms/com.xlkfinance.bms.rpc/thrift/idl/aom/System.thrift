namespace java com.qfang.xk.aom.rpc.system
include "../Common.thrift"
include "../System.thrift"
include "Page.thrift"
/*机构管理平台--资产合作用户表*/
struct OrgUserInfo{
	1:i32 pid;
	2:string userName;//用户名
	3:string password;//登录密码
	4:string realName;//真实姓名
	5:string nickName;//昵称
	6:string jobNo;//工号
	7:string phone;//联系电话
	8:string email;//Email
	9:i32 orgId;//所属机构,当用户为合伙人时，机构编号为0
	10:i32 role;//角色
	11:i32 status;//状态,1表示有效,0表示无效
	12:i32 userType;//用户类型,1表示机构,2表示合伙人,2表示员工(机构下的员工)
	13:i32 dateScope;//数据权限,1表示私有,2表示所有
	14:i32 createId;//创建者
	15:string createDate;//创建时间
	16:i32 updateId;//更新者
	17:string updateDate;//更新时间
	18:string remark;//备注
	19:Page.PageInfo pageInfo;//分页参数
	20:i32 page;
	21:i32 rows;
	22:string deptName;//部门
	23:i32 menuType;//菜单类型
}

/*机构管理平台用户资金帐户*/
struct OrgUserFunAccount{
	1:i32 pid;//账号编号
	2:i32 userId;//用户编号
	3:double totalMoney;//总资金
	4:double availableMoney;//可用资金
	5:double freezeMoney;//冻结资金
	6:string traderPassword;//交易密码
	7:string updateDate;//更新日期
	8:i32 status;//状态,1表示有效,0表示无效
}
/*资金账户交易流水(充值/提现)*/
struct OrgFundFlowInfo{
	1:i32 pid;//流水号
	2:i32 transType;//交易类型
	3:double transMoney;//交易金额
	4:i32 userAccountId;//用户帐户
	5:string transDate;//交易日期
	6:string transNo;//交易流水
	7:double totalMoney;//当前余额
	8:double availableMoney;//当前可用余额
	9:i32 paymentType;//支付方式,1表示线下转账,2表示网银支付,其他方式一次递增
	10:i32 status;//状态,-1表示交易失败,1表示交易成功,0表示交易中
}
/*
 *短信验证码信息表
 *2016-07-07 09:10:32
 **/
struct SmsValidateCodeInfo{
	1: i32 pid;//
	2: string telphone;//手机号码
	3: string code;//验证码
	4: string sendDate;//发送时间
	5: string createDate;//创建时间
	6: i32 creatorId;//创建者id
	7: i32 category;//短信验证码类型,1表示注册验证,2表示密码找回
	8: i32 status;//状态,1表示有效,2表示失效
	9: string ipAddress;//ip地址
}
/*
 *系统菜单信息
 *2016-07-11 09:37:21
 **/
struct OrgSysMenuInfo{
	1: i32 pid;//菜单编号
	2: string menuName;//菜单名称
	3: i32 parentId;//父级菜单编号
	4: i32 level;//菜单层级
	5: string url;//
	6: i32 status;//状态:有效=1，无效=2
	7: i32 menuIndex;//索引
	8: string iconCls;//图标样式
	9: list<OrgSysMenuInfo> childrenList;//子菜单list
	10: i32 userType;//菜单归属于用户类型：机构=1，合伙人=2
}
/*机构管理平台--员工列表*/
struct OrgEmpIndex{
	1:i32 page;
	2:i32 rows;
	3:i32 pid;
	4:string userName;//用户名
	5:string realName;//真实姓名
	6:string nickName;//昵称
	7:string jobNo;//工号
	8:string phone;//联系电话
	9:string email;//Email
	10:i32 status;//
	11:string deptName;//部门
	12:i32 dateScope;//数据权限,1表示私有,2表示所有
	13:i32 orgId;//
}
/*
 *用户与菜单关联表
 *2016-07-26 14:36:46
 **/
struct OrgUserMenuInfo{
	1: i32 pid;//
	2: i32 userId;//
	3: i32 orgId;//
	4: i32 menuId;//
}
/*机构管理平台--功能权限列表*/
struct OrgFnPermissionIndex{
    1:i32 page;
	2:i32 rows;
	3:i32 menuId;
	4:string menuName;//
	5:i32 status;//状态：无权限=0，有权限=大于零
	6:i32 orgId;//
	7:i32 userId;//
	8:i32 userType;//
	9:i32 parentId;//父级id
}
/*
 *系统访问日志
 *2016-07-29 15:10:43
 **/
struct OrgSysLogInfo{
	1: i32 pid;//
	2: i32 orderId;//
	3: i32 type;//
	4: string content;//
	5: string ipAddress;//
	6: i32 operator;//
	7: string createDate;//
	8: string browser;//
}
/*
 *机构用户资料附件
 *2016-09-01 10:47:34
 **/
struct OrgUserFile{
	1: i32 pid;//
	2: i32 userId;//
	3: i32 fileId;//
	4: i32 status;//
	5: System.BizFile file;//文件
	6: i32 page=1; //第几页
	7: i32 rows=10; //总页数
}
/*机构管理平台--资产合作用户Service*/
service OrgUserService{
	//根据条件查询用户集合
	list<OrgUserInfo> getAll(1:OrgUserInfo orgUser)throws (1:Common.ThriftServiceException e);
	//根据id查询用户
	OrgUserInfo getById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	
	//新增用户
	i32 insert(1:OrgUserInfo orgUser)throws (1:Common.ThriftServiceException e);
	//修改用户
	i32 update(1:OrgUserInfo orgUser)throws (1:Common.ThriftServiceException e);
	//删除用户
	i32 deleteById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//批量删除用户
	i32 deleteByIds(1:list<i32> pids)throws (1:Common.ThriftServiceException e);
	//分页查询用户
	list<OrgUserInfo> getOrgUserByPage(1:OrgUserInfo orgUser)throws (1:Common.ThriftServiceException e);
	//查询用户总数
	i32 getOrgUserCount(1:OrgUserInfo orgUser)throws (1:Common.ThriftServiceException e);
	//修改用户账号启用或者禁用
	i32 updateStatus(1:i32 pid,2:i32 status)throws (1:Common.ThriftServiceException e);
	//根据用户名查询用户
	OrgUserInfo queryOrgUserByName(1:string userName);
	//根据手机号码查询用户
	OrgUserInfo getUserByPhone(1:string phone);
	//根据email查询用户
	OrgUserInfo getUserByEmail(1:string email);
	//检查用户名是否已存在
	bool checkUserNameIsExist(1:string userName);
	//检查手机号码是否已存在
	bool checkPhoneIsExist(1:string phone);
	//检查email是否已存在
	bool checkEmailIsExist(1:string email);
	//员工列表
	list<OrgEmpIndex> findOrgEmpIndexPage(1:OrgEmpIndex emp);
	i32 getOrgEmpIndexTotal(1:OrgEmpIndex emp);
	//机构管理平台--功能权限列表
	list<OrgFnPermissionIndex> findOrgFnPermissionIndexPage(1:OrgFnPermissionIndex orgFnPermissionIndex);
	i32 getOrgFnPermissionIndexTotal(1:OrgFnPermissionIndex orgFnPermissionIndex);
	//增加机构员工
	i32 addOrgEmp(1:OrgUserInfo orgUser);
	//根据用户id集合更新用户的数据权限
	i32 updateDateScopeByIds(1:list<i32> idList,2:i32 dateScope,3:i32 orgId,4:i32 userType);
}
/*短信验证码信息表Service*/
service SmsValidateCodeInfoService{
    //根据条件查询所有短信验证码信息表
	list<SmsValidateCodeInfo> getAll(1:SmsValidateCodeInfo smsValidateCodeInfo);
	i32 getCount(1:SmsValidateCodeInfo smsValidateCodeInfo);
	//查询短信验证码信息表
	SmsValidateCodeInfo getById(1:i32 pid);
	//新增短信验证码信息表
	i32 insert(1:SmsValidateCodeInfo smsValidateCodeInfo);
	//修改短信验证码信息表
	i32 update(1:SmsValidateCodeInfo smsValidateCodeInfo);
	//删除短信验证码信息表
	i32 deleteById(1:i32 pid);
	//批量删除短信验证码信息表
	i32 deleteByIds(1:list<i32> pids);
	//根据手机号码，验证码，短信类型，把短信验设置成无效状态
	i32 disabledMsg(1:string telphone, 2:string code,3:i32 category);
	//根据号码和短信类型获取今天内发送短信的条数
	i32 getTodayMsgCountByPhone(1:string telphone, 2:i32 category);
	//根据ip获取今天内发送短信的条数
	i32 getTodayMsgCountByIp(1:string ipAddress);
	//验证验证码是否正确（true表示有效,false表示失效）
	bool validCode(1:string telphone, 2:string code,3:i32 category);
	//发送验证码
	string sendCodeMsg(1:string telphone, 2:i32 category,3:string ipAddress);
}
/*系统菜单信息Service*/
service OrgSysMenuInfoService{
    //根据条件查询所有系统菜单信息
	list<OrgSysMenuInfo> getAll(1:OrgSysMenuInfo orgSysMenuInfo);
    //获取菜单树（只包含二级菜单）
	list<OrgSysMenuInfo> getTree(1:OrgUserInfo orgUserInfo);
	//查询系统菜单信息
	OrgSysMenuInfo getById(1:i32 pid);
	//新增系统菜单信息
	i32 insert(1:OrgSysMenuInfo orgSysMenuInfo);
	//修改系统菜单信息
	i32 update(1:OrgSysMenuInfo orgSysMenuInfo);
	//删除系统菜单信息
	i32 deleteById(1:i32 pid);
	//批量删除系统菜单信息
	i32 deleteByIds(1:list<i32> pids);
}
/*用户与菜单关联表Service*/
service OrgUserMenuInfoService{
    //根据条件查询所有用户与菜单关联表
	list<OrgUserMenuInfo> getAll(1:OrgUserMenuInfo orgUserMenuInfo);
	//查询用户与菜单关联表
	OrgUserMenuInfo getById(1:i32 pid);
	//新增用户与菜单关联表
	i32 insert(1:OrgUserMenuInfo orgUserMenuInfo);
	//修改用户与菜单关联表
	i32 update(1:OrgUserMenuInfo orgUserMenuInfo);
	//删除用户与菜单关联表
	i32 deleteById(1:i32 pid);
	//批量删除用户与菜单关联表
	i32 deleteByIds(1:list<i32> pids);
}

/*系统访问日志Service*/
service OrgSysLogInfoService{
    //根据条件查询所有系统访问日志
	list<OrgSysLogInfo> getAll(1:OrgSysLogInfo orgSysLogInfo);
	//查询系统访问日志
	OrgSysLogInfo getById(1:i32 pid);
	//新增系统访问日志
	i32 insert(1:OrgSysLogInfo orgSysLogInfo);
}

/*机构用户资料附件Service*/
service OrgUserFileService{
    //根据条件查询所有机构用户资料附件
	list<OrgUserFile> getAll(1:OrgUserFile orgUserFile);
	//根据用户id机构用户资料附件
	list<OrgUserFile> getByUserId(1:i32 userId);
	//查询机构用户资料附件
	OrgUserFile getById(1:i32 pid);
	//新增机构用户资料附件
	i32 insert(1:OrgUserFile orgUserFile);
	//保存文件信息并关联到机构
	i32 saveOrgUserFile(1:i32 userId,2:list<System.BizFile> fileList);
	//修改机构用户资料附件
	i32 update(1:OrgUserFile orgUserFile);
	//删除机构用户资料附件
	i32 deleteById(1:i32 pid);
	//批量删除机构用户资料附件
	i32 deleteByIds(1:list<i32> pids);
	list<System.BizFile> queryOrgUserFilePage(1:OrgUserFile query);
	i32 getOrgUserFileTotal(1:OrgUserFile query);
}