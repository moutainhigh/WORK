package com.xlkfinance.bms.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 系统常量类
 * @author huxinlong
 *
 */
public class Constants {
   public static final String ERROR_SENDFAIL="连接网络超时！请稍后查询交易记录";
   
   /**
    * 系统管理员id
    */
   public static final String SYSTEM_ADMIN_PID = "system.admin.pid";
   
   /**
    * 数据权限--集体数据(受保护的数据)
    */
   public static final int DATA_SCOPE_PROTECED=1;
   /**
    * 数据权限--私有数据(仅自己)
    */
   public static final int DATA_SCOPE_PRIVATE =2;
   //有效状态
   public static final Integer STATUS_ENABLED = 1;
   //失效状态
   public static final Integer STATUS_DISABLED = -1;
   /**
    * 是否确认：未确认=1，已确认=2
    */
   public static final Integer NO_CONFIRM =1;
   public static final Integer IS_CONFIRM =2;
   
   /**
    * 公共常量
    */
   
   public static final Integer COMMONE_ZERO=0;
   /**
    * 赎楼申请状态:1、待客户经理提交2、待部门经理审批3、待业务总监审批4、待审查员审批5、待风控初审
    * 6、待风控复审7、待风控终审8、待风控总监审批9、待总经理审批10、已审批11、已放款12、业务办理已完成13、已归档14、待审查主管审批15、待合规复审
    */
   public static final Integer FORECLOSURE_STATUS_1 = 1;
   public static final Integer FORECLOSURE_STATUS_2 = 2;
   public static final Integer FORECLOSURE_STATUS_3 = 3;
   public static final Integer FORECLOSURE_STATUS_4 = 4;
   public static final Integer FORECLOSURE_STATUS_5 = 5;
   public static final Integer FORECLOSURE_STATUS_6 = 6;
   public static final Integer FORECLOSURE_STATUS_7 = 7;
   public static final Integer FORECLOSURE_STATUS_8 = 8;
   public static final Integer FORECLOSURE_STATUS_9 = 9;
   public static final Integer FORECLOSURE_STATUS_10 = 10;
   public static final Integer FORECLOSURE_STATUS_11 = 11;
   public static final Integer FORECLOSURE_STATUS_12 = 12;
   public static final Integer FORECLOSURE_STATUS_13 = 13;
   public static final Integer FORECLOSURE_STATUS_14 = 14;
   public static final Integer FORECLOSURE_STATUS_15 = 15;
   public static final Map<String, String> FORECLOSURE_STATUS_MAP = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;
      {
         put("1", "待客户经理提交");
         put("2", "待部门经理审批");
         put("3", "待业务总监审批");
         put("4", "待审查员审批");
         put("5", "待风控初审");
         put("6", "待风控复审");
         put("7", "待风控终审");
         put("8", "待风控总监审批");
         put("9", "待总经理审批");
         put("10", "已审批");
         put("11", "已放款");
         put("12", "业务办理已完成");
         put("13", "已归档（解保）");
         put("14", "待审查主管审批");
         put("15", "待合规复审");
      }
   };
   public static final Map<String, String> BUSINESS_SOURCE_MAP = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;
      {
         put("13779", "银行");
         put("13780", "中介");
         put("13781", "朋友");
         put("13782", "合作机构");
         put(null, "其它");
      }
   };
   public static final Integer INNER_ORDER = 1;
   public static final Integer OUT_ORDER = 2;
   public static final Map<String, String> INNER_OR_OUT_MAP = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;
      {
         put("1", "内单");
         put("2", "外单");
      }
   };
   public static final Map<String, String> LOAN_WAY_MAP = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;
      {
         put("1", "按揭");
         put("2", "组合贷");
         put("3", "公积金贷");
         put("4", "一次性付款");
         put("5", "经营贷");
         put("6", "消费贷");
      }
   };
   //贷中 -------------------------start
   /**
    * 财务办理的收费状态（BIZ_LOAN_FINANCE_HANDLE.STATUS）：未收费=1，已收费=2，已放款=3，放款未完结=4，放款申请中=5
    */
   public static final int REC_STATUS_NO_CHARGE =1;
   public static final int REC_STATUS_ALREADY_CHARGE =2;
   public static final int REC_STATUS_ALREADY_LOAN =3;
   public static final int REC_STATUS_LOAN_NO_FINISH =4;
   public static final int REC_STATUS_LOAN_APPLY =5;
   /**
    * 财务办理列表类型：财务收费=1，财务放款=2
    */
   public static final int FINANCE_INDEX_TYPE_1 =1;
   public static final int FINANCE_INDEX_TYPE_2 =2;
   
   /**
    * 放款是否已完结：已完结=1，未完结=-1
    */
   public static final int LOAN_NO_FINISH =-1;
   public static final int LOAN_IS_FINISH =1;
   /**
    * 财务办理的收款项目(BIZ_LOAN_FINANCE_HANDLE.REC_PRO):咨询费=1，手续费=2，第一次放款=3，一次赎楼余额转二次放款=4，第二次放款=5，监管（客户）资金转入=6，佣金=7，展期费=8
    * 佣金=7,二次放款=9
    * 下户费=10
    * 居间服务费=11
    */
   public static final int REC_PRO_1 =1;
   public static final int REC_PRO_2 =2;
   public static final int REC_PRO_3 =3;
   public static final int REC_PRO_4 =4;
   public static final int REC_PRO_5 =5;
   public static final int REC_PRO_6 =6;
   public static final int REC_PRO_7 =7;
   public static final int REC_PRO_8 =8;
   public static final int REC_PRO_9 =9;
   public static final int REC_PRO_10 =10;
   public static final int REC_PRO_11 =11;
   public static final Map<Long, String> REC_PRO_MAP = new HashMap<Long, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Long(1), "咨询费");
         put(new Long(2), "手续费");
         put(new Long(3), "第一次放款");
         put(new Long(4), "一次赎楼余额转二次放款");
         put(new Long(5), "第二次放款");
         put(new Long(6), "监管（客户）资金转入");
         put(new Long(7), "佣金");
         put(new Long(8), "展期费");
         put(new Long(9), "二次放款");
         put(new Long(10), "下户费");
         put(new Long(11), "居间服务费");
      }
   };
   /**
    * 办理流程条目:发放贷款=1,赎楼=2,取旧证=3,注销抵押=4,过户=5,取新证  =6,抵押=7,回款=10
    */
   public static final Integer HANDLE_FLOW_ID_1 = 1;
   public static final Integer HANDLE_FLOW_ID_2 = 2;
   public static final Integer HANDLE_FLOW_ID_3 = 3;
   public static final Integer HANDLE_FLOW_ID_4 = 4;
   public static final Integer HANDLE_FLOW_ID_5 = 5;
   public static final Integer HANDLE_FLOW_ID_6 = 6;
   public static final Integer HANDLE_FLOW_ID_7 = 7;
   public static final Integer HANDLE_FLOW_ID_10 = 10;
   
   public static final Map<Integer, String> HANDLE_DYNAMIC_ROLE_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Integer(1), "");
         put(new Integer(2), "HOUSE_CLERK,DEPARTMENT_MANAGER");
         put(new Integer(3), "JUNIOR_ACCOUNT_MANAGER,DEPARTMENT_MANAGER");
         put(new Integer(4), "JUNIOR_ACCOUNT_MANAGER,GUARANTEE_DEPARTMENT_ASSISTANT,RESIDENT_COMMISSIONER");
         put(new Integer(5), "GUARANTEE_DEPARTMENT_ASSISTANT,RESIDENT_COMMISSIONER");
         put(new Integer(6), "GUARANTEE_DEPARTMENT_ASSISTANT,RESIDENT_COMMISSIONER");
         put(new Integer(7), "GUARANTEE_DEPARTMENT_ASSISTANT,RESIDENT_COMMISSIONER");
         put(new Integer(10), "FUND_MANAGER");
      }
   };
   
   /**
    * 业务办理 到账状态(BIZ_LOAN_HANDLE_INFO.REC_STATUS)：未到账=1，已到账=2
    */
   public static final int REC_STATUS_1 =1;
   public static final int REC_STATUS_2 =2;
   /**
    * 业务申请办理状态（BIZ_LOAN_HANDLE_INFO.APPLY_HANDLE_STATUS）：未申请=1，已申请=2，已完成（已解保）=3，已归档=4
    */
   public static final int APPLY_HANDLE_STATUS_1 =1;
   public static final int APPLY_HANDLE_STATUS_2 =2;
   public static final int APPLY_HANDLE_STATUS_3 =3;
   public static final int APPLY_HANDLE_STATUS_4 =4;   
   
   /**
    * 赎楼状态（BIZ_LOAN_HANDLE_INFO.FORECLOSURE_STATUS）：未赎楼=1，已赎楼=2,已驳回=3
    */
   public static final int NO_FORECLOSURE =1;
   public static final int IS_FORECLOSURE =2;
   public static final int TURN_DOWN_FORECLOSURE =3;
   /**
    * 余额确认（BIZ_LOAN_HOUSE_BALANCE.balance_confirm）：未确认=1，已确认=2
    */
   public static final int NO_BALANCE_CONFIRM =1;
   public static final int IS_BALANCE_CONFIRM =2;
   /**
    * 收件状态（BIZ_PROJECT.COLLECT_FILE_STATUS）：未收件=1，已收件=2
    */
   public static final int COLLECT_FILE_STATUS_1 =1;
   public static final int COLLECT_FILE_STATUS_2 =2;
   /**
    * 退件完结状态（BIZ_PROJECT.REFUND_FILE_STATUS）：未退件=1，已退件=2
    */
   public static final int REFUND_FILE_STATUS_1 =1;
   public static final int REFUND_FILE_STATUS_2 =2;
   /**
    * 要件列表
    */
   public static final Map<String, String> COLLECT_FILE_MAP = new HashMap<String, String>() {
      private static final long serialVersionUID = 1L;
      {
         put("SELLER_CARD_NO", "卖方身份证");
         put("REPAYMENT_CARD", "回款卡/折");
         put("FORECLOSURE_CARD", "赎楼卡/折");
         put("BUYER_CARD_NO", "买方身份证");
         put("SUPERVISE_CARD", "监管卡/折");
         put("BANK_CARD", "银行卡/折");
         put("NET_SILVER", "网上银行");
         put("SUPER_NET_SILVER", "超级网银");
         put("BANK_PHONE", "银行预留手机号");
         put("TRANSFER_LIMIT_MONEY", "转账限额");
         put("COMPANY_NAME", "公司名称");
         put("E_BANK_RELATE_ACCOUNT", "网银关联账户");
         put("OFFICIAL_SEAL", "公章");
         put("PERSONAL_SEAL", "私章");
         put("FINANCE_SEAL", "财务章");
         put("OPEN_ACCOUNT_LICENCE", "开户许可证");
         put("RESERVED_SIGNATURE_CARD", "预留印鉴卡");
         put("APPLY_SEAL", "刻章申请");
         put("BANK_DEBIT_CARD", "银行结算卡");
         put("BUSINESS_LICENSE_ORIGINAL", "营业执照正本");
         put("BUSINESS_LICENSE_COPY", "营业执照副本");
         put("TAX_REGISTRATION_ORIGINAL", "税务登记证正本");
         put("TAX_REGISTRATION_COPY", "税务登记证副本");
         put("ORG_CODE__LICENSE_ORIGINAL", "组织机构代码证正本");
         put("ORG_CODE__LICENSE_COPY", "组织机构代码证副本");
         put("CREDIT_ORG_CODE_LICENSE", "信用机构代码证");
         put("PUBLIC_CONVERSION_PRIVATE", "公转私");
         put("CHEQUE", "支票 剩余");
         put("CHEQUE_PWD_MACHINE", "支票密码器");
         put("POSTING_FEE", "过账费");
         put("WECHAT_PAY", "微信支付");
         put("PAY_TREASURE", "支付宝");
         put("ORDER", "其他");
         put("ID_CARD_NUMBER", "回款人身份证");
         put("PAYMENT_NAME", "回款账号名");
      }
   };
   /**
    * 查档审批状态(BIZ_CHECK_DOCUMENT.APPROVAL_STATUS)：未审批=1，不通过=2，通过=3，重新查档（已失效）=4
    */
   public static final int CHECK_DOCUMENT_APPROVAL_STATUS_1 =1;
   public static final int CHECK_DOCUMENT_APPROVAL_STATUS_2 =2;
   public static final int CHECK_DOCUMENT_APPROVAL_STATUS_3 =3;
   public static final int CHECK_DOCUMENT_APPROVAL_STATUS_4 =4;
   public static final Map<Integer, String> CHECK_DOCUMENT_APPROVAL_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Integer(1), "未审批");
         put(new Integer(2), "不通过");
         put(new Integer(3), "通过");
         put(new Integer(4), "重新查档");
      }
   };
   /**
    * 查档状态(BIZ_CHECK_DOCUMENT.CHECK_STATUS)：未查档=1，抵押=2，有效=3，无效=4，查封=5，抵押查封=6，轮候查封=7
    */
   public static final int CHECK_DOCUMENT_STATUS_1 =1;
   public static final int CHECK_DOCUMENT_STATUS_2 =2;
   public static final int CHECK_DOCUMENT_STATUS_3 =3;
   public static final int CHECK_DOCUMENT_STATUS_4 =4;
   public static final int CHECK_DOCUMENT_STATUS_5 =5;
   public static final int CHECK_DOCUMENT_STATUS_6 =6;
   public static final int CHECK_DOCUMENT_STATUS_7 =7;
   public static final Map<Integer, String> CHECK_DOCUMENT_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Integer(1), "未查档");
         put(new Integer(2), "抵押");
         put(new Integer(3), "有效");
         put(new Integer(4), "无效");
         put(new Integer(5), "查封");
         put(new Integer(6), "抵押查封");
         put(new Integer(7), "轮候查封");
         put(new Integer(8), "再抵押");
      }
   };
   /**
    * 查档复核状态(BIZ_CHECK_DOCUMENT.RE_CHECK_STATUS)：未复核=1，重新复核=2，同意=3，不同意=4
    */
   public static final int CHECK_DOCUMENT_RE_CHECK_STATUS_1 =1;
   public static final int CHECK_DOCUMENT_RE_CHECK_STATUS_2 =2;
   public static final int CHECK_DOCUMENT_RE_CHECK_STATUS_3 =3;
   public static final int CHECK_DOCUMENT_RE_CHECK_STATUS_4 =4;
   public static final Map<Integer, String> CHECK_DOCUMENT_RE_CHECK_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Integer(1), "未复核");
         put(new Integer(2), "重新复核");
         put(new Integer(3), "同意");
         put(new Integer(4), "不同意");
      }
   };
   /**
    * 查诉讼审批状态(BIZ_CHECK_LITIGATION.APPROVAL_STATUS)：未审批=1，不通过=2，通过=3，重新查诉讼（已失效）=4
    */
   public static final int CHECK_LITIGATION_APPROVAL_STATUS_1 =1;
   public static final int CHECK_LITIGATION_APPROVAL_STATUS_2 =2;
   public static final int CHECK_LITIGATION_APPROVAL_STATUS_3 =3;
   public static final int CHECK_LITIGATION_APPROVAL_STATUS_4 =4;
   public static final Map<Integer, String> CHECK_LITIGATION_APPROVAL_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Integer(1), "未审批");
         put(new Integer(2), "不通过");
         put(new Integer(3), "通过");
         put(new Integer(4), "重新查诉讼");
      }
   };
   /**
    * 查诉讼状态(BIZ_CHECK_LITIGATION.CHECK_STATUS)：诉讼状态：诉讼状态：未查诉讼=1，无新增诉讼=2，有增诉讼=3，有新增诉讼非本人=4
    */
   public static final int CHECK_LITIGATION_STATUS_1 =1;
   public static final int CHECK_LITIGATION_STATUS_2 =2;
   public static final int CHECK_LITIGATION_STATUS_3 =3;
   public static final int CHECK_LITIGATION_STATUS_4 =4;
   public static final Map<Integer, String> CHECK_LITIGATION_STATUS_MAP = new HashMap<Integer, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Integer(1), "未查诉讼");
         put(new Integer(2), "无新增诉讼");
         put(new Integer(3), "有增诉讼");
         put(new Integer(4), "有新增诉讼非本人");
      }
   };
   /**
    * 执行岗备注状态:未备注=1，已备注=2
    */
   public static final int PERFORM_JOB_REMARK_STATUS_1 = 1;
   public static final int PERFORM_JOB_REMARK_STATUS_2 = 2;
   /**
    * 回款状态(BIZ_LOAN_REPAYMENT.STATUS)：未回款=1，已回款=2
    * 抵押贷回款状态：正常还款中=1，已结清=2，3=逾期还款中，4=已逾期
    */
   public static final int REPAYMENT_STATUS_1 =1;
   public static final int REPAYMENT_STATUS_2 =2;
   public static final int REPAYMENT_STATUS_3 =3;
   public static final int REPAYMENT_STATUS_4 =4;
   /**
    * 逾期费确认(BIZ_LOAN_OVERDUE_FEE.IS_CONFIRM)：未确认=1，已确认=2
    */
   public static final int NO_CONFIRM_OVERDUE_FEE =1;
   public static final int IS_CONFIRM_OVERDUE_FEE =2;
   /**
    * 逾期费付款方式（BIZ_LOAN_OVERDUE_FEE.PAYMENT_WAY）：付款方式：从尾款扣=1，转账收费=2
    */
   public static final int OVERDUE_FEE_PAYMENT_WAY_1 =1;
   public static final int OVERDUE_FEE_PAYMENT_WAY_2 =2;
   /**
    * 财务退款明细:退款项目(BIZ_LOAN_REFUND_DETAILS.refund_pro):监管资金转出=1,退付余额=2,应付佣金=3,应付手续费=4
    */
   public static final Map<Long, String> REFUND_PRO_MAP = new HashMap<Long, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Long(1), "监管资金转出");
         put(new Long(2), "退尾款");
         put(new Long(3), "退佣金");
         put(new Long(4), "退手续费");
      }
   };
   /**
    * 退费（退尾款，退手续费，退利息，退佣金）申请办理状态（(BIZ_LOAN_REFUND_TAIL_MONEY|BIZ_LOAN_REFUND_FEE|BIZ_LOAN_REFUND_FEE).APPLY_STATUS）
    * 申请状态：未申请=1，申请中=2，驳回=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
    */
   public static final int APPLY_REFUND_FEE_STATUS_1 =1;
   public static final int APPLY_REFUND_FEE_STATUS_2 =2;
   public static final int APPLY_REFUND_FEE_STATUS_3 =3;
   public static final int APPLY_REFUND_FEE_STATUS_4 =4;
   public static final int APPLY_REFUND_FEE_STATUS_5 =5;
   public static final int APPLY_REFUND_FEE_STATUS_6 =6;
   public static final int APPLY_REFUND_FEE_STATUS_7 =7;
   /**
    * 退款类型：退手续费=1，退咨询费=2，退尾款=3，退佣金=4
    */
   public static final int REFUND_FEE_TYPE_1 =1;
   public static final int REFUND_FEE_TYPE_2 =2;
   public static final int REFUND_FEE_TYPE_3 =3;
   public static final int REFUND_FEE_TYPE_4 =4;
   /**
    * 差异预警处理表的处理状态：失效=-1,未处理=1，已处理=2
    * BIZ_LOAN_HANDLE_DIFFER_WARN.STATUS
    */
   public static final int DISABLED_HANDLE_WARN_STATUS =-1;
   public static final int NOT_HANDLE_WARN_STATUS =1;
   public static final int IS_HANDLE_WARN_STATUS =2;
   /**
    * 处理类型：必须处理=1，非必须处理=2   系统查档=3
    */
   public static final int WARN_HANDLE_TYPE_1 =1;
   public static final int WARN_HANDLE_TYPE_2 =2;
   public static final int WARN_HANDLE_TYPE_3 =3;
   /**
    * 未处理预警事项的数量
    */
   public static final String NOT_HANDLE_WARN_COUNT = "NOT_HANDLE_WARN_COUNT";
   /**
    * 工作流key
    */
   //退尾款工作流
   public static final String REFUND_TAIL_MONEY_WORKFLOW_ID = "refundTailMoneyProcess";
   //退手续费工作流
   public static final String REFUND_FEE_WORKFLOW_ID = "refundFeeProcess";
   //退利息工作流
   public static final String REFUND_INTEREST_FEE_WORKFLOW_ID = "refundInterestFeeProcess";
   //退佣金工作流
   public static final String REFUND_COMMISSION_WORKFLOW_ID = "refundCommissionProcess";
   public static final Map<Long, String> REFUND_FEE_MAP = new HashMap<Long, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Long(1), "refundFeeProcess");
         put(new Long(2), "refundInterestFeeProcess");
         put(new Long(3), "refundTailMoneyProcess");
         put(new Long(4), "refundCommissionProcess");
      }
   };
   public static final Map<Long, String> WT_REFUND_FEE_MAP = new HashMap<Long, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Long(1), "refundFeeProcess");
         put(new Long(2), "refundInterestFeeProcess");
         put(new Long(3), "WTrefundTailMoneyProcess");
         put(new Long(4), "refundCommissionProcess");
      }
   };
   /**
    * 退款工作流的第一个节点的处理角色
    * 退手续费：财务审核
    * 退利息：部门经理
    * 退尾款：贷后经理
    * 退佣金：财务审核
    */
   public static final Map<Long, String> REFUND_FEE_ROLE_CODE_MAP = new HashMap<Long, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new Long(1), "FINANCE_CHECK_ROLE");
         put(new Long(2), "DEPARTMENT_MANAGER");
         put(new Long(3), "POST_LOAN_MANAGEMENT");
         put(new Long(4), "FINANCE_CHECK_ROLE");
      }
   };
   
   
   /**
    * 业务办理申请状态
    */
   public static final Map<Long, String> BIZ_APPLY_HANDLE_STATUS_MAP = new HashMap<Long, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put(new Long(1), "未申请");
	         put(new Long(2), "已申请");
	         put(new Long(3), "已完成");
	         put(new Long(4), "已归档");
	         put(null, "未知");
	      }
	   };
	   
	   /**
	    * 退款申请状态
	    */
   public static final Map<Long, String> BACK_FEE_APPLY_HANDLE_STATUS_MAP = new HashMap<Long, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put(new Long(1), "未申请");
	         put(new Long(2), "申请中");
	         put(new Long(3), "驳回");
	         put(new Long(4), "审核中");
	         put(new Long(5), "审核通过");
	         put(new Long(6), "审核不通过");
	         put(new Long(7), "已归档");
	         put(null, "未知");
	      }
	   };
   /**
    * 
    * 申请状态：未申请=1，申请中=2，驳回=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
    */
   public static final int APPLY_STATUS_1 =1;
   public static final int APPLY_STATUS_2 =2;
   public static final int APPLY_STATUS_3 =3;
   public static final int APPLY_STATUS_4 =4;
   public static final int APPLY_STATUS_5 =5;
   public static final int APPLY_STATUS_6 =6;
   public static final int APPLY_STATUS_7 =7;
   /**
    * 退佣金工作流
    */
   public static final String REFUND_COMMISSION_FEE_WORKFLOW_ID = "refundCommissionProcess";
   // 贷中 -------------------------end
   
   
   //----------------------------------资金机构管理begin------------------------------------------------

   /**
    * 合作项目申请状态,1--未申请 2--已申请  3：申请成功   4：申请拒绝
    */
   public static final int REQUEST_APPLY_STATUS_1 = 1;
   public static final int REQUEST_APPLY_STATUS_2 = 2;
   public static final int REQUEST_APPLY_STATUS_3 = 3;
   public static final int REQUEST_APPLY_STATUS_4 = 4;
   
   /**
    * 接口调用成功
    */
   public static final String REQUEST_SUCCSS = "0";
   
   /**
    * 放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
    */
   public static final int LOAN_STATUS_1 =1;
   /**
    * 放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
    */
   public static final int LOAN_STATUS_2 =2;
   /**
    * 放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
    */
   public static final int LOAN_STATUS_3 =3;
   /**
    * 放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
    */
   public static final int LOAN_STATUS_4 =4;
   /**
    * 放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败）
    */
   public static final int LOAN_STATUS_5 =5;
   /**
    * 放款状态（1：未申请 2：申请中3：放款中 4：放款成功 5：放款失败 6:驳回）
    */
   public static final int LOAN_STATUS_6 =6;
   
   /**
    * 审批状态（BIZ_PROJECT_PARTNER.APPROVAL_STATUS） 1:审核中 2:审核通过 3:审核未通过 4:驳回补件 5：未审核 
    */
   public static final int APPROVAL_STATUS_1=1;
   public static final int APPROVAL_STATUS_2=2;
   public static final int APPROVAL_STATUS_3=3;
   public static final int APPROVAL_STATUS_4=4;
   public static final int APPROVAL_STATUS_5=5;
   
   /**
    * 是否通知回调（BIZ_PROJECT_PARTNER.IS_NOTIFY） 1:否 2:是 
    */
   public static final int IS_NOTIFY_1=1;
   public static final int IS_NOTIFY_2=2;
   
   /**
    * 回调类型（BIZ_PROJECT_PARTNER.NOTIFY_TYPE） 1: 申请 2: 复议 3: 复议确认要款 4：放款
    */
   public static final int NOTIFY_TYPE_1=1;
   public static final int NOTIFY_TYPE_2=2;
   public static final int NOTIFY_TYPE_3=3;
   public static final int NOTIFY_TYPE_4=4;
   
   
   /**
    * 确认要款状态（BIZ_PROJECT_PARTNER.CONFIRM_LOAN_STATUS） （1：未发送  2：审核中 3：审核通过 4 审核不通过 ）  
    */
   public static final int CONFIRM_LOAN_STATUS_1=1;
   public static final int CONFIRM_LOAN_STATUS_2=2;
   public static final int CONFIRM_LOAN_STATUS_3=3;
   public static final int CONFIRM_LOAN_STATUS_4=4;
   
   
   
   /**
    * 还款回购类型 (BIZ_PROJECT_PARTNER.REPAYMENT_REPURCHASE_TYPE) （1：还款  2：回购）
    */
   public static final int REPAYMENT_REPURCHASE_TYPE_1=1;
   public static final int REPAYMENT_REPURCHASE_TYPE_2=2;
   
   
   
   
   
   /**
    * 还款、回购状态（BIZ_PROJECT_PARTNER.REPAYMENT_REPURCHASE_STATUS） 1:未申请2:已申请3:确认收到；4：未收到; 5：确认中; 6：己逾期  ; 7：坏帐; 8：正常; 
    * 	 9：宽限期内
    */
   public static final int REPAYMENT_REPURCHASE_STATUS_1=1;
   public static final int REPAYMENT_REPURCHASE_STATUS_2=2;
   public static final int REPAYMENT_REPURCHASE_STATUS_3=3;
   public static final int REPAYMENT_REPURCHASE_STATUS_4=4;
   public static final int REPAYMENT_REPURCHASE_STATUS_5=5;
   public static final int REPAYMENT_REPURCHASE_STATUS_6=6;
   public static final int REPAYMENT_REPURCHASE_STATUS_7=7;
   public static final int REPAYMENT_REPURCHASE_STATUS_8=8;
   public static final int REPAYMENT_REPURCHASE_STATUS_9=9;
   
   /**
    * 息费状态（BIZ_PROJECT_PARTNER.XIFEE_STATUS） 1:未发送 2:已发送3:未到账；4：已到账；  5：数据不存在
    */
   public static final int XIFEE_STATUS_1 = 1;
   public static final int XIFEE_STATUS_2 = 2;
   public static final int XIFEE_STATUS_3 = 3;
   public static final int XIFEE_STATUS_4 = 4;
   public static final int XIFEE_STATUS_5 = 5;
   
   /**
    * 小赢申请城市开放地区  001：北京  002：上海 003：深圳  004：广州
    */
   /**北京*/
   public static final String CITY_XIAOYING_BJ="001";
   /**上海*/
   public static final String CITY_XIAOYING_SH="002";
   /**深圳*/
   public static final String CITY_XIAOYING_SZ="003";
   /**广州*/
   public static final String CITY_XIAOYING_GZ="004";
   
   
   
   /**
    * 分期还款状态 （BIZ_PROJECT_PARTNER_REFUND.REFUND_STATUS）
    * 1：未申请，2：申请中，3：还款成功，4：还款失败
    */
   public static final int REFUND_STATUS_1 = 1;
   public static final int REFUND_STATUS_2 = 2;
   public static final int REFUND_STATUS_3 = 3;
   public static final int REFUND_STATUS_4 = 4;
   
   
   /**
    * 分期还款状态 （BIZ_PROJECT_PARTNER_REFUND.CURR_OVERDUE_STATUS）
    * 1:未逾期,2:逾期未结清,3:逾期已结清
    */
   public static final int CURR_OVERDUE_STATUS_1 = 1;
   public static final int CURR_OVERDUE_STATUS_2 = 2;
   public static final int CURR_OVERDUE_STATUS_3 = 3;
   
   
   
   
   
   
   /**
    * 小赢接口公钥
    */
//   public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDvZWrVlz2vK3ZFyd4T/BsrLw2zJlHggS9EIajwoNd8QWb3MOINUJ4u/WaOLrlUAMGX6G7flAhXGMTo/hyOqoYwq9xR6Lfc2u+1Dgbx8oMtkIRyunqW8SoJtxm2ZI+tazM4CEqXsHhKU7X8YJSRQ4buOmU30KQmJRB1F1+xN7O6PQIDAQAB";//加密的公钥
//   public static final String XIAOYING_PUBLIC_KEY = "xiaoying.partner.public.key";//加密的公钥
   
   //public static final String PUBLIC_KEY = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDYtNXUPoaB2kxJZjgX50YF1vN8EDyWvUSXDyBjc2LzCcXaN4KYc+2ZsveuI2pTHtNHkRPyAUUNCTmpjE8ivXJahy3lMRT8po1gWuh7oeFGfeu78YElRTTco6l+pEtOLNeHWGLzSpoymNuEm4zdjqjY2DzMf6Zpw4MI6KG/UzlZ5wIDAQAB";
   
   /**
    * 解密私钥
    */
//   public static final String PRIVATE_KEY = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAN8Y4Pz77bCDitKbw7VXbaYLDHbmy+JhH9UTs6Sq/xjbqC6muE/QBLVd5U1yEeU/i3xq++hwC+sMg2Y6i5epP73zUL8Jx6vaygnDRNEcisc47d7wAGPrF3ExJrKz/dlCCzz+9UHpmqQhrJO6idteseP0F5ZKk5wu3jIwY4GqbYyTAgMBAAECgYAnxUSedL67n8eIofHyiuJmHkQbkis4cP3+uyNkMIOHR+kkX94thDTCrDlrY/Fiv8A67st3dTn6reFiwP8Pi0FsYu2lCiSLDS+mYXRz6/iAFEJgZDmAmkNvbUSRd3S4amWu4kPlywjAWAnwBNBNA1VPYH2z0Ovjel+e8UsZUzjvMQJBAP30nQmuqmrTywXh472wzxP6MdN0sQw+UCVMCW4toG7qfitvtUqcp6RRspILlVk9OhqOctgLHpTPuK4rc0GqPKkCQQDg5KsWb3HlhvwJbOOWoW9O3CDWpe5u1Q9p24WIjcPgeGNSdrambisZ+iLQAT8VSohfTs0v+G5jlKhsrarVT2jbAkAVBpWh4ZY2dgaPHB6w69yZONO7QCQQcXBNRjhZqOlphXyguAT4gnPWxPTV5qOc2dbfr/+OJ42GoJQXx30paKDRAkEAw1V6UMtNlzoOUbV1XBEt18YU87s+BKAkeiKEsl3H44FNa4PXE2mfIDoF4Lskar7JAlc0m1ThtLabL4IMXqhRfwJAWQoXy8bqV18SPPJqsRU8y0WXsKQmiUk6d2atoCan/bGuwAq1+5M2OAS/srCAyEbYKisVuW5b4o8lWnb5zC8k+Q==";
//   public static final String PRIVATE_KEY = "qfang.partner.private.key";

   /**
    * 开发环境MD5Key
    */
//   public static final String MD5_KEY = "16dbf0a1892e8c6b99fc1f4758d07c8e";//开发环境
   
   //public static final String MD5_KEY = "b0184bce3d2a3c1bded1382c3086c4a1";//开发环境
   
   /**
    * 小赢的URL
    */
//   public static final String URL_XIAOYING = "http://qf.rc.yingzhongtong.com:8000/api/sld/qf";
//   public static final String XIAOYING_URL = "xiaoying.partner.api.url";
   
   
   
   /**小赢白名单操作用户*/
//   public static final String XIAOYING_PARAM_ADD_USER = "11";
//   public static final String XIAOYING_PARAM_ADD_USER = "xiaoying.partner.param.add.user";
   
   
   /**
    * 上传材料/补充材料方法
    */
   public static final String URL_XIAOYING_APPLY = "/apply";
   
   
   /**确认要款*/
   public static final String URL_XIAOYING_REQUEST_LOAN = "/request-loan";
   
   /**确认要款复议*/
   public static final String URL_XIAOYING_REQUEST_LOAN_REC = "/request-loan-rec";
   
   /**
    * 关闭单据接口
    */
   public static final String URL_XIAOYING_CLOSE_APPLY = "/close-apply";
   /**
    * 放款请求接口
    */
   public static final String URL_XIAOYING_SEND_LOAN = "/send-loan";

   /**
    * 提交复议接口
    */
   public static final String URL_XIAOYING_RE_APPLY = "/re-apply";
   /**
    * 发送还款、回购凭证接口
    */
   public static final String URL_XIAOYING_SEND_LOAN_VONCHER = "/send-loan-voucher";
   
   /**
    * 获取息费信息接口
    */
   public static final String URL_XIAOYING_XIFEE_LIST = "/xi-fee-list";
   /**
    * 发送批量支付息费指令
    */
   public static final String URL_XIAOYING_BATCH_SEND_XIFEE = "/batch-send-xi-fee";
   /**
    * 批量获取息费到款确认接口
    */
   public static final String URL_XIAOYING_BATCH_XIFEE_STATUS = "/batch-xi-fee-status";
   /**
    * 请求返回码 0
    */
   public static final int ERR_CODE_0 = 0;
   /**
    * 请求返回码 1
    */
   public static final int ERR_CODE_1 = 1;
   /**
    * 请求返回码 2
    */
   public static final int ERR_CODE_2 = 2;
   
   /**
    * 请求返回码 99
    */
   public static final int ERR_CODE_99 = 99;
   
   
   /* 小赢附件类型 ----------------------------------------------------------------
    * 业务申请办理状态（BIZ_PROJECT_PARTNER_FILE.ACCESSORY_TYPE）：
    * 	客户基本信息(loaner_base_info)=1,	交易方基本信息(counterparty_base_info)=2,
    * 	房产信息(house_info)=3,			交易信息(transation_info)=4,
    * 	还款来源信息(payment)=5,			征信信息(credit_info)=6,
    * 	合同协议信息(pact_info)=7,			其他材料(other_info)=8
    *   居间服务协议(agreement_info)=9
    */
   /**客户基本信息*/
   public static final String XIAOYING_ACCESSORY_TYPE_1="1";
   /**交易方基本信息*/
   public static final String XIAOYING_ACCESSORY_TYPE_2="2";
   /**房产信息*/
   public static final String XIAOYING_ACCESSORY_TYPE_3="3";
   /**交易信息*/
   public static final String XIAOYING_ACCESSORY_TYPE_4="4";
   /**还款来源信息*/
   public static final String XIAOYING_ACCESSORY_TYPE_5="5";
   /**征信信息*/
   public static final String XIAOYING_ACCESSORY_TYPE_6="6";
   /**合同协议信息*/
   public static final String XIAOYING_ACCESSORY_TYPE_7="7";
   /**其他材料*/
   public static final String XIAOYING_ACCESSORY_TYPE_8="8";
   /**居间服务协议*/
   public static final String XIAOYING_ACCESSORY_TYPE_9="9";
   
   
   //===========================统联常量 begin=====================================
   /*==================证件类型===================*/
   /**身份证*/
   public static final String PARTNER_ACCESSORY_TYPE_00="00";
   /**护照*/
   public static final String PARTNER_ACCESSORY_TYPE_01="01";
   /**军官证*/
   public static final String PARTNER_ACCESSORY_TYPE_02="02";
   /**士兵证*/
   public static final String PARTNER_ACCESSORY_TYPE_03="03";
   /**港澳居民来往内地通行证*/
   public static final String PARTNER_ACCESSORY_TYPE_04="04";
   /**户口本*/
   public static final String PARTNER_ACCESSORY_TYPE_05="05";
   /**外国护照*/
   public static final String PARTNER_ACCESSORY_TYPE_06="06";
   /**其它*/
   public static final String PARTNER_ACCESSORY_TYPE_07="07";
   /**文职证*/
   public static final String PARTNER_ACCESSORY_TYPE_08="08";
   /**警官证*/
   public static final String PARTNER_ACCESSORY_TYPE_09="09";
   /**台胞证*/
   public static final String PARTNER_ACCESSORY_TYPE_10="10";
   
   /*==================文件类型===================*/
   /**卖房人身份证*/
   public static final String PARTNER_ACCESSORY_TYPE_F00="F00";
   /**卖房人身份证-正*/
   public static final String PARTNER_ACCESSORY_TYPE_F00_0="F00_0";
   /**卖房人身份证-反*/
   public static final String PARTNER_ACCESSORY_TYPE_F00_1="F00_1";
   /**卖房人户口本*/
   public static final String PARTNER_ACCESSORY_TYPE_F01="F01";
   /**卖房人婚姻证明*/
   public static final String PARTNER_ACCESSORY_TYPE_F02="F02";
   
   /**买房人身份证*/
   public static final String PARTNER_ACCESSORY_TYPE_F03="F03";
   /**买房人户口本*/
   public static final String PARTNER_ACCESSORY_TYPE_F04="F04";
   /**买房人婚姻证明*/
   public static final String PARTNER_ACCESSORY_TYPE_F05="F05";
   /**房产证*/
   public static final String PARTNER_ACCESSORY_TYPE_F06="F06";
   /**房产查档单*/
   public static final String PARTNER_ACCESSORY_TYPE_F07="F07";
   /**交易合同*/
   public static final String PARTNER_ACCESSORY_TYPE_F08="F08";
   /**资金监管/托管协议*/
   public static final String PARTNER_ACCESSORY_TYPE_F09="F09";
   /**定金收据/转账凭证*/
   public static final String PARTNER_ACCESSORY_TYPE_F10="F10";
   /**首付款收据/转账凭证*/
   public static final String PARTNER_ACCESSORY_TYPE_F11="F11";
   /**银行贷款审批通过凭证*/
   public static final String PARTNER_ACCESSORY_TYPE_F12="F12";
   /**买家按揭贷款核实单原件*/
   public static final String PARTNER_ACCESSORY_TYPE_F13="F13";
   /**买房人征信报告*/
   public static final String PARTNER_ACCESSORY_TYPE_F14="F14";
   /**银行还款流水/收入证明*/
   public static final String PARTNER_ACCESSORY_TYPE_F15="F15";
   /**卖方人征信报告*/
   public static final String PARTNER_ACCESSORY_TYPE_F16="F16";
   /**法院诉讼查询结果截屏*/
   public static final String PARTNER_ACCESSORY_TYPE_F17="F17";
   /**借款合同*/
   public static final String PARTNER_ACCESSORY_TYPE_F18="F18";
   /**卖房人原按揭*/
   public static final String PARTNER_ACCESSORY_TYPE_F19="F19";
   /**抵押合同及还贷纪录*/
   public static final String PARTNER_ACCESSORY_TYPE_F20="F20";
   /**合作方申请表*/
   public static final String PARTNER_ACCESSORY_TYPE_F21="F21";
   /**项目申请审批表*/
   public static final String PARTNER_ACCESSORY_TYPE_F22="F22";
   /**推荐确认书*/
   public static final String PARTNER_ACCESSORY_TYPE_F23="F23";
   /**合作方服务协议*/
   public static final String PARTNER_ACCESSORY_TYPE_F24="F24";
   /**众安投保单*/
   public static final String PARTNER_ACCESSORY_TYPE_F25="F25";
   /**居间服务协议*/
   public static final String PARTNER_ACCESSORY_TYPE_F26="F26";
   /**公证处授权委托书*/
   public static final String PARTNER_ACCESSORY_TYPE_F27="F27";
   /**本金还款凭证*/
   public static final String PARTNER_ACCESSORY_TYPE_F28="F28";
   /**利息还款凭证*/
   public static final String PARTNER_ACCESSORY_TYPE_F29="F29";
   /**放款凭证*/
   public static final String PARTNER_ACCESSORY_TYPE_F50="F50";
   /**收款凭证*/
   public static final String PARTNER_ACCESSORY_TYPE_F51="F51";
   
   
   /*==============业务类别================*/
   /**1:交易类赎楼*/
   public static final int PARTNER_TL_TYPE_1 =1;
   /**2:非交易类赎楼*/
   public static final int PARTNER_TL_TYPE_2 =2;
   /**3:尾款贷*/
   public static final int PARTNER_TL_TYPE_3 =3;
   
   
   //===========================统联常量 end=====================================
   
   //----------------------------------资金机构管理end------------------------------------------------

   
   
   //===========================系统字典配置（key必须一致） begin=====================================
   
   /**机构合作接口放款额度限制*/
   public static final String SYS_LOOKUP_PARTNER_LOAN_LIMIT= "PARTNER_LOAN_LIMIT";
   /**统联放款额度*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_TL= "PARTNER_LOAN_LIMIT_TL";
   
   /**点融放款单笔额度*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_ONE= "PARTNER_LOAN_LIMIT_DR_ONE";
   /**点融放款单天额度*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_DAY= "PARTNER_LOAN_LIMIT_DR_DAY";
   /**点融放款单月额度*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_MONTH= "PARTNER_LOAN_LIMIT_DR_MONTH";
   /**点融放款总额度*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_LIMIT_DR_TOTAL= "PARTNER_LOAN_LIMIT_DR_TOTAL";
   
   
   /**合作机构小科银行*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_BANK= "PARTNER_LOAN_BANK";
   
   /**合作机构小科银行-配置详情  打款银行（点融）：银行_支行_开户名_帐号_省code_市_code*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_BANK_1= "PARTNER_LOAN_BANK_1";
   /**合作机构小科银行-配置详情  收款银行（华安保险）：银行_开户名_帐号*/
   public static final String SYS_LOOKUP_VAL_PARTNER_LOAN_BANK_2= "PARTNER_LOAN_BANK_2";
   
   /**合作机构小科银行-配置详情  还款银行帐号（南粤）：开户名_帐号*/
   public static final String SYS_LOOKUP_VAL_PARTNER_REPAYMENT_BANK_1= "PARTNER_REPAYMENT_BANK_1";
   /**合作机构小科银行-配置详情  还款银行帐号（华安保险）：银行_支行_开户名_帐号_省code_市_code*/
   public static final String SYS_LOOKUP_VAL_PARTNER_REPAYMENT_BANK_2= "PARTNER_REPAYMENT_BANK_2";
   
   
   /**合作机构小赢城市*/
   public static final String SYS_LOOKUP_PARTNER_XIAOGYIN_CITY= "PARTNER_XIAOGYIN_CITY";
   
   
   /**资金合作机构服务器信息*/
   public static final String SYS_LOOKUP_PARTNER_PC_SERVER= "PARTNER_PC_SERVER";
   /**资金合作机构服务器ip*/
   public static final String SYS_LOOKUP_PARTNER_PC_SERVER_IP= "PARTNER_PC_SERVER_IP";
   /**资金合作机构服务器mac*/
   public static final String SYS_LOOKUP_PARTNER_PC_SERVER_MAC= "PARTNER_PC_SERVER_MAC";
   
   /**系统查档异常通知角色*/
   public static final String SYS_LOOKUP_CHECK_DOC_EXCEPTION_NOTICE_ROLE= "CHECK_DOC_EXCEPTION_NOTICE_ROLE";
   
   
   
   
   
   
   
   
   //===========================系统字典配置 end=====================================
   
   
   
   
   /*
    * 通用常量    1:是    2:否
    */
   /**是*/
   public static final int COMM_YES = 1;
   /**否*/
   public static final int COMM_NO = 2;
   
   
   public static final String SEPARATOR = "/";
   /**
    * 用户来源 1是万通2是小科
    */
   public static final String USER_SOURCE="userSource";
   /**
    * 项目来源：万通=1，小科=2，机构=3
    */
   public static final Integer PROJECT_SOURCE_WT=1;
   /**
    * 项目来源：万通=1，小科=2，机构=3
    */
   public static final Integer PROJECT_SOURCE_XK=2;
   /**
    * 项目来源：万通=1，小科=2，机构=3
    */
   public static final Integer PROJECT_SOURCE_ORG=3;
   /**
    * 是否撤单:是=1，否=0
    */
   public static final Integer PROJECT_IS_CHECHAN=1;
   /**
    * 是否撤单:是=1，否=0
    */
   public static final Integer PROJECT_NOT_CHECHAN=0;
   /**
    * 是否需要办理贷中1、办理2、不办理
    */
   public static final int IS_NEED_HANDLE_1 = 1;
   /**
    * 是否需要办理贷中1、办理2、不办理
    */
   public static final int IS_NEED_HANDLE_2 = 2;
   
   /**
    * 修改记录操作类型1、贷前业务数据变更2、机构合作信息变更
    */
   public static final int CHANGE_OPERATE_TYPE_1 = 1;
   /**
    * 修改记录操作类型1、贷前业务数据变更2、机构合作信息变更
    */
   public static final int CHANGE_OPERATE_TYPE_2 = 2;
   
   /**
    * 修改记录值类型1、借款金额2、接口天数3、费率4、咨询费5、授信额度6、启用授信额度7、可用授信额度8、预收费率9、实际收费费率10、出款标准11、单笔上限12、保证金比例13、保证金金额
    */
   public static final int CHANGE_VALUE_TYPE_1 = 1;
   public static final int CHANGE_VALUE_TYPE_2 = 2;
   public static final int CHANGE_VALUE_TYPE_3 = 3;
   public static final int CHANGE_VALUE_TYPE_4 = 4;
   public static final int CHANGE_VALUE_TYPE_5 = 5;
   public static final int CHANGE_VALUE_TYPE_6 = 6;
   public static final int CHANGE_VALUE_TYPE_7 = 7;
   public static final int CHANGE_VALUE_TYPE_8 = 8;
   public static final int CHANGE_VALUE_TYPE_9 = 9;
   public static final int CHANGE_VALUE_TYPE_10 = 10;
   public static final int CHANGE_VALUE_TYPE_11 = 11;
   public static final int CHANGE_VALUE_TYPE_12 = 12;
   public static final int CHANGE_VALUE_TYPE_13 = 13;

   /**
    * 查档和诉讼方式：1=手动（人工）查询，2=系统查询
    */
   public static final int CHECK_WAY_ARTIFICIAL = 1;
   public static final int CHECK_WAY_SYSTEM = 2;

   /**
    * 赎楼贷后监控状态：无异常=1，有异常=2，异常转正常=3
    */
   public static final int FORE_AFTER_MONITOR_STATUS_1 = 1;
   public static final int FORE_AFTER_MONITOR_STATUS_2 = 2;
   public static final int FORE_AFTER_MONITOR_STATUS_3 = 3;
   /**
    * 通知方式：1=短信，2=邮件
    */
   public static final String FORE_AFTER_NOTICE_WAY_SMS = "1";
   public static final String FORE_AFTER_NOTICE_WAY_MAIL = "2";
   
   
   /**
    * 查档接口地址
    */
   public static final String CHECK_DOC_API_URL = "api.check.doc.url";

   /**
    * 查档方式 1:分户   2：分栋
    */
   public static final int CHECK_DOC_QUERY_TYPE_1 = 1 ;
   public static final int CHECK_DOC_QUERY_TYPE_2 = 2 ;
   /**
    * 产权证书类型    1：房地产权证书 2 ： 不动产权证书
    */
   public static final int CHECK_DOC_CERT_TYPE_1 = 1 ;
   public static final int CHECK_DOC_CERT_TYPE_2 = 2 ;
   
   /**
    * 短信或邮件发送状态：1=成功，2=失败
    */
   public static final int SUCCEED = 1 ;
   public static final int FAILED = 2 ;
   
   /**
    * 提放贷
    */
   public static final String JY_YSL_TFD = "JYYSLTFD" ;
   public static final String FJY_YSL_TFD = "FJYYSLTFD";
   public static final String JY_WSL_TFD = "JYWSLTFD" ;
   public static final String FJY_WSL_TFD = "FJYWSLTFD";
   

  
   /**
    * 客户来源0、ERP后台录入1、AOM端录入
    */
   public static final int CUS_SOURCE_0 = 0;
   /**
    * 客户来源0、ERP后台录入1、AOM端录入
    */
   public static final int CUS_SOURCE_1 = 1;
   
   
   //1:正常未还2：正常已还3：逾期未还4：逾期已还5：提前还款
   /**
    * 还款期数中的状态1:正常未还
    */
   public static final int REPAYMENT_PLAN_STATUS_1 = 1;
   /**
    * 还款期数中的状态2：正常已还
    */
   public static final int REPAYMENT_PLAN_STATUS_2 = 2;
   /**
    * 还款期数中的状态3：逾期未还
    */
   public static final int REPAYMENT_PLAN_STATUS_3 = 3;
   /**
    * 还款期数中的状态4：逾期已还
    */
   public static final int REPAYMENT_PLAN_STATUS_4 = 4;
   /**
    * 还款期数中的状态5：提前还款
    */
   public static final int REPAYMENT_PLAN_STATUS_5 = 5;
   
   /**
    * 抵押贷还款模式 1=正常还款
    */
   public static final int REPAYMENT_TYPE_1 = 1;
   
   /**
    * 抵押贷还款模式 2=提前还款
    */
   public static final int REPAYMENT_TYPE_2 = 2;
   
   /**
    * 房抵贷产品前缀
    */
   public static final String MORTGAGE_LOAN_PRODUCT_PREFIX = "FDD";
   
   /**
    * 房抵贷项目类型-8
    */
   public static final int MORTGAGE_LOAN_PROJECT_TYPE = 8;
   /**
    * 消费贷产品前缀
    */
   public static final String CONSUME_LOAN_PRODUCT_PREFIX = "XFD";
   
   /**
    * 消费贷项目类型-10
    */
   public static final int CONSUMER_LOAN_PROJECT_TYPE = 10;
   
   /**
    * 产品类型-信用贷
    */
   public static final int PRODUCT_TYPE_CREDIT = 1;
   
   /**
    * 产品类型-赎楼贷
    */
   public static final int PRODUCT_TYPE_REDEMPTION = 2;
   
   /**
    * 产品类型-抵押贷
    */
   public static final int PRODUCT_TYPE_MORTGAGE = 3;
   
   
   /**
    * 房抵贷项目状态-1（待提交）
    */
   public static final int MORTGAGE_LOAN_TO_SUBMIT = 1;
   
   /**
    * 房抵贷项目状态-2（待评估）
    */
   public static final int MORTGAGE_LOAN_TO_ESTIMATE = 2;
   
   /**
    * 房抵贷项目状态-3（待下户）
    */
   public static final int MORTGAGE_LOAN_TO_SURVEY = 3;
   
   /**
    * 房抵贷项目状态-6（待复审）
    */
   public static final int MORTGAGE_LOAN_TO_REAUDIT = 6;
   
   /**
    * 房抵贷项目状态-7（待终审）
    */
   public static final int MORTGAGE_LOAN_TO_FINAL_AUDIT = 7;
   
   /**
    * 房抵贷项目状态-8（待放款）
    */
   public static final int MORTGAGE_LOAN_TO_MAKE_LOAN = 8;
   
   /**
    * 房抵贷项目状态-9（待放款复核）
    */
   public static final int MORTGAGE_LOAN_TO_MAKE_LOAN_CHECK = 9;
   
   /**
    * 房抵贷项目状态-10（待资金放款）
    */
   public static final int MORTGAGE_LOAN_TO_MAKE_LOAN_FUNDS = 10;
   
   public static final int MORTGAGE_LOAN_TO_REPAYMENT = 11;
   
   /**
    * 房抵贷项目状态-12（已结清）
    */
   public static final int MORTGAGE_LOAN_TO_OVER = 12;
   
   /**
    * 房抵贷项目状态-13（面签）
    */
   public static final int MORTGAGE_LOAN_TO_INTERVIEW = 13;
   /**
    * 消费贷项目状态-1（待提交）
    */
   public static final int CONSUME_LOAN_TO_SUBMIT = 1;
   /**
    * 消费贷项目状态-2（待初审）
    */
   public static final int CONSUME_LOAN_TO_AUDIT = 2;
   /**
    * 消费贷项目状态-3（待下户）
    */
   public static final int CONSUME_LOAN_TO_SURVEY = 3;
   /**
    * 消费贷项目状态-4（待复审）
    */
   public static final int CONSUME_LOAN_TO_REAUDIT = 4;
   /**
    * 消费贷项目状态-5（复审通过）
    */
   public static final int CONSUME_LOAN_REAUDIT_PASS = 5;
   
   /**
    * 回款项目类型1=实收本金，2=实收利息3=实收逾期金额4=实收罚息5=实收剩余本金6=实收提前还款费
    */
   public static final String REPAYMENT_PRO_1 = "1";
   public static final String REPAYMENT_PRO_2 = "2";
   public static final String REPAYMENT_PRO_3 = "3";
   public static final String REPAYMENT_PRO_4 = "4";
   public static final String REPAYMENT_PRO_5 = "5";
   public static final String REPAYMENT_PRO_6 = "6";
   /**
    * 抵押物状态(1=待登记入库,2=待出库申请,3=待出具注销材料,4=待退证登记,5=已完结)
    */
   public static final int MORTGAGE_STATUS_1 = 1;
   public static final int MORTGAGE_STATUS_2 = 2;
   public static final int MORTGAGE_STATUS_3 = 3;
   public static final int MORTGAGE_STATUS_4 = 4;
   public static final int MORTGAGE_STATUS_5 = 5;
   
}
