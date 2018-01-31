<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>

<div id="main_body" style="width:700px;float: left;" style="height:100%;">
<div id="cus_content" style="width:700px;padding-top:10px;" style="height:100%;">

<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
     <input type="hidden" name="pid" value=""/>
	 <td class="align_right" style="width: 150px;">收款日期：
</td>
	 <td>${bean.paymentDttm}
	 </td>
	 <td class="align_right">收入金额：</td>
	 <td>
	 <span class="paymentAmount"></span>
	 </td>
	 <td class="align_right">已使用余额：</td>
	 <td> <span class="useBalance"></span></td>
  </tr>
   <tr >	
	 <td class="align_right">已对账金额:</td>
	 <td> <span class="reconciliationAmt"></span>
		 
	</td>
	 <td class="align_right">未对账金额：</td>
	 <td > <span class="availableBalance"></span> &nbsp; </td>
	 <td colspan="2" id="reconciliationBtn"><a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="onReconciliation(${bean.pid})">手动对账</a></td>
  </tr>
</table>
</div>
<div class="listFinanceTotaldetailDiv" style="height:100%;">
<table id="detailGrid" title="" class="easyui-datagrid" 
	data-options="		
	    url: '${basePath}financeController/getLoanReconciliationDtlUrl.action?receId=${bean.pid}',
	    method: 'post',
	    rownumbers: true,
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		checkOnSelect:false">
	<!-- 表头 -->
	<thead><tr>
		<th data-options="field:'pid',width:160,hidden:true" id='pid'  ></th>
	    <th data-options="field:'cycleName',width:50"  >对账期数</th>
	    <th data-options="field:'reconciliationDt',width:90"  >对账日期</th>
	    <th data-options="field:'delTypeName',width:90"  >类别</th>
	    <th data-options="field:'reconciliationAmt',width:180,align:'right',formatter:formatMoney" >已对账金额</th>
	    <th data-options="field:'description',width:150" >备注</th>
	</tr>
	</thead>
</table>
</div>    
</div>	

<script type="text/javascript">
//收款对账
function onReconciliation(financeReceivablesId){
	parent.openNewTab("${basePath}financeReconciliationController/handReconciliation.action?loanId=${bean.loanId}&financeReceivablesId="+financeReceivablesId,"收款对账信息录入/查看",true);

}
$(function(){
	var paymentAmount='${bean.paymentAmount}';
	var useBalance='${bean.useBalance}';
	var reconciliationAmt='${bean.reconciliationAmt}';
	var availableBalance='${bean.availableBalance}';
	
	if(paymentAmount!=''){
		$('.paymentAmount').html(accounting.formatMoney(Number(paymentAmount), "", 2, ",", "."));
	}
	if(useBalance!=''){
		$('.useBalance').html(accounting.formatMoney(Number(useBalance), "", 2, ",", "."));
	}
	if(reconciliationAmt!=''){
		$('.reconciliationAmt').html(accounting.formatMoney(Number(reconciliationAmt), "", 2, ",", "."));
	}
	if(availableBalance!=''){
		$('.availableBalance').html(accounting.formatMoney(Number(availableBalance), "", 2, ",", "."));
	}
	if(${bean.availableBalance}>0){
		$("#reconciliationBtn").show();		
	}else{
		$("#reconciliationBtn").hide();
	}
	
	 
});

</script>