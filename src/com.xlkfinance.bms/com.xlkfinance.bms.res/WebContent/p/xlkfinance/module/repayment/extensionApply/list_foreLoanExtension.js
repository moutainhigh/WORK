var loanExtension = {
			init:function(){
				$(".ecoTrade").hide();
			},
			openNewTab:function(rowData){
				// 根据项目ID判断是否有流程在执行
				if(isWorkflowByPid(rowData.projectId)){
					return;
				}
				parent.openNewTab(BASE_PATH+"foreLoanExtensionController/toLoanExtensionApply.action?projectId="+rowData.projectId+"&projectType="+rowData.projectType,"展期申请",true);
			},
			isWorkflowByPid:function(projectId){
				var bool = true;
				$.ajax({
					type: "POST",
			    	url : BASE_PATH+"loanExtensionController/isWorkflowByStatus.action?projectId="+projectId,
					async:false, 
				    success: function(result){
				    	if(result != 0){
				    		$.messager.alert('操作提示',"该项目已有展期流程在执行!",'error');
				    	}else{
				    		bool = false;
				    	}
					}
				});
				return bool;
			},
			// 操作类型
			datiloperat:function(value, rowData, index) {
				var projectDom = "<a href='javascript:void(0)' onclick='loanExtension.openNewTab("+JSON.stringify(rowData)+")'><font color='blue'>展期申请</font></a>";
				return projectDom;
			},
			// 查询点击事件
			loadList:function () {
				$('#grid').datagrid('load',{
					projectName:$.trim($('#projectName').textbox('getValue')),
					projectNum:$.trim($('#projectNum').textbox('getValue')),
					requestDttm:$('#requestDttm').datebox('getValue'),
					requestDttmLast:$('#requestDttmLast').datebox('getValue')
				});
			}, 
			// 项目名称format
			formatProjectName:function (value, rowData, index) {
				var btn = "<a id='"+rowData.projectId+"' class='easyui-linkbutton' onclick='loanExtension.disposeClick("+JSON.stringify(rowData)+")' href='#'>" +
	 			"<span style='color:blue; '>"+rowData.projectName+"</span></a>";  
				return btn;   
			} 
			// 贷款申请详情
			,disposeClick:function (row) {
				var url = "";
				var titleName = "";
				if(row.projectType == 2 || row.projectType == 5|| row.projectType == 6){
					url = BASE_PATH+"beforeLoanController/addNavigation.action?biaoZhi=3&projectId="+row.projectId+"&param='projectId':'"+row.projectId+"','projectName':1";
					titleName = "贷款申请详情";
				}else if (row.projectType == 3){
					var oldProjectId = -1;
					$.ajax({
						type: "POST",
				    	url : BASE_PATH+"creditsController/listLoanProjectUrl.action?rows=10&page=1&projectNumber="+row.projectNum,
						async:false, 
					    success: function(data){
					    	var str = eval("("+data+")");
					    	oldProjectId = str.rows[0].oldProjectId;
						}
					});
					url = BASE_PATH+"creditsCustomerInfoController/toAddCredit.action?projectId="+oldProjectId+"&newProjectId="+row.projectId+"&biaoZhi=3"
					titleName = "额度提取申请详情";
				}else if (row.projectType == 4){
					// 展期申请需要查询被展期的项目ID
					var oldProjectId = -1;
					$.ajax({
						type: "POST",
				    	url : BASE_PATH+"foreLoanExtensionController/getProjectExtension.action?projectId="+row.projectId,
						async:false, 
					    success: function(data){
					    	var str = eval("("+data+")");
					    	oldProjectId = str.extensionProjectId;
						}
					});
					url = BASE_PATH+"foreLoanExtensionController/toEditLoanExtensionApply.action?projectId="+oldProjectId+"&newProjectId="+row.projectId+"&biaoZhi=3"
					titleName = "展期申请详情";
				}
				parent.openNewTab(url,titleName,true);
			} 
	}
	
	//重置
	function resetss(){
		
	}