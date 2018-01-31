<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeBusiness.js" charset="utf-8"></script>
<style type="text/css">
</style>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="scroll-bar-div">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="listCusBusinessUrl.action" method="post"
			id="cusBusinessForm">
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
						<td class="label_right">到期期间:</td>
						<td><input name="expireStartDt" id="expireStartDt"
							editable="false" class="easyui-datebox" style="width: 110px;" />
							<span>~</span> <input name="expireEndDt" id="expireEndDt"
							editable="false" class="easyui-datebox" style="width: 110px;" />
						</td>
						<td class="label_right" id="ecoTradeId1">经济行业类别:</td>
						<td colspan="2" id="ecoTradeId2"><input
							class="easyui-combobox" style="width: 220px;" name="ecoTrade"
							id="ecoTrade" editable="false" panelHeight="auto"
							data-options="
					            valueField:'pid',
					            textField:'lookupDesc',
					            method:'get',value:'-1',
					            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />

						</td>
					</tr>
					<tr>
						<td class="label_right">已逾期间(日):</td>
						<td><input name="overdueStartDay" id="overdueStartDay"
							class="easyui-textbox" style="width: 98px;" /> <span>~</span> <input
							name="overdueEndDay" id="overdueEndDay" class="easyui-textbox"
							style="width: 98px;" /></td>

						<td class="label_right">申请时间:</td>
						<td><input name="requestStartDt" id="requestStartDt"
							editable="false" class="easyui-datebox" style="width: 110px;" />
							<span>~</span> <input name="requestEndDt" id="requestEndDt"
							editable="false" class="easyui-datebox" style="width: 110px;" />
						</td>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-search" onclick="ajaxSearchBusiness()">查询</a> <a href="#"
							onclick="majaxReset('#cusBusinessForm')"
							class="easyui-linkbutton" style="width: 60px;">重置</a></td>
					</tr>

				</table>

			</div>
		</form>

	</div>

	<div class="cusBusinessGridDiv" style="height: 100%;">
		<table id="cusBusinessGrid" title="客户业务查询" class="easyui-datagrid"
			style="height: 100%; width: auto;"
			data-options="
	    url: 'listCusBusinessUrl.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		onLoadSuccess:function(data){
				clearSelectRows('#cusBusinessGrid');
			}">

			<!-- 表头 -->
			<thead>
				<tr>
					<th data-options="field:'pid',hidden:true" id="pid"></th>
					<th data-options="field:'loanId',hidden:true" id="loanId"></th>
					<th
						data-options="field:'projectName',width:180,formatter:formatProjectName">项目名称</th>
					<th data-options="field:'projectNumber',width:90">项目编号</th>
					<th data-options="field:'cusType',width:55">客户类型</th>
					<th
						data-options="field:'creditAmt',width:100,align:'right',formatter:formatMoney">贷款金额</th>
					<th
						data-options="field:'dueUnreceived',width:90,align:'right',formatter:formatMoney">到期未收金额</th>
					<th
						data-options="field:'unDue',width:100,align:'right',formatter:formatMoney">未到期金额</th>
					<th
						data-options="field:'reconciliationAmt',width:100,align:'right',formatter:formatMoney">已经对账金额</th>
					<th
						data-options="field:'unReconciliationAmt',width:90,align:'right',formatter:formatMoney">未对账金额</th>
					<th data-options="field:'yyy',width:320,formatter:operateLoadBusiness">操作</th>

				</tr>
			</thead>


		</table>
	</div>
</div>
</div>
</body>
