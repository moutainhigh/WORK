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
			applyCommon.getLoanProjectByProjectId(projectId);
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
		// 设置项目信息 
		setProjectInfo : function(projectId){
			$.post( BASE_PATH+'beforeLoanController/getProjectNameOrNumber.action?projectId='+projectId,
					function(data){
						// 重新设置项目名称和项目编号
						$("#projectName").val(data.projectName);
			  			$("#projectNumber").val(data.projectNumber);
				}, "json");
		},
		// 加载尽职调查报告
		loadSurveyReport:function(projectId){
			// 尽职调查报告URL			  			
			var urlSr = BASE_PATH+"secondBeforeLoanController/getSurveyReportByProjectId.action?projectId="+projectId;
			// 加载尽职调查报告的数据
			$.post(urlSr,
				function(ret){
					$("#specialDesc").textbox('setValue',ret.specialDesc);
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

