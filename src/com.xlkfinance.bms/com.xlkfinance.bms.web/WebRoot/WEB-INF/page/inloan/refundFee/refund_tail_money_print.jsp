<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>财务办理</title>
<style type="text/css">
.table_css td {
	padding: 7px 10px;
	font-size: 14px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}
</style>
</head>
<script type="text/javascript">
/**************工作流字段 begin******************/
var WORKFLOW_ID = "refundTailMoneyProcess"; 
var workflowTaskDefKey = "task_Request";
nextRoleCode = "POST_LOAN_MANAGEMENT";//
/**************工作流字段 end********************/
var projectId = ${editRefundFeeDTO.projectId};
	function subForm() {
		var applyStatus=$("#applyStatus").val();
		if (applyStatus><%=com.xlkfinance.bms.common.constant.Constants.APPLY_REFUND_FEE_STATUS_2%>
		    &&applyStatus<<%=com.xlkfinance.bms.common.constant.Constants.APPLY_REFUND_FEE_STATUS_7%>
	) {
			$.messager.alert("操作提示", "该项目正在审核中，不可以修改！", "error");
			return;
		} else if (applyStatus >=
<%=com.xlkfinance.bms.common.constant.Constants.APPLY_REFUND_FEE_STATUS_7%>
	) {
			$.messager.alert("操作提示", "该项目已归档，不可以修改！", "error");
			return;
		}
		$.ajax({
			url : "${basePath}refundTailMoneyController/update.action",
			cache : true,
			type : "POST",
			data : $("#refundTailMoneyForm").serialize(),
			async : false,
			success : function(data, status) {
				if (data == "1") {
					$.messager.alert("操作提示", "保存成功！");
				} else {
					$.messager.alert("操作提示", "保存失败！", "error");
				}
			}
		});
	}

	/**
	 * 关闭窗口
	 */
	function closeWindow() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
		parent.$('#layout_center_tabs').tabs('close', index);
	}
	function print() {
		$("#myPrintArea").printArea();
	};
	$(document).ready(function() {
		$("#returnFeeCny").text(numToCny($("#returnFee").text()));
	});
</script>
<head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="退尾款信息" style="padding: 10px;">
    <form action="#" id="refundTailMoneyForm" name="refundTailMoneyForm" method="post">
    <div id="myPrintArea">
	<style>/*打印*/
	.table_css td {
		padding: 7px 5px;
		font-size: 12px;
		border-bottom: 1px #95B8E7 solid;
		border-right: 1px #95B8E7 solid;
	}
	</style>
     <table cellpadding="0" cellspacing="0" class="table_css" style="width: 100%; border-top: 1px #95B8E7 solid; border-left: 1px #95B8E7 solid;">
      <tr>
       <td colspan="7" align="center" class="bt"><b style="font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">退付结算余款通知单(${editRefundFeeDTO.projectNumber })</b></td>
      </tr>
      <tr>
       <td>产品名称：</td>
       <td colspan="2">${editRefundFeeDTO.productName }</td>
       <td>交易方式：</td>
       <td colspan="3">${editRefundFeeDTO.tradeWay }</td>
      </tr>
      <tr>
       <td>交易日期：</td>
       <td colspan="2">${editRefundFeeDTO.tradeDate }</td>
       <td>抵押编号：</td>
       <td colspan="3">${editRefundFeeDTO.mortgageNumber }</td>
      </tr>
      <tr>
       <td>借款申请人：</td>
       <td colspan="2">${editRefundFeeDTO.customerName }</td>
       <td>经办人：</td>
       <td colspan="3">${editRefundFeeDTO.pmUserName }</td>
      </tr>
      <tr>
       <td>房产名称：</td>
       <td colspan="2">${editRefundFeeDTO.homeName }</td>
       <td>原业主：</td>
       <td colspan="3">${editRefundFeeDTO.oldHome }</td>
      </tr>
      <tr>
       <td>借款金额：</td>
       <td colspan="2">${editRefundFeeDTO.guaranteeMoney }</td>
       <td>放款金额：</td>
       <td colspan="3">${editRefundFeeDTO.extractMoney }</td>
      </tr>
      <tr>
       <td>放款银行：</td>
       <td colspan="2">${editRefundFeeDTO.lendBank }</td>
       <td>赎楼银行：</td>
       <td colspan="3">${editRefundFeeDTO.foreclosureFloorBank }</td>
      </tr>
      <tr>
       <td>回款金额：</td>
       <td>银行放款合计金额：</td>
       <td>${editRefundFeeDTO.bankLendTotalAmount }</td>
       <td width="75">业务部门：</td>
       <td>${editRefundFeeDTO.deptName }</td>
       <td>已收咨询费：</td>
       <td>${editRefundFeeDTO.recGuaranteeMoney }</td>
      </tr>
      <tr>
       <td rowspan="2">额度资金付出：</td>
       <td>赎楼金额本金：</td>
       <td>${editRefundFeeDTO.foreclosureFloorMoney }</td>
       <td>利息：</td>
       <td>${editRefundFeeDTO.interest }</td>
       <td>罚息：</td>
       <td>${editRefundFeeDTO.defaultInterest }</td>
      </tr>
      <tr>
       <td>客户补差额：</td>
       <td>&nbsp;</td>
       <td>付款合计：</td>
       <td colspan="4">${editRefundFeeDTO.payTotal }</td>
      </tr>
      <tr>
       <td>结算：</td>
       <td>应退尾款：</td>
       <td id="returnFee"><b style="font-size: 16px; font-family: 'Microsoft YaHei', '微软雅黑';">${editRefundFeeDTO.returnFee }</b></td>
       <td>合计金额：</td>
       <td colspan="3"><b style="font-size: 16px; font-family: 'Microsoft YaHei', '微软雅黑';" id="returnFeeCny"></b></td>
      </tr>
      <tr>
       <td>赎楼余（尾）款：</td>
       <td>收款人户名：</td>
       <td>${editRefundFeeDTO.recAccountName }</td>
       <td>收款人账号：</td>
       <td>${editRefundFeeDTO.recAccount }</td>
       <td width="60">开户行：</td>
       <td>${editRefundFeeDTO.bankName }</td>
      </tr>
      <c:forEach items="${workFlowHistoryList }" var="workFlowHistory">
      <tr>
       <td width="100">${workFlowHistory.taskName }：</td>
       <td colspan="2" style="border-right: none;">${workFlowHistory.message }</td>
       <td colspan="2" style="border-right: none;">处理人：${workFlowHistory.executeUserRealName }</td>
       <td colspan="2">${workFlowHistory.executeDttm }</td>
      </tr>
      </c:forEach>
     </table>
     <input type="hidden" name="pid" value="${editRefundFeeDTO.pid }"> <input type="hidden" name="applyStatus" id="applyStatus"
      value="${editRefundFeeDTO.applyStatus }">
    </form>
    </div>
    <p class="jl">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">关闭</a>
     <a href="javascript:void(0)" class="easyui-linkbutton" id="biuuu_button" iconCls="icon-print" onclick="print()">打印</a>
    </p>
   </div>
  </div>
 </div>
</body>
</html>
