//重置
function resetss(){
	$(".ecoTrade").hide();	
}

var loanExtensionList = {
			// 数据查询
			searchData:function(){
				$('#grid').datagrid('load',{
					projectName:$.trim($('#projectName').textbox('getValue')),
					projectNum:$.trim($('#projectNum').textbox('getValue')),
					cusName:''+$.trim($('#cusName').textbox('getValue')),
					cusType:$('#cusType').combobox('getValue'),
					requestStatus:$('#requestStatus').combobox('getValue'),
					ecoTrade:$('#ecoTrade').combobox('getValue'),
					requestDttm:''+$('#requestDttm').datebox('getValue'),
					requestDttmLast:''+$('#requestDttmLast').datebox('getValue')
				});
			},
			openNewTab:function(extensionProjectId,projectId){
				var url = BASE_PATH+"loanExtensionController/toEditLoanExtensionApply.action?projectId="+extensionProjectId+"&newProjectId="+projectId;
				//根据流程定义Key和引用ID（引用ID可以是利率ID、项目Id ...等等） 查询当前任务
				var datas = getTaskVoByWPDefKeyAndRefId("extensionRequestProcess",projectId);
				if(datas){
					url+="&"+datas;
				}
				parent.openNewTab(url,"展期申请编辑",true);
			},
			// 项目删除
			removeExtensionPorject:function(pid,projectId){
			 	$.messager.confirm("操作提示","确定删除选择的展期申请吗?",
					function(r) {
			 			if(r){
							$.post("removeExtensionProject.action",{pid : pid,projectId:projectId}, 
								function(ret) {
									if(ret && ret.header["success"]){
										$.messager.alert('操作提示',"删除成功!",'info');
										$("#grid").datagrid('reload');
									}else{
										$.messager.alert('操作提示',ret.header["msg"],'error');	
									}
								},'json');
			 			}
					}
			 	);
			},
			// 批量删除
			batchRemove:function(){
				 var rows = $('#grid').datagrid('getSelections');
			 	if ( rows.length == 0 ) {
			 		$.messager.alert("操作提示","请选择数据","error");
					return;
				}// 获取选中的系统用户名 
			 	var pId = "";
				var projectId = "";
				for(var i=0;i<rows.length;i++){
					if(1 != rows[i].requestStatus){
						$.messager.alert("操作提示","只能删除处理状态为 '申请中' 的展期申请!","error");
						return;
					}
					
			  		if(i==0){
			  			pId+=rows[i].pid;
			  			projectId+=rows[i].projectId;
			  		}else{
			  			pId+=","+rows[i].pid;
			  			projectId+=","+rows[i].projectId;
			  		}
			  	}
				loanExtensionList.removeExtensionPorject(pId,projectId);
			},
			// 项目名称format
			formatProjectName:function(value, row, index){
				var va="<a href='javascript:void(0)' onclick = 'loanExtensionList.searchProjectDeil("+JSON.stringify(row)+")'> <font color='blue'>"+row.projectName+"</font></a>"
				return va;
			},
			// 查看项目详情
			searchProjectDeil:function(row){
				var url = BASE_PATH+"loanExtensionController/toEditLoanExtensionApply.action?projectId="+row.extensionProjectId+"&newProjectId="+row.projectId+"&biaoZhi=3"
				parent.openNewTab(url,"展期申请详情",true);
			},
			// 操作format
			formatOperation:function(value, row, index){
				// 只要申请中的展期 才能编辑删除
				if( 1 != row.requestStatus){
					return "<font color='grey'>编辑| 删除</font>";
				}
				var edit="<a href='javascript:loanExtensionList.openNewTab("+row.extensionProjectId+','+row.projectId+")'><font color='blue'>编辑</font></a>";
				var remove = "|<a href='javascript:void(0)' onclick='loanExtensionList.removeExtensionPorject("+row.pid+','+row.projectId+")'><font color='blue'> 删除</font></a>";
				return edit+remove;
			},
			// 客户类型format
			formatCusType:function(value, row, index){
				if (1==row.cusType) {
					return "个人";
				}
				if (2==row.cusType) {
					return "企业";
				}else{
					return "";
				}
			}
	}