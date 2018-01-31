package com.xlkfinance.bms.server.finance.util;
/**
 * @Title: FinanceTypeEnum.java
 * @Package com.xlkfinance.bms.web.util
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: qiancong.Xian
 * @date: 2015年3月15日 下午4:19:05
 * @version V1.0
 */

/***
 * 必须保持Service的FinanceTypeEnum 一样，是为了方便controller使用
  * @ClassName: FinanceTypeEnum
  * @Description: 部署使用分布式，不方便一样service的枚举，在controller复制一份一样的,定义这里不要使用负数，原来基础上-1000为逾期利息 -2000 未逾期罚息
  * 如：50 为："利息" -950 未利息逾期利息    -1950为 利息逾期罚息
  * @author: qiancong.Xian
  * @date: 2015年3月19日 上午9:52:09
 */
public enum FinanceTypeEnum {

	/**
	 * 挪用罚息
	 */
	TYPE_1(1,"挪用罚息"),
	/**
	 * 违约罚金
	 */
	TYPE_2(2,"违约罚金"),
	/**
	 * 坏账
	 */
	TYPE_3(3,"坏账后应收本金"),
	/**
	 * 退管理费
	 */
	TYPE_4(4,"退管理费"),
	/**
	 * 退其他费用
	 */
	TYPE_5(5,"退其他费用"),
	/**
	 * 退利息
	 */
	TYPE_6(6,"退利息"),
	/**
	 * 提前还款
	 */
	TYPE_7(7,"提前还款"),
	/**
	 * 提前还款罚金
	 */
	TYPE_8(8,"提前还款罚金"),

	/**
	 * 手续费
	 */
	TYPE_10(10,"手续费"),
	/**
	 * 坏账损失金额
	 */
	TYPE_11(11,"损失金额"),
	/**
	 * 退管理费
	 */
	TYPE_12(12,"坏账后应收管理费"),
	/**
	 * 退其他费用
	 */
	TYPE_13(13,"坏账后应收其他费用"),
	/**
	 * 退利息
	 */
	TYPE_14(14,"坏账后应收利息"),
	/**
	 * 本金
	 */
	TYPE_30(30,"本金"),
	/**
	 * 管理费
	 */
	TYPE_40(40,"管理费"),
	/**
	 * 利息
	 */
	TYPE_50(50,"利息"),
	/**
	 * 其他费用
	 */
	TYPE_60(60,"其他费用"),
	/**
	 * 费用减免(共用)
	 */
	TYPE_100(100,"费用减免");
	
	private int type;
	private String name;
	private FinanceTypeEnum(int type,String name)
	{
		this.type = type;
		this.name = name;
	}
	
	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取当前类型的逾期利息type
	  * @param type
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年3月27日 下午2:25:42
	 */
	public static int getOverdueinterestType(int type)
	{
		return type -1000;
	}
	
	/**
	 * 获取当前类型的逾期罚息type
	  * @param type
	  * @return
	  * @author: qiancong.Xian
	  * @date: 2015年3月27日 下午2:25:42
	 */
	public static int getOverduePenaltyType(int type)
	{
		return type -2000;
	}

	public static String getName(int type)
	{
		String suffix ="";
		// 逾期利息
		if(type<0 && type>-1000)
		{
			type = type +1000;
			suffix="逾期利息";
		}
		// 逾期罚息
		else if(type<-1000 && type>-2000)
		{
			type = type +2000;
			suffix="逾期罚息";
		}
			
		for(FinanceTypeEnum financeTypeEnum :FinanceTypeEnum.values())
		{
			if(financeTypeEnum.getType() == type)
			{
				return financeTypeEnum.getName() + suffix;
			}
		}
		
		return "";
	}
}
