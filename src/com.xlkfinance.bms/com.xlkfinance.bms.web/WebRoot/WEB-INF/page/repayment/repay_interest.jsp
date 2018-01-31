<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
	<script>
	var projectNum=null;
	$(function() {
		if(projectNum==null||projectNum==undefined||projectNum==''){
			 projectNum ='${projectNum}';
		}
	});
	$(function() {
		$('#tttw').datagrid({
			title : '放款收息表',
			selectOnCheck : true,
			collapsible:true,
			rownumbers : true,
			idField : 'pId',
			method : 'POST',
			url : '${basePath}rePaymentController/advapplyrepaymenturl.action?projectId='+projectNum,
			pagination : true,
			fitColumns:true,
			frozenColumns : [ [  {
				field : 'www',
				title : '全选',
				width : 50,
				checkbox:true
			}  ] ],
			columns : [ [ {
				field : 'planRepayDt',
				title : '还款日期',
				rowspan : 2,
				width : 100

			}, {
				field : 'planCycleNum',
				title : '还款期数',
				rowspan : 2,
				align : 'center',
				width : 80,
				formatter : function(val, rec) {
					if (val == -2) {
						return "挪用罚金";
					} else if (val == -1) {
						return "坏账";
					} else if (val == -3) {
						return "违约罚金";
					} else {
						return val;
					}
				}
			}, {
				title : '本期应还款明细',
				colspan : 5
			},

			{
				field : 'principalBalance',
				title : '本金余额',
				width : 60,
				align : 'left',
				rowspan : 2,
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'overdueLoanmt',
				title : '应收逾期利息',
				width : 80,
				align : 'left',
				rowspan : 2,
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'overdueFineAmt',
				title : '应收逾期罚息',
				width : 80,
				align : 'left',
				rowspan : 2,
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'shouldTotailAmt',
				title : '应收合计',
				width : 60,
				align : 'left',
				rowspan : 2
			}, {
				field : 'hasReceiveAmt',
				title : '已收合计',
				width : 60,
				align : 'left',
				rowspan : 2,
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'noReceiveAmt',
				title : '未收合计',
				width : 60,
				align : 'left',
				rowspan : 2,
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			} ], [ {
				field : 'shouldPrincipal',
				title : '本期应还本金',
				width : 80,
				align : 'left',
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'shouldMangCost',
				width : 100,
				align : 'left',
				title : '本期应付管理费',
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'shouldOtherCost',
				width : 80,
				align : 'left',
				title : '其他费用',
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'shouldInterest',
				width : 80,
				align : 'left',
				title : '本期应付利息',
				formatter : function(val, rec) {
					if (val == 0) {
						return "-";
					} else {
						return val;
					}
				}
			}, {
				field : 'total',
				width : 80,
				align : 'left',
				title : '合计'
			} ] ]
		});
		$('#tttw').datagrid({
			rowStyler : function(index, row) {
				if (row.planCycleNum == -2) {
					return 'background-color:red;';
				}
				if (row.planCycleNum == -3) {
					return 'background-color:blue;';
				}
				if (row.planCycleNum == -1) {
					return 'background-color:pink;';
				}
				if (row.isReconciliation == 1) {
					return 'background-color:gray;';
				}
			}
		});
	});
	</script>
		<table id="tttw" height="400"></table>