<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeTransaction.js" charset="utf-8"></script>
<script type="text/javascript">
//绑定赋值
var bankAcctId=${financeBank.pid};

</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false" style="height: 100%">

			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="" method="post" id="acctInputForm">
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

								<td colspan="3"><a href="#" class="easyui-linkbutton"
									iconCls="icon-search" style="margin-left: 20px;"
									onclick="ajaxSearch()">查询</a>
									<a href="#" class="easyui-linkbutton"
									 style="margin-left: 10px;width:50px"
									onclick="resetAcctInputForm()">重置</a></td>
							</tr>
						</table>

					</div>
				</form>
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="addItemInput()">新增理财收入</a>

				</div>
			</div>
			<div class="listFinanceAcctDiv" style="height: 100%;">
				<table id="inputGrid" title="银行账户理财利息查询" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="		
	    url: 'listFinanceTransactionUrl.action?regCategory=3&bankAcctId='+${financeBank.pid},
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
							<th data-options="field:'ftDt',width:160">收入日期</th>
							<th data-options="field:'ftAmt',width:100,formatter:formatMoney">收入金额</th>
							<th data-options="field:'remark',width:180">支出备注</th>
							<th data-options="field:'yyy',width:180,formatter:operateLoad">操作</th>
						</tr>
					</thead>
				</table>
		
</div>
</div>
</body>