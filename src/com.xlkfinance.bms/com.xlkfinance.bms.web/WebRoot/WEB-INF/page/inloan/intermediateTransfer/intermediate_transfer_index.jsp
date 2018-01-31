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
<script type="text/javascript">

//重置按钮
function majaxReset(){
	$('#searchFrom').form('reset')
}
//业务办理状态格式化
function formatterBizApplyHandleStatus(val, row) {
	if (val == 1) {
		return "未申请";
	} else if (val == 2) {
		return "已申请";
	} else if (val == 3) {
		return "已完成";
	} else if (val == 4) {
		return "已归档";
	} else {
		return "未知";
	}
}
// 放款到账状态格式化
function formatterRecStatus(val, row) {
	if (val == 1) {
		return "未到账";
	} else if (val == 2) {
		return "已到账";
	} else {
		return "未知";
	}
}
// 编辑
function editItem(){
	var url = "";// 路径
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
		$.post("${basePath}intermediateTransferController/checkWorkFlowExist.action", {projectId : row[0].projectId,applyStatus : 2},
				function(date){
					if(date > 0 ){
						$.messager.alert("操作提示","此项目已经有中途划转申请了！","info");
					}else{
   		                var url ="${basePath}intermediateTransferController/edit.action?projectId="+row[0].projectId+"&pid="+row[0].pid;
 		                parent.openNewTab(url,"中途划转申请",true);
					}
				});
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}
var intermediateTransferList = {
		// 项目名称format
		formatProjectName:function(value, row, index){
			var va="<a href='javascript:void(0)' onclick = 'formatterProjectOverview.disposeClick("+row.projectId+")'> <font color='blue'>"+row.projectName+"</font></a>"
			return va;
		}
	}
$(document).ready(function() {
    $('#grid').datagrid({  
		  rowStyler:function(index,row){    
		      if (row.isChechan==1){    
		          return 'background-color:#FFAF4C;';    
		      }    
		  } 
    }) ;
});
</script>
<title>贷中管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<!--图标按钮 -->
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="<%=basePath%>intermediateTransferController/intermediateTransferList.action?type=1" method="post" id="searchFrom" name="searchFrom" >
			<!-- 查询条件 -->
			<div style="padding:5px">
				<table>
					<tr>
						<td width="80" align="right" height="28">项目编号：</td>
						<td>
							<input class="easyui-textbox" name="projectNumber" id="projectNumber"/>
						</td>
						<td width="80" align="right"  height="28">项目名称：</td>
						<td>
							<input class="easyui-textbox" name="projectName" id="projectName"/>						
						</td>
						<td width="80" align="right" height="28">申请人：</td>
						<td>
							<input class="easyui-textbox" name="customerName" id="customerName"/>
						</td>
						<td width="80" align="right"  height="28">原业主：</td>
						<td>
							<input class="easyui-textbox" name="oldHome" id="oldHome"/>						
						</td>
					</tr>
                   <tr>
                     <td align="right" >物业名称：</td>
                     <td><input class="easyui-textbox" name="houseName" /></td>
                     <td align="right" >买方姓名：</td>
                     <td><input class="easyui-textbox" name="buyerName" /></td>
                     <td align="right" >卖方姓名：</td>
                     <td><input class="easyui-textbox" name="sellerName" /></td>
                  </tr>
					<tr>
						<td colspan="3">
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="majaxReset()" class="easyui-linkbutton" style="width: 60px;">重置</a>
						</td>
					</tr>				
				</table>
			</div>
		</form>
		
		<!-- 操作按钮 -->
		<div style="padding-bottom:5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editItem()">申请办理</a>
		</div>
		
	</div>
	<div class="dksqListDiv" style="height:100%;">
	<table id="grid" title="中途划转列表" 
		style="height:100%;width: auto;"
		data-options="
		    url: '<%=basePath%>intermediateTransferController/intermediateTransferList.action?type=1',
		    method: 'POST',
		    rownumbers: true,
		    singleSelect: true,
		    pagination: true,
		    sortOrder:'asc',
		    remoteSort:false,
		    toolbar: '#toolbar',
		    idField: 'pid',
		    fitColumns:true">
		<!-- 表头 -->
		<thead data-options="frozen:true">
			 <tr>
				<th data-options="field:'pid',checkbox:true" ></th>
		     	<th data-options="field:'projectName',formatter:intermediateTransferList.formatProjectName,sortable:true" align="center" halign="center"  >项目名称</th>
			 </tr>
	 	</thead>
		<thead><tr>
		    <!-- <th data-options="field:'pid',checkbox:true" ></th>
		    <th data-options="field:'projectName',formatter:intermediateTransferList.formatProjectName,sortable:true" align="center" halign="center"  >项目名称</th> -->
		    <th data-options="field:'projectNumber',sortable:true" align="center" halign="center"  >项目编号</th>
            <th data-options="field:'loanMoney',sortable:true,formatter:formatMoney" align="center" halign="center"  >借款金额</th>
            <th data-options="field:'realLoan',sortable:true,formatter:formatMoney" align="center" halign="center"  >放款金额</th>
		    <th data-options="field:'bizApplyHandleStatus',sortable:true,formatter:formatterBizApplyHandleStatus" align="center" halign="center"  >业务办理状态</th>
		    <th data-options="field:'projectPassDate',sortable:true,formatter:convertDate" align="center" halign="center"  >审批时间</th>
            <th data-options="field:'isChechan',sortable:true,formatter:formatterIsChechan" align="center" halign="center">是否撤单</th>
            <th data-options="field:'productName',sortable:true" align="center" halign="center"  >产品名称</th>
		    <th data-options="field:'customerName',sortable:true" align="center" halign="center"  >申请人</th>
		    <th data-options="field:'oldHome',sortable:true" align="center" halign="center"  >原业主</th>
            <th data-options="field:'houseName',sortable:true" align="center" halign="center">物业名称</th>
            <th data-options="field:'buyerName',sortable:true" align="center" halign="center">买方姓名</th>
            <th data-options="field:'sellerName',sortable:true" align="center" halign="center">卖方姓名</th>   
		    <th data-options="field:'cancelGuaranteeDate',sortable:true,formatter:convertDate" align="center" halign="center"  >解保日期</th>
            </tr></thead>
	</table>
	</div>
	</div>
	</div>
</body>
</html>
