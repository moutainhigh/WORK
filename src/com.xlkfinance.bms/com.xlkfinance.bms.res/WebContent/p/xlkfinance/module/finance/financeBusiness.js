//业务查询页面
	//收款对账
	function reconciliation(loanId,unReconciliationAmt) {
		if(Number(unReconciliationAmt)>0)
		{
			$.messager.confirm('确认','该项目有未对账金额，可以点击‘查看对账流水/查看对账信息/手动对账’使用未对账金额来对账，确认要重新收款吗？',function(r){    
			    if (r){    
			    	parent.openNewTab(
							BASE_PATH+"financeReconciliationController/handReconciliation.action?loanId="
									+ loanId, "收款对账信息录入/查看", true);
			    }    
			});
		}	
		else
		{
			parent.openNewTab(
					BASE_PATH+"financeReconciliationController/handReconciliation.action?loanId="
							+ loanId, "收款对账信息录入/查看", true);
		}	
	}

	//查看对账流水
	function findReconciliation(loanId, pid) {
		if (loanId != 0 || pid != 0) {
			$('<div id="listFinanceTotaldetailDiv"/>')
					.dialog(
							{
								href : BASE_PATH+'financeController/getProjectTotalDetailList.action?loanId='
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
	//清酒减免
	function findIncome(loanId, projectId) {
		parent.openNewTab(
				BASE_PATH+"financeController/getFinanceListLoanCalculate.action?loanId="
						+ loanId + '&projectId=' + projectId, "查看收息表", true);
	}

	//查看收息表
	function handFeedtl(loanId) {
		$.messager.confirm("操作提示", "确定减免项目所剩的余额吗?", function(r) {
			if (r) {
				$.post( BASE_PATH+"financeReconciliationController/handFeewdtl.action", {
					loanId : loanId
				}, function(ret) {					
					if(ret==-1){
						$.messager.alert("操作提示", "该项目没有期数是部分对账剩余的余额！", "error");
					}else if(ret==0){
						$.messager.alert("操作提示", "减免失败！", "error");
					}else if(ret==-2){
						$.messager.alert("操作提示", "剩余金额大于可操作的金额不可直接减免！", "error");
					}else{
						$.messager.alert("操作提示", "操作成功！", "success");
					}
					//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
					$("#dlg").dialog('close');
					$("#cusBusinessGrid").datagrid('reload');
					//clearSelectRows('#grid');
				}, 'json');
			}
		});
	}
	//查看收息表
	function insertLoanRecInto(loanId) {
		$.messager.confirm("操作提示", "确定把未对账金额直接转入收入吗?", function(r) {
			if (r) {
				$.post( BASE_PATH+"financeReconciliationController/insertLoanRecInto.action", {
					loanId : loanId
				}, function(ret) {					
					if(ret==-1){
						$.messager.alert("操作提示", "该项目没有期数是部分对账剩余的余额！", "error");
					}else if(ret==0){
						$.messager.alert("操作提示", "转入失败！", "error");
					}else if(ret==-2){
						$.messager.alert("操作提示", "剩余金额大于可操作的金额不可直接减免！", "error");
					}else{
						$.messager.alert("操作提示", "操作成功！", "success");
					}
					//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
					$("#dlg").dialog('close');
					$("#cusBusinessGrid").datagrid('reload');
					//clearSelectRows('#grid');
				}, 'json');
			}
		});
	}
	$(function() {

		$("#acct_type").change(function() {
			var ctype = $("#acct_type").combobox('getValue');
			if (ctype == 2) {
				$("#eco_trade_span").show();
			} else {
				$("#eco_trade_span").hide();
			}
		});
	});

	function operateLoadBusiness(value, row, index) {
		
		return '<a href="javascript:void(0)" onclick="reconciliation('
				+ row.loanId + ','+row.unReconciliationAmt+')"><font color="blue">收款对账</font></a>|'
				+ '<a href="javascript:void(0)" onclick="findReconciliation('
				+ row.loanId + ',' + row.pid
				+ ')" ><font color="blue">查看对账流水</font></a>|'
				+ '<a href="javascript:void(0)" onclick="findIncome('
				+ row.loanId + ',' + row.pid
				+ ')" ><font color="blue">查看收息表</font></a>|<a href="javascript:void(0) " onclick="handFeedtl('+row.loanId+')" ><font color="blue">减免</font></a>'+
				'|<a href="javascript:void(0) " onclick="insertLoanRecInto('+row.loanId+')" ><font color="blue">剩余金额转入</font></a>'				;
	}
	//查询 
	function ajaxSearchBusiness() {
		if (!compareDate('expireStartDt', 'expireEndDt')) {
			return false;
		}
		if (!compareDate('requestStartDt', 'requestEndDt')) {
			return false;
		}
		var data = {
			projectName : $("#projectName").val(),
			projectNumber : $("#projectNumber").val(),
			cusName : $("#cusName").val(),
			cusType : $('#cusType').combobox('getValue'),
			expireStartDt : $('#expireStartDt').datebox('getValue'),
			expireEndDt : $('#expireEndDt').datebox('getValue'),
			ecoTrade : $('#ecoTrade').combobox('getValue'),
			overdueStartDay : $('#overdueStartDay').textbox('getValue'),
			overdueEndDay : $('#overdueEndDay').textbox('getValue'),
			requestStartDt : $('#requestStartDt').datebox('getValue'),
			requestEndDt : $('#requestEndDt').datebox('getValue')
		};

		$('#cusBusinessGrid').datagrid('load', data);
		clearSelectRows('#cusBusinessGrid');
	}
	//项目名称format
	var formatProjectName = function(value, rowData, index) {
		var btn = "<a id='"
				+ rowData.pid
				+ "' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("
				+ rowData.pid + ",\"" + rowData.projectNumber
				+ "\")' href='#'>" + "<span style='color:blue; '>"
				+ rowData.projectName + "</span></a>";
		return btn;
	}