<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="partnerBankList.action" method="post" id="searchFrom">
				<!-- 查询条件 -->
				<div style="padding: 5px">
				<table>
					<tr>
						<td width="100" align="right" height="25">联行行号 ：</td>
						<td>
							<input  class="easyui-textbox" name="bankNo" style="width:150px;" />
						</td>
						<td width="100" align="right" height="25">联行行名  ：</td>
						<td>
							<input  class="easyui-textbox" name="bankName" style="width:150px;" />
						</td>
						<td width="100" align="right" height="25">城市   ：</td>
						<td>
							<input  class="easyui-textbox" name="cityName" style="width:150px;" />
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
			<table id="grid" title="资金银行列表" class="easyui-datagrid"
				style="height:100%; width: auto;"
				data-options="
				    url: '<%=basePath%>partnerBankController/partnerBankList.action',
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
						<th data-options="field:'bankNo',width:80,sortable:true" align="center" halign="center">联行行号</th>
						<th data-options="field:'bankName',width:80,sortable:true" align="center" halign="center">联行行名</th>
						<th data-options="field:'provinceName',width:80,sortable:true" align="center" halign="center">省份</th>
						<th data-options="field:'cityName',width:80,sortable:true" align="center" halign="center">城市 </th>
						<th data-options="field:'bankPhone',width:80,sortable:true" align="center" halign="center">联系电话 </th>
						<th data-options="field:'bankAddr',width:80,sortable:true" align="center" halign="center">联系地址 </th>
					</tr>
				</thead>
			</table>
		</div>
		
	</div>
	
 
</body>

 

