<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">
	//查询 
	function ajaxSearch() {
		if (!compareDate('expireStartDt', 'expireEndDt')) {
			return false;
		}
		if (!compareDate('requestStartDt', 'requestEndDt')) {
			return false;
		}
		var overdueStartDay = $('#overdueStartDay').numberbox('getValue');
		var overdueEndDay = $('#overdueEndDay').numberbox('getValue');
		if (overdueStartDay > overdueEndDay) {
			$.messager.alert("提示", "已逾期间开始天数不能大于结束天数！", "error");
			return;
		}
		var data = {
			projectName : $("#projectName").val(),
			projectNumber : $("#projectNumber").val(),
			cusName : $("#cusName").val(),
			cusType : $('#cusType').combobox('getValue'),
			expireStartDt : $('#expireStartDt').datebox('getValue'),
			expireEndDt : $('#expireEndDt').datebox('getValue'),
			ecoTrade : $('#ecoTrade').combobox('getValue'),
			overdueStartDay : $('#overdueStartDay').numberbox('getValue'),
			overdueEndDay : $('#overdueEndDay').numberbox('getValue'),
			requestStartDt : $('#requestStartDt').datebox('getValue'),
			requestEndDt : $('#requestEndDt').datebox('getValue')
		};

		$('#custArrearsGrid').datagrid('load', data);
	}
	function operateLoad(value, row, index) {
		if (row.pid > 0) {
			return '<a href="javascript:void(0)" onclick="findIncome('
					+ row.loanId + ',' + row.pid
					+ ')" ><font color="blue">查看收息表</font></a>';
		}

	}
	//查看收息表
	function findIncome(loanId, projectId) {
		parent.openNewTab(
				"${basePath}financeController/getFinanceListLoanCalculate.action?loanId="
						+ loanId + '&projectId=' + projectId, "查看收息表", true);
	}

	//项目名称format
	var formatProjectName = function(value, rowData, index) {
		if (!rowData.projectName) {
			return "";
		}

		var btn = "<a id='"
				+ rowData.pid
				+ "' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("
				+ rowData.pid + ",\"" + rowData.projectNumber
				+ "\")' href='#'>" + "<span style='color:blue; '>"
				+ rowData.projectName + "</span></a>";
		return btn;
	}
	
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;" style="height: 100%">
				<form action="custArrearsListUrl.action" method="post"
					id="custArrearsForm">
					<!-- 查询条件 -->
					<div style="padding: 5px">
						<table width="90%">
							<tr>
								<td class="label_right">项目名称:</td>
								<td><input name="projectName" id="projectName" onch
									class="easyui-textbox" style="width: 220px;" /></td>
								<td class="label_right">项目编号:</td>
								<td><input name="projectNumber" id="projectNumber"
									class="easyui-textbox" style="width: 220px;" /></td>
							</tr>
							<tr>
								<td class="label_right">客户名称:</td>
								<td><input name="cusName" id="cusName"
									class="easyui-textbox" style="width: 220px;" /></td>
								<td class="label_right">客户类别:</td>
								<td><input name="cusType" id="cusType"
									class="easyui-combobox" style="width: 220px;" editable="false"
									panelHeight="auto"
									data-options="valueField:'pid',textField:'lookupDesc',value:'-1',
					url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />
								</td>
							</tr>
							<tr>
								<td class="label_right">到期期间:</td>
								<td><input name="expireStartDt" id="expireStartDt"
									class="easyui-datebox" data-options="editable:false"
									style="width: 90px;" /> <span>~</span> <input
									name="expireEndDt" id="expireEndDt" class="easyui-datebox"
									data-options="editable:false" style="width: 90px;" /></td>
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
							</tr>
							<tr>
								<td class="label_right">已逾期间(日):</td>
								<td><input name="overdueStartDay" id="overdueStartDay"
									class="easyui-numberbox" style="width: 100px;"
									data-options="min:0" /> <span>~</span> <input
									name="overdueEndDay" id="overdueEndDay" data-options="min:0"
									class="easyui-numberbox" style="width: 100px;" /></td>
								<td class="label_right">贷款申请时间:</td>
								<td><input name="requestStartDt" id="requestStartDt"
									class="easyui-datebox" data-options="editable:false"
									style="width: 100px;" /> <span>~</span> <input
									name="requestEndDt" id="requestEndDt" class="easyui-datebox"
									data-options="editable:false" style="width: 100px;" /> <a
									href="#" class="easyui-linkbutton" iconCls="icon-search"
									style="margin-left: 10px;" onclick="ajaxSearch()">查询</a> <a
									href="#" onclick="majaxReset('#custArrearsForm')"
									class="easyui-linkbutton" style="width: 60px;">重置</a></td>
							</tr>
						</table>
					</div>
				</form>

			</div>
			<div style="height: 100%">
				<table id="custArrearsGrid" title="客户欠款明细列表" class="easyui-datagrid"
					style="height: 100%; width: auto;"
					data-options="		
	    url: 'custArrearsListUrl.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:false,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		checkOnSelect:false">
					<!-- 表头 -->
					<thead>
						<tr>
							<th data-options="field:'pid',hidden:true,width:100" id="pid"></th>
							<th data-options="field:'contractUrl',hidden:true,width:100" id="contractUrl"></th>
							<th data-options="field:'loanId',hidden:true,width:100"
								id="loanId"></th>
							<th data-options="field:'cusName',width:120,formatter:custRepay">客户名称</th>
							<th data-options="field:'contractNo',width:120,formatter:contractdeite">借款合同编号</th>
							<th
								data-options="field:'unReceivedPrincipal',width:80,align:'right',formatter:formatMoney">未收本金</th>
							<th
								data-options="field:'unReceivedInterest',width:80,align:'right',formatter:formatMoney">未收利息</th>
							<th
								data-options="field:'unReceivedMangCost',width:80,align:'right',formatter:formatMoney">未收管理费</th>
							<th
								data-options="field:'unReceivedOtherCost',width:80,align:'right',formatter:formatMoney">未收其他费</th>
							<th
								data-options="field:'unReceivedOverdueInterest',width:80,align:'right',formatter:formatMoney">未收逾期利息</th>
							<th
								data-options="field:'unReceivedOverduePunitive',width:80,align:'right',formatter:formatMoney">未收逾期罚息</th>
							<th
								data-options="field:'dueUnReceivedTotal',width:100,align:'right',formatter:formatMoney">到期未收小计</th>
							<th
								data-options="field:'outstandingTotal',width:100,align:'right',formatter:formatMoney">未到期本息</th>
							<th
								data-options="field:'noReceiveTotalAmt',width:100,align:'right',formatter:formatMoney">未收合计</th>
							<th
								data-options="field:'projectName',width:160,formatter:formatProjectName">项目名称</th>
							<th data-options="field:'projectNumber',width:120">项目编号</th>
							<th data-options="field:'y',width:120,formatter:operateLoad">操作</th>
						</tr>
					</thead>
				</table>
			
</div>
</div>
</body>