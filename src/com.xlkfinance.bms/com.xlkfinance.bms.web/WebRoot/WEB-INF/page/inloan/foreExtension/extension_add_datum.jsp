<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
	<div class="datum1" style="padding:15px;width:1039px;">
		<div id="toolbar_data">
			<div style="padding-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addData()">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="delAnyData()">删除</a>
				<a href="javascript:void(0)" id="downAll" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="downLoadAllData()">一键下载</a>
			</div>
		</div>
		<div style="height:400px" class="datum">
			<table id="grid_data" title="资料上传列表" class="easyui-datagrid" style="height:100%;width:100%;"
				 data-options="
				    url: '${basePath}beforeLoanController/fileList.action?projectId='+newProjectId,
				    method: 'get',
				    collapsible:true,
				    rownumbers: true,
				    singleSelect: false,
				    pagination: true,
				    toolbar: '#toolbar_data',
				    idField: 'dataId',
				    selectOnCheck:true,
					checkOnSelect: true,
					onLoadSuccess:lookfor"> 
				<thead><tr>
				<th data-options="checkbox:true,field:'dataId',width:30"></th>
				<th hidden="true" data-options="field:'fileProperty'">
				<th data-options="field:'filePropertyName',width:283" >文件类型</th>
			    <th data-options="field:'fileType',width:120">文件种类</th>
			    <th hidden="true" data-options="field:'fileUrl'"></th>
			    <th data-options="field:'fileName',width:170">文件名称</th>
			    <th data-options="field:'fileSize',width:50,formatter:fileSizefiter">大小</th>
			    <th data-options="field:'uploadDttm',width:120">上传时间</th>
			    <th data-options="field:'fileDesc',width:120">上传说明</th>
			    <th data-options="field:'cz',width:180,formatter:caozuofiter">操作</th>
			     
				</tr></thead>
			</table>
		</div>
		<div id="dialog_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons">
			<form id="addOrupdate" name="addOrupdate" method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>文件类型：</td>
						<td>
							<span id="filePropertyName"></span>
							<input type="hidden" name="dataPid" id="dataPid">
							<input type="hidden" name="dataProjectId" id="dataProjectId">
							<input type="hidden" name="dataFileProperty" id="dataFileProperty">
						</td>
					</tr>
					<tr>
						<td width="120" align="right">上传说明：</td>
						<td><input  class="easyui-textbox" style="width:240px;height:100px" data-options="required:false,multiline:true" name="datafileDesc" id="datafileDesc"></td>
					</tr>
					<tr>
						<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
						<td>
							<input class="easyui-filebox" data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" id="file2" name="file2" style="width:240px;" required="true"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="data-buttons">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOrupdateData()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data').dialog('close')">取消</a>
		</div>
		
		
		<div id="dialog_data2"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons2">
			<form id="addOrupdate2" name="addOrupdate2" method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<td width="120" align="right" height="28"><font color="red">*</font>文件类型：</td>
						<td>
							<input class="easyui-combobox" name="dataFileProperty" id="dataFileProperty2" style="width:200px"
							data-options="url:'${basePath}beforeLoanController/searchDataType.action?projectId='+newProjectId,
										  method:'get',
										  valueField:'pid',		            
										  textField:'lookupDesc'" required="true">
							<input type="hidden" name="dataProjectId" id="dataProjectId2">
						</td>
					</tr>
					<tr>
						<td width="120" align="right">上传说明：</td>
						<td><input  class="easyui-textbox" style="width:240px;height:100px" data-options="required:false,multiline:true" name="datafileDesc" id="datafileDesc"></td>
					</tr>
					<tr>
						<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
						<td>
							<input class="easyui-filebox" data-options="buttonText:'选择文件',missingMessage:'选择一个文件'" id="file3" name="file2" style="width:240px;" required="true"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="data-buttons2">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOrupdateData2()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data2').dialog('close')">取消</a>
		</div>
		
		<input type="hidden" name="pro_Id" id="pro_Id">
	</div>
	<script type="text/javascript" src="${basePath}upload/javascript/upload.js" charset="utf-8"></script>
	<script type="text/javascript">
	var foreStatus=${foreStatus};
	$(function(){
		$('#pro_Id').val(newProjectId);
		
		$('.easyui-filebox').filebox({
			buttonText: '选择文件',
			missingMessage:'选择一个文件'
		})
		//流程控制
		
	});

	function caozuofiter(value,row,index){
		var fileName = row.fileName;
		if(fileName!='' && fileName!=null &&fileName!=undefined){
			fileName = fileName.replace(/\ +/g,"");
		}
		if(row.fileUrl=='' || row.fileUrl==null ||row.fileUrl==undefined){
			var downloadDom = "<a href='javascript:void(0)'><font color='gray'>下载</font> | </a>";
		}else{
			var downloadDom = "<a class='download' href='${basePath}beforeLoanController/downloadData.action?path="+row.fileUrl+"&fileName="+fileName+"'><font color='blue'>下载</font> | </a>";
		}
		
		var lookUpDom = "";
		if(row.fileUrl!='' && row.fileUrl!=null &&row.fileUrl!=undefined){
			var path = row.fileUrl;
			var fileType=path.substring(path.lastIndexOf(".")+1);
			fileType = fileType.toLowerCase();
			if (fileType=="bmp"||fileType=="jpeg"||fileType=="jpg"||fileType=="png"||fileType=="gif") {
				lookUpDom = "<a href='javascript:void(0)' onclick=lookUpFileByPath('"+row.fileUrl+"','"+ fileName+"') class='download'><font color='blue'>预览</font> | </a> ";
			}
		}
		
		if(row.dataId=='' || row.dataId==null ||row.dataId==undefined  || foreStatus>1){
			var dataDelDom = "<a href='javascript:void(0)' ><font color='gray'>删除</font></a>";
		}else{
			var dataDelDom = "<a href='javascript:void(0)' onclick='delData("+row.dataId+")'><font color='blue'>删除</font></a>";	
		}
		//初始节点可以编辑，其余节点不允许编辑
		if(foreStatus>1){
			var dataEditDom = "<a href='javascript:void(0)' class='download')'><font color='gray'>编辑</font></a> | ";
		}else{
			var dataEditDom = "<a href='javascript:void(0)' class='download' onclick='editData("+row.dataId+",\""+row.fileDesc+"\",\""+row.filePropertyName+"\","+row.fileProperty+",\""+fileName+"\")'><font color='blue'>编辑</font></a> | ";
		}
		return downloadDom+lookUpDom+dataEditDom+dataDelDom;
	}
	
	function fileSizefiter(value,row,index){
		var sNum='';
		if(value==0 || value == undefined|| value==null || value ==''){
		}else{
			sNum = getFileSize(value);
		}
		return sNum;
	}
	
	function addData(){
		$('#addOrupdate2').form('clear');
		$('#dataProjectId2').val($('#pro_Id').val());
		$('#addOrupdate2').attr('action',"${basePath}beforeLoanController/saveData.action");
		$('#dialog_data2').dialog('open').dialog('setTitle','上传资料');
	}
	
	function editData(dId,fDesc,fPropertyName,fProperty,fName){
		if(dId=='' || dId==undefined || dId ==null){
			if (confirm("该类文件不存在，是否进行上传?")) {
				$('#addOrupdate').form('reset');
				$('#filePropertyName').text(fPropertyName);
				$('#dataFileProperty').val(fProperty);
				$('#dataProjectId').val($('#pro_Id').val());
				$('#addOrupdate').attr('action',"${basePath}beforeLoanController/saveData.action");
				$('#dialog_data').dialog('open').dialog('setTitle','上传资料');
			}
			return false;
		}
		$('#addOrupdate').form('reset');
		$('#filePropertyName').text(fPropertyName);
		$('#dataPid').val(dId);
		if(fDesc=='' || fDesc==null || fDesc=='null' || fDesc == 'undefined'){
			$('#datafileDesc').textbox('setValue','');
		}else{			
			$('#datafileDesc').textbox('setValue',fDesc);
		}
		$('#dataFileProperty').val(fProperty);
		$('#dataProjectId').val($('#pro_Id').val());
		$('#addOrupdate').attr('action',"${basePath}beforeLoanController/editData.action");
		$('#dialog_data').dialog('open').dialog('setTitle','修改资料');
	}
	
	
	function addOrupdateData(){
		var file2 = $('#file2').textbox('getValue');
		var proId = $('#dataProjectId').val();
		var fileProperty = $('#dataFileProperty').val();
		if(proId=="" || null==proId){
			$.messager.alert("操作提示","请重新选择项目","error"); 
			return false;
		}
		if(fileProperty=="" || null==fileProperty){
			$.messager.alert("操作提示","文件类型出错","error"); 
			return false;
		}
		if(file2=="" || null==file2){
			$.messager.alert("操作提示","请选择文件资料","error"); 
			return false;
		}
		$('#addOrupdate').form('submit',{
			success:function(data){
				var datas = strToJson(data);
				$.messager.alert("操作提示",datas.uploadStatus,"success");
				$('#dialog_data').dialog("close");
				$('#grid_data').datagrid("reload");
				$('#grid_data').datagrid("clearChecked");
			}
		})
	}
	
	
	
	function addOrupdateData2(){
 		var file = $('#file3').textbox('getValue');
		var proId = $('#dataProjectId2').val();
		var fileProperty = $('#dataFileProperty2').combobox('getValue');
		if(proId=="" || null==proId){
			$.messager.alert("操作提示","请重新选择项目","error"); 
			return false;
		}
		if(fileProperty=="" || null==fileProperty){
			$.messager.alert("操作提示","文件类型出错","error"); 
			return false;
		}
		if(file=="" || null==file){
			$.messager.alert("操作提示","请选择文件资料","error"); 
			return false;
		}
		$('#addOrupdate2').form('submit',{
			success:function(data){
				var datas = strToJson(data);
				$.messager.alert("操作提示",datas.uploadStatus,"success");
				$('#dialog_data2').dialog("close");
				$('#grid_data').datagrid("reload");
				$('#grid_data').datagrid("clearChecked");
			}
		})
	}
	
	function delData(dId){
		if(dId=="" || null==dId){
			 $.messager.alert("操作提示","该类型文件不存在，无法删除！","error"); 
			 return false;
		}else{
			if (confirm("是否确认删除?")) {
				$.ajax({
					type: "POST",
			        url: "${basePath}beforeLoanController/delData.action",
			        data:{"pidArray":dId},
			        dataType: "json",
			        success: function(data){
		        		$.messager.alert("操作提示",data.delStatus,"success"); 
		        		$('#grid_data').datagrid("reload");
		        		$('#grid_data').datagrid("clearChecked");
			        }
				});
			}
		}
	}

	
	function strToJson(str){
		return JSON.parse(str);
	} 
	
	function getPidArray(row){
		var pidArray = "";
		for(var i=0; i<row.length; i++){
			if(i!=row.length-1){
				pidArray =pidArray+row[i].dataId+",";
			}else if(i==0){
				pidArray =pidArray+row[i].dataId;
			}else if(i==row.length-1){
				pidArray =pidArray+row[i].dataId;
			} 
		}
		return pidArray;
	}
	
	function delAnyData(){
		var row = $('#grid_data').datagrid('getChecked');
		if(row.length==0){
			$.messager.alert("操作提示","请选择文件类型","error"); 
			return false;
		}
		for(var i=0;i<row.length;i++){
			if(row[i].dataId==null || row[i].dataId ==''|| row[i].dataId==undefined){
				$.messager.alert("操作提示","请选中已上传的文件类型进行删除","error"); 
				return false;
			}
		}
		var pidArray = getPidArray(row);
		if (confirm("是否确认删除?")) {
			$.ajax({
				type: "POST",
		        url: "${basePath}beforeLoanController/delData.action",
		        data:{"pidArray":pidArray},
		        dataType: "json",
		        success: function(data){
	        		$.messager.alert("操作提示",data.delStatus,"success"); 
	        		$('#grid_data').datagrid("reload");
	        		$('#grid_data').datagrid("clearChecked");
		        }
			});
		}
	}
	
	//查看控制
	function lookfor(){
		
		// 详情链接点击进来,表单禁用
		if(biaoZhi == 3){
			fromDisable();
			return;
		}
		if(projectName){		
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body a:not(.download)').removeAttr('onclick');
			$('.panel-body a:not(.download) font').attr('color','gray');
	 	}

		if("task_LoanRequest"==workflowTaskDefKey){
		}else{
			fromDisable();
		}
		$("#downAll").linkbutton("enable");
		$("#downAll").bind('click', function(){
			downLoadAllData();
	    });
		
		//已审批前新增按钮都有效
		if(foreStatus<10){
			$("#addData").linkbutton("enable");
			$("#addData").bind('click', function(){
				addData();
		    });
		}
	}
	
	function fromDisable(){
		$('.datum .easyui-combobox').attr('disabled','disabled');
		$('.datum input[type="checkbox"]').attr('disabled','disabled');
		$('.datum .easyui-textbox').attr('disabled','disabled');
		$('.datum .easyui-linkbutton ,.datum a:not(.download)').removeAttr('onclick');
		$('.datum a:not(.download) font').attr('color','gray');
		$('.datum .easyui-linkbutton ,.datum  input,.datum  textarea').attr('disabled','disabled');
		$('.datum .easyui-linkbutton ,.datum  input,.datum  textarea').attr('readOnly','readOnly');
		$('.datum .easyui-linkbutton ,.datum  input,.datum  textarea').addClass('l-btn-disabled');
	}
	function downLoadAllData(){
		window.location.href=BASE_PATH+"beforeLoanController/downLoadZipFile.action?projectId="+projectId;
	}
	</script> 