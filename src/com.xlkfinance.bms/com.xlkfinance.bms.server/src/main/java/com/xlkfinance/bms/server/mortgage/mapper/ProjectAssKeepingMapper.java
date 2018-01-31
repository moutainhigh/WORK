/**
 * @Title: ContractServiceMapper.java
 * @Package com.xlkfinance.bms.server.contract.mapper
 * @Description: 合同参数
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: zhanghg
 * @date: 2015年2月9日 下午19:23:35
 * @version V1.0
 */
package com.xlkfinance.bms.server.mortgage.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssKeeping;

/**
 * 
 * @ClassName: ProjectAssKeepingMapper
 * @Description: 抵质押物保管处理Mapper
 * @author: Cai.Qing
 * @date: 2015年6月24日 下午6:19:11
 */
@MapperScan("projectAssKeepingMapper")
public interface ProjectAssKeepingMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 查询当前抵质押物的最新的保管信息
	 * @param baseId
	 *            抵质押物ID
	 * @return 保管对象
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午4:57:34
	 */
	public ProjectAssKeeping getProjectAssKeepingByBaseId(int baseId);

	/**
	 * 
	 * @Description: 查询当前抵质押物下面的保管信息
	 * @param baseId
	 *            抵质押物ID
	 * @return 保管列表
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午10:44:10
	 */
	public List<ProjectAssKeeping> getListProjectAssKeepingByBaseId(int baseId);

	/**
	 * 
	 * @Description: 查询当前抵质押物下面的保管信息的数量
	 * @param baseId
	 *            抵质押物ID
	 * @return 数量
	 * @author: Cai.Qing
	 * @date: 2015年6月30日 上午10:44:34
	 */
	public int getListProjectAssKeepingCountByBaseId(int baseId);

}
