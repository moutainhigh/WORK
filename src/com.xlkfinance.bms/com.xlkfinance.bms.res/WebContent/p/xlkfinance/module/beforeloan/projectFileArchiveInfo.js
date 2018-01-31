//var projectFileArchivePid = "-1";
/**
 * 加载列表数据
 */
function projectArchiveFileInfo(){
	//var projectId = $("#projectFileArchivePid").val();
	/**
	var url = '';
	if(projectId>=0){
		url = BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFile.action?projectFileArchivePid='+projectId;
	}
	**/
	
	var archiveId_textbox = $("#fileArchiveId").val();
	
	$('#allArchiveFileInfoTable').datagrid({
		toolbar: ["-",{
            id: 'addArchiveFile',
            text: '新增归档资料',
            iconCls: 'icon-add',
            handler: function () { 
            	addArchiveFile();
            }
        },"-",{
            id: 'removeArchiveFile',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () { 
            	deleteALLProjectArchiveFile();
            }
        },"-",{
            id: 'refreshArchiveFile',
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function () { 
            	refreshFileAndRemark();
            }
        }, "-"],
	 	width: '800',
	 	fitColumns: true,
	 	height: '300',            
        striped: true,  
        url:BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFile.action?archiveId_textbox='+archiveId_textbox,
        //url: BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFileAndRemark.action?projectFileArchivePid='+projectFileArchivePid, 
        loadMsg: '数据加载中请稍后……',  
        pagination: false,  
        rownumbers: true,
        checkOnSelect: false,
        onClickRow:function(rowIndex,rowData){
        	$("#allArchiveFileInfoTable").datagrid("clearSelections");
		},
	    columns:[[
	             {field:'ck',checkbox:true},
	             {field:'bizFilePid',hidden:true},
	             {field:'fileUrl',hidden:true},
	             {field:'fileBusType',hidden:true},
	             {field:'fileType',title:'文件种类',width:100,align:'center'},
	             {field:'fileName',title:'文件名称',width:100,align:'center'},
	  	         {field:'fileSize',title:'大小',width:100,align:'center',formatter:getFileSize},
	  	         {field:'uploadDttm',title:'上传时间',width:100,align:'center'},
	  	         {field:'fileRemark',title:'备注',width:100,align:'center'},
	  			 {field:'description',title:'操作',width:120,align:'center', 
	  				formatter:function(value,rowData,rowIndex){ 
	  		            var btn = "<a id='"+rowData.id+"' class='easyui-linkbutton download' onclick='downloadFileInfo("+
	  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
	  		            "<span style='color:blue; '>下载</span></a>&nbsp;|&nbsp;"+
	  		            
	  		          "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='editProjectArchiveFileInfo("+
	  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
	  		            "<span style='color:blue; '>编辑</span></a>&nbsp;|&nbsp;"+
	  		            
	  		            "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='deleteSingleSelectedRowsInfo("+
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
	    },
	    onLoadSuccess:function(){
	    	if(projectName || biaoZhi==3){
	    		$('#allArchiveFileInfo .easyui-combobox').attr('disabled','disabled');
				$('#allArchiveFileInfo input[type="checkbox"]').attr('disabled','disabled');
				$('#allArchiveFileInfo .easyui-textbox').attr('disabled','disabled');
				$('#allArchiveFileInfo a:not(.download) font').css('color','gray');
				$('#allArchiveFileInfo a:not(.download) span').css('color','gray');
				$('#allArchiveFileInfo .easyui-linkbutton:not(.download) ,#allArchiveFileInfo a:not(.download)').removeAttr('onclick');
				$('#allArchiveFileInfo .easyui-linkbutton:not(.download) ,#allArchiveFileInfo input,#allArchiveFileInfo textarea,#allArchiveFileInfo a:not(.download)').attr('disabled','disabled');
				$('#allArchiveFileInfo .easyui-linkbutton:not(.download) ,#allArchiveFileInfo input,#allArchiveFileInfo textarea,#allArchiveFileInfo a:not(.download)').attr('readOnly','readOnly');
				$('#allArchiveFileInfo .easyui-linkbutton:not(.download) ,#allArchiveFileInfo input,#allArchiveFileInfo textarea,#allArchiveFileInfo a:not(.download)').addClass('l-btn-disabled');
	    	}
	    }
	});
	var p = $('#allArchiveFileInfoTable').datagrid('getPager');  
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

/**
function refreshFileAndRemark(){
	alert(projectId);
	$.ajax({
			type: "POST",
	        url:BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFileAndRemark.action?projectFileArchivePid='+projectId, 
	        data:{projectFileArchivePid:projectFileArchivePid},
	        dataType: "text",
	        success: function(fileLoadData){
	        	fileLoadDataJSON = $.parseJSON(fileLoadData);
	        	$('#opFileTable').datagrid('loadData', fileLoadDataJSON);
	        }
		});
}
**/

function addArchiveFile(){
	isArchiveFileEdit = false;
	clearForm2();
	$('#archiveFileInfoWindow').window('open');
}

function clearForm2(){
	$('#hasProjectArchiveFile').val('');
	$('#projectArchiveFileName').textbox('setValue','');
	$('#fileRemark').textbox('setValue','');
	$('#projectArchiveFile').filebox('setValue','');
}



function refreshFileAndRemark(){
	$("#allArchiveFileInfoTable").datagrid("reload");
}

/**
 * 下载文件
 */
function downloadFileInfo(rowData,rowIndex){
	var fileUrl = rowData.fileUrl;
	var fileName = rowData.fileName;
	window.location.href=BASE_PATH+'beforeLoanController/downloadData.action?path='+fileUrl+'&fileName='+fileName;
}

/**
 * 文件大小
 */
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
 * 编辑初始化
 */
function editProjectArchiveFileInfo(rowData,rowIndex){
	
	
	var bizFilePid = rowData.bizFilePid;
	$("#archiveFileId").val(bizFilePid);
	
	isArchiveFileEdit = true;
	
	$.ajax({
		type: "POST",
        url: BASE_PATH+'beforeLoanController/initBizProjectArchiveFile.action',
        data:{bizFilePid:bizFilePid},
        success: function(data){
        	var dataJSON = $.parseJSON(data);
        	var editInitSucc = dataJSON.editInitSucc;
        	var projectArchiveFileDTO = dataJSON.projectArchiveFileDTO;
        	
        	if(editInitSucc=='editInitSucc'){
 				$('#archiveFileInfoForm').form('load', projectArchiveFileDTO);
 				$('#archiveFileInfoWindow').window('open');
        	}else{
        		$.messager.alert('初始化','获取项目资料归档文件附件失败!','error');
        	}
        }
	});
	
}

/**
 * 单行删除
 */
function deleteSingleSelectedRowsInfo(rowData,rowIndex){
	 $.messager.confirm('确认','确认删除?',function(e){  
        if(e){  
        	var pid = rowData.bizFilePid;
        	var fileUrl = rowData.fileUrl;
        	var basePath = BASE_PATH;
            $.ajax({  
            	type: "POST",
                url:BASE_PATH+'beforeLoanController/deleteSingleProjectArchiveFile.action',
                data:{
                	pid:pid,
                	fileUrl:fileUrl,
                	basePath:basePath             
                },
                dataType: "text",
                success:function(data){
                	if(data=="delSucc"){
                		//window.location=BASE_PATH+'customerController/gotoComFinancialStatusByAnnualPage.action?acctId='+acctId+'&comId='+comId+'&isDelete='+isDelete+'&startYear='+startYear+'&endYear='+endYear+'';
                		$("#allArchiveFileInfoTable").datagrid("reload");
                		$.messager.alert('操作','删除成功!','info');
                	}else{
                		$.messager.alert('操作','删除失败!','error');
                	}
                }  
            });  
       	 var rows = $('#allArchiveFileInfoTable').datagrid("getRows");
            $('#allArchiveFileInfoTable').datagrid('deleteRow',rowIndex);
            for (var i = rowIndex ; i < rows.length; i++) {
           	 $('#allArchiveFileInfoTable').attr("datagrid-row-index", (i - 1));
    	    }
        }  
    })  
}

/**
 * 删除所有已选
 */
function deleteALLProjectArchiveFile(){
	$.messager.confirm('确认','确认删除?',function(e){  
		 if(e){
			var rows = $('#allArchiveFileInfoTable').datagrid('getSelections'); 
			$.ajax({
				type: "POST",
				contentType: "application/json",
		        url: BASE_PATH+'beforeLoanController/deleteALLProjectArchiveFile.action',
		        data:JSON.stringify(rows),
		        dataType: "text",
		        success: function(data){
		 			if(data=="delSucc" || data==true){
		 				$("#allArchiveFileInfoTable").datagrid("reload");
		 				$.messager.alert('操作提示', '删除信息成功!', 'success');
		 			}else{
		 				$.messager.alert('操作提示', '删除信息失败!', 'error');
		 			}
		        }
			});
		 }
	})
}

$(document).ready(function() {
	var height = document.body.clientHeight;
	$('#allArchiveFileInfoTable').datagrid({ 
		height:(height)
	});
	
	projectArchiveFileInfo();
});
