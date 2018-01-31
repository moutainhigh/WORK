//财务账户管理资金头寸

//跳转到 添加页面
function addItemInput(){
	   $('<div id="editAcctountInput" />').dialog({
			href : BASE_PATH+'financeTransactionController/editAcctountOutput.action?regCategory=3&bankAcctId='+bankAcctId,
			width : 500,
			height : 300,
			modal : true,
			title : '新增/编辑理财利息收入',
			buttons : [ {
				text : '保存',
				iconCls : 'icon-save',
				handler : function() {
					window.saveInput();
				}	
				},{
					text : '取消 ',
					iconCls : 'icon-no',
					handler : function() {
						$("#editAcctountInput").dialog('close');
						}
					} ],
			onClose : function() {
				$(this).dialog('destroy');
			},
			onLoad : function() {
			}
		});
	   
};
function editItemInput(pid){	   
		  $('<div id="editAcctountInput"/>').dialog({
				href : BASE_PATH+'financeTransactionController/editAcctountOutput.action?regCategory=3&pid='+pid+'&bankAcctId='+bankAcctId,
				width : 500,
				height : 300,
				modal : true,
				title : '新增/编辑理财利息收入',
				buttons : [ {
					text : '保存',
					iconCls : 'icon-save',
					handler : function() {
						window.saveInput();
					}	
					},{
						text : '取消 ',
						iconCls : 'icon-no',
						handler : function() {
							$("#editAcctountInput").dialog('close');
							}
						} ],
				onClose : function() {
					$(this).dialog('destroy');
				},
				onLoad : function() {
				}
			});
}
function saveInput(){
	$('#baseInfo').form('submit', {
		url : "editOuptut.action",
		onSubmit : function() {return $(this).form('validate');},
		success : function(result) {
			if(result>0){
				$.messager.alert("提示","保存成功！","success");  
			}else{
				$.messager.alert("提示","保存失败！","error");  
			}
			
			$("#editAcctountInput").dialog('close');
			$('#inputGrid').datagrid('load');
		}
	});
		
		    
	}
//删除
 function removeItemInput(pid){	  
 	$.messager.confirm("操作提示","确定删除选择的数据吗?",
		function(r) {
 			if(r){
				$.post("deleteFinanceTransaction.action",{pid : pid}, 
					function(ret) {
						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
						  if(ret>0){
							  $.messager.alert("提示","删除成功！","success");    
						  }else{
							  $.messager.alert("提示","删除失败！","success"); 
						  }
						  $("#dlg").dialog('close');
						  $("#inputGrid").datagrid('reload');
					},'json');
 			}
		});
	  	
 }
//查询 
 function ajaxSearch() {
	if(!compareDate('ftStartDt','ftEndDt')){
			return false;
	}
   var data={
		    ftStartDt: $('#ftStartDt').datebox('getValue'),
		    ftEndDt: $('#ftEndDt').datebox('getValue'),
 		};
   
 	$('#inputGrid').datagrid('load',data);
 }
 function operateLoad(value, row, index) {
	 if(row.pid!=0){
		return '<a href="javascript:editItemInput(\''+row.pid+'\')"><font color="blue">编辑</font></a>|'+
		'<a href="javascript:removeItemInput(\''+row.pid+'\')" ><font color="blue">删除</font></a>';
	 }
	}
 function resetAcctInputForm(){
	 $('#acctInputForm').form('reset');
 }
 // 账户支出js
//跳转到 添加页面
 function addItemOut(){
 	   $('<div id="editAcctountOutput" />').dialog({
 			href : BASE_PATH+'financeTransactionController/editAcctountOutput.action?regCategory=4&bankAcctId='+bankAcctId,
 			width : 800,
 			height : 300,
 			modal : true,
 			title : '新增/编辑支出费用',
 			buttons : [ {
 				text : '保存',
 				iconCls : 'icon-save',
 				handler : function() {
 					window.saveOut();
 				}	
 				},{
 					text : '取消 ',
 					iconCls : 'icon-no',
 					handler : function() {
 						$("#editAcctountOutput").dialog('close');
 						}
 					} ],
 			onClose : function() {
 				$(this).dialog('destroy');
 			},
 			onLoad : function() {
 			}
 		});
 	   
 };
 function editItemOut(pid){	   
 		  $('<div id="editAcctountOutput"/>').dialog({
 				href : BASE_PATH+'financeTransactionController/editAcctountOutput.action?regCategory=4&pid='+pid+'&bankAcctId='+bankAcctId,
 				width : 800,
 				height : 300,
 				modal : true,
 				title : '新增/编辑支出费用',
 				buttons : [ {
 					text : '保存',
 					iconCls : 'icon-save',
 					handler : function() {
 						window.saveOut();
 					}	
 					},{
 						text : '取消 ',
 						iconCls : 'icon-no',
 						handler : function() {
 							$("#editAcctountOutput").dialog('close');
 							}
 						} ],
 				onClose : function() {
 					$(this).dialog('destroy');
 				},
 				onLoad : function() {
 				}
 			});
 }

  
  
 //删除
  function removeItemOut(pid){	  
  	$.messager.confirm("操作提示","确定删除选择的数据吗?",
 		function(r) {
  			if(r){
 				$.post("deleteFinanceTransaction.action",{pid : pid}, 
 					function(ret) {
 						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
 						  if(ret>0){
 							  $.messager.alert("提示","删除成功！","success");    
 						  }else{
 							  $.messager.alert("提示","删除失败！","success"); 
 						  }
 						  $("#dlg").dialog('close');
 						  $("#outputGrid").datagrid('reload');
 					},'json');
  			}
 		});
 	  	
  }
 //查询 
  function ajaxSearchOut() {
     if(!compareDate('ftStartDt','ftEndDt')){
 			return false;
 	}
    var data={
 		    usedType: $('#usedType').combobox('getValue'),
 		    ftStartDt: $('#ftStartDt').datebox('getValue'),
 		    ftEndDt: $('#ftEndDt').datebox('getValue'),
  		};
    
  	$('#outputGrid').datagrid('load',data);
  }
  function operateLoadOut(value, row, index) {
 	 if(row.pid!=0){
 		return '<a href="javascript:editItemOut('+row.pid+')"><font color="blue">编辑</font></a>|'+
 		'<a href="javascript:removeItemOut('+row.pid+')" ><font color="blue">删除</font></a>';
 	 }
 	}
  function resetAcctOutputForm(){
 	 $('#acctOutputForm').form('reset');
  }
//绑定赋值
  function saveOut(){
  	if($("#usedType2").combobox('getValue')>0){
  		$('#baseInfo').form('submit', {
  			url : "editOuptut.action",
  			onSubmit : function() {return $(this).form('validate');},
  			success : function(result) {
  				if(result>0){
  					$.messager.alert("提示","保存成功！","success");  
  				}else{
  					$.messager.alert("提示","保存失败！","error");  
  				}
  				
  				$("#editAcctountOutput").dialog('close');
  				$('#outputGrid').datagrid('load');
  			}
  		});
  	}else{
  		$.messager.alert("提示","请选择支出类型!！","error");  
  	}
  	
  	    
  }
  
  /*融资页面js*/
//跳转到 添加页面
  function addItemFinancing(){
  	   $('<div id="editAcctountFinancing" />').dialog({
  			href : BASE_PATH+'financeTransactionController/editAcctountFinancing.action?regCategory=1&bankAcctId='+bankAcctId,
  			width : 800,
  			height : 300,
  			modal : true,
  			title : '新增/编辑融资借款',
  			buttons : [ {
  				text : '保存',
  				iconCls : 'icon-save',
  				handler : function() {
  					window.saveFinancing();
  				}	
  				},{
  					text : '取消 ',
  					iconCls : 'icon-no',
  					handler : function() {
  						$("#editAcctountFinancing").dialog('close');
  						}
  					} ],
  			onClose : function() {
  				$(this).dialog('destroy');
  			},
  			onLoad : function() {
  			}
  		});
  	   
  };
  function editItemFinancing(pid,regCategory){	   
  		  $('<div id="editAcctountFinancing"/>').dialog({
  				href : BASE_PATH+'financeTransactionController/editAcctountFinancing.action?pid='+pid+'&bankAcctId='+bankAcctId+'&regCategory='+regCategory,
  				width : 800,
  				height : 300,
  				modal : true,
  				title : '新增/编辑融资借款',
  				buttons : [ {
  					text : '保存',
  					iconCls : 'icon-save',
  					handler : function() {
  						window.saveFinancing();
  					}	
  					},{
  						text : '取消 ',
  						iconCls : 'icon-no',
  						handler : function() {
  							$("#editAcctountFinancing").dialog('close');
  							}
  						} ],
  				onClose : function() {
  					$(this).dialog('destroy');
  				},
  				onLoad : function() {
  				}
  			});
  }

  function editRepayment(pid,regCategory,repayType,ftAmtLoan,ftAmtInput){	   
  	
  	  $('<div id="editFinancingRepayment"/>').dialog({
  			href : BASE_PATH+'financeTransactionController/editAcctountFinancing.action?pid='+pid+'&bankAcctId='+bankAcctId+'&regCategory='
  								+regCategory+'&repayType='+repayType+'&ftAmtLoan='+ftAmtLoan+'&ftAmtInput='+ftAmtInput,
  			width : 800,
  			height : 300,
  			modal : true,
  			title : '融资借款还款/支息',
  			buttons : [ {
  				text : '保存',
  				iconCls : 'icon-save',
  				handler : function() {
  					window.saveFinancingRepayment();
  				}	
  				},{
  					text : '取消 ',
  					iconCls : 'icon-no',
  					handler : function() {
  						$("#editFinancingRepayment").dialog('close');
  						}
  					} ],
  			onClose : function() {
  				$(this).dialog('destroy');
  			},
  			onLoad : function() {
  			}
  		});
  }
   
  //删除
   function removeItemFinancing(pid){	  
   	$.messager.confirm("操作提示","确定删除选择的数据吗?",
  		function(r) {
   			if(r){
  				$.post("deleteFinanceFinancing.action",{pid : pid}, 
  					function(ret) {
  						//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
  						  if(ret>0){
  							  $.messager.alert("提示","删除成功！","success");    
  						  }else{
  							  $.messager.alert("提示","删除失败！","success"); 
  						  }
  						  $("#dlg").dialog('close');
  						  $("#financingGrid").datagrid('reload');
  					},'json');
   			}
  		});
  	  	
   }
  //查询 
   function ajaxSearchFinancing() {
      if(!compareDate('ftStartDt','ftEndDt')){
  			return false;
  	}
     var data={
  		    ftStartDt: $('#ftStartDt').datebox('getValue'),
  		    ftEndDt: $('#ftEndDt').datebox('getValue'),
   		};
     
   	$('#financingGrid').datagrid('load',data);
   }
   function operateLoad(value, row, index) {
  	 if(row.pid!=0){
  		 return '<a href="javascript:editItemFinancing('+row.pid+','+row.regCategory+')"><font color="blue">编辑</font></a>|'+
  			'<a href="javascript:editRepayment('+row.pid+',2,1,'+row.ftAmtLoan+','+row.ftAmtInput+')"><font color="blue">还款</font></a>|'+
  			'<a href="javascript:editRepayment('+row.pid+',2,2,'+row.ftAmtLoan+','+row.ftAmtInput+')"><font color="blue">支息</font></a>|'+
  			'<a href="javascript:removeItemFinancing('+row.pid+')" ><font color="blue">删除</font></a>';
  	 }
  		
  	}
   function resetAcctOutputFormFinancing(){
  	 $('#acctOutputFormFinancing').form('reset');
   }

   function saveFinancing(){
   	var ftamt=$('#ftAmt').numberbox('getValue');
   	if(ftamt>0){
   		$('#baseInfo').form('submit', {
   			url : "saveAcctountFinancing.action",
   			onSubmit : function() {return $(this).form('validate');},
   			success : function(result) {
   				if(result>0){
   					$.messager.alert("提示","保存成功！","success");  
   				}else{
   					$.messager.alert("提示","保存失败！","error");  
   				}
   				
   				$("#editAcctountFinancing").dialog('close');
   				$('#financingGrid').datagrid('load');
   			}
   		});
   	}else {
   		$.messager.alert("提示","借款金额必须大于0！","error");  
   	}
   	    
   }

   function saveFinancingRepayment(){
   	//判断发生金额不能大于借款金额
   	//var repayType=${repayType};
   	if(repayType!=2){
   		//var unFtAmtLoan=${unFtAmtLoan};
   		var ftAmt= $('#ftAmt').numberbox('getValue');
   		if(ftAmt>unFtAmtLoan){
   			$.messager.alert("提示","发生金额大于可还的金额！","error"); 
   			return ;	
   		}
   	}
   	$('#baseInfo').form('submit', {
   		url : "saveAcctountFinancing.action",
   		onSubmit : function() {return $(this).form('validate');},
   		success : function(result) {
   			if(result>0){
   				$.messager.alert("提示","保存成功！","success");  
   			}else{
   				$.messager.alert("提示","保存失败！","error");  
   			}
   			
   			$("#editFinancingRepayment").dialog('close');
   			$('#financingGrid').datagrid('load');
   		}
   	});
   }
   	

   	
   	    