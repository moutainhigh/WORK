<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 小科的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
	
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}orgAssureFundFlowController/editFundFlowInfo.action?editType=3&pid="
					+ row[0].pid+"&cooperationId="+row[0].applyId,"查看保证金变更信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//账户状态
	function formatterStatus(val,row){
		if(val == 1){
			return "待审核";
		}else if(val == 10){
			return "审核通过";
		}else if(val == 20){
			return "审核不通过";
		}
		
	}
	
	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].status == 1){
				url = '<%=basePath%>orgAssureFundFlowController/editFundFlowInfo.action?editType=2&pid='
					+ row[0].pid+"&cooperationId="+row[0].applyId;
				parent.openNewTab(url,"审核保证金变更",true);
			}else{
				$.messager.alert("操作提示", "此申请已审核,请重新选择！", "error");
			}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
	
	$(document).ready(function(){
		$('#grid').datagrid({  
			 onDblClickRow: function (rowIndex, rowData){
				 parent.openNewTab("${basePath}orgAssureFundFlowController/editFundFlowInfo.action?editType=3&pid="
							+ rowData.pid+"&cooperationId="+rowData.applyId,"查看保证金变更信息",true);
			 }
		 });
	});
</script>
<title>保证金变更管理</title>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getAllFundFlow.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right"  height="25">机构名称：</td>
				<td colspan="2"><input name="orgName" class="easyui-textbox" style="width: 200px;" /></td>
				<td width="80" align="right">统一社会信用代码：</td>
				<td colspan="2"><input name="orgCode" class="easyui-textbox" style="width: 200px;" /></td>
			</tr>
			<tr>
				<td width="80" align="right">联系人：</td>
				<td colspan="2"><input name="contact" class="easyui-textbox" style="width: 200px;" /></td>
				<td width="80" align="right" >审核状态：</td>
				<td>
					<select class="select_style easyui-combobox" width="180px;" editable="false" name="status">
						<option value="-1">--请选择--</option>
						<option value="1" <c:if test="${orgCooperateInfo.status==1 }">selected </c:if>>待审核    </option>
						<option value="10" <c:if test="${orgCooperateInfo.status==2 }">selected </c:if>>审核通过    </option>
						<option value="20" <c:if test="${orgCooperateInfo.status==3 }">selected </c:if>>审核不通过    </option>
					</select>
				</td>
				<td colspan="3">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 120px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="UPDATE_ORG_FUNDFLOW,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		</shiro:hasAnyRoles>
	</div>
</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="保证金变更管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getAllFundFlow.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	    
	<!--
	 表头 -->
	<thead>
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'orgName',width:80,sortable:true">机构名称</th>
			<th data-options="field:'orgCode',width:80,sortable:true">统一社会信用代码</th>
			<th data-options="field:'contact',width:80,sortable:true">联系人</th>
			<th data-options="field:'phone',width:80,sortable:true">联系电话</th>
			<th data-options="field:'createdDateTime',width:120,sortable:true">申请时间</th>
			<th data-options="field:'oldAssureMoney',formatter:formatAomMoney,width:80">变更前保证金</th>
			<th data-options="field:'curAssureMoney',formatter:formatAomMoney,width:80">变更后保证金</th>
			<th data-options="field:'status',width:50,formatter:formatterStatus">申请状态</th>
		</tr>
	</thead>
</table>
</div>

</div>


</body>
</html>