/**
 * @Title: Constants.java
 * @Package com.xlkfinance.bms.web.constant
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳前海小科互联网金融服务有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月10日 下午6:17:47
 * @version V1.0
 */
package com.xlkfinance.bms.web.constant;

public class Constants {

	public static final int DEFAULT_PAGE_SIZE = 5;
	public static final String DEFAULT_PAGE_NAME = "page";
	public static final String CONTEXT_PATH = "ctx";
	public static final String SERVER_IMG_PATH = "sip";

	public static final String LOGIN_SESSION_DATANAME = "users";
	public static final String LOGIN_URL = "login";
	public static final String LOGIN_SUCCESS_URL = "index";
	public static final String LOGIN_LOGIN_OUT_URL = "loginout";
	public static final String LOGIN_MSG = "loginMsg";
	public static final String USERNAME_IS_NULL = "用户名为空!";
	public static final String LOGIN_IS_EXIST = "该用户已登录!";
	public static final String UNKNOWN_SESSION_EXCEPTION = "异常会话!";
	public static final String UNKNOWN_ACCOUNT_EXCEPTION = "账号错误!";
	public static final String INCORRECT_CREDENTIALS_EXCEPTION = "密码错误!";
	public static final String UNKNOWN_PWD_ACCOUNT_EXCEPTION = "账号或密码错误!";
	public static final String LOCKED_ACCOUNT_EXCEPTION = "账号已被锁定，请与系统管理员联系!";
	public static final String INCORRECT_CAPTCHA_EXCEPTION = "验证码错误!";
	public static final String AUTHENTICATION_EXCEPTION = "您没有授权!";
	public static final String UNKNOWN_EXCEPTION = "出现未知异常,请与系统管理员联系!";
	public static final String TREE_GRID_ADD_STATUS = "add";
	public static final String POST_DATA_SUCCESS = "数据更新成功!";
	public static final String POST_DATA_FAIL = "提交失败了!";
	public static final String GET_SQL_LIKE = "%";
	public static final String IS_FUNCTION = "F";
	public static final String PERSISTENCE_STATUS = "A";
	public static final String PERSISTENCE_DELETE_STATUS = "I";
	public static final String SYSTEM_ADMINISTRATOR = "admin";
	public static final String NULL_STRING = "";
	public static final String IS_DOT = ".";
	public static final String HQL_LIKE = "like";
	public static final String TEXT_TYPE_PLAIN = "text/plain";
	public static final String TEXT_TYPE_HTML = "text/html";
	public static final String FUNCTION_TYPE_O = "O";
	public static final String TREE_STATUS_OPEN = "open";
	public static final String TREE_STATUS_CLOSED = "closed";
	public static final String IS_EXT_SUBMENU = " 或可能包含菜单!";
	public static final String SHIRO_USER = "shiroUser";
	public static final String LOGS_INSERT = "insert:";
	public static final String LOGS_INSERT_TEXT = "插入:";
	public static final String LOGS_INSERT_NAME = "insertLogs";
	public static final String LOGS_UPDATE = "update:";
	public static final String LOGS_UPDATE_TEXT = "更新:";
	public static final String LOGS_UPDATE_NAME = "updateLogs";
	public static final String LOGS_DELETE = "delete:";
	public static final String LOGS_DELETE_TEXT = "删除:";
	public static final String LOGS_DELETE_NAME = "deleteLogs";
	public static final String LOGS_TB_NAME = "Log";
	public static final String FILE_SUFFIX_SQL = ".sql";
	public static final String FILE_SUFFIX_ZIP = ".zip";

	// 合同类别

	public static final String CONTRACT_CATELOG_DK = "DK";// 贷款合同
	public static final String CONTRACT_CATELOG_BZ = "BZ";// 保证合同
	public static final String CONTRACT_CATELOG_DY = "DY";// 抵押合同
	public static final String CONTRACT_CATELOG_ZY = "ZY";// 质押合同
	public static final String CONTRACT_CATELOG_SX = "SX";// 授信合同
	public static final String CONTRACT_CATELOG_JJ = "JJ";// 借款借据
	public static final String CONTRACT_CATELOG_ZK = "ZK";// 展期合同
	public static final String CONTRACT_CATELOG_ZKBZ = "ZKBZ";// 展期保证
	public static final String CONTRACT_CATELOG_LLBG = "LLBG";// 利率变更
	
	
	/**
	 * 数据权限--集体数据(受保护的数据)
	 */
	public static final int DATA_SCOPE_PROTECED=2;
	/**
	 * 数据权限--私有数据(仅自己)
	 */
	public static final int DATA_SCOPE_PRIVATE =1;
	
	/**
	 * Q房集团层级=1
	 */
	public static final int QFANG_GROUP_LAYER=1;
	public static final int ORG_TREE_LAYER=2;
	//数据权限--用户编号
	public static final String DATA_USERIDS = "dataUserIds";
	
	/**
	 * 状态--有效
	 */
	public static final int STATUS_ENABLED = 1;
	/**
	 * 状态--失效
	 */
	public static final int STATU_DISABLED = 0;

	/**
	 * 系统用户状态0、无效1、有效2、离职
	 */
    public static final int STATU_DISABLED_2 = 2;
	
}
