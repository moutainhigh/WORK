package com.xlkfinance.bms.common.constant;

import java.util.Map;
import java.util.TreeMap;

/**
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆ @author： liangyanjun <br>
 * ★☆ @time：2016年5月11日下午5:39:05 <br>
 * ★☆ @version： <br>
 * ★☆ @lastMotifyTime： <br>
 * ★☆ @ClassAnnotation： 业务动态（项目总览）常量<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 * ★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★☆★<br>
 */
public class BizDynamicConstant {
   /**
    * 状态：未完成=1，进行中=2，已完成=3，失效=4
    */
   public static final Integer STATUS_1 = 1;
   public static final Integer STATUS_2 = 2;
   public static final Integer STATUS_3 = 3;
   public static final Integer STATUS_4 = 4;

   /**
    * 模块编号
    */
   public static final String MODUEL_NUMBER_BEFORELOAN = "a"; // 贷前管理
   public static final String MODUEL_NUMBER_INLOAN = "b"; // 贷中管理
   public static final String MODUEL_NUMBER_BEFORELOAN_OTHER = "c";//贷前审批驳回模块
   public static final String MODUEL_NUMBER_OTHER = "d";//其他模块
   /**
    * 动态编号(贷前)
    */
   public static final String DYNAMIC_NUMBER_BEFORELOAN_1 = "a";// 贷款申请
   public static final String DYNAMIC_NUMBER_BEFORELOAN_2 = "b";// 风控初审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_2_1 = "b_a";// 风控初审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_3 = "c";// 风控复审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_4 = "d";// 风控终审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_5 = "e";// 部门经理审批
   public static final String DYNAMIC_NUMBER_BEFORELOAN_6 = "f";// 业务总监审批
   public static final String DYNAMIC_NUMBER_BEFORELOAN_7 = "g";// 审查员审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_8 = "h";// 审查主管审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_9 = "i";// 风控总监审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_95 = "i_a";// 财务总监审
   public static final String DYNAMIC_NUMBER_BEFORELOAN_10 = "j";// 总经理审
   
   public static final Map<String, String> DYNAMIC_NUMBER_BEFORELOAN_MAP = new TreeMap<String, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new String("a"), "贷款申请");
         put(new String("b"), "风控初审");
         put(new String("b_a"), "合规复审");
         put(new String("c"), "风控复审");
         put(new String("d"), "风控终审");
         put(new String("e"), "部门经理审批");
         put(new String("f"), "业务总监审批");
         put(new String("g"), "审查员审");
         put(new String("h"), "审查主管审");
         put(new String("i"), "风控总监审");
         put(new String("i_a"), "财务总监审");
         put(new String("j"), "总经理审");
      }
   };

   /**
    * 动态编号(贷中)
    */
   public static final String DYNAMIC_NUMBER_INLOAN_1 = "a";// 财务收费
                                                        
   public static final String DYNAMIC_NUMBER_INLOAN_2 = "b";// 收件
   public static final String DYNAMIC_NUMBER_INLOAN_3 = "c"; // 执行岗备注
   public static final String DYNAMIC_NUMBER_INLOAN_4 = "d";// 查诉讼
   public static final String DYNAMIC_NUMBER_INLOAN_5 = "e";// 查档
   public static final String DYNAMIC_NUMBER_INLOAN_6 = "f";// 查档复核
                                                        
   public static final String DYNAMIC_NUMBER_INLOAN_7 = "g";// 财务放款申请
   public static final String DYNAMIC_NUMBER_INLOAN_7_1= "g_a";// 资金主管审批
   public static final String DYNAMIC_NUMBER_INLOAN_7_2 = "g_b";// 财务总监审批
   public static final String DYNAMIC_NUMBER_INLOAN_7_3 = "g_c";// 已放款
   public static final String DYNAMIC_NUMBER_INLOAN_8 = "h";// 赎楼
  public static final String DYNAMIC_NUMBER_INLOAN_9 = "i";// 赎楼余额确认
   public static final String DYNAMIC_NUMBER_INLOAN_10 = "j";// 取旧证
   public static final String DYNAMIC_NUMBER_INLOAN_11 = "k";// 注销抵押
   public static final String DYNAMIC_NUMBER_INLOAN_12 = "l";// 过户
   public static final String DYNAMIC_NUMBER_INLOAN_13 = "m";// 取新证
   public static final String DYNAMIC_NUMBER_INLOAN_14 = "n";// 抵押
                                                         
   public static final String DYNAMIC_NUMBER_INLOAN_15 = "o";// 回款
   public static final String DYNAMIC_NUMBER_INLOAN_16 = "p";// 逾期费确认
                                                         
   public static final String DYNAMIC_NUMBER_INLOAN_17 = "q";// 退手续费
   public static final String DYNAMIC_NUMBER_INLOAN_18 = "r";// 退咨询费
   public static final String DYNAMIC_NUMBER_INLOAN_19 = "s";// 退尾款
   public static final String DYNAMIC_NUMBER_INLOAN_20 = "t";// 退佣金
   public static final String DYNAMIC_NUMBER_INLOAN_21 = "u";// 已解保

   public static final Map<String, String> DYNAMIC_NUMBER_INLOAN_MAP = new TreeMap<String, String>() {
      private static final long serialVersionUID = 1L;
      {
         put(new String("a"), "财务收费");
         put(new String("b"), "收件");
         put(new String("c"), "执行岗备注");
         put(new String("d"), "查诉讼");
         put(new String("e"), "查档");
         put(new String("f"), "查档复核");
         put(new String("g"), "财务放款申请");
         put(new String("g_a"), "资金主管审批");
         put(new String("g_b"), "财务总监审批");
         put(new String("g_c"), "已放款");
         put(new String("h"), "赎楼");
         put(new String("i"), "赎楼余额确认");
         put(new String("j"), "取旧证");
         put(new String("k"), "注销抵押");
         put(new String("l"), "过户");
         put(new String("m"), "取新证");
         put(new String("n"), "抵押");
         put(new String("o"), "回款");
         put(new String("p"), "逾期费确认");
         put(new String("q"), "退手续费");
         put(new String("r"), "退咨询费");
         put(new String("s"), "退尾款");
         put(new String("t"), "退佣金");
         put(new String("u"), "已(归档)解保");
      }
   };

   /**
    * 动态编号（其他类型）
    */
   /**
    * 风控初审驳回
    */
   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_10 = "a";//风控初审驳回
   /**
    * 风控复审驳回
    */
   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_20 = "b";//风控复审驳回
   /**
    * 风控终审驳回
    */
   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_30 = "c";//风控终审驳回
   /**
    * 总经理审驳回
    */
   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_40 = "d";//总经理审驳回
   
   /**
    * 合规复审驳回
    */
   public static final String DYNAMIC_NUMBER_BEFORELOAN_OTHER_50 = "a_b";//合规复审驳回
   
   public static final Map<String, String> DYNAMIC_NUMBER_BEFORELOAN_OTHER_MAP = new TreeMap<String, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put(new String("a"), "风控初审驳回");
	         put(new String("a_b"), "合规复审驳回");
	         put(new String("b"), "风控复审驳回");
	         put(new String("c"), "风控终审驳回");
	         put(new String("d"), "总经理审批驳回");

	      }
	   };
   
   /**
    * 撤单
    */
   public static final String DYNAMIC_NUMBER_OTHER_10 = "a";//撤单
   /**
    * 机构拒单
    */
   public static final String DYNAMIC_NUMBER_OTHER_20 = "b";//机构拒单
   /**
    * 机构驳回
    */
   public static final String DYNAMIC_NUMBER_OTHER_30 = "c";//机构驳回
   /**
    * 业务审批拒单
    */
   public static final String DYNAMIC_NUMBER_OTHER_40 = "d";//业务审批拒单
   
   
   public static final Map<String, String> DYNAMIC_NUMBER_OTHER_MAP = new TreeMap<String, String>() {
	      private static final long serialVersionUID = 1L;
	      {
	         put(new String("a"), "撤单");
	         put(new String("b"), "机构业务拒单");
	         put(new String("c"), "机构业务驳回");
	         put(new String("d"), "业务审批拒单");

	      }
	   };
   
   public static final Map<String, Map<String, String>> MODUEL_NUMBER_INLOAN_MAP = new TreeMap<String, Map<String, String>>() {
      private static final long serialVersionUID = 1L;
      {
         put(MODUEL_NUMBER_BEFORELOAN, DYNAMIC_NUMBER_BEFORELOAN_MAP);
         put(MODUEL_NUMBER_INLOAN, DYNAMIC_NUMBER_INLOAN_MAP);
         put(MODUEL_NUMBER_BEFORELOAN_OTHER, DYNAMIC_NUMBER_BEFORELOAN_OTHER_MAP);
         put(MODUEL_NUMBER_OTHER, DYNAMIC_NUMBER_OTHER_MAP);
      }
   };
}
