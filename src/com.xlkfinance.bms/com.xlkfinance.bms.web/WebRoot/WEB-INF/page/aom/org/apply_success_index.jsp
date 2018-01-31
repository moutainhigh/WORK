<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 小科的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
	
	$(function(){
		$('#grid').datagrid({ 
			 onDblClickRow: function (rowIndex, rowData) {
				 parent.openNewTab("${basePath}orgCooperatCompanyApplyController/lookupCooperate.action?pid="
							+ rowData.pid+"&param='refId':'"+rowData.pid+"','projectName':'1'"+",'projectId':'"+rowData.pid+"'","查看合作申请详情",true);
			 }
		});
	});
	
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}orgCooperatCompanyApplyController/lookupCooperate.action?pid="
					+ row[0].pid+"&param='refId':'"+row[0].pid+"','projectName':'1'"+",'projectId':'"+row[0].pid+"'","查看合作申请详情",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
</script>
<title>机构合作信息</title>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getAllApplySuccess.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right"  height="25">机构名称：</td>
				<td colspan="2"><input name="orgName" class="easyui-textbox" style="width: 200px;" /></td>
				<td width="80" align="right">统一社会信用代码：</td>
				<td colspan="2"><input name="orgCode" class="easyui-textbox" style="width: 200px;" /></td>
				<td colspan="3">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 120px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
	</div>
</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="机构合作信息" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getAllApplySuccess.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	    
	<!--
	 表头 -->
	<thead>
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'orgName',width:80,sortable:true">机构名称</th>
			<th data-options="field:'orgCode',width:80,sortable:true">统一社会信用代码</th>
			<th data-options="field:'partnerName',width:80,sortable:true">所属合伙人</th>
			<th data-options="field:'contact',width:80,sortable:true">联系人</th>
			<th data-options="field:'phone',width:80,sortable:true">联系电话</th>
			<th data-options="field:'applyDate',width:120,sortable:true">申请时间</th>
			<th data-options="field:'creditLimit',formatter:formatAomMoney,width:80">授信额度</th>
			<th data-options="field:'singleUpperLimit',formatter:formatAomMoney,width:80">单笔上限</th>
			<th data-options="field:'rate',formatter:formatAomMoney,width:80">费率(%)</th>
			<th data-options="field:'assureMoney',formatter:formatAomMoney,width:80">保证金</th>
			<th data-options="field:'startTime',width:80">合作开始时间</th>
			<th data-options="field:'endTime',width:80">合作结束时间</th>
		</tr>
	</thead>
</table>
</div>

</div>


</body>
</html>