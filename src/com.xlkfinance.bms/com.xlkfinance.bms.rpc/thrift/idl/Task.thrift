namespace java com.xlkfinance.bms.rpc.task
include "Common.thrift"
include "Workflow.thrift"

/**任务管理**/
/**struct TaskVo{
	1: i32 pid;					//PID
	2: string taskUserName;		//用户名称
	3: string taskType;			//任务类型（工作流任务、分配任务）
	4: string allocationType;	//分配任务类型
	5: i32 allocationRefId;		//分配任务索引ID
	6: string taskName;			//任务名称
	7: string taskDetail;		//任务内容
	8: string allocationDttm;	//分配时间
	9: string completeDttm;		//完成时间
	10: i32 taskStatus;			//任务状态
	11: i32 status;				//状态
}**/

/**任务设定Vo**/
struct TaskSettingVo{
	 1: i32 pid;
	 2: string workflowProcessDefId;
	 3: string workflowProcessDefName;
	 4: string workflowTaskId;
	 5: string workflowTaskName;
	 6: i32 oldUserId;
	 7: i32 agencyUserId;
	 8: string agencyUserName;
	 9: string beginDt;
	 10: string endDt;
	 11: i32 useStatus;
	 12: string creDttm;
	 13: i32 status;
}


/**任务管理接口**/
service TaskService{
	/**
	 * 根据TaskVo实体类查询任务列
	 * @param userId 用户ID
	 * @return List<TaskVo>
	 * @throws ThriftServiceException
	 */
	list<Workflow.TaskVo> getTaskVosByTaskVo(1:Workflow.TaskVo taskVo) throws (1:Common.ThriftServiceException e);

	/**
	 * 新增任务集合
	 * @param taskVos 任务TaskVo实体类集合
	 * @throws ThriftServiceException
	 */
	 void insertTaskVos(1:list<Workflow.TaskVo> taskVos) throws (1:Common.ThriftServiceException e);
	
	/**
	 * 新增任务
	 * @param taskVo 任务TaskVo实体类
	 * @throws ThriftServiceException
	 */
	void insertTaskVo(1:Workflow.TaskVo taskVo) throws (1:Common.ThriftServiceException e);
	
	/**
	 * 更改任表集合
	 * @param taskVos 任务列表集合
	 * @throws ThriftServiceException
	 */
	void updateTaskVos(1:list<Workflow.TaskVo> taskVos) throws (1:Common.ThriftServiceException e);
	
	/**
	 * 根据用户据Id查询设定任务列表
	 * @param userId
	 * @return List<TaskSettingVo>
	 * @throws ThriftServiceException
	 */
	list<TaskSettingVo> findTaskSettingsByUserId(1:i32 userId) throws (1:Common.ThriftServiceException e);
	
	/**
	 * 新增设定任务
	 * @param settingVo
	 * @throws ThriftServiceException
	 */
	void insertTaskSetting(1:TaskSettingVo settingVo) throws (1:Common.ThriftServiceException e);
	
	/**
	 * 修改设定任务
	 * @param settingVo
	 * @throws ThriftServiceException
	 */
	void updateTaskSetting(1:TaskSettingVo settingVo) throws (1:Common.ThriftServiceException e);
	
	/**
	  * @Description: 根据Id 删除已设定的任务
	  * @param pid 
	  * @throws DataAccessException
	  * @author: Daijingyu
	  * @date: 2015年2月8日 下午5:08:56
	  */
	void deleteTaskSetting(1:i32 pid) throws (1:Common.ThriftServiceException e);
	
	/**
	  * @Description: 根据pids 批量删除
	  * @param pids
	  * @throws DataAccessException
	  * @author: Dai.jingyu
	  * @date: 2015年3月13日 上午11:58:40
	  */
	void deleteTaskSettings(1:list<i32> pids) throws(1:Common.ThriftServiceException e);
}


