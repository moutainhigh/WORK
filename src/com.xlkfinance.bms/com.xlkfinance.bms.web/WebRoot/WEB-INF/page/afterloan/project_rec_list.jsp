<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>

<div id="main_body" style="width:700px;float: left;height:100%;" >
<div id="cus_content" >

<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
     <input type="hidden" name="pid" value=""/>
	 <td class="align_right" style="width: 150px;">项目名称：
</td>
	 <td><a href="javascript:void(0)" onclick="disposeClick('${project.pid}')"><font color="blue">${project.projectName}</font></a>
	 </td>
	 <td class="align_right">项目编号：
</td>
	 <td>${project.projectNumber}</td>
  </tr>
   
</table>
 
</div>
<div class="projectReconciliationListDiv" style="height:100%;">
<table id="detailGrid" title="" class="easyui-datagrid" 
	style="height:100%;width: auto;"
	data-options="		
	    url: '${basePath}badDebtController/getProjectReconciliationList.action?projectId=${projectId}&loanId=${loanId}&realtimeIds=${realtimeIds}&recCycleNums=${recCycleNums}',
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
	    <th data-options="field:'reconciliationDt',width:150"  >对账日期</th>
	    <th data-options="field:'delTypeName',width:90"  >类别</th>
	    <th data-options="field:'reconciliationAmt',width:180,align:'right',formatter:formatMoney" >已对账金额</th>
	    <th data-options="field:'description',width:60" >备注</th>
	</tr>
    
	</thead>
</table>
</div>    
</div>	

