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

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.checkapi.HouseCheckDoc;
/**
 * 查记记录
 * @author chenzhuzhen
 * @date 2016年11月1日 上午9:23:39
 * @param <T>
 * @param <PK>
 */
@MapperScan("houseCheckDocMapper")
public interface HouseCheckDocMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**生成主键*/
	public int getSeqHouseCheckDoc();

	/**根据主键查询*/
	public HouseCheckDoc getHouseCheckDocById(int pid);

	/**查询列表*/
	public List<HouseCheckDoc> getHouseCheckDocs(HouseCheckDoc houseCheckDoc);
	
	/**查询列表总数*/
	public int getHouseCheckDocCount(HouseCheckDoc houseCheckDoc);
 
	/**添加房产查档*/
	public int addHouseCheckDoc(HouseCheckDoc houseCheckDoc);
	
	/**修改房产查档*/
	public int updateHouseCheckDoc(HouseCheckDoc houseCheckDoc);
	
	
}