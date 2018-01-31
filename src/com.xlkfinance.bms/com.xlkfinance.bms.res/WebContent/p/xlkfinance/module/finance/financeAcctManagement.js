//财务账户管理js
//绑定赋值
	$(function() {
		setCombobox("bank_Card_Type", "CUS_TYPE", "${financeBank.bankCardType}");//1:id 2:数据字典 3：赋值 
	});
//跳转到 添加页面
	function addItem() {
		$('<div id="editFinaceAcctManager" />')
				.dialog(
						{
							href : BASE_PATH+'financeController/saveFinanceAcctManager.action',
							width : 830,
							height : 350,
							modal : true,
							title : '新增财务账户管理',
							buttons : [
									{
										text : '保存',
										iconCls : 'icon-save',
										handler : function() {
											window.save();
										}
									},
									{
										text : '取消 ',
										iconCls : 'icon-no',
										handler : function() {
											$("#editFinaceAcctManager").dialog(
													'close');
										}
									} ],
							onClose : function() {
								$(this).dialog('destroy');
							},
							onLoad : function() {
							}
						});

	};

	function editItem() {
		var row = $('#grid').datagrid('getSelections');
		if (row.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}
		if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		}
		if (row.length == 1) {
			var pid = row[0].pid;
			$('<div id="editFinaceAcctManager"/>')
					.dialog(
							{
								href : BASE_PATH+'financeController/editFinanceAcctManager.action?pid='
										+ pid,
								width : 800,
								height : 350,
								modal : true,
								title : '编辑财务账户管理',
								buttons : [
										{
											text : '保存',
											iconCls : 'icon-save',
											handler : function() {
												window.save();
											}
										},
										{
											text : '取消 ',
											iconCls : 'icon-no',
											handler : function() {
												$("#editFinaceAcctManager").dialog('close');
											}
										} ],
								onClose : function() {
									$(this).dialog('destroy');
								},
								onLoad : function() {
								}
							});
		} else if (row.length > 1) {
			$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
		} else {
			alert("操作提示", "请选择数据", "error");
		}
	}

	//删除
	function removeItem() {
		var rows = $('#grid').datagrid('getSelections');
		if (rows.length == 0) {
			$.messager.alert("操作提示", "请选择数据", "error");
			return;
		}// 获取选中的系统用户名 
		var pid = "";
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				pid += rows[i].pid;
			} else {
				pid += "," + rows[i].pid;
			}
		}
		$.messager.confirm("操作提示", "确定删除选择的此批账户吗?", function(r) {
			if (r) {
				$.post("deleteFinaceAcctManager.action", {
					pid : pid
				}, function(ret) {					
					if(ret=='success'){
						$.messager.alert("操作提示", "删除成功！", "success");
					}else{
						$.messager.alert("操作提示", "该账户有流水信息不可删除！", "error");
					}
					//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
					$("#dlg").dialog('close');
					$("#grid").datagrid('reload');
					clearSelectRows('#grid');
				}, 'json');
			}
		});

	}
	//查询 
	function ajaxSearch() {

		var data = {
			chargeName : $("#chargeName").val(),
			bank : $('#bank').combobox('getValue'),
			bankCardType : $('#bankCardType').combobox('getValue'),
			bankNum : $('#bankNum').textbox('getValue')
		};

		$('#grid').datagrid('load', data);
		clearSelectRows('#grid');
	}
	function operateLoad(value, row, index) {
		return '<a href="javascript:findAcctOutput(' + row.pid
				+ ')"><font color="blue">账户支出</font></a>|'
				+ '<a href="javascript:findAcctInput(' + row.pid
				+ ')" ><font color="blue">理财收入</font></a>|'
				+ '<a href="javascript:findFinancing(' + row.pid
				+ ')" ><font color="blue">融资借贷</font></a>';
	}
	//账户支出
	function findAcctOutput(bankAcctId) {
		parent.openNewTab(
				BASE_PATH+"financeTransactionController/listAcctountOutput.action?regCategory=4&bankAcctId="
								+ bankAcctId, "账户支出查询", true);
	}
	//理财收入
	function findAcctInput(bankAcctId) {
		parent.openNewTab(
				BASE_PATH+"financeTransactionController/listAcctountOutput.action?regCategory=3&bankAcctId="
								+ bankAcctId, "银行账户理财利息查询", true);
	}
	//融资借款查询
	function findFinancing(bankAcctId) {
		parent.openNewTab(
				BASE_PATH+"financeTransactionController/listFinanceFinancing.action?bankAcctId="
								+ bankAcctId, "银行账户融资借贷查询", true);
	}
	
	/**新增账户页面js**/
	
	function save(){
		   $('#baseInfo').form('submit', {
				url : "addFinanceAcctManager.action",
				onSubmit : function() {return $(this).form('validate');},
				success : function(result) {
					window.location.href='listFinanceAcctManage.action';
				}
			}); 
	}
		 
		 function updateMoney(){
			 $('#updateMoneyDiv').dialog({    
				    title: '修改初始金额',    
				    width: 400,    
				    height: 200,    
				    closed: false,    
				    cache: false,    
				    modal: true  , 
				    buttons : [ {
						text : '保存',
						iconCls : 'icon-save',
						handler : function() {
							
							var updateMoney = $("#updateMoney").numberbox('getValue');
							$('#defaultAmtId').textbox('setValue',updateMoney)
							$("#updateMoneyDiv").dialog('close');
						}	
						},{
							text : '取消 ',
							iconCls : 'icon-no',
							handler : function() {
								$("#updateMoneyDiv").dialog('close');
								}
							} ]
				});    
				var thisMoney = $("#defaultAmtId").textbox('getValue');
				$("#thisMoney").textbox('setValue',thisMoney);
				$("#updateMoney").numberbox('setValue','');
			
		 };