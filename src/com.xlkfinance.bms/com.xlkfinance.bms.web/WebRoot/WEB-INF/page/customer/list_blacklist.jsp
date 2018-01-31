<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript">
function revokeBlacklist(cusStatus){
	var rows = $('#grid').datagrid('getSelections');
	var pids="";
	var acctIds="";
	var type=true;
	if (rows.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	}
	
	for (var i = 0; i < rows.length; i++) {
		if (i == 0) {
			pids += rows[i].value10;
			acctIds += rows[i].pid;
		} else {
			pids += "," + rows[i].value10;
			acctIds += "," + rows[i].pid;
		}
	}
	
	$('<div id="addblacklist"/>').dialog({
			href : '${basePath}customerController/editRevokeReason.action?cusStatus=2&blacklistRefuseId='+pids+'&acctIds='+acctIds,
			width : 500,
			height : 260,
			modal : true,
			title : '撤销黑名单',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.save();
				}	
				},{
					text : '取消 ',
					iconCls : 'icon-cancel',
					handler : function() {
						$("#addblacklist").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});	
};
function cusDetail(){
  var row = $('#grid').datagrid('getSelections');
  if (row.length == 1) {
		if(row[0].value3=='企业客户'){
			parent.openNewTab('${basePath}customerController/editComBases.action?acctId='+row[0].pid+"&comId="+row[0].value11+'&flag='+1,"黑名单企业客户");
		}else{
			parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='+row[0].pid+'&flag='+1,"黑名单个人客户");
		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	} 
}
function loadBlackRefuse(){
	ajaxSearch('#grid','#searchFrom');
}
//查询 
function ajaxSearch(){
	var pageNumber=$('#grid').datagrid('options')['pageSize'];
	$('#rows').val(pageNumber);
	$('#searchFrom').form('submit',{
        success:function(data){
        	var str = JSON.parse(data);
           	$('#grid').datagrid('loadData',str);
        }
    });
}
</script>
<body>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="listBlacklistUrl.action" method="post" id="searchFrom" >
	<!-- 查询条件 -->
	<div style="padding:5px">
		<table>
			<tr>
				<td>客户姓名：</td>
				<td><input name="cusName"  class="easyui-textbox"  style="width: 120px;" /></td>
				<td width="70" align="right">客户类型：</td>
				<td>
					<select name="cusType"  class="easyui-combobox"  style="width: 120px;" >
						<option value="0">请选择</option>
						<option value="1">个人客户</option>
						<option value="2">企业客户</option>
					</select>
				</td>
				<td width="70" align="right">证件号码：</td>
				<td><input name="certNumber" class="easyui-textbox" style="width:120px;"  /></td>
				<input type="hidden" name="page" value="1">
					<input type="hidden" name="rows" id="rows">
				<td>
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 120px;" onclick="ajaxSearch()">查询</a>
		<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton" >重置</a>
				</td>
			</tr>
		</table>
		
	</div>
	</form>
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="cusDetail()">客户详情</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-undo" plain="true" onclick="revokeBlacklist()">撤销黑名单</a>
	</div>
	
</div>
<div class="blackListDiv" style="height:100%">
<table id="grid" title="黑名单客户管理" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: 'listBlacklistUrl.action',
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
	    <th data-options="field:'value2',width:300,sortable:true"  >客户名称</th>
	    <th data-options="field:'value3',width:100,sortable:true"  >客户类型</th>
	    <th data-options="field:'value4',width:300,sortable:true" >证件类型</th>
	    <th data-options="field:'value5',width:150,sortable:true" >证件号码</th>
	    <th data-options="field:'value6',width:150,sortable:true" >联系电话</th>
	    <th data-options="field:'value7',width:200,sortable:true" >原因说明</th>
	    <th data-options="field:'value8',width:150,sortable:true" >客户经理</th>
	    <th data-options="field:'value12',width:150,sortable:true" hidden="true"></th>
	</tr></thead>
</table>
</div>
</div>
</div>
</body>