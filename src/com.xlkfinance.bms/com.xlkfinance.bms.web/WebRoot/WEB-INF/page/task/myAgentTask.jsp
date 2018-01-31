<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!-- <div id="my_Agent_Task_toolbar" class="easyui-panel"
	style="border-bottom: 0; height: 50px">
	<form id="task_from" name="my_Agent_Task_from">
		查询条件
		<div style="margin: 10px">
			任务名称: <input class="easyui-textbox" name="taskName" id="taskName" />&nbsp; 
			分配时间: <input id="startDate" name="startDate" type="text"class="easyui-datebox">&nbsp; 
			至 &nbsp; <input id="endDate" name="endDate" type="text" class="easyui-datebox">&nbsp;&nbsp;
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"onclick="searchTaskList()">查询</a> &nbsp;&nbsp; 
			<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="reset()">重置</a>
		</div>
	</form>
	操作按钮
</div> -->
<script type="text/javascript">
//查询 
function ajaxSearch(){
	var pageNumber=$('#my_Agent_Task_grid').datagrid('options')['pageSize'];
	$('#rows').val(pageNumber);
	$('#workflow_from').form('submit',{
        success:function(data){
        	var str = JSON.parse(data);
           	$('#my_Agent_Task_grid').datagrid('loadData',str);
           	$('#my_Agent_Task_grid').datagrid('clearChecked');
       }
    });
};


function formatterCz(value,rowData,rowIndex){
	var btn = "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='disposeClick("+
      JSON.stringify(rowData)+")' href='#'>" +
      "<span style='color:blue; '>处理</span></a>&nbsp;|&nbsp;"+
      "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='processLogging("+
      JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
      "<span style='color:blue; '>处理记录查看</span></a> ";  
      return btn; 
}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form method="post" id="workflow_from" action="searchTaskList.action">
		<!-- 查询条件 -->
		<div style="margin:10px">
			<span>项目名称:</span>
				<input class="easyui-textbox" name="workfloPprojectName" id="workfloPprojectName"/> &nbsp;
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
		</div>
	</form>
	</div>
	
<div id="scroll-bar-div">
<table id="my_Agent_Task_grid" title="任务管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'searchTaskList.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: false,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	    
	<!--
	 表头 -->
	<thead>
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'workflowTaskDefkey',hidden:true"></th>
			<th data-options="field:'workflowInstanceId',hidden:true"></th>
			<th data-options="field:'workflowTaskId',hidden:true"></th>
			<th data-options="field:'workfloPprojectName',width:'20%',sortable:true" >项目名称</th>
			<th data-options="field:'workflowName',width:'20%',sortable:true">流程名称</th>
			<th data-options="field:'taskName',width:'20%',sortable:true">结点名称</th>
			<th data-options="field:'allocationDttm',width:'20%',sortable:true">任务提交时间</th>
			<th data-options="field:'description',formatter:formatterCz,width:'20%',sortable:true">操作</th>
		</tr>
	</thead>
</table>
<div id="my_agent_win">
	<%@ include file="../common/task_hi_list.jsp"%>
</div>
</div>
</div>
</body>
<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/task/myAgentTask.js"></script>

