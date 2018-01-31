<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/tree_common.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据权限列表</title>
<script language="javascript">
//查询 
function ajaxSearch() {
	
	$('#grid').datagrid('load', {
		userName : $("#userName").val(),
		dataScope: $("#dataScope").combobox('getValue') 
	});
	$('#grid').datagrid('clearChecked');
}

//删除
function removeItem() {
	var rows = $('#grid').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	}
	// 获取选中的系统用户名  and 判断是否存在系统管理员
	var dataIds = "";
	for (var i = 0; i < rows.length; i++) {
		var row = rows[i];
		dataIds += row.id + ",";
		
	}
	
	$.messager.confirm("操作提示", "您确定要删除吗?", function(r) {
		if (r) {
			$.post("delete.action", {
				ids : dataIds
			}, function(ret) {
				//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if (ret && ret.header["code"] == 200) {
					$("#dlg").dialog('close');
					// 必须重置 datagr 选中的行 
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}, 'json');
		}
	});
}
</script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
			<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="queryDataAuthList.action" method="post" id="searchFrom"
					name="searchFrom">
					<!-- 查询条件 -->
					<div style="margin: 5px">
						真实姓名:<input class="easyui-textbox" name="userName" id="userName" />
						数据权限:
						<select class="select_style easyui-combobox" editable="false" name="dataScope"  id="dataScope">
							<option value="" >--请选择--</option>
							<option value="1" >私有数据</option>
							<option value="2" >集体数据</option>
						</select>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							onclick="ajaxSearch()">查询</a>
					</div>
				</form>

				<!-- 操作按钮 -->
				<div style="padding-bottom: 5px">
					<!-- <a	href="javascript:void(0)" class="easyui-linkbutton"	iconCls="icon-edit" plain="true" onclick="editItem()">修改</a> -->
					<a  href="javascript:void(0)" class="easyui-linkbutton"	iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
				</div>
			</div>
			<div style="height:100%">
			<table id="grid" title="数据权限管理 " class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
				url: 'queryDataAuthList.action',
			    method: 'POST',
			    rownumbers: true,
			    singleSelect: false,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    toolbar: '#toolbar',
			    idField: 'id',
			    onLoadSuccess:function(data){
		    		clearSelectRows('#grid');
		   		},
			    fitColumns:true">
				<!-- 表头 -->
				<thead>
					<tr>
						<th height="30" data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'userName',sortable:true">真实姓名</th>
						<th data-options="field:'orgName',sortable:true">所属机构</th>
						<th data-options="field:'dataName',sortable:true">数据机构</th>
						<th data-options="field:'dataScope',sortable:true">数据权限</th>
						<th data-options="field:'category',sortable:true">机构类型</th>
					</tr>
				</thead>
			</table>
			</div>
	</div>
</body>
</html>