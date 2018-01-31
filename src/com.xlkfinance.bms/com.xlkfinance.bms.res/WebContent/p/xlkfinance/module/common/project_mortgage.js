/** 担保方式-抵质押物所有的JS函数存放 **/


/**
 * 省市初始化对象
 */

var mortgage = {
		// 初始化省份
		loadProvince:function (){
			var province = document.getElementById("addressProvince");
			var city = document.getElementById("addressCity");
			var area = document.getElementById("addressArea");
			for ( var i = 0; i < arrCity.length; i++) {
				province.options[i] = new Option(arrCity[i].name, arrCity[i].name);
			}
			$("#addressCity").empty();
			$("#addressArea").empty();
			city.options[0] = new Option("请选择", "请选择");
			area.options[0] = new Option("请选择", "请选择");
		},
		// 初始化市
		loadCity:function (){
			var province = document.getElementById("addressProvince");
			var city = document.getElementById("addressCity");
			var area = document.getElementById("addressArea");
			var region1_obj = arrCity[province.selectedIndex];
			var region2_arr = region1_obj.sub;
			city.options.length = 0;
			for ( var i = 0; i < region2_arr.length; i++) {
				city.options[i] = new Option(region2_arr[i].name, region2_arr[i].name);
			}
			$("#addressArea").empty();
			area.options[0] = new Option("请选择", "请选择");
		},
		
		// 初始化区
		loadArea:function (){
			var province = document.getElementById("addressProvince");
			var city = document.getElementById("addressCity");
			var area = document.getElementById("addressArea");
			var region1_obj = arrCity[province.selectedIndex];
            var region2_obj = region1_obj.sub[city.selectedIndex];
            var region3_arr = region2_obj.sub;
            if (region2_obj.type == 0) {
            	area.options.length = 0;
                for ( var i = 0; i < region3_arr.length; i++) {
                	area.options[i] = new Option(region3_arr[i].name, region3_arr[i].name);
                }
         	}
            if(region3_arr == undefined){ 
				$("#addressArea").empty();
         		area.options[0] = new Option("请选择", "请选择");
         	}
		}
}




// 格式化物件地址 
function formatTheObject(val,row){
	if(null == val || val == ""){
		return "";
	}else {
		// 拼接物件地点-- 省份和城市必填
		var address = row.addressProvince+row.addressCity;
		// 判断区是否选择,如果没选,不拼接进去
		if(row.addressArea != "请选择"){
			address += row.addressArea;
		}
		// 拼接物件地址的详细地点
		address  += row.addressDetail;
		return address;
	}
}

// 判断此行数据是属于新项目还是属于老项目的,如果属于老项目的就置灰
function formatProjectNew(val,row){
	if(null == val || val == ""){
		return "";
	}else if(val == projectId){
		return "";
	}
}

// 抵质押物的初始化
function InitializationMortgage(){
	// 获取所有文本信息
	var str = $("#assWay").combobox("getText");
	// 隐藏抵押列表
	$("#mortgageNews").hide();
	// 隐藏质押列表
	$("#pledgeNews").hide();
	// 隐藏个人和企业保证信息列表
	$("#personalGuaranteeNews").hide();
	$("#corporationGuaranteeNews").hide();
	// 如果是初始化,没有选中就直接退出
	if(str == ""){
		return;
	}
	var arr = str.split(",");
	// 循环判断选取的担保信息 
	for (var i = 0; i < arr.length; i++) {
		if(arr[i] == "抵押"){
			// 显示抵押列表
			$("#mortgageNews").show();
		}else if(arr[i] == "质押"){
			// 显示质押列表
			$("#pledgeNews").show();
		}else if(arr[i] == "保证"){
			// 显示个人和企业保证信息列表
			$("#personalGuaranteeNews").show();
			$("#corporationGuaranteeNews").show();
		}
	}
}

// 根据选取不同,展示不同的DIV
function selectMortgageType(rec){
	var str = rec.lookupDesc;
	if(str == "抵押"){
		// 显示抵押列表
		$("#mortgageNews").show();
		// 刷新列表数据
		$("#dyxx_grid").datagrid('reload');
	}else if(str == "质押"){
		// 显示质押列表
		$("#pledgeNews").show();
		$("#zyxx_grid").datagrid('reload');
	}else if(str == "保证"){
		// 显示个人和企业保证信息列表
		$("#personalGuaranteeNews").show();
		$("#corporationGuaranteeNews").show();	
		$('#personalGuarantee_grid').datagrid('reload');
		$('#corporationGuarantee_grid').datagrid('reload');
	}
}

// 根据取消不同的选择项,隐藏不同的DIV
function unSelectMortgageType(rec){
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
}

// 动态获取抵质押物详细信息
function getAssDtl(rec){
	// 获取选取的担保方式的value
	var lookupType = rec.lookupVal;
	// post请求获取选择的担保方式的详细信息数据
	$.post(BASE_PATH+"sysLookupController/getProjectAssDtlByLookType.action",{lookupType : lookupType}, 
			function(data) {
				//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if(data){
					// 动态生成table and 标题
					var dom = "<h4>填写"+rec.lookupDesc+"详细信息</h4><hr />"
					dom = dom + "<table id='add_mortgageTypeDiv' class='datagrid-btable'> <tr  style='line-height:30px'>";
					for (var i = 0; i < data.length; i++) {
		        		// 第一个 <td> 显示文字
	   		 			dom = dom+'<td align="right" width="160">'+data[i].lookupDesc+':</td>';
	   		 			// 第二个 <td> 显示input输入框和输入的值
		   		 		dom = dom+'<td align="left" width="160"><input class="easyui-textbox text2_style" id="'+data[i].pid+'" title="'+data[i].lookupDesc+'" value="" name="'+data[i].lookupVal+'" /></td>'; 
		   		 		// 判断是否换行
		   		 		if( i % 2 == 0 ){
		       		 		dom = dom+'</tr><tr style="line-height:30px">';
		       		 	}
					}
		        	dom = dom+"</table>";
		        	// 赋值到指定的div
		        	$("#mortgageTypeDiv").html(dom); 
				}else{
					$.messager.alert('操作提示',"获取抵质押物详细信息失败!",'error');	
				}
			},'json');
}

// 打开新增抵质押物信息的窗口 
function openAdd(mortgageGuaranteeType){
	$("#dzywxxForm input[name='mortgageGuaranteeType']").val(mortgageGuaranteeType);
	// 重置form数据
	$("#dzywxxForm").form('reset');
	// 置空div里面动态的数据
	$("#mortgageTypeDiv").html(""); 
	// 设置pid == -1 标志为新增
	$("#dzywxxForm input[name='pid']").val(-1);
	// 让共有人为空
	$("#owns").val("");
	// 设置旧的所有人类型为 -1 
	$("#ownTypeHidden").val(-1);
	// 调用省份初始化
	mortgage.loadProvince();
	// 打开窗口
	$('#dlg_dzywxx').dialog('open').dialog('setTitle', "新增抵质押物信息");
	var pH=$('#dlg_dzywxx').parent().height()+12;
	$("#dlg_dzywxx").dialog("move",{top:($(document).scrollTop()?$(document).scrollTop()+pH*0.5:$(document).height()*0.5-pH*0.5 )});

}

// 打开编辑抵质押物信息窗口 
function openEdit(mortgageGuaranteeType){
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
	// 初始化省市级联效果
	$("#addressProvince").val(row[0].addressProvince);
	mortgage.loadCity();
	$("#addressCity").val(row[0].addressCity);
	mortgage.loadArea();
	$("#addressArea").val(row[0].addressArea);
	// 赋值所有权人类型
	$("#ownTypeHidden").val(row[0].ownType);
	// 根据抵质押物ID动态生成抵质押物类型的详细数据 
	// post请求获取选择的担保方式的详细信息数据
	$.post(BASE_PATH+"mortgageController/getProjectAssDtlByBaseId.action",{baseId : row[0].pid}, 
			function(data) {
				//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if(data){
					// 动态生成table and 标题
					var dom = "<h4>填写"+row[0].mortgageTypeText+"详细信息</h4><hr />"
					dom = dom + "<table id='add_mortgageTypeDiv' class='datagrid-btable'> <tr  style='line-height:30px'>";
		        	for (var i = 0; i < data.length; i++) {
	        			// 第一个 <td> 显示文字
	   		 			dom = dom+'<td align="right" width="160">'+data[i].lookupDesc+':</td>';
	   		 			// 第二个 <td> 显示input输入框和输入的值
		   		 		dom = dom+'<td align="left" width="160"><input class="easyui-textbox text2_style" id="'+data[i].lookupId+'" title="'+data[i].lookupDesc+'" value="'+(data[i].infoVal==null?'':data[i].infoVal)+'" name="'+data[i].lookupVal+'" /></td>';
		   		 		// 判断是否换行
		   		 		if( i % 2 == 0 ){
		       		 		dom = dom+'</tr><tr style="line-height:30px">';
		       		 	}
					}
		        	dom = dom+"</table>";
		        	// 赋值到指定的div
		        	$("#mortgageTypeDiv").html(dom); 
				}else{
					$.messager.alert('操作提示',"获取抵质押物详细信息失败!",'error');	
				}
			},'json');
	// 打开窗口
	$('#dlg_dzywxx').dialog('open').dialog('setTitle', "编辑抵质押物信息");	
	var pH=$('#dlg_dzywxx').parent().height()+12;
	$("#dlg_dzywxx").dialog("move",{top:($(document).scrollTop()?$(document).scrollTop()+pH*0.5:$(document).height()*0.5-pH*0.5 )});

}

// 删除抵质押物信息
function removeDzywxx(mortgageGuaranteeType){
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
								clearSelectRows("#dyxx_grid");
								$("#dyxx_grid").datagrid('reload');
							}else{
								// 必须重置 datagrid 选中的行 
								clearSelectRows("#zyxx_grid");
								$("#zyxx_grid").datagrid('reload');
							}
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json');
 			}
		});
}

// 打开保证信息窗口  1 是个人保证窗口   2 是企业法人保证窗口
function openAddPerGuarantee(assureType){
	// 判断打开那一个窗口
	if(assureType == 1){
		// 打开个人窗口 
		$('#dlg_personalGuarantee').dialog('open').dialog('setTitle', "个人客户保证人查询");	
		$('#personalGuarantee_grid').datagrid('reload');
	}else{
		// 打开企业窗口
		$('#dlg_corporationGuarantee').dialog('open').dialog('setTitle', "法人保证查询");
		$('#corporationGuarantee_grid').datagrid('reload');
	}
}

/**
 * 选取个人保证信息
 * @param type 1:个人担保信息 2:企业法人担保信息
 */
function saveProjectAssure(type,projectId){
	var rows = "";
	// 判断用户所选取的是企业还是个人
	if(type == 1){
		rows = $("#personalGuarantee_grid").datagrid('getSelections');
	}else{
		rows = $("#corporationGuarantee_grid").datagrid('getSelections');
	}
	// 判断是否选取数据
	if(rows.length == 0){
 		$.messager.alert("操作提示","请选择数据!","error");
		return;
	}
	// 循环获取选取的数据的主键
 	var refIds = "";
	for(var i=0;i<rows.length;i++){
  		var row = rows[i];
  		if(type == 1){
  			refIds += row.pid+",";
  		}else if(type == 2){
  			refIds += row.acctId+",";
  		}
  	}
	// 赋值担保人类型
	$("#customProjectAssure input[name='assureType']").val(type);
	// 赋值项目ID
	$("#customProjectAssure input[name='projectId']").val(projectId);
	// 赋值所选取的担保人
	$("#customProjectAssure input[name='refIds']").val(refIds);
	
	// 提交表单
	$("#customProjectAssure").form('submit', {
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示',ret.header["msg"],'info');
				// 刷新指定的datagrid
				if(type == 1){
					// 必须重置 datagrid 选中的行 
					clearSelectRows("#personalGuarantee_grid");
					clearSelectRows("#perGuarantee_grid");
					$("#perGuarantee_grid").datagrid('load');
					// 关闭个人担保人员选取
					$("#dlg_personalGuarantee").dialog("close");
				}else{
					// 必须重置 datagrid 选中的行 
					clearSelectRows("#corporationGuarantee_grid");
					clearSelectRows("#corGuarantee_grid");
					$("#corGuarantee_grid").datagrid('load');
					// 关闭企业法人担保信息选取
					$("#dlg_corporationGuarantee").dialog("close");
				}
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		}
	});
}

/**
 * 删除保证信息
 * @param type 1:个人担保信息 2:企业法人担保信息
 */
function deleteProjectAssure(type){
	// 获取数据
	var rows = "";
	// 判断是个人担保还是企业法人担保
	if(type == 1){
		// 个人担保
		rows = $('#perGuarantee_grid').datagrid('getSelections');
	}else{
		// 企业法人担保 
		rows = $('#corGuarantee_grid').datagrid('getSelections');
	}
	// 判断选取的数量
	if(rows.length == 0){
 		$.messager.alert("操作提示","请选择数据!","error");
		return;
	}
 	// 循环获取选取的数据的主键
 	var refIds = "";
	for(var i=0;i<rows.length;i++){
  		var row = rows[i];
  		refIds += row.pid+",";
  	}
 	$.messager.confirm("操作提示","确定删除选择的此批担保信息吗?",
		function(r) {
 			if(r){
				$.post(BASE_PATH+"secondBeforeLoanController/deleteProjectAssure.action",{refIds : refIds}, 
					function(ret) {
						//  此方法，不需要转换json格式数据,因为传过来的值就是json格式的数据
						if(ret && ret.header["success"]){
							$.messager.alert('操作提示',ret.header["msg"],'info');
							// 判断是抵押还是质押
							if(type == 1){
								// 必须重置 datagrid 选中的行 
								clearSelectRows("#perGuarantee_grid");
								$("#perGuarantee_grid").datagrid('reload');
							}else{
								// 必须重置 datagrid 选中的行 
								clearSelectRows("#corGuarantee_grid");
								$("#corGuarantee_grid").datagrid('reload');
							}
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					},'json');
 			}
		});
}

//打开共有人页面
function openOwnItem(){
	// 获取所有劝人类型
	var ownType = $("#dzywxxForm input[name='ownType']").val();
	// 判断是否选取所有权人 类型
	if(ownType == -1){
		$.messager.alert('操作提示',"请先选择所有人类型,再选取共有人!",'error');
		return;
	}
	// 清空datagrid数据
	clearSelectRows("#acct_set_own_grid");
	// 获取编辑的数据
	var owns = $("#owns").val();
	// 判断是否是新增,还是编辑操作
	if($("#dzywxxForm input[name='pid']").val() == -1){
		// 新增操作
		if(owns != ""){
			// 加载数据
			var url = BASE_PATH+"mortgageController/getProjectAssOwnByRelationId.action?relationIds="+owns+"&ownType="+ownType;
			$('#acct_set_own_grid').datagrid('options').url = url;
			$('#acct_set_own_grid').datagrid('reload');
		}else{
			// 清空datagrid
			$('#acct_set_own_grid').datagrid('loadData', { total: 0, rows: [] });
		}
	}else{
		// 加载数据
		var url = BASE_PATH+"mortgageController/getProjectAssOwnByBaseId.action?baseId="+$("#dzywxxForm input[name='pid']").val()+"&ownType="+ownType;
		$('#acct_set_own_grid').datagrid('options').url = url;
		$('#acct_set_own_grid').datagrid('reload');
	}
	$('#dlg_own').dialog('open').dialog('setTitle', "查看设定共有人");
}

// 打开新增共有人
function openAddOwnItem(){
	// 重置表单数据
	$("#searchFromAcctOwn").form('reset');
	// 赋值客户类型
	$("#searchFromAcctOwn input[name='cusType']").val($("#dzywxxForm input[name='ownType']").val());
	// 判断所有人类型,如果是个人,就禁用 ‘经济行业类别’ 的选择
	if($("#dzywxxForm input[name='ownType']").val() == 1){
		$("#ecoTradeGYR").attr("disable",true);
	}
	// 打开
	$('#dlg_acct_own').dialog('open').dialog('setTitle', "选取共有人");
	// 查询数据
	ajaxSearchPage('#acct_own_grid','#searchFromAcctOwn');
}

// 选择共有人
function selectAssOwn(){
	// 获取所有劝人类型
	var ownType = $("#dzywxxForm input[name='ownType']").val();
	// 获取选择的共有人信息
	var rows = $('#acct_own_grid').datagrid('getSelections');
	// 判断是否选中数据
 	if ( rows.length == 0 ) {
 		$.messager.alert("操作提示","请选择数据","error");
		return;
	}
 	// 获取选中的数据行的PID
 	var pids = "";
 	// 循环拼接(1,2,3)
	for (var i = 0; i < rows.length; i++) {
		if (i == 0) {
			pids = rows[i].pid;
		} else {
			pids += "," + rows[i].pid;
		}
	}
	// 刷新共有人列表 URL
	var url = "";
 	// 判断是什么操作 
 	if($("#dzywxxForm input[name='pid']").val() == -1){
 		// 如果是新增,拼接到以前的共有人中
		var owns = $("#owns").val();
		// 判断是否存在值
		if( owns == "" ){
			$("#owns").val(pids);
		}else{
			$("#owns").val(owns+","+pids);
		}
		owns = $("#owns").val();
		url = BASE_PATH+"mortgageController/getProjectAssOwnByRelationId.action?relationIds="+owns+"&ownType="+ownType;
		// 刷新共有人列表
		$('#acct_set_own_grid').datagrid('options').url = url;
		$('#acct_set_own_grid').datagrid('reload');
		// 清空查看共有人的datagrid 和 选取共有人的datagrid
		clearSelectRows("#acct_set_own_grid");
		clearSelectRows("#acct_own_grid");
 	}else{
 		// 如果是编辑 :新增的数据,直接添加到数据库中
 		saveAssOwn(pids,$("#dzywxxForm input[name='pid']").val(),ownType);
 	}
	// 关闭选取共有人窗口
	$('#dlg_acct_own').dialog('close');
}

//新增共有人到数据库中
function saveAssOwn(pids,baseId,ownType){
	$.post(BASE_PATH+"mortgageController/addProjectAssOwn.action",{ownUserIds : pids,baseId : baseId}, 
		function(ret) {
			//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示',ret.header["msg"]);
				// 刷新表单的URL
		 		var url = BASE_PATH+"mortgageController/getProjectAssOwnByBaseId.action?baseId="+$("#dzywxxForm input[name='pid']").val()+"&ownType="+ownType;
				$('#acct_set_own_grid').datagrid('options').url = url;
				$('#acct_set_own_grid').datagrid('reload');
				// 清空查看共有人的datagrid 和 选取共有人的datagrid
				clearSelectRows("#acct_set_own_grid");
				clearSelectRows("#acct_own_grid");
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		},'json');
}

// 删除抵质押物共有人
function removeAssOwn(){
	// 获取所有劝人类型
	var ownType = $("#dzywxxForm input[name='ownType']").val();
	
	// 获取选中数据
	var rows = $('#acct_set_own_grid').datagrid('getSelections');
	// 判断是什么操作
	if($("#dzywxxForm input[name='pid']").val() == -1){
		//在easyui-1.3.3以前的版本中datatgrid删除行deleteRow的方法中，他在删除行以后会去调opts.view.deleteRow.call(opts.view,_4d2,_4d3);
		//刷新页面上的行的index，index会发生改变
		
		// 新创建一个数组
		var copyRows = [];
		// 赋值到一个新的数组中
		for (var i = 0; i < rows.length; i++) {
			copyRows.push(rows[i])
		}
		// 循环对新数组进行删除操作
		for (var i = 0; i < copyRows.length; i++) {
			// 获取某行的rowIndex
			var index = $('#acct_set_own_grid').datagrid('getRowIndex',copyRows[i]);
			// 删除行
            $('#acct_set_own_grid').datagrid('deleteRow',index); //通过行号移除该行
		}
		// 删除成功后,获取当前列表的共有人ID
		var delRows = $('#acct_set_own_grid').datagrid('getRows');
		// 循环获取共有人ID
		var pids = "";
		for (var i = 0; i < delRows.length; i++) {
			if (i == 0) {
				pids = delRows[i].pid;
			} else {
				pids += "," + delRows[i].pid;
			}
		}
		// 赋值到属性上
		$("#owns").val(pids);
	}else {
		var pids = "";
		// 如果是编辑操作,可以直接删除数据库里面的东西,再刷新你页面 
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				pids = rows[i].pid;
			} else {
				pids += "," + rows[i].pid;
			}
		}
		$.post(BASE_PATH+"mortgageController/batchDeleteProjectAssOwn.action",{pids : pids}, 
			function(ret) {
				//  此方法，不许换转换json格式数据,因为传过来的值就是json格式的数据
				if(ret && ret.header["success"]){
					$.messager.alert('操作提示',ret.header["msg"]);
					// 刷新共有人列表
					var url = BASE_PATH+"mortgageController/getProjectAssOwnByBaseId.action?baseId="+$("#dzywxxForm input[name='pid']").val()+"&ownType="+ownType;
					$('#acct_set_own_grid').datagrid('options').url = url;
					$('#acct_set_own_grid').datagrid('reload');
				}else{
					$.messager.alert('操作提示',ret.header["msg"],'error');	
				}
			},'json');
	}
	// 清空查看共有人的datagrid 
	clearSelectRows("#acct_set_own_grid");
}

// 判断抵质押物存在数据,但是项目担保方式没有选择此类型
function checkMortgageType(assWayText){
	// 获取抵押列表的所有数据 
	var rowsDY = $("#dyxx_grid").datagrid('getRows');
	// 获取质押列表的所有数据
	var rowsZY = $("#zyxx_grid").datagrid('getRows');
	// 获取个人和法人列表的所有数据
	var per_rows = $("#perGuarantee_grid").datagrid('getRows');
	var cor_rows = $("#corGuarantee_grid").datagrid('getRows');
	
	// 判断--没有选择抵押类型,是否存在抵押数据
	if( rowsDY.length > 0 && assWayText.indexOf("抵押") == -1 ){
		$.messager.confirm("操作提示","您填写了抵押数据,但是您没有选择抵押担保方式,如果继续会删除抵押数据,是否继续？",
			function(r) {
				if(r){
					// 判断--没有选择质押类型,是否存在质押数据
					if( rowsZY.length > 0 && assWayText.indexOf("质押") == -1 ){
						$.messager.confirm("操作提示","您填写了质押数据,但是您没有选择质押担保方式,如果继续会删除质押数据,是否继续？",
							function(r) {
								if(r){
									// 判断--没有选择保证类型,是否存在保证数据
									if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
										$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
											function(r) {
												if(r){
													preservation();
												}else{
													return;
												}
										});
									}else{
										preservation();
									}
								}else{
									return;
								}
						});
					}else{
						// 判断--没有选择保证类型,是否存在保证数据
						if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
							$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
								function(r) {
									if(r){
										preservation();
									}else{
										return;
									}
							});
						}else{
							preservation();
						}
					}
				}else{
					return;
				}
		});
	}else{
		// 判断--没有选择质押类型,是否存在质押数据
		if( rowsZY.length > 0 && assWayText.indexOf("质押") == -1 ){
			$.messager.confirm("操作提示","您填写了质押数据,但是您没有选择质押担保方式,如果继续会删除质押数据,是否继续？",
				function(r) {
					if(r){
						// 判断--没有选择保证类型,是否存在保证数据
						if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
							$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
								function(r) {
									if(r){
										preservation();
									}else{
										return;
									}
							});
						}else{
							preservation();
						}
					}else{
						return;
					}
			});
		}else{
			// 判断--没有选择保证类型,是否存在保证数据
			if( (per_rows.length > 0 || cor_rows.length > 0) && assWayText.indexOf("保证") == -1 ){
				$.messager.confirm("操作提示","您填写了保证数据,但是您没有选择保证担保方式,如果继续会删除保证数据,是否继续？",
					function(r) {
						if(r){
							preservation();
						}else{
							return;
						}
				});
			}else{
				preservation();
			}
		}
	}
	
}

// 保存方法
function preservation(){
	
	//判断同意客户是否项目相同，根据项目的计划放款日期和贷款金额判断
		var planOutLoanDt=$("#planOutLoanDt").textbox('getValue');
		//var loanAmt=$("#loanAmt").numberbox('getValue');
		var perAccId = $("#personalForm input[name='acctId']").val();
		// 贷款金额
 		var loanAmt = $("#investigationForm input[name='loanAmt']").val();
	$.post(BASE_PATH+"beforeLoanController/getCheckAcctProject.action", {
		     loanAmt : loanAmt,
			 planOutLoanDt:planOutLoanDt,
			 acctId:perAccId
	}, function(ret) {	
		//debugger;
//		if(ret.pid!=null && ret.pid!=0){
//			$.messager.confirm("操作提示","改客户已存在同一金额，同一放款时间的项目是否继续保存",	function(r) {
// 						if(r){
// 							
// 							
// 							$("#investigationForm").form('submit', {
// 								onSubmit : function() {return $(this).form('validate');},
// 								success : function(result) {
// 									var ret = eval("("+result+")");
// 									if(ret && ret.header["success"]){
// 										$.messager.alert('操作提示',"保存成功,可以进行贷款试算!");
// 										bianzhi = 1;// 标志为是流程
// 										loanId = ret.header["msg"];
// 										// 刷新抵质押物和保证的列表并清除所有列表的选择
// 										clearSelectRows("#dyxx_grid");
// 										$('#dyxx_grid').datagrid('reload');
// 										clearSelectRows("#zyxx_grid");
// 										$('#zyxx_grid').datagrid('reload');
// 										clearSelectRows("#perGuarantee_grid");
// 										$('#perGuarantee_grid').datagrid('reload');
// 										clearSelectRows("#corGuarantee_grid");
// 										$("#corGuarantee_grid").datagrid('reload');
// 										// 让还款期限新旧一样
// 										$("#hkqx").val($("#investigationForm input[name='repayCycle']").val());
// 									}else{
// 										$.messager.alert('操作提示',ret.header["msg"],'error');	
// 									}
// 								}
// 							}); 
// 							
// 							
// 						}else{
// 							return;
// 						}
// 				});
//		}else{
			$("#investigationForm").form('submit', {
					onSubmit : function() {return $(this).form('validate');},
					success : function(result) {
						var ret = eval("("+result+")");
						if(ret && ret.header["success"]){
							$.messager.alert('操作提示',"保存成功,可以进行贷款试算!");
							bianzhi = 1;// 标志为是流程
							loanId = ret.header["msg"];
							// 刷新抵质押物和保证的列表并清除所有列表的选择
							clearSelectRows("#dyxx_grid");
							$('#dyxx_grid').datagrid('reload');
							clearSelectRows("#zyxx_grid");
							$('#zyxx_grid').datagrid('reload');
							clearSelectRows("#perGuarantee_grid");
							$('#perGuarantee_grid').datagrid('reload');
							clearSelectRows("#corGuarantee_grid");
							$("#corGuarantee_grid").datagrid('reload');
							// 让还款期限新旧一样
							$("#hkqx").val($("#investigationForm input[name='repayCycle']").val());
						}else{
							$.messager.alert('操作提示',ret.header["msg"],'error');	
						}
					}
				}); 
//		}
	}, 'json');
	
	
}
