/**
 * @Title: ProjectPartnerRefundMapper.java
 * @Package com.xlkfinance.bms.server.partner.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: chenzhuzhen
 * @date: 2016年7月25日 上午11:22:03
 * @version V1.0
 */
package com.xlkfinance.bms.server.checkapi.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDocDetails;
/**
 * 查记记录详情
 * @author chenzhuzhen
 * @date 2016年11月1日 上午9:23:39
 * @param <T>
 * @param <PK>
 */
@MapperScan("houseCheckDocDetailsMapper")
public interface HouseCheckDocDetailsMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**生成主键*/
	public int getSeqHouseCheckDocDetails();

	/**查询列表*/
	public List<HouseCheckDocDetails> getCheckDocDetailsList(HouseCheckDocDetails houseCheckDocDetails);
	
	/**查询列表总数*/
	public int getHouseCheckDocDetailsCount(HouseCheckDocDetails houseCheckDocDetails);
 
	/**添加房产查档详情*/
	public int addHouseCheckDocDetails(HouseCheckDocDetails houseCheckDocDetails);
	
	/**修改房产查档详情*/
	public int updateHouseCheckDocDetails(HouseCheckDocDetails houseCheckDocDetails);
	
	
	/**查询最后一条查档记录*/
	public HouseCheckDocDetails getLastOneCheckDocDetails(Map<String, String> queryMap);
	
}
