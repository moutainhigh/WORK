<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeAcctManagement.js" charset="utf-8"></script>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="searcherFinanceAcctManage.action" method="post"
					id="acctManageForm">
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
								<td class="label_right">开户行:</td>
								<td><input name="bank" id="bank" class="easyui-combobox"
									editable="false" style="width: 180px;" panelHeight="auto"
									data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=BANK_TYPE'" />
								</td>
								<td class="label_right">类型:</td>
								<td><input name="bankCardType" id="bankCardType"
									class="easyui-combobox" style="width: 180px;" editable="false"
									panelHeight="auto"
									data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />

								</td>
								<td><a href="#" class="easyui-linkbutton"
									iconCls="icon-search" style="margin-left: 140px;"
									onclick="ajaxSearch()">查询</a> <a href="#"
									onclick="javascript:$('#acctManageForm').form('reset');"
									iconCls="icon-clear" class="easyui-linkbutton">重置</a></td>
							</tr>
						</table>

					</div>
				</form>
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="addItem()">新增</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="editItem()">修改</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
				</div>

			</div>
			<div class="listFinanceAcctDiv" style="height: 100%;">
				<table id="grid" title="财务账户管理" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="		
	    url: 'searcherFinanceAcctManage.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		checkOnSelect:false,
		onLoadSuccess:function(data){
				clearSelectRows('#grid');
			}"
		>
					<!-- 表头 -->
					<thead>
						<tr>
							<th data-options="field:'pid',checkbox:true,width:100" id="pid"></th>
							<th data-options="field:'chargeName',width:160">收取单位显示名称</th>
							<th data-options="field:'bankCardTypeText',width:50">类型</th>
							<th data-options="field:'bankText',width:100">开户行</th>
							<th data-options="field:'bankNum',width:180">账号</th>
							<th data-options="field:'defaultAmt',width:100,align:'right',formatter:formatMoney">账户初始额</th>
							<th data-options="field:'bankUserName',width:130">开户名</th>
							<th data-options="field:'showSeq',width:50">表示顺序</th>
							<th data-options="field:'isOpenText',width:60">是否启用</th>
							<th data-options="field:'remark',width:100">备注</th>
							<th data-options="field:'yyy',width:180,formatter:operateLoad">操作</th>
						</tr>
					</thead>
				</table>
			</div>
		</div>
</body>