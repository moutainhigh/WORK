<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/config.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<!-- 签约公证抵押 -->
<form action="<%=basePath%>projectInfoController/saveSpotInfo.action" id="spotInfoForm" name="surveyReportInfo" method="post" >
	<div style="padding: 30px 10px 0 10px;">
		<div class=" easyui-panel" title="签约公证抵押" data-options="collapsible:true">
			<div style="padding: 10px 10px 0 10px;">
				<div class="easyui-panel" title="签约" data-options="collapsible:true" style="margin-bottom: 10px;">
					<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;">
						<table class="">
							<tbody>
								<tr>
									<td class="label_right">面签人员:</td>
									<td>
										<input class="easyui-textbox" readonly value="${interviewInfo.interviewer }"/>
									</td>
									<td class="label_right">面签时间:</td>
									<td>
										<input class="easyui-datebox" readonly value="${interviewInfo.interviewTime }"/>
									</td>
									<td class="label_right">面签地点:</td>
									<td>
										<input id="interviewPlace" class="easyui-textbox" readonly value="${interviewInfo.interviewPlace }"/>
									</td>
								</tr>
							</tbody>
						</table>	
					</div>
				</div>	
				<div class="easyui-panel" title="公证" data-options="collapsible:true" style="margin-bottom: 10px;">
					<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;">
						<table class="">
							<tbody>
								<tr>
									<td class="label_right">公证类型:</td>
									<td class="label_right">
										<input id="notarizationType" type="hidden" readonly value="${interviewInfo.notarizationType }"/>
										<label data="全委"><input disabled="disabled" type="checkbox">全委</label>
										<label data="强执"><input disabled="disabled" type="checkbox">强执</label>
									</td>
									<td class="label_right">取证日期:</td>
									<td class="label_right">
										<input class="easyui-datebox" readonly value="${interviewInfo.receiveTime }"/>
									</td>
									<td class="label_right">公证人员:</td>
									<td class="label_right">
										<input class="easyui-textbox" readonly value="${interviewInfo.notarizator }"/>
									</td>
									<td class="label_right">办理时间:</td>
									<td class="label_right">
										<input class="easyui-datebox" readonly value="${interviewInfo.handingTime }"/>
									</td>
								</tr>
							</tbody>
						</table>		
					</div>
				</div>	
				<div class="easyui-panel" title="抵押" data-options="collapsible:true" style="margin-bottom: 10px;">
					<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;">
						<table class="">
							<tbody>
								<tr>
									<td class="label_right">抵押权人:</td>
									<td class="label_right">
										<input class="easyui-textbox" readonly value="${interviewInfo.mortgageName }"/>
									</td>
									<td class="label_right">抵押回执编号:</td>
									<td class="label_right">
										<input class="easyui-textbox" readonly value="${interviewInfo.mortgageCode }"/>
									</td>
									<td class="label_right">取证日期:</td>
									<td class="label_right">
										<input class="easyui-datebox" readonly value="${interviewInfo.mortgageTime }"/>
									</td>
								</tr>
								<tr>
									<td class="label_right">抵押人员:</td>
									<td class="label_right">
										<input class="easyui-textbox" readonly value="${interviewInfo.mortgator }"/>
									</td>
									<td class="label_right">办理日期:</td>
									<td class="label_right">
										<input class="easyui-datebox" readonly value="${interviewInfo.mortgageHandTime }"/>
									</td>
								</tr>
							</tbody>
						</table>		
					</div>
				</div>	
				<div class="easyui-panel" title="领取他证信息情况" data-options="collapsible:true" style="margin-bottom: 10px;">
					<div style="padding: 10px 0 10px 10px;height: auto;line-height: 35px;">
						<table class="">
							<tbody>
								<tr>
									<td class="label_right">他项权证:</td>
									<td class="label_right">
										<input class="easyui-textbox" readonly value="${interviewInfo.hisWarrant }"/>
									</td>
									<td class="label_right">领取时间:</td>
									<td class="label_right">
										<input class="easyui-datebox" readonly value="${interviewInfo.hisWarrantTime }"/>
									</td>
									<td class="label_right">经办人:</td>
									<td class="label_right">
										<input class="easyui-textbox" readonly value="${interviewInfo.hisWarrantUserName }"/>
									</td>
								</tr>
								<tr>
									<td class="label_right">他证影象:</td>
									<td colspan="5">
										<c:if test="${not empty interviewInfo.interviewFile }">
										<div>
											<a style="display: inline-block;max-width: 200px;max-height: 200px;">
												<img alt="${interviewInfo.interviewFile.fileName }" src="${basePath }customerController/download.action?path=${interviewInfo.interviewFile.fileUrl }">
											</a>
										</div>
										<div>
											<a download="${interviewInfo.interviewFile.fileName }" href="${ basePath}customerController/download.action?path=${interviewInfo.interviewFile.fileUrl }">下载</a>
										</div>
										</c:if>
									</td>
								</tr>
							</tbody>
						</table>		
					</div>
				</div>	
		</div>
	</div>
</form>

<script type="text/javascript">
$(function(){
	$("input#interviewPlace").combobox({
		valueField:'lookupVal',
		textField:'lookupDesc',
		url:'${basePath}sysLookupController/getSysLookupValByLookType.action?lookupType=INLOAN_INTERVIEW_PLACE',
	});
	
	var notarizationType = $("#notarizationType").val();
	if(notarizationType){
		var types = notarizationType.split(",");
		for(var i =0;i<types.length;i++){
			$("#notarizationType").siblings("label[data='"+types[i]+"']").children("input").attr("checked",true);
		}
	}
	
});
</script>
