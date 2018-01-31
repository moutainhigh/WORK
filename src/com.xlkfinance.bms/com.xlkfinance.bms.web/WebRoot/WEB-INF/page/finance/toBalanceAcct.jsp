<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>
<!-- 多付金额转入客户余额处理-->
<div id="saveAcctProjectBalanceDiv" style="width:600px;float: left;">
<div id="cus_content" style="width:600px;padding-top:10px;">
<form id="baseInfo" action="financeReconciliationController/saveAcctProjectBalance.action" method="POST">
<table  class="cus_table"  style="width:600px;min-width:600px;margin-bottom: 0px;border:none;">
	 <input type="hidden" name="loanId" id="loanId" value="${acct.pid}" />
	 <input type="hidden" name="acctId" id="acctId" value="${acct.acctId}" />
	 <input type="hidden" name="projectId" id="projectId" value="${acct.projectId}" />
	 <input type="hidden" id="receivablesId" name="receivablesId" value="${balance.receivablesId}">
	 <input type="hidden" id="dateVersion" name="dateVersion" value="${balance.dateVersion}">
	 
	
    <tr>
     <input type="hidden" name="pid" value=""/>
	 <td class="align_right" style="width: 150px;"><font color="red">*</font>客户名称：</td>
	 <td colspan="3"><input type="text" class="easyui-textbox" disabled name="acctName" style="width:410px;" value="${acct.acctName}"  /></td>
	
  </tr>
   <tr >
	 <td class="align_right">客户余额：</td>
	 <td  colspan="3"><input type="text" class="easyui-textbox"  readOnly name="acctAmt" value="${acct.acctAmt}" /></td>
	 
  </tr>
  <tr>
	 <td class="align_right">转入金额：</td>
	 <td style="width: 100px;"><input id="balanceAmt" name="balanceAmt"  class="easyui-textbox" editable="false" value="${balance.balanceAmt}" /></td>
	 <td class="align_right" style="width: 100px;"><font color="red">*</font>退还日期:</td>
	 <td ><input type="text" class="easyui-datebox" name="balanceDt" required="true" missingMessage="请选择退还日期!"/> </td>
  </tr>
  <tr>
	 <td class="align_right">处理备注：</td>
	 <td  colspan="3"><input type="text" class="easyui-textbox" name="remark" style="width:410px;" /> 
	 </td>
	 
  </tr>
   
</table>
 </form>	    
</div>
</div>	
<script type="text/javascript">

function save(){
	   $('#baseInfo').form('submit', {
			url : "saveAcctProjectBalance.action",
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				if(result>0){					
					$.messager.alert("系统提示","添加成功","success"); 
					$("#saveAcctProjectBalanceDiv").dialog('close');
				}else if(result==-1){
					$.messager.alert("系统提示","对账信息已改变请从新对账","error"); 
					$("#saveAcctProjectBalanceDiv").dialog('close');
				}else{
					$.messager.alert("系统提示","添加失败","error"); 
				}
				
				//window.location.href='listFinanceAcctManage.action';
			}
		}); 
}
	 
	 
</script>