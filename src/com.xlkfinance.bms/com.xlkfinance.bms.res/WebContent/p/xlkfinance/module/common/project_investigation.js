/** 项目基本信息的保存 and 尽职调查报告操作 and 财务银行帐号的读取 **/


// 导出尽职查询报告
function exportSurveyReport(){
	$.messager.alert('操作提示','该功能暂未实现！','error');	
}

// 设置贷款利息收取单位帐号
function getAccountByPrimaryKeyAccrual(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		// 如果是请选择,必须情况
		$("#investigationForm input[name='loanInterestRecordNo']").val("");
		return;
	}
	// 设置帐号
	$("#investigationForm input[name='loanInterestRecordNo']").val(rec.bankNum);
}

// 设置贷款管理费收取单位帐号
function getAccountByPrimaryKeyFees(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		// 如果是请选择,必须情况
		$("#investigationForm input[name='loanMgrRecordNo']").val("");
		return;
	}
	// 设置帐号
	$("#investigationForm input[name='loanMgrRecordNo']").val(rec.bankNum);
}

// 设置其它费用收取单位帐号
function getAccountByPrimaryKeyOther(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		// 如果是请选择,必须情况
		$("#investigationForm input[name='loanOtherFeeNo']").val("");
		return;
	}
	// 设置帐号
	$("#investigationForm input[name='loanOtherFeeNo']").val(rec.bankNum);
}

