<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/system/role.js"></script>

<style type="text/css">

</style>
<body class="easyui-layout">
<div id="roleDiv" style="width:49%;float: left;">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<!-- 查询条件 -->
		<div style="margin:5px">
			角色名称:<input class="easyui-textbox" name="roleName" id="roleName"/> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchRole()">查询</a>
		</div>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="updateRole()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="findUser()">添加用户</a>
		</div> 
	
	</div>

	<table id="grid" title="系统角色管理" class="easyui-datagrid" 
	style="height:auto;width:90%;"
	data-options="
	    url: '${context}/BMS/systemRoleController/rolelist.action',
	    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true">
	<!-- 表头 -->
	
		<thead>
			<tr>
				<th data-options="field:'pid',checkbox:true,width:40" ></th>
			    <th data-options="field:'roleName',width:80,sortable:true"  >角色名称</th>
			    <th data-options="field:'roleDesc',width:120,sortable:true"  >角色描述</th>
			    <th data-options="field:'roleCode',width:160,sortable:true"  >角色编码</th>
			</tr>
		</thead>
	</table>
	
	<div>
		<div id="roleUserTool" class="easyui-panel" style="border-bottom: 0;doSize:true">
		<!-- 操作按钮 -->
		<div style="margin:5px">
				姓名:<input type="text" class="text_style"  name ="realName" id="realName"/> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searchUserOfRoleNameBtn" >查询</a>
		</div>
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="removeRoleUser()">取消该用户</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRoleUser()">添加角色</a>
		</div>
		</div>
	
		<table id="roleUserGrid" title="该角色下的用户" class="easyui-datagrid" 
		style="height:auto;width:500px;"
		data-options="toolbar: '#roleUserTool'">
		</table>
	</div>
</div>


<div style="width: 48%;float: left;" id="RolePermis">
	<div>
		<div id="rolePermisTool" class="easyui-panel" style="border-bottom: 0;doSize:true">
		<!-- 操作按钮 -->
		<div style="margin:5px">
			拥有权限名称:<input type="text" class="text_style" id="rolePermisName" name ="rolePermisName"/> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searchRolePermisNameBtn" >查询</a>
		</div>
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="removeRolePermis()">取消该权限</a>
		</div>
		</div>
		<table id="rolePermisGrid" title="该角色已拥有的权限" class="easyui-datagrid" 
			   style="height:auto;width:500px;"data-options="toolbar:'#rolePermisTool'">
		</table>
	</div>
	
	<div>
		<div id="roleNotPermisTool" class="easyui-panel" style="border-bottom: 0;doSize:true">
			<!-- 操作按钮 -->
			<div style="margin:5px">
				权限名称:<input type="text" class="text_style" id="roleNotpermisName" name ="roleNotpermisName"/> 
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searchRoleNotpermisNameBtn" >查询</a>
			</div>
			<div style="padding-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRolePermis()">附加该权限</a>
			</div>
		</div>
		<table id="roleNotPermisGrid" title="该角色未拥有的权限" class="easyui-datagrid" 
		style="height:auto;width: auto;"data-options="toolbar:'#roleNotPermisTool'">
		</table>
	</div>
</div>
<div class="clear"></div>
<!-- 保存 和取消按钮 -->
<!-- 保存 和取消按钮 -->
<div id="dlg-buttons" >  
    <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveItem()" style="display:none;">保存</a>
    <a id="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="savaUpdate()"  style="display:none;">保存</a>
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>
<!--窗口 -->
<div id="dlg" class="easyui-dialog" style="width:500px;height:330px;padding:10px 20px"
        closed="true" buttons="#dlg-buttons">
     <form id="fm" method="post">
          <div class="fitem">
          <table>
          	<tr>
          		<td height="28"><label style="text-align:right;"><font color="red">*</font>角色名称：</label></td>
          		<td><input name="roleName" class="easyui-validatebox textbox" data-options="required:true" id="saveRoleName"></td>
          	</tr>
          	<tr>
          		<td height="28"><label style="text-align:right;">从属角色：</label></td>
          		<td><select name="parentId" class="easyui-combobox" id="saveParentId" style="width:170px;"></select></td>
          	</tr>
          	<tr>
          		<td height="28"><label style="text-align:right;"><font color="red">*</font>角色编码：</label></td>
          		<td><input name="roleCode" class="easyui-validatebox textbox" data-options="required:true" id="saveRoleCode"></td>
          	</tr>
          	<tr>
          		<td height="28"><label style="text-align:right;vertical-align: top;"><font color="red">*</font>角色描述：</label></td>
          		<td><input  name="roleDesc" style="width:240px;height:100px;" class="easyui-textbox" data-options="required:true,multiline:true" id="saveRoleDesc"></td>
          	</tr>
          </table>          
          </div>
         <div class="fitem">
         </div>
    </form>
</div>

<div id="addRoleButtons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addUserToRoleDialog').dialog('close')">取消</a>
</div>

<!--窗口 -->
<div id="addUserToRoleDialog" class="easyui-dialog" style="width:800px;height:auto"
        closed="true"  buttons="#addRoleButtons">
    <div id="addUserToRoleTool" class="easyui-panel" style="border-bottom: 0;">
    <div style="margin:5px">
		角色名称:<input class="easyui-textbox" name="roleName" id="roleName2"/> 
		<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchUserNotRole()">查询</a>
	</div>
		<!-- 操作按钮 -->
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addRoleToUser()">添加用户角色</a>
	</div>
    <table id="addUserToRolegrid" title="" class="easyui-datagrid" 
		style="height:auto;width: auto;"data-options="toolbar:'#addUserToRoleTool'">
	</table>
</div>


<div id="addUserButtons">
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="addUserToRoles()">确认</a>
	
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#addUserDialog').dialog('close')">取消</a>
</div>

<!--窗口 -->
<div id="addUserDialog" class="easyui-dialog" style="width:800px;height:450px"
        closed="true"  buttons="#addUserButtons">
    <div id="addUserTool" class="easyui-panel" style="border-bottom: 0;">
		<!-- 查询条件 -->
		<div style="margin:5px">
			员工账户:<input class="easyui-textbox" name="userName" id="userName"/> 
			员工工号:<input class="easyui-textbox" name="memberId" id="memberId"/> 
			<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
		</div>
		<!-- 操作按钮 -->
	</div>
    <table id="addUsergrid" title="" class="easyui-datagrid" 
		style="height:auto;width: auto;"data-options="toolbar:'#addUserTool'">
	</table>
</div>
</body>

 <script type="text/javascript">
	var userId = 0;
	var roleIds = 0;
	//查询角色没有的用户
	function findUser(){
		var current_tab = $('#grid').datagrid("getChecked");
		if(current_tab.length>1){
			alert("一次只能选中一个角色");
			return;
		}
		if(current_tab.length==0){
			alert("请选择要增加的角色");
			return;
		}
		$("#memberId").textbox('setValue',null);
		$("#userName").textbox('setValue',null);
		roleIds = current_tab[0].pid;
		$('#addUserDialog').dialog('open').dialog('setTitle', "添加用户角色");
		$("#addUserDialog").dialog("move",{top:$(document).scrollTop()+((window.screen.availHeight-134)*0.5-($("#addUserDialog").parent().height()+12)*0.5)}); 
		
		$('#addUsergrid').datagrid('clearSelections'); 
 		$('#addUsergrid').datagrid('clearChecked');
		$("#addUsergrid").datagrid({
		    url: 'getRoleNoUserList.action',
		    method: 'POST',
		    queryParams: { 'rid': roleIds},
		    striped: true,
		    fitColumns: true,
		    singleSelect: false,
		    toolbar: '#addUserTool',
		    rownumbers: true,
		    pagination: true,
		    nowrap: false,
		    showFooter: true,
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck: true,
			checkOnSelect: true,
			onLoadSuccess:function(data){
				clearSelectRows('#addUsergrid');
			},
		    columns: [[
		        { field: 'pid', checkbox: true},
		        { field: 'userName', title: '员工账户', width: 100 },
		        { field: 'memberId', title: '员工工号', width: 100},
		        { field: 'department', title: '部门', width: 200},
		        { field: 'jobTitle', title: '职位', width: 200}
		    ]],
		    loadFilter: function(result) {
		    	var data = result.rows;
		    	var viewData = {'total':result.count,'rows':data}
		    	return viewData;
		    }
		});
		
	}
	// 查询 
	function ajaxSearch() {
		$('#addUsergrid').datagrid('load',{
			userName: $("#userName").val(),
			memberId: $("#memberId").val(),
			rid: roleIds
		});
	}
	/**
	 *查询用户没有的角色
	 */
	function searchUserNotRole(){
		$('#addUserToRolegrid').datagrid('load',{
			roleName: $("#roleName2").val(),
			uid: userId
		});
	}

	//增加用户角色关系
	function addUserToRoles(){
		var current_tab = $('#addUsergrid').datagrid("getChecked");
		if(current_tab.length==0){
			alert("请选择要增加的角色");
			return;
		}
		var pidArray = new Array();
		for (var i = 0; i < current_tab.length; i++) {
			pidArray.push(current_tab[i].pid);
		}
		
		$.ajax({
			type: "POST",
	        url: "addRoleOfUser.action",
	        data:{"userId":pidArray,"roleId":roleIds},
	        dataType: "text",
	        success: function(data){
	        	$('#roleUserGrid').datagrid('reload');
	        	$('#addUsergrid').datagrid('reload');
	        }
		});
		$('#addUsergrid').datagrid("clearChecked");
		$('#addUserDialog').dialog('close');
		
	}
	
	function addRoleUser(){
		var current_tab = $('#roleUserGrid').datagrid("getChecked");
		if(current_tab.length>1){
			alert("一次只能选中一个用户");
			return;
		}
		if(current_tab.length==0){
			alert("请选择要增加的用户");
			return;
		}
		$("#roleName2").textbox('setValue',null);
		$("#addUserToRolegrid").datagrid({
		    url: 'getUserNotRoleList.action',
		    method: 'POST',
		    queryParams: { 'uid': current_tab[0].pid},
		    rownumbers: true,
		    pagination: true,
		    toolbar: '#addUserToRoleTool',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck: true,
			checkOnSelect: true,
			onLoadSuccess:function(data){
				clearSelectRows('#addUserToRolegrid');
			},
		    columns: [[
		        { field: 'pid',checkbox:true,width:50},
		        { field: 'roleName', title: '角色名称', width: 50},
		        { field: 'roleDesc', title: '角色描述', width: 50},
				{ field: 'roleCode', title: '角色编码', width: 50}
		    ]]
		});
		userId = current_tab[0].pid;
		$('#addUserToRoleDialog').dialog('open').dialog('setTitle', "该用户没有的角色");
		$("#addUserToRoleDialog").dialog("move",{top:$(document).scrollTop()+((window.screen.availHeight-134)*0.5-($("#addUserToRoleDialog").parent().height()+12)*0.5)}); 
		
	}
	
	//增加用户角色关系
	function addRoleToUser(){
		var current_tab = $('#addUserToRolegrid').datagrid("getChecked");
		if(current_tab.length==0){
			alert("请选择要增加的角色");
			return;
		}
		var pidArray = new Array();
		for (var i = 0; i < current_tab.length; i++) {
			pidArray.push(current_tab[i].pid);
		}
		
		$.ajax({
			type: "POST",
	        url: "addUserOfRole.action",
	        data:{"roleId":pidArray,"userId":userId},
	        dataType: "text",
	        success: function(data){
	        	$('#addUserToRolegrid').datagrid('reload');
	        	$('#roleUserGrid').datagrid('reload');
	        	$.messager.alert("系统提示","添加角色成功","success"); 
	        }
		});
		$('#addUserToRolegrid').datagrid("clearChecked");
	}
	$(document).ready(function() {
		$('#grid').datagrid({  
		    onClickRow: function (rowIndex, rowData) { 
				$('#searchUserOfRoleNameBtn').attr('onClick', "findUserOfRole("+rowData.pid+")");
				$('#searchRoleNotpermisNameBtn').attr('onClick', "findNotPermisOfRole("+rowData.pid+")");
				$('#searchRolePermisNameBtn').attr('onClick', "findPermisOfRole("+rowData.pid+")");
				
		    	findPermisOfRole(rowData.pid);
				findUserOfRole(rowData.pid);
				findNotPermisOfRole(rowData.pid); 
		    },onLoadSuccess:function(data){
				clearSelectRows('#grid');
			} 
		}) ;
	});
</script>
 