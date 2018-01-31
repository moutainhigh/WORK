<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>
<script type="text/javascript">
	//获取路径
	var url = decodeURI(window.location.href);
	var arr = url.split("=");
	var projectId = arr[1];
	$.ajax({
		type : "POST",
		url : "getCollectionRecordlist.action",
		data : {
			"projectId" : projectId
		},
		dataType : "text",
		success : function(data) {
			$('#rcGrid').datagrid('reload');
		}
	});

	function recordDetail(value, row, index) {
		return '<textarea cols="85"  rows="4" readonly="readonly" disabled="disabled" >'
				+ value + '</textarea>';
	}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div id="roleDiv" style="width: 100%;height: 100%;">
	<div id="scroll-bar-div">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<!-- 操作按钮 -->
		</div>
		<div class="repaymentCollectDiv" style="height: 100%">
			<table id="rcGrid" title="还款催收记录查询" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
		url: 'getCollectionRecordlist.action?projectId='+projectId,
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    singleSelect: true,
	    singleOnCheck: true,
		selectOnCheck: false,
		checkOnSelect: false
	    ">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'cycleNum',width:80" align="center">期数</th>
						<th data-options="field:'reminderDt',width:100" align="center">催收时间</th>
						<th data-options="field:'reminderUser',width:100" align="center">催收人</th>
						<th data-options="field:'subject',width:120" align="center">标题</th>
						<th
							data-options="field:'content',formatter:recordDetail,width:650"
							align="center">催收内容</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</div>
</div>
</body>
