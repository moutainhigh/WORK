<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<title></title>
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
$(document).ready(function() {
	    var appCategory = '${appVersionInfo.appCategory>0?appVersionInfo.appCategory:-1}';
	    setCombobox3("appCategory","APP_CATEGORY",appCategory);
	});
	function subForm() {
		// 提交表单
		$("#appVersionInfoForm").form('submit', {
			onSubmit : function() {
				return $(this).form('validate');
			},
			success : function(data) {
				var ret = eval("(" + data + ")");
				if (ret && ret.header["success"]) {
					$.messager.alert("操作提示", ret.header["msg"]);
					cancel();
				} else {
					$.messager.alert('操作提示', ret.header["msg"], 'error');
				}
			}
		});
	}

	function cancel() {
		var pid = '${appVersionInfo.pid}';
		if (pid <= 0) {
			closeWindow();
		}
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
		<div class="easyui-tabs" id="tabs" data-options="border:false"
			style="width: auto; height: auto">
			<div title="APP版本信息" id="tab1" style="padding: 10px;">
				<form
					action="${basePath}sysAppVersionInfoController/addOrUpdate.action"
					id="appVersionInfoForm" name="appVersionInfoForm" method="post">
					<table cellpadding="0" cellspacing="0" class="table_css"
						style="width: 100%; border-top: 1px #95B8E7 solid; border-left: 1px #95B8E7 solid;">
						<tr>
							<td><font color="red">*</font>APP名称：</td>
							<td><input name="appName" class="easyui-textbox"
								value="${appVersionInfo.appName }"
								data-options="required:true,validType:'length[0,30]'" /></td>
							<td><font color="red">*</font>版本号：</td>
							<td><input name="appVersion" class="easyui-textbox"
								value="${appVersionInfo.appVersion }"
								data-options="required:true,validType:'appVersion'"></td>
						</tr>
						<tr>
							<td><font color="red">*</font>状态：</td>
							<td><select class="select_style easyui-combobox"
								data-options="validType:'selrequired'" id="status" name="status"
								style="width: 150px" editable="false">
									<option value=1
										<c:if test="${appVersionInfo.status==1 }">selected </c:if>>有效</option>
									<option value=-1
										<c:if test="${appVersionInfo.status==-1 }">selected </c:if>>无效</option>
							</select></td>
							<td><font color="red">*</font>系统类型：</td>
							<td><select class="select_style easyui-combobox"
								data-options="validType:'selrequired'" id="systemPlatform"
								name="systemPlatform" style="width: 150px" editable="false">
									<option value=1
										<c:if test="${appVersionInfo.systemPlatform==1 }">selected </c:if>>Android</option>
									<option value=2
										<c:if test="${appVersionInfo.systemPlatform==2 }">selected </c:if>>IOS</option>
							</select></td>
						</tr>
						<tr>
							<td><font color="red">*</font>强制升级状态：</td>
							<td><select class="select_style easyui-combobox"
								data-options="validType:'selrequired'"
								id="coercivenessUpgradesStatus"
								name="coercivenessUpgradesStatus" style="width: 150px"
								editable="false">
									<option value=1
										<c:if test="${appVersionInfo.coercivenessUpgradesStatus==1 }">selected </c:if>>不强制升级</option>
									<option value=2
										<c:if test="${appVersionInfo.coercivenessUpgradesStatus==2 }">selected </c:if>>强制升级</option>
							</select></td>
							<td><font color="red">*</font>app类型：</td>
							<td>
							<input name="appCategory" id="appCategory" data-options="validType:'selrequired'" class="easyui-combobox" editable="false"/>
							</td>
						</tr>
						<tr>
							<td>描述：</td>
							<td colspan="7"><textarea rows="2" id="appDescription"
									name="appDescription" maxlength="500" style="width: 250px;">${appVersionInfo.appDescription }</textarea></td>
						</tr>
					</table>
					<input type="hidden" name="pid" id="pid"
						value="${appVersionInfo.pid }">
				</form>
				<p class="jl">
					<a href="#" class="easyui-linkbutton" id="saveIntermediateTransfer"
						iconCls="icon-save" onclick="subForm()">保存</a> <a
						href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-cancel" onclick="closeWindow()">关闭</a>
				</p>
			</div>
		</div>
	</div>
</body>
</html>