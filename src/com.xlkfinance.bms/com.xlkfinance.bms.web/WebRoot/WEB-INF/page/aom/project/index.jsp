<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 业务申请的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">

	// 编辑
	function editItem() {
		var loginId = "${shiroUser.pid}";
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(loginId != row[0].pmUserId){
	 			$.messager.alert("操作提示","业务申请只有分配用户才能进行编辑！","error");
	 			return;
	 		}
			if(row[0].foreclosureStatus == 1 && row[0].isReject == 2){
				url = '<%=basePath%>bizProjectController/toEditProject.action?editType=2&projectId='
					+ row[0].pid;
				//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
				var datas = getTaskVoByWPDefKeyAndRefId("businessApplyRequestProcess",row[0].pid);
				if(datas){
					url+="&"+datas;
				}
				parent.openNewTab(url,"修改业务申请信息",true);
			}else{
	 			$.messager.alert("操作提示","业务申请审核中或者已驳回，请重新选择！","error");
	 		}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}bizProjectController/toEditProject.action?editType=3&projectId="
					+ row[0].pid+"&param='refId':'"+row[0].pid+"','projectName':'"+row[0].projectName+"','projectId':'"+row[0].pid+"'","查看业务申请信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//审批状态
	function formatterApplyStatus(val,row){
		if(val == 1){
			return "待客户经理提交";
		}else if(val == 2){
			return "待部门经理审批";
		}else if(val == 3){
			return "待业务总监审批";
		}else if(val == 4){
			return "待审查员审批";
		}else if(val == 5){
			return "待风控初审";
		}else if(val == 6){
			return "待风控复审";
		}else if(val == 7){
			return "待风控终审";
		}else if(val == 8){
			return "待风控总监审批";
		}else if(val == 9){
			return "待总经理审批";
		}else if(val == 10){
			return "已审批";
		}else if(val == 11){
			return "已放款";
		}else if(val == 12){
			return "业务办理已完成";
		}else if(val == 13){
			return "已归档";
		}else if(val == 15){
			return "待合规复审";
		}
	}
	//formatAomMoney
	function formatAomMoney(value,row,index){
		if(value){
			return accounting.formatMoney(value, "", 2, ",", ".");
		}else{
			return "-";
		}
	}
	
	//打开驳回页面
	function openRejectProject(){
		var loginId = "${shiroUser.pid}";
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		if(loginId != row[0].pmUserId){
	 			$.messager.alert("操作提示","业务申请只有分配用户才能进行驳回！","error");
	 			return;
	 		}
	 		if(row[0].isReject == 2 && row[0].foreclosureStatus == 1){
	 			$("#pid").val(row[0].pid);
		 		$('#audit_dialog').dialog('open').dialog('setTitle', "驳回业务申请");
	 		}else{
	 			$.messager.alert("操作提示","此业务申请已驳回或者审核中,请重新选择！","error");
	 		}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//驳回业务申请
	function rejectProject(){
		// 提交表单
		$("#auditForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(result) {
				var ret = eval("(" + result + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert('操作提示', ret.header["msg"], 'info');
	                //关闭窗口
					$('#audit_dialog').dialog('close');
					// 刷新
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
		
	}
	function formatterReject(value,row,index) {
		if (value == 1) {
			return "已驳回";
		} else if (value == 2) {
			return "正常";
		}
	}
	
	$(function() {
		$("#realName").textbox({
		    "onChange":function(){
		    	var realName=$("#realName").textbox("getValue");
				if (realName==null) {
					realName="";
				}
				// 重新加载数据
				$("#grid2").datagrid('load', {"realName":realName});
				// 清空所选择的行数据
				clearSelectRows("#grid2");
		    }
		  });
		
		$('#grid').datagrid({  
			 onDblClickRow: function (rowIndex, rowData){
				 parent.openNewTab("${basePath}bizProjectController/toEditProject.action?editType=3&projectId="
							+ rowData.pid+"&param='refId':'"+rowData.pid+"','projectName':'"+rowData.projectName+"','projectId':'"+rowData.pid+"'","查看业务申请信息",true);
			 }
		 });
	});
	//分配业务
	function assignedProject() {
		var gridRow = $('#grid').datagrid('getSelections');
		var grid2Row = $('#grid2').datagrid('getSelections');
		if (grid2Row.length != 1) {
			$.messager.alert('操作提示', "请选择用户", 'error');
			return;
		}
		
		var pids = "";
		for (var i = 0; i < gridRow.length; i++) {
				if (i == 0) {
					pids += gridRow[i].pid;
				} else {
					pids += "," + gridRow[i].pid;
				}
			}
		
		var userId=grid2Row[0].pid;
		var managers = grid2Row[0].realName;
		var managersPhone = grid2Row[0].phone;
		$.ajax({
			url : "${basePath}bizProjectController/assignedProject.action",
			cache : true,
			type : "POST",
			data : {'pmUserId':userId,'pids':pids,'managers':managers,'managersPhone':managersPhone},
			async : false,
			success : function(data, status) {
				var ret = eval("(" + data + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert("操作提示", ret.header["msg"]);
					$("#grid").datagrid("clearChecked");
					$("#grid").datagrid('reload');
					$('#allocation_dialog').dialog('close');
				}else{
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}
	//打开业务分配页面
	function openAssignedProject() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 0) {
			$.messager.alert('操作提示', "请选择业务", 'error');
			return;
		}
		// 重新加载数据
		$("#grid2").datagrid('load', {});
		// 清空所选择的行数据
		clearSelectRows("#grid2");
		$('#allocation_dialog').dialog('open').dialog('setTitle',"业务分配");
	}
	// 项目名称format
	function formatProjectName(value, row, index){
		var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.pid+")'> <font color='blue'>"+row.projectName+"</font></a>"
		return va;
	}
	
	function editProjectInfo(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		// 判断选中的状态是否是放款前,只有放款前的状态才可以编辑
	 		if(row[0].foreclosureStatus <11 || row[0].foreclosureStatus == 14){
	 	 		// 如果是编辑 ,需要让 bianzhi = 1
	 	 		var title = "编辑赎楼贷款申请";
	 			url = "${basePath}bizProjectController/toEditProject.action?editType=2&isEdit=isEdit&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].projectName+"','WORKFLOW_ID':'businessApplyRequestProcess'";

	 			parent.openNewTab(url,title,true);
	 		}else{
	 			$.messager.alert("操作提示","只有放款前才能进行修改！","error");
	 		}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
</script>
<title>业务申请管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getAssignedProjectList.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">订单编号：</td>
				<td>
					<input name="projectNumber" class="easyui-textbox" style="width: 200px;" />
				</td>
				<td width="80" align="right" height="25">订单名称：</td>
				<td>
					<input name="projectName" class="easyui-textbox" style="width: 200px;" />
				</td>
				<td width="80" align="right" height="25">分配人：</td>
				<td>
					<input name="pmUserName" class="easyui-textbox" style="width: 200px;" />
				</td>
			</tr>
			<tr>
				<td width="80" align="right" height="25">客户名称：</td>
				<td><input name="orgCustomerName" class="easyui-textbox" style="width: 200px;" /></td>
				<td width="80" align="right" >订单状态：</td>
				<td>
					<select class="select_style easyui-combobox" width="180px;" editable="false" name="foreclosureStatus">
						<option value=-1 selected="selected">--请选择--</option>
			            <option value=1>待客户经理提交</option>
			            <option value=5>待风控初审</option>
			            <option value=6>待风控复审</option>
			            <option value=7>待风控终审</option>
			            <option value=9>待总经理审批</option>
			            <option value=10>已审批</option>
			            <option value=11>已放款</option>
			            <option value=12>业务办理已完成</option>
			            <option value=13>已归档</option>
			            <option value=15>待合规复审</option>
					</select>
				</td>
						
				<td colspan="2">
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 120px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
				<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
		<shiro:hasAnyRoles name="UPDATE_BIZ_PROJECT,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		</shiro:hasAnyRoles>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="REJECT_BIZ_PROJECT,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-setting" plain="true" onclick="openRejectProject()">驳回</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="ASSIGNED_BIZ_PROJECT,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="openAssignedProject()">分配</a>
		</shiro:hasAnyRoles>
		
		<shiro:hasAnyRoles name="EDIT_BIZ_PROJECT,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editProjectInfo()">修改业务信息</a>
		</shiro:hasAnyRoles>
	</div>
</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="业务申请管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getAssignedProjectList.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    width: '100%',
	 	height: '100%',  
	 	 striped: true, 
	 	 loadMsg: '数据加载中请稍后……',  
	    fitColumns:true" >
	    
	<!--
	 表头 -->
	<thead>
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'projectName',formatter:formatProjectName,width:50,sortable:true" >订单名称</th>
			<th data-options="field:'projectNumber',width:50,sortable:true" >订单编号</th>
			<th data-options="field:'applyUserName',width:50,sortable:true">业务来源</th>
			<th data-options="field:'pmUserName',sortable:true" align="center" halign="center">分配用户</th>
			<th data-options="field:'orgCustomerName',width:30,sortable:true">客户名称</th>
			<th data-options="field:'orgCustomerPhone',width:50,sortable:true">客户电话</th>
			<th data-options="field:'orgCustomerCard',width:50,sortable:true">身份证号</th>
			<th data-options="field:'requestDttm',width:50,sortable:true">提单日期</th>
			<th data-options="field:'planLoanMoney',formatter:formatAomMoney,width:50">借款金额(元)</th>
			<th data-options="field:'foreclosureStatus',width:80,formatter:formatterApplyStatus">审批状态</th>
			<th data-options="field:'isReject',width:80,formatter:formatterReject">驳回状态</th>
		</tr>
	</thead>
</table>
</div>
<!-- 驳回操作开始 -->
 <div id="audit_dialog" class="easyui-dialog" buttons="#audit_dialogDiv" style="width: 450px; height: 250px; padding: 10px;" closed="true">
   <form id="auditForm" name="auditForm" action="${basePath}bizProjectController/rejectProject.action" method="post">
     <table style="width: 100%; height: 50px;">
     	<input type="hidden" name="pid" id="pid">
     	<input type="hidden" name="isReject" value="1">
		<tr>
        <td class="label_right">驳回意见:</td>
        <td><textarea rows="2" id="examineOpinion" name="examineOpinion" required="required" maxlength="200" style="height: 120px;width: 250px;"></textarea></td>
       </tr>
     </table>
    </form>
   </div>
   <div id="audit_dialogDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="rejectProject()">提交</a> 
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#audit_dialog').dialog('close')">取消</a>
   </div>
<!-- 驳回操作结束 -->
<!-- 分配页面开始 -->
<div id="allocation_dialog" class="easyui-dialog" buttons="#subAllocationDiv" style="width: 400px; height: 410px; padding: 10px;"
    closed="true"
   >
           用户姓名:<input name="realName" id="realName" data-options="prompt:'查询条件'" class="easyui-textbox"/>
    <table id="grid2" title="请选择人员" class="easyui-datagrid" style="height: 90%; width: 100%;"
     data-options="
      url: '<%=basePath%>bizProjectController/orderAllocationUserList.action',
      method: 'POST',
      rownumbers: true,
      singleSelect: true,
      pagination: true,
      sortOrder:'asc',
      remoteSort:false,
      idField: 'pid',
      fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'pid',checkbox:true"></th>
       <th data-options="field:'userName',sortable:true" align="center" halign="center">用户名</th>
       <th data-options="field:'realName',sortable:true" align="center" halign="center">姓名</th>
       <th data-options="field:'memberId',sortable:true" align="center" halign="center">工号</th>
       <th data-options="field:'phone',sortable:true" align="center" halign="center">手机</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="subAllocationDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="assignedProject()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#allocation_dialog').dialog('close')"
    >取消</a>
   </div>
   <!-- 分配页面结束 -->
</div>
</div>
</body>
</html>