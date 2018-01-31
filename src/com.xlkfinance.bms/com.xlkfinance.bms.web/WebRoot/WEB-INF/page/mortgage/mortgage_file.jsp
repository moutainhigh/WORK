<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<!-- 上传文件弹出框 -->
	<div id="dizhiyafileDialog" class="easyui-dialog" style="width:500px;height:200px;" closed="true">
		<form action="uploadFile.action" method="post" enctype="multipart/form-data" id="dizhiyafileDialogForm">
		<input type="hidden" name="baseId" id="baseId"/>
		<table style="margin-left: 30px">
			<tr>
				<td style="height: 25px;">选择文件类型：</td>
				<td><input class="easyui-textbox fileProperty" name="fileProperty" readonly="readonly" style="width: 300px"/></td>
			</tr>
			<tr>
				<td style="height: 25px;">文件选择：</td>
				<td><input class="easyui-filebox" name="file2" data-options="buttonText:'选择文件',onChange:setFileName" style="width: 300px" required="true"></td>
			</tr>
			<tr>
				<td style="height: 25px;">文件名称：</td>
				<td><input class="easyui-textbox" name="fileName" id="fileName" style="width: 300px" required="true"></td>
			</tr>
			<tr>
				<td style="height: 25px;">上传备注：</td>
				<td><input class="easyui-textbox" name="fileDesc" style="width: 300px" required="true"></td>
			</tr>
			<tr>
				<td style="height: 25px;" colspan="2">
					<input type="button" value="上传" onclick="saveDizhiyaFileDialog()" style="width: 80px;height: 25px;">
					<input type="button" value="取消" onclick="javascript:$('#dizhiyafileDialog').dialog('close')" style="width: 80px;height: 25px;">
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	<div id="editDizhiyaFileDilog" class="easyui-dialog" style="width:500px;height:200px;" closed="true">
		<form action="editDesc.action" method="post" id="editDizhiyaFileForm">
		<input type="hidden" name="pid" class="filePid"/>
		<table style="margin-left: 30px">
			<tr>
				<td style="height: 25px;">选择文件类型：</td>
				<td><input class="easyui-combobox fileProperty" name="fileProperty" readonly="readonly" style="width: 300px"/></td>
			</tr>
			<tr>
				<td style="height: 25px;">上传备注：</td>
				<td><input class="easyui-textbox" name="fileDesc" id="fileDesc" style="width: 300px" required="true"></td>
			</tr>
			<tr>
				<td style="height: 25px;" colspan="2">
					<input type="button" value="上传" onclick="editDizhiyaFile()" style="width: 80px;height: 25px;">
					<input type="button" value="取消" onclick="javascript:$('#editDizhiyaFileDilog').dialog('close')" style="width: 80px;height: 25px;">
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	<div id="reloadDizhiyaFileDilog" class="easyui-dialog" style="width:500px;height:200px;" closed="true">
		<form action="reloadFile.action" method="post" enctype="multipart/form-data" id="reloadDizhiyaFileForm">
		<input type="hidden" name="pid" class="filePid"/>
		<table style="margin-left: 30px">
			<tr>
				<td style="height: 25px;">文件选择：</td>
				<td><input class="easyui-filebox" name="file2" data-options="buttonText:'选择文件',prompt:'选择一个文件...',onChange:setFileName2" style="width: 300px" required="true"></td>
			</tr>
			<tr>
				<td style="height: 25px;">文件名称：</td>
				<td><input class="easyui-textbox" name="fileName" id="fileName" style="width: 300px" required="true"></td>
			</tr>
			<tr>
				<td style="height: 25px;" colspan="2">
					<input type="button" value="上传" onclick="reloadDizhiyaFile()" style="width: 80px;height: 25px;">
					<input type="button" value="取消" onclick="javascript:$('#reloadDizhiyaFileDilog').dialog('close')" style="width: 80px;height: 25px;">
				</td>
			</tr>
		</table>
		</form>
	</div>
	
	
	<script type="text/javascript">
		
		// 打开文件上传的窗口
		function insertFileDizhiya(fileProperty,formId){
			var ss=$('#baseId').val();
			//debugger;
			$('#dizhiyafileDialogForm').form('reset');
			$(".fileProperty").textbox("setValue",fileProperty);
			// 判断当前操作是什么操作
			if(fileProperty == '提取抵押申请类型'){
				if( null == $("#tqsqForm input[name='extractionId']").val() || $("#tqsqForm input[name='extractionId']").val() == ''){
					$.messager.alert("操作提示","请先保存提取申请信息,再上传文件!","error");
					return;
				}
				//$('#baseId').val($("#tqsqForm input[name='extractionId']").val());
			}else{
				//$('#baseId').val($(formId+" input[name='pid']").val());
			}
			$('#dizhiyafileDialog').dialog('open').dialog('setTitle','上传资料');
		}

		// 解析出文件名称
		function setFileName(newValue,oldValue){
			var value = newValue.replace(/.*(\/|\\)/, "");
			$('#dizhiyafileDialog #fileName').textbox('setValue',value);
		}
		
		// 解析出文件名称-重新上传
		function setFileName2(newValue,oldValue){
			var value = newValue.replace(/.*(\/|\\)/, "");
			$('#reloadDizhiyaFileDilog #fileName').textbox('setValue',value);
		}

		// 执行抵质押物文件上传保存
		function saveDizhiyaFileDialog(){
			//debugger;
			$('#dizhiyafileDialogForm').form('submit',{
				success:function(data){
					var datas = strToJson(data);
					$.messager.alert("操作提示",datas.uploadStatus,"success");
					$('#dizhiyafileDialog').dialog("close");
					// 刷新不同的datagrid
					if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "提取抵押申请类型"){
						var extractionId = $("#tqsqForm input[name='extractionId']").val();
						$('#tqsq-datagrid').datagrid({
				 			url:"getProjectAssFile.action?baseId="+extractionId+"&fileTyle=2",
				 			method:'post'
						}); 
					}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押解除类型"){
						$('#jcdzy-datagrid').datagrid("reload");
					}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押物办理类型"){
						$('#bldj-datagrid').datagrid("reload");
					}
				}
			})
		}
		
		// 编辑上传
		function editDizhiyaFile(){
			$('#editDizhiyaFileForm').form('submit',{
				success:function(data){
					var datas = strToJson(data);
					$.messager.alert("操作提示",datas.uploadStatus,"success");
					$('#editDizhiyaFileDilog').dialog("close");
					// 刷新不同的datagrid
					if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "提取抵押申请类型"){
						$('#tqsq-datagrid').datagrid("reload");
					}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押解除类型"){
						$('#jcdzy-datagrid').datagrid("reload");
					}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押物办理类型"){
						$('#bldj-datagrid').datagrid("reload");
					}
				}
			})
		}
		
		//  重新上传
		function reloadDizhiyaFile(){
			$('#reloadDizhiyaFileForm').form('submit',{
				success:function(data){
					var datas = strToJson(data);
					$.messager.alert("操作提示",datas.uploadStatus,"success");
					$('#reloadDizhiyaFileDilog').dialog("close");
					// 刷新不同的datagrid
					if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "提取抵押申请类型"){
						$('#tqsq-datagrid').datagrid("reload");
					}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押解除类型"){
						$('#jcdzy-datagrid').datagrid("reload");
					}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押物办理类型"){
						$('#bldj-datagrid').datagrid("reload");
					}
				}
			})
		}

		// 删除文件
		function delFileDi(pid){
			if (confirm("是否确认删除?")) {
				$.ajax({
					type: "POST",
			        url: "${basePath}mortgageController/delProjectAssFile.action",
			        type:'post',
			        data:{"pids":pid},
			        dataType: "json",
			        success: function(data){
			        	if(data!=0){
				        	$.messager.alert("系统提示","删除资料成功","success"); 
			        	}else{
			        		$.messager.alert("系统提示","删除资料失败","error"); 
			        	}
			        	// 刷新不同的datagrid
						if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "提取抵押申请类型"){
							$('#tqsq-datagrid').datagrid("reload");
						}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押解除类型"){
							$('#jcdzy-datagrid').datagrid("reload");
						}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押物办理类型"){
							$('#bldj-datagrid').datagrid("reload");
						}
			        }
				});
			}
		}
		
		//批量删除文件
		function delFileDizhiya(datagrid){
			var pidArrays = $(datagrid).datagrid("getChecked");
			if(pidArrays.length<1){
				$.messager.alert("系统提示","请选择要删除的附件","error"); 
				return;
			}
			var pids ="";
			for (var i = 0; i < pidArrays.length; i++) {
				if (i == 0) {
					pids += pidArrays[i].pid;
				} else {
					pids += "," + pidArrays[i].pid;
				}
			}
			if (confirm("是否确认删除?")) {
				$.ajax({
					type: "POST",
			        url: "${basePath}mortgageController/delProjectAssFile.action",
			        type:'post',
			        data:{"pids":pids},
			        dataType: "json",
			        success: function(data){
			        	if(data!=0){
				        	$.messager.alert("系统提示","删除资料成功","success"); 
			        	}else{
			        		$.messager.alert("系统提示","删除资料失败","error"); 
			        	}
			        	// 刷新不同的datagrid
						if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "提取抵押申请类型"){
							$('#tqsq-datagrid').datagrid("reload");
						}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押解除类型"){
							$('#jcdzy-datagrid').datagrid("reload");
						}else if($("#dizhiyafileDialogForm input[name='fileProperty']").val() == "抵质押物办理类型"){
							$('#bldj-datagrid').datagrid("reload");
						}
			        }
				});
			}
		}
		
		// 打开编辑or重新上传窗口
		function editFileDi(pid,desc,bId,property){
			if(bId==1){
				$('.filePid').val(pid);
				$('#editDizhiyaFileDilog #fileProperty').combobox('setValue',property);
				$('#fileDesc').textbox('setValue',desc);
				$('#editDizhiyaFileDilog').dialog("open").dialog('setTitle','编辑');
			}
			if(bId==2){
				$('#reloadDizhiyaFileForm').form('reset');
				$('.filePid').val(pid);
				$('#reloadDizhiyaFileDilog').dialog("open").dialog('setTitle','重新上传');
			}
		}
		// 转换JSON格式
		function strToJson(str){
			return JSON.parse(str);
		} 
		
		// 文件大小格式化
		function sizeFileter(value,row,index){
			var fileSize = value;
			var num = new Number();
			var unit = '';
			if (fileSize > 1*1024*1024*1024){
				num = fileSize/1024/1024/1024;
				unit = "G"
			}else if (fileSize > 1*1024*1024){
				num = fileSize/1024/1024;
				unit = "M"			
			}else if (fileSize > 1*1024){
				num = fileSize/1024;
				unit = "K"
			}else{
				return fileSize;
			}
			return num.toFixed(2) + unit;
		}
	</script>