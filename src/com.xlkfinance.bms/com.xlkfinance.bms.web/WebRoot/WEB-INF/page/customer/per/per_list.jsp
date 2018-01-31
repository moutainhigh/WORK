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
$(document).ready(function(){
$('#grid').datagrid({  
	 onDblClickRow: function (rowIndex, rowData){
		 var url = '${basePath}customerController/editPerBase.action?acctId='
					+ rowData.pid+'&currUserPid='+${currUser.pid}+'&flag='+2+'&isLook='+3;
		 
			parent.openNewTab(url,"查看个人客户",true);
	 }
});
});
	
	//新增
	function addItem() {
		parent.openNewTab("${basePath}customerController/editPerBase.action?flag=1&isLook=1","新增个人客户");
	}

	// 编辑
	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='
					+ row[0].pid+'&currUserPid='+${currUser.pid}+'&flag='+2+'&isLook='+1,"修改个人客户",true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	function lookupItem(){
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 1) {
			parent.openNewTab('${basePath}customerController/editPerBase.action?acctId='
					+ row[0].pid+'&currUserPid='+${currUser.pid}+'&flag='+2+'&isLook='+3,"查看个人客户",true);
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能查看一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
	}
	
	//删除
	function removeItem() {
		var rows = $('#grid').datagrid('getSelections');
		var acctId=rows[0].pid;
		var falg=true;
		var cusStatus =rows[0].value7;
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}// 获取选中的系统用户名 
		var pids = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}
		if(falg){
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
							$.messager.confirm("操作提示","确定删除选择的个人客户信息吗?",
									function(r) {
										if (r) {
											$.post("deleteCusAcct.action",
															{
																pids : pids
															},
															function(ret) {
																window.location.href = "${basePath}customerController/perList.action";
															}, 'json');
										}
									});
						}
			        }
				});
			}
			else{
				$.messager.confirm("操作提示","确定删除选择的个人客户信息吗?",
						function(r) {
							if (r) {
								$.post("deleteCusAcct.action",
												{
													pids : pids
												},
												function(ret) {
													window.location.href = "${basePath}customerController/perList.action";
												}, 'json');
							}
						});
			}
		}
		
	}

	function editBlacklistRefuse(cusStatus) {
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
			if(rows[i].value9==cusStatus || rows[i].value9==5 || rows[i].value9==9 || (rows[i].value9-cusStatus)==4){
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
			title : '新增' + statusName + '信息',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.save();
				}
			}, {
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
	function business() {
		 var row = $('#grid').datagrid('getSelections');
			if (row.length == 1) {
				window.location.href ="${basePath}customerController/listPerBusiness.action?acctId="+row[0].pid;
			}else if (row.length > 1) {
				$.messager.alert("操作提示", "每次只能操作一条数据,请重新选择！", "error");
			} else {
				$.messager.alert("操作提示", "请选择数据", "error");
			}
		
	}

	function addPerLoan() {
		var rows = $('#grid').datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("操作提示", "请选择客户进行新增贷款申请", "error");
			return false;
		}else if(rows.length>1){
			$.messager.alert("操作提示", "只能选择一个客户进行新增贷款申请", "error");
			return false;
		}else{
			var pid = rows[0].pid;
			var cusStatus =rows[0].value7;
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
				});
			}
			else{
				var isWantong = checkUserOrgInfo();
				if(isWantong == 2){//小科
					parent.openNewTab('<%=basePath%>beforeLoanController/addNavigation.action?type=2&acctId='+pid,"赎楼贷款申请",true);
	 		    }else if(isWantong == 1){
	 		    	parent.openNewTab('<%=basePath%>beforeLoanController/addNavigation.action?type=1&acctId='+pid,"赎楼贷款申请(万通)",true);
	 		    }
				
			}
		}
	}
	
	function addMortgageLoan() {
		var rows = $('#grid').datagrid('getSelections');
		if(rows.length==0){
			$.messager.alert("操作提示", "请选择客户进行新增抵押贷款申请", "error");
			return false;
		}else if(rows.length>1){
			$.messager.alert("操作提示", "只能选择一个客户进行新增抵押贷款申请", "error");
			return false;
		}else{
			var pid = rows[0].pid;
			var cusStatus =rows[0].value7;
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
							alert("您不能新增抵押贷款申请，原因是:"+tip);
						}
					}
				});
			}
			else{
				parent.openNewTab('<%=basePath%>projectInfoController/toEditProject.action?type=2&acctId='+pid,"抵押贷款申请",true);
			}
		}
	}
	
	//查询登录用户是否属于万通
	function checkUserOrgInfo(){
		var url = BASE_PATH
		+ "beforeLoanController/checkUserOrgInfo.action";
		var isWantong = 1;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
				isWantong = data;
			}
		});
		
		return isWantong;
	}
	
	function cxportPerCus() {
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
					data:{templateName:'PEROUT'},
					dataType:'json',
					success:function(data){
						if(data==1){				
							window.location.href ="${basePath}customerController/perExcelExportList.action?pids="+pids;
						}else{
							$.messager.alert('操作提示',"个人客户的导出模板不存在，请上传模板后重试！",'error');
						}
					}
				})
	}

	$(function() {
		setCombobox2("certTypeId", "CERT_TYPE", "");
		setCombobox2("sex", "SEX", "");
	});
	//查询 
	function ajaxSearch(){
		var pageNumber=$('#grid').datagrid('options')['pageSize'];
		$('#rows').val(pageNumber);
		$('#searchFrom').form('submit',{
	        success:function(data){
	        	var str = JSON.parse(data);
	           	$('#grid').datagrid('loadData',str);
	           	$('#grid').datagrid('clearChecked');
	       }
	    });
	};
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
	<form action="${basePath}customerController/perListUrl.action" method="post" id="searchFrom">
		<!-- 查询条件 -->
		<div style="padding: 5px">
		<table>
			<tr>
				<td width="80" align="right" height="25">姓 名：</td>
				<td><input name="cusName" class="easyui-textbox" style="width: 150px;" /></td>
				<td width="80" align="right">性 别：</td>
				<td>
					<select name="sexId" id="sex" editable="false" class="easyui-combobox" style="width: 150px;"></select>
				</td>
				<td width="80" align="right" height="25">客户状态：</td>
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
				<td width="80" align="right" height="25">证件类型：</td>
				<td>
					<select name="certTypeId" editable="false" id="certTypeId" class="easyui-combobox" style="width: 150px;"></select>
				</td>
				<td width="80" align="right">证件号码：</td>
				<td colspan="2"><input name="certNumber" class="easyui-textbox" style="width: 200px;" /></td>			
				<input type="hidden" id="rows" name="rows">
				<input type="hidden" id="page" name="page" value='1'>
				<td>
					<shiro:hasAnyRoles name="SEARCH_CUS_PER,ALL_BUSINESS">
						<a href="#" class="easyui-linkbutton" iconCls="icon-search"
							style="margin-left: 120px;"
							onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
					</shiro:hasAnyRoles>
					<a href="#" onclick="javascript:$('#searchFrom').form('reset');"
						iconCls="icon-clear" class="easyui-linkbutton">重置</a>
				</td>
			</tr>
		</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
	<shiro:hasAnyRoles name="ADD_CUS_PER,ALL_BUSINESS"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addItem()">新增</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="UPD_CUS_PER,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">修改</a>
 		</shiro:hasAnyRoles>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="DEL_CUS_PER,ALL_BUSINESS"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
		</shiro:hasAnyRoles> 
		<shiro:hasAnyRoles name="EX_OUTPUT_EXCEL,ALL_BUSINESS"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="cxportPerCus()">导出Excel</a>
 		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="ADD_BACKLIST,ALL_BUSINESS"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="editBlacklistRefuse(2)">加入黑名单</a>
		</shiro:hasAnyRoles> 
		<shiro:hasAnyRoles name="ADD_REFUSE_LIST,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="editBlacklistRefuse(3)">加入拒贷客户</a>
		</shiro:hasAnyRoles> 
		<shiro:hasAnyRoles name="QUY_BUSINESS_HIS,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-wanglai" plain="true" onclick="business()">业务往来</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="ADD_LOAN_REQUEST,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addPerLoan()">新增个人贷款</a>
		</shiro:hasAnyRoles>
		<shiro:hasAnyRoles name="ADD_LOAN_REQUEST,ALL_BUSINESS">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="addMortgageLoan()">新增抵押贷</a>
		</shiro:hasAnyRoles>
	</div>

</div>
<div class="perListDiv" style="height:100%;">
<table id="grid" title="个人客户管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'perListUrl.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    singleSelect: false,
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
			<th data-options="field:'value2',width:'15%',sortable:true" >姓名</th>
			<th data-options="field:'value3',width:'10%',sortable:true">性别</th>
			<th data-options="field:'value4',width:'15%',sortable:true">证件类型</th>
			<th data-options="field:'value5',width:'15%',sortable:true">证件号码</th>
			<th data-options="field:'value13',width:'20%',sortable:true">居住地址</th>
			<th data-options="field:'value7',width:'10%',sortable:true">客户状态</th>
			<th data-options="field:'value8',width:'10%',sortable:true">客户经理</th>
		</tr>
	</thead>
</table>
</div>
</div>
</body>
</html>