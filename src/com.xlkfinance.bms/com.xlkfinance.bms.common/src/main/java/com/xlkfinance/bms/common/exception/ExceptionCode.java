package com.xlkfinance.bms.common.exception;

/**
 * 
 * @Title: Constants.java
 * @Description: 常量接口
 * @author : Mr.Cai
 * @date : 2014年12月20日 下午4:02:36
 * @email: caiqing12110@vip.qq.com
 */
public interface ExceptionCode {

	// E00开是 通用
	// E01开是 客户管理
	// E02开是 贷前管理
	// E03开是 合同管理
	// E04开是 低质押物管理
	// E05开是 额度管理
	// E06开是 任务管理
	// E07开是 还款管理
	// E08开是 贷后管理
	// E09开是 系统管理 1 代表系统用户管理 2权限管理 3角色管理 4菜单管理 5系统配置管理 6业务日志 7数据字典
	// E10开是 财务管理
	//E11 产品管理
	// 新增 001 修改002 删除003 查询004
	// E01客户管理 1
	// 个人客户管理(01=基础信息,02=家庭信息,03=银行开户,04=旗下子公司,05征信记录,06=关系人信息,07资信评估,08业务往来)
	// 2企业客户管理
	// (01=企业基础信息，02=财务信息,03=员工结构，04=银行开户,,05=企业联系人信息,06=管理团队,07=债务情况,08=债权,09=对外担保
	// ,10=对外投资, 11=获奖情况,12=企业资信评估，13=业务往来，14=企业控制人信息)3资信评估 4 黑名单客户 5拒贷客户 6潜在客户
	// 7业务往来
	public static final String SYSUSER_ADD = "E09101";
	public static final String SYSUSER_UPDATE = "E09102";
	public static final String SYSUSER_DELETE = "E09103";
	public static final String SYSCONFIG_ADD = "E09501";
	public static final String SYSCONFIG_UPDATE = "E09502";
	public static final String SYSCONFIG_DELETE = "E09503";
	 

	// 新增 001 修改002 删除003 查询004
	// E01客户管理 1
	// 个人客户管理(01=基础信息,02=家庭信息,03=银行开户,04=旗下子公司,05征信记录,06=关系人信息,07资信评估,08业务往来)
	// 2企业客户管理
	// (01=企业基础信息，02=财务信息,03=员工结构，04=银行开户,,05=企业联系人信息,06=管理团队,07=债务情况,08=债权,09=对外担保
	// ,10=对外投资,
	// 11=获奖情况,12=企业资信评估，13=业务往来，14=企业控制人信息)3资信评估(01评估模板，02要素权重，03指标信息，04指标选项，05资信评估，06评估值)
	// 4 黑名单客户 5拒贷客户 6潜在客户 7业务往来

	public static final String CUS_ACCT_ADD = "E010001";
	public static final String CUSPER_BASE_ADD = "E011011";
	public static final String CUSPER_FAMILY_ADD = "E011021";
	public static final String CUSPER_BANK_ADD = "E011031";
	public static final String CUSPER_COM_ADD = "E011041";
	public static final String CUSPER_CREDIT_ADD = "E011051";
	public static final String CUSPER_PERSON_ADD = "E011061";
	public static final String CUSPER_EST_ADD = "E011071";
	public static final String CUSPER_BUSINESS_ADD = "E011081";

	public static final String CUS_ACCT_UPDATE = "E010002";
	public static final String CUSPER_BASE_UPDATE = "E011012";
	public static final String CUSPER_FAMILY_UPDATE = "E011022";
	public static final String CUSPER_BANK_UPDATE = "E011032";
	public static final String CUSPER_COM_UPDATE = "E011042";
	public static final String CUSPER_CREDIT_UPDATE = "E011052";
	public static final String CUSPER_PERSON_UPDATE = "E011062";
	public static final String CUSPER_EST_UPDATE = "E011072";
	public static final String CUSPER_BUSINESS_UPDATE = "E011082";

	public static final String CUS_ACCT_DELETE = "E010003";
	public static final String CUSPER_BASE_DELETE = "E011013";
	public static final String CUSPER_FAMILY_DELETE = "E011023";
	public static final String CUSPER_BANK_DELETE = "E011033";
	public static final String CUSPER_COM_DELETE = "E011043";
	public static final String CUSPER_CREDIT_DELETE = "E011053";
	public static final String CUSPER_PERSON_DELETE = "E011063";
	public static final String CUSPER_EST_DELETE = "E011073";
	public static final String CUSPER_BUSINESS_DELETE = "E011083";

	public static final String CUS_ACCT_QUERY = "E010004";
	public static final String CUSPER_BASE_QUERY = "E011014";
	public static final String CUSPER_FAMILY_QUERY = "E011024";
	public static final String CUSPER_BANK_QUERY = "E011034";
	public static final String CUSPER_COM_QUERY = "E011044";
	public static final String CUSPER_CREDIT_QUERY = "E011054";
	public static final String CUSPER_PERSON_QUERY = "E011064";
	public static final String CUSPER_EST_QUERY = "E011074";
	public static final String CUSPER_BUSINESS_QUERY = "E011084";

	// 企业客户

	// 资信评估
	public static final String CUSEST_TEMPLATE_QUERY = "E013014";
	public static final String CUSEST_FACTOR_QUERY = "E013024";
	public static final String CUSEST_QUOTA_QUERY = "E013034";
	public static final String CUSEST_OPTION_QUERY = "E013044";
	public static final String CUSEST_INFO_QUERY = "E013054";
	public static final String CUSEST_VALUE_QUERY = "E013054";

	public static final String CUSEST_TEMPLATE_ADD = "E013011";
	public static final String CUSEST_FACTOR_ADD = "E013021";
	public static final String CUSEST_QUOTA_ADD = "E013031";
	public static final String CUSEST_OPTION_ADD = "E013041";
	public static final String CUSEST_INFO_ADD = "E013051";
	public static final String CUSEST_VALUE_ADD = "E013061";

	public static final String CUSEST_TEMPLATE_UPDATE = "E013012";
	public static final String CUSEST_FACTOR_UPDATE = "E013022";
	public static final String CUSEST_QUOTA_UPDATE = "E013032";
	public static final String CUSEST_OPTION_UPDATE = "E013042";
	public static final String CUSEST_INFO_UPDATE = "E013052";
	public static final String CUSEST_VALUE_UPDATE = "E013062";

	public static final String CUSEST_TEMPLATE_DELETE = "E013013";
	public static final String CUSEST_FACTOR_DELETE = "E013023";
	public static final String CUSEST_QUOTA_DELETE = "E013033";
	public static final String CUSEST_OPTION_DELETE = "E013043";
	public static final String CUSEST_INFO_DELETE = "E013053";
	public static final String CUSEST_VALUE_DELETE = "E013063";

	// 企业客户
	public static final String CUSCOM_BASE_ADD = "E012011";
	public static final String CUSCOM_FINAACIA_ADD = "E012021";
	public static final String CUSCOM_STAFF_ADD = "E012031";
	public static final String CUSCOM_BANK_ADD = "E012041";
	public static final String CUSCOM_CONTACT_ADD = "E012051";
	public static final String CUSCOM_TEAM_ADD = "E012061";
	public static final String CUSCOM_DEPTRIGHT_ADD = "E012071";
	public static final String CUSCOM_DEPT_ADD = "E012081";
	public static final String CUSCOM_ASSURE_ADD = "E012091";// 担保
	public static final String CUSCOM_INVEST_ADD = "E0120101";
	public static final String CUSCOM_REWARD_ADD = "E0120111";
	public static final String CUSCOM_CONTROLLER_ADD = "E0120141";

	public static final String CUSCOM_BASE_UPDATE = "E012012";
	public static final String CUSCOM_FINAACIA_UPDATE = "E012022";
	public static final String CUSCOM_STAFF_UPDATE = "E012032";
	public static final String CUSCOM_BANK_UPDATE = "E012042";
	public static final String CUSCOM_CONTACT_UPDATE = "E012052";
	public static final String CUSCOM_TEAM_UPDATE = "E012062";
	public static final String CUSCOM_DEPTRIGHT_UPDATE = "E012072";
	public static final String CUSCOM_DEPT_UPDATE = "E012082";
	public static final String CUSCOM_ASSURE_UPDATE = "E012092";
	public static final String CUSCOM_INVEST_UPDATE = "E0120102";
	public static final String CUSCOM_REWARD_UPDATE = "E0120112";
	public static final String CUSCOM_CONTROLLER_UPDATE = "E0120142";

	public static final String CUSCOM_BASE_DELETE = "E012013";
	public static final String CUSCOM_FINAACIA_DELETE = "E012023";
	public static final String CUSCOM_STAFF_DELETE = "E012033";
	public static final String CUSCOM_BANK_DELETE = "E012043";
	public static final String CUSCOM_CONTACT_DELETE = "E012053";
	public static final String CUSCOM_TEAM_DELETE = "E012063";
	public static final String CUSCOM_DEPTRIGHT_DELETE = "E012073";
	public static final String CUSCOM_DEPT_DELETE = "E012083";
	public static final String CUSCOM_ASSURE_DELETE = "E012093";
	public static final String CUSCOM_INVEST_DELETE = "E0120103";
	public static final String CUSCOM_REWARD_DELETE = "E0120113";
	public static final String CUSCOM_CONTROLLER_DELETE = "E0120143";

	public static final String CUSCOM_BASE_QUERY = "E012014";
	public static final String CUSCOM_FINAACIA_QUERY = "E012024";
	public static final String CUSCOM_STAFF_QUERY = "E012034";
	public static final String CUSCOM_BANK_QUERY = "E012044";
	public static final String CUSCOM_CONTACT_QUERY = "E012054";
	public static final String CUSCOM_TEAM_QUERY = "E012064";
	public static final String CUSCOM_DEPTRIGHT_QUERY = "E012074";
	public static final String CUSCOM_DEPT_QUERY = "E012084";
	public static final String CUSCOM_ASSURE_QUERY = "E012094";
	public static final String CUSCOM_INVEST_QUERY = "E0120104";
	public static final String CUSCOM_REWARD_QUERY = "E0120114";
	public static final String CUSCOM_CONTROLLER_QUERY = "E0120144";

	// 额度管理(001=客户授信，002=授信额度，003=授信额度记录)

	public static final String CREDIT_ADD = "E050011";
	public static final String CREDIT_LIMIT_ADD = "E050021";
	public static final String CREDIT_RECORD_ADD = "E050031";

	public static final String CREDIT_UPDATE = "E050012";
	public static final String CREDIT_LIMIT_UPDATE = "E050022";
	public static final String CREDIT_RECORD_UPDATE = "E050032";

	public static final String CREDIT_DELETE = "E050013";
	public static final String CREDIT_LIMIT_DELETE = "E050023";
	public static final String CREDIT_RECORD_DELETE = "E050033";

	public static final String CREDIT_QUERY = "E050014";
	public static final String CREDIT_LIMIT_QUERY = "E050024";
	public static final String CREDIT_RECORD_QUERY = "E050034";

	// E10开是 财务管理(001=业务查询类型，02=手动对账（1收款信息,）)
	// 新增1 修改2 删除3 查询4
	public static final String FINANCE_CUS_BUSINESS_QUERY = "E100014";
	public static final String FINANCE_RECONCILIATION_QUERY = "E100214";
	
	//系统管理异常常量定义
	public static final String SYS_EDIT_PWD = "SYS10000";
	
	// 操作地区信息时出现异常
	public static final String SYS_AREA_OPERATE = "SYS10001";
	
	
	
	
	
	// 贷后  监管
	public static final String AFTER_LOAD_QUERY="E08004";
	
	
	//合同管理
	public static final String CONTRACT_TAB_PARAMETER_ADD="E030011";
	public static final String CONTRACT_TAB_PARAMETER_UPDATE="E030012";
	public static final String CONTRACT_TAB_PARAMETER_QUERY="E030013";
	
	
	public static final String CONTRACT_QUERY="E030023";
	
	public static final String CONTRACT_PRODUCT_ADD = "E11001";
	public static final String CONTRACT_PRODUCT_UPDATE = "E11002";
	public static final String CONTRACT_PRODUCT_QUERY = "E11003";
	
   // 请求成功
   public static final String MOBILE_OK = "EB200";
   // 拒绝请求
   public static final String MOBILE_ACCEPTED = "EB202";
   // 请求参数有误
   public static final String MOBILE_BAD_REQUEST = "EB400";
   // 服务器已经理解请求，但是拒绝执行它
   public static final String MOBILE_FORBIDDEN = "EB403";
   // 服务器异常
   public static final String MOBILE_SERVER_ERROR = "EB500";
   
   // 会话异常
   public static final String MOBILE_SESSION = "EB10001";
   // 登录异常：密码或用户名错误
   public static final String MOBILE_LOGIN = "EB10002";
   // 验证码错误
   public static final String MOBILE_CAPTCHA = "EB10003";
   // 用户被锁
   public static final String MOBILE_USER_LOCK = "EB10004";
   // 认证失败
   public static final String MOBILE_AUTHENTICATION_FAILURE = "EB10005";


   //----------------通用
   //限制
   public static final String MOBILE_COMM_LIMIT = "EB00001";
   //数据不存在
   public static final String MOBILE_COMM_NO_EXIST = "EB00404";
   
   

}
