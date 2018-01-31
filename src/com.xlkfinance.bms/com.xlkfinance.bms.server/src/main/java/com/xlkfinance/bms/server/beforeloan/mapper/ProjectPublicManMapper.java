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

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectPublicMan;

/**
 * 
 * @ClassName: ProjectPublicManMapper
 * @Description: 共同借款人
 * @author: Cai.Qing
 * @date: 2015年3月3日 下午3:32:16
 */
@MapperScan("projectPublicManMapper")
public interface ProjectPublicManMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 根据项目ID查询共同接口人
	 * @param projectId
	 *            项目ID
	 * @return 共同借款人集合
	 * @author: Cai.Qing
	 * @date: 2015年3月3日 下午4:16:36
	 */
	public List<ProjectPublicMan> getAllProjectPublicMan(int projectId);

	/**
	 * 
	 * @Description: 批量删除共同借款人
	 * @param userPids
	 *            共同借款人ID(1,2,3)
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年6月16日 下午3:36:31
	 */
	public int batchDelete(String[] userPids);

	/**
	 * 
	 * @Description: 根据项目ID和共同借款人ID查询是否当前项目存在当前共同借款人
	 * @param personId
	 *            共同借款人ID
	 * @param projectId
	 *            项目ID
	 * @return 是否存在(大于1 存在，反之 不存在)
	 * @author: Cai.Qing
	 * @date: 2015年6月16日 下午3:41:07
	 */
	public int getCountByPersonIdAndProjectId(@Param(value = "personId") int personId, @Param(value = "projectId") int projectId);

	/**
	 * 根据项目ID删除共同借款人
	  * @param projectId
	  * @return
	  * @author: baogang
	  * @date: 2016年5月11日 下午3:07:34
	 */
	public int deleteByProjectId(int projectId);
	
}
