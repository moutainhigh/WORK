<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	if($('#baseInfo #acc_use').combobox('getText')=='个人贷款放款账户'){
		var rows = $('#grid').datagrid('getRows');
		for(var i=0;i<rows.length;i++){
			if(rows[i].value8=='个人贷款放款账户' && rows[i].pid!=$("#baseInfo input[name='pid']").val()){
				alert('个人贷款放款账户只允许一个!');
				return false;
			}
		}
	}
	   $('#baseInfo').form('submit', {
			url : "saveBankAcct.action",
			onSubmit : function() { 
				return $(this).form('validate');
				},
			success : function(result) {
					window.location.reload();
			}
		}); 
}

function cancle(){
	$("#addbank").dialog('close');
}
$(function(){
	setCombobox("acc_type","${cusType}_ACCT_TYPE","${cusAcctBank.accType}");
	setCombobox("acc_use","${cusType}_ACCT_USE","${cusAcctBank.accUse}");
	setCombobox("bank_acc_cate","OPEN_ACCT_TYPE","${cusAcctBank.bankAccCate}");
	
});
</script>
<div id="main_body" style="width:700px;float: left;">
<div id="cus_content" class="pt15" style="width:700px;">
<form id="baseInfo" action="customerController/saveBankAcct.action" method="POST">
	  <input type="hidden" name="pid" value="${cusAcctBank.pid}"/>
      <input type="hidden" name="cusAcct.pid" value="${acctId}"/>
      <input type="hidden" name="status" value="1"/>
      <c:if test="${cusType=='PER'}">
      	<input type="hidden" name="cusType" value="1"/>
      </c:if>
      <c:if test="${cusType=='COM'}">
      	<input type="hidden" name="cusType" value="2"/>
      </c:if>
<table class="cus_table" style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
      
      <td class="align_right" style="width: 120px;"><span class="cus_red">*</span>银行名称：</td>
      <td><input type="text"  class="text_style easyui-validatebox" name="bankName" value="${cusAcctBank.bankName}" data-options="required:true" /><div style="color: gray; display: inline-block;">示例：中国银行深圳福田支行</div></td>
      <td class="align_right" style="width: 120px;"><span class="cus_red">*</span>账户类型：</td>
      <td>
			<select id="acc_type" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="accType" style="width:150px;"/>
      </td>
   </tr>
   <tr>
<!--       <td class="align_right" ><span class="cus_red">*</span>网点名称：</td> -->
<%--       <td><input type="text"  class="text_style easyui-validatebox" name="branchName"  value="${cusAcctBank.branchName}" data-options="required:true"/></td> --%>
      <td class="align_right" ><span class="cus_red">*</span>开户类型：</td>
      <td>
			<select id="bank_acc_cate" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="bankAccCate" style="width:150px;"  />
      </td>
      <td class="align_right" ><span class="cus_red">*</span>账户用途：</td>
      <td>
			<select id="acc_use" class="easyui-combobox" editable="false" data-options="validType:'selrequired'" name="accUse" style="width:150px;" />
      </td>
   </tr>
<!--    <tr> -->
<!--       <td class="align_right" ><span class="cus_red">*</span>开户地区：</td> -->
<!--       <td> -->
<%-- 			<input type="text"  class="text_style easyui-validatebox" name="accArea"   data-options="required:true" value="${cusAcctBank.accArea}" /> --%>
<!--       </td> -->
      
<!--    </tr> -->
   <tr>
      <td class="align_right" ><span class="cus_red">*</span>开户名称：</td>
      <td><input type="text"  class="text_style easyui-validatebox" name="accName" value="${cusAcctBank.accName}" data-options="required:true"/></td>
      <td class="align_right" >用途说明：</td>
      <td><input type="text"  class="text_style" name="useexplain" value="${cusAcctBank.useexplain}" /></td>
   </tr>
   <tr>
      <td class="align_right" ><span class="cus_red">*</span>卡号：</td>
      <td><input type="text"  class="text_style easyui-validatebox" name="loanCardId" value="${cusAcctBank.loanCardId}" data-options="required:true"/></td>
      <td class="align_right" >备注：</td>
      <td><input type="text"  class="text_style easyui-validatebox" name="remark" value="${cusAcctBank.remark}" /></td>
   </tr>
</table>	
<div style="text-align: center;">
<!--       <input type="button" value="保存" onclick="save();"> -->
<!--       <input type="button" value="取消" onclick="cancle();">  -->
   </div>
</form>	    
</div>
</div>