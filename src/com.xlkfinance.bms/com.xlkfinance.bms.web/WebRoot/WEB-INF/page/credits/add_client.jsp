<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
 
<script>
function loadCreditDiv(action) {
	 $.ajax({
			type: "POST",
	        data:{"projectId":action},
	    	url : '${basePath}creditsCustomerInfoController/getAllCreditsById.action',
			dataType: "json",
		    success: function(data){
		    	var str = eval(data);
		    	if(str.length!=0){
		    		var url="<%=basePath%>beforeLoanController/addNavigation.action?projectId="+str[0].projectId+"&param='refId':'"+str[0].projectId+"','projectName':"+1;
			    	var cAmt=accounting.formatMoney(str[0].creditAmt, "", 2, ",", "."); 	
			    	var eAmt=  accounting.formatMoney(str[0].extractionAmt, "", 2, ",", ".") ;
			    	var fAmt= accounting.formatMoney(str[0].freezeAmt, "", 2, ",", ".") ;
			    	var aAmt= accounting.formatMoney(str[0].availableAmt, "", 2, ",", ".") ;
			    	var result='<div class="s_title" style="padding:0 15px;width:750px;"><input type="hidden" name="" id="oldProject_Id" value="'+str[0].projectId+'"/><span class="left bold">项目名称：</span><a href="javascript:void(0)" class="left" style="color:blue" onClick="toCredits('+str[0].projectId+')">'+str[0].projectName+'</a> <span class="right bold">项目编号：'+str[0].projectNumber+'</span><div class="clr"></div></div>';
			    	result+='<table class="cus_table s_cus_table" style="width:780px;"><tr><th style="text-align:center">额度种类</th><th style="text-align:center">总额度</th><th style="text-align:center">已提取额度</th><th style="text-align:center">冻结额度</th><th style="text-align:center">可用额度</th><th style="text-align:center">期限开始</th><th style="text-align:center">期限结束</th></tr>';
			    	result+='<tr><td>'+(str[0].isHoopVal==null?"-":str[0].isHoopVal)+'</td><td>'+cAmt+'</td><td>'+eAmt+'</td><td> <span id="fAmtSpan" fmtInfo="'+str[0].freezeAmt+'">'+fAmt+'</span></td><td> <span id="clientAmtSpan" amtInfo="'+str[0].availableAmt+'">'+aAmt+'</span></td><td>'+str[0].credtiStartDt+'</td><td>'+str[0].credtiEndDt+'</td></tr>';
			    	result+='</table>';
			    	$('.search_result_info').html(result);
			    	//	alert(projectId);
	//$("#investigationForm input[name='oldProjectId']").val(newProjectId);
		    	}else{
		    		$('.search_result_info').html("授信项目信息加载失败！");
		    	}
		    	//createReconciliationOptionTable(data);
			},error : function(result){
				alert("授信项目信息加载失败！");
			}
		}); 
	}
	function toCredits(projectId){
		var url="<%=basePath%>beforeLoanController/addNavigation.action?projectId="+projectId+"&param='refId':'"+projectId+"','projectName':"+1;
		parent.openNewTab(url,"项目信息查询",true);
	}
</script>
<div class="result_info">

</div>