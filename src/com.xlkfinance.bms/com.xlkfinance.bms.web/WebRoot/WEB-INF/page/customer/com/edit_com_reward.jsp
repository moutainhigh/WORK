<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	   $('#baseInfo').form('submit', {
			url : "saveComReward.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				window.location.href="listComReward.action?acctId=${acctId}&comId=${comId}";
			}
		}); 
}

</script>
<div id="main_body">
<div id="cus_content">
<form id="baseInfo" action="customerController/saveComReward.action" method="POST">
<table  class="cus_table" style="border:none;">
  <tr>
  	 <input type="hidden" name="pid" value="${cusComReward.pid}"/>
  	  <input type="hidden" name="cusComBase.pid" value="${comId}"/>
      <input type="hidden" name="cusAcct.pid" value="${acctId}"/> 
  	 <input type="hidden" name="status" value="1"/>
	 <td class="align_right" style="width: 150px;"><span class="cus_red">*</span>证书名称：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="certName" value="${cusComReward.certName}" data-options="required:true"/></td>
	 <td class="align_right" style="width: 150px;">颁发事件：</td>
	 <td><input type="text" class="text_style" name="issEvent" value="${cusComReward.issEvent}"/></td>
  </tr>
  <tr>
	 <td class="align_right"><span class="cus_red">*</span>证书编号：</td>
	 <td>
	 <input type="text" class="text_style  easyui-validatebox" name="certNo"  value="${cusComReward.certNo}" data-options="required:true"/>
	 </td>
	 <td class="align_right">颁发日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" name="issDate" value="${cusComReward.issDate}"/></td>
  </tr>
   <tr>
	 <td class="align_right"><span class="cus_red">*</span>获奖人：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="rewPerson" value="${cusComReward.rewPerson}" data-options="required:true"  />	</td>
	 <td class="align_right"><span class="cus_red">*</span>颁发机构名称：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="issOrgan" value="${cusComReward.issOrgan}" data-options="required:true" /></td>
  </tr>
  <tr>
	 <td class="align_right">备注：</td>
	 <td colspan="3" style="height: 80px;">
	   <textarea rows="4" cols="75" name="remark">${cusComReward.remark}</textarea>
	 </td>
  </tr>
</table>
</form>