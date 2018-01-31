<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">

//新增
function addItem(){
	//window.location.href='${basePath}customerController/editCusPerson.action?acctId=${acctId}&relationType=${relationType}&comId=${comId}';
	
	$('<div id="addper"/>').dialog({
		href : '${basePath}customerController/editCusPerson.action?acctId=${acctId}&relationType=${relationType}&comId=${comId}',
		width : 790,
		height : 300,
		modal : true,
		title : '关系人(非配偶)信息',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				window.save();
			}	
			},{
				text : '取消 ',
				iconCls : 'icon-cancel',
				handler : function() {
					$("#addper").dialog('close');
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	});

}

// 编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		var pid=row[0].pid;
 		//window.location.href='${basePath}customerController/editCusPerson.action?pid='+row[0].pid+'&acctId=${acctId}&relationType=${relationType}&comId=${comId}';
 		$('<div id="addper"/>').dialog({
 			href : '${basePath}customerController/editCusPerson.action?pid='+pid+'&acctId=${acctId}&relationType=${relationType}&comId=${comId}',
 			width : 790,
 			height : 300,
 			modal : true,
 			title : '关系人(非配偶)信息',
 			buttons : [ {
 				text : '保存',
 				iconCls : 'icon-save',
 				handler : function() {
 					window.save();
 				}	
 				},{
 					text : '取消 ',
 					iconCls : 'icon-cancel',
 					handler : function() {
 						$("#addper").dialog('close');
 						}
 					} ],
 			onClose : function() {
 				$(this).dialog('destroy');
 			},
 			onLoad : function() {
 			}
 		});
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
	 	$.messager.confirm("操作提示","确定删除选择人信息吗?",
			function(r) {
	 			if(r){
					$.post("deleteCusPerson.action",{pids : pids}, 
						function(ret) {
						    var relationType=${relationType};
						    if(relationType==5){
						    	//window.location.href ="${basePath }customerController/listPerPerson.action?acctId=${acctId}&relationType=5"
								// 重新加载数据
								$("#grid").datagrid('load', {});
								// 清空所选择的行数据
								clearSelectRows("#grid");
						    }
						    else{
								window.location.href="${basePath}customerController/listComPerson.action?acctId=${acctId}&relationType=${relationType}";
						    }
						},'json');
	 			}
			});
	  	
}
function formatNumber(value,row,index){
	if(value){
		return accounting.formatMoney(value, "", 2, ",", ".");
	}else{
		return "-";
	}
}

</script>

<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
	</div>
	
</div>
<table id="grid" title="${relationType==5?'关系人信息':'企业控制人信息' }" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'listPersonUrl.action?acctId=${acctId }&relationType=${relationType}',
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
	    <th data-options="field:'value1',width:80,sortable:true" >姓名</th>
	    <th data-options="field:'value11',width:80,sortable:true" >身份证号</th>
	    <th data-options="field:'value5',width:80,sortable:true" >手机号码</th>
	    <th data-options="field:'value14',formatter:formatNumber,width:80,sortable:true" >产权占比(%)</th>
	    <th data-options="field:'value12',width:80,sortable:true" >关系</th>
	</tr></thead>
</table>
