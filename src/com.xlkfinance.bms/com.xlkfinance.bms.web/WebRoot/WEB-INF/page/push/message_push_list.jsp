<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/tree_common.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据权限列表</title>
<script language="javascript">
//查询 
function ajaxSearch() {
	
	$('#grid').datagrid('load', {
		userName : $("#userName").val(),
		title: $("#title").val()
	});
	$('#grid').datagrid('clearChecked');
}

</script>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
			<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="listMessagePush.action" method="post" id="searchFrom"
					name="searchFrom">
					<!-- 查询条件 -->
					<div style="margin: 5px">
						用户名:<input class="easyui-textbox" name="userName" id="userName" />
						标题:<input class="easyui-textbox" name="title" id="title" />
						<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							onclick="ajaxSearch()">查询</a>
					</div>
				</form>
			</div>
			<div style="height:100%">
			<table id="grid" title="消息推送列表 " class="easyui-datagrid"
				style="height: 100%; width: 100%;"
				data-options="
				url: 'listMessagePush.action',
			    method: 'POST',
			    rownumbers: true,
			    singleSelect: false,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    toolbar: '#toolbar',
			    idField: 'id',
			    onLoadSuccess:function(data){
		    		clearSelectRows('#grid');
		   		},
			    fitColumns:true">
				<!-- 表头 -->
				<thead>
					<tr>
						<th height="30" data-options="field:'id',checkbox:true"></th>
						<th data-options="field:'title',sortable:true">标题</th>
						<th data-options="field:'content',sortable:true">内容</th>
						<th data-options="field:'userName',sortable:true">用户</th>
						<th data-options="field:'sendTime',sortable:true">推送时间</th>
					</tr>
				</thead>
			</table>
			</div>
	</div>
</body>
</html>