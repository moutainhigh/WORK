package com.xlkfinance.bms.common.constant;

import java.util.Map;
import java.util.TreeMap;
/**
 * 房抵贷产品办理动态常量类
 * @author baogang
 *
 */
public class MortgageDynamicConstant {
	/**
	    * 状态：未完成=1，进行中=2，已完成=3，失效=4
	    */
	   public static final Integer STATUS_1 = 1;
	   public static final Integer STATUS_2 = 2;
	   public static final Integer STATUS_3 = 3;
	   public static final Integer STATUS_4 = 4;

	   /**
	    * 模块编号 贷前管理
	    */
	   public static final String MODUEL_NUMBER_BEFORELOAN = "a"; // 贷前管理
	   /**
	    * 模块编号 财务管理
	    */
	   public static final String MODUEL_NUMBER_FINANCE = "b"; // 财务管理
	   /**
	    * 模块编号 贷前审批驳回模块
	    */
	   public static final String MODUEL_NUMBER_BEFORELOAN_OTHER = "c";//贷前审批驳回模块
	   /**
	    * 抵押管理
	    */
	   public static final String MODUEL_NUMBER_MORTGAGE = "d";
	   
	   /**
	    * 其他模块
	    */
	   public static final String MODUEL_NUMBER_OTHER = "e";//其他模块
	   
	   /**
	    * 面签模块
	    */
	   public static final String MODUEL_NUMBER_INTERVIEW = "f";
	   
	   /**
	    * 动态编号(抵押贷申请)
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_1 = "a";// 抵押贷申请
	   /**
	    * 动态编号(评估)
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_2 = "b";// 评估
	   /**
	    * 动态编号(下户)
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_3 = "c";// 下户
	   /**
	    * 动态编号(总部复审)
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_4 = "d";// 总部复审
	   /**
	    * 动态编号(总部终审)
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_5 = "e";// 总部终审
	   /**
	    * 动态编号(贷前)
	    */
	   public static final String DYNAMIC_NUMBER_CONSUME_BEFORELOAN_1 = "a";// 消费贷申请
	   /**
	    * 动态编号(贷前)
	    */
	   public static final String DYNAMIC_NUMBER_CONSUME_BEFORELOAN_2 = "b";// 消费贷初审
	   /**
	    * 动态编号(贷前)
	    */
	   public static final String DYNAMIC_NUMBER_CONSUME_BEFORELOAN_3 = "c";// 消费贷下户
	   /**
	    * 动态编号(贷前)
	    */
	   public static final String DYNAMIC_NUMBER_CONSUME_BEFORELOAN_4 = "d";// 消费贷复审

	   /**
	    * 贷前模块对应动态
	    */
	   public static final Map<String, String> DYNAMIC_NUMBER_BEFORELOAN_MAP = new TreeMap<String, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put(new String("a"), "抵押贷申请");
	         put(new String("b"), "评估");
	         put(new String("c"), "下户");
	         put(new String("d"), "风控复审");
	         put(new String("e"), "风控终审");
	      }
	   };

	   /**
	    * 动态编号(财务收费)
	    */
	   public static final String DYNAMIC_NUMBER_FINANCE_1 = "a";// 财务收费
	   /**
	    * 动态编号(财务放款申请 )
	    */                                                   
	   public static final String DYNAMIC_NUMBER_FINANCE_2 = "b";// 财务放款申请 
	   /**
	    * 动态编号(运营经理审核 )
	    */
	   public static final String DYNAMIC_NUMBER_FINANCE_3= "c";//运营经理审核
	   /**
	    * 动态编号(资金主管审批 )
	    */
	   public static final String DYNAMIC_NUMBER_FINANCE_4 = "d";//资金主管审批 
	   /**
	    * 动态编号(财务总监审批 )
	    */
	   public static final String DYNAMIC_NUMBER_FINANCE_5 = "e";//财务总监审批 
	   /**
	    * 动态编号(已放款)
	    */                                                   
	   public static final String DYNAMIC_NUMBER_FINANCE_6 = "f";// 已放款
	   /**
	    * 动态编号(回款)
	    */  
	   public static final String DYNAMIC_NUMBER_FINANCE_7 = "g";// 回款
	   /**
	    * 财务管理模块对应动态
	    */
	   public static final Map<String, String> DYNAMIC_NUMBER_FINANCE_MAP = new TreeMap<String, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put(new String("a"), "财务收费");
	         put(new String("b"), "财务放款申请");
	         put(new String("c"), "运营经理审核");
	         put(new String("d"), "资金主管审核");
	         put(new String("e"), "财务总监审核");
	         put(new String("f"), "已放款");
	         put(new String("g"), "回款");
	      }
	   };
	   
	   
	   /**
	    * 评估驳回
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_10 = "a";//评估驳回
	   /**
	    * 下户驳回
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_20 = "b";//下户驳回
	   /**
	    * 复审驳回
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_30 = "c";//复审驳回
	   /**
	    * 终审驳回
	    */
	   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_40 = "d";//终审驳回
	   /**
	    * 贷前审批驳回动态
	    */
	   public static final Map<String, String> DYNAMIC_NUMBER_BEFORELOAN_OTHER_MAP = new TreeMap<String, String>() {
		      private static final long serialVersionUID = 1L;
		      {
		         put(new String("a"), "评估驳回");
		         put(new String("b"), "下户驳回");
		         put(new String("c"), "风控复审驳回");
		         put(new String("d"), "风控终审驳回");
		      }
		   };

	   /**
	    * 入库登记
	    */
	   public static final String MODUEL_NUMBER_MORTGAGE_1 ="a";
	   /**
	    * 出库申请
	    */
	   public static final String MODUEL_NUMBER_MORTGAGE_2 ="b";
	   /**
	    * 出具注销
	    */
	   public static final String MODUEL_NUMBER_MORTGAGE_3 ="c";
	   /**
	    * 退证登记
	    */
	   public static final String MODUEL_NUMBER_MORTGAGE_4 ="d";
	   /**
	    * 抵押管理动态
	    */
	   public static final Map<String, String> MODUEL_NUMBER_MORTGAGE_MAP = new TreeMap<String, String>() {
		      private static final long serialVersionUID = 1L;
		      {
		         put(new String("a"), "入库登记");
		         put(new String("b"), "出库申请");
		         put(new String("c"), "出具注销");
		         put(new String("d"), "退证登记");
		      }
		   };
	  /**
	   * 面签
	   */
	  public static final String MODUEL_NUMBER_INTERVIEW_1 = "a";
	  /**
	   * 公证
	   */
	  public static final String MODUEL_NUMBER_INTERVIEW_2 = "b";
	  /**
	   * 抵押
	   */
	  public static final String MODUEL_NUMBER_INTERVIEW_3 = "c";
	  /**
	   * 领他证
	   */
	  public static final String MODUEL_NUMBER_INTERVIEW_4 = "d";
	  /**
	   * 面签管理动态
	   */
	  public static final Map<String, String> MODUEL_NUMBER_INTERVIEW_MAP = new TreeMap<String, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put(new String("a"), "面签");
	         put(new String("b"), "公证");
	         put(new String("c"), "抵押");
	         put(new String("d"), "领他证");
	      }
	   };
	   
	   /**
	    * 业务审批拒单
	    */
	   public static final String DYNAMIC_NUMBER_OTHER_10 = "a";//业务审批拒单
	   
	   public static final Map<String, String> DYNAMIC_NUMBER_OTHER_MAP = new TreeMap<String, String>() {
		      private static final long serialVersionUID = 1L;
		      {
		         put(new String("a"), "业务审批拒单");

		      }
		   };
		   /**
	       * 
	       */
	      public static final Map<String, Map<String, String>> MODUEL_NUMBER_MAP = new TreeMap<String, Map<String, String>>() {
	         private static final long serialVersionUID = 1L;
	         {
	            put(MODUEL_NUMBER_BEFORELOAN, DYNAMIC_NUMBER_BEFORELOAN_MAP);
	            put(MODUEL_NUMBER_FINANCE, DYNAMIC_NUMBER_FINANCE_MAP);
	            put(MODUEL_NUMBER_BEFORELOAN_OTHER, DYNAMIC_NUMBER_BEFORELOAN_OTHER_MAP);
	            put(MODUEL_NUMBER_MORTGAGE, MODUEL_NUMBER_MORTGAGE_MAP);
	            put(MODUEL_NUMBER_INTERVIEW, MODUEL_NUMBER_INTERVIEW_MAP);
	         }
	      };

}
