<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript">
//隐藏行业类别
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

//重置
function resetss(){
	$(".ecoTrade").hide();	
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

function datiloperat(value, row, index) {
	var projectDom = "<a href='javascript:void(0)' onclick='feewapply("+row.pId+")'><font color='blue'> 费用减免申请</font></a>";
	return projectDom;
}

/**
 * 费用减免申请
 */
function feewapply(projectId){
	var result = true;
	// 3 验证项目本金金额是否已还清
	$.ajax({
		type: "POST",
    	url : '<%=basePath%>loanExtensionController/getExtensionNum.action?projectId='+projectId,
		async:false, 
	    success: function(data){
	    	if(data.length == 2){
	    		result = false;
	    	}
		}
	});
	//根据项目ID判断是否有流程在执行
	if(isWorkflowByStatus(projectId)){
		return;
	}
	var result = true;
	$.ajax({
		type: "POST",
    	url : "${basePath}workflowController/isAllowStartWorkflowByProjectId.action?projectId="+projectId,
		async:false, 
	    success: function(data){
	    	result = data;
		}
	});
	if(result != 'false'){
		$.post("${basePath}repayFeewdtlController/checkFeewDealByProjectId.action", {projectId : projectId},
				function(da){
			if(da>0){
				$.messager.alert("操作提示","此项目已经有费用减免申请流程了！","info");
			}else{
				url ="${basePath}repayFeewdtlController/repayFeewdtlDeal.action?projectId="+projectId; 
				parent.openNewTab(url,"费用减免申请",true);
			}
		});
	
	}else{
		$.messager.alert('操作提示',"该项目已有流程在執行!",'error');
		
	}

}

//查询
function repayList() {
	if(!compareDate('requestDttm','requestDttmLast')){
		return false;
	}
	$('#grid').datagrid('load',{
		projectName:$.trim($('#projectName').textbox('getValue')),
		projectId:$.trim($('#projectId').textbox('getValue')),
		cusName:$.trim($('#cusName').textbox('getValue')),
		cusType:$('#cusType').combobox('getValue'),
		ecoTrade:$('#ecoTrade').combobox('getValue'),
		requestDttm:$('#requestDttm').datebox('getValue'),
		requestDttmLast:$('#requestDttmLast').datebox('getValue')
	});
}

function advApplyRepay(value, rowData, index) {
	var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='feeDisposeClick("+rowData.pId+",\""+rowData.projectName+"\")' href='#'>" +
			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
	return btn;   
}

function feeDisposeClick(pid,pName) {
	var url = "";// 路径
 	var	url = "${basePath}beforeLoanController/addNavigation.action?projectId="+pid+"&param='refId':'"+pid+"','projectName':'"+pName+"'";
		parent.openNewTab(url,"查看贷款申请",true);
}

//项目名称format
var formatProjectName=function (value, rowData, index) {
	var btn = "<a id='"+rowData.pId+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pId+",\""+rowData.projectId+"\")' href='#'>" +
		"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
	return btn;   
}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<!-- 查询条件 -->
			<form action="listcostDerate.action" method="post" id="searcFrom"
				name="searcFrom">
				<div style="padding: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td>
								<input class="easyui-textbox" style="width:220px" name="projectName" id="projectName" />
							</td>
							<td class="label_right">项目编号：</td>
							<td>
								<input class="easyui-textbox" style="width:220px" name="projectId" id="projectId" />
							</td>
						</tr>
						<tr>
							<td class="label_right">客户名称:</td>
							<td><input class="easyui-textbox" style="width:220px" name="cusName" id="cusName" /></td>
							<td class="label_right">客户类别:</td>
							<td>
								<select name="cusType" id="cusType" class="easyui-combobox" style="width:220px;" editable="false">
									<option value="">---选择类型---</option>
									<option value="1">个人 </option>
									<option value="2">企业 </option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label_right">贷款申请时间:</td>
							<td >
								<input class="easyui-datebox" name="requestDttm"
								id="requestDttm" editable="false" /> <span>~</span> <input
								class="easyui-datebox" name="requestDttmLast" id="requestDttmLast"
								editable="false" />
							</td>						
							<td class="label_right1 ecoTrade">经济行业类别：</td>
							<td class="ecoTrade">
								<input class="easyui-combobox" style="width:150px;"  name="ecoTrade"
									id="ecoTrade" editable="false"
									data-options="
					            valueField:'pid',
					            textField:'lookupVal',
					            method:'get',
					            url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
							</td>
							<td colspan="3">			
								<a href="javascript:void(0);"class="easyui-linkbutton" iconCls="icon-search"
								onclick="repayList()">查询</a>
								<a href="javascript:void(0);" iconCls="icon-clear" onclick="javascript:$('#searcFrom').form('reset');resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>
						</tr>
					</table>	
				</div>
			</form>
		</div>
		<div class="repaymentListDiv" style="height:100%">
			<table id="grid" title="费用减免信息管理" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
			    url: '${basePath}rePaymentController/listcostDerate.action',
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
						<th data-options="field:'projectName',width:100,formatter:formatProjectName">项目名称</th>
						<th data-options="field:'projectId',width:100">项目编号</th>
						<th data-options="field:'pId',width:100,hidden:true">项目编号</th>
						<th data-options="field:'cusType',width:100,formatter:cusType">客户类别</th>
						<th data-options="field:'requestDttm',width:100">贷款申请时间</th>
						<th data-options="field:'creditAmt',width:100,align:'right',formatter:formatMoney">贷款金额</th>
						<th data-options="field:'pmuserName',width:100">项目经理</th>
						<th data-options="field:'planOutLoanDt',width:100">贷款开始日期</th>
						<th data-options="field:'planRepayLoanDt',width:100">贷款结束日期</th>
						<th data-options="field:'yy',width:200,formatter:datiloperat">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>