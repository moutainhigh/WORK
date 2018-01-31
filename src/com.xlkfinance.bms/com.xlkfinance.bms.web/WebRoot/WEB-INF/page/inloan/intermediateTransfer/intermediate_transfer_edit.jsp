<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<%@ include file="/common.jsp"%>
<title>中途划转</title>
<style type="text/css">
.table_css td {
	padding: 7px 10px;
	font-size: 12px;
	border-bottom: 1px #95B8E7 solid;
	border-right: 1px #95B8E7 solid;
}

.bt {
	background-color: #E0ECFF;
	background: -webkit-linear-gradient(top, #EFF5FF 0, #E0ECFF 100%);
	background: -moz-linear-gradient(top, #EFF5FF 0, #E0ECFF 100%);
	background: -o-linear-gradient(top, #EFF5FF 0, #E0ECFF 100%);
	background: linear-gradient(to bottom, #EFF5FF 0, #E0ECFF 100%);
	background-repeat: repeat-x;
	filter: progid:DXImageTransform.Microsoft.gradient(startColorstr=#EFF5FF,
		endColorstr=#E0ECFF, GradientType=0);
	font-size: 12px;
	font-weight: bold;
	color: #0E2D5F;
}
</style>
</head>
<script type="text/javascript">
	/**************工作流字段 begin******************/
	//中途划转处理申请
	var WORKFLOW_ID = "intermediateTransferProcess";
	var workflowTaskDefKey = "task_Request";
	nextRoleCode = "DEPARTMENT_MANAGER";//
	/**************工作流字段 end********************/
	var refId = ${intermediateTransfer.pid == null ? -1 : intermediateTransfer.pid};
	var projectId = ${projectId};
	var intermediateTransferId = ${intermediateTransfer.pid == null ? -1 : intermediateTransfer.pid};
	var specialType = ${intermediateTransfer.specialType== null ? -1 : intermediateTransfer.specialType};
	//var projectNum = "${projectNum}";
	function subForm() {
		// 提交表单
		$("#intermediateTransferForm").form(
				'submit',
				{
					onSubmit : function() {
						return $(this).form('validate');
					},
					success : function(data) {
						if (data > 0) {
							if (intermediateTransferId == null
									|| intermediateTransferId == -1) {
								intermediateTransferId = data;
								$("#pid").val(intermediateTransferId);
								$.messager.alert("系统提示", "保存成功！", "info");
							} else {
								$.messager.alert("系统提示", "更新成功！", "info");
							}
						} else {
							var ret = eval("(" + data + ")");
							$.messager
									.alert("系统提示", ret.header["msg"], "error");
						}
					}
				});
	}
	function refreshPage() {
		window.location.reload();
	}
	/**
	 * 关闭窗口
	 */
	function closeWindow() {
		var tab = parent.$('#layout_center_tabs').tabs('getSelected');
		var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
		parent.$('#layout_center_tabs').tabs('close', index);
	}
</script>
<body class="easyui-layout">
 <div data-options="region:'center',border:false">
  <div class="easyui-tabs" id="tabs" data-options="border:false" style="width: auto; height: auto">
   <div title="中途划转申请信息" id="tab1" style="padding: 10px;">
     <table>
      <tr>
       <td height="35" width="300" align="center">项目名称: <a href="#" onclick="formatterProjectOverview.disposeClick(${projectId})"><font color='blue'> ${projectName }</font></a></td>
       <td><div class="iniTitle">项目编号:${projectNumber}</div></td>
      </tr>
     </table>
    <form action="${basePath}intermediateTransferController/addOrUpdate.action" id="intermediateTransferForm" name="intermediateTransferForm" method="post">
     <table cellpadding="0" cellspacing="0" class="table_css" style="width: 100%; border-top: 1px #95B8E7 solid; border-left: 1px #95B8E7 solid;">
      <tr>
       <td><font color="red">*</font>到账金额：</td>
       <td><input name="recMoney" class="easyui-numberbox" value="${intermediateTransfer.recMoney }"
        data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"
       /></td>
       <td><font color="red">*</font>到账账号：</td>
       <td><input name="recAccount" class="easyui-textbox" value="${intermediateTransfer.recAccount }"
        data-options="required:true,validType:'length[0,30]'"
       ></td>
       <td><font color="red">*</font>账户户名：</td>
       <td><input name="recAccountName" class="easyui-textbox" value="${intermediateTransfer.recAccountName }"
        data-options="required:true,validType:'length[0,30]'"
       ></td>
       <td><font color="red">*</font>到账时间：</td>
       <td><input name="recDate" class="easyui-datebox" editable="false" value="${intermediateTransfer.recDate }" data-options="required:true"></td>
      </tr>
      <tr>
       <td><font color="red">*</font>划出金额：</td>
       <td><input name="transferMoney" class="easyui-numberbox" value="${intermediateTransfer.transferMoney }"
        data-options="required:true,min:0,max:9999999999,precision:2,groupSeparator:','"
       /></td>
       <td><font color="red">*</font>划出账户：</td>
       <td><input name="transferAccount" class="easyui-textbox" value="${intermediateTransfer.transferAccount }"
        data-options="required:true,validType:'length[0,30]'"
       ></td>
       <td><font color="red">*</font>账户户名：</td>
       <td><input name="transferAccountName" class="easyui-textbox" value="${intermediateTransfer.transferAccountName }"
        data-options="required:true,validType:'length[0,30]'"
       ></td>
       <td><font color="red">*</font>划出时间：</td>
       <td><input name="transferDate" class="easyui-datebox" editable="false" value="${intermediateTransfer.transferDate }" data-options="required:true"></td>
      </tr>
      <tr>
       <td>特殊情况类型：</td>
       <td colspan="7"><select class="select_style easyui-combobox" data-options="validType:'selrequired'" id="specialType" name="specialType" style="width: 150px" panelHeight="auto" editable="false">
         <option value=-1>--请选择--</option>
         <option value=1 <c:if test="${intermediateTransfer.specialType==1 }">selected </c:if>>客户自由资金到账</option>
         <option value=2 <c:if test="${intermediateTransfer.specialType==2 }">selected </c:if>>客户首期款优先</option>
       </select></td>
      </tr>
      <tr>
       <td>特殊情况备注：</td>
       <td colspan="7"><textarea rows="2" id="remark" name="remark" maxlength="500" style="width: 250px;">${intermediateTransfer.remark }</textarea></td>
      </tr> 
     </table>
     <input type="hidden" name="pid" id="pid" value="${intermediateTransfer.pid }"> <input type="hidden" name="projectId" id="projectId"
      value="${projectId }"
     >
    </form>
    <p class="jl">
     <a href="#" class="easyui-linkbutton" id="saveIntermediateTransfer" iconCls="icon-save" onclick="subForm()">保存</a> <a href="javascript:void(0)"
      class="easyui-linkbutton" iconCls="icon-cancel" onclick="closeWindow()"
     >关闭</a>
    </p>
    <div class="panel-body1 pt10" id="loanworkflowWrapDivPanel">
     <div class="easyui-panel" title="工作流程" data-options="collapsible:true" style="width: 99%;">
      <div style="padding: 5px">
       <%@ include file="../../common/loanWorkflow.jsp"%>
       <%@ include file="../../common/task_hi_list.jsp"%>
      </div>
     </div>
    </div>
   </div>
  </div>
 </div>
</body>
</html>