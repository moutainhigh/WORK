namespace java com.xlkfinance.bms.rpc.workflow
include "Common.thrift"
include "System.thrift"

/**流程与项目映射表**/
struct WorkflowProjectVo{
	1: i32 pid;
	2: string workflowInstanceId;	//流程ID
	3: i32 refId;	//应用id 有可能是项目id , 有可能是利率变更id 等等
	4: string processDefinitionKey; //流程定义id
	5: i32 projectId;	//项目ID
	6: string workflowTaskDefKey; //任务节点KEY
}
/**流程项目客户中间表**/
struct BizTaskAcctProHisVo{
	1: i32 pid;						//主键
	2: i32 acctId;					//客户ID
	3: i32 projectId;				//项目ID
	4: string workflowId;				//流程定义ID
	5: i32 projectWorkflowStatus;	//项目流程状态（1：审核中，2：流程结束）
	6: string beginDt;				//开始日期
	7: string endDt;				//结束日期
	8: i32 status;					//状态
}

/**任务管理**/
struct TaskVo{
	1: i32 pid;					//PID
	2: string taskUserName;		//用户名称
	3: string taskUserRealName; //用户姓名
	4: i32 taskType;			//任务类型(工作流，分配任务）
	5: string allocationType;	//分配任务类型
	6: i32 allocationRefId;		//分配任务索引ID
	7: string workflowName;		//流程名称
	8: string taskName;			//任务名称
	9: string allocationDttm;	//分配时间
	10: string completeDttm;	//完成时间
	11: i32 taskStatus;			//任务状态
	12: i32 status;				//状态
	13: string workflowProcessDefkey; //工作流定义ID
	14: string workflowInstanceId; //工作流实例ID
	15: string workflowTaskDefKey; //任务节点KEY
	16: string workflowTaskId; //任务ID
	17: string nextRoleCode;//下一节点角色名称
	18: i32 refId;  //引用ID 可以是项目ID , 利率变更ID 等
	19: string allowTurnDown;
	20: i32 page; //第几页
	21: i32 rows; //总页数
	22: i32 projectId;	//项目ID
	23: string workfloPprojectName;
}

/**
 *	已结办任务列表   查询条件封装
 */
struct EndTaskPageVO{
	1: string projectName;	//项目名称
	2: string executeDttm;	//处理时间
	3: string userName;		//用户名
	4: i32 page;
	5: i32 rows;
}

struct TaskHistoryDto{
	1: string workflowTaskDefKey; //任务节点KEY
	2: string workflowInstanceId; //工作流实例ID
	3: string workflowProcessName; //工作流定义名称
	4: string workflowTaskId; //任务ID
	5: string taskName;//任务名称
	6: string executeUserName; //执行人
	7: string executeUserRealName; //用户姓名
	8: string executeDttm;//执行时间
	9: string approvalStatus;//执行状态
	10: string message; //审批意见
	11: string workflowProcessDefkey; //工作流定义ID
	12: i32 refId;  //引用ID 可以是项目ID , 利率变更ID 等
	13: i32 projectId;	//项目ID
	14: string projectName; //项目名称
}

/***任务流程处理意见****/
struct TaskFlowIdea{
	1: i32 pid; 					
	2: i32 taskId;					//任务ID
	3: i32 processUserId;			//处理用户ID
	4: string allocationDttm;		//分配时间
	5: string completeDttm;			//完成时间
	6: i32 reviewResult;			//处理结果
	7: string reviewContent; 		//处理意见
	8: i32 refoulementTaskNodeId;	//驱回任务节点ID
	9: i32 submitNextTaskNodeId;	//提交下一任务节点ID
	10: i32 remindType;				//提醒方式（短信、邮件、两种）
	11: i32 status;					//状态
	
}

/***任务流程Vo****/
struct WorkflowVo{
	1: string taskNodeName;				//节点名称
	2: list<string> userIds;			//用户列表ID 集合
	3: string destTaskId;				//目标节点列表
	4: bool ifEmail;					//是否发送邮件
	5: bool ifSms;						//是否发送短信
	6: string idea;						//意见说明
	7: string taskId; 					//任务ID
	8: string workflowId;				//流程定义ID
	9: i32 refId;						//项目ID
	10: string workflowInstanceId; 		//流程实例Id
	11: string commitToTask;			//提交至下一节点的标记
	12: string vetoProject;				//是否否决项目
	13: i32 projectId;					//项目ID
	14: string workflowTaskDefKey;		//任务定义节点KEY
}

/***任务流程Vo****/
struct WorkflowHistoryVo{
	1: i32 taskId;						//任务ID
	2: string processInstractId;		//流程处理ID
	3: string processdefintionId;		//流程版本id prepaymentRequestProcess:3:107536
	4: i32 exectionId;					//流程处理数据ID
	5: string taskDefKey				//任务节点key
	6: string taskDefName;				//任务节点名称
	7: string execUserName; 			//当前处理人 用户名
	8: string execUserRealName;			//当前处理人 名字
	9: string executeDttm;					//处理时间
	10: string deleteReson; 			//
	11: string workflowProcessDefkey;			//流程key
	12: string processDefName;			//流程名称
	13: i32 projectId;					//项目ID
	14: i32 refId;						//引用项目ID
	15: string projectName;				//项目名称
	16: string message;					//审批信息
	17:i32 rows;
	18:i32 page;
}

/**流程管理**/
service WorkflowService{
	i32 executeDeploy(1:string zipFile) throws (1:Common.ThriftServiceException e);
	string executeStart(1:map<string,string> paramMap, 2:list<string> nextUserList) throws (1:Common.ThriftServiceException e);
	string executeStartOfCommon(1:map<string,string> paramMap) throws (1:Common.ThriftServiceException e);
	void completeOfCommon(1:map<string,string> paramMap) throws (1:Common.ThriftServiceException e);
	i32 executeComplete(1:map<string,string> paramMap, 2:list<string> nextUserList, 3:map<string,string> fromVariables) throws (1:Common.ThriftServiceException e);
	i32 executeTurnDown(1:map<string,string> paramMap) throws (1:Common.ThriftServiceException e);
	list<TaskVo> queryWorkFlowTodoTask(1:TaskVo taskVo) throws (1:Common.ThriftServiceException e);
	list<TaskHistoryDto> queryWorkFlowHistory(1:string processInstanceId,2:i32 page,3:i32 rows) throws (1:Common.ThriftServiceException e);
	i32 queryWorkFlowHistoryCount(1:string processInstanceId) throws (1:Common.ThriftServiceException e);
	list<TaskHistoryDto> queryWorkFlowFinishedHistory(1:EndTaskPageVO endTaskPageVO) throws (1:Common.ThriftServiceException e);
	list<System.SysLookupVal> filterTurnDownTaskNodes(1:string taskId) throws (1:Common.ThriftServiceException e);
	WorkflowProjectVo findWorkflowProject(1:WorkflowProjectVo workflowProjectVo) throws (1:Common.ThriftServiceException e);
	bool isAllowStartWorkflow(1:i32 projectId) throws (1:Common.ThriftServiceException e);
	bool isAllowStartWorkflowByAcctId(1:i32 acctId) throws (1:Common.ThriftServiceException e);
	i32 selectTaskAcctProHis(1:BizTaskAcctProHisVo acctProHisVo) throws (1:Common.ThriftServiceException e);
	list<TaskHistoryDto> selectWorkflowHistoryList(1:EndTaskPageVO workflowHistoryVo) throws (1:Common.ThriftServiceException e);
	i32 selectWorkflowHistoryListTotal(1:EndTaskPageVO workflowHistoryVo) throws (1:Common.ThriftServiceException e);
	string getRunLastTaskKeyByWIId(1:string workflowInstanceId) throws (1:Common.ThriftServiceException e);
	i32 queryWorkFlowTodoTaskTotal(1:TaskVo taskVo) throws (1:Common.ThriftServiceException e);
	void deleteProcessInstance(1:i32 refId,2:i32 projectId,3:string workflowKey) throws (1:Common.ThriftServiceException e);
	void deleteProcessInstances(1:string workflowInstanceIds) throws (1:Common.ThriftServiceException e);
	i32 getWorkflowStatusByProjectId(1:i32 projectId)throws (1:Common.ThriftServiceException e);
	TaskVo getTaskVoByWPDefKeyAndRefId(1:string workflowProcessDefkey,2:i32 refId)throws (1:Common.ThriftServiceException e);
	list<TaskVo> queryAllRunTask(1:TaskVo taskVo)throws (1:Common.ThriftServiceException e);
	i32 queryAllRunTaskTotal(1:TaskVo taskVo)throws (1:Common.ThriftServiceException e);
	string getFormValueStr(1:string taskId, 2:string formKey);
	//根据流程的key和项目id，查询运行的数据
	list<WorkflowProjectVo> getRunWorkflowProject(1:WorkflowProjectVo workflowProjectVo);
	//根据流程实例ID查询任务id
	string getTaskIdByProcessInstanceId(1:string processInstanceId);
	//根据流程实例ID查询任务基本信息map
	map<string,string> getTaskMapByProcessInstanceId(1:string processInstanceId);
	//执行工作流
	i32 executeFlow(1:i32 userId, 2:i32 refId, 3:i32 projectId,4:string processDefinitionKey,5:string startTaskDefKey,6:string message)
	
   /**
	* @Description: 查找业务处理人
	* @param User user 业务当前处理人
	* @param roleId 业务下一节点处理角色
	* @return list<SysUser>
	* @author: huxinlong
	* @date: 2016年5月03日 下午2:51:26
	*/
	list<System.SysUser> findBizUser(1:System.SysUser user,2:i32 roleId) throws (1:Common.ThriftServiceException e);
	list<System.SysUser> findBizUserByRoleCode(1:System.SysUser user,2:string roleCode) throws (1:Common.ThriftServiceException e);
	
	set<map<string,string>> getUserMapByRoleCodes(1:string roleCodes,2:System.SysUser user);
}
