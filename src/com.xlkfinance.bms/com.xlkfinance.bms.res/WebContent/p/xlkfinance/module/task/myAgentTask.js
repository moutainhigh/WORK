/**
 * 加载任务管理列表数据
 */
function loadMyAgentList(){
	$('#my_Agent_Task_grid').datagrid({
		//toolbar: my_Agent_Task_toolbar,
	 	width: '100%',
	 	fitColumns: true,
	 	height: '100%',            
        striped: true,  
        url: BASE_PATH+'taskController/searchTaskList.action', 
        loadMsg: '数据加载中请稍后……',  
        pagination: true,  
        rownumbers: true,
        checkOnSelect: false,
        onClickRow:function(rowIndex,rowData){
        	$("#my_Agent_Task_grid").datagrid("clearSelections");
		},
	    columns:[[
	             {field:'pid',hidden:true},
	             {field:'workflowTaskDefkey',hidden:true},
	 			 {field:'workflowInstanceId',hidden:true},
	 			 {field:'workflowTaskId',hidden:true},
	 			 {field:'workfloPprojectName',title:'项目名称',width:100,align:'center'},
	             {field:'workflowName',title:'流程名称',width:100,align:'center'},
	  	         {field:'taskName',title:'结点名称',width:100,align:'center'},
	  	         {field:'allocationDttm',title:'任务提交时间',width:100,align:'center'},
	  			 {field:'description',title:'操作',width:120,align:'center', 
	  				formatter:function(value,rowData,rowIndex){ 
	  		            var btn = "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='disposeClick("+
	  		            JSON.stringify(rowData)+")' href='#'>" +
	  		            "<span style='color:blue; '>处理</span></a>&nbsp;|&nbsp;"+
	  		            "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='processLogging("+
	  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
	  		            "<span style='color:blue; '>处理记录查看</span></a> ";  
	  		            return btn;  
	  				}  
	  			}
	    ]]
	});
};

function seachTaskList(){
	$('#task_from').form('submit',{
        success:function(data){
           	$('#grid').datagrid('loadData', eval("{"+data+"}"));
       }
    });
	
}
/****************************************************************************************/
/**
 * @param taskId
 */
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

$(document).ready(function() {
	/*var height = document.body.clientHeight;
	$('#my_Agent_Task_grid').datagrid({ 
		height:(height)
	});*/
	//loadMyAgentList();
});
	