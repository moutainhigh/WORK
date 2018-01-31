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
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssOwn;

/**
 * 
 * @ClassName: ProjectAssOwnMapper
 * @Description: 抵质押物共有人Mapper
 * @author: Cai.Qing
 * @date: 2015年6月1日 下午5:31:03
 */
@MapperScan("projectAssOwnMapper")
public interface ProjectAssOwnMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 查询抵质押物共有人-根据抵质押物ID
	 * @param baseId
	 *            抵质押物ID
	 * @return 共有人列表集合
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午5:30:58
	 */
	public List<ProjectAssOwn> getProjectAssOwnByBaseId(@Param(value = "baseId") int baseId, @Param(value = "ownType") int ownType);

	/**
	 * 
	 * @Description: 查询抵质押物共有人-根据类型人ID和所有人类型查询
	 * @param map
	 *            条件Map
	 * @return 共有人列表集合
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午5:34:45
	 */
	public List<ProjectAssOwn> getProjectAssOwnByRelationId(Map<String, Object> map);

	/**
	 * 
	 * @Description: 查询所有-抵质押物共有人列表
	 * @param projectAssOwn
	 *            条件对象
	 * @return 共有人列表集合
	 * @author: Cai.Qing
	 * @date: 2015年6月3日 上午12:45:50
	 */
	public List<ProjectAssOwn> getAllProjectAssOwnByOwnType(ProjectAssOwn projectAssOwn);

	/**
	 * 
	 * @Description: 查询所有-抵质押物共有人数量
	 * @param projectAssOwn
	 *            条件对象
	 * @return 共有人数量
	 * @author: Cai.Qing
	 * @date: 2015年6月3日 上午12:58:10
	 */
	public int getAllProjectAssOwnByOwnTypeCount(ProjectAssOwn projectAssOwn);

	/**
	 * 
	 * @Description: 新增抵质押物共有人
	 * @param projectAssOwn
	 *            抵质押物共有人对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午5:32:16
	 */
	public int addProjectAssOwn(ProjectAssOwn projectAssOwn);

	/**
	 * 
	 * @Description: 查询当前共有人在当前抵质押物是否存在
	 * @param baseId
	 *            抵质押物ID
	 * @param publicOwnUserId
	 *            共有人ID
	 * @return > 0 存在, = 0 不存在
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午10:03:54
	 */
	public int checkBaseIdAndOwnUser(@Param(value = "baseId") int baseId, @Param(value = "publicOwnUserId") int publicOwnUserId);

	/**
	 * 
	 * @Description: 删除抵质押物共有人-根据抵质押物ID
	 * @param baseId
	 *            抵质押物ID
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午5:35:20
	 */
	public int deleteProjectAssOwn(int baseId);

	/**
	 * 
	 * @Description: 批量删除抵质押物共有人-根据主键
	 * @param pids
	 *            主键数组(1,2,3)
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年6月1日 下午5:35:46
	 */
	public int batchDeleteProjectAssOwn(String[] pids);

}
