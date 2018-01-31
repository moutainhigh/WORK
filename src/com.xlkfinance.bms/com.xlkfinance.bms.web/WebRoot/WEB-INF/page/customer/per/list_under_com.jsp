<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
var flag='${isLook}';
//新增
function addItem(){
	parent.openNewTab("${basePath}customerController/editComBases.action?acctId=${acctId}&flag=1&type=4","新增个人旗下公司");
}
$(function(){
	if(flag == '3'){
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled', 'disabled');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly', 'readOnly');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
		$('.panel-body .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
});
// 编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		var comId = row[0].pid;
 	   	 parent.openNewTab("${basePath}customerController/editComBases.action?acctId=${acctId}&comId="+comId+"&flag=1&type=5","修改个人旗下公司",true);
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
							$('#grid').datagrid('reload');
							// window.location.href="${basePath}customerController/listPerCredit.action?acctId=${acctId}";
						},'json');
	 			}
			});
	  	
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
	 	$.messager.confirm("操作提示","确定删除选择的子公司信息吗?",
			function(r) {
	 			if(r){
					$.post("deleteUnderCom.action",{pids : pid}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								$("#dlg").dialog('close');
								$("#grid").datagrid("clearChecked");
								$("#grid").datagrid('reload');
						},'json');
	 			}
			});
	  	
}   
function selectUnderCom(){
	$('<div id="xzComCus"/>').dialog({
		href : 'xzUnderCom.action?acctId=${acctId}',
		width : 700,
		height : 400,
		modal : true,
		title : '选择企业客户',
		buttons : [ {
			text : '确认',
			iconCls : 'icon-save',
			handler : function() {
				window.saveComCus();
			
			}	
			},{
				text : '取消 ',
				iconCls : 'icon-cancel',
				handler : function() {
					$("#xzComCus").dialog("close");
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	});
}
//查看业务往来 
function business(){
	 var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			window.location.href ="${basePath}customerController/listPerBusiness.action?acctId="+row[0].pid;
		}else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能操作一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
}
//excel导出
function auditItem(){
	 var rows = $('#grid').datagrid('getSelections');
	 var pids = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}
	window.location.href ="${basePath}customerController/listUnderExcelExportList.action?pids="+pids;
}
//定义一个刷新datagrid的方法，将其保存到window.top中  zhengxiu
window.top["reload_Abnormal_Monitor"]=function(){
	$('#grid').datagrid( "load");
	};
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
	<jsp:include page="cus_per_tab.jsp">
	<jsp:param value="4" name="tab"/>
	</jsp:include>
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-wanglai" plain="true" onclick="business()">业务往来</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="auditItem()">导出Excel</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="selectUnderCom()">选择旗下公司</a>
	</div>
	
</div>
<table id="grid" title="旗下子公司列表" class="easyui-datagrid" 
	style="height:450px;width: auto;"
	data-options="
	    url: 'listUnderComUrl.action?acctId=${acctId }',
	    method: 'post',
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
	    <th data-options="field:'value1',width:200,sortable:true"  >企业名称</th>
	    <th data-options="field:'value3',width:200,sortable:true"  >组织机构代码</th>
	    <th data-options="field:'value4',width:200,sortable:true" >营业执照号</th>
	    <th data-options="field:'value5',width:100,sortable:true" >所用制性质</th>
	    <th data-options="field:'value6',width:100,sortable:true" >法人</th>
	    <th data-options="field:'value7',width:100,sortable:true" >注册资金</th>
	    <th data-options="field:'value8',width:100,sortable:true" >联系电话</th>
	    <th data-options="field:'value9',width:100,sortable:true" >成立日期</th>
	</tr></thead>
</table>
</div>
</div>
</div>
</div>
</body>