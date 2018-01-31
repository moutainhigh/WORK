<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">
#cus_content .noneborder td{border:none;}
</style>
<script type="text/javascript">
$(function(){
	 <c:forEach var="estVal" items="${cusEstInfo.cusEstValues}" varStatus="status">
	 	$("#radio${estVal.cusEstOption.pid}").attr("checked","checked");
	 </c:forEach>
});
</script>



<div id="cus_content" style="width:780px;float:left">
	<c:set var="optionIndex" value="0"></c:set>
	<c:forEach var="factor" items="${cusEstTemplate.factors }" varStatus="factorStatus">
	<div id="est_factor" class="pb10">
		<table>
			<tr>
				<td>要素名称：</td>
				<td>${factor.fname }&nbsp;（${fn:length(factor.quotas) }项）</td>
				<td>权重：</td>
				<td>${factor.weight }%</td>
			</tr>
		</table>
	</div>
	<table class="cus_table staff_table ">
		<c:forEach var="quota" items="${factor.quotas}" varStatus="quotaStatus"> 
		   <input type="hidden" name="cusEstValues[${optionIndex }].status" value="1">  	  
		   	  
          <tr >
	 		  <td valign="top" style="width:200px;padding-top:8px;">
	 		    ${factor.fname }
	 		  </td>	
	 		   <td valign="top" style="width:200px;padding-top:8px;">
	 		     ${quota.quotaName }
	 		  </td>
	 		  <td style="width:400px;">
	 		     <table class="noneborder" style="border:none;width:400px;min-width:400px;">
	 		     
	 		     	<c:forEach var="option" items="${quota.options}" varStatus="optionStatus">      	 
	 		     		<tr style="height:25px;">
	 		     			<td style="width:30px;">
	 		     				<input type="radio" id="radio${option.pid}" name="cusEstValues[${optionIndex }].cusEstOption.pid" value="${option.pid}">
	 		     				<c:if test="${not empty cusEstInfo.cusEstValues }">
	 		     				  <c:forEach var="estVal" items="${cusEstInfo.cusEstValues}">
	 		     				  	<c:if test="${option.pid==estVal.cusEstOption.pid }">
	 		     				  	 <input type="hidden" name="cusEstValues[${optionIndex }].pid" value="${estVal.pid }"> 
	 		     				  	 <input type="hidden" name="cusEstValues[${optionIndex }].cusEstInfo.pid" value="${estVal.cusEstInfo.pid }"> 
	 		     				  	</c:if> 
	 		     				  </c:forEach>
	 		     				</c:if>
	 		     				
	 		     			</td>
	 		     			<td style="width:280px;">${option.optionName }</td>
	 		     			<td>${option.score }</td>
	 		     		</tr>
	 		     	</c:forEach>
	 		     </table>
	 		  </td>
          </tr>
         <c:set var="optionIndex" value="${optionIndex+1 }"></c:set>
         </c:forEach>
      	</table>
	</c:forEach>
	    
</div>    
	