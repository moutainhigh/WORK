<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
	<div class="datum1" style="padding:30px 10px 10px 10px;width:1039px; ">
		<div style="height:400px" class="datum"> 
			<table id="grid" title="财务明细" class="easyui-datagrid" style="height:100%;width: auto;"
		     data-options="
		      url: '<%=basePath%>projectInfoController/getRepaymentByPage.action?projectId='+projectId,
		      method: 'POST',
		      rownumbers: true,
		      singleSelect: false,
		      pagination: false,
		      sortOrder:'asc',
		      remoteSort:false,
		      idField: 'pid',
		      fitColumns:true">
	     	<!-- 表头 -->
		     <thead>
		      <tr>
		       <th data-options="field:'planRepayDt'" align="center" halign="center">应还日期</th>
		       <th data-options="field:'planCycleNum'" align="center" halign="center">期数</th>
		       <th data-options="field:'thisStatus',formatter:formatThisStatus" align="center" halign="center">账单状态</th>
		       <th data-options="field:'shouldPrincipal',formatter:formatMoney" align="center" halign="center">应还本金</th>
		       <th data-options="field:'shouldInterest',formatter:formatMoney" align="center" halign="center">应还利息</th>
		       <th data-options="field:'productInterest',formatter:formatMoney" align="center" halign="center">产品利息</th>
		       <th data-options="field:'rebateFee',formatter:formatMoney" align="center" halign="center">返佣利息</th>
		       <th data-options="field:'total',formatter:formatMoney" align="center" halign="center">应还合计</th>
		       <th data-options="field:'actualPrincipal',formatter:formatMoney" align="center" halign="center">已收本金</th>
		       <th data-options="field:'actualInterest',formatter:formatMoney" align="center" halign="center">已收利息</th>
		       <th data-options="field:'actualTotal',formatter:formatMoney" align="center" halign="center">已收合计</th>
		       <th data-options="field:'actualRepayDt'" align="center" halign="center">本息收取日</th>
		       <th data-options="field:'overdueDays'" align="center" halign="center">逾期天数</th>
		       <th data-options="field:'overdueMoney',formatter:formatMoney" align="center" halign="center">本期逾期金额</th>
		       <th data-options="field:'actualOverdueMoney',formatter:formatMoney" align="center" halign="center">已收逾期金额</th>
		       <!-- <th data-options="field:'actualPenalty',formatter:formatMoney" align="center" halign="center">已收罚息</th> -->
		       <th data-options="field:'actualOverdueDt',formatter:formatOverdueDt" align="center" halign="center">逾期收取日</th>
		       <th data-options="field:'principalBalance',formatter:formatMoney" align="center" halign="center">应还剩余本金</th>
		       <th data-options="field:'shouldPrepaymentFee',formatter:formatMoney" align="center" halign="center">应收提前还款费</th>
		       <th data-options="field:'preRepayAmt',formatter:formatMoney" align="center" halign="center">已收剩余本金</th>
		       <th data-options="field:'fine',formatter:formatMoney" align="center" halign="center">已收提前还款费</th>
		       <th data-options="field:'repayDate'" align="center" halign="center">提前还款日</th>
		      </tr>
		     </thead>
	    </table>
	    </div>
	    <div class="table_label">
    			罚息提示：从第<b id="overdueCycleNum" style='color:#FF9900'></b>期开始已逾期<b id="overdueDays" style='color:red'></b>天，应还罚息=<b id="overdueDay"></b>（天）*<b id="principal"></b>（放款金额）*<b id="overdueRate"></b>%（逾期日罚息率）=<b id="shouldPenaltySpan"></b>元；已收罚息<b id="actualPenaltyLabel"></b>元；需还罚息<b id="shouldPenaltyLabel"  style='color:red'></b>元。
    	</div>
	    <div style="height:400px" class="datum"> 
	    <!-- 财务收费历史列表(分页查询) -->
	    <table id="fddCollectFeeHis_grid"  title="费用收取明细表" class="easyui-datagrid" style="height: 100%; width: auto;"
	     	data-options="
	     		url:'<%=basePath%>financeHandleController/collectFeeHisList.action?projectId='+projectId,
			    method: 'POST',
			    rownumbers: true,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    idField: 'pid',
			    fitColumns:true">
		     <!-- 表头 -->
		     <thead>
		      <tr>
		       <th data-options="field:'recProStr'" align="center" halign="center">收款项目</th>
		       <th data-options="field:'consultingFee',formatter:formatMoney" align="center" halign="center">金额</th>
		       <th data-options="field:'recDate',formatter:convertDate" align="center" halign="center">收款日期</th>
		       <th data-options="field:'recAccount'" align="center" halign="center">收款账号</th>
		       <th data-options="field:'createrName'" align="center" halign="center">操作人</th>
		       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center">操作时间</th>
		      </tr>
		     </thead>
	    </table>
	     </div>
	    <div style="height:400px" class="datum"> 
	    <!-- 还款历史记录 -->
	    <table id="repayment_record"  title="还款明细表" class="easyui-datagrid" style="height: 100%; width: auto;"
	     	data-options="
	     		url:'<%=basePath%>projectInfoController/getRepaymentRecordByPage.action?projectId='+projectId,
			    method: 'POST',
			    rownumbers: true,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    idField: 'pid',
			    fitColumns:true">
		     <!-- 表头 -->
		     <thead>
		      <tr>
		       <th data-options="field:'planCycleNum'" align="center" halign="center">收款期数</th>
		       <th data-options="field:'repaymentType',formatter:formatType" align="center" halign="center">收款项目</th>
		       <th data-options="field:'repaymentMoney',formatter:formatMoney" align="center" halign="center">金额</th>
		       <th data-options="field:'repaymentDate',formatter:convertDate" align="center" halign="center">收款日期</th>
		       <th data-options="field:'accountNo'" align="center" halign="center">收款账号</th>
		       <th data-options="field:'createrName'" align="center" halign="center">操作人</th>
		       <th data-options="field:'createrDate',formatter:convertDateTime" align="center" halign="center">操作时间</th>
		      </tr>
		     </thead>
	    </table>
	</div>
</div>
<script type="text/javascript">
	function formatType(val,row,index){
		if (val == 1) {
			return "实收本金";
		} else if (val == 2) {
			return "实收利息";
		} else if (val == 3) {
			return "实收逾期金额";
		} else if (val == 4) {
			return "实收罚息";
		} else if (val == 5) {
			return "实收剩余本金";
		} else if (val == 6) {
			return "实收提前还款费";
		}
	}
	$(document).ready(function() {
		$.ajax({
			url : "${basePath}repaymentController/getRepaymentDetailInfo.action",
			cache : true,
			type : "POST",
			data : {'projectId' :projectId},
			async : false,
			success : function(data, status) {
				var result = eval("("+data+")");
				var overdueCycleNum = result.overdueCycleNum;
				if(overdueCycleNum == 0){
					overdueCycleNum = result.planCycleNum;
				}
				
				$('#overdueCycleNum').html(overdueCycleNum);
				$('#overdueDays').html(result.overdueDays);
				$('#overdueDay').html(result.overdueDays);
				$('#principal').html(result.principalBalance);
				$('#overdueRate').html(result.overdueRate);
				$('#shouldPenaltySpan').html(result.shouldPenalty);
				$('#actualPenaltyLabel').html(result.actualPenalty);
				var penalty = result.shouldPenalty - result.actualPenalty;
				$('#shouldPenaltyLabel').html(penalty);
				}
			}); 
	});
	 
	
</script> 