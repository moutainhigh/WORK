package com.xlkfinance.bms.server.workflow.mapper;

import java.util.List;

import org.activiti.engine.task.Task;
import org.apache.ibatis.annotations.Param;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.dao.DataAccessException;

import com.achievo.framework.mybatis.mapper.BaseMapper;
import com.xlkfinance.bms.rpc.common.GridViewDTO;
import com.xlkfinance.bms.rpc.workflow.BizTaskAcctProHisVo;
import com.xlkfinance.bms.rpc.workflow.EndTaskPageVO;
import com.xlkfinance.bms.rpc.workflow.TaskHistoryDto;
import com.xlkfinance.bms.rpc.workflow.TaskVo;
import com.xlkfinance.bms.rpc.workflow.WorkflowProjectVo;

@MapperScan("workflowProjectMapper")
public interface WorkflowProjectMapper<T, PK> extends BaseMapper<T, PK> {
	
	/**
	 * 根据流程ID查询WorkflowProjectVo实例
	 * @param workflowInstanceId 流程实例ID
	 * @return List<WorkflowProjectVo>
	 * @throws DataAccessException
	 */
	public WorkflowProjectVo findWorkflowProject(WorkflowProjectVo workflowProjectVo) throws DataAccessException;
	
	/**
	 * 新增 流程与项目的映射关系
	 * @param workflowProjectVo
	 * @throws DataAccessException
	 */
	public void insertWorkflowProject(WorkflowProjectVo workflowProjectVo) throws DataAccessException;
	
	/**
	  * @Description: 查询流程记录数据
	  * @param bizTaskAcctProHisVo
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年4月22日 下午5:25:22
	  */
	public int selectTaskAcctProHis(BizTaskAcctProHisVo bizTaskAcctProHisVo);

	/**
	  * @Description:  根据客户ID和流程实例ID查询数据
	  * @param acctId
	  * @return int 
	  * @throws DataAccessException
	  * @author: JingYu.Dai
	  * @date: 2015年5月2日 下午2:30:39
	  */
	public int getTaskAcctProHisByAcctId(int acctId) throws DataAccessException;
	
	/**
	  * @Description: 根据项目ID和流程实例ID查询数据
	  * @param projectId 项目ID
	  * @return int
	  * @author: JingYu.Dai
	  * @date: 2015年4月22日 下午8:19:34
	  */
	public int getTaskAcctProHisByProjectId(int projectId);
	
	/**
	  * @Description: 新增流程记录数据
	  * @param bizTaskAcctProHisVo
	  * @author: JingYu.Dai
	  * @date: 2015年4月22日 下午5:26:15
	  */
	public void insertTaskAcctProHis (BizTaskAcctProHisVo bizTaskAcctProHisVo);
	
	/**
	  * @Description: 修改流程记录数据
	  * @param bizTaskAcctProHisVo
	  * @author: JingYu.Dai
	  * @date: 2015年4月22日 下午5:26:35
	  */
	public void updateTaskAcctProHisPWStatus(BizTaskAcctProHisVo bizTaskAcctProHisVo);
	
	/**
	 * 查询已结束任务
	 * @param WorkflowHistoryVo
	 * @throws DataAccessException
	 */
	public List<TaskHistoryDto> selectWorkflowHistoryList(EndTaskPageVO historyVo) throws DataAccessException;
	/**
	 * 已结任务size
	 * @param endTaskPageVO
	 * @return
	 */
	public int countWorkflowHistoryList(EndTaskPageVO endTaskPageVO);
	/**
	 * 查询已结束任务
	 * @param WorkflowHistoryVo
	 * @throws DataAccessException
	 */
	public int selectWorkflowHistoryListTotal(EndTaskPageVO historyVo) throws DataAccessException;
	
	/**
	  * @Description: 返回流程中正在运行的上一个节点，和流程实例ID
	  * @param map
	  * @return String (返回流程中正在运行的上一个节点)
	  * @throws DataAccessException
	  * @author: JingYu.Dai
	  * @date: 2015年4月26日 下午6:47:47
	 */
	public String getRunLastTaskKeyByWIId(String workflowInstanceId) throws DataAccessException;
	
	/**
	  * @Description: 根据refId删除流程项目中间表数据
	  * @param refId 引用Id
	  * @return int 受影响行数
	  * @throws DataAccessException
	  * @author: JingYu.Dai
	  * @date: 2015年5月29日 下午7:03:23
	 */
	public int deleteWorkflowProject(int refId) throws DataAccessException;
	
	/**
	  * @Description: 根据任务Id查询用户名集合字符窜
	  * @param taskId 任务ID
	  * @return String
	  * @throws DataAccessException
	  * @author: JingYu.Dai
	  * @date: 2015年6月8日 下午5:03:33
	 */
	public String getUserNamesByTaskId(String taskId) throws DataAccessException;
	
	/**
	  * @Description: 查询所有运行的任务
	  * @param taskVo
	  * @return List<TaskVo>
	  * @throws DataAccessException
	  * @author: JingYu.Dai
	  * @date: 2015年6月19日 下午4:55:18
	 */
	public List<TaskVo> findAllRunTask(TaskVo taskVo) throws DataAccessException;
	
	/**
	  * @Description: 查询所有运行的任务的总数
	  * @param taskVo 
	  * @return int 
	  * @throws DataAccessException
	  * @author: JingYu.Dai
	  * @date: 2015年6月29日 上午11:20:47
	 */
	public int findAllRunTaskTotal(TaskVo taskVo) throws DataAccessException;

   /**
    * 根据流程的key和项目id，查询运行的数据
    *@author:liangyanjun
    *@time:2016年3月4日下午5:39:57
    *@param workflowProjectVo
    *@return
    */
   public List<WorkflowProjectVo> getRunWorkflowProject(WorkflowProjectVo workflowProjectVo);
	
   public List<GridViewDTO> queryTaskList(@Param(value = "userName") String userName, @Param(value = "projectName") String projectName, @Param(value = "page") int page, @Param(value = "rows") int rows);

   public List<WorkflowProjectVo> findWorkflowProjectByWhere(WorkflowProjectVo queryProjectVo);
   
}
