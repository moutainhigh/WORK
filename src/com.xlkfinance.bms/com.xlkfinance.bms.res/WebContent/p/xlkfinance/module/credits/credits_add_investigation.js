// 初始化省份
function loadProvince(){
	var province = document.getElementById("addressProvince");
	var city = document.getElementById("addressCity");
	var area = document.getElementById("addressArea");
	for ( var i = 0; i < arrCity.length; i++) {
		province.options[i] = new Option(arrCity[i].name, arrCity[i].name);
	}
	city.options[0] = new Option("请选择", "请选择");
	area.options[0] = new Option("请选择", "请选择");
}

// 初始化市
function loadCity(){
	var province = document.getElementById("addressProvince");
	var city = document.getElementById("addressCity");
	var area = document.getElementById("addressArea");
	var region1_obj = arrCity[province.selectedIndex];
	var region2_arr = region1_obj.sub;
	city.options.length = 0;
	for ( var i = 0; i < region2_arr.length; i++) {
		city.options[i] = new Option(region2_arr[i].name, region2_arr[i].name);
	}
	area.options[0] = new Option("请选择", "请选择");
}

// 初始化区
function loadArea(){
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
}

/**
 * 计算贷款利率
 */
function changeLoanInterest(newValue, oldValue){
	var str =  parseFloat($('#monthLoanMgr').numberbox('getValue'));
	str += parseFloat($('#monthLoanOtherFee').numberbox('getValue'));
	str += parseFloat(newValue);
	$("#heji").numberbox('setValue', str );
	$("#yearLoanInterest").numberbox('setValue', newValue*12.0 );
	$("#dayLoanInterest").numberbox('setValue', newValue/30.0 );
}

/**
 * 计算贷款管理费率
 */
function changeLoanMgr(newValue, oldValue){
	var str = parseFloat($('#monthLoanInterest').numberbox('getValue'));
	str += parseFloat($('#monthLoanOtherFee').numberbox('getValue'));
	str += parseFloat(newValue);
	$("#heji").numberbox('setValue', str );
	$("#yearLoanMgr").numberbox('setValue', newValue*12.0 );
	$("#dayLoanMgr").numberbox('setValue', newValue/30.0 );
}

/**
 * 计算其它费用费率
 */
function changeLoanOtherFee(newValue, oldValue){
	var str = parseFloat($('#monthLoanMgr').numberbox('getValue'));
	str +=  parseFloat($('#monthLoanInterest').numberbox('getValue'));
	str += parseFloat(newValue);
	$("#heji").numberbox('setValue', str );
	$("#yearLoanOtherFee").numberbox('setValue', newValue*12.0 );
	$("#dayLoanOtherFee").numberbox('setValue', newValue/30.0 );
}

//保存基本信息
function saveInvestigation(){
	// 判断项目名称
	if($("#projectName").val()==null || $("#projectName").val()==""){
		$.messager.alert('操作提示',"项目名称不允许为空!",'error');	
		return;
	}
	// 获取所选担保方式
	var assWay = $("#assWay").combobox("getValues");
	// 判断是否选择担保方式
	if( assWay == "" ){
		$.messager.alert('操作提示',"请选择担保方式!",'error');	
		return;
	}
	// 赋值担保方式
	$("#investigationForm input[name='assWay']").val(assWay);
	// 获取授信额度和贷款金额
	var loanAmt = $("#investigationForm input[name='loanAmt']").val();
	var creditAmt = $("#investigationForm input[name='creditAmt']").val();
	// 转换数字类型
	loanAmt = parseFloat(loanAmt);
	creditAmt = parseFloat(creditAmt);
	// 判断贷款金额是否录入正确
	if(loanAmt > creditAmt){
		$.messager.alert('操作提示',"贷款金额不允许超过授信额度!",'error');	
		return;
	}
	// 赋值
	//debugger;
	$("#investigationForm #project_id").val(newProjectId);

	$("#investigationForm input[name='loanId']").val(loanId);
	$("#investigationForm input[name='creditId']").val(creditId);
	
	
	
	$("#investigationForm").form('submit', {
		onSubmit : function() {return $(this).form('validate');},
		success : function(result) {
			var ret = eval("("+result+")");
			if(ret && ret.header["success"]){
				$.messager.alert('操作提示',"保存成功,可以进行贷款试算!",'info');
				loanId = ret.header["msg"];
			}else{
				$.messager.alert('操作提示',ret.header["msg"],'error');	
			}
		}
	}); 
}



// 导出尽职查询报告
function exportSurveyReport(){
	$.messager.alert('操作提示','该功能暂未实现！','error');	
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
				$.post("<%=basePath%>mortgageController/deleteProjectAssBase.action",{pids : pids}, 
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
}

// 设置贷款利息收取单位帐号
function getAccountByPrimaryKeyAccrual(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		return;
	}
	// 设置帐号
	$("#investigationForm input[name='loanInterestRecordNo']").val(rec.bankNum);
}

// 设置贷款管理费收取单位帐号
function getAccountByPrimaryKeyFees(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		return;
	}
	// 设置帐号
	$("#investigationForm input[name='loanMgrRecordNo']").val(rec.bankNum);
}

// 设置其它费用收取单位帐号
function getAccountByPrimaryKeyOther(rec){
	// 判断是不是请选择,如果是直接退出
	if(rec.pid == -1){
		return;
	}
	// 设置帐号
	$("#investigationForm input[name='loanOtherFeeNo']").val(rec.bankNum);
}

//打开抵质押物信息窗口 
function openEdit(mortgageGuaranteeType){
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
}

// 根据取消不同的选择项,隐藏不同的DIV
function unSelectMortgageType(rec){
	var str = rec.lookupDesc;
	if(str == "抵押"){
		$("#mortgageNews").hide();
	}else if(str == "质押"){
		$("#pledgeNews").hide();
	}else if(str == "保证"){
		// 判断是个人,还是企业
		if(1==2){
			// 如果是个人
			$("#personalGuaranteeNews").hide();
		}else{
			// 如果是企业
			$("#corporationGuaranteeNews").hide();
		}
	}
}

// 打开新增抵质押物信息的窗口 
function openAdd(mortgageGuaranteeType){
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
}

// 保存抵质押物
function saveDzyw(){
	// 判断是执行什么操作
	var urlDzyw = "<%=basePath%>mortgageController/addProjectAssBase.action";
	if($("#dzywxxForm input[name='pid']").val() != -1){
		urlDzyw = "<%=basePath%>mortgageController/updateProjectAssBase.action";
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
}

 //是否循环选项 动态显示 最高额授信
function isCheckHoop(){
	var val=$('input:radio[name="isHoop"]:checked').val();
	if(val==2){
	 $(".selectlist1").css("display",'none');
	 $("#circulateType").val("");
	}else{
		$(".selectlist1").css("display",'block');
	}
}

// 根据选取不同,展示不同的DIV
function selectMortgageType(rec){
	var str = rec.lookupDesc;
	if(str == "抵押"){
		$("#mortgageNews").show();
	}else if(str == "质押"){
		$("#pledgeNews").show();
	}else if(str == "保证"){
		// 判断是个人,还是企业
		if(1==1){
			// 如果是个人
			$("#personalGuaranteeNews").show();
		}else{
			// 如果是企业
			$("#corporationGuaranteeNews").show();
		}
	}
}

