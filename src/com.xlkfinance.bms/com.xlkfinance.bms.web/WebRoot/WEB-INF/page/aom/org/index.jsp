<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 小科的页面 -->
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript">
	
	//新增
	function addItem() {
		parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=1","新增机构");
	}

	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=2&orgId="
					+ row[0].pid,"修改机构信息",true);
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
			parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=3&orgId="
					+ row[0].pid,"查看机构信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//账户状态
	function formatterStatus(val,row){
		if(val == 1){
			return "启用";
		}else if(val == 2){
			return "禁用";
		}
	}
	//认证状态
	function formatterAuditStatus(val,row){
		if(val == 1){
			return "未认证";
		}else if(val == 2){
			return "认证中";
		}else if(val == 3){
			return "已认证";
		}
	}
	//合作状态
	function formatterCooperateStatus(val,row){
		if(val == 1){
			return "未合作";
		}else if(val == 2){
			return "已合作";
		}else if(val == 3){
			return "合作已过期";
		}else if(val == 4){
			return "合作待确认";
		}
	}
	
	function updateAuditStatus(){
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
	//打开认证页面
	function openAuditStatus(){
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		if(row[0].auditStatus != 3){
	 			$("#pid").val(row[0].pid);
	 			$("#auditStatus").combobox('setValue',row[0].auditStatus);
		 		$('#audit_dialog').dialog('open').dialog('setTitle', row[0].orgName+"认证");
	 		}else{
	 			$.messager.alert("操作提示","此机构已认证,请重新选择！","error");
	 		}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//合作申请
	function addOrgCooperateInfo(){
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		if(row[0].auditStatus == 3){
		 		$.ajax({
		 			url : '${basePath}orgCooperatCompanyApplyController/checkOrgCooperate.action',
		 			cache : true,
					type : "POST",
					data : {'orgId':row[0].pid},
					async : false,
					success : function(data, status) {
		 				var flag = data;
		 				if(flag == '1'){
		 					$.messager.alert("操作提示","此机构已存在合作申请,请重新选择！","error");
		 				}else{
		 					parent.openNewTab("${basePath}orgCooperatCompanyApplyController/editCooperate.action?editType=1&orgId="+row[0].pid,"新增机构合作申请",true);
		 				}
		 			}
		 		});
	 		}else{
	 			$.messager.alert("操作提示","此机构未认证,请重新选择！","error");
	 		}
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能选中一条数据进行操作,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	$(document).ready(function(){
		$('#grid').datagrid({  
			 onDblClickRow: function (rowIndex, rowData){
				 parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=3&orgId="
							+ row[0].pid,"查看机构信息",true);
			 }
		 });
	});
</script>
<title>机构管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getAllOrgAssets.action" method="POST" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">登录名：</td>
				<td>
					<input name="loginName" class="easyui-textbox" style="width: 200px;" />
				</td>
				<td width="80" align="right">机构名称：</td>
				<td colspan="2"><input name="orgName" class="easyui-textbox" style="width: 200px;" /></td>
			</tr>
			<tr>
				<td width="80" align="right" >账号状态：</td>
				<td>
					<select class="select_style easyui-combobox" width="180px;" editable="false" name="status">
						<option value="-1">--请选择--</option>
						<option value="1" <c:if test="${orgAssets.status==1 }">selected </c:if>>有效    </option>
						<option value="2" <c:if test="${orgAssets.status==2 }">selected </c:if>>无效    </option>
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
		<shiro:hasAnyRoles name="ADD_ORG_ASSETS,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addItem()">新增</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="UPDATE_ORG_ASSETS,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
		</shiro:hasAnyRoles>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="AUDIT_ORG_ASSETS,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-setting" plain="true" onclick="openAuditStatus()">认证</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="ADD_ORG_COOPERATE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addOrgCooperateInfo()">合作申请</a>
		</shiro:hasAnyRoles>
	</div>

</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="机构管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getAllOrgAssets.action',
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
			<th data-options="field:'loginName',width:80,sortable:true" >登录名</th>
			<th data-options="field:'orgName',width:80,sortable:true">机构名称</th>
			<th data-options="field:'partnerName',width:80,sortable:true">所属合伙人</th>
			<th data-options="field:'email',width:80,sortable:true">邮箱</th>
			<th data-options="field:'contact',width:80,sortable:true">联系人</th>
			<th data-options="field:'phone',width:80,sortable:true">联系电话</th>
			<th data-options="field:'createdDate',width:80,sortable:true">创建时间</th>
			<th data-options="field:'status',width:50,formatter:formatterStatus">账户状态</th>
			<th data-options="field:'auditStatus',width:50,formatter:formatterAuditStatus">认证状态</th>
			<th data-options="field:'cooperateStatus',width:50,formatter:formatterCooperateStatus">合作状态</th>
		</tr>
	</thead>
</table>
</div>

    <div id="audit_dialog" class="easyui-dialog" buttons="#audit_dialogDiv" style="width: 450px; height: 250px; padding: 10px;" closed="true">
   <form id="auditForm" name="auditForm" action="${basePath}orgAssetsCooperationController/editAuditStatus.action" method="post">
     <table style="width: 100%; height: 50px;">
     	<input type="hidden" name="pid" id="pid"">
       <tr>
       <td class="label_right"><font color="red">*</font>认证状态：</td>
		<td>
			<select class="select_style easyui-combobox" id="auditStatus" editable="false" data-options="validType:'selrequired'" required="true" name="auditStatus" style="width:129px;">
				<option value="-1">--请选择--</option>
				<option value="1">未认证</option>
				<option value="2">认证中</option>
				<option value="3">已认证</option>
			</select>
		</td>
		</tr>
		<tr>
        <td class="label_right">认证说明:</td>
        <td><textarea rows="2" id="auditDesc" name="auditDesc" maxlength="500" style="height: 120px;width: 250px;"></textarea></td>
       </tr>
     </table>
    </form>
   </div>
   <div id="audit_dialogDiv">
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="updateAuditStatus()">提交</a> 
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#audit_dialog').dialog('close')">取消</a>
   </div>
</div>
</div>
</body>
</html>