<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div id="scroll-bar-div">
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
	<table id="grid" title="磁铁菜单管理" class="easyui-treegrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: '${basePath}sysMetroUiController/findSysMetroUiPage.action',	    
			    method: 'POST',
			    rownumbers: true,
			    singleSelect: true,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    fitColumns:true,	    
		        treeField: 'menuName',
		        singleSelect: true,
				selectOnCheck:true,
				checkOnSelect: true,
				loadFilter:myLoadFilter
	    ">
	<!-- 表头 -->
		<thead><tr>
		<th data-options="field:'pid',checkbox:true,width:100"  >菜单编号</th>
	    <th data-options="field:'menuName',width:100,sortable:true"  >磁铁菜单名称</th>
	    <th data-options="field:'actionUrl',width:100,sortable:true"  >菜单链接</th>
	    <th data-options="field:'locationId',width:100,sortable:true"  >菜单顺序</th>
		</tr></thead>
	</table>
	</div>
</div>
 </div>
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
          			<td align="right" height="28"><label><font color="red">*</font>菜单名称：</label></td>
          			<td>
          				 <select id="menuName" name="menuName" class="easyui-combobox" data-options="url:'${basePath}sysMenuController/getRootMenuList.action',valueField:'pid',textField:'menuName',width:350,required:true">
              			</select>
          			</td>
          		</tr>
          		
          		<tr>
          			<td align="right" height="28"><label><font color="red">*</font>子菜单：</label></td>
          			<td>
          				<select id="menuId" name="menuId" class="easyui-combobox" data-options="valueField:'pid',textField:'menuName',width:350,required:true">
              			</select>
          			</td>
          		</tr>
          		<tr>
          			<td align="right" height="28"><label>显示顺序：</label></td>
          			<td><input name="locationId" class="easyui-validatebox textbox" data-options="required:true" id="locationId" style="width:350px " /></td>
          		</tr>
          	</table>
          	
          </div>
    </form>
</div>

<script type="text/javascript">
$(function(){
	$('#menuName').combobox({
	      onSelect: function () {
	    	  var url = '${basePath}sysMenuController/selectChildMenuByPid.action?pid=' + $('#menuName').combobox('getValue');
	    	  $('#menuId').combobox('reload', url);
	      }
	  });
});
 //删除
function deleteMenu(){
	var current_tab = $('#grid').treegrid("getSelections");
	var pid = "";
	for(var i=0; i<current_tab.length; i++){
		if(i!=current_tab.length-1){
			pid =current_tab[i].pid+",";
		}else if(i==0){
			pid =current_tab[i].pid;
		}else if(i==current_tab.length-1){
			pid =current_tab[i].pid;
		} 
	}
 
	if(pid==""){
		$.messager.alert("操作提示","请选择要删除的菜单！","error"); 
		return;
	}
	if (confirm("是否确认删除?")) {
		var url = "${basePath}sysMetroUiController/deleteSysMetroUi.action";
		$.post(url, {'pid':pid},
		   function(data){
			$('#grid').treegrid("reload");
        	if(data>0){
        		$.messager.alert("操作提示","删除成功！","success"); 
        	}else{
        		$.messager.alert("操作提示","删除失败！","error"); 
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
	
}

//保存新增
function saveAdd(){
	var menuName = $("#menuName").combobox('getText');
	var menuId = $("#menuId").combobox('getValue');
	var locationId = $("#locationId").val();
	if( menuName == null || menuName=="" || locationId=="" || locationId==null){
		$.messager.alert("操作提示","信息输入不完整！","error"); 
		return;
	}
	$.ajax({
		type: "POST",
        url: "${basePath}sysMetroUiController/insertSysMetroUi.action",
        data:{"menuName":menuName,"menuId":menuId,"locationId":locationId},
        dataType: "text",
        success: function(data){	
        	$('#grid').treegrid("reload");
        	 if(data==1 || data=="1"){
        		 $.messager.alert("操作提示","新增磁铁菜单成功！","success"); 
        	 }else{
        		 $.messager.alert("操作提示","新增磁铁菜单失败！","error"); 
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
	var pid = current_tab[0].parentId;
	var url = '${basePath}sysMenuController/selectChildMenuByPid.action?pid='+pid;
    $('#menuId').combobox('reload', url);
	$('#fm').form('clear');
	$('#fm').form('load',current_tab[0]);
	$('#dlg').dialog('open').dialog('setTitle', "修改菜单");
	$('#update').show();
	$('#add').hide();
}

//保存修改信息
function saveUpdate(){
	var menuName = $("#menuName").combobox('getText');
	var menuId = $("#menuId").combobox('getValue');
	var locationId = $("#locationId").val();
	var pid = $("#pid").val();
	if(menuName == null || menuName=="" || menuId=="" || menuId==null|| locationId=="" || locationId==null){
		$.messager.alert("操作提示","信息输入不完整！","error"); 
		return;
	}
	if(status==null){
		status="";
	} 
	
	$.ajax({
		type: "POST",
        url: "${basePath}sysMetroUiController/updateSysMetroUi.action",
        data:{"menuName":menuName,"menuId":menuId,"locationId":locationId,"pid":pid},
        dataType: "text",
        success: function(data){	
        	$('#grid').treegrid("reload");
        	 if(data==1 || data=="1"){
        		 $.messager.alert("操作提示","修改菜单成功！","success",function(){
     					$('#grid').treegrid("reload");
        				$('#dlg').dialog('close');
        		 }); 
        	 }else{
        		 $.messager.alert("操作提示","修改菜单失败！","error",function(){
     					$('#grid').treegrid("reload");
        				$('#dlg').dialog('close');
        		 }); 
        	 }
        }
	});
}

</script>
</body>
 