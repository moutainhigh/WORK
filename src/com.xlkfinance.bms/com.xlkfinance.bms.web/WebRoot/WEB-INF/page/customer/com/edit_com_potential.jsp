<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	   $('#baseInfo').form('submit', {
			url : "savePotential.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				$("#grid").datagrid('clearChecked');
				window.location.href='searchlistComPotential.action';
				
			}
		}); 
}
$(function(){
	//绑定下拉框的值
	setCombobox("acct_type","CUS_TYPE","${cusAcctPotential.acctType}");
	setCombobox("cert_type","CERT_TYPE","${cusAcctPotential.certType}");
	 setCombobox("acct_Level","CUS_LV","${cusAcctPotential.acctLevel}"); 
});
</script>
<div id="main_body">
<div id="cus_content">
<form id="baseInfo" action="customerController/savePotential.action" method="POST">	
<input type="hidden" name="pid" value="${cusAcctPotential.pid}" />
<table  class="cus_table" style="border:none;">
  <tr>
	 <td class="align_right" style="width: 150px;"><span class="cus_red">*</span>客户名称：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="acctName" value="${cusAcctPotential.acctName}" data-options="required:true"/></td>
	 <td class="align_right" style="width: 200px;"><span class="cus_red">*</span>客户类型：</td>
	 <td><select name="acctType" id="acct_type" editable="false" value="" class="easyui-combobox"  style="width: 180px;"></select></td>
  </tr>
  <tr>
	 <td class="align_right">联系电话：</td>
	 <td><input type="text" class="text_style" name="phone" onkeyup="value=value.replace(/[^\d]/g,'')" value="${cusAcctPotential.phone}"/></td>
	 <td class="align_right">客户等级：</td>
	 <td>
	 <select  class="select_style easyui-combobox" editable="false" id="acct_Level" name="acctLevel" value="">
	  </select>
	 </td>
  </tr>
  <tr>
	 <td class="align_right">证件类型：</td>
	 <td><select id="cert_type" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="certType" style="width:220px;"   /></td>
	 <td class="align_right">证件号码：</td>
	 <td><input type="text" class="text_style" name="certNumber" value="${cusAcctPotential.certNumber}"/></td>
  </tr>
  
  <tr>
	 <td class="align_right">备注：</td>
	 <td colspan="3" style="height: 80px;">
        <textarea rows="4" cols="77" name="remark">${cusAcctPotential.remark}</textarea>
     </td>
  </tr>
</table>
</form>
</div>
</div>
