/**
 * @Title: ProjectForeInformationMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年3月31日 下午3:47:06
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeInformation;
/**
 * 项目赎楼清单MAPPER
  * @ClassName: ProjectForeInformationMapper
  * @author: Administrator
  * @date: 2016年3月31日 下午3:47:24
 */
@MapperScan("projectForeInformationMapper")
public interface ProjectForeInformationMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 根据项目ID 查询项目赎楼清单
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月31日 下午3:50:55
	 */
	public List<ProjectForeInformation> queryForeInformations(Integer projectId);

	/**
	 * 新增赎楼清单
	  * @param projectForeInfomation
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月31日 下午3:51:06
	 */
	public int insertProjectForeInformation(ProjectForeInformation projectForeInformation);

	/**
	 * 修改赎楼清单
	  * @param projectForeInfomation
	  * @return
	  * @author: Administrator
	  * @date: 2016年3月31日 下午3:51:10
	 */
	public int updateProjectForeInformation(ProjectForeInformation projectForeInformation);


}
