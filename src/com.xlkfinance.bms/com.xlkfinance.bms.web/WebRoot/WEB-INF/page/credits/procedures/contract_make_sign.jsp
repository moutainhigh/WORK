<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
 
 	<%-- 合同 --%>
 <div class="extension_control">
 	<div  class="easyui-panel control_disappr" title="贷款合同（所有贷款合同相关的列表）" data-options="collapsible:true" style="padding:10px" style="width:auto">
		
		<!-- 操作按钮 -->
		<div id="toolbar_schtbh"  class="easyui-panel" style="border-bottom: 5px;padding:10px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" onclick="generatedContractNumber()">生成合同编号</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" onclick="publicRefresh('#grid_schtbh')">刷新</a>
		</div>
		
		<div class="contractListDiv" style="height:auto;width: auto;">
			<table id="grid_schtbh" class="easyui-datagrid" 
				style="height:auto;width: auto;"
				data-options="
					    url: '',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: false,
					    toolbar: '#toolbar_schtbh',
					    idField: 'pid',
					    fitColumns:true ">
					<!-- 表头 -->
					<thead><tr>
						<th data-options="field:'pid',checkbox:true" align="center" ></th>
						<th data-options="field:'templateName',width:150" align="center" halign="center" >合同模版名称</th>
						<th data-options="field:'contractName',width:200" align="center" halign="center" >合同名称</th>
						<th data-options="field:'contractNo',width:150" align="center" halign="center" >合同编号</th>
						<th data-options="field:'cz',formatter:contractMakeFilter" >操作</th>
					</tr></thead>
			</table>
		</div>
	</div>
</div>

<div id="access-buttons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAttachment()">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#accessDialog').dialog('close')">关闭</a>
</div>
<div id="accessDialog" class="easyui-dialog" style="width:880px;height:400px;top:100px"
        closed="true" buttons="#access-buttons">
    <div style="margin-left: 20px">
    <form action="${basePath}contractInfoController/addContractAttachment.action" method="post" id="attachment">
	    <input type="hidden" name="contractId" class="fileContractId">
	    <div style="margin-left: 50px">
	    	<table>
	    		<tr>
	    			<td style="height: 25px;">合同名称：</td>
	    			<td><input class="easyui-textbox" id="attContractName" style="width: 200px;" data-options="editable:false"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">合同编号：</td>
	    			<td><input class="easyui-textbox" id="attContractNumber" style="width: 200px" data-options="editable:false"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">合同类型：</td>
	    			<td><input class="easyui-combobox" id="attContractType" style="width: 200px" data-options="editable:false" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">附件名称：</td>
	    			<td><input class="easyui-textbox" name="attachmentFileName" style="width: 200px" required="true"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">线下资料位置：</td>
	    			<td><input class="easyui-textbox" name="attachmentLocation" style="width: 200px"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">线下份数：</td>
	    			<td><input class="easyui-numberbox" name="offlineCnt" style="width: 200px"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">线上份数：</td>
	    			<td><input class="easyui-numberbox" name="onlineCnt" style="width: 200px"/></td>
	    		</tr>
	    		<tr>
	    			<td style="height: 25px;">备注：</td>
	    			<td><input class="easyui-textbox" name="remark" style="width: 200px"/></td>
	    		</tr>
	    	</table>
	    </div>
    </form>
    <!-- 操作按钮 -->
	<div style="padding-bottom:5px" id="toolbar_Data">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addFileAccessArray()">新增附件</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delFileAccessArray()">删除</a>
	</div> 
	    
    <table id="accessGrid" class="easyui-datagrid" 
		style="height:200px;width: 800px;" data-options="toolbar:'#toolbar_Data'">
	</table>
	</div>
</div>	
	
<div id="fileDialog" class="easyui-dialog" style="width:500px;height:200px;" closed="true">
	<form action="${basePath}contractInfoController/addAccess.action" method="post" enctype="multipart/form-data" id="fileDialogForm">
	<input type="hidden" name="contractId" class="fileContractId">
	<table style="margin-left: 30px">
		<tr>
			<td style="height: 25px;">选择文件类型：</td>
			<td><input class="easyui-combobox" name="fileType" value="合同附件资料" readonly="readonly" style="width: 300px"/></td>
		</tr>
		<tr>
			<td style="height: 25px;">文件选择：</td>
			<td><input class="easyui-filebox" name="file2" data-options="buttonText:'选择文件',prompt:'选择一个文件...',onChange:setValueDo2" style="width: 300px" required="true"></td>
		</tr>
		<tr>
			<td style="height: 25px;">文件名称：</td>
			<td><input class="easyui-textbox" name="fileName" id="fileName" style="width: 300px" required="true"></td>
		</tr>
		<tr>
			<td style="height: 25px;">上传备注：</td>
			<td><input class="easyui-textbox" name="fileDesc" style="width: 300px"></td>
		</tr>
		<tr>
			<td style="height: 25px;" colspan="2">
				<input type="button" value="上传" onclick="saveFileDialog()" style="width: 80px;height: 25px;">
				<input type="button" value="取消" onclick="javascript:$('#fileDialog').dialog('close')" style="width: 80px;height: 25px;">
			</td>
		</tr>
	</table>
	</form>
</div>

<div id="editDescDialog" class="easyui-dialog" style="width:500px;height:150px;" closed="true">
	<form action="${basePath}contractInfoController/editAccessDesc.action" method="post" id="descDialogForm">
	<input type="hidden" name="pid" class="pid">
	<table style="margin-left: 30px">
		<tr>
			<td style="height: 25px;">选择文件类型：</td>
			<td><input class="easyui-combobox" name="fileType" value="合同附件资料" readonly="readonly" style="width: 300px"/></td>
		</tr>
		<tr>
			<td style="height: 25px;">上传备注：</td>
			<td><input class="easyui-textbox" name="fileDesc" id="fileDesc" style="width: 300px"></td>
		</tr>
		<tr>
			<td style="height: 25px;" colspan="2">
				<input type="button" value="保存" onclick="saveDescDialog()" style="width: 80px;height: 25px;">
				<input type="button" value="取消" onclick="javascript:$('#editDescDialog').dialog('close')" style="width: 80px;height: 25px;">
			</td>
		</tr>
	</table>
	</form>
</div>

<div id="editFileDialog" class="easyui-dialog" style="width:500px;height:150px;" closed="true">
	<form action="${basePath}contractInfoController/editAccess.action" method="post" enctype="multipart/form-data" id="editFileDialogForm">
	<input type="hidden" name="pid" class="pid">
	<table style="margin-left: 30px">
		<tr>
			<td style="height: 25px;">文件选择：</td>
			<td><input class="easyui-filebox" name="file2" data-options="buttonText:'选择文件',prompt:'选择一个文件...',onChange:setValueDo1" style="width: 300px"></td>
		</tr>
		<tr>
			<td style="height: 25px;">文件名称：</td>
			<td><input class="easyui-textbox" name="fileName" id="fileName1" style="width: 300px"></td>
		</tr>
		<tr>
			<td style="height: 25px;" colspan="2">
				<input type="button" value="上传" onclick="editFileDialog()" style="width: 80px;height: 25px;">
				<input type="button" value="取消" onclick="javascript:$('#editFileDialog').dialog('close')" style="width: 80px;height: 25px;">
			</td>
		</tr>
	</table>
	</form>
</div>
<script type="text/javascript">

// 初始化
$(document).ready(function(){
	$("#grid_schtbh").datagrid({url:"<%=basePath%>contractInfoController/getAllContractListByProjectId.action?projectId="+newProjectId });

	$('#grid_schtbh').datagrid({
		onLoadSuccess:function(){
			if("task_ContractSign"==workflowTaskDefKey) {
			}else{
				$('.extension_control .easyui-combobox').attr('disabled','disabled');
				$('.extension_control input[type="checkbox"]').attr('disabled','disabled');
				$('.extension_control .easyui-textbox').attr('disabled','disabled');
				//lv 
				 //不管 什么时候都可以点击下载跟预览 
				$('.extension_control .easyui-linkbutton ,.extension_control a:not(.download)').removeAttr('onclick');
				$('.extension_control a:not(.download) font').attr('color','gray');
				$('.extension_control .easyui-linkbutton ,.extension_control  input,.extension_control  textarea').attr('disabled','disabled');
				$('.extension_control .easyui-linkbutton ,.extension_control  input,.extension_control  textarea').attr('readOnly','readOnly');
				$('.extension_control .easyui-linkbutton ,.extension_control  input,.extension_control  textarea').addClass('l-btn-disabled');
			}
		}
	})
})

//打开办理页面	
function generatedContractNumber(){
	var rows = $('#grid_schtbh').datagrid('getSelections');
	// 所选取的合同编号
	var contractIds = "";
	// 判断选取的数量
 	if ( rows.length == 0 ) {
		$.messager.alert("操作提示","请选择数据","error");
		return;
	}else if( rows.length > 0 ){
		for(var i = 0 ; i < rows.length ; i++){
			// 不能对已生成合同编码的继续操作
			if(null != rows[i].contractNo && rows[i].contractNo != ""){
				$.messager.alert("操作提示","所选取的合同有些已生成合同编码!","error");
				return;
			}
			// 判断插入符的位置,拼接合同信息ID
			if( i == 0 ){
				contractIds = rows[i].pid ;
			}else {
				contractIds +="," + rows[i].pid ;
			}
		}
	}
	$.post("<%=basePath%>contractInfoController/genContractNumber.action",{contractIds : contractIds}, 
		function(ret) {
			//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
			if(ret && ret.header["success"] ){
				$.messager.alert('操作提示',ret.header["msg"]);	
				// 重新加载抵质押物列表信息
				$("#grid_schtbh").datagrid('load');
				// 清空所选择的行数据
				clearSelectRows("#grid_schtbh");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		},'json');
}

function showFileAccess(pid,cName,cTypeText,projectNuber){
	$('#accessDialog').dialog('open').dialog('setTitle', "合同附件查看编辑");
	$('#attachment').form('reset');
	$('.fileContractId').val(pid);
	$('#attContractName').textbox('setValue',cName);
	if(projectNuber!=null && projectNuber!="null"){
		$('#attContractNumber').textbox('setValue',projectNuber);
	}
	$('#attContractType').combobox('setValue',cTypeText);
	$.ajax({
		url:'${basePath}contractInfoController/getContractAttachment.action',
		type:'post',
		data:{pid:pid},
		dataType:'json',
		success:function(data){
			if(data.contractId!=0){
				$('#attachment').form('load',data);
			}
		}
	});
	$("#accessGrid").datagrid({
	    url: '${basePath}contractInfoController/pageAccesstList.action',
	    method: 'POST',
	    queryParams:{"pid":pid},
	    rownumbers: true,
	    pagination: true,
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck:true,
		checkOnSelect: false,
	    columns: [[
	        { field: 'pid',checkbox:true,width:50},
	        { field: 'fileType', title: '文件类型', width: 50},
	        { field: 'fileName', title: '文件名称', width: 50},
	        { field: 'fileSize', title: '文件大小', width: 50,formatter:sizeFileter},
	        { field: 'uploadDttm', title: '上传日期', width: 50,formatter:convertDate},
	        { field: 'fileDesc', title: '备注', width: 50},
	        { field: 'fileUrl',hidden:true, width: 1},
	        { field: 'do',title: '操作', width: 80,formatter:doFileter}
	    ]]
	});
}

function doFileter(value,row,index){
	var downloadDom = "<a href='${basePath}beforeLoanController/downloadData.action?path="+row.fileUrl+"&fileName="+row.fileName+"'><font color='blue'>下载</font></a> | ";
	var editDom = "<a href='javascript:void(0)' onclick='editFileAccess("+row.pid+",\""+row.fileDesc+"\",1)'><font color='blue'>编辑</font></a> | ";
	var reload = "<a href='javascript:void(0)' onclick='editFileAccess("+row.pid+",\""+row.fileDesc+"\",2)'><font color='blue'>重新上传</font></a> | ";
	var projectDom = "<a href='javascript:void(0)' onclick='delFileAccess("+row.pid+")'><font color='blue'> 删除</font></a>";
	return downloadDom+editDom+reload+projectDom;
}

function contractMakeFilter(value,row,index){
	var downloadDom = "<a  class='download' href='"+BASE_PATH+"contractInfoController/download.action?path="+row.contractUrl+"'><font color='blue'>下载</font></a> | ";
	var fjReadDom = "<a href='javascript:void(0)' onclick='showFileAccess("+row.pid+",\""+row.contractName+"\",\""+row.contractTypeText+"\",\""+row.contractNo+"\")'><font color='blue'>附件查看</font></a> | ";
	var contractReadDom = "<a class='download' href='javascript:void(0)' onclick='contractReview("+'"'+row.contractUrl+'"'+")'><font color='blue'>预览</font></a> ";
	return downloadDom+fjReadDom+contractReadDom;
}

function saveAttachment(){
	$('#attachment').form('submit',{
		success:function(data){
			if(data==0){
				$.messager.alert("系统提示","附件保存失败","error"); 
			}else{
				$.messager.alert("系统提示","附件保存成功","success"); 
				$('#accessDialog').dialog('close');
			}
		}
	})
}

function addFileAccessArray(){
	$('#fileDialogForm').form('reset');
	$('#fileDialog').dialog('open').dialog('setTitle', "新增合同附件资料");
}

function setValueDo2(newValue,oldValue){
	var value = newValue.replace(/.*(\/|\\)/, "");
	$('#fileDialog #fileName').textbox('setValue',value);
}

function setValueDo1(newValue,oldValue){
	var value = newValue.replace(/.*(\/|\\)/, "");
	$('#editFileDialog #fileName1').textbox('setValue',value);
}

function saveFileDialog(){
	$('#fileDialogForm').form('submit',{
		success:function(data){
			var datas = strToJson(data);
			$.messager.alert("操作提示",datas.uploadStatus,"success");
			$('#fileDialog').dialog("close");
			$('#accessGrid').datagrid("reload");
		}
	})
}	

function editFileAccess(pid,desc,aId){
	if(aId==1){
		$('#descDialogForm').form('reset');
		$('.pid').val(pid);
		$('#fileDesc').textbox('setValue',desc);
		$('#editDescDialog').dialog('open').dialog('setTitle','编辑');
	}
	if(aId==2){
		$('#editFileDialogForm').form('reset');
		$('.pid').val(pid);
		$('#editFileDialog').dialog('open').dialog('setTitle','重新上传');
	}
	
}

function saveDescDialog(){
	$('#descDialogForm').form('submit',{
		success:function(data){
			var datas = strToJson(data);
			$.messager.alert("操作提示",datas.uploadStatus,"success");
			$('#editDescDialog').dialog("close");
			$('#accessGrid').datagrid("reload");
		}
	})	
}

function editFileDialog(){
	$('#editFileDialogForm').form('submit',{
		success:function(data){
			var datas = strToJson(data);
			$.messager.alert("操作提示",datas.uploadStatus,"success");
			$('#editFileDialog').dialog("close");
			$('#accessGrid').datagrid("reload");
		}
	})
}


function strToJson(str){
	return JSON.parse(str);
} 
function sizeFileter(value,row,index)
{
	var fileSize = value;
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

//批量删除文件
function delFileAccessArray(){
	var pidArrays = $("#accessGrid").datagrid("getSelections");
	if(pidArrays.length<1){
		$.messager.alert("系统提示","请选择要删除的附件","error"); 
		return;
	}
	var pidArray = new Array();
	for (var i = 0; i < pidArrays.length; i++) {
		pidArray.push(pidArrays[i].pid);
	}
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/deleteAccess.action",
	        data:{"pidArray":pidArray},
	        dataType: "text",
	        success: function(data){
	        	$('#accessGrid').datagrid('reload');
	        	$.messager.alert("系统提示","删除附件成功","success"); 
	        	
	        }
		});
	}
}

//删除文件
function delFileAccess(pid){
	var pidArray = new Array();
	pidArray.push(pid);
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "${basePath}contractInfoController/deleteAccess.action",
	        data:{"pidArray":pidArray},
	        dataType: "text",
	        success: function(data){
	        	$('#accessGrid').datagrid('reload');
	        	$.messager.alert("系统提示","删除附件成功","success");
	        }
		});
	}
}
</script>