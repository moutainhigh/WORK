<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>

<style type="text/css">
</style>
<script type="text/javascript">
	//查询 
	function repayList() {
		// 获取用户输入的逾期日期
		var beginDay = $("#overdueStartDay").textbox("getValue");
		var endDay = $("#overdueEndDay").textbox("getValue");
		if (parseInt(beginDay) > parseInt(endDay)) {
			$.messager.alert("操作提示", "已逾期日输入有误!", "error");
			return;
		}
		ajaxSearchPage('#rcGrid', '#searcFrom');
	}

	$(document).ready(function() {
		$("#ecoTradeId1").hide();
		$("#ecoTradeId2").hide();
		$('#acctType').combobox({
			onSelect : function(row) {
				var opts = $(this).combobox('options');
				if (row[opts.textField] == '企业') {
					$("#ecoTradeId1").show();
					$("#ecoTradeId2").show();
				} else {
					$("#ecoTradeId1").hide();
					$("#ecoTradeId2").hide();
				}

			}
		});
	})

	//绑定赋值  列表中文本框
	$(function() {
		$('#conlletGrid').datagrid({
			onSelect : function(rowIndex, rowData) {
				$('#conlletGrid').datagrid('beginEdit', rowIndex);
				isEdit = true;
			},
			unselectRow : function() {
				$('#conlletGrid').datagrid('endEdit', rowIndex);
				isEdit = false;
			},
			onBeforeEdit : function(index, row) {
				row.editing = true;
				$('#conlletGrid').datagrid('refreshRow', index);
			},
			onAfterEdit : function(index, row) {
				row.editing = false;
				$('#conlletGrid').datagrid('refreshRow', index);
			},
			onCancelEdit : function(index, row) {
				row.editing = false;
				$('#conlletGrid').datagrid('refreshRow', index);
			}
		});

	});

	//项目名称format
	var formatProjectName=function (value, rowData, index) {
		var btn = "<a id='"+rowData.pid+"' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("+rowData.pid+",\""+rowData.projectId+"\")' href='#'>" +
			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
		return btn;   
	}

	function openProjectDetail(projectId, projectName) {
		var url = "${basePath}beforeLoanController/addNavigation.action?projectId="
				+ projectId
				+ "&param='refId':'"
				+ projectId
				+ "','projectName':'" + projectName + "'";
		parent.openNewTab(url, "查看项目详情", true);
	}

	/***
	 * 

	 //查询 
	 function ajaxSearch() {
	
	 var data={
	 projectName: $("#projectName").val(),
	 projectNumber: $("#projectId").val(),
	 cusType: $('#cusType').combobox('getValue'),
	 expireStartDt:$('#appDate').datebox('getValue') ,
	 expireEndDt: $('#expireEndDt').datebox('getValue'),
	 ecoTrade: $('#telephone').textbox('getValue'),
	 overdueStartDay: $('#creditAmt').textbox('getValue'),
	 overdueEndDay: $('#creditAmt').textbox('getValue'),
	 requestStartDt: $('#realName').textbox('getValue'),
	 requestEndDt: $('#planOutLoanDt').datebox('getValue')
	 };
	
	 $('#conlletGrid').datagrid('load',data);
	 }
	 */

	//保存催收专员
	function saveTask() {
		var rows = $('#rcGrid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		var pid = new Array();
		var realName = new Array();
		var planDt = new Array();
		for (var i = 0; i < rows.length; i++) {
			var temp;
			var row = rows[i];
			var realNames = $('[value="' + rows[i].pid + '"]').parents('td')
					.siblings('td[field="realName"]').find('[type="hidden"]');
			var planOutLoanDts = $('[value="' + rows[i].pid + '"]').parents(
					'td').siblings('td[field="planOutLoanDt"]').find(
					'[type="hidden"]');
			pid.push(row.pid);
			realName.push(realNames.val());
			planDt.push(planOutLoanDts.val());
		}
		var isMail = "0";
		if ($(".is_mail:checked").val() == "1") {
			isMail = 1;
		}
		var isSms = "0";
		if ($(".is_sms:checked").val() == "1") {
			isSms = "1";
		}
		$.ajax({
			type : "POST",
			url : "conllectionTaskSave.action",
			data : {
				"pid" : pid,
				"realName" : realName,
				"planDt" : planDt,
				"isMail" : isMail,
				"isSms" : isSms
			},
			dataType : "text",
			success : function(data) {
				$('#addUserToRolegrid').datagrid('reload');
				$('#conlletGrid').datagrid('reload');
				$.messager.alert("系统提示", "添加成功", "success");
			}
		});
		$('#conlletGrid').datagrid("clearChecked");
	}

	//查看催收记录
	function datiloperat(value, row, index) {
		var va = "<a href='#' onClick='openCollectionRecord(";
		var re = ")'><font color='blue'>";
		var fo = "</font></a>";
		if (row.factReminderUser != null && row.factReminderUser != "") {
			return va + row.pid + re + "查看催收记录" + fo;
		} else {
			return "无催收记录";
		}
		
	}
	
	function openCollectionRecord(pid){
		var url="${basePath}afterLoanController/getCollectionRecord.action?projectId="+pid;
		parent.openNewTab(url, "还款催收记录", true);
	}

	//新增
	function addItem() {
		window.location.href = 'listCollectingDetail.action';
	}

	function addRepaymentCollection() {
		var rows = $('#rcGrid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		} else if (rows.length > 1) {
			$.messager.alert("操作提示", "每次只能设置一条数据为有效,请重新选择！", "error");
			return;
		}
		var pid = 0;
		var planId = "";
		var loanId = "";
		for (var i = 0; i < rows.length; i++) {
			pid = rows[i].pid;
			planId = rows[i].planId;
			loanId = rows[i].loanId;
		}
		var url = '${basePath}afterLoanController/getCollectionProjectCustomer.action?projectId='
				+ pid + "&planId=" + planId + "&loanId=" + loanId;
		parent.openNewTab(url, "还款催收", true);
		/* $('<div id="collectionDiv"/>').dialog({
			href : '${basePath}afterLoanController/getCollectionProjectCustomer.action?projectId='+projectId+'&pId='+pid,
			width : 800,
			height : 500,
			modal : true,
			title : '还款催收',
			onLoad : function() {
			}
		}); */

		/**
		var va="<a href='getCollectionProjectCustomer.action?projectId=";
		var pi="&pId=";
		var re=	 "'>";
		var fo= "</a>";
		alert(va+ projectId+pi+pid+re+fo);
		return va+ projectId+pi+pid+re+fo;
		 */
	}

	//查看对账流水
	function findReconciliation(loanId, pid) {
		if (loanId != 0) {
			$('<div id="listFinanceTotaldetailDiv"/>')
					.dialog(
							{
								href : '${basePath}financeController/getProjectTotalDetailList.action?loanId='
										+ loanId + '&projectId=' + pid,
								width : 800,
								height : 500,
								modal : true,
								title : '项目总流水账查看',
								onLoad : function() {
								}
							});

		}
	}

	function exportRepayReminder() {
		var rows = $('#rcGrid').datagrid('getSelections');
		var pids = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				pids += rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}
		$
				.ajax({
					url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data : {
						templateName : 'HKCSJLBB'
					},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
							window.location.href = "${basePath}afterLoanController/exportRepayReminder.action?pids="
									+ pids;
						} else {
							alert('导出excel模板不存在，请上传模板后重试');
						}
					}
				});

	}

	function makeCollectionNotice() {
		var rows = $('#rcGrid').datagrid('getSelections');
		if (rows.length > 1) {
			alert('只能选择一条数据进行生成催收通知单')
			return false;
		}
		$
				.ajax({
					url : BASE_PATH
							+ 'templateFileController/checkFileUrl.action',
					data : {
						templateName : 'HKCSTZD'
					},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
							window.location.href = '${basePath}afterLoanController/makeCollectionNotice.action?projectId='
									+ rows[0].pid;
						} else {
							alert('催收通知单模板不存在，请上传模板后重试');
						}
					}
				});
	}
</script>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="toolbar" class="easyui-panel" style="border-bottom: 0;">
			<form action="getRepaymentCollectionlist.action" method="post"
				id="searcFrom" name=searcFrom>
				<!-- 查询条件 -->
				<div style="padding: 5px">
					<table class="beforeloanTable">
						<tr>
							<td class="label_right">项目名称：</td>
							<td><input type="text" name="projectName"
								class="easyui-textbox" style="width: 220px;" /></td>
							<td class="label_right">项目编号：</td>
							<td colspan="3"><input type="text" name="projectNum"
								class="easyui-textbox" style="width: 220px;" /></td>
						</tr>
						<tr>
							<td class="label_right">客户名称：</td>
							<td><input type="text" name="cusName" class="easyui-textbox"
								style="width: 220px;" /></td>
							<td class="label_right">客户类别：</td>
							<td><input name="acctType" id="acctType"
								class="easyui-combobox" style="width: 220px;" editable="false"
								panelHeight="auto"
								data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=CUS_TYPE'" /></td>
							<td class="label_right" id="ecoTradeId1">经济行业类别：</td>
							<td id="ecoTradeId2"><input name="ecoTrade"
								class="easyui-combobox" editable="false" panelHeight="auto"
								data-options="valueField:'pid',textField:'lookupDesc',value:'-1',url:'<%=basePath%>sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'" /></td>
						</tr>
						<tr>
							<td class="label_right">贷款到期时间：</td>
							<td><input name="expireStartDt" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
								<span>~</span> <input name="expireEndt" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
							</td>
							<!-- 
					<td>
						<input class="easyui-combobox" name="ecoTrade" id="ecoTrade" style="width: 160px;" editable="false" 
				            data-options="
				            valueField:'pid',
				            textField:'lookupVal',
				            method:'get',
				            url:'/BMS/sysLookupController/getSysLookupValByLookType.action?lookupType=ECO_TRADE'"
						/>
					</td>
					 -->
							<td class="label_right">已逾期间(日)：</td>
							<td><input name="overdueStartDay" id="overdueStartDay"
								type="text" class="easyui-numberbox" style="width: 100px;" /> <span>~</span>
								<input name="overdueEndDay" id="overdueEndDay" type="text"
								class="easyui-numberbox" style="width: 100px;" /></td>
							<td class="label_right">贷款申请时间：</td>
							<td><input name="requestStartDt" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
								<span>~</span> <input name="requestEndDT" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
							</td>
						</tr>
						<tr>
							<td class="label_right">计划催收时间：</td>
							<td><input name="planStartDt" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
								<span>~</span> <input name="planEndDT" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
							</td>
							<td class="label_right">计划催收业务员：</td>
							<td><input class="easyui-combobox"
								data-options="valueField:'pid',textField:'realName',url:'${basePath }sysUserController/getUsersByRoleCode.action?roleCode=COLLETIONER'"
								name="planCollectionUser" style="width: 220px;" /></td>
						</tr>

						<tr>
							<td class="label_right">实际催收时间：</td>
							<td><input name="factStartDt" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
								<span>~</span> <input name="factEndDT" type="text"
								class="easyui-datebox" style="width: 100px;" editable="false" />
							</td>
							<td class="label_right">实际催收业务员：</td>
							<td><input class="easyui-combobox"
								data-options="valueField:'pid',textField:'realName',url:'${basePath }sysUserController/getUsersByRoleCode.action?roleCode=COLLETIONER'"
								name="factCollectionUser" style="width: 220px;" /></td>
							<td colspan="2"><a href="javascript:void(0);"
								class="easyui-linkbutton" iconCls="icon-search"
								onclick="repayList()">查询</a> <a href="#"
								class="easyui-linkbutton" iconCls="icon-clear"
								onclick="javascript:$('#searcFrom').form('reset');">重置</a></td>
						</tr>
					</table>

					<a class="easyui-linkbutton"
						data-options="iconCls:'icon-search',plain:true"
						onclick="addRepaymentCollection()">还款催收</a> <a
						class="easyui-linkbutton"
						data-options="iconCls:'icon-save',plain:true"
						onclick="makeCollectionNotice()">生成催收通知单</a> <a
						class="easyui-linkbutton"
						data-options="iconCls:'icon-export',plain:true"
						onclick="exportRepayReminder()">导出Excel</a>
				</div>
			</form>

			<!-- 操作按钮 -->
		</div>
		<div class="repaymentCollectDiv" style="height: 100%">
			<table id="rcGrid" title="还款催收查询" class="easyui-datagrid"
				style="height: 100%; width: auto;"
				data-options="
	    url: 'getRepaymentCollectionlist.action',
	    method: 'POST',
	    singleSelect: false,
	    rownumbers: true,
	    pagination: true,
	    toolbar: '#toolbar',
	    fitColumns:true
	    ">
				<!-- 表头 -->
				<thead>
					<tr>
						<th data-options="field:'pid',checkbox:true,width:50" id="pid"></th>
						<th
							data-options="field:'projectName',width:100,formatter:formatProjectName"
							align="center">项目名称</th>
						<th data-options="field:'projectId',width:100" id="projectId"
							align="center">项目编号</th>
						<th data-options="field:'acctType',width:100" align="center">客户类别</th>
						<th data-options="field:'appDate',width:100" align="center">申请时间</th>
						<th data-options="field:'telephone',width:100" align="center">联系电话</th>
						<th data-options="field:'creditAmt',width:80" align="center">贷款金额</th>
						<th data-options="field:'balanceAmt',width:120" align="center">到期未收金额</th>
						<th data-options="field:'realName',width:100" align="center">计划催收人员</th>
						<th data-options="field:'planOutLoanDt',width:100" align="center">计划催收日期</th>
						<th data-options="field:'factReminderUser',width:100"
							align="center">实际催收人员</th>
						<th data-options="field:'factPlanDt',width:102" align="center">实际催收日期</th>
						<th data-options="field:'cz',width:100,formatter:datiloperat">操作</th>
					</tr>
				</thead>
			</table>
		</div>
	</div>
</body>
