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
<style type="text/css">
</style>
<script type="text/javascript">
	//重置按钮
	function majaxReset() {
		$(".ecoTradeId1").hide();
		$('#searchFrom').form('reset')
	}
	//打开机构分配页面
	function openAllocation() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length != 1) {
			$.messager.alert('操作提示', "请选择机构", 'error');
			return;
		}
		if(row[0].auditStatus != 3){
			$.messager.alert('操作提示', "此机构未认证，不可分配！", 'error');
			return;
		}
		// 重新加载数据
		$("#grid2").datagrid('load', {});
		// 清空所选择的行数据
		clearSelectRows("#grid2");
		$('#allocation_dialog').dialog('open').dialog('setTitle',"机构分配");
	}
	//分配机构
	function subAllocationForm() {
		var gridRow = $('#grid').datagrid('getSelections');
		var grid2Row = $('#grid2').datagrid('getSelections');
		if (grid2Row.length != 1) {
			$.messager.alert('操作提示', "请选择用户", 'error');
			return;
		}
		if(gridRow[0].auditStatus != 3){
			$.messager.alert('操作提示', "此机构未认证，不可分配！", 'error');
			return;
		}
		
		var orgId=gridRow[0].orgId;
		var userId=grid2Row[0].pid;
		$.ajax({
			url : "${basePath}orgAssetsCooperationController/orgAllocation.action",
			cache : true,
			type : "POST",
			data : {'bizAdviserId':userId,'serviceObjId':orgId},
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
				 parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=3&orgId="
							+ rowData.orgId,"查看机构信息",true);
			 }
		 });
		
	});
	
	//账户状态
	function formatterStatus(val,row){
		if(val == 1){
			return "启用";
		}else if(val == 2){
			return "禁用";
		}
	}
	
	function lookupItem(){
		var url = "";// 路径
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
			parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=3&orgId="
					+ row[0].orgId,"查看机构信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//修改认证状态
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
		$("#auditForm").form('reset');
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		if(row[0].auditStatus != 3){
	 			$("#pid").val(row[0].orgId);
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
</script>
<title>机构未分配</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>orgAssetsCooperationController/orgAllocationList.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td align="right" height="28">机构名称：</td>
        <td><input class="easyui-textbox" name="orgName" data-options="validType:'length[0,20]'"/></td>
        <td align="right" height="28">统一社会信用代码：</td>
        <td><input class="easyui-textbox" name=orgCode data-options="validType:'length[0,20]'"/></td>
       </tr>
       <tr>
       	<td width="80" align="right" height="28">登录名：</td>
		<td>
			<input name="loginName" class="easyui-textbox" style="width: 200px;" />
		</td>
       	<td align="right" height="28">所属合伙人：</td>
        <td><input class="easyui-textbox" name="partnerName" data-options="validType:'length[0,20]'"/></td>
       </tr>
       <tr>
        <td colspan="6"><a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
         onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;"
        >重置</a></td>
       </tr>
      </table>
     </div>
    </form>

    <!-- 操作按钮 -->
    <div style="padding-bottom: 5px">
    	<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="AUDIT_ORG_ASSETS,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-setting" plain="true" onclick="openAuditStatus()">认证</a>
		</shiro:hasAnyRoles>
    	<shiro:hasAnyRoles name="ENALLOCATION_ORG_ASSETS,ALL_BUSINESS">
     		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAllocation()">分配机构</a>
    	</shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="机构未分配列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>orgAssetsCooperationController/orgAllocationList.action',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true"
    >
     <!-- 表头 -->
     <thead>
      <tr>
       <th data-options="field:'orgId',checkbox:true"></th>
       <th data-options="field:'loginName',sortable:true" align="center" halign="center">登录名</th>
       <th data-options="field:'orgName',sortable:true" align="center" halign="center">机构名称</th>
       <th data-options="field:'orgCode',sortable:true" align="center" halign="center">统一社会信用代码</th>
       <th data-options="field:'email',sortable:true" align="center" halign="center">邮箱</th>
       <th data-options="field:'contact',sortable:true" align="center" halign="center">联系人</th>
       <th data-options="field:'phone',sortable:true" align="center" halign="center">联系人电话</th>
       <th data-options="field:'partnerName',sortable:true" align="center" halign="center">所属合伙人</th>
       <th data-options="field:'status',formatter:formatterStatus" align="center" halign="center">账户状态</th>
       <th data-options="field:'auditStatus',sortable:true,formatter:formatterOrgAuditStatus" align="center" halign="center">认证状态</th>
       <th data-options="field:'cooperateStatus',sortable:true,formatter:formatterOrgCooperateStatus" align="center" halign="center">合作状态</th>
       <th data-options="field:'registerDate',sortable:true" align="center" halign="center">注册时间</th>
      </tr>
     </thead>
    </table>
   </div>
   <div id="allocation_dialog" class="easyui-dialog" buttons="#subAllocationDiv" style="width: 400px; height: 410px; padding: 10px;"
    closed="true"
   >
           用户姓名:<input name="realName" id="realName" data-options="prompt:'查询条件'" class="easyui-textbox"/>
    <table id="grid2" title="请选择机构" class="easyui-datagrid" style="height: 100%; width: 350px;"
     data-options="
      url: '<%=basePath%>sysUserController/orgAllocationUserList.action',
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
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="subAllocationForm()">提交</a> <a href="javascript:void(0)"
     class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#allocation_dialog').dialog('close')"
    >取消</a>
   </div>
<!-- 认证页面开始 -->
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
