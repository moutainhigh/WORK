<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
	var url = "";

	// 新增
	function addItem() {
		$('#fm').form('reset');
		// 将 url 地址改变
		url = "insert.action";
		$('#dlg').dialog('open').dialog('setTitle', "新增");
	}

	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			$('#fm').form('load', row[0]);
			// 将 url 地址改变
			url = "update.action";
			$('#dlg').dialog('open').dialog('setTitle', "编辑");
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}

	// 删除
	function removeItem() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		// 获取选中的系统用户名 
		var configPids = "";
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			configPids += row.pid + ",";
		}
		$.messager.confirm("操作提示", "确定删除选择的此批系统用户吗?", function(r) {
			if (r) {
				$.post("delete.action", {
					"configPids" : configPids
				}, function(ret) {
					//  此方法，不需要换转换json格式数据,因为传过来的值就是json格式的数据 
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

	// 新增  or 更新
	function saveItem() {
		$("#fm").form('submit', {
			url : url,
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				// 转换成json格式的对象
				var ret = eval("(" + result + ")");
				if (ret && ret.header["code"] == 200) {
					$("#dlg").dialog('close');
					$("#grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}

	// 查询 
/* 	function ajaxSearch() {
		$('#searchFrom').form('submit', {
			success : function(data) {
				$('#grid').datagrid('loadData', eval("{" + data + "}"));
			}
		});
	} */
	
	
	//查询 
	function ajaxSearch(){
		var pageNumber=$('#grid').datagrid('options')['pageSize'];
		$('#rows').val(pageNumber);
		$('#searchFrom').form('submit',{
	        success:function(data){
	        	var str = JSON.parse(data);
	           	$('#grid').datagrid('loadData',str);
	           	$('#grid').datagrid('clearChecked');
	       }
	    });
	};
	
</script>
<title>系统配置管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">

	<div id="scroll-bar-div">
		<!--图标按钮 -->
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="getAllSysConfig.action" method="post" id="searchFrom" name="searchFrom">
			
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<!-- 查询条件 -->
				<div style="margin: 5px">
					参数名:<input class="easyui-textbox" name="configName" /> <a href="#"
						class="easyui-linkbutton" iconCls="icon-search"
						onclick="ajaxSearch()">查询</a>
				</div>
			</form>

			<!-- 操作按钮 -->
			<div style="padding-bottom: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="addItem()">新增</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-edit" plain="true" onclick="editItem()">修改</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
			</div>
		</div>
		<div class="sysConfigDiv" style="height: 100%">
			<table id="grid" title="系统配置管理" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
				    url: 'getAllSysConfig.action',
				    method: 'post',
				    rownumbers: true,
				    singleSelect: false,
				    pagination: true,
				    sortOrder:'asc',
				    remoteSort:false,
				    toolbar: '#toolbar',
				    idField: 'pid',
				    onLoadSuccess:function(data){
	    				clearSelectRows('#grid');
	    			},
				    fitColumns:true">
						<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'configName',sortable:true">参数名</th>
						<th data-options="field:'configVal',sortable:true">参数值</th>
						<th data-options="field:'description',sortable:true">描述</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
	<!-- 保存 和取消按钮 -->
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" onclick="saveItem()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
	<!--窗口 -->
	<div id="dlg" class="easyui-dialog"
		style="width: 450px; height: 320px; padding: 20px 20px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm" method="post">
			<input type="hidden" name="pid" />
			<table>
				<tr>
					<td align="right" height="28"><label><font color="red">*</font>参数名：</label></td>
					<td><input name="configName" class="easyui-textbox"
						data-options="required:true" missingMessage="请填写参数名!" /></td>
				</tr>
				<tr>
					<td align="right" height="28"><label><font color="red">*</font>参数值：</label></td>
					<td><input name="configVal" class="easyui-textbox"
						data-options="required:true" missingMessage="请填写参数值!" /></td>
				</tr>
				<tr>
					<td align="right"><label>描述：</label></td>
					<td><textarea name="description" rows="2" cols="20"
							class="easyui-textbox" style="width: 240px; height: 100px"></textarea>
					</td>
				</tr>
			</table>
		</form>
	</div>
</div>
</body>
</html>
