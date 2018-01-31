<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String tab=request.getParameter("tab");
	request.setAttribute("tab",tab);
%>
<link rel="stylesheet" href="${ctx}/p/xlkfinance/css/customer/css/rewriteStyle.css" type="text/css"></link>
<script type="text/javascript">
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=$("#"+(name+i));
		var classN=i==cursel?"hover":"";
		$("#"+(name+i)).attr("class",classN);
		//con.style.display=i==cursel?"block":"none";
	}
}

/* function loadcus(url){
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
} */
//效验权限，如果是从其他地方链接过来的只能做查看 
/* function validateJuris(){
	var perType ='${perType}';
	if(perType==1){
		$("#cus_content input").attr("disabled","disabled");
		$("#cus_content select").attr("disabled","disabled");
		$("#cus_content textarea").attr("disabled","disabled");
		$("#can_cle").attr("disabled",false);
	}
}
var perTypeUrl=''; 

$(function(){*/
	//var perType ='${perType}';
 $(function(){
	 /*	var perType ='${perType}';

	if(perType==1){
		validateJuris();
		perTypeUrl='&perType=1';
	} */
	$("#tab${tab}").attr("class","hover");
	
	$("#cus_per_tab li").click(function(){
		var acctId="${acctId}";
		if(acctId=="" && $(this).attr("id")!='tab1'){
			alert("客户基础信息未保存，不能保存其他信息")
		}else{
			window.location=$(this).attr("name")/* +perTypeUrl */;
		}
	});
	$("#cus_per_tab li").each(function(){
		
	});
});
</script>
<div class="cus_tab" id="cus_per_tab" style="margin-bottom:5px;">
	<ul>
	  <li id="tab1" name="${basePath }customerController/editPerBase.action?acctId=${acctId}&flag=1&isLook=${isLook}"  onmouseover="setTab('tab',1,8)" onmouseout="setTab('tab',${tab},8)">客户基本信息</li>
	  <li id="tab6" name="${basePath }customerController/listPerPerson.action?acctId=${acctId}&relationType=5&isLook=${isLook}" onmouseover="setTab('tab',6,8)" onmouseout="setTab('tab',${tab},8)" >关系人信息</li>
	  <li id="tab2" name="${basePath }customerController/editPerFamily.action?acctId=${acctId}&isLook=${isLook}" onmouseover="setTab('tab',2,8)" onmouseout="setTab('tab',${tab},8)" >个人家庭信息</li>
	  <li id="tab5" name="${basePath }customerController/listPerCredit.action?acctId=${acctId}&isLook=${isLook}" onmouseover="setTab('tab',5,8)" onmouseout="setTab('tab',${tab},8)" >征信记录</li>
	  <li id="tab3" name="${basePath }customerController/listPerBank.action?acctId=${acctId}&isLook=${isLook}" onmouseover="setTab('tab',3,8)" onmouseout="setTab('tab',${tab},8)" >银行开户</li>
	  <li id="tab4" name="${basePath }customerController/listUnderCom.action?acctId=${acctId}&isLook=${isLook}" onmouseover="setTab('tab',4,8)" onmouseout="setTab('tab',${tab},8)" >旗下公司</li>
	  <li id="tab7" name="${basePath }customerController/listPerEst.action?acctId=${acctId}&isLook=${isLook}" onmouseover="setTab('tab',7,8)" onmouseout="setTab('tab',${tab},8)" >资信评估</li>
	  <li id="tab8" name="${basePath }customerController/listPerBusiness.action?acctId=${acctId}&isLook=${isLook}" onmouseover="setTab('tab',8,8)" onmouseout="setTab('tab',${tab},8)" >业务往来</li>
	</ul>
</div>