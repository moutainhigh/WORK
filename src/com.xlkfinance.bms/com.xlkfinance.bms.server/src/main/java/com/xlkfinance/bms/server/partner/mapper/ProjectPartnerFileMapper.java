/**
 * @Title: ProjectPartnerFileMapper.java
 * @Package com.xlkfinance.bms.server.partner.mapper
 * Copyright: Copyright (c) 2014 
 * Company:深圳市云房网络技术有限公司
 * 
 * @author: chenzhuzhen
 * @date: 2016年5月12日 上午11:22:03
 * @version V1.0
 */
package com.xlkfinance.bms.server.partner.mapper;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.partner.CusCreditInfo;
import com.xlkfinance.bms.rpc.partner.ProjectPartnerFile;
/**
 * 项目合作机构附件表
 * @author chenzhuzhen
 * @date 2016年5月12日 上午11:22:03
 * @param <T>
 * @param <PK>
 */
@MapperScan("projectPartnerFileMapper")
public interface ProjectPartnerFileMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**生成主键*/
	public int getSeqBizProjectPartnerFile();

	/**查询附件列表*/
	public List<ProjectPartnerFile> findAllProjectPartnerFile(ProjectPartnerFile projectPartnerFile);
	
	/**查询附件列表总数*/
	public int findAllProjectPartnerFileCount(ProjectPartnerFile projectPartnerFile);
	
	/**添加附件*/
	public int addProjectPartnerFile(ProjectPartnerFile projectPartnerFile);
	
	/**更新附件*/
	public int updateProjectPartnerFile(ProjectPartnerFile projectPartnerFile);
	
	/**
	 * 根据id查询
	 *
	 * @author:czz
	 * @time:2017-02-15 18:35:29
	 */
	public ProjectPartnerFile getById(int pid);
	
	/**清空项目机构所有附件上传第三方路径*/
	public int updateAllFileUploadEmpty(ProjectPartnerFile projectPartnerFile);
	
	
}
