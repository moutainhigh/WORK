/**
 * 加载列表数据
 */
function loadTable(){
	//var projectId = $("#projectId").val();
	var url = '';
	if(nProjectId>=0){
		url = BASE_PATH+'beforeLoanController/obtainLoanApprovalPaperFileInfo.action?projectId='+nProjectId;
	}
	$('#loadTable').datagrid({
		toolbar: ["-", {
            id: 'generateLoadPaper',
            text: '生成放款审批单',
            iconCls: 'icon-add',
            handler: function () {
            	generatePaper(nProjectId);
            }
        },"-", {
            id: 'removeLoad_data',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () { 
            	deleteAllSelectedRowsLoad();
            }
        }, "-",{
            id: 'refresh_data',
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function () { 
            	refreshUploadLoanApprovalPaperFileForm();
            }
        }, "-"],
	 	width: '100%',
	 	fitColumns: true,
	 	height: 300,            
        striped: true,  
        url:url,
        method: 'post',
        //url: BASE_PATH+'beforeLoanController/obtainLoanApprovalPaperFileInfo.action?projectId='+projectId, 
        loadMsg: '数据加载中请稍后……',  
        pagination: true,  
        rownumbers: true,
        checkOnSelect: false,
        onClickRow:function(rowIndex,rowData){
        	$("#loadTable").datagrid("clearSelections");
		},
	    columns:[[
	             {field:'ck',checkbox:true},
	             {field:'pid',hidden:true},
	             {field:'fileUrl',hidden:true},
	             {field:'fileName',title:'文件名称',width:100,align:'center'},
	  	         {field:'fileSize',title:'大小',width:100,align:'center',formatter:getFileSize},
	  	         {field:'fileType',title:'类型',width:100,align:'center'},
	  	         {field:'uploadDttm',title:'上传时间',width:100,align:'center'},
	  			 {field:'description',title:'操作',width:120,align:'center', 
	  				formatter:function(value,rowData,rowIndex){ 
	  		            var btn = "<a class='download' id='"+rowData.pid+"' class='easyui-linkbutton' href='"+BASE_PATH+"beforeLoanController/downloadData.action?path="+rowData.fileUrl+"&fileName="+rowData.fileName+"'>" +
	  		            "<span style='color:blue; '>下载</span></a>&nbsp;|&nbsp;"+
	  		            
	  		            "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='deleteSingleSelectedRowsLoad("+
	  		          rowData.pid+",\""+rowData.fileUrl+"\")' href='#'>" +
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
	/*var p = $('#loadTable').datagrid('getPager');  
    $(p).pagination({  
    	 width: '100%',
    	 height: '100%',
         pageSize: 10,//每页显示的记录条数，默认为10  
         pageList: [5,10,15,20],//可以设置每页记录条数的列表  
         beforePageText: '第',//页数文本框前显示的汉字  
         afterPageText: '页    共 {pages} 页',  
         displayMsg: '当前显示 {from} - {to} 条记录   共 {total} 条记录'
	    }); */
};


function refreshUploadLoanApprovalPaperFileForm(){
	$("#loadTable").datagrid("reload");
}

function getFileSize(fileSize)
{
	var num = new Number();
	var unit = '';
	
	if (fileSize > 1*1024*1024*1024){
		num = fileSize/1024/1024/1024;
		unit = "G"
	}
	else if (fileSize > 1*1024*1024){
		num = fileSize/1024/1024;
		unit = "M"			
	}
	else if (fileSize > 1*1024){
		num = fileSize/1024;
		unit = "K"
	}
	else{
		return fileSize;
	}
	
	return num.toFixed(2) + unit;
	
}

/**
 * 单行删除
 */
function deleteSingleSelectedRowsLoad(pid,fileUrl){
	 $.messager.confirm('确认','确认删除?',function(e){  
        if(e){  
        	var basePath = BASE_PATH;
            $.ajax({  
            	type: "POST",
                url:BASE_PATH+'beforeLoanController/deleteSingleLoanApprovalPaperFile.action',
                data:{
                	pid:pid,
                	fileUrl:fileUrl,
                	basePath:basePath             
                },
                dataType: "text",
                success:function(data){
                	if(data=="delSucc"){
                		//window.location=BASE_PATH+'customerController/gotoComFinancialStatusByAnnualPage.action?acctId='+acctId+'&comId='+comId+'&isDelete='+isDelete+'&startYear='+startYear+'&endYear='+endYear+'';
                		$.messager.alert('操作','删除成功!','info');
                		$("#loadTable").datagrid("reload");
                	}else{
                		$.messager.alert('操作','删除失败!','error');
                	}
                }  
            });  
       	 var rows = $('#loadTable').datagrid("getRows");
            $('#loadTable').datagrid('deleteRow',rowIndex);
            for (var i = rowIndex ; i < rows.length; i++) {
           	 $('#loadTable').attr("datagrid-row-index", (i - 1));
    	    }
        }  
    })  
}

/**
 * 删除所有已选
 */
function deleteAllSelectedRowsLoad(){
	var rows = $('#loadTable').datagrid('getSelections'); 
	if(rows.length!=0){
		if(confirm('是否确认删除?')){
			$.ajax({
				type: "POST",
				contentType: "application/json",
		        url: BASE_PATH+'beforeLoanController/deleteAllSelectedLoanApprovalPaperFile.action',
		        data:JSON.stringify(rows),
		        dataType: "text",
		        success: function(data){
		 			if(data=="delSucc" || data==true){
		 				$.messager.alert('操作提示', '删除信息成功!', 'success');
		 				$("#loadTable").datagrid("reload");
		 			}else{
		 				$.messager.alert('操作提示', '删除信息失败!', 'error');
		 			}
		        }
			});
		}
	}else{
		$.messager.alert('操作提示', '请选择文件!', 'error');
	}
}



function generatePaper(projectId){

	$.ajax({
		url:BASE_PATH+'templateFileController/checkFileUrl.action',
		data:{templateName:'FKTZ'},
		dataType:'json',
		success:function(data){
			if(data==1){				
				window.location.href=BASE_PATH+'beforeLoanController/makeLoanApprovalPaper.action?projectId='+projectId;
			}else{
				alert('放款审批单模板不存在，请上传模板后重试');
			}
		}
	})
}
