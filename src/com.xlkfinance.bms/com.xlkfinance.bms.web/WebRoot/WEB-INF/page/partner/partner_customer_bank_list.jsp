<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/partner/partner.js"></script> 

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="projectList.action" method="post" id="searchFrom">
				<!-- 查询条件 -->
				<div style="padding: 5px">
				<table>
					<tr>
						<td width="100" align="right" height="25">客户姓名：</td>
						<td>
							<input  class="easyui-textbox" name="customerName" style="width:150px;" />
						</td>
						<td width="80" align="right" height="25">客户身份证：</td>
						<td>
							<input  class="easyui-textbox" name="customerIdCard" style="width:150px;" />
						</td>
						<input type="hidden" id="rows" name="rows">
						<input type="hidden" id="page" name="page" value='1'>
						<td>
							<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a>
							<a href="#" onclick="javascript:$('#searchFrom').form('reset');" iconCls="icon-clear" class="easyui-linkbutton">重置</a>
						</td>
					</tr>
				</table>
				</div>
			</form>
		</div>
		
		<!-- 列表 -->
		<div class="perListDiv" style="height:100%;">
			<table id="grid" title="客户银行开户列表" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
				    url: '<%=basePath%>partnerCustomerBankController/getPartnerCustomerBankList.action',
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
						<th data-options="field:'pid',checkbox:true" align="center" halign="center"></th>
						<th data-options="field:'acctId',hidden:true" ></th>
						<th data-options="field:'partnerNo',formatter:partnerGlobal.partnerMoreFormat,width:80,sortable:true" align="center" halign="center">合作机构</th>
						<th data-options="field:'customerName',width:80,sortable:true" align="center" halign="center">姓名</th>
						<th data-options="field:'mobileNo',width:80,sortable:true" align="center" halign="center">手机号</th>
						<th data-options="field:'customerIdCard',width:80,sortable:true" align="center" halign="center">身份证</th>
						<th data-options="field:'bankCard',width:80,sortable:true" align="center" halign="center">银行卡号</th>
						<th data-options="field:'bankMobileNo',width:80,sortable:true" align="center" halign="center">银行预留手机号</th>
						<th data-options="field:'ip',width:80,sortable:true" align="center" halign="center">IP</th>
						<th data-options="field:'mac',width:80,sortable:true" align="center" halign="center">MAC</th>
						<th data-options="field:'createTime',width:80,sortable:true" align="center" halign="center">创建时间</th>
					</tr>
				</thead>
	</div>
</body>

 

