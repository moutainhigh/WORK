var creditsCommon =  {
	 init:function (projectId,newProjectId,projectName,biaoZhi){
		// 老项目的抵质押物信息是不能修改的
		 applyCommon.dyxx_gridOnLoadSuccess(projectId);
		// 老项目的个人保证信息是不能修改的
		 applyCommon.perGuarantee_gridOnLoadSuccess(projectId);
		// 老项目的企业法人保证信息是不能修改的
		 applyCommon.corGuarantee_gridOnLoadSuccess(projectId);
		// 老项目的质押押物信息是不能修改的
		 applyCommon.dyxx_grid_checkOnLoadSuccess (projectId);
		//初始化根据url加载数据 判断赋值之类
		 creditsCommon.loadInit(newProjectId,projectId);
		//显示客户个人或者企业
		 creditsCommon.mcusTypeHtml();
		//权限置灰色  
		 creditsCommon.projectNameAddClass(projectName);
		 creditsCommon.biaoZhiAddClass(biaoZhi);
	 }, 
	// 保存基本信息
	saveInvestigation : function (){
		
		var p=$("#oldProject_Id").val();
		//debugger;
		//$(".old_Project_Id").attr("value",p);
			$("#old_Project_Id").val(p);
		var amts = $("#aAmts").val();
		if(amts!=null && amts!=0){
			var loanAmtId = $('#loanAmtId').numberbox('getValue');
			if(Number(loanAmtId)>Number(amts)){
				$.messager.alert('操作提示',"提取金额超过了可用额度!",'error');	
				return;
			}
		}else{
			$.messager.alert('操作提示',"可用额度为【0】,不能进行提款!",'error');	
			return;
		}
		
 		// 判断项目名称
 		if($("#projectName").val()==null || $("#projectName").val()==""){
 			$.messager.alert('操作提示',"项目名称不允许为空!",'error');	
 			return;
 		}
 		// 获取所选担保方式
 		var assWay = $("#assWay").combobox("getValues");
 		// 判断是否选择担保方式
 		//debugger;
 		if( assWay == "" ){
 			$.messager.alert('操作提示',"请选择担保方式!",'error');	
 			return;
 		}
 		// 赋值担保方式
 		$("#investigationForm input[name='assWay']").val(assWay);
 		
 		
 		// 获取授信额度和贷款金额
 		var loanAmt = $("#investigationForm input[name='loanAmt']").val();
 		var creditAmt = $("#investigationForm input[name='creditAmt']").val();
 		//获取还款期限
 		var repayCycle = $("#investigationForm input[name='repayCycle']").val();
 		//授信额度录入：限制最高5000万
 	   if(creditAmt > 50000001){
 		   $.messager.alert('操作提示',"授信额度不能大于5000万!",'error');	
 	    	return;
 	   }
 		   
 		//贷款金额：限制最高5000万
 	   if(loanAmt > 10000001){
 		   $.messager.alert('操作提示',"贷款金额不能大于1000万!",'error');	
 	    	return;
 	   }
 	   
 		//还款期限：最高36期
 	   if(repayCycle > 36){
 		   $.messager.alert('操作提示',"还款期限不能大于36期!",'error');	
 	    	return;
 	   }
 		
 		// 验证担保方式
		if(!applyCommon.validateAssWay()){
			return ;
		}

 		$("#investigationForm input[name='projectType']").val(1);
 		// 赋值贷款ID
 		$("#investigationForm input[name='loanId']").val(loanId);
 		// 赋值授信ID
 		$("#investigationForm input[name='creditId']").val(creditId);
 		// 赋值项目ID
 		$("#investigationForm input[name='pid']").val(newProjectId);
 		// 赋值客户类型
 		$("#investigationForm input[name='acctId']").val(acct_id);
 		// 判断还款期限是否发生改变
 		if($("#hkqx").val() != $("#investigationForm input[name='repayCycle']").val()){
 			$("#investigationForm input[name='judgeRepayCycle']").val(1);
 		}else{
 			$("#investigationForm input[name='judgeRepayCycle']").val(-1);
 		}
 		
 		//debugger;
 		// 验证其他还款的确认方法
 		if($('#repayFun').combobox('getText') == "其他还款方式"){
 			$.messager.confirm("操作提示","在其他还款方式下，会清空原先录入的还款计划表数据，请确认是否继续保存？",
				function(r) {
					if(r){
						// 判断抵质押物存在数据,但是项目担保方式没有选择此类型
						checkMortgageType($("#assWay").combobox("getText"));
					}
			});
 		}else{
 			// 判断抵质押物存在数据,但是项目担保方式没有选择此类型
			checkMortgageType($("#assWay").combobox("getText"));
 		}
	},
	// 老项目的抵质押物信息是不能修改的
	dyxx_gridOnLoadSuccess : function (projectId){
		$("#dyxx_grid").datagrid({
			onLoadSuccess : function(data){
				 if (data.rows.length > 0) {
		         	// 循环判断操作为新增的不能选择
		            for (var i = 0; i < data.rows.length; i++) {
		            	// 根据老项目ID让某些行不可选
		                if (data.rows[i].projectId == projectId ) {
		                	$('#dyxxDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
		                	$('#dyxxDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
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
		         	// 循环判断操作为新增的不能选择
		            for (var i = 0; i < data.rows.length; i++) {
		            	// 根据老项目ID让某些行不可选
		                if (data.rows[i].projectId == projectId ) {
		                	$('#zyxxDiv .datagrid-header input[type="checkbox"]').attr('disabled',true);
		                	$('#zyxxDiv tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
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
		         	// 循环判断操作为新增的不能选择
		            for (var i = 0; i < data.rows.length; i++) {
		            	// 根据老项目ID让某些行不可选
		                if (data.rows[i].projectId == projectId ) {
		                	$('#personalGuarantee_div .datagrid-header input[type="checkbox"]').attr('disabled',true);
		                	$('#personalGuarantee_div tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
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
	// 老项目的企业法人保证信息是不能修改的
	corGuarantee_gridOnLoadSuccess : function (projectId){
		$("#corGuarantee_grid").datagrid({
			onLoadSuccess : function(data){
				 if (data.rows.length > 0) {
		         	// 循环判断操作为新增的不能选择
		            for (var i = 0; i < data.rows.length; i++) {
		            	// 根据老项目ID让某些行不可选
		                if (data.rows[i].projectId == projectId ) {
		                	$('#corporationGuarantee_div .datagrid-header input[type="checkbox"]').attr('disabled',true);
		                	$('#corporationGuarantee_div tr[datagrid-row-index="'+i+'"] input[type="checkbox"]').attr('disabled',true);
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
	//置灰
	biaoZhiAddClass : function (biaoZhi){
		if(biaoZhi==3){
			$('#tabs .panel-body .easyui-linkbutton ,#tabs .panel-body input,#tabs .panel-body textarea').attr('disabled','disabled');
			$('#tabs .panel-body .easyui-linkbutton ,#tabs .panel-body input,#tabs .panel-body textarea').attr('readOnly','readOnly');
			$('#tabs .panel-body .easyui-linkbutton ,#tabs .panel-body input,#tabs .panel-body textarea').addClass('l-btn-disabled');
			//$('.loanworkflowWrapDiv').addClass('none');
		}
	},
	//权限置灰色
	projectNameAddClass : function (projectName){
		if(projectName){
			$('#investigationForm .easyui-linkbutton ,#investigationForm input,#investigationForm textarea').attr('disabled','disabled');
			$('#investigationForm .easyui-linkbutton ,#investigationForm input,#investigationForm textarea').attr('readOnly','readOnly');
			$('#investigationForm .easyui-linkbutton ,#investigationForm input,#investigationForm textarea').addClass('l-btn-disabled');

			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('disabled','disabled');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').attr('readOnly','readOnly');
			$('.panel-body .easyui-linkbutton ,.panel-body input,.panel-body textarea').addClass('l-btn-disabled');
	 	}
	},
	//显示客户个人或者企业 
	mcusTypeHtml : function (){
		var mcusType=$("input[name='cusType']:checked").val();
		if(mcusType==1){   
			$("#introductionId").html("个人简介：");
			$("#projectSource").html("个人主要财务状况：");
		}
		if(mcusType==2){
			$("#introductionId").html("企业简介：");
			$("#projectSource").html("企业经营及财务状况：");
		}

	},
	//初始化根据url加载数据 判断赋值之类
	loadInit : function (newProjectId,projectId){
		// 作为新项目的loanId,默认为-1假设不存在
		var judge = -1;
		// 临时项目ID -- 初始值等于老项目ID
		var temporaryProjectId = projectId;
		// 根据新项目ID查询新项目是否存在贷款ID
		judge = applyCommon.getLoanId(newProjectId);
		// 判断新项目是否存在贷款ID
		if(judge != -1 ){
			// 如果存在,把新项目的ID赋值到临时项目ID上面
			temporaryProjectId = newProjectId;
		}

		var url = BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action";
		$.post(url,{projectId : temporaryProjectId},
		  	function(data){
				// 显示 or 隐藏抵质押物担保
				setTimeout("InitializationMortgage()",1);
				// 判断是否存在当前项目的贷款ID
	  			if(data.isHoop==1){//是否循环项是否隐藏
	  				$(".selectlist1").css("display",'block');
	  				$("input[name='isHoop'][value='1']").attr("checked",true);
	  			}else{
	  				$(".selectlist1").css("display",'none');
	  				$("input[name='isHoop'][value='2']").attr("checked",true);
	  				$("#circulateType").val("");
	  			}
				//如果不是旧项目Id，再重新根据老项目id单独给*授信额度：*授信开始日期：*授信结束日期：*是否循环 赋值
	  			if(temporaryProjectId!=projectId){
			    	applyCommon.loadCreditsInfo(projectId);
	  			}
	  			if(data.eachissueOption==2){//2为按实际放款日对日还款  置灰不让操作 
	  				$("#repayDate").numberbox("readonly"); 
	  			}
	  			if(data.repayOption==3){//还款选项：2个都选中默认为3
	  				data.repayOption = 2;
	  				data.repayOptionTest = 4;
				}else if(data.repayOption==4){
					data.repayOptionTest = 4;//
				}else if(data.repayOption==2){
					data.repayOption = 2;//
				}
	  			// 设置合计费利率
	  			$("#total").numberbox('setValue', parseFloat(data.monthLoanInterest+data.monthLoanMgr+data.monthLoanOtherFee) ); //合计利率
	  			var loanInterestRecord = data.loanInterestRecord;
	  			var loanMgrRecord = data.loanMgrRecord;
				var loanOtherFee =data.loanOtherFee;
				$("#investigationForm").form('load',data);
				
				// 等额本息、等本等息 禁用前置付息、一次性付息复选框
				if(data.repayFun == 13317 || data.repayFun == 13318){
					$("#repayOption").attr("checked",false);
					$("#repayOption").attr("disabled",'disabled');
					$("#repayOptionTest").attr("checked",false);
					$("#repayOptionTest").attr("disabled",'disabled');
				}
				$("#realName").combobox('select',data.pmUserId);
				// 赋值还款期限
				$("#hkqx").val(data.repayCycle);
				// 动态加载银行帐号出来
				applyCommon.loadOtherFee(loanOtherFee);
				applyCommon.loadInterestRecord(loanInterestRecord);
				applyCommon.loadMgrRecord(loanOtherFee);
				//*****如果新项目有值 就直接放老项目的值
				var url = BASE_PATH+"beforeLoanController/getLoanProjectByProjectId.action";
				$.post(url,{projectId : newProjectId},
				  	function(data){
					if(data.loanAmtId!=0){
						$("#loanAmtId").numberbox('setValue',data.loanAmt);
					}else{
						$("#loanAmtId").numberbox('setValue',0);
					}
				}, "json");
				 
				//****
				if(judge == -1){
					// 根据上面的判断如果是老项目的话,就需要把新的项目名称和项目编码重新赋值重新赋值
					applyCommon.setProjectInfo(newProjectId);
					// 赋值项目ID
					$("#surveyReport input[name='projectId']").val(newProjectId);
				}
				
		 		// 动态绑定担保方式
				applyCommon.bingGuaranteeType(projectId,newProjectId,judge);
				// 尽职调查报告URL
				applyCommon.loadSurveyReport(temporaryProjectId);
				// 如果下拉框,数字狂的值是0 默认不显示 zhengxiu
				applyCommon.initInput();
		  	}, "json");
	},
	//隐藏抵押质押
	hideDivAddClass : function (){
	    	// 隐藏抵押
	    	$("#mortgageNews").addClass('none');
	    	// 隐藏质押
	    	$("#pledgeNews").addClass('none');
	}
	
	


	

	

  
	

 
}