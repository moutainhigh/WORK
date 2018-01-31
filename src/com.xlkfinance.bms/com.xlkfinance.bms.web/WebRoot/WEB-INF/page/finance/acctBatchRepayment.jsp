<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeBatchRepayment.js" charset="utf-8"></script>
<script type="text/javascript">
	//跳转到 添加页面
	/*function addItem() {
		$('<div id="editFinaceAcctManager" />')
				.dialog(
						{
							href : '${basePath}financeController/saveFinanceAcctManager.action',
							width : 830,
							height : 350,
							modal : true,
							title : '新增财务账户管理',
							buttons : [
									{
										text : '保存',
										iconCls : 'icon-save',
										handler : function() {
											window.save();
										}
									},
									{
										text : '取消 ',
										iconCls : 'icon-no',
										handler : function() {
											$("#editFinaceAcctManager").dialog(
													'close');
										}
									} ],
							onClose : function() {
								$(this).dialog('destroy');
							},
							onLoad : function() {
							}
						});
	};*/
	//绑定赋值
	
</script>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="previewBatchRepaymentResult.action" method="post"
					id="searchFrom" name="searchFrom">
					<input type="hidden" id="savePara" name="para" />

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
								<td><input name="cusName" id="cusName"
									class="easyui-textbox" style="width: 220px;" /></td>
								<td class="label_right">客户类别:</td>
								<td colspan="2"><input name="cusType" id="cusType"
									class="easyui-combobox" editable="false" style="width: 220px;"
									panelHeight="auto"
									data-options="valueField:'pid',textField:'lookupDesc',value:'-1',
						url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />
								</td>
							</tr>
							<tr>
								<td class="label_right">应收期间:</td>
								<td><input name="expireStartDt" id="expireStartDt"
									class="easyui-datebox" data-options='editable:false'
									style="width: 100px;" value="${currDate}" /> <span>~</span> <input
									name="expireEndDt" id="expireEndDt" class="easyui-datebox"
									data-options='editable:false' style="width: 100px;"
									value="${currDate}" /></td>
								<td class="label_right" id="ecoTradeId1">经济行业类别:</td>
								<td id="ecoTradeId2"><input class="easyui-combobox"
									style="width: 220px;" name="ecoTrade" id="ecoTrade"
									editable="false" panelHeight="auto"
									data-options="
					            valueField:'pid',
					            textField:'lookupDesc',
					            method:'get',value:'-1',
					            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
								</td>
								<td style="text-align: right;"> <a href="#" class="easyui-linkbutton"
									iconCls="icon-search" onclick="ajaxSearch()">查询</a> 
								</td>
								<td style="text-align: left;">	
									<a href="#"
									onclick="majaxReset('#searchFrom')" class="easyui-linkbutton"
									style="width: 60px;">重置</a>
								</td>
							</tr>

						</table>

					</div>
				</form>
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-save" plain="true" onclick="saveAcctBatchRepay();">对账处理</a>
				</div>

			</div>
			<div class="acctBatchGridDiv" style="height: 100%;">
				<table id="acctBatchGrid" title="批量对账" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="
	    url: 'listAcctBatchRepayment.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'loanId',
	    showFooter:true,
		selectOnCheck: false,
		checkOnSelect: false,
		onLoadSuccess:function(data){
				clearSelectRows('#acctBatchGrid');
			}">

					<!-- 表头 -->
					<thead>
						<tr>
							<th data-options="field:'loanId',checkbox:true,width:30"
								id="loanId"></th>
							<th data-options="field:'pid',hidden:true" id="pid"></th>
							<th
								data-options="field:'projectName',width:150,formatter:formatProjectName">项目名称</th>
							<th data-options="field:'projectNumber',width:150">项目编号</th>
							<th data-options="field:'repayDt',width:80,formatter:convertDate">应收日期</th>
							<th
								data-options="field:'actualReceDt',width:100,editor:{type:'datebox'},formatter:convertDate">实收日期</th>
							<th
								data-options="field:'actualAmt',width:100,editor:{type:'numberbox',options:{precision:2,groupSeparator:','}}">实收金额</th>

						</tr>
					</thead>
				</table>
			</div>
		</div>
	
</body>