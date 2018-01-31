/**
 * 加载任务管理列表数据
 */
function loadTaskAgentSettingList(){
	$('#task_agent_setting_grid').datagrid({
		toolbar: ["-", {
            id: 'add_agent_task',
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
            	 openWin();
            }
        }, "-", {
            id: 'remove_agent_task',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () { 
            	var rows = $("#task_agent_setting_grid").datagrid("getChecked");
            	var pids = new Array();
            	$(rows).each(function(i,row){
            		pids[i] = row.pid;
            	});
            	$.messager.confirm('确认','确认进行批量删除?',function(e){  
            	    if(e){  
            	    	var url =  BASE_PATH+'taskController/deleteTaskSettings.action'
            	    	$.post(url, {'pids':pids},
            			   function(data){
            	    		   $('#task_agent_setting_grid').datagrid("load");
            		    		var ret = eval("("+data+")");
            	            	var header = ret.header;
            	            	if(header["success"] == true){
            	            		$('#task_agent_setting_grid').datagrid('load');
            	            		$.messager.alert("提示","删除成功！");
            	            		$('#task_agent_setting_win').window('close');
            	            		var rows = $('#task_agent_setting_grid').datagrid("load");
            	            	}else{
            	            		$.messager.alert("提示","删除失败！");
            	            	}
            			   });
            	    }  
            	}) ;
            }
        }, "-"],
	 	width: '100%',
	 	fitColumns: true,
	 	height: '100%',            
        striped: true,  
        url: BASE_PATH+'taskController/searchTaskSettingList.action', 
        loadMsg: '数据加载中请稍后……',  
        pagination: true,  
        rownumbers: true,
        checkOnSelect: false,
        onClickRow:function(rowIndex,rowData){
        	$("#task_agent_setting_grid").datagrid("clearSelections");
		},
	    columns:[[
	        {field:'ck',checkbox:true},     
	        {field:'pid',hidden:true},
	        {field:'workflowProcessDefId',hidden:true},
			{field:'workflowTaskId',hidden:true},
			{field:'agencyUserId',hidden:true},
			{field:'workflowProcessDefName',title:'代办工作流名称',width:100,align:'center'},
			{field:'workflowTaskName',title:'工作流结点',width:100,align:'center'},
			{field:'beginDt',title:'代办开始时间',width:100,align:'center'},
			{field:'endDt',title:'代办结束时间',width:100,align:'center'},
			{field:'agencyUserName',title:'代办人',width:100,align:'center'},
			{field:'useStatus',title:'启用状态',width:100,align:'center',
				formatter:function(value,rowData,rowIndex){
					if("1"==value){
						return "是";
					}else{
						return "否";
					}
				}
			},
			{field:'description',title:'操作',width:120,align:'center', 
				formatter:function(value,rowData,rowIndex){  
					var btn = "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='editTaskAgentSetting("+
  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
  		            "<span style='color:blue; '>编辑</span></a>&nbsp;|&nbsp;"+
  		            "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='deleteTaskAgentSetting("+
  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
  		            "<span style='color:blue; '>删除</span></a> ";  
  		            return btn;    
				}  
			}
	    ]],
	    loadFilter: function(result) {
	    	var data = result.rows;
	    	var viewData = {'total':result.count,'rows':data}
	    	return viewData;
	    }
	});
	var p = $('#task_agent_setting_grid').datagrid('getPager');  
    $(p).pagination({  
    	 width: '100%',
    	 height: '100%',
         pageSize: 10,//每页显示的记录条数，默认为10  
         pageList: [10,20,30,40],//可以设置每页记录条数的列表  
         beforePageText: '第',//页数文本框前显示的汉字  
         afterPageText: '页    共 {pages} 页',  
         displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	    }); 
};
function loadLookupValByLookType(){    
	var rec = $("#workflowProcessDefId").val();
    var url = BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType='+rec;    
    $('#workflowTaskId').combobox('reload', url);    
}
/**
 * 修改任务代办设定
 * @param rowData
 */
function editTaskAgentSetting(rowData){
	$('#workflowProcessDefId').combobox('reload'); 
	openWin();
	$('#task_agent_setting_from').form('load',{
		pid:rowData.pid,
		workflowProcessDefId:rowData.workflowProcessDefId,
		workflowTaskId:rowData.workflowTaskId,
		beginDt: rowData.beginDt,
		endDt:rowData.endDt,
		agencyUserId:rowData.agencyUserId,
		useStatus:rowData.useStatus
	});
	loadLookupValByLookType();
	
}
function deleteTaskAgentSetting(rowData,rowIndex){
	$.messager.confirm('确认','确认删除?',function(e){  
	    if(e){  
	    	var url =  BASE_PATH+'taskController/deleteTaskSetting.action'
	    	$.post(url, {'pid':rowData.pid},
			   function(data){
	    		// $('#task_agent_setting_grid').datagrid("load");
		    		var ret = eval("("+data+")");
	            	var header = ret.header;
	            	if(header["success"] == true){
	            		$('#task_agent_setting_grid').datagrid('load');
	            		$.messager.alert("提示","删除成功！");
	            		$('#task_agent_setting_win').window('close');
	            		var rows = $('#task_agent_setting_grid').datagrid("load");
	            	}else{
	            		$.messager.alert("提示","删除失败！");
	            	}
			   });
	    }  
	}) ;
}

/**
 * 打开二级窗口
 */
function openWin(){
	resetForm();
	var height = document.body.clientHeight*0.45;
	var width = document.body.clientWidth*0.7;
	$('#task_agent_setting_win').window({
		width:width,
		collapsible:false,
		minimizable:false,
		maximizable:false,
		height:height,
		title : "任务代办设定",
		modal:true
	});
}
/**
 * 重置表单
 */
function resetForm(){
	$('#task_agent_setting_from').form('reset');
}
/**
 * 保存任务设定数据
 */
function saveTaskAgentSet(){
	$('#task_agent_setting_from').form('submit',{
		onSubmit : function() {
			var starTime=$('#beginDt').datebox('getValue');
			var endTime=$('#endDt').datebox('getValue');
			
			if(starTime>endTime){
        		$.messager.alert("提示",'代办结束时间要大于开始时间');
        		return false;
        	}
			return $(this).form('validate');
		},
        success:function(ret){
        	var ret = eval("("+ret+")");
        	var header = ret.header;
        	if(header["success"]){
        		$('#task_agent_setting_grid').datagrid('load');
        		$.messager.alert("提示",header["msg"]);
        		$('#task_agent_setting_win').window('close'); 
        	}else{
        		$.messager.alert("提示",header.message);
        	}
       }
    });
}
$(document).ready(function() {
	$('#task_agent_setting_win').window("close");
	var height = document.body.clientHeight;
	$('#task_agent_setting_grid').datagrid({ 
		height:(height)
	});
	loadTaskAgentSettingList();
	$('#beginDt,#endDt').datebox({    
	    width:'220'
	});  

});
	