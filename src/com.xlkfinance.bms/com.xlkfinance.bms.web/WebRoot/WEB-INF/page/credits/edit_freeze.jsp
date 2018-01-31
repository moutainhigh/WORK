<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
//归档类型
var Iam = "冻结与解冻";
/**************工作流字段 begin******************/
var WORKFLOW_ID = "creditFreezeOrThawRequestProcess"; 
workflowTaskDefKey = "task_CreditFreezeOrThawRequest";
/**************工作流字段 end********************/
var projectId = -1;// 项目ID
var loanId = -1;// 贷款ID
var creditId = -1;// 授信ID
var limitId =-1;//记录ID
var bianzhi = -1;// 流程设置为1,编辑设置为2,其他的不用管
var acct_id=0; // 账户id 
var com_id=0; // 企业id
var creditUseType =-1;//状态 
var typeStats =-1;//冻结与解冻状态  3冻结，4解冻
var infoStats = -1;
$(document).ready(function(){
	//加载授项目
	loadCreditDiv(${projectId});

	//显示审批冻结解冻金额
	if(${fail}==1){
		$("#requestAmt,.requestAmtDiv .textbox-text").attr("readonly","readonly");
		$("#reason").attr("disabled","disabled");
		$("#saveCredits").attr("disabled","disabled");
	}
	
	//根据状态动态显示标题
  	typeStats = ${creditUseType};
	if(typeStats!=""){
		if(${creditUseType}=='3'){
			//$(".rel_title").html("冻结申请");
			$('#freeze_panel').panel({title:"冻结申请"});
			$(".cus_cred_pan").html("冻结申请金额：");
			$(".cus_cred_pan_str").html("审批冻结金额：");
			$(".cus_cred_pan_str_info").html("审批冻结金额不可大于可用额度");
			$(".cus_info_span").html("冻结金额不可大于可用额度");
		}else{
			//$(".rel_title").html("解冻申请：");
			$('#freeze_panel').panel({title:"解冻申请"});
			$(".cus_cred_pan").html("解冻申请金额：");
			$(".cus_cred_pan_str").html("审批解冻金额：");
			$(".cus_cred_pan_str_info").html("审批解冻金额不可大于冻结额度");
			$(".cus_info_span").html("解冻金额不可大于冻结额度");
			
		} 
	}else{
		//$(".rel_title").html("解冻与冻结申请");
		$('#freeze_panel').panel({title:"解冻与冻结申请"});
		$(".cus_cred_pan_str").html("审批解冻与冻结金额");
	}  
	//给审批解冻金额赋值
	var amt = $("#amt").numberbox('getValue');
	var requestAmt = $("#requestAmt").numberbox('getValue');
	if(amt==""&&requestAmt!=""){
		 $("#amt").numberbox('setValue',requestAmt);
	}
	 
});

//返回页面 
function cancelFreeze(){
	window.location.href='${basePath}creditsController/navigation.action';
}

//保存
function saveFreeze(){
	
	$('#freezeForm').form('submit', {
		url : "saveFreeze.action",
		onSubmit : function() {			
			if(checkFreezeForm()){
				return $(this).form('validate');
			}{
				return false;
			}
		},
		success : function(result) {
			var str =eval(result);
			projectId = Number($("#projectIdById").val());
			if($("#creditLimitRecordPid").val()==""){
				$("#creditLimitRecordPid").val(str[0]);
				limitId = str[0];
			}else{
				infoStats =1;
			}
			
			alert("保存成功！");
		//	$("#saveCredits").attr("disabled","disabled");
			//window.location='${basePath }creditsController/navigation.action';
		},error : function(result){
			alert("保存失败！");
			$("#saveCredits").removeAttr("disabled");
		}
	}); 
}

//校验金额
function checkFreezeForm(){
	var Limit=$('#clientAmtSpan').attr('amtInfo');//从add_client.jsp获取的可用额度 
	var fmt = $('#fAmtSpan').attr('fmtInfo')//冻结金额 
	var requestAmt=$("#requestAmt").numberbox('getValue');//冻结解冻金额
	var usableLimit=$("#creditLimit").val();//可用额度 
	var freezeLimit=$("#freezeLimit").val();//冻结额度
	var reqStatus=$("#creditUseType").val();//状态 
	if($('#amt').length){
		var amt=$("#amt").numberbox('getValue');
	}else{
		var amt="";
	}
	
	 if(reqStatus=='4'){
		usableLimit=freezeLimit;
	}
	if(requestAmt!='' && usableLimit !=''){
		if(parseFloat(requestAmt) > parseFloat(usableLimit)){
			if(reqStatus=='4'){
				$.messager.alert("操作提示","解冻金额不能大于冻结额度！","error",function(){
					$(".requestAmtDiv .textbox-text").focus();
				});
			}else if(reqStatus=='3'){
				$.messager.alert("操作提示","冻结金额不能大于可用额度！","error",function(){
					$(".requestAmtDiv .textbox-text").focus();
				});
			}	
			return false;
		}
		return true;
	}else if(amt !=undefined && amt!=''){
		if(reqStatus=='4'){ //解冻
			if(Number(amt)>Number(fmt)){
				$.messager.alert("操作提示","审批解冻额度不能大于解冻额度！","error",function(){
					$(".amtDiv .textbox-text").select();
				});
				return false;
			}
		}else if(reqStatus=='3'){ //冻结
			if(Number(amt)>Number(Limit)){
				$.messager.alert("操作提示","审批冻结额度不能大于可用额度！","error",function(){
					$(".amtDiv .textbox-text").select();
				});
				return false;
			}
		}
		return true;
	}else{
		return true;
	}
}
</script>
<body class="easyui-layout">
<div data-options="region:'center',border:false">
<div class="easyui-panel" id="freeze_panel" data-options="collapsible:true">
	<div id="cus_content">
		<table class="cus_table" style="width:100%;border:none">			
			<tr>
				<td>
					<div id="receiptContractBiao">
						<div class="search_result_info">
							<%@ include file="add_client.jsp"%>
						</div>
						<div id="req_freeze">
							<form id="freezeForm" action="saveFreeze.action" method="POST">
								<input type="hidden" name="projectId" id="projectIdById" value="${projectId}" />
								<input type="hidden" name="creditId" value="${creditId}" />
								<input type="hidden" name="status" value="0" />
								<input type="hidden" name="creditUseType"  id="creditUseType" value="${creditUseType}" /> <!-- 冻结3  解冻4 -->
								<input type="hidden" name="creditLimit" id="creditLimit" value="${creditLimit}" /> <!-- 可用额度 -->
								<input type="hidden" id="freezeLimit" name="freezeLimit" value="${freezeLimit}" /> <!-- 冻结额度 -->
								<input type="hidden" name="pid" value="${creditLimitRecord.pid}"  id="creditLimitRecordPid" /> 
								<table class="cus_table_2">
									<tr> 
										<td class="align_right" style="width: 150px;">
										<span class="cus_red">*</span><span class="cus_cred_pan">申请解冻/冻结金额：</span></td>
										<td>
										<div class="requestAmtDiv">
										<input type="text" class="text_style easyui-numberbox easyui-validatebox"  precision="2" groupSeparator="," min="0" id="requestAmt"   name="requestAmt" value="${creditLimitRecord.requestAmt}"
											data-options="required:true" />&nbsp;&nbsp;
											 <span class="cus_red"><span class="cus_info_span">冻结/解冻金额不可大于现有额度或者可用额度</span>
										</div> 
										 </td>
									</tr>
									<tr>
										<td class="align_right"><span class="cus_red">*</span>申请理由：</td>
										<td height="80px"><textarea rows="4" style="width: 594px;" class="easyui-validatebox" id="reason" name="reason" data-options="required:true">${creditLimitRecord.reason}</textarea></td>
									</tr>
									   <tr class="none" id="info">
								            <td class="align_right"><span class="cus_red">*</span><span class="cus_cred_pan_str">审批解冻/冻结金额：</span></td>
								            <td >
								            	<div class='amtDiv'>
												<input type="text" class="text_style  easyui-numberbox easyui-validatebox" precision="2" groupSeparator="," min="0" data-options="required:true"   id="amt" name="amt" value="${creditLimitRecord.amt==''?0:creditLimitRecord.amt}"  />&nbsp;&nbsp;
												<span class="cus_red"><span class="cus_cred_pan_str_info">冻结/解冻金额不可大于现有额度或者可用额度</span></span>
												</div>
											</td>
									  </tr>
								</table>
								<div style="width: 780px; float: left;">
									<div id="creditPro"></div>
									<div style="text-align: center;">
										<input type="button" class="cus_save_btn" name="save" id="saveCredits" value="保     存" onclick="saveFreeze();" />&nbsp;&nbsp;&nbsp;
										<input type="button" class="cus_save_btn" name="cancel" id="" value="取     消" onclick="cancelFreeze();" />
									</div>
							</form>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
<div class="pt10">
	<div class="easyui-panel" title="工作流程" data-options="collapsible:true">
		<div style="padding:5px">
			<%@ include file="../common/loanWorkflow.jsp"%>
			<%@ include file="../common/task_hi_list.jsp"%>
		</div>
	</div>
</div>
</div>
<script>
$(document).ready(function() {
	//总经理 task_GeneralManagerReview
	 if(workflowTaskDefKey=="task_ApprovalOfficerReview"){//审批官
		$("#info").removeClass("none");
		$('#amt').validatebox({required:true,validType:'number'});
		$("#saveCredits").removeAttr("disabled");
	}else if(workflowTaskDefKey=="task_GeneralManagerReview"){//总经理
		$(".amtDiv .textbox-text").attr("readonly","readonly");
		$("#info").removeClass("none");
		
	}else if(workflowTaskDefKey=="task_RiskControlDirectorReview"){//风控总监审批
		$(".amtDiv .textbox-text").attr("readonly","readonly");
		$("#info").removeClass("none");
	}else{
		$("#info").remove();
	} 
});


</script>
</body>
