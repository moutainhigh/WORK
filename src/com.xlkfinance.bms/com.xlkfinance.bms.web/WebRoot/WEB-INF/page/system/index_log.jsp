<%@ page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
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
<script type="text/javascript">
	// 删除
	function removeItem() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		// 获取选中的日志ID
		var logPids = "";
		for (var i = 0; i < rows.length; i++) {
			var row = rows[i];
			logPids += row.pid + ",";
		}
		$.messager.confirm("操作提示", "确定删除选择的此批业务日志吗?", function(r) {
			if (r) {
				$.post("delete.action", {
					"logPids" : logPids
				}, function(ret) {
					//  此方法，不需要换转换json格式数据,因为传过来的值就是json格式的数据 
					if (ret && ret.header["code"] == 200) {
						$("#dlg").dialog('close');
						// 必须重置 datagr 选中的行 
						$("#grid").datagrid("clearChecked");
						$("#grid").datagrid('reload');
					} else {
						$.messager.alert('操作提示', ret.header["msg"], 'error');
					}
				}, 'json');
			}
		});
	}

	// 查询 
	function ajaxSearch() {
		$('#grid').datagrid('load', {
			modules : $("#modules").combobox('getValue'),
			logType : $("#logType").combobox('getValue'),
			realName : $('#realName').val(),
			projectName : $('#projectName').val(),
			logMsg : $('#logMsg').val()
		});
	}

	function systemModel(value, row, index) {
		if (value == "afterloan") {
			return "贷后管理";
		} else if (value == "beforeloan") {
			return "贷前管理";
		} else if (value == "contract") {
			return "合同管理";
		} else if (value == "credits") {
			return "额度管理";
		} else if (value == "customer") {
			return "客户管理";
		} else if (value == "finance") {
			return "财务管理";
		} else if (value == "mortgage") {
			return "抵制押物";
		} else if (value == "repayment") {
			return "还款管理";
		} else if (value == "system") {
			return "系统管理";
		} else if (value == "task") {
			return "任务管理";
		} else if (value == "workflow") {
			return "工作流";
		} else if (value == "inloan") {
			return "贷中管理";
		}
	}
	//初始化
	$(document).ready(function() {
		 $('#grid').datagrid({  
			 onDblClickRow: function (rowIndex, rowData){
				 $("#realName1").textbox('setValue',rowData.realName);
				 $("#projectName1").textbox('setValue',rowData.projectName);
				 $("#modules1").textbox('setValue',systemModel(rowData.modules));
				 $("#logDateTime").datebox('setValue',rowData.logDateTime);
				 $("#logMsgVal").val(rowData.logMsg);
				 $("#ipAddress").textbox('setValue',rowData.ipAddress);
				 $("#browser").textbox('setValue',rowData.browser);
				 
				 $('#log-dialog').dialog('open').dialog('setTitle', "查看日志");
			 }
		 });
		
	});
	
</script>
<title>业务日志管理</title>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
		<!--图标按钮 -->
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="getAllSysLog.action" method="post" id="searchFrom"
				name="searchFrom">
				<!-- 查询条件 -->
				<div style="margin: 5px">
					业务模块: <select id="modules" class="easyui-combobox" name="modules"
						style="width: 200px;">
						<option value="">--全部--</option>
						<option value="afterloan">贷后管理</option>
						<option value="beforeloan">贷前管理</option>
						<option value="inloan">贷中管理</option>
						<option value="contract">合同管理</option>
						<option value="credits">额度管理</option>
						<option value="customer">客户管理</option>
						<option value="finance">财务管理</option>
						<option value="mortgage">抵制押物</option>
						<option value="repayment">还款管理</option>
						<option value="system">系统管理</option>
						<option value="task">任务管理</option>
						<option value="workflow">工作流</option>
					</select> 日志类型: <select id="logType" class="easyui-combobox" name="logType"
						style="width: 200px;">
						<option value="">--全部--</option>
						<option value="添加">添加</option>
						<option value="删除">删除</option>
						<option value="修改">修改</option>
						<option value="登录">登录</option>
						<option value="登出">登出</option>
						<option value="上传文件">上传文件</option>
						<option value="贷中-业务办理">贷中-业务办理</option>
						<option value="贷中-财务受理">贷中-财务受理</option>
						<option value="贷中-查档">贷中-查档</option>
						<option value="贷中-查诉讼">贷中-查诉讼</option>
					</select>
				</div>
				<div style="margin: 5px">
					用户名称:<input class="easyui-textbox" name="realName" id="realName" />
                                                              项目名称:<input class="easyui-textbox" name="projectName" id="projectName" />
                                                              日志内容:<input class="easyui-textbox" name="logMsg" id="logMsg" />
					<a href="#" class="easyui-linkbutton" iconCls="icon-search"
						onclick="ajaxSearch()">查询</a>
				</div>
			</form>

			<!-- 操作按钮 -->
			<div style="padding-bottom: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-remove" plain="true" onclick="removeItem()">删除</a>
			</div>

		</div>
		<div class="sysLogDiv" style="height: 100%">
			<table id="grid" title="业务日志管理" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
			    url: 'getAllSysLog.action',
			    method: 'POST',
			    rownumbers: true,
			    singleSelect: false,
			    pagination: true,
			    sortOrder:'asc',
			    remoteSort:false,
			    toolbar: '#toolbar',
			    onLoadSuccess:function(data){
    				clearSelectRows('#grid');
    			},
			    idField: 'pid',
			    fitColumns:true">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true,width:50"></th>
						<th data-options="field:'realName',sortable:true,width:50">用户名称</th>
						<th data-options="field:'projectName',sortable:true,width:50">项目名称</th>
						<th data-options="field:'logType',sortable:true,width:50">日志类型</th>
						<th data-options="field:'modules',sortable:true,width:50,formatter:systemModel">业务模块</th>
						<th data-options="field:'ipAddress',sortable:true,width:80">ip地址</th>
						<th data-options="field:'browser',sortable:true,width:80">浏览器信息</th>
						<th data-options="field:'logDateTime',sortable:true,width:80">日志时间</th>
						<th data-options="field:'logMsg',sortable:true,width:200">日志内容</th>
					</tr>
				</thead>
			</table>
		</div>
		
		<div id="log-dialog" class="easyui-dialog" buttons="#dynamicHandleOperateDiv" style="width: 630px; height: 380px; padding: 10px" closed="true">
       <table style="width: 100%; height: 90%;">
         <tr>
          <td>用户名称:</td>
          <td><input name="realName" id="realName1" class="easyui-textbox" readonly="readonly" style="width: 100px;"></td>
          <td>项目名称:</td>
          <td><input name="projectName" id="projectName1" class="easyui-textbox" readonly="readonly" ></td>
         </tr>
         <tr>
         <tr>
          <td>业务模块:</td>
          <td><input name="modules" id="modules1" class="easyui-textbox" readonly="readonly" style="width: 100px;"></td>
          <td>日志时间:</td>
          <td><input name="logDateTime" id="logDateTime" class="easyui-datebox" readonly="readonly" style="width: 100px;"></td>
         </tr>
         <tr>
          <td>ip地址:</td>
          <td><input name="ipAddress" id="ipAddress" class="easyui-textbox" readonly="readonly" style="width: 100px;"></td>
         </tr>
         <tr>
          <td>浏览器信息:</td>
          <td colspan="3"><input name="browser" id="browser" class="easyui-textbox" readonly="readonly" style="width: 400px;"></td>
         </tr>
         <tr>
          <td>日志内容:</td>
          <td colspan="3"><textarea rows="2" id="logMsgVal" class="text_style" name="logMsg"  readonly="readonly" style="width: 402px;height: 153px;"></textarea></td>
         </tr>
       </table>
     </div>
     <div id="dynamicHandleOperateDiv">
      <a href="javascript:void(0)"class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#log-dialog').dialog('close')">取消</a>
     </div>
	</div>
	</div>
</body>
</html>
