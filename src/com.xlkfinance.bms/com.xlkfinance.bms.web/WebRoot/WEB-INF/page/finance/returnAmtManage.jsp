<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!-- 退还金额处理页面 -->
<div id="main_body" >
<div id="cus_content" >
<div style="padding-bottom:5px">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="addItem()">新增</a>
	</div>
<table id="returnAmtManageGrid" title="客户业务查询" class="easyui-datagrid" 
	style="width: auto;"
	data-options="
	    url: 'getReturnAmtInfoListUrl.action?loanId='+${loanId},
	    method: 'post',
	    rownumbers: true,
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true">

	<!-- 表头 -->
	<thead><tr>
	    <th data-options="field:'pid',hidden:true" id="pid"></th>
	    <th data-options="field:'loanId',hidden:true" id="loanId"></th>
	    <th data-options="field:'actualRefundAmt',width:180,align:'right',formatter:formatMoney"  >退还金额</th>
	    <th data-options="field:'refundDifferenceAmt',width:200,align:'right',formatter:formatMoney"  >未退还金额</th>	    
	    <th data-options="field:'refundUserName',width:200" >放款人</th>
	    <th data-options="field:'refundDt',width:200" >放款时间</th>
	    <th data-options="field:'remark',width:200" >备注</th>
	</tr></thead>	
</table>

</div>
</div>	
       
<script type="text/javascript">
//跳转到 添加页面
function addItem(){
	   $('<div id="addReturnAmt" />').dialog({
			href : '${basePath}financeReconciliationController/getReturnAmtInfo.action?receivablesId='+${receivablesId}+'&loanId='+${loanId},
			width : 800,
			height : 350,
			modal : true,
			title : '新增多付金额退还处理',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.save();
				}	
				},{
					text : '取消 ',
					iconCls : 'icon-no',
					handler : function() {
						$("#addReturnAmt").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
	   
};
	 
	 
</script>