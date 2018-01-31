<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">

	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<!-- 查询条件 -->
		 
	<!-- 操作按钮 -->
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="showAddDiv()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="showUpdateDiv()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteMenu()">删除</a>
	</div> 
	
	</div>
	<div class="menuListDiv" style="height:100%">
	<table id="grid" title="菜单管理" class="easyui-treegrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: '${context}/BMS/sysMenuController/pageMenulist.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: false,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
        treeField: 'menuName',
        singleSelect: false,
		selectOnCheck:true,
		checkOnSelect: true,
		onLoadSuccess:function(data){
			clearSelectRows('#grid');
		},
		loadFilter:myLoadFilter
	    ">
	<!-- 表头 -->
		<thead><tr>
		<th data-options="field:'pid',checkbox:true,width:100"  >菜单编号</th>
		<th hidden="true" data-options="field:'parentId',width:100"  ></th>
	    <th data-options="field:'menuName',width:100,sortable:true"  >菜单名称</th>
	    <th data-options="field:'iconCls',width:100,sortable:true"  >菜单样式</th>
	    <th data-options="field:'menuUrl',width:100,sortable:true"  >菜单链接</th>
		</tr></thead>
	</table>
	</div>
</div>
</body>
<!-- 保存 和取消按钮 -->
<div id="dlg-buttons">
    <a id="add" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveAdd()" style="display:none;">保存</a>
    <a id="update" href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveUpdate()"  style="display:none;">保存</a>
    
	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>
<!--窗口 -->
<div id="dlg" class="easyui-dialog" style="width:auto;height:auto;padding:10px 20px"
        closed="true" buttons="#dlg-buttons">
     <form id="fm" method="post">
          <div class="fitem">
          	<input name="pid" class="easyui-validatebox" data-options="required:true" id="pid" type="hidden">	
          	<table>
          		<tr>
          			<td align="right" height="28"><label><font color="red">*</font>父级菜单：</label></td>
          			<td><select id="parentId" name="menuName"  style="width:350px "></select></td>
          		</tr>
          		<tr>
          			<td align="right" height="28"><label><font color="red">*</font>菜单名称：</label></td>
          			<td><input name="menuName" class="easyui-validatebox easyui-textbox" data-options="required:true" id="menuNames" style="width:350px " /></td>
          		</tr>
          		<tr>
          			<td align="right" height="28"><label><font color="red">*</font>菜单链接：</label></td>
          			<td><input name="menuUrl" class="easyui-validatebox easyui-textbox" data-options="required:true" id="menuUrl" style="width:350px " /></td>
          		</tr>
          		<tr>
          			<td align="right" height="28"><label>菜单样式：</label></td>
          			<td><input name="iconCls" class="easyui-validatebox easyui-textbox" data-options="required:false" id="miconCls" style="width:350px " /></td>
          		</tr>
          		<tr>
          			<td align="right" height="28"><label>显示顺序：</label></td>
          			<td><input name="menuIndex" class="easyui-validatebox easyui-textbox" data-options="required:true" id="menuIndex" style="width:350px " /></td>
          		</tr>
          	</table>
          </div>
    </form>
</div>


<script type="text/javascript">
 //模糊查询
 function searchMenu(){
	 var menuName = $("#menuName").val().trim();
	 if(menuName=="" || menuName==null){
		 $("#grid").datagrid({
			    url: 'pageMenulist.action',
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
			    columns: [[
			        { field: 'pid',checkbox:true,width:100},
			        { field: 'menuName', title: '菜单名称', width: 100},
			        { field: 'iconCls', title: '菜单样式', width: 200},
			        { field: 'menuUrl', title: '菜单链接', width: 200},
			    ]]
			}); 
		 
		 return;
	 }
	 
	 $("#grid").datagrid({
		    url: 'searchMenu.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    queryParams: {"menuName":menuName},
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true,
		    singleSelect: false,
			selectOnCheck:true,
			checkOnSelect: true,
		    columns: [[
		        { field: 'pid',checkbox:true,width:100},
		        { field: 'menuName', title: '菜单名称', width: 100},
		        { field: 'iconCls', title: '菜单样式', width: 200},
		        { field: 'menuUrl', title: '菜单链接', width: 200},
		    ]]
		}); 
 }
 
 //删除
function deleteMenu(){
	var current_tab = $('#grid').treegrid("getSelections");
	var menuId = "";
	for(var i=0; i<current_tab.length; i++){
		if(i!=current_tab.length-1){
			menuId =menuId+current_tab[i].pid+",";
		}else if(i==0){
			menuId =menuId+current_tab[i].pid;
		}else if(i==current_tab.length-1){
			menuId =menuId+current_tab[i].pid;
		} 
	}
 
	if(menuId==""){
		$.messager.alert("操作提示","请选择要删除的菜单！","error"); 
		return;
	}
	if (confirm("是否确认删除?")) {
		$.ajax({
			type: "POST",
	        url: "deleteMenu.action",
	        data:{"menuId":menuId},
	        dataType: "text",
	        success: function(data){
	        	$('#grid').treegrid("reload");
	        	if(data>0){
	        		$.messager.alert("操作提示","删除成功！","success"); 
	        	}else{
	        		$.messager.alert("操作提示","删除失败！","error"); 
	        	}
	        	
	        }
		});
	}
}
 
//弹出新增的DIV
function showAddDiv(){
	var current_tab = $('#grid').treegrid("getSelections");
	
	$('#fm').form('clear');
	$('#dlg').dialog('open').dialog('setTitle', "新增菜单");
	$('#add').show();
	$('#update').hide();
	
	if(current_tab.length>0){
		appendSelect(current_tab[0].pid);
	}else{
		appendSelect();
	}
}

function appendSelect(parentId){
	var opion = "";
	//查询所有的菜单
	$.ajax({
		type: "POST",
        url: "menulist.action",
        dataType: "json",
        success: function(data){	
        	for(var i=0; i<data.length; i++){
        		if(data[i].pid==parentId){
        			opion = "<option selected value='"+data[i].pid+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].menuName+"</option>";
        		}else{
        			opion = "<option value='"+data[i].pid+"'>&nbsp;&nbsp;&nbsp;&nbsp;"+data[i].menuName+"</option>";
        		}        		
        		$("#parentId").append(opion);
    		}
        }
	});
	
}
//保存新增
function saveAdd(){
	var parentId =$("#parentId").val();
	var menuName =$("#menuNames").textbox('getValue');
	var iconCls =$("#miconCls").textbox('getValue');
	var menuUrl =$("#menuUrl").textbox('getValue');
	var menuIndex =$("#menuIndex").textbox('getValue');
	
	//查询所有的菜单
	$.ajax({
		type: "POST",
        url: "addmenu.action",
        data:{"parentId":parentId,"menuName":menuName,"iconCls":iconCls,"menuUrl":menuUrl,"menuIndex":menuIndex},
        dataType: "text",
        success: function(data){	
        	$('#grid').treegrid("reload");
        	 if(data==1 || data=="1"){
        		 $.messager.alert("操作提示","新增菜单成功！","success"); 
        	 }else{
        		 $.messager.alert("操作提示","新增菜单失败！","error"); 
        	 }
        }
	});
	$('#fm').form('clear');
	$('#dlg').dialog('close');
}

function myLoadFilter(data,parentId){
	function setData(){
		var todo = [];
		for(var i=0; i<data.length; i++){
			todo.push(data[i]);
		}
		while(todo.length){
			var node = todo.shift();
			if (node.children){
				node.state = 'closed';
				node.children1 = node.children;
				node.children = undefined;
				todo = todo.concat(node.children1);
			}
		}
	}
	
	setData(data);
	var tg = $(this);
	var opts = tg.treegrid('options');
	opts.onBeforeExpand = function(row){
		if (row.children1){
			tg.treegrid('append',{
				parent: row[opts.idField],
				data: row.children1
			});
			row.children1 = undefined;
			tg.treegrid('expand', row[opts.idField]);
		}
		return row.children1 == undefined;
	};
	return data;
}

//弹出修改层
function showUpdateDiv(){
	var current_tab = $('#grid').treegrid("getSelections");
	if(current_tab.length>1){
		alert("一次只能选中一个菜单");
		return;
	}
	
	if(current_tab.length==0){
		alert("请选择要修改的菜单");
		return;
	} 
	appendSelect(current_tab[0].parentId);
	$('#fm').form('clear');
	$('#fm').form('load',current_tab[0]);
	$('#dlg').dialog('open').dialog('setTitle', "修改菜单");
	$('#update').show();
	$('#add').hide();
}

//保存修改信息
function saveUpdate(){
	var parentId = $("#parentId").val();
	var menuName = $("#menuNames").textbox('getValue');
	//var iconCls1 ='';
	//debugger;
	var iconCls = $("#miconCls").textbox('getValue');
	var menuUrl = $("#menuUrl").textbox('getValue');
	var menuIndex = $("#menuIndex").textbox('getValue');
	
	var pid = $("#pid").val();
	if(parentId=="" || parentId==null || menuName == null || menuName=="" || menuUrl=="" || menuUrl==null|| menuIndex=="" || menuIndex==null){
		$.messager.alert("操作提示","信息输入不完整！","error"); 
		return;
	}
	if(iconCls==null){
		iconCls="";
	} 
	
	$.ajax({
		type: "POST",
        url: "updateMenu.action",
        data:{"parentId":parentId,"menuName":menuName,"iconCls":iconCls,"menuUrl":menuUrl,"menuIndex":menuIndex,"pid":pid},
        dataType: "text",
        success: function(data){	
        	$('#grid').treegrid("reload");
        	 if(data==1 || data=="1"){
        		 $.messager.alert("操作提示","修改菜单成功！","success"); 
        	 }else{
        		 $.messager.alert("操作提示","修改菜单失败！","error"); 
        	 }
        }
	});
}

</script>
 