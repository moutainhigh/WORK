<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
var flag='${isLook}';
//新增
function addItem(){
	window.location.href='${basePath}customerController/editPerCredit.action?acctId=${acctId}&perId=${perId}';
}

// 编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		window.location.href='${basePath}customerController/editPerCredit.action?pid='+row[0].pid+'&acctId=${acctId}&perId=${perId}';
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
//删除
function removeItem(){
	  var rows = $('#grid').datagrid('getSelections');
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}// 获取选中的系统用户名 
	 	var pids = "";
		for(var i=0;i<rows.length;i++){
	  		if(i==0){
	  			pids+=rows[i].pid;
	  		}else{
	  			pids+=","+rows[i].pid;
	  		}
	  	}
	 	$.messager.confirm("操作提示","确定删除选择的征信记录吗?",
			function(r) {
	 			if(r){
					$.post("deletePerCredit.action",{pids : pids}, 
						function(ret) {
							window.location.href="${basePath}customerController/listPerCredit.action?acctId=${acctId}";
						},'json');
	 			}
			});
	  	
}
$(function(){
	if(flag == '3'){
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled', 'disabled');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly', 'readOnly');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
		$('.panel-body .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
});

</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
	<jsp:include page="cus_per_tab.jsp">
	<jsp:param value="5" name="tab"/>
	</jsp:include>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
	</div>
	
</div>
<table id="grid" title="征信记录列表" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'listPerCreditUrl.action?perId=${perId}',
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
	    <th data-options="field:'pid',checkbox:true"></th>
	    <th data-options="field:'value1',width:100,sortable:true" >征信报告编号</th>
	    <!-- <th data-options="field:'value2',width:100,sortable:true" >查询日期</th> -->
	    <th data-options="field:'value3',width:200,sortable:true" >报告查询日期</th>
	    <th data-options="field:'value4',width:200,sortable:true" >银行信贷账号数</th>
	    <th data-options="field:'value5',width:200,sortable:true" >银行信贷余额</th>
	</tr></thead>
</table>

</div>
</div>
</div>
</div>
</body>