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
<script type="text/javascript"
	src="${ctx}/p/xlkfinance/module/system/cneter.js"></script>
<script type="text/javascript">
var url = "";
 
//查询 
function majaxSearch(){
    $('#grid').datagrid('load',{
	    projectNumber: $("#projectNumber").textbox("getValue"),
	    projectName: $("#projectName").textbox("getValue"),
	    acctName: $("#acctName").textbox("getValue"),
	    acctType: $("#acctType").combobox("getValue"),
	    requestStatus: $("#requestStatus").combobox("getValue"),
	    ecoTrade: $("#ecoTrade").combobox("getValue"),
	    beginRequestDttm:$("#beginRequestDttm").datebox("getValue"),
	    endRequestDttm:$("#endRequestDttm").datebox("getValue"),
	    beginCompleteDttm:$("#beginCompleteDttm").datebox("getValue"),
	    endCompleteDttm:$("#endCompleteDttm").datebox("getValue")
    }); 
}
 
// 查看
function editItem(mflag){
	var row = $('#grid').datagrid('getSelections');
	var proStatus=1;
 	if (row.length == 1) {
 		if(mflag==2 && row[0].requestStatusVal=="已归档"){
 			$.messager.alert("操作提示","选择项目已归档,请选择未归档的项目！","info");
				return;
 		}
 		if(mflag==2 && row[0].requestStatusVal=="未归档"){
 			proStatus=2;
 		}
 		window.parent.addTab("<%=basePath%>afterLoanFileController/afterLoanFileDateil.action?mflag="
							+ mflag + "&projectId=" + row[0].pid
							+ "&projectNumber=" + row[0].projectNumber
							+ "&proStatus="+proStatus,
					"办理项目归档");
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能查看一条数据,请重新选择！", "error");
		} else {
			$.messager.alert("操作提示", "请选择数据", "error");
		}
		/**
		 * 打开二级窗口
		
			var height = document.body.clientHeight*0.45;
			var width = document.body.clientWidth*0.7;
			$('#dksqListDiv11').dialog({
				width:width,
				height:height,
				title : "任务代办设定",
				modal:true
			}); */
	}
	$(function() {
		//为客户类别绑定事件
		$("#acctType").combobox({
			onChange : function(newValue, oldValue) {
				if (newValue == 2) {
					$(".ecoTradeDiv").show();
				} else {
					$(".ecoTradeDiv").hide();
					$("#ecoTrade").combobox('setValue', "");
				}
			}
		});
		//默认隐藏经济行业类别
		$(".ecoTradeDiv").hide();
	});
	
	function advApplyRepay(value, rowData, index){
		var btn = "<a onclick='formatterProjectName.disposeClick("+rowData.pid+",\""+rowData.projectNumber+"\")' href='#'>" +
		"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
		return btn;   
	} 
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
	<div id="scroll-bar-div">
		<!--图标按钮 -->
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form
				action="<%=basePath%>afterLoanFileController/getAfterLoadFileList.action"
				method="post" id="searchFrom" name="searchFrom">
				<!-- 查询条件 -->
				<div style="padding: 5px">
					<table>
						<tr>
							<td width="80" align="right" height="28">项目编号：</td>
							<td><input class="easyui-textbox" name="projectNumber"
								id="projectNumber" style="width: 220px;" /></td>
							<td width="80" align="right">项目名称：</td>
							<td><input class="easyui-textbox" name="projectName"
								id="projectName" style="width: 220px;" /></td>
							<td width="80" align="right">客户名称：</td>
							<td><input class="easyui-textbox" name="acctName"
								id="acctName" /></td>
						</tr>
						<tr>
							<td width="80" align="right">项目状态：</td>
							<td><select class="easyui-combobox" id="requestStatus"
								name="requestStatus" style="width: 220px;" panelHeight="auto"
								editable="false">
									<option value=-1 selected="selected">--请选择--</option>
									<option value=2>未归档</option>
									<option value=1>已归档</option>
							</select></td>
							<td width="80" align="right" height="28">客户类别：</td>
							<td><input name="acctType" id="acctType"
								class="easyui-combobox" style="width: 220px;" editable="false"
								panelHeight="auto"
								data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" /></td>

							<!-- 						<td width="120" align="right" id="ecoTradeId1">经济行业类别：</td> -->
							<%-- 						<td id="ecoTradeId2"><input name="ecoTrade" id="ecoTrade" class="easyui-combobox" editable="false" panelHeight="auto"  data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td> --%>
							<td class="label_right"><div class="ecoTradeDiv">经济行业类别：</div></td>
							<td>
								<div class="ecoTradeDiv">
									<input class="easyui-combobox" style="width: 235px;"
										name="ecoTrade" id="ecoTrade" editable="false"
										data-options="
				            valueField:'pid',
				            textField:'lookupVal',
				            method:'get',
				            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" />
								</div>
							</td>
						</tr>
						<tr>
							<td width="100" align="right" height="28">贷款申请时间：</td>
							<td><input name="beginRequestDttm" id="beginRequestDttm"
								class="easyui-datebox" editable="false" /> <span>~</span> <input
								name="endRequestDttm" id="endRequestDttm" class="easyui-datebox"
								editable="false" /></td>
							<td width="80" align="right" height="28">归档时间：</td>
							<td colspan="3"><input name="beginCompleteDttm"
								id="beginCompleteDttm" class="easyui-datebox" editable="false" />
								<span>~</span> <input name="endCompleteDttm"
								id="endCompleteDttm" class="easyui-datebox" editable="false" />
								&nbsp;&nbsp; <a href="#" class="easyui-linkbutton"
								iconCls="icon-search" onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a href="#"
								onclick="javascript:$('#searchFrom').form('reset');"
								iconCls="icon-clear" class="easyui-linkbutton"
								style="width: 60px;">重置</a></td>
						</tr>
					</table>
				</div>
			</form>

			<!-- 操作按钮 -->
			<div style="padding-bottom: 5px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-view" plain="true" onclick="editItem(1)">查看</a> <a
					href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-add" plain="true" onclick="editItem(2)">办理归档</a>
			</div>

		</div>
		<div class="dksqListDiv" style="height: 100%;">
			<table id="grid" title="归档项目查询管理" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
			    url: '<%=basePath%>afterLoanFileController/getAfterLoadFileList.action',
			    method: 'post',
			    rownumbers: true,
			    singleSelect: false,
			    pagination: true,
			    toolbar: '#toolbar',
			    idField: 'pid',
			    fitColumns:true">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true"></th>
						<th data-options="field:'projectName',width:80,formatter:advApplyRepay">项目名称</th>
						<th data-options="field:'projectNumber',width:80">项目编号</th>
						<th data-options="field:'acctTypeText',width:80">客户类别</th>
						<th data-options="field:'creditAmt',width:80">贷款金额</th>
						<th data-options="field:'realName',width:80">业务经理</th>
						<th data-options="field:'requestDttm',width:80">贷款申请时间</th>
						<th data-options="field:'completeDttm',width:80">归档时间</th>
						<th data-options="field:'requestStatusVal',width:80">归档状态</th>
					</tr>
				</thead>
			</table>
		</div>

		<div id="dksqListDiv11"></div>
	</div>
	</div>
</body>
</html>
