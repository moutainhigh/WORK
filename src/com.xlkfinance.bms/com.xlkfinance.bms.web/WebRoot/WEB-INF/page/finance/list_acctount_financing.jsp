<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
//绑定赋值
var bankAcctId=${financeBank.pid};

</script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeTransaction.js" charset="utf-8"></script>

<body class="easyui-layout">
	<div data-options="region:'center',border:false" style="height: 100%">

			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="" method="post" id="acctOutputFormFinancing">
					<!-- 查询条件 -->
					<div style="padding: 5px">
						<table>
							<tr>
								<td class="label_right">账户类型:</td>
								<td>${financeBank.bankCardTypeText}</td>
								<td class="label_right">收取单位名称:</td>
								<td>${financeBank.chargeName}</td>
								<td class="label_right">银行账号:</td>
								<td>${financeBank.bankNum}</td>
							</tr>
							<tr>
								<td class="label_right">查询期间:</td>
								<td><input name="ftStartDt" id="ftStartDt" editable="false"
									class="easyui-datebox" style="width: 150px;" /> <span>~</span>
									<input name="ftEndDt" id="ftEndDt" editable="false"
									class="easyui-datebox" style="width: 150px;" /></td>

								<td colspan="3">
									<a href="#" class="easyui-linkbutton"
									iconCls="icon-search" style="margin-left: 20px;"
									onclick="ajaxSearchFinancing()">查询</a>
									<a href="#" class="easyui-linkbutton"
									 style="margin-left: 10px;width:50px"
									onclick="resetAcctOutputFormFinancing()">重置</a>
								</td>
							</tr>
						</table>

					</div>
				</form>
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="addItemFinancing()">新增融资贷款</a>

				</div>
			</div>
			<div class="listFinanceAcctDiv" style="height: 100%;">
				<table id="financingGrid" title="银行账户融资借款查询" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="		
	    url: 'listFinanceFinancingUrl.action?bankAcctId='+${financeBank.pid},
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
							<th data-options="field:'ftDt',width:160">借入日期</th>
							<th data-options="field:'repayDt',width:160">应还款日期</th>
							<th
								data-options="field:'ftAmtLoan',width:100,align:'right',formatter:formatMoney">借款金额</th>
							<th
								data-options="field:'ftAmtInput',width:100,align:'right',formatter:formatMoney">已还金额</th>
							<th
								data-options="field:'loanBalance',width:100,align:'right',formatter:formatMoney">借款余额</th>
							<th
								data-options="field:'ftAmtInterest',width:100,align:'right',formatter:formatMoney">支出利息</th>
							<th data-options="field:'remark',width:180">借入备注</th>
							<th data-options="field:'yyy',width:180,formatter:operateLoad">操作</th>
						</tr>
					</thead>
				</table>

		

</div>
</div>
</body>