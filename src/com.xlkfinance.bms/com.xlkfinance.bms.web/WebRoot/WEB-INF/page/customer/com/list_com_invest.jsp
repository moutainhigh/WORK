<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
   //跳转到 添加页面
   function addItem(){
	   
	   $('<div id="editComInvest"/>').dialog({
			href : '${basePath}customerController/editComInvest.action?comId=${comId}&acctId=${acctId}',
			width : 890,
			height : 405,
			modal : true,
			title : '新增对外投资信息',
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
						$("#editComInvest").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
	   
   };
   function editItem(){
	   var row = $('#grid').datagrid('getSelections');
	   if (row.length == 0) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
	 	if (row.length == 1) {
	 		 var pid=row[0].pid;
	 		  $('<div id="editComInvest"/>').dialog({
	 				href : '${basePath}customerController/editComInvest.action?comId=${comId}&acctId=${acctId}&pid='+pid,
	 				width : 890,
	 				height : 405,
	 				modal : true,
	 				title : '编辑对外投资信息 ',
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
	 							$("#editComInvest").dialog('close');
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
					$.post("deleteComInvest.action",{pid : pid}, 
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
<jsp:param value="10" name="tab"/>
</jsp:include>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
	</div>
	
</div>
<table id="grid" title="对外投资" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'searchComInvest.action?comId=${comId}',
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
	    <th data-options="field:'pid',checkbox:true,width:100" id="pid"></th>
	    <th data-options="field:'value1',width:100,sortable:true"  >投资对象</th>
	    <th data-options="field:'value2',width:100,sortable:true"  >投资方式</th>
	    <th data-options="field:'value3',align:'right',width:200,sortable:true,formatter:formatMoney" >投资金额(万)</th>
	    <th data-options="field:'value4',width:200,sortable:true" >投资起始日期</th>
	    <th data-options="field:'value5',width:200,sortable:true" >投资截止日期</th>
	    <th data-options="field:'value6',align:'right',width:200,sortable:true,formatter:formatMoney" >预计担保期内企业支出<br>的投资金额(万)</th>
	    <th data-options="field:'value7',align:'right',width:200,sortable:true,formatter:formatMoney" >预计担保期内企业<br>投资收益金额(万)</th>
	</tr></thead>
</table>
</div>
</div>
</div>
</div>
</body>