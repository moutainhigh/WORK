<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/common.jsp"%>
<style type="text/css">
</style>
<head>
<meta http-equiv="content-type" content="text/html;charset=UTF-8">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="KEYS">
<meta http-equiv="description" content="">
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/customer/saveComFinancialInfo.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/customer/comFinancialInfoCalculate.js"></script>
<script type="text/javascript" src="${ctx}/p/xlkfinance/module/customer/financialInfoStyle.js"></script>
<script type="text/javascript">

/*资产负债表导出  */
function ExcelExcelCusComBalanceSheet() {
	var leftCusComBalanceSheetContent = $("#saveLeftCusComBalanceSheetTable").datagrid('getRows');//获得datagrid表的所有行的值
	var rightCusComBalanceSheetContent = $("#saveRightCusComBalanceSheetTable").datagrid('getRows');
	var reportId = "";
	 var value = $("#submitBtn").val();
	 var flag=true;
	 if(value==0){
		$.messager.alert('导出资产负债表','请保存编辑后的数据!','error');
		return flag=false;		
	 }
	 if(flag){
			for (var i = 0; i < leftCusComBalanceSheetContent.length; i++) {
				if (i == 0) {
					reportId += leftCusComBalanceSheetContent[i].bsPid;
				} else {
					reportId += "," + leftCusComBalanceSheetContent[i].bsPid;
				}
			}
			
		  var reportId2 = "";
				for (var i = 0; i < rightCusComBalanceSheetContent.length; i++) {
					if (i == 0) {
						reportId2 += rightCusComBalanceSheetContent[i].bsPid;
					} else {
						reportId2 += "," + rightCusComBalanceSheetContent[i].bsPid;
					}
				}
				
		var reportId3 = reportId+","+reportId2;
		window.location.href ="${basePath}comFinancialStatus/excelExcelCusComBalanceSheet.action?pids="+reportId3;
	}
}

/*利润表导出  */
function ExcelCusComIncomeReport() {
	 var rows = $("#saveCusComIncomeReportTable").datagrid('getRows'); //获得datagrid表的所有行的值
	 var reportId = "";
	 var value = $("#submitBtn").val();
	 var flag=true;
	 if(value==0){
		$.messager.alert('导出利润表','请保存编辑后的数据!','error');
		return flag=false;		
	 }
	 if(flag){
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				reportId += rows[i].irReportId;
			} else {
				reportId += "," + rows[i].irReportId;
			}
		}
		window.location.href ="${basePath}comFinancialStatus/excelCusComIncomeReportByReportId.action?reportId="+reportId;
	}
}

//导出现金流量表
function exportCashFlow(){
	var rows = $("#saveCusComCaseFlowTable").datagrid('getRows');//获得datagrid表的所有行的值
	var value = $("#submitBtn").val();
	var flag=true;
	 var reportId = "";
	 if(value==0){
		 $.messager.alert('导出现金流量表','请保存编辑后的数据!','error');
	     return flag=false;		 
	 }
	 if(flag){
		 for (var i = 0; i < rows.length; i++) {
				if (i == 0) {
					reportId += rows[i].cfPid;
				} else {
					reportId += "," + rows[i].cfPid;
				}
			}
		window.location.href ="${basePath}comFinancialStatus/excelCashFlowByReportId.action?reportId="+reportId;
	 }
}

//导出现金流量表材料
function exportCashFlowMaterial(){
	var rows = $("#saveCusComCaseFlowMetaTable").datagrid('getRows');//获得datagrid表的所有行的值
	var reportId = "";
	var value = $("#submitBtn").val();
	var flag=true;
	if(value==0){
		$.messager.alert('导出现金流量表材料','请保存编辑后的数据!','error');
		return flag=false;		
	}
	if(flag){
		for (var i = 0; i < rows.length; i++) {
			if (i == 0) {
				reportId += rows[i].cfPid;
			} else {
				reportId += "," + rows[i].cfPid;
			}
		}
    window.location.href ="${basePath}comFinancialStatus/exportCashFlowMaterialByReportId.action?reportPid="+reportId;
	}
}

Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  
$(document).ready(function() {
	var cYear=Number(new Date().Format('yyyy'));
	var cMonth=Number(new Date().Format('MM'));
	var len=cYear+10-1982;
	var resultYear='';
	for(var i=0;i<len;i++){
		resultYear+='<option onclick="check(this.value)"'+(cYear==(1982+i)?' selected="selected"':'')+'>'+(1982+i)+'</option>';
	}
	$('#accountingYear').html(resultYear);
	var acctId = ${acctId};
	var isEditReport = $("#isEditReport").val();
	var isEditReport = isEditReport==null?"":isEditReport;
	if(isEditReport=='yes'){
		var reportPeriod = $("#reportPeriod").val();
		$("#accountingYear").val(reportPeriod.substring(0,4));
		$("#accountingMonth").val(reportPeriod.substring(4));
	}else{
		$("#accountingMonth").val(cMonth);
	}
	
	
	var accountingYear = $("#accountingYear").val();
	var accountingMonth = $("#accountingMonth").val();
	
	var reportName = '';
	if(accountingMonth=='13'){
		reportName = accountingYear + '年' + '年度财报';
	}else{
		reportName = accountingYear + '年' + accountingMonth + '月财报';
	}
	
	$("#reportName").val(reportName);
	
	$($("#accountingYear")).change( function() {
		accountingYear = $("#accountingYear").val();
		//var reportName = accountingYear + '年' + accountingMonth + '月财报';
		var reportName = '';
		if(accountingMonth=='13'){
			reportName = accountingYear + '年' + '年度财报';
		}else{
			reportName = accountingYear + '年' + accountingMonth + '月财报';
		}
		$("#reportName").val(reportName);
	}); 
	
	$($("#accountingMonth")).change( function() {
		accountingMonth = $("#accountingMonth").val();
		//var reportName = accountingYear + '年' + accountingMonth + '月财报';
		var reportName = '';
		if(accountingMonth=='13'){
			reportName = accountingYear + '年' + '年度财报';
		}else{
			reportName = accountingYear + '年' + accountingMonth + '月财报';
		}
		$("#reportName").val(reportName);
	});
	
	$('#saveLeftCusComBalanceSheetTable').datagrid({    
		onClickRow:function(rowIndex){
			$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',0);
			$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',15);
			$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',20);
			$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',29);
			$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',33);
			$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',34);
			calculateLeftCusComBalanceSheetVal(rowIndex);
	    }    
	});   
	
	$('#saveRightCusComBalanceSheetTable').datagrid({    
		onClickRow:function(rowIndex){
			$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',0);
			$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',16);
			$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',23);
			$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',27);
			
			calculateRightCusComBalanceSheetVal(rowIndex);
	    }    
	});   
	
	//利润表
	$('#saveCusComIncomeReportTable').datagrid({    
		onClickRow:function(rowIndex){
			calculateCusComIncomeReportSheetVal(rowIndex);
	    }    
	}); 
	
	//现金流量表
	$('#saveCusComCaseFlowTable').datagrid({    
		onClickRow:function(rowIndex){
			$('#saveCusComCaseFlowTable').datagrid('endEdit',0);
			$('#saveCusComCaseFlowTable').datagrid('endEdit',11);
			$('#saveCusComCaseFlowTable').datagrid('endEdit',22);
			
			calculateCusComCaseFlowSheetVal(rowIndex);
	    }    
	});   

	//计算补充材料表
	$('#saveCusComCaseFlowMetaTable').datagrid({    
		onClickRow:function(rowIndex){
			$('#saveCusComCaseFlowMetaTable').datagrid('endEdit',0);
			$('#saveCusComCaseFlowMetaTable').datagrid('endEdit',19);
			$('#saveCusComCaseFlowMetaTable').datagrid('endEdit',24);
			
			
			calculateCusComCaseFlowMetaSheetVal(rowIndex);
	    }    
	}); 
	
	
});


//保存利润表
function saveCusComIncomeReportForm(){
	var content = $("#saveCusComIncomeReportTable").datagrid('getRows');//获得datagrid表的所有行的值
	for (var i = 0; i < content.length; i++) {
		var row = content[i];
		var thisMonthVal = row.thisMonthVal;
		if(thisMonthVal=="" || thisMonthVal==null || thisMonthVal=="NaN"){
			row.thisMonthVal = "0";
		}
		
		var thisYearVal = row.thisYearVal;
		if(thisYearVal=="" || thisYearVal==null || thisYearVal=="NaN"){
			row.thisYearVal = "0";
		}
	}
	
	
	var acctId = ${acctId};
	var comId=${comId};
	var accountingYear = $("#accountingYear").val();
	var accountingMonth = $("#accountingMonth").val();
	var reportName = $("#reportName").val();
	//$.getJSON(
	  $.post(
			"${basePath }customerController/saveCusComIncomeReport.action",
			"reportName="+reportName+"&accountingYear="+accountingYear+"&accountingMonth="+accountingMonth+"&acctId="+acctId+"&comId="+comId+"&content="+JSON.stringify(content),
			function call(data){
				var data = data.trim();
				if(data=="saveSucc"){
					$.messager.alert('保存利润表','保存成功!','info','height','500');
					if(isEditedIncome){
						$("#saveCusComIncomeReportTable").datagrid('reload');
					}
					/*
					if(confirm('保存利润表成功!')){
						location.reload();
					}*/
				}else if(data=="hasExistRecord"){
					$.messager.alert('保存利润表','已存在该年月报表，修改请到编辑窗口!','error');
				}else{
					$.messager.alert('保存利润表','保存失败!','error');
				}
				
				//location.reload();
			}
		);
	$('#saveCusComIncomeReportForm').form('submit');
}

//编辑利润表
var isEditedIncome = false;
function editCusComIncomeReportForm(){
	
	var hasEditIncomeReportData = "${hasEditIncomeReportData}";
	if(hasEditIncomeReportData=="noData" && !isEditedIncome){
		saveCusComIncomeReportForm();
		isEditedIncome = true;
		//$("#saveCusComIncomeReportTable").datagrid('load');
		//clearCusComIncomeReportTable();
		//clearCusComIncomeReportTable();
		//$.messager.alert('利润表','无该利润表历史数据，请新增!','error');
		//return;
	}else{
	
	var content = $("#saveCusComIncomeReportTable").datagrid('getRows');//获得datagrid表的所有行的值
	var acctId = ${acctId};
	var comId=${comId};
	//var accountingYear = $("#accountingYear").val();
	//var accountingMonth = $("#accountingMonth").val();
    $.post(
			"${basePath }customerController/updateCusComIncomeReport.action",
			"acctId="+acctId+"&comId="+comId+"&content="+JSON.stringify(content),
			function call(data){
				var data = data.trim();
				if(data=="editSucc"){
					$.messager.alert('利润表','编辑成功!','info');
					$("#submitBtn").val('1');//编辑数据后保存就改变它的状态
					/*
					if(confirm('编辑利润表成功!')){
						location.reload();
					}*/
				}else if(data=="noHistoricalData"){
					$.messager.alert('利润表','无该表历史数据，请新增!','error');
				}else{
					$.messager.alert('利润表','编辑失败!','error');
				}
				//location.reload();
			}
		);
	$('#saveCusComIncomeReportForm').form('submit');
	}
}

//保存负债表
function submitCusComBalanceSheetForm(){
	var leftCusComBalanceSheetContent = $("#saveLeftCusComBalanceSheetTable").datagrid('getRows');//获得datagrid表的所有行的值
	var rightCusComBalanceSheetContent = $("#saveRightCusComBalanceSheetTable").datagrid('getRows');
	
	/**
	for (var i = 0; i < leftCusComBalanceSheetContent.length; i++) {
		var row = leftCusComBalanceSheetContent[i];
		var lineNum = row.lineNum;
		if(lineNum==0){
			leftCusComBalanceSheetContent.splice(i,1);
			
		}
	}
	**/
	
	for (var i = 0; i < leftCusComBalanceSheetContent.length; i++) {
		var row = leftCusComBalanceSheetContent[i];
		var beginVal = row.beginVal;
		if(beginVal=="" || beginVal==null || beginVal=="NaN"){
			row.beginVal = "0";
		}
		
		var endVal = row.endVal;
		if(endVal=="" || endVal==null || endVal=="NaN"){
			row.endVal = "0";
		}
		
	}
	
	for (var i = 0; i < rightCusComBalanceSheetContent.length; i++) {
		var row = rightCusComBalanceSheetContent[i];
		var beginVal = row.beginVal;
		if(beginVal=="" || beginVal==null || beginVal=="NaN"){
			row.beginVal = "0";
		}
		
		var endVal = row.endVal;
		if(endVal=="" || endVal==null || endVal=="NaN"){
			row.endVal = "0";
		}
	}
	
	
	
	
	var acctId = ${acctId};
	var comId=${comId};
	var accountingYear = $("#accountingYear").val();
	var accountingMonth = $("#accountingMonth").val();
	var reportName = $("#reportName").val();
	$.ajax({
		type: "POST",
        url: "${basePath}customerController/saveCusComBalanceSheet.action",
		data:{
			comId:comId,
			acctId:acctId,
			reportName:reportName,
			accountingYear:accountingYear,
			accountingMonth:accountingMonth,
			leftCusComBalanceSheetContent:JSON.stringify(leftCusComBalanceSheetContent),
			rightCusComBalanceSheetContent:JSON.stringify(rightCusComBalanceSheetContent)
			},		
		success : function(data) {
			var data = data.trim();
			if (data == "saveSucc") {
				$.messager.alert('保存负债表', '保存成功!', 'info');
				if(isEditedBalanceSheet){
					$("#saveLeftCusComBalanceSheetTable").datagrid('reload');
					$("#saveRightCusComBalanceSheetTable").datagrid('reload');
				}
			} else if (data == "hasExistRecord") {
				$.messager.alert('保存负债表', '已存在该年月报表，修改请到编辑窗口!','error');
			} else {
				$.messager.alert('保存负债表', '保存失败!', 'error');
			}
		}
				});
		$('#saveCusComBalanceSheetForm').form('submit');
	}

	//编辑负债表后，保存
	var isEditedBalanceSheet = false;
	function submitEditCusComBalanceSheetForm() {
		
		var hasEditLeftBalanceSheetData = "${hasEditLeftBalanceSheetData}";
		var hasEditRightBalanceSheetData = "${hasEditRightBalanceSheetData}";
		if((hasEditLeftBalanceSheetData=="noData" || hasEditRightBalanceSheetData=="noData") && !isEditedBalanceSheet){
			submitCusComBalanceSheetForm();
			isEditedBalanceSheet = true;
			//$.messager.alert('负债表','无该负债表历史数据，请新增!','error');
			//return;
		}else{
		
		var leftCusComBalanceSheetContent = $(
				"#saveLeftCusComBalanceSheetTable").datagrid('getRows');//获得datagrid表的所有行的值
		var rightCusComBalanceSheetContent = $(
				"#saveRightCusComBalanceSheetTable").datagrid('getRows');
		var acctId = ${acctId};
		var comId = ${comId};
		//var accountingYear = $("#accountingYear").val();
		//var accountingMonth = $("#accountingMonth").val();
		//$.getJSON(
		$.post(
			"${basePath }customerController/updateCusComBalanceSheet.action",
			"acctId="
					+ acctId
					+ "&comId="
					+ comId
					+ "&leftCusComBalanceSheetContent="
					+ JSON.stringify(leftCusComBalanceSheetContent)
					+ "&rightCusComBalanceSheetContent="
					+ JSON
							.stringify(rightCusComBalanceSheetContent),
			function call(data) {
				var data = data.trim();
				if (data == "editSucc") {
					$.messager.alert('负债表','编辑成功!','info');
					$("#submitBtn").val('1');//编辑数据后保存就改变它的状态
					/*
					if (confirm('编辑负债表成功!')) {
						location.reload();
					}*/
				}else if(data=="noHistoricalData"){
					$.messager.alert('负债表','无该表历史数据，请新增!','error');
				}else {
					$.messager.alert('负债表', '编辑失败!', 'error');
				}
				//location.reload();
			});
		$('#saveCusComBalanceSheetForm').form('submit');
		}
	}

	function writeHtml(data) {
	}

	function newAddFinancialData() {
		window.location = '${basePath }customerController/initFinancialInfo.action';
	}

	function saveCusComIncomeReport() {
		$('#saveCusComIncomeReport').form(
						'submit',
						{
							url : "saveCusComIncomeReport.action",
							onSubmit : function() {

							},
							success : function(result) {
								window.location = '${basePath }customerController/initFinancialInfo.action';
							},
							error : function(result) {
								alert("保存失败！");
							}
						});
	}

	//保存现金流量表补充材料
	function submitCaseFlowIt() {
		var content = $("#saveCusComCaseFlowMetaTable").datagrid('getRows');//获得datagrid表的所有行的值
		
		for (var i = 0; i < content.length; i++) {
			var row = content[i];
			var thisMonthVal = row.thisMonthVal;
			if(thisMonthVal=="" || thisMonthVal==null || thisMonthVal=="NaN"){
				row.thisMonthVal = "0";
			}
			
			var thisYearVal = row.thisYearVal;
			if(thisYearVal=="" || thisYearVal==null || thisYearVal=="NaN"){
				row.thisYearVal = "0";
			}
		}		
		
		var acctId = ${acctId};
		var comId = ${comId};
		var accountingYear = $("#accountingYear").val();
		var accountingMonth = $("#accountingMonth").val();
		var reportName = $("#reportName").val();
		$.post("${basePath }customerController/saveCusComCaseFlowMeta.action",
				"reportName=" + reportName + "&accountingYear="
						+ accountingYear + "&accountingMonth="
						+ accountingMonth + "&acctId=" + acctId + "&comId="
						+ comId + "&content=" + JSON.stringify(content),
				function call(data) {
					var data = data.trim();
					if (data == "saveSucc") {
						$.messager.alert('保存现金流量表','保存成功!','info');
						if(isEditedCaseFlowMeta){
							$("#saveCusComCaseFlowMetaTable").datagrid('reload');
						}
						/*
						if (confirm('保存现金流量成功!')) {
							location.reload();
						}*/
					} else if (data == "hasExistRecord") {
						$.messager.alert('保存现金流量表', '已存在该年月报表，修改请到编辑窗口!',
								'error');
					} else {
						$.messager.alert('保存现金流量表', '保存失败!', 'error');
					}
					//location.reload();
				});
		$('#saveCusComCaseFlowMetaForm').form('submit');
	}

//保存编辑后的现金流量表补充材料
var isEditedCaseFlowMeta = false; 
function submitEditCaseFlowIt() {
		
		var hasEditCaseFlowITData = "${hasEditCaseFlowITData}";
		if(hasEditCaseFlowITData=="noData" && !isEditedCaseFlowMeta){
			submitCaseFlowIt();
			isEditedCaseFlowMeta = true; 
			//$.messager.alert('现金流量补充材料','无该现金流量补充材料表历史数据，请新增!','error');
			//return;
		}else{
		
		var content = $("#saveCusComCaseFlowMetaTable").datagrid('getRows');//获得datagrid表的所有行的值
		var acctId = ${acctId};
		var comId = ${comId};
		//var accountingYear = $("#accountingYear").val();
		//var accountingMonth = $("#accountingMonth").val();
		//var reportName = $("#reportName").val();
		$.post("${basePath }customerController/updateCusComCaseFlow.action", //因为现金流量表与现金流量补充材料表更新都是一样的
		"acctId=" + acctId + "&comId=" + comId + "&content="
				+ JSON.stringify(content), function call(data) {
			var data = data.trim();
			if (data == "editSucc") {
				$.messager.alert('现金流量补充材料表','编辑成功!','info');
				$("#submitBtn").val('1');
				/*
				if (confirm('编辑现金流量补充材料成功!')) {
					location.reload();
				}*/
			}else if(data=="noHistoricalData"){
				$.messager.alert('现金流量补充材料表','无该表历史数据，请新增!','error');
			}else {
				$.messager.alert('现金流量补充材料表', '编辑失败!', 'error');
			}
			//location.reload();
		});
		$('#saveCusComCaseFlowMetaForm').form('submit');
	}
}

	//保存现金流量表
	function submitCaseFlow() {
		var content = $("#saveCusComCaseFlowTable").datagrid('getRows');//获得datagrid表的所有行的值
		
		for (var i = 0; i < content.length; i++) {
			var row = content[i];
			var thisMonthVal = row.thisMonthVal;
			if(thisMonthVal=="" || thisMonthVal==null || thisMonthVal=="NaN"){
				row.thisMonthVal = "0";
			}
			
			var thisYearVal = row.thisYearVal;
			if(thisYearVal=="" || thisYearVal==null || thisYearVal=="NaN"){
				row.thisYearVal = "0";
			}
		}
		
		
		var acctId = ${acctId};
		var comId = ${comId};
		var accountingYear = $("#accountingYear").val();
		var accountingMonth = $("#accountingMonth").val();
		var reportName = $("#reportName").val();
		$.post("${basePath }customerController/saveCusComCaseFlow.action",
				"reportName=" + reportName + "&accountingYear="
						+ accountingYear + "&accountingMonth="
						+ accountingMonth + "&acctId=" + acctId + "&comId="
						+ comId + "&content=" + JSON.stringify(content),
				function call(data) {
					var data = data.trim();
					if (data == "saveSucc") {
						$.messager.alert('现金流量表','保存成功!','info');
						if(isEditedCaseFlow){
							$("#saveCusComCaseFlowTable").datagrid('reload');
						}
						/*
						if (confirm('保存现金流量表成功!')) {
							location.reload();
						}
						*/
					} else if (data == "hasExistRecord") {
						$.messager.alert('现金流量表', '已存在该年月报表，修改请到编辑窗口!',
								'error');
					} else {
						$.messager.alert('现金流量表', '保存失败!', 'error');
					}
					//location.reload();
				});
		$('#saveCusComCaseFlowForm').form('submit');
	}

//保存编辑后的现金流量表
var isEditedCaseFlow = false;
function submitEditCaseFlow() {
		var hasEditCaseFlowData = "${hasEditCaseFlowData}";
		if(hasEditCaseFlowData=="noData"  && !isEditedCaseFlow){
			submitCaseFlow();
			isEditedCaseFlow = true;
			//$.messager.alert('现金流量表','无该现金流量表历史数据，请新增!','error');
			//return;
		}else{
		
		var content = $("#saveCusComCaseFlowTable").datagrid('getRows');//获得datagrid表的所有行的值
		var acctId = ${acctId};
		var comId = ${comId};
		//var accountingYear = $("#accountingYear").val();
		//var accountingMonth = $("#accountingMonth").val();
		//var reportName = $("#reportName").val();
		$.post("${basePath }customerController/updateCusComCaseFlow.action",
				"acctId=" + acctId + "&comId=" + comId + "&content="
						+ JSON.stringify(content), function call(data) {
					var data = data.trim();
					if (data == "editSucc") {
						$.messager.alert('流量表','编辑成功!','info');
						$("#submitBtn").val('1');//编辑数据后保存就改变它的状态
						/*
						if (confirm('编辑流量表成功!')) {
							location.reload();
						}*/
					}else if(data=="noHistoricalData"){
						$.messager.alert('流量表','无该表历史数据，请新增!','error');
					}else {
						$.messager.alert('流量表', '编辑失败!', 'error');
					}
					//location.reload();
				});
		$('#saveCusComCaseFlowForm').form('submit');
	}
}
	
function clearCusComBalanceSheetForm(){
	$("#saveLeftCusComBalanceSheetTable").datagrid('reload');
	$("#saveRightCusComBalanceSheetTable").datagrid('reload');
}

function clearCusComIncomeReportTable(){
	$("#saveCusComIncomeReportTable").datagrid('reload');
}

function clearCusComCaseFlowTable(){
	$("#saveCusComCaseFlowTable").datagrid('reload');
}

function clearCusComCaseFlowMetaTable(){
	$("#saveCusComCaseFlowMetaTable").datagrid('reload');

}

//formatMoney
function formatMoney(value,row,index){
	if(value){
		return accounting.formatMoney(value, "", 2, ",", ".");
	}else{
		return "";
	}
}
function changStatus(){
	$("#submitBtn").val('0');
}
</script>
</head>
<body class="easyui-layout">
	<div data-options="region:'center',border:false">
		<div id="scroll-bar-div">
<div id="main_body">
<div id="cus_content">
<jsp:include page="cus_com_tab.jsp">
<jsp:param value="2" name="tab"/>
</jsp:include>
<!-- <div class="tab_title">
		财务信息录入
</div>
<div style="margin:0;padding:0; width:580px;height:1px;background-color:#303030;overflow:hidden;"> </div> 
 <br/> -->
<div title="财务信息录入" data-options="closable:true,iconCls:'icon-cut'" style="padding:10px;height: 600px">

		<div class="easyui-tabs" >
			<table width="100%">
		<tr>
			<td>
				报表期间：
			<select  class="#" id="accountingYear" name="accountingYear" size="">
				
		   </select>
		  年
		  <select  class="#" id="accountingMonth" name="accountingMonth" size="">
				<option onclick="check1(this.value)" value="1">1</option>
				<option onclick="check1(this.value)" value="2">2</option>
				<option onclick="check1(this.value)" value="3">3</option>
				<option onclick="check1(this.value)" value="4">4</option>
				<option onclick="check1(this.value)" value="5">5</option>
				<option onclick="check1(this.value)" value="6">6</option>
				<option onclick="check1(this.value)" value="7">7</option>
				<option onclick="check1(this.value)" value="8">8</option>
				<option onclick="check1(this.value)" value="9">9</option>
				<option onclick="check1(this.value)" value="10">10</option>
				<option onclick="check1(this.value)" value="11">11</option>
				<option onclick="check1(this.value)" value="12">12</option>
				<option onclick="check1(this.value)" value="13">年度</option>
		  </select>月

		  报表表示名称：<input type="text" id="reportName" name="reportName" class="text_style"/>
			
			</td>
			<td align="right">
			<a  href="javascript:history.go(-1);" class="easyui-linkbutton" iconcls="icon-back">后退</a>
			</td>
		</tr>
		 </table>
			<div title="资产负债表" style="padding:10px;" >
			
				<div style="text-align:right;padding:5px;width:960px;height:auto;">
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="ExcelExcelCusComBalanceSheet()">导出资产负债表</a>
			    </c:when>
				</c:choose>
		    	</div>
		    	<br/>
		    	
				<input type="hidden" id="reportId" name="reportId" value="${reportId }"/>
				<input type="hidden" id="isEditReport" name="isEditReport" value="${isEditReport }"/>
				<input type="hidden" id="reportPeriod" name="reportPeriod" value="${reportPeriod }"/>
				 
				<form id="saveCusComBalanceSheetForm" method="post" >

					<table>
						<tr>
							<td>
								<table id="saveLeftCusComBalanceSheetTable" class="easyui-datagrid" title="资产负债表" style="width:500px;height:auto;"
								data-options="
									iconCls: 'icon-edit',
									singleSelect: true,
									url: '${basePath }customerController/initLeftCusComBalanceSheet.action?reportId='+'${reportId }',
									method:'post',
									rownumbers: true,
									onClickCell: onClickCellLeftCusComBalanceSheetTable
								">
									<thead>
										<tr>
										    <th align="right" hidden="true"  data-options="field:'bsPid',resizable:false"><%--bsPid编辑报表表示BalanceSheetEditDTO类的值，资产负债表,CUS_COM_BALANCE_SHEET的PID--%>
											<th align="right" hidden="true"  data-options="field:'pid',resizable:false"><%--资产负债表报表会计科目表(CUS_COM_BALANCE_SHEET_META)PID --%>
											<th align="left"  data-options="field:'accountsName',width:150,resizable:false,formatter:formatCss" style="text-indent: 2em"><p align="center" style="">资产类</p></th>
											<th align="right" data-options="field:'beginVal',width:160,align:'right',styler:hideLeftBeginVal,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">年初数（万元）</p></th>
											<th align="right" data-options="field:'endVal',width:160,align:'right',styler:hideLeftEndVal,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">期末数（万元）</p></th>
										</tr>
									</thead>
								</table>
							</td>
							<td align="left">
								<table id="saveRightCusComBalanceSheetTable" class="easyui-datagrid" title="  " style="width:475px;height:auto;"
								data-options="
									singleSelect: true,
									url: '${basePath }customerController/initRightCusComBalanceSheet.action?reportId='+'${reportId }',
									method:'post',
									rownumbers: false,
									onClickCell: onClickCellRightCusComBalanceSheetTable
								">
									<thead>
										<tr>
										    <th align="right" hidden="true"  data-options="field:'bsPid',resizable:false"><%--bsPid编辑报表表示BalanceSheetEditDTO类的值，资产负债表,CUS_COM_BALANCE_SHEET的PID--%>
											<th align="right" hidden="true"  data-options="field:'pid',resizable:false"><%--资产负债表报表会计科目表(CUS_COM_BALANCE_SHEET_META)PID --%>
											<th align="left"  data-options="field:'accountsName',width:155,resizable:false,formatter:formatCss" style="text-indent: 2em"><p align="center" style="">资产类</p></th>
											<th align="right" data-options="field:'beginVal',width:155,align:'right',styler:hideRightBeginVal,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">年初数（万元）</p></th>
											<th align="right" data-options="field:'endVal',width:155,align:'right',styler:hideRightBeginVal,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">期末数（万元）</p></th>
										</tr>
									</thead>
								</table>
							</td>
						</tr>
					</table>
				<br/>
			</form>
			<div style="text-align:center;padding:5px" align="center">
				<%--如果新增进入该页面，显示保存；如果是编辑进入该页面，显示提交 --%>
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submitEditCusComBalanceSheetForm()">提交</a>
					<input type="hidden" id="submitBtn" value="0"/>
				</c:when>
				<c:otherwise>
		    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submitCusComBalanceSheetForm()">保存</a>
				</c:otherwise>
				</c:choose>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-reload" onclick="clearCusComBalanceSheetForm()">重置</a>
	    	</div>
			</div>
			
			<div title="利润表" style="padding:10px;">
				<div style="text-align:right;padding:5px;width:945px;height:auto">
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="ExcelCusComIncomeReport()">导出利润表</a>
			 	</c:when>
				</c:choose>
		    	</div>
			    
			    <br/>
			    
				<form id="saveCusComIncomeReportForm" method="post">
				<input type="hidden" id="hasEditIncomeReportData" name="hasEditIncomeReportData" value="${hasEditIncomeReportData}"/>
				<table id="saveCusComIncomeReportTable" class="easyui-datagrid" title="利润表" style="width:960px;height:auto"
				data-options="
					iconCls: 'icon-edit',
					singleSelect: true,
					url: '${basePath }customerController/initCusComIncomeReport.action?reportId='+'${reportId }',
					method:'post',
					rownumbers: true,
					onClickCell: onClickCellCusComIncomeReportTable
				">
					<thead>
						<tr>
							<th align="left" hidden="true"  data-options="field:'irPid'"></th><%--编辑利润报表使用,CUS_COM_INCOME_REPORT的PID --%>
							<th align="left" hidden="true"  data-options="field:'irReportId'"></th><%--编辑利润报表使用,CUS_COM_INCOME_REPORT的PID --%>
							<th align="left" hidden="true"  data-options="field:'pid'"></th><%--新增时使用利润表项目表CUS_COM_INCOME_REPORT_META的PID  --%>
							<th align="center" hidden="true"   data-options="field:'lineNum',width:30,align:'center'"><p align="center" style="">序号</p></th>
							<th align="right"  hidden="true" data-options="field:'itemPrefix',width:30"style=""></th>
							<th align="left"  data-options="field:'itemName',width:310,styler:incomeItemNameStyle"><p align="center" style="">项目</p></th>
							<th align="right" data-options="field:'thisMonthVal',width:310,align:'right',styler:incomeThisMonthValStyle,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">本月数（万元）</p></th>
							<th align="right" data-options="field:'thisYearVal',width:310,align:'right',styler:incomeThisYearValStyle,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">本期累计数（万元）</p></th>
						</tr>
					</thead>
				</table>
				<br/>
			</form>
			<div style="text-align:center;padding:5px" align="center">
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="editCusComIncomeReportForm()">提交</a>
					<input type="hidden" id="submitBtn" value="0"/>
				</c:when>
				<c:otherwise>
		    		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveCusComIncomeReportForm()">保存</a>
				</c:otherwise>
				</c:choose>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-reload" onclick="clearCusComIncomeReportTable()">重置</a>
	    	</div>
		</div>
		
		
			  <div title="现金流量表" style="padding:10px;"><!--onclick="initItem();" -->
			    <div style="text-align:right;padding:5px;width:960px;height:auto">
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportCashFlow()">导出现金流量表</a>
			 	</c:when>
				</c:choose>
		    	</div>
			    <br/>
			    
				<form id="saveCusComCaseFlowForm" method="post">
				<table id="saveCusComCaseFlowTable" class="easyui-datagrid" title="现金流量表" style="width:960px;height:auto"
				data-options="
					iconCls: 'icon-edit',
					singleSelect: true,
					url: '${basePath}customerController/searcherCusComCaseFlowTable.action?reportId='+'${reportId }',
					method:'post',   
					rownumbers: true,
					onClickCell: onClickCells,
					onBeforeEdit:changStatus
				">
					<thead>
						<tr>
							<th align="left" hidden="true"  data-options="field:'cfPid'"></th><%--编辑现金流量表使用,CUS_COM_CASH_FLOW的PID --%>
							<th align="left" hidden="true"  data-options="field:'cfReportId'"></th><%--编辑现金流量表使用,CUS_COM_CASH_FLOW的PID --%>
							<%--<th align="center" data-options="field:'lineNum',width:30,align:'center'"><p align="center" style="">序号</p></th>--%>
							<th align="left"  data-options="field:'itemName',width:310,styler:caseFlowItemNameStyle"><p align="center" style="">项目</p></th>
							<th align="right" data-options="field:'thisMonthVal',width:310,align:'right',styler:caseFlowThisMonthValStyle,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">本月数（万元）</p></th>
							<th align="right" data-options="field:'thisYearVal',width:310,align:'right',styler:caseFlowThisYearValStyle,formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">本期累计数（万元）</p></th>
						</tr>
					</thead>
				</table>
				<br/>
			</form>
			
			<div style="text-align:center;padding:5px" align="center">
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submitEditCaseFlow()">提交</a>
					<input type="hidden" id="submitBtn" value="0"/>
				</c:when>
				<c:otherwise>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submitCaseFlow();">保存</a>
				</c:otherwise>
				</c:choose>
		    	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-reload" onclick="clearCusComCaseFlowTable();">重置</a>
	    	</div>
		</div>
		
		
			<div title="现金流量表补充资料" style="padding:10px;">
	
				<div style="text-align:right;padding:5px;width:960px;height:auto">
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-export" plain="true" onclick="exportCashFlowMaterial()">导出现金流量表补充资料</a>
				</c:when>
				</c:choose>
				</div>
				<br/>

		
				    
					<form id="saveCusComCaseFlowMetaForm" method="post">
					<table id="saveCusComCaseFlowMetaTable" class="easyui-datagrid" title="现金流量表补充资料" style="width:960px;height:auto" 
					data-options="
						iconCls: 'icon-edit',
						singleSelect: true,
						url: '${basePath}customerController/searcherCusComCaseFlowMetaTable.action?reportId='+'${reportId }',
						method:'post',
						rownumbers: true,
						onClickCell: onClickCellsIt,
						onBeforeEdit:changStatus
					">
						<thead>
							<tr>
								<th align="left" hidden="true"  data-options="field:'cfPid'"></th><%--编辑现金流量补充表使用,CUS_COM_CASH_FLOW的PID --%>
								<th align="left" hidden="true"  data-options="field:'pid'"></th>
								<%--<th align="center"  data-options="field:'lineNum',width:30,align:'center'"><p align="center" style="">序号</p></th> --%>
								<th align="left"  data-options="field:'itemName',width:310,styler:caseFlowMetaItemNameStyle"><p align="center" style="">项目</p></th>
								<th align="right" data-options="field:'thisMonthVal',width:310,styler:caseFlowMetaThisMonthValStyle,align:'right',formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">本月数（万元）</p></th>
								<th align="right" data-options="field:'thisYearVal',width:310,styler:caseFlowMetaThisYearValStyle,align:'right',formatter:formatMoney,editor:{type:'numberbox',options:{groupSeparator:',',precision:2}}"><p align="center" style="">本期累计数（万元）</p></th>
							</tr>
						</thead>
					</table>
					<br/>
				</form>

				<div style="text-align:center;padding:5px" align="center">
				<c:choose>
				<c:when test="${isEditReport=='yes'}">
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submitEditCaseFlowIt()">提交</a>
					<input type="hidden" id="submitBtn" value="0"/>
				</c:when>
				<c:otherwise>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="submitCaseFlowIt();">保存</a>
				</c:otherwise>
				</c:choose>
			    	<a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-reload" onclick="clearCusComCaseFlowMetaTable();">重置</a>
		    	</div>
			</div>
			</div>
		</div>
</div>
</div>
</div>
</div>
</body>




