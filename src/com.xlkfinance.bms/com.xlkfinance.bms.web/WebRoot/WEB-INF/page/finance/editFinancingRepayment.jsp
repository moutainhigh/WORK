<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>

<div id="main_body" style="width:300px;float: left;">
<div id="cus_content" style="width:300px;padding-top:10px;">
<form id="baseInfo" action="" method="POST">
<table  class="cus_table"  style="width:500px;min-width:520px;margin-bottom: 0px;border:none;">
 <input type="hidden" name="bankAcctId" value="${financeBank.pid}"/>
 <input type="hidden" name="pid" value="${financeTransactionView.pid}"/>
 <input type="hidden" name="regCategory" value="${financeTransactionView.regCategory!=null?financeTransactionView.regCategory:1}"/>
<!--  <input id="unFtAmtLoan" name="unFtAmtLoan" type="hidden" class="easyui-textbox" value="${unFtAmtLoan}">
 <input id="repayType" name="repayType" type="hidden" class="easyui-textbox" value="${repayType}"> -->
    <tr>
		<td class="label_right">账户类型：</td>
		<td colspan="3">${financeBank.bankCardTypeText}</td>			
	</tr>
    <tr>    
	    <td class="label_right">收取单位名称：</td>
		<td >${financeBank.chargeName}</td>
  </tr>
  <tr>
	  <td class="label_right">银行账号：</td>
	  <td colspan="2">${financeBank.bankNum}</td>
  </tr>
   <tr >
	 <td class="align_right"><font color="red">*</font>发生日期：</td>
	 <td>
	    <input  class="easyui-datebox" style="width:180px;" id="ftDt" name="ftDt"  value="${financeTransactionView.ftDt}" /> 
	 
  </tr>
  <tr>
  <td class="align_right"><font color="red">*</font>发生金额：</td>
	 <td>
	 
	 <input name="ftAmt" id="ftAmt"   required="true" missingMessage="请输入发生金额!" panelHeight="auto"  
	           			 class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"  style="width:180px;" /></td>
	</td>
  </tr>
	<tr >
	 <td class="align_right"><font color="red">*</font>支出类型：</td>
	 <td>
	 <select id="cc" class="easyui-combobox" name="repayType" style="width:200px;"  data-options=" value:'${financeTransactionView.repayType}'" >   
	    <option value="1">融资还款</option>   
	    <option value="2">借款支息</option>    
	</select> 
	    
  </tr>
  <tr >
   <td class="align_right">支出备注：</td>
	 <td >
	  <input class="easyui-textbox" name="remark"  style="width:250px;"  value=""/>
	 </td>
  </tr>
    
</table>
 </form>	    
</div>
</div>	


<script type="text/javascript">
var repayType=${repayType};
var unFtAmtLoan=${unFtAmtLoan};
</script>