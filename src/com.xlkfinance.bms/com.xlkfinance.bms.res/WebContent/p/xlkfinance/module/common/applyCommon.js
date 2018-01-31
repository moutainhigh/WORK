/**
 * 申请单公用类
 */
var applyCommon = {
		WORKFLOW_ID:'',
		projectId:-1,	// 项目ID
		newProjectId:-1, //新的项目ID
		loanId:-1,		// 贷款ID
		creditId:-1,	// 授信ID
		biaoZhi:-1,		// 流程设置为1,编辑设置为2,查看设置为3
		acct_id:0,		// 账户id 
		com_id:0,		// 企业id
		approveScrutiny:-1,	// 审批官审批是否保存标识 -1 未保存 1 保存
		offlineMeeting:-1,	// 线下贷审会议是否保存标识 -1 未保存 1 保存
		organization:-1,	// 组织召开贷审会是否保存标识 -1 未保存 1 保存 
		init:function(){
			//付款方式 onselect 方法绑定
			applyCommon.repayFunOnSekect();
			//校验时间
			applyCommon.initCheckDate();
			//还款期限初始化 
			applyCommon.repayCycleTdOnSekect();
			//给月利率绑定鼠标事件
			applyCommon.monthLoanOtherFeeOnBlur();
			//给年利率绑定鼠标事件
			applyCommon.yearLoanOtherFeeOnBlur();
			//给日利率绑定鼠标事件
			applyCommon.dayLoanOtherFeeOnBlur();
			//类型重灰
			applyCommon.repayCycleTypeOnSelect();
			//担保措施 省市信息初始化
			applyCommon.initAddress();
		},
		// 初始化事件参数
		initCheckDate:function(){
			$.extend($.fn.validatebox.defaults.rules, {
				checkDateRange: {
					validator: function(value,param){
						return value > $(param[0]).datebox('getValue');
					},
					message: '结束时间必须大于开始时间.'
				}
			});
		},
		//付款方式 onselect 方法绑定
		repayFunOnSekect:function(){
			// 展期申请不使用
			if(WORKFLOW_ID == "extensionRequestProcess"){
				return ;
			}
			$("input[name='repayFun']").combobox({
				onSelect: function(row){
					var opts = $(this).combobox('options');
					var outloanDt = $('#planOutLoanDt').datebox('getValue');
					var str =  $('#planOutLoanDt').datebox('getValue');
					var repayloanDt = $('#planRepayLoanDt').datebox('getValue');
					var str2 = $('#planRepayLoanDt').datebox('getValue');
					if(row[opts.textField] == "等额本息" || row[opts.textField] == "等本等息"){
						$("#repayOption").attr("checked",false);
						$("#repayOption").attr("disabled",'disabled');
						$("#repayOptionTest").attr("checked",false);
						$("#repayOptionTest").attr("disabled",'disabled');
					}else {
						$("#repayOption").removeAttr("disabled");
						$("#repayOptionTest").removeAttr("disabled");
					}
					if(row[opts.textField]=="按月-先息后本"|| row[opts.textField]=="等额本金" || row[opts.textField]=="等额本息"||row[opts.textField]=="等本等息" || row[opts.textField]=="其他还款方式") { 
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							checkLoanDate(outloanDt,repayloanDt,str,str2);
							return;
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					} else if(row[opts.textField]=="按日-5天收息，到期还本" ){
						var str = 5; 
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}else if(row[opts.textField]=="按日-7天收息，到期还本"){
						var str = 7;
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}else if(row[opts.textField]=="按日-10天收息，到期还本"){
						var str = 10;
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}else if(row[opts.textField]=="按日-15天收息，到期还本"){
						var str = 15;
						if(outloanDt!="" && repayloanDt!=""){//如果计划放款开始跟计划还款日期不为空 就通过它俩得出还款期限  （按月）
							selectRepayFun(str,outloanDt,repayloanDt);
							return;
						}else if(outloanDt!="" || repayloanDt!=""){
							repayCycleCheck();
						} 
					}		 
				}
			});
		},
		//还款期限初始化 
		repayCycleTdOnSekect:function(){
			// 展期申请不使用
			if(WORKFLOW_ID == "extensionRequestProcess"){
				return ;
			}
			$('body').delegate('.repayCycleTd > span','blur',function(){
				var   v =  $(this).find(' > input').val();//获取还款期限
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
									/*var compare1 =  endTime.format('yyyy-MM-dd').substr(8);*/	
									var compare1 =  endTime.substr(8);//
									if(Number(compare)==Number(compare1)){
										 var newDate = DateAdd("-d", 1, dateInfo(endTime)); // 在减一天
										 endTime = newDate.format('yyyy-MM-dd');// 转换格式 赋值 */
									}
									 if(dateInfo(endTime)>= dateInfo(StartDt) &&  dateInfo(endTime)<=dateInfo(EndDt)){
										 $('#planRepayLoanDt').datebox('setValue',endTime);
									 }else{
										 alert("通过还款期限与放款开始时间得出的放款结束时间不在授信时间范围内");
										 $('#planOutLoanDt').datebox('setValue',"");
										 $('#planRepayLoanDt').datebox('setValue',"");
										 return ;
									 }
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
								 var dates =  newDate.format('yyyy-MM-dd');
								 if(dates>=StartDt && dates<=EndDt){
									 $('#planRepayLoanDt').datebox('setValue',dates);
								 }else{
									 alert("计划还款日期不在授信日期范围内");
									 $('#planRepayLoanDt').datebox('setValue',"");
								 }
								
							}else if(repayloanDt!=""){
								 var newDate   =   DateAdd( "-d",Number(v*str+1),dateInfo(repayloanDt));  
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
			});
		},

		//给月利率绑定鼠标离开事件 
		monthLoanOtherFeeOnBlur:function(){
			$('body').delegate('.monthLoanOtherFee > span','blur',function(){
				var v2= $(this).find(' > input').val();//获取到当前的值 
				var t=0;
				$('.monthLoanOtherFee > span').each(function(){//循环三个月利率 把值叠加起来 算出合计利率 
					t+= Number($(this).find(' > input').val());
				});
				
				$("#total").numberbox('setValue', parseFloat(t) ); //合计利率 
				$(this).parent().parent().find('.yearLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*12.0);//年化利率
				$(this).parent().parent().find('.dayLoanOtherFee .easyui-numberbox').numberbox('setValue',v2/30.0);//日化利率
			})
		},
		//年利率绑定鼠标离开事件  
		yearLoanOtherFeeOnBlur:function(){
			$('body').delegate('.yearLoanOtherFee > span','blur',function(){//给月利率绑定鼠标离开事件 
				var v2= $(this).find(' > input').val();//获取到当前的值 
				var t=0;
				$('.monthLoanOtherFee > span').each(function(){//循环三个月利率 把值叠加起来 算出合计利率 
					t+= Number($(this).find(' > input').val());
				});
				$("#total").numberbox('setValue', parseFloat(t) ); //合计利率 
				$(this).parent().parent().find('.monthLoanOtherFee .easyui-numberbox').numberbox('setValue',v2/12.0);//年化利率
				$(this).parent().parent().find('.dayLoanOtherFee .easyui-numberbox').numberbox('setValue',v2/360.0);//日化利率
			})
		},
		//日利率绑定鼠标离开事件  
		dayLoanOtherFeeOnBlur:function(){
			$('body').delegate('.dayLoanOtherFee > span','blur',function(){//给月利率绑定鼠标离开事件 
				var v2= $(this).find(' > input').val();//获取到当前的值 
				var t=0;
				$('.monthLoanOtherFee > span').each(function(){//循环三个月利率 把值叠加起来 算出合计利率 
					t+= Number($(this).find(' > input').val());
				});
				$("#total").numberbox('setValue', parseFloat(t) ); //合计利率 
				$(this).parent().parent().find('.monthLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*30.0);//年化利率
				$(this).parent().parent().find('.yearLoanOtherFee .easyui-numberbox').numberbox('setValue',v2*360.0);//日化利率
			})
		},
		//还款周期置灰不让操作 
		repayCycleTypeOnSelect:function(){
		 	$("input[name='repayCycleType']").combobox({
				onSelect: function(row){
					var opts = $(this).combobox('options');
					if(row[opts.textField]=="自定义")
					{
						$("#repayCycleDate").numberbox("enable");
					}else{
						$("#repayCycleDate").numberbox("disable");
					}		 
				}
			});
		},// 设置项目信息 
		setProjectInfo : function(projectId){
			$.post( BASE_PATH+'beforeLoanController/getProjectNameOrNumber.action?projectId='+projectId,
					function(data){
						// 重新设置项目名称和项目编号
						$("#projectName").val(data.projectName);
			  			$("#projectNumber").val(data.projectNumber);
			  			// 设置项目经理 
						$("#realName").combobox('select',data.pmUserId);
				}, "json");
		},
		// 加载尽职调查报告
		loadSurveyReport:function(projectId){
			// 尽职调查报告URL			  			
			var urlSr = BASE_PATH+"secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
			// 加载尽职调查报告的数据
			$.post(urlSr,
				function(ret){
					$("#surveyReport").form('load',ret);
				},"json");
		},
		// 根据项目ID 获取贷款ID
		getLoanId:function(projectId){
			var loanId = -1;
			$.ajax({
				type: "POST",
		    	url :   BASE_PATH+"secondBeforeLoanController/getLoanIdByProjectId.action?projectId="+projectId,
				async:false, 
			    success: function(data){
			    	var str = eval("("+data+")");
			    	loanId = str.header["msg"];
				}
			});
			return loanId;
		}, 
		// 获取绑定方式值
		getGuaranteeType:function(projectId){
			var result = "";
			$.ajax({
				type: "POST",
		        data:{"projectId":projectId},
		    	url : BASE_PATH+'secondBeforeLoanController/getGuaranteeTypeByProjectId.action',
				dataType: "json",
				async:false,
			    success: function(data){
			    	var str = eval(data);
			    	for(var i = 0;i<str.length;i++){
			    		if(result == ""){
			    			result = str[i].guaranteeType;
			    		}else{
			    			result += ","+str[i].guaranteeType;
			    		}
			    	}
				}
			});
			return result+"";
		},
		// 动态绑定担保方式
		bingGuaranteeType:function(projectId,newProjectId,loanId){
			// 查询老项目的担保方式
			var guaranteeType = applyCommon.getGuaranteeType(projectId);
			var newGuaranteeType = "";
			// 判断是否保存贷款基本信息 保存过则取新项目的担保方式赋值
			if(loanId != -1){
				newGuaranteeType = applyCommon.getGuaranteeType(newProjectId);
				if(newGuaranteeType.split(",").length == 1){
					$("#assWay").combobox("setValue",newGuaranteeType);
				}else{
					$("#assWay").combobox("setValues",newGuaranteeType.split(','));
				}
			}else{
				if(guaranteeType.split(",").length == 1){
					$("#assWay").combobox("setValue",guaranteeType);
				}else{
					$("#assWay").combobox("setValues",guaranteeType.split(','));
				}
			}
			
			$("#assWayTemp").val(guaranteeType);
			
	    	// 显示 or 隐藏抵质押物担保
			setTimeout("InitializationMortgage()",1);
		},
		//
		loadOtherFee : function(loanOtherFee){					
			if(loanOtherFee!=""){
		 		 $.ajax({
						type: "POST",
				        data:{"pid":loanOtherFee},
				    	url : BASE_PATH+'financeController/editFinanceAcctManageUrl.action',
						dataType: "json",
					    success: function(data){
					    	var str = eval(data);
					    	$("#loanOtherFeeNo").val(str.bankNum);
						}
					}); 
			}
		},
		// 
		loadInterestRecord:function(loanInterestRecord){
			if(loanInterestRecord!=""){
				$.ajax({
					type: "POST",
			        data:{"pid":loanInterestRecord},
			    	url : BASE_PATH+'financeController/editFinanceAcctManageUrl.action',
					dataType: "json",
				    success: function(data){
				    	var str = eval(data);
				    	$("#loanInterestRecordNo").val(str.bankNum);
					}
				});  
			}
		},
		//
		loadMgrRecord:function(loanMgrRecord){
			if(loanMgrRecord!=""){
			 $.ajax({
					type: "POST",
			        data:{"pid":loanMgrRecord},
			    	url : BASE_PATH+'financeController/editFinanceAcctManageUrl.action',
					dataType: "json",
				    success: function(data){
				    	var str = eval(data);
				    	$("#loanMgrRecordNo").val(str.bankNum);
					}
				}); 
			}
		},
		//贷款试算
		loancalc : function (){
			if(loanId==-1){
				$.messager.alert('操作提示','请先保存贷款信息','warning');
			}else{
				operRepaymentList(BASE_PATH+'beforeLoanController/loanCalc.action?loanId='+loanId);
			}
		},
		// 如果下拉框,数字框，文本框的值是0 默认不显示 zhengxiu
		initInput:function(){
			$('.easyui-combobox').each(function(i){
				var cValue=$(this).combobox('getValue');
				if(cValue=='0' ||cValue=='0.0'||cValue=='0.00'){
					$(this).combobox('setValue','');
				}
			});
			$(".easyui-textbox").each(function(i) {
				var v=$(this).textbox('getValue');
			    if( v=='0' ||v=='0.0'||v=='0.00'){
			    	$(this).textbox('setValue','');
			    };       
			}); 
		},
		// 详情状态禁用按钮文本框
		detailDisable:function(biaoZhi,status){
			if(biaoZhi == status){
				$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
				$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
				$('.panel-body .easyui-linkbutton:not(.download) ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
				$('#loan_workflow_from .beforeloanTable').addClass('none');
				loadHistoryApprover(projectId,WORKFLOW_ID);
			}
		},
		// 保存抵质押物
		saveDzyw : function (projectId){
			// 赋值项目ID--抵质押物新增
	 		$("#dzywxxForm input[name='projectId']").val(projectId);
	 		// 判断是执行什么操作
	 		var urlDzyw = BASE_PATH+'mortgageController/addProjectAssBase.action';
	 		if($("#dzywxxForm input[name='pid']").val() != -1){
	 			urlDzyw = BASE_PATH+'mortgageController/updateProjectAssBase.action';
	 		}
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
		// 保存尽职调查报告--针对于展期，额度。
		 saveSurveyReport:function(){
			url = "";
			url = BASE_PATH+"secondBeforeLoanController/updateSurveyReport.action";
			$("#surveyReport").form('submit', {
				url : url,
				onSubmit : function() {return $(this).form('validate');},
				success : function(result) {
					var ret = eval("("+result+")");
					if(ret && ret.header["success"]){
						surPid = ret.header["msg"];
						$("#surveyReport input[name='pid']").val(surPid);
						$.messager.alert('操作提示',"修改成功!",'info');
					}else{
						$.messager.alert('操作提示',ret.header["msg"],'error');	
					}
				}
			});
		},
		// 根据选取不同,展示不同的DIV
		selectMortgageType : function (rec){
			var str = rec.lookupDesc;
			if(str == "抵押"){
				// 显示抵押列表
				$("#mortgageNews").show();
			}else if(str == "质押"){
				// 显示质押列表
				$("#pledgeNews").show();
			}else if(str == "保证"){
				// 显示个人和企业保证信息列表
				$("#personalGuaranteeNews").show();
				$("#corporationGuaranteeNews").show();
			}
		},
		// 根据取消不同的选择项,隐藏不同的DIV
		unSelectMortgageType:function (rec){
			var asswayTemp = $("#assWayTemp").val();
			var assways = asswayTemp.split(",");
			for(var i in assways){
				if(rec.pid == assways[i]){
					alert("担保方式在原项目的基础上只可增加,不可减少!");
					$('#assWay').combobox('select', rec.pid);
					return ;
				}
			}
			var str = rec.lookupDesc;
			if(str == "抵押"){
				// 隐藏抵押列表
				$("#mortgageNews").hide();
			}else if(str == "质押"){
				// 隐藏质押列表
				$("#pledgeNews").hide();
			}else if(str == "保证"){
				// 隐藏个人和企业保证信息列表
				$("#personalGuaranteeNews").hide();
				$("#corporationGuaranteeNews").hide();
			}
		},// 
		getLoanProjectByProjectId:function(projectId){
			var result = {};
			$.ajax({
				type: "POST",
		        async:false, 
		    	url : BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action?projectId="+projectId,
				dataType: "json",
			    success: function(data){
			    	result = data;
				}
			}); 
			return result;
		},
		// 加载授信项目
		loadCreditsInfo:function(projectId){
			// 加载旧项目授信信息
			var url = BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action";
	    	$.post(url,
	    		   {projectId : projectId},
					function(data){
						$('#creditAmt').numberbox('setValue', data.creditAmt);
						$('#credtiStartDt').datebox('setValue', data.credtiStartDt);
						$('#credtiEndDt').datebox('setValue', data.credtiEndDt);
						if(data.isHoop==1){//是否循环项是否隐藏
			  				$(".selectlist1").css("display",'block');
			  				$("input[name='isHoop'][value='1']").attr("checked",true);
			  			}else{
			  				$(".selectlist1").css("display",'none');
			  				$("input[name='isHoop'][value='2']").attr("checked",true);
			  				$("#circulateType").val("");
			  			}
	    		   },"json");
		},
		initAddress:function(){
			// 初始化省市的选取  mortgage 在 project_mortgage.js 中
			mortgage.loadProvince();
			$("#addressProvince").change(function(){
				mortgage.loadCity();
			});
			$("#addressCity").change(function(){
				mortgage.loadArea();
			});
		},
		// 老项目的抵质押物信息是不能修改的
		dyxx_gridOnLoadSuccess : function (projectId){
			$("#dyxx_grid").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
						// 防止传过来的参数为number 类型
						projectId = projectId+"";
						var projectIds = projectId.split(",");
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			            	for(var j =0; j<projectIds.length; j++){
				                if (data.rows[i].projectId == projectIds[j] ) {
				                	$('#dyxxDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
				                	$('#dyxxDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
				            	}
			            	}
			        	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#dyxxDiv tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#dyxx_grid").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的质押押物信息是不能修改的
		dyxx_grid_checkOnLoadSuccess : function (projectId){
			$("#zyxx_grid").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
						// 防止传过来的参数为number 类型
						projectId = projectId+"";
						var projectIds = projectId.split(",");
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			            	for(var j =0; j<projectIds.length; j++){
			            		if (data.rows[i].projectId == projectIds[j] ) {
				                	$('#zyxxDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
				                	$('#zyxxDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
				            	}
			            	}
			        	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#zyxxDiv tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#zyxx_grid").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的个人保证信息是不能修改的
		perGuarantee_gridOnLoadSuccess : function (projectId){
			$("#perGuarantee_grid").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
						// 防止传过来的参数为number 类型
						projectId = projectId+"";
						var projectIds = projectId.split(",");
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			            	for(var j =0; j<projectIds.length; j++){
			            		if (data.rows[i].projectId == projectIds[j] ) {
				                	$('#personalGuarantee_div .datagrid-header input[type="checkbox"]').attr('disabled',true);
				                	$('#personalGuarantee_div tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            		}
			            	}
		            	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#personalGuarantee_div tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#perGuarantee_grid").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的办理抵制押物信息是不能修改的
		mortgage_gridOnLoadSuccess : function (projectId){
			$("#grid_loadZy").datagrid({
				onLoadSuccess : function(data){
					if (data.rows.length > 0) {
						// 防止传过来的参数为number 类型
						projectId = projectId+"";
						
						var projectIds = projectId.split(",");
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			            	for(var j =0; j<projectIds.length; j++){
			            		if (data.rows[i].projectId == projectIds[j] ) {
				                	$('.contractListDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
				                	$('.contractListDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            		}
			            	}
			        	}
			            // 数据加载完后,控制表单是否禁用
			            applyCommon.mortgageFormControl();
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('.contractListDiv tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#grid_loadZy").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的企业法人保证信息是不能修改的
		corGuarantee_gridOnLoadSuccess : function (projectId){
			$("#corGuarantee_grid").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
						// 防止传过来的参数为number 类型
						projectId = projectId+"";
						
						var projectIds = projectId.split(",");
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			            	for(var j =0; j<projectIds.length; j++){
			            		if (data.rows[i].projectId == projectIds[j] ) {
			            			$('#corporationGuarantee_div .datagrid-header input[type="checkbox"]').attr('disabled',true);
			            			$('#corporationGuarantee_div tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            		}
			            	}
		            	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#corporationGuarantee_div tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#corGuarantee_grid").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的借据合同信息是不能修改的
		receiptContract_gridOnLoadSuccess : function (projectId){
			$("#grid_receipt_contract").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			                if (data.rows[i].projectId == projectId ) {
			                	$('#receiptContractList .datagrid-header input[type="checkbox"]').attr('disabled',true);
			                	$('#receiptContractList tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            	}
			        	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#receiptContractList tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#grid_receipt_contract").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的抵押合同信息是不能修改的
		mortgageContract_gridOnLoadSuccess : function (projectId){
			$("#grid_contract22").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			                if (data.rows[i].projectId == projectId ) {
			                	$('#mortgageContractDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
			                	$('#mortgageContractDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            	}
			        	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#mortgageContractDiv tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#grid_contract22").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的质押合同信息是不能修改的
		pledgeContract_gridOnLoadSuccess : function (projectId){
			$("#grid_contract33").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			                if (data.rows[i].projectId == projectId ) {
			                	$('#pledgeContractDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
			                	$('#pledgeContractDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            	}
			        	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#pledgeContractDiv tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#grid_contract33").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的保证合同信息是不能修改的
		guaranteeContract_gridOnLoadSuccess : function (projectId){
			$("#grid_contract44").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			                if (data.rows[i].projectId == projectId ) {
			                	$('#guaranteeContractDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
			                	$('#guaranteeContractDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            	}
			        	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#guaranteeContractDiv tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#grid_contract44").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 老项目的授信合同信息是不能修改的
		creditContract_gridOnLoadSuccess : function (projectId){
			$("#grid_contract").datagrid({
				onLoadSuccess : function(data){
					 if (data.rows.length > 0) {
			         	// 循环判断操作为新增的不能选择
			            for (var i = 0; i < data.rows.length; i++) {
			            	// 根据老项目ID让某些行不可选
			                if (data.rows[i].projectId == projectId ) {
			                	$('#creditContractDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
			                	$('#creditContractDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
			            	}
			        	}
			    	}
				},
		        onClickRow: function(rowIndex, rowData){
		            // 加载完毕后获取所有的checkbox遍历		            
			        if($('#creditContractDiv tr[datagrid-row-index="'+rowIndex+'"] input[type="checkbox"]').attr('disabled')){
			        	$("#grid_contract").datagrid('unselectRow', rowIndex);
			        }
		        } 
			});
		},
		// 抵质押物环表单禁用控制
		mortgageFormControl:function(){
			if("task_MortgageRegistration" == workflowTaskDefKey ){
				
			}else{
				// 禁用 办理抵质押物 按钮
				$("#toolbar_bldzyw a font").attr('color','gray');
				$('#toolbar_bldzyw a').removeAttr('onclick');
				$('#toolbar_bldzyw a').attr('disabled','disabled');
				$('#toolbar_bldzyw a').addClass('l-btn-disabled');
			}
		},// 借据tab 页是否编辑
		receiptTabIsEdit:function(projectId){
			var result = true;
			var url = BASE_PATH+'creditsContractController/getContractListUrl.action?projectId='+projectId+'&contractCatelogKey=JJ';
			 $.ajax({
					type: "POST",
			    	url : url,
					dataType: "json",
					async:false, 
				    success: function(data){
				    	if(data.rows.length ==0 ){
				    		result = false;
				    	}
					}
				}); 
			return result;
		},// 合同 tab 页是否编辑
		contractTabIsEdit:function(projectId){
			var result = true;
			var url = BASE_PATH+'creditsContractController/getContractListUrl.action?projectId='+projectId+'&contractCatelogKey=-1';
			 $.ajax({
					type: "POST",
			    	url : url,
					dataType: "json",
					async:false, 
				    success: function(data){
				    	if(data.rows.length ==0 ){
				    		result = false;
				    	}
					}
				}); 
			 return result;
		}, //自动计算出抵抵押率
	 	 mortgageValueInfo:function(){
	 		var assessValue = $('#assessValue').numberbox('getValue')//评估净值（元）
	 		var loanAmt = 	$('#loanAmtId').numberbox('getValue');//尽职调查金额 
	 		 if(assessValue==""){
	 			 return;
	 		 }
	 		 if(loanAmt==""){
	 			return;
	 		 }
	 		
	 		$('#mortgageRate').numberbox('setValue',loanAmt/assessValue*100);
	 		//$("#mortgageRate").numberbox("readonly",true);
	 	},//根据放款日期判断自动给每期还款日赋值
	 	repayDateInfo:function(outloanDt){
	 		//计算每期还款日
	 		var val = $('input:radio[name="eachissueOption"]:checked').val();
	 		if (val == 1) {
	 			$("#repayDate").numberbox("readonly",false);
	 		} else {
	 			var repayDateInfo = dateInfo(outloanDt).getDate() - 1;
	 			if (repayDateInfo == 0) {
	 				$("#repayDate").numberbox('setValue', 1);
	 			} else {
	 				$("#repayDate").numberbox('setValue', repayDateInfo);// 按实际放款日对日还款
	 			}
	 			$("#repayDate").numberbox("readonly"); 
	 		}

	 	},// 加载组织召开贷审会信息
	 	loadOrganizationInfo:function(projectId){
	 		if(projectId!=-1){
				$.ajax({
					url:BASE_PATH+"secondBeforeLoanController/getOrganizationCommission.action",
					type:"get",
					data:{"projectId":projectId},
					dataType:"json",
					success:function(response){
						if(response!=null || response!=''){
							$('#resMeetingId').val(response.pid);
							$('#userPids').val(response.meetingMenberAllUserId);
							$('#realNameText').textbox('setValue',response.meetingMenberUser);
							if(response.meetingMenberAllUserId!=""){
								// 更新状态为 1 ,流程提交时允许通过
								applyCommon.organization =1;
							}
						}
					}
				})
			}
	 	},// 加载审批官审批信息
	 	loadApproveScrutiny:function(projectId){
	 		if(projectId!=-1){
				$.post(BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action?projectId="+projectId,
					function(ret){
						if(ret.surveyResult!="" && ret.surveyResult!=null){
							$("#approveForm").form('load',ret);
							applyCommon.approveScrutiny = 1;
						}
					}, "json");
			}
	 	},
	 	// 验证担保方式
	 	validateAssWay:function(){

			// 判断选中的担保方式是否录入数据,如果没有录入数据,就要提示
	 		// 获取担保方式的所有文本信息
			var assWayText = $("#assWay").combobox("getText");
			// 如果是初始化,没有选中就直接退出
			if(assWayText == ""){
				$.messager.alert('操作提示',"请选择担保方式!",'error');
				return false;
			}
			// 劈开选中的担保方式
			var assWayArr = assWayText.split(",");
			// 循环判断选取的担保信息 
			for (var i = 0; i < assWayArr.length; i++) {
				if(assWayArr[i] == "抵押"){
					// 判断抵押列表是否有数据 
					var rows = $("#dyxx_grid").datagrid('getRows');
					// 判断是否为0,如果没有数据,就提示添加数据
					if(rows.length == 0){
						$.messager.alert('操作提示',"您选择了抵押,请录入相对应的抵押数据!",'error');
						return false;
					}
				}else if(assWayArr[i] == "质押"){
					// 判断质押列表是否有数据
					var rows = $("#zyxx_grid").datagrid('getRows');
					// 判断是否为0,如果没有数据,就提示添加数据
					if(rows.length == 0){
						$.messager.alert('操作提示',"您选择了质押,请录入相对应的质押数据!",'error');
						return false;
					}
				}else if(assWayArr[i] == "保证"){
					// 判断个人或者法人列表是否有数据
					var per_rows = $("#perGuarantee_grid").datagrid('getRows');
					var cor_rows = $("#corGuarantee_grid").datagrid('getRows');
					// 判断是否为0,如果没有数据,就提示添加数据
					if(per_rows.length == 0 && cor_rows.length == 0){
						$.messager.alert('操作提示',"您选择了保证,请录入相对应的个人保证或法人保证数据!",'error');
						return false;
					}
				}
			}
			// 赋值担保方式文本
			$("#investigationForm input[name='assWayText']").val(assWayText);
			return true;
	 	}
		
}

