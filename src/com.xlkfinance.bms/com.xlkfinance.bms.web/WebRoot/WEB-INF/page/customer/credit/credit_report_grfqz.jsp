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
	String queryResult = tempcusCreditReportHis.queryResult;
	
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
 
<title>个人反欺诈报告</title>


<link id="easyuiTheme" rel="stylesheet" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/default/easyui.css?v=${v}" type="text/css"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/themes/icon.css?v=${v}"></link>
<link rel="stylesheet" type="text/css" href="${ctx}/p/xlkfinance/css/index.css?v=${v}" />
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.min.js?v=${v}"></script> 
<script type="text/javascript" src="${ctx}/p/xlkfinance/plug-in/jquery-easyui-1.4/jquery.easyui.min.js?v=${v}"></script>

<!-- 自己定义的样式和JS扩展 -->
<script type="text/javascript" src="${ctx}/p/xlkfinance/js/jquery.PrintArea.js?v=${v}" charset="utf-8"></script>
 

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
<body class="">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="个人反欺诈分析" style="padding: 10px;">
    <form action="#" id="refundTailMoneyForm" name="refundTailMoneyForm" method="post">
	 <div id="myPrintArea">
		<!-- 打印样式 =================================================== -->
		<style>
			/*打印*/
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
			    height: 100%;
			    font-size: 16px;
			    background: #eee;
			    position:absolute;
			    top:0;
			    text-align: center;
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
			
		    .div_remark{ background: #f9f9f9; padding:5px 10px; border: 1px #ddd solid;}
		</style>
		
		<!-- 打印样式 =================================================== -->
		
		<div class="query">
			<b style="width: 100%; display:block; height: 35px; line-height: 35px; text-align: center;font-size: 18px; font-family: 'Microsoft YaHei', '微软雅黑';">个人反欺诈分析</b>
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
					<td>手机号码</td>
					<td colspan="3">${cusCreditReportHis.queryPhone }</td>
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
		
		<!-- =============================身份认证====================================== -->
		<div class="query">
			<div class="print_id"><span class="title">身份认证</span></div>
			
			<% 
				Object policeCheckInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("policeCheckInfo");
				
				String policeCheckInfoStr = policeCheckInfoObj.toString();
				JSONObject policeCheckInfoJson = new JSONObject();
				if (policeCheckInfoStr instanceof String) {
					policeCheckInfoStr = policeCheckInfoStr.replaceAll("\n", "");
					if(policeCheckInfoStr != null && !policeCheckInfoStr.equals("")){
						policeCheckInfoJson = JSONObject.parseObject(policeCheckInfoStr);
					}
				}
				request.setAttribute("mobileCheckInfo",policeCheckInfoJson);
			%> 
			
			<c:set var="indexObj" value="${mobileCheckInfo }"></c:set>
				<c:choose>
					<c:when test="${indexObj.treatResult == '1' }">
						<table cellpadding="0" cellspacing="0" class="table_css">
							<tr>
								<td width="75">姓名</td>
								<td colspan="3">
									${indexObj.item.name }
								</td>
							</tr>
							<tr>
								<td width="75">证件号码</td>
								<td colspan="3">${indexObj.item.documentNo }</td>
							</tr>
							<tr>
								<td width="75">认证结果</td>
								<td colspan="3" >
									<c:if test="${indexObj.item.result == '1' }">一致</c:if>
									<c:if test="${indexObj.item.result == '2' }">
										<font style="color: red;">不一致</font>
									</c:if>
								</td>
							</tr>
						</table>
					</c:when>
					<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
						<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
					</c:when>
					<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
				</c:choose>  
			
		</div>
		
		<!-- =============================手机号码核查结果====================================== -->
		<div class="personal">
			<div class="print_id"><span class="title">手机号码核查结果</span></div>
			
			<% 
				Object mobileCheckInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("mobileCheckInfo");
				
				String mobileCheckInfoStr = mobileCheckInfoObj.toString();
				JSONObject mobileCheckInfoJson = new JSONObject();
				if (mobileCheckInfoStr instanceof String) {
					mobileCheckInfoStr = mobileCheckInfoStr.replaceAll("\n", "");
					if(mobileCheckInfoStr != null && !mobileCheckInfoStr.equals("")){
						mobileCheckInfoJson = JSONObject.parseObject(mobileCheckInfoStr);
					}
				}
				request.setAttribute("mobileCheckInfo",mobileCheckInfoJson);
			%> 
			<c:set var="indexObj" value="${mobileCheckInfo }"></c:set>
			<table cellpadding="0" cellspacing="0" class="table_css">
				<tr>
					<td width="75" rowspan="3" style="background: #f9f9f9;">核查条件</td>
					<td width="75" align="center" style="background: #f9f9f9;">姓名</td>
					<td style="text-align: left;">${cusCreditReportHis.queryName }</td>
				</tr>
				<tr>
					<td style="background: #f9f9f9;">证件号码</td>
					<td>${cusCreditReportHis.queryDocumentNo }</td>
				</tr>
				<tr>
					<td style="background: #f9f9f9;">手机号码</td>
					<td >${cusCreditReportHis.queryPhone }</td>
				</tr>
				<tr>
					<td style="background: #f9f9f9;">查询结果</td>
					<td colspan="2" >
						<c:choose>
							<c:when test="${indexObj.treatResult == '1' }">
							
								
								<c:if test="${indexObj.item.nameCheckResult != ''}">
									姓名核查结果：${indexObj.item.nameCheckResult};</br>
								</c:if>
								<c:if test="${indexObj.item.documentNoCheckResult != ''}">
									证件号码核查结果：${indexObj.item.documentNoCheckResult};</br>
								</c:if>
								<c:if test="${indexObj.item.phoneCheckResult != ''}">
									手机号码核查结果：${indexObj.item.phoneCheckResult};
								</c:if>
							 
							</c:when>
							<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">根据己提供的查询条件，未能在系统中查得相关信息</c:when>
							<c:otherwise>
								${indexObj.errorMessage}
							</c:otherwise>
						</c:choose>
					</td>
				</tr>
			</table>
		</div>
		
		<!-- =============================反欺诈分析综述====================================== -->
		<div class="personal">

			<c:set var="indexObj" value="${queryResultJson.cisReport.personAntiSpoofingDescInfo }"></c:set>
			<div class="print_id"><span class="title">反欺诈分析综述</span></div>
			<div class="div_remark"> 
				<c:choose>
					<c:when test="${indexObj.treatResult == '1' and indexObj.personAntiSpoofingDesc !='' }">
						<% 
							//request.setAttribute("vEnter", "\n"); 
							String personAntiSpoofingDesc = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("personAntiSpoofingDescInfo").getString("personAntiSpoofingDesc");
							personAntiSpoofingDesc = StringUtil.replayFirstAndEnd(personAntiSpoofingDesc, "\n\t\t").replace("\t", "").replace("\n", "</br>");
							request.setAttribute("personAntiSpoofingDesc", personAntiSpoofingDesc); 
						%> 
 						<p>
 						<%-- <c:set var="personAntiSpoofingDesc" value="${fn:replace(indexObj.personAntiSpoofingDesc, vEnter, '</br>')}" /> --%>
 							${personAntiSpoofingDesc}
 						</p>
					</c:when>
					<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">根据己提供的查询条件，未能在系统中查得相关信息</c:when>
					<c:otherwise>
						<p>${indexObj.errorMessage}</p>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		
		<!-- =============================反欺诈风险评分====================================== -->
		<div class="query">
		
			<c:set var="indexObj" value="${queryResultJson.cisReport.personAntiSpoofingInfo }"></c:set>
			<div class="print_id"><span class="title">反欺诈风险评分</span></div>
			<div class="fx_bg">
				
				<c:choose>
					<c:when test="${indexObj.treatResult == '1' }">
 						<h1>检查到${indexObj.riskLevel}风险信息<span class="label">${indexObj.suggest}</span></h1>
						<div class="fx_pf">
							风险评分：<b>${indexObj.riskScore}分</b>
						</div>
						<div class="fx_dj">
							<ul>
								<li <c:if test="${indexObj.riskLevel =='低'}">class="active"</c:if> style="width: 50%;">低</li>
								<li <c:if test="${indexObj.riskLevel =='中'}">class="active"</c:if> style="width: 25%;">中</li>
								<li <c:if test="${indexObj.riskLevel =='高'}">class="active"</c:if> style="width: 25%;">高</li>
								
								<i style="top: 32px; left:-2px;">0</i>
								<i style="left:0; border-left: 1px #ddd solid; padding-top: 15px;">&nbsp;</i>
								<i style="left:50%; border-left: 1px #ddd solid; padding-top: 15px;">&nbsp;</i>
								<i style="left:75%; border-left: 1px #ddd solid; padding-top: 15px;">&nbsp;</i>
								<i style="right:0;border-right: 1px #ddd solid; padding-top: 15px;">&nbsp;</i>
								<i style="top: 32px; right:-10px;">100</i>
							</ul>
						</div>
						<div class="fx_mz">
							<div id="comment_bubble">
								<div style="position: relative;top: 35%;">
									<c:if test="${indexObj.hitTypes == '' && indexObj.hitTypes == null }">未命中</c:if>
									<c:if test="${indexObj.hitTypes != '' }">命中</c:if>
								</div>
							</div>
							<ul>
								<c:if test="${indexObj.hitTypes != ''  }">
									<c:set var="hitTypesList" value="${fn:split(indexObj.hitTypes,'，')}"/>  
							
									<c:forEach var="indexItem" items="${hitTypesList }">
										<li><b>·</b>${indexItem }</li>
									</c:forEach>
								</c:if>
							</ul>
						</div>
						
					</c:when>
					<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
						<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
					</c:when>
					<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
				</c:choose>
				<p style="background: #f9f9f9; padding: 10px; font-size: 12px;" >
					提示信息：<br />
					<b style="color: #666;  padding-right:10px; font-size: 20px;">·</b>风险评分值介于0至100之间，分值越高，风险值越高，代表欺诈风险越高。<br />
					<b style="color: #666;  padding-right:10px; font-size: 20px;">·</b>风险评分将分数0至100划分为三级，该客户处于第三级。
				</p>
			</div>
		</div>
		
		<!-- =============================手机号码信息====================================== -->
		<div class="personal">
			<% 
				Object mobileStatusInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("mobileStatusInfo");
				
				String mobileStatusInfoStr = mobileStatusInfoObj.toString();
				JSONObject mobileStatusInfoJson = new JSONObject();
				if (mobileStatusInfoStr instanceof String) {
					mobileStatusInfoStr = mobileStatusInfoStr.replaceAll("\n", "");
					if(mobileStatusInfoStr != null && !mobileStatusInfoStr.equals("")){
						mobileStatusInfoJson = JSONObject.parseObject(mobileStatusInfoStr);
					}
				}
				request.setAttribute("mobileStatusInfo",mobileStatusInfoJson);
			%> 
			<c:set var="indexObj" value="${mobileStatusInfo }"></c:set>
			<div class="print_id"><span class="title">手机号码信息</span></div>
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
					<table cellpadding="0" cellspacing="0" class="table_css">
		 				<tr>
							<td style="background: #f9f9f9; width: 90px;">手机号码</td>
							<td>${cusCreditReportHis.queryPhone }</td>
						</tr>
						<tr>
							<td style="background: #f9f9f9;">运营商名称</td>
							<td>
								<c:if test="${indexObj.item.operator == '1' }">中国电信</c:if>
								<c:if test="${indexObj.item.operator == '2' }">中国移动</c:if>
								<c:if test="${indexObj.item.operator == '3' }">中国联通</c:if>
							</td>
						</tr>
						<tr>
							<td style="background: #f9f9f9;">号码归属地</td>
							<td>${indexObj.item.areaInfo}</td>
						</tr>
						<tr>
							<td style="background: #f9f9f9;">手机号码状态</td>
							<td>
								<c:if test="${indexObj.item.phoneStatus == '1' }">正常在用</c:if>
								<c:if test="${indexObj.item.phoneStatus == '2' }">停机</c:if>
								<c:if test="${indexObj.item.phoneStatus == '3' }">未启用</c:if>
								<c:if test="${indexObj.item.phoneStatus == '4' }">已销号</c:if>
								<c:if test="${indexObj.item.phoneStatus == '5' }">其他</c:if>
								<c:if test="${indexObj.item.phoneStatus == '6' }">预销号</c:if>
							</td>
						</tr>
						<tr>
							<td style="background: #f9f9f9;">手机号在网时长</td>
							<td>${indexObj.item.timeLength}</td>
						</tr>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		<!-- =============================高危人员名单====================================== -->
		<div class="personal">
			<% 
				Object personRiskAssessObj = tempQueryResultJson.getJSONObject("cisReport").get("personRiskAssess");
				
				String personRiskAssessStr = personRiskAssessObj.toString();
				JSONObject personRiskAssessJson = new JSONObject();
				if (personRiskAssessStr instanceof String) {
					personRiskAssessStr = personRiskAssessStr.replaceAll("\n", "");
					if(personRiskAssessStr != null && !personRiskAssessStr.equals("")){
						personRiskAssessJson = JSONObject.parseObject(personRiskAssessStr);
					}
				}
				request.setAttribute("personRiskAssess",personRiskAssessJson);
			%> 
			<c:set var="indexObj" value="${personRiskAssess }"></c:set>			
			<div class="print_id"><span class="title">高危人员名单</span></div>
				<c:choose>
					<c:when test="${indexObj.treatResult == '1' }">
						<table cellpadding="0" cellspacing="0" class="table_css">
							<tr>
								<td style="background: #f9f9f9;  width: 180px;">身份信息是否命中高危人员名单</td>
								<td>
									<c:if test="${indexObj.checkResult == '1' }">是</c:if>
									<c:if test="${indexObj.checkResult == '2' }">否</c:if>
								</td>
							</tr>
						</table>
					</c:when>
					<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
						<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
					</c:when>
					<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
				</c:choose>
		</div>
		<!-- =============================羊毛党名单====================================== -->
		<div class="personal">
			<% 
				Object econnoisserurInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("econnoisserurInfo");
				
				String econnoisserurInfoStr = econnoisserurInfoObj.toString();
				JSONObject econnoisserurInfoJson = new JSONObject();
				if (econnoisserurInfoStr instanceof String) {
					econnoisserurInfoStr = econnoisserurInfoStr.replaceAll("\n", "");
					if(econnoisserurInfoStr != null && !econnoisserurInfoStr.equals("")){
						econnoisserurInfoJson = JSONObject.parseObject(econnoisserurInfoStr);
					}
				}
				request.setAttribute("econnoisserurInfo",econnoisserurInfoJson);
			%> 
			<c:set var="indexObj" value="${econnoisserurInfo }"></c:set>	
			<div class="print_id"><span class="title">羊毛党名单</span></div>
				<c:choose>
					<c:when test="${indexObj.treatResult == '1' }">
						<table cellpadding="0" cellspacing="0" class="table_css">
							<tr>
								<td style="background: #f9f9f9;  width: 180px;">是否命中羊毛党名单</td>
								<td>
									<c:if test="${indexObj.state == '1' }">是</c:if>
									<c:if test="${indexObj.state == '0' }">否</c:if>
								</td>
							</tr>
						</table>
					</c:when>
					<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
						<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
					</c:when>
					<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
				</c:choose>
		</div>
		
		<!-- =============================欺诈风险名单====================================== -->
		<div class="personal">
		
			<% 
				Object fraudRiskInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("fraudRiskInfo");
				
				String fraudRiskInfoStr = fraudRiskInfoObj.toString();
				JSONObject fraudRiskInfoJson = new JSONObject();
				if (fraudRiskInfoStr instanceof String) {
					fraudRiskInfoStr = fraudRiskInfoStr.replaceAll("\n", "");
					if(fraudRiskInfoStr != null && !fraudRiskInfoStr.equals("")){
						fraudRiskInfoJson = JSONObject.parseObject(fraudRiskInfoStr);
					}
				}
				request.setAttribute("fraudRiskInfo",fraudRiskInfoJson);
			%> 
		
			<c:set var="indexObj" value="${fraudRiskInfo }"></c:set>	
			<div class="print_id"><span class="title">欺诈风险名单</span></div>
				<c:choose>
					<c:when test="${indexObj.treatResult == '1' }">
						<table cellpadding="0" cellspacing="0" class="table_css">
							<tr>
								<td style="background: #f9f9f9;  width: 180px;">是否命中欺诈风险名单</td>
								<td>
									<c:if test="${indexObj.state == '1' }">是</c:if>
									<c:if test="${indexObj.state == '0' }">否</c:if>
								</td>
							</tr>
						</table>
					</c:when>
					<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
						<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
					</c:when>
					<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
				</c:choose>
		</div>
		
		
		<!-- =============================信贷逾期名单====================================== -->
		
		<div class="personal">
		
			<% 
				Object microNearlyThreeYearsOverdueInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("microNearlyThreeYearsOverdueInfo");
				
				String microNearlyThreeYearsOverdueInfoStr = microNearlyThreeYearsOverdueInfoObj.toString();
				JSONObject microNearlyThreeYearsOverdueInfoJson = new JSONObject();
				if (microNearlyThreeYearsOverdueInfoStr instanceof String) {
					microNearlyThreeYearsOverdueInfoStr = microNearlyThreeYearsOverdueInfoStr.replaceAll("\n", "");
					if(microNearlyThreeYearsOverdueInfoStr != null && !microNearlyThreeYearsOverdueInfoStr.equals("")){
						microNearlyThreeYearsOverdueInfoJson = JSONObject.parseObject(microNearlyThreeYearsOverdueInfoStr);
					}
				}
				request.setAttribute("microNearlyThreeYearsOverdueInfo",microNearlyThreeYearsOverdueInfoJson);
			%> 
		
		
			<c:set var="indexObj" value="${microNearlyThreeYearsOverdueInfo }"></c:set>	
			<div class="print_id"><span class="title">信贷逾期名单</span></div>
			
			<c:choose>
				<c:when test="${indexObj.treatResult == '1' }">
					<b style="font-size: 12px; padding-bottom: 5px; display: block;">证件号码命中汇总</b>
					<table cellpadding="0" cellspacing="0" class="table_css">
						<th align="center">序号</th>
						<th align="center">逾期类型</th>
						<th align="center">逾期总本金</th>
						<th align="center">逾期总笔数</th>
						<th align="center">匹配字段</th>
						<tr align="center">
							<td width="45">1</td>
							<td>${indexObj.loanHistoryStatusCountInfo.stat.overdueDays}</td>
							<td width="75" align="center">${indexObj.loanHistoryStatusCountInfo.stat.overdueAmount}元</td>
							<td width="75" align="center">${indexObj.loanHistoryStatusCountInfo.stat.overdueCount}笔</td>
							<td width="110" align="center">证件号码</td>
						</tr>
					</table>
					
					<b style="font-size: 12px; padding-bottom: 5px; display: block;">证件号码命中详情</b>
					<table cellpadding="0" cellspacing="0" class="table_css">
						<th align="center">序号</th>
						<th align="center">逾期类型</th>
						<th align="center">逾期总本金</th>
						<th align="center">逾期总笔数</th>
						<th align="center">匹配字段</th>
						<c:forEach var="indexItem" items="${indexObj.loanHistoryStatusInfos.loanHistoryStatusInfo.items}" varStatus="indexStatus">
							<tr>
								<td width="45">${indexStatus.index+1 }</td>
								<td>${indexItem.overdueDays }</td>
								<td width="75" align="center">${indexItem.overdueAmount }元</td>
								<td width="75" align="center">${indexItem.overdueCount }笔</td>
								<td width="110" align="center">证件号码</td>
							</tr>
						</c:forEach>
						<tr>
							<td colspan="5" style="background: #f9f9f9; text-align: left; ">提示：信贷逾期名单采用的是最近3年内的逾期数据</td>
						</tr>
					</table>
				</c:when>
				<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
					<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
				</c:when>
				<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
			</c:choose>
		</div>
		
		<!-- =============================个人风险信息====================================== -->
		<div class="query">
			<% 
				Object personRiskInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("personRiskInfo");
				
				String personRiskInfoStr = personRiskInfoObj.toString();
				JSONObject personRiskInfoJson = new JSONObject();
				if (personRiskInfoStr instanceof String) {
					personRiskInfoStr = personRiskInfoStr.replaceAll("\n", "");
					if(personRiskInfoStr != null && !personRiskInfoStr.equals("")){
						personRiskInfoJson = JSONObject.parseObject(personRiskInfoStr);
					}
				}
				request.setAttribute("personRiskInfo",personRiskInfoJson);
			%> 
			<c:set var="indexObj" value="${personRiskInfo }"></c:set>	
			<div class="print_id"><span class="title">个人风险信息</span></div>
				<c:choose>
					<c:when test="${indexObj.treatResult == '1' }">
						<b style="font-size: 12px; padding-bottom: 5px; display: block;">汇总信息</b>
						<table cellpadding="0" cellspacing="0" class="table_css">
							<tr>
								<td width="110">执行信息</td>
								<td>${indexObj.stat.zxCount}条</td>
								<td width="110">税务行政执法信息</td>
								<td>${indexObj.stat.swCount}条</td>
							</tr>
							<tr>
								<td>失信信息</td>
								<td>${indexObj.stat.sxCount}条</td>
								<td>网贷逾期信息</td>
								<td>${indexObj.stat.wdyqCount}条</td>
							</tr>
							<tr>
								<td>案例信息</td>
								<td>${indexObj.stat.alCount}条</td>
								<td>催欠公告信息</td>
								<td>${indexObj.stat.cqggCount}条</td>
							</tr>
						</table>
						
						<c:set var="summaryCount" value="${indexObj.stat.zxCount + indexObj.stat.swCount + indexObj.stat.sxCount + indexObj.stat.wdyqCount + indexObj.stat.alCount + indexObj.stat.cqggCount}"></c:set>
						
						<div class="personal">
						<b style="font-size: 14px; padding:10px 0 ; display: block;">概要信息（共${summaryCount }条）</b>
							
							<b style="font-size: 12px; padding-bottom: 5px; display: block;">执行信息（共${indexObj.stat.zxCount}条）</b>
							 <c:choose>
							 	<c:when test="${fn:length(indexObj.summary.zxs) > 0 }">
									<table cellpadding="0" cellspacing="0" class="table_css">
										<th>序号</th>
										<th align="left">标题</th>
										<th align="center">执行标的</th>
										<th align="center">立案日期</th>
										
										<!-- 设置变量信息 -->	
					  					<% 
					  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("personRiskInfo")
					  														.getJSONObject("summary").getJSONObject("zxs").get("item");
					  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
					  					 	request.setAttribute("indexArray", indexArray);
										%>  
										
										<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
											<tr>
												<td width="45">${indexStatus.index + 1 }</td>
												<td>${indexItem.bt }</td>
												<td width="75" align="center">${indexItem.zxbd }</td>
												<td width="75" align="center">${indexItem.larq }</td>
											</tr>
										</c:forEach>
									</table>
							 	</c:when>
							 	<c:otherwise><div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div></c:otherwise>
							 </c:choose>
						</div>
						
						<div class="personal">
							<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">失信信息（共${indexObj.stat.sxCount}条）</b>
							 <c:choose>
							 	<c:when test="${fn:length(indexObj.summary.sxs) > 0 }">
									<table cellpadding="0" cellspacing="0" class="table_css">
										<th>序号</th>
										<th align="left">标题</th>
										<th align="center">立案日期</th>
										<th align="center">发布日期</th>
										
										<!-- 设置变量信息 -->	
					  					<% 
					  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("personRiskInfo")
					  														.getJSONObject("summary").getJSONObject("sxs").get("item");
					  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
					  					 	request.setAttribute("indexArray", indexArray);
										%>  
										
										<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
											<tr>
												<td width="45">${indexStatus.index + 1 }</td>
												<td>${indexItem.bt }</td>
												<td width="75" align="center">${indexItem.larq }</td>
												<td width="75" align="center">${indexItem.fbrq }</td>
											</tr>
										</c:forEach>
									</table>
							 	</c:when>
							 	<c:otherwise><div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div></c:otherwise>
							 </c:choose>
							
						</div>
						
						<div class="personal">
							<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">司法案例概要信息（共${indexObj.stat.alCount}条）</b>
							 <c:choose>
							 	<c:when test="${fn:length(indexObj.summary.als) > 0 }">
									<table cellpadding="0" cellspacing="0" class="table_css">
										<th>序号</th>
										<th align="left">标题</th>
										<th align="center">案件类型</th>
										<th align="center">审结年份</th>
										<th align="center">当事人类型</th>
										
										<!-- 设置变量信息 -->	
					  					<% 
					  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("personRiskInfo")
					  														.getJSONObject("summary").getJSONObject("als").get("item");
					  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
					  					 	request.setAttribute("indexArray", indexArray);
										%>  
										
										<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
											<tr>
												<td width="45">${indexStatus.index + 1 }</td>
												<td>${indexItem.bt }</td>
												<td width="75" align="center">${indexItem.ajlx }</td>
												<td width="75" align="center">${indexItem.sjnf }</td>
												<td width="75" align="center">${indexItem.dsrlx }</td>
											</tr>
										</c:forEach>
									</table>
							 	</c:when>
							 	<c:otherwise><div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div></c:otherwise>
							 </c:choose>
							
						</div>
						
						
						<div class="personal">
							<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">税务行政执法信息（共${indexObj.stat.swCount}条）</b>
							 <c:choose>
							 	<c:when test="${fn:length(indexObj.summary.sws) > 0 }">
									<table cellpadding="0" cellspacing="0" class="table_css">
										<th>序号</th>
										<th align="left">标题</th>
										<th align="center">公告日期</th>
										
					  					<% 
					  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("personRiskInfo")
					  														.getJSONObject("summary").getJSONObject("sws").get("item");
					  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
					  					 	request.setAttribute("indexArray", indexArray);
										%>  
										
										<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
											<tr>
												<td width="45">${indexStatus.index + 1 }</td>
												<td>${indexItem.bt }</td>
												<td width="75" align="center">${indexItem.ggrq }</td>
											</tr>
										</c:forEach>
									</table>
							 	</c:when>
							 	<c:otherwise><div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div></c:otherwise>
							 </c:choose>
						</div>
						
						<div class="personal">
							<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">网贷逾期信息（共${indexObj.stat.wdyqCount}条）</b>
							 <c:choose>
							 	<c:when test="${fn:length(indexObj.summary.wdyqs) > 0 }">
									<table cellpadding="0" cellspacing="0" class="table_css">
										<th>序号</th>
										<th align="left">标题</th>
										<th align="center">发布日期</th>
										<c:forEach var="indexItem" items="${indexObj.summary.wdyqs}" varStatus="indexStatus">
											<tr>
												<td width="45">${indexStatus.index + 1 }</td>
												<td>${indexItem.bt }</td>
												<td width="75" align="center">${indexItem.fbrq }</td>
											</tr>
										</c:forEach>
									</table>
							 	</c:when>
							 	<c:otherwise><div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div></c:otherwise>
							 </c:choose>
							
						</div>
						

						
						<div class="personal">
							<b style="font-size: 12px; padding:10px 0 5px 0; display: block;">催欠公告概要信息（共${indexObj.stat.cqggCount}条）</b>
							 <c:choose>
							 	<c:when test="${fn:length(indexObj.summary.cqs) > 0 }">
									<table cellpadding="0" cellspacing="0" class="table_css">
										<th>序号</th>
										<th align="left">标题</th>
										<th align="center">发布日期</th>
										
					  					<% 
					  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("personRiskInfo")
					  														.getJSONObject("summary").getJSONObject("cqs").get("item");
					  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
					  					 	request.setAttribute("indexArray", indexArray);
										%> 
										
										<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
											<tr>
												<td width="45">${indexStatus.index + 1 }</td>
												<td>${indexItem.bt }</td>
												<td width="75" align="center">${indexItem.fbrq }</td>
											</tr>
										</c:forEach>
									</table>
							 	</c:when>
							 	<c:otherwise><div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div></c:otherwise>
							 </c:choose>
						</div>
					</c:when>
					<c:when test="${indexObj.treatResult == '2' || indexObj.treatResult == null }">
						<div class="div_remark">根据己提供的查询条件，未能在系统中查得相关信息</div>
					</c:when>
					<c:otherwise><div class="div_remark">${indexObj.errorMessage}</div></c:otherwise>
				</c:choose>  
				
				
				</div>		
				
				
				<!-- ============================被机构查询信息====================================== -->
				<div class="personal">
				
				
				<% 
					Object historySimpleQueryInfoObj = tempQueryResultJson.getJSONObject("cisReport").get("historySimpleQueryInfo");
					
					String historySimpleQueryInfoStr = historySimpleQueryInfoObj.toString();
					JSONObject historySimpleQueryInfoJson = new JSONObject();
					if (historySimpleQueryInfoStr instanceof String) {
						historySimpleQueryInfoStr = historySimpleQueryInfoStr.replaceAll("\n", "");
						if(historySimpleQueryInfoStr != null && !historySimpleQueryInfoStr.equals("")){
							historySimpleQueryInfoJson = JSONObject.parseObject(historySimpleQueryInfoStr);
						}
					}
					request.setAttribute("historySimpleQueryInfo",historySimpleQueryInfoJson);
				%> 
				
					<c:set var="indexObj" value="${historySimpleQueryInfo }"></c:set>	
					<div class="print_id"><span class="title">被机构查询信息</span></div>
					
					<c:choose>
						<c:when test="${indexObj.treatResult == '1' }">
							<table cellpadding="0" cellspacing="0" class="table_css">
								<th width="140px">
									<div class="out">
										<b>时间</b>
										<em>类别</em> 
									</div>
								</th>
								<th align="center">近1个月</th>
								<th align="center">近3个月</th>
								<th align="center">近6个月</th>
								<th align="center">近12个月</th>
								<th align="center">近18个月</th>
								<th align="center">近24个月</th>
								
								<!-- 设置变量信息 -->
			  					<% 
			  						Object item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("historySimpleQueryInfo").get("items");
			  						if(item != null){
			  							try{
			  								if(item.toString().contains("item")){
			  									item = tempQueryResultJson.getJSONObject("cisReport").getJSONObject("historySimpleQueryInfo").getJSONObject("items").get("item");
			  								}
			  							}catch(Exception e){
			  								
			  							}
			  						}
			  					
			  					 	JSONArray indexArray = JsonUtil.changeToList(item); 
			  					 	request.setAttribute("indexArray", indexArray);
								%>  
								<c:forEach var="indexItem" items="${indexArray}" varStatus="indexStatus">
									<tr>
										<td >${indexItem.unitMember }</td>
										<td align="center">${indexItem.last1Month }次</td>
										<td width="75" align="center">${indexItem.last3Month }次</td>
										<td width="75" align="center">${indexItem.last6Month }次</td>
										<td width="110" align="center">${indexItem.last12Month }次</td>
										<td width="75" align="center">${indexItem.last18Month }次</td>
										<td width="110" align="center">${indexItem.last24Month }次</td>
									</tr>
								</c:forEach>
								
								<tr>
									<td>合计</td>
									<td align="center">${indexObj.count.last1Month }次</td>
									<td width="75" align="center">${indexObj.count.last3Month }次</td>
									<td width="75" align="center">${indexObj.count.last6Month }次</td>
									<td width="110" align="center">${indexObj.count.last12Month }次</td>
									<td width="75" align="center">${indexObj.count.last18Month }次</td>
									<td width="110" align="center">${indexObj.count.last24Month }次</td>
								</tr>
								
							</table>
						</c:when>
						<c:when test="${indexObj.treatResult == '2'  || indexObj.treatResult == null}">
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
