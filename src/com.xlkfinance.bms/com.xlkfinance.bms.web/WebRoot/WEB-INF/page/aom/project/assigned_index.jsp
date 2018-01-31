<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<!-- 业务申请分配的页面 -->
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
	 		var url = "${basePath}beforeLoanController/addNavigation.action?type=2&projectId="+row[0].pid+"&param='projectId':'"+row[0].pid+"','projectName':'"+row[0].projectName+"','WORKFLOW_ID':'businessApplyRequestProcess'";
	 		parent.openNewTab(url,"查看业务申请信息",true);
		}else if(row.length > 1){
			$.messager.alert("操作提示","每次只能查看一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	//重置按钮
	function majaxReset() {
		$('#searchFrom').form('reset');
		$('#productId').combobox('setValue',"-1");
	}
	$(document).ready(function() {
		 var productId = $("#productId").combobox('getValue');//产品ID
			$('#productId').combobox({
				url:'${basePath}productController/getProductLists.action',    
			    valueField:'pid',
			    textField:'productName',
			    onLoadSuccess: function(rec){
			    	if(productId >0){
			    		$('#productId').combobox('setValue',productId);
			    	}else{
			    		$('#productId').combobox('setValue',-1);
			    	}
		       } 
			});
		
	});
	//审批状态
	function formatterApplyStatus(val,row){
		if(val == 1){
			return "待客户经理提交";
		}else if(val == 5){
			return "待风控初审";
		}else if(val == 6){
			return "待风控复审";
		}else if(val == 7){
			return "待风控终审";
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
					 parent.openNewTab("${basePath}beforeLoanController/addNavigation.action?type=2&projectId="+rowData.pid+"&param='projectId':'"+rowData.pid+"','projectName':'"+rowData.projectName+"','WORKFLOW_ID':'businessApplyRequestProcess'","查看业务申请信息",true);
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
	
</script>
<title>业务申请分配管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
<div id="toolbar" class="easyui-panel" style="border-bottom: 0;width:100%;">
	<form action="getProjectList.action" method="POST" id="searchFrom">
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
			</tr>
			<tr>
				<td width="80" align="right">客户名称：</td>
				<td><input name="orgCustomerName" class="easyui-textbox" style="width: 200px;" /></td>
				
				<td width="80" align="right" >订单状态：</td>
				<td>
					<select class="select_style easyui-combobox" style="width: 200px;" editable="false" name="foreclosureStatus">
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
				</tr>
				<tr>
					<td width="100" align="right" height="28">产品名称：</td>
					<td colsapn="2"><input class="easyui-combobox"
						editable="false" style="width: 200px;" name="productId"
						id="productId" /></td>

					<td colspan="2"><a href="#" class="easyui-linkbutton"
						iconCls="icon-search" style="margin-left: 120px;"
						onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a
						href="#" onclick="majaxReset()" iconCls="icon-clear"
						class="easyui-linkbutton">重置</a></td>
				</tr>
			</table>
		</div>
	</form>

	<div style="padding-bottom: 5px">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-view" plain="true" onclick="lookupItem()">查看</a>
		<shiro:hasAnyRoles name="ENASSIGNED_BIZ_PROJECT,ALL_BUSINESS">
		<a href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-add" plain="true" onclick="openAssignedProject()">分配</a>
		</shiro:hasAnyRoles>
	</div>

</div>
<div class="dksqListDiv" style="height:100%;">
<table id="grid" title="业务申请管理" class="easyui-datagrid"
	style="height:100%; width: auto;"
	data-options="
	    url: 'getProjectList.action',
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
	<thead data-options="frozen:true">
		<tr>
			<th data-options="field:'pid',checkbox:true"></th>
			<th data-options="field:'projectName',formatter:formatProjectName,sortable:true" >订单名称</th>
		</tr>
	</thead>    
	<!--
	 表头 -->
	<thead>
		<tr>
			<th data-options="field:'projectNumber',sortable:true" >订单编号</th>
			<th data-options="field:'applyUserName',sortable:true">业务来源</th>
			<th data-options="field:'productName',sortable:true">产品名称</th>
			<th data-options="field:'orgCustomerName',sortable:true">客户名称</th>
			<th data-options="field:'orgCustomerPhone',sortable:true">客户电话</th>
			<th data-options="field:'orgCustomerCard',sortable:true">身份证号</th>
			<th data-options="field:'requestDttm',sortable:true">提单日期</th>
			<th data-options="field:'planLoanMoney',formatter:formatAomMoney">借款金额(元)</th>
			<th data-options="field:'foreclosureStatus',formatter:formatterApplyStatus">审批状态</th>
			<th data-options="field:'isReject',formatter:formatterReject">驳回状态</th>
		</tr>
	</thead>
</table>
</div>
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