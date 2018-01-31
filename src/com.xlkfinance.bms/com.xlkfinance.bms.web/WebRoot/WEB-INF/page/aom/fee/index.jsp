<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 费用结算的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
$(function(){
	$('#grid').datagrid({ 
		 onDblClickRow: function (rowIndex, rowData) {
			 parent.openNewTab("${basePath}orgFeeSettleController/toEditOrgFee.action?orgId="
						+ rowData.orgId+"&pid="+rowData.pid,"查看费用详情信息",true);
		 }
	});
});
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "0.00";
		}
	}

	function formatAomRate(value,row,index){
		if(value){
			value = value*100;
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "0.00";
		}
	}
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}orgFeeSettleController/toEditOrgFee.action?orgId="
					+ row[0].orgId+"&pid="+row[0].pid,"查看费用详情信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//查看详情链接
	function lookUpDetail(pid,orgId){
		parent.openNewTab("${basePath}orgFeeSettleController/toEditOrgFee.action?orgId="
				+ orgId+"&pid="+pid,"查看费用详情信息",true);
	}
	
	function createFeeSettle(){
		$.messager.confirm("操作提示","确认进行结算?",
				function(r) {
		 			if(r){
						$.post("<%=basePath%>orgFeeSettleController/createFeeSettle.action",{number : 0}, 
							function(ret) {
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(ret && ret.header["success"]){
									$.messager.alert('操作提示',"结算成功!");	
									// 重新加载列表信息
				 					$("#grid").datagrid('load');
				 					// 清空所选择的行数据
				 					clearSelectRows("#grid");
								}else{
									$.messager.alert('操作提示',ret.header["msg"],'error');	
								}
							},'json');
		 			}
				});
	}
	
	function formatOperate(value,row,index){
		var html= "<a href='javascript:void(0)' onclick = 'lookUpDetail("+row.pid+","+row.orgId+")'> <font color='blue'>查看详情</font></a>";
		return html;
	}
	
	function createFeeSettleOther(){
		$.messager.confirm("操作提示","确认进行结算?",
				function(r) {
		 			if(r){
	 					$.post("<%=basePath%>orgFeeSettleController/createHistoryFeeSettle.action", 
								function(ret) {
									//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
									if(ret && ret.header["success"]){
										$.messager.alert('操作提示',"结算成功!");	
										// 重新加载列表信息
					 					$("#grid").datagrid('load');
					 					// 清空所选择的行数据
					 					clearSelectRows("#grid");
									}else{
										$.messager.alert('操作提示',ret.header["msg"],'error');	
									}
								},'json');
						
		 			}
				});
	}
</script>
<title>费用结算管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getOrgFeeList.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">机构名称：</td>
				<td>
					<input name="orgName" class="easyui-textbox" style="width: 129px;" />
				</td>
				<td width="80" align="right">机构编号：</td>
				<td><input name="orgCode" class="easyui-textbox" style="width: 129px;" /></td>
			</tr>
			<tr>
				<td width="80" align="right" >结算日期：</td>
				<td colsapn="2">
					<input name="settleDate" id="requestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
					<input name="settleDateEnd" id="requestDttmEnd" class="easyui-datebox" editable="false"/>
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
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="CREATE_FEE_SETTLE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="createFeeSettle()">前月结算</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="CREATE_HISTORY_FEE_SETTLE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="createFeeSettleOther()">历史结算</a>
		</shiro:hasAnyRoles>
	</div>

</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="费用结算列表" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getOrgFeeList.action',
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
	<thead data-options="frozen:true">
		<tr>
		 	<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'orgName',sortable:true" >机构名称</th>
			<th data-options="field:'orgCode',sortable:true">统一社会信用代码</th>
			<th data-options="field:'settleDate',sortable:true">计算月份</th>
		 </tr>
	</thead>
	<thead>
		<tr>
			<!-- <th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'orgName',width:80,sortable:true" >机构名称</th>
			<th data-options="field:'orgCode',width:50,sortable:true">机构代码</th> -->
			<th data-options="field:'partnerName',sortable:true">合伙人</th>
			<!-- <th data-options="field:'settleDate',width:50,sortable:true">计算月份</th> -->
			<th data-options="field:'loanMoneyTotal',formatter:formatAomMoney">放款金额(元)</th>
			<th data-options="field:'paymentMoneyTotal',formatter:formatAomMoney">回款金额(元)</th>
			<th data-options="field:'chargeMoneyTotal',formatter:formatAomMoney,sortable:true">收费总金额(元)</th>
			<th data-options="field:'refundMoneyTotal',formatter:formatAomMoney">退费总金额(元)</th>
			<th data-options="field:'extimateFeeRate'">预收费率(%)</th>
			<th data-options="field:'actualFeeRate',sortable:true">应收费率(%)</th>
			<th data-options="field:'returnCommRate',formatter:formatAomRate,sortable:true">返佣率(%)</th>
			<th data-options="field:'returnCommTotal',formatter:formatAomMoney,sortable:true">返佣金额(元)</th>
			<th data-options="field:'cz',formatter:formatOperate" align="center" halign="center"  >操作</th>
		</tr>
	</thead>
</table>
</div>
</div>
</div>
</body>
</html>