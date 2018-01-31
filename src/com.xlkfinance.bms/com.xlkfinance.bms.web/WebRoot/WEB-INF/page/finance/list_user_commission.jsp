<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/finance/financeBusiness.js" charset="utf-8"></script>
<style type="text/css">
</style>
<<script type="text/javascript">
<!--

//-->
function operateLoadcommisstion(value, row, index) {
	
	return '<a href="javascript:void(0)" onclick="findCommissionDetail('
			+ row.userId + ')"><font color="blue">查看明细</font></a>'				;
}

//查询 
function ajaxSearchCommission() {
	
	var data = {
			realName : $("#realName").val(),
			memberId : $("#memberId").val(),
			date : $("#date").val()
		
	};

	$('#cusCommissionGrid').datagrid('load', data);
	clearSelectRows('#cusCommissionGrid');
}
//查看对账流水
function findCommissionDetail(pid) {
	var date =$("#date").val()
	if (loanId != 0 || pid != 0) {
		$('<div id="listCommissiondetailDiv"/>')
				.dialog(
						{
							href : BASE_PATH+'financeController/getListUserCommissionDetail.action?userId='
									+ pid +'&date='+date,
							width : 600,
							height : 500,
							modal : true,
							title : '提成详细信息',
							onLoad : function() {
							}
						});

	}
}
</script>
<!-- 提成列表 -->
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
<div id="scroll-bar-div">
	<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
		<form action="findListUserCommissionUrl.action" method="post"
			id="cusCommissionForm">
			<!-- 查询条件 -->
			<div style="padding: 5px">
				<table width="90%">
					
					
					
					<tr>
						<td class="label_right">姓名:</td>
						<td><input name="realName" id="realName"
							class="easyui-textbox" style="width: 200px;" /> </td>

						<td class="label_right">工号:</td>
						<td><input
							name="memberId" id="memberId" class="easyui-textbox"
							style="width: 200px;" />
						</td>
						<td class="label_right">月份:</td>
						<td><input
							name="date" id="date" class="easyui-textbox"
							style="width: 200px;" />
						</td>
						<td><a href="#" class="easyui-linkbutton"
							iconCls="icon-search" onclick="ajaxSearchCommission()">查询</a> <a href="#"
							onclick="majaxReset('#cusCommissionForm')"
							class="easyui-linkbutton" style="width: 60px;">重置</a></td>
					</tr>
					<tr>
						<td class="red" colspan="5">提示！提成金额： 回款金额的8% ，实发金额：提成金额的75%</td>
						
					</tr>

				</table>

			</div>
		</form>
 
	</div>

	<div class="cusCommissionGridDiv" style="height: 100%;">
		<table id="cusCommissionGrid" title="员工提成列表" class="easyui-datagrid"
			style="height: 100%; width: auto;"
			data-options="
	    url: 'findListUserCommissionUrl.action',
	    method: 'post',
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    showFooter:true,
	    ctrlSelect:true,
		striped:true,
		onLoadSuccess:function(data){
				clearSelectRows('#cusBusinessGrid');
			}">

			<!-- 表头 -->
			<thead>
				<tr>
					<th data-options="field:'userId',hidden:true" id="userId"></th>
					<th data-options="field:'loanId',hidden:true" id="loanId"></th>
					<th
						data-options="field:'realName',width:180,">姓名</th>
					<th data-options="field:'memberId',width:90">工号</th>
					<th data-options="field:'projectAmt',width:55,align:'right',formatter:formatMoney">回款金额</th>
					<th data-options="field:'commissionAmt',width:100,align:'right',formatter:formatMoney"  >提成金额</th>
					<th data-options="field:'realCommissionAmt',width:100,align:'right',formatter:formatMoney"  >实发金额</th>
					<th data-options="field:'yyy',width:320,formatter:operateLoadcommisstion">操作</th>

				</tr>
			</thead>


		</table>
	</div>
</div>
</div>
</body>
