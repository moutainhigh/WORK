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
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/task_common.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$(".ecoTradeId1").hide();
	
	$('#acctType').combobox({
		onSelect: function(row){
			var opts = $(this).combobox('options');
			if(row[opts.textField]=='企业')
			{
				$(".ecoTradeId1").show();
			}else{
				$(".ecoTradeId1").hide();
			}
 
		}
	});
	
	//  初始化加载datagrid
	ajaxSearchPage('#grid','#searchFrom');
})

//重置按钮
function majaxReset(){
	$(".ecoTradeId1").hide();
	$('#searchFrom').form('reset')
}

// 新增
function addItem(){
	$('#fm').form('reset');
	// 将 url 地址改变
	parent.openNewTab("${basePath}beforeLoanController/addNavigation.action","贷款申请办理");
}

// 编辑
function editItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		// 判断选中的状态是否是申请中,只有申请中的状态才可以编辑
 		if(row[0].requestStatus == 1){
 	 		// 如果是编辑 ,需要让 bianzhi = 1
 			url = "<%=basePath%>beforeLoanController/addNavigation.action?projectId="+row[0].pid;
 			//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
			var datas = getTaskVoByWPDefKeyAndRefId("loanRequestProcess",row[0].pid);
			if(datas){
				url+="&"+datas;
			}
 			parent.openNewTab(url,"编辑贷款申请",true);
 		}else{
 			$.messager.alert("操作提示","贷款申请只有申请中才能进行编辑！","error");
 		}
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

function lookupItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		url = "${basePath}beforeLoanController/addNavigation.action?projectId="+row[0].pid+"&param='refId':'"+row[0].pid+"','projectName':'"+row[0].projectName+"'";
		parent.openNewTab(url,"查看贷款申请",true);
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
		if(rows[i].requestStatus == 1){
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}else{
			$.messager.alert("操作提示","只有申请中的项目才能删除","error");
			return false;
		}
	}
 	$.messager.confirm("操作提示","确认删除选择的数据?",
		function(r) {
 			if(r){
				$.post("<%=basePath%>beforeLoanController/deleteProject.action",{pids : pids}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						if(ret && ret.header["code"] == 200){
							$.messager.alert('操作提示',"删除成功!");	
							// 重新加载抵质押物列表信息
		 					$("#grid").datagrid('load');
		 					// 清空所选择的行数据
		 					clearSelectRows("#grid");
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json');
 			}
		});
}

//设置授信有效
function setEffective(){
	// 获取选中的数据行
	var rows = $('#grid').datagrid('getSelections');
	// 判断是否选中数据
 	if ( rows.length == 0 ) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}else if(rows.length > 1){
		$.messager.alert("操作提示","每次只能设置一条数据为有效,请重新选择！","error");
		return;
	}else if(rows[0].projectType == 2){
		$.messager.alert("操作提示","不能对贷款项目进行项目授信的操作,请重新选择！","error");
		return;
	}else if(rows[0].creditStatus != 2){
		$.messager.alert("操作提示","只能把无效的项目置为有效,请重新选择！","error");
		return;
	}
 	$.messager.confirm("操作提示","确认把当前选中的授信项目置为有效?",
			function(r) {
	 			if(r){
					$.post("<%=basePath%>secondBeforeLoanController/setProjectEffective.action",{projectId : rows[0].pid}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["success"] ){
								$.messager.alert('操作提示',ret.header["msg"]);
								// 重新加载抵质押物列表信息
			 					$("#grid").datagrid('load');
			 					// 清空所选择的行数据
			 					clearSelectRows("#grid");
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
						},'json');
	 			}
			});
}

//设置授信无效
function setInvalid(){
	// 获取选中的数据行
	var rows = $('#grid').datagrid('getSelections');
	// 判断是否选中数据
 	if ( rows.length == 0 ) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
	// 获取选中的数据行的PID
 	var pids = "";
	// 循环拼接(1,2,3)
	for (var i = 0; i < rows.length; i++) {
		// 必须是有效的项目才能置为无效
		if(rows[i].creditStatus == 1){
			if (i == 0) {
				pids = rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}else{
			$.messager.alert("操作提示","只能把有效的项目置为无效","error");
			return false;
		}
		// 只能对非贷款项目进行项目授信操作
		if(rows[i].projectType == 2){
			$.messager.alert("操作提示","不能对贷款项目进行项目授信的操作,请重新选择！","error");
			return false;
		}
	}
	$.messager.confirm("操作提示","确认把当前选中的授信项目置为无效?",
			function(r) {
	 			if(r){
					$.post("<%=basePath%>secondBeforeLoanController/setProjectInvalid.action",{pids : pids}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["success"] ){
								$.messager.alert('操作提示',ret.header["msg"]);	
								// 重新加载抵质押物列表信息
			 					$("#grid").datagrid('load');
			 					// 清空所选择的行数据
			 					clearSelectRows("#grid");
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
						},'json');
	 			}
			});
}

// 项目类型格式化
function formatterProjectType(val,row){
	if(val == 1){
		return "授信";
	} else if(val == 2){
		return "贷款";
	} else if(val == 5){
		return "授信、提款";
	} else {
		return "未知";
	}
}

// 授信状态格式化
function formatterCreditStatus(val,row){
	if(val == 0){
		return "贷款项目";
	} else if(val == 1){
		return "有效";
	} else if(val == 2){
		return "无效";
	}else if(val == 3){
		return "未知";
	}
}


</script>
<title>贷前管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>beforeLoanController/getAllProject.action" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="80" align="right" height="28">项目编号：</td>
						<td>
							<input class="easyui-textbox" name="projectNumber" id="projectNumber"/>
						</td>
						<td width="100" align="right"  height="28">项目名称：</td>
						<td colsapn="2">
							<input class="easyui-textbox" style="width:220px" name="projectName" id="projectName"/>						
						</td>
					</tr>
					<tr>
						<td width="80" align="right" >客户类别：</td>
						<td>
							<input name="acctType" id="acctType" class="easyui-combobox" editable="false" panelHeight="auto"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" />
						</td>
						<td colspan="3">
							<div class="ecoTradeId1 none">
								<table>
									<tr>
										<td width="100" align="right">经济行业类别：</td>
										<td>
											<input name="ecoTrade" id="ecoTrade" style="width:220px" class="easyui-combobox" editable="false" panelHeight="auto"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
										</td>
									</tr>
								</table>
							
							</div>
						</td>
					</tr>
					<tr>
						<td width="80" align="right" >客户名称：</td>
						<td>
							<input class="easyui-textbox" name="acctName" id="acctName"/>
						</td>
						<td width="100" align="right" >申请时间：</td>
						<td colsapn="2">
							<input name="beginRequestDttm" id="beginRequestDttm" class="easyui-datebox" editable="false"/> <span>~</span> 
							<input name="endRequestDttm" id="endRequestDttm" class="easyui-datebox" editable="false"/>
						</td>
					</tr>
					<tr>
						<td width="80" align="right"  height="28">项目状态：</td>
						<td >
							<select class="easyui-combobox" id="requestStatus"  name="requestStatus" style="width:150px" panelHeight="auto" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
							    <option value=1 >申请中</option>
								<option value=2 >审核中</option>
								<option value=3 >已放款</option>
								<option value=4 >已归档</option>
								<option value=7 >已否决</option>
							</select>
						</td>
						<td width="100" align="right"  height="28">授信状态：</td>
						<td >
							<select class="easyui-combobox" id="creditStatus"  name="creditStatus" style="width:220px" panelHeight="auto" editable="false" >
								<option value=-1  selected="selected" >--请选择--</option>
								<option value=0 >贷款项目</option>
							    <option value=1 >有效</option>
								<option value=2 >无效</option>
							    <option value=3 >未知</option>
							</select>
						</td>
						<td>
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>				
				</table>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting" plain="true" onclick="setEffective()">设置授信有效</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-setting" plain="true" onclick="setInvalid()">设置授信无效</a>
		</div>
		
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="贷款申请列表" class="easyui-datagrid" 
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>beforeLoanController/getAllProject.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: false,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true">
		<!-- 表头 -->
		<thead><tr>
		    <th data-options="field:'pid',checkbox:true" ></th>
		    <th data-options="field:'requestStatus',hidden:true"></th>
		    <th data-options="field:'projectName',width:80" align="center" halign="center"  >项目名称</th>
		    <th data-options="field:'projectNumber',width:80" align="center" halign="center"  >项目编号</th>
		    <th data-options="field:'projectType',formatter:formatterProjectType,width:80" align="center" halign="center"  >项目类型</th>
		    <th data-options="field:'creditStatus',formatter:formatterCreditStatus,width:80" align="center" halign="center"  >授信状态</th>
		    <th data-options="field:'requestStatusVal',width:80" align="center" halign="center"  >项目状态</th>
		    <th data-options="field:'requestDttm',width:80" align="center" halign="center"  >申请时间</th>
		    <th data-options="field:'endCompleteDttm',width:80" align="center" halign="center"  >归档时间</th>
		    <th data-options="field:'acctTypeText',width:80" align="center" halign="center"  >客户类别</th>
		    <th data-options="field:'realName',width:80" align="center" halign="center"  >项目经理</th>
		    <th data-options="field:'credtiStartDt',formatter:convertDate,width:80" align="center" halign="center"  >授信开始日期</th>
		    <th data-options="field:'credtiEndDt',formatter:convertDate,width:80" align="center" halign="center"  >授信到期日期  </th>
		</tr></thead>
	</table>
	</div>
	</div>
	</div>
</body>
</html>
