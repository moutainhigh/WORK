<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 机构合作列表的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
	
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}orgCooperatCompanyApplyController/editCooperate.action?editType=3&pid="
					+ row[0].pid+"&orgId="+row[0].orgId+"&param='refId':'"+row[0].pid+"','projectName':'1'"+",'projectId':'"+row[0].pid+"'","查看机构合作申请信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//账户状态
	function formatterStatus(val,row){
		if(val == 1){
			return "未提交";
		}else if(val == 2){
			return "已提交";
		}else if(val == 3){
			return "审核中";
		}else if(val == 4){
			return "审核通过";
		}else if(val == 5){
			return "审核不通过";
		}
	}
	
	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].applyStatus == 1 || row[0].applyStatus == 2){
				
				url = '<%=basePath%>orgCooperatCompanyApplyController/editCooperate.action?editType=2&pid='
					+ row[0].pid+"&orgId="+row[0].orgId;
				//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
				var datas = getTaskVoByWPDefKeyAndRefId("cooperationRequestProcess",row[0].pid);
				if(datas){
					url+="&"+datas;
				}
				parent.openNewTab(url,"修改机构合作申请",true);
			}else{
				$.messager.alert("操作提示", "此申请正在审核中或者已审核结束,请重新选择！", "error");
			}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	//修改合作信息
	function editCompanyApply(openType){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			var cooperationUpdateStatus=row[0].cooperationUpdateStatus;
			var orgId=row[0].orgId;
			var pid=row[0].pid;
			var applyStatus=row[0].applyStatus;
			if (applyStatus!=4) {
				$.messager.alert("操作提示", "合作申请状态未通过，不能申请修改数据", "error");
				return;
			}
			if (cooperationUpdateStatus==3||cooperationUpdateStatus==4) {
				url = "${basePath}orgCooperationUpdateApplyController/toOrgCooperationUpdateApply.action?openType="+openType+"&pid="
					+ pid+"&orgId="+orgId+"&param='projectId':'"+pid+"','refId':'"+pid+"','projectName':'1'";
			}else{
				url = "${basePath}orgCooperationUpdateApplyController/toOrgCooperationUpdateApply.action?openType="+openType+"&pid="
					+ pid+"&orgId="+orgId;
			}
				
				parent.openNewTab(url,"修改机构合作申请",true);

		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	// 新增保证金变更
	function addFundFlow() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			if(row[0].applyStatus == 4){
				url = '<%=basePath%>orgAssureFundFlowController/editFundFlowInfo.action?editType=2&cooperationId='
					+ row[0].pid;
				parent.openNewTab(url,"新增保证金变更",true);
			}else{
				$.messager.alert("操作提示", "只有审核通过的合作申请才能申请保证金变更,请重新选择！", "error");
			}
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
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
	$(document).ready(function(){
		$('#grid').datagrid({  
			 onDblClickRow: function (rowIndex, rowData){
				 parent.openNewTab("${basePath}orgCooperatCompanyApplyController/editCooperate.action?editType=3&pid="
							+ rowData.pid+"&orgId="+rowData.orgId+"&param='refId':'"+rowData.pid+"','projectName':'1'"+",'projectId':'"+rowData.pid+"'","查看机构合作申请信息",true);
			 }
		 });
	});
</script>
<title>机构合作管理</title>
</head>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getAllOrgCooperate.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right"  height="25">机构名称：</td>
				<td colspan="2"><input name="orgName" class="easyui-textbox" style="width: 200px;" /></td>
				<td width="80" align="right">统一社会信用代码：</td>
				<td colspan="2"><input name="orgCode" class="easyui-textbox" style="width: 200px;" /></td>
			</tr>
			<tr>
				<td width="80" align="right" >申请状态：</td>
				<td>
					<select class="select_style easyui-combobox" width="180px;" editable="false" name="applyStatus">
						<option value="-1">--请选择--</option>
						<option value="1" <c:if test="${orgCooperateInfo.applyStatus==1 }">selected </c:if>>未提交    </option>
						<option value="2" <c:if test="${orgCooperateInfo.applyStatus==2 }">selected </c:if>>已提交    </option>
						<option value="3" <c:if test="${orgCooperateInfo.applyStatus==3 }">selected </c:if>>审核中    </option>
						<option value="4" <c:if test="${orgCooperateInfo.applyStatus==4 }">selected </c:if>>审核通过    </option>
						<option value="5" <c:if test="${orgCooperateInfo.applyStatus==5 }">selected </c:if>>审核不通过    </option>
					</select>
				</td>
				<td colspan="3">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" style="margin-left: 120px;" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="UPDTE_ORG_COOPERATE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="ADD_ORG_FUNDFLOW,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-setting" plain="true" onclick="addFundFlow()">保证金变更</a>
		</shiro:hasAnyRoles>
		
		<shiro:hasAnyRoles name="EDIT_ORG_COOPERATE,ALL_BUSINESS">
		<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editCompanyApply(1)">修改合作信息</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="SHOW_ORG_COOPERATE,ALL_BUSINESS">
		<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editCompanyApply(2)">查看修改合作信息</a>
		</shiro:hasAnyRoles>
	</div>
</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="机构合作申请" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getAllOrgCooperate.action',
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
			<th data-options="field:'orgName',width:80,sortable:true">机构名称</th>
			<th data-options="field:'orgCode',width:80,sortable:true">统一社会信用代码</th>
			<th data-options="field:'partnerName',width:80,sortable:true">所属合伙人</th>
			<th data-options="field:'contact',width:80,sortable:true">联系人</th>
			<th data-options="field:'phone',width:80,sortable:true">联系电话</th>
			<th data-options="field:'applyDate',width:120,sortable:true">申请时间</th>
			<th data-options="field:'applyStatus',width:80,formatter:formatterStatus">申请状态</th>
			<th data-options="field:'creditLimit',formatter:formatAomMoney,width:80">授信额度</th>
			<th data-options="field:'singleUpperLimit',formatter:formatAomMoney,width:80">单笔上限</th>
			<th data-options="field:'rate',formatter:formatAomMoney,width:80">费率(%)</th>
			<th data-options="field:'assureMoney',formatter:formatAomMoney,width:80">保证金</th>
			<th data-options="field:'realAssureMoney',formatter:formatAomMoney,width:80">实收保证金</th>
			<th data-options="field:'startTime',width:80">合作开始时间</th>
			<th data-options="field:'endTime',width:80">合作结束时间</th>
			<th data-options="field:'cooperationUpdateStatus',formatter:formatterCommonApplyHandleStatus">合作修改审批状态</th>
		</tr>
	</thead>
</table>
</div>

</div>


</body>
</html>