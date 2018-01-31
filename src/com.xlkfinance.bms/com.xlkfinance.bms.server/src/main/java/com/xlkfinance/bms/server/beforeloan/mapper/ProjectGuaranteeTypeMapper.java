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

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectSurveyReport;

/**
 * 
 * @ClassName: ProjectGuaranteeTypeMapper
 * @Description: 担保方式Mapper
 * @author: Cai.Qing
 * @date: 2015年3月28日 下午3:18:05
 */
@MapperScan("projectGuaranteeTypeMapper")
public interface ProjectGuaranteeTypeMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 获取担保方式PID
	 * @return PID
	 * @author: Cai.Qing
	 * @date: 2015年3月28日 下午3:20:03
	 */
	public int getSeqProjectGuaranteeType();

	/**
	 * 
	 * @Description: 根据项目ID查询项目的担保方法
	 * @param projectId
	 *            项目DI
	 * @return 担保方式
	 * @author: Cai.Qing
	 * @date: 2015年3月28日 下午3:19:33
	 */
	public List<ProjectSurveyReport> getProjectGuaranteeTypeByProjectId(int projectId);

	/**
	 * 
	 * @Description: 根据项目ID删除当前项目的所有担保方式
	 * @param projectId
	 *            项目ID
	 * @author: Cai.Qing
	 * @date: 2015年3月30日 下午8:29:34
	 */
	public void deleteProjectGuaranteeTypeByProjectId(int projectId);
}
