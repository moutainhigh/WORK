<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">
//新增
function addItem(){
	window.location.href='${basePath}customerController/editEstInfo.action?flag=1';
}

// 编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
	var ctypeName=row[0].value3;
	var userName='${currUser.realName}';
	var estInfo = row[0].value8;
	var ctype=2;
	if(ctypeName=='个人'){
		ctype=1;
	}
	if(userName==estInfo){
		if (row.length == 1) {
	 		window.location.href='${basePath}customerController/editEstInfo.action?pid='+row[0].pid+'&acctId='+row[0].value1+'&cusType='+ctype+'&flag=1';
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	else{
		alert("您没权限操作此数据！");
	}
 	
}
//删除
function removeItem(){
	  var rows = $('#grid').datagrid('getSelections');
	  var userName='${currUser.realName}';
	  var estInfo = rows[0].value8;
 	  if ( rows.length == 0 ) {
 	  		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
 	  // 获取选中的系统用户名 
	 	var pids = "";
		for(var i=0;i<rows.length;i++){
	  		if(i==0){
	  			pids+=rows[i].pid;
	  		}else{
	  			pids+=","+rows[i].pid;
	  		}
	  	}
		if(userName==estInfo){
		 	$.messager.confirm("操作提示","确定删除资信评估信息吗?",
					function(r) {
			 			if(r){
							$.post("deleteEstInfo.action",{pids : pids}, 
								function(ret) {
								$("#grid").datagrid("clearChecked");
								$("#grid").datagrid('reload');
								},'json');
			 			}
					});
		}
		else{
			alert("您没权限删除此数据！");
		}
	
	  	
}


$(function(){
	setTemplateCombobox("cre_model_type","");
	setEstPersonCombobox("est_person","");
	$('#est_lv').combobox({    
		url:'${basePath }sysLookupController/getSysLookupValByLookType.action?lookupType=CREDIT_LEVEL',    
	    valueField:'lookupVal',    
	    textField:'lookupDesc',
	    onLoadSuccess: function(rec){
        }
	});  
});	
function setTemplateCombobox(inputId,selVal){
	$('#'+inputId).combobox({    
		url:'${basePath }customerController/getTemplateName.action',    
	    valueField:'pid',    
	    textField:'modelName',
	    onLoadSuccess: function(rec){
	    	if(selVal!=''){
	    		$("#"+inputId).combobox('setValue',selVal);
	    	}
        }
	});  
}

function setEstPersonCombobox(inputId,selVal){
	$('#'+inputId).combobox({    
		url:'${basePath }sysUserController/getAllUsers.action',    
	    valueField:'pid',    
	    textField:'realName',
	    onLoadSuccess: function(rec){
	    	if(selVal!=''){
	    		$("#"+inputId).combobox('setValue',selVal);
	    	}
        }
	});  
}

</script>
<body>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="listEstInfoUrl.action" method="post" id="searchFrom" >
	<!-- 查询条件 -->
	<div style="padding:5px">
		<table>
		<tr>
			<td align="right" width="80" height="25">客户名称：</td>
			<td><input name="cusName"  class="easyui-textbox"  style="width: 180px;" /></td>
			<td align="right" width="120">客户类型：</td>
			<td colspan="2"><select name="cusType"  class="easyui-combobox"  style="width:150px;" >
				<option value="0">--请选择-- </option>
				<option value="1">个人 </option>
				<option value="2">企业 </option>
				</select></td>
		</tr>
		<tr>
			<td align="right" width="80" height="25">评&nbsp;估&nbsp;人：</td>
			<td><select name="estPerson" id="est_person" class="easyui-combobox"  style="width:180px;" ></select></td>
			<td align="right" width="120">评估模板类型：</td>
			<td colspan="2"><select name="templateId" id="cre_model_type" class="easyui-combobox"  style="width:150px;" ></select></td>
		</tr>
		<tr>
			<td align="right" width="80" height="25">信用评级：</td>
			<td><select name="estLv" id="est_lv" class="easyui-combobox"  style="width: 180px;" ></select></td>
			<td align="right" width="120">评估得分：</td>
			<td><input name="startScore"  class="easyui-textbox"  style="width: 60px;" />&nbsp;~&nbsp;<input name="endScore"  class="easyui-textbox"  style="width: 60px;" /></td>
			<input type="hidden" name="page" value="1">
			<input type="hidden" name="rows" id="rows">
			<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 20px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
				<a href="#" onclick="javascript:$('#searchFrom').form('reset');"
						iconCls="icon-clear" class="easyui-linkbutton">重置</a>
			</td>
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
<table id="grid" title="资信评估管理" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'listEstInfoUrl.action',
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
	    <th data-options="field:'value2',width:80,sortable:true"  >客户名称</th>
	    <th data-options="field:'value3',width:80,sortable:true"  >客户类型</th>
	    <th data-options="field:'value4',width:100,sortable:true" >所用评估模板</th>
	    <th data-options="field:'value5',width:80,sortable:true" >评估百分制总分</th>
	    <th data-options="field:'value6',width:80,sortable:true" >资信评级</th>
	    <th data-options="field:'value7',width:100,sortable:true" >评级含义</th>
	    <th data-options="field:'value8',width:80,sortable:true" >评估人</th>
	    <th data-options="field:'value9',width:80,sortable:true" >评估日期</th>
	</tr></thead>
</table>
</body>
