<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/system/role.js"></script>

<style type="text/css">
</style>


<div id="roleDiv" style="width: 49%; float: left;">
	<div id="scroll-bar-div">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<!-- 操作按钮 -->
			<div style="padding-bottom: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="confirms();">确认</a>
			</div>

		</div>

		<table id="grid" title="" class="easyui-datagrid"
			style="height: auto; width: 100%;"
			data-options="
		    url: '${context}/BMS/systemRoleController/rolelist.action',
		    method: 'POST',
		    rownumbers: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    singleSelect: true,
		    onLoadSuccess:function(data){
				clearSelectRows('#grid');
			},
			onClickRow:function(rowIndex,rowData){
				findPermisOfRole(rowData.pid);
			}">
			<!-- 表头 -->
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true,width:40"></th>
					<th data-options="field:'roleName',width:80,sortable:true">角色名称</th>
					<th data-options="field:'roleDesc',width:120,sortable:true">角色描述</th>
					<th data-options="field:'roleCode',width:160,sortable:true">角色编码</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
<script type="text/javascript">
	function confirms() {
		var row = $('#grid').datagrid('getSelections');
		var pid = row[0].pid;

		//$("#superioridValue_id").val(pid);
		///parent.removePanel();
	}
</script>
