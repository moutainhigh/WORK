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
<title>抵质押物管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
		<!--图标按钮 -->
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="<%=basePath%>mortgageController/getAllProjectAssBase.action" method="post" id="searchFrom" name="searchFrom" >
				<!-- 查询条件 -->
				<div style="padding:5px">
					<table class="beforeloanTable">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" name="projectName" /> </td>
							<td class="label_right">项目编号：</td>
							<td><input class="easyui-textbox" name="projectNumber" /></td>
							<td class="label_right">抵质押物名称：</td>
							<td><input class="easyui-textbox" name="itemName" /> </td>
						</tr>
						<tr>
							<td class="label_right">担保类型：</td>
							<td>
								<select class="easyui-combobox"  name="mortgageGuaranteeType" style="width:150px;" panelHeight="auto" editable="false" >
									<option value=-1  selected="selected" >--请选择--</option>
								    <option value=1 >抵押</option>
									<option value=2 >质押</option>
								</select>
							</td>
							<td class="label_right">抵押物类型：</td>
							<td>
								<input name="mortgageType" class="easyui-combobox" editable="false"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=MORTGAGE_MORTGAGE_TYPE'" />
							</td>
							<td class="label_right">权证编号：</td>
							<td><input class="easyui-textbox" name="warrantsNumber" /> </td>
						</tr>
						<tr>
							<td class="label_right">评估净值范围：</td>
							<td colspan="3">
								<input name="beginMoney" class="easyui-numberbox" style="width:65px;" /> -
								<input name="endMoney" class="easyui-numberbox" style="width:65px;" />
							</td>
							<td class="label_right">项目状态：</td>
							<td>
								<select class="easyui-combobox"  name="status" style="width:150px;" panelHeight="auto" editable="false" >
									<option value=-1  selected="selected" >--请选择--</option>
								    <option value=1 >等待办理登记</option>
									<option value=2 >已办理登记</option>
									<option value=3 >已保管</option>
									<option value=4 >提取申请中</option>
									<option value=5 >已提取</option>
									<option value=6 >已解除</option>
								</select>
								<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
								<a href="#" onclick="javascript:$('#searchFrom').form('reset');" class="easyui-linkbutton" style="width: 60px;">重置</a>
							</td>
						</tr>
					</table>				
				</div>
			</form>
			
			<!-- 操作按钮 -->
			<div style="padding-bottom:5px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true"  onclick="openTransact()">办理抵质押物</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="openSafekeepingDispose()">保管处理</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="openApplyExtraction()">提取申请</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="openRelieve()">解除抵质押物</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" plain="true" onclick="revoke()">撤销抵质押物状态</a>
			</div>
			
		</div>
		<div class="dzywListDiv" style="height:100%">
			<table id="grid" title="抵质押物列表" class="easyui-datagrid" 
				style="height:100%;width: auto;"
				data-options="
				    url: '<%=basePath%>mortgageController/getAllProjectAssBase.action',
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
				    <th data-options="field:'projectName',formatter:projectNameHyperlink" width="15%" align="center" halign="center" >项目名称</th>
				    <th data-options="field:'projectNumber'"  width="15%" align="center" halign="center" >项目编号</th>
				    <th data-options="field:'itemName',formatter:projectAssBaseHyperlink" width="15%" align="center" halign="center" >抵质押物名称</th>
				    <th data-options="field:'ownNameText'" width="10%" align="center" halign="center" >所有权人</th>
				    <th data-options="field:'mortgageGuaranteeTypeText'" width="10%" align="center" halign="center" >担保类型</th>
				    <th data-options="field:'mortgageTypeText'" width="10%" align="center" halign="center" >抵质押物类型</th>
				    <th data-options="field:'status',formatter:formatterMortgageStstus"  width="10%" align="center" halign="center" >抵质押物状态</th>
				</tr></thead>
			</table>
		</div>
		
		<%-- 查看抵质押物详细信息 --%>
		<%@ include file="../common/common_mortgage.jsp" %>		
		<%-- 办理抵质押登记 窗口--%>
		<%@ include file="mortgage_transact.jsp" %>
		<%-- 保管处理 窗口--%>
		<%@ include file="mortgage_safekeepingDispose.jsp" %>
		<%-- 提取申请 窗口--%>
		<%@ include file="mortgage_applyExtraction.jsp" %>
		<%-- 提取处理 窗口--%>
		<%@ include file="mortgage_applyManagetransact.jsp" %>
		<%-- 解除抵质押物 窗口--%>
		<%@ include file="mortgage_relieve.jsp" %>
		<%-- 上传附件资料 --%>
		<%@ include file="mortgage_file.jsp" %>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">

// 初始化页面时,加载抵质押物列表
$(document).ready(function(){
	//  初始化加载datagrid
	ajaxSearchPage('#grid','#searchFrom');
});

</script>
