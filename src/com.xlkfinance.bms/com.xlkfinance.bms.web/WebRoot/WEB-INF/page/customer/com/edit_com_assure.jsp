<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	   $('#baseInfo').form('submit', {
			url : "saveComAssure.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				window.location.href="listComAssure.action?acctId=${acctId}&comId=${comId}";
			}
		}); 
}
//绑定赋值
$(function(){
	 setCombobox("ass_Way","ASS_WAY","${cusComAssure.assWay}");//1:id 2:数据字典 3：赋值 
});
</script>
<div id="main_body">
<div id="cus_content">
<form id="baseInfo" action="customerController/saveComAssure.action" method="POST">
  	  <input type="hidden" name="pid" value="${cusComAssure.pid}"/>
  	  <input type="hidden" name="cusComAssure.status" value="1"/>
  	   <input type="hidden" name="cusComBase.pid" value="${comId}"/>
      <input type="hidden" name="cusAcct.pid" value="${acctId}"/>
<table  class="cus_table" style="border:none;">
  <tr>
	 <td class="align_right" style="width: 250px;">担保对象：</td>
	 <td><input type="text" class="text_style" name="assObj" value="${cusComAssure.assObj}"/></td>
	 <td class="align_right" style="width: 150px;">担保方式：</td>
	 <td>
	   <select  class="select_style easyui-combobox" editable="false" name="assWay" id="ass_Way">
	  </select>
	 </td>
  </tr>
  <tr>
	 <td class="align_right" >担保内容：</td>
	 <td><input type="text" class="text_style" name="assContent" value="${cusComAssure.assContent}"/></td>
	 <td class="align_right" >担保金额(万)：</td>
	  <td><input type="text" class="easyui-numberbox" name="assMoney" value="${cusComAssure.assMoney}" precision="2" groupSeparator=","/></td>
  </tr>
  <tr>
	 <td class="align_right" >担保起始日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="assStartDate" value="${cusComAssure.assStartDate}"/></td>
	 <td class="align_right" >担保期限（月）：</td>
	  <td><input type="text" class="text_style" name="assDeadline" value="${cusComAssure.assDeadline}"/></td>
  </tr>
  <tr>
	 <td class="align_right" >担保截止日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="assEndDate" value="${cusComAssure.assEndDate}"/></td>
	 <td class="align_right" >责任比例（%）：</td>
	  <td><input type="text" class="easyui-numberbox" name="dutyRatio" value="${cusComAssure.dutyRatio}" precision="4" /></td>
  </tr>
  <tr>
	 <td class="align_right" >担保解除条件：</td>
	 <td><input type="text" class="text_style" name="assRelCon" value="${cusComAssure.assRelCon}"/></td>
	 <td class="align_right" >责任余额（万）：</td>
	  <td><input type="text" class="easyui-numberbox" name="dutyBalance" value="${cusComAssure.dutyBalance}" precision="2" groupSeparator=","/></td>
  </tr>
  <tr>
	 <td class="align_right" >预计担保期内支付的担保金额（万）：</td>
	 <td>
	     <input type="text" class="easyui-numberbox" name="assPayMoney" value="${cusComAssure.assPayMoney}" precision="2" groupSeparator=","/>
	 </td>
	 <td class="align_right" >被担保方目前运营情况：</td>
	  <td><input type="text" class="text_style" name="assOptn" value="${cusComAssure.assOptn}"/></td>
  </tr>
  <tr>
	 <td class="align_right" >备注：</td>
	 <td colspan="3" style="height: 80px;"><textarea rows="4" cols="75" name="remark">${cusComAssure.remark}</textarea></td>
  </tr>
</table>
</form>