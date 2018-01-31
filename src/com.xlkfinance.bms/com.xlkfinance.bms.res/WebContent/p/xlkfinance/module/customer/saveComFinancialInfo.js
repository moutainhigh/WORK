var editIndex = undefined;
function endEditingsIt(){
	if (editIndex == undefined){return true}
	if ($('#saveCusComCaseFlowMetaTable').datagrid('validateRow', editIndex)){
		$('#saveCusComCaseFlowMetaTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCellsIt(index, field){
	if (endEditingsIt()){
		$('#saveCusComCaseFlowMetaTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
		var editor = $('#saveCusComCaseFlowMetaTable').datagrid('getEditor', {index:index,field:field});
		editor.target[0].nextElementSibling.firstChild.focus();
	}
}


////
function endEditings(){
	if (editIndex == undefined){return true}
	if ($('#saveCusComCaseFlowTable').datagrid('validateRow', editIndex)){
		$('#saveCusComCaseFlowTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCells(index, field){
	if (endEditings()){
		$('#saveCusComCaseFlowTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
		var editor = $('#saveCusComCaseFlowTable').datagrid('getEditor', {index:index,field:field});
		editor.target[0].nextElementSibling.firstChild.focus();
	}
}

/////
function endEditingCusComIncomeReportTable(){
	if (editIndex == undefined){return true}
	if ($('#saveCusComIncomeReportTable').datagrid('validateRow', editIndex)){
		$('#saveCusComIncomeReportTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCellCusComIncomeReportTable(index, field){
	
	if (endEditingCusComIncomeReportTable()){
		$('#saveCusComIncomeReportTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
		var editor = $('#saveCusComIncomeReportTable').datagrid('getEditor', {index:index,field:field});
		editor.target[0].nextElementSibling.firstChild.focus();
	}
}

////////////
function endEditingLeftCusComBalanceSheetTable(){
	if (editIndex == undefined){return true}
	if ($('#saveLeftCusComBalanceSheetTable').datagrid('validateRow', editIndex)){
		$('#saveLeftCusComBalanceSheetTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCellLeftCusComBalanceSheetTable(index, field){
	if (endEditingLeftCusComBalanceSheetTable()){
		$('#saveLeftCusComBalanceSheetTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
		var editor = $('#saveLeftCusComBalanceSheetTable').datagrid('getEditor', {index:index,field:field});
		editor.target[0].nextElementSibling.firstChild.focus();
	}
}

function endEditingRightCusComBalanceSheetTable(){
	if (editIndex == undefined){return true}
	if ($('#saveRightCusComBalanceSheetTable').datagrid('validateRow', editIndex)){
		$('#saveRightCusComBalanceSheetTable').datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}
function onClickCellRightCusComBalanceSheetTable(index, field){
	if (endEditingRightCusComBalanceSheetTable()){
		$('#saveRightCusComBalanceSheetTable').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
		var editor = $('#saveRightCusComBalanceSheetTable').datagrid('getEditor', {index:index,field:field});
		editor.target[0].nextElementSibling.firstChild.focus();
	}
}

//公共
$.extend($.fn.datagrid.methods, {
	editCell : function(jq, param) {
		
		return jq.each(function() {
			var opts = $(this).datagrid('options');
			var fields = $(this).datagrid('getColumnFields', true).concat(
					$(this).datagrid('getColumnFields'));
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor1 = col.editor;
				if (fields[i] != param.field) {
					col.editor = null;
				}
			}
			$(this).datagrid('beginEdit', param.index);
			for (var i = 0; i < fields.length; i++) {
				var col = $(this).datagrid('getColumnOption', fields[i]);
				col.editor = col.editor1;
			}
		});
	}
});