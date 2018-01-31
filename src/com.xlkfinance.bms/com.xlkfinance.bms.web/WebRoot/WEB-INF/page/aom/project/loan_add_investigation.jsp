<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
	<div style="width:auto;padding-top:30px;">
		<div id="foreclosure">
		<div class="control_disbase" style="padding: 10px 10px 0 10px;width:1039px">
		<div class=" easyui-panel" title="尽职报告调查" data-options="collapsible:true">	
		<form action="<%=basePath%>secondBeforeLoanController/addSurveyReport.action" id="surveyReportInfo" name="surveyReportInfo" method="post" >
			<div style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
			<input type="hidden" name="pid" id="pid" value="0">
			<input type="hidden" name="projectId" >
		
			<div class="fitem">
				<label style="text-align:right;width: 160px;vertical-align: top;"><span>特殊情况说明：</span></label>
             	<input name="specialDesc" id="specialDesc" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
			</div>
			
			</div>
		</form>
		</div>
		<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveSurveyReportInfo()">保存调查报告</a>
				<!-- <a href="#" class="easyui-linkbutton download"  style="margin-left: 10px;" iconCls="icon-save" onclick="exportSurveyReport()">导出</a> -->
			</div>
		</div>
		</div>
	<script type="text/javascript">
	$(function(){  
		// 尽职调查报告URL			  			
		var urlSr = "<%=basePath%>secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
		// 加载尽职调查报告的数据
		$.post(urlSr,
			function(ret){
			if(ret.pid){
				$("#surveyReport").form('load',ret);
				$("#surveyReportInfo").form('load',ret);
				surPid = ret.pid;
			}
			}, "json"); 
		
		//流程控制
		if("task_BusinessRequest"==workflowTaskDefKey){
			
		}else{
			//风控总监审查  组织召开贷审会 办理抵押登记
			$('.control_dis .easyui-combobox').attr('disabled','disabled');
			$('.control_dis input[type="checkbox"]').attr('disabled','disabled');
			$('.control_dis .easyui-textbox').attr('disabled','disabled');
			$('.control_dis input:not(.download)').attr('readOnly','readOnly');
			$('.control_disbase .easyui-linkbutton:not(.download) ,.control_disbase input,.control_disbase textarea').attr('disabled','disabled');
		}
	
	});

	function saveSurveyReportInfo(){
		// 赋值项目ID--尽职调查报告
 		$("#surveyReportInfo input[name='projectId']").val(projectId);		
 		url = "";
 		if($("#surveyReportInfo input[name='pid']").val() == 0){
 			url = "<%=basePath%>secondBeforeLoanController/addSurveyReport.action";
 		}else{
 			url = "<%=basePath%>secondBeforeLoanController/updateSurveyReport.action";
 		}
 	// 提交表单
 		$("#surveyReportInfo").form('submit', {
 			url : url,
 			onSubmit : function() {return $(this).form('validate');},
 			success : function(result) {
 				var ret = eval("("+result+")");
 				if(ret && ret.header["success"]){
 					bianzhi = 1;// 标志为是流程
 					// 判断当前是什么操作
 					if($("#surveyReportInfo input[name='pid']").val() == 0){
 						$.messager.alert('操作提示',"保存成功!",'info');
 						surPid = ret.header["msg"];
 						$("#surveyReportInfo input[name='pid']").val(surPid);
 					}else{
 						$.messager.alert('操作提示',"修改成功!",'info');
 					}
 				}else{
 					$.messager.alert('操作提示',ret.header["msg"],'error');	
 				}
 			}
 		});
 		
	}
 	
 	</script>
 	