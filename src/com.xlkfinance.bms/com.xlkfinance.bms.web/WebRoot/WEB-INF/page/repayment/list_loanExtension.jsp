<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/json2.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/repayment/extensionApply/list_loanExtension.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
	$(document).ready(function(){
		loanExtension.init();
	});
	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<!-- 查询条件 -->
		<form action="extentionList.action" method="post" id="searcFrom"
			name="searcFrom">
			<div style="padding: 5px" >
				<table class="beforeloanTable" width="90%">
					<tr>
						<td class="label_right">项目名称：</td>
						<td>
							<input class="easyui-textbox" style="width:220px" name="projectName" id="projectName" />
						</td>
						
						<td class="label_right">项目编号：</td>
						<td colspan="2">
							<input class="easyui-textbox" style="width:220px" name="projectNum" id="projectNum" />
						</td>
					</tr>
					<tr>
						<td class="label_right">客户名称:</td>
						<td><input class="easyui-textbox" style="width:220px" name="cusName" id="cusName" /></td>
						<td class="label_right">客户类别:</td>
						<td colspan="2">
							<select name="cusType" id="cusType"  class="easyui-combobox" style="width:220px;" >
							<option value="">--请选择--</option>
							<option value="1">个人 </option>
							<option value="2">企业 </option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="label_right">到期期间:</td>
						<td >
							<input class="easyui-datebox" name="planRepayDt"
							id="planRepayDt" editable="false" /> <span>~</span> <input
							class="easyui-datebox" name="planRepayDtLast" id="planRepayDtLast"
							editable="false" />
						</td>						

						<td class="label_right ecoTrade">经济行业类别:</td>
						<td class="ecoTrade">
							<input class="easyui-combobox" style="width:220px" name="ecoTrade" id="ecoTrade"
							editable="false"
							data-options="
				            valueField:'pid',
				            textField:'lookupVal',
				            method:'get',
				            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
						</td>					
					
					</tr>
					<tr>
						<td class="label_right">已逾期间:</td>
						<td >
							<input class="easyui-textbox" style="width:100px" name="overdueStartDay" id="overdueStartDay" /> <span>~</span> 
							<input class="easyui-textbox" style="width:100px" name="overdueEndDay" id="overdueEndDay" />
						</td>	
						<td class="label_right">贷款申请时间:</td>
						<td >
							<input class="easyui-datebox" name="requestDttm"
							id="requestDttm" editable="false" /> <span>~</span> <input
							class="easyui-datebox" name="requestDttmLast" id="requestDttmLast"
							editable="false" />
						</td>
						
					</tr>
					<tr>
						<td>
						</td>	
						<td align="right">
							<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="loanExtension.loadList()">查询</a>
						</td>
						<td > 
							<input type="hidden" id="rows" name="rows">
							<input type="hidden" id="page" name="page" value='1'>
							<a href="javascript:void(0);" iconCls="icon-clear" onclick="javascript:$('#searcFrom').form('reset');resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						<td>
						</td>	
						</td>	
					</tr>
				</table>			
				
			</div>
		</form>
	</div>
	<div class="repaymentListDiv" style="height:100%">
	<table id="grid" title="贷款展期" class="easyui-datagrid"
		style="height:100%; width: auto;"
		data-options="
	    url: 'extentionList.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	striped: true, 
	    fitColumns:true,
	    onLoadSuccess:function(data){
			clearSelectRows('#grid');
		}
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
				<th data-options="field:'projectName',width:'100',formatter:loanExtension.formatProjectName">项目名称</th>
				<th data-options="field:'projectNum',width:'100'">项目编号</th>
				<th data-options="field:'cusType',width:'100',formatter:loanExtension.formatCusType">客户类别</th>
				<th data-options="field:'requestDttm',width:'100'">贷款申请时间</th>
				<th data-options="field:'creditAmt',width:'100',align:'right',formatter:formatMoney">贷款金额</th>
				<th data-options="field:'pmUserName',width:'100'">项目经理</th>
				<th data-options="field:'planOutLoanDt',width:'100'">贷款开始日期</th>
				<th data-options="field:'planRepayLoanDt',width:'100'">贷款结束日期</th>
				<th data-options="field:'yy',width:'100',formatter:loanExtension.datiloperat">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
</body>