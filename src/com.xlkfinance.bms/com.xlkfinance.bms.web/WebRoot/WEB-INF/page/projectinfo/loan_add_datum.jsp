<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ include file="/config.jsp"%>
	<div class="datum1" style="padding:30px 10px 10px 10px;width:1039px; ">
		<div id="toolbar_data">
			<div style="padding-bottom:5px">
			<shiro:hasAnyRoles name="UPLOAD_PROJECT_FILE,ALL_BUSINESS">
				<a href="javascript:void(0)" class="easyui-linkbutton" id="addData" iconCls="icon-add" plain="true"  onclick="addData()">新增</a>
			</shiro:hasAnyRoles>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="delAnyData()">删除</a>
				<a href="javascript:void(0)" id="downAll" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="downLoadAllData()">一键下载</a>
			</div>
		</div>
		<div style="height:400px" class="datum"> 
			<table id="grid_data" title="资料上传列表" class="easyui-datagrid" style="height:100%;width: auto;"
				 data-options="
				    url: '${basePath}beforeLoanController/fileList.action?projectId='+projectId,
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
				<th data-options="field:'filePropertyName',width:220" >文件类型</th>
			    <th data-options="field:'fileType',width:60">文件种类</th>
			    <th hidden="true" data-options="field:'fileUrl'"></th>
			    <th data-options="field:'fileName',width:170">文件名称</th>
			    <th data-options="field:'fileSize',width:50,formatter:fileSizefiter">大小</th>
			    <th data-options="field:'createNode',width:80,formatter:formatterCreateNode">上传节点</th>
			    <th data-options="field:'createUserName',width:80">操作人</th>
			    <th data-options="field:'uploadDttm',width:120">上传时间</th>
			    <th data-options="field:'fileDesc',width:120">上传说明</th>
			    <th data-options="field:'cz',width:160,formatter:caozuofiter">操作</th>
			     
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
							<input type="hidden" name="createNode" id="createNode">
							<input type="hidden" name="dataProjectId" id="dataProjectId">
							<input type="hidden" name="dataFileProperty" id="dataFileProperty">
						</td>
					</tr>
					<tr>
						<td width="120" align="right">上传说明：</td>
						<td><input  class="easyui-textbox" style="width:240px;height:100px" data-options="required:false,multiline:true,validType:'length[1,100]'" name="datafileDesc" id="datafileDesc"></td>
					</tr>
					<tr>
						<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
						<td>
							<input class="easyui-filebox" class="download"  data-options="buttonText:'选择文件',missingMessage:'选择一个文件',fileSize:[200,'MB']" id="file2" name="file2" style="width:240px;" required="true"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="data-buttons">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOrupdateData(this)" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data').dialog('close')">取消</a>
		</div>
		
		
		<div id="dialog_data2"  class="easyui-dialog" style="width:500px;height:auto;top:100px;padding:10px 20px;" closed="true"  buttons="#data-buttons2">
			<form id="addOrupdate2" name="addOrupdate2" method="post" enctype="multipart/form-data">
				<table>
					<tr>
						<input type="hidden" name="createNode" id="createNode1">
						<td width="120" align="right" height="28"><font color="red">*</font>文件类型：</td>
						<td>
							<input class="easyui-combobox" name="dataFileProperty" id="dataFileProperty2" style="width:200px"
							data-options="url:'${basePath}beforeLoanController/searchDataType.action?projectId='+projectId,
										  method:'get',
										  valueField:'pid',		            
										  textField:'lookupDesc'" required="true">
							<input type="hidden" name="dataProjectId" id="dataProjectId2">
						</td>
					</tr>
					<tr>
						<td width="120" align="right">上传说明：</td>
						<td><input  class="easyui-textbox" style="width:240px;height:100px" data-options="required:false,multiline:true,validType:'length[1,100]'" name="datafileDesc" id="datafileDesc"></td>
					</tr>
					<tr>
						<td align="right" width="120" height="28"><font color="red">*</font>文件路径：</td>
						<td>
							<input class="easyui-filebox" class="download" data-options="buttonText:'选择文件',missingMessage:'选择一个文件',fileSize:[200,'MB']" id="file3" name="file2"  style="width:240px;" required="true"/>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="data-buttons2">
			<a id="addsss" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addOrupdateData2(this)" >保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dialog_data2').dialog('close')">取消</a>
		</div>
		
		<input type="hidden" name="pro_Id" id="pro_Id">
	</div>
	<script type="text/javascript" src="${basePath}upload/javascript/upload.js" charset="utf-8"></script>
	<script type="text/javascript">
	$.extend($.fn.validatebox.defaults.rules, {
		// filebox验证文件大小的规则函数
		// 如：validType : ['fileSize[1,"MB"]']
		fileSize : {
			validator : function(value, array) {
				var size = array[0];
				var unit = array[1];
				
				var index = -1;
				var unitArr = new Array("bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb");
				for (var i = 0; i < unitArr.length; i++) {
					if (unitArr[i] == unit.toLowerCase()) {
						index = i;
						break;
					}
				}
				if (index == -1) {
					$.error('请指定正确的验证文件大小的单位：["bytes", "kb", "mb", "gb", "tb", "pb", "eb", "zb", "yb"]');
				}
				// 转换为bytes公式
				var formula = 1;
				while (index > 0) {
					formula = formula * 1024;
					index--;
				}
				// this为页面上能看到文件名称的文本框，而非真实的file
				// $(this).next()是file元素
				
				return $(this).next().get(0).files[0].size < parseFloat(size) * formula;
			},
			message : '文件大小必须小于 {0}{1}'
		}
		
	});
	
	
	var foreStatus = ${foreStatus};
	$(function(){
		$('#pro_Id').val(projectId);
		$('.easyui-filebox').filebox({
			buttonText: '选择文件',
				 required : true,  
			        //width : '300px',  
			        //multiple : true,  
			        validType : ['fileSize[200,"MB"]' ],  
			        buttonText : '请选择',  
			        buttonAlign : 'right',  
			        prompt : '请选择文件',  
			        //accept : [ 'image/jpg', 'image/bmp', 'image/jpeg', 'image/gif', 'image/png' ]  
		})
		//流程控制
	
	});

	// 文件上传节点描述信息
	function formatterCreateNode(val,row){
		row.projectType = 8;
		return formatterProjectStatus(val, row);
	}
	
	function downLoadAllData(){
		window.location.href=BASE_PATH+"beforeLoanController/downLoadZipFile.action?projectId="+projectId;
	}
	function caozuofiter(value,row,index){

		var fileName = row.fileName;
		if(fileName!='' && fileName!=null &&fileName!=undefined){
			fileName = fileName.replace(/\ +/g,"");
		}
		
		if(row.fileUrl=='' || row.fileUrl==null ||row.fileUrl==undefined){
			var downloadDom = "<a href='javascript:void(0)' class='download'><font color='gray'>下载</font> | </a>";
		}else{
			var downloadDom = "<a href='javascript:void(0)' onclick=downLoadFileByPath('"+row.fileUrl+"','"+ fileName+"') class='download'><font color='blue'>下载</font> | </a> ";
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
		
		if(row.dataId=='' || row.dataId==null ||row.dataId==undefined || foreStatus>1){
			var dataDelDom = "<a href='javascript:void(0)' ><font color='gray'>删除</font></a>";
		}else{
			var dataDelDom = "<a href='javascript:void(0)' class='download'  onclick='delData("+row.dataId+")'><font color='blue'>删除</font></a>";	
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
	
	
	function addOrupdateData(target){
		if(foreStatus <= 0) {
			foreStatus = 1;
		}
		$('#createNode').val(foreStatus);
		var file2 = $('#file2').textbox('getValue');
		var proId = $('#dataProjectId').val();
		var fileProperty = $('#dataFileProperty').val();
		if(proId=="" || null==proId){
			$.messager.alert("操作提示","请重新选择项目","error"); 
			return false;
		}
		if ($('#file2').next().find("input[type='file']").get(0).files[0].size > 200 *1024 *1024) {
			value = " ";
			$.messager.alert("操作提示","文件大小必须小于200MB","error");
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
	
	
	
	function addOrupdateData2(target){
		if(foreStatus <=0){
			foreStatus = 1;
		}
		 $('#createNode1').val(foreStatus);
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
		if ($('#file3').next().find("input[type='file']").get(0).files[0].size > 200 * 1024 * 1024 ) {
			value = " ";
			$.messager.alert("操作提示","文件大小必须小于200MB","error");
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
		if((isEdit != 'isEdit' && foreStatus > 1) || foreStatus > 7){
			$('.datum .easyui-combobox').attr('disabled','disabled');
			$('.datum input[type="checkbox"]').attr('disabled','disabled');
			$('.datum .easyui-textbox').attr('disabled','disabled');
			$('.datum .easyui-linkbutton ,.datum a:not(.download)').removeAttr('onclick');
			$('.datum a:not(.download) font').attr('color','gray');
			$('.datum .easyui-linkbutton ,.datum  input,.datum  textarea').attr('disabled','disabled');
			$('.datum .easyui-linkbutton ,.datum  input,.datum  textarea').attr('readOnly','readOnly');
			$('.datum .easyui-linkbutton ,.datum  input,.datum  textarea').addClass('l-btn-disabled');
		}
		$("#downAll").linkbutton("enable");
		$("#downAll").bind('click', function(){
			downLoadAllData();
		});
		//待放款申请前新增按钮都有效
		if(foreStatus < 11) {
			$("#addData").linkbutton("enable");
			$("#addData").bind('click', function(){
				addData();
			});
		}
		
		
	}

	</script> 
	