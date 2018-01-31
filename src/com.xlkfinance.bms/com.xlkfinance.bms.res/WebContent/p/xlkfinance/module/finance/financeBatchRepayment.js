//财务批量对账页面js
//跳转到 添加页面
var isEdit = undefined;
	$(function() {

		$('#acctBatchGrid').datagrid({
			onSelect : function(rowIndex, rowData) {
				$('#acctBatchGrid').datagrid('beginEdit', rowIndex);
				isEdit = true;
			},
			unselectRow : function() {
				$('#acctBatchGrid').datagrid('endEdit', rowIndex);
				isEdit = false;
			},
			onBeforeEdit : function(index, row) {
				row.editing = true;
				$('#acctBatchGrid').datagrid('refreshRow', index);
			},
			onAfterEdit : function(index, row) {
				row.editing = false;
				$('#acctBatchGrid').datagrid('endEdit', index);
			},
			onCancelEdit : function(index, row) {
				row.editing = false;
				$('#acctBatchGrid').datagrid('refreshRow', index);
			}
		});
	});

	//查询 
	function ajaxSearch() {
		if (!compareDate('expireStartDt', 'expireEndDt')) {
			return false;
		}
		var data = {
			projectName : $("#projectName").val(),
			projectNumber : $("#projectNumber").val(),
			cusName : $("#cusName").val(),
			cusType : $('#cusType').combobox('getValue'),
			expireStartDt : $('#expireStartDt').datebox('getValue'),
			expireEndDt : $('#expireEndDt').datebox('getValue'),
			ecoTrade : $('#ecoTrade').combobox('getValue')
		};

		$('#acctBatchGrid').datagrid('load', data);
		clearSelectRows('#acctBatchGrid');
	}

	function saveAcctBatchRepay() {
		var rows = $('#acctBatchGrid').datagrid('getChecked');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}

		var para = [];
		for (var i = 0; i < rows.length; i++) {
			var temp = {};
			var row = rows[i];
			//var actualAmt = $('[value="'+rows[i].pid+'"]').parents('td').siblings('td[field="actualAmt"]').find('[type="hidden"]');
			var actualAmt = $('[value="' + rows[i].loanId + '"]').parents('td')
					.siblings('td[field="actualAmt"]').find('[type="hidden"]');
			var actualReceDt = $('[value="' + rows[i].loanId + '"]').parents(
					'td').siblings('td[field="actualReceDt"]').find(
					'[type="hidden"]');
			temp.loanId = row.loanId;
			temp.projectName = rows[i].projectName;
			temp.projectNumber = rows[i].projectNumber;
			temp.amount = actualAmt.val();
			temp.currentDt = actualReceDt.val();
			para.push(temp);

			if (undefined == actualAmt.val() || actualAmt.val() == "") {
				$.messager.alert("操作提示", "请输入实收金额", "error");
				return false;
			}
		}

		var paraStr = JSON.stringify(para);

		var url = encodeURI(BASE_PATH+"financeReconciliationController/previewBatchRepaymentResult.action?para="
				+ paraStr);
		parent.layout_center_addTabFun({
			title : "对账处理", //tab的名称
			closable : true, //是否显示关闭按钮
			content : createFrame(url),//加载链接
			falg : true
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
	