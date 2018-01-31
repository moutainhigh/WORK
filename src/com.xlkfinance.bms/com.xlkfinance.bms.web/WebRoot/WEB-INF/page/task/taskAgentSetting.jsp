<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<style type="text/css">
	.align_center{
		text-align:right;
		font-size: 10px;
		width: 20%;
		height: 28px;
	}
	.align_right{
		text-align: right;
	}
	.task_agent_table{
		margin-top:30px;
	}
</style>
<script type="text/javascript">
	var BASE_PATH = "${basePath}";
</script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/task/taskAgentSetting.js"></script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
	<div id="task_agent_setting_win" class="easyui-window" 
		data-options="iconCls:'icon-save'">
    	<form id="task_agent_setting_from"
    		action="${basePath}taskController/insertTaskSetting.action"
    		method="post"
			name="task_agent_setting_from">
			<input id="pid" name="pid" hidden="true">
			<table class="task_agent_table">
				<tr>
				    <td class="align_center" >流程选择：</td>
				    <td>
				    <input id="workflowProcessDefId" name="workflowProcessDefId" style="width:220px;" 
				    class="easyui-combobox" data-options="
				    	required:'true',
				        valueField: 'lookupVal',    
				        textField: 'lookupDesc',    
				        url: '<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=WORK_FLOW_LIST',    
				        onSelect: function(rec){    
				            var url = BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType='+rec.lookupVal;    
    						 $('#workflowTaskId').combobox('reload', url); 
				        },
				        onLoadSuccess:function(){
				       		 clearSelectRows('#task_agent_table');
				        	 var lookupVal = $(this).combobox('getValue');
				        	 var url = BASE_PATH+'sysLookupController/getSysLookupValByLookType.action?lookupType='+lookupVal;    
    						 $('#workflowTaskId').combobox('reload', url); 
				        }" />  
				    </td>
				    <td class="align_center">审核结点选择：</td>
				    <td>
				    	<input id="workflowTaskId" name="workflowTaskId"
						style="width: 220px;" class="easyui-combobox"
						data-options="required:'true',valueField:'lookupVal',textField:'lookupDesc'" />
					</td>
			  	</tr>
			  	<tr>
				    <td class="align_center">代办开始时间：</td>
				    <td>
						<input id="beginDt" name="beginDt" style="width:220px" class="easyui-datebox" data-options="required:'true'"/>
				    </td>
				    <td class="align_center">代办结束时间：</td>
				    <td>
				    	<input id="endDt" name="endDt" style="width:220px;" class="easyui-datebox" data-options="required:'true'"/>
				    </td>
			  	</tr>
			  	<tr>
				    <td class="align_center">代办人：</td>
				    <td>
				    <input id="agencyUserId" name="agencyUserId"
						style="width: 220px;" class="easyui-combobox"
						data-options="required:'true',valueField:'pid',textField:'realName',
						url:'<%=basePath%>sysUserController/getAllSysUser.action'" />
				    </td>
				    <td class="align_center">是否启用：</td>
				    <td>
				    	<select id="useStatus" class="easyui-combobox" data-options="required:'true'" name="useStatus" style="width:220px;">   
						    <option value="0">否</option>   
						    <option value="1">是</option>   
						</select> 
				    </td>
			  	</tr>
			  	<tr>
			  		<td colspan="4" height="10"></td>
			  	</tr>
			  	<tr>
				    <td colspan="4" align="center" style="line-height: 50px;" >
				    	&nbsp;
						<a href="#"class="easyui-linkbutton" iconCls="icon-save" onclick="saveTaskAgentSet()">保存</a>
				    	&nbsp;
				    	<a href="#"class="easyui-linkbutton" iconCls="icon-clear" onclick="resetForm()">重置</a>
				    </td>
			  	</tr>
			</table>
		</form>
    </div> 
	<table id="task_agent_setting_grid"></table>
	</div>
	</div>
</body>
</html>
