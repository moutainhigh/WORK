<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>

<script type="text/javascript">
	function datiladvoperat(value, row, index) {
		return "<a href='javascript:void(0)' onclick='reprojectbyid(" + row.pid
				+ "," + row.projectId + "," + row.loanId
				+ ")'><font color='blue'> 坏账处理</font></a>";
	}

	function reprojectbyid(pid, projectId, loanId) {
		window.parent.addTab(
				"${basePath}badDebtController/viewBadDebt.action?pid=" + pid
						+ "&projectId=" + projectId + "&loanId=" + loanId,
				"坏账处理");
	}

	//查询 
	// function ajaxSearch(){
	// 	$('#searchFrom').form('submit',{
	//         success:function(data){
	//            	$('#grid').datagrid('loadData', eval("{"+data+"}"));
	//        }
	//     });
	// };

	function advApplyRepay(value, rowData, index) {
		var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.projectId+",\""+rowData.projectNumber+"\")' href='#'>" +
		"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
		return btn;
	}

	function doclear() {
		$("#projectName").textbox('setValue', '');
		$("#projectNumber").textbox('setValue', '');
		$("#cusName").textbox('setValue', '');
		$("#cusType").textbox('setValue', '');
		$("#reviewStatus").textbox('setValue', '');
		$('#ecoTrade').combobox('select', '-1');
		$('#startRequestDttm').textbox('setValue', '');
		$('#endRequestDttm').textbox('setValue', '');
		$(".ecoTradeDiv").hide();
	}

	$(document).ready(function() {
		$('#cusType').combobox({
			onSelect : function(row) {
				if (row.value == 2) {
					$(".ecoTradeDiv").show();
				} else {
					$(".ecoTradeDiv").hide();
				}
			}
		});
		$(".ecoTradeDiv").hide();
	});
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="getBadDebtlist.action" method="post" id="searchFrom"
				name="searchFrom">
				<!-- 查询条件 -->
				<div style="margin: 5px">
					<table class="beforeloanTable" width="90%">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input class="easyui-textbox" style="width: 235px;"
								name="projectName" id="projectName" /></td>
							<td class="label_right">项目编号：</td>
							<td><input class="easyui-textbox" style="width: 235px;"
								name="projectNumber" id="projectNumber" /></td>
						</tr>

						<tr>
							<td class="label_right">客户名称：</td>
							<td><input class="easyui-textbox" style="width: 235px;"
								name="cusName" id="cusName" />
							<td class="label_right">客户类别：</td>
							<td><select name="cusType" id="cusType" editable="false"
								class="easyui-combobox" style="width: 235px;">
									<option value="">--请选择--</option>
									<option value="1">个人</option>
									<option value="2">企业</option>
							</select></td>
						</tr>

						<tr>
							<td class="label_right">申请时间：</td>
							<td><input name="startRequestDttm" type="text"
								editable="false" class="easyui-datebox" style="width: 100px;"
								id="startRequestDttm" value="" />&nbsp;~&nbsp;<input
								name="endRequestDttm" type="text" editable="false"
								class="easyui-datebox" id="endRequestDttm" value=""
								style="width: 100px;" /></td>
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
							<td class="label_right">处理状态：</td>
							<td><select name="reviewStatus" id="reviewStatus"
								editable="false" class="easyui-combobox" style="width: 235px;">
									<option value="-1">全部</option>
									<option value="0">处理中</option>
									<!-- 						<option value="2">审核中</option> -->
									<option value="1">处理结束</option>
							</select></td>

							<td colspan="2">&nbsp;&nbsp; <a href="#"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="ajaxSearchPage('#grid','#searchFrom')">查询</a> <a
								href="#" class="easyui-linkbutton" iconCls="icon-clear"
								onclick="doclear()">重置</a>
							</td>
						</tr>

					</table>
				</div>
			</form>

			<!-- 操作按钮 -->
		</div>
		<div style="height: 100%">
			<table id="grid" title="坏账呆账处理列表" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
	    url: 'getBadDebtlist.action',
	    method: 'POST',
	    rownumbers: true,
	    singleSelect: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    idField: 'pid',
	    fitColumns:true,
	    singleSelect: false,
		selectOnCheck: true,
		checkOnSelect: true
	    ">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'projectId',hidden:true"></th>
						<th data-options="field:'loanId',hidden:true"></th>
						<th data-options="field:'pid',checkbox:true,width:100"></th>
						<th
							data-options="field:'projectName',width:100,formatter:advApplyRepay"
							align="center">项目名称</th>
						<th data-options="field:'projectNumber',width:100" align="center">项目编号</th>
						<th data-options="field:'cusType',width:100" align="center">客户类型</th>
						<th data-options="field:'badShouldAmt',width:100" align="center">坏账处理后应收金额</th>
						<th
							data-options="field:'shouldDt',width:100,formatter:convertDate"
							align="center">应收时间</th>
						<th data-options="field:'badLossAmt',width:100" align="center">坏账处理后损失金额</th>
						<th data-options="field:'reviewStatus',width:100" align="center">处理状态</th>
						<th
							data-options="field:'requestDttm',width:100,formatter:convertDate"
							align="center">申请时间</th>
						<th data-options="field:'yy',width:150,formatter:datiladvoperat">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
