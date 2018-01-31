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
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/mortgage/mortgage.js"></script>
<title>抵质押物提取列表</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
		<!--图标按钮 -->
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form method="post" id="searchFrom" name="searchFrom" >
				<!-- 查询条件 -->
				<div style="padding:5px">
					<table class="beforeloanTable">
						<tr>
							<td class="label_right">抵质押物名称：</td>
							<td><input class="easyui-textbox" name="itemName" /> </td>
							<td >
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
								<a href="#" onclick="javascript:$('#searchFrom').form('reset');" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>
						</tr>
					</table>				
				</div>
			</form>
			<!-- 操作按钮 -->
			<div style="padding-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="openApplyManagetransact()">提取处理</a>
			</div>
		</div>
		<div class="dzywListDiv" style="height:100%">
			<table id="grid" title="抵质押物提取列表" class="easyui-datagrid" 
				style="height:100%;width: auto;"
				data-options="
				    url: '<%=basePath%>mortgageController/getAllProjectAssExtraction.action',
				    method: 'post',
				    rownumbers: true,
				    singleSelect: false,
				    pagination: true,
				    toolbar: '#toolbar',
				    idField: 'pid',
				    fitColumns:true">
				<!-- 表头 -->
				<thead><tr>
				    <th data-options="field:'pid',checkbox:true" ></th>
				    <th data-options="field:'itemName'" width="20%" align="center" halign="center" >抵质押物名称</th>
				    <th data-options="field:'applyDttm',formatter:convertDate" width="8%" align="center" halign="center" >提取申请时间</th>
				    <th data-options="field:'applyUserName'"  width="10%" align="center" halign="center" >提取申请人</th>
				    <th data-options="field:'applyRemark'"  width="20%" align="center" halign="center" >提取申请备注</th>
				    <th data-options="field:'handleDttm',formatter:convertDate" width="8%" align="center" halign="center" >提取处理时间</th>
				    <th data-options="field:'handleUserName'" width="10%" align="center" halign="center" >提取处理人</th>
				    <th data-options="field:'handleRemark'" width="20%" align="center" halign="center" >提取处理备注</th>
				</tr></thead>
			</table>
		</div>
		
		<%-- 提取处理 窗口--%>
		<%@ include file="mortgage_applyManagetransact.jsp" %>
		<%-- 上传附件资料 --%>
		<%@ include file="mortgage_file.jsp" %>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
//初始化页面时,加载抵质押物列表
$(document).ready(function(){
	// 设置Jquery post,get同步提交
	$.ajaxSetup({
	    async : false
	});	      
	//  初始化加载datagrid
	ajaxSearchPage('#grid','#searchFrom');
});

</script>
