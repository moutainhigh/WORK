<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
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
function  ecoTradehide(){
	$(".ecoTrade").hide();	
}
function dateSplit(value, row, index){
	if(null!=row.requestLoanDttm&&""!=row.requestLoanDttm){
		return row.requestLoanDttm.split(" ")[0];
	}
	return row.requestLoanDttm;
}
function datePlanSplit(value, row, index){
	if(null!=row.planRepayLoanDt&&""!=row.planRepayLoanDt){
		return row.planRepayLoanDt.split(" ")[0];
	}
	return row.planRepayLoanDt;
}
function datecompelteSplit(value, row, index){
	if(null!=row.compelteAdvDttm&&""!=row.compelteAdvDttm){
		return row.compelteAdvDttm.split(" ")[0];
	}
	return row.compelteAdvDttm;
}
function daterequestSplit(value, row, index){
	if(null!=row.requestAdvDttm&&""!=row.requestAdvDttm){
		return row.requestAdvDttm.split(" ")[0];
	}
	return row.requestAdvDttm;
}
	function repayAdvList() {
		$('#grid').datagrid('load',{
			projectName:$.trim($('#projectName').textbox('getValue')),
			projectNum:$.trim($('#projectNum').textbox('getValue')),
			cusName:$.trim($('#cusName').textbox('getValue')),
			cusType:$('#cusType').combobox('getValue'),
			status:$('#status').combobox('getValue'),
			ecoTrade:$('#ecoTrade').combobox('getValue'),
			requestAdvDttm:$('#requestAdvDttm').datebox('getValue'),
			requestAdvDttmLast:$('#requestAdvDttmLast').datebox('getValue')
		});
	}
	function advApplyRepay(value, row, index) {
		var btn = "<a id='"+row.pid+"' class='easyui-linkbutton' onclick='disposeClick("+JSON.stringify(row)+")' href='#'>" +
			"<span style='color:blue; '>"+row.projectName+"</span></a>";  
	return btn; 
	}
	function disposeClick(row) {
		url = "<%=basePath%>rePaymentController/repaydatilbyProcess.action?preRepayId="
			+ row.preRepayId;
		parent.openNewTab(url, "提前还款查看", true);
	}
	function datiladvoperat(value, row, index) {
		if(row.status==1){
			var bt = "<a class='easyui-linkbutton' onclick='advappl("+JSON.stringify(row)+")' href='#'>" + "<span style='color:blue; '>"+ "编辑"+"</span></a>";  
			var projectDom = "|<a href='javascript:void(0)' onclick='reprojectbyid("+row.preRepayId+","+row.pId+")'><font color='blue'> 删除</font></a>";
			return bt+ projectDom;
		}else{
			var bt = "<a class='easyui-linkbutton' onclick='advapply("+JSON.stringify(row)+")' href='#'>" + "<span style='color:blue; '>"+ "查看"+"</span></a>";
			return bt+"| <font color='grey'>删除</font>";
		}
	}
	function advappl(row) {
		var url = "<%=basePath%>rePaymentController/repaydatilbyProcess.action?preRepayId=" + row.preRepayId;
		//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
		var datas = getTaskVoByWPDefKeyAndRefId("prepaymentRequestProcess",row.preRepayId);
		if(datas){
			url+="&"+datas;
		}
		parent.openNewTab(url, "提前还款编辑", true);
	}
	function advapply(row) {
		url = "<%=basePath%>rePaymentController/repaydatilbyProcess.action?preRepayId=" + row.preRepayId+"&isShow=true";
		parent.openNewTab(url, "提前还款查看", true);
	}
	function reprojectbyid(pId,projectId){
		 	$.messager.confirm("操作提示","确定删除选中行吗?",
				function(r) {
		 			if(r){
						$.post("<%=basePath%>rePaymentController/deleteProjectbyPreRepayId.action",{preRepayId : pId,projectId:projectId}, 
							function(ret) {
								if(ret > 0 ){
									$.messager.alert("操作提示","删除成功!","info");
									$("#grid").datagrid('reload');
								}else{
									$.messager.alert("操作提示","删除失败!","info");
								}
							},'json');
		 			}
				});
		  	
	}  
	//删除
	function removeItem(){
		 var rows = $('#grid').datagrid('getSelections');
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
	 	
	 	// 获取选中的系统用户名 
	 	var pids = "";
	 	var projectIds = "";
		for(var i=0;i<rows.length;i++){
			if(rows[i].status!=1){
				$.messager.alert("操作提示","请选择申请中的项目做删除！","error");
				return false;
			}
			if(i == (rows.length-1)){
				pids+=rows[i].preRepayId;
				projectIds+=rows[i].pId;
			}else{
				pids+=rows[i].preRepayId+",";
				projectIds+=rows[i].pId+",";
			}
		}
		
		reprojectbyid(pids,projectIds);
	}   
	function cusType(value, row, index){
		if (1==row.cusType) {
			return "个人";
		}
		if (2==row.cusType) {
			return "企业";
		}else{
			return "";
		}
		
	}
	//(申请中=0，办理中=1，审核=2)
	function requestStatus(value, row, index){
		if (1==row.status) {
			return "申请中";
		}
		if (2==row.status) {
			return "审核中";
		}if (4==row.status) {
			return "处理结束";
		}else{
			return "";
		}
		
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<div>
			<!-- 查询条件 -->
			<form action="advrepaymenturl.action" method="post" id="searcm"
				name="searcm">
				<div style="padding: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" name="projectName" style="width:220px;"  id="projectName" /></td>
							<td class="label_right">项目编号: </td>
							<td>
								<input class="easyui-textbox" name="projectNum" style="width:220px;" id="projectNum" />
							</td>
						</tr>
						<tr>
							<td class="label_right">客户名称:</td>
							<td>
							<input class="easyui-textbox" name="cusName"style="width:220px;"  id="cusName" /></td>
							<td class="label_right">客户类别: </td>
							<td>
								<select name="cusType" id="cusType" class="easyui-combobox"  style="width:220px;" >
							    <option value="">---选择类型---</option>
								<option value="1">个人 </option>
								<option value="2">企业 </option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label_right1">提前还款处理状态：</td>
							<td><select name="status" id="status"  class="easyui-combobox"  style="width:220px;" >
						     <option value="">---选择状态---</option>
							    <option value="1">申请中</option>
								<option value="2">审核中</option>
								<option value="4">处理结束 </option>
								</select></td>
							<td class="label_right1 ecoTrade">经济行业类别：</td>
							<td class="ecoTrade">
								<input class="easyui-combobox" style="width:220px;"  name="ecoTrade"
									id="ecoTrade" editable="false"
									data-options="
					            valueField:'pid',
					            textField:'lookupVal',
					            method:'get',
					            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
							</td>
						</tr>
						<tr>
							<td class="label_right">申请时间: </td>
							<td colspan="3">
								<input class="easyui-datebox"
									name="requestAdvDttm" id="requestAdvDttm" editable="false" /> <span>~</span>
								<input class="easyui-datebox" name="requestAdvDttmLast"
									id="requestAdvDttmLast" editable="false" />
							
							&nbsp;&nbsp;<a
								href="javascript:void(0);" class="easyui-linkbutton"
								iconCls="icon-search" onclick="repayAdvList()">查询</a>
								<a href="javascript:void(0);" onclick="javascript:$('#searcm').form('reset');ecoTradehide();" iconCls="icon-clear" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>							
						</tr>
					</table>	
						<div >
		
	</div>		
				</div>
			</form>
		</div>
		<div style="padding-bottom: 5px"><a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="removeItem()">删除</a></div>
	</div>
	
	<!-- 操作按钮 -->
	<div class="advrepaymentListDiv" style="height:100%">
		<table id="grid" title="提前还款管理" class="easyui-datagrid"
			style="height: 100%; width: auto;"
			data-options="
		     url:'advrepaymenturl.action',
		    rownumbers: true,
		    pagination: true,
		    idField: 'pId',
		    toolbar: '#toolbar',
		    fitColumns:true
		    ">
			<thead>
				<tr>
				  <th data-options="field:'pId',checkbox:true"></th> 
					<th	data-options="field:'projectId',width:150">项目编号</th>
					<th data-options="field:'projectName',width:150,formatter:advApplyRepay">项目名称</th>
					<th data-options="field:'status',width:150,formatter:requestStatus">提前还款状态</th>
					<th data-options="field:'requestAdvDttm',width:150,formatter:daterequestSplit">申请时间</th>
					<th data-options="field:'compelteAdvDttm',width:150,formatter:datecompelteSplit">办理结束时间</th>
					<th data-options="field:'cusType',width:150,formatter:cusType">客户类别</th>
					<th data-options="field:'creditAmt',width:150,align:'right',formatter:formatMoney">贷款金额</th>
					<th data-options="field:'pmuserName',width:150">项目经理</th>
					<th data-options="field:'requestLoanDttm',width:150,formatter:dateSplit">贷款开始时间</th>
					<th data-options="field:'planRepayLoanDt',width:150,formatter:datePlanSplit">贷款结束时间</th>
					<th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
				</tr>
			</thead>
		</table>
	</div>
</div>
</body>
