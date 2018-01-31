//金额格式化
function formatterMoney2(cellvalue, options, rowObject) {
	if(cellvalue){
		return accounting.formatMoney(cellvalue, "", 2, ",", ".");
	}else{
		return "";
	}
}
//放款状态格式化
function formatterMakeLoansStatus(cellvalue, options, rowObject) {
	if (cellvalue == 1) {
		return "未收费";
	} else if (cellvalue == 2) {
		return "已收费";
	} else if (cellvalue == 3) {
		return "已放款";
	} else if (cellvalue == 4) {
		return "放款未完结";
	} else if (cellvalue == 5) {
		return "放款申请中";
	} else {
		return "未知";
	}
}
//项目状态格式化
function formatterProjectStatus(cellvalue, options, rowObject) {
	var arr=[{'key':1,'value':'待客户经理提交'},
	         {'key':2,'value':'待部门经理审批'},
	         {'key':3,'value':'待业务总监审批'},
	         {'key':4,'value':'待审查员审批'},
	         {'key':5,'value':'待风控初审'},
	         {'key':6,'value':'待风控复审'},
	         {'key':7,'value':'待风控终审'},
	         {'key':8,'value':'待风控总监审批'},
	         {'key':9,'value':'待总经理审批'},
	         {'key':10,'value':'已审批'},
	         {'key':11,'value':'已放款'},
	         {'key':12,'value':'业务办理已完成'},
	         {'key':13,'value':'已归档（解保）'},
	         {'key':14,'value':'待审查主管审批'},
	         {'key':15,'value':'待合规复审'}
	         ];
	for (var i = 0; i < arr.length; i++) {
		if (arr[i].key==cellvalue) {
			return arr[i].value;
		}
	}
}
//回款状态格式化
function formatterRepaymentStatus(cellvalue, options, rowObject) {
	if (cellvalue == 1) {
		return "未回款";
	} else if (cellvalue == 2) {
		return "已回款";
	} else {
		return "未知";
	}
}
//机构息费返点状态
function formatterFeeSettleRebateType(cellvalue, options, rowObject) {
	if (cellvalue == 1) {
		return "有返点";
	} else if (cellvalue == 2) {
		return "无返点";
	} else {
		return "未知";
	}
}