<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>

<script type="text/javascript">

	//查询 
	function ajaxSearch(){
		var pageNumber=$('#grid').datagrid('options')['pageSize'];
		$('#rows').val(pageNumber);
		$('#searchFrom').form('submit',{
	        success:function(data){
	        	var str = JSON.parse(data);
	           	$('#grid').datagrid('loadData',str);
	           	$('#grid').datagrid('clearChecked');
	       }
	    });
	};
	
	function formatterStatus(val,row){
		if(val == 1){
			return "启用";
		}else if(val == 2){
			return "禁用";
		}
	}
	
	function caozuofiter(value, row, index) {
		var status = row.status;
		var changeStatus = "";
		if(status == 1){
			changeStatus = "<a href='javascript:changeStatus(2,"+row.pid+")'><font color='blue'>禁用</font></a>";
		}else{
			changeStatus = "<a href='javascript:changeStatus(1,"+row.pid+")'><font color='blue'>启用</font></a>";
		}

		return changeStatus;
	}
	
	function changeStatus(status,pid){
		var message = "";
		if(status == 1){
			message = "确认启用该机构?";
		}else{
			message = "确认禁用该机构?";
		}
		
		$.messager.confirm("操作提示",message,
				function(r) {
					if (r) {
						$.post("updateStatus.action",
										{
											pid : pid,
											status : status
										},
										function(ret) {
											ajaxSearch();
										}, 'json');
					}
				});
	}
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
	//新增
	function addItem() {
		parent.openNewTab("${basePath}capitalOrgInfoController/toEditCapital.action?editType=1","新增资金机构");
	}

	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			parent.openNewTab('${basePath}capitalOrgInfoController/toEditCapital.action?editType=2&pid='
					+ row[0].pid,"修改资金机构",true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab('${basePath}capitalOrgInfoController/toEditCapital.action?editType=3&pid='
					+ row[0].pid,"查看资金机构信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	$(document).ready(function(){
		$('#grid').datagrid({  
			 onDblClickRow: function (rowIndex, rowData){
				 parent.openNewTab('${basePath}capitalOrgInfoController/toEditCapital.action?editType=3&pid='
							+ rowData.pid,"查看资金机构信息",true);
			 }
		 });
	});
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getCapitalList.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">机构名称：</td>
				<td>
					<input name="orgName" class="easyui-textbox" style="width: 200px;" />
				</td>
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							style="margin-left: 120px;"
							onclick="ajaxSearch()">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');"
						iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">

			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addItem()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
	</div>

</div>
<div class="perListDiv" style="height:100%;">
<table id="grid" title="资金机构管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getCapitalList.action',
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
			<th data-options="field:'orgName',width:'15%',sortable:true" >机构名称</th>
			<th data-options="field:'orgCode',width:'10%',sortable:true">统一社会信用代码</th>
			<th data-options="field:'loanMoneyTotal',formatter:formatAomMoney,width:'15%',sortable:true">放款总金额</th>
			<th data-options="field:'status',width:'15%',formatter:formatterStatus,sortable:true">状态</th>
			<th data-options="field:'createDate',formatter:convertDateTime,width:'20%',sortable:true">创建日期</th>
			<th data-options="field:'caozuo',width:200,formatter:caozuofiter">操作</th>
		</tr>
	</thead>
</table>
</div>
</div>
</body>
