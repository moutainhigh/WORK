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
 
<title>个人信用报告</title>



<link id="easyuiTheme" rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/default/easyui.css?v=${v}" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/icon.css?v=${v}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/index.css?v=${v}" />
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.min.js?v=${v}"></script> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.min.js?v=${v}"></script>

<!-- 自己定义的样式和JS扩展 -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery.PrintArea.js?v=${v}" charset="utf-8"></script>
 
 
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
<body class="">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="个人信用报告" style="padding: 10px;">
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
		    .print_id span{ display: block;}
		    .print_id span.title{ float: left; border-left: 3px #0073bd solid; font-weight: bold; color: #0073bd; padding-left: 10px;}
		    .print_id span.bg_id{ float: right;}
		    
   	    	.div_remark{ background: #f9f9f9; padding:5px 10px; border: 1px #ddd solid;}	
		</style>
		
		<!-- 打印样式 =================================================== -->
		
		<div class="query">
			<b style="width: 100%; display:block; height: 35px; line-height: 35px; text-align: center;font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">个人信用报告</b>
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
					<td colspan="3">
						${queryResultJson.unitName }
					</td>
				</tr>
			</table>
		</div>
		
		
		<!-- =============================个人担任法定代表人信息====================================== -->
		<div class="personal">
		
			<% 
				Object artificialNationalInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("artificialNationalInfo");
				
				String artificialNationalInfoStr = artificialNationalInfoObj.toString();
				JSONObject artificialNationalInfoJson = new JSONObject();
				if (artificialNationalInfoStr instanceof String) {
					artificialNationalInfoStr = artificialNationalInfoStr.replaceAll("\n", "");
					if(artificialNationalInfoStr != null && !artificialNationalInfoStr.equals("")){
						artificialNationalInfoJson = JSONObject.parseObject(artificialNationalInfoStr);
					}
				}
				request.setAttribute("artificialNationalInfo",artificialNationalInfoJson);
			%> 
		
<%-- 			<c:set var="indexObj" value="${queryResultJson.cisReport.artificialNationalInfo }"></c:set> --%>	
			<c:set var="indexObj" value="${artificialNationalInfo }"></c:set>	
			<div class="print_id"><span class="title">个人担任法定代表人信息</span></div>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
  					<% 
  						//转化成集合
  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("artificialNationalInfo").get("item");
  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
  					 	request.setAttribute("indexArray", indexArray);
					%>  
				
					<!-- 汇总信息 -->
					<b style="font-size: 12px; padding-bottom: 5px; display: block;">汇总信息</b>
					<table cellpadding="0" cellspacing="0" class="table_css">
						<th align="center">序号</th>
						<th align="center">机构名称</th>
						<th align="center">证件号码</th>
						<th align="center">证件类型</th>
						<th align="center">法定代表人</th>
						<th align="center">机构状态</th>
						<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
							<tr>
								<td width="45">${indexStatus.index+1 }</td>
								<td>${indexItem.corpName }</td>
								<td>
									<c:choose>
										<c:when test="${indexItem.registerNo != ''}">${indexItem.registerNo}</c:when>
										<c:when test="${indexItem.orgCode != ''}">${indexItem.orgCode}</c:when>
									</c:choose>
								
								</td>
								<td align="center">
									<c:choose>
										<c:when test="${indexItem.registerNo != ''}">工商注册号</c:when>
										<c:when test="${indexItem.orgCode != ''}">组织机构代码</c:when>
									</c:choose>
								</td>
								<td align="center" width="75">${indexItem.artificialName }</td>
								<td width="110" align="center">${indexItem.statusCaption }</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null }">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>

		</div>
		<div class="query">
			<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">担任法定代表人机构概要信息</b>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
					<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
						<table cellpadding="0" cellspacing="0" class="table_css" style="padding-bottom:10px;">
							<tr>
								<td width="15%">机构名称</td>
								<td colspan="3" width="85%">${indexItem.corpName}</td>
							</tr>
							<tr>
								<td width="15%">法定代表人</td>
								<td width="35%">${indexItem.artificialName}</td>
								<td width="15%">机构状态</td>
								<td width="35%">${indexItem.statusCaption}</td>
							</tr>
							<tr>
								<td >工商注册号</td>
								<td>${indexItem.registerNo}</td>
								<td>组织机构代码</td>
								<td>${indexItem.orgCode}</td>
							</tr>
							<tr>
								<td>注册资本</td>
								<td>
									<fmt:formatNumber value="${indexItem.registFund}" type="number" pattern="0.00" />万	
								</td>
								<td>企业类型</td>
								<td>${indexItem.corpTypeCaption}</td>
							</tr>
						</table>
					</c:forEach>
				</c:when>
				<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null }">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		
		<!-- =============================个人股权投资信息====================================== -->
		<div class="personal">
		
			<% 
				Object nationalPersonShareholderReportObj = tempQueryResultJson.getJSONObject("cisReport").get("nationalPersonShareholderReport");
				
				String nationalPersonShareholderReportStr = nationalPersonShareholderReportObj.toString();
				JSONObject nationalPersonShareholderReportJson = new JSONObject();
				if (nationalPersonShareholderReportStr instanceof String) {
					nationalPersonShareholderReportStr = nationalPersonShareholderReportStr.replaceAll("\n", "");
					if(nationalPersonShareholderReportStr != null && !nationalPersonShareholderReportStr.equals("")){
						nationalPersonShareholderReportJson = JSONObject.parseObject(nationalPersonShareholderReportStr);
					}
				}
				request.setAttribute("nationalPersonShareholderReport",nationalPersonShareholderReportJson);
			%> 
		
			<c:set var="indexObj" value="${nationalPersonShareholderReport }"></c:set>	

			<div class="print_id"><span class="title">个人股权投资信息</span></div>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
				
					<!-- 设置变量信息 -->
  					<% 
  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("nationalPersonShareholderReport").get("item");
  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
  					 	/**是否是本人*/
  					 	boolean isSelf = true;
  					 	double tatalContributiveFund = 0 ;
  					 	Map<String,Object> tempNameMap = new HashMap<String,Object>();
  					 	for(int i = 0 ; i < indexArray.size() ; i ++  ){
  					 		JSONObject tempObj = indexArray.getJSONObject(i);
  					 		Object nameObj = tempObj.get("name");
  					 		tatalContributiveFund += tempObj.getDoubleValue("contributiveFund");
  					 		tempNameMap.put(nameObj.toString(),nameObj);
  					 	}
  					 	
  					 	//投资人姓名
  					 	String shareName = StringUtil.mapKeyToString(tempNameMap);
  					 	//如果不包含 ，则说明不一致
  					 	if(!shareName.contains(tempcusCreditReportHis.queryName)){
  					 		isSelf = false;
  					 	}
  					 	
  					 	request.setAttribute("indexArray", indexArray);
  					 	request.setAttribute("tatalContributiveFund", tatalContributiveFund);
  					 	request.setAttribute("isSelf", isSelf);
  					 	request.setAttribute("shareName", shareName);
					%>  
				
					<!-- 汇总信息 -->
					<b style="font-size: 12px; padding-bottom: 5px; display: block;">汇总信息</b>
					<table cellpadding="0" cellspacing="0" class="table_css">
						<tr>
							<td width="75" style="background: #f9f9f9;">投资人姓名</td>
							<td colspan="6">
								${shareName }
								<c:if test="${isSelf == false }">（与输入姓名不一致）</c:if>
							</td>
						</tr>
						<tr>
							<td width="15%" style="background: #f9f9f9;">累计投资总家数</td>
							<td width="35%" colspan="2">${fn:length(indexArray)}</td>
							<td width="15%" style="background: #f9f9f9;">累计投资总金额</td>
							<td width="35%" colspan="3">${tatalContributiveFund }万元</td>
						</tr>
						<th align="center">序号</th>
						<th align="center">被投资机构名称</th>
						<th align="center">注册资本（万元）</th>
						<th align="center">认缴出资额（万元）</th>
						<th align="center">出资比例（%）</th>
						<th align="center">币种</th>
						<th align="center">机构状态</th>
						
						<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
							<tr>
								<td>${indexStatus.index + 1 }</td>
								<td>${indexItem.corpName}</td>
								<td width="75" align="center" >
									<c:if test="${not empty indexItem.registFund }">
										<fmt:formatNumber value="${indexItem.registFund}" type="number" pattern="0.00" />
									</c:if>
								</td>
								<td width="75" align="center">
									<c:if test="${not empty indexItem.contributiveFund }">
										<fmt:formatNumber value="${indexItem.contributiveFund}" type="number" pattern="0.00" />
									</c:if>
								</td>
								<td width="75" align="center">
									<c:if test="${not empty indexItem.contributivePercent }">
										<fmt:formatNumber value="${indexItem.contributivePercent}" type="number" pattern="0.00" />%
									</c:if>
								</td>
								<td width="75" align="center">人民币</td>
								<td width="75" align="center">${indexItem.statusCaption}</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null}">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		<div class="query">
			<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">被投资机构概要信息</b>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
					<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
						<table cellpadding="0" cellspacing="0" class="table_css" style="padding-bottom:10px;">
							<tr>
								<td width="15%">机构名称</td>
								<td width="35%">${indexItem.corpName }</td>
								<td width="15%">机构状态</td>
								<td width="35%">${indexItem.statusCaption }</td>
							</tr>
							<tr>
								<td>注册资本</td>
								<td>
									<c:if test="${not empty indexItem.registFund }">
										<fmt:formatNumber value="${indexItem.registFund}" type="number" pattern="0.00" />万
									</c:if>
								</td>
								<td>币种</td>
								<td>人民币</td>
							</tr>
							<tr>
								<td >工商注册号</td>
								<td>${indexItem.registNo }</td>
								<td>企业类型</td>
								<td>${indexItem.corpTypeCaption }</td>
							</tr>
						</table>
					</c:forEach>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null}">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		<!-- =============================个人担任高管信息====================================== -->
		<div class="personal">
		
			<% 
				Object personTopManagerInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("personTopManagerInfo");
				
				String personTopManagerInfoStr = personTopManagerInfoObj.toString();
				JSONObject personTopManagerInfoJson = new JSONObject();
				if (personTopManagerInfoStr instanceof String) {
					personTopManagerInfoStr = personTopManagerInfoStr.replaceAll("\n", "");
					if(personTopManagerInfoStr != null && !personTopManagerInfoStr.equals("")){
						personTopManagerInfoJson = JSONObject.parseObject(personTopManagerInfoStr);
					}
				}
				request.setAttribute("personTopManagerInfo",personTopManagerInfoJson);
			%> 
		
			<c:set var="indexObj" value="${personTopManagerInfo }"></c:set>	
			<div class="print_id"><span class="title">个人担任高管信息</span></div>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
				
					<!-- 设置变量信息 -->
  					<% 
  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("personTopManagerInfo").get("item");
  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
  					 	request.setAttribute("indexArray", indexArray);
					%>  
					<!-- 汇总信息 -->
					<b style="font-size: 12px; padding-bottom: 5px; display: block;">汇总信息</b>
					
					<table cellpadding="0" cellspacing="0" class="table_css">
						<th align="center" >序号</th>
						<th align="center" >机构名称</th>
						<th align="center" >任职人姓名</th>
						<th align="center" >职务</th>
						<th align="center" >机构状态</th>
					
						<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
							<tr>
								<td>${indexStatus.index + 1 }</td>
								<td>${indexItem.corpName }</td>
								<td width="75" align="center">${indexItem.name }</td>
								<td width="75" align="center">${indexItem.positionCaption }</td>
								<td width="110" align="center">${indexItem.statusCaption }</td>
							</tr>
						</c:forEach>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null}">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		
		<div class="query">
			<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">任职机构概要信息</b>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
					<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
						<table cellpadding="0" cellspacing="0" class="table_css" style="padding-bottom:10px;">
							<tr>
								<td width="15%">机构名称</td>
								<td width="35%">${indexItem.corpName }</td>
								<td width="15%">机构状态</td>
								<td width="35%">${indexItem.statusCaption }</td>
							</tr>
							<tr>
								<td>任职人姓名</td>
								<td>${indexItem.name }</td>
								<td>职务</td>
								<td>${indexItem.positionCaption }</td>
								
							</tr>
							<tr>
								<td>注册资本</td>
								<td>
									<c:if test="${not empty indexItem.registFund }">
										<fmt:formatNumber value="${indexItem.registFund}" type="number" pattern="0.00" />万
									</c:if>
								</td>
								<td>企业类型</td>
								<td>${indexItem.corpTypeCaption }</td>
								
							</tr>
							<tr>
								<td >工商注册号</td>
								<td>${indexItem.registNo }</td>
								<td>组织机构代码</td>
								<td></td>
							</tr>
						</table>
					</c:forEach>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null}">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		
		
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
