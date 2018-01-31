<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>

<script type="text/javascript">
	function datiloperat(value, row, index) {
		return '<a href="javascript:void(0)"  onclick="repayDatil()"><font color="blue">贷款详细</font></a>|<a href="javascript:void(0)"  onclick="repayAplDatil()"><font color="blue">展期申请</font></a>';
	}
	function repayDatil() {
	}
	function repayAplDatil() {
	}
	function repayList() {
		$('#searcFrom').form('submit', {
			success : function(data) {
				$('#grid').datagrid('loadData', eval(data));
			}
		});

	}
</script>

<div id="roleDiv" style="width: 100%;">

	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<!-- 查询条件 -->
		
		<form action="/BMS/rePaymentController/listrepaymenturl.action" method="post" id="searcFrom"
			name="searcFrom">
			<div style="margin: 5px">
				<p> &nbsp;&nbsp;&nbsp;&nbsp;项目编号:<input class="easyui-textbox"name="projectId" id="projectId" />   
				 	项目名称:<input class="easyui-textbox" name="projectName" id="projectName" /></p>
				<p>	&nbsp;&nbsp;&nbsp;&nbsp;客户名称:<input class="easyui-textbox" name="cusName" id="cusName" />   
				 	客户类别:<input class="easyui-textbox" name="cusType" id="cusType" /></p>  
				<p>	
					经济行业类别:<input class="easyui-combobox" name="ecoTrade" id="ecoTrade" editable="false" 
			            data-options="
			            valueField:'pid',
			            textField:'lookupVal',
			            method:'get',
			            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'"
							/>
				</p>
				<p>	贷款申请时间:<input class="easyui-datebox"name="requestDttm" id="requestDttm" editable="false" /> <span>~</span> 
					<input class="easyui-datebox" name="requestDttmLast" id="requestDttmLast" editable="false" /></p>
				<p> 计划还款时间:<input class="easyui-datebox"name="planRepayLoanDt" id=planRepayLoanDt editable="false"/> <span>~</span> 
			     	<input editable="false" class="easyui-datebox" name="planRepayLoanDtLast" id="planRepayLoanDtLast" />  
					<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="repayList()">查询</a>
			</p></div>
		</form>
 
	</div>
	<table id="grid" title="项目列表" class="easyui-datagrid"
		style="height: auto; width: auto;"
		data-options="
	    url: '/BMS/rePaymentController/listrepaymenturl.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: true,
	    singleOnCheck: true,
		selectOnCheck: false,
		checkOnSelect: false
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
				<th data-options="field:'pid',checkbox:true"></th>
				<th data-options="field:'projectId',width:100">项目编号</th>
				<th data-options="field:'projectName',width:150">项目名称</th>
				<th data-options="field:'cusType',width:150">客户类别</th>
				<th data-options="field:'creditAmt',width:150">贷款金额</th>
				<th data-options="field:'pmuserName',width:150">项目经理</th>
				<th data-options="field:'requestDttm',width:200">贷款开始日期</th>
				<th data-options="field:'planRepayLoanDt',width:150">贷款结束日期</th>
				<th data-options="field:'yy',width:100,formatter:datiloperat">操作</th>
			</tr>
		</thead>
	</table>
</div>
