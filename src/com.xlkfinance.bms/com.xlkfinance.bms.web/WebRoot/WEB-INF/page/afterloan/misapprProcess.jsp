<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<script type="text/javascript">

$(document).ready(function(){
	$('#cusType').combobox({
		onSelect: function(row){
			if(row.value==2){
				$(".ecoTradeDiv").show();
			}else{
				$(".ecoTradeDiv").hide();
			}
		}
	});
	$(".ecoTradeDiv").hide();
});

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

function datiladvoperat(value, row, index) {
	var projectDom = "|<a href='javascript:void(0)' onclick='deldivertpor("+JSON.stringify(row)+")'><font color='blue'> 删除  </font></a>";
	var dom = "|<a href='javascript:void(0)' > 删除 </a>";
	if(row.reviewStatus!='申请中'){
		return "<a href='javascript:void(0)' onclick='divertinfo("+JSON.stringify(row)+")'><font color='blue'>查看 </font></a>"+dom ;
	}else{
		return "<a href='javascript:void(0)' onclick='divertinfo("+JSON.stringify(row)+")'><font color='blue'>   编辑  </font></a>" + projectDom;	
	}
}
function advApplyRepay(value, rowData, index) {
	var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pid+",\""+rowData.projectNumber+"\")' href='#'>" +
			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
	return btn;   
}

function divertinfo(row){
	if(row.reviewStatus!='申请中'){
		url = "${basePath}afterLoanDivertController/afterLoanDivertbyprocess.action?divertId="+row.divertId;
		parent.openNewTab(url,"挪用处理查看",true);
	}else{
		url = "${basePath}afterLoanDivertController/afterLoanDivertbyprocess.action?divertId="+row.divertId;
		parent.openNewTab(url,"挪用处理编辑",true);
	}
}
function deldivertpor(row){
	if (confirm("是否确认删除?")) {
	
	$.post(	'${basePath}afterLoanDivertController/delectDivertbyId.action',	{
				divertId : row.divertId,
				projectId : row.pid
			},
			function(data) {
				$.messager.alert("操作提示", "删除成功！", "success");
				$('#grid').datagrid('reload');
			});
	}
}


function disposeClick(row) {
	url = "<%=basePath%>beforeLoanController/addNavigation.action?projectId="+row.pid;
	parent.openNewTab(url,"贷款申请详情",true);
}

//重置
function resetss(){
	$('#searchFrom').form('reset');
	$(".ecoTradeDiv").hide();	
}

//查询
function repayList() {
	$('#grid').datagrid('load',{
		projectName:$.trim($('#projectName').textbox('getValue')),
		projectNumber:$.trim($('#projectNumber').textbox('getValue')),
		cusName:$.trim($('#cusName').textbox('getValue')),
		cusType:$('#cusType').combobox('getValue'),
		ecoTrade:$('#ecoTrade').combobox('getValue'),
		reviewStatus:$('#reviewStatus').combobox('getValue'),
		divertFineEndDt:$('#divertFineEndDt').datebox('getValue'),
		divertFinePayDt:$('#divertFinePayDt').datebox('getValue')
	});
}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="getMisappProcess.action" method="post" id="searchFrom" name="searchFrom" >
		<!-- 查询条件 -->
		<div style="margin:5px">
		<table class="beforeloanTable">
			<tr>
				<td class="label_right">项目名称：</td>
				<td><input class="easyui-textbox" name="projectName" id="projectName" style="width: 235px;"/></td>
				<td class="label_right" style="width:160px;">项目编号：</td>
				<td>
					<input class="easyui-textbox" name="projectNumber" id="projectNumber" style="width: 235px;"/>
				</td>
			</tr>
			
			<tr>
				<td class="label_right">客户名称：</td>
				<td><input class="easyui-textbox" name="cusName" id="cusName" style="width: 235px;" />
				<td class="label_right">客户类别：</td>
				<td>
					<select name="cusType"  id="cusType" class="easyui-combobox" style="width: 235px;" >
					<option value="">--请选择--</option>
					<option value="1">个人 </option>
					<option value="2">企业 </option>
					</select>
				</td>
			</tr>
			
			<tr>
				<td class="label_right" style="width:120px">挪用处理申请时间：</td>
				<td>
					<input name="divertFineEndDt" type="text" class="easyui-datebox" style="width:100px;"  id="divertFineEndDt" value="" />&nbsp;~&nbsp;<input name="divertFinePayDt" type="text" class="easyui-datebox" id="divertFinePayDt" value="" style="width:100px;"  />
				</td>
				<td class="label_right"><div class="ecoTradeDiv">经济行业类别：</div></td>
				<td>
					<div class="ecoTradeDiv">
						<input class="easyui-combobox" style="width: 235px;" name="ecoTrade"
							id="ecoTrade" editable="false"
							data-options="
				            valueField:'pid',
				            textField:'lookupVal',
				            method:'get',
				            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
		            </div>
				</td>
			</tr>

		   <tr>
				<td class="label_right">处理状态：</td>
				<td>
					<select name="reviewStatus" id="reviewStatus" class="easyui-combobox" style="width: 235px;">
						<option value="">全部</option>
						<option value="1">申请中</option>
						<option value="2">审核中</option>
						<option value="4">处理结束</option>
					</select>
				</td>

				<td colspan="2" align="right">
					<a href="javascript:void(0);"class="easyui-linkbutton" iconCls="icon-search" onclick="repayList()">查询</a>
					<a href="javascript:void(0);"iconCls="icon-clear" onclick="resetss()" class="easyui-linkbutton" style="width: 60px;">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<!-- 操作按钮 -->
	</div>
	<div style="height:100%">
	<table id="grid" title="贷款挪用处理" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: 'getMisappProcess.action',
	    method: 'POST',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	striped: true, 
	 	loadMsg: '数据加载中请稍后……',
	    fitColumns:true,
	    singleSelect: true,
	    singleOnCheck: true,
		selectOnCheck: false,
		checkOnSelect: false
	    ">
	<!-- 表头 -->
		<thead><tr>
			<th data-options="field:'pid',checkbox:true,width:'100'" ></th>
		    <th data-options="field:'projectName',width:'100',formatter:advApplyRepay">项目名称</th>
		    <th data-options="field:'projectNumber',width:'100'">项目编号</th>
		    <th data-options="field:'cusType',width:'100',formatter:cusType">客户类型</th>
		    <th data-options="field:'divertAmt',width:'100',formatter:formatMoney">挪用金额</th>
		    <th data-options="field:'divertFineEndDt',width:'100'">挪用记息结束日</th>
		    <th data-options="field:'divertFinePayAmt',width:'100',formatter:formatMoney">挪用罚息</th>
		    <th data-options="field:'divertFinePayDt',width:'100'">挪用罚息应收日期</th>
		    <th data-options="field:'reviewStatus',width:'100'">处理状态</th>
		    <th data-options="field:'requestDttm',width:'100'">申请时间</th>
		    <th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
		</tr></thead>
	</table>
	</div>
	</div>
</body>