<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<style type="text/css">

</style>
<script type="text/javascript">
function save(){
	// 获取所选担保方式
	var warWay = $("#war_Way").combobox("getValues");
	// 判断是否选择担保方式
	if( warWay == "" ){
		$.messager.alert('操作提示',"请选择担保方式!",'error');	
		return;
	}
	// 赋值担保方式
	$("#baseInfo input[name='warWay']").val(warWay);
	   $('#baseInfo').form('submit', {
			url : "saveComDebtRight.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				window.location.href="listDebtRight.action?comId=${comId}&acctId=${acctId}&debtType=${debtType}";
			}
		}); 
}
$(function(){
	//绑定下拉框的值
	setCombobox("repay_Way","REPAY_WAY","${cusComDebt.repayWay}");
	setCombobox("voucherc_Way","VOUCHER_WAY","${cusComDebt.vouchercWay}");
	//绑定下拉框的值
	if("${cusComDebt.warWay}" ==null || "${cusComDebt.warWay}"=="" || "${cusComDebt.warWay}"==-1 ){}
	else{
	setCombobox("war_Way","ASS_WAY","${cusComDebt.warWay}");}
	
	// 动态绑定担保方式 
	var cusComBasePid = '${cusComDebt.pid}';
 		  $.ajax({
				type: "POST",
		        data:{"cusComBasePid":cusComBasePid},
		    	url : 'getGuaranteeTypeBycusComBasePid.action',
				dataType: "json",
			    success: function(data){
			    	var str = eval(data);
			    	// 判断担保方式的个数
			    	if(str.length == 1){
			    		// 如果只有一个
			    		$("#war_Way").combobox("setValue",str[0].guaranteeType);
			    	} else {
				    	var values = "";
				    	for(var i = 0;i<str.length;i++){
				    		if(values == ""){
				    			values = str[i].guaranteeType;
				    		}else{
				    			values += ","+str[i].guaranteeType;
				    		}
				    	}
				    	if(values!=""){
				    		$("#war_Way").combobox("setValues",values.split(','));
				    	}
			    	}
				}
		}); 
});
</script>
<div id="main_body">
<div id="cus_content">
<form id="baseInfo" action="customerController/saveComDebt.action" method="POST">	
<table  class="cus_table" style="border:none;">
  <tr>
      <input type="hidden" name="pid" value="${cusComDebt.pid}"/>
      <input type="hidden" name="debtType" value="${debtType}">
      <input type="hidden" name="cusComBase.pid" value="${comId}"/>
      <input type="hidden" name="cusAcct.pid" value="${acctId}"/>
      <input type="hidden" name="status" value="1">
       <input type="hidden" name="warWay" >
	 <td class="align_right" style="width: 150px;"><span class="cus_red">*</span>债权人：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="debtPerson" value="${cusComDebt.debtPerson}" data-options="required:true"/></td>
	 <td class="align_right" style="width: 200px;"><span class="cus_red">*</span>借款金额（万）：</td>
	 <td><input type="text" class="text_style easyui-validatebox" name="loanMoney" value="${cusComDebt.loanMoney}" data-options="required:true"/></td><p/>
  </tr>
  <tr>
	 <td class="align_right">债权说明：</td>
	 <td><input type="text" class="text_style" name="debtExpl" value="${cusComDebt.debtExpl}"/></td>
	 <td class="align_right">借款起始日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="loanStartDate" value="${cusComDebt.loanStartDate}"/></td><p/>
  </tr>
  <tr>
	 <td class="align_right">借款截止日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="loadEndDate" value="${cusComDebt.loadEndDate}"/></td>
	 <td class="align_right">已还金额(万)：</td>
	 <td><input type="text" class="text_style" name="repay" value="${cusComDebt.repay}"/></td><p/>
  </tr>
   <tr>
	 <td class="align_right">借款余额(万)：</td>
	 <td><input type="text" class="text_style" name="loanSurplu" value="${cusComDebt.loanSurplu}"/></td>
	 <td class="align_right">最后还款日期：</td>
	 <td><input type="text" class="text_style easyui-datebox" editable="false" name="lastRepayDate" value="${cusComDebt.lastRepayDate}"/></td><p/>
  </tr>
  <tr>
	 <td class="align_right">偿还方式：</td>
	 <td>
       <select class="select_style easyui-combobox" editable="false" name="repayWay" id="repay_Way">
				</select>
     </td>
	 <td class="align_right">预计担保期内支付的金额(万元)：</td>
	 <td><input type="text" class="text_style" name="assPayMoney" value="${cusComDebt.assPayMoney}"/></td>
  </tr>
   <tr>
	 <td class="align_right">凭证方式：</td>
     <td>
         <select class="select_style easyui-combobox" editable="false" name="vouchercWay" id="voucherc_Way">
	    </select>
	 </td>
	 <td class="align_right">担保方式：</td>
	<td>						
		 <input id="war_Way" class="easyui-combobox"  editable="false" required="true"  panelHeight="auto" 
			            data-options="valueField:'pid',textField:'lookupDesc',multiple:true,url:BASE_PATH+'sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=ASS_WAY'" />
	</td>
  </tr>
  <tr>
	 <td class="align_right">备注：</td>
	 <td colspan="3" style="height: 80px;" >
        <textarea rows="4" cols="77" name="remark">${cusComDebt.remark}</textarea>
     </td>
  </tr>
</table>
</form>