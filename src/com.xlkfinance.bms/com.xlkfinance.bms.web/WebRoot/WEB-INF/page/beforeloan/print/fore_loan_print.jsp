<%@page import="com.xlkfinance.bms.rpc.inloan.*"%>
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
<title>打印出账审批单</title>
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
   <div title="出账审批单" style="padding: 10px;">
    <form action="#" id="printForm" name="printForm" method="post">
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
       <td colspan="6" align="center" class="bt"><b style="font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">出账审批单(${project.projectNumber })</b></td>
      </tr>
      
      <tr>
       <td colspan="2">业务部门：</td>
       <td>${project.orgName }</td>
       <td width="75" colspan="2">部门经理：</td>
       <td>${project.managerName }</td>
      </tr>
      
      <tr>
       <td colspan="2">借款人：</td>
       <td>${project.acctName }</td>
       <td colspan="2">经办人：</td>
       <td>${project.pmUserName }</td>
      </tr>
      
      <tr>
       <td colspan="2">借款金额：</td>
       <td>${project.loanMoneyStr }</td>
       <td colspan="2">借款期限：</td>
       <td>${project.loanDay }天</td>
      </tr>
      
      <tr>
       <td colspan="2">业务来源（机构名称）：</td>
       	<td>${project.businessSourceStr}</td>
       <td colspan="2">放款银行：</td>
       <td>${project.paymentBank}</td>
     </tr>

      <tr>
       <td colspan="2">业务类型：</td>
       <td>${project.productName}</td>
       <td colspan="2">赎楼银行：</td>
       <td>${project.foreBank }</td>
      </tr>
      
      <tr>
       <td colspan="2">回款账户名称：</td>
       <td>${project.paymentName}</td>
       <td colspan="2">回款账号：</td>
       <td>${project.paymentAccount}</td>
      </tr>
      
      <tr>
       <td colspan="2">房产证号：</td>
       <td>${project.houseCardNo }</td>
       <td colspan="2">物业名称：</td>
       <td>${project.houseName }</td>
      </tr>
      
      <tr>
       <td colspan="2">收费情况：</td>
       <td colspan="4">${project.chargeSituation }</td>
      </tr>
      
       <tr>
	       <td colspan="2">出款账号：</td>
	       <td colspan="4">${project.foreAccount}</td>
      </tr>
      <tr>
       <td colspan="2">审查员意见：</td>
       <td colspan="4">
       	<c:if test="${examinerCheckDto !=null }">
       		${examinerCheckDto.executeUserRealName }：${examinerCheckDto.message }
       	</c:if>
       </td>
      </tr>
      
      <tr>
       <td colspan="2">风控经理意见：</td>
       <td colspan="4">
       <c:if test="${riskCheckDto !=null }">
       		${riskCheckDto.executeUserRealName }：${riskCheckDto.message }
       	</c:if>
       </td>
      </tr>
      
      <tr>
       <td colspan="2">贷后意见：</td>
       <td colspan="4">
       <c:if test="${orgManagerCheckDto !=null }">
       		${orgManagerCheckDto.executeUserRealName }：${orgManagerCheckDto.message }
       	</c:if>
       </td>
      </tr>
      
  	 <tr>
       <td rowspan="5">运营部门意见：</td>
      </tr>
      <tr>
      	<td align="center">产权查询：</td>
      	<td colspan="4">
      	<c:forEach items="${cdtoList}" var="item">
      		查档时间：${(item.checkDate).substring(0,10)}&nbsp;
<!--       		查档状态：未查档=1,抵押=2,有效=3,无效=4,查封=5,抵押查封=6,轮候查封=7，再抵押=8 -->
      		 <c:if test ="${item.checkStatus==2}"> 
      			查档状态：抵押&nbsp;</br>
      		</c:if>
      		 <c:if test ="${item.checkStatus==3}"> 
      			查档状态：有效&nbsp;</br>
      		</c:if>
      		<c:if test ="${item.checkStatus==4}"> 
      			查档状态：无效&nbsp;</br>
      		</c:if>
      		<c:if test ="${item.checkStatus==5}"> 
      			查档状态：查封&nbsp;</br>
      		</c:if>
      		<c:if test ="${item.checkStatus==6}"> 
      			查档状态：抵押查封&nbsp;</br>
      		</c:if>
      		<c:if test ="${item.checkStatus==7}"> 
      			查档状态：轮候查封&nbsp;</br>
      		</c:if>
      		<c:if test ="${item.checkStatus==8}"> 
      			查档状态：再抵押&nbsp;</br>
      		</c:if>
      	</c:forEach>
      	 <c:if test="${documentDTO != null && project.isNeedHandle==1}">
                                  审批意见：${documentDTO.remark}&nbsp;&nbsp; 
         </c:if>   
<%--        <c:if test="${documentDTO != null && project.isNeedHandle==1}"> --%>
<%--          <%CheckDocumentDTO documentDTO=(CheckDocumentDTO)request.getAttribute("documentDTO"); %> --%>
<%--                              查档时间：${documentDTO.checkDate},查档状态：<%=com.xlkfinance.bms.common.constant.Constants.CHECK_DOCUMENT_STATUS_MAP.get(documentDTO.getCheckStatus())%>,审批意见：${documentDTO.remark}&nbsp;&nbsp; --%>
<%--           ${documentDTO.updateName} &nbsp; ${documentDTO.updateDate}                   --%>
<%--         </c:if>   --%>

                     
        </td>
      </tr>
      <tr>
      </tr>
      <tr>
      	<td width="120" align="center">其它：</td>
        <td colspan="4"></td>
      </tr>
      <tr>
        <td align="center">运营部经理：</td>
        <td colspan="4">
         <c:if test="${documentDTO != null && project.isNeedHandle==1}">
         <%CheckDocumentDTO documentDTO=(CheckDocumentDTO)request.getAttribute("documentDTO"); %>
            ${documentDTO.reCheckUserName}&nbsp;${documentDTO.reCheckDate}&nbsp;<%=com.xlkfinance.bms.common.constant.Constants.CHECK_DOCUMENT_RE_CHECK_STATUS_MAP.get(documentDTO.getReCheckStatus())%>    
        </c:if>
        </td>
      </tr>

      <tr>
       <td width="50" rowspan="6">财务中心意见：</td>
       </tr>
       <tr>
        <td align="center">出纳：</td>
        <td colspan="4">
        <c:if test="${consultFee!=null }">
        <%ApplyFinanceHandleDTO consultFee=(ApplyFinanceHandleDTO)request.getAttribute("consultFee"); %>
        ${consultFee.recDate }(收费日)&nbsp;${consultFee.resourceStr }&nbsp;${consultFee.recAccount }&nbsp;￥${consultFee.recMoney }&nbsp;${consultFee.operator }&nbsp;${consultFee.updateDate }
        </c:if>
        </td>
      </tr>
      <tr>
        <td align="center">资金专员：</td>
        <td colspan="4">
        <c:if test="${firstRealLoan!=null }">
        <%ApplyFinanceHandleDTO firstRealLoan=(ApplyFinanceHandleDTO)request.getAttribute("firstRealLoan"); %>
                              一次放款：${firstRealLoan.recDate }(出账日)&nbsp;${firstRealLoan.resourceStr }&nbsp;￥${firstRealLoan.recMoney }&nbsp;${firstRealLoan.operator }&nbsp;${firstRealLoan.updateDate }(操作日)
        </c:if>
        <c:if test="${secondLoan!=null }">
        <%ApplyFinanceHandleDTO secondRealLoan=(ApplyFinanceHandleDTO)request.getAttribute("secondLoan"); %>
        <br>二次放款：${secondLoan.recDate }(出账日)&nbsp;${secondLoan.resourceStr }&nbsp;￥${secondLoan.recMoney }&nbsp;${secondLoan.operator }&nbsp;${secondLoan.updateDate }(操作日)
        </c:if>
        </td>
      </tr>
      <c:if test="${firstRealLoan!=null && secondLoan ==null}">
	      <tr>
	        <td align="center">资金经理：</td>
	        <td colspan="4">${firstRealLoan.fundManagerRemark}</td>
	      </tr>
      </c:if>
      <c:if test="${firstRealLoan!=null && secondLoan !=null}">
	      <tr>
	        <td align="center">资金经理：</td>
	        <td colspan="4"><span>第一次放款意见:</span>${firstRealLoan.fundManagerRemark}
	        <span>第二次放款意见:</span>${secondLoan.fundManagerRemark}</td>
	      </tr>
      </c:if>
      <c:if test="${firstRealLoan!=null && secondLoan==null &&firstRealLoan.recMoney>=3000000}">
	      <tr>
	        <td align="center">财务总监：</td>
	        <td colspan="4">${firstRealLoan.financeDirectorRemark }</td>
	      </tr>
      </c:if>
      <c:if test="${firstRealLoan!=null && secondLoan!=null &&firstRealLoan.recMoney>=3000000}">
	      <tr>
	        <td align="center">财务总监：</td>
	        <td colspan="4"><span>第一次放款意见:</span>${firstRealLoan.financeDirectorRemark } 
	        <span>第二次放款意见:</span>${secondLoan.financeDirectorRemark}</td>
	      </tr>
      </c:if>
<%--       <tr>
        <td align="center">出账制单、复核、赎楼：</td>
        <td colspan="4">
        <c:if test="${houseBalances1 !=null }">
                              一次赎楼：${firstRealLoan.recDate }(出账日)&nbsp;${houseBalances1.handleUserName }&nbsp;${houseBalances1.backAccount }&nbsp;￥${houseBalances1.principal }&nbsp;${houseBalances1.createrName }&nbsp;${houseBalances1.createrDate } (操作日)
        </c:if>
        <c:if test="${houseBalances2 !=null }">
          <br> 一次赎楼：${secondRealLoan.recDate }(出账日)&nbsp;${houseBalances2.handleUserName }&nbsp;${houseBalances2.backAccount }&nbsp;￥${houseBalances2.principal }&nbsp;${houseBalances2.createrName }&nbsp;${houseBalances2.createrDate }(操作日)
        </c:if>
        
        </td>
        </tr> --%>
     </table>
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
