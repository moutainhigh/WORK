<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	String tab=request.getParameter("tab");
	request.setAttribute("tab",tab);
%>
<script type="text/javascript">
function setTab(name,cursel,n){
	for(i=1;i<=n;i++){
		var menu=$("#"+(name+i));
		var classN=i==cursel?"hover":"";
		$("#"+(name+i)).attr("class",classN);
	}
}

//效验权限，如果是从其他地方链接过来的只能做查看 
/* function validateJuris(){
	var comType ='${comType}';
	if(comType==1){
		$("#cus_content input").attr("disabled","disabled");
		$("#cus_content select").attr("disabled","disabled");
		$("#cus_content textarea").attr("disabled","disabled");
		$("#can_cle").attr("disabled",false);
	}
}
var comTypeUrl='';	 */
$(function(){
	/* var comType ='${comType}';
	if(comType==1){
		validateJuris();
		comTypeUrl='&comType=1';
	} */
	$("#tab${tab}").attr("class","hover");
	$("#cus_com_tab li").click(function(){
		var comId="${comId}";
		if( comId=="" &&  $(this).attr("id")!='tab1'){
			alert("企业基础信息未保存，不能保存其他信息!")
		}else{
			window.location=$(this).attr("name")/* +comTypeUrl */;
		}
	});
});
</script>
<div id="cus_com_tab"style="margin-bottom:5px;margin-right:5px;">
	<ul>
	  <li id="tab1" name="${basePath }customerController/editComBases.action?acctId=${acctId}&comId=${comId}&flag=1" onmouseover="setTab('tab',1,14)" onmouseout="setTab('tab',${tab},14)">企业基础信息</li>
	  <li id="tab2" name="${basePath }comFinancialStatus/gotoComFinancialStatus.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',2,14)" onmouseout="setTab('tab',${tab},14)">财务信息</li>
	  <li id="tab3" name="${basePath }customerController/editComStaff.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',3,14)" onmouseout="setTab('tab',${tab},14)">员工结构</li>
	  <li id="tab4" name="${basePath }customerController/listComBank.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',4,14)" onmouseout="setTab('tab',${tab},14)">银行开户</li>
	  <li id="tab5" name="${basePath }customerController/listComContact.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',5,14)" onmouseout="setTab('tab',${tab},14)">企业联系人信息</li>
	  <li id="tab6" name="${basePath }customerController/listComTeam.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',6,14)" onmouseout="setTab('tab',${tab},14)">管理团队</li>
	  <li id="tab7" name="${basePath }customerController/listComDebt.action?acctId=${acctId}&comId=${comId}"  onmouseover="setTab('tab',7,14)" onmouseout="setTab('tab',${tab},14)">债务情况</li>
	  <li id="tab8" name="${basePath }customerController/listDebtRight.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',8,14)" onmouseout="setTab('tab',${tab},14)">债权情况</li>
	  <li id="tab9" name="${basePath }customerController/listComAssure.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',9,14)" onmouseout="setTab('tab',${tab},14)">对外担保</li>
	  <li id="tab10" name="${basePath }customerController/listComInvest.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',10,14)" onmouseout="setTab('tab',${tab},14)">对外投资</li>
	  <li id="tab11" name="${basePath }customerController/listComReward.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',11,14)" onmouseout="setTab('tab',${tab},14)">获奖情况</li>
	 <%--  <li id="tab12" name="${basePath }customerController/listComEst.action?acctId=${acctId}&comId=${comId}&comType=${comType}" onmouseover="setTab('tab',12,14)" onmouseout="setTab('tab',${tab},14)">企业资信评估</li>
	  <li id="tab13" name="${basePath }customerController/listComBusiness.action?acctId=${acctId}&comId=${comId}" onmouseover="setTab('tab',13,14)" onmouseout="setTab('tab',${tab},14)">业务往来</li> --%>
	  <li id="tab14" name="${basePath }customerController/listComPerson.action?acctId=${acctId}&comId=${comId}&relationType=4" onmouseover="setTab('tab',14,14)" onmouseout="setTab('tab',${tab},14)" style="border-bottom:1px solid #95B8E7;">企业控制人信息</li>
	</ul>
</div>