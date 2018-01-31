<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
</head>
<body>
	<div id="task_toolbar" class="easyui-panel" style="border-bottom: 0;height: 15%">
	<!-- <form action="seachTaskList.action" method="post" id="task_from" name="task_from" > -->
		<form id="task_from" name="task_from" >
			<!-- 查询条件 -->
			<div style="margin:10px">
				任务名称:
					<input class="easyui-textbox" name="taskName" id="taskName"/> &nbsp;
				当前任务内容:
					<input class="easyui-textbox" name="presentTaskMsg" id="presentTaskMsg"/> &nbsp;
				任务状态:
					<input class="easyui-combobox" name="taskState" id="taskState"/>
				<br/><br/>
				分配时间:
					<input id="startDate" name="startDate" type="text" class="easyui-datebox">&nbsp; 至 &nbsp;
					<input id="endDate" name="endDate" type="text" class="easyui-datebox">&nbsp;&nbsp;
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="seachTaskList()">查询</a>
			</div>
		</form>
		<!-- 操作按钮 -->
	</div>
	<table id="task_manage_grid" title="任务管理" ></table>
</body>
<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/taskManage.js"></script>
</html>
