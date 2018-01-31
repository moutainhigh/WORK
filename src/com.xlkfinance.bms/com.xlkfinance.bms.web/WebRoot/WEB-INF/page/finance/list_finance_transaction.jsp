<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/listFinanceTransaction.js" charset="utf-8"></script>

<body class="easyui-layout">
<div data-options="region:'center',border:false" style="height: 100%">

<div id="scroll-bar-div">
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="" method="post" id="financeTransactionForm">
					<!-- 查询条件 -->
					<div style="padding: 5px">
						<table>
							<tr>
								<td class="label_right">收取单位名称:</td>
								<td><input name="chargeName" id="chargeName" onch
									class="easyui-textbox" style="width: 180px;" /></td>
								<td class="label_right">账号:</td>
								<td colspan="2"><input name="bankNum" id="bankNum"
									class="easyui-textbox" style="width: 180px;" /></td>
							</tr>
							<tr>
								<td class="label_right">开户名:</td>
								<td><input name="bankUserName" id="bankUserName"
									class="easyui-textbox" style="width: 180px;" panelHeight="auto" /></td>
								<td class="label_right">类型:</td>
								<td><input name="bankCardType" id="bankCardType"
									class="easyui-combobox" style="width: 180px;" editable="false"
									panelHeight="auto"
									data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />

								</td>
								<td><a href="#" class="easyui-linkbutton"
									iconCls="icon-search" style="margin-left: 140px;"
									onclick="ajaxSearchList()">查询</a> <a href="#"
									onclick="javascript:$('#financeTransactionForm').form('reset');"
									iconCls="icon-clear" class="easyui-linkbutton">重置</a></td>
							</tr>
						</table>

					</div>
				</form>
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-export" plain="true" onclick="exportData()">资金头寸导出</a>

				</div>
			</div>
			<div class="listFinanceTransactionDiv" style="height: 100%;">
				<table id="financeTransactionGrid" title="资金头寸查询"
					class="easyui-datagrid" style="height: 100%; width: 100%;"
					data-options="		
	    url: '<%=basePath %>financeTransactionController/getTransactionListUrl.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		checkOnSelect:false">
					<!-- 表头 -->
					<thead>
						<tr>
							<th data-options="field:'pid',hidden:true,width:100" id="pid"></th>
							<th data-options="field:'chargeName',width:300">收取单位名称</th>
							<th data-options="field:'bankCardTypeText',width:100">类型</th>
							<th
								data-options="field:'defaultAmt',width:250,align:'right',formatter:formatMoney">投资总资本</th>
							<th
								data-options="field:'borrowLoanBalance',width:250,align:'right',formatter:formatMoney">借款余额</th>
							<th
								data-options="field:'loanTotal',width:250,align:'right',formatter:formatMoney">借款总额</th>
							<th
								data-options="field:'loanHasAlsoTotal',width:250,align:'right',formatter:formatMoney">借款已还总额</th>
							<th
								data-options="field:'loanBalance',width:250,align:'right',formatter:formatMoney">贷款余额</th>
							<th
								data-options="field:'loanPrincipalTotal',width:250,align:'right',formatter:formatMoney">放款本金总额</th>
							<th
								data-options="field:'takeBackPrincipalTotal',width:250,align:'right',formatter:formatMoney">收回本金总额</th>
							<th
								data-options="field:'rateTakeBacTotal',width:250,align:'right',formatter:formatMoney">费率收回总额</th>
							<th
								data-options="field:'financialDepositInterest',width:250,align:'right',formatter:formatMoney">理财存款利息</th>
							<th
								data-options="field:'expensesCostTotal',width:250,align:'right',formatter:formatMoney">支出费用总额</th>
								<th
								data-options="field:'inputUnrecAmt',width:250,align:'right',formatter:formatMoney">收款未对账金额</th>
							<th
								data-options="field:'availableFundBalance',width:250,align:'right',formatter:formatMoney">可用资金余额</th>
						</tr>
					</thead>
				</table>
		
</div>
</div>
</div>
</body>
