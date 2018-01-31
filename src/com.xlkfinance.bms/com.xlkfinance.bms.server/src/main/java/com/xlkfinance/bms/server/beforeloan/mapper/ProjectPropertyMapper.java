/**
 * @Title: ProjectPropertyMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年1月19日 上午9:38:39
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectProperty;

/**
 * 物业双方信息
  * @ClassName: ProjectPropertyMapper
  * @Description: TODO
  * @author: Administrator
  * @date: 2016年1月19日 上午9:38:49
 */
@MapperScan("projectPropertyMapper")
public interface ProjectPropertyMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * 查询物业刷分信息PId
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月26日 上午11:09:17
	 */
	public int getSeqBizProjectProperty();
	
	/**
	 * 新增项目担保信息
	  * @param ProjectProperty
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:28:03
	 */
	public int insertProperty(ProjectProperty projectProperty);
	
	/**
	 * 通过项目编号查询
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:33:20
	 */
	public ProjectProperty getPropertyByProjectId(Integer projectId);
	
	/**
	 * 通过主键修改
	  * @param ProjectProperty
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:38:14
	 */
	public int updateByPrimaryKey(ProjectProperty projectProperty);
}
