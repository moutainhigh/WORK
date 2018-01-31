<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>
<script type="text/javascript">

//查询 
function repayList() {
	$('#searcFrom').form('submit', {
		success : function(data) {
			$('#grid').datagrid('loadData', eval("{" + data + "}"));
		}
	});
}

function recordDetail(value,row,index){
	return '<textarea cols="85"  rows="4" readonly="readonly" disabled="disabled" >'+value+'</textarea>';
}
</script>
<div id="roleDiv" style="width:100%;">
	<!-- 操作按钮 -->

	<table id="grid" title="催收记录" class="easyui-datagrid" 
	style="height:auto;width: auto;"
	data-options="
	    url: 'getCollectingRecord.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: true,
	    singleOnCheck: true,
		selectOnCheck: false,
		checkOnSelect: false
	    ">
	<!-- 表头 -->
		<thead><tr>
		 	<th data-options="field:'cycleNum',width:80" align="center">期数</th>
		    <th data-options="field:'reminderDttm',width:100" align="center">催收时间</th>
		    <th data-options="field:'reminderUserId',width:100" align="center">催收人</th>
		    <th data-options="field:'creditAmt',width:100" align="center">催收方式</th>
		    <th data-options="field:'reminderSubject',width:100" align="center">标题</th>
		    <th data-options="field:'reminderMsg',formatter:recordDetail,width:650" align="center">催收内容</th>
		</tr></thead>
	</table>
</div>

