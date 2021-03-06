<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
//跳转到 添加页面
function addItem(){
	   $('<div id="editComDebt"/>').dialog({
			href : '${basePath}customerController/editDebtRight.action?comId=${comId}&acctId=${acctId}&debtType=${debtType}',
			width : 890,
			height : 420,
			modal : true,
			title : '新增债权管理信息',
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
	   
};
	//编辑
	function editItem(){
	   var row = $('#grid').datagrid('getSelections');
	   if (row.length == 0) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
	 	if (row.length == 1) {
	 		 var pid=row[0].pid;
	 		  $('<div id="editComDebt"/>').dialog({
	 				href : '${basePath}customerController/editDebtRight.action?comId=${comId}&acctId=${acctId}&pid='+pid,
	 				width : 890,
	 				height : 420,
	 				modal : true,
	 				title : '编辑债权人信息 ',
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
	//删除
	  function removeItem(){
		  var rows = $('#grid').datagrid('getSelections');
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
		 	$.messager.confirm("操作提示","确定删除选择的此批数据吗?",
				function(r) {
		 			if(r){
						$.post("deleteCusComDebt.action",{pid : pid}, 
							function(ret) {
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
									$("#dlg").dialog('close');
									$("#grid").datagrid("clearChecked");
									$("#grid").datagrid('reload');
							},'json');
		 			}
				});
		  	
	  }
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
<jsp:include page="cus_com_tab.jsp">
<jsp:param value="8" name="tab"/>
</jsp:include>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
	</div>
	
</div>
<table id="grid" title="债权情况" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'searchlistComDebtRight.action?debtType=${debtType}&comId=${comId}',
	    method: 'get',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true">
	<!-- 表头 -->
	<thead><tr>
	    <th data-options="field:'pid',checkbox:true,width:100"></th>
	    <th data-options="field:'value1',width:100,sortable:true"  >债权人</th>
	    <th data-options="field:'value2',width:100,sortable:true"  >借款金额(万)</th>
	    <th data-options="field:'value3',width:200,sortable:true" >已还款金额(万)</th>
	    <th data-options="field:'value4',width:200,sortable:true" >借款余额(万)</th>
	    <th data-options="field:'value5',width:200,sortable:true" >借款截止日期</th>
	    <th data-options="field:'value6',width:200,sortable:true" >偿还方式</th>
	    <th data-options="field:'value7',width:200,sortable:true" >凭证方式</th>	
	</tr></thead>
</table>
</div>
</div>
</div>
</div>
</body>