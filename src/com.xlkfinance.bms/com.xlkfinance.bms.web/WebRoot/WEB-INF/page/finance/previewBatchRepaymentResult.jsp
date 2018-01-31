<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

.red{color:red;}
</style>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/batchRepaymentResult.js" charset="utf-8"></script>

<body>
<input type="hidden" value="${loanIds}"/>
<input type="hidden" value="${currentDt}"/>
<input type="hidden" value="${amounts}"/>

<div id="toolbarDiv"><a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" plain="true" onclick="saveBatchRepayment();">对账保存</a></div>
<div class="batchRepaymentDiv">
	<table class="batchRepaymentTable"></table>
</div>
</body>
