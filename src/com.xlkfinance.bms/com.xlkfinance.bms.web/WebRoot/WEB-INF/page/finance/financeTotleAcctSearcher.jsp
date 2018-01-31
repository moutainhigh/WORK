<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeTotleAcct.js" charset="utf-8"></script>
<script type="text/javascript">
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="listFinanceAcctTotalUrl.action" method="post"
					id="searchFrom" name="searchFrom">
					<!-- 查询条件 -->
					<div style="padding: 5px">
						<table>
							<tr>
								<td class="label_right">账户类型：</td>
								<td><input name="bankCardType" id="bankCardType"
									class="easyui-combobox" editable="false" panelHeight="auto"
									data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />
								</td>
								<td class="label_right">收取单位名称：</td>
								<td><input class="easyui-textbox" name="chargeName"
									style="width: 220px;" id="chargeName" /></td>
								<td class="label_right">银行账号：</td>
								<td><input class="easyui-textbox" name="bankNum"
									id="bankNum" /></td>
							</tr>
							<tr>
								<td class="label_right">开户名：</td>
								<td><input class="easyui-textbox" name="bankUserName"
									id='bankUserName' /></td>
								<td class="label_right">查询期间：</td>
								<td colspan="3"><input class="easyui-datebox"
									name="searcherPeriodStart" id='searcherPeriodStart' /> <span>~</span>
									<input class="easyui-datebox" name="searcherPeriodEnd"
									id='searcherPeriodEnd' /> <a href="#" class="easyui-linkbutton"
									iconCls="icon-search" onclick="ajaxSearchTotleAcct()">查询</a> <a href="#"
									onclick="javascript:$('#searchFrom').form('reset');"
									iconCls="icon-clear" class="easyui-linkbutton">重置</a></td>
							</tr>
						</table>

					</div>
				</form>


			</div>
			<div style="height: 100%">
				<table id="acctTotalGrid" title="财务总账查询" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="
	    url: 'listFinanceAcctTotalUrl.action',
	    method: 'post',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    singleSelect: true,
		selectOnCheck: true,
		checkOnSelect: true">
					<!-- 表头 -->
					<thead>
						<tr>
							<th
								data-options="field:'pid',checkbox:true,width:100,hidden:true"
								id="pid"></th>
							<th data-options="field:'chargeName',width:200">收取单位显示名称</th>
							<th data-options="field:'bankCardTypeText',width:50">类型</th>
							<th data-options="field:'bankNum',width:200">账号</th>
							<th
								data-options="field:'initialAmt',width:100,align:'right',formatter:formatMoney">期初金额</th>
							<th
								data-options="field:'incomeAccount',width:100,align:'right',formatter:formatMoney">账户收入</th>
							<th
								data-options="field:'accountOut',width:100,align:'right',formatter:formatMoney">账户支出</th>
							<th
								data-options="field:'periodBalance',width:100,align:'right',formatter:formatMoney">期间余额</th>
							<th data-options="field:'yyy',width:80,formatter:operateLoadTotleAcct">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
	
</body>