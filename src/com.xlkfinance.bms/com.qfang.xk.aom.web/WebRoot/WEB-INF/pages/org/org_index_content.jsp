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
li {list-style-type: none;}
.gray-bg{background: #fff;}
.col-sm-4{ position: relative;}
.help-tip{position: absolute; top: 15px; right: 30px; text-align: center; background-color: rgba(200, 200, 200, 0.8); border-radius: 50%; width: 24px; height: 24px;
 font-size: 14px; line-height: 26px; cursor: default;}

.help-tip:before{content:'?'; font-weight: bold; color:#fff;}
@media (min-width:768px) {.zaibao_sum .help-tip, .zaibao .help-tip{right:15px;}}
.Surplus .help-tip, .frozen .help-tip {background-color: rgba(255, 255, 255, 0.8);top: 30px;}
.Surplus .help-tip:before{color:#f6ad02;}
.frozen .help-tip:before{color:#45b6e1;}
.help-tip:hover p{
	display:block;
	transform-origin: 100% 0%;
	-webkit-animation: fadeIn 0.3s ease-in-out;
	animation: fadeIn 0.3s ease-in-out;
}

.help-tip p{
	display: none;
	text-align: left;
	background-color:#fff;
	padding:15px;
	width: 300px;
	position: absolute;
	border-radius: 3px;
	box-shadow: 1px 1px 1px rgba(0, 0, 0, 0.2);
	right: -4px;
	color: #848798;
	font-size: 14px;
	line-height: 1.4;
	font-weight: bold;
	top:32px;
	 z-index: 999;
}

.help-tip p:before{
	position: absolute;
	content: '';
	width:0;
	height: 0;
	border:6px solid transparent;
	border-bottom-color:#fff;
	right:10px;
	top:-12px;
}

.help-tip p:after{
	width:100%;
	height:40px;
	content:'';
	position: absolute;
	top:-40px;
	left:0;
}

@-webkit-keyframes fadeIn {
	0% { 
		opacity:0; 
		transform: scale(0.6);
	}

	100% {
		opacity:100%;
		transform: scale(1);
	}
}

@keyframes fadeIn {
	0% { opacity:0; }
	100% { opacity:100%; }
}
.dashboard-header{ overflow: visible;}
</style>
</head>
<body class="gray-bg">
 <div class="white-bg dashboard-header">
  <%-- <div class="col-sm-12 bg">
   <label>机构名称：</label>${orgInfo.orgName } <br> <label>认证状态：</label>${orgAuditStatusMap[orgInfo.auditStatus] } <br> <label> 合作状态：</label>${orgCooperateStatusMap[orgInfo.cooperateStatus] }
   <!-- <hr> -->
  </div> --%>
  <div class="col-sm-4 Surplus">
   <ul>
    <li><label>剩余可用金额：<br/><b><comm:moneyFormat object="${orgOrderSummary.availableMoney }"></comm:moneyFormat> </b></label></li>
   </ul>
   <div class="help-tip">
		<p>剩余可用额度 = 授信额度 — 冻结金额</p>
	</div>
  </div>
  <div class="col-sm-4 frozen">
   <ul>
    <li><label>冻结金额：<br/><b><comm:moneyFormat object="${orgOrderSummary.frozenMoney }"></comm:moneyFormat> </b></label></li>
   </ul>
   <div class="help-tip">
		<p>冻结金额 = 已提交后台订单总额（未驳回、未拒单、未回款）— 部分回款额度</p>
	</div>
  </div>
  <div class="col-sm-4 Interest">
   <ul>
    <li><label>息费合计：<br/><b><comm:moneyFormat object="${orgOrderSummary.feeSettleTotalMoney }"></comm:moneyFormat></b></label></li>
   </ul>
   
  </div>
  
  <div class="col-sm-4 history_sum">
   <ul>
    <li><label>历史成交金额：<br/><b><comm:moneyFormat object="${orgOrderSummary.hisTotalMoney }"></comm:moneyFormat></b>元</label></li>
   </ul>
   <div class="help-tip">
		<p>历史成交金额 = 已解保的订单总额</p>
	</div>
  </div>
  
  <div class="col-sm-4 applying_sum">
   <ul>
    <li><label>申请中金额：<br/><b><comm:moneyFormat object="${orgOrderSummary.applyTotalMoney }"></comm:moneyFormat></b>元</label></li>
   </ul>
   <div class="help-tip">
		<p>申请中金额 = （未提交以外状态的订单总额）— （已驳回状态的订单总额）— 已拒单的订单总额 — 已放款的订单总额</p>
	</div>
  </div>
  
  <div class="col-sm-4 zaibao_sum">
   <ul>
    <li><label>在途金额：<br/><b><comm:moneyFormat object="${orgOrderSummary.reinsuranceTotalMoney }"></comm:moneyFormat></b>元</label></li>
   </ul>
   <div class="help-tip">
		<p>在途金额 = （已放款未回款的放款总额）</p>
	</div>
  </div>
  
  <div class="col-sm-4 history">
   <ul>
    <li><label>历史成交订单总数：<br/><b>${orgOrderSummary.hisTotal }</b>笔</label></li>
   </ul>
   <div class="help-tip">
		<p>历史成交笔数 = 已解保的订单笔数</p>
	</div>
  </div>
  
  
  <div class="col-sm-4 applying">
   <ul>
    <li><label>申请中笔数：<br/><b>${orgOrderSummary.applyTotal }</b>笔</label></li>
   </ul>
   <div class="help-tip">
		<p>申请笔数 = （未提交以外状态的订单笔数） — （已驳回状态的订单笔数） — 已拒单状态的订单笔数 — 已放款的订单笔数</p>
	</div>
  </div>

  <div class="col-sm-4 zaibao">
   <ul>
    <li><label>在途笔数：<br/><b>${orgOrderSummary.reinsuranceTotal }</b>笔</label></li>
   </ul>
   <div class="help-tip">
		<p>在途笔数 = （已放款状态的订单笔数） — （已回款状态的订单笔数）</p>
	</div>
  </div>
  
  <div class="col-sm-4 current">
   <ul>
    <li><label>当前订单总数：<br/><b>${orgOrderSummary.curTotal }</b>笔</label></li>
   </ul>
   <div class="help-tip">
		<p>当前笔数 = （所有状态订单笔数） — （已关闭状态订单笔数） — 已解保订单笔数</p>
	</div>
  </div>

 </div>
 <!-- <div class="wrapper wrapper-content">
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
<!--  <div class="wrapper wrapper-content">
  <div class="row">
   <div class="col-sm-12">
    <div class="ibox float-e-margins">
     <div class="ibox-title">
      <h5>计算公式参考</h5>
     </div>
     <div class="ibox-content">
      <p>
       <i class="fa"></i> 1.剩余可用额度=授信额度-冻结金额<br>
       <i class="fa"></i> 2.冻结金额=已提交后台订单总额（未驳回、未拒单、未回款）-部分回款额度<br>
       <i class="fa"></i> 3.申请中金额=（未提交以外状态的订单总额）-（已驳回状态的订单总额）-已拒单的订单总额-已放款的订单总额<br>
       <i class="fa"></i> 4.在途金额=（已放款未回款的放款总额）<br>
       <i class="fa"></i> 5.历史成交金额=已解保的订单总额<br>
       <i class="fa"></i> 6.当前笔数=（所有状态订单笔数）-（已关闭状态订单笔数）-已解保订单笔数<br>
       <i class="fa"></i> 7.申请笔数=（未提交以外状态的订单笔数）-（已驳回状态的订单笔数）-已拒单状态的订单笔数-已放款的订单笔数<br>
       <i class="fa"></i> 8.在途笔数=（已放款状态的订单笔数）-（已回款状态的订单笔数）<br>
       <i class="fa"></i> 9.历史成交笔数=已解保的订单笔数<br>
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
