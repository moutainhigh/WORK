<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>

<div id="main_body" style="width:700px;float: left;">
<div id="cus_content" style="width:700px;padding-top:10px;">

<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
     <input type="hidden" name="pid" value=""/>
	 <td class="align_right" style="width: 150px;">收取单位名称：</td>
	 <td>${financeBank.chargeName}
	 </td>
	 <td class="align_right">银行账号：</td>
	 <td>${financeBank.bankNum}</td>
  </tr>
   <tr >	
	 <td class="align_right">开户名：</td>
	 <td>${financeBank.bankUserName}
		 
	</td>
	 <td class="align_right">查询期间：</td>
	 <td>${searcherPeriodText}</td>
	 
  </tr>
  
</table>
 
</div>
<div class="listFinanceTotaldetailDiv" style="height:100%;">
<table id="detailGrid" title="" class="easyui-datagrid" 
	data-options="		
	    url: 'getFinanceAcctDetailListUrl.action?pid=${pId}&searcherPeriodStart=${searcherPeriodStart}&searcherPeriodEnd=${searcherPeriodEnd}&defaultAmt=${defaultAmt}',
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
	    <th data-options="field:'ftDate',width:80"  >日期</th>
	    <th data-options="field:'initialAmt',width:120,align:'right',formatter:formatMoney"  >期初余额</th>
	    <th data-options="field:'incomeAccount',width:90,align:'right',formatter:formatMoney"  >账户收入</th>
	    <th data-options="field:'accountOut',width:120,align:'right',formatter:formatMoney" >账户支出</th>
	    <th data-options="field:'gapAmt',width:100,align:'right',formatter:formatMoney" >收支差</th>
	    <th data-options="field:'terminalBalance',width:120,align:'right',formatter:formatMoney" >期间余额</th>
	    <th data-options="field:'remarks',width:150" >备注</th>
	</tr>
	</thead>
</table>
</div>    
</div>	
