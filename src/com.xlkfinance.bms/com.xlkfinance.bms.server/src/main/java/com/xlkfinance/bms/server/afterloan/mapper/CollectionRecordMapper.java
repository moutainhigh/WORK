/**
 * @Title: MisapprProcessMapper.java
 * @Package com.xlkfinance.bms.server.afterloan.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年1月31日 下午3:28:23
 * @version V1.0
 */
package com.xlkfinance.bms.server.afterloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.afterloan.CollectionRecord;

@MapperScan("collectionRecordMapper")
public interface CollectionRecordMapper<T, PK> extends BaseMapper<T, PK>  {
	List<CollectionRecord> getCollectionRecord(CollectionRecord collectionRecord);
	
	
	/**
	 * 
	  * @Description: 导出-根据项目ids 查询催收记录
	  * @param pids
	  * @return
	  * @author: zhanghg
	  * @date: 2015年5月5日 下午5:09:24
	 */
	List<CollectionRecord> getCollectionRecordsByProjectIds(List<String> pids);

}
