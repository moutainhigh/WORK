<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<table id="my_agent_grid" ></table>
<script type="text/javascript">

	var BASE_PATH = "${basePath}";
	/**
	 * 加载任务管理-任务处理详情列表数据
	 */
	function loadProcessLoggingGrid(rowData){
		var title = "";
		var workflowInstanceId = -1;
		if(rowData){
			var taskName = rowData.taskName;
			var startDate = rowData.allocationDttm;
			if(taskName && startDate){
				title = "<span style='float: left;'>任务名称:"+
				taskName+"&nbsp;&nbsp;&nbsp;&nbsp;分配时间:"+startDate+"<span/>";
			}
			workflowInstanceId = rowData.workflowInstanceId
		}
		
		$('#my_agent_grid').datagrid({
		 	width: '100%',
		 	height: '360',            
	        striped: true,  
	        title: title,
	        url: BASE_PATH+'taskController/searchHiTaskList.action', 
	        queryParams:{"workflowInstanceId":workflowInstanceId},
	        loadMsg: '数据加载中请稍后……',  
	        pagination: true,  
	        rownumbers: true,
	        checkOnSelect: false,
	        fitColumns:false,
	        nowrap:false,
	        onClickRow:function(rowIndex,rowD){
	        	$("#my_agent_grid").datagrid("clearSelections");
			},
		    columns:[[
		        {field:'pid',hidden:true},
		        {field:'executeUserName',hidden:true},
		        {field:'taskName',title:'任务描述',align:'center'},
				{field:'executeUserRealName',title:'执行人',align:'center'},
				{field:'executeDttm',title:'任务时间',align:'center'},
				{field:'approvalStatus',title:'任务状态',align:'center'},
				{field:'message',width:700,title:'意见与说明',align:'left'}
		    ]]
		});
		
	};
</script>
