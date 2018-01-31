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
<title>刑事案底核查</title>

</head>

<head>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="个人风险汇总信息" style="padding: 10px;">
    <form action="#" id="refundTailMoneyForm" name="refundTailMoneyForm" method="post">
    
	<style>/*打印*/
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
	.personal .table_css td{
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
    .print_id span, .collect span{ display: block;}
    .print_id span.title{ float: left; border-left: 3px #0073bd solid; font-weight: bold; color: #0073bd; padding-left: 10px;}
    .collect span.title{float: left;}
    .print_id span.bg_id, .collect span.bg_id{ float: right;}
	</style>
	<div class="query">
		<b style="width: 100%; display:block; height: 35px; line-height: 35px; text-align: center;font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">刑事案底核查</b>
		<div class="print_id"><span class="title">查询条件</span><!--<span class="bg_id">报告编号：87788788</span>--></div>
		<table cellpadding="0" cellspacing="0" class="table_css">
			<tr>
				<td width="75">姓名</td>
				<td>${cusCreditReportHis.queryName }</td>
				<td width="75">查询时间</td>
				<td> <fmt:formatDate value="${createDate}" type="date" dateStyle="long"/>  </td>
			</tr>
			<tr>
				<td>证件号码</td>
				<td colspan="3">${cusCreditReportHis.queryDocumentNo}</td>
			</tr>
			
		</table>
	</div>
	
	
	<div class="query">
		<div class="print_id"><span class="title">刑事案底核查</span></div>
		    <c:set var="resultObj" value="${queryResultJson.data.result}"></c:set>
		<c:choose>
		   <c:when test="${empty resultObj}">
			<table cellpadding="0" cellspacing="0" class="table_css">
				<tr>
					<td width="75">查询结果</td>
					<td>根据己提供的查询条件，未能在系统中查得相关信息</td>
				</tr>
			</table>
		   </c:when>
		   <c:when test="${resultObj !=null &&fn:length(resultObj)>0}">
		   <c:forEach var="indexItem" items="${resultObj}" varStatus="indexStatus">
			<b style="font-size: 12px; padding-bottom: 5px; display: block;">信息${indexStatus.index +1} </b>
			<table cellpadding="0" cellspacing="0" class="table_css">
				<tr>
					<td width="75">涉案类型</td>
					<td>${indexItem.crimeType}</td>
				</tr>
				<tr>
					<td width="75">案件数量</td>
					<td>${indexItem.count}</td>
				</tr>
				<tr>
					<td width="75">案件类别</td>
					<td>${indexItem.caseType}</td>
				</tr>
				<tr>
					<td width="75">案件时间段</td>
					<td>${indexItem.casePeriod}</td>
				</tr>
				<tr>
					<td width="75">案件来源</td>
					<td>${indexItem.caseSource}</td>
				</tr>
				<tr>
					<td width="75">案件级别</td>
					<td>${indexItem.caseLevel}</td>
				</tr>
				
			</table>
			</c:forEach>
		   </c:when>
		<c:otherwise>          	
			${resultObj.statusMsg}
		 </c:otherwise>
		</c:choose> 
	</div>
	
    </form>
    
    <p class="jl">
     <a href="javascript:void(0)" class="easyui-linkbutton" id="biuuu_button" iconCls="icon-print" onclick="print()">打印</a>
    </p>
   </div>
  </div>
 </div>

</body>
</html>