<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">
//新增
function addItem(){
	window.location.href='${basePath}customerController/editEstTemplate.action';
}

//编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		window.location.href='${basePath}customerController/editEstTemplate.action?pid='+row[0].pid;
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
//删除
function removeItem(){
	  var rows = $('#grid').datagrid('getSelections');
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}// 获取选中的系统用户名 
	 	var pids = "";
		for(var i=0;i<rows.length;i++){
	  		if(i==0){
	  			pids+=rows[i].pid;
	  		}else{
	  			pids+=","+rows[i].pid;
	  		}
	  	}
	 	$.messager.confirm("操作提示","确定删除选择的资信评估模板吗?",
			function(r) {
	 			if(r){
					$.post("deleteEstTemplate.action",{pids : pids}, 
						function(ret) {
						ajaxSearch('#grid','#searchFrom');
						},'json');
	 			}
			});
	  	
}



$(function(){
	setCombobox2("cre_model_type","CUS_TYPE","");
});	

</script>
<body>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="listEstTemplateUrl.action" method="post" id="searchFrom" >
	<!-- 查询条件 -->
	<div style="padding:5px">
	<table>
		<tr>			
			<td align="right" width="80">模板名称：</td>
			<td><input name="modelName"  class="easyui-textbox"  style="width: 180px;" /></td>
			<td align="right" width="80">模板类型：</td>
			<td><select name="modelType" id="cre_model_type" class="easyui-combobox"  style="width:150px;" ></select></td>
			<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 20px;" onclick="ajaxSearch('#grid','#searchFrom')">查询</a></td>
		</tr>
	</table>
	</div>
	</form>
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">编辑</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
	</div>
	
</div>
<table id="grid" title="资信评估模板管理" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'listEstTemplateUrl.action',
	    method: 'get',
	    rownumbers: true,
	    singleSelect: false,
	    pagination: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true">
	<!-- 表头 -->
	<thead><tr>
	    <th data-options="field:'pid',checkbox:true" ></th>
	    <th data-options="field:'value1',width:300,sortable:true"  >模板名称</th>
	    <th data-options="field:'value2',width:100,sortable:true"  >模板类型</th>
	    <th data-options="field:'value3',width:300,sortable:true" >生成时间</th>
	    <th data-options="field:'value4',width:150,sortable:true" >备注</th>
	</tr></thead>
</table>
</body>
