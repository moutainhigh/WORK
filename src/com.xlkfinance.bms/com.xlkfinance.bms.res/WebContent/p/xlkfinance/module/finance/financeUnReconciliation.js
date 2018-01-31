//财务未对账列表js
	//确认处理
	function returnBalanceAmt() {
		var re = '';
		$('input[name="returnAmt"]').each(
				function(i) {
					if ($(this).is(':checked')) {
						if (re == '') {
							re += $(this).parent().parent().parent().find(
									'td[field="pid"]').text();
						} else {
							re += ','
									+ $(this).parent().parent().parent().find(
											'td[field="pid"]').text();
						}
					}
				});
		var b = '';
		$('input[name="balance"]').each(
				function(i) {
					if ($(this).is(':checked')) {
						if (b == '') {
							b += $(this).parent().parent().parent().find(
									'td[field="pid"]').text();
						} else {
							b += ','
									+ $(this).parent().parent().parent().find(
											'td[field="pid"]').text();
						}
					}
				});
		$.messager.confirm("操作提示", "确定处理选择的款项吗?", function(r) {
			if (r) {
				$.post("batchReturnBalanceAmt.action", {
					balance : b,
					returnAmt : re
				}, function(ret) {
					if (ret > 0) {
						$.messager.alert("操作提示", "处理完成", "success");
						$("#unReconciliationGrid").datagrid('load');
					} else {
						$.messager.alert("操作提示", "处理失败", "success");
					}
				});
			}
		});

	}
	function checkboxField(value, row, index) {
		return '<input type="checkbox" name="returnAmt"  onclick="checkState(this,\'balance\','
				+ index + ')"/>';

	}
	function checkboxField2(value, row, index) {
		return '<input type="checkbox" name="balance"  onclick="checkState(this,\'returnAmt\','
				+ index + ')"/>';

	}
	function checkState(t, tName, index) {
		if ($(t).is(':checked')) {
			$(
					'.datagrid-btable tr[datagrid-row-index="' + index
							+ '"] input[type="checkbox"][name="' + tName + '"]')
					.attr('disabled', 'disabled');
		} else {
			$(
					'.datagrid-btable tr[datagrid-row-index="' + index
							+ '"] input[type="checkbox"][name="' + tName + '"]')
					.removeAttr('disabled');

		}
	}
	$(function() {
		//$('#receiveStartDt').datebox('setValue',''+new Date()) ;
		//$('#receiveEndDt').datebox('setValue',''+new Date());

	});

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

	function operateLoad(value, row, index) {
		return '<a href="javascript:void(0)" onclick="detailList(' + row.pid
				+ ')"><font color="blue">查看对账明细</font></a>';
	}
	//查看对账信息
	function detailList(receId) {
		$('<div id="listFinanceTotaldetailDiv"/>')
				.dialog(
						{
							href :  BASE_PATH+'financeController/getLoanReconciliationDtl.action?financeReceivablesId='
									+ receId,
							width : 800,
							height : 600,
							modal : true,
							title : '查看对账信息',
							onLoad : function() {
							}
						});
	}
	//查询 
	function ajaxSearch() {
		if (!compareDate('receiveStartDt', 'receiveEndDt')) {
			return false;
		}
		var data = {
			projectName : $("#projectName").val(),
			projectNumber : $("#projectNumber").val(),
			cusName : $("#cusName").val(),
			cusType : $('#cusType').combobox('getValue'),
			receiveStartDt : $('#receiveStartDt').datebox('getValue'),
			receiveEndDt : $('#receiveEndDt').datebox('getValue'),
			ecoTrade : $('#ecoTrade').combobox('getValue')
		};

		$('#unReconciliationGrid').datagrid('load', data);
	}

	//项目名称format
	var formatProjectName = function(value, rowData, index) {
		var btn = "<a id='"
				+ rowData.projectId
				+ "' class='easyui-linkbutton' onclick='formatterProjectName.disposeClick("
				+ rowData.projectId + ",\"" + rowData.projectNumber
				+ "\")' href='#'>" + "<span style='color:blue; '>"
				+ rowData.projectName + "</span></a>";
		return btn;
	}