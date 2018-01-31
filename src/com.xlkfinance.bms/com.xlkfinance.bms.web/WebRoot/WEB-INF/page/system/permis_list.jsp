<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/system/permis.js"></script>
<script type="text/javascript">
	var permisType = [ {
		"id" : "",
		"text" : "全部"
	}, {
		"id" : "MNU",
		"text" : "菜单权限"
	}, {
		"id" : "CDM",
		"text" : "增删修改"
	}, {
		"id" : "QUY",
		"text" : "数据查询"
	} ];
	var t = [ {
		"id" : "CDM",
		"text" : "增删修改"
	}, {
		"id" : "QUY",
		"text" : "数据查询"
	} ];
	$(document).ready(function() {
		$('#permisType').combobox({
			data : permisType,
			valueField : 'id',
			textField : 'text'
		});
		$('#savePermisType').combobox({
			data : t,
			valueField : 'id',
			textField : 'text'
		})
		$('#grid').datagrid({  
		    onClickRow: function (rowIndex, rowData) {
		    	getRoleOfPermisList(rowData.pid);
		    },onLoadSuccess:function(data){
				clearSelectRows('#grid');
			} 
		}) ;
		$('#rolePermisGrid').datagrid({  
		    onClickRow: function (rowIndex, rowData) {
		    	debugger;
		    	findUserOfRole(rowData.pid);
		    },onLoadSuccess:function(data){
				clearSelectRows('#rolePermisGrid');
			} 
		}) ;
		
	});
	
	//查询已有的角色
 	function getRoleOfPermisList(pid){
 		$("#rolePermisGrid").datagrid({
		    url: '${basePath}systemPermisController/getRoleOfPermisList.action',
		    method: 'POST',
		    rownumbers: true,
		    queryParams: {"pid":pid},
		    singleSelect: true,
		    pagination: true,
		    toolbar: '#rolePermisTool',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck:false,
			checkOnSelect: false,
			onLoadSuccess:function(data){
				clearSelectRows('#rolePermisGrid');
			},
		    columns: [[
		        { field: 'pid',checkbox:true,width:100},
		        { field: 'roleName', title: '角色名字', width: 100},
		        { field: 'roleDesc', title: '角色描述', width: 170},
				{ field: 'roleCode', title: '角色编码', width: 100}
		    ]]
		}); 
	}
	
	//查询角色下的用户
	function findUserOfRole(rid){
		debugger;
		console.log("findUserOfRole rid:"+rid);
		
		var current_tab = $('#grid').datagrid("getChecked");
		var realName = $("#realName").val();
		if(rid=="" || null==rid){
			$.messager.alert("提示","用户选择错误","info");
			return;
		}
		userRoleId = rid;
		$("#roleUserGrid").datagrid({
		    url: '${basePath}systemRoleController/getRoleUserList.action',
		    method: 'POST',
		    queryParams: { 'pid': rid ,'realName':realName},
		    striped: true,
		    fitColumns: true,
		    singleSelect: false,
		    toolbar: '#roleUserTool',
		    rownumbers: true,
		    pagination: true,
		    nowrap: false,
		    showFooter: true,
		    idField: 'pid',
		    fitColumns:true,
			selectOnCheck: true,
			checkOnSelect: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    onLoadSuccess:function(data){
				clearSelectRows('#roleUserGrid');
			},
		    columns: [[
		        { field: 'pid', checkbox: true},
		        { field: 'userName', title: '用户名',sortable:true },
		        { field: 'realName', title: '姓名',sortable:true },
		        { field: 'memberId', title: '工号',sortable:true},
		        { field: 'department', title: '部门',sortable:true},
		        { field: 'jobTitle', title: '职位',sortable:true}
		    ]]
		});
		
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div style="height: 380px;width: 100%">
				<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<!-- 查询条件 -->
				<div style="padding: 5px">
					<table>
						<tr>
							<td>权限名称:</td>
							<td><input class="easyui-textbox" name="permisName"
								id="permisName" /></td>
							<td>权限类型:</td>
							<td><select id="permisType" name="permisType"
								class="easyui-combobox" data-option="editable:false"
								style="width: 150px;">
							</select></td>
							<td>权限编码:</td>
							<td><input class="easyui-textbox" name="permisCode"
								id="permisCode" /></td>
							<td><a href="#" class="easyui-linkbutton"
								iconCls="icon-search" onclick="searchPermis()">查询</a></td>
						</tr>
					</table>
				</div>
				<!-- 操作按钮 -->
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="showAddDiv()">新增</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="showUpdateDiv()">修改</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="deletePermis()">删除</a>
				</div>
			</div>
			<div class="permislistDiv" >
				<table id="grid" title="权限管理" class="easyui-datagrid"
					style="height:380px; width: auto;"
					data-options="
				    url: '${context}/BMS/systemPermisController/permislist.action',
				    method: 'POST',
				    rownumbers: true,
				    singleSelect: true,
				    pagination: true,
				    sortOrder:'asc',
				    remoteSort:false,
				    toolbar: '#toolbar',
				    idField: 'pid',
				    fitColumns:true,
				    singleSelect: false,
					selectOnCheck:true,
					onLoadSuccess:function(data){
	    				clearSelectRows('#grid');
	    			},
					checkOnSelect: true
				    ">
					<!-- 表头 -->
					<thead>
						<tr>
							<th data-options="field:'pid',checkbox:true,width:100"></th>
							<th data-options="field:'permisName',width:100,sortable:true">权限名称</th>
							<th data-options="field:'permisType',width:100,sortable:true">权限类型</th>
							<th data-options="field:'permisDesc',width:100,sortable:true">权限描述</th>
							<th data-options="field:'permisCode',width:100,sortable:true">权限编码</th>
						</tr>
					</thead>
				</table>

		</div>
		<div style="height: 380px; width:100%;">
				<div style="height: 380px;  width: 50%;  float:left;" id="RolePermis">

						<table id="rolePermisGrid" title="系统角色管理" class="easyui-datagrid" 
							   style="height:380px;width:100%;"data-options="toolbar:'#rolePermisTool'">
						</table>
				</div>

				<div style="height: 380px;  width: 50%;  float:left;">
				<table id="roleUserGrid" title="该角色下的用户" class="easyui-datagrid" 
			   		style="height:380px;width:100%;"data-options="toolbar:'#rolePermisTool'">
				</table>
				</div>
			</div>
		<!-- 保存 和取消按钮 -->
		<div id="dlg-buttons">
			<a id="add" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-save" onclick="savePermis()" style="display: none;">保存</a>
			<a id="update" href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-save" onclick="updatePremis()" style="display: none;">保存</a>

			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
		</div>
		<!--窗口 -->
		<div id="dlg" class="easyui-dialog"
			style="width: 500px; height: auto; padding: 10px 20px" closed="true"
			buttons="#dlg-buttons">
			<form id="fm" method="post">
				<table>
					<tr>
						<td align="right" height="28"><label><font
								color="red">*</font>权限类型：</label></td>
						<td><select id="savePermisType" name="permisType"
							data-option="editable:false" class="easyui-combobox"
							style="width: 171px">

						</select></td>
					</tr>
					<tr>
						<td align="right" height="28"><label><font
								color="red">*</font>权限名称：</label></td>
						<td><input name="pid" class="easyui-validatebox"
							data-options="required:true" id="savePermisId" type="hidden">
							<input name="permisName" class="easyui-validatebox textbox "
							data-options="required:true" id="savePermisName"></td>
					</tr>
					<tr>
						<td align="right" height="28"><label><font
								color="red">*</font>权限编码：</label></td>
						<td><input name="permisCode"
							class="easyui-validatebox textbox" data-options="required:true"
							id="savePermisCode"></td>
					</tr>
					<tr>
						<td align="right"><label>权限描述：</label></td>
						<td><input name="permisDesc"
							style="width: 240px; height: 100px" class="easyui-textbox"
							data-options="required:false,multiline:true" id="savePermisDesc"></td>
					</tr>
				</table>
				<p></p>
				<p></p>
				<p></p>
				<p>&nbsp;</p>
				<div class="fitem"></div>
			</form>
		</div>
	</div>
</body>