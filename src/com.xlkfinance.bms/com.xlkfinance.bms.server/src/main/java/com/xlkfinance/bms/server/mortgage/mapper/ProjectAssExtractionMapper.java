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
import com.xlkfinance.bms.rpc.mortgage.ProjectAssExtraction;

/**
 * 
 * @ClassName: ProjectAssExtractionMapper
 * @Description: 抵质押物提取Mapper
 * @author: Cai.Qing
 * @date: 2015年6月24日 下午6:19:11
 */
@MapperScan("projectAssExtractionMapper")
public interface ProjectAssExtractionMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 查询所有有效的提取信息
	 * @param projectAssExtraction
	 *            提取信息对象
	 * @return 提取列表
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:31:01
	 */
	public List<ProjectAssExtraction> getAllProjectAssExtraction(ProjectAssExtraction projectAssExtraction);

	/**
	 * 
	 * @Description: 查询所有有效的提取信息数量
	 * @param projectAssExtraction
	 *            提取信息对象
	 * @return 数量
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:31:29
	 */
	public int getAllProjectAssExtractionCount(ProjectAssExtraction projectAssExtraction);

	/**
	 * 
	 * @Description:查询当前抵质押物的提取信息
	 * @param baseId
	 *            抵质押物ID
	 * @return 提取信息列表
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:33:20
	 */
	public List<ProjectAssExtraction> getProjectAssExtractionByBaseId(Integer baseId);

	/**
	 * 
	 * @Description: 查询当前抵质押物的提取信息总数
	 * @param baseId
	 *            抵质押物ID
	 * @return 数量
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午7:34:23
	 */
	public int getProjectAssExtractionCountByBaseId(Integer baseId);

	/**
	 * 
	 * @Description: 修改抵质押物的提取信息>>>提取处理操作
	 * @param projectAssExtraction
	 *            提取信息对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年6月29日 下午9:31:09
	 */
	public int updateProjectAssExtraction(ProjectAssExtraction projectAssExtraction);
}
