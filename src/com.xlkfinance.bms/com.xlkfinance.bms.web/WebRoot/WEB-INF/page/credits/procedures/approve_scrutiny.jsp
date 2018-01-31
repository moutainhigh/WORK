<%@ page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
	
	<div  class="easyui-panel control_disappr" data-options="collapsible:true" style="padding:10px" title="审批官审批信息" style="width:auto">
		<!--图标按钮 -->
		<div id="fkqlstjToolbar" class="easyui-panel" style="border-bottom: 0;">
			<!-- 操作按钮 -->
			<div style="border-bottom: 5px;padding:10px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="openItemAddSpxx(1)">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="openItemEditSpxx(1)">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton toReady" iconCls="icon-edit"  onclick="confirmMakeLoan('#fkqlstjGrid')">条件确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton toReady" iconCls="icon-edit"  onclick="cancelMakeLoan('#fkqlstjGrid')">取消确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeItem('#fkqlstjGrid','#fkqlstjGrid')">删除</a>
			</div>
		</div>
		
		<div class="dksqListDiv" style="height:auto;width: auto;">
			<table id="fkqlstjGrid" disabled="true" title="放款前落实条件列表" class="easyui-datagrid" 
				style="height:auto;width: auto;"
				data-options="
				    url: '',
				    method: 'get',
				    rownumbers: true,
				    singleSelect: false,
				    fitColumns:false,
				    toolbar: '#fkqlstjToolbar',
				    idField: 'pid'">
				<!-- 表头 -->
				<thead><tr>
				    <th data-options="field:'pid',checkbox:true" ></th>
				    <th data-options="field:'infoContent',align:'center'"  >落实条件</th>
				    <th data-options="field:'isConfirm',align:'center',formatter:isConfirmfilter"  >是否确认</th>
				</tr></thead>
			</table>
		</div>
		<p />
		<!--图标按钮 -->
		<div id="dhglyqToolbar" class="easyui-panel" style="border-bottom: 0;">
			<!-- 操作按钮 -->
			<div style="border-bottom: 5px;padding:10px">
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add"  onclick="openItemAddSpxx(2)">新增</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="openItemEditSpxx(2)">编辑</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="confirmMakeLoan('#dhglyqGrid')">条件确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit"  onclick="cancelMakeLoan('#dhglyqGrid')">取消确认</a>
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeItem('#dhglyqGrid','#dhglyqGrid')">删除</a>
			</div>
		</div>
		<div class="dksqListDiv" style="height:auto;width: auto;">
			<table id="dhglyqGrid" title="贷后管理要求列表" class="easyui-datagrid" 
				style="height:auto;width: auto;"
				data-options="
				    url: '',
				    method: 'get',
				    rownumbers: true,
				    singleSelect: false, 
				    fitColumns:false,
				    toolbar: '#dhglyqToolbar',
				    idField: 'pid'">
				<!-- 表头 -->
				<thead><tr>
				    <th data-options="field:'pid',checkbox:true" ></th>
				    <th data-options="field:'infoContent',align:'center'"  >管理要求</th>
				    <th data-options="field:'isConfirm',align:'center',formatter:isConfirmfilter"  >是否确认</th>
				</tr></thead>
			</table>
		</div>
		<div>
			<form action="<%=basePath%>secondBeforeLoanController/addProcedures.action" id="approveForm" name="approveForm" method="post" >
				<input name="pid" type="hidden" />
				<table class="beforeloanTable" style="padding-left: 50px;line-height: 35px;">
					<tr>
						<td class="label_right">是否可提前还款：</td>
						<td>
							<input name="isAllowPrepay" type="radio" value="1" checked="checked" />可
		           			<input name="isAllowPrepay" type="radio" value="0" />不可
						</td>
					</tr>
					<tr>
						<td class="label_right">是否退还利息：</td>
						<td>
							<input name="isReturnInterest" type="radio" value="1" checked="checked" />可
		           			<input name="isReturnInterest" type="radio" value="0" />不可
						</td>
					</tr>
					<tr>
						<td colspan="2" >项目调查结论-负责人意见：</td>
					</tr>
					<tr>
						<td colspan="2" >
							<input name="surveyResult" class="easyui-textbox" style="width:400px;height:60px" data-options="multiline:true" required="true">
						</td>
					</tr>
				</table>
			</form>
			<div style="padding-bottom: 20px;padding-top: 10px;">
				<a href="#" class="easyui-linkbutton" style="margin-left: 25%;" iconCls="icon-save" onclick="spgscSave()">保存</a>
			</div>
		</div>
		
		<!-- 保存 和取消按钮 -->
		<div id="dlg_spgsc_but">		
		    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveApprove()">保存</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg_spgsc').dialog('close')">关闭</a>
		</div>
		<div id="dlg_spgsc" class="easyui-dialog" style="width:550px;height:180px;padding:20px 20px" 
			closed="true" buttons="#dlg_spgsc_but" data-options="modal:true">
	     	<form id="fm_spgsc" method="post">
	     		<div class="fitem" id="update">
	     			<div>
	             	<label style="text-align:right;" class="spgsc_label"><font color="red">*</font>审批信息：</label>
	             	<input name="infoContent" class="easyui-textbox" style="width:320px;height:40px" required="true" data-options="multiline:true" missingMessage="请录入审批信息!" />
	             	</div>
	             	<input name="pid" type="hidden" value="-1" />
	             	<input name="infoType" type="hidden" />
	             	<input name="projectId" type="hidden" />
	             	<input name="status" value="1" type="hidden" />
	     	  	</div>
	    	</form>
		</div>
	</div>
	
<script type="text/javascript">
	
	$(function(){
		// 加载数据的路径
		var urlLS = "<%=basePath%>secondBeforeLoanController/getProjectApprovalLs.action?projectId="+newProjectId;
		var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId="+newProjectId;
		var urlJl = "<%=basePath%>beforeLoanController/getLoanProjectByProjectId.action?projectId="+newProjectId;
		// 加载数据
		$('#fkqlstjGrid').datagrid('options').url = urlLS;
		$('#fkqlstjGrid').datagrid('reload');
		$('#dhglyqGrid').datagrid('options').url = urlGL;
		$('#dhglyqGrid').datagrid('reload');
		
		applyCommon.loadApproveScrutiny(newProjectId);
		
		if("task_ApprovalOfficerReview"==workflowTaskDefKey || "task_OfflineMeetingResult"==workflowTaskDefKey){
		}else if("task_ConfirmSingleLoanApproval"==workflowTaskDefKey){
			$('.control_disappr .easyui-combobox').attr('disabled','true');
			$('.control_disappr input[type="checkbox"]').attr('disabled','disabled');
			$('.control_disappr .easyui-textbox').attr('disabled','true');
			$('.control_disappr input:not(.toReady)').attr('readOnly','readOnly');
			$('#include_approve_scrutiny .easyui-linkbutton:not(.toReady)').removeAttr('onclick');
			$('#include_approve_scrutiny .easyui-linkbutton:not(.toReady),.control_disappr input ,.control_disappr textarea').attr('disabled','true');
			$('#include_approve_scrutiny .easyui-linkbutton:not(.toReady),.control_disappr input ,.control_disappr textarea').attr('readOnly','readOnly');
			$('#include_approve_scrutiny .easyui-linkbutton:not(.toReady),.control_disappr input ,.control_disappr textarea').addClass('l-btn-disabled');
		}else{
			$('.control_disappr .easyui-combobox').attr('disabled','true');
			$('.control_disappr input[type="checkbox"]').attr('disabled','disabled');
			$('.control_disappr .easyui-textbox').attr('disabled','true');
			$('.control_disappr input').attr('readOnly','readOnly');
			$('#include_approve_scrutiny .easyui-linkbutton').removeAttr('onclick');
			$('#include_approve_scrutiny .easyui-linkbutton ,.control_disappr input,.control_disappr textarea').attr('disabled','true');
			$('#include_approve_scrutiny .easyui-linkbutton ,.control_disappr input,.control_disappr textarea').attr('readOnly','readOnly');
			$('#include_approve_scrutiny .easyui-linkbutton ,.control_disappr input,.control_disappr textarea').addClass('l-btn-disabled');
		
		}
	});
	
	// 新增审批官信息
	function openItemAddSpxx(infoType){
		$("#fm_spgsc").form('reset');
		// 赋值
		$("#fm_spgsc input[name='infoType']").val(infoType);
		$("#fm_spgsc input[name='projectId']").val(newProjectId);
		$("#fm_spgsc input[name='pid']").val(-1);
		// 打开新增窗口 
		if(infoType==1){
			$('#dlg_spgsc').dialog('open').dialog('setTitle', "新增放款前落实条件");
			$(".spgsc_label").text("落实条件：");
		}else{
			$('#dlg_spgsc').dialog('open').dialog('setTitle', "新增贷后管理要求");
			$(".spgsc_label").text("管理要求：");
		}
	}
	
	// 编辑审批官信息
	function openItemEditSpxx(infoType){
		$("#fm_spgsc").form('reset');
		// 根据不同的类型获取当前选中的数据
		row = "";
		if( infoType == 1){
			// 如果是放款前落实条件列表
			rows = $("#fkqlstjGrid").datagrid("getSelections");
			$(".spgsc_label").text("落实条件：");
			
		}else{
			// 如果是贷后管理要求列表
			rows = $("#dhglyqGrid").datagrid("getSelections");
			$(".spgsc_label").text("管理要求：");
		}
		// 判断是否选取数据
		if(rows.length == 1){
			// 赋值
			$("#fm_spgsc").form('load', rows[0]);
			if( infoType == 1){
				$('#dlg_spgsc').dialog('open').dialog('setTitle', "编辑放款前落实条件");
			}else{
				$('#dlg_spgsc').dialog('open').dialog('setTitle', "编辑贷后管理要求");
			}
		}else if(rows.length > 1){
			$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
		}else{
			$.messager.alert("操作提示","请选择数据","error");
		}
	}
	
	// 保存数据到数据库
	function saveApprove(){
		var url = "<%=basePath%>secondBeforeLoanController/addProjectApprovalInfo.action";
		if($("#fm_spgsc input[name='pid']").val() != -1 ){
			url = "<%=basePath%>secondBeforeLoanController/updateProjectApprovalInfo.action";
		}
		$("#fm_spgsc").form('submit', {
			url : url,
			success : function(result) {
				// 转换成json格式的对象
				var ret = eval("("+result+")");
				if(ret && ret.header["success"]){
					if($("#fm_spgsc input[name='pid']").val() != -1 ){
						if($("#fm_spgsc input[name='infoType']").val() == 1){
							$.messager.alert('操作提示',"修改落实条件成功!",'info');
						}else{
							$.messager.alert('操作提示',"修改管理要求成功!",'info');
						}
					}else{
						if($("#fm_spgsc input[name='infoType']").val() == 1){
							$.messager.alert('操作提示',"新增落实条件成功!",'info');
						}else{
							$.messager.alert('操作提示',"新增管理要求成功!",'info');
						}
					}
					$("#dlg_spgsc").dialog('close');
					// 加载数据的路径
					var urlLS = "<%=basePath%>secondBeforeLoanController/getProjectApprovalLs.action?projectId="+newProjectId;
					var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId="+newProjectId;
					// 判断加载那一个 datagrid
					if($("#fm_spgsc input[name='infoType']").val() == 1){
						$('#fkqlstjGrid').datagrid('options').url = urlLS;
						$('#fkqlstjGrid').datagrid('reload');
						// 清空datagrid的选中数据
						clearSelectRows('#fkqlstjGrid');
					}else{
						$('#dhglyqGrid').datagrid('options').url = urlGL;
						$('#dhglyqGrid').datagrid('reload');
						// 清空datagrid的选中数据
						clearSelectRows('#dhglyqGrid');
					}
				}else{
					$.messager.alert('操作提示',ret.header["msg"],'info');	
				}
			}
		});
	}
	
	// 删除
	function removeItem(formId,datagridId){
		var rows = $(datagridId).datagrid('getSelections');
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
		var pids = "";
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		pids += row.pid+",";
	  	}
	 	var url = "<%=basePath%>secondBeforeLoanController/deleteProjectApprovalInfo.action";
	 	$.messager.confirm("操作提示","确定删除选择的此批数据吗?",
			function(r) {
	 			if(r){
					$.post(url,{pids : pids}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["code"] == 200){
								// 加载数据的路径
								var urlLS = "<%=basePath%>secondBeforeLoanController/getProjectApprovalLs.action?projectId="+newProjectId;
								var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId="+newProjectId;
								if(datagridId == "#fkqlstjGrid"){
									$(datagridId).datagrid('options').url = urlLS;
								}else{
									$(datagridId).datagrid('options').url = urlGL;
								}
								$(datagridId).datagrid('reload');
								// 清空datagrid的选中数据
								clearSelectRows(datagridId);
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
						},'json');
	 			}
			});
	}
	
	// 新增审批意见
	function spgscSave(){
		// 赋值
		$("#approveForm input[name='pid']").val(newProjectId);
		$("#approveForm").form('submit', {
			success : function(result) {
				// 转换成json格式的对象
				var ret = eval("("+result+")");
				if(ret && ret.header["success"]){
					$.messager.alert('操作提示',"新增审批意见成功!",'info');
					applyCommon.approveScrutiny = 1;
				}else{
					$.messager.alert('操作提示',ret.header["msg"],'info');	
				}
			}
		});
	}
	
	// 确认放款
	function confirmMakeLoan(datagridId){
		// 获取所有选中的数据
		var rows = $(datagridId).datagrid('getSelections');
		// 判断是否选中数据
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
		var pids = "";
		// 循环获取所选中的数据的PID,进行拼接
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		// 判断选中的数据中是否存在已经确认了数据,如果存在,就提醒
	  		if( row.isConfirm == 1 ){
	  			$.messager.alert("操作提示","选中的数据中有已经确认的,请重新选择!","error");
	  			// 调用清除选中数据的函数
				clearSelectRows(datagridId);
				return;
	  		}
	  		pids += row.pid+",";
	  	}
		// 更新路径
		var url = "<%=basePath%>secondBeforeLoanController/updateIsConfirmPrimaryKey.action";
		$.messager.confirm("操作提示","确定确认选择的数据吗?",
			function(r) {
 				if(r){
 					$.post(url,{pids : pids,isConfirm : 1}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["code"] == 200){
								// 加载数据的路径
								var urlLS = "<%=basePath%>secondBeforeLoanController/getProjectApprovalLs.action?projectId="+newProjectId;
								var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId="+newProjectId;
								if(datagridId == "#fkqlstjGrid"){
									$(datagridId).datagrid('options').url = urlLS;
								}else{
									$(datagridId).datagrid('options').url = urlGL;
								}
								$(datagridId).datagrid('reload');
								// 清空datagrid的选中数据
								clearSelectRows(datagridId);
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
						},'json')	
 				}
			}
		);
	}
	
	// 取消放款
	function cancelMakeLoan(datagridId){
		// 获取所有选中的数据
		var rows = $(datagridId).datagrid('getSelections');
		// 判断是否选中数据
	 	if ( rows.length == 0 ) {
	 		$.messager.alert("操作提示","请选择数据","error");
			return;
		}
		var pids = "";
		// 循环获取所选中的数据的PID,进行拼接
		for(var i=0;i<rows.length;i++){
	  		var row = rows[i];
	  		// 判断选中的数据中是否存在已经确认了数据,如果存在,就提醒
	  		if( row.isConfirm == -1 ){
	  			$.messager.alert("操作提示","选中的数据中有没有确认的,请重新选择!","error");
	  			// 调用清除选中数据的函数
				clearSelectRows(datagridId);
				return;
	  		}
	  		pids += row.pid+",";
	  	}
		// 更新路径
		var url = "<%=basePath%>secondBeforeLoanController/updateIsConfirmPrimaryKey.action";
		$.messager.confirm("操作提示","确定取消确认选择的数据吗?",
			function(r) {
 				if(r){
 					$.post(url,{pids : pids,isConfirm : -1}, 
						function(ret) {
							//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
							if(ret && ret.header["code"] == 200){
								// 加载数据的路径
								var urlLS = "<%=basePath%>secondBeforeLoanController/getProjectApprovalLs.action?projectId="+newProjectId;
								var urlGL = "<%=basePath%>secondBeforeLoanController/getProjectApprovalGl.action?projectId="+newProjectId;
								if(datagridId == "#fkqlstjGrid"){
									$(datagridId).datagrid('options').url = urlLS;
								}else{
									$(datagridId).datagrid('options').url = urlGL;
								}
								$(datagridId).datagrid('reload');
								// 清空datagrid的选中数据
								clearSelectRows(datagridId);
							}else{
								$.messager.alert('操作提示',ret.header["msg"],'error');	
							}
						},'json')	
 				}
			}
		);
	}
	
	// 格式化显示--是否确认
	function isConfirmfilter(value,row,index){
		if(value==1){
			return "是";
		}else{
			return "否";
		}
	}
</script>
