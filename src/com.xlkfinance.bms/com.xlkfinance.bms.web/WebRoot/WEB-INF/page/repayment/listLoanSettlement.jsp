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
	$("#project_Name").val('');
	$("#project_Id").val('');
	$("#cus_Name").val('');
	$("#acctType").combobox('setValue','');
	$("#request_StartDt").datebox('setValue','');
	$("#request_EndDt").datebox('setValue','');
	$("#ecoTrade").combobox('setValue','');
	$("#settleProcess_BeginDT").datebox('setValue','');
	$("#settleProcess_EndDT").datebox('setValue','');
}


//结清处理状态
function iscomplete(value, row, index){
	if(1==row.is_complete) {
		return "已结清";
	}else {
		return "";
	}
};


//客户类别
function acctTypeText(value, row, index){
	if(1==row.acctTypeText) {
		return "个人";
	}else if(2==row.acctTypeText) {
		return "企业";
	}else {
		return "";
	}
};

//查询 
function ajaxSearch(){
	var pageNumber=$('#grid').datagrid('options')['pageSize'];
	$('#rows').val(pageNumber);
	$('#searchFrom').form('submit',{
        success:function(data){
        	var str = JSON.parse(data);
           	$('#grid').datagrid('loadData',str);
       }
    });
};

$(function(){
	setCombobox("acct_type","CUS_TYPE","");
	setCombobox("eco_trade","ECO_TRADE","");
});	  
//删除
function removeItem(){
	  var rows = $('#grid').datagrid('getSelections');
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
	 	var pid = "";
		for(var i=0;i<rows.length;i++){
	  		if(i==0){
	  			pid+=rows[i].pid;
	  		}else{
	  			pid+=","+rows[i].pid;
	  		}
	  	}
	 	$.messager.confirm("操作提示","确定删除?",
			function(r) {
	 			if(r){
					$.post("deleteLoanSettle.action",{pid : pid}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								$("#dlg").dialog('close');
								$("#grid").datagrid('reload');
						},'json');
	 			}
			});
	  	
}

function datiloperat(value, row, index) {
	return "<a href='javascript:void(0)'  onclick='repayDatil("+row.pid+","+row.loanId+")'><font color='blue'>编辑</font></a>";
};
function repayDatil(pid,loanId,projectName){
	parent.openNewTab("${basePath }rePaymentController/toSettlement.action?projectId="+pid+'&loanId='+loanId,"项目结清办理编辑",true);
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
	<form action="getLoanSettle.action" method="post" id="searchFrom" name="searchFrom" >
		<!-- 查询条件 -->
		<div style="padding:5px">
			<table>
				<tr>
					<td class="label_right">项目名称:</td>
					<td><input type="text" name="projectName" class="text_style" id="project_Name" style="width:220px;" value=""/> </td>
					<td class="label_right">项目编号:</td>
					<td><input type="text" name="projectId" class="text_style" id="project_Id" style="width:220px;" value=""/></td>
					<td width="120"><div class="label_right" >客户名称:</div></td>
					<td><input type="text" name="cusName" class="text_style" id="cus_Name" value=""/></td>
				</tr>
				<tr>
					<td class="label_right">客户类别:</td>
					<td><select name="acctType" id="acctType"  value="" class="easyui-combobox"  style="width:220px;" data-options="editable:false">
						<option value="">---选择类型---</option>
						<option value="1">个人 </option>
						<option value="2">企业 </option>
					</select></td>
					<td class="label_right">结清处理时间:</td>
					<td>
						<input name="settleProcessBeginDT" type="text" class="easyui-datebox" data-options="editable:false"
						 style="width:100px;"  id="settleProcess_BeginDT" value="" />
						  <span>~</span> 
						 <input name="settleProcessEndDT" type="text" class="easyui-datebox" data-options="editable:false" id="settleProcess_EndDT" value="" style="width:100px;"  />
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
				</tr>
				<tr>
					<td class="label_right">贷款申请时间:</td>
					<td>
						<input name="requestStartDt" type="text" class="easyui-datebox" data-options="editable:false" style="width:100px;"  id="request_StartDt" value="" /> <span>~</span> <input name="requestEndDt" type="text" class="easyui-datebox" data-options="editable:false" id="request_EndDt" value="" style="width:100px;"  />
					</td>
					<td colspan="4">&nbsp;&nbsp;
						<input type="hidden" id="rows" name="rows">
						<input type="hidden" id="page" name="page" value='1'>
						<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
						<a href="#" class="easyui-linkbutton" iconCls="icon-clear" onclick="resetss();">重置</a>
					</td>
				</tr>
			</table>			
		</div>
	</form>
	
	<!-- 操作按钮 -->
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="removeItem()">删除</a>
	</div>
	</div>
	<div style="height:100%">
	<table id="grid" title="贷款结清客户信息管理" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: 'getLoanSettle.action',
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
			<th data-options="field:'loadId',width:100,hidden:true">
		    <th data-options="field:'projectName',formatter:formatProjectName,width:100" align="center">项目名称</th>
		    <th data-options="field:'projectId',width:100" align="center">项目编号</th>
		    <th data-options="field:'is_complete',width:100,formatter:iscomplete" align="center">结清处理状态</th>
		    <th data-options="field:'settleDT',width:100,formatter:convertDate" align="center">结清时间</th>
		    <th data-options="field:'creditAmt',width:100,align:'right',formatter:formatMoney">贷款金额</th>
		    <th data-options="field:'acctTypeText',width:100,formatter:acctTypeText" align="center">客户类别</th>
		    <th data-options="field:'pmuserName',width:100" align="center">项目经理</th>
		    <th data-options="field:'planOutLoanDT',width:100" align="center">贷款开始日期</th>
		    <th data-options="field:'planRepayLoanDT',width:100" align="center">贷款到期日期</th>
		    <th data-options="field:'yy',width:100, formatter:datiloperat" align="center">操作</th>
		</tr></thead>
	</table>
	</div>
	</div>
</body>



 