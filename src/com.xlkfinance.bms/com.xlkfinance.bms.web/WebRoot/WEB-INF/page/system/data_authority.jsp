<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/tree_common.jsp" %>    
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据授权</title>
<script type="text/javascript">

var zTree;  
var orgTree;
var treeNodes;  
var url = "";

var setting = {
	view: {
		dblClickExpand: false,
		showLine: true,
		selectedMulti: false
	},
	data: {
		simpleData: {
			enable:true,
			idKey: "id",
			pIdKey: "pId",
			rootPId: "",
			autoParam: ["id","name"]
		}
	},
	callback: {		
		beforeClick:beforeClick
	}
};

var checkedSetting = {
		check:{
			enable:true,
			chkboxType:{
				Y:"",
				N:""
			}
		},
		view:{
			dblClickExpand:false,
			showLine:true,
			selectedMulti:true
		},
		data:{
			simpleData:{
				enable:true,
				idKey:"id",
				pIdKey:"pId",
				rootPId:"",
				autoParam: ["id","name"]
			}
		}
};
/**
 * 初始化加载数据(构造树)
 */
function initTree() {
	$.ajax({
		url : "<%=basePath%>sysOrgInfoController/treelist.action",
		type : 'post',
		cache: false,
		dataType : "json",
		success : function(result) {
			treeNodes = result;
			zTree = $.fn.zTree.init($("#tree"), setting, treeNodes);
			zTree.expandAll(false);
		},
		error:function(){
			alert("请求失败!");
		}
	});
}

//初始化机构树
$(document).ready(function(){
	 var orgNo = $("#sysOrgId").val();
	 initTree();
	 if(orgNo == null || orgNo == ''){
		 $("#grid").datagrid({
			 url:"getAllSysUserNormal.action"
		 });
	 }
	 
});

function beforeClick(treeId, treeNode, clickFlag) {
	$("#sysOrgId").val(treeNode.id);
	$("#grid").datagrid('load', {
		orgId:treeNode.id
	});
	$('#grid').datagrid('clearChecked');
	return (treeNode.click != false);
}
	
//查询 
function ajaxSearch() {
	
	// 重新加载数据
	$('#grid').datagrid('load', $.serializeObject($("#searchFrom")));
	// 清空所选择的行数据
	clearSelectRows('#grid');
	
/* 	$('#grid').datagrid({
		url:'getAllSysUser.action',
		userName : $("#userName").val(),
		memberId : $("#memberId").val(),
		realName : $("#realName").val()
	}); */
	$('#grid').datagrid('clearChecked');
}

//打开机构数据授权窗口 
function authortity() {
	var rows = $('#grid').datagrid('getSelections');
	
	if(rows == null || rows.length == 0){
		$.messager.alert("操作提示", "请选择用户！", "error");
		return;
	}
	$('#dlg').dialog('open').dialog('setTitle', "数据授权");
	$.ajax({
		url : "<%=basePath%>sysOrgInfoController/treelist.action",
		type : 'post',
		cache: false,
		dataType : "json",
		success : function(result) {
			treeNodes = result;
			orgTree = $.fn.zTree.init($("#orgTree"), checkedSetting, treeNodes);
			orgTree.expandAll(false);
		},
		error:function(){
			alert("请求失败!");
		}
	});
	$("#dataScope")[0].checked=true;
	
}


//数据授权
function dataAuth(){
	orgTree = $.fn.zTree.getZTreeObj("orgTree")
	var checkedNodes = orgTree.getCheckedNodes(true);
	var rows = $('#grid').datagrid('getSelections');
	var nodeIds = "";
	var userIds = "";
	//获取userid
	if(rows != null && rows.length > 0){
		
		rows.forEach(function(row){
			
			userIds +=row.pid+",";
		});
		$("#userIds").val(userIds);
	}else{
		
		$.messager.alert("操作提示", "请选择用户！", "error");
		return;
	}
	//获取被选中的机构id
	if(checkedNodes != null && checkedNodes.length){
		
		checkedNodes.forEach(function(node){
			nodeIds += node.id+",";
		});
		$("#sysOrgIds").val(nodeIds);
	}else{
		$.messager.alert("操作提示", "请选择机构！", "error");
		return;
	}
	//保存数据授权信息
	$("#fm").form('submit', {
		url : "<%=basePath%>sysUserController/saveDataAuthrotity.action",
		success : function(result) {
			// 转换成json格式的对象
			var ret = eval("(" + result + ")");
			if (ret && ret.header["success"]) {
				$.messager.alert('操作提示', '数据授权成功!', 'info');
				$("#dlg").dialog('close');
				$("#fm").form('clear');
				initTree();
			} else {
				$.messager.alert('操作提示', ret.header["msg"], 'info');
			}
		}
	});
}
</script>
</head>
<body class="easyui-layout">
<div style="height:100%;" data-options="region:'center',border:false">
<table style="height: 100%; width: 100%;">
<tr>
	<td style="width:22%;height:auto;vertical-align:top; ">
		<ul id="tree" class="ztree" style="border: 1px #95B8E7 solid;margin: 10px;height:auto; min-height:700px; overflow-x: hidden;"></ul>
	</td>
	<td width="75%" style="vertical-align:top; ">
	<div  data-options="region:'center',border:false">
	<form action="getAllSysUser.action" method="post" id="searchFrom"	name="searchFrom">
		<!-- 查询条件 -->
		<div style="margin:15px 5px;">
			真实姓名:<input class="easyui-textbox" name="realName" id="realName" />
			员工账户:<input class="easyui-textbox" name="userName" id="userName" />
			员工工号:<input class="easyui-textbox" name="memberId" id="memberId" />
			<a href="#" class="easyui-linkbutton" iconCls="icon-search"
				onclick="ajaxSearch()">查询</a>
		</div>
	</form>	
	</div>
		<table id="grid" title="数据授权" class="easyui-datagrid"
				style="min-height:665px;  width: auto;"
				data-options="
			    method: 'POST',
			    rownumbers: true,
			    singleSelect: false,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    onLoadSuccess:function(data){
		    		clearSelectRows('#grid');
		   		},
			    fitColumns:true">
				<!-- 表头 -->
				<thead>
					<tr>
						<th height="30" data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'realName',sortable:true">真实姓名</th>
						<th data-options="field:'userName',sortable:true">员工账户</th>
						<th data-options="field:'memberId',sortable:true">员工工号</th>
						<th data-options="field:'department',sortable:true">部门</th>
						<th data-options="field:'jobTitle',sortable:true">职位</th>
						<th data-options="field:'mail',sortable:true">电子邮箱</th>
						<th data-options="field:'phone',sortable:true">手机号码</th>
					</tr>
				</thead>
			</table>
			<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">	
				<!-- 操作按钮 -->
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="authortity()">数据授权</a>
				</div>
			</div>
	</td>
</tr>
</table>
<!-- 机构树窗口(选择机构给用户授予数据权限) -->
<div id="dlg" class="easyui-dialog"	style="width: 700px; height: 350px; padding:10px;"	closed="true" buttons="#dlg-buttons">
<form id="fm" method="post">
<input type="hidden" id="sysOrgIds" name="sysOrgIds"> 
<input type="hidden" id="userIds" name="userIds"> 
<table style="height: 100%; width: 100%;">
<tr>
	<td style="height: auto; width:22%;vertical-align:top;border: 1px #95B8E7 solid; ">
		<ul id="orgTree" class="ztree"></ul>
	</td>
	<td style="height: auto; width: 75%;text-align: left;padding:0 0 10px 10px;vertical-align:top; ">
		<div class="fitem" id="memberIdDiv">
			<label style="text-align:left;"><b><font color="red">*</font>数据范围：</b></label>
			<label style="text-align:left;">
				<input type="radio" id="dataScope" name="dataScope" class="easyui-radiobox" checked="true"	 value="1">私有数据
			</label>&nbsp;&nbsp;
			<label style="text-align: left;">
				<input type="radio" id="dataScope" 	name="dataScope" class="easyui-radiobox" value="2">集体数据
			</label> 
			
		</div>
		<div class="fitem">
			<p style=" text-align: left; line-height: 22px;">
				<b>数据范围说明：</b><br/>
				   1、私有数据是指业务线员工创建的业务数据，仅自己可以查看，同级无权限查看。<br/>
				   2、集体数据是指业务线员工创建的数据，ta的上级可以查看，同级无权限查看。<br/>
				        集体数据一般仅授予管理人员和非业务线人员。<br/>
				<b>授权说明：</b><br/>
				   1、当选中了左边机构树的某几个节点后点击授权后，用户获得了这些机构的数据权限。<br/>
				   2、当未选择机构树节点进行授权时，用户仅查看所属机构的数据。
			</p>
		</div>
	</td>
</tr>
</table>
<div id="dlg-buttons">
<a href="javascript:void(0)" class="easyui-linkbutton"
	iconCls="icon-save" onclick="dataAuth()">确定</a> <a
	href="javascript:void(0)" class="easyui-linkbutton"
	iconCls="icon-cancel"
	onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>
</form>
</div>
</div>

</body>
</html>