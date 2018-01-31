//财务自己头寸列表js
	//查询 
	function ajaxSearchList() {

		var data = {
			chargeName : $("#chargeName").val(),
			bankUserName : $('#bankUserName').textbox('getValue'),
			bankCardType : $('#bankCardType').combobox('getValue'),
			bankNum : $('#bankNum').textbox('getValue')
		};

		$('#financeTransactionGrid').datagrid('load', data);
	}
	//导出文件
	function exportData() {
		$.ajax({
					url : BASE_PATH+'templateFileController/checkFileUrl.action',
					data : {templateName : 'ZJTC'},
					dataType : 'json',
					success : function(data) {
						if (data == 1) {
							window.location.href = BASE_PATH+'financeTransactionController/exportTransactionTable.action?chargeName='
									+ $("#chargeName").val()
									/* + '&bank='
									+ $('#bank').combobox('getValue') */
									+ '&bankCardType='
									+ $('#bankCardType').combobox('getValue')
									+ '&bankNum='
									+ $('#bankNum').textbox('getValue');
						} else {
							alert('资金头寸模板不存在，请上传模板后重试');
						}
					}
				})

	}