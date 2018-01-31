/**
 * @Title: ProjectGuaranteeMapper.java
 * @Package com.xlkfinance.bms.server.beforeloan.mapper
 * @Description: TODO
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: Administrator
 * @date: 2016年1月19日 上午9:28:25
 * @version V1.0
 */
package com.xlkfinance.bms.server.beforeloan.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectGuarantee;

/**
 * 
  * @ClassName: ProjectGuaranteeMapper
  * @Description: TODO
  * @author: Administrator
  * @date: 2016年1月19日 上午9:39:09
 */
@MapperScan("projectGuaranteeMapper")
public interface ProjectGuaranteeMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 查询担保信息PId
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月26日 上午11:09:17
	 */
	public int getSeqBizProjectGuarantee();
	
	/**
	 * 新增项目担保信息
	  * @param projectGuarantee
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:28:03
	 */
	public int insertGuarantee(ProjectGuarantee projectGuarantee);
	
	/**
	 * 通过项目编号查询
	  * @param projectId
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:33:20
	 */
	public ProjectGuarantee getGuaranteeByProjectId(Integer projectId);
	
	/**
	 * 通过主键修改
	  * @param projectGuarantee
	  * @return
	  * @author: Administrator
	  * @date: 2016年1月19日 下午7:38:14
	 */
	public int updateByPrimaryKey(ProjectGuarantee projectGuarantee);
}
