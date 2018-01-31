<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<script type="text/javascript">

/*企业财务状况录入导出  */
function exportStatistics() {
	var comId = $("#comId").val();//获得datagrid表的所有行的值
		
	var acctId = $("#acctId").val();

	window.location.href ="${basePath }comFinancialStatus/excelComFinancialStatus.action?comId="+comId+"&acctId="+acctId;
}

	function intoFinancialData() {
		var acctId = $
		{
			acctId
		}
		;
		var comId = $
		{
			comId
		}
		;

		var nowDate = new Date();
		var year = nowDate.getFullYear();

		window.location = '${basePath}customerController/gotoComFinancialStatusByAnnualPage.action?acctId=${acctId}&comId=${comId}&startYear='
				+ year + '&endYear=' + year;
	}
	function onLoadSuccess(data) {
		var merges = [{
			index: 2,
			rowspan: 9
		},{
			index: 12,
			rowspan: 14
		},{
			index: 27,
			rowspan: 5
		}];
		for (var i = 0; i < merges.length; i++) {
			$(this).datagrid('mergeCells', {
				index : merges[i].index,
				field : 'indexCategory',
				rowspan : merges[i].rowspan
			});
		}
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
	<div id="cus_content">
		<jsp:include page="cus_com_tab.jsp">
			<jsp:param value="2" name="tab" />
		</jsp:include>
		<div style="width: 780px; float: left">
			<table class="cus_table2" id="tt">
				<tr>
					<th>填写企业财务状况</th>
				</tr>
				<tr>
					<td>
						<div style="padding: 10px 0;">
						
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="intoFinancialData()">录入财务数据</a>
							<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportStatistics()">导出统计数据</a>
						</div> 
						<input type="hidden" id="comId" name="comId" value="${comId }" />
						<input type="hidden" id="acctId" name="acctId" value="${acctId }" />
						<table class="easyui-datagrid" id="ii" style="width: 780px; height: auto;"
							data-options="
				singleSelect:true,
			    sortOrder:'asc',
			     sortOrder:'asc',
			    remoteSort:false,
			    collapsible:true,
			    onLoadSuccess: onLoadSuccess,
			    url:'${basePath }comFinancialStatus/listComFinancialStatus.action?comId='+${comId }+'&acctId='+${acctId },method:'get'">
							<thead>
								<tr>
									<th
										data-options="field:'indexCategory',width:100,sortable:true">指标类别</th>
									
									<th data-options="field:'indexName',width:150,sortable:true">指标名称</th>
									<th
										data-options="field:'currentAnnualReport',width:120,editor:{type:'numberbox',options:{precision:2}}">当前年度财务报表</th>
									<th
										data-options="field:'lastYearReport',width:120,editor:{type:'numberbox',options:{precision:2}}">前一年年度财务报表</th>
									<th
										data-options="field:'lastTwoYearsReport',width:120,editor:{type:'numberbox',options:{precision:2}}">前二年年度财务报表</th>
									<th
										data-options="field:'lastThreeYearsReport',width:120,editor:{type:'numberbox',options:{precision:2}}">前三年年度财务报表</th>
								</tr>
							</thead>
						</table>

					</td>
				</tr>
			</table>
		</div>
	</div>
</div>
</div>
</div>
</body>