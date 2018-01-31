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
	
	//新增
	function addItem() {
		parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=1","新增机构");
	}
	
	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=2&orgId="
					+ row[0].orgId,"修改机构信息",true);
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
					+ row[0].orgId,"查看机构信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	//打开分配页面
	function openAllocation() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length != 1) {
			$.messager.alert('操作提示', "请选择机构", 'error');
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
	//账户状态
	function formatterStatus(val,row){
		if(val == 1){
			return "启用";
		}else if(val == 2){
			return "禁用";
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
				 parent.openNewTab("${basePath}orgAssetsCooperationController/editOrgAssets.action?editType=3&orgId="
							+ rowData.orgId,"查看机构信息",true);
			 }
		 });
	});
	
	//合作申请
	function addOrgCooperateInfo(){
		var row = $('#grid').datagrid('getSelections');
	 	if (row.length == 1) {
	 		var orgId=row[0].orgId;
	 		if(row[0].auditStatus == 3){
		 		$.ajax({
		 			url : '${basePath}orgCooperatCompanyApplyController/checkOrgCooperate.action',
		 			cache : true,
					type : "POST",
					data : {'orgId':orgId},
					async : false,
					success : function(data, status) {
		 				var flag = data;
		 				if(flag == '1'){
		 					$.messager.alert("操作提示","此机构已存在合作申请,请重新选择！","error");
		 				}else{
		 					parent.openNewTab("${basePath}orgCooperatCompanyApplyController/editCooperate.action?editType=1&orgId="+orgId,"新增机构合作申请",true);
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
</script>
<title>机构管理列表</title>
</head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div id="scroll-bar-div">
   <!--图标按钮 -->
   <div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
    <form action="<%=basePath%>orgAssetsCooperationController/getAllOrgAssets.action" method="post" id="searchFrom" name="searchFrom">
     <!-- 查询条件 -->
     <div style="padding: 5px">
      <table>
       <tr>
        <td align="right" height="28">机构名称：</td>
        <td><input class="easyui-textbox" name="orgName" data-options="validType:'length[0,20]'"/></td>
        <td align="right" height="28">统一社会信用代码：</td>
        <td><input class="easyui-textbox" name=orgCode data-options="validType:'length[0,20]'"/></td>
       	<td width="80" align="right" height="28">登录名：</td>
		<td>
			<input name="loginName" class="easyui-textbox" style="width: 200px;" />
		</td>
       </tr>
       <tr>
       	<td align="right" height="28">所属合伙人：</td>
        <td><input class="easyui-textbox" name="partnerName" data-options="validType:'length[0,20]'"/></td>
        <td align="right" height="28">分配用户：</td>
        <td><input class="easyui-textbox" name="bizAdviserName" data-options="validType:'length[0,20]'"/></td>
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
		<shiro:hasAnyRoles name="ADD_ORG_COOPERATE,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addOrgCooperateInfo()">合作申请</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="ALLOCATION_ORG_ASSETS,ALL_BUSINESS">
     		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="openAllocation()">重新分配</a>
    	</shiro:hasAnyRoles>
    </div>

   </div>
   <div class="dksqListDiv" style="height: 100%;">
    <table id="grid" title="机构管理列表" class="easyui-datagrid" style="height: 100%; width: auto;"
     data-options="
		    url: '<%=basePath%>orgAssetsCooperationController/getAllOrgAssets.action',
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
     <thead data-options="frozen:true">
		<tr>
		 	<th data-options="field:'orgId',checkbox:true"></th>
       		<th data-options="field:'loginName',sortable:true" align="center" halign="center">登录名</th>
       		<th data-options="field:'orgName',sortable:true" align="center" halign="center">机构名称</th>
       		<th data-options="field:'orgCode',sortable:true" align="center" halign="center">统一社会信用代码</th>
		 </tr>
	 </thead>
     <thead>
      <tr>
       <!-- <th data-options="field:'orgId',checkbox:true"></th>
       <th data-options="field:'loginName',sortable:true" align="center" halign="center">登录名</th>
       <th data-options="field:'orgName',sortable:true" align="center" halign="center">机构名称</th>
       <th data-options="field:'orgCode',sortable:true" align="center" halign="center">机构代码</th> -->
       <th data-options="field:'email',sortable:true" align="center" halign="center">邮箱</th>
       <th data-options="field:'contact',sortable:true" align="center" halign="center">联系人</th>
       <th data-options="field:'phone',sortable:true" align="center" halign="center">联系人电话</th>
       <th data-options="field:'partnerName',sortable:true" align="center" halign="center">所属合伙人</th>
       <th data-options="field:'bizAdviserName',sortable:true" align="center" halign="center">分配用户</th>
       <th data-options="field:'allocationDate',sortable:true" align="center" halign="center">分配时间</th>
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

  </div>
 </div>
</body>
</html>
