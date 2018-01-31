/**
 * @Title: SysUserMapper.java
 * @Package com.xlkfinance.bms.server.system.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市信利康供应链管理有限公司
 * 
 * @author: Simon.Hoo
 * @date: 2014年12月11日 下午7:22:11
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectAssure;

/**
 * 
 * @ClassName: ProjectAssureMapper
 * @Description: 保证信息Mapper
 * @author: Cai.Qing
 * @date: 2015年4月7日 下午3:49:09
 */
@MapperScan("projectAssureMapper")
public interface ProjectAssureMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 根据项目ID查询保证信息
	 * @param projectId
	 *            项目ID
	 * @return 保证信息集合
	 * @author: Cai.Qing
	 * @date: 2015年4月7日 下午3:48:42
	 */
	public List<ProjectAssure> getAssureByProjectId(int projectId);

	/**
	 * 
	 * @Description: 根据项目ID查询个人保证信息
	 * @param projectId
	 *            项目ID
	 * @return 保证信息集合
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:08:13
	 */
	public List<ProjectAssure> getProjectAssureByPersonal(Map<String, Object> map);

	/**
	 * 
	 * @Description: 根据项目ID查询企业法人保证信息
	 * @param projectId
	 *            项目ID
	 * @return 保证信息集合
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:08:37
	 */
	public List<ProjectAssure> getProjectAssureByEnterprise(Map<String, Object> map);

	/**
	 * 
	 * @Description: 批量删除
	 * @param refIds
	 *            保证信息PID字符串
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月14日 上午9:44:48
	 */
	public int deleteProjectAssure(String[] refIds);

	/**
	 * 
	 * @Description:根据项目ID删除保证信息
	 * @param projectId
	 *            项目ID
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年6月5日 下午4:06:12
	 */
	public int deleteProjectAssureByProjectId(int projectId);

}
