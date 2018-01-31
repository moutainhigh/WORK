<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	   $('#baseInfo').form('submit', {
			url : "addCpyContact.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				window.location.href='listComContact.action?acctId=${acctId}&comId=${comId}';
			}
		}); 
}
</script>
<div id="main_body" style="width:700px;float: left;">
<div id="cus_content" style="width:700px;">
<form id="baseInfo" action="customerController/addCpyContact.action" method="POST">
<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
     <input type="hidden" name="cusComBase.status" value="1"/>
     <input type="hidden" name="pid" value="${cusComContact.pid}"/>
     <input type="hidden" name="cusComBase.pid" value="${comId}"/>
      <input type="hidden" name="cusAcct.pid" value="${acctId}"/>
	 <td class="align_right" style="width: 150px;"><span class="cus_red">*</span>联系人姓名：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="cttName" value="${cusComContact.cttName}" data-options="required:true"/></td>
	 <td class="align_right" style="width: 150px;">固定电话：</td>
	 <td><input type="text" class="text_style" onkeyup="value=value.replace(/[^\d]/g,'')" name="fixedPhone" value="${cusComContact.fixedPhone}"/></td>
  </tr>
   <tr >
	 <td class="align_right">移动电话：</td>
	 <td><input type="text" class="text_style" onkeyup="value=value.replace(/[^\d]/g,'')" name="movePhone" value="${cusComContact.movePhone}"/></td>
	 <td class="align_right"> <span class="cus_red">*</span>是否主联系人：</td>
	 <td>
        <input type="radio" name="mainCtt" value="1" <c:if test="${cusComContact.mainCtt==1 || empty cusComContact.mainCtt}">checked </c:if>/>是
   		<input type="radio" name="mainCtt" value="2" <c:if test="${cusComContact.mainCtt==2}">checked </c:if>/>否
	</td>
  </tr>
  <tr>
	 <td class="align_right">职务：</td>
	 <td><input type="text" class="text_style" name="duty" value="${cusComContact.duty}"/></td>
	 <td class="align_right">所属部门：</td>
	 <td><input type="text" class="text_style" name="department" value="${cusComContact.department}" /></td>
  </tr>
  <tr>
	 <td class="align_right">单位办公室电话：</td>
	 <td><input type="text" class="text_style" name="comPhone" value="${cusComContact.comPhone}"/></td>
	 <td class="align_right">家庭地址：</td>
	 <td><input type="text" class="text_style" name="familyAddr" value="${cusComContact.familyAddr}"/></td>
  </tr>
    <tr>
	 <td class="align_right">备注：</td>
	 <td colspan="3" style="height: 80px; width: 450px;"><textarea rows="4" name="remark" cols="70">${cusComContact.remark}</textarea></td>
  </tr>
</table>
 </form>	    
</div>
</div>	 