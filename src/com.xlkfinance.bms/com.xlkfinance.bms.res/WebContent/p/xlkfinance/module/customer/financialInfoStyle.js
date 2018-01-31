/**
 * 资产负债表itemName项目style
 */
function formatCss(val,row){
		 if (val.indexOf("合计")!=-1 || val.indexOf("总计")!=-1){
             return '<label style="font-weight:bold;">'+val+'</label>';
         }else if(val.charAt(val.length-1)=="："){
             return '<label>'+val+'</label>';
         }else if(val.indexOf("减")!=-1){
        	 return '<label style="padding-left:20px;">'+val+'</label>';
         }else {
             return '<label style="padding-left:10px;">'+val+'</label>';
         }
}

//资产负债表不显示的行处理
function hideLeftBeginVal(value, row, index){
	
	//var isEdit = $("#isEditReport").val();
	
	if(index==0 || index==15 || index==20 || index==29 || index==34){
		return valStyle();
	}
}

function hideLeftEndVal(value, row, index){
	
	if(index==0 || index==15 || index==20 || index==29 || index==34){
		return valStyle();
	}
}


function hideRightBeginVal(value, row, index){
	
	if(index==0 || index==16 || index==23 || index==27){
		return valStyle();
	}
}

function hideRightEndVal(value, row, index){
	
	if(index==0 || index==16 || index==23 || index==27){
		return valStyle();
	}
}
//资产负债表不显示的行处理。结束


/**
 * 利润表itemName项目style
 */
function incomeItemNameStyle(value,rowData,rowIndex){
	
	if(value.charAt(0)=="一" || value.charAt(0)=="二" || value.charAt(0)=="三" || value.charAt(0)=="四" || value.charAt(0)=="五" || value.charAt(0)=="加" || value.charAt(0)=="减"){
    }else{
    	//return "padding-left:20px;";
    	return "text-indent:2em;";
    }
}

//利润表不显示的行处理
function incomeThisMonthValStyle(value, row, index){
	
	/**
	if(index==0 || index==3 || index==8){
		return valStyle();
	}
	**/
}

function incomeThisYearValStyle(value, row, index){
	
	/**
	if(index==0 || index==3 || index==8){
		return valStyle();
	}
	**/
}
//利润表不显示的行处理。结束

/**
 * 现金流量表itemName项目style
 */
function caseFlowItemNameStyle(value,rowData,rowIndex){
	
	if(value.charAt(0)=="一" || value.charAt(0)=="二" || value.charAt(0)=="三" || value.charAt(0)=="四" || value.charAt(0)=="五"){
		return "font-weight:bold;";
    }else if(value.indexOf("小计")!=-1){
    	//return "padding-left:20px;";
    	return "text-indent:2em;";
    }
}

//现金流量表不显示的行处理
function caseFlowThisMonthValStyle(value, row, index){
	
	if(index==0 || index==11 || index==22){
		return valStyle();
	}
}

function caseFlowThisYearValStyle(value, row, index){
	
	if(index==0 || index==11 || index==22){
		return valStyle();
	}
}
//现金流量表不显示的行处理。结束

/**
 * 现金流量补充材料表itemName项目style
 */
function caseFlowMetaItemNameStyle(value,rowData,rowIndex){
	
	if(value.charAt(0)=="1" || value.charAt(0)=="2" || value.charAt(0)=="3"){
    }else if(rowIndex>=4 && rowIndex<=18){
    	//return "padding-left:40px;";
    	return "text-indent:4em;";
    }else{
    	//return "padding-left:20px;";
    	return "text-indent:2em;";
    }
}

//现金流量补充材料表不显示的行处理
function caseFlowMetaThisMonthValStyle(value, row, index){
	
	if(index==0 || index==19 || index==24){
		return valStyle();
	}
}

function caseFlowMetaThisYearValStyle(value, row, index){
	
	if(index==0 || index==19 || index==24){
		return valStyle();
	}
}
//现金流量补充材料表不显示的行处理。结束

function valStyle(){
	return "color:FFFFFF;";
	//return "display:none;";
	//return "color:FFFFFF;font-size:1px;display:none";
}