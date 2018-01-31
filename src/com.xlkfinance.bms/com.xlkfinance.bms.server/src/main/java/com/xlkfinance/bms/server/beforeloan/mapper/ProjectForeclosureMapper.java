/**
 * @Title: ProjectForeclosureMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年1月19日 上午9:40:53
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectForeclosure;

/**
 * 赎楼信息
  * @ClassName: ProjectForeclosureMapper
  * @author: Administrator
  * @date: 2016年1月19日 上午9:41:15
 */
@MapperScan("projectForeclosureMapper")
public interface ProjectForeclosureMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * 查询疏漏信息Pid
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月26日 上午11:11:04
	 */
	public int getSeqBizProjectForeclosure();
	/**
	 * 新增项目赎楼信息
	  * @param ProjectForeclosure
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:28:03
	 */
	public int insertForeclosure(ProjectForeclosure projectForeclosure);
	
	/**
	 * 通过项目编号查询
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:33:20
	 */
	public ProjectForeclosure getForeclosureByProjectId(Integer projectId);
	
	/**
	 * 通过主键修改
	  * @param ProjectForeclosure
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:38:14
	 */
	public int updateByPrimaryKey(ProjectForeclosure projectForeclosure);
	 
	 /**
	 * 通过项目编号查询,用于合作机构业务申请信息查询
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:33:20
	 */
	public ProjectForeclosure getProjectForeclosureInfo(Integer projectId);
	
	
	/**
	 * 修改赎楼信息（资金机构）
	 */
	public int updateForeclosureByPartner(ProjectForeclosure projectForeclosure);
	
	
}
