
/**
 * 展期信息对象
 */
var extension = {
		oldProjectType:-1,	// 被展期的项目类型
		hisProjectIds:-1,	// 被展期历史项目ID 
		init:function(projectId,newProjectId){
			// 根据新项目ID查询新项目是否存在贷款ID
			loanId = applyCommon.getLoanId(newProjectId);
			// 临时项目ID -- 初始值等于老项目ID
			var temporaryProjectId = projectId;
			// 判断新项目是否存在贷款ID
			if(loanId != -1 ){
				// 如果存在,把新项目的ID赋值到临时项目ID上面
				temporaryProjectId = newProjectId;
			}
		},
		openLoanDetailWin:function(projectId){
			var url = BASE_PATH+"beforeLoanController/addNavigation.action?projectId="+projectId+"&param='refId':'"+projectId+"','projectName':1";
			parent.openNewTab(url,"贷款申请详情",true);
		},
		// 设置展期金额
		setExtensionAmt:function(rec){
			$("#investigationForm #extensionAmt").numberbox("setValue",rec.noReconciliationAmt);
		},
		// 保存贷款信息
		saveExtensionInvestigation:function(){
			// 判断项目名称
			if($("#projectName").val()==null || $("#projectName").val()==""){
				$.messager.alert('操作提示',"项目名称不允许为空!",'error');	
				return;
			}
			
			// 赋值
			$("#investigationForm input[name='projectPid']").val(newProjectId);
			$("#investigationForm input[name='loanId']").val(loanId);
	 		
		},
		// get载展期金额,展期期数
		getLoanExtension:function(projectId){
			$.ajax({
				type: "POST",
		        data:{"projectId":projectId},
		        async:false, 
		    	url : BASE_PATH+'foreLoanExtensionController/getProjectExtension.action',
				dataType: "json",
			    success: function(resultData){
			    	
			    	$('#investigationForm').form('reset');
	        		$('#investigationForm').form('load', resultData);
			    	
			    	var extensionMoney = resultData.extensionAmt;
			    	if(extensionMoney >0){
			    		$("#extensionAmt").val(extensionMoney);
			    	}else{
			    		$("#extensionAmt").val(loanMoney);
			    	}
			    	
			    	var extensionRate = resultData.extensionRate;
			    	if(extensionRate >0){
			    		$("#extensionRate").val(extensionRate);
			    	}else{
			    		$("#extensionRate").val(feeRate);
			    	}
			    	
				}
			});  
		},
		// get 授信信息
		getCreditsInfo:function(projectId){
			var result = {};
			$.ajax({
				type: "POST",
		        data:{"projectId":projectId},
		        async:false, 
		    	url : BASE_PATH+'creditsController/getCreditsInfo.action',
				dataType: "json",
			    success: function(resultData){
			    	result = eval(resultData);
				}
			});  
			return result;
		},
		// 加载展期基本信息
		loadLoanInfo:function(temporaryProjectId,newProjectId,projectId,loanId){
			var url = BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action";
			$.post(url,{projectId : temporaryProjectId},
			  	function(data){
					
					// 根据上面的判断如果是老项目的话,就需要把新的项目名称和项目编码重新赋值重新赋值
					if(loanId == -1){
						applyCommon.setProjectInfo(newProjectId);
					}
					
			 		// 动态绑定担保方式
					applyCommon.bingGuaranteeType(projectId,newProjectId,loanId);
					
			 		
			 		// 加载尽职调查报告的数据
					applyCommon.loadSurveyReport(newProjectId);
			 		// 尽职调查报告 prjectId 保存
			 		$("#surveyReport input[name='projectId']").val(newProjectId);
			 		
			 		// 如果下拉框,数字框，文本框的值是0 默认不显示 zhengxiu
			 		applyCommon.initInput();
					
			  	}, "json");
			
		},
		// 打开新增抵质押物信息的窗口 
		openMortgageWin: function (mortgageGuaranteeType){
			// 初始化省市的选取 
				loadProvince();
			$("#addressProvince").change(function(){
				loadCity();
			});
			$("#addressCity").change(function(){
				loadArea();
			});
			$("#dzywxxForm input[name='mortgageGuaranteeType']").val(mortgageGuaranteeType);
			// 重置form数据
			$("#dzywxxForm").form('reset');
			// 设置pid == -1 标志为新增
			$("#dzywxxForm input[name='pid']").val(-1);
			// 打开窗口
			$('#dlg_dzywxx').dialog('open').dialog('setTitle', "新增抵质押物信息");
		},
		openEditMortgageWin:function (mortgageGuaranteeType){
			// 初始化省市的选取 
				loadProvince();
			$("#addressProvince").change(function(){
				loadCity();
			});
			$("#addressCity").change(function(){
				loadArea();
			});
			// 获取数据
			var row = "";
			// 判断是抵押还是质押
			if(mortgageGuaranteeType == 1){
				// 抵押
				row = $('#dyxx_grid').datagrid('getSelections');
			}else{
				// 质押
				row = $('#zyxx_grid').datagrid('getSelections');
			}
			// 判断选取的数量
			if(row.length == 0){
		 		$.messager.alert("操作提示","请选择数据!","error");
				return;
			}else if(row.length > 1){
				$.messager.alert("操作提示","每次只能修改一条数据,请重新选择!","error");
				return;
			}
			// 重置 
			$("#dzywxxForm").form('reset');
			// 赋值
				$('#dzywxxForm').form('load', row[0]);
			// 打开窗口
			$('#dlg_dzywxx').dialog('open').dialog('setTitle', "编辑抵质押物信息");
		},// 删除抵质押物信息
		removeMortgage : function (mortgageGuaranteeType){
			// 获取数据
			var rows = "";
			// 判断是抵押还是质押
			if(mortgageGuaranteeType == 1){
				// 抵押
				rows = $('#dyxx_grid').datagrid('getSelections');
			}else{
				// 质押
				rows = $('#zyxx_grid').datagrid('getSelections');
			}
			// 判断选取的数量
			if(rows.length == 0){
		 		$.messager.alert("操作提示","请选择数据!","error");
				return;
			}
		 	// 循环获取选取的数据的主键
		 	var pids = "";
			for(var i=0;i<rows.length;i++){
		  		var row = rows[i];
		  		pids += row.pid+",";
		  	}
		 	$.messager.confirm("操作提示","确定删除选择的此批抵质押物信息吗?",
				function(r) {
		 			if(r){
						$.post(BASE_PATH+"mortgageController/deleteProjectAssBase.action",{pids : pids}, 
							function(ret) {
								//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
								if(ret && ret.header["success"]){
									$.messager.alert('操作提示',ret.header["msg"],'info');
									// 判断是抵押还是质押
									if(mortgageGuaranteeType == 1){
										// 必须重置 datagrid 选中的行 
										$("#dyxx_grid").datagrid("clearChecked");
										$("#dyxx_grid").datagrid('reload');
									}else{
										// 必须重置 datagrid 选中的行 
										$("#zyxx_grid").datagrid("clearChecked");
										$("#zyxx_grid").datagrid('reload');
									}
								}else{
									$.messager.alert('操作提示',ret.header["msg"],'error');	
								}
							},'json');
		 			}
				});
		},
		// 保存抵质押物
		saveMortgage: function (projectId){
			alert(projectId +"  "+newProjectId);
			// 判断是执行什么操作
			var urlDzyw = BASE_PATH+"mortgageController/addProjectAssBase.action";
			if($("#dzywxxForm input[name='pid']").val() != -1){
				urlDzyw = BASE_PATH+"mortgageController/updateProjectAssBase.action";
			}
			// 赋值项目ID
			$("#dzywxxForm input[name='projectId']").val(projectId);
			$("#dzywxxForm").form('submit', {
				url : urlDzyw,
				onSubmit : function() {return $(this).form('validate');},
				success : function(result) {
					var ret = eval("("+result+")");
					if(ret && ret.header["success"]){
						// 关闭窗口
						$('#dlg_dzywxx').dialog('close');
						// 操作提示
						$.messager.alert('操作提示',ret.header["msg"],'info');
						// 刷新datagrid
						if($("#dzywxxForm input[name='mortgageGuaranteeType']").val() == 1){
							// 重新加载抵押信息
							$("#dyxx_grid").datagrid('load');
						}else{
							// 重新加载质押信息
							$("#zyxx_grid").datagrid('load');
						}
						// 重置form数据
						$("#dzywxxForm").form('reset');
					}else{
						$.messager.alert('操作提示',ret.header["msg"],'error');
					}
				}
			});
		},
		// 打开保证信息窗口  1 是个人保证窗口   2 是企业法人保证窗口
		openAddPerGuarantee:function (assureType){
			// 判断打开那一个窗口
			if(assureType == 1){
				// 打开个人窗口 
				$('#dlg_personalGuarantee').dialog('open').dialog('setTitle', "个人客户保证人查询");	
			}else{
				// 打开企业窗口
				$('#dlg_corporationGuarantee').dialog('open').dialog('setTitle', "法人保证查询");
			}
		},
		accessControl:function(projectName,workflowKey){
			if(projectName){
				$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
				$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
				$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
				$('.panel-body .easyui-linkbutton ,.panel-body a').removeAttr('onclick');
				$('.panel-body a font').attr('color','gray');
				return ;
		 	}
			// 审批官审查    线下贷审会会议纪要  项目名称,项目编号只读,其它可编辑
			if ("task_LoanRequest" == workflowKey){ // 申请中的申请
				
			}else{
				$('.extension .easyui-linkbutton ,.extension input,.extension textarea').attr('disabled','disabled');
				$('.extension .easyui-linkbutton ,.extension input,.extension textarea').attr('readOnly','readOnly');
				$('.extension .easyui-linkbutton ,.extension input,.extension textarea').addClass('l-btn-disabled');
				$('.extension .easyui-linkbutton ,.extension a').removeAttr('onclick');
				$('.extension a font').each(function(){
					if($(this).text()== "编辑" || $(this).text()== "删除" || $(this).text()== "重新上传"){
						$(this).attr('color','gray');
					}
				});
			}
		},
		// 获取展期目标历史项目ID
		loadHisProjectIds:function(projectId){
			if(projectId !=-1){
				$.ajax({
					type: "POST",
			        async:false, 
			    	url : BASE_PATH+'loanExtensionController/getHisProjectIds.action?projectId='+projectId,
					dataType: "json",
				    success: function(resultData){
				    	extension.hisProjectIds = resultData;
					}
				});  
			}
		},
		// 展期贷款试算
		loanCalc:function(){
			if(loanId==-1){
				$.messager.alert('操作提示','请先保存贷款信息','warning');
			}else{
				operRepaymentList(BASE_PATH+'loanExtensionController/loanCalc.action?loanId='+loanId+"&projectId="+newProjectId);
			}
		}
		, //自动计算出抵抵押率
	 	 mortgageValueInfo:function(){
	 		var assessValue = $('#assessValue').numberbox('getValue')//评估净值（元）
	 		var loanAmt = 	$('#extensionAmt').numberbox('getValue');//尽职调查金额 
	 		 if(assessValue==""){
	 			 return;
	 		 }
	 		 if(loanAmt==""){
	 			return;
	 		 }
	 		
	 		$('#mortgageRate').numberbox('setValue',loanAmt/assessValue*100);

	 		 
	 		/* 
	 		var assessValue = $('#assessValue').numberbox('getValue')//评估净值（元）
	 		var loanAmt = 	$('#extensionAmt').numberbox('getValue');//尽职调查金额 
	 		$('#mortgageRate').numberbox('setValue',loanAmt/assessValue*100);*/
	 	},
	 	setValue1Do:function(){
	 		var outloanDt = $('#planOutLoanDt').datebox('getValue');// 放款日期
	 		applyCommon.repayDateInfo(outloanDt);//根据放款日期判断自动给每期还款日赋值
	 		
	 		extension.setValue2Do();
	 	}
		,// 计算还款期限
		setValue2Do:function(){
			var outloanDt = $('#planOutLoanDt').datebox('getValue');// 放款日期
			var str = $('#planOutLoanDt').datebox('getValue');// 放款日期
			var repayloanDt = $('#planRepayLoanDt').datebox('getValue');// 还款日期
			var str2 = $('#planRepayLoanDt').datebox('getValue');// 还款日期
			var StartDt = $('#credtiStartDt').datebox('getValue');// 授信开始日期
			var EndDt = $('#credtiEndDt').datebox('getValue');// 授信结束日期
			var testInfo = $('#repayFun').combobox('getText');// 还款方式
			var v = $('#repayCycle').textbox('getValue');// 获取还款期限
			
			if (outloanDt != '' && repayloanDt != '') { // 如果两个值不为空直接根据还款方式去操作
				var days = 0;
				if (repayloanDt > outloanDt) {
					if (testInfo == "等额本金" || testInfo == "按月-先息后本"
							|| testInfo == "等额本息" || testInfo == "等本等息"
							|| testInfo == "其他还款方式") {
						checkLoanDate(outloanDt, repayloanDt, str, str2);
					} else if (testInfo == "按日-5天收息，到期还本") {
						days = 5;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-7天收息，到期还本") {
						days = 7;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-10天收息，到期还本") {
						days = 10;
						selectRepayFun(days, outloanDt, repayloanDt);
					} else if (testInfo == "按日-15天收息，到期还本") {
						days = 15;
						selectRepayFun(days, outloanDt, repayloanDt);
					}
				} else {
					alert('计划还款日期要大于计划放款日期');
					$('#planRepayLoanDt').datebox('setValue', '');
				}
			} else { // 否则 直接 还款日期 与还款期限计算出 放款日期
				if (testInfo == "等额本金" || testInfo == "按月-先息后本"
						|| testInfo == "等额本息" || testInfo == "等本等息"
						|| testInfo == "其他还款方式") {
					if (v != "" && v != 0) { // 还款期限不等于空
						if (repayloanDt != "") {
							var newMonth = addMoth(repayloanDt, -v);
							var startDateInfo = DateAdd("d", 1, dateInfo(newMonth)); // 在加一天
							var dates =startDateInfo.format('yyyy-MM-dd');// 转换格式
							// 赋值
							if (dates >= StartDt && dates <= EndDt) {
								$('#planOutLoanDt').datebox('setValue', dates);
							} else {
								alert("通过还款期限与还款日期得出的放款开始日期不在授信时间范围内");
								$('#planRepayLoanDt').datebox('setValue', "");
							}
						}
					}
				} else { // 按天去计算
					Date.prototype.diff = function(date) {
						return (this.getTime() - date.getTime())/ (24 * 60 * 60 * 1000);
					}
					if (testInfo != "其他还款方式") {
						var daysInfo = 0;
						if (testInfo == "按日-5天收息，到期还本") {
							daysInfo = 5;
						}
						if (testInfo == "按日-7天收息，到期还本") {
							daysInfo = 7;
						}
						if (testInfo == "按日-10天收息，到期还本") {
							daysInfo = 10;
						}
						if (testInfo == "按日-15天收息，到期还本") {
							daysInfo = 15;
						}
						if (v != "") {
							var newDate = DateAdd("-d", Number(v * daysInfo),dateInfo(repayloanDt)); // 减去按标准的天数
							var dates = newDate.format('yyyy-MM-dd');
							var newstartDate = DateAdd("d", 1, dateInfo(dates)); // 给当前时间加1
							var values = newstartDate.format('yyyy-MM-dd');// 转换格式
							$('#planOutLoanDt').datebox('setValue', values);
						}
					}
				}
			}
		},
		repayFunOnSekect:function(){
			var opts = $(this).combobox('options');
			var outloanDt = $('#planOutLoanDt').datebox('getValue');
			var str =  $('#planOutLoanDt').datebox('getValue');
			var repayloanDt = $('#planRepayLoanDt').datebox('getValue');
			var str2 = $('#planRepayLoanDt').datebox('getValue');
			var testInfo = $('#repayFun').combobox('getText');//还款方式 
			if(testInfo == "等额本息" || testInfo == "等本等息"){
				$("#repayOption").attr("checked",false);
				$("#repayOption").attr("disabled",'disabled');
				$("#repayOptionTest").attr("checked",false);
				$("#repayOptionTest").attr("disabled",'disabled');
			}else {
				$("#repayOption").removeAttr("disabled");
				$("#repayOptionTest").removeAttr("disabled");
			}
			if(testInfo=="按月-先息后本"|| testInfo=="等额本金" || testInfo=="等额本息"||testInfo=="等本等息" || testInfo=="其他还款方式") { 
				if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
					checkLoanDate(outloanDt,repayloanDt,str,str2);
					return;
				}else if(outloanDt!="" || repayloanDt!=""){
					extension.repayCycleCheck();
				} 
			} else if(testInfo=="按日-5天收息，到期还本" ){
				var str = 5; 
				if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
					selectRepayFun(str,outloanDt,repayloanDt);
				}else if(outloanDt!="" || repayloanDt!=""){
					extension.repayCycleCheck();
				} 
			}else if(testInfo=="按日-7天收息，到期还本"){
				var str = 7;
				if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
					selectRepayFun(str,outloanDt,repayloanDt);
				}else if(outloanDt!="" || repayloanDt!=""){
					extension.repayCycleCheck();
				} 
			}else if(testInfo=="按日-10天收息，到期还本"){
				var str = 10;
				if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
					selectRepayFun(str,outloanDt,repayloanDt);
				}else if(outloanDt!="" || repayloanDt!=""){
					extension.repayCycleCheck();
				} 
			}else if(testInfo=="按日-15天收息，到期还本"){
				var str = 15;
				if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
					selectRepayFun(str,outloanDt,repayloanDt);
					return;
				}else if(outloanDt!="" || repayloanDt!=""){
					extension.repayCycleCheck();
				} 
			}	
		},
		repayCycleOnChange:function(){
			var   v =  $('#repayCycle').textbox('getValue');// 获取还款期限
			var outloanDt = $('#planOutLoanDt').datebox('getValue');//放款开始时间
			var repayloanDt = $('#planRepayLoanDt').datebox('getValue');//放款结束时间
			var StartDt = $('#credtiStartDt').datebox('getValue');//授信开始时间
			var EndDt = $('#credtiEndDt').datebox('getValue');//授信开始结束
			var testInfo = $('#repayFun').combobox('getText');//还款方式 
			if(testInfo=="等额本金"|| testInfo=="按月-先息后本" || testInfo=="等额本息" || testInfo=="等本等息"  ){
				if(StartDt!=""&&EndDt!=""){
					 if(v!="" && v!=0){
						 if(outloanDt!=""){
								var endTime =  addMoth(outloanDt,Number(v)); 
								var compare  = outloanDt.substr(8); //截取yyyy-mm-dd最后两位 
								var compare1 =  dateInfo(endTime).format('yyyy-MM-dd').substr(8);//
								if(Number(compare)==Number(compare1)){
									 var newDate = DateAdd("-d", 1, dateInfo(endTime)); // 在减一天
									 endTime = newDate.format('yyyy-MM-dd');// 转换格式 赋值 */
								}
								 //if(new Date(endTime)>= new Date(StartDt) &&  new Date(endTime)<=new Date(EndDt)){
									 $('#planRepayLoanDt').datebox('setValue',endTime);
								 //}else{
								//	 alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
								//	 $('#planOutLoanDt').datebox('setValue',"");
								//	 $('#planRepayLoanDt').datebox('setValue',"");
								//	 return ;
								// }
								 return; 
						 }else  if(repayloanDt!=""){
							 var newMoth =  addMoth(repayloanDt,-v); 
							 if(dateInfo(newMoth) >= dateInfo(StartDt) && dateInfo(newMoth) <= dateInfo(EndDt)){
								 $('#planOutLoanDt').datebox('setValue',newMoth);
							 }else{
								 alert("通过还款期限与放款结束时间得出的放款开始时间不在授信时间范围内");
								 $('#planOutLoanDt').datebox('setValue',"");
								 $('#planRepayLoanDt').datebox('setValue',"");
								 return ;
							 }
							 return;
						 } 
					}else{
						return ;
					}
				}
			}else {//按天去计算
				Date.prototype.diff = function(date){
					  return (this.getTime() - date.getTime())/(24 * 60 * 60 * 1000);
				}
				if(testInfo!="其他还款方式"){
					var str = 0;
					if(testInfo=="按日-5天收息，到期还本") {
						str = 5;
					}  
					if(testInfo=="按日-7天收息，到期还本" ) {
						str = 7;
					} 
					if(testInfo=="按日-10天收息，到期还本") {
						str = 10;
					} 
					if(testInfo=="按日-15天收息，到期还本") {
						str = 15;
					}
					if(v!=""){
						if(outloanDt!=""){
							 var newDate   =   DateAdd("d",Number(v*str-1),dateInfo(outloanDt));  
							 var dates = newDate.format('yyyy-MM-dd');
							 //if(dates>=StartDt && dates<=EndDt){
								 $('#planRepayLoanDt').datebox('setValue',dates);
							 //}else{
							//	 alert("计划还款日期不在授信日期范围内");
							//	 $('#planRepayLoanDt').datebox('setValue',"");
							// }
							
						}else if(repayloanDt!=""){
							 var newDate   =   DateAdd("-d",Number(v*str+1),dateInfo(repayloanDt));  
							 var dates = newDate.format('yyyy-MM-dd');
							 if(dates>=StartDt && dates<=EndDt){
								 $('#planOutLoanDt').datebox('setValue',str);
							 }else{
								 alert("计划开始日期不在授信日期范围内");
								 $('#planOutLoanDt').datebox('setValue',"");
							 }
						}
					}
				}
			}
		},
		repayCycleCheck:function () {
			var v = $('#repayCycle').textbox('getValue');// 获取还款期限
			var outloanDt = $('#planOutLoanDt').datebox('getValue');// 放款开始时间
			var repayloanDt = $('#planRepayLoanDt').datebox('getValue');// 放款结束时间
			var StartDt = $('#credtiStartDt').datebox('getValue');// 授信开始时间
			var EndDt = $('#credtiEndDt').datebox('getValue');// 授信开始结束
			var testInfo = $('#repayFun').combobox('getText');// 还款方式
			if (testInfo == "等额本金" || testInfo == "按月-先息后本" || testInfo == "等额本息"
					|| testInfo == "等本等息" || testInfo == "其他还款方式") {
				if (StartDt != "" && EndDt != "") {
					if (v != "") {
						if (outloanDt != "" && repayloanDt == "") {
							var newMonth = addMoth(outloanDt, v);
							//if (new Date(newMonth) >= new Date(StartDt) && new Date(newMonth) <= new Date(EndDt)) {
								$('#planRepayLoanDt').datebox('setValue', newMonth);
							//} else {
							//	alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
							//	setTimeout(function() {// 让方法延迟100
							//		$('#repayCycle').textbox('setValue', "");
							//	}, 100);
							//	return;
							//}
						} else if (outloanDt == "" && repayloanDt != "") {
							var newMonth = addMoth(repayloanDt, -v);
							if (dateInfo(newMonth) >= dateInfo(StartDt)
									&& dateInfo(newMonth) <= dateInfo(EndDt)) {
								$('#planOutLoanDt').datebox('setValue', newMonth);
							} else {
								alert("通过还款期限与放款结束时间得出的放款开始时间不在授信时间范围内");
								setTimeout(function() {
									$('#repayCycle').textbox('setValue', "");
								}, 100);
								return;
							}
						} else if (outloanDt != "" && repayloanDt != "") {// 如果两个有值 直接
							// 拿开始日期相加
							var newMonth = addMoth(outloanDt, v);
							var newDate = DateAdd("-d", 1, dateInfo(newMonth)); // 在减一天
							var dates = newDate.format('yyyy-MM-dd');// 转换格式 赋值
							//if (dates >= StartDt && dates <= EndDt) {
								$('#planRepayLoanDt').datebox('setValue', dates);
							//} else {
							//	alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
							//	setTimeout(function() {
							//		$('#repayCycle').textbox('setValue', 1);
							//	}, 100);
							//	return;
							//}
						}
					} else {
						return;
					}
				}
			} else {// 按天去计算
				Date.prototype.diff = function(date) {
					return (this.getTime() - date.getTime()) / (24 * 60 * 60 * 1000);
				}
				if (testInfo != "其他还款方式") {
					var str = 0;
					if (testInfo == "按日-5天收息，到期还本") {
						str = 5;
					}
					if (testInfo == "按日-7天收息，到期还本") {
						str = 7;
					}
					if (testInfo == "按日-10天收息，到期还本") {
						str = 10;
					}
					if (testInfo == "按日-15天收息，到期还本") {
						str = 15;
					}
					if (v != "") {
						if (outloanDt != "") {
							var newDate = DateAdd("d", Number(v * str - 1), dateInfo(outloanDt));
							var dates = newDate.format('yyyy-MM-dd');
							//if (dates >= StartDt && dates <= EndDt) {
								$('#planRepayLoanDt').datebox('setValue', dates);
							//} else {
							//	alert("计划还款日期不在授信日期范围内");
							//	$('#planRepayLoanDt').datebox('setValue', "");
							//}

						} else if (repayloanDt != "") {
							var newDate = DateAdd("-d", Number(v * str + 1), dateInfo(repayloanDt));
							var dates = newDate.format('yyyy-MM-dd');
							if (dates >= StartDt && dates <= EndDt) {
								$('#planOutLoanDt').datebox('setValue', str);
							} else {
								alert("计划开始日期不在授信日期范围内");
								$('#planOutLoanDt').datebox('setValue', "");
							}
						}
					}
				}
			}
		}
}

/**
 * 展期文件对象
 */
var extensionFile = {
		// 打开文件上传框
		openWin:function(){
			$("#fileUrlTr").show();
			$("#fileTypeTr").show();
			$("#fileDescTr").show();
			// 重置form数据
			$("#extensionFileForm").form('reset');
			$("#extensionFileForm").attr("enctype","multipart/form-data");
			$("#extensionFileForm input[name='opertionType']").val("save");
			// 赋值项目ID
			$("#extensionFileForm input[name='projectId']").val(newProjectId);
			// 打开窗口
			$('#dlg_extensionFile').dialog('open').dialog('setTitle', "展期资料上传");
		},
		// 编辑
		editOpenWin:function(index){
			$("#fileUrlTr").hide();
			$("#fileTypeTr").show();
			$("#fileDescTr").show();
			var rows = $("#extensionFile_grid").datagrid("getRows");
			var row = rows[index];
			$("#extensionFileForm input[name='pid']").val(row.pid);
			$('#extensionFileForm #fileCategory').combobox('select', row.fileCategory);
			if(fileDesc == 'undefined'){
				fileDesc = '';
			}
			$("#extensionFileForm #fileDesc").textbox('setValue',row.fileDesc);
			$("#extensionFileForm input[name='opertionType']").val("edit");
			$("#extensionFileForm").removeAttr("enctype");
			$('#dlg_extensionFile').dialog('open').dialog('setTitle', "展期资料编辑");
		},// 重新上传
		modifyUploadOpenWin:function(fileId){
			$("#fileTypeTr").hide();
			$("#fileDescTr").hide();
			$("#fileUrlTr").show();
			$("#extensionFileForm").attr("enctype","multipart/form-data");
			$("#extensionFileForm input[name='opertionType']").val("modify");
			$("#extensionFileForm input[name='fileId']").val(fileId);
			$('#dlg_extensionFile').dialog('open').dialog('setTitle', "重新上传");
		},
		// 保存上传信息
		saveExtensionFile:function(){
			var opertionType = $("#extensionFileForm input[name='opertionType']").val();
			var url = "";
			if(opertionType == "save"){
				url = "saveFile.action";
			}else if (opertionType == "edit"){
				url = "updateFile.action";
			}else if (opertionType == "modify"){
				url = "modifyFile.action";
			}
			$("#extensionFileForm").form('submit', {
				url : url,
				onSubmit : function() {return $(this).form('validate');},
				success : function(result) {
					var datas =eval("("+result+")");
					$.messager.alert("操作提示",datas.uploadStatus,"success");
					$('#dlg_extensionFile').dialog("close");
					$('#extensionFile_grid').datagrid("reload");
				}
			});
		},
		// 批量删除文件
		removeFileAll:function(){
			var rows = $('#extensionFile_grid').datagrid('getSelections');
		 	if ( rows.length == 0 ) {
		 		$.messager.alert("操作提示","请选择数据","error");
				return;
			}// 获取选中的系统用户名 
		 	var fileId = "";
			for(var i=0;i<rows.length;i++){
		  		if(i==0){
		  			fileId+=rows[i].pid;
		  		}else{
		  			fileId+=","+rows[i].pid;
		  		}
		  	}
			extensionFile.removeFile(fileId);
		},
		// 删除文件
		removeFile:function(fileId){
		 	$.messager.confirm("操作提示","确定删除选择的申请书吗?",
				function(r) {
		 			if(r){
						$.post("deleteFile.action",{fileId:fileId}, 
							function(result) {
								$.messager.alert("操作提示",result.uploadStatus,"success");
								$('#dlg_extensionFile').dialog("close");
								$('#extensionFile_grid').datagrid("reload");
							},'json');
		 			}
				});
		},
		// 操作格式化
		operationFormater:function(value, row, index){
			var downloadDom = "";
			var dataDelDom = "";
			if(row.fileUrl=='' || row.fileUrl==null ||row.fileUrl==undefined){
				downloadDom = "<a href='javascript:void(0)'><font color='gray'>下载</font> | </a>";
			}else{
				downloadDom = "<a href='"+BASE_PATH+"beforeLoanController/downloadData.action?path="+row.fileUrl+"&fileName="+row.fileName+"'><font color='blue'>下载</font> | </a>";
			}
			// 如果是详情链接进来, 重新下载 删除 编辑 不能使用
			if(biaoZhi != 3){
				if(row.pid=='' || row.pid==null ||row.pid==undefined){
					dataDelDom = "<a href='javascript:void(0)' ><font color='gray'>删除</font></a> | ";
				}else{
					dataDelDom = "<a href='javascript:void(0)' onclick='extensionFile.removeFile("+row.pid+")'><font color='red'>删除</font></a>  ";	
				}
				var dataEditDom = "<a href='javascript:void(0)' onclick=extensionFile.editOpenWin('"+index+"')><font color='blue'>编辑</font></a> | ";
				var modify = "<a href='javascript:void(0)' onclick='extensionFile.modifyUploadOpenWin("+row.fileId+")'><font color='blue'>重新上传</font></a> | ";
				
				return downloadDom+modify+dataEditDom+dataDelDom;
			}else{
				return downloadDom+"<font color='grey'>重新下载 | 编辑  | 删除</font>"	
			}
			
		},fileSizeFormat : function (value,row,index){
			var sNum='';
			if(value==0 || value == undefined|| value==null || value ==''){
			}else{
				sNum = getFileSize(value);
			}
			return sNum;
		}
		
}