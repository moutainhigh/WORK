<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
	
<jsp:include page="cus_com_tab.jsp">
<jsp:param value="14" name="tab"/>
</jsp:include>
	<%@ include file="../common/list_person.jsp" %>
</div>
</div>	
</div>
</div>
</body>