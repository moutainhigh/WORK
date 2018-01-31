namespace java com.qfang.xk.aom.rpc.partner
include "../Common.thrift"
include "../System.thrift"
include "Page.thrift"
include "System.thrift"
/*机构管理平台--合伙人信息*/
struct OrgPartnerInfo{
	1:i32 pid;//合伙人编号
	2:i32 userId;//用户编号
	3:string startTime;//合作开始时间
	4:string endTime;//合作结束时间
	5:string cardNo;//身份证号码
	6:double rate;//提成比例
	7:i32 cooperationStatus;//合作状态,1:未合作,2表示已合作,3表示已过期,4合作待确认
	8:i32 contractId;//合同编号
	9:string url;//合同存放地址
	10:string cooperationDesc;//合作说明
	11:i32 status;//认证状态1:未认证,2表示认证中3、已认证
	12:i32 bizAdviserId;//商务顾问,关联商务顾问表
	13:i32 creatorId;//创建者
	14:string createDate;//创建时间
	15:i32 updateId;//更新者
	16:string updateDate;//更新时间
	17:string remark;//备注
	18:System.OrgUserInfo orgUserInfo;//合伙人账户信息
	19:string auditDesc;//认证说明
	20:i32 reviewStatus;//审核状态：合伙人合作申请状态 1、未申请,10、审核中20、审核通过30、审核不通过
	21:string reviewDesc;//审核意见
	22:i32 reviewId;//审核人
	23:string reviewTime;//审核时间
	24:string applyTime;//合作申请时间
	25:string msgCode;//短信验证码（注册时用到）
	26:string payeeAccount;//收款账号
}
/*合伙人列表*/
struct OrgPartnerDTO{
	1:i32 pid;//合伙人编号
	2:string loginName;//登录名
	3:string realName;//合伙人姓名
	4:string email;//邮箱
	5:string phone;//手机号码
	6:string createTime;
	7:string cardNo;//身份证号码
	8:i32 auditStatus;//认证状态
	9:i32 cooperationStatus;//合作状态
	10:i32 userStatus;//用户账户状态
	11:i32 page;
	12:i32 rows;
	13:i32 userId;
	14:i32 orgNums;//机构数
	15:string applyTime;//合作申请时间
	16:string reviewTime;//审核时间
	17:i32 reviewStatus;//审核状态
	18:string startTime;//合作开始时间
	19:string endTime;//合作结束时间
	20:string rate;//提成比例
}

/*机构管理平台--合伙人信息*/
service OrgPartnerInfoService{
	//查询所有合伙人
	list<OrgPartnerInfo> getAll(1:OrgPartnerInfo orgPartner)throws (1:Common.ThriftServiceException e);
	//查询有合伙人
	OrgPartnerInfo getById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//根据用户id查询
	OrgPartnerInfo getByUserId(1:i32 userId)throws (1:Common.ThriftServiceException e);
	
	//新增有合伙人
	i32 insert(1:OrgPartnerInfo orgPartner)throws (1:Common.ThriftServiceException e);
	//修改有合伙人
	i32 update(1:OrgPartnerInfo orgPartner)throws (1:Common.ThriftServiceException e);
	//删除有合伙人
	i32 deleteById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//批量删除有合伙人
	i32 deleteByIds(1:list<i32> pids)throws (1:Common.ThriftServiceException e);
	//分页查询有合伙人
	list<OrgPartnerDTO> getOrgPartnerByPage(1:OrgPartnerDTO orgPartner)throws (1:Common.ThriftServiceException e);
	//查询有合伙人总数
	i32 getOrgPartnerInfoCount(1:OrgPartnerDTO orgPartner)throws (1:Common.ThriftServiceException e);
	//认证状态修改
	i32 updateAuditStatus(1:OrgPartnerInfo orgPartner)throws (1:Common.ThriftServiceException e);
	//合作状态修改
	i32 updateCooperateStatus(1:OrgPartnerInfo orgPartner)throws (1:Common.ThriftServiceException e);
	//保存合伙人信息
	string saveOrUpdateOrgPartner(1:OrgPartnerInfo orgPartner)throws (1:Common.ThriftServiceException e);
	//修改合作申请信息
	i32 updateReviewStatus(1:OrgPartnerInfo orgPartner)throws (1:Common.ThriftServiceException e);
}

