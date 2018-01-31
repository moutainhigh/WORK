namespace java com.qfang.xk.aom.rpc.org
include "../Common.thrift"
include "../System.thrift"
include "../Customer.thrift"
include "Page.thrift"
include "System.thrift"
/*机构管理平台--资产合作机构信息*/
struct OrgAssetsCooperationInfo{
	1:i32 pid;
	2:string orgName;//机构名称
	3:string code;//机构代码
	4:string address;//地址
	5:string email;//公司邮箱
	6:string contact;//联系人
	7:string phone;//联系电话
	8:i32 auditStatus;//审核状态 1、未认证2、认证中3、已认证4、认证未通过
	9:i32 cooperateStatus;//合作状态,1:未合作,2表示已合作,3表示已过期
	10:i32 bizAdviserId;//商务顾问,关联商务顾问表
	11:i32 partnerId;//合伙人,关联合伙人表
	12:i32 level;//等级
	13:i32 creatorId;//创建者
	14:string createdDate;//创建时间
	15:i32 updateId;//更新者
	16:string updateDate;//更新时间
	17:string remark;//备注
	18:Customer.CusComBase cusComBase;//机构详细信息
	19:list<OrgCooperateCityInfo> OrgCooperateCitys;//关联的合作城市列表
	20:Page.PageInfo pageInfo;//分页参数
	21:i32 page;
	22:i32 rows;
	23:string loginName;//登录名
	24:string partnerName;//合伙人姓名
	25:i32 status;//用户账户状态 1表示有效,2表示无效
	26:System.OrgUserInfo orgUserInfo;//用户账户信息
	27:string auditDesc;//认证说明
	28:string msgCode;//短信验证码
	29:OrgCooperateCityInfo orgCooperateCityInfo;//城市编码表
}
/*合作城市信息表*/
struct OrgCooperateCityInfo{
	1:i32 pid;//主键
	2:string areaCode;//城市编码
	3:string cityName;//城市名称
	4:i32 orgId;//机构ID
	5:list<string> areaCodes;//城市编码集合
	6:list<string> provinceCodes;//省份编码集合
	7:string provinceCode;//省份编码
	8:string contact;//联系人
	9:string phone;//联系人电话
	10:string remark;//备注
	11:i32 status;//状态
	12:string provinceName;//省份名称
}
/*机构合作申请信息*/
struct OrgCooperatCompanyApplyInf{
	1:i32 pid;
	2:i32 orgId;//机构编号,关联资产机构合作信息表
	3:i32 userId;//用户编号,关联用户表
	4:string startTime;//合作开始时间
    5:string endTime;//合作结束时间
    6:double creditLimit;//授信额度
    7:double availableLimit;//可用额度
    8:double assureMoney;//保证金
    9:i32 status;//保证金额调整状态,1表示正常（不做调整），2表示待审核,3表示已调整
    10:string item;//合作条款
    11:i32 creatorId;//创建者
    12:string createdDate;//创建时间
    13:i32 updateId;//更新者
    14:string updateDate;//更新时间
    15:string remark;//备注
	16:OrgAssetsCooperationInfo orgAssetsInfo;//机构信息
	17:i32 applyStatus;//合作申请状态 1、未提交2、已提交3、审核中4、审核通过5、审核不通过
	18:string cooperationCitys;//合作城市编号
	19:list<OrgCooperateCityInfo> cooperateCityList;//合作城市列表
	20:i32 dataVersion;//数据版本，用于控制并发
	21:i32 isNeedHandle;//是否需要办理贷中1、办理2、不办理
	22:double rate;//预收费费率
	23:double singleUpperLimit;//单笔订单金额上限
	24:double realAssureMoney;//实收保证金
	25:double actualFeeRate;//实际收费费率
	26:double fundSizeMoney;//机构资金出款规模
	
	27:double assureMoneyProportion;//保证金比例
	28:double activateCreditLimit;//启用授信额度
	29:string assureMoneyRemark;//保证金备注
	30:double usedLimit;//已用额度
}
/*合作申请合同信息*/
struct OrgCooperationContract{
	1:i32 pid;
    2:i32 cooperationId;//合作编号
    3:string contractNo;//合同编号
    4:i32 fileId;//文件id
    5:string contractName;//合同名称
    6:i32 contractType;//合同类型：合作合同=10，授信合同=20
    7:i32 creatorId;//创建者
    8:string createdDatetime;//创建时间
    9:i32 updateId;//更新者
    10:string updateDate;//更新时间
    11:string remark;//备注
    12:System.BizFile file;//合同文件
}

/*机构合作附加信息(废弃)*/
struct OrgAdditionalCooperationInf{
	1:i32 pid;
    2:i32 cooperationId;//合作编号
    3:string contractNo;//合同编号
    4:string contractUrl;//合同存放地址
    5:string creditContractId;//授信合同编号
    6:string creditContractUrl;//授信合同存放地址
    7:string guranteeNo;//保证函编号
    8:string guranteePath;//保证函存放地址
    9:i32 creatorId;//创建者
    10:string createdDatetime;//创建时间
    11:i32 updateId;//更新者
    12:string updateDate;//更新时间
    13:string remark;//备注
}
/*机构合作申请列表展示*/
struct OrgCooperatCompanyApply{
	1:i32 pid;
	2:i32 orgId;//机构编号,关联资产机构合作信息表
	3:i32 userId;//用户编号,关联用户表
	4:string startTime;//合作开始时间
    5:string endTime;//合作结束时间
    6:double creditLimit;//授信额度
    7:double availableLimit;//可用额度
    8:double assureMoney;//保证金
    9:string orgName;//机构名称
    10:string orgCode;//机构代码
    11:i32 cooperationStatus;//合作状态
    12:i32 hisCooperationNum;//历史合作笔数
    13:i32 cooperationNum;//正在合作笔数
    14:i32 page;
	15:i32 rows;
	16:i32 applyStatus;//合作申请状态 1、未提交2、已提交3、审核中4、审核通过5、审核不通过
	17:string partnerName;//合伙人
	18:string contact;//联系人
	19:string phone;//联系人电话
	20:string applyDate;//申请时间
	21:i32 isNeedHandle;//是否需要办理贷中1、办理2、不办理
	22:double rate;//收费费率
	23:double singleUpperLimit;//单笔订单金额上限
	24:double actualFeeRate;//实际收费费率
	25:double fundSizeMoney;//机构资金出款规模
	26:double realAssureMoney;//实收保证金
	27:list<i32> userIds;//数据权限集合
	28:string cooperationUpdateStatus;//机构合作信息修改申请审批状态
}
/*合作机构保证金额变更流水*/
struct OrgAssureFundFlowInfo{
	1:i32 pid;
	2:i32 applyId;//合作申请编号
	3:double oldAssureMoney;//原保证金
	4:double curAssureMoney;//当前保证金
	5:string updateDate;//变更日期
	6:i32 operator;//操作人
	7:string createdDateTime;//操作时间
	8:string audit;//审核意见
	9:string auditDate;//审核日期
	10:i32 auditId;//审核人
	11:i32 status;//状态,10表示同意变更,20表示不同意变更,1待审核
	12:double oldCreditLimit;//原授信额度
	13:double curCreditLimit;//当前授信额度
}
/*保证金变更列表展示*/
struct OrgAssureFundFlowDTO{
	1:i32 pid;
	2:i32 applyId;//合作申请编号
	3:double oldAssureMoney;//原保证金
	4:double curAssureMoney;//当前保证金
	5:string orgName;//机构名称
    6:string orgCode;//机构代码
    7:string contact;//联系人
	8:string phone;//联系人电话
	9:string createdDateTime;//操作时间
	10:i32 status;//状态,1表示同意变更,2表示不同意变更
	15:i32 page;
	16:i32 rows;
	17:list<i32> userIds;//数据权限集合
}
/*合伙人的客户（机构）列表*/
struct PartnerOrgIndex{
	1:i32 page;
	2:i32 rows;
	3:i32 orgId;
	4:string orgName;//机构名称
    5:string orgCode;//机构代码
    6:string contact;//联系人
	7:string phone;//联系人电话
	8:i32 auditStatus;//认证状态 1、未认证，2、认证中，3、已认证，4、认证未通过
	9:i32 cooperateStatus;//合作状态,1:未合作,2表示已合作,3表示已过期
	10:i32 cooperateApplyStatus;//合作申请状态 1、未申请，2、已提交，3、审核中，4、审核通过，5、审核不通过
	11:i32 partnerId;//合伙人id
	12:string userName;//用户名（登录名）
}

/*机构管理平台--资产合作机构信息*/
service OrgAssetsCooperationService{
	//查询所有资产合作机构
	list<OrgAssetsCooperationInfo> getAll(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//查询资产合作机构
	OrgAssetsCooperationInfo getById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	
	//新增资产合作机构
	i32 insert(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//修改资产合作机构
	i32 update(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//删除资产合作机构
	i32 deleteById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//批量删除资产合作机构
	i32 deleteByIds(1:list<i32> pids)throws (1:Common.ThriftServiceException e);
	//分页查询资产合作机构
	list<OrgAssetsCooperationInfo> getOrgAssetsByPage(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//查询资产合作机构总数
	i32 getOrgAssetsCooperationCount(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//认证状态修改
	i32 updateAuditStatus(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//合作状态修改
	i32 updateCooperateStatus(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//查询所有机构合作城市
	list<OrgCooperateCityInfo> getAllOrgCooperateCitys(1:i32 orgId);
	//修改机构信息
	i32 updateOrgAssetsInfo(1:OrgAssetsCooperationInfo orgAssets)throws (1:Common.ThriftServiceException e);
	//根据机构代码查询
	OrgAssetsCooperationInfo getOrgByCode(1:string code);
	//检查机构代码是否已存在
	bool checkOrgCodeIsExist(1:string code);
	
	//合伙人的客户（机构）列表（分页查询）
	list<PartnerOrgIndex> findPartnerOrgIndex(1:PartnerOrgIndex partnerOrgIndex);
	i32 getPartnerOrgIndexTotal(1:PartnerOrgIndex partnerOrgIndex);
	//级联删除机构,未认证之前可删除，保存用户信息，机构信息，资金账户信息
	i32 cascadeDeleteOrg(1:i32 orgId,2:i32 loginUserId);
	//通过pid获得OrgCooperateCityInfo信息
	list<OrgCooperateCityInfo> getOrgCityInfoListByOrgId (1:i32 orgId)throws (1:Common.ThriftServiceException e);
	//增加合作城市OrgCooperateCityInfo信息
	i32 insertOrgCooperateCityInfo(1:OrgCooperateCityInfo orgCooperateCityInfo)throws (1:Common.ThriftServiceException e);
	//修改合作城市OrgCooperateCityInfo信息
	i32 updateOrgCooperateCityInfo(1:OrgCooperateCityInfo orgCooperateCityInfo)throws (1:Common.ThriftServiceException e);
}
/*机构合作申请信息Service*/
service OrgCooperatCompanyApplyService{
	//查询所有机构合作申请信息
	list<OrgCooperatCompanyApplyInf> getAll(1:OrgCooperatCompanyApplyInf orgCooperate)throws (1:Common.ThriftServiceException e);
	//查询机构合作申请信息
	OrgCooperatCompanyApplyInf getById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//根据用户id查询
	OrgCooperatCompanyApplyInf getByUserId(1:i32 userId)throws (1:Common.ThriftServiceException e);
	
	//新增机构合作申请信息
	i32 insert(1:OrgCooperatCompanyApplyInf orgCooperate)throws (1:Common.ThriftServiceException e);
	//修改机构合作申请信息
	i32 update(1:OrgCooperatCompanyApplyInf orgCooperate)throws (1:Common.ThriftServiceException e);
	//删除机构合作申请信息
	i32 deleteById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	
	//分页查询机构合作申请信息
	list<OrgCooperatCompanyApply> getOrgCooperateByPage(1:OrgCooperatCompanyApply orgCooperate)throws (1:Common.ThriftServiceException e);
	//查询机构合作申请信息总数
	i32 getOrgCooperateCount(1:OrgCooperatCompanyApply orgCooperate)throws (1:Common.ThriftServiceException e);

	//查询机构合作申请详细信息，包括附件信息
	OrgCooperatCompanyApplyInf getByPid(1:i32 pid)throws (1:Common.ThriftServiceException e);
	//修改合作申请状态
	i32 updateApplyStatus(1:OrgCooperatCompanyApplyInf orgCooperate)throws (1:Common.ThriftServiceException e);
	//保存合作城市信息
	i32 saveOrgCooperateCitys(1:string citys,2:i32 orgId)throws (1:Common.ThriftServiceException e);
	//保存合作申请信息
	i32 saveOrgCooperateApplyInfo(1:OrgCooperatCompanyApplyInf orgCooperate)throws (1:Common.ThriftServiceException e);
	//提交合作申请
	i32 subApplyCooperat(1:OrgCooperatCompanyApplyInf orgCooperate)throws (1:Common.ThriftServiceException e);
	//查询机构合作城市列表
	list<OrgCooperateCityInfo> getOrgCooperateCityInfo(1:OrgCooperateCityInfo query);
	//根据机构ID查询机构合作情况
	OrgCooperatCompanyApplyInf getByOrgId(1:i32 orgId)throws (1:Common.ThriftServiceException e);
	//修改可用授信额度
	i32 updateOrgCreditLimit(1:i32 projectId,2:double money)throws (1:Common.ThriftServiceException e);
	//贷前业务来源数据查询
	list<OrgCooperatCompanyApply> getOrgAssetsList();
}
/*保证金变更Service*/
service OrgAssureFundFlowInfoService{
	//查询所有保证金变更
	list<OrgAssureFundFlowInfo> getAll(1:OrgAssureFundFlowInfo orgAssureFundFlow)throws (1:Common.ThriftServiceException e);
	//查询保证金变更信息
	OrgAssureFundFlowInfo getById(1:i32 pid)throws (1:Common.ThriftServiceException e);
	
	//新增保证金变更信息
	i32 insert(1:OrgAssureFundFlowInfo orgAssureFundFlow)throws (1:Common.ThriftServiceException e);
	//修改保证金变更信息
	i32 update(1:OrgAssureFundFlowInfo orgAssureFundFlow)throws (1:Common.ThriftServiceException e);
	
	//分页查询保证金变更申请
	list<OrgAssureFundFlowDTO> getOrgAssureFundByPage(1:OrgAssureFundFlowDTO assureFundFlow)throws (1:Common.ThriftServiceException e);
	//查询保证金变更总数
	i32 getOrgAssureFundCount(1:OrgAssureFundFlowDTO assureFundFlow)throws (1:Common.ThriftServiceException e);
	//保存保证金变更信息，同时修改授信额度以及保证金金额
	i32 updateOrgFundFlow(1:OrgAssureFundFlowInfo orgAssureFundFlow)throws (1:Common.ThriftServiceException e);
	//获取申请中的保证金变更信息
	OrgAssureFundFlowInfo getApplyOrgFundFlow(1:i32 orgId)throws (1:Common.ThriftServiceException e);
}
/*机构合作合同Service*/
service OrgCooperationContractService{
    //根据条件查询所有机构合作合同
	list<OrgCooperationContract> getAll(1:OrgCooperationContract orgCooperationContract);
	//查询机构合作合同
	OrgCooperationContract getById(1:i32 pid);
	//根据机构id和类型查询机构合作合同
	OrgCooperationContract getByOrgIdAndType(1:i32 orgId,2:i32 contractType);
	//新增机构合作合同
	i32 insert(1:OrgCooperationContract orgCooperationContract);
	//修改机构合作合同
	i32 update(1:OrgCooperationContract orgCooperationContract);
	//删除机构合作合同
	i32 deleteById(1:i32 pid);
	//批量删除机构合作合同
	i32 deleteByIds(1:list<i32> pids);
}
/*
 *商务顾问分配
 *2016-09-08 09:56:48
 **/
struct OrgBizAdviserAllocationInf{
	1: i32 pid;//
	2: i32 serviceObjId;//服务对象编号(机构系统用户id)
	3: i32 bizAdviserId;//商务顾问编号(ERP后台系统用户id)
	4: i32 type;//1表示合作机构,2表示合伙人
	5: string remark;//备注
	6: string createrDate;//创建时间
	7: i32 createrId;//创建人ID(ERP后台系统用户id)
	8: i32 updateId;//更新人ID(ERP后台系统用户id)
	9: string updateDate;//更新时间
}
/*机构分配列表*/
struct OrgBizAdviserAllocationInfPage{
    1:i32 page;
	2:i32 rows;
	3:i32 orgId;//机构编号,关联资产机构合作信息表
	4:i32 userId;//用户编号,关联用户表
    5:string orgName;//机构名称
    6:string orgCode;//机构代码
	7:string contact;//联系人
	8:string phone;//联系人电话
	9:string partnerName;//所属合伙人
    10:i32 auditStatus;//审核状态 1、未认证2、认证中3、已认证4、认证未通过
	11:i32 cooperateStatus;//合作状态,1:未合作,2表示已合作,3表示已过期
	12:i32 allocationType;//分配类型：未分配=-1，已分配=1
	13:string registerDate;//注册时间
	14: i32 bizAdviserId;//商务顾问编号(ERP后台系统用户id)
	15: string bizAdviserName;//商务顾问名称(ERP后台系统用户名称)
	16: string allocationDate;//分配时间
	17: string allocationRemark;//分配备注
	18:string email;//公司邮箱
	19:i32 status;//用户账户状态 1表示有效,2表示无效
	20:string loginName;//登录名
	21:list<i32> userIds;//数据权限集合
}
/*商务顾问分配Service*/
service OrgBizAdviserAllocationInfService{
    //机构分配列表(分页查询)
	list<OrgBizAdviserAllocationInfPage> queryOrgBizAdviserAllocationInfPage(1:OrgBizAdviserAllocationInfPage query);
	i32 getOrgBizAdviserAllocationInfPageTotal(1:OrgBizAdviserAllocationInfPage query);
    //根据条件查询所有商务顾问分配
	list<OrgBizAdviserAllocationInf> getAll(1:OrgBizAdviserAllocationInf orgBizAdviserAllocationInf);
	//查询商务顾问分配
	OrgBizAdviserAllocationInf getById(1:i32 pid);
	//新增商务顾问分配
	i32 insert(1:OrgBizAdviserAllocationInf orgBizAdviserAllocationInf);
	//修改商务顾问分配
	i32 update(1:OrgBizAdviserAllocationInf orgBizAdviserAllocationInf);
	//删除商务顾问分配
	i32 deleteById(1:i32 pid);
	//批量删除商务顾问分配
	i32 deleteByIds(1:list<i32> pids);
}
/*
 *机构合作信息修改申请表
 *2017-01-06 15:23:23
 **/
struct OrgCooperationUpdateApply{
	1: i32 pid;//
	2: i32 orgId;//机构id
	3: double creditLimit;//授信额度
	4: double oldCreditLimit;//旧授信额度
	5: double activateCreditLimit;//启用授信额度
	6: double oldActivateCreditLimit;//旧启用授信额度
	7: double planRate;//预收费费率
	8: double oldPlanRate;//旧预收费费率
	9: double fundSizeMoney;//出款标准
	10: double oldFundSizeMoney;//旧出款标准
	11: double assureMoneyProportion;//保证金比例
	12: double oldAssureMoneyProportion;//旧保证金比例
	13: double assureMoney;//保证金
	14: double oldAssureMoney;//旧保证金
	15: double actualFeeRate;//实际收费费率
	16: double oldActualFeeRate;//旧实际收费费率
	17: double singleUpperLimit;//单笔上限
	18: double oldSingleUpperLimit;//旧单笔上限
	19: string assureMoneyRemark;//保证金备注
	20: string oldAssureMoneyRemark;//旧保证金备注
	21: i32 applyStatus;//申请状态：未申请=1,申请中=2,驳回=3,审核中=4,审核通过=5,审核不通过=6,已归档=7
	22: string remark;//备注
	23: string createrDate;//
	24: i32 createrId;//
	25: i32 updateId;//
	26: string updateDate;//
    27: i32 page=1; //第几页
	28: i32 rows=10; //总页数
	29: string createrName;//
}
/*机构合作信息修改申请表Service*/
service OrgCooperationUpdateApplyService{
    //根据条件查询所有机构合作信息修改申请表
	list<OrgCooperationUpdateApply> getAll(1:OrgCooperationUpdateApply orgCooperationUpdateApply);
	//查询机构合作信息修改申请表
	OrgCooperationUpdateApply getById(1:i32 pid);
	//根据机构id查询最后一次申请的机构合作信息修改申请信息
	OrgCooperationUpdateApply getLastByOrgId(1:i32 orgId);
	//根据机构id和申请状态集合查询
	list<OrgCooperationUpdateApply> getByOrgIdAndApplyStatus(1:i32 orgId,2:list<i32> applyStatusList);
	//新增机构合作信息修改申请表
	i32 insert(1:OrgCooperationUpdateApply orgCooperationUpdateApply);
	//修改机构合作信息修改申请表
	i32 update(1:OrgCooperationUpdateApply orgCooperationUpdateApply);
	//删除机构合作信息修改申请表
	i32 deleteById(1:i32 pid);
	//批量删除机构合作信息修改申请表
	i32 deleteByIds(1:list<i32> pids);
	//历史列表(分页查询)
	list<OrgCooperationUpdateApply> queryApplyHisIndex(1:OrgCooperationUpdateApply query);
	//历史列表总数
	i32 getApplyHisIndexTotal(1:OrgCooperationUpdateApply query);
}