<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!--360浏览器优先以webkit内核解析-->
<title>主页</title>
<%@ include file="../../../common.jsp"%>
<link rel="shortcut icon" href="favicon.ico">
<style>
li {
	list-style-type: none;
}
.gray-bg{background: #fff;}
</style>
</head>
<body class="gray-bg">
 <div class=" white-bg dashboard-header">
  <%-- <div class="col-sm-12 bg">
    <label>认证状态：</label>${orgAuditStatusMap[partnerInfo.status] } <br> <label> 合作状态：</label>${orgCooperateStatusMap[partnerInfo.cooperationStatus] }
  </div> --%>
 <%--  <div class="col-sm-4 Surplus">
   <ul>
    <li><label>待结算金额：<br/><b><comm:moneyFormat object="${partnerOrderSummary.notFeeSettleTotalMoney }"/></b></label></li>
   </ul>
  </div> --%>
  <div class="col-sm-6 frozen">
   <ul>
    <li><label>已结算金额：<br/><b><comm:moneyFormat object="${partnerOrderSummary.feeSettleTotalMoney }"/></b></label></li>
   </ul>
  </div>
  <div class="col-sm-6 organization">
   <ul>
    <li><label>机构总数：<br/><b>${partnerOrderSummary.orgTotal }</b></label></li>
   </ul>
  </div>
 
 </div>
<!--  <div class="wrapper wrapper-content">
  <div class="row">
   <div class="col-sm-4">
    <div class="ibox float-e-margins">
     <div class="ibox-title">
      <h5>商务顾问</h5>

     </div>
     <div class="ibox-content">
      <p>
       <i class="fa fa-user"></i> 你的顾问：<a href="javascript:;">张三</a>
      </p>
      <p>
       <i class="fa fa-phone"></i> 手机号：<a href="javascript:;">13300000000</a>
      </p>
      <p>
       <i class="fa fa-qq"></i> QQ：<a href="http://wpa.qq.com/msgrd?v=3&amp;uin=995396426&amp;site=qq&amp;menu=yes" target="_blank">995396426</a>
      </p>
      <p>
       <i class="fa fa-weixin"></i> 微信：<a href="javascript:;">martian</a>
      </p>
      <p>
       <i class="fa fa-credit-card"></i> 邮 箱：<a href="javascript:;" class="支付宝信息">13300000000@qfang.com / *君君</a>
      </p>
     </div>
    </div>
   </div>
  </div>
 </div> -->
 <%@ include file="../../../../delayLoadCommon.jsp"%>
 <script src="js/welcome.min.js"></script>
</body>
</html>