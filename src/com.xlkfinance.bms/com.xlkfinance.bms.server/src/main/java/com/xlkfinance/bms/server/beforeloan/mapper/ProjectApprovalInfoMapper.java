package com.xlkfinance.bms.server.beforeloan.mapper;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.beforeloan.ProjectApprovalInfo;

/**
 * 
 * @ClassName: ProjectApprovalInfoMapper
 * @Description: 审批信息 Mapper
 * @author: Cai.Qing
 * @date: 2015年3月10日 下午4:44:42
 */
@MapperScan("projectApprovalInfoMapper")
public interface ProjectApprovalInfoMapper<T, PK> extends BaseMapper<T, PK> {

	/**
	 * 
	 * @Description: 根据项目ID查询落实条件
	 * @param projectId
	 *            项目ID
	 * @return 当前项目的所有落实条件
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午4:55:18
	 */
	public List<ProjectApprovalInfo> getProjectApprovalLs(int projectId);

	/**
	 * 
	 * @Description: 根据项目ID查询管理要求
	 * @param projectId
	 *            项目ID
	 * @return 当前项目的所有管理要求
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午4:55:41
	 */
	public List<ProjectApprovalInfo> getProjectApprovalGl(int projectId);

	/**
	 * 
	 * @Description: 批量删除
	 * @param pids
	 *            审批信息PID 数组
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年3月10日 下午5:08:13
	 */
	public int batchDelete(String[] pids);

	/**
	 * 
	 * @Description: 更新是否确认
	 * @param map
	 *            条件Map
	 * @return 受影响的行数
	 * @author: Cai.Qing
	 * @date: 2015年4月21日 上午10:34:04
	 */
	public int updateIsConfirmPrimaryKey(Map<String, Object> map);
	
	/**
	 * 
	 * @Description: 利率贷后管理要求增加
	 * @param projectApprovalInfo
	 *            
	 * @return 受影响的行数
	 * @author: Rain.Lv
	 * @date: 2015年3月10日 下午5:08:13
	 */
	public int addProjectApprovalInfoRes(ProjectApprovalInfo projectApprovalInfo);
	
	/**
	 * 
	  * @Description: 修改利率贷后管理要求增加
	  * @param projectApprovalInfo
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 上午10:54:23
	 */
	public int updateProjectApprovalInfoResInfo(ProjectApprovalInfo projectApprovalInfo);
	
 
	/**
	 * 
	  * @Description: 根据利率Id查询落实条件
	  * @param interestChgId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午2:18:25
	 */
	public List<ProjectApprovalInfo> getProjectApprovalLsInfo(int interestChgId);

	 
	/**
	 * 
	  * @Description: 根据利率ID查询管理要求
	  * @param interestChgId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午2:18:07
	 */
	public List<ProjectApprovalInfo> getProjectApprovalGlInfo(int interestChgId);
	/**
	 * 
	  * @Description: 利率变更增加成功后通过项目id把贷审会要求数据保存
	  * @param interestChgId
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午3:19:18
	 */
	public void addProjectApprovalGlInfoByProjectId(int interestChgId);
	
	/**
	 *  
	  * @Description: 通过项目id得出全部的管理要求信息
	  * @param projectId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午3:28:16
	 */
	public List<ProjectApprovalInfo> getProjectApprovalInfo(int projectId); 
	
	/**
	 * 
	  * @Description: 更新利率变更是否确认
	  * @param map
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午4:54:37
	 */
	public int updateIsConfirmPrimaryKeyInfo(Map<String, Object> map);
	
	/**
	 *  
	  * @Description: 批量删除利率变更贷审会信息
	  * @param pids
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午5:17:36
	 */
	public int deleteProjectApprovalById(String[] pids);
	
	/**
	 * 
	  * @Description: 通过项目id删除贷审会信息（把原来的贷审会信息删掉替换新表中的利率变更贷审会信息）
	  * @param projectId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午5:39:07
	 */
	public int deleteProjectApprovalByProjectId(int projectId);
	
	/**
	 * 
	  * @Description: 根据利率变更id得到信息
	  * @param interestChgId
	  * @return
	  * @author: Rain.Lv
	  * @date: 2015年6月27日 下午5:45:50
	 */
	public List<ProjectApprovalInfo> getProjectApprovalAll(int interestChgId); 
	
}
