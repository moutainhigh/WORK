<%@page language="java" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/layout/taglibs.jsp"%>
<style type="text/css">

</style>
<script type="text/javascript">
var url="";
$(function(){
	editContractD();
	
	if('${contractType}' =="DY"){
		
		url =BASE_PATH+"mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId=${projectId}&mortgageGuaranteeType=1&oldProject=-1";
	}else if('${contractType}' =="ZY"){
		
		url = BASE_PATH+"mortgageController/getProjectAssBaseByMortgageGuaranteeType.action?projectId=${projectId}&mortgageGuaranteeType=2&oldProject=-1";
	}else if('${contractType}' =="BZ"){
		
		url =BASE_PATH+"secondBeforeLoanController/getAssureByProjectId.action?projectId=${projectId}";
	}else{
		url =BASE_PATH+"creditsController/getProjectAcctAndPublicManByProjectId.action?projectId=${projectId}";
	}
	
});


function editContractD(){
	$.ajax({
		type: "POST",
        url:  BASE_PATH+"contractInfoController/editDocument.action",
        data:{"contractUrl":'123',"tempLateId":'${tempId}',"contractId":'${contractId}'},
        dataType: "json",
        success: function(data){
        	var dom = "<table id='edit_templateDialogDiv' class='datagrid-btable'> <tr  style='line-height:30px'>";
        	 for (var i = 0; i < data.length; i++) {
   		 		//dom = dom+'<td align="right" width="160">'+data[i].matchName+':</td>';
   		 		dom = dom+'&nbsp;&nbsp;<td style="line-height:30px;height:30px;" align="left" width="160"><input readOnly="readOnly" class="easyui-textbox text2_style" id="'+data[i].pid+'" value="'+(data[i].matchValue==null ||data[i].matchValue=='null' ?'':data[i].matchValue)+'" name="'+data[i].matchFlag+'"/></td>'; 
       		 
   		 		if(i%2!=0){
       		 		dom=dom+'</tr><tr style="line-height:30px;height:30px;">';
       		 	}
			}
        	 dom=dom+"</table>";
        	
         	
        	$("#old_edit_templateDialog").html(dom);
        	
        }
	});
}

function makeContract(){
	var row = $("#contract_obj_grid").datagrid('getSelections');
	if (row.length == 1) {
		
		loanContract(row[0].pid);
	} else if (row.length > 1) {
		$.messager.alert("操作提示", "每次只能修改一条数据,请重新选择！", "error");
	} else {
		$.messager.alert("操作提示", "请选择数据", "error");
	}
}

function loanContract(pid){
	var projectId="${projectId}";
	var tempLateId='${tempId}';
	$.ajax({
		type: "POST",
        url: BASE_PATH+"contractInfoController/getContractDynamicParmList.action",
        data:{	"contractType":'${contractType}',
        		"tempLateId":tempLateId,
        		"debtorProjectId":projectId,
        		"creditProjectId":projectId,
        	  	"projectId":projectId,
        	  	"mortgagorId":pid,
        	  	"suretyId":pid,
			  	"exdProjectId":-1,
			  	"commonDebtorPids":''
		},
        dataType: "json",
        success: function(data){
        	if(data!=null && data.length!=null){
        		
	        	var dom = '<table id="add_templateDialog" class="datagrid-btable"> <tr style="line-height:30px">';

	        	for (var i = 0; i < data.length; i++) {
        			if(data[i].fixedVal==null){
        				data[i].fixedVal=='';
        			}
      
        			dom = dom+'<td align="right" width="160">'+data[i].matchName+':</td>';
        			dom = dom+'<td align="left"  width="200"><input class="easyui-textbox text2_style" id="'+data[i].pid+'" value="'+(data[i].fixedVal==null?'':data[i].fixedVal)+'" name="'+data[i].matchFlag+'"/></td>'; 
        			
 	       		 	if(i%2!=0){
 	       		 		dom=dom+'</tr><tr style="line-height:30px">';
 	       		 	}
				}
	        	dom=dom+"</tr></table>";
	        	// 合同模板中的表格标签
	        	var tableLabel="";
	        	if(projectId !=null && projectId>0){
	        		tableLabel = getContractTableLabel(tempLateId);
		        	if(tableLabel !=""){
		        		var tableDom ="<table id='add_contract_table'  class='easyui-datagrid' style='height:auto;width: auto;' > </table>";  // getTableParameterVal(tempLateId);
			        		dom = dom+tableDom;
		        	}
	        	}
	        	$("#edit_templateDialogData").html(dom); 
	        	
	        	
        	}else{
        		$.messager.alert("操作提示", "读取合同失败，请检查是否上传合同模板!", "error");
    			return ;
        	}
        }
	});
}

//保存编辑后的类容
function saveContractData(){
	var content = "";
	
	$("#edit_templateDialogData").find("input").each(function (i){
		var key = $(this).attr("id");
		var value = $(this).val();
		var flag = $(this).attr("name");
		if(key!="contractId" && key!="contractUrl" && key!="tempLateId"){
			content=content+key+":"+value+":"+flag+"###";
		}
		
	});
	
	$.ajax({
		type: "POST",
        url: BASE_PATH+"contractInfoController/saveContractData.action",
        data:{"content":content,"contractId":'${contractId}',"tempLateId":'${tempId}'},
        dataType: "json",
        success: function(data){
        	alert("编辑成功");
        	 $('#editContractData').dialog('close');
        }
	});
}
</script>
<div id="main_body" style="width:1300px;float: left;">
<div id="cus_content" class="pt15" style="width:1300px;">


<table id="contract_obj_grid" class="easyui-datagrid" 
	style="height:auto;width: auto;"
	data-options="
	    url: ''+url,
	    method: 'get',
	    rownumbers: true,
	    singleSelect: true, 
	    fitColumns:true">
	<!-- 表头 -->
	<thead><tr>
	    <th data-options="field:'pid',checkbox:true" ></th>
	    <th data-options="field:'dataObject'" width="200px" >合同数据对象</th>
	</tr></thead>
</table>

 <div style="padding-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="makeContract()">读取数据</a>
</div>	
<!-- <div id="edit_template-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-save" onclick="saveEidtContent()">保存</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#editContractDialog').dialog('close')">关闭</a>
	</div> -->
	<div id="editContractDialog" class="scroll-bar-div" style="width:850px;height:400px;float: left;">
    	<div class="fitem" style="margin-top:5px;padding-left: 50px;">
			<label style="text-align: left;">合同名称：</label>
            <input name="contractName" id="editcontractName" class="easyui-validatebox"  readonly="readonly" style="width:350px;" value="${contractName }" />
		</div>
		<%-- <div class="fitem" style="margin-top:5px;padding-left:50px;">
			<label style="text-align: left;">所属合同：</label>
            <input name="" id="edit_parent_contract_id" class="easyui-combobox" style="width: 350px;" editable="false" data-options="valueField:'pid',textField:'contractName',url:'${basePath }contractInfoController/getParentContracts.action?projectId=${projectId }&isParent=1'" />
		</div> --%>
	    <div id="edit_templateDialogData"  style="width: 800px;border-right: solid 1px #999">
	    	 
		</div>
</div>

<div id="old_edit_template-buttons" style="float: left;">
   <div id="old_edit_templateDialog" class="datagrid-btable" style="width: 400px;">
   	 
   </div>
</div>
    
</div>
</div>