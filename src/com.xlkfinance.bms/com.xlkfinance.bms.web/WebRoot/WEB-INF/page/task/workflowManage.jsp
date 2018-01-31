<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>


<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="scroll-bar-div">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form method="post" id="workflow_from" name="workflow_from" >
				<!-- 查询条件 -->
				<div style="margin:10px">
					<span>任务名称:</span>
						<input class="easyui-textbox" name="taskName" id="taskName"/> &nbsp;
					<span>项目名称:</span>
						<input class="easyui-textbox" name="workfloPprojectName" id="workfloPprojectName"/> &nbsp;
					<span>提交时间:</span>
						<input class="easyui-datebox" name="allocationDttm" id="allocationDttm" style="width:200px"/> &nbsp;
						<br/>
					<span>用户账号:</span>
						<input class="easyui-textbox" name="taskUserName" id="taskUserName"/> &nbsp;	
					<span>用户名称:</span>
						<input class="easyui-textbox" name="taskUserRealName" id="taskUserRealName"/> &nbsp;	
					<span>任务状态:</span>
						<select id="taskStatus" class="easyui-combobox" name="taskStatus" style="width:150px">   
						    <option value="1">处理中</option>   
						    <option value="2">已结束</option>  
						</select>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="reloadWorkflowData()">查询</a>
				</div>
			</form>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="deleteWorkflow()">删除</a>
			<!-- 操作按钮 -->
			<!-- <div style="padding-bottom:5px;">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true"  onclick="cloasTask()">关闭任务</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="openTask()">开启任务</a>
			</div> -->
		</div>
		<div  style="height: 91%;">
			<table id=workflow_manage_grid class="easyui-datagrid"
				style="height:91%; width: auto;"
				data-options="
			    url: '${basePath}workflowController/queryAllRunTask.action',
			    method: 'POST',
			    idField: 'workflowInstanceId',
			    toolbar: '#task_toolbar',
			    fitColumns:true,
			    rownumbers: true,
			    pagination: true,
			    onLoadSuccess:function(data){
					clearSelectRows('#workflow_manage_grid');
				}
			    ">
				<thead>
					<tr>
						<th data-options="field:'ck',checkbox:true"></th>
						<th data-options="field:'pid',hidden:true"></th>
						<th data-options="field:'workflowTaskDefkey',hidden:true"></th>
						<th data-options="field:'workflowInstanceId',hidden:true"></th>
						<th data-options="field:'refId',width:100,align:'center'">引用ID</th>
						<th data-options="field:'projectId',width:100,align:'center'">项目ID</th>
						<th data-options="field:'taskUserName',width:100,align:'center'">用户账号</th>
						<th data-options="field:'taskUserRealName',width:100,align:'center'">用户名称</th>
						<th data-options="field:'workfloPprojectName',width:100,align:'center'">项目名称</th>
						<th data-options="field:'workflowName',width:100,align:'center'">流程名称</th>
						<th data-options="field:'taskName',width:100,align:'center'">任务名称</th>
						<th data-options="field:'taskDetail',width:100,align:'center'">当前任务内容</th>
						<th data-options="field:'allocationDttm',width:100,align:'center'">提交任务时间</th>
						<th data-options="field:'taskStatus',width:100,align:'center',
							formatter:function(value){
								if(value){
									if('1' == value){
										return '处理中'
									}else if('2' == value){
										return '已结束'
									}else{
										return '默认'
									}
								}
							}">任务状态</th>
					   <th data-options="field:'description',width:220,align:'center', 
						formatter:operation">操作</th> 
					</tr>
				</thead>
			</table>
		</div>
		<div id="my_agent_win">
			<%@ include file="../common/task_hi_list.jsp"%>
		</div>
    </div>
</body>

<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/workflowManage.js"></script>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
