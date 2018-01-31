<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">

//新增
function addItem(){
	window.location.href='${basePath}customerController/editEstInfo.action?acctId=${acctId}&cusType=${cusType}&comId=${comId}';
}

// 编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		window.location.href='${basePath}customerController/editEstInfo.action?pid='+row[0].pid+'&acctId=${acctId}&cusType=${cusType}&comId=${comId}';
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
	 	$.messager.confirm("操作提示","确定删除资信评估吗?",
			function(r) {
	 		var cusType='${cusType}';
	 			if(r){
					$.post("deleteEstInfo.action",{pids : pids}, 
						function(ret) {
						if(cusType=='1'){
							window.location.href="${basePath}customerController/listPerEst.action?acctId=${acctId}";
						}
						else{
							window.location.href="${basePath}customerController/listComEst.action?acctId=${acctId}";
						}
						},'json');
	 			}
			});
	  	
}


</script>
<body>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
	</div>
	
</div>

<table id="grid" title="${cusType==1?'个人资信评估':'企业资信评估' }" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'listEstInfoUrl.action?cusType=${cusType }&cusId=${acctId}',
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
	    <th data-options="field:'value4',width:200,sortable:true" >评估模板</th>
	    <th data-options="field:'value5',width:200,sortable:true" >评估百分制总分</th>
	    <th data-options="field:'value6',width:100,sortable:true" >资信评级</th>
	    <th data-options="field:'value8',width:100,sortable:true" >评估人</th>
	    <th data-options="field:'value9',width:100,sortable:true" >评估日期</th>
	</tr></thead>
</table>
</body>