var loanExtension = {
			init:function(){
				// 客户类别与企业经济类别初始化
				$('#cusType').combobox({
					onSelect: function(row){
						if(row.value==2){
							$(".ecoTrade").show();
						}else{
							$(".ecoTrade").hide();
						}
					}
				});
				$(".ecoTrade").hide();
			},
			openNewTab:function(rowData){
				// 根据项目ID判断是否有流程在执行
				if(isWorkflowByStatus(rowData.projectId)){
					return;
				}
				var result = true;
				
			   /* // 1 验证项目是否被其它申请提交任务
				$.ajax({
					  url: "<%=basePath%>workflowController/isAllowStartWorkflowByProjectId.action?projectId="+rowData.projectId,
					  async: false,
					  success: function(ret){ 
						  result = ret;
					  }
				});
				if(result == "false"){
					$.messager.alert("提示","此项目已有其它申请提交任务或者已经存在展期申请任务,请检查 !","info");
					return;
				}
				
				// 2 验证项目是否已经存在展期申请
				$.ajax({
					type: "POST",
			    	url : "<%=basePath%>loanExtensionController/isRequestingByExtensionProjectId.action?extensionProjectId="+rowData.projectId,
					async:false, 
				    success: function(data){
				    	result = data;
					}
				});
				if(result != 'false'){
					$.messager.alert('操作提示',"此项目已经存在展期申请,请检查!",'error');
					return ;
				} */
				
				// 3 验证项目本金金额是否已还清
				$.ajax({
					type: "POST",
			    	url : BASE_PATH+'loanExtensionController/getExtensionNum.action?projectId='+rowData.projectId,
					async:false, 
				    success: function(data){
				    	if(data.length == 2){
				    		result = false;
				    	}
					}
				});
				if(!result){
					$.messager.alert('操作提示',"此项目本金已全部还清,请检查!",'error');
					return ;
				}
				parent.openNewTab(BASE_PATH+"loanExtensionController/toLoanExtensionApply.action?projectId="+rowData.projectId+"&projectType="+rowData.projectType,"展期申请",true);
			},
			// 操作类型
			datiloperat:function(value, rowData, index) {
				var projectDom = "<a href='javascript:void(0)' onclick='loanExtension.openNewTab("+JSON.stringify(rowData)+")'><font color='blue'>展期申请</font></a>";
				return projectDom;
			}, 
			//客户类别format
			formatCusType:function (value, row, index){
				if (1==row.cusType) {
					return "个人";
				}
				if (2==row.cusType) {
					return "企业";
				}else{
					return "";
				}
			},
			// 查询点击事件
			loadList:function () {
				$('#grid').datagrid('load',{
					projectName:$.trim($('#projectName').textbox('getValue')),
					projectNum:$.trim($('#projectNum').textbox('getValue')),
					cusName:$.trim($('#cusName').textbox('getValue')),
					cusType:$('#cusType').combobox('getValue'),
					planRepayDt:$('#planRepayDt').datebox('getValue'),
					planRepayDtLast:$('#planRepayDtLast').datebox('getValue'),
					overdueStartDay:$('#overdueStartDay').textbox('getValue'),
					overdueEndDay:$('#overdueEndDay').textbox('getValue'),
					ecoTrade:$('#ecoTrade').combobox('getValue'),
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
				if(row.projectType == 2 || row.projectType == 5){
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
				    	url : BASE_PATH+"loanExtensionController/getProjectExtension.action?projectId="+row.projectId,
						async:false, 
					    success: function(data){
					    	var str = eval("("+data+")");
					    	oldProjectId = str.extensionProjectId;
						}
					});
					url = BASE_PATH+"loanExtensionController/toEditLoanExtensionApply.action?projectId="+oldProjectId+"&newProjectId="+row.projectId+"&biaoZhi=3"
					titleName = "展期申请详情";
				}
				parent.openNewTab(url,titleName,true);
			} 
	}
	
	//重置
	function resetss(){
		$(".ecoTrade").hide();	
	}