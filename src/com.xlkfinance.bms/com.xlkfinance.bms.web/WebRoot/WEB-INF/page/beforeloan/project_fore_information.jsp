<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/config.jsp"%>
<style>
.cus_table{border-top: 1px #95B8E7 solid; border-left: 1px #95B8E7 solid; margin-bottom: 10px;}
.cus_table td{border-right:1px solid #95B8E7;border-bottom:1px solid #95B8E7; padding: 5px;height:24px;}

</style>

	<div style="width:auto;padding-top:30px;">
		<div id="foreclosure">
		<div class="control_disbase" style="padding: 10px 10px 0 10px;width:1039px">
		<div class=" easyui-panel" title="赎楼清单" data-options="collapsible:true">	
		<form action="<%=basePath%>beforeLoanController/saveForeInformation.action" id="foreInformation" name="foreInformation" method="post" >
			<div style="padding-left: 10px;padding-top:10px;height: auto;" >
				<table class="cus_table" id="est_factor" cellpadding="0" cellspacing="0">
					 <tr style="height:20px;background: linear-gradient(to bottom,#F9F9F9 0,#efefef 100%);"> 
			           <td align="center">资料名称</td>
			           <td align="center">原件(份数)</td>
			           <td align="center">复印件(份数)</td>
			           <td align="center">备注</td>
			         </tr>
			         <c:if test="${not empty foreInformations }" >
			         <c:forEach items="${foreInformations}" var="item" varStatus="status">
					<tr>
						<td align="center" style="width:140px;">${item.foreInformationName}</td>
						<td>
							<input type="hidden" value="${item.pid}" name="foreList[${status.index }].pid"/>
							<input type="hidden" value="${item.projectId}" name="foreList[${status.index }].projectId"/>
							<input type="hidden" value="${item.foreId}" name="foreList[${status.index }].foreId"/>
				            <input type="text" class="text_style easyui-validatebox" data-options="validType:'integ'" value="${item.originalNumber}" name="foreList[${status.index }].originalNumber"/>
						</td>
						<td>
				            <input type="text" class="text_style easyui-validatebox" data-options="validType:'integ'" value="${item.copyNumber}" name="foreList[${status.index }].copyNumber"/>
						</td>
						<td>
				            <input type="text" class="text_style easyui-validatebox" data-options="validType:'length[0,100]'" value="${item.remark}" name="foreList[${status.index }].remark"/>
						</td>
					</tr>
					</c:forEach>
					</c:if>
				</table>
			</div>
		</form>
		</div>
		<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveForeInformation()">保存</a>
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-print" onclick="printForeInformation()">打印</a>
			</div>
		</div>
		</div>
	</div>
<script type="text/javascript">

$(function(){  
	//流程控制
	if("task_LoanRequest"==workflowTaskDefKey  || workflowTaskDefKey == "task_ExaminerCheck" || isEdit == 'isEdit'||"task_BusinessRequest"==workflowTaskDefKey){
		
	}else{
		//申请办理
		$('.control_dis .easyui-combobox').attr('disabled','disabled');
		$('.control_dis input[type="checkbox"]').attr('disabled','disabled');
		$('.control_dis .easyui-textbox').attr('disabled','disabled');
		$('.control_dis input:not(.download)').attr('readOnly','readOnly');
		$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('disabled','disabled');
		$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('readOnly','readOnly');
		$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').addClass('l-btn-disabled');
	}
});
	function printForeInformation(){
		var url = "${basePath}beforeLoanController/printForeInformation.action?projectId="+projectId;
		parent.openNewTab(url,"赎楼资料交接单打印",true);
	}

	function saveForeInformation(){
			// 提交表单
			$("#foreInformation").form('submit', {
			onSubmit : function() {
					return $(this).form('validate'); 
			},
				success : function(result) {
					var ret = eval("("+result+")");
					if(ret && ret.header["success"]){
							$.messager.alert('操作提示',"保存成功!",'info');
						}else{
						$.messager.alert('操作提示',ret.header["msg"],'error');	
					}
				}
			});
	}

</script>
