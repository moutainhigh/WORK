<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>

<div id="main_body" style="width:500px;float: left;">
<div id="cus_content" style="width:500px;padding-top:10px;">
<form id="baseInfo" action="" method="POST">
<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
 <input type="hidden" name="bankAcctId" value="${financeBank.pid}"/>
 <input type="hidden" name="pid" value="${financeTransactionView.pid}"/>
  <input type="hidden" name="regCategory" value="4"/>
    <tr>
		<td class="label_right">账户类型:</td>
		<td colspan="3">${financeBank.bankCardTypeText}</td>			
	</tr>
    <tr>    
	 <td class="label_right">收取单位名称:</td>
		<td >${financeBank.chargeName}</td>
		<td class="label_right">银行账号:</td>
		<td colspan="2">
			${financeBank.bankNum}
	</td>
  </tr>
   <tr >
	 <td class="align_right"><font color="red">*</font>支出日期：</td>
	 <td>
	    <input type="text" class="easyui-datebox"  id="ftDt" name="ftDt" required="true" missingMessage="请选择支出日期!"  style="width:180px;" value="${financeTransactionView.ftDt }" data-options="currentText:'Today'"/> 
	 <td class="align_right"><font color="red">*</font>支出金额：</td>
	 <td><input name="ftAmt"   required="true" missingMessage="请输入支出金额!" panelHeight="auto"  
	           			 class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','"  style="width:250px;" value="${financeTransactionView.ftAmt }"/></td>
	</td>
  </tr>
  <tr>
	
	 <td class="align_right"><font color="red">*</font>支出类型：</td>
	 <td> <input name="usedType" id="usedType2"  class="easyui-combobox" editable="false"  style="width: 180px;"  panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',value:'${financeTransactionView.usedType!=0?financeTransactionView.usedType:-1}',url:'<%=basePath %>sysLookupController/getSysLookupValByLookType.action?lookupType=USED_TYPE'" />
		</td>
   <td class="align_right">支出备注：</td>
	 <td >
	  <input class="easyui-textbox" name="remark"  style="width:250px;"  value="${financeTransactionView.remark}"/>
	 </td>
  </tr>
    
</table>
 </form>	    
</div>
</div>	


<script type="text/javascript">

</script>