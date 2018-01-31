<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/repayment/extensionApply/list_loanExtensionList.js"></script>
<style type="text/css">
</style>
<script type="text/javascript">
$(document).ready(function(){
	$('#cusType').combobox({
		onSelect: function(row){
			if(row.value==2){
				$(".ecoTrade").show();
			}else{
				$(".ecoTrade").hide();
			}
		}
	});
	$(".ecoTrade").hide();
});


	
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<div>
			<!-- 查询条件 -->
			<form action="extentionRequestList.action" method="post" id="searcm"
				name="searcm">
				<div style="padding: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" name="projectName" style="width:220px"  id="projectName" />
							</td>
							<td class="label_right">项目编号: </td>
							<td><input class="easyui-textbox" name="projectNum" style="width:220px"  id="projectNum" />
								
							</td>
						</tr>
						<tr>
							<td class="label_right">客户名称:</td>
							<td><input class="easyui-textbox" name="cusName"  style="width:220px"  id="cusName" /></td>
							<td class="label_right">客户类别: </td>
							<td>
								<select name="cusType" id="cusType"  class="easyui-combobox"  style="width:220px"  >
								<option value=""></option>
								<option value="1">个人 </option>
								<option value="2">企业 </option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label_right1">处理状态：</td>
							<td>
								<select name="requestStatus" id="requestStatus"  class="easyui-combobox"  style="width:220px"  editable="false">
								<option value="">请选择</option>
								<option value="1">申请中 </option>
								<option value="2">审核中 </option>
								<option value="4">已归档 </option>
								<option value="7">已否决</option>
								</select>
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
							<td class="label_right">展期申请时间: </td>
							<td colspan="3">
								<input class="easyui-datebox"
									name="requestDttm" id="requestDttm" editable="false" /> <span>~</span>
								<input class="easyui-datebox" name="requestDttmLast"
									id="requestDttmLast" editable="false" />
							<input type="hidden" id="rows" name="rows">
							<input type="hidden" id="page" name="page" value='1'>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a
								href="javascript:void(0);" class="easyui-linkbutton"
								iconCls="icon-search" onclick="loanExtensionList.searchData()">查询</a>
								<a href="javascript:void(0);" iconCls="icon-clear" onclick="javascript:$('#searcm').form('reset');resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>							
						</tr>
					</table>	
						<div >
	</div>		
	
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="loanExtensionList.batchRemove()">删除</a>
				</div>
			</form>
	
	
	</div>
	
		<!-- 操作按钮 -->
		
	</div>

	<div class="loanExtensionListDiv" style="height:100%">
	
	<table id="grid" title="展期申请列表" class="easyui-datagrid"
		style="height: 100%; width: auto;"
		data-options="
	     url:'extentionRequestList.action',
       method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleOnCheck: true,
		selectOnCheck: true,
		 singleSelect: false,
		checkOnSelect: true,
		onLoadSuccess:function(data){
				clearSelectRows('#grid');
			}
	    ">
		<!-- 表头 -->
		<thead>
			<tr>
			  <th data-options="field:'pId',checkbox:true"></th> 
				<th	data-options="field:'projectNum',width:150">项目编号</th>
				<th data-options="field:'projectName',width:150,formatter:loanExtensionList.formatProjectName">项目名称</th>
				<th data-options="field:'requestStatusVal',width:150">处理状态</th>
				<th data-options="field:'requestDttm',width:150">申请时间</th>
				<th data-options="field:'cusType',width:150,formatter:loanExtensionList.formatCusType">客户类别</th>
				<th data-options="field:'creditAmt',width:150,align:'right',formatter:formatMoney">贷款金额</th>
				<th data-options="field:'pmUserName',width:150">项目经理</th>
				<th data-options="field:'planOutLoanDt',width:150">贷款开始时间</th>
				<th data-options="field:'planRepayLoanDt',width:150">贷款结束时间</th>
				<th data-options="field:'yy',width:150,formatter:loanExtensionList.formatOperation">操作</th>
			</tr>
		</thead>
	</table>
	</div>
	</div>
</body>