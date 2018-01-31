/**
 * @Title: CusPerCom.java
 * @Package com.xlkfinance.bms.server.customer.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Heng.Wang
 * @date: 2015年3月9日 上午9:18:09
 * @version V1.0
 */
package com.xlkfinance.bms.server.customer.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.customer.CusPerCom;
@MapperScan("cusPerComMapper")
public interface CusPerComMapper<T, PK> extends BaseMapper<T, PK> {
	public int addUnderCom(List<CusPerCom> list);

}
