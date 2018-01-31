package com.xlkfinance.bms.web.api.qfangapi.util;

import com.qfang.data.facade.model.enums.CityDtoEnum;
import com.qfang.data.facade.model.enums.DirectionEnum;

/**
 * Q房集团接口工具类
 * @author chenzhuzhen
 * @date 2016年10月13日 下午4:48:57
 */
public class QfangUtil {
	
	/**
	 * 获取对应城市枚举对象
	 * @param qfingCity
	 * @return
	 */
	public static CityDtoEnum getQfangCityDtoEnum(String cityCode){
		CityDtoEnum cityDtoEnum = null;
		for (CityDtoEnum indexObj : CityDtoEnum.values()) {
			if(cityCode.equals(indexObj.getValue())){
				cityDtoEnum =  indexObj;
				break;
			}
		}
		return cityDtoEnum;
	}
	
	
	/**
	 * 获取房子朝向枚举对象
	 * @param qfingCity
	 * @return
	 */
	public static DirectionEnum getQfangDirectionEnum(String direction){
		DirectionEnum directionEnum = null;
		for (DirectionEnum indexObj : DirectionEnum.values()) {
			if(direction.equals(indexObj.name())){
				directionEnum =  indexObj;
				break;
			}
		}
		return directionEnum;
	}
	
	

}
