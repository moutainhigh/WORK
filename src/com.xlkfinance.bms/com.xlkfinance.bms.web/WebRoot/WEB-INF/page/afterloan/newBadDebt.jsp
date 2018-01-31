<%@page language="java" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<%@ include file="/layout/taglibs.jsp"%>

<html>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>违约处理申请</title>


<script type="text/javascript">
	var vpid = ${vio.pid};
	//工作流
	var Iam = "贷款违约申请";
	var WORKFLOW_ID = "breachOfContractRequestProcess";
	workflowTaskDefKey = "task_BreachOfContractRequest";
	var projectId = "${projectId}";// 项目ID
	var refId = "${vio.pid}";//  // 引用ID 违约对象的主键

	// 判断工作流提交前数据合法性
	function checkCommitTask() {
		if ("0" == refId) {
			$.messager.alert("提示", "请先保存违约处理数据", "info");
			return false;
		} else {
			return true;
		}
	}
	
	//违约呆账坏账
	function save() {
		if ($("#shouldDt").datebox('getValue') == "") {
			$.messager.alert("提示", "应收日期不能为空", "info");
			return false;
		}
		// 应收日期
		$("#violationDt").datebox('setValue',$("#shouldDt").datebox('getValue'));
		
		$('#myform').form('submit', {
			onSubmit : function() {
				return true;
			},
			success : function(result) {
				var data = jQuery.parseJSON(result);
				if (data.header.success) {
					var violationId = data.header.code; // 违约记录ID
					$("#pid").val(violationId);
					vpid = violationId;
					refId = violationId;// 赋值给工作流使用
					
					window.location.href = "${basePath}afterLoanController/listBreachDispose.action?projectId=${projectId}&violationId="+ $("#pid").val();

				} else {
					$.messager.alert("提示", data.header.msg, "error");
				}
			},
			error : function(result) {
				alert("保存失败！");
			}
		});
	}
	var vpid = '${vio.pid}';
	//重算违约
	function saveRepayPlan() {

		if (vpid > 0) {
			$.ajax({
						type : "POST",
						url : "${basePath}afterLoanController/advmakeRepayMent.action",
						data : {"projectId" : projectId,"proVioId" : vpid},
						dataType : "json",
						// async:false,
						success : function(data) {
							$.messager.alert("操作提示", "还款计划更新成功！", "info");
							window.location.href = "${basePath}afterLoanController/listBreachDispose.action?projectId=${projectId}&violationId="+ $("#pid").val();
						}
					});

		} else {
			$.messager.alert("操作提示", "请先保存违约信息！", "error");
		}
	}

	//  重算坏账呆账
	function reCalculationBadDebt() {
		var postData = {
			"pid" : "0",
			"asOfDT" : $("#asOfDT").datebox('getValue'),
			"shouldDt" : $("#shouldDt").datebox('getValue'),
			"projectId" : "${projectId}"
		};
		// 保存提交
		$.ajax({
			type : "POST",
			data : postData,
			url : "${basePath}/badDebtController/reCalculationBadDebt.action",
			dataType : "json",
			success : function(data) {
				// 保存成功
				if (data.header.success) {
					// 重新加载呆账坏账数据
					showBadDetInfo();
					// 保存呆账坏账记录
					save();
				}
				// 保存失败
				else {
					$.messager.alert("提示", data.header.msg, "error");
				}
			},
			error : function(result) {
				$.messager.alert("提示", "重算失败", "error");
			}
		});
	}

	// 展现违约信息
	function showBadDetInfo() {
		$('#detailTable').datagrid({
							url : "${basePath}/badDebtController/loanBadDetCalculation.action?loanId=${loanId}",
							collapsible : true,
							rownumbers : true,
							selectOnCheck : false,
							checkOnSelect : false,
							columns : [ [ {
								field : 'typeName',
								title : '类别',
								width : 120,
								align : 'center'
							}, {
								field : 'col1',
								title : '本金',
								formatter : formatMoney,
								width : 100,
								align : 'center'
							}, {
								field : 'col2',
								title : '管理费',
								formatter : formatMoney,
								width : 100,
								align : 'center'
							}, {
								field : 'col3',
								title : '其他费用',
								formatter : formatMoney,
								width : 100,
								align : 'center'
							}, {
								field : 'col4',
								title : '贷款利息',
								formatter : formatMoney,
								width : 100,
								align : 'center'
							}, {
								field : 'totalAmt',
								title : '合计',
								formatter : formatMoney,
								width : 100,
								align : 'center'
							} ] ]
						});
	}

	// 修改是否项目终止
	function setIsTerm() {
		$("#breachDiv").hide();
		$("#badDebtDiv").show();

		// 如果没有计算过呆账坏账，执行计算
		if ($('#detailTable').datagrid("getRows").length < 2) {
			reCalculationBadDebt();
		}
	}

	//工作流
	var Iam = "贷款违约申请";
	var WORKFLOW_ID = "breachOfContractRequestProcess";
	var projectId = ${projectId};// 项目ID
	$(function() {
		$('.formatterCss span').each(
				function() {
					if ($(this).text()) {
						$(this).text(
								accounting.formatMoney($(this).text(), "", 2,
										",", "."));
					}
		});

		// 当前不能修改了
		if ("${modifyAble}" == "false") {
			setReadOnly();
		}
		
		// 显示呆账坏账信息
		showBadDetInfo();
		setTimeout(function() {
				$("#breachDiv").hide();
				$("#badDebtDiv").show();
			}, 1000);
	});

	function setReadOnly() {
		$(".dueInfoDiv").find("input").attr("disabled", "disabled");
		$("#remark").attr("readonly", "readonly");
		$("#violationDt").datebox("editable", "false");
	}

	function changeProportion(newValue, oldValue) {
		var value = Number(newValue)* Number($('#loanAmt').numberbox('getValue') / 100);
		$('#violationAmt').numberbox('setValue', value);
	}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center'">
		<div id="main_body">

			<div class=" easyui-panel" title="项目基础信息"
				data-options="collapsible:true">
				<div id="cus_content">
					<div style="">
						<table class="cus_table" style="border: none">
							<tr>
								<td class="align_right">项目名称：</td>
								<td colspan="3">
									<h2>
										<a style="color:blue;cursor:pointer;" onclick="formatterProjectName.disposeClick('${bean.projectId}','${bean.projectNumber}')">${bean.projectName}</a>
									</h2>
								</td>
							</tr>
							<tr>
								<td class="align_right">项目编号：</td>
								<td colspan="3"><h2>${bean.projectNumber}</h2></td>
							</tr>
							<tr>
								<td class="align_right">业务类别：</td>
								<td style="width: 300px;"><h2>${bean.businessCatelog}</h2></td>
								<td class="align_right">业务品种：</td>
								<td><h2>${bean.businessType}</h2></td>
							</tr>
							<tr>
								<td class="align_right">流程类别：</td>
								<td><h2>${bean.flowCatelog }</h2></td>
								<td class="align_right">项目经理：</td>
								<td><h2 />${bean.realName}</h2></td>
							</tr>

						</table>
						<%--    <a href="javascript:void(0)" class="easyui-linkbutton" onclick="lookupRecord(${projectId},${loanId})">查看对账记录</a> --%>
					</div>
				</div>
			</div>
			<div class="pt10"></div>
			<div class=" easyui-panel" title="贷款收息表"
				data-options="collapsible:true">
				<jsp:include page="../repayment/list_loanCalculate.jsp"></jsp:include>
				<div style="padding-left: 10px;">
					<table>
						<tr>
							<td colspan="4" height="25"><div style="padding-left: 10px">
									<b>总计提示:</b>
								</div></td>
						</tr>
						<tr>
							<td height="25" class="formatterCss" style="width: 20%">&nbsp;&nbsp;&nbsp;&nbsp;贷款本金总额：
								<span>${custArrears.receivablePrincipalStr}</span>元
							</td>
							<td class="formatterCss" style="width: 20%">未还贷款本金： <span>${custArrears.principalSurplusStr}</span>元
							</td>
							<td class="formatterCss" style="width: 20%">应收费用总额： <span>${custArrears.totalFeedStr}</span>元
							</td>
							<td class="formatterCss" style="width: 20%">到期未收费用总额： <span>${custArrears.noReceiveTotalAmtStr}</span>元
							</td>
						</tr>
						<tr>
							<td colspan="4" height="25"><div style="padding-left: 10px">
									<b>逾期提示:</b>
								</div></td>
						</tr>
						<tr>
							<td height="25" style="width: 20%">&nbsp;&nbsp;&nbsp;&nbsp;逾期未还款项共计${custArrears.overdueCount}
								笔</td>
							<td class="formatterCss" style="width: 20%">逾期未还款项总额: <span>${custArrears.unReceivedOverdueInterestStr}</span>元
							</td>
							<td class="formatterCss" style="width: 20%">逾期违约金总额： <span>${custArrears.unReceivedOverduePunitiveStr}</span>元
							</td>

						</tr>

					</table>
				</div>
			</div>

			<!-- 违约处理 -->
			<div class="pt10"></div>
			<div class=" easyui-panel" title="坏账呆账处理"
				data-options="collapsible:true">
				<form id="myform"
					action="${basePath}afterLoanController/createBizProjectViolation.action"
					method="post">
					<input type="hidden" value="${loanId}" name="loanId" id="pid" />
					<input type="hidden" value="${vio.pid}" name="pid" id="pid" /> <input
						type="hidden" value="${projectId}" name="projectId" id="projectId" />
					<input type="hidden" value="${regHistoryId}" name="regHistoryId"
						id="regHistoryId" /> <input type="hidden" value="${vio.badId}"
						name="badId" id="badId" /> <input type="hidden" value="1"
						name="status" id="status" />
					<div class="dueInfoDiv">
						<table class="cus_table" style="border: none;">
							<tr>
								<td colspan="4" height="10"></td>
							</tr>
							<tr>
								<td class="label_right">
								<input name="isTermInAtion" id="isTermInAtion" type="hidden" value="1" >
								<span class="cus_red">*</span>是否将此客户：
								</td>
								<td colspan="3"><c:if test="${vio.isBackList==1}">
										<input name="isBackList" id="isBackList" type="checkbox"
											value="1" checked="checked" />
									</c:if> <c:if test="${vio.isBackList==0}">
										<input name="isBackList" id="isBackList" type="checkbox"
											value="1" />
									</c:if> 加入黑名单</td>
							</tr>
							<tr>
								<td colspan="4">
									<!-- 违约处理信息 -->
									<div id="breachDiv" style="margin-left: 40px;">
										<table style="width: 95%">
											<tr>
												<td class="label_right" style="width: 150px;"><span
													class="cus_red">*</span>贷款本金金额：</td>
												<td><input id="loanAmt" name="loanAmt"
													value="${vio.loanAmt}" class="easyui-numberbox"
													readonly="readonly" precision="2" groupSeparator=","
													required="true" /></td>
												<td class="label_right"><span class="cus_red">*</span>违约金比例：</td>
												<td><input name="violationProportion"
													data-options="onChange:changeProportion"
													value="${vio.violationProportion}" class="easyui-numberbox"
													precision="2" groupSeparator="," required="true" />%</td>
											</tr>
											<tr>
												<td class="label_right"><span class="cus_red">*</span>应收违约金额:</td>
												<td><input id="violationAmt" name="violationAmt"
													readonly="readonly" value="${vio.violationAmt}"
													class="easyui-numberbox" precision="2" groupSeparator=","
													style="width: 150px;" required="true" /></td>
												<td class="label_right"><span class="cus_red">*</span>应收日期：</td>
												<td><input name="violationDt" id="violationDt"
													value="${vio.violationDt}" class="easyui-datebox"
													required="true" /></td>
											</tr>
											<tr>
												<td class="label_right"><span class="cus_red">*</span>违约金逾期费率：</td>
												<td colspan="3"><input name="violationOtInterest"
													value="${vio.violationOtInterest}" class="easyui-numberbox"
													precision="2" groupSeparator="," required="true" />%日<span
													style="padding-left: 20px;">违约金逾期时，按此费率收取逾期罚息</span></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td class="label_right">备注：</td>
								<td colspan="3">
									<%--  <input id="remark" name="remark"
							value="${vio.remark}" class="easyui-textbox"
							data-options="width:400,height:50,multiline:true" /> --%> <textarea
										rows="4" cols="77" name="remark" id="remark"
										class="text_style" style="width: 600px; height: 60px">${vio.remark}</textarea>

								</td>
							</tr>
							<tr>
								<td colspan="4">
									<!--  坏账计算 -->
									<div id="badDebtDiv" style="margin-left: 40px;">
										<table style="width: 95%">
											<tr>
												<td>应收日期：&nbsp; <input id='shouldDt' name="shouldDt"
													class='endDateText easyui-datebox'
													value="${badDebtBean.shouldDt}"
													data-options='editable:false' />&nbsp; &nbsp;
													坏账记息截止日：&nbsp; <input id='asOfDT' name="asOfDT"
													class='endDateText easyui-datebox'
													value="${badDebtBean.asOfDT}" data-options='editable:false' />
													&nbsp;
												</td>
											</tr>
											<tr>
												<td>
													<table id="detailTable"></table>
												</td>
											</tr>
											<tr>
												<td></td>
											</tr>
										</table>
									</div>
								</td>
							</tr>
							<tr>
								<td colspan="4" align="center" height="35"><input
									type="button" class="save_data text_btn" onclick="reCalculationBadDebt();"
									value="保存"> <!-- 							 <input type="button" class="update_data long_text_btn" value="更新还款计划表" onclick="saveRepayPlan()">  -->
								</td>
							</tr>
						</table>
					</div>
				</form>
			</div>
			<!-- 资料上传列表 -->
			<div class="pt10"></div>
			<jsp:include page="loan_add_datum.jsp"></jsp:include>
			<!-- 工作流程 -->
			<c:if test="${showFlow}">
				<div class="panel-body1 pt10" id="loanworkflowWrapDivPanel"
					style="padding: 10px 0px 0 0px;">
					<div class="easyui-panel" title="工作流程"
						data-options="collapsible:true">
						<div style="padding: 5px">
							<%@ include file="../common/loanWorkflow.jsp"%>
							<%@ include file="../common/task_hi_list.jsp"%>
						</div>
					</div>
				</div>
			</c:if>

		</div>
	</div>
</body>
</html>