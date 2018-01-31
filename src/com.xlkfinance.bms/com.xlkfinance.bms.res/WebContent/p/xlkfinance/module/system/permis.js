	var permisId=0;
	
	 //弹出新增框
	 function showAddDiv(){
		$('#fm').form('clear');
		$('#dlg').dialog('open').dialog('setTitle', "新增");
		$('#add').show();
		$('#update').hide();
	 }
	 
	 //调用后台新增
	 function savePermis(){
		 var permisName = $.trim($("#savePermisName").val());
		 var permisType = $.trim($("#savePermisType").combobox('getValue'));
		 var permisCode = $.trim($("#savePermisCode").val());
		 var permisDesc = $.trim($("#savePermisDesc").val());
		 if(permisName=="" || permisType==""){
		 		alert("信息输入不完整");
		 		return;
		 }
		var all =  $('#grid').datagrid("getRows");
		for(var i=0;i<all.length;i++){
			if(permisName==all[i].permisName){
				alert("权限名字已经存在，请重新输入");
				return;
			}
		}
		 if(permisType=="菜单权限"){
			permisType="MNU";
		 }else if(permisType=="增删修改"){
			permisType="CDM";
		 }else if(permisType=="数据查询"){
			permisType="QUY";
		 }
		 
		 $.ajax({
			type: "POST",
	        url: "addpermis.action",
	        data: {"permisName":permisName,"permisType":permisType,"permisDesc":permisDesc,"permisCode":permisCode},
	        dataType: "text",
	        success: function(data){	
	        	$('#grid').datagrid("reload");
	        }
		});
		$('#fm').form('clear');
		$('#dlg').dialog('close');
	 }
 
 	//弹出修改框
 	function showUpdateDiv(){
		var current_tab = $('#grid').datagrid("getSelections");
		if(current_tab.length>1){
			$.messager.alert("提示","一次只能选中一个权限","info");
			return;
		}
		if(current_tab.length==0){
			$.messager.alert("提示","请选择要修改的权限","info");
			return;
		}
		if(current_tab[0].permisType == "菜单权限"){
			var c=[{ "id": "CDM", "text": "增删修改" },{ "id": "QUY", "text": "数据查询" },{"id":"MNU","text":"菜单权限"}];
			$('#savePermisType').combobox({
				data:c,    
			    valueField:'id',    
			    textField:'text'  
			});
			//$.messager.alert("提示","菜单权限不能修改","info");
			//return;
		}
		$('#fm').form('clear'); 
		$('#fm').form('load',current_tab[0]);
		$('#update').show();
		$('#add').hide();
		$('#dlg').dialog('open').dialog('setTitle', "修改");
	}
 	
 	//调用后台修改
 	function updatePremis(){
 		var permisName = $.trim($("#savePermisName").val());
	 	var permisType = $.trim($("#savePermisType").combobox('getValue'));
	 	var permisDesc = $.trim($("#savePermisDesc").val());
	 	var permisCode = $.trim($("#savePermisCode").val()); 
	 	var pid = $("#savePermisId").val();
	 	
	 	if(permisName=="" || permisType=="" || permisCode=="" || savePermisId==""){
	 		alert("信息输入不完整");
	 		return;
	 	}
	 	if(permisType=="菜单权限"){
			permisType="MNU";
		 }else if(permisType=="增删修改"){
			permisType="CDM";
		 }else if(permisType=="数据查询"){
			permisType="QUY";
		 }
		$.ajax({
			type: "POST",
	        url: "getPermisByName.action",
	        data: {"permisName":permisName},
	        dataType: "text",
	        success: function(data){	
	        	if(data && data!=""){
	        		var ret =  eval("("+data+")");
	        		if(pid != ret.pid && permisName==ret.permisName){
		        		$.messager.alert("提示","此权限名称 已存在！","info");
		        	}else{
		        		$.ajax({
		        			type: "POST",
		        	        url: "updatepermis.action",
		        	        data: {"pid":pid,"permisName":permisName,"permisType":permisType,"permisDesc":permisDesc,"permisCode":permisCode},
		        	        dataType: "text",
		        	        success: function(data){	
		        	        	$('#grid').datagrid("reload");
		        	        }
		        		});
		        		$('#fm').form('clear');
		        		$('#dlg').dialog('close');
		        	}
	        	}else{
	        		$.ajax({
	        			type: "POST",
	        	        url: "updatepermis.action",
	        	        data: {"pid":pid,"permisName":permisName,"permisType":permisType,"permisDesc":permisDesc,"permisCode":permisCode},
	        	        dataType: "text",
	        	        success: function(data){	
	        	        	$('#grid').datagrid("reload");
	        	        }
	        		});
	        		$('#fm').form('clear');
	        		$('#dlg').dialog('close');
	        	}
	        }
		});
 	}

 	//删除一个权限
 	function deletePermis(){
 		var current_tab = $('#grid').datagrid("getSelections");
		if(current_tab.length==0){
			$.messager.alert("提示","至少选择一个用户","info");
			return;
		}
		$.messager.confirm('确认', '是否确认删除该权限？', function(r){
			if (r){
				var permisIds = [];
				var j = 0;
				for(var i=0; i<current_tab.length; i++){
					if(current_tab[i].permisType == "菜单权限"){
						j++;
					}else{
						permisIds.push(current_tab[i].pid);
					}
				}
				if(j>0){
					$.messager.alert("提示","菜单权限不能删！","info");
				}
				if(permisIds.length==0){
					return;
				}
				for (var i = 0; i < permisIds.length; i++) {
					$.ajax({
						type: "POST",
				        url: "deletePermis.action",
				        data: {"pid":permisIds[i]},
				        dataType: "text",
				        success: function(data){	
				        	$('#grid').datagrid("reload");
				        }
					});
				}
			}
		},"info");
 	}
 	
 	//模糊查询
 	function searchPermis(){
 		$('#grid').datagrid('clearSelections'); 
 		$('#grid').datagrid('clearChecked');
 		var permisName = $.trim($("#permisName").val());
		var permisType = $.trim($("#permisType").combobox('getValue'));
	 	var permisCode = $.trim($("#permisCode").val()); 
	 	if((permisName=="") && permisType=="" && permisCode==""){
	 		$("#grid").datagrid({
			    url: 'permislist.action',
			    method: 'POST',
			    rownumbers: true,
			    singleSelect: true,
			    pagination: true,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    fitColumns:true,
			    singleSelect: false,
				selectOnCheck:true,
				checkOnSelect: true,
				onLoadSuccess:function(data){
					clearSelectRows('#grid');
				},
			    columns: [[
			        { field: 'pid',checkbox:true,width:100},
			        { field: 'permisName', title: '权限名称', width: 100},
			        { field: 'permisType', title: '权限类型', width: 200},
			        { field: 'permisDesc', title: '权限描述', width: 200},
			        { field: 'permisCode', title: '权限编码', width: 200},
			    ]]
			}); 
	 		return;
	 	}
	 	
	 	$("#grid").datagrid({
		    url: 'searchPermisByLike.action',
		    method: 'POST',
		    rownumbers: true,
		    queryParams: {"permisName":permisName,"permisType":permisType,"permisDesc":null,"permisCode":permisCode},
		    singleSelect: true,
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck:true,
			checkOnSelect: true,
			onLoadSuccess:function(data){
				clearSelectRows('#grid');
			},
		    columns: [[
		        { field: 'pid',checkbox:true,width:100},
		        { field: 'permisName', title: '权限名称', width: 100},
		        { field: 'permisType', title: '权限类型', width: 200},
		        { field: 'permisDesc', title: '权限描述', width: 200},
		        { field: 'permisCode', title: '权限编码', width: 200},
		    ]]
		}); 
 	}
 	
 	//查询已有和未有的角色
 	function getRoleOfPermisList(pid){
 		permisId = pid;
 		$("#rolePermisGrid").datagrid({
		    url: 'getRoleOfPermisList.action',
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
		        { field: 'roleDesc', title: '角色描述', width: 200},
				{ field: 'roleCode', title: '角色编码', width: 100}
		    ]]
		}); 
 		
 		$("#roleNotPermisGrid").datagrid({
		    url: 'getNotHaveRoleOfPermisList.action',
		    method: 'POST',
		    rownumbers: true,
		    queryParams: {"pid":pid},
		    singleSelect: true,
		    pagination: true,
		    toolbar: '#roleNotPermisTool',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck:false,
			checkOnSelect: false,
			onLoadSuccess:function(data){
				clearSelectRows('#roleNotPermisGrid');
			},
		    columns: [[
		        { field: 'pid',checkbox:true,width:100},
		        { field: 'roleName', title: '角色名字', width: 100},
		        { field: 'roleDesc', title: '角色描述', width: 200},
				{ field: 'roleCode', title: '角色编码', width: 100}
		    ]]
		});
 	}
 	
 	//删除权限下的角色
 	function removeRolePermis(){
 		var current_tab = $('#rolePermisGrid').datagrid("getSelections");
 		var roleIds = [];
 		for(var i=0; i<current_tab.length; i++){
 			roleIds.push(current_tab[i].pid);
 		}
 		if(roleIds.length==0){
 			alert("请选择要删除的角色");
 			return;
 		}
 		if(permisId==""){
 			alert("系统错误，角色暂时获取不到，请刷新后操作");
 			return;
 		}
 		
 		if (confirm("是否确认删除?")) {
 			for (var i = 0; i < roleIds.length; i++) {
 	 			$.ajax({
 	 				type: "POST",
 	 		        url: "deleteRoleToPermis.action",
 	 		        data: {"roleId":roleIds[i],"permisId":permisId},
 	 		        dataType: "text",
 	 		        success: function(data){
 	 	        		$('#rolePermisGrid').datagrid("reload");
 	 		        	$('#roleNotPermisGrid').datagrid("reload");
 	 		        }
 	 			});
 	 		}
 		}
 	}
 	
 	var flag=false;
 	//添加权限到觉得
 	function addRolePermis(){
 		var current_tab = $('#roleNotPermisGrid').datagrid("getSelections");
 		var roleIds = [];
 		for(var i=0; i<current_tab.length; i++){
 			roleIds.push(current_tab[i].pid);
 		}
 		if(roleIds.length==0){
 			alert("请选择要添加的角色");
 			return;
 		}
 		if(permisId==""){
 			alert("系统错误，角色暂时获取不到，请刷新后操作");
 			return;
 		}
		
		for (var i = 0; i < roleIds.length; i++) {
			$.ajax({
				type: "POST",
		        url: "addRoleToPermis.action",
		        data: {"roleId":roleIds[i],"permisId":permisId},
		        dataType: "text",
		        success: function(data){
		        	$('#roleNotPermisGrid').datagrid("reload");
	        		$('#rolePermisGrid').datagrid("reload");
		        }
			});
 		}
 	}