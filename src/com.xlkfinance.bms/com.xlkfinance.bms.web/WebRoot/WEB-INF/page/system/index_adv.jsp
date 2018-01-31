<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>

<script type="text/javascript">
	
	//新增
	function addItem() {
		parent.openNewTab("${basePath}sysAdvPicController/editAdvPic.action","新增广告");
	}

	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			parent.openNewTab('${basePath}sysAdvPicController/editAdvPic.action?pid='
					+ row[0].pid,"修改广告",true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}

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
			message = "确认把此广告置为有效?";
		}else{
			message = "确认把此广告置为无效?";
		}
		
		$.messager.confirm("操作提示",message,
				function(r) {
					if (r) {
						$.post("updateAdvStatus.action",
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
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="<%=basePath%>sysAdvPicController/getAllAdvPic.action" method="POST" id="searchFrom"  name="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">广告标题：</td>
				<td>
					<input name="title" class="easyui-textbox" style="width: 200px;" />
				</td>
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							style="margin-left: 120px;"
							onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
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
	</div>

</div>
<div class="perListDiv" style="height:100%;">
<table id="grid" title="广告管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getAllAdvPic.action',
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
			<th data-options="field:'title',width:'15%',sortable:true" >广告标题</th>
			<th data-options="field:'url',width:'10%',sortable:true">广告链接</th>
			<th data-options="field:'pictureUrl',width:'15%',sortable:true">图片链接</th>
			<th data-options="field:'orderIndex',width:'15%',sortable:true">显示顺序</th>
			<th data-options="field:'createDate',width:'20%',sortable:true">创建日期</th>
			<th data-options="field:'caozuo',width:200,formatter:caozuofiter">操作</th>
		</tr>
	</thead>
</table>
</div>
</div>
</body>
