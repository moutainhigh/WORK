<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<!-- 项目基本信息 -->

 <!-- 还款计划表 -->
 <div class="pt10"></div>
<div class="listCommissiondetailDiv" style="height:100%;width: auto;">
<table id="detailGrid" title="" class="easyui-datagrid" 
	data-options="		
	    url: 'getListUserCommissionDetailUrl.action?userId=${userId}&date=${date}',
	    method: 'post',
	    rownumbers: true,
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		checkOnSelect:false">
	<!-- 表头 -->
	<thead><tr>
		<th data-options="field:'pid',width:160,hidden:true" id='pid'  ></th>
	    <th data-options="field:'projectName',width:80"  >项目名称</th>
	    <th data-options="field:'commissionAmt',width:100,align:'right',formatter:formatMoney"  >回款金额</th>
	    <th data-options="field:'reconciltaionDt',width:100,"  >对账日期</th>
	   
	</tr>
	</thead>
</table>
</div>   
</div> 
<script type="text/javascript">

</script>
</body>
