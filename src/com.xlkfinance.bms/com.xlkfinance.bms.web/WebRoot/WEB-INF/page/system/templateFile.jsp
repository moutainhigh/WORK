<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script type="text/javascript">
//重置按钮
function majaxReset() {
	$(".ecoTradeId1").hide();
	$('#searchFrom').form('reset')
}
	function getFileSize(fileSize) {
		var num = new Number();
		var unit = '';
		if (fileSize > 1 * 1024 * 1024 * 1024) {
			num = fileSize / 1024 / 1024 / 1024;
			unit = "G"
		} else if (fileSize > 1 * 1024 * 1024) {
			num = fileSize / 1024 / 1024;
			unit = "M"
		} else if (fileSize > 1 * 1024) {
			num = fileSize / 1024;
			unit = "K"
		} else {
			return fileSize;
		}

		return num.toFixed(2) + unit;

	}

	function upload(pid, fileProperty, filePropertyName, fileType) {
		if (pid == '' || pid == undefined || pid == null) {
			$('#form_temp').form('reset');
			$('#filePropertyName').text(filePropertyName);
			$('#fileProperty').val(fileProperty);
			$('#form_temp')
					.attr('action',
							"${basePath}templateFileController/saveTemplateFile.action");
			$('#dialog_temp').dialog('open').dialog('setTitle', '上传模板');
		} else {
			if (confirm("该类文件已存在，是否进行重新上传?")) {
				$('#form_temp').form('reset');
				$('#filePropertyName').text(filePropertyName);
				$('#fileProperty').val(fileProperty);
				$('#pid').val(pid);
				$('#fileType').combobox('setValue', fileType);
				$('#form_temp')
						.attr('action',
								'${basePath}templateFileController/updateTemplateFile.action');
				$('#dialog_temp').dialog('open').dialog('setTitle', '重新上传模板');
			}
		}
	}

	function reupload(pid, fileProperty, filePropertyName, fileType) {
		if (pid == '' || pid == undefined || pid == null) {
			if (confirm("该类文件未存在，是否先进行上传?")) {
				$('#form_temp').form('reset');
				$('#filePropertyName').text(filePropertyName);
				$('#fileProperty').val(fileProperty);
				$('#form_temp')
						.attr('action',
								"${basePath}templateFileController/saveTemplateFile.action");
				$('#dialog_temp').dialog('open').dialog('setTitle', '上传模板');
			}
		} else {
			$('#form_temp').form('reset');
			$('#filePropertyName').text(filePropertyName);
			$('#fileProperty').val(fileProperty);
			$('#pid').val(pid);
			$('#fileType').combobox('setValue', fileType);
			$('#form_temp')
					.attr('action',
							'${basePath}templateFileController/updateTemplateFile.action');
			$('#dialog_temp').dialog('open').dialog('setTitle', '重新上传模板');
		}
	}

	function saveTemp() {
		$('#form_temp').form('submit', {
			onSubmit : function() {
				var fileVal = $('#file2').filebox('getValue')
				var fileType = fileVal.substring(fileVal.lastIndexOf(".") + 1);
				if ($('#fileType').combobox('getValue') == 1) {
					if (fileType != "doc" && fileType != "docx") {
						$.messager.alert("操作提示", "文件类型不对，请重新选择", "error");
						return false;
					}
				} else {
					if (fileType != "xls" && fileType != "xlsx") {
						$.messager.alert("操作提示", "文件类型不对，请重新选择", "error");
						return false;
					}
				}
			},
			success : function(response) {
				var datas = strToJson(response);
				$.messager.alert("操作提示", datas.uploadStatus, "success");
				$('#dialog_temp').dialog('close');
				$('#temp_grid').datagrid('reload');
			}
		}, 'json')
	}

	function delTemplate(pid) {
		if (pid == '' || pid == undefined || pid == null) {
			$.messager.alert("操作提示", "文件不存在，请选择已上传文件", "error");
		} else {
			if (confirm("是否确认删除?")) {
				$
						.ajax({
							url : "${basePath}templateFileController/delTemplateFile.action",
							type : "post",
							data : {
								pid : pid
							},
							dataType : "json",
							success : function(response) {
								$.messager.alert("操作提示", response.uploadStatus,
										"success");
								$('#temp_grid').datagrid('reload');
							}
						})
			}
		}
	}

	function fileTypeFilter(value, row, index) {
		if (value == 0) {
			return "";
		} else if (value == 1) {
			return "Word";
		} else if (value == 2) {
			return "Excel";
		}

	}

	function caozuofiter(value, row, index) {
		var upload = "<a href='javascript:upload(" + row.pid + ","
				+ row.fileProperty + ",\"" + row.filePropertyName + "\","
				+ row.fileType + ")'><font color='blue'>上传</font></a> | ";
		var reupload = "<a href='javascript:reupload(" + row.pid + ","
				+ row.fileProperty + ",\"" + row.filePropertyName + "\","
				+ row.fileType + ")'><font color='blue'>重新上传</font></a> | ";
		var download = "<a href='${basePath}beforeLoanController/downloadData.action?path="
				+ row.fileUrl
				+ "&fileName="
				+ row.fileName
				+ "'><font color='blue'>下载</font></a> | ";
		var delload = "<a href='javascript:delTemplate(" + row.pid
				+ ")'><font color='blue' >删除</font></a>";
		return upload + reupload + download + delload;
	}

	function strToJson(str) {
		return JSON.parse(str);
	}
</script>

<title>模板管理列表</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
			<!--图标按钮 -->
			<div id="toolbar_data" class="easyui-panel" style="border-bottom: 0;">
				<form
					action="<%=basePath%>templateFileController/templateFileList.action"
					method="post" id="searchFrom" name="searchFrom">
					<!-- 查询条件 -->
					<div style="padding: 5px">
						<table>
							<tr>
								<td width="80" align="right">模板类型：</td>
								<td>
									<input class="easyui-textbox" name="filePropertyName" id="filePropertyName" />
								</td>
								<td width="80" align="right">文件名称：</td>
								<td>
									<input class="easyui-textbox" name="fileName" id="fileName" />
								</td>
								<td width="80" align="right">上传时间：</td>
								<td>
									<input id="fromUploadDttm" name="fromUploadDttm" editable="false" class="easyui-datebox" />
									<span id="radioUploadDttm">至</span>
									<input id="endUploadDttm" name="endUploadDttm" editable="false" class="easyui-datebox" />
								</td>
							</tr>

							<tr>
								<td colspan="10"><a href="#" class="easyui-linkbutton"iconCls="icon-search"
									onclick="ajaxSearchPage('#temp_grid','#searchFrom')">查询</a> <a
									href="#" onclick="majaxReset()" class="easyui-linkbutton"
									style="width: 60px;">重置</a></td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<div class="dksqListDiv" style="height: 100%;">
				<table id="temp_grid" title="模板管理" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="
				    url: '${basePath}templateFileController/templateFileList.action',
				    method: 'get',
				    rownumbers: true,
				    singleSelect:true,
				    pagination: true,
				    toolbar: '#toolbar_data',
				    idField: 'pid',
				    selectOnCheck:true,
				    onLoadSuccess:function(data){
						clearSelectRows('#temp_grid');
					},
					checkOnSelect: true">
					<thead>
						<tr>
							<th data-options="field:'pid',width:30" hidden="true"></th>
							<th data-options="field:'filePropertyName',width:200">模板类型</th>
							<th
								data-options="field:'fileType',width:80,formatter:fileTypeFilter">文件类型</th>
							<th hidden="true" data-options="field:'fileUrl'"></th>
							<th hidden="true" data-options="field:'fileProperty'"></th>
							<th data-options="field:'fileName',width:260">文件名称</th>
							<th
								data-options="field:'fileSize',width:90,formatter:getFileSize">大小</th>
							<th data-options="field:'uploadDttm',width:150">上传时间</th>
							<th data-options="field:'cz',width:200,formatter:caozuofiter">操作</th>
						</tr>
					</thead>
				</table>

				<div id="data-buttons">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-save" onclick="saveTemp()">保存</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel"
						onclick="javascript:$('#dialog_temp').dialog('close')">取消</a>
				</div>
				<div class="easyui-dialog" id="dialog_temp"
					data-options="closed:true" buttons="#data-buttons"
					style="height: 220px; width: 500px">
					<form id="form_temp" action="" method="post"
						enctype="multipart/form-data">
						<table>
							<tr>
								<td height="40px" width="80px" align="center">模板类型：</td>
								<td width="350px"><span id="filePropertyName"></span> <input
									type="hidden" id="fileProperty" name="fileProperty"> <input
									type="hidden" id="pid" name="pid"></td>
							</tr>
							<tr>
								<td height="40px" align="center">文件类型：</td>
								<td><select class="easyui-combobox" name="fileType"
									id="fileType" required="true" style="width: 100px">
										<option value="">--请选择--</option>
										<option value="1">word</option>
										<option value="2">excel</option>
								</select></td>
							</tr>
							<tr>
								<td height="40px" align="center">上传文件：</td>
								<td><input class="easyui-filebox"
									data-options="buttonText: '选择文件',missingMessage:'选择一个文件'"
									id="file2" name="file2" style="width: 350px" required="true">
								</td>
							</tr>
						</table>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
