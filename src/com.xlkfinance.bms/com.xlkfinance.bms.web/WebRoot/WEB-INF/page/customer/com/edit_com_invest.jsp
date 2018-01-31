<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	   $('#baseInfo').form('submit', {
			url : "saveComInvest.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				window.location.href="listComInvest.action?acctId=${acctId}&comId=${comId}";
			}
		}); 
}
//绑定赋值
$(function(){
	 setCombobox("inv_Way","INVEST_WAY","${cusComInvest.invWay}");//1:id 2:数据字典 3：赋值 
});
</script>
<div id="main_body">
<div id="cus_content" class="pt10">
<form id="baseInfo" action="customerController/saveComInvest.action" method="POST">
  	 <input type="hidden" name="pid" value="${cusComInvest.pid}"/>
  	 <input type="hidden" name="status" value="1"/>
  	 <input type="hidden" name="cusComBase.pid" value="${comId}"/>
      <input type="hidden" name="cusAcct.pid" value="${acctId}"/>
<table  class="cus_table" style="border:none;">
	
  <tr>
	 <td class="align_right" style="width: 250px;">投资对象：</td>
	 <td><input type="text" class="text_style" name="invObj" value="${cusComInvest.invObj}"/></td>
	 <td class="align_right" style="width: 150px;">投资金额（万）：</td>
	 <td>
	 	<input name="invMoney" class="easyui-numberbox" value="${cusComInvest.invMoney}" precision="2" groupSeparator=","  />
	 </td>
  </tr>
    <tr>
	 <td class="align_right">投资方式：</td>
	 <td>
	     <select  class="select_style easyui-combobox" editable="false" name="invWay" id="inv_Way">
	  </select>
	 </td>
	 <td class="align_right">预计收益（万）：</td>
	 <td><input type="text" class="easyui-numberbox" name="preEarn" value="${cusComInvest.preEarn}" precision="2" groupSeparator=","   /></td>
  </tr>
   <tr>
	 <td class="align_right">投资起始日期：</td>
	      <td><input type="text" class="text_style easyui-datebox" editable="false" name="invStartDate" value="${cusComInvest.invStartDate}" /></td>
	 <td class="align_right">实际收益（万）：</td>
	 <td><input type="text" class="easyui-numberbox" name="realEarn" value="${cusComInvest.realEarn}" precision="2" groupSeparator="," /></td>
  </tr>
  <tr>
	 <td class="align_right">投资截止日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="invEndDate" value="${cusComInvest.invEndDate}"/></td>
	 <td class="align_right">上期预计投资收益.万元：</td>
	 <td><input type="text" class="easyui-numberbox" name="priorPreEarn" value="${cusComInvest.priorPreEarn}" precision="2" groupSeparator="," /></td>
  </tr>
  <tr>
	 <td class="align_right">上期实际投资收益.万元：</td>
	 <td><input type="text" class="easyui-numberbox" name="priorRealEarn" value="${cusComInvest.priorRealEarn}" precision="2" groupSeparator="," /></td>
	 <td class="align_right">预计担保期内企业支出的投资金额 （万）：</td>
	 <td><input type="text" class="easyui-numberbox" name="payInvest" value="${cusComInvest.payInvest}" precision="2" groupSeparator="," /></td>
  </tr>
  <tr>
	 <td class="align_right">预计担保期内企业的投资收益金额（万）：</td>
	 <td><input type="text" class="easyui-numberbox" name="invEarn" value="${cusComInvest.invEarn}" precision="2" groupSeparator="," /></td>
	 <td></td>
	 <td></td>
  </tr>
  <tr>
	 <td class="align_right">备注：</td>
	 <td colspan="3" style="height: 80px;"><textarea rows="4" cols="67" name="remark">${cusComInvest.remark}</textarea></td>
  </tr>
</table>
</form>
