//资产负债表不显示的行处理
function hideLeftBeginVal(value, row, index){
	
	var isEdit = $("#isEditReport").val();
	
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',0);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',15);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',20);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',29);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',34);
	
	if(index==0 || index==15 || index==20 || index==29 ||index==34 ){
		return "display:none;";
		//return "color:FFFFFF;font-size:1px;display:none";
	}
}

function hideLeftEndVal(value, row, index){
	
	var isEdit = $("#isEditReport").val();
	
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',0);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',15);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',20);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',29);
	$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit',34);
	
	if(index==0 || index==15 || index==20 || index==29 || index==34){
		return "display:none;";
		//return "color:FFFFFF;font-size:1px;display:none";
	}
}


function hideRightBeginVal(value, row, index){
	
	var isEdit = $("#isEditReport").val();
	
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',0);
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',16);
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',23);
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',27);
	
	if(index==0 || index==16 || index==23 || index==27){
		return "display:none;";
		//return "color:FFFFFF;font-size:1px;display:none";
	}
}

function hideRightEndVal(value, row, index){
	
	var isEdit = $("#isEditReport").val();
	
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',0);
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',16);
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',23);
	$('#saveRightCusComBalanceSheetTable').datagrid('endEdit',27);
	
	if(index==0 || index==16 || index==23 || index==27){
		return "display:none;";
		//return "color:FFFFFF;font-size:1px;display:none";
	}
}
//资产负债表不显示的行处理。结束