<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
		<div style="width:auto;padding:15px;border:none" class="contract_dis">
			<form action="<%=basePath%>foreLoanExtensionController/addForeInformation.action" id="investigationForm"  name="investigationForm" method="post" >
			<input type="hidden" name="projectPid" id="project_id"> 
			<input type="hidden" name="loanId" id="loan_id"> 
			<input type="hidden" name="oldProjectId" id="oldProjectId"> 
			<div class="fitem" style="padding-left: 25px;" >
			<table>
				<tr>
					<td align="right" width="80" height="28">项目名称：</td>
					<td><input name="projectName" id="projectName" type="text" style="width: 500px;color: red;" class="text_style" readonly="readonly"> </td>
				</tr>
				<tr>
					<td align="right" width="80" height="28">项目编号：</td>
					<td ><input name="projectNumber" id="projectNumber" type="text" style="width: 500px;color: red;" class="text_style" readonly="readonly" > </td>
				</tr>
			</table>
			
			</div>
			
		<div class="clear pt10"></div>
		<!-- 提前还款登记信息 -->
		<div class="dueInfoDiv easyui-panel " title="展期基本信息设置" data-options="collapsible:true"  width="1039px;">	
			<div id="loanBasicNews" style="padding: 15px;height: auto;" >
				<table class="extensionTable">
				<tr>
					<td class="label_right1"><font color="red">*</font>展期金额：</td>
					<td>
						<input class="easyui-numberbox" id="extensionAmt" required="true" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="extensionAmt"/>
					</td>
					<td class="label_right1"><font color="red">*</font>展期费率：</td>
					<td>
						<input class="easyui-numberbox" id="extensionRate" required="true" data-options="min:0,max:100,precision:2,groupSeparator:','" name="extensionRate"/>%
					</td>
					<td class="label_right1"><font color="red">*</font>展期费用：</td>
					<td>
						<input class="easyui-numberbox" id="extensionFee" required="true" data-options="min:0,max:9999999999,precision:2,groupSeparator:','" name="extensionFee"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1"><font color="red">*</font>原回款时间：</td>
					<td>
						<input id="paymentDate" class="easyui-datebox" readonly="readonly" editable="false" style="width:129px;"/>
					</td>
					<td class="label_right1"><font color="red">*</font>展期天数：</td>
					<td>
						<input class="easyui-numberbox" id="extensionDays" required="true" data-options="min:0,max:9999,required:true,validType:'integer'" name="extensionDays"/>
					</td>
					<td class="label_right1"><font color="red">*</font>展期结束时间：</td>
					<td>
						<input name="extensionDate" id="extensionDate" required="true" readonly="readonly" missingMessage="请输入展期时间!"  class="easyui-datebox" editable="false" style="width:129px;"/>
					</td>
				</tr>
				<tr>
					<td class="label_right1">展期原因：</td>
					<td colspan="9">
						<input name="specialDesc" id="specialDesc" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,500]'">
					</td>
				</tr>
				<tr>
					<td colspan="6" align="center" height="35">
						<a href="#" class="easyui-linkbutton" iconCls="icon-save" onclick="saveExtensionInfo()">保存基本信息</a>
					</td>
				</tr>
				</table>
			</div>
			
			</div>
			</form>
		</div>
		
	<style type="text/css">
		.investigation{
			width:120px;
			text-align: right;
		}
		
		.no_frame{
			border:0;
			background: white;
		}
		
		#table_dzywxx tr td{
			width:150px;
		}
		
		</style>
	
	<script type="text/javascript">
		
	$(function(){	
 		// 详情链接点击进来,表单禁用
 		applyCommon.detailDisable(biaoZhi,3);
 		
 		extension.getLoanExtension(newProjectId);
 		// 表单初始化
 		extension.init(projectId,newProjectId);
 		applyCommon.setProjectInfo(newProjectId);
 		
 		// 加载尽职调查报告的数据
		// 尽职调查报告URL			  			
		var urlSr = BASE_PATH+"secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+newProjectId;
		// 加载尽职调查报告的数据
		$.post(urlSr,
			function(ret){
				$("#specialDesc").textbox('setValue',ret.specialDesc);
			},"json");
 		
		$.ajax({
			type: "POST",
	    	url : BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action?projectId="+newProjectId,
			dataType: "json",
		    success: function(data){
		    	$("#paymentDate").datebox('setValue', data.projectForeclosure.paymentDate);
		    	//$("#extensionRate").numberbox('setValue',data.projectGuarantee.feeRate);
			}
		});
 		$("#extensionDays").numberbox({
 		    "onChange":function(){
 		    	changeExtensionDays();
 		    }
 		  });
 		
 		if(projectName){
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
	 	}
		if("task_LoanRequest"==workflowTaskDefKey){
			
		}else{
			$('.contract_dis .easyui-combobox').attr('disabled','disabled');
			$('.contract_dis input[type="checkbox"]').attr('disabled','disabled');
			$('.contract_dis .easyui-textbox').attr('disabled','disabled');
			$('.contract_dis .easyui-numberbox').attr('disabled','disabled');
			$('.contract_dis .easyui-linkbutton ,.contract_dis a').removeAttr('onclick');
			$('.contract_dis a font').attr('color','gray');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input:not(.download),.contract_dis  textarea').attr('disabled','disabled');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input:not(.download),.contract_dis  textarea').attr('readOnly','readOnly');
			$('.contract_dis .easyui-linkbutton ,.contract_dis  input:not(.download),.contract_dis  textarea').addClass('l-btn-disabled');
		} 
 		
	});  
	
	function changeExtensionDays(){
		var paymentDate = $("#paymentDate").datebox('getValue');
		var extensionDays = $("#extensionDays").numberbox('getValue');
		var newDate =  DateAdd("d ",Number(extensionDays),new Date(paymentDate));  
		var dates = newDate.format('yyyy-MM-dd');
		$("#extensionDate").datebox('setValue', dates);
	}
	
	function changFee(){
		var extensionAmt = $("#extensionAmt").numberbox('getValue');
		var extensionRate = $("#extensionRate").numberbox('getValue');
		var extensionDays = $("#extensionDays").numberbox('getValue');
		
		var extensionFee = (extensionDays/365)*extensionAmt*extensionRate/100;
		$("#extensionFee").numberbox('setValue',extensionFee);
	}
	
	function selectComToExport(){
		var rows = $('#percomGrid').datagrid('getChecked');
		if(rows.length==0){
			alert('请选择旗下公司');
		}else if(rows.length==1){
			$.ajax({
				url:'${basePath}secondBeforeLoanController/getAcctIdByComId.action',
				type:'post',
				data:{comId:rows[0].pid},
				dataType:'json',
				success:function(data){
					if(data==0){
						alert('查询不到客户ID');
					}else{
		 			window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+newProjectId+"&comId="+data; 
					}
				}
			});
		}else{
			alert('只能请选择一个旗下公司进行导出');
		}
	}
	
//保存展期信息
function saveExtensionInfo(){
	// 判断项目名称
	if($("#projectName").val()==null || $("#projectName").val()==""){
		$.messager.alert('操作提示',"项目名称不允许为空!",'error');	
		return;
	}
	var extensionAmt = $("#extensionAmt").val();
	if(extensionAmt <= 0){
		$.messager.alert('操作提示',"展期金额必须大于0!",'error');	
		return;
	}
	
	var extensionFee = $("#extensionFee").val();
	if(extensionFee <= 0){
		$.messager.alert('操作提示',"展期费用必须大于0!",'error');	
		return;
	}
	
	// 赋值
	$("#investigationForm input[name='projectPid']").val(newProjectId);
	$("#investigationForm input[name='oldProjectId']").val(projectId);
	// 提交表单
	$("#investigationForm").form('submit',{
		onSubmit : function() {return $(this).form('validate');},
        success:function(result){
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$("#loanId").val(ret.header["msg"]);
				$.messager.alert('操作提示',"保存成功!",'info');
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
        }
	});
	
}
	
	
	function saveSurveyReport(){
		url = "";
		url = BASE_PATH+"secondBeforeLoanController/updateSurveyReport.action";
		$("#surveyReport").form('submit', {
			url : url,
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				var ret = eval("("+result+")");
				if(ret && ret.header["success"]){
					surPid = ret.header["msg"];
					$("#surveyReport input[name='pid']").val(surPid);
					$.messager.alert('操作提示',"修改成功!",'info');
				}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');	
				}
			}
		});
	}
	
	function UnselectComToExport(){
		window.location.href="${basePath}secondBeforeLoanController/exportSurveyReport.action?projectId="+newProjectId+"&comId=0"; 
	}
 	</script>
</body>