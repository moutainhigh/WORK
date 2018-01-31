/**
 * 相应处理按钮点击事件
 * @param data行数据
 */
function disposeClick(rowData){
	if(rowData){
		var pd = rowData.workflowProcessDefkey;
		if(pd){
			var arr = pd.split(":");
			var url = "";
			//贷前流程流程控制跳转
			if("loanRequestProcess"==arr[0]){
				url = sy.bpName()+"/beforeLoanController/addNavigation.action?"+joinParam(rowData);
				url = encodeURI(url);
			//提前还款申请工作流
			}else if("prepaymentRequestProcess"==arr[0]){
				url = sy.bpName()+"/rePaymentController/repaydatilbyProcess.action?preRepayId="+rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url);
			//利率变更流程控制跳转
			}else if('interestChangeRequestProcess' == arr[0]){
				url = sy.bpName()+"/repaymanageloan/repaydatilapplybyProcess.action?interestChgId="+rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url)
			//违约处理工作流层控制跳转
			}else if('breachOfContractRequestProcess' == arr[0]){
				url = sy.bpName()+"/afterLoanController/listBreachDispose.action?projectId="+rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
			//额度冻结与解冻工作流
			}else if('creditFreezeOrThawRequestProcess' == arr[0]){
				url = sy.bpName()+"/creditsCustomerInfoController/toEditfreeze.action?pid="+rowData.refId+"&projectId="+rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
			//额度提款申请工作流
			}else if('creditWithdrawalRequestProcess' == arr[0]){
				url = sy.bpName()+"/creditsCustomerInfoController/toAddCredit.action?projectId="+rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
			//展期申请工作流
			}else if('extensionRequestProcess' == arr[0]){
				url = sy.bpName()+"/loanExtensionController/toEditLoanExtensionApply.action?projectId="+rowData.projectId+"&newProjectId="+rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url)
			//费用减免工作流
			}else if('feeWaiversRequestProcess' == arr[0]){
				url = sy.bpName()+"/repayFeewdtlController/repayFeewdtlDealbyprocess.action?repayId="+rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url)
			//挪用处理工作流
			}else if('misappropriateRequestProcess' == arr[0]){
				url = sy.bpName()+"/afterLoanDivertController/afterLoanDivertbyprocess.action?projectId="+rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
		    //退尾款处理工作流
			}else if('refundTailMoneyProcess' == arr[0]||'WTrefundTailMoneyProcess' == arr[0]){
				url = sy.bpName()+"/refundFeeController/editByProcess.action?projectId="+rowData.projectId+"&type=3&"+joinParam(rowData);
				url = encodeURI(url)
		    //退手续费处理工作流
			}else if('refundFeeProcess' == arr[0]){
				url = sy.bpName()+"/refundFeeController/editByProcess.action?projectId="+rowData.projectId+"&type=1&"+joinParam(rowData);
				url = encodeURI(url)
			//退利息处理工作流
			}else if('refundInterestFeeProcess' == arr[0]){
				url = sy.bpName()+"/refundFeeController/editByProcess.action?projectId="+rowData.projectId+"&type=2&"+joinParam(rowData);
				var tempWorkfloPprojectName="";
				var tempProjectName="";
				try {
					tempWorkfloPprojectName=rowData.workfloPprojectName.substring(0,4);
				} catch (e) {
				}
				try {
					tempProjectName=rowData.projectName.substring(0,4);
				} catch (e) {
				}
				var isBatch=tempWorkfloPprojectName=="批量退费"||tempProjectName=="批量退费";
				if (isBatch) {
					url = sy.bpName()+"/refundFeeController/toBatchHandle.action?batchRefundFeeMainId="+rowData.refId+"&type=2&"+joinParam(rowData);
				}
				url = encodeURI(url)
				//中途划转处理工作流
			}else if('intermediateTransferProcess' == arr[0]){
				url = sy.bpName()+"/intermediateTransferController/editByProcess.action?pid="+rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url)
			//赎楼贷业务办理处理工作流
			}else if('nonTransactionCashRedemptionHomeProcess' == arr[0]||'transactionCashRedemptionHomeProcess' == arr[0]){
				url = sy.bpName()+"/bizHandleController/toIndexByTask.action?projectId="+rowData.projectId;
				if ("赎楼"==rowData.taskName) {
					url = sy.bpName()+"/bizHandleController/toForeclosureIndexByTask.action?projectId="+rowData.projectId;
				}else if ("回款"==rowData.taskName) {
					url = sy.bpName()+"/repaymentController/toIndexByTask.action?projectId="+rowData.projectId;
				}
				url = encodeURI(url)
			}else if('creditLoanRequestProcess' == arr[0]){
				url = sy.bpName()+"/beforeLoanController/addNavigation.action?type=2&projectId="+rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
			}else if('foreLoanRequestProcess' == arr[0]){
				url = sy.bpName()+"/beforeLoanController/addNavigation.action?type=1&projectId="+rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
				//要件借出
			}else if('elementLendRequestProcess' == arr[0]){
				url = sy.bpName()+"/elementLendController/editElementLend.action?pid="+rowData.refId+"&status=1&"+joinParam(rowData);
				url = encodeURI(url)
			}else if('creditExtensionRequestProcess' == arr[0]){
				url = sy.bpName()+"/foreLoanExtensionController/toEditLoanExtensionApply.action?projectId="+rowData.projectId+"&newProjectId="+rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url)
			}else if('foreExtensionRequestProcess' == arr[0]){
				url = sy.bpName()+"/foreLoanExtensionController/toEditLoanExtensionApply.action?projectId="+rowData.projectId+"&newProjectId="+rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url)
				//退佣金处理工作流
			}else if('refundCommissionProcess' == arr[0]){
				url = sy.bpName()+"/refundFeeController/editByProcess.action?projectId="+rowData.projectId+"&type=4&"+joinParam(rowData);
				url = encodeURI(url)
			}else if('cooperationRequestProcess' == arr[0]){
				url = sy.bpName()+"/orgCooperatCompanyApplyController/editCooperate.action?editType=3&pid="+ rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
			}else if('businessApplyRequestProcess' == arr[0]){
				url = sy.bpName()+"/beforeLoanController/addNavigation.action?type=2&projectId="+rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
				//放款申请工作流
			}else if('makeLoansProcess' == arr[0]){
				url = sy.bpName()+"/financeHandleController/toMakeLoans.action?chooseType=0&projectId="+ rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
				//放款申请(房抵贷)工作流
			}else if('fddMakeLoansProcess' == arr[0]){
				url = sy.bpName()+"/financeHandleController/toFddMakeLoans.action?projectId="+ rowData.projectId+"&"+joinParam(rowData);
				url = encodeURI(url)
				//修改机构合作信息工作流
			}else if('cooperationUpdateProcess' == arr[0]){
				url = sy.bpName()+"/orgCooperationUpdateApplyController/toOrgCooperationUpdateApplyWorkFolw.action?openType=1&pid="+ rowData.refId+"&"+joinParam(rowData);
				url = encodeURI(url)
			}else if('mortgageLoanForeAppRequestProcess' == arr[0]){
				url = sy.bpName()+"/projectInfoController/toEditProject.action?type=2&projectId="+ rowData.projectId + "&"+joinParam(rowData);
				url = encodeURI(url)
			}else if('consumeLoanForeAppRequestProcess' == arr[0]){
				url = sy.bpName()+"/consumeProjectInfoController/toEditConsumeProject.action?type=2&projectId="+ rowData.projectId + "&"+joinParam(rowData);
				url = encodeURI(url)
			}
			parent.layout_center_addTabFun({
				title : rowData.taskName, //tab的名称
				closable : true, //是否显示关闭按钮
				content : createFrame(url),//加载链接
				falg:true
			});
		}else if(rowData.allocationRefId>0){
			url = sy.bpName()+"/afterLoanController/execuSuperyviseBusiness.action?planId="+rowData.allocationRefId;
			url = encodeURI(url)
			parent.layout_center_addTabFun({
				title : rowData.taskName, //tab的名称
				closable : true, //是否显示关闭按钮
				content : createFrame(url),//加载链接
				falg:true
			});
		}
	}
}

/**
 * 拼接参数
 */
function joinParam(rowData){
	var params = "";
	if(rowData){
		params += "param=";
		params +="'refId':'"+rowData.refId+"'"
		if(rowData.projectId){params +=",'projectId':'"+rowData.projectId+"'"}
		if(rowData.taskUserName){params += ",'taskUserName':'"+rowData.taskUserName+"'"}
		if(rowData.taskType){params += ",'taskType':'"+rowData.taskType+"'"}
		if(rowData.allocationType){params += ",'allocationType':'"+rowData.allocationType+"'"}
		if(rowData.allocationRefId){params += ",'allocationRefId':'"+rowData.allocationRefId+"'"}
		if(rowData.taskName){params += ",'taskName':'"+rowData.taskName+"'"}
		if(rowData.workflowName){params += ",'workflowName':'"+rowData.workflowName+"'"}
		if(rowData.allocationDttm){params += ",'allocationDttm':'"+rowData.allocationDttm+"'"}
		if(rowData.completeDttm){params += ",'completeDttm':'"+rowData.completeDttm+"'"}
		if(rowData.taskStatus){params += ",'taskStatus':'"+rowData.taskStatus+"'"}
		if(rowData.status){params += ",'status':'"+rowData.status+"'"}
		if(rowData.workflowProcessDefkey){params += ",'workflowProcessDefkey':'"+rowData.workflowProcessDefkey+"'"}
		if(rowData.workflowInstanceId){params += ",'workflowInstanceId':'"+rowData.workflowInstanceId+"'"}
		if(rowData.workflowTaskDefKey){params += ",'workflowTaskDefKey':'"+rowData.workflowTaskDefKey+"'"}
		if(rowData.workflowTaskId){params += ",'workflowTaskId':'"+rowData.workflowTaskId+"'"}
		if(rowData.nextRoleCode){params += ",'nextRoleCode':'"+rowData.nextRoleCode+"'"}
		if(rowData.allowTurnDown){params +=",'allowTurnDown':'"+rowData.allowTurnDown+"'"}
		if(rowData.projectName){params +=",'projectName':'"+rowData.projectName+"'"}
	}
	return params;
}
/**
 * 添加连接
 */
function createFrame(url)
{
    var s = '<iframe id="my_Agent_Task_grid_iframe" name="my_Agent_Task_grid_iframe" src="'+ url+'" frameborder="0" style="border:0;width:100%;height:99%;"></iframe>';
    return s;
}
