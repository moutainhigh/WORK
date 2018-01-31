/**
 * 加载列表数据
 */
function projectFileArchive(){
	var url = '';
	if(nProjectId>=0){
		url = BASE_PATH+'beforeLoanController/obtainBizProjectArchiveByProjectId.action?projectId='+nProjectId;
	}
	//var projectId = $("#projectId").val();
	$('#projectFileArchive').datagrid({
		toolbar: ["-", {
            id: 'addProjectFileArchive',
            text: '新增',
            iconCls: 'icon-add',
            handler: function () {
            	addProjectFileArchive();
            }
        },"-", {
            id: 'remove_projectFileArchive_data',
            text: '删除',
            iconCls: 'icon-remove',
            handler: function () { 
            	//deleteAllSelectedRows();
            	deleteAllSelectedProjectFileArchive();
            }
        }, "-",{
            id: 'refresh_data',
            text: '刷新',
            iconCls: 'icon-reload',
            handler: function () { 
            	refreshProjectFileArchive();
            }
        }, "-"],
	 	width: '100%',
	 	fitColumns: true,
	 	height: 300,            
        striped: true,  
        url:url,
       // url:BASE_PATH+'beforeLoanController/obtainBizProjectFileArchiveAndTime.action?projectId='+projectId, 
        loadMsg: '数据加载中请稍后……',  
        pagination: false,  
        rownumbers: true,
        checkOnSelect: false,
        onClickRow:function(rowIndex,rowData){
        	$("#projectFileArchive").datagrid("clearSelections");
		},
	    columns:[[
	             {field:'ck',checkbox:true},
	             {field:'pid',hidden:true},//BIZ_PROJECT_ARCHIVE表的PID
	             {field:'bizFilePid',hidden:true},
	             {field:'archiveCatelogName',hidden:true},
	             {field:'archiveCatelogValue',title:'归档类别',width:100,align:'center'},
	  	         {field:'archiveFileName',title:'归档材料名称',width:100,align:'center'},
	  	         {field:'offlineCnt',title:'线下份数',width:100,align:'center'},
	  	         {field:'onlineCnt',title:'线上份数',width:100,align:'center'},
	  	         {field:'isArchiveValue',title:'是否归档',width:100,align:'center'},
	  	         {field:'createDttm',title:'归档时间',width:100,align:'center'},
	  	         {field:'remark',title:'备注',width:100,align:'center'},
	  			 {field:'description',title:'操作',width:120,align:'center', 
	  				formatter:function(value,rowData,rowIndex){ 
	  					
	  		            var btn = "<a id='"+rowData.id+"' class='easyui-linkbutton' onclick='editProjectFileArchive("+
	  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
	  		            "<span style='color:blue; '>编辑</span></a>&nbsp;|&nbsp;"+
	  		            
	  		            "<a id='"+rowData.id+"' class='easyui-linkbutton download' onclick='lookupArchiveFile("+
	  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
	  		            "<span style='color:blue; '>查看附件</span></a> ";  
	  		            
	  		            if(rowData.archiveCatelogName!=undefined && rowData.archiveCatelogName!=Iam){
	  		            	var btn = "<a class='easyui-linkbutton' href='#'><span style='color:gray;'>编辑</span></a>&nbsp;|&nbsp;" +
	  		            	
	  		            	"<a id='"+rowData.id+"' class='easyui-linkbutton download' onclick='lookupArchiveFile("+
		  		            JSON.stringify(rowData)+","+rowIndex+")' href='#'>" +
		  		            "<span style='color:blue; '>查看附件</span></a> ";   
	  		            }
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
	    	if(typeof(proStatus) != "undefined"){
	    		if(proStatus == '1'){
	    		    $('#projectFileArchiveDiv .easyui-combobox').attr('disabled','disabled');
	    			$('#projectFileArchiveDiv input[type="checkbox"]').attr('disabled','disabled');
	    			$('#projectFileArchiveDiv .easyui-textbox').attr('disabled','disabled');
	    			$('#projectFileArchiveDiv a:not(.download) font').css('color','gray');
	    			$('#projectFileArchiveDiv a:not(.download) span').css('color','gray');
	    			$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv a:not(.download)').removeAttr('onclick');
	    			$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv a:not(.download)').attr('disabled','disabled');
	    			$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv a:not(.download)').attr('readOnly','readOnly');
	    			$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv a:not(.download)').addClass('l-btn-disabled');
	    		}
	    	}
	    	if(projectName || biaoZhi==3){
	    		$('#projectFileArchiveDiv .easyui-combobox').attr('disabled','disabled');
				$('#projectFileArchiveDiv input[type="checkbox"]').attr('disabled','disabled');
				$('#projectFileArchiveDiv .easyui-textbox').attr('disabled','disabled');
				$('#projectFileArchiveDiv a:not(.download) font').css('color','gray');
				$('#projectFileArchiveDiv a:not(.download) span').css('color','gray');
				$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv a:not(.download)').removeAttr('onclick');
				$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv input,#projectFileArchive textarea,#projectFileArchiveDiv a:not(.download)').attr('disabled','disabled');
				$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv input,#projectFileArchive textarea,#projectFileArchiveDiv a:not(.download)').attr('readOnly','readOnly');
				$('#projectFileArchiveDiv .easyui-linkbutton:not(.download) ,#projectFileArchiveDiv input,#projectFileArchive textarea,#projectFileArchiveDiv a:not(.download)').addClass('l-btn-disabled');
	    	}
	    }
	});
	var p = $('#projectFileArchive').datagrid('getPager');  
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

//新增项目资料归档
function addProjectFileArchive(){
	isArchiveEdit = false;
	$("#buttonDIV").show();
	$("#allArchiveFileInfo").hide();
	$("#lastStepClose").hide();
	clearForm();
	//设置归档类型——
	$('#archiveCatelog').combobox('reload');
	$('#projectFileArchiveInfoWindow').window('open');
}



/**
 * 表单清空
 */
function clearForm(){
	$('#archiveFileName').textbox('setValue','');
	$('#archiveLocation').textbox('setValue','');
	$('#offlineCnt').numberbox('setValue','');
	$('#onlineCnt').numberbox('setValue','');
	$('#remark').textbox('setValue','');
}




function refreshProjectFileArchive(){
	$('#projectFileArchive').datagrid("reload");
}

/**
 * 下载文件
 */
function downloadFileProjectFileArchive(rowData,rowIndex){
	var fileUrl = rowData.fileUrl;
	var fileName = rowData.fileName;
	window.location=BASE_PATH+fileUrl;
}

/**
 * 编辑初始化
 */
function editProjectFileArchive(rowData,rowIndex){
	
	var pid = rowData.pid;
	archiveId = pid;
	$("#projectArchiveId").val(pid);
	$("#fileArchiveId").val(pid);
	isArchiveEdit = true;
	
	$.ajax({
		type: "POST",
        url: BASE_PATH+'beforeLoanController/initBizProjectArchive.action',
        data:{pid:pid},
       // dataType: "text",
        success: function(data){
        	
        	
        	var dataJSON = $.parseJSON(data);

        	var editInitSucc = dataJSON.editInitSucc;
        	var bizProjectArchive = dataJSON.bizProjectArchive;
        	if(editInitSucc=='editInitSucc'){
        		
 				$('#projectFileArchiveInfoForm').form('load', bizProjectArchive);
 				
 				
 				//初始化文件表的内容
 				$('#allArchiveFileInfoTable').datagrid("reload",BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFile.action?archiveId_textbox='+pid);
 				$("#buttonDIV").show();
 				$("#allArchiveFileInfo").hide();
 				$("#lastStepClose").hide();
 				$('#projectFileArchiveInfoWindow').window('open');
        	}else{
        		$.messager.alert('初始化','获取项目资料归档失败!','error');
        	}
        }
	});
	
}

//查看附件
function lookupArchiveFile(rowData,rowIndex){
var pid = rowData.pid;
	
	archiveId = pid;
	$("#projectArchiveId").val(pid);
	$("#fileArchiveId").val(pid);
	
	isArchiveEdit = true;
	

	
	$.ajax({
		type: "POST",
        url: BASE_PATH+'beforeLoanController/initBizProjectArchive.action',
        data:{pid:pid},
       // dataType: "text",
        success: function(data){
  
        	
        	var dataJSON = $.parseJSON(data);

        	var editInitSucc = dataJSON.editInitSucc;
        	var bizProjectArchive = dataJSON.bizProjectArchive;
        	
        	if(editInitSucc=='editInitSucc'){
        		
 				$('#projectFileArchiveInfoForm').form('load', bizProjectArchive);
 				$('#allArchiveFileInfoTable').datagrid("reload",BASE_PATH+'beforeLoanController/obtainBizProjectArchiveFile.action?archiveId_textbox='+pid);
 				$("#buttonDIV").hide();
 				$("#allArchiveFileInfo").show();
 				$("#lastStepClose").show();
 				$('#projectFileArchiveInfoWindow').window('open');
        	}else{
        		$.messager.alert('初始化','获取项目资料归档失败!','error');
        	}
        	
        }
	});
}

/**
 * 删除所有已选
 */
function deleteAllSelectedProjectFileArchive(){
	var rows = $('#projectFileArchive').datagrid('getSelections'); 
	if(rows.length==0){
		$.messager.alert('操作提示', '请选择归档资料', 'error');
		return false;
	}
	for(var i=0;i<rows.length;i++){
		if(rows[i].archiveCatelogName!=undefined && rows[i].archiveCatelogName!=Iam){
			$.messager.alert('操作提示', rows[i].archiveCatelogValue+'资料不能删除', 'error');
			return false;
		}
	}
	$.messager.confirm('确认','确认删除?',function(e){  
        if(e){  
			$.ajax({
				type: "POST",
				contentType: "application/json",
		        url: BASE_PATH+'beforeLoanController/deleteAllSelectedProjectArchive.action',
		        data:JSON.stringify(rows),
		        dataType: "text",
		        success: function(data){
		 			if(data=="delSucc" || data==true){
		 				$.messager.alert('操作提示', '删除信息成功!', 'success');
		 				$("#projectFileArchive").datagrid("reload");
		 			}else{
		 				$.messager.alert('操作提示', '删除信息失败!', 'error');
		 			}
		        }
			});
        }
	})
}


