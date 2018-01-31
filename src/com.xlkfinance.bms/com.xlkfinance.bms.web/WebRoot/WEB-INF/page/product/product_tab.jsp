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

</script>
<div class="cus_tab" id="cus_per_tab" style="margin-bottom:5px;">
	<ul>
	  <li id="tab1" name="${basePath }productController/editProduct.action?productId=${product.pid}"  onmouseover="setTab('tab',1,8)" onmouseout="setTab('tab',${tab},8)">产品信息</li>
	</ul>
</div>