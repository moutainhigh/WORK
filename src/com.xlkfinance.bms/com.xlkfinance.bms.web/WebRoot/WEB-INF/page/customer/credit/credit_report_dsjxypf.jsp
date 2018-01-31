<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@page import="com.alibaba.fastjson.*"%>
<%@page import="java.util.*"%>
<%@page import="com.xlkfinance.bms.rpc.customer.*"%>
<%@page import="com.xlkfinance.bms.common.util.DateUtils"%>
<%
	CusCreditReportHis tempcusCreditReportHis = (CusCreditReportHis) request.getAttribute("cusCreditReportHis");
	String queryResult = tempcusCreditReportHis.getQueryResult();
	
	JSONObject tempQueryResultJson = JSONObject.parseObject(queryResult);
	request.setAttribute("queryResultJson", tempQueryResultJson);
	Date createDate = DateUtils.stringToDate(tempcusCreditReportHis.getCreateTime());
	request.setAttribute("createDate", createDate);
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">

<link id="easyuiTheme" rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/default/easyui.css?v=${v}" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/icon.css?v=${v}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/index.css?v=${v}" />
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.min.js?v=${v}"></script> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.min.js?v=${v}"></script>

<!-- 自己定义的样式和JS扩展 -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery.PrintArea.js?v=${v}" charset="utf-8"></script>
<title>FICO大数据信用评分</title>

</head>
<script type="text/javascript">
	
 
	/**
	 * 关闭窗口
	 */
	/* function closeWindow() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
		parent.$('#layout_center_tabs').tabs('close', index);
	} */
	
	function print() {
		$("#myPrintArea").printArea();
	}

</script>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="大数据信用评分" style="padding: 10px;">
    <div id="myPrintArea">
	<style>/*打印*/
		ul,li{ list-style-type: none; margin: 0; padding: 0;}
	.table_css{
		width: 100%;
		border-top: 1px #ddd solid;
		border-right: 1px #ddd solid;
	}
	.table_css th,
	.table_css td {
		padding: 7px 5px;
		font-size: 12px;
		border-bottom: 1px #ddd solid;
		border-left: 1px #ddd solid;
	}
	.table_css th{background: #f9f9f9;}
	.table_css td:nth-child(odd){
		 text-align: center;
	}
	.query .table_css td:nth-child(odd){
		background: #f9f9f9;
	}
	.print_id {
    	font-size: 14px;
    	padding: 20px 0 10px 0;
    	border-bottom: 1px #0073bd solid; 
    	height: auto;
    	overflow: hidden;
    	margin-bottom: 15px;
    }
    .collect {
    	font-size: 14px;
    	height: auto;
    	overflow: hidden;
    	padding-bottom: 10px;
    }
    .personal p{ font-size: 14px;}
    .print_id span, .collect span{ display: block;}
    .print_id span.title{ float: left; border-left: 3px #0073bd solid; font-weight: bold; color: #0073bd; padding-left: 10px;}
    .collect span.title{float: left;}
    .print_id span.bg_id, .collect span.bg_id{ float: right;}
    .query h1{ text-align: center;padding:5px 0 15px 0; color: red; font-size:24px; font-family: "微软雅黑"; font-weight: normal; position: relative;}
    .query h1 .label{ padding: 5px; border: 1px red solid; font-size: 14px;border-radius: 5px; color: red; position: absolute;top:15px; right: 15px;}
   	.fx_bg {font-family: '微软雅黑'; border: 1px #ddd solid; height: auto;overflow: hidden;}
    .fx_pf{float:left;width: 27%; font-size:14px ; height: auto;overflow: hidden; text-align: center;}
    .fx_pf b{color: red;font-size:14px ; }
    .fx_dj{float:left; width: 70%; margin-bottom: 15px;}
    .fx_dj ul{position: relative; width: 100%;padding-bottom: 55px;}
    .fx_dj ul li{ float: left; color: #fff; padding: 3px 0; background: #ccc; text-align: center;}
    .fx_dj ul li.active{ background: red;}
    .fx_dj ul i{position: absolute; top:0;font-size: 12px;font-style:normal}
    .fx_mz{position: relative;border: 1px #ddd solid; height: auto; width: 95%; margin:0 auto 15px auto; overflow: hidden;clear: both;}
    #comment_bubble {
	    width: 100px;
	    height: 50px;
	    line-height: 50px;
	    font-size: 16px;
	    padding: 10px 0;
	    background: #eee;
	    position:absolute;
	    top:0;
	    text-align: center;
	}
  
	#comment_bubble:before {
	    content: "";
	    width: 0;
	    height: 0;
	    left: 100px;
	    top: 0;
	    position: absolute;
	    border-top: 35px solid transparent;
	    border-left: 20px solid #eee;
	    border-bottom: 35px solid transparent;
	}
	.fx_mz ul{ padding: 5px 0 5px 140px;} 
	.fx_mz ul li{ display: inline-block; height: 28px; line-height: 28px; width: 30%; font-size: 12px;} 
	.fx_mz ul li b{ color: #666;  padding-right:10px; font-size: 20px;}
	/*模拟对角线*/ 
	.out{ 
	border-top:40px #D6D3D6 solid;/*上边框宽度等于表格第一行行高*/ 
	width:0px;/*让容器宽度为0*/ 
	height:0px;/*让容器高度为0*/ 
	border-left:145px #BDBABD solid;/*左边框宽度等于表格第一行第一格宽度*/ 
	position:relative;/*让里面的两个子容器绝对定位*/ 
	} 
	.out b{font-style:normal;display:block;position:absolute;top:-40px;left:-40px;width:35px;} 
	.out em{font-style:normal;display:block;position:absolute;top:-25px;left:-130px;width:55x;} 
	</style>
	
	
	<div class="query">
		<b style="width: 100%; display:block; height: 35px; line-height: 35px; text-align: center;font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">FICO大数据信用评分</b>
		<div class="print_id">
			<span class="title">查询条件</span>
			<c:if test="${ not empty queryResultJson}">
				<span class="bg_id">报告编号：${cusCreditReportHis.reportNo }</span>
			</c:if>
		
		</div>
		<table cellpadding="0" cellspacing="0" class="table_css">
			<tr>
				<td width="75">姓名</td>
				<td>${cusCreditReportHis.queryName }</td>
				<td width="75">查询时间</td>
				<td> <fmt:formatDate value="${createDate}" type="date" dateStyle="long"/>  </td>
			</tr>
			<tr>
				<td>证件号码</td>
				<td>${cusCreditReportHis.queryDocumentNo}</td>
				<td>手机号码</td>
				<td>${cusCreditReportHis.queryPhone}</td>
			</tr>
			<tr>
				<td>有无人行征信</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${ cusCreditReportHis.queryPbocStatus == 'true' }">有</c:when>
						<c:when test="${ cusCreditReportHis.queryPbocStatus == 'false' }">无</c:when>
						<c:when test="${ cusCreditReportHis.queryPbocStatus == 'und' }">不确定</c:when>
						<c:otherwise>--</c:otherwise>
					</c:choose>
				</td>
			</tr>
			
		</table>
	</div>
	
	<div class="query">
		<div class="print_id"><span class="title">FICO大数据信用评分</span></div>
		<c:choose>
		   <c:when test="${empty queryResultJson}">
			<table cellpadding="0" cellspacing="0" class="table_css">
				<tr>
					<td width="75">查询结果</td>
					<td>根据己提供的查询条件，未能在系统中查得相关信息</td>
				</tr>
			</table>
		   </c:when>
		<c:otherwise>  
			<table cellpadding="0" cellspacing="0" class="table_css">
				<tr>
					<td width="75">评分</td>
					<td style="color: red; font-size: 24px; font-weight: bold;">${queryResultJson.score}</td>
				</tr>
				<tr>
					<td width="75">原因码</td>
					<td colspan="4" style="text-align: left; padding-left: 15px;font-size: 14px;">
						
						<c:set var="reasonList" value="" />
						
 
						<c:if test="${queryResultJson.reason != null }">
								<c:set var="reasonList" value="${fn:split(queryResultJson.reason,',')}" />
						</c:if> 
					 	<c:if test="${reasonList != '' }">
							<c:forEach var="indexItem" items="${reasonList }" varStatus="indexStatus">
								<p>原因码${indexStatus.index +1 }：${indexItem }，
									<c:choose>
										<c:when test="${indexItem == '1001' }">贷记借记账户不活跃</c:when>
										<c:when test="${indexItem == '1002' }">借记账户账龄过短或未知</c:when>
										<c:when test="${indexItem == '1003' }">贷记账户账龄过短或未知</c:when>
										<c:when test="${indexItem == '1004' }">入网时长过短或未知</c:when>
										<c:when test="${indexItem == '1005' }">借记账户余额</c:when>
										<c:when test="${indexItem == '1006' }">借记账户入账金额</c:when>
										<c:when test="${indexItem == '1007' }">借记账户出账金额</c:when>
										<c:when test="${indexItem == '1008' }">借记账户出账频率</c:when>
										<c:when test="${indexItem == '1009' }">借记账户取现金额</c:when>
										<c:when test="${indexItem == '1010' }">借记账户取现频率</c:when>
										<c:when test="${indexItem == '1011' }">贷记账户余额</c:when>
										<c:when test="${indexItem == '1012' }">贷记账户取现频率</c:when>
										<c:when test="${indexItem == '1013' }">贷记账户额度使用</c:when>
										<c:when test="${indexItem == '1014' }">贷记账户线上消费频率</c:when>
										<c:when test="${indexItem == '1015' }">贷记账户分期消费</c:when>
										<c:when test="${indexItem == '1016' }">借记账户使用情况</c:when>
										<c:when test="${indexItem == '1017' }">近期活跃过的P2P平台数</c:when>
										<c:when test="${indexItem == '1018' }">近期在P2P平台上的申请次数</c:when>
										<c:when test="${indexItem == '1019' }">最近一次P2P平台贷款申请距今时间短</c:when>
										<c:when test="${indexItem == '1020' }">最近一次P2P平台注册距今时间短</c:when>
										<c:when test="${indexItem == '1021' }">近期在P2P平台上的注册次数</c:when>
										<c:when test="${indexItem == '1022' }">近期在P2P平台有过逾期</c:when>
										<c:when test="${indexItem == '1023' }">高风险投资交易次数</c:when>
										<c:when test="${indexItem == '1024' }">P2P投资交易次数</c:when>
										<c:when test="${indexItem == '1025' }">近期电商消费情况</c:when>
										<c:when test="${indexItem == '1026' }">近期商旅情况</c:when>
										<c:when test="${indexItem == '1027' }">近期休闲娱乐情况</c:when>
										<c:when test="${indexItem == '1028' }">近期贷记账户取现情况</c:when>
										<c:when test="${indexItem == '1029' }">贷记借记账户交易频率</c:when>
										<c:when test="${indexItem == '1030' }">夜间消费情况</c:when>
										<c:when test="${indexItem == '1031' }">近期消费情况</c:when>
										<c:when test="${indexItem == '1032' }">贷记账户还款情况</c:when>
										<c:when test="${indexItem == '1033' }">近期付费电视使用情况</c:when>
										<c:when test="${indexItem == '1034' }">近期借记贷记账户活跃度</c:when>
										<c:when test="${indexItem == '1035' }">理财账户账龄短或未知</c:when>
										<c:when test="${indexItem == '1036' }">理财账户夜间交易次数</c:when>
										<c:when test="${indexItem == '1037' }">理财账户出账交易次数</c:when>
										<c:when test="${indexItem == '1038' }">理财账户余额</c:when>
										<c:when test="${indexItem == '1039' }">理财账户不活跃</c:when>
										<c:when test="${indexItem == '1040' }">最新注册的理财账户距今时间短或未知</c:when>
									</c:choose>
								</p>
							</c:forEach>
						</c:if> 
					</td>
				</tr>
			</table>        	
		 </c:otherwise>
		</c:choose> 
	</div>
	
	<c:if test="${not empty queryResultJson }">
		<div class="personal">
			<div class="print_id"><span class="title">业务应用审批建议</span></div>
			<div style="background: #f9f9f9; padding:10px; border: 1px #ddd solid;">
				<c:choose>
					<c:when test="${ queryResultJson.recAction == 'autoApprove' }"><b style="color: gray;">自动通过</b></c:when>
					<c:when test="${ queryResultJson.recAction == 'autoDecline ' }"><b style="color: red;">自动拒绝</b></c:when>
					<c:when test="${ queryResultJson.recAction == 'approve' }"><b style="color: gray;">建议通过</b></c:when>
					<c:when test="${ queryResultJson.recAction == 'decline' }"><b style="color: red;">建议拒绝</b></c:when>
					<c:when test="${ queryResultJson.recAction == 'refer' }"><b style="color:  blue;">人工审核</b></c:when>
					<c:when test="${ queryResultJson.recAction != '' }"><b style="color:  blue;">${queryResultJson.recAction }</b></c:when>
					<c:otherwise>无</c:otherwise>
				</c:choose>
			</div> 
		</div>
	</c:if>
		   
</div>
    
    <p class="jl">
     <a href="javascript:void(0)" class="easyui-linkbutton" id="biuuu_button" iconCls="icon-print" onclick="print()">打印</a>
    </p>
   </div>
  </div>
 </div>

</body>
</html>