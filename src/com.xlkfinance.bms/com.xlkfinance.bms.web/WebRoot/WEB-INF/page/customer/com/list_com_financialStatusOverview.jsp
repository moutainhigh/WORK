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
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/customer/customer.js"></script>
<script type="text/javascript">
var BASE_PATH = "${basePath}";

/**
$(document).ready(function() {
	//var startYear = ${startYear};
	//var endYear = ${endYear};
	
	
	var  nowDate = new Date();
    var  nowYear = nowDate.getFullYear();
	$("#startYear").val(nowYear);
	$("#endYear").val(nowYear);
});
**/
	
function searchReportOverviewList(){
	
	var startYear = $("#startYear").val();
	var endYear = $("#endYear").val();
	var comId = $("#comId").val();
	$.post(
			"${basePath }customerController/searchCusComFinanceReportByAnnualFromJsp.action",
			"startYear="+startYear+"&endYear="+endYear+"&comId="+comId,
			function call(data){
				loadReportOverviewList();
				$('#reportOverview_grid').datagrid('load');
			}
		);
	$('#reportOverview_from').form('submit');
}

function resetForm(){
	$('#reportOverview_form').form('reset');
}

</script>


</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
<jsp:include page="cus_com_tab.jsp">
<jsp:param value="2" name="tab"/>
</jsp:include>
	<div style="width:780px;float:left">  
	<div id="reportOverview_toolbar" class="easyui-panel" style="border-bottom: 0;height: 50px">
		<!-- <form action="${basePath }customerController/searcherCusComFinanceReportByAnnualBetween.action?=startYear="+$("#startYear").val()+"&endYear="+$("#startYear").val() method="post" id="reportOverview_form" name="reportOverview_reportOverview_form" > -->
		<form action="#"  method="post" id="reportOverview_form" name="reportOverview_form" >
			<input type="hidden" id="comId" value="${comId}"/>
			<input type="hidden" id="acctId" value="${acctId}"/>
			<!-- 查询条件 -->
			<div style="margin:10px">
				报表年度范围：
					<input id="startYear" class="text_style" name="startYear" type="text" value="${startYear}">&nbsp; 至 &nbsp;
					<input id="endYear" class="text_style" name="endYear" type="text" value="${endYear}">&nbsp;&nbsp;
				<a href="#" class="easyui-linkbutton" iconCls="icon-search" onclick="searchReportOverviewList()">查询</a>
				<%--<a href="#" class="easyui-linkbutton" iconCls="icon-reload" onclick="resetForm()">重置</a> --%>
			</div>
		</form>		
		<!-- 操作按钮 -->
		
	</div>	
	<table id="cusComFinanceReportOverview" title="企业客户财务状况查询/录入" ></table>
		<div id="my_agreportOverview_iconCls="icon-save" >
	    	<table id="reportOverview_grid" class="easyui-datagrid" ></table>
	    </div>
	</div>
</div>
</div>
</div>
</div>
</body>
</html>
