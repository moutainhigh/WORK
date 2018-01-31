/**
 * @Title: BusinessModuel.java
 * @Package com.xlkfinance.bms.common.constant
 * @Description: 业务模块
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月24日 下午4:01:49
 * @version V1.0
 */
package com.xlkfinance.bms.common.constant;

/**
 * @ClassName: BusinessModule
 * @Description: 业务模块
 * @author: Simon.Hoo
 * @date: 2015年2月27日 上午11:48:06
 */
public interface BusinessModule {
   // 系统模块名
   public static final String MODUEL_AFTERLOAN = "afterloan"; // 贷后管理
   public static final String MODUEL_INLOAN = "inloan"; // 贷中管理
   public static final String MODUEL_BEFORELOAN = "beforeloan";// 贷前管理
   public static final String MODUEL_CONTRACT = "contract";// 合同管理
   public static final String MODUEL_CREDITS = "credits";// 额度管理
   public static final String MODUEL_CUSTOMER = "customer";// 客户管理
   public static final String MODUEL_FINANCE = "finance";// 财务管理
   public static final String MODUEL_MORTGAGE = "mortgage";// 抵制押物
   public static final String MODUEL_REPAYMENT = "repayment";// 还款管理
   public static final String MODUEL_SYSTEM = "system";// 系统管理
   public static final String MODUEL_TASK = "task";// 任务管理
   public static final String MODUEL_WORKFLOW = "workflow";// 工作流
   public static final String MODUEL_PRODUCT = "product";//产品
   public static final String MODUEL_PUSH = "push";//消息推送
   public static final String MODUEL_REPORT = "report";//统计分析报表
   public static final String MODUEL_OTHER_REPORT = "otherReport";//统计分析报表
   public static final String MODUEL_PARTNER = "partner";//合作结构项目
   public static final String MODUEL_ELEMENT = "element";//要件借出
   
   public static final String MODUEL_ORG_SYSTEM = "system";//机构管理系统模块
   public static final String MODUEL_CHECKAPI = "checkapi";//查档模块
   public static final String MODUEL_FORE_AFTERLOAN = "foreafterloan";//赎楼贷后模块
   public static final String MODUEL_FDD_AFTERLOAN = "fddafterloan";//房抵贷贷后模块

   /**
    * 机构管理
    */
   public static final String MODUEL_ORG = "org";
   /**
    * 合伙人
    */
   public static final String MODUEL_ORG_PARTNER = "partner";

   /**
    * 合作机构系统--业务申请模块
    */
   public static final String MODUEL_ORG_PROJECT = "project";
   /**
    * 合作机构系统--客户模块
    */
   public static final String MODUEL_ORG_CUSTOMER = "customer";

   /**
    * 机构管理-财务
    */
   public static final String MODUEL_ORG_FINANCE = "finance";
   /**
    * 费用
    */
   public static final String MODUEL_ORG_FEE = "fee";
	/** 
	 * 法定节假日
	 */
   public static final String MODUEL_WORK_DAY = "workday";
   /**
    * 抵押贷模块
    */
   public static final String MODUEL_PROJECT = "project";
}
