<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">

</style>

<script type="text/javascript">

//新增
function addItem(){
	parent.openNewTab("${basePath}customerController/editComBases.action?comId=${comId}&acctId=${acctId}&flag=1","新增企业客户");
	//window.location.href='${basePath}customerController/editComBases.action?comId=${comId}&acctId=${acctId}';
}
//编辑
function editItem(){
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		parent.openNewTab('${basePath}customerController/editComBases.action?acctId='+row[0].pid+"&comId="+row[0].value13+'&currUserPid='+${currUser.pid}+'&flag='+2,"修改企业客户",true);
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		alert("操作提示","请选择数据","error");
	}
}

//保存
function saveItem(){
	var roleName = $("#saveRoleName").val();
	var roleDesc = $("#saveRoleDesc").val();
	
	$.ajax({
		type: "POST",
        url: "addRole.action",
        data: {"roleName":roleName, "roleDesc":roleDesc},
        dataType: "json",
        success: function(data){	
        	alert("新增成功"); 
        }
	});
	$("#grid").datagrid("clearChecked");
	$("#grid").datagrid('reload');
	$('#fm').form('clear');
	$('#dlg').dialog('close');
}

//删除
function removeItem(){
	  var rows = $('#grid').datagrid('getSelections');
	  var flag=true;
	  var acctId=rows[0].pid;
	  var cusStatus =rows[0].value10;
// 	  if(cusStatus=="借贷"){
// 			$.messager.alert("操作提示","该客户已经存在借贷记录不能删除，请确认！","error");
// 			return flag=false;
// 		}
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}// 获取选中的系统用户名 
		
	 	var pid = "";
		for(var i=0;i<rows.length;i++){
	  		if(i==0){
	  			pid+=rows[i].pid;
	  		}else{
	  			pid+=","+rows[i].pid;
	  		}
	  	}
		if(flag){
			if(cusStatus=='借贷'){
				$.ajax({
					type: "POST",
			        url: "selectProjectStatus.action",
			        data: {"acctID":acctId},
			        dataType: "json",
			        cache: false,
			        success: function(result){	
						var type=result.header["flags"];
						if(type>0){
							$.messager.alert("操作提示","该客户已经存在借贷记录不能删除，请确认！","error");
						} 
						else{
							$.messager.confirm("操作提示","确定删除选择的此批数据吗?",
									function(r) {
							 			if(r){
											$.post("deleteComBaseInfo.action",{pid : pid}, 
												function(ret) {
													//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
														$("#dlg").dialog('close');
														$("#grid").datagrid("clearChecked");
														$("#grid").datagrid('reload');
												},'json');
							 			}
									});
						}
			        }
				});
			}
			else{
				$.messager.confirm("操作提示","确定删除选择的此批数据吗?",
						function(r) {
				 			if(r){
								$.post("deleteComBaseInfo.action",{pid : pid}, 
									function(ret) {
										//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
											$("#dlg").dialog('close');
											$("#grid").datagrid("clearChecked");
											$("#grid").datagrid('reload');
									},'json');
				 			}
						});
			}
			
		}
	 	
	  	
};   
function business(){
	var row = $('#grid').datagrid('getSelections');
	var pid = row[0].pid;
	var comId = row[0].value13;
	if (row.length == 1) {
		window.location.href ='${basePath}customerController/listComBusiness.action?acctId='+pid+'&comId='+comId;
	}else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能操作一条数据,请重新选择！", "error");
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
	
}
function applications(){
	var rows = $("#grid").datagrid('getSelections');
	var pid = rows[0].pid;
	var cusStatus =rows[0].value10;
	if(cusStatus.indexOf("黑名单")>-1 || cusStatus.indexOf("拒贷")>-1){
		$.ajax({
			url : '${basePath}customerController/searcherBlackListRefuse.action?pid='
				+ pid,
			type : 'post',
			cache: false,
			success : function(result) {
				var ret = eval("("+result+")");
				var tip=ret.header["tip"]; 
				if(tip !=""){
					alert("您不能新增贷款申请，原因是:"+tip);
				}
			}
		})
	}
	else{
		var url = '<%=basePath%>beforeLoanController/addNavigation.action?acctId='+pid; 
		parent.openNewTab(url,"贷款申请办理",true);
	}
}

//查询 
function ajaxSearch(){
	var pageNumber=$('#grid').datagrid('options')['pageSize'];
	$('#rows').val(pageNumber);
	$('#searchFrom').form('submit',{
        success:function(data){
        	var str = JSON.parse(data);
           	$('#grid').datagrid('loadData',str);
           	clearSelectRows("#grid");
           	
        }
    });
}
//加入黑名单
function editBlacklistRefuse(cusStatus){
	var statusName = cusStatus == 2 ? "黑名单" : "拒贷";
	var rows = $('#grid').datagrid('getSelections');
	var pids="";
	var flag=true;
	var acctName="";
	
	if (rows.length == 0) {
		$.messager.alert("操作提示", "请选择数据", "error");
		return;
	}
	
	for (var i = 0; i < rows.length; i++) {
		if(rows[i].value11==cusStatus || rows[i].value11==5 || rows[i].value11==9 || (rows[i].value11-cusStatus)==4){
			flag=false;
			acctName=rows[i].value2;
		}
		if (i == 0) {
			pids += rows[i].pid;
		} else {
			pids += "," + rows[i].pid;
		}
	}
	if(!flag){
		$.messager.alert("操作提示", "客户("+acctName+")已经加入"+ statusName + ",请重新选择！","warning");
		return;
	}
	
	$('<div id="addblacklist"/>').dialog({
			href : '${basePath}customerController/editBlacklistRefuse.action?acctIds='
		+ pids
		+ '&cusStatus='
		+ cusStatus,
			width : 500,
			height : 260,
			modal : true,
			title : '新增'+statusName+'信息',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.save();
				}	
				},{
					text : '取消 ',
					iconCls : 'icon-cancel',
					handler : function() {
						$("#addblacklist").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});	
 		
	
}
//导出
 function comExport(){
	 var rows = $('#grid').datagrid('getSelections');
	 var pids = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}
		$.ajax({
			url:BASE_PATH+'templateFileController/checkFileUrl.action',
			data:{templateName:'COMOUT'},
			dataType:'json',
			success:function(data){
				if(data==1){				
					window.location.href ="${basePath}customerController/comExcelExportList.action?pids="+pids;
				}else{
					alert('企业客户的导出模板不存在，请上传模板后重试');
				}
			}
		})
	
}
$(function(){
	setCombobox("com_all_nature","UNIT_NATURE","");
});


</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div id="scroll-bar-div">

	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="getComBaseInfo.action" method="post" id="searchFrom" name="searchFrom" >
		<!-- 查询条件 -->
		<div style="padding:5px">
			<table>
			<tr>
				<td width="80" align="right" height="25">企业名称：</td>
				<td><input class="easyui-textbox" name="cpyName" /> </td>
				<td width="120" align="right">组织机构代码：</td>
				<td colspan="2"><input class="easyui-textbox" name="orgCode"/> </td>
				<td width="80" align="right" height="25">企业状态：</td>
				<td>
					<select name="cusStatus" editable="false"
						class="easyui-combobox" style="width: 150px;">
						<option value="0">--请选择--</option>
						<option value="1">普通</option>
						<option value="2">黑名单</option>
						<option value="3">拒贷</option>
						<option value="4">借贷</option>
						<option value="5">黑名单、拒贷</option>
						<option value="6">借贷、黑名单</option>
						<option value="7">借贷、拒贷</option>
						<option value="9">借贷、黑名单、拒贷</option>
					</select>
				</td>
			</tr>
			<tr>
				<td width="80" align="right" height="25">所有制性质：</td>
				<td><select  class="select_style easyui-combobox" editable="false" id="com_all_nature" name="comAllNature" style="width:150px;"></select></td>
				<td width="120" align="right">营业执照号码：</td>
				<td colspan="2"><input class="easyui-textbox" name="busLicCert"/> </td>
				<td align="right" colspan="2">
					<input type="hidden" name="page" value="1">
					<input type="hidden" name="rows" id="rows">
					<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearch()">查询</a>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');"
						iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
			</table>
			
		</div>
	</form>
	
	<!-- 操作按钮 -->
	<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"  onclick="editItem()">修改</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-wanglai" plain="true" onclick="business()">业务往来</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="applications()">新贷款申请</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="comExport()">导出Excel</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="editBlacklistRefuse(2)">加入黑名单</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="editBlacklistRefuse(3)">加入拒贷客户</a>
	</div>
	</div>
	<div class="comBaseInfoDiv" style="height:100%;">
	<table id="grid" title="企业客户信息查询" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="
	    url: 'getComBaseInfo.action',
	    method: 'POST',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    sortOrder:'asc',
	    remoteSort:false,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true
	    ">
	<!-- 表头 -->
		<thead><tr>
			<th data-options="field:'pid',checkbox:true,width:100" ></th>
		    <th data-options="field:'value1',width:100,sortable:true" align="center">企业名称</th>
		    <th data-options="field:'value2',width:100,sortable:true" align="center">企业简称</th>
		    <th data-options="field:'value3',width:100,sortable:true" align="center">组织机构代码</th>
		    <th data-options="field:'value4',width:100,sortable:true" align="center">营业执照号码</th>
		    <th data-options="field:'value5',width:100,sortable:true" align="center">所有制性质</th>
		    <th data-options="field:'value6',width:100,sortable:true" align="center">法人代表</th>
		    <th data-options="field:'value7',width:100,sortable:true" align="center">注册资金</th>
		    <th data-options="field:'value8',width:100,sortable:true" align="center">联系电话</th>
		    <th data-options="field:'value9',width:100,sortable:true" align="center">企业成立日期</th>
		    <th data-options="field:'value10',width:100,sortable:true" align="center">企业状态</th>
		    <th data-options="field:'value12',width:100,sortable:true" align="center">客户经理</th>
		</tr></thead>
	</table>
	</div>
</div>
</div>
</body>




 