<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>

<script type="text/javascript">
var flag='${isLook}';
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
			$("#cus_per_form").html(data);
		},error: function(data){
			alert("123");
		}
	});
}
	
$(function(){
	loadcus("${basePath }customerController/editPerBase.action");
	
	$("#cus_per_tab li").click(function(){
		//loadcus($(this).attr("name"));
		window.location=$(this).attr("name");
		//this.layout_center_refreshTab("");
		//parent.layout_center_refreshTab($(this).attr("name"));
	});
	if(flag == '3'){
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('disabled', 'disabled');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').attr('readOnly', 'readOnly');
		$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea').addClass('l-btn-disabled');
		$('.panel-body .easyui-linkbutton:not(.download)').removeAttr('onclick');
	}
});

	
</script>
<body>
<div id="main_body">
<div id="cus_content">
	<%@ include file="cus_per_tab.jsp"%>
	<div id="cus_per_form">
		
	</div>	
</div>
</div>
</body>
