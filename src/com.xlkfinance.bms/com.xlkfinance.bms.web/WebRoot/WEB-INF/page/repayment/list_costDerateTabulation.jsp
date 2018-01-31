<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">
//格式化日期
function dateSplit(value, row, index){
	if(null!=row.requestLoanDttm&&""!=row.requestLoanDttm){
		return row.requestLoanDttm.split(" ")[0];
	}
	return row.requestLoanDttm;
}
function requestDt(value, row, index){
	if(null!=row.requestDttm&&""!=row.requestDttm){
		return row.requestDttm.split(" ")[0];
	}
	return row.requestDttm;	
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

function advApplyRepay(value, rowData, index) {
	var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='feeDisposeClick("+JSON.stringify(rowData)+")' href='#'>" +
			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
	return btn;   	
}
//费用减免申请
function feeDisposeClick(row) {
	url = "<%=basePath%>repayFeewdtlController/repayFeewdtlDealbyprocess.action?repayId="
			+ row.repayId;
	parent.openNewTab(url, "费用减免查看", true);
}

function datiladvoperat(value, row, index) {
	if(row.requestStatus==1){
		var bt = "<a class='easyui-linkbutton' onclick='advappl("+JSON.stringify(row)+")' href='#'>" +
		"<span style='color:blue; '>"+ "编辑"+"</span></a>";  
		var projectDom = "|<a href='javascript:void(0)' onclick='reprojectbyid("+JSON.stringify(row)+")'><font color='blue'> 删除</font></a>";
		return bt+ projectDom;
		}else{
			var bt = "<a class='easyui-linkbutton' onclick='advapply("+JSON.stringify(row)+")' href='#'>" +
			"<span style='color:blue; '>"+ "查看"+"</span></a>";
			var projectDom = "|<a href='javascript:void(0)' onclick='reprojectbyid("+JSON.stringify(row)+")'><font color='blue'> 删除</font></a>";
			return bt+"|删除";
		}	
	
}

//跳转
function advappl(row) {
	url = "<%=basePath%>repayFeewdtlController/repayFeewdtlDealbyprocess.action?repayId="
		+ row.repayId;
	//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
	var datas = getTaskVoByWPDefKeyAndRefId("feeWaiversRequestProcess",row.repayId);
	if(datas){
		url+="&"+datas;
	}
	parent.openNewTab(url, "费用减免编辑", true);
}
	
function advapply(row) {
	url = "<%=basePath%>repayFeewdtlController/repayFeewdtlDealbyprocess.action?repayId="
			+ row.repayId;
	parent.openNewTab(url, "费用减免查看", true); 
}
function reprojectbyid(row){
	$.messager.confirm(
		"操作提示",
		"确定删除吗?",
		function(r) {
			if (r) {$.post("${basePath}repayFeewdtlController/deleteProjectbyFeewDeal.action",
				{
					repayId : row.repayId,
					projectId : row.pId
				}, function(ret) {
					$("#grid").datagrid(
							'reload');
				}, 'json');

			}
		});
}
//删除
function removeItem() {
	var rows = $('#grid').datagrid('getSelections');
	if (rows.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	}// 获取选中的系统用户名 
	var stat = 1;
	for (var i = 0; i < rows.length; i++) {
		if (rows[i].requestStatus != 1) {
			stat = 2;
			break;
		}
	}
	if (stat == 2) {
		$.messager.alert("操作提示", "请选择申请中的项目做删除！", "error");
		return false;
	}
	$.messager.confirm(
					"操作提示",
					"确定删除吗?",
					function(r) {
						if (r) {
							var repayIds = [];
							var projectIds = [];
							for (var i = 0; i < rows.length; i++) {
								repayIds[repayIds.length] = rows[i].repayId;
								projectIds[projectIds.length] = rows[i].pId;
							}
							$.post("${basePath}repayFeewdtlController/deleteProjectbyFeewDealList.action"
								,{"repayIds":repayIds,
								 "projectIds":projectIds
								 }
								,function(ret) {
									$("#grid").datagrid('reload');
								}, 'json');
						}
					});

}

function cusType(value, row, index) {
	if (1 == row.cusType) {
		return "个人";
	}
	if (2 == row.cusType) {
		return "企业";
	} else {
		return "";
	}

}

//重置
function resetss() {
	$(".ecoTrade").hide();
}
//(申请中=0，办理中=1，审核=2)
function requestStatus(value, row, index) {
	if (1 == row.requestStatus) {
		return "申请中";
	}
	if (2 == row.requestStatus) {
		return "审核中";
	}
	if (3 == row.requestStatus) {
		return "已否决";
	} else {
		return "申请完成";
	}
}

//隐藏行业类别
$(document).ready(function() {
	$('#cusType').combobox({
		onSelect : function(row) {
			if (row.value == 2) {
				$(".ecoTrade").show();
			} else {
				$(".ecoTrade").hide();
			}
		}
	});
	$(".ecoTrade").hide();
});

//查询
function repayAdvList() {
	if (!compareDate('requestDttm', 'requestDttmLast')) {
		return false;
	}
	$('#grid').datagrid('load', {
		projectName : $.trim($('#projectName').textbox('getValue')),
		projectId : $.trim($('#projectId').textbox('getValue')),
		cusName : $.trim($('#cusName').textbox('getValue')),
		cusType : $('#cusType').combobox('getValue'),
		requestStatus : $('#requestStatus').combobox('getValue'),
		ecoTrade : $('#ecoTrade').combobox('getValue'),
		requestDttm : $('#requestDttm').datebox('getValue'),
		requestDttmLast : $('#requestDttmLast').datebox('getValue')
	});
};
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<!-- 查询条件 -->
			<form action="costDerateTabulation.action" method="post" id="searcm"
				name="searcm">
				<div style="padding: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" style="width:220px" name="projectName" id="projectName" /></td>
							<td class="label_right">项目编号: </td>
							<td>
								<input class="easyui-textbox" name="projectId" id="projectId" />
							</td>
						</tr>
						<tr>
							<td class="label_right">客户名称:</td>
							<td><input class="easyui-textbox" style="width:220px" name="cusName" id="cusName" /></td>
							<td class="label_right">客户类别: </td>
							<td>
								<select name="cusType" id="cusType" class="easyui-combobox"  style="width:150px;" >
								<option value="">---选择类型---</option>
								<option value="1">个人 </option>
								<option value="2">企业 </option>
								</select>
							</td>
						</tr>
						<tr>
							<td class="label_right1">处理状态: </td>
							<td>
								<select name="requestStatus" id="requestStatus" class="easyui-combobox"  style="width:220px;"  editable="false">
								<option value="">--全部--</option>
								<option value="1">申请中 </option>
								<option value="2">审核中 </option>								
								<option value="3">已否决 </option>
								<option value="4">申请完成 </option>
								</select>
							</td>
							<td class="label_right1 ecoTrade">经济行业类别：</td>
							<td class="ecoTrade">
								<input class="easyui-combobox" name="ecoTrade"
									id="ecoTrade" editable="false"
									data-options="
					            valueField:'pid',
					            textField:'lookupVal',
					            method:'get',
					            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
							</td>
						</tr>
						<tr>
							<td class="label_right" style="width:130px;">费用减免申请时间: </td>
							<td colspan="3">
								<input class="easyui-datebox"
									name="requestDttm" id="requestDttm" editable="false" /> <span>~</span>
								<input class="easyui-datebox" name="requestDttmLast"
									id="requestDttmLast" editable="false" />
							
							&nbsp;&nbsp;<a
								href="javascript:void(0);" class="easyui-linkbutton"
								iconCls="icon-search" onclick="repayAdvList()">查询</a>
								<a href="javascript:void(0);" iconCls="icon-clear" onclick="javascript:$('#searcm').form('reset');resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>							
						</tr>
					</table>	
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"  onclick="removeItem()">删除</a>
				</div>
			</form>
		</div>
		<div style="height:100%">
			<table id="grid" title="费用减免列表" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
			     url:'costDerateTabulation.action',
			    rownumbers: true,
			    pagination: true,
			    idField: 'repayId',
			    toolbar: '#toolbar',
			    fitColumns:true
			    ">
				<!-- 表头 -->
				<thead>
					<tr>
					  <th data-options="field:'repayId',checkbox:true"></th> 
						<th	data-options="field:'projectId',width:150">项目编号</th>
						<th data-options="field:'projectName',width:150,formatter:advApplyRepay">项目名称</th>
						<th data-options="field:'requestStatus',width:150,formatter:requestStatus">处理状态</th>
						<th data-options="field:'requestDttm',width:150 ,formatter:requestDt">申请时间</th>
						<th data-options="field:'compelteAdvDttm',width:150,formatter:datecompelteSplit">办理结束时间</th>
						<th data-options="field:'cusType',width:150,formatter:cusType">客户类别</th>
						<th data-options="field:'creditAmt',width:150,align:'right',formatter:formatMoney">贷款金额</th>
						<th data-options="field:'pmuserName',width:150">项目经理</th>
						<th data-options="field:'planOutLoanDt',width:150">贷款开始时间</th>
						<th data-options="field:'planRepayLoanDt',width:150">贷款结束时间</th>
						<th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>