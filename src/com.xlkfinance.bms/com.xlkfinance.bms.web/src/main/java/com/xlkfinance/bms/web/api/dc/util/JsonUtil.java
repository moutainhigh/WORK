package com.xlkfinance.bms.web.api.dc.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * json 工具类
 * @author chenzhuzhen
 * @date 2017年6月15日 下午3:10:36
 */
public class JsonUtil {

	/**
	 * 将对象转成集合
	 * @param indexObj
	 * @return
	 */
	public static JSONArray changeToList(Object indexObj) {

		JSONArray array = new JSONArray();
		if(indexObj == null){
			return array;
		}
		if (indexObj instanceof JSONObject) {
			array.add(indexObj);
		}else if(indexObj instanceof JSONArray){ 
			array = JSONArray.parseArray(indexObj.toString());
		}
		return array;
	}
	
	
}
