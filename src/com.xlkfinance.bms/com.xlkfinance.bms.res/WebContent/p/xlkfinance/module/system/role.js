/**
 * 
 */
var permisRoleId=0;
var userRoleId=0;
 
//根据名字模糊查询角色
function searchRole(){
	$('#grid').datagrid('clearSelections'); 
	$('#grid').datagrid('clearChecked');
	var roleName = $("#roleName").val().trim();
	if(roleName==""){
		$("#grid").datagrid({
		    url: 'rolelist.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: true,
			selectOnCheck: false,
			checkOnSelect: false,
			onLoadSuccess:function(data){
				clearSelectRows('#grid');
				clearSelectRows('#rolePermisGrid');
				clearSelectRows('#roleNotPermisGrid');
			},
			onClickRow:function(rowIndex,rowData){
				$('#searchUserOfRoleNameBtn').attr('onClick', "findUserOfRole("+rowData.pid+")");
				$('#searchRoleNotpermisNameBtn').attr('onClick', "findNotPermisOfRole("+rowData.pid+")");
				$('#searchRolePermisNameBtn').attr('onClick', "findPermisOfRole("+rowData.pid+")");
		    	findPermisOfRole(rowData.pid);
				findUserOfRole(rowData.pid);
				findNotPermisOfRole(rowData.pid); 
			},
		    columns: [[
		        { field: 'pid',checkbox:true,width:50},
		        { field: 'roleName', title: '角色名字', width: 100},
		        { field: 'roleDesc', title: '角色描述', width: 200},
				{ field: 'roleCode', title: '角色编码', width: 100}
		    ]]
		});
		return;
	}
	$("#grid").datagrid({
	    url: 'seachRoleByRoleName.action',
	    method: 'POST',
	    queryParams: { 'roleName': roleName,'roleDesc':null },
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck: false,
		checkOnSelect: false,
		onLoadSuccess:function(data){
			clearSelectRows('#grid');
		},
		onClickRow:function(rowIndex,rowData){
			$('#searchUserOfRoleNameBtn').attr('onClick', "findUserOfRole("+rowData.pid+")");
			$('#searchRoleNotpermisNameBtn').attr('onClick', "findNotPermisOfRole("+rowData.pid+")");
			$('#searchRolePermisNameBtn').attr('onClick', "findPermisOfRole("+rowData.pid+")");
	    	findPermisOfRole(rowData.pid);
			findUserOfRole(rowData.pid);
			findNotPermisOfRole(rowData.pid); 
		},
	    columns: [[
	        { field: 'pid',checkbox:true,width:50},
	        { field: 'roleName', title: '角色名字', width: 100},
	        { field: 'roleDesc', title: '角色描述', width: 200},
			{ field: 'roleCode', title: '角色编码', width: 100}
	    ]]
	});
	
}

//新增
function addItem(){
	$('#fm').form('clear');
	$('#dlg').dialog('open').dialog('setTitle', "新增");
	$("#dlg").dialog("move",{top:$(document).scrollTop()+((window.screen.availHeight-134)*0.5-($("#dlg").parent().height()+12)*0.5)}); 
	
	$('#saveParentId').combobox({ 
		url:'getAllRoles.action',    
	    valueField:'pid',    
	    textField:'roleName',
	    value:0
	});  
	$('#add').show();
	$('#update').hide();
}

//保存
function savaUpdate(){
	var roleName = $("#saveRoleName").val();
	var roleDesc = $("#saveRoleDesc").val();
	var roleCode = $("#saveRoleCode").val();
	var parentId = $("#saveParentId").combobox('getValue');
	var current_tab = $('#grid').datagrid("getChecked");
	if(current_tab.length>1){
		alert("一次只能选中一个角色");
		return;
	}
	if(current_tab.length==0){
		alert("请选择要修改的角色");
		return;
	}
	
	var all =  $('#grid').datagrid("getRows");
	for(var i=0;i<all.length;i++){
		if(roleName==all[i].roleName && all[i].roleName !=current_tab[0].roleName){
			alert("角色名字已经存在，请重新输入");
			return;
		}
	}
	if(roleName=="" || roleDesc=="" || roleCode==""){
		alert("信息输入不完整");
		return;
	}
	$.ajax({
		type: "POST",
        url: "updateRole.action",
        data: {"roleName":roleName,"roleCode":roleCode,"parentId":parentId, "roleDesc":roleDesc,"pid":current_tab[0].pid},
        dataType: "json",
        success: function(data){
        	$('#grid').datagrid("reload");
        },
        error:function(){
        	alert("修改失败"); 
        }
	});
	$("#grid").datagrid('reload');
	$('#fm').form('clear');
	$('#dlg').dialog('close');
	$("#grid").datagrid("clearChecked");
}

//更新角色信息
function updateRole(){
	var current_tab = $('#grid').datagrid("getChecked");
	if(current_tab.length>1){
		alert("一次只能选中一个用户");
		return;
	}
	
	if(current_tab.length==0){
		alert("请选择要修改的用户");
		return;
	}
	$('#fm').form('clear');
	$('#fm').form('load',current_tab[0]);
	$('#update').show();
	$('#add').hide();
	$('#dlg').dialog('open').dialog('setTitle', "修改");
	$("#dlg").dialog("move",{top:$(document).scrollTop()+((window.screen.availHeight-134)*0.5-($("#dlg").parent().height()+12)*0.5)}); 
	
}
//保存成功
function saveItem(){
	var roleName = $("#saveRoleName").val().trim();
	var roleDesc = $("#saveRoleDesc").val().trim();
	var roleCode = $("#saveRoleCode").val().trim();
	var parentId = $("#saveParentId").combobox('getValue');
	if(roleName=="" || roleName==null || roleDesc=="" || roleDesc==null || roleCode=="" || roleCode==null){
		$.messager.alert("提示","信息输入不完整","info");
		return;
	}
	var all =  $('#grid').datagrid("getRows");
	for(var i=0;i<all.length;i++){
		if(roleName==all[i].roleName){
			$.messager.alert("提示","角色名字已经存在，请重新输入","info");
			return;
		}
	}
	$.ajax({
		type: "POST",
        url: "addRole.action",
        data: {"roleName":roleName,"roleDesc":roleDesc,"roleCode":roleCode,"parentId":parentId},
        dataType: "json",
        success: function(data){
			if(data=="code_error"){
				$.messager.alert("提示","角色编码已经存在，请重新输入","info");
				return;
			}else{
				$('#fm').form('clear');
				$('#dlg').dialog('close');
				$("#grid").datagrid("clearChecked");
				$("#grid").datagrid("reload");
			}
        	 
        	
        }
	});
	
	
}

//删除角色
function removeItem(){
	var ids = [];
	var current_tab = $('#grid').datagrid("getChecked");
	for(var i=0; i<current_tab.length; i++){
		ids.push(current_tab[i].pid);
	}
	if(ids.length==0){
		$.messager.alert("提示","请选择要删除的角色","info");
		return;
	}
	for (var i = 0; i < current_tab.length; i++) {
		if(current_tab[i].roleName=="系统管理员" || current_tab[i].roleName=="超级管理员"){
			$.messager.alert("提示","系统管理员不能删除","info");
			return;
		}
	}
	if (confirm("是否确认删除角色?")) { 
		$.ajax({
			type: "POST",
			url: "deleteRole.action",
			data: {"pid":ids},
			dataType: "text",
			success: function(data){	
				$('#grid').datagrid("reload");
				$('#rolePermisGrid').datagrid("reload");
				$('#roleNotPermisGrid').datagrid("reload");
				if(data=='-1' || data==-1){
					$.messager.alert("提示","该角色下面存在用户，禁止取删除","info");	
				}else if(data=='1' || data==1){
					$.messager.alert("提示","删除角色成功","info");
				}else{
					$.messager.alert("提示","删除角色失败，请稍后再试","info");
				}
			}
		});
	}
	
	 
}    

//查询用户拥有的权限
function findPermisOfRole(rid){
	//$("#grid").datagrid("clearSelections");
	//debugger;
	var permisName = $("#rolePermisName").val();
	if(rid=="" || null==rid){
		$.messager.alert("提示","用户选择错误","info");
		return;
	} 
	permisRoleId = rid;
	$("#rolePermisGrid").datagrid({
		    url: 'getSysPermissionList.action',
		    method: 'POST',
		    //queryParams: { 'pid': rid },
		    queryParams: { 'pid': rid,'permisName': permisName},
		    striped: true,
		    fitColumns: true,
		    singleSelect: false,
		    toolbar: '#rolePermisTool',
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
				clearSelectRows('#rolePermisGrid');
			},
		    columns: [[
		        { field: 'pid', checkbox: true},
		        { field: 'permisType', title: '类别', width: 70,sortable:true },
		        { field: 'permisName', title: '权限', width: 120,sortable:true},
		        { field: 'permisDesc', title: '描述', width: 210,sortable:true}
		    ]]
	});
//	findNotPermisOfRole(rid);
//	findUserOfRole(rid);
	
}
//取消角色的某个权限
function removeRolePermis(){
	var permisIds = [];
	var current_tab = $('#rolePermisGrid').datagrid("getChecked");
	for(var i=0; i<current_tab.length; i++){
		permisIds.push(current_tab[i].pid);
	}
	
	if(permisIds.length==0){
		$.messager.alert("提示","请选择要取消的权限","info");
		return;
	}
	
	var roleId = permisRoleId;
	if (confirm("是否确认取消?")) { 
		for (var i = 0; i < permisIds.length; i++) {
			$.ajax({
				type: "POST",
		        url: "deletePermisOfRoleById.action",
		        data: {"permisId":permisIds[i],"roleId":roleId},
		        dataType: "text",
		        success: function(data){	
		        	$('#rolePermisGrid').datagrid("reload");
		        	$('#roleNotPermisGrid').datagrid("reload");
		        	permisIds=[];
		        }
			});
		}
		
	}
	
	 
} 

//查询角色未拥有的权限
function findNotPermisOfRole(rid){
	var permisName = $("#roleNotpermisName").val();
	// 必须重置 datagr 选中的行 
	$("#roleNotPermisGrid").datagrid("clearChecked");
	$("#roleNotPermisGrid").datagrid({
	    url: 'searchRoleNotHavePermis.action',
	    method: 'POST',
	    queryParams: { 'pid': rid,'permisName': permisName},
	    striped: true,
	    fitColumns: true,
	    singleSelect: false,
	    toolbar: "#roleNotPermisTool",
	    rownumbers: true,
	    pagination: true,
	    nowrap: false,
	    showFooter: true,
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    onLoadSuccess:function(data){
			clearSelectRows('#roleNotPermisGrid');
		},
	    columns: [[
	        { field: 'pid', checkbox: true},
	        { field: 'permisType', title: '类别', width: 70,sortable:true },
	        { field: 'permisName', title: '权限', width: 120,sortable:true},
	        { field: 'permisDesc', title: '描述', width: 210,sortable:true}
	    ]]
	});
}

//给角色添加权限
function addRolePermis(){
	var current_tab = $('#roleNotPermisGrid').datagrid("getChecked");
	var rid = permisRoleId;
	var permisIds = [];
	
	for(var i=0; i<current_tab.length; i++){
		permisIds.push(current_tab[i].pid);
	}
	if(permisIds.length==0){
		$.messager.alert("提示","请选择要添加的权限","info");
		return;
	}
	if(rid==""){
		$.messager.alert("提示","系统错误，角色暂时获取不到，请刷新后操作","info");
		return;
	}
	
	for (var i = 0; i < permisIds.length; i++) {
		$.ajax({
			type: "POST",
	        url: "addPermisToRole.action",
	        data: {"permisId":permisIds[i],"roleId":rid},
	        dataType: "text",
	        success: function(data){
        		$('#rolePermisGrid').datagrid("reload");
	        	$('#roleNotPermisGrid').datagrid("reload");
	        }
		});
	}
	
}

//查询角色下的用户
function findUserOfRole(rid){
	
	console.log("findUserOfRole rid:"+rid);
	
//	debugger;
	var current_tab = $('#grid').datagrid("getChecked");
	var realName = $("#realName").val();
	if(rid=="" || null==rid){
		$.messager.alert("提示","用户选择错误","info");
		return;
	}
	userRoleId = rid;
	$("#roleUserGrid").datagrid({
	    url: 'getRoleUserList.action',
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

//取消用户与角色的关系
function removeRoleUser(){
	var current_tab = $('#roleUserGrid').datagrid("getChecked");
	var rid = userRoleId;
	var userIds = [];
	for(var i=0; i<current_tab.length; i++){
		userIds.push(current_tab[i].pid);
	}
	
	if(userIds.length==0){
		$.messager.alert("提示","请选择要取消的用户","info");
		return;
	}
	if(rid==""){
		$.messager.alert("提示","系统错误，角色暂时获取不到，请刷新后操作","info");
		return;
	}
	if (confirm("是否确认取消?")) {
		for (var i = 0; i < userIds.length; i++) {
			$.ajax({
				type: "POST",
				url: "deleteUserOfRole.action",
				data: {"userId":userIds[i],"roleId":rid},
				dataType: "text",
				success: function(data){
					$('#roleUserGrid').datagrid("reload");
					
				}
			});
		}
	}
	
	$('#roleUserGrid').datagrid("clearChecked");
}

$(document).ready(function(){
	var height = document.body.clientHeight;
	var width = document.body.clientWidth;
	$('#roleDiv').css('width',width*0.5);
	$('#RolePermis').css('width',(width*0.5-18));
	$('#grid').datagrid({ 
		height:(height*0.5),
		width:(width*0.5)
	});
	$('#roleUserGrid').datagrid({ 
		height:(height*0.5),
		width:(width*0.5)
	});
	$('#rolePermisGrid').datagrid({ 
		height:(height*0.5),
		width:(width*0.5)-18
	});
	$('#roleNotPermisGrid').datagrid({ 
		height:(height*0.5),
		width:(width*0.5)-18
	});
});