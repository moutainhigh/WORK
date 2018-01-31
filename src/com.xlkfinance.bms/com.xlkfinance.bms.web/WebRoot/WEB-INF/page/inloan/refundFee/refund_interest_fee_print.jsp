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
<title>退咨询费</title>
<style type="text/css">
.table_css td {
	padding: 7px 10px;
	font-size: 14px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}
.bt{ background-color: #E0ECFF;
background: -webkit-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: -moz-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: -o-linear-gradient(top,#EFF5FF 0,#E0ECFF 100%);
background: linear-gradient(to bottom,#EFF5FF 0,#E0ECFF 100%);
background-repeat: repeat-x;
filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,endColorstr=#E0ECFF,GradientType=0);
font-size: 12px;
font-weight: bold;
color: #0E2D5F;}
</style>
</head>
<script type="text/javascript">
	/**************工作流字段 begin******************/
	var WORKFLOW_ID = "refundInterestFeeProcess";
	var workflowTaskDefKey = "task_Request";
	nextRoleCode = "GUARANTEE_DEPARTMENT_MANAGER";//
	/**************工作流字段 end********************/
	var projectId = ${editRefundFeeDTO.projectId};

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
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="退咨询费信息" style="padding: 10px;">
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
     <table border="0" cellpadding="0" cellspacing="0" class="table_css" style="width: 100%; border-top: 1px #95B8E7 solid; border-left: 1px #95B8E7 solid;">
      <tr>
       <td colspan="6" align="center" class="bt"><b style="font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">退咨询费通知单(${editRefundFeeDTO.projectNumber })</td>
      </tr>
      <tr>
       <td>产品名称：</td>
       <td colspan="2">${editRefundFeeDTO.productName }</td>
       <td>实际用款天数：</td>
       <td colspan="2">${editRefundFeeDTO.realUseDays }</td>
      </tr>
      <tr>
       <td>借款申请人：</td>
       <td colspan="2">${editRefundFeeDTO.customerName }</td>
       <td>经办人：</td>
       <td colspan="2">${editRefundFeeDTO.pmUserName }</td>
      </tr>
      <tr>
       <td>借款金额：</td>
       <td colspan="2">${editRefundFeeDTO.guaranteeMoney }</td>
       <td>放款金额：</td>
       <td colspan="2">${editRefundFeeDTO.extractMoney }</td>
      </tr>
      <tr>
       <td>回款日期：</td>
       <td colspan="2">${editRefundFeeDTO.repaymentDate }</td>
       <td>放款日期：</td>
       <td colspan="2">${editRefundFeeDTO.payDate }</td>
      </tr>
      <tr>
       <td rowspan="2">额度资金转入：</td>
       <td>银行放款合计金额：</td>
       <td>${editRefundFeeDTO.bankLendTotalMoney }</td>
       <td>业务部门：</td>
       <td colspan="2">${editRefundFeeDTO.deptName }</td>
      </tr>
      <tr>
       <td>已收咨询费：</td>
       <td>${editRefundFeeDTO.recGuaranteeMoney }</td>
       <td>费率：</td>
       <td colspan="2">${editRefundFeeDTO.feeRate }</td>
      </tr>
      <tr>
       <td>结算：</td>
       <td>退费金额：</td>
       <td id="returnFee"><b style="font-size: 16px; font-family: 'Microsoft YaHei', '微软雅黑';">${editRefundFeeDTO.returnFee }</b></td>
       <td>大写：</td>
       <td colspan="2"><b style="font-size: 16px; font-family: 'Microsoft YaHei', '微软雅黑';" id="returnFeeCny"></b></td>
      </tr>
      <tr>
       <td rowspan="2">收退款账户：</td>
       <td>收款人户名：</td>
       <td>${editRefundFeeDTO.recAccountName }</td>
       <td>开户行：</td>
       <td colspan="2">${editRefundFeeDTO.bankName }</td>
      </tr>
      <tr>
       <td>收款人账号：</td>
       <td colspan="4">${editRefundFeeDTO.recAccount }</td>
      </tr>
      <c:forEach items="${workFlowHistoryList }" var="workFlowHistory">
      <tr>
       <td width="100">${workFlowHistory.taskName }：</td>
       <td colspan="2" style="width:40%; border-right: none;word-break: break-all;">${workFlowHistory.message }</td>
       <td style="border-right: none;">处理人：${workFlowHistory.executeUserRealName }</td>
       <td>${workFlowHistory.executeDttm }</td>
      </tr>
      </c:forEach>
     </table>
     <input type="hidden" name="pid" value="${editRefundFeeDTO.pid }"> <input type="hidden" name="applyStatus" id="applyStatus"
      value="${editRefundFeeDTO.applyStatus }"
     >
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