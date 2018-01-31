<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">


function reqMoney(){
	
}

function reqFreeze(){
	var row = $('#limitGrid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].usableLimit>0){
 			window.location.href='${basePath}creditsController/editFreeze.action?limitId='+row[0].limitId+"&limitStatus=3";
 		}else{
 			$.messager.alert("操作提示","可以余额为0,不能执行冻结操作","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

function noFreeze(){
	var row = $('#limitGrid').datagrid('getSelections');
 	if (row.length == 1) {
 		if(row[0].freezeLimit>0){
 			window.location.href='${basePath}creditsController/editFreeze.action?limitId='+row[0].limitId+"&limitStatus=4";
 		}else{
 			$.messager.alert("操作提示","冻结余额为0,不能执行解冻操作","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

function limitDetail(){
	
}

$(function(){
	setCombobox("acct_type","CUS_TYPE","");
	setCombobox("eco_trade","ECO_TRADE","");
	
	$("#acct_type").change(function(){
		var ctype=$("#acct_type").combobox('getValue');
		if(ctype==2){
			$("#eco_trade_span").show();
		}else{
			$("#eco_trade_span").hide();
		}
	});
});	

function appload(value, row, index) {
	return '<a href="javascript:void(0)"  onclick="loadapply('+row.limitId+','+row.projectName+','+row.projectId+')"><font color="blue">提款申请</font></a>|<a href="javascript:void(0)"  onclick="repayAplDatil()"><font color="blue">冻结|解冻</font></a>';
}
function loadapply(dte,dt,projectId){
	loadHistoryApprover(projectId,"creditWithdrawalRequestProcess");
    window.location.href='${basePath}creditsController/listLoanAppProject.action?limitId='+dte+"&limitStatus=3";
} 
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="listCreditsUrl.action" method="post" id="limitForm" >
	<!-- 查询条件 -->
	<div style="padding:5px">
		<table class="beforeloanTable">
			<tr>
				<td class="label_right">项目名称：</td>
				<td><input name="projectName" onch class="easyui-textbox"  style="width: 180px;" /></td>
				<td class="label_right">项目编号：</td>
				<td><input name="projectId"  class="easyui-textbox"  style="width: 180px;" /></td>
				<td></td>
			</tr>
			<tr>
				<td class="label_right">客户名称：</td>
				<td><input name="acctName" class="easyui-textbox" style="width:180px;"  /></td>
				<td class="label_right">客户类别：</td>
				<td><select name="acctType" id="acct_type"  class="easyui-combobox"  style="width: 180px;"></select></td>
				<td></td>
			</tr>
			<tr id="eco_trade_span" style="display:none;">
				<td class="label_right">经济行业类别：</td>
				<td colsapn="3"><select name="ecoTrade" id="eco_trade"  class="easyui-combobox"  style="width: 180px;"></select></td>
				<td></td>
			</tr>
			<tr>
				<td class="label_right">申请时间：</td>
				<td colspan="3"><input name="requestStartDt" class="easyui-datebox" style="width:100px;"  />&nbsp;~&nbsp;<input name="requestEndDt" class="easyui-datebox" style="width:100px;"  /></td>
				<td><a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 20px;" onclick="ajaxSearch('#limitGrid','#limitForm')">查询</a></td>
			</tr>
		</table>
		
		
	</div>
	</form>
	
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="reqMoney()">提款申请</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-encrypted" plain="true" onclick="reqFreeze()">冻结</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-decrypted" plain="true" onclick="noFreeze()">解冻</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="limitDetail()">查看详细</a>
	</div>
</div>
<div class="creditListDiv" style="height:100%">
<table id="limitGrid" title="额度查询管理" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: 'listCreditsUrl.action',
	    method: 'post',
	    rownumbers: true,
	    singleSelect: false,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true">
	<!-- 表头 -->
	<thead><tr>
	    <th data-options="field:'limitId',checkbox:true" ></th>
	    <th data-options="field:'projectName',width:100"  >项目名称</th>
	    <th data-options="field:'projectId',width:100"  >项目编号</th>
	    <th data-options="field:'isHoopVal',width:100" >额度种类</th>
	    <th data-options="field:'creditAmt',width:100" >总额度</th>
	    <th data-options="field:'useLimit',width:100" >已提取额度</th>
	    <th data-options="field:'freezeLimit',width:100" >冻结额度</th>
	    <th data-options="field:'usableLimit',width:100" >可用额度</th>
	    <th data-options="field:'startDt',width:100" >授信开始日期</th>
	    <th data-options="field:'endDt',width:100" >授信结束日期</th>
	    <th data-options="field:'yy',width:100,formatter:appload">操作</th>
	</tr></thead>
</table>
</div>
</div>
</div>
</body>