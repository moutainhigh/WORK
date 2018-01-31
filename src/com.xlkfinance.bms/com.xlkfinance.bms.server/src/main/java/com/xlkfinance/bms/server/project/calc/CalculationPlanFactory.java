/**
 * 抵押贷产品贷款利息计算工厂类
 */
package com.xlkfinance.bms.server.project.calc;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
/**
 * 
 * ClassName: CalculationPlanFactory <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年12月11日 下午6:43:05 <br/>
 * 抵押贷产品贷款利息计算工厂类
 * @author baogang
 * @version V1.0
 *
 */
@Component("calculationPlanFactory")
public class CalculationPlanFactory {
	
	/**
	 * 先息后本还款计划生成类
	 */
	@Resource(name = "monthInterestCalculationPlan")
	private CalPlanLine monthInterestCalculationPlan;
	
	/**
	 * 等额本息还款计划生产类
	 */
	@Resource(name = "eqPrincipalInterestCalculationPlanLine")
	private CalPlanLine eqPrincipalInterestCalculationPlanLine;
	/**
	 * 根据还款类型不同使用不同的还款计划生成类进行处理
	 * @param type
	 * @return
	 */
	public CalPlanLine factory(int type){
		/*String lookupVal=sysLookupValMapper.getSysLookupValByPid(type);
		int index=1;
		if(lookupVal!=null && !"".equals(lookupVal)){
			index=Integer.parseInt(lookupVal);
		}*/
		switch (type) {
			case 1:
				return monthInterestCalculationPlan;
			case 2:
				return eqPrincipalInterestCalculationPlanLine;
		}
		return null;
	}
}
