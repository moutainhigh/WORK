<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/tree_common.jsp"%>
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
	//用于提交
	var submitFalg = true;
	var url = "";

	var chars = [ '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B',
			'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
			'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b',
			'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
			'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' ];

	// 产生动态密码
	function generateMixed(n) {
		var res = "";
		for (var i = 0; i < n; i++) {
			var id = Math.ceil(Math.random() * 59);
			res += chars[id];
		}
		return res;
	}
	// 新增
	function addItem() {
		$('#fm').form('reset');
		// 显示与隐藏 input 且 不提交隐藏div里的元素
		$("#add").show();
		$("#update").hide();
		$("#add input").attr("disabled", false);
		$("#update input").attr("disabled", true);
		$("#pwd").textbox('setValue', generateMixed(8));
		// 将 url 地址改变
		url = "insert.action";
		$('#dlg').dialog('open').dialog('setTitle', "新增");
	}
	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			// 显示与隐藏 input 且 不提交隐藏div里的元素
			$("#update").show();
			$("#add").hide();
			$("#update input").attr("disabled", false);
			$("#add input").attr("disabled", true);
			$('#fm').form('load', row[0]);
			// 将 url 地址改变
			url = "update.action";
			$('#dlg').dialog('open').dialog('setTitle', "编辑");
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择一条用户数据！", "info");
		}
	}
	//重置密码
	function resetPwd() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		// 获取选中的系统用户名  and 判断是否存在系统管理员
		var userIds = "";
		for (var i = 0; i < rows.length; i++) {
			if (i > 0) {
				userIds += ",";
			}
			userIds += rows[i].pid;
			if (rows[i].userName == "admin") {
				$.messager.alert("操作提示", "不能重置超级管理员的密码", "error");
				return;
			}
		}
		$.messager.confirm("操作提示", "确定重置选择的系统用户密码吗?", function(r) {
			if (r) {
				$.post("resetPwd.action", {
					userIds : userIds
				}, function(ret) {
					//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
					if (ret) {
						$.messager.alert('操作提示', ret.header["msg"], 'info');
						// 必须重置 datagr 选中的行 
						$("#grid").datagrid("clearChecked");
						$("#grid").datagrid('reload');
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}, 'json');
			}
		});
	}

	// 删除
	function removeItem() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		// 获取选中的系统用户名  and 判断是否存在系统管理员
		var userNames = "";
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			userNames += row.userName + ",";
			if (row.userName == "admin") {
				$.messager.alert("操作提示", "不能删除超级管理员", "error");
				return;
			}
		}
		$.messager.confirm("操作提示", "确定删除选择的此批系统用户吗?", function(r) {
			if (r) {
				$.post("delete.action", {
					userNames : userNames
				}, function(ret) {
					//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
					if (ret && ret.header["code"] == 200) {
						$("#dlg").dialog('close');
						// 必须重置 datagr 选中的行 
						$("#grid").datagrid("clearChecked");
						$("#grid").datagrid('reload');
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}, 'json');
			}
		});
	}
	// 新增  or 更新
	function saveItem() {
		if (submitFalg) {
			$("#fm").form('submit', {
				url : url,
				onSubmit: function(){   
// 			        alert(1);  
			    }, 
				success : function(result) {
					// 转换成json格式的对象
					var ret = eval("(" + result + ")");
					if (ret && ret.header["success"]) {
						$("#dlg").dialog('close');
						$("#fm").form('clear');
						$("#grid").datagrid('reload');
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'info');
					}
				}
			});
		} else {
			$.messager.alert('操作提示', "请检查一下数据", 'info');
		}
	}

	// 查询 
	function ajaxSearch() {
		$('#grid').datagrid('load', {
			userName : $("#userName").val(),
			memberId : $("#memberId").val(),
			realName : $("#realName").val()
		});
		$('#grid').datagrid('clearChecked');
	}

	// 查询 
	function ajaxSearch1() {
		$('#grid1').datagrid('load', {
			userName : $("#userName1").val(),
			memberId : $("#memberId1").val(),
			realName : $("#realName1").val()
		});
	}

	//打开直接上属窗口 
	function selectSuperior() {
		$('#dlgZjss').dialog('open').dialog('setTitle', "选择直接上属");
	}

	//
	function selectZjss() {
		var row = $('#grid1').datagrid('getSelected');
		if (!row) {
			$.messager.alert('操作提示', "请选择一个用户!", 'info');
			return;
		}
		$("#superioridValue_id").val(row.pid);
		$('#superiorName').textbox('setValue', row.realName);
		// 关闭选取直接上属窗口
		$('#dlgZjss').dialog('close');
	}
	//取消方法
	function cancelFunc() {
		$("#fm").form('clear');
		$('#dlg').dialog('close');
	}

	$(function() {
		/**
		 * 检查员工工号是否存在
		 */
		$('body').delegate(
						'#memberIdDiv span:first > input:first',
						'blur',
						function() {
							//员工工号
							var memberId = $(this).val();
							var userId=$("#userId").val();
							$.ajax({
										url : "${basePath}sysUserController/checkMemberIdIsExist.action",
										data : {
											"memberId" : memberId,
											"pid" : userId
										},
										dataType : "json",
										async : false,
										success : function(ret) {
											submitFalg = ret;
											if (!submitFalg) {
												$.messager.alert('操作提示',
														"员工工号已存在!", 'info');
											}
										}
									});
						});
	});
	$
			.extend(
					$.fn.validatebox.defaults.rules,
					{
						//检查员工账户
						checkUname : {//value值为文本框中的值
							validator : function(value) {
								var r = false;
								$.ajax({
											url : "${basePath}sysUserController/checkUserNameIsExist.action",
											data : {
												"userName" : value
											},
											dataType : "json",
											async : false,
											success : function(ret) {
												r = ret;
											}
										});
								return r;
							},
							message : '账号已存在!'
						},
						//检查员工账户
						checkUphone : {//value值为文本框中的值
							validator : function(value) {
								var r = false;
								var userId=$("#userId").val();
								$.ajax({
											url : "${basePath}sysUserController/checkPhoneIsExist.action",
											data : {
												"phone" : value,
												"pid" : userId
											},
											dataType : "json",
											async : false,
											success : function(ret) {
												r = ret;
											}
										});
								return r;
							},
							message : "手机号码已存在!"
						}
					});
	
	function clearSuperior(){
		$('#superiorName').textbox('setValue','');
		$('#superioridValue_id').val('');
	}
	
	
	var zTree;  
	var treeNodes;  
	var submitFalg = true;
	var existsFlag = false;
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
	
	/**
	 * 初始化加载数据(构造树)
	 */
	function initTree() {
		
		$('#dlgOrg').dialog('open').dialog('setTitle', "选择所属机构");
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
	};
	
	function beforeClick(treeId, treeNode, clickFlag) {
		$("#orgId").val(treeNode.id);
		$("#orgName").textbox("setValue",treeNode.name);
		return (treeNode.click != false);
	}
	
	function cancleOrg(){
		$("#orgId").val();
		$("#orgName").textbox("setValue","");
		$('#dlgOrg').dialog('close');
	}
	
	$(document).ready(function() {
		$('#grid').datagrid({  
		    onClickRow: function (rowIndex, rowData) {
				findRoleListByUserName(rowData.userName);
		    },onLoadSuccess:function(data){
				clearSelectRows('#grid');
			} 
		}) ;
		$('#rolePermisGrid').datagrid({  
		    onClickRow: function (rowIndex, rowData) {
		    	findPermisOfRole(rowData.pid);
		    },onLoadSuccess:function(data){
				clearSelectRows('#rolePermisGrid');
			} 
		}) ;
	});
	
	//查询用户拥有的角色
	function findRoleListByUserName(userName){
		var permisName = $("#userName").val();
		if(userName=="" || null==userName){
			$.messager.alert("提示","用户选择错误","info");
			return;
		} 
// 		alert(userName);
		permisRoleId = userName;
		$("#rolePermisGrid").datagrid({
		    url:  '${basePath}systemRoleController/getRoleIdListByUserName.action',
			    method: 'POST',
			    queryParams: { 'userName': userName},
			    striped: true,
			    fitColumns: true,
			    singleSelect: false,
			    toolbar: '#rolePermisTool',
			    rownumbers: true,
			    pagination: true,
			    nowrap: false,
			    showFooter: true,
			    idField: 'pid',
			    fitColumns:true,
				selectOnCheck: true,
				checkOnSelect: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    onLoadSuccess:function(data){
					clearSelectRows('#rolePermisGrid');
				},
			    columns: [[
			        { field: 'pid', checkbox: true},
			        { field: 'roleName', title: '角色名称', width: 70,sortable:true },
			        { field: 'roleDesc', title: '角色描述', width: 120,sortable:true},
			        { field: 'roleCode', title: '角色编码', width: 210,sortable:true}
			    ]]
		});
	}
	
	//查询用户拥有的权限
	function findPermisOfRole(rid){
		var permisName = null;
		if(rid=="" || null==rid){
			$.messager.alert("提示","用户选择错误","info");
			return;
		} 
		permisRoleId = rid;
		$("#rolePermisGrid1").datagrid({
			    url: '${basePath}systemRoleController/getSysPermissionList.action',
			    method: 'POST',
			    queryParams: { 'pid': rid,'permisName': permisName},
			    striped: true,
			    fitColumns: true,
			    singleSelect: false,
			    toolbar: '#rolePermisTool',
			    rownumbers: true,
			    pagination: true,
			    nowrap: false,
			    showFooter: true,
			    idField: 'pid',
			    fitColumns:true,
				selectOnCheck: true,
				checkOnSelect: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    onLoadSuccess:function(data){
					clearSelectRows('#rolePermisGrid1');
				},
			    columns: [[
			        { field: 'pid', checkbox: true},
			        { field: 'permisType', title: '类别', width: 70,sortable:true },
			        { field: 'permisName', title: '权限', width: 120,sortable:true},
			        { field: 'permisDesc', title: '描述', width: 210,sortable:true}
			    ]]
		});
	}
	
</script>
<title>系统用户管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
			<div style="height: 380px;">
				<!--图标按钮 -->
			<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
				<form action="getAllSysUser.action" method="post" id="searchFrom"
					name="searchFrom">
					<!-- 查询条件 -->
					<div style="margin: 5px">
						真实姓名:<input class="easyui-textbox" name="realName" id="realName" />
						员工账户:<input class="easyui-textbox" name="userName" id="userName" />
						员工工号:<input class="easyui-textbox" name="memberId" id="memberId" />
						<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							onclick="ajaxSearch()">查询</a>
					</div>
				</form>
				<!-- 操作按钮 -->
				<div style="padding-bottom: 5px">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true" onclick="addItem()">新增</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="editItem()">修改</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true" onclick="resetPwd()">重置密码</a>
				</div>
			</div>
			<table id="grid" title="系统用户管理" class="easyui-datagrid"
				style="height: 380px; width: auto;"
				data-options="
			    url: 'getAllSysUser.action',
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
						<th data-options="field:'personalQQ',sortable:true">个人QQ</th>
						<th data-options="field:'enterpriseQQ',sortable:true">企业QQ</th>
						<th data-options="field:'phone',sortable:true">手机号码</th>
						<th data-options="field:'workPhone',sortable:true">工作电话</th>
						<th data-options="field:'extension',sortable:true">座机分号</th>
						<th
							data-options="field:'status',formatter:formatStatus,sortable:true">状态</th>
					</tr>
				</thead>
			</table>
			</div>
			<div style="height: 380px; width:100%;">
				<div style="height: 380px;  width: 50%;  float:left;" id="RolePermis">
<!-- 						<div id="rolePermisTool" class="easyui-panel" style="border-bottom: 0;doSize:true"> -->
						<!-- 操作按钮 -->
<!-- 						<div style="margin:5px"> -->
<!-- 							角色名称:<input type="text" class="text_style" id="rolePermisName" name ="rolePermisName"/>  -->
<!-- 							<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searchRolePermisNameBtn" >查询</a> -->
<!-- 						</div> -->
<!-- 						</div> -->
						<table id="rolePermisGrid" title="系统角色管理" class="easyui-datagrid" 
							   style="height:380px;width:550px;"data-options="toolbar:'#rolePermisTool'">
						</table>
				</div>

				<div style="height: 380px;  width: 50%;  float:left;">
<!-- 						<div id="roleUserTool" class="easyui-panel" style="border-bottom: 0;doSize:true"> -->
						<!-- 操作按钮 -->
<!-- 						<div style="margin:5px"> -->
<!-- 								姓名:<input type="text" class="text_style"  name ="realName" id="realName"/>  -->
<!-- 								<a href="#" class="easyui-linkbutton" iconCls="icon-search" id="searchUserOfRoleNameBtn" >查询</a> -->
<!-- 						</div> -->
<!-- 						</div> -->
				<table id="rolePermisGrid1" title="该角色已拥有的权限" class="easyui-datagrid" 
			   		style="height:380px;width:500px;"data-options="toolbar:'#rolePermisTool'">
				</table>
				</div>
			</div>
			<!-- 保存 和取消按钮 -->
			<div id="dlg-buttons">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-save" onclick="saveItem()">保存</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel" onclick="cancelFunc()">取消</a>
			</div>
			<!--窗口 -->
			<div id="dlg" class="easyui-dialog"
				style="width: 700px; height: 350px; padding: 20px 20px"
				closed="true" buttons="#dlg-buttons">
				<form id="fm" method="post">
					<div class="fitem" id="update">
						<label style="text-align: right;"><font color="red">*</font>员工账户：</label>
						<input name="userName" id="userName" class="easyui-textbox" readonly="readonly" missingMessage="请填写用户名!">
						<label style="text-align: right;">状态：</label>
<!-- 						<input name="status" class="easyui-numberbox"> -->
						  <select class="form-control" name="status" id="status">
				           <option value=1 <c:if test="${status==1}">selected="selected"</c:if>>正常</option>
				           <option value=2 <c:if test="${status==4}">selected="selected"</c:if>>无效</option>
         				 </select>
						<input type="hidden" name="pid" id="userId"/>
					</div>
					<div class="fitem" id="add">
						<label style="text-align: right;"><font color="red">*</font>员工账户：</label>
						<input name="userName" id="userName" class="easyui-textbox"
							data-options="required:true,validType:'checkUname'"
							missingMessage="请填写用户名!"> <label
							style="text-align: right;" id="pwd_text"><font
							color="red">*</font>密码：</label> <input name="pwd" id="pwd"
							class="easyui-textbox" data-options="required:true,validType:'englishOrNum'"
							missingMessage="请输入数字或者字母，6到18个字符!">
					</div>
					<div class="fitem">
						<label style="text-align: right;"><font color="red">*</font>真实姓名：</label>
						<input name="realName" id="realName" class="easyui-textbox"
							data-options="required:true" missingMessage="请填写真实姓名!"> <label
							style="text-align: right;">部门：</label> <input name="department"
							class="easyui-textbox">
					</div>
					<div class="fitem" id="memberIdDiv">
						<label style="text-align: right;"><font color="red">*</font>员工工号：</label>
						<input name="memberId" class="easyui-textbox"
							data-options="required:true" missingMessage="请填写员工工号!">
						<label style="text-align: right;">职位：</label> <input
							name="jobTitle" class="easyui-textbox">
					</div>
					<div class="fitem">
						<label style="text-align: right;"><font color="red">*</font>手机号码：</label>
						<input name="phone" class="easyui-textbox"
							data-options="required:true,validType:['checkUphone','phone']" missingMessage="请填写手机号码!"> <label
							style="text-align: right;"><font color="red">*</font>邮箱：</label>
						<input name="mail" class="easyui-textbox"
							data-options="required:true,validType:'email'" missingMessage="请填写邮箱!">
					</div>
					<div class="fitem">
						<label style="text-align: right;"><font color="red">*</font>企业QQ：</label>
						<input name="enterpriseQQ" class="easyui-numberbox"
							data-options="required:true" missingMessage="请填写企业QQ!"> <label
							style="text-align: right;">个人QQ：</label> <input name="personalQQ"
							class="easyui-numberbox">
					</div>
					<div class="fitem">
						<label style="text-align: right;">办公 电话：</label> <input
							name="workPhone" class="easyui-textbox"> 分机- <input
							name="extension" class="easyui-textbox" style="width: 40px;">
						<!-- <input name="directlySuperior" id="directlySuperior" type="text"
							class="easyui-textbox" missingMessage="直属上级"> -->

					</div>
					<div class="fitem">
						<label style="text-align: right;">直属上级：</label> <input
							id="superiorName" disabled="true" name="superiorName"
							class="easyui-textbox"> <input name="superior" style="width:100px;height:25px"
							id="superior_id" type="button" value="选择直属上级"
							onclick="selectSuperior();"> <input name="superiorId"
							id="superioridValue_id" type="hidden">
							<input style="width:100px;height:25px" type="button" value="删除上级" onclick="clearSuperior()">
					</div>
					<div class="fitem">
						<label style="text-align: right;"><font color="red">*</font>所属机构：</label>
						<input type="text" id="orgName" name="orgName" editable="false" class="easyui-textbox" data-options="required:true" missingMessage="请请选择所属机构!">
						<input name="org" style="width:100px;height:25px" onclick="initTree();"	id="org" type="button" value="选择所属机构">
						<input type="hidden" name="orgId" id="orgId" />
					</div>
				</form>
			</div>

			<div id="dlg-buttonsZjss">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-save" onclick="selectZjss()">确定</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-cancel"
					onclick="javascript:$('#dlgZjss').dialog('close')">取消</a>
			</div>
			<div id="dlgZjss" class="easyui-dialog"
				style="width: 940px; height: 380px; padding: 10px" closed="true"
				buttons="#dlg-buttonsZjss">
				<div id="toolbar1" class="easyui-panel" style="border-bottom: 0;">
					<form action="getAllSysUser.action" method="post" id="searchFrom"
						name="searchFrom">
						<!-- 查询条件 -->
						<div style="margin: 5px">
							真实姓名:<input class="easyui-textbox" name="realName" id="realName1" />
							员工账户:<input class="easyui-textbox" name="userName" id="userName1" />
							员工工号:<input class="easyui-textbox" name="memberId" id="memberId1" />
							<a href="#" class="easyui-linkbutton" iconCls="icon-search"
								onclick="ajaxSearch1();">查询</a>
						</div>
					</form>
				</div>
				<div style="height: 100%">
					<table id="grid1" class="easyui-datagrid"
						style="height: 100%; width: auto;"
						data-options="
					    url: 'getAllSysUser.action',
					    method: 'POST',
					    rownumbers: true,
					    singleSelect: true,
					    pagination: true,
					    sortOrder:'asc',
					    remoteSort:false,
					    toolbar: '#toolbar1',
					    idField: 'pid',
					    onLoadSuccess:function(data){
		    				clearSelectRows('#grid1');
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
								<th data-options="field:'personalQQ',sortable:true">个人QQ</th>
								<th data-options="field:'enterpriseQQ',sortable:true">企业QQ</th>
								<th data-options="field:'phone',sortable:true">手机号码</th>
								<th data-options="field:'workPhone',sortable:true">工作电话</th>
								<th data-options="field:'extension',sortable:true">座机分号</th>
								<th data-options="field:'status',formatter:formatStatus,sortable:true">状态</th>
							</tr>
						</thead>
					</table>
				</div>
			</div>
	</div>
	<!-- 机构树 -->
	<div id="dlgOrg" class="easyui-dialog"
				style="width: 400px; height: auto; padding: 10px" closed="true"
				buttons="#dlg-buttonsOrg">
		<ul id="tree" class="ztree">
	</div>
	</div>
	<div id="dlg-buttonsOrg">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-save" onclick="javascript:$('#dlgOrg').dialog('close')">确定</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel"
			onclick="javascript:cancleOrg();">取消</a>
	</div>
	
	
</body>
</html>
