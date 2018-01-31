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
 * @ClassName: ProjectSurveyReportMapper
 * @Description: 尽职调查报告Mapper
 * @author: Cai.Qing
 * @date: 2015年3月5日 下午5:00:58
 */
@MapperScan("projectSurveyReportMapper")
public interface ProjectSurveyReportMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 查询尽职调查报告PID
	 * @return 尽职调查报告PID
	 * @author: Cai.Qing
	 * @date: 2015年3月5日 下午5:09:57
	 */
	public int getSeqBizProjectSurveyReport();

	/**
	 * 
	 * @Description: 查询尽职调查报告对象
	 * @param projectId
	 *            项目ID
	 * @return 尽职调查报告对象
	 * @author: Cai.Qing
	 * @date: 2015年3月9日 下午5:50:09
	 */
	public List<ProjectSurveyReport> getSurveyReportByProjectId(int projectId);
}
