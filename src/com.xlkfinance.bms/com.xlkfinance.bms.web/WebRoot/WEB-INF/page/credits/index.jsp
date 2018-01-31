<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
var url = "";
$(document).ready(function(){
	majaxSearch();
	$("#ecoTradeId1").hide();
	$("#ecoTradeId2").hide();
	
	$('#acctType').combobox({
		onSelect: function(row){
			var opts = $(this).combobox('options');
			if(row[opts.textField]=='企业')
			{
				$("#ecoTradeId1").show();
				$("#ecoTradeId2").show();
			}else{
				$("#ecoTradeId1").hide();
				$("#ecoTradeId2").hide();
			}
 
		}
	});
})
// 新增
function addItem(){
	var row = $('#grid').datagrid('getChecked');
	if (row.length == 1) {
		$.ajax({
			type: "POST",
	    	url : "<%=basePath%>creditsController/getCreditsLineProjectId.action?projectId="+row[0].projectId,
			async:false, 
		    success: function(data){
		    	if(data == 1){
		    		$('#fm').form('reset');
		    		if (row.length == 1) {
		    	 		var result = true;
		    			$.ajax({
		    				type: "POST",
		    		    	url : "<%=basePath%>workflowController/isAllowStartWorkflowByProjectId.action?projectId="+row[0].projectId,
		    				async:false, 
		    			    success: function(data){
		    			    	result = data;
		    				}
		    			});
		    			if(result != 'false'){ //如果项目没有执行其它流程就进入冻结页面 
		    				// 将 url 地址改变
		    				var url="<%=basePath%>creditsCustomerInfoController/toAddCredit.action?projectId="+row[0].projectId+"&newProjectId=-1&biaoZhi=-1";
		    				// 打开新的tab页面
		    				parent.openNewTab(url,"新增额度提取",true);
		    			}else{
		    				$.messager.alert('操作提示',"该项目已有流程在執行!",'error');
		    			} 
		    		}else if(row.length > 1){
		    			$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
		    		}else{
		    			$.messager.alert("操作提示","请选择数据","error");
		    		}
		    	}else {
		    		$.messager.alert("操作提示","项目已提取，项目未归档不能继续提取","error");
		    	}
			}
		});
	}
}

// 冻结
function editItem(){
	var row = $('#grid').datagrid('getChecked');
 	if (row.length == 1) {//x选中一条 
 		var result = true;
		$.ajax({
			type: "POST",
	    	url : "<%=basePath%>workflowController/isAllowStartWorkflowByProjectId.action?projectId="+row[0].projectId,
			async:false, 
		    success: function(data){
		    	result = data;
			}
		});
		if(result != 'false'){ //如果项目没有执行其它流程就进入冻结页面 
		 	var amt =  row[0].availableAmt;//可用金额 
		 	if(amt!=0){
		 		var parma =row[0].creditId+","+3+","+row[0].availableAmt+","+row[0].freezeAmt;
		 		// 将 url 地址改变
				<%-- url = "<%=basePath%>creditsCustomerInfoController/toEditfreeze.action?projectId="+row[0].projectId+"&creditId="+row[0].creditId+"&creditUseType="+3+"&creditLimit="+row[0].availableAmt+"&freezeLimit="+row[0].freezeAmt; --%>
				url = "<%=basePath%>creditsCustomerInfoController/toEditfreeze.action?projectId="+row[0].projectId + "&parma="+parma;
				//window.location.href = url;
				parent.openNewTab(url,"冻结申请页面",true);
	 		}else{
	 			$.messager.alert("操作提示","可用额度为【0】,不能进行冻结","error");
	 		}
		 }else{
			$.messager.alert('操作提示',"该项目已有流程在執行!",'error');
		} 
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

//解冻
function editItemInfo(){
	var row = $('#grid').datagrid('getChecked');
 	if (row.length == 1) {
 	 	//判断是否有其它任务在执行
 		var result = true;
		$.ajax({
			type: "POST",
	    	url : "<%=basePath%>workflowController/isAllowStartWorkflowByProjectId.action?projectId="+row[0].projectId,
			async:false, 
		    success: function(data){
		    	result = data;
			}
		});
		if(result != 'false'){ //如果项目没有执行其它流程就进入解冻页面 
	 		var amt =  row[0].freezeAmt;//冻结额度 
	 		if(amt!=0){
	 			var parma =row[0].creditId+","+4+","+row[0].availableAmt+","+row[0].freezeAmt;
	 	 		// 将 url 地址改变
	 			url = "<%=basePath%>creditsCustomerInfoController/toEditfreeze.action?projectId="+row[0].projectId + "&parma="+parma;
	 		//	window.location.href = url;
	 			parent.openNewTab(url,"解冻申请页面",true);
	 		}else{
	 			$.messager.alert("操作提示","冻结额度为【0】,不能进行解冻","error");
	 		}
		}else{
			$.messager.alert('操作提示',"该项目已有流程在執行!",'error');
			
		} 
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

// 删除
function removeItem(){
	var rows = $('#grid').datagrid('getSelections');
 	if ( rows.length == 0 ) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
 	var pids = "";
	for (var i = 0; i < rows.length; i++) {
		if (i == 0) {
			pids += rows[i].pid;
		} else {
			pids += "," + rows[i].pid;
		}
	}
 	$.messager.confirm("操作提示","确认删除选择的数据?",
		function(r) {
 			if(r){
				$.post("<%=basePath%>beforeLoanController/deleteProject.action",{pids : pids}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						if(ret && ret.header["code"] == 200){
							location.reload();
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json');
 			}
		});
}

function pNameFilter(value,row,index){
	loadHistoryApprover(row.projectId,"creditWithdrawalRequestProcess");
	return  "<a href=\"<%=basePath%>beforeLoanController/addNavigation.action?projectId="+row.projectId+"&param='refId':'"+row.projectId+"','projectName':1\"><font color='blue'>"+value+"</font></a>"
}
// 查询 
function majaxSearch(){
	ajaxSearchPage('#grid','#searchFrom');
}

</script>
<title>额度管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>creditsController/getAllCredits.action" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="80" align="right" height="28">项目编号：</td>
						<td><input class="easyui-textbox" style="width:220px" name="projectNumber" id="projectNumber"/></td>
						<td width="80" align="right" >项目名称：</td>
						<td><input class="easyui-textbox" name="projectName" id="projectName"/></td>
						<td width="120" align="right" >客户名称：</td>
						<td><input class="easyui-textbox" name="acctName" id="acctName"/> </td>
					</tr>
					<tr>
						<td width="80" align="right"  height="28">客户类别：</td>
						<td><input name="acctType" id="acctType" style="width:220px" class="easyui-combobox" editable="false" panelHeight="auto"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" /></td>
						<td  align="right" >循环种类：</td>
						<td>
							<select class="easyui-combobox" id="isHoop" name="isHoop" style="width:150px;" panelHeight="auto" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
							    <option value=1 >可循环</option>
								<option value=2 >不可循环</option>
							</select>
						</td>
						<td width="120" align="right" id="ecoTradeId1">经济行业类别：</td>
						<td id="ecoTradeId2"><input name="ecoTrade" id="ecoTrade" class="easyui-combobox" editable="false" panelHeight="auto"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
					</tr>
					<tr>
						<td width="80" align="right"  height="28">申请时间：</td>
						<td  colspan="4">
							<input name="beginRequestDttm" id="beginRequestDttm" class="easyui-datebox" editable="false"/> -- 
							<input name="endRequestDttm" id="endRequestDttm" class="easyui-datebox" editable="false"/>
						</td>
						<td>
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="majaxSearch()">查询</a>
							<a href="#" onclick="majaxReset('#searchFrom')" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>
				</table>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">提款申请</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">冻结</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="editItemInfo()">解冻</a>
		</div>
		
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="额度信息查询管理" class="easyui-datagrid" 
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>creditsController/getAllCredits.action',
		    method: 'post',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    toolbar: '#toolbar',
		    idField: 'projectId',
		    fitColumns:true">
		<!-- 表头 -->
		<thead><tr>
		    <th data-options="field:'projectId',checkbox:true" ></th>
		    <th data-options="field:'projectName',width:80,formatter:projectNameHyperlink" halign="center" align="center">项目名称</th>
		    <th data-options="field:'projectNumber',width:80"  halign="center" align="center">项目编号</th>
		    <th data-options="field:'requestDttm',width:80"  halign="center" align="center">额度申请时间</th>
		    <th data-options="field:'isHoopVal',width:80"  halign="center" align="center">额度种类</th>
		    <th data-options="field:'creditAmt',align:'right',width:80,formatter:formatMoney"  halign="center" >总额度</th>
		    <th data-options="field:'extractionAmt',align:'right',width:80,formatter:formatMoney"  halign="center" >已提取额度</th>
		    <th data-options="field:'freezeAmt',align:'right',width:80,formatter:formatMoney"  halign="center" >冻结额度</th>
		    <th data-options="field:'availableAmt',align:'right',width:80,formatter:formatMoney"  halign="center" >可用额度</th>
		    <th data-options="field:'pmUser',width:80"  halign="center" align="center">项目经理</th>
		    <th data-options="field:'credtiStartDt',width:80"  halign="center" align="center">授信开始日期</th>
		    <th data-options="field:'credtiEndDt',width:80"  halign="center" align="center">授信到期日期  </th>
		</tr></thead>
	</table>
	</div>
	</div>
</body>
</html>
