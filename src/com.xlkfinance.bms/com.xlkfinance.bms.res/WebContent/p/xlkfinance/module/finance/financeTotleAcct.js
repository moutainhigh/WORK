//财务总账查询页面js

//查询 
	function ajaxSearchTotleAcct() {
		if (!compareDate('searcherPeriodStart', 'searcherPeriodEnd')) {
			return false;
		}
		var data = {
			chargeName : $("#chargeName").val(),
			bankCardType : $('#bankCardType').combobox('getValue'),
			bankNum : $('#bankNum').textbox('getValue'),
			bankUserName : $('#bankUserName').textbox('getValue'),
			searcherPeriodStart : $('#searcherPeriodStart').datebox('getValue'),
			searcherPeriodEnd : $('#searcherPeriodEnd').datebox('getValue')
		};

		$('#acctTotalGrid').datagrid('load', data);
	}

	function operateLoadTotleAcct(value, row, index) {
		if (row.pid != null) {
			return '<a href="javascript:void(0)" onclick="detailTotleAcct(' + row.pid
					+ ',' + row.initialAmt
					+ ')"><font color="blue">查询明细账</font></a>';
		}

	}
	function detailTotleAcct(pid, initialAmt) {

		var searcherPeriodStart = $('#searcherPeriodStart').datebox('getValue');
		var searcherPeriodEnd = $('#searcherPeriodEnd').datebox('getValue');
		$('<div id="detailFinanceAcctList"/>')
				.dialog(
						{
							href : BASE_PATH+'financeController/getFinanceAcctDetailList.action?pId='
									+ pid
									+ '&searcherPeriodStart='
									+ searcherPeriodStart
									+ '&searcherPeriodEnd='
									+ searcherPeriodEnd
									+ '&defaultAmt=' + initialAmt,
							width : 800,
							height : 400,
							modal : true,
							title : '财务明细查看',
							onLoad : function() {
							}
						});

	}    