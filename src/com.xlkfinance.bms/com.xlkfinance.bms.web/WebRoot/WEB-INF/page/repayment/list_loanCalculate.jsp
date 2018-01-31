<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<script type="text/javascript">
	var loanCalculate = {
		// 更新行背景颜色
		updateRowStyler:function(index,rows){
	    	var color = "";
			// 已对账数据背景颜色设置
			if(rows.isReconciliation == 1 || rows.isReconciliation == 4 ){
				color = 'background-color:#C0C0C0;';   
			}
			
			
			if(rows.operType == 7){// 提前还款
				color = 'background-color:#ADFFFF;';   
			}else if(rows.operType == 1){// 挪用罚息
				color = 'background-color:#6EC0FF;';   
			}else if(rows.operType == 2){// 违约罚金
				color = 'background-color:#FFB8FC;';   
			}else if(rows.operType == 3){// 坏账
				color = 'background-color:#FF7775;';   
			}
			return color;
			 
		},// 合并单元格
		colspan:function (){
			// 获取当前列表数据
			var rows = $("#loanCaculateGrid").datagrid("getRows");
			// 合并单元格
			$('#loanCaculateGrid').datagrid('mergeCells',{
				index:(rows.length-1),	
				field:'planRepayDt',
				colspan:2
			});
		},//金额格式化，并且字体标红
		formatMoneyRed:function (value,row,index){
			if(value){
				var formatManey = accounting.formatMoney(value, "", 2, ",", ".");
				return "<font color='red'>"+formatManey+"</font>"
			}else{
				return "-";
			}
		}
			
	}
</script>
<a href="javascript:void(0)" class="easyui-linkbutton download" onclick="lookupRecord(${projectId},${loanId})">查看对账记录</a>
<div class="clear pt10"></div>
<form id="repaymentPlanForm" method="post">
	<div class="refuseListDiv" style="height:auto">
		<table id="loanCaculateGrid"  class="easyui-datagrid" 
			style="height:auto;width: auto;"
			data-options="
			 url: '${basePath}rePaymentController/queryLoanCaculate.action?loanId=${loanId}&showNew=${showNew}',
			    fitColumns:true,
			    idField:'pId',
				method:'post',
				onLoadSuccess:loanCalculate.colspan,
				rowStyler:loanCalculate.updateRowStyler"
			    >
			<!-- 表头 -->
			<thead>
				<tr>
					<th data-options="field:'pid',checkbox:true"  rowspan="2"></th> 
				    <th data-options="field:'planRepayDt',width:90,align:'center'" rowspan="2">还款日期</th>
				    <th data-options="field:'planCycleName',width:60,align:'center'" rowspan="2">期数</th>
				    <th data-options="" colspan="5" >本期应还明细</th>
				    <th data-options="field:'principalBalance',width:80,align:'right',formatter:formatMoney" rowspan="2">本金余额</th>
				    <th data-options="field:'overdueDays',width:80,align:'center'" rowspan="2">逾期天数</th>
				    <th data-options="field:'overdueInterest',width:80,align:'right',formatter:formatMoney" rowspan="2">应收逾期利息</th>
				    <th data-options="field:'overdueFine',width:80,align:'right',formatter:formatMoney" rowspan="2">应收逾期罚息</th>
				    <th data-options="field:'accountsTotal',width:80,align:'right',formatter:formatMoney" rowspan="2">应收合计</th>
				    <th data-options="field:'receivedTotal',width:80,align:'right',formatter:formatMoney" rowspan="2">已收合计</th>
				    <th data-options="field:'uncollectedTotal',width:80,align:'right',formatter:formatMoney" rowspan="2">未收合计</th>
				</tr>
				<tr>
				    <th data-options="field:'shouldPrincipal',width:100,align:'right',formatter:formatMoney">本期应还本金</th>
				    <th data-options="field:'shouldMangCost',width:100,align:'right',formatter:formatMoney">本期应付管理费</th>
				    <th data-options="field:'shouldOtherCost',width:100,align:'right',formatter:formatMoney">其它费用${otherCostName }</th>
				    <th data-options="field:'shouldInterest',width:100,align:'right',formatter:formatMoney">本期应付利息</th>
				    <th data-options="field:'total',width:80,align:'right',formatter:formatMoney">合计</th>
				</tr>
			</thead>
		</table>
	</div>
</form>