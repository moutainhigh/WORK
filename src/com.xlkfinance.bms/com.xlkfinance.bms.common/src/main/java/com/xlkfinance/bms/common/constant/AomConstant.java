package com.xlkfinance.bms.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年7月6日下午2:55:14 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation：机构管理平台常量 <br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class AomConstant {
   /**
    * 系统：信贷系统=1，机构管理系统=2
    */
   public static final Integer BMS_SYSTEM = 1;
   public static final Integer AOM_SYSTEM = 2;
   /**
    * 用户类型：机构=1，合伙人=2，员工=3
    */
   public static final Integer USER_TYPE_ORG = 1;
   public static final Integer USER_TYPE_PARTNER = 2;
   public static final Integer USER_TYPE_EMP = 3;
   public static final Map<Integer, String> USER_TYPE_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(1, "机构用户");
         put(2, "合伙人用户");
         put(3, "普通员工用户");
      }
   };
   /**
    *  有效状态
    */
   public static final Integer STATUS_ENABLED = 1;
   /**
    *  失效状态
    */
   public static final Integer STATUS_DISABLED = 2;

   /**
    * 短信发送模板
    */
   public static final String SMS_TEMPLATE_ONE = "sms_template_one";
   /**
    * 随机位数
    */
   public static final String RAMDOM_BIT = "ramdom_bit";
   /**
    * 短信验证码类型,1表示注册验证,2表示密码找回
    */
   public static final Integer VALIDATE_CODE_CATEGORY_1 = 1;
   public static final Integer VALIDATE_CODE_CATEGORY_2 = 2;
   
   /**
    * 合作申请状态 1、未提交,2、已提交,3、审核中,4、审核通过,5、审核不通过
    */
   public static final Integer ORG_COOPERATE_APPLY_STATUS_1 = 1;
   public static final Integer ORG_COOPERATE_APPLY_STATUS_2 = 2;
   public static final Integer ORG_COOPERATE_APPLY_STATUS_3 = 3;
   public static final Integer ORG_COOPERATE_APPLY_STATUS_4 = 4;
   public static final Integer ORG_COOPERATE_APPLY_STATUS_5 = 5;
   public static final Map<Integer, String> ORG_COOPERATE_APPLY_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(1, "未提交");
         put(2, "已提交");
         put(3, "审核中");
         put(4, "审核通过");
         put(5, "审核不通过");
      }
   };
   /**
    * 角色 1为机构2合伙人（系统初始化数据）
    */
   public static final Integer ORG_ROLE_1 = 1;
   /**
    * 角色 1为机构2合伙人（系统初始化数据）
    */
   public static final Integer ORG_ROLE_2 = 2;
   
   /**
    * 数据权限1表示私有,2表示所有
    */
   public static final Integer DATE_SCOPE_1 = 1;
   /**
    * 数据权限1表示私有,2表示所有
    */
   public static final Integer DATE_SCOPE_2 = 2;
   
   /**
    * 认证状态1:未认证,2表示认证中,3、已认证
    */
   public static final Integer ORG_AUDIT_STATUS_1 = 1;
   public static final Integer ORG_AUDIT_STATUS_2 = 2;
   public static final Integer ORG_AUDIT_STATUS_3 = 3;
   public static final Map<Integer, String> ORG_AUDIT_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(1, "未认证");
         put(2, "认证中");
         put(3, "已认证");
      }
   };
   
   /**
    * 合作状态,1:未合作,2表示已合作,3表示已过期,4合作待确认
    */
   public static final Integer ORG_COOPERATE_STATUS_1 = 1;
   public static final Integer ORG_COOPERATE_STATUS_2 = 2;
   public static final Integer ORG_COOPERATE_STATUS_3 = 3;
   public static final Integer ORG_COOPERATE_STATUS_4 = 4;
   public static final Map<Integer, String> ORG_COOPERATE_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(1, "未合作");
         put(2, "已合作");
         put(3, "已过期");
         put(4, "合作待确认");
      }
   };
   /**
    * 用户类型,1表示机构,2表示合伙人,3表示员工(机构下的员工)
    */
   public static final Integer USER_TYPE_1 = 1;
   public static final Integer USER_TYPE_2 = 2;
   public static final Integer USER_TYPE_3 = 3;
   /**
    * 保证金变更流水状态,10表示同意变更,20表示不同意变更,1待审核
    */
   public static final Integer FUND_FLOW_STATUS_1 = 1;
   public static final Integer FUND_FLOW_STATUS_10 = 10;
   public static final Integer FUND_FLOW_STATUS_20 = 20;
   
   /**
    * 保证金额调整状态,1表示正常（不做调整），2表示待审核,3表示已调整
    */
   public static final Integer FUND_FLOW_APPLY_STATUS_1= 1;
   public static final Integer FUND_FLOW_APPLY_STATUS_2= 2;
   public static final Integer FUND_FLOW_APPLY_STATUS_3= 3;
   public static final Map<Integer, String> FUND_FLOW_APPLY_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(1, "正常");
         put(2, "待审核");
         put(3, "已调整");
      }
   };
   /**
    * 合伙人合作申请状态 1、未申请,10、审核中,20、审核通过,30、审核不通过
    */
   public static final Integer ORG_PARTNER_COOPERATE_APPLY_STATUS_1 = 1;
   public static final Integer ORG_PARTNER_COOPERATE_APPLY_STATUS_10 = 10;
   public static final Integer ORG_PARTNER_COOPERATE_APPLY_STATUS_20 = 20;
   public static final Integer ORG_PARTNER_COOPERATE_APPLY_STATUS_30 = 30;

   public static final Map<Integer, String> ORG_PARTNER_COOPERATE_APPLY_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
    	 put(1, "未申请");
         put(10, "审核中");
         put(20, "审核通过");
         put(30, "审核不通过");
      }
   };
   /**
    * 财务状态:未申请=1，审核中=2，已放款=3，审核驳回=4，审核否决=5
    */
   public static final Integer FINANCE_STATUS_1 = 1;
   public static final Integer FINANCE_STATUS_2 = 2;
   public static final Integer FINANCE_STATUS_3 = 3;
   public static final Integer FINANCE_STATUS_4 = 4;
   public static final Integer FINANCE_STATUS_5 = 5;
   /**
    * 息费结算状态:未申请=1，审核中=2，已结算=3，审核驳回=4，审核否决=5
    */
   public static final Integer SETTLE_STATUS_1 = 1;
   public static final Integer SETTLE_STATUS_2 = 2;
   public static final Integer SETTLE_STATUS_3 = 3;
   public static final Integer SETTLE_STATUS_4 = 4;
   public static final Integer SETTLE_STATUS_5 = 5;

   /**
    * 项目类型 授信=1、贷款=2、额度提取=3、贷款展期=4  授信and贷款=5  6=机构提交业务
    */
   public static final Integer PROJECT_TYPE_1 = 1;
   public static final Integer PROJECT_TYPE_2 = 2;
   public static final Integer PROJECT_TYPE_3 = 3;
   public static final Integer PROJECT_TYPE_4 = 4;
   public static final Integer PROJECT_TYPE_5 = 5;
   public static final Integer PROJECT_TYPE_6 = 6;
   
   /**
    * 业务申请状态（合作机构web端展示）1、未提交2、待审核3、审核中4、审核通过5、审核失败
    */
   public static final Integer BUSINESS_REQUEST_STATUS_1 = 1;
   public static final Integer BUSINESS_REQUEST_STATUS_2 = 2;
   public static final Integer BUSINESS_REQUEST_STATUS_3 = 3;
   public static final Integer BUSINESS_REQUEST_STATUS_4 = 4;
   public static final Integer BUSINESS_REQUEST_STATUS_5 = 5;
   public static final Map<String, String> BUSINESS_REQUEST_STATUS_MAP = new HashMap<String, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put("1", "未提交");
	         put("2", "待审核");
	         put("3", "审核中");
	         put("4", "审核通过");
	         put("5", "审核失败");
	      }
	   };
	/**
	 * 业务申请关闭状态 1关闭2正常
	 */
   public static final Integer IS_CLOSED_1 = 1;
   public static final Integer IS_CLOSED_2 = 2;
   
   /**
    * 资金流转类型 ,1表示放款，2表示回款
    */
   public static final Integer FLOW_TYPE_1 = 1;
   public static final Integer FLOW_TYPE_2 = 2;
   
   /**
    * 结算类型:机构给平台的利息费=1,平台给合伙人的佣金=2
    */
   public static final Integer SETTLE_TYPE_1 = 1;
   public static final Integer SETTLE_TYPE_2 = 2;
   
   /**
    * 返点状态1、有返点2、无返点
    */
   public static final Integer REBATE_TYPE_1 = 1;
   public static final Integer REBATE_TYPE_2 = 2;
   
   /**
    * 返点率
    */
   public static final double REBATE_ONE = 0.01;
   public static final double REBATE_TWO = 0.02;
   public static final double REBATE_THREE = 0.03;
   public static final double REBATE_FOUR = 0.04;
   
   /**
    * 放款总金额
    */
   public static final double LOAN_MONEY_ONE = 10000000;
   public static final double LOAN_MONEY_TWO = 20000000;
   public static final double LOAN_MONEY_THREE = 30000000;
   public static final double LOAN_MONEY_FOUR = 40000000;
   
   /**
    * 数据字典中合作合同类型
    */
   public static final String ORG_COOPERATE_CONTRACT_TYPE = "ORG_COOPERATE_CONTRACT_TYPE";
   
	/**
	 * 业务申请驳回状态 是否驳回1、完全驳回2、正常3、补充资料4、拒单
	 */
    public static final Integer IS_REJECT_1 = 1;
    /**
	 * 业务申请驳回状态 是否驳回1、完全驳回2、正常3、补充资料4、拒单
	 */
	public static final Integer IS_REJECT_2 = 2;
	/**
	 * 业务申请驳回状态 是否驳回1、完全驳回2、正常3、补充资料4、拒单
	 */
	public static final Integer IS_REJECT_3 = 3;
	/**
	 * 业务申请驳回状态 是否驳回1、完全驳回2、正常3、补充资料4、拒单
	 */
	public static final Integer IS_REJECT_4 = 4;
	/**
	 * 订单分配状态 1未分配2已分配
	 */
	public static final Integer IS_ASSIGNED_1 = 1;
	public static final Integer IS_ASSIGNED_2 = 2;
	/**
	 * 保证金记录类型：收保证金=1，退保证金=2
	 */
	public static final Integer ASSURE_FUND_RECORD_TYPE_1 = 1;
	public static final Integer ASSURE_FUND_RECORD_TYPE_2 = 2;
	/**
	 * 发送短信开关 1：发送 2：关闭
	 */
	public static final Integer SEND_MSG_FLAG_1 = 1;
	public static final Integer SEND_MSG_FLAG_2 = 1;
  
	/**
	 * 机构分配状态 -1未分配1已分配
	 */
	public static final Integer ORG_IS_ASSIGNED_1 = -1;
	public static final Integer ORG_IS_ASSIGNED_2 = 1;
	/**
	 * 拒单类型1、黑名单2、其他原因
	 */
	public static final Integer REJECT_TYPE_1 = 1;
	/**
	 * 拒单类型1、黑名单2、其他原因
	 */
	public static final Integer REJECT_TYPE_2 = 2;
}
