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

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssDtl;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssFile;

/**
 * 
 * @ClassName: ProjectAssDtlMapper
 * @Description: 抵质押物详细信息
 * @author: Cai.Qing
 * @date: 2015年4月22日 下午11:38:10
 */
@MapperScan("projectAssDtlMapper")
public interface ProjectAssDtlMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 根据抵质押物ID查询抵质押物详情信息集合
	 * @param baseId
	 *            抵质押物ID
	 * @return 抵质押物详情信息集合
	 * @author: Cai.Qing
	 * @date: 2015年4月24日 上午11:15:41
	 */
	public List<ProjectAssDtl> getProjectAssDtlByBaseId(int baseId);

	/**
	 * 
	 * @Description:批量新增抵质押物详情
	 * @param list
	 *            抵质押物详情集合
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月22日 下午11:37:35
	 */
	public int addProjectAssDtls(List<ProjectAssDtl> list);

	/**
	 * 
	 * @Description: 批量修改抵质押物详情
	 * @param list
	 *            抵质押物详情集合
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月22日 下午11:36:51
	 */
	public int updateProjectAssDtls(List<ProjectAssDtl> list);

	/**
	 * 
	 * @Description: 根据抵质押物ID删除详细信息
	 * @param baseId
	 *            抵质押物ID
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月23日 下午5:00:39
	 */
	public int deleteProjectAssDtlByBaseId(int baseId);
	
	
	
	
	/**
	 * 
	  * @Description: 抵质押办理资料上传
	  * @param projectAssFile
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月20日 下午10:59:50
	 */
	public int saveProjectAssFile(ProjectAssFile projectAssFile);
	
	/**
	 * 
	  * @Description: 根据baseId查询文件资料
	  * @param baseId
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月20日 下午11:26:39
	 */
	public List<ProjectAssFile> getProjectAssFile(@Param("baseId") int baseId,@Param("fileType") String fileType);
	
	/**
	 * 
	  * @Description: 修改文件资料信息
	  * @param projectAssFile
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月20日 下午11:27:20
	 */
	public int editProjectAssFile(ProjectAssFile projectAssFile);
	
	/**
	 * 
	  * @Description: 删除抵质押资料
	  * @param pid
	  * @return
	  * @author: xuweihao
	  * @date: 2015年5月21日 上午12:02:59
	 */
	public int delProjectAssFile(int pid);

}
