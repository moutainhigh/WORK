<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeUnReconciliation.js" charset="utf-8"></script>
<script type="text/javascript">
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="scroll-bar-div">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<table id="dg"></table>
		<form action="getListUnReconciliationUrl.action" method="post"
			id="unReconciliationForm">
			<!-- 查询条件 -->
			<div style="padding: 5px">
				<table width="90%">
					<tr>
						<td class="label_right">项目名称:</td>
						<td><input name="projectName" id="projectName" onch
							class="easyui-textbox" style="width: 220px;" /></td>
						<td class="label_right">项目编号:</td>
						<td colspan="2"><input name="projectNumber"
							id="projectNumber" class="easyui-textbox" style="width: 220px;" /></td>
					</tr>
					<tr>
						<td class="label_right">客户名称:</td>
						<td><input name="cusName" id="cusName" class="easyui-textbox"
							style="width: 220px;" /></td>
						<td class="label_right">客户类别:</td>
						<td colspan="2"><input name="cusType" id="cusType"
							class="easyui-combobox" style="width: 220px;" editable="false"
							panelHeight="auto"
							data-options="valueField:'pid',textField:'lookupDesc',value:'-1',
				url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" /></td>
					</tr>
					<tr>
						<td class="label_right">收款日期:</td>
						<td><input name="receiveStartDt" id="receiveStartDt"
							class="easyui-datebox" style="width: 110px;" value="${currDate}" />
							<span>~</span> <input name="receiveEndDt" id="receiveEndDt"
							class="easyui-datebox" style="width: 110px;" value="${currDate}" /></td>
						<td class="label_right" id="ecoTradeId1">经济行业类别:</td>
						<td id="ecoTradeId2"><input class="easyui-combobox"
							style="width: 220px;" name="ecoTrade" id="ecoTrade"
							editable="false" panelHeight="auto"
							data-options="
					            valueField:'pid',
					            textField:'lookupDesc',
					            method:'get',value:'-1',
					            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-search" onclick="ajaxSearch()">查询</a> </td>
							<td>
							<a href="#"
							onclick="majaxReset('#unReconciliationForm')"
							class="easyui-linkbutton" style="width: 60px;">重置</a></td>
					</tr>


				</table>

			</div>
		</form>
		<div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				data-options="iconCls:'icon-save'" plain="true"
				onclick="returnBalanceAmt()">确认处理</a>
		</div>
	</div>
	<div class="unReconciliationGridDiv" style="height: 100%;">
		<table id="unReconciliationGrid" title="未对账处理查询"
			class="easyui-datagrid" style="height: 100%; width: auto;"
			data-options="
	    url: 'getListUnReconciliationUrl.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true">

			<!-- 表头 -->
			<thead>
				<tr>
					<th data-options="field:'pid',hidden:true" id="pid"></th>
					<th data-options="field:'loanId',hidden:true" id="loanId"></th>
					<th data-options="field:'projectId',hidden:true" id="projectId"></th>
					<th
						data-options="field:'projectName',width:180,formatter:formatProjectName">项目名称</th>
					<th data-options="field:'projectNumber',width:100">项目编号</th>
					<th data-options="field:'receiveDt',width:150">收款日期</th>
					<th
						data-options="field:'actualAmt',width:150,align:'right',formatter:formatMoney">收款金额</th>
					<th
						data-options="field:'reconciliationAmt',width:150,align:'right',formatter:formatMoney">已对账金额</th>
					<th
						data-options="field:'unReconciliationAmt',width:150,align:'right',formatter:formatMoney">未对账金额</th>
					<th
						data-options="field:'returnAmt',formatter:checkboxField,width:100">退还处理</th>
					<th
						data-options="field:'balance',formatter:checkboxField2,width:100">转入客户余额</th>
					<th data-options="field:'yyy',width:220,formatter:operateLoad">操作</th>

				</tr>
			</thead>


		</table>
	</div>
</div>
</div>
</body>