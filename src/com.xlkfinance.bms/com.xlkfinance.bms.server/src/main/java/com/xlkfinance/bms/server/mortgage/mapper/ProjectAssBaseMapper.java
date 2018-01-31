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
package com.xlkfinance.bms.server.mortgage.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.mortgage.ProjectAssBase;

/**
 * 
 * @ClassName: ProjectAssBaseMapper
 * @Description: 抵质押物 mapper
 * @author: Cai.Qing
 * @date: 2015年1月26日 上午11:15:09
 */
@MapperScan("projectAssBaseMapper")
public interface ProjectAssBaseMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 条件查询抵质押物
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 抵质押物集合
	 * @author: Cai.Qing
	 * @date: 2015年1月26日 上午11:15:18
	 */
	public List<ProjectAssBase> getAllProjectAssBase(ProjectAssBase projectAssBase);

	/**
	 * 
	 * @Description: 条件查询抵质押物数量
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 总数
	 * @author: Cai.Qing
	 * @date: 2015年4月22日 下午3:15:13
	 */
	public int getAllProjectAssBaseCount(ProjectAssBase projectAssBase);

	/**
	 * 
	 * @Description: 根据担保类型查询担保信息
	 * @param map
	 *            条件Map
	 * @return 抵质押物集合
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:34:50
	 */
	public List<ProjectAssBase> getProjectAssBaseByMortgageGuaranteeType(Map<String, Object> map);

	/**
	 * 
	 * @Description: 批量删除抵质押物
	 * @param pids
	 *            抵质押物pid 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年1月26日 上午11:16:08
	 */
	public int batchDelete(String[] pids);

	/**
	 * 
	 * @Description: 根据项目ID和担保类型删除抵质押物
	 * @param projectId
	 *            项目ID
	 * @param mortgageGuaranteeType
	 *            担保类型
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年6月5日 下午3:58:13
	 */
	public int deleteProjectAssBaseByProjectId(@Param(value = "projectId") int projectId, @Param(value = "mortgageGuaranteeType") int mortgageGuaranteeType);

	/**
	 * 
	 * @Description: 根据项目ID 查询所有担保信息
	 * @param projectId
	 * @return
	 * @author: Zhangyu.Hoo
	 * @date: 2015年4月7日 下午4:12:12
	 */
	public List<ProjectAssBase> getProjectAssBaseByProjectId(int projectId);

	/**
	 * 
	 * @Description: 办理抵质押物处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:41:52
	 */
	public int transactProjectAssBase(ProjectAssBase projectAssBase);

	/**
	 * 
	 * @Description: 抵质押物保管处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:44:18
	 */
	public int safekeepingProjectAssBase(ProjectAssBase projectAssBase);

	/**
	 * 
	 * @Description: 抵质押物提取申请
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:44:01
	 */
	public int applyExtractionProjectAssBase(ProjectAssBase projectAssBase);

	/**
	 * 
	 * @Description: 抵质押物提取处理
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:43:36
	 */
	public int applyManagetransactProjectAssBase(ProjectAssBase projectAssBase);

	/**
	 * 
	 * @Description: 解除抵质押物
	 * @param projectAssBase
	 *            抵质押物对象
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月8日 下午5:43:13
	 */
	public int relieveProjectAssBase(ProjectAssBase projectAssBase);

	/**
	 * 
	 * @Description: 批量撤销抵质押物状态(把状态修改为 等待抵质押物办理 状态)
	 * @param pid
	 *            抵质押物ID(1,2,3)
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年7月3日 上午11:18:51
	 */
	public int revokeProjectAssBase(String[] pids);

	/**
	 * 根据主键获取抵押物对象
	 * 
	 * @param pid
	 * @return
	 * @author: qiancong.Xian
	 * @date: 2015年4月11日 下午2:31:19
	 */
	public ProjectAssBase getProjectAssBaseByPid(int pid);

	public List<ProjectAssBase> getCommonProjectAssBaseByPid(int pid);

	/**
	 * 更新抵押物的处理信息
	 * 
	 * @param projectAssBase
	 * @return
	 * @author: qiancong.Xian
	 * @date: 2015年4月11日 下午2:31:34
	 */
	int updateProjectAssBaseProcessing(ProjectAssBase projectAssBase);

}
