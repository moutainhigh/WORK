<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
function loadCreditDiv(action) {
	$.ajax({
		type:			'GET',
		url:			'findCredit.action?limitId=${limitId}',
		cache:			false,
		dataType:		'html',
		success: function(data){
			$('#creditPro').html(data);
		},
		error: function(data){
			$('#creditPro').html("授信项目加载失败");
		}
	});
}

function cancelFreeze(){
	window.location.href='${basePath}creditsController/listCredits.action';
}

function saveFreeze(){
	$('#freezeForm').form('submit', {
		url : "saveFreeze.action",
		onSubmit : function() {
			checkFreezeForm();
			return $(this).form('validate'); 
		},
		success : function(result) {
			alert("保存成功！");
			window.location='${basePath }creditsController/listCredits.action';
		},error : function(result){
			alert("保存失败！");
		}
	}); 
}

function checkFreezeForm(){
	var requestAmt=$("#requestAmt").val();
	var usableLimit=$("#usableLimit").val();
	var freezeLimit=$("#freezeLimit").val();
	var reqStatus='${requestStatus}';
	if(reqStatus=='4'){
		usableLimit=freezeLimit;
	}
	if(requestAmt!='' && usableLimit !=''){
		if(parseFloat(requestAmt) > parseFloat(usableLimit)){
			if(reqStatus=='4'){
				$.messager.alert("操作提示","冻结金额不能大于可用额度！","error");
			}else if(reqStatus=='3'){
				$.messager.alert("操作提示","解冻金额不能大于冻结额度！","error");
			}	
			$("#requestAmt").focus();
			return false;
		}
	}
	var amt=$("#amt").val();
	if(amt !=undefined && amt!=''){
		if(amt>requestAmt){
			$.messager.alert("操作提示","审批冻结/解冻额度不能大于申请冻结/解冻额度！","error");
			$("#amt").focus();
			return false;
		}
	}
	
}

$(function(){
	loadCreditDiv();
})
</script>
<div id="main_body">
<div id="cus_content">
<div  style="width:780px;float:left">
	<div id="creditPro">
	</div>
	
	<div id="req_freeze">
	<form id="freezeForm" action="saveFreeze.action" method="POST">
		<input type="hidden" name="pid" value="${creditLimitRecord.pid}" />
		<input type="hidden" name="status" value="1" />
		<input type="hidden" name="limitId" value="${limitId}" />
		<input type="hidden" name="creditUseType" value="${empty creditLimitRecord ?limitStatus:creditLimitRecord.creditUseType}" />
    	<table class="cus_table2">
		</table>
	</form>
	</div>
 </div>	    
</div>
</div>	