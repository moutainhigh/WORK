<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
var flag='${isLook}';
$(function(){
	if(flag == '3'){
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled', 'disabled');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly', 'readOnly');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
		$('.panel-body .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
});
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
	<jsp:include page="cus_per_tab.jsp">
	<jsp:param value="7" name="tab"/>
	</jsp:include>
	<%@ include file="../common/list_est.jsp" %>
</div>
</div>	
</div>
</div>
</body>