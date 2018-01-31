/**
 * 操作
 */
function operation(value,rowData,rowIndex){
	var btn ="";
	if(rowData.taskStatus == 1){
		btn+="<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='disposeClick("+
	      JSON.stringify(rowData)+")' href='#'>" +
	      "<span style='color:blue; '>处理</span></a>&nbsp;|&nbsp;"
	}
	btn+="<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='processLogging("+
      JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
      "<span style='color:blue; '>处理记录查看</span></a>";
	if(rowData.refId!=0){
		btn+="&nbsp;|&nbsp;<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='deleteWorkflow("+
		JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
		"<span style='color:blue; '>删除</span></a> ";  
	 }
      return btn;   
}

/**
 * 删除流程
 */
function deleteWorkflow(){
	$.messager.confirm('确认','确认删除此流程吗?',function(e){  
        if(e){  
       	 	var rows = $('#workflow_manage_grid').datagrid("getSelections");
       	 	//流程实例Id集合
       	 	var workflowInstanceIds = "";
            for (var i = 0 ; i < rows.length; i++) {
            	workflowInstanceIds += rows[i].workflowInstanceId+",";
    	    }
            var url = BASE_PATH+'workflowController/deleteWorkflowInstanceIds.action'
            $.post(url,
            	{'workflowInstanceIds':workflowInstanceIds},
            	function(data){
            		data = eval("("+data+")");
            		if("true"==data){
            			$.messager.alert("错误","删除成功！","info");
            			$('#workflow_manage_grid').datagrid("reload");
            		}else{
            			$.messager.alert("错误","删除失败！","error");
            			$('#workflow_manage_grid').datagrid("reload")
            		}
            	});
        }  
    })  
}

function processLogging(rowData){
	loadProcessLoggingGrid(rowData);
	var height = document.body.clientHeight*0.9;
	var width = document.body.clientWidth*0.9;
	$('#my_agent_win').window({
		width:width,
		height:400,
		title : "任务处理详情",
		modal:true,
		collapsible:false,
		minimizable:false,
		maximizable:false
	});
}
/**
 * 
 * @param taskId
 */
function taskDetails(data){
	loadTaskManageDetails(data);
	var height = document.body.clientHeight*0.9;
	var width = document.body.clientWidth*0.9;
	$('#win').window({
		width:width,
		height:height,
		title : "任务处理详情",
		modal:true
	});
}

/**
 * 从新加载流程数据
 */
function reloadWorkflowData(){
	$('#workflow_manage_grid').datagrid('reload', {
		taskName : $("#taskName").val(),
		workfloPprojectName : $("#workfloPprojectName").val(),
		allocationDttm : $("#allocationDttm").datebox('getValue'),
		taskUserName : $("#taskUserName").val(),
		taskUserRealName : $("#taskUserRealName").val(),
		taskStatus : $('#taskStatus').combobox('getValue')


	});
}
	