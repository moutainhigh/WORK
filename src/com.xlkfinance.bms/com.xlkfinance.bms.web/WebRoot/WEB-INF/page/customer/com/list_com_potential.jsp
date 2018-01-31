<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
//查询 
function pajaxSearch(){
	$('#grid').datagrid('load',{
		acctName:$.trim($('#acctName').textbox('getValue')),
		certNumber:$.trim($('#certNumber').textbox('getValue')),
		acctType:$('#acctType').combobox('getValue')
		
	})

};
//跳转到 添加页面
function addItem(){
	   $('<div id="editPotential"/>').dialog({
			href : '${basePath}customerController/editPotential.action',
			width : 850,
			height : 320,
			modal : true,
			title : '新增潜在客户',
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
						$("#editPotential").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
	   
};
	//编辑
	function editItem(){
	   var row = $('#grid').datagrid('getSelections');
	   var s_pid=row[0].value7;//角色pid
	   var currUserId='${currUser.pid}';
	   var type=true;
	   $.ajax({
	 		url : '${basePath}customerController/validateIsPermissions.action?spid='
	 			+ s_pid
	 			+ '&currUserId='
	 			+ currUserId,
	 		type : 'post',
	 		cache: false,
	 		success : function(result) {
	 			var ret = eval("("+result+")");
	 			var flags=ret.header["flags"]; 
	 			if(flags==0){
	 				alert("您没有修改潜在客户的权限！");
	 				type=false;
	 			}
	 			else if(type){
	 				if (row.length == 0) {
	 			 		$.messager.alert("操作提示","请选择数据","error");
	 					return;
	 				}
	 			 	if (row.length == 1) {
	 			 		 var pid=row[0].pid;
	 			 		  $('<div id="editComDebt"/>').dialog({
	 			 				href : '${basePath}customerController/editPotential.action?pid='+pid,
	 			 				width : 890,
	 			 				height : 420,
	 			 				modal : true,
	 			 				title : '编辑潜在客户信息 ',
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
	 			 							$("#editComDebt").dialog('close');
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
	 					alert("操作提示","请选择数据","error");
	 				}
	 			}		
	 		}
	 	});
	   
}
	//删除
	  function removeItem(){
		  var rows = $('#grid').datagrid('getSelections');
		  var s_pid=rows[0].value7;//角色pid
		   var currUserId='${currUser.pid}';
		   var type=true;
		   $.ajax({
		 		url : '${basePath}customerController/validateIsPermissions.action?spid='
		 			+ s_pid
		 			+ '&currUserId='
		 			+ currUserId,
		 		type : 'post',
		 		cache: false,
		 		success : function(result) {
		 			var ret = eval("("+result+")");
		 			var flags=ret.header["flags"]; 
		 			if(flags==0){
		 				alert("您没有删除潜在客户的权限！");
		 				type=false;
		 			}
		 			else if(type){
		 				if ( rows.length == 0 ) {
		 			 		$.messager.alert("操作提示","请选择数据","error");
		 					return;
		 				}// 获取选中的系统用户名 
		 			 	var pid = "";
		 				for(var i=0;i<rows.length;i++){
		 			  		if(i==0){
		 			  			pid+=rows[i].pid;
		 			  		}else{
		 			  			pid+=","+rows[i].pid;
		 			  		}
		 			  	}
		 			 	$.messager.confirm("操作提示","确定删除选择的潜在客户吗?",
		 					function(r) {
		 			 			if(r){
		 							$.post("deleteCusComPotential.action",{pid : pid}, 
		 								function(ret) {
		 									//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
		 										$("#dlg").dialog('close');
		 										$("#grid").datagrid("clearChecked");
		 										$("#grid").datagrid('reload');
		 								},'json');
		 			 			}
		 					});
		 			}		
		 		}
		 	});
		  
	  }
</script>
<body>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="listComPotential.action" method="post" id="searchFrom" >
	<!-- 查询条件 -->
	<div style="padding:5px">
		<table>
		<tr>
			<td align="right" width="80" height="25">客户名称：</td>
			<td><input name="acctName" id="acctName"  class="easyui-textbox"  style="width: 180px;" /></td>
			<td align="right" width="80" height="25">证件号码：</td>
			<td><input name="certNumber" id="certNumber"  class="easyui-textbox"  style="width: 180px;" /></td>
		</tr>
		<tr>
		   <td align="right" width="120">客户类型：</td>
			<td colspan="2"><select name="acctType" id="acctType" class="easyui-combobox"  style="width:150px;" >
				<option value="0">--请选择-- </option>
				<option value="1">个人 </option>
				<option value="2">企业 </option>
				</select></td>
				<td>
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 20px;" onclick="pajaxSearch()">查询</a>
				<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
		</tr>
		</table>
	</div>
	</form>
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
	</div>
	
</div>
<div style="height:100%">
<table id="grid" title="潜在客户管理" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: 'listComPotential.action',
	    method: 'get',
	    rownumbers: true,
	    singleSelect: false,
	    pagination: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
		selectOnCheck: true,
		checkOnSelect: true">
	<!-- 表头 -->
	<thead><tr>
	    <th data-options="field:'pid',checkbox:true,width:100"></th>
	    <th data-options="field:'value1',width:100,sortable:true"  >客户名称</th>
	    <th data-options="field:'value2',width:100,sortable:true"  >客户类型</th>
	    <th data-options="field:'value3',width:200,sortable:true" >客户等级</th>
	    <th data-options="field:'value4',width:200,sortable:true" >证件号码</th>
	    <th data-options="field:'value5',width:200,sortable:true" >联系电话</th>
	    <th data-options="field:'value6',width:200,sortable:true" >备注</th>
	    <th data-options="field:'value7',width:200,sortable:true" hidden="true"></th>
	</tr></thead>
</table>
</div>
</body>
