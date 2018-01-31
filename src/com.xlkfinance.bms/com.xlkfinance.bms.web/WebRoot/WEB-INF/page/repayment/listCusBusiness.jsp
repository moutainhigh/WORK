<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">


$(document).ready(function(){
	$('#acctType').combobox({
		onSelect: function(row){
			if(row.value==2){
				$(".ecoTrade").show();
			}else if(row.value==""){
				$(".ecoTrade").hide();
			}else {
				$(".ecoTrade").hide();
			}
		}
	});
	$(".ecoTrade").hide();
});

//重置
function resetss(){
	$(".ecoTrade").hide();	
	$('#searchFrom').form('reset');
}
//查询 
function ajaxSearch(){
	$('#grid').datagrid('load',{
		projectName:$.trim($('#projectName').textbox('getValue')),
		projectId:$.trim($('#projectId').textbox('getValue')),
		cusName:$.trim($('#cusName').textbox('getValue')),
		acctType:$('#acctType').combobox('getValue'),
		ecoTrade:$('#ecoTrade').combobox('getValue'),
		requestStartDt:$('#requestStartDt').datebox('getValue'),
		requestEndDt:$('#requestEndDt').datebox('getValue')
	});
};

$(function(){
	setCombobox("acct_type","CUS_TYPE","");
	setCombobox("eco_trade","ECO_TRADE","");
	
});	  
function datiloperat(value, row, index) {
	return "<a href='javascript:void(0)'  onclick='repayDatil("+row.pid+","+row.loanId+")'><font color='blue'>结清办理</font></a>";
};

function repayDatil(pid,loanId){
	parent.openNewTab("${basePath }rePaymentController/toSettlement.action?projectId="+pid+'&loanId='+loanId,"项目结清办理",true);
};
//项目名称format
var formatProjectName=function (value, rowData, index) {
	var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pid+",\""+rowData.projectId+"\")' href='#'>" +
		"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
	return btn;   
}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<!-- 查询条件 -->
		<form id="searchFrom" >
		<div style="padding:5px">
			<table>
				<tr>
					<td class="label_right">项目名称:</td>
					<td><input  name="projectName" class="easyui-textbox" id="projectName" value=""/> </td>
					<td class="label_right">项目编号:</td>
					<td><input  name="projectId" class="easyui-textbox" id="projectId" style="width: 220px;" value=""/></td>
					<td class="label_right">客户名称:</td>
					<td colspan="2"><input  name="cusName" class="easyui-textbox" id="cusName" value=""/></td>
				</tr>
				<tr>

					<td class="label_right">客户类别: </td>
					<td>
						<select name="acctType" id="acctType"  class="easyui-combobox" style="width:150px;" editable="false">
					    <option value="">---选择类型---</option>
						<option value="1">个人 </option>
						<option value="2">企业 </option>
						</select>
					</td>
					
					<td class="label_right">申请时间:</td>
					<td>
						<input name="requestStartDt"  class="easyui-datebox" style="width:100px;"  id="requestStartDt" value="" /> <span>~</span>
						<input name="requestEndDt"  class="easyui-datebox" id="requestEndDt" value="" style="width:100px;"  />
					</td>
					<td class="label_right1 ecoTrade">经济行业类别：</td>
					<td class="ecoTrade">
						<input class="easyui-combobox" style="width:150px;"  name="ecoTrade"
							id="ecoTrade" editable="false"
							data-options="
			            valueField:'pid',
			            textField:'lookupVal',
			            method:'get',
			            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
					</td>
					<td style="width: 140px;text-align: right;">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-clear" onclick="resetss();">重置</a>
					</td>
				</tr>
			</table>
		</div>
		</form>
	<!-- 操作按钮 -->
	</div>
	<div style="height:100%">
	<table id="grid" title="待结清客户信息管理" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: '${basePath }rePaymentController/getCusBusiness.action',
	    method: 'POST',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true
	    ">
	<!-- 表头 -->
		<thead><tr>
			<th data-options="field:'pid',checkbox:true,width:100" ></th>
		    <th data-options="field:'projectName',formatter:formatProjectName,width:100" align="center">项目名称</th>
		    <th data-options="field:'projectId',width:100" align="center">项目编号</th>
		    <th data-options="field:'acctTypeText',width:100" align="center">客户类型</th>
		    <th data-options="field:'requestStartDt',width:100,formatter:convertDate" align="center">贷款申请时间</th>
		   	<th data-options="field:'creditAmt',width:100,align:'right',formatter:formatMoney">贷款金额</th>
		    <th data-options="field:'pmuserName',width:100" align="center">项目经理</th>
		    <th data-options="field:'planOutLoanDT',width:100" align="center">贷款开始日期</th>
		    <th data-options="field:'planRepayLoanDT',width:100" align="center">贷款到期日期</th>
		    <th data-options="field:'yy',width:100, formatter:datiloperat">操作</th>
		</tr></thead>
	</table>
	</div>
	</div>
</body>



 