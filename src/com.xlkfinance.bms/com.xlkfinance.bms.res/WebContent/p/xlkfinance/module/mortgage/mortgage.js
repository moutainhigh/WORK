// 格式化抵质押物状态
function formatterMortgageStstus(val,row){
	// 先判断是否为空
	if(null == val || "" == val){
		return "";
	}else if(val == 1){
		return "等待办理登记";
	}else if(val == 2){
		return "已办理登记";
	}else if(val == 3){
		return "已保管";
	}else if(val == 4){
		return "提取申请中";
	}else if(val == 5){
		return "已提取";
	}else if(val == 6){
		return "已解除";
	}
}

/**
 * 抵质押物的提交操作
 * @param operationType 操作类型(1.保管处理 2.提取申请 3.提取处理 4.解除抵质押 5.办理抵质押登记)
 * @param formId Form表单的ID
 * @param dialogId 弹出窗口的ID
 */
function saveMortgageOperation(operationType ,formId , dialogId){
	// 提交的路径
	var url = "";
	// 根据不同的类型,执行不同的操作
	if(operationType == 1){
		url = BASE_PATH+"mortgageController/safekeepingProjectAssBase.action";
	}else if(operationType == 2){
		url = BASE_PATH+"mortgageController/applyExtractionProjectAssBase.action";
	}else if(operationType == 3){
		url = BASE_PATH+"mortgageController/applyManagetransactProjectAssBase.action";
	}else if(operationType == 4){
		url = BASE_PATH+"mortgageController/relieveProjectAssBase.action";
	}else if(operationType == 5){
		url = BASE_PATH+"mortgageController/transactProjectAssBase.action";
	}
	// 提交form表单
	$(formId).form('submit', {
			url : url,
			onSubmit : function() {return $(this).form('validate');},
			success : function(result) {
				var ret = eval("("+result+")");
 				if(ret && ret.header["success"]){
 					if(operationType == 2){
 						$("#tqsqForm input[name='extractionId']").val(ret.header["code"]);
 						$(dialogId).dialog('close');
 					}else{
 	 					// 关闭窗口
 	 					$(dialogId).dialog('close');
 					}
 					// 操作提示
 					$.messager.alert('操作提示',ret.header["msg"]);
 					// 重新加载抵质押物列表信息
 					$("#grid").datagrid('load');
 					// 清空所选择的行数据
 					clearSelectRows("#grid");
 				}else{
 					// 关闭窗口
 					$.messager.alert('操作提示',ret.header["msg"],'error');
 				}
			}
	});
}

// 打开抵质押办理窗口
function openTransact(){
	var row = $('#grid').datagrid('getSelections');
	// 判断选取的数量
 	if (row.length == 1) {
 		if(row[0].status != 1){
 			$.messager.alert("操作提示","只能对未办理的担保信息进行抵押登记!","error");
 			return;
 		} 
 		// 重置  and 赋值 form表单
 		$("#dzywxxForm_lc").form("reset");
 		$("#dzywxxForm_lc").form('load',row[0]);
 		// 拼接物件地点-- 省份和城市必填
 		var address = row[0].addressProvince+row[0].addressCity;
 		// 判断区是否选择,如果没选,不拼接进去
 		if(row[0].addressArea != "请选择"){
 			address += row[0].addressArea;
 		}
 		// 拼接物件地址的详细地点
 		address  += row[0].addressDetail;
 		$("#objectLocation_tqcl").val(address);
 		$('#dlg_dzywxx_lc').dialog('open').dialog('setTitle', "办理抵质押登记");
 		$('#baseId').val(+row[0].pid);
 		$('#pid_ass').val(+row[0].pid);
 		
 		// 初始化文件列表 bldj-datagrid
 		$('#bldj-datagrid').datagrid({
			url:"getProjectAssFile.action?baseId="+row[0].pid+"&fileTyle=1"
		}); 
	} else if(row.length > 1){
		$.messager.alert("操作提示","每次只能修改一条数据,请重新选择！","error");
	} else{
		$.messager.alert("操作提示","请选择数据","error");
	}
	
	
}

// 打开保管处理窗口
function openSafekeepingDispose(){
	// 获取选中的数据
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		/*// 判断选中的数据是否是已办理的,只能对已办理的数据进行保管处理
 		if(row[0].status != 2){
 			$.messager.alert("操作提示","只能对已办理的抵押登记物件进行保管处理!","error");
 			return;
 		}*/
 		// 重置from表单数据
 		$('#bgclForm').form('reset');
 		// 赋值
 		$('#bgclForm').form('load', row[0]);
 		// 格式化登记时间
 		$("#bgclForm input[name='regDt']").val(convertDate(row[0].regDt));
 		// 拼接物件地点-- 省份和城市必填
 		var address = row[0].addressProvince+row[0].addressCity;
 		// 判断区是否选择,如果没选,不拼接进去
 		if(row[0].addressArea != "请选择"){
 			address += row[0].addressArea;
 		}
 		// 拼接物件地址的详细地点
 		address  += row[0].addressDetail;
 		$("#objectLocation_tqcl").val(address);
 		// 打开窗口   dlg_bgcl
 		$('#dlg_bgcl').dialog('open').dialog('setTitle', "保管处理");
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能保管处理一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

// 打开提取申请窗口
function openApplyExtraction(){
	// 获取选中的数据
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		// 判断选中的数据是否是已保管的,只能对已保管的数据进行提取申请
 		if(row[0].status != 3){
 			$.messager.alert("操作提示","只能对已保管的抵押登记物件进行提取申请!","error");
 			return;
 		}
 		// 重置from表单数据
 		$('#tqsqForm').form('reset');
 		// 调用函数赋值抵质押物信息和保管信息
 		getProjectAssBaseAndKeepingInfo('#tqsqForm',row[0].pid,"#objectLocation_tqcl");
 		// 打开窗口   dlg_tqsq
 		$('#dlg_tqsq').dialog('open').dialog('setTitle', "提取申请");
 		// 初始化文件列表 tqsq-datagrid
 		$('#tqsq-datagrid').datagrid({
 			url:"getProjectAssFile.action?baseId="+row[0].pid+"&fileTyle=2",
 			method:'post'
		}); 
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能提取申请一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

// 打开提取处理窗口
function openApplyManagetransact(){
	// 获取选中的数据
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		// 判断选中的数据是否是提取申请的,只能对提取申请的数据进行提取处理
 		if( null != row[0].handleDttm && row[0].handleDttm != ''){
 			$.messager.alert("操作提示","一条提取信息只能处理一次,请重新选择!","error");
 			return;
 		}
 		// 重置from表单数据
 		$('#tqclForm').form('reset');
 		// 调用函数赋值抵质押物信息和保管信息
 		getProjectAssBaseAndKeepingInfo('#tqclForm',row[0].baseId,"#objectLocation_tqcl");
 		// 赋值提取申请事由
 		$("#applyRemark").textbox("textbox").val(row[0].applyRemark);
 		// 赋值提取申请人
 		$("#tqclForm input[name='applyUserName']").val(row[0].applyUserName);
 		// 赋值baseId
 		$("#tqclForm input[name='baseId']").val(row[0].baseId);
 		// 赋值pid
 		$("#tqclForm input[name='pid']").val(row[0].pid);
 		// 打开窗口   dlg_tqcl
 		$('#dlg_tqcl').dialog('open').dialog('setTitle', "提取处理");
 		// 初始化文件列表 tqcl-datagrid
 		$('#tqcl-datagrid').datagrid({
			url:"getProjectAssFile.action?baseId="+row[0].pid+"&fileTyle=2",
 			method:'post'
		}); 
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能提取处理一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

// 打开抵质押解除窗口
function openRelieve(){
	// 获取选中的数据
	var row = $('#grid').datagrid('getSelections');
 	if (row.length == 1) {
 		/*// 判断选中的数据是否是已保管,只能对已保管的数据进行抵押解除
 		if(row[0].status != 3 ){
 			$.messager.alert("操作提示","只能对已保管的抵押登记物件进行抵押解除!","error");
 			return;
 		}*/
 		// 重置from表单数据
 		$('#jcdzyForm').form('reset');
 		// 调用函数赋值抵质押物信息和保管信息
 		getProjectAssBaseAndKeepingInfo('#jcdzyForm',row[0].pid,"#objectLocation_tqcl");
 		// 赋值pid
 		$("#jcdzyForm input[name='pid']").val(row[0].pid);
 		// 打开窗口   dlg_jcdzy
 		$('#dlg_jcdzy').dialog('open').dialog('setTitle', "抵质押解除");
 		// 初始化文件列表 jcdzy-datagrid
 		$('#jcdzy-datagrid').datagrid({
			url:"getProjectAssFile.action?baseId="+row[0].pid+"&fileTyle=3",
			method:'post'
		});
	}else if(row.length > 1){
		$.messager.alert("操作提示","每次只能抵押解除一条数据,请重新选择！","error");
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

/**
 * 撤销抵质押物状态
 */
function revoke(){
	// 获取选中的数据
	var rows = $('#grid').datagrid('getSelections');
 	if (rows.length > 0) {
 		// 循环获取所选取的抵质押物的PID
 		var pids = "";
 		for(var i = 0;i < rows.length ; i ++){
 			if (i == 0) {
				pids = rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
 		}
 		// ajax调用方法
 		$.ajax({
 			type: "POST",
 	        url: BASE_PATH+"mortgageController/revokeProjectAssBase.action",
 	        data:{"pids":pids},
 	        dataType: "json",
 	        success: function(data){
 	        	if(data && data.header["success"]){
 	        		$.messager.alert('操作提示',data.header["msg"]);
 	        		// 刷新datagrid
 	        		publicRefresh('#grid');
 	        	}
 	        }
 		});
	}else{
		$.messager.alert("操作提示","请选择数据","error");
	}
}

/**
 * 根据抵质押物ID获取相对应的抵质押物信息and保管信息
 * @param formId 表单formID
 * @param baseId 抵质押物ID
 * @param addressId 物件地址ID
 */
function getProjectAssBaseAndKeepingInfo(formId,baseId,addressId){
	// 获取抵质押物详细信息的URL
	var url_info = BASE_PATH + "mortgageController/getProjectAssBaseByPid.action";
	$.post(url_info,{baseId : baseId}, 
		function(data) {
			//  赋值
			$(formId).form('load', data);
			// 格式化登记时间
			$(formId +" input[name='regDt']").val(convertDate(data.regDt));
			// 拼接物件地点-- 省份和城市必填
	 		var address = data.addressProvince + data.addressCity;
	 		// 判断区是否选择,如果没选,不拼接进去
	 		if(data.addressArea != "请选择"){
	 			address += data.addressArea;
	 		}
	 		// 拼接物件地址的详细地点
	 		address  += data.addressDetail;
	 		// 赋值物件地址
	 		$(addressId).val(address);
	 		// 获取最新的保管信息的URL
	 		var url_save = BASE_PATH + "mortgageController/getProjectAssKeepingByBaseId.action";
	 		$.post(url_save,{baseId : baseId}, 
	 			function(data1) {
	 				// 赋值保管时间和保管备注
	 				$(formId +" input[name='saveDttm']").val(convertDateTime(data1.saveDttm));
	 				$(formId +" input[name='saveRemark']").val(data1.saveRemark);
	 		},'json');
	},'json');
	
}
