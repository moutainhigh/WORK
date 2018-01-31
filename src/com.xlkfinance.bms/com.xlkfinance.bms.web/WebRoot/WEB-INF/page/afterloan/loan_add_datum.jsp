<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
	<div class="badDebt">
		<div id="toolbar_data">
			<div style="padding-bottom:5px">
				   	<a href="javascript:void(0)" id="addBtn" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addData('${vio.pid}')">新增</a>
			</div>
		</div>
		<div style="height:400px">
			<table id="grid_data" title="资料上传列表"  class="easyui-datagrid" style="height:100%;"
				 data-options="
				    url: '${basePath}afterLoanController/getFileInfoList.action?refId=${refId}&fileType=${fileType}',
				    method: 'get',
				    rownumbers: true,
				    collapsible:true,
				    singleSelect: false,
				    pagination: true,
				    toolbar: '#toolbar_data',
				    idField: 'dataId',
				    selectOnCheck:true,
					checkOnSelect: true,
					onLoadSuccess:lookfor
					"> 
				<thead><tr>
				<th data-options="checkbox:true,field:'dataId',width:30"></th>
				<th hidden="true" data-options="field:'refId'">
				<th hidden="true" data-options="field:'fileProperty'">
				<th hidden="true" data-options="field:'fileId'">
				<th data-options="field:'filePropertyName',width:300" >文件类型</th>
			    <th data-options="field:'fileType',width:120">文件种类</th>
			    <th hidden="true" data-options="field:'fileUrl'"></th>
			    <th data-options="field:'fileName',width:170">文件名称</th>
			    <th data-options="field:'fileSize',width:50,formatter:fileSizefiter">大小</th>
			    <th data-options="field:'uploadDttm',width:120">上传时间</th>
			    <th data-options="field:'fileDesc',width:120">上传说明</th>
			    <th data-options="field:'cz',width:190,formatter:caozuofiter">操作</th>
			     
				</tr></thead>
			</table>
		</div>
		
	    <!-- <div id="dialog_data"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons">
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
						<td width="120" align="right">上传说明1：</td>
						<td><input  class="easyui-textbox" style="width:240px;height:100px" data-options="required:false,multiline:true" name="datafileDesc" id="datafileDesc"></td>
					</tr>
					<tr>
						<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
						<td>
							<input class="easyui-filebox" data-options="buttonText:'选择文件'" id="file2" name="file2" style="width:240px;" required="true"/>
						</td>
					</tr>
				</table>
			</form>
		 
		</div>
		<div id="data-buttons">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOrupdateData()" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data').dialog('close')">取消</a>
		</div>  -->
		
		
		<div id="dialog_data2"></div>
	</div>	
	<script type="text/javascript" src="${basePath}upload/javascript/upload.js" charset="utf-8"></script>
	<script type="text/javascript">
	
	function caozuofiter(value,row,index){
		if(row.fileUrl=='' || row.fileUrl==null ||row.fileUrl==undefined){
			var downloadDom = "<a href='javascript:void(0)'><font color='gray'>下载</font></a>";
		}else{
			var downloadDom = "<a href='${basePath}afterLoanController/downloadData.action?fileName="+encodeURI(row.fileName)+"&path="+row.fileUrl+"' class='download'><font color='blue'>下载</font></a>";
		}
		if(row.dataId=='' || row.dataId==null ||row.dataId==undefined){
			var dataDelDom = " | <a href='javascript:void(0)' ><font color='gray'>删除</font></a>";
		}else{
			var dataDelDom = " | <a href='javascript:void(0)'class='delRement' onclick='delData("+row.dataId+","+row.fileId+")'><font color='blue'>删除</font></a>";	 
		}
		//var url=row.dataId+",'"+row.fileDesc+"',"+row.violationId+",'"+row.fileProperty+"','"+row.fileUrl+"',"+row.fileId;
		var dataEditDom = " | <a href='javascript:void(0)' onclick='editData("+row.dataId+",\""+row.fileDesc+"\",\""+row.refId+"\","+row.fileProperty+",\""+row.fileUrl+"\","+row.fileId+")'><font color='blue'>编辑</font></a>";
		
		if("${readOnly}"=="true")
		{
			return downloadDom;
		}
		else
		{
		  return downloadDom+dataDelDom;
		}	
	}
	
	function fileSizefiter(value,row,index){
		var sNum='';
		if(value==0 || value == undefined|| value==null || value ==''){
		}else{
			sNum = getFileSize(value);
		}
		return sNum;
	}
	
	function addData(pid){

		if(''== pid)
		{
		   pid='0';
		}
		selectDesc='';
		var url="${basePath}afterLoanController/showUpdateFileDiog.action?projectId=${projectId}&fileType=${fileType}&refId="+pid+"&fileId=0";
		$('#dialog_data2').dialog({
			href : url,
			width : 500,
			height : 260,
			modal : true,
			title : '文件上传',
			onBeforeClose:function(){
				
			}
		});
		
	}
	
	var selectDesc='';
	function editData(dataId,fDesc,refId,fProperty,furl,fileId){
		selectDesc  = fDesc;
		var url="${basePath}afterLoanController/showUpdateFileDiog.action?projectId=${projectId}&fileType=${fileType}&refId="+refId+"&fileId="+fileId;
		$('#dialog_data2').dialog({
			href : url,
			width : 500,
			height : 400,
			modal : true,
			title : '文件编辑',
			onBeforeClose:function(){
				
			}
		});
	}
	
	function delData(dataId,fileId){
		if(dataId=="" || null==dataId){
			 $.messager.alert("操作提示","该类型文件不存在，无法删除！","error"); 
			 return false;
		}else{
			if (confirm("是否确认删除?")) {
				$.ajax({
					type: "POST",
			        url: "${basePath}afterLoanController/delData.action?fileType=${fileType}",
			        data:{"dataId":dataId,"fileId":fileId},
			        dataType: "json",
			        success: function(data){
			        	
						if(data.header.success)
						{
							$.messager.alert("操作提示","删除成功","success"); 
			        		$('#grid_data').datagrid("reload");
			        		$('#grid_data').datagrid("clearChecked");
						}
						else
						{
						  $.messager.alert("操作提示",data.header.msg,"error");
						}	
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
		        url: "${basePath}afterLoanController/delData.action",
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
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var atTitle = tab.panel('options').title;
		if(atTitle=="违约处理查看"){
			$('.delRement font').attr('color','gray');
			$('.delRement').removeAttr('onclick');
			$('#addBtn').attr('disabled','disabled');
			$('#addBtn').attr('readOnly','readOnly');
			$('#addBtn').addClass('l-btn-disabled');
			$('#addBtn').removeAttr('onclick');
		}
		if(workflowTaskDefKey != "task_BreachOfContractRequest"){
			$('.badDebt .easyui-linkbutton ,.badDebt input,.badDebt textarea').attr('disabled','disabled');
			$('.badDebt .easyui-linkbutton ,.badDebt input,.badDebt textarea').attr('readOnly','readOnly');
			$('.badDebt .easyui-linkbutton ,.badDebt input,.badDebt textarea').addClass('l-btn-disabled');
			$('.badDebt .easyui-linkbutton ,.badDebt a:not(.download)').removeAttr('onclick');
			$('.badDebt a:not(.download) font').attr('color','gray');
		}
	}
	</script> 
	
