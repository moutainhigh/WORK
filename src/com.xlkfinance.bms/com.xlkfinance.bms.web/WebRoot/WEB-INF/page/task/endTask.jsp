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
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<div id="end_Task_toolbar" class="easyui-panel" style="border-bottom: 0;height: 50px">
		<form id="task_from" name="end_Task_from" >
			<!-- 查询条件 -->
			<div style="margin:10px">
				项目名称：
				<input class="easyui-textbox" name="projectName" id="projectName"/> &nbsp;
				处理时间：
				<input id="executeDttm" name="executeDttm" type="text" class="easyui-datebox">&nbsp;
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="endTaskJsObj.seachEndTaskList()">查询</a>
				&nbsp;&nbsp;
				<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="endTaskJsObj.resetForm()">重置</a>
			</div>
		</form>
	</div>
	<table id="end_Task_grid"></table>
	<div id="end_Task_win" >
    	<table id="process_logging_grid">
    	</table>
    </div> 
    </div>
    </div>
</body>
<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/endTask.js">
</script>
</html>
