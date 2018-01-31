<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<%@ include file="/config.jsp"%>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<!-- 项目基本信息 -->
<div class=" easyui-panel" title="项目基本信息" data-options="collapsible:true">	
<input type="hidden" name="pid" value=""/>
<table  class="cus_table"  style="width:700px;min-width:720px;margin-bottom: 0px;border:none;">
    <tr>
	 <td class="label_right" style="width: 150px;">项目名称：
</td>
	 <td><a href="javascript:void(0)" onclick="formatterProjectName.disposeClick(${project.pid},'${project.projectNumber}')"><font color="blue">${project.projectName}</font></a>
	 </td>
	 <td class="label_right">项目编号：
</td>
	 <td>${project.projectNumber}</td>
  </tr>
   <tr >	
	 <td class="label_right">业务类别:</td>
	 <td>
	<input name="bankCardType" id="bankCardType" class="easyui-combobox" editable="false" panelHeight="auto"  disabled
	           			 data-options="valueField:'pid',textField:'lookupDesc',value:'${project.businessCatelog}',url:'<%=basePath %>sysLookupController/getSysLookupValByLookType.action?lookupType=LOAN_BUSINESS_CATELOG'" />  </td>
	 
	 <td class="label_right">业务品种:</td>
	 <td> <input name="bankCardType" id="bankCardType" class="easyui-combobox" editable="false" panelHeight="auto"  disabled
	           			 data-options="valueField:'pid',textField:'lookupDesc',value:'${project.businessType}',url:'<%=basePath %>sysLookupController/getSysLookupValByLookType.action?lookupType=LOAN_BUSINESS_TYPE'" />  </td>
	 
  </tr>
  <tr >	
	 <td class="label_right">流程类别:</td>
	 <td>${project.flowCatelogText}</td>
	 <td class="label_right">项目经理:</td>
	 <td>${project.realName}</td>
  </tr>
</table>
 </div>
 <!-- 还款计划表 -->
 <div class="pt10"></div>
<div class="listFinanceTotaldetailDiv" style="height:100%;width: auto;">
<table id="detailGrid" title="项目总流水查询" class="easyui-datagrid" 
	data-options="		
	    url: 'getProjectTotalDetailListUrl.action?loanId=${loanId}',
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
		<th data-options="field:'deleteAble',hidden:true"></th>
		<th data-options="field:'loanId',hidden:true" id='loanId'  ></th>
	    <th data-options="field:'receiveDt',width:80"  >收款日期</th>
	    <th data-options="field:'actualAmt',width:100,align:'right',formatter:formatMoney"  >收入金额</th>
	    <th data-options="field:'useBalAmt',width:100,align:'right',formatter:formatMoney"  >已使用余额</th>
	    <th data-options="field:'reconciliationAmt',width:90,align:'right',formatter:formatMoney"  >已对账金额</th>
	    <th data-options="field:'availableBalance',width:100,align:'right',formatter:formatMoney" >未对账金额</th>
	    <th data-options="field:'remarks',formatter:operateLoad" >操作</th>
	</tr>
	</thead>
</table>
</div>   
</div> 
<script type="text/javascript">
function operateLoad(value, row, index) {
	var str="";
	if(row.pid!=null && row.pid!=0){
		str= '<a href="javascript:void(0)" onclick="detailList('+row.pid+')"><font color="blue">查看对账信息</font></a>';
		// 可删除
		if(!row.deleteAble)
		{
			str += ' | <a href="javascript:void(0)" onclick="antiVerification('+row.pid+','+row.loanId+')"><font color="blue">反核销</font></a>';
		}	
		
	}
	// 可删除
	if(row.deleteAble)
	{
		str += ' | <a href="javascript:void(0)" onclick="deleteDate('+row.pid+')"><font color="blue">删除</font></a>';
	}	
	return str;
}

//查看对账信息
function detailList(receId){
	$('<div id="listFinanceTotaldetailDiv"/>').dialog({
		href : '${basePath}financeController/getLoanReconciliationDtl.action?financeReceivablesId='+receId,
		width : 800,
		height : 600,
		modal : true,
		title : '查看对账信息',
		onLoad : function() {
		}
	});
}

function deleteDate(pid)
{
	
	$.messager.confirm('Confirm','确认要删除选中的财务收款数据?',function(r){
	    if (r){
	    	
	    	$.ajax({
	    		type: "POST",
	            data:"pid="+pid,
	    		url : "deleteLoanInputDate.action",
	    		dataType: "json",
	    	    success: function(data){
	    	    	if(data.header.success)
	    	    	{
	    	    		alert("删除成功");
	    	    	}
	    	    	// 保存失败
	    	    	else
	        		{
	    	    		alert(data.header.msg);
	        		}
	    	    	
	    	    	// 刷新页面
	        		window.location.reload();
	    	    	
	    		},error : function(result){
	    			$.messager.alert("提示","删除失败","error");
	    		}
	    	}); 
	    }
	});
}
function antiVerification(pid,loanId)
{
	
	$.messager.confirm('Confirm','确认要反核销这笔款项吗?',function(r){
	    if (r){
	    	
	    	$.ajax({
	    		type: "POST",
	            data:"inputId="+pid+"&loanId="+loanId,
	    		url : "${basePath}financeReconciliationController/antiVerification.action",
	    		dataType: "json",
	    	    success: function(data){
	    	    	if(data>0)
	    	    	{
	    	    		alert("操作完成");
	    	    	}	// 保存失败
	    	    	else
	        		{
	    	    		alert(data.header.msg);
	        		}
	    	    	
	    	    	// 刷新页面
	        		window.location.reload();
	    	    	
	    		},error : function(result){
	    			$.messager.alert("提示","操作失败","error");
	    		}
	    	}); 
	    }
	});
}
</script>
</body>
