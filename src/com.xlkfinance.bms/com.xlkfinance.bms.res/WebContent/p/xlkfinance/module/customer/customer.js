/**
 * 加载列表数据
 */
function loadReportOverviewList(){
	//alert($("#comId").val());
	var comId = $("#comId").val();
	var startYear = $("#startYear").val();
	var endYear = $("#endYear").val();
	$('#cusComFinanceReportOverview').datagrid({
		toolbar: ["-", {
            id: 'add_financialReport',
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
            	addNewFinancialReport();
            }
        }, "-", {
            id: 'remove_financialReport',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () { 
            	deleteAllSelectedRows();
            }
        }, "-"],
	 	width: '100%',
	 	fitColumns: true,
	 	height: '100%',            
        striped: true,  
        url: BASE_PATH+'customerController/searchCusComFinanceReportByAnnualFromJS.action?startYear='+startYear+"&endYear="+endYear+"&comId="+comId, 
        loadMsg: '数据加载中请稍后……',  
        pagination: true,  
        rownumbers: true,
        checkOnSelect: false,
        onClickRow:function(rowIndex,rowData){
        	//$("#cusComFinanceReportOverview").datagrid("clearSelections");
		},
	    columns:[[
	             {field:'ck',checkbox:true},
	             {field:'pid',hidden:true},
	             {field:'reportPeriod',title:'报表期间',width:100,align:'center'},
	  	         {field:'reportName',title:'期间表示名称',width:100,align:'center'},
	  			 {field:'description',title:'操作',width:120,align:'center', 
	  				formatter:function(value,rowData,rowIndex){ 
	  		            var btn = "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='editSingleSelectedRows("+
	  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
	  		            "<span style='color:blue; '>编辑</span></a>&nbsp;|&nbsp;"+
	  		            "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='deleteSingleSelectedRows("+
	  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
	  		            "<span style='color:blue; '>删除</span></a> ";  
	  		            return btn;  
	  				}  
	  			}
	    ]],
	    loadFilter: function(result) {
	    	var viewData = {'total':result.total,'rows':result.rows}
	    	return viewData;
	    }
	});
	var p = $('#reportOverview_grid').datagrid('getPager');  
    $(p).pagination({  
    	 width: '100%',
    	 height: '100%',
         pageSize: 10,//每页显示的记录条数，默认为10  
         pageList: [5,10,15,20],//可以设置每页记录条数的列表  
         beforePageText: '第',//页数文本框前显示的汉字  
         afterPageText: '页    共 {pages} 页',  
         displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	    }); 
};


function resetForm(){
	$('#reportOverview_form').form('reset');
}

function addNewFinancialReport(){
	var comId = $("#comId").val();
	var acctId = $("#acctId").val();
	window.location=BASE_PATH+'customerController/initFinancialInfo.action?comId='+comId+"&acctId="+acctId;
}

/**
 * 单行编辑window.location=BASE_PATH+'customerController/initFinancialInfo.action?comId='+comId+"&acctId="+acctId;
 */
function editSingleSelectedRows(rowData,rowIndex){
	var comId = $("#comId").val();
	var acctId = $("#acctId").val();
	var reportId = rowData.pid;
	var reportPeriod = rowData.reportPeriod;
	reportPeriod = reportPeriod.replace(/\年/g,"");
	reportPeriod = reportPeriod.replace("度","13");
	reportPeriod = reportPeriod.replace("月","");
	var isEditReport = 'yes';
	window.location=BASE_PATH+'customerController/initFinancialInfo.action?reportId='+reportId+'&isEditReport='+isEditReport+'&comId='+comId+"&acctId="+acctId+"&reportPeriod="+reportPeriod;
}

function editSingleSelectedRows1(rowData,rowIndex){
	 $.messager.confirm('确认','确认编辑?',function(e){  
        if(e){  
        	var pid = rowData.pid;
            $.ajax({  
                url:BASE_PATH+'customerController/editSingleSelectedFinancialReport.action?pid='+pid,    
                success:function(){}  
            });  
       	 var rows = $('#cusComFinanceReportOverview').datagrid("getRows");
            $('#cusComFinanceReportOverview').datagrid('deleteRow',rowIndex);
            for (var i = rowIndex ; i < rows.length; i++) {
           	 $('#cusComFinanceReportOverview').attr("datagrid-row-index", (i - 1));
    	    }
        }  
    })  
}

/**
 * 单行删除
 */
function deleteSingleSelectedRows(rowData,rowIndex){
	var startYear = $("#startYear").val();
	var endYear = $("#endYear").val();
	var acctId = $("#acctId").val();
	var comId = $("#comId").val();
	var isDelete = true;
	 $.messager.confirm('确认','确认删除?',function(e){  
        if(e){  
        	var reportId = rowData.pid;
            $.ajax({  
            	type: "POST",
                url:BASE_PATH+'customerController/deleteSingleSelectedFinancialReport.action?reportId='+reportId,  
                dataType: "text",
                success:function(data){
                	if(data=="succDelete"){
                		//彭注：下面一行代码正确，重新查询一次
                		//window.location=BASE_PATH+'customerController/gotoComFinancialStatusByAnnualPage.action?acctId='+acctId+'&comId='+comId+'&isDelete='+isDelete+'&startYear='+startYear+'&endYear='+endYear+'';
                		//$.messager.alert('操作','删除成功!','info');
                		$("#reportOverview_grid").datagrid('reload');
                		$("#cusComFinanceReportOverview").datagrid('reload');
         				$.messager.alert('操作', '删除信息成功!', 'info');
                	}else{
                		$.messager.alert('操作','删除失败!','error');
                	}
                }  
            });  
       	 var rows = $('#cusComFinanceReportOverview').datagrid("getRows");
            $('#cusComFinanceReportOverview').datagrid('deleteRow',rowIndex);
            for (var i = rowIndex ; i < rows.length; i++) {
           	 $('#cusComFinanceReportOverview').attr("datagrid-row-index", (i - 1));
    	    }
        }  
    })  
}

/**
 * 删除所有已选的报表
 */
function deleteAllSelectedRows(){
	$.messager.confirm('提示', '确定要删除?', function(r){
		if (r){
			deleteAllSelectedRows1();
		}
	});
}


function deleteAllSelectedRows1(){
	var rows = $('#cusComFinanceReportOverview').datagrid('getSelections'); 
	$.ajax({
		type: "POST",
		contentType: "application/json",
        url: BASE_PATH+"customerController/deleteAllSelectedFinancialReport.action",
        data:JSON.stringify(rows),
        dataType: "text",
        success: function(data){
 			if(data=="succDelete" || data==true){
 				$("#reportOverview_grid").datagrid('reload');
 				$("#cusComFinanceReportOverview").datagrid('reload');
 				$.messager.alert('操作提示', '删除信息成功!', 'info');
 				//loadReportOverviewList(); //彭注：该行代码成功，重新查询了一次
 			}else{
 				$.messager.alert('操作提示', '删除信息失败!', 'error');
 			}
        }
	});
}

$(document).ready(function() {
	var height = document.body.clientHeight;
	$('#cusComFinanceReportOverview').datagrid({ 
		height:(height)
	});
	loadReportOverviewList();
});

/*******************************************************************************************
 * 以上函数都已经确认
 *******************************************************************************************/



function taskClick(taskId){
	alert(taskId);
}


function searchReportOverviewList2(){
	$('#reportOverview_from').form('submit',{
        success:function(data){
           	$('#reportOverview_grid').datagrid('loadData', eval("{"+data+"}"));
       }
    });
}


function searchReportOverviewList1(){
	$('#reportOverview_from').form('submit',{
        success:function(data){
           	$('#cusComFinanceReportOverview').datagrid('loadData', eval("{"+data+"}"));
       }
    });
	
}




/****************************************************************************************/
/**
 * 
 * @param taskId
 */
function processLogging(data){
	loadProcessLoggingGrid(data);
	var height = document.body.clientHeight*0.9;
	var width = document.body.clientWidth*0.9;
	$('#reportOverview_win').window({
		width:width,
		height:height,
		title : "任务处理详情",
		modal:true
	});
}
/**
 * 加载任务管理-任务处理详情列表数据
 */
function loadProcessLoggingGrid(data){
	var taskName = data.taskName;
	var startYear = data.allocationDate;
	var title = "<span style='float: left;'>任务名称:"+
	taskName+"&nbsp;&nbsp;&nbsp;&nbsp;分配时间:"+startYear+"<span/>";
	$('#reportOverview_grid').datagrid({
	 	width: '100%',
	 	fitColumns: true,
	 	height: '100%',            
        striped: true,  
        title: title,
        url: 'seachEndTaskList.action', 
        loadMsg: '数据加载中请稍后……',  
        pagination: true,  
        rownumbers: true,
        //singleSelect:false,
        checkOnSelect: false,
        onClickRow:function(rowIndex,rowData){
        	$("#reportOverview_grid").datagrid("clearSelections");
		},
	    columns:[[
			{field:'ck',checkbox:true},
	        {field:'pid',hidden:true},
	        {field:'currentNote',title:'任务内容',width:100,align:'center'},
			{field:'executor',title:'执行人',width:100,align:'center'},
			{field:'allocationDate',title:'提交时间',width:100,align:'center'},
			{field:'taskType',title:'执行状态',width:100,align:'center',
				formatter:function(value){
					if(value){
						if("1" == value){
							return "处理中"
						}else if("2" == value){
							return "关闭中"
						}
					}
				}
			},
			{field:'opinion',title:'意见与说明',width:100,align:'center'}
	    ]],
	    loadFilter: function(result) {
	    	var data = result.rows;
	    	var viewData = {'total':result.count,'rows':data}
	    	return viewData;
	    }
	});
};
