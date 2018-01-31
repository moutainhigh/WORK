package com.xlkfinance.bms.common.constant;

/**
 * 
 * @Title: SysLogTypeConstant.java
 * @Description: 日志类型常量接口
 * @author : Mr.Cai
 * @date : 2014年12月25日 上午10:02:08
 * @email: caiqing12110@vip.qq.com
 */
public interface SysLogTypeConstant {

	/**
	 * 添加
	 */
	public static final String LOG_TYPE_ADD = "添加";

	/**
	 * 删除
	 */
	public static final String LOG_TYPE_DELETE = "删除";

	/**
	 * 修改
	 */
	public static final String LOG_TYPE_UPDATE = "修改";

	/**
	 * 登录
	 */
	public static final String LOG_TYPE_LOGIN = "登录";

	/**
	 * 登出
	 */
	public static final String LOG_TYPE_LOGOUT = "登出";
	public static final String LOG_TYPE_UPLOAD_FILE = "上传文件";
	/**
    * 办理动态日志
    */
   public static final String LOG_TYPE_BIZ_HANDLE = "贷中-业务办理";
   public static final String LOG_TYPE_FINANCE_HANDLE = "贷中-财务受理";
   public static final String LOG_TYPE_CHECK_DOCUMENT = "贷中-查档";
   public static final String LOG_TYPE_CHECK_LITIGATION = "贷中-查诉讼";
}
