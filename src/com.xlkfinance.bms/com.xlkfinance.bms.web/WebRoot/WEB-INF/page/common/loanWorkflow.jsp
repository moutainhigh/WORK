<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>

<form action="${basePath}taskController/executeFlow.action" 
	method="post" id="loan_workflow_from">
	<input id="taskId" name="taskId" type="hidden">
	<input id="workflowId" name="workflowId" type="hidden">
	<input id="refId" name="refId" type="hidden">
	<input id="projectId" name="projectId" type="hidden">
	<input id="workflowInstanceId" name="workflowInstanceId" type="hidden">
	<input id="vetoProject" name="vetoProject" type="hidden">
	<input id="taskNodeName" name="taskNodeName" type="hidden">
	<input id="workflowTaskDefKey" name="workflowTaskDefKey" type="hidden">
	<table class="beforeloanTable common_flow" style="width:96%">
		<tr id="next_node_tr" style="height:auto;">
	  	</tr>
	  	<tr id="reject_select_tr" style="height:auto;">
	  	</tr>
	  	<!-- <tr class="pview"> 
		    <td class="label_right" width="100">任务提醒方式：</td>
		    <td colspan="3">
		   		<input type="checkbox" id="ifSms" name="ifSms"/><span>短信提醒</span>&nbsp;&nbsp;&nbsp;
		   		<input type="checkbox" id="ifEmail" name="ifEmail" /><span>邮件提醒</span>
		    </td>
	  	</tr> -->
	  	<tr>
	  		<td class="label_right">意见与说明：</td>
		    <td colspan="3">
				<div style="width:100%"><textarea style="height:60px;width:100%;" maxlength="1000" id="idea" class="text_style" name="idea"></textarea></div>
		    </td>
	  	</tr>
	  	<tr>
	  		<td></td>
		    <td class="align_center"  height="50">
				<a href="#"class="easyui-linkbutton" iconCls="icon-save" onclick="commitTask()">提交任务</a>
		    	<a href="#"class="easyui-linkbutton" iconCls="icon-clear" onclick="resetForm()">取消</a>
		    </td>
		    
		    <td></td>
	  	</tr>
	</table>
</form>
</body>
<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/common/loanWorkflow.js?v=${v}"></script>
