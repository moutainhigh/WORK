<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 合伙人的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">

	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].reviewStatus == 20){
	 			$.messager.alert("操作提示","此合伙人合作申请已通过,请重新选择！","error");
			}else{
	 			parent.openNewTab("${basePath}orgPartnerInfoController/editPartnerCooperate.action?editType=2&pid="
						+ row[0].pid,"修改合伙人合作信息",true);
	 		}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}orgPartnerInfoController/editPartnerCooperate.action?editType=3&pid="
					+ row[0].pid,"查看合伙人合作信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//账户状态
	function formatterStatus(val,row){
		if(val == 1){
			return "启用";
		}else if(val == 2){
			return "禁用";
		}
	}
	//认证状态
	function formatterAuditStatus(val,row){
		if(val == 1){
			return "未认证";
		}else if(val == 2){
			return "认证中";
		}else if(val == 3){
			return "已认证";
		}
	}
	//合作状态
	function formatterReviewStatus(val,row){
		if(val == 1){
			return "未申请";
		}else if(val == 10){
			return "审核中";
		}else if(val == 20){
			return "审核通过";
		}else if(val == 30){
			return "审核不通过";
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
	
	//修改合伙人合作信息
	function editPartnerCooperation(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
	 			parent.openNewTab("${basePath}orgPartnerInfoController/editPartnerCooperate.action?editType=2&pid="
						+ row[0].pid,"修改合伙人合作信息",true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	$(document).ready(function(){
		$('#grid').datagrid({  
			 onDblClickRow: function (rowIndex, rowData){
				 parent.openNewTab("${basePath}orgPartnerInfoController/editPartnerCooperate.action?editType=3&pid="
							+ rowData.pid,"查看合伙人合作信息",true);
			 }
		 });
	});
</script>
<title>合伙人合作管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getPartnerCooperationInfo.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right">合伙人名称：</td>
				<td colspan="2"><input name="realName" class="easyui-textbox" style="width: 200px;" /></td>
				<td width="80" align="right">联系电话：</td>
				<td colspan="2"><input name="phone" class="easyui-textbox" style="width: 200px;" /></td>
			</tr>
			<tr>
				<td width="120" align="right" >合作申请状态：</td>
				<td>
					<select class="select_style easyui-combobox" width="180px;" editable="false" name="reviewStatus">
						<option value="-1">--请选择--</option>
						<option value="1" <c:if test="${orgPartner.reviewStatus==1 }">selected </c:if>>未申请    </option>
						<option value="10" <c:if test="${orgPartner.reviewStatus==10 }">selected </c:if>>审核中    </option>
						<option value="20" <c:if test="${orgPartner.reviewStatus==20 }">selected </c:if>>审核通过    </option>
						<option value="30" <c:if test="${orgPartner.reviewStatus==30 }">selected </c:if>>审核不通过    </option>
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
		<shiro:hasAnyRoles name="UPDATE_PARTNER_COOPERATE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		</shiro:hasAnyRoles>
			<!-- <a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="openAuditStatus()">审核</a> -->
		<shiro:hasAnyRoles name="EDIT_PARTNER_COOPERATE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editPartnerCooperation()">修改合伙人合作信息</a>
		</shiro:hasAnyRoles>
	</div>

</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="合伙人账户管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getPartnerCooperationInfo.action',
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
			<th data-options="field:'realName',width:50,sortable:true">合伙人名称</th>
			<th data-options="field:'orgNums',width:30,sortable:true">机构数</th>
			<th data-options="field:'phone',width:30,sortable:true">联系电话</th>
			<th data-options="field:'createTime',width:50,sortable:true">申请时间</th>
			<th data-options="field:'startTime',width:30">合作开始时间</th>
			<th data-options="field:'endTime',width:30">合作结束时间</th>
			<th data-options="field:'rate',formatter:formatAomMoney,width:30">提成比例(%)</th>
			<th data-options="field:'reviewStatus',width:50,formatter:formatterReviewStatus">合作申请状态</th>
		</tr>
	</thead>
</table>
</div>

</div>
</div>
</body>
</html>