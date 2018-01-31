<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<title>项目风险业务报告</title>
<style type="text/css">
.table_css td {
	padding: 7px 10px;
	font-size: 14px;
	border-bottom: 1px #ddd solid;
	border-right: 1px #ddd solid;
}
</style>
</head>
<script>
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
}
</script>
<head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="项目风险业务报告" style="padding: 10px;">
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
       <td colspan="6" align="center" class="bt"><b style="font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">关于${projectNumber}的风险业务报告</b></td>
      </tr>
      <tr>
       <td colspan="3"><b>报告人：${loginUserName}</b></td>
       <td colspan="3" align="right"><b>报告日期：${reportDate}</b></td>
       </tr>
      <tr>
       <td colspan="6" align="center"><b style="font-size: 16px;" >合作机构情况</b></td>
       </tr>
      <tr>
       <td align="center"><strong>业务来源</strong></td>
       <td align="center"><strong>存入保证金</strong></td>
       <td align="center"><strong>单笔上线</strong></td>
       <td align="center"><strong>启用授信额度</strong></td>
       <td align="center"><strong>截止${reportDate}日在保余额/笔数</strong></td>
      </tr>
      <tr>
       <td align="center">${businessSourceStr}</td>
       <td align="center">${assureMoney}</td>
       <td align="center">${singleUpperLimit}</td>
       <td align="center">${availableLimit}</td>
       <td align="center"> 
	       <c:if test="${fn:startsWith(businessSourceStr, '朋友')}">0.0元/0笔</c:if>
	       <c:if test="${fn:startsWith(businessSourceStr, '其他')}">0.0元/0笔</c:if>
	       <c:if test="${fn:startsWith(businessSourceStr, '银行')}">0.0元/0笔</c:if>
	       <c:if test="${fn:startsWith(businessSourceStr, '中介')}">0.0元/0笔</c:if>
	       <c:if test ="${!fn:startsWith(businessSourceStr, '朋友') && !fn:startsWith(businessSourceStr, '其他') && !fn:startsWith(businessSourceStr, '银行') && !fn:startsWith(businessSourceStr, '中介')}">${totalMoney}元/${totalCount}笔</c:if>
       </td>
      </tr>
      <tr>
       <td align="center"><strong>返担保情况</strong></td>
       <td colspan="4" align="left">${unAssureCondition}</td>
      </tr>
      <tr align="center">
       <td colspan="6"><b style="font-size: 16px;">风险业务情况</b></td>
      </tr>
      <tr>
       <td align="center"><strong>借款人</strong></td>
       <td align="center"><strong>借款金额</strong></td>
       <td align="center"><strong>借款期限</strong></td>
       <td align="center"><strong>业务类型</strong></td>
       <td align="center"><strong>我司出款情况</strong></td>
      </tr>
      <tr>
        <td align="center">${chinaName}</td>
        <td align="center">${loanMoney}</td>
        <td align="center">${loanDays}</td>
        <td align="center">${productName}</td>
        <td align="center">${updateDate}</td>
      </tr>
      <tr>
       <td align="center"><strong>身份证号:</strong></td>
       <td align="center"><strong>房产证号</strong></td>
       <td align="center"><strong>关联企业</strong></td>
       <td align="center"><strong>赎楼日期</strong></td>
       <td align="center"><strong>上资方情况</strong></td>
      </tr>
      <tr>
       <td align="center">${certNumber}</td>
       <td align="center">${housePropertyCard}</td>
       <td align="center">${cpyName}</td>
       <td align="center">${payDate}</td>
       <td align="center">${partnerLoanDate}</td>
      </tr>
      <tr>
       <td align="center"><strong>物业名称</strong></td>
       <td colspan="2" align="center">${houseName}</td>
       <td align="center"><strong>房产状态</strong></td>
       <td align="center">${houseProperyCondition}</td>
      </tr>
      
      
      <c:forEach items="${legalList}" var="item">
      	<tr>
      		<c:if test="${item.status==1}">
      		 <td align="center"><strong>个人诉讼</strong></td>
      		 <td colspan="4">${item.legalContent}</td>
      		</c:if>
      		<c:if test="${item.status==2}">
      		 <td align="center"><strong>企业诉讼</strong></td>
      		 <td colspan="4">${item.legalContent}</td>
      		</c:if>
      	</tr>
      </c:forEach>
      <tr>
       <td align="center"><strong>跟进情况</strong></td>
       <td colspan="4">
       <c:forEach items="${afterExceptionReportList}" var="item">
       	 异常及说明：${(item.createrDate).substring(0,10)}:${item.remark};</br>
       </c:forEach>
       <c:forEach items="${exceptionMonitorIndexList}" var="item">
       	 跟进意见：${(item.monitorDate).substring(0,10)}:
       		 	${item.monitorOpinion};</br>
     </c:forEach>
       </td>
      </tr>
            <tr>
       <td align="center"><strong>备注</strong></td>
       <td colspan="4">
       		${remark}
       </td>
      </tr>
     </table>
     <input type="hidden" name="pid" value="${editRefundFeeDTO.pid }"> <input type="hidden" name="applyStatus" id="applyStatus"
      value="${editRefundFeeDTO.applyStatus }">
    
    </div>
    </form>
    <p class="jl">
     <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">关闭</a>
     <a href="javascript:void(0)" class="easyui-linkbutton" id="biuuu_button" iconCls="icon-print" onclick="print()">打印</a>
    </p>
   </div>
  </div>
 </div>

</body>
</html>
