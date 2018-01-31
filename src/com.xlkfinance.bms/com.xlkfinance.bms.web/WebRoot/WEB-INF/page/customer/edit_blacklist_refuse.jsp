<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	$('#blacklistInfo').form('submit', {
		url : "saveBlacklistRefuse.action",
		onSubmit : function() {
			return $(this).form('validate');
		},
		success : function(result) {
			alert("保存成功！");
			window.$("#addblacklist").dialog('close');
			window.ajaxSearch('#grid','#searchFrom');
			$('#grid').datagrid('clearChecked');
		}
	}); 
}


</script>
<div id="main_body" style="width:450px;float: left;">
<div id="cus_content" style="width:450px;">
<form id="blacklistInfo" action="customerController/saveBlacklistRefuse.action" method="POST">
 <input type="hidden" name="acctIds" value="${acctIds}"/>
 <input type="hidden" name="listType" value="${cusStatus}"/>
 <input type="hidden" name="status" value="1"/>
<table class="cus_table" style="width:460px;min-width:450px;margin-right:15px;height:155px;">
    <tr>
      <td class="align_right" style="width: 115px;height:100px;">
      	<span class="cus_red">*</span>
      	<c:if test="${cusStatus==2 }">加入黑名单原因</c:if>
      	<c:if test="${cusStatus==3 }">加入拒贷原因</c:if>：
      </td>
      <td>
      	<textarea rows="6" style="width:320px;" class="easyui-validatebox" data-options="required:true" name="refuseReason"></textarea>
      </td>
   </tr>
   <c:if test="${cusStatus==3 }">
   <tr>
      <td class="align_right" >截止时间：</td>
      <td>
      	<input type="text" class="text_style easyui-datebox" name="deadline" />
      </td>
   </tr>
   </c:if>
</table>	
</form>	    
</div>
</div>