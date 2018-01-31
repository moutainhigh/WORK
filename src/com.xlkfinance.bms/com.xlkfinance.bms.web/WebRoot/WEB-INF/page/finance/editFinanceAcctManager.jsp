<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>

<div id="main_body" style="width:700px;float: left;">
<div id="cus_content" style="width:700px;padding-top:10px;">
<form id="baseInfo" action="financeController/addFinanceAcctManager.action" method="POST">
<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
     <input type="hidden" name="pid" value=""/>
	 <td class="align_right" style="width: 150px;"><font color="red">*</font>收取单位名称：</td>
	 <td><input type="text" class="easyui-textbox" name="chargeName"  required="true" missingMessage="请输入收取单位名称!" /></td>
	 <td class="align_right" style="width: 120px;"><font color="red">*</font>账号类型：</td>
	 <td> 
	 <input name="bankCardType" class="easyui-combobox" required="true" missingMessage="请选择账号类型!"  editable="false" panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',url:'<%=basePath %>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=CUS_TYPE'" />

	 </td>
  </tr>
   <tr >
	 <td class="align_right"><font color="red">*</font>银行账号：</td>
	 <td><input type="text" class="easyui-textbox" name="bankNum" required="true" missingMessage="请输入银行账号!"/></td>
	 <td class="align_right">初始金额：</td>
	 <td>
	    <input type="text" readonly="true" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" id="defaultAmtId" name="defaultAmt" />
		 <a href="#" onclick="updateMoney();"><font color="blue">修改初始金额</font></a>
	</td>
  </tr>
  <tr>
	 <td class="align_right"><font color="red">*</font>开户银行：</td>
	 <td><input name="bank" class="easyui-combobox" editable="false" required="true" missingMessage="请选择开户银行!" panelHeight="auto"  
	           			 data-options="valueField:'pid',textField:'lookupDesc',url:'<%=basePath %>sysLookupController/getSysLookupValByLookTypeTwo.action?lookupType=BANK_TYPE'" /></td>
	 <td class="align_right"><font color="red">*</font>开户名：</td>
	 <td><input type="text" class="easyui-textbox" name="bankUserName" required="true" missingMessage="请输入开户名!"/> </td>
  </tr>
  <tr>
	 <td class="align_right"><font color="red">*</font>是否启用：</td>
	 <td >
	  <input type="radio" class="easyui-radiobox" name="isOpen" value="1" checked  required="true" missingMessage="请选择是否启用!"/>是
   	  <input type="radio" class="easyui-radiobox" name="isOpen" value="2" required="true" missingMessage="请选择是否启用!"/>否
	 </td>
	 <td class="align_right"><font color="red">*</font>表示顺序</td>
	 <td >
	  <input  type="text" class="easyui-textbox"  name="showSeq" id="showSeq" required="true" missingMessage="请输入表示顺序!"/> 
	 </td>
  </tr>
    <tr>
	 <td class="align_right">备注：</td>
	 <td colspan="3" style="height: 80px; width: 450px;"><textarea rows="4" name="remark"  cols="70"></textarea></td>
  </tr>
</table>
 </form>	    
</div>
</div>	

<div id="updateMoneyDiv" class="easyui-dialog" fitColumns="true"  title="修改初始金额"
	     style="width:350px; height:200px;" closed="true" modal="true"><br/>
	 <label>&nbsp;当前账户余额：</label><input type="text"   readonly="true" class="easyui-numberbox"  id="thisMoney" data-options="min:0,precision:2,groupSeparator:','" name="defaultAmt" value=""/><br/><br/>
	 <label>修改后账户余额：</label><input type="text" class="easyui-numberbox" data-options="min:0,precision:2,groupSeparator:','" name="defaultAmt" id="updateMoney" required="true" missingMessage="请输入修改后的金额!" value=""/><br/><br/>
</div> 

<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeAcctManagement.js" charset="utf-8"></script>
<script type="text/javascript">
//绑定赋值
$(function(){

		$("#baseInfo").form("load","editFinanceAcctManageUrl.action?pid=${pid}");
		 
	});


</script>