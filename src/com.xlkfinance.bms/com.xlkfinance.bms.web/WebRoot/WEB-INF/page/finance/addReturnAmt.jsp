<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>
<!-- 退还金额添加 -->
<div id="addReturnAmt" style="width:500px;padding-top:10px;">
<form id="addReturnform" action="" method="POST">
<input type="hidden" name="receivablesId" value="${receivablesId}">
<input type="hidden" name="loanId" value="${loanId}">
<input type="hidden" name="dateVersion" value="${balance.dateVersion}">
<input type="hidden" name="refundBankId" value="${cusAcctBankView.bank}">
<input type="hidden" name="projectId" value="${acct.projectId}">
<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
     <input type="hidden" name="pid" value=""/>
	 <td class="label_right" style="width: 150px;">应退金额：</td>
	 <td><input type="text" class="easyui-numberbox" data-options="min:0,precision:2" name="payableRefundAmt" id="payableRefundAmt" readonly value="${balance.balanceAmt}"/></td>
	 <td class="label_right" style="width: 120px;"><font color="red">*</font>退还金额：</td>
	 <td> 
	 <input name="actualRefundAmt" id="actualRefundAmt" class="easyui-numberbox" data-options="min:0,precision:2,onChange:checkAmt" required="true" missingMessage="请输入退还金额!"    />
	 </td>
  </tr>
   <tr >
	 <td class="label_right"><font color="red">*</font>退还日期：</td>
	 <td><input type="text" class="easyui-datebox" name="refundDt" id="refundDt" required="true" missingMessage="请输入银行账号!"/></td>
	 <td class="label_right">差额：</td>
	 <td>
	    <input type="text"  class="easyui-numberbox" data-options="min:0,precision:2" id="refundDifferenceAmt" name="refundDifferenceAmt" readonly value="${refundDifferenceAmt}" />
	</td>
  </tr>
  <tr>
	 <td class="label_right">处理备注：</td>
	 <td colspan="3"><input name="remark" id="remark" class="easyui-textbox" style="width:492px;"   /></td>
  </tr>
  <tr>
	 <td class="label_right">退还账号：</td>
	 <td ><input name="loanCardId" id="loanCardId" class="easyui-textbox" editable="false"   disabled value="${cusAcctBankView.loanCardId }"/></td>
	 <td class="label_right">开户名称：</td>
	 <td >
	  <input  type="text" class="easyui-textbox"  name="accName" id="accName" disabled value="${cusAcctBankView.accName }"/> 
	 </td>
  </tr>
    <tr>
	 <td class="label_right">账户类型：</td>
	 <td ><input name="accType" id="accType" class="easyui-textbox" editable="false"   disabled value="${cusAcctBankView.accType }"/></td>
	 <td class="label_right">银行开户类别：</td>
	 <td ><input  type="text" class="easyui-textbox"  name="bankAccCate" id="bankAccCate" disabled value="${cusAcctBankView.bankAccCate}"/> 
	 </td>
  </tr>       
  <tr>
	 <td class="label_right">银行名称：</td>
	 <td ><input name="bankName" class="easyui-textbox" editable="false" id="bankName"   value="${cusAcctBankView.bankName}"/></td>
	 <td class="label_right">网点名称：</td>
	 <td ><input  type="text" class="easyui-textbox"  name="branchName" id="branchName" disabled value="${cusAcctBankView.branchName}"/> 
	 </td>
  </tr>
  <tr>
	 <td class="label_right">开户地区：</td>
	 <td colspan="3"><input name="accArea" id="accArea" class="easyui-textbox" editable="false"  disabled value="${cusAcctBankView.accArea}" /></td>
  </tr>
  <tr>
	 <td class="label_right">银行备注：</td>
	 <td colspan="3"><textarea rows="3" cols="78" disabled name="bankRemark" id="bankRemark">${cusAcctBankView.remark}</textarea> </td> 
  </tr>
</table>
 </form>	    
</div>
       
<script type="text/javascript">
function checkAmt(){	
	var payableRefundAmt=$("#payableRefundAmt").val();
	var actualRefundAmt=$("#actualRefundAmt").val();
	var refundDifferenceAmt=payableRefundAmt-actualRefundAmt;
	$("#refundDifferenceAmt").textbox('setValue',refundDifferenceAmt);
	
}
function save(){
	var payableRefundAmt=Number($("#payableRefundAmt").val());
	var actualRefundAmt=Number($("#actualRefundAmt").val());
	
	if(payableRefundAmt<=0 || actualRefundAmt<=0){
		$.messager.alert("系统提示","退还金额与应退金额必须大于0","error"); 
		return;
	}else{
		if(actualRefundAmt>payableRefundAmt){
			$.messager.alert("系统提示","退还金额不可以大于应退金额","error"); 
			return;
		}
		$('#addReturnform').form('submit', {
			url : "saveReturnAmt.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				if(result>0){					
					$.messager.alert("系统提示","添加成功","success"); 
				}else if(result==-1){
					$.messager.alert("系统提示","对账信息已改变请从新对账","error"); 
				}else{
					$.messager.alert("系统提示","添加失败","error"); 
				}
				$('#returnAmtManageGrid').datagrid('load');
				$("#addReturnAmt").dialog('close');
				
			}
		}); 
	}
	   
}
	 
	 
</script>