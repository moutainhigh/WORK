<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>

<div id="loadList1" style="height:auto;width: auto;">
	<table id="loanOutputGrid" class="easyui-datagrid"
		style="height:auto; width: 100%;"
		data-options="
			    url: '',
			    rownumbers: true,
			    pagination: false,
			    idField: 'pid',
		        singleSelect: true,
			    singleOnCheck: true,
				selectOnCheck: false,
				fitColumns:true,
				checkOnSelect: false,
				onLoadSuccess:outputdis
			    ">
		<thead>
			<tr>
				<th data-options="field:'shouldAmt',width:100,align:'right',formatter:formatMoney">应放款金额</th>
				<th data-options="field:'ftAmt',width:100,align:'right',formatter:formatMoney">放款金额</th>
				<th data-options="field:'difAmt',width:100,align:'right',formatter:formatMoney">未放款金额</th>
				<th data-options="field:'ftUserName',width:100">操作人</th>
				<th data-options="field:'ftDate',width:100,formatter:dateftSplit">交易日期</th>
				<th data-options="field:'outputDesc',width:100">放款备注</th>
				<th data-options="field:'outp',width:100,formatter:dateftSta">状态</th>
				<th data-options="field:'eee',width:100,formatter:deOrestore">操作</th>
			</tr>
		</thead>
	</table>
</div>