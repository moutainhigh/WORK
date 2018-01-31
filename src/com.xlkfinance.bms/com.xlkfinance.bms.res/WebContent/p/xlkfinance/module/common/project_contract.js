//根据合同模板加载合同内容
var isReadContract=0;
var commonDebtorPids="";
var isParent=0;
function loadProjectContract(dialogId,contractType,tempLateId,debtorId,creditId,projectId,mortgagorId,suretyId,exdProjectId,mortgagorType,pids,assBaseIds,rateChgId) {
	$.ajax({
		type: "POST",
        url: BASE_PATH+"contractInfoController/getContractDynamicParmList.action",
        data:{	"contractType":contractType,
        		"tempLateId":tempLateId,
        		"debtorProjectId":debtorId,
        		"creditProjectId":creditId,
        	  	"projectId":projectId,
        	  	"mortgagorId":mortgagorId,
        	  	"suretyId":suretyId,
			  	"exdProjectId":exdProjectId,
			  	"commonDebtorPids":pids,
			  	"rateChgId":rateChgId
		},
        dataType: "json",
        success: function(data){
        	if(data!=null && data.length!=null){
        		//合同加载成功
        		isReadContract=1;
        		
	        	var dom = '<table id="add_templateDialog" class="datagrid-btable"> <tr style="line-height:30px">';

	        	for (var i = 0; i < data.length; i++) {
        			if(data[i].fixedVal==null){
        				data[i].fixedVal=='';
        			}        			
        			if(data[i].valConvertFlag==5){
        				dom = dom+'<td align="right" width="160">'+data[i].matchName+':</td>';
            			dom = dom+'<td align="left"  width="200"><input class="easyui-numberbox text2_style" onblur="formatTheMoney(this)"  id="'+data[i].pid+'" value="'+(data[i].fixedVal==null?'':data[i].fixedVal)+'" name="'+data[i].matchFlag+'"/></td>'; 
            			
        			}else{
        				dom = dom+'<td align="right" width="160">'+data[i].matchName+':</td>';
            			dom = dom+'<td align="left"  width="200"><input class="easyui-textbox text2_style" id="'+data[i].pid+'" value="'+(data[i].fixedVal==null?'':data[i].fixedVal)+'" name="'+data[i].matchFlag+'"/></td>'; 
            			
        			}
        			
 	       		 	if(i%2!=0){
 	       		 		dom=dom+'</tr><tr style="line-height:30px">';
 	       		 	}
				}
	        	dom=dom+"<input type='hidden' value='"+tempLateId+"' id='tempLateId'></tr></table>";
	        	// 合同模板中的表格标签
	        	var tableLabel="";
	        	if(projectId !=null && projectId>0){
	        		tableLabel = getContractTableLabel(tempLateId);
		        	if(tableLabel !=""){
		        		var tableDom ="<table id='add_contract_table'  class='easyui-datagrid' style='height:auto;width: auto;' > </table>";  // getTableParameterVal(tempLateId);
			        		dom = dom+tableDom;
		        	}
	        	}
	        	$("#"+dialogId).html(dom); 
	        	
	        	if("" != tableLabel){
	        		addloadContractTable(tableLabel,projectId,mortgagorType,assBaseIds);
	        	}
        	}else{
        		$.messager.alert("操作提示", "读取合同失败，请检查是否上传合同模板!", "error");
    			return ;
        	}
        }
	});
}
// 格式化金额
function formatTheMoney(input)
{
	input.value=formatMoney(input.value);
}

//新增获取合同表格标签
function getContractTableLabel(tempLateId){
	var tableLabel="";
	$.ajax({
		type: "POST",
		async: false,
        url: BASE_PATH+"contractInfoController/getContractTableLabel.action",
        data:{	"tempLateId":tempLateId
		},
        dataType: "json",
        success: function(data){
			if(data!=null && data.length>0){
				tableLabel= data;
			}
        }
	});
	
	
	return tableLabel;
}

//新增合同表格加载数据
function addloadContractTable(tableLabel,projectId,mortgagorType,assBaseIds){
	// 查询表头
	var url = BASE_PATH+'contractInfoController/getContractTableTitle.action?tableLabel='+tableLabel;
	$.ajax({
		type: "POST",
		async: false,
        url: url,
        dataType: "json",
        success: function(data){
        	
			if(data!=null && data.length>0){
				var titleCols = [];
				var row = {};
				row['field']="pid";
				row['hidden'] = true;
				titleCols[titleCols.length]=row;
				
				for(var i=0;i<data.length;i++)
				{
					var row = {};
					row['field']="col"+i;
					row['title'] = data[i];
					row['width'] = 150;
					row['editor']={type:'validatebox',options:{precision:2}};
			
					titleCols[titleCols.length]=row;
				}	
				
				var allCols=[];
				allCols[0] = titleCols;
				// 加载表格数据
				 url = BASE_PATH+'contractInfoController/getContractTableRows.action?tableLabel='+tableLabel+"&projectId="+projectId+"&mortgageGuaranteeType="+mortgagorType+"&assBaseIds="+assBaseIds;
				$("#add_contract_table").datagrid({
				    url: url,
				    method: 'POST',
				    rownumbers: true,
				    pagination: false,
				    fitColumns:true,
				    singleSelect: false,
					selectOnCheck: false,
					checkOnSelect: false,
					onClickCell: onClickCellsItContract,
				    columns:allCols
				});
			}
        }
	});
}

//编辑合同表格加载数据
function editloadContractTable(tableLabel,contractId){
	
	// 查询表头
	var url = BASE_PATH+'contractInfoController/getContractTableTitle.action?tableLabel='+tableLabel;
	$.ajax({
		type: "POST",
		async: false,
        url: url,
        dataType: "json",
        success: function(data){
        	
			if(data!=null && data.length>0){
				var titleCols = [];
				
				var row = {};
				row['field']="pid";
				row['hidden'] = true;
				titleCols[titleCols.length]=row;
				
				for(var i=0;i<data.length;i++)
				{
					row = {};
					row['field']="col"+i;
					row['title'] = data[i];
					row['width'] = 150;
					row['editor'] = {type:'validatebox',options:{precision:2}};
			
					titleCols[titleCols.length]=row;
				}	
				
				var allCols=[];
				allCols[0] = titleCols;
				// 加载表格数据
				url = BASE_PATH+"contractInfoController/listContractTabUrl.action?contractId="+contractId;
				$("#edit_contract_table").datagrid({
				    url: url,
				    method: 'POST',
				    rownumbers: true,
				    pagination: false,
				    idField: 'pid',
				    fitColumns:true,
				    singleSelect: false,
					selectOnCheck: false,
					checkOnSelect: false,
					onClickCell: onClickCellsItContractEdit,
				    columns:allCols
				});
			}
        }
	});
}

//合同编辑
function editContract(dialogId,contractUrl,tempLateId,contractId){
	$.ajax({
		type: "POST",
        url:  BASE_PATH+"contractInfoController/editDocument.action",
        data:{"contractUrl":contractUrl,"tempLateId":tempLateId,"contractId":contractId},
        dataType: "json",
        success: function(data){
        	var dom = "<table id='edit_templateDialogDiv' class='datagrid-btable'> <tr  style='line-height:30px'>";
        	 for (var i = 0; i < data.length; i++) {
   		 		dom = dom+'<td align="right" width="160">'+data[i].matchName+':</td>';
   		 		dom = dom+'<td align="left" width="200"><input class="easyui-textbox text2_style" id="'+data[i].pid+'" value="'+(data[i].matchValue==null ||data[i].matchValue=='null' ?'':data[i].matchValue)+'" name="'+data[i].matchFlag+'"/></td>'; 
       		 
   		 		if(i%2!=0){
       		 		dom=dom+'</tr><tr style="line-height:30px">';
       		 	}
			}
        	 dom=dom+"</table>";
        	dom=dom+"<input type='hidden' value='"+contractId+"' id='edit_contractId'>";
         	dom=dom+"<input type='hidden' value='"+contractUrl+"' id='edit_contractUrl'>";
         	dom=dom+"<input type='hidden' value='"+tempLateId+"' id='edit_tempLateId'>";
         	var flag=getEditTableParameterVal(contractId);
         	
        	var tableLabel = getContractTableLabel(tempLateId);
        	if(tableLabel !=""){
         		var tableDom ="<table id='edit_contract_table'  class='easyui-datagrid' style='height:auto;width: 96%;' > </table>"; 
        		dom = dom+tableDom;
         	}
        	$("#"+dialogId).html(dom);
        	if(tableLabel !=""){
        		editloadContractTable(tableLabel,contractId);
        	}
        }
	});
}

//新增获取合同表格数据
function getEditTableParameterVal(contractId){
	var flag=false;
	$.ajax({
		type: "POST",
		async: false,
        url: BASE_PATH+"contractInfoController/listContractTabUrl.action",
        data:{	"contractId":contractId},
        dataType: "json",
        success: function(data){
			if(data!=null && data.length>0){
				flag= true;
			}
        }
	});
	return flag;
}

var editIndex = undefined;
function endEditingsItContract(divName){
	if (editIndex == undefined){return true}
	if ($('#'+divName).datagrid('validateRow', editIndex)){
		$('#'+divName).datagrid('endEdit', editIndex);
		editIndex = undefined;
		return true;
	} else {
		return false;
	}
}

function onClickCellsItContract(index, field){
	//debugger;
	if (endEditingsItContract('add_contract_table')){
		$('#add_contract_table').datagrid('selectRow', index)
				.datagrid('editCell', {index:index,field:field});
		editIndex = index;
	}
}
function onClickCellsItContractEdit(index, field){
	if (endEditingsItContract("edit_contract_table")){
		$('#edit_contract_table').datagrid('selectRow', index)
		.datagrid('editCell', {index:index,field:field});
		editIndex = index;
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

// 合同操格式化
function contractOperationFilter(value,row,index){
	var downloadDom = "<a href='"+BASE_PATH+"beforeLoanController/downloadData.action?path="+row.contractUrl+"&fileName="+row.contractName+"'><font color='blue'>点击下载</font></a> | ";
	var contractReadDom = "<a href='javascript:void(0)' onclick='contractReview("+'"'+row.contractUrl+'"'+")'><font color='blue'>合同预览</font></a> ";
	return downloadDom+contractReadDom;
}

//合同预览事件
function contractReview(url){
	$.ajax({
		type: "POST",
        url: BASE_PATH+"contractInfoController/reviewDocument.action",
        data:{"url":url},
        dataType: "text",
        success: function(data){
        	data = data.replace('"',"").replace('"',"");
        	openContractUrl(BASE_PATH+data);
        }
	});
}
// 合同预览页面打开
function openContractUrl( url ){
   var f=document.createElement("form");
   f.setAttribute("action" , url );
   f.setAttribute("method" , 'get' );
   f.setAttribute("target" , '_black' );
   document.body.appendChild(f);
   f.submit();
}

function checkContractCount(creditProId,templateId,contractType){
	var flag=false;
	$.ajax({
		url : BASE_PATH+'contractInfoController/getContractCount.action?projectId='+creditProId+'&templateId='+templateId+"&contractType="+contractType,
		type : 'post',
		cache: false,
		async: false,
		success : function(result) {
			var ret = eval("("+result+")");
			var count=ret.header["count"]; 
			if(count>0){
				flag=true;
			}
		}
	});
	return flag;
}



function getChildContractName(contractId){
	var contractName='';
	$.ajax({
		url : BASE_PATH+'contractInfoController/getChildContractName.action?contractId='+contractId,
		type : 'post',
		cache: false,
		async: false,
		success : function(result) {
			var ret = eval("("+result+")");
			contractName=ret.header["contractName"];
		}
	});
	return contractName;
}



function getContractTemplateCombox(tempInputId,parentInputId,contractDialogId,customerType,templateCatelog,projectId){
	$('#'+tempInputId).combobox({    
		url:BASE_PATH+'beforeLoanController/getTempLateList.action?customerType='+customerType+"&templateCatelogText="+templateCatelog+"&projectId="+projectId,    
	    valueField:'pid',    
	    textField:'comboboxTemplateText',
	    value:'0',
	    onSelect: function(rec){
	    	isParent=0;
	    	var contractNumberFun=rec.contractNumberFun;
	    	if(contractNumberFun !=null){
	    		var index=contractNumberFun.indexOf("parent");
		    	if(index>-1){
		    		isParent=1;
		    	}
	    	}
	    	$('#'+parentInputId).combobox({url:BASE_PATH+"contractInfoController/getParentContracts.action?projectId="+projectId+"&isParent="+isParent,valueField:'pid',textField:'contractName' });
        },onChange:function(){  
        	isReadContract=0;
        	$("#"+contractDialogId).html('');
        } 
	});  
	//$('#templateName').combobox({url:BASE_PATH+"beforeLoanController/getTempLateList.action?customerType="+customerType+"&templateCatelogText="+templateCatelog,valueField:'pid',textField:'comboboxTemplateText' });
	
}

function editCreditContractData(mcontracType,tempId,contractId,projectId,contractName){	

	$('<div id="editContractData" />').dialog({
		href : BASE_PATH+'contractInfoController/editContractData.action?contractType='+mcontracType+'&contractId='+contractId+'&tempId='+tempId+'&projectId='+projectId+'&contractName='+contractName,
		width : 1400,
		height : 600,
		modal : true,
		title : '修改合同 ',
		buttons : [ {
			text : '保存',
			iconCls : 'icon-save',
			handler : function() {
				window.saveContractData();
			}	
			},{
				text : '取消 ',
				iconCls : 'icon-cancel',
				handler : function() {
					$("#editContractData").dialog('close');
					}
				} ],
		onClose : function() {
			$(this).dialog('destroy');
		},
		onLoad : function() {
		}
	});

}