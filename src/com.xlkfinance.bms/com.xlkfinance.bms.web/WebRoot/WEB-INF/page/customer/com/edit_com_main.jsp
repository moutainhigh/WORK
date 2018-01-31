<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>

<script type="text/javascript">
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=$("#"+(name+i));
		var classN=i==cursel?"hover":"";
		$("#"+(name+i)).attr("class",classN);
		//con.style.display=i==cursel?"block":"none";
	}
}

function loadcus(url){
	jQuery.ajax({
		type:			'GET',
		url:			url,
		cache:			false,
		contentType:	'application/html; charset=utf-8',
		success: function(data){
			$("#cus_com_form").html(data);
		},error: function(data){
			alert("123");
		}
	});
}
	
$(function(){
	loadcus("${basePath }customerController/editComBase.action");
	
	$("#cus_com_tab li").click(function(){
		loadcus($(this).attr("name"));
	});
		
});

	
</script>
<div id="main_body">
<div id="cus_content">
	<%@ include file="cus_com_tab.jsp"%>
	<div id="cus_com_form">
	</div>	
</div>
</div>
