<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 放款管理的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
	//审批状态
	function formatterApplyStatus(val,row){
		if (val == 1) {
			return "未申请";
		} else if (val == 2) {
			return "审核中";
		} else if (val == 3) {
			return "已放款";
		} else if (val == 4) {
			return "审核驳回";
		} else if (val == 5) {
			return "审核否决";
		} else {
			return "未知";
		}
	}
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "--";
		}
	}
	
	function editItem(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].orgCashFlowApplyStatus == 2){
				url = '<%=basePath%>aomFinanceController/toEditLoanInfo.action?editType=2&pid='
					+ row[0].orgCashFlowApplyId;
				parent.openNewTab(url,"放款确认",true);
			}else{
				$.messager.alert("操作提示", "只有审核中的放款才能确认！", "error");
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
			parent.openNewTab("${basePath}aomFinanceController/toEditLoanInfo.action?editType=3&pid="
					+ row[0].orgCashFlowApplyId,"查看放款信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
</script>
<title>放款管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getOrgMakeLoansIndex.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">项目名称：</td>
				<td>
					<input name="projectName" class="easyui-textbox" style="width: 129px;" />
				</td>
				<td width="80" align="right">客户名称：</td>
				<td><input name="orgCustomerName" class="easyui-textbox" style="width: 129px;" /></td>
				<td width="80" align="right" >放款状态：</td>
				<td colsapn="2">
					<select class="select_style easyui-combobox" width="129px;" editable="false" name="orgCashFlowApplyStatus">
						  <option value=-1 selected="selected">--请选择--</option>
			              <option value=2>审核中</option>
			              <option value=3>已放款</option>
			              <option value=4>审核驳回</option>
			              <option value=5>审核否决</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="80" align="right" >提单日期：</td>
				<td colsapn="2">
					<input name="requestDttm" id="requestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="requestDttmEnd" id="requestDttmEnd" class="easyui-datebox" editable="false"/>
				</td>
				<td width="80" align="right" >放款日期：</td>
				<td colsapn="2">
					<input name="accountDate" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="accountDateEnd" class="easyui-datebox" editable="false"/>
				</td>
				
				<td colspan="2">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 10px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
				<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">放款确认</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
	</div>

</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="放款申请管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getOrgMakeLoansIndex.action',
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
			<th data-options="field:'orgCashFlowApplyId',checkbox:true"></th>
			<th data-options="field:'projectName',width:80,sortable:true" >项目名称</th>
			<th data-options="field:'pmUserName',width:50,sortable:true">提交人</th>
			<th data-options="field:'customerName',width:50,sortable:true">客户名称</th>
			<th data-options="field:'money',formatter:formatAomMoney,width:50">放款金额(元)</th>
			<th data-options="field:'accountDate',width:50,sortable:true">放款日期</th>
			<th data-options="field:'orgCashFlowApplyStatus',width:50,formatter:formatterApplyStatus">放款状态</th>
			<th data-options="field:'planLoanMoney',formatter:formatAomMoney,width:50">借款金额(元)</th>
			<th data-options="field:'requestDttm',width:60,sortable:true">提单日期</th>
			<th data-options="field:'customerPhone',width:50,sortable:true">客户电话</th>
			<th data-options="field:'customerCard',width:80,sortable:true">身份证号</th>
		</tr>
	</thead>
</table>
</div>
</div>
</div>
</body>
</html>