/** ************工作流字段 begin***************** */
//var nextRoleCode; //下一处理人
var taskId; // 任务ID
var taskVo; // 任务对象
var allowTurnDown; // 驳回的节点
var workflowTaskDefKey; // 任务定义ID
var workflowInstanceId; // 流程ID
var taskNodeName; // 下一任务节点名称
var taskName; // 任务节点名称
var projectName; // 项目名称
var projectId; // 项目Id
var isEdit;
var nextRoleCodeStr;
/**
 * 解析参数
 */
function analysisParam() {
	/** ****************工作流字段代码 begin *********************/
	// 获取路径
	var url = decodeURI(window.location.href);

	
	var arr = url.split("=");
	if (nextRoleCode == '') {
		nextRoleCode = "BIZ_DIRECTOR";//
	}
	// 深圳总部项目
	if ('creditLoanRequestProcess' == WORKFLOW_ID || 'creditExtensionRequestProcess' == WORKFLOW_ID) {
		nextRoleCode = "RISK_ONE";
	}
	// 万通项目
	if ('foreLoanRequestProcess' == WORKFLOW_ID) {
		nextRoleCode = "INSPECTOR,DEPARTMENT_MANAGER";
	}
	
	// 万通项目
	if ('elementLendRequestProcess' == WORKFLOW_ID) {
		nextRoleCode = "DEPARTMENT_MANAGER";
	}
	
	// 将参数转换为Json对象
	var params;
	if (arr) {
		if (arr.length <= 1) {
			return;
		}
		if (arr[arr.length - 1]) {
			// 获取参数
			var param = "{" + arr[arr.length - 1] + "}";
			if (param.indexOf(":") > 0) {
				// 将参数转换为Json对象
				params = eval('(' + param + ')');
			
				taskVo = params;
				if (params) {
					nextRoleCode = params.nextRoleCode;
					taskId = params.workflowTaskId;
					allowTurnDown = params.allowTurnDown;
					workflowTaskDefKey = params.workflowTaskDefKey;
					workflowInstanceId = params.workflowInstanceId;
					projectId = params.projectId; 
					projectName = params.projectName;
					
					// 万通项目
					if ('elementLendRequestProcess' == WORKFLOW_ID && !nextRoleCode) {
						nextRoleCode = "DEPARTMENT_MANAGER";
						workflowTaskDefKey = "task_ElementLendProcess";
					}
					// 贷前流程控制跳转
					if (WORKFLOW_ID == "loanRequestProcess") {
						projectId = params.refId;
						// 提前还款申请工作流
					} else if (WORKFLOW_ID == "prepaymentRequestProcess") {
						preRepayId = params.refId;
						// 利率变更流程控制跳转
					} else if (WORKFLOW_ID == "interestChangeRequestProcess") {
						interestChgId = params.refId;
						// 违约处理工作流层控制跳转
					} else if ('breachOfContractRequestProcess' == WORKFLOW_ID) {
						interestChgId = params.refId;
						// 额度冻结与解冻工作流
					} else if ('creditFreezeOrThawRequestProcess' == WORKFLOW_ID) {
						limitId = params.refId;
						// 额度提款申请工作流
					} else if ('creditWithdrawalRequestProcess' == WORKFLOW_ID) {
						newProjectId = params.refId;
						// 展期申请工作流
					} else if ('extensionRequestProcess' == WORKFLOW_ID) {
						newProjectId = params.refId;
						// 费用减免工作流
					} else if ('feeWaiversRequestProcess' == WORKFLOW_ID) {
						repayId = params.refId;
						// 挪用处理工作流
					} else if ('misappropriateRequestProcess' == WORKFLOW_ID) {
						repayId = params.refId;
					} else if ('foreExtensionRequestProcess' == WORKFLOW_ID || 'creditExtensionRequestProcess' == WORKFLOW_ID) {
						newProjectId = params.refId;
						// 要件借出工作流
					}else if ('elementRequestProcess' == WORKFLOW_ID) {
						pid = params.refId;
						// 中途划转工作流
					}else if ('intermediateTransferProcess' == WORKFLOW_ID) {
						intermediateTransferId = params.refId;
						//放款申请工作流
					}else if ('makeLoansProcess' == WORKFLOW_ID) {
						makeLoansId = params.refId;
					//机构合作信息修改申请工作流
					}else if ('cooperationUpdateProcess' == WORKFLOW_ID) {
						orgCooperationUpdateApplyId = params.refId;
					}
					taskName = params.taskName;
					bianzhi = 1;// 标志为是流程
				}
				// 加载任务管理-任务处理详情列表数据 此函数在 task_hi_list.jsp页面
				loadProcessLoggingGrid(taskVo);
			}
		}
	}
}

/**
 * 重置表单
 */
function resetForm() {
	var tab = parent.$('#layout_center_tabs').tabs('getSelected');
	var index = parent.$('#layout_center_tabs').tabs('getTabIndex', tab);
	parent.$('#layout_center_tabs').tabs('close', index);
}
/**
 * 根据角色Code查询用户列表
 */
function findUsersByRoleCode() {
	taskNodeName = $(this).attr('taskNodeName');
	$('#userIds').val(null);
	var dataStr = [];
	if($(this).attr('code') && $(this).attr('code') != ""){
		$('#userIds')
		.combobox({
					url : BASE_PATH
							+ 'systemRoleController/findUsersByRoleCode.action?roleCode='
							+ $(this).attr('code'),
					valueField : 'pid',
					textField : 'realName',
					onLoadSuccess: function(data){
						if($("#commitToTask:checked").length== 1){
						for(var i = 0;i<data.length;i++){
							if($.inArray(data[i].pid, dataStr) == -1){
								dataStr.push(data[i].pid);
							}
					    }
						var roleCode = $("#commitToTask").attr('code');
						if(roleCode == 'RISK_ONE' || roleCode == 'INSPECTOR' || roleCode == 'CONSUME_RISK_ONE' || roleCode == 'CONSUME_INVESTIGATION'){
							$('#userIds').combobox("setValues",dataStr);
							$("#userIds").combobox("disable");
						}
			        }else{
			        	$('#userIds').val(null);
			        }
					}
				});
	}
	
}

/**
 * 提交任务时 贷前公共验证
 * 
 * @param projectId
 * @param destTaskId
 * @param workflowTaskDefKey
 */
function commitTaskValidate(projectId, surPid, destTaskId, workflowTaskDefKey) {

	if (destTaskId && destTaskId != '') {
		return true;
	}
	// 项目ID
	if (projectId && projectId == -1) {
		$.messager.alert("提示", "请先保存项目数据!", "info");
		return false;
		// 贷款ID
	} 
	
	if(productType == 2){
		if (findProjectLoanMoney(projectId) > 0) {
			
		}else{
			$.messager.alert("提示", "借款金额不能小于0!", "info");
			return false;
		}
	}
	//校验是否填写相关赎楼信息
	if(checkForeInfo(projectId) == 0){
		$.messager.alert("提示", "请先保存赎楼信息!", "info");
		return false;
	}
	//校验是否填写申请办理信息
	if(checkApplyHandleInfo(projectId) == 0){
		$.messager.alert("提示", "请先保存申请办理信息!", "info");
		return false;
	}
	
	// 验证是否添加合同信息
	/*if (!applyCommon.contractTabIsEdit(projectId)) {
		$.messager.alert("提示", "请先添加合同信息!", "error");
		return false;
	}*/
	
	if(findProjectForeState(projectId) >= 2 && workflowTaskDefKey == "task_LoanRequest"){
		$.messager.alert("提示", "贷款申请已提交!", "info");
		return false;
	}
	
	if (checkTrueOrFalse(workflowTaskDefKey) == false) {
		$.messager.alert("提示", checkMess, "error");
		return false;
	}
	return true;
}

/**
 * 提交任务时展期公共验证
 * 
 * @param projectId
 * @param destTaskId
 * @param workflowTaskDefKey
 */
function commitTaskValidateExtension(projectId, surPid, destTaskId, workflowTaskDefKey) {

	if (destTaskId && destTaskId != '') {
		return true;
	}
	// 项目ID
	if (projectId && projectId == -1) {
		$.messager.alert("提示", "请先保存项目数据!", "info");
		return false;
		// 贷款ID
	} 
	
	if (getProjectExtension(projectId) > 0) {
		
	}else{
		$.messager.alert("提示", "请先保存展期费用!", "info");
		return false;
	}
	
	// 验证是否添加合同信息
	/*if (!applyCommon.contractTabIsEdit(projectId)) {
		$.messager.alert("提示", "请先添加合同信息!", "error");
		return false;
	}*/
	
	if(findProjectForeState(projectId) >= 2 && workflowTaskDefKey == "task_LoanRequest"){
		$.messager.alert("提示", "贷款申请已提交!", "info");
		return false;
	}
	
	if (checkTrueOrFalse(workflowTaskDefKey) == false) {
		$.messager.alert("提示", checkMess, "error");
		return false;
	}
	return true;
}


/**
 * 提交任务流程
 */
function commitTask() {
	// 驳回的节点
	var destTaskId = null;
	if ($('#destTaskId') && $('#destTaskId').length > 0) {
		destTaskId = $('#destTaskId').combobox('getValue');
	}
	
    //退手续费工作流
	if('refundFeeProcess' == WORKFLOW_ID){
		var applyStatus=getRefundFeeApplyStatus(projectId,1);
		if(applyStatus == 1){
			$.messager.alert("提示","请先保存退手续费处理数据!","error");
			return;
		}
		if((applyStatus>3&&workflowTaskDefKey == "task_Request")){
			$.messager.alert('操作提示', '项目已申请退手续费', 'error');
			return;
		}else if(applyStatus>=5){
			$.messager.alert('操作提示', '退手续费工作流已申请完成', 'error');
			return;
		}else if(applyStatus==-1&&workflowTaskDefKey == "task_Request"){
			$.messager.alert('操作提示', '非该业务经理不能提交', 'error');
			return;
		}
		$('#loan_workflow_from #refId').val(projectId);
	//退利息费工作流
	}else if('refundInterestFeeProcess' == WORKFLOW_ID){
		var applyStatus=1;
		if (batchRefundFeeFlag==1) {//批量退咨询费
		    applyStatus=getBatchRefundFeeApplyStatus(batchRefundFeeMainId);
		} else {//单一退咨询费
			applyStatus=getRefundFeeApplyStatus(projectId,2);
		}
		if(applyStatus == 1){
			$.messager.alert("提示","请先保存退咨询费费处理数据!","error");
			return;
		}
		if(applyStatus>3&&workflowTaskDefKey == "task_Request"){
			$.messager.alert('操作提示', '项目已申请退咨询费!', 'error');
			return;
		}else if(applyStatus>=5){
			$.messager.alert('操作提示', '退利咨询费工作流已申请完成', 'error');
			return;
		}else if(applyStatus==-1&&workflowTaskDefKey == "task_Request"){
			$.messager.alert('操作提示', '非该业务经理不能提交', 'error');
			return;
		}
		$('#loan_workflow_from #refId').val(projectId);
	//退尾款工作流
	}else if('refundTailMoneyProcess' == WORKFLOW_ID||'WTrefundTailMoneyProcess' ==WORKFLOW_ID){
		var applyStatus=getRefundFeeApplyStatus(projectId,3);
		if(applyStatus == 1){
			$.messager.alert("提示","请先保存退尾款处理数据!","error");
			return;
		}
		if(applyStatus>3&&workflowTaskDefKey == "task_Request"){
			$.messager.alert('操作提示', '项目已申请退尾款!', 'error');
			return;
		}else if(applyStatus>=5){
			$.messager.alert('操作提示', '退尾款工作流已申请完成', 'error');
			return;
		}else if(applyStatus==-1&&workflowTaskDefKey == "task_Request"){
			$.messager.alert('操作提示', '非该业务经理不能提交', 'error');
			return;
		}
		$('#loan_workflow_from #refId').val(projectId);
		//退佣金工作流
	}else if('refundCommissionProcess' == WORKFLOW_ID){
		var applyStatus=getRefundFeeApplyStatus(projectId,4);
		if(applyStatus == 1){
			$.messager.alert("提示","请先保存退佣金处理数据!","error");
			return;
		}
		if((applyStatus>3&&workflowTaskDefKey == "task_Request")){
			$.messager.alert('操作提示', '项目已申请退佣金', 'error');
			return;
		}else if(applyStatus>=5){
			$.messager.alert('操作提示', '退佣金工作流已申请完成', 'error');
			return;
		}else if(applyStatus==-1&&workflowTaskDefKey == "task_Request"){
			$.messager.alert('操作提示', '非该业务经理不能提交', 'error');
			return;
		}
		$('#loan_workflow_from #refId').val(projectId);
		//中途划转工作流
	}else if('intermediateTransferProcess' == WORKFLOW_ID){
		if(intermediateTransferId && intermediateTransferId == -1){
			$.messager.alert("提示","请先保存数据!","info");
			return;
		}
		if(checkWorkFlowExist(projectId,4)>0&&workflowTaskDefKey == "task_Request"){
			$.messager.alert("提示","此项目已经有中途划转申请流程了！","info");
			return;
		}
		$('#loan_workflow_from #refId').val(intermediateTransferId);
		//放款申请工作流
	}else if('makeLoansProcess' == WORKFLOW_ID){
		if(makeLoansId && makeLoansId == -1){
			$.messager.alert("提示","请先保存数据!","info");
			return;
		}
		var productNumber = getProductNumber(projectId);
		var recPro=3;
		var applyStatus=getApplyFinanceHandleApplyStatus(projectId,recPro);//申请状态：未申请=1，申请中=2，驳回=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
		if((productNumber =="FJYYSLTFD" || "JYYSLTFD" ==productNumber) && applyStatus == 5){
			recPro=9;
			applyStatus=getApplyFinanceHandleApplyStatus(projectId,recPro);
		}
		if(applyStatus == 1){
			$.messager.alert("提示","请先保存放款数据!","error");
			return;
		}
		
		var financeStatus=getFinanceStatus(projectId);//未收费=1，已收费=2，已放款=3，放款未完结=4
		if(financeStatus==4){
			$.messager.alert("提示","放款未完结不能提交申请!","error");
			return;
		}

			if((applyStatus>3&&workflowTaskDefKey == "task_Request")){
				
				$.messager.alert('操作提示', '项目已提交放款申请', 'error');
				return;
			}else if(applyStatus>=5){
				$.messager.alert('操作提示', '放款工作流已申请完成', 'error');
				return;
			}
		$('#loan_workflow_from #refId').val(makeLoansId);
     //房抵贷放款工作流申请校验数据
	}else if('fddMakeLoansProcess' == WORKFLOW_ID){
		if(capitalName==''){
			$.messager.alert("提示","请先保存资金信息!","info");
			return;
		}
		var recPro=3;
		var applyStatus=getApplyFinanceHandleApplyStatus(projectId,recPro);// 申请状态：未申请=1，申请中=2，驳回=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
		if(applyStatus == 1){
			$.messager.alert("提示","请先保存资金信息数据!","error");
			return;
		}
	    //到了资金主管审核录入放款数据节点或者运营专员录入放款数据节点时，检查放款金额是否有值，有值则说明以保存放款数据
		if((recMoney==null||recMoney==0)&&(workflowTaskDefKey=='task_FundManagerCheck'||workflowTaskDefKey=='task_inputData')&&(destTaskId==null||destTaskId=="")){
			$.messager.alert("提示","请先保存放款数据!","error");
			return;
		}
		
		if((applyStatus>3&&workflowTaskDefKey == "task_Request")){
			
			$.messager.alert('操作提示', '项目已提交放款申请', 'error');
			return;
		}else if(applyStatus>=5){
			$.messager.alert('操作提示', '放款工作流已申请完成', 'error');
			return;
		}
		$('#loan_workflow_from #refId').val(refId);
	}
	//机构合作信息修改申请工作流
	else if('cooperationUpdateProcess' == WORKFLOW_ID){
		var applyStatus=getOrgCooperationUpdateApplyStatus(orgId);//申请状态：未申请=1，申请中=2，驳回=3，审核中=4，审核通过=5，审核不通过=6，已归档=7
		if(applyStatus == 1||applyStatus == 5||applyStatus == 6||applyStatus == 7){
			$.messager.alert("提示","请先保存修改机构合作信息数据!","error");
			return;
		}
		if((applyStatus == 4&&workflowTaskDefKey == "task_CooperationRequest")){
			$.messager.alert('操作提示', '审批正在审核中，请勿重复提交', 'error');
			return;
		}
		$('#loan_workflow_from #refId').val(orgCooperationUpdateApplyId);
	}
	if(("foreLoanRequestProcess" == WORKFLOW_ID || "creditLoanRequestProcess" == WORKFLOW_ID) && "task_LoanRequest" == workflowTaskDefKey){
		if(!checkProjectInfo()){
			return;
		}
	}
	
	if("businessApplyRequestProcess" == WORKFLOW_ID && "task_BusinessRequest" == workflowTaskDefKey){
		if(!checkProjectInfo()){
			return;
		}
	}
	
	//贷前审批流程
	if("foreLoanRequestProcess" == WORKFLOW_ID || "creditLoanRequestProcess" == WORKFLOW_ID ){
		if (!commitTaskValidate(projectId, surPid, destTaskId,
				workflowTaskDefKey)) {
			return;
		}
		$('#loan_workflow_from #refId').val(projectId);
	}
	
	//赎楼展期
	if("foreExtensionRequestProcess" == WORKFLOW_ID || "creditExtensionRequestProcess" == WORKFLOW_ID){
		
		if (!commitTaskValidateExtension(newProjectId, 1, destTaskId,
				workflowTaskDefKey)) {
			return;
		}
		$('#loan_workflow_from #refId').val(newProjectId);
	}
	
	if('elementLendRequestProcess' == WORKFLOW_ID){
		if(pid <= 0){
			$.messager.alert("提示","请先保存要件借出数据!","info");
			return;
		}
		
		$('#loan_workflow_from #refId').val(pid);
	}
	//合作申请
	if('cooperationRequestProcess' == WORKFLOW_ID){
		if(pid <= 0){
			$.messager.alert("提示","请先保存合作申请数据!","info");
			return;
		}
		
		$('#loan_workflow_from #refId').val(pid);
	}
	
	 if("businessApplyRequestProcess" == WORKFLOW_ID){
		 $('#loan_workflow_from #refId').val(projectId);
	 }
	
	$('#loan_workflow_from #workflowId').val(WORKFLOW_ID);
	$("#loan_workflow_from #workflowInstanceId").val(workflowInstanceId);
	$('#loan_workflow_from #taskNodeName').val(taskNodeName);
	$('#loan_workflow_from #taskId').val(taskId);
	$('#loan_workflow_from #projectId').val(projectId);
	$('#loan_workflow_from #workflowTaskDefKey').val(workflowTaskDefKey);
	// 提交的下一节点
	var commitToTask = $('.verifier:checked').val();
	var vetoProject_ch = $('#vetoProject_ch:checked').val();//审批拒单的操作
	
	// 选择的用户
	var userIds = null;
	if ($('#userIds') && $('#userIds').length > 0) {
		userIds = $('#userIds').combobox('getValues');
	}
	
	// 财务确认节点 特殊处理
	if ("TASK_FINANCIL_REPAYMENT" == nextRoleCode) {
		if ((commitToTask && commitToTask != "")
				|| (destTaskId && destTaskId != "")) {
			commitForm();
			return;
		} else {
			$.messager.alert("提示", "请您进行财务确认!或者驳回", "info");
			return;
		}
	} else if ("TASK_GENERAL_MANAGER_SPECIAL" == nextRoleCode) {
		if ((commitToTask && commitToTask != "")
				|| (destTaskId && destTaskId != "")) {
			commitForm();
			return;
		} else {
			$.messager.alert("提示", "请您进行财务确认!或者驳回!", "info");
			return;
		}
	}else if ("TASK_GENERAL_MANAGER" == nextRoleCode) {
		if ((commitToTask && commitToTask != "")
				|| (destTaskId && destTaskId != "")||(vetoProject_ch && vetoProject_ch !="")) {
			commitForm();
			return;
		} else {
			$.messager.alert("提示", "请您进行审核通过!或者驳回!", "info");
			return;
		}
		//风控复审
	}else if(("creditLoanRequestProcess" == WORKFLOW_ID || "creditExtensionRequestProcess" == WORKFLOW_ID )
			&& "task_RiskTwoCheck" == workflowTaskDefKey){
		var loanMoney = findProjectLoanMoney(projectId);
		if("creditExtensionRequestProcess" == WORKFLOW_ID){
			loanMoney = findProjectLoanMoney(newProjectId);
		}
		if(loanMoney >5000000||(loanMoney > 3000000 && 
				nextRoleCodeStr == 'RISK_OVER,TASK_GENERAL_MANAGER')){
				if ((commitToTask && commitToTask != "")
						|| (destTaskId && destTaskId != "")) {
					if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
						commitForm();
					} else {
						$.messager.alert("提示", "请选择人员!", "info");
						return;
					}
				} else {
					$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
					return;
				}
		}else{
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "") ||(vetoProject_ch && vetoProject_ch !="")) {
				commitForm();
				return;
			} else {
				$.messager.alert("提示", "请您进行风控确认审核!或者驳回", "info");
				return;
			}
		}
		//风控终审判断
	}else if(("creditLoanRequestProcess" == WORKFLOW_ID || "creditExtensionRequestProcess" == WORKFLOW_ID )
			&& "task_RiskOverCheck" == workflowTaskDefKey){
		var loanMoney = findProjectLoanMoney(projectId);
		if("creditExtensionRequestProcess" == WORKFLOW_ID){
			loanMoney = findProjectLoanMoney(newProjectId);
		}
		
		if(loanMoney > 10000000){
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
					commitForm();
				} else {
					$.messager.alert("提示", "请选择人员!", "info");
					return;
				}
			} else {
				$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
				return;
			}
		}else{
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")  ||(vetoProject_ch && vetoProject_ch !="")) {
				commitForm();
				return;
			} else {
				$.messager.alert("提示", "请您进行风控确认审核!或者驳回", "info");
				return;
			}
		}
		
	}else if("businessApplyRequestProcess" == WORKFLOW_ID && "task_RiskTwoCheck" == workflowTaskDefKey){
		var loanMoney = findProjectApplyMoney(projectId);
		
		if(loanMoney >5000000||(loanMoney > 3000000 && nextRoleCodeStr == 'RISK_OVER,TASK_GENERAL_MANAGER')){
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
					commitForm();
				} else {
					$.messager.alert("提示", "请选择人员!", "info");
					return;
				}
			} else {
				$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
				return;
			}
		}else{
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "") ||(vetoProject_ch && vetoProject_ch !="")) {
				commitForm();
				return;
			} else {
				$.messager.alert("提示", "请您进行风控确认审核!或者驳回", "info");
				return;
			}
		}
	}else if("businessApplyRequestProcess" == WORKFLOW_ID && "task_RiskOverCheck" == workflowTaskDefKey){
		var loanMoney = findProjectApplyMoney(projectId);
		
		if(loanMoney > 10000000){
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
					commitForm();
				} else {
					$.messager.alert("提示", "请选择人员!", "info");
					return;
				}
			} else {
				$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
				return;
			}
		}else{
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "") ||(vetoProject_ch && vetoProject_ch !="")) {
				commitForm();
				return;
			} else {
				$.messager.alert("提示", "请您进行风控确认审核!或者驳回", "info");
				return;
			}
		}
		
	}else if("foreLoanRequestProcess" == WORKFLOW_ID && "task_RiskDirectorCheck" == workflowTaskDefKey){
		var loanMoney = findProjectLoanMoney(projectId);
		if(loanMoney > 3000000){
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
					commitForm();
				} else {
					$.messager.alert("提示", "请选择人员!", "info");
					return;
				}
			} else {
				$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
				return;
			}
		}else{
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				commitForm();
				return;
			} else {
				$.messager.alert("提示", "请您进行风控总监审核!或者驳回", "info");
				return;
			}
		}
		//放款审批流程
	}else if("makeLoansProcess" == WORKFLOW_ID && "task_FundManagerCheck" == workflowTaskDefKey){
		// 放款金额
		var makeLoansMoney = getMakeLoansMoney(projectId,3);
		//查看第一次放款状态
		var applyStatus = getApplyFinanceHandleApplyStatus(projectId,3);
		//获取产品编码
		var productNumber = getProductNumber(projectId);
	
		if((productNumber =="FJYYSLTFD" || "JYYSLTFD" ==productNumber) && applyStatus == 5){
			recPro=9;
			makeLoansMoney = getMakeLoansMoney(projectId,recPro);
		}
		if(makeLoansMoney >= 3000000){
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
					commitForm();
				} else {
					$.messager.alert("提示", "请选择人员!", "info");
					return;
				}
			} else {
				$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
				return;
			}
		}else{
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				commitForm();
				return;
			} else {
				$.messager.alert("提示", "请您进行资金主管审核通过!或者驳回", "info");
				return;
			}
		}
	}else if("makeLoansProcess" == WORKFLOW_ID&& "task_FinanceDirectorCheck" == workflowTaskDefKey){	
		if ((commitToTask && commitToTask != "")
				|| (destTaskId && destTaskId != "")) {
			commitForm();
			return;
		} else {
			$.messager.alert("提示", "请您进行财务总监审核通过!或者驳回", "info");
			return;
		}
	}else if("fddMakeLoansProcess" == WORKFLOW_ID &&(workflowTaskDefKey=='task_FinanceDirectorCheck'||workflowTaskDefKey=='task_FundManagerCheck'||workflowTaskDefKey=='task_operateDepartmentCheck2')){	
		var bool=(commitToTask && commitToTask != "")|| (destTaskId && destTaskId != "")
		var makeLoansMoney = getMakeLoansMoney(projectId,3);// 放款金额
		//自由资金,放款金额大于等于三百万,财务总监审核则提交
		var bool1=(capitalName=='ziyouzijin'&&workflowTaskDefKey=='task_FinanceDirectorCheck'&&makeLoansMoney >= 3000000);
		//自由资金,放款金额小于三百万,资金主管审核则提交
		var bool2=(capitalName=='ziyouzijin'&&workflowTaskDefKey=='task_FundManagerCheck'&&makeLoansMoney < 3000000);
		if (bool&&(bool1||bool2||(capitalName!='ziyouzijin'&&workflowTaskDefKey=='task_operateDepartmentCheck2'))) {
			commitForm();
			return;
		} else if(!bool) {
			$.messager.alert("提示", "请您进行审核通过!或者驳回", "info");
			return;
		}else{
			commitForm();
			return;
		}
		//展期申请
	}else if("foreExtensionRequestProcess" == WORKFLOW_ID && "task_RiskDirectorCheck" == workflowTaskDefKey){
		var exFeeRate = checkFeeRate(newProjectId);
		if(exFeeRate == 0){
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
					commitForm();
				} else {
					$.messager.alert("提示", "请选择人员!", "info");
					return;
				}
			} else {
				$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
				return;
			}
		}else{
			if ((commitToTask && commitToTask != "")
					|| (destTaskId && destTaskId != "")) {
				commitForm();
				return;
			} else {
				$.messager.alert("提示", "请您进行风控总监审核!或者驳回", "info");
				return;
			}
		}
	} else if('elementLendRequestProcess' == WORKFLOW_ID){
		if ((commitToTask && commitToTask != "")
				|| (destTaskId && destTaskId != "")) {
			commitForm();
			return;
		} else {
			$.messager.alert("提示", "请您进行监管员审核通过!或者驳回", "info");
			return;
		}
		
	}else if("task_WindControlCommissionCheck" == workflowTaskDefKey){
		if ($('#vetoProject_ch:checked') && $('#vetoProject_ch:checked').length>0) {
			commitForm();
			//$.messager.alert("提示", "请您进行审核通过！", "info");
			return;
		} else if($('.verifier:checked') && $('.verifier:checked').length>0){
			commitForm();
			//$.messager.alert("提示", "请您进行项目否决！", "info");
			return;
		}else{
			$.messager.alert("提示", "请您进行风控委员会审核！", "info");
			return;
		}
	}
	//消费贷贷前申请流程
	else if('consumeLoanForeAppRequestProcess' == WORKFLOW_ID){
		// 校验消费贷申请信息
		if(projectId == -1) {
			$.messager.alert("操作提示","请选择保存贷款信息,才能提交审批!","error");
			return;
		}
		
		// 提交至复审
		if("task_InvestigationCheck" == workflowTaskDefKey) {		
			var investigationStatus = getInvestigationStatus(projectId); // < 1 下户调查未完成，不能提交至复审
			if (investigationStatus < 1) {
				$.messager.alert("提示","总部复审前下户调查未完结不能提交申请!","error");
				return;
			}
		}
		
		// 设置流程refId
		$('#loan_workflow_from #refId').val(projectId);
		// 如果是拒单操作，直接提交拒单流程或如果驳回操作，则直接提交驳回流程
		if ($('#vetoProject_ch:checked').length > 0 || !!destTaskId) {
			commitForm();
			return;
		}
		// 校验项目信息
		if(!checkProjectInfo()) {
			return;
		}
		// 如果是勾选提交下一流程节，则进入
		if (!!commitToTask) {
			// 校验是否勾选下一节点处理人，未勾选则提示并结束
			if ("task_RiskTwoCheck" != workflowTaskDefKey && (userIds == null || userIds.length == 0)) {
				$.messager.alert("提示", "请选择人员!", "info");
				return;
			}
		} else {
			$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
			return;
		}
		$("#personalForm").form('submit', {
			onSubmit: function() {return true;},
			success: function(result){
				$("#userIds").combobox("enable");
				commitForm();
			}
		});
	}
	// 房抵贷贷前申请工作流
	else if('mortgageLoanForeAppRequestProcess' == WORKFLOW_ID) {
		// 校验房抵贷申请信息
		if(projectId == -1) {
			$.messager.alert("操作提示","请选择保存贷款信息,才能提交审批!","error");
			return;
		}
		// 设置流程refId
		$('#loan_workflow_from #refId').val(projectId);
		// 如果是拒单操作，直接提交拒单流程或如果驳回操作，则直接提交驳回流程
		if ($('#vetoProject_ch:checked').length > 0 || !!destTaskId) {
			commitForm();
			return;
		}
		// 如果是勾选提交下一流程节，则进入
		if (!!commitToTask) {
			// 校验是否勾选下一节点处理人，未勾选则提示并结束
			if ("task_RiskOverCheck" != workflowTaskDefKey && (userIds == null || userIds.length == 0)) {
				$.messager.alert("提示", "请选择人员!", "info");
				return;
			}
		} else {
			$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
			return;
		}
		// 校验项目信息
		if(!checkProjectInfo()) {
			return;
		}
		var chargesType = $("#personalForm input[name='projectGuarantee.chargesType']").val();
		// 提交至下户
		if("task_AssessmentRequest" == workflowTaskDefKey) {
			var estimateStatus = getMortgageEstimateStatus(projectId); // < 1抵押评估未完成，不能提交至下户
			if (estimateStatus < 1) {
				$.messager.alert("提示","下户前抵押物评估未完结不能提交申请!","error");
				return;
			}
		}
		// 提交至复审
		if("task_InvestigationCheck" == workflowTaskDefKey) {
			if (chargesType == 1) {
				// 下户前未收费则提示收费后再提交业务流程
				var financeStatus = getFinanceStatus(projectId);//未收费=1，已收费=2，已放款=3，放款未完结=4
				if (financeStatus < 2) {
					$.messager.alert("提示","下户前收费未完结不能提交申请!","error");
					return;
				}
			}
			var investigationStatus = getInvestigationStatus(projectId); // < 1 下户调查未完成，不能提交至复审
			if (investigationStatus < 1) {
				$.messager.alert("提示","总部复审前下户调查未完结不能提交申请!","error");
				return;
			}
		}
		$("#personalForm").form('submit', {
			onSubmit: function() {return true;},
			success: function(result){
				commitForm();
			}
		});
	}
	else if ((!$('.verifier:checked') && !$('#userIds'))

			|| ($('.verifier:checked').length == 0 && ($('#userIds').length == 0))) {
		commitForm();
		return;
	} else if ($('#vetoProject_ch:checked')
			&& $('#vetoProject_ch:checked').length > 0) {
		commitForm();
		return;
	}else {
		if ((commitToTask && commitToTask != "")
				|| (destTaskId && destTaskId != "")) {
			if ((userIds && userIds != "") || (destTaskId && destTaskId != "")) {
				$("#userIds").combobox("enable");
				commitForm();
			} else {
				$.messager.alert("提示", "请选择人员!", "info");
				return;
			}
		} else {
			$.messager.alert("提示", "请勾选要提交的流程节点!", "info");
			return;
		}
	}
}

/**
 * 提交表单数据
 */
function commitForm() {
	$('#loan_workflow_from')
			.form(
					'submit',
					{
						success : function(data) {
							var data = eval('(' + data + ')');
							var header = data.header;
							if (header["success"] == true) {
								$.messager
										.alert(
												"提示",
												"提交成功！",
												"info",
												function() {
													var tab = parent
															.$(
																	'#layout_center_tabs')
															.tabs('getSelected');
													// 获取tabs对象
													var myObj = parent
															.$(
																	'#layout_center_tabs')
															.tabs('getTab',
																	'我的任务');
													var homeObj = parent
															.$(
																	'#layout_center_tabs')
															.tabs('getTab',
																	'首页');
													var atTitle = tab
															.panel('options').title;
													if (homeObj) {
														parent
																.layout_center_refreshTab('首页')
													}
													if (myObj) {
														// 获取iframe的URL
														var url = myObj[0].innerHTML;
														// 打开数据
														parent
																.layout_center_addTabFun({
																	title : '我的任务', // tab的名称
																	closable : true, // 是否显示关闭按钮
																	content : url,// 加载链接
																	falg : true
																});
														// 关闭选项卡面板
														parent
																.$(
																		'#layout_center_tabs')
																.tabs('close',
																		atTitle);
													} else if (tab) {
														var url = null;
														var title = null;
														// 贷款申请办理特殊处理
														if ("赎楼贷款申请(万通)" == atTitle || "编辑赎楼贷款申请(万通)" == atTitle || "赎楼贷款申请" == atTitle || "编辑赎楼贷款申请" == atTitle) {
															title = '贷款申请列表(赎楼)';
															url = encodeURI(sy
																	.bpName()
																	+ '/beforeLoanController/navigationFore.action');
															// 利率变更特殊处理
														}else if ("展期申请" == atTitle
																|| "展期申请编辑" == atTitle) {
															title = '展期申请列表';
															url = encodeURI(sy
																	.bpName()
																	+ '/foreLoanExtensionController/loanExtentionApplyList.action');
														}
														else if ("新增抵押贷款申请" == atTitle
																|| "编辑抵押贷款申请" == atTitle) {
															title = '抵押贷申请列表';
															url = encodeURI(sy
																	.bpName()
																	+ '/projectInfoController/index.action');
														}
														else if ("新增消费贷申请" == atTitle
																|| "编辑消费贷申请" == atTitle) {
															title = '消费贷申请列表';
															url = encodeURI(sy
																	.bpName()
																	+ '/consumeProjectInfoController/index.action');
														}
														else if ("修改要件借出" == atTitle || "要件借出申请" == atTitle) {
															title = '要件借出';
															url = encodeURI(sy
																	.bpName()
																	+ '/elementLendController/index.action');
														}else if ("退手续费办理" == atTitle) {
															title = '退手续费';
															url = encodeURI(sy
																	.bpName()
																	+ '/refundFeeController/index.action?type=1');
														}else if ("退咨询费办理" == atTitle||"批量退咨询费办理"== atTitle) {
															title = '退咨询费';
															url = encodeURI(sy
																	.bpName()
																	+ '/refundFeeController/index.action?type=2');
														}else if ("退尾款办理" == atTitle) {
															title = '退尾款';
															url = encodeURI(sy
																	.bpName()
																	+ '/refundFeeController/index.action?type=3');
														}else if ("退佣金办理" == atTitle) {
															title = '退佣金';
															url = encodeURI(sy
																	.bpName()
																	+ '/refundFeeController/index.action?type=4');
														}else if("新增机构合作申请" == atTitle || "修改机构合作申请" == atTitle){
															title = '机构合作管理';
															url = encodeURI(sy
																	.bpName()
																	+ '/orgCooperatCompanyApplyController/cooperateIndex.action');
														}else if("放款" == atTitle||"财务放款" == atTitle){
															title = '财务放款';
															url = encodeURI(sy
																	.bpName()
																	+ '/financeHandleController/make_loans_index.action');
														}
														if (url) {
															parent
																	.layout_center_addTabFun({
																		title : title, // tab的名称
																		closable : true, // 是否显示关闭按钮
																		content : createFrame(url),// 加载链接
																		falg : true
																	});
														}
														parent
																.$(
																		'#layout_center_tabs')
																.tabs('close',
																		atTitle);
													}
												});
							} else {
								$.messager.alert("提示", header["msg"], "error");
								return;
							}
						}
					});
}
/**
 * 添加连接
 */
function createFrame(url) {
	var s = '<iframe id="my_Agent_Task_grid_iframe" name="my_Agent_Task_grid_iframe" src="'
			+ url
			+ '" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>';
	return s;
}
/**
 * 根据角色codes查询角色ID
 */
function findRolesByRoleCodes(nextRoleCodeStr) {
	nextRoleCodeStr = nextRoleCodeStr;
	if ("TASK_FINANCIL_REPAYMENT" == nextRoleCodeStr) {
		var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ nextRoleCodeStr + '" class="verifier" value="'
				+ nextRoleCodeStr + '" taskNodeName="财务确认"/>'
				+ '<span id="transfer_down">财务确认</span></td></tr>';
		$("#next_node_tr").replaceWith(htmlStr);
	} else if ("TASK_GENERAL_MANAGER_SPECIAL" == nextRoleCodeStr) {
		var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ nextRoleCodeStr
				+ '" class="verifier" value="'
				+ nextRoleCodeStr
				+ '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span>'
				+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
				+ '<span id="transfer_down">否决</span></td></tr>';
		$('#vetoProject').val("pass");
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch();
		// 贷前深圳项目风控复审
	} else if ("creditLoanRequestProcess" == WORKFLOW_ID && "task_RiskTwoCheck" == workflowTaskDefKey) {
		// 贷款金额
		var loanMoney = findProjectLoanMoney(projectId);
		if (loanMoney >5000000||(loanMoney > 3000000 && nextRoleCodeStr == 'RISK_OVER,TASK_GENERAL_MANAGER')) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
/*			htmlStr += '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" class="vetoProject_ch_class" id="vetoProject_ch" type="checkbox" value="pass"/>'
			+ '<span id="transfer_down">项目否决</span>';*/
			
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
					
			
			$('#vetoProject').val("pass");
			
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
			attachEvent_vetoProject_ch();
		} else {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ "next" + '" class="verifier" value="'
				+ "next" + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span>'
				+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
				+ '<span id="transfer_down">审核拒单</span></td></tr>';

			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch2();
		}
		//展期深圳项目风控复审
	} else if ("creditExtensionRequestProcess" == WORKFLOW_ID && "task_RiskTwoCheck" == workflowTaskDefKey) {
		// 贷款金额
		var loanMoney = findProjectLoanMoney(newProjectId);
		if (loanMoney >5000000||(loanMoney > 3000000 && nextRoleCodeStr == 'RISK_OVER,TASK_GENERAL_MANAGER')) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		} else {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ "next" + '" class="verifier" value="'
				+ "next" + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span></td></tr>';
			$('#vetoProject').val("pass");
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch();
		}
		//深圳项目风控终审
	} else if ("creditLoanRequestProcess" == WORKFLOW_ID && "task_RiskOverCheck" == workflowTaskDefKey) {
		// 贷款金额
		var loanMoney = findProjectLoanMoney(projectId);
		if("creditExtensionRequestProcess" == WORKFLOW_ID){
			loanMoney = findProjectLoanMoney(newProjectId);
		}
		if (loanMoney > 10000000) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		} else {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ "next" + '" class="verifier" value="'
				+ "next" + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过'
				+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
				+ '<span id="transfer_down">审核拒单</span></td></tr>';
			
			
			$('#vetoProject').val("pass");
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch2();
		}
		
	}
	//展期深圳项目风控终审
	else if ("creditExtensionRequestProcess" == WORKFLOW_ID && "task_RiskOverCheck" == workflowTaskDefKey) {
		// 贷款金额
		var loanMoney = findProjectLoanMoney(projectId);
		if("creditExtensionRequestProcess" == WORKFLOW_ID){
			loanMoney = findProjectLoanMoney(newProjectId);
		}
		if (loanMoney > 10000000) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		} else {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ "next" + '" class="verifier" value="'
				+ "next" + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span></td></tr>';
			$('#vetoProject').val("pass");
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch();
		}
		
	}
	//展期申请
	else if("foreExtensionRequestProcess" == WORKFLOW_ID && "task_RiskDirectorCheck" == workflowTaskDefKey){
		var exFeeRate = checkFeeRate(newProjectId);
		if(exFeeRate == 0){
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		}else{
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ "next" + '" class="verifier" value="'
				+ "next" + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span></td></tr>';
			$('#vetoProject').val("pass");
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch();
		}
		
		// 总经理审批或者万通总经理审批特殊处理
	} else if (("businessApplyRequestProcess" == WORKFLOW_ID || "creditLoanRequestProcess" == WORKFLOW_ID)
			&& "task_GeneralCheck" == workflowTaskDefKey) {
		
			var htmlStr = '<tr id="next_node_tr">'
					+ '<td class="label_right" >业务提交至：</td>'
					+ '<td id="next_node_td">'
					+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
					+ nextRoleCodeStr + '" class="verifier" value="'
					+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
					+ '<span id="transfer_down">审核通过</span>'
					+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
					+ '<span id="transfer_down">审核拒单</span></td></tr>';
					
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch2();
		//赎楼贷（万通）审批流特殊处理
	}
//	start
	else if (("foreLoanRequestProcess" == WORKFLOW_ID || "creditExtensionRequestProcess" == WORKFLOW_ID || "foreExtensionRequestProcess" == WORKFLOW_ID)
			&& "task_GeneralCheck" == workflowTaskDefKey) {
		
			var htmlStr = '<tr id="next_node_tr">'
					+ '<td class="label_right" >业务提交至：</td>'
					+ '<td id="next_node_td">'
					+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
					+ nextRoleCodeStr + '" class="verifier" value="'
					+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
					+ '<span id="transfer_down">审核通过</span></td></tr>';
			$('#vetoProject').val("pass");
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch();
		//赎楼贷（万通）审批流特殊处理
	}else if("foreLoanRequestProcess" == WORKFLOW_ID && "task_LoanRequest" == workflowTaskDefKey){
		var loanMoney = 0.0;
		if(projectId != -1){
			loanMoney = findProjectLoanMoney(projectId);
		}
		var specialState = checkSpecialDesc(projectId);
		var roleCode = null;
		var roleName = null;
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">';
		//贷款申请下一节点，大于300万或者有特殊情况需要部门经理审批，否者审查员审批
		var url = BASE_PATH
		+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
					var datas = eval(data);
					var len = datas.length;
					for(var i = 0; i < len; i++){
						var value = datas[i].roleCode;
						if (loanMoney > 3000000 || specialState ==1) {
							if ("DEPARTMENT_MANAGER" == value) {
								roleCode = value;
								roleName = datas[i].roleName;
								break;
							}
						} else {
							if ("INSPECTOR" == value) {
								roleCode = value;
								roleName = datas[i].roleName;
								break;
							}
						}
					}
				}
			});
			
		htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ roleCode
			+ '" class="verifier" value="'
			+ roleCode 
			+ '" taskNodeName="'
			+ roleName
			+ '"/>'
			+ '<span id="transfer_down">'
			+ roleName
			+ '</span>&nbsp;&nbsp;&nbsp;';
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
			+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
	$("#next_node_tr").replaceWith(htmlStr);
	$('#userIds').val(null);
	$('#userIds').combobox({
		valueField : 'pid',
		textField : 'realName',
		multiple : true,
		editable : false,
		disabled : true
	});
	$('body').delegate('.verifier', 'change',
			findUsersByRoleCode);
	//部门经理审批节点，大于500万或者有特殊情况需要业务总监审，否则审查员审
	}else if("foreLoanRequestProcess" == WORKFLOW_ID && "task_OrgManagerCheck" == workflowTaskDefKey){
		var loanMoney = 0.0;
		if(projectId != -1){
			loanMoney = findProjectLoanMoney(projectId);
		}
		var specialState = checkSpecialDesc(projectId);
		var roleCode = null;
		var roleName = null;
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">';
		var url = BASE_PATH
		+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
					var datas = eval(data);
					var len = datas.length;
					for(var i = 0; i < len; i++){
						var value = datas[i].roleCode;
						if (loanMoney > 5000000 || specialState == 1) {
							if ("BIZ_DIRECTOR" == value) {
									roleCode = value;
									roleName = datas[i].roleName;
									break;
								}
						} else {
							if ("INSPECTOR" == value) {
								roleCode = value;
								roleName = datas[i].roleName;
								break;
							}
						}
					}
				}
			});
		
		htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ roleCode
			+ '" class="verifier" value="'
			+ roleCode
			+ '" taskNodeName="'
			+ roleName
			+ '"/>'
			+ '<span id="transfer_down">'
			+ roleName
			+ '</span>&nbsp;&nbsp;&nbsp;';
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
			+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
	$("#next_node_tr").replaceWith(htmlStr);
	$('#userIds').val(null);
	$('#userIds').combobox({
		valueField : 'pid',
		textField : 'realName',
		multiple : true,
		editable : false,
		disabled : true
	});
	$('body').delegate('.verifier', 'change',
			findUsersByRoleCode);
	//审查员审批节点，大于300万或者有特殊情况需要审查主管审，否则风控总监审
	}else if("foreLoanRequestProcess" == WORKFLOW_ID && "task_ExaminerCheck" == workflowTaskDefKey){
		var loanMoney = 0.0;
		if(projectId != -1){
			loanMoney = findProjectLoanMoney(projectId);
		}
		var specialState = checkSpecialDesc(projectId);
		var roleCode = null;
		var roleName = null;
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">';
		var url = BASE_PATH
		+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
					var datas = eval(data);
					var len = datas.length;
					for(var i = 0; i < len; i++){
						var value = datas[i].roleCode;
						if (loanMoney > 3000000 || specialState == 1) {
							if ("REVIEW_DEPARTMENT_SUPERVISOR" == value) {
									roleCode = value;
									roleName = datas[i].roleName;
									break;
								}
						} else {
							if ("RISK_DIRECTOR" == value) {
								roleCode = value;
								roleName = datas[i].roleName;
								break;
							}
						}
					}
				}
			});
		
		htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ roleCode
			+ '" class="verifier" value="'
			+ roleCode
			+ '" taskNodeName="'
			+ roleName
			+ '"/>'
			+ '<span id="transfer_down">'
			+ roleName
			+ '</span>&nbsp;&nbsp;&nbsp;';
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
			+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
	$("#next_node_tr").replaceWith(htmlStr);
	$('#userIds').val(null);
	$('#userIds').combobox({
		valueField : 'pid',
		textField : 'realName',
		multiple : true,
		editable : false,
		disabled : true
	});
	$('body').delegate('.verifier', 'change',
			findUsersByRoleCode);
	//风控总监审批节点，大于300万需要总经理审批，否者风控总监审批即可
	}else if("task_RiskDirectorCheck" == workflowTaskDefKey && "foreLoanRequestProcess" == WORKFLOW_ID){
		var loanMoney = findProjectLoanMoney(projectId);
		if (loanMoney > 3000000) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		} else {
			var htmlStr = '<tr id="next_node_tr">'
					+ '<td class="label_right" >业务提交至：</td>'
					+ '<td id="next_node_td">'
					+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
					+ "next" + '" class="verifier" value="'
					+ "next" + '" taskNodeName="审核通过"/>'
					+ '<span id="transfer_down">审核通过</span></td></tr>';
			$('#vetoProject').val("pass");
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch();
		}
		//中途划转
	}else if(("intermediateTransferProcess" == WORKFLOW_ID ) && "task_BizDirectorCheck" == workflowTaskDefKey){
		var roleCode = null;
		var roleName = null;
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">';
		var url = BASE_PATH
		+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
					var datas = eval(data);
					var len = datas.length;
					for(var i = 0; i < len; i++){
						var value = datas[i].roleCode;
						if (specialType == 1) {
							if ("IMPORTANT_DOCUMENT_KEEPER" == value) {
									roleCode = value;
									roleName = datas[i].roleName;
									break;
								}
						} else {
							if ("INSPECTOR" == value) {
								roleCode = value;
								roleName = datas[i].roleName;
								break;
							}
						}
					}
				}
			});
		
		htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ roleCode
			+ '" class="verifier" value="'
			+ roleCode
			+ '" taskNodeName="'
			+ roleName
			+ '"/>'
			+ '<span id="transfer_down">'
			+ roleName
			+ '</span>&nbsp;&nbsp;&nbsp;';
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
			+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
	$("#next_node_tr").replaceWith(htmlStr);
	$('#userIds').val(null);
	$('#userIds').combobox({
		valueField : 'pid',
		textField : 'realName',
		multiple : true,
		editable : false,
		disabled : true
	});
	$('body').delegate('.verifier', 'change',
			findUsersByRoleCode);
		//放款审批流程
	}else if(("makeLoansProcess" == WORKFLOW_ID ) && "task_FundManagerCheck" == workflowTaskDefKey){
		// 放款金额
		var makeLoansMoney = getMakeLoansMoney(projectId,3);
		//查看第一次放款状态
		var applyStatus = getApplyFinanceHandleApplyStatus(projectId,3);
		//获取产品编码
		var productNumber = getProductNumber(projectId);
		
		if((productNumber =="FJYYSLTFD" || "JYYSLTFD" ==productNumber) && applyStatus == 5){
			recPro=9;
			makeLoansMoney = getMakeLoansMoney(projectId,recPro);
		}

		if (makeLoansMoney >= 3000000) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		}else{
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ nextRoleCodeStr + '" class="verifier" value="'
				+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span></td></tr>';
		     $("#next_node_tr").replaceWith(htmlStr);
		}
	}else if(("makeLoansProcess" == WORKFLOW_ID ) && "task_FinanceDirectorCheck" == workflowTaskDefKey){
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ nextRoleCodeStr + '" class="verifier" value="'
			+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
			+ '<span id="transfer_down">审核通过</span></td></tr>';
	     $("#next_node_tr").replaceWith(htmlStr);
	     //房抵贷最后节点判断
	}else if("fddMakeLoansProcess" == WORKFLOW_ID  && ("task_FinanceDirectorCheck" == workflowTaskDefKey||"task_operateDepartmentCheck2" == workflowTaskDefKey||"task_FundManagerCheck" == workflowTaskDefKey)){
		// 放款金额
		var makeLoansMoney = getMakeLoansMoney(projectId,3);
		if (capitalName=='ziyouzijin'&&workflowTaskDefKey=='task_FundManagerCheck'&&makeLoansMoney >= 3000000) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		}else{
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ nextRoleCodeStr + '" class="verifier" value="'
				+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span></td></tr>';
		     $("#next_node_tr").replaceWith(htmlStr);
		}
		//房抵贷开始节点角色不处理，在放款页面已做处理
	}else if("fddMakeLoansProcess" == WORKFLOW_ID&&workflowTaskDefKey=="task_Request"){
		//要件借出审批流程
	}else if("task_Supervisor" == workflowTaskDefKey && "elementLendRequestProcess" == WORKFLOW_ID){
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ nextRoleCodeStr + '" class="verifier" value="'
			+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
			+ '<span id="transfer_down">审核通过</span></td></tr>';
		$('#vetoProject').val("pass");
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch();
	}else if("task_FinanceSupervisorCheck" == workflowTaskDefKey && "refundFeeProcess" == WORKFLOW_ID){
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<span id="transfer_down">审核通过</span></td></tr>';
		$('#vetoProject').val("pass");
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch();
	}else if("task_FinanceSupervisorCheck" == workflowTaskDefKey && "refundInterestFeeProcess" == WORKFLOW_ID){
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<span id="transfer_down">审核通过</span></td></tr>';
		$('#vetoProject').val("pass");
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch();
	}else if("task_FinanceSupervisorCheck" == workflowTaskDefKey && ("refundTailMoneyProcess" == WORKFLOW_ID||"WTrefundTailMoneyProcess" == WORKFLOW_ID)){
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<span id="transfer_down">审核通过</span></td></tr>';
		$('#vetoProject').val("pass");
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch();
	}else if("task_FinanceSupervisorCheck" == workflowTaskDefKey && "refundCommissionProcess" == WORKFLOW_ID){
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<span id="transfer_down">审核通过</span></td></tr>';
		$('#vetoProject').val("pass");
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch();
	}else if("task_FinanceSupervisorCheck" == workflowTaskDefKey && "intermediateTransferProcess" == WORKFLOW_ID){
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<span id="transfer_down">审核通过</span></td></tr>';
		$('#vetoProject').val("pass");
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch();
		//合作申请副总裁审
	}else if ("cooperationRequestProcess" == WORKFLOW_ID && "task_WindControlCommissionCheck" == workflowTaskDefKey) {
		
		var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ nextRoleCodeStr + '" class="verifier" value="'
				+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span>'
				+'&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" class="vetoProject_ch_class" id="vetoProject_ch" type="checkbox" value="pass"/>'
			+ '<span id="transfer_down">项目否决</span></td></tr>';
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch2();
	}else if ("cooperationUpdateProcess" == WORKFLOW_ID && "task_WindControlCommissionCheck" == workflowTaskDefKey) {
		
		var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ nextRoleCodeStr + '" class="verifier" value="'
				+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span>'
				+'&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" class="vetoProject_ch_class" id="vetoProject_ch" type="checkbox" value="pass"/>'
			+ '<span id="transfer_down">项目否决</span></td></tr>';
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch2();
		//业务申请风控复审
//		start
	}  else if ("businessApplyRequestProcess" == WORKFLOW_ID && "task_RiskTwoCheck" == workflowTaskDefKey) {
		// 借款金额
		var loanMoney = findProjectApplyMoney(projectId);

		if (loanMoney >5000000||(loanMoney > 3000000 && nextRoleCodeStr == 'RISK_OVER,TASK_GENERAL_MANAGER')) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		} else {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">'
				+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
				+ "next" + '" class="verifier" value="'
				+ "next" + '" taskNodeName="审核通过"/>'
				+ '<span id="transfer_down">审核通过</span>'
				+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
				+ '<span id="transfer_down">审核拒单</span></td></tr>';
			
			$('#vetoProject').val("pass");
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch2();
		}
		//业务申请风控终审
	}else if ("businessApplyRequestProcess" == WORKFLOW_ID && "task_RiskOverCheck" == workflowTaskDefKey) {
		// 借款金额
		var loanMoney = findProjectApplyMoney(projectId);

		if (loanMoney > 10000000) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		} else {
			var htmlStr = '<tr id="next_node_tr">'
					+ '<td class="label_right" >业务提交至：</td>'
					+ '<td id="next_node_td">'
					+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
					+ nextRoleCodeStr + '" class="verifier" value="'
					+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
					+ '<span id="transfer_down">审核通过</span>'
					+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
					+ '<span id="transfer_down">审核拒单</span></td></tr>';
					
			$("#next_node_tr").replaceWith(htmlStr);
			$('#vetoProject').val("pass");
			// 为否决绑定vetoProject_ch事件绑定
			attachEvent_vetoProject_ch2();
		}
	}else if ("refundInterestFeeProcess" == WORKFLOW_ID && "task_Request" == workflowTaskDefKey) {
		if (projectSource==1) {
			nextRoleCode = "DEPARTMENT_MANAGER";//
		} else {
			nextRoleCode = "OPERATE_DEPARTMENT";//
		}
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
	// 房抵贷申请流程-贷前审批流程
	} else if ("mortgageLoanForeAppRequestProcess" == WORKFLOW_ID && "task_MortgageLoanRequest" == workflowTaskDefKey) {
			var htmlStr = '<tr id="next_node_tr">'
				+ '<td class="label_right" >业务提交至：</td>'
				+ '<td id="next_node_td">';
			var url = BASE_PATH
					+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
	// 消费贷申请流程-贷前审批流程
	}else if ("consumeLoanForeAppRequestProcess" == WORKFLOW_ID && "task_ConsumeLoanRequest" == workflowTaskDefKey) {
		//console.info(">>>>>>>>>>>>task_ConsumeLoanRequest"+" nextRoleCode"+nextRoleCode);
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">';
		var url = BASE_PATH
				+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
							var datas = eval(data);
							htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
										+ datas[0].roleCode
										+ '" class="verifier" value="'
										+ datas[0].roleCode
										+ '" taskNodeName="'
										+ datas[0].roleName
										+ '"/>'
										+ '<span id="transfer_down">'
										+ datas[0].roleName + '</span>';
						}
		});
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
				+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
		$("#next_node_tr").replaceWith(htmlStr);
		$('#userIds').val(null);
		$('#userIds').combobox({
			valueField : 'pid',
			textField : 'realName',
			multiple : true,
			editable : false,
			disabled : true
		});
		$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		// 消费贷初审流程-贷前审批流程
	}else if ("consumeLoanForeAppRequestProcess" == WORKFLOW_ID && "task_ConsumeLoanFirstTrial" == workflowTaskDefKey) {
		//console.info(">>>>>>>>task_ConsumeLoanFirstTrial"+" nextRoleCode"+nextRoleCode);	
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			
			var url = BASE_PATH
			+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
			$.ajax({
				url : url,
				async : false,
				success : function(data) {
								var datas = eval(data);
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
											+ datas[0].roleCode
											+ '" class="verifier" value="'
											+ datas[0].roleCode
											+ '" taskNodeName="'
											+ datas[0].roleName
											+ '"/>'
											+ '<span id="transfer_down">'
											+ datas[0].roleName + '</span>'
											+ '<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
											+ '<span id="transfer_down">拒绝此单</span>';
							}
			});
			htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
					+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td>'
			
			$("#next_node_tr").replaceWith(htmlStr);
			$('#userIds').val(null);
			$('#userIds').combobox({
				valueField : 'pid',
				textField : 'realName',
				multiple : true,
				editable : false,
				disabled : true
			});
			$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch2();
	//消费贷 复审
	} else if ("consumeLoanForeAppRequestProcess" == WORKFLOW_ID && "task_RiskTwoCheck" == workflowTaskDefKey) {
		//console.info(">>>>>>>>>>>>task_RiskTwoCheck"+" nextRoleCode"+nextRoleCode);
		var htmlStr = '<tr id="next_node_tr">'
		+ '<td class="label_right" >业务提交至：</td>'
		+ '<td id="next_node_td">'
		+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
		+ nextRoleCodeStr + '" class="verifier" value="'
		+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
		+ '<span id="transfer_down">审核通过</span>'
		+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
		+ '<span id="transfer_down">审核拒单</span></td></tr>';
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch2();
		// 消费贷下户流程-贷前审批流程
	}else if ("consumeLoanForeAppRequestProcess" == WORKFLOW_ID && "task_InvestigationCheck" == workflowTaskDefKey) {
		//console.info(">>>>>>>>>>>>task_InvestigationCheck"+" nextRoleCode"+nextRoleCode);
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">';
		var url = BASE_PATH
				+ 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
							var datas = eval(data);
							htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
										+ datas[0].roleCode
										+ '" class="verifier" value="'
										+ datas[0].roleCode
										+ '" taskNodeName="'
										+ datas[0].roleName
										+ '"/>'
										+ '<span id="transfer_down">'
										+ datas[0].roleName + '</span>';
						}
		});
		htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
				+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
		$("#next_node_tr").replaceWith(htmlStr);
		$('#userIds').val(null);
		$('#userIds').combobox({
			valueField : 'pid',
			textField : 'realName',
			multiple : true,
			editable : false,
			disabled : true
		});
		$('body').delegate('.verifier', 'change', findUsersByRoleCode);
		
		// 房抵贷申请流程-贷前审批流程
	} else if ("mortgageLoanForeAppRequestProcess" == WORKFLOW_ID && "task_RiskOverCheck" == workflowTaskDefKey) {
		var htmlStr = '<tr id="next_node_tr">'
			+ '<td class="label_right" >业务提交至：</td>'
			+ '<td id="next_node_td">'
			+ '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
			+ nextRoleCodeStr + '" class="verifier" value="'
			+ nextRoleCodeStr + '" taskNodeName="审核通过"/>'
			+ '<span id="transfer_down">审核通过</span>'
			+ '&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" id="vetoProject_ch" class="vetoProject_ch_class"  type="checkbox" value="pass"/>'
			+ '<span id="transfer_down">审核拒单</span></td></tr>';
		$("#next_node_tr").replaceWith(htmlStr);
		$('#vetoProject').val("pass");
		// 为否决绑定vetoProject_ch事件绑定
		attachEvent_vetoProject_ch2();
	} else {
		var url = BASE_PATH + 'systemRoleController/findRolesByCodes.action?roleCode='+nextRoleCode;
		$.ajax({
			url : url,
			async : false,
			success : function(data) {
							var htmlStr = '<tr id="next_node_tr">'
									+ '<td class="label_right" >业务提交至：</td>'
									+ '<td id="next_node_td">';
							var datas = eval(data);
							var len = datas.length;
							if (len > 1) {
								for (var i = 0; i < len; i++) {
										htmlStr += '<input name="commitToTask" id="commitToTask" type="radio" code="'
												+ datas[i].roleCode
												+ '" class="verifier" value="'
												+ datas[i].roleCode
												+ '" taskNodeName="'
												+ datas[i].roleName
												+ '"/>'
												+ '<span id="transfer_down">'
												+ datas[i].roleName
												+ '</span>&nbsp;&nbsp;&nbsp;';
								}
							} else if(len == 1){
								htmlStr += '<input name="commitToTask" id="commitToTask" type="checkbox" code="'
										+ datas[0].roleCode
										+ '" class="verifier" value="'
										+ datas[0].roleCode
										+ '" taskNodeName="'
										+ datas[0].roleName
										+ '"/>'
										+ '<span id="transfer_down">'
										+ datas[0].roleName + '</span>';
								if("task_WindControlCommissionCheck" == workflowTaskDefKey){
									htmlStr +='&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" class="vetoProject_ch_class" id="vetoProject_ch" type="checkbox" value="pass"/>'
											+ '<span id="transfer_down">项目否决</span>';
											$('#vetoProject').val("pass");
								} else if ("mortgageLoanForeAppRequestProcess" == WORKFLOW_ID && ["task_AssessmentRequest", "task_RiskTwoCheck", "task_RiskOverCheck"].indexOf(workflowTaskDefKey) != -1) {
									htmlStr +='&nbsp;&nbsp;&nbsp;<input name="vetoProject_ch" class="vetoProject_ch_class" id="vetoProject_ch" type="checkbox" value="pass"/>'
										+ '<span id="transfer_down">拒绝此单</span>';
										$('#vetoProject').val("pass");
								}
							}
							htmlStr += '</td><td class="label_right pview">人员选择：</td> <td class="pview">'
									+ '<input id="userIds" name="userIds" class="easyui-combobox text_style"></td> </tr>';
							$("#next_node_tr").replaceWith(htmlStr);
							$('#userIds').val(null);
							$('#userIds').combobox({
								valueField : 'pid',
								textField : 'realName',
								multiple : true,
								editable : false,
								disabled : true
							});
							$('body').delegate('.verifier', 'change',
									findUsersByRoleCode);
							// 为否决绑定vetoProject_ch事件绑定
							attachEvent_vetoProject_ch();
						}
		});
	}
}

/**
 * 为否决绑定vetoProject_ch事件绑定
 */
function attachEvent_vetoProject_ch() {
	$('#vetoProject_ch').click(function() {
		
		if ($('#vetoProject_ch:checked').length) {// 选中
			$('#vetoProject').val("refuse");
			$('.pview').addClass('none');
			$('#commitToTask').attr("checked",false);
		} else {
			$('.pview').removeClass('none');
			$('#vetoProject').val("pass");
		}
	})
}

/**
 * 为否决绑定vetoProject_ch事件绑定
 */
function attachEvent_vetoProject_ch2() {
	$('#vetoProject_ch').click(function() {
		if ($('#vetoProject_ch:checked').length) {// 选中
		
			$('.pview').addClass('none');
			$('#vetoProject').val("refuse");
			$('#commitToTask').attr("checked",false);
		} else {
			$('.pview').removeClass('none');
			$('#vetoProject').val("pass");
		}
	});
	
	$('#commitToTask').click(function() {
		if ($('#commitToTask:checked').length) {// 选中
			$('#vetoProject_ch').val("refuse");
			$('#vetoProject').val("pass");
			$('#vetoProject_ch').attr("checked",false);
		} else {
			$('.pview').removeClass('none');
			$('#vetoProject').val("refuse");
		}
	});
}
/**
 * 判断jsp模块是否加载
 */
function hideOrShow(workflowTaskDefKey) {
	if (workflowTaskDefKey) {
		$("#include_approve_scrutiny").show();
		$("#include_offlineCreditMeetingSummary").show();
		$("#include_make_sign").show();
		$("#include_loanApprovalPaper").show();
		$("#include_approve_dizhiya").show();
		$("#include_projectArchive").show();
		$("#include_organization").show();
		$("#include_loan_output").show();
		$("#include_loan_output_list").show();
		//小科赎楼项目
		if(WORKFLOW_ID == "creditLoanRequestProcess"  && workflowTaskDefKey != "task_LoanRequest" && isEdit != 'isEdit' && workflowTaskDefKey != "task_RiskOverCheck"){
			// 禁用 新增共同借款人和删除共同借款人,项目基本信息以及贷款、赎楼信息保存按钮
			$("#personalSave").linkbutton("disable");
			$("#gtjkrAdd").linkbutton("disable");
			$("#gtjkrRemove").linkbutton("disable");
			$("#fore_info_click").linkbutton("disable");
			$("#investigation_save_click").linkbutton("disable");
			$("#project_info_click").linkbutton("disable");
			
			$(
			'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
			.attr('disabled', 'disabled');
			$(
					'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
					.attr('readOnly', 'readOnly');
			$(
					'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
					.addClass('l-btn-disabled');
			$('#tabs .easyui-linkbutton:not(.download)')
					.removeAttr('onclick');
			}
		//机构赎楼项目
		if(WORKFLOW_ID == "businessApplyRequestProcess"  && workflowTaskDefKey != "task_BusinessRequest" && isEdit != 'isEdit' && workflowTaskDefKey != "task_RiskOverCheck"){
			// 禁用 新增共同借款人和删除共同借款人,项目基本信息以及贷款、赎楼信息保存按钮
			$("#personalSave").linkbutton("disable");
			$("#fore_info_click").linkbutton("disable");
			$("#investigation_save_click").linkbutton("disable");
			$("#project_info_click").linkbutton("disable");
			
			$(
			'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
			.attr('disabled', 'disabled');
			$(
					'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
					.attr('readOnly', 'readOnly');
			$(
					'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
					.addClass('l-btn-disabled');
			$('#tabs .easyui-linkbutton:not(.download)')
					.removeAttr('onclick');
			}
		
		//万通赎楼贷前申请
		if(WORKFLOW_ID == "foreLoanRequestProcess" && workflowTaskDefKey != "task_LoanRequest" && workflowTaskDefKey != "task_ExaminerCheck" && isEdit != 'isEdit'){
			// 禁用 新增共同借款人和删除共同借款人,项目基本信息以及贷款、赎楼信息保存按钮
			$("#personalSave").linkbutton("disable");
			$("#gtjkrAdd").linkbutton("disable");
			$("#gtjkrRemove").linkbutton("disable");
			$("#fore_info_click").linkbutton("disable");
			$("#investigation_save_click").linkbutton("disable");
			$("#project_info_click").linkbutton("disable");
			$(
			'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
			.attr('disabled', 'disabled');
			$(
					'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
					.attr('readOnly', 'readOnly');
			$(
					'#tabs .easyui-linkbutton:not(.download) ,#tabs input:not(.download),#tabs textarea')
					.addClass('l-btn-disabled');
			$('#tabs .easyui-linkbutton:not(.download)')
					.removeAttr('onclick');
			$("#printAuditorOpinion").linkbutton("enable");
			$('#printAuditorOpinion').bind('click', function(){
				printAuditorOpinion();
		    });
		}
		
		if(WORKFLOW_ID == "elementLendRequestProcess" && workflowTaskDefKey != "task_ElementLendProcess"){
			$(
			'#elementLendForm .easyui-linkbutton:not(.download) ,#elementLendForm input:not(.download),#elementLendForm textarea')
			.attr('disabled', 'disabled');
			$(
					'#elementLendForm .easyui-linkbutton:not(.download) ,#elementLendForm input:not(.download),#elementLendForm textarea')
					.attr('readOnly', 'readOnly');
			$('#elementLendForm .easyui-linkbutton:not(.download)')
					.removeAttr('onclick');
		}
		//放款申请表单，如果不是第一个节点，其他节点时把表单禁用
		if(WORKFLOW_ID == "makeLoansProcess" && workflowTaskDefKey != "task_Request"){
			$("#saveFile").linkbutton("disable");
		}
		//放款申请（房抵贷
		if(WORKFLOW_ID == "fddMakeLoansProcess"){
			//如果不是第一个节点，其他节点时把资金表单禁用
			if (workflowTaskDefKey != "task_Request") {
				$("#saveCapitalNameInfoForm").linkbutton("disable");
			} 
			//如果不是待录入数据和资金主管审核节点，其他节点时把放款信息表单禁用
			if(workflowTaskDefKey != "task_inputData"&&workflowTaskDefKey != "task_FundManagerCheck"){
				$("#saveMakeLoansInfoForm").linkbutton("disable");
				$('#makeLoansInfoForm .easyui-linkbutton:not(.download) ,#makeLoansInfoForm input:not(.download),#makeLoansInfoForm textarea')
				.attr('readOnly', 'readOnly');
			}
		}
		if(WORKFLOW_ID == "cooperationUpdateProcess" && workflowTaskDefKey != "task_FundManagerCheck"){
			$("#saveCooperationUpdateApply").linkbutton("disable");
			$('#capitalNameInfoForm .easyui-linkbutton:not(.download) ,#capitalNameInfoForm input:not(.download),#capitalNameInfoForm textarea')
			.attr('readOnly', 'readOnly');
		}
		
		if ("task_InterestChangeRequest" == workflowTaskDefKey) {
			$("#include_approve_scrutiny").hide();
		} else if ("task_PrepaymentRequest" == workflowTaskDefKey) {
			if (WORKFLOW_ID == "prepaymentRequestProcess") {
				$("#include_financial_deal").hide();
				return;
			}
		}
	} else {
		$("#include_make_sign").hide();
		$("#include_approve_scrutiny").hide();
		$("#include_offlineCreditMeetingSummary").hide();
		$("#include_loanApprovalPaper").hide();
		$("#include_approve_dizhiya").hide();
		$("#include_projectArchive").hide();
		$("#include_organization").hide();
		$("#include_loan_output").hide();
		$("#include_loan_output_list").hide()
		return;
	}
}

$(document).ready(function() {
					// 解析参数
					analysisParam();
					// 加载任务管理-任务处理详情列表数据
					loadProcessLoggingGrid(taskVo);
					if (nextRoleCode) {
						findRolesByRoleCodes(nextRoleCode);
						if (allowTurnDown && allowTurnDown == "yes") {
							var htmlStr = '<td class="label_right pview">驳回至：</td><td class="pview" colspan="3">'
									+ "<input id='destTaskId' name='destTaskId' /></td>";
							$("#reject_select_tr").html(htmlStr);
							$('#destTaskId')
									.combobox(
											{
												url : BASE_PATH
														+ 'taskController/filterTurnDownTaskNodes.action?taskId='
														+ taskId,
												valueField : 'lookupVal',
												textField : 'lookupDesc',
												multiple : false,
												editable : false,
												onSelect : function(record) {
													var lookupVal = record.lookupVal;
													taskNodeName = record.lookupDesc;
													//console.info("lookupVal="+lookupVal+" taskNodeName"+taskNodeName);
													if (lookupVal == "") {
														$("#commitToTask").attr("disabled", false);
														$("#vetoProject_ch").attr("disabled", false);

														// $('#userIds').combobox({disabled:false});
														// //启用该combobox
													} else {

														$("#commitToTask").attr("disabled",true);
														$("#vetoProject_ch").attr("disabled",true);
														//$('#userIds').combobox({disabled:true});
														// //禁用该combobox
													}
												},
												formatter : function(row) {
													var opts = $(this)
															.combobox('options');
													var textField = row[opts.textField];
														return textField;
												}
											});
						}
						//s2 操作 勾选审批助理 隐藏 驳回至这一行
						$('body')
								.delegate(
										'#commitToTask',
										'click',
										function() {
											$('#destTaskId').combobox(
													'setValue', '');
											if ($(this).is(':checked')) {
												$('.pview').removeClass('none');
												$('#vetoProject_ch').attr("checked", false);
												$('#vetoProject').val("pass");
												$("#reject_select_tr")
														.addClass('none');
												$('#userIds').combobox({
													disabled : false
												}); //启用该combobox
											} else {
												$("#reject_select_tr")
														.removeClass('none');
												$('#userIds').combobox({
													disabled : true
												}); //禁用该combobox
											}
										});
					} else {
						$('.pview').addClass('none');
					}
					if (taskId) {
						$("#taskId").val(taskId);
					}
					if (projectName && isEdit != 'isEdit') {
						$(
								'.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea')
								.attr('disabled', 'disabled');
						$(
								'.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea')
								.attr('readOnly', 'readOnly');
						$(
								'.panel-body .easyui-linkbutton:not(.download) ,.panel-body input:not(.download),.panel-body textarea')
								.addClass('l-btn-disabled');
						$('.panel-body .easyui-linkbutton:not(.download):not(#bankHistory):not(#loanApplyHistory)').removeAttr('onclick');
						$('.common_flow').addClass('none');
						if((WORKFLOW_ID=="intermediateTransferProcess"||WORKFLOW_ID=="elementLendRequestProcess"||WORKFLOW_ID=="makeLoansProcess"||WORKFLOW_ID=="cooperationUpdateProcess")&&refId>0){
							loadHistoryApprover2(projectId, WORKFLOW_ID,refId);
						}else{
							loadHistoryApprover(projectId, WORKFLOW_ID);
						}
						$("#printAuditorOpinion").linkbutton("enable");
						$("#bankHistory").linkbutton("enable");
						$("#loanApplyHistory").linkbutton("enable");
						
						$('#printAuditorOpinion').bind('click', function(){
							printAuditorOpinion();
					    });
					}
					if(isEdit && isEdit == 'isEdit'){
						$('.common_flow').addClass('none');
						loadHistoryApprover(projectId, WORKFLOW_ID);
					}
					findUsersByRoleCode();
					//控制页面显示
					hideOrShow(workflowTaskDefKey);
				});


function loadHistoryApprover2(projectId, WORKFLOW_ID,refId) {
	$.ajax({
		type : "GET",
		url : BASE_PATH + "workflowController/findWorkflowProject.action",
		data : {
			"projectId" : projectId,
			"processDefinitionKey" : WORKFLOW_ID,
			"refId" : refId
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data != null) {
				//workflowTaskDefKey = data.workflowTaskDefKey;
				hideOrShow(data.workflowTaskDefKey);
				var resData = {
						workflowInstanceId : data.workflowInstanceId
				};
				loadProcessLoggingGrid(resData);
			}
		}
	});
}
function loadHistoryApprover(projectId, WORKFLOW_ID) {
	$.ajax({
		type : "GET",
		url : BASE_PATH + "workflowController/findWorkflowProject.action",
		data : {
			"projectId" : projectId,
			"processDefinitionKey" : WORKFLOW_ID
		},
		dataType : "json",
		async : false,
		success : function(data) {
			if (data != null) {
				//workflowTaskDefKey = data.workflowTaskDefKey;
				hideOrShow(data.workflowTaskDefKey);
				var resData = {
						workflowInstanceId : data.workflowInstanceId
				};
				loadProcessLoggingGrid(resData);
			}
		}
	});
}

var checkMess;//未确认警告
//验证流程必须确认的内容
function checkTrueOrFalse(wflowTaskDefKey) {
	if ("task_ConfirmSingleLoanApproval" == wflowTaskDefKey) {
		var rows = $('#fkqlstjGrid').datagrid('getData');
		if (rows.total >= 1) {//超过一条就确认，没有纪录就可以提交
			for (var i = 0; i < rows.total; i++) {
				if (rows.rows[i].isConfirm != 1) {
					checkMess = "请确认全部的落实条件";
					return false;
				}
			}
		}
	}
	if ("task_ProjectArchive" == wflowTaskDefKey) {
		var rows = $('#projectFileArchive').datagrid('getData');
		for (var i = 0; i < rows.total; i++) {
			if (rows.rows[i]) {
				if (rows.rows[i].isArchiveValue == '否') {
					checkMess = "请确认归档全部的归档资料";
					return false;
				}
			}
		}
	}
	return true;
}
/**
 * 判断是否有抵押
 * @param projectId 项目ID
 */
function fuIsPledge(projectId) {
	var url = BASE_PATH
			+ "mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId="
			+ projectId + "&mortgageGuaranteeType=-1&oldProject=-1";
	var isPledge = 0;
	//查询是否有抵押
	$.ajax({
		url : url,
		async : false,
		success : function(ret) {
			var param = eval('(' + ret + ')');
			isPledge = param.length;
		}
	});
	if (isPledge > 0) {
		return false;
	}
	return true;
}
//获取项目贷款金额
function findProjectLoanMoney(projectId) {
	var url = BASE_PATH
			+ "beforeLoanController/findProjectLoanMoney.action?projectId="
			+ projectId;
	var loanMoney = 0;
	//查询是否有抵押
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			loanMoney = data;
		}
	});

	return loanMoney;
}

//获取业务申请借款金额
function findProjectApplyMoney(projectId) {
	var url = BASE_PATH
			+ "bizProjectController/getLoanMoney.action?projectId="
			+ projectId;
	var loanMoney = 0;
	//查询是否有抵押
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			loanMoney = data;
		}
	});

	return loanMoney;
}

//获取项目状态
function findProjectForeState(projectId) {
	var url = BASE_PATH
			+ "secondBeforeLoanController/getProjectForeStates.action?projectId="
			+ projectId;
	var foreState = 0;
	
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			foreState = data;
		}
	});

	return foreState;
}

//获取赎楼信息是否填写
function checkForeInfo(projectId) {
	var url = BASE_PATH
			+ "secondBeforeLoanController/checkForeInfo.action?projectId="
			+ projectId;
	var foreState = 0;
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			foreState = data;
		}
	});

	return foreState;
}

//获取客户经理是否提那些申请办理信息
function checkApplyHandleInfo(projectId) {
	var url = BASE_PATH
			+ "secondBeforeLoanController/checkApplyHandleInfo.action?projectId="
			+ projectId;
	var applyHandleState = 0;
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			applyHandleState = data;
		}
	});

	return applyHandleState;
}

//查询是否填写特殊情况说明
function checkSpecialDesc(projectId) {
	var url = BASE_PATH
			+ "secondBeforeLoanController/checkSpecialDesc.action?projectId="
			+ projectId;
	var specialState = 0;
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			specialState = data;
		}
	});

	return specialState;
}

//查询退款（退手续费，尾款，利息等）是否可以申请
function getRefundFeeApplyStatus(projectId,type){
	var value=0;
	$.ajax({
		url : "getApplyStatus.action?projectId="+projectId+"&type="+type,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}
//查询批量是否可以申请
function getBatchRefundFeeApplyStatus(batchRefundFeeMainId){
	var value=0;
	$.ajax({
		url : "getBatchApplyStatus.action?batchRefundFeeMainId="+batchRefundFeeMainId,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}
//根据项目id和状态（查询的条件为>=状态值） 检查是否有工作流在运行,返回值大于0则有工作流运行
function checkWorkFlowExist(projectId,applyStatus){
	var value=0;
	$.ajax({
		url : "checkWorkFlowExist.action?projectId="+projectId+"&applyStatus="+applyStatus,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}

//查询展期费率是否大于贷前费率（大于返回1，小于返回0）
function checkFeeRate(projectId) {
	var url = BASE_PATH
			+ "secondBeforeLoanController/checkFeeRate.action?projectId="
			+ projectId;
	var exFeeRate = 0;
	$.ajax({
		url : url,
		async : false,
		success : function(data) {
			exFeeRate = data;
		}
	});

	return exFeeRate;
}
//根据项目id和收款项目查询财务受理的审批状态
function getApplyFinanceHandleApplyStatus(projectId,recPro){
	var value=0;
	$.ajax({
		url : "getApplyStatus.action?projectId="+projectId+"&recPro="+recPro,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}

//根据项目id查询产品编码
function getProductNumber(projectId){
	var value="";
	$.ajax({
		url : BASE_PATH+"productController/getProductNumber.action?projectId="+projectId,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}
//根据项目id和收款项目查询财务受理的金额
function getMakeLoansMoney(projectId,recPro){
	$.ajax({
		url : "getMakeLoansMoney.action?projectId="+projectId+"&recPro="+recPro,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}
//获取放款状态
function getFinanceStatus(projectId) {
	var value=0;
	$.ajax({
		url : BASE_PATH+"financeHandleController/getFinanceStatus.action?projectId="+projectId,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}

// 获取抵押物评估状态
function getMortgageEstimateStatus(projectId) {
	var value=0;
	$.ajax({
		url : BASE_PATH+"projectInfoController/isPledgeEval.action?projectId="+projectId,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}

// 获取下户调查报告状态
function getInvestigationStatus(projectId) {
	var value=0;
	$.ajax({
		url : BASE_PATH+"projectInfoController/isSpotInfoSave.action?projectId="+projectId,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}

//根据机构id查询机构合作信息修改申请的审批状态
function getOrgCooperationUpdateApplyStatus(orgId){
	var value=0;
	$.ajax({
		url : "getOrgCooperationUpdateApplyStatus.action?orgId="+orgId,
		cache : true,
		type : "POST",
		async : false,
		success : function(data, status) {
			value=data
		}
	});
	return value;
}
/**
 * 校验展期信息
 */
function getProjectExtension(projectId){
	var extensionFee = 0;
	$.ajax({
		type: "POST",
        data:{"projectId":projectId},
        async:false, 
    	url : BASE_PATH+'foreLoanExtensionController/getProjectExtension.action',
		dataType: "json",
	    success: function(resultData){
	    	extensionFee = resultData.extensionFee;//展期费
	    	
	    	}
	    });
	return extensionFee;
}