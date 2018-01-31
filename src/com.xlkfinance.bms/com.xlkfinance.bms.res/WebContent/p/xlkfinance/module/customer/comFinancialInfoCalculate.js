//左边资产负债表计算
function calculateLeftCusComBalanceSheetVal(j){
	
	//pengtao todo
	//$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',1);//当前行编辑事件取消
	//$("#saveLeftCusComBalanceSheetTable").datagrid('getEditor',{index:0,field:'beginVal'}).disabled();
	//$("#saveLeftCusComBalanceSheetTable").disabled();
	
	var leftRows = $("#saveLeftCusComBalanceSheetTable").datagrid('getRows'); 
	
	var  totalCurrentAssets_beginVal = 0;
	var  totalCurrentAssets_endVal = 0;
	for(var i=0;i<leftRows.length;i++){

		//计算流动资产合计
		if(i<14){
			totalCurrentAssets_beginVal += parseFloat(convertZero(leftRows[i].beginVal));
			totalCurrentAssets_endVal += parseFloat(convertZero(leftRows[i].endVal));
		}	
	}
	
	//长期投资合计
	var leftVal21 = parseFloat(convertZero(leftRows[16].beginVal)) + parseFloat(convertZero(leftRows[17].beginVal));
	var leftVal22 = parseFloat(convertZero(leftRows[16].endVal)) + parseFloat(convertZero(leftRows[17].endVal));
	
	//  固定资产净值
	var leftVal31 = parseFloat(convertZero(leftRows[21].beginVal)) - parseFloat(convertZero(leftRows[22].beginVal));
	var leftVal32 = parseFloat(convertZero(leftRows[21].endVal)) - parseFloat(convertZero(leftRows[22].endVal));
	
	//固定资产净额
	//var leftVal41 = parseFloat(convertZero(leftRows[21].beginVal)) - parseFloat(convertZero(leftRows[22].beginVal));
	//var leftVal42 = parseFloat(convertZero(leftRows[21].endVal)) - parseFloat(convertZero(leftRows[22].endVal));
	
	//固定资产合计
	var leftVal51 = parseFloat(convertZero(leftVal31)) + parseFloat(convertZero(leftRows[25].beginVal)) + parseFloat(convertZero(leftRows[26].beginVal)) + parseFloat(convertZero(leftRows[27].beginVal));
	var leftVal52 = parseFloat(convertZero(leftVal32)) + parseFloat(convertZero(leftRows[25].endVal)) + parseFloat(convertZero(leftRows[26].endVal)) + parseFloat(convertZero(leftRows[27].endVal));
	
	//无形资产及其他资产合计
	var leftVal61 = parseFloat(convertZero(leftRows[30].beginVal)) + parseFloat(convertZero(leftRows[31].beginVal)) + parseFloat(convertZero(leftRows[32].beginVal));
	var leftVal62 = parseFloat(convertZero(leftRows[30].endVal)) + parseFloat(convertZero(leftRows[31].endVal)); + parseFloat(convertZero(leftRows[32].endVal));
	
	// 资产总计
	var leftVal71 = parseFloat(convertZero(leftRows[14].beginVal)) + parseFloat(convertZero(leftRows[18].beginVal)) + parseFloat(convertZero(leftRows[28].beginVal)) + parseFloat(convertZero(leftRows[33].beginVal))  + parseFloat(convertZero(leftRows[35].beginVal));
	var leftVal72 = parseFloat(convertZero(leftRows[14].endVal)) + parseFloat(convertZero(leftRows[18].endVal)) + parseFloat(convertZero(leftRows[28].endVal)) + parseFloat(convertZero(leftRows[33].endVal))  + parseFloat(convertZero(leftRows[35].endVal));
	
	$('#saveLeftCusComBalanceSheetTable').datagrid('updateRow',{index:14,row:{beginVal:Digit.round(totalCurrentAssets_beginVal, 2),endVal:Digit.round(totalCurrentAssets_endVal, 2)}});
	$('#saveLeftCusComBalanceSheetTable').datagrid('updateRow',{index:18,row:{beginVal:Digit.round(leftVal21, 2),endVal:Digit.round(leftVal22, 2)}});
	$('#saveLeftCusComBalanceSheetTable').datagrid('updateRow',{index:23,row:{beginVal:Digit.round(leftVal31, 2),endVal:Digit.round(leftVal32, 2)}});
	$('#saveLeftCusComBalanceSheetTable').datagrid('updateRow',{index:28,row:{beginVal:Digit.round(leftVal51, 2),endVal:Digit.round(leftVal52, 2)}});
	$('#saveLeftCusComBalanceSheetTable').datagrid('updateRow',{index:33,row:{beginVal:Digit.round(leftVal61, 2),endVal:Digit.round(leftVal62, 2)}});
	$('#saveLeftCusComBalanceSheetTable').datagrid('updateRow',{index:36,row:{beginVal:Digit.round(leftVal71, 2),endVal:Digit.round(leftVal72, 2)}});  
}


//右边资产负债表的计算
function calculateRightCusComBalanceSheetVal(j){

	var rightRows = $("#saveRightCusComBalanceSheetTable").datagrid('getRows'); 
	
	var  total_beginVal = 0;
	var  total_endVal = 0;
	for(var i=0;i<rightRows.length;i++){
		//计算流动负债合计
		if(i<15){
			total_beginVal += parseFloat(convertZero(rightRows[i].beginVal));
			total_endVal += parseFloat(convertZero(rightRows[i].endVal));
		}	
	}
	
	
	// 长期负债合计
	var rightVal21 = parseFloat(convertZero(rightRows[17].beginVal)) + parseFloat(convertZero(rightRows[18].beginVal)) + 
					 parseFloat(convertZero(rightRows[19].beginVal)) + parseFloat(convertZero(rightRows[20].beginVal)) +parseFloat(convertZero(rightRows[21].beginVal));
	var rightVal22 = parseFloat(convertZero(rightRows[17].endVal)) + parseFloat(convertZero(rightRows[18].endVal)) + 
					 parseFloat(convertZero(rightRows[19].endVal)) + parseFloat(convertZero(rightRows[20].endVal)) + parseFloat(convertZero(rightRows[21].endVal));
	
	// 负债总计
	var rightVal31 = parseFloat(convertZero(rightRows[15].beginVal)) + parseFloat(convertZero(rightRows[22].beginVal)) + parseFloat(convertZero(rightRows[24].beginVal));
	var rightVal32 = parseFloat(convertZero(rightRows[15].endVal)) + parseFloat(convertZero(rightRows[22].endVal)) + parseFloat(convertZero(rightRows[24].endVal));
	
	//  实收资本（或股本）净额
	var rightVal41 = parseFloat(convertZero(rightRows[28].beginVal)) - parseFloat(convertZero(rightRows[29].beginVal));
	var rightVal42 = parseFloat(convertZero(rightRows[28].endVal)) - parseFloat(convertZero(rightRows[29].endVal));
	
	//期末数，未分配利润,还没实现
	//var incomeRow = $('#saveCusComIncomeReportTable').getRowIndex(17);
	//var rightVal52 = incomeRow.thisMonthVal;

	//所有者权益（或股东权益）合计
	var rightVal61 = parseFloat(convertZero(rightRows[30].beginVal)) + parseFloat(convertZero(rightRows[31].beginVal)) + parseFloat(convertZero(rightRows[32].beginVal)) + parseFloat(convertZero(rightRows[33].beginVal)) + parseFloat(convertZero(rightRows[34].beginVal));
	var rightVal62 = parseFloat(convertZero(rightRows[30].endVal)) + parseFloat(convertZero(rightRows[31].endVal)) + parseFloat(convertZero(rightRows[32].endVal)) + parseFloat(convertZero(rightRows[33].endVal)) + parseFloat(convertZero(rightRows[34].endVal));

	//  负债和所有者权益（或股东权益）总计
	var rightVal71 = parseFloat(convertZero(rightRows[25].beginVal)) + parseFloat(convertZero(rightRows[35].beginVal));
	var rightVal72 = parseFloat(convertZero(rightRows[25].endVal)) + parseFloat(convertZero(rightRows[35].endVal));

	$('#saveRightCusComBalanceSheetTable').datagrid('updateRow',{index:15,row:{beginVal:Digit.round(total_beginVal, 2),endVal:Digit.round(total_endVal, 2)}});
	$('#saveRightCusComBalanceSheetTable').datagrid('updateRow',{index:22,row:{beginVal:Digit.round(rightVal21, 2),endVal:Digit.round(rightVal22, 2)}});
	$('#saveRightCusComBalanceSheetTable').datagrid('updateRow',{index:25,row:{beginVal:Digit.round(rightVal31, 2),endVal:Digit.round(rightVal32, 2)}});
	$('#saveRightCusComBalanceSheetTable').datagrid('updateRow',{index:30,row:{beginVal:Digit.round(rightVal41, 2),endVal:Digit.round(rightVal42, 2)}});
	//$('#saveRightCusComBalanceSheetTable').datagrid('updateRow',{index:34,row:{endVal:rightVal52}});
	$('#saveRightCusComBalanceSheetTable').datagrid('updateRow',{index:35,row:{beginVal:Digit.round(rightVal61, 2),endVal:Digit.round(rightVal62, 2)}});
	$('#saveRightCusComBalanceSheetTable').datagrid('updateRow',{index:36,row:{beginVal:Digit.round(rightVal71, 2),endVal:Digit.round(rightVal72, 2)}});

}

//计算利润表
function calculateCusComIncomeReportSheetVal(j){
	
	var rightRows = $("#saveCusComIncomeReportTable").datagrid('getRows'); 
	//二、主营业务利润
	var a = parseFloat(convertZero(rightRows[0].thisMonthVal)) - parseFloat(convertZero(rightRows[1].thisMonthVal))- parseFloat(convertZero(rightRows[2].thisMonthVal));
	var b= parseFloat(convertZero(rightRows[0].thisYearVal)) - parseFloat(convertZero(rightRows[1].thisYearVal)) - parseFloat(convertZero(rightRows[2].thisYearVal));
	//三、营业利润
	var a1 = a - parseFloat(convertZero(rightRows[4].thisMonthVal))- parseFloat(convertZero(rightRows[5].thisMonthVal))- parseFloat(convertZero(rightRows[6].thisMonthVal))- parseFloat(convertZero(rightRows[7].thisMonthVal));
	var b1 = b  - parseFloat(convertZero(rightRows[4].thisYearVal)) - parseFloat(convertZero(rightRows[5].thisYearVal))- parseFloat(convertZero(rightRows[6].thisYearVal))- parseFloat(convertZero(rightRows[7].thisYearVal));
	//四、利润总额
	var a2 =  parseFloat(convertZero(rightRows[8].thisMonthVal))+ parseFloat(convertZero(rightRows[9].thisMonthVal))+ parseFloat(convertZero(rightRows[10].thisMonthVal))+ parseFloat(convertZero(rightRows[11].thisMonthVal))- parseFloat(convertZero(rightRows[12].thisMonthVal))+ parseFloat(convertZero(rightRows[13].thisMonthVal));
	var b2 =  parseFloat(convertZero(rightRows[8].thisYearVal)) +parseFloat(convertZero(rightRows[9].thisYearVal)) + parseFloat(convertZero(rightRows[10].thisYearVal))+ parseFloat(convertZero(rightRows[11].thisYearVal))- parseFloat(convertZero(rightRows[12].thisYearVal))+ parseFloat(convertZero(rightRows[13].thisYearVal));
	//五、净利润
	var a3 = parseFloat(convertZero(rightRows[14].thisMonthVal)) - parseFloat(convertZero(rightRows[15].thisMonthVal))- parseFloat(convertZero(rightRows[16].thisMonthVal));
	var b3= parseFloat(convertZero(rightRows[14].thisYearVal)) - parseFloat(convertZero(rightRows[15].thisYearVal)) - parseFloat(convertZero(rightRows[16].thisYearVal));
	
	$('#saveCusComIncomeReportTable').datagrid('updateRow',{index:3,row:{thisMonthVal:Digit.round(a, 2),thisYearVal:Digit.round(b, 2)}});
	$('#saveCusComIncomeReportTable').datagrid('updateRow',{index:8,row:{thisMonthVal:Digit.round(a1, 2),thisYearVal:Digit.round(b1, 2)}});
	$('#saveCusComIncomeReportTable').datagrid('updateRow',{index:14,row:{thisMonthVal:Digit.round(a2, 2),thisYearVal:Digit.round(b2, 2)}});
	$('#saveCusComIncomeReportTable').datagrid('updateRow',{index:17,row:{thisMonthVal:Digit.round(a3, 2),thisYearVal:Digit.round(b3, 2)}});
}

//计算现金流量表
function calculateCusComCaseFlowSheetVal(j){
	
	var rightRows = $("#saveCusComCaseFlowTable").datagrid('getRows'); 
	//1.现金流入小计
	var a = parseFloat(convertZero(rightRows[1].thisMonthVal)) + parseFloat(convertZero(rightRows[2].thisMonthVal))+ parseFloat(convertZero(rightRows[3].thisMonthVal));
	var b= parseFloat(convertZero(rightRows[1].thisYearVal)) + parseFloat(convertZero(rightRows[2].thisYearVal)) + parseFloat(convertZero(rightRows[3].thisYearVal));
	//1.现金流出小计
	var a1 = parseFloat(convertZero(rightRows[5].thisMonthVal)) + parseFloat(convertZero(rightRows[6].thisMonthVal))+ parseFloat(convertZero(rightRows[7].thisMonthVal))+ parseFloat(convertZero(rightRows[8].thisMonthVal));
	var b1= parseFloat(convertZero(rightRows[5].thisYearVal)) + parseFloat(convertZero(rightRows[6].thisYearVal))+ parseFloat(convertZero(rightRows[7].thisYearVal))+ parseFloat(convertZero(rightRows[8].thisYearVal));
	//经营活动产生的现金流量净额
	var f = parseFloat(convertZero(rightRows[4].thisMonthVal)) - parseFloat(convertZero(rightRows[9].thisMonthVal));
	var h = parseFloat(convertZero(rightRows[4].thisYearVal)) - parseFloat(convertZero(rightRows[9].thisYearVal));
	//2.现金流入小计
	var a2 = parseFloat(convertZero(rightRows[12].thisMonthVal)) + parseFloat(convertZero(rightRows[13].thisMonthVal))+ parseFloat(convertZero(rightRows[14].thisMonthVal))+ parseFloat(convertZero(rightRows[15].thisMonthVal));
	var b2 = parseFloat(convertZero(rightRows[12].thisYearVal)) + parseFloat(convertZero(rightRows[13].thisYearVal))+ parseFloat(convertZero(rightRows[14].thisYearVal))+ parseFloat(convertZero(rightRows[15].thisYearVal));
	//2.现金流出小计
	var a3 = parseFloat(convertZero(rightRows[17].thisMonthVal)) + parseFloat(convertZero(rightRows[18].thisMonthVal)) + parseFloat(convertZero(rightRows[19].thisMonthVal));
	var b3 = parseFloat(convertZero(rightRows[17].thisYearVal)) + parseFloat(convertZero(rightRows[18].thisYearVal)) + parseFloat(convertZero(rightRows[19].thisYearVal));
	//投资活动产生的现金流量净额
	var f1 = parseFloat(convertZero(rightRows[16].thisMonthVal)) - parseFloat(convertZero(rightRows[20].thisMonthVal));
	var h1 = parseFloat(convertZero(rightRows[16].thisYearVal)) - parseFloat(convertZero(rightRows[20].thisYearVal));
	//3.现金流入小计
	var a4 = parseFloat(convertZero(rightRows[23].thisMonthVal)) + parseFloat(convertZero(rightRows[24].thisMonthVal)) + parseFloat(convertZero(rightRows[25].thisMonthVal));
	var b4 = parseFloat(convertZero(rightRows[23].thisYearVal)) + parseFloat(convertZero(rightRows[24].thisYearVal)) + parseFloat(convertZero(rightRows[25].thisYearVal));
	//3.现金流出小计
	var a5 = parseFloat(convertZero(rightRows[27].thisMonthVal)) + parseFloat(convertZero(rightRows[28].thisMonthVal)) + parseFloat(convertZero(rightRows[29].thisMonthVal));
	var b5 = parseFloat(convertZero(rightRows[27].thisYearVal)) + parseFloat(convertZero(rightRows[28].thisYearVal)) + parseFloat(convertZero(rightRows[29].thisYearVal));
	//筹资活动产生的现金流量净额
	var f2 = parseFloat(convertZero(rightRows[26].thisMonthVal)) - parseFloat(convertZero(rightRows[30].thisMonthVal));
	var h2 = parseFloat(convertZero(rightRows[26].thisYearVal)) - parseFloat(convertZero(rightRows[30].thisYearVal));
	//五、筹资活动产生的现金流量净额
	var t = parseFloat(convertZero(rightRows[10].thisMonthVal)) + parseFloat(convertZero(rightRows[21].thisMonthVal))+ parseFloat(convertZero(rightRows[31].thisMonthVal))+ parseFloat(convertZero(rightRows[32].thisMonthVal));
	var r = parseFloat(convertZero(rightRows[10].thisYearVal)) + parseFloat(convertZero(rightRows[21].thisYearVal))+ parseFloat(convertZero(rightRows[31].thisYearVal))+ parseFloat(convertZero(rightRows[32].thisYearVal));
	
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:4,row:{thisMonthVal:Digit.round(a, 2),thisYearVal:Digit.round(b, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:9,row:{thisMonthVal:Digit.round(a1, 2),thisYearVal:Digit.round(b1, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:10,row:{thisMonthVal:Digit.round(f, 2),thisYearVal:Digit.round(h, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:16,row:{thisMonthVal:Digit.round(a2, 2),thisYearVal:Digit.round(b2, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:20,row:{thisMonthVal:Digit.round(a3, 2),thisYearVal:Digit.round(b3, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:21,row:{thisMonthVal:Digit.round(f1, 2),thisYearVal:Digit.round(h1, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:26,row:{thisMonthVal:Digit.round(a4, 2),thisYearVal:Digit.round(b4, 2)}});	
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:30,row:{thisMonthVal:Digit.round(a5, 2),thisYearVal:Digit.round(b5, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:31,row:{thisMonthVal:Digit.round(f2, 2),thisYearVal:Digit.round(h2, 2)}});
	$('#saveCusComCaseFlowTable').datagrid('updateRow',{index:33,row:{thisMonthVal:Digit.round(t, 2),thisYearVal:Digit.round(r, 2)}});
	
	var  total_beginVal = 0;
	var  total_endVal = 0;
}


//计算补充材料表
function calculateCusComCaseFlowMetaSheetVal(j){
	
	var Meta_rows = $("#saveCusComCaseFlowMetaTable").datagrid('getRows'); 
	
	var  Meta_a = 0;
	var  Meta_b = 0;
	for(var i=0;i<Meta_rows.length;i++){
		if(i<18){
			Meta_a += parseFloat(convertZero(Meta_rows[i].thisMonthVal));
			Meta_b += parseFloat(convertZero(Meta_rows[i].thisYearVal));
		}	
	}
	
	
	var Meta_a1 = parseFloat(convertZero(Meta_rows[25].thisMonthVal)) - parseFloat(convertZero(Meta_rows[26].thisMonthVal))+ parseFloat(convertZero(Meta_rows[27].thisMonthVal))- parseFloat(convertZero(Meta_rows[28].thisMonthVal));
	var Meta_b1 = parseFloat(convertZero(Meta_rows[25].thisYearVal)) - parseFloat(convertZero(Meta_rows[26].thisYearVal)) + parseFloat(convertZero(Meta_rows[27].thisYearVal))- parseFloat(convertZero(Meta_rows[28].thisYearVal));
	
	$('#saveCusComCaseFlowMetaTable').datagrid('updateRow',{index:18,row:{thisMonthVal:Digit.round(Meta_a, 2),thisYearVal:Digit.round(Meta_b, 2)}});
	$('#saveCusComCaseFlowMetaTable').datagrid('updateRow',{index:29,row:{thisMonthVal:Digit.round(Meta_a1, 2),thisYearVal:Digit.round(Meta_b1, 2)}});
	
}



function convertZero(content){
	if(isNaN(content) || typeof(content) == "undefined" || content=="" || content==null){
		return "0";
	}else{
		return content;
	}
}

var Digit = {};
Digit.round = function(digit, length) {
    length = length ? parseInt(length) : 0;
    if (length <= 0) return Math.round(digit);
    digit = Math.round(digit * Math.pow(10, length)) / Math.pow(10, length);
    return digit;
};