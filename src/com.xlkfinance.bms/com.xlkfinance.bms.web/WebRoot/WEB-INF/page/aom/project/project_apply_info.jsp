<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/config.jsp"%>
	<div style="width:auto;padding-top:30px;">
		<div id="foreclosure">
		<div class="control_disbase" style="padding: 10px 10px 0 10px;width:1039px">
		<div class=" easyui-panel" title="申请办理" data-options="collapsible:true">	
		<form action="<%=basePath%>beforeLoanController/saveApplyInfo.action" id="applyInfoForm" name="applyInfoForm" method="post" >
			<input type="hidden" value="${applyHandleInfo.pid }" name="pid">
			<input type="hidden" value="${applyHandleInfo.createrId }" name="createrId">
			<input type="hidden" value="${projectId}" name="projectId">
			
			<div style="padding-left: 10px;padding-top:10px;height: auto;line-height: 35px;" >
				<table class="beforeloanTable" style="width:100%;padding: 10px 0 15px 0;border-bottom: 1px #ddd dashed;margin-bottom: 15px;">
				<tr>
					<td class="label_right1"><font color="red">*</font>赎楼办理时间：</td>
					<td>
						<input name="handleDate" class="easyui-datebox" data-options="required:true" missingMessage="请填写办理时间!" value="${applyHandleInfo.handleDate }" editable="false"/>
					</td>
					<td class="label_right"><font color="red">*</font>赎楼联系人：</td>
					<td>
						<input name="contactPerson" class="easyui-textbox" data-options="required:true,validType:'length[0,20]'" missingMessage="请填写赎楼联系人!" value="${applyHandleInfo.contactPerson }"/>
					</td>
					<td class="label_right"><font color="red">*</font>联系电话：</td>
					<td>
						 <input name="contactPhone" class="easyui-textbox" data-options="required:true,validType:'phone'" missingMessage="请填写联系电话!" value="${applyHandleInfo.contactPhone }"/>
					</td>
				</tr>
				<tr>
					<td class="label_right">特殊情况说明：</td>
					<td colspan="3">
						<input name="specialCase" value="${applyHandleInfo.specialCase }" id="specialCase" class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'">
					</td>
				</tr>
				<tr>
					<td class="label_right">备注：</td>
					<td colspan="3">
						 <input name="remark" id="remark" value="${applyHandleInfo.remark }"  class="easyui-textbox" style="width:65%;height:60px" data-options="multiline:true,validType:'length[0,800]'">
					</td>
				</tr>
			</table>
			</div>
		</form>
		</div>
		<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 40px;" iconCls="icon-save" onclick="saveApplyInfo()">保存</a>
			
			</div>
		</div>
		</div>
	</div>
<script type="text/javascript">
$(function(){  
	//流程控制
	if("task_BusinessRequest"==workflowTaskDefKey){
		
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

	function saveApplyInfo(){
		$('#applyInfoForm').form('submit', {
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
