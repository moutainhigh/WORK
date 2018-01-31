<%@page import="com.xlkfinance.bms.common.util.StringUtil"%>
<%@page import="com.alibaba.druid.sql.ast.statement.SQLWithSubqueryClause.Entry"%>
<%@page import="com.xlkfinance.bms.web.api.dc.util.JsonUtil"%>
<%@page import="com.xlkfinance.bms.common.util.DateUtils"%>
<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.alibaba.fastjson.*"%>
<%@page import="java.util.*"%>
<%@page import="com.xlkfinance.bms.rpc.customer.*"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<%
	CusCreditReportHis tempcusCreditReportHis = (CusCreditReportHis) request.getAttribute("cusCreditReportHis");
	String queryResult = tempcusCreditReportHis.getQueryResult();
	
	JSONObject tempQueryResultJson = JSONObject.parseObject(queryResult);
	
	request.setAttribute("queryResultJson", tempQueryResultJson);
	//
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
 
<title>个人风险汇总信息</title>


<link id="easyuiTheme" rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/default/easyui.css?v=${v}" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/icon.css?v=${v}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/index.css?v=${v}" />
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.min.js?v=${v}"></script> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.min.js?v=${v}"></script>

<!-- 自己定义的样式和JS扩展 -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery.PrintArea.js?v=${v}" charset="utf-8"></script>
 
 
</head>
<script type="text/javascript">
	
	 /*
	  *关闭窗口
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
<body class="">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="个人风险汇总信息" style="padding: 10px;">
    <form action="#" id="refundTailMoneyForm" name="refundTailMoneyForm" method="post">
	 <div id="myPrintArea">
		<!-- 打印样式 =================================================== -->
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
	    
	    .div_remark{ background: #f9f9f9; padding:5px 10px; border: 1px #ddd solid;}
	    
		</style>
		
		<!-- 打印样式 =================================================== -->
		
		<div class="query">
			<b style="width: 100%; display:block; height: 35px; line-height: 35px; text-align: center;font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">个人风险汇总信息</b>
			<div class="print_id">
				<span class="title">查询条件</span>
				<span class="bg_id">报告编号：${cusCreditReportHis.reportNo }</span>
			</div>
			<table cellpadding="0" cellspacing="0" class="table_css">
				<tr>
					<td width="75">姓名</td>
					<td>${cusCreditReportHis.queryName }</td>
					<td>证件号码</td>
					<td>${cusCreditReportHis.queryDocumentNo }</td>
				</tr>
				<tr>	
					<td>操作员</td>
					<td>${queryResultJson.queryUserID }</td>
					<td width="75">查询时间</td>
					<td>
						 <fmt:formatDate value="${createDate}" type="date" dateStyle="long"/>   
					</td>
				</tr>
				<tr>
					<td>查询机构</td>
					<td colspan="3">${queryResultJson.unitName }</td>
				</tr>
			</table>
		</div>
		
		
		<!-- =============================个人风险信息====================================== -->
		<div class="personal">
		
			<% 
				Object personRiskStatInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("personRiskStatInfo");
				
				String personRiskStatInfoStr = personRiskStatInfoObj.toString();
				JSONObject personRiskStatInfoJson = new JSONObject();
				if (personRiskStatInfoStr instanceof String) {
					personRiskStatInfoStr = personRiskStatInfoStr.replaceAll("\n", "");
					if(personRiskStatInfoStr != null && !personRiskStatInfoStr.equals("")){
						personRiskStatInfoJson = JSONObject.parseObject(personRiskStatInfoStr);
					}
				}
				request.setAttribute("personRiskStatInfo",personRiskStatInfoJson);
			%> 
		
			<c:set var="indexObj" value="${personRiskStatInfo }"></c:set>	
			<div class="print_id"><span class="title">个人风险信息</span></div>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
					<b style="font-size: 12px; padding-bottom: 5px; display: block;">汇总信息</b>
					<table cellpadding="0" cellspacing="0" class="table_css">
						<th align="center">案例信息</th>
						<th align="center">执行信息</th>
						<th align="center">失信信息</th>
						<th align="center">税务行政执法信息</th>
						<th align="center">催收公告信息</th>
						<th align="center">网贷逾期信息</th>
						<tr>
							<td>${indexObj.stat.alCount }</td>
							<td>${indexObj.stat.zxCount }</td>
							<td>${indexObj.stat.sxCount }</td>
							<td>${indexObj.stat.swCount }</td>
							<td>${indexObj.stat.cqggCount }</td>
							<td>${indexObj.stat.wdyqCount }</td>
						</tr>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null }">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		
	</div>
		
   </form>
    <p class="jl">
	    <!--  <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()">关闭</a> -->
	     <a href="javascript:void(0)" class="easyui-linkbutton" id="biuuu_button" iconCls="icon-print" onclick="print()">打印</a>
    </p> 
 
   </div>
 </div>
</div>


</body>
</html>
