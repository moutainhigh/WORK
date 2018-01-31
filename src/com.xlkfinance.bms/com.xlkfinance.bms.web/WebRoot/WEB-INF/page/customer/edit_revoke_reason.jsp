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
				alert("撤销成功！");
				window.$("#addblacklist").dialog('close');
				window.loadBlackRefuse();
				$('#grid').datagrid('clearChecked');
			}
		}); 
}

$(function(){
	setCombobox("cus_level","CUS_LV","");
});
</script>
<div id="main_body" style="width:450px;float: left;">
<div id="cus_content" style="width:450px;">
<form id="blacklistInfo" action="customerController/saveBlacklistRefuse.action" method="POST">
 <input type="hidden" name="blackRefuseIds" value="${blacklistRefuseId}"/>
 <input type="hidden" name="acctIds" value="${acctIds}"/>
 <input type="hidden" name="listType" value="${cusStatus}"/>
<table class="cus_table" style="width:460px;min-width:450px;margin-right:15px;height:155px;border:none;">
    <tr>
    	<td class="align_right" style="width: 115px;">
	      	<span class="cus_red">*</span>客户级别：
	     </td>
      <td>
      	<select id="cus_level" class="easyui-combobox" data-options="validType:'selrequired'" name="cusLevel" style="width:150px;" />
      </td>
    </tr>
    <tr>  
      <td class="align_right" style="width: 115px;height:100px;">
      	<span class="cus_red">*</span>
      	<c:if test="${cusStatus==2 }">撤销黑名单原因</c:if>
      	<c:if test="${cusStatus==3 }">撤销拒贷原因</c:if>：
      </td>
      <td>
      	<textarea rows="6" style="width:320px;" class="easyui-validatebox" data-options="required:true" name="revokeReason"></textarea>
      </td>
   </tr>
  
</table>	
<div style="text-align: center;">
<!--       <input type="button" value="保存" onclick="save();"> -->
<!--       <input type="button" value="取消" onclick="cancle();">  -->
   </div>
</form>	    
</div>
</div>